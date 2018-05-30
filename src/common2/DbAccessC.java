//+------------------------------------------------+----------------+--------+
//| CLASS:........ DbAccessC                       | JAVA CLASS     | 990924 |
//+------------------------------------------------+----------------+--------+
//| PROGRAMMER: .. Ursula von St Ange                                        |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//| Generic db accessor class                                                |
//|                                                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 19991023  Ursula von St Ange   create program                            |
//| 19991207  Ursula von St Ange   ub01: get dates as Strings iso Timestamps |
//| 20000119  Ursula von St Ange   ub02: include NULL values, test for NULL  |
//|                                      in doSelect                         |
//| 20000314  Ursula von St Ange   ub03: change name, and move to common     |
//|                                      Put userid/password in variables    |
//| 20010716  Ursula von St Ange         set autocommit(false) & commit      |
//| 20010727  Ursula von St Ange         move request for connection to      |
//|                                      where db access is required         |
//| 20091221  Ursula von St Ange   ub04: include a lock function             |
//+--------------------------------------------------------------------------+

package common2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * A generic class that does the actual JDBC calls.  This is the only place
 * in the surveys package that the connection to the relevant database is
 * done.  The connection is only done the first time this class is
 * instantiated.  A class variable <b>isConnected</b> prevents subsequent
 * instantiations to connect to the database.  The connection is closed by
 * the static method close(), which should be called just before the
 * application ends.
 *
 * The desired driver is read in from the 'DbAccess.cfg' file, and contains
 * an integer denoting the driver.  The following are currently possible:<pre>
 * 1      Oracle oci driver - unix
 * 2      Oracle thin driver - pc/unix
 * 3      text driver (survey) - pc
 * 4      MSAccess driver (survey2) - pc
 * </pre>
 * If the file does not exist, the driver defaults to 2.
 *
 * @author 031202 - SIT Group
 * @version
 * 19991023  Ursula von St Ange   create program                            <br>
 * 19991207  Ursula von St Ange   ub01: get dates as Strings iso Timestamps <br>
 * 20000119  Ursula von St Ange   ub02: include NULL values, test for NULL
 *                                      in doSelect                         <br>
 * 20000314  Ursula von St Ange   ub03: change name, and move to common
 *                                      Put userid/password in variables    <br>
 * 20010716  Ursula von St Ange         set autocommit(false) & commit      <br>
 * 20010727  Ursula von St Ange         move request for connection to
 *                                      where db access is required         <br>
 * 20040127  Ursula von St Ange   ub04  change doSelect - get numerics as
 *                                      String from database.               <br>
 * 20100507 - Ursula von St Ange - ub06: change for postgres                <br>
 */
public class DbAccessC {

    boolean dbg = false;
    //boolean dbg = true;
    //boolean dbg2 = false;
    boolean dbg2 = true;
    String thisClass = this.getClass().getName();
    static common.edmCommonPC ec = new common.edmCommonPC();

    private static boolean exists = false;

    // table variables
    String[]     colNames;
    int[]        colTypes;
    int          colCount, rowCount;

    // database variables
    static boolean isConnected = false;
    static Connection conn;
    static int driver;
    static int driver2;
    String dbase = "";
    //String unixHost = "stel-apps.csir.co.za";                               //ub06
    String unixHost = "localhost";                               //ub06
    //String unixHost = "freddy";                               //ub06

    // for select
    static Statement stmt;
    static ResultSet rset;
    static ResultSetMetaData rsmd;

    // for delete, update, insert
    int   numRecords;
    String errorMessages = "none";
    String insertStr = "";
    String updateStr = "";
    String deleteStr = "";
    String selectStr = "";

    String cfgFileNameLocalUX = "/home/localuser/Desktop/SadcoLoading/common2/DbAccess.cfg";   //ub06
    //String cfgFileNameLocalUX = "/usr2/people/sadco/www/webConfig/DbAccess.cfg";    //ub06
    String cfgFileNameLocalPC = "/home/localuser/Desktop/SadcoLoading/common2/DbAccess.cfg";

    // database connection variables
    String user;
    String pwd;
    String cfgFileName;
    String callingRoutine = "";

    /** The null value for all int fields (-2147483648)*/             // ub02
    public static final int INTNULL = Integer.MIN_VALUE;              // ub02
    /** The null value for all character fields */                    // ub02
    public static final String  CHARNULL = null;                      // ub02
    /** The null value for all long fields*/                          // ub02
    public static final long    LONGNULL = Long.MIN_VALUE;            // ub02
    /** The null value for all float fields (Float.intBitsToFloat(0x1)) */ // ub02
    public static final float   FLOATNULL = Float.MIN_VALUE;          // ub02
    /** The null value for all double fields (Double.intBitsToFloat(0x1L)) */ // ub02
    public static final double  DOUBLENULL = Double.MIN_VALUE;        // ub02
    /** The null value for all Timestamp fields */                    // ub02
    public static final Timestamp  DATENULL =                         // ub02
        Timestamp.valueOf("1800-01-01 00:00:00");                     // ub02


    /**
    * Sets the user, password and default configuration file name, and gets
    * the driver to use for database connection.
    * @param user The userid of the schema
    * @param pwd  The password for the schema
    */
    public DbAccessC (String user, String pwd) {
        this(user, pwd, "");
    } // constructor


    /**
    * Sets the user, password and configuration file name, and gets the driver
    * to use for database connection.
    * @param user The userid of the schema
    * @param pwd  The password for the schema
    * @param cfgFileName  The configuration file to be used
    */
    public DbAccessC (String user, String pwd, String cfgFileName) {
        this.user = user;
        this.pwd = pwd;
        this.cfgFileName = cfgFileName;
        if (dbg) System.out.println("<br>" + thisClass + ".constructor2: " +
            "host = " + ec.getHost());
        if ("".equals(cfgFileName)) {
            if (ec.getHost().startsWith(unixHost)) {                        //ub06
                this.cfgFileName = cfgFileNameLocalUX;                  //ub06
            } else {                                                    //ub06
                this.cfgFileName = cfgFileNameLocalPC;                  //ub06
            } // if host                                                //ub06
            //this.cfgFileName = cfgFileNameLocalUX;
        } // if ("".equals(cfgFileName)) {
        //readDriver();
        if (dbg) System.out.println("<br>" + thisClass + ".constructor2: " +
             "user = " + user + ", isConnected = " + isConnected);
    } // constructor

    /**
    * Sets the driver according to the configuration file, and gets the database
    * name in the case of text and MSAccess.
    */
    private void readDriver() {

        if (dbg) System.out.println("<br>" + thisClass + ".readDriver");
        try {
            RandomAccessFile cfgFile = new RandomAccessFile(cfgFileName , "r");
            String line = cfgFile.readLine();
            cfgFile.close();
            cfgFile = null;
            StringTokenizer st = new StringTokenizer (line, "\t\n\r ");
            driver = new Integer (st.nextToken()).intValue();
            if (driver == 4) {
                dbase = st.nextToken();
            }
            if (dbg) System.out.println("<br>" + thisClass + ": driver: " +
                driver);
        } catch (IOException e) {
            driver = 6;
            if (dbg) System.out.println("<br>" + thisClass +
                ".readDriver: No configuration file found - default = " +
                "postgres set-oradb (driver = 6)");
        } // try-catch
    } // driver


    private void connect() {

        readDriver();
        // use driver2 for connection
        driver2 = driver;

        if (dbg) System.out.println("<br>" + thisClass + ".connect: " +
            "user = " + user +", pwd = " + pwd +
            ", cfgFileName = " + cfgFileName +
            ", isConnected = " + isConnected);

        // +---------------------------------------------------------+
        // | Load the relevant driver and connect to the database    |
        // +---------------------------------------------------------+
        try {
            switch (driver2) {
            case 1:                     // oracle oci driver - unix
                DriverManager.registerDriver
                    (new oracle.jdbc.driver.OracleDriver());
                conn = DriverManager.getConnection
                  ("jdbc:oracle:oci8:@localhost", user, pwd);
                conn.setAutoCommit(false);
                break;
            case 2:                     // oracle thin driver - pc/unix
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection
                    ("jdbc:oracle:thin:" + user + "/" + pwd +
                     "@localhost:5432:sadco");          // morph
                conn.setAutoCommit(false);
                break;
            case 3:                     // text driver = pc
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                conn = DriverManager.getConnection("jdbc:odbc:" + dbase,
                    "", "");
                break;
            case 4:                     // MSAccess driver = pc
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                conn = DriverManager.getConnection("jdbc:odbc:" + dbase,
                    "", "");
                break;
            case 5:                     // oracle thin driver - pc/unix
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection
                    ("jdbc:oracle:thin:" + user + "/" + pwd +
                     "@localhost:5432:sadco");          // steamer
                conn.setAutoCommit(false);
                break;
            case 6:                     // postgresql                   //ub06
                //String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true"
                //jdbc.postgresql:edm/ter91qes//146.64.123.216:5432/etek8
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/sadco", user,  pwd);
                conn.setAutoCommit(false);
                if (dbg) System.out.println (thisClass +
                    ".connect: connected2: driver=" + driver2 +
                    (driver2==5?" (steamer) ":" (stel-oradb) ") + user +
                    " " + isConnected + " " + callingRoutine);

                // correct for use in other classes
                driver = 2;
                break;
            } // switch

            isConnected = true;
            if (dbg2) System.out.println (thisClass +
//                ".connect: connected: driver=" + driver2 +
//                (driver2==5?" (steamer) ":" (stel-oradb) ") + user +
//                " " + isConnected + " " + callingRoutine);
                ".connect:dr=" + driver2 + ":" + user +
                ":" + isConnected + ":" + callingRoutine + ":" +
                ec.getDateTime());

        } catch (Exception e) {
            processError(e, "connect error", "");
        } // try ... catch

    } // connect


    /**
     * executes a lock table command (locks table in share mode)
     * @param table    The name of the table to be locked.
     */
    public void lock (String table) {                                   //ub04
        String lockStr = "";                                            //ub04
        try {                                                           //ub04
            lockStr = "lock table " + table + " in share mode";         //ub04
            stmt = conn.createStatement();                              //ub04
            stmt.execute(lockStr);                                      //ub04
            stmt.close();                                               //ub04
            stmt = null;                                                //ub04
        } catch (Exception e) {                                         //ub04
            processError(e, "lock", lockStr);                           //ub04
        } // try-catch                                                  //ub04
    } // lock                                                           //ub04


    /**
     * Does the commit to the database
     */
    public static void commit () {
        try {
            conn.commit();
        } catch (Exception e) {}
    } // commit


    /**
     * Closes the database connection - should be called last thing before
     * the application finishes.
     */
    public static void close () {
        try {
            // close all other stuff as well
            if (stmt != null) {
                stmt.close();
                stmt = null;
            } // if (stmt != null)
            if (rset != null) {
                rset.close();
                rset = null;
            } // if (rset != null)
            //if (rsmd != null) {
            //    rsmd.close();
            //    rsmd = null;
            //} // if (rsmd != null)
            if ((conn != null) && !conn.isClosed()) {
                commit();
                conn.close();
                conn = null;
                isConnected = false;
                //if (dbg) System.out.println ("<br>disconnected...");
            } // if
        } catch (Exception e) {}
    } // close


    /**
     * Returns the selected driver number.
     * @return driver The selected driver number (int)
     */
    public int getDriver() {
        return driver;
    } // getDriver


    /**
     * Returns the number of records returned by statement.executeUpdate(sql).
     * @return numRecords The number of records process (int)
     */
    public int getNumRecords() {
        return numRecords;
    } // getNumRecords


    /**
     * Returns the SQLException messages returned by e.getMessage()
     * @return warning The SQL error messages (String)
     */
    public String getMessages() {
        return errorMessages;
    } // getMessages


    /**
     * Returns the insert DDL string
     * @return insert DDL string (String)
     */
    public String getInsertStr() {
        return insertStr;
    } // getInsertStr


    /**
     * Returns the update DDL string
     * @return update DDL string (String)
     */
    public String getUpdateStr() {
        return updateStr;
    } // getUpdateStr


    /**
     * Returns the delete DDL string
     * @return delete DDL string (String)
     */
    public String getDeleteStr() {
        return deleteStr;
    } // getDeleteStr


    /**
     * Returns the select DDL string
     * @return select DDL string (String)
     */
    public String getSelectStr() {
        return selectStr;
    } // getSelectStr


    /**
     * Returns the connection.
     * @return connection The connection to the database (Connection)
     */
    public static Connection getConnection() {
        return conn;
    } // getConnection


    /**
     * Select all the fields and all the records from a table
     * @param table The table to do the select from
     * @return a Vector (rows) of Vectors (columns)
     */
    public Vector select (String table) {
        return select ("*", table, null, null);
    } // select

    /**
     * Select all the fields, but only selected records, from a table
     * @param table The table to do the select from
     * @param where The 'where' clause of a select statement.  (String)
     *     If all the records are required, specify "1=1".
     *     If where == "" / null, it is ignored.
     * @return a Vector (rows) of Vectors (columns)
     */
    public Vector select (String table, String where) {
        return select ("*", table, where, null);
    } // select

    /**
     * Select only certain fields, and only selected records, from a table
     * @param fields a comma-delimited string of the fields to be selected
     * @param table The table to do the select from
     * @param where The 'where' clause of a select statement (String)
     *     If all the records are required, specify "1=1".
     *     If where == "" / null, it is ignored.
     * @return a Vector (rows) of Vectors (columns)
     */
    public Vector select (String fields, String table, String where) {
        return select (fields, table, where, null);
    } // select

    /**
     * Select only certain fields, and only selected records, from a table,
     * and sort the records before returning.
     * @param fields a comma-delimited string of the fields to be selected
     * @param table The table to do the select from
     * @param where The 'where' clause of a select statement (String)
     *     If all the records are required, specify "1=1".
     *     If where == "" / null, it is ignored.
     * @param  order A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return a Vector (rows) of Vectors (columns)
     */
    public Vector select (String fields, String table, String where,
            String order) {

        // test for where and order == ""
        try {
            if (where.equals("")) { where = null; }
        } catch (NullPointerException e) {} // try catch where
        try {
            if (order.equals("")) { order = null; };
        } catch (NullPointerException e) {} // try catch order

        if (dbg) System.out.println ("<br>" + thisClass + ".select: " + table +
            " & " + where );
        if (where != null) table += " where " + where;
        if (dbg) System.out.println("<br>" + thisClass + ".select: table = " +
            table);
        return doSelect (fields, table, order);
    } // select

    /**
     * Does the work for the 3 <b>select</b> methods.
     * @param fields A comma-delimited string of the fields to be selected
     * @param tablewhere The combined table and where clause
     * @param order The 'order by' clause of a select statement
     *     If order == "" / null, it is ignored.
     * @return a Vector (rows) of Vectors (columns)
     */
    Vector doSelect (String fields, String tablewhere, String order) {

        // temporary variables                                       // ub02
        Integer tmpInt;                                              // ub02
        Float   tmpFloat;                                            // ub02
        Double  tmpDouble;                                           // ub02
        Long    tmpLong;
        Integer tmpNullInt = new Integer (INTNULL);                  // ub02
        Float   tmpNullFloat = new Float (FLOATNULL);                // ub02
        Double  tmpNullDouble = new Double (DOUBLENULL);             // ub02
        Long    tmpNullLong = new Long (LONGNULL);                   // ub02

        // create the vector
        Vector result = new Vector();

        // set up the query
        selectStr = "select " + fields + " from " + tablewhere;
        if (order != null) {
            selectStr = selectStr + " order by " + order;
        } // if

        if (dbg) System.out.println ("<br>" + thisClass +
            ".doSelect: selectStr : " + selectStr);  // debug

        //if (!isConnected) connect();
        try {
            callingRoutine = "doSelect";
            if ((conn == null) || conn.isClosed()) connect();
        } catch (Exception e) {
            processError(e, "doSelect error0", selectStr);
        } // try-catch

        try {
            //Statement
            stmt = conn.createStatement();
            if (dbg) System.out.println ("<br>" + thisClass +
                ".doSelect: 1 : " + 1);  // debug
        } catch (Exception e) {
            processError(e, "doSelect error1", selectStr);
        } // try-catch

        try {
            //ResultSet
            rset = stmt.executeQuery(selectStr);
            if (dbg) System.out.println ("<br>" + thisClass +
                ".doSelect: 2 : " + 2);  // debug
        } catch (Exception e) {
            processError(e, "doSelect error2", selectStr);
        } // try-catch

        try {
            // get the number of columns and their names, and their types
            //ResultSetMetaData
            rsmd = rset.getMetaData ();
        } catch (Exception e) {
            processError(e, "doSelect error3", selectStr);
        } // try-catch

        try {
            colCount = rsmd.getColumnCount();
        } catch (Exception e) {
            processError(e, "doSelect error4", selectStr);
        } // try-catch

        if (dbg) System.out.println ("<br>colCount = " + colCount);  // debug
        colNames = new String[colCount];
        colTypes = new int[colCount];

        try {
            for (int i=0; i<colCount; i++) {
                colNames[i] = rsmd.getColumnName(i+1);
                colTypes[i] = rsmd.getColumnType(i+1);
                if (dbg) System.out.println ("<br>" + thisClass + ".doSelect: " +
                    "colNames[] & colTypes[] = " + i + " " + colNames[i] +
                    " " + colTypes[i]); // debug
            } // for
        } catch (Exception e) {
            processError(e, "doSelect error5", selectStr);
        } // try-catch

        //System.out.println("before while (rset.next())");
        try {
            // get the records themselves
            int dbgcnt = 0;
            while (rset.next()) {
                if (dbg) dbgcnt++;
                if (dbg) System.out.println("<br>" + thisClass + ".doSelect: " +
                    "rset.next() " + dbgcnt);

                Vector row = new Vector();

                for (int i=0; i<colCount; i++) {
                    if (dbg) System.out.println ("<br>" + thisClass +
                        ".doSelect: i = " + i + " *" + rset.getString (i+1) + "*");
                    switch (colTypes[i]) {
                    case Types.LONGVARCHAR:     // long = -1
                        rset.getString (i+1);  // without this, the long values
                                               // sometimes bomb out
                        tmpLong = new Long (rset.getLong (i+1));         // ub02
                        if (rset.wasNull()) { tmpLong = tmpNullLong; }   // ub02
                        row.addElement (tmpLong);                        // ub02
                        break;
                    case Types.CHAR:            // char = 1
                    case Types.VARCHAR:         // varchar = 12
                    case Types.TIMESTAMP:       // timestamp (oracle date) = 93
                        row.addElement (rset.getString (i+1));
                        break;
                    case Types.NUMERIC:         // numeric = 2
                    case Types.DECIMAL:         // decimal = 3
                    case Types.FLOAT:           // float = 6
                    case Types.REAL:            // real = 7
                        //tmpFloat = new Float (rset.getFloat (i+1));      // ub02
                        //if (rset.wasNull()) { tmpFloat = tmpNullFloat; } // ub02
                        //row.addElement (tmpFloat);                       // ub02
                        if (dbg) System.out.println("DbAccessC: numeric = " +
                            rset.getString (i+1) + " " + colNames[i]);
                        row.addElement (rset.getString (i+1));             // ub04
                        break;
                    case Types.INTEGER:         // int = 4
                    case Types.SMALLINT:        // smallint = 5
                        tmpInt = new Integer (rset.getInt (i+1));        // ub02
                        if (rset.wasNull()) { tmpInt = tmpNullInt; }     // ub02
                        row.addElement (tmpInt);                         // ub02
                        break;
                    case Types.DOUBLE:          // double = 8
                        tmpDouble = new Double (rset.getDouble (i+1));   // ub02
                        if (rset.wasNull()) { tmpDouble = tmpNullDouble; }// ub02
                        row.addElement (tmpDouble);                      // ub02
                        break;
                    case Types.DATE:            // date = 91
                        row.addElement (rset.getDate (i+1));
                        break;
                    default:
                        if (dbg) System.out.println ("<br>" + thisClass + ".doSelect: " +
                            "default: i = " + i + " *" + rset.getString (i+1) +
                            "*");  // debug
                        row.addElement (rset.getString (i+1));
                        break;
                    } // switch
                } // for
                result.addElement (row);
            } // while rset.next()
        } catch (Exception e) {
            processError(e, "doSelect error6", selectStr);
        } // try-catch

        try {
            stmt.close();
            stmt = null;
            rset.close();
            rset = null;
            //rsmd.close();
            //rsmd = null;
        } catch (Exception e) {
            processError(e, "doSelect error7", selectStr);
        } // try-catch

        return result;

    } // doSelect


    /**
     * Insert the record into a table.
     * @param table   The table to do the insert to
     * @param columns An array of Strings with the column names
     * @param values  An array of Strings with the column values
     * @return success = true/false
     */
    public boolean insert (String table, String[] columns, String[] values) {
        String columnStr = columns[0];
        String valueStr = values[0];
        for (int i=1; i<values.length; i++) {
            columnStr = columnStr + "," + columns[i];
            valueStr = valueStr + "," + values[i];
        } // for
        if (dbg) System.out.println ("<br>" + thisClass + ".insert: " +
            columnStr + " " + valueStr);
        return doInsert (table, columnStr, valueStr);
    } // insert

    /**
     * Insert the record into a table.
     * @param table   The table to do the insert to
     * @param columns A comma-delimited string of the fields to be inserted.
     * @param values  A comma-delimited string of the values to be selected
     * @return success = true/false
     */
    public boolean insert (String table, String columns, String values) {
        if (dbg) System.out.println ("<br>" + thisClass + ".insert: " + columns +
            " " + values);
        return doInsert (table, columns, values);
    } // insert

    /**
     * Does the work for the 2 <b>insert</b> methods.
     * @param table   The table to do the insert to
     * @param columns A comma-delimited string of the fields to be inserted.
     * @param values  A comma-delimited string of the values to be selected
     * @return success = true/false
     */
    public boolean doInsert (String table, String columns, String values) {
        insertStr = "";
        try {
            //if (!isConnected) connect();
            callingRoutine = "doInsert";
            if ((conn == null) || conn.isClosed()) connect();
            insertStr = "insert into " + table + " ( " + columns +
                " ) values ( " + values + " )";
            stmt = conn.createStatement();
            if (dbg) System.out.println ("<br>" + thisClass + ".doInsert: " +
                insertStr);
            // get the number of records
            numRecords = stmt.executeUpdate(insertStr);
            if (dbg) System.out.println ("<br>" + thisClass +
                ".doInsert: numRecords : " + numRecords);
            // release resources
            stmt.close();
            stmt = null;
        } catch (SQLException e) {
            processError(e, "insert", insertStr);
            return false;
        } // try

        return true;
    } // doInsert


    /**
     * Delete the record(s) from the table.
     * @param table   The table to do the delete from
     * @param where The 'where' clause of a delete statement
     * @return success = true/false
     */
    public boolean delete (String table, String where) {
        deleteStr = "";
        try {
            //if (!isConnected) connect();
            callingRoutine = "delete";
            if ((conn == null) || conn.isClosed()) connect();
            deleteStr = "delete from " + table + " where " + where;
            stmt = conn.createStatement();
            numRecords = stmt.executeUpdate (deleteStr);
            if (dbg) System.out.println ("<br>" + thisClass +
                ".delete: numRecords : " + numRecords);
            if (dbg) System.out.println ("<br>" + thisClass +
                ".delete: deleteStr : " + deleteStr);
            // release resources
            stmt.close();
            stmt = null;
        } catch (SQLException e) {
            processError(e, "delete", deleteStr);
            return false;
        } // try

        return true;
    } // delete


    /**
     * Update the record(s) from the table.
     * @param table The table to do the delete from
     * @param colVals A comma-delimited string of field=values pairs
     * @param where The 'where' clause of a delete statement
     * @return success = true/false
     */
    public boolean update (String table, String colVals, String where) {
        updateStr = "";
        try {
            //if (!isConnected) connect();
            callingRoutine = "update";
            if ((conn == null) || conn.isClosed()) connect();
            updateStr = "update " + table + " set " + colVals;
            stmt = conn.createStatement();
            if (where != null) {
                updateStr = updateStr + " where " + where;
            } // if
            if (dbg) System.out.println ("<br>" + thisClass + ".update: " +
                updateStr);  // debug
            numRecords = stmt.executeUpdate (updateStr);
            if (dbg) System.out.println ("<br>" + thisClass +
                ".update: numRecords : " + numRecords);
            // release resources
            stmt.close();
            stmt = null;
        } catch (Exception e) {
            processError(e, "update", updateStr);
            return false;
        } // try

        return true;
    } // update


    /**
     * Get the column number in the select statement for a particular field
     * @param   name The field name
     * @return The column number.  Returns -1 if the column was not found
     */
    public int getColNumber (String name) {
        for (int i=0; i<colNames.length; i++) {
            if (dbg) System.out.println ("<br>" + thisClass +
                ".getColNumber: " + colNames[i] + " " + name);  // debug
            if (colNames[i].equalsIgnoreCase(name)) return i;
        } // for
        return -1;
    } // getColNumber


    /**
     * Get the column typefor a particular field
     * @param   name The field name
     * @return The column type.  Returns -9999 if the column was not found
     */
    public int getColType (String name) {
        for (int i=0; i<colNames.length; i++) {
            //System.out.println ("<br>" + colNames[i] + " " + name);  // debug
            if (colNames[i].equalsIgnoreCase(name)) return colTypes[i];
        } // for
        return -9999;
    } // getColType


    /**
     * Create a new table using another table as base.  The records in the
     * table can be restricted with a where clause. e.g.
     *<pre> create table <newTable> as select * from <table>
     *  where <JOB_CODE = 1></pre>
     * @param table    The base table name
     * @param newTable The new table name
     * @param where    The where clause (without 'where').
     * @return success = true/false.
     */
    public boolean create (String table, String newTable, String where) {
        String createStr = "";
        try {
            //if (!isConnected) connect();
            callingRoutine = "create";
            if ((conn == null) || conn.isClosed()) connect();
            createStr = "create table " + newTable +
                " as select * from " + table;
            stmt = conn.createStatement();
            if (where != null) {
                createStr = createStr + " where " + where;
            }
            if (dbg) System.out.println ("<br>" + thisClass + ".create: " +
                createStr);
            numRecords = stmt.executeUpdate (createStr);
            // release resources
            stmt.close();
            stmt = null;
        } catch (Exception e) {
            processError(e, "create", createStr);
            return false;
        } // try

        return true;
    } // create


    /**
     * Drop a table.
     * @param table    The name of the table to be dropped.
     * @return success = true/false.
     */
    public boolean drop (String table) {
        String dropStr = "";
        try {
            //if (!isConnected) connect();
            callingRoutine = "drop";
            if ((conn == null) || conn.isClosed()) connect();
            dropStr = "drop table " + table;
            stmt = conn.createStatement();
            if (dbg) System.out.println ("<br>" + thisClass + ".drop: " +
                dropStr);
            numRecords = stmt.executeUpdate (dropStr);
            // release resources
            stmt.close();
            stmt = null;
        } catch (Exception e) {
            processError(e, "drop", dropStr);
            return false;
        } // try

        return true;
    } // drop


    private void processError(Exception e, String text1, String text2) {
        errorMessages = e.getMessage();
        if (dbg) System.out.println("<br>" + thisClass + "." + text1 + ": " +
            errorMessages + " " + text2);
        e.printStackTrace();
        e.printStackTrace(System.out);
    } // processError


} // class DbAccessC
