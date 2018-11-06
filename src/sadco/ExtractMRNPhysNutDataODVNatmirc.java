package sadco;

import common2.Queue;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Does the actual extraction of the requested data in ODV format.
 * specially for Anja to include etopo2 as bottom depth
 *
 * SadcoMRN
 * screen.equals("extract")
 * version.equals("extract")
 * extrType.equals("physnutodv")
 *
 * @author 020618 - SIT Group
 * @version
 * 020618 - Mario August       - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020710 - Ursula von St Ange - fetch watphy records in batches of depths  <br>
 * 040401 - Ursula von St Ange - change forcing of passkey to uppercase ub1 <br>
 * 091214 - Ursula von St Ange - change for inventory user (ub01)           <br>
 * 100105 - Ursula von St Ange - change for queuing of job (ub02)           <br>
 * 100521 - Ursula von St Ange - change for Anja - add etpop2 stuff (ub03)  <br>
 * 100615 - Ursula von St Ange - non-inventory users don't queue (ub04)     <br>
 * 100624 - Ursula von St Ange - change logging of job to beginning with
 *                               update at enc (ub05)                       <br>
 */
public class ExtractMRNPhysNutDataODVNatmirc { //extends CompoundItem {

    boolean dbg = false;
    //boolean dbg = true;
    boolean dbgQ = false;
    //boolean dbgQ = true;

    String thisClass = this.getClass().getName();

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
    String depMin;
    String depMax;
    float latitudeNorth;
    float latitudeSouth;
    float longitudeWest;
    float longitudeEast;
    Timestamp dateStart;
    Timestamp dateEnd;
    float depthMin;
    float depthMax;
    String flagType;
    String dataSet;
    String password;
    String passKey = "";
    String userType = "";                                               //ub01

    // variable from database
    int             noRecords = 0;

    // minimum and maximum's
    float           latitude     = 0.000f;
    float           maxLatitude  = -20.0f;
    float           minLatitude  =  99.0f;
    float           maxLongitude = -20.0f;
    float           minLongitude =  99.0f;
    Timestamp       minDate  = Timestamp.valueOf("2100-01-01 00:00:00.0");
    Timestamp       maxDate  = Timestamp.valueOf("1900-01-01 00:00:00.0");

    // constant for extracting watphy records
    int             spldepInterval = 100;

    /**
     * Main constructor ExtractMRNPhysNutDataODVNatmirc
     * @param   args   String array of arguments as from form or URL
     */
    public ExtractMRNPhysNutDataODVNatmirc(String args[])  {

        // Get the arguments
        getArgParms(args);

        // extract the user record from db
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        String userid = sessions[0].getUserid();
        if (dbg) System.out.println("<br>userid = " + userid);

        // get the correct PATH NAME - used while debugging
        String rootPath = "";
        if (dbg) System.out.println("<br>" + thisClass + ": host = " +
            ec.getHost());
        //if (ec.getHost().equals("morph")) {
        //if (ec.getHost().equals("morph")) {
        //    rootPath = sc.MORPH + userid + "/";
        //} else {
        //    rootPath = sc.LOCAL + "marine/";
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                rootPath = sc.HOSTDIR + "inv_user/";                    //ub01
            } else {                                                    //ub01
                rootPath = sc.HOSTDIR + userid + "/";                   //ub01
            } // if ("0".equals(userType))                              //ub01
        } else {
            rootPath = sc.LOCALDIR + "marine/";
        } // if host

        // new filename, but keep the original file name                //ub02
        fileNameIn = fileName;                                          //ub02
        if (!reqNumber.equals("")) {
            fileName = reqNumber + "_" + fileName;
        } // if

        //changing file exstention from dbm to txt for ODV programme.
        fileName += ".txt";
        if (dbg) System.out.println("<br>" + thisClass + ": " + fileName);

        // delete an old dummy file for Progress purposes ......
        String dummyFile = rootPath + fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        // create .queued file for Progress purposes ......             //ub02
        String dummyFile2 = rootPath + fileName + ".queued";            //ub02
        Queue queue = null;

        Timestamp startQDate = new Timestamp(new Date().getTime());     //ub05
        if ("0".equals(userType)) {  // from inventory                  //ub04
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
        } // if ("0".equals(userType)) {                                //ub04

        // create the user_log entry                                    //ub05
        Timestamp endQDate = new Timestamp(new Date().getTime());       //ub05
        LogUsers log = createLogUsers(                                  //ub05
            userid, endQDate, ec.timeDiff(endQDate, startQDate));       //ub05

        // create the 'processing' file
        dummyFile2 = rootPath + fileName + ".processing";
        ec.createNewFile(dummyFile2);

        // Open the data file.
        ec.deleteOldFile(rootPath + fileName);
        ofile = ec.openFile(rootPath + fileName, "rw");

        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob("chmod 644 " + rootPath + fileName);
        } //if (ec.getHost().startsWith(sc.HOST)) {

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
        } // if ("".equals(surveyId))
        depthMin = Float.parseFloat(depMin);
        depthMax = Float.parseFloat(depMax);


        // check if flagType combination is valid.  If not, revert back to
        // unflagged data extraction only
        if (dbg) System.out.println("<br>flagType = " + flagType +
            ", dataSet = " + dataSet + ", password = " + password);
        if (!"".equals(flagType)) {
            // was there a dataSet or a Password
            if ("".equals(dataSet) || "".equals(password)) {
                flagType = "1";
            } else {
                // is this user registered for the dataset
                //SadUserDset[] userDset = new SadUserDset(userid, dataSet).get();
                SadUserDset tmp = new SadUserDset(userid, dataSet);
                SadUserDset[] userDset = tmp.get();
                if (dbg) System.out.println("<br>tmp.getSelStr() = " + tmp.getSelStr());
                if (dbg) System.out.println("<br>userDset.length = " +
                    userDset.length);
                if (userDset.length == 0) {
                    flagType = "1";
                } else {
                    // did the user give the right password?
                    SadUsers[] users = new SadUsers().get(
                        SadUsers.FLAG_PASSWORD,
                        SadUsers.USERID + "='" + userid + "'", "");
                    if (dbg) System.out.println("<br>password = " + password +
                        ", users[0].getFlagPassword() = " +
                        users[0].getFlagPassword());
                    if (dbg) System.out.println("<br>users[0] = " + users[0]);
                    if (!users[0].getFlagPassword().equals(password)) {
                        flagType = "1";
                    } else {
                        // get the dataset password
                        SadUsers[] dataset = new SadUsers().get(
                        SadUsers.PASSWORD,
                        SadUsers.USERID + "='" + dataSet + "'", "");
                        if (dataset.length > 0) {
                            passKey = dataset[0].getPassword(); //.toUpperCase(); // ub1
                        } else {
                            flagType = "1";
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
            System.gc();
            getDataRecords(stations);
        } // if (validQuery)

        // clean up
        ec.closeFile(ofile);

        Timestamp endJDate = new Timestamp(new Date().getTime());       //ub05
        updateLogUsers(log, ec.timeDiff(endJDate, endQDate));           //ub05

        // delete the .processing file
        ec.deleteOldFile(dummyFile2);
        // create the dummy file for Progress purposes ......
        ec.createNewFile(dummyFile);

        // dequeue extraction                                           //ub02
        if ("0".equals(userType)) {  // from inventory                  //ub04
            if (!dbgQ) {
                queue.deQueue();                                        //ub02
            } // if (!dbgQ)
        } // if ("0".equals(userType))                                  //ub04

    } // ExtractMRNPhysNutDataODVNatmirc(String args[])


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        if (dbg) for (int i = 0; i < args.length; i++) {
            System.out.println("<br>" + args[i]);
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

        depMin = ec.getArgument(args,"pdepmin");
        depMax = ec.getArgument(args,"pdepmax");

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
        // where += " and " + MrnStation.STNDEP + " >= 1000";    // special

        String fields =
            MrnStation.STATION_ID + "," + MrnStation.SURVEY_ID + "," +
            MrnStation.STNNAM + "," + MrnStation.LATITUDE + "," +
            MrnStation.LONGITUDE + "," + MrnStation.DATE_START + "," +
            MrnStation.STNDEP + "," + MrnStation.MAX_SPLDEP + "," +
            MrnStation.PASSKEY;
        String order = MrnStation.SURVEY_ID + "," + MrnStation.DATE_START;
        if (dbg) System.out.println("<br>station where = " + where);
        MrnStation[] stations = new MrnStation().get(fields, where, order);
        if (dbg) System.out.println("<br>station where = " + where +
        ", stations.length = " + stations.length);
        return stations;
    } // getStationRecords


    /**
     * get the data records from the database
     */
    int getDataRecords(MrnStation[] stations) {
        noRecords = 0;
        String fields = "", where = "", order = "";
        String surveyId2 = "";
        float etopo2Depth = 0;
        float stnDepth = 0;
        int numStations = 0;

        for (int i = 0; i < stations.length; i++) {
            // get the etopo2 depth
            etopo2Depth = getEtopo2(stations[i]);
            stnDepth = stations[i].getStndep();

            //if (dbg)
            System.out.print("min, stnDepth, max = " + depthMin + " " +
                stnDepth + " " + depthMax);

            //if ((depthMin <= etopo2Depth) && (etopo2Depth <= depthMax)) {
            if ((depthMin <= stnDepth) && (stnDepth <= depthMax)) {
                //if (dbg)
                System.out.print(" ***");
                numStations++;
                if (dbg) System.out.println("<br>i = " + i + " " + stations[i].getStationId());
                // get watphy records
                // first check what the max depth for the station is.
                fields = "max(" + MrnWatphy.SPLDEP + ") as " + MrnWatphy.SPLDEP;
                where =
                    MrnWatphy.STATION_ID + "='" + stations[i].getStationId() + "'";
                order = MrnWatphy.SPLDEP;
                if (dbg) System.out.println("<br>watphy where = " + where);
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
                order = MrnWatphy.SPLDEP;

                // do a loop over the station depth - in intervals of 500 m
                for (int k = 0; k <= maxSpldep; k += spldepInterval) {

                    where =
                        MrnWatphy.STATION_ID + "='" + stations[i].getStationId() + "' and " +
                        MrnWatphy.SPLDEP + " between " + k + " and " + (k+spldepInterval-0.01);
                    //if (dbg) System.out.println("<br>where = " + where);
                    MrnWatphy[] watphy = new MrnWatphy().get(fields, where, order);
                    if (dbg) System.out.println("<br>i = " + i +
                        ", where = " + where +
                        ", watphy.length = " + watphy.length);

                    for (int j = 0; j < watphy.length; j++) {
                        //if (dbg) System.out.println("<br>j = " + j);
                        int code = watphy[j].getCode();

                        // get the water nutrients data
                        String fields2 = MrnWatnut.NO2 + "," + MrnWatnut.NO3 + "," +
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

                        printRecords(stations[i], watphy[j], watnut,
                            watchem1, watchl, etopo2Depth);
                        noRecords += 1;
                    } // for (int j = 0; j < watphys.length; j++)

                } // for int k

            } // if ((depMin <= etopo2Depth) && (etopo2Depth <= depMax))
            //if (dbg)
            System.out.println("");

        } // for int i
        System.out.println("number of stations = " + numStations);
        System.err.println("number of stations = " + numStations);
        System.gc();

        return noRecords;
    } //


    /**
     * Print the current marine data records
     * @param   station   MrnStation data
     * @param   watphy    MrnWatphy data
     * @param   watnut    MrnWatnut data
     * @param   watchem1  MrnWatchem1 data
     * @param   watchl    MrnWatchl data
     */
    void printRecords(MrnStation station, MrnWatphy watphy,
            MrnWatnut[] watnut, MrnWatchem1[] watchem1, MrnWatchl[] watchl,
            float etopo2Depth) {

        // get the variables for records that may be null
        //String planamName = "";
        //if (planam.length > 0) {
        //    planamName = planam[0].getName("");
        //    //ec.writeFileLine(ofile, "planam:  " + planam[0].toString());
        //} //
        //if (dbg) System.out.println("<br>planamName = " + planamName);

        //ec.writeFileLine(ofile, "station: " + station.toString());
        //ec.writeFileLine(ofile, "watphy:  " + watphy.toString());

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
        } //

        float ph = MrnWatchem1.FLOATNULL;
        if (watchem1.length > 0) {
            ph = watchem1[0].getPh();
            //ec.writeFileLine(ofile, "watchem1:" + watchem1[0].toString());
        } //

        float chla = MrnWatchl.FLOATNULL;
        if (watchl.length > 0) {
            chla = watchl[0].getChla();
            //ec.writeFileLine(ofile, "watchl:  " + watchl[0].toString());
        } //

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

        //get getSubdes and test fot type
        String sub = watphy.getSubdes();
        if ("bottl".equalsIgnoreCase(sub)) {
            sub = "B";
        } else if ("osd".equalsIgnoreCase(sub)) {
            sub = "B";
        } else if ("ctd".equalsIgnoreCase(sub)) {
            sub = "C";
        } else if ("xbt".equalsIgnoreCase(sub)) {
            sub = "C";
        } else if ("mbt".equalsIgnoreCase(sub)) {
            sub = "C";
        } else {
            sub = "*";
        } //if ("Bottl".equalsIgnoreCase(sub))

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

        // Print the BODY OF THE FILE
            latitude = station.getLatitude() * -1;

            String stId       = station.getStationId();
            String giveName   = stId.substring(3);
            String stName     = station.getStnnam();

            if ( ("".equals(stName) ) || (stName == MrnStation.CHARNULL) ) {
                stName=giveName;
            } //if ("".equals(stName)) {
            String tab = "\t";

            String line =
            ec.frm(station.getSurveyId(),9)     +tab+
            ec.frm(stName,10)                   +tab+
            ec.frm(sub,5)                       +tab+
            month+"/" +day+"/"+ year            +tab+
            ftime.trim()                        +tab+
            ec.frm(station.getLongitude(),8,3)  +tab+
            ec.frm(latitude,8,3)                +tab+
            nullToZERO(station.getStndep(),8,2)     +tab+
            nullToNULL(watphy.getSpldep(),8,2)      +tab+
            nullToNULL(watphy.getTemperature(),8,2) +tab+
            nullToNULL(watphy.getSalinity(),9,3)    +tab+
            nullToNULL(watphy.getDisoxygen(),8,2)   +tab+
            nullToNULL(no2,8,2)                     +tab+
            nullToNULL(no3,8,2)                     +tab+
            nullToNULL(po4,8,2)                     +tab+
            nullToNULL(p,8,2)                       +tab+
            nullToNULL(sio3,8,2)                    +tab+
            nullToNULL(ph,8,2)                      +tab+
            nullToNULL(watphy.getSoundv(),7,1)      +tab+
            nullToNULL(chla,9,3)                    +tab+
            nullToNULL(station.getMaxSpldep(),8,2)  +tab+
            nullToNULL(etopo2Depth,8,2);
            /*
            ec.frm(ec.nullToNines(station.getStndep(),9999.0f),8,2)    +tab+
            ec.frm(ec.nullToNines(watphy.getSpldep(),9999.0f),8,2)     +tab+
            ec.frm(ec.nullToNines(watphy.getTemperature(),9999.0f),8,2)+tab+
            ec.frm(ec.nullToNines(watphy.getSalinity(),9999.0f),9,3)   +tab+
            ec.frm(ec.nullToNines(watphy.getDisoxygen(),9999.0f),8,2)  +tab+
            ec.frm(ec.nullToNines(no2,9999.0f),8,2)                    +tab+
            ec.frm(ec.nullToNines(no3,9999.0f),8,2)                    +tab+
            ec.frm(ec.nullToNines(po4,9999.0f),8,3)                    +tab+
            ec.frm(ec.nullToNines(p,9999.0f),9,3)                      +tab+
            ec.frm(ec.nullToNines(sio3,9999.0f),8,2)                   +tab+
            ec.frm(ec.nullToNines(ph,9999.0f),8,2)                     +tab+
            ec.frm(ec.nullToNines(watphy.getSoundv(),9999.0f),7,1)     +tab+
            ec.frm(ec.nullToNines(chla,9999.0f),9,3)                   +tab+
            ec.frm(ec.nullToNines(station.getMaxSpldep(),9999.0f),8,2);
            */

        //if (dbg) System.out.println("<br>" + thisClass + ".printRecords: " +
        //    line1);

        //if (dbg) System.out.println("<br>" + thisClass + ".printRecords: " +
        //    line);

        ec.writeFileLine(ofile,line);

    } // printRecords

    /**
     * print the column headings
     */
    void ColumnHeadings() {
        // method to write headings of estraxted data
        String head =
            "Cruise\tStation\tType\tmon/day/yr\thh:mm\tLon (°E)\t"+
            "Lat (°N)\tBot. Depth [m]\tDepth [m]\t"+
            "Temperature [deg C]\tSalinity [ppt]\tDisolved Oxygen [ml/l]\t"+
            "Nitrite [~$m~#g atom/l]\tNitrate [~$m~#g atom/l]\t"+
            "Phosphate [~$m~#g atom/l]\tPhosporus [~$m~#g atom/l]\t"+
            "Silicate [~$m~#g atom/l]\tpH []\tSound Velocity [m/s]\t"+
            "Chlorophyll A [~$m~#g/l]\tDeepest Obs. Depth [m]\t"+
            "Etopo2 Depth [m]";
        ec.writeFileLine(ofile,head);
    } // ColumnHeadings


    String nullToZERO(float value, int length, int decimal) {
        return
            (value == Tables.FLOATNULL
                ? "0"
                : ec.frm(value,length,decimal));
        //(station.getStndep()==INTN?ec.frm(station.getStndep(),8,2):"1e+010") +tab+
    } //

    String nullToNULL(float value, int length, int decimal) {
        return
            (value == Tables.FLOATNULL
                ? "1e+010"
                : ec.frm(value,length,decimal));
        //(station.getStndep()==INTN?ec.frm(station.getStndep(),8,2):"1e+010") +tab+
    } //

    /**
     * print an error message if inputs not OK
     */
    void printErrorMessage() {
    } // printErrorMessage


    /**                                                                 //ub05
     * Create the LOG_USERS table entry for this extraction.            //ub05
     */                                                                 //ub05
    LogUsers createLogUsers(String userid, Timestamp start, String timeDiff) {//ub05
        LogUsers logs = new LogUsers();                                 //ub05
        logs.setUserid(userid);                                         //ub05
        logs.setDateTime(start);                                        //ub05
        logs.setDatabase("MARINE");                                     //ub05
        logs.setDiscipline("physnutodv");                               //ub05
        logs.setProduct("Extraction");                                  //ub05
        logs.setFileName(fileName);                                     //ub05
        logs.setQueueDuration(timeDiff);                                //ub05
        logs.setSurveyId(surveyId);                                     //ub05
        boolean success = logs.put();                                   //ub05
                                                                        //ub05
        // commit this log - in case the job bombs out                  //ub05
        common2.DbAccessC.commit();                                     //ub05
                                                                        //ub05
        return logs;                                                    //ub05
                                                                        //ub05
    } // createLogUsers                                                 //ub05


    /**
     * Update the LOG_USERS table for this extraction.
     */
/*
    void updateLogUsers(String userid, String timeDiff) {
        LogUsers logs = new LogUsers();
        logs.setUserid(userid);
        logs.setDateTime(new Timestamp(new java.util.Date().getTime()));
        logs.setDatabase("MARINE");
        logs.setDiscipline("physnutodv");
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
        System.out.println(logs);
        boolean success = logs.put();
    } // updateLogUsers
*/

    /**                                                                 //ub05
     * Update the LOG_USERS table for this extraction.                  //ub05
     */                                                                 //ub05
    void updateLogUsers(LogUsers logs, String timeDiff) {               //ub05
        // define the value that should be updated                      //ub05
        LogUsers updateLogs = new LogUsers();                           //ub05
        updateLogs.setLatitudeNorth(minLatitude);                       //ub05
        updateLogs.setLatitudeSouth(maxLatitude);                       //ub05
        updateLogs.setLongitudeWest(minLongitude);                      //ub05
        updateLogs.setLongitudeEast(maxLongitude);                      //ub05
        updateLogs.setDateStart(minDate);                               //ub05
        updateLogs.setDateEnd(maxDate);                                 //ub05
        updateLogs.setNumRecords(noRecords);                            //ub05
        updateLogs.setJobDuration(timeDiff);                            //ub05
                                                                        //ub05
        // define the 'where' clause                                    //ub05
        LogUsers whereLogs = new LogUsers();                            //ub05
        whereLogs.setDateTime(logs.getUserid());                        //ub05
        whereLogs.setDateTime(logs.getDateTime());                      //ub05
                                                                        //ub05
        // do the update                                                //ub05
        whereLogs.upd(updateLogs);                                      //ub05
                                                                        //ub05
    } // updateLogUsers                                                 //ub05


    /**
     * Test for overland data
     * @param station : array of station records
     */
    float getEtopo2(MrnStation station) {

        if (dbg) System.out.println("\n" + new java.util.Date());

        // using etopo and etopo2 tables
        float difference = 0.01667f;
        String where = MrnEtopo.LATITUDE + " between " +
            (station.getLatitude() - difference) + " and " +
            (station.getLatitude() + difference) + " and " +
            MrnEtopo.LONGITUDE + " between " +
            (station.getLongitude() - difference) + " and " +
            (station.getLongitude() + difference);

        // find out which etopo table to use, and get the height
        int height = 999999;
        if ((station.getLatitude() >= -10) &&
            (station.getLongitude() >= -30) &&
            (station.getLongitude() <= 70)) {
            MrnEtopo etop = new MrnEtopo();
            MrnEtopo etopo[] = etop.get(where);
            if (etopo.length > 0) {
                height = etopo[0].getHeight();
            } // if (etopo.length > 0)
            if (dbg) System.out.println("etopo");
            if (dbg) System.out.println("selStr = " + etop.getSelStr());
            if (dbg) System.out.println("etopo.length = " + etopo.length);
            if (dbg) System.out.println("height = " + height);
        } else {
            MrnEtopo2 etop = new MrnEtopo2();
            MrnEtopo2 etopo[] = etop.get(where);
            if (etopo.length > 0) {
                height = etopo[0].getHeight();
            } // if (etopo.length > 0)
            if (dbg) System.out.println("etopo2");
            if (dbg) System.out.println("selStr = " + etop.getSelStr());
            if (dbg) System.out.println("etopo.length = " + etopo.length);
            if (dbg) System.out.println("height = " + height);
        } // if ((station.getLatitude() <= -10) &&

        return -height;

    } // getEtopo2



    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            ExtractMRNPhysNutDataODVNatmirc local =
                new ExtractMRNPhysNutDataODVNatmirc(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractMRNPhysNutDataODVNatmirc", "Constructor", "");
            // close the connection to the database
            //common2.DbAccessC.close();
        } // try-catch
        common2.DbAccessC.close();

    } // main


 }// class ExtractMRNPhysNutDataODVNatmirc
