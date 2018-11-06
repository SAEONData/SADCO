package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update the GIS block
*
* @author  20070817 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070817 - Ursula von St Ange - change to package class              <br>
*/
public class UpdatePSDdataBlock extends CompoundItem {

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
    String  pSurveyId           = "";
    String  pRecUpdated         = "";
    String  pEProjectionCode    = "";
    String  pESpheroidCode      = "";
    String  pEDatumCode         = "";

    // database variables
    int     pProjectionCode   = 0;
    String  pProjection       = "";
    int     pSpheroidCode     = 0;
    String  pSpheroid         = "";
    int     pDatumCode        = 0;
    String  pDatum            = "";
    int     plProjectionCode  [];
    String  plProjection      [];
    int     plSpheroidCode    [];
    String  plSpheroid        [];
    int     plDatumCode       [];
    String  plDatum           [];

//    static String recordErr    ;


    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public UpdatePSDdataBlock(String args[]) {

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
        pSurveyId           = sc.getArgument(args,"surveyid");
        pRecUpdated         = sc.getArgument(args,"recUpdated");
        pEProjectionCode    = sc.getArgument(args,"projectioncode");
        pESpheroidCode      = sc.getArgument(args,"spheroidcode");
        pEDatumCode         = sc.getArgument(args,"datumcode");

        if (dbg) System.out.println("<br>pEProjectionCode1 = " + pEProjectionCode);
        if (dbg) System.out.println("<br>pESpheroidCode1 = " + pESpheroidCode);
        if (dbg) System.out.println("<br>pEDatumCode1 = " + pEDatumCode);

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
        // | Create title table and add to the main table               |
        // +------------------------------------------------------------+
        DynamicTable tab_titl  = new DynamicTable(3);
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
        // | Create the buttons table                                   |
        // +------------------------------------------------------------+
        DynamicTable tab_buttons = new DynamicTable(1);
        tab_buttons
            .setBorder(0)
            .setCellSpacing(0)
            .setFrame(ITableFrame.BOX)
            ;

        // +------------------------------------------------------------+
        // | Create listing         table                               |
        // +------------------------------------------------------------+
        DynamicTable tab_list  = new DynamicTable(1);
        tab_list
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        // +------------------------------------------------------------+
        // | Update the inventory record   if required                  |
        // +------------------------------------------------------------+
        if (pRecUpdated.equals("Y")) {
            updatePsdDataBlock(pSurveyId, pEProjectionCode, pESpheroidCode,
                pEDatumCode);
         }  //  if (pExistUpdated.length() > 0)

        // +------------------------------------------------------------+
        // | Retrieve the PSD data                                      |
        // +------------------------------------------------------------+
        getPsdData(pSurveyId);


        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>PSD Data</u></b>")).setColSpan(80));
        tab_list.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig(
            "Projection: <b>" + pProjection +
            "<br></b>Spheroid: <b>" + pSpheroid +
            "<br></b>Datum: <b>" + pDatum +
            "</b>"
            )))
            ;
        tab_list.addRow(rows);

        // +------------------------------------------------------------+
        // | Projection select list                                     |
        // +============================================================+
        int projectionCount = countProjection();
        plProjectionCode= new int   [projectionCount];
        plProjection    = new String[projectionCount];
        listProjections();
        Select selectProjection    = new Select("projectioncode");
        for (int i = 0; i < projectionCount; i++) {
            selectProjection.addOption(new Option(
                plProjection[i],
                String.valueOf(plProjectionCode[i]),
                sc.getDefault(plProjectionCode[i],pProjectionCode)
                ));
        }  //  for (int i = 0; i < projectionCount; i++)

        // +------------------------------------------------------------+
        // | Spheroid   select list                                     |
        // +============================================================+
        int spheroidCount = countSpheroid();
        plSpheroidCode  = new int   [spheroidCount];
        plSpheroid      = new String[spheroidCount];
        listSpheroids();
        Select selectSpheroid      = new Select("spheroidcode");
        for (int i = 0; i < spheroidCount; i++) {
            selectSpheroid.addOption(new Option(
                plSpheroid[i],
                String.valueOf(plSpheroidCode[i]),
                sc.getDefault(plSpheroidCode[i],pSpheroidCode)
                ));
        }  //  for (int i = 0; i < spheroidCount; i++)

        // +------------------------------------------------------------+
        // | datum      select list                                     |
        // +============================================================+
        int datumCount = countDatum();
        plDatumCode     = new int   [datumCount];
        plDatum         = new String[datumCount];
        listDatums();
        Select selectDatum      = new Select("datumcode");
        for (int i = 0; i < datumCount; i++) {
            selectDatum.addOption(new Option(
                plDatum[i],
                String.valueOf(plDatumCode[i]),
                sc.getDefault(plDatumCode[i],pDatumCode)
                ));
        }  //  for (int i = 0; i < datumCount; i++)


        // +------------------------------------------------------------+
        // | Create the form to Add new sampling batch record           |
        // +------------------------------------------------------------+
        Form newFrm = new Form("GET", "SurvSum");
        newFrm
//            .addItem(new Hidden("recorderr", recordErr))
            .addItem(new Hidden("pscreen", "UpdatePSDdataBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("recUpdated", "Y"))

            //.addItem(new Hidden("projectioncode", "1"))
            .addItem(new SimpleItem("Projection: "))
            .addItem(selectProjection)

            .addItem(new SimpleItem("<br>Spheroid: "))
            .addItem(selectSpheroid)

            //.addItem(new Hidden("datumcode", "1"))
            .addItem(new SimpleItem("<br>Datum: "))
            .addItem(selectDatum)

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit PSD data") )
            .addItem(new Reset   ("Reset") )
            ;

        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Edit PSD Data"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(newFrm)) ;
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateSamplingBatches"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update Sampling Batches Block")   )
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

        rows = new TableRow();
        rows.addCell(new TableHeaderCell(close));
        rows.addCell(new TableHeaderCell(nextBlock));
        tab_buttons.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Add Title table to the Main table                          |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        // +------------------------------------------------------------+
        // | Add List  table to the Main table                          |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_list).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());


        // +------------------------------------------------------------+
        // | Add closing buttons to the main table                      |
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

    } // constructor UpdatePSDdataBlock()


    /**
     * Update GIS Data block
     * @param   pSurveyId       surveyId of cruise
     * @param   pEProjectionCode
     * @param   pESpheroidCode
     * @param   pEDatumCode
     */
    void updatePsdDataBlock(String pSurveyId, String pEProjectionCode,
            String pESpheroidCode, String pEDatumCode){

        if (dbg) System.out.println("<br>pEProjectionCode2 = " + pEProjectionCode);
        if (dbg) System.out.println("<br>pESpheroidCode2 = " + pESpheroidCode);
        if (dbg) System.out.println("<br>pEDatumCode2 = " + pEDatumCode);

        try {

            sql = "update inventory set projection_code = " + pEProjectionCode +
                  ", spheroid_code = " + pESpheroidCode +
                  ", datum_code = " + pEDatumCode +
                  " where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.err.println("<br>updatePsdDataBlock: cnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updatePsdDataBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updatePsdDataBlock


    /**
     * Get GIS data
     * @param   surveyId    surveyId of cruise
     */
   void getPsdData(String pSurveyId) {

        try {

            // get codes
            sql = "select projection_code, spheroid_code, datum_code from inventory " +
                  " where survey_id = '" + pSurveyId + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                int j = 1;
                pProjectionCode = (int)rset.getFloat(j++);
                pSpheroidCode   = (int)rset.getFloat(j++);
                pDatumCode      = (int)rset.getFloat(j++);
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // get the projection name
            sql = "select name from projection " +
                   "where code = " + pProjectionCode;
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pProjection    = sc.spaceIfNull(rset.getString(1));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // get the spheroid name
            sql = "select name from spheroid " +
                   "where code = " + pSpheroidCode;
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pSpheroid   = sc.spaceIfNull(rset.getString(1));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // get the datum name
            sql = "select name from datum " +
                   "where code = " + pDatumCode;
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pDatum      = sc.spaceIfNull(rset.getString(1));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process getPsdData: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // getPsdData


    /**
     * Get the number of Projections
     * @returns number of projections
     */
    int countProjection() {

        int count = 0;

        try {

            sql = "select count(*) from projection";
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
            this.addItem("<br>Could not process countProjection: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return count;

    } // countProjection


    /**
     * List all Projections
     */
    void listProjections() {

        try {

            sql = "select code, name from projection order by upper(name)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                plProjectionCode[i] = (int)rset.getFloat(1);
                plProjection[i]     = sc.spaceIfNull(rset.getString(2));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listProjections: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listProjections


    /**
     * Get the number of Spheroids
     * @returns number of spheroids
     */
    int countSpheroid() {

        int count = 0;

        try {

            sql = "select count(*) from spheroid";
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
            this.addItem("<br>Could not process countSpheroid: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return count;

    } // countSpheroid


    /**
     * List all spheroids
     */
    void listSpheroids() {

        try {

            sql = "select code, name from spheroid order by upper(name)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                plSpheroidCode[i] = (int)rset.getFloat(1);
                plSpheroid[i]     = sc.spaceIfNull(rset.getString(2));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listSpheroids: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listSpheroids


    /**
     * Get the number of Datums
     * @returns number of datums
     */
    int countDatum() {

        int count = 0;

        try {

            sql = "select count(*) from datum";
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
            this.addItem("<br>Could not process countDatum: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return count;

    } // countDatum


    /**
     * List all datums
     */
    void listDatums() {

        try {

            sql = "select code, name from datum order by upper(name)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                plDatumCode[i] = (int)rset.getFloat(1);
                plDatum[i]     = sc.spaceIfNull(rset.getString(2));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listDatums: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listDatums


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
//    private String spaceIfNull(String text) {
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

        HtmlHead hd = new HtmlHead("UpdatePSDdataBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdatePSDdataBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdatePSDdataBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdatePSDdataBlock
