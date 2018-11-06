package sadco;

//import oracle.html.*;
import java.util.*;
import java.sql.*;
import java.io.*;

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
 */
public class ExtractMRNPhysNutDataTest { //extends CompoundItem {

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

    // constant for extracting watphy records
    int             spldepInterval = 500;

    /**
     * Main constructor ExtractMRNPhysNutDataTest
     * @param   args   String array of arguments as from form or URL
     */
    public ExtractMRNPhysNutDataTest(String args[])  {

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
        //if (ec.getHost().startsWith("morph")) {
        //if (ec.getHost().startsWith("fred")) {
        //    rootPath = sc.MORPH + userid + "/";
        //} else {
        //    rootPath = sc.LOCAL + "marine/";
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIR+ userid + "/";
        } else {
            rootPath = sc.LOCALDIR;
        } // if host

        // new filename
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
        // create the 'processing' file
        String dummyFile2 = rootPath + fileName + ".processing";
        ec.createNewFile(dummyFile2);


        // Open the data file and write an empty line for header later
        ec.deleteOldFile(rootPath + fileName);
        try {
            ofile = new RandomAccessFile(rootPath + fileName, "rw");
        } catch (Exception e) {
            ec.processError(e, thisClass, "constructor", "File Open Error");
        }  // try-catch

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

        Timestamp date0 = new Timestamp(new java.util.Date().getTime());

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
        try {
            ofile.close();
        } catch (Exception e) {
            ec.processError(e, thisClass, "constructor", "close file Error");
        } // try-catch

        Timestamp date2 = new Timestamp(new java.util.Date().getTime());
        updateLogUsers(userid, ec.timeDiff(date2, date0));


        // delete the .processing file
        ec.deleteOldFile(dummyFile2);
        // create the dummy file for Progress purposes ......
        ec.createNewFile(dummyFile);

    } // ExtractMRNPhysNutDataTest(String args[])


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        if (dbg) for (int i = 0; i < args.length; i++) {
            System.out.println("<br>" + args[i]);
        } // for i

        // special for testing
        String check = ec.getArgument(args, "pinterval");
        if (!"".equals(check)) {
            spldepInterval = Integer.parseInt(check);
        } // if (!"".equals(check))
        System.out.println("spldepInterval = " + spldepInterval);

        // normal stuff
        sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        reqNumber = ec.getArgument(args, sc.REQNUMBER);
        fileName = ec.getArgument(args, sc.FILENAME);
        extrType = ec.getArgument(args, sc.EXTRTYPE);

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
        if (dbg) System.out.println("\n\n<br>" + MrnStation.TABLE +
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
            fields = "max(" + MrnWatphy.SPLDEP + ") as " + MrnWatphy.SPLDEP;
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

                    printRecords(stations[i], watphy[j], planam, watnut,
                        watchem1, watchl);
                    noRecords += 1;
                } // for (int j = 0; j < watphys.length; j++)

            } // for int k

        } // for int i
        System.gc();

        return noRecords;
    } //


    /**
     * Print the current marine data records
     * @param   station   MrnStation data
     * @param   watphy    MrnWatphy data
     * @param   planam    MrnPlanam data
     * @param   watnut    MrnWatnut data
     * @param   watchem1  MrnWatchem1 data
     * @param   watchl    MrnWatchl data
     */
    void printRecords(MrnStation station, MrnWatphy watphy, MrnPlanam[] planam,
            MrnWatnut[] watnut, MrnWatchem1[] watchem1, MrnWatchl[] watchl) {

        // get the variables for records that may be null
        String planamName = "";
        if (planam.length > 0) {
            planamName = planam[0].getName("");
            //ec.writeFileLine(ofile, "planam:  " + planam[0].toString());
        } //
        if (dbg) System.out.println("<br>planamName = " + planamName);

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
            ec.frm(ec.nullToNines(station.getMaxSpldep(),9999.0f),10,2) + ",\"" +
            ec.frm(station.getStnnam(""),10) + "\",\"" +
            ec.frm(station.getSurveyId(),9) + "\",\"" +
            ec.frm(planamName,20) + "\",\"" +
            ec.frm(watphy.getSubdes(""),5) + "\",";

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
            ec.frm(noRecords,13) + " " + surveyId;

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
    void ColumnHeadings() {
        // method to write headings of estraxted data
        //String head = " LATDEG ,  LONDEG, YEAR, MONTH, DEPTH, DAY,  TIME,    " +
        //    "TMP,  SALIN, DISOX,    NO2,    NO3,    PO4,    PTOT,    " +
        //    "SIO3,    PH, SOUNDV,    CHLA, STATION ID,     STNDEP,MXSPLDEP, " +
        //    "STN NAM,     SURVEYID,   PLANAM,                INSTR";

        String head = " LATDEG ,  LONDEG, YEAR, MONTH, DEPTH,DAY,    TIME,   TMP,    "+
                      "SALIN,   DISOX,      NO2,      NO3,      PO4,      PTOT,      "+
                      "SIO3,      PH,   SOUNDV,      CHLA,    STATION ID,    STNDEP,  "+
                      "MXSPLDEP,     STN NAM,   SURVEYID,                PLANAM,  INSTR";


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
            "  STN NAM   =   STATION NAME AS GIVEN BY CONTRIBUTOR\n" +
            "  SURVEYID  =   UNIQUE SADCO SURVEY IDENTIFIER\n" +
            "  PLANAM    =   PLATFORM NAME\n" +
            "  INSTR     =   MEASURING INSTRUMENT";
         ec.writeFileLine(ofile,legend);
    } // printLegend


    /**
     * print an error message if inputs not OK
     */
    void printErrorMessage() {
    } // printErrorMessage


    /**
     * Update the LOG_USERS table for this extraction.
     */
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

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            ExtractMRNPhysNutDataTest local =
                new ExtractMRNPhysNutDataTest(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractMRNPhysNutDataTest", "Constructor", "");
        } // try-catch
        // close the connection to the database
        common2.DbAccessC.close();

    } // main


 }// class ExtractMRNPhysNutDataTest
