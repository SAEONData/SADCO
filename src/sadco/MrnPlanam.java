package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the PLANAM table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnPlanam extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "PLANAM";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The name field name */
    public static final String NAME = "NAME";
    /** The platformCode field name */
    public static final String PLATFORM_CODE = "PLATFORM_CODE";
    /** The callsign field name */
    public static final String CALLSIGN = "CALLSIGN";
    /** The nodcCountryCode field name */
    public static final String NODC_COUNTRY_CODE = "NODC_COUNTRY_CODE";
    /** The nodcShipCode field name */
    public static final String NODC_SHIP_CODE = "NODC_SHIP_CODE";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    name;
    private int       platformCode;
    private String    callsign;
    private String    nodcCountryCode;
    private String    nodcShipCode;

    /** The error variables  */
    private int codeError            = ERROR_NORMAL;
    private int nameError            = ERROR_NORMAL;
    private int platformCodeError    = ERROR_NORMAL;
    private int callsignError        = ERROR_NORMAL;
    private int nodcCountryCodeError = ERROR_NORMAL;
    private int nodcShipCodeError    = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage            = "";
    private String nameErrorMessage            = "";
    private String platformCodeErrorMessage    = "";
    private String callsignErrorMessage        = "";
    private String nodcCountryCodeErrorMessage = "";
    private String nodcShipCodeErrorMessage    = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       PLATFORM_CODE_MN = INTMIN;
    public static final int       PLATFORM_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception platformCodeOutOfBoundsException =
        new Exception ("'platformCode' out of bounds: " +
            PLATFORM_CODE_MN + " - " + PLATFORM_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnPlanam() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnPlanam constructor 1"); // debug
    } // MrnPlanam constructor

    /**
     * Instantiate a MrnPlanam object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MrnPlanam(
            int code) {
        this();
        setCode            (code           );
        if (dbg) System.out.println ("<br>in MrnPlanam constructor 2"); // debug
    } // MrnPlanam constructor

    /**
     * Instantiate a MrnPlanam object and initialize the instance variables.
     * @param code             The code            (int)
     * @param name             The name            (String)
     * @param platformCode     The platformCode    (int)
     * @param callsign         The callsign        (String)
     * @param nodcCountryCode  The nodcCountryCode (String)
     * @param nodcShipCode     The nodcShipCode    (String)
     */
    public MrnPlanam(
            int    code,
            String name,
            int    platformCode,
            String callsign,
            String nodcCountryCode,
            String nodcShipCode) {
        this();
        setCode            (code           );
        setName            (name           );
        setPlatformCode    (platformCode   );
        setCallsign        (callsign       );
        setNodcCountryCode (nodcCountryCode);
        setNodcShipCode    (nodcShipCode   );
        if (dbg) System.out.println ("<br>in MrnPlanam constructor 3"); // debug
    } // MrnPlanam constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode            (INTNULL );
        setName            (CHARNULL);
        setPlatformCode    (INTNULL );
        setCallsign        (CHARNULL);
        setNodcCountryCode (CHARNULL);
        setNodcShipCode    (CHARNULL);
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
     * Set the 'name' class variable
     * @param  name (String)
     */
    public int setName(String name) {
        try {
            this.name = name;
            if (this.name != CHARNULL) {
                this.name = stripCRLF(this.name.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>name = " + this.name);
        } catch (Exception e) {
            setNameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return nameError;
    } // method setName

    /**
     * Called when an exception has occured
     * @param  name (String)
     */
    private void setNameError (String name, Exception e, int error) {
        this.name = name;
        nameErrorMessage = e.toString();
        nameError = error;
    } // method setNameError


    /**
     * Set the 'platformCode' class variable
     * @param  platformCode (int)
     */
    public int setPlatformCode(int platformCode) {
        try {
            if ( ((platformCode == INTNULL) ||
                  (platformCode == INTNULL2)) ||
                !((platformCode < PLATFORM_CODE_MN) ||
                  (platformCode > PLATFORM_CODE_MX)) ) {
                this.platformCode = platformCode;
                platformCodeError = ERROR_NORMAL;
            } else {
                throw platformCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlatformCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return platformCodeError;
    } // method setPlatformCode

    /**
     * Set the 'platformCode' class variable
     * @param  platformCode (Integer)
     */
    public int setPlatformCode(Integer platformCode) {
        try {
            setPlatformCode(platformCode.intValue());
        } catch (Exception e) {
            setPlatformCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return platformCodeError;
    } // method setPlatformCode

    /**
     * Set the 'platformCode' class variable
     * @param  platformCode (Float)
     */
    public int setPlatformCode(Float platformCode) {
        try {
            if (platformCode.floatValue() == FLOATNULL) {
                setPlatformCode(INTNULL);
            } else {
                setPlatformCode(platformCode.intValue());
            } // if (platformCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlatformCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return platformCodeError;
    } // method setPlatformCode

    /**
     * Set the 'platformCode' class variable
     * @param  platformCode (String)
     */
    public int setPlatformCode(String platformCode) {
        try {
            setPlatformCode(new Integer(platformCode).intValue());
        } catch (Exception e) {
            setPlatformCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return platformCodeError;
    } // method setPlatformCode

    /**
     * Called when an exception has occured
     * @param  platformCode (String)
     */
    private void setPlatformCodeError (int platformCode, Exception e, int error) {
        this.platformCode = platformCode;
        platformCodeErrorMessage = e.toString();
        platformCodeError = error;
    } // method setPlatformCodeError


    /**
     * Set the 'callsign' class variable
     * @param  callsign (String)
     */
    public int setCallsign(String callsign) {
        try {
            this.callsign = callsign;
            if (this.callsign != CHARNULL) {
                this.callsign = stripCRLF(this.callsign.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>callsign = " + this.callsign);
        } catch (Exception e) {
            setCallsignError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return callsignError;
    } // method setCallsign

    /**
     * Called when an exception has occured
     * @param  callsign (String)
     */
    private void setCallsignError (String callsign, Exception e, int error) {
        this.callsign = callsign;
        callsignErrorMessage = e.toString();
        callsignError = error;
    } // method setCallsignError


    /**
     * Set the 'nodcCountryCode' class variable
     * @param  nodcCountryCode (String)
     */
    public int setNodcCountryCode(String nodcCountryCode) {
        try {
            this.nodcCountryCode = nodcCountryCode;
            if (this.nodcCountryCode != CHARNULL) {
                this.nodcCountryCode = stripCRLF(this.nodcCountryCode.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>nodcCountryCode = " + this.nodcCountryCode);
        } catch (Exception e) {
            setNodcCountryCodeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return nodcCountryCodeError;
    } // method setNodcCountryCode

    /**
     * Called when an exception has occured
     * @param  nodcCountryCode (String)
     */
    private void setNodcCountryCodeError (String nodcCountryCode, Exception e, int error) {
        this.nodcCountryCode = nodcCountryCode;
        nodcCountryCodeErrorMessage = e.toString();
        nodcCountryCodeError = error;
    } // method setNodcCountryCodeError


    /**
     * Set the 'nodcShipCode' class variable
     * @param  nodcShipCode (String)
     */
    public int setNodcShipCode(String nodcShipCode) {
        try {
            this.nodcShipCode = nodcShipCode;
            if (this.nodcShipCode != CHARNULL) {
                this.nodcShipCode = stripCRLF(this.nodcShipCode.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>nodcShipCode = " + this.nodcShipCode);
        } catch (Exception e) {
            setNodcShipCodeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return nodcShipCodeError;
    } // method setNodcShipCode

    /**
     * Called when an exception has occured
     * @param  nodcShipCode (String)
     */
    private void setNodcShipCodeError (String nodcShipCode, Exception e, int error) {
        this.nodcShipCode = nodcShipCode;
        nodcShipCodeErrorMessage = e.toString();
        nodcShipCodeError = error;
    } // method setNodcShipCodeError


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
     * Return the 'name' class variable
     * @return name (String)
     */
    public String getName() {
        return name;
    } // method getName

    /**
     * Return the 'name' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getName methods
     * @return name (String)
     */
    public String getName(String s) {
        return (name != CHARNULL ? name.replace('"','\'') : "");
    } // method getName


    /**
     * Return the 'platformCode' class variable
     * @return platformCode (int)
     */
    public int getPlatformCode() {
        return platformCode;
    } // method getPlatformCode

    /**
     * Return the 'platformCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlatformCode methods
     * @return platformCode (String)
     */
    public String getPlatformCode(String s) {
        return ((platformCode != INTNULL) ? new Integer(platformCode).toString() : "");
    } // method getPlatformCode


    /**
     * Return the 'callsign' class variable
     * @return callsign (String)
     */
    public String getCallsign() {
        return callsign;
    } // method getCallsign

    /**
     * Return the 'callsign' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCallsign methods
     * @return callsign (String)
     */
    public String getCallsign(String s) {
        return (callsign != CHARNULL ? callsign.replace('"','\'') : "");
    } // method getCallsign


    /**
     * Return the 'nodcCountryCode' class variable
     * @return nodcCountryCode (String)
     */
    public String getNodcCountryCode() {
        return nodcCountryCode;
    } // method getNodcCountryCode

    /**
     * Return the 'nodcCountryCode' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNodcCountryCode methods
     * @return nodcCountryCode (String)
     */
    public String getNodcCountryCode(String s) {
        return (nodcCountryCode != CHARNULL ? nodcCountryCode.replace('"','\'') : "");
    } // method getNodcCountryCode


    /**
     * Return the 'nodcShipCode' class variable
     * @return nodcShipCode (String)
     */
    public String getNodcShipCode() {
        return nodcShipCode;
    } // method getNodcShipCode

    /**
     * Return the 'nodcShipCode' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNodcShipCode methods
     * @return nodcShipCode (String)
     */
    public String getNodcShipCode(String s) {
        return (nodcShipCode != CHARNULL ? nodcShipCode.replace('"','\'') : "");
    } // method getNodcShipCode


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
            (name == CHARNULL) &&
            (platformCode == INTNULL) &&
            (callsign == CHARNULL) &&
            (nodcCountryCode == CHARNULL) &&
            (nodcShipCode == CHARNULL)) {
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
            nameError +
            platformCodeError +
            callsignError +
            nodcCountryCodeError +
            nodcShipCodeError;
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
     * Gets the errorcode for the name instance variable
     * @return errorcode (int)
     */
    public int getNameError() {
        return nameError;
    } // method getNameError

    /**
     * Gets the errorMessage for the name instance variable
     * @return errorMessage (String)
     */
    public String getNameErrorMessage() {
        return nameErrorMessage;
    } // method getNameErrorMessage


    /**
     * Gets the errorcode for the platformCode instance variable
     * @return errorcode (int)
     */
    public int getPlatformCodeError() {
        return platformCodeError;
    } // method getPlatformCodeError

    /**
     * Gets the errorMessage for the platformCode instance variable
     * @return errorMessage (String)
     */
    public String getPlatformCodeErrorMessage() {
        return platformCodeErrorMessage;
    } // method getPlatformCodeErrorMessage


    /**
     * Gets the errorcode for the callsign instance variable
     * @return errorcode (int)
     */
    public int getCallsignError() {
        return callsignError;
    } // method getCallsignError

    /**
     * Gets the errorMessage for the callsign instance variable
     * @return errorMessage (String)
     */
    public String getCallsignErrorMessage() {
        return callsignErrorMessage;
    } // method getCallsignErrorMessage


    /**
     * Gets the errorcode for the nodcCountryCode instance variable
     * @return errorcode (int)
     */
    public int getNodcCountryCodeError() {
        return nodcCountryCodeError;
    } // method getNodcCountryCodeError

    /**
     * Gets the errorMessage for the nodcCountryCode instance variable
     * @return errorMessage (String)
     */
    public String getNodcCountryCodeErrorMessage() {
        return nodcCountryCodeErrorMessage;
    } // method getNodcCountryCodeErrorMessage


    /**
     * Gets the errorcode for the nodcShipCode instance variable
     * @return errorcode (int)
     */
    public int getNodcShipCodeError() {
        return nodcShipCodeError;
    } // method getNodcShipCodeError

    /**
     * Gets the errorMessage for the nodcShipCode instance variable
     * @return errorMessage (String)
     */
    public String getNodcShipCodeErrorMessage() {
        return nodcShipCodeErrorMessage;
    } // method getNodcShipCodeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnPlanam. e.g.<pre>
     * MrnPlanam planam = new MrnPlanam(<val1>);
     * MrnPlanam planamArray[] = planam.get();</pre>
     * will get the MrnPlanam record where code = <val1>.
     * @return Array of MrnPlanam (MrnPlanam[])
     */
    public MrnPlanam[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnPlanam planamArray[] =
     *     new MrnPlanam().get(MrnPlanam.CODE+"=<val1>");</pre>
     * will get the MrnPlanam record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnPlanam (MrnPlanam[])
     */
    public MrnPlanam[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnPlanam planamArray[] =
     *     new MrnPlanam().get("1=1",planam.CODE);</pre>
     * will get the all the MrnPlanam records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlanam (MrnPlanam[])
     */
    public MrnPlanam[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnPlanam.CODE,MrnPlanam.NAME;
     * String where = MrnPlanam.CODE + "=<val1";
     * String order = MrnPlanam.CODE;
     * MrnPlanam planamArray[] =
     *     new MrnPlanam().get(columns, where, order);</pre>
     * will get the code and name colums of all MrnPlanam records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlanam (MrnPlanam[])
     */
    public MrnPlanam[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnPlanam.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnPlanam[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol            = db.getColNumber(CODE);
        int nameCol            = db.getColNumber(NAME);
        int platformCodeCol    = db.getColNumber(PLATFORM_CODE);
        int callsignCol        = db.getColNumber(CALLSIGN);
        int nodcCountryCodeCol = db.getColNumber(NODC_COUNTRY_CODE);
        int nodcShipCodeCol    = db.getColNumber(NODC_SHIP_CODE);
        MrnPlanam[] cArray = new MrnPlanam[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnPlanam();
            if (codeCol != -1)
                cArray[i].setCode           ((String) row.elementAt(codeCol));
            if (nameCol != -1)
                cArray[i].setName           ((String) row.elementAt(nameCol));
            if (platformCodeCol != -1)
                cArray[i].setPlatformCode   ((String) row.elementAt(platformCodeCol));
            if (callsignCol != -1)
                cArray[i].setCallsign       ((String) row.elementAt(callsignCol));
            if (nodcCountryCodeCol != -1)
                cArray[i].setNodcCountryCode((String) row.elementAt(nodcCountryCodeCol));
            if (nodcShipCodeCol != -1)
                cArray[i].setNodcShipCode   ((String) row.elementAt(nodcShipCodeCol));
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
     *     new MrnPlanam(
     *         INTNULL,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     name            = <val2>,
     *     platformCode    = <val3>,
     *     callsign        = <val4>,
     *     nodcCountryCode = <val5>,
     *     nodcShipCode    = <val6>.
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
     * Boolean success = new MrnPlanam(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where name = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPlanam planam = new MrnPlanam();
     * success = planam.del(MrnPlanam.CODE+"=<val1>");</pre>
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
     * update are taken from the MrnPlanam argument, .e.g.<pre>
     * Boolean success
     * MrnPlanam updMrnPlanam = new MrnPlanam();
     * updMrnPlanam.setName(<val2>);
     * MrnPlanam whereMrnPlanam = new MrnPlanam(<val1>);
     * success = whereMrnPlanam.upd(updMrnPlanam);</pre>
     * will update Name to <val2> for all records where
     * code = <val1>.
     * @param  planam  A MrnPlanam variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlanam planam) {
        return db.update (TABLE, createColVals(planam), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPlanam updMrnPlanam = new MrnPlanam();
     * updMrnPlanam.setName(<val2>);
     * MrnPlanam whereMrnPlanam = new MrnPlanam();
     * success = whereMrnPlanam.upd(
     *     updMrnPlanam, MrnPlanam.CODE+"=<val1>");</pre>
     * will update Name to <val2> for all records where
     * code = <val1>.
     * @param  planam  A MrnPlanam variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlanam planam, String where) {
        return db.update (TABLE, createColVals(planam), where);
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
        if (getName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NAME + "='" + getName() + "'";
        } // if getName
        if (getPlatformCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLATFORM_CODE + "=" + getPlatformCode("");
        } // if getPlatformCode
        if (getCallsign() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CALLSIGN + "='" + getCallsign() + "'";
        } // if getCallsign
        if (getNodcCountryCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NODC_COUNTRY_CODE + "='" + getNodcCountryCode() + "'";
        } // if getNodcCountryCode
        if (getNodcShipCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NODC_SHIP_CODE + "='" + getNodcShipCode() + "'";
        } // if getNodcShipCode
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnPlanam aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NAME +"=";
            colVals += (aVar.getName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getName() + "'");
        } // if aVar.getName
        if (aVar.getPlatformCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLATFORM_CODE +"=";
            colVals += (aVar.getPlatformCode() == INTNULL2 ?
                "null" : aVar.getPlatformCode(""));
        } // if aVar.getPlatformCode
        if (aVar.getCallsign() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CALLSIGN +"=";
            colVals += (aVar.getCallsign().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCallsign() + "'");
        } // if aVar.getCallsign
        if (aVar.getNodcCountryCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NODC_COUNTRY_CODE +"=";
            colVals += (aVar.getNodcCountryCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNodcCountryCode() + "'");
        } // if aVar.getNodcCountryCode
        if (aVar.getNodcShipCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NODC_SHIP_CODE +"=";
            colVals += (aVar.getNodcShipCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNodcShipCode() + "'");
        } // if aVar.getNodcShipCode
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getName() != CHARNULL) {
            columns = columns + "," + NAME;
        } // if getName
        if (getPlatformCode() != INTNULL) {
            columns = columns + "," + PLATFORM_CODE;
        } // if getPlatformCode
        if (getCallsign() != CHARNULL) {
            columns = columns + "," + CALLSIGN;
        } // if getCallsign
        if (getNodcCountryCode() != CHARNULL) {
            columns = columns + "," + NODC_COUNTRY_CODE;
        } // if getNodcCountryCode
        if (getNodcShipCode() != CHARNULL) {
            columns = columns + "," + NODC_SHIP_CODE;
        } // if getNodcShipCode
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
        if (getName() != CHARNULL) {
            values  = values  + ",'" + getName() + "'";
        } // if getName
        if (getPlatformCode() != INTNULL) {
            values  = values  + "," + getPlatformCode("");
        } // if getPlatformCode
        if (getCallsign() != CHARNULL) {
            values  = values  + ",'" + getCallsign() + "'";
        } // if getCallsign
        if (getNodcCountryCode() != CHARNULL) {
            values  = values  + ",'" + getNodcCountryCode() + "'";
        } // if getNodcCountryCode
        if (getNodcShipCode() != CHARNULL) {
            values  = values  + ",'" + getNodcShipCode() + "'";
        } // if getNodcShipCode
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")            + "|" +
            getName("")            + "|" +
            getPlatformCode("")    + "|" +
            getCallsign("")        + "|" +
            getNodcCountryCode("") + "|" +
            getNodcShipCode("")    + "|";
    } // method toString

} // class MrnPlanam
