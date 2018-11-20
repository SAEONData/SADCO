package survsum;

import oracle.html.*;
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
 */
public class DisplaySurvsum extends CompoundItem  {

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
    String  plHours        [];

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
    String  pZoneCode      [];
    String  pMarineZone    [];

    // getPsdData
    String  pProjection       = "";
    String  pSpheroid         = "";
    String  pDatum            = "";


    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public DisplaySurvsum(String args[]) {

        // +------------------------------------------------------------+
        // | The SADCO logo                                             |
        // +------------------------------------------------------------+
        Image slogo = new Image
            ("http://sadco.int.ocean.gov.za/sadco-img/sadlogo.gif",
            "sadlog.gif",
            IVAlign.TOP, false);
        //this.addItem(slogo.setCenter()); //print later, only if survsum system

        // +------------------------------------------------------------+
        // | Prepare page title                                         |
        // +------------------------------------------------------------+
//        bd.addItem(new SadInvHeader("Display Survey Information"));

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
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String pSurveyId = sc.getArgument(args,"surveyid");
        String passCode  = sc.getArgument(args,"passcode");
        String sessionCode  = sc.getArgument(args,"psc");
        if (dbg) System.out.println(thisClass + ":SId=" + pSurveyId);

        // only add logo if it comes from the survsum system
        if (passCode.equals("postgres")) {
            this.addItem(slogo.setCenter());
        } // if (passCode.equals("ter91qes"))

        // +------------------------------------------------------------+
        // | Are there any input parameters                             |
        // +------------------------------------------------------------+
        if (args.length < 2) {
            this.addItem(new SimpleItem("There are no input parameters"));
            return;
        }  //  if (args.length < 2)

        // not a valid survey id
        if (pSurveyId.length() < 7) {
            this.addItem(new SimpleItem("Invalid surveyId: " + pSurveyId));
            return;
        } // if (pSurveyId.length().length < 7)

        // +------------------------------------------------------------+
        // | Load the JDBC driver and connect to the database           |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass, sessionCode);

        // do some hacking to get the survey id right when called from sadinv
        if (dbg) System.out.println("<br>pSurveyId1 = " + pSurveyId);
        int len = pSurveyId.length();
        if (dbg) System.out.println("<br>pSurveyId2 = " + pSurveyId);
        pSurveyId = pSurveyId.substring(0,4) + "/" + pSurveyId.substring(len-4,len);
        System.out.println(thisClass + ":SId=" + pSurveyId);

        // +------------------------------------+
        // | Count the number position records  |
        // +------------------------------------+
        //try {
        //    pRecordcount = sadsql.count_positions(pSurveyId);
        //} catch (ServerException e) {
        //    bd.addItem(new SimpleItem("Could not process count_positions: " + e.getSqlerrm() ));
        //    hp.print();
        //    return;
        //}  //  catch (ServerException e)
        int positionCount = getRecordCount("positions", pSurveyId);

        // +------------------------------------+
        // | Count the number batch    records  |
        // +------------------------------------+
        //try {
        //    pRecordcount = sadsql.count_sampling_batches(pSurveyId);
        //} catch (ServerException e) {
        //    bd.addItem(new SimpleItem("Could not process count_sampling_batches: " + e.getSqlerrm() ));
        //    hp.print();
        //    return;
        //}  //  catch (ServerException e)
        int batchCount = getRecordCount("sampling_batch", pSurveyId);

        // +------------------------------------+
        // | Count the number station  records  |
        // +------------------------------------+
        //try {
        //    pRecordcount = sadsql.count_stations(pSurveyId);
        //} catch (ServerException e) {
        //    bd.addItem(new SimpleItem("Could not process count_stations: " + e.getSqlerrm() ));
        //    hp.print();
        //    return;
        //}  //  catch (ServerException e)
        int stationCount = getRecordCount("station", pSurveyId);

        // +---------------------------------+
        // | Count the number sample records |
        // +---------------------------------+
        //try {
        //    pRecordcount = sadsql.count_samples(pSurveyId);
        //} catch (ServerException e) {
        //    bd.addItem(new SimpleItem("Could not process count_samples: " + e.getSqlerrm() ));
        //    hp.print();
        //    return;
        //}  //  catch (ServerException e)
        int sampleCount = getRecordCount("samples", pSurveyId);

        // +--------------------------------+
        // | Count the marine zones records |
        // +--------------------------------+
        //try {
        //    pRecordcount = sadsql.count_zones(pSurveyId);
        //} catch (ServerException e) {
        //    bd.addItem(new SimpleItem("Could not process count_zones: " + e.getSqlerrm() ));
        //    hp.print();
        //    return;
        //}  //  catch (ServerException e)
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
        plHours        = new String[batchCount];

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
        pZoneCode      = new String[zoneCount];
        pMarineZone    = new String[zoneCount];


        // +-----------------+
        // | Batch    arrays |
        // +-----------------+
        for (int i = 0; i < batchCount; i++) {
            plBatchId       [i]    = "";
            plStartDateTime [i]    = "";
            plEndDateTime   [i]    = "";
            plHours         [i]    = "";
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
        //try {
        //    sadsql.display_survey_full(
        //        pSurveyId      ,
        //        pDataCentre    ,
        //        pProjectName   ,
        //        pCruiseName    ,
        //        pPlanamName    ,
        //        pPlatformName  ,
        //        pCallSign      ,
        //        pCountryName   ,
        //        pSurname1      ,
        //        pFName1        ,
        //        pTitle1        ,
        //        pSurname2      ,
        //        pFName2        ,
        //        pTitle2        ,
        //        pInstitName    ,
        //        pInstitAddress ,
        //        pInstitName1   ,
        //        pInstitAddress1,
        //        pInstitName2   ,
        //        pInstitAddress2,
        //        pInstitNameC   ,
        //        pInstitAddressC,
        //        pDateStart     ,
        //        pDateEnd       ,
        //        pGMTdiff       ,
        //        pLatNorth      ,
        //        pLatSouth      ,
        //        pLongWest      ,
        //        pLongEast      ,
        //        pTrackChart    ,
        //        pTargetCountryName   ,
        //        pDataRecorded  ,
        //        pNationalPgm      ,
        //        pExchangeRestrict ,
        //        pCoopPgm          ,
        //        pCoordinatedInt   ,
        //        pPortStart        ,
        //        pPortEnd
        //     );
        //} catch (ServerException e) {
        //    bd.addItem(new SimpleItem("Could not process display_survey: " + e.getSqlerrm() ));
        //    hp.print();
        //    return;
        //}  //  catch (ServerException e)

        // Get data_center, exchange_restrict, survey_type code from Inventory.
        //try {
        //    sql = " select name "+
        //          " from inventory a, survey_type b "+
        //          " where survey_id = '"+pSurveyId+"' and "+
        //          " survey_type_code = b.code";
        //
        //     // join om code en name tekry
        //
        //    stmt = conn.createStatement();
        //    rset = stmt.executeQuery(sql);
        //
        //    while (rset.next()) {
        //
        //        pSurveyTypeName   = new String(rset.getString(1));
        //
        //        if (dbg) System.err.println("<br> pSurveyTypeName "+
        //            pSurveyTypeName);
        //
        //    } //while (rset.next()) {
        //
        //    rset.close();
        //    stmt.close();
        //
        //} catch (Exception e) {
        //    System.err.println("<br> sql "+sql+":"+e.getMessage());
        //    e.printStackTrace();
        //} // try {
        getDisplaySurveyFull(pSurveyId);

        // +------------------+
        // | Get survey_notes |
        // +------------------+
        //try {
        //    sadsql.display_survey_notes(
        //        pSurveyId      ,
        //        pLine1         ,
        //        pLine2         ,
        //        pLine3         ,
        //        pLine4         ,
        //        pLine5         ,
        //        pLine6         ,
        //        pLine7         ,
        //        pLine8         ,
        //        pLine9         ,
        //        pLine10
        //    );
        //} catch (ServerException e) {
        //}  //  catch (ServerException e)
        getDisplaySurveyNotes(pSurveyId);

        // +------------------------------------------+
        // | Get a listing of Principal Investigators |
        // +------------------------------------------+
        //try {
        //    sadsql.list_investigators(
        //        pSurveyId      ,
        //        pSciCodeI     ,
        //        pSurnameI     ,
        //        pFNameI       ,
        //        pTitleI       ,
        //        pPiCode       ,
        //        pInstitNameI  ,
        //        pAddressI
        //    );
        //} catch (ServerException e) {
        //    bd.addItem(new SimpleItem("Could not process list_investigators: " + e.getSqlerrm() ));
        //    hp.print();
        //    return;
        //}  //  catch (ServerException e)
        getListInvestigators(pSurveyId);

        if (batchCount > 0) {
            // +----------------------+
            // | Get sampling Batches |
            // +----------------------+
            //try {
            //    sadsql.list_sampling_batches(
            //        pSurveyId      ,
            //        plBatchCode    ,
            //        plBatchId      ,
            //        plStartDateTime,
            //        plEndDateTime  ,
            //        plHours
            //    );
            //} catch (ServerException e) {
            //    bd.addItem(new SimpleItem("Could not process list_sampling_batches: " + e.getSqlerrm() ));
            //    hp.print();
            //    return;
            //}  //  catch (ServerException e)
            getListSamplingBatches(pSurveyId);
        }  //  if (batchCount > 0)


        if (stationCount > 0) {
            // +----------------------+
            // | Get stations         |
            // +----------------------+
            //try {
            //    sadsql.list_stations(
            //        pSurveyId      ,
            //        plStationId    ,
            //        plStnnam       ,
            //        plLatitude     ,
            //        plLongitude    ,
            //        plStndep       ,
            //        plMaxSpldep    ,
            //        plLandTest     ,
            //        plFlaggingName ,
            //        plQualityName  ,
            //        plWatphyCount  ,
            //        plSedphyCount  ,
            //        plTisphyCount
            //    );
            //} catch (ServerException e) {
            //    bd.addItem(new SimpleItem("Could not process list_stations: " + e.getSqlerrm() ));
            //    hp.print();
            //    return;
            //}  //  catch (ServerException e)
            getListStations(pSurveyId);
        }  //  if (stationCount > 0)


        // +---------------+
        // | Get positions |
        // +---------------+
        if (positionCount > 0) {
            //try {
            //    sadsql.list_positions(
            //        pSurveyId      ,
            //        pPositionsCodeP,
            //        pPiCodeP       ,
            //        pTypeCodeP     ,
            //        pTypeP         ,
            //        pLatitude      ,
            //        pLongitude     ,
            //        pRemarksP
            //    );
            //} catch (ServerException e) {}
            getListPositions(pSurveyId);
        } //  if (positionCount > 0)

        if (sampleCount > 0) {
            // +-------------+
            // | Get samples |
            // +-------------+
            //try {
            //    sadsql.list_samples(
            //        pSurveyId      ,
            //        pSamplesCodeS  ,
            //        pPiCodeS       ,
            //        pSampleNumber  ,
            //        pSampleUnit    ,
            //        pTypeCodeS     ,
            //        pTypeS         ,
            //        pRemarksS
            //    );
            //} catch (ServerException e) {}
            getListSamples(pSurveyId);
        }  //  if (sampleCount > 0)

        if (zoneCount > 0) {
            // +-----------+
            // | Get zones |
            // +-----------+
            //try {
            //    sadsql.list_zones(
            //        pSurveyId      ,
            //        pMarineZone
            //    );
            //} catch (ServerException e) {}
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
        //try {
        //    sadsql.get_psd_data(
        //        pSurveyId      ,
        //        pProjectionCode,
        //        pProjection    ,
        //        pSpheroidCode  ,
        //        pSpheroid      ,
        //        pDatumCode     ,
        //        pDatum
        //    );
        //} catch (ServerException e) {
        //    bd.addItem(new SimpleItem("Could not process get_psd_data: " + e.getSqlerrm() ));
        //    hp.print();
        //    return;
        //}  //  catch (ServerException e)
        getPsdData(pSurveyId);

        DynamicTable tab_main  = new DynamicTable(1);
        tab_main
            .setBorder(1)
            .setCellSpacing(1)
            .setWidth("100%")
            .setBackgroundColor("#33CCFF")
        ;
        DynamicTable tab_titl  = createNewDynamicTable(2,0);
        DynamicTable tab_data  = createNewDynamicTable(2,0);
        DynamicTable tab_plat  = createNewDynamicTable(2,0);
        DynamicTable tab_name  = createNewDynamicTable(1,0);
        DynamicTable tab_peri  = createNewDynamicTable(2,0);
        DynamicTable tab_samb  = createNewDynamicTable(2,0);
        DynamicTable tab_samb1 = createNewDynamicTable(4,1);
        DynamicTable tab_stns  = createNewDynamicTable(2,0);
        DynamicTable tab_stns1 = createNewDynamicTable(4,1);
        DynamicTable tab_labo  = createNewDynamicTable(1,0);
        DynamicTable tab_csci  = createNewDynamicTable(1,0);
        DynamicTable tab_narr  = createNewDynamicTable(1,0);
        DynamicTable tab_proj  = createNewDynamicTable(1,0);
        DynamicTable tab_pinv  = createNewDynamicTable(1,0);
        DynamicTable tab_moor  = createNewDynamicTable(1,0);
        DynamicTable tab_moor1 = createNewDynamicTable(5,1);
        DynamicTable tab_samp  = createNewDynamicTable(1,0);
        DynamicTable tab_samp1 = createNewDynamicTable(5,1);
        DynamicTable tab_trac  = createNewDynamicTable(1,0);
        DynamicTable tab_ocar  = createNewDynamicTable(1,0);
        DynamicTable tab_zone  = createNewDynamicTable(1,0);
        DynamicTable tab_tcnt  = createNewDynamicTable(1,0);
        DynamicTable tab_psd   = createNewDynamicTable(1,0);

        // +-------+
        // | Title |
        // +-------+
        TableRow rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>SURVEY SUMMARY REPORT</b>")).setColSpan(80))
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>" + pSurveyId + "</b")))
        ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------+
        // | Data |
        // +------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (dbg) System.out.println("<br>passCode = " + passCode);
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Data</u></b>")));
            if (dbg) System.out.println("<br>if then = " + passCode);
        } else {
            String linkData = new String("SurvSum?pscreen=UpdateDataBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkData, new SimpleItem("<b><u>Data</u></b>"))));
            if (dbg) System.out.println("<br>if else = " + passCode);
        }  //  if (!passCode.equals("ter91qes"))
        tab_data.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                //"&nbsp;&nbsp;&nbsp;&nbsp;Date Centre:<b>" + pDataCentre  +
                "&nbsp;&nbsp;Date Centre:<b>" + pDataCentre  +
                "</b><br>&nbsp;&nbsp;Data Exchange:<b>" + pExchangeRestrict
                //(pExchangeRestrict == null ? "" : pExchangeRestrict)
            )))
            // Add line to display survey_type
            .addCell(new TableDataCell(sc.formatStr("Survey Type: <b>" +
                pSurveyTypeName +"</b>"
            )))
        ;
        tab_data.addRow(rows.setIVAlign(IVAlign.TOP));


        // +----------+
        // | Platform |
        // +----------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Platform</u></b>")));
        } else {
            String linkPlatform = new String("SurvSum?pscreen=UpdatePlatformBlock&surveyid=" + pSurveyId);
             rows.addCell(new TableDataCell(new Link(linkPlatform, new SimpleItem("<b><u>Platform</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_plat.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;Name: <b>" + pPlanamName +
                "</b><br>&nbsp;&nbsp;Type: <b>" + pPlatformName + "</b>"
            )))
            .addCell(new TableDataCell(sc.formatStr(
                "Call Sign: <b>" + pCallSign  + "</b>"
            )))
        ;
        tab_plat.addRow(rows.setIVAlign(IVAlign.TOP));


        // +-----------------+
        // | Survey No./Name |
        // +-----------------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Survey No./Name</u></b>")).setColSpan(80));
        } else {
            String linkSurvnam = new String("SurvSum?pscreen=UpdateSurvNamBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkSurvnam, new SimpleItem("<b><u>Survey No./Name</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_name.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;<b>" + (pCruiseName == null ? "" : pCruiseName) + "</b>"
            )))
        ;
        tab_name.addRow(rows.setIVAlign(IVAlign.TOP));


        // +---------+
        // | Periods |
        // +---------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Periods</u></b>")).setColSpan(80));
        } else {
            String linkPeriods = new String("SurvSum?pscreen=UpdatePeriodsBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkPeriods, new SimpleItem("<b><u>Periods</u>*</b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_peri.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;Dates From: <b>" + pDateStart +
                "<br></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To: <b>" +
                pDateEnd +
                "<br></b>&nbsp;&nbsp;Hours from/to GMT : <b>" + pGMTdiff + "</b>"
            )))
            .addCell(new TableDataCell(sc.formatStr(
                "Departing From: <b>" + pPortStart +
                "<br></b>&nbsp;&nbsp;&nbsp;&nbsp;Returning To: <b>" + pPortEnd + "</b>"
            )))
        ;
        tab_peri.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------+
        // | Sampling Batches |
        // +------------------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Sampling Batches</u></b>")).setColSpan(80));
        } else {
            String linkSamplingBatches = new String("SurvSum?pscreen=UpdateSamplingBatches&surveyid=" + pSurveyId + "&noend=Y");
            rows.addCell(new TableDataCell(new Link(linkSamplingBatches, new SimpleItem("<b><u>SamplingBatches</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_samb.addRow(rows);

        // +------------------------------------------------------------+
        // | Only process if there are records                          |
        // +------------------------------------------------------------+
        if (batchCount > 0) {
            rows = new TableRow();
            rows
                .addCell(new TableDataCell(sc.formatStrBig("Batch Id")))
                .addCell(new TableDataCell(sc.formatStrBig("Start")))
                .addCell(new TableDataCell(sc.formatStrBig("End")))
                .addCell(new TableDataCell(sc.formatStrBig("Hours")))
            ;
            tab_samb1.addRow(rows);

            // +------------------------------------------------------------+
            // | Create a string describing the GMT value                   |
            // +------------------------------------------------------------+
            String         GMTstring;
            if (pGMTdiff >= 0) {
                GMTstring = "&nbsp;&nbsp;(GMT +" + pGMTdiff + ")";
            } else {
                GMTstring = "&nbsp;&nbsp;(GMT " + pGMTdiff + ")";
            }  //  if (GTMdiff.intValue() >= 0)


            // +------------------------------------------------------------+
            // | Process each batch     record                              |
            // +------------------------------------------------------------+
            for (int i = 0; i < batchCount; i++) {
                int recnum = i + 1;
                rows = new TableRow();
                rows
                    .addCell(new TableDataCell(sc.formatStr("<b><FONT COLOR=#3333FF>" + plBatchId[i] + "</font></b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plStartDateTime[i] + GMTstring + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plEndDateTime[i] + GMTstring + "</b>" )))
                ;
//                if (plHours[i] <= 0.0) {
//                    rows.addCell(new TableDataCell(sc.formatStr("<b><FONT COLOR=#FF0000>" + plHours[i] + " - ERROR</font></b>" )));
//                } else {
                    rows.addCell(new TableDataCell(sc.formatStr("<b>" + plHours[i] + "</b>" )));
//                }  //  if (plHours <= 0.0)

               //;
                tab_samb1.addRow(rows.setIVAlign(IVAlign.TOP));
            }  //  for (int i = 0; i < batchCount; i++)

            rows = new TableRow();
            rows.addCell(new TableDataCell(tab_samb1).setVAlign(IVAlign.TOP));
            tab_samb.addRow(rows);
        }  //  if (batchCount > 0)



        // +------------------+
        // | Stations         |
        // +------------------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Sampling Stations</u></b>")).setColSpan(80));
        } else {
            String linkStations = new String("SurvSum?pscreen=UpdateStationsBlock&surveyid=" + pSurveyId + "&noend=Y");
            rows.addCell(new TableDataCell(new Link(linkStations, new SimpleItem("<b><u>Sampling Stations</u>*</b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_stns.addRow(rows);

        // +------------------------------------------------------------+
        // | Only process if there are records                          |
        // +------------------------------------------------------------+
        if (stationCount > 0) {
            rows = new TableRow();
            rows
                .addCell(new TableDataCell(sc.formatStrBig("Station Id")))
                .addCell(new TableDataCell(sc.formatStrBig("Station<br>Name")))
                .addCell(new TableDataCell(sc.formatStrBig("Latitude")))
                .addCell(new TableDataCell(sc.formatStrBig("Longitude")))
                .addCell(new TableDataCell(sc.formatStrBig("")))
                .addCell(new TableDataCell(sc.formatStrBig("Station<br>Depth(m)")))
                .addCell(new TableDataCell(sc.formatStrBig("Max Sampling<br>Depth(m)")))
                .addCell(new TableDataCell(sc.formatStrBig("Data Quality")))
                .addCell(new TableDataCell(sc.formatStrBig("Water<br>Samples")))
                .addCell(new TableDataCell(sc.formatStrBig("Sediment<br>Samples")))
                .addCell(new TableDataCell(sc.formatStrBig("Biological<br>Samples")))
            ;
            tab_stns1.addRow(rows);

            // +------------------------------------------------------------+
            // | Process each station   record                              |
            // +------------------------------------------------------------+
            for (int i = 0; i < stationCount; i++) {
                int recnum = i + 1;
                rows = new TableRow();
                rows
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plStationId[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plStnnam[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plLatitude[i]+ "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plLongitude[i] + "</b>" )))
                ;


                // +------------------------------------------------------------+
                // | If the position has beed detected to be over the land mass |
                // | add a warning tag to the displayed record                  |
                // +------------------------------------------------------------+
                if(plLandTest[i] > 1) {
                    rows.addCell(new TableDataCell(sc.formatStr("<b><FONT COLOR=#FF6666>Over Land</font></b>" )));
                } else {
                    rows.addCell(new TableDataCell(sc.formatStr("<b> </b>")));
                }  //  if(pLandTest.equals("1"))

                rows
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plStndep[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plMaxSpldep[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plFlaggingName[i] + " - " + plQualityName[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plWatphyCount[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plSedphyCount[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plTisphyCount[i] + "</b>" )))
                ;
                tab_stns1.addRow(rows.setIVAlign(IVAlign.TOP));
            }  //  for (int i = 0; i < stationCount; i++)

            rows = new TableRow();
            rows.addCell(new TableDataCell(tab_stns1).setVAlign(IVAlign.TOP));
            tab_stns.addRow(rows);
        }  //  if (stationCount > 0)


        // +------------------------+
        // | Responsible Laboratory |
        // +------------------------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Responsible Laboratory</u></b>")).setColSpan(80));
        } else {
            String linkResLab = new String("SurvSum?pscreen=UpdateResLabBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkResLab, new SimpleItem("<b><u>Responsible Laboratory</u>*</b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_labo.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name: <b>" + pInstitName +
                "</b><br>&nbsp;&nbsp;Address: <b>" + pInstitAddress +
                "</b><br>&nbsp;&nbsp;Country: <b>" + pCountryName + "</b>"
            )))
        ;
        tab_labo.addRow(rows.setIVAlign(IVAlign.TOP));


        // +--------------------+
        // | Chief Scientist(s) |
        // +--------------------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Chief Scientist(s)</u>*</b>")).setColSpan(80));
        } else {
            String linkChiefSc = new String("SurvSum?pscreen=UpdateChiefScBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkChiefSc, new SimpleItem("<b><u>Chief Scientists</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_csci.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;1.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name: <b>" +
                pTitle1.trim() + " " + pFName1.trim() + " " + pSurname1 +
                "</b><br>&nbsp;&nbsp;Laboratory: <b>" + pInstitName1 +
                "</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address: <b>" +
                pInstitAddress1 + "</b>"
            )))
        ;
        tab_csci.addRow(rows.setIVAlign(IVAlign.TOP));

        if (!pSurname2.equals("unknown")) {
            rows = new TableRow();
            rows.addCell(new TableDataCell("&nbsp;"));
            tab_csci.addRow(rows);

            rows = new TableRow();
            rows
                .addCell(new TableDataCell(sc.formatStr(
                    "&nbsp;2.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name: <b>" +
                    pTitle2.trim() + " " + pFName2.trim() + " " + pSurname2 +
                    "</b><br>&nbsp;&nbsp;Laboratory: <b>" + pInstitName2 +
                    "</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address: <b>" +
                    pInstitAddress2 + "</b>"
                )))
            ;
            tab_csci.addRow(rows.setIVAlign(IVAlign.TOP));
        }  //  if (!pSurname.equals("unknown"))


        // +-------------------------------------------+
        // | Objectives and Brief Narrative of Cruise  |
        // +-------------------------------------------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(
                sc.formatStrBig("<b><u>Objectives and Brief Narrative of Cruise</u></b>")).setColSpan(80));
        } else {
            String linkObjects = new String("SurvSum?pscreen=UpdateObjectsBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(
                new Link(linkObjects, new SimpleItem("<b><u>Objectives and Brief Narrative of Cruise</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_narr.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp; <b>" +
                pLine1 + //" " +
                pLine2 + //" " +
                pLine3 + //" " +
                pLine4 + //" " +
                pLine5 + //" " +
                pLine6 + //" " +
                pLine7 + //" " +
                pLine8 + //" " +
                pLine9 + //" " +
                pLine10 + "</b>"
            )))
        ;
        tab_narr.addRow(rows.setIVAlign(IVAlign.TOP));



        // +---------+
        // | Project |
        // +---------+
        rows = new TableRow();
        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Project</u></b>")).setColSpan(80));
        } else {
            String linkProject = new String("SurvSum?pscreen=UpdateProjectBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkProject, new SimpleItem("<b><u>Project</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_proj.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name: <b>" +
                pProjectName +
                "</b><br>&nbsp;&nbsp;Co-ordin. Body: <b>" +
                pInstitNameC +
                "</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>" +
                pInstitAddressC + "</b>"
            )))
        ;
        tab_proj.addRow(rows.setIVAlign(IVAlign.TOP));


        // +-------------------------+
        // | Principal Investigators |
        // +-------------------------+
        rows = new TableRow();

        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig(
                "<b><u>Principal Investigators</u></b>")).setColSpan(80));
        } else {
            String linkPriInvs = new String("SurvSum?pscreen=UpdatePriInvsBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkPriInvs,
                new SimpleItem("<b><u>Principal Investigators</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_pinv.addRow(rows);

        // +---------------------------+
        // | Process each investigator |
        // +---------------------------+
        for (int i = 0; i < 12; i++) {
            // +----------------------------------------------------+
            // | Stop processing if there are no more investigators |
            // +----------------------------------------------------+
            if (pSurnameI[i].length() == 0) break;
            if (pSciCodeI[i] < 2) break;

            rows = new TableRow();
            rows
                .addCell(new TableDataCell(sc.formatStr(
                    "&nbsp;<FONT COLOR=#3333FF SIZE=+1>" +
                    pPiCode  [i] + ". </font><b>" +
                    pTitleI  [i].trim() + " " +
                    pFNameI  [i].trim() + " " +
                    pSurnameI[i] +
                    "</b><br>&nbsp;&nbsp;&nbsp;<b>" +
                    pInstitNameI[i] +
                    "</b><br>&nbsp;&nbsp;&nbsp;<b>" +
                    pAddressI[i] + "</b><br>&nbsp;"
                )))
            ;
            tab_pinv.addRow(rows.setIVAlign(IVAlign.TOP));
        }  //  for (int i = 0; i < 6; i++)


        // +------------------------------------------------------------+
        // | Mooring, Bottom Mounted Geer and Drifting Systems          |
        // +------------------------------------------------------------+
        rows = new TableRow();

        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Mooring, " +
                "Bottom Mounted Gear and Drifting Systems</u></b>")).setColSpan(80));
        } else {
            String linkMooring = new String("SurvSum?pscreen=UpdateMooringBlock&surveyid=" +
                pSurveyId + "&recnum=0");
            rows.addCell(new TableDataCell(new Link(linkMooring, new SimpleItem("<b><u>Mooring, Bottom Mounted Gear and Drifting Systems</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_moor.addRow(rows);

        // +------------------------------------------------------------+
        // | Only process if there are records                          |
        // +------------------------------------------------------------+
        if (positionCount > 0) {
            rows = new TableRow();
            rows
                .addCell(new TableDataCell(sc.formatStrBig("Invstgr<br>Code")))
                .addCell(new TableDataCell(sc.formatStrBig("Latitude")))
                .addCell(new TableDataCell(sc.formatStrBig("Longitude")))
                .addCell(new TableDataCell(sc.formatStrBig("Type<br>Code")))
                .addCell(new TableDataCell(sc.formatStrBig("Type")))
                .addCell(new TableDataCell(sc.formatStrBig("Remarks")))
            ;
            tab_moor1.addRow(rows);

            // +------------------------------------------------------------+
            // | Process each positions record                              |
            // +------------------------------------------------------------+
            for (int i = 0; i < positionCount; i++) {
                rows = new TableRow();
                rows
                    .addCell(new TableDataCell(sc.formatStr("<b><FONT COLOR=#3333FF>" +
                        pPiCodeP[i] + "</font></b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" +
                        pLatitude[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" +
                        pLongitude[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" +
                        pTypeCodeP[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" +
                        pTypeP[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" +
                        pRemarksP[i] + "</b>" )))
                ;
                tab_moor1.addRow(rows.setIVAlign(IVAlign.TOP));
            }  //  for (int i = 0; i < positionCount; i++)

            rows = new TableRow();
            rows.addCell(new TableDataCell(tab_moor1).setVAlign(IVAlign.TOP));
            tab_moor.addRow(rows);
        }  //  if (positionCount > 0)


        // +------------------------------------------------------------+
        // | Summary of Measurements and Samples Taken                  |
        // +------------------------------------------------------------+
        rows = new TableRow();

        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Summary of " +
                "Measurements and Samples Taken</u></b>")).setColSpan(80));
        } else {
            String linkSamples = new String("SurvSum?pscreen=UpdateSamplesBlock&surveyid=" +
                pSurveyId + "&recnum=0");
            rows.addCell(new TableDataCell(new Link(linkSamples, new SimpleItem(
                "<b><u>Summary of Measurements and Samples Taken</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_samp.addRow(rows);

        // +-----------------------------------+
        // | Only process if there are records |
        // +-----------------------------------+
        if (sampleCount > 0) {
            rows = new TableRow();
            rows
                .addCell(new TableDataCell(sc.formatStrBig("Invstgr<br>Code")))
                .addCell(new TableDataCell(sc.formatStrBig("Number")))
                .addCell(new TableDataCell(sc.formatStrBig("Units")))
                .addCell(new TableDataCell(sc.formatStrBig("Type<br>Code")))
                .addCell(new TableDataCell(sc.formatStrBig("Type")))
                .addCell(new TableDataCell(sc.formatStrBig("Remarks")))
            ;
            tab_samp1.addRow(rows);

            // +--------------------------------+
            // | Process each sample    record  |
            // +--------------------------------+
            for (int i = 0; i < sampleCount; i++) {
                rows = new TableRow();
                rows
                    .addCell(new TableDataCell(sc.formatStr("<b><FONT COLOR=#3333FF>" + pPiCodeS[i] + "</font></b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pSampleNumber[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pSampleUnit[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pTypeCodeS[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pTypeS[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pRemarksS[i] + "</b>" )))
                ;
                tab_samp1.addRow(rows.setIVAlign(IVAlign.TOP));
            }  //  for (int i = 0; i < sampleCount; i++)

            rows = new TableRow();
            rows.addCell(new TableDataCell(tab_samp1).setVAlign(IVAlign.TOP));
            tab_samp.addRow(rows);
        }  //  if (sampleCount > 0)


        // +-------------+
        // | Track Chart |
        // +-------------+
        rows = new TableRow();

        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig(
                "<b><u>Track Chart</u></b>")).setColSpan(80));
        } else {
            String linkTrackCh = new String("SurvSum?pscreen=UpdateTrackChBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkTrackCh,
                new SimpleItem("<b><u>Track Chart</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_trac.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;Has a track chart been submitted? <b>" +
                pTrackChart + "</b>"
            )))
        ;
        tab_trac.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | General Ocean Areas                                        |
        // +------------------------------------------------------------+
        rows = new TableRow();

        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig(
                "<b><u>General Ocean Areas</u></b>")).setColSpan(80));
        } else {
            String linkOcAreas = new String("SurvSum?pscreen=UpdateOcAreasBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkOcAreas, new SimpleItem(
                "<b><u>General Ocean Areas</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_ocar.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp; <b>" +
                oceanArea + "</b>"
            )))
        ;
        tab_ocar.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------+
        // | Specific Areas (Zones) |
        // +------------------------+
        rows = new TableRow();

        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Specific Areas (zones)</u></b>")).setColSpan(80));
        } else {
            String linkZones = new String("SurvSum?pscreen=UpdateZonesBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkZones, new SimpleItem("<b><u>Specific Areas (zones)</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_zone.addRow(rows);

        // +-----------------------------------+
        // | Only process if there are records |
        // +-----------------------------------+
        if (zoneCount > 0) {
            for (int i = 0; i < zoneCount; i++) {
                rows = new TableRow();
                rows
                    .addCell(new TableDataCell(sc.formatStr(
                        "&nbsp;&nbsp; <b>" +
                        pMarineZone[i] + "</b>"
                    )))
                ;
                tab_zone.addRow(rows.setIVAlign(IVAlign.TOP));

            }  //  for (int i = 0; i < zoneCount; i++)

        }  //  if (zoneCount > 0)


        // +------------------------+
        // | Target Country         |
        // +------------------------+
        rows = new TableRow();

        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig(
                "<b><u>Survey Country</u></b>")).setColSpan(80));
        } else {
            String linkTCountry = new String("SurvSum?pscreen=UpdateTCountryBlock&surveyid=" +
                pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkTCountry, new SimpleItem(
                "<b><u>Survey Country</u></b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_tcnt.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "</b>&nbsp;&nbsp;Country: <b>" +
                pTargetCountryName + "</b>"
            )))
        ;
        tab_tcnt.addRow(rows.setIVAlign(IVAlign.TOP));


        // +----------+
        // | PSD data |
        // +----------+
        rows = new TableRow();

        // +------------------------------------------------------------+
        // | Only have updating link if authorized                      |
        // +------------------------------------------------------------+
        if (!passCode.equals("ter91qes")) {
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>PSD Data</u></b>")));
        } else {
            String linkPSD = new String("SurvSum?pscreen=UpdatePSDdataBlock&surveyid=" + pSurveyId);
            rows.addCell(new TableDataCell(new Link(linkPSD, new SimpleItem(
                "<b><u>PSD Data</u>*</b>"))));
        }  //  if (!passCode.equals("ter91qes"))
        tab_psd.addRow(rows.setIVAlign(IVAlign.TOP));

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStr(
                "Projection: <b>" + pProjection +
                "<br></b>Spheroid: <b>" + pSpheroid +
                "<br></b>Datum: <b>" + pDatum +
                "</b>"
            )))
        ;
        tab_psd.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Decide which program to return to                          |
        // +------------------------------------------------------------+
        String prevProg = new String();
        String prevProgTitle = new String();
        if (!passCode.equals("ter91qes")) {
            //prevProg = new String("DisplaySurvey");
            //prevProg = new String("http://sadcoinv.csir.co.za/sadco1/SadInv");
            prevProg = new String("http://sadcoinv.ocean.gov.za/");
            prevProgTitle = new String(">>> Survey Listing");
        } else {
            prevProg = new String("SurvSum");
            prevProgTitle = new String("<<< Survey Summaries Listing");
        }  //  if (!passCode.equals("ter91qes"))

        // +------------------------------------------------------------+
        // | Create button to go to the Survey Summary form             |
        // +------------------------------------------------------------+
        Form survButton = new Form("GET", prevProg);
        survButton.addItem(new Submit("", prevProgTitle));

        if (!passCode.equals("ter91qes")) {
            survButton.addItem(new Hidden("pscreen", "displaysurvey"));
            //survButton.addItem(new Hidden("pscreen", "InvListSurveyId"));
            survButton.addItem(new Hidden("psurveyid", pSurveyId));
            survButton.addItem(new Hidden("psc", sessionCode));
        } else {
            survButton.addItem(new Hidden("exisurveyid", pSurveyId));
            //survButton.addItem(new Hidden("pscreen", "DisplaySurvsumList"));
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

        this.addItem(tab_main .setCenter() );


        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
        try {
            if (dbg) System.err.println("Before conn.close()");
            sc.disConnect(stmt, rset, conn);
            if (dbg) System.err.println("After conn.close()");
        }  catch (Exception e){}

    }  // DisplaySurvsum


    /**
     * Routine to get all the record counts of various tables for a specific surveyId
     * @param table The table to get the record count for
     * @param pSurveyId The surveyId to get the record count for
     * @return the recordcount
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
            this.addItem("Could not process count_" + table + ": " +
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
                pSurname1      = sc.spaceIfNull(rset.getString(j++));
                pFName1        = sc.spaceIfNull(rset.getString(j++));
                pTitle1        = sc.spaceIfNull(rset.getString(j++));
                pInstitName1   = sc.spaceIfNull(rset.getString(j++));
                pInstitAddress1= sc.spaceIfNull(rset.getString(j++));
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
                pSurname2      = sc.spaceIfNull(rset.getString(j++));
                pFName2        = sc.spaceIfNull(rset.getString(j++));
                pTitle2        = sc.spaceIfNull(rset.getString(j++));
                pInstitName2   = sc.spaceIfNull(rset.getString(j++));
                pInstitAddress2= sc.spaceIfNull(rset.getString(j++));
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
            this.addItem("Could not process getDisplaySurveyFull: " +
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
                pLine1         = sc.spaceIfNull(rset.getString(j++));
                pLine2         = sc.spaceIfNull(rset.getString(j++));
                pLine3         = sc.spaceIfNull(rset.getString(j++));
                pLine4         = sc.spaceIfNull(rset.getString(j++));
                pLine5         = sc.spaceIfNull(rset.getString(j++));
                pLine6         = sc.spaceIfNull(rset.getString(j++));
                pLine7         = sc.spaceIfNull(rset.getString(j++));
                pLine8         = sc.spaceIfNull(rset.getString(j++));
                pLine9         = sc.spaceIfNull(rset.getString(j++));
                pLine10        = sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next()) {

            rset.close();
            stmt.close();
        } catch (Exception e) {
            this.addItem("Could not process getDisplaySurveyNotes: " +
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
                pSurnameI   [i]  = sc.spaceIfNull(rset.getString(j++));
                pFNameI     [i]  = sc.spaceIfNull(rset.getString(j++));
                pTitleI     [i]  = sc.spaceIfNull(rset.getString(j++));
                pPiCode     [i]  = sc.spaceIfNull(rset.getString(j++));
                pInstitNameI[i]  = sc.spaceIfNull(rset.getString(j++));
                pAddressI   [i]  = sc.spaceIfNull(rset.getString(j++));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
        } catch (Exception e) {
            this.addItem("Could not process getListInvestigators: " +
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
//                plHours        [i]= (int)rset.getFloat(j++);
                plHours        [i]= sc.spaceIfNull(rset.getString(j++));
                i++;
            } //while (rset.next()) {
            int numRecs = i;

            rset.close();
            stmt.close();

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
            this.addItem("Could not process getListSamplingBatches: " +
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

            // select the basic information for the station
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

            // for each station, test whether overland and get number of detail records
            for (i = 0; i < numRecs; i++) {

                // get overland stuff
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

                // get the number of watphy records
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

                // get the number of sedphy records
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

                // get the number of tisphy records
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

            } // for int(i = 0; i < numRecs; i++)

        } catch (Exception e) {
            this.addItem("Could not process getListStations: " +
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

            //String sql = "select positions.code, pi_code, positions.type_code, " +
            //      "name, latitude, longitude, remarks " +
            //      "from positions, data_types " +
            //      "where survey_id = '" + pSurveyId + "' " +
            //      "and   data_types.code (+) =  positions.type_code " +
            //      "order by upper(pi_code)";
            String sql = "select code, pi_code, type_code, " +
                  "latitude, longitude, remarks " +
                  "from positions " +
                  "where survey_id = '" + pSurveyId + "' " +
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
                pLatitude      [i]  = sc.spaceIfNull(rset.getString(j++));
                pLongitude     [i]  = sc.spaceIfNull(rset.getString(j++));
                pRemarksP      [i]  = sc.spaceIfNull(rset.getString(j++));
                if (dbg) System.out.println("<br>pPiCodeP[i] = " + pPiCodeP[i]);
                pTypeP         [i]  = "";

                i++;
            } //while (rset.next()) {
            int numRecs = i;
            rset.close();
            stmt.close();

            for (i = 0; i < numRecs; i++) {
                sql = "select name from data_types " +
                      "where data_types.code =  '" + pTypeCodeP[i] + "'";
                if (dbg) System.out.println("<br> sql "+sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                //if (dbg) System.err.println("before while ");
                while (rset.next()) {
                    pTypeP[i] = sc.spaceIfNull(rset.getString(1));;
                } //while (rset.next()) {

                rset.close();
                stmt.close();

            } // for (int i = 0; i < numRecs; i++)

        } catch (Exception e) {
            this.addItem("Could not process getListPositions: " +
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

            //String sql = "select samples.code, pi_code, sample_number, sample_units, " +
            //      "samples.type_code, name, remarks " +
            //      "from samples, data_types " +
            //      "where survey_id = '" + pSurveyId + "' " +
            //      "and   data_types.code (+)    = samples.type_code " +
            //      "order by upper(pi_code)";
            String sql = "select code, pi_code, sample_number, sample_units, " +
                  "type_code, remarks " +
                  "from samples " +
                  "where survey_id = '" + pSurveyId + "' " +
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
                //pTypeS       [i]  = sc.spaceIfNull(rset.getString(j++));
                pRemarksS    [i]  = sc.spaceIfNull(rset.getString(j++));
                if (dbg) System.out.println("<br>pPiCodeS[i] = " + pPiCodeS[i]);
                pTypeS       [i]  = "";

                i++;
            } //while (rset.next()) {
            int numRecs = i;

            rset.close();
            stmt.close();

            for (i = 0; i < numRecs; i++) {
                sql = "select name from data_types " +
                      "where data_types.code =  '" + pTypeCodeS[i] + "'";;
                if (dbg) System.out.println("<br> sql "+sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                //if (dbg) System.err.println("before while ");
                while (rset.next()) {
                    pTypeS[i] = sc.spaceIfNull(rset.getString(1));;
                } //while (rset.next()) {

                rset.close();
                stmt.close();

            } // for (int i = 0; i < numRecs; i++)

        } catch (Exception e) {
            this.addItem("Could not process getListSamples: " +
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

            //String sql = "select marine_zone " +
            //      "from zones_covered, marine_zones " +
            //      "where survey_id = '" + pSurveyId + "' " +
            //      "and zones_covered.zone_code (+) = marine_zones.code";
            String sql = "select zone_code " +
                  "from zones_covered " +
                  "where survey_id = '" + pSurveyId + "' ";
            if (dbg) System.out.println("<br> sql "+sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            int i = 0;
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pZoneCode[i]  = sc.spaceIfNull(rset.getString(j++));
                i++;
            } //while (rset.next()) {
            int numRecs = i;

            rset.close();
            stmt.close();

            for (i = 0; i < numRecs; i++) {
                sql = "select marine_zone from marine_zones " +
                      "where code =  " + pZoneCode[i];
                if (dbg) System.out.println("<br> sql "+sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                //if (dbg) System.err.println("before while ");
                while (rset.next()) {
                    pMarineZone[i] = sc.spaceIfNull(rset.getString(1));;
                } //while (rset.next()) {

                rset.close();
                stmt.close();

            } // for (int i = 0; i < numRecs; i++)

        } catch (Exception e) {
            this.addItem("Could not process getListZones: " +
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

            String sql = "select projection.name, spheroid.name, datum.name " +
                  "from inventory, projection, spheroid, datum " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "and projection_code = projection.code " +
                  "and spheroid_code = spheroid.code " +
                  "and datum_code = datum.code";

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
                pProjection = sc.spaceIfNull(rset.getString(j++));
                pSpheroid = sc.spaceIfNull(rset.getString(j++));
                pDatum = sc.spaceIfNull(rset.getString(j++));
            } // while (rset.next())

            rset.close();
            stmt.close();

/*
            // get the existing projection / spheriod / datum info
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

            // get the projection name
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

            // get the spheroid name
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

            // get the datum name
            sql = "select name from datum " +
                  "where code = " + pDatumCode;
            if (dbg) System.out.println("<br> sql "+sql);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                int j = 1;
                pDatum = sc.spaceIfNull(rset.getString(j++));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
*/
        } catch (Exception e) {
            this.addItem("Could not process getPsdData: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // getPsdData


    /**
     * Look up a URL parameter
     * @param   args    list of url name-value parameter pairs
     * @param   name    name of parameter to find value for
     */
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

    /**
     * Routine to create a standard format DynamicTable
     * @param cols The number of columns
     * @returns the DynamicTable
     */
    public DynamicTable createNewDynamicTable(int cols, int border) {
        DynamicTable table = new DynamicTable(cols);
        table
            .setBorder(border)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
        ;
        return table;
    } // createNewDynamicTable


    /**
     * Format text strings for html
     * @param   oldStr  the text to format
     */
/*
    private static Container formatStr(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(2));
        return newStrc;
    }  //  private static Container formatStr(String oldStr)
*/


    /**
     * Format text strings for html (big)
     * @param   oldStr  the text to format
     */
/*
    private static Container formatStrBig(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(3));
        return newStrc;
    }  //  private static Container formatStrBig(String oldStr)
*/


//    public String spaceIfNull(String text) {
//        return (text == null ? "" : text);
//    } // sc.spaceIfNull


    /**
     * main class for testing purposes
     * @param   args    list of URL name-value parameters
     */
    public static void main(String args[]) {

        HtmlHead hd = new HtmlHead("DisplaySurvsum local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new DisplaySurvsum(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in DisplaySurvsum: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class DisplaySurvsum

