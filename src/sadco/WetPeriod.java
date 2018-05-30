package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WET_PERIOD table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class WetPeriod extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WET_PERIOD";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The instrumentCode field name */
    public static final String INSTRUMENT_CODE = "INSTRUMENT_CODE";
    /** The heightSurface field name */
    public static final String HEIGHT_SURFACE = "HEIGHT_SURFACE";
    /** The heightMsl field name */
    public static final String HEIGHT_MSL = "HEIGHT_MSL";
    /** The speedCorrFactor field name */
    public static final String SPEED_CORR_FACTOR = "SPEED_CORR_FACTOR";
    /** The speedAverMethod field name */
    public static final String SPEED_AVER_METHOD = "SPEED_AVER_METHOD";
    /** The dirAverMethod field name */
    public static final String DIR_AVER_METHOD = "DIR_AVER_METHOD";
    /** The windSamplingInterval field name */
    public static final String WIND_SAMPLING_INTERVAL = "WIND_SAMPLING_INTERVAL";
    /** The startDate field name */
    public static final String START_DATE = "START_DATE";
    /** The endDate field name */
    public static final String END_DATE = "END_DATE";
    /** The sampleInterval field name */
    public static final String SAMPLE_INTERVAL = "SAMPLE_INTERVAL";
    /** The numberRecords field name */
    public static final String NUMBER_RECORDS = "NUMBER_RECORDS";
    /** The numberNullRecords field name */
    public static final String NUMBER_NULL_RECORDS = "NUMBER_NULL_RECORDS";
    /** The loadDate field name */
    public static final String LOAD_DATE = "LOAD_DATE";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    stationId;
    private int       instrumentCode;
    private float     heightSurface;
    private float     heightMsl;
    private float     speedCorrFactor;
    private String    speedAverMethod;
    private String    dirAverMethod;
    private int       windSamplingInterval;
    private Timestamp startDate;
    private Timestamp endDate;
    private int       sampleInterval;
    private int       numberRecords;
    private int       numberNullRecords;
    private Timestamp loadDate;

    /** The error variables  */
    private int codeError                 = ERROR_NORMAL;
    private int stationIdError            = ERROR_NORMAL;
    private int instrumentCodeError       = ERROR_NORMAL;
    private int heightSurfaceError        = ERROR_NORMAL;
    private int heightMslError            = ERROR_NORMAL;
    private int speedCorrFactorError      = ERROR_NORMAL;
    private int speedAverMethodError      = ERROR_NORMAL;
    private int dirAverMethodError        = ERROR_NORMAL;
    private int windSamplingIntervalError = ERROR_NORMAL;
    private int startDateError            = ERROR_NORMAL;
    private int endDateError              = ERROR_NORMAL;
    private int sampleIntervalError       = ERROR_NORMAL;
    private int numberRecordsError        = ERROR_NORMAL;
    private int numberNullRecordsError    = ERROR_NORMAL;
    private int loadDateError             = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage                 = "";
    private String stationIdErrorMessage            = "";
    private String instrumentCodeErrorMessage       = "";
    private String heightSurfaceErrorMessage        = "";
    private String heightMslErrorMessage            = "";
    private String speedCorrFactorErrorMessage      = "";
    private String speedAverMethodErrorMessage      = "";
    private String dirAverMethodErrorMessage        = "";
    private String windSamplingIntervalErrorMessage = "";
    private String startDateErrorMessage            = "";
    private String endDateErrorMessage              = "";
    private String sampleIntervalErrorMessage       = "";
    private String numberRecordsErrorMessage        = "";
    private String numberNullRecordsErrorMessage    = "";
    private String loadDateErrorMessage             = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       INSTRUMENT_CODE_MN = INTMIN;
    public static final int       INSTRUMENT_CODE_MX = INTMAX;
    public static final float     HEIGHT_SURFACE_MN = FLOATMIN;
    public static final float     HEIGHT_SURFACE_MX = FLOATMAX;
    public static final float     HEIGHT_MSL_MN = FLOATMIN;
    public static final float     HEIGHT_MSL_MX = FLOATMAX;
    public static final float     SPEED_CORR_FACTOR_MN = FLOATMIN;
    public static final float     SPEED_CORR_FACTOR_MX = FLOATMAX;
    public static final int       WIND_SAMPLING_INTERVAL_MN = INTMIN;
    public static final int       WIND_SAMPLING_INTERVAL_MX = INTMAX;
    public static final Timestamp START_DATE_MN = DATEMIN;
    public static final Timestamp START_DATE_MX = DATEMAX;
    public static final Timestamp END_DATE_MN = DATEMIN;
    public static final Timestamp END_DATE_MX = DATEMAX;
    public static final int       SAMPLE_INTERVAL_MN = INTMIN;
    public static final int       SAMPLE_INTERVAL_MX = INTMAX;
    public static final int       NUMBER_RECORDS_MN = INTMIN;
    public static final int       NUMBER_RECORDS_MX = INTMAX;
    public static final int       NUMBER_NULL_RECORDS_MN = INTMIN;
    public static final int       NUMBER_NULL_RECORDS_MX = INTMAX;
    public static final Timestamp LOAD_DATE_MN = DATEMIN;
    public static final Timestamp LOAD_DATE_MX = DATEMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception instrumentCodeOutOfBoundsException =
        new Exception ("'instrumentCode' out of bounds: " +
            INSTRUMENT_CODE_MN + " - " + INSTRUMENT_CODE_MX);
    Exception heightSurfaceOutOfBoundsException =
        new Exception ("'heightSurface' out of bounds: " +
            HEIGHT_SURFACE_MN + " - " + HEIGHT_SURFACE_MX);
    Exception heightMslOutOfBoundsException =
        new Exception ("'heightMsl' out of bounds: " +
            HEIGHT_MSL_MN + " - " + HEIGHT_MSL_MX);
    Exception speedCorrFactorOutOfBoundsException =
        new Exception ("'speedCorrFactor' out of bounds: " +
            SPEED_CORR_FACTOR_MN + " - " + SPEED_CORR_FACTOR_MX);
    Exception windSamplingIntervalOutOfBoundsException =
        new Exception ("'windSamplingInterval' out of bounds: " +
            WIND_SAMPLING_INTERVAL_MN + " - " + WIND_SAMPLING_INTERVAL_MX);
    Exception startDateException =
        new Exception ("'startDate' is null");
    Exception startDateOutOfBoundsException =
        new Exception ("'startDate' out of bounds: " +
            START_DATE_MN + " - " + START_DATE_MX);
    Exception endDateException =
        new Exception ("'endDate' is null");
    Exception endDateOutOfBoundsException =
        new Exception ("'endDate' out of bounds: " +
            END_DATE_MN + " - " + END_DATE_MX);
    Exception sampleIntervalOutOfBoundsException =
        new Exception ("'sampleInterval' out of bounds: " +
            SAMPLE_INTERVAL_MN + " - " + SAMPLE_INTERVAL_MX);
    Exception numberRecordsOutOfBoundsException =
        new Exception ("'numberRecords' out of bounds: " +
            NUMBER_RECORDS_MN + " - " + NUMBER_RECORDS_MX);
    Exception numberNullRecordsOutOfBoundsException =
        new Exception ("'numberNullRecords' out of bounds: " +
            NUMBER_NULL_RECORDS_MN + " - " + NUMBER_NULL_RECORDS_MX);
    Exception loadDateException =
        new Exception ("'loadDate' is null");
    Exception loadDateOutOfBoundsException =
        new Exception ("'loadDate' out of bounds: " +
            LOAD_DATE_MN + " - " + LOAD_DATE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public WetPeriod() {
        clearVars();
        if (dbg) System.out.println ("<br>in WetPeriod constructor 1"); // debug
    } // WetPeriod constructor

    /**
     * Instantiate a WetPeriod object and initialize the instance variables.
     * @param code  The code (int)
     */
    public WetPeriod(
            int code) {
        this();
        setCode                 (code                );
        if (dbg) System.out.println ("<br>in WetPeriod constructor 2"); // debug
    } // WetPeriod constructor

    /**
     * Instantiate a WetPeriod object and initialize the instance variables.
     * @param code                  The code                 (int)
     * @param stationId             The stationId            (String)
     * @param instrumentCode        The instrumentCode       (int)
     * @param heightSurface         The heightSurface        (float)
     * @param heightMsl             The heightMsl            (float)
     * @param speedCorrFactor       The speedCorrFactor      (float)
     * @param speedAverMethod       The speedAverMethod      (String)
     * @param dirAverMethod         The dirAverMethod        (String)
     * @param windSamplingInterval  The windSamplingInterval (int)
     * @param startDate             The startDate            (java.util.Date)
     * @param endDate               The endDate              (java.util.Date)
     * @param sampleInterval        The sampleInterval       (int)
     * @param numberRecords         The numberRecords        (int)
     * @param numberNullRecords     The numberNullRecords    (int)
     * @param loadDate              The loadDate             (java.util.Date)
     */
    public WetPeriod(
            int            code,
            String         stationId,
            int            instrumentCode,
            float          heightSurface,
            float          heightMsl,
            float          speedCorrFactor,
            String         speedAverMethod,
            String         dirAverMethod,
            int            windSamplingInterval,
            java.util.Date startDate,
            java.util.Date endDate,
            int            sampleInterval,
            int            numberRecords,
            int            numberNullRecords,
            java.util.Date loadDate) {
        this();
        setCode                 (code                );
        setStationId            (stationId           );
        setInstrumentCode       (instrumentCode      );
        setHeightSurface        (heightSurface       );
        setHeightMsl            (heightMsl           );
        setSpeedCorrFactor      (speedCorrFactor     );
        setSpeedAverMethod      (speedAverMethod     );
        setDirAverMethod        (dirAverMethod       );
        setWindSamplingInterval (windSamplingInterval);
        setStartDate            (startDate           );
        setEndDate              (endDate             );
        setSampleInterval       (sampleInterval      );
        setNumberRecords        (numberRecords       );
        setNumberNullRecords    (numberNullRecords   );
        setLoadDate             (loadDate            );
        if (dbg) System.out.println ("<br>in WetPeriod constructor 3"); // debug
    } // WetPeriod constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode                 (INTNULL  );
        setStationId            (CHARNULL );
        setInstrumentCode       (INTNULL  );
        setHeightSurface        (FLOATNULL);
        setHeightMsl            (FLOATNULL);
        setSpeedCorrFactor      (FLOATNULL);
        setSpeedAverMethod      (CHARNULL );
        setDirAverMethod        (CHARNULL );
        setWindSamplingInterval (INTNULL  );
        setStartDate            (DATENULL );
        setEndDate              (DATENULL );
        setSampleInterval       (INTNULL  );
        setNumberRecords        (INTNULL  );
        setNumberNullRecords    (INTNULL  );
        setLoadDate             (DATENULL );
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
     * Set the 'instrumentCode' class variable
     * @param  instrumentCode (int)
     */
    public int setInstrumentCode(int instrumentCode) {
        try {
            if ( ((instrumentCode == INTNULL) ||
                  (instrumentCode == INTNULL2)) ||
                !((instrumentCode < INSTRUMENT_CODE_MN) ||
                  (instrumentCode > INSTRUMENT_CODE_MX)) ) {
                this.instrumentCode = instrumentCode;
                instrumentCodeError = ERROR_NORMAL;
            } else {
                throw instrumentCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setInstrumentCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return instrumentCodeError;
    } // method setInstrumentCode

    /**
     * Set the 'instrumentCode' class variable
     * @param  instrumentCode (Integer)
     */
    public int setInstrumentCode(Integer instrumentCode) {
        try {
            setInstrumentCode(instrumentCode.intValue());
        } catch (Exception e) {
            setInstrumentCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentCodeError;
    } // method setInstrumentCode

    /**
     * Set the 'instrumentCode' class variable
     * @param  instrumentCode (Float)
     */
    public int setInstrumentCode(Float instrumentCode) {
        try {
            if (instrumentCode.floatValue() == FLOATNULL) {
                setInstrumentCode(INTNULL);
            } else {
                setInstrumentCode(instrumentCode.intValue());
            } // if (instrumentCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setInstrumentCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentCodeError;
    } // method setInstrumentCode

    /**
     * Set the 'instrumentCode' class variable
     * @param  instrumentCode (String)
     */
    public int setInstrumentCode(String instrumentCode) {
        try {
            setInstrumentCode(new Integer(instrumentCode).intValue());
        } catch (Exception e) {
            setInstrumentCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentCodeError;
    } // method setInstrumentCode

    /**
     * Called when an exception has occured
     * @param  instrumentCode (String)
     */
    private void setInstrumentCodeError (int instrumentCode, Exception e, int error) {
        this.instrumentCode = instrumentCode;
        instrumentCodeErrorMessage = e.toString();
        instrumentCodeError = error;
    } // method setInstrumentCodeError


    /**
     * Set the 'heightSurface' class variable
     * @param  heightSurface (float)
     */
    public int setHeightSurface(float heightSurface) {
        try {
            if ( ((heightSurface == FLOATNULL) ||
                  (heightSurface == FLOATNULL2)) ||
                !((heightSurface < HEIGHT_SURFACE_MN) ||
                  (heightSurface > HEIGHT_SURFACE_MX)) ) {
                this.heightSurface = heightSurface;
                heightSurfaceError = ERROR_NORMAL;
            } else {
                throw heightSurfaceOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setHeightSurfaceError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return heightSurfaceError;
    } // method setHeightSurface

    /**
     * Set the 'heightSurface' class variable
     * @param  heightSurface (Integer)
     */
    public int setHeightSurface(Integer heightSurface) {
        try {
            setHeightSurface(heightSurface.floatValue());
        } catch (Exception e) {
            setHeightSurfaceError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return heightSurfaceError;
    } // method setHeightSurface

    /**
     * Set the 'heightSurface' class variable
     * @param  heightSurface (Float)
     */
    public int setHeightSurface(Float heightSurface) {
        try {
            setHeightSurface(heightSurface.floatValue());
        } catch (Exception e) {
            setHeightSurfaceError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return heightSurfaceError;
    } // method setHeightSurface

    /**
     * Set the 'heightSurface' class variable
     * @param  heightSurface (String)
     */
    public int setHeightSurface(String heightSurface) {
        try {
            setHeightSurface(new Float(heightSurface).floatValue());
        } catch (Exception e) {
            setHeightSurfaceError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return heightSurfaceError;
    } // method setHeightSurface

    /**
     * Called when an exception has occured
     * @param  heightSurface (String)
     */
    private void setHeightSurfaceError (float heightSurface, Exception e, int error) {
        this.heightSurface = heightSurface;
        heightSurfaceErrorMessage = e.toString();
        heightSurfaceError = error;
    } // method setHeightSurfaceError


    /**
     * Set the 'heightMsl' class variable
     * @param  heightMsl (float)
     */
    public int setHeightMsl(float heightMsl) {
        try {
            if ( ((heightMsl == FLOATNULL) ||
                  (heightMsl == FLOATNULL2)) ||
                !((heightMsl < HEIGHT_MSL_MN) ||
                  (heightMsl > HEIGHT_MSL_MX)) ) {
                this.heightMsl = heightMsl;
                heightMslError = ERROR_NORMAL;
            } else {
                throw heightMslOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setHeightMslError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return heightMslError;
    } // method setHeightMsl

    /**
     * Set the 'heightMsl' class variable
     * @param  heightMsl (Integer)
     */
    public int setHeightMsl(Integer heightMsl) {
        try {
            setHeightMsl(heightMsl.floatValue());
        } catch (Exception e) {
            setHeightMslError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return heightMslError;
    } // method setHeightMsl

    /**
     * Set the 'heightMsl' class variable
     * @param  heightMsl (Float)
     */
    public int setHeightMsl(Float heightMsl) {
        try {
            setHeightMsl(heightMsl.floatValue());
        } catch (Exception e) {
            setHeightMslError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return heightMslError;
    } // method setHeightMsl

    /**
     * Set the 'heightMsl' class variable
     * @param  heightMsl (String)
     */
    public int setHeightMsl(String heightMsl) {
        try {
            setHeightMsl(new Float(heightMsl).floatValue());
        } catch (Exception e) {
            setHeightMslError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return heightMslError;
    } // method setHeightMsl

    /**
     * Called when an exception has occured
     * @param  heightMsl (String)
     */
    private void setHeightMslError (float heightMsl, Exception e, int error) {
        this.heightMsl = heightMsl;
        heightMslErrorMessage = e.toString();
        heightMslError = error;
    } // method setHeightMslError


    /**
     * Set the 'speedCorrFactor' class variable
     * @param  speedCorrFactor (float)
     */
    public int setSpeedCorrFactor(float speedCorrFactor) {
        try {
            if ( ((speedCorrFactor == FLOATNULL) ||
                  (speedCorrFactor == FLOATNULL2)) ||
                !((speedCorrFactor < SPEED_CORR_FACTOR_MN) ||
                  (speedCorrFactor > SPEED_CORR_FACTOR_MX)) ) {
                this.speedCorrFactor = speedCorrFactor;
                speedCorrFactorError = ERROR_NORMAL;
            } else {
                throw speedCorrFactorOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpeedCorrFactorError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return speedCorrFactorError;
    } // method setSpeedCorrFactor

    /**
     * Set the 'speedCorrFactor' class variable
     * @param  speedCorrFactor (Integer)
     */
    public int setSpeedCorrFactor(Integer speedCorrFactor) {
        try {
            setSpeedCorrFactor(speedCorrFactor.floatValue());
        } catch (Exception e) {
            setSpeedCorrFactorError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return speedCorrFactorError;
    } // method setSpeedCorrFactor

    /**
     * Set the 'speedCorrFactor' class variable
     * @param  speedCorrFactor (Float)
     */
    public int setSpeedCorrFactor(Float speedCorrFactor) {
        try {
            setSpeedCorrFactor(speedCorrFactor.floatValue());
        } catch (Exception e) {
            setSpeedCorrFactorError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return speedCorrFactorError;
    } // method setSpeedCorrFactor

    /**
     * Set the 'speedCorrFactor' class variable
     * @param  speedCorrFactor (String)
     */
    public int setSpeedCorrFactor(String speedCorrFactor) {
        try {
            setSpeedCorrFactor(new Float(speedCorrFactor).floatValue());
        } catch (Exception e) {
            setSpeedCorrFactorError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return speedCorrFactorError;
    } // method setSpeedCorrFactor

    /**
     * Called when an exception has occured
     * @param  speedCorrFactor (String)
     */
    private void setSpeedCorrFactorError (float speedCorrFactor, Exception e, int error) {
        this.speedCorrFactor = speedCorrFactor;
        speedCorrFactorErrorMessage = e.toString();
        speedCorrFactorError = error;
    } // method setSpeedCorrFactorError


    /**
     * Set the 'speedAverMethod' class variable
     * @param  speedAverMethod (String)
     */
    public int setSpeedAverMethod(String speedAverMethod) {
        try {
            this.speedAverMethod = speedAverMethod;
            if (this.speedAverMethod != CHARNULL) {
                this.speedAverMethod = stripCRLF(this.speedAverMethod.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>speedAverMethod = " + this.speedAverMethod);
        } catch (Exception e) {
            setSpeedAverMethodError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return speedAverMethodError;
    } // method setSpeedAverMethod

    /**
     * Called when an exception has occured
     * @param  speedAverMethod (String)
     */
    private void setSpeedAverMethodError (String speedAverMethod, Exception e, int error) {
        this.speedAverMethod = speedAverMethod;
        speedAverMethodErrorMessage = e.toString();
        speedAverMethodError = error;
    } // method setSpeedAverMethodError


    /**
     * Set the 'dirAverMethod' class variable
     * @param  dirAverMethod (String)
     */
    public int setDirAverMethod(String dirAverMethod) {
        try {
            this.dirAverMethod = dirAverMethod;
            if (this.dirAverMethod != CHARNULL) {
                this.dirAverMethod = stripCRLF(this.dirAverMethod.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>dirAverMethod = " + this.dirAverMethod);
        } catch (Exception e) {
            setDirAverMethodError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return dirAverMethodError;
    } // method setDirAverMethod

    /**
     * Called when an exception has occured
     * @param  dirAverMethod (String)
     */
    private void setDirAverMethodError (String dirAverMethod, Exception e, int error) {
        this.dirAverMethod = dirAverMethod;
        dirAverMethodErrorMessage = e.toString();
        dirAverMethodError = error;
    } // method setDirAverMethodError


    /**
     * Set the 'windSamplingInterval' class variable
     * @param  windSamplingInterval (int)
     */
    public int setWindSamplingInterval(int windSamplingInterval) {
        try {
            if ( ((windSamplingInterval == INTNULL) ||
                  (windSamplingInterval == INTNULL2)) ||
                !((windSamplingInterval < WIND_SAMPLING_INTERVAL_MN) ||
                  (windSamplingInterval > WIND_SAMPLING_INTERVAL_MX)) ) {
                this.windSamplingInterval = windSamplingInterval;
                windSamplingIntervalError = ERROR_NORMAL;
            } else {
                throw windSamplingIntervalOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindSamplingIntervalError(INTNULL, e, ERROR_LOCAL);
        } // try
        return windSamplingIntervalError;
    } // method setWindSamplingInterval

    /**
     * Set the 'windSamplingInterval' class variable
     * @param  windSamplingInterval (Integer)
     */
    public int setWindSamplingInterval(Integer windSamplingInterval) {
        try {
            setWindSamplingInterval(windSamplingInterval.intValue());
        } catch (Exception e) {
            setWindSamplingIntervalError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windSamplingIntervalError;
    } // method setWindSamplingInterval

    /**
     * Set the 'windSamplingInterval' class variable
     * @param  windSamplingInterval (Float)
     */
    public int setWindSamplingInterval(Float windSamplingInterval) {
        try {
            if (windSamplingInterval.floatValue() == FLOATNULL) {
                setWindSamplingInterval(INTNULL);
            } else {
                setWindSamplingInterval(windSamplingInterval.intValue());
            } // if (windSamplingInterval.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWindSamplingIntervalError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windSamplingIntervalError;
    } // method setWindSamplingInterval

    /**
     * Set the 'windSamplingInterval' class variable
     * @param  windSamplingInterval (String)
     */
    public int setWindSamplingInterval(String windSamplingInterval) {
        try {
            setWindSamplingInterval(new Integer(windSamplingInterval).intValue());
        } catch (Exception e) {
            setWindSamplingIntervalError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windSamplingIntervalError;
    } // method setWindSamplingInterval

    /**
     * Called when an exception has occured
     * @param  windSamplingInterval (String)
     */
    private void setWindSamplingIntervalError (int windSamplingInterval, Exception e, int error) {
        this.windSamplingInterval = windSamplingInterval;
        windSamplingIntervalErrorMessage = e.toString();
        windSamplingIntervalError = error;
    } // method setWindSamplingIntervalError


    /**
     * Set the 'startDate' class variable
     * @param  startDate (Timestamp)
     */
    public int setStartDate(Timestamp startDate) {
        try {
            if (startDate == null) { this.startDate = DATENULL; }
            if ( (startDate.equals(DATENULL) ||
                  startDate.equals(DATENULL2)) ||
                !(startDate.before(START_DATE_MN) ||
                  startDate.after(START_DATE_MX)) ) {
                this.startDate = startDate;
                startDateError = ERROR_NORMAL;
            } else {
                throw startDateOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStartDateError(DATENULL, e, ERROR_LOCAL);
        } // try
        return startDateError;
    } // method setStartDate

    /**
     * Set the 'startDate' class variable
     * @param  startDate (java.util.Date)
     */
    public int setStartDate(java.util.Date startDate) {
        try {
            setStartDate(new Timestamp(startDate.getTime()));
        } catch (Exception e) {
            setStartDateError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return startDateError;
    } // method setStartDate

    /**
     * Set the 'startDate' class variable
     * @param  startDate (String)
     */
    public int setStartDate(String startDate) {
        try {
            int len = startDate.length();
            switch (len) {
            // date &/or times
                case 4: startDate += "-01";
                case 7: startDate += "-01";
                case 10: startDate += " 00";
                case 13: startDate += ":00";
                case 16: startDate += ":00"; break;
                // times only
                case 5: startDate = "1800-01-01 " + startDate + ":00"; break;
                case 8: startDate = "1800-01-01 " + startDate; break;
            } // switch
            if (dbg) System.out.println ("startDate = " + startDate);
            setStartDate(Timestamp.valueOf(startDate));
        } catch (Exception e) {
            setStartDateError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return startDateError;
    } // method setStartDate

    /**
     * Called when an exception has occured
     * @param  startDate (String)
     */
    private void setStartDateError (Timestamp startDate, Exception e, int error) {
        this.startDate = startDate;
        startDateErrorMessage = e.toString();
        startDateError = error;
    } // method setStartDateError


    /**
     * Set the 'endDate' class variable
     * @param  endDate (Timestamp)
     */
    public int setEndDate(Timestamp endDate) {
        try {
            if (endDate == null) { this.endDate = DATENULL; }
            if ( (endDate.equals(DATENULL) ||
                  endDate.equals(DATENULL2)) ||
                !(endDate.before(END_DATE_MN) ||
                  endDate.after(END_DATE_MX)) ) {
                this.endDate = endDate;
                endDateError = ERROR_NORMAL;
            } else {
                throw endDateOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setEndDateError(DATENULL, e, ERROR_LOCAL);
        } // try
        return endDateError;
    } // method setEndDate

    /**
     * Set the 'endDate' class variable
     * @param  endDate (java.util.Date)
     */
    public int setEndDate(java.util.Date endDate) {
        try {
            setEndDate(new Timestamp(endDate.getTime()));
        } catch (Exception e) {
            setEndDateError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return endDateError;
    } // method setEndDate

    /**
     * Set the 'endDate' class variable
     * @param  endDate (String)
     */
    public int setEndDate(String endDate) {
        try {
            int len = endDate.length();
            switch (len) {
            // date &/or times
                case 4: endDate += "-01";
                case 7: endDate += "-01";
                case 10: endDate += " 00";
                case 13: endDate += ":00";
                case 16: endDate += ":00"; break;
                // times only
                case 5: endDate = "1800-01-01 " + endDate + ":00"; break;
                case 8: endDate = "1800-01-01 " + endDate; break;
            } // switch
            if (dbg) System.out.println ("endDate = " + endDate);
            setEndDate(Timestamp.valueOf(endDate));
        } catch (Exception e) {
            setEndDateError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return endDateError;
    } // method setEndDate

    /**
     * Called when an exception has occured
     * @param  endDate (String)
     */
    private void setEndDateError (Timestamp endDate, Exception e, int error) {
        this.endDate = endDate;
        endDateErrorMessage = e.toString();
        endDateError = error;
    } // method setEndDateError


    /**
     * Set the 'sampleInterval' class variable
     * @param  sampleInterval (int)
     */
    public int setSampleInterval(int sampleInterval) {
        try {
            if ( ((sampleInterval == INTNULL) ||
                  (sampleInterval == INTNULL2)) ||
                !((sampleInterval < SAMPLE_INTERVAL_MN) ||
                  (sampleInterval > SAMPLE_INTERVAL_MX)) ) {
                this.sampleInterval = sampleInterval;
                sampleIntervalError = ERROR_NORMAL;
            } else {
                throw sampleIntervalOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSampleIntervalError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sampleIntervalError;
    } // method setSampleInterval

    /**
     * Set the 'sampleInterval' class variable
     * @param  sampleInterval (Integer)
     */
    public int setSampleInterval(Integer sampleInterval) {
        try {
            setSampleInterval(sampleInterval.intValue());
        } catch (Exception e) {
            setSampleIntervalError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sampleIntervalError;
    } // method setSampleInterval

    /**
     * Set the 'sampleInterval' class variable
     * @param  sampleInterval (Float)
     */
    public int setSampleInterval(Float sampleInterval) {
        try {
            if (sampleInterval.floatValue() == FLOATNULL) {
                setSampleInterval(INTNULL);
            } else {
                setSampleInterval(sampleInterval.intValue());
            } // if (sampleInterval.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSampleIntervalError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sampleIntervalError;
    } // method setSampleInterval

    /**
     * Set the 'sampleInterval' class variable
     * @param  sampleInterval (String)
     */
    public int setSampleInterval(String sampleInterval) {
        try {
            setSampleInterval(new Integer(sampleInterval).intValue());
        } catch (Exception e) {
            setSampleIntervalError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sampleIntervalError;
    } // method setSampleInterval

    /**
     * Called when an exception has occured
     * @param  sampleInterval (String)
     */
    private void setSampleIntervalError (int sampleInterval, Exception e, int error) {
        this.sampleInterval = sampleInterval;
        sampleIntervalErrorMessage = e.toString();
        sampleIntervalError = error;
    } // method setSampleIntervalError


    /**
     * Set the 'numberRecords' class variable
     * @param  numberRecords (int)
     */
    public int setNumberRecords(int numberRecords) {
        try {
            if ( ((numberRecords == INTNULL) ||
                  (numberRecords == INTNULL2)) ||
                !((numberRecords < NUMBER_RECORDS_MN) ||
                  (numberRecords > NUMBER_RECORDS_MX)) ) {
                this.numberRecords = numberRecords;
                numberRecordsError = ERROR_NORMAL;
            } else {
                throw numberRecordsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNumberRecordsError(INTNULL, e, ERROR_LOCAL);
        } // try
        return numberRecordsError;
    } // method setNumberRecords

    /**
     * Set the 'numberRecords' class variable
     * @param  numberRecords (Integer)
     */
    public int setNumberRecords(Integer numberRecords) {
        try {
            setNumberRecords(numberRecords.intValue());
        } catch (Exception e) {
            setNumberRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberRecordsError;
    } // method setNumberRecords

    /**
     * Set the 'numberRecords' class variable
     * @param  numberRecords (Float)
     */
    public int setNumberRecords(Float numberRecords) {
        try {
            if (numberRecords.floatValue() == FLOATNULL) {
                setNumberRecords(INTNULL);
            } else {
                setNumberRecords(numberRecords.intValue());
            } // if (numberRecords.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setNumberRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberRecordsError;
    } // method setNumberRecords

    /**
     * Set the 'numberRecords' class variable
     * @param  numberRecords (String)
     */
    public int setNumberRecords(String numberRecords) {
        try {
            setNumberRecords(new Integer(numberRecords).intValue());
        } catch (Exception e) {
            setNumberRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberRecordsError;
    } // method setNumberRecords

    /**
     * Called when an exception has occured
     * @param  numberRecords (String)
     */
    private void setNumberRecordsError (int numberRecords, Exception e, int error) {
        this.numberRecords = numberRecords;
        numberRecordsErrorMessage = e.toString();
        numberRecordsError = error;
    } // method setNumberRecordsError


    /**
     * Set the 'numberNullRecords' class variable
     * @param  numberNullRecords (int)
     */
    public int setNumberNullRecords(int numberNullRecords) {
        try {
            if ( ((numberNullRecords == INTNULL) ||
                  (numberNullRecords == INTNULL2)) ||
                !((numberNullRecords < NUMBER_NULL_RECORDS_MN) ||
                  (numberNullRecords > NUMBER_NULL_RECORDS_MX)) ) {
                this.numberNullRecords = numberNullRecords;
                numberNullRecordsError = ERROR_NORMAL;
            } else {
                throw numberNullRecordsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNumberNullRecordsError(INTNULL, e, ERROR_LOCAL);
        } // try
        return numberNullRecordsError;
    } // method setNumberNullRecords

    /**
     * Set the 'numberNullRecords' class variable
     * @param  numberNullRecords (Integer)
     */
    public int setNumberNullRecords(Integer numberNullRecords) {
        try {
            setNumberNullRecords(numberNullRecords.intValue());
        } catch (Exception e) {
            setNumberNullRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberNullRecordsError;
    } // method setNumberNullRecords

    /**
     * Set the 'numberNullRecords' class variable
     * @param  numberNullRecords (Float)
     */
    public int setNumberNullRecords(Float numberNullRecords) {
        try {
            if (numberNullRecords.floatValue() == FLOATNULL) {
                setNumberNullRecords(INTNULL);
            } else {
                setNumberNullRecords(numberNullRecords.intValue());
            } // if (numberNullRecords.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setNumberNullRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberNullRecordsError;
    } // method setNumberNullRecords

    /**
     * Set the 'numberNullRecords' class variable
     * @param  numberNullRecords (String)
     */
    public int setNumberNullRecords(String numberNullRecords) {
        try {
            setNumberNullRecords(new Integer(numberNullRecords).intValue());
        } catch (Exception e) {
            setNumberNullRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberNullRecordsError;
    } // method setNumberNullRecords

    /**
     * Called when an exception has occured
     * @param  numberNullRecords (String)
     */
    private void setNumberNullRecordsError (int numberNullRecords, Exception e, int error) {
        this.numberNullRecords = numberNullRecords;
        numberNullRecordsErrorMessage = e.toString();
        numberNullRecordsError = error;
    } // method setNumberNullRecordsError


    /**
     * Set the 'loadDate' class variable
     * @param  loadDate (Timestamp)
     */
    public int setLoadDate(Timestamp loadDate) {
        try {
            if (loadDate == null) { this.loadDate = DATENULL; }
            if ( (loadDate.equals(DATENULL) ||
                  loadDate.equals(DATENULL2)) ||
                !(loadDate.before(LOAD_DATE_MN) ||
                  loadDate.after(LOAD_DATE_MX)) ) {
                this.loadDate = loadDate;
                loadDateError = ERROR_NORMAL;
            } else {
                throw loadDateOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLoadDateError(DATENULL, e, ERROR_LOCAL);
        } // try
        return loadDateError;
    } // method setLoadDate

    /**
     * Set the 'loadDate' class variable
     * @param  loadDate (java.util.Date)
     */
    public int setLoadDate(java.util.Date loadDate) {
        try {
            setLoadDate(new Timestamp(loadDate.getTime()));
        } catch (Exception e) {
            setLoadDateError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return loadDateError;
    } // method setLoadDate

    /**
     * Set the 'loadDate' class variable
     * @param  loadDate (String)
     */
    public int setLoadDate(String loadDate) {
        try {
            int len = loadDate.length();
            switch (len) {
            // date &/or times
                case 4: loadDate += "-01";
                case 7: loadDate += "-01";
                case 10: loadDate += " 00";
                case 13: loadDate += ":00";
                case 16: loadDate += ":00"; break;
                // times only
                case 5: loadDate = "1800-01-01 " + loadDate + ":00"; break;
                case 8: loadDate = "1800-01-01 " + loadDate; break;
            } // switch
            if (dbg) System.out.println ("loadDate = " + loadDate);
            setLoadDate(Timestamp.valueOf(loadDate));
        } catch (Exception e) {
            setLoadDateError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return loadDateError;
    } // method setLoadDate

    /**
     * Called when an exception has occured
     * @param  loadDate (String)
     */
    private void setLoadDateError (Timestamp loadDate, Exception e, int error) {
        this.loadDate = loadDate;
        loadDateErrorMessage = e.toString();
        loadDateError = error;
    } // method setLoadDateError


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
     * Return the 'instrumentCode' class variable
     * @return instrumentCode (int)
     */
    public int getInstrumentCode() {
        return instrumentCode;
    } // method getInstrumentCode

    /**
     * Return the 'instrumentCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getInstrumentCode methods
     * @return instrumentCode (String)
     */
    public String getInstrumentCode(String s) {
        return ((instrumentCode != INTNULL) ? new Integer(instrumentCode).toString() : "");
    } // method getInstrumentCode


    /**
     * Return the 'heightSurface' class variable
     * @return heightSurface (float)
     */
    public float getHeightSurface() {
        return heightSurface;
    } // method getHeightSurface

    /**
     * Return the 'heightSurface' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getHeightSurface methods
     * @return heightSurface (String)
     */
    public String getHeightSurface(String s) {
        return ((heightSurface != FLOATNULL) ? new Float(heightSurface).toString() : "");
    } // method getHeightSurface


    /**
     * Return the 'heightMsl' class variable
     * @return heightMsl (float)
     */
    public float getHeightMsl() {
        return heightMsl;
    } // method getHeightMsl

    /**
     * Return the 'heightMsl' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getHeightMsl methods
     * @return heightMsl (String)
     */
    public String getHeightMsl(String s) {
        return ((heightMsl != FLOATNULL) ? new Float(heightMsl).toString() : "");
    } // method getHeightMsl


    /**
     * Return the 'speedCorrFactor' class variable
     * @return speedCorrFactor (float)
     */
    public float getSpeedCorrFactor() {
        return speedCorrFactor;
    } // method getSpeedCorrFactor

    /**
     * Return the 'speedCorrFactor' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpeedCorrFactor methods
     * @return speedCorrFactor (String)
     */
    public String getSpeedCorrFactor(String s) {
        return ((speedCorrFactor != FLOATNULL) ? new Float(speedCorrFactor).toString() : "");
    } // method getSpeedCorrFactor


    /**
     * Return the 'speedAverMethod' class variable
     * @return speedAverMethod (String)
     */
    public String getSpeedAverMethod() {
        return speedAverMethod;
    } // method getSpeedAverMethod

    /**
     * Return the 'speedAverMethod' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpeedAverMethod methods
     * @return speedAverMethod (String)
     */
    public String getSpeedAverMethod(String s) {
        return (speedAverMethod != CHARNULL ? speedAverMethod.replace('"','\'') : "");
    } // method getSpeedAverMethod


    /**
     * Return the 'dirAverMethod' class variable
     * @return dirAverMethod (String)
     */
    public String getDirAverMethod() {
        return dirAverMethod;
    } // method getDirAverMethod

    /**
     * Return the 'dirAverMethod' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDirAverMethod methods
     * @return dirAverMethod (String)
     */
    public String getDirAverMethod(String s) {
        return (dirAverMethod != CHARNULL ? dirAverMethod.replace('"','\'') : "");
    } // method getDirAverMethod


    /**
     * Return the 'windSamplingInterval' class variable
     * @return windSamplingInterval (int)
     */
    public int getWindSamplingInterval() {
        return windSamplingInterval;
    } // method getWindSamplingInterval

    /**
     * Return the 'windSamplingInterval' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSamplingInterval methods
     * @return windSamplingInterval (String)
     */
    public String getWindSamplingInterval(String s) {
        return ((windSamplingInterval != INTNULL) ? new Integer(windSamplingInterval).toString() : "");
    } // method getWindSamplingInterval


    /**
     * Return the 'startDate' class variable
     * @return startDate (Timestamp)
     */
    public Timestamp getStartDate() {
        return startDate;
    } // method getStartDate

    /**
     * Return the 'startDate' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStartDate methods
     * @return startDate (String)
     */
    public String getStartDate(String s) {
        if (startDate.equals(DATENULL)) {
            return ("");
        } else {
            String startDateStr = startDate.toString();
            return startDateStr.substring(0,startDateStr.indexOf('.'));
        } // if
    } // method getStartDate


    /**
     * Return the 'endDate' class variable
     * @return endDate (Timestamp)
     */
    public Timestamp getEndDate() {
        return endDate;
    } // method getEndDate

    /**
     * Return the 'endDate' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getEndDate methods
     * @return endDate (String)
     */
    public String getEndDate(String s) {
        if (endDate.equals(DATENULL)) {
            return ("");
        } else {
            String endDateStr = endDate.toString();
            return endDateStr.substring(0,endDateStr.indexOf('.'));
        } // if
    } // method getEndDate


    /**
     * Return the 'sampleInterval' class variable
     * @return sampleInterval (int)
     */
    public int getSampleInterval() {
        return sampleInterval;
    } // method getSampleInterval

    /**
     * Return the 'sampleInterval' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSampleInterval methods
     * @return sampleInterval (String)
     */
    public String getSampleInterval(String s) {
        return ((sampleInterval != INTNULL) ? new Integer(sampleInterval).toString() : "");
    } // method getSampleInterval


    /**
     * Return the 'numberRecords' class variable
     * @return numberRecords (int)
     */
    public int getNumberRecords() {
        return numberRecords;
    } // method getNumberRecords

    /**
     * Return the 'numberRecords' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNumberRecords methods
     * @return numberRecords (String)
     */
    public String getNumberRecords(String s) {
        return ((numberRecords != INTNULL) ? new Integer(numberRecords).toString() : "");
    } // method getNumberRecords


    /**
     * Return the 'numberNullRecords' class variable
     * @return numberNullRecords (int)
     */
    public int getNumberNullRecords() {
        return numberNullRecords;
    } // method getNumberNullRecords

    /**
     * Return the 'numberNullRecords' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNumberNullRecords methods
     * @return numberNullRecords (String)
     */
    public String getNumberNullRecords(String s) {
        return ((numberNullRecords != INTNULL) ? new Integer(numberNullRecords).toString() : "");
    } // method getNumberNullRecords


    /**
     * Return the 'loadDate' class variable
     * @return loadDate (Timestamp)
     */
    public Timestamp getLoadDate() {
        return loadDate;
    } // method getLoadDate

    /**
     * Return the 'loadDate' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLoadDate methods
     * @return loadDate (String)
     */
    public String getLoadDate(String s) {
        if (loadDate.equals(DATENULL)) {
            return ("");
        } else {
            String loadDateStr = loadDate.toString();
            return loadDateStr.substring(0,loadDateStr.indexOf('.'));
        } // if
    } // method getLoadDate


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
            (instrumentCode == INTNULL) &&
            (heightSurface == FLOATNULL) &&
            (heightMsl == FLOATNULL) &&
            (speedCorrFactor == FLOATNULL) &&
            (speedAverMethod == CHARNULL) &&
            (dirAverMethod == CHARNULL) &&
            (windSamplingInterval == INTNULL) &&
            (startDate.equals(DATENULL)) &&
            (endDate.equals(DATENULL)) &&
            (sampleInterval == INTNULL) &&
            (numberRecords == INTNULL) &&
            (numberNullRecords == INTNULL) &&
            (loadDate == DATENULL)) {
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
            instrumentCodeError +
            heightSurfaceError +
            heightMslError +
            speedCorrFactorError +
            speedAverMethodError +
            dirAverMethodError +
            windSamplingIntervalError +
            startDateError +
            endDateError +
            sampleIntervalError +
            numberRecordsError +
            numberNullRecordsError +
            loadDateError;
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
     * Gets the errorcode for the instrumentCode instance variable
     * @return errorcode (int)
     */
    public int getInstrumentCodeError() {
        return instrumentCodeError;
    } // method getInstrumentCodeError

    /**
     * Gets the errorMessage for the instrumentCode instance variable
     * @return errorMessage (String)
     */
    public String getInstrumentCodeErrorMessage() {
        return instrumentCodeErrorMessage;
    } // method getInstrumentCodeErrorMessage


    /**
     * Gets the errorcode for the heightSurface instance variable
     * @return errorcode (int)
     */
    public int getHeightSurfaceError() {
        return heightSurfaceError;
    } // method getHeightSurfaceError

    /**
     * Gets the errorMessage for the heightSurface instance variable
     * @return errorMessage (String)
     */
    public String getHeightSurfaceErrorMessage() {
        return heightSurfaceErrorMessage;
    } // method getHeightSurfaceErrorMessage


    /**
     * Gets the errorcode for the heightMsl instance variable
     * @return errorcode (int)
     */
    public int getHeightMslError() {
        return heightMslError;
    } // method getHeightMslError

    /**
     * Gets the errorMessage for the heightMsl instance variable
     * @return errorMessage (String)
     */
    public String getHeightMslErrorMessage() {
        return heightMslErrorMessage;
    } // method getHeightMslErrorMessage


    /**
     * Gets the errorcode for the speedCorrFactor instance variable
     * @return errorcode (int)
     */
    public int getSpeedCorrFactorError() {
        return speedCorrFactorError;
    } // method getSpeedCorrFactorError

    /**
     * Gets the errorMessage for the speedCorrFactor instance variable
     * @return errorMessage (String)
     */
    public String getSpeedCorrFactorErrorMessage() {
        return speedCorrFactorErrorMessage;
    } // method getSpeedCorrFactorErrorMessage


    /**
     * Gets the errorcode for the speedAverMethod instance variable
     * @return errorcode (int)
     */
    public int getSpeedAverMethodError() {
        return speedAverMethodError;
    } // method getSpeedAverMethodError

    /**
     * Gets the errorMessage for the speedAverMethod instance variable
     * @return errorMessage (String)
     */
    public String getSpeedAverMethodErrorMessage() {
        return speedAverMethodErrorMessage;
    } // method getSpeedAverMethodErrorMessage


    /**
     * Gets the errorcode for the dirAverMethod instance variable
     * @return errorcode (int)
     */
    public int getDirAverMethodError() {
        return dirAverMethodError;
    } // method getDirAverMethodError

    /**
     * Gets the errorMessage for the dirAverMethod instance variable
     * @return errorMessage (String)
     */
    public String getDirAverMethodErrorMessage() {
        return dirAverMethodErrorMessage;
    } // method getDirAverMethodErrorMessage


    /**
     * Gets the errorcode for the windSamplingInterval instance variable
     * @return errorcode (int)
     */
    public int getWindSamplingIntervalError() {
        return windSamplingIntervalError;
    } // method getWindSamplingIntervalError

    /**
     * Gets the errorMessage for the windSamplingInterval instance variable
     * @return errorMessage (String)
     */
    public String getWindSamplingIntervalErrorMessage() {
        return windSamplingIntervalErrorMessage;
    } // method getWindSamplingIntervalErrorMessage


    /**
     * Gets the errorcode for the startDate instance variable
     * @return errorcode (int)
     */
    public int getStartDateError() {
        return startDateError;
    } // method getStartDateError

    /**
     * Gets the errorMessage for the startDate instance variable
     * @return errorMessage (String)
     */
    public String getStartDateErrorMessage() {
        return startDateErrorMessage;
    } // method getStartDateErrorMessage


    /**
     * Gets the errorcode for the endDate instance variable
     * @return errorcode (int)
     */
    public int getEndDateError() {
        return endDateError;
    } // method getEndDateError

    /**
     * Gets the errorMessage for the endDate instance variable
     * @return errorMessage (String)
     */
    public String getEndDateErrorMessage() {
        return endDateErrorMessage;
    } // method getEndDateErrorMessage


    /**
     * Gets the errorcode for the sampleInterval instance variable
     * @return errorcode (int)
     */
    public int getSampleIntervalError() {
        return sampleIntervalError;
    } // method getSampleIntervalError

    /**
     * Gets the errorMessage for the sampleInterval instance variable
     * @return errorMessage (String)
     */
    public String getSampleIntervalErrorMessage() {
        return sampleIntervalErrorMessage;
    } // method getSampleIntervalErrorMessage


    /**
     * Gets the errorcode for the numberRecords instance variable
     * @return errorcode (int)
     */
    public int getNumberRecordsError() {
        return numberRecordsError;
    } // method getNumberRecordsError

    /**
     * Gets the errorMessage for the numberRecords instance variable
     * @return errorMessage (String)
     */
    public String getNumberRecordsErrorMessage() {
        return numberRecordsErrorMessage;
    } // method getNumberRecordsErrorMessage


    /**
     * Gets the errorcode for the numberNullRecords instance variable
     * @return errorcode (int)
     */
    public int getNumberNullRecordsError() {
        return numberNullRecordsError;
    } // method getNumberNullRecordsError

    /**
     * Gets the errorMessage for the numberNullRecords instance variable
     * @return errorMessage (String)
     */
    public String getNumberNullRecordsErrorMessage() {
        return numberNullRecordsErrorMessage;
    } // method getNumberNullRecordsErrorMessage


    /**
     * Gets the errorcode for the loadDate instance variable
     * @return errorcode (int)
     */
    public int getLoadDateError() {
        return loadDateError;
    } // method getLoadDateError

    /**
     * Gets the errorMessage for the loadDate instance variable
     * @return errorMessage (String)
     */
    public String getLoadDateErrorMessage() {
        return loadDateErrorMessage;
    } // method getLoadDateErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of WetPeriod. e.g.<pre>
     * WetPeriod wetPeriod = new WetPeriod(<val1>);
     * WetPeriod wetPeriodArray[] = wetPeriod.get();</pre>
     * will get the WetPeriod record where code = <val1>.
     * @return Array of WetPeriod (WetPeriod[])
     */
    public WetPeriod[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * WetPeriod wetPeriodArray[] =
     *     new WetPeriod().get(WetPeriod.CODE+"=<val1>");</pre>
     * will get the WetPeriod record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of WetPeriod (WetPeriod[])
     */
    public WetPeriod[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * WetPeriod wetPeriodArray[] =
     *     new WetPeriod().get("1=1",wetPeriod.CODE);</pre>
     * will get the all the WetPeriod records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetPeriod (WetPeriod[])
     */
    public WetPeriod[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = WetPeriod.CODE,WetPeriod.STATION_ID;
     * String where = WetPeriod.CODE + "=<val1";
     * String order = WetPeriod.CODE;
     * WetPeriod wetPeriodArray[] =
     *     new WetPeriod().get(columns, where, order);</pre>
     * will get the code and stationId colums of all WetPeriod records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetPeriod (WetPeriod[])
     */
    public WetPeriod[] get (String fields, String where, String order) {
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
     * and transforms it into an array of WetPeriod.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private WetPeriod[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol                 = db.getColNumber(CODE);
        int stationIdCol            = db.getColNumber(STATION_ID);
        int instrumentCodeCol       = db.getColNumber(INSTRUMENT_CODE);
        int heightSurfaceCol        = db.getColNumber(HEIGHT_SURFACE);
        int heightMslCol            = db.getColNumber(HEIGHT_MSL);
        int speedCorrFactorCol      = db.getColNumber(SPEED_CORR_FACTOR);
        int speedAverMethodCol      = db.getColNumber(SPEED_AVER_METHOD);
        int dirAverMethodCol        = db.getColNumber(DIR_AVER_METHOD);
        int windSamplingIntervalCol = db.getColNumber(WIND_SAMPLING_INTERVAL);
        int startDateCol            = db.getColNumber(START_DATE);
        int endDateCol              = db.getColNumber(END_DATE);
        int sampleIntervalCol       = db.getColNumber(SAMPLE_INTERVAL);
        int numberRecordsCol        = db.getColNumber(NUMBER_RECORDS);
        int numberNullRecordsCol    = db.getColNumber(NUMBER_NULL_RECORDS);
        int loadDateCol             = db.getColNumber(LOAD_DATE);
        WetPeriod[] cArray = new WetPeriod[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new WetPeriod();
            if (codeCol != -1)
                cArray[i].setCode                ((String) row.elementAt(codeCol));
            if (stationIdCol != -1)
                cArray[i].setStationId           ((String) row.elementAt(stationIdCol));
            if (instrumentCodeCol != -1)
                cArray[i].setInstrumentCode      ((String) row.elementAt(instrumentCodeCol));
            if (heightSurfaceCol != -1)
                cArray[i].setHeightSurface       ((String) row.elementAt(heightSurfaceCol));
            if (heightMslCol != -1)
                cArray[i].setHeightMsl           ((String) row.elementAt(heightMslCol));
            if (speedCorrFactorCol != -1)
                cArray[i].setSpeedCorrFactor     ((String) row.elementAt(speedCorrFactorCol));
            if (speedAverMethodCol != -1)
                cArray[i].setSpeedAverMethod     ((String) row.elementAt(speedAverMethodCol));
            if (dirAverMethodCol != -1)
                cArray[i].setDirAverMethod       ((String) row.elementAt(dirAverMethodCol));
            if (windSamplingIntervalCol != -1)
                cArray[i].setWindSamplingInterval((String) row.elementAt(windSamplingIntervalCol));
            if (startDateCol != -1)
                cArray[i].setStartDate           ((String) row.elementAt(startDateCol));
            if (endDateCol != -1)
                cArray[i].setEndDate             ((String) row.elementAt(endDateCol));
            if (sampleIntervalCol != -1)
                cArray[i].setSampleInterval      ((String) row.elementAt(sampleIntervalCol));
            if (numberRecordsCol != -1)
                cArray[i].setNumberRecords       ((String) row.elementAt(numberRecordsCol));
            if (numberNullRecordsCol != -1)
                cArray[i].setNumberNullRecords   ((String) row.elementAt(numberNullRecordsCol));
            if (loadDateCol != -1)
                cArray[i].setLoadDate            ((String) row.elementAt(loadDateCol));
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
     *     new WetPeriod(
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
     *         <val15>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     stationId            = <val2>,
     *     instrumentCode       = <val3>,
     *     heightSurface        = <val4>,
     *     heightMsl            = <val5>,
     *     speedCorrFactor      = <val6>,
     *     speedAverMethod      = <val7>,
     *     dirAverMethod        = <val8>,
     *     windSamplingInterval = <val9>,
     *     startDate            = <val10>,
     *     endDate              = <val11>,
     *     sampleInterval       = <val12>,
     *     numberRecords        = <val13>,
     *     numberNullRecords    = <val14>,
     *     loadDate             = <val15>.
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
     * Boolean success = new WetPeriod(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.DATENULL,
     *     Tables.DATENULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.DATENULL).del();</pre>
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
     * WetPeriod wetPeriod = new WetPeriod();
     * success = wetPeriod.del(WetPeriod.CODE+"=<val1>");</pre>
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
     * update are taken from the WetPeriod argument, .e.g.<pre>
     * Boolean success
     * WetPeriod updWetPeriod = new WetPeriod();
     * updWetPeriod.setStationId(<val2>);
     * WetPeriod whereWetPeriod = new WetPeriod(<val1>);
     * success = whereWetPeriod.upd(updWetPeriod);</pre>
     * will update StationId to <val2> for all records where
     * code = <val1>.
     * @param  wetPeriod  A WetPeriod variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(WetPeriod wetPeriod) {
        return db.update (TABLE, createColVals(wetPeriod), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WetPeriod updWetPeriod = new WetPeriod();
     * updWetPeriod.setStationId(<val2>);
     * WetPeriod whereWetPeriod = new WetPeriod();
     * success = whereWetPeriod.upd(
     *     updWetPeriod, WetPeriod.CODE+"=<val1>");</pre>
     * will update StationId to <val2> for all records where
     * code = <val1>.
     * @param  wetPeriod  A WetPeriod variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(WetPeriod wetPeriod, String where) {
        return db.update (TABLE, createColVals(wetPeriod), where);
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
        if (getInstrumentCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTRUMENT_CODE + "=" + getInstrumentCode("");
        } // if getInstrumentCode
        if (getHeightSurface() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + HEIGHT_SURFACE + "=" + getHeightSurface("");
        } // if getHeightSurface
        if (getHeightMsl() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + HEIGHT_MSL + "=" + getHeightMsl("");
        } // if getHeightMsl
        if (getSpeedCorrFactor() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPEED_CORR_FACTOR + "=" + getSpeedCorrFactor("");
        } // if getSpeedCorrFactor
        if (getSpeedAverMethod() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPEED_AVER_METHOD + "='" + getSpeedAverMethod() + "'";
        } // if getSpeedAverMethod
        if (getDirAverMethod() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DIR_AVER_METHOD + "='" + getDirAverMethod() + "'";
        } // if getDirAverMethod
        if (getWindSamplingInterval() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SAMPLING_INTERVAL + "=" + getWindSamplingInterval("");
        } // if getWindSamplingInterval
        if (!getStartDate().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + START_DATE +
                "=" + Tables.getDateFormat(getStartDate());
        } // if getStartDate
        if (!getEndDate().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + END_DATE +
                "=" + Tables.getDateFormat(getEndDate());
        } // if getEndDate
        if (getSampleInterval() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SAMPLE_INTERVAL + "=" + getSampleInterval("");
        } // if getSampleInterval
        if (getNumberRecords() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NUMBER_RECORDS + "=" + getNumberRecords("");
        } // if getNumberRecords
        if (getNumberNullRecords() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NUMBER_NULL_RECORDS + "=" + getNumberNullRecords("");
        } // if getNumberNullRecords
        if (!getLoadDate().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LOAD_DATE +
                "=" + Tables.getDateFormat(getLoadDate());
        } // if getLoadDate
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(WetPeriod aVar) {
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
        if (aVar.getInstrumentCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTRUMENT_CODE +"=";
            colVals += (aVar.getInstrumentCode() == INTNULL2 ?
                "null" : aVar.getInstrumentCode(""));
        } // if aVar.getInstrumentCode
        if (aVar.getHeightSurface() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += HEIGHT_SURFACE +"=";
            colVals += (aVar.getHeightSurface() == FLOATNULL2 ?
                "null" : aVar.getHeightSurface(""));
        } // if aVar.getHeightSurface
        if (aVar.getHeightMsl() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += HEIGHT_MSL +"=";
            colVals += (aVar.getHeightMsl() == FLOATNULL2 ?
                "null" : aVar.getHeightMsl(""));
        } // if aVar.getHeightMsl
        if (aVar.getSpeedCorrFactor() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPEED_CORR_FACTOR +"=";
            colVals += (aVar.getSpeedCorrFactor() == FLOATNULL2 ?
                "null" : aVar.getSpeedCorrFactor(""));
        } // if aVar.getSpeedCorrFactor
        if (aVar.getSpeedAverMethod() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPEED_AVER_METHOD +"=";
            colVals += (aVar.getSpeedAverMethod().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSpeedAverMethod() + "'");
        } // if aVar.getSpeedAverMethod
        if (aVar.getDirAverMethod() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DIR_AVER_METHOD +"=";
            colVals += (aVar.getDirAverMethod().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDirAverMethod() + "'");
        } // if aVar.getDirAverMethod
        if (aVar.getWindSamplingInterval() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SAMPLING_INTERVAL +"=";
            colVals += (aVar.getWindSamplingInterval() == INTNULL2 ?
                "null" : aVar.getWindSamplingInterval(""));
        } // if aVar.getWindSamplingInterval
        if (!aVar.getStartDate().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += START_DATE +"=";
            colVals += (aVar.getStartDate().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getStartDate()));
        } // if aVar.getStartDate
        if (!aVar.getEndDate().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += END_DATE +"=";
            colVals += (aVar.getEndDate().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getEndDate()));
        } // if aVar.getEndDate
        if (aVar.getSampleInterval() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SAMPLE_INTERVAL +"=";
            colVals += (aVar.getSampleInterval() == INTNULL2 ?
                "null" : aVar.getSampleInterval(""));
        } // if aVar.getSampleInterval
        if (aVar.getNumberRecords() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NUMBER_RECORDS +"=";
            colVals += (aVar.getNumberRecords() == INTNULL2 ?
                "null" : aVar.getNumberRecords(""));
        } // if aVar.getNumberRecords
        if (aVar.getNumberNullRecords() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NUMBER_NULL_RECORDS +"=";
            colVals += (aVar.getNumberNullRecords() == INTNULL2 ?
                "null" : aVar.getNumberNullRecords(""));
        } // if aVar.getNumberNullRecords
        if (!aVar.getLoadDate().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LOAD_DATE +"=";
            colVals += (aVar.getLoadDate().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getLoadDate()));
        } // if aVar.getLoadDate
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
        if (getInstrumentCode() != INTNULL) {
            columns = columns + "," + INSTRUMENT_CODE;
        } // if getInstrumentCode
        if (getHeightSurface() != FLOATNULL) {
            columns = columns + "," + HEIGHT_SURFACE;
        } // if getHeightSurface
        if (getHeightMsl() != FLOATNULL) {
            columns = columns + "," + HEIGHT_MSL;
        } // if getHeightMsl
        if (getSpeedCorrFactor() != FLOATNULL) {
            columns = columns + "," + SPEED_CORR_FACTOR;
        } // if getSpeedCorrFactor
        if (getSpeedAverMethod() != CHARNULL) {
            columns = columns + "," + SPEED_AVER_METHOD;
        } // if getSpeedAverMethod
        if (getDirAverMethod() != CHARNULL) {
            columns = columns + "," + DIR_AVER_METHOD;
        } // if getDirAverMethod
        if (getWindSamplingInterval() != INTNULL) {
            columns = columns + "," + WIND_SAMPLING_INTERVAL;
        } // if getWindSamplingInterval
        if (!getStartDate().equals(DATENULL)) {
            columns = columns + "," + START_DATE;
        } // if getStartDate
        if (!getEndDate().equals(DATENULL)) {
            columns = columns + "," + END_DATE;
        } // if getEndDate
        if (getSampleInterval() != INTNULL) {
            columns = columns + "," + SAMPLE_INTERVAL;
        } // if getSampleInterval
        if (getNumberRecords() != INTNULL) {
            columns = columns + "," + NUMBER_RECORDS;
        } // if getNumberRecords
        if (getNumberNullRecords() != INTNULL) {
            columns = columns + "," + NUMBER_NULL_RECORDS;
        } // if getNumberNullRecords
        if (!getLoadDate().equals(DATENULL)) {
            columns = columns + "," + LOAD_DATE;
        } // if getLoadDate
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
        if (getInstrumentCode() != INTNULL) {
            values  = values  + "," + getInstrumentCode("");
        } // if getInstrumentCode
        if (getHeightSurface() != FLOATNULL) {
            values  = values  + "," + getHeightSurface("");
        } // if getHeightSurface
        if (getHeightMsl() != FLOATNULL) {
            values  = values  + "," + getHeightMsl("");
        } // if getHeightMsl
        if (getSpeedCorrFactor() != FLOATNULL) {
            values  = values  + "," + getSpeedCorrFactor("");
        } // if getSpeedCorrFactor
        if (getSpeedAverMethod() != CHARNULL) {
            values  = values  + ",'" + getSpeedAverMethod() + "'";
        } // if getSpeedAverMethod
        if (getDirAverMethod() != CHARNULL) {
            values  = values  + ",'" + getDirAverMethod() + "'";
        } // if getDirAverMethod
        if (getWindSamplingInterval() != INTNULL) {
            values  = values  + "," + getWindSamplingInterval("");
        } // if getWindSamplingInterval
        if (!getStartDate().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getStartDate());
        } // if getStartDate
        if (!getEndDate().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getEndDate());
        } // if getEndDate
        if (getSampleInterval() != INTNULL) {
            values  = values  + "," + getSampleInterval("");
        } // if getSampleInterval
        if (getNumberRecords() != INTNULL) {
            values  = values  + "," + getNumberRecords("");
        } // if getNumberRecords
        if (getNumberNullRecords() != INTNULL) {
            values  = values  + "," + getNumberNullRecords("");
        } // if getNumberNullRecords
        if (!getLoadDate().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getLoadDate());
        } // if getLoadDate
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")                 + "|" +
            getStationId("")            + "|" +
            getInstrumentCode("")       + "|" +
            getHeightSurface("")        + "|" +
            getHeightMsl("")            + "|" +
            getSpeedCorrFactor("")      + "|" +
            getSpeedAverMethod("")      + "|" +
            getDirAverMethod("")        + "|" +
            getWindSamplingInterval("") + "|" +
            getStartDate("")            + "|" +
            getEndDate("")              + "|" +
            getSampleInterval("")       + "|" +
            getNumberRecords("")        + "|" +
            getNumberNullRecords("")    + "|" +
            getLoadDate("")             + "|";
    } // method toString

} // class WetPeriod
