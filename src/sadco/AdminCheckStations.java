package sadco;

import oracle.html.*;
import java.sql.*;
import java.io.*;

/**
 * This class checks for valid stations:                                    <br>
 * 1.  calculates the speed in knots between two points.                    <br>
 * 2.  ...                                                                  <br>
 *                                                                          <br>
 * It's parameters are the following:                                       <br>
 *                                                                          <br>
 * SadcoMRN                                                                 <br>
 * screen.equals("admin")                                                   <br>
 * version.equals("") (not in program yet)                                  <br>
 *                                                                          <br>
 * @author 2002/02/13 - SIT Group.                                          <br>
 * @version                                                                 <br>
 * 2002/02/13 - Mario August       - created class                          <br>
 * 2004/07/10 - Ursula von St Ange - replace overland checking with etopo   <br>
 * 2004/07/16 - Ursula von St Ange - add profile checking for temperature   <br>
 *                                   and salinity and oxygen                <br>
 * 2004/12/14 - Ursula von St Ange - add profile spike testing              <br>
 */
public class AdminCheckStations extends CompoundItem {

    /** A boolean variable for debugging purposes */
    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();
    sadco.SadConstants sc = new sadco.SadConstants();

    // 1 minute == 1 Nm either side == 2 Nm sq
    static final float  AREA_RANGE_VAL   = 0.01667f;
    // 30 seconds == 1/2 Nm either side = 1 Nm sq
    //static final float  AREA_RANGE_VAL   = 0.00833f;

    //static final int    TIME_RANGE_VAL   = 30;
    //static final int    TIME_RANGE_VAL   = 120;
    // +/- (2 hours + 2 minutes) - catch out SAST / UTC
    //static final int    TIME_RANGE_VAL   = 122;
    static final int    TIME_RANGE_VAL   = 10;

    static float MAX_SPEED = 20f;

    static int MAX_SUBDES = 10;

    /** Arguments */
    String surveyId = "";

    //int totStations = 0;
    int speedErrorCnt = 0;
    int overlandErrorCnt = 0;
    int northErrorCnt = 0;
    int watphyErrorCnt = 0;

    /** RandomAccessFile variable */
    RandomAccessFile stationLstFile;
    RandomAccessFile stationRepFile;
    RandomAccessFile surveyRepFile;
    RandomAccessFile speedErrFile;
    RandomAccessFile overlandErrFile;
    RandomAccessFile northErrFile;
    RandomAccessFile watphyErrFile;
    RandomAccessFile profErrFile;

    // work variables for watphy info
    //String subdesArray[][] = null;
    //int    watphyCounts[][] = null;
    //float  lastDepths[][] = null;
    //String parms[][] = null;
    //float  aveDistances[][] = null;

    boolean isCruiseTemperature = false;
    boolean isCruiseSalinity = false;
    boolean isCruiseOxygen = false;
    int     numSubdes = 0;
    String  subdesArray2[] = new String[50];
    int     numRecords = 0;
    int     numStations = 0;
    boolean isWatnutData = false;
    boolean isWatchlData = false;
    boolean isWatchem1Data = false;
    boolean isWatchem2Data = false;
    boolean isWatpol1Data = false;
    boolean isWatpol2Data = false;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public AdminCheckStations(String args[]) {

        try {
            AdminCheckStationsActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // AdminCheckStations constructor


    /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void AdminCheckStationsActual(String args[]) {

        // get the correct PATH NAME - used while debugging
        if (dbg) System.out.println("<br>" + thisClass + ": host = " +
            ec.getHost());
        String pathName = "";
        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL;
        } else {
            pathName = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(sc.HOST))
        pathName += "marine/";

        // get the survey_id from the command line
        String surveyId = ec.getArgument(args, sc.SURVEYID);

        // create file names
        String stationLstFileName   = surveyId.replace('/','_') + ".stnLst";
        String stationRepFileName   = surveyId.replace('/','_') + ".stnRep";
        String surveyRepFileName    = surveyId.replace('/','_') + ".srvRep";
        String speedErrFileName     = surveyId.replace('/','_') + ".spdErr";
        String overlandErrFileName  = surveyId.replace('/','_') + ".ovlErr";
        String northErrFileName     = surveyId.replace('/','_') + ".nrthErr";
        String watphyErrFileName    = surveyId.replace('/','_') + ".wphErr";
        String profErrFileName      = surveyId.replace('/','_') + ".prfErr";
        if (dbg) System.out.println("<br>stationLstFileName = " + pathName +
            stationLstFileName);

        // delete old files
        ec.deleteOldFile(pathName + stationLstFileName);
        ec.deleteOldFile(pathName + stationRepFileName);
        ec.deleteOldFile(pathName + surveyRepFileName);
        ec.deleteOldFile(pathName + speedErrFileName);
        ec.deleteOldFile(pathName + overlandErrFileName);
        ec.deleteOldFile(pathName + northErrFileName);
        ec.deleteOldFile(pathName + watphyErrFileName);
        ec.deleteOldFile(pathName + profErrFileName);

        // open files
        try {
            stationLstFile  = new RandomAccessFile(pathName + stationLstFileName, "rw");
            stationRepFile  = new RandomAccessFile(pathName + stationRepFileName, "rw");
            surveyRepFile   = new RandomAccessFile(pathName + surveyRepFileName,  "rw");
            speedErrFile    = new RandomAccessFile(pathName + speedErrFileName,   "rw");
            overlandErrFile = new RandomAccessFile(pathName + overlandErrFileName,"rw");
            northErrFile    = new RandomAccessFile(pathName + northErrFileName,   "rw");
            watphyErrFile   = new RandomAccessFile(pathName + watphyErrFileName,  "rw");
            profErrFile     = new RandomAccessFile(pathName + profErrFileName,    "rw");
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "opening files", "");
        } //try-catch

        // get the stations for this survey, and
        MrnStation[] stations = getStationRecords(surveyId);
        System.err.println("AdminCheckStationsActual: " +
            "stations.length = " + stations.length + " " + surveyId);

        // sort stations according to date/time
        stations = bubbleSort(stations);

        // initialise watphy info arrays
        //subdesArray = new String[stations.length][MAX_SUBDES];
        //watphyCounts = new int[stations.length][MAX_SUBDES];
        //lastDepths = new float[stations.length][MAX_SUBDES];
        //parms = new String[stations.length][MAX_SUBDES];
        //aveDistances = new float[stations.length][MAX_SUBDES];

        // check watphy records for profile errors
        stations = checkWatphyRecs(stations);

        String allSubdes = "";
        for (int i = 0; i < numSubdes; i++) {
            allSubdes += ec.frm(subdesArray2[i],6);
        } // for (int i = 0; i < numSubdes; i++)

        float aveRecords = (float) numRecords / (float)numStations;

        ec.writeFileLine(surveyRepFile,
            ec.frm(surveyId,9) + ec.frm(stations.length, 5) + " " +
                ec.frm(aveRecords, 10,2) + " " +
                (isCruiseTemperature ? "t" : " ") +
                (isCruiseSalinity    ? "s" : " ") +
                (isCruiseOxygen      ? "o" : " ") + " " +
                (isWatnutData   ? "nut "   : "    ") +
                (isWatchlData   ? "chl "   : "    ") +
                (isWatchem1Data ? "chem1 " : "      ") +
                (isWatchem2Data ? "chem2 " : "      ") +
                (isWatpol1Data  ? "pol1 "  : "     ") +
                (isWatpol2Data  ? "pol2 "  : "     ") +
                ec.frm(numSubdes,3) + " " + allSubdes);


        // calculate the ship speeds between stations
        calcSpeeds(stations);

        // test for overland, for > 10N, and check for adjacent stations
        System.err.print("testing various: ");
        for (int i = 0; i < stations.length; i++) {
            System.err.print(".");
            if (dbg) System.out.println("<br>i, stations[i].getStationId(), " +
                "stations[i].getDateStart() = " + i + " " +
                stations[i].getStationId("") + " " + stations[i].getDateStart(""));
            testOverLand(stations[i]);
            testNorth(stations[i]);
            //checkAdjacentStations(stations[i], subdesArray[i],
            //    watphyCounts[i], lastDepths[i]);
        } // for (int i = 0; i < stations.length; i++)
        System.err.println();

        // create links for download of files
        Link stationRepLink = new Link (
            sc.LOADS_URL + stationLstFileName, stationLstFileName);
        Link speedErrLink = new Link (
            sc.LOADS_URL + speedErrFileName, speedErrFileName);
        Link overlandErrLink = new Link (
            sc.LOADS_URL + overlandErrFileName, overlandErrFileName);
        Link northErrLink = new Link (
            sc.LOADS_URL + northErrFileName, northErrFileName);
        Link watphyErrLink = new Link (
            sc.LOADS_URL + watphyErrFileName, watphyErrFileName);
        Link profErrLink = new Link (
            sc.LOADS_URL + profErrFileName, profErrFileName);

        // Create main HTML table
        DynamicTable mainTable = new DynamicTable(0);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        mainTable.setCenter();
        //mainTable.setFrame(ITableFrame.BOX);

        // heading
        mainTable.addRow(ec.crSpanColsRow("Check for valid stations",2,true,
            "blue",IHAlign.CENTER,"+2" ));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));

        // feedback to user
        mainTable.addRow(ec.cr2ColRow("Survey id :",surveyId));
        mainTable.addRow(ec.cr2ColRow("No of stations :",stations.length));
        mainTable.addRow(ec.cr2ColRow("No of suspect stations :",speedErrorCnt));
        mainTable.addRow(ec.cr2ColRow("No of stations over land:",overlandErrorCnt));
        mainTable.addRow(ec.cr2ColRow("No of stations > 10N:",northErrorCnt));
        mainTable.addRow(ec.cr2ColRow("No of stations with no watphy records:",watphyErrorCnt));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTable.addRow(ec.cr2ColRow("Detail Station Eeport :",stationRepLink.toHTML()));
        mainTable.addRow(ec.cr2ColRow("Speed Error Report :",speedErrLink.toHTML()));
        mainTable.addRow(ec.cr2ColRow("Overland Error Report :",overlandErrLink.toHTML()));
        mainTable.addRow(ec.cr2ColRow("North Error Report :",northErrLink.toHTML()));
        mainTable.addRow(ec.cr2ColRow("Watphy Error Report :",watphyErrLink.toHTML()));
        mainTable.addRow(ec.cr2ColRow("Profiles Error Report :",profErrLink.toHTML()));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTable.addRow(ec.crSpanColsRow(ec.downloadFileInstructions().toHTML(),2));

        addItem(mainTable.setCenter());

        try {
            stationLstFile.close();
            stationRepFile.close();
            surveyRepFile.close();
            speedErrFile.close();
            overlandErrFile.close();
            northErrFile.close();
            watphyErrFile.close();
            profErrFile.close();
        } catch(Exception e) {
            ec.processError(e, "AdminCheckStations", "constructor", "");
        } //try-catch

    } // AdminCheckStationsActual


    /**
     * Calculate the ship speeds between stations
     * @param stations array (MrnStation[])
     * @return void
     */
    void calcSpeeds(MrnStation[] stations) {

        if (dbg) System.out.println("<br>calcSpeeds: stations[0].getStation() = " +
            stations[0].getStationId(""));
        if (dbg) System.out.println("<br>calcSpeeds: stations[0].getDateStart() = " +
            stations[0].getDateStart(""));

        String dt1 = stations[0].getDateStart("").substring(0,16);

        ec.writeFileLine(stationLstFile,
            ec.frm(stations[0].getStationId(),12) + ", " +
            ec.frm(-stations[0].getLatitude(),10,5) + ", " +
            ec.frm(stations[0].getLongitude(),10,5) + ", " + dt1);

        System.err.print("calcSpeeds: ");
        for (int i = 1; i < stations.length;i++) {
            System.err.print(".");

            // call function to calulate speed
            float speed = calcSpeed(
                stations[i-1].getLatitude(),  stations[i].getLatitude(),
                stations[i-1].getLongitude(), stations[i].getLongitude(),
                stations[i-1].getDateEnd(),   stations[i].getDateStart());

            String dt2 = stations[i].getDateStart("").substring(0,16);

            ec.writeFileLine(stationLstFile, ec.frm(
                stations[i].getStationId(),12) + ", "+
                ec.frm(-stations[i].getLatitude(),10,5) + ", " +
                ec.frm(stations[i].getLongitude(),10,5) + ", " + dt2);

            if (speed > MAX_SPEED) {
                ec.writeFileLine(speedErrFile,
                    ec.frm(stations[i-1].getStationId(),12) + ", " +
                    ec.frm(-stations[i-1].getLatitude(),10,5) + ", " +
                    ec.frm(stations[i-1].getLongitude(),10,5) + ", " + dt1);
                ec.writeFileLine(speedErrFile, ec.frm(speed,10,3) +" knots");
                ec.writeFileLine(speedErrFile,
                    ec.frm(stations[i].getStationId(),12) + ", "+
                    ec.frm(-stations[i].getLatitude(),10,5) + ", " +
                    ec.frm(stations[i].getLongitude(),10,5) + ", " + dt2);
                ec.writeFileLine(speedErrFile, "");
            } // if (speed > MAX_SPEED)

            dt1 = dt2;

        } // for (int i = 1; i < stations.length;i++) {
        System.err.println();

    } // calcSpeeds


    /**
     *
     *
     */
    void checkAdjacentStations(MrnStation station, String subdes,
            int watphyCount, float lastDepth) {

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

        String where =
            MrnStation.DATE_START + ">=" +
                Tables.getDateFormat(dateStartMin)  + " and " +
            MrnStation.DATE_END + "<=" +
                Tables.getDateFormat(dateEndMax)  + " and " +
            MrnStation.LATITUDE + " between " +
                (station.getLatitude()-AREA_RANGE_VAL) + " and " +
                (station.getLatitude()+AREA_RANGE_VAL) + " and " +
            MrnStation.LONGITUDE + " between " +
                (station.getLongitude()-AREA_RANGE_VAL) + " and " +
                (station.getLongitude()+AREA_RANGE_VAL);
        if (dbg) System.out.println("<br><br>checkStationExists: station: " +
            "where = " + where);

        // get the records
        MrnStation[] tStations = new MrnStation().get(
            "*", where, MrnStation.STATION_ID);

        // if any were found, write to file
        if (tStations.length > 1) {
            ec.writeFileLine(stationRepFile,
                ec.frm(station.getSurveyId(),10) +
                ec.frm(station.getStationId(),14) +
                ec.frm(-station.getLatitude(),10,5) +
                ec.frm(station.getLongitude(),10,5) + " " +
                ec.frm(station.getStnnam(""),12) +
                ec.frm(station.getDateStart("").substring(0,16),18) +
                ec.frm(subdes,5) + ec.frm(watphyCount,5) +
                ec.frm(lastDepth,10,2));
            for (int i = 0; i < tStations.length; i++) {
                if (!station.getStationId().equals(tStations[i].getStationId())) {

                    String lSubdes = "";
                    int lWatphyCount = 0;
                    float lLastDepth = 0f;

                    // get the watphy records
                    MrnWatphy[] watphyRecs = getWatphyRecords(tStations[i].getStationId());
                    if (watphyRecs.length > 0) {
                        // put the watphy time into station time - for later sorting
                        // of stations
                        tStations[i].setDateStart(watphyRecs[0].getSpldattim());
                        lSubdes = watphyRecs[0].getSubdes("");
                        lWatphyCount = watphyRecs.length;
                        lLastDepth = watphyRecs[watphyRecs.length - 1].getSpldep();
                    } // if (watphyRecs.length > 0)

                    ec.writeFileLine(stationRepFile,
                        ec.frm(tStations[i].getSurveyId(),10) +
                        ec.frm(tStations[i].getStationId(),14) +
                        ec.frm(-tStations[i].getLatitude(),10,5) +
                        ec.frm(tStations[i].getLongitude(),10,5) + " " +
                        ec.frm(tStations[i].getStnnam(""),12) +
                        ec.frm(tStations[i].getDateStart("").substring(0,16),18) +
                        ec.frm(lSubdes,5) +
                        ec.frm(lWatphyCount,5) +
                        ec.frm(lLastDepth,10,2) +
                        " ");
                } // if (!station.getStationId().equals(
            } // for (int i = 0; i < tStations.length; i++)

            ec.writeFileLine(stationRepFile, " ");
        } // if (tStations.length > 0)

        if (dbg) System.out.println("<br><br>checkStationExists: " +
            "tStations.length = " + tStations.length);

    } // checkAdjacentStations


    /**
     * Check the parameter profile (temperature, salinity or oxygen)
     * @param latitude : station latitude
     * @param longitude : station longitude
     * @param atlas     : WOAtlas array
     * @param watphyRecs : watphy records for station
     */
    void checkProfiles(float latitude, float longitude, MrnWoatlas5[] atlas,
            MrnWatphy[] watphyRecs) {

        // find out whether the parameters exist for the station
        boolean isTemperature = false;
        boolean isSalinity = false;
        boolean isOxygen = false;
        for (int i = 0; i < watphyRecs.length; i++) {
            if (watphyRecs[i].getTemperature() != MrnWatphy.FLOATNULL) {
                isTemperature = true;
            } // if (watphyRecs[i].getTemperature() != MrnWatphy.FLOATNULL)
            if (watphyRecs[i].getSalinity() != MrnWatphy.FLOATNULL) {
                isSalinity = true;
            } // if (watphyRecs[i].getSalinity() != MrnWatphy.FLOATNULL)
            if (watphyRecs[i].getDisoxygen() != MrnWatphy.FLOATNULL) {
                isOxygen = true;
            } // if (watphyRecs[i].getDisoxygen() != MrnWatphy.FLOATNULL)
//            System.out.println(watphyRecs[i].getStationId() + " " +
//                watphyRecs[i].getSpldep() + " *" + watphyRecs[i].getSubdes("") + "*");
        } // for (int i = 0; i < watphyRecs.length; i++)

        // work variables
        float woaDepth[] = new float[atlas.length];
        float woaParmMin[] = new float[atlas.length];
        float woaParmMax[] = new float[atlas.length];
        float watphyDepth[] = new float[watphyRecs.length];
        float watphyParm[] = new float[watphyRecs.length];

        // check the temperature profile
        if (isTemperature) {

            System.out.println("temperature");

            // transfer the values to the work variables
            for (int i = 0; i < atlas.length; i++) {
                woaDepth[i] = atlas[i].getDepth();
                woaParmMin[i] = atlas[i].getTemperatureMin();
                woaParmMax[i] = atlas[i].getTemperatureMax();
            } // for (int i = 0; i < atlas.length; i++)

            int j = 0;
            for (int i = 0; i < watphyRecs.length; i++) {
                if (watphyRecs[i].getTemperature() != MrnWatphy.FLOATNULL) {
                    watphyDepth[j] = watphyRecs[i].getSpldep();
                    watphyParm[j] = watphyRecs[i].getTemperature();
                    j++;
                } // if (watphyRecs[i].getTemperature() != MrnWatphy.FLOATNULL)
            } // for (int i = 0; i < watphyRecs.length; i++)

            checkProfileEnvelopes("temperature", woaDepth,
                woaParmMin, woaParmMax, j, watphyDepth, watphyParm);

//            checkSpikesGradient("temperature",  j, watphyDepth, watphyParm,
//                2f, -10f, 10f, 10f);

        } else {
            ec.writeFileLine(profErrFile, "temperature 0 0");
        } // if (isTemperature)

        // check the salinity profile
        if (isSalinity) {

            System.out.println("salinity");

            // transfer the values to the work variables
            for (int i = 0; i < atlas.length; i++) {
                woaDepth[i] = atlas[i].getDepth();
                woaParmMin[i] = atlas[i].getSalinityMin();
                woaParmMax[i] = atlas[i].getSalinityMax();
            } // for (int i = 0; i < atlas.length; i++)

            int j = 0;
            for (int i = 0; i < watphyRecs.length; i++) {
                if (watphyRecs[i].getSalinity() != MrnWatphy.FLOATNULL) {
                    watphyDepth[j] = watphyRecs[i].getSpldep();
                    watphyParm[j] = watphyRecs[i].getSalinity();
                    j++;
                } // if (watphyRecs[i].getSalinity() != MrnWatphy.FLOATNULL)
            } // for (int i = 0; i < watphyRecs.length; i++)

            checkProfileEnvelopes("salinity", woaDepth,
                woaParmMin, woaParmMax, j, watphyDepth, watphyParm);

            checkSpikesGradient("salinity",  j, watphyDepth, watphyParm,
                0.3f, -5f, 5f, 5f);

        } else {
            ec.writeFileLine(profErrFile, "salinity 0 0");
        } // if (isSalinity)

        // check the oxygen profile
        if (isOxygen) {

            System.out.println("oxygen");

            // transfer the values to the work variables
            for (int i = 0; i < atlas.length; i++) {
                woaDepth[i] = atlas[i].getDepth();
                woaParmMin[i] = atlas[i].getOxygenMin();
                woaParmMax[i] = atlas[i].getOxygenMax();
            } // for (int i = 0; i < atlas.length; i++)

            int j = 0;
            for (int i = 0; i < watphyRecs.length; i++) {
                if (watphyRecs[i].getDisoxygen() != MrnWatphy.FLOATNULL) {
                    watphyDepth[j] = watphyRecs[i].getSpldep();
                    watphyParm[j] = watphyRecs[i].getDisoxygen();
                    j++;
                } // if (watphyRecs[i].getDisoxygen() != MrnWatphy.FLOATNULL)
            } // for (int i = 0; i < watphyRecs.length; i++)

            checkProfileEnvelopes("oxygen", woaDepth,
                woaParmMin, woaParmMax, j, watphyDepth, watphyParm);

        } else {
            ec.writeFileLine(profErrFile, "oxygen 0 0");
        } // if (isOxygen)

    } // checkProfiles


    /**
     * Does the actual checking of the parameter profile (temperature,
     *   salinity or oxygen)
     * @param parameter     : the profile being checked
     * @param woaDepth      : array of WOAtlas depths
     * @param woaParmMin    : array of WOAtlas minima for parameter
     * @param woaParmMax    : array of WOAtlas maxima for parameter
     * @param watphyLength  : the number of watphy records
     * @param watphyDep     : array of watphy sample depth values
     * @param watphyParm    : array of watphy parameter values
     */
    void checkProfileEnvelopes (String parameter,
            float[] woaDepth, float[] woaParmMin, float[] woaParmMax,
            int watphyLength, float[] watphyDep, float[] watphyParm) {

        // find deepest depth for temperature
        int maxIndex = woaParmMin.length;
        for (int i = woaParmMin.length-1; i >= 0 ; i--) {
            //if (atlas[i].getTemperatureMin() != MrnWoatlas.FLOATNULL)
            //    System.out.println("i = " + ec.frm(i,2) +
            //        ec.frm(atlas[i].getDepth(),8,1) +
            //        ec.frm(atlas[i].getTemperatureMin(),8,3) +
            //        ec.frm(atlas[i].getTemperatureMax(),8,3));
            if (woaParmMin[i] == MrnWoatlas5.FLOATNULL)
                maxIndex = i;
        } // for (int i = 0; i < atlas.length; i++)
        if (maxIndex > 0) maxIndex--;
        System.out.println("maxIndex = " + maxIndex);

        int j = 0;
        boolean error = false;
        //boolean errorArray[] = new boolean[watphyParm.length];

        // loop through all watphy recs
        for (int i = 0; i < watphyLength; i++) {

            // look for depth in woatlas
            while ((j < maxIndex) && (woaDepth[j] < watphyDep[i])) {
                j++;
            } // while (j < maxIndex &&  ..

            // if there is an atlas record
            if ((woaDepth[maxIndex] >= woaDepth[j]) && (j <= maxIndex)) {

                // correct j value
                if (j > 0) j--;

                // find out where spldep lies
                if (woaDepth[j] == watphyDep[i]) {

                    if ((watphyParm[i] < woaParmMin[j]) ||
                        (watphyParm[i] > woaParmMax[j])) {
                        System.out.print(ec.frm(woaParmMin[j],9,3) +
                            ec.frm(watphyParm[i],9,3) +
                            ec.frm(woaParmMax[j],9,3));
                        System.out.println(" *** error *** (same j)");
                        error = true;
                    } // if ((watphyParm[i] < woaParmMin[j])

                } else if (woaDepth[j+1] == watphyDep[i]){

                    if ((watphyParm[i] < woaParmMin[j+1]) ||
                        (watphyParm[i] > woaParmMax[j+1])) {
                        System.out.print(ec.frm(woaParmMin[j+1],9,3) +
                            ec.frm(watphyParm[i],9,3) +
                            ec.frm(woaParmMax[j+1],9,3));
                        System.out.println(" *** error *** (same j+1)");
                        error = true;
                    } // if ((watphyParm[i] < woaParmMin[j+1])

                } else {

                    float min = interpolate(woaParmMin[j], woaParmMin[j+1],
                        woaDepth[j], woaDepth[j+1], watphyDep[i]);
                    float max = interpolate(woaParmMax[j], woaParmMax[j+1],
                        woaDepth[j], woaDepth[j+1], watphyDep[i]);
                    if ((watphyParm[i] < min) || (watphyParm[i] > max)) {
                        System.out.print(ec.frm(min,9,3) +
                            ec.frm(watphyParm[i],9,3) +
                            ec.frm(max,9,3));
                        System.out.print(ec.frm(woaDepth[j],9,3) +
                            ec.frm(watphyDep[i],9,3) +
                            ec.frm(woaDepth[j+1],9,3));
                        System.out.println(" *** error *** (between)");
                        error = true;
                    } // if ((watphyParm[i] < min)
                } // if (atlas[j].getDepth() == watphyDep[i])

            } else {

                System.out.println(" no more atlas records, last =  " +
                    woaDepth[maxIndex] + ", " + watphyParm[i]);

            } // if (j < atlas.length())

        } // for (int i = 0; i < watphyRecs.length; i++)

        // for plotting
        String errorString = (error ? "***Error***" : "");

        // print profile envelope
        ec.writeFileLine(profErrFile, parameter + " " + (maxIndex+1) + " " +
            watphyLength + " " + errorString);
        for (int i = 0; i <= maxIndex; i++) {
            ec.writeFileLine(profErrFile, ec.frm(i,3) +
                ec.frm(woaDepth[i],8,1) +
                ec.frm(woaParmMin[i],8,3) +
                ec.frm(woaParmMax[i],8,3));
            //System.out.println("i = " + ec.frm(i,2) +
            //    ec.frm(woaDepth[i],8,1) +
            //    ec.frm(woaParmMin[i],8,3) +
            //    ec.frm(woaParmMax[i],8,3));
        } // for (int i = 0; i < maxIndex; i++)

        // print the watphy records
        for (int i = 0; i < watphyLength; i++) {
            //System.out.println(ec.frm(i,3) + " " +
            //    ec.frm(watphyDep[i],8,2) + ec.frm(watphyParm[i],8,2));
            ec.writeFileLine(profErrFile, ec.frm(i,3) +
                ec.frm(watphyDep[i],8,2) + ec.frm(watphyParm[i],8,2));
        } // for (int i = 0; i < watphyRecs.length; i++)

    } // checkProfileEnvelopes


    /**
     * Check for spikes.  According to Manual 22, test 2.7
     * @param parameter     : the profile being checked
     * @param watphyLength  : the number of watphy records
     * @param watphyDep     : array of watphy sample depth values
     * @param watphyParm    : array of watphy parameter values
     * @param threshold     : threshold value for test
     */
    void checkSpikesGradient(String parameter,
            int watphyLength, float[] watphyDep, float[] watphyParm,
            float threshold, float lowerThreshold, float upperThreshold,
            float gradient) {

        // check for top spike only
        if (watphyLength >= 2) {
            float difference = watphyParm[0] - watphyParm[1];
            if ((lowerThreshold < difference) && (difference < upperThreshold)) {
                // no spike
            } else {
                ec.writeFileLine(profErrFile, parameter + " top spike = " +
                    watphyDep[0] + " " + watphyParm[0] + " " + difference);
            } // if ((lowerThreshold < difference) && (difference < upperThreshold))

        } // if (watphyLength >= 2)

        if (watphyLength > 2) {

            // check for spikes in the middle and gradient
            for (int i = 1; i < (watphyLength-1); i++) {

                float part1 = Math.abs(watphyParm[i] -
                    (watphyParm[i+1] + watphyParm[i-1])/2f);
                float part2 = Math.abs(watphyParm[i-1] - watphyParm[i+1])/2f;
                if ((part1 - part2) > threshold) {
                    ec.writeFileLine(profErrFile, parameter + " spike = " +
                        watphyDep[i] + " " + watphyParm[i] + " " + (part1 - part2));
                    System.out.println(parameter + " spike = " +
                        watphyDep[i] + " " + watphyParm[i] + " " + (part1 - part2));
                } // if ((part1 - part2) > threshold)

                if (part1 > gradient) {
                    ec.writeFileLine(profErrFile, parameter + " gradient = " +
                        watphyDep[i] + " " + watphyParm[i] + " " + (part1 - part2));
                } // if (part1 > gradient)
            } // for (int i = 1; i < (watphyLength-1); i++)

            // check for bottom spikes
            float difference = watphyParm[watphyLength-1] - watphyParm[watphyLength-2];
            if ((lowerThreshold < difference) && (difference < upperThreshold)) {
                // no spike
            } else {
                ec.writeFileLine(profErrFile, parameter + " bottom spike = " +
                    watphyDep[watphyLength-1] + " " + watphyParm[watphyLength-1] +
                    " " + difference);
            } // if ((lowerThreshold < difference) && (difference < upperThreshold))

        } // if (watphyLength > 2)
    } // checkSpikesGradient


    /**
     * Get the relevant watphy records,;
     * Check for no watphy records;
     * Check for profiles
     * @param  stations array of station records
     * @return array of station records with updated date_times
     */
    MrnStation[] checkWatphyRecs(MrnStation[] stations) {

        System.err.print("checkWatphyRecs: ");

        //for all stations
        for (int i = 0; i < stations.length;i++) {
            System.err.print(".");

            // do some writing to file
            ec.writeFileLine(profErrFile, stations[i].getStationId());

            // get the watphy records
            MrnWatphy[] watphyRecs = getWatphyRecords(stations[i].getStationId());
            if (watphyRecs.length > 0) {

                // put the watphy time into station time - for later sorting
                // of stations
                stations[i].setDateStart(watphyRecs[0].getSpldattim());
                stations[i].setDateEnd(watphyRecs[watphyRecs.length - 1].getSpldattim());

                // get other stats
                //if (i == 0)
                getWatphyStats(stations[i], i, watphyRecs);
                //subdesArray[i] = watphyRecs[0].getSubdes("");
                //watphyCounts[i] = watphyRecs.length;
                //lastDepths[i] = watphyRecs[watphyRecs.length - 1].getSpldep();

                // get the relevant WO Atlas record
                MrnWoatlas5[] atlas = getWOAtlas5(stations[i].getLatitude(),
                    stations[i].getLongitude());

                System.out.println(" new station: " + stations[i].getStationId());
                if (atlas.length > 0)
                    System.out.println("woatlas latitude = " + atlas[0].getLatitude() +
                        ", longitude = " + atlas[0].getLongitude());

                // check profiles against envelopes
                if (atlas.length > 0 ) {
                    checkProfiles(stations[i].getLatitude(),
                        stations[i].getLongitude(), atlas, watphyRecs);
                } else {
                    System.out.println("no WOAtlas records");
                } //if (atlas.length > 0 )

            } else {

                // no watphy records for stations
                watphyErrorCnt += 1;
                ec.writeFileLine(watphyErrFile,
                    ec.frm(stations[i].getStationId(),12) + ", "+
                    ec.frm(-stations[i].getLatitude(),10,5) + ", " +
                    ec.frm(stations[i].getLongitude(),10,5) + ", " +
                    stations[i].getDateStart("").substring(0,16));

                //System.out.println(stations[i]);
                //System.out.println("<br>checkWatphyRecs: i, " +
                //    "stations[i].getStationId(), stations[i].getDateStart(), " +
                //    "stations[i].getDateEnd() = " +
                //    i + " " + stations[i].getStationId("") + " " +
                //    stations[i].getDateStart("") + " " +
                //    stations[i].getDateEnd(""));
            } // if (watphyRecs.length > 0)

        } //for (int i = 1; i < stations.length;i++) {
        System.err.println();

        return stations;
    } // checkWatphyRecs


    /**
     * Get Station records from Database for the surveyId
     * @param surveyId : argument value from url (string)
     * @return array of station records
     */
    MrnStation[] getStationRecords(String surveyId) {
        //if (dbg) System.out.println("<br>getStationRecords");

        // construct fields, and where and order by clauses
        String fields = MrnStation.STATION_ID + "," +
            MrnStation.SURVEY_ID + "," + MrnStation.STNNAM + "," +
            MrnStation.LATITUDE + "," + MrnStation.LONGITUDE + "," +
            MrnStation.DATE_START + "," + MrnStation.DATE_END;
        String where = MrnStation.SURVEY_ID +"='"+ surveyId +"'";
        //String where = MrnStation.SURVEY_ID +"='"+ surveyId +"'" +
        //    " and latitude between 27.5 and 30.5 " +
        //    " and longitude between 15.25 and 17.5";
        String order = MrnStation.DATE_START;

        MrnStation[] stations = new MrnStation().get(fields, where, order);

        return stations;
    } //MrnStation[] getStationRecords


    /**
     * Get Watphy records from Database for the @param stationId
     * @param stationId
     * @return array of watphy records
     */
    MrnWatphy[] getWatphyRecords(String stationId) {

        //if (dbg) System.out.println("<br>getWatphyRecords");

        // construct fields, and where and order by clauses
        String fields = "*";
        String where = MrnWatphy.STATION_ID +"='"+ stationId +"'";
        String order = MrnWatphy.SUBDES + "," + MrnWatphy.SPLDEP;

        MrnWatphy watphy = new MrnWatphy();
        MrnWatphy[] watphyRecs = watphy.get(fields,where,order);
        if (dbg) System.out.println("watphy selString = " + watphy.getSelStr());
        if (dbg) System.out.println("watphyRecs.length = " + watphyRecs.length);

        return watphyRecs;
    } // getWatphyRecords


    /**
     *
     *
     */
    void getWatphyStats(MrnStation station, int index, MrnWatphy[] watphyRecs) {

        int j = -1;
        String prevSubdes = "first";

        float sum = 0f;
        int count = 0;

        boolean isTemperature = false;
        boolean isSalinity = false;
        boolean isOxygen = false;

        boolean first = true;


        // work variables for watphy info
        String subdesArray[] = new String[MAX_SUBDES];
        int    watphyCounts[] = new int[MAX_SUBDES];
        float  lastDepths[] = new float[MAX_SUBDES];
        String parms[] = new String[MAX_SUBDES];
        float  aveDistances[] = new float[MAX_SUBDES];

        int watnutCnt[] = new int[MAX_SUBDES];
        int watchem1Cnt[] = new int[MAX_SUBDES];
        int watchem2Cnt[] = new int[MAX_SUBDES];
        int watchlCnt[] = new int[MAX_SUBDES];
        int watpol1Cnt[] = new int[MAX_SUBDES];
        int watpol2Cnt[] = new int[MAX_SUBDES];

//        System.err.println("watphyRecs.length = " + watphyRecs.length);
        for (int i = 0; i < watphyRecs.length; i++) {

            //System.out.println(i + " " + prevSubdes + " " + watphyRecs[i].getSubdes("") + " " + j);
            if (!prevSubdes.equals(watphyRecs[i].getSubdes(""))) {
//                System.err.println("1: " + i + " *" + prevSubdes + "* *" +
//                    watphyRecs[i].getSubdes("") + "*");

                if (!"first".equals(prevSubdes)) {
                    //aveDistances[index][j] = sum / (float)(count-1);
                    //watphyCounts[index][j] = count;
                    //lastDepths[index][j] = watphyRecs[i-1].getSpldep();
                    //parms[index][j] = "";
                    //if (isTemperature) parms[index][j] += "t";
                    //if (isSalinity) parms[index][j] += "s";
                    //if (isOxygen) parms[index][j] += "o";
                    aveDistances[j] = sum / (float)(count-1);
                    watphyCounts[j] = count;
                    lastDepths[j] = watphyRecs[i-1].getSpldep();
                    parms[j] = "";
                    if (isTemperature)  parms[j] += "t";
                    if (isSalinity)     parms[j] += "s";
                    if (isOxygen)       parms[j] += "o";

                    if (isTemperature)  isCruiseTemperature = true;
                    if (isSalinity)     isCruiseSalinity = true;
                    if (isOxygen)       isCruiseOxygen = true;

                    numRecords += count;
                    numStations ++;

                    sum = 0f;
                    count = 0;
                    isTemperature = false;
                    isSalinity = false;
                    isOxygen = false;
                    first = true;

                } // if (!"".equals(prevSubdes))

                // change subdes for printing
                String tmpSubdes = watphyRecs[i].getSubdes("");
                if ("".equals(tmpSubdes)) {
                    tmpSubdes = "null";
                } // if ("".equals(watphyRecs[i].getSubdes(""))
                // is subdes already in list
//                System.err.println("numSubdes = " + numSubdes);
                boolean found = false;
                for (int k = 0; k < numSubdes; k++) {
//                    System.err.println("in loop " + tmpSubdes + " " + subdesArray2[k]);
                    if (tmpSubdes.equals(subdesArray2[k]))
//                        System.err.println("found");
                        found = true;
                } // for (int k = 0; k < numSubdes; k++)
                if (!found) {
                    subdesArray2[numSubdes] = tmpSubdes;
//                    System.err.println("numSubdes = " + numSubdes + " " + subdesArray2[numSubdes]);
                    numSubdes++;
                } // if (found)

                j++;
                prevSubdes = watphyRecs[i].getSubdes("");
//                System.err.println("2: " + i + " *" + prevSubdes + "* *" +
//                    watphyRecs[i].getSubdes("") + "*");
                subdesArray[j] = watphyRecs[i].getSubdes("");
                //System.err.println(" j = " + j + " *" + watphyRecs[i].getSubdes("") + "*");

            } // if (!prevSubdes.equals(watphyRecs[i].getSubdes(""))

            // calculate sums of differences
            if (!first) {
                sum += watphyRecs[i].getSpldep() - watphyRecs[i-1].getSpldep();
            } else {
                first = false;
            } // if (!first)
            count++;

            // find out whether the parameters exist for the station
            if (watphyRecs[i].getTemperature() != MrnWatphy.FLOATNULL) {
                isTemperature = true;
            } // if (watphyRecs[i].getTemperature() != MrnWatphy.FLOATNULL)
            if (watphyRecs[i].getSalinity() != MrnWatphy.FLOATNULL) {
                isSalinity = true;
            } // if (watphyRecs[i].getSalinity() != MrnWatphy.FLOATNULL)
            if (watphyRecs[i].getDisoxygen() != MrnWatphy.FLOATNULL) {
                isOxygen = true;
            } // if (watphyRecs[i].getDisoxygen() != MrnWatphy.FLOATNULL)


            // find out presence of sub-table data
            String where = MrnWatnut.WATPHY_CODE + "=" + watphyRecs[i].getCode();
            watnutCnt[j] += new MrnWatnut().getRecCnt(where);
            watchlCnt[j] += new MrnWatchl().getRecCnt(where);
            watchem1Cnt[j] += new MrnWatchem1().getRecCnt(where);
            watchem2Cnt[j] += new MrnWatchem2().getRecCnt(where);
            watpol1Cnt[j] += new MrnWatpol1().getRecCnt(where);
            watpol2Cnt[j] += new MrnWatpol2().getRecCnt(where);

            //System.out.println(ec.frm(j,4) + " " + ec.frm(watphyRecs[i].getCode(),10) + " " +
            //    ec.frm(watnutCnt[j],4) + " " + ec.frm(watchlCnt[j],4) + " " +
            //    ec.frm(watchem1Cnt[j],4) + " " + ec.frm(watchem2Cnt[j],4) + " " +
            //    ec.frm(watpol1Cnt[j],4)  + " " + ec.frm(watpol2Cnt[j],4) );

            //subdesArray[i] = watphyRecs[0].getSubdes("");
            //watphyCounts[i] = watphyRecs.length;
            //lastDepths[i] = watphyRecs[watphyRecs.length - 1].getSpldep();


        } // for (int i = 0; i < watphyRecs.length; i++)

        aveDistances[j] = sum / (float)(count-1);
        watphyCounts[j] = count;
        lastDepths[j] = watphyRecs[watphyRecs.length-1].getSpldep();
        parms[j] = "";
        if (isTemperature) parms[j] += "t";
        if (isSalinity) parms[j] += "s";
        if (isOxygen) parms[j] += "o";

        if (isTemperature)  isCruiseTemperature = true;
        if (isSalinity)     isCruiseSalinity = true;
        if (isOxygen)       isCruiseOxygen = true;


        ec.writeFileLine(stationRepFile,
            ec.frm(station.getSurveyId(),10) +
            ec.frm(station.getStationId(),14) +
            ec.frm(-station.getLatitude(),10,5) +
            ec.frm(station.getLongitude(),10,5) + " " +
            ec.frm(station.getStnnam(""),12) +
            ec.frm(station.getDateStart("").substring(0,16),18));

        for (int i = 0; i <= j; i++) {
            ec.writeFileLine(stationRepFile, "     " +
                ec.frm(subdesArray[i],5) +
                ec.frm(watphyCounts[i],5) +
                ec.frm(lastDepths[i],10,2) + " " +
                ec.frm(parms[i],5) +
                ec.frm(watnutCnt[i],5) +
                ec.frm(watchlCnt[i],5) +
                ec.frm(watchem1Cnt[i],5) +
                ec.frm(watchem2Cnt[i],5) +
                ec.frm(watpol1Cnt[i],5) +
                ec.frm(watpol2Cnt[i],5) +
                " ");

                if (watnutCnt[i] > 0)   isWatnutData = true;
                if (watchlCnt[i] > 0)   isWatchlData = true;
                if (watchem1Cnt[i] > 0) isWatchem1Data = true;
                if (watchem1Cnt[i] > 0) isWatchem2Data = true;
                if (watpol1Cnt[i] > 0)  isWatpol1Data = true;
                if (watpol1Cnt[i] > 0)  isWatpol2Data = true;

        } // for (int i = 0; i < j; i++)

        //if (j > numSubdes) numSubdes = j;

    } // getWatphyStats

    /**
     * get the relavant WO Atlas records
     * @param latitude : station latitude
     * @param longitude : station longitude
     * @return the relevant WOAtlas records
     */
    MrnWoatlas5[] getWOAtlas5(float latitude, float longitude) {

        // get the records
        float difference = 2.5f;
        String fields = "*";
        String where = MrnWoatlas5.LATITUDE + " between " +
            (latitude - difference + .00001f) + " and " +
            (latitude + difference) + " and " +
            MrnWoatlas5.LONGITUDE + " between " +
            (longitude - difference + .00001f) + " and " +
            (longitude + difference);
        String order = MrnWoatlas5.DEPTH;

        MrnWoatlas5 atlas = new MrnWoatlas5();
        MrnWoatlas5 atlasArray[] = atlas.get(fields, where, order);


        System.out.println("selStr = " + atlas.getSelStr());
        System.out.println("atlasArray.length = " + atlasArray.length);
        System.out.println("station latitude = " + latitude + ", longitude = " + longitude);
        return atlasArray;

    } // getWOAtlas5


    /**
     * Test for overland data
     * @param station : array of station records
     */
    void testOverLand(MrnStation station) {

        System.out.println("\n" + new java.util.Date());

/*
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
            System.out.println("etopo");
            System.out.println("selStr = " + etop.getSelStr());
            System.out.println("etopo.length = " + etopo.length);
            System.out.println("height = " + height);
        } else {
            MrnEtopo2 etop = new MrnEtopo2();
            MrnEtopo2 etopo[] = etop.get(where);
            if (etopo.length > 0) {
                height = etopo[0].getHeight();
            } // if (etopo.length > 0)
            System.out.println("etopo2");
            System.out.println("selStr = " + etop.getSelStr());
            System.out.println("etopo.length = " + etopo.length);
            System.out.println("height = " + height);
        } // if ((station.getLatitude() <= -10) &&
*/

        // get the etopo depth (depth = positive, height = negative)
        float etopoDepth = sc.getEtopo2(station);
        if (etopoDepth <= 0) {
            String text =
                (etopoDepth == -999999 ? "  Outside of area in DB (etopo2)" : "  Over land");
            if (etopoDepth == -999999)
                ec.writeFileLine(overlandErrFile,
                    ec.frm(station.getStationId(),12) + " "+
                    ec.frm(-station.getLatitude(),10,5) + " " +
                    ec.frm(station.getLongitude(),10,5) + text);
            overlandErrorCnt += 1;
        } // if (height >= 0)

        System.out.println("\n" + new java.util.Date());

     } // testOverLand


    /**
     * Check whether station is > 10N
     * @param station : array of station records
     */
    void testNorth(MrnStation station) {
        if (station.getLatitude() < -10f) {
            ec.writeFileLine(northErrFile,
                ec.frm(station.getStationId(),12) + " "+
                ec.frm(-station.getLatitude(),10,5) + " " +
                ec.frm(station.getLongitude(),10,5));
            northErrorCnt += 1;
        } // if (station.getLatitude() < -10f)
    } // testNorth


// utility functions

    /**
     * Method of sort: Bubble Sort
     * Function get Stations array and sort the stations array in date order.
     * @param stations : array of station records
     * @return stations sorted by date array value
     */
    MrnStation[] bubbleSort(MrnStation[] stations) {
        for (int i=0; i < stations.length; i++) {
            if (dbg) System.out.println("<br>");
            for (int j=0; j < stations.length-i-1; j++) {

                if (dbg) System.out.println("<br>"+ j + " " +
                    stations[j].getDateStart()+" "+
                    stations[j+1].getDateStart());

                if (stations[j].getDateStart().after(stations[j+1].getDateStart())) {
                    if (dbg) System.out.println("<br>swop "+j);

                    String surveyID = stations[j].getSurveyId();
                    stations[j].setSurveyId(stations[j+1].getSurveyId());
                    stations[j+1].setSurveyId(surveyID);

                    String stationID = stations[j].getStationId();
                    stations[j].setStationId(stations[j+1].getStationId());
                    stations[j+1].setStationId(stationID);

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

                }// if (stations[j]
            } //for (int j= 0; j <
        }//for (int i= 0; i <

        return stations;
    } // bubbleSort


    /**
     * Function calculate the speed between two stations.
     * @param la1 : Latitude  of station one (double)
     * @param la2 : Latitude  of station two (double)
     * @param lo1 : Longitude of station one (double)
     * @param lo2 : Longitude of station two (double)
     * @param pt1 : Time value of point one (Timestamp)
     * @param pt2 : Time value of point two (Timestamp)
     * @return  speed (float) return speed(double) as float value (casting)
     */
    float calcSpeed(double la1, double la2, double lo1, double lo2,
        Timestamp pt1, Timestamp pt2) {

        if (dbg) System.out.println("<br>calcSpeed");
        if (dbg) System.out.println("<br>pt1 = "+pt1);
        java.util.Date date1 = pt1;
        if (dbg) System.out.println("<br>pt2 = "+pt2);
        java.util.Date date2 = pt2;

        long diff = pt2.getTime() - pt1.getTime();

        if (dbg) System.out.println("<br>difference before / 1000 / 60 / 60 = "+diff);
        //difference = difference / 1000.0 / 60.0 / 60.0;
        double difference = (double)diff / 3600000.0;
        //difference = difference / 3600000.0f;

        if (dbg) System.out.println("<br>difference = "+difference);
        double DEG2RAD = Math.PI / 180;
        double rLat1 = la1 * DEG2RAD;
        double rLat2 = la2 * DEG2RAD;
        double rLong1 = lo1 * DEG2RAD;
        double rLong2 = lo2 * DEG2RAD;

        //compute the angle
        double angle = rLong2 - rLong1;
        if (dbg) System.out.println("<br> Value of angle "+angle);

        // compute distance with formular
        // dr = acos { sin(lat1).sin(lat2)+cos(lat1).cos(lat2).cos(long2-long1)}
        double dr = Math.acos(Math.sin(rLat1) * Math.sin(rLat2) +
            Math.cos(rLat1) * Math.cos(rLat2) * Math.cos(angle));
        if (dbg) System.out.println("<br> Formula results "+dr);

        if (dr < 0 ) {
            dr += Math.PI;
            if (dbg) System.out.println("<br> ** Formula results if dr < 0 "+dr);

        } //if (dr < 0 ) {

        //double distance = StrictMath.toDegrees(dr * 6371.2);
        //double distance = dr * 6371.2;

        double distance = dr * 6371.2 / 1.852;
        double speed = distance / difference;

        // the speed may not exceed 20 knots ...
        if ( speed > MAX_SPEED) {
           speedErrorCnt++;
        } //if ( speed > MAX_SPEED) {

        return (float)speed;

    } // calcSpeed


    /**
     * do the interpolation
     * @param parm1 : first parameter value
     * @param parm2 : second parameter value
     * @param dep1  : first depth value
     * @param dep2  : second depth value
     * @param dep3  : third depth value
     * @return the interpolated value corresponding to dep3
     */
    float interpolate(float parm1, float parm2, float dep1, float dep2, float dep3) {
        float parm3 = parm2 - ((dep2 - dep3) * (parm2 - parm1) / (dep2 - dep1));
        //System.out.println(ec.frm(((parm2 - parm3)/(parm2 - parm1)),9,3) +
        //                   ec.frm(((dep2 - dep3)/(dep2 - dep1)),9,3));
        //System.out.println(ec.frm(parm1,9,3) + ec.frm(parm3,9,3) + ec.frm(parm2,9,3));
        //System.out.println(ec.frm(dep1,9,3) + ec.frm(dep3,9,3) + ec.frm(dep2,9,3));
        return parm3;
    } // interpolate


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("AdminCheckStations local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreAdminCheckStations local= new PreAdminCheckStations(args);
            bd.addItem(new AdminCheckStations(args));
            hp.printHeader();
            hp.print();
            common2.DbAccessC.close();
        } catch(Exception e) {
            ec.processErrorStatic(e, "AdminCheckStations", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class AdminCheckStations