package sadinv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.html.*;


/**
 * This class handles the search on data types

 * SadInv
 * pscreen = datatypes
 *
 * @author 20090113 - SIT Group.
 * @version
 * 20090113 - Ursula von St Ange - create program                       <br>
 * 20100507 - Ursula von St Ange - change for postgres             ub01 <br>
 */

public class GetDataType extends CompoundItem  {

    //boolean dbg = true;
    boolean dbg = false;

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    static private Connection conn = null;
    static private java.sql.Statement stmt = null;
    static private ResultSet rset = null;

    static private int MAX_RECS = 25;

    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public GetDataType(String args[]) {

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String sessionCode  = ec.getArgument(args,sc.PSC);

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass, sessionCode);
        /*
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName(sc.DRIVER);                                   //ub01
            //conn = DriverManager.getConnection
            //    ("jdbc:oracle:thin:" + sc.USER + "/" + sc.PWD +
            //     sc.CONNECTION);          // steamer
            //     //"@morph.csir.co.za:1522:etek8");
            conn = DriverManager.getConnection(                         //ub01
                sc.CONNECTION, sc.USER,  sc.PWD);                       //ub01
            conn.setAutoCommit(false);
        } catch (Exception e) {
            return;
        } // try-catch
        */

        // Create a statement
        String sql = "";

        // +------------------------------------------------------------+
        // | Build the empty arrays for holding data                    |
        // +------------------------------------------------------------+
        String dataTypeCodes[]    = new String[MAX_RECS];
        String dataTypeNames[]    = new String[MAX_RECS];
        String dataTypeDescs[]    = new String[MAX_RECS];

        int numRecs = 0;
        try {

            sql =
                " select code, name, description "+
                " from survey_type "+
                " order by upper(name)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (dbg) System.out.println("<br> ***Test sql "+sql);
            int i = 0;
            while (rset.next() && (i < MAX_RECS)) {
                dataTypeCodes[i]    = checkNull(rset.getString(1));
                dataTypeNames[i]    = checkNull(rset.getString(2));
                dataTypeDescs[i]    = checkNull(rset.getString(3));
                if (dbg) System.out.println("<br> dataTypeCodes["+i+"] "+ dataTypeCodes[i]);
                if (dbg) System.out.println("<br> dataTypeNames["+i+"]"+dataTypeNames[i]);
                i++;
            } // while (rset.next() && (i < MAX_RECS)) {

            numRecs = i;
            if (dbg) System.out.println("<br> i "+ i);
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.err.println("<br>Debug "+sql);
            System.err.println(e.getMessage());
            ;
            e.printStackTrace();
        } // try-catch

        // +------------------------------------------------------------+
        // | Prompt user to click on scientist name to select           |
        // +------------------------------------------------------------+
        this
            //.addItem(SimpleItem.LineBreak)
            .addItem(new SimpleItem("Click on a Data Type for more information."))
            ;

        // +------------------------------------------------------------+
        // | Create the main listing table                              |
        // +------------------------------------------------------------+
        DynamicTable tab = new DynamicTable(2);
        tab
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
        rows
            .addCell(new TableHeaderCell(formatStr("Data Type")))
            .addCell(new TableHeaderCell(formatStr("Description")))
            ;
        rows.setBackgroundColor(new Color("FFCC66"));
        tab.addRow(rows);

        // +------------------------------------------------------------+
        // | Create the table entries                                   |
        // +------------------------------------------------------------+
        for (int i = 0; i < numRecs; i++) {
            // +------------------------------------------------------------+
            // | Create the link to call this page again and pass the last  |
            // | name      to it, which will become the start name      in  |
            // | new page.                                                  |
            // +------------------------------------------------------------+

            String screen = sc.DATATYPES2;
            if ("VOS".equals(dataTypeNames[i])) screen = sc.DATATYPEVOS;
            String link1 = new String(
                sc.APP + "?" + sc.SCREEN + "=" + screen + "&" +
                sc.CODE + "=" + dataTypeCodes[i] + "&" +
                sc.PSC + "=" + sessionCode);

            // +------------------------------------------------------------+
            // | Build a new row and add to the list table                  |
            // +------------------------------------------------------------+
            rows = new TableRow();
            rows
                .addCell(new TableDataCell(new Link(link1,new SimpleItem(
                    dataTypeNames[i]).setFontSize(1))) )
                 .addCell(new TableDataCell(formatStr(dataTypeDescs[i])))
                ;
            tab.addRow(rows);

        }  //  for (int i = 0; i < numRecs; i++)

        this.addItem(tab);


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
        } catch (SQLException e) {
            //ec.processError(e, thisClass, "constructor", "Error: close connection");
            return;
        } // try-catch

    } // main


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
            HtmlHead hd = new HtmlHead("GetDataType local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new GetDataType(args));
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

} // class GetDataType
