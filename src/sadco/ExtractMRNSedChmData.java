package sadco;

import common2.Queue;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Does the actual extraction of the requested data.
 *
 * SadcoMRNApps
 * screen.equals("extract")
 * version.equals("doextract")
 * extrType.equals("sedchem")
 *
 * @author 011001 - SIT Group
 * @version
 * 011001 - Ursula von St Ange - create class
 * 020528 - Ursula von St Ange - update for non-frame menus
 * 020528 - Ursula von St Ange - update for exception catching
 * 020710 - Ursula von St Ange - fetch watphy records in batches of depths
 * 030916 - Ursula von St Ange - adapt from ExtractMRNWatPolData
 * 051201 - Ursula von St Ange - adapt from ExtractMRNSedPolData
 * 091214 - Ursula von St Ange - change for inventory user (ub01)           <br>
 * 100105 - Ursula von St Ange - change for queuing of job (ub02)           <br>
 * 100615 - Ursula von St Ange - non-inventory users don't queue (ub03)     <br>
 * 100624 - Ursula von St Ange - change logging of job to beginning with
 *                               update at end (ub04)                       <br>
 * 121113 - Ursula von St Ange - add etopo2 depth (ub06)                    <br>
 */
public class ExtractMRNSedChmData { //extends CompoundItem {

    boolean dbg = false;
    //boolean dbg = true;
    boolean dbg2File = false;
    //boolean dbg2File = true;
    boolean dbgQ = false;
    //boolean dbgQ = true;

    RandomAccessFile dbgFile = null;

    String thisClass = this.getClass().getName();
    String thisFunction = "";

    /** some common functions */
    common.edmCommonPC ec = new common.edmCommonPC();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    // file stuff
    static RandomAccessFile ofile;

    // arguments
    String sessionCode;
    String reqNumber;
    String fileNameIn;                                                  //ub02
    String fileName;
    String extrType;
    String surveyId;
    String startDate;
    String endDate;
    String latNorth;
    String latSouth;
    String longWest;
    String longEast;
    float latitudeNorth;
    float latitudeSouth;
    float longitudeWest;
    float longitudeEast;
    Timestamp dateStart;
    Timestamp dateEnd;
    String flagType;
    String dataSet;
    String password;
    String passKey = "";
    String userType = "";                                               //ub01

    // variable from database
    int             noRecords = 0;
    long            headerPos;

    // minimum and maximum's
    float           maxLatitude  = -20.0f;
    float           minLatitude  =  99.0f;
    float           maxLongitude = -20.0f;
    float           minLongitude =  99.0f;
    Timestamp       minDate  = Timestamp.valueOf("2100-01-01 00:00:00.0");
    Timestamp       maxDate  = Timestamp.valueOf("1900-01-01 00:00:00.0");

    /**
     * Main constructor ExtractMRNSedChmData
     * @param   args   String array of arguments as from form or URL
     */
    public ExtractMRNSedChmData(String args[])  {

        thisFunction = "constructor";

        // Get the arguments
        getArgParms(args);

        // extract the user record from db
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        String userid = sessions[0].getUserid();

        // get the correct PATH NAME - used while debugging
        String rootPath = "";

        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                rootPath = sc.HOSTDIR + "inv_user/";                    //ub01
            } else {                                                    //ub01
                rootPath = sc.HOSTDIR + userid + "/";                   //ub01
            } // if ("0".equals(userType))                              //ub01
            //rootPath = sc.HOSTDIR+ userid + "/";
        } else {
            rootPath = sc.LOCALDIR + "marine/";
        } // if host

        if (dbg && dbg2File) {
            String dbgFileName = rootPath + "ExtractMRNSedChmData.dbg";
            ec.deleteOldFile(dbgFileName);
            dbgFile = ec.openFile(dbgFileName, "rw");
            if (ec.getHost().startsWith(sc.HOST)) {
                ec.submitJob("chmod 644 " + dbgFileName);
            } //if (ec.getHost().startsWith(sc.HOST)) {
        } // if (dbg & dbg2File)

        printDebug("userid = " + userid);
        printDebug("host = " + ec.getHost());

        for (int i = 0; i < args.length; i++) {
            printDebug("" + args[i]);
        } // for i

        // new filename, but keep the original file name                //ub02
        fileNameIn = fileName;                                          //ub02
        if (!reqNumber.equals("")) {
            fileName = reqNumber + "_" + fileName;
        } // if
        fileName += ".dbsc";
        printDebug("fileName = " + fileName);

        // delete an old dummy file for Progress purposes ......
        String dummyFile = rootPath + fileName + ".finished";
        printDebug(rootPath + fileName);
        ec.deleteOldFile(dummyFile);

        // create .queued file for Progress purposes ......             //ub02
        String dummyFile2 = rootPath + fileName + ".queued";            //ub02
        Queue queue = null;

        Timestamp startQDate = new Timestamp(new Date().getTime());     //ub04
        if ("0".equals(userType)) {  // from inventory                  //ub03
            // create .queued file for Progress purposes ......         //ub02
            ec.createNewFile(dummyFile2);                               //ub02

            // queue the extraction                                     //ub02
            if (!dbgQ) {
                queue = new Queue(userid,thisClass+" "+fileNameIn,args);//ub02
                queue.enQueue();                                        //ub02
                queue.wait4Turn();                                      //ub02
            } // if (!dbgQ)

            // delete .queued file, and create .progressing file        //ub02
            ec.deleteOldFile(dummyFile2);                               //ub02
        } // if ("0".equals(userType)) {                                //ub03

        // create the user_log entry                                    //ub04
        Timestamp endQDate = new Timestamp(new Date().getTime());       //ub04
        LogUsers log = createLogUsers(                                  //ub04
            userid, endQDate, ec.timeDiff(endQDate, startQDate));       //ub04

        // create the 'processing' file
        dummyFile2 = rootPath + fileName + ".processing";
        ec.createNewFile(dummyFile2);

        // Open the data file and write an empty line for header later
        ec.deleteOldFile(rootPath + fileName);
        ofile = ec.openFile(rootPath + fileName, "rw");

        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob("chmod 644 " + rootPath + fileName);
        } //if (ec.getHost().startsWith(sc.HOST)) {

        try {
            ofile.writeBytes(" ");
            headerPos = ofile.getFilePointer();
        } catch (Exception e) {
            ec.processError(e, thisClass, "constructor", "Header Pos Error");
        }  // try-catch
        ec.writeFileLine(ofile, ec.frm(" ", 100));

        // print the column headings
        ColumnHeadings();

        // check if the arguments are valid
        boolean validQuery = true;
        if ("".equals(surveyId)) {
            if ("".equals(latNorth) || "".equals(latSouth) ||
                "".equals(longWest) || "".equals(longEast) ||
                "".equals(startDate) || "".equals(endDate)) {
                printErrorMessage();
                validQuery = false;
            } else {
                latitudeNorth = new Float(latNorth).floatValue();
                latitudeSouth = new Float(latSouth).floatValue();
                longitudeWest = new Float(longWest).floatValue();
                longitudeEast = new Float(longEast).floatValue();
                // correct end latitudes and longitudes
                latitudeSouth -= .00001f;
                longitudeEast -= .00001f;
                // timestamp in format yyyy-mm-dd hh:mm:ss.fffffffff
                dateStart = Timestamp.valueOf(startDate + " 00:00:00.00");
                dateEnd = Timestamp.valueOf(endDate + " 00:00:00.00");
            } // any range values ""
        } else { // survey-id specified - for when there are no records
            latitudeNorth = maxLatitude;
            latitudeSouth = minLatitude;
            longitudeWest = maxLongitude;
            longitudeEast = minLongitude;
            // correct end latitudes and longitudes
            latitudeSouth -= .00001f;
            longitudeEast -= .00001f;
            // timestamp in format yyyy-mm-dd hh:mm:ss.fffffffff
            dateStart = minDate;
            dateEnd = maxDate;
        }// if ("".equals(surveyId))

        // check if flagType combination is valid.  If not, revert back to
        // unflagged data extraction only
        printDebug("flagType = " + flagType +
            ", dataSet = " + dataSet + ", password = " + password);
        if (!"".equals(flagType)) {
            // was there a dataSet or a Password
            if ("".equals(dataSet) || "".equals(password)) {
                printDebug("password test 1:true (dataset or password blank) ");
                flagType = "1";
            } else {
                printDebug("password test 1:false (dataset or password blank)");
                // is this user registered for the dataset
                SadUserDset[] userDset = new SadUserDset(userid, dataSet).get();
                printDebug("userDset.length = " +
                    userDset.length);
                if (userDset.length == 0) {
                    printDebug("password test 2:true (userDset.length == 0)");
                    flagType = "1";
                } else {
                    printDebug("password test 2:false (userDset.length == 0)");
                    // did the user give the right password?
                    SadUsers[] users = new SadUsers().get(
                        SadUsers.FLAG_PASSWORD,
                        SadUsers.USERID + "='" + userid + "'", "");
                    printDebug("password = " + password +
                        ", users[0].getFlagPassword() = " +
                        users[0].getFlagPassword());
                    printDebug("users[0] = " + users[0]);
                    if (!users[0].getFlagPassword().equals(password)) {
                        printDebug("password test 3:true " +
                            users[0].getFlagPassword() + " " + password);
                        flagType = "1";
                    } else {
                        printDebug("password test 3:false " +
                            users[0].getFlagPassword() + " " + password);
                        // get the dataset password
                        SadUsers[] dataset = new SadUsers().get(
                            SadUsers.PASSWORD,
                            SadUsers.USERID + "='" + dataSet + "'", "");
                        if (dataset.length == 0) {
                            printDebug("password test 4:true " +
                                " (dataset.length == 0)");
                            flagType = "1";
                        } else {
                            printDebug("password test 4:false " +
                                " (dataset.length == 0)");
                            passKey = dataset[0].getPassword(); //.toUpperCase();
                        } // if (dataset.length > 0)
                    }// if (!users[0].getPassword.equals(password)
                } // if (userDSet.length == 0)
            } // if ("".equals(dataSet) || "".equals(password))
        } else {
            flagType = "1";
        } // if (!"".equals(flagType))
        System.gc();

        printDebug("flagType = " + flagType);
        printDebug("validQuery = " + validQuery);

        if (validQuery) {
            MrnStation[] stations = getStationRecords();
            System.gc();
            getDataRecords(stations);
        } // if (validQuery)

        printLegend();
        printHeader();

        // clean up
        ec.closeFile(ofile);
        if (dbg && dbg2File) {
            ec.closeFile(dbgFile);
        } // if (dbg && dbg2File)

        Timestamp endJDate = new Timestamp(new Date().getTime());       //ub04
        updateLogUsers(log, ec.timeDiff(endJDate, endQDate));           //ub04

        // delete the .processing file
        ec.deleteOldFile(dummyFile2);
        // create the dummy file for Progress purposes ......
        ec.createNewFile(dummyFile);

        // dequeue extraction                                           //ub02
        if ("0".equals(userType)) {  // from inventory                  //ub03
            if (!dbgQ) {
                queue.deQueue();                                        //ub02
            } // if (!dbgQ)
        } // if ("0".equals(userType))                                  //ub03

    } // ExtractMRNSedChmData(String args[])


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        if (dbg) for (int i = 0; i < args.length; i++) {
            System.out.println("" + args[i]);
        } // for i
        sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        reqNumber = ec.getArgument(args, sc.REQNUMBER);
        fileName = ec.getArgument(args, sc.FILENAME);
        extrType = ec.getArgument(args, sc.EXTRTYPE);
        userType = ec.getArgument(args,SadConstants.USERTYPE);          //ub01

        flagType = ec.getArgument(args, sc.FLAGTYPE);
        dataSet = ec.getArgument(args, sc.DATASET);

        password = ec.getArgument(args, sc.PASSWORD);

        surveyId = ec.getArgument(args, sc.SURVEYID);

        startDate = ec.getArgument(args, sc.STARTDATE);
        endDate = ec.getArgument(args, sc.ENDDATE);

        latNorth = ec.getArgument(args,sc.LATITUDENORTH);
        latSouth = ec.getArgument(args,sc.LATITUDESOUTH);
        longWest = ec.getArgument(args,sc.LONGITUDEWEST);
        longEast = ec.getArgument(args,sc.LONGITUDEEAST);

    } // getArgParms


    /**
     * get the station records from the database
     */
    MrnStation[] getStationRecords() {

        thisFunction = "getStationRecords";

        // contruct the where clause
        String where = "";
        if (!"".equals(surveyId)) {
            where = MrnStation.SURVEY_ID + "='" + surveyId + "'";
        } else {
            surveyId = "/";
            where =
                MrnStation.LATITUDE + " between " +
                    latitudeNorth + " and " + latitudeSouth + " and " +
                MrnStation.LONGITUDE + " between " +
                    longitudeWest + " and " + longitudeEast + " and " +
                MrnStation.DATE_START + " between " +
                    "to_timestamp('" + startDate + "','yyyy-mm-dd') and " +
                    "to_timestamp('" + endDate + "','yyyy-mm-dd')";
        } // if (!"".equals(surveyId))
        if ("1".equals(flagType)) {
            where += " and " + MrnStation.PASSKEY + " is NULL";
        } else if ("2".equals(flagType)) {
            where += " and " + MrnStation.PASSKEY + "='" + passKey + "'";
        } else {
            where += " and (" + MrnStation.PASSKEY + " is NULL";
            where += " or " + MrnStation.PASSKEY + "='" + passKey + "')";
        } // if ("1".equals(flagType))

        String fields =
            MrnStation.STATION_ID + "," + MrnStation.SURVEY_ID + "," +
            MrnStation.STNNAM + "," + MrnStation.LATITUDE + "," +
            MrnStation.LONGITUDE + "," + MrnStation.DATE_START + "," +
            MrnStation.STNDEP + "," + MrnStation.MAX_SPLDEP + "," +
            MrnStation.PASSKEY;
        String order = MrnStation.SURVEY_ID + "," + MrnStation.DATE_START;
        MrnStation[] stations = new MrnStation().get(fields, where, order);
        printDebug(MrnStation.TABLE + ": where = " + where);
        printDebug("stations.length = " + stations.length);
        return stations;
    } // getStationRecords


    /**
     * get the data records from the database
     */
    int getDataRecords(MrnStation[] stations) {
        noRecords = 0;
        String fields = "", where = "", order = "";
        String surveyId2 = "";

        for (int i = 0; i < stations.length; i++) {
            //printDebug("i = " + i);

            fields =
                MrnSedphy.CODE + "," + MrnSedphy.SPLDATTIM + "," +
                MrnSedphy.SPLDEP; // + "," + MrnSedphy.SUBDES + "," +

            where =
                MrnSedphy.STATION_ID + "='" + stations[i].getStationId() + "'";
            MrnSedphy[] sedphy = new MrnSedphy().get(fields, where, order);
            printDebug("i = " + i + ", where = " + where +
                ", sedphy.length = " + sedphy.length);

            for (int j = 0; j < sedphy.length; j++) {
                //printDebug("j = " + j);
                int code = sedphy[j].getCode();

                // get the sediment pollution 1 data
                String fields2 = "*";
                where = MrnSedchem1.SEDPHY_CODE + "=" + code;
                MrnSedchem1[] sedchem1 = new MrnSedchem1().get(fields2, where, "");
                //printDebug("sedchem1.length = " + sedchem1.length);

                // get the sediment pollution 2 data
                where = MrnSedchem2.SEDPHY_CODE + "=" + code;
                MrnSedchem2[] sedchem2 = new MrnSedchem2().get(fields2, where, "");
                //printDebug("sedchem2.length = " + sedchem2.length);

                thisFunction = "getDataRecords";
                printDebug("j = " + j + ", where = " + where +
                    ", sedchem1.length = " + sedchem1.length +
                    ", sedchem2.length = " + sedchem2.length);

                if ((sedchem1.length > 0) || (sedchem2.length > 0)) {
                    printRecords(stations[i], sedphy[j], sedchem1, sedchem2);
                    noRecords += 1;
                } // if ((sedchem1.length > 0) !! (sedchem1.length > 0))
            } // for (int j = 0; j < sedphys.length; j++)

        } // for int i
        System.gc();

        return noRecords;
    } // getDataRecords


    /**
     * Print the current marine data records
     * @param   station   MrnStation data
     * @param   sedphy    MrnSedphy data
     * @param   sedchem1   MrnSedchem1 data
     * @param   sedchem2   MrnSedchem2 data
     */
    void printRecords(MrnStation station, MrnSedphy sedphy, MrnSedchem1[] sedchem1,
            MrnSedchem2[] sedchem2) {

        thisFunction = "printRecords";

        //ec.writeFileLine(ofile, "station: " + station.toString());
        //ec.writeFileLine(ofile, "sedphy:  " + sedphy.toString());

        float flu  = MrnSedchem1.FLOATNULL;
        float kjn  = MrnSedchem1.FLOATNULL;
        float oxa  = MrnSedchem1.FLOATNULL;
        float toc  = MrnSedchem1.FLOATNULL;
        float ptot = MrnSedchem1.FLOATNULL;
        if (sedchem1.length > 0) {
            flu = sedchem1[0].getFluoride();
            kjn = sedchem1[0].getKjn();
            oxa = sedchem1[0].getOxa();
            toc = sedchem1[0].getToc();
            ptot = sedchem1[0].getPtot();
            //ec.writeFileLine(ofile, "sedchem1:  " + sedchem1[0]);
        } // if (sedchem1.length > 0)

        float ca  = MrnSedchem2.FLOATNULL;
        float mg  = MrnSedchem2.FLOATNULL;
        float po  = MrnSedchem2.FLOATNULL;
        float so  = MrnSedchem2.FLOATNULL;
        float st  = MrnSedchem2.FLOATNULL;
        float so3 = MrnSedchem2.FLOATNULL;
        if (sedchem2.length > 0) {
            ca  = sedchem2[0].getCalcium();
            mg  = sedchem2[0].getMagnesium();
            po  = sedchem2[0].getPotassium();
            so  = sedchem2[0].getSodium();
            st  = sedchem2[0].getStrontium();
            so3 = sedchem2[0].getSo3();
            //ec.writeFileLine(ofile, "sedchem2:  " + sedchem2[0]);
        } // if (sedchem1.length > 0)

        // get the time in string format
        Timestamp dateT = sedphy.getSpldattim();
        String    year = dateT.toString().substring(0,4);
        String    month = dateT.toString().substring(5,7);
        String    day = dateT.toString().substring(8,10);
        String    ftime = dateT.toString().substring(10,16);

        // get actual min and max ranges
        maxLatitude = ec.max(maxLatitude, station.getLatitude());
        minLatitude = ec.min(minLatitude, station.getLatitude());
        maxLongitude = ec.max(maxLongitude, station.getLongitude());
        minLongitude = ec.min(minLongitude, station.getLongitude());
        maxDate = ec.max(maxDate, dateT);
        minDate = ec.min(minDate, dateT);

        printDebug("maxLatitude = " + maxLatitude +
            ", minLatitude = " + minLatitude +
            ", maxLongitude = " + maxLongitude +
            ", minLongitude = " + minLongitude +
            ", maxDate = " + maxDate +
            ", minDate = " + minDate);
        //if (dbg) {
        //    String tLine = dateT.toString() + " " + year + " " + month + " " +
        //        day + " " + ftime;
        //    System.out.println("" + thisClass +  ".printRecords: " +
        //    tLine);
        //} // if (dbg)

        printDebug("latitude = " + station.getLatitude());
        printDebug("longitude = " + station.getLongitude());
        printDebug("date = " + year + ", " + month + ", " + day);
        printDebug("spldep = " + ec.nullToNines(sedphy.getSpldep(),9999.0f));
        printDebug("ftime = " + ftime);
        printDebug("station id = " + station.getStationId());
        printDebug("stnnam = " + station.getStnnam());
        printDebug("stndep = " + ec.nullToNines(station.getStndep(),9999.0f));
        printDebug("maxspldep = " + station.getMaxSpldep());

        float etopo2Depth = sc.getEtopo2(station);                      //ub06

        // Print the BODY OF THE FILE
        String line =
            ec.frm(station.getLatitude(),8,3) + "," +
            ec.frm(station.getLongitude(),8,3) + ", "  +
                year + ", " + month + ", " +
            ec.frm(ec.nullToNines(sedphy.getSpldep(),9999.0f),8,2) +
                ", " + day + ", \"" + ftime.trim() + "\"," +

            ec.frm(ec.nullToNines(flu ,9999.0f), 9,3) + "," +
            ec.frm(ec.nullToNines(kjn ,9999.0f), 9,3) + "," +
            ec.frm(ec.nullToNines(oxa ,9999.0f), 9,3) + "," +
            ec.frm(ec.nullToNines(toc ,9999.0f), 9,3) + "," +
            ec.frm(ec.nullToNines(ptot,9999.0f), 9,3) + "," +

            ec.frm(ec.nullToNines(ca ,9999.0f),10,3) + "," +
            ec.frm(ec.nullToNines(mg ,9999.0f), 9,3) + "," +
            ec.frm(ec.nullToNines(po ,9999.0f), 9,3) + "," +
            ec.frm(ec.nullToNines(so ,9999.0f), 9,3) + "," +
            ec.frm(ec.nullToNines(st ,9999.0f), 9,3) + "," +
            ec.frm(ec.nullToNines(so3,9999.0f), 9,3) + ",\"" +

            ec.frm(station.getStationId(),12) + "\",\"" +
            ec.frm(station.getStnnam(""),10) + "\"," +
            ec.frm(ec.nullToNines(station.getStndep(),9999.0f),10,2) + "," +
            ec.frm(ec.nullToNines(etopo2Depth,9999.0f),10,2) + "," +    //ub06
            ec.frm(ec.nullToNines(station.getMaxSpldep(),9999.0f),10,2) + ",\"" +
            ec.frm(station.getSurveyId(),9) + "\",";

        //printDebug(line);

        ec.writeFileLine(ofile,line);

    } // printRecords


    /**
     * Print the FIRST LINE on the form
     */
    void printHeader() {

        thisFunction = "printHeader";

        if (noRecords == 0) {
            minLatitude = latitudeNorth;
            maxLatitude = latitudeSouth;
            minLongitude = longitudeWest;
            maxLongitude = longitudeEast;
            minDate = dateStart;
            maxDate = dateEnd;
        } // if (noRecords == 0)

        printDebug("minLatitude = " + minLatitude);
        printDebug("maxLatitude = " + maxLatitude);
        printDebug("minLongitude = " + minLongitude);
        printDebug("maxLongitude = " + maxLongitude);
        printDebug("minDate = " + minDate);
        printDebug("maxDate = " + maxDate);

        String line =
            "MARINE  " +
            ec.frm(minLatitude,6,1) +
            ec.frm(maxLatitude,6,1) +
            ec.frm(minLongitude,6,1) +
            ec.frm(maxLongitude,6,1) + "  " +
            minDate.toString().substring(0,10) + "  " +
            maxDate.toString().substring(0,10) + " SEDCHEM " +
            ec.frm(noRecords,12) + " " + surveyId;

        printDebug("" + thisClass + ".printHeader: " + line);

        // Print HEADER
        try {
            ofile.seek(headerPos);
            ofile.writeBytes(line);
        } catch (Exception e) {
            System.out.println("" + thisClass +
                ".printHeader: Write Error: " + e.getMessage());
            e.printStackTrace();
        }  // try-catch
    } // printHeader


    /**
     * print the column headings
     */
    void ColumnHeadings() {
        String head = "  LATDEG,  LONDEG, YEAR,MNTH,   DEPTH,DAY,    TIME, " +
            "       F,      KJN,      OXA,      TOC,     PTOT,        CA," +
            "       MG,        K,       NA,       SR,      S03," +
            "STATION ID    ,STNNAM      ,    STNDEP,ETOPO2 DEP,  MXSPLDEP,"+ //ub06
            "SURVEYID";
        ec.writeFileLine(ofile,head);
    } // ColumnHeadings


    /**
     * print the legend at the bottom of the file
     */
    void printLegend() {

        String legend = "\n\n" +
            "  LATDEG    =   LATITUDE (DEGREES) \n"+
            "  LONDEG    =   LONGITUDE (DEGREES) \n"+
            "  YEAR      =   YEAR IN WHICH RECORDING WAS MADE\n"+
            "  MONTH     =   MONTH IN WHICH RECORDING WAS MADE\n"+
            "  DEPTH     =   MEASUREMENT DEPTH (METRES)\n"+
            "  DAY       =   DAY IN WHICH RECORDING WAS MADE \n"+
            "  TIME      =   TIME OF RECORDING \n"+

            "  F         =   FLUORIDE (MICROGRAM/GRAM) (NO VALUE = 9999.0)\n" +
            "  KJN       =   KJELDAL NITROGEN (MICROGRAM/GRAM) (NO VALUE = 9999.0)\n" +
            "  OXA       =   OXYGEN ABSORBED (MILLIGRAM/GRAM) (NO VALUE = 9999.0)\n" +
            "  TOC       =   TOTAL ORGANIC CARBON (MILLIGRAM/GRAM) (NO VALUE = 9999.0)\n" +
            "  PTOT      =   TOTAL PHOSPHORUS (MICROGRAM ATOM/L) (NO VALUE = 9999.0)\n" +

            "  CA        =   CALCIUM (MICROGRAM/GRAM) (NO VALUE = 9999.0)\n" +
            "  MG        =   MAGNESIUM (MICROGRAM/GRAM) (DRY MASS) (NO VALUE = 9999.0)\n" +
            "  K         =   POTASSIUM (MICROGRAM/GRAM) (DRY MASS) (NO VALUE = 9999.0)\n" +
            "  NA        =   SODIUM (MICROGRAM/GRAM) (DRY MASS) (NO VALUE = 9999.0)\n" +
            "  SR        =   STRONTIUM (MICROGRAM/GRAM) (DRY MASS) (NO VALUE = 9999.0)\n" +
            "  SO3       =   SULPHIDE (MICROGRAM/GRAM) (DRY MASS) (NO VALUE = 9999.0)\n" +

            "  STATION ID=   UNIQUE STATION IDENTIFIER\n" +
            "  STNNAM    =   STATION NAME\n" +
            "  STNDEP    =   STATION DEPTH\n" +
            "  ETOPO2 DEP=   ETOPO2 DEPTH\n" +                          //ub06
            "  MXSPLDEP  =   MAXIMUM SAMPLING DEPTH\n" +
            "  SURVEYID  =   UNIQUE SADCO SURVEY IDENTIFIER";

         ec.writeFileLine(ofile,legend);
    } // printLegend


    /**
     * print an error message if inputs not OK
     */
    void printErrorMessage() {
    } // printErrorMessage


    /**
     * print an error message if inputs not OK
     */
    void printDebug(String text) {
        if (dbg) {
            if (dbg2File) {
                ec.writeFileLine(dbgFile, thisFunction + ": " + text);
            } else {
                System.out.println(thisFunction + ": " + text);
            } // if (dbg2File)
        } // if (dbg)
    } // printDebug


    /**                                                                 //ub04
     * Create the LOG_USERS table entry for this extraction.            //ub04
     */                                                                 //ub04
    LogUsers createLogUsers(String userid, Timestamp start, String timeDiff) {//ub04

        LogUsers logs = new LogUsers();                                 //ub04
        logs.setUserid(userid);                                         //ub04
        logs.setDateTime(start);                                        //ub04
        logs.setDatabase("MARINE");                                     //ub04
        logs.setDiscipline("sedchem");                                  //ub04
        logs.setProduct("Extraction");                                  //ub04
        logs.setFileName(fileName);                                     //ub04
        logs.setQueueDuration(timeDiff);                                //ub04
        logs.setSurveyId(surveyId);                                     //ub04
        boolean success = logs.put();                                   //ub04
                                                                        //ub04
        // commit this log - in case the job bombs out                  //ub04
        common2.DbAccessC.commit();                                     //ub04
                                                                        //ub04
        return logs;                                                    //ub04
                                                                        //ub04
    } // createLogUsers                                                 //ub04


    /**
     * Update the LOG_USERS table for this extraction.
     */
/*
    void updateLogUsers(String userid, String timeDiff) {
        LogUsers logs = new LogUsers();
        logs.setUserid(userid);
        logs.setDateTime(new Timestamp(new java.util.Date().getTime()));
        logs.setDatabase("MARINE");
        logs.setDiscipline("sedchem");
        logs.setProduct("Extraction");
        logs.setFileName(fileName);
        logs.setLatitudeNorth(minLatitude);
        logs.setLatitudeSouth(maxLatitude);
        logs.setLongitudeWest(minLongitude);
        logs.setLongitudeEast(maxLongitude);
        logs.setDateStart(minDate);
        logs.setDateEnd(maxDate);
        logs.setSurveyId(surveyId);
        logs.setNumRecords(noRecords);
        logs.setJobDuration(timeDiff);
        boolean success = logs.put();
    } // updateLogUsers
*/

    /**                                                                 //ub04
     * Update the LOG_USERS table for this extraction.                  //ub04
     */                                                                 //ub04
    void updateLogUsers(LogUsers logs, String timeDiff) {               //ub04
        // define the value that should be updated                      //ub04
        LogUsers updateLogs = new LogUsers();                           //ub04
        updateLogs.setLatitudeNorth(minLatitude);                       //ub04
        updateLogs.setLatitudeSouth(maxLatitude);                       //ub04
        updateLogs.setLongitudeWest(minLongitude);                      //ub04
        updateLogs.setLongitudeEast(maxLongitude);                      //ub04
        updateLogs.setDateStart(minDate);                               //ub04
        updateLogs.setDateEnd(maxDate);                                 //ub04
        updateLogs.setNumRecords(noRecords);                            //ub04
        updateLogs.setJobDuration(timeDiff);                            //ub04
                                                                        //ub04
        // define the 'where' clause                                    //ub04
        LogUsers whereLogs = new LogUsers();                            //ub04
        whereLogs.setDateTime(logs.getUserid());                        //ub04
        whereLogs.setDateTime(logs.getDateTime());                      //ub04
                                                                        //ub04
        // do the update                                                //ub04
        whereLogs.upd(updateLogs);                                      //ub04
    } // updateLogUsers                                                 //ub04


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            ExtractMRNSedChmData local =
                new ExtractMRNSedChmData(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractMRNSedChmData", "Constructor", "");
        } // try-catch
        // close the connection to the database
        common2.DbAccessC.close();

    } // main


 }// class ExtractMRNSedChmData
