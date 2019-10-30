package sadco;

//import common2.sadinv.Queue;
import java.util.Vector;
import java.sql.Timestamp;
import java.io.RandomAccessFile;

/**
 * Does the actual extraction of the requested data.
 *
 * SadcoVOS
 * screen.equals("extract")
 * version.equals("doextract")
 *
 * @author  001115 - SIT Group
 * @version
 * 001115 - Ursula von St Ange - create class                           <br>
 * 021123 - Ursula von St Ange - add link to products page              <br>
 * 020528 - Ursula von St Ange - update for non-frame menus             <br>
 * 020528 - Ursula von St Ange - update for exception catching          <br>
 * 030828 - Ursula von St Ange - ub01 - speed test - change deltaLat/Lon<br>
 * 030829 - Ursula von St Ange - ub02 - speed test - introduce deltaYear<br>
 * 091214 - Ursula von St Ange - change for inventory user (ub01)       <br>
 * 100105 - Ursula von St Ange - change for queuing of job (ub02)       <br>
 */
public class ExtractVOSDataP { //extends CompoundItem {

    boolean dbg = false;
    //boolean dbg = true;
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
    String startDate;
    String endDate;
    float latitudeNorth;
    float latitudeSouth;
    float longitudeWest;
    float longitudeEast;
    Timestamp dateStart;
    Timestamp dateEnd;
    String userType = "";                                               //ub01

    // variable from database
    int             noRecords = 0;
    long            headerPos;

    //float deltaLat = 1;                     // ub01
    //float deltaLon = .5f;                   // ub01
    float deltaLat = 5;                     // ub01
    float deltaLon = 5;                     // ub01
    //int deltaYear = 5;                      // ub02
    int deltaYear = 1;                      // ub02

    // minimum and maximum's
    float           maxLatitude = -20.0f;
    float           minLatitude =  99.0f;
    float           maxLongitude = -40.0f;
    float           minLongitude =  99.0f;
    Timestamp       minDate = Timestamp.valueOf("2100-01-01 00:00:00.0");
    Timestamp       maxDate  = Timestamp.valueOf("1900-01-01 00:00:00.0");


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ExtractVOSDataP (String args[]) {

        try {
            ExtractVOSDataPActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ExtractVOSDataP constructor


    /**
     * Does the actual work
     * @param args the string array of url-type name-value parameter pairs
     */
    void ExtractVOSDataPActual(String args[]) {

        // Get the arguments
        getArgParms(args);

        // extract the user record from db
        if (dbg) System.out.println("<br>ExtractVOSDataPActual: sessioncode = " + sessionCode);
        String userid = "user";
        if (!"".equals(sessionCode )) {
//            LogSessions session = new LogSessions();
//            session.setCode(sessionCode);
//            LogSessions sessions[] = session.get();
//            if (dbg) System.out.println("<br>ExtractVOSDataPActual: session.getSelStr = " + session.getSelStr());
//            userid = sessions[0].getUserid();
            userid = "ursula";
        } // if (!"".equals(sessionCode ))

        // get the correct PATH NAME - used while debugging
        String rootPath = "";
        if (dbg) System.out.println("<br>" + thisClass + ": host = " +
            ec.getHost() + ", userid = " + userid);
        //if (ec.getHost().equals("morph")) {
        //    rootPath = sc.MORPH + userid + "/";
        //} else {
        //    rootPath = sc.LOCAL + "vos/";
        //} // if host
        //System.out.println(rootPath);

        //System.out.println("ec.getHost() = " + ec.getHost());
        //System.out.println("sc.HOST = " + sc.HOST);

//        if (ec.getHost().equals(sc.HOST) || ec.getHost().equals(sc.HOST_ST)) {
        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                rootPath = sc.HOSTDIR + "inv_user/";                    //ub01
            } else {                                                    //ub01
                rootPath = sc.HOSTDIR;                   //ub01
            } // if ("0".equals(userType))                              //ub01
            //rootPath = sc.HOSTDIR;;
        } else {
            rootPath = sc.LOCALDIR;
        } // if host

        // new filename, but keep the original file name                //ub02
        fileNameIn = fileName;                                          //ub02
        if (!reqNumber.equals("")) {
            fileName = reqNumber + "_" + fileName;
        } // if
        fileName += ".dbv";
        if (dbg) System.out.println("<br>" + thisClass + ": " + fileName);


        // delete an old dummy file for Progress purposes ......
        String dummyFile = rootPath + fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        // create .queued file for Progress purposes ......             //ub02
        String dummyFile2 = rootPath + fileName + ".queued";            //ub02
//        ec.createNewFile(dummyFile2);                                   //ub02

        // queue the extraction                                         //ub02
//        sadinv.Queue queue = new sadinv.Queue(userid, thisClass +" "+fileNameIn);     //ub02
//        queue.enQueue();                                                //ub02
//        queue.wait4Turn();                                              //ub02

        // delete .queued file, and create .progressing file            //ub02
        ec.deleteOldFile(dummyFile2);                                   //ub02

        // create the 'processing' file
        dummyFile2 = rootPath + fileName + ".processing";
        ec.createNewFile(dummyFile2);


        // Open the data file and write an empty line for header later
        ec.deleteOldFile(rootPath + fileName);
        try {
            ofile = new RandomAccessFile(rootPath + fileName, "rw");
        } catch (Exception e) {
            System.out.println("<br>" + thisClass + ":File Open Error: " +
                e.getMessage());
            e.printStackTrace();
        }  // END try {

        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob("chmod 644 " + rootPath + fileName);
        } //if (ec.getHost().startsWith(sc.HOST)) {

        try {
            ofile.writeBytes(" ");
            headerPos = ofile.getFilePointer();
        } catch (Exception e) {
            System.out.println("<br>" + thisClass + ": Header Pos Error: " +
                e.getMessage());
            e.printStackTrace();
        }  // END try {
        ec.writeFileLine(ofile, ec.frm(" ", 78));

        ColumHeadings();

        // submit the query to the database

        // get start and end values for loops
        if (dbg) System.out.println("<br>" + thisClass + ":1 :" +
            startDate + " " + endDate + " " +
            latitudeNorth + " " + latitudeSouth + " " +
            longitudeWest + " " + longitudeEast);
        int startYear = Integer.parseInt(startDate.substring(0,4));
        int endYear   = Integer.parseInt(endDate.substring(0,4));

        float startLat  = ec.floor(latitudeNorth/deltaLat, 0) * deltaLat;
        float endLat    = ec.floor(latitudeSouth/deltaLat, 0) * deltaLat;
        float startLon  = ec.floor(longitudeWest/deltaLon, 0) * deltaLon;
        float endLon    = ec.floor(longitudeEast/deltaLon, 0) * deltaLon;
        if (dbg) System.out.println("<br>:" + thisClass + "2 : " +
            startYear + " " + endYear + " " +
            startLat + " " + endLat + " " + startLon + " " + endLon);

        Timestamp date0 = new Timestamp(new java.util.Date().getTime());
        // subdivide the big block into yearly,
        // deltaLat * deltaLon degree blocks
        for (int year = startYear; year <= endYear; year += deltaYear) {    // ub02
            //System.out.println("year = " + year);
            int startYr = year;                                             // ub02
            if (year == startYear) { startYr = startYear; }                 // ub02
            int endYr = year + deltaYear - 1;                               // ub02
            if (endYr > endYear) { endYr = endYear; }                       // ub02

            // get start and end year for extraction
            String startD = String.valueOf(startYr) + "-01-01";             // ub02
            if (year == startYear) { startD = startDate; }
            String endD = String.valueOf(endYr) + "-12-31";                 // ub02
            if (year == endYear) { endD = endDate; }
            //if (dbg) System.out.println("<br>" + thisClass + ": startD, endD =
            //    " + startD + " " + endD);

            for (float lat = startLat; lat <= endLat; lat += deltaLat) {
                // get the start and end latitudes
                float startLt = lat;
                if (lat == startLat) { startLt = latitudeNorth; }
                float endLt = lat + deltaLat - .00001f;
                if ((lat+deltaLat) > latitudeSouth) { endLt = latitudeSouth; }
                //if (dbg) System.out.println("<br>" + thisClass + ": startLt, endLt = " + startLt +
                //    " " + endLt);

                for (float lon = startLon; lon <= endLon; lon += deltaLon) {
                    // get the start and end longitudes
                    float  startLn = lon;
                    if (lon == startLon) { startLn = longitudeWest; }
                    float  endLn = lon + deltaLon - .00001f;
                    if ((lon+deltaLon) > longitudeEast) { endLn = longitudeEast; }
                    //if (dbg) System.out.println("<br>" + thisClass +
                    //    ": startLn, endLn = " + startLn + " " + endLn);
                    //if (dbg) System.out.println("<br>" + thisClass +
                    //    ": start, end = " + startD + " " + endD + " " +
                    //    startLt + " " + endLt + " " +
                    //    startLn + " " + endLn);

                    // create the where clause based on the Date,
                    // Longitude and Latitude
                    String where = "(date_time BETWEEN " +
                        Tables.getDateFormat(startD + " 00:00:00") + " and " +
                        Tables.getDateFormat(endD + " 23:59:59") + ")" +
                        " and (latitude BETWEEN " + ec.frm(startLt,10,5) +
                        " and " + ec.frm(endLt,10,5) + ")" +
                        " and ( longitude BETWEEN " + ec.frm(startLn,10,5) +
                        " and " + ec.frm(endLn,10,5) + ")";
                    //if (dbg) System.out.println("<br>" + thisClass +
                    //    ": where = " + where);
                    Timestamp date1 = new Timestamp(new java.util.Date().getTime());
                    //if (dbg) System.out.println("<br>" + thisClass +
                    //    ": Date1 = " + date1);

                    // only at most 1 degree square - blocks were never fall
                    // over boundaries of 0 or 70
                    int recs = 0;
                    //recs = printRecords(new VosArch(), where);

                    if (year >= 1960) {
                        //if (dbg) System.out.println("<br>" + thisClass +
                        //    ": year ge 1960: =" + year);
                        if ((endLn < 0) || (startLn >= 50)) {
                            if (dbg) System.out.println("<br>ExtractVOSDataPActual: VosArch");
                            recs = printRecords(new VosArch(), where);
                            //printRecords(new VosArch().getV(where), vosData.db);
                        } else {
                            if (dbg) System.out.println("<br>ExtractVOSDataPActual: VosMain");
                            recs = printRecords(new VosMain(), where);
                            //getData(new VosMain(), where);
                        } // if ((endLn < 0) || (startLn >= 50))
                    } else { // year < 1960
                        if ((endLn < 0) || (startLn >= 50)) {
                            if (dbg) System.out.println("<br>ExtractVOSDataPActual: VosArch2");
                            recs = printRecords(new VosArch2(), where);
                            //getData(new VosArch2(), where);
                        } else {
                            if (dbg) System.out.println("<br>ExtractVOSDataPActual: VosMain2");
                            recs = printRecords(new VosMain2(), where);
                            //getData(new VosMain2(), where);
                        } // if ((endLn < 0) || (startLn >= 50))
                    } // if (year >= 1960)

                    Timestamp date2 =
                        new Timestamp(new java.util.Date().getTime());
                    if (dbg) System.out.println("<br>" + thisClass + ": " +
                        startD + " " + endD +
                        ec.frm(startLt,6,1) + ec.frm(endLt,10,5) +
                        ec.frm(startLn,7,1) + ec.frm(endLn,11,5) +
                        ec.frm(recs,3) + " " +
                        ec.timeDiff(date2, date1) + "/" +
                        ec.timeDiff(date2, date0));

                } // for lon;
            } // for lat
        } // end for year

        printLegend();
        printHeader();

        try {
            ofile.close();
        } catch (Exception e) {
            System.out.println("<br>" + thisClass + ": close file Error: " +
                e.getMessage());
            e.printStackTrace();
        }  // END try {

        Timestamp date2 = new Timestamp(new java.util.Date().getTime());
//        updateLogUsers(userid, ec.timeDiff(date2, date0));

        // delete the .processing file
        ec.deleteOldFile(dummyFile2);
        // create the dummy file for Progress purposes ......
        ec.createNewFile(dummyFile);

//        // dequeue extraction                                           //ub02
//        queue.deQueue();                                                //ub02

    } // ExtractVOSDataP(String args[])


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
        startDate = ec.getArgument(args, sc.STARTDATE);
        endDate = ec.getArgument(args, sc.ENDDATE);
        userType = ec.getArgument(args,SadConstants.USERTYPE);          //ub01

        latitudeNorth = new Float(ec.getArgument(args,sc.LATITUDENORTH)).floatValue();
        latitudeSouth = new Float(ec.getArgument(args,sc.LATITUDESOUTH)).floatValue();
        longitudeWest = new Float(ec.getArgument(args,sc.LONGITUDEWEST)).floatValue();
        longitudeEast = new Float(ec.getArgument(args,sc.LONGITUDEEAST)).floatValue();

        // correct end latitudes and longitudes
        latitudeSouth -= .00001f;
        longitudeEast -= .00001f;

        // timestamp in format yyyy-mm-dd hh:mm:ss.fffffffff
        dateStart = Timestamp.valueOf(startDate + " 00:00:00.00");
        dateEnd = Timestamp.valueOf(endDate + " 00:00:00.00");

    } // getArgParms


    /**
     * READ INFO from the Array
     * @param   vosData1   VosTable array of vos data
     * @param   where   where clause of select string
     * @return number of records
     */
    int printRecords(VosTable vosData1, String where) {

        // get the data first
        Vector result = vosData1.getV(where, VosTable.DATE_TIME);
        common2.DbAccessC db = vosData1.db;

        // loop through all returned records
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            if (dbg) System.out.println("<br>printRecords: new VosTable");
            VosTable vosData = new VosTable();
            vosData.setLatitude           ((String)
                row.elementAt(db.getColNumber(VosTable.LATITUDE)));
            vosData.setLongitude          ((String)
                row.elementAt(db.getColNumber(VosTable.LONGITUDE)));
            vosData.setDateTime           ((String)
                row.elementAt(db.getColNumber(VosTable.DATE_TIME)));
            vosData.setSurfaceTemperature ((String)
                row.elementAt(db.getColNumber(VosTable.SURFACE_TEMPERATURE)));
            vosData.setAtmosphericPressure((String)
                row.elementAt(db.getColNumber(VosTable.ATMOSPHERIC_PRESSURE)));
            vosData.setSwellHeight        ((String)
                row.elementAt(db.getColNumber(VosTable.SWELL_HEIGHT)));
            vosData.setWindSpeed          ((String)
                row.elementAt(db.getColNumber(VosTable.WIND_SPEED)));
            vosData.setDrybulb            ((String)
                row.elementAt(db.getColNumber(VosTable.DRYBULB)));
            vosData.setWetbulb            ((String)
                row.elementAt(db.getColNumber(VosTable.WETBULB)));
            vosData.setDewpoint           ((String)
                row.elementAt(db.getColNumber(VosTable.DEWPOINT)));
            vosData.setWaveHeight         ((String)
                row.elementAt(db.getColNumber(VosTable.WAVE_HEIGHT)));
            vosData.setCloudAmount        ((String)
                row.elementAt(db.getColNumber(VosTable.CLOUD_AMOUNT)));
            vosData.setWindDirection      ((String)
                row.elementAt(db.getColNumber(VosTable.WIND_DIRECTION)));
            vosData.setSwellDirection     ((String)
                row.elementAt(db.getColNumber(VosTable.SWELL_DIRECTION)));
            vosData.setSwellPeriod        ((String)
                row.elementAt(db.getColNumber(VosTable.SWELL_PERIOD)));
            vosData.setWavePeriod         ((String)
                row.elementAt(db.getColNumber(VosTable.WAVE_PERIOD)));

            Timestamp dateT = vosData.getDateTime();
            String    year = dateT.toString().substring(0,4);
            String    month = dateT.toString().substring(5,7);
            String    day = dateT.toString().substring(8,10);
            String    ftime = dateT.toString().substring(10,16);

            // get actual min and max ranges
            maxLatitude = ec.max(maxLatitude, vosData.getLatitude());
            minLatitude = ec.min(minLatitude, vosData.getLatitude());
            maxLongitude = ec.max(maxLongitude, vosData.getLongitude());
            minLongitude = ec.min(minLongitude, vosData.getLongitude());
            maxDate = ec.max(maxDate, dateT);
            minDate = ec.min(minDate, dateT);

            //if (dbg) {
            //    String tLine = dateT.toString() + " " + year + " " + month + " " +
            //        day + " " + ftime;
            //    System.out.println("<br>" + thisClass +  ".printRecords: " +
            //    tLine);
            //} // if (dbg)


            // Print the BODY OF THE FILE
            String line =
                ec.frm(vosData.getLatitude(),8,3) + "," +
                ec.frm(vosData.getLongitude(),8,3) + ",   " +
                    year + ",  " + month+" " + ",    0, " + " "+day + ", \"" + ftime.trim() + "\"," +
                ec.frm(ec.nullToNines(
                    vosData.getSurfaceTemperature(),9999.0f),7,1) + "," +
                ec.frm(ec.nullToNines(
                    vosData.getAtmosphericPressure(),9999.0f),7,1) + "," +
                ec.frm(ec.nullToNines(
                    vosData.getSwellHeight(),9999.0f),7,1) + "," +
                ec.frm(ec.nullToNines(
                    vosData.getWindSpeed(),9999.0f),7,1) + "," +
                ec.frm(ec.nullToNines(
                    vosData.getDrybulb(),9999.0f),7,1) + "," +
                ec.frm(ec.nullToNines(
                    vosData.getWetbulb(),9999.0f),7,1) + "," +
                ec.frm(ec.nullToNines(
                    vosData.getDewpoint(),9999.0f),7,1) + "," +
                ec.frm(ec.nullToNines(
                    vosData.getWaveHeight(),9999.0f),7,1) + ",    " +
                       ec.nullToNines(vosData.getCloudAmount(), "9") + ",  " +
                ec.frm(ec.nullToNines(
                    (int)vosData.getWindDirection(),9999),4) + "," +
                ec.frm(ec.nullToNines(
                    (int)vosData.getSwellDirection(),9999),4) + "," +
                ec.frm(ec.nullToNines(
                    (int)vosData.getSwellPeriod(),9999),4) + "," +
                ec.frm(ec.nullToNines(
                    (int)vosData.getWavePeriod(),9999),4) + ",";

            //if (dbg) System.out.println("<br>" + thisClass + ".printRecords: " +
            //    line1);

            //if (dbg) System.out.println("<br>" + thisClass + ".printRecords: " +
            //    line);

            ec.writeFileLine(ofile,line);
            noRecords += 1;  //will just count records as run through loop
         } // for i

         // method for leggend

         return result.size();
    } // printRecords


    /**
     * Print the FIRST LINE on the form
     */
    void printHeader() {
        //System.out.println("printHeader");
        // MARCLIM  28  34  28  33  1960  2000 WEATHER        92550
        if (noRecords == 0) {
            minLatitude = latitudeNorth;
            maxLatitude = latitudeSouth;
            minLongitude = longitudeWest;
            maxLongitude = longitudeEast;
            minDate = dateStart;
            maxDate = dateEnd;
        } // if (noRecords == 0)

        String line =
            "MARCLIM " +
            ec.frm(minLatitude,6,1) +
            ec.frm(maxLatitude,6,1) +
            ec.frm(minLongitude,6,1) +
            ec.frm(maxLongitude,6,1) + "  " +
            minDate.toString().substring(0,10) + "  " +
            maxDate.toString().substring(0,10) + " WEATHER" +
            ec.frm(noRecords,13);

        if (dbg) System.out.println("<br>" + thisClass + ".printHeader: " + line);
        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: minLatitude = " + minLatitude);
        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: maxLatitude = " + maxLatitude);
        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: minLongitude = " + minLongitude);
        if (dbg) System.out.println("<br>" + thisClass +
            ".printHeader: maxLongitude = " + maxLongitude);

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

    void ColumHeadings() {
        // method to write headings of estraxted data
        String head = " LATDEG ,  LONDEG,  YEAR ,MONTH,DEPTH, DAY,  TIME  , SRFTMP," +
                      "ATPRES ,SWHT   , WINSP , DRYBLB, WETBLB, DEWPNT,  WAVHT,CAMNT," +
                      "WINDIR,SWDIR,SWPER,WPER";

        ec.writeFileLine(ofile,head);
    }

    void printLegend() {
        //System.out.println("printLegend");

         String legend = "\n\n  LATDEG    =   LATITUDE (DEGREES) \n"+
             "  LONDEG    =   LONGITUDE (DEGREES) \n"+
             "  YEAR      =   YEAR IN WHICH RECORDING WAS MADE\n"+
             "  MONTH     =   MONTH IN WHICH RECORDING WAS MADE\n"+
             "  DEPTH     =   DEPTH \n"+
             "  DAY       =   DAY IN WHICH RECORDING WAS MADE \n"+
             "  TIME      =   TIME OF RECORDING \n"+
             "  SRFTMP    =   SURFACE TEMPERATURE (DEGREES CELSIUS)\n" +
             "  ATPRES    =   ATMOSPHERIC PRESSURE (MILLIBARS) \n"+
             "  SWHT      =   SWELL HEIGHT (METERS)\n"+
             "  WINSP     =   WIND SPEED (METER/SEC)\n"+
             "  DRYBLB    =   DRY BULB THERMOMETER TEMP. AIR TEMP (DEGREES CELSIUS) \n"+
             "  WETBLB    =   WET BULB THERMOMETER TEMP (DEGREES CELSIUS) \n"+
             "  DEWPNT    =   DEW POINT (DEGREES CELSIUS)\n"+
             "  WAVHT     =   WAVE HEIGHT (METERS)\n"+
             "  CAMNT     =   ClOUD AMOUNT (EIGHTHS)\n"+
             "  WINDIR    =   WIND DIRECTION (DEGREES FROM TRUE NORTH)\n"+
             "  SWDIR     =   SWELL DIRECTION (DEGREES FROM TRUE NORTH)\n" +
             "  SWPER     =   SWELL PERIOD (SECONDS)\n"+
             "  WPER      =   WAVE PERIOD (SECONDS)";

         //String legend =  ec.frm("Lat",8,3) + "Latitude" +
         //                 ec.frm("Lon",8,3) + "Longitude";

         ec.writeFileLine(ofile,legend);
    }


    /**
     * Update the LOG_USERS table for this extraction.
     */
    void updateLogUsers(String userid, String timeDiff) {
        LogUsers logs = new LogUsers();
        logs.setUserid(userid);
        logs.setDateTime(new Timestamp(new java.util.Date().getTime()));
        logs.setDatabase("MARCLIM");
        logs.setDiscipline("weather");
        logs.setProduct("Extraction");
        logs.setFileName(fileName);
        logs.setLatitudeNorth(minLatitude);
        logs.setLatitudeSouth(maxLatitude);
        logs.setLongitudeWest(minLongitude);
        logs.setLongitudeEast(maxLongitude);
        logs.setDateStart(minDate);
        logs.setDateEnd(maxDate);
        logs.setNumRecords(noRecords);
        logs.setJobDuration(timeDiff);
        boolean success = logs.put();

    } // updateLogUsers



    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            ExtractVOSDataP local =
                new ExtractVOSDataP(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractVOSDataP", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


 }// class ExtractVOSDataP
