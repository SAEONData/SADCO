package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update the data block
*
* @author  20070817 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070817 - Ursula von St Ange - change to package class              <br>
*/
public class UpdatePlatformBlock extends CompoundItem {

    //boolean dbg = true;
    boolean dbg = false;

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

    // input parameters
    String pSurveyId         = "";
    String planamErr        = "";
    String callSignErr      = "";
    String pNewUpdated      = "";
    String pExistUpdated    = "";
    String pNewPlanamName   = "";
    String pExistPlanamCode = "";
    String pNewCallSign     = "";
    String pNewPlatformName = "";

    // database variables
    int    pPlanamCodeI     = 0;
    String pPlanamNameI     = "";
    String pCallSignI       = "";
    int    pPlatformCodeI   = 0;
    String pPlatformNameI   = "";
    int    pPlanamCode[];
    String pPlanamName[];
    int    pPlatformCode[];
    String pPlatformName[];
    int    pPlanamCount     = 0;
    int    pCallSignCount   = 0;
    int    pRecordcount     = 0;



    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public UpdatePlatformBlock(String args[]) {

        TableRow rows = new TableRow();

        // +------------------------------------------------------------+
        // | Print SADCO logo                                           |
        // +------------------------------------------------------------+
        Image slogo = new Image
            ("http://sadco.int.ocean.gov.za/sadco-img/sadlogo.gif",
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
        pSurveyId = sc.getArgument(args,"surveyid");
        planamErr   = sc.getArgument(args,"planamerr");
        callSignErr = sc.getArgument(args,"callsignerr");
        if (dbg) System.out.println("<br>surveyid = " + pSurveyId);
        if (dbg) System.out.println("<br>planamerr = " + planamErr);
        if (dbg) System.out.println("<br>callsignerr = " + callSignErr);

        pNewUpdated          = sc.getArgument(args,"newUpdated");
        pExistUpdated        = sc.getArgument(args,"existUpdated");
        pNewPlanamName       = sc.getArgument(args,"newPlanamName");
        pExistPlanamCode     = sc.getArgument(args,"existPlanamCode");
        pNewCallSign         = sc.getArgument(args,"newCallSign");
        pNewPlatformName     = sc.getArgument(args,"newPlatformName");
        if (dbg) System.out.println("<br>newUpdated = " + pNewUpdated);
        if (dbg) System.out.println("<br>existUpdated = " + pExistUpdated);
        if (dbg) System.out.println("<br>newPlanamName = " + pNewPlanamName);
        if (dbg) System.out.println("<br>existPlanamCode = " + pExistPlanamCode);
        if (dbg) System.out.println("<br>newCallSign = " + pNewCallSign);
        if (dbg) System.out.println("<br>newPlatformName = " + pNewPlatformName);

        // +----------------------------------------------------------+
        // | Count the number of platform types, and create variables |
        // +----------------------------------------------------------+
        // update inventory record with an existing platform
        if (pExistUpdated.length() > 0) {
            updatePlatformBlock(pSurveyId, pExistPlanamCode);
        }  //  if (pNewDataCentre.length() > 0)

        planamErr = "N";
        callSignErr = "N";
        // update inventory with a new platform
        if (pNewUpdated.length() > 0) {
            addPlatformBlock(pSurveyId, pNewPlanamName, pNewCallSign,
                pNewPlatformName);

            if (dbg) System.out.println("<br>pPlanamCount = " + pPlanamCount);
            if (dbg) System.out.println("<br>pCallSignCount = " + pCallSignCount);

            if (1 == pPlanamCount) planamErr = "Y";
            if (1 == pCallSignCount) callSignErr = "Y";

        }  //  if (pNewDataCentre.length() > 0)

        // get the inventory record again
        getPlatformBlock(pSurveyId);

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
        // | Create title table                                         |
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
            .addCell(new TableHeaderCell(
                sc.formatStrBig("<b>SURVEY SUMMARY REPORT</b>")).setColSpan(80))
            .addCell(new TableHeaderCell(
                sc.formatStrBig("<b>" + pSurveyId + "</b")))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Create platform table                                      |
        // +------------------------------------------------------------+
        DynamicTable tab_plat  = new DynamicTable(2);
        tab_plat
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Platform</u></b>")));
        tab_plat.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;Name: <b>" +
                pPlanamNameI +
                "</b><br>&nbsp;&nbsp;&nbsp;&nbsp;Type: <b>" +
                pPlatformNameI + "</b>"
                )))
            .addCell(new TableDataCell(sc.formatStr(
                "Call Sign: <b>" +
                pCallSignI  + "</b>"
                )))
            ;
        tab_plat.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_plat).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());


        // +------------------------------------------------------------+
        // | Create the select list of platform names                   |
        // +------------------------------------------------------------+
        int planamCount = countPlanam(); // + 1;
        pPlanamCode    = new int[planamCount];
        pPlanamName    = new String[planamCount];
        listAllPlanam();
        Select selectPlanamList = new Select("existPlanamCode");
        for (int i = 0; i < planamCount; i++) {
            if (pPlanamName[i] != null) {
                if (dbg) System.out.println(
                    "<br>pPlanamCode[i] = " +
                    pPlanamCode[i]);
                selectPlanamList.addOption(new Option(
                    pPlanamName[ i],
                    String.valueOf(pPlanamCode[i]),
                    sc.getDefault(pPlanamCode[i],pPlanamCodeI)
                    ));
            } // if (pPlanamCode[i].length() > 0)
        }  //  for (int i = 0; i < planamCount; i++)


        // +------------------------------------------------------------+
        // | Create the select list of platform types                   |
        // +------------------------------------------------------------+
        int platformCount = countPlatform(); // + 1;
        pPlatformCode  = new int[platformCount];
        pPlatformName  = new String[platformCount];
        listAllPlatform();
        Select selectPlatformList = new Select("newPlatformName");
        for (int i = 0; i < platformCount; i++) {
            selectPlatformList.addOption(new Option(
                pPlatformName[i],
                String.valueOf(pPlatformCode[i]),
                sc.getDefault(pPlanamCode[i],pPlatformCodeI)
                ));
        }  //  for (int i = 0; i < platformCount; i++)


        // +------------------------------------------------------------+
        // | Create the form to update the data block                   |
        // +------------------------------------------------------------+
        Form existfrm = new Form("GET", "SurvSum");
        existfrm
            .addItem(new Hidden("pscreen", "UpdatePlatformBlock"))
            .addItem(new Hidden("existUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))

            .addItem(selectPlanamList)

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("", "Submit selected platform!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Create the form to add new    data block                   |
        // +------------------------------------------------------------+
        Form newfrm = new Form("GET", "SurvSum");
        newfrm
            .addItem(new Hidden("pscreen", "UpdatePlatformBlock"))
            .addItem(new Hidden("newUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("callsignerr", callSignErr))
            .addItem(new Hidden("planamerr", planamErr))

            .addItem(new SimpleItem("Platform name: "))
            .addItem(new TextField("newPlanamName", 20, 20, pNewPlanamName))
            ;

        // +------------------------------------------------------------+
        // | Display planam duplicate error if there is a duplicate     |
        // +------------------------------------------------------------+
        if (planamErr.equals("Y")) {
            newfrm .addItem(new SimpleItem(
                "<br><FONT COLOR=#FF6666>ERR: Platform already recorded</font><br>").setBold()   );
        }  //  if (planamErr.equals("Y"))

        // +------------------------------------------------------------+
        // | Get callsign                                               |
        // +------------------------------------------------------------+
        newfrm
            .addItem(new SimpleItem("<br>Call Sign: "))
            .addItem(new TextField("newCallSign", 7, 7, pNewCallSign))
            ;

        // +------------------------------------------------------------+
        // | Display callsign duplicate error if there is a duplicate   |
        // +------------------------------------------------------------+
        if (callSignErr.equals("Y")) {
            newfrm .addItem(new SimpleItem(
                "<br><FONT COLOR=#FF6666>ERR: Callsign already recorded</font><br>").setBold()   );
        }  //  if (planamErr.equals("Y"))

        newfrm
            // +------------------------------------------------------------+
            // | Select a platform type from a listing                      |
            // +------------------------------------------------------------+
            .addItem(new SimpleItem("<br>Type: "))
            .addItem(selectPlatformList)

            // +------------------------------------------------------------+
            // | Produce buttons to submit or reset                         |
            // +------------------------------------------------------------+
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("", "Submit new platform!") )
            .addItem(new Reset   ("Reset") )
            ;


        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateSurvNamBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("", ">>> Update Survey Name")   )
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

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell("Choose Platform from Listing").setBackgroundColor(Color.cyan))
            .addCell(new TableHeaderCell("Register New Platform").setBackgroundColor(Color.cyan))
            ;
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(existfrm))
            .addCell(new TableDataCell(newfrm))
            ;
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(tab_buttons).setBackgroundColor(Color.cyan))
            .addCell(new TableHeaderCell("&nbsp;").setBackgroundColor(Color.cyan))
            ;
        tab_update.addRow(rows);


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

    } // UpdatePlatformBlock constructor


    /**
     * Get the number of platform types
     */
    int countPlatform() {

        int platformCount = 0;

        try {

            sql = "select count(*) from platform";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
               platformCount = (int)rset.getFloat(1);
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process countPlatform: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return platformCount;
    } // countPlatform


    /**
     * Get the number of platforms
     */
    int countPlanam() {
        int planamCount = 0;

        try {

            sql = "select count(*) from planam";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
               planamCount = (int)rset.getFloat(1);
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process countPlanam: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return planamCount;
    } // countPlanam


    /**
     * Update Platform block with platform / ship code
     * used for an existing ship on the database
     * @param   pSurveyId    surveyId of cruise
     * @param   planamCode  code of platform / ship
     */
    void updatePlatformBlock(String pSurveyId, String planamCode){

        try {

            sql = "update inventory set planam_code = " + planamCode +
                  " where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.err.println("<br>updatePlatformBlock: cnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updatePlatformBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updatePlatformBlock


    /**
     * Add new Platform block - update ship / platform information
     * used for a new ship to be added to the database
     * @param   pSurveyId    surveyId of cruise
     * @param   planamName  name of platform / ship
     * @param   callSign    call sign of platform / ship
     * @param   platformName    type of platform / ship
     */
    void addPlatformBlock(String pSurveyId, String planamName, String callSign,
        String platformName) {

        try {

            // check whether platform / ship is already on database
            sql = "select count(*) from planam where upper(name) = upper('" +
                planamName + "')";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
               pPlanamCount = (int)rset.getFloat(1);
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // check whether callsign is already on database
            sql = "select count(*) from planam where upper(callsign) = upper('" +
                callSign + "')";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
               pCallSignCount = (int)rset.getFloat(1);
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // update the inventory records
            if ((pPlanamCount == 0) && (pCallSignCount == 0)) {

                // get platform type code
                int platformCode = 0;
                sql = "select code from platform where name = '" +
                    platformName + "'";
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                while (rset.next()) {
                   platformCode = (int)rset.getFloat(1);
                } //while (rset.next()) {
                rset.close();
                stmt.close();
            rset = null;
            stmt = null;

                // get next platform /ship code to use
                int planamCode = 0;
                sql = "select max(code) from planam";
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                while (rset.next()) {
                   planamCode = (int)rset.getFloat(1);
                } //while (rset.next()) {
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;
                planamCode++;

                // insert new platform / ship into database
                sql = "insert into planam values(" + planamCode + ", '" +
                    planamName + "', "+ platformCode + ", '" + callSign +
                    "', null, null)";
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                stmt = null;
                stmt.close();

                // update the inventory with the new details
                sql = "update inventory set planam_code = " + planamCode +
                      " where survey_id = '" + pSurveyId + "'";
                stmt = conn.createStatement();
                num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

                if (dbg) System.err.println("<br>addPlatformBlock: cnt = " + num);

            } // if ((pPlanamCount == 0) && (pCallSignCount == 0))

        } catch (Exception e) {
            this.addItem("<br>Could not process addPlatformBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // addPlatformBlock


    /**
     * Get Platform block information
     * @param   surveyId    surveyId of cruise
     */
   void getPlatformBlock(String pSurveyId) {

        try {

            sql = "select planam.code, planam.name, callsign, " +
                  "platform_code, platform.name " +
                  "from inventory, planam, platform where " +
                  "survey_id = '" + pSurveyId + "' and " +
                  "planam.code = inventory.planam_code and " +
                  "platform.code = planam.platform_code";

            if (dbg) System.out.println("<br>getPlatformBlock: sql = " + sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
                pPlanamCodeI   = (int) rset.getFloat(1);
                pPlanamNameI   = sc.spaceIfNull(rset.getString(2));
                pCallSignI     = sc.spaceIfNull(rset.getString(3));
                pPlatformCodeI = (int) rset.getFloat(4);
                pPlatformNameI = sc.spaceIfNull(rset.getString(5));
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process getPlatformBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // getPlatformBlock


    /**
     * List all Platforms
     */
    void listAllPlanam() {

        try {

            sql = "select code, name from planam order by upper(name)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                pPlanamCode[i] = (int)rset.getFloat(1);
                pPlanamName[i]  = sc.spaceIfNull(rset.getString(2));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listAllPlanam: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listAllPlanam


    /**
     * List all Platforms
     */
    void listAllPlatform() {

        try {

            sql = "select code, name from platform order by upper(name)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                pPlatformCode[i] = (int)rset.getFloat(1);
                pPlatformName[i]  = sc.spaceIfNull(rset.getString(2));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listAllPlatform: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listAllPlanam


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
//    } // formatStr


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
     * Identify the select item that is to be flagged as default
     * @param    item
     * @param    prevSelect
     * @return   true/false
     */
//    private boolean getDefault(int item, int prevSelect) {
//        return (item == prevSelect ? true : false);
//    } // getDefault


    /**
     * main class for testing purposes
     * @param   args    list of URL name-value parameters
     */
    public static void main(String args[]) {

        HtmlHead hd = new HtmlHead("UpdatePlatformBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdatePlatformBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdatePlatformBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdatePlatformBlock
