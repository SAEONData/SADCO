package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update the projects block
*
* @author  20070817 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070817 - Ursula von St Ange - change to package class              <br>
*/
public class UpdateProjectBlock extends CompoundItem {

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
    String  institErr           = "";
    String  pNewIUpdated        = "";
    String  pExistUpdated       = "";
    String  pNewProjectName     = "";
    String  pExistInstitCode    = "";
    String  pNewInstitCode      = "";
    String  pNewInstitName      = "";
    String  pNewInstitAddress   = "";

    // database variables
    String  pProjectName        = "";
    int     pInstitCode         = 0;
    String  pInstitName         = "";
    String  pInstitAddress      = "";
    int     paInstitCode[];
    String  paInstitName[];
    int     pInstitCount        = 0;
    int     pRecordcount        = 0;


   // +============================================================+
   // | void main(String args[])                                   |
   // +============================================================+
   // | MAIN PROGRAM                                               |
   // +------------------------------------------------------------+
   public UpdateProjectBlock(String args[]) {

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
        pSurveyId           = sc.getArgument(args,"surveyid");
        institErr           = sc.getArgument(args,"institerr");
        pNewIUpdated         = sc.getArgument(args,"NewIUpdated");
        pExistUpdated        = sc.getArgument(args,"existUpdated");
        pNewProjectName      = sc.getArgument(args,"newProjectName");
        pExistInstitCode     = sc.getArgument(args,"existInstitCode");
        pNewInstitCode       = sc.getArgument(args,"newInstitCode");
        pNewInstitName       = sc.getArgument(args,"newInstitName");
        pNewInstitAddress    = sc.getArgument(args,"newInstitAddress");

        // +------------------------------------------------------------+
        // | Update the project name                                    |
        // +------------------------------------------------------------+
        if (pNewProjectName.length() > 0) {
            updateProjectName(pSurveyId, pNewProjectName);
        }  //  if (pNewProjectName.lenght() > 0)

        if (pExistUpdated.length() > 0) {
          updateCoordCode(pSurveyId, pExistInstitCode);
        }  //  if (pExistUpdated.length() > 0)

        // +------------------------------------------------------------+
        // | Add the Institute if required                              |
        // +------------------------------------------------------------+
        institErr = new String("N");
        if (pNewIUpdated.length() > 0) {
            addInstituteCoord(pSurveyId, pNewInstitName, pNewInstitAddress);
            if (pInstitCount == 1) institErr = "Y";
        }  //  if (pNewIUpdated() > 0)

        // get the data again
        getProjectBlock(pSurveyId);

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
        DynamicTable tab_proj  = new DynamicTable(2);
        tab_proj
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Project</u></b>")).setColSpan(80));
        tab_proj.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name: <b>" +
                pProjectName +
                "</b><br>&nbsp;&nbsp;Co-ordin. Body: <b>" +
                pInstitName  +
                "</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>" +
                pInstitAddress  + "</b>"
                )))
            ;
        tab_proj.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_proj).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());

        // +------------------------------------------------------------+
        // | Create the select list of institute names                  |
        // +------------------------------------------------------------+
        int institCount = countInstit();
        paInstitCode    = new int[institCount];
        paInstitName    = new String[institCount];
        listAllInstit();
        Select selectInstitList = new Select("existInstitCode");
        for (int i = 0; i < institCount; i++) {
            selectInstitList.addOption(new Option(
                paInstitName[i],
                String.valueOf(paInstitCode[i]),
                sc.getDefault(paInstitCode[i],pInstitCode)
                ));
        }  //  for (int i = 0; i < institCount; i++)

        // +------------------------------------------------------------+
        // | Create the form to update the data block                   |
        // +------------------------------------------------------------+
        Form updateProjectFrm = new Form("GET", "SurvSum");
        updateProjectFrm
            .addItem(new Hidden("pscreen", "UpdateProjectBlock"))
            .addItem(new Hidden("updated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))

            .addItem(new SimpleItem("Project Name: "))
            .addItem(new TextField("newProjectName", 100, 100, pProjectName))

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit New Project Name!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Create the form to update the institute block              |
        // +------------------------------------------------------------+
        Form existfrm = new Form("GET", "SurvSum");
        existfrm
            .addItem(new Hidden("pscreen", "UpdateProjectBlock"))
            .addItem(new Hidden("existUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))

            .addItem(new SimpleItem("Institute Name: "))
            .addItem(selectInstitList)

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit selected institute!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Create the form to add new institute                       |
        // +------------------------------------------------------------+
        Form newInstitFrm = new Form("Get", "SurvSum");
        newInstitFrm
            .addItem(new Hidden("pscreen", "UpdateProjectBlock"))
            .addItem(new Hidden("NewIUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("institerr", institErr))

            .addItem(new SimpleItem("Institute Name: "))
            .addItem(new TextField("newInstitName", 100, 100, pNewInstitName))
            ;

        // +------------------------------------------------------------+
        // | Display instit duplicate error if there is a duplicate     |
        // +------------------------------------------------------------+
        if (institErr.equals("Y")) {
            newInstitFrm.addItem(new SimpleItem(
                "<br><FONT COLOR=#FF6666>ERR: Institute already recorded</font><br>").setBold());
        }  //  if (institErr.equals("Y"))

        // +------------------------------------------------------------+
        // | Get Institute Address                                      |
        // +------------------------------------------------------------+
        newInstitFrm
            .addItem(new SimpleItem("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;Address: "))
            .addItem(new TextField("newInstitAddress", 50, 50, pNewInstitAddress))

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit new Institute!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdatePriInvsBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update Principal Investigators Block")   )
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
        // | Add new project name                                       |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Add a new Project Name"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(updateProjectFrm));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Choose from listing                                        |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Choose Institute from Listing"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(existfrm));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Add new Institute                                          |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Add a new Institute"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(newInstitFrm));
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

    } // constructor UpdateProjectBlock


    /**
     * Update project block
     * @param   pSurveyId       surveyId of cruise
     * @param   pNewProjectName new project name
     */
    void updateProjectName(String pSurveyId, String pNewProjectName){

        try {

            sql = "update inventory set project_name = '" + pNewProjectName +
                  "' where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.err.println("<br>updateProjectName: cnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateProjectName: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updateProjectName


    /**
     * Update co-ordinating body
     * @param   pSurveyId       surveyId of cruise
     * @param   pNewProjectName new project name
     */
    void updateCoordCode(String pSurveyId, String pExistInstitCode){

        try {

            sql = "update inventory set coord_code = " + pExistInstitCode +
                  " where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.err.println("<br>updateCoordCode: cnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateCoordCode: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updateCoordCode


    /**
     * Add new Coord Institute
     * @param   pSurveyId       surveyId of cruise
     * @param   pNewProjectName new project name
     */
    void addInstituteCoord(String pSurveyId, String pNewInstitName,
            String pNewInstitAddress){

        try {

            // does institute exist
            sql = "select count(*) from institutes where upper(name) = " +
                  "upper('" + pNewInstitName + "')";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
               pInstitCount = (int)rset.getFloat(1);
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            if (pInstitCount == 0) {

                // get next code
                int code = 0;
                sql = "select max(code) from institutes";
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                while (rset.next()) {
                   code = (int)rset.getFloat(1);
                } //while (rset.next()) {
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

                //insert new institute
                code++;
                sql = "insert into institutes values ( " + code + ", '" +
                      pNewInstitName + "', '" + pNewInstitAddress + "')";
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

                // update inventory
                sql = "update inventory set coord_code = " + code +
                      " where survey_id = '" + pSurveyId + "'";
                stmt = conn.createStatement();
                num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

            } // if (pInstitCount == 0)
        } catch (Exception e) {
            this.addItem("<br>Could not process addInstituteCoord: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // addInstituteCoord


    /**
     * Get project block
     * @param   surveyId    surveyId of cruise
     */
   void getProjectBlock(String pSurveyId) {

        try {

            // get project and coordinating code
            sql = "select project_name, coord_code from inventory " +
                  " where survey_id = '" + pSurveyId + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pProjectName = sc.spaceIfNull(rset.getString(1));
                pInstitCode = (int)rset.getFloat(2);
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // get the institute name and address
            sql = "select name, address from institutes " +
                   "where code = " + pInstitCode;
            if (dbg) System.out.println("<br>getProjectBlock: sql = " + sql);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pInstitName    = sc.spaceIfNull(rset.getString(1));
                pInstitAddress = sc.spaceIfNull(rset.getString(2));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process getProjectBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // getProjectBlock


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
                institCount = (int)(rset.getFloat(1)) ;
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
     * List all Institutes
     */
    void listAllInstit() {

        try {

            sql = "select code, name from institutes order by upper(name)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                paInstitCode[i] = (int)rset.getFloat(1);
                paInstitName[i]  = sc.spaceIfNull(rset.getString(2));
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

        HtmlHead hd = new HtmlHead("UpdateProjectBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateProjectBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateProjectBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdateProjectBlock
