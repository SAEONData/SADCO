package sadco;

import common2.Queue;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Does the actual extraction of the requested data.
 *
 * SadcoMRN
 * screen.equals("extract")
 * version.equals("extract")
 * extrType.equals("physnut")
 *
 * @author 011001 - SIT Group
 * @version
 * 011001 - Ursula von St Ange - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020710 - Ursula von St Ange - fetch watphy records in batches of depths  <br>
 * 091214 - Ursula von St Ange - change for inventory user (ub01)           <br>
 * 100105 - Ursula von St Ange - change for queuing of job (ub02)           <br>
 * 100615 - Ursula von St Ange - non-inventory users don't queue (ub03)     <br>
 * 100624 - Ursula von St Ange - change logging of job to beginning with
 *                               update at enc (ub04)                       <br>
 * 110613 - Ursula von St Ange - add station count to header line (ub05)    <br>
 * 121113 - Ursula von St Ange - add etopo2 depth (ub06)                    <br>
 * 150204 - Ursula von St Ange - add QC flags and descriptions (ub07)       <br>
 */
public class ExtractMRNPhysNutData { //extends CompoundItem {

    boolean dbg = false;
    //boolean dbg = true;
    boolean test = false;
    //boolean test = true;
    //boolean dbgQ = false;
    boolean dbgQ = true;  // set false when HOST is known.

    String thisClass = this.getClass().getName();

    /** some common functions */
    common.edmCommonPC ec = new common.edmCommonPC();
    /** url parameters names & applications */
    SadConstants sc = new SadConstants();

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
    int             noStations = 0;                                     //ub05
    long            headerPos;

    // minimum and maximum's
    float           maxLatitude  = -20.0f;
    float           minLatitude  =  99.0f;
    float           maxLongitude = -20.0f;
    float           minLongitude =  99.0f;
    Timestamp       minDate  = Timestamp.valueOf("2100-01-01 00:00:00.0");
    Timestamp       maxDate  = Timestamp.valueOf("1900-01-01 00:00:00.0");

    // constant for extracting watphy records
    int             spldepInterval = 500;

    /**
     * Main constructor ExtractMRNPhysNutData
     * @param   args   String array of arguments as from form or URL
     */
    public ExtractMRNPhysNutData(String args[])  {
        if (test) System.out.println(thisClass + ": start = " + new java.util.Date());

        // Get the arguments
        getArgParms(args);
        if (test) System.out.println(thisClass + ": surveyId = " + surveyId);


        // extract the user record from db
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        String userid = sessions[0].getUserid();
        if (dbg) System.out.println("<br>userid = " + userid);

        // get the correct PATH NAME - used while debugging
        String rootPath = "";
        if (dbg) System.err.println("<br>" + thisClass + ": host = " +
            ec.getHost());
        //if (ec.getHost().equals("morph")) {
        //if (ec.getHost().equals("fred")) {
        //    rootPath = sc.MORPH + userid + "/";
        //} else {
        //    rootPath = sc.LOCAL + "marine/";
        //} // if host

        if (dbg) System.out.println("ec.getHost() = " + ec.getHost());
        if (dbg) System.out.println("sc.HOST = " + sc.HOST);
        //if (dbg) System.out.println("sc.HOST_ST = " + sc.HOST_ST);

        //if (ec.getHost().equals(sc.HOST) || ec.getHost().equals(sc.HOST_ST)) {
        if (ec.getHost().startsWith(sc.HOST)) {                             //ub07
            if ("0".equals(userType)) {  // from inventory              //ub01
                rootPath = sc.HOSTDIR + "inv_user/";                    //ub01
            } else {                                                    //ub01
                rootPath = sc.HOSTDIR + userid + "/";                   //ub01
            } // if ("0".equals(userType))                              //ub01
            dbgQ = false;                                               //ub07
        } else {
            rootPath = sc.LOCALDIR + "marine/";
        } // if host

        // new filename, but keep the original file name                //ub02
        fileNameIn = fileName;                                          //ub02
        if (!reqNumber.equals("")) {
            fileName = reqNumber + "_" + fileName;
        } // if
        fileName += ".dbm";
        if (dbg) System.out.println("<br>" + thisClass + ": " + fileName);
        //this.addItem("The current fileName = " + fileName);

        // delete an old dummy file for Progress purposes ......
        String dummyFile = rootPath + fileName + ".finished";
        if (dbg) System.out.println("<br>" + rootPath + fileName);
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
        columnHeadings();

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
        } // if ("".equals(surveyId))

        // check if flagType combination is valid.  If not, revert back to
        // unflagged data extraction only
        if (dbg) System.out.println("<br>flagType = " + flagType +
            ", dataSet = " + dataSet + ", password = " + password);
        if (!"".equals(flagType)) {
            // was there a dataSet or a Password
            if ("".equals(dataSet) || "".equals(password)) {
                if (dbg) System.out.println("<br>password test 1:true (dataset or password blank) ");
                flagType = "1";
            } else {
                if (dbg) System.out.println("<br>password test 1:false (dataset or password blank)");
                // is this user registered for the dataset
                SadUserDset[] userDset = new SadUserDset(userid, dataSet).get();
                if (dbg) System.out.println("<br>userDset.length = " +
                    userDset.length);
                if (userDset.length == 0) {
                    if (dbg) System.out.println("<br>password test 2:true (userDset.length == 0)");
                    flagType = "1";
                } else {
                    if (dbg) System.out.println("<br>password test 2:false (userDset.length == 0)");
                    // did the user give the right password?
                    SadUsers[] users = new SadUsers().get(
                        SadUsers.FLAG_PASSWORD,
                        SadUsers.USERID + "='" + userid + "'", "");
                    if (dbg) System.out.println("<br>password = " + password +
                        ", users[0].getFlagPassword() = " +
                        users[0].getFlagPassword());
                    if (dbg) System.out.println("<br>users[0] = " + users[0]);
                    if (!users[0].getFlagPassword().equals(password)) {
                        if (dbg) System.out.println("<br>password test 3:true " +
                            users[0].getFlagPassword() + " " + password);
                        flagType = "1";
                    } else {
                        if (dbg) System.out.println("<br>password test 3:false " +
                            users[0].getFlagPassword() + " " + password);
                        // get the dataset password
                        SadUsers[] dataset = new SadUsers().get(
                            SadUsers.PASSWORD,
                            SadUsers.USERID + "='" + dataSet + "'", "");
                        if (dataset.length == 0) {
                            if (dbg) System.out.println("<br>password test 4:true " +
                                " (dataset.length == 0)");
                            flagType = "1";
                        } else {
                            if (dbg) System.out.println("<br>password test 4:false " +
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

        if (dbg) System.out.println("<br>flagType = " + flagType);
        if (dbg) System.out.println("<br>validQuery = " + validQuery);
        //
        if (validQuery) {
            MrnStation[] stations = getStationRecords();
            noStations = stations.length;                               //ub05
            System.gc();
            getDataRecords(stations);
        } // if (validQuery)

        printLegend();
        printHeader();

        // clean up
        ec.closeFile(ofile);

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

        if (test) System.out.println(thisClass + ": end = " + new java.util.Date());

    } // ExtractMRNPhysNutData(String args[])


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        if (dbg) for (int i = 0; i < args.length; i++) {
            System.out.println("<br>" + args[i]);
        } // for i

        // normal stuff
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
        if (dbg) System.err.println("\n\n<br>" + MrnStation.TABLE +
            ": where = " + where + ", \nstations.length = " + stations.length);
        return stations;
    } // getStationRecords


    /**
     * get the data records from the database
     */
    int getDataRecords(MrnStation[] stations) {
        noRecords = 0;
        String fields = "", where = "", order = "";
        String surveyId2 = "";
        //MrnSurvey[] survey = new MrnSurvey[1];
        MrnPlanam[] planam = new MrnPlanam[1];
        for (int i = 0; i < stations.length; i++) {
            //if (dbg) System.out.println("<br>i = " + i);

            // get new planam for new survey
            if (!surveyId2.equals(stations[i].getSurveyId())) {
                surveyId2 = stations[i].getSurveyId();
                fields = MrnInventory.PLANAM_CODE;
                where = MrnInventory.SURVEY_ID + "='" + surveyId2 + "'";
                MrnInventory[] inv = new MrnInventory().get(fields, where, "");
                fields = MrnPlanam.NAME;
                where = MrnPlanam.CODE + "=" + inv[0].getPlanamCode();
                planam = new MrnPlanam().get(fields, where, "");
                if (dbg) System.out.println("<br>\nplanam.length = " + planam.length);
            } // if (!surveyId2.equals(stations[i].getSurveyId()))

            // get watphy records
            // first check what the max depth for the station is.
            fields = "max(" + MrnWatphy.SPLDEP + ") as " +
                MrnWatphy.SPLDEP;
            where =
                MrnWatphy.STATION_ID + "='" + stations[i].getStationId() + "'";
            order = MrnWatphy.SPLDEP;
            MrnWatphy[] watphy2 = new MrnWatphy().get(fields, where, order);
            float maxSpldep = 0;
            if (watphy2.length > 0) {
                //if (dbg) System.out.println("<br>watphy = " + watphy[0]);
                maxSpldep = watphy2[0].getSpldep();
            }
            if (dbg) System.out.println("<br>watphy2.length = " + watphy2.length +
                ", maxSpldep = " + maxSpldep);

            fields =
                MrnWatphy.CODE + "," + MrnWatphy.SPLDATTIM + "," +
                MrnWatphy.SPLDEP + "," + MrnWatphy.SUBDES + "," +
                MrnWatphy.DISOXYGEN + "," + MrnWatphy.SALINITY + "," +
                MrnWatphy.TEMPERATURE + "," + MrnWatphy.SOUNDV;

            // do a loop over the station depth - in intervals of 500 m
            for (int k = 0; k <= maxSpldep; k += spldepInterval) {

                where =
                    MrnWatphy.STATION_ID + "='" + stations[i].getStationId() + "' and " +
                    MrnWatphy.SPLDEP + " between " + k + " and " + (k+spldepInterval-0.01);
                MrnWatphy[] watphy = new MrnWatphy().get(fields, where, order);
                if (dbg) System.err.println("<br>i = " + i +
                    ", where = " + where +
                    ", watphy.length = " + watphy.length);

                for (int j = 0; j < watphy.length; j++) {
                    //if (dbg) System.out.println("<br>j = " + j);
                    int code = watphy[j].getCode();

                    // get the QC records
                    String fields2 = "*";                                       //ub07
                    where = MrnWatqc.WATPHY_CODE + "=" + code;                  //ub07
                    MrnWatqc[] watqc = new MrnWatqc().get(fields2, where, "");  //ub07

                    // get the water nutrients data
                    fields2 = MrnWatnut.NO2 + "," + MrnWatnut.NO3 + "," +
                        MrnWatnut.PO4 + "," + MrnWatnut.P + "," +
                        MrnWatnut.SIO3;
                    where = MrnWatnut.WATPHY_CODE + "=" + code;
                    MrnWatnut[] watnut = new MrnWatnut().get(fields2, where, "");
                    //if (dbg) System.out.println("<br>watnut.length = " + watnut.length);

                    // get the water chemical data
                    fields2 = MrnWatchem1.PH;
                    where = MrnWatchem1.WATPHY_CODE + "=" + code;
                    MrnWatchem1[] watchem1 = new MrnWatchem1().get(fields2, where, "");
                    //if (dbg) System.out.println("<br>watchem1.length = " + watchem1.length);

                    // get the water chlorofil data
                    fields2 = MrnWatchl.CHLA;
                    where = MrnWatchl.WATPHY_CODE + "=" + code;
                    MrnWatchl[] watchl = new MrnWatchl().get(fields2, where, "");
                    if (dbg) System.out.println("<br>j = " + j +
                        ", watnut.length = " + watnut.length +
                        ", watchem1.length = " + watchem1.length +
                        ", watchl.length = " + watchl.length);

                    printRecords(stations[i], watphy[j], watqc, planam, watnut, //ub07
                        watchem1, watchl);
                    noRecords += 1;
                } // for (int j = 0; j < watphys.length; j++)

            } // for int k

        } // for int i
        System.gc();

        return noRecords;
    } // getDataRecords


    /**
     * Print the current marine data records
     * @param   station   MrnStation data
     * @param   watphy    MrnWatphy data
     * @param   planam    MrnPlanam data
     * @param   watnut    MrnWatnut data
     * @param   watchem1  MrnWatchem1 data
     * @param   watchl    MrnWatchl data
     */
    void printRecords(MrnStation station, MrnWatphy watphy, MrnWatqc[] watqc,  //ub07
            MrnPlanam[] planam,                                                //ub07
            MrnWatnut[] watnut, MrnWatchem1[] watchem1, MrnWatchl[] watchl) {

        // get the variables for records that may be null
        String planamName = "";
        if (planam.length > 0) {
            planamName = planam[0].getName("");
            //ec.writeFileLine(ofile, "planam:  " + planam[0].toString());
        } // if (planam.length > 0)
        if (dbg) System.out.println("<br>planamName = " + planamName);

        //ec.writeFileLine(ofile, "station: " + station.toString());
        //ec.writeFileLine(ofile, "watphy:  " + watphy.toString());

        int depthQC = -1;                                               //ub07
        int disoxQC = -1;                                               //ub07
        int salinQC = -1;                                               //ub07
        int tmpQC   = -1;                                               //ub07
        int no3QC   = -1;                                               //ub07
        int po4QC   = -1;                                               //ub07
        int sio3QC  = -1;                                               //ub07
        int chlaQC  = -1;                                               //ub07
        int phQC    = -1;                                               //ub07
        if (watqc.length > 0) {                                         //ub07
            depthQC = watqc[0].getSpldep();                             //ub07
            disoxQC = watqc[0].getDisoxygen();                          //ub07
            salinQC = watqc[0].getSalinity();                           //ub07
            tmpQC   = watqc[0].getTemperature();                        //ub07
            no3QC   = watqc[0].getNo3();                                //ub07
            po4QC   = watqc[0].getPo4();                                //ub07
            sio3QC  = watqc[0].getSio3();                               //ub07
            chlaQC  = watqc[0].getChla();                               //ub07
            phQC    = watqc[0].getPh();                                 //ub07
        } // if (watqc.length > 0)                                      //ub07

        float no2  = MrnWatnut.FLOATNULL;
        float no3  = MrnWatnut.FLOATNULL;
        float p    = MrnWatnut.FLOATNULL;
        float po4  = MrnWatnut.FLOATNULL;
        float sio3 = MrnWatnut.FLOATNULL;
        if (watnut.length > 0) {
            no2  = watnut[0].getNo2();
            no3  = watnut[0].getNo3();
            po4  = watnut[0].getPo4();
            p    = watnut[0].getP();
            sio3 = watnut[0].getSio3();
            //ec.writeFileLine(ofile, "watnut:  " + watnut[0].toString());
        } // if (watnut.length > 0)

        float ph = MrnWatchem1.FLOATNULL;
        if (watchem1.length > 0) {
            ph = watchem1[0].getPh();
            //ec.writeFileLine(ofile, "watchem1:" + watchem1[0].toString());
        } // if (watchem1.length > 0)

        float chla = MrnWatchl.FLOATNULL;
        if (watchl.length > 0) {
            chla = watchl[0].getChla();
            //ec.writeFileLine(ofile, "watchl:  " + watchl[0].toString());
        } // if (watchl.length > 0)

        // get the time in string format
        Timestamp dateT = watphy.getSpldattim();
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

        if (dbg) System.out.println("<br>maxLatitude = " + maxLatitude +
            ", minLatitude = " + minLatitude +
            ", maxLongitude = " + maxLongitude +
            ", minLongitude = " + minLongitude +
            ", maxDate = " + maxDate +
            ", minDate = " + minDate);
        //if (dbg) {
        //    String tLine = dateT.toString() + " " + year + " " + month + " " +
        //        day + " " + ftime;
        //    System.out.println("<br>" + thisClass +  ".printRecords: " +
        //    tLine);
        //} // if (dbg)

        float etopo2Depth = sc.getEtopo2(station);                      //ub06

        // Print the BODY OF THE FILE
        String line =
            ec.frm(station.getLatitude(),8,3) + "," +
            ec.frm(station.getLongitude(),8,3) + ", "  +
                year + ", " + month + ", " +
            ec.frm(ec.nullToNines(watphy.getSpldep(),9999.0f),8,2) +
                ", " + day + ", \"" + ftime.trim() + "\"," +
            ec.frm(ec.nullToNines(watphy.getTemperature(),9999.0f),8,2) + "," +
            ec.frm(ec.nullToNines(watphy.getSalinity(),9999.0f),9,3) + "," +
            ec.frm(ec.nullToNines(watphy.getDisoxygen(),9999.0f),8,2) + "," +
            ec.frm(ec.nullToNines(no2,9999.0f),9,2) + "," +
            ec.frm(ec.nullToNines(no3,9999.0f),9,2) + "," +
            ec.frm(ec.nullToNines(po4,9999.0f),9,3) + "," +
            ec.frm(ec.nullToNines(p,9999.0f),10,3) + "," +
            ec.frm(ec.nullToNines(sio3,9999.0f),10,2) + "," +
            ec.frm(ec.nullToNines(ph,9999.0f),8,2) + "," +
            ec.frm(ec.nullToNines(watphy.getSoundv(),9999.0f),9,1) + "," +
            ec.frm(ec.nullToNines(chla,9999.0f),10,3) + ",\"" +
            ec.frm(station.getStationId(),12) + "\"," +
            ec.frm(ec.nullToNines(station.getStndep(),9999.0f),10,2) + "," +
            ec.frm(ec.nullToNines(etopo2Depth,9999.0f),10,2) + "," +    //ub06
            ec.frm(ec.nullToNines(station.getMaxSpldep(),9999.0f),10,2) + ",\"" +
            ec.frm(station.getStnnam(""),10) + "\",\"" +
            ec.frm(station.getSurveyId(),9) + "\",\"" +
            ec.frm(planamName,20) + "\",\"" +
            ec.frm(watphy.getSubdes(""),5) + "\"," +
            ec.frm(depthQC,8) + "," +                                   //ub07
            ec.frm(disoxQC,8) + "," +                                   //ub07
            ec.frm(salinQC,8) + "," +                                   //ub07
            ec.frm(tmpQC,6) + "," +                                     //ub07
            ec.frm(no3QC,6) + "," +                                     //ub07
            ec.frm(po4QC,6) + "," +                                     //ub07
            ec.frm(sio3QC,7) + "," +                                    //ub07
            ec.frm(chlaQC,7) + "," +                                    //ub07
            ec.frm(phQC,5) + ",";                                       //ub07
        //if (dbg) System.out.println("<br>" + thisClass + ".printRecords: " +
        //    line1);

        //if (dbg) System.out.println("<br>" + thisClass + ".printRecords: " +
        //    line);

        ec.writeFileLine(ofile,line);

    } // printRecords


    /**
     * Print the FIRST LINE on the form
     */
    void printHeader() {
        if (noRecords == 0) {
            minLatitude = latitudeNorth;
            maxLatitude = latitudeSouth;
            minLongitude = longitudeWest;
            maxLongitude = longitudeEast;
            minDate = dateStart;
            maxDate = dateEnd;
        } // if (noRecords == 0)

        if (dbg) System.err.println("<br>" + thisClass +
            ".printHeader: minLatitude = " + minLatitude);
        if (dbg) System.err.println("<br>" + thisClass +
            ".printHeader: maxLatitude = " + maxLatitude);
        if (dbg) System.err.println("<br>" + thisClass +
            ".printHeader: minLongitude = " + minLongitude);
        if (dbg) System.err.println("<br>" + thisClass +
            ".printHeader: maxLongitude = " + maxLongitude);
        if (dbg) System.err.println("<br>" + thisClass +
            ".printHeader: minDate = " + minDate);
        if (dbg) System.err.println("<br>" + thisClass +
            ".printHeader: maxDate = " + maxDate);

        String line =
            "MARINE  " +
            ec.frm(minLatitude,6,1) +
            ec.frm(maxLatitude,6,1) +
            ec.frm(minLongitude,6,1) +
            ec.frm(maxLongitude,6,1) + "  " +
            minDate.toString().substring(0,10) + "  " +
            maxDate.toString().substring(0,10) + " PHYSNUT" +
            ec.frm(noRecords,13) + " " + surveyId + ec.frm(noStations,10);  //ub05

        if (dbg) System.out.println("<br>" + thisClass + ".printHeader: " + line);

        // Print HEADER
        try {
            ofile.seek(headerPos);
            ofile.writeBytes(line);
        } catch (Exception e) {
            System.out.println("<br>" + thisClass +
                ".printHeader: Write Error: " + e.getMessage());
            e.printStackTrace();
        }  // try-catch
    } // printHeader


    /**
     * print the column headings
     */
    void columnHeadings() {
        // method to write headings of estraxted data
        //String head = " LATDEG ,  LONDEG, YEAR, MONTH, DEPTH, DAY,  TIME,    " +
        //    "TMP,  SALIN, DISOX,    NO2,    NO3,    PO4,    PTOT,    " +
        //    "SIO3,    PH, SOUNDV,    CHLA, STATION ID,     STNDEP,MXSPLDEP, " +
        //    "STN NAM,     SURVEYID,   PLANAM,                INSTR";

        //String head = " LATDEG ,  LONDEG, YEAR, MONTH, DEPTH,DAY,    TIME,   TMP,    "+
        //              "SALIN,   DISOX,      NO2,      NO3,      PO4,      PTOT,      "+
        //              "SIO3,      PH,   SOUNDV,      CHLA,    STATION ID,    STNDEP,"+
        //              "ETOPO2 DEP,  MXSPLDEP,     STN NAM,   SURVEYID,"+            //ub06
        //              "                PLANAM,INSTR";
        String head = " LATDEG,   LONDEG, YEAR, MONTH, DEPTH,DAY,    TIME,   TMP,    "+
                      "SALIN,   DISOX,      NO2,      NO3,      PO4,      PTOT,      "+
                      "SIO3,      PH,   SOUNDV,      CHLA,    STATION ID,     STNDEP, "+
                      "ETOPO2 DEP,  MXSPLDEP, STN NAM,     SURVEYID,  PLANAM,         "+
                      "       INSTR,  DEPTH_QC,DISOX_QC,SALIN_QC,TMP_QC,NO3_QC,PO4_QC,"+   //ub07
                      "SIO3_QC,CHLA_QC,PH_QC";                                             //ub07

        ec.writeFileLine(ofile,head);
    } // columnHeadings


    /**
     * print the legend at the bottom of the file
     */
    void printLegend() {

        String legend = "\n" +
            "LEGEND:\n" +                                               //ub07
            "  LATDEG    =   LATITUDE (DEGREES) \n"+
            "  LONDEG    =   LONGITUDE (DEGREES) \n"+
            "  YEAR      =   YEAR IN WHICH RECORDING WAS MADE\n"+
            "  MONTH     =   MONTH IN WHICH RECORDING WAS MADE\n"+
            "  DEPTH     =   MEASUREMENT DEPTH (METRES)\n"+
            "  DAY       =   DAY IN WHICH RECORDING WAS MADE \n"+
            "  TIME      =   TIME OF RECORDING \n"+
            "  TMP       =   TEMPERATURE (DEGREES CELSIUS)(NO VALUE = 99.0)\n" +
            "  SALIN     =   SALINITY (1/1000)(NO VALUE = 99.0)\n" +
            "  DISOX     =   DISOLVED OXYGEN (MLS/LITRE)(NO VALUE = 99.0)\n" +
            "  NO2       =   NITRITE (MICROGRAM ATOM/LITRE) (NO VALUE = 999.0)\n" +
            "  NO3       =   NITRATE (MICROGRAM ATOM/LITRE) (NO VALUE = 999.0)\n" +
            "  PO4       =   PHOSPHATE (MICROGRAM ATOM/LITRE) (NO VALUE = 999.0)\n" +
            "  PTOT      =   PHOSPORUS (MICROGRAM ATOM/LITRE) (NO VALUE = 999.0)\n" +
            "  SIO3      =   SILICATE (MICROGRAM ATOM/LITRE) (NO VALUE = 9999.0)\n" +
            "  PH        =   PH (NO VALUE = 99.0)\n" +
            "  SOUNDV    =   SOUND VELOCITY (METRE/SECOND) (NO VALUE = 999.0)\n" +
            "  CHLA      =   CHLOROPHYLL A (MICROGRAM/LITRE) (NO VALUE = 999.0)\n" +
            "  STATION ID=   UNIQUE STATION IDENTIFIER\n" +
            "  STNDEP    =   STATION DEPTH\n" +
            "  ETOPO2 DEP=   ETOPO2 DEPTH\n" +                          //ub06
            "  MXSPLDEP  =   MAXIMUM SAMPLING DEPTH\n" +
            "  STN NAM   =   STATION NAME AS GIVEN BY CONTRIBUTOR\n" +
            "  SURVEYID  =   UNIQUE SADCO SURVEY IDENTIFIER\n" +
            "  PLANAM    =   PLATFORM NAME\n" +
            "  INSTR     =   MEASURING INSTRUMENT\n" +
            "  DEPTH_QC  =   QUALITY CONTROL FLAG FOR MEASUREMENT DEPTH\n"+ //ub07
            "  DISOX_QC  =   QUALITY CONTROL FLAG FOR DISOLVED OXYGEN\n" +  //ub07
            "  SALIN_QC  =   QUALITY CONTROL FLAG FOR SALINITY\n" +         //ub07
            "  TMP_QC    =   QUALITY CONTROL FLAG FOR TEMPERATURE\n" +      //ub07
            "  NO3_QC    =   QUALITY CONTROL FLAG FOR NITRATE\n" +          //ub07
            "  PO4_QC    =   QUALITY CONTROL FLAG FOR PHOSPHATE\n" +        //ub07
            "  SIO3_QC   =   QUALITY CONTROL FLAG FOR SILICATE\n" +         //ub07
            "  CHLA_QC   =   QUALITY CONTROL FLAG FOR CHLOROPHYLL A\n" +    //ub07
            "  PH_QC     =   QUALITY CONTROL FLAG FOR PH";                  //ub07
         ec.writeFileLine(ofile,legend);

         String qcFlags = "\n" +
            "Description of Quality Control flags (no value = -1)\n" +      //ub07
            "  Flags on individual observations - depth flags\n" +          //ub07
            "    0 accepted value\n" +                                      //ub07
            "    1 duplicates or inversions in recorded depth\n" +          //ub07
            "    2 density inversion\n" +                                   //ub07
            "  Flags on individual observations - variable flags\n" +       //ub07
            "    0 accepted value\n" +                                      //ub07
            "    1 range outlier (outside of broad range check)\n" +        //ub07
            "    2 failed inversion check\n" +                              //ub07
            "    3 failed gradient check\n" +                               //ub07
            "    4 not used\n" +                                            //ub07
            "    5 failed combined gradient and inversion checks\n" +       //ub07
            "    6 failed range and inversion checks\n" +                   //ub07
            "    7 failed range and gradient checks\n" +                    //ub07
            "    8 not used\n" +                                            //ub07
            "    9 failed range and combined gradient and inversion checks";//ub07
         ec.writeFileLine(ofile,qcFlags);

    } // printLegend


    /**
     * print an error message if inputs not OK
     */
    void printErrorMessage() {
    } // printErrorMessage


    /**                                                                 //ub04
     * Create the LOG_USERS table entry for this extraction.            //ub04
     */                                                                 //ub04
    LogUsers createLogUsers(String userid, Timestamp start, String timeDiff) {//ub04
        LogUsers logs = new LogUsers();                                 //ub04
        logs.setUserid(userid);                                         //ub04
        logs.setDateTime(start);                                        //ub04
        logs.setDatabase("MARINE");                                     //ub04
        logs.setDiscipline("physnut");                                  //ub04
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
        logs.setDiscipline("physnut");
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
                                                                        //ub04
    } // updateLogUsers                                                 //ub04


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            ExtractMRNPhysNutData local =
                new ExtractMRNPhysNutData(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractMRNPhysNutData", "Constructor", "");
        } // try-catch
        // close the connection to the database
        common2.DbAccessC.close();

    } // main


 }// class ExtractMRNPhysNutData
