package sadinv;

import oracle.html.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import sadco.LogSessions;
import sadco.SadUsers;

/**
 * This class logs the user into SADCO.  If he is not registerd, the startup screen is
 * called again, with an appropriate message, else it displays the 'you are logged in'
 * screen and updates the button panel on the left with the session code (psc).
 *
 * SadInv
 * pscreen = login
 *
 * @author 20091117 - SIT Group.
 * @version
 * 20091117 - Ursula von St Ange - create program                       <br>
 */
public class Login extends CompoundItem {

    /** For debugging code: true/false */
    static boolean dbg = false;
    //static boolean dbg = true;
    static boolean dbg2 = false;
    //static boolean dbg2 = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    static private Connection conn = null;
    static private java.sql.Statement stmt = null;
    static private ResultSet rset = null;

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    // work variables
    String firstName = "";
    String userPassword = "";
    int sessionCode = 0;
    String userOK = "";

    /**
     * main constructor
     */
    public Login (String args[]) {

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass);
        String sql = "";

        // get the argument values
        String email    = ec.getArgument(args,sc.EMAIL);
        String password = ec.getArgument(args,sc.PASSWORD);
        if (dbg) System.out.println("<br>" + thisClass + " email = " + email);
        if (dbg) System.out.println("<br>" + thisClass + " password = " + password);
        if (dbg2) {
            email = "uvstange@csir.co.za";
            password = "mikki1";
        } // if (dbg2)

        // check whether the user is registered
        //SadUsers user = new SadUsers();
        //user.setUserid(email);
        //SadUsers users[] = user.get();
        //userPassword = users[0].getPassword();
        //firstName = users[0].getFname();
        try {
            sql =
                "select password, fname " +
                "from sad_users " +
                "where userid = '" + email + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                userPassword = checkNull(rset.getString(1));
                firstName = checkNull(rset.getString(2));
            } // while (rset.next())
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;
        } catch (SQLException e) { }


        // check user status
        if (userPassword.equals("")) { //

            // user does not exist
            userOK = sc.ERROR_USER;

        } else if (!password.equals(userPassword)) {

            // user entered wrong password
            userOK = sc.ERROR_PASSW;

        } else {

            //LogSessions session = new LogSessions();
            //session.setUserid(email);
            //session.setDateTime(new java.util.Date());
            //if (dbg) System.out.println("<br>" + thisClass + " session = " + session.toString());
            //session.put();
            //sessionCode = session.getCode("");
            try {

                // get the max code
                sql =
                    "select max(code) as code " +
                    "from log_sessions";
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                while (rset.next()) {
                    sessionCode = Integer.parseInt(checkNull(rset.getString(1)));
                } // while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

                // insert the new record
                sessionCode++;
                Timestamp ts = new Timestamp(new java.util.Date().getTime());
                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
                String now = formatter.format(ts);
                sql =
                    "insert into log_sessions values(" + sessionCode + ",'" +
                    email + "','" + now + "')";
                if (dbg) System.out.println("<br>" + thisClass + " sql = " + sql);
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                if (dbg) System.out.println("<br>" + thisClass + " num = " + num);
                stmt.close();
                stmt = null;
            } catch (SQLException e) {
                e.printStackTrace();
                ec.processErrorStatic(e, "Login", "Constructor", "");
            }
            if (dbg) System.out.println("<br>" + thisClass + " sessionCode = " + sessionCode);

        } // if (users.length == 0)
        if (dbg) System.out.println("<br>" + thisClass + " userOK = " + userOK);


        if ("".equals(userOK)) {
            // show the 'login successfull' screen

            // create the main table
            DynamicTable mainTab = new DynamicTable(1);
            mainTab
                .setBorder(0)
                .setRules(ITableRules.NONE)
                .setFrame(ITableFrame.VOLD)
                ;

            // create the container to hook everyhting on to
            Container con = new Container();
            con
                .addItem(new SimpleItem("Welcome to the SADCO inventory, " + firstName)
                    .setFontSize(6))
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("You are logged in as <b>" + email + " </b>")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("Continue to browsing the inventory by " +
                    "clicking on one of the buttons on the left.")
                ;

            // add everthing to the main table, and add to the screen
            TableRow rows = new TableRow();
            rows
                .addCell(new TableDataCell(" ").setWidth(20))
                .addCell(new TableDataCell(con))
                ;
            mainTab.addRow(rows);
            this.addItem("</body><body onload=buttons()>");
            this.addItem(buttonFunction(String.valueOf(sessionCode)));
            this.addItem(mainTab);

        } else {

            // call the startup screen again, with an error message
            this.addItem("</body><body onload=startup()>");
            this.addItem(startupFunction(userOK, email));

        } // if (userOK)

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
                conn.commit();
                conn.close();
                conn = null;
                if (dbg) System.out.println("<br>" + thisClass + " connection closed");
            } // if (conn != null)
        }  catch (SQLException se){
            se.printStackTrace();
            ec.processErrorStatic(se, "Login", "Constructor", "");
        }
        // close the connection to the database
        //common2.DbAccessC.close();

    } // constructor Login


    /**
     * Generate the javascript buttons() function
     * @param   sessionCode the session code
     */
    String buttonFunction(String sessionCode) {

        // call the Buttons class and get the HTML version
        if (dbg) System.out.println("<br>" + thisClass +
            ".buttonFunction: sessionCode = " + sessionCode);
        //String args2[] = {sc.PSC + "=" + sessionCode};
        //String html = new Buttons(args2).toHTML().replaceAll("\"","\\\\\"");
        //String html = new Buttons(args2).toHTML().replaceAll("\"","'");

        // write the script
        StringBuffer text = new StringBuffer("");
        text.append("<script>" + sc.CR);
        text.append("function buttons() {" + sc.CR);
        text.append("  with (parent._buttons.document){" + sc.CR);
        text.append("    open()" + sc.CR);

        // write each line of the buttons panel separately
        //StringTokenizer t = new StringTokenizer(html,sc.CR);
        //while(t.hasMoreTokens()) {
        //    text.append("    writeln(\"" + t.nextToken() + "\")" + sc.CR);
        //} // while(t.hasMoreTokens())

        text.append("    writeln(\"<html>\")" + sc.CR);
        text.append("    writeln(\"<head>\")" + sc.CR);
        text.append("    writeln(\"<META HTTP-EQUIV='refresh' CONTENT='0;URL=" + sc.APP +
            "?" + sc.SCREEN + "=" + sc.BUTTONS +
            "&" + sc.PSC + "=" + sessionCode + "'>\")" + sc.CR);
        text.append("    writeln(\"</head>\")" + sc.CR);
        text.append("    writeln(\"<body>\")" + sc.CR);
        text.append("    writeln(\"aaaa\")" + sc.CR);
        text.append("    writeln(\"</body>\")" + sc.CR);
        text.append("    writeln(\"</html>\")" + sc.CR);

        // close off
        text.append("    close()" + sc.CR);
        text.append("  }" + sc.CR);
        text.append("}" + sc.CR);
        text.append("</script>" + sc.CR);
        return text.toString();
    } // buttonFunction


    /**
     * Generate the javascript login() function
     * @param   sessionCode the session code
     */
    String startupFunction(String userOK, String email) {

        // call the Buttons class and get the HTML version
        //String args2[] = {sc.USEROK+"="+userOK, sc.EMAIL+"="+email};
        //String html = new Startup(args2).toHTML().replaceAll("\"","\\\\\"");
        //String html = new Startup(args2).toHTML().replaceAll("\"","'");

        // write the script
        StringBuffer text = new StringBuffer("");
        text.append("<script>" + sc.CR);
        text.append("function startup() {" + sc.CR);
        text.append("  with (document){" + sc.CR);
        text.append("    open()" + sc.CR);

        // write each line of the buttons panel separately
        //StringTokenizer t = new StringTokenizer(html,sc.CR);
        //while(t.hasMoreTokens()) {
        //    text.append("    writeln(\"" + t.nextToken() + "\")" + sc.CR);
        //} // while(t.hasMoreTokens())

        text.append("    writeln(\"<html>\")" + sc.CR);
        text.append("    writeln(\"<head>\")" + sc.CR);
        text.append("    writeln(\"<META HTTP-EQUIV='refresh' CONTENT='0;URL=" + sc.APP +
            "?" + sc.SCREEN + "=" + sc.STARTUP +
            "&" + sc.VERSION + "=" + sc.LOGIN +
            "&" + sc.USEROK + "=" + userOK +
            "&" + sc.EMAIL + "=" + email + "'>\")" + sc.CR);
        text.append("    writeln(\"</head>\")" + sc.CR);
        text.append("    writeln(\"<body>\")" + sc.CR);
        text.append("    writeln(\"aaaa\")" + sc.CR);
        text.append("    writeln(\"</body>\")" + sc.CR);
        text.append("    writeln(\"</html>\")" + sc.CR);

        // close off
        text.append("    close()" + sc.CR);
        text.append("  }" + sc.CR);
        text.append("}" + sc.CR);
        text.append("</script>" + sc.CR);
        return text.toString();

    } // startupFunction


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
            HtmlHead hd = new HtmlHead("Login local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new Login(args));
            bd
                .setActivatedLinkColor   ("#000088")
                .setFollowedLinkColor    ("#663366")
                .setUnfollowedLinkColor  ("#666666")
                ;
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            ec.processErrorStatic(e, "Login", "Constructor", "");
        } // try-catch
        // close the connection to the database
        //common2.DbAccessC.close();
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
                conn.commit();
                conn.close();
                conn = null;
            } // if (conn != null)
        }  catch (SQLException se){}

    } // main

} // class Login