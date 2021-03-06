package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the PLATAX table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 090108 - SIT Group
 * @version
 * 090108 - GenTableClassB class - create class<br>
 */
public class MrnPlatax extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "PLATAX";
    /** The plaphyCode field name */
    public static final String PLAPHY_CODE = "PLAPHY_CODE";
    /** The taxcod field name */
    public static final String TAXCOD = "TAXCOD";
    /** The taxlev field name */
    public static final String TAXLEV = "TAXLEV";
    /** The taxcnt field name */
    public static final String TAXCNT = "TAXCNT";
    /** The taxcom field name */
    public static final String TAXCOM = "TAXCOM";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       plaphyCode;
    private String    taxcod;
    private int       taxlev;
    private int       taxcnt;
    private String    taxcom;

    /** The error variables  */
    private int plaphyCodeError = ERROR_NORMAL;
    private int taxcodError     = ERROR_NORMAL;
    private int taxlevError     = ERROR_NORMAL;
    private int taxcntError     = ERROR_NORMAL;
    private int taxcomError     = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String plaphyCodeErrorMessage = "";
    private String taxcodErrorMessage     = "";
    private String taxlevErrorMessage     = "";
    private String taxcntErrorMessage     = "";
    private String taxcomErrorMessage     = "";

    /** The min-max constants for all numerics */
    public static final int       PLAPHY_CODE_MN = INTMIN;
    public static final int       PLAPHY_CODE_MX = INTMAX;
    public static final int       TAXLEV_MN = INTMIN;
    public static final int       TAXLEV_MX = INTMAX;
    public static final int       TAXCNT_MN = INTMIN;
    public static final int       TAXCNT_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception plaphyCodeOutOfBoundsException =
        new Exception ("'plaphyCode' out of bounds: " +
            PLAPHY_CODE_MN + " - " + PLAPHY_CODE_MX);
    Exception taxlevOutOfBoundsException =
        new Exception ("'taxlev' out of bounds: " +
            TAXLEV_MN + " - " + TAXLEV_MX);
    Exception taxcntOutOfBoundsException =
        new Exception ("'taxcnt' out of bounds: " +
            TAXCNT_MN + " - " + TAXCNT_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnPlatax() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnPlatax constructor 1"); // debug
    } // MrnPlatax constructor

    /**
     * Instantiate a MrnPlatax object and initialize the instance variables.
     * @param plaphyCode  The plaphyCode (int)
     */
    public MrnPlatax(
            int plaphyCode) {
        this();
        setPlaphyCode (plaphyCode);
        if (dbg) System.out.println ("<br>in MrnPlatax constructor 2"); // debug
    } // MrnPlatax constructor

    /**
     * Instantiate a MrnPlatax object and initialize the instance variables.
     * @param plaphyCode  The plaphyCode (int)
     * @param taxcod      The taxcod     (String)
     * @param taxlev      The taxlev     (int)
     * @param taxcnt      The taxcnt     (int)
     * @param taxcom      The taxcom     (String)
     * @return A MrnPlatax object
     */
    public MrnPlatax(
            int    plaphyCode,
            String taxcod,
            int    taxlev,
            int    taxcnt,
            String taxcom) {
        this();
        setPlaphyCode (plaphyCode);
        setTaxcod     (taxcod    );
        setTaxlev     (taxlev    );
        setTaxcnt     (taxcnt    );
        setTaxcom     (taxcom    );
        if (dbg) System.out.println ("<br>in MrnPlatax constructor 3"); // debug
    } // MrnPlatax constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setPlaphyCode (INTNULL );
        setTaxcod     (CHARNULL);
        setTaxlev     (INTNULL );
        setTaxcnt     (INTNULL );
        setTaxcom     (CHARNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'plaphyCode' class variable
     * @param  plaphyCode (int)
     */
    public int setPlaphyCode(int plaphyCode) {
        try {
            if ( ((plaphyCode == INTNULL) || 
                  (plaphyCode == INTNULL2)) ||
                !((plaphyCode < PLAPHY_CODE_MN) ||
                  (plaphyCode > PLAPHY_CODE_MX)) ) {
                this.plaphyCode = plaphyCode;
                plaphyCodeError = ERROR_NORMAL;
            } else {
                throw plaphyCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlaphyCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plaphyCodeError;
    } // method setPlaphyCode

    /**
     * Set the 'plaphyCode' class variable
     * @param  plaphyCode (Integer)
     */
    public int setPlaphyCode(Integer plaphyCode) {
        try {
            setPlaphyCode(plaphyCode.intValue());
        } catch (Exception e) {
            setPlaphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCodeError;
    } // method setPlaphyCode

    /**
     * Set the 'plaphyCode' class variable
     * @param  plaphyCode (Float)
     */
    public int setPlaphyCode(Float plaphyCode) {
        try {
            if (plaphyCode.floatValue() == FLOATNULL) {
                setPlaphyCode(INTNULL);
            } else {
                setPlaphyCode(plaphyCode.intValue());
            } // if (plaphyCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlaphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCodeError;
    } // method setPlaphyCode

    /**
     * Set the 'plaphyCode' class variable
     * @param  plaphyCode (String)
     */
    public int setPlaphyCode(String plaphyCode) {
        try {
            setPlaphyCode(new Integer(plaphyCode).intValue());
        } catch (Exception e) {
            setPlaphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCodeError;
    } // method setPlaphyCode

    /**
     * Called when an exception has occured
     * @param  plaphyCode (String)
     */
    private void setPlaphyCodeError (int plaphyCode, Exception e, int error) {
        this.plaphyCode = plaphyCode;
        plaphyCodeErrorMessage = e.toString();
        plaphyCodeError = error;
    } // method setPlaphyCodeError


    /**
     * Set the 'taxcod' class variable
     * @param  taxcod (String)
     */
    public int setTaxcod(String taxcod) {
        try {
            this.taxcod = taxcod;
            if (this.taxcod != CHARNULL) {
                this.taxcod = stripCRLF(this.taxcod.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>taxcod = " + this.taxcod);
        } catch (Exception e) {
            setTaxcodError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return taxcodError;
    } // method setTaxcod

    /**
     * Called when an exception has occured
     * @param  taxcod (String)
     */
    private void setTaxcodError (String taxcod, Exception e, int error) {
        this.taxcod = taxcod;
        taxcodErrorMessage = e.toString();
        taxcodError = error;
    } // method setTaxcodError


    /**
     * Set the 'taxlev' class variable
     * @param  taxlev (int)
     */
    public int setTaxlev(int taxlev) {
        try {
            if ( ((taxlev == INTNULL) || 
                  (taxlev == INTNULL2)) ||
                !((taxlev < TAXLEV_MN) ||
                  (taxlev > TAXLEV_MX)) ) {
                this.taxlev = taxlev;
                taxlevError = ERROR_NORMAL;
            } else {
                throw taxlevOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTaxlevError(INTNULL, e, ERROR_LOCAL);
        } // try
        return taxlevError;
    } // method setTaxlev

    /**
     * Set the 'taxlev' class variable
     * @param  taxlev (Integer)
     */
    public int setTaxlev(Integer taxlev) {
        try {
            setTaxlev(taxlev.intValue());
        } catch (Exception e) {
            setTaxlevError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return taxlevError;
    } // method setTaxlev

    /**
     * Set the 'taxlev' class variable
     * @param  taxlev (Float)
     */
    public int setTaxlev(Float taxlev) {
        try {
            if (taxlev.floatValue() == FLOATNULL) {
                setTaxlev(INTNULL);
            } else {
                setTaxlev(taxlev.intValue());
            } // if (taxlev.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTaxlevError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return taxlevError;
    } // method setTaxlev

    /**
     * Set the 'taxlev' class variable
     * @param  taxlev (String)
     */
    public int setTaxlev(String taxlev) {
        try {
            setTaxlev(new Integer(taxlev).intValue());
        } catch (Exception e) {
            setTaxlevError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return taxlevError;
    } // method setTaxlev

    /**
     * Called when an exception has occured
     * @param  taxlev (String)
     */
    private void setTaxlevError (int taxlev, Exception e, int error) {
        this.taxlev = taxlev;
        taxlevErrorMessage = e.toString();
        taxlevError = error;
    } // method setTaxlevError


    /**
     * Set the 'taxcnt' class variable
     * @param  taxcnt (int)
     */
    public int setTaxcnt(int taxcnt) {
        try {
            if ( ((taxcnt == INTNULL) || 
                  (taxcnt == INTNULL2)) ||
                !((taxcnt < TAXCNT_MN) ||
                  (taxcnt > TAXCNT_MX)) ) {
                this.taxcnt = taxcnt;
                taxcntError = ERROR_NORMAL;
            } else {
                throw taxcntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTaxcntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return taxcntError;
    } // method setTaxcnt

    /**
     * Set the 'taxcnt' class variable
     * @param  taxcnt (Integer)
     */
    public int setTaxcnt(Integer taxcnt) {
        try {
            setTaxcnt(taxcnt.intValue());
        } catch (Exception e) {
            setTaxcntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return taxcntError;
    } // method setTaxcnt

    /**
     * Set the 'taxcnt' class variable
     * @param  taxcnt (Float)
     */
    public int setTaxcnt(Float taxcnt) {
        try {
            if (taxcnt.floatValue() == FLOATNULL) {
                setTaxcnt(INTNULL);
            } else {
                setTaxcnt(taxcnt.intValue());
            } // if (taxcnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTaxcntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return taxcntError;
    } // method setTaxcnt

    /**
     * Set the 'taxcnt' class variable
     * @param  taxcnt (String)
     */
    public int setTaxcnt(String taxcnt) {
        try {
            setTaxcnt(new Integer(taxcnt).intValue());
        } catch (Exception e) {
            setTaxcntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return taxcntError;
    } // method setTaxcnt

    /**
     * Called when an exception has occured
     * @param  taxcnt (String)
     */
    private void setTaxcntError (int taxcnt, Exception e, int error) {
        this.taxcnt = taxcnt;
        taxcntErrorMessage = e.toString();
        taxcntError = error;
    } // method setTaxcntError


    /**
     * Set the 'taxcom' class variable
     * @param  taxcom (String)
     */
    public int setTaxcom(String taxcom) {
        try {
            this.taxcom = taxcom;
            if (this.taxcom != CHARNULL) {
                this.taxcom = stripCRLF(this.taxcom.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>taxcom = " + this.taxcom);
        } catch (Exception e) {
            setTaxcomError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return taxcomError;
    } // method setTaxcom

    /**
     * Called when an exception has occured
     * @param  taxcom (String)
     */
    private void setTaxcomError (String taxcom, Exception e, int error) {
        this.taxcom = taxcom;
        taxcomErrorMessage = e.toString();
        taxcomError = error;
    } // method setTaxcomError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'plaphyCode' class variable
     * @return plaphyCode (int)
     */
    public int getPlaphyCode() {
        return plaphyCode;
    } // method getPlaphyCode

    /**
     * Return the 'plaphyCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlaphyCode methods
     * @return plaphyCode (String)
     */
    public String getPlaphyCode(String s) {
        return ((plaphyCode != INTNULL) ? new Integer(plaphyCode).toString() : "");
    } // method getPlaphyCode


    /**
     * Return the 'taxcod' class variable
     * @return taxcod (String)
     */
    public String getTaxcod() {
        return taxcod;
    } // method getTaxcod

    /**
     * Return the 'taxcod' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTaxcod methods
     * @return taxcod (String)
     */
    public String getTaxcod(String s) {
        return (taxcod != CHARNULL ? taxcod.replace('"','\'') : "");
    } // method getTaxcod


    /**
     * Return the 'taxlev' class variable
     * @return taxlev (int)
     */
    public int getTaxlev() {
        return taxlev;
    } // method getTaxlev

    /**
     * Return the 'taxlev' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTaxlev methods
     * @return taxlev (String)
     */
    public String getTaxlev(String s) {
        return ((taxlev != INTNULL) ? new Integer(taxlev).toString() : "");
    } // method getTaxlev


    /**
     * Return the 'taxcnt' class variable
     * @return taxcnt (int)
     */
    public int getTaxcnt() {
        return taxcnt;
    } // method getTaxcnt

    /**
     * Return the 'taxcnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTaxcnt methods
     * @return taxcnt (String)
     */
    public String getTaxcnt(String s) {
        return ((taxcnt != INTNULL) ? new Integer(taxcnt).toString() : "");
    } // method getTaxcnt


    /**
     * Return the 'taxcom' class variable
     * @return taxcom (String)
     */
    public String getTaxcom() {
        return taxcom;
    } // method getTaxcom

    /**
     * Return the 'taxcom' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTaxcom methods
     * @return taxcom (String)
     */
    public String getTaxcom(String s) {
        return (taxcom != CHARNULL ? taxcom.replace('"','\'') : "");
    } // method getTaxcom


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
        if ((plaphyCode == INTNULL) &&
            (taxcod == CHARNULL) &&
            (taxlev == INTNULL) &&
            (taxcnt == INTNULL) &&
            (taxcom == CHARNULL)) {
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
        int sumError = plaphyCodeError +
            taxcodError +
            taxlevError +
            taxcntError +
            taxcomError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the plaphyCode instance variable
     * @return errorcode (int)
     */
    public int getPlaphyCodeError() {
        return plaphyCodeError;
    } // method getPlaphyCodeError

    /**
     * Gets the errorMessage for the plaphyCode instance variable
     * @return errorMessage (String)
     */
    public String getPlaphyCodeErrorMessage() {
        return plaphyCodeErrorMessage;
    } // method getPlaphyCodeErrorMessage


    /**
     * Gets the errorcode for the taxcod instance variable
     * @return errorcode (int)
     */
    public int getTaxcodError() {
        return taxcodError;
    } // method getTaxcodError

    /**
     * Gets the errorMessage for the taxcod instance variable
     * @return errorMessage (String)
     */
    public String getTaxcodErrorMessage() {
        return taxcodErrorMessage;
    } // method getTaxcodErrorMessage


    /**
     * Gets the errorcode for the taxlev instance variable
     * @return errorcode (int)
     */
    public int getTaxlevError() {
        return taxlevError;
    } // method getTaxlevError

    /**
     * Gets the errorMessage for the taxlev instance variable
     * @return errorMessage (String)
     */
    public String getTaxlevErrorMessage() {
        return taxlevErrorMessage;
    } // method getTaxlevErrorMessage


    /**
     * Gets the errorcode for the taxcnt instance variable
     * @return errorcode (int)
     */
    public int getTaxcntError() {
        return taxcntError;
    } // method getTaxcntError

    /**
     * Gets the errorMessage for the taxcnt instance variable
     * @return errorMessage (String)
     */
    public String getTaxcntErrorMessage() {
        return taxcntErrorMessage;
    } // method getTaxcntErrorMessage


    /**
     * Gets the errorcode for the taxcom instance variable
     * @return errorcode (int)
     */
    public int getTaxcomError() {
        return taxcomError;
    } // method getTaxcomError

    /**
     * Gets the errorMessage for the taxcom instance variable
     * @return errorMessage (String)
     */
    public String getTaxcomErrorMessage() {
        return taxcomErrorMessage;
    } // method getTaxcomErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnPlatax. e.g.<pre>
     * MrnPlatax platax = new MrnPlatax(<val1>);
     * MrnPlatax plataxArray[] = platax.get();</pre>
     * will get the MrnPlatax record where plaphyCode = <val1>.
     * @return Array of MrnPlatax (MrnPlatax[])
     */
    public MrnPlatax[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnPlatax plataxArray[] = 
     *     new MrnPlatax().get(MrnPlatax.PLAPHY_CODE+"=<val1>");</pre>
     * will get the MrnPlatax record where plaphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnPlatax (MrnPlatax[])
     */
    public MrnPlatax[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnPlatax plataxArray[] = 
     *     new MrnPlatax().get("1=1",platax.PLAPHY_CODE);</pre>
     * will get the all the MrnPlatax records, and order them by plaphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlatax (MrnPlatax[])
     */
    public MrnPlatax[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnPlatax.PLAPHY_CODE,MrnPlatax.TAXCOD;
     * String where = MrnPlatax.PLAPHY_CODE + "=<val1";
     * String order = MrnPlatax.PLAPHY_CODE;
     * MrnPlatax plataxArray[] = 
     *     new MrnPlatax().get(columns, where, order);</pre>
     * will get the plaphyCode and taxcod colums of all MrnPlatax records,
     * where plaphyCode = <val1>, and order them by plaphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlatax (MrnPlatax[])
     */
    public MrnPlatax[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnPlatax.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnPlatax[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int plaphyCodeCol = db.getColNumber(PLAPHY_CODE);
        int taxcodCol     = db.getColNumber(TAXCOD);
        int taxlevCol     = db.getColNumber(TAXLEV);
        int taxcntCol     = db.getColNumber(TAXCNT);
        int taxcomCol     = db.getColNumber(TAXCOM);
        MrnPlatax[] cArray = new MrnPlatax[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnPlatax();
            if (plaphyCodeCol != -1)
                cArray[i].setPlaphyCode((String) row.elementAt(plaphyCodeCol));
            if (taxcodCol != -1)
                cArray[i].setTaxcod    ((String) row.elementAt(taxcodCol));
            if (taxlevCol != -1)
                cArray[i].setTaxlev    ((String) row.elementAt(taxlevCol));
            if (taxcntCol != -1)
                cArray[i].setTaxcnt    ((String) row.elementAt(taxcntCol));
            if (taxcomCol != -1)
                cArray[i].setTaxcom    ((String) row.elementAt(taxcomCol));
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
     *     new MrnPlatax(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>).put();</pre>
     * will insert a record with:
     *     plaphyCode = <val1>,
     *     taxcod     = <val2>,
     *     taxlev     = <val3>,
     *     taxcnt     = <val4>,
     *     taxcom     = <val5>.
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
     * Boolean success = new MrnPlatax(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where taxcod = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPlatax platax = new MrnPlatax();
     * success = platax.del(MrnPlatax.PLAPHY_CODE+"=<val1>");</pre>
     * will delete all records where plaphyCode = <val1>.
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
     * update are taken from the MrnPlatax argument, .e.g.<pre>
     * Boolean success
     * MrnPlatax updMrnPlatax = new MrnPlatax();
     * updMrnPlatax.setTaxcod(<val2>);
     * MrnPlatax whereMrnPlatax = new MrnPlatax(<val1>);
     * success = whereMrnPlatax.upd(updMrnPlatax);</pre>
     * will update Taxcod to <val2> for all records where 
     * plaphyCode = <val1>.
     * @param platax  A MrnPlatax variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlatax platax) {
        return db.update (TABLE, createColVals(platax), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPlatax updMrnPlatax = new MrnPlatax();
     * updMrnPlatax.setTaxcod(<val2>);
     * MrnPlatax whereMrnPlatax = new MrnPlatax();
     * success = whereMrnPlatax.upd(
     *     updMrnPlatax, MrnPlatax.PLAPHY_CODE+"=<val1>");</pre>
     * will update Taxcod to <val2> for all records where 
     * plaphyCode = <val1>.
     * @param  updMrnPlatax  A MrnPlatax variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlatax platax, String where) {
        return db.update (TABLE, createColVals(platax), where);
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
        if (getPlaphyCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPHY_CODE + "=" + getPlaphyCode("");
        } // if getPlaphyCode
        if (getTaxcod() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TAXCOD + "='" + getTaxcod() + "'";
        } // if getTaxcod
        if (getTaxlev() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TAXLEV + "=" + getTaxlev("");
        } // if getTaxlev
        if (getTaxcnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TAXCNT + "=" + getTaxcnt("");
        } // if getTaxcnt
        if (getTaxcom() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TAXCOM + "='" + getTaxcom() + "'";
        } // if getTaxcom
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnPlatax aVar) {
        String colVals = "";
        if (aVar.getPlaphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPHY_CODE +"=";
            colVals += (aVar.getPlaphyCode() == INTNULL2 ?
                "null" : aVar.getPlaphyCode(""));
        } // if aVar.getPlaphyCode
        if (aVar.getTaxcod() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TAXCOD +"=";
            colVals += (aVar.getTaxcod().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTaxcod() + "'");
        } // if aVar.getTaxcod
        if (aVar.getTaxlev() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TAXLEV +"=";
            colVals += (aVar.getTaxlev() == INTNULL2 ?
                "null" : aVar.getTaxlev(""));
        } // if aVar.getTaxlev
        if (aVar.getTaxcnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TAXCNT +"=";
            colVals += (aVar.getTaxcnt() == INTNULL2 ?
                "null" : aVar.getTaxcnt(""));
        } // if aVar.getTaxcnt
        if (aVar.getTaxcom() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TAXCOM +"=";
            colVals += (aVar.getTaxcom().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTaxcom() + "'");
        } // if aVar.getTaxcom
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = PLAPHY_CODE;
        if (getTaxcod() != CHARNULL) {
            columns = columns + "," + TAXCOD;
        } // if getTaxcod
        if (getTaxlev() != INTNULL) {
            columns = columns + "," + TAXLEV;
        } // if getTaxlev
        if (getTaxcnt() != INTNULL) {
            columns = columns + "," + TAXCNT;
        } // if getTaxcnt
        if (getTaxcom() != CHARNULL) {
            columns = columns + "," + TAXCOM;
        } // if getTaxcom
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getPlaphyCode("");
        if (getTaxcod() != CHARNULL) {
            values  = values  + ",'" + getTaxcod() + "'";
        } // if getTaxcod
        if (getTaxlev() != INTNULL) {
            values  = values  + "," + getTaxlev("");
        } // if getTaxlev
        if (getTaxcnt() != INTNULL) {
            values  = values  + "," + getTaxcnt("");
        } // if getTaxcnt
        if (getTaxcom() != CHARNULL) {
            values  = values  + ",'" + getTaxcom() + "'";
        } // if getTaxcom
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getPlaphyCode("") + "|" +
            getTaxcod("")     + "|" +
            getTaxlev("")     + "|" +
            getTaxcnt("")     + "|" +
            getTaxcom("")     + "|";
    } // method toString

} // class MrnPlatax
