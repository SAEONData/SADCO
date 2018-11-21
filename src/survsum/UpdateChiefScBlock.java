package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update the chief scientist block
*
* @author  20070821 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070821 - Ursula von St Ange - change to package class              <br>
*/
public class UpdateChiefScBlock extends CompoundItem {

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
    String  pExistUpdated   = "";
    String  pExistSciCode1  = "";
    String  pExistSciCode2  = "";

    // work variables
    int     pSciCode1       = 0;
    String  pSurname1       = "";
    String  pFName1         = "";
    String  pTitle1         = "";
    int     pSciCode2       = 0;
    String  pSurname2       = "";
    String  pFName2         = "";
    String  pTitle2         = "";
    String  pInstitName1    = ""; //new String(50,"x");
    String  pInstitAddress1 = ""; //new String(50,"x");
    String  pInstitName2    = ""; //new String(50,"x");
    String  pInstitAddress2 = ""; //new String(50,"x");
    int     paSciCode[];
    String  paSurname[];
    String  paFName[];
    String  paTitle[];
    int     pRecordcount    = 0;

   // +============================================================+
   // | void main(String args[])                                   |
   // +============================================================+
   // | MAIN PROGRAM                                               |
   // +------------------------------------------------------------+
   public UpdateChiefScBlock(String args[]) {

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
        pExistUpdated   = sc.getArgument(args,"existUpdated");
        pExistSciCode1  = sc.getArgument(args,"existSciCode1");
        pExistSciCode2  = sc.getArgument(args,"existSciCode2");

        if (dbg) System.out.println("<br>pExistSciCode1 = " + pExistSciCode1);
        if (dbg) System.out.println("<br>pExistSciCode2 = " + pExistSciCode2);

        // +------------------------------------------------------------+
        // | Update the record from the selected lists                  |
        // +------------------------------------------------------------+
        if (pExistUpdated.length() > 0) {
            updateChiefscBlock(pSurveyId, pExistSciCode1, pExistSciCode2);
         }  //  if (pExistUpdated.length() > 0)

        // get the values again
        getChiefscBlock(pSurveyId);


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
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>SURVEY SUMMARY REPORT</b>")).setColSpan(80))
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>" + pSurveyId + "</b")))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Create Chief Scientist table                               |
        // +------------------------------------------------------------+
        DynamicTable tab_csci  = new DynamicTable(2);
        tab_csci
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Chief Scientist(s)</u></b>")).setColSpan(80));
        tab_csci.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;1.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name: <b>" +
                pTitle1.trim() + " " + pFName1.trim() + " " + pSurname1 +
                "</b><br>&nbsp;&nbsp;Laboratory: <b>" +
                pInstitName1 +
                "</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address: <b>" +
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
                    "</b><br>&nbsp;&nbsp;Laboratory: <b>" +
                    pInstitName2 +
                    "</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address: <b>" +
                    pInstitAddress2 + "</b>"
                    )))
                ;
            tab_csci.addRow(rows.setIVAlign(IVAlign.TOP));
        }  //  if (!pSurname.equals("unknown"))


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_csci).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());

        // +------------------------------------------------------------+
        // | Create the select lists of scientist names                 |
        // +------------------------------------------------------------+
        int sciCount = countScientists();
        paSciCode    = new int[sciCount];
        paSurname    = new String[sciCount];
        paFName      = new String[sciCount];
        paTitle      = new String[sciCount];
        listAllScientists();
        // scientist 1
        Select selectSci1List = new Select("existSciCode1");
        for (int i = 0; i < sciCount; i++) {
//            boolean state = false;
//            if (pSciCode1 == paSciCode[i]) state = true;
            selectSci1List.addOption(new Option(
                paSurname[i].trim() + ", " + paFName[i],
                String.valueOf(paSciCode[i]),
                sc.getDefault(paSciCode[i],pSciCode1)
                ));
//                state));
        }  //  for (int i = 0; i < sciCount; i++)
        // scientist 2
        Select selectSci2List = new Select("existSciCode2");
        for (int i = 0; i < sciCount; i++) {
//            boolean state = false;
//            if (pSciCode2 == paSciCode[i]) state = true;
            selectSci2List.addOption(new Option(
                paSurname[i].trim() + ", " + paFName[i],
                String.valueOf(paSciCode[i]),
                sc.getDefault(paSciCode[i],pSciCode2)
                ));
//                state));
        }  //  for (int i = 0; i < sciCount; i++)


        // +------------------------------------------------------------+
        // | Create the form to update the scientists  block            |
        // +------------------------------------------------------------+
        Form existfrm = new Form("GET", "SurvSum");
        existfrm
            .addItem(new Hidden("pscreen", "UpdateChiefScBlock"))
            .addItem(new Hidden("existUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))

            .addItem(new SimpleItem("Scientist 1: "))
            .addItem(selectSci1List)
            .addItem(new SimpleItem("<br>Scientist 2: "))
            .addItem(selectSci2List)

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit selected Scientists") )
            .addItem(new Reset   ("Reset") )
            ;


        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateObjectsBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update Objectives Block")   )
            ;

        // +------------------------------------------------------------+
        // | Create button to go to add scientist                       |
        // +------------------------------------------------------------+
        Form addscifrm = new Form("GET", "SurvSum");
        addscifrm
            .addItem(new Hidden("pscreen", "AddNewScientist"))
            .addItem(new Submit("submit", "Add New Scientist") )
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("cobj", "cs"))
            ;

        DynamicTable tab_addsci = new DynamicTable(1);
        tab_addsci
            .setBorder(0)
            .setCellSpacing(0)
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableHeaderCell(addscifrm));
        tab_addsci.addRow(rows);


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
        DynamicTable tab_update  = new DynamicTable(1);
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
        rows.addCell(new TableHeaderCell("Choose Chief Scientists from Listing"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(existfrm))
            ;
        tab_update.addRow(rows);

        // +------------------------------------------------------------+
        // | Add new client button                                      |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell(tab_addsci));
        tab_update.addRow(rows);

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

    } // conctructor UpdateChiefScBlock


    /**
     * Update Chief Scientists Block
     * @param   pSurveyId
     * @param   pExistSciCode1
     * @param   pExistSciCode2
     */
     void updateChiefscBlock(String pSurveyId, String pExistSciCode1,
            String pExistSciCode2) {

        try {

            sql = "update inventory set sci_code_1 = " + pExistSciCode1 +
                  ", sci_code_2 = " + pExistSciCode2 + " " +
                  "where survey_id = '" + pSurveyId + "'";

            if (dbg) System.out.println("<br>updateChiefscBlock: sql = " + sql);

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.err.println("<br>typeCnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateChiefscBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updateChiefscBlock


    /**
     * Get Chief Scientist block
     * @param   pSurveyId
     */
    void getChiefscBlock(String pSurveyId) {

        try {

            // select first scientist
            sql = " select sci_code_1, surname, f_name, title, " +
                  "institutes.name, address " +
                  "from inventory, scientists, institutes " +
                  "where survey_id = '" + pSurveyId + "' and " +
                  "sci_code_1 = scientists.code and " +
                  "institutes.code = scientists.instit_code";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pSciCode1       = (int)rset.getFloat(1);
                pSurname1       = sc.spaceIfNull(rset.getString(2));
                pFName1         = sc.spaceIfNull(rset.getString(3));
                pTitle1         = sc.spaceIfNull(rset.getString(4));
                pInstitName1    = sc.spaceIfNull(rset.getString(5));
                pInstitAddress1 = sc.spaceIfNull(rset.getString(6));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // select second scientist
            sql = " select sci_code_2, surname, f_name, title, " +
                  "institutes.name, address " +
                  "from inventory, scientists, institutes " +
                  "where survey_id = '" + pSurveyId + "' and " +
                  "sci_code_2 = scientists.code and " +
                  "institutes.code = scientists.instit_code";
            if (dbg) System.out.println("<br>getChiefscBlock: sql = " + sql);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pSciCode2       = (int)rset.getFloat(1);
                pSurname2       = sc.spaceIfNull(rset.getString(2));
                pFName2         = sc.spaceIfNull(rset.getString(3));
                pTitle2         = sc.spaceIfNull(rset.getString(4));
                pInstitName2    = sc.spaceIfNull(rset.getString(5));
                pInstitAddress2 = sc.spaceIfNull(rset.getString(6));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process getChiefscBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try {

    } // getChiefscBlock


    /**
     * Get the number of Scientists
     * @returns number of scientists
     */
    int countScientists() {

        int sciCount = 0;

        try {

            // check if institute exists
            sql = "select count(*) from scientists";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                sciCount = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process countScientists: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return sciCount;

    } // countScientists


    /**
     * List all Scientists
     */
    void listAllScientists() {

        try {

            sql = "select code, surname, f_name, title " +
                  "from scientists order by upper(surname)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                paSciCode[i] = (int)rset.getFloat(1);
                paSurname[i] = sc.spaceIfNull(rset.getString(2));
                paFName[i]   = sc.spaceIfNull(rset.getString(3));
                paTitle[i]   = sc.spaceIfNull(rset.getString(4));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listAllScientists: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listAllScientists


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
     * Identify the select item that is to be flagged as default
     * @param    item
     * @param    prevSelect
     * @return   true/false
     */
/*
    private boolean getDefault(int item, int prevSelect) {
        return (item == prevSelect ? true : false);
    } // getDefault
*/


    /**
     * main class for testing purposes
     * @param   args    list of URL name-value parameters
     */
    public static void main(String args[]) {

        HtmlHead hd = new HtmlHead("UpdateChiefScBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateChiefScBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateChiefScBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdateChiefScBlock

