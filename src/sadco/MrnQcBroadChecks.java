package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the QC_BROAD_CHECKS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 070703 - SIT Group
 * @version
 * 070703 - GenTableClassB class - create class<br>
 */
public class MrnQcBroadChecks extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "QC_BROAD_CHECKS";
    /** The basin field name */
    public static final String BASIN = "BASIN";
    /** The parameterCode field name */
    public static final String PARAMETER_CODE = "PARAMETER_CODE";
    /** The depth field name */
    public static final String DEPTH = "DEPTH";
    /** The minValue field name */
    public static final String MIN_VALUE = "MIN_VALUE";
    /** The maxValue field name */
    public static final String MAX_VALUE = "MAX_VALUE";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       basin;
    private int       parameterCode;
    private int       depth;
    private float     minValue;
    private float     maxValue;

    /** The error variables  */
    private int basinError         = ERROR_NORMAL;
    private int parameterCodeError = ERROR_NORMAL;
    private int depthError         = ERROR_NORMAL;
    private int minValueError      = ERROR_NORMAL;
    private int maxValueError      = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String basinErrorMessage         = "";
    private String parameterCodeErrorMessage = "";
    private String depthErrorMessage         = "";
    private String minValueErrorMessage      = "";
    private String maxValueErrorMessage      = "";

    /** The min-max constants for all numerics */
    public static final int       BASIN_MN = INTMIN;
    public static final int       BASIN_MX = INTMAX;
    public static final int       PARAMETER_CODE_MN = INTMIN;
    public static final int       PARAMETER_CODE_MX = INTMAX;
    public static final int       DEPTH_MN = INTMIN;
    public static final int       DEPTH_MX = INTMAX;
    public static final float     MIN_VALUE_MN = FLOATMIN;
    public static final float     MIN_VALUE_MX = FLOATMAX;
    public static final float     MAX_VALUE_MN = FLOATMIN;
    public static final float     MAX_VALUE_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception basinOutOfBoundsException =
        new Exception ("'basin' out of bounds: " +
            BASIN_MN + " - " + BASIN_MX);
    Exception parameterCodeOutOfBoundsException =
        new Exception ("'parameterCode' out of bounds: " +
            PARAMETER_CODE_MN + " - " + PARAMETER_CODE_MX);
    Exception depthOutOfBoundsException =
        new Exception ("'depth' out of bounds: " +
            DEPTH_MN + " - " + DEPTH_MX);
    Exception minValueOutOfBoundsException =
        new Exception ("'minValue' out of bounds: " +
            MIN_VALUE_MN + " - " + MIN_VALUE_MX);
    Exception maxValueOutOfBoundsException =
        new Exception ("'maxValue' out of bounds: " +
            MAX_VALUE_MN + " - " + MAX_VALUE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnQcBroadChecks() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnQcBroadChecks constructor 1"); // debug
    } // MrnQcBroadChecks constructor

    /**
     * Instantiate a MrnQcBroadChecks object and initialize the instance variables.
     * @param basin  The basin (int)
     */
    public MrnQcBroadChecks(
            int basin) {
        this();
        setBasin         (basin        );
        if (dbg) System.out.println ("<br>in MrnQcBroadChecks constructor 2"); // debug
    } // MrnQcBroadChecks constructor

    /**
     * Instantiate a MrnQcBroadChecks object and initialize the instance variables.
     * @param basin          The basin         (int)
     * @param parameterCode  The parameterCode (int)
     * @param depth          The depth         (int)
     * @param minValue       The minValue      (float)
     * @param maxValue       The maxValue      (float)
     * @return A MrnQcBroadChecks object
     */
    public MrnQcBroadChecks(
            int   basin,
            int   parameterCode,
            int   depth,
            float minValue,
            float maxValue) {
        this();
        setBasin         (basin        );
        setParameterCode (parameterCode);
        setDepth         (depth        );
        setMinValue      (minValue     );
        setMaxValue      (maxValue     );
        if (dbg) System.out.println ("<br>in MrnQcBroadChecks constructor 3"); // debug
    } // MrnQcBroadChecks constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setBasin         (INTNULL  );
        setParameterCode (INTNULL  );
        setDepth         (INTNULL  );
        setMinValue      (FLOATNULL);
        setMaxValue      (FLOATNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'basin' class variable
     * @param  basin (int)
     */
    public int setBasin(int basin) {
        try {
            if ( ((basin == INTNULL) || 
                  (basin == INTNULL2)) ||
                !((basin < BASIN_MN) ||
                  (basin > BASIN_MX)) ) {
                this.basin = basin;
                basinError = ERROR_NORMAL;
            } else {
                throw basinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setBasinError(INTNULL, e, ERROR_LOCAL);
        } // try
        return basinError;
    } // method setBasin

    /**
     * Set the 'basin' class variable
     * @param  basin (Integer)
     */
    public int setBasin(Integer basin) {
        try {
            setBasin(basin.intValue());
        } catch (Exception e) {
            setBasinError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return basinError;
    } // method setBasin

    /**
     * Set the 'basin' class variable
     * @param  basin (Float)
     */
    public int setBasin(Float basin) {
        try {
            if (basin.floatValue() == FLOATNULL) {
                setBasin(INTNULL);
            } else {
                setBasin(basin.intValue());
            } // if (basin.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setBasinError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return basinError;
    } // method setBasin

    /**
     * Set the 'basin' class variable
     * @param  basin (String)
     */
    public int setBasin(String basin) {
        try {
            setBasin(new Integer(basin).intValue());
        } catch (Exception e) {
            setBasinError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return basinError;
    } // method setBasin

    /**
     * Called when an exception has occured
     * @param  basin (String)
     */
    private void setBasinError (int basin, Exception e, int error) {
        this.basin = basin;
        basinErrorMessage = e.toString();
        basinError = error;
    } // method setBasinError


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
     * Set the 'depth' class variable
     * @param  depth (int)
     */
    public int setDepth(int depth) {
        try {
            if ( ((depth == INTNULL) || 
                  (depth == INTNULL2)) ||
                !((depth < DEPTH_MN) ||
                  (depth > DEPTH_MX)) ) {
                this.depth = depth;
                depthError = ERROR_NORMAL;
            } else {
                throw depthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDepthError(INTNULL, e, ERROR_LOCAL);
        } // try
        return depthError;
    } // method setDepth

    /**
     * Set the 'depth' class variable
     * @param  depth (Integer)
     */
    public int setDepth(Integer depth) {
        try {
            setDepth(depth.intValue());
        } catch (Exception e) {
            setDepthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthError;
    } // method setDepth

    /**
     * Set the 'depth' class variable
     * @param  depth (Float)
     */
    public int setDepth(Float depth) {
        try {
            if (depth.floatValue() == FLOATNULL) {
                setDepth(INTNULL);
            } else {
                setDepth(depth.intValue());
            } // if (depth.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDepthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthError;
    } // method setDepth

    /**
     * Set the 'depth' class variable
     * @param  depth (String)
     */
    public int setDepth(String depth) {
        try {
            setDepth(new Integer(depth).intValue());
        } catch (Exception e) {
            setDepthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthError;
    } // method setDepth

    /**
     * Called when an exception has occured
     * @param  depth (String)
     */
    private void setDepthError (int depth, Exception e, int error) {
        this.depth = depth;
        depthErrorMessage = e.toString();
        depthError = error;
    } // method setDepthError


    /**
     * Set the 'minValue' class variable
     * @param  minValue (float)
     */
    public int setMinValue(float minValue) {
        try {
            if ( ((minValue == FLOATNULL) || 
                  (minValue == FLOATNULL2)) ||
                !((minValue < MIN_VALUE_MN) ||
                  (minValue > MIN_VALUE_MX)) ) {
                this.minValue = minValue;
                minValueError = ERROR_NORMAL;
            } else {
                throw minValueOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMinValueError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return minValueError;
    } // method setMinValue

    /**
     * Set the 'minValue' class variable
     * @param  minValue (Integer)
     */
    public int setMinValue(Integer minValue) {
        try {
            setMinValue(minValue.floatValue());
        } catch (Exception e) {
            setMinValueError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return minValueError;
    } // method setMinValue

    /**
     * Set the 'minValue' class variable
     * @param  minValue (Float)
     */
    public int setMinValue(Float minValue) {
        try {
            setMinValue(minValue.floatValue());
        } catch (Exception e) {
            setMinValueError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return minValueError;
    } // method setMinValue

    /**
     * Set the 'minValue' class variable
     * @param  minValue (String)
     */
    public int setMinValue(String minValue) {
        try {
            setMinValue(new Float(minValue).floatValue());
        } catch (Exception e) {
            setMinValueError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return minValueError;
    } // method setMinValue

    /**
     * Called when an exception has occured
     * @param  minValue (String)
     */
    private void setMinValueError (float minValue, Exception e, int error) {
        this.minValue = minValue;
        minValueErrorMessage = e.toString();
        minValueError = error;
    } // method setMinValueError


    /**
     * Set the 'maxValue' class variable
     * @param  maxValue (float)
     */
    public int setMaxValue(float maxValue) {
        try {
            if ( ((maxValue == FLOATNULL) || 
                  (maxValue == FLOATNULL2)) ||
                !((maxValue < MAX_VALUE_MN) ||
                  (maxValue > MAX_VALUE_MX)) ) {
                this.maxValue = maxValue;
                maxValueError = ERROR_NORMAL;
            } else {
                throw maxValueOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMaxValueError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return maxValueError;
    } // method setMaxValue

    /**
     * Set the 'maxValue' class variable
     * @param  maxValue (Integer)
     */
    public int setMaxValue(Integer maxValue) {
        try {
            setMaxValue(maxValue.floatValue());
        } catch (Exception e) {
            setMaxValueError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return maxValueError;
    } // method setMaxValue

    /**
     * Set the 'maxValue' class variable
     * @param  maxValue (Float)
     */
    public int setMaxValue(Float maxValue) {
        try {
            setMaxValue(maxValue.floatValue());
        } catch (Exception e) {
            setMaxValueError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return maxValueError;
    } // method setMaxValue

    /**
     * Set the 'maxValue' class variable
     * @param  maxValue (String)
     */
    public int setMaxValue(String maxValue) {
        try {
            setMaxValue(new Float(maxValue).floatValue());
        } catch (Exception e) {
            setMaxValueError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return maxValueError;
    } // method setMaxValue

    /**
     * Called when an exception has occured
     * @param  maxValue (String)
     */
    private void setMaxValueError (float maxValue, Exception e, int error) {
        this.maxValue = maxValue;
        maxValueErrorMessage = e.toString();
        maxValueError = error;
    } // method setMaxValueError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'basin' class variable
     * @return basin (int)
     */
    public int getBasin() {
        return basin;
    } // method getBasin

    /**
     * Return the 'basin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getBasin methods
     * @return basin (String)
     */
    public String getBasin(String s) {
        return ((basin != INTNULL) ? new Integer(basin).toString() : "");
    } // method getBasin


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
     * Return the 'depth' class variable
     * @return depth (int)
     */
    public int getDepth() {
        return depth;
    } // method getDepth

    /**
     * Return the 'depth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDepth methods
     * @return depth (String)
     */
    public String getDepth(String s) {
        return ((depth != INTNULL) ? new Integer(depth).toString() : "");
    } // method getDepth


    /**
     * Return the 'minValue' class variable
     * @return minValue (float)
     */
    public float getMinValue() {
        return minValue;
    } // method getMinValue

    /**
     * Return the 'minValue' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMinValue methods
     * @return minValue (String)
     */
    public String getMinValue(String s) {
        return ((minValue != FLOATNULL) ? new Float(minValue).toString() : "");
    } // method getMinValue


    /**
     * Return the 'maxValue' class variable
     * @return maxValue (float)
     */
    public float getMaxValue() {
        return maxValue;
    } // method getMaxValue

    /**
     * Return the 'maxValue' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMaxValue methods
     * @return maxValue (String)
     */
    public String getMaxValue(String s) {
        return ((maxValue != FLOATNULL) ? new Float(maxValue).toString() : "");
    } // method getMaxValue


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
        if ((basin == INTNULL) &&
            (parameterCode == INTNULL) &&
            (depth == INTNULL) &&
            (minValue == FLOATNULL) &&
            (maxValue == FLOATNULL)) {
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
        int sumError = basinError +
            parameterCodeError +
            depthError +
            minValueError +
            maxValueError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the basin instance variable
     * @return errorcode (int)
     */
    public int getBasinError() {
        return basinError;
    } // method getBasinError

    /**
     * Gets the errorMessage for the basin instance variable
     * @return errorMessage (String)
     */
    public String getBasinErrorMessage() {
        return basinErrorMessage;
    } // method getBasinErrorMessage


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
     * Gets the errorcode for the depth instance variable
     * @return errorcode (int)
     */
    public int getDepthError() {
        return depthError;
    } // method getDepthError

    /**
     * Gets the errorMessage for the depth instance variable
     * @return errorMessage (String)
     */
    public String getDepthErrorMessage() {
        return depthErrorMessage;
    } // method getDepthErrorMessage


    /**
     * Gets the errorcode for the minValue instance variable
     * @return errorcode (int)
     */
    public int getMinValueError() {
        return minValueError;
    } // method getMinValueError

    /**
     * Gets the errorMessage for the minValue instance variable
     * @return errorMessage (String)
     */
    public String getMinValueErrorMessage() {
        return minValueErrorMessage;
    } // method getMinValueErrorMessage


    /**
     * Gets the errorcode for the maxValue instance variable
     * @return errorcode (int)
     */
    public int getMaxValueError() {
        return maxValueError;
    } // method getMaxValueError

    /**
     * Gets the errorMessage for the maxValue instance variable
     * @return errorMessage (String)
     */
    public String getMaxValueErrorMessage() {
        return maxValueErrorMessage;
    } // method getMaxValueErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnQcBroadChecks. e.g.<pre>
     * MrnQcBroadChecks qcBroadChecks = new MrnQcBroadChecks(<val1>);
     * MrnQcBroadChecks qcBroadChecksArray[] = qcBroadChecks.get();</pre>
     * will get the MrnQcBroadChecks record where basin = <val1>.
     * @return Array of MrnQcBroadChecks (MrnQcBroadChecks[])
     */
    public MrnQcBroadChecks[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnQcBroadChecks qcBroadChecksArray[] = 
     *     new MrnQcBroadChecks().get(MrnQcBroadChecks.BASIN+"=<val1>");</pre>
     * will get the MrnQcBroadChecks record where basin = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnQcBroadChecks (MrnQcBroadChecks[])
     */
    public MrnQcBroadChecks[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnQcBroadChecks qcBroadChecksArray[] = 
     *     new MrnQcBroadChecks().get("1=1",qcBroadChecks.BASIN);</pre>
     * will get the all the MrnQcBroadChecks records, and order them by basin.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnQcBroadChecks (MrnQcBroadChecks[])
     */
    public MrnQcBroadChecks[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnQcBroadChecks.BASIN,MrnQcBroadChecks.PARAMETER_CODE;
     * String where = MrnQcBroadChecks.BASIN + "=<val1";
     * String order = MrnQcBroadChecks.BASIN;
     * MrnQcBroadChecks qcBroadChecksArray[] = 
     *     new MrnQcBroadChecks().get(columns, where, order);</pre>
     * will get the basin and parameterCode colums of all MrnQcBroadChecks records,
     * where basin = <val1>, and order them by basin.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnQcBroadChecks (MrnQcBroadChecks[])
     */
    public MrnQcBroadChecks[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnQcBroadChecks.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnQcBroadChecks[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int basinCol         = db.getColNumber(BASIN);
        int parameterCodeCol = db.getColNumber(PARAMETER_CODE);
        int depthCol         = db.getColNumber(DEPTH);
        int minValueCol      = db.getColNumber(MIN_VALUE);
        int maxValueCol      = db.getColNumber(MAX_VALUE);
        MrnQcBroadChecks[] cArray = new MrnQcBroadChecks[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnQcBroadChecks();
            if (basinCol != -1)
                cArray[i].setBasin        ((String) row.elementAt(basinCol));
            if (parameterCodeCol != -1)
                cArray[i].setParameterCode((String) row.elementAt(parameterCodeCol));
            if (depthCol != -1)
                cArray[i].setDepth        ((String) row.elementAt(depthCol));
            if (minValueCol != -1)
                cArray[i].setMinValue     ((String) row.elementAt(minValueCol));
            if (maxValueCol != -1)
                cArray[i].setMaxValue     ((String) row.elementAt(maxValueCol));
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
     *     new MrnQcBroadChecks(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>).put();</pre>
     * will insert a record with:
     *     basin         = <val1>,
     *     parameterCode = <val2>,
     *     depth         = <val3>,
     *     minValue      = <val4>,
     *     maxValue      = <val5>.
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
     * Boolean success = new MrnQcBroadChecks(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where parameterCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnQcBroadChecks qcBroadChecks = new MrnQcBroadChecks();
     * success = qcBroadChecks.del(MrnQcBroadChecks.BASIN+"=<val1>");</pre>
     * will delete all records where basin = <val1>.
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
     * update are taken from the MrnQcBroadChecks argument, .e.g.<pre>
     * Boolean success
     * MrnQcBroadChecks updMrnQcBroadChecks = new MrnQcBroadChecks();
     * updMrnQcBroadChecks.setParameterCode(<val2>);
     * MrnQcBroadChecks whereMrnQcBroadChecks = new MrnQcBroadChecks(<val1>);
     * success = whereMrnQcBroadChecks.upd(updMrnQcBroadChecks);</pre>
     * will update ParameterCode to <val2> for all records where 
     * basin = <val1>.
     * @param qcBroadChecks  A MrnQcBroadChecks variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnQcBroadChecks qcBroadChecks) {
        return db.update (TABLE, createColVals(qcBroadChecks), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnQcBroadChecks updMrnQcBroadChecks = new MrnQcBroadChecks();
     * updMrnQcBroadChecks.setParameterCode(<val2>);
     * MrnQcBroadChecks whereMrnQcBroadChecks = new MrnQcBroadChecks();
     * success = whereMrnQcBroadChecks.upd(
     *     updMrnQcBroadChecks, MrnQcBroadChecks.BASIN+"=<val1>");</pre>
     * will update ParameterCode to <val2> for all records where 
     * basin = <val1>.
     * @param  updMrnQcBroadChecks  A MrnQcBroadChecks variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnQcBroadChecks qcBroadChecks, String where) {
        return db.update (TABLE, createColVals(qcBroadChecks), where);
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
        if (getBasin() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + BASIN + "=" + getBasin("");
        } // if getBasin
        if (getParameterCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PARAMETER_CODE + "=" + getParameterCode("");
        } // if getParameterCode
        if (getDepth() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEPTH + "=" + getDepth("");
        } // if getDepth
        if (getMinValue() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MIN_VALUE + "=" + getMinValue("");
        } // if getMinValue
        if (getMaxValue() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MAX_VALUE + "=" + getMaxValue("");
        } // if getMaxValue
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnQcBroadChecks aVar) {
        String colVals = "";
        if (aVar.getBasin() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += BASIN +"=";
            colVals += (aVar.getBasin() == INTNULL2 ?
                "null" : aVar.getBasin(""));
        } // if aVar.getBasin
        if (aVar.getParameterCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PARAMETER_CODE +"=";
            colVals += (aVar.getParameterCode() == INTNULL2 ?
                "null" : aVar.getParameterCode(""));
        } // if aVar.getParameterCode
        if (aVar.getDepth() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEPTH +"=";
            colVals += (aVar.getDepth() == INTNULL2 ?
                "null" : aVar.getDepth(""));
        } // if aVar.getDepth
        if (aVar.getMinValue() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MIN_VALUE +"=";
            colVals += (aVar.getMinValue() == FLOATNULL2 ?
                "null" : aVar.getMinValue(""));
        } // if aVar.getMinValue
        if (aVar.getMaxValue() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MAX_VALUE +"=";
            colVals += (aVar.getMaxValue() == FLOATNULL2 ?
                "null" : aVar.getMaxValue(""));
        } // if aVar.getMaxValue
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = BASIN;
        if (getParameterCode() != INTNULL) {
            columns = columns + "," + PARAMETER_CODE;
        } // if getParameterCode
        if (getDepth() != INTNULL) {
            columns = columns + "," + DEPTH;
        } // if getDepth
        if (getMinValue() != FLOATNULL) {
            columns = columns + "," + MIN_VALUE;
        } // if getMinValue
        if (getMaxValue() != FLOATNULL) {
            columns = columns + "," + MAX_VALUE;
        } // if getMaxValue
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getBasin("");
        if (getParameterCode() != INTNULL) {
            values  = values  + "," + getParameterCode("");
        } // if getParameterCode
        if (getDepth() != INTNULL) {
            values  = values  + "," + getDepth("");
        } // if getDepth
        if (getMinValue() != FLOATNULL) {
            values  = values  + "," + getMinValue("");
        } // if getMinValue
        if (getMaxValue() != FLOATNULL) {
            values  = values  + "," + getMaxValue("");
        } // if getMaxValue
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getBasin("")         + "|" +
            getParameterCode("") + "|" +
            getDepth("")         + "|" +
            getMinValue("")      + "|" +
            getMaxValue("")      + "|";
    } // method toString

} // class MrnQcBroadChecks
