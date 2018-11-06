package common2;

import java.sql.Timestamp;
//import java.util.*;

/**
 * This file contains the parent class for all tables in all databases      |
 * The parent class is called 'TablesC', and all the child classes in the   |
 * separate packages are called 'Tables'.  The actual tables classes are    |
 * called the same as the table name, without the application prefix, e.g.  |
 * DBN for RemVid, and SRV for Surveys.                                     |
 *
 * @author 030520 - SIT Group
 * @version
 * 19991024 - Ursula von St Ange - create class                         <br>
 * 20000119 - Ursula von St Ange - ub01: change NULL value for float, add
 *                                 for double                           <br>
 * 20000314 - Ursula von St Ange - copied from surveys package.         <br>
 * 20000918 - Ursula von St Ange - ub02: added error reporting variables<br>
 * 20030520 - Ursula von St Ange - ub03: add methods to get hold of DbAccessC
 *                                 variables                            <br>
 * 20040129 - Ursula von St Ange - ub04: introduce a second lot of NULL
 *                                 values so one can update a value to
 *                                 NULL if need be. Also introduce ...MAX
 *                                 values for ...MAX constants in table
 *                                 classes                              <br>
 * 20040202 - Ursula von St Ange - ub05: introduce ... MIN constants    <br>
 * 20100507 - Ursula von St Ange - ub06: change for postgres            <br>
 *
 */

public class TablesC {

    boolean dbg = false;
    //boolean dbg = true;
    String thisClass = this.getClass().getName();


    /** The null value for all 'code' fields */
    public static final int    CODENULL = -1;
    public static final int    CODENULL2 = -2;                          // ub04
    /** The null value for all int fields */                            // ub01
    public static final int    INTNULL = Integer.MIN_VALUE;             // ub01
    public static final int    INTNULL2 = INTNULL + 1;                  // ub04
    public static final int    INTMIN = Integer.MIN_VALUE;              // ub05
    public static final int    INTMAX = Integer.MAX_VALUE;              // ub04
    /** The null value for all long fields*/
    public static final long   LONGNULL = Long.MIN_VALUE;               // ub02
    public static final long   LONGNULL2 = LONGNULL + 1;                // ub04
    public static final long   LONGMIN = Long.MIN_VALUE;                // ub05
    public static final long   LONGMAX = Long.MAX_VALUE;                // ub04
    /** The null value for all character fields */
    public static final String CHARNULL = null;
    public static final String CHARNULL2 = "";                          // ub04
    /** The null value for all float fields */
    public static final float  FLOATNULL = Float.MIN_VALUE;             // ub01
    public static final float  FLOATNULL2 = FLOATNULL + 1E-44f;         // ub04
    public static final float  FLOATMIN = -Float.MAX_VALUE;             // ub05
    public static final float  FLOATMAX = Float.MAX_VALUE;              // ub04
    /** The null value for all double fields */
    public static final double DOUBLENULL = Double.MIN_VALUE;           // ub01
    public static final double DOUBLENULL2 = DOUBLENULL + 4E-323;       // ub04
    public static final double DOUBLEMIN = -Double.MAX_VALUE;           // ub05
    public static final double DOUBLEMAX = Double.MAX_VALUE;            // ub04
    /** The null value for all Timestamp fields */
    public static final Timestamp  DATENULL =
        Timestamp.valueOf("1800-01-01 00:00:00");
    public static final Timestamp  DATENULL2 =                          // ub04
        Timestamp.valueOf("1800-01-01 00:00:01");                       // ub04
    public static final Timestamp  DATEMIN =                            // ub05
        Timestamp.valueOf("1600-01-01 00:00:00");                       // ub05
    public static final Timestamp  DATEMAX =                            // ub04
        Timestamp.valueOf("2999-01-01 00:00:00");                       // ub04

    /** error level: no error */                                     // ub02
    public static final int    ERROR_NORMAL = 0;                     // ub02
    /** error level: local error e.g out-of-bounds */                // ub02
    public static final int    ERROR_LOCAL = 1;                      // ub02
    /** error level: system error */                                 // ub02
    public static final int    ERROR_SYSTEM = 2;                     // ub02

    /** the holder of error messages */                              // ub02
    String errorMessage;                                             // ub02


    /** The database accessor class that does all the actual work. */
    public DbAccessC db;
    public static int driver;


    /**
     * Makes use of TablesC constructor where the DbAccessC class is
     * instantiated.
     */
    public TablesC (String user, String pwd) {
        if (dbg) System.out.println ("<br>" + thisClass + ".constructor1 (TablesC)" + driver); // debug
        //if (dbg) System.out.println("<br>common2.TableC.DATENULL1 = " + DATENULL);
        db = new DbAccessC(user, pwd);
        driver = db.getDriver();
        clearVars();
        //if (dbg) System.out.println("<br>common2.TableC.DATENULL2 = " + DATENULL);
    } // TablesC constructor


    /**
     * Makes use of TablesC constructor where the DbAccessC class is
     * instantiated.
     */
    public TablesC (String user, String pwd, String cfgFile) {
        if (dbg) System.out.println ("<br>" + thisClass + ".constructor2 " + driver); // debug
        db = new DbAccessC(user, pwd, cfgFile);
        driver = db.getDriver();
        clearVars();
    } // TablesC constructor


    /**
     * Initialise the variables to null - just a placeholder for this
     * method in the child classes.
     */
    public void clearVars() {
        if (dbg) System.out.println("<br>" + thisClass + ".clearVars");
    }  // method clearVars


    /**
     * Returns the SQLException messages returned by e.getMessage()
     * @return warning The SQL error messages (String)
     */
    public String getMessages() {                                      // ub02
        return db.getMessages();                                       // ub02
    } // getMessages                                                   // ub02


    /**
     * Returns the number of records returned by statement.executeUpdate(sql).
     * @return numRecords The number of records process (int)
     */
    public int getNumRecords() {                                       // ub03
        return db.getNumRecords();                                     // ub03
    } // getNumRecords                                                 // ub03


    /**
     * Returns the insert DDL string
     * @return insert DDL string (String)
     */
    public String getInsStr() {                                        // ub03
        return db.getInsertStr();                                      // ub03
    } // getInsStr                                                     // ub03


    /**
     * Returns the update DDL string
     * @return update DDL string (String)
     */
    public String getUpdStr() {                                        // ub03
        return db.getUpdateStr();                                      // ub03
    } // getUpdStr                                                     // ub03


    /**
     * Returns the delete DDL string
     * @return delete DDL string (String)
     */
    public String getDelStr() {                                        // ub03
        return db.getDeleteStr();                                      // ub03
    } // getDelStr                                                     // ub03


    /**
     * Returns the select DDL string
     * @return select DDL string (String)
     */
    public String getSelStr() {                                        // ub03
        return db.getSelectStr();                                      // ub03
    } // getSelStr                                                     // ub03



    /**
     * Return the formatted string for dates. If the driver is an oracle driver,
     * e.g.:
     * <pre>to_date('1999-01-01 00:00:00','rrrr-mm-dd hh24:mi:ss')</pre>
     * if it is not an oracle driver, it returns only the date/time as string,
     * e.g.
     * <pre>'1999-01-01 00:00:00'</pre>
     * for aYear = '1999'
     * @param aDate  The date or part of a date (String)
     * @return date format (String)
     */
    public static String getDateFormat (String aDate) {
        if (aDate.length() == 4)  aDate = aDate + "-01-01 00:00:00";
        if (aDate.length() == 10) aDate = aDate + " 00:00:00";
        if (aDate.length() == 16) aDate = aDate + ":00";

        if (driver <= 2) {   // oracle drivers
            //return "to_date('" + aDate + "','rrrr-mm-dd hh24:mi:ss')";
            return "to_timestamp('" + aDate + "','yyyy-mm-dd hh24:mi:ss')"; //ub06
        } else {
            return "'" + aDate + "'";
        }
    } // method getDateFormat

    /**
     * Return the formatted string for dates. If the driver is an oracle driver,
     * e.g.:
     * <pre>to_date('1999-01-01 00:00:00','rrrr-mm-dd hh24:mi:ss')</pre>
     * if it is not an oracle driver, it returns only the date/time as string,
     * e.g.
     * If the date/time field only consists of a date, a time is appended.
     * @param aCompleteDate  The complete date, e.g. '2000-02-03 12:01:04' (String)
     * @return date format (String)
     */
    public static String getDateFormat (String aCompleteDate, String dummy) {
        if (aCompleteDate.length() == 10) {
            aCompleteDate = aCompleteDate + " 00:00:00";
        } // if length == 10
        if (driver <= 2) {   // oracle drivers
            //return "to_date('" + aCompleteDate + "','rrrr-mm-dd hh24:mi:ss')";
            return "to_timestamp('" + aCompleteDate +                       //ub06
                "','yyyy-mm-dd hh24:mi:ss')";                               //ub06
        } else {
            return "'" + aCompleteDate + "'";
        } // if driver
    } // method getDateFormat

    /**
     * Return the formatted string for dates. If the driver is an oracle driver,
     * e.g.:
     * <pre>to_date('1999-09-09 12:00:00','rrrr-mm-dd hh24:mi:ss')</pre>
     * if it is not an oracle driver, it returns only the date/time as string,
     * e.g.
     * <pre>'1999-09-09 12:00:00'</pre>
     * for aDateTime = '1999-09-09 12:00:00'
     * @param aDateTime  The date/time (Timestamp)
     * @return date format (String)
     */
    public static String getDateFormat (Timestamp aDateTime) {
        String dateTimeStr = aDateTime.toString();
        dateTimeStr = dateTimeStr.substring(0,dateTimeStr.indexOf('.'));
        if (driver <= 2) {   // oracle drivers
            //return "to_date('" + dateTimeStr + "','rrrr-mm-dd hh24:mi:ss')";
            return "to_timestamp('" + dateTimeStr + "','yyyy-mm-dd hh24:mi:ss')";   //ub06
        } else {
            return "'" + dateTimeStr + "'";
        }
    } // method getDateFormat

    /**
     * Return the formatted string for times. If the driver is an oracle driver,
     * e.g.:
     * <pre>to_date('12:00:00','hh24:mi:ss')</pre>
     * if it is not an oracle driver, it returns only the time as string,
     * e.g.
     * If the time consists only of hh:mm, the seconds are appended.
     * @param aTime  The time, e.g. '12:01:04' (String)
     * @return date format (String)
     */
    public static String getTimeFormat (String aTime) {
        if (aTime.length() == 2) aTime = aTime + ":00:00";
        if (aTime.length() == 5) aTime = aTime + ":00";
        if (driver <= 2) {   // oracle drivers
            //return "to_date('" + aTime + "','hh24:mi:ss')";
            return "to_timestamp('" + aTime + "','hh24:mi:ss')";        //ub06
        } else {
            return "'" + aTime + "'";
        }
    } // method getTimeFormat


    /**
     * trim trailing CRLF characters off the 'textfield' parameters
     * @param  inString The input string (String)
     * @return a String opbject without trailing CRLF (String)
     */
    public static String stripCRLF (String inString) {
        int indx = inString.lastIndexOf('\r');
        if (indx > -1) {
          inString = inString.substring(0, indx);
        }
        return inString;
    } // stripCRLF

}   // class TablesC
