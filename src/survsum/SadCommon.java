package survsum;

import java.io.File;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import oracle.html.*;

class SadCommon {


    /** login details */
    static final public String USER          = "sadco";
    static final public String PWD           = "ter91qes";
    static final public String DRIVER        = "org.postgresql.Driver";
    static final public String CONNECTION    = "jdbc:postgresql://localhost:5432/sadco"; // stel-oradb
    
    /**
     * Look up a URL parameter
     * @param   args    list of url name-value parameter pairs
     * @param   name    name of parameter to find value for
     */
    public static String getArgument(String args[], String name) {
    	
        String prefix = name + "=";
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith(prefix) ) {
                return args[i].substring(prefix.length());
            }  //  if (args[i].startsWith(prefix) )
        }  //  for (int i = 0; i < args.length; i++)
        return "";
    } // getArgument


    /**
     * Format text strings for html
     * @param   oldStr  the text to format
     */
    public static Container formatStr(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(2));
        return newStrc;
    } // formatStr


    /**
     * Format text strings for html (big)
     * @param   oldStr  the text to format
     */
    public static Container formatStrBig(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(3));
        return newStrc;
    } // formatStrBig


    /**
     * Identify the select item that is to be flagged as default
     * @param    item
     * @param    prevSelect
     * @return   true/false
     */
    public boolean getDefault(String item, String prevSelect) {
        return (item.equals(prevSelect) ? true : false);
    } // getDefault


    /**
     * Identify the select item that is to be flagged as default
     * @param    item
     * @param    prevSelect
     * @return   true/false
     */
    public boolean getDefault(int item, int prevSelect) {
        return (item == prevSelect ? true : false);
    } // getDefault


    /**
     * Return space iso null
     * @param   text    text to test for null
     */
    public String spaceIfNull(String text) {
        return (text == null ? "" : text);
    } // spaceIfNull



    // +============================================================+
    // | String getNewStationId(String, String)                     |
    // +============================================================+
    // | Create a new sequential station_id                         |
    // +------------------------------------------------------------+
    public String getNewStationId(String stationIdPre, String stationId) {
        String  newStationId = new String("");

        String snum;
        String convert = new String();
        Integer inum = new Integer(0);

        if (stationId.length() < 1)   {
            // +------------------------------------------------------------+
            // | There is no previous sequential station_id for the         |
            // | selected prefix. Create a "zero" sequential station_id     |
            // +------------------------------------------------------------+
            newStationId = stationIdPre + "SQ0000000";

        }  else  {
            // +------------------------------------------------------------+
            // | The lasted sequential station_id has been found for the    |
            // | selected prefix.                                           |
            // +------------------------------------------------------------+

            // +------------------------------------------------------------+
            // | - store the numeric part of the last sequential station_id |
            // | - convert the string number into a long and add to it so   |
            // |   that the number is incremented by one and leading zeros  |
            // |   are forced into the result.                              |
            // | - The long number is returned to a string and the first    |
            // |   digit, 1, now a character, is removed, resulting in a    |
            // |   string that starts with leading zeros                    |
            // | - The new station_id is reassembled starting by the        |
            // |   selected prefix, followed by "SQ" which identifies the   |
            // |   station_id as a sequential one, followed by the new      |
            // |   sequential number in a string format.                    |
            // | e.g. last sequential station_id = EMDSQ0001303             |
            // |      new  sequential station_id = EMDSQ0001304             |
            // +------------------------------------------------------------+
            snum = stationId.substring(5,12);
            long lnum = inum.valueOf(snum).intValue() + 10000001;
            snum = convert.valueOf(lnum).substring(1,8);
            newStationId = stationIdPre + "SQ" + snum;
            //System.out.println("hello3" + newStationId); //kyle hack
        }  //  if (stationId.length() = 0)
        
        return  newStationId;

    } // getNewStationId


    // +============================================================+
    // | boolean isNotNumeric(String)                               |
    // +============================================================+
    // | Create a new sequential station_id                         |
    // +------------------------------------------------------------+
    public boolean isNotNumeric(String inputString) {
        boolean test = false;

        for (int i = 0; i < inputString.length(); i++)    {
            char ch = inputString.charAt(i);
            if (!((ch >= '0' && ch <= '9') || ch == '.' || ch == '-')) test = true;
        }  //  for (int i = 0; i < inputString.length(); i++)

        return test;
    } // isNumeric


    // +============================================================+
    // | boolean isNotTime(String)                                  |
    // +============================================================+
    // | Test the validity of the time                              |
    // +------------------------------------------------------------+
    public boolean isNotTime(String inputString)   {
        boolean test = false;
        Integer Itest = new Integer(0);

        // +------------------------------------------------------------+
        // | Test for non-numeric characters                            |
        // +------------------------------------------------------------+
        for (int i = 0; i < inputString.length(); i++)    {
            char ch = inputString.charAt(i);
            if (!((ch >= '0' && ch <= '9') || ch == '.' || ch == ':')) test = true;
        }  //  for (int i = 0; i < inputString.length(); i++)

        // +------------------------------------------------------------+
        // | If there are only numeric or . or : test hours, minutes    |
        // | and seconds                                                |
        // +------------------------------------------------------------+
        if (!test)   {
            // +------------------------------------------------------------+
            // | test hours                                                 |
            // +------------------------------------------------------------+
            int itest = Itest.valueOf(inputString.substring(0,2)).intValue();
            if (!(itest >= 0 && itest <= 23)) test = true;

            // +------------------------------------------------------------+
            // | test minutes                                               |
            // +------------------------------------------------------------+
            itest = Itest.valueOf(inputString.substring(3,5)).intValue();
            if (!(itest >= 0 && itest <= 59)) test = true;

            // +------------------------------------------------------------+
            // | test seconds                                               |
            // +------------------------------------------------------------+
            itest = Itest.valueOf(inputString.substring(6,8)).intValue();
            if (!(itest >= 0 && itest <= 59)) test = true;
        }  //  if (!test)

        return test;
    }  //  public boolean isNumeric(String inputString)


    // +============================================================+
    // | void writeFileLine(String,String)                          |
    // +============================================================+
    // | Open an output file and write a line of data               |
    // +------------------------------------------------------------+
    public void writeFileLine(String fileName, String dataLine) {
        try  {
            RandomAccessFile file = new RandomAccessFile(fileName , "rw");
            file.skipBytes((int)file.length());
//            file.writeBytes(dataLine + "\015");
            file.writeBytes(dataLine + "\n");
            file.close();
        }   catch (Exception e)  {
            System.out.println("<br>Error: " + e.toString());
        }  //  catch (Exception e)

    }  //  private static void writeFile(String fileName, String dataLine)


    // +============================================================+
    // | void createNewFile(String)                                 |
    // +------------------------------------------------------------+
    // | Create a new file                                          |
    // +------------------------------------------------------------+
    public void createNewFile(String infile) {
        File file = new File(infile);
        try  {
//          if (!file.exists())
//            {
            file.createNewFile();
//            }  //  if (mtlFile.exists()
        }   catch (Exception e)  {
            System.out.println("<br>Error: " + e.toString());
        }  //  catch (Exception e)
    }  //  private static void deleteOldFile(String file)



    // +============================================================+
    // | void deleteOldFile(String)                                 |
    // +------------------------------------------------------------+
    // | Does the file already exist? If it does it needs to be     |
    // | deleted first.                                             |
    // +------------------------------------------------------------+
    public void deleteOldFile(String infile) {
        File file = new File(infile);
        if (file.exists()) {
            file.delete();
        }  //  if (mtlFile.exists()
    }  //  private static void deleteOldFile(String file)


    /**
     * get connected to the database
     */
    Connection getConnected(String callingClass) {
        return getConnected(callingClass, "");
    } // getConnected


    /**
     * get connected to the database
     */
    Connection getConnected(String callingClass, String sessionCode) {
        Connection conn = null;
        try {
            //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            //conn = DriverManager.getConnection
            //("jdbc:oracle:oci8:@etek8.csir.co.za","sadco","ter91qes");
            Class.forName(DRIVER);                                      //ub05
            //conn = DriverManager.getConnection
            //    ("jdbc:oracle:thin:" + sc.USER + "/" + sc.PWD +
            //     sc.CONNECTION);          // steamer
                 //"@morph.csir.co.za:1522:etek8");
            conn = DriverManager.getConnection(                         //ub05
                CONNECTION, USER,  PWD);                                //ub05
            System.out.println(callingClass + ":p:" +
                getDateTime() + "-" + getUser(conn, sessionCode));
//            System.out.println(callingClass + ": connected to postgres: " +
//                getDateTime() + " - " + getUser(conn, sessionCode));
            conn.setAutoCommit(false);
        } catch (Exception e) {
            return null;
        } // try-catch
        return conn;
    } // getConnected


    /**
     * get the current date and time
     */
    public String getDateTime() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd't'HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    } // getDateTime


    /**
     * get the current user
     */
    private String getUser(Connection conn, String sessionCode) {

        // get the user's e-mail address
        String email = "";
        if (!"".equals(sessionCode)) {
            try {
                String sql =
                    "select userid from log_sessions " +
                    "where code = " + sessionCode;
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery(sql);
                while (rset.next()) {
                    email = checkNull(rset.getString(1));
                } // while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;
            } catch (SQLException e) { }
        } // if (!"".equals(sessionCode))

        return email;
    } // getUser


    /**
     * check if the value is null
     * returns  value / empty string
     */
    String checkNull(String value) {
        return (value != null ? value : "");
    } // checkNull

    /**
     * connect to the postgres db
     */
/*
    Connection getConnected(String callingClass) {
        Connection conn = null;
        try {
            //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            //conn = DriverManager.getConnection
            //("jdbc:oracle:oci8:@etek8.csir.co.za","sadco","ter91qes");
            Class.forName(DRIVER);                                      //ub05
            //conn = DriverManager.getConnection
            //    ("jdbc:oracle:thin:" + sc.USER + "/" + sc.PWD +
            //     sc.CONNECTION);          // steamer
                 //"@morph.csir.co.za:1522:etek8");
            conn = DriverManager.getConnection(                         //ub05
                CONNECTION, USER,  PWD);                                //ub05
            System.out.println(callingClass + ": connected to postgres");
            conn.setAutoCommit(false);
        } catch (Exception e) {
            return null;
        } // try-catch
        return conn;
    } // getConnected
*/

    void disConnect(Statement stmt, ResultSet rset, Connection conn) {
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
    } // disConnect

}  //  class SadCommon



