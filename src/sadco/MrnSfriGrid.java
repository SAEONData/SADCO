package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SFRI_GRID table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnSfriGrid extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SFRI_GRID";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The gridNo field name */
    public static final String GRID_NO = "GRID_NO";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    surveyId;
    private String    stationId;
    private String    gridNo;

    /** The error variables  */
    private int surveyIdError  = ERROR_NORMAL;
    private int stationIdError = ERROR_NORMAL;
    private int gridNoError    = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String surveyIdErrorMessage  = "";
    private String stationIdErrorMessage = "";
    private String gridNoErrorMessage    = "";

    /** The min-max constants for all numerics */

    /** The exceptions for non-Strings */


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnSfriGrid() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnSfriGrid constructor 1"); // debug
    } // MrnSfriGrid constructor

    /**
     * Instantiate a MrnSfriGrid object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     */
    public MrnSfriGrid(
            String surveyId) {
        this();
        setSurveyId  (surveyId );
        if (dbg) System.out.println ("<br>in MrnSfriGrid constructor 2"); // debug
    } // MrnSfriGrid constructor

    /**
     * Instantiate a MrnSfriGrid object and initialize the instance variables.
     * @param surveyId   The surveyId  (String)
     * @param stationId  The stationId (String)
     * @param gridNo     The gridNo    (String)
     */
    public MrnSfriGrid(
            String surveyId,
            String stationId,
            String gridNo) {
        this();
        setSurveyId  (surveyId );
        setStationId (stationId);
        setGridNo    (gridNo   );
        if (dbg) System.out.println ("<br>in MrnSfriGrid constructor 3"); // debug
    } // MrnSfriGrid constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSurveyId  (CHARNULL);
        setStationId (CHARNULL);
        setGridNo    (CHARNULL);
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
     * Set the 'gridNo' class variable
     * @param  gridNo (String)
     */
    public int setGridNo(String gridNo) {
        try {
            this.gridNo = gridNo;
            if (this.gridNo != CHARNULL) {
                this.gridNo = stripCRLF(this.gridNo.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>gridNo = " + this.gridNo);
        } catch (Exception e) {
            setGridNoError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return gridNoError;
    } // method setGridNo

    /**
     * Called when an exception has occured
     * @param  gridNo (String)
     */
    private void setGridNoError (String gridNo, Exception e, int error) {
        this.gridNo = gridNo;
        gridNoErrorMessage = e.toString();
        gridNoError = error;
    } // method setGridNoError


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
     * Return the 'gridNo' class variable
     * @return gridNo (String)
     */
    public String getGridNo() {
        return gridNo;
    } // method getGridNo

    /**
     * Return the 'gridNo' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getGridNo methods
     * @return gridNo (String)
     */
    public String getGridNo(String s) {
        return (gridNo != CHARNULL ? gridNo.replace('"','\'') : "");
    } // method getGridNo


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
            (stationId == CHARNULL) &&
            (gridNo == CHARNULL)) {
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
            stationIdError +
            gridNoError;
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
     * Gets the errorcode for the gridNo instance variable
     * @return errorcode (int)
     */
    public int getGridNoError() {
        return gridNoError;
    } // method getGridNoError

    /**
     * Gets the errorMessage for the gridNo instance variable
     * @return errorMessage (String)
     */
    public String getGridNoErrorMessage() {
        return gridNoErrorMessage;
    } // method getGridNoErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnSfriGrid. e.g.<pre>
     * MrnSfriGrid sfriGrid = new MrnSfriGrid(<val1>);
     * MrnSfriGrid sfriGridArray[] = sfriGrid.get();</pre>
     * will get the MrnSfriGrid record where surveyId = <val1>.
     * @return Array of MrnSfriGrid (MrnSfriGrid[])
     */
    public MrnSfriGrid[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnSfriGrid sfriGridArray[] =
     *     new MrnSfriGrid().get(MrnSfriGrid.SURVEY_ID+"=<val1>");</pre>
     * will get the MrnSfriGrid record where surveyId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnSfriGrid (MrnSfriGrid[])
     */
    public MrnSfriGrid[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnSfriGrid sfriGridArray[] =
     *     new MrnSfriGrid().get("1=1",sfriGrid.SURVEY_ID);</pre>
     * will get the all the MrnSfriGrid records, and order them by surveyId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSfriGrid (MrnSfriGrid[])
     */
    public MrnSfriGrid[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnSfriGrid.SURVEY_ID,MrnSfriGrid.STATION_ID;
     * String where = MrnSfriGrid.SURVEY_ID + "=<val1";
     * String order = MrnSfriGrid.SURVEY_ID;
     * MrnSfriGrid sfriGridArray[] =
     *     new MrnSfriGrid().get(columns, where, order);</pre>
     * will get the surveyId and stationId colums of all MrnSfriGrid records,
     * where surveyId = <val1>, and order them by surveyId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSfriGrid (MrnSfriGrid[])
     */
    public MrnSfriGrid[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnSfriGrid.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnSfriGrid[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int surveyIdCol  = db.getColNumber(SURVEY_ID);
        int stationIdCol = db.getColNumber(STATION_ID);
        int gridNoCol    = db.getColNumber(GRID_NO);
        MrnSfriGrid[] cArray = new MrnSfriGrid[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnSfriGrid();
            if (surveyIdCol != -1)
                cArray[i].setSurveyId ((String) row.elementAt(surveyIdCol));
            if (stationIdCol != -1)
                cArray[i].setStationId((String) row.elementAt(stationIdCol));
            if (gridNoCol != -1)
                cArray[i].setGridNo   ((String) row.elementAt(gridNoCol));
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
     *     new MrnSfriGrid(
     *         <val1>,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     surveyId  = <val1>,
     *     stationId = <val2>,
     *     gridNo    = <val3>.
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
     * Boolean success = new MrnSfriGrid(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where stationId = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSfriGrid sfriGrid = new MrnSfriGrid();
     * success = sfriGrid.del(MrnSfriGrid.SURVEY_ID+"=<val1>");</pre>
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
     * update are taken from the MrnSfriGrid argument, .e.g.<pre>
     * Boolean success
     * MrnSfriGrid updMrnSfriGrid = new MrnSfriGrid();
     * updMrnSfriGrid.setStationId(<val2>);
     * MrnSfriGrid whereMrnSfriGrid = new MrnSfriGrid(<val1>);
     * success = whereMrnSfriGrid.upd(updMrnSfriGrid);</pre>
     * will update StationId to <val2> for all records where
     * surveyId = <val1>.
     * @param  sfriGrid  A MrnSfriGrid variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSfriGrid sfriGrid) {
        return db.update (TABLE, createColVals(sfriGrid), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSfriGrid updMrnSfriGrid = new MrnSfriGrid();
     * updMrnSfriGrid.setStationId(<val2>);
     * MrnSfriGrid whereMrnSfriGrid = new MrnSfriGrid();
     * success = whereMrnSfriGrid.upd(
     *     updMrnSfriGrid, MrnSfriGrid.SURVEY_ID+"=<val1>");</pre>
     * will update StationId to <val2> for all records where
     * surveyId = <val1>.
     * @param  sfriGrid  A MrnSfriGrid variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSfriGrid sfriGrid, String where) {
        return db.update (TABLE, createColVals(sfriGrid), where);
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
        if (getStationId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATION_ID + "='" + getStationId() + "'";
        } // if getStationId
        if (getGridNo() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + GRID_NO + "='" + getGridNo() + "'";
        } // if getGridNo
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnSfriGrid aVar) {
        String colVals = "";
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getGridNo() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += GRID_NO +"=";
            colVals += (aVar.getGridNo().equals(CHARNULL2) ?
                "null" : "'" + aVar.getGridNo() + "'");
        } // if aVar.getGridNo
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SURVEY_ID;
        if (getStationId() != CHARNULL) {
            columns = columns + "," + STATION_ID;
        } // if getStationId
        if (getGridNo() != CHARNULL) {
            columns = columns + "," + GRID_NO;
        } // if getGridNo
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getSurveyId() + "'";
        if (getStationId() != CHARNULL) {
            values  = values  + ",'" + getStationId() + "'";
        } // if getStationId
        if (getGridNo() != CHARNULL) {
            values  = values  + ",'" + getGridNo() + "'";
        } // if getGridNo
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getSurveyId("")  + "|" +
            getStationId("") + "|" +
            getGridNo("")    + "|";
    } // method toString

} // class MrnSfriGrid
