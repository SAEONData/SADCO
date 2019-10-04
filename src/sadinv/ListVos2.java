package sadinv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.html.*;

/**
 * This class checks for valid input and calls ExtractRequest
 *
 * SadInv
 * pscreen = DATATYPEVOS2
 * pdatatype = <datatype code>
 *
 * @author 20100416 - SIT Group.
 * @version
 * 20100416 - Ursula von St Ange - create program                       <br>
 */

public class ListVos2 extends CompoundItem {

    //boolean dbg = true;
    boolean dbg = false;

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    static private Connection conn = null;
    static private java.sql.Statement stmt = null;
    static private ResultSet rset = null;

//    static private int MAX_RECS = 20;

    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public ListVos2 (String args[]) {


        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        try {
            Class.forName(sc.DRIVER);
            conn = DriverManager.getConnection
                (sc.CONNECTION + sc.USER + "/" + sc.PWD +
                 sc.CONNECTION);          // steamer
            conn.setAutoCommit(false);
        } catch (Exception e) {
            return;
        } // try-catch

        // prepare the sql string
        String sql = "";


        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String datatypeCode = ec.getArgument(args,sc.CODE);
        String sessionCode  = ec.getArgument(args,sc.PSC);              //ub03
        String latNorth = ec.getArgument(args,sc.LATITUDENORTH);
        String latSouth = ec.getArgument(args,sc.LATITUDESOUTH);
        String lonWest = ec.getArgument(args,sc.LONGITUDEWEST);
        String lonEast = ec.getArgument(args,sc.LONGITUDEEAST);
        String startDate = ec.getArgument(args,sc.STARTDATE);
        String endDate = ec.getArgument(args,sc.ENDDATE);

        // +------------------------------------------------------------+
        // | Get the datatype details                                  |
        // +------------------------------------------------------------+
        String  datatypeName    = "";
        String  datatypeDesc    = "";
        try {

            sql =
                " select name, description "+
                " from survey_type "+
                " where code = "+datatypeCode;

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (dbg) System.out.println("<br> **sql "+sql);

            while (rset.next()) {
                datatypeName  = checkNull(rset.getString(1));
                datatypeDesc  = checkNull(rset.getString(2));
                if (dbg) System.out.println("<br> **datatypeName "+datatypeName);

            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (SQLException e) {
            System.err.println("<br> "+e.getMessage());
                e.printStackTrace();
        } // try-catch

        // +------------------------------------------------------------+
        // | Create the main listing table                              |
        // +------------------------------------------------------------+
        DynamicTable header_tab = new DynamicTable(1);
        header_tab
            .setBorder(1)
            .setCellSpacing(1)
            .setBackgroundColor(new Color("FFFF99"))
            .setFrame(ITableFrame.BOX)
            .setRules(ITableRules.ALL)
        ;

        // +------------------------------------------------------------+
        // | Create the headings                                        |
        // +------------------------------------------------------------+
        TableRow rows = new TableRow();
        rows.addCell(new TableHeaderCell(new SimpleItem(
            datatypeName + "<br>" +datatypeDesc).setFontSize(5)));
        header_tab.addRow(rows);

        // +------------------------------------------------------------+
        // | Build a new row and add to the list table                  |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(
            "Date range : from 1662 till present<br>" +
            "Number of records : > 7.25 million<br>" +
            "Area : 10 N - 80 S, 30 W - 70 E"));
        header_tab.addRow(rows);

        this.addItem(header_tab .setCenter());

        // +------------------------------------------------------------+
        // | Info Heading                                               |
        // +------------------------------------------------------------+
        this.addItem(new SimpleItem("<br>"));

        if (!"".equals(sessionCode)) {

            // +------------------------------------------------------------+
            // | Create the table to display allowable extractions          |
            // +------------------------------------------------------------+
            DynamicTable infoTab  = new DynamicTable(10);
            infoTab
                .setBorder(3)
                .setBackgroundColor(new Color("FFFF99"))
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL);
            rows = new TableRow();
            rows.addCell(new TableDataCell(
                "The following extractions are allowed on-line:<br>" +
                "1. 1 degree, up to 10 years of data<br>" +
                "2. 1 year of data, up to 10 degrees square<br>" +
                "Any other extraction size an off-line request will " +
                "be submitted on your behalf"));
            infoTab.addRow(rows);
            this.addItem(infoTab.setCenter());
            this.addItem(new SimpleItem("<br>"));

            // +------------------------------------------------------------+
            // | Enter a input parameters                                   |
            // +------------------------------------------------------------+
            DynamicTable table = new DynamicTable(1);
            table.setBorder(0).setCellPadding(0).setCellSpacing(0);
            table.setFrame(ITableFrame.BOX).setRules(ITableRules.NONE);
            table.addRow(ec.latlonRangeInput("", 6));
            ec.datetimeRangeInput(table, "daterange", 12,
                "", "00:00", "", "23:59", "");

            Form frm = new Form("GET", sc.APP);
            frm
                .addItem(table.setCenter())
                .addItem(new Hidden(sc.SCREEN, sc.DATATYPEVOS2))
                .addItem(new Hidden(sc.CODE, datatypeCode))
                .addItem(new Hidden(sc.PSC, sessionCode))                   //ub03
                .addItem("<br>")
                .addItem(new Submit("Submit", "Go!").setCenter())
                ;

            this.addItem(frm);

        } // if (!"".equals(sessionCode))

        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            } // if (stmt != null)
            if (rset != null) {
                rset.close();
                rset = null;
            } // if (rset != null)
            if (conn != null) {
                conn.close();
                conn = null;
            } // if (conn != null)
        }  catch (SQLException e) { }

    } // ListVos2


    /**
     * Format text strings
     */
    private static Container formatStr(String oldStr) {
        Container newStrc = new Container();
        newStrc
            .addItem(new SimpleItem(oldStr).setFontSize(1))
            ;
        return newStrc;

    } // formatStr


    /**
     * check if the value is null
     * returns  value / empty string
     */
    String checkNull(String value) {
        return (value != null ? value : "");
    } // checkNull


    /**
       * The constructor is instantiated in the main class to get away from
       * static / non-static problems.
       * @param args the string array of url-type name-value parameter pairs
       */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("ListVos2 local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new ListVos2(args));
            hp.printHeader();
            hp.print();
            //common2.DbAccessC.close();
        } catch(Exception e) {
            e.printStackTrace();
            // make sure everything is closed and null
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                } // if (stmt != null)
                if (rset != null) {
                    rset.close();
                    rset = null;
                } // if (rset != null)
                if (conn != null) {
                    conn.close();
                    conn = null;
                } // if (conn != null)
            }  catch (SQLException se){}
        } // try-catch

    } // main

} // class ListVos2
