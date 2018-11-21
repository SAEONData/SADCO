package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Add a new scientist
*
* @author  20070821 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070821 - Ursula von St Ange - change to package class              <br>
*/
public class AddNewScientist extends CompoundItem {

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
    String  scientistErr     = "";
    String  pSurveyId        = "";
    String  CallingObj       = "";
    String  pNewUpdated      = "";
    String  pNewTitle        = "";
    String  pNewFName        = "";
    String  pNewSurname      = "";
    String  pExistInstitCode = "";

    // work variables
    int     pSciCode          = 0;
    String  pSurname          = "";
    String  pFName            = "";
    String  pTitle            = "";
    int     pScientistCount   = 0;
    int     pInstitCode       = 0;
    String  pInstitName       = "";
    int     paInstitCode[];
    String  paInstitName[];
    int     pRecordcount      = 0;


    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public AddNewScientist(String args[]) {

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
        scientistErr     = sc.getArgument(args,"scientisterr");
        pSurveyId        = sc.getArgument(args,"surveyid");
        CallingObj       = sc.getArgument(args,"cobj");
        pNewUpdated      = sc.getArgument(args,"newUpdated");
        pNewTitle        = sc.getArgument(args,"newTitle");
        pNewFName        = sc.getArgument(args,"newFName");
        pNewSurname      = sc.getArgument(args,"newSurname");
        pExistInstitCode = sc.getArgument(args,"existInstitCode");

        // +------------------------------------------------------------+
        // | Add the new Scientist                                      |
        // +------------------------------------------------------------+
        scientistErr = new String("N");
        if (pNewUpdated.length() > 0) {
            addScientist(pNewTitle, pNewFName, pNewSurname, pExistInstitCode);
        }  //  if (pNewUpdated.length() > 0)

        if (pScientistCount == 1) scientistErr = "Y";

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
        // | Create title table                                         |
        // +------------------------------------------------------------+
        DynamicTable tab_titl  = new DynamicTable(2);
        tab_titl
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            ;

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>ADD NEW SCIENTIST</b>")).setColSpan(80))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());

        // +------------------------------------------------------------+
        // | Create the select list of institute names                  |
        // +------------------------------------------------------------+
        int institCount = countInstit();
        paInstitCode    = new int[institCount];
        paInstitName    = new String[institCount];
        listAllInstit();
        int code = 1;
        if (!"".equals(pExistInstitCode)) code = Integer.parseInt(pExistInstitCode);
        Select selectInstitList = new Select("existInstitCode");
        for (int i = 0; i < institCount; i++) {
            selectInstitList.addOption(new Option(
                paInstitName[i],
                String.valueOf(paInstitCode[i]),
                sc.getDefault(paInstitCode[i],code)
                ));
        } // for (int i = 0; i < institCount; i++)


        // +------------------------------------------------------------+
        // | Create the form to update the scientists  block            |
        // +------------------------------------------------------------+
        Form newfrm = new Form("GET", "SurvSum");
        newfrm
            .addItem(new Hidden("pscreen", "AddNewScientist"))
            .addItem(new Hidden("cobj", CallingObj))
            .addItem(new Hidden("newUpdated", "Y"))
            .addItem(new SimpleItem("Title: "))
            .addItem(new TextField("newTitle", 5, 5, pNewTitle))
            .addItem(new SimpleItem("<br>First Name: "))
            .addItem(new TextField("newFName", 20, 20, pNewFName))
            .addItem(new SimpleItem("<br>Surname: "))
            .addItem(new TextField("newSurname", 20, 20, pNewSurname))
            .addItem(new SimpleItem("<br>Institute Name: "))
            .addItem(selectInstitList)
            ;

        // +------------------------------------------------------------+
        // | Display scientist duplicate error if there is a duplicate  |
        // +------------------------------------------------------------+
        if (scientistErr.equals("Y")) {
            newfrm .addItem(new SimpleItem(
                "<br><FONT COLOR=#FF6666>ERR: Scientist already recorded</font><br>").setBold());
        }  //  if (scientistErr.equals("Y"))


        // +------------------------------------------------------------+
        // | Produce buttons to submit or reset                         |
        // +------------------------------------------------------------+
        newfrm
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit  ("submit", "Submit new Scientist") )
            .addItem(new Reset   ("Reset") )
            ;


        // +------------------------------------------------------------+
        // | Create Update table                                        |
        // +------------------------------------------------------------+
        DynamicTable tab_update  = new DynamicTable(1);
        tab_update
            .setBorder(2)
            .setCellSpacing(2)
            .setWidth("100%")
            ;

        // +------------------------------------------------------------+
        // | Choose from listing                                        |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Enter new Chief Scientist details"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(newfrm));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        // +------------------------------------------------------------+
        // | Add closing buttons if calling oblject identified          |
        // +------------------------------------------------------------+
        // +------------------------------------------------------------+
        // | Create the completed button if there is a calling object   |
        // +------------------------------------------------------------+
        if (CallingObj.length() > 0) {
            Form close = new Form("GET", "SurvSum");

            // +------------------------------------------------------------+
            // | Return to the update chief scientist block app             |
            // +------------------------------------------------------------+
            if (CallingObj.equals("cs")) {
                close.addItem(new Hidden("pscreen", "UpdateChiefScBlock"));
            }  //  if (CallingObj.length() > 0)

            if (CallingObj.equals("pi")) {
                close.addItem(new Hidden("pscreen", "UpdatePriInvsBlock"));
            }  //  if (CallingObj.length() > 0)

            close
                .addItem(new Hidden("surveyid", pSurveyId))
                .addItem(new Submit("submit", "Finished!")   )
                ;

            DynamicTable tab_buttons = new DynamicTable(2);
            tab_buttons
                .setBorder(0)
                .setCellSpacing(0)
                ;

            rows = new TableRow();
            rows.addCell(new TableHeaderCell(close));
            tab_buttons.addRow(rows.setIVAlign(IVAlign.TOP));

            rows = new TableRow();
            rows.addCell(new TableHeaderCell(tab_buttons));
            tab_update.addRow(rows.setBackgroundColor(Color.cyan));
         }  //  if (CallingObj.length() > 0)


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

    } // constructor AddNewScientist


    /**
     * Add new Scientist
     * @param   pNewTitle
     * @param   pNewFName
     * @param   pNewSurname
     * @param   pExistInstitCode
     */
     void addScientist(String pNewTitle, String pNewFName, String pNewSurname,
            String pExistInstitCode) {

        try {

            // check whether scientist is alreay there
            sql = "select count(*) from scientists " +
                  "where upper(surname) = upper('" + pNewFName + "') " +
                  "and upper(f_name) = upper('" + pNewSurname + "')";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pScientistCount = (int)rset.getFloat(1);
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            if (pScientistCount == 0) {

                // get next scientist code
                int code = 0;
                sql = "select max(code) from scientists";
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                while (rset.next()) {
                    code = (int)rset.getFloat(1);
                } //while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

                // insert the scientist
                code++;
                sql = "insert into scientists values (" + code + ",'" +
                      pNewSurname + "', '" + pNewFName + "', '" +
                      pNewTitle + "', " + pExistInstitCode + ")";
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

            } // if (pScientistCount == 0)

        } catch (Exception e) {
            this.addItem("<br>Could not process addScientist: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // addScientist


    /**
     * Get the number of Institutes
     * @returns number of institutes
     */
    int countInstit() {

        int institCount = 0;

        try {

            // check if institute exists
            sql = "select count(*) from institutes";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                institCount = (int)(rset.getFloat(1));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process countInstit: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return institCount;

    } // countInstit


    /**
     * List all Countries
     */
    void listAllInstit() {

        try {

            sql = "select code, name from institutes order by upper(name)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                paInstitCode[i] = (int)rset.getFloat(1);
                paInstitName[i] = sc.spaceIfNull(rset.getString(2));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listAllInstit: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listAllInstit


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
    } // getArgument
*/

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
    } // formatStrBig
*/

    /**
     * Return space iso null
     * @param   text    text to test for null
     */
/*
    public String spaceIfNull(String text) {
        return (text == null ? "" : text);
    } // spaceIfNull
*/

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

        HtmlHead hd = new HtmlHead("AddNewScientist local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new AddNewScientist(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in AddNewScientist: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class AddNewScientist
