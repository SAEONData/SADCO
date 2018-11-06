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
public class UpdateDataBlock extends CompoundItem {

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
    String  pSurveyId           = "";
    String  pUpdated            = "";
    String  pNewDataCentre      = "";
    String  pNewExchangeRestrict= "";
    String  pNewSurveyType      = "";
    String  pPrevSurveyTypeCode = "";

    // work variables
    String  pDataCentre         = "";
    String  pExchangeRestrict   = "";
    String  pSurveyTypeCode     = "";
    String  pSurveyTypeName     = "";

    // survey type code and name
    String  codeArray[] = new String[20];
    String  nameArray[] = new String[20];


    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+

    public UpdateDataBlock(String args[]) {

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
        pSurveyId            = sc.getArgument(args,"surveyid");
        pUpdated             = sc.getArgument(args,"updated");
        pNewDataCentre       = sc.getArgument(args,"newDataCentre");
        pNewExchangeRestrict = sc.getArgument(args,"newExchangeRestrict");
        pNewSurveyType       = sc.getArgument(args,"newSurveyType");
        pPrevSurveyTypeCode  = sc.getArgument(args,"prevsurveytypecode");


        // +------------------------------------------------------------+
        // | Update for new values                                      |
        // +------------------------------------------------------------+
        if (pUpdated.length() > 0) {

            updateDataBlock(pSurveyId, pNewDataCentre, pNewExchangeRestrict);

            if (dbg) System.err.println("<br> **pNewSurveyType "+pNewSurveyType); //0
            if (dbg) System.err.println("<br> **pSurveyTypeCode "+pSurveyTypeCode); //1

            if (!pNewSurveyType.equals("") &&
                !pNewSurveyType.equals(pPrevSurveyTypeCode)) {

                if (dbg) System.err.println("<br> if ( (!pNewSurveyType.equals()) & ");
                updateSurveyType(pSurveyId, pNewSurveyType);

            } // if (!pNewSurveyType.equals("") &&

        } // if (pUpdated.length() > 0)

        // +------------------------------------------------------------+
        // | Get the existing data block values                         |
        // +------------------------------------------------------------+
        getDataBlock(pSurveyId);


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

        TableRow rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>SURVEY SUMMARY REPORT</b>")).setColSpan(80))
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>" + pSurveyId.toString() + "</b")))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Create data table                                          |
        // +------------------------------------------------------------+
        DynamicTable tab_data  = new DynamicTable(2);
        tab_data
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStrBig("<b><u>Data</u></b>")))
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;&nbsp;&nbsp;Date Centre:<b>" +
                pDataCentre + "</b><br>Data Exchange:<b>" +
                pExchangeRestrict)))
            .addCell(new TableDataCell(sc.formatStrBig("Survey Type: <b>" +
                pSurveyTypeName +"</b>")))
            ;
        tab_data.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_data).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());


        // +------------------------------------------------------------+
        // | Create the select lists of survey types                    |
        // +------------------------------------------------------------+
        int typeCnt = getSurveyTypes();
        Select selectSurveyType = new Select("newSurveyType");
        for (int i = 0; i < typeCnt; i++) {
            boolean state = false;
            if (pSurveyTypeCode.equals(codeArray[i])) state = true;
            selectSurveyType.addOption(new Option(
                nameArray[i], codeArray[i], state));
        } // for (int i = 0; i < typeCnt; i++)


        // +------------------------------------------------------------+
        // | Create the form to update the data block                   |
        // +------------------------------------------------------------+
        Form frm = new Form("GET", "SurvSum");
        frm
            .addItem(new Hidden("pscreen", "UpdateDataBlock"))
            .addItem(new Hidden("updated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("prevsurveytypecode", pSurveyTypeCode))

            .addItem(new SimpleItem("Date Centre: "))
            .addItem(new TextField("newDataCentre", 5, 5, pDataCentre))

            .addItem(new SimpleItem("&nbsp;&nbsp;Data Exchange: "))
            .addItem(new TextField("newExchangeRestrict", 1, 1, pExchangeRestrict))
            .addItem(new SimpleItem("Select survey type: "))
            .addItem(selectSurveyType)
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("", "Submit!") )
            .addItem(new Reset   ("Reset") )
            ;
//        this.addItem(frm);


        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdatePlatformBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update Platform")   )
            ;
//        this.addItem(nextBlock);


        // +------------------------------------------------------------+
        // | Return to survey summary                                   |
        // +------------------------------------------------------------+
        Form close = new Form("GET", "SurvSum");
        close
            .addItem(new Hidden("pscreen", "DisplaySurvsum"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("passcode", "ter91qes"))
            .addItem(new Submit("submit", "<<< Survey Summary")   )
            ;
//        this.addItem(close);

        DynamicTable tab_buttons = new DynamicTable(2);
        tab_buttons
            .setBorder(0)
            .setFrame(ITableFrame.BOX)
            .setCellSpacing(0)
            ;

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(close))
            .addCell(new TableHeaderCell(nextBlock))
            ;
        tab_buttons.addRow(rows.setIVAlign(IVAlign.TOP));
        //System.err.println("after tab_buttons");

        // +------------------------------------------------------------+
        // | Create Update table                                        |
        // +------------------------------------------------------------+
        DynamicTable tab_update  = new DynamicTable(2);
        tab_update
            .setBorder(2)
            .setFrame(ITableFrame.BOX)
            .setCellSpacing(2)
            .setWidth("100%")
            ;

        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Add / update Data Centre"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(frm));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        //System.err.println("after tab_update");
//          rows = new TableRow();
//          rows

//         .addCell(new TableDataCell(existfrm))
//         .addCell(new TableDataCell(newfrm))
//         ;
//      tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(tab_buttons).setBackgroundColor(Color.cyan))
//            .addCell(new TableHeaderCell("&nbsp;").setBackgroundColor(Color.cyan))
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

    } // UpdateDataBlock constructor


    /**
     * Update the data block with new data centre and exchange restrictions
     * @param   pSurveyId
     * @param   pNewDataCentre
     * @param   pNewExchangeRestrict
     */
     void updateDataBlock(String pSurveyId, String pNewDataCentre,
                String pNewExchangeRestrict) {
        try {

            sql = "update inventory set data_centre = '" +
                pNewDataCentre + "', exchange_restrict = '" +
                pNewExchangeRestrict + "' " +
                "where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.err.println("<br>typeCnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateDataBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updateDataBlock


    /**
     * Get data_center, exchange_restrict, survey_type code from Inventory.
     * @param   pSurveyId
     */
    void getDataBlock(String pSurveyId) {

        try {

            sql = " select data_centre, exchange_restrict, " +
                  " survey_type_code, b.name " +
                  " from inventory a, survey_type b " +
                  " where survey_id = '" + pSurveyId + "' and " +
                  " survey_type_code = b.code";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
                pDataCentre       = sc.spaceIfNull(rset.getString(1));
                pExchangeRestrict = sc.spaceIfNull(rset.getString(2));
                pSurveyTypeCode   = sc.spaceIfNull(rset.getString(3));
                pSurveyTypeName   = sc.spaceIfNull(rset.getString(4));

                if (dbg) System.err.println("<br>pDataCentre       " + pDataCentre);
                if (dbg) System.err.println("<br>pExchangeRestrict " + pExchangeRestrict);
                if (dbg) System.err.println("<br>pSurveyTypeCode   " + pSurveyTypeCode);
                if (dbg) System.err.println("<br>pSurveyTypeName   " + pSurveyTypeName);

            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process getDataBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try {

    } // getDataBlock


    /**
     * Update the survey type
     * @param   pSurveyId
     * @param   pNewSurveyType
     */
    void updateSurveyType(String pSurveyId, String pNewSurveyType) {

        try {

            sql= " Update inventory set survey_type_code "+
                 " = " + pNewSurveyType +
                 " where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.err.println("<br>typeCnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateSurveyType: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch
    } // updateSurveyType


    /**
     * get all info from survey_type table. For drop down box.
     * @returns the number of survey types
     */
    int getSurveyTypes() {

        int typeCnt = 0;

        try {

            sql = "select code, name from survey_type";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {

               codeArray[typeCnt] = sc.spaceIfNull(rset.getString(1));
               nameArray[typeCnt] = sc.spaceIfNull(rset.getString(2));

               if (dbg) System.err.println("<br> typeCnt "+typeCnt);
               if (dbg) System.err.println("<br> nameArray[typeCnt] "+
                  nameArray[typeCnt]);
               if (dbg) System.err.println("<br> codeArray[typeCnt] "+
                  codeArray[typeCnt]);

               typeCnt++;

            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process getSurveyTypes: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return typeCnt;
    } // getSurveyTypes


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

        HtmlHead hd = new HtmlHead("UpdateDataBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateDataBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateDataBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdateDataBlock
