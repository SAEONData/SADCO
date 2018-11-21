package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
* Update survey country
*
* @author  20070822 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070822 - Ursula von St Ange - change to package class              <br>
*/
public class UpdateTCountryBlock extends CompoundItem {

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
    String  countryErr          = "";
    String  pNewCUpdated        = "";
    String  pExistUpdated       = "";
    String  pExistCountryCode   = "";
    String  pNewCountryName     = "";

    // work variables
    int     pCountryCode      = 0;
    String  pCountryName      = "";
    int     paCountryCode[];
    String  paCountryName[];
    int     pCountryCount     = 0;


    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public UpdateTCountryBlock(String args[]) {

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
        countryErr          = sc.getArgument(args,"countryerr");
        pNewCUpdated        = sc.getArgument(args,"NewCUpdated");
        pExistUpdated       = sc.getArgument(args,"existUpdated");
        pExistCountryCode   = sc.getArgument(args,"existCountryCode");
        pNewCountryName     = sc.getArgument(args,"newCountryName");


        if (pExistUpdated.length() > 0) {
            updateTargetCountryBlock(pSurveyId, pExistCountryCode);
        }  //  if (pExistUpdated.length() > 0)


        // +------------------------------------------------------------+
        // | Add new country if required                                |
        // +------------------------------------------------------------+
        countryErr = "N";
        if (pNewCUpdated.length() > 0) {
            addCountryBlock(pSurveyId, pNewCountryName);
            if (pCountryCount == 1) countryErr = "Y";
        }  //  if (pNewCUpdated.length() > 0)


        // +------------------------------------------------------------+
        // | Retrieve the data from the packaged PL/SQL                 |
        // | using procedure get_reslab_block                           |
        // +------------------------------------------------------------+
        getTargetCountryBlock(pSurveyId);

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
        // | Create target country table                                |
        // +------------------------------------------------------------+
        DynamicTable tab_tstn  = new DynamicTable(2);
        tab_tstn
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Survey Country</u></b>")).setColSpan(80));
        tab_tstn.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "</b>&nbsp;&nbsp;Country: <b>" +
                pCountryName + "</b>"
                )))
            ;
        tab_tstn.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_tstn).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());


        // +------------------------------------------------------------+
        // | Create the select list of country   names                  |
        // +------------------------------------------------------------+
        int countryCount = countCountry();
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
        // | Create the form to update the country block               |
        // +------------------------------------------------------------+
        Form existfrm = new Form("GET", "SurvSum");
        existfrm
            .addItem(new Hidden("pscreen", "UpdateTCountryBlock"))
            .addItem(new Hidden("existUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))

            .addItem(new SimpleItem("Country: "))
            .addItem(selectCountryList)

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit selected country!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Create the form to add new country                         |
        // +------------------------------------------------------------+
        Form newCountryFrm = new Form("Get", "SurvSum");
        newCountryFrm
            .addItem(new Hidden("pscreen", "UpdateTCountryBlock"))
            .addItem(new Hidden("NewCUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("countryerr", countryErr))

            .addItem(new SimpleItem("Country Name: "))
            .addItem(new TextField("newCountryName", 20, 20, pNewCountryName))
            ;

        // +------------------------------------------------------------+
        // | Display instit duplicate error if there is a duplicate     |
        // +------------------------------------------------------------+
        if (countryErr.equals("Y")) {
            newCountryFrm .addItem(new SimpleItem(
                "<br><FONT COLOR=#FF6666>ERR: Country already recorded</font><br>").setBold()   );
        }  //  if (institErr.equals("Y"))

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
            .addItem(new Hidden("pscreen", "UpdatePSDdataBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update PSD Data Block")   )
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
        rows.addCell(new TableHeaderCell("Choose Survey Country from Listing"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(existfrm));
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
            if (dbg) System.err.println("Before conn.close()");
            //conn.close();
            sc.disConnect(stmt, rset, conn);
            if (dbg) System.err.println("After conn.close()");
        }  catch (Exception e){}

    } // constructor UpdateTCountryBlock



    /**
     * Update Target Country  Block
     * @param   surveyId            surveyId of cruise
     * @param   pExistInstitCode    institute code
     * @param   pExistCountryCode   country code
     */
    void updateTargetCountryBlock(String surveyId, String pExistCountryCode){

        try {

            sql = "update inventory set target_country_code = " + pExistCountryCode +
                " where survey_id = '" + surveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.out.println("<br>updateTargetCountryBlock: sql = " + sql);
            if (dbg) System.out.println("<br>updateTargetCountryBlock: cnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateTargetCountryBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updateTargetCountryBlock


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
    void getTargetCountryBlock(String surveyId) {

        try {

            // get institute info
            sql = "select target_country_code, country.name " +
                  "from inventory left outer join country " +
                  "on inventory.target_country_code = country.code " +
                  "and survey_id = '" + surveyId + "'";

            //System.out.println("sql = " + sql);
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

        } catch (SQLException e) {
            this.addItem("<br>Could not process getTargetCountryBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // getTargetCountryBlock


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

        HtmlHead hd = new HtmlHead("UpdateTCountryBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateTCountryBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateTCountryBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdateTCountryBlock
