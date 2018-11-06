package sadinv;

import oracle.html.*;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;


/**
 * This class generates the code to display the google map

 * SadInv
 * pscreen = kml
 * psurveyid = <survey Id>
 *
 * @author 20151210 - SIT Group.
 * @version
 * 20151210 - Ursula von St Ange - create program                       <br>
 */

public class DisplayKML extends CompoundItem  {


    boolean dbg = false;
    //boolean dbg = true;
    boolean dbg2 = false;
    //boolean dbg2 = true;

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();
/*
    static private Connection conn = null;
    static private Statement stmt = null;
    static private ResultSet rset = null;
    static private Statement stmt2 = null;
    static private ResultSet rset2 = null;
    String sql = "";
    String sql2 = "";
*/
    String nl = "\r\n";
//    static final String NBSP = "&nbsp;&nbsp;&nbsp;&nbsp;";

    // for the drop-down lists
//    Select selOnline = new Select(sc.EXTRTYPE);
//    Select selOffline = new Select(sc.EXTRTYPE);
//    boolean isOnline = false;
//    boolean isOffline = false;
//    boolean isPhysNut = false;

    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public DisplayKML(String args[]) {


        // +------------------------------------------------------------+
        // | Add the SADCO banner at the top                            |
        // +------------------------------------------------------------+
        Image banner = new Image
            ("http://sadco.int.ocean.gov.za/sadco-img/banner.jpg",
            "banner.jpg",
            IVAlign.TOP, false);

        this.addItem(banner);

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
        String sessionCode = "";

        if (dbg) for (int i = 0; i < args.length; i++)
            System.out.println("args["+i+"] = " + args[i]);

        if (dbg2) {
            //1992/0003 Y,1993/0009 Y,1993/0010 Y,
            //psurveyid=1974/0001&psc=2791&pavailable=Y
            surveyId_in = "2015/0280";
        } // if (dbg2)

        // +------------------------------------------------------------+
        // | Get connected to the db                                    |
        // +------------------------------------------------------------+
//        conn = sc.getConnected(thisClass, sessionCode);

        // +------------------------------------------------------------+
        // | if logged in, then get survey list from db                 |
        // +------------------------------------------------------------+
//        String nextSurvey = "";
//        if (!"".equals(version)) {
//            nextSurvey = sc.getNextSurvey(conn, sessionCode, surveyId_in, available);
//            if (dbg) System.out.println("nextSurvey1 = " + nextSurvey);
//            if (!"".equals(nextSurvey)) {
//                surveyId_in = nextSurvey.substring(0,9);
//                available = nextSurvey.substring(10);
//            } // if (!"".equals(nextSurvey))
//        } // if (!"".equals(version))

//        // also get the following survey - for the Next button
//        nextSurvey = sc.getNextSurvey(conn, sessionCode, surveyId_in, available);
//        if (dbg) System.out.println("nextSurvey2 = " + nextSurvey);

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

        this.addItem(nl);
        this.addItem("<p><b>Survey ID:</b> " + surveyId_in + "</p>");
        this.addItem(nl);
        this.addItem("<p>Please click on the green markers for more information</p>");
        this.addItem(nl);
//        this.addItem(nl+"<br><br>"+nl);

/*
        // +------------------------------------------------------------+
        // | Prepare for PL/SQL values                                  |
        // +------------------------------------------------------------+
        String  pDataRecorded   = "";
        String  pSurveyCode     = "";

        // +------------------------------------------------------------+
        // | Retrieve the survey information from the packaged PL/SQL   |
        // | using procedure display_survey                             |
        // +------------------------------------------------------------+
        try {

            sql =
                " select data_recorded, survey_type_code"+
                " from inventory" +
                " where survey_id = '"+pSurveyId+"'";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (dbg) System.out.println("<br> sql "+sql);

            while (rset.next()) {
                pDataRecorded  = sc.checkNull(rset.getString(1));
                pSurveyCode    = sc.checkNull(rset.getString(2));
            } // while (rset.next())

            if (dbg) System.out.println("<br> pDataRecorded = " + pDataRecorded);

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.err.println("<br>sql = "+sql);
            System.err.println("<br>message = "+e.getMessage());
            e.printStackTrace();
        }  //  catch (SQLException e)
*/
//        Image track = new Image
//            ("http://sadco.int.ocean.gov.za/sadco-img/noload.gif",
//             "DATA NOT LOADED",
//             IVAlign.TOP, true);

        // +------------------------------------------------------------+
        // | Create button for next survey                              |
        // +------------------------------------------------------------+
//        String nextSurvButtonStr = "";
//        if (!"".equals(nextSurvey)) {
//            Form nextSurvButton = new Form("GET", sc.APP);
//            nextSurvButton
//                .addItem(new Hidden(sc.SURVEYID, pSurveyId) )
//                .addItem(new Hidden(sc.PSC, sessionCode))               //ub03
//                .addItem(new Hidden(sc.SCREEN, sc.KML))
//                .addItem(new Hidden(sc.VERSION, "next"))
////                .addItem(new Hidden(sc.AVAILABLE, available))
//                .addItem(new Submit("Submit", "Next"))
//                ;
//            nextSurvButtonStr = nextSurvButton.toHTML();
//        } // if (!"".equals(sessionCode))

/*        DynamicTable tab_main  = new DynamicTable(2);
        tab_main
            .setBorder(0)
            .setCellSpacing(0)
            .setBackgroundColor(Color.white)
            .setFrame(ITableFrame.VOLD)
            .setRules(ITableRules.NONE)
*/            ;

/*        DynamicTable tab_dates = new DynamicTable(2);
        tab_dates
            .setBorder(1)
            .setCellSpacing(0)
            .setBackgroundColor(new Color("FFFF99"))
            .setFrame(ITableFrame.BOX)
            .setRules(ITableRules.ALL)
*/            ;


//        if (dbg) System.out.println("pSurveyCode = " + pSurveyCode);


//        if (pSurveyCode.equals("1")) { // hydro
            // +------------------------------------------------------------+
            // | If the data is stored on the SADCO database, a track chart |
            // | will be available.                                         |
            // +------------------------------------------------------------+

//            if (pDataRecorded.startsWith("Y")) {

                // +------------------------------------------------------------+
                // | create the kml file name                                   |
                // +------------------------------------------------------------+
                String kmlFile =
                    "c" +
                    pSurveyId.substring(0,4) +
                    pSurveyId.substring(5,9) +
                    ".kml";

                this.addItem("<iframe " +
                    "src=\"http://sadco.int.ocean.gov.za/sadco-img/kml/fake-kmlgadget.html?" +
                    "up_kml_url=http://sadco.int.ocean.gov.za/sadco-img/kml/" + kmlFile +
                    "&up_view_mode=maps&up_lat=&up_lng=&up_zoom=&" +
                    "up_earth_fly_from_space=1&up_earth_show_nav_controls=1&" +
                    "up_earth_show_buildings=1&up_earth_show_terrain=1&" +
                    "up_earth_show_roads=1&up_earth_show_borders=1&" +
                    "up_earth_sphere=earth&up_maps_streetview=0&" +
                    "up_maps_default_type=map\" height=\"500\" width=\"800\"></iframe>");
                this.addItem(nl);


//                DynamicTable stats_tab1 = new DynamicTable(4);
//                stats_tab1
//                    .setBorder(1)
//                    .setCellSpacing(1)
//                    .setFrame(ITableFrame.BOX)
//                    .setRules(ITableRules.ALL)
//                    .setBackgroundColor(new Color("FFFF99"))
//                    ;

//                rows = new TableRow();
//                rows
//                    .addCell(new TableHeaderCell(formatStr2("Number Of Stations : "))
//                        .setBackgroundColor(new Color("FFCC66")))
//                    .addCell(new TableHeaderCell(formatStr2(pStationCnt)))
//                    //.addCell(new TableHeaderCell(formatStr("Weather : "))
//                    //    .setBackgroundColor(new Color("FFCC66")))
//                    //.addCell(new TableHeaderCell(formatStr(pWeatherCnt)))
//                    ;
//                stats_tab1.addRow(rows);

//                con
//                    .addItem(SimpleItem.LineBreak)
//                    .addItem(formatStr4("<b>Database Statistics</b>"))
//                    .addItem(SimpleItem.LineBreak)
//                    .addItem(SimpleItem.LineBreak)
//                    .addItem(stats_tab1)
//                    .addItem(SimpleItem.LineBreak)
//                    .addItem(stats_tab2)
//                    .addItem(SimpleItem.LineBreak)
//                    ;

//            }  //  if pDataStored.equals("Y")

//        } // if (pSurveyCode.equals("1"))
/*
        // +------------------------------------------------------------+
        // | Insert the information panel                               |
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
*/
        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
//        sc.disConnect(stmt, rset, conn);

    } // DisplayKML constructor


    /**
     * Format text strings - font size 1
     */
//    private static Container formatStr(String oldStr) {
//        Container newStrc = new Container();
//        newStrc.addItem(new SimpleItem(oldStr).setFontSize(1));
//        return newStrc;
//    } // formatStr


    /**
     * Format text strings - font size 2
     */
//    private static Container formatStr2(String oldStr) {
//        Container newStrc = new Container();
//        newStrc.addItem(new SimpleItem(oldStr).setFontSize(2));
//        return newStrc;
//    } // formatStr2


    /**
     * Format text strings - font size 3
     */
/*    private static Container formatStr3(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(3));
        return newStrc;
    } // formatStr3
*/

    /**
     * Format text strings - font size 4
     */
/*    private static Container formatStr4(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(4));
        return newStrc;
    } // formatStr4
*/

    /**
     * if the value is not -1, return the sum of the two values
     * @param   count   value
     * @param   value   value to be added
     * returns sum of two values
     */
/*    int addValue(int count, String value) {
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
*/

    /**
     * Add options to sellOffline or selOnLIne, depending on the value of the count
     * @param   count   the record count
     * @param   index   the index to the string array HYDRO_EXTR
     */
/*    void addSelectOption(int count, int index) {
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
*/


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("DisplayKML local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new DisplayKML(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            // make sure everything is closed and null
//            sc.disConnect(stmt, rset, conn);
        } // try-catch

    } // main

} // DisplayKML class
