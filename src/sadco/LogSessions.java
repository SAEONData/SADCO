package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the LOG_SESSIONS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class LogSessions extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "LOG_SESSIONS";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The userid field name */
    public static final String USERID = "USERID";
    /** The dateTime field name */
    public static final String DATE_TIME = "DATE_TIME";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    userid;
    private Timestamp dateTime;

    /** The error variables  */
    private int codeError     = ERROR_NORMAL;
    private int useridError   = ERROR_NORMAL;
    private int dateTimeError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage     = "";
    private String useridErrorMessage   = "";
    private String dateTimeErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final Timestamp DATE_TIME_MN = DATEMIN;
    public static final Timestamp DATE_TIME_MX = DATEMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception dateTimeException =
        new Exception ("'dateTime' is null");
    Exception dateTimeOutOfBoundsException =
        new Exception ("'dateTime' out of bounds: " +
            DATE_TIME_MN + " - " + DATE_TIME_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public LogSessions() {
        clearVars();
        if (dbg) System.out.println ("<br>in LogSessions constructor 1"); // debug
    } // LogSessions constructor

    /**
     * Instantiate a LogSessions object and initialize the instance variables.
     * @param code  The code (int)
     */
    public LogSessions(
            int code) {
        this();
        setCode     (code    );
        if (dbg) System.out.println ("<br>in LogSessions constructor 2"); // debug
    } // LogSessions constructor

    /**
     * Instantiate a LogSessions object and initialize the instance variables.
     * @param code      The code     (int)
     * @param userid    The userid   (String)
     * @param dateTime  The dateTime (java.util.Date)
     */
    public LogSessions(
            int            code,
            String         userid,
            java.util.Date dateTime) {
        this();
        setCode     (code    );
        setUserid   (userid  );
        setDateTime (dateTime);
        if (dbg) System.out.println ("<br>in LogSessions constructor 3"); // debug
    } // LogSessions constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode     (INTNULL );
        setUserid   (CHARNULL);
        setDateTime (DATENULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'code' class variable
     * @param  code (int)
     */
    public int setCode(int code) {
        try {
            if ( ((code == INTNULL) ||
                  (code == INTNULL2)) ||
                !((code < CODE_MN) ||
                  (code > CODE_MX)) ) {
                this.code = code;
                codeError = ERROR_NORMAL;
            } else {
                throw codeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (Integer)
     */
    public int setCode(Integer code) {
        try {
            setCode(code.intValue());
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (Float)
     */
    public int setCode(Float code) {
        try {
            if (code.floatValue() == FLOATNULL) {
                setCode(INTNULL);
            } else {
                setCode(code.intValue());
            } // if (code.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (String)
     */
    public int setCode(String code) {
        try {
            setCode(new Integer(code).intValue());
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Called when an exception has occured
     * @param  code (String)
     */
    private void setCodeError (int code, Exception e, int error) {
        this.code = code;
        codeErrorMessage = e.toString();
        codeError = error;
    } // method setCodeError


    /**
     * Set the 'userid' class variable
     * @param  userid (String)
     */
    public int setUserid(String userid) {
        try {
            this.userid = userid;
            if (this.userid != CHARNULL) {
                this.userid = stripCRLF(this.userid.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>userid = " + this.userid);
        } catch (Exception e) {
            setUseridError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return useridError;
    } // method setUserid

    /**
     * Called when an exception has occured
     * @param  userid (String)
     */
    private void setUseridError (String userid, Exception e, int error) {
        this.userid = userid;
        useridErrorMessage = e.toString();
        useridError = error;
    } // method setUseridError


    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (Timestamp)
     */
    public int setDateTime(Timestamp dateTime) {
        try {
            if (dateTime == null) { this.dateTime = DATENULL; }
            if ( (dateTime.equals(DATENULL) ||
                  dateTime.equals(DATENULL2)) ||
                !(dateTime.before(DATE_TIME_MN) ||
                  dateTime.after(DATE_TIME_MX)) ) {
                this.dateTime = dateTime;
                dateTimeError = ERROR_NORMAL;
            } else {
                throw dateTimeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (java.util.Date)
     */
    public int setDateTime(java.util.Date dateTime) {
        try {
            setDateTime(new Timestamp(dateTime.getTime()));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (String)
     */
    public int setDateTime(String dateTime) {
        try {
            int len = dateTime.length();
            switch (len) {
            // date &/or times
                case 4: dateTime += "-01";
                case 7: dateTime += "-01";
                case 10: dateTime += " 00";
                case 13: dateTime += ":00";
                case 16: dateTime += ":00"; break;
                // times only
                case 5: dateTime = "1800-01-01 " + dateTime + ":00"; break;
                case 8: dateTime = "1800-01-01 " + dateTime; break;
            } // switch
            if (dbg) System.out.println ("dateTime = " + dateTime);
            setDateTime(Timestamp.valueOf(dateTime));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Called when an exception has occured
     * @param  dateTime (String)
     */
    private void setDateTimeError (Timestamp dateTime, Exception e, int error) {
        this.dateTime = dateTime;
        dateTimeErrorMessage = e.toString();
        dateTimeError = error;
    } // method setDateTimeError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'code' class variable
     * @return code (int)
     */
    public int getCode() {
        return code;
    } // method getCode

    /**
     * Return the 'code' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCode methods
     * @return code (String)
     */
    public String getCode(String s) {
        return ((code != INTNULL) ? new Integer(code).toString() : "");
    } // method getCode


    /**
     * Return the 'userid' class variable
     * @return userid (String)
     */
    public String getUserid() {
        return userid;
    } // method getUserid

    /**
     * Return the 'userid' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getUserid methods
     * @return userid (String)
     */
    public String getUserid(String s) {
        return (userid != CHARNULL ? userid.replace('"','\'') : "");
    } // method getUserid


    /**
     * Return the 'dateTime' class variable
     * @return dateTime (Timestamp)
     */
    public Timestamp getDateTime() {
        return dateTime;
    } // method getDateTime

    /**
     * Return the 'dateTime' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateTime methods
     * @return dateTime (String)
     */
    public String getDateTime(String s) {
        if (dateTime.equals(DATENULL)) {
            return ("");
        } else {
            String dateTimeStr = dateTime.toString();
            return dateTimeStr.substring(0,dateTimeStr.indexOf('.'));
        } // if
    } // method getDateTime


    /**
     * Gets the maximum value for the code from the table
     * @return maximum code value (int)
     */
    public int getMaxCode() {
        Vector result = db.select ("max(" + CODE + ")",TABLE,"1=1");
        Vector row = (Vector) result.elementAt(0);
        return (new Integer((String) row.elementAt(0)).intValue());
    } // method getMaxCode


    /**
     * Gets the number of records in the table
     * @return number of records (int)
     */
    public int getRecCnt () {
        return (getRecCnt("1=1"));
    } // method getRecCnt


    /**
     * Gets the number of records in the table
     * @param  where  The where clause (String)
     * @return number of records (int)
     */
    public int getRecCnt (String where) {
        Vector result = db.select ("count(*)",TABLE, where);
        Vector row = (Vector) result.elementAt(0);
        return (new Integer((String) row.elementAt(0)).intValue());
    } // method getRecCnt


    //=========================//
    // The isNullRecord method //
    //=========================//

    /**
     * Checks whether all the instance variables are NULL
     * @return true/false (boolean)
     */
    public boolean isNullRecord () {
        if ((code == INTNULL) &&
            (userid == CHARNULL) &&
            (dateTime == DATENULL)) {
            return true;
        } else {
            return false;
        } // if ...
    } // method isNullRecord


    //===================//
    // The error methods //
    //===================//

    /**
     * Checks whether all the instance variables were valid
     * @return true/false (boolean)
     */
    public boolean isValidRecord () {
        int sumError = codeError +
            useridError +
            dateTimeError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the code instance variable
     * @return errorcode (int)
     */
    public int getCodeError() {
        return codeError;
    } // method getCodeError

    /**
     * Gets the errorMessage for the code instance variable
     * @return errorMessage (String)
     */
    public String getCodeErrorMessage() {
        return codeErrorMessage;
    } // method getCodeErrorMessage


    /**
     * Gets the errorcode for the userid instance variable
     * @return errorcode (int)
     */
    public int getUseridError() {
        return useridError;
    } // method getUseridError

    /**
     * Gets the errorMessage for the userid instance variable
     * @return errorMessage (String)
     */
    public String getUseridErrorMessage() {
        return useridErrorMessage;
    } // method getUseridErrorMessage


    /**
     * Gets the errorcode for the dateTime instance variable
     * @return errorcode (int)
     */
    public int getDateTimeError() {
        return dateTimeError;
    } // method getDateTimeError

    /**
     * Gets the errorMessage for the dateTime instance variable
     * @return errorMessage (String)
     */
    public String getDateTimeErrorMessage() {
        return dateTimeErrorMessage;
    } // method getDateTimeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of LogSessions. e.g.<pre>
     * LogSessions logSessions = new LogSessions(<val1>);
     * LogSessions logSessionsArray[] = logSessions.get();</pre>
     * will get the LogSessions record where code = <val1>.
     * @return Array of LogSessions (LogSessions[])
     */
    public LogSessions[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * LogSessions logSessionsArray[] =
     *     new LogSessions().get(LogSessions.CODE+"=<val1>");</pre>
     * will get the LogSessions record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of LogSessions (LogSessions[])
     */
    public LogSessions[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * LogSessions logSessionsArray[] =
     *     new LogSessions().get("1=1",logSessions.CODE);</pre>
     * will get the all the LogSessions records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of LogSessions (LogSessions[])
     */
    public LogSessions[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = LogSessions.CODE,LogSessions.USERID;
     * String where = LogSessions.CODE + "=<val1";
     * String order = LogSessions.CODE;
     * LogSessions logSessionsArray[] =
     *     new LogSessions().get(columns, where, order);</pre>
     * will get the code and userid colums of all LogSessions records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of LogSessions (LogSessions[])
     */
    public LogSessions[] get (String fields, String where, String order) {
        // create the column list from the instance variables if neccessary
        try {
            if (fields.equals("")) { fields = null; }
        } catch (NullPointerException e) {}
        if (fields == null) {
            fields = createColumns();
        } // if (fields != null)
        return doGet(db.select(fields, TABLE, where, order));
    } // method get

    /**
     * Receives the result of the select statement from DbAccess,
     * and transforms it into an array of LogSessions.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private LogSessions[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol     = db.getColNumber(CODE);
        int useridCol   = db.getColNumber(USERID);
        int dateTimeCol = db.getColNumber(DATE_TIME);
        LogSessions[] cArray = new LogSessions[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new LogSessions();
            if (codeCol != -1)
                cArray[i].setCode    ((String) row.elementAt(codeCol));
            if (useridCol != -1)
                cArray[i].setUserid  ((String) row.elementAt(useridCol));
            if (dateTimeCol != -1)
                cArray[i].setDateTime((String) row.elementAt(dateTimeCol));
        } // for i
        return cArray;
    } // method doGet


    //=====================//
    // All the put methods //
    //=====================//

    /**
     * Insert a record into the table.  The values are taken from the current
     * value of the instance variables. .e.g.<pre>
     * String    CHARNULL  = Tables.CHARNULL;
     * Timestamp DATENULL  = Tables.DATENULL;
     * int       INTNULL   = Tables.INTNULL;
     * float     FLOATNULL = Tables.FLOATNULL;
     * Boolean success =
     *     new LogSessions(
     *         INTNULL,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     userid   = <val2>,
     *     dateTime = <val3>.
     * @return success = true/false (boolean)
     */
    public boolean put() {
        return db.insert (TABLE, createColumns(), createValues());
    } // method put


    //=====================//
    // All the del methods //
    //=====================//

    /**
     * Delete record(s) from the table, The where clause is created from the
     * current values of the instance variables. .e.g.<pre>
     * Boolean success = new LogSessions(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.DATENULL).del();</pre>
     * will delete all records where userid = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * LogSessions logSessions = new LogSessions();
     * success = logSessions.del(LogSessions.CODE+"=<val1>");</pre>
     * will delete all records where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean del(String where) {
        return db.delete (TABLE, where);
    } // method del


    //=====================//
    // All the upd methods //
    //=====================//

    /**
     * Update record(s) from the table, The fields and values to use for the
     * update are taken from the LogSessions argument, .e.g.<pre>
     * Boolean success
     * LogSessions updLogSessions = new LogSessions();
     * updLogSessions.setUserid(<val2>);
     * LogSessions whereLogSessions = new LogSessions(<val1>);
     * success = whereLogSessions.upd(updLogSessions);</pre>
     * will update Userid to <val2> for all records where
     * code = <val1>.
     * @param  logSessions  A LogSessions variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(LogSessions logSessions) {
        return db.update (TABLE, createColVals(logSessions), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * LogSessions updLogSessions = new LogSessions();
     * updLogSessions.setUserid(<val2>);
     * LogSessions whereLogSessions = new LogSessions();
     * success = whereLogSessions.upd(
     *     updLogSessions, LogSessions.CODE+"=<val1>");</pre>
     * will update Userid to <val2> for all records where
     * code = <val1>.
     * @param  logSessions  A LogSessions variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(LogSessions logSessions, String where) {
        return db.update (TABLE, createColVals(logSessions), where);
    } // method upd


    //======================//
    // Other helper methods //
    //======================//

    /**
     * Creates the where clause from the current values of the
     * instance variables.
     * @return  where clause (String)
     */
    private String createWhere() {
        String where = "";
        if (getCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CODE + "=" + getCode("");
        } // if getCode
        if (getUserid() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + USERID + "='" + getUserid() + "'";
        } // if getUserid
        if (!getDateTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME +
                "=" + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(LogSessions aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getUserid() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += USERID +"=";
            colVals += (aVar.getUserid().equals(CHARNULL2) ?
                "null" : "'" + aVar.getUserid() + "'");
        } // if aVar.getUserid
        if (!aVar.getDateTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME +"=";
            colVals += (aVar.getDateTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTime()));
        } // if aVar.getDateTime
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getUserid() != CHARNULL) {
            columns = columns + "," + USERID;
        } // if getUserid
        if (!getDateTime().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME;
        } // if getDateTime
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        setCode(getMaxCode()+1);
        String values  = getCode("");
        if (getUserid() != CHARNULL) {
            values  = values  + ",'" + getUserid() + "'";
        } // if getUserid
        if (!getDateTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")     + "|" +
            getUserid("")   + "|" +
            getDateTime("") + "|";
    } // method toString

} // class LogSessions
