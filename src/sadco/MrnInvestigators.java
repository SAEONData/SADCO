package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the INVESTIGATORS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnInvestigators extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "INVESTIGATORS";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The sciCode field name */
    public static final String SCI_CODE = "SCI_CODE";
    /** The piCode field name */
    public static final String PI_CODE = "PI_CODE";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    surveyId;
    private int       sciCode;
    private String    piCode;

    /** The error variables  */
    private int surveyIdError = ERROR_NORMAL;
    private int sciCodeError  = ERROR_NORMAL;
    private int piCodeError   = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String surveyIdErrorMessage = "";
    private String sciCodeErrorMessage  = "";
    private String piCodeErrorMessage   = "";

    /** The min-max constants for all numerics */
    public static final int       SCI_CODE_MN = INTMIN;
    public static final int       SCI_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception sciCodeOutOfBoundsException =
        new Exception ("'sciCode' out of bounds: " +
            SCI_CODE_MN + " - " + SCI_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnInvestigators() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnInvestigators constructor 1"); // debug
    } // MrnInvestigators constructor

    /**
     * Instantiate a MrnInvestigators object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     */
    public MrnInvestigators(
            String surveyId) {
        this();
        setSurveyId (surveyId);
        if (dbg) System.out.println ("<br>in MrnInvestigators constructor 2"); // debug
    } // MrnInvestigators constructor

    /**
     * Instantiate a MrnInvestigators object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     * @param sciCode   The sciCode  (int)
     * @param piCode    The piCode   (String)
     */
    public MrnInvestigators(
            String surveyId,
            int    sciCode,
            String piCode) {
        this();
        setSurveyId (surveyId);
        setSciCode  (sciCode );
        setPiCode   (piCode  );
        if (dbg) System.out.println ("<br>in MrnInvestigators constructor 3"); // debug
    } // MrnInvestigators constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSurveyId (CHARNULL);
        setSciCode  (INTNULL );
        setPiCode   (CHARNULL);
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
     * Set the 'sciCode' class variable
     * @param  sciCode (int)
     */
    public int setSciCode(int sciCode) {
        try {
            if ( ((sciCode == INTNULL) ||
                  (sciCode == INTNULL2)) ||
                !((sciCode < SCI_CODE_MN) ||
                  (sciCode > SCI_CODE_MX)) ) {
                this.sciCode = sciCode;
                sciCodeError = ERROR_NORMAL;
            } else {
                throw sciCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSciCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sciCodeError;
    } // method setSciCode

    /**
     * Set the 'sciCode' class variable
     * @param  sciCode (Integer)
     */
    public int setSciCode(Integer sciCode) {
        try {
            setSciCode(sciCode.intValue());
        } catch (Exception e) {
            setSciCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sciCodeError;
    } // method setSciCode

    /**
     * Set the 'sciCode' class variable
     * @param  sciCode (Float)
     */
    public int setSciCode(Float sciCode) {
        try {
            if (sciCode.floatValue() == FLOATNULL) {
                setSciCode(INTNULL);
            } else {
                setSciCode(sciCode.intValue());
            } // if (sciCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSciCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sciCodeError;
    } // method setSciCode

    /**
     * Set the 'sciCode' class variable
     * @param  sciCode (String)
     */
    public int setSciCode(String sciCode) {
        try {
            setSciCode(new Integer(sciCode).intValue());
        } catch (Exception e) {
            setSciCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sciCodeError;
    } // method setSciCode

    /**
     * Called when an exception has occured
     * @param  sciCode (String)
     */
    private void setSciCodeError (int sciCode, Exception e, int error) {
        this.sciCode = sciCode;
        sciCodeErrorMessage = e.toString();
        sciCodeError = error;
    } // method setSciCodeError


    /**
     * Set the 'piCode' class variable
     * @param  piCode (String)
     */
    public int setPiCode(String piCode) {
        try {
            this.piCode = piCode;
            if (this.piCode != CHARNULL) {
                this.piCode = stripCRLF(this.piCode.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>piCode = " + this.piCode);
        } catch (Exception e) {
            setPiCodeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return piCodeError;
    } // method setPiCode

    /**
     * Called when an exception has occured
     * @param  piCode (String)
     */
    private void setPiCodeError (String piCode, Exception e, int error) {
        this.piCode = piCode;
        piCodeErrorMessage = e.toString();
        piCodeError = error;
    } // method setPiCodeError


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
     * Return the 'sciCode' class variable
     * @return sciCode (int)
     */
    public int getSciCode() {
        return sciCode;
    } // method getSciCode

    /**
     * Return the 'sciCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSciCode methods
     * @return sciCode (String)
     */
    public String getSciCode(String s) {
        return ((sciCode != INTNULL) ? new Integer(sciCode).toString() : "");
    } // method getSciCode


    /**
     * Return the 'piCode' class variable
     * @return piCode (String)
     */
    public String getPiCode() {
        return piCode;
    } // method getPiCode

    /**
     * Return the 'piCode' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPiCode methods
     * @return piCode (String)
     */
    public String getPiCode(String s) {
        return (piCode != CHARNULL ? piCode.replace('"','\'') : "");
    } // method getPiCode


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
            (sciCode == INTNULL) &&
            (piCode == CHARNULL)) {
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
            sciCodeError +
            piCodeError;
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
     * Gets the errorcode for the sciCode instance variable
     * @return errorcode (int)
     */
    public int getSciCodeError() {
        return sciCodeError;
    } // method getSciCodeError

    /**
     * Gets the errorMessage for the sciCode instance variable
     * @return errorMessage (String)
     */
    public String getSciCodeErrorMessage() {
        return sciCodeErrorMessage;
    } // method getSciCodeErrorMessage


    /**
     * Gets the errorcode for the piCode instance variable
     * @return errorcode (int)
     */
    public int getPiCodeError() {
        return piCodeError;
    } // method getPiCodeError

    /**
     * Gets the errorMessage for the piCode instance variable
     * @return errorMessage (String)
     */
    public String getPiCodeErrorMessage() {
        return piCodeErrorMessage;
    } // method getPiCodeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnInvestigators. e.g.<pre>
     * MrnInvestigators investigators = new MrnInvestigators(<val1>);
     * MrnInvestigators investigatorsArray[] = investigators.get();</pre>
     * will get the MrnInvestigators record where surveyId = <val1>.
     * @return Array of MrnInvestigators (MrnInvestigators[])
     */
    public MrnInvestigators[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnInvestigators investigatorsArray[] =
     *     new MrnInvestigators().get(MrnInvestigators.SURVEY_ID+"=<val1>");</pre>
     * will get the MrnInvestigators record where surveyId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnInvestigators (MrnInvestigators[])
     */
    public MrnInvestigators[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnInvestigators investigatorsArray[] =
     *     new MrnInvestigators().get("1=1",investigators.SURVEY_ID);</pre>
     * will get the all the MrnInvestigators records, and order them by surveyId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnInvestigators (MrnInvestigators[])
     */
    public MrnInvestigators[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnInvestigators.SURVEY_ID,MrnInvestigators.SCI_CODE;
     * String where = MrnInvestigators.SURVEY_ID + "=<val1";
     * String order = MrnInvestigators.SURVEY_ID;
     * MrnInvestigators investigatorsArray[] =
     *     new MrnInvestigators().get(columns, where, order);</pre>
     * will get the surveyId and sciCode colums of all MrnInvestigators records,
     * where surveyId = <val1>, and order them by surveyId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnInvestigators (MrnInvestigators[])
     */
    public MrnInvestigators[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnInvestigators.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnInvestigators[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int surveyIdCol = db.getColNumber(SURVEY_ID);
        int sciCodeCol  = db.getColNumber(SCI_CODE);
        int piCodeCol   = db.getColNumber(PI_CODE);
        MrnInvestigators[] cArray = new MrnInvestigators[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnInvestigators();
            if (surveyIdCol != -1)
                cArray[i].setSurveyId((String) row.elementAt(surveyIdCol));
            if (sciCodeCol != -1)
                cArray[i].setSciCode ((String) row.elementAt(sciCodeCol));
            if (piCodeCol != -1)
                cArray[i].setPiCode  ((String) row.elementAt(piCodeCol));
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
     *     new MrnInvestigators(
     *         <val1>,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     surveyId = <val1>,
     *     sciCode  = <val2>,
     *     piCode   = <val3>.
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
     * Boolean success = new MrnInvestigators(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where sciCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnInvestigators investigators = new MrnInvestigators();
     * success = investigators.del(MrnInvestigators.SURVEY_ID+"=<val1>");</pre>
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
     * update are taken from the MrnInvestigators argument, .e.g.<pre>
     * Boolean success
     * MrnInvestigators updMrnInvestigators = new MrnInvestigators();
     * updMrnInvestigators.setSciCode(<val2>);
     * MrnInvestigators whereMrnInvestigators = new MrnInvestigators(<val1>);
     * success = whereMrnInvestigators.upd(updMrnInvestigators);</pre>
     * will update SciCode to <val2> for all records where
     * surveyId = <val1>.
     * @param  investigators  A MrnInvestigators variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnInvestigators investigators) {
        return db.update (TABLE, createColVals(investigators), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnInvestigators updMrnInvestigators = new MrnInvestigators();
     * updMrnInvestigators.setSciCode(<val2>);
     * MrnInvestigators whereMrnInvestigators = new MrnInvestigators();
     * success = whereMrnInvestigators.upd(
     *     updMrnInvestigators, MrnInvestigators.SURVEY_ID+"=<val1>");</pre>
     * will update SciCode to <val2> for all records where
     * surveyId = <val1>.
     * @param  investigators  A MrnInvestigators variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnInvestigators investigators, String where) {
        return db.update (TABLE, createColVals(investigators), where);
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
        if (getSciCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SCI_CODE + "=" + getSciCode("");
        } // if getSciCode
        if (getPiCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PI_CODE + "='" + getPiCode() + "'";
        } // if getPiCode
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnInvestigators aVar) {
        String colVals = "";
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getSciCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SCI_CODE +"=";
            colVals += (aVar.getSciCode() == INTNULL2 ?
                "null" : aVar.getSciCode(""));
        } // if aVar.getSciCode
        if (aVar.getPiCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PI_CODE +"=";
            colVals += (aVar.getPiCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPiCode() + "'");
        } // if aVar.getPiCode
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SURVEY_ID;
        if (getSciCode() != INTNULL) {
            columns = columns + "," + SCI_CODE;
        } // if getSciCode
        if (getPiCode() != CHARNULL) {
            columns = columns + "," + PI_CODE;
        } // if getPiCode
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getSurveyId() + "'";
        if (getSciCode() != INTNULL) {
            values  = values  + "," + getSciCode("");
        } // if getSciCode
        if (getPiCode() != CHARNULL) {
            values  = values  + ",'" + getPiCode() + "'";
        } // if getPiCode
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
            getSciCode("")  + "|" +
            getPiCode("")   + "|";
    } // method toString

} // class MrnInvestigators
