package sadco;

import java.io.*;

/**
 * This class generates the load reports for water physical and nutrients,
 * and water chemistry.
 *
 * It's parameters are the following:
 *
 * SadcoMRN
 * screen.equals("load")
 * version.equals("reports")
 *
 * It's parameters are the following:                                   <br>
 * pscreen   - used by SadcoMRN program. Value must be 'load'.          <br>
 * pversion  - used by SadcoMRN program. Value must be 'reports'.       <br>
 * surveyId  - argument value from url (string)                         <br>
 *
 * @author 2004/02/11 - SIT Group.
 * @version
 * 2008/09/01 - Ursula von St Ange - change to add flags               <br>
 * 2015/09/02 - Ursula von St Ange - change header, add pressure ub01  <br>
 */
public class LoadMRNReports {

    /** A boolean variable for debugging purposes */
    boolean dbg = false;
    //boolean dbg = true;
    boolean dbg2 = false;
    //boolean dbg2 = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommonPC ec = new common.edmCommonPC();
    sadco.SadConstants sc = new sadco.SadConstants();

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoadMRNReports(String args[]) {

        try {
            LoadMRNReportsActual(args);
        } catch(Exception e) {
            ec.processError(e, thisClass, "constructor", "");
        } // try - catch

    } // LoadMRNReports constructor

    /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadMRNReportsActual(String args[]) {

        // get the argument
        String surveyId = ec.getArgument(args, sc.SURVEYID);
        if (dbg2) surveyId = "2013/0039";

        // get the corect path
        String rootPath = "";
        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIRL;
        } else {
            rootPath = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(ec.HOST))

        String fileName = rootPath + "marine/" + surveyId.replace('/','_');
        String physnutFileName = fileName + ".physnut.loadrep";
        String watchm1FileName = fileName + ".watchm1.loadrep";

        // delete the old the dummy file
        String dummyFile = fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        // create the 'processing' file
        String dummyFile2 = fileName + ".processing";
        ec.createNewFile(dummyFile2);

        // open the report files
        RandomAccessFile physnutReport = openOutputFile(physnutFileName);
        RandomAccessFile watchm1Report = openOutputFile(watchm1FileName);

        // get the inventory and survey info
        MrnInventory[] inv = new MrnInventory(surveyId).get();
        MrnSurvey[] survey = new MrnSurvey(surveyId).get();
        if (dbg) System.out.println("LoadMRNReportsActual: inv.length = " + inv.length);
        if (dbg) System.out.println("LoadMRNReportsActual: survey.length = " + survey.length);

        // only continue if there is a survey record
        if (survey.length > 0) {

            // get the station records
            MrnStation[] stations = getStationRecords(surveyId);
            if (dbg) System.out.println("LoadMRNReportsActual: stations.length = " + stations.length);

            // write the survey info
            //outputPhysnutSurveyInfo(physnutReport, inv[0], survey[0]);        //ub01
            outputSurveyInfo(physnutReport, true, inv[0], survey[0], stations.length);  //ub01

            // check var for watchm1 data
            boolean firstStation = true;

            // for printing watphy & nutrients & current records
            MrnWatphy watphyNull = null;
            MrnCurrents currentsNull = null;
            MrnWatnut watnutNull[] = new MrnWatnut[0];
            MrnWatqc watqcNull[] = new MrnWatqc[0];
            MrnWatprofqc profqcNull[] = new MrnWatprofqc[0];

            // process each station
            for (int i = 0; i < stations.length; i++) {
//            for (int i = 0; i < 2; i++) {

                // get all the station detail records to make sure we get a station time
                if (dbg) System.out.println("LoadMRNReportsActual: stations[i].getStationId() = " + stations[i].getStationId());
                MrnWatphy[] watphy = getWatphyRecords(stations[i].getStationId());
                if (dbg) System.out.println("LoadMRNReportsActual: watphy.length = " + watphy.length);
                MrnCurrents[] currents = getCurrentsRecords(stations[i].getStationId());
                if (dbg) System.out.println("LoadMRNReportsActual: currents.length = " + currents.length);
                MrnSedphy[] sedphy = getSedphyRecords(stations[i].getStationId());
                if (dbg) System.out.println("LoadMRNReportsActual: sedphy.length = " + sedphy.length);
                MrnPlaphy[] plaphy = getPlaphyRecords(stations[i].getStationId());
                if (dbg) System.out.println("LoadMRNReportsActual: plaphy.length = " + plaphy.length);
                MrnTisphy[] tisphy = getTisphyRecords(stations[i].getStationId());
                if (dbg) System.out.println("LoadMRNReportsActual: tisphy.length = " + tisphy.length);
                MrnWatprofqc[] profqc = getWatprofqcRecords(stations[i].getStationId());
                if (dbg) System.out.println("LoadMRNReportsActual: profqc.length = " + profqc.length);

                // get the station time
                String spldattim = "00:00:00";
                if (watphy.length > 0) {
                    if (dbg) System.out.println("LoadMRNReportsActual: " +
                        "watphy[0].getSpldattim().length = " +
                        watphy[0].getSpldattim("").length());
                    if (dbg) System.out.println("LoadMRNReportsActual: " +
                        "watphy[0].getSpldattim() = " + watphy[0].getSpldattim(""));
                    spldattim = watphy[0].getSpldattim("").substring(11,19);
                } else if (currents.length > 0) {
                    spldattim = currents[0].getSpldattim("").substring(11,19);
                } else if (sedphy.length > 0) {
                    spldattim = sedphy[0].getSpldattim("").substring(11,19);
                } else if (plaphy.length > 0) {
                    spldattim = plaphy[0].getSpldattim("").substring(11,19);
                } else if (tisphy.length > 0) {
                    spldattim = tisphy[0].getSpldattim("").substring(11,19);
                } // if (watphy.length > 0)

                // write the station info
                outputStationInfo(physnutReport, stations[i], spldattim);

                // get the weather record
                MrnWeather[] weather = new MrnWeather(stations[i].getStationId()).get();

                // write the weather info
                outputWeatherInfo(physnutReport, weather);

                // write the data headers
                //if (watphy.length == 0) profqc = profqcNull;
                outputPhysnutDataHeader(physnutReport, profqc);

                // some working vars for watchm1 report
                boolean firstRecord = true;

                //System.out.println(stations[i].getStationId());
                int curI = 0;
                for (int j = 0; j < watphy.length; j++) {

                    // get the watnut info
                    MrnWatnut[] watnut = new MrnWatnut(watphy[j].getCode()).get();
                    if (dbg) System.out.println("LoadMRNReportsActual: watnut.length = " + watnut.length);

                    // get the qc info
                    MrnWatqc[] watqc = new MrnWatqc(watphy[j].getCode()).get();
                    if (dbg) System.out.println("LoadMRNReportsActual: watqc.length = " + watqc.length);

                    // write the watphy info
                    if (currents.length == 0) {
                        // no currents info, so just write watphy
                        outputPhysnutInfo(physnutReport, watphy[j], currentsNull, watnut, watqc);
                    } else {
                        for (int ci = curI; ci < currents.length; ci++) {
                            // check for any shallower currents
                            if (currents[ci].getSpldep() < watphy[j].getSpldep()) {
                                if (dbg) System.out.println("c < w: " + ci + " " + j + " " + currents[ci].getSpldep() +  " " + watphy[j].getSpldep());
                                outputPhysnutInfo(physnutReport, watphyNull, currents[ci], watnutNull, watqcNull);
                            // currents and watphy at same level
                            } else if (currents[ci].getSpldep() == watphy[j].getSpldep()) {
                                if (dbg) System.out.println("c = w: " + ci + " " + j + " " + currents[ci].getSpldep() +  " " + watphy[j].getSpldep());
                                outputPhysnutInfo(physnutReport, watphy[j], currents[ci], watnut, watqc);
                                curI = ci+1;
                                break;
                            // currents are deeper,so write watphy
                            } else if (currents[ci].getSpldep() > watphy[j].getSpldep()) {
                                if (dbg) System.out.println("c > w: " + ci + " " + j + " " + currents[ci].getSpldep() +  " " + watphy[j].getSpldep());
                                outputPhysnutInfo(physnutReport, watphy[j], currentsNull, watnut, watqc);
                                curI = ci;
                                break;
                            } // if (currents.getSpldep() < watphy[j].getSplDep())
                        } // for (int ci = curI; ci < currents.length; ci++)
                    } // if (currents.length == 0)


                    // get the watchem1, watchl info
                    MrnWatchem1[] watchem1 = new MrnWatchem1(watphy[j].getCode()).get();
                    if (dbg) System.out.println("LoadMRNReportsActual: watchem1.length = " + watchem1.length);
                    MrnWatchl[] watchl = new MrnWatchl(watphy[j].getCode()).get();
                    if (dbg) System.out.println("LoadMRNReportsActual: watchl.length = " + watchl.length);

                    // only write to report if there is data
                    if ((watchem1.length > 0) || (watchl.length > 0)) {

                        if (dbg) System.out.println("LoadMRNReportsActual: watchem1.length = " + watchem1.length);
                        if (dbg) System.out.println("LoadMRNReportsActual: watchl.length = " + watchl.length);
                        // write the survey and station info
                        if (firstStation) {
                            //outputWatchm1SurveyInfo(watchm1Report, inv[0], survey[0]);    //ub01
                            outputSurveyInfo(watchm1Report, false, inv[0], survey[0], stations.length); //ub01
                            firstStation = false;
                        } // if (firstStation)

                        // write the data header
                        if (firstRecord) {
                            outputStationInfo(watchm1Report, stations[i], spldattim);
                            outputWatchm1DataHeader(watchm1Report, profqc);
                            firstRecord = false;
                        } // if (firstRecord)

                        // write the watchem1, watchl info
                        outputWatchm1Info(watchm1Report, watphy[j], watchem1, watchl, watqc);

                    } // if ((watchem1.length > 0) || (watchl.length > 0))

                } // for (int j = 0; j < watphy.length; j++)

                // write any remaining current records
                for (int ci = curI; ci < currents.length; ci++) {
                        outputCurrentInfo(physnutReport, currents[ci]);
                } // for (int ci = curI; ci < currents.length; ci++)

                // write error message
                if ((watphy.length == 0) && (currents.length == 0)) {
                    ec.writeFileLine(physnutReport,
                        "    No physical, nutrient or current data available");
                } // if (watphy.length == 0)

            } // for (int i = 0; i < stations.length; i++)
        } // if (survey.length > 0)

        try {
            physnutReport.close();
            watchm1Report.close();
        } catch(Exception e) { } //

        // delete .processing, create the .finished
        ec.deleteOldFile(dummyFile2);
        ec.createNewFile(dummyFile);

    } // LoadMRNReportsActual


    /**
     * Get Station records from Database for the @param surveyId
     * @param surveyId : argument value from url (string)
     * @return array of station records
     */
    MrnStation[] getStationRecords(String surveyId) {

        if (dbg) System.out.println("<br>getStationRecords ...");

        String where = MrnStation.SURVEY_ID +"='"+ surveyId +"'";
        String order = MrnStation.STATION_ID;
        MrnStation[] stations = new MrnStation().get(where, order);

        return stations;
    } // getStationRecords


    /**
     * Get watphy records from Database for the @param station-id
     * @param stationId : argument value from url (string)
     * @return array of watphy records
     */
    MrnWatphy[] getWatphyRecords(String stationId) {

        if (dbg) System.out.println("<br>getWatphyRecords ...");

        String where = MrnWatphy.STATION_ID +"='"+ stationId +"'";
        String order = MrnWatphy.SPLDEP;
        MrnWatphy[] watphy = new MrnWatphy().get(where, order);

        return watphy;
    } //getWatphyRecords


    /**
     * Get sedphy records from Database for the @param station-id
     * @param stationId : argument value from url (string)
     * @return array of sedphy records
     */
    MrnSedphy[] getSedphyRecords(String stationId) {

        if (dbg) System.out.println("<br>getSedphyRecords ...");

        String where = MrnSedphy.STATION_ID +"='"+ stationId +"'";
        String order = MrnSedphy.SPLDEP;
        MrnSedphy[] sedphy = new MrnSedphy().get(where, order);

        return sedphy;
    } //getSedphyRecords


    /**
     * Get plaphy records from Database for the @param station-id
     * @param stationId : argument value from url (string)
     * @return array of plaphy records
     */
    MrnPlaphy[] getPlaphyRecords(String stationId) {

        if (dbg) System.out.println("<br>getPlaphyRecords ...");

        String where = MrnPlaphy.STATION_ID +"='"+ stationId +"'";
        String order = MrnPlaphy.SPLDEP;
        MrnPlaphy[] plaphy = new MrnPlaphy().get(where, order);

        return plaphy;
    } //getPlaphyRecords


    /**
     * Get tisphy records from Database for the @param station-id
     * @param stationId : argument value from url (string)
     * @return array of tisphy records
     */
    MrnTisphy[] getTisphyRecords(String stationId) {

        if (dbg) System.out.println("<br>getTisphyRecords ...");

        String where = MrnTisphy.STATION_ID +"='"+ stationId +"'";
        String order = MrnTisphy.SPLDEP;
        MrnTisphy[] tisphy = new MrnTisphy().get(where, order);

        return tisphy;
    } //getTisphyRecords


    /**
     * Get currents records from Database for the @param station-id
     * @param stationId : argument value from url (string)
     * @return array of currents records
     */
    MrnCurrents[] getCurrentsRecords(String stationId) {

        if (dbg) System.out.println("<br>getCurrentsRecords ...");

        String where = MrnCurrents.STATION_ID +"='"+ stationId +"'";
        String order = MrnCurrents.SPLDEP;
        MrnCurrents[] currents = new MrnCurrents().get(where, order);

        return currents;
    } //getCurrentsRecords


    /**
     * Get profqc records from Database for the @param station-id
     * @param stationId : argument value from url (string)
     * @return array of watphy records
     */
    MrnWatprofqc[] getWatprofqcRecords(String stationId) {

        if (dbg) System.out.println("<br>getWatprofqcRecords ...");

        String where = MrnWatprofqc.STATION_ID +"='"+ stationId +"'";
        //String order = MrnWatprofqc.SPLDEP;
        MrnWatprofqc[] watphy = new MrnWatprofqc().get(where);

        return watphy;
    } //getWatprofqcRecords


    /**
     * Open the output file and make it readable by all
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
     * Write the survey info to the report file
     * @param  physnutReport  the report file
     * @param  inv      the inventory record
     * @param  survey   the survey record
     */
/*                                                                          //ub01
    void outputPhysnutSurveyInfo(RandomAccessFile physnutReport,
            MrnInventory inv, MrnSurvey survey) {

        // some pre-processing
        String areaName = inv.getAreaname("");
        if (areaName.length() > 10) {
            areaName = areaName.substring(0,10);
        } // if (areaName.length() > 10)

        String notes[] = new String[4];
        if (dbg) System.out.println("<br>survey.getNotes1().length() = " +//ub01
            survey.getNotes1("").length());
        for (int i = 0; i < 4; i++) {
            notes[i] = "";
        } // for (int i = 0; i < 4, i++)
        for (int i = 0; i < 4; i++) {
            if (survey.getNotes1("").length() >= ((i+1)*40)) {
                notes[i] = survey.getNotes1("").substring((i*40),((i+1)*40)); //ub01
            } else {
                notes[i] = survey.getNotes1("").substring((i*40));
                break;
            } // if (survey.getNotes1("").length > ((i+1)*40))
        } // for (int i = 0; i < 4, i++)

        // write the survey info on physnut report
        ec.writeFileLine(oFile, "Water physical nutrient and currents check data " +
            "for survey id: " + inv.getSurveyId());

        ec.writeFileLine(oFile, "");
        ec.writeFileLine(oFile, "+---------+----------+----------+---+" +
            "----------+----------+----------+----------+" +
            "----------------------------------------+");
        ec.writeFileLine(oFile, "|survey-ID|PlaNam    |ExpNam    |Ins|" +
            "PrjNam    |AreNam    |Domain    |PlatFm    |" +
            "Notes                                   |");
        ec.writeFileLine(oFile, "|" +
            inv.getSurveyId() + "|" +
            ec.frm(survey.getPlanam(""), 10) + "|" +
            ec.frm(survey.getExpnam(""), 10) + "|" +
            ec.frm(survey.getInstitute(""), 3) + "|" +
            ec.frm(survey.getPrjnam(""), 10) + "|" +
            ec.frm(areaName, 10) + "|" +
            ec.frm(inv.getDomain(""), 10) + "|SHIP      |" +
            ec.frm(notes[0], 40) + "|");
        ec.writeFileLine(oFile, "+---------+----------+----------+---+" +
            "----------+----------+----------+----------+" +
            ec.frm(notes[1], 40) + "|");
        for (int i = 2; i < 4; i++) {
            ec.writeFileLine(oFile, ec.frm(" ", 80) + "|" +
                ec.frm(notes[i], 40) + "|");
        } // for (int i = 2; i < 4; i++)
        ec.writeFileLine(oFile, ec.frm(" ", 80) +
            "+----------------------------------------+");
        ec.writeFileLine(oFile, " ");

    } // outputPhysnutSurveyInfo
*/                                                                          //ub01

    /**
     * Write the survey info to the report file
     * @param  watchm1Report  the report file
     * @param  inv      the inventory record
     * @param  survey   the survey record
     */
/*                                                                          //ub01
    void outputWatchm1SurveyInfo(RandomAccessFile watchm1Report,
            MrnInventory inv, MrnSurvey survey) {

        // some pre-processing
        String areaName = inv.getAreaname("");
        if (areaName.length() > 10) {
            areaName = areaName.substring(0,10);
        } // if (areaName.length() > 10)

        // write the survey info on watchm1 report
        ec.writeFileLine(watchm1Report, "Water chemistry 1 check data for " +
            "survey id: " + inv.getSurveyId());
        ec.writeFileLine(watchm1Report, " ");
        ec.writeFileLine(watchm1Report, "+---------+----------+----------+---+----------+" +
            "----------+----------+----------+");
        ec.writeFileLine(watchm1Report, "|survey-ID|PlaNam    |ExpNam    |Ins|PrjNam    |" +
            "AreNam    |Domain    |PlatFm    |");
        ec.writeFileLine(watchm1Report, "|" +
            inv.getSurveyId() + "|" +
            ec.frm(survey.getPlanam(""), 10) + "|" +
            ec.frm(survey.getExpnam(""), 10) + "|" +
            ec.frm(survey.getInstitute(""), 3) + "|" +
            ec.frm(survey.getPrjnam(""), 10) + "|" +
            ec.frm(areaName, 10) + "|" +
            ec.frm(inv.getDomain(""), 10) + "|SHIP      |");
        ec.writeFileLine(watchm1Report, "+---------+----------+----------+---+----------+" +
            "----------+----------+----------+");
        ec.writeFileLine(watchm1Report, " ");

    } // outputWatchm1SurveyInfo
*/                                                                          //ub01

    /**
     * Write the survey info to the report file
     * @param  oFile    the report file
     * @param  physnut  true (physnut) / false (watchm1)
     * @param  inv      the inventory record
     * @param  survey   the survey record
     */
// for display in header:
// survey_id,
// project name (inv)
// survey name (inv - cruise_name)
// chief scientists (lookup scientists)
// institute (lookup institutes)
// country (lookup countries),
// ship (lookup planam),
// start date,
// end date,
// latitude range,
// longitude range
// notes

    void outputSurveyInfo(RandomAccessFile oFile, boolean physnut,      //ub01
            MrnInventory inv, MrnSurvey survey, int numStations) {      //ub01
                                                                        //ub01
        // write the survey info on physnut report                      //ub01
        if (physnut) {                                                  //ub01
            ec.writeFileLine(oFile, "Water physical nutrient and currents check data " +
                "for survey id: " + inv.getSurveyId());                  //ub01
        } else {                                                         //ub01
            ec.writeFileLine(oFile, "Water chemistry 1 check data for " +//ub01
                "survey id: " + inv.getSurveyId());                      //ub01
        } // if (physnut)                                                //ub01
                                                                        //ub01
        ec.writeFileLine(oFile, "");                                    //ub01
                                                                        //ub01
        ec.writeFileLine(oFile, ec.frm("SurveyID",18) + ": " +          //ub01
            inv.getSurveyId(""));                                       //ub01
        ec.writeFileLine(oFile, ec.frm("Project Name",18) + ": " +      //ub01
            inv.getProjectName(""));                                    //ub01
        ec.writeFileLine(oFile, ec.frm("Survey Name",18) + ": " +       //ub01
            inv.getCruiseName(""));                                     //ub01
        ec.writeFileLine(oFile, ec.frm("Chief Scientist 1",18) + ": " + //ub01
            getScientist(inv.getSciCode1()));                           //ub01
        ec.writeFileLine(oFile, ec.frm("Chief Scientist 2",18) + ": " + //ub01
            getScientist(inv.getSciCode2()));                           //ub01
        ec.writeFileLine(oFile, ec.frm("Institute",18) + ": " +         //ub01
            getInstitute(inv.getInstitCode()));                         //ub01
        ec.writeFileLine(oFile, ec.frm("Country",18) + ": " +           //ub01
            getCountry(inv.getCountryCode()));                          //ub01
        ec.writeFileLine(oFile, ec.frm("Ship",18) + ": " +              //ub01
            getShip(inv.getPlanamCode()));                              //ub01
        ec.writeFileLine(oFile, ec.frm("Start Date", 18) + ": " +       //ub01
            inv.getDateStart("").substring(0,10));                      //ub01
        ec.writeFileLine(oFile, ec.frm("End Date", 18) + ": " +         //ub01
            inv.getDateEnd("").substring(0,10));                        //ub01
        ec.writeFileLine(oFile, ec.frm("Latitude", 18) + ": " +         //ub01
            inv.getLatNorth() + " to " + inv.getLatSouth() +            //ub01
            " degrees (South is positive)");                             //ub01
        ec.writeFileLine(oFile, ec.frm("Longitude", 18) + ": " +         //ub01
            inv.getLongWest() + " to " + inv.getLongEast() +             //ub01
            " degrees (East is positive)");                              //ub01
        ec.writeFileLine(oFile, ec.frm("Number of stations", 18) + ": " +//ub01
            numStations);                                               //ub01
        ec.writeFileLine(oFile, "");                                    //ub01
        ec.writeFileLine(oFile, ec.frm("Notes", 18) + ": " +            //ub01
            survey.getNotes1(""));                                      //ub01
        if (!"".equals(survey.getNotes2("")))                           //ub01
            ec.writeFileLine(oFile, ec.frm(" ", 18) + ": " +            //ub01
                survey.getNotes2(""));                                  //ub01
        if (!"".equals(survey.getNotes3("")))                           //ub01
            ec.writeFileLine(oFile, ec.frm(" ", 18) + ": " +            //ub01
                survey.getNotes3(""));                                  //ub01
        if (!"".equals(survey.getNotes3("")))                           //ub01
            ec.writeFileLine(oFile, ec.frm(" ", 18) + ": " +            //ub01
                survey.getNotes4(""));                                  //ub01
        ec.writeFileLine(oFile, "");                                    //ub01
                                                                        //ub01
    } // outputSurveyInfo                                               //ub01


    /**                                                                 //ub01
     * get the scientist                                                //ub01
     * @param   code    the scientist code                              //ub01
     * @return          the full scientist name                         //ub01
     */                                                                 //ub01
    String getScientist(int code) {                                     //ub01
        String scientist = "";                                          //ub01
        if (code != MrnScientists.INTNULL) {                            //ub01
            if (code > 1) {                                             //ub01
                MrnScientists[] scientists = new MrnScientists(code).get();//ub01();
                scientist = scientists[0].getSurname("") + " " +        //ub01
                    scientists[0].getFName("");                         //ub01
            } // if (code > 1)                                          //ub01
        } // if (code != null)                                          //ub01
        return scientist;                                               //ub01
    } // getScientist                                                   //ub01


    /**                                                                 //ub01
     * get the institute                                                //ub01
     * @param   code    the institute code                              //ub01
     * @return          the institute name                              //ub01
     */                                                                 //ub01
    String getInstitute(int code) {                                     //ub01
        String institute = "";                                          //ub01
        if (code != MrnInstitutes.INTNULL) {                            //ub01
            MrnInstitutes[] institutes = new MrnInstitutes(code).get(); //ub01
            institute = institutes[0].getName("");                      //ub01
        } // if (code != null)                                          //ub01
        return institute;                                               //ub01
    } // getInstitute                                                   //ub01


    /**                                                                 //ub01
     * get the country                                                  //ub01
     * @param   code    the country code                                //ub01
     * @return          the country name                                //ub01
     */                                                                 //ub01
    String getCountry(int code) {                                       //ub01
        String country = "";                                            //ub01
        if (code != MrnCountry.INTNULL) {                               //ub01
            MrnCountry[] countries = new MrnCountry(code).get();        //ub01
            country = countries[0].getName("");                         //ub01
        } // if (code != null)                                          //ub01
        return country;                                                 //ub01
    } // getCountry                                                     //ub01


    /**                                                                 //ub01
     * get the ship                                                     //ub01
     * @param   code    the ship code                                   //ub01
     * @return          the ship name and callsign                      //ub01
     */                                                                 //ub01
    String getShip(int code) {                                          //ub01
        String ship = "";                                               //ub01
        if (code != MrnPlanam.INTNULL) {                                //ub01
            MrnPlanam[] ships = new MrnPlanam(code).get();              //ub01
            ship = ships[0].getName("");                                //ub01
            String callsign = ships[0].getCallsign("");                 //ub01
            if (!"".equals(callsign)) ship += " (call sign: " +         //ub01
                callsign + ")";                                         //ub01
        } // if (code != null)                                          //ub01
        return ship;                                                    //ub01
    } // getShip                                                        //ub01


    /**
     * Write the station info to the report file
     * @param  report   the report file
     * @param  station  the station record
     * @param  watphy   the watphy record
     */
    void outputStationInfo(RandomAccessFile report, MrnStation station, String spldattim) {

//---------------------------------------------------------------------------------------------
//Station Id        StnNam   Latitude   Longitude  Date        Time   MaxSplDep  StnDep  OffShD
//SFI06638      06638        32.68080    17.76670  2001-02-05  15:00:00  100.00  101.00
//

        // write the station info on physnut report
        //ec.writeFileLine(report, " ");
        ec.writeFileLine(report, "-----------------------------------" +
            "-------------------------------------------------------------------");
        ec.writeFileLine(report, "Station Id    StnNam       " +
            "Latitude   Longitude  Date        Time   MaxSplDep  StnDep  OffShD");
        ec.writeFileLine(report,
            ec.frm(station.getStationId(), 14) +
            ec.frm(station.getStnnam(""), 10) +
            ec.frm(station.getLatitude(), 11, 5) +
            ec.frm(station.getLongitude(), 12, 5) + "  " +
            ec.frm(station.getDateStart("").substring(0,10), 12) +
            ec.frm(spldattim, 8) +
            blankIfNull(station.getMaxSpldep(), 8, 2) +
            blankIfNull(station.getStndep(), 8, 2) +
            blankIfNull(station.getOffshd(), 8, 2));
        ec.writeFileLine(report, " ");

    } // outputStationInfo


    /**
     * Write the weather info to the report file
     * @param  report         the report file
     * @param  weather        the weather records
     */
    void outputWeatherInfo(RandomAccessFile report, MrnWeather[] weather) {

//                    __Temperature___        Vis   Wx   Water        __wind__   ___swell___
//NavEquTyp  AtmoPre  Surf   Dry   Wet Cloud  code  code Color Tran   Dir  Spd   Dir  Hgt Per
//

        // write the station info on physnut report
        ec.writeFileLine(report, "                    __Temperature___        " +
            "Vis   Wx   Water        __wind__   ___swell___");
        ec.writeFileLine(report, "NavEquTyp  AtmoPre  Surf   Dry   Wet Cloud  " +
            "code  code Color Tran   Dir  Spd   Dir  Hgt Per");

        if (weather.length > 0) {
            ec.writeFileLine(report,
                ec.frm(weather[0].getNavEquipType(""), 11) +
                blankIfNull(weather[0].getAtmosphPres(), 7, 1) +
                blankIfNull(weather[0].getSurfaceTmp(), 6, 1) +
                blankIfNull(weather[0].getDrybulb(), 6, 1) +
                blankIfNull(weather[0].getWetbulb(), 6, 1) + " " +
                ec.frm(weather[0].getCloud(""), 7) +
                ec.frm(weather[0].getVisCode(""), 6) +
                ec.frm(weather[0].getWeatherCode(""), 5) +
                blankIfNull(weather[0].getWaterColor(), 5) +
                blankIfNull(weather[0].getTransparency(), 5) +
                blankIfNull(weather[0].getWindDir(), 6) +
                blankIfNull(weather[0].getWindSpeed(), 5, 1) +
                blankIfNull(weather[0].getSwellDir(), 6) +
                blankIfNull(weather[0].getSwellHeight(), 5, 1) +
                blankIfNull(weather[0].getSwellPeriod(), 4));
        } else {
            ec.writeFileLine(report, " ");
        } // if (weather.length > 0)
        ec.writeFileLine(report, " ");

    } // outputStationInfo


    /**
     * Write the physnut data header to the report file
     * @param  physnutReport  the report file
     */
    void outputPhysnutDataHeader(RandomAccessFile physnutReport, MrnWatprofqc[] profqc) {

//    Sub     F  Depth   DOx      Sal    Tmp  Sndv     no2    no3    po4    ptot    sio3

        // write physnut data header
        ec.writeFileLine(physnutReport, "    Sub       Depth F   DOx F      " +
            "Sal F    Tmp F  Sndv F  Press F   Turb F Fluores F     " +          //ub01
            "no2 F    no3 F    po4 F    ptot F    sio3 F  curSpd F  curDir F");  //ub01

        String disoxygenFlag = "  ";
        String salinityFlag = "  ";
        String temperatureFlag = "  ";
        String no3Flag = "  ";
        String po4Flag = "  ";
        String sio3Flag = "  ";
        if (profqc.length > 0) {
            disoxygenFlag = blankIfNull(profqc[0].getDisoxygen(), 2, -1);
            salinityFlag = blankIfNull(profqc[0].getSalinity(), 2, -1);
            temperatureFlag = blankIfNull(profqc[0].getTemperature(), 2, -1);
            no3Flag = blankIfNull(profqc[0].getNo3(), 2, -1);
            po4Flag = blankIfNull(profqc[0].getPo4(), 2, -1);
            sio3Flag = blankIfNull(profqc[0].getSio3(), 2, -1);
        } // if (profqc.length > 0)

        StringBuffer line = new StringBuffer("    Prof Flag        ");
        line.append(
            ec.frm(" ", 6) + disoxygenFlag +
            ec.frm(" ", 9) + salinityFlag +
            ec.frm(" ", 7) + temperatureFlag +
            ec.frm(" ", 8) +    // sound velocity
            ec.frm(" ", 9) +    // pressure                             //ub01
            ec.frm(" ", 9) +    // turbidity                            //ub01
            ec.frm(" ", 10) +   // fluorescence                         //ub01
            ec.frm(" ", 10) +   // no2
            ec.frm(" ", 7) + no3Flag +
            ec.frm(" ", 7) + po4Flag +
            ec.frm(" ", 9) +   // ptot
            ec.frm(" ", 9) + sio3Flag +
            ec.frm(" ", 10) +   // cur speed
            ec.frm(" ", 10));   // cur dir

        ec.writeFileLine(physnutReport, line.toString());


    } // outputPhysnutDataHeader


    /**
     * Write the watchem1, watchl data header to the report file
     * @param  watchm1Report  the report file
     */
    void outputWatchm1DataHeader(RandomAccessFile watchm1Report, MrnWatprofqc[] profqc) {

//    Sub     F  Depth        dic     doc fluoride    idtot      kjn     nh3     oxa      poc      toc    ph chlor-A chlor_B chlor-C

        // write watchm1 data header
        ec.writeFileLine(watchm1Report, "    Sub       Depth F        dic F     " +
            "doc F fluoride F    idtot F      kjn F     nh3 F     oxa F      poc F      " +
            "toc F    ph F chlor-A F chlor_B F chlor-C F");

        StringBuffer line = new StringBuffer("    Prof Flag        ");
        line.append(
            ec.frm(" ", 11) + blankIfNull(profqc[0].getDic(), 2, -1) +
            ec.frm(" ", 10) +   // doc
            ec.frm(" ", 11) +   // fluoride
            ec.frm(" ", 11) +   // iodate
            ec.frm(" ", 11) +   // kjn
            ec.frm(" ", 10) +   // nh3
            ec.frm(" ", 10) +   // oxa
            ec.frm(" ", 11) +   // poc
            ec.frm(" ", 11) +   // toc
            ec.frm(" ", 6) + blankIfNull(profqc[0].getPh(), 2, -1) +
            ec.frm(" ", 8) + blankIfNull(profqc[0].getChla(), 2, -1) +
            ec.frm(" ", 10) +   // chlb
            ec.frm(" ", 10));   // chlc

        ec.writeFileLine(watchm1Report, line.toString());

    } // outputWatchm1DataHeader


    /**
     * write the physnut data info to the report file
     * @param  physnutReport  the report file
     * @param  watphy         the watphy record
     * @param  watnut         the watnut record
     */
    void outputPhysnutInfo(RandomAccessFile physnutReport, MrnWatphy watphy,
            MrnCurrents currents, MrnWatnut[] watnut, MrnWatqc[] watqc) {

//    Sub     F  Depth   DOx      Sal    Tmp  Sndv     no2    no3    po4    ptot    sio3
        StringBuffer line = new StringBuffer("    ");

        if (watphy != null) {
            if (dbg) System.out.println("watphy != null");
            line.append(
                ec.frm(watphy.getSubdes(""), 8) +
                //ec.frm(watphy.getFiltered(""), 1) +
                blankIfNull(watphy.getSpldep(), 7, 2) +
                blankIfNull(watqc[0].getSpldep(), 2, -1) +
                blankIfNull(watphy.getDisoxygen(), 6, 2) +
                blankIfNull(watqc[0].getDisoxygen(), 2, -1) +
                blankIfNull(watphy.getSalinity(), 9, 3) +
                blankIfNull(watqc[0].getSalinity(), 2, -1) +
                blankIfNull(watphy.getTemperature(), 7, 2) +
                blankIfNull(watqc[0].getTemperature(), 2, -1) +
                blankIfNull(watphy.getSoundv(), 6, 1) + "  " +
                blankIfNull(watphy.getPressure(), 7, 2) + "  " +        //ub01
                blankIfNull(watphy.getTurbidity(), 7, 3) + "  " +       //ub01
                blankIfNull(watphy.getFluorescence(), 8, 4) + "  ");    //ub01
        } else {
            if (dbg) System.out.println("watphy is null");
            line.append(
                ec.frm(" ", 8) +
                blankIfNull(currents.getSpldep(), 7, 2) + "  " +
                //ec.frm(" ", 36));                                     //ub01
                ec.frm(" ", 65));                                       //ub01
        } // if (watphy != null)

        if ((watnut.length > 0) && (watnut != null)) {
            if (dbg) System.out.println("watnut != null");
            line.append(
                blankIfNull(watnut[0].getNo2(), 8, 2) + "  " +
                blankIfNull(watnut[0].getNo3(), 7, 2) + blankIfNull(watqc[0].getNo3(), 2, -1) +
                blankIfNull(watnut[0].getPo4(), 7, 2) + blankIfNull(watqc[0].getPo4(), 2, -1) +
                blankIfNull(watnut[0].getPtot(), 8, 3) + "  " +
                blankIfNull(watnut[0].getSio3(), 8, 2) + blankIfNull(watqc[0].getSio3(), 2, -1));
        } else {
            if (dbg) System.out.println("watnut is null");
            line.append(ec.frm(" ", 48));
        } // if (watnut.length > 0)

        if (currents != null) {
            if (dbg) System.out.println("currents != null");
            line.append(
                blankIfNull(currents.getCurrentSpeed(),8,2) + "  " +
                blankIfNull(currents.getCurrentDir(),8,2) + "  ");
        } else {
            if (dbg) System.out.println("currents is null");
            line.append(ec.frm(" ", 20));
        } // if (currents != null)

        ec.writeFileLine(physnutReport, line.toString());

    } // outputPhysnutInfo


    /**
     * write the currents data info to the report file
     * @param  physnutReport  the report file
     * @param  currents       the currents record
     */
    void outputCurrentInfo(RandomAccessFile physnutReport, MrnCurrents currents) {

//    Sub     F  Depth   DOx      Sal    Tmp  Sndv     no2    no3    po4    ptot    sio3
        if (dbg) System.out.println("currents only");
        String line = "    " +
            ec.frm(" ", 8) +
            blankIfNull(currents.getSpldep(), 7, 2) +
            ec.frm(" ", 86) +
            blankIfNull(currents.getCurrentSpeed(),8,2) + "  " +
            blankIfNull(currents.getCurrentDir(),8,2) + "  ";

        ec.writeFileLine(physnutReport, line);

    } // outputCurrentsInfo


    /**
     * write the watchem1 data info to the report file
     * @param  watchm1Report  the report file
     * @param  watphy         the watphy record
     * @param  watchem1       the watchem1 record
     * @param  watchl         the watchl record
     */
    void outputWatchm1Info(RandomAccessFile watchm1Report, MrnWatphy watphy,
            MrnWatchem1[] watchem1, MrnWatchl[] watchl, MrnWatqc[] watqc) {

//    Sub     F  Depth        dic     doc fluoride    idtot      kjn     nh3
//     oxa      poc      toc    ph chlor-A chlor_B chlor-C

        StringBuffer line = new StringBuffer("    ");
        line.append(
            ec.frm(watphy.getSubdes(""), 8) +
            //ec.frm(watphy.getFiltered(""), 1) +
            blankIfNull(watphy.getSpldep(), 7, 2) +
            blankIfNull(watqc[0].getSpldep(), 2, -1));

        if (watchem1.length > 0) {
            if (dbg) System.out.println("watchem1.length > 0");
            line.append(
                blankIfNull(watchem1[0].getDic(), 11, 3) +
                blankIfNull(watqc[0].getDic(), 2, -1) +
                blankIfNull(watchem1[0].getDoc(), 8, 2) + "  " +
                blankIfNull(watchem1[0].getFluoride(), 9, 3) + "  " +
                blankIfNull(watchem1[0].getIodate(), 9, 3) + "  " +
                blankIfNull(watchem1[0].getKjn(), 9, 2) + "  " +
                blankIfNull(watchem1[0].getNh3(), 8, 2) + "  " +
                blankIfNull(watchem1[0].getOxa(), 8, 3) + "  " +
                ec.frm(" ", 9) +  "  " + // poc
                ec.frm(" ", 9) +  "  " + // toc
                blankIfNull(watchem1[0].getPh(), 6, 2) +
                blankIfNull(watqc[0].getPh(), 2, -1));
        } else {
            if (dbg) System.out.println("watchem1.length == 0");
            line.append(ec.frm(" ", 106));
        } // if (watchem1.length > 0)

        if (watchl.length > 0) {
            if (dbg) System.out.println("watchl.length > 0");
            line.append(
                blankIfNull(watchl[0].getChla(), 8, 3) +
                blankIfNull(watqc[0].getChla(), 2, -1) +
                blankIfNull(watchl[0].getChlb(), 8, 3) + "  " +
                blankIfNull(watchl[0].getChlc(), 8, 3) + "  ");
        } else {
            if (dbg) System.out.println("watchl.length == 0");
            line.append(ec.frm(" ", 30));
        } // if (watnut.length > 0)

        ec.writeFileLine(watchm1Report, line.toString());

    } // outputWatchm1Info


    /**
     * blank if null
     * @param value     the value
     * @param length    the length of the string
     * @param dec       the number of decimal places
     * @return the string
     */
    String blankIfNull(float value, int length, int dec) {
         return (value == common2.TablesC.FLOATNULL ?
            ec.frm(" ", length) : ec.frm(value, length, dec));
    } // blankIfNull


    /**
     * blank if null
     * @param value     the value
     * @param length    the length of the string
     */
    String blankIfNull(int value, int length) {
         return (value == common2.TablesC.INTNULL ?
            ec.frm(" ", length) : ec.frm(value, length));
    } // blankIfNull


    /**
     * blank if null
     * @param value     the value
     * @param length    the length of the string
     */
    String blankIfNull(int value, int length, int value2) {
         return ((value == common2.TablesC.INTNULL) || (value == value2) ?
            ec.frm(" ", length) : ec.frm(value, length));
    } // blankIfNull


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            LoadMRNReports local= new LoadMRNReports(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, "LoadMRNReports", "Main", "");
            // close the connection to the database
        } // try-catch
        common2.DbAccessC.close();

    } // main

} // class LoadMRNReports