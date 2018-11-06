package sadco;

import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * This class counts all the records in the various tables, and updates <br>
 * the inv_stats table. It also updates the area and date ranges.       <br>
 *                                                                      <br>
 * It's parameters are the following:                                   <br>
 * survey ID                                                            <br>
 *                                                                      <br>
 * @author 2009/01/08 - SIT Group.                                      <br>
 * @version                                                             <br>
 * 2009/01/08 - Ursula von St Ange - created class                      <br>
 * 2013/02/20 - Ursula von St Ange - adapt for station level counts ub01<br>
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

    // *Count[0] == station count, and *Count[1] == record count
    final static int STN = 0;
    final static int REC = 1;
    final static int CUR = 0;
    final static int PLA = 1;
    final static int SED = 2;
    final static int TIS = 3;
    final static int WAT = 4;
    // currents
    int curntsCount[] = {0,0};
    // plankton
    int plaphyCount[] = {0,0};
    int plachlCount[] = {0,0};
    int plapesCount[] = {0,0};
    int plapolCount[] = {0,0};
    int plataxCount[] = {0,0};
    // sediments
    int sedphyCount[] = {0,0};
    int sedcheCount[] = {0,0};
    int sedpesCount[] = {0,0};
    int sedpolCount[] = {0,0};
    int sedtaxCount[] = {0,0};
    // tissue
    int tisaniCount[] = {0,0};
    int tisanpCount[] = {0,0};
    int tispesCount[] = {0,0};
    int tisphyCount[] = {0,0};
    int tispolCount[] = {0,0};
    // water physical
    int watphyCount[] = {0,0};
    int watcheCount[] = {0,0};
    int watchlCount[] = {0,0};
    int watnutCount[] = {0,0};
    int watpolCount[] = {0,0};
    int watctdCount[] = {0,0};
    int watxbtCount[] = {0,0};
    int watosdCount[] = {0,0};
    int watmbtCount[] = {0,0};
    int watpflCount[] = {0,0};

    // station counts
    int stnRecCounts[] = {0,0,0,0,0};                                   //ub01

    // work variables
    int cheCount = 0;
    int chlCount = 0;
    int nutCount = 0;
    int pesCount = 0;
    int polCount = 0;
    int taxCount = 0;

    int ctdCount = 0;
    int xbtCount = 0;
    int osdCount = 0;
    int mbtCount = 0;
    int pflCount = 0;

    // range variables
    float minLongitude = 180f;
    float maxLongitude = -180;
    float minLatitude = 90;
    float maxLatitude = -90;
    Timestamp minDate = Timestamp.valueOf("2099-12-31 23:59:59");
    Timestamp maxDate = Timestamp.valueOf("1900-10-01 00:00:00");

    /** main (and only) constructor */
    AdminUpdateInvStats(String args[]) {// throws Exception {

        System.err.println("Start: " +
            new SimpleDateFormat("yyyyMMdd_HHmmss").
            format(Calendar.getInstance().getTime()));

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

        int ii = 1;
        for (int i = 0; i < stationsCount; i++) {

            // re-initialise individual station record counts
            for (int init = 0; init < 5; init++) stnRecCounts[init] = 0; //ub01

            // show progress to the user
            if (Math.IEEEremainder((i+1),10f) == 0) {
                System.err.print(ii);
                ii++;
            } else System.err.print(".");

            if (Math.IEEEremainder((i+1),100f) == 0) {
                ii = 1;
                System.err.println("");
            } // if (Math.IEEEremainder((i+1),100f) == 0)

            //update min-max of ranges
            minLatitude = Math.min(minLatitude, stations[i].getLatitude());
            maxLatitude = Math.max(maxLatitude, stations[i].getLatitude());
            minLongitude = Math.min(minLongitude, stations[i].getLongitude());
            maxLongitude = Math.max(maxLongitude, stations[i].getLongitude());
            if (stations[i].getDateStart().before(minDate))
                minDate = stations[i].getDateStart();
            if (maxDate.before(stations[i].getDateStart()))
                maxDate = stations[i].getDateStart();

            // get all the counts
            String stationId = stations[i].getStationId();
            stnRecCounts[CUR] = getCurrentCounts(stationId);            //ub01
            stnRecCounts[PLA] = getPlanktonCounts(stationId);           //ub01
            stnRecCounts[SED] = getSedimentCounts(stationId);           //ub01
            stnRecCounts[TIS] = getTissueCounts(stationId);             //ub01
            stnRecCounts[WAT] = getWaterCounts(stationId);              //ub01
            //updateWeatherCount(stationId);

            // update the station counts                                //ub01
            updateStationCounts(stationId, stnRecCounts);               //ub01

        } // for (i = 0; i < stationsCount; i++)
        System.err.println();

        // ignore all non-hydro and 'empty' surveys
        if (stationsCount > 0) {

            // update the area and time ranges
            updateRanges(surveyId);

            // update the inventory stats table
            updateInvStats(surveyId);

        } // if (stationsCount > 0)

        System.err.println("AdminUpdateInvStats: " + surveyId + ": " + stationsCount);
        System.out.println("AdminUpdateInvStats: " + surveyId + ": " + stationsCount);

        System.err.println("  End: " +
            new SimpleDateFormat("yyyyMMdd_HHmmss").
            format(Calendar.getInstance().getTime()));

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
            MrnStation.SURVEY_ID + "," + MrnStation.LATITUDE + "," +
            MrnStation.LONGITUDE + "," + MrnStation.DATE_START;
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
    int getCurrentCounts(String stationId) {                          //ub01
        String where = MrnCurrents.STATION_ID +"='"+ stationId + "'";
        int recCnt = new MrnCurrents().getRecCnt(where);
        if (recCnt > 0) curntsCount[STN]++;
        curntsCount[REC] += recCnt;
        return recCnt;                                                  //ub01
    } // getCurrentCounts


    /**
     * Get the number of plankton records and the detail records
     * for the station
     * @param   station ID  the current station
     */
    int getPlanktonCounts(String stationId) {                        //ub01

        // construct variables and get records
        String where = MrnPlaphy.STATION_ID +"='"+ stationId + "'";
        String fields = MrnPlaphy.STATION_ID + "," + MrnPlaphy.CODE;
        String order = MrnPlaphy.CODE;
        MrnPlaphy[] plaphy = new MrnPlaphy().get(fields, where, order);
        if (plaphy.length > 0) plaphyCount[STN]++;
        plaphyCount[REC] += plaphy.length;

        chlCount = 0;
        pesCount = 0;
        polCount = 0;
        taxCount = 0;

        // get records counts for this station, if applicable
        for (int i = 0; i < plaphy.length; i++) {

            // get detail record counts
            where = MrnPlachl.PLAPHY_CODE +"="+ plaphy[i].getCode("");
            chlCount += new MrnPlachl().getRecCnt(where);
            pesCount += new MrnPlapes().getRecCnt(where);
            polCount += Math.max(new MrnPlapol1().getRecCnt(where),
                new MrnPlapol2().getRecCnt(where));
            taxCount += new MrnPlatax().getRecCnt(where);

        } // for (int i = 0; i < plaphy.length; i++)

        // update the stations counts
        if (chlCount > 0) plachlCount[STN]++;
        if (pesCount > 0) plapesCount[STN]++;
        if (polCount > 0) plapolCount[STN]++;
        if (taxCount > 0) plataxCount[STN]++;

        // update the record counts
        plachlCount[REC] += chlCount;
        plapesCount[REC] += pesCount;
        plapolCount[REC] += polCount;
        plataxCount[REC] += taxCount;

        return plaphy.length;                                           //ub01

    } // getPlanktonCounts


    /**
     * Get the number of sediment records and the detail records
     * for the station
     * @param   station ID  the current station
     */
    int getSedimentCounts(String stationId) {                        //ub01

        // construct variables and get records
        String where = MrnSedphy.STATION_ID +"='"+ stationId + "'";
        String fields = MrnSedphy.STATION_ID + "," + MrnSedphy.CODE;
        String order = MrnSedphy.CODE;
        MrnSedphy[] sedphy = new MrnSedphy().get(fields, where, order);
        if (sedphy.length > 0) sedphyCount[STN] ++;
        sedphyCount[REC] += sedphy.length;

        cheCount = 0;
        pesCount = 0;
        polCount = 0;
        taxCount = 0;

        // get records counts if applicable
        for (int i = 0; i < sedphy.length; i++) {

            // get detail record counts
            where = MrnSedchem1.SEDPHY_CODE +"="+ sedphy[i].getCode("");
            cheCount += Math.max(new MrnSedchem1().getRecCnt(where),
                new MrnSedchem2().getRecCnt(where));
            pesCount += new MrnSedpes().getRecCnt(where);
            polCount += Math.max(new MrnSedpol1().getRecCnt(where),
                new MrnSedpol2().getRecCnt(where));
            taxCount += new MrnSedtax().getRecCnt(where);

        } // for (int i = 0; i < sedphy.length; i++)

        // update the stations counts
        if (cheCount > 0) sedcheCount[STN]++;
        if (pesCount > 0) sedpesCount[STN]++;
        if (polCount > 0) sedpolCount[STN]++;
        if (taxCount > 0) sedtaxCount[STN]++;

        // update the record counts
        sedcheCount[REC] += cheCount;
        sedpesCount[REC] += pesCount;
        sedpolCount[REC] += polCount;
        sedtaxCount[REC] += taxCount;

        return sedphy.length;                                           //ub01

    } // getSedimentCounts


    /**
     * Get the number of tissue records and the detail records
     * for the station
     * @param   station ID  the current station
     */
    int getTissueCounts(String stationId) {                          //ub01

        // construct variables and get records
        String where = MrnTisphy.STATION_ID +"='"+ stationId + "'";
        String fields = MrnTisphy.STATION_ID + "," + MrnTisphy.CODE;
        String order = MrnTisphy.CODE;
        MrnTisphy[] tisphy = new MrnTisphy().get(fields, where, order);
        if (tisphy.length > 0) tisphyCount[STN]++;
        tisphyCount[REC] += tisphy.length;

        pesCount = 0;
        polCount = 0;
        int anpartCount = 0;

        // get counts if applicable
        for (int i = 0; i < tisphy.length; i++) {

            // get animal records
            where = MrnTisanimal.TISPHY_CODE +"="+ tisphy[i].getCode("");
            fields = MrnTisanimal.TISPHY_CODE + "," + MrnTisanimal.ANISEQ;
            order = MrnTisanimal.ANISEQ;
            MrnTisanimal[] animal = new MrnTisanimal().get(fields, where, order);
            if (animal.length > 0) tisaniCount[STN]++;
            tisaniCount[REC] += animal.length;

            // get counts if applicable
            for (int j = 0; j < animal.length; j++) {

                // get animal part records
                where = MrnTisanpart.ANISEQ +"="+ animal[j].getAniseq("");
                fields = MrnTisanpart.ANISEQ + "," + MrnTisanpart.PARSEQ;
                order = MrnTisanpart.PARSEQ;
                MrnTisanpart[] anpart = new MrnTisanpart().get(fields, where, order);
                tisanpCount[REC] += anpart.length;
                anpartCount++;

                // get the detail record counts
                for (int k = 0; k < anpart.length; k++) {

                    where = MrnTispes.PARSEQ +"="+ anpart[k].getParseq("");
                    pesCount += new MrnTispes().getRecCnt(where);
                    polCount += Math.max(new MrnTispol1().getRecCnt(where),
                        new MrnTispol2().getRecCnt(where));

                } // for (int k = 0; k < anpart.length; k++)
            } // for (int j = 0; j < animal.length; j++)
        } // for (int i = 0; i < tisphy.length; i++)

        // update the stations counts
        if (pesCount > 0) tispesCount[STN]++;
        if (polCount > 0) tispolCount[STN]++;
        if (anpartCount > 0) tisanpCount[STN]++;

        // update the record counts
        tispesCount[REC] += pesCount;
        tispolCount[REC] += polCount;

        return tisphy.length;                                           //ub01

    } // getTissueCounts


    /**
     * Get the number of water records and the detail records
     * for the station
     * @param   station ID  the current station
     */
    int getWaterCounts(String stationId) {                           //ub01

        // construct variables and get records
        String where = MrnWatphy.STATION_ID +"='"+ stationId + "'";
        String fields = MrnWatphy.STATION_ID + "," + MrnWatphy.CODE + "," +
            MrnWatphy.SUBDES;
        String order = MrnWatphy.CODE;
        MrnWatphy[] watphy = new MrnWatphy().get(fields, where, order);
        if (watphy.length > 0) watphyCount[STN]++;
        watphyCount[REC] += watphy.length;

        cheCount = 0;
        chlCount = 0;
        nutCount = 0;
        polCount = 0;

        ctdCount = 0;
        xbtCount = 0;
        osdCount = 0;
        mbtCount = 0;
        pflCount = 0;

        // get counts if applicable
        for (int i = 0; i < watphy.length; i++) {

            // get detail record counts
            where = MrnWatchl.WATPHY_CODE +"="+ watphy[i].getCode("");
            cheCount += Math.max(new MrnWatchem1().getRecCnt(where),
                new MrnWatchem2().getRecCnt(where));
            chlCount += new MrnWatchl().getRecCnt(where);
            nutCount += new MrnWatnut().getRecCnt(where);
            polCount += Math.max(new MrnWatpol1().getRecCnt(where),
                new MrnWatpol2().getRecCnt(where));

            if ("BOTTL".equals(watphy[i].getSubdes())) osdCount++;
            if ("DISCR".equals(watphy[i].getSubdes())) osdCount++;
            if ("NO PR".equals(watphy[i].getSubdes())) osdCount++;
            if ("OSD".equals(watphy[i].getSubdes())) osdCount++;
            if ("CTD".equals(watphy[i].getSubdes())) ctdCount++;
            if ("CONTI".equals(watphy[i].getSubdes())) ctdCount++;
            if ("DPFL".equals(watphy[i].getSubdes())) pflCount++;
            if ("PFL".equals(watphy[i].getSubdes())) pflCount++;
            if ("MBT".equals(watphy[i].getSubdes())) mbtCount++;
            if ("XBT".equals(watphy[i].getSubdes())) xbtCount++;

        } // for (int i = 0; i < watphy.length; i++)

        // update the stations counts
        if (cheCount > 0) watcheCount[STN]++;
        if (chlCount > 0) watchlCount[STN]++;
        if (nutCount > 0) watnutCount[STN]++;
        if (polCount > 0) watpolCount[STN]++;

        if (ctdCount > 0) watctdCount[STN]++;
        if (mbtCount > 0) watmbtCount[STN]++;
        if (osdCount > 0) watosdCount[STN]++;
        if (pflCount > 0) watpflCount[STN]++;
        if (xbtCount > 0) watxbtCount[STN]++;

        // update the record counts
        watcheCount[REC] += cheCount;
        watchlCount[REC] += chlCount;
        watnutCount[REC] += nutCount;
        watpolCount[REC] += polCount;

        watctdCount[REC] += ctdCount;
        watmbtCount[REC] += mbtCount;
        watosdCount[REC] += osdCount;
        watpflCount[REC] += pflCount;
        watxbtCount[REC] += xbtCount;

        return watphy.length;                                           //ub01

    } // getWaterCounts


    /**
     * Update the individual station records with the record type counts
     * @param   surveyId        ID of survey
     * @param   stationId       ID of station
     * @param   stationCounts   array of record counts for each type
     */
    void updateStationCounts(String stationId, int[] stationCounts){    //ub01

        // set new values
        MrnStationStats stnStats = new MrnStationStats();               //ub01
        stnStats.setCurrentsCnt  (stationCounts[CUR]);                  //ub01
        stnStats.setPlaphyCnt    (stationCounts[PLA]);                  //ub01
        stnStats.setSedphyCnt    (stationCounts[SED]);                  //ub01
        stnStats.setTisphyCnt    (stationCounts[TIS]);                  //ub01
        stnStats.setWatphyCnt    (stationCounts[WAT]);                  //ub01

        // check if stationStats exists                                 //ub01
        MrnStationStats stnStatsWhere = new MrnStationStats();          //ub01
        stnStatsWhere.setStationId(stationId);                          //ub01
        MrnStationStats stnStatsArray[] = stnStatsWhere.get();          //ub01

        if (stnStatsArray.length == 0) {                                //ub01
            stnStats.setStationId(stationId);                           //ub01
            stnStats.put();                                             //ub01
            if (dbg) System.out.println("stnStats inserted: " +         //ub01
                stnStats);                                              //ub01
            if (dbg) System.out.println("stnStats insString =  " +      //ub01
                stnStats.getInsStr());                                  //ub01
        } else {                                                        //ub01
            stnStatsWhere.upd(stnStats);                                //ub01
            if (dbg) System.out.println("stnStats updated: " +stnStats);//ub01
        } // if (stnStatsArray.length == 0)                             //ub01

    } // updateStationCounts                                            //ub01


    /**
     * Get the number of weather records for the station
     * @param   station ID  the current station
     */
/*
    void updateWeatherCount(String stationId) {
        String where = MrnWeather.STATION_ID +"='"+ stationId + "'";
        weatherCount += new MrnWeather().getRecCnt(where);
    } // updateWeatherCount
*/

    /**
     * Insert / Update the inv_stats record for the survey
     * @param   surveyId    ID of survey
     */
    void updateInvStats(String surveyId) {
        if (dbg) {
            String textLine =
                "stationsCount  = "+stationsCount                        +"\n"+
                "***** currents   "                                      +"\n"+
                "curntsCount    = "+curntsCount[STN]+" "+curntsCount[REC]+"\n"+
                "***** plankton   "                                      +"\n"+
                "plaphyCount    = "+plaphyCount[STN]+" "+plaphyCount[REC]+"\n"+
                "plachlCount    = "+plachlCount[STN]+" "+plachlCount[REC]+"\n"+
                "plapesCount    = "+plapesCount[STN]+" "+plapesCount[REC]+"\n"+
                "plapolCount    = "+plapolCount[STN]+" "+plapolCount[REC]+"\n"+
                "plataxCount    = "+plataxCount[STN]+" "+plataxCount[REC]+"\n"+
                "***** sediments  "                                      +"\n"+
                "sedphyCount    = "+sedphyCount[STN]+" "+sedphyCount[REC]+"\n"+
                "sedcheCount    = "+sedcheCount[STN]+" "+sedcheCount[REC]+"\n"+
                "sedpesCount    = "+sedpesCount[STN]+" "+sedpesCount[REC]+"\n"+
                "sedpolCount    = "+sedpolCount[STN]+" "+sedpolCount[REC]+"\n"+
                "sedtaxCount    = "+sedtaxCount[STN]+" "+sedtaxCount[REC]+"\n"+
                "***** tissue     "                                      +"\n"+
                "tisaniCount    = "+tisaniCount[STN]+" "+tisaniCount[REC]+"\n"+
                "tisanpCount    = "+tisanpCount[STN]+" "+tisanpCount[REC]+"\n"+
                "tispesCount    = "+tispesCount[STN]+" "+tispesCount[REC]+"\n"+
                "tisphyCount    = "+tisphyCount[STN]+" "+tisphyCount[REC]+"\n"+
                "tispolCount    = "+tispolCount[STN]+" "+tispolCount[REC]+"\n"+
                "***** water      "                                      +"\n"+
                "watphyCount    = "+watphyCount[STN]+" "+watphyCount[REC]+"\n"+
                "watcheCount    = "+watcheCount[STN]+" "+watcheCount[REC]+"\n"+
                "watchlCount    = "+watchlCount[STN]+" "+watchlCount[REC]+"\n"+
                "watnutCount    = "+watnutCount[STN]+" "+watnutCount[REC]+"\n"+
                "watpolCount    = "+watpolCount[STN]+" "+watpolCount[REC]+"\n"+
                "watctdCount    = "+watctdCount[STN]+" "+watctdCount[REC]+"\n"+
                "watmbtCount    = "+watmbtCount[STN]+" "+watmbtCount[REC]+"\n"+
                "watosdCount    = "+watosdCount[STN]+" "+watosdCount[REC]+"\n"+
                "watpflCount    = "+watpflCount[STN]+" "+watpflCount[REC]+"\n"+
                "watxbtCount    = "+watxbtCount[STN]+" "+watxbtCount[REC]+"\n";
            System.out.println(textLine);
        } // if (dbg)


        MrnInvStats invStats = new MrnInvStats();
        invStats.setStationCnt    (stationsCount   );

        invStats.setWatphyStnCnt     (watphyCount[STN]);
        invStats.setWatctdStnCnt     (watctdCount[STN]);
        invStats.setWatxbtStnCnt     (watxbtCount[STN]);
        invStats.setWatosdStnCnt     (watosdCount[STN]);
        invStats.setWatmbtStnCnt     (watmbtCount[STN]);
        invStats.setWatpflStnCnt     (watpflCount[STN]);
        invStats.setWatnutStnCnt     (watnutCount[STN]);
        invStats.setWatpolStnCnt     (watpolCount[STN]);
        invStats.setWatchemStnCnt    (watcheCount[STN]);
        invStats.setWatchlStnCnt     (watchlCount[STN]);
        invStats.setSedphyStnCnt     (sedphyCount[STN]);
        invStats.setSedpesStnCnt     (sedpesCount[STN]);
        invStats.setSedpolStnCnt     (sedpolCount[STN]);
        invStats.setSedchemStnCnt    (sedcheCount[STN]);
        invStats.setSedtaxStnCnt     (sedtaxCount[STN]);
        invStats.setPlaphyStnCnt     (plaphyCount[STN]);
        invStats.setPlapesStnCnt     (plapesCount[STN]);
        invStats.setPlapolStnCnt     (plapolCount[STN]);
        invStats.setPlataxStnCnt     (plataxCount[STN]);
        invStats.setPlachlStnCnt     (plachlCount[STN]);
        invStats.setTisphyStnCnt     (tisphyCount[STN]);
        invStats.setTispesStnCnt     (tispesCount[STN]);
        invStats.setTispolStnCnt     (tispolCount[STN]);
        invStats.setTisanimalStnCnt  (tisaniCount[STN]);
        invStats.setWatcurrentsStnCnt(curntsCount[STN]);

        invStats.setWatphyCnt     (watphyCount[REC]);
        invStats.setWatctdCnt     (watctdCount[REC]);
        invStats.setWatxbtCnt     (watxbtCount[REC]);
        invStats.setWatosdCnt     (watosdCount[REC]);
        invStats.setWatmbtCnt     (watmbtCount[REC]);
        invStats.setWatpflCnt     (watpflCount[REC]);
        invStats.setWatnutCnt     (watnutCount[REC]);
        invStats.setWatpol1Cnt    (watpolCount[REC]);
        invStats.setWatchem1Cnt   (watcheCount[REC]);
        invStats.setWatchlCnt     (watchlCount[REC]);
        invStats.setSedphyCnt     (sedphyCount[REC]);
        invStats.setSedpesCnt     (sedpesCount[REC]);
        invStats.setSedpol1Cnt    (sedpolCount[REC]);
        invStats.setSedchem1Cnt   (sedcheCount[REC]);
        invStats.setSedtaxCnt     (sedtaxCount[REC]);
        invStats.setPlaphyCnt     (plaphyCount[REC]);
        invStats.setPlapesCnt     (plapesCount[REC]);
        invStats.setPlapol1Cnt    (plapolCount[REC]);
        invStats.setPlataxCnt     (plataxCount[REC]);
        invStats.setPlachlCnt     (plachlCount[REC]);
        invStats.setTisphyCnt     (tisphyCount[REC]);
        invStats.setTispesCnt     (tispesCount[REC]);
        invStats.setTispol1Cnt    (tispolCount[REC]);
        invStats.setTisanimalCnt  (tisaniCount[REC]);
        invStats.setWatcurrentsCnt(curntsCount[REC]);

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
     * Update the inventory tabel for the area ranges
     * @param   surveyId    ID of survey
     */
    void updateRanges(String surveyId) {

        MrnInventory inv = new MrnInventory();
        inv.setLatNorth(minLatitude);
        inv.setLatSouth(maxLatitude);
        inv.setLongWest(minLongitude);
        inv.setLongEast(maxLongitude);
        inv.setDateStart(minDate);
        inv.setDateEnd(maxDate);

        if (dbg) {
            String textLine =
                "latNorth    = "+inv.getLatNorth("")+" "+minLatitude+"\n"+
                "latSouth    = "+inv.getLatSouth("")+" "+maxLatitude+"\n"+
                "longWest    = "+inv.getLongWest("")+" "+minLongitude+"\n"+
                "longEast    = "+inv.getLongEast("")+" "+maxLongitude+"\n"+
                "dateStart   = "+inv.getDateStart("")+" "+minDate+"\n"+
                "dateEnd     = "+inv.getDateEnd("")+" "+maxDate;
            System.out.println(textLine);
        } // if (dbg)

        MrnInventory invWhere = new MrnInventory();
        invWhere.setSurveyId(surveyId);
        invWhere.upd(inv);

    } // updateRanges


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
