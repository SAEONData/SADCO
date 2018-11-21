package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update zones
*
* @author  20070822 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070822 - Ursula von St Ange - change to package class              <br>
*/
public class UpdateZonesBlock extends CompoundItem {

    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();
    static SadCommon sc = new SadCommon();

    // Create java.sql stuff
//    String USER = "sadco";
//    String PWD = "ter91qes";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rset = null;
    String sql = "";

    // arguments
    String  pSurveyId       = "";
    String  zoneErr         = "";
    String  pUpdated        = "";
    String  pNewZoneCode    = "";

    // work variables
    String  pMarineZone[];
    int     paZoneCode[];
    String  paMarineZone[];
    int     pDuplicate  = 0;

    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public UpdateZonesBlock(String args[]) {

        TableRow rows = new TableRow();

        // +------------------------------------------------------------+
        // | Print SADCO logo                                           |
        // +------------------------------------------------------------+
        Image slogo = new Image
            ("http://sadco.ocean.gov.za/sadco-img/sadlogo.gif",
            "sadlog.gif", IVAlign.TOP, false);
        this.addItem(slogo.setCenter());

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
            this .addItem(new SimpleItem("There are no input parameters"))   ;
            return;
        }  //  if (args.length < 1)

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        pSurveyId       = sc.getArgument(args,"surveyid");
        zoneErr         = sc.getArgument(args,"zoneerr");
        pUpdated        = sc.getArgument(args,"updated");
        pNewZoneCode    = sc.getArgument(args,"newzonecode");

        // +------------------------------------------------------------+
        // | Add new marine zone if required                            |
        // +------------------------------------------------------------+
        zoneErr = "N";
        if (pUpdated.length() > 0) {
            addMarineZone(pSurveyId, pNewZoneCode);
            if (pDuplicate == 1) zoneErr = "Y";
        }  //  if (pUpdated.length() > 0)


        // +------------------------------------------------------------+
        // | Create the select list of zone names                       |
        // +------------------------------------------------------------+
        int marineZoneCount = countMarineZones();
        paZoneCode      = new int[marineZoneCount];
        paMarineZone    = new String[marineZoneCount];
        listAllMarineZones();
        Select selectMarineZoneList = new Select("newzonecode");
        for (int i = 0; i < marineZoneCount; i++) {
            selectMarineZoneList.addOption(new Option(
                paMarineZone[i],String.valueOf(paZoneCode[i]),false));
        }  //  for (int i = 0; i < marineZoneCount; i++)


        // +--------------------------------+
        // | Count the marine zones records |
        // +--------------------------------+
        int zoneCount = countZones(pSurveyId);
        pMarineZone    = new String[zoneCount];

        // +-----------+
        // | Get zones |
        // +-----------+
        if (zoneCount > 0) {
            listZones(pSurveyId);
        }  //  if (zoneCount > 0)

        // +------------------------------------------------------------+
        // | Create the form to update the marine Zones block           |
        // +------------------------------------------------------------+
        Form newfrm = new Form("GET", "SurvSum");
        newfrm
            .addItem(new Hidden("pscreen", "UpdateZonesBlock"))
            .addItem(new Hidden("updated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("zoneerr", zoneErr))

            .addItem(new SimpleItem("Marine Zone: "))
            .addItem(selectMarineZoneList)
            ;

        // +------------------------------------------------------------+
        // | Display        duplicate error if there is a duplicate     |
        // +------------------------------------------------------------+
        if (zoneErr.equals("Y")) {
            newfrm .addItem(new SimpleItem(
                "<br><FONT COLOR=#FF6666>ERR: Marine Zone already recorded</font><br>").setBold()   );
        }  //  if (zoneErr.equals("Y"))

        newfrm
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit selected marine zone!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Create Main table                                          |
        // +------------------------------------------------------------+
        DynamicTable tab_main  = new DynamicTable(1);
        tab_main
            .setBorder(1)
            .setCellSpacing(1)
            .setWidth("100%")
            .setBackgroundColor("#33CCFF")
            .setFrame(ITableFrame.BOX)
            ;

        // +------------------------------------------------------------+
        // | Create title   table                                       |
        // +------------------------------------------------------------+
        DynamicTable tab_titl  = new DynamicTable(2);
        tab_titl
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>SURVEY SUMMARY REPORT</b>")).setColSpan(80))
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>" + pSurveyId + "</b")))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Create Project         table                               |
        // +------------------------------------------------------------+
        DynamicTable tab_zone  = new DynamicTable(2);
        tab_zone
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Specific Areas (zones)</u></b>")).setColSpan(80));
        tab_zone.addRow(rows);

        // +-----------------------------------+
        // | Only process if there are records |
        // +-----------------------------------+
        if (zoneCount > 0) {
            for (int i = 0; i < zoneCount; i++) {
                rows = new TableRow();
                rows.addCell(new TableDataCell(
                    "&nbsp;&nbsp; <b>" + pMarineZone[i] + "</b>"));
                tab_zone.addRow(rows.setIVAlign(IVAlign.TOP));
            } // for (int i = 0; i < zoneCount; i++)
        } // if (zoneCount > 0)


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_zone).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());


        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateTCountryBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update Survey Country Block")   )
            ;

        // +------------------------------------------------------------+
        // | Create the completed button                                |
        // +------------------------------------------------------------+
        Form close = new Form("GET", "SurvSum");
        close
            .addItem(new Hidden("pscreen", "DisplaySurvsum"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("passcode", "ter91qes"))
            .addItem(new Submit("submit", "<<< Survey Summary")   )
            ;

        DynamicTable tab_buttons = new DynamicTable(2);
        tab_buttons
            .setBorder(0)
            .setCellSpacing(0)
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(close))
            .addCell(new TableHeaderCell(nextBlock))
            ;
        tab_buttons.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Create Update table                                        |
        // +------------------------------------------------------------+
        DynamicTable tab_update  = new DynamicTable(2);
        tab_update
            .setBorder(2)
            .setCellSpacing(2)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        // +------------------------------------------------------------+
        // | Choose from listing                                        |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Choose Marine Zone from Listing"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(newfrm));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Add closing buttons                                        |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell(tab_buttons));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        this.addItem(tab_update.setCenter());

        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
        try {
            if (dbg) System.err.println("Before conn.close()");
            //conn.close();
            sc.disConnect(stmt, rset, conn);
            if (dbg) System.err.println("After conn.close()");
        }  catch (Exception e){}

    } // constructor UpdateZonesBlock



    /**
     * Add new marine zone record
     * @param   pSurveyId
     * @param   pNewZoneCode
     */
    void addMarineZone(String pSurveyId, String pNewZoneCode) {

        try {

            // does the zone exist
            sql = "select count(*) from zones_covered " +
                  "where survey_id =  '" + pSurveyId +
                  "' and zone_code =  " + pNewZoneCode;
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pDuplicate = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            if (pDuplicate == 0) {

                sql = "insert into zones_covered values ('" +
                      pSurveyId + "', " + pNewZoneCode + ")";
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;
                if (dbg) System.out.println("<br>addMarineZone: num = " + num);
                if (dbg) System.out.println("<br>addMarineZone: sql = " + sql);

            } // if (pDuplicate == 0)

        } catch (Exception e) {
            this.addItem("<br>Could not process addMarineZone: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // addMarineZone


    /**
     * Get the number of marine zones
     * @returns number of marine zones
     */
    int countMarineZones() {

        int count = 0;

        try {

            sql = "select count(*) from marine_zones";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
                count = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process countMarineZones: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return count;

    } // countMarineZones


    /**
     * List all Marine Zones
     */
    void listAllMarineZones() {

        try {

            sql = "select code, marine_zone " +
                  "from marine_zoneS order by marine_zone";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                paZoneCode[i]   = (int)rset.getFloat(1);
                paMarineZone[i] = sc.spaceIfNull(rset.getString(2));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listAllMarineZones: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listAllMarineZones


    /**
     * Get the number of marine zones
     * @returns number of marine zones
     */
    int countZones(String pSurveyId) {

        int count = 0;

        try {

            sql = "select count(*) from zones_covered " +
                  "where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
                count = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process countMarineZones: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return count;

    } // countMarineZones


    /**
     * List marine zones
     */
    void listZones(String pSurveyId) {

        try {

            sql = "select marine_zone from zones_covered, marine_zones " +
                  "where survey_id = '" + pSurveyId + "' and " +
                  "zones_covered.zone_code = marine_zones.code";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                pMarineZone[i] = sc.spaceIfNull(rset.getString(1));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listZones: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listZones


    /**
     * Look up a URL parameter
     * @param   args    list of url name-value parameter pairs
     * @param   name    name of parameter to find value for
     */
//    private static String getArgument(String args[], String name) {
//        String prefix = name + "=";
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].startsWith(prefix) ) {
//                return args[i].substring(prefix.length());
//            }  //  if (args[i].startsWith(prefix) )
//        }  //  for (int i = 0; i < args.length; i++)
//        return "";
//    } // getArgument


    /**
     * Format text strings for html
     * @param   oldStr  the text to format
     */
//    private static Container formatStr(String oldStr) {
//        Container newStrc = new Container();
//        newStrc.addItem(new SimpleItem(oldStr).setFontSize(2));
//        return newStrc;
//    }  //  private static Container formatStr(String oldStr)


    /**
     * Format text strings for html (big)
     * @param   oldStr  the text to format
     */
//    private static Container formatStrBig(String oldStr) {
//        Container newStrc = new Container();
//        newStrc.addItem(new SimpleItem(oldStr).setFontSize(3));
//        return newStrc;
//    } // formatStrBig


    /**
     * Return space iso null
     * @param   text    text to test for null
     */
//    public String spaceIfNull(String text) {
//        return (text == null ? "" : text);
//    } // spaceIfNull


    /**
     * main class for testing purposes
     * @param   args    list of URL name-value parameters
     */
    public static void main(String args[]) {

        HtmlHead hd = new HtmlHead("UpdateZonesBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateZonesBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateZonesBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdateZonesBlock
