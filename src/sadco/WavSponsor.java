package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WAV_SPONSOR table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 080222 - SIT Group
 * @version
 * 080222 - GenTableClassB class - create class<br>
 */
public class WavSponsor extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WAV_SPONSOR";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The dateTime field name */
    public static final String DATE_TIME = "DATE_TIME";
    /** The clientCode field name */
    public static final String CLIENT_CODE = "CLIENT_CODE";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private Timestamp dateTime;
    private int       clientCode;

    /** The error variables  */
    private int stationIdError  = ERROR_NORMAL;
    private int dateTimeError   = ERROR_NORMAL;
    private int clientCodeError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage  = "";
    private String dateTimeErrorMessage   = "";
    private String clientCodeErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final Timestamp DATE_TIME_MN = DATEMIN;
    public static final Timestamp DATE_TIME_MX = DATEMAX;
    public static final int       CLIENT_CODE_MN = INTMIN;
    public static final int       CLIENT_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception dateTimeException =
        new Exception ("'dateTime' is null");
    Exception dateTimeOutOfBoundsException =
        new Exception ("'dateTime' out of bounds: " +
            DATE_TIME_MN + " - " + DATE_TIME_MX);
    Exception clientCodeOutOfBoundsException =
        new Exception ("'clientCode' out of bounds: " +
            CLIENT_CODE_MN + " - " + CLIENT_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public WavSponsor() {
        clearVars();
        if (dbg) System.out.println ("<br>in WavSponsor constructor 1"); // debug
    } // WavSponsor constructor

    /**
     * Instantiate a WavSponsor object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public WavSponsor(
            String stationId) {
        this();
        setStationId  (stationId );
        if (dbg) System.out.println ("<br>in WavSponsor constructor 2"); // debug
    } // WavSponsor constructor

    /**
     * Instantiate a WavSponsor object and initialize the instance variables.
     * @param stationId   The stationId  (String)
     * @param dateTime    The dateTime   (java.util.Date)
     * @param clientCode  The clientCode (int)
     * @return A WavSponsor object
     */
    public WavSponsor(
            String         stationId,
            java.util.Date dateTime,
            int            clientCode) {
        this();
        setStationId  (stationId );
        setDateTime   (dateTime  );
        setClientCode (clientCode);
        if (dbg) System.out.println ("<br>in WavSponsor constructor 3"); // debug
    } // WavSponsor constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId  (CHARNULL);
        setDateTime   (DATENULL);
        setClientCode (INTNULL );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'stationId' class variable
     * @param  stationId (String)
     */
    public int setStationId(String stationId) {
        try {
            this.stationId = stationId;
            if (this.stationId != CHARNULL) {
                this.stationId = stripCRLF(this.stationId.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>stationId = " + this.stationId);
        } catch (Exception e) {
            setStationIdError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return stationIdError;
    } // method setStationId

    /**
     * Called when an exception has occured
     * @param  stationId (String)
     */
    private void setStationIdError (String stationId, Exception e, int error) {
        this.stationId = stationId;
        stationIdErrorMessage = e.toString();
        stationIdError = error;
    } // method setStationIdError


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


    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (int)
     */
    public int setClientCode(int clientCode) {
        try {
            if ( ((clientCode == INTNULL) || 
                  (clientCode == INTNULL2)) ||
                !((clientCode < CLIENT_CODE_MN) ||
                  (clientCode > CLIENT_CODE_MX)) ) {
                this.clientCode = clientCode;
                clientCodeError = ERROR_NORMAL;
            } else {
                throw clientCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (Integer)
     */
    public int setClientCode(Integer clientCode) {
        try {
            setClientCode(clientCode.intValue());
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (Float)
     */
    public int setClientCode(Float clientCode) {
        try {
            if (clientCode.floatValue() == FLOATNULL) {
                setClientCode(INTNULL);
            } else {
                setClientCode(clientCode.intValue());
            } // if (clientCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (String)
     */
    public int setClientCode(String clientCode) {
        try {
            setClientCode(new Integer(clientCode).intValue());
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Called when an exception has occured
     * @param  clientCode (String)
     */
    private void setClientCodeError (int clientCode, Exception e, int error) {
        this.clientCode = clientCode;
        clientCodeErrorMessage = e.toString();
        clientCodeError = error;
    } // method setClientCodeError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'stationId' class variable
     * @return stationId (String)
     */
    public String getStationId() {
        return stationId;
    } // method getStationId

    /**
     * Return the 'stationId' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStationId methods
     * @return stationId (String)
     */
    public String getStationId(String s) {
        return (stationId != CHARNULL ? stationId.replace('"','\'') : "");
    } // method getStationId


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
     * Return the 'clientCode' class variable
     * @return clientCode (int)
     */
    public int getClientCode() {
        return clientCode;
    } // method getClientCode

    /**
     * Return the 'clientCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getClientCode methods
     * @return clientCode (String)
     */
    public String getClientCode(String s) {
        return ((clientCode != INTNULL) ? new Integer(clientCode).toString() : "");
    } // method getClientCode


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
        if ((stationId == CHARNULL) &&
            (dateTime.equals(DATENULL)) &&
            (clientCode == INTNULL)) {
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
        int sumError = stationIdError +
            dateTimeError +
            clientCodeError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the stationId instance variable
     * @return errorcode (int)
     */
    public int getStationIdError() {
        return stationIdError;
    } // method getStationIdError

    /**
     * Gets the errorMessage for the stationId instance variable
     * @return errorMessage (String)
     */
    public String getStationIdErrorMessage() {
        return stationIdErrorMessage;
    } // method getStationIdErrorMessage


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


    /**
     * Gets the errorcode for the clientCode instance variable
     * @return errorcode (int)
     */
    public int getClientCodeError() {
        return clientCodeError;
    } // method getClientCodeError

    /**
     * Gets the errorMessage for the clientCode instance variable
     * @return errorMessage (String)
     */
    public String getClientCodeErrorMessage() {
        return clientCodeErrorMessage;
    } // method getClientCodeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of WavSponsor. e.g.<pre>
     * WavSponsor wavSponsor = new WavSponsor(<val1>);
     * WavSponsor wavSponsorArray[] = wavSponsor.get();</pre>
     * will get the WavSponsor record where stationId = <val1>.
     * @return Array of WavSponsor (WavSponsor[])
     */
    public WavSponsor[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * WavSponsor wavSponsorArray[] = 
     *     new WavSponsor().get(WavSponsor.STATION_ID+"=<val1>");</pre>
     * will get the WavSponsor record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of WavSponsor (WavSponsor[])
     */
    public WavSponsor[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * WavSponsor wavSponsorArray[] = 
     *     new WavSponsor().get("1=1",wavSponsor.STATION_ID);</pre>
     * will get the all the WavSponsor records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WavSponsor (WavSponsor[])
     */
    public WavSponsor[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = WavSponsor.STATION_ID,WavSponsor.DATE_TIME;
     * String where = WavSponsor.STATION_ID + "=<val1";
     * String order = WavSponsor.STATION_ID;
     * WavSponsor wavSponsorArray[] = 
     *     new WavSponsor().get(columns, where, order);</pre>
     * will get the stationId and dateTime colums of all WavSponsor records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WavSponsor (WavSponsor[])
     */
    public WavSponsor[] get (String fields, String where, String order) {
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
     * and transforms it into an array of WavSponsor.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private WavSponsor[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol  = db.getColNumber(STATION_ID);
        int dateTimeCol   = db.getColNumber(DATE_TIME);
        int clientCodeCol = db.getColNumber(CLIENT_CODE);
        WavSponsor[] cArray = new WavSponsor[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new WavSponsor();
            if (stationIdCol != -1)
                cArray[i].setStationId ((String) row.elementAt(stationIdCol));
            if (dateTimeCol != -1)
                cArray[i].setDateTime  ((String) row.elementAt(dateTimeCol));
            if (clientCodeCol != -1)
                cArray[i].setClientCode((String) row.elementAt(clientCodeCol));
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
     *     new WavSponsor(
     *         <val1>,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     stationId  = <val1>,
     *     dateTime   = <val2>,
     *     clientCode = <val3>.
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
     * Boolean success = new WavSponsor(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where dateTime = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WavSponsor wavSponsor = new WavSponsor();
     * success = wavSponsor.del(WavSponsor.STATION_ID+"=<val1>");</pre>
     * will delete all records where stationId = <val1>.
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
     * update are taken from the WavSponsor argument, .e.g.<pre>
     * Boolean success
     * WavSponsor updWavSponsor = new WavSponsor();
     * updWavSponsor.setDateTime(<val2>);
     * WavSponsor whereWavSponsor = new WavSponsor(<val1>);
     * success = whereWavSponsor.upd(updWavSponsor);</pre>
     * will update DateTime to <val2> for all records where 
     * stationId = <val1>.
     * @param wavSponsor  A WavSponsor variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(WavSponsor wavSponsor) {
        return db.update (TABLE, createColVals(wavSponsor), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WavSponsor updWavSponsor = new WavSponsor();
     * updWavSponsor.setDateTime(<val2>);
     * WavSponsor whereWavSponsor = new WavSponsor();
     * success = whereWavSponsor.upd(
     *     updWavSponsor, WavSponsor.STATION_ID+"=<val1>");</pre>
     * will update DateTime to <val2> for all records where 
     * stationId = <val1>.
     * @param  updWavSponsor  A WavSponsor variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(WavSponsor wavSponsor, String where) {
        return db.update (TABLE, createColVals(wavSponsor), where);
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
        if (getStationId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATION_ID + "='" + getStationId() + "'";
        } // if getStationId
        if (!getDateTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME +
                "=" + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getClientCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLIENT_CODE + "=" + getClientCode("");
        } // if getClientCode
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(WavSponsor aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (!aVar.getDateTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME +"=";
            colVals += (aVar.getDateTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTime()));
        } // if aVar.getDateTime
        if (aVar.getClientCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLIENT_CODE +"=";
            colVals += (aVar.getClientCode() == INTNULL2 ?
                "null" : aVar.getClientCode(""));
        } // if aVar.getClientCode
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (!getDateTime().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME;
        } // if getDateTime
        if (getClientCode() != INTNULL) {
            columns = columns + "," + CLIENT_CODE;
        } // if getClientCode
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (!getDateTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getClientCode() != INTNULL) {
            values  = values  + "," + getClientCode("");
        } // if getClientCode
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getStationId("")  + "|" +
            getDateTime("")   + "|" +
            getClientCode("") + "|";
    } // method toString

} // class WavSponsor
