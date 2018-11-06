package sadco;

import oracle.html.*;

/**
 * This class calculate's the speed in knots between two points.
 *
 * SadcoMRN
 * screen.equals("admin")
 * version.equals("delstn")
 *
 * It's parameters are the following:
 * <br>pscreen   - used by SadcoMRN program. Value must be 'admin'.
 * <br>pversion  - used by SadcoMRN program. Value must be 'delstn'.
 * <br>surveyId  - the ID of the survey that the station belongs to.
 * <br>stationId - the ID of the station to be deleted.
 *
 * @author 2002/02/13 - SIT Group.
 * @version
 * 2002/02/13 - Mario August       - created class<br>
 */
public class AdminDelStation extends CompoundItem {

    /** A boolean variable for debugging purposes */
        boolean dbg = false;
    //boolean dbg = true;

    /** A boolean variable for deleting & updating process */
    boolean invStatsExist = false;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters. */
    static common.edmCommon ec = new common.edmCommon();

    /** a class that contains all the constants used in the sadco application */
    sadco.SadConstants sc = new sadco.SadConstants();

    // arguments
    String surveyId    = "";
    String stationId   = "";
    String surveyId2   = "";

    /** The total number of watnut records for the relevant station. */
    int totWatnutCnt   = 0;
    /** The total number of watchem1 records for the relevant station. */
    int totWatchem1Cnt = 0;
    /** The total number of watchem2 records for the relevant station. */
    int totWatchem2Cnt = 0;
    /** The total number of watpol1 records for the relevant station. */
    int totWatpol1Cnt  = 0;
    /** The total number of watpol2 records for the relevant station. */
    int totWatpol2Cnt  = 0;
    /** The total number of watchl records for the relevant station. */
    int totWatchlCnt   = 0;
    /** The total number of watcurrents records for the relevant station. */
    int totWatcurrents = 0;

    /** The number of station records for the relevant station. */
    int stationCnt     = 0;
    /** The number of weather records for the relevant station. */
    int weatherCnt     = 0;

    /** The original number of watphy records for the relevant station. */
    int orgWatphyCnt   = 0;
    /** The original number of weather records for the relevant station. */
    int orgWeatherCnt  = 0;
    /** The original number of station records for the relevant station. */
    int orgStationCnt  = 0;
    /** The original number of watnut records for the relevant station. */
    int orgWatnutCnt   = 0;
    /** The original number of watpol1 records for the relevant station. */
    int orgWatpol1Cnt  = 0;
    /** The original number of watpol2 records for the relevant station. */
    int orgWatpol2Cnt  = 0;
    /** The original number of watchem1 records for the relevant station. */
    int orgWatchem1Cnt = 0;
    /** The original number of watchem2 records for the relevant station. */
    int orgWatchem2Cnt = 0;
    /** The original number of watchl records for the relevant station. */
    int orgWatchlCnt   = 0;
    /** The original number of watcurrents records for the relevant station. */
    int orgWatcurrentsCnt   = 0;

    /** the SQL error message if the deletion was unsuccessfull */
    String errorMessages;


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public AdminDelStation (String args[]) {

        try {
            AdminDelStationActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // AdminDelStation constructor

    /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void AdminDelStationActual(String args[]) {

        getArgParms(args);
        // test if arg are blank
        //if ("".equals(surveyId)) {
        //    surveyId = validateStation(stationId);
        //} else {
        //    stationCnt = 1;
        //}// if ("".equals(surveyId)) {

        // cal function to count records in tables watpol1, watpol2, watnut, watchem1,
        // watchem2, watcurrents & watchl for the same watphy code
        if (dbg) System.out.println(stationId+ " " +surveyId);
        String surveyId2 = validateStation(stationId);
        if (stationCnt <= 0) {
            this.addItem("<p>There is no station record "+
                "for the station Id = "+stationId + "<p>");
            return;
        } //if (stationCnt < 0) {


        if (dbg) System.out.println("URL survey_id "+surveyId+" database survey id "+
            surveyId2);

        if ("".equals(surveyId)) {
            if (dbg) System.out.println("Swop!");
            surveyId = surveyId2;

        } else if (!(surveyId2.equals( surveyId))) {
            this.addItem("Survey Id not found in Database!");
            return;

        } // close if


        MrnWatphy[] watphyRecs= getRecordsCount();
        weatherCnt = getWeatherRecords(stationId);

        // function getInvStatsRecs()
        if (dbg) System.out.println("before function call "+invStatsExist);
        MrnInvStats invStats = createNewInvStats(watphyRecs);
        if (dbg) System.out.println("after createNew... "+invStatsExist);

        if (invStatsExist) {
            updateInvStats(invStats);
            if (dbg) System.out.println("if boolean true... "+invStatsExist);

        } // if (invStatsExist) {

        //DynamicTable table = new DynamicTable(1);
        //table.setFrame(ITableFrame.BOX);
        //table.setBorder(0);

        this.addItem("\n<center><br>");
        this.addItem("<b><font color=blue><font size=+2>Delete a station"+
                    "</font></font></b>");

        int numRecsDeleted = deleteStation(stationId);
//        int numRecsDeleted = 0;


        // create table
        DynamicTable table = new DynamicTable(1);
        table.setFrame(ITableFrame.VOLD);
        table.setRules(ITableRules.NONE);
        //table.setFrame(ITableFrame.BOX);
        table.setBorder(0);

        table.addRow(ec.crSpanColsRow("&nbsp",2));
        table.addRow(ec.cr2ColRow("Station Id: ",stationId));
        table.addRow(ec.cr2ColRow("Survey Id: ",surveyId));
        table.addRow(ec.crSpanColsRow("&nbsp",2));
        table.addRow(ec.crSpanColsRow(
            "<b>No of records deleted for each table : </b>" ,2));
        table.addRow(ec.cr2ColRow("Station: ",stationCnt     +" of "+orgStationCnt));
        table.addRow(ec.cr2ColRow("Weather: ",weatherCnt     +" of "+orgWeatherCnt));
        table.addRow(ec.cr2ColRow("Watphy: ",watphyRecs.length +" of "+orgWatphyCnt));
        table.addRow(ec.cr2ColRow("Watcurrents: ",totWatcurrents +" of "+orgWatcurrentsCnt));
        table.addRow(ec.cr2ColRow("Watnut :",totWatnutCnt   +" of "+orgWatnutCnt));
        table.addRow(ec.cr2ColRow("Watpol1 :",totWatpol1Cnt  +" of "+orgWatpol1Cnt));
        table.addRow(ec.cr2ColRow("Watpol2 :",totWatpol2Cnt  +" of "+orgWatpol2Cnt));
        table.addRow(ec.cr2ColRow("Watchem1 :",totWatchem1Cnt +" of "+orgWatchem1Cnt));
        table.addRow(ec.cr2ColRow("Watchem2 :",totWatchem2Cnt +" of "+orgWatchem2Cnt));
        table.addRow(ec.cr2ColRow("Watchl2 :",totWatchlCnt   +" of "+orgWatchlCnt));

        String text = "successful";
        String color = "green";
        if (numRecsDeleted == 0) {
            text = "un" + text;
            color = "red";
        } //if (numRecsDel == 0)
        table.addRow(ec.crSpanColsRow("&nbsp",2));
        table.addRow(ec.crSpanColsRow("<b><font color=" + color + "><font size=+1>" +
            "Deletion was "+ text + ".<br><br></font></font></b>",2));

        this.addItem(table);

    } // AdminDelStationActual


    /**
     * Get the parameters from the arguments in the URL.
     * @param args
     * @return void
     */
   void getArgParms(String args[])   {
        for (int i = 0; i < args.length; i++) {
            if (dbg) System.out.println("<br>" + args[i]);
        } // for i

        surveyId = ec.getArgument(args, sc.SURVEYID);
        stationId = ec.getArgument(args, sc.STATIONID);

    } // getArgParms


   /**
    * Get all Watph records from Database for the stationId.
    * @param stationId : argument value from url (string)
    * @return the array value watphyRecs
    */
   MrnWatphy[] getWatphyRecords(String stationId) {

        if (dbg) System.out.println("Station id = "+stationId);
        String fields = MrnWatphy.CODE;
        String where =  MrnStation.STATION_ID +"='"+ stationId+"'";
        String order = "";
        MrnWatphy[] watphyRecs = new MrnWatphy().get(fields,where,order);
        return watphyRecs;

    } //MrnWatphy getWatphyRecords(String stationId) {


    /**
     * Get all Weather records from Database for the stationId.
     * @param stationId : argument value from url (string)
     * @return integer weatherRecs value
     */
    int getWeatherRecords(String stationId) {

        String where =  MrnWeather.STATION_ID +"='"+ stationId+"'";
        int weatherRecs = new MrnWeather().getRecCnt(where);
        if (dbg) System.out.println("Weather records count = "+ weatherRecs);
        return weatherRecs;

    } //MrnWeather[] getWeatherRecords(String stationId) {


    /**
     * Get surveyId from Database for the stationId.
     * @param stationId : argument value from url (string)
     * @return String surveyId
     */
    String validateStation(String stationId) {

        String fields = MrnStation.SURVEY_ID;
        String where = MrnStation.STATION_ID +"='"+stationId+"'";
        String order = "";

        MrnStation[] station = new MrnStation().get(fields,where,order);
        if (dbg) System.out.println("SurveyId = "+station[0].getSurveyId());

        // counter contains number of stations found

        stationCnt = station.length;
        String temp = "";

        if (stationCnt > 0) {
            temp = station[0].getSurveyId();
        } //temp

        return temp;

    } // MrnStation getStationId(String surveyId) {


    /**
     * Get Inv_Stats records from Database for the surveyId.
     * @param surveyId : argument value from url (string)
     * @return array value invStatsRecs
     */
    MrnInvStats[] getStatsRecords(String surveyId) {

        String where = MrnInvStats.SURVEY_ID + "='"+surveyId + "'";
        MrnInvStats[] invStatsRecs = new MrnInvStats().get(where);
        if (dbg) System.out.println("Debug ....must gou through here....");
        if (dbg) System.out.println("Debug ...."+invStatsRecs.length);
        return invStatsRecs;

    } // MrnInvStats[] getStatsRecords() {

    /**
     * The function Update the Inv_Stats table.
     * @param invStats
     * @return boolean value success
     */
    boolean updateInvStats(MrnInvStats invStats) {

        // Update the Inv Stats Table
        String where = MrnInvStats.SURVEY_ID +"='"+ surveyId+"'";
        boolean success = new MrnInvStats().upd(invStats,where);
        return success;

    } // updateInvStats

    /**
     * The function delete records from Station table.
     * @param stationId : argument value from url (String)
     * @return boolean value success
     */
    int deleteStation(String stationId) {

        String where = MrnStation.STATION_ID +"='"+ stationId+"'";
        MrnStation station = new MrnStation();
        boolean success = station.del(where);

        // get number of records deleted
        int numRecsDel = 0;
        if (success) {
            numRecsDel = station.getNumRecords();
            //this.addItem("<br>Deleting Inventory record where survey ID = "+surveyId);
        } else {
            errorMessages = station.getMessages();
        } //if (success) {

        return numRecsDel;

    } // deleteStation

    /**
     * This function count all records for the watphy table code.
     * @return boolean value success
     */
    MrnWatphy[] getRecordsCount() {

        MrnWatphy[] watphyRecs = getWatphyRecords(stationId);
        if (dbg) System.out.println(" length of watphy "+ watphyRecs.length);

        for ( int i = 0; i < watphyRecs.length; i++) {

            //watnut
            String where = MrnWatnut.WATPHY_CODE +"="+
                watphyRecs[i].getCode();
            int watnutCnt = new MrnWatnut().getRecCnt(where);
            if (dbg) System.out.println("Watnut record cnt = "+watnutCnt);

            //watchem1,
            where = MrnWatchem1.WATPHY_CODE +"="+
                watphyRecs[i].getCode();
            int watchem1Cnt = new MrnWatchem1().getRecCnt(where);
            if (dbg) System.out.println("Watchem1 record cnt = "+watchem1Cnt);

            //watchem2
            where = MrnWatchem2.WATPHY_CODE +"="+
                watphyRecs[i].getCode();
            int watchem2Cnt = new MrnWatchem2().getRecCnt(where);
            if (dbg) System.out.println("Watchem2 record cnt = "+watchem2Cnt);

            //watpol1
            where = MrnWatpol1.WATPHY_CODE +"="+
                watphyRecs[i].getCode();
            int watpol1Cnt = new MrnWatpol1().getRecCnt(where);
            if (dbg) System.out.println("Watpol1 record cnt = "+watpol1Cnt);

            //watpol2
            where = MrnWatpol2.WATPHY_CODE +"="+
                watphyRecs[i].getCode();
            int watpol2Cnt = new MrnWatpol2().getRecCnt(where);
            if (dbg) System.out.println("Watpol2 record cnt = "+watpol2Cnt);

            //watchl
            where = MrnWatchl.WATPHY_CODE +"="+
                watphyRecs[i].getCode();
            int watchlCnt = new MrnWatchl().getRecCnt(where);
            if (dbg) System.out.println("Watchl record cnt = "+watchlCnt);

            //watcurrents
            where = MrnWatcurrents.WATPHY_CODE +"="+
                watphyRecs[i].getCode();
            int watcurrentsCnt = new MrnWatcurrents().getRecCnt(where);
            if (dbg) System.out.println("Watcurrents record cnt = "+watcurrentsCnt);

            totWatnutCnt   += watnutCnt;
            totWatchem1Cnt += watchem1Cnt;
            totWatchem2Cnt += watchem2Cnt;
            totWatpol1Cnt  += watpol1Cnt;
            totWatpol2Cnt  += watpol2Cnt;
            totWatchlCnt   += watchlCnt;
            totWatcurrents += watcurrentsCnt;

            if (dbg) System.out.println("totWatnutCnt "+totWatnutCnt+" totWatchem1Cnt "+
                totWatchem1Cnt +"totWatchem2Cnt "+totWatchem2Cnt + " totWatpol1Cnt"+
                totWatpol1Cnt + " totWatpol2Cnt" +totWatpol2Cnt +" totWatchlCnt"+
                totWatchlCnt + " totWatcurrents"+totWatcurrents );

        } //for ( int i = 0; i < watphyRecs.length; i++) {

        return watphyRecs;

    } //MrnWatphy[] getRecordsCount() {


    /**
     * Gets the current inv_stats record, and updates the counts.
     * @param watphyRecs an array of all the watphy records for the relevant station
     * @return a new invstats object
     */
    MrnInvStats createNewInvStats(MrnWatphy[] watphyRecs) {


        MrnInvStats[] invStatsRecs = getStatsRecords(surveyId);
        MrnInvStats invStats = new MrnInvStats();

        if (dbg) System.out.println("before if "+invStatsExist);
        if (invStatsRecs.length > 0) {

            if (dbg) System.out.println("net na die if "+invStatsExist);
            invStats.setStationCnt(invStatsRecs[0].getStationCnt()-1);
            invStats.setWatphyCnt(invStatsRecs[0].getWatphyCnt()-watphyRecs.length);
            invStats.setWatnutCnt(invStatsRecs[0].getWatnutCnt()-totWatnutCnt);
            invStats.setWatpol1Cnt(invStatsRecs[0].getWatpol1Cnt()-totWatchem1Cnt);
            invStats.setWatpol2Cnt(invStatsRecs[0].getWatpol2Cnt()-totWatchem2Cnt);
            invStats.setWatchem1Cnt(invStatsRecs[0].getWatchem1Cnt()-totWatpol1Cnt);
            invStats.setWatchem2Cnt(invStatsRecs[0].getWatchem2Cnt()-totWatpol2Cnt);
            invStats.setWatchlCnt(invStatsRecs[0].getWatchlCnt()-totWatchlCnt);
            invStats.setWeatherCnt(invStatsRecs[0].getWeatherCnt()-weatherCnt);
            invStats.setWatcurrentsCnt(invStatsRecs[0].getWatcurrentsCnt()-totWatcurrents);
            if (dbg) System.out.println("before setting original values "+invStatsExist);
            orgWatphyCnt   = invStatsRecs[0].getWatphyCnt();
            orgStationCnt  = invStatsRecs[0].getStationCnt();
            orgWatnutCnt   = invStatsRecs[0].getWatnutCnt();
            orgWatpol1Cnt  = invStatsRecs[0].getWatpol1Cnt();
            orgWatpol2Cnt  = invStatsRecs[0].getWatpol2Cnt();
            orgWatchem1Cnt = invStatsRecs[0].getWatchem1Cnt();
            orgWatchem2Cnt = invStatsRecs[0].getWatchem2Cnt();
            orgWatchlCnt   = invStatsRecs[0].getWatchlCnt();
            orgWeatherCnt  = invStatsRecs[0].getWeatherCnt();
            orgWatcurrentsCnt = invStatsRecs[0].getWatcurrentsCnt();
            if (dbg) System.out.println("before setting flags "+invStatsExist);
            invStatsExist = true;
            if (dbg) System.out.println("after set flag "+invStatsExist);

        } else {
            orgWatphyCnt   = 0;
            orgStationCnt  = 0;
            orgWatnutCnt   = 0;
            orgWatpol1Cnt  = 0;
            orgWatpol2Cnt  = 0;
            orgWatchem1Cnt = 0;
            orgWatchem2Cnt = 0;
            orgWatchlCnt   = 0;
            orgWeatherCnt  = 0;
            orgWatcurrentsCnt = 0;
            if (dbg) System.out.println("function else "+invStatsExist);
        } // else-if

        if (dbg) System.out.println(" original values orgWatphyCnt "+orgWatphyCnt+
            " orgStationCnt "+orgStationCnt+" orgWatnutCnt "+ orgWatnutCnt+
            " orgWatpol1Cnt "+orgWatpol1Cnt+" orgWatpol2Cnt "+orgWatpol2Cnt+
            " orgWatchem1Cnt "+orgWatchem1Cnt+" orgWatchem2Cnt "+ orgWatchem2Cnt+
            " orgWatchlCnt "+ orgWatchlCnt +" orgWeatherCnt "+orgWeatherCnt+
            " orgWatcurrentsCnt " + orgWatcurrentsCnt);

        return invStats;

    } //MrnInvStats[] createNewInvStats(MrnInvStats[] invStatsRecs) {

} // class AdminDelStation

















