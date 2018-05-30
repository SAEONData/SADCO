package sadco;

import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Does the actual extraction of the requested data.  Called directly
 *
 *
 * @author 100706 - SIT Group
 * @version
 * 100706 - Ursula von St Ange - create class                               <br>
 */
public class ExtractMRNPhysNutDataIMT {

    boolean dbg = false;
    //boolean dbg = true;
    //boolean dbg2 = false;
    boolean dbg2 = true;

    String thisClass = this.getClass().getName();

    /** some common functions */
    common.edmCommonPC ec = new common.edmCommonPC();
    /** url parameters names & applications */
    SadConstants sc = new SadConstants();

    static final float DELTA_DEG = 0.125f;
    static final String USERID = "sadreq";

    // file stuff
    static RandomAccessFile ifile = null;
    static RandomAccessFile ofilePRF = null;
    static RandomAccessFile ofileSUM = null;

    // arguments
    String province = "";
//    String provDescription = "";
    int numGrids = 0;
    int gridNum = 0;
    float gridLat = 0;
    float gridLon = 0;
    float gridLatTL = 0;
    float gridLonTL = 0;
    float gridLatBR = 0;
    float gridLonBR = 0;

    int profileStart[] = new int[10];
    int profileEnd[] = new int[10];
    boolean hasSalinity[] = new boolean[10];
    boolean hasTemperature[] = new boolean[10];
    int numProfiles = 0;
    int numCTDprofiles = 0;
    int numXBTprofiles = 0;
    int numTotalProfiles = 0;
    int month = 0;

//    String sessionCode;
//    String reqNumber;
//    String fileNameIn;                                                  //ub02
    String rootPath = "";
    String fileName = "";
    String fileNamePRF = "";
    String fileNameSUM = "";
    String line = "";
//    String extrType;
//    String surveyId;
//    String startDate;
//    String endDate;
//    String latNorth;
//    String latSouth;
//    String longWest;
//    String longEast;
    int monthIn = 0;
    float latitudeNorth;
    float latitudeSouth;
    float longitudeWest;
    float longitudeEast;
//    Timestamp dateStart;
//    Timestamp dateEnd;
//    String flagType;
//    String dataSet;
//    String password;
//    String passKey = "";
//    String userType = "";                                               //ub01
    int noRecords = 0;


    int numVals = 0;
    float sumVals = 0;
    float sumSqVals = 0;

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
     * Main constructor ExtractMRNPhysNutDataIMT
     * @param   args   String array of arguments as from form or URL
     */
    public ExtractMRNPhysNutDataIMT(String args[])  {

        // Get the arguments
        if (args.length > 0) {
            monthIn = Integer.parseInt(args[0]);
            fileName = args[1];
        } else {
            if (dbg2) {
                //monthIn = 6;
                monthIn = 99;
                //fileName = "Prov1_Jun.txt";
                fileName = "prov3/Prov3.txt";
            } else {
                System.out.println("Please enter a file name");
                return;
            } // if (dbg2)
        } // if (args.length > 1)

        // get the correct PATH NAME - used while debugging
        //if (ec.getHost().startsWith(sc.HOST) || ec.getHost().startsWith(sc.HOST_ST)) {
        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIR + USERID + "/";                   //ub01
        } else {
            rootPath = sc.LOCALDIR + "imt/";
        } // if host

        printDebug("fileName = " + rootPath + fileName);

        // create the user_log entry                                    //ub04
        Timestamp startQDate = new Timestamp(new Date().getTime());     //ub04
        Timestamp endQDate = new Timestamp(new Date().getTime());       //ub04
        LogUsers log = createLogUsers(                                  //ub04
            USERID, endQDate, ec.timeDiff(endQDate, startQDate));       //ub04

        // get the three output file names, and open them
        int dotPos = fileName.lastIndexOf(".");
        fileNamePRF = fileName.substring(0,dotPos) + "_PRF.txt";
        fileNameSUM = fileName.substring(0,dotPos) + "_SUM.txt";
        ec.deleteOldFile(rootPath + fileNamePRF);
        ec.deleteOldFile(rootPath + fileNameSUM);
        ofilePRF = ec.openFile(rootPath + fileNamePRF, "rw");
        ofileSUM = ec.openFile(rootPath + fileNameSUM, "rw");

        // read the file, and get the data
        ifile = ec.openFile(rootPath + fileName, "r");
        line = ec.getNextLine(ifile);
        StringTokenizer t = new StringTokenizer(line, " ,/t");
        province = t.nextToken();
//        provDescription = t.nextToken();
        numGrids = Integer.parseInt(t.nextToken());
        printDebug("province = " + province + ", numGrids = " + numGrids);

        numVals = 0;
        sumVals = 0;
        sumSqVals = 0;
        gridNum = 0;
        for (int i = 0; i < numGrids; i++) {

            line = ec.getNextLine(ifile);
            t = new StringTokenizer(line, ", \t");
            //gridNum = Integer.parseInt(t.nextToken());
            gridLatTL = -Float.parseFloat(t.nextToken());
            gridLonTL = Float.parseFloat(t.nextToken());
            gridLatBR = -Float.parseFloat(t.nextToken());
            gridLonBR = Float.parseFloat(t.nextToken());

            //printDebug("gridNum = " + gridNum + ", gridLat = " + gridLat +
            //     ", gridLon = " + gridLon);
            printDebug("gridLatTL = " + gridLatTL +
                 ", gridLonTL = " + gridLonTL + ", gridLatBR = " + gridLatBR +
                 ", gridLonBR = " + gridLonBR);

            // loop through all the .25 degree square blocks
            for (float lat = gridLatTL; lat < gridLatBR; lat += 0.25) {

                for (float lon =  gridLonTL; lon < gridLonBR; lon += 0.25) {

                    gridNum++;
                    numVals = 0;
                    sumVals = 0f;
                    sumSqVals = 0f;

                    // get the correct lat and long
                    //latitudeNorth = gridLat - DELTA_DEG;
                    //latitudeSouth = gridLat + DELTA_DEG;
                    //longitudeWest = gridLon - DELTA_DEG;
                    //longitudeEast = gridLon + DELTA_DEG;
                    latitudeNorth = lat;
                    latitudeSouth = lat + DELTA_DEG * 2 - .00001f;
                    longitudeWest = lon;
                    longitudeEast = lon + DELTA_DEG * 2 - .00001f;
                    gridLat = -lat - DELTA_DEG;
                    gridLon = lon + DELTA_DEG;

                    // loop through the months
                    int monthStart = 1;
                    int monthEnd = 12;
                    if (monthIn < 12) {
                        monthStart = monthIn;
                        monthEnd = monthIn;
                    } // if (month < 12)

                    for (month = monthStart; month <= monthEnd; month++) {

                        // get the stations
                        MrnStation stations[] = getStationRecords();

                        //for each station
                        numProfiles = 0;
                        numCTDprofiles = 0;
                        numXBTprofiles = 0;

                        for (int j = 0; j < stations.length; j++) {
                            MrnWatphy watphy[] = getDataRecords(stations[j]);

                            // get actual min and max ranges for logging
                            maxLatitude = Math.max(maxLatitude, stations[j].getLatitude());
                            minLatitude = Math.min(minLatitude, stations[j].getLatitude());
                            maxLongitude = Math.max(maxLongitude, stations[j].getLongitude());
                            minLongitude = Math.min(minLongitude, stations[j].getLongitude());

                            // ignore surface-only profiles, and profiles with less than 4 values
                            for (int ii = 0; ii < 10; ii++) hasSalinity[ii] = false;
                            for (int ii = 0; ii < 10; ii++) hasTemperature[ii] = false;
                            if (watphy.length > 4) {

                                noRecords += watphy.length;
                                //printDebug("noRecords = " + noRecords);

                                // find separate profiles, and whether the profile has salinity
                                numProfiles = 0;
                                profileStart[numProfiles] = 0;
                                printDebug(" k = " + 0 +
                                    ", spldep = " + watphy[0].getSpldep() +
                                    ", temperature = *" +
                                    watphy[0].getTemperature("") + "*" +
                                    ", salinity = *" +
                                    watphy[0].getSalinity("") + "*");

                                if (!"".equals(watphy[0].getSalinity(""))) {
                                    hasSalinity[numProfiles] = true;
                                } // if (watphy[k].getSalinity() != null)
                                if (!"".equals(watphy[0].getTemperature(""))) {
                                    hasTemperature[numProfiles] = true;
                                } // if (watphy[k].getSalinity() != null)
                                for (int k = 1; k < watphy.length; k++) {
                                    //printDebug(" k = " + k +
                                    //    ", spldep = " + watphy[k].getSpldep() +
                                    //    ", temperature = *" +
                                    //    watphy[k].getTemperature("") + "*" +
                                    //    ", salinity = *" +
                                    //    watphy[k].getSalinity("") + "*");
                                    if (watphy[k].getSpldep() < watphy[k-1].getSpldep()) {
                                        //profileEnd[numProfiles] = k-1;
                                        numProfiles++;
                                        profileStart[numProfiles] = k;
                                        printDebug("numProfiles = " + numProfiles);
                                    } // if (watphy[k].getSpldep() < watphy[k-1].getSpldep())
                                    if (!"".equals(watphy[k].getSalinity(""))) {
                                        hasSalinity[numProfiles] = true;
                                    } // if (watphy[k].getSalinity() != null)
                                    if (!"".equals(watphy[k].getTemperature(""))) {
                                        hasTemperature[numProfiles] = true;
                                    } // if (watphy[k].getSalinity() != null)
                                } // for (int k = 0; k < watphy.length; k++)
                                numProfiles++;
                                profileStart[numProfiles] = watphy.length;
                                printDebug("numProfiles = " + numProfiles);

                                // do for each profile
                                for (int l = 0; l < numProfiles; l++) {

                                    if (hasTemperature[l]) {

                                        // stuff for logging
                                        Timestamp dateT = watphy[profileStart[l]].getSpldattim();
                                        maxDate = ec.max(maxDate, dateT);
                                        minDate = ec.min(minDate, dateT);

                                        // was there a surface value
                                        if ((watphy[profileStart[l]].getSpldep() < 1f) &&
                                            !"".equals(watphy[profileStart[l]].getTemperature(""))) {
                                            numVals++;
                                            float temp = watphy[profileStart[l]].getTemperature();
                                            sumVals += temp;
                                            sumSqVals += temp * temp;
                                            printDebug("temp = " + temp +
                                                ", numVals = " + numVals +
                                                ", sumVals = " + sumVals +
                                                ", sumSqVals = " + sumSqVals);
                                        } // if (watphy[profileStart[l]].getSpldep() <= 1f)

                                        // sort out the subdes
                                        String subdes = watphy[profileStart[l]].getSubdes();
                                        if ("".equals(subdes)) subdes = "OSD";
                                        if ("BOTTL".equals(subdes)) subdes = "OSD";
                                        if ("DISCR".equals(subdes)) subdes = "OSD";
                                        if ("CONTI".equals(subdes)) subdes = "CTD";
                                        if ("DPFL".equals(subdes)) subdes = "PFL";
                                        //System.out.println(thisClass + ": *" +
                                        //    watphy[profileStart[l]].getSubdes() +
                                        //    "*" + subdes + "*");

                                        // write the profile to file
                                        numTotalProfiles++;
                                        ec.writeFileLine(ofilePRF, province + ", " +
                                            "Profile" + numTotalProfiles + ", " +
                                            stations[j].getStationId() + ", " +
                                            stations[j].getDateStart("").substring(0,10) + ", " +
                                            subdes + ", " +
                                            gridNum + ", " +
                                            -stations[j].getLatitude() + ", " +
                                            stations[j].getLongitude());
                                        if (hasSalinity[l]) {
                                            numCTDprofiles++;
                                        } else {
                                            numXBTprofiles++;
                                        } // if (hasSalinity)
                                        printDebug("start = " + profileStart[l] +
                                            ", end = " + profileStart[l+1]);
                                        for (int k = profileStart[l]; k < profileStart[l+1]; k++) {
                                            // only write records that has data
                                            if (!"".equals(watphy[k].getTemperature("")) ||
                                                !"".equals(watphy[k].getSalinity(""))) {
                                                StringBuffer text = new StringBuffer(
                                                    watphy[k].getSpldep("") + ", " +
                                                    watphy[k].getTemperature(""));
                                                if (hasSalinity[l]) {
                                                    text.append(", " + watphy[k].getSalinity("") + ", " +
                                                        getSoundVelocity(watphy[k].getSpldep(),
                                                            watphy[k].getTemperature(),
                                                            watphy[k].getSalinity()));
                                                } // if (hasSalinity)
                                                ec.writeFileLine(ofilePRF,text.toString());
                                            } // if (!"".equals(watphy[k].getTemperature("") ||
                                        } // for (int k = profileStart[l]; i < profileStart[l]+1; k++)
                                    } // for (int l = 0; l < numProfiles; l++)

                                } // if (hasTemperature[l])

                            } // if (watphy.length > 1)

                        } // for (int j = 0; j < stations.length; j++)

                        // calculate statistics
                        float average = sumVals;
                        float stdev = 0;
                        if (numVals > 1) {
                            average = sumVals / (float)numVals;
                            stdev = (sumSqVals - sumVals*sumVals/numVals)/(numVals-1f);
                        } // if (numVals > 1)

                        // write summary file
                        ec.writeFileLine(ofileSUM, gridNum + ", " + province + ", " +
    //                        provDescription + ", " +
                            month + "," + gridLat + ", " + gridLon + ", " +
                            numXBTprofiles + ", " + numCTDprofiles + ", " +
                            ec.frm(average,8,2) + ", " + ec.frm(stdev,8,2));

                    } // for (int month

                } // for (float lon

            } // for (float lat


        } // for (int i = 0; i < numGrids; i++)

        ec.closeFile(ifile);
        ec.closeFile(ofilePRF);
        ec.closeFile(ofileSUM);

        Timestamp endJDate = new Timestamp(new Date().getTime());       //ub04
        updateLogUsers(log, ec.timeDiff(endJDate, endQDate));           //ub04



/*
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
        if (dbg) System.out.println("sc.HOST_ST = " + sc.HOST_ST);

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

        if (ec.getHost().equals(sc.HOST)) {
            ec.submitJob("chmod 644 " + rootPath + fileName);
        } //if (ec.getHost().equals(sc.HOST)) {

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

        if (test) System.out.println("end = " + new java.util.Date());
*/

    } // ExtractMRNPhysNutDataIMT(String args[])


    /**
     * get the station records from the database
     */
    MrnStation[] getStationRecords() {

        // contruct the where clause
        String where =
            MrnStation.LATITUDE + " between " +
                latitudeNorth + " and " + latitudeSouth + " and " +
            MrnStation.LONGITUDE + " between " +
                longitudeWest + " and " + longitudeEast + " and " +
            MrnStation.PASSKEY + " is NULL and " +
            " date_part('month'," + MrnStation.DATE_START + ") = " + month;

        String fields =
            MrnStation.SURVEY_ID + "," +
            MrnStation.STATION_ID + "," +
            MrnStation.LATITUDE + "," +
            MrnStation.LONGITUDE + "," +
            MrnStation.DATE_START;

        String order = MrnStation.SURVEY_ID + "," + MrnStation.DATE_START;

        MrnStation[] stations = new MrnStation().get(fields, where, order);
        printDebug(MrnStation.TABLE + ": where = " + where +
            ", \nstations.length = " + stations.length);
        return stations;

    } // getStationRecords


    /**
     * get the data records from the database
     */
    MrnWatphy[] getDataRecords(MrnStation station) {
        String fields = MrnWatphy.SUBDES + "," +
            MrnWatphy.SPLDEP + "," +
            MrnWatphy.TEMPERATURE + "," +
            MrnWatphy.SALINITY + "," +
            MrnWatphy.SOUNDV;
        String where = MrnWatphy.STATION_ID + "='" + station.getStationId() + "'";
        //station.station_id, subdes, spldattim,spldep
        String order = MrnWatphy.SUBDES + "," +
            MrnWatphy.SPLDATTIM + "," +
            MrnWatphy.SPLDEP;
        MrnWatphy watphy[] = new MrnWatphy().get(fields, where, order);
        return watphy;

    } // getDataRecords


    /**
     * calculate the sound velocity
     */
    float getSoundVelocity(float po, float t, float s) {
        //PROGRAM TRYIT
        //SNDVEL = SVEL (40.0, 40.0, 10000.0)
        //WRITE (6, *) 'SNDVEL = ', SNDVEL
        //STOP
        //

        //      REAL FUNCTION SVEL (T, S, PO)
        //C +--------------------------------------------------+--------------+--------+
        //C | FUNCTION:..... svel                              | FORTRAN PRGM | 930308 |
        //C +--------------------------------------------------+--------------+--------+
        //C | PROGRAMMER: .. Ursula von St. Ange                                       |
        //C +--------------------------------------------------------------------------+
        //C | Description                                                              |
        //C | ===========                                                              |
        //C |  This function calculates the sound speed (velocity) in seawater         |
        //C |  according to Millero 1977, JASA, 62, 1129-1135                          |
        //C |  Units:                                                                  |
        //C |        Pressure (depth)       PO       decibars                          |
        //C |        Temperature            T        deg Celcius (IPTS-68)             |
        //C |        Salinity               S        (PSS-78)                          |
        //C |        Sound velocity         SVel     meters/second                     |
        //C |  CheckValue: SVel = 1731.995 m/s, S = 40, T = 40 deg C, P = 10000 DBar   |
        //C +--------------------------------------------------------------------------+
        //C | History                                                                  |
        //C | =======                                                                  |
        //C | 921204  Ursula von St Ange   Create program                              |
        //C | 930308  Neville Paynter      Move to fred                                |
        //C +--------------------------------------------------------------------------+
        //C  called by  MEANS    FORTRAN sadlib txtlib
        //C
        //  EQUIVALENCE (A0,B0,C0), (A1,B1,C1), (A2,C2), (A3,C3)
        //C
        //C SCALE PRESSURE TO BARS
        //      P = PO/10.
        //      SR = SQRT(ABS(S))
        //C S**2 TERM
        //      D = 1.727E-3 - 7.9836E-6*P
        //C S**3/2 TERM
        //      B1 = 7.3637E-5 + 1.7945E-7*T
        //      B0 = -1.922E-2 - 4.42E-5*T
        //      B = B0 + B1*P
        //C S**1 TERM
        //      A3 = (-3.389E-13*T + 6.649E-12)*T + 1.100E-10
        //      A2 = ((7.988E-12*T - 1.6002E-10)*T + 9.1041E-9)*T - 3.9064E-7
        //      A1 = (((-2.0122E-10*T + 1.0507E-8)*T - 6.4885E-8)*T -
        //     +     1.2580E-5)*T + 9.4742E-5
        //      A0 = (((-3.21E-8*T + 2.006E-6)*T + 7.164E-5)*T - 1.262E-2)*T +
        //     +     1.389
        //      A = ((A3*P + A2)*P + A1)*P + A0
        //C S**0 TERM
        //      C3 = (-2.3643E-12*T + 3.8504E-10)*T - 9.7729E-9
        //      C2 = (((1.0405E-12*T - 2.5335E-10)*T + 2.5974E-8)*T -
        //     +     1.7107E-6)*T + 3.1260E-5
        //      C1 = (((-6.1185E-10*T + 1.3621E-7)*T - 8.1788E-6)*T +
        //     +     6.8982E-4)*T + 0.153563
        //      C0 = ((((3.1464E-9*T - 1.47800E-6)*T + 3.3420E-4)*T -
        //     +     5.80852E-2)*T + 5.03711)*T + 1402.388
        //      C = ((C3*P + C2)*P + C1)*P + C0
        //C SOUND SPEED RETURN
        //      SVEL = C + (A + B*SR + D*S)*S
        //      RETURN
        //      END

        //scale pressure to bars
        float p = po/10f;
        double sr = Math.sqrt(Math.abs(s));

        // s**2 term
        double d = 1.727e-3 - 7.9836E-6*p;

        //s**3/2 term
        double b1 = 7.3637e-5 + 1.7945e-7*t;
        double b0 = -1.922e-2 - 4.42e-5*t;
        double b = b0 + b1*p;

        //s**1 term
        double a3 = (-3.389e-13*t + 6.649e-12)*t + 1.100e-10;
        double a2 = ((7.988e-12*t - 1.6002e-10)*t + 9.1041e-9)*t - 3.9064e-7;
        double a1 = (((-2.0122e-10*t + 1.0507e-8)*t - 6.4885e-8)*t -
                    1.2580e-5)*t + 9.4742e-5;
        double a0 = (((-3.21e-8*t + 2.006e-6)*t + 7.164e-5)*t - 1.262e-2)*t + 1.389;
        double a = ((a3*p + a2)*p + a1)*p + a0;

        //s**0 term
        double c3 = (-2.3643e-12*t + 3.8504e-10)*t - 9.7729e-9;
        double c2 = (((1.0405e-12*t - 2.5335e-10)*t + 2.5974e-8)*t -
                    1.7107e-6)*t + 3.1260e-5;
        double c1 = (((-6.1185e-10*t + 1.3621e-7)*t - 8.1788e-6)*t +
                    6.8982e-4)*t + 0.153563;
        double c0 = ((((3.1464e-9*t - 1.47800e-6)*t + 3.3420e-4)*t -
                    5.80852e-2)*t + 5.03711)*t + 1402.388;
        double c = ((c3*p + c2)*p + c1)*p + c0;

        //sound speed return
        float svel = (float)(c + (a + b*sr + d*s)*s);
        return svel;

    } // getSoundVelocity


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
        logs.setSurveyId(" ");                                          //ub04
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
     * print a debug line
     */
    void printDebug(String text) {
        if (dbg) System.out.println(thisClass + ": " + text);
    } // printDebug


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            ExtractMRNPhysNutDataIMT local =
                new ExtractMRNPhysNutDataIMT(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractMRNPhysNutDataIMT", "Constructor", "");
        } // try-catch
        // close the connection to the database
        common2.DbAccessC.close();

    } // main


 }// class ExtractMRNPhysNutDataIMT
