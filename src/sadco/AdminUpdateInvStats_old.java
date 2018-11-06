package sadco;

/**
 * This class counts all the records in the various tables, and updates <br>
 * the inv_stats table.                                                 <br>
 *                                                                      <br>
 * It's parameters are the following:                                   <br>
 * survey ID                                                            <br>
 *                                                                      <br>
 * @author 2009/01/08 - SIT Group.                                      <br>
 * @version                                                             <br>
 * 2009/01/08 - Ursula von St Ange - created class                      <br>
 */
public class AdminUpdateInvStats {

    /** for debugging */
    boolean dbg = false;
    //boolean dbg = true;
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommonPC ec = new common.edmCommonPC();

    // record counts
    int stationsCount = 0;
    // currents
    int currentsCount = 0;
    // plankton
    int plaphyCount = 0;
    int plachlCount = 0;
    int plapesCount = 0;
    int plapol1Count = 0;
    int plapol2Count = 0;
    int plataxCount = 0;
    // sediments
    int sedphyCount = 0;
    int sedchem1Count = 0;
    int sedchem2Count = 0;
    int sedpesCount = 0;
    int sedpol1Count = 0;
    int sedpol2Count = 0;
    int sedtaxCount = 0;
    // tissue
    int tisanimalCount = 0;
    int tisanpartCount = 0;
    int tispesCount = 0;
    int tisphyCount = 0;
    int tispol1Count = 0;
    int tispol2Count = 0;
    // water physical
    int watphyCount = 0;
    int watchem1Count = 0;
    int watchem2Count = 0;
    int watchlCount = 0;
    int watnutCount = 0;
    int watpol1Count = 0;
    int watpol2Count = 0;
    //weather
    int weatherCount = 0;


    /** main (and only) constructor */
    AdminUpdateInvStats(String args[]) throws Exception {

        // Get input argument
        String surveyId = args[0];

        // just in case the argument was 'psurveyid=<surveyID>'
        int dotPos = surveyId.indexOf('=');
        if (dotPos > -1) {
            surveyId = surveyId.substring(dotPos+1);
        } // if (dotPos > -1)
        if (dbg) System.out.println("surveyId = " + surveyId);

        // get the stations
        MrnStation[] stations = getStationRecords(surveyId);
        stationsCount = stations.length;

        System.err.println(surveyId + ": " + stationsCount);
        System.out.println(surveyId + ": " + stationsCount);
        int ii = 1;
        for (int i = 0; i < stationsCount; i++) {

            // show progress to the user
            if (Math.IEEEremainder((i+1),10f) == 0) {
                System.err.print(ii);
                ii++;
            } else System.err.print(".");
            if (Math.IEEEremainder((i+1),100f) == 0) {
                ii = 1;
                System.err.println("");
            } // if (Math.IEEEremainder((i+1),100f) == 0)

            // get all the counts
            String stationId = stations[i].getStationId();
            updateCurrentCount(stationId);
            updatePlanktonCounts(stationId);
            updateSedimentCounts(stationId);
            updateTissueCounts(stationId);
            updateWaterCounts(stationId);
            updateWeatherCount(stationId);

        } // for (i = 0; i < stationsCount; i++)
        System.err.println();

        // update the inventory stats table
        updateInvStats(surveyId);

    } // AdminUpdateInvStats constructor


    /**
     * Get Station records from database for the surveyId
     * @param   surveyId    argument value from url (string)
     * @return array of station records
     */
    MrnStation[] getStationRecords(String surveyId) {
        //if (dbg) System.out.println("<br>getStationRecords");

        // construct fields, and where and order by clauses
        String fields = MrnStation.STATION_ID + "," +
            MrnStation.SURVEY_ID;
        String where = MrnStation.SURVEY_ID +"='"+ surveyId +"'";
        String order = MrnStation.STATION_ID;

        MrnStation station = new MrnStation();
        MrnStation[] stations = station.get(fields, where, order);
        if (dbg) System.out.println("station.selStr = " + station.getSelStr());

        return stations;
    } // getStationRecords


    /**
     * Get the number of currents records for the station
     * @param   station ID  the current station
     */
    void updateCurrentCount(String stationId) {
        String where = MrnCurrents.STATION_ID +"='"+ stationId + "'";
        currentsCount += new MrnCurrents().getRecCnt(where);
    } // updateCurrentCount


    /**
     * Get the number of plankton records and the detail records
     * for the station
     * @param   station ID  the current station
     */
    void updatePlanktonCounts(String stationId) {

        // construct variables and get records
        String where = MrnPlaphy.STATION_ID +"='"+ stationId + "'";
        String fields = MrnPlaphy.STATION_ID + "," + MrnPlaphy.CODE;
        String order = MrnPlaphy.CODE;
        MrnPlaphy[] plaphy = new MrnPlaphy().get(fields, where, order);
        plaphyCount += plaphy.length;

        // get counts if applicable
        for (int i = 0; i < plaphy.length; i++) {

            // get detail record counts
            where = MrnPlachl.PLAPHY_CODE +"="+ plaphy[i].getCode("");
            plachlCount += new MrnPlachl().getRecCnt(where);
            plapesCount += new MrnPlapes().getRecCnt(where);
            plapol1Count += new MrnPlapol1().getRecCnt(where);
            plapol2Count += new MrnPlapol2().getRecCnt(where);
            plataxCount += new MrnPlatax().getRecCnt(where);

        } // for (int i = 0; i < plaphy.length; i++)

    } // updatePlanktonCounts


    /**
     * Get the number of sediment records and the detail records
     * for the station
     * @param   station ID  the current station
     */
    void updateSedimentCounts(String stationId) {

        // construct variables and get records
        String where = MrnSedphy.STATION_ID +"='"+ stationId + "'";
        String fields = MrnSedphy.STATION_ID + "," + MrnSedphy.CODE;
        String order = MrnSedphy.CODE;
        MrnSedphy[] sedphy = new MrnSedphy().get(fields, where, order);
        sedphyCount += sedphy.length;

        // get counts if applicable
        for (int i = 0; i < sedphy.length; i++) {

            // get detail record counts
            where = MrnSedchem1.SEDPHY_CODE +"="+ sedphy[i].getCode("");
            sedchem1Count += new MrnSedchem1().getRecCnt(where);
            sedchem2Count += new MrnSedchem2().getRecCnt(where);
            sedpesCount += new MrnSedpes().getRecCnt(where);
            sedpol1Count += new MrnSedpol1().getRecCnt(where);
            sedpol2Count += new MrnSedpol2().getRecCnt(where);
            sedtaxCount += new MrnSedtax().getRecCnt(where);

        } // for (int i = 0; i < sedphy.length; i++)

    } // updateSedimentCounts


    /**
     * Get the number of tissue records and the detail records
     * for the station
     * @param   station ID  the current station
     */
    void updateTissueCounts(String stationId) {

        // construct variables and get records
        String where = MrnTisphy.STATION_ID +"='"+ stationId + "'";
        String fields = MrnTisphy.STATION_ID + "," + MrnTisphy.CODE;
        String order = MrnTisphy.CODE;
        MrnTisphy[] tisphy = new MrnTisphy().get(fields, where, order);
        tisphyCount += tisphy.length;

        // get counts if applicable
        for (int i = 0; i < tisphy.length; i++) {

            // get animal records
            where = MrnTisanimal.TISPHY_CODE +"="+ tisphy[i].getCode("");
            fields = MrnTisanimal.TISPHY_CODE + "," + MrnTisanimal.ANISEQ;
            order = MrnTisanimal.ANISEQ;
            MrnTisanimal[] animal = new MrnTisanimal().get(fields, where, order);
            tisanimalCount += animal.length;

            // get counts if applicable
            for (int j = 0; j < animal.length; j++) {

                // get animal part records
                where = MrnTisanpart.ANISEQ +"="+ animal[j].getAniseq("");
                fields = MrnTisanpart.ANISEQ + "," + MrnTisanpart.PARSEQ;
                order = MrnTisanpart.PARSEQ;
                MrnTisanpart[] anpart = new MrnTisanpart().get(fields, where, order);
                tisanpartCount += anpart.length;

                // get the detail record counts
                for (int k = 0; k < anpart.length; k++) {

                    where = MrnTispes.PARSEQ +"="+ anpart[k].getParseq("");
                    tispesCount += new MrnTispes().getRecCnt(where);
                    tispol1Count += new MrnTispol1().getRecCnt(where);
                    tispol2Count += new MrnTispol2().getRecCnt(where);

                } // for (int k = 0; k < anpart.length; k++)
            } // for (int j = 0; j < animal.length; j++)
        } // for (int i = 0; i < tisphy.length; i++)
    } // updateTissueCounts


    /**
     * Get the number of water records and the detail records
     * for the station
     * @param   station ID  the current station
     */
    void updateWaterCounts(String stationId) {

        // construct variables and get records
        String where = MrnWatphy.STATION_ID +"='"+ stationId + "'";
        String fields = MrnWatphy.STATION_ID + "," + MrnWatphy.CODE;
        String order = MrnWatphy.CODE;
        MrnWatphy[] watphy = new MrnWatphy().get(fields, where, order);
        watphyCount += watphy.length;

        // get counts if applicable
        for (int i = 0; i < watphy.length; i++) {

            // get detail record counts
            where = MrnWatchl.WATPHY_CODE +"="+ watphy[i].getCode("");
            watchem1Count += new MrnWatchem1().getRecCnt(where);
            watchem2Count += new MrnWatchem2().getRecCnt(where);
            watchlCount += new MrnWatchl().getRecCnt(where);
            watnutCount += new MrnWatnut().getRecCnt(where);
            watpol1Count += new MrnWatpol1().getRecCnt(where);
            watpol2Count += new MrnWatpol2().getRecCnt(where);

        } // for (int i = 0; i < watphy.length; i++)

    } // updateWaterCounts


    /**
     * Get the number of weather records for the station
     * @param   station ID  the current station
     */
    void updateWeatherCount(String stationId) {
        String where = MrnWeather.STATION_ID +"='"+ stationId + "'";
        weatherCount += new MrnWeather().getRecCnt(where);
    } // updateWeatherCount


    /**
     * Insert / Update the inv_stats record for the survey
     * @param   surveyId    ID of survey
     */
    void updateInvStats(String surveyId) {
        if (dbg) {
            String textLine =
                "stationsCount  = " + stationsCount  + "\n" +
                "***** currents   "                  + "\n" +
                "currentsCount  = " + currentsCount  + "\n" +
                "plankton         "                  + "\n" +
                "plaphyCount    = " + plaphyCount    + "\n" +
                "plachlCount    = " + plachlCount    + "\n" +
                "plapesCount    = " + plapesCount    + "\n" +
                "plapol1Count   = " + plapol1Count   + "\n" +
                "plapol2Count   = " + plapol2Count   + "\n" +
                "plataxCount    = " + plataxCount    + "\n" +
                "***** sediments  "                  + "\n" +
                "sedphyCount    = " + sedphyCount    + "\n" +
                "sedchem1Count  = " + sedchem1Count  + "\n" +
                "sedchem2Count  = " + sedchem2Count  + "\n" +
                "sedpesCount    = " + sedpesCount    + "\n" +
                "sedpol1Count   = " + sedpol1Count   + "\n" +
                "sedpol2Count   = " + sedpol2Count   + "\n" +
                "sedtaxCount    = " + sedtaxCount    + "\n" +
                "***** tissue     "                  + "\n" +
                "tisanimalCount = " + tisanimalCount + "\n" +
                "tisanpartCount = " + tisanpartCount + "\n" +
                "tispesCount    = " + tispesCount    + "\n" +
                "tisphyCount    = " + tisphyCount    + "\n" +
                "tispol1Count   = " + tispol1Count   + "\n" +
                "tispol2Count   = " + tispol2Count   + "\n" +
                "***** water      "                  + "\n" +
                "watphyCount    = " + watphyCount    + "\n" +
                "watchem1Count  = " + watchem1Count  + "\n" +
                "watchem2Count  = " + watchem2Count  + "\n" +
                "watchlCount    = " + watchlCount    + "\n" +
                "watnutCount    = " + watnutCount    + "\n" +
                "watpol1Count   = " + watpol1Count   + "\n" +
                "watpol2Count   = " + watpol2Count   + "\n" +
                "***** weather    "                  + "\n" +
                "weatherCount   = " + weatherCount;
            System.out.println(textLine);
        } // if (dbg)

        MrnInvStats invStats = new MrnInvStats();
        invStats.setStationCnt     (stationsCount );
        invStats.setWatphyCnt      (watphyCount   );
        invStats.setWatnutCnt      (watnutCount   );
        invStats.setWatpol1Cnt     (watpol1Count  );
        invStats.setWatpol2Cnt     (watpol2Count  );
        invStats.setWatchem1Cnt    (watchem1Count );
        invStats.setWatchem2Cnt    (watchem2Count );
        invStats.setWatchlCnt      (watchlCount   );
        invStats.setSedphyCnt      (sedphyCount   );
        invStats.setSedpesCnt      (sedpesCount   );
        invStats.setSedpol1Cnt     (sedpol1Count  );
        invStats.setSedpol2Cnt     (sedpol2Count  );
        invStats.setSedchem1Cnt    (sedchem1Count );
        invStats.setSedchem2Cnt    (sedchem2Count );
        invStats.setSedtaxCnt      (sedtaxCount   );
        invStats.setPlaphyCnt      (plaphyCount   );
        invStats.setPlapesCnt      (plapesCount   );
        invStats.setPlapol1Cnt     (plapol1Count  );
        invStats.setPlapol2Cnt     (plapol2Count  );
        invStats.setPlataxCnt      (plataxCount   );
        invStats.setPlachlCnt      (plachlCount   );
        invStats.setTisphyCnt      (tisphyCount   );
        invStats.setTispesCnt      (tispesCount   );
        invStats.setTispol1Cnt     (tispol1Count  );
        invStats.setTispol2Cnt     (tispol2Count  );
        invStats.setTisanimalCnt   (tisanimalCount);
        invStats.setWeatherCnt     (weatherCount  );
        invStats.setWatcurrentsCnt (currentsCount );

        // check if invStats exists
        MrnInvStats invStatsWhere = new MrnInvStats();
        invStatsWhere.setSurveyId(surveyId);
        MrnInvStats invStatsArray[] = invStatsWhere.get();

        if (invStatsArray.length == 0) {
            invStats.setSurveyId(surveyId);
            invStats.put();
            if (dbg) System.out.println("invStats inserted: " +invStats);
            if (dbg) System.out.println("invStats insString =  " +invStats.getInsStr());
        } else {
            invStatsWhere.upd(invStats);
            if (dbg) System.out.println("invStats updated: " +invStats);
        } // if (invStatsArray.length == 0)

    } // updateInvStats


    /**
     * The main procedure for processing in standalone mode
     * @param   args[]  array of command-line / URL arguments
     */
    public static void main(String args[]) {
        try {
            AdminUpdateInvStats local = new AdminUpdateInvStats(args);
        } catch (Exception e) {
            ec.processError(e, "AdminUpdateInvStats", "constructor", "");
        } // try-catch
        common2.DbAccessC.close();
    } // main

} // AdminUpdateInvStats
