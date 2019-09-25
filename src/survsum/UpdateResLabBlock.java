package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update the responsible laboratory block
*
* @author  20070820 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070820 - Ursula von St Ange - change to package class              <br>
*/
public class UpdateResLabBlock extends CompoundItem {

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
    String surveyId         = "";
    String institErr        = "";
    String countryErr       = "";
    String pNewIUpdated     = "";
    String pNewCUpdated     = "";
    String pExistUpdated    = "";
    String pNewInstitName   = "";
    String pExistInstitCode = "";
    String pExistCountryCode= "";
    String pNewInstitAddress= "";
    String pNewCountryName  = "";

    // database variables
    int    pInstitCode      = 0;
    String pInstitName      = "";
    String pInstitAddress   = "";
    int    pCountryCode     = 0;
    String pCountryName     = "";
    int    paInstitCode[];
    String paInstitName[];
    int    paCountryCode[];
    String paCountryName[];
    int    pInstitCount     = 0;
    int    pCountryCount    = 0;
    int    pRecordcount;

   // +============================================================+
   // | void main(String args[])                                   |
   // +============================================================+
   // | MAIN PROGRAM                                               |
   // +------------------------------------------------------------+
   public UpdateResLabBlock(String args[]) {

        TableRow rows = new TableRow();

        // +------------------------------------------------------------+
        // | Print SADCO logo                                           |
        // +------------------------------------------------------------+
       String host = sadco.SadConstants.LIVE ? "sadco.ocean.gov.za" : "sadco.int.ocean.gov.za";
       Image slogo = new Image
               ("http://" + host + "/sadco-img/sadlogo.gif",
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
        surveyId          = sc.getArgument(args,"surveyid");
        institErr         = sc.getArgument(args,"institerr");
        countryErr        = sc.getArgument(args,"countryerr");
        pNewIUpdated      = sc.getArgument(args,"NewIUpdated");
        pNewCUpdated      = sc.getArgument(args,"NewCUpdated");
        pExistUpdated     = sc.getArgument(args,"existUpdated");
        pNewInstitName    = sc.getArgument(args,"newInstitName");
        pExistInstitCode  = sc.getArgument(args,"existInstitCode");
        pExistCountryCode = sc.getArgument(args,"existCountryCode");
        pNewInstitAddress = sc.getArgument(args,"newInstitAddress");
        pNewCountryName   = sc.getArgument(args,"newCountryName");

        if (pExistUpdated.length() > 0) {
            updateReslabBlock(surveyId, pExistInstitCode, pExistCountryCode);
        }  //  if (pExistUpdated.length() > 0)

        // +------------------------------------------------------------+
        // | Add the Institute if required                              |
        // +------------------------------------------------------------+
        institErr = new String("N");
        if (pNewIUpdated.length() > 0) {
            addInstituteBlock(surveyId, pNewInstitName, pNewInstitAddress);
            if (1 == pInstitCount) institErr = "Y";
        }  //  if (pNewIUpdated() > 0)


        // +------------------------------------------------------------+
        // | Add new country if required                                |
        // +------------------------------------------------------------+
        countryErr = new String("N");
        if (pNewCUpdated.length() > 0) {
            addCountryBlock(surveyId, pNewCountryName);
            if (1 == pCountryCount) countryErr = "Y";
        }  //  if (pNewCUpdated.length() > 0)


        // get the responsible laboratory data from the database
        getReslabBlock(surveyId);

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
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>" + surveyId + "</b")))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Create platform table                                      |
        // +------------------------------------------------------------+
        DynamicTable tab_labo  = new DynamicTable(2);
        tab_labo
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Responsible Laboratory</u></b>")).setColSpan(80));
        tab_labo.addRow(rows);

        rows = new TableRow();
        rows
         .addCell(new TableDataCell(sc.formatStr(
            "&nbsp;&nbsp;&nbsp;&nbsp;Name: <b>" + pInstitName +
            "</b><br>&nbsp;&nbsp;Address: <b>" + pInstitAddress +
            "</b><br>&nbsp;&nbsp;Country: <b>" + pCountryName + "</b>"
            )))
            ;
        tab_labo.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_labo).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());


        // +------------------------------------------------------------+
        // | Create the select list of institute names                  |
        // +------------------------------------------------------------+
        int institCount = countInstit();// + 1;
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
        // | Create the select list of country   names                  |
        // +------------------------------------------------------------+
        int countryCount = countCountry(); // + 1;
        paCountryCode    = new int[countryCount];
        paCountryName    = new String[countryCount];
        listAllCountry();
        Select selectCountryList = new Select("existCountryCode");
        for (int i = 0; i < countryCount; i++) {
            selectCountryList.addOption(new Option(
                paCountryName[i],
                String.valueOf(paCountryCode[i]),
                sc.getDefault(paCountryCode[i],pCountryCode)
                ));
        }  //  for (int i = 0; i < countryCount; i++)


        // +------------------------------------------------------------+
        // | Create the form to update the institute block              |
        // +------------------------------------------------------------+
        Form existfrm = new Form("GET", "SurvSum");
        existfrm
            .addItem(new Hidden("pscreen", "UpdateResLabBlock"))
            .addItem(new Hidden("existUpdated", "Y"))
            .addItem(new Hidden("surveyid", surveyId))
            .addItem(new SimpleItem("Institute Name: "))
            .addItem(selectInstitList)
            .addItem(new SimpleItem("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Country: "))
            .addItem(selectCountryList)
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit selected institute!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Create the form to add new institute                       |
        // +------------------------------------------------------------+
        Form newInstitFrm = new Form("Get", "SurvSum");
        newInstitFrm
            .addItem(new Hidden("pscreen", "UpdateResLabBlock"))
            .addItem(new Hidden("NewIUpdated", "Y"))
            .addItem(new Hidden("surveyid", surveyId))
            .addItem(new Hidden("institerr", institErr))
            .addItem(new SimpleItem("Institute Name: "))
            .addItem(new TextField("newInstitName", 50, 50, pNewInstitName))
            ;

        // +------------------------------------------------------------+
        // | Display instit duplicate error if there is a duplicate     |
        // +------------------------------------------------------------+
        if (institErr.equals("Y")) {
            newInstitFrm .addItem(new SimpleItem(
                "<br><FONT COLOR=#FF6666>ERR: Institute already recorded</font><br>").setBold());
        }  //  if (institErr.equals("Y"))

        // +------------------------------------------------------------+
        // | Get Institute Address                                      |
        // +------------------------------------------------------------+
        newInstitFrm
            .addItem(new SimpleItem("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address: "))
            .addItem(new TextField("newInstitAddress", 50, 50, pNewInstitAddress))
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit new Institute!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Create the form to add new institute                       |
        // +------------------------------------------------------------+
        Form newCountryFrm = new Form("Get", "SurvSum");
        newCountryFrm
            .addItem(new Hidden("pscreen", "UpdateResLabBlock"))
            .addItem(new Hidden("NewCUpdated", "Y"))
            .addItem(new Hidden("surveyid", surveyId))
            .addItem(new Hidden("countryerr", countryErr))
            .addItem(new SimpleItem("Country Name: "))
            .addItem(new TextField("newCountryName", 20, 20, pNewCountryName))
            ;
        if (countryErr.equals("Y")) {
            newCountryFrm .addItem(new SimpleItem(
                "<br><FONT COLOR=#FF6666>ERR: Country already recorded</font><br>").setBold());
        } // if (countryErr.equals("Y"))

        newCountryFrm
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit new Country!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateChiefScBlock"))
            .addItem(new Hidden("surveyid", surveyId))
            .addItem(new Submit("submit", ">>> Update Chief Scientist")   )
            ;

        // +------------------------------------------------------------+
        // | Create the completed button                                |
        // +------------------------------------------------------------+
        Form close = new Form("GET", "SurvSum");
        close
            .addItem(new Hidden("pscreen", "DisplaySurvsum"))
            .addItem(new Hidden("surveyid", surveyId))
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
        // | Add new Country                                            |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Add a new Country"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(newCountryFrm));
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
            if (dbg) System.out.println("Before conn.close()");
            //conn.close();
            sc.disConnect(stmt, rset, conn);
            if (dbg) System.out.println("After conn.close()");
        }  catch (Exception e){}

    } //  constructor UpdateResLabBlock


    /**
     * Update Responsible Lab Block
     * @param   surveyId            surveyId of cruise
     * @param   pExistInstitCode    institute code
     * @param   pExistCountryCode   country code
     */
    void updateReslabBlock(String surveyId, String pExistInstitCode,
            String pExistCountryCode){

        try {

            sql = "update inventory set instit_code = " + pExistInstitCode +
                ", country_code = " + pExistCountryCode +
                " where survey_id = '" + surveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.out.println("<br>updateReslabBlock: sql = " + sql);
            if (dbg) System.out.println("<br>updateReslabBlock: cnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateReslabBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updateReslabBlock


    /**
     * Add new Institute block
     * @param   surveyId            surveyId of cruise
     * @param   pNewInstitName      institute name
     * @param   pNewInstitAddress   institute address
     */
    void addInstituteBlock(String surveyId, String pNewInstitName,
            String pNewInstitAddress) {

        try {

            // check if institute exists
            sql = "select count(*) from institutes where " +
                "upper(name) = upper('" + pNewInstitName + "')";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pInstitCount = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            if (pInstitCount == 0) {

                // get max institute code
                sql = "select max(code) from institutes";
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                int institCode = 0;
                while (rset.next()) {
                    institCode = (int)(rset.getFloat(1)) ;
                } //while (rset.next())
                institCode++;
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

                // insert into institutes
                sql = "insert into institutes values (" + institCode + ", '" +
                    pNewInstitName + "', '" + pNewInstitAddress + "')";
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

                // update inventory
                sql = "update inventory set instit_code = " + institCode +
                      " where survey_id = '" + surveyId + "'";
                stmt = conn.createStatement();
                num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

            } // if (pInstitCount > 0)

        } catch (Exception e) {
            this.addItem("<br>Could not process addInstituteBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // addInstituteBlock


    /**
     * Add new Country block
     * @param   surveyId            surveyId of cruise
     * @param   pNewCountryName     country name
     */
    void addCountryBlock(String surveyId, String pNewCountryName) {

        try {

            // check if institute exists
            sql = "select count(*) from country where " +
                "upper(name) = upper('" + pNewCountryName + "')";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pCountryCount = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            if (pCountryCount == 0) {

                // get max institute code
                sql = "select max(code) from country";
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                int countryCode = 0;
                while (rset.next()) {
                    countryCode = (int)(rset.getFloat(1));
                } //while (rset.next())
                countryCode++;
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

                // insert into institutes
                sql = "insert into country values (" + countryCode + ", '" +
                    pNewCountryName + "')";
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

                // update inventory
                sql = "update inventory set country_code = " + countryCode +
                      " where survey_id = '" + surveyId + "'";
                stmt = conn.createStatement();
                num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

            } // if (pCountryCount > 0)

        } catch (Exception e) {
            this.addItem("<br>Could not process addCountryBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // addCountryBlock


    /**
     * Get responsible Lab block
     * @param   surveyId            surveyId of cruise
     */
    void getReslabBlock(String surveyId) {

        try {

            // get institute info
            sql = "select instit_code, institutes.name, institutes.address " +
                  "from inventory, institutes " +
                  "where survey_id = '" + surveyId + "' and " +
                  "inventory.instit_code = institutes.code";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pInstitCode     = (int)(rset.getFloat(1));
                pInstitName     = sc.spaceIfNull(rset.getString(2));
                pInstitAddress  = sc.spaceIfNull(rset.getString(3));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // get country info
            sql = "select country_code, country.name " +
                  "from inventory, country " +
                  "where survey_id = '" + surveyId + "' and " +
                  "inventory.country_code = country.code";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pCountryCode     = (int)(rset.getFloat(1));
                pCountryName     = sc.spaceIfNull(rset.getString(2));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process getReslabBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // getReslabBlock


    /**
     * Get the number of Institutes
     * @returns number of institutes
     */
    int countInstit() {

        int institCount = 0;

        try {

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
     * Get the number of Countries
     * @returns number of countries
     */
    int countCountry() {

        int countryCount = 0;

        try {

            // check if institute exists
            sql = "select count(*) from country";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                countryCount = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process countCountry: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return countryCount;

    } // countCountry


    /**
     * List all Countries
     */
    void listAllCountry() {

        try {

            sql = "select code, name from country order by upper(name)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                paCountryCode[i] = (int)rset.getFloat(1);
                paCountryName[i]  = sc.spaceIfNull(rset.getString(2));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listAllCountry: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listAllCountry


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

        HtmlHead hd = new HtmlHead("UpdateResLabBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateResLabBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateResLabBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdateResLabBlock
