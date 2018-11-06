package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Displays the survey summary listing
*
* @author  20070817 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070817 - Ursula von St Ange - change to package class              <br>
*/
public class DisplaySurvsumList extends CompoundItem {

    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();
    static SadCommon sc = new SadCommon();

    // Create java.sql stuff
    //String USER = "sadco";
    //String PWD = "ter91qes";
    Connection conn = null;
    Statement stmt = null; // = conn.createStatement();
    ResultSet rset = null;;

    String  pSurveyId[]      = new String[30];
    String  pProjectName[]   = new String[30];
    String  pCruiseName[]    = new String[30];
    String  pPlanamName[]    = new String[30];
    String  pSurname[]       = new String[30];
    String  pFName[]         = new String[30];
    String  pTitle[]         = new String[30];
    String  pInstitName[]    = new String[30];
    String  pDateStart[]     = new String[30];
    String  pDateEnd[]       = new String[30];
    String  pDataRecorded[]  = new String[30];

    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public DisplaySurvsumList(String args[]) {

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
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String pNewSurveyId = sc.getArgument(args,"newsurveyid");
        String pExiSurveyId = sc.getArgument(args,"exisurveyid");

        if (pNewSurveyId.length() > 0) {
            newInventory(pNewSurveyId);
        }  //  if (newSurveyId.length > 0)


        // +------------------------------------------------------------+
        // | Enter a Survey ID                                          |
        // +------------------------------------------------------------+
        Form frm = new Form("GET", "SurvSum");

        frm
            .addItem(new Hidden("pscreen", "DisplaySurvsumList"))
            .addItem(new SimpleItem("NEW survey ID: "))
            .addItem(new TextField("newsurveyid", 9, 9, ""))
            .addItem(new SimpleItem("<br>"))
            .addItem(new SimpleItem("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-or-"))
            .addItem(new SimpleItem("<br>"))
            .addItem(new SimpleItem("<br>Enter first few characters of a Survey ID: "))
            .addItem(new TextField("exisurveyid", 9, 9, pNewSurveyId.toString()))
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit!") )
            .addItem(new Reset   ("Reset") )
            ;

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

        TableRow rows = new TableRow();
        rows.addCell(new TableHeaderCell("Maintain Survey Summaries"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(frm));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        this.addItem(tab_update .setCenter() );

        // +------------------------------------------------------------+
        // | Get a listing of the surveys if there is a survey ID input |
        // +------------------------------------------------------------+
        if (pExiSurveyId.length() > 0) {

            // +------------------------------------------------------------+
            // | Retrieve the survey information from the packaged PL/SQL   |
            // | using procedure display_survey                             |
            // +------------------------------------------------------------+
            listSurveySurvey(pExiSurveyId);

            // +------------------------------------------------------------+
            // | Prompt user to click on survey ID to select                |
            // +------------------------------------------------------------+
            this
                .addItem(SimpleItem.LineBreak)
                .addItem(new SimpleItem("Click on a survey ID for more information."))
                ;

            // +------------------------------------------------------------+
            // | Create the main listing table                              |
            // +------------------------------------------------------------+
            DynamicTable tab = new DynamicTable(9);
            tab
                .setBorder(1)
                .setCellSpacing(1)
                .setBackgroundColor(Color.cyan)
                .setFrame(ITableFrame.BOX)
                ;

            // +------------------------------------------------------------+
            // | Create the headings                                        |
            // +------------------------------------------------------------+
            rows = new TableRow();
            rows
                .addCell(new TableHeaderCell(sc.formatStr("Survey ID")))
                .addCell(new TableHeaderCell(sc.formatStr("Project Name")))
                .addCell(new TableHeaderCell(sc.formatStr("Cruise Name")))
                .addCell(new TableHeaderCell(sc.formatStr("Platform Name")))
                .addCell(new TableHeaderCell(sc.formatStr("Chief Scientist")))
                .addCell(new TableHeaderCell(sc.formatStr("Institute")))
                .addCell(new TableHeaderCell(sc.formatStr("Start")))
                .addCell(new TableHeaderCell(sc.formatStr("End")))
                .addCell(new TableHeaderCell(sc.formatStr("Data Stored?")))
                ;
            tab.addRow(rows);

            // +------------------------------------------------------------+
            // | Set End of Data flag off                                   |
            // +------------------------------------------------------------+
            int eod = 0;

            for (int i = 0; i < 30; i++) {
                // +------------------------------------------------------------+
                // | The end of the data list has been reached. Set the End of  |
                // | data flag and end the table loop                           |
                // +------------------------------------------------------------+
                //if (pSurveyId[i].length() == 0) {
                if (pSurveyId[i] == null) {
                    eod = 1;
                    break;
                }  //  if (pSurveyId[i].length() == 0)

                // +------------------------------------------------------------+
                // | Create the link to call this page again and pass the last  |
                // | survey ID to it, which will become the start survey ID in  |
                // | new page.                                                  |
                // +------------------------------------------------------------+
                String link1 = new String(
                    "SurvSum?pscreen=DisplaySurvsum&surveyid=" + pSurveyId[i] + "&passcode=ter91qes");

                // +------------------------------------------------------------+
                // | Get rid of nulls                                           |
                // +------------------------------------------------------------+
                if (pProjectName  [i].length() == 0)   pProjectName  [i] = new String(".");
                if (pCruiseName   [i].length() == 0)   pCruiseName   [i] = new String(".");
                if (pDataRecorded [i].length() == 0)   pDataRecorded [i] = new String(".");

                // +------------------------------------------------------------+
                // | Build a new row and add to the list table                  |
                // +------------------------------------------------------------+
                rows = new TableRow();
                rows
                    .addCell(new TableDataCell(
                        new Link(link1,new SimpleItem(pSurveyId[i]).setFontSize(1))) )
                    .addCell(new TableDataCell(sc.formatStr(pProjectName  [i])))
                    .addCell(new TableDataCell(sc.formatStr(pCruiseName   [i])))
                    .addCell(new TableDataCell(sc.formatStr(pPlanamName   [i])))
                    .addCell(new TableDataCell(sc.formatStr(pTitle        [i].trim() + " " +
                        pFName        [i].trim() + " " + pSurname      [i])))
                    .addCell(new TableDataCell(sc.formatStr(pInstitName   [i])))
                    .addCell(new TableDataCell(sc.formatStr(pDateStart    [i])))
                    .addCell(new TableDataCell(sc.formatStr(pDateEnd      [i])))
                    .addCell(new TableDataCell(sc.formatStr(pDataRecorded [i])))
                    ;
                tab.addRow(rows);

            } // for (int i = 0; i < 30; i++)

            this.addItem(tab .setCenter() );

            // +------------------------------------------------------------+
            // | If there are more surveys, create a button for the user to |
            // | click for the next stations on the list.                   |
            // +------------------------------------------------------------+
            if (eod < 1) {
                Form moresurvs = new Form("GET", "SurvSum");
                moresurvs
                    .addItem(new Hidden("pscreen", "DisplaySurvsumList"))
                    .addItem(new Hidden("exisurveyid", pSurveyId[29].toString()) )
                    .addItem(new Submit  ("submit", "More") )
                    ;
                this.addItem(moresurvs);
            }  //  if (eod < 1)


        } // if (pExiSurveyId.length() > 0)

        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
        try {
            if (dbg) System.err.println("Before conn.close()");
            sc.disConnect(stmt, rset, conn);
            if (dbg) System.err.println("After conn.close()");
        }  catch (Exception e){}

    } // DisplaySurvsumList constructor


    /**
     * Add new empty inventory record
     * @param   surveyId    surveyId of new record
     */
    void newInventory (String surveyId) {

        int surveyCount = 0;

        try {

            // test wether surveyId already on database
            String sql = "select count(*) from inventory " +
                  "where survey_id = '" + surveyId + "'";
            if (dbg) System.out.println("<br> sql = " + sql);

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            //if (dbg) System.err.println("before while ");
            while (rset.next()) {
                //if (dbg) System.err.println("in while ");
                surveyCount = (int)rset.getFloat(1);
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // if does not exist - insert a new records
            if (surveyCount == 0) {

                sql = "insert into inventory values ('" + surveyId + "', " +
                    "'SADCO','','','','','','',1,'','',1,1,1,1,1,'1900-01-01'," +
                    "'1900-01-01',0,0,0,0,'','','N',1,'',0,'N',1,1,1,'N',0,'N')";
                if (dbg) System.out.println("<br>sql = " + sql);

                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;
            } // if (surveyCount == 0)

        } catch (Exception e) {
            this.addItem("Could not process newInventory: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

        //return surveyCount;

    } // newInventory


    /**
     * List surveys in according to survey
     * @param   surveyId    start surveyId
     */
    void listSurveySurvey (String surveyId) {
        try {
            String sql = "select survey_id, project_name, cruise_name, " +
                "planam.name, surname, f_name, title, institutes.name, " +
                "to_char(date_start,'YYYY-MM-DD'), " +
                "to_char(date_end  ,'YYYY-MM-DD'), data_recorded " +
                "from inventory, planam, institutes, scientists " +
                "where inventory.survey_id >= '" + surveyId + "' " +
                "and   planam.code         = inventory.planam_code " +
                "and   institutes.code     = inventory.instit_code " +
                "and   scientists.code     = inventory.sci_code_1 " +
                "order by inventory.survey_id";
            
            if (dbg) System.out.println("<br>sql = " + sql);
       
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            
            if (dbg) System.err.println("before while ");
            int i = 0;
            while (rset.next()) {
                int j = 1;
                pSurveyId[i]    = sc.spaceIfNull(rset.getString(j++));
                pProjectName[i] = sc.spaceIfNull(rset.getString(j++));
                pCruiseName[i]  = sc.spaceIfNull(rset.getString(j++));
                pPlanamName[i]  = sc.spaceIfNull(rset.getString(j++));
                pSurname[i]     = sc.spaceIfNull(rset.getString(j++));
                pFName[i]       = sc.spaceIfNull(rset.getString(j++));
                pTitle[i]       = sc.spaceIfNull(rset.getString(j++));
                pInstitName[i]  = sc.spaceIfNull(rset.getString(j++));
                pDateStart[i]   = sc.spaceIfNull(rset.getString(j++));
                pDateEnd[i]     = sc.spaceIfNull(rset.getString(j++));
                pDataRecorded[i]= sc.spaceIfNull(rset.getString(j++));
                if (dbg) System.out.println("<br>listSurveySurvey: pSurveyId[i] = " + i + " " +
                    pSurveyId[i]);
                i++;
                if (i >= 30) break;
                //if (dbg) System.err.println("in while ");
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("Could not process listSurveySurvey: " +
                e.getMessage());
            e.printStackTrace();
        } // try-catch

    } // listSurveySurvey;


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
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(1));
        return newStrc;
    } // formatStr
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
     * main class for testing purposes
     * @param   args    list of URL name-value parameters
     */
    public static void main(String args[]) {

        HtmlHead hd = new HtmlHead("DisplaySurvsumList local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {
        	//System.out.println("test this " ); //kyle hack
            bd.addItem(new DisplaySurvsumList(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in DisplaySurvsumList: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class DisplaySurvsumList