package sadinv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.html.*;


/**
 * This class handles the search on platforms

 * SadInv
 * pscreen = platforms
 * pplatform = <platform>
 *
 * @author 20090110 - SIT Group.
 * @version
 * 20090110 - Ursula von St Ange - create program                       <br>
 * 20100507 - Ursula von St Ange - change for postgres             ub01 <br>
 */

public class GetPlatform extends CompoundItem  {

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
    public GetPlatform(String args[]) {

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String platform = ec.getArgument(args,sc.PLATFORM);
        String sessionCode  = ec.getArgument(args,sc.PSC);

        // +------------------------------------------------------------+
        // | Enter an Institute                                         |
        // +------------------------------------------------------------+
        Form frm = new Form("GET", sc.APP);
        frm
            .addItem(new SimpleItem("Enter first few characters of " +
                "the Platform Name and press <b>ENTER</b>: "))
            .addItem(new Hidden(sc.SCREEN, sc.PLATFORMS))
            .addItem(new Hidden(sc.PSC, sessionCode))
            .addItem(new TextField(sc.PLATFORM, 20, 20, ""))
            ;
        this.addItem(frm);

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

        // +-----------------------------------------------------------------+
        // | Get a listing of the scientistif there is a scientistname input |
        // +-----------------------------------------------------------------+
        if (!"".equals(platform)) {

            // +------------------------------------------------------------+
            // | Build the empty arrays for holding data                    |
            // +------------------------------------------------------------+
            String platformCodes[]  = new String[MAX_RECS];
            String platformNames[]  = new String[MAX_RECS];
            String callSigns[]      = new String[MAX_RECS];
            String planamNames[]    = new String[MAX_RECS];

            int numRecs = 0;
            try {

                int i = 0;

                sql =
                    " select plan.code, plat.name, plan.callsign, "+
                    " plan.name, plan.wod_code"+
                    " from planam plan, platform plat"+
                    " where upper(plan.name) >= upper('"+platform+"')"+
                    " and plat.code = plan.platform_code "+
                    " order by upper(plan.name)";

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                if (dbg) System.out.println("<br> ***Test sql "+sql);
                while (rset.next() && (i < MAX_RECS)) {
                    platformCodes[i]    = checkNull(rset.getString(1));
                    platformNames[i]    = checkNull(rset.getString(2));
                    callSigns[i]        = checkNull(rset.getString(3));
                    planamNames[i]      = checkNull(rset.getString(4)) +
                        "(" + checkNull(rset.getString(4)) + ")";
                    if (dbg) System.out.println("<br> platformCodes["+i+"] "+ platformCodes[i]);
                    if (dbg) System.out.println("<br> platformNames["+i+"]"+platformNames[i]);
                    if (dbg) System.out.println("<br> callSigns["+i+"]"+callSigns[i]);
                    if (dbg) System.out.println("<br> planamNames["+i+"]"+planamNames[i]);
                    i++;
                } // while (rset.next() && (i < 30)) {

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
                .addItem(new SimpleItem("Click on a Platform name for more information."))
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
                .addCell(new TableHeaderCell(formatStr("Platform Name")))
                .addCell(new TableHeaderCell(formatStr("Platform Type")))
                .addCell(new TableHeaderCell(formatStr("Callsign")))
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

                String link1 = new String(
                    sc.APP + "?" + sc.SCREEN + "=" + sc.PLATFORMS2 + "&" +
                    sc.CODE + "=" + platformCodes[i] + "&" +
                    sc.PSC + "=" + sessionCode);

                // +------------------------------------------------------------+
                // | Build a new row and add to the list table                  |
                // +------------------------------------------------------------+
                rows = new TableRow();
                rows
                    .addCell(new TableDataCell(new Link(link1,new SimpleItem(
                        planamNames[i]).setFontSize(1))) )
                    .addCell(new TableDataCell(formatStr(platformNames[i])))
                    .addCell(new TableDataCell(formatStr(callSigns[i])))
                    ;
                tab.addRow(rows);

            }  //  for (int i = 0; i < numRecs; i++)

            this.addItem(tab);

            // +------------------------------------------------------------+
            // | If there are more names,   create a button for the user to |
            // | click for the next names    on the list.                   |
            // +------------------------------------------------------------+
            if (numRecs == MAX_RECS) {
                Form morenames = new Form("GET", sc.APP);
                morenames
                    .addItem(new Hidden(sc.SCREEN, sc.PLATFORMS))
                    .addItem(new Hidden(sc.PLATFORM, planamNames[numRecs-1]) )
                    .addItem(new Hidden(sc.PSC, sessionCode))
                    .addItem(new Submit("Submit", "More") )
                    ;
                this.addItem(morenames);

            } // if (numRecs == MAX_RECS)

        } // if (!"".equals(platform))


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
            HtmlHead hd = new HtmlHead("GetPlatform local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new GetPlatform(args));
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

} // class GetPlatform
