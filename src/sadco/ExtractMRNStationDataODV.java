package sadco;

//import oracle.html.*;
import java.util.*;
import java.sql.*;
import java.io.*;

/**
 * Does the actual extraction of the requested data.
 *
 * SadcoMRNApps
 * screen.equals("extract")
 * version.equals("doextract")
 * extrType.equals("station")
 *
 * @author 011001 - SIT Group
 * @version
 * 030925 - Ursula von St Ange - create class from ExtractMRNPhysNutData    <br>
 * 041104 - Ursula von St Ange - for each station, get all watphy records to get,
 *                               a time, then sort station records on that time<br>
 * 121113 - Ursula von St Ange - add etopo2 depth (ub06)                    <br>
 */
public class ExtractMRNStationDataODV {

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

    // inventory details
    String shipName = ".";
    String projectName = ".";
    String cruiseName = ".";
    String institute = ".";


    /**
     * Main constructor ExtractMRNStationDataODV
     * @param   args   String array of arguments as from form or URL
     */
    public ExtractMRNStationDataODV(String args[])  {

        // Get the arguments
        getArgParms(args);

        // extract the user record from db - only if run from website
        String userid = "sadlod";
        if (!"".equals(sessionCode)) {
            LogSessions session = new LogSessions();
            session.setCode(sessionCode);
            LogSessions sessions[] = session.get();
            userid = sessions[0].getUserid();
        } // if (!"".equals(sessionCode))

        if (dbg) System.out.println("<br>userid = " + userid);

        // get the correct PATH NAME - used while debugging
        String rootPath = "";
        if (dbg) System.out.println("<br>" + thisClass + ": host = " +
            ec.getHost());
        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIR+ userid + "/";
        } else {
            rootPath = sc.LOCALDIR + "marine/";
        } // if host

        // new filename
        if (!reqNumber.equals("")) {
            fileName = reqNumber + "_" + fileName;
        } // if
        fileName += ".txt";
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
            ec.submitJob("chmod 666 " + rootPath + fileName);
        } //if (ec.getHost().startsWith(sc.HOST)) {

/*
        try {
            ofile.writeBytes("");
            headerPos = ofile.getFilePointer();
        } catch (Exception e) {
            ec.processError(e, thisClass, "constructor", "Header Pos Error");
        }  // try-catch
        ec.writeFileLine(ofile, ec.frm(" ", 100));
*/
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

//        if (validQuery) {
            MrnStation[] stations = getStationRecords();

            getInventoryDetails(stations);
            printRecords(stations);
            noRecords = stations.length;
//        } // if (validQuery)

        // clean up
        try {
            ofile.close();
        } catch (Exception e) {
            ec.processError(e, thisClass, "constructor", "close file Error");
        } // try-catch

        Timestamp date2 = new Timestamp(new java.util.Date().getTime());
        if (!"".equals(sessionCode)) {
            updateLogUsers(userid, ec.timeDiff(date2, date0));
        } // if (!"".equals(sessionCode))


        // delete the .processing file
        ec.deleteOldFile(dummyFile2);
        // create the dummy file for Progress purposes ......
        ec.createNewFile(dummyFile);

    } // ExtractMRNStationDataODV(String args[])


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
                    "to_date('" + startDate + "','yyyy-mm-dd') and " +
                    "to_date('" + endDate + "','yyyy-mm-dd')";
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
            MrnStation.STNNAM + "," +
            MrnStation.LATITUDE + "," + MrnStation.LONGITUDE + "," +
            MrnStation.DATE_START + "," + MrnStation.DATE_END + "," +
            MrnStation.STNDEP + "," + MrnStation.MAX_SPLDEP + "," +
            MrnStation.PASSKEY;
        String order = MrnStation.SURVEY_ID + "," + MrnStation.DATE_START;
        if (dbg) System.out.println("\n\n<br>" + MrnStation.TABLE +
            ": where = " + where);
        MrnStation[] stations = new MrnStation().get(fields, where, order);
        if (dbg) System.out.println("\n\n<br>" + MrnStation.TABLE +
            ": where = " + where + ", \nstations.length = " + stations.length);

/*
        //for all stations - get date_time
        for (int i = 0; i < stations.length;i++) {

            MrnWatphy[] watphyRecs2 = getWatphyRecords(stations[i].getStationId());
            if (watphyRecs2.length > 0) {
                stations[i].setDateStart(watphyRecs2[0].getSpldattim());
                stations[i].setDateEnd(watphyRecs2[watphyRecs2.length - 1].getSpldattim());
            } // if (watphyRecs2.length > 0)

        } //for (int i = 1; i < stations.length;i++)
*/
        // sort stations according to date/time
        stations = bubbleSort(stations);

        return stations;
    } // getStationRecords


    /**
     * Get Watphy records from Database for the @param stationId
     * @param stationId : argument value from url (string)
     * @return array value watphyRecs
     */
    MrnWatphy[] getWatphyRecords(String stationId) {

        if (dbg) System.out.println("<br>getWatphyRecords");
        String fields = MrnWatphy.SPLDATTIM;
        String where = MrnWatphy.STATION_ID +"='"+ stationId +"'";
        String order = MrnWatphy.SPLDATTIM;

        MrnWatphy[] watphyRecs = new MrnWatphy().get(fields,where,order);

        return watphyRecs;

    } //MrnWatphy[] getWatphyRecords


    /**
     * get the data records from the database
     */
    void getInventoryDetails(MrnStation[] stations) {

        if (!"/".equals(surveyId)) {

            // get inventory details
            String fields = MrnInventory.PLANAM_CODE + "," +
                MrnInventory.INSTIT_CODE + "," +
                MrnInventory.PROJECT_NAME + "," +
                MrnInventory.CRUISE_NAME;
            String where = MrnInventory.SURVEY_ID + "='" + surveyId + "'";
            MrnInventory[] inv = new MrnInventory().get(fields, where, "");
            if (dbg) System.out.println("where = " + where);
            if (dbg) System.out.println("inv.length = " + inv.length);
            if (inv.length > 0) {
                projectName = inv[0].getProjectName("");
                cruiseName = inv[0].getCruiseName("");
            } // if (inv.length > 0)

            // get planam
            fields = MrnPlanam.NAME;
            where = MrnPlanam.CODE + "=" + inv[0].getPlanamCode();
            MrnPlanam[] planam = new MrnPlanam().get(fields, where, "");
            if (dbg) System.out.println("where = " + where);
            if (dbg) System.out.println("planam.length = " + planam.length);
            if (planam.length > 0) { shipName = planam[0].getName(""); }

            // get institute
            fields = MrnInstitutes.NAME;
            where = MrnInstitutes.CODE + "=" + inv[0].getInstitCode();

            MrnInstitutes[] instit = new MrnInstitutes().get(fields, where, "");
            if (dbg) System.out.println("where = " + where);
            if (dbg) System.out.println("instit.length = " + instit.length);
            if (instit.length > 0) { institute = instit[0].getName(""); }
        } // if (!"".equals(surveyId))

    } // getInventoryDetails


    /**
     * Print the current marine data records
     * @param   station   MrnStation data
     */
    void printRecords(MrnStation[] station) {

//        read (1, *) table, latmin, latmax, lonmin, lonmax, datmin, datmax
//        read (1, *) cruise_id, num_stn, vessel, project, cruise
//        read (1, *) instit
//        do 10 i = 1, num_stn
//            read (1, *) dumchar, datum(i), lat(i), lon(i)
//            write (station(i), '(a)') dumchar(4:12)
//     10 continue
//'MARINE' 17 31  11  17 '1996/01/01' '1996/12/31' 'STATIONS'
//'Various'  245 '.' '.' '.'
//'.'
//'NMI40196001 ', '1996/01/14',  28.017,   15.550,     90.000

        String tab = "\t";
        int depthQC = -1;                                               //ub08
        int disoxQC = -1;                                               //ub08
        int salinQC = -1;                                               //ub08
        int tmpQC   = -1;                                               //ub08
        int no3QC   = -1;                                               //ub08
        int po4QC   = -1;                                               //ub08
        int sio3QC  = -1;                                               //ub08
        int chlaQC  = -1;                                               //ub08
        int phQC    = -1;                                               //ub08

        for (int i = 0; i < station.length; i++) {

            // get actual min and max ranges
            minLatitude = ec.min(minLatitude, station[i].getLatitude());
            maxLatitude = ec.max(maxLatitude, station[i].getLatitude());
            minLongitude = ec.min(minLongitude, station[i].getLongitude());
            maxLongitude = ec.max(maxLongitude, station[i].getLongitude());
            minDate = ec.min(minDate, station[i].getDateStart());
            maxDate = ec.max(maxDate, station[i].getDateEnd());

            // get the time in string format
            Timestamp dateT = station[i].getDateStart();
            String    year = dateT.toString().substring(0,4);
            String    month = dateT.toString().substring(5,7);
            String    day = dateT.toString().substring(8,10);
            String    ftime = dateT.toString().substring(10,16);

            String sub = "*";
            if (dbg) System.out.println("<br>maxLatitude = " + maxLatitude +
                ", minLatitude = " + minLatitude +
                ", maxLongitude = " + maxLongitude +
                ", minLongitude = " + minLongitude +
                ", maxDate = " + maxDate +
                ", minDate = " + minDate);

            float etopo2Depth = sc.getEtopo2(station[i]);                   //ub06

                // Print the BODY OF THE FILE
            String line =
            ec.frm(station[i].getSurveyId(),9)     +tab+
            ec.frm(station[i].getStationId(),12)   +tab+       //ub05
            ec.frm(sub,5)                       +tab+
            month+"/" +day+"/"+ year            +tab+
            ftime.trim()                        +tab+
            ec.frm(station[i].getLongitude(),8,3)  +tab+
            ec.frm(-station[i].getLatitude(),8,3)                +tab+
            nullToZERO(station[i].getStndep(),8,2)         +tab+
            nullToZERO(etopo2Depth,8,2)                 +tab+ //ub06
            nullToNULL(Tables.FLOATNULL,8,2)          +tab+
            checkMissing(Tables.FLOATNULL,depthQC)    +tab+ //ub08
            nullToNULL(Tables.FLOATNULL,8,2)     +tab+
            checkMissing(Tables.FLOATNULL, tmpQC)+tab+ //ub08
            nullToNULL(Tables.FLOATNULL,9,3)        +tab+
            checkMissing(Tables.FLOATNULL, salinQC) +tab+ //ub08
            nullToNULL(Tables.FLOATNULL,8,2)       +tab+
            checkMissing(Tables.FLOATNULL, disoxQC)+tab+ //ub08
            nullToNULL(Tables.FLOATNULL,8,2)                         +tab+
            nullToNULL(Tables.FLOATNULL,8,2)                         +tab+
            checkMissing(Tables.FLOATNULL, no3QC)                    +tab+ //ub08
            nullToNULL(Tables.FLOATNULL,8,2)                         +tab+
            checkMissing(Tables.FLOATNULL, po4QC)                    +tab+ //ub08
            nullToNULL(Tables.FLOATNULL,8,2)                           +tab+
            nullToNULL(Tables.FLOATNULL,8,2)                        +tab+
            checkMissing(Tables.FLOATNULL, sio3QC)                  +tab+ //ub08
            nullToNULL(Tables.FLOATNULL,8,2)                          +tab+
            checkMissing(Tables.FLOATNULL, phQC)                      +tab+ //ub08
            nullToNULL(Tables.FLOATNULL,7,1)          +tab+
            nullToNULL(Tables.FLOATNULL,9,3)                        +tab+
            checkMissing(Tables.FLOATNULL, chlaQC)                  +tab+ //ub08
            nullToNULL(station[i].getMaxSpldep(),8,2);

            ec.writeFileLine(ofile,line);

        }  // for (int i = 0; i < station.length; i++)


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

        if (dbg) System.err.println(thisClass +
            ".printHeader: minLatitude = " + minLatitude);
        if (dbg) System.err.println(thisClass +
            ".printHeader: maxLatitude = " + maxLatitude);
        if (dbg) System.err.println(thisClass +
            ".printHeader: minLongitude = " + minLongitude);
        if (dbg) System.err.println(thisClass +
            ".printHeader: maxLongitude = " + maxLongitude);
        if (dbg) System.err.println(thisClass +
            ".printHeader: minDate = " + minDate);
        if (dbg) System.err.println(thisClass +
            ".printHeader: maxDate = " + maxDate);

//'MARINE' 17 31  11  17 '1996/01/01' '1996/12/31' 'STATIONS'
        // correct min's and max's for integer areas
        if (minLatitude < 0) { minLatitude -= 0.99; }
        if (maxLatitude > 0) { maxLatitude += 0.99; }
        if (minLongitude < 0) { minLongitude -= 0.99; }
        if (maxLongitude > 0) { maxLongitude += 0.99; }
        String line =
            "'MARINE' " +
            ec.frm((int) minLatitude,4) +
            ec.frm((int) maxLatitude,4) +
            ec.frm((int) minLongitude,4) +
            ec.frm((int) maxLongitude,4) + " '" +
            minDate.toString().substring(0,10) + "' '" +
            maxDate.toString().substring(0,10) + "' 'STATIONS'";

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
        String head =
            "Cruise\tStation\tType\tmon/day/yr\thh:mm\tLon (°E)\t"+
            "Lat (°N)\tBot. Depth [m]\tEtopo2 Depth [m]\tDepth [m]\tQF\t"+  //ub06 ub08
            "Temperature [deg C]\tQF\tSalinity [ppt]\tQF\tDisolved Oxygen [ml/l]\tQF\t"+ //ub08
            "Nitrite [~$m~#g atom/l]\tNitrate [~$m~#g atom/l]\tQF\t"+           //ub08
            "Phosphate [~$m~#g atom/l]\tQF\tPhosporus [~$m~#g atom/l]\t"+       //ub08
            "Silicate [~$m~#g atom/l]\tQF\tpH []\tQF\tSound Velocity [m/s]\t"+  //ub08
            "Chlorophyll A [~$m~#g/l]\tQF\tDeepest Obs. Depth [m]";             //ub08
        ec.writeFileLine(ofile,head);
    } // ColumnHeadings


    /**
     * Method of sort: Bubble Sort
     * Function get Stations array and sort the stations array in date order.
     * @param stations : array value (string)
     * @return stations sorted by date array value (MrnStations[])
     */
    MrnStation[] bubbleSort(MrnStation[] stations) {

        //String fields =
        //    MrnStation.STATION_ID + "," +
        //    MrnStation.SURVEY_ID + "," +
        //    MrnStation.STNNAM + "," +
        //    MrnStation.LATITUDE + "," +
        //    MrnStation.LONGITUDE + "," +
        //    MrnStation.DATE_START + "," +
        //    MrnStation.DATE_END + "," +
        //    MrnStation.STNDEP + "," +
        //    MrnStation.MAX_SPLDEP + "," +
        //    MrnStation.PASSKEY;

        for (int i=0; i < stations.length; i++) {
            for (int j=0; j < stations.length-i-1; j++) {

                if (dbg) System.out.println("<br>"+stations[j].getDateStart()+" "+
                    stations[j+1].getDateStart());

                if (stations[j].getDateStart().after(stations[j+1].getDateStart())) {
                    if (dbg) System.out.println("<br>swop "+j);

                    String stationID = stations[j].getStationId();
                    stations[j].setStationId(stations[j+1].getStationId());
                    stations[j+1].setStationId(stationID);

                    String surveyID = stations[j].getSurveyId();
                    stations[j].setSurveyId(stations[j+1].getSurveyId());
                    stations[j+1].setSurveyId(surveyID);

                    String stnnam = stations[j].getStnnam();
                    stations[j].setStnnam(stations[j+1].getStnnam());
                    stations[j+1].setStnnam(stnnam);

                    float latitude = stations[j].getLatitude();
                    stations[j].setLatitude(stations[j+1].getLatitude());
                    stations[j+1].setLatitude(latitude);

                    float longitude = stations[j].getLongitude();
                    stations[j].setLongitude(stations[j+1].getLongitude());
                    stations[j+1].setLongitude(longitude);

                    Timestamp dateStart = stations[j].getDateStart();
                    stations[j].setDateStart(stations[j+1].getDateStart());
                    stations[j+1].setDateStart(dateStart);

                    Timestamp dateEnd = stations[j].getDateEnd();
                    stations[j].setDateEnd(stations[j+1].getDateEnd());
                    stations[j+1].setDateEnd(dateEnd);

                    float stndep = stations[j].getStndep();
                    stations[j].setStndep(stations[j+1].getStndep());
                    stations[j+1].setStndep(stndep);

                    float maxSpldep = stations[j].getMaxSpldep();
                    stations[j].setMaxSpldep(stations[j+1].getMaxSpldep());
                    stations[j+1].setMaxSpldep(maxSpldep);

                    String passkey = stations[j].getPasskey();
                    stations[j].setPasskey(stations[j+1].getPasskey());
                    stations[j+1].setPasskey(passkey);

                }// if (stations[j]
            } //for (int j= 0; j <
        }//for (int i= 0; i <

        return stations;
    } //MrnStation[] bubbleSort() {

    /**
     * print an error message if inputs not OK
     */
    void printErrorMessage() {
    } // printErrorMessage


    /**
     * return "0" of null value
     */
    String nullToZERO(float value, int length, int decimal) {
        return
            (value == Tables.FLOATNULL
                ? "0"
                : ec.frm(value,length,decimal));
        //(station.getStndep()==INTN?ec.frm(station.getStndep(),8,2):"1e+010") +tab+
    } // nullToZERO


    /**
     * return ODV null value if null
     */
    String nullToNULL(float value, int length, int decimal) {
        return
            (value == Tables.FLOATNULL
                ? "1e+010"
                : ec.frm(value,length,decimal));
        //(station.getStndep()==INTN?ec.frm(station.getStndep(),8,2):"1e+010") +tab+
    } // nullToNULL


    /**
     * Do mapping of WOD to ODV flags, and return a 1 if the value is missing
     */
    int checkMissing(float value, int qcValue) {

        int qcValue2 = qcValue;
        if (value == Tables.FLOATNULL) qcValue2 = 1;
        if (qcValue == -1) qcValue2 = 1;
        if ((qcValue >= 1) && (qcValue <= 5)) qcValue2 = 4;
        if (qcValue >= 6) qcValue2 = 8;
        return qcValue2;

    } // checkMissing


    /**
     * Update the LOG_USERS table for this extraction.
     */
    void updateLogUsers(String userid, String timeDiff) {
        LogUsers logs = new LogUsers();
        logs.setUserid(userid);
        logs.setDateTime(new Timestamp(new java.util.Date().getTime()));
        logs.setDatabase("MARINE");
        logs.setDiscipline("station");
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
        System.out.println(logs.getInsStr());

    } // updateLogUsers

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            ExtractMRNStationDataODV local =
                new ExtractMRNStationDataODV(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractMRNStationDataODV", "Constructor", "");
        } // try-catch
        // close the connection to the database
        common2.DbAccessC.close();

    } // main


 }// class ExtractMRNStationDataODV
