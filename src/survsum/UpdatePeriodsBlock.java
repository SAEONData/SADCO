package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;

/**
* Update the periods block
*
* @author  20070817 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070817 - Ursula von St Ange - change to package class              <br>
*/

public class UpdatePeriodsBlock extends CompoundItem {

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
    String surveyId         = "";
    String pUpdated         = "";
    String pNewDateStart    = "";
    String pNewDateEnd      = "";
    String pNewGMTdiff      = "";
    String pNewPortStart    = "";
    String pNewPortEnd      = "";

    // database variables
    String pDateStart   = "";
    String pDateEnd     = "";
    int    pGMTdiff     = 0;
    String pGMTFreeze   = "";
    String pPortStart   = "";
    String pPortEnd     = "";



    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public UpdatePeriodsBlock(String args[]) {

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
        surveyId        = sc.getArgument(args,"surveyid");
        pUpdated        = sc.getArgument(args,"updated");
        pNewDateStart   = sc.getArgument(args,"newDateStart");
        pNewDateEnd     = sc.getArgument(args,"newDateEnd");
        pNewGMTdiff     = sc.getArgument(args,"newGMTdiff");
        pNewPortStart   = sc.getArgument(args,"newPortStart");
        pNewPortEnd     = sc.getArgument(args,"newPortEnd");

        if (pUpdated.length() > 0) {
            updatePeriodsBlock(surveyId, pNewDateStart, pNewDateEnd,
                pNewGMTdiff, pNewPortStart, pNewPortEnd);
        }  //  if (pNewCruiseName.length() > 0)


        // get the period block information again
        getPeriodsBlock(surveyId);


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
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>" + surveyId.toString() + "</b")))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Create periods table                                       |
        // +------------------------------------------------------------+
        DynamicTable tab_peri  = new DynamicTable(2);
        tab_peri
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;Dates From: <b>" + pDateStart +
                "<br></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To: <b>" +
                pDateEnd + "</b>"
                )))
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp;Departing From: <b>" +
                pPortStart +
                "<br></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returning To: <b>" +
                pPortEnd + "</b>"
                )))
            ;
        tab_peri.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_peri).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());


        // +------------------------------------------------------------+
        // | Create the form to update the periods block                |
        // +------------------------------------------------------------+
        Form newfrm = new Form("GET", "SurvSum");
        newfrm
            .addItem(new Hidden("pscreen", "UpdatePeriodsBlock"))
            .addItem(new Hidden("updated", "Y"))
            .addItem(new Hidden("surveyid", surveyId))
            .addItem(new SimpleItem("Start Date: "))
            .addItem(new TextField("newDateStart", 10, 10, pDateStart))
            .addItem(new SimpleItem("<br>&nbsp;End Date: "))
            .addItem(new TextField("newDateEnd",   10, 10, pDateEnd))
            .addItem(new SimpleItem("<br>&nbsp;GMT hours: "))
            .addItem(new TextField("newGMTdiff", 3, 3, String.valueOf(pGMTdiff)))
            .addItem(new SimpleItem("<br><br>Departing From: "))
            .addItem(new TextField("newPortStart",   20, 20, pPortStart))
            .addItem(new SimpleItem("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returning to: "))
            .addItem(new TextField("newPortEnd",   20, 20, pPortEnd))
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit!") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateResLabBlock"))
            .addItem(new Hidden("surveyid", surveyId))
            .addItem(new Submit("submit", ">>> Update Responsible Lab")   )
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

        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Date Periods and Ports").setBackgroundColor(Color.cyan));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        rows = new TableRow();
        rows.addCell(new TableDataCell(newfrm));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        rows = new TableRow();
        rows.addCell(new TableHeaderCell(tab_buttons).setBackgroundColor(Color.cyan));
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

    } // constructor UpdatePeriodsBlock


    /**
     * Update Periods block
     * @param   surveyId
     * @param   dateStart
     * @param   dateEnd
     * @param   GMTDiff
     * @param   portStart
     * @param   portEnd
     */
    void updatePeriodsBlock(String surveyId, String pNewDateStart,
        String pNewDateEnd, String pNewGMTdiff, String pNewPortStart,
        String pNewPortEnd) {

        try {

            sql = "update inventory set " +
                "date_start = to_timestamp('" + pNewDateStart + "','YYYY-MM-DD'), " +
                "date_end   = to_timestamp('" + pNewDateEnd + "','YYYY-MM-DD'), " +
                "GMT_diff   = " + pNewGMTdiff + ", " +
                "port_start = '" + pNewPortStart + "', " +
                "port_end   = '" + pNewPortEnd + "' " +
                "where survey_id = '" + surveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.err.println("<br>typeCnt = " + num);

        } catch (Exception e) {
            this.addItem("<br>Could not process updatePeriodsBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updatePeriodsBlock


    /**
     * Get the period block information
     * @param   pSurveyId
     */
    void getPeriodsBlock(String surveyId) {

        try {
            sql =
                "select to_char(date_start,'YYYY-MM-DD')," +
                "       to_char(date_end,'YYYY-MM-DD'),  " +
                "       GMT_diff, GMT_freeze,            " +
                "       port_start, port_end             " +
                "from inventory                          " +
                "where survey_id = '" + surveyId +"'";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while(rset.next()) {
                pDateStart  = sc.spaceIfNull(rset.getString(1));
                pDateEnd    = sc.spaceIfNull(rset.getString(2));
                pGMTdiff    = new Float(rset.getFloat(3)).intValue();
                pGMTFreeze  = sc.spaceIfNull(rset.getString(4));
                pPortStart  = sc.spaceIfNull(rset.getString(5));
                pPortEnd    = sc.spaceIfNull(rset.getString(6));
            } // while(rset.next())

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process getPeriodsBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // getPeriodsBlock


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
     * main class for testing purposes
     * @param   args    list of URL name-value parameters
     */
    public static void main(String args[]) {

        HtmlHead hd = new HtmlHead("UpdatePeriodsBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdatePeriodsBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdatePeriodsBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdatePeriodsBlock
