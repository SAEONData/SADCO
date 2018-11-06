package sadco;

import java.io.*;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.SimpleDateFormat;
//import oracle.html.*;

/**
 * This class contains the common routines for PreLoadMRN???Frame and
 * LoadMRN???Frame.
 *
 * @author 030309 - SIT Group
 * @version
 * 030309 - Ursula von St Ange - create class                               <br>
 * 030618 - Ursula von St Ange - change to pre-process                      <br>
 * 030811 - Ursula von St Ange - change input format                        <br>
 * 030820 - Ursula von St Ange - change to LoadMRNCommon class              <br>
 * 040609 - Ursula von St Ange - change for new marine ctd load format      <br>
 * 040625 - Ursula von St Ange - change name from LoadMRNCommon to
 *                               LoadMRNWatCommon                           <br>
 * 041013 - Ursula von St Ange - check for .9999 in toFloat(String, float) (ub01)<br>
 * 041013 - Ursula von St Ange - set passkey in station record if relevant (ub02)<br>
 * 050303 - Ursula von St Ange - change lat/lon input to decimal degrees   (ub03)<br>
 * 050311 - Ursula von St Ange - add commit after station data save        (ub04)<br>
 * 051020 - Ursula von St Ange - add turbidity to watphy table             (ub05)<br>
 * 051025 - Ursula von St Ange - check for 10 chars in survey table values (ub06)<br>
 * 051027 - Ursula von St Ange - add SIO4 to watnut load                   (ub07)<br>
 * 060130 - Ursula von St Ange - combined LoadMrnCurCommon and LoadMrnWatCommon
 *                               by adding dataType and DATA_TYPE vars      <br>
 * 051027 - Ursula von St Ange - take out substrings in format01/02        (ub08)<br>
 * 090710 - Ursula von St Ange - add pressure and fluorescence to watphy   (ub09)<br>
 *                             - temporarily take out test for stationid   (ub10)<br>
 * 090819 - Ursula von St Ange - calculate depth if only have pressure     (ub11)<br>
 * 091026 - Ursula von St Ange - take out counts of record types - now done
 *                               by AdminUpdateInvStats called in
 *                               LoadMRNDataApp                            (ub12)<br>
 * 100528 - Ursula von St Ange - set data_available in inventory           (ub13)<br>
 * 110524 - Ursula von St Ange - add percGood to currents                  (ub14)<br>
 */
public class LoadMRNCommon {

    // values for in this application
    String thisClass   = this.getClass().getName(); // class name

    /** A general purpose class used to extract the url-type parameters*/
    static common.edmCommonPC ec = new common.edmCommonPC();
    SadConstants sc = new SadConstants();

    boolean dbg = false;
    //boolean dbg = true;
    boolean dbg2 = false;
    //boolean dbg2 = true;
    boolean dbg3 = false;
    //boolean dbg3 = true;
    boolean dbg4 = false;
    //boolean dbg4 = true;


    static final int    MAX_STATIONS     = 100;
    static final int    MAX_SUBDES       = 5;

    // 1 minute == 1 Nm either side == 2 Nm sq
    //static final float  AREA_RANGE_VAL   = .01667f;
    static final float  AREA_RANGE_VAL   = 1f;
    // 30 seconds == 1/2 Nm either side = 1 Nm sq
    //static final float  AREA_RANGE_VAL   = 0.00833f;
    //static final float  AREA_RANGE_VAL   = 0.1f;

    //static final int    TIME_RANGE_VAL   = 30;
    //static final int    TIME_RANGE_VAL   = 120;
    // +/- (2 hours + 2 minutes) - catch out SAST / UTC
    //static final int    TIME_RANGE_VAL   = 122;

    //static final int    TIME_RANGE_VAL   = 10;
    static final int    TIME_RANGE_VAL   = 10;
    //static final int    TIME_RANGE_VAL   = 1;

    static final int WATER = 0;
    static final int WATERWOD = 1;
    static final int CURRENTS = 2;
    static final int SEDIMENT = 3;
    //static final String DATA_TYPE[] = {"watphy","currents","sedphy"};
    static final String DATA_TYPE[] = {"watphy","watphy(WOD)","currents","sedphy"};

    float areaRangeVal = AREA_RANGE_VAL / 60f;
    int timeRangeVal = TIME_RANGE_VAL;

    // arguments
    String passkey = "";
    String timeZone = "";
    String updateStationsList[] = new String[3];
    String replaceDataList[] = new String[3];
    boolean loadFlag = false;

    int dataType = 0;

    MrnInventory inventory = new MrnInventory();
    MrnSurvey survey = new MrnSurvey();
    MrnStation station = new MrnStation();
    MrnSfriGrid sfriGrid = new MrnSfriGrid();
    MrnWeather weather = new MrnWeather();

    MrnCurrents currents = new MrnCurrents();

    MrnSedphy sedphy = new MrnSedphy();
    MrnSedchem1 sedchem1 = new MrnSedchem1();
    MrnSedchem2 sedchem2 = new MrnSedchem2();
    MrnSedpol1 sedpol1 = new MrnSedpol1();
    MrnSedpol2 sedpol2 = new MrnSedpol2();

    MrnWatprofqc watProfQC = new MrnWatprofqc();
    MrnWatqc watQC = new MrnWatqc();

    MrnWatphy watphy = new MrnWatphy();
    MrnWatchem1 watchem1 = new MrnWatchem1();
    MrnWatchem2 watchem2 = new MrnWatchem2();
    MrnWatchl watchl = new MrnWatchl();
    MrnWatnut watnut = new MrnWatnut();
    MrnWatpol1 watpol1 = new MrnWatpol1();
    MrnWatpol2 watpol2 = new MrnWatpol2();

    LogMrnLoads logMrnLoads = new LogMrnLoads();

    MrnSurvey tSurvey[] = null;
    MrnStation tStation[] = null;
    MrnCurrents tCurrents[] = null;
    MrnWatphy tWatphy[] = null;
    MrnSedphy tSedphy[] = null;

    int lineCount  = 0;
    int fatalCount   = 0;
    int code01Count  = 0;
    int code02Count  = 0;
    int code06Count  = 0;
    int code07Count  = 0;
    int code08Count  = 0;
    int code09Count  = 0;
    int code10Count  = 0;
    int code11Count  = 0;
    int code12Count  = 0;
    int code13Count  = 0;

    int dataCodeEnd = 0;
    int dataCodeStart = 0;

    int stationCount = 0;
    int newStationCount = 0;
    int stationSampleCount = 0;
    int stationSampleRejectCount = 0;
    int sampleCount = 0;
    int sampleRejectCount = 0;

    /*  // ub12
    int weatherCount = 0;

    int currentsCount = 0;

    int watphyCount = 0;
    int watchem1Count = 0;
    int watchem2Count = 0;
    int watchlCount = 0;
    int watnutCount = 0;
    int watpol1Count = 0;
    int watpol2Count = 0;

    int sedphyCount = 0;
    int sedchem1Count = 0;
    int sedchem2Count = 0;
    int sedpol1Count = 0;
    int sedpol2Count = 0;
    */  // ub12

    // counts for loadFlag=false
    int didCount = 0;  // duplicate - no loading
    int dsdCount = 0;
    int disCount = 0;  // duplicate with subdes - no loading
    int dssCount = 0;

    // counts for loadFlag=true
    int dipCount = 0;  // replace data with same subdes
    int dspCount = 0;
    int dijCount = 0;  // reject (ignore)
    int dsjCount = 0;
    int diaCount = 0;  // add subdes
    int dsaCount = 0;

    int code         = 0;
    int prevCode     = 0;
    //int subCode      = 0;
    String stationId04 = "";

//    boolean fatalErrors = false;
    boolean firstStation = true;
    boolean lastStation = true;
    boolean surveyLoaded = false;
    String surveyStatus = "";
    boolean inventoryExists = false;

    boolean stationExists = false;
    boolean stationExistsArray[] = new boolean[MAX_STATIONS];
    int     stationExistsCount = 0;
    boolean stationUpdated = false;

    //boolean stationIgnore = false;
    String stationStatusDB[] = new String[MAX_STATIONS];
    String stationStatusLD = "";
    String stationStatusTmp = "";
    boolean duplicateStations = false;

    boolean dataExists = false;

    boolean weatherIsLoaded = false;

    String platformName = "";                               // ub03
    String expeditionName = "";                             // ub03
    String projectName = "";                                // ub03
    String instituteName = "";                              // ub03
    String chiefScientist = "";                             // ub03
    String countryName = "";                                // ub03
    String submitScientist = "";                            // ub03
    String respPerson = "Louise Watt";
    String institute = "";
    String stationId = "";
    String upIndicator = "";
    boolean rejectDepth = false;

    String startDate = "";
    String endDate = "";

    String startDateTime = "";
    //String prevStartDateTime = "";
    String subdes = "";

    Timestamp dateMin = Timestamp.valueOf("2050-12-31 23:59:59.9");
    Timestamp dateMax = Timestamp.valueOf("1850-01-01 00:00:00.0");
    float latitudeMin = 90f;
    float latitudeMax = -90f;
    float longitudeMin = 360f;
    float longitudeMax = -360f;

    float prevDepth = 0f;
    float depthMin = 9999f;
    float depthMax = -9999f;
    float loadedDepthMin[] = new float[MAX_STATIONS];
    float loadedDepthMax[] = new float[MAX_STATIONS];

    String spldattimArray[] = new String[MAX_STATIONS];
    String subdesArray[][] = new String[MAX_STATIONS][MAX_SUBDES];
    int subdesCount[] = new int[MAX_STATIONS];
    int thisSubdesCount = 0;

    int currentsRecordCountArray[] = new int[MAX_STATIONS];
    int sedphyRecordCountArray[] = new int[MAX_STATIONS];
    int watphyRecordCountArray[] = new int[MAX_STATIONS];

    RandomAccessFile inputFile = null;
    RandomAccessFile debugFile = null;
    RandomAccessFile reportFile = null;
    RandomAccessFile workFile = null;
    RandomAccessFile stnFile = null;

    long headerPos1 = 0, headerPos2 = 0, headerPos3 = 0;


    void reInitVars() {
        inventory = new MrnInventory();
        survey = new MrnSurvey();
        station = new MrnStation();
        sfriGrid = new MrnSfriGrid();
        weather = new MrnWeather();

        currents = new MrnCurrents();

        sedphy = new MrnSedphy();
        sedchem1 = new MrnSedchem1();
        sedchem2 = new MrnSedchem2();
        sedpol1 = new MrnSedpol1();
        sedpol2 = new MrnSedpol2();

        watProfQC = new MrnWatprofqc();
        watQC = new MrnWatqc();

        watphy = new MrnWatphy();
        watchem1 = new MrnWatchem1();
        watchem2 = new MrnWatchem2();
        watchl = new MrnWatchl();
        watnut = new MrnWatnut();
        watpol1 = new MrnWatpol1();
        watpol2 = new MrnWatpol2();

        logMrnLoads = new LogMrnLoads();

        lineCount  = 0;
        fatalCount   = 0;
        code01Count  = 0;
        code02Count  = 0;
        code06Count  = 0;
        code07Count  = 0;
        code08Count  = 0;
        code09Count  = 0;
        code10Count  = 0;
        code11Count  = 0;
        code12Count  = 0;
        code13Count  = 0;

        stationCount = 0;
        newStationCount = 0;
        stationSampleCount = 0;
        stationSampleRejectCount = 0;
        sampleCount = 0;
        sampleRejectCount = 0;

        /*  // ub12
        weatherCount = 0;

        currentsCount = 0;

        watphyCount = 0;
        watchem1Count = 0;
        watchem2Count = 0;
        watchlCount = 0;
        watnutCount = 0;
        watpol1Count = 0;
        watpol2Count = 0;

        sedphyCount = 0;
        sedchem1Count = 0;
        sedchem2Count = 0;
        sedpol1Count = 0;
        sedpol2Count = 0;
        */ // ub12

        // counts for loadFlag=false
        didCount = 0;  // duplicate - no loading
        dsdCount = 0;
        disCount = 0;  // duplicate with subdes - no loading
        dssCount = 0;

        // counts for loadFlag=true
        dipCount = 0;  // replace data with same subdes
        dspCount = 0;
        dijCount = 0;  // reject (ignore)
        dsjCount = 0;
        diaCount = 0;  // add subdes
        dsaCount = 0;

        firstStation = true;
        lastStation = true;
        surveyLoaded = false;
        surveyStatus = "";
        inventoryExists = false;

        stationExists = false;
        stationExistsCount = 0;
        stationUpdated = false;

        //boolean stationIgnore = false;
        stationStatusLD = "";
        stationStatusTmp = "";
        duplicateStations = false;

        dataExists = false;

        weatherIsLoaded = false;

        platformName = "";                               // ub03
        expeditionName = "";                             // ub03
        projectName = "";                                // ub03
        instituteName = "";                              // ub03
        chiefScientist = "";                             // ub03
        countryName = "";                                // ub03
        submitScientist = "";                            // ub03
        respPerson = "Louise Watt";
        institute = "";
        stationId = "";
        upIndicator = "";
        rejectDepth = false;

        startDate = "";
        endDate = "";

        startDateTime = "";
        subdes = "";

        dateMin = Timestamp.valueOf("2050-12-31 23:59:59.9");
        dateMax = Timestamp.valueOf("1850-01-01 00:00:00.0");
        latitudeMin = 90f;
        latitudeMax = -90f;
        longitudeMin = 360f;
        longitudeMax = -360f;

        prevDepth = 0f;
        depthMin = 9999f;
        depthMax = -9999f;

        thisSubdesCount = 0;

        code         = 0;
        prevCode     = 0;

    } // reInitVars


    /**
     * Check whether this is not the first record in the file
     * @param  code  the record code
     * @param  lineCount  the record count
     */
    void checkFirstRecord(int code, int lineCount) {
        if (lineCount == 1) {
            outputError(lineCount + " - Fatal - " +
                "Load file must start with a code 01 record.  " +
                "This record is code: " + code);
        } // if (lineCount == 1)
    } // checkFirstRecord


    /**
     * Check whether the station Id is the same as in the preceding station
     * record.
     */
    void checkStationId(int lineCount, String testStationId) {
        if (!stationId.equals(testStationId)) {
            outputError(lineCount + " - Fatal - " +
                "Station Id does not match previous station record " +
                stationId + ": " + testStationId);
        } // if (!stationId.equals(station.getStationId("")))
    } // checkStationId


    /**
     * Check whether the station Id is already in the database
     */
    void checkStationExists() {

        stationExists = false;
        dataExists = false;
        stationExistsCount = 0;
        thisSubdesCount = 0;
//        stationIgnore = false;

        for (int i = 0; i < MAX_STATIONS; i++) {
            stationExistsArray[i] = false;
            loadedDepthMin[i] = 9999;
            loadedDepthMax[i] = -9999;
            spldattimArray[i] = "";
            currentsRecordCountArray[i] = 0;
            watphyRecordCountArray[i] = 0;
            stationStatusDB[i] = "";
            subdesCount[i] = 0;
            for (int j = 0; j < MAX_SUBDES; j++) {
                subdesArray[i][j] = "";
            } // for (int j = 0; j < MAX_SUBDES; j++)

        } // for (int i = 0; i < MAX_STATIONS; i++)

        // true if station with same station Id (first select) or
        // station with same date_start & latitude & longitude &
        // <data>.spltim & <data>.subdes (second select)
        String where = MrnStation.STATION_ID + "='" + station.getStationId() + "'";
        if (dbg) System.out.println("<br>checkStationExists: station: where = " +
            where);

        // Is there already a station record with the same station-id?
        MrnStation[] tStation2 = new MrnStation().get(where);
        if (dbg) System.out.println("<br>checkStationExists: tStation2.length = " +
            tStation2.length);
        if (tStation2.length > 0) {
            //stationStatusLD = "di";   // == duplicate station-id
            outputDebug("checkStationExists: station Id found: " +
                station.getStationId(""));
            stationExists = true;
            stationExistsArray[0] = true;
        } else {
            //stationStatusLD = "ds";      // == duplicate station (lat/lon/date/time)
            outputDebug("checkStationExists: station Id not found: " +
                station.getStationId(""));
        } // if (tStation2.length > 0)

        // are there any other stations around the same day that fall within the
        // latitude range? (it could include the station found above)

        // get the previous and next day for the select, in case the
        // spldattim is just before or after midnight
        java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.GregorianCalendar calDate = new java.util.GregorianCalendar();
        calDate.setTime(station.getDateStart());
        calDate.add(java.util.Calendar.DATE, -1);
        String dateStartMin = formatter.format(calDate.getTime());

        calDate.setTime(station.getDateEnd());
        calDate.add(java.util.Calendar.DATE, +1);
        String dateEndMax = formatter.format(calDate.getTime());

        where =
            MrnStation.DATE_START + ">=" +
                Tables.getDateFormat(dateStartMin)  + " and " +
                //dateStartMin  + " and " +
            MrnStation.DATE_END + "<=" +
                Tables.getDateFormat(dateEndMax)  + " and " +
                //dateEndMax  + " and " +
            MrnStation.LATITUDE + " between " +
                (station.getLatitude()-areaRangeVal) + " and " +
                (station.getLatitude()+areaRangeVal) + " and " +
            MrnStation.LONGITUDE + " between " +
                (station.getLongitude()-areaRangeVal) + " and " +
                (station.getLongitude()+areaRangeVal);
        if (dbg) System.out.println("<br><br>checkStationExists: station: " +
            "where = " + where);

        // get the records
        MrnStation[] tStation3 = new MrnStation().get(
            "*", where, MrnStation.STATION_ID);
        if (dbg) System.out.println("<br><br>checkStationExists: " +
            "tStation3.length = " + tStation3.length);

        // put both sets of stations found in one array for processing
        tStation = new MrnStation[tStation2.length + tStation3.length];
        if (dbg) System.out.println("<br><br>checkStationExists: " +
            "tStation.length = " + tStation.length);
        int ii = 0;
        for (int i = 0; i < tStation2.length; i++) {  // same station_id
            tStation[ii] = tStation2[i];
            ii++;
        } // for (int i = 0; i < tStation2.length; i++)
        for (int i = 0; i < tStation3.length; i++) {  // satem lat/lon/date/time
            tStation[ii] = tStation3[i];
            ii++;
        } // for (int i = 0; i < tStation3.length; i++)

        // process the records
        stationUpdated = false;
        for (int i = 0; i < tStation.length; i++) {

            // same station id already done
            if ((i == 0) ||
                !tStation[i].getStationId().equals(station.getStationId())) {

                if (tStation[i].getStationId().equals(station.getStationId())) {
                    stationStatusTmp = "di";   // == duplicate station-id
                } else {
                    stationStatusTmp = "ds";   // == duplicate station (lat/lon/date/time)
                } // if (tStation[i].getStationId().equals(station.getStationId()))

                String dbgMess = "checkStationExists: station found in area: " +
                    i + " " + station.getStationId("") + " " +
                    tStation[i].getStationId("");

                getExistingStationDetails(tStation[i], i);

                if (stationExistsArray[i]) {
                    stationExistsCount++;

                    outputDebug(dbgMess + ": within time range");

//                    if (loadFlag) {
//                        updateStationDetails(i);
//                    } else {
                        // d == Duplicate stationId/station only
                        // s == Subdes: duplicate Station (lat/lon/date/time) && subdes
                        stationStatusDB[i] = stationStatusTmp;
                        if (subdesCount[i] == 0) {
                            // d == Duplicate stationId/station only (lat/lon/date/time)
                            stationStatusDB[i] += "d";
                            if ("did".equals(stationStatusDB[i])) {
                                didCount++;
                            } else {
                                dsdCount++;
                            } // if ("did".equals(stationStatusDB))
                        } else { // if (subdesCount == 0)
                            // s == Subdes: duplicate Station (lat/lon/date/time) && subdes
                            stationStatusDB[i] += "s";
                            if ("dis".equals(stationStatusDB[i])) {
                                disCount++;
                            } else {
                                dssCount++;
                            } // if ("dia".equals(stationStatusDB))

                            thisSubdesCount += subdesCount[i];

                        } // if (subdesCount == 0)
//                    } // if (loadFlag)
                } else {
                    outputDebug(dbgMess + ": NOT within time range");
                } // if (stationExistsArray[i]) {

            } // if (!tStation.getStationId().equals(station.getStationId())

        } // for (int i = 0;  i < tStation.length; i++)

        if (dbg) System.out.println("checkStationExists: stationStatusTmo = " + stationStatusTmp);

        // either stations with same station Id or within same
        //  area & date-time range found
        if (stationExists) {
            duplicateStations = true;
            if (dataExists) {
                stationStatusLD = "dup";
            } else {
                stationStatusLD = "new";
                newStationCount++;
            } // if (dataExists)
        } else {
            stationStatusLD = "new";
            newStationCount++;
        } // if (stationExists)

        if (dbg) System.out.println("checkStationExists: stationStatusLD = " + stationStatusLD);
        if (dbg) System.out.println("checkStationExists: stationStatusTmo = " + stationStatusTmp);
        for (int i = 0; i < tStation.length; i++) {
            if (dbg) System.out.println("checkStationExists: stationStatusDB[i] = " +
                i + " " + stationStatusDB[i]);
        } // for (int i = 0; i < tStation.length; i++)

    } // checkStationExists


    /**
     * Check whether the unique index (lat, lon, date, stnnam) is already
     * in use.  (Normally happens within the same loads) (time is not taken into account)
     * If it is, it changes stnnam until it is unique
     */
    void checkStationUIExists() {

        int recordCount = 9999;
        int duplicateCount = 0;

        String where = MrnStation.DATE_START + "=" +
            Tables.getDateFormat(station.getDateStart()) +
            " and " + MrnStation.LATITUDE +
                " between " + (station.getLatitude()-areaRangeVal) +
                " and " + (station.getLatitude()+areaRangeVal) +
            " and " + MrnStation.LONGITUDE +
                " between " + (station.getLongitude()-areaRangeVal) +
                " and " + (station.getLongitude()+areaRangeVal) ;
        if (dbg) System.out.println("<br>checkStationUIExists: where = " + where);
        //outputDebug("checkStationUIExists: where = " + where);

        MrnStation[] stns = new MrnStation().get(where);
        if (dbg) System.out.println("<br>checkStationUIExists: stns.length = " + stns.length);
        //outputDebug("checkStationUIExists: number of stations found = " + stns.length);

        String debugMessage = "\ncheckStationUIExists: duplicate UI check \n   " +
            station.getDateStart("").substring(0,10) +
            ", " + ec.frm(station.getLatitude(),10,5) +
            ", " + ec.frm(station.getLongitude(),10,5) +
            ", " + station.getStnnam("") +
            " (" + station.getStationId("") + ")";

        if (stns.length == 0) {
            outputDebug(debugMessage + ": duplicate UI not found");
        } else {

            outputDebug(debugMessage);

            // check for duplicate station names (stnnam)
            while (recordCount > 0) {

                // count station names the same
                recordCount = 0;
                for (int i = 0; i < stns.length; i++) {
                    if (stns[i].getStnnam("").equals(station.getStnnam(""))) {
                        recordCount++;
                    } // if (stns[i].getStnnam("").equals(station.getStnnam("")))
                } // for (int i = 0; i < stns.length; i++)

                if (dbg) System.out.println("<br>checkStationUIExists: recordCount = " +
                    recordCount + ", stnnam = " + station.getStnnam(""));

                debugMessage =  "   " + station.getStnnam("") +
                        ", recordCount (same stnnam) = " + recordCount;

                if (recordCount > 0) {
                    duplicateCount++;

                    station.setStnnam(station.getStnnam("") + duplicateCount);

                    debugMessage += " - new stnnam = " +
                        station.getStnnam("");
                    //outputDebug(debugMessage);
                    if (dbg) System.out.println("checkStationUIExists: new stnnam = " +
                        station.getStnnam(""));
                }// if (recordCount > 0)

                outputDebug(debugMessage);

            } // while (recordCount > 0)

        } // if (stns.length == 0)

    } // checkStationUIExists


    /**
     * check if the inventory record exists
     */
    void checkInventoryExists() {

        MrnInventory[] checkInventory =
            new MrnInventory(survey.getSurveyId()).get();
            inventoryExists = false;
        if (checkInventory.length > 0) {
            inventoryExists = true;
        } // if (checkInventory.length > 0)


    } // checkSurveyExists


    /**
     * Process the inventory / survey record - check if valid survey.  If it is
     * valid, and loadFlag == true, then create the survey record.
     */
    void checkSurveyExists() {

        if (dbg) System.out.println("<br>checkSurveyExists: inventory = " + inventory);
        if (dbg) System.out.println("<br>checkSurveyExists: survey = " + survey);
        if (dbg) System.out.println("<br>checkSurveyExists: lineCount = " + lineCount);

        // is the survey already loaded?
        surveyLoaded = false;
        surveyStatus = "new";

        boolean emptyFields = false;

        // survey_id may not be blank
        if ("".equals(survey.getSurveyId(""))) {
            outputError((lineCount-1) + " - Fatal - " +
                "Field empty - Survey-ID");
            emptyFields = true;
        } // if ("".equals(survey.getSurveyId())

        // institute may not be blank
        if ("".equals(survey.getInstitute(""))) {
            outputError((lineCount-1) + " - Fatal - " +
                "Field empty - Institute (3-letter institute code: code 01 3)");
            emptyFields = true;
        } // if ("".equals(survey.getInstitute())

        // domain may not be blank - only need to check if inventory does not exist
        if (dbg) System.out.println("<br>checkSurveyExists: inventoryExists = " +
            inventoryExists);
        if (!inventoryExists) {
            if ("".equals(inventory.getDomain(""))) {
                if (dataType == WATER) {
                    outputError((lineCount-1) + " - Fatal - " +
                        "Field empty - Domain (code 01 4)");
                    emptyFields = true;
                } else {
                    inventory.setDomain("DEEPSEA");
                } // if (dataType == WATER) {
            } // if ("".equals(survey.getDomain())
        } // if (!inventoryExists)

        if (!emptyFields) {

            tSurvey = new MrnSurvey(survey.getSurveyId()).get();
            if (dbg) if (tSurvey.length > 0)
                System.out.println("<br>checkSurveyExists: tsurvey[0] = " + tSurvey[0]);
            if (dbg) System.out.println("<br>checkSurveyExists: survey = " + survey);
            if (tSurvey.length > 0) {
                //if (!tSurvey[0].getPlanam().equals(survey.getPlanam()) &&
                //    !tSurvey[0].getExpnam().equals(survey.getExpnam()) &&
                //    !tSurvey[0].getInstitute().equals(survey.getInstitute())) {
                if (!tSurvey[0].getPlanam("").equals(survey.getPlanam("")) ||
                    !tSurvey[0].getExpnam("").equals(survey.getExpnam("")) ||
                    !tSurvey[0].getInstitute("").equals(survey.getInstitute(""))) {

                    outputError((lineCount-1) + " - Fatal - " +
                        "Survey-ID already in use: " + survey.getSurveyId() +
                        ": planam (code 01 1), expnam (code 01 2) or\n" +
                        "            institute (code 01 3) not same as " +
                        "in survey table");
                } else {
                    surveyLoaded = true;
                    surveyStatus = "dup";
                } // if (!tSurvey.getPlanam().equals(survey.getPlanam()) &&
            } // if (tSurvey.length > 0)

        } // if (!emptyFields)

        // load the survey data (only if no errors)
        if (dbg) System.out.println("checkSurveyExists: loadflag = " + loadFlag);
        if (dbg) System.out.println("checkSurveyExists: fatalCount = " + fatalCount);
        if (dbg) System.out.println("checkSurveyExists: surveyLoaded = " + surveyLoaded);
        if (loadFlag && (fatalCount == 0) && !surveyLoaded) {
            loadInventory();
            try {
                survey.put();
            } catch(Exception e) {
                System.err.println("checkSurveyExists: put survey = " + survey);
                System.err.println("checkSurveyExists: put sql = " + survey.getInsStr());
                e.printStackTrace();
            } // try-catch

            if (dbg3) System.out.println("<br>checkSurveyExists: put survey = " + survey);
        } // if (loadFlag)

        institute = survey.getInstitute("");  // 3-letter institute code

    } // checkSurveyExists


    /**
     * Check for a valid date/time
     * @param   dateTime in format of yyyy-MM-dd HH:mm:ss.s
     */
    void checkForValidDate(String dateTime) {

        int daysOfMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31};

        Timestamp now = new Timestamp(new java.util.Date().getTime());
        Timestamp date = Timestamp.valueOf(startDateTime);
        if (date.after(now)) {
            outputError(lineCount + " - Fatal - " +
                "Date / Time later than today's date : " +
                startDateTime + " : " + station.getStationId(""));
        } // if (date.after(now))

        StringTokenizer t = new StringTokenizer(dateTime, "- :.");
        int year = Integer.parseInt(t.nextToken());
        int month = Integer.parseInt(t.nextToken());
        int day = Integer.parseInt(t.nextToken());
        int hour = Integer.parseInt(t.nextToken());
        int minute = Integer.parseInt(t.nextToken());
        boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
        if (isLeapYear) daysOfMonth[1] += 1;
        if (year < 1850) {
            outputError(lineCount + " - Fatal - " +
                "Year before 1850 : " +
                startDateTime + " : " + station.getStationId(""));
        } // if (year < 1850)

        if (month > 12) {
            outputError(lineCount + " - Fatal - " +
                "Month invalid ( > 12) : " +
                startDateTime + " : " + station.getStationId(""));
        } // if (month > 12)

        if (day > daysOfMonth[month-1]) {
            outputError(lineCount + " - Fatal - " +
                "Day invalid ( > no of days in month) : " +
                startDateTime + " : " + station.getStationId(""));
        } // if (day > (daysOfMonth[month-1])

        if (hour > 23) {
            outputError(lineCount + " - Fatal - " +
                "Hour invalid ( > 23) : " +
                startDateTime + " : " + station.getStationId(""));
        } // if (hour > 23)

        if (minute > 59) {
            outputError(lineCount + " - Fatal - " +
                "Minute invalid ( > 59) : " +
                startDateTime + " : " + station.getStationId(""));
        } // if (minute > 59)

    } // checkForValidDate


    /**
     * Process the inventory / survey record - get the data from the input file
     * @param  line   The input line
     * @param  lineCount  the record count
     */
    void format01(String line,  int lineCount) {

        if (dbg4) System.out.println("format01: dataType = " + dataType + " " + DATA_TYPE[dataType]);

        // only 1 survey is allowed per load file - max of 9 code 01 lines
        code01Count++;
        if (prevCode > 1) {
            outputError((lineCount-1) + " - Fatal - " +
                "More than 1 survey in data file : " + code01Count);
        } // if (prevCode != 1)

        // get the data from the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code
        int subCode = 0;
        if (t.hasMoreTokens()) subCode = toInteger(t.nextToken());
        switch (subCode) {
            //01  a2  format code Always '01'                   n/a
            //02  a1  format subCode  Always '1'                n/a
            //03  a9  survey_id   e.g. '1997/0001'              survey
            //04  a10 planam  Platform name                     inventory
            case 1: if (t.hasMoreTokens()) survey.setSurveyId(t.nextToken());
                    if (t.hasMoreTokens()) {
                        dummy = t.nextToken();                                  //ub08
                        while (t.hasMoreTokens()) dummy += " " + t.nextToken(); //ub08
                        platformName = dummy;
                        if (dummy.length() > 10) dummy = dummy.substring(0, 10);//ub06
                        survey.setPlanam(dummy);        //ub06
                        if (dbg) System.out.println("planam = " + platformName + " " + survey.getPlanam(""));
                    } // if (t.hasMoreTokens())
                    break;
            //01  a2  format code Always '01'                   n/a
            //02  a1  format subCode  Always '2'                n/a
            //03  a15 expnam  Expedition name, e.g. 'AJAXL2'    survey
            case 2: if (t.hasMoreTokens()) {
                        dummy = t.nextToken();                                  //ub08
                        while (t.hasMoreTokens()) dummy += " " + t.nextToken(); //ub08
                        expeditionName = dummy;
                        if (dummy.length() > 10) dummy = dummy.substring(0, 10);//ub06
                        survey.setExpnam(dummy);                                //ub06
                        if (dbg) System.out.println("expnam = " + expeditionName + " " + survey.getExpnam(""));
                    } // if (t.hasMoreTokens())
                    break;
            //01  a2  format code Always '01'                   n/a
            //02  a1  format subCode  Always '3'                n/a
            //03  a3  institute   3-letter Institute code, e.g. 'RIO' survey
            //04  a28 prjnam  project name, e.g. 'HEAVYMETAL' survey
            case 3: survey.setInstitute(t.nextToken());
                    if (t.hasMoreTokens()) {
                        dummy = t.nextToken();                                  //ub08
                        while (t.hasMoreTokens()) dummy += " " + t.nextToken(); //ub08
                        projectName = dummy;
                        if (dummy.length() > 10) dummy = dummy.substring(0, 10);//ub06
                        survey.setPrjnam(dummy);                                //ub06
                        if (dbg) System.out.println("prjnam = " + projectName + " " + survey.getPrjnam(""));
                    } // if (t.hasMoreTokens())
                    break;
            //01  a2  format code Always '01'                   n/a
            //02  a1  format subCode  Always '4'                n/a
            //03  a10 domain  e.g. 'SURFZONE', 'DEEPSEA'        survey
            // domain corrected by user in form
            //case 4: inventory.setDomain(toString(line.substring(5)));
            //case 4: if (!loadFlag) inventory.setDomain(toString(line.substring(5)));
            case 4: //if (!loadFlag) {
                        if (t.hasMoreTokens()) {
                            dummy = t.nextToken();                                  //ub08
                            while (t.hasMoreTokens()) dummy += " " + t.nextToken(); //ub08
                            if (dummy.length() > 10) dummy = dummy.substring(0, 10);//ub06
                            inventory.setDomain(dummy);                             //ub06
                        } // if (t.hasMoreTokens())
                    //} // if (!loadFlag)
                    break;
            //01  a2  format code Always '01'                   n/a
            //02  a1  format subCode  Always '5'                n/a
            //03  a10 arenam  Area name, e.g. 'AGULHAS BK'      survey
            // area name corrected by user in form
            //case 5: inventory.setAreaname(toString(line.substring(5)));
            //case 5: if (!loadFlag) inventory.setAreaname(toString(line.substring(5)));
            case 5: //if (!loadFlag) {
                        if (t.hasMoreTokens()) {
                            dummy = t.nextToken();                                  //ub08
                            while (t.hasMoreTokens()) dummy += " " + t.nextToken(); //ub08
                            if (dummy.length() > 20) dummy = dummy.substring(0, 20);//ub06
                            inventory.setAreaname(dummy);                           //ub06
                        } // if (t.hasMoreTokens())
                    //} // if (!loadFlag)
                    break;
            //01  a2  format code Always '01'                   n/a
            //02  a1  format subCode  Always '6'                n/a
            //04  a10 insitute  e.g. 'World Ocean database'     inventory
            case 6: //if (!loadFlag) {                                    //ub03
                        if (t.hasMoreTokens()) {                        //ub03
                            instituteName = t.nextToken();              //ub03
                            while (t.hasMoreTokens())                   //ub03
                                instituteName += " " + t.nextToken();   //ub03
                        } // if (t.hasMoreTokens())                     //ub03
                    //} // if (!loadFlag)                                 //ub03
                    break;
            //01  a2  format code Always '01'                   n/a
            //02  a1  format subCode  Always '7'                n/a
            //04  a10 chief scientist                           inventory
            case 7: //if (!loadFlag) {                                    //ub03
                        if (t.hasMoreTokens()) {                        //ub03
                            chiefScientist = t.nextToken();             //ub03
                            while (t.hasMoreTokens())                   //ub03
                                chiefScientist += " " + t.nextToken();  //ub03
                        } // if (t.hasMoreTokens())                     //ub03
                    //} // if (!loadFlag)                                 //ub03
                    break;
            //01  a2  format code Always '01'                   n/a
            //02  a1  format subCode  Always '8'                n/a
            //04  a10 country e.g. 'Germany'                    inventory
            case 8: //if (!loadFlag) {                                    //ub03
                        if (t.hasMoreTokens()) {                        //ub03
                            countryName = t.nextToken();                //ub03
                            while (t.hasMoreTokens())                   //ub03
                                countryName += " " + t.nextToken();     //ub03
                        } // if (t.hasMoreTokens())                     //ub03
                    //} // if (!loadFlag)                                 //ub03
                    break;
            //01  a2  format code Always '01'                   n/a
            //02  a1  format subCode  Always '8'                n/a
            //04  a10 submitting scientist                      load log
            case 9: //if (!loadFlag) {                                    //ub03
                        if (t.hasMoreTokens()) {                        //ub03
                            submitScientist = t.nextToken();            //ub03
                            while (t.hasMoreTokens())                   //ub03
                                submitScientist += " " + t.nextToken(); //ub03
                        } // if (t.hasMoreTokens())                     //ub03
                    //!} // if (!loadFlag)                                 //ub03
                    break;
        } // switch (subCode)

        //survey.setSurveyId(toString(line.substring(2,11)));
        //survey.setPlanam(toString(line.substring(11,21)));
        //survey.setExpnam(toString(line.substring(21,31)));
        //survey.setInstitute(toString(line.substring(31,34)));
        //survey.setPrjnam(toString(line.substring(34,44)));
        //if (!loadFlag) {
        //    inventory.setAreaname(toString(line.substring(44,54))); // use as default to show on screen
        //    inventory.setDomain(toString(line.substring(54,64)));   // use as default to show on screen
        //} // if (!loadFlag)

        if (dbg) System.out.println("<br>format01: line = " + line);
        if (dbg) System.out.println("<br>format01: subCode = " + subCode);

    } // format01


    /**
     * Get the survey notes
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format02(String line,  int lineCount) {

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // only one code 02 record allowed per load file
        code02Count++;
        if (prevCode != 1) {
            outputError(lineCount + " - Fatal - " +
                //"More than 1 code 02 record in data file: " + code02Count);
                "Code 02 record does not follow code 01 record: " + code02Count);
        } // if (prevCode != 1)

        //01  a2  format code always '02'                   n/a
        //02  a9  survey_id   e.g. '1997/0001'              survey
        //03  a50 notes_1 general notes                     survey

        // get rid of the code and survey_id
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        for (int i = 0; i < 2; i++) t.nextToken();
        //String notes1 = toString(line.substring(13));
        String notes1 = t.nextToken();                                      //ub08
        while (t.hasMoreTokens()) notes1 += " " + t.nextToken();            //ub08
        survey.setNotes1(notes1);  // not strictly neccessary - but used in report

/*
        // update the survey record - new survey record created in checkSurveyExists
        if (dbg) System.err.println("before if");
        if (loadFlag && !"".equals(notes1)) {

            // define the value that should be updated
            MrnSurvey updateSurvey = new MrnSurvey();
            updateSurvey.setNotes1(notes1);

            // define the 'where' clause
            MrnSurvey whereSurvey = new MrnSurvey(survey.getSurveyId());

            // do the update
            if (dbg3) System.err.println("<br>format02: upd whereSurvey = " + whereSurvey);
            if (dbg3) System.err.println("<br>format02: upd updateSurvey = " + updateSurvey);
            if (dbg) System.err.println("before .upd");
            try {
                whereSurvey.upd(updateSurvey);
            } catch(Exception e) {
                System.err.println("format02: upd whereSurvey = " + whereSurvey);
                System.err.println("format02: upd updateSurvey = " + updateSurvey);
                System.err.println("format02: upd sql = " + whereSurvey.getUpdStr());
                e.printStackTrace();
            } // try-catch

        } // if (loadFlag && !"".equals(notes1))
*/
        if (dbg) System.out.println("<br>format02: survey = " + survey);
    } // format02


    /**
     * Get the station data
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format03(String line,  int lineCount) {
        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // There must be data records after each code 03 record, therefore
        // this record must not be preceded by a code 03 record.
        if (prevCode == 3) {
            outputError((lineCount-1) + " - Fatal - " +
                "No data for station: " + stationId);
        } // if (prevCode == 3)

        //01  a2  format code always '03'                                   n/a
        //02  a12 stnid   station id: composed of as following:             station
        //          1-3: institute id
        //          4-8: 1st 5 of expedition name ??
        //          9-12: station number
        //03  a10 stnnam  station number                                    station
        //04  i8  date    yyyymmdd, e.g. 19900201                           station
        //06  i3  latdeg  latitude degrees (negative for north)             station
        //07  f6.3    latmin  latitude minutes (with decimal seconds)       station
        //08  i4  londeg  longitude degrees (negative for west)             station
        //09  f6.3    lonmin  longitude minutes (with decimal seconds)      station
        //10  f7.2    stndep  station depth                                 station
        //11  a1  up indicator    = 'D' for down, = 'U' for up (def = 'D')  n/a
        //12  a8  grid_no for sfri only - grid number                       sfri_grid

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code

        station.setSurveyId(survey.getSurveyId());
        if (t.hasMoreTokens()) station.setStationId(t.nextToken());
        if (t.hasMoreTokens()) station.setStnnam(t.nextToken());
        if (t.hasMoreTokens()) {
            String temp = t.nextToken();
            startDate = temp.substring(0,4) + "-" + temp.substring(4,6) +
                "-" + temp.substring(6);
            station.setDateStart(startDate + " 00:00:00.0");
            if (dbg) System.out.println("<br>format03: timeZone = " +
                timeZone + ", startDate = " + station.getDateStart());
            // convert to UTC?
            /*
            if ("sast".equals(timeZone)) {
                java.util.GregorianCalendar calDate = new java.util.GregorianCalendar();
                calDate.setTime(station.getDateStart());
                calDate.add(java.util.Calendar.HOUR, -2);
                //station.setDateStart(new Timestamp(calDate.getTime().getTime()));
                //Timestamp dateTimeMin2 = new Timestamp(calDate.getTime().getTime());
                java.text.SimpleDateFormat formatter =
                    new java.text.SimpleDateFormat ("yyyy-MM-dd");
                station.setDateStart(formatter.format(calDate.getTime()) + " 00:00:00.0");
            } // if ('sast'.equals(timeZone))
            */
            if (dbg4) System.out.println("<br>format03: timeZone = " +
                timeZone + ", startDate = " + station.getDateStart());
            station.setDateEnd(station.getDateStart());

        } // if (t.hasMoreTokens())

        /*  ub03
        float degree = 0f;  float minute= 0f;
        if (t.hasMoreTokens()) degree = toFloat(t.nextToken(), 1f);
        if (t.hasMoreTokens()) minute = toFloat(t.nextToken(), 60f);
        station.setLatitude(degree + minute);
        */
        float latitude = 0f;                                                //ub03
        if (t.hasMoreTokens()) latitude = toFloat(t.nextToken(), 1f);       //ub03
        station.setLatitude(latitude);                                      //ub03
        if ((latitude > 90f) || (latitude < -90f)) {
            outputError(lineCount + " - Fatal - " +
                "Latitude invalid ( > 90 or < -90) : " +
                latitude + " : " + station.getStationId(""));
        } // if ((latitude > 90f) || (latitude < -90f))


        //sign = line.substring(46,47);
        //temp = toFloat(line.substring(47,50), 1f) +
        //    toFloat(line.substring(50,55), 60000f);
        //station.setLongitude(("-".equals(sign) ? -temp : temp));
        /* ub03
        if (t.hasMoreTokens()) degree = toFloat(t.nextToken(), 1f);
        if (t.hasMoreTokens()) minute = toFloat(t.nextToken(), 60f);
        station.setLongitude(degree + minute);
        */
        float longitude = 0f;                                               //ub03
        if (t.hasMoreTokens()) longitude = toFloat(t.nextToken(), 1f);      //ub03
        station.setLongitude(longitude);                                    //ub03
        if ((longitude > 180f) || (longitude < -180f)) {
            outputError(lineCount + " - Fatal - " +
                "Longitude invalid ( > 180 or < -180) : " +
                longitude + " : " + station.getStationId(""));
        } // if ((longitude > 180f) || (longitude < -180f))

        if (t.hasMoreTokens()) station.setStndep(toFloat(t.nextToken(), 1f));

        if (t.hasMoreTokens()) upIndicator = t.nextToken().trim().toUpperCase();

        sfriGrid.setSurveyId(survey.getSurveyId());
        sfriGrid.setStationId(station.getStationId());
        if (t.hasMoreTokens()) sfriGrid.setGridNo(toString(t.nextToken()));

        // the first three letters of the station Id must be the institute Id
// ub10
// take out because of profiling floats - station id starts with D
//        if (!institute.equals(station.getStationId("").substring(0,3))) {
//            if (dbg) System.out.println("<br>institute = " + institute);
//            outputError(lineCount + " - Fatal - " +
//                "Station Id does not start with institute Id " + institute +
//                ": " + station.getStationId(""));
//        } // if (!institute.equals(station.getStationId().substring(0,3)))

        stationId = station.getStationId("");

        // update the minimum and maximum dates
        if (station.getDateStart().before(dateMin)) {
            dateMin = station.getDateStart();
        } // if (station.getDateStart().before(dateMin))
        if (station.getDateEnd().after(dateMax)) {
            dateMax = station.getDateEnd();
        } // if (station.getDateStart().before(dateMin))

        // update the minimum and maximum latitudes
        if (station.getLatitude() < latitudeMin) {
            latitudeMin = station.getLatitude();
        } // if (station.getLatitude() < latitudeMin)
        if (station.getLatitude() > latitudeMax) {
            latitudeMax = station.getLatitude();
        } // if (station.getLatitude() < latitudeMin)

        // update the minimum and maximum longitudes
        if (station.getLongitude() < longitudeMin) {
            longitudeMin = station.getLongitude();
        } // if (station.getLongitude() < LongitudeMin)
        if (station.getLongitude() > longitudeMax) {
            longitudeMax = station.getLongitude();
        } // if (station.getLongitude() < LongitudeMin)

        // update the station counter
        stationCount++;
        if (dbg) System.out.println("");
        if (dbg) System.out.println("<br>format03: station = " + station);
        if (dbg) System.out.println("<br>format03: sfriGrid = " + sfriGrid);
        if (dbg) System.out.println("<br>format03: stationCount = " + stationCount);

    } // format03


    /**
     * Get the station weather data.  Check whether the station Id is already
     * on the database, or whether the unique index is already used
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    int format04(String line,  int lineCount) {

        // local variables
        String splDattim = "";
        String tmpStationId = "";

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // code 04 must be preceded by a code 03 or another code 04
        if ((prevCode != 3) && (prevCode != 4)) {
            outputError(lineCount + " - Fatal - " +
                "Code 04 not preceded by code 03");
        } // if (!"03".equals(prevCode))

        // get the data off the data line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code
        int subCode = 0;
        if (t.hasMoreTokens()) subCode = toInteger(t.nextToken());
        if (t.hasMoreTokens()) tmpStationId = toString(t.nextToken());
        // station Id must match that of previous station record
        checkStationId(lineCount, tmpStationId);

        switch (subCode) {

            //01  a2    format code always "04"                         n/a
            //02  a1    format subCode  Always '1'                      n/a
            //03  a12   stnid   station id: composed as for format 03   weather
            //04  a5    subdes  substation descriptor e.g. CTD, XBT     watphy
            //05  i6    spltim  hhmmss UCT, e.g. 142134                 watphy
            //06  f6.1  atmosph_pres    in mBars                        weather
            //07  a5    cloud                                           weather
            //08  f4.1  drybulb in deg C                                weather
            //09  f4.1  surface temp    in deg C                        weather
            //10  a10   nav_equip_type                                  weather
            case 1: if (t.hasMoreTokens())
                        subdes = toString(t.nextToken().toUpperCase());
                    switch (dataType) {  // - moved to loadData
                       case CURRENTS: currents.setSubdes(subdes); break;
                       case SEDIMENT: sedphy.setSubdes(subdes);  break;
                       case WATER: watphy.setSubdes(subdes);  break;
                       case WATERWOD: watphy.setSubdes(subdes);  break;
                    } // switch (dataType)
                    if (t.hasMoreTokens()) splDattim = toString(t.nextToken());
                    if (t.hasMoreTokens())
                        weather.setAtmosphPres(toFloat(t.nextToken(), 1f));
                    if (t.hasMoreTokens()) weather.setCloud(toString(t.nextToken()));
                    if (t.hasMoreTokens()) weather.setDrybulb(toFloat(t.nextToken(), 1f));
                    if (t.hasMoreTokens()) weather.setSurfaceTmp(toFloat(t.nextToken(), 1f));
                    if (t.hasMoreTokens()) weather.setNavEquipType(toString(t.nextToken()));
                    break;
            //01  a2    format code always '04'                         n/a
            //02  a1    format subCode  Always '2'                      n/a
            //03  a12   stnid   station id: composed as for format 03   weather
            //04  i3    swell dir   in degrees TN                       weather
            //05  i2    swell height    in m                            weather
            //06  i2    swell period    in s                            weather
            //07  i2    transparency    coded                           weather
            //08  a2    visibility code coded                           weather
            //09  i2    water colour    coded                           weather
            //10  f4.1  wetbulb in deg C                                weather
            //11  i3    wind direction  in degrees TN                   weather
            //12  f4.1  wind speed  in m/s                              weather
            //13  a2    weather code                                    weather
            case 2: if (t.hasMoreTokens()) weather.setSwellDir(toInteger(t.nextToken()));
                    if (t.hasMoreTokens()) weather.setSwellHeight(toFloat(t.nextToken(), 1f));
                    if (t.hasMoreTokens()) weather.setSwellPeriod(toInteger(t.nextToken()));
                    if (t.hasMoreTokens()) weather.setTransparency(toInteger(t.nextToken()));
                    if (t.hasMoreTokens()) weather.setVisCode(toString(t.nextToken()));
                    if (t.hasMoreTokens()) weather.setWaterColor(toInteger(t.nextToken()));
                    if (t.hasMoreTokens()) weather.setWetbulb(toFloat(t.nextToken(), 1f));
                    if (t.hasMoreTokens()) weather.setWindDir(toInteger(t.nextToken()));
                    if (t.hasMoreTokens()) weather.setWindSpeed(toFloat(t.nextToken(), 1f));
                    if (t.hasMoreTokens()) weather.setWeatherCode(toString(t.nextToken()));
                    break;
        } // switch (subCode)


        // time is on record with subCode = 1
        if ((subCode == 1) && !"".equals(splDattim)) {
            String seconds = "00";
            if (splDattim.length() == 6) {
                seconds = splDattim.substring(4);
            } // if (splDattim.length() == 6)
            startDateTime = startDate + " " + splDattim.substring(0,2) +
                ":" + splDattim.substring(2,4) + ":" + seconds + ".0";
            if (dbg) System.out.println("<br>format04: startDateTime = " + startDateTime);
            checkForValidDate(startDateTime);

            // convert to UTC?
            if ("sast".equals(timeZone)) {
                GregorianCalendar calDate = new GregorianCalendar();
                calDate.setTime(Timestamp.valueOf(startDateTime));
                calDate.add(Calendar.HOUR, -2);
                SimpleDateFormat formatter =
                    new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.s");
                startDateTime = formatter.format(calDate.getTime());
            } // if ('sast'.equals(timeZone))
            if (dbg4) System.out.println("<br>format04: startDateTime = " + startDateTime);
            //watphy.setSpldattim(startDateTime);  //- moved to loadData
            switch (dataType) {
               case CURRENTS: currents.setSpldattim(startDateTime); break;
               case SEDIMENT: sedphy.setSpldattim(startDateTime);  break;
               case WATER: watphy.setSpldattim(startDateTime);  break;
               case WATERWOD: watphy.setSpldattim(startDateTime);  break;
            } // switch (dataType)
        } // if ((subCode == 1) && !"".equals(splDattim))

        if (dbg) System.out.println("<br>format04: weather = " + weather);
        if (dbg) {
            System.out.print("<br>format04: ");
            switch (dataType) {
               case CURRENTS: System.out.println("currents = " + currents); break;
               case SEDIMENT: System.out.println("sedphy = " + sedphy); break;
               case WATER: System.out.println("watphy = " + watphy);  break;
               case WATERWOD: System.out.println("watphy = " + watphy);  break;
            } // switch (dataType)
        } // if (dbg)

        return subCode;

    } // format04


    /**
     * Get the currents / sedphy / watphy data
     * Check the depth interval between samples, and get the minimum and
     * maximum depth.
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format05(String line,  int lineCount) {

        float spldepDifference = 0f;

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        String tmpStationId = "";
        String tmpSubdes = "";
        float tmpSpldep = -9999f;

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code

        int subCode = 0;
        if (dataType == WATERWOD) {
            if (t.hasMoreTokens()) subCode = toInteger(t.nextToken());
            switch (subCode) {
                case 1: if (t.hasMoreTokens()) t.nextToken(); //watProfQC.setStationId(toString(t.nextToken()));
                        if (t.hasMoreTokens()) //set profile temperature quality flag
                            watProfQC.setTemperature((toInteger(t.nextToken())));
                        if (t.hasMoreTokens()) //set profile salinity quality flag
                            watProfQC.setSalinity((toInteger(t.nextToken())));
                        if (t.hasMoreTokens()) //set profile disoxygen quality flag
                            watProfQC.setDisoxygen((toInteger(t.nextToken())));
                        break;

            } // switch (subCode)
        } // if (dataType = WATERWOD)
         if (dbg) System.out.println("format05: subCode = " + subCode);

        if (dataType == CURRENTS) {

            //01  a2    format code always "05" n/a
            //02  a12   stnid   station id: composed as for format 03   currents
            //03  f7.2  sample depth    in m    currents
            //04  i3    current dir in deg TN   currents
            //05  f7.3  current speed   in m/s  currents

            if (t.hasMoreTokens()) currents.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) currents.setSpldep(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) currents.setCurrentDir(toInteger(t.nextToken()));
            if (t.hasMoreTokens()) currents.setCurrentSpeed(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) {                                        //ub14
                String percGood = t.nextToken();                            //ub14
                while (t.hasMoreTokens()) percGood += " " + t.nextToken();  //ub14
                currents.setPercGood(percGood);                             //ub14
            } // if (t.hasMoreTokens())                                     //ub14

            tmpStationId = currents.getStationId("");
            tmpSubdes = currents.getSubdes("");
            tmpSpldep = currents.getSpldep();

        } else if (dataType == SEDIMENT) {

            // This is a new sub-station record - set flags for code 06 to 13
            // to false
            code06Count = 0;
            code07Count = 0;
            //code08Count = 0;
            code09Count = 0;
            //code10Count = 0;
            code11Count = 0;
            code12Count = 0;
            code13Count = 0;

            //01  a2  format code always �05" n/a
            //02  a12 stnid   station id: composed as for format 03   sedphy
            //03  f7.2    sample depth    in m    sedphy
            //04  f6.3    cod mg O2 / litre   sedphy
            //05  f8.4    dwf     sedphy
            //06  i5  meanpz  �ns     sedphy
            //07  i5  medipz  �ns     sedphy
            //08  f8.3    kurt        sedphy
            //09  f8.3    skew        sedphy

            if (t.hasMoreTokens()) sedphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) sedphy.setSpldep(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedphy.setCod(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedphy.setDwf(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedphy.setMeanpz(toInteger(t.nextToken()));
            if (t.hasMoreTokens()) sedphy.setMedipz(toInteger(t.nextToken()));
            if (t.hasMoreTokens()) sedphy.setKurt(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedphy.setSkew(toFloat(t.nextToken(), 1f));

            tmpStationId = sedphy.getStationId("");
            tmpSubdes = sedphy.getSubdes("");
            tmpSpldep = sedphy.getSpldep();
            if (dbg) System.out.println("<br>format05: subdes = " + subdes +
                ", sedphy.getSubdes() = " + sedphy.getSubdes(""));
            if (dbg) System.out.println("<br>format05: tmpSpldep = " + tmpSpldep);

        } else if ((dataType == WATER) || (dataType == WATERWOD)) {

            // This is a new sub-station record - set flags for code 06 to 13
            // to false
            code06Count = 0;
            code07Count = 0;
            code08Count = 0;
            code09Count = 0;
            code10Count = 0;
            code11Count = 0;
            code12Count = 0;
            code13Count = 0;

            // subCode = 1 - ignore
            // subCode = 0 (WATER) or subCode = 2 (WATERWOD)
            if (subCode != 1) {
                //01  a2    format code always "05" n/a
                //02  a12   stnid   station id: composed as for format 03   watphy
                //03  f7.2  sample depth    in m    watphy
                //04  f5.2  temperature in deg C    watphy
                //05  f6.3  salinity    in parts per thousand (?)   watphy
                //06  f5.2  dis oxygen  in ml / litre   watphy

                if (t.hasMoreTokens()) watphy.setStationId(toString(t.nextToken()));
                if (t.hasMoreTokens()) watphy.setSpldep(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set spldep quality flag
                    watQC.setSpldep((toInteger(t.nextToken())));

                if (t.hasMoreTokens()) watphy.setTemperature(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set temperature quality flag
                    watQC.setTemperature((toInteger(t.nextToken())));

                if (t.hasMoreTokens()) watphy.setSalinity(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set salinity quality flag
                    watQC.setSalinity((toInteger(t.nextToken())));

                if (t.hasMoreTokens()) watphy.setDisoxygen(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set disoxygen quality flag
                     watQC.setDisoxygen((toInteger(t.nextToken())));

                if (t.hasMoreTokens()) watphy.setTurbidity(toFloat(t.nextToken(), 1f));
                if (t.hasMoreTokens()) watphy.setPressure(toFloat(t.nextToken(), 1f));  // ub09
                if (t.hasMoreTokens()) watphy.setFluorescence(toFloat(t.nextToken(), 1f));  // ub09

                if (watphy.getSpldep() == Tables.FLOATNULL) {               // ub11
                    if ((watphy.getPressure() != Tables.FLOATNULL) &&       // ub11
                        (station.getLatitude() != Tables.FLOATNULL)) {      // ub11
                            watphy.setSpldep(                               // ub11
                                calcDepth(watphy.getPressure(),             // ub11
                                station.getLatitude()));                    // ub11
                    } // if ((watphy.getPressure() != Tables.FLOATNULL) && .// ub11
                } // if (watphy.getSpldep() == Tables.FLOATNULL)            // ub11

                tmpStationId = watphy.getStationId("");
                tmpSubdes = watphy.getSubdes("");
                tmpSpldep = watphy.getSpldep();
                if (dbg) System.out.println("<br>format05: subdes = " + subdes +
                    ", watphy.getSubdes() = " + watphy.getSubdes(""));
            } // if (subCode != 1)

        } // if (dataType == CURRENTS)

        // subCode = 1 - ignore
        // subCode = 0 (WATER/CURRENTS/SEDIMENT) or subCode = 2 (WATERWOD)
        if (subCode != 1) {
            // station Id must match that of previous station record
            checkStationId(lineCount, tmpStationId);

            // samples must be at least 0.5 metre deeper than the previous.
            // samples must be at least 0.1 metre deeper than the previous. (2012/03/02)
            // If this is not the case, reject the sample.
            // Only valid for CTD-type data, not for XBT or currents
            if ("CTD".equals(tmpSubdes)) {
                if ("U".equals(upIndicator)) {
                    spldepDifference = prevDepth - tmpSpldep;
                } else {
                    spldepDifference = tmpSpldep - prevDepth;
                } // if ("U".equals(upIndicator))
            } else {
                spldepDifference = 1f;
            } // if ("CTD".equals(tmpSubdes))

            //if ((spldepDifference < 0.5f) && (tmpSpldep != 0f) &&
            if ((spldepDifference < 0.1f) && (tmpSpldep != 0f) &&  // (2012/03/02)
                    (prevDepth != 0f)) {
                rejectDepth = true;
                stationSampleRejectCount++;
                sampleRejectCount++;

                if ((dataType == WATER) || (dataType == WATERWOD)) {
                    watchem1 = new MrnWatchem1();
                    watchem1 = new MrnWatchem1();
                    watchl = new MrnWatchl();
                    watnut = new MrnWatnut();
                    watpol1 = new MrnWatpol1();
                    watpol2 = new MrnWatpol2();
                } // if ((dataType == WATER) || (dataType == WATERWOD))
                if (dataType == WATERWOD) {
                    watQC = new MrnWatqc();
                } // if (dataType == WATERWOD)
            } else {
                rejectDepth = false;
                prevDepth = tmpSpldep;

                // update the minimum and maximum depth
                if (tmpSpldep < depthMin) {
                    depthMin = tmpSpldep;
                } // if (tmpSpldep < depthMin)
                if (tmpSpldep > depthMax) {
                    depthMax = tmpSpldep;
                } // if (tmpSpldep < depthMin)

            } // if ((spldepDifference < 0.5f) &&

            // update the counters
            sampleCount++;
            stationSampleCount++;

            if (dbg) System.out.println("<br>format05: depthMax = " + depthMax);
            // keep the maximum depth for the station
            station.setMaxSpldep(depthMax);
            //if (dbg) System.out.println("format05: station = " + station);
            if (dbg) {
                if (dataType == CURRENTS) {
                    System.out.println("<br>format05: currents = " + currents);
                } else if (dataType == SEDIMENT) {
                    System.out.println("<br>format05: sedphy = " + sedphy);
                } else if (dataType == WATER) {
                    System.out.println("<br>format05: watphy = " + watphy);
                } else if (dataType == WATERWOD) {
                    System.out.println("format05: watProfQC = " + watProfQC);
                    System.out.println("format05: watQC = " + watQC);
                    System.out.println("format05: watphy = " + watphy);
                } // if (dataType == CURRENTS)
            } // if (dbg)

        } // if (subCode != 1)

    } // format05


    /**
     * Get the rest of sedphy / nutrients and chlorofil data.
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format06(String line,  int lineCount) {

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code

        int subCode = 0;
        if (dataType == WATERWOD) {
            if (t.hasMoreTokens()) subCode = toInteger(t.nextToken());
            switch (subCode) {
                case 1: if (t.hasMoreTokens()) watProfQC.setStationId(toString(t.nextToken()));
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile no2 quality flag
                        if (t.hasMoreTokens()) //set profile no3 quality flag
                            watProfQC.setNo3((toInteger(t.nextToken())));
                        if (t.hasMoreTokens()) //set profile po4 quality flag
                            watProfQC.setPo4((toInteger(t.nextToken())));
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile ptot quality flag
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile sio3 quality flag
                        if (t.hasMoreTokens()) //set profile sio4 quality flag
                            watProfQC.setSio3((toInteger(t.nextToken())));
                        if (t.hasMoreTokens()) //set profile chla quality flag
                            watProfQC.setChla((toInteger(t.nextToken())));
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile chlb quality flag
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile chlc quality flag
                        break;
            } // switch (subCode)
        } // if (dataType = WATERWOD)


        // there can only be one code 06 record per substation
        if (subCode != 1) code06Count++;  // ignore WATERWOD code 1
        if (code06Count > 1) {
            outputError(lineCount + " - Fatal - " +
                "More than 1 code 06 record in substation");
        } // if (code06Count > 1)

        if (dataType == SEDIMENT) {

            //01  a2  format code always �06" n/a/a
            //02  a12 stnid   station id: composed as for format 03   sedphyhy
            //03  f4.1    pctsat  percent     sedphywatnut
            //04  f4.1    pctsil  percent     sedphywatnut
            //05  i5  permty  seconds     sedphyM   watnut
            //06  f4.1    porsty  percent     sedphyM   watnut
            //07  f5.1    splvol  litre   sedphy = �M   watnut
            //08  i3  spldis      sedphytre watchl
            //09  f8.1    sievsz      sedphywatchl

            if (t.hasMoreTokens()) sedphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) sedphy.setPctsat(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedphy.setPctsil(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedphy.setPermty(toInteger(t.nextToken()));
            if (t.hasMoreTokens()) sedphy.setPorsty(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedphy.setSplvol(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedphy.setSpldis(toInteger(t.nextToken()));   // ub07
            if (t.hasMoreTokens()) sedphy.setSievsz(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, sedphy.getStationId(""));

            if (dbg) System.out.println("format06: sedphy = " + sedphy);

        } else if ((dataType == WATER) || (dataType == WATERWOD)) {

            //01  a2    format code always "06" n/a
            //02  a12   stnid   station id: composed as for format 03   watphy
            //03  f6.2  NO2 �gm atom / litre = uM   watnut
            //04  f6.2  NO3 �gm atom / litre = uM   watnut
            //05  f6.2  PO4 �gm atom / litre = uM   watnut
            //06  f7.3  Ptot    �gm atom / litre = �M   watnut
            //07  f7.2  SIO3    �gm atom / litre = �M   watnut
            //08  f7.2  SIO4    �gm atom / litre = �M   watnut
            //09  f7.3  chla    �gm / litre watchl
            //10  f7.3  chlb    �gm / litre watchl
            //11  f7.3  chlc    �gm / litre watchl


            // subCode = 1 - ignore
            // subCode = 0 (WATER) or subCode = 2 (WATERWOD)
            if (subCode != 1) {
                if (t.hasMoreTokens()) watphy.setStationId(toString(t.nextToken()));

                if (t.hasMoreTokens()) watnut.setNo2(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile no2 quality flag

                if (t.hasMoreTokens()) watnut.setNo3(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set No3 quality flag
                    watQC.setNo3((toInteger(t.nextToken())));

                if (t.hasMoreTokens()) watnut.setPo4(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set Po4 quality flag
                    watQC.setPo4((toInteger(t.nextToken())));

                if (t.hasMoreTokens()) watnut.setP(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile ptot quality flag

                if (t.hasMoreTokens()) watnut.setSio3(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile Sio3 quality flag

                if (t.hasMoreTokens()) watnut.setSio3(toFloat(t.nextToken(), 1f));   // ub07
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set Sio4 quality flag
                    watQC.setSio3((toInteger(t.nextToken())));

                if (t.hasMoreTokens()) watchl.setChla(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set chla quality flag
                    watQC.setChla((toInteger(t.nextToken())));

                if (t.hasMoreTokens()) watchl.setChlb(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile chlb quality flag

                if (t.hasMoreTokens()) watchl.setChlc(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile chlc quality flag

                // station Id must match that of previous station record
                checkStationId(lineCount, watphy.getStationId(""));

                if (dbg) System.out.println("format06: watnut = " + watnut);
                if (dbg) System.out.println("format06: watchl = " + watchl);

            } // if (subCode != 1)

        } // if (dataType == SEDIMENT)

    } // format06


    /**
     * Get the sedchem1 / watchem1 chemical data
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format07(String line,  int lineCount) {

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code

        int subCode = 0;
        if (dataType == WATERWOD) {
            if (t.hasMoreTokens()) subCode = toInteger(t.nextToken());
            switch (subCode) {
                case 1: if (t.hasMoreTokens()) watProfQC.setStationId(toString(t.nextToken()));
                        if (t.hasMoreTokens()) //set profile DIC quality flag
                            watProfQC.setDic((toInteger(t.nextToken())));
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile DOC quality flag
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile fluoride quality flag
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile iodene quality flag
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile iodate quality flag
                        break;
            } // switch (subCode)
        } // if (dataType = WATERWOD)

        // there can only be one code 07 record per substation
        if (subCode != 1) code07Count++;
        if (code07Count > 1) {
            outputError(lineCount + " - Fatal - " +
                "More than 1 code 07 record in substation");
        } // if (code07Count > 1)


        if (dataType == SEDIMENT) {

            //01  a2  format code always "07" n/a
            //02  a12 stnid   station id: composed as for format 03   sedphy
            //03  f7.3    toc �gm / gram  sedchem1
            //04  f8.3    fluoride    �gm / gram  sedchem1
            //05  f7.2    kjn �gm / gram  sedchem1
            //06  f7.3    oxa �gm / gram  sedchem1
            //07  f7.3    ptot    �gm / gram  sedchem1

            if (t.hasMoreTokens()) sedphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) sedchem1.setToc(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedchem1.setFluoride(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedchem1.setKjn(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedchem1.setOxa(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedchem1.setPtot(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, sedphy.getStationId(""));

            if (dbg) System.out.println("format07: sedchem1 = " + sedchem1);

        } else if ((dataType == WATER) || (dataType == WATERWOD)) {

            //01  a2     format code always "07" n/a
            //02  a12    stnid   station id: composed as for format 03   watphy
            //03  f10.3  DIC �gm / litre watchem1
            //04  f7.2   DOC mgm / litre watchem1
            //05  f8.3   fluoride    �gm / litre watchem1
            //06  f7.2   iodene  �gm atom / litre = �M   watchem1
            //07  f7.3   iodate  �gm atom / litre = �M   watchem1

            // subCode = 1 - ignore
            // subCode = 0 (WATER) or subCode = 2 (WATERWOD)
            if (subCode != 1) {
                if (t.hasMoreTokens()) watphy.setStationId(toString(t.nextToken()));

                if (t.hasMoreTokens()) watchem1.setDic(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set DIC quality flag
                    watQC.setDic((toInteger(t.nextToken())));

                if (t.hasMoreTokens()) watchem1.setDoc(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile DOC quality flag

                if (t.hasMoreTokens()) watchem1.setFluoride(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile fluoride quality flag

                if (t.hasMoreTokens()) watchem1.setIodene(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile iodene quality flag

                if (t.hasMoreTokens()) watchem1.setIodate(toFloat(t.nextToken(), 1f));
                if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile iodate quality flag

                // station Id must match that of previous station record
                checkStationId(lineCount, watphy.getStationId(""));

                if (dbg) System.out.println("format07: watchem1 = " + watchem1);

            } // if (subCode != 1)

        } // if (dataType == SEDIMENT)

    } // format07


    /**
     * Get the rest of the watchem1 chemical data
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format08(String line,  int lineCount) {

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code

        int subCode = 0;
        if (dataType == WATERWOD) {
            if (t.hasMoreTokens()) subCode = toInteger(t.nextToken());
            switch (subCode) {
                case 1: if (t.hasMoreTokens()) watProfQC.setStationId(toString(t.nextToken()));
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile kjn quality flag
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile nh3 quality flag
                        if (t.hasMoreTokens()) t.nextToken(); // is no profile oxa quality flag
                        if (t.hasMoreTokens()) //set profile ph quality flag
                            watProfQC.setPh((toInteger(t.nextToken())));
                        break;
            } // switch (subCode)
        } // if (dataType = WATERWOD)

        // there can only be one code 08 record per substation
        if (subCode != 1) code08Count++;
        if (code08Count > 1) {
            outputError(lineCount + " - Fatal - " +
                "More than 1 code 08 record in substation");
        } // if (code08Count > 1)

        //01  a2    format code always "08" n/a
        //02  a12   stnid   station id: composed as for format 03   watphy
        //03  f7.2  kjn �gm atom / litre = �M   watchem1
        //04  f6.2  nh3 �gm atom / litre = �M   watchem1
        //05  f7.3  oxa mgm / litre watchem1
        //07  f5.2  ph      watchem1

        // subCode = 1 - ignore
        // subCode = 0 (WATER) or subCode = 2 (WATERWOD)
        if (subCode != 1) {
            if (t.hasMoreTokens()) watphy.setStationId(toString(t.nextToken()));

            if (t.hasMoreTokens()) watchem1.setKjn(toFloat(t.nextToken(), 1f));
            if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile kjn quality flag

            if (t.hasMoreTokens()) watchem1.setNh3(toFloat(t.nextToken(), 1f));
            if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile nh3 quality flag

            if (t.hasMoreTokens()) watchem1.setOxa(toFloat(t.nextToken(), 1f));
            if ((dataType == WATERWOD) && (t.hasMoreTokens())) t.nextToken(); // is no profile oxa quality flag

            if (t.hasMoreTokens()) watchem1.setPh(toFloat(t.nextToken(), 1f));
            if ((dataType == WATERWOD) && (t.hasMoreTokens())) // set ph quality flag
                watQC.setPh((toInteger(t.nextToken())));
        } // if (subCode != 1)

        // station Id must match that of previous station record
        checkStationId(lineCount, watphy.getStationId(""));

        if (dbg) System.out.println("format08: watchem1 = " + watchem1);

    } // format08


    /**
     * Get the sedchem2 / watchem2 chemical data
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format09(String line,  int lineCount) {

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // there can only be one code 09 record per substation
        code09Count++;
        if (code09Count > 1) {
            outputError(lineCount + " - Fatal - " +
                "More than 1 code 09 record in substation");
        } // if (code09Count > 1)

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code

        if (dataType == SEDIMENT) {

            //01  a2  format code always "09" n/a
            //02  a12 stnid   station id: composed as for format 03   sedphy
            //03  f10.3   calcium �gm / gram  sedchem2
            //04  f8.2    magnesium   �gm / gram  sedchem2
            //05  f7.3    sulphide(SO3)   �gm / gram  sedchem2
            //06  f8.3    potassium   �gm / gram  sedchem2
            //07  f8.2    sodium  �gm / gram  sedchem2
            //08  f9.3    strontium   �gm / gram  sedchem2

            if (t.hasMoreTokens()) sedphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) sedchem2.setCalcium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedchem2.setMagnesium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedchem2.setSo3(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedchem2.setPotassium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedchem2.setSodium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedchem2.setStrontium(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, sedphy.getStationId(""));

            if (dbg) System.out.println("format09: sedchem2 = " + sedchem2);

        } else if (dataType == WATER) {

            //01  a2  format code always "09" n/a
            //02  a12 stnid   station id: composed as for format 03   watphy
            //03  f7.3    suspendedSolids mgm / litre watchem2
            //04  f10.3   calcium �gm / litre watchem2
            //05  f7.4    sulphate(SO4)   gm / litre  watchem2
            //06  f8.3    potassium   �gm / litre watchem2
            //07  f8.2    magnesium   �gm / litre watchem2
            //08  f8.2    sodium  �gm / litre watchem2
            //09  f9.3    strontium   �gm / litre watchem2

            if (t.hasMoreTokens()) watphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) watchem2.setSussol(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watchem2.setCalcium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watchem2.setSo4(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watchem2.setPotassium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watchem2.setMagnesium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watchem2.setSodium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watchem2.setStrontium(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, watphy.getStationId(""));

            if (dbg) System.out.println("format09: watchem2 = " + watchem2);

        } // if (dataType == SEDIMENT)

    } // format09


    /**
     * Get the rest of the watchem2 chemical data
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format10(String line,  int lineCount) {

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // there can only be one code 10 record per substation
        code10Count++;
        if (code10Count > 1) {
            outputError(lineCount + " - Fatal - " +
                "More than 1 code 10 record in substation");
        } // if (code10Count > 1)

        //01  a2  format code always �10" n/a
        //02  a12 stnid   station id: composed as for format 03   watphy
        //03  f7.2    pah �gm / litre watchem2
        //04  f7.3    rubidium    �gm / litre watchem2
        //05  f7.3    csaesium    �gm / litre watchem2
        //06  f7.2    hydrocarbons    �gm / litre watchem2

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code
        if (t.hasMoreTokens()) watphy.setStationId(toString(t.nextToken()));
        if (t.hasMoreTokens()) watchem2.setPah(toFloat(t.nextToken(), 1f));
        if (t.hasMoreTokens()) watchem2.setRubidium(toFloat(t.nextToken(), 1f));
        if (t.hasMoreTokens()) watchem2.setCesium(toFloat(t.nextToken(), 1f));
        if (t.hasMoreTokens()) watchem2.setHydrocarbons(toFloat(t.nextToken(), 1f));

        // station Id must match that of previous station record
        checkStationId(lineCount, watphy.getStationId(""));

        if (dbg) System.out.println("format10: watchem2 = " + watchem2);

    } // format10


    /**
     * Get the sedpol1 / watpol1 pollution data
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format11(String line,  int lineCount) {

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // there can only be one code 11 record per substation
        code11Count++;
        if (code11Count > 1) {
            outputError(lineCount + " - Fatal - " +
                "More than 1 code 11 record in substation");
        } // if (code11Count > 1)

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code

        if (dataType == SEDIMENT) {

            //01  a2  format code always "11" n/a
            //02  a12 stnid   station id: composed as for format 03   sedphy
            //03  f7.3    cadmium �gm / gram  sedpol1
            //04  f8.3    lead    �gm / gram  sedpol1
            //05  f8.3    copper  �gm / gram  sedpol1
            //06  f8.3    zinc    �gm / gram  sedpol1
            //07  f8.4    mercury �gm / gram  sedpol1
            //08  f8.3    nickel  �gm / gram  sedpol1

            if (t.hasMoreTokens()) sedphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) sedpol1.setCadmium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setLead(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setCopper(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setZinc(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setMercury(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setNickel(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, sedphy.getStationId(""));

            if (dbg) System.out.println("format11: sedpol1 = " + sedpol1);

        } else if (dataType == WATER) {

            //01  a2  format code always "11" n/a
            //02  a12 stnid   station id: composed as for format 03   watphy
            //03  f7.3    cadmium �gm / litre watpol1
            //04  f8.3    lead    �gm / litre watpol1
            //05  f8.3    copper  �gm / litre watpol1
            //06  f8.3    zinc    �gm / litre watpol1
            //07  f8.4    mercury �gm / litre watpol1
            //08  f8.3    nickel  �gm / litre watpol1

            if (t.hasMoreTokens()) watphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) watpol1.setCadmium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setLead(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setCopper(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setZinc(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setMercury(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setNickel(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, watphy.getStationId(""));

            if (dbg) System.out.println("format11: watpol1 = " + watpol1);

        } // if (dataType == SEDIMENT)

    } // format11


    /**
     * Get the rest of the sedpol1 / watpol1 pollution data
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format12(String line,  int lineCount) {

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // there can only be one code 12 record per substation
        code12Count++;
        if (code12Count > 1) {
            outputError(lineCount + " - Fatal - " +
                "More than 1 code 12 record in substation");
        } // if (code12Count > 1)

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code

        if (dataType == SEDIMENT) {

            //01  a2  format code always "12" n/a
            //02  a12 stnid   station id: composed as for format 03   sedphy
            //03  f10.3   iron    �gm / gram  sedpol1
            //04  f9.3    chromium    �gm / gram  sedpol1
            //05  f8.3    manganese   �gm / gram  sedpol1
            //06  f8.3    cobalt  �gm / gram  sedpol1
            //07  f8.3    selenium    �gm / gram  sedpol1
            //08  f8.3    arsenic �gm / gram  sedpol1

            if (t.hasMoreTokens()) sedphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) sedpol1.setIron(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setChromium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setManganese(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setCobalt(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setSelenium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol1.setArsenic(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, sedphy.getStationId(""));

            if (dbg) System.out.println("format12: sedpol1 = " + sedpol1);

        } else if (dataType == WATER) {

            //01  a2  format code always "12" n/a
            //02  a12 stnid   station id: composed as for format 03   watphy
            //03  f10.3   iron    �gm / litre watpol1
            //04  f9.3    chromium    �gm / litre watpol1
            //05  f8.3    manganese   �gm / litre watpol1
            //06  f8.3    cobalt  �gm / litre watpol1
            //07  f8.3    selenium    �gm / litre watpol1
            //08  f8.3    arsenic �gm / litre watpol1

            if (t.hasMoreTokens()) watphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) watpol1.setIron(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setChromium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setManganese(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setCobalt(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setSelenium(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol1.setArsenic(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, watphy.getStationId(""));

            if (dbg) System.out.println("format12: watpol1 = " + watpol1);

        } // if (dataType == SEDIMENT)

    } // format12


    /**
     * Get the sedpol2 / watpol2 pollution data
     * @param  line       The input line
     * @param  lineCount  The line count
     */
    void format13(String line,  int lineCount) {

        // is this the first record in the file?
        checkFirstRecord(code, lineCount);

        // there can only be one code 13 record per substation
        code13Count++;
        if (code13Count > 1) {
            outputError(lineCount + " - Fatal - " +
                "More than 1 code 13 record in substation");
        } // if (code13Count > 1)

        // get the data off the line
        java.util.StringTokenizer t = new java.util.StringTokenizer(line, " ");
        String dummy = t.nextToken(); // get 'rid' of the code

        if (dataType == SEDIMENT) {

            //01  a2  format code always "13" n/a
            //02  a12 stnid   station id: composed as for format 03   sedphy
            //03  i5  aluminium   �gm / gram  sedpol2
            //04  f8.3    antimony    �gm / gram  sedpol2
            //05  f4.1    bismuth �gm / gram  sedpol2
            //06  f4.1    molybdenum  �gm / gram  sedpol2
            //07  f8.3    silver  �gm / gram  sedpol2
            //08  i4  titanium    �gm / gram  sedpol2
            //09  f5.2    vanadium    �gm / gram  sedpol2

            if (t.hasMoreTokens()) sedphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) sedpol2.setAluminium(toInteger(t.nextToken()));
            if (t.hasMoreTokens()) sedpol2.setAntimony(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol2.setBismuth(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol2.setMolybdenum(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol2.setSilver(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) sedpol2.setTitanium(toInteger(t.nextToken()));
            if (t.hasMoreTokens()) sedpol2.setVanadium(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, sedphy.getStationId(""));

            if (dbg) System.out.println("format13: sedpol2 = " + sedpol2);

        } else if (dataType == WATER) {

            //01  a2  format code always "13" n/a
            //02  a12 stnid   station id: composed as for format 03   watphy
            //03  i5  aluminium   �gm / litre watpol2
            //04  f8.3    antimony    �gm / litre watpol2
            //05  f4.1    bismuth �gm / litre watpol2
            //06  f4.1    molybdenum  �gm / litre watpol2
            //07  f8.3    silver  �gm / litre watpol2
            //08  i4  titanium    �gm / litre watpol2
            //08  f5.2    vanadium    �gm / litre watpol2

            if (t.hasMoreTokens()) watphy.setStationId(toString(t.nextToken()));
            if (t.hasMoreTokens()) watpol2.setAluminium(toInteger(t.nextToken()));
            if (t.hasMoreTokens()) watpol2.setAntimony(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol2.setBismuth(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol2.setMolybdenum(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol2.setSilver(toFloat(t.nextToken(), 1f));
            if (t.hasMoreTokens()) watpol2.setTitanium(toInteger(t.nextToken()));
            if (t.hasMoreTokens()) watpol2.setVanadium(toFloat(t.nextToken(), 1f));

            // station Id must match that of previous station record
            checkStationId(lineCount, watphy.getStationId(""));

            if (dbg) System.out.println("format13: watpol2 = " + watpol2);

        } // if (dataType == SEDIMENT)

    } // format13


    /**
     * Get the existing station details
     * @param  tStation
     * @param  i
     */
    void getExistingStationDetails(MrnStation tStation, int i) {
        // find out the existing station details for the report
        // check if also within a date-time range

        Timestamp tmpSpldattim = null;
        if (dataType == CURRENTS) {
            tmpSpldattim = currents.getSpldattim();
        } else if (dataType == SEDIMENT) {
            tmpSpldattim = sedphy.getSpldattim();
        } else if ((dataType == WATER) || (dataType == WATERWOD)) {
            tmpSpldattim = watphy.getSpldattim();
        } // if (dataType == CURRENTS)

        // find the date range for this station
        java.util.GregorianCalendar calDate = new java.util.GregorianCalendar();
        calDate.setTime(tmpSpldattim); //Timestamp.valueOf(startDateTime));

        calDate.add(java.util.Calendar.MINUTE, -timeRangeVal);
        Timestamp dateTimeMinTs = new Timestamp(calDate.getTime().getTime());

        calDate.add(java.util.Calendar.MINUTE, timeRangeVal*2);
        Timestamp dateTimeMaxTs = new Timestamp(calDate.getTime().getTime());

        if (dbg) System.out.println("<br><br>getExistingStationDetails: " +
            "dateTimeMinTs = " + dateTimeMinTs);
        if (dbg) System.out.println("<br>getExistingStationDetails: " +
            "dateTimeMaxTs = " + dateTimeMaxTs);

        //if (dbg) System.out.println("<br>checkStationExists: in loop: " +
        //    "tStation[i] = " + tStation[i]);

        String where = "STATION_ID='" + tStation.getStationId() + "'";
        if (dbg) System.out.println("<br>getExistingStationDetails: " +
            "data where = " + where);
        String order = "SUBDES";

        String prevSubdes = "";
        int k = 0;

        //
        // are there any currents records for this station
        //------------------------------------------------
        tCurrents = new MrnCurrents().get("*", where, order);
        if (dbg) System.out.println("<br>getExistingStationDetails: " +
            "tCurrents.length = " + tCurrents.length);

        for (int j = 0; j < tCurrents.length; j++) {

            if (dbg) System.out.println("<br>getExistingStationDetails: " +
                "tCurrents[j].getSpldattim() = " +
                tCurrents[j].getSpldattim());

            // only found if station_id's the same or also within date-time range
            if (tCurrents[j].getStationId().equals(station.getStationId()) ||
                (dateTimeMinTs.before(tCurrents[j].getSpldattim()) &&
                tCurrents[j].getSpldattim().before(dateTimeMaxTs))) {
//            if (dateTimeMinTs.before(tCurrents[j].getSpldattim()) &&
//                tCurrents[j].getSpldattim().before(dateTimeMaxTs)) {

                stationExists = true;
                stationExistsArray[i] = true;
                spldattimArray[i] = tCurrents[j].getSpldattim("");
                currentsRecordCountArray[i]++;

                if (dataType == CURRENTS) {
                    dataExists = true;

                    // get the min and max depth
                    if (tCurrents[j].getSpldep() < loadedDepthMin[i]) {
                        loadedDepthMin[i] = tCurrents[j].getSpldep();
                    } // if (tWaphy.getSpldep() < depthMin)
                    if (tCurrents[j].getSpldep() > loadedDepthMax[i]) {
                        loadedDepthMax[i] = tCurrents[j].getSpldep();
                    } // if (tWaphy.getSpldep() < depthMin)

                    // is it the same subdes ?
                    if (dbg) System.out.println(
                        "<br>getExistingStationDetails: subdes = " + subdes +
                        ", currents.getSubdes() = " + currents.getSubdes(""));
                    //if (tCurrents[j].getSubdes("").equals(subdes)) {
                    if (tCurrents[j].getSubdes("").equals(currents.getSubdes(""))) {
                        subdesCount[i]++;
                    } // if (tCurrents[j].getSubdes("").equals(currents.getSubdes(""))

                    if (!prevSubdes.equals(tCurrents[j].getSubdes(""))) {
                        subdesArray[i][k] = tCurrents[j].getSubdes("");
                        if ("".equals(subdesArray[i][k])) subdesArray[i][k] = "none";
                        if (dbg) {
                            System.out.println("<br>getExistingStationDetails: " +
                                "k = " + k);
                            System.out.println("<br>getExistingStationDetails: " +
                                "prevSubdes = " + prevSubdes);
                            System.out.println("<br>getExistingStationDetails: " +
                                "tCurrents[j].getSubdes() = " +
                                tCurrents[j].getSubdes(""));
                            System.out.println("<br>getExistingStationDetails: " +
                                "subdesArray[i][k] = " + subdesArray[i][k]);
                        } // if (dbg)
                        k++;
                    } // if (!prevSubdes.equals(subdesArray[i][k])

                    prevSubdes = tCurrents[j].getSubdes("");

                } // if (dataType == CURRENTS)

            } // if (dateTimeMinTs.before(tCurrents[j].getSpldattim()) ...

        } // for (int j = 0; j < tCurrents.length; j++)

        //
        // are there any sedphy records for this station
        //------------------------------------------------
        tSedphy = new MrnSedphy().get("*", where, order);
        if (dbg) System.out.println("<br>getExistingStationDetails: " +
            "tSedphy.length = " + tSedphy.length);

        for (int j = 0; j < tSedphy.length; j++) {

            if (dbg) System.out.println("<br>getExistingStationDetails: " +
                "tSedphy[j].getSpldattim() = " + tSedphy[j].getSpldattim());

            // only found if station_id's the same or also within date-time range
            if (tSedphy[j].getStationId().equals(station.getStationId()) ||
                (dateTimeMinTs.before(tSedphy[j].getSpldattim()) &&
                tSedphy[j].getSpldattim().before(dateTimeMaxTs))) {
//            if (dateTimeMinTs.before(tSedphy[j].getSpldattim()) &&
//                tSedphy[j].getSpldattim().before(dateTimeMaxTs)) {

                stationExists = true;
                stationExistsArray[i] = true;
                spldattimArray[i] = tSedphy[j].getSpldattim("");
                sedphyRecordCountArray[i]++;

                if (dataType == SEDIMENT) {
                    dataExists = true;

                    // get the min and max depth
                    if (tSedphy[j].getSpldep() < loadedDepthMin[i]) {
                        loadedDepthMin[i] = tSedphy[j].getSpldep();
                    } // if (tSedphy.getSpldep() < depthMin)
                    if (tSedphy[j].getSpldep() > loadedDepthMax[i]) {
                        loadedDepthMax[i] = tSedphy[j].getSpldep();
                    } // if (tSedphy.getSpldep() < depthMin)

                    // is it the same subdes ?
                    if (dbg) System.out.println(
                        "<br>getExistingStationDetails: subdes = " + subdes +
                        ", sedphy.getSubdes() = " + sedphy.getSubdes(""));
                    //if (tSedphy[j].getSubdes("").equals(subdes)) {
                    if (tSedphy[j].getSubdes("").equals(sedphy.getSubdes(""))) {
                        subdesCount[i]++;
                    } // if (tSedphy[j].getSubdes("").equals(sedphy.getSubdes(""))

                    if (!prevSubdes.equals(tSedphy[j].getSubdes(""))) {
                        subdesArray[i][k] = tSedphy[j].getSubdes("");
                        if ("".equals(subdesArray[i][k])) subdesArray[i][k] = "none";
                        if (dbg) {
                            System.out.println("<br>getExistingStationDetails: " +
                                "k = " + k);
                            System.out.println("<br>getExistingStationDetails: " +
                                "prevSubdes = " + prevSubdes);
                            System.out.println("<br>getExistingStationDetails: " +
                                "tSedphy[j].getSubdes() = " +
                                tSedphy[j].getSubdes(""));
                            System.out.println("<br>getExistingStationDetails: " +
                                "subdesArray[i][k] = " + subdesArray[i][k]);
                        } // if (dbg)
                        k++;
                    } // if (!prevSubdes.equals(subdesArray[i][k])

                    prevSubdes = tSedphy[j].getSubdes("");

                } // if (dataType == SEDIMENT)

            } // if (dateTimeMinTs.before(tSedphy[j].getSpldattim()) ...

        } // for (int j = 0; j < tSedphy.length; j++)

        //
        // are there any watphy records for this station
        //------------------------------------------------
        tWatphy = new MrnWatphy().get("*", where, order);
        if (dbg) System.out.println("<br>getExistingStationDetails: " +
            "tWatphy.length = " + tWatphy.length);

        for (int j = 0; j < tWatphy.length; j++) {

            if (dbg) System.out.println("<br>getExistingStationDetails: " +
                "tWatphy[j].getSpldattim() = " + tWatphy[j].getSpldattim());

            // only found if station_id's the same or also within date-time range
            if (tWatphy[j].getStationId().equals(station.getStationId()) ||
                (dateTimeMinTs.before(tWatphy[j].getSpldattim()) &&
                tWatphy[j].getSpldattim().before(dateTimeMaxTs))) {
//            if (dateTimeMinTs.before(tWatphy[j].getSpldattim()) &&
//                tWatphy[j].getSpldattim().before(dateTimeMaxTs)) {

                stationExists = true;
                stationExistsArray[i] = true;
                spldattimArray[i] = tWatphy[j].getSpldattim("");
                watphyRecordCountArray[i]++;

                if ((dataType == WATER) || (dataType == WATERWOD)) {
                    dataExists = true;

                    // get the min and max depth
                    if (tWatphy[j].getSpldep() < loadedDepthMin[i]) {
                        loadedDepthMin[i] = tWatphy[j].getSpldep();
                    } // if (tWaphy.getSpldep() < depthMin)
                    if (tWatphy[j].getSpldep() > loadedDepthMax[i]) {
                        loadedDepthMax[i] = tWatphy[j].getSpldep();
                    } // if (tWaphy.getSpldep() < depthMin)

                    // is it the same subdes ?
                    if (dbg) System.out.println(
                        "<br>getExistingStationDetails: subdes = " + subdes +
                        ", watphy.getSubdes() = " + watphy.getSubdes(""));
                    //if (tWatphy[j].getSubdes("").equals(subdes)) {
                    if (tWatphy[j].getSubdes("").equals(watphy.getSubdes(""))) {
                        subdesCount[i]++;
                    } // if (tWatphy[j].getSubdes("").equals(watphy.getSubdes(""))

                    if (!prevSubdes.equals(tWatphy[j].getSubdes(""))) {
                        subdesArray[i][k] = tWatphy[j].getSubdes("");
                        if ("".equals(subdesArray[i][k])) subdesArray[i][k] = "none";
                        if (dbg) {
                            System.out.println("<br>getExistingStationDetails: " +
                                "k = " + k);
                            System.out.println("<br>getExistingStationDetails: " +
                                "prevSubdes = " + prevSubdes);
                            System.out.println("<br>getExistingStationDetails: " +
                                "tWatphy[j].getSubdes() = " +
                                tWatphy[j].getSubdes(""));
                            System.out.println("<br>getExistingStationDetails: " +
                                "subdesArray[i][k] = " + subdesArray[i][k]);
                        } // if (dbg)
                        k++;
                    } // if (!prevSubdes.equals(subdesArray[i][k])

                    prevSubdes = tWatphy[j].getSubdes("");

                } // if ((dataType == WATER) || (dataType == WATERWOD))

            } // if (dateTimeMinTs.before(tWatphy[j].getSpldattim()) ...

        } // for (int j = 0; j < tWatphy.length; j++)

    } // getExistingStationDetails


    /**
     * load the currents / watphy, watnut, ... data
     */
    void loadData() {

        // update if
        //     station does not exist OR
        //     the station exists and should be updated
        // don't update if the depth is rejected, regardless of the above 2

        String tmpStationId = "";
        if (dataType == CURRENTS) {
            tmpStationId = currents.getStationId("");
        } else if (dataType == SEDIMENT) {
            tmpStationId = sedphy.getStationId("");
        } else if ((dataType == WATER) || (dataType == WATERWOD)) {
            tmpStationId = watphy.getStationId("");
        } // if (dataType == CURRENTS)
        if (dbg3) System.out.println("<br>loadData: tmpStationId = " + tmpStationId);
        if (dbg3) System.out.println("<br>loadData: rejectDepth = " + rejectDepth);
        if (dbg3) System.out.println("<br>loadData: stationExists = " + stationExists);
        if (dbg3) System.out.println("<br>loadData: stationUpdated = " + stationUpdated);
        if (dbg3) System.out.println("<br>loadData: dataExists = " + dataExists);
        if (dbg3) System.out.println("<br>loadData: if = " +
            (!"".equals(tmpStationId) && !rejectDepth &&
            (!stationExists || stationUpdated || !dataExists)));
        if (dbg3) System.out.println("<br>loadData: dataType = " + dataType);

//<br>loadData: tmpStationId = WOD007919212
//<br>loadData: rejectDepth = false
//<br>loadData: stationExists = true
//<br>loadData: stationUpdated = false
//<br>loadData: dataExists = true

        if (!"".equals(tmpStationId) && !rejectDepth &&
                (!stationExists || stationUpdated || !dataExists || (thisSubdesCount == 0))) {

            // was it a duplicate station with a different station-id?
            // as we can't update station-id's in oracle (used as FK elsewhere),
            // we have to update the watphy record's station id
            if (dbg4) System.out.println("<br>loadData: stationIDs = " +
                station.getStationId("") + " " + tmpStationId);
            //if (!station.getStationId("").equals(watphy.getStationId(""))) {
            if (!station.getStationId("").equals(tmpStationId)) {
                if (dataType == CURRENTS) {
                    currents.setStationId(station.getStationId(""));
                } else if (dataType == SEDIMENT) {
                    sedphy.setStationId(station.getStationId(""));
                } else if ((dataType == WATER) || (dataType == WATERWOD)) {
                    if (dbg4) System.out.println("<br>loadData: put watphy = " + watphy);
                    watphy.setStationId(station.getStationId(""));
                    if (dbg4) System.out.println("<br>loadData: put watphy = " + watphy);
                } // if (dataType == CURRENTS)
            } // if (!station.getStationId("").equals(tmpStationId))

            if (dataType == CURRENTS) {

                // insert the currents record
                if (dbg4) System.out.println("<br>loadData: put currents1 = " +
                    currents);
                try {
                    currents.put();
                } catch(Exception e) {
                    System.err.println("loadData: put currents1 = " + currents);
                    System.err.println("loadData: station = " + station);
                    System.err.println("loadData: put sql = " + currents.getInsStr());
                    e.printStackTrace();
                } // try-catch

                //common2.DbAccessC.commit();
                if (dbg4) System.out.println("<br>loadData: put currents2 = " +
                    currents);
                //dataCodeEnd = currents.getCode();
                //if (dbg3) System.out.println("<br>loadData: put dataCodeEnd = " + dataCodeEnd);
                //currentsCount++;                                      // ub12

                currents = new MrnCurrents();

                // set initial values for currents
                currents.setSpldattim(startDateTime);
                currents.setSubdes(subdes);

            } else if (dataType == SEDIMENT) {

                // set default values
                sedphy.setDeviceCode(1);   // == unknown
                sedphy.setMethodCode(1);   // == unknown
                sedphy.setStandardCode(1); // == unknown

                // insert the sedphy and child records
                if (dbg4) System.out.println("<br>loadData: put sedphy1 = " + sedphy);
                try {
                    sedphy.put();
                } catch(Exception e) {
                    System.err.println("loadData: put sedphy1 = " + sedphy);
                    System.err.println("loadData: station = " + station);
                    System.err.println("loadData: put sql = " + sedphy.getInsStr());
                    e.printStackTrace();
                } // try-catch

                //common2.DbAccessC.commit();
                if (dbg3) System.out.println("<br>loadData: put sedphy2 = " + sedphy);
                dataCodeEnd = sedphy.getCode();
                if (dbg3) System.out.println("<br>loadData: put dataCodeEnd = " + dataCodeEnd);

                //sedphyCount++;                                        // ub12

                if (!sedchem1.isNullRecord()) {
                    sedchem1.setSedphyCode(dataCodeEnd);
                    try {
                        sedchem1.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put sedchem1 = " + sedchem1);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + sedchem1.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put sedchem1 = " + sedchem1);
                    //sedchem1Count++;                                  // ub12
                } // if (!sedchem1.isNullRecord()

                if (!sedchem2.isNullRecord()) {
                    sedchem2.setSedphyCode(dataCodeEnd);
                    try {
                        sedchem2.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put sedchem2 = " + sedchem2);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + sedchem2.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put sedchem2 = " + sedchem2);
                    //sedchem2Count++;                                  // ub12
                } // if (!sedchem2.isNullRecord()

                if (!sedpol1.isNullRecord()) {
                    sedpol1.setSedphyCode(dataCodeEnd);
                    try {
                        sedpol1.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put sedpol1 = " + sedpol1);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + sedpol1.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put sedpol1 = " + sedpol1);
                    //sedpol1Count++;                                   // ub12
                } // if (!sedpol1.isNullRecord()

                if (!sedpol2.isNullRecord()) {
                    sedpol2.setSedphyCode(dataCodeEnd);
                    try {
                        sedpol2.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put sedpol2 = " + sedpol2);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + sedpol2.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put sedpol2 = " + sedpol2);
                    //sedpol2Count++;                                   // ub12
                } // if (!sedpol2.isNullRecord()

                sedphy = new MrnSedphy();
                sedchem1 = new MrnSedchem1();
                sedchem2 = new MrnSedchem2();
                sedpol1 = new MrnSedpol1();
                sedpol2 = new MrnSedpol2();

                // set initial values for sedphy
                sedphy.setSpldattim(startDateTime);
                sedphy.setSubdes(subdes);

            } else if ((dataType == WATER) || (dataType == WATERWOD)) {

                // set default values
                watphy.setDeviceCode(1);   // == unknown
                watphy.setMethodCode(1);   // == unknown
                watphy.setStandardCode(1); // == unknown

                // insert the watphy and child records
                if (dbg4) System.out.println("<br>loadData: put watphy1 = " + watphy);
                try {
                    watphy.put();
                } catch(Exception e) {
                    System.err.println("loadData: put watphy1 = " + watphy);
                    System.err.println("loadData: station = " + station);
                    System.err.println("loadData: put sql = " + watphy.getInsStr());
                    e.printStackTrace();
                } // try-catch

                //common2.DbAccessC.commit();
                if (dbg3) System.out.println("<br>loadData: put watphy2 = " + watphy);
                dataCodeEnd = watphy.getCode();
                if (dbg3) System.out.println("<br>loadData: put dataCodeEnd = " + dataCodeEnd);

                //watphyCount++;                                        // ub12
                if (dbg4) System.out.println("<br>loadData: put watProfQC = " + watProfQC);
                if (!watProfQC.isNullRecord()) {
                    watProfQC.setStationId(station.getStationId(""));
                    watProfQC.setSubdes(watphy.getSubdes(""));
                    if (dbg4) System.out.println("<br>loadData: put watProfQC = " + watProfQC);
                    try {
                        watProfQC.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put watProfQC1 = " + watProfQC);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + watProfQC.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put watProfQC = " + watProfQC);
                } // if (!watProfQC.isNullRecord()

                if (dbg4) System.out.println("<br>loadData: put watQC = " + watQC);
                if (!watQC.isNullRecord()) {
                    try {
                        watQC.setWatphyCode(dataCodeEnd);
                        watQC.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put watQC = " + watQC);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + watQC.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put watQC = " + watQC);
                } // if (!watQC.isNullRecord())

                if (!watchem1.isNullRecord()) {
                    watchem1.setWatphyCode(dataCodeEnd);
                    try {
                        watchem1.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put watchem1 = " + watchem1);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + watchem1.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put watchem1 = " + watchem1);
                    //watchem1Count++;                                  // ub12
                } // if (!watchem1.isNullRecord()

                if (!watchem2.isNullRecord()) {
                    watchem2.setWatphyCode(dataCodeEnd);
                    try {
                        watchem2.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put watchem2 = " + watchem2);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + watchem2.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put watchem2 = " + watchem2);
                    //watchem2Count++;                                  // ub12
                } // if (!watchem2.isNullRecord()

                if (!watchl.isNullRecord()) {
                    watchl.setWatphyCode(dataCodeEnd);
                    try {
                        watchl.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put watchl = " + watchl);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + watchl.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put watchl = " + watchl);
                    //watchlCount++;                                    // ub12
                } // if (!watchl.isNullRecord())

                if (!watnut.isNullRecord()) {
                    watnut.setWatphyCode(dataCodeEnd);
                    try {
                        watnut.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put watnut = " + watnut);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + watnut.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put watnut = " + watnut);
                    //watnutCount++;                                    // ub12
                } // if (!watnut.isNullRecord())

                if (!watpol1.isNullRecord()) {
                    watpol1.setWatphyCode(dataCodeEnd);
                    try {
                        watpol1.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put watpol1 = " + watpol1);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + watpol1.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put watpol1 = " + watpol1);
                    //watpol1Count++;                                   // ub12
                } // if (!watpol1.isNullRecord()

                if (!watpol2.isNullRecord()) {
                    watpol2.setWatphyCode(dataCodeEnd);
                    try {
                        watpol2.put();
                    } catch(Exception e) {
                        System.err.println("loadData: put watpol2 = " + watpol2);
                        System.err.println("loadData: station = " + station);
                        System.err.println("loadData: put sql = " + watpol2.getInsStr());
                        e.printStackTrace();
                    } // try-catch
                    if (dbg3) System.out.println("<br>loadData: put watpol2 = " + watpol2);
                    //watpol2Count++;                                   // ub12
                } // if (!watpol2.isNullRecord()

                watphy = new MrnWatphy();
                watchem1 = new MrnWatchem1();
                watchem2 = new MrnWatchem2();
                watchl = new MrnWatchl();
                watnut = new MrnWatnut();
                watpol1 = new MrnWatpol1();
                watpol2 = new MrnWatpol2();

                watProfQC = new MrnWatprofqc();
                watQC = new MrnWatqc();

                // set initial values for watphy
                watphy.setSpldattim(startDateTime);
                watphy.setSubdes(subdes);

            } // if (dataType == CURRENTS)

        } // if (!rejectDepth && (!stationExists || stationUpdated))
    } // loadData


     /**
      * load the station, sfri_grid
      */
    void loadStation() {

        // only do this if the station does not exist
        if (!stationExists) {

            station.setStatusCode(35); // open & unknown
            if (!"".equals(passkey)) {         // ub02
                station.setPasskey(passkey);   // ub02
            } // if (!"".equals(passkey))      // ub02

            if (dbg3) System.out.println("<br>loadStation: put station = " + station);
            if (dbg4) System.out.println("<br>loadStation: put station = " + station);
            try {
                station.put();
            } catch(Exception e) {
                System.err.println("loadStation: put station = " + station);
                System.err.println("loadStation: put sql = " + station.getInsStr());
                e.printStackTrace();
            } // try-catch
            if (dbg3) System.out.println("loadStation: sql = " + station.getInsStr());

            //stationCount++;

            if (!"".equals(sfriGrid.getGridNo(""))) {
                try {
                    sfriGrid.put();
                } catch(Exception e) {
                    System.err.println("loadStation: put sfriGrid = " + sfriGrid);
                    System.err.println("loadStation: put sql = " + sfriGrid.getInsStr());
                    e.printStackTrace();
                } // try-catch
                if (dbg3) System.out.println("<br>loadStation: put sfriGrid = " + sfriGrid);
            } // if (!"".equals(sfriGrid.getGridNo("")))

            weatherIsLoaded = false;

        } // if (!stationExists)

    } // loadStation


     /**
      * update the station with the maximum spldepth
      */
    void updateStation() {

        // only do this if the station did not exist  ????
        if ((!stationExists || stationUpdated)
                && !"".equals(station.getStationId("")) &&
                station.getMaxSpldep()!= Tables.FLOATNULL) {    // for sediments

            MrnStation updStation = new MrnStation();
            updStation.setMaxSpldep(station.getMaxSpldep());

            MrnStation whereStation =
                new MrnStation(station.getStationId());

            try {
                whereStation.upd(updStation);
            } catch(Exception e) {
                System.err.println("updateStation: upd whereStation = " + whereStation);
                System.err.println("updateStation: upd updStation = " + updStation);
                System.err.println("updateStation: upd sql = " + whereStation.getUpdStr());
                e.printStackTrace();
            } // try-catch
            if (dbg3) System.out.println("<br>updateStation: upd whereStation = " + whereStation);
            if (dbg3) System.out.println("<br>updateStation: upd updStation = " + updStation);
            //if (dbg3) System.out.println("<br>updateStation: sql = " + whereStation.getUpdStr());

        } // if (!stationExists)

        // commit this station's data - for stations with many depths
        common2.DbAccessC.commit();                                         //ub04

    } // updateStation


    /**
     * load the weather data
     */
    void loadWeather() {

        // existing station's weather records are never updated.
        // why not?????

//        if (!"".equals(stationId) && !stationIgnore) {
        if (!stationExists && !weather.isNullRecord()) {

            if (!weatherIsLoaded) {

                // is there a weather record?
//                int count = weather.getRecCnt(
//                    MrnWeather.STATION_ID + "=" + stationId);

//                if (count == 0) {

                    // insert weather record
                    weather.setStationId(stationId);
                    if (dbg3) System.out.println("<br>loadWeather: put weather = " + weather);
                    try {
                        weather.put();
                    } catch(Exception e) {
                        System.err.println("loadWeather: put weather = " + weather);
                        System.err.println("loadWeather: put sql = " + weather.getInsStr());
                        e.printStackTrace();
                    } // try-catch

                    //weatherCount++;                                   // ub12

//                } else {
//
//                    // update weather record
//                    MrnWeather whereWeather = new MrnWeather(stationId);
//                    whereWeather.upd(weather);
//
//                } // if (weatherRecordCount == 0)
//
                weatherIsLoaded = true;
            } // if (!weather.isNullRecord())

        } // if (!stationExists && !weather.isNullRecord())
//        } // if (!"".equals(stationId) && !stationIgnore)

        weather = new MrnWeather();

    } // loadWeather


    /**
     * Open the input file
     * @param   fileName   The complete file name (String)
     * @return  the RandomAccessFile handle
     */
    RandomAccessFile openInputFile(String fileName) {
        if (dbg) System.out.println("<br>" + thisClass + ".openInputFile: " +
            "fileName = " + fileName);
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(fileName, "r");
            if (dbg) System.out.println("<br>" + thisClass + ".openInputFile: " +
                "success: file opened for read");
        } catch (Exception e) {
            ec.processError(e, thisClass, "openInputFile", "");
            if (dbg) System.out.println("<br>" + thisClass + ".openInputFile: " +
                "error: file opened for read");
        } // try-catch
        return file;
    } // openInputFile


    /**
     * Open the output file
     * @param   fileName   The complete file name (String)
     * @return  the RandomAccessFile handle
     */
    RandomAccessFile openOutputFile(String fileName) {
        ec.deleteOldFile(fileName);
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(fileName, "rw");
        } catch (Exception e) {
            //ec.printError()
        } // try-catch
        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob("chmod 644 " + fileName);
        } // if (ec.getHost().startsWith(sc.HOST))
        return file;
    } // openInputFile


    /**
     * Close all the files
     */
    void closeFiles() {
        try {
            inputFile.close();
            debugFile.close();
            reportFile.close();
            workFile.close();
            stnFile.close();
        } catch(Exception e) {}
    } // closeFiles


    /**
     * Re-initialise the station statistic variables
     */
    void resetStationStatistics() {

        if (dbg) System.out.println("<br>resetStationStatistics: " +
           "depthMin, depthMax = " + depthMin + " " + depthMax);
        if (dbg) System.out.println("<br>resetStationStatistics: " +
           "sampleCount = " + sampleCount);
        if (dbg) System.out.println("<br>resetStationStatistics: " +
           "stationSampleCount = " + stationSampleCount);
        if (dbg) System.out.println("<br>resetStationStatistics: " +
           "sampleRejectCount = " + sampleRejectCount);
        if (dbg) System.out.println("<br>resetStationStatistics: " +
           "stationSampleRejectCount = " + stationSampleRejectCount);

        depthMin = 9999.99f;
        depthMax = -9999.99f;
        stationSampleCount = 0;
        stationSampleRejectCount = 0;
        prevDepth = 0f;

    } // resetStationStatistics


    /**
     * Load the inventory - only called from LoadMrnData
     */
    void loadInventory() {

        // make sure there isn't already an inventory record
        MrnInventory checkInv[] =
            new MrnInventory(survey.getSurveyId()).get();

        if (dbg3) System.out.println("<br>loadInventory: survey.getSurveyId() = " +
            survey.getSurveyId());
        if (dbg3) System.out.println("<br>loadInventory: checkInv.length = " +
            checkInv.length);

        if (checkInv.length == 0) {

            inventory.setSurveyId(survey.getSurveyId());

            // defaults
            inventory.setDataCentre("SADCO");
            inventory.setTargetCountryCode(0);   // unknown
            inventory.setStnidPrefix(survey.getInstitute());
            inventory.setDataRecorded("Y");

            // from screen - done in LoadMRNData.getArgsFromFile()
            //inventory.setCountryCode(screenInv.getCountryCode());
            //inventory.setPlanamCode(screenInv.getPlanamCode());
            //inventory.setInstitCode(screenInv.getInstitCode());
            //inventory.setCruiseName(screenInv.getCruiseName());
            //inventory.setProjectName(screenInv.getProjectName());
            //inventory.setAreaname(screenInv.getAreaname()); // use as default to show on screen
            //inventory.setDomain(screenInv.getDomain());   // use as default to show on screen

            inventory.setCountryCode(0);  // unknown

            inventory.setSciCode1(1);  // unknown
            inventory.setSciCode2(1);  // unknown    // new
            inventory.setCoordCode(1);   // unknown

            inventory.setProjectionCode(1);  // unknown
            inventory.setSpheroidCode(1);  // unknown
            inventory.setDatumCode(1);  // unknown

            inventory.setSurveyTypeCode(1);  // hydro

            inventory.setDataAvailable("Y");        // ub13
            if (!"".equals(passkey)) {              // ub13
                inventory.setDataAvailable("N");    // ub13
            } // if (!"".equals(passkey))           // ub13

            if (dbg) System.out.println("loadInventory: put inventory = " + inventory);

            try {
                inventory.put();
            } catch(Exception e) {
                System.err.println("loadInventory: put inventory = " + inventory);
                System.err.println("loadInventory: put sql = " + inventory.getInsStr());
                e.printStackTrace();
            } // try-catch
            if (dbg3) System.out.println("<br>loadInventory: put inventory = " +
                inventory);

        } // if (checkInv.length > 0)

    } // loadInventory


    /**
     * Update the inventory - only called from LoadMrnData
     */
    void updateInventory() {

        // make sure there isn't already an inventory record
        MrnInventory checkInv[] =
            new MrnInventory(survey.getSurveyId()).get();

        if (dbg3) {
            System.out.println("<br>updateInventory: survey.getSurveyId() = " +
                survey.getSurveyId());
            System.out.println("<br>updateInventory: inventory = " + inventory);
            System.out.println("<br>updateInventory: checkInv[0] = " +
                checkInv[0]);
            System.out.println("<br>updateInventory: checkInv.length = " +
                checkInv.length);
        } // if (dbg3)

        if (checkInv.length > 0) {

            //MrnInventory updInventory = new MrnInventory();

            if (dbg3) {
                System.out.println("<br>updateInventory: dateMin = " + dateMin);
                System.out.println("<br>updateInventory: dateMax = " + dateMax);
                System.out.println("<br>updateInventory: latitudeMin = " + latitudeMin);
                System.out.println("<br>updateInventory: latitudeMax = " + latitudeMax);
                System.out.println("<br>updateInventory: longitudeMin = " + longitudeMin);
                System.out.println("<br>updateInventory: longitudeMax = " + longitudeMax);
            } // if (dbg3)

            // check dates
            if (MrnInventory.DATENULL.equals(checkInv[0].getDateStart()) ||
                    dateMin.before(checkInv[0].getDateStart())) {
                inventory.setDateStart(dateMin);

            } // if (dateMin.before(checkInv[0].getDateStart()))
            if (MrnInventory.DATENULL.equals(checkInv[0].getDateEnd()) ||
                    dateMax.after(checkInv[0].getDateEnd())) {
                inventory.setDateEnd(dateMax);
            } // if (dateMax.after(checkInv[0].getDateEnd()))

            // check area
            if ((MrnInventory.FLOATNULL == checkInv[0].getLatNorth()) ||
                    (latitudeMin < checkInv[0].getLatNorth())) {
                inventory.setLatNorth(latitudeMin);
            } // if (latitudeMin < checkInv[0].getLatNorth())
            if ((MrnInventory.FLOATNULL == checkInv[0].getLatSouth()) ||
                    (latitudeMax > checkInv[0].getLatSouth())) {
                inventory.setLatSouth(latitudeMax);
            } // if (latitudeMin < checkInv[0].getLatSouth())
            if ((MrnInventory.FLOATNULL == checkInv[0].getLongWest()) ||
                    (longitudeMin < checkInv[0].getLongWest())) {
                inventory.setLongWest(longitudeMin);
            } // if (longitudeMin < checkInv[0].getlongWest())
            if ((MrnInventory.FLOATNULL == checkInv[0].getLongEast()) ||
                    (longitudeMax > checkInv[0].getLongEast())) {
                inventory.setLongEast(longitudeMax);
            } // if (longitudeMax < checkInv[0].getlongEast())

            // check others
            if ("".equals(checkInv[0].getCruiseName(""))) {
                inventory.setCruiseName(inventory.getCruiseName());
            } // if ("".equals(checkInv[0].getCruiseName()))
            if ("".equals(checkInv[0].getProjectName(""))) {
                inventory.setProjectName(inventory.getProjectName());
            } // if ("".equals(checkInv[0].getProjectName()))

            inventory.setDataRecorded("Y");

            MrnInventory whereInventory =
                new MrnInventory(survey.getSurveyId());

            try {
                //whereInventory.upd(updInventory);
                whereInventory.upd(inventory);
            } catch(Exception e) {
                System.err.println("updateInventory: upd inventory = " + inventory);
                System.err.println("updateInventory: upd whereInventory = " + whereInventory);
                System.err.println("updateStation: upd sql = " + whereInventory.getUpdStr());
                e.printStackTrace();
            } // try-catch
            if (dbg3) System.out.println(
                "<br>updateInventory: upd inventory = " + inventory);
            if (dbg3) System.out.println(
                "<br>updateInventory: upd whereInventory = " + whereInventory);
            if (dbg3) System.out.println("<br>updateInventory: updSQL = " + whereInventory.getUpdStr());


/*            } else {
            inventory.setSurveyId(survey.getSurveyId());
            // defaults
            inventory.setDataCentre("SADCO");
            //inventory.setCountryCode(0); // done in LoadMrnData
            inventory.setTargetCountryCode(0);
            inventory.setStnidPrefix(survey.getInstitute());
            inventory.setDataRecorded("Y");

            // from data
            inventory.setDateStart(dateMin);
            inventory.setDateEnd(dateMax);
            inventory.setLatNorth(latitudeMin);
            inventory.setLatSouth(latitudeMax);
            inventory.setLongWest(longitudeMin);
            inventory.setLongEast(longitudeMax);

            // from screen - done in LoadMRNData.getArgsFromFile()
            //inventory.setCountryCode(screenInv.getCountryCode());
            //inventory.setPlanamCode(screenInv.getPlanamCode());
            //inventory.setInstitCode(screenInv.getInstitCode());
            //inventory.setCruiseName(screenInv.getCruiseName());
            //inventory.setProjectName(screenInv.getProjectName());
            //inventory.setAreaname(screenInv.getAreaname()); // use as default to show on screen
            //inventory.setDomain(screenInv.getDomain());   // use as default to show on screen

            inventory.put();
*/
        } // if (checkInv.length > 0)

        /* // ub12
        // make sure there isn't already an invStats record
        MrnInvStats checkInvStats[] =
            new MrnInvStats(survey.getSurveyId()).get();

        MrnInvStats invStats = new MrnInvStats();

        if (checkInvStats.length > 0) {

            System.out.println("updateInventory: checkInvStats[0] = " + checkInvStats[0]);

            invStats.setStationCnt(
                checkInvStats[0].getStationCnt() + newStationCount); //stationCount
            invStats.setWeatherCnt(
                checkNull(checkInvStats[0].getWeatherCnt()) + weatherCount);

            if (dataType == CURRENTS) {

                invStats.setWatcurrentsCnt(
                    checkNull(checkInvStats[0].getWatcurrentsCnt()) + currentsCount);

            } else if (dataType == SEDIMENT) {

                System.out.println(
                    "updateInventory: checkInvStats[0].getSedphyCnt(), sedphyCount = " +
                    checkInvStats[0].getSedphyCnt() + " " + sedphyCount + " " +
                    checkNull(checkInvStats[0].getSedphyCnt()));
                System.out.println(
                    "updateInventory: checkInvStats[0].getSedchem1Cnt(), sedchem1Count = " +
                    checkInvStats[0].getSedchem1Cnt() + " " + sedchem1Count + " " +
                    checkNull(checkInvStats[0].getSedchem1Cnt()));
                System.out.println(
                    "updateInventory: checkInvStats[0].getSedchem2Cnt(), sedchem2Count = " +
                    checkInvStats[0].getSedchem2Cnt() + " " + sedchem2Count + " " +
                    checkNull(checkInvStats[0].getSedchem2Cnt()));
                System.out.println(
                    "updateInventory: checkInvStats[0].getSedpol1Cnt(), sedpol1Count = " +
                    checkInvStats[0].getSedpol1Cnt() + " " + sedpol1Count + " " +
                    checkNull(checkInvStats[0].getSedpol1Cnt()));
                System.out.println(
                    "updateInventory: checkInvStats[0].getSedpol2Cnt(), sedpol2Count = " +
                    checkInvStats[0].getSedpol2Cnt() + " " + sedpol2Count + " " +
                    checkNull(checkInvStats[0].getSedpol2Cnt()));

                invStats.setSedphyCnt(
                    checkNull(checkInvStats[0].getSedphyCnt()) + sedphyCount);
                invStats.setSedchem1Cnt(
                    checkNull(checkInvStats[0].getSedchem1Cnt()) + sedchem1Count);
                invStats.setSedchem2Cnt(
                    checkNull(checkInvStats[0].getSedchem2Cnt()) + sedchem2Count);
                invStats.setSedpol1Cnt(
                    checkNull(checkInvStats[0].getSedpol1Cnt()) + sedpol1Count);
                invStats.setSedpol2Cnt(
                    checkNull(checkInvStats[0].getSedpol2Cnt()) + sedpol2Count);

                System.out.println("updateInventory: invStats = " + invStats);


            } else if ((dataType == WATER) || (dataType == WATERWOD)) {

                invStats.setWatphyCnt(
                    checkNull(checkInvStats[0].getWatphyCnt()) + watphyCount);
                invStats.setWatchem1Cnt(
                    checkNull(checkInvStats[0].getWatchem1Cnt()) + watchem1Count);
                invStats.setWatchem2Cnt(
                    checkNull(checkInvStats[0].getWatchem2Cnt()) + watchem2Count);
                invStats.setWatchlCnt(
                    checkNull(checkInvStats[0].getWatchlCnt()) + watchlCount);
                invStats.setWatnutCnt(
                    checkNull(checkInvStats[0].getWatnutCnt()) + watnutCount);
                invStats.setWatpol1Cnt(
                    checkNull(checkInvStats[0].getWatpol1Cnt()) + watpol1Count);
                invStats.setWatpol2Cnt(
                    checkNull(checkInvStats[0].getWatpol2Cnt()) + watpol2Count);

            } // if (dataType == CURRENTS)

            MrnInvStats whereInvStats =
                new MrnInvStats(survey.getSurveyId());
            try {
                whereInvStats.upd(invStats);
            } catch(Exception e) {
                System.err.println("updateInventory: upd invStats = " + invStats);
                System.err.println("updateInventory: upd whereInvStats = " + whereInvStats);
                System.err.println("updateInventory: upd sql = " + whereInvStats.getUpdStr());
                e.printStackTrace();
            } // try-catch
            if (dbg3) System.out.println("<br>updateInventory: upd invStats = " +
                invStats);

        } else {

            invStats.setStationCnt(stationCount);
            invStats.setWeatherCnt(weatherCount);

            if (dataType == CURRENTS) {

                invStats.setWatcurrentsCnt(currentsCount);

            } else if (dataType == SEDIMENT) {

                invStats.setSedphyCnt(sedphyCount);
                invStats.setSedchem1Cnt(sedchem1Count);
                invStats.setSedchem2Cnt(sedchem2Count);
                invStats.setSedpol1Cnt(sedpol1Count);
                invStats.setSedpol2Cnt(sedpol2Count);

            } else if ((dataType == WATER) || (dataType == WATERWOD)) {

                invStats.setWatphyCnt(watphyCount);
                invStats.setWatchem1Cnt(watchem1Count);
                invStats.setWatchem2Cnt(watchem2Count);
                invStats.setWatchlCnt(watchlCount);
                invStats.setWatnutCnt(watnutCount);
                invStats.setWatpol1Cnt(watpol1Count);
                invStats.setWatpol2Cnt(watpol2Count);

            } // if (dataType == CURRENTS)

            invStats.setSurveyId(survey.getSurveyId());
            try {
                invStats.put();
            } catch(Exception e) {
                System.err.println("updateInventory: put invStats = " + invStats);
                System.err.println("updateInventory: put sql = " + invStats.getInsStr());
                e.printStackTrace();
            } // try-catch
            if (dbg3) System.out.println("<br>updateInventory: put invStats = " +
                invStats);
        } // if (checkInvStats.length > 0)
        */  // ub12
/*   ????

        // define the value that should be updated
        MrnInventory updateInv = new MrnInventory();
        updateInv.setDateStart(dateMin);

        // define the 'where' clause
        MrnInventory whereInv = new MrnInventory(inventory.getSurveyId());

        // do the update
        whereInv.upd(updateInv);

*/

    } // updateInventory


    /**
     *
     */
    void updateLoadLog() {

        // set the values not set in getArgParms
        if (dataType != CURRENTS) {
            logMrnLoads.setRecnumStart(dataCodeStart);
            logMrnLoads.setRecnumEnd(dataCodeEnd);
        } // if (dataType != CURRENTS)
        logMrnLoads.setStationsLoaded(stationCount);
        logMrnLoads.setRecordsLoaded(sampleCount - sampleRejectCount);
        logMrnLoads.setRecordsRejected(sampleRejectCount);
        logMrnLoads.setDateStart(dateMin);
        logMrnLoads.setDateEnd(dateMax);
        logMrnLoads.setLatNorth(latitudeMin);
        logMrnLoads.setLatSouth(latitudeMax);
        logMrnLoads.setLongWest(longitudeMin);
        logMrnLoads.setLongEast(longitudeMax);
        logMrnLoads.setExpnam(survey.getExpnam());
        logMrnLoads.setLoadDate(new java.util.Date());
        //if (!"".equals(passkey)) {
        if (!"".equals(station.getPasskey(""))) {
            logMrnLoads.setFlagged("Y");
        } else {
            logMrnLoads.setFlagged("N");
        } // if (!"".equals(station.getPasskey(""))


        // check whether there is a loadlog record
        String logSurveyId = survey.getSurveyId() +
            subdes.substring(0,3);
        String where =
            LogMrnLoads.LOADID + "='" + logMrnLoads.getLoadid() +
            "' and  " + LogMrnLoads.SURVEY_ID + "='" + logSurveyId + "'";
        LogMrnLoads[] logMrnLoadsA = new LogMrnLoads().get(where);


        if (dbg) System.out.println("<br>updateLoadLog: where = " + where);
        if (dbg) System.out.println("<br>updateLoadLog: logMrnLoadsA.length = " +
            logMrnLoadsA.length);
        // insert or update record
        if (logMrnLoadsA.length == 0) {

            logMrnLoads.setSurveyId(logSurveyId);
            if (dbg4) System.out.println("<br>updateLoadLog: put logMrnLoads = " +
                logMrnLoads);
            try {
                logMrnLoads.put();
            } catch(Exception e) {
                System.err.println("updateLoadLog: put logMrnLoads = " + logMrnLoads);
                System.err.println("updateLoadLog: put sql = " + logMrnLoads.getInsStr());
                e.printStackTrace();
            } // try-catch
            if (dbg3) System.out.println("<br>updateLoadLog: put logMrnLoads = " +
                logMrnLoads);
            if (dbg3) System.out.println("<br>updateLoadLog: put sql = " +
                logMrnLoads.getInsStr());

        } else {

            LogMrnLoads whereLogMrnLoads = new LogMrnLoads();
            whereLogMrnLoads.setLoadid(logMrnLoads.getLoadid());
            whereLogMrnLoads.setSurveyId(logSurveyId);
            try {
                whereLogMrnLoads.upd(logMrnLoads);
            } catch(Exception e) {
                System.err.println("updateLoadLog: upd logMrnLoads = " +
                    logMrnLoads);
                System.err.println("updateLoadLog: upd whereLogMrnLoads = " +
                    whereLogMrnLoads);
                System.err.println("updateStation: upd sql = " +
                    whereLogMrnLoads.getUpdStr());
                e.printStackTrace();
            } // try-catch
            if (dbg3) System.out.println("<br>updateLoadLog: upd logMrnLoads = " +
                logMrnLoads);

        } // if (logMrnLoadsA.length > 0)


    } // updateLoadLog


    /**
     * Write the message to the report file
     * @param  reportFile
     * @param  last
     */
    void outputReport(RandomAccessFile reportFile, boolean last) {

        String line1 = "----+---------+----------+----------+---+----------+" +
            "----------+----------+----------+" +
            "---------------------------------------------+";
        String line2 = "----+---------+------------+----------+---------------------+" +
            "---------+----------+----------+--------------------+--------+" +
            "-----+-----+-----+";

        if (dbg3) System.out.println("outputReport: station.getStationId() = *" +
            station.getStationId("") + "*");
        if ("".equals(station.getStationId(""))) { // only done formats 01/02 -> survey data only

            //display the survey data }
            //ec.writeFileLine(reportFile, "----+---------+----------+----------" +
            //    "+---+----------+----------+----------+----------+----------" +
            //    "------------------------------+");
            ec.writeFileLine(reportFile, line1);
            ec.writeFileLine(reportFile, "dup.|Survey   |Platform  |          " +
                "|   |          |          |          |          |          " +
                "                                   |");
            ec.writeFileLine(reportFile, "code|Id       |Name      |Expedition" +
                "|Ins|Proj Name |Area Name |Domain    |Platform  |Notes     " +
                "                                   |");
            ec.writeFileLine(reportFile, line1);

            ec.writeFileLine(reportFile, surveyStatus + " |" +    //j24
                  ec.frm(survey.getSurveyId(""),9)    + "|" +
                  ec.frm(survey.getPlanam(""),10)     + "|" +
                  ec.frm(survey.getExpnam(""),10)     + "|" +
                  ec.frm(survey.getInstitute(""),03)  + "|" +
                  ec.frm(survey.getPrjnam(""),10)     + "|" +
                  ec.frm(inventory.getAreaname(""),10)+ "|" +
                  ec.frm(inventory.getDomain(""),10)  + "|" +
                  ec.frm(" ",10)                      + "|" +
                  //survey.getPlatfm()   + "|" +
                  ec.frm(survey.getNotes1(""),45)   + "|");

            // write to station file - keep place for actual output
            if (loadFlag) {
                try {
                    stnFile.writeBytes("");
                    headerPos1 = stnFile.getFilePointer();
                    ec.writeFileLine(stnFile, ec.frm(" ",80));
                    headerPos2 = stnFile.getFilePointer();
                    ec.writeFileLine(stnFile, ec.frm(" ",80));
                    headerPos3 = stnFile.getFilePointer();
                    ec.writeFileLine(stnFile, ec.frm(" ",80));
                } catch (Exception e) {
                    ec.processError(e, thisClass, "outputReport", "Header Pos Error");
                }  // try-catch
            } // if (loadFlag)


            if (surveyLoaded) { // display details of loaded survey    //j24
                // get the old inventory record
                MrnInventory tInventory[] =
                    new MrnInventory(survey.getSurveyId()).get();

                ec.writeFileLine(reportFile, "lod |" +
                    ec.frm(tSurvey[0].getSurveyId(""),9)     + "|" +
                    ec.frm(tSurvey[0].getPlanam(""),10)      + "|" +             //j24
                    ec.frm(tSurvey[0].getExpnam(""),10)      + "|" +             //j24
                    ec.frm(tSurvey[0].getInstitute(""),03)   + "|" +             //j24
                    ec.frm(tSurvey[0].getPrjnam(""),10)      + "|" +             //j24
                    ec.frm(tInventory[0].getAreaname(""),10) + "|" +             //j24
                    ec.frm(tInventory[0].getDomain(""),10)   + "|" +             //j24
                    ec.frm(" ",10)                           + "|" +
                    //tSurvey[0].getPlatfm()    + "|" +             //j24
                    ec.frm(tSurvey[0].getNotes1(""),45)      + "|");             //j24
            } // if (survey_loaded)

            ec.writeFileLine(reportFile, line1);
            ec.writeFileLine(reportFile, " ");

            ec.writeFileLine(reportFile, line2);
            ec.writeFileLine(reportFile, "dup.|         |            |          |" +
                "                     |         |          |          |" +
                "                    |        |     Records     |");
            ec.writeFileLine(reportFile, "code| Surv Id | Station Id | StnNam   |" +
                " Date       Time GMT | Latitude| Longitude| Subdes(s)|" +
                " Sample depth range | StnDep | Tot.| O.K.| Rej |");
            ec.writeFileLine(reportFile, line2);
        } else { // if ("".equals(station.getStationId("")))


            if (dbg) System.out.println("<br>outputReport: tStation.length = " +
                tStation.length);

            int stationSampleOKCount = stationSampleCount - stationSampleRejectCount;
            ec.writeFileLine(reportFile, stationStatusLD + " |" +   //j24
                ec.frm(" ",9) + "|" +
                ec.frm(station.getStationId(),12) + "|" +
                ec.frm(station.getStnnam(),10) + "| " +
                //station.getDatum + "| " +
                ec.frm(startDateTime,20) + "|" +
                ec.frm(station.getLatitude(),9,5) + "|" +
                ec.frm(station.getLongitude(),10,5) + "|" +
                ec.frm(subdes,10) + "|" +
                ec.frm(depthMin + " to " + depthMax,20) + "|" +
                ec.frm(station.getStndep(),8,2) + "|" +
                ec.frm(stationSampleCount,5) + "|" +
                ec.frm(stationSampleOKCount,5) + "|" +
                ec.frm(stationSampleRejectCount,5) + "|");
            if (!loadFlag) {
                ec.writeFileLine(workFile, ec.frm(station.getStationId(),12) +
                    ec.frm(stationSampleCount,5));
            } // if (!loadFlag)

            if (stationExists) { // || (!"new".equals(stationStatusLD))) {
                for (int i = 0; i < tStation.length; i++) {
                    int tmpRecordCount = 0;
                    if (dataType == CURRENTS) {
                        tmpRecordCount = currentsRecordCountArray[i];
                    } else if (dataType == SEDIMENT) {
                        tmpRecordCount = sedphyRecordCountArray[i];
                    } else if ((dataType == WATER) || (dataType == WATERWOD)) {
                        tmpRecordCount = watphyRecordCountArray[i];
                    } // if (dataType == CURRENTS)

                    String subdesLocal = subdesArray[i][0];
                    for (int j = 1; j < MAX_SUBDES; j++) {
                        subdesLocal += (!"".equals(subdesArray[i][j]) ?
                            "/"+subdesArray[i][j] : "");
                    } // for int (j = 1; j < MAX_SUBDES; j++)
                    if (stationExistsArray[i]) {
                        ec.writeFileLine(reportFile, stationStatusDB[i] + " |" +
                            ec.frm(tStation[i].getSurveyId(),9) + "|" +
                            ec.frm(tStation[i].getStationId(),12) + "|" +
                            ec.frm(tStation[i].getStnnam(""),10) + "| " +
                            //tStation[i].getDateStart(""), "| ",
                            ec.frm(spldattimArray[i],20) + "|" +
                            ec.frm(tStation[i].getLatitude(),9,5) + "|" +
                            ec.frm(tStation[i].getLongitude(),10,5) + "|" +
                            //ec.frm(subdesArray[i],5) + "|" +
                            ec.frm(subdesLocal,10) + "|" +
                            ec.frm(loadedDepthMin[i] + " to " + loadedDepthMax[i],20) + "|" +
                            ec.frm(tStation[i].getStndep(),8,2) + "|" +
                            ec.frm(tmpRecordCount,5) + "|     |     |");
                    } // if (stationExistsArray[i])
                } // for (int i = 0; i < tStation.length; i++)
            } // if (stationExists || (!"new".equals(stationStatusDB))
            ec.writeFileLine(reportFile, "");

            // write to station file
            if (loadFlag) {
                ec.writeFileLine(stnFile, "'" +
                    ec.frm(station.getStationId(""),12) + "' '" +
                    station.getDateStart("").substring(0,10) + "' " +
                    ec.frm(station.getLatitude(),11,5) + " " +
                    ec.frm(station.getLongitude(),11,5) + " "  +
                    ec.frm(ec.nullToNines(station.getStndep(),9999.0f),10,2) + " '" +
                    ec.frm(station.getStnnam(""),10) + "'");
            } // if (loadFlag)


            if (last) {
                //ec.writeFileLine(reportFile, "last");

                int sampleOKCount = sampleCount - sampleRejectCount;
                //ec.writeFileLine(reportFile, "----+------------+----------" +
                //    "+---------------------+---------+----------+-----+---" +
                //    "-----------------+--------+-----------------+");
                ec.writeFileLine(reportFile, line2);
                ec.writeFileLine(reportFile, " ^");                                                         //j24//
                ec.writeFileLine(reportFile, " |");                                                         //j24//
                ec.writeFileLine(reportFile, " +--new = new record");                                       //j24//
                ec.writeFileLine(reportFile, "    dup = new record is a duplicate record");                 //j24//
                //ec.writeFileLine(reportFile, "    lod = existing record");                                //j24//
                ec.writeFileLine(reportFile, "    did = duplicate station-id - different SUBDES");          //k46//
                ec.writeFileLine(reportFile, "    dis = duplicate station-id - same SUBDES");               //u02//
                ec.writeFileLine(reportFile, "    dia = duplicate station-id - different SUBDES: data added");//u02//
                ec.writeFileLine(reportFile, "    dij = ignored station - matched existing station record - did");   //k46//
                ec.writeFileLine(reportFile, "    dip = data records replaced for existing station record - did");       //k46//
                ec.writeFileLine(reportFile, "    dsd = duplicate station record - different SUBDES");      //k46//
                ec.writeFileLine(reportFile, "    dss = duplicate station record - same SUBDES");           //u02//
                ec.writeFileLine(reportFile, "    dsa = duplicate station record - different SUBDES: data added");
                ec.writeFileLine(reportFile, "    dsj = ignored station - matched existing station record - dsd");   //k46//
                ec.writeFileLine(reportFile, "    dsp = data records replaced for existing station record - dsd");       //k46//

                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "----------------------------" +
                    "-----------------------------------------------------" +
                    "------------------------------");
                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "This data was " +
                    (loadFlag ? "loaded" : "checked") + " with the following ranges:");
                ec.writeFileLine(reportFile, "     Area: " + areaRangeVal + " (decimal degrees)");
                ec.writeFileLine(reportFile, "     Time: " + timeRangeVal + " (minutes)");
                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "----------------------------" +
                    "-----------------------------------------------------" +
                    "------------------------------");
                ec.writeFileLine(reportFile, "Closing statistics - survey_id: " +
                    survey.getSurveyId());
                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "              Number of lines - " +
                    ec.frm(lineCount,6));
                ec.writeFileLine(reportFile, "                 Fatal Errors - " +
                    ec.frm(fatalCount,6));
                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "           Number of stations - " +
                    ec.frm(stationCount,6));
                ec.writeFileLine(reportFile, "                          New - " +
                    ec.frm(newStationCount,6));
                ec.writeFileLine(reportFile, "                          Dup - " +
                    ec.frm((stationCount-newStationCount),6));
                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "    Duplicate Station-id (DI)");
                ec.writeFileLine(reportFile, "        DI: diff SUBDES (DID) - " +
                    ec.frm(didCount,6));
                ec.writeFileLine(reportFile, "        DI: same SUBDES (DIS) - " +
                    ec.frm(disCount,6));
                ec.writeFileLine(reportFile, "  DI: diff SUBDES added (DIA) - " +
                    ec.frm(diaCount,6));
                ec.writeFileLine(reportFile, "          DI's Rejected (DIJ) - " +
                    ec.frm(dijCount,6));
                ec.writeFileLine(reportFile, "          DI's Replaced (DIP) - " +
                    ec.frm(dipCount,6));
                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "       Duplicate Station (DS)");
                ec.writeFileLine(reportFile, "        DS: diff SUBDES (DSD) - " +
                    ec.frm(dsdCount,6));
                ec.writeFileLine(reportFile, "        DS: same SUBDES (DSS) - " +
                    ec.frm(dssCount,6));
                ec.writeFileLine(reportFile, "  DS: diff SUBDES added (DSA) - " +
                    ec.frm(dsaCount,6));
                ec.writeFileLine(reportFile, "          DS's Rejected (DSJ) - " +
                    ec.frm(dsjCount,6));
                ec.writeFileLine(reportFile, "          DS's Replaced (DSP) - " +
                    ec.frm(dspCount,6));
                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "            Number of Samples - " +
                    ec.frm(sampleCount,6));
                ec.writeFileLine(reportFile, "                           OK - " +
                    ec.frm(sampleOKCount,6));
                ec.writeFileLine(reportFile, "                     Rejected - " +
                    ec.frm(sampleRejectCount,6));
                ec.writeFileLine(reportFile, " ");
                java.text.SimpleDateFormat formatter =
                    new java.text.SimpleDateFormat ("yyyy-MM-dd");
                ec.writeFileLine(reportFile, "                   date range - " +
                    formatter.format(dateMin) + " to " + formatter.format(dateMax));
                ec.writeFileLine(reportFile, "               latitude range - " +
                    ec.frm(latitudeMin,10,5) + " to " + ec.frm(latitudeMax,10,5));
                ec.writeFileLine(reportFile, "              longitude range - " +
                    ec.frm(longitudeMin,10,5) + " to " + ec.frm(longitudeMax,10,5));
                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "----------------------------" +
                    "-----------------------------------------------------" +
                    "------------------------------");

                if (loadFlag) {
                    ec.writeFileLine(reportFile, " ");

                    if (fatalCount > 0) {
                        ec.writeFileLine(reportFile,
                            "  THERE ARE FATAL ERRORS - THIS LOAD IS UNSUCCESSFUL ");
                        ec.writeFileLine(reportFile,
                            "  ================================================== ");
                        ec.writeFileLine(reportFile,
                            "  As some data has been loaded, it is necessary to remove it\n" +
                            "  by running the 'Delete a survey' option on the SADCO website\n" +
                            "  located in the 'Marine Database: Admin / Load Data' menu options");
                    } else { // if (fatalCount > 0)
                        Timestamp loadDate = new Timestamp(new java.util.Date().getTime());
                        ec.writeFileLine(reportFile, "  LOAD SUCCESSFUL ");
                        ec.writeFileLine(reportFile, "  =============== ");
                        ec.writeFileLine(reportFile, "         date loaded:  " +
                            formatter.format(loadDate));

                        if (dataType != CURRENTS) {
                            ec.writeFileLine(reportFile, "  " + DATA_TYPE[dataType] +
                                " code range - " + dataCodeStart + " to " + dataCodeEnd);
                        } // if (dataType != CURRENTS)

                        ec.writeFileLine(reportFile, " ");
                        if ("".equals(passkey)) {
                            ec.writeFileLine(reportFile, "  This data is NOT flagged ");
                        } else {
                            ec.writeFileLine(reportFile, "  This data is FLAGGED ");
                        } // if (p_station.passkey is NULL)
                    } // if (fatalCount > 0)

                    // write header to station file
                    String lineA =
                        "'MARINE' " +
                        ec.frm((int) latitudeMin,4) +
                        ec.frm((int) (latitudeMax+1f),4) +
                        ec.frm((int) longitudeMin,4) +
                        ec.frm((int) (longitudeMax+1f),4) + " '" +
                        dateMin.toString().substring(0,10) + "' '" +
                        dateMax.toString().substring(0,10) + "' 'STATIONS'";
                    String lineB = "'" + survey.getSurveyId("") +
                        "' " + stationCount + " '" +
                        platformName + "' '" +
                        projectName + "' '" +
                        expeditionName + "'";
                    String lineC = "'" + instituteName + "'";
                    try {
                        stnFile.seek(headerPos1);
                        stnFile.writeBytes(lineA);
                        stnFile.seek(headerPos2);
                        stnFile.writeBytes(lineB);
                        stnFile.seek(headerPos3);
                        stnFile.writeBytes(lineC);
                    } catch (Exception e) {
                        System.out.println("<br>" + thisClass +
                            ".printHeader: Write Error: " + e.getMessage());
                        e.printStackTrace();
                    }  // try-catch

                } // if (loadflag)

                Timestamp loadDate = new Timestamp(new java.util.Date().getTime());
                String text = (loadFlag ? "loaded" : "checked");
                ec.writeFileLine(reportFile, "         date " + text + ":  " +
                    formatter.format(loadDate));
                ec.writeFileLine(reportFile, " ");
                ec.writeFileLine(reportFile, "----------------------------" +
                    "-----------------------------------------------------" +
                    "------------------------------");
            } // if (last)

        } // if ("".equals(station.getStationId("")))


        //ec.writeFileLine(reportFile, message);
        //if (dbg) System.out.println("outputReport: message = " + message);
        // output to screen or file
    } // outputReport

    void updateStationDetails(int index) {

        if (dbg) System.out.println("updateStationDetails: stationStatusTmp = " + stationStatusTmp);
        if (dbg) System.out.println("updateStationDetails: stationStatusLD = " + stationStatusLD);
        if (dbg) System.out.println("updateStationDetails: stationStatusDB[index] = " +
            index + " " + stationStatusDB[index]);

        boolean updateStation = false;
        //boolean replaceWater = false;
        boolean replaceData = false;

        // Only one station record is updated, even if the user might have
        // specified 2 stations to be replaced.  Want to come into this routine
        // to the the 'ignore' bit.
        if (!stationUpdated) {
            // check whether the record should be updated
            for (int i = 0;  i < updateStationsList.length; i++) {
                if ((station.getStationId("")+index).equals(updateStationsList[i])) {
                    updateStation = true;
                    break;
                } // if ((station.getStationId("")+index).equals ...
            } // for (int i

            // check whether the water records should be added / replaced
            for (int i = 0;  i < replaceDataList.length; i++) {
                if ((station.getStationId("")+index).equals(replaceDataList[i])) {
                    replaceData = true;
                    break;
                } // if ((station.getStationId("")+index).equals ...
            } // for (int i
        } // if (stationUpdated)

        // first check whether watphy's & children should be deleted
        // don't want to update them and then delete them
        if (dbg2) System.out.println("<br>updateStationDetails: " +
            "station.getStationId() = " + station.getStationId(""));
        if (dbg2) System.out.println("<br>updateStationDetails: " +
            "updateStation = " + updateStation);
        if (dbg2) System.out.println("<br>updateStationDetails: " +
            "replaceData = " + replaceData);

        // must the station details be updated?
        if (updateStation) {

//            stationUpdated = true;

            // update station info
            // test also check if station_id not same and lat & lon not the same
            MrnStation updStation = new MrnStation();
            //if ("di".equals(stationStatusTmp)) { // dup id
                updStation.setDateStart(station.getDateStart());
                updStation.setDateEnd(station.getDateEnd());
                updStation.setLatitude(station.getLatitude());
                updStation.setLongitude(station.getLongitude());
            //} else {  // dup record
            //    updStation.setStationId(station.getStationId());
            //} // if ("di".equals(stationStatusTmp))
            //updStation.setStnnam(station.getStnnam());   -- Don't want to update this!
            updStation.setStndep(station.getStndep());

            MrnStation whereStation =
                new MrnStation(tStation[index].getStationId());
            try {
                whereStation.upd(updStation);
            } catch(Exception e) {
                System.err.println("updateStationDetails: " +
                    "upd updStation = " + updStation);
                System.err.println("updateStationDetails: " +
                    "upd whereStation = " + whereStation);
                System.err.println("updateStation: upd sql = " +
                    whereStation.getUpdStr());
                e.printStackTrace();
            } // try-catch
            if (dbg3) System.out.println("<br>updateStationDetails: " +
                "upd whereStation = " + whereStation);
            if (dbg3) System.out.println("<br>updateStationDetails: " +
                "upd updStation = " + updStation);
            outputDebug("updateStationDetails: " + station.getStationId("") +
                " " + tStation[index].getStationId(""));

        } // if (updateStation)

        // replaceData also used to indicate which station to add the data to
        if (replaceData) {

            // only do replacement of data if there are records!
            // ignore the request if there were no data records with the same
            // subdes! - data records added automatically
            if (subdesCount[index] == 0) {

                outputDebug("subdesCount == 0");
                // a == Added
                stationStatusDB[index] = stationStatusTmp + "a";
                if ("di".equals(stationStatusTmp)) {
                    diaCount++;
                } else {
                    dsaCount++;
                } // if ("di".equals(stationStatusTmp))

            } else { // if (subdesCount[index] == 0)

                stationUpdated = true;

                String where2 =
                    "STATION_ID='" + tStation[index].getStationId("") +
                    "' and " + "SUBDES='"; // + watphy.getSubdes() + "'";

                if (dataType == CURRENTS) {

                    // delete currents
                    where2 += currents.getSubdes() + "'";
                    //currentsCount -= currents.getRecCnt(where2);      // ub12
                    currents.del(where2);

                } else if (dataType == SEDIMENT) {

                    // delete sedphy & children
                    String field = MrnSedphy.CODE;

                    where2 += sedphy.getSubdes() + "'";
                    MrnSedphy delSedphy[] = sedphy.get(field, where2, field);
                    if (dbg2) System.out.println("<br>updateStationDetails: " +
                        "del where2 = " + where2);

                    for (int i = 0; i < delSedphy.length; i++) {
                        String where3 = MrnSedchem1.SEDPHY_CODE + "=" +
                            delSedphy[i].getCode();
                        if (dbg2) System.out.println("<br>updateStationDetails: " +
                            "del where3 = " + where3);

                        // update counts
                        //sedphyCount      -= 1;                        // ub12
                        //sedchem1Count    -= sedchem1.getRecCnt(where3);// ub12
                        //sedchem2Count    -= sedchem2.getRecCnt(where3);// ub12
                        //sedpol1Count     -= sedpol1.getRecCnt(where3);// ub12
                        //sedpol2Count     -= sedpol2.getRecCnt(where3);// ub12

                        // delete sedphy & children
                        where3 = MrnSedphy.CODE + "=" + delSedphy[i].getCode();
                        sedphy.del(where3);
                    } // for (int i = 0; i < delSedphy.length; i++)

                } else if ((dataType == WATER) || (dataType == WATERWOD)) {

                    // delete watphy & children
                    String field = MrnWatphy.CODE + "," + MrnWatphy.SUBDES + "," + MrnWatphy.STATION_ID;

                    where2 += watphy.getSubdes() + "'";
                    MrnWatphy delWatphy[] = watphy.get(field, where2, field);
                    if (dbg2) System.out.println("<br>updateStationDetails: " +
                        "del where2 = " + where2);

                    for (int i = 0; i < delWatphy.length; i++) {
                        String where3 = MrnWatchem1.WATPHY_CODE + "=" +
                            delWatphy[i].getCode();
                        if (dbg2) System.out.println("<br>updateStationDetails: " +
                            "del where3 = " + where3);

                        // update counts
                        //watphyCount      -= 1;                        // ub12
                        //watchem1Count    -= watchem1.getRecCnt(where3);// ub12
                        //watchem2Count    -= watchem2.getRecCnt(where3);// ub12
                        //watchlCount      -= watchl.getRecCnt(where3); // ub12
                        //watnutCount      -= watnut.getRecCnt(where3); // ub12
                        //watpol1Count     -= watpol1.getRecCnt(where3);// ub12
                        //watpol2Count     -= watpol2.getRecCnt(where3);// ub12

                        // delete watphy & children
                        where3 = MrnWatphy.CODE + "=" + delWatphy[i].getCode();
                        watphy.del(where3);

                    } // for (int i = 0; i < delWatphy.length; i++)

                    // also delete water profile qc record
                    String where4 = MrnWatprofqc.STATION_ID + "='" +
                        delWatphy[0].getStationId("") + "' and " +
                        MrnWatprofqc.SUBDES + "='" + delWatphy[0].getSubdes("") + "'";
                    watProfQC.del(where4);

                } // if (dataType == CURRENTS)

                // p == rePlace
                stationStatusDB[index] = stationStatusTmp + "p";
                if ("di".equals(stationStatusTmp)) {
                    dipCount++;
                } else {
                    dspCount++;
                } // if ("di".equals(stationStatusTmp))

            } // if (subdesCount[index] == 0)

            // update the station record 2b loaded with the station-id of the
            // station to add /replace the water records
            station.setStationId(tStation[index].getStationId(""));    // *****
            if (dbg) System.out.println("<br>updateStationDetails: station = " +
                station);

        } else { // if (replaceData)

            //stationIgnore = true;

            // j == reJect
            stationStatusDB[index] = stationStatusTmp + "j";
            if ("di".equals(stationStatusTmp)) {
                dijCount++;
            } else {
                dsjCount++;
            } // if ("di".equals(stationStatusTmp))

        } // if (replaceData)



        // if the station was updated, and the station_id is different,
        // make the current station id same as the one that was updated
        // check in loadData if the station-id is different, and update
        // the <data> records accordingly
        // special case - if the station was not updated, but there were no
        // current records (i.e. current records to be added to an existing
        // CTD station) - make sure that the stationIDs are the same
        //if (stationUpdated) {
/*
        if (stationUpdated || !dataExists) {
            if (!tStation[index].getStationId("").equals(station.getStationId(""))) {
                station.setStationId(tStation[index].getStationId(""));
                if (dbg) System.out.println("<br>updateStationDetails: station = " +
                    station);
            } // if (!tStation[0].getStationId("").equals(
        } // if (stationUpdated)
*/
        if (dbg) System.out.println("updateStationDetails: stationStatusDB[index] = " +
            index + " " + stationStatusDB[index]);

    } // updateStationDetails


    /**                                                                 // ub11
     * Calculate the depth from the pressure and latitude               // ub11
     * @param   p   pressure                                            // ub11
     * @param   lat latitude, in decimal degrees                        // ub11
     * @returns the depth                                               // ub11
     */                                                                 // ub11
    float calcDepth(float p, float lat) {                               // ub11
                                                                        // ub11
        //function depth = calcDepth(p,lat)                             // ub11
        double x = Math.sin(lat/57.29578);                              // ub11
        //%disp(sprintf('%20.15f',x));                                  // ub11
        x = x*x;                                                        // ub11
        //%disp(sprintf('%20.15f',x));                                  // ub11
        double gr = 9.780318*(1.0+(5.2788e-3+2.36e-5*x)*x) + 1.092e-6*p;// ub11
        //%disp(sprintf('%20.15f',gr));                                 // ub11
        double depth = (((-1.82E-15*p+2.279E-10)*p-2.2512e-5)*p+9.72659)*p;// ub11
        //%disp(sprintf('%20.15f',depth));                              // ub11
        depth = depth/gr;                                               // ub11
        //%disp(sprintf('%20.15f',depth));                              // ub11
        return (float)depth;                                            // ub11
                                                                        // ub11
    } // calcDepth                                                      // ub11

/** utility functions


    /**
     * Convert a string to a float, and divide by the give number
     * @param t StringTokenizer
     * @return the next token
     */
    String getNextToken(java.util.StringTokenizer t) {
        return (t.hasMoreTokens() ? t.nextToken() : "");
    } // getNextToken


    /**
     * Convert a string to a float, and divide by the give number
     * @param value
     * @param divideBy number to divide by
     * @return the converted float value
     */
    float toFloat(String value, float divideBy) {
        value = value.trim();
        if (value.indexOf("9999") > -1) {
            // .9999's were made floatnull - check for them!
            if (value.indexOf(".9999") == -1) value = ""; // ub01
        } // if (value.indexOf("9999") > -1)s
        return (!"".equals(value)
            ? new Float(value).floatValue() / divideBy
            : Tables.FLOATNULL);
    } // toFloat


    /**
     * Convert a string to an integer
     * @param value
     * @return the converted integer value
     */
    int toInteger(String value) {
        value = value.trim();
        if ("9999".equals(value)) value = "";
        return (!"".equals(value)
            ? new Integer(value).intValue()
            : Tables.INTNULL);
    } // toInteger


    /**
     * Convert a string to null if blank
     * @param value
     * @return the converted string value
     */
    String toString(String value) {
        value = value.trim();
        if (".".equals(value)) value = "";
        if ("n/a".equals(value)) value = "";
        return (!"".equals(value) ? value : Tables.CHARNULL);
    } // toString


    /**
     * return a 0 if NULL
     * @param value
     * @return 0 / value
     */
    int checkNull(int value) {
        return (value != Tables.INTNULL ? value : 0);
    } // checkNull
    float checkNull(float value) {
        return (value != Tables.FLOATNULL ? value : 0f);
    } // checkNull


    /**
     * Write the error message to output
     * @param  message  the error message
     */
    void outputError(String message) {

        fatalCount++;
/*
        if (fatalCount == 1) {
            //fatalErrorsTable.addRow(ec.cr1ColRow(
            //    "Fatal errors", true, "red", IHAlign.CENTER));
            TableRow row = new TableRow();
            row.addCell(new TableDataCell(
                "<font color=red><b>Fatal errors</b></font>").setHAlign(IHAlign.CENTER));
            fatalErrorsTable.addRow(row);
        } // if (fatalCount == 1)
        //fatalErrorsTable.addRow(ec.cr1ColRow(message));
        TableRow row = new TableRow();
        row.addCell(new TableDataCell(message));
        fatalErrorsTable.addRow(row);
*/
        ec.writeFileLine(reportFile, message);
        if (dbg) System.out.println("<br>outputError: message = " + message);
    } // outputError


    /**
     * Write the debug message to output
     * @param  message  the debug message
     */
    void outputDebug(String message) {
        ec.writeFileLine(debugFile, message);
    } // outputDebug


} // class LoadMRNCommon
