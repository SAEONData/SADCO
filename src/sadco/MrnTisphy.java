package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the TISPHY table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnTisphy extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "TISPHY";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The deviceCode field name */
    public static final String DEVICE_CODE = "DEVICE_CODE";
    /** The methodCode field name */
    public static final String METHOD_CODE = "METHOD_CODE";
    /** The standardCode field name */
    public static final String STANDARD_CODE = "STANDARD_CODE";
    /** The subdes field name */
    public static final String SUBDES = "SUBDES";
    /** The spldattim field name */
    public static final String SPLDATTIM = "SPLDATTIM";
    /** The spldep field name */
    public static final String SPLDEP = "SPLDEP";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    stationId;
    private int       deviceCode;
    private int       methodCode;
    private int       standardCode;
    private String    subdes;
    private Timestamp spldattim;
    private float     spldep;

    /** The error variables  */
    private int codeError         = ERROR_NORMAL;
    private int stationIdError    = ERROR_NORMAL;
    private int deviceCodeError   = ERROR_NORMAL;
    private int methodCodeError   = ERROR_NORMAL;
    private int standardCodeError = ERROR_NORMAL;
    private int subdesError       = ERROR_NORMAL;
    private int spldattimError    = ERROR_NORMAL;
    private int spldepError       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage         = "";
    private String stationIdErrorMessage    = "";
    private String deviceCodeErrorMessage   = "";
    private String methodCodeErrorMessage   = "";
    private String standardCodeErrorMessage = "";
    private String subdesErrorMessage       = "";
    private String spldattimErrorMessage    = "";
    private String spldepErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       DEVICE_CODE_MN = INTMIN;
    public static final int       DEVICE_CODE_MX = INTMAX;
    public static final int       METHOD_CODE_MN = INTMIN;
    public static final int       METHOD_CODE_MX = INTMAX;
    public static final int       STANDARD_CODE_MN = INTMIN;
    public static final int       STANDARD_CODE_MX = INTMAX;
    public static final Timestamp SPLDATTIM_MN = DATEMIN;
    public static final Timestamp SPLDATTIM_MX = DATEMAX;
    public static final float     SPLDEP_MN = FLOATMIN;
    public static final float     SPLDEP_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception deviceCodeOutOfBoundsException =
        new Exception ("'deviceCode' out of bounds: " +
            DEVICE_CODE_MN + " - " + DEVICE_CODE_MX);
    Exception methodCodeOutOfBoundsException =
        new Exception ("'methodCode' out of bounds: " +
            METHOD_CODE_MN + " - " + METHOD_CODE_MX);
    Exception standardCodeOutOfBoundsException =
        new Exception ("'standardCode' out of bounds: " +
            STANDARD_CODE_MN + " - " + STANDARD_CODE_MX);
    Exception spldattimException =
        new Exception ("'spldattim' is null");
    Exception spldattimOutOfBoundsException =
        new Exception ("'spldattim' out of bounds: " +
            SPLDATTIM_MN + " - " + SPLDATTIM_MX);
    Exception spldepOutOfBoundsException =
        new Exception ("'spldep' out of bounds: " +
            SPLDEP_MN + " - " + SPLDEP_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnTisphy() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnTisphy constructor 1"); // debug
    } // MrnTisphy constructor

    /**
     * Instantiate a MrnTisphy object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MrnTisphy(
            int code) {
        this();
        setCode         (code        );
        if (dbg) System.out.println ("<br>in MrnTisphy constructor 2"); // debug
    } // MrnTisphy constructor

    /**
     * Instantiate a MrnTisphy object and initialize the instance variables.
     * @param code          The code         (int)
     * @param stationId     The stationId    (String)
     * @param deviceCode    The deviceCode   (int)
     * @param methodCode    The methodCode   (int)
     * @param standardCode  The standardCode (int)
     * @param subdes        The subdes       (String)
     * @param spldattim     The spldattim    (java.util.Date)
     * @param spldep        The spldep       (float)
     */
    public MrnTisphy(
            int            code,
            String         stationId,
            int            deviceCode,
            int            methodCode,
            int            standardCode,
            String         subdes,
            java.util.Date spldattim,
            float          spldep) {
        this();
        setCode         (code        );
        setStationId    (stationId   );
        setDeviceCode   (deviceCode  );
        setMethodCode   (methodCode  );
        setStandardCode (standardCode);
        setSubdes       (subdes      );
        setSpldattim    (spldattim   );
        setSpldep       (spldep      );
        if (dbg) System.out.println ("<br>in MrnTisphy constructor 3"); // debug
    } // MrnTisphy constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode         (INTNULL  );
        setStationId    (CHARNULL );
        setDeviceCode   (INTNULL  );
        setMethodCode   (INTNULL  );
        setStandardCode (INTNULL  );
        setSubdes       (CHARNULL );
        setSpldattim    (DATENULL );
        setSpldep       (FLOATNULL);
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
     * Set the 'deviceCode' class variable
     * @param  deviceCode (int)
     */
    public int setDeviceCode(int deviceCode) {
        try {
            if ( ((deviceCode == INTNULL) ||
                  (deviceCode == INTNULL2)) ||
                !((deviceCode < DEVICE_CODE_MN) ||
                  (deviceCode > DEVICE_CODE_MX)) ) {
                this.deviceCode = deviceCode;
                deviceCodeError = ERROR_NORMAL;
            } else {
                throw deviceCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (Integer)
     */
    public int setDeviceCode(Integer deviceCode) {
        try {
            setDeviceCode(deviceCode.intValue());
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (Float)
     */
    public int setDeviceCode(Float deviceCode) {
        try {
            if (deviceCode.floatValue() == FLOATNULL) {
                setDeviceCode(INTNULL);
            } else {
                setDeviceCode(deviceCode.intValue());
            } // if (deviceCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (String)
     */
    public int setDeviceCode(String deviceCode) {
        try {
            setDeviceCode(new Integer(deviceCode).intValue());
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Called when an exception has occured
     * @param  deviceCode (String)
     */
    private void setDeviceCodeError (int deviceCode, Exception e, int error) {
        this.deviceCode = deviceCode;
        deviceCodeErrorMessage = e.toString();
        deviceCodeError = error;
    } // method setDeviceCodeError


    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (int)
     */
    public int setMethodCode(int methodCode) {
        try {
            if ( ((methodCode == INTNULL) ||
                  (methodCode == INTNULL2)) ||
                !((methodCode < METHOD_CODE_MN) ||
                  (methodCode > METHOD_CODE_MX)) ) {
                this.methodCode = methodCode;
                methodCodeError = ERROR_NORMAL;
            } else {
                throw methodCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (Integer)
     */
    public int setMethodCode(Integer methodCode) {
        try {
            setMethodCode(methodCode.intValue());
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (Float)
     */
    public int setMethodCode(Float methodCode) {
        try {
            if (methodCode.floatValue() == FLOATNULL) {
                setMethodCode(INTNULL);
            } else {
                setMethodCode(methodCode.intValue());
            } // if (methodCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (String)
     */
    public int setMethodCode(String methodCode) {
        try {
            setMethodCode(new Integer(methodCode).intValue());
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Called when an exception has occured
     * @param  methodCode (String)
     */
    private void setMethodCodeError (int methodCode, Exception e, int error) {
        this.methodCode = methodCode;
        methodCodeErrorMessage = e.toString();
        methodCodeError = error;
    } // method setMethodCodeError


    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (int)
     */
    public int setStandardCode(int standardCode) {
        try {
            if ( ((standardCode == INTNULL) ||
                  (standardCode == INTNULL2)) ||
                !((standardCode < STANDARD_CODE_MN) ||
                  (standardCode > STANDARD_CODE_MX)) ) {
                this.standardCode = standardCode;
                standardCodeError = ERROR_NORMAL;
            } else {
                throw standardCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (Integer)
     */
    public int setStandardCode(Integer standardCode) {
        try {
            setStandardCode(standardCode.intValue());
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (Float)
     */
    public int setStandardCode(Float standardCode) {
        try {
            if (standardCode.floatValue() == FLOATNULL) {
                setStandardCode(INTNULL);
            } else {
                setStandardCode(standardCode.intValue());
            } // if (standardCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (String)
     */
    public int setStandardCode(String standardCode) {
        try {
            setStandardCode(new Integer(standardCode).intValue());
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Called when an exception has occured
     * @param  standardCode (String)
     */
    private void setStandardCodeError (int standardCode, Exception e, int error) {
        this.standardCode = standardCode;
        standardCodeErrorMessage = e.toString();
        standardCodeError = error;
    } // method setStandardCodeError


    /**
     * Set the 'subdes' class variable
     * @param  subdes (String)
     */
    public int setSubdes(String subdes) {
        try {
            this.subdes = subdes;
            if (this.subdes != CHARNULL) {
                this.subdes = stripCRLF(this.subdes.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>subdes = " + this.subdes);
        } catch (Exception e) {
            setSubdesError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return subdesError;
    } // method setSubdes

    /**
     * Called when an exception has occured
     * @param  subdes (String)
     */
    private void setSubdesError (String subdes, Exception e, int error) {
        this.subdes = subdes;
        subdesErrorMessage = e.toString();
        subdesError = error;
    } // method setSubdesError


    /**
     * Set the 'spldattim' class variable
     * @param  spldattim (Timestamp)
     */
    public int setSpldattim(Timestamp spldattim) {
        try {
            if (spldattim == null) { this.spldattim = DATENULL; }
            if ( (spldattim.equals(DATENULL) ||
                  spldattim.equals(DATENULL2)) ||
                !(spldattim.before(SPLDATTIM_MN) ||
                  spldattim.after(SPLDATTIM_MX)) ) {
                this.spldattim = spldattim;
                spldattimError = ERROR_NORMAL;
            } else {
                throw spldattimOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpldattimError(DATENULL, e, ERROR_LOCAL);
        } // try
        return spldattimError;
    } // method setSpldattim

    /**
     * Set the 'spldattim' class variable
     * @param  spldattim (java.util.Date)
     */
    public int setSpldattim(java.util.Date spldattim) {
        try {
            setSpldattim(new Timestamp(spldattim.getTime()));
        } catch (Exception e) {
            setSpldattimError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return spldattimError;
    } // method setSpldattim

    /**
     * Set the 'spldattim' class variable
     * @param  spldattim (String)
     */
    public int setSpldattim(String spldattim) {
        try {
            int len = spldattim.length();
            switch (len) {
            // date &/or times
                case 4: spldattim += "-01";
                case 7: spldattim += "-01";
                case 10: spldattim += " 00";
                case 13: spldattim += ":00";
                case 16: spldattim += ":00"; break;
                // times only
                case 5: spldattim = "1800-01-01 " + spldattim + ":00"; break;
                case 8: spldattim = "1800-01-01 " + spldattim; break;
            } // switch
            if (dbg) System.out.println ("spldattim = " + spldattim);
            setSpldattim(Timestamp.valueOf(spldattim));
        } catch (Exception e) {
            setSpldattimError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return spldattimError;
    } // method setSpldattim

    /**
     * Called when an exception has occured
     * @param  spldattim (String)
     */
    private void setSpldattimError (Timestamp spldattim, Exception e, int error) {
        this.spldattim = spldattim;
        spldattimErrorMessage = e.toString();
        spldattimError = error;
    } // method setSpldattimError


    /**
     * Set the 'spldep' class variable
     * @param  spldep (float)
     */
    public int setSpldep(float spldep) {
        try {
            if ( ((spldep == FLOATNULL) ||
                  (spldep == FLOATNULL2)) ||
                !((spldep < SPLDEP_MN) ||
                  (spldep > SPLDEP_MX)) ) {
                this.spldep = spldep;
                spldepError = ERROR_NORMAL;
            } else {
                throw spldepOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (Integer)
     */
    public int setSpldep(Integer spldep) {
        try {
            setSpldep(spldep.floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (Float)
     */
    public int setSpldep(Float spldep) {
        try {
            setSpldep(spldep.floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (String)
     */
    public int setSpldep(String spldep) {
        try {
            setSpldep(new Float(spldep).floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Called when an exception has occured
     * @param  spldep (String)
     */
    private void setSpldepError (float spldep, Exception e, int error) {
        this.spldep = spldep;
        spldepErrorMessage = e.toString();
        spldepError = error;
    } // method setSpldepError


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
     * Return the 'deviceCode' class variable
     * @return deviceCode (int)
     */
    public int getDeviceCode() {
        return deviceCode;
    } // method getDeviceCode

    /**
     * Return the 'deviceCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDeviceCode methods
     * @return deviceCode (String)
     */
    public String getDeviceCode(String s) {
        return ((deviceCode != INTNULL) ? new Integer(deviceCode).toString() : "");
    } // method getDeviceCode


    /**
     * Return the 'methodCode' class variable
     * @return methodCode (int)
     */
    public int getMethodCode() {
        return methodCode;
    } // method getMethodCode

    /**
     * Return the 'methodCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMethodCode methods
     * @return methodCode (String)
     */
    public String getMethodCode(String s) {
        return ((methodCode != INTNULL) ? new Integer(methodCode).toString() : "");
    } // method getMethodCode


    /**
     * Return the 'standardCode' class variable
     * @return standardCode (int)
     */
    public int getStandardCode() {
        return standardCode;
    } // method getStandardCode

    /**
     * Return the 'standardCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStandardCode methods
     * @return standardCode (String)
     */
    public String getStandardCode(String s) {
        return ((standardCode != INTNULL) ? new Integer(standardCode).toString() : "");
    } // method getStandardCode


    /**
     * Return the 'subdes' class variable
     * @return subdes (String)
     */
    public String getSubdes() {
        return subdes;
    } // method getSubdes

    /**
     * Return the 'subdes' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSubdes methods
     * @return subdes (String)
     */
    public String getSubdes(String s) {
        return (subdes != CHARNULL ? subdes.replace('"','\'') : "");
    } // method getSubdes


    /**
     * Return the 'spldattim' class variable
     * @return spldattim (Timestamp)
     */
    public Timestamp getSpldattim() {
        return spldattim;
    } // method getSpldattim

    /**
     * Return the 'spldattim' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpldattim methods
     * @return spldattim (String)
     */
    public String getSpldattim(String s) {
        if (spldattim.equals(DATENULL)) {
            return ("");
        } else {
            String spldattimStr = spldattim.toString();
            return spldattimStr.substring(0,spldattimStr.indexOf('.'));
        } // if
    } // method getSpldattim


    /**
     * Return the 'spldep' class variable
     * @return spldep (float)
     */
    public float getSpldep() {
        return spldep;
    } // method getSpldep

    /**
     * Return the 'spldep' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpldep methods
     * @return spldep (String)
     */
    public String getSpldep(String s) {
        return ((spldep != FLOATNULL) ? new Float(spldep).toString() : "");
    } // method getSpldep


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
            (stationId == CHARNULL) &&
            (deviceCode == INTNULL) &&
            (methodCode == INTNULL) &&
            (standardCode == INTNULL) &&
            (subdes == CHARNULL) &&
            (spldattim.equals(DATENULL)) &&
            (spldep == FLOATNULL)) {
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
            stationIdError +
            deviceCodeError +
            methodCodeError +
            standardCodeError +
            subdesError +
            spldattimError +
            spldepError;
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
     * Gets the errorcode for the deviceCode instance variable
     * @return errorcode (int)
     */
    public int getDeviceCodeError() {
        return deviceCodeError;
    } // method getDeviceCodeError

    /**
     * Gets the errorMessage for the deviceCode instance variable
     * @return errorMessage (String)
     */
    public String getDeviceCodeErrorMessage() {
        return deviceCodeErrorMessage;
    } // method getDeviceCodeErrorMessage


    /**
     * Gets the errorcode for the methodCode instance variable
     * @return errorcode (int)
     */
    public int getMethodCodeError() {
        return methodCodeError;
    } // method getMethodCodeError

    /**
     * Gets the errorMessage for the methodCode instance variable
     * @return errorMessage (String)
     */
    public String getMethodCodeErrorMessage() {
        return methodCodeErrorMessage;
    } // method getMethodCodeErrorMessage


    /**
     * Gets the errorcode for the standardCode instance variable
     * @return errorcode (int)
     */
    public int getStandardCodeError() {
        return standardCodeError;
    } // method getStandardCodeError

    /**
     * Gets the errorMessage for the standardCode instance variable
     * @return errorMessage (String)
     */
    public String getStandardCodeErrorMessage() {
        return standardCodeErrorMessage;
    } // method getStandardCodeErrorMessage


    /**
     * Gets the errorcode for the subdes instance variable
     * @return errorcode (int)
     */
    public int getSubdesError() {
        return subdesError;
    } // method getSubdesError

    /**
     * Gets the errorMessage for the subdes instance variable
     * @return errorMessage (String)
     */
    public String getSubdesErrorMessage() {
        return subdesErrorMessage;
    } // method getSubdesErrorMessage


    /**
     * Gets the errorcode for the spldattim instance variable
     * @return errorcode (int)
     */
    public int getSpldattimError() {
        return spldattimError;
    } // method getSpldattimError

    /**
     * Gets the errorMessage for the spldattim instance variable
     * @return errorMessage (String)
     */
    public String getSpldattimErrorMessage() {
        return spldattimErrorMessage;
    } // method getSpldattimErrorMessage


    /**
     * Gets the errorcode for the spldep instance variable
     * @return errorcode (int)
     */
    public int getSpldepError() {
        return spldepError;
    } // method getSpldepError

    /**
     * Gets the errorMessage for the spldep instance variable
     * @return errorMessage (String)
     */
    public String getSpldepErrorMessage() {
        return spldepErrorMessage;
    } // method getSpldepErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnTisphy. e.g.<pre>
     * MrnTisphy tisphy = new MrnTisphy(<val1>);
     * MrnTisphy tisphyArray[] = tisphy.get();</pre>
     * will get the MrnTisphy record where code = <val1>.
     * @return Array of MrnTisphy (MrnTisphy[])
     */
    public MrnTisphy[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnTisphy tisphyArray[] =
     *     new MrnTisphy().get(MrnTisphy.CODE+"=<val1>");</pre>
     * will get the MrnTisphy record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnTisphy (MrnTisphy[])
     */
    public MrnTisphy[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnTisphy tisphyArray[] =
     *     new MrnTisphy().get("1=1",tisphy.CODE);</pre>
     * will get the all the MrnTisphy records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnTisphy (MrnTisphy[])
     */
    public MrnTisphy[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnTisphy.CODE,MrnTisphy.STATION_ID;
     * String where = MrnTisphy.CODE + "=<val1";
     * String order = MrnTisphy.CODE;
     * MrnTisphy tisphyArray[] =
     *     new MrnTisphy().get(columns, where, order);</pre>
     * will get the code and stationId colums of all MrnTisphy records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnTisphy (MrnTisphy[])
     */
    public MrnTisphy[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnTisphy.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnTisphy[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol         = db.getColNumber(CODE);
        int stationIdCol    = db.getColNumber(STATION_ID);
        int deviceCodeCol   = db.getColNumber(DEVICE_CODE);
        int methodCodeCol   = db.getColNumber(METHOD_CODE);
        int standardCodeCol = db.getColNumber(STANDARD_CODE);
        int subdesCol       = db.getColNumber(SUBDES);
        int spldattimCol    = db.getColNumber(SPLDATTIM);
        int spldepCol       = db.getColNumber(SPLDEP);
        MrnTisphy[] cArray = new MrnTisphy[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnTisphy();
            if (codeCol != -1)
                cArray[i].setCode        ((String) row.elementAt(codeCol));
            if (stationIdCol != -1)
                cArray[i].setStationId   ((String) row.elementAt(stationIdCol));
            if (deviceCodeCol != -1)
                cArray[i].setDeviceCode  ((String) row.elementAt(deviceCodeCol));
            if (methodCodeCol != -1)
                cArray[i].setMethodCode  ((String) row.elementAt(methodCodeCol));
            if (standardCodeCol != -1)
                cArray[i].setStandardCode((String) row.elementAt(standardCodeCol));
            if (subdesCol != -1)
                cArray[i].setSubdes      ((String) row.elementAt(subdesCol));
            if (spldattimCol != -1)
                cArray[i].setSpldattim   ((String) row.elementAt(spldattimCol));
            if (spldepCol != -1)
                cArray[i].setSpldep      ((String) row.elementAt(spldepCol));
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
     *     new MrnTisphy(
     *         INTNULL,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     stationId    = <val2>,
     *     deviceCode   = <val3>,
     *     methodCode   = <val4>,
     *     standardCode = <val5>,
     *     subdes       = <val6>,
     *     spldattim    = <val7>,
     *     spldep       = <val8>.
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
     * Boolean success = new MrnTisphy(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL).del();</pre>
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
     * MrnTisphy tisphy = new MrnTisphy();
     * success = tisphy.del(MrnTisphy.CODE+"=<val1>");</pre>
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
     * update are taken from the MrnTisphy argument, .e.g.<pre>
     * Boolean success
     * MrnTisphy updMrnTisphy = new MrnTisphy();
     * updMrnTisphy.setStationId(<val2>);
     * MrnTisphy whereMrnTisphy = new MrnTisphy(<val1>);
     * success = whereMrnTisphy.upd(updMrnTisphy);</pre>
     * will update StationId to <val2> for all records where
     * code = <val1>.
     * @param  tisphy  A MrnTisphy variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnTisphy tisphy) {
        return db.update (TABLE, createColVals(tisphy), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnTisphy updMrnTisphy = new MrnTisphy();
     * updMrnTisphy.setStationId(<val2>);
     * MrnTisphy whereMrnTisphy = new MrnTisphy();
     * success = whereMrnTisphy.upd(
     *     updMrnTisphy, MrnTisphy.CODE+"=<val1>");</pre>
     * will update StationId to <val2> for all records where
     * code = <val1>.
     * @param  tisphy  A MrnTisphy variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnTisphy tisphy, String where) {
        return db.update (TABLE, createColVals(tisphy), where);
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
        if (getStationId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATION_ID + "='" + getStationId() + "'";
        } // if getStationId
        if (getDeviceCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEVICE_CODE + "=" + getDeviceCode("");
        } // if getDeviceCode
        if (getMethodCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + METHOD_CODE + "=" + getMethodCode("");
        } // if getMethodCode
        if (getStandardCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STANDARD_CODE + "=" + getStandardCode("");
        } // if getStandardCode
        if (getSubdes() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SUBDES + "='" + getSubdes() + "'";
        } // if getSubdes
        if (!getSpldattim().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPLDATTIM +
                "=" + Tables.getDateFormat(getSpldattim());
        } // if getSpldattim
        if (getSpldep() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPLDEP + "=" + getSpldep("");
        } // if getSpldep
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnTisphy aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getDeviceCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEVICE_CODE +"=";
            colVals += (aVar.getDeviceCode() == INTNULL2 ?
                "null" : aVar.getDeviceCode(""));
        } // if aVar.getDeviceCode
        if (aVar.getMethodCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += METHOD_CODE +"=";
            colVals += (aVar.getMethodCode() == INTNULL2 ?
                "null" : aVar.getMethodCode(""));
        } // if aVar.getMethodCode
        if (aVar.getStandardCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STANDARD_CODE +"=";
            colVals += (aVar.getStandardCode() == INTNULL2 ?
                "null" : aVar.getStandardCode(""));
        } // if aVar.getStandardCode
        if (aVar.getSubdes() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SUBDES +"=";
            colVals += (aVar.getSubdes().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSubdes() + "'");
        } // if aVar.getSubdes
        if (!aVar.getSpldattim().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPLDATTIM +"=";
            colVals += (aVar.getSpldattim().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getSpldattim()));
        } // if aVar.getSpldattim
        if (aVar.getSpldep() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPLDEP +"=";
            colVals += (aVar.getSpldep() == FLOATNULL2 ?
                "null" : aVar.getSpldep(""));
        } // if aVar.getSpldep
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getStationId() != CHARNULL) {
            columns = columns + "," + STATION_ID;
        } // if getStationId
        if (getDeviceCode() != INTNULL) {
            columns = columns + "," + DEVICE_CODE;
        } // if getDeviceCode
        if (getMethodCode() != INTNULL) {
            columns = columns + "," + METHOD_CODE;
        } // if getMethodCode
        if (getStandardCode() != INTNULL) {
            columns = columns + "," + STANDARD_CODE;
        } // if getStandardCode
        if (getSubdes() != CHARNULL) {
            columns = columns + "," + SUBDES;
        } // if getSubdes
        if (!getSpldattim().equals(DATENULL)) {
            columns = columns + "," + SPLDATTIM;
        } // if getSpldattim
        if (getSpldep() != FLOATNULL) {
            columns = columns + "," + SPLDEP;
        } // if getSpldep
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
        if (getStationId() != CHARNULL) {
            values  = values  + ",'" + getStationId() + "'";
        } // if getStationId
        if (getDeviceCode() != INTNULL) {
            values  = values  + "," + getDeviceCode("");
        } // if getDeviceCode
        if (getMethodCode() != INTNULL) {
            values  = values  + "," + getMethodCode("");
        } // if getMethodCode
        if (getStandardCode() != INTNULL) {
            values  = values  + "," + getStandardCode("");
        } // if getStandardCode
        if (getSubdes() != CHARNULL) {
            values  = values  + ",'" + getSubdes() + "'";
        } // if getSubdes
        if (!getSpldattim().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getSpldattim());
        } // if getSpldattim
        if (getSpldep() != FLOATNULL) {
            values  = values  + "," + getSpldep("");
        } // if getSpldep
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")         + "|" +
            getStationId("")    + "|" +
            getDeviceCode("")   + "|" +
            getMethodCode("")   + "|" +
            getStandardCode("") + "|" +
            getSubdes("")       + "|" +
            getSpldattim("")    + "|" +
            getSpldep("")       + "|";
    } // method toString

} // class MrnTisphy
