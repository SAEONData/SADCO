package survsum;

import oracle.html.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.text.SimpleDateFormat;

/**
 * Update the sampling stations block
 *
 * @author  20070820 - SIT Group
 * @version
 * 19980512 - Neville Paynter - create class                            <br>
 * 20070820 - Ursula von St Ange - change to package class              <br>
 */
public class UpdateStationsBlock extends CompoundItem {

    //boolean dbg = true;
    boolean dbg = false;

    // Create java.sql stuff
    String USER = "sadco";
    String PWD = "ter91qes";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rset = null;
    String sql = "";

    // input parameters
    String pSurveyId        = "";
    String recordErr        = "";
    String recnum           = "";
    String  pSurveyId       = "";
    String  pStationId      = "";
    String  pRecUpdated     = "";
    String  pNewUpdated     = "";
    String  pDelUpdated     = "";
    String  pStnidPrefix    = "";
    String  pEStnnam        = "";
    String  pEDateStart     = "";
    String  pEDateEnd       = "";
    String  pELatitude      = "";
    String  pELongitude     = "";
    String  pEStndep        = "";
    String  pNewPrefixId    = "";
    String  pNewPrefixName  = "";

    // database variables

    // work variables
    String dtItem           = "";
    boolean stnnamErr       = ;
    boolean positionErr     = false;
    boolean boundsErr1      = false;
    boolean boundsErr2      = false;
    boolean inputErr        = false;
    boolean prefixErr       = false;
    boolean PSDErr          = false;

    String stnnam       ;
    String latitude     ;
    String longitude    ;
    String stndep       ;
    double dTest        ;

    int inum = 0;

    int spldepCount = 0;
    String  pEStationId       = new String(12);
    String  pDateStart        = new String(10);
    String  pDateEnd          = new String(10);
    int        pProjectionCode   = new int();
    String  pProjection       = new String(25);
    int        pSpheroidCode     = new int();
    String  pSpheroid         = new String(25);
    int        pDatumCode        = new int();
    String  pDatum            = new String(25);
    String  pDisciplineCode   = new String(2);
    String  plStationId    [];
    String  plStnnam       [];
    int        plLatitude     [];
    int        plLongitude    [];
    int        plStndep       [];
    int        plMaxSpldep    [];
    int        plLandTest     [];
    String  plFlagging     [];
    String  plQuality      [];
    int        plWatphyCount  [];
    int        plSedphyCount  [];
    int        plTisphyCount  [];
    String  plPrefixId     [];
    String  plPrefixName   [];

    int        pGMTdiff       = new int();

    int        pPrefixCount   = new int();
    int        pStnnamCount   = new int();
    int        pPositionCount = new int();
    int        pRecordcount;

    boolean        updateRun      = false;

    SadCommon sc = new SadCommon();

    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public UpdateStationsBlock(String args[]) {

        TableRow rows = new TableRow();

        // +------------------------------------------------------------+
        // | Print SADCO logo                                           |
        // +------------------------------------------------------------+
        Image slogo = new Image
            ("http://fred.csir.co.za/sadco-img/sadlogo.gif",
            "sadlog.gif", IVAlign.TOP, false);
        this.addItem(slogo.setCenter());

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the database    |
        // +------------------------------------------------------------+
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = java.sql.DriverManager.getConnection
                ("jdbc:oracle:thin:" + USER + "/" + PWD +
                "@morph.csir.co.za:1522:etek8");
            conn.setAutoCommit(false);
        } catch (Exception e) {
            this.addItem("Logon to SADCO fails: " + e.getMessage());
            return;
        } // try-catch

        // +------------------------------------------------------------+
        // | Are there any input parameters                             |
        // +------------------------------------------------------------+
        if (args.length < 1) {
            this .addItem(new SimpleItem("There are no input parameters"));
            return;
        }  //  if (args.length < 1)

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        recordErr      = sc.getArgument(args,"recorderr");
        recnum         = sc.getArgument(args,"recnum");
//        stnnamErr      = false;
//        positionErr    = false;
//        boundsErr1     = false;
//        boundsErr2     = false;
//        inputErr       = false;

        pSurveyId            = sc.getArgument(args,"surveyid");
        pStationId           = sc.getArgument(args,"stationid");
        pRecUpdated          = sc.getArgument(args,"recUpdated");
        pNewUpdated          = sc.getArgument(args,"newUpdated");
        pDelUpdated          = sc.getArgument(args,"delUpdated");
        pStnidPrefix         = sc.getArgument(args,"stationidpre");
        pEStnnam             = sc.getArgument(args,"stnnam");
        pEDateStart          = sc.getArgument(args,"datestart");
        pEDateEnd            = sc.getArgument(args,"dateend");
        pELatitude           = sc.getArgument(args,"latitude");
        pELongitude          = sc.getArgument(args,"longitude");
        pEStndep             = sc.getArgument(args,"stndep");
        pNewPrefixId         = sc.getArgument(args,"newprefixid");
        pNewPrefixName       = sc.getArgument(args,"newprefixname");

        int iRecnum = 0;
        if (recnum.length() > 0)  {
            iRecnum = Integer.paresInt(recnum);
        }  //  if (recnum.length() > 0)

        if (pStationId.length() > 0)  {
            pStnidPrefix = pStationId.substring(0,3);
        }  //  if (pStationId.length() > 0)


        Select selectDate       = new Select("recdate");
        Select selectPrefixId   = new Select("stationidpre");

        // +------------------------------------------------------------+
        // | Create Main table                                          |
        // +------------------------------------------------------------+
        DynamicTable tab_main  = new DynamicTable(1);
        tab_main
            .setBorder(1)
            .setCellSpacing(1)
            .setWidth("100%")
            .setBackgroundColor("#33CCFF")
            ;

        // +------------------------------------------------------------+
        // | Create title table and add to the main table               |
        // +------------------------------------------------------------+
        DynamicTable tab_titl  = new DynamicTable(2);
        tab_titl
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            ;

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>CAPTURE STATION DATA</b>")).setColSpan(80))
            .addCell(new TableHeaderCell(sc.formatStrBig("Survey Id: <b>" + pSurveyId + "</b")))
            .addCell(new TableHeaderCell(slogo))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Create input  table                                        |
        // +------------------------------------------------------------+
        DynamicTable tab_inp  = new DynamicTable(1);
        tab_inp
            .setBorder(2)
            .setCellSpacing(2)
            .setBackgroundColor("#33AAFF")
            ;

        // +------------------------------------------------------------+
        // | Create new    table                                        |
        // +------------------------------------------------------------+
        DynamicTable tab_new1  = new DynamicTable(1);
        tab_new1
            .setBorder(2)
            .setCellSpacing(2)
            .setBackgroundColor("#33AAFF")
            ;

        // +------------------------------------------------------------+
        // | Create Update table                                        |
        // +------------------------------------------------------------+
        DynamicTable tab_update1  = new DynamicTable(3);
        tab_update1
            .setBorder(2)
            .setCellSpacing(2)
            .setBackgroundColor("#33AAFF")
            ;

        // +------------------------------------------------------------+
        // | Create select station_id prefix table                      |
        // +------------------------------------------------------------+
        DynamicTable tab_selSIP  = new DynamicTable(2);
        tab_selSIP
            .setBorder(2)
            .setCellSpacing(2)
            ;

        // +------------------------------------------------------------+
        // | Create the editing table                                   |
        // +------------------------------------------------------------+
        DynamicTable tab_editing = new DynamicTable(2);
        tab_editing
            .setBorder(1)
            .setCellSpacing(1)
            ;

        // +------------------------------------------------------------+
        // | Create the buttons table  for the data updates             |
        // +------------------------------------------------------------+
        DynamicTable tab_data_buttons = new DynamicTable(1);
        tab_data_buttons
            .setBorder(0)
            .setCellSpacing(0)
            ;

        // +------------------------------------------------------------+
        // | Create the buttons table                                   |
        // +------------------------------------------------------------+
        DynamicTable tab_buttons = new DynamicTable(1);
        tab_buttons
            .setBorder(0)
            .setCellSpacing(0)
            ;

        // +------------------------------------------------------------+
        // | Create Station listing table                               |
        // +------------------------------------------------------------+
        DynamicTable tab_stns  = new DynamicTable(1);
        tab_stns
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            ;

        DynamicTable tab_stns1 = new DynamicTable(4);
        tab_stns1
            .setBorder(1)
            .setCellSpacing(1)
            .setWidth("100%")
            ;



        // +------------------------------------------------------------+
        // | Retrieve the PSD data                                      |
        // +------------------------------------------------------------+
        try  {
            //get_psd_data(pSurveyId);
//         // select from tables inventory, projection, spheriod, datum
//         sadsql.get_psd_data
//            (
//            pSurveyId      ,
//            pProjectionCode,
//            pProjection    ,
//            pSpheroidCode  ,
//            pSpheroid      ,
//            pDatumCode     ,
//            pDatum
//            );
//      } catch (ServerException e)  {
//         bd.addItem(new SimpleItem("Could not process get_psd_data: " + e.getSqlerrm() ));
//         hp.print();
//         return;
//      }  //  catch (ServerException e)


        // +------------------------------------------------------------+
        // | If any of the PSD data is "unknown" the user must go to    |
        // | update PSD block first.                                    |
        // +------------------------------------------------------------+
        Form updatePSDFrm = new Form("GET", "UpdatePSDdataBlock");
        if (pProjection.equals(" Unknown") ||
            pSpheroid  .equals(" Unknown") ||
            pDatum     .equals(" Unknown") ) {
            updatePSDFrm
                .addItem(new SimpleItem("<FONT COLOR=#FF6666><b>PSD data needs to be entered before the Station Data<b></font> <br> "))
                .addItem(new Hidden("surveyid", pSurveyId))
                .addItem(new Submit("submit", "Get PSD data")   )
                ;
            PSDErr = true;

        }  //  if (pProjection.equals(" unknown") ||


        // +------------------------------------------------------------+
        // | Add new prefix id if required                              |
        // +------------------------------------------------------------+
        if (pNewPrefixId.length() > 0)  {
            pNewPrefixId = pNewPrefixId.toUpperCase();
            //addNewPrefix(pNewPrefixId, pNewPrefixName);
//
//         try {
//            // insert into table stnid_prefix
//            sadsql.add_new_prefix(
//               pNewPrefixId      ,
//               pNewPrefixName    ,
//               pPrefixCount
//               );
//         } catch (ServerException e) {
//            bd.addItem(new SimpleItem("Could not process add_new_prefix_id: " + e.getSqlerrm() ));
//            hp.print();
//            return;
//         }  //  catch (ServerException e)

            // +------------------------------------------------------------+
            // | flag errors                                                |
            // +------------------------------------------------------------+
            if (pPrefixCount == 1) prefixErr    = true;

            // +------------------------------------------------------------+
            // | If the addition has been successful, record the prefix ID  |
            // +------------------------------------------------------------+
            if (!prefixErr)
                pStnidPrefix = pNewPrefixId.substring(0,3);

        }  //  if (pNewPrefixId.length() > 0)


        // +------------------------------------------------------------+
        // | Delete  station record if required                         |
        // +------------------------------------------------------------+
        if (pDelUpdated.equals("Y")) {

            // +------------------------------------------------------------+
            // | Delete the station record                                  |
            // +------------------------------------------------------------+
            //delete_station_record(pStationId);
//         try   {
//            // delete from table station
//            sadsql.delete_station_record(
//               pStationId
//               );
//            }  //  try
//         catch (ServerException e)  {
//            bd.addItem(new SimpleItem("Could not process delete_station_record: " + e.getSqlerrm() ));
//            hp.print();
//            return;
//            }  //  catch (ServerException e)
        }  //  if (pDelUpdated.equals("Y"))


        // +------------------------------------------------------------+
        // | Add or edit the station record if required                 |
        // +------------------------------------------------------------+
        if (pNewUpdated.equals("Y")) {

            // +------------------------------------------------------------+
            // | set the flag to indicate that an update run is done        |
            // +------------------------------------------------------------+
            if (pRecUpdated.equals("Y")) updateRun = true;

            // +------------------------------------------------------------+
            // | Check for illegal numbers                                  |
            // +------------------------------------------------------------+
            if (sc.isNotNumeric(pELatitude   ))inputErr  = true;
            if (sc.isNotNumeric(pELongitude  ))inputErr  = true;
            if (sc.isNotNumeric(pEStndep     ))inputErr  = true;

            // +------------------------------------------------------------+
            // | only process if there's no illegal characters              |
            // +------------------------------------------------------------+
            if (!inputErr) {

                // +------------------------------------------------------------+
                // | Check the position co-ordinates                            |
                // +------------------------------------------------------------+
                int dConvert = new int(0.0);
                dTest = Float.parseFloat(pELatitude);
                if (dTest < -10.0f || dTest > 90.0f) boundsErr1 = true;

                dTest = Float.parseFloat(pELongitude);
                if (dTest < -70.0f || dTest > 90.0f) boundsErr2 = true;

                // +------------------------------------------------------------+
                // | Only process if there are within the position co-ordinate  |
                // | ranges                                                     |
                // +------------------------------------------------------------+
                if (!boundsErr1 && !boundsErr2)  {
                    if (pRecUpdated.equals("Y"))   {
                        // +------------------------------------------------------------+
                        // | Update the station record                                  |
                        // +------------------------------------------------------------+
                        //edit_station_record();
//                  try   {
//                    // update table station
//                     sadsql.edit_station_record
//                        (
//                        pStationId        ,
//                        pSurveyId         ,
//                        pEStnnam          ,
//                        pEDateStart       ,
//                        pEDateEnd         ,
//                        pELatitude        ,
//                        pELongitude       ,
//                        pEStndep          ,
//                        pStnnamCount      ,
//                        pPositionCount
//                        );
//                     }  //  try
//                  catch (ServerException e) {
//                     bd.addItem(new SimpleItem("Could not process edit_station_record: " + e.getSqlerrm() ));
//                     hp.print();
//                     return;
//                     }  //  catch (ServerException e)

                    }  else  {

                        // +------------------------------------------------------------+
                        // | Get the last sequential station-id for the selected prefix |
                        // +------------------------------------------------------------+
                        //get_latest_group_station_id()
//                  try    {
//                    // select from table station
//                     sadsql.get_latest_group_station_id(
//                        pStnidPrefix  ,
//                        pEStationId
//                        );
//                     }  //  try
//                  catch (ServerException e)  {
//                     bd.addItem(new SimpleItem("Could not process get_latest_group_station_id: " + e.getSqlerrm() ));
//                     hp.print();
//                     return;
//                     }  //  catch (ServerException e)

                        // +------------------------------------------------------------+
                        // | Creat a new station_id                                     |
                        // +------------------------------------------------------------+
                        pEStationId = new String(sc.getNewStationId(pStnidPrefix, pEStationId ));

                        // +------------------------------------------------------------+
                        // | Add the new station                                        |
                        // +------------------------------------------------------------+
                        //add_station_record();
//                  try  {
//                    // insert into table station
//                     sadsql.add_station_record
//                        (
//                        pEStationId       ,
//                        pSurveyId         ,
//                        pEStnnam          ,
//                        pEDateStart       ,
//                        pEDateEnd         ,
//                        pELatitude        ,
//                        pELongitude       ,
//                        pEStndep          ,
//                        pStnnamCount      ,
//                        pPositionCount
//                        );
//                     }  //  try
//                  catch (ServerException e)   {
//                     bd.addItem(new SimpleItem("Could not process add_station_record: " + e.getSqlerrm() ));
//                     hp.print();
//                     return;
//                     }  //  catch (ServerException e)

                    }  //  if (pRecUpdated.equals("Y"))


                    // +------------------------------------------------------------+
                    // | flag errors                                                |
                    // +------------------------------------------------------------+
                    if (pStnnamCount   .equals("1")) stnnamErr    = true;
                    if (pPositionCount .equals("1")) positionErr  = true;

                }  //  if (!boundsErr1 && !boundsErr2)

            }  //  if (!inputErr)

        }  //  if (pNewUpdated.equals("Y"))








      // +------------------------------------------------+
      // | Count the number of water constituent records  |
      // +------------------------------------------------+
      pDisciplineCode = new String("1");
      try  {
        // count on table survey_constituent
         pRecordcount = sadsql.count_survey_constituent
            (
            pSurveyId      ,
            pDisciplineCode
            );
         }  //  try

      catch (ServerException e)  {
         bd.addItem(new SimpleItem("Could not process count_survey_constituent: " + e.getSqlerrm() ));
         hp.print();
         return;
         }  //  catch (ServerException e)


      int watSurvConstCount = (int)pRecordcount.doubleValue();

      // +------------------------------------------------+
      // | Count the number of sedim constituent records  |
      // +------------------------------------------------+
      pDisciplineCode = new String("2");
      try  {
        // count on table survey_constituent
         pRecordcount = sadsql.count_survey_constituent
            (
            pSurveyId      ,
            pDisciplineCode
            );
         }  //  try

      catch (ServerException e)  {
         bd.addItem(new SimpleItem("Could not process count_survey_constituent: " + e.getSqlerrm() ));
         hp.print();
         return;
         }  //  catch (ServerException e)


      int sedSurvConstCount = (int)pRecordcount.doubleValue();

      // +------------------------------------------------+
      // | Count the number of biolo constituent records  |
      // +------------------------------------------------+
      pDisciplineCode = new String("3");
      try  {
        // count on table survey_constituent
         pRecordcount = sadsql.count_survey_constituent
            (
            pSurveyId      ,
            pDisciplineCode
            );
         }  //  try

      catch (ServerException e)  {
         bd.addItem(new SimpleItem("Could not process count_survey_constituent: " + e.getSqlerrm() ));
         hp.print();
         return;
         }  //  catch (ServerException e)


      int bioSurvConstCount = (int)pRecordcount.doubleValue();


      // +------------------------------------------------------------+
      // | total of all survey constituent counts                     |
      // +------------------------------------------------------------+
      int totSurvConstCount =
          watSurvConstCount +
          sedSurvConstCount +
          bioSurvConstCount;

      // +------------------------------------+
      // | Count the number station  records  |
      // +------------------------------------+
      try  {
        // count on table station
         pRecordcount = sadsql.count_stations(pSurveyId);
         }  //  try

      catch (ServerException e)  {
         bd.addItem(new SimpleItem("Could not process count_stations: " + e.getSqlerrm() ));
         hp.print();
         return;
         }  //  catch (ServerException e)


      int stationCount = (int)pRecordcount.doubleValue();


      // +---------------------------------------+
      // | Count the number station_id prefixes  |
      // +---------------------------------------+
      try  {
        // count on table stnid_prefix
         pRecordcount = sadsql.count_stnid_prefix();
         }  //  try

      catch (ServerException e) {
         bd.addItem(new SimpleItem("Could not process count_stnid_prefix: " + e.getSqlerrm() ));
         hp.print();
         return;
         }  //  catch (ServerException e)


      int prefixCount = (int)pRecordcount.doubleValue();





      // +------------------------------------------------------------+
      // | Build the empty arrays for holding data                    |
      // +------------------------------------------------------------+
      plStationId     = new String[stationCount];
      plStnnam        = new String[stationCount];
      plLatitude      = new int      [stationCount];
      plLongitude     = new int      [stationCount];
      plStndep        = new int      [stationCount];
      plMaxSpldep     = new int      [stationCount];
      plLandTest      = new int      [stationCount];
      plFlagging      = new String[stationCount];
      plQuality       = new String[stationCount];
      plWatphyCount   = new int      [stationCount];
      plSedphyCount   = new int      [stationCount];
      plTisphyCount   = new int      [stationCount];
      plPrefixId      = new String[prefixCount];
      plPrefixName    = new String[prefixCount];

      // +-----------------+
      // | Station  arrays |
      // +-----------------+
      for (int i = 0; i < stationCount; i++)  {
         plStationId     [i]    = new String(12);
         plStnnam        [i]    = new String(10);
         plLatitude      [i]    = new int      (  );
         plLongitude     [i]    = new int      (  );
         plStndep        [i]    = new int      (  );
         plMaxSpldep     [i]    = new int      (  );
         plLandTest      [i]    = new int      (  );
         plWatphyCount   [i]    = new int      (  );
         plSedphyCount   [i]    = new int      (  );
         plTisphyCount   [i]    = new int      (  );
         plFlagging      [i]    = new String(10);
         plQuality       [i]    = new String(10);
         }  //  for (int i = 0; i < stationCount; i++)

      // +-----------------+
      // | Prefix   arrays |
      // +-----------------+
      for (int i = 0; i < prefixCount; i++)  {
         plPrefixId      [i]    = new String( 3);
         plPrefixName    [i]    = new String(40);
         }  //  for (int i = 0; i < prefixCount; i++)






      // +------------------------------------------------------------+
      // | Create buttons to capture constituent data                 |
      // +------------------------------------------------------------+
      Form getWatConstitFrm         = new Form("GET", "getSurveyConstituent");
      Form getSedConstitFrm         = new Form("GET", "getSurveyConstituent");
      Form getBioConstitFrm         = new Form("GET", "getSurveyConstituent");
      Form addWatphyStationsFrm     = new Form("GET", "AddWatphyStations");
      Form addSedphyStationsFrm     = new Form("GET", "xx");
      Form addBiophyStationsFrm     = new Form("GET", "xx");
      Form createWatSpreadSheetFrm  = new Form("GET", "createWatSpreadSheet");
      Form createSedSpreadSheetFrm  = new Form("GET", "xx");
      Form createBioSpreadSheetFrm  = new Form("GET", "xx");
      Form loadWatSpreadSheetFrm  = new Form("GET", "xx");
      Form loadSedSpreadSheetFrm  = new Form("GET", "xx");
      Form loadBioSpreadSheetFrm  = new Form("GET", "xx");

      getWatConstitFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("disciplinecode", "1"))
         .addItem(new Submit("submit", "Water"))
         ;

      getSedConstitFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("disciplinecode", "2"))
         .addItem(new Submit("submit", "Sediment"))
         ;

      getBioConstitFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("disciplinecode", "3"))
         .addItem(new Submit("submit", "Biology"))
         ;

      addWatphyStationsFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Submit("submit", "Water"))
         ;

      createWatSpreadSheetFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("disciplinecode", "1"))
         .addItem(new Submit("submit", "Water"))
         ;

      createSedSpreadSheetFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("disciplinecode", "2"))
         .addItem(new Submit("submit", "Sediment"))
         ;

      createBioSpreadSheetFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("disciplinecode", "3"))
         .addItem(new Submit("submit", "Biology"))
         ;

      loadWatSpreadSheetFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("disciplinecode", "1"))
         .addItem(new Submit("submit", "Water"))
         ;

      loadSedSpreadSheetFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("disciplinecode", "1"))
         .addItem(new Submit("submit", "Sediment"))
         ;

      loadBioSpreadSheetFrm
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("disciplinecode", "1"))
         .addItem(new Submit("submit", "Biology"))
         ;

      rows = new TableRow();
      rows.addCell(new TableDataCell("."));
      tab_data_buttons.addRow(rows);

      rows = new TableRow();
      rows.addCell(new TableDataCell("<u><b>Select by clicking on a button:</b></u>"));
      tab_data_buttons.addRow(rows);

      rows = new TableRow();
      rows.addCell(new TableDataCell("."));
      tab_data_buttons.addRow(rows);

      rows = new TableRow();
      rows
         .addCell(new TableDataCell("<b>Select Constitents:</b>"))
         .addCell(new TableDataCell(getWatConstitFrm))
         .addCell(new TableDataCell(getSedConstitFrm))
         .addCell(new TableDataCell(getBioConstitFrm))
         ;
      tab_data_buttons.addRow(rows);

      rows = new TableRow();
      rows
         .addCell(new TableDataCell("<b>Select Seq Stations:</b>"))
         .addCell(new TableDataCell(addWatphyStationsFrm))
//         .addCell(new TableDataCell(getSedConstitFrm))
//         .addCell(new TableDataCell(getBioConstitFrm))
         ;
      tab_data_buttons.addRow(rows);

      // +------------------------------------------------------------+
      // | Only produce data capture buttons after constituents have  |
      // | been selected                                              |
      // +------------------------------------------------------------+
      if (totSurvConstCount > 0)   {
         rows = new TableRow();
         rows.addCell(new TableDataCell("<b>Create Spreadsheet:</b>"));

         if(watSurvConstCount > 0)   {
            rows.addCell(new TableDataCell(createWatSpreadSheetFrm));
            }  else   {
            rows.addCell(new TableDataCell(" "));
            }  //  if(watSurvXonstCount > 0)

         if(sedSurvConstCount > 0)   {
            rows.addCell(new TableDataCell(createSedSpreadSheetFrm));
            }  else   {
            rows.addCell(new TableDataCell(" "));
            }  //  if(watSurvXonstCount > 0)

         if(bioSurvConstCount > 0)   {
            rows.addCell(new TableDataCell(createBioSpreadSheetFrm));
            }  else   {
            rows.addCell(new TableDataCell(" "));
            }  //  if(watSurvXonstCount > 0)

         tab_data_buttons.addRow(rows);

         rows = new TableRow();
         rows.addCell(new TableDataCell("<b>Load Spreadsheet:</b>"));

         if(watSurvConstCount > 0)   {
            rows.addCell(new TableDataCell(loadWatSpreadSheetFrm));
            }  else   {
            rows.addCell(new TableDataCell(" "));
            }  //  if(watSurvXonstCount > 0)

         if(sedSurvConstCount > 0)   {
            rows.addCell(new TableDataCell(loadSedSpreadSheetFrm));
            }  else   {
            rows.addCell(new TableDataCell(" "));
            }  //  if(watSurvXonstCount > 0)

         if(bioSurvConstCount > 0)   {
            rows.addCell(new TableDataCell(loadBioSpreadSheetFrm));
            }  else   {
            rows.addCell(new TableDataCell(" "));
            }  //  if(watSurvXonstCount > 0)

         tab_data_buttons.addRow(rows);

         }  //  if (totSurvConstCount > 0)




      // +------------------------------------------------------------+
      // | Get survey dates                                           |
      // +------------------------------------------------------------+
      try   {
        // select from table inventory
         sadsql.get_survey_dates
            (
            pSurveyId      ,
            pDateStart     ,
            pDateEnd       ,
            pGMTdiff
            );
         }  //  try                            !
      catch (ServerException e)  {
         bd.addItem(new SimpleItem("Could not process get_survey_dates: " + e.getSqlerrm() ));
         hp.print();
         return;
         }  //  catch (ServerException e)






      // +------------------------------------------------------------+
      // | Get station ID prefix                                      |
      // +------------------------------------------------------------+
      if (pStnidPrefix.length() < 0)  {
          try  {
            // select from table inventory
             sadsql.get_stnid_prefix(
                pSurveyId      ,
                pStnidPrefix
                );
             }  //  try                            !
          catch (ServerException e)  {
             bd.addItem(new SimpleItem("Could not process get_stnid_prefix: " + e.getSqlerrm() ));
             hp.print();
             return;
             }  //  catch (ServerException e)
         }  //  if (pStnidPrefix.length() < 0)






      // +------------------------------------------------------------+
      // | If there are station records get them                      |
      // +------------------------------------------------------------+
      if (stationCount > 0)  {
         // +----------------------+
         // | Get stations         |
         // +----------------------+
         try  {
            // select from station, status_mode
            // count on land_test, watphy, sedphy, tisphy
            sadsql.list_stations
               (
               pSurveyId      ,
               plStationId    ,
               plStnnam       ,
               plLatitude     ,
               plLongitude    ,
               plStndep       ,
               plMaxSpldep    ,
               plLandTest     ,
               plFlagging     ,
               plQuality      ,
               plWatphyCount  ,
               plSedphyCount  ,
               plTisphyCount
               );
            }  //  try
         catch (ServerException e)  {
            bd.addItem(new SimpleItem("Could not process list_stations: " + e.getSqlerrm() ));
            hp.print();
            return;
            }  //  catch (ServerException e)


         pStnidPrefix = new String(plStationId[0].substring(0,3));



         // +------------------------------------------------------------+
         // | Construct the station listing table                        |
         // +------------------------------------------------------------+
         rows = new TableRow();
         rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Stations </u></b>")).setColSpan(80));
         tab_stns.addRow(rows);

         rows = new TableRow();
         rows
            .addCell(new TableDataCell(sc.formatStr("<b>Station Id</b><br>Select & <br>Click to edit/delete")))
            .addCell(new TableDataCell(sc.formatStr("<b>Station<br>Name</b>")))
            .addCell(new TableDataCell(sc.formatStr("<b>Latitude</b>")))
            .addCell(new TableDataCell(sc.formatStr("<b>Longitude</b>")))
            .addCell(new TableDataCell(sc.formatStr("")))
            .addCell(new TableDataCell(sc.formatStr("<b>Station<br>Depth(m)</b>")))
            .addCell(new TableDataCell(sc.formatStr("<b>Max Sample<br>Depth(m)</b>")))
            .addCell(new TableDataCell(sc.formatStr("<b>Data Quality</b>")))
            .addCell(new TableDataCell(sc.formatStr("Water<br>Samples<br>")))
            .addCell(new TableDataCell(sc.formatStr("Sediment<br>Samples")))
            .addCell(new TableDataCell(sc.formatStr("Biological<br>Samples")))
            ;
         tab_stns1.addRow(rows);

         // +------------------------------------------------------------+
         // | Process each station   record                              |
         // +------------------------------------------------------------+
         for (int i = 0; i < stationCount; i++)  {

            // +------------------------------------------------------------+
            // | Create a link to update a station record                   |
            // +------------------------------------------------------------+
            String stnidLink = new String(
               "UpdateStationsBlock" +
               "?surveyid=" + pSurveyId +
               "&recUpdated=Y" +
               "&stationid=" + plStationId[i] +
               "&recnum=" + i
               );

            // +-----------------------------------------------+
            // | Count the number watphy sample depth records  |
            // +-----------------------------------------------+
            try   {
                // count on table watphy_spldep
               pRecordcount = sadsql.count_watphy_spldep_station(plStationId[i]);
               }  //  try

            catch (ServerException e)   {
               bd.addItem(new SimpleItem("Could not process count_watphy_spldep_station: " + e.getSqlerrm() ));
               hp.print();
               return;
               }  //  catch (ServerException e)

            spldepCount = (int)pRecordcount.doubleValue();



            // +------------------------------------------------------------+
            // | Create a link to the watphy capture                        |
            // +------------------------------------------------------------+
            String watphyLink = new String();
            if (spldepCount > 0)  {
               if (watSurvConstCount > 0)   {
                  watphyLink = new String(
                     "getConstituentData" +
                     "?surveyid=" + pSurveyId +
                     "&stationid=" + plStationId[i] +
                     "&pagestatus=getotherlists" +
                     "&disciplinecode=1"
                     );
                  } else {
                  watphyLink = new String(
                     "getSurveyConstituent" +
                     "?surveyid=" + pSurveyId +
                     "&pagestatus=getotherlists" +
                     "&disciplinecode=1"
                     );
                  }  //  if (watSurvConstCount > 0)
               } else {
               watphyLink = new String(
                  "AddWatphySpldeps" +
                  "?surveyid=" + pSurveyId +
                  "&stationid=" + plStationId[i] +
                  "&firsttime=Y"
                  );
               }  //  if (spldepCount = 0)

            rows = new TableRow();
            rows
               .addCell(new TableDataCell(new Link(stnidLink,new SimpleItem("<b>" + plStationId[i] + "</b>").setFontSize(2)) ))
               .addCell(new TableDataCell(sc.formatStr(plStnnam[i] )))
               .addCell(new TableDataCell(sc.formatStr(plLatitude[i] )))
               .addCell(new TableDataCell(sc.formatStr(plLongitude[i] )))
               ;


            // +------------------------------------------------------------+
            // | If the position has beed detected to be over the land mass |
            // | add a warning tag to the displayed record                  |
            // +------------------------------------------------------------+
            if(plLandTest[i].equals("1")) {
               rows.addCell(new TableDataCell(sc.formatStr("<b><FONT COLOR=#FF6666>Over Land</font></b>" )));
               }  else  {
               rows.addCell(new TableDataCell(" "));
               }  //  if(pLandTest.equals("1"))


            rows
               .addCell(new TableDataCell(sc.formatStr(plStndep[i] )))
               .addCell(new TableDataCell(sc.formatStr(plMaxSpldep[i] )))
               .addCell(new TableDataCell(sc.formatStr(plFlagging[i] + " - " + plQuality[i] )))
               ;

            if (plQuality[i].startsWith("unchecked")) {
               rows.addCell(new TableDataCell(new Link(watphyLink,new SimpleItem(plWatphyCount[i]).setFontSize(2)) ));
               } else {
               rows.addCell(new TableDataCell(sc.formatStr("<b>" + plWatphyCount[i] + "</b>" )));
               }  //  if (plQuality[i].startsWith("unchecked")))


            rows
               .addCell(new TableDataCell(sc.formatStr("<b>" + plSedphyCount[i] + "</b>" )))
               .addCell(new TableDataCell(sc.formatStr("<b>" + plTisphyCount[i] + "</b>" )))
               ;


            tab_stns1.addRow(rows.setIVAlign(IVAlign.TOP));
            }  //  for (int i = 0; i < stationCount; i++)

         rows = new TableRow();
         rows.addCell(new TableDataCell(tab_stns1).setVAlign(IVAlign.TOP));
         tab_stns.addRow(rows);

         }  //  if (stationCount > 0)








      // +------------------------------------------------------------+
      // | Only produce a prefix select list if this is the first     |
      // | station record for this survey. All other prefixes are     |
      // | default, taken from the existing station_ids               |
      // +------------------------------------------------------------+
      boolean stnidPrefixNeeded = false;
      if (pStnidPrefix.length() < 1 && !PSDErr)  {
         // +----------------------+
         // | Get prefixes         |
         // +----------------------+
         try  {
            // select from table stnid_prefix
            sadsql.list_stnid_prefix(
               plPrefixId     ,
               plPrefixName
               );
            }  //  try
         catch (ServerException e)  {
            bd.addItem(new SimpleItem("Could not process list_stnid_prefix: " + e.getSqlerrm() ));
            hp.print();
            return;
            }  //  catch (ServerException e)

         for (int i = 0; i < prefixCount; i++) {
            dtItem = plPrefixId[i];
            selectPrefixId.addOption(new Option(
               dtItem + " - " + plPrefixName[i],
               dtItem,
               sc.getDefault(dtItem,pStnidPrefix)
               ));
            }  //  for (int i = 0; i < prefixCount; i++)

         stnidPrefixNeeded = true;
         }  //  if (pStnidPrefix.length() < 1)




      // +------------------------------------------------------------+
      // | If the update mode has been selected, store the selected   |
      // | record data into the form default fields                   |
      // +------------------------------------------------------------+
      if (pRecUpdated.equals("Y") && recnum.length() > 0)  {
         stnnam      = plStnnam     [iRecnum];
         latitude    = plLatitude   [iRecnum];
         longitude   = plLongitude  [iRecnum];
         stndep      = plStndep     [iRecnum];
         }  //  if (pRecUpdated.equals("Y"))










      // +------------------------------------------------------------+
      // | Construct error messages                                   |
      // +------------------------------------------------------------+
      String errMessage = "";


      // +-------------------------------------------------------------+
      // | Display input error                                         |
      // +-------------------------------------------------------------+
      if (inputErr) errMessage = errMessage + "<br>input error - character in numeric field";


      // +-------------------------------------------------------------+
      // | Display station name duplicate error if there is a duplicate|
      // +-------------------------------------------------------------+
      if (stnnamErr) errMessage = errMessage + "<br>Station Name already recorded for this survey";

      // +-------------------------------------------------------------+
      // | Display position duplicate error if there is a duplicate    |
      // +-------------------------------------------------------------+
      if (positionErr) errMessage = errMessage + "<br>position already used for selected date";

      // +-------------------------------------------------------------+
      // | Display positions out of bounds error                       |
      // +-------------------------------------------------------------+
      if (boundsErr1) errMessage = errMessage + "<br>Latitude out of bounds (choose between -10 to 90)";

      if (boundsErr2) errMessage = errMessage + "<br>Longitude out of bounds (choose between -70 to 90)";


      if (errMessage.length() > 0) {
         errMessage = "<FONT COLOR=#FF6666><b>Could not process. Reason:</b>" + errMessage + "</font>";
         stnnam      = pEStnnam     ;
         latitude    = pELatitude   ;
         longitude   = pELongitude  ;
         stndep      = pEStndep     ;
         }  //  if (errMessage.length() > 0)




      // +------------------------------------------------------------+
      // | If a station ID prefix is needed, create and display a     |
      // | form to get the prefix                                     |
      // +------------------------------------------------------------+
      if (stnidPrefixNeeded) {
         Form selSIPFrm = new Form("GET", "UpdateStationsBlock");
         selSIPFrm
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(selectPrefixId)
            .addItem(new Submit("submit", "Submit selected prefix")   )
            .addItem(new Reset   ("Reset") )
            ;

         Form newSIPFrm = new Form("GET", "UpdateStationsBlock");
         newSIPFrm
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new SimpleItem("ID: "))
            .addItem(new TextField("newprefixid", 3, 3, pNewPrefixId))
            ;

         if (prefixErr) newSIPFrm.addItem(new SimpleItem("<FONT COLOR=#FF6666><b>Duplicate Prefix ID</b></font><br>"));


         newSIPFrm
            .addItem(new SimpleItem("<br>Name: "))
            .addItem(new TextField("newprefixname",40,40, pNewPrefixName))
            .addItem(new Submit("submit", "Submit new prefix")   )
            .addItem(new Reset   ("Reset") )
            ;

         rows = new TableRow();
         rows
            .addCell(new TableHeaderCell("Select a Prefix"))
            .addCell(new TableHeaderCell("Register a new Prefix"))
            ;
         tab_selSIP.addRow(rows.setIVAlign(IVAlign.TOP));

         rows = new TableRow();
         rows
            .addCell(new TableDataCell(selSIPFrm))
            .addCell(new TableDataCell(newSIPFrm))
            ;
         tab_selSIP.addRow(rows.setIVAlign(IVAlign.TOP));

         rows = new TableRow();
         rows.addCell(new TableHeaderCell("Choose a Station ID Prefix"));
         tab_inp.addRow(rows.setBackgroundColor(Color.cyan));

         rows = new TableRow();
         rows.addCell(new TableDataCell(tab_selSIP));
         tab_inp.addRow(rows.setIVAlign(IVAlign.TOP));


         }  else   {


         // +------------------------------------------------------------+
         // | Create the form to Edit a station record if the are records|
         // +------------------------------------------------------------+
         if ((recnum.length() > 0  && !updateRun) ||
            (recnum.length() > 0  && updateRun && errMessage.length() > 0)) {
            Form updateFrm = new Form("GET", "UpdateStationsBlock");
            updateFrm
               .addItem(new Hidden("recorderr", recordErr))
               .addItem(new Hidden("surveyid", pSurveyId))
               .addItem(new Hidden("stationid", plStationId[iRecnum]))
               .addItem(new Hidden("datestart", pDateStart))
               .addItem(new Hidden("dateend", pDateEnd))
               .addItem(new Hidden("newUpdated", "Y"))
               .addItem(new Hidden("recUpdated", "Y"))
               .addItem(new Hidden("delUpdated", "N"))
               .addItem(new Hidden("stationidpre", pStnidPrefix))
               .addItem(new Hidden("recnum", recnum))
               .addItem(new SimpleItem(
                  "<b>Station<br>Name</b></td>" +
                  "<td><b>Co-ordinates<br>-##.##### dec degrees</b></td>" +
                  "<td><b>Station<br>Depth(m)</b></td>" +
                  "</tr><tr>"
                  ))
               .addItem(new SimpleItem("<td>"))
               .addItem(new TextField("stnnam", 10, 10, stnnam))
               .addItem(new SimpleItem("</td><td>"))
               .addItem(new SimpleItem("&nbsp;&nbsp;&nbsp;Latitude: "))
               .addItem(new TextField("latitude", 10, 10, latitude))
               .addItem(new SimpleItem("<br> Longitude: "))
               .addItem(new TextField("longitude", 10, 10, longitude))
               .addItem(new SimpleItem("</td><td>"))
               .addItem(new TextField("stndep", 10, 10, stndep))
               .addItem(new SimpleItem("</td>"))
               .addItem(new SimpleItem("</tr><tr><td>"))
               .addItem(new Submit  ("submit", "submit changes") )
               .addItem(new SimpleItem("<br>"))
               .addItem(new Reset   ("Reset") )
               .addItem(new SimpleItem(
                  "<td><b>Projection: </b>" + pProjection +
                  "<br><b>Spheroid: </b>" + pSpheroid +
                  "<br><b>Datum: </b>" + pDatum + "</td>"
                  ))
               .addItem(new SimpleItem("</td></tr>"))
               ;

            rows = new TableRow();
            rows
               .addCell(new TableDataCell(updateFrm))
               ;
            tab_update1.addRow(rows.setIVAlign(IVAlign.TOP));

            // +------------------------------------------------------------+
            // | Create the delete record button                            |
            // +------------------------------------------------------------+
            Form deleteFrm = new Form("GET", "UpdateStationsBlock");
            deleteFrm
               .addItem(new Hidden("surveyid", pSurveyId))
               .addItem(new Hidden("stationid", plStationId[iRecnum]))
               .addItem(new Hidden("delUpdated", "Y"))
               .addItem(new Submit("submit", "delete station record")   )
               ;

            rows = new TableRow();
            rows.addCell(new TableHeaderCell("Edit Station details - " + plStationId[iRecnum]));
            tab_inp.addRow(rows.setBackgroundColor(Color.cyan));

            rows = new TableRow();
            rows.addCell(new TableHeaderCell(errMessage));
            tab_inp.addRow(rows);

            rows = new TableRow();
            rows.addCell(new TableDataCell(tab_update1));
            tab_inp.addRow(rows.setIVAlign(IVAlign.TOP));

            rows = new TableRow();
            rows.addCell(new TableDataCell(deleteFrm));
            tab_inp.addRow(rows.setIHAlign(IHAlign.CENTER));

            }  else  {





            // +------------------------------------------------------------+
            // | Create the form to Update/Delete station record            |
            // +------------------------------------------------------------+
            Form newFrm = new Form("GET", "UpdateStationsBlock");

            newFrm
               .addItem(new Hidden("recorderr", recordErr))
               .addItem(new Hidden("surveyid", pSurveyId))
               .addItem(new Hidden("newUpdated", "Y"))
               .addItem(new Hidden("recUpdated", "N"))
               .addItem(new Hidden("delUpdated", "N"))
               .addItem(new Hidden("stationidpre", pStnidPrefix))
               .addItem(new Hidden("datestart", pDateStart))
               .addItem(new Hidden("dateend", pDateEnd))
               ;





            // +------------------------------------------------------------+
            // | If there are no error messages, clear the form defaults    |
            // | fields                                                     |
            // +------------------------------------------------------------+
            if (errMessage.length() < 1) {
               stnnam      = "";
               latitude    = "";
               longitude   = "";
               stndep      = "";
               }  //  if (errMessage.length() < 1)




            newFrm
               .addItem(new SimpleItem(
                  "<b>Station<br>Name</b></td>" +
                  "<td><b>Co-ordinates<br>-##.##### dec degrees</b></td>" +
                  "<td><b>Station<br>Depth(m)</b></td>" +
                  "</tr><tr>"
                  ))
               .addItem(new SimpleItem("<td>"))
               .addItem(new TextField("stnnam", 10, 10, stnnam))
               .addItem(new SimpleItem("</td><td>"))
               .addItem(new SimpleItem("&nbsp;&nbsp;&nbsp;Latitude: "))
               .addItem(new TextField("latitude", 9, 9, latitude))
               .addItem(new SimpleItem("<br> Longitude: "))
               .addItem(new TextField("longitude", 9, 9, longitude))
               .addItem(new SimpleItem("</td><td>"))
               .addItem(new TextField("stndep", 10, 9, stndep))
               .addItem(new SimpleItem("</td>"))
               .addItem(new SimpleItem("</tr><tr><td>"))
               .addItem(new Submit  ("submit", "Add New Station") )
               .addItem(new SimpleItem("<br>"))
               .addItem(new Reset   ("Reset") )
               .addItem(new SimpleItem(
                  "</td><td>" +
                  "<td><b>Projection: </b>" + pProjection +
                  "<br><b>Spheroid: </b>" + pSpheroid +
                  "<br><b>Datum: </b>" + pDatum + "</td>"
                  ))
                  .addItem(new SimpleItem("</td></tr>"))
               ;

            rows = new TableRow();
            rows.addCell(new TableHeaderCell("Add Station details"));
            tab_inp.addRow(rows.setBackgroundColor(Color.cyan));

            rows = new TableRow();
            rows.addCell(new TableHeaderCell(errMessage));
            tab_inp.addRow(rows);

            rows = new TableRow();
            rows.addCell(new TableDataCell(newFrm));
            tab_new1.addRow(rows.setIVAlign(IVAlign.TOP));

            rows = new TableRow();
            if (PSDErr)   {
               rows.addCell(new TableDataCell(updatePSDFrm));
               }  else  {
               rows.addCell(new TableDataCell(tab_new1));
               }  //  if (PSDErr)
            tab_inp.addRow(rows.setIHAlign(IHAlign.CENTER));

            }  //  if (recnum.length() > 0)


         }  //  if (stnidPrefixNeeded)

      // +------------------------------------------------------------+
      // | Create the completed button                                |
      // +------------------------------------------------------------+
      Form close = new Form("GET", "DisplaySurvsum");
      close
         .addItem(new Hidden("surveyid", pSurveyId))
         .addItem(new Hidden("passcode", "ter91qes"))
         .addItem(new Submit("submit", "<<< Survey Summary")   )
         ;

      rows = new TableRow();
      rows.addCell(new TableHeaderCell(close));
      tab_buttons.addRow(rows.setIVAlign(IVAlign.TOP));








      // +------------------------------------------------------------+
      // | Add Title table to the Main table                          |
      // +------------------------------------------------------------+
      rows = new TableRow();
      rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
      tab_main.addRow(rows);




      if (stationCount > 0)  {
         // +------------------------------------------------------------+
         // | Add List  table to the Main table                          |
         // +------------------------------------------------------------+
         rows = new TableRow();
         rows.addCell(new TableDataCell(tab_stns).setVAlign(IVAlign.TOP));
         tab_main.addRow(rows);
         }  //  if (stationCount > 0)

      rows = new TableRow();
      rows.addCell(new TableDataCell(tab_inp).setVAlign(IVAlign.TOP));

      // +------------------------------------------------------------+
      // | produce data buttons only if there are station records     |
      // +------------------------------------------------------------+
      if (stationCount > 0)    {
         rows.addCell(new TableDataCell(tab_data_buttons).setVAlign(IVAlign.TOP));
         }  //  if (stationCount > 0)

      tab_editing.addRow(rows);

      rows = new TableRow();
      rows.addCell(new TableHeaderCell(tab_editing));
      tab_main.addRow(rows.setBackgroundColor(Color.cyan));


      // +------------------------------------------------------------+
      // | Add closing buttons to the main table                      |
      // +------------------------------------------------------------+
      rows = new TableRow();
      rows.addCell(new TableHeaderCell(tab_buttons));
      tab_main.addRow(rows.setBackgroundColor(Color.cyan));

      // +------------------------------------------------------------+
      // | Add main table to the page                                 |
      // +------------------------------------------------------------+
      hp.addItem(tab_main.setCenter());

      hp.print();

      // +------------------------------------------------------------+
      // | Close down the Oracle database session                     |
      // +------------------------------------------------------------+
      try {
         session.logoff();
         }  //  try
      catch (ServerException e){}

      }  //  public UpdateStationsBlock()


   }  //  public class UpdateStationsBlock
