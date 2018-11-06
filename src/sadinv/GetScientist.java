package sadinv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.html.*;


/**
 * This class handles the search on scientists
 *
 * SadInv
 * pscreen = scientists
 * pinstitute = <scientist>
 *
 * @author 20090112 - SIT Group.
 * @version
 * 20090112 - Ursula von St Ange - create program                       <br>
 * 20100507 - Ursula von St Ange - change for postgres             ub01 <br>
 */

public class GetScientist extends CompoundItem  {

    boolean dbg = true;
    //boolean dbg = false;

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
    public GetScientist(String args[]) {

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String surname = ec.getArgument(args,sc.SURNAME);
        String sessionCode  = ec.getArgument(args,sc.PSC);
        if (dbg) System.out.println("surname = " + surname);

        // +------------------------------------------------------------+
        // | Enter a Scientist Name                                     |
        // +------------------------------------------------------------+
        Form frm = new Form("GET", sc.APP);

        frm
            .addItem(new SimpleItem(
                "Enter first few characters of the Scientist's" +
                " Surname and press <b>ENTER</b>: "))
            .addItem(new Hidden(sc.SCREEN, sc.SCIENTISTS))
            .addItem(new Hidden(sc.PSC, sessionCode))
            .addItem(new TextField(sc.SURNAME, 20, 20, ""))
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
        if (!"".equals(surname)) {

            // +------------------------------------------------------------+
            // | Build the empty arrays for holding data                    |
            // +------------------------------------------------------------+
            String  scientistsCodes[]   = new String[MAX_RECS];
            String  surnames[]          = new String[MAX_RECS];
            String  fNames[]            = new String[MAX_RECS];
            String  titles[]            = new String[MAX_RECS];
            String  institutes[]        = new String[MAX_RECS];

            int numRecs = 0;
            try {

                sql =
                    " select sc.surname, sc.f_name, sc.title, "+
                    " inst.name, sc.code"+
                    " from scientists sc, institutes inst "+
                    " where upper(sc.surname) >= upper('"+surname+"')"+
                    " and inst.code = sc.instit_code "+
                    " order by upper(sc.surname)" ;
                
                if (dbg) System.out.println("sql = " + sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                int i = 0;
                
                while (rset.next() && (i < MAX_RECS)) {
                	
                	
                    surnames[i]   = checkNull(rset.getString(1));
                    fNames  [i]   = checkNull(rset.getString(2));
                    titles  [i]   = checkNull(rset.getString(3));
                    institutes[i] = checkNull(rset.getString(4));
                    scientistsCodes[i] = checkNull(rset.getString(5));
                    if (dbg) System.out.println("surnames[i] = " + i + " " + surnames[i]);
                    i++;
                } //while (rset.next() && (i < MAX_RECS)) {

                numRecs = i;
                if (dbg) System.out.println("numRecs"+numRecs);

                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

            } catch (SQLException e) {
            	if (dbg) e.printStackTrace() ;
            }


            // +------------------------------------------------------------+
            // | Prompt user to click on scientist name to select           |
            // +------------------------------------------------------------+
            this
                //.addItem(SimpleItem.LineBreak)
                .addItem(new SimpleItem("Click on a scientist name for more information."))
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
                .addCell(new TableHeaderCell(formatStr("Scientist")))
                .addCell(new TableHeaderCell(formatStr("Institute")))
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
                    sc.APP + "?" + sc.SCREEN + "=" + sc.SCIENTISTS2 + "&" +
                    sc.CODE + "=" + scientistsCodes[i] + "&" +
                    sc.PSC + "=" + sessionCode);

                // +------------------------------------------------------------+
                // | Build a new row and add to the list table                  |
                // +------------------------------------------------------------+
                rows = new TableRow();
                rows.addCell(new TableDataCell(new Link(link1,new SimpleItem(
                         surnames[i] + ", "  +
                         titles  [i] + " "   +
                         fNames  [i]).setFontSize(1))) );
                rows.addCell(new TableDataCell(formatStr(institutes[i])));

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
                    .addItem(new Hidden(sc.SURNAME, surnames[numRecs-1]) )
                    .addItem(new Hidden(sc.SCREEN, sc.SCIENTISTS))
                    .addItem(new Hidden(sc.PSC, sessionCode))
                    ;
                this.addItem(morenames);

            } // if (numRecs == MAX_RECS)

        } // if (!"".equals(surname))

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


    } // constructor GetScientist


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
            HtmlHead hd = new HtmlHead("GetScientist local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new GetScientist(args));
            hp.printHeader();
            hp.print();
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

} // class GetScientist
