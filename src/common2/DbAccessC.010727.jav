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
//| 20010716  Ursula von St Ange         set autocommit(false) & commit     |
//+--------------------------------------------------------------------------+

package common2;

import java.io.*;
import java.sql.*;
import java.util.*;

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
 */
public class DbAccessC {

    boolean dbg = false;
    //boolean dbg = true;
    String thisClass = this.getClass().getName();

    // table variables
    String[]     colNames;
    //String[][]   colValues;
    int[]        colTypes;
    int          colCount, rowCount;

    // database variables
    static boolean isConnected = false;
    static Connection conn;
    //static Statement  stmt;
    //ResultSet  rset;
    static int driver;
    //int driver;
    static String host;

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
    * Connects to the database upon first instantiation
    * @param user The userid of the schema
    * @param pwd  The password for the schema
    */
    public DbAccessC (String user, String pwd) {
        this(user, pwd,
            "/oracle/oraweb/product/734/ows/4.0/java/common2/DbAccess.cfg");
    } // constructor


    /**
    * Connects to the database upon first instantiation
    * @param user The userid of the schema
    * @param pwd  The password for the schema
    * @param cfgFileName  The configuration file to be used
    */
    public DbAccessC (String user, String pwd, String cfgFileName) {

        if (dbg) System.out.println("<br>" + thisClass + ": user = " + user +
            ", pwd = " + pwd + ", cfgFileName = " + cfgFileName);

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the database    |
        // +------------------------------------------------------------+
        if (!isConnected) {

            // get the desired driver from the config file
            //String            cfgFileName = "surveys/DbAccess.cfg";
            String            line;
            RandomAccessFile  cfgFile;
            String            dbase = "";

            try {
                cfgFile = new RandomAccessFile(cfgFileName , "r");
                line = cfgFile.readLine();
                cfgFile.close();
                StringTokenizer st = new StringTokenizer (line, "\t\n\r ");
                driver = new Integer (st.nextToken()).intValue();
                if (driver == 4) {
                    dbase = st.nextToken();
                }
                if (dbg) System.out.println("<br>" + thisClass + ": driver: " +
                    driver);
            } catch (IOException e) {
                driver = 2;
                if (dbg) System.out.println("<br>" + thisClass +
                    ": No file found - default = oracle oci8");
            }

            try {
                switch (driver) {
                //case 1:                     // oracle oci driver - unix
                //    DriverManager.registerDriver
                //        (new oracle.jdbc.driver.OracleDriver());
                //    conn = DriverManager.getConnection
                //      ("jdbc:oracle:oci8:@etek8.csir.co.za", user, pwd);
                //    break;
                case 2:                     // oracle thin driver - pc/unix
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn = DriverManager.getConnection
                        ("jdbc:oracle:thin:" + user + "/" + pwd +
                         "@morph.csir.co.za:1522:etek8");
                    conn.setAutoCommit(false);
                    break;
                //    Class.forName("oracle.jdbc.driver.OracleDriver");
                //    conn = DriverManager.getConnection
                //        ("jdbc:oracle:thin:" + user + "/" + pwd +
                //         "@morph.csir.co.za:1521:etek");
                //    break;
                case 3:                     // text driver = pc
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    conn = DriverManager.getConnection("jdbc:odbc:survey", "", "");
                    break;
                case 4:                     // MSAccess driver = pc
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    conn = DriverManager.getConnection("jdbc:odbc:" + dbase,
                        "", "");
                    break;
                case 1:                     // oracle thin driver - pc/unix
                default:                    // oracle thin driver - pc/unix
                    DriverManager.registerDriver
                        (new oracle.jdbc.driver.OracleDriver());
                    conn = DriverManager.getConnection
                      ("jdbc:oracle:oci8:@etek8.csir.co.za", user, pwd);
                    conn.setAutoCommit(false);
                //    Class.forName("oracle.jdbc.driver.OracleDriver");
                //    conn = DriverManager.getConnection
                //        ("jdbc:oracle:thin:" + user + "/" + pwd +
                //         "@morph.csir.co.za:1522:etek8.csir.co.za");
                    break;
                } // switch
                if (dbg) System.out.println ("<br>" + thisClass +
                    ": connected ... " + driver);

                isConnected = true;
                //stmt = conn.createStatement ();

            } catch (Exception e) {
                System.out.println("<br>" + thisClass + ":constructor error: " +
                    e.getMessage());
                e.printStackTrace();
            } // try ... catch

        } // if

    } // constructor


    /**
     * Closes the database connection - should be called last thing before
     * the application finishes.
     */
    public static void close () {
        if (isConnected) {
            try {
                conn.commit();
                conn.close();
                isConnected = false;
            } catch (Exception e) {
                System.out.println("<br>DbAccesC.close error: " +
                    e.getMessage());
            } // try catch
            //if (dbg) System.out.println ("<br>disconnected...");
        } // if
    } // close


    /**
     * Returns the selected driver number.
     * @return driver The selected driver number (int)
     */
    //public static int getDriver() {
    public int getDriver() {
        return driver;
    } // getDriver


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
        //return doSelect ("*", table, null);
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
        String selectStr = "select " + fields + " from " + tablewhere;
        if (order != null) {
            selectStr = selectStr + " order by " + order;
        } // if
        if (dbg) System.out.println ("<br>" + thisClass +
            ".doSelect: selectStr : " + selectStr);  // debug

        try {
            Statement stmt = conn.createStatement();
            if (dbg) System.out.println ("<br>" + thisClass +
                ".doSelect: 1 : " + 1);  // debug
            ResultSet rset = stmt.executeQuery(selectStr);
            if (dbg) System.out.println ("<br>" + thisClass +
                ".doSelect: 2 : " + 2);  // debug

            // get the number of columns and their names, and their types
            ResultSetMetaData rsmd = rset.getMetaData ();
            colCount = rsmd.getColumnCount();
            //System.out.println ("<br>colCount = " + colCount);  // debug
            colNames = new String[colCount];
            colTypes = new int[colCount];
            for (int i=0; i<colCount; i++) {
                colNames[i] = rsmd.getColumnName(i+1);
                colTypes[i] = rsmd.getColumnType(i+1);
                if (dbg)  System.out.println ("<br>" + thisClass + ".doSelect: " +
                    "colNames[] & colTypes[] = " + i + " " + colNames[i] +
                    " " + colTypes[i]); // debug
            } // for

            // get the records themselves
            while (rset.next()) {
                Vector row = new Vector();

                for (int i=0; i<colCount; i++) {
                    //System.out.println ("<br>i = " + i + " *" + rset.getString (i+1) + "*");  // debug
                    switch (colTypes[i]) {
                    case Types.LONGVARCHAR:     // long = -1
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
                        tmpFloat = new Float (rset.getFloat (i+1));      // ub02
                        if (rset.wasNull()) { tmpFloat = tmpNullFloat; } // ub02
                        row.addElement (tmpFloat);                       // ub02
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

            stmt.close();
            rset.close();

        } catch (Exception e) {
            System.out.println("<br>" + thisClass + ":doSelect error1: " +
                e.getMessage());
            e.printStackTrace();
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
        String insStr = "";
        try {
            Statement stmt = conn.createStatement();
            insStr = "insert into " + table + " ( " + columns +
                " ) values ( " + values + " )";
            if (dbg) System.out.println ("<br>" + thisClass + ".doInsert: " +
                insStr);
            int ii = stmt.executeUpdate (insStr);
            // release resources
            stmt.close();
        } catch (Exception e) {
            System.out.println ("<br>" + thisClass + ".insert: " +
                e.getMessage() + " " + insStr);
            return false;
        } // try

        return true;
    } // insert


    /**
     * Delete the record(s) from the table.
     * @param table   The table to do the delete from
     * @param where The 'where' clause of a delete statement
     * @return success = true/false
     */
    public boolean delete (String table, String where) {
        String delStr = "";
        try {
            Statement stmt = conn.createStatement();
            delStr = "delete from " + table + " where " + where;
            int ii = stmt.executeUpdate (delStr);
            if (dbg) System.out.println ("<br>" + thisClass +
                ".delete: ii : " + ii);
            if (dbg) System.out.println ("<br>" + thisClass +
                ".delete: delStr : " + delStr);
            // release resources
            stmt.close();
        } catch (Exception e) {
            System.out.println ("<br>" + thisClass + ".delete: " +
                e.getMessage() + " " + delStr);
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
        String updStr = "";
        try {
            Statement stmt = conn.createStatement();
            updStr = "update " + table + " set " + colVals;
            if (where != null) {
                updStr = updStr + " where " + where;
            } // if
            if (dbg) System.out.println ("<br>" + thisClass + ".update: " +
                updStr);  // debug
            int ii = stmt.executeUpdate (updStr);
            // release resources
            stmt.close();
        } catch (Exception e) {
            System.out.println ("<br>" + thisClass + ".update: " + e.getMessage() +
                " " + updStr);
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
            //System.out.println ("<br>" + colNames[i] + " " + name);  // debug
            if (colNames[i].equals(name.toUpperCase())) return i;
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
            if (colNames[i].equals(name.toUpperCase())) return colTypes[i];
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
            Statement stmt = conn.createStatement();
            createStr = "create table " + newTable +
                " as select * from " + table;
            if (where != null) {
                createStr = createStr + " where " + where;
            }
            if (dbg) System.out.println ("<br>" + thisClass + ".create: " +
                createStr);
            int ii = stmt.executeUpdate (createStr);
            // release resources
            stmt.close();
        } catch (Exception e) {
            System.out.println ("<br>" + thisClass + ".create: " + e.getMessage() +
                " " + createStr);
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
            Statement stmt = conn.createStatement();
            dropStr = "drop table " + table;
            if (dbg) System.out.println ("<br>" + thisClass + ".drop: " +
                dropStr);
            int ii = stmt.executeUpdate (dropStr);
            // release resources
            stmt.close();
        } catch (Exception e) {
            System.out.println ("<br>" + thisClass + ".drop: " +
                e.getMessage() + " " + dropStr);
            return false;
        } // try

        return true;
    } // drop


} // class DbAccessC
