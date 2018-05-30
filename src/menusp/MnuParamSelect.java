package menusp;

import java.sql.*;
import java.util.*;

/**
 * This class manages the DMN_PARAM_SELECT table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MnuParamSelect extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "DMN_PARAM_SELECT";
    /** The parameterCode field name */
    public static final String PARAMETER_CODE = "PARAMETER_CODE";
    /** The value field name */
    public static final String VALUE = "VALUE";
    /** The text field name */
    public static final String TEXT = "TEXT";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       parameterCode;
    private String    value;
    private String    text;

    /** The error variables  */
    private int parameterCodeError = ERROR_NORMAL;
    private int valueError         = ERROR_NORMAL;
    private int textError          = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String parameterCodeErrorMessage = "";
    private String valueErrorMessage         = "";
    private String textErrorMessage          = "";

    /** The min-max constants for all numerics */
    public static final int       PARAMETER_CODE_MN = INTMIN;
    public static final int       PARAMETER_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception parameterCodeOutOfBoundsException =
        new Exception ("'parameterCode' out of bounds: " +
            PARAMETER_CODE_MN + " - " + PARAMETER_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MnuParamSelect() {
        clearVars();
        if (dbg) System.out.println ("<br>in MnuParamSelect constructor 1"); // debug
    } // MnuParamSelect constructor

    /**
     * Instantiate a MnuParamSelect object and initialize the instance variables.
     * @param parameterCode  The parameterCode (int)
     */
    public MnuParamSelect(
            int parameterCode) {
        this();
        setParameterCode (parameterCode);
        if (dbg) System.out.println ("<br>in MnuParamSelect constructor 2"); // debug
    } // MnuParamSelect constructor

    /**
     * Instantiate a MnuParamSelect object and initialize the instance variables.
     * @param parameterCode  The parameterCode (int)
     * @param value          The value         (String)
     * @param text           The text          (String)
     */
    public MnuParamSelect(
            int    parameterCode,
            String value,
            String text) {
        this();
        setParameterCode (parameterCode);
        setValue         (value        );
        setText          (text         );
        if (dbg) System.out.println ("<br>in MnuParamSelect constructor 3"); // debug
    } // MnuParamSelect constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setParameterCode (INTNULL );
        setValue         (CHARNULL);
        setText          (CHARNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'parameterCode' class variable
     * @param  parameterCode (int)
     */
    public int setParameterCode(int parameterCode) {
        try {
            if ( ((parameterCode == INTNULL) ||
                  (parameterCode == INTNULL2)) ||
                !((parameterCode < PARAMETER_CODE_MN) ||
                  (parameterCode > PARAMETER_CODE_MX)) ) {
                this.parameterCode = parameterCode;
                parameterCodeError = ERROR_NORMAL;
            } else {
                throw parameterCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setParameterCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return parameterCodeError;
    } // method setParameterCode

    /**
     * Set the 'parameterCode' class variable
     * @param  parameterCode (Integer)
     */
    public int setParameterCode(Integer parameterCode) {
        try {
            setParameterCode(parameterCode.intValue());
        } catch (Exception e) {
            setParameterCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return parameterCodeError;
    } // method setParameterCode

    /**
     * Set the 'parameterCode' class variable
     * @param  parameterCode (Float)
     */
    public int setParameterCode(Float parameterCode) {
        try {
            if (parameterCode.floatValue() == FLOATNULL) {
                setParameterCode(INTNULL);
            } else {
                setParameterCode(parameterCode.intValue());
            } // if (parameterCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setParameterCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return parameterCodeError;
    } // method setParameterCode

    /**
     * Set the 'parameterCode' class variable
     * @param  parameterCode (String)
     */
    public int setParameterCode(String parameterCode) {
        try {
            setParameterCode(new Integer(parameterCode).intValue());
        } catch (Exception e) {
            setParameterCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return parameterCodeError;
    } // method setParameterCode

    /**
     * Called when an exception has occured
     * @param  parameterCode (String)
     */
    private void setParameterCodeError (int parameterCode, Exception e, int error) {
        this.parameterCode = parameterCode;
        parameterCodeErrorMessage = e.toString();
        parameterCodeError = error;
    } // method setParameterCodeError


    /**
     * Set the 'value' class variable
     * @param  value (String)
     */
    public int setValue(String value) {
        try {
            this.value = value;
            if (this.value != CHARNULL) {
                this.value = stripCRLF(this.value.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>value = " + this.value);
        } catch (Exception e) {
            setValueError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return valueError;
    } // method setValue

    /**
     * Called when an exception has occured
     * @param  value (String)
     */
    private void setValueError (String value, Exception e, int error) {
        this.value = value;
        valueErrorMessage = e.toString();
        valueError = error;
    } // method setValueError


    /**
     * Set the 'text' class variable
     * @param  text (String)
     */
    public int setText(String text) {
        try {
            this.text = text;
            if (this.text != CHARNULL) {
                this.text = stripCRLF(this.text.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>text = " + this.text);
        } catch (Exception e) {
            setTextError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return textError;
    } // method setText

    /**
     * Called when an exception has occured
     * @param  text (String)
     */
    private void setTextError (String text, Exception e, int error) {
        this.text = text;
        textErrorMessage = e.toString();
        textError = error;
    } // method setTextError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'parameterCode' class variable
     * @return parameterCode (int)
     */
    public int getParameterCode() {
        return parameterCode;
    } // method getParameterCode

    /**
     * Return the 'parameterCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getParameterCode methods
     * @return parameterCode (String)
     */
    public String getParameterCode(String s) {
        return ((parameterCode != INTNULL) ? new Integer(parameterCode).toString() : "");
    } // method getParameterCode


    /**
     * Return the 'value' class variable
     * @return value (String)
     */
    public String getValue() {
        return value;
    } // method getValue

    /**
     * Return the 'value' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getValue methods
     * @return value (String)
     */
    public String getValue(String s) {
        return (value != CHARNULL ? value.replace('"','\'') : "");
    } // method getValue


    /**
     * Return the 'text' class variable
     * @return text (String)
     */
    public String getText() {
        return text;
    } // method getText

    /**
     * Return the 'text' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getText methods
     * @return text (String)
     */
    public String getText(String s) {
        return (text != CHARNULL ? text.replace('"','\'') : "");
    } // method getText


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
        if ((parameterCode == INTNULL) &&
            (value == CHARNULL) &&
            (text == CHARNULL)) {
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
        int sumError = parameterCodeError +
            valueError +
            textError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the parameterCode instance variable
     * @return errorcode (int)
     */
    public int getParameterCodeError() {
        return parameterCodeError;
    } // method getParameterCodeError

    /**
     * Gets the errorMessage for the parameterCode instance variable
     * @return errorMessage (String)
     */
    public String getParameterCodeErrorMessage() {
        return parameterCodeErrorMessage;
    } // method getParameterCodeErrorMessage


    /**
     * Gets the errorcode for the value instance variable
     * @return errorcode (int)
     */
    public int getValueError() {
        return valueError;
    } // method getValueError

    /**
     * Gets the errorMessage for the value instance variable
     * @return errorMessage (String)
     */
    public String getValueErrorMessage() {
        return valueErrorMessage;
    } // method getValueErrorMessage


    /**
     * Gets the errorcode for the text instance variable
     * @return errorcode (int)
     */
    public int getTextError() {
        return textError;
    } // method getTextError

    /**
     * Gets the errorMessage for the text instance variable
     * @return errorMessage (String)
     */
    public String getTextErrorMessage() {
        return textErrorMessage;
    } // method getTextErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MnuParamSelect. e.g.<pre>
     * MnuParamSelect dmnParamSelect = new MnuParamSelect(<val1>);
     * MnuParamSelect dmnParamSelectArray[] = dmnParamSelect.get();</pre>
     * will get the MnuParamSelect record where parameterCode = <val1>.
     * @return Array of MnuParamSelect (MnuParamSelect[])
     */
    public MnuParamSelect[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MnuParamSelect dmnParamSelectArray[] =
     *     new MnuParamSelect().get(MnuParamSelect.PARAMETER_CODE+"=<val1>");</pre>
     * will get the MnuParamSelect record where parameterCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MnuParamSelect (MnuParamSelect[])
     */
    public MnuParamSelect[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MnuParamSelect dmnParamSelectArray[] =
     *     new MnuParamSelect().get("1=1",dmnParamSelect.PARAMETER_CODE);</pre>
     * will get the all the MnuParamSelect records, and order them by parameterCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MnuParamSelect (MnuParamSelect[])
     */
    public MnuParamSelect[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MnuParamSelect.PARAMETER_CODE,MnuParamSelect.VALUE;
     * String where = MnuParamSelect.PARAMETER_CODE + "=<val1";
     * String order = MnuParamSelect.PARAMETER_CODE;
     * MnuParamSelect dmnParamSelectArray[] =
     *     new MnuParamSelect().get(columns, where, order);</pre>
     * will get the parameterCode and value colums of all MnuParamSelect records,
     * where parameterCode = <val1>, and order them by parameterCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MnuParamSelect (MnuParamSelect[])
     */
    public MnuParamSelect[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MnuParamSelect.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MnuParamSelect[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int parameterCodeCol = db.getColNumber(PARAMETER_CODE);
        int valueCol         = db.getColNumber(VALUE);
        int textCol          = db.getColNumber(TEXT);
        MnuParamSelect[] cArray = new MnuParamSelect[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MnuParamSelect();
            if (parameterCodeCol != -1)
                cArray[i].setParameterCode((String) row.elementAt(parameterCodeCol));
            if (valueCol != -1)
                cArray[i].setValue        ((String) row.elementAt(valueCol));
            if (textCol != -1)
                cArray[i].setText         ((String) row.elementAt(textCol));
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
     *     new MnuParamSelect(
     *         <val1>,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     parameterCode = <val1>,
     *     value         = <val2>,
     *     text          = <val3>.
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
     * Boolean success = new MnuParamSelect(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where value = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MnuParamSelect dmnParamSelect = new MnuParamSelect();
     * success = dmnParamSelect.del(MnuParamSelect.PARAMETER_CODE+"=<val1>");</pre>
     * will delete all records where parameterCode = <val1>.
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
     * update are taken from the MnuParamSelect argument, .e.g.<pre>
     * Boolean success
     * MnuParamSelect updMnuParamSelect = new MnuParamSelect();
     * updMnuParamSelect.setValue(<val2>);
     * MnuParamSelect whereMnuParamSelect = new MnuParamSelect(<val1>);
     * success = whereMnuParamSelect.upd(updMnuParamSelect);</pre>
     * will update Value to <val2> for all records where
     * parameterCode = <val1>.
     * @param  dmnParamSelect  A MnuParamSelect variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MnuParamSelect dmnParamSelect) {
        return db.update (TABLE, createColVals(dmnParamSelect), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MnuParamSelect updMnuParamSelect = new MnuParamSelect();
     * updMnuParamSelect.setValue(<val2>);
     * MnuParamSelect whereMnuParamSelect = new MnuParamSelect();
     * success = whereMnuParamSelect.upd(
     *     updMnuParamSelect, MnuParamSelect.PARAMETER_CODE+"=<val1>");</pre>
     * will update Value to <val2> for all records where
     * parameterCode = <val1>.
     * @param  dmnParamSelect  A MnuParamSelect variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MnuParamSelect dmnParamSelect, String where) {
        return db.update (TABLE, createColVals(dmnParamSelect), where);
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
        if (getParameterCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PARAMETER_CODE + "=" + getParameterCode("");
        } // if getParameterCode
        if (getValue() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + VALUE + "='" + getValue() + "'";
        } // if getValue
        if (getText() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TEXT + "='" + getText() + "'";
        } // if getText
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MnuParamSelect aVar) {
        String colVals = "";
        if (aVar.getParameterCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PARAMETER_CODE +"=";
            colVals += (aVar.getParameterCode() == INTNULL2 ?
                "null" : aVar.getParameterCode(""));
        } // if aVar.getParameterCode
        if (aVar.getValue() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += VALUE +"=";
            colVals += (aVar.getValue().equals(CHARNULL2) ?
                "null" : "'" + aVar.getValue() + "'");
        } // if aVar.getValue
        if (aVar.getText() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TEXT +"=";
            colVals += (aVar.getText().equals(CHARNULL2) ?
                "null" : "'" + aVar.getText() + "'");
        } // if aVar.getText
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = PARAMETER_CODE;
        if (getValue() != CHARNULL) {
            columns = columns + "," + VALUE;
        } // if getValue
        if (getText() != CHARNULL) {
            columns = columns + "," + TEXT;
        } // if getText
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getParameterCode("");
        if (getValue() != CHARNULL) {
            values  = values  + ",'" + getValue() + "'";
        } // if getValue
        if (getText() != CHARNULL) {
            values  = values  + ",'" + getText() + "'";
        } // if getText
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getParameterCode("") + "|" +
            getValue("")         + "|" +
            getText("")          + "|";
    } // method toString

} // class MnuParamSelect
