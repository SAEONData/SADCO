package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the ZONES_COVERED table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnZonesCovered extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "ZONES_COVERED";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The zoneCode field name */
    public static final String ZONE_CODE = "ZONE_CODE";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    surveyId;
    private int       zoneCode;

    /** The error variables  */
    private int surveyIdError = ERROR_NORMAL;
    private int zoneCodeError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String surveyIdErrorMessage = "";
    private String zoneCodeErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final int       ZONE_CODE_MN = INTMIN;
    public static final int       ZONE_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception zoneCodeOutOfBoundsException =
        new Exception ("'zoneCode' out of bounds: " +
            ZONE_CODE_MN + " - " + ZONE_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnZonesCovered() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnZonesCovered constructor 1"); // debug
    } // MrnZonesCovered constructor

    /**
     * Instantiate a MrnZonesCovered object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     */
    public MrnZonesCovered(
            String surveyId) {
        this();
        setSurveyId (surveyId);
        if (dbg) System.out.println ("<br>in MrnZonesCovered constructor 2"); // debug
    } // MrnZonesCovered constructor

    /**
     * Instantiate a MrnZonesCovered object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     * @param zoneCode  The zoneCode (int)
     */
    public MrnZonesCovered(
            String surveyId,
            int    zoneCode) {
        this();
        setSurveyId (surveyId);
        setZoneCode (zoneCode);
        if (dbg) System.out.println ("<br>in MrnZonesCovered constructor 3"); // debug
    } // MrnZonesCovered constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSurveyId (CHARNULL);
        setZoneCode (INTNULL );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'surveyId' class variable
     * @param  surveyId (String)
     */
    public int setSurveyId(String surveyId) {
        try {
            this.surveyId = surveyId;
            if (this.surveyId != CHARNULL) {
                this.surveyId = stripCRLF(this.surveyId.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>surveyId = " + this.surveyId);
        } catch (Exception e) {
            setSurveyIdError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return surveyIdError;
    } // method setSurveyId

    /**
     * Called when an exception has occured
     * @param  surveyId (String)
     */
    private void setSurveyIdError (String surveyId, Exception e, int error) {
        this.surveyId = surveyId;
        surveyIdErrorMessage = e.toString();
        surveyIdError = error;
    } // method setSurveyIdError


    /**
     * Set the 'zoneCode' class variable
     * @param  zoneCode (int)
     */
    public int setZoneCode(int zoneCode) {
        try {
            if ( ((zoneCode == INTNULL) ||
                  (zoneCode == INTNULL2)) ||
                !((zoneCode < ZONE_CODE_MN) ||
                  (zoneCode > ZONE_CODE_MX)) ) {
                this.zoneCode = zoneCode;
                zoneCodeError = ERROR_NORMAL;
            } else {
                throw zoneCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setZoneCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return zoneCodeError;
    } // method setZoneCode

    /**
     * Set the 'zoneCode' class variable
     * @param  zoneCode (Integer)
     */
    public int setZoneCode(Integer zoneCode) {
        try {
            setZoneCode(zoneCode.intValue());
        } catch (Exception e) {
            setZoneCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return zoneCodeError;
    } // method setZoneCode

    /**
     * Set the 'zoneCode' class variable
     * @param  zoneCode (Float)
     */
    public int setZoneCode(Float zoneCode) {
        try {
            if (zoneCode.floatValue() == FLOATNULL) {
                setZoneCode(INTNULL);
            } else {
                setZoneCode(zoneCode.intValue());
            } // if (zoneCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setZoneCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return zoneCodeError;
    } // method setZoneCode

    /**
     * Set the 'zoneCode' class variable
     * @param  zoneCode (String)
     */
    public int setZoneCode(String zoneCode) {
        try {
            setZoneCode(new Integer(zoneCode).intValue());
        } catch (Exception e) {
            setZoneCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return zoneCodeError;
    } // method setZoneCode

    /**
     * Called when an exception has occured
     * @param  zoneCode (String)
     */
    private void setZoneCodeError (int zoneCode, Exception e, int error) {
        this.zoneCode = zoneCode;
        zoneCodeErrorMessage = e.toString();
        zoneCodeError = error;
    } // method setZoneCodeError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'surveyId' class variable
     * @return surveyId (String)
     */
    public String getSurveyId() {
        return surveyId;
    } // method getSurveyId

    /**
     * Return the 'surveyId' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurveyId methods
     * @return surveyId (String)
     */
    public String getSurveyId(String s) {
        return (surveyId != CHARNULL ? surveyId.replace('"','\'') : "");
    } // method getSurveyId


    /**
     * Return the 'zoneCode' class variable
     * @return zoneCode (int)
     */
    public int getZoneCode() {
        return zoneCode;
    } // method getZoneCode

    /**
     * Return the 'zoneCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getZoneCode methods
     * @return zoneCode (String)
     */
    public String getZoneCode(String s) {
        return ((zoneCode != INTNULL) ? new Integer(zoneCode).toString() : "");
    } // method getZoneCode


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
        if ((surveyId == CHARNULL) &&
            (zoneCode == INTNULL)) {
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
        int sumError = surveyIdError +
            zoneCodeError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the surveyId instance variable
     * @return errorcode (int)
     */
    public int getSurveyIdError() {
        return surveyIdError;
    } // method getSurveyIdError

    /**
     * Gets the errorMessage for the surveyId instance variable
     * @return errorMessage (String)
     */
    public String getSurveyIdErrorMessage() {
        return surveyIdErrorMessage;
    } // method getSurveyIdErrorMessage


    /**
     * Gets the errorcode for the zoneCode instance variable
     * @return errorcode (int)
     */
    public int getZoneCodeError() {
        return zoneCodeError;
    } // method getZoneCodeError

    /**
     * Gets the errorMessage for the zoneCode instance variable
     * @return errorMessage (String)
     */
    public String getZoneCodeErrorMessage() {
        return zoneCodeErrorMessage;
    } // method getZoneCodeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnZonesCovered. e.g.<pre>
     * MrnZonesCovered zonesCovered = new MrnZonesCovered(<val1>);
     * MrnZonesCovered zonesCoveredArray[] = zonesCovered.get();</pre>
     * will get the MrnZonesCovered record where surveyId = <val1>.
     * @return Array of MrnZonesCovered (MrnZonesCovered[])
     */
    public MrnZonesCovered[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnZonesCovered zonesCoveredArray[] =
     *     new MrnZonesCovered().get(MrnZonesCovered.SURVEY_ID+"=<val1>");</pre>
     * will get the MrnZonesCovered record where surveyId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnZonesCovered (MrnZonesCovered[])
     */
    public MrnZonesCovered[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnZonesCovered zonesCoveredArray[] =
     *     new MrnZonesCovered().get("1=1",zonesCovered.SURVEY_ID);</pre>
     * will get the all the MrnZonesCovered records, and order them by surveyId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnZonesCovered (MrnZonesCovered[])
     */
    public MrnZonesCovered[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnZonesCovered.SURVEY_ID,MrnZonesCovered.ZONE_CODE;
     * String where = MrnZonesCovered.SURVEY_ID + "=<val1";
     * String order = MrnZonesCovered.SURVEY_ID;
     * MrnZonesCovered zonesCoveredArray[] =
     *     new MrnZonesCovered().get(columns, where, order);</pre>
     * will get the surveyId and zoneCode colums of all MrnZonesCovered records,
     * where surveyId = <val1>, and order them by surveyId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnZonesCovered (MrnZonesCovered[])
     */
    public MrnZonesCovered[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnZonesCovered.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnZonesCovered[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int surveyIdCol = db.getColNumber(SURVEY_ID);
        int zoneCodeCol = db.getColNumber(ZONE_CODE);
        MrnZonesCovered[] cArray = new MrnZonesCovered[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnZonesCovered();
            if (surveyIdCol != -1)
                cArray[i].setSurveyId((String) row.elementAt(surveyIdCol));
            if (zoneCodeCol != -1)
                cArray[i].setZoneCode((String) row.elementAt(zoneCodeCol));
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
     *     new MrnZonesCovered(
     *         <val1>,
     *         <val2>).put();</pre>
     * will insert a record with:
     *     surveyId = <val1>,
     *     zoneCode = <val2>.
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
     * Boolean success = new MrnZonesCovered(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where zoneCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnZonesCovered zonesCovered = new MrnZonesCovered();
     * success = zonesCovered.del(MrnZonesCovered.SURVEY_ID+"=<val1>");</pre>
     * will delete all records where surveyId = <val1>.
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
     * update are taken from the MrnZonesCovered argument, .e.g.<pre>
     * Boolean success
     * MrnZonesCovered updMrnZonesCovered = new MrnZonesCovered();
     * updMrnZonesCovered.setZoneCode(<val2>);
     * MrnZonesCovered whereMrnZonesCovered = new MrnZonesCovered(<val1>);
     * success = whereMrnZonesCovered.upd(updMrnZonesCovered);</pre>
     * will update ZoneCode to <val2> for all records where
     * surveyId = <val1>.
     * @param  zonesCovered  A MrnZonesCovered variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnZonesCovered zonesCovered) {
        return db.update (TABLE, createColVals(zonesCovered), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnZonesCovered updMrnZonesCovered = new MrnZonesCovered();
     * updMrnZonesCovered.setZoneCode(<val2>);
     * MrnZonesCovered whereMrnZonesCovered = new MrnZonesCovered();
     * success = whereMrnZonesCovered.upd(
     *     updMrnZonesCovered, MrnZonesCovered.SURVEY_ID+"=<val1>");</pre>
     * will update ZoneCode to <val2> for all records where
     * surveyId = <val1>.
     * @param  zonesCovered  A MrnZonesCovered variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnZonesCovered zonesCovered, String where) {
        return db.update (TABLE, createColVals(zonesCovered), where);
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
        if (getSurveyId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_ID + "='" + getSurveyId() + "'";
        } // if getSurveyId
        if (getZoneCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ZONE_CODE + "=" + getZoneCode("");
        } // if getZoneCode
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnZonesCovered aVar) {
        String colVals = "";
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getZoneCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ZONE_CODE +"=";
            colVals += (aVar.getZoneCode() == INTNULL2 ?
                "null" : aVar.getZoneCode(""));
        } // if aVar.getZoneCode
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SURVEY_ID;
        if (getZoneCode() != INTNULL) {
            columns = columns + "," + ZONE_CODE;
        } // if getZoneCode
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getSurveyId() + "'";
        if (getZoneCode() != INTNULL) {
            values  = values  + "," + getZoneCode("");
        } // if getZoneCode
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getSurveyId("") + "|" +
            getZoneCode("") + "|";
    } // method toString

} // class MrnZonesCovered
