package survsum;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.SimpleDateFormat;


/**
 * Dislays the survey summary
 *
 * @author  980512 - SIT Group
 * @version
 * 980512 - Neville Paynter - create class                              <br>
 * 031110 - Ursula von St Ange - change to package class - parts where
 *          passcode=ter91qes do not  yet work                          <br>
 * 061130 - Ursula von St Ange - Make copy of DisplaySurvsum, and change
 *          to writing to file iso html.                                <br>

 */
public class PrintSurvsum {

    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();
    static SadCommon sc = new SadCommon();

    // Create java.sql stuff
    //String USER = "sadco";
    //String PWD = "ter91qes";
    Connection conn = null;
    java.sql.Statement stmt = null; // = conn.createStatement();
    ResultSet rset = null;;

    // +------------------------------------------------------------+
    // | Prepare for PL/SQL values                                  |
    // +------------------------------------------------------------+
    // getDisplaySurveyFull
    String  pDataCentre       = "";
    String  pProjectName      = "";
    String  pCruiseName       = "";
    String  pPlanamName       = "";
    String  pPlatformName     = "";
    String  pCallSign         = "";
    String  pCountryName      = "";
    String  pSurnameCoord     = "";
    String  pFNameCoord       = "";
    String  pTitleCoord       = "";
    String  pSurname1         = "";
    String  pFName1           = "";
    String  pTitle1           = "";
    String  pSurname2         = "";
    String  pFName2           = "";
    String  pTitle2           = "";
    String  pInstitName       = "";
    String  pInstitAddress    = "";
    String  pInstitName1      = "";
    String  pInstitAddress1   = "";
    String  pInstitName2      = "";
    String  pInstitAddress2   = "";
    String  pInstitNameC      = "";
    String  pInstitAddressC   = "";
    String  pDateStart        = "";
    String  pDateEnd          = "";
    int     pGMTdiff          = 0;
    float   pLatNorth         = 0;
    float   pLatSouth         = 0;
    float   pLongWest         = 0;
    float   pLongEast         = 0;
    String  pTrackChart       = "";
    String  pTargetCountryName = "";
    String  pDataRecorded     = "";
    String  pNationalPgm      = "";
    String  pExchangeRestrict = "";
    String  pCoopPgm          = "";
    String  pCoordinatedInt   = "";
    String  pPortStart        = "";
    String  pPortEnd          = "";
    String  pSurveyTypeName   = "";

    // getDisplaySurveyNotes
    String  pLine1            = "";
    String  pLine2            = "";
    String  pLine3            = "";
    String  pLine4            = "";
    String  pLine5            = "";
    String  pLine6            = "";
    String  pLine7            = "";
    String  pLine8            = "";
    String  pLine9            = "";
    String  pLine10           = "";

    // getListInvestigators
    int     pSciCodeI      [];
    String  pSurnameI      [];
    String  pFNameI        [];
    String  pTitleI        [];
    String  pPiCode        [];
    String  pInstitNameI   [];
    String  pAddressI      [];

    // getListSamplingBatches
    String  plBatchId      [];
    String  plStartDateTime[];
    String  plEndDateTime  [];
    int     plHours        [];

    // getListStations
    String  plStationId    [];
    String  plStnnam       [];
    String  plLatitude     [];
    String  plLongitude    [];
    float   plStndep       [];
    float   plMaxSpldep    [];
    int     plLandTest     [];
    String  plFlaggingName [];
    String  plQualityName  [];
    int     plWatphyCount  [];
    int     plSedphyCount  [];
    int     plTisphyCount  [];

    // getListPositions
    String  pPiCodeP       [];
    String  pTypeCodeP     [];
    String  pTypeP         [];
    String  pLatitude      [];
    String  pLongitude     [];
    String  pRemarksP      [];

    // getListSamples
    String  pPiCodeS       [];
    int     pSampleNumber  [];
    String  pSampleUnit    [];
    String  pTypeCodeS     [];
    String  pTypeS         [];
    String  pRemarksS      [];

    // getListZones
    String  pMarineZone    [];

    // getPsdData
    String  pProjection       = "";
    String  pSpheroid         = "";
    String  pDatum            = "";


    // +------------------------------------------------------------+
    // | look up a URL parameter                                    |
    // +------------------------------------------------------------+
/*
    private static String getArgument(String args[], String name) {
        String prefix = name + "=";
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith(prefix) ) {
                return args[i].substring(prefix.length());
            }  //  if (args[i].startsWith(prefix) )
        }  //  for (int i = 0; i < args.length; i++)
        return "";
    }  //  private static String getArgument(String args[], String name)
*/


    // +------------------------------------------------------------+
    // | Format text strings                                        |
    // +------------------------------------------------------------+
//    private static Container formatStr(String oldStr) {
//        Container newStrc = new Container();
//        newStrc.addItem(new SimpleItem(oldStr).setFontSize(2));
//        return newStrc;
//    }  //  private static Container formatStr(String oldStr)


    // +------------------------------------------------------------+
    // | Format text strings  (Big)                                 |
    // +------------------------------------------------------------+
//    private static Container formatStrBig(String oldStr) {
//        Container newStrc = new Container();
//        newStrc.addItem(new SimpleItem(oldStr).setFontSize(3));
//        return newStrc;
//    }  //  private static Container formatStrBig(String oldStr)


    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    //public static void main(String args[]) throws ServerException, SQLException {
    public PrintSurvsum(String args[]) {

        // +------------------------------------------------------------+
        // | Print SADCO logo                                           |
        // +------------------------------------------------------------+
        System.out.println("SADCO - Southern African Data Centre for Oceanography - Inventories");
        System.out.println("-------------------------------------------------------------------");
        System.out.println();

        // +------------------------------------------------------------+
        // | Load the JDBC driver and connect to the database           |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass);
        /*
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = java.sql.DriverManager.getConnection
                ("jdbc:oracle:thin:" + USER + "/" + PWD +
                    "@146.64.23.200:1522:etek8");          // steamer
                    //"@morph.csir.co.za:1522:etek8");
            conn.setAutoCommit(false);
        } catch (Exception e) {
            this.addItem("Logon to SADCO fails: " + e.getMessage());
            return;
        } // try-catch
        */

        // +------------------------------------------------------------+
        // | Are there any input parameters                             |
        // +------------------------------------------------------------+
        if (args.length < 1) {
            System.out.println("There are no input parameters")   ;
            return;
        }  //  if (args.length < 1)

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String pSurveyId = sc.getArgument(args,"surveyid");
        String passCode  = sc.getArgument(args,"passcode");


        // +------------------------------------+
        // | Count the number position records  |
        // +------------------------------------+
        int positionCount = getRecordCount("positions", pSurveyId);

        // +------------------------------------+
        // | Count the number batch    records  |
        // +------------------------------------+
        int batchCount = getRecordCount("sampling_batch", pSurveyId);

        // +------------------------------------+
        // | Count the number station  records  |
        // +------------------------------------+
        int stationCount = getRecordCount("station", pSurveyId);

        // +---------------------------------+
        // | Count the number sample records |
        // +---------------------------------+
        int sampleCount = getRecordCount("samples", pSurveyId);

        // +--------------------------------+
        // | Count the marine zones records |
        // +--------------------------------+
        int zoneCount = getRecordCount("zones_covered", pSurveyId);


        // +------------------------------------------------------------+
        // | Build the empty arrays for holding investigators data      |
        // +------------------------------------------------------------+
        pSciCodeI      = new int   [12];
        pSurnameI      = new String[12];
        pFNameI        = new String[12];
        pTitleI        = new String[12];
        pPiCode        = new String[12];
        pInstitNameI   = new String[12];
        pAddressI      = new String[12];

        // getListSamplingBatches
        plBatchId      = new String[batchCount];
        plStartDateTime= new String[batchCount];
        plEndDateTime  = new String[batchCount];
        plHours        = new int   [batchCount];

        // getListStations
        plStationId    = new String[stationCount];
        plStnnam       = new String[stationCount];
        plLatitude     = new String[stationCount];
        plLongitude    = new String[stationCount];
        plStndep       = new float [stationCount];
        plMaxSpldep    = new float [stationCount];
        plLandTest     = new int   [stationCount];
        plFlaggingName = new String[stationCount];
        plQualityName  = new String[stationCount];
        plWatphyCount  = new int   [stationCount];
        plSedphyCount  = new int   [stationCount];
        plTisphyCount  = new int   [stationCount];

        // getListPositions
        pPiCodeP       = new String[positionCount];
        pTypeCodeP     = new String[positionCount];
        pTypeP         = new String[positionCount];
        pLatitude      = new String[positionCount];
        pLongitude     = new String[positionCount];
        pRemarksP      = new String[positionCount];

        // getListSamples
        pPiCodeS       = new String[sampleCount];
        pSampleNumber  = new int   [sampleCount];
        pSampleUnit    = new String[sampleCount];
        pTypeCodeS     = new String[sampleCount];
        pTypeS         = new String[sampleCount];
        pRemarksS      = new String[sampleCount];

        // getListZones
        pMarineZone    = new String[zoneCount];


        // +-----------------+
        // | Batch    arrays |
        // +-----------------+
        for (int i = 0; i < batchCount; i++) {
            plBatchId       [i]    = "";
            plStartDateTime [i]    = "";
            plEndDateTime   [i]    = "";
            plHours         [i]    = 0;
        }  //  for (int i = 0; i < batchCount; i++)

        // +-----------------+
        // | Station  arrays |
        // +-----------------+
        for (int i = 0; i < stationCount; i++) {
            plStationId     [i]    = "";
            plStnnam        [i]    = "";
            plLatitude      [i]    = "";
            plLongitude     [i]    = "";
            plStndep        [i]    = 0;
            plMaxSpldep     [i]    = 0;
            plLandTest      [i]    = 0;
            plWatphyCount   [i]    = 0;
            plSedphyCount   [i]    = 0;
            plTisphyCount   [i]    = 0;
            plFlaggingName  [i]    = "";
            plQualityName   [i]    = "";
        }  //  for (int i = 0; i < stationCount; i++)

        // +------------------------------------------------------------+
        // | Build the buffers for the investigators data               |
        // +------------------------------------------------------------+
        for (int i = 0; i < 12; i++) {
            pSciCodeI   [i]    = 0;
            pSurnameI   [i]    = "";
            pFNameI     [i]    = "";
            pTitleI     [i]    = "";
            pPiCode     [i]    = "";
            pInstitNameI[i]    = "";
            pAddressI   [i]    = "";
        }  //  for (int i = 0; i < 6; i++)

        // +-----------------+
        // | Position arrays |
        // +-----------------+
        for (int i = 0; i < positionCount; i++) {
            pPiCodeP       [i]    = "";
            pTypeCodeP     [i]    = "";
            pTypeP         [i]    = "";
            pLatitude      [i]    = "";
            pLongitude     [i]    = "";
            pRemarksP      [i]    = "";
        }  //  for (int i = 0; i < positionCount; i++)

        // +-----------------+
        // | Samples arrays  |
        // +-----------------+
        for (int i = 0; i < sampleCount; i++) {
            pPiCodeS     [i]    = "";
            pTypeCodeS   [i]    = "";
            pTypeS       [i]    = "";
            pSampleNumber[i]    = 0;
            pSampleUnit  [i]    = "";
            pRemarksS    [i]    = "";
        }  //  for (int i = 0; i < sampleCount; i++)

        // +--------------------+
        // | Marine Zone arrays |
        // +--------------------+
        for (int i = 0; i < zoneCount; i++) {
            pMarineZone [i]    = "";
        }  //  for (int i = 0; i < positionCount; i++)


        // +------------------------------------------------------------+
        // | Retrieve the survey information from the packaged PL/SQL   |
        // | using procedure display_survey                             |
        // +------------------------------------------------------------+
        getDisplaySurveyFull(pSurveyId);

        // +------------------+
        // | Get survey_notes |
        // +------------------+
        getDisplaySurveyNotes(pSurveyId);

        // +------------------------------------------+
        // | Get a listing of Principal Investigators |
        // +------------------------------------------+
        getListInvestigators(pSurveyId);

        // +----------------------+
        // | Get sampling Batches |
        // +----------------------+
        if (batchCount > 0) {
            getListSamplingBatches(pSurveyId);
        }  //  if (batchCount > 0)

        // +----------------------+
        // | Get stations         |
        // +----------------------+
        if (stationCount > 0) {
            getListStations(pSurveyId);
        }  //  if (stationCount > 0)

        // +---------------+
        // | Get positions |
        // +---------------+
        if (positionCount > 0) {
            getListPositions(pSurveyId);
        } //  if (positionCount > 0)

        // +-------------+
        // | Get samples |
        // +-------------+
        if (sampleCount > 0) {
            getListSamples(pSurveyId);
        }  //  if (sampleCount > 0)

        // +-----------+
        // | Get zones |
        // +-----------+
        if (zoneCount > 0) {
            getListZones(pSurveyId);
        }  //  if (zoneCount > 0)


        // +--------------------------+
        // | Generate the ocean area  |
        // +--------------------------+
        int latSouth = (int)pLatSouth;
        int latNorth = (int)pLatNorth;
        int lonWest  = (int)pLongWest;
        int lonEast  = (int)pLongEast;

        String oceanArea = "";

        if (lonWest < 10) {
            if (lonWest < -20 & lonEast >= -20) {
                oceanArea = new String("SW and SE Atlantic");
            } else {
                if (lonWest >= -20) {
                    oceanArea = new String("SE Atlantic");
                } else {
                    oceanArea = new String("SW Atlantic");
                }  //  if (lonWest >= -20)
            }  //  if (lonWest < -20 & lonEast >= -20)
        }  //  if (lonWest < 10)

        if (lonEast > 20) {
            if (oceanArea.length() < 1) {
                oceanArea = new String("SW Indian");
            } else {
                oceanArea = new String(oceanArea + ", SW Indian");
            }  //  if (oceanArea.length() < 1)
        }  //  if (lonEat > 20)

        if (latSouth > 40) {
            if (oceanArea.length() < 1) {
                oceanArea = new String("Southern");
            } else {
                oceanArea = new String(oceanArea + ", Southern");
            }  //  if (oceanArea.length() < 1)
        }  //  if (latSouth > 40)
        if (dbg) System.out.println("<br>oceanArea = " + oceanArea);

        // +------------------------------------------------------------+
        // | Retrieve the PSD data                                      |
        // +------------------------------------------------------------+
        getPsdData(pSurveyId);

        // +-------+
        // | Title |
        // +-------+
        System.out.println("SURVEY SUMMARY REPORT: " + pSurveyId);
        System.out.println("-------------------------------");
        System.out.println();

        // +------+
        // | Data |
        // +------+
        System.out.println("Data:");
        System.out.println("-----");
        System.out.println("Date Centre:   " + pDataCentre);
        System.out.println("Data Exchange: " + pExchangeRestrict);
        System.out.println("Survey Type:   " + pSurveyTypeName);
        System.out.println();

        // +----------+
        // | Platform |
        // +----------+
        System.out.println("Platform:");
        System.out.println("---------");
        System.out.println("Name:      " + pPlanamName);
        System.out.println("Type:      " + pPlatformName);
        System.out.println("Call Sign: " + pCallSign);
        System.out.println();

        // +-----------------+
        // | Survey No./Name |
        // +-----------------+
        System.out.println("Survey No./Name: " + (pCruiseName == null ? "" : pCruiseName));
        System.out.println("----------------");
        System.out.println();

        // +---------+
        // | Periods |
        // +---------+
        System.out.println("Periods:");
        System.out.println("--------");
        System.out.println("Dates: " + pDateStart + " to " + pDateEnd + " GMT+" + pGMTdiff);
        System.out.println("From:  " + pPortStart + " to " + pPortEnd);
        System.out.println();

        // +------------------------+
        // | Responsible Laboratory |
        // +------------------------+
        System.out.println("Responsible Laboratory:");
        System.out.println("-----------------------");
        System.out.println("Name:    " + pInstitName);
        System.out.println("Address: " + pInstitAddress);
        System.out.println("Country: " + pCountryName);
        System.out.println();

        // +--------------------+
        // | Chief Scientist(s) |
        // +--------------------+
        System.out.println("Chief Scientist(s):");
        System.out.println("-------------------");
        System.out.println("1. Name:       " +
            (pTitle1.equals("") ? "" : pTitle1 + " ") +
            pFName1 + " " + pSurname1);
        System.out.println("   Laboratory: " + pInstitName1);
        System.out.println("   Address:    " + pInstitAddress1);
        if (!pSurname2.equals("unknown")) {
            System.out.println("2. Name:       " +
                (pTitle2.equals("") ? "" : pTitle2 + " ") +
                pFName2 + " " + pSurname2);
            System.out.println("   Laboratory: " + pInstitName2);
            System.out.println("   Address:    " + pInstitAddress2);
        }  //  if (!pSurname.equals("unknown"))
        System.out.println();

        // +-------------------------------------------+
        // | Objectives and Brief Narrative of Cruise  |
        // +-------------------------------------------+
        System.out.println("Objectives and Brief Narrative of Cruise:");
        System.out.println("-----------------------------------------");
        if (!"".equals(pLine1))  System.out.println("   " + pLine1);
        if (!"".equals(pLine2))  System.out.println("   " + pLine2);
        if (!"".equals(pLine3))  System.out.println("   " + pLine3);
        if (!"".equals(pLine4))  System.out.println("   " + pLine4);
        if (!"".equals(pLine5))  System.out.println("   " + pLine5);
        if (!"".equals(pLine6))  System.out.println("   " + pLine6);
        if (!"".equals(pLine7))  System.out.println("   " + pLine7);
        if (!"".equals(pLine8))  System.out.println("   " + pLine8);
        if (!"".equals(pLine9))  System.out.println("   " + pLine9);
        if (!"".equals(pLine10)) System.out.println("   " + pLine10);
        System.out.println();

        // +---------+
        // | Project |
        // +---------+
        System.out.println("Project:");
        System.out.println("--------");
        System.out.println("Name:           " + pProjectName.trim());
        System.out.println("Co-ordin. Body: " + pInstitNameC.trim());
        System.out.println("Address:        " + pInstitAddressC.trim());
        System.out.println();

        // +-------------------------+
        // | Principal Investigators |
        // +-------------------------+
        System.out.println("Principal Investigators:");
        System.out.println("------------------------");

        // +---------------------------+
        // | Process each investigator |
        // +---------------------------+
        for (int i = 0; i < 12; i++) {
            // Stop processing if there are no more investigators
            if (pSurnameI[i].length() == 0) break;
            if (pSciCodeI[i] < 2) break;
            System.out.println(pPiCode[i] + ". " +
                (pTitleI[i].equals("") ? "" : pTitleI[i] + " ") +
                pFNameI[i] + " " + pSurnameI[i]);
            System.out.println("   " + pInstitNameI[i]);
            System.out.println("   " + pAddressI[i]);
        }  //  for (int i = 0; i < 6; i++)
        System.out.println();

        // +------------------------------------------------------------+
        // | Mooring, Bottom Mounted Geer and Drifting Systems          |
        // +------------------------------------------------------------+
        System.out.println("Bottom Mounted Gear and Drifting Systems:");
        System.out.println("-----------------------------------------");

        // Only process if there are records
        if (positionCount > 0) {
            System.out.println("Invstgr                    Type");
            System.out.println("Code    Latitude Longitude " +
                "Code Type                     Remarks");
            System.out.println("---------------------------" +
                "-------------------------------------");

            // Process each positions record
            for (int i = 0; i < positionCount; i++) {
                System.out.println(frm(pPiCodeP[i],8) +
                    frm(pLatitude[i],9) + frm(pLongitude[i],10) +
                    frm(pTypeCodeP[i],5) + frm(pTypeP[i],25) +
                    pRemarksP[i]);
            }  //  for (int i = 0; i < positionCount; i++)
        }  //  if (positionCount > 0)
        System.out.println();

        // +------------------------------------------------------------+
        // | Summary of Measurements and Samples Taken                  |
        // +------------------------------------------------------------+
        System.out.println("Summary of Measurements and Samples Taken:");
        System.out.println("------------------------------------------");

        // Only process if there are records
        if (sampleCount > 0) {
            System.out.println("Invstgr              Type");
            System.out.println("Code    Number Units Code " +
                "Type                               Remarks");
            System.out.println("-------------------------" +
                "----------------------------------------");

            // Process each sample record
            for (int i = 0; i < sampleCount; i++) {
                System.out.println(frm(pPiCodeS[i],8) +
                    frm(String.valueOf(pSampleNumber[i]),7) +
                    frm(pSampleUnit[i],6) + frm(pTypeCodeS[i],5) +
                    frm(pTypeS[i],35) + pRemarksS[i]);
            }  //  for (int i = 0; i < sampleCount; i++)
        }  //  if (sampleCount > 0)
        System.out.println();

        // +-------------+
        // | Track Chart |
        // +-------------+
        System.out.println("Track Chart:");
        System.out.println("------------");
        System.out.println("Has a track chart been submitted? " + pTrackChart);
        System.out.println();

        // +------------------------------------------------------------+
        // | General Ocean Areas                                        |
        // +------------------------------------------------------------+
        System.out.println("General Ocean Areas: " + oceanArea);
        System.out.println("--------------------");
        System.out.println();


        // +------------------------+
        // | Specific Areas (Zones) |
        // +------------------------+
        System.out.println("Specific Areas (zones):");
        System.out.println("----------------------");

        // Only process if there are records
        if (zoneCount > 0) {
            for (int i = 0; i < zoneCount; i++) {
                System.out.println("  " + pMarineZone[i]);
            }  //  for (int i = 0; i < zoneCount; i++)
        }  //  if (zoneCount > 0)
        System.out.println();


        // +------------------------+
        // | Target Country         |
        // +------------------------+
        System.out.println("Survey Country: " + pTargetCountryName);
        System.out.println("---------------");
        System.out.println();

        // +----------+
        // | PSD data |
        // +----------+
        System.out.println("PSD Data:");
        System.out.println("---------");
        System.out.println("Projection: " + pProjection);
        System.out.println("Spheroid:   " + pSpheroid);
        System.out.println("Datum:      " + pDatum);
        System.out.println();

        // +------------------+
        // | Sampling Batches |
        // +------------------+
        System.out.println("Sampling Batches:");
        System.out.println("-----------------");

        // Only process if there are records
        if (batchCount > 0) {
            System.out.println("BatchId Start                      "  +
                "End                        Hours");
            System.out.println("-----------------------------------" +
                "--------------------------------");
            // Create a string describing the GMT value
            String plusMinus = "";
            if (pGMTdiff >= 0) plusMinus = "+";
            String GMTstring = " (GMT " + plusMinus + pGMTdiff + ")";

            // Process each batch record
            for (int i = 0; i < batchCount; i++) {
                System.out.println(frm(plBatchId[i],8) +
                    frm(plStartDateTime[i] + GMTstring,27) +
                    frm(plEndDateTime[i] + GMTstring,27) +
                    plHours[i] + (plHours[i] <= 0.0 ? " - ERROR" : ""));
            }  //  for (int i = 0; i < batchCount; i++)
        }  //  if (batchCount > 0)
        System.out.println();


        // +------------------+
        // | Stations         |
        // +------------------+
        System.out.println("Sampling Stations:");
        System.out.println("------------------");

        // Only process if there are records
        if (stationCount > 0) {
            System.out.println("              Station                      " +
                "Station  Max Sampling  Water   Sediment Biological");
            System.out.println("StationId     Name    Latitude  Longitude  " +
                "Depth(m) Depth(m)      Samples Samples  Samples   ");
            System.out.println("-------------------------------------------" +
                "--------------------------------------------------");
            // Process each station record
            for (int i = 0; i < stationCount; i++) {
                System.out.println(frm(plStationId[i],14) +
                    frm(plStnnam[i],8) +
                    frm(plLatitude[i],8) + "  " + frm(plLongitude[i],8) + "   " +
                    frm(String.valueOf(plStndep[i]),9) +
                    frm(String.valueOf(plMaxSpldep[i]),14) +
                    frm(String.valueOf(plWatphyCount[i]),8) +
                    frm(String.valueOf(plSedphyCount[i]),9) +
                    String.valueOf(plTisphyCount[i]));
            }  //  for (int i = 0; i < stationCount; i++)
        }  //  if (stationCount > 0)

/*


        // +------------------------------------------------------------+
        // | Decide which program to return to                          |
        // +------------------------------------------------------------+
        String prevProg = new String();
        String prevProgTitle = new String();
        if (!passCode.equals("ter91qes")) {
            //prevProg = new String("DisplaySurvey");
            prevProg = new String("SadInv");
            prevProgTitle = new String(">>> Survey Listing");
        } else {
            prevProg = new String("survsum");
            prevProgTitle = new String("<<< Survey Summaries Listing");
        }  //  if (!passCode.equals("ter91qes"))

        // +------------------------------------------------------------+
        // | Create button to go to the Survey Summary form             |
        // +------------------------------------------------------------+
        Form survButton = new Form("GET", prevProg);
        survButton
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("", prevProgTitle)   )
        ;
        if (!passCode.equals("ter91qes")) {
            survButton.addItem(new Hidden("pscreen", "DisplaySurvey"));
        } // if (!passCode.equals("ter91qes"))



        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell(survButton).setHAlign(IHAlign.LEFT));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_data).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_plat).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_name).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_peri).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_labo).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_csci).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_narr).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_proj).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_pinv).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_moor).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_samp).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_trac).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_ocar).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_zone).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_tcnt).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_psd).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_samb).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_stns).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        System.out.println(tab_main .setCenter() );
*/

        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
        try {
            if (dbg) System.err.println("Before conn.close()");
            //conn.close();
            sc.disConnect(stmt, rset, conn);
            if (dbg) System.err.println("After conn.close()");
        }  catch (Exception e){}

    }  // PrintSurvsum


    /**
     * Routine to get all the record counts of various tables for a specific surveyId
     * @param table The table to get the record count for
     * @param pSurveyId The surveyId to get the record count for
     * @return the recordount
     */
    public int getRecordCount(String table, String pSurveyId) {

        int pRecordcount = 0;
        try {

            String sql = "select count(*) from " + table +
                " where survey_id = '" + pSurveyId + "'";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                pRecordcount      = (int)rset.getFloat(1);
                if (dbg) System.out.println("<br>" + table + ": pRecordcount " +
                    pRecordcount);
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.out.println("Could not process count_" + table + ": " +
                e.getMessage());
            e.printStackTrace();
            return 0;
        } // try-catch

        return pRecordcount;
    } // getRecordCount


    /**
     * Routine to get all the details for a specific survey
     * @param pSurveyId The surveyId
     */
    public void getDisplaySurveyFull(String pSurveyId) {
        try {
            int planamCode      = 0;
            int institCode      = 0;
            int coordCode       = 0;
            int sciCode1        = 0;
            int sciCode2        = 0;
            int countryCode     = 0;
            int targetCountryCode = 0;

            String sql = "select data_centre, project_name, cruise_name, national_pgm, " +
                  "exchange_restrict, coop_pgm, coordinated_int, port_start, " +
                  "port_end, to_char(date_start,'YYYY-MM-DD'), " +
                  "to_char(date_end  ,'YYYY-MM-DD'), GMT_diff, " +
                  "lat_north, lat_south, long_west, long_east, " +
                  "track_chart, data_recorded , planam_code, instit_code, " +
                  "coord_code, sci_code_1, sci_code_2, country_code, " +
                  "target_country_code " +
                  "from inventory, planam " +
                  "where inventory.survey_id = '" + pSurveyId + "' " +
                  "and inventory.planam_code = planam.code";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pDataCentre         = sc.spaceIfNull(rset.getString(j++));
                pProjectName        = sc.spaceIfNull(rset.getString(j++));
                pCruiseName         = sc.spaceIfNull(rset.getString(j++));
                pNationalPgm        = sc.spaceIfNull(rset.getString(j++));
                pExchangeRestrict   = sc.spaceIfNull(rset.getString(j++));
                pCoopPgm            = sc.spaceIfNull(rset.getString(j++));
                pCoordinatedInt     = sc.spaceIfNull(rset.getString(j++));
                pPortStart          = sc.spaceIfNull(rset.getString(j++));
                pPortEnd            = sc.spaceIfNull(rset.getString(j++));
                pDateStart          = sc.spaceIfNull(rset.getString(j++));
                pDateEnd            = sc.spaceIfNull(rset.getString(j++));
                pGMTdiff            = (int)rset.getFloat(j++);
                pLatNorth           = rset.getFloat(j++);
                pLatSouth           = rset.getFloat(j++);
                pLongWest           = rset.getFloat(j++);
                pLongEast           = rset.getFloat(j++);
                pTrackChart         = sc.spaceIfNull(rset.getString(j++));
                pDataRecorded       = sc.spaceIfNull(rset.getString(j++));
                planamCode          = (int)rset.getFloat(j++);
                institCode          = (int)rset.getFloat(j++);
                coordCode           = (int)rset.getFloat(j++);
                sciCode1            = (int)rset.getFloat(j++);
                sciCode2            = (int)rset.getFloat(j++);
                countryCode         = (int)rset.getFloat(j++);
                targetCountryCode   = (int)rset.getFloat(j++);
            } //while (rset.next()) {

                if (dbg) System.out.println("<br>pDataCentre         = " + pDataCentre      );
                if (dbg) System.out.println("<br>pProjectName        = " + pProjectName     );
                if (dbg) System.out.println("<br>pCruiseName         = " + pCruiseName      );
                if (dbg) System.out.println("<br>pNationalPgm        = " + pNationalPgm     );
                if (dbg) System.out.println("<br>pExchangeRestrict   = " + pExchangeRestrict);
                if (dbg) System.out.println("<br>pCoopPgm            = " + pCoopPgm         );
                if (dbg) System.out.println("<br>pCoordinatedInt     = " + pCoordinatedInt  );
                if (dbg) System.out.println("<br>pPortStart          = " + pPortStart       );
                if (dbg) System.out.println("<br>pPortEnd            = " + pPortEnd         );
                if (dbg) System.out.println("<br>pDateStart          = " + pDateStart       );
                if (dbg) System.out.println("<br>pDateEnd            = " + pDateEnd         );
                if (dbg) System.out.println("<br>pGMTdiff            = " + pGMTdiff         );
                if (dbg) System.out.println("<br>pLatNorth           = " + pLatNorth        );
                if (dbg) System.out.println("<br>pLatSouth           = " + pLatSouth        );
                if (dbg) System.out.println("<br>pLongWest           = " + pLongWest        );
                if (dbg) System.out.println("<br>pLongEast           = " + pLongEast        );
                if (dbg) System.out.println("<br>pTrackChart         = " + pTrackChart      );
                if (dbg) System.out.println("<br>pDataRecorded       = " + pDataRecorded    );
                if (dbg) System.out.println("<br>planamCode          = " + planamCode       );
                if (dbg) System.out.println("<br>institCode          = " + institCode       );
                if (dbg) System.out.println("<br>coordCode           = " + coordCode        );
                if (dbg) System.out.println("<br>sciCode1            = " + sciCode1         );
                if (dbg) System.out.println("<br>sciCode2            = " + sciCode2         );
                if (dbg) System.out.println("<br>countryCode         = " + countryCode      );
                if (dbg) System.out.println("<br>targetCountryCode   = " + targetCountryCode);

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = "select planam.name, platform.name, callsign " +
                  "from planam, platform " +
                  "where planam.code = " + planamCode +
                  "  and platform.code = planam.platform_code";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pPlanamName    = sc.spaceIfNull(rset.getString(j++));
                pPlatformName  = sc.spaceIfNull(rset.getString(j++));
                pCallSign      = sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = "select name, address from institutes " +
                  "where  code = " + institCode;
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pInstitName    = sc.spaceIfNull(rset.getString(j++));
                pInstitAddress = sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next())

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = "select name, address from institutes " +
                  "where  code = " + coordCode;
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pInstitNameC   = sc.spaceIfNull(rset.getString(j++));
                pInstitAddressC= sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next())

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = "select name from country " +
                  "where  code = " + countryCode;
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pCountryName   = sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next())

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = "select name from country " +
                  "where  code = " + targetCountryCode;
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pTargetCountryName = sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next())

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = "select surname, f_name, title, institutes.name, address " +
                  "from scientists, institutes " +
                  "where scientists.code = " + sciCode1 +
                  "  and institutes.code = scientists.instit_code";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pSurname1      = sc.spaceIfNull(rset.getString(j++)).trim();
                pFName1        = sc.spaceIfNull(rset.getString(j++)).trim();
                pTitle1        = sc.spaceIfNull(rset.getString(j++)).trim();
                pInstitName1   = sc.spaceIfNull(rset.getString(j++)).trim();
                pInstitAddress1= sc.spaceIfNull(rset.getString(j++)).trim();
            } //while (rset.next())

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = "select surname, f_name, title, institutes.name, address " +
                  "from scientists, institutes " +
                  "where scientists.code = " + sciCode2 +
                  "  and institutes.code = scientists.instit_code";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pSurname2      = sc.spaceIfNull(rset.getString(j++)).trim();
                pFName2        = sc.spaceIfNull(rset.getString(j++)).trim();
                pTitle2        = sc.spaceIfNull(rset.getString(j++)).trim();
                pInstitName2   = sc.spaceIfNull(rset.getString(j++)).trim();
                pInstitAddress2= sc.spaceIfNull(rset.getString(j++)).trim();
            } //while (rset.next())

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = " select name "+
                  " from inventory a, survey_type b "+
                  " where survey_id = '"+pSurveyId+"' and "+
                  " survey_type_code = b.code";

             // join om code en name tekry

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
                pSurveyTypeName   = rset.getString(1);
                if (dbg) System.err.println("<br> pSurveyTypeName "+
                    pSurveyTypeName);
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.out.println("Could not process display_survey_full: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch
    } // getDisplaySurveyFull


    /**
     * Routine to get all the survey notes for a specific surveyId
     * @param pSurveyId The surveyId to get the record count for
     */
    public void getDisplaySurveyNotes(String pSurveyId) {

        try {

            String sql = "select line1, line2, line3, line4, line5, line6, line7, " +
                  "line8, line9, line10 " +
                  "from survey_notes where survey_id = '" + pSurveyId + "'";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pLine1         = sc.spaceIfNull(rset.getString(j++)).trim();
                pLine2         = sc.spaceIfNull(rset.getString(j++)).trim();
                pLine3         = sc.spaceIfNull(rset.getString(j++)).trim();
                pLine4         = sc.spaceIfNull(rset.getString(j++)).trim();
                pLine5         = sc.spaceIfNull(rset.getString(j++)).trim();
                pLine6         = sc.spaceIfNull(rset.getString(j++)).trim();
                pLine7         = sc.spaceIfNull(rset.getString(j++)).trim();
                pLine8         = sc.spaceIfNull(rset.getString(j++)).trim();
                pLine9         = sc.spaceIfNull(rset.getString(j++)).trim();
                pLine10        = sc.spaceIfNull(rset.getString(j++)).trim();
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.out.println("Could not process display_survey_notes: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // getDisplaySurveyNotes


    /**
     * Routine to get all the investigators info for a specific surveyId
     * @param pSurveyId The surveyId to get the record count for
     */
    public void getListInvestigators(String pSurveyId) {

        try {

            String sql = "select scientists.code, surname, f_name, title, " +
                  "pi_code, institutes.name, address " +
                  "from investigators, scientists, institutes " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "and   scientists.code  =  investigators.sci_code " +
                  "and   institutes.code  =  scientists.instit_code " +
                  "order by upper(pi_code) ";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            int i = 0;
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pSciCodeI   [i]  = (int)rset.getFloat(j++);
                pSurnameI   [i]  = sc.spaceIfNull(rset.getString(j++)).trim();
                pFNameI     [i]  = sc.spaceIfNull(rset.getString(j++)).trim();
                pTitleI     [i]  = sc.spaceIfNull(rset.getString(j++)).trim();
                pPiCode     [i]  = sc.spaceIfNull(rset.getString(j++)).trim();
                pInstitNameI[i]  = sc.spaceIfNull(rset.getString(j++)).trim();
                pAddressI   [i]  = sc.spaceIfNull(rset.getString(j++)).trim();
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.out.println("Could not process list_investigators: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // getListInvestigators


    /**
     * Routine to get all the sampling batches for a specific surveyId
     * @param pSurveyId The surveyId to get the record count for
     */
    public void getListSamplingBatches(String pSurveyId) {

        try {

            String sql = "select code, batch_id, " +
                  "to_char(start_date_time, 'YYYY-MM-DD HH24:MI'), " +
                  "to_char(end_date_time, 'YYYY-MM-DD HH24:MI'), " +
                  "(end_date_time - start_date_time) * 24 " +
                  "from sampling_batch " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "order by batch_id";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);


            //if (dbg) System.err.println("before while ");
            int i = 0;
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                int batchCode     = (int)rset.getFloat(j++);
                plBatchId      [i]= sc.spaceIfNull(rset.getString(j++));
                plStartDateTime[i]= sc.spaceIfNull(rset.getString(j++));
                plEndDateTime  [i]= sc.spaceIfNull(rset.getString(j++));
                plHours        [i]= (int)rset.getFloat(j++);
                i++;
            } //while (rset.next()) {
            int numRecs = i;

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // Add GMTDiff to start and end date/times
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
            for (i = 0; i < numRecs; i++) {
                GregorianCalendar calDate = new GregorianCalendar();
                if (dbg) System.out.println("<br>plStartDateTime[i] = " + plStartDateTime[i]);
                calDate.setTime(Timestamp.valueOf(plStartDateTime[i]+":00.0"));
                calDate.add(Calendar.HOUR, pGMTdiff);
                plStartDateTime[i] = formatter.format(calDate.getTime());
                if (dbg) System.out.println(" -- " + plStartDateTime[i]);

                if (dbg) System.out.println("<br>plEndDateTime[i] = " + plEndDateTime[i]);
                calDate.setTime(Timestamp.valueOf(plEndDateTime[i]+":00.0"));
                calDate.add(Calendar.HOUR, pGMTdiff);
                plEndDateTime[i] = formatter.format(calDate.getTime());
                if (dbg) System.out.println(" -- " + plEndDateTime[i]);
            } // for (int k = 0; k < i; k++)

        } catch (Exception e) {
            System.out.println("Could not process list_sampling_batches: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // getListSamplingBatches


    /**
     * Routine to get all the station info for a specific surveyId
     * @param pSurveyId The surveyId to get the record count for
     */
    public void getListStations(String pSurveyId) {

        try {

            String sql = "select station_id, stnnam, latitude, longitude, " +
                  "stndep, max_spldep, flagging, quality " +
                  "from station, status_mode " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "and   status_mode.code = station.status_code " +
                  "order by upper(stnnam)";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            int i = 0;
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                plStationId   [i]  = sc.spaceIfNull(rset.getString(j++));
                plStnnam      [i]  = sc.spaceIfNull(rset.getString(j++));
                plLatitude    [i]  = sc.spaceIfNull(rset.getString(j++));
                plLongitude   [i]  = sc.spaceIfNull(rset.getString(j++));
                plStndep      [i]  = rset.getFloat(j++);
                plMaxSpldep   [i]  = rset.getFloat(j++);
                plFlaggingName[i]  = sc.spaceIfNull(rset.getString(j++));
                plQualityName [i]  = sc.spaceIfNull(rset.getString(j++));
                i++;
            } //while (rset.next()) {
            int numRecs = i;

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            for (i = 0; i < numRecs; i++) {
                sql = "select count(*) " +
                      "from land_test " +
                      "where " +
                      plLatitude[i] + " >= latN and " +
                      plLatitude[i] + " < latS  and " +
                      plLongitude[i] + " >= lonW and " +
                      plLongitude[i] + " < lonE";
                if (dbg) System.out.println("<br> sql "+sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                while (rset.next()) {
                    //if (dbg) System.err.println("in while ");
                    int j = 1;
                    plLandTest[i] = (int)rset.getFloat(j++);
                } // while (rset.next()) {

                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

                sql = "select count(*) from watphy " +
                      "where station_id = '" + plStationId[i] + "'";
                if (dbg) System.out.println("<br> sql "+sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                while (rset.next()) {
                    //if (dbg) System.err.println("in while ");
                    int j = 1;
                    plWatphyCount[i] = (int)rset.getFloat(j++);
                } // while (rset.next()) {

                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

                sql = "select count(*) from sedphy " +
                      "where station_id = '" + plStationId[i] + "'";
                if (dbg) System.out.println("<br> sql "+sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                while (rset.next()) {
                    //if (dbg) System.err.println("in while ");
                    int j = 1;
                    plSedphyCount[i] = (int)rset.getFloat(j++);
                } // while (rset.next()) {

                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

                sql = "select count(*) from tisphy " +
                      "where station_id = '" + plStationId[i] + "'";
                if (dbg) System.out.println("<br> sql "+sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                while (rset.next()) {
                    //if (dbg) System.err.println("in while ");
                    int j = 1;
                    plTisphyCount[i] = (int)rset.getFloat(j++);
                } // while (rset.next()) {

                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

            } // for int(i = 0; i < numRecs; i++)

        } catch (Exception e) {
            System.out.println("Could not process list_stations: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // getListStations


    /**
     * Routine to get all the position info for a specific surveyId
     * @param pSurveyId The surveyId to get the record count for
     */
    public void getListPositions(String pSurveyId) {

        try {

            String sql = "select positions.code, pi_code, positions.type_code, " +
                  "name, latitude, longitude, remarks " +
                  "from positions, data_types " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "and   data_types.code (+) =  positions.type_code " +
                  "order by upper(pi_code)";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            int i = 0;
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                int positionsCode   = (int)rset.getFloat(j++);
                pPiCodeP       [i]  = sc.spaceIfNull(rset.getString(j++));
                pTypeCodeP     [i]  = sc.spaceIfNull(rset.getString(j++));
                pTypeP         [i]  = sc.spaceIfNull(rset.getString(j++));
                pLatitude      [i]  = sc.spaceIfNull(rset.getString(j++));
                pLongitude     [i]  = sc.spaceIfNull(rset.getString(j++));
                pRemarksP      [i]  = sc.spaceIfNull(rset.getString(j++));
                if (dbg) System.out.println("<br>pPiCodeP[i] = " + pPiCodeP[i]);

                i++;
            } //while (rset.next()) {
            int numRecs = i;

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.out.println("Could not process list_positions: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // getListPositions


    /**
     * Routine to get all the sample info for a specific surveyId
     * @param pSurveyId The surveyId to get the record count for
     */
    public void getListSamples(String pSurveyId) {

        try {

            String sql = "select samples.code, pi_code, sample_number, sample_units, " +
                  "samples.type_code, name, remarks " +
                  "from samples, data_types " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "and   data_types.code (+)    = samples.type_code " +
                  "order by upper(pi_code)";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            int i = 0;
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                int samplesCode   = (int)rset.getFloat(j++);
                pPiCodeS     [i]  = sc.spaceIfNull(rset.getString(j++));
                pSampleNumber[i]  = (int)rset.getFloat(j++);
                pSampleUnit  [i]  = sc.spaceIfNull(rset.getString(j++));
                pTypeCodeS   [i]  = sc.spaceIfNull(rset.getString(j++));
                pTypeS       [i]  = sc.spaceIfNull(rset.getString(j++));
                pRemarksS    [i]  = sc.spaceIfNull(rset.getString(j++));
                if (dbg) System.out.println("<br>pPiCodeS[i] = " + pPiCodeS[i]);

                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.out.println("Could not process list_samples: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // getListSamples


    /**
     * Routine to get all the zone info for a specific surveyId
     * @param pSurveyId The surveyId to get the record count for
     */
    public void getListZones(String pSurveyId) {

        try {

            String sql = "select marine_zone " +
                  "from zones_covered, marine_zones " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "and zones_covered.zone_code (+) = marine_zones.code";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            int i = 0;
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pMarineZone[i]  = sc.spaceIfNull(rset.getString(j++));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.out.println("Could not process list_zones: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // getListZones


    /**
     * Routine to get all the zone info for a specific surveyId
     * @param pSurveyId The surveyId to get the record count for
     */
    public void getPsdData(String pSurveyId) {

        try {

            String sql = "select projection_code, spheroid_code, datum_code " +
                  "from inventory " +
                  "where survey_id = '" + pSurveyId + "'";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int     pProjectionCode   = 0;
            int     pSpheroidCode     = 0;
            int     pDatumCode        = 0;
            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pProjectionCode = (int)rset.getFloat(j++);
                pSpheroidCode   = (int)rset.getFloat(j++);
                pDatumCode      = (int)rset.getFloat(j++);
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = "select name from projection " +
                  "where code = " + pProjectionCode;
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pProjection = sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            sql = "select name from spheroid " +
                  "where code = " + pSpheroidCode;
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pSpheroid = sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            sql = "select name from datum " +
                  "where code = " + pDatumCode;
            if (dbg) System.out.println("<br> sql "+sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pDatum = sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.out.println("Could not process get_psd_data: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // getPsdData


    /**
     * Format a string for output (left justified), e.g. frm ('abc',5) will
     * return 'abc  '
     * @param  value  the value to be formatted
     * @param  width  the total width of the formatted string,
     * @return  the int in the requested format,
     */
    public String frm (String value, int width) {
        String frmFiller = " ";
        if (dbg) System.out.print ("String: " + value + ":" + width + "*");   // debug
        String svalue = value.trim();
        if (svalue.length() > width) svalue = svalue.substring(0,width);
        String rvalue = svalue;
        if (dbg) System.out.print (svalue.length() + "*");   // debug
        if (width > svalue.length())
            for (int i=0; i<width-svalue.length(); i++) rvalue = rvalue + frmFiller;
        else rvalue = rvalue.substring(0,width);
        if (dbg) System.out.println (svalue + "*" + rvalue + "*");   // debug
        return rvalue;
    }   // frm (String value


//    public String sc.spaceIfNull(String text) {
//        return (text == null ? "" : text);
//    } // sc.spaceIfNull


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {
        try {
            PrintSurvsum local= new PrintSurvsum(args);
        } catch(Exception e) {
            //ec.processErrorStatic(e, "PrintSurvsum", "Constructor", "");
        } // try-catch

        // close the connection to the database
        //common2.DbAccessC.close();

    } // main

}  //  public class Pp
