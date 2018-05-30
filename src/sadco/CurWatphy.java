package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the CUR_WATPHY table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class CurWatphy extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "CUR_WATPHY";
    /** The depthCode field name */
    public static final String DEPTH_CODE = "DEPTH_CODE";
    /** The dataCode field name */
    public static final String DATA_CODE = "DATA_CODE";
    /** The ph field name */
    public static final String PH = "PH";
    /** The salinity field name */
    public static final String SALINITY = "SALINITY";
    /** The disOxy field name */
    public static final String DIS_OXY = "DIS_OXY";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       depthCode;
    private int       dataCode;
    private float     ph;
    private float     salinity;
    private float     disOxy;

    /** The error variables  */
    private int depthCodeError = ERROR_NORMAL;
    private int dataCodeError  = ERROR_NORMAL;
    private int phError        = ERROR_NORMAL;
    private int salinityError  = ERROR_NORMAL;
    private int disOxyError    = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String depthCodeErrorMessage = "";
    private String dataCodeErrorMessage  = "";
    private String phErrorMessage        = "";
    private String salinityErrorMessage  = "";
    private String disOxyErrorMessage    = "";

    /** The min-max constants for all numerics */
    public static final int       DEPTH_CODE_MN = INTMIN;
    public static final int       DEPTH_CODE_MX = INTMAX;
    public static final int       DATA_CODE_MN = INTMIN;
    public static final int       DATA_CODE_MX = INTMAX;
    public static final float     PH_MN = FLOATMIN;
    public static final float     PH_MX = FLOATMAX;
    public static final float     SALINITY_MN = FLOATMIN;
    public static final float     SALINITY_MX = FLOATMAX;
    public static final float     DIS_OXY_MN = FLOATMIN;
    public static final float     DIS_OXY_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception depthCodeOutOfBoundsException =
        new Exception ("'depthCode' out of bounds: " +
            DEPTH_CODE_MN + " - " + DEPTH_CODE_MX);
    Exception dataCodeOutOfBoundsException =
        new Exception ("'dataCode' out of bounds: " +
            DATA_CODE_MN + " - " + DATA_CODE_MX);
    Exception phOutOfBoundsException =
        new Exception ("'ph' out of bounds: " +
            PH_MN + " - " + PH_MX);
    Exception salinityOutOfBoundsException =
        new Exception ("'salinity' out of bounds: " +
            SALINITY_MN + " - " + SALINITY_MX);
    Exception disOxyOutOfBoundsException =
        new Exception ("'disOxy' out of bounds: " +
            DIS_OXY_MN + " - " + DIS_OXY_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public CurWatphy() {
        clearVars();
        if (dbg) System.out.println ("<br>in CurWatphy constructor 1"); // debug
    } // CurWatphy constructor

    /**
     * Instantiate a CurWatphy object and initialize the instance variables.
     * @param depthCode  The depthCode (int)
     */
    public CurWatphy(
            int depthCode) {
        this();
        setDepthCode (depthCode);
        if (dbg) System.out.println ("<br>in CurWatphy constructor 2"); // debug
    } // CurWatphy constructor

    /**
     * Instantiate a CurWatphy object and initialize the instance variables.
     * @param depthCode  The depthCode (int)
     * @param dataCode   The dataCode  (int)
     * @param ph         The ph        (float)
     * @param salinity   The salinity  (float)
     * @param disOxy     The disOxy    (float)
     */
    public CurWatphy(
            int   depthCode,
            int   dataCode,
            float ph,
            float salinity,
            float disOxy) {
        this();
        setDepthCode (depthCode);
        setDataCode  (dataCode );
        setPh        (ph       );
        setSalinity  (salinity );
        setDisOxy    (disOxy   );
        if (dbg) System.out.println ("<br>in CurWatphy constructor 3"); // debug
    } // CurWatphy constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setDepthCode (INTNULL  );
        setDataCode  (INTNULL  );
        setPh        (FLOATNULL);
        setSalinity  (FLOATNULL);
        setDisOxy    (FLOATNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (int)
     */
    public int setDepthCode(int depthCode) {
        try {
            if ( ((depthCode == INTNULL) ||
                  (depthCode == INTNULL2)) ||
                !((depthCode < DEPTH_CODE_MN) ||
                  (depthCode > DEPTH_CODE_MX)) ) {
                this.depthCode = depthCode;
                depthCodeError = ERROR_NORMAL;
            } else {
                throw depthCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (Integer)
     */
    public int setDepthCode(Integer depthCode) {
        try {
            setDepthCode(depthCode.intValue());
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (Float)
     */
    public int setDepthCode(Float depthCode) {
        try {
            if (depthCode.floatValue() == FLOATNULL) {
                setDepthCode(INTNULL);
            } else {
                setDepthCode(depthCode.intValue());
            } // if (depthCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (String)
     */
    public int setDepthCode(String depthCode) {
        try {
            setDepthCode(new Integer(depthCode).intValue());
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Called when an exception has occured
     * @param  depthCode (String)
     */
    private void setDepthCodeError (int depthCode, Exception e, int error) {
        this.depthCode = depthCode;
        depthCodeErrorMessage = e.toString();
        depthCodeError = error;
    } // method setDepthCodeError


    /**
     * Set the 'dataCode' class variable
     * @param  dataCode (int)
     */
    public int setDataCode(int dataCode) {
        try {
            if ( ((dataCode == INTNULL) ||
                  (dataCode == INTNULL2)) ||
                !((dataCode < DATA_CODE_MN) ||
                  (dataCode > DATA_CODE_MX)) ) {
                this.dataCode = dataCode;
                dataCodeError = ERROR_NORMAL;
            } else {
                throw dataCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDataCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return dataCodeError;
    } // method setDataCode

    /**
     * Set the 'dataCode' class variable
     * @param  dataCode (Integer)
     */
    public int setDataCode(Integer dataCode) {
        try {
            setDataCode(dataCode.intValue());
        } catch (Exception e) {
            setDataCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataCodeError;
    } // method setDataCode

    /**
     * Set the 'dataCode' class variable
     * @param  dataCode (Float)
     */
    public int setDataCode(Float dataCode) {
        try {
            if (dataCode.floatValue() == FLOATNULL) {
                setDataCode(INTNULL);
            } else {
                setDataCode(dataCode.intValue());
            } // if (dataCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDataCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataCodeError;
    } // method setDataCode

    /**
     * Set the 'dataCode' class variable
     * @param  dataCode (String)
     */
    public int setDataCode(String dataCode) {
        try {
            setDataCode(new Integer(dataCode).intValue());
        } catch (Exception e) {
            setDataCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataCodeError;
    } // method setDataCode

    /**
     * Called when an exception has occured
     * @param  dataCode (String)
     */
    private void setDataCodeError (int dataCode, Exception e, int error) {
        this.dataCode = dataCode;
        dataCodeErrorMessage = e.toString();
        dataCodeError = error;
    } // method setDataCodeError


    /**
     * Set the 'ph' class variable
     * @param  ph (float)
     */
    public int setPh(float ph) {
        try {
            if ( ((ph == FLOATNULL) ||
                  (ph == FLOATNULL2)) ||
                !((ph < PH_MN) ||
                  (ph > PH_MX)) ) {
                this.ph = ph;
                phError = ERROR_NORMAL;
            } else {
                throw phOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPhError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return phError;
    } // method setPh

    /**
     * Set the 'ph' class variable
     * @param  ph (Integer)
     */
    public int setPh(Integer ph) {
        try {
            setPh(ph.floatValue());
        } catch (Exception e) {
            setPhError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phError;
    } // method setPh

    /**
     * Set the 'ph' class variable
     * @param  ph (Float)
     */
    public int setPh(Float ph) {
        try {
            setPh(ph.floatValue());
        } catch (Exception e) {
            setPhError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phError;
    } // method setPh

    /**
     * Set the 'ph' class variable
     * @param  ph (String)
     */
    public int setPh(String ph) {
        try {
            setPh(new Float(ph).floatValue());
        } catch (Exception e) {
            setPhError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phError;
    } // method setPh

    /**
     * Called when an exception has occured
     * @param  ph (String)
     */
    private void setPhError (float ph, Exception e, int error) {
        this.ph = ph;
        phErrorMessage = e.toString();
        phError = error;
    } // method setPhError


    /**
     * Set the 'salinity' class variable
     * @param  salinity (float)
     */
    public int setSalinity(float salinity) {
        try {
            if ( ((salinity == FLOATNULL) ||
                  (salinity == FLOATNULL2)) ||
                !((salinity < SALINITY_MN) ||
                  (salinity > SALINITY_MX)) ) {
                this.salinity = salinity;
                salinityError = ERROR_NORMAL;
            } else {
                throw salinityOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSalinityError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return salinityError;
    } // method setSalinity

    /**
     * Set the 'salinity' class variable
     * @param  salinity (Integer)
     */
    public int setSalinity(Integer salinity) {
        try {
            setSalinity(salinity.floatValue());
        } catch (Exception e) {
            setSalinityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return salinityError;
    } // method setSalinity

    /**
     * Set the 'salinity' class variable
     * @param  salinity (Float)
     */
    public int setSalinity(Float salinity) {
        try {
            setSalinity(salinity.floatValue());
        } catch (Exception e) {
            setSalinityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return salinityError;
    } // method setSalinity

    /**
     * Set the 'salinity' class variable
     * @param  salinity (String)
     */
    public int setSalinity(String salinity) {
        try {
            setSalinity(new Float(salinity).floatValue());
        } catch (Exception e) {
            setSalinityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return salinityError;
    } // method setSalinity

    /**
     * Called when an exception has occured
     * @param  salinity (String)
     */
    private void setSalinityError (float salinity, Exception e, int error) {
        this.salinity = salinity;
        salinityErrorMessage = e.toString();
        salinityError = error;
    } // method setSalinityError


    /**
     * Set the 'disOxy' class variable
     * @param  disOxy (float)
     */
    public int setDisOxy(float disOxy) {
        try {
            if ( ((disOxy == FLOATNULL) ||
                  (disOxy == FLOATNULL2)) ||
                !((disOxy < DIS_OXY_MN) ||
                  (disOxy > DIS_OXY_MX)) ) {
                this.disOxy = disOxy;
                disOxyError = ERROR_NORMAL;
            } else {
                throw disOxyOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDisOxyError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return disOxyError;
    } // method setDisOxy

    /**
     * Set the 'disOxy' class variable
     * @param  disOxy (Integer)
     */
    public int setDisOxy(Integer disOxy) {
        try {
            setDisOxy(disOxy.floatValue());
        } catch (Exception e) {
            setDisOxyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return disOxyError;
    } // method setDisOxy

    /**
     * Set the 'disOxy' class variable
     * @param  disOxy (Float)
     */
    public int setDisOxy(Float disOxy) {
        try {
            setDisOxy(disOxy.floatValue());
        } catch (Exception e) {
            setDisOxyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return disOxyError;
    } // method setDisOxy

    /**
     * Set the 'disOxy' class variable
     * @param  disOxy (String)
     */
    public int setDisOxy(String disOxy) {
        try {
            setDisOxy(new Float(disOxy).floatValue());
        } catch (Exception e) {
            setDisOxyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return disOxyError;
    } // method setDisOxy

    /**
     * Called when an exception has occured
     * @param  disOxy (String)
     */
    private void setDisOxyError (float disOxy, Exception e, int error) {
        this.disOxy = disOxy;
        disOxyErrorMessage = e.toString();
        disOxyError = error;
    } // method setDisOxyError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'depthCode' class variable
     * @return depthCode (int)
     */
    public int getDepthCode() {
        return depthCode;
    } // method getDepthCode

    /**
     * Return the 'depthCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDepthCode methods
     * @return depthCode (String)
     */
    public String getDepthCode(String s) {
        return ((depthCode != INTNULL) ? new Integer(depthCode).toString() : "");
    } // method getDepthCode


    /**
     * Return the 'dataCode' class variable
     * @return dataCode (int)
     */
    public int getDataCode() {
        return dataCode;
    } // method getDataCode

    /**
     * Return the 'dataCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataCode methods
     * @return dataCode (String)
     */
    public String getDataCode(String s) {
        return ((dataCode != INTNULL) ? new Integer(dataCode).toString() : "");
    } // method getDataCode


    /**
     * Return the 'ph' class variable
     * @return ph (float)
     */
    public float getPh() {
        return ph;
    } // method getPh

    /**
     * Return the 'ph' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPh methods
     * @return ph (String)
     */
    public String getPh(String s) {
        return ((ph != FLOATNULL) ? new Float(ph).toString() : "");
    } // method getPh


    /**
     * Return the 'salinity' class variable
     * @return salinity (float)
     */
    public float getSalinity() {
        return salinity;
    } // method getSalinity

    /**
     * Return the 'salinity' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSalinity methods
     * @return salinity (String)
     */
    public String getSalinity(String s) {
        return ((salinity != FLOATNULL) ? new Float(salinity).toString() : "");
    } // method getSalinity


    /**
     * Return the 'disOxy' class variable
     * @return disOxy (float)
     */
    public float getDisOxy() {
        return disOxy;
    } // method getDisOxy

    /**
     * Return the 'disOxy' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDisOxy methods
     * @return disOxy (String)
     */
    public String getDisOxy(String s) {
        return ((disOxy != FLOATNULL) ? new Float(disOxy).toString() : "");
    } // method getDisOxy


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
        if ((depthCode == INTNULL) &&
            (dataCode == INTNULL) &&
            (ph == FLOATNULL) &&
            (salinity == FLOATNULL) &&
            (disOxy == FLOATNULL)) {
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
        int sumError = depthCodeError +
            dataCodeError +
            phError +
            salinityError +
            disOxyError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the depthCode instance variable
     * @return errorcode (int)
     */
    public int getDepthCodeError() {
        return depthCodeError;
    } // method getDepthCodeError

    /**
     * Gets the errorMessage for the depthCode instance variable
     * @return errorMessage (String)
     */
    public String getDepthCodeErrorMessage() {
        return depthCodeErrorMessage;
    } // method getDepthCodeErrorMessage


    /**
     * Gets the errorcode for the dataCode instance variable
     * @return errorcode (int)
     */
    public int getDataCodeError() {
        return dataCodeError;
    } // method getDataCodeError

    /**
     * Gets the errorMessage for the dataCode instance variable
     * @return errorMessage (String)
     */
    public String getDataCodeErrorMessage() {
        return dataCodeErrorMessage;
    } // method getDataCodeErrorMessage


    /**
     * Gets the errorcode for the ph instance variable
     * @return errorcode (int)
     */
    public int getPhError() {
        return phError;
    } // method getPhError

    /**
     * Gets the errorMessage for the ph instance variable
     * @return errorMessage (String)
     */
    public String getPhErrorMessage() {
        return phErrorMessage;
    } // method getPhErrorMessage


    /**
     * Gets the errorcode for the salinity instance variable
     * @return errorcode (int)
     */
    public int getSalinityError() {
        return salinityError;
    } // method getSalinityError

    /**
     * Gets the errorMessage for the salinity instance variable
     * @return errorMessage (String)
     */
    public String getSalinityErrorMessage() {
        return salinityErrorMessage;
    } // method getSalinityErrorMessage


    /**
     * Gets the errorcode for the disOxy instance variable
     * @return errorcode (int)
     */
    public int getDisOxyError() {
        return disOxyError;
    } // method getDisOxyError

    /**
     * Gets the errorMessage for the disOxy instance variable
     * @return errorMessage (String)
     */
    public String getDisOxyErrorMessage() {
        return disOxyErrorMessage;
    } // method getDisOxyErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of CurWatphy. e.g.<pre>
     * CurWatphy curWatphy = new CurWatphy(<val1>);
     * CurWatphy curWatphyArray[] = curWatphy.get();</pre>
     * will get the CurWatphy record where depthCode = <val1>.
     * @return Array of CurWatphy (CurWatphy[])
     */
    public CurWatphy[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * CurWatphy curWatphyArray[] =
     *     new CurWatphy().get(CurWatphy.DEPTH_CODE+"=<val1>");</pre>
     * will get the CurWatphy record where depthCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of CurWatphy (CurWatphy[])
     */
    public CurWatphy[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * CurWatphy curWatphyArray[] =
     *     new CurWatphy().get("1=1",curWatphy.DEPTH_CODE);</pre>
     * will get the all the CurWatphy records, and order them by depthCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurWatphy (CurWatphy[])
     */
    public CurWatphy[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = CurWatphy.DEPTH_CODE,CurWatphy.DATA_CODE;
     * String where = CurWatphy.DEPTH_CODE + "=<val1";
     * String order = CurWatphy.DEPTH_CODE;
     * CurWatphy curWatphyArray[] =
     *     new CurWatphy().get(columns, where, order);</pre>
     * will get the depthCode and dataCode colums of all CurWatphy records,
     * where depthCode = <val1>, and order them by depthCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurWatphy (CurWatphy[])
     */
    public CurWatphy[] get (String fields, String where, String order) {
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
     * and transforms it into an array of CurWatphy.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private CurWatphy[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int depthCodeCol = db.getColNumber(DEPTH_CODE);
        int dataCodeCol  = db.getColNumber(DATA_CODE);
        int phCol        = db.getColNumber(PH);
        int salinityCol  = db.getColNumber(SALINITY);
        int disOxyCol    = db.getColNumber(DIS_OXY);
        CurWatphy[] cArray = new CurWatphy[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new CurWatphy();
            if (depthCodeCol != -1)
                cArray[i].setDepthCode((String) row.elementAt(depthCodeCol));
            if (dataCodeCol != -1)
                cArray[i].setDataCode ((String) row.elementAt(dataCodeCol));
            if (phCol != -1)
                cArray[i].setPh       ((String) row.elementAt(phCol));
            if (salinityCol != -1)
                cArray[i].setSalinity ((String) row.elementAt(salinityCol));
            if (disOxyCol != -1)
                cArray[i].setDisOxy   ((String) row.elementAt(disOxyCol));
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
     *     new CurWatphy(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>).put();</pre>
     * will insert a record with:
     *     depthCode = <val1>,
     *     dataCode  = <val2>,
     *     ph        = <val3>,
     *     salinity  = <val4>,
     *     disOxy    = <val5>.
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
     * Boolean success = new CurWatphy(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where dataCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurWatphy curWatphy = new CurWatphy();
     * success = curWatphy.del(CurWatphy.DEPTH_CODE+"=<val1>");</pre>
     * will delete all records where depthCode = <val1>.
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
     * update are taken from the CurWatphy argument, .e.g.<pre>
     * Boolean success
     * CurWatphy updCurWatphy = new CurWatphy();
     * updCurWatphy.setDataCode(<val2>);
     * CurWatphy whereCurWatphy = new CurWatphy(<val1>);
     * success = whereCurWatphy.upd(updCurWatphy);</pre>
     * will update DataCode to <val2> for all records where
     * depthCode = <val1>.
     * @param  curWatphy  A CurWatphy variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(CurWatphy curWatphy) {
        return db.update (TABLE, createColVals(curWatphy), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurWatphy updCurWatphy = new CurWatphy();
     * updCurWatphy.setDataCode(<val2>);
     * CurWatphy whereCurWatphy = new CurWatphy();
     * success = whereCurWatphy.upd(
     *     updCurWatphy, CurWatphy.DEPTH_CODE+"=<val1>");</pre>
     * will update DataCode to <val2> for all records where
     * depthCode = <val1>.
     * @param  curWatphy  A CurWatphy variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(CurWatphy curWatphy, String where) {
        return db.update (TABLE, createColVals(curWatphy), where);
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
        if (getDepthCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEPTH_CODE + "=" + getDepthCode("");
        } // if getDepthCode
        if (getDataCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATA_CODE + "=" + getDataCode("");
        } // if getDataCode
        if (getPh() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PH + "=" + getPh("");
        } // if getPh
        if (getSalinity() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SALINITY + "=" + getSalinity("");
        } // if getSalinity
        if (getDisOxy() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DIS_OXY + "=" + getDisOxy("");
        } // if getDisOxy
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(CurWatphy aVar) {
        String colVals = "";
        if (aVar.getDepthCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEPTH_CODE +"=";
            colVals += (aVar.getDepthCode() == INTNULL2 ?
                "null" : aVar.getDepthCode(""));
        } // if aVar.getDepthCode
        if (aVar.getDataCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATA_CODE +"=";
            colVals += (aVar.getDataCode() == INTNULL2 ?
                "null" : aVar.getDataCode(""));
        } // if aVar.getDataCode
        if (aVar.getPh() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PH +"=";
            colVals += (aVar.getPh() == FLOATNULL2 ?
                "null" : aVar.getPh(""));
        } // if aVar.getPh
        if (aVar.getSalinity() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SALINITY +"=";
            colVals += (aVar.getSalinity() == FLOATNULL2 ?
                "null" : aVar.getSalinity(""));
        } // if aVar.getSalinity
        if (aVar.getDisOxy() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DIS_OXY +"=";
            colVals += (aVar.getDisOxy() == FLOATNULL2 ?
                "null" : aVar.getDisOxy(""));
        } // if aVar.getDisOxy
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = DEPTH_CODE;
        if (getDataCode() != INTNULL) {
            columns = columns + "," + DATA_CODE;
        } // if getDataCode
        if (getPh() != FLOATNULL) {
            columns = columns + "," + PH;
        } // if getPh
        if (getSalinity() != FLOATNULL) {
            columns = columns + "," + SALINITY;
        } // if getSalinity
        if (getDisOxy() != FLOATNULL) {
            columns = columns + "," + DIS_OXY;
        } // if getDisOxy
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getDepthCode("");
        if (getDataCode() != INTNULL) {
            values  = values  + "," + getDataCode("");
        } // if getDataCode
        if (getPh() != FLOATNULL) {
            values  = values  + "," + getPh("");
        } // if getPh
        if (getSalinity() != FLOATNULL) {
            values  = values  + "," + getSalinity("");
        } // if getSalinity
        if (getDisOxy() != FLOATNULL) {
            values  = values  + "," + getDisOxy("");
        } // if getDisOxy
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getDepthCode("") + "|" +
            getDataCode("")  + "|" +
            getPh("")        + "|" +
            getSalinity("")  + "|" +
            getDisOxy("")    + "|";
    } // method toString

} // class CurWatphy
