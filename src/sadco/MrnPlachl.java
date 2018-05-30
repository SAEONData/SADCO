package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the PLACHL table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 071213 - SIT Group
 * @version
 * 071213 - GenTableClassB class - create class<br>
 */
public class MrnPlachl extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "PLACHL";
    /** The plaphyCode field name */
    public static final String PLAPHY_CODE = "PLAPHY_CODE";
    /** The chla field name */
    public static final String CHLA = "CHLA";
    /** The chlb field name */
    public static final String CHLB = "CHLB";
    /** The chlc field name */
    public static final String CHLC = "CHLC";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       plaphyCode;
    private float     chla;
    private float     chlb;
    private float     chlc;

    /** The error variables  */
    private int plaphyCodeError = ERROR_NORMAL;
    private int chlaError       = ERROR_NORMAL;
    private int chlbError       = ERROR_NORMAL;
    private int chlcError       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String plaphyCodeErrorMessage = "";
    private String chlaErrorMessage       = "";
    private String chlbErrorMessage       = "";
    private String chlcErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       PLAPHY_CODE_MN = INTMIN;
    public static final int       PLAPHY_CODE_MX = INTMAX;
    public static final float     CHLA_MN = FLOATMIN;
    public static final float     CHLA_MX = FLOATMAX;
    public static final float     CHLB_MN = FLOATMIN;
    public static final float     CHLB_MX = FLOATMAX;
    public static final float     CHLC_MN = FLOATMIN;
    public static final float     CHLC_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception plaphyCodeOutOfBoundsException =
        new Exception ("'plaphyCode' out of bounds: " +
            PLAPHY_CODE_MN + " - " + PLAPHY_CODE_MX);
    Exception chlaOutOfBoundsException =
        new Exception ("'chla' out of bounds: " +
            CHLA_MN + " - " + CHLA_MX);
    Exception chlbOutOfBoundsException =
        new Exception ("'chlb' out of bounds: " +
            CHLB_MN + " - " + CHLB_MX);
    Exception chlcOutOfBoundsException =
        new Exception ("'chlc' out of bounds: " +
            CHLC_MN + " - " + CHLC_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnPlachl() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnPlachl constructor 1"); // debug
    } // MrnPlachl constructor

    /**
     * Instantiate a MrnPlachl object and initialize the instance variables.
     * @param plaphyCode  The plaphyCode (int)
     */
    public MrnPlachl(
            int plaphyCode) {
        this();
        setPlaphyCode (plaphyCode);
        if (dbg) System.out.println ("<br>in MrnPlachl constructor 2"); // debug
    } // MrnPlachl constructor

    /**
     * Instantiate a MrnPlachl object and initialize the instance variables.
     * @param plaphyCode  The plaphyCode (int)
     * @param chla        The chla       (float)
     * @param chlb        The chlb       (float)
     * @param chlc        The chlc       (float)
     * @return A MrnPlachl object
     */
    public MrnPlachl(
            int   plaphyCode,
            float chla,
            float chlb,
            float chlc) {
        this();
        setPlaphyCode (plaphyCode);
        setChla       (chla      );
        setChlb       (chlb      );
        setChlc       (chlc      );
        if (dbg) System.out.println ("<br>in MrnPlachl constructor 3"); // debug
    } // MrnPlachl constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setPlaphyCode (INTNULL  );
        setChla       (FLOATNULL);
        setChlb       (FLOATNULL);
        setChlc       (FLOATNULL);
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
     * Set the 'chla' class variable
     * @param  chla (float)
     */
    public int setChla(float chla) {
        try {
            if ( ((chla == FLOATNULL) || 
                  (chla == FLOATNULL2)) ||
                !((chla < CHLA_MN) ||
                  (chla > CHLA_MX)) ) {
                this.chla = chla;
                chlaError = ERROR_NORMAL;
            } else {
                throw chlaOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setChlaError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return chlaError;
    } // method setChla

    /**
     * Set the 'chla' class variable
     * @param  chla (Integer)
     */
    public int setChla(Integer chla) {
        try {
            setChla(chla.floatValue());
        } catch (Exception e) {
            setChlaError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlaError;
    } // method setChla

    /**
     * Set the 'chla' class variable
     * @param  chla (Float)
     */
    public int setChla(Float chla) {
        try {
            setChla(chla.floatValue());
        } catch (Exception e) {
            setChlaError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlaError;
    } // method setChla

    /**
     * Set the 'chla' class variable
     * @param  chla (String)
     */
    public int setChla(String chla) {
        try {
            setChla(new Float(chla).floatValue());
        } catch (Exception e) {
            setChlaError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlaError;
    } // method setChla

    /**
     * Called when an exception has occured
     * @param  chla (String)
     */
    private void setChlaError (float chla, Exception e, int error) {
        this.chla = chla;
        chlaErrorMessage = e.toString();
        chlaError = error;
    } // method setChlaError


    /**
     * Set the 'chlb' class variable
     * @param  chlb (float)
     */
    public int setChlb(float chlb) {
        try {
            if ( ((chlb == FLOATNULL) || 
                  (chlb == FLOATNULL2)) ||
                !((chlb < CHLB_MN) ||
                  (chlb > CHLB_MX)) ) {
                this.chlb = chlb;
                chlbError = ERROR_NORMAL;
            } else {
                throw chlbOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setChlbError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return chlbError;
    } // method setChlb

    /**
     * Set the 'chlb' class variable
     * @param  chlb (Integer)
     */
    public int setChlb(Integer chlb) {
        try {
            setChlb(chlb.floatValue());
        } catch (Exception e) {
            setChlbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlbError;
    } // method setChlb

    /**
     * Set the 'chlb' class variable
     * @param  chlb (Float)
     */
    public int setChlb(Float chlb) {
        try {
            setChlb(chlb.floatValue());
        } catch (Exception e) {
            setChlbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlbError;
    } // method setChlb

    /**
     * Set the 'chlb' class variable
     * @param  chlb (String)
     */
    public int setChlb(String chlb) {
        try {
            setChlb(new Float(chlb).floatValue());
        } catch (Exception e) {
            setChlbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlbError;
    } // method setChlb

    /**
     * Called when an exception has occured
     * @param  chlb (String)
     */
    private void setChlbError (float chlb, Exception e, int error) {
        this.chlb = chlb;
        chlbErrorMessage = e.toString();
        chlbError = error;
    } // method setChlbError


    /**
     * Set the 'chlc' class variable
     * @param  chlc (float)
     */
    public int setChlc(float chlc) {
        try {
            if ( ((chlc == FLOATNULL) || 
                  (chlc == FLOATNULL2)) ||
                !((chlc < CHLC_MN) ||
                  (chlc > CHLC_MX)) ) {
                this.chlc = chlc;
                chlcError = ERROR_NORMAL;
            } else {
                throw chlcOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setChlcError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return chlcError;
    } // method setChlc

    /**
     * Set the 'chlc' class variable
     * @param  chlc (Integer)
     */
    public int setChlc(Integer chlc) {
        try {
            setChlc(chlc.floatValue());
        } catch (Exception e) {
            setChlcError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlcError;
    } // method setChlc

    /**
     * Set the 'chlc' class variable
     * @param  chlc (Float)
     */
    public int setChlc(Float chlc) {
        try {
            setChlc(chlc.floatValue());
        } catch (Exception e) {
            setChlcError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlcError;
    } // method setChlc

    /**
     * Set the 'chlc' class variable
     * @param  chlc (String)
     */
    public int setChlc(String chlc) {
        try {
            setChlc(new Float(chlc).floatValue());
        } catch (Exception e) {
            setChlcError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlcError;
    } // method setChlc

    /**
     * Called when an exception has occured
     * @param  chlc (String)
     */
    private void setChlcError (float chlc, Exception e, int error) {
        this.chlc = chlc;
        chlcErrorMessage = e.toString();
        chlcError = error;
    } // method setChlcError


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
     * Return the 'chla' class variable
     * @return chla (float)
     */
    public float getChla() {
        return chla;
    } // method getChla

    /**
     * Return the 'chla' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getChla methods
     * @return chla (String)
     */
    public String getChla(String s) {
        return ((chla != FLOATNULL) ? new Float(chla).toString() : "");
    } // method getChla


    /**
     * Return the 'chlb' class variable
     * @return chlb (float)
     */
    public float getChlb() {
        return chlb;
    } // method getChlb

    /**
     * Return the 'chlb' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getChlb methods
     * @return chlb (String)
     */
    public String getChlb(String s) {
        return ((chlb != FLOATNULL) ? new Float(chlb).toString() : "");
    } // method getChlb


    /**
     * Return the 'chlc' class variable
     * @return chlc (float)
     */
    public float getChlc() {
        return chlc;
    } // method getChlc

    /**
     * Return the 'chlc' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getChlc methods
     * @return chlc (String)
     */
    public String getChlc(String s) {
        return ((chlc != FLOATNULL) ? new Float(chlc).toString() : "");
    } // method getChlc


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
            (chla == FLOATNULL) &&
            (chlb == FLOATNULL) &&
            (chlc == FLOATNULL)) {
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
            chlaError +
            chlbError +
            chlcError;
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
     * Gets the errorcode for the chla instance variable
     * @return errorcode (int)
     */
    public int getChlaError() {
        return chlaError;
    } // method getChlaError

    /**
     * Gets the errorMessage for the chla instance variable
     * @return errorMessage (String)
     */
    public String getChlaErrorMessage() {
        return chlaErrorMessage;
    } // method getChlaErrorMessage


    /**
     * Gets the errorcode for the chlb instance variable
     * @return errorcode (int)
     */
    public int getChlbError() {
        return chlbError;
    } // method getChlbError

    /**
     * Gets the errorMessage for the chlb instance variable
     * @return errorMessage (String)
     */
    public String getChlbErrorMessage() {
        return chlbErrorMessage;
    } // method getChlbErrorMessage


    /**
     * Gets the errorcode for the chlc instance variable
     * @return errorcode (int)
     */
    public int getChlcError() {
        return chlcError;
    } // method getChlcError

    /**
     * Gets the errorMessage for the chlc instance variable
     * @return errorMessage (String)
     */
    public String getChlcErrorMessage() {
        return chlcErrorMessage;
    } // method getChlcErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnPlachl. e.g.<pre>
     * MrnPlachl plachl = new MrnPlachl(<val1>);
     * MrnPlachl plachlArray[] = plachl.get();</pre>
     * will get the MrnPlachl record where plaphyCode = <val1>.
     * @return Array of MrnPlachl (MrnPlachl[])
     */
    public MrnPlachl[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnPlachl plachlArray[] = 
     *     new MrnPlachl().get(MrnPlachl.PLAPHY_CODE+"=<val1>");</pre>
     * will get the MrnPlachl record where plaphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnPlachl (MrnPlachl[])
     */
    public MrnPlachl[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnPlachl plachlArray[] = 
     *     new MrnPlachl().get("1=1",plachl.PLAPHY_CODE);</pre>
     * will get the all the MrnPlachl records, and order them by plaphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlachl (MrnPlachl[])
     */
    public MrnPlachl[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnPlachl.PLAPHY_CODE,MrnPlachl.CHLA;
     * String where = MrnPlachl.PLAPHY_CODE + "=<val1";
     * String order = MrnPlachl.PLAPHY_CODE;
     * MrnPlachl plachlArray[] = 
     *     new MrnPlachl().get(columns, where, order);</pre>
     * will get the plaphyCode and chla colums of all MrnPlachl records,
     * where plaphyCode = <val1>, and order them by plaphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlachl (MrnPlachl[])
     */
    public MrnPlachl[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnPlachl.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnPlachl[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int plaphyCodeCol = db.getColNumber(PLAPHY_CODE);
        int chlaCol       = db.getColNumber(CHLA);
        int chlbCol       = db.getColNumber(CHLB);
        int chlcCol       = db.getColNumber(CHLC);
        MrnPlachl[] cArray = new MrnPlachl[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnPlachl();
            if (plaphyCodeCol != -1)
                cArray[i].setPlaphyCode((String) row.elementAt(plaphyCodeCol));
            if (chlaCol != -1)
                cArray[i].setChla      ((String) row.elementAt(chlaCol));
            if (chlbCol != -1)
                cArray[i].setChlb      ((String) row.elementAt(chlbCol));
            if (chlcCol != -1)
                cArray[i].setChlc      ((String) row.elementAt(chlcCol));
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
     *     new MrnPlachl(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>).put();</pre>
     * will insert a record with:
     *     plaphyCode = <val1>,
     *     chla       = <val2>,
     *     chlb       = <val3>,
     *     chlc       = <val4>.
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
     * Boolean success = new MrnPlachl(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where chla = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPlachl plachl = new MrnPlachl();
     * success = plachl.del(MrnPlachl.PLAPHY_CODE+"=<val1>");</pre>
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
     * update are taken from the MrnPlachl argument, .e.g.<pre>
     * Boolean success
     * MrnPlachl updMrnPlachl = new MrnPlachl();
     * updMrnPlachl.setChla(<val2>);
     * MrnPlachl whereMrnPlachl = new MrnPlachl(<val1>);
     * success = whereMrnPlachl.upd(updMrnPlachl);</pre>
     * will update Chla to <val2> for all records where 
     * plaphyCode = <val1>.
     * @param plachl  A MrnPlachl variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlachl plachl) {
        return db.update (TABLE, createColVals(plachl), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPlachl updMrnPlachl = new MrnPlachl();
     * updMrnPlachl.setChla(<val2>);
     * MrnPlachl whereMrnPlachl = new MrnPlachl();
     * success = whereMrnPlachl.upd(
     *     updMrnPlachl, MrnPlachl.PLAPHY_CODE+"=<val1>");</pre>
     * will update Chla to <val2> for all records where 
     * plaphyCode = <val1>.
     * @param  updMrnPlachl  A MrnPlachl variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlachl plachl, String where) {
        return db.update (TABLE, createColVals(plachl), where);
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
        if (getChla() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CHLA + "=" + getChla("");
        } // if getChla
        if (getChlb() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CHLB + "=" + getChlb("");
        } // if getChlb
        if (getChlc() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CHLC + "=" + getChlc("");
        } // if getChlc
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnPlachl aVar) {
        String colVals = "";
        if (aVar.getPlaphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPHY_CODE +"=";
            colVals += (aVar.getPlaphyCode() == INTNULL2 ?
                "null" : aVar.getPlaphyCode(""));
        } // if aVar.getPlaphyCode
        if (aVar.getChla() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CHLA +"=";
            colVals += (aVar.getChla() == FLOATNULL2 ?
                "null" : aVar.getChla(""));
        } // if aVar.getChla
        if (aVar.getChlb() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CHLB +"=";
            colVals += (aVar.getChlb() == FLOATNULL2 ?
                "null" : aVar.getChlb(""));
        } // if aVar.getChlb
        if (aVar.getChlc() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CHLC +"=";
            colVals += (aVar.getChlc() == FLOATNULL2 ?
                "null" : aVar.getChlc(""));
        } // if aVar.getChlc
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = PLAPHY_CODE;
        if (getChla() != FLOATNULL) {
            columns = columns + "," + CHLA;
        } // if getChla
        if (getChlb() != FLOATNULL) {
            columns = columns + "," + CHLB;
        } // if getChlb
        if (getChlc() != FLOATNULL) {
            columns = columns + "," + CHLC;
        } // if getChlc
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getPlaphyCode("");
        if (getChla() != FLOATNULL) {
            values  = values  + "," + getChla("");
        } // if getChla
        if (getChlb() != FLOATNULL) {
            values  = values  + "," + getChlb("");
        } // if getChlb
        if (getChlc() != FLOATNULL) {
            values  = values  + "," + getChlc("");
        } // if getChlc
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
            getChla("")       + "|" +
            getChlb("")       + "|" +
            getChlc("")       + "|";
    } // method toString

} // class MrnPlachl
