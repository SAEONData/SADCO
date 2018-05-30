package sadinv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import oracle.html.*;
//import sadco.SadUsers;
//import sadco.LogSessions;

/**
 * This class registers the user for the SADCO inventory.  If he is already registerd,
 * the startup screen is called again with an appropriate message, else it displays the
 * 'you are registered'.
 *
 * SadInv
 * pscreen = register
 *
 * @author 20091130 - SIT Group.
 * @version
 * 20091130 - Ursula von St Ange - create program                       <br>
 */
public class Register extends CompoundItem {

    /** For debugging code: true/false */
    static boolean dbg = false;
    //static boolean dbg = true;
    static boolean dbg2 = false;
    //static boolean dbg2 = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    static private Connection conn = null;
    static private java.sql.Statement stmt = null;
    static private java.sql.PreparedStatement pStmt = null;
    static private ResultSet rset = null;

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    // work variables
    String userExists = "";
    String userOK = "";

    /**
     * main constructor
     */
    public Register (String args[]) {

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass);
        String sql = "";

        // get the argument values
        String firstName    = ec.getArgument(args,sc.FNAME);
        String surname      = ec.getArgument(args,sc.SURNAME);
        String affiliation  = ec.getArgument(args,sc.AFFIL);
        String occupation   = ec .getArgument(args,sc.OCCUPATION);
        String address      = ec.getArgument(args,sc.ADDRESS);
        String email        = ec.getArgument(args,sc.EMAIL);
        String password     = ec.getArgument(args,sc.PASSWORD);
        if (dbg) System.out.println("<br>" + thisClass + " firstName = " + firstName);
        if (dbg) System.out.println("<br>" + thisClass + " surname = " + surname);
        if (dbg) System.out.println("<br>" + thisClass + " affiliation = " + affiliation);
        if (dbg) System.out.println("<br>" + thisClass + " occupation = " + occupation);
        if (dbg) System.out.println("<br>" + thisClass + " address = " + address);
        if (dbg) System.out.println("<br>" + thisClass + " email = " + email);
        if (dbg) System.out.println("<br>" + thisClass + " password = " + password);
        if (dbg2) {
            firstName    = "Philip";
            surname      = "Haupt";
            affiliation  = "Nelson Mandela Metropolitan University";
            occupation   = "student";
            address      = "PO Box 13053, Cascades, 3203";
            email        = "philip.haupt@gmail.com";
            password     = "dataminer";
        } // if (dbg2)

        // check whether the user is registered
        //SadUsers user = new SadUsers();
        //user.setUserid(email);
        //SadUsers users[] = user.get();
        try {
            sql =
                "select count(*) " +
                "from sad_users " +
                "where userid = '" + email + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                userExists = checkNull(rset.getString(1));
            } // while (rset.next())
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;
        } catch (SQLException e) { }

        if (dbg) System.out.println("<br>" + thisClass + " sql = " + sql);
        if (dbg) System.out.println("<br>" + thisClass + " userExists = " + userExists);

        if (userExists.equals("1")) {

            // user already on system
            userOK = sc.ERROR_USER;

            // call the startup screen again, with an error message
            this.addItem("</body><body onload=startup()>");
            this.addItem(startupFunction(userOK, email));

        } else {

            // register the user
            //user.setFname(firstName);
            //user.setSurname(surname);
            //user.setAffiliation(affiliation);
            //user.setAddress(address);
            //user.setPassword(password);
            //user.setUserType(0);
            //user.setFlagType(2);
            //user.put();
            try {
                // insert the new record
                sql =
                    //"insert into sad_users values('" + email + "','" +
                    //password + "',0,2,null,'" + firstName + "','" +
                    //surname + "','" + affiliation + "','" + address + "','" +
                    //occupation + "')";
                    "insert into sad_users values(?,?,0,2,null,?,?,?,?,?)";
                if (dbg) System.out.println("<br>" + thisClass + " sql = " + sql);
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, email);
                pStmt.setString(2, password);
                pStmt.setString(3, firstName);
                pStmt.setString(4, surname);
                pStmt.setString(5, affiliation);
                pStmt.setString(6, address);
                pStmt.setString(7, occupation);

                int num = pStmt.executeUpdate();
                if (dbg) System.out.println("<br>" + thisClass + " num = " + num);
                pStmt.close();
                pStmt = null;

                // commit so that the e-mail system gets the user's details
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                ec.processErrorStatic(e, "Login", "Constructor", "");
            }

            // have to close the database, else the system doesn't commit,
            // and the e-mail system does not get the user's details
            //common2.DbAccessC.close();

            // send the password
            String args2[] = {sc.VERSION + "=" + sc.REGISTER,sc.EMAIL + "=" + email};
            new SendEmail(args2);

            // create the main table and container
            DynamicTable mainTab = new DynamicTable(1);
            mainTab
                .setBorder(0)
                .setRules(ITableRules.NONE)
                .setFrame(ITableFrame.VOLD)
                ;

            // create the container to hook everyhting on to
            Container con = new Container();
            con
                .addItem(new SimpleItem("Thank you for registering for the " +
                    "SADCO inventory, " + firstName)
                    .setFontSize(6))
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("You will receive an e-mail from the data centre soon.")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("In the mean time, feel free to continue " +
                    "browsing the inventory by clicking on one of the " +
                    "buttons on the left.")
                ;

            // add everthing to the main table, and add to the screen
            TableRow rows = new TableRow();
            rows
                .addCell(new TableDataCell(" ").setWidth(20))
                .addCell(new TableDataCell(con))
                ;
            mainTab.addRow(rows);
            this.addItem(mainTab);

        } // if (userExists.equals("1"))

        if (dbg) System.out.println("<br>" + thisClass + " userOK = " + userOK);

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

    } // constructor Register


    /**
     * Generate the javascript login() function
     * @param   sessionCode the session code
     */
    String startupFunction(String userOK, String email) {

        // write the script
        StringBuffer text = new StringBuffer("");
        text.append("<script>" + sc.CR);
        text.append("function startup() {" + sc.CR);
        text.append("  with (document){" + sc.CR);
        text.append("    open()" + sc.CR);

        // write the page contents
        text.append("    writeln(\"<html>\")" + sc.CR);
        text.append("    writeln(\"<head>\")" + sc.CR);
        text.append("    writeln(\"<META HTTP-EQUIV='refresh' CONTENT='0;URL=" + sc.APP +
            "?" + sc.SCREEN + "=" + sc.STARTUP +
            "&" + sc.VERSION + "=" + sc.REGISTER +
            "&" + sc.USEROK + "=" + userOK +
            "&" + sc.EMAIL + "=" + email + "'>\")" + sc.CR);
        text.append("    writeln(\"<html>\")" + sc.CR);
        text.append("    writeln(\"<head>\")" + sc.CR);
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
            HtmlHead hd = new HtmlHead("Register local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new Register(args));
            bd
                .setActivatedLinkColor   ("#000088")
                .setFollowedLinkColor    ("#663366")
                .setUnfollowedLinkColor  ("#666666")
                ;
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            ec.processErrorStatic(e, "Register", "Constructor", "");
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

} // class Register