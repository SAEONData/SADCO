package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update the principal investigators block
*
* @author  20070822 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070822 - Ursula von St Ange - change to package class              <br>
*/
public class UpdatePriInvsBlock extends CompoundItem {
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
    String  paExistSciCode[]    = {"","","","","",""};

    // work variables
    int     pSciCodeA         = 0;
    int     pSciCodeB         = 0;
    int     pSciCodeC         = 0;
    int     pSciCodeD         = 0;
    int     pSciCodeE         = 0;
    int     pSciCodeF         = 0;
    String  pSurnameA         = "";
    String  pSurnameB         = "";
    String  pSurnameC         = "";
    String  pSurnameD         = "";
    String  pSurnameE         = "";
    String  pSurnameF         = "";
    String  pFNameA           = "";
    String  pFNameB           = "";
    String  pFNameC           = "";
    String  pFNameD           = "";
    String  pFNameE           = "";
    String  pFNameF           = "";
    String  pTitleA           = "";
    String  pTitleB           = "";
    String  pTitleC           = "";
    String  pTitleD           = "";
    String  pTitleE           = "";
    String  pTitleF           = "";
    String  pInstitNameA      = "";
    String  pInstitNameB      = "";
    String  pInstitNameC      = "";
    String  pInstitNameD      = "";
    String  pInstitNameE      = "";
    String  pInstitNameF      = "";
    String  pInstitAddressA   = "";
    String  pInstitAddressB   = "";
    String  pInstitAddressC   = "";
    String  pInstitAddressD   = "";
    String  pInstitAddressE   = "";
    String  pInstitAddressF   = "";
    int     paSciCode    []   = new int[6];
    String  paSurname    []   = new String[6];
    String  paFName      []   = new String[6];
    String  paTitle      []   = new String[6];
    String  paPiCode     []   = {"A","B","C","D","E","F"};
    String  paInstitName []   = new String[6];
    String  paAddress    []   = new String[6];
    int     plSciCode[];
    String  plSurname[];
    String  plFName[];
    String  plTitle[];
    int    codeA = 0;
    int    codeB = 0;
    int    codeC = 0;
    int    codeD = 0;
    int    codeE = 0;
    int    codeF = 0;


   // +============================================================+
   // | void main(String args[])                                   |
   // +============================================================+
   // | MAIN PROGRAM                                               |
   // +------------------------------------------------------------+
   public UpdatePriInvsBlock(String args[]) {

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
        paExistSciCode[0]   = sc.getArgument(args,"existSciCodeA");
        paExistSciCode[1]   = sc.getArgument(args,"existSciCodeB");
        paExistSciCode[2]   = sc.getArgument(args,"existSciCodeC");
        paExistSciCode[3]   = sc.getArgument(args,"existSciCodeD");
        paExistSciCode[4]   = sc.getArgument(args,"existSciCodeE");
        paExistSciCode[5]   = sc.getArgument(args,"existSciCodeF");

        // +------------------------------------------------------------+
        // | Update the investigators table                             |
        // +------------------------------------------------------------+
        for (int i = 0; i <  6; i++) {
            if (paExistSciCode[i].length() > 0) {
                updateInvestigators(pSurveyId, paExistSciCode[i], paPiCode[i]);
            }  //  if (paExistSciCode[i].length() > 0)
        }  //  for (int i = 0; i <  6; i++)


        // +------------------------------------+
        // | Count the number of Scientists     |
        // +------------------------------------+
        int sciCount = countScientists();
        plSciCode    = new int[sciCount];
        plSurname    = new String[sciCount];
        plFName      = new String[sciCount];
        plTitle      = new String[sciCount];
        listAllScientists();

        // +-------------------------------------------------------------------+
        // | Retrieve the principal investigator data from the packaged PL/SQL |
        // | using procedure list_investigators                                |
        // +-------------------------------------------------------------------+
        listInvestigators(pSurveyId);

        // +------------------------------------------------------------+
        // | Allocate the current investigators into the correct option |
        // | lines                                                      |
        // +------------------------------------------------------------+
        for (int i = 0; i < 6; i++) {
            if (paPiCode[i].startsWith("A")) codeA = paSciCode[i];
            if (paPiCode[i].startsWith("B")) codeB = paSciCode[i];
            if (paPiCode[i].startsWith("C")) codeC = paSciCode[i];
            if (paPiCode[i].startsWith("D")) codeD = paSciCode[i];
            if (paPiCode[i].startsWith("E")) codeE = paSciCode[i];
            if (paPiCode[i].startsWith("F")) codeF = paSciCode[i];
        }  //  for (int i = 0; i > 6; i++)

        // +------------------------------------------------------------+
        // | Create the select lists of scientist names                 |
        // +------------------------------------------------------------+
        Select selectSciListA = new Select("existSciCodeA");
        Select selectSciListB = new Select("existSciCodeB");
        Select selectSciListC = new Select("existSciCodeC");
        Select selectSciListD = new Select("existSciCodeD");
        Select selectSciListE = new Select("existSciCodeE");
        Select selectSciListF = new Select("existSciCodeF");

        // +------------------------------------------------------------+
        // | Create the list of all investigators from the investigators|
        // | table.                                                     |
        // +------------------------------------------------------------+
        for (int i = 0; i < sciCount; i++) {
            selectSciListA.addOption(new Option(
                plSurname[i].trim() + ", " + plFName[i],
                String.valueOf(plSciCode[i]),
                sc.getDefault(plSciCode[i],codeA)
                ));

            selectSciListB.addOption(new Option(
                plSurname[i].trim() + ", " + plFName[i],
                String.valueOf(plSciCode[i]),
                sc.getDefault(plSciCode[i],codeB)
                ));

            selectSciListC.addOption(new Option(
                plSurname[i].trim() + ", " + plFName[i],
                String.valueOf(plSciCode[i]),
                sc.getDefault(plSciCode[i],codeC)
                ));

            selectSciListD.addOption(new Option(
                plSurname[i].trim() + ", " + plFName[i],
                String.valueOf(plSciCode[i]),
                sc.getDefault(plSciCode[i],codeD)
                ));

            selectSciListE.addOption(new Option(
                plSurname[i].trim() + ", " + plFName[i],
                String.valueOf(plSciCode[i]),
                sc.getDefault(plSciCode[i],codeE)
                ));

            selectSciListF.addOption(new Option(
                plSurname[i].trim() + ", " + plFName[i],
                String.valueOf(plSciCode[i]),
                sc.getDefault(plSciCode[i],codeF)
                ));
        }  //  for (int i = 0; i < sciCount; i++)


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
        DynamicTable tab_titl  = new DynamicTable(1);
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
        // | Create Principal Investigator table                        |
        // +------------------------------------------------------------+
        DynamicTable tab_pinv  = new DynamicTable(2);
        tab_pinv
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Principal Investigators</u></b>")).setColSpan(80));
        tab_pinv.addRow(rows);

        // +---------------------------+
        // | Process each investigator |
        // +---------------------------+
        for (int i = 0; i < 6; i++) {

            // +----------------------------------------------------+
            // | Stop processing if there are no more investigators |
            // +----------------------------------------------------+
            if (paSurname[i] == null) break;
            if (paSciCode[i] < 2) break;

            rows = new TableRow();
            rows
                .addCell(new TableDataCell(sc.formatStr(
                    "&nbsp;<FONT COLOR=#3333FF SIZE=+1>" + paPiCode[i] + ". </font><b>" +
                    paTitle  [i].trim() + " " +
                    paFName  [i].trim() + " " +
                    paSurname[i] +
                    "</b><br>&nbsp;&nbsp;&nbsp;<b>" +
                    paInstitName[i] +
                    "</b><br>&nbsp;&nbsp;&nbsp;<b>" +
                    paAddress[i] + "</b>"
                    )))
                ;
            tab_pinv.addRow(rows.setIVAlign(IVAlign.TOP));
        }  //  for (int i = 0; i < 6; i++)


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_pinv).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());


        // +------------------------------------------------------------+
        // | Create the form to update the investigators  block         |
        // +------------------------------------------------------------+
        Form existfrm = new Form("GET", "SurvSum");
        existfrm
            .addItem(new Hidden("pscreen", "UpdatePriInvsBlock"))
            .addItem(new Hidden("existUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))

            .addItem(new SimpleItem("<FONT COLOR=#3333FF SIZE=+1>A. </font> "))
            .addItem(selectSciListA)
            .addItem(new SimpleItem("<br><FONT COLOR=#3333FF SIZE=+1>B. </font> "))
            .addItem(selectSciListB)
            .addItem(new SimpleItem("<br><FONT COLOR=#3333FF SIZE=+1>C. </font> "))
            .addItem(selectSciListC)
            .addItem(new SimpleItem("<br><FONT COLOR=#3333FF SIZE=+1>D. </font> "))
            .addItem(selectSciListD)
            .addItem(new SimpleItem("<br><FONT COLOR=#3333FF SIZE=+1>E. </font> "))
            .addItem(selectSciListE)
            .addItem(new SimpleItem("<br><FONT COLOR=#3333FF SIZE=+1>F. </font> "))
            .addItem(selectSciListF)

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit selected Scientists") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateMooringBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("recnum", "0"))
            .addItem(new Submit("submit", ">>> Update Moorings Block")   )
            ;

        // +------------------------------------------------------------+
        // | Create button to go to add scientist                       |
        // +------------------------------------------------------------+
        Form addscifrm = new Form("GET", "SurvSum");
        addscifrm
            .addItem(new Hidden("pscreen", "AddNewScientist"))
            .addItem(new Submit("submit", "Add New Investigator") )
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("cobj", "pi"))
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
        DynamicTable tab_update  = new DynamicTable(2);
        tab_update
            .setBorder(2)
            .setCellSpacing(2)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Add or Change an Investigator"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(existfrm));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Add new scientist button                                   |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell(tab_addsci));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

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

    } // contructor UpdatePriInvsBlock


    /**
     * Update investigors table
     * @param   pSurveyId
     * @param   paExistSciCode
     * @param   paPiCode
     */
     void updateInvestigators(String pSurveyId, String paExistSciCode,
            String paPiCode) {

        try {

            //check whether investigator are there
            int count = 0;
            sql = "select count(*) from investigators " +
                  "where survey_id = '" + pSurveyId + "' and " +
                  "pi_code = '" + paPiCode + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                count = (int)rset.getFloat(1);
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            if (dbg) System.out.println("<br>updateInvestigators: count = " + count);

            if (count == 0) {
                // insert investigator
                sql = "insert into investigators values ('" + pSurveyId +
                      "', " +paExistSciCode + ", '" + paPiCode + "')";
            } else {
                // udate investigator
                sql = "update investigators set sci_code = " + paExistSciCode +
                      " where survey_id = '" + pSurveyId + "' and " +
                  "pi_code = '" + paPiCode + "'";
            } // if (count == 0)

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.out.println("<br>updateInvestigators: num = " + num);
            if (dbg) System.out.println("<br>updateInvestigators: sql = " + sql);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateInvestigators: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updateInvestigators


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
                plSciCode[i] = (int)rset.getFloat(1);
                plSurname[i] = sc.spaceIfNull(rset.getString(2));
                plFName[i]   = sc.spaceIfNull(rset.getString(3));
                plTitle[i]   = sc.spaceIfNull(rset.getString(4));
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
     * Routine to get all the investigators info for a specific surveyId
     * @param pSurveyId The surveyId to get the record count for
     */
    public void listInvestigators(String pSurveyId) {

        try {

            String sql = "select scientists.code, surname, f_name, title, " +
                  "pi_code, institutes.name, address " +
                  "from investigators, scientists, institutes " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "and   scientists.code  =  investigators.sci_code " +
                  "and   institutes.code  =  scientists.instit_code " +
                  "order by upper(pi_code) ";
            if (dbg) System.out.println("<br>getListInvestigators: sql = " + sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                int j = 1;
                paSciCode   [i]  = (int)rset.getFloat(j++);
                paSurname   [i]  = sc.spaceIfNull(rset.getString(j++));
                paFName     [i]  = sc.spaceIfNull(rset.getString(j++));
                paTitle     [i]  = sc.spaceIfNull(rset.getString(j++));
                paPiCode    [i]  = sc.spaceIfNull(rset.getString(j++));
                paInstitName[i]  = sc.spaceIfNull(rset.getString(j++));
                paAddress   [i]  = sc.spaceIfNull(rset.getString(j++));
                if (dbg) System.out.println("<br>listInvestigators: paPiCode[" +
                    i + "] = " + paPiCode[i] + " " + paSciCode[i]);
                i++;

            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("Could not process listInvestigators: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // listInvestigators


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
//    private boolean getDefault(int item, int prevSelect) {
//        return (item == prevSelect ? true : false);
//    } // getDefault


    /**
     * main class for testing purposes
     * @param   args    list of URL name-value parameters
     */
    public static void main(String args[]) {

        HtmlHead hd = new HtmlHead("UpdatePriInvsBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdatePriInvsBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdatePriInvsBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdatePriInvsBlock

