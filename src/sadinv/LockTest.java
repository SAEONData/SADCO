package sadinv;//import java.util.StringTokenizer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//import sadco.SadUsers;
//import sadco.LogSessions;

public class LockTest {

    /** For debugging code: true/false */
    static boolean dbg = false;
    //static boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    static private Connection conn = null;
    static private Statement stmt = null;
    static private ResultSet rset = null;
    String sql = "";

    int count = 0;

    /**
     * main constructor
     */
    public LockTest (String args[]) {

        String charCol = args[0];

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection
                ("jdbc:oracle:thin:sadco/ter91qes@146.64.23.200:1522:etek8");// steamer
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        } // try-catch


        // lock the table
        try {
            stmt = conn.createStatement();
            stmt.execute("lock table test in share mode");
            stmt.close();
            stmt = null;
        } catch (Exception e) {
            e.printStackTrace();
        } // try-catch

        // get the max code
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select max(numCol) from test");
            while (rset.next()) {
                count = rset.getInt(1);
            } // while (rset.next())
            rset.close();
            rset = null;
            stmt.close();
            stmt = null;
        } catch (Exception e) {
            e.printStackTrace();
        } // try-catch

        count++;

        // insert new row
        try {
            sql = "insert into test values (" + count + ",'" + charCol + "')";
            stmt = conn.createStatement();
            int numRecords = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } // try-catch

        // close connection
        try {
            conn.close();
            conn = null;
        } catch (Exception e) {
            e.printStackTrace();
        } // try-catch

    } // constructor sadinv.LockTest


    /**
     * The main procedure for processing in standalone mode
     * @param   args[]  array of command-line / URL arguments
     */
    public static void main(String args[]) {
        try {
            LockTest local = new LockTest(args);
        } catch (Exception e) {
            e.printStackTrace();
        } // try-catch
    } // main

} // class sadinv.LockTest