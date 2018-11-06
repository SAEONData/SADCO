package sadco;

import common2.Queue;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Does the actual extraction of the requested data.  Calculates the cross-bearings
 * vector component of the currents.  It calculates it at the first and last
 * stations using the actual current speed and directions, and for the rest
 * at the position in between the two stations, using the average speed and
 * direction of the two stations.
 *
 * SadcoMRNApps
 * screen.equals("extract")
 * version.equals("extract")
 * extrType.equals("currentsvec")
 *
 * @author 011001 - SIT Group
 * @version
 * 040609 - Ursula von St Ange - create class using ExtractMRNPhysnutData   <br>
 * 091214 - Ursula von St Ange - change for inventory user (ub01)           <br>
 * 100105 - Ursula von St Ange - change for queuing of job (ub02)           <br>
 * 100615 - Ursula von St Ange - non-inventory users don't queue (ub03)     <br>
 * 100624 - Ursula von St Ange - change logging of job to beginning with
 *                               update at enc (ub04)                       <br>
 * 110525 - Ursula von St Ange - add perc_good (ub05)                       <br>
 * 121113 - Ursula von St Ange - add etopo2 depth (ub06)                    <br>
 * 150316 - Ursula von St Ange - create from ExtractMRNCurData (ub07)       <br>
 */
public class ExtractMRNCurDataComponent {

    boolean dbg = false;
    //boolean dbg = true;
    //boolean dbgQ = false;
    boolean dbgQ = true;  // set false when HOST is known.

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

    // constant for extracting currents records
    int             spldepInterval = 500;

    /**
     * Main constructor ExtractMRNCurDataComponent
     * @param   args   String array of arguments as from form or URL
     */
    public ExtractMRNCurDataComponent(String args[])  {

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
        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                rootPath = sc.HOSTDIR + "inv_user/";                    //ub01
            } else {                                                    //ub01
                rootPath = sc.HOSTDIR + userid + "/";                   //ub01
            } // if ("0".equals(userType))                              //ub01
            dbgQ = false;                                               //ub08
        } else {
            rootPath = sc.LOCALDIR + "marine/";
        } // if host

        // new filename, but keep the original file name                //ub02
        fileNameIn = fileName;                                          //ub02
        if (!reqNumber.equals("")) {
            fileName = reqNumber + "_" + fileName;
        } // if
        fileName += ".dbc";
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
            System.gc();
            getDataRecords(stations);
        } // if (validQuery)

        printLegend();
        printHeader();

        // clean up
        ec.closeFile(ofile);

        Timestamp endJDate = new Timestamp(new Date().getTime());       //ub04
        updateLogUsers(log, ec.timeDiff(endJDate, endQDate));           //ub04

        // delete .progressing file ......
        ec.deleteOldFile(dummyFile2);
        // create the finished file ......
        ec.createNewFile(dummyFile);

        // dequeue extraction                                           //ub02
        if ("0".equals(userType)) {  // from inventory                  //ub03
            if (!dbgQ) {
                queue.deQueue();                                        //ub02
            } // if (!dbgQ)
        } // if ("0".equals(userType))                                  //ub03

    } // ExtractMRNCurDataComponent(String args[])


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
        //String order = MrnStation.SURVEY_ID + "," + MrnStation.DATE_START;
        String order = MrnStation.SURVEY_ID + "," + MrnStation.DATE_START + "," +
            MrnStation.STNNAM;
        MrnStation[] stations = new MrnStation().get(fields, where, order);
        if (dbg) System.out.println("\n\n<br>" + MrnStation.TABLE +
            ": where = " + where + ", \nstations.length = " + stations.length);
        return stations;

    } // getStationRecords


    /**
     * get the data records from the database
     */
    int getDataRecords(MrnStation[] stations) {

        // calculate ship bearings between stations
        float bearings[] = calcBearings(stations);                      //ub07

        noRecords = 0;
        String fields = "", where = "", order = "";

        for (int i = 0; i < stations.length; i++) {
//        for (int i = 0; i < 10; i++) {
            if (dbg) System.out.println("\n<br>i = " + i);

            // check to see whether bearings changed a lot between
            // stations.  Then do with both bearings (to station and from station)
            int doBearingLoop = 1;                                          //ub07
            if (i < stations.length-1) {
                if (Math.abs(bearings[i] - bearings[i+1]) > sc.MAX_BEARING_DEV) {  //ub07
                    doBearingLoop = 2;                                          //ub07
                } // if (Math.abs(bearings[i] - bearings[i+1]) > sc.MAX_BEARING_DEV)//ub07
            } // if (i < stations.length-1)                                     //ub07

            // first check what the max depth for the station is.
            fields = "max(" + MrnCurrents.SPLDEP + ") as " + MrnCurrents.SPLDEP;
            where =
                MrnCurrents.STATION_ID + "='" + stations[i].getStationId() + "'";
            order = MrnCurrents.SPLDEP;
            //MrnCurrents[] currents2 = new MrnCurrents().get(fields, where, order);
            MrnCurrents temp = new MrnCurrents();
            MrnCurrents[] currents2 = temp.get(fields, where, order);
            if (dbg) System.out.println("<br>selStr = " + temp.getSelStr());
            float maxSpldep = 0;
            if (currents2.length > 0) {
                //if (dbg) System.out.println("<br>currents = " + currents[0]);
                maxSpldep = currents2[0].getSpldep();
            }
            if (dbg) System.out.println("<br>currents2.length = " + currents2.length +
                ", maxSpldep = " + maxSpldep);

            // get watcurrent records
            fields =
                MrnCurrents.SPLDATTIM + "," + MrnCurrents.SPLDEP + "," +
                MrnCurrents.SUBDES + "," + MrnCurrents.CURRENT_DIR + "," +
                MrnCurrents.CURRENT_SPEED + "," + MrnCurrents.PERC_GOOD;    //ub05

            for (int bl = 0; bl < doBearingLoop; bl++) {                    //ub07
                                                                            //ub07
                float bearing = bearings[i+bl];                             //ub07
                String bearingIndicator = "";                               //ub07
                if (bl == 1) bearingIndicator = "B";                        //ub07
                if (dbg) System.out.println("getDataRecords: bearingIndicator = " + bl +
                    " " +bearingIndicator);                                 //ub07

                // do a loop over the station depth - in intervals of 500 m
                for (int k = 0; k <= maxSpldep; k += spldepInterval) {
//                for (int k = 0; k <= spldepInterval; k += spldepInterval) {

                    where =
                        MrnCurrents.STATION_ID + "='" + stations[i].getStationId() + "' and " +
                        MrnCurrents.SPLDEP + " between " + k + " and " + (k+spldepInterval-0.01);
                    //MrnCurrents[] currents = new MrnCurrents().get(fields, where, order);
                    MrnCurrents[] currents = temp.get(fields, where, order);
                    if (dbg) System.out.println("<br>selStr = " + temp.getSelStr());
                    if (dbg) System.out.println("<br>i = " + i +
                        ", where = " + where +
                        ", currents.length = " + currents.length);

                    for (int j = 0; j < currents.length; j++) {

                        //if (dbg) System.out.println("<br>j = " + j);
                        printRecords(stations[i], bearing, bearingIndicator,    //ub07
                            currents[j]);                                       //ub07
                        noRecords += 1;

                    } // for (int j = 0; j < currentss.length; j++)

                } // for int k

            } // for (int bl = 0; bl < doBearingLoop; bl++)             //ub07

        } // for int i
        System.gc();

        return noRecords;
    } //


    /**
     * calculate the bearings between stations
     * bearings for 1st and 2nd stations are the same
     * @param   station     MrnStation data
     * @returns bearings     bearings between stations
     */
    float[] calcBearings(MrnStation[] stations) {                       //ub07

        float bearings[] = new float[stations.length];
        for (int i = 1; i < stations.length; i++) {
//        for (int i = 1; i < 5; i++) {


//            if (dbg) System.out.println("CalcBearings: lat1, lat2 = " +
//                ec.frm(-stations[i-1].getLatitude(),8,4) +
//                ec.frm(-stations[i].getLatitude(),8,4));
//            if (dbg) System.out.println("CalcBearings: lon1, lon2 = " +
//                ec.frm(stations[i-1].getLongitude(),8,4) +
//                ec.frm(stations[i].getLongitude(),8,4));

            double lat1 = Math.toRadians(-stations[i-1].getLatitude());
            double lat2 = Math.toRadians(-stations[i].getLatitude());
            double lon1 = Math.toRadians(stations[i-1].getLongitude());
            double lon2 = Math.toRadians(stations[i].getLongitude());

//            if (dbg) System.out.println("CalcBearings: lat1, lat2 = " +
//                ec.frm(lat1,8,5) + ec.frm(lat2,8,5));
//            if (dbg) System.out.println("CalcBearings: lon1, lon2 = " +
//                ec.frm(lon1,8,5) + ec.frm(lon2,8,5));

            double dlat = Math.toRadians
                (-stations[i].getLatitude() + stations[i-1].getLatitude());
            double dlon = Math.toRadians
                (stations[i].getLongitude() - stations[i-1].getLongitude());
//            if (dbg) System.out.println("CalcBearings: dlat, dlon = " +
//                ec.frm(dlat,8,5) + " " + ec.frm(dlon,8,5));

            double y = Math.sin(dlon) * Math.cos(lat2);
            double x = Math.cos(lat1) * Math.sin(lat2) -
                Math.sin(lat1) * Math.cos(lat2) * Math.cos(dlon);

//            if (dbg) System.out.println("CalcBearings: y, x = " +
//                ec.frm(y,8,5) + ec.frm(x,8,5));

            double tc1 = 0;
            if (dbg) {
                if (y > 0) {
                    if (x > 0) System.out.println("CalcBearings: Y>0,x>0: Math.toDegrees(Math.atan(y/x));");
                    if (x < 0) System.out.println("CalcBearings: Y>0,x<0: 180 - Math.toDegrees(Math.atan(-y/x));");
                    if (x == 0)System.out.println("CalcBearings: Y>0,x=0: 90;");
                } // if (y > 0}
                if (y < 0) {
                    if (x > 0) System.out.println("CalcBearings: Y<0,x>0: Math.toDegrees(-Math.atan(-y/x));");
                    if (x < 0) System.out.println("CalcBearings: Y<0,x<0: Math.toDegrees(Math.atan(y/x)-180);");
                    if (x == 0) System.out.println("CalcBearings: Y<0,x=0: 270;");
                } // if (y < 0)
                if (y == 0) {
                    if (x > 0) System.out.println("CalcBearings: Y=0,x>0: 0;");
                    if (x < 0) System.out.println("CalcBearings: Y=0,x<0: 180;");
                    if (x == 0) System.out.println("CalcBearings: Y=0,x=0: 0;  // [the 2 points are the same]");
                } // if (y = 0)
            } // if (dbg)
            if (y > 0) {
                if (x > 0) tc1 = Math.toDegrees(Math.atan(y/x));
                if (x < 0) tc1 = 180 - Math.toDegrees(Math.atan(-y/x));
                if (x == 0) tc1 = 90;
            } // if (y > 0}
            if (y < 0) {
                if (x > 0) tc1 = Math.toDegrees(-Math.atan(-y/x));
                if (x < 0) tc1 = Math.toDegrees(Math.atan(y/x))-180;
                if (x == 0) tc1 = 270;
            } // if (y < 0)
            if (y == 0) {
                if (x > 0) tc1 = 0;
                if (x < 0) tc1 = 180;
                if (x == 0) tc1 = 0;  // [the 2 points are the same]
            } // if (y = 0)
            if (tc1 < 0) tc1 = 360 + tc1;
            bearings[i] = (float)tc1;

//            if (dbg) System.out.println("CalcBearings: bearing = " + tc1);
        } // for (i = 1; i < stations.length; i++)

        bearings[0] = bearings[1];

        if (dbg)
            for (int i = 0; i < 7; i++)
                System.out.println("calcBearings: bearing = " +
                    i + " " + bearings[i]);

        return bearings;

    } // calcBearings                                                   //ub07


    /**
     * Print the current marine data records
     * @param   station     MrnStation data
     * @param   bearing     the ship bearing / heading
     * @param   currents    MrnCurrents data
     */
    void printRecords(MrnStation station, float bearing, String bearingIndicator, //ub07
        MrnCurrents currents) {                                         //ub07

        //ec.writeFileLine(ofile, "station: " + station.toString());
        //ec.writeFileLine(ofile, "currents:  " + currents.toString());

        // calculate cross-bearing component                            //ub07
        float dirTrig = 90 - currents.getCurrentDir();                  //ub07
        double dirToUse = dirTrig + bearing;                            //ub07
        float vCross = (float)(currents.getCurrentSpeed() *             //ub07
            Math.cos(Math.toRadians(dirToUse)));

        if (dbg) System.out.println("printRecords: cDir, dirTrig = " +  //ub07
            currents.getCurrentDir() + " " + dirTrig);                  //ub07
        if (dbg) System.out.println("printRecords: bearing, speed = " + //ub07
            bearing + " " + currents.getCurrentSpeed());                //ub07
        if (dbg) System.out.println("printRecords: dirToUse, vCross = " + //ub07
            dirToUse + " " + vCross);                                   //ub07

        // get the time in string format
        Timestamp dateT = currents.getSpldattim();
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
            ec.frm(ec.nullToNines(currents.getSpldep(),9999.0f),8,2) +
                ", " + day + ", \"" + ftime.trim() + "\"," +
            ec.frm(ec.nullToNines(bearing,9999.0f),7,3) + "," +         //ub07
            ec.frm(ec.nullToNines(vCross,9999.0f),14,4) + ", \"" +      //ub07
            ec.frm(station.getStationId()+bearingIndicator,13) + "\"," + //ub07
            ec.frm(ec.nullToNines(station.getStndep(),9999.0f),10,2) + "," +
            ec.frm(ec.nullToNines(etopo2Depth,9999.0f),10,2) + ", " + //ub06
            ec.frm(ec.nullToNines(station.getMaxSpldep(),9999.0f),10,2) + ", \"" +
            ec.frm(station.getStnnam(""),10) + "\", \"" +
            ec.frm(station.getSurveyId(),9) + "\", \"" +
            ec.frm(currents.getSubdes(""),10) + "\", \"" +
            ec.frm(currents.getPercGood(""),10) + "\",";                //ub05

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

        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: minLatitude = " + minLatitude);
        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: maxLatitude = " + maxLatitude);
        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: minLongitude = " + minLongitude);
        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: maxLongitude = " + maxLongitude);
        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: minDate = " + minDate);
        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: maxDate = " + maxDate);

        String line =
            "MARINE  " +
            ec.frm(minLatitude,6,1) +
            ec.frm(maxLatitude,6,1) +
            ec.frm(minLongitude,6,1) +
            ec.frm(maxLongitude,6,1) + "  " +
            minDate.toString().substring(0,10) + "  " +
            maxDate.toString().substring(0,10) + " CURRENT CROSS-BEARING COMP" +  //ub07
            " " + surveyId;

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

        String head = "  LATDEG,  LONDEG, YEAR, MONTH, DEPTH,DAY, TIME   ,"+
                      "BEARING,X-BEARING CURR COMP, STATION ID    ,    STNDEP,"+   //ub07
                      "ETOPO2 DEP,   MXSPLDEP, STN NAM     , SURVEYID   , " + //ub06
                      "INSTR       , PERC GOOD";                        //ub05

        ec.writeFileLine(ofile,head);
    } // columnHeadings


    /**
     * print the legend at the bottom of the file
     */
    void printLegend() {

        String head = "\n\n" +                                              //ub07
            "  The ship bearing between two stations is the\n" +            //ub07
            "    *initial* heading for a great-circle route.\n" +           //ub07
            "  The cross-bearing current component is perpindicular to the\n" + //ub07
            "    ship bearing, positive to the right.\n"+                   //ub07
            "  If the angle between two consecutive bearings are\n" +       //ub07
            "    greater than " + sc.MAX_BEARING_DEV + " degrees, the" +       //ub07
            " cross-bearing current\n" +                                    //ub07
            "    component was calculated twice, once for each bearing.\n";                                  //ub07
        ec.writeFileLine(ofile,head);

        String legend = "\n\n" +
            "  LATDEG    =   LATITUDE (DEGREES) \n"+
            "  LONDEG    =   LONGITUDE (DEGREES) \n"+
            "  YEAR      =   YEAR IN WHICH RECORDING WAS MADE\n"+
            "  MONTH     =   MONTH IN WHICH RECORDING WAS MADE\n"+
            "  DEPTH     =   MEASUREMENT DEPTH (METRES)\n"+
            "  DAY       =   DAY IN WHICH RECORDING WAS MADE \n"+
            "  TIME      =   TIME OF RECORDING \n"+
            "  BEARING   =   SHIP BEARING(DEGREES TN) \n"+              //ub07
            "  X-BEARING CURR COMP = THE CURRENT COMPONENT PERPINDICULAR\n"+//ub07
            "                 TO THE SHIP BEARING, POSITIVE TO THE RIGHT\n"+ //ub07
            "  STATION ID=   UNIQUE STATION IDENTIFIER\n" +
            "  STNDEP    =   STATION DEPTH\n" +
            "  ETOPO2 DEP=   ETOPO2 DEPTH\n" +                          //ub06
            "  MXSPLDEP  =   MAXIMUM SAMPLING DEPTH\n" +
            "  STN NAM   =   STATION NAME AS GIVEN BY CONTRIBUTOR\n" +
            "  SURVEYID  =   UNIQUE SADCO SURVEY IDENTIFIER\n" +
            "  INSTR     =   MEASURING INSTRUMENT\n" +
            "  PERC GOOD =   QUALITY IN PERCENTAGE GOOD";               //ub05
         ec.writeFileLine(ofile,legend);
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
        logs.setDatabase("CURRENT");                                    //ub04
        logs.setDiscipline("curcomp");                                  //ub04 ub07
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
        logs.setDatabase("CURRENT");
        logs.setDiscipline("curdbc");
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
            ExtractMRNCurDataComponent local =
                new ExtractMRNCurDataComponent(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractMRNCurDataComponent", "Constructor", "");
        } // try-catch
        // close the connection to the database
        common2.DbAccessC.close();

    } // main


 }// class ExtractMRNCurDataComponent
