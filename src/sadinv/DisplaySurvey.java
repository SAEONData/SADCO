package sadinv;

import common2.JSLLib;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.html.*;


/**
 * This class displays the details of the survey

 * SadInv
 * pscreen = display_survey
 * psurveyid = <survey Id>
 *
 * @author 20090105 - SIT Group.
 * @version
 * 20090105 - Ursula von St Ange - create program                       <br>
 * 20090513 - Ursula von St Ange - do stuff depending on data type ub01 <br>
 * 20090602 - Ursula von St Ange - chnge cruise name to survey name ub02<br>
 * 20091026 - Ursula von St Ange - change display of record counts for
 *                                 hydro cruises                   ub03 <br>
 * 20091201 - Ursula von St Ange - add stuff for extraction        ub04 <br>
 * 20100507 - Ursula von St Ange - change for postgres             ub05 <br>
 * 20111103 - Ursula von St Ange - get survey_id list on db        ub06 <br>
 * 20150319 - Ursula von St Ange - add current component extract   ub07 <br>
 * 20160115 - Ursula von St ANge - add code for KML maps           ub08 <br>
 */

public class DisplaySurvey extends CompoundItem  {


    //boolean dbg = false;
    boolean dbg = true;
    boolean dbg2 = false;
    //boolean dbg2 = true;

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    static private Connection conn = null;
    static private Statement stmt = null;
    static private ResultSet rset = null;
    static private Statement stmt2 = null;
    static private ResultSet rset2 = null;
    String sql = "";
    String sql2 = "";

    static final String NBSP = "&nbsp;&nbsp;&nbsp;&nbsp;";

    // for the drop-down lists
    Select selOnline = new Select(sc.EXTRTYPE);
    Select selOffline = new Select(sc.EXTRTYPE);
    boolean isOnline = false;
    boolean isOffline = false;
    boolean isPhysNut = false;

    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    //public static void main(String args[]) throws SQLException, SQLException {

    public DisplaySurvey(String args[]) {

        // +------------------------------------------------------------+
        // | Are there any input parameters                             |
        // +------------------------------------------------------------+
        if (args.length < 1) {
            if (!dbg2) {
                this.addItem(new SimpleItem("There are no input parameters"))   ;
                //hp.print();
                return;
            } // if (!dbg2)
        }  //  if (args.length < 1)


        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String surveyId_in = ec.getArgument(args,sc.SURVEYID);
        String yearIn = ec.getArgument(args,sc.YEAR);
        String sessionCode = ec.getArgument(args,sc.PSC);              //ub04
        String available = ec.getArgument(args,sc.AVAILABLE);          //ub04
        String version = ec.getArgument(args,sc.VERSION);              //ub06

        if (dbg) for (int i = 0; i < args.length; i++)
            System.out.println("args["+i+"] = " + args[i]);

        if (dbg) System.out.println("sessionCode = " + sessionCode);
        if (dbg) System.out.println("available = " + available);

        if (dbg2) {
            //1992/0003 Y,1993/0009 Y,1993/0010 Y,
            //psurveyid=1974/0001&psc=2791&pavailable=Y
            surveyId_in = "2015/0280";
            sessionCode = "2791";
            //version = "next";
            available = "Y"; //"";
        } // if (dbg2)

        // +------------------------------------------------------------+
        // | Get connected to the db                                    |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass, sessionCode);

        // +------------------------------------------------------------+
        // | if logged in, then get survey list from db                 |
        // +------------------------------------------------------------+
        String nextSurvey = "";
        if (!"".equals(version)) {
            nextSurvey = sc.getNextSurvey(conn, sessionCode, surveyId_in, available);
            if (dbg) System.out.println("nextSurvey1 = " + nextSurvey);
            if (!"".equals(nextSurvey)) {
                surveyId_in = nextSurvey.substring(0,9);
                available = nextSurvey.substring(10);
            } // if (!"".equals(nextSurvey))
        } // if (!"".equals(version))

        // also get the following survey - for the Next button
        nextSurvey = sc.getNextSurvey(conn, sessionCode, surveyId_in, available);
        if (dbg) System.out.println("nextSurvey2 = " + nextSurvey);

        // not a valid survey id
        String  pSurveyId      = surveyId_in;
        if (pSurveyId.length() < 7) {
            this.addItem(new SimpleItem("Invalid surveyId: " + pSurveyId));
            return;
        } // if (pSurveyId.length().length < 7)

         // do some hacking to get the survey id right when called from sadinv
        if (dbg) System.out.println("<br>pSurveyId1 = " + pSurveyId);
        int len = pSurveyId.length();
        pSurveyId = pSurveyId.substring(0,4) + "/" + pSurveyId.substring(len-4,len);
        if (dbg) System.out.println("<br> pSurveyId "+pSurveyId);
        System.out.println(thisClass + ":SId=" + surveyId_in);


        // +------------------------------------------------------------+
        // | Prepare for PL/SQL values                                  |
        // +------------------------------------------------------------+
        //if (pSurveyId.length() > 0 ) {
        //    pSurveyId = surveyId_in;
        //} // if (pSurveyId.length() > 0 )

        String  pDataCentre     = "";
        String  pProjectName    = "";
        String  pCruiseName     = "";
        String  pPlanamName     = "";
        String  pPlatformName   = "";
        String  pCountryName    = "";
        String  pSurname        = "";
        String  pFName          = "";
        String  pTitle          = "";
        String  pInstitName     = "";
        String  pDateStart      = "";
        String  pDateEnd        = "";
        String  pLatNorth       = "";
        String  pLatSouth       = "";
        String  pLongWest       = "";
        String  pLongEast       = "";
        String  pSurveyTypeName = "";
        String  pSurveyCode     = "";
        String  pDataRecorded   = "";

        String  pStationCnt     = "";
        String  pWatphyCnt      = "";
        String  pWatnutCnt      = "";
        String  pWatpolCnt      = "";       // ub03
        //String  pWatpol1Cnt     = "";     // ub03
        //String  pWatpol2Cnt     = "";     // ub03
        String  pWatcheCnt      = "";       // ub03
        //String  pWatchem1Cnt    = "";     // ub03
        //String  pWatchem2Cnt    = "";     // ub03
        String  pWatchlCnt      = "";
        String  pWatctdCnt      = "";
        String  pWatxbtCnt      = "";
        String  pWatosdCnt      = "";
        String  pWatmbtCnt      = "";
        String  pWatpflCnt      = "";
        String  pSedphyCnt      = "";
        String  pSedpesCnt      = "";
        String  pSedpolCnt      = "";       // ub03
        //String  pSedpol1Cnt     = "";     // ub03
        //String  pSedpol2Cnt     = "";     // ub03
        String  pSedcheCnt      = "";       // ub03
        //String  pSedchem1Cnt    = "";     // ub03
        //String  pSedchem2Cnt    = "";     // ub03
        String  pSedtaxCnt      = "";
        String  pPlaphyCnt      = "";
        String  pPlapesCnt      = "";
        String  pPlapolCnt      = "";       // ub03
        //String  pPlapol1Cnt     = "";     // ub03
        //String  pPlapol2Cnt     = "";     // ub03
        String  pPlataxCnt      = "";
        String  pPlachlCnt      = "";
        String  pTisphyCnt      = "";
        String  pTispesCnt      = "";
        String  pTispolCnt      = "";       // ub03
        //String  pTispol1Cnt     = "";     // ub03
        //String  pTispol2Cnt     = "";     // ub03
        String  pTistaxCnt      = "";
        //String  pWeatherCnt     = "";     // ub03
        String  pCurrentCnt     = "";    // ub03

        String  pWatphyStnCnt      = "";    // ub03
        String  pWatnutStnCnt      = "";    // ub03
        String  pWatpolStnCnt      = "";    // ub03
        String  pWatcheStnCnt      = "";    // ub03
        String  pWatchlStnCnt      = "";    // ub03
        String  pWatctdStnCnt      = "";
        String  pWatxbtStnCnt      = "";
        String  pWatosdStnCnt      = "";
        String  pWatmbtStnCnt      = "";
        String  pWatpflStnCnt      = "";
        String  pSedphyStnCnt      = "";    // ub03
        String  pSedpesStnCnt      = "";    // ub03
        String  pSedpolStnCnt      = "";    // ub03
        String  pSedcheStnCnt      = "";    // ub03
        String  pSedtaxStnCnt      = "";    // ub03
        String  pPlaphyStnCnt      = "";    // ub03
        String  pPlapesStnCnt      = "";    // ub03
        String  pPlapolStnCnt      = "";    // ub03
        String  pPlataxStnCnt      = "";    // ub03
        String  pPlachlStnCnt      = "";    // ub03
        String  pTisphyStnCnt      = "";    // ub03
        String  pTispesStnCnt      = "";    // ub03
        String  pTispolStnCnt      = "";    // ub03
        String  pTistaxStnCnt      = "";    // ub03
        String  pCurrentStnCnt     = "";    // ub03

        int pDisOxygenCnt       = -1;
        int pSalinityCnt        = -1;
        int pTemperatureCnt     = -1;
        int pNo3Cnt             = -1;
        int pPo4Cnt             = -1;
        int pSio3Cnt            = -1;
        int pChlaCnt            = -1;
        int pDICCnt             = -1;
        int pPHCnt              = -1;

        // +------------------------------------------------------------+
        // | Retrieve the survey information from the packaged PL/SQL   |
        // | using procedure display_survey                             |
        // +------------------------------------------------------------+
        try {

            sql =
                " select inv.data_centre, inv.project_name, inv.cruise_name,"+
                " plan.name, plat.name, c.name, sc.surname, sc.f_name, sc.title,"+
                " inst.name, to_char(inv.date_start,'YYYY-MM-DD'),"+
                " to_char(inv.date_end,'YYYY-MM-DD'), inv.lat_north,"+
                " inv.lat_south, inv.long_west, inv.long_east, inv.data_recorded, "+
                " st.code, st.name" +
                " from inventory inv, platform plat, planam plan, institutes inst,"+
                " country c, scientists sc, survey_type st" +
                " where inv.survey_id = '"+pSurveyId+"'"+
                " and plan.code = inv.planam_code"+
                " and plat.code = plan.platform_code"+
                " and inst.code = inv.instit_code"+
                " and sc.code = inv.sci_code_1"+
                " and c.code = inv.country_code"+
                " and st.code = survey_type_code"+
                " order by inv.survey_id ";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (dbg) System.out.println("<br> sql "+sql);

            while (rset.next()) {
                pDataCentre    = sc.checkNull(rset.getString(1));
                pProjectName   = sc.checkNull(rset.getString(2));
                pCruiseName    = sc.checkNull(rset.getString(3));
                pPlanamName    = sc.checkNull(rset.getString(4));
                pPlatformName  = sc.checkNull(rset.getString(5));
                pCountryName   = sc.checkNull(rset.getString(6));
                pSurname       = sc.checkNull(rset.getString(7));
                pFName         = sc.checkNull(rset.getString(8));
                pTitle         = sc.checkNull(rset.getString(9));
                pInstitName    = sc.checkNull(rset.getString(10));
                pDateStart     = sc.checkNull(rset.getString(11));
                pDateEnd       = sc.checkNull(rset.getString(12));
                pLatNorth      = sc.checkNull(rset.getString(13));
                pLatSouth      = sc.checkNull(rset.getString(14));
                pLongWest      = sc.checkNull(rset.getString(15));
                pLongEast      = sc.checkNull(rset.getString(16));
                pDataRecorded  = sc.checkNull(rset.getString(17));
                pSurveyCode    = sc.checkNull(rset.getString(18));
                pSurveyTypeName= sc.checkNull(rset.getString(19));
            } // while (rset.next())

            if (dbg) System.out.println("<br> pProjectName = " + pProjectName);
            //if (pProjectName == null) pProjectName = " ";
            //if (pCruiseName == null) pCruiseName = " ";
            //if (pCountryName == null) pCountryName = " ";
            //if (pTitle == null) pTitle = " ";
            if (dbg) System.out.println("scientist title = " +pTitle+", surname = "+pSurname+
                ", fname = "+pFName);

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.err.println("<br>sql = "+sql);
            System.err.println("<br>message = "+e.getMessage());
            e.printStackTrace();
        }  //  catch (SQLException e)


        // +------------------------------------------------------------+
        // |check whether the survey is flagged                         |
        // +------------------------------------------------------------+
        if ("".equals(available)) {                                          //ub04
            try {

                sql =
                    //"select distinct nvl(passkey,'null') from station "  +    //ub05
                    "select distinct coalesce(passkey,'null') from station "  + //ub05
                    "where survey_id = '"+pSurveyId+"'";

                if (dbg) System.out.println("<br>*** sql = "+sql);
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                int numFlagged = 0;
                boolean hasNull = false;
                while (rset.next()) {
                    numFlagged++;
                    String passkey = rset.getString(1);
                    if ("null".equals(passkey)) hasNull = true;
                    if (dbg) System.out.println("<br> numFlagged, hasNull = " +
                        hasNull + " " + passkey);
                } // while (rset.next())

                if (numFlagged == 0) hasNull = true; // for cww
                available = (hasNull?"Y":"N");
                available = ("N".equals(pDataRecorded)?"N":available);
                if (dbg) System.out.println("<br>surveyId, numFlagged = " +
                    pSurveyId + " " + hasNull + " " + available);

                rset.close();
                rset = null;
                stmt.close();
                stmt = null;

            } catch (SQLException e) {
                System.err.println("<br>Debug "+sql +" "+ e.getMessage());
                e.printStackTrace();
            } // try-catch
        } // if ("".equals(available))                                  // ub04

        Image track = new Image
            ("http://sadco.int.ocean.gov.za/sadco-img/noload.gif",
             "DATA NOT LOADED",
             IVAlign.TOP, true);

        // +------------------------------------------------------------+
        // | Create button to go to the Survey Summary form             |
        // +------------------------------------------------------------+
        Form survsumButton = new Form ("GET", "http://sadcosum.ocean.gov.za/");
        survsumButton
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("passcode", "0") )
            .addItem(new Hidden(sc.PSC, sessionCode))                   //ub04
            .addItem(new Hidden(sc.SCREEN, "DisplaySurvsum"))
            .addItem(new Submit("Submit", ">>> Survey Summaries") )
            ;

        // +------------------------------------------------------------+
        // | Create button for next survey                              |
        // +------------------------------------------------------------+
        String nextSurvButtonStr = "";
        if (!"".equals(nextSurvey)) {
            Form nextSurvButton = new Form("GET", sc.APP);
            nextSurvButton
                .addItem(new Hidden(sc.SURVEYID, pSurveyId) )
                .addItem(new Hidden(sc.PSC, sessionCode))               //ub03
                .addItem(new Hidden(sc.SCREEN, sc.DISPLAYSURVEY))
                .addItem(new Hidden(sc.VERSION, "next"))
                .addItem(new Hidden(sc.AVAILABLE, available))
                .addItem(new Submit("Submit", "Next"))
                ;
            nextSurvButtonStr = nextSurvButton.toHTML();
        } // if (!"".equals(sessionCode))

        DynamicTable tab_main  = new DynamicTable(2);
        tab_main
            .setBorder(0)
            .setCellSpacing(0)
            .setBackgroundColor(Color.white)
            .setFrame(ITableFrame.VOLD)
            .setRules(ITableRules.NONE)
            ;

        DynamicTable tab_dates = new DynamicTable(2);
        tab_dates
            .setBorder(1)
            .setCellSpacing(0)
            .setBackgroundColor(new Color("FFFF99"))
            .setFrame(ITableFrame.BOX)
            .setRules(ITableRules.ALL)
            ;

        // +------------------------------------------------------------+
        // | Survey details                                             |
        // +------------------------------------------------------------+
        TableRow rows = new TableRow();
        rows.addCell(new TableHeaderCell(nextSurvButtonStr));
        rows.addCell(new TableHeaderCell(survsumButton.toHTML()));
//            survsumButton.toHTML() + nextSurvButtonStr).setColSpan(2));
        tab_dates.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("Survey ID"))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pSurveyId)))
            ;
        tab_dates.addRow(rows);

        if (dbg) System.out.println("Survey ID"+pSurveyId);

        //rows = new TableRow();
        //rows
        //    .addCell(new TableHeaderCell("<p> <p> "))
        //    ;
        //tab_dates.addRow(rows);

        String projectText = "Project Name ";                           // ub01
        if (pSurveyCode.equals("3") || pSurveyCode.equals("4"))         // ub01
            projectText = "Station Name ";                              // ub01
        rows = new TableRow();
        rows.addCell(new TableHeaderCell(formatStr(projectText))        // ub01
                .setBackgroundColor(new Color("FFCC66")));
        rows.addCell(new TableDataCell(formatStr(pProjectName)));
        tab_dates.addRow(rows);

        if (dbg) System.out.println("<br>Survey ID"+pSurveyId);
        if (dbg) System.out.println("<br>row Project Name ");


        if (pSurveyCode.equals("1") || pSurveyCode.equals("2") ||
            pSurveyCode.equals("6")) {       // ub01
            String cruiseText = "Survey Name ";                         // ub01 ub02
            if (pSurveyCode.equals("2")) cruiseText = "Station Name ";  // ub01
            if (pSurveyCode.equals("6")) cruiseText = "Station Name ";  // ub01
            rows = new TableRow();
            rows
                .addCell(new TableHeaderCell(formatStr(cruiseText))     // ub01
                    .setBackgroundColor(new Color("FFCC66")))
                .addCell(new TableDataCell(formatStr(pCruiseName)))
                ;
            tab_dates.addRow(rows);

            if (dbg) System.out.println("<br>row Survey Name ");        // ub02
        } // if (pSurveyCode.equals("1") || pSurveyCode.equals("2"))    // ub01

        //rows = new TableRow();
        //rows
        //    .addCell(new TableHeaderCell("<p> "))
        //    ;
        //tab_dates.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableHeaderCell(formatStr("Chief Scientist"))
                .setBackgroundColor(new Color("FFCC66")));
        rows.addCell(new TableDataCell(formatStr(pTitle.trim()
            + " " + pFName.trim()
            + " " + pSurname)));
        tab_dates.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("Institute "))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pInstitName)))
            ;
        tab_dates.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("Country"))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pCountryName)))
            ;
        tab_dates.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("Ship or Platform"))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pPlanamName)))
            ;
        tab_dates.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("Platform Type"))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pPlatformName)))
            ;
        tab_dates.addRow(rows);


        // +------------------------------------------------------------+
        // |  Survey start and end dates                                |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("Start Date "))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pDateStart)))
            ;
        tab_dates.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("End Date "))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pDateEnd)))
            ;
        tab_dates.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("Latitude "))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pLatNorth + " to " +
                pLatSouth + " degrees")))
            ;
        tab_dates.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("Longitude "))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pLongWest + " to " +
                pLongEast + " degrees")))
            ;
        tab_dates.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(formatStr("Survey Type"))
                .setBackgroundColor(new Color("FFCC66")))
            .addCell(new TableDataCell(formatStr(pSurveyTypeName)))
            ;
        tab_dates.addRow(rows);

        if (dbg) System.out.println("<br>before con.addItem(tab_dates);");

        Container con = new Container();
        con.addItem(tab_dates);

        if (dbg) System.out.println("<br>after con.addItem(tab_dates);");


        if (pDataRecorded.startsWith("Y")) {

            // +------------------------------------------------------------+
            // | create the gif file name                                   |
            // +------------------------------------------------------------+
            String image_file =
                "c" +
                pSurveyId.substring(0,4) +
                pSurveyId.substring(5,9) +
                ".gif";

            track = new Image(
                "http://sadco.int.ocean.gov.za/sadco-img/" + image_file,
                image_file,
                IVAlign.TOP, true);

            // add a help function
            String urlStr = sc.APP + "?" + sc.SCREEN + "=" + sc.HELP;
            String linkStr = "<a href=\"#\" " + "onclick=\"window.open('" +
                urlStr + "','mywindow','width=400,height=300,scrollbars=1')\"" +
                ">here</a>";
            //String linkStr = "<a href=\"" + urlStr + "\" " +
            //    "onclick=\"return popup(this, 'notes')\"" +
            //    ">here</a>";

            con
                .addItem(SimpleItem.LineBreak)
                .addItem(formatStr3("Please click " + linkStr + " for help."))
                .addItem(SimpleItem.LineBreak)
                ;

        } // if (pDataRecorded.startsWith("Y"))

        if (dbg) System.out.println("pSurveyCode = " + pSurveyCode);


        if (pSurveyCode.equals("1")) { // hydro
            // +------------------------------------------------------------+
            // | If the data is stored on the SADCO database, a track chart |
            // | will be available.                                         |
            // +------------------------------------------------------------+

            if (pDataRecorded.startsWith("Y")) {

                try {
                    sql =
                        /* // ub03
                        "select station_cnt, watphy_cnt, watnut_cnt, "+
                        "watpol1_cnt, watpol2_cnt, watchem1_cnt, "+
                        "watchem2_cnt, watchl_cnt, sedphy_cnt, sedpes_cnt, "+
                        "sedpol1_cnt, sedpol2_cnt, sedchem1_cnt, sedchem2_cnt, "+
                        "sedtax_cnt, plaphy_cnt, plapes_cnt, plapol1_cnt, " +
                        "plapol2_cnt, platax_cnt, plachl_cnt, tisphy_cnt, "+
                        "tispes_cnt, tispol1_cnt, tispol2_cnt, tisanimal_cnt, "+
                        "weather_cnt, watcurrents_cnt "+
                        "from inv_stats where "+
                        "inv_stats.survey_id = '"+pSurveyId+"'"+
                        "order by inv_stats.survey_id";
                        */ // ub03
                        "select station_cnt, watphy_cnt, watnut_cnt, "+
                        "watpol1_cnt, watchem1_cnt, "+
                        "watchl_cnt, sedphy_cnt, sedpes_cnt, "+
                        "sedpol1_cnt, sedchem1_cnt, "+
                        "sedtax_cnt, plaphy_cnt, plapes_cnt, plapol1_cnt, " +
                        "platax_cnt, plachl_cnt, tisphy_cnt, "+
                        "tispes_cnt, tispol1_cnt, tisanimal_cnt, "+
                        "watcurrents_cnt, "+
                        "watctd_cnt, watxbt_cnt, watosd_cnt, watmbt_cnt, watpfl_cnt, "+
                        "watphy_stn_cnt, watnut_stn_cnt, "+
                        "watpol_stn_cnt, watchem_stn_cnt, "+
                        "watchl_stn_cnt, sedphy_stn_cnt, sedpes_stn_cnt, "+
                        "sedpol_stn_cnt, sedchem_stn_cnt, "+
                        "sedtax_stn_cnt, plaphy_stn_cnt, plapes_stn_cnt, plapol_stn_cnt, " +
                        "platax_stn_cnt, plachl_stn_cnt, tisphy_stn_cnt, "+
                        "tispes_stn_cnt, tispol_stn_cnt,tisanimal_stn_cnt, "+
                        "watcurrents_stn_cnt, "+
                        "watctd_stn_cnt, watxbt_stn_cnt, watosd_stn_cnt, "+
                        "watmbt_stn_cnt, watpfl_stn_cnt "+
                        "from inv_stats where "+
                        "inv_stats.survey_id = '"+pSurveyId+"' "+
                        "order by inv_stats.survey_id";

                    if (dbg) System.out.println("<br> sql = " + sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    while (rset.next()) {
                        if (dbg) System.out.println("<br>inside loop inv_stats");
                        int ii = 0;
                        pStationCnt   = sc.checkNull0(rset.getString(++ii));
                        pWatphyCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatnutCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatpolCnt    = sc.checkNull0(rset.getString(++ii));
                        //pWatpol1Cnt   = sc.checkNull0(rset.getString(4));
                        //pWatpol2Cnt   = sc.checkNull0(rset.getString(5));
                        pWatcheCnt    = sc.checkNull0(rset.getString(++ii));
                        //pWatchem1Cnt  = sc.checkNull0(rset.getString(6));
                        //pWatchem2Cnt  = sc.checkNull0(rset.getString(7));
                        pWatchlCnt    = sc.checkNull0(rset.getString(++ii));
                        pSedphyCnt    = sc.checkNull0(rset.getString(++ii));
                        pSedpesCnt    = sc.checkNull0(rset.getString(++ii));
                        pSedpolCnt    = sc.checkNull0(rset.getString(++ii));
                        //pSedpol1Cnt   = sc.checkNull0(rset.getString(11));
                        //pSedpol2Cnt   = sc.checkNull0(rset.getString(12));
                        pSedcheCnt    = sc.checkNull0(rset.getString(++ii));
                        //pSedchem1Cnt  = sc.checkNull0(rset.getString(13));
                        //pSedchem2Cnt  = sc.checkNull0(rset.getString(14));
                        pSedtaxCnt    = sc.checkNull0(rset.getString(++ii));
                        pPlaphyCnt    = sc.checkNull0(rset.getString(++ii));
                        pPlapesCnt    = sc.checkNull0(rset.getString(++ii));
                        pPlapolCnt    = sc.checkNull0(rset.getString(++ii));
                        //pPlapol1Cnt   = sc.checkNull0(rset.getString(18));
                        //pPlapol2Cnt   = sc.checkNull0(rset.getString(19));
                        pPlataxCnt    = sc.checkNull0(rset.getString(++ii));
                        pPlachlCnt    = sc.checkNull0(rset.getString(++ii));
                        pTisphyCnt    = sc.checkNull0(rset.getString(++ii));
                        pTispesCnt    = sc.checkNull0(rset.getString(++ii));
                        pTispolCnt    = sc.checkNull0(rset.getString(++ii));
                        //pTispol1Cnt   = sc.checkNull0(rset.getString(24));
                        //pTispol2Cnt   = sc.checkNull0(rset.getString(25));
                        pTistaxCnt = sc.checkNull0(rset.getString(++ii));
                        //pWeatherCnt   = sc.checkNull0(rset.getString(27));
                        pCurrentCnt   = sc.checkNull0(rset.getString(++ii));
                        pWatctdCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatxbtCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatosdCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatmbtCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatpflCnt    = sc.checkNull0(rset.getString(++ii));

                        pWatphyStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatnutStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatpolStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatcheStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatchlStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pSedphyStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pSedpesStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pSedpolStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pSedcheStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pSedtaxStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pPlaphyStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pPlapesStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pPlapolStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pPlataxStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pPlachlStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pTisphyStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pTispesStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pTispolStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pTistaxStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pCurrentStnCnt   = sc.checkNull0(rset.getString(++ii));
                        pWatctdStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatxbtStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatosdStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatmbtStnCnt    = sc.checkNull0(rset.getString(++ii));
                        pWatpflStnCnt    = sc.checkNull0(rset.getString(++ii));

                    } // while (rset.next())

                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.err.println("<br>sql = "+sql);
                    System.err.println("<br>message = "+e.getMessage());
                    e.printStackTrace();
                }  //  catch (SQLException e)

                // get the profile QC flags
                try {
                    sql =
                        " select disoxygen, salinity, temperature, no3, po4, " +
                        " sio3, chla, dic, ph " +
                        " from station, watprofqc where " +
                        " station.survey_id = '"+pSurveyId+"' and " +
                        " station.station_id = watprofqc.station_id";

                    if (dbg) System.out.println("<br> sql = " + sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    while (rset.next()) {
                        if (dbg) System.out.println("<br>inside loop watprofqc");
                        pDisOxygenCnt   = addValue(pDisOxygenCnt  , rset.getString(1));
                        pSalinityCnt    = addValue(pSalinityCnt   , rset.getString(2));
                        pTemperatureCnt = addValue(pTemperatureCnt, rset.getString(3));
                        pNo3Cnt         = addValue(pNo3Cnt        , rset.getString(4));
                        pPo4Cnt         = addValue(pPo4Cnt        , rset.getString(5));
                        pSio3Cnt        = addValue(pSio3Cnt       , rset.getString(6));
                        pChlaCnt        = addValue(pChlaCnt       , rset.getString(7));
                        pDICCnt         = addValue(pDICCnt        , rset.getString(8));
                        pPHCnt          = addValue(pPHCnt         , rset.getString(9));

                    } // while (rset.next())

                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.err.println("<br>sql = "+sql);
                    System.err.println("<br>message = "+e.getMessage());
                    e.printStackTrace();
                }  //  catch (SQLException e)


                DynamicTable stats_tab1 = new DynamicTable(4);
                stats_tab1
                    .setBorder(1)
                    .setCellSpacing(1)
                    .setFrame(ITableFrame.BOX)
                    .setRules(ITableRules.ALL)
                    .setBackgroundColor(new Color("FFFF99"))
                    ;

                DynamicTable stats_tab2 = new DynamicTable(5);
                stats_tab2
                    .setBorder(1)
                    .setCellSpacing(1)
                    .setFrame(ITableFrame.BOX)
                    .setRules(ITableRules.ALL)
                    .setBackgroundColor(new Color("FFFF99"))
                    ;

                rows = new TableRow();
                rows
                    .addCell(new TableHeaderCell(formatStr2("Number Of Stations : "))
                        .setBackgroundColor(new Color("FFCC66")))
                    .addCell(new TableHeaderCell(formatStr2(pStationCnt)))
                    //.addCell(new TableHeaderCell(formatStr("Weather : "))
                    //    .setBackgroundColor(new Color("FFCC66")))
                    //.addCell(new TableHeaderCell(formatStr(pWeatherCnt)))
                    ;
                stats_tab1.addRow(rows);

                rows = new TableRow();
                rows
                    .addCell(new TableHeaderCell(formatStr2("Data Type")))   // ub03
                    .addCell(new TableHeaderCell(formatStr2("Stations")))    // ub03
                    .addCell(new TableHeaderCell(formatStr2("Records")))     // ub03
                    ;
                rows.setBackgroundColor(new Color("FFCC66"));
                stats_tab2.addRow(rows);

                if (!pWatphyStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr2("Water"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr2(pWatphyStnCnt)))
                        .addCell(new TableHeaderCell(formatStr2(pWatphyCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ccff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatphyStnCnt.equals("0"))

                if (!pWatosdStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"OSD"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pWatosdStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pWatosdCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatosdStnCnt.equals("0")) {

                if (!pWatctdStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"CTD"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pWatctdStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pWatctdCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatctdStnCnt.equals("0")) {

                if (!pWatxbtStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"XBT"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pWatxbtStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pWatxbtCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatxbtStnCnt.equals("0")) {

                if (!pWatmbtStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"MBT"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pWatmbtStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pWatmbtCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatmbtStnCnt.equals("0")) {

                if (!pWatpflStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"PFL"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pWatpflStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pWatpflCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatpflStnCnt.equals("0")) {

                if (!pWatnutStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Nutrients"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pWatnutStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pWatnutCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatnutStnCnt.equals("0")) {

                if (!pWatcheStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Chemistry"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pWatcheStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pWatcheCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatcheStnCnt.equals("0")) {

                if (!pWatpolStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Pollution"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pWatpolStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pWatpolCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatpolStnCnt.equals("0")) {

                if (!pWatchlStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Chlorophyll"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pWatchlStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pWatchlCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pWatchlStnCnt.equals("0")) {

                if (!pCurrentStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Currents"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pCurrentStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pCurrentCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pCurrentStnCnt.equals("0")) {

                if (!pSedphyStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr2("Sediment"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr2(pSedphyStnCnt)))
                        .addCell(new TableHeaderCell(formatStr2(pSedphyCnt)))
                        ;
                    rows.setBackgroundColor(new Color("cc9966"));
                    stats_tab2.addRow(rows);
                } // if (!pSedphyStnCnt.equals("0"))

                if (!pSedcheStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Chemistry"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pSedcheStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pSedcheCnt)))
                        ;
                    rows.setBackgroundColor(new Color("ffcc99"));
                    stats_tab2.addRow(rows);
                } // if (!pSedcheStnCnt.equals("0")) {

                if (!pSedpolStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Pollution"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pSedpolStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pSedpolCnt)))
                        ;
                    rows.setBackgroundColor(new Color("ffcc99"));
                    stats_tab2.addRow(rows);
                } // if (!pSedpolStnCnt.equals("0")) {

                if (!pSedpesStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Pesticides"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pSedpesStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pSedpesCnt)))
                        ;
                    rows.setBackgroundColor(new Color("ffcc99"));
                    stats_tab2.addRow(rows);
                } // if (!pSedpesStnCnt.equals("0")) {

                if (!pSedtaxStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Taxonomy"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pSedtaxStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pSedtaxStnCnt)))
                        ;
                    rows.setBackgroundColor(new Color("ffcc99"));
                    stats_tab2.addRow(rows);
                } // if (!pSedtaxStnCnt.equals("0")) {

                if (!pPlaphyStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr2("Plankton"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr2(pPlaphyStnCnt)))
                        .addCell(new TableHeaderCell(formatStr2(pPlaphyCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99cc99"));
                    stats_tab2.addRow(rows);
                } // if (!pPlaphyStnCnt.equals("0"))

                if (!pPlapolStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Pollution"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pPlapolStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pPlapolCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ff99"));
                    stats_tab2.addRow(rows);
                } // if (!pPlapolStnCnt.equals("0")) {

                if (!pPlachlStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Chlorophyll"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pPlachlStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pPlachlCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ffff"));
                    stats_tab2.addRow(rows);
                } // if (!pPlachlStnCnt.equals("0")) {

                if (!pPlapesStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Pesticides"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pPlapesStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pPlapesCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ff99"));
                    stats_tab2.addRow(rows);
                } // if (!pPlapesStnCnt.equals("0")) {

                if (!pPlataxStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Taxonomy"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pPlataxStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pPlataxStnCnt)))
                        ;
                    rows.setBackgroundColor(new Color("99ff99"));
                    stats_tab2.addRow(rows);
                } // if (!pPlataxStnCnt.equals("0")) {

//                        pTisphyStnCnt    = sc.checkNull0(rset.getString(++ii));
//                        pTispolStnCnt    = sc.checkNull0(rset.getString(++ii));
//                        pTispesStnCnt    = sc.checkNull0(rset.getString(++ii));
//                        pTistaxStnCnt = sc.checkNull0(rset.getString(++ii));

                if (!pTisphyStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr2("Tissue"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr2(pTisphyStnCnt)))
                        .addCell(new TableHeaderCell(formatStr2(pTisphyCnt)))
                        ;
                    rows.setBackgroundColor(new Color("ffff66"));
                    stats_tab2.addRow(rows);
                } // if (!pTisphyStnCnt.equals("0"))

                if (!pTispolStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Pollution"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pTispolStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pTispolCnt)))
                        ;
                    rows.setBackgroundColor(new Color("ffff99"));
                    stats_tab2.addRow(rows);
                } // if (!pTispolStnCnt.equals("0")) {

                if (!pTispesStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Pesticides"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pTispesStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pTispesCnt)))
                        ;
                    rows.setBackgroundColor(new Color("ffff99"));
                    stats_tab2.addRow(rows);
                } // if (!pTispesStnCnt.equals("0")) {

                if (!pTistaxStnCnt.equals("0")) {
                    rows = new TableRow();
                    rows
                        .addCell(new TableHeaderCell(formatStr(NBSP+"Taxonomy"))
                            .setHAlign(IHAlign.LEFT))
                        .addCell(new TableHeaderCell(formatStr(pTistaxStnCnt)))
                        .addCell(new TableHeaderCell(formatStr(pTistaxStnCnt)))
                        ;
                    rows.setBackgroundColor(new Color("ffff99"));
                    stats_tab2.addRow(rows);
                } // if (!pTistaxStnCnt.equals("0")) {

                con
                    .addItem(SimpleItem.LineBreak)
                    .addItem(formatStr4("<b>Database Statistics</b>"))
                    .addItem(SimpleItem.LineBreak)
                    .addItem(SimpleItem.LineBreak)
                    .addItem(stats_tab1)
                    .addItem(SimpleItem.LineBreak)
                    .addItem(stats_tab2)
                    .addItem(SimpleItem.LineBreak)
                    ;

                if (!"".equals(sessionCode) && "Y".equals(available)) { // ub04

                    int watphyCnt = Integer.parseInt(pWatphyCnt);
                    int watcheCnt = Integer.parseInt(pWatcheCnt);
                    int watpolCnt = Integer.parseInt(pWatpolCnt);
                    int sedphyCnt = Integer.parseInt(pSedphyCnt);
                    int sedcheCnt = Integer.parseInt(pSedcheCnt);
                    int sedpolCnt = Integer.parseInt(pSedpolCnt);
                    //int tisphyCnt = Integer.parseInt(pTisphyCnt);
                    int tispolCnt = Integer.parseInt(pTispolCnt);
                    int currentCnt = Integer.parseInt(pCurrentCnt);

                    // create the drop-down list
                    addSelectOption(watphyCnt, 0);
                    addSelectOption(watphyCnt, 1);
                    addSelectOption(watcheCnt, 2);
                    addSelectOption(watpolCnt, 3);
                    addSelectOption(sedphyCnt, 4);
                    addSelectOption(sedcheCnt, 5);
                    addSelectOption(sedpolCnt, 6);
                    addSelectOption(tispolCnt, 7);
                    addSelectOption(currentCnt, 8);
                    addSelectOption(currentCnt, 9);
                    addSelectOption(currentCnt, 10);            //ub07
                    addSelectOption(currentCnt, 11);            //ub07

                    if (dbg) System.out.println("pTispolCnt = " + pTispolCnt +
                        " " + isOnline + " " + isOffline);
                    if (dbg) System.out.println("tispolCnt = " + tispolCnt +
                        " " + isOnline + " " + isOffline);

                    // create the extract forms and add to the page
                    if (isOnline) {
                        Form onlineForm = new Form("GET", sc.APP);
                        onlineForm
                            .addItem(new Hidden(sc.SCREEN, sc.EXTRACT))
                            .addItem(new Hidden(sc.DATATYPE, pSurveyCode))
                            .addItem(new Hidden(sc.DATANAME, pSurveyTypeName))
                            .addItem(new Hidden(sc.SURVEYID, pSurveyId))
                            .addItem(new Hidden(sc.PSC, sessionCode))
                            .addItem(selOnline)
                            .addItem(SimpleItem.LineBreak)
                            .addItem(new Submit(sc.ACTION, sc.ONLINE));
                        con.addItem(onlineForm);
                    } // if (isOnline)

                    if (isOffline) {
                        Form offlineForm = new Form("GET", sc.APP);
                        offlineForm
                            .addItem(new Hidden(sc.SCREEN, sc.EXTRACT))
                            .addItem(new Hidden(sc.DATATYPE, pSurveyCode))
                            .addItem(new Hidden(sc.DATANAME, pSurveyTypeName))
                            .addItem(new Hidden(sc.SURVEYID, pSurveyId))
                            .addItem(new Hidden(sc.PSC, sessionCode))
                            .addItem(selOffline)
                            .addItem(SimpleItem.LineBreak)
                            .addItem(new Submit(sc.ACTION, sc.OFFLINE));
                        con.addItem(offlineForm);
                    } // if (isOffline)

                } // if (!"".equals(sessionCode) && "Y".equals(available))

                if (!"".equals(sessionCode) && "N".equals(available)) { // ub04
                    con
                        .addItem(SimpleItem.LineBreak)
                        .addItem(formatStr3("This data is not available<br>for extraction"))
                        ;
                } // if (!"".equals(sessionCode) && "N".equals(available))

                Form kmlMapButton = new Form("PUT", sc.APP, "maps");        //ub08
                kmlMapButton                                                //ub08
                    .addItem(new Hidden(sc.SURVEYID, pSurveyId) )           //ub08
                    //.addItem(new Hidden(sc.PSC, sessionCode))             //ub08
                    .addItem(new Hidden(sc.SCREEN, sc.KML))                 //ub08
                    //.addItem(new Hidden(sc.VERSION, "next"))              //ub08
                    //.addItem(new Hidden(sc.AVAILABLE, available))         //ub08
                    .addItem(new Submit("Submit", ">>> Zoomable Map (new tab)"))//ub08
                    ;
                con.addItem(kmlMapButton);                                  //ub08

            }  //  if pDataStored.equals("Y")

        } else if (pSurveyCode.equals("2") || pSurveyCode.equals("6")) {  // currents, utr

            // get the mooring code
            String mooringCode = "";
            try {
                sql =
                    "select code from cur_mooring  " +
                    "where survey_id = '" + pSurveyId + "' ";

                if (dbg) System.out.println("<br> sql = " + sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                while (rset.next()) {
                    if (dbg) System.out.println("<br>inside loop select mooring code");
                    mooringCode = rset.getString(1);
                } // while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;
                if (dbg) System.out.println("<br>mooringCode = " + mooringCode);

            } catch (Exception e) {
                System.err.println("<br>sql = "+sql);
                System.err.println("<br>message = "+e.getMessage());
                e.printStackTrace();
            }  //  catch (SQLException e)

            // create the depths table
            DynamicTable depths_tab = new DynamicTable(1);
            depths_tab
                .setBorder(1)
                .setCellSpacing(1)
                .setCellPadding(3)
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL)
                .setBackgroundColor(new Color("FFFF99"))
                ;

            rows = new TableRow();
            rows
                .addCell(new TableHeaderCell(formatStr("Depth")))
                .addCell(new TableHeaderCell(formatStr("Instr.<br>No")))
                .addCell(new TableHeaderCell(formatStr("Para-<br>meters")))
                .addCell(new TableHeaderCell(formatStr("Start Date")))
                .addCell(new TableHeaderCell(formatStr("End Date")))
                .addCell(new TableHeaderCell(formatStr("Intrvl")))
                .addCell(new TableHeaderCell(formatStr("Recs")))
                .addCell(new TableHeaderCell(formatStr("QC")))
                ;

            if (!"".equals(sessionCode) && "Y".equals(available)) { // ub04
                rows.addCell(new TableHeaderCell(formatStr("Extract")));
            } // if (!"".equals(sessionCode) && "Y".equals(available))

            rows.setBackgroundColor(new Color("FFCC66"));
            depths_tab.addRow(rows);

            if (!"".equals(mooringCode)) {

                // get all the depth details
                try {
                    sql =
                        //"select code, spldep, instrument_number, " +
                        //"parameters, date_time_start, date_time_end, " +
                        //"time_interval, number_of_records, coalesce(person_checked,'No') " +
                        //"from cur_depth left outer join cur_depth_qc " +
                        //"on cur_depth.code = cur_depth_qc.depth_code " +
                        //"and mooring_code = " + mooringCode +
                        //" order by spldep,date_time_start";
                        "select code, spldep, instrument_number, " +
                        "parameters, date_time_start, date_time_end, " +
                        "time_interval, number_of_records " +
                        "from cur_depth " +
                        "where mooring_code = " + mooringCode +
                        " order by spldep,date_time_start";

                    if (dbg) System.out.println("<br> sql = " + sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    String depth = "";
                    String depth2 = "";
                    String depthCode = "";
                    String value = "";
                    boolean lightYellow = true;
                    while (rset.next()) {
                        rows = new TableRow();
                        if (dbg) System.out.println("<br>inside loop select depth info");
                        // get code and depth separately to keep
                        depthCode = sc.checkNull(rset.getString(1));
                        depth2 = sc.checkNull(rset.getString(2));
                        rows.addCell(new TableDataCell(formatStr(depth2)));

                        // get rest of fields
                        for (int i = 3; i <=8; i++) {
                            value = sc.checkNull(rset.getString(i));
                            if ((i == 5) || (i == 6))
                                if (value.length() >= 10) value = value.substring(0,10);
                            rows.addCell(new TableDataCell(formatStr(value)));
                        } // for (int i = 3; i <=8; i++)

                        sql2 = "select person_checked from " +
                            "cur_depth_qc where cur_depth_qc.depth_code = " +
                            depthCode;
                        stmt2 = conn.createStatement();
                        rset2 = stmt2.executeQuery(sql2);
                        String person = "";
                        while (rset2.next()) {
                            person = rset.getString(1);
                        } // while (rset2.next()) {
                        rset2.close();
                        stmt2.close();
                        rset2 = null;
                        stmt2 = null;

                        String qcPresent = ("No".equals(person) ? "No" : "Yes");
                        rows.addCell(new TableDataCell(formatStr(qcPresent)));

                        if (!"".equals(sessionCode) && "Y".equals(available)) { // ub04
                            // check if one can extract
                            int numRecs = Integer.parseInt(value);
                            String text = sc.ONLINE;
                            if (numRecs > sc.MAX_CURRENTS) text = sc.OFFLINE;

    //                        // create form with button
    //                        Form form = new Form("GET", sc.APP);
    //                        form
    //                            .addItem(new Hidden(sc.SCREEN, sc.LOGIN))
    //                            .addItem(new Submit(sc.ACTION, text));
    //                        rows.addCell(new TableDataCell(form.toHTML()));

                            // create link
                            String url = sc.APP +
                                "?" + sc.SCREEN + "=" + sc.EXTRACT +
                                "&" + sc.PSC + "=" + sessionCode +
                                "&" + sc.DATATYPE + "=" + pSurveyCode +
                                "&" + sc.DATANAME + "=" + pSurveyTypeName +
                                "&" + sc.SURVEYID + "=" + pSurveyId +
                                "&" + sc.DEPTHCODE + "=" + depthCode +
                                "&" + sc.ACTION + "=" + text;
                            Link link = new Link(url, text);
                            rows.addCell(new TableDataCell(formatStr(link.toHTML())));
                        } // if (!"".equals(sessionCode) && "Y".equals(available))

                        depths_tab.addRow(rows);
                        // make every alternate depth light yellow
                        if (!depth.equals(depth2)) {
                            lightYellow = (lightYellow ? false : true);
                        } // if (!depth.equals(depth2) && lightYellow)
                        if (lightYellow) rows.setBackgroundColor(new Color("FFFFCC"));

                        depth = depth2;
                    } // while (rset.next())
                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.err.println("<br>sql = "+sql);
                    System.err.println("<br>message = "+e.getMessage());
                    e.printStackTrace();
                }  //  catch (SQLException e)

                if (dbg) System.out.println("sessionCode = " + sessionCode);
                if (dbg) System.out.println("available = " + available);
                if (!"".equals(sessionCode) && "N".equals(available)) { // ub04
                    con
                        .addItem(SimpleItem.LineBreak)
                        .addItem(formatStr3("This data is not available<br>for extraction"))
                        .addItem(SimpleItem.LineBreak)
                        ;
                } // if (!"".equals(sessionCode) && "N".equals(available))

                // add table to page
                con
                    .addItem(SimpleItem.LineBreak)
                    .addItem(formatStr4("<b>Mooring Details</b>"))
                    .addItem(SimpleItem.LineBreak)
                    .addItem(SimpleItem.LineBreak)
                    .addItem(depths_tab)
                    ;
            } // if (!"".equals(mooringCode))

        } else if (pSurveyCode.equals("3")) {  // weather

            // get no of records in wav_period
            int recCount = 0;
            try {
                sql =
                    "select count(*) " +
                    "from wet_station, wet_period_counts " +
                    "where survey_id = '" + pSurveyId + "' " +
                    "and wet_station.station_id = wet_period_counts.station_id";
                if (dbg) System.out.println("<br> sql = " + sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                while (rset.next()) {
                    if (dbg) System.out.println("<br>inside loop select count");
                    recCount = Integer.parseInt(rset.getString(1));
                } // while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;
                if (dbg) System.out.println("<br>recCount = " + recCount);


            } catch (Exception e) {
                System.err.println("<br>sql = "+sql);
                System.err.println("<br>message = "+e.getMessage());
                e.printStackTrace();
            }  //  catch (SQLException e)

            // define variables for counts
            String stationId = "";
            String yearP[] = new String[recCount];
            String months[][] = new String[recCount][12];
            int totals[] = new int[recCount];

            // get the period data
            try {
                sql =
                    "select wet_period_counts.* " +
                    "from wet_station, wet_period_counts " +
                    "where survey_id = '" + pSurveyId + "' " +
                    "and wet_station.station_id = wet_period_counts.station_id";
                if (dbg) System.out.println("<br> sql = " + sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                int i = 0;
                while (rset.next()) {
                    if (dbg) System.out.println("<br>inside loop select wet_period_counts");
                    stationId = rset.getString(1);
                    yearP[i] = rset.getString(2);
                    totals[i] = 0;
                    for (int j = 0; j < 12; j++) {
                        months[i][j] = rset.getString(j+3);
                        totals[i] += Integer.parseInt(months[i][j]);
                        if ("0".equals(months[i][j])) months[i][j] = ".";
                    } // for (int j = 0, j < 12; j++)
                    i++;
                } // while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

            } catch (Exception e) {
                System.err.println("<br>sql = "+sql);
                System.err.println("<br>message = "+e.getMessage());
                e.printStackTrace();
            }  //  catch (SQLException e)

            DynamicTable period_tab = new DynamicTable(1);
            period_tab
                .setBorder(1)
                .setCellSpacing(1)
                .setCellPadding(3)
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL)
                .setBackgroundColor(new Color("FFFF99"))
                ;

            rows = new TableRow();
            rows
                .addCell(new TableHeaderCell(formatStr("Year")))
                .addCell(new TableHeaderCell(formatStr("Jan")))
                .addCell(new TableHeaderCell(formatStr("Feb")))
                .addCell(new TableHeaderCell(formatStr("Mar")))
                .addCell(new TableHeaderCell(formatStr("Apr")))
                .addCell(new TableHeaderCell(formatStr("May")))
                .addCell(new TableHeaderCell(formatStr("Jun")))
                .addCell(new TableHeaderCell(formatStr("Jul")))
                .addCell(new TableHeaderCell(formatStr("Aug")))
                .addCell(new TableHeaderCell(formatStr("Sep")))
                .addCell(new TableHeaderCell(formatStr("Oct")))
                .addCell(new TableHeaderCell(formatStr("Nov")))
                .addCell(new TableHeaderCell(formatStr("Dec")))
                .addCell(new TableHeaderCell(formatStr("Total")))
                ;

            if (!"".equals(sessionCode) && "Y".equals(available)) { // ub04
                rows.addCell(new TableHeaderCell(formatStr("Extract")));
            } // if (!"".equals(sessionCode) && "Y".equals(available))

            rows.setBackgroundColor(new Color("FFCC66"));
            period_tab.addRow(rows);

            for (int i = 0; i < recCount; i++) {

                rows = new TableRow();
                rows.addCell(new TableHeaderCell(formatStr(yearP[i])));
                for (int j = 0; j < 12; j++) {
                    rows.addCell(new TableDataCell(formatStr(months[i][j])));
                } // for (int j = 0; j < 12; j++)
                rows.addCell(new TableDataCell(formatStr(String.valueOf(totals[i]))));

                if (!"".equals(sessionCode) && "Y".equals(available)) { // ub04
                    // check if one can extract
                    String text = sc.ONLINE;
                    if (totals[i] > sc.MAX_WEATHER) text = sc.OFFLINE;

                    // create linke
                    String url = sc.APP +
                        "?" + sc.SCREEN + "=" + sc.EXTRACT +
                        "&" + sc.PSC + "=" + sessionCode +
                        "&" + sc.DATATYPE + "=" + pSurveyCode +
                        "&" + sc.DATANAME + "=" + pSurveyTypeName +
                        "&" + sc.SURVEYID + "=" + pSurveyId +
                        "&" + sc.YEAR + "=" + yearP[i] +
                        "&" + sc.ACTION + "=" + text;
                    Link link = new Link(url, text);
                    rows.addCell(new TableDataCell(formatStr(link.toHTML())));
                } // if (!"".equals(sessionCode) && "Y".equals(available))

                period_tab.addRow(rows);

            } // for (int i = 0; i < recCount; i++)

            con
                .addItem(SimpleItem.LineBreak)
                .addItem(formatStr4("<b>Weather Database Period Record Counts</b>"))
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem(period_tab)
                ;

        } else if (pSurveyCode.equals("4")) {  // waves

            // get no of records in wav_period
            int recCount = 0;
            try {
                sql =
                    "select count(*) " +
                    "from wav_station, wav_period " +
                    "where survey_id = '" + pSurveyId + "' " +
                    "and wav_station.station_id = wav_period.station_id";
                if (dbg) System.out.println("<br> sql = " + sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                while (rset.next()) {
                    if (dbg) System.out.println("<br>inside loop select count");
                    recCount = Integer.parseInt(rset.getString(1));
                } // while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;
                if (dbg) System.out.println("<br>recCount = " + recCount);


            } catch (Exception e) {
                System.err.println("<br>sql = "+sql);
                System.err.println("<br>message = "+e.getMessage());
                e.printStackTrace();
            }  //  catch (SQLException e)

            // define variables for counts
            String stationId = "";
            String yearP[] = new String[recCount];
            String months[][] = new String[recCount][12];
            int instrCount[] = new int[recCount];
            int totals[] = new int[recCount];

            // get the period data
            try {
                sql =
                    "select wav_period.* " +
                    "from wav_station, wav_period " +
                    "where survey_id = '" + pSurveyId + "' " +
                    "and wav_station.station_id = wav_period.station_id";
                if (dbg) System.out.println("<br> sql = " + sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                int i = 0;
                while (rset.next()) {
                    if (dbg) System.out.println("<br>inside loop select wav_period");
                    stationId = rset.getString(1);
                    yearP[i] = rset.getString(2);
                    totals[i] = 0;
                    for (int j = 0; j < 12; j++) {
                        months[i][j] = rset.getString(j+3);
                        totals[i] += Integer.parseInt(months[i][j]);
                        if ("0".equals(months[i][j])) months[i][j] = ".";
                    } // for (int j = 0, j < 12; j++)
                    i++;
                } // while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

            } catch (Exception e) {
                System.err.println("<br>sql = "+sql);
                System.err.println("<br>message = "+e.getMessage());
                e.printStackTrace();
            }  //  catch (SQLException e)

            DynamicTable period_tab = new DynamicTable(1);
            period_tab
                .setBorder(1)
                .setCellSpacing(1)
                .setCellPadding(3)
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL)
                .setBackgroundColor(new Color("FFFF99"))
                ;

            rows = new TableRow();
            rows
                .addCell(new TableHeaderCell(formatStr("Year")))
                .addCell(new TableHeaderCell(formatStr("Jan")))
                .addCell(new TableHeaderCell(formatStr("Feb")))
                .addCell(new TableHeaderCell(formatStr("Mar")))
                .addCell(new TableHeaderCell(formatStr("Apr")))
                .addCell(new TableHeaderCell(formatStr("May")))
                .addCell(new TableHeaderCell(formatStr("Jun")))
                .addCell(new TableHeaderCell(formatStr("Jul")))
                .addCell(new TableHeaderCell(formatStr("Aug")))
                .addCell(new TableHeaderCell(formatStr("Sep")))
                .addCell(new TableHeaderCell(formatStr("Oct")))
                .addCell(new TableHeaderCell(formatStr("Nov")))
                .addCell(new TableHeaderCell(formatStr("Dec")))
                .addCell(new TableHeaderCell(formatStr("Total")))
                .addCell(new TableHeaderCell(formatStr("Instruments")))
                ;
            if (!"".equals(sessionCode) && "Y".equals(available)) { // ub04
                rows.addCell(new TableHeaderCell(formatStr("Extract")));
            } // if (!"".equals(sessionCode) && "Y".equals(available))
            rows.setBackgroundColor(new Color("FFCC66"));
            period_tab.addRow(rows);

            for (int i = 0; i < recCount; i++) {

                // get the instruments
                String instruments = "";
                int count = 0;
                try {
                    sql =
                        "select distinct edm_instrument2.name from " +
                        "wav_inst_stats, edm_instrument2 " +
                        "where station_id = '" + stationId + "' and " +
                        "yearP = " + yearP[i] + " and " +
                        "wav_inst_stats.instrument_code = edm_instrument2.code";
                    if (dbg) System.out.println("<br> sql = " + sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);
                    while (rset.next()) {
                        if (dbg) System.out.println("<br>inside loop select instruments");
                        instruments += ("".equals(instruments) ? "": "<br>") +
                            rset.getString(1);
                        count++;
                    } // while (rset.next())
                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.err.println("<br>sql = "+sql);
                    System.err.println("<br>message = "+e.getMessage());
                    e.printStackTrace();
                }  //  catch (SQLException e)

                instrCount[i] = count;


                rows = new TableRow();
                if (count > 1) {
                    String url = sc.APP +
                        "?" + sc.SCREEN + "=" + sc.DISPLAYSURVEY +
                        "&" + sc.SURVEYID + "=" + pSurveyId +
                        "&" + sc.YEAR + "=" + yearP[i] +
                        "&" + sc.PSC + "=" + sessionCode +
                        "&" + sc.AVAILABLE + "=" + available;
                    rows.addCell(new TableDataCell(
                        //new Link(url,new SimpleItem(yearP[i]).setFontSize(1))) );
                        new Link(url,formatStr(yearP[i]))));
                } else {
                    rows.addCell(new TableDataCell(formatStr(yearP[i])));
                } // if (count > 1)
                for (int j = 0; j < 12; j++) {
                    rows.addCell(new TableDataCell(formatStr(months[i][j])));
                } // for (int j = 0; j < 12; j++)
                rows.addCell(new TableDataCell(formatStr(String.valueOf(totals[i]))));
                rows.addCell(new TableDataCell(formatStr(instruments)));

                if (!"".equals(sessionCode) && "Y".equals(available)) { // ub04
                    // check if one can extract
                    String text = sc.ONLINE;
                    if (totals[i] > sc.MAX_WAVES) text = sc.OFFLINE;

                    // create linke
                    String url = sc.APP +
                        "?" + sc.SCREEN + "=" + sc.EXTRACT +
                        "&" + sc.PSC + "=" + sessionCode +
                        "&" + sc.DATATYPE + "=" + pSurveyCode +
                        "&" + sc.DATANAME + "=" + pSurveyTypeName +
                        "&" + sc.SURVEYID + "=" + pSurveyId +
                        "&" + sc.YEAR + "=" + yearP[i] +
                        "&" + sc.ACTION + "=" + text;
                    Link link = new Link(url, text);
                    rows.addCell(new TableDataCell(formatStr(link.toHTML())));
                } // if (!"".equals(sessionCode) && "Y".equals(available))

                period_tab.addRow(rows);

            } // for (int i = 0; i < recCount; i++)

            con
                .addItem(SimpleItem.LineBreak)
                .addItem(formatStr4("<b>Waves Database Period Record Counts</b>"))
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem(period_tab)
                ;

            // get the year's instrument stats
            if (dbg) System.out.println("<br>yearIn = " + yearIn);
            if (!"".equals(yearIn)) {

                // get no of records in wav_inst_stats
                recCount = 0;
                try {
                    sql =
                        "select count(*) " +
                        "from wav_inst_stats " +
                        "where station_id = '" + stationId + "' " +
                        "and yearp = " + yearIn;
                    if (dbg) System.out.println("<br> sql = " + sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);
                    while (rset.next()) {
                        if (dbg) System.out.println("<br>inside loop select count");
                        recCount = Integer.parseInt(rset.getString(1));
                    } // while (rset.next())
                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;
                    if (dbg) System.out.println("<br>recCount = " + recCount);

                } catch (Exception e) {
                    System.err.println("<br>sql = "+sql);
                    System.err.println("<br>message = "+e.getMessage());
                    e.printStackTrace();
                }  //  catch (SQLException e)

                // define the variables
                int    pMonth[]    = new int[recCount];
                String pStats[]    = new String[recCount];
                String pInsttype[] = new String[recCount];
                totals = new int[recCount];

                try {
                    sql =
                        "select monthp, stats, name " +
                        "from wav_inst_stats, edm_instrument2 " +
                        "where station_id = '" + stationId + "' " +
                        "and yearp = " + yearIn + " " +
                        "and wav_inst_stats.instrument_code = edm_instrument2.code " +
                        "order by name, monthp";
                    if (dbg) System.out.println("<br> sql = " + sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);
                    int i = 0;
                    while (rset.next()) {
                        if (dbg) System.out.println("<br>inside loop select instruments stats");
                        pMonth[i] = Integer.parseInt(rset.getString(1));
                        pStats[i] = rset.getString(2);
                        pInsttype[i] = rset.getString(3);
                        if (dbg) System.out.println("<br>values are: " +
                            pMonth[i] + " " + pStats[i] + " " + pInsttype[i]);
                        i++;
                    } // while (rset.next())
                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.err.println("<br>sql = "+sql);
                    System.err.println("<br>message = "+e.getMessage());
                    e.printStackTrace();
                }  //  catch (SQLException e)

                // put the stats into a matrix
                String[][] astats    = new String[10][13];
                for (int i = 0; i < 10; i++) {
                    astats[i][0] = null;
                    for (int j = 1; j < 13 ; j++) {
                        astats[i][j] = ".";
                    }  //  for (int j = 0; j < 13 ; j++)
                }  //  for (int i = 0; i < instStats.length; i++)

                int rownum = -1;
                String prevInst = "";
                for (int i = 0; i < recCount; i++) {
                    if (!prevInst.equals(pInsttype[i])) {
                        rownum++;
                        astats[rownum][0] = pInsttype[i];
                        totals[rownum] = 0;
                        prevInst = pInsttype[i];
                    } // if (prevInst != pInstcod[i])
                    astats[rownum][pMonth[i]] = pStats[i];
                    totals[rownum] += Integer.parseInt(pStats[i]);
                }  //  for (int i = 0; i < instStats.length; i++)

                DynamicTable statsTab = new DynamicTable(1);
                statsTab
                    .setBorder(1)
                    .setCellSpacing(1)
                    .setCellPadding(3)
                    .setFrame(ITableFrame.BOX)
                    .setRules(ITableRules.ALL)
                    .setBackgroundColor(new Color("FFFF99"))
                    ;

                rows = new TableRow();
                rows
                    .addCell(new TableHeaderCell(formatStr("Instrument")))
                    .addCell(new TableHeaderCell(formatStr("Jan")))
                    .addCell(new TableHeaderCell(formatStr("Feb")))
                    .addCell(new TableHeaderCell(formatStr("Mar")))
                    .addCell(new TableHeaderCell(formatStr("Apr")))
                    .addCell(new TableHeaderCell(formatStr("May")))
                    .addCell(new TableHeaderCell(formatStr("Jun")))
                    .addCell(new TableHeaderCell(formatStr("Jul")))
                    .addCell(new TableHeaderCell(formatStr("Aug")))
                    .addCell(new TableHeaderCell(formatStr("Sep")))
                    .addCell(new TableHeaderCell(formatStr("Oct")))
                    .addCell(new TableHeaderCell(formatStr("Nov")))
                    .addCell(new TableHeaderCell(formatStr("Dec")))
                    .addCell(new TableHeaderCell(formatStr("Total")))
                    ;
                rows.setBackgroundColor(new Color("FFCC66"));
                statsTab.addRow(rows);

                // create a table from the matrix
                for (int i = 0; i  < 10; i++) {
                    rows = new TableRow();
                    if (!(astats[i][0] == null)) {
                        for (int j = 0; j < 13 ; j++)
                            rows.addCell(new TableDataCell(formatStr(astats[i][j])));
                        rows.addCell(new TableDataCell(formatStr(String.valueOf(totals[i]))));
                        statsTab.addRow(rows);
                    }  //  if (!(astats[i][0] == null) )
                }  //  for (int i = 1; i  < 10; i++)

                con
                    .addItem(SimpleItem.LineBreak)
                    .addItem(formatStr4
                        ("<b>Detailed Instrument Record Counts for " + yearIn + "</b>"))
                    .addItem(SimpleItem.LineBreak)
                    .addItem(SimpleItem.LineBreak)
                    .addItem(statsTab)
                    ;

            } // if (!"".equals(year))

        } // if (pSurveyCode.equals("1"))

        // +------------------------------------------------------------+
        // | Insert the data quality panel                              |
        // +------------------------------------------------------------+

        rows = new TableRow();
        rows.addCell(new TableDataCell(con).setVAlign(IVAlign.TOP));
        //if (pSurveyCode.equals("1")) {
            rows.addCell(new TableDataCell(track).setVAlign(IVAlign.TOP));
        //} // if (pSurveyCode.equals("1"))
        tab_main.addRow(rows);

        if (pSurveyCode.equals("1") && pDataRecorded.startsWith("Y")) {

            DynamicTable quality_tab = new DynamicTable(9);
            quality_tab
                .setBorder(1)
                .setCellSpacing(1)
                .setCellPadding(3)
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL)
                .setBackgroundColor(new Color("FFFF99"))
                ;

            rows = new TableRow();
            rows
                .addCell(new TableHeaderCell(formatStr("Temperature : " + pTemperatureCnt)))
                .addCell(new TableHeaderCell(formatStr("Salinity : " + pSalinityCnt)))
                .addCell(new TableHeaderCell(formatStr("Dis Oxygen : " + pDisOxygenCnt)))
                .addCell(new TableHeaderCell(formatStr("Nitrate : " + pNo3Cnt)))
                .addCell(new TableHeaderCell(formatStr("Phosphate : " + pPo4Cnt)))
                .addCell(new TableHeaderCell(formatStr("Silicate : " + pSio3Cnt)))
                .addCell(new TableHeaderCell(formatStr("Chlorophyll : " + pChlaCnt)))
                .addCell(new TableHeaderCell(formatStr("DIC : " + pDICCnt)))
                .addCell(new TableHeaderCell(formatStr("pH : " + pPHCnt)))
                ;
            quality_tab.addRow(rows);


            String quality_file = pSurveyId.substring(0,4) + "_" +
                pSurveyId.substring(5,9) + ".pdf";

            String linkString = "http://sadco.int.ocean.gov.za/sadco-pdf/" + quality_file;
            Link link1 = new Link(linkString, "Click to view detail");


            Container con2 = new Container();
            con2
                .addItem(SimpleItem.LineBreak)
                .addItem(formatStr4("<b>Data Quality</b>").toHTML() +
                    " " + link1.toHTML())
                .addItem(SimpleItem.LineBreak)
                    .addItem(SimpleItem.LineBreak)
                .addItem(formatStr2("<b>Number of stations with flags " +
                    "for the following parameters</b> (-1 indicates parameter " +
                    "not present)"))
                .addItem(quality_tab)
                ;

            rows = new TableRow();
            rows.addCell(new TableDataCell(con2).setColSpan(2).setVAlign(IVAlign.TOP));
            tab_main.addRow(rows);

        } // if (pSurveyCode.equals("1"))

        //this.addItem(JSLLib.OpenScript())
        //    .addItem("function popup(mylink, windowname) {"+sc.CR)
        //    .addItem("  if (! window.focus)return true;"+sc.CR)
        //    .addItem("  var href;"+sc.CR)
        //    .addItem("  if (typeof(mylink) == 'string')"+sc.CR)
        //    .addItem("    href=mylink;"+sc.CR)
        //    .addItem("  else"+sc.CR)
        //    .addItem("    href=mylink.href;"+sc.CR)
        //    .addItem("  window.open(href, windowname, 'width=400,height=400,scrollbars=yes');"+sc.CR)
        //    .addItem("  return false;"+sc.CR)
        //    .addItem("}"+sc.CR)
        //    .addItem(JSLLib.CloseScript())
        this.addItem(tab_main.setCenter() );

        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
        sc.disConnect(stmt, rset, conn);

    } // DisplaySurvey constructor


    /**
     * Format text strings - font size 1
     */
    private static Container formatStr(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(1));
        return newStrc;
    } // formatStr


    /**
     * Format text strings - font size 2
     */
    private static Container formatStr2(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(2));
        return newStrc;
    } // formatStr2


    /**
     * Format text strings - font size 3
     */
    private static Container formatStr3(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(3));
        return newStrc;
    } // formatStr3


    /**
     * Format text strings - font size 4
     */
    private static Container formatStr4(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(4));
        return newStrc;
    } // formatStr4


    /**
     * if the value is not -1, return the sum of the two values
     * @param   count   value
     * @param   value   value to be added
     * returns sum of two values
     */
    int addValue(int count, String value) {
        int intValue = 0;
        if ((value != null) && !("-1".equals(value))) {
            intValue = Integer.parseInt(value);
            if (intValue > -1) intValue = 0;
            if (count == -1) {
                count = intValue;
            } else {
                count += intValue;
            } // if (count == -1)
        } // if (value != null)
        return count;
    } // addValue


    /**
     * Add options to sellOffline or selOnLIne, depending on the value of the count
     * @param   count   the record count
     * @param   index   the index to the string array HYDRO_EXTR
     */
    void addSelectOption(int count, int index) {
        if (count > 0) {
            String ii = String.valueOf(index);
            if (count > sc.MAX_HYDRO) {
                selOffline.addOption(new Option(sc.HYDRO_EXTR[index],ii,false));
                isOffline = true;
            } else {
                selOnline.addOption(new Option(sc.HYDRO_EXTR[index],ii,false));
                isOnline = true;
            } // if (watphyCnt > 10000)
        } // if (count > 0)
    } // addSelectOption



    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("DisplaySurvey local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new DisplaySurvey(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            // make sure everything is closed and null
            sc.disConnect(stmt, rset, conn);
        } // try-catch

    } // main

} // DisplaySurvey class
