package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WATPHY table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 090710 - SIT Group
 * @version
 * 090710 - GenTableClassB class - create class<br>
 */
public class MrnWatphy extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WATPHY";
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
    /** The filtered field name */
    public static final String FILTERED = "FILTERED";
    /** The disoxygen field name */
    public static final String DISOXYGEN = "DISOXYGEN";
    /** The salinity field name */
    public static final String SALINITY = "SALINITY";
    /** The temperature field name */
    public static final String TEMPERATURE = "TEMPERATURE";
    /** The soundFlag field name */
    public static final String SOUND_FLAG = "SOUND_FLAG";
    /** The soundv field name */
    public static final String SOUNDV = "SOUNDV";
    /** The turbidity field name */
    public static final String TURBIDITY = "TURBIDITY";
    /** The pressure field name */
    public static final String PRESSURE = "PRESSURE";
    /** The fluorescence field name */
    public static final String FLUORESCENCE = "FLUORESCENCE";

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
    private String    filtered;
    private float     disoxygen;
    private float     salinity;
    private float     temperature;
    private String    soundFlag;
    private float     soundv;
    private float     turbidity;
    private float     pressure;
    private float     fluorescence;

    /** The error variables  */
    private int codeError         = ERROR_NORMAL;
    private int stationIdError    = ERROR_NORMAL;
    private int deviceCodeError   = ERROR_NORMAL;
    private int methodCodeError   = ERROR_NORMAL;
    private int standardCodeError = ERROR_NORMAL;
    private int subdesError       = ERROR_NORMAL;
    private int spldattimError    = ERROR_NORMAL;
    private int spldepError       = ERROR_NORMAL;
    private int filteredError     = ERROR_NORMAL;
    private int disoxygenError    = ERROR_NORMAL;
    private int salinityError     = ERROR_NORMAL;
    private int temperatureError  = ERROR_NORMAL;
    private int soundFlagError    = ERROR_NORMAL;
    private int soundvError       = ERROR_NORMAL;
    private int turbidityError    = ERROR_NORMAL;
    private int pressureError     = ERROR_NORMAL;
    private int fluorescenceError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage         = "";
    private String stationIdErrorMessage    = "";
    private String deviceCodeErrorMessage   = "";
    private String methodCodeErrorMessage   = "";
    private String standardCodeErrorMessage = "";
    private String subdesErrorMessage       = "";
    private String spldattimErrorMessage    = "";
    private String spldepErrorMessage       = "";
    private String filteredErrorMessage     = "";
    private String disoxygenErrorMessage    = "";
    private String salinityErrorMessage     = "";
    private String temperatureErrorMessage  = "";
    private String soundFlagErrorMessage    = "";
    private String soundvErrorMessage       = "";
    private String turbidityErrorMessage    = "";
    private String pressureErrorMessage     = "";
    private String fluorescenceErrorMessage = "";

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
    public static final float     DISOXYGEN_MN = FLOATMIN;
    public static final float     DISOXYGEN_MX = FLOATMAX;
    public static final float     SALINITY_MN = FLOATMIN;
    public static final float     SALINITY_MX = FLOATMAX;
    public static final float     TEMPERATURE_MN = FLOATMIN;
    public static final float     TEMPERATURE_MX = FLOATMAX;
    public static final float     SOUNDV_MN = FLOATMIN;
    public static final float     SOUNDV_MX = FLOATMAX;
    public static final float     TURBIDITY_MN = FLOATMIN;
    public static final float     TURBIDITY_MX = FLOATMAX;
    public static final float     PRESSURE_MN = FLOATMIN;
    public static final float     PRESSURE_MX = FLOATMAX;
    public static final float     FLUORESCENCE_MN = FLOATMIN;
    public static final float     FLUORESCENCE_MX = FLOATMAX;

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
    Exception disoxygenOutOfBoundsException =
        new Exception ("'disoxygen' out of bounds: " +
            DISOXYGEN_MN + " - " + DISOXYGEN_MX);
    Exception salinityOutOfBoundsException =
        new Exception ("'salinity' out of bounds: " +
            SALINITY_MN + " - " + SALINITY_MX);
    Exception temperatureOutOfBoundsException =
        new Exception ("'temperature' out of bounds: " +
            TEMPERATURE_MN + " - " + TEMPERATURE_MX);
    Exception soundvOutOfBoundsException =
        new Exception ("'soundv' out of bounds: " +
            SOUNDV_MN + " - " + SOUNDV_MX);
    Exception turbidityOutOfBoundsException =
        new Exception ("'turbidity' out of bounds: " +
            TURBIDITY_MN + " - " + TURBIDITY_MX);
    Exception pressureOutOfBoundsException =
        new Exception ("'pressure' out of bounds: " +
            PRESSURE_MN + " - " + PRESSURE_MX);
    Exception fluorescenceOutOfBoundsException =
        new Exception ("'fluorescence' out of bounds: " +
            FLUORESCENCE_MN + " - " + FLUORESCENCE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnWatphy() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnWatphy constructor 1"); // debug
    } // MrnWatphy constructor

    /**
     * Instantiate a MrnWatphy object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MrnWatphy(
            int code) {
        this();
        setCode         (code        );
        if (dbg) System.out.println ("<br>in MrnWatphy constructor 2"); // debug
    } // MrnWatphy constructor

    /**
     * Instantiate a MrnWatphy object and initialize the instance variables.
     * @param code          The code         (int)
     * @param stationId     The stationId    (String)
     * @param deviceCode    The deviceCode   (int)
     * @param methodCode    The methodCode   (int)
     * @param standardCode  The standardCode (int)
     * @param subdes        The subdes       (String)
     * @param spldattim     The spldattim    (java.util.Date)
     * @param spldep        The spldep       (float)
     * @param filtered      The filtered     (String)
     * @param disoxygen     The disoxygen    (float)
     * @param salinity      The salinity     (float)
     * @param temperature   The temperature  (float)
     * @param soundFlag     The soundFlag    (String)
     * @param soundv        The soundv       (float)
     * @param turbidity     The turbidity    (float)
     * @param pressure      The pressure     (float)
     * @param fluorescence  The fluorescence (float)
     * @return A MrnWatphy object
     */
    public MrnWatphy(
            int            code,
            String         stationId,
            int            deviceCode,
            int            methodCode,
            int            standardCode,
            String         subdes,
            java.util.Date spldattim,
            float          spldep,
            String         filtered,
            float          disoxygen,
            float          salinity,
            float          temperature,
            String         soundFlag,
            float          soundv,
            float          turbidity,
            float          pressure,
            float          fluorescence) {
        this();
        setCode         (code        );
        setStationId    (stationId   );
        setDeviceCode   (deviceCode  );
        setMethodCode   (methodCode  );
        setStandardCode (standardCode);
        setSubdes       (subdes      );
        setSpldattim    (spldattim   );
        setSpldep       (spldep      );
        setFiltered     (filtered    );
        setDisoxygen    (disoxygen   );
        setSalinity     (salinity    );
        setTemperature  (temperature );
        setSoundFlag    (soundFlag   );
        setSoundv       (soundv      );
        setTurbidity    (turbidity   );
        setPressure     (pressure    );
        setFluorescence (fluorescence);
        if (dbg) System.out.println ("<br>in MrnWatphy constructor 3"); // debug
    } // MrnWatphy constructor

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
        setFiltered     (CHARNULL );
        setDisoxygen    (FLOATNULL);
        setSalinity     (FLOATNULL);
        setTemperature  (FLOATNULL);
        setSoundFlag    (CHARNULL );
        setSoundv       (FLOATNULL);
        setTurbidity    (FLOATNULL);
        setPressure     (FLOATNULL);
        setFluorescence (FLOATNULL);
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


    /**
     * Set the 'filtered' class variable
     * @param  filtered (String)
     */
    public int setFiltered(String filtered) {
        try {
            this.filtered = filtered;
            if (this.filtered != CHARNULL) {
                this.filtered = stripCRLF(this.filtered.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>filtered = " + this.filtered);
        } catch (Exception e) {
            setFilteredError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return filteredError;
    } // method setFiltered

    /**
     * Called when an exception has occured
     * @param  filtered (String)
     */
    private void setFilteredError (String filtered, Exception e, int error) {
        this.filtered = filtered;
        filteredErrorMessage = e.toString();
        filteredError = error;
    } // method setFilteredError


    /**
     * Set the 'disoxygen' class variable
     * @param  disoxygen (float)
     */
    public int setDisoxygen(float disoxygen) {
        try {
            if ( ((disoxygen == FLOATNULL) || 
                  (disoxygen == FLOATNULL2)) ||
                !((disoxygen < DISOXYGEN_MN) ||
                  (disoxygen > DISOXYGEN_MX)) ) {
                this.disoxygen = disoxygen;
                disoxygenError = ERROR_NORMAL;
            } else {
                throw disoxygenOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDisoxygenError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return disoxygenError;
    } // method setDisoxygen

    /**
     * Set the 'disoxygen' class variable
     * @param  disoxygen (Integer)
     */
    public int setDisoxygen(Integer disoxygen) {
        try {
            setDisoxygen(disoxygen.floatValue());
        } catch (Exception e) {
            setDisoxygenError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return disoxygenError;
    } // method setDisoxygen

    /**
     * Set the 'disoxygen' class variable
     * @param  disoxygen (Float)
     */
    public int setDisoxygen(Float disoxygen) {
        try {
            setDisoxygen(disoxygen.floatValue());
        } catch (Exception e) {
            setDisoxygenError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return disoxygenError;
    } // method setDisoxygen

    /**
     * Set the 'disoxygen' class variable
     * @param  disoxygen (String)
     */
    public int setDisoxygen(String disoxygen) {
        try {
            setDisoxygen(new Float(disoxygen).floatValue());
        } catch (Exception e) {
            setDisoxygenError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return disoxygenError;
    } // method setDisoxygen

    /**
     * Called when an exception has occured
     * @param  disoxygen (String)
     */
    private void setDisoxygenError (float disoxygen, Exception e, int error) {
        this.disoxygen = disoxygen;
        disoxygenErrorMessage = e.toString();
        disoxygenError = error;
    } // method setDisoxygenError


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
     * Set the 'temperature' class variable
     * @param  temperature (float)
     */
    public int setTemperature(float temperature) {
        try {
            if ( ((temperature == FLOATNULL) || 
                  (temperature == FLOATNULL2)) ||
                !((temperature < TEMPERATURE_MN) ||
                  (temperature > TEMPERATURE_MX)) ) {
                this.temperature = temperature;
                temperatureError = ERROR_NORMAL;
            } else {
                throw temperatureOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTemperatureError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Set the 'temperature' class variable
     * @param  temperature (Integer)
     */
    public int setTemperature(Integer temperature) {
        try {
            setTemperature(temperature.floatValue());
        } catch (Exception e) {
            setTemperatureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Set the 'temperature' class variable
     * @param  temperature (Float)
     */
    public int setTemperature(Float temperature) {
        try {
            setTemperature(temperature.floatValue());
        } catch (Exception e) {
            setTemperatureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Set the 'temperature' class variable
     * @param  temperature (String)
     */
    public int setTemperature(String temperature) {
        try {
            setTemperature(new Float(temperature).floatValue());
        } catch (Exception e) {
            setTemperatureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Called when an exception has occured
     * @param  temperature (String)
     */
    private void setTemperatureError (float temperature, Exception e, int error) {
        this.temperature = temperature;
        temperatureErrorMessage = e.toString();
        temperatureError = error;
    } // method setTemperatureError


    /**
     * Set the 'soundFlag' class variable
     * @param  soundFlag (String)
     */
    public int setSoundFlag(String soundFlag) {
        try {
            this.soundFlag = soundFlag;
            if (this.soundFlag != CHARNULL) {
                this.soundFlag = stripCRLF(this.soundFlag.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>soundFlag = " + this.soundFlag);
        } catch (Exception e) {
            setSoundFlagError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return soundFlagError;
    } // method setSoundFlag

    /**
     * Called when an exception has occured
     * @param  soundFlag (String)
     */
    private void setSoundFlagError (String soundFlag, Exception e, int error) {
        this.soundFlag = soundFlag;
        soundFlagErrorMessage = e.toString();
        soundFlagError = error;
    } // method setSoundFlagError


    /**
     * Set the 'soundv' class variable
     * @param  soundv (float)
     */
    public int setSoundv(float soundv) {
        try {
            if ( ((soundv == FLOATNULL) || 
                  (soundv == FLOATNULL2)) ||
                !((soundv < SOUNDV_MN) ||
                  (soundv > SOUNDV_MX)) ) {
                this.soundv = soundv;
                soundvError = ERROR_NORMAL;
            } else {
                throw soundvOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSoundvError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return soundvError;
    } // method setSoundv

    /**
     * Set the 'soundv' class variable
     * @param  soundv (Integer)
     */
    public int setSoundv(Integer soundv) {
        try {
            setSoundv(soundv.floatValue());
        } catch (Exception e) {
            setSoundvError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return soundvError;
    } // method setSoundv

    /**
     * Set the 'soundv' class variable
     * @param  soundv (Float)
     */
    public int setSoundv(Float soundv) {
        try {
            setSoundv(soundv.floatValue());
        } catch (Exception e) {
            setSoundvError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return soundvError;
    } // method setSoundv

    /**
     * Set the 'soundv' class variable
     * @param  soundv (String)
     */
    public int setSoundv(String soundv) {
        try {
            setSoundv(new Float(soundv).floatValue());
        } catch (Exception e) {
            setSoundvError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return soundvError;
    } // method setSoundv

    /**
     * Called when an exception has occured
     * @param  soundv (String)
     */
    private void setSoundvError (float soundv, Exception e, int error) {
        this.soundv = soundv;
        soundvErrorMessage = e.toString();
        soundvError = error;
    } // method setSoundvError


    /**
     * Set the 'turbidity' class variable
     * @param  turbidity (float)
     */
    public int setTurbidity(float turbidity) {
        try {
            if ( ((turbidity == FLOATNULL) || 
                  (turbidity == FLOATNULL2)) ||
                !((turbidity < TURBIDITY_MN) ||
                  (turbidity > TURBIDITY_MX)) ) {
                this.turbidity = turbidity;
                turbidityError = ERROR_NORMAL;
            } else {
                throw turbidityOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTurbidityError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return turbidityError;
    } // method setTurbidity

    /**
     * Set the 'turbidity' class variable
     * @param  turbidity (Integer)
     */
    public int setTurbidity(Integer turbidity) {
        try {
            setTurbidity(turbidity.floatValue());
        } catch (Exception e) {
            setTurbidityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return turbidityError;
    } // method setTurbidity

    /**
     * Set the 'turbidity' class variable
     * @param  turbidity (Float)
     */
    public int setTurbidity(Float turbidity) {
        try {
            setTurbidity(turbidity.floatValue());
        } catch (Exception e) {
            setTurbidityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return turbidityError;
    } // method setTurbidity

    /**
     * Set the 'turbidity' class variable
     * @param  turbidity (String)
     */
    public int setTurbidity(String turbidity) {
        try {
            setTurbidity(new Float(turbidity).floatValue());
        } catch (Exception e) {
            setTurbidityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return turbidityError;
    } // method setTurbidity

    /**
     * Called when an exception has occured
     * @param  turbidity (String)
     */
    private void setTurbidityError (float turbidity, Exception e, int error) {
        this.turbidity = turbidity;
        turbidityErrorMessage = e.toString();
        turbidityError = error;
    } // method setTurbidityError


    /**
     * Set the 'pressure' class variable
     * @param  pressure (float)
     */
    public int setPressure(float pressure) {
        try {
            if ( ((pressure == FLOATNULL) || 
                  (pressure == FLOATNULL2)) ||
                !((pressure < PRESSURE_MN) ||
                  (pressure > PRESSURE_MX)) ) {
                this.pressure = pressure;
                pressureError = ERROR_NORMAL;
            } else {
                throw pressureOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPressureError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return pressureError;
    } // method setPressure

    /**
     * Set the 'pressure' class variable
     * @param  pressure (Integer)
     */
    public int setPressure(Integer pressure) {
        try {
            setPressure(pressure.floatValue());
        } catch (Exception e) {
            setPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pressureError;
    } // method setPressure

    /**
     * Set the 'pressure' class variable
     * @param  pressure (Float)
     */
    public int setPressure(Float pressure) {
        try {
            setPressure(pressure.floatValue());
        } catch (Exception e) {
            setPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pressureError;
    } // method setPressure

    /**
     * Set the 'pressure' class variable
     * @param  pressure (String)
     */
    public int setPressure(String pressure) {
        try {
            setPressure(new Float(pressure).floatValue());
        } catch (Exception e) {
            setPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pressureError;
    } // method setPressure

    /**
     * Called when an exception has occured
     * @param  pressure (String)
     */
    private void setPressureError (float pressure, Exception e, int error) {
        this.pressure = pressure;
        pressureErrorMessage = e.toString();
        pressureError = error;
    } // method setPressureError


    /**
     * Set the 'fluorescence' class variable
     * @param  fluorescence (float)
     */
    public int setFluorescence(float fluorescence) {
        try {
            if ( ((fluorescence == FLOATNULL) || 
                  (fluorescence == FLOATNULL2)) ||
                !((fluorescence < FLUORESCENCE_MN) ||
                  (fluorescence > FLUORESCENCE_MX)) ) {
                this.fluorescence = fluorescence;
                fluorescenceError = ERROR_NORMAL;
            } else {
                throw fluorescenceOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFluorescenceError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return fluorescenceError;
    } // method setFluorescence

    /**
     * Set the 'fluorescence' class variable
     * @param  fluorescence (Integer)
     */
    public int setFluorescence(Integer fluorescence) {
        try {
            setFluorescence(fluorescence.floatValue());
        } catch (Exception e) {
            setFluorescenceError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fluorescenceError;
    } // method setFluorescence

    /**
     * Set the 'fluorescence' class variable
     * @param  fluorescence (Float)
     */
    public int setFluorescence(Float fluorescence) {
        try {
            setFluorescence(fluorescence.floatValue());
        } catch (Exception e) {
            setFluorescenceError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fluorescenceError;
    } // method setFluorescence

    /**
     * Set the 'fluorescence' class variable
     * @param  fluorescence (String)
     */
    public int setFluorescence(String fluorescence) {
        try {
            setFluorescence(new Float(fluorescence).floatValue());
        } catch (Exception e) {
            setFluorescenceError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fluorescenceError;
    } // method setFluorescence

    /**
     * Called when an exception has occured
     * @param  fluorescence (String)
     */
    private void setFluorescenceError (float fluorescence, Exception e, int error) {
        this.fluorescence = fluorescence;
        fluorescenceErrorMessage = e.toString();
        fluorescenceError = error;
    } // method setFluorescenceError


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
     * Return the 'filtered' class variable
     * @return filtered (String)
     */
    public String getFiltered() {
        return filtered;
    } // method getFiltered

    /**
     * Return the 'filtered' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFiltered methods
     * @return filtered (String)
     */
    public String getFiltered(String s) {
        return (filtered != CHARNULL ? filtered.replace('"','\'') : "");
    } // method getFiltered


    /**
     * Return the 'disoxygen' class variable
     * @return disoxygen (float)
     */
    public float getDisoxygen() {
        return disoxygen;
    } // method getDisoxygen

    /**
     * Return the 'disoxygen' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDisoxygen methods
     * @return disoxygen (String)
     */
    public String getDisoxygen(String s) {
        return ((disoxygen != FLOATNULL) ? new Float(disoxygen).toString() : "");
    } // method getDisoxygen


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
     * Return the 'temperature' class variable
     * @return temperature (float)
     */
    public float getTemperature() {
        return temperature;
    } // method getTemperature

    /**
     * Return the 'temperature' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTemperature methods
     * @return temperature (String)
     */
    public String getTemperature(String s) {
        return ((temperature != FLOATNULL) ? new Float(temperature).toString() : "");
    } // method getTemperature


    /**
     * Return the 'soundFlag' class variable
     * @return soundFlag (String)
     */
    public String getSoundFlag() {
        return soundFlag;
    } // method getSoundFlag

    /**
     * Return the 'soundFlag' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSoundFlag methods
     * @return soundFlag (String)
     */
    public String getSoundFlag(String s) {
        return (soundFlag != CHARNULL ? soundFlag.replace('"','\'') : "");
    } // method getSoundFlag


    /**
     * Return the 'soundv' class variable
     * @return soundv (float)
     */
    public float getSoundv() {
        return soundv;
    } // method getSoundv

    /**
     * Return the 'soundv' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSoundv methods
     * @return soundv (String)
     */
    public String getSoundv(String s) {
        return ((soundv != FLOATNULL) ? new Float(soundv).toString() : "");
    } // method getSoundv


    /**
     * Return the 'turbidity' class variable
     * @return turbidity (float)
     */
    public float getTurbidity() {
        return turbidity;
    } // method getTurbidity

    /**
     * Return the 'turbidity' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTurbidity methods
     * @return turbidity (String)
     */
    public String getTurbidity(String s) {
        return ((turbidity != FLOATNULL) ? new Float(turbidity).toString() : "");
    } // method getTurbidity


    /**
     * Return the 'pressure' class variable
     * @return pressure (float)
     */
    public float getPressure() {
        return pressure;
    } // method getPressure

    /**
     * Return the 'pressure' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPressure methods
     * @return pressure (String)
     */
    public String getPressure(String s) {
        return ((pressure != FLOATNULL) ? new Float(pressure).toString() : "");
    } // method getPressure


    /**
     * Return the 'fluorescence' class variable
     * @return fluorescence (float)
     */
    public float getFluorescence() {
        return fluorescence;
    } // method getFluorescence

    /**
     * Return the 'fluorescence' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFluorescence methods
     * @return fluorescence (String)
     */
    public String getFluorescence(String s) {
        return ((fluorescence != FLOATNULL) ? new Float(fluorescence).toString() : "");
    } // method getFluorescence


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
            (spldep == FLOATNULL) &&
            (filtered == CHARNULL) &&
            (disoxygen == FLOATNULL) &&
            (salinity == FLOATNULL) &&
            (temperature == FLOATNULL) &&
            (soundFlag == CHARNULL) &&
            (soundv == FLOATNULL) &&
            (turbidity == FLOATNULL) &&
            (pressure == FLOATNULL) &&
            (fluorescence == FLOATNULL)) {
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
            spldepError +
            filteredError +
            disoxygenError +
            salinityError +
            temperatureError +
            soundFlagError +
            soundvError +
            turbidityError +
            pressureError +
            fluorescenceError;
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


    /**
     * Gets the errorcode for the filtered instance variable
     * @return errorcode (int)
     */
    public int getFilteredError() {
        return filteredError;
    } // method getFilteredError

    /**
     * Gets the errorMessage for the filtered instance variable
     * @return errorMessage (String)
     */
    public String getFilteredErrorMessage() {
        return filteredErrorMessage;
    } // method getFilteredErrorMessage


    /**
     * Gets the errorcode for the disoxygen instance variable
     * @return errorcode (int)
     */
    public int getDisoxygenError() {
        return disoxygenError;
    } // method getDisoxygenError

    /**
     * Gets the errorMessage for the disoxygen instance variable
     * @return errorMessage (String)
     */
    public String getDisoxygenErrorMessage() {
        return disoxygenErrorMessage;
    } // method getDisoxygenErrorMessage


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
     * Gets the errorcode for the temperature instance variable
     * @return errorcode (int)
     */
    public int getTemperatureError() {
        return temperatureError;
    } // method getTemperatureError

    /**
     * Gets the errorMessage for the temperature instance variable
     * @return errorMessage (String)
     */
    public String getTemperatureErrorMessage() {
        return temperatureErrorMessage;
    } // method getTemperatureErrorMessage


    /**
     * Gets the errorcode for the soundFlag instance variable
     * @return errorcode (int)
     */
    public int getSoundFlagError() {
        return soundFlagError;
    } // method getSoundFlagError

    /**
     * Gets the errorMessage for the soundFlag instance variable
     * @return errorMessage (String)
     */
    public String getSoundFlagErrorMessage() {
        return soundFlagErrorMessage;
    } // method getSoundFlagErrorMessage


    /**
     * Gets the errorcode for the soundv instance variable
     * @return errorcode (int)
     */
    public int getSoundvError() {
        return soundvError;
    } // method getSoundvError

    /**
     * Gets the errorMessage for the soundv instance variable
     * @return errorMessage (String)
     */
    public String getSoundvErrorMessage() {
        return soundvErrorMessage;
    } // method getSoundvErrorMessage


    /**
     * Gets the errorcode for the turbidity instance variable
     * @return errorcode (int)
     */
    public int getTurbidityError() {
        return turbidityError;
    } // method getTurbidityError

    /**
     * Gets the errorMessage for the turbidity instance variable
     * @return errorMessage (String)
     */
    public String getTurbidityErrorMessage() {
        return turbidityErrorMessage;
    } // method getTurbidityErrorMessage


    /**
     * Gets the errorcode for the pressure instance variable
     * @return errorcode (int)
     */
    public int getPressureError() {
        return pressureError;
    } // method getPressureError

    /**
     * Gets the errorMessage for the pressure instance variable
     * @return errorMessage (String)
     */
    public String getPressureErrorMessage() {
        return pressureErrorMessage;
    } // method getPressureErrorMessage


    /**
     * Gets the errorcode for the fluorescence instance variable
     * @return errorcode (int)
     */
    public int getFluorescenceError() {
        return fluorescenceError;
    } // method getFluorescenceError

    /**
     * Gets the errorMessage for the fluorescence instance variable
     * @return errorMessage (String)
     */
    public String getFluorescenceErrorMessage() {
        return fluorescenceErrorMessage;
    } // method getFluorescenceErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnWatphy. e.g.<pre>
     * MrnWatphy watphy = new MrnWatphy(<val1>);
     * MrnWatphy watphyArray[] = watphy.get();</pre>
     * will get the MrnWatphy record where code = <val1>.
     * @return Array of MrnWatphy (MrnWatphy[])
     */
    public MrnWatphy[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnWatphy watphyArray[] = 
     *     new MrnWatphy().get(MrnWatphy.CODE+"=<val1>");</pre>
     * will get the MrnWatphy record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnWatphy (MrnWatphy[])
     */
    public MrnWatphy[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnWatphy watphyArray[] = 
     *     new MrnWatphy().get("1=1",watphy.CODE);</pre>
     * will get the all the MrnWatphy records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatphy (MrnWatphy[])
     */
    public MrnWatphy[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnWatphy.CODE,MrnWatphy.STATION_ID;
     * String where = MrnWatphy.CODE + "=<val1";
     * String order = MrnWatphy.CODE;
     * MrnWatphy watphyArray[] = 
     *     new MrnWatphy().get(columns, where, order);</pre>
     * will get the code and stationId colums of all MrnWatphy records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatphy (MrnWatphy[])
     */
    public MrnWatphy[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnWatphy.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnWatphy[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol         = db.getColNumber(CODE);
        int stationIdCol    = db.getColNumber(STATION_ID);
        int deviceCodeCol   = db.getColNumber(DEVICE_CODE);
        int methodCodeCol   = db.getColNumber(METHOD_CODE);
        int standardCodeCol = db.getColNumber(STANDARD_CODE);
        int subdesCol       = db.getColNumber(SUBDES);
        int spldattimCol    = db.getColNumber(SPLDATTIM);
        int spldepCol       = db.getColNumber(SPLDEP);
        int filteredCol     = db.getColNumber(FILTERED);
        int disoxygenCol    = db.getColNumber(DISOXYGEN);
        int salinityCol     = db.getColNumber(SALINITY);
        int temperatureCol  = db.getColNumber(TEMPERATURE);
        int soundFlagCol    = db.getColNumber(SOUND_FLAG);
        int soundvCol       = db.getColNumber(SOUNDV);
        int turbidityCol    = db.getColNumber(TURBIDITY);
        int pressureCol     = db.getColNumber(PRESSURE);
        int fluorescenceCol = db.getColNumber(FLUORESCENCE);
        MrnWatphy[] cArray = new MrnWatphy[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnWatphy();
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
            if (filteredCol != -1)
                cArray[i].setFiltered    ((String) row.elementAt(filteredCol));
            if (disoxygenCol != -1)
                cArray[i].setDisoxygen   ((String) row.elementAt(disoxygenCol));
            if (salinityCol != -1)
                cArray[i].setSalinity    ((String) row.elementAt(salinityCol));
            if (temperatureCol != -1)
                cArray[i].setTemperature ((String) row.elementAt(temperatureCol));
            if (soundFlagCol != -1)
                cArray[i].setSoundFlag   ((String) row.elementAt(soundFlagCol));
            if (soundvCol != -1)
                cArray[i].setSoundv      ((String) row.elementAt(soundvCol));
            if (turbidityCol != -1)
                cArray[i].setTurbidity   ((String) row.elementAt(turbidityCol));
            if (pressureCol != -1)
                cArray[i].setPressure    ((String) row.elementAt(pressureCol));
            if (fluorescenceCol != -1)
                cArray[i].setFluorescence((String) row.elementAt(fluorescenceCol));
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
     *     new MrnWatphy(
     *         INTNULL,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>,
     *         <val9>,
     *         <val10>,
     *         <val11>,
     *         <val12>,
     *         <val13>,
     *         <val14>,
     *         <val15>,
     *         <val16>,
     *         <val17>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     stationId    = <val2>,
     *     deviceCode   = <val3>,
     *     methodCode   = <val4>,
     *     standardCode = <val5>,
     *     subdes       = <val6>,
     *     spldattim    = <val7>,
     *     spldep       = <val8>,
     *     filtered     = <val9>,
     *     disoxygen    = <val10>,
     *     salinity     = <val11>,
     *     temperature  = <val12>,
     *     soundFlag    = <val13>,
     *     soundv       = <val14>,
     *     turbidity    = <val15>,
     *     pressure     = <val16>,
     *     fluorescence = <val17>.
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
     * Boolean success = new MrnWatphy(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
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
     * MrnWatphy watphy = new MrnWatphy();
     * success = watphy.del(MrnWatphy.CODE+"=<val1>");</pre>
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
     * update are taken from the MrnWatphy argument, .e.g.<pre>
     * Boolean success
     * MrnWatphy updMrnWatphy = new MrnWatphy();
     * updMrnWatphy.setStationId(<val2>);
     * MrnWatphy whereMrnWatphy = new MrnWatphy(<val1>);
     * success = whereMrnWatphy.upd(updMrnWatphy);</pre>
     * will update StationId to <val2> for all records where 
     * code = <val1>.
     * @param watphy  A MrnWatphy variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatphy watphy) {
        return db.update (TABLE, createColVals(watphy), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatphy updMrnWatphy = new MrnWatphy();
     * updMrnWatphy.setStationId(<val2>);
     * MrnWatphy whereMrnWatphy = new MrnWatphy();
     * success = whereMrnWatphy.upd(
     *     updMrnWatphy, MrnWatphy.CODE+"=<val1>");</pre>
     * will update StationId to <val2> for all records where 
     * code = <val1>.
     * @param  updMrnWatphy  A MrnWatphy variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatphy watphy, String where) {
        return db.update (TABLE, createColVals(watphy), where);
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
        if (getFiltered() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FILTERED + "='" + getFiltered() + "'";
        } // if getFiltered
        if (getDisoxygen() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DISOXYGEN + "=" + getDisoxygen("");
        } // if getDisoxygen
        if (getSalinity() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SALINITY + "=" + getSalinity("");
        } // if getSalinity
        if (getTemperature() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TEMPERATURE + "=" + getTemperature("");
        } // if getTemperature
        if (getSoundFlag() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SOUND_FLAG + "='" + getSoundFlag() + "'";
        } // if getSoundFlag
        if (getSoundv() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SOUNDV + "=" + getSoundv("");
        } // if getSoundv
        if (getTurbidity() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TURBIDITY + "=" + getTurbidity("");
        } // if getTurbidity
        if (getPressure() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PRESSURE + "=" + getPressure("");
        } // if getPressure
        if (getFluorescence() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FLUORESCENCE + "=" + getFluorescence("");
        } // if getFluorescence
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnWatphy aVar) {
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
        if (aVar.getFiltered() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FILTERED +"=";
            colVals += (aVar.getFiltered().equals(CHARNULL2) ?
                "null" : "'" + aVar.getFiltered() + "'");
        } // if aVar.getFiltered
        if (aVar.getDisoxygen() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DISOXYGEN +"=";
            colVals += (aVar.getDisoxygen() == FLOATNULL2 ?
                "null" : aVar.getDisoxygen(""));
        } // if aVar.getDisoxygen
        if (aVar.getSalinity() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SALINITY +"=";
            colVals += (aVar.getSalinity() == FLOATNULL2 ?
                "null" : aVar.getSalinity(""));
        } // if aVar.getSalinity
        if (aVar.getTemperature() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TEMPERATURE +"=";
            colVals += (aVar.getTemperature() == FLOATNULL2 ?
                "null" : aVar.getTemperature(""));
        } // if aVar.getTemperature
        if (aVar.getSoundFlag() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SOUND_FLAG +"=";
            colVals += (aVar.getSoundFlag().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSoundFlag() + "'");
        } // if aVar.getSoundFlag
        if (aVar.getSoundv() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SOUNDV +"=";
            colVals += (aVar.getSoundv() == FLOATNULL2 ?
                "null" : aVar.getSoundv(""));
        } // if aVar.getSoundv
        if (aVar.getTurbidity() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TURBIDITY +"=";
            colVals += (aVar.getTurbidity() == FLOATNULL2 ?
                "null" : aVar.getTurbidity(""));
        } // if aVar.getTurbidity
        if (aVar.getPressure() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PRESSURE +"=";
            colVals += (aVar.getPressure() == FLOATNULL2 ?
                "null" : aVar.getPressure(""));
        } // if aVar.getPressure
        if (aVar.getFluorescence() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FLUORESCENCE +"=";
            colVals += (aVar.getFluorescence() == FLOATNULL2 ?
                "null" : aVar.getFluorescence(""));
        } // if aVar.getFluorescence
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
        if (getFiltered() != CHARNULL) {
            columns = columns + "," + FILTERED;
        } // if getFiltered
        if (getDisoxygen() != FLOATNULL) {
            columns = columns + "," + DISOXYGEN;
        } // if getDisoxygen
        if (getSalinity() != FLOATNULL) {
            columns = columns + "," + SALINITY;
        } // if getSalinity
        if (getTemperature() != FLOATNULL) {
            columns = columns + "," + TEMPERATURE;
        } // if getTemperature
        if (getSoundFlag() != CHARNULL) {
            columns = columns + "," + SOUND_FLAG;
        } // if getSoundFlag
        if (getSoundv() != FLOATNULL) {
            columns = columns + "," + SOUNDV;
        } // if getSoundv
        if (getTurbidity() != FLOATNULL) {
            columns = columns + "," + TURBIDITY;
        } // if getTurbidity
        if (getPressure() != FLOATNULL) {
            columns = columns + "," + PRESSURE;
        } // if getPressure
        if (getFluorescence() != FLOATNULL) {
            columns = columns + "," + FLUORESCENCE;
        } // if getFluorescence
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
        if (getFiltered() != CHARNULL) {
            values  = values  + ",'" + getFiltered() + "'";
        } // if getFiltered
        if (getDisoxygen() != FLOATNULL) {
            values  = values  + "," + getDisoxygen("");
        } // if getDisoxygen
        if (getSalinity() != FLOATNULL) {
            values  = values  + "," + getSalinity("");
        } // if getSalinity
        if (getTemperature() != FLOATNULL) {
            values  = values  + "," + getTemperature("");
        } // if getTemperature
        if (getSoundFlag() != CHARNULL) {
            values  = values  + ",'" + getSoundFlag() + "'";
        } // if getSoundFlag
        if (getSoundv() != FLOATNULL) {
            values  = values  + "," + getSoundv("");
        } // if getSoundv
        if (getTurbidity() != FLOATNULL) {
            values  = values  + "," + getTurbidity("");
        } // if getTurbidity
        if (getPressure() != FLOATNULL) {
            values  = values  + "," + getPressure("");
        } // if getPressure
        if (getFluorescence() != FLOATNULL) {
            values  = values  + "," + getFluorescence("");
        } // if getFluorescence
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
            getSpldep("")       + "|" +
            getFiltered("")     + "|" +
            getDisoxygen("")    + "|" +
            getSalinity("")     + "|" +
            getTemperature("")  + "|" +
            getSoundFlag("")    + "|" +
            getSoundv("")       + "|" +
            getTurbidity("")    + "|" +
            getPressure("")     + "|" +
            getFluorescence("") + "|";
    } // method toString

} // class MrnWatphy
