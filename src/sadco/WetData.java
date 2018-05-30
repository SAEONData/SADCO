package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WET_DATA table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class WetData extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WET_DATA";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The periodCode field name */
    public static final String PERIOD_CODE = "PERIOD_CODE";
    /** The dateTime field name */
    public static final String DATE_TIME = "DATE_TIME";
    /** The airTempAve field name */
    public static final String AIR_TEMP_AVE = "AIR_TEMP_AVE";
    /** The airTempMin field name */
    public static final String AIR_TEMP_MIN = "AIR_TEMP_MIN";
    /** The airTempMinTime field name */
    public static final String AIR_TEMP_MIN_TIME = "AIR_TEMP_MIN_TIME";
    /** The airTempMax field name */
    public static final String AIR_TEMP_MAX = "AIR_TEMP_MAX";
    /** The airTempMaxTime field name */
    public static final String AIR_TEMP_MAX_TIME = "AIR_TEMP_MAX_TIME";
    /** The barometricPressure field name */
    public static final String BAROMETRIC_PRESSURE = "BAROMETRIC_PRESSURE";
    /** The fog field name */
    public static final String FOG = "FOG";
    /** The rainfall field name */
    public static final String RAINFALL = "RAINFALL";
    /** The relativeHumidity field name */
    public static final String RELATIVE_HUMIDITY = "RELATIVE_HUMIDITY";
    /** The solarRadiation field name */
    public static final String SOLAR_RADIATION = "SOLAR_RADIATION";
    /** The solarRadiationMax field name */
    public static final String SOLAR_RADIATION_MAX = "SOLAR_RADIATION_MAX";
    /** The windDir field name */
    public static final String WIND_DIR = "WIND_DIR";
    /** The windSpeedAve field name */
    public static final String WIND_SPEED_AVE = "WIND_SPEED_AVE";
    /** The windSpeedMin field name */
    public static final String WIND_SPEED_MIN = "WIND_SPEED_MIN";
    /** The windSpeedMax field name */
    public static final String WIND_SPEED_MAX = "WIND_SPEED_MAX";
    /** The windSpeedMaxTime field name */
    public static final String WIND_SPEED_MAX_TIME = "WIND_SPEED_MAX_TIME";
    /** The windSpeedMaxLength field name */
    public static final String WIND_SPEED_MAX_LENGTH = "WIND_SPEED_MAX_LENGTH";
    /** The windSpeedMaxDir field name */
    public static final String WIND_SPEED_MAX_DIR = "WIND_SPEED_MAX_DIR";
    /** The windSpeedStd field name */
    public static final String WIND_SPEED_STD = "WIND_SPEED_STD";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private int       periodCode;
    private Timestamp dateTime;
    private float     airTempAve;
    private float     airTempMin;
    private Timestamp airTempMinTime;
    private float     airTempMax;
    private Timestamp airTempMaxTime;
    private float     barometricPressure;
    private float     fog;
    private float     rainfall;
    private float     relativeHumidity;
    private float     solarRadiation;
    private float     solarRadiationMax;
    private float     windDir;
    private float     windSpeedAve;
    private float     windSpeedMin;
    private float     windSpeedMax;
    private Timestamp windSpeedMaxTime;
    private int       windSpeedMaxLength;
    private float     windSpeedMaxDir;
    private float     windSpeedStd;

    /** The error variables  */
    private int stationIdError          = ERROR_NORMAL;
    private int periodCodeError         = ERROR_NORMAL;
    private int dateTimeError           = ERROR_NORMAL;
    private int airTempAveError         = ERROR_NORMAL;
    private int airTempMinError         = ERROR_NORMAL;
    private int airTempMinTimeError     = ERROR_NORMAL;
    private int airTempMaxError         = ERROR_NORMAL;
    private int airTempMaxTimeError     = ERROR_NORMAL;
    private int barometricPressureError = ERROR_NORMAL;
    private int fogError                = ERROR_NORMAL;
    private int rainfallError           = ERROR_NORMAL;
    private int relativeHumidityError   = ERROR_NORMAL;
    private int solarRadiationError     = ERROR_NORMAL;
    private int solarRadiationMaxError  = ERROR_NORMAL;
    private int windDirError            = ERROR_NORMAL;
    private int windSpeedAveError       = ERROR_NORMAL;
    private int windSpeedMinError       = ERROR_NORMAL;
    private int windSpeedMaxError       = ERROR_NORMAL;
    private int windSpeedMaxTimeError   = ERROR_NORMAL;
    private int windSpeedMaxLengthError = ERROR_NORMAL;
    private int windSpeedMaxDirError    = ERROR_NORMAL;
    private int windSpeedStdError       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage          = "";
    private String periodCodeErrorMessage         = "";
    private String dateTimeErrorMessage           = "";
    private String airTempAveErrorMessage         = "";
    private String airTempMinErrorMessage         = "";
    private String airTempMinTimeErrorMessage     = "";
    private String airTempMaxErrorMessage         = "";
    private String airTempMaxTimeErrorMessage     = "";
    private String barometricPressureErrorMessage = "";
    private String fogErrorMessage                = "";
    private String rainfallErrorMessage           = "";
    private String relativeHumidityErrorMessage   = "";
    private String solarRadiationErrorMessage     = "";
    private String solarRadiationMaxErrorMessage  = "";
    private String windDirErrorMessage            = "";
    private String windSpeedAveErrorMessage       = "";
    private String windSpeedMinErrorMessage       = "";
    private String windSpeedMaxErrorMessage       = "";
    private String windSpeedMaxTimeErrorMessage   = "";
    private String windSpeedMaxLengthErrorMessage = "";
    private String windSpeedMaxDirErrorMessage    = "";
    private String windSpeedStdErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       PERIOD_CODE_MN = INTMIN;
    public static final int       PERIOD_CODE_MX = INTMAX;
    public static final Timestamp DATE_TIME_MN = DATEMIN;
    public static final Timestamp DATE_TIME_MX = DATEMAX;
    public static final float     AIR_TEMP_AVE_MN = FLOATMIN;
    public static final float     AIR_TEMP_AVE_MX = FLOATMAX;
    public static final float     AIR_TEMP_MIN_MN = FLOATMIN;
    public static final float     AIR_TEMP_MIN_MX = FLOATMAX;
    public static final Timestamp AIR_TEMP_MIN_TIME_MN = DATEMIN;
    public static final Timestamp AIR_TEMP_MIN_TIME_MX = DATEMAX;
    public static final float     AIR_TEMP_MAX_MN = FLOATMIN;
    public static final float     AIR_TEMP_MAX_MX = FLOATMAX;
    public static final Timestamp AIR_TEMP_MAX_TIME_MN = DATEMIN;
    public static final Timestamp AIR_TEMP_MAX_TIME_MX = DATEMAX;
    public static final float     BAROMETRIC_PRESSURE_MN = FLOATMIN;
    public static final float     BAROMETRIC_PRESSURE_MX = FLOATMAX;
    public static final float     FOG_MN = FLOATMIN;
    public static final float     FOG_MX = FLOATMAX;
    public static final float     RAINFALL_MN = FLOATMIN;
    public static final float     RAINFALL_MX = FLOATMAX;
    public static final float     RELATIVE_HUMIDITY_MN = FLOATMIN;
    public static final float     RELATIVE_HUMIDITY_MX = FLOATMAX;
    public static final float     SOLAR_RADIATION_MN = FLOATMIN;
    public static final float     SOLAR_RADIATION_MX = FLOATMAX;
    public static final float     SOLAR_RADIATION_MAX_MN = FLOATMIN;
    public static final float     SOLAR_RADIATION_MAX_MX = FLOATMAX;
    public static final float     WIND_DIR_MN = FLOATMIN;
    public static final float     WIND_DIR_MX = FLOATMAX;
    public static final float     WIND_SPEED_AVE_MN = FLOATMIN;
    public static final float     WIND_SPEED_AVE_MX = FLOATMAX;
    public static final float     WIND_SPEED_MIN_MN = FLOATMIN;
    public static final float     WIND_SPEED_MIN_MX = FLOATMAX;
    public static final float     WIND_SPEED_MAX_MN = FLOATMIN;
    public static final float     WIND_SPEED_MAX_MX = FLOATMAX;
    public static final Timestamp WIND_SPEED_MAX_TIME_MN = DATEMIN;
    public static final Timestamp WIND_SPEED_MAX_TIME_MX = DATEMAX;
    public static final int       WIND_SPEED_MAX_LENGTH_MN = INTMIN;
    public static final int       WIND_SPEED_MAX_LENGTH_MX = INTMAX;
    public static final float     WIND_SPEED_MAX_DIR_MN = FLOATMIN;
    public static final float     WIND_SPEED_MAX_DIR_MX = FLOATMAX;
    public static final float     WIND_SPEED_STD_MN = FLOATMIN;
    public static final float     WIND_SPEED_STD_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception periodCodeOutOfBoundsException =
        new Exception ("'periodCode' out of bounds: " +
            PERIOD_CODE_MN + " - " + PERIOD_CODE_MX);
    Exception dateTimeException =
        new Exception ("'dateTime' is null");
    Exception dateTimeOutOfBoundsException =
        new Exception ("'dateTime' out of bounds: " +
            DATE_TIME_MN + " - " + DATE_TIME_MX);
    Exception airTempAveOutOfBoundsException =
        new Exception ("'airTempAve' out of bounds: " +
            AIR_TEMP_AVE_MN + " - " + AIR_TEMP_AVE_MX);
    Exception airTempMinOutOfBoundsException =
        new Exception ("'airTempMin' out of bounds: " +
            AIR_TEMP_MIN_MN + " - " + AIR_TEMP_MIN_MX);
    Exception airTempMinTimeException =
        new Exception ("'airTempMinTime' is null");
    Exception airTempMinTimeOutOfBoundsException =
        new Exception ("'airTempMinTime' out of bounds: " +
            AIR_TEMP_MIN_TIME_MN + " - " + AIR_TEMP_MIN_TIME_MX);
    Exception airTempMaxOutOfBoundsException =
        new Exception ("'airTempMax' out of bounds: " +
            AIR_TEMP_MAX_MN + " - " + AIR_TEMP_MAX_MX);
    Exception airTempMaxTimeException =
        new Exception ("'airTempMaxTime' is null");
    Exception airTempMaxTimeOutOfBoundsException =
        new Exception ("'airTempMaxTime' out of bounds: " +
            AIR_TEMP_MAX_TIME_MN + " - " + AIR_TEMP_MAX_TIME_MX);
    Exception barometricPressureOutOfBoundsException =
        new Exception ("'barometricPressure' out of bounds: " +
            BAROMETRIC_PRESSURE_MN + " - " + BAROMETRIC_PRESSURE_MX);
    Exception fogOutOfBoundsException =
        new Exception ("'fog' out of bounds: " +
            FOG_MN + " - " + FOG_MX);
    Exception rainfallOutOfBoundsException =
        new Exception ("'rainfall' out of bounds: " +
            RAINFALL_MN + " - " + RAINFALL_MX);
    Exception relativeHumidityOutOfBoundsException =
        new Exception ("'relativeHumidity' out of bounds: " +
            RELATIVE_HUMIDITY_MN + " - " + RELATIVE_HUMIDITY_MX);
    Exception solarRadiationOutOfBoundsException =
        new Exception ("'solarRadiation' out of bounds: " +
            SOLAR_RADIATION_MN + " - " + SOLAR_RADIATION_MX);
    Exception solarRadiationMaxOutOfBoundsException =
        new Exception ("'solarRadiationMax' out of bounds: " +
            SOLAR_RADIATION_MAX_MN + " - " + SOLAR_RADIATION_MAX_MX);
    Exception windDirOutOfBoundsException =
        new Exception ("'windDir' out of bounds: " +
            WIND_DIR_MN + " - " + WIND_DIR_MX);
    Exception windSpeedAveOutOfBoundsException =
        new Exception ("'windSpeedAve' out of bounds: " +
            WIND_SPEED_AVE_MN + " - " + WIND_SPEED_AVE_MX);
    Exception windSpeedMinOutOfBoundsException =
        new Exception ("'windSpeedMin' out of bounds: " +
            WIND_SPEED_MIN_MN + " - " + WIND_SPEED_MIN_MX);
    Exception windSpeedMaxOutOfBoundsException =
        new Exception ("'windSpeedMax' out of bounds: " +
            WIND_SPEED_MAX_MN + " - " + WIND_SPEED_MAX_MX);
    Exception windSpeedMaxTimeException =
        new Exception ("'windSpeedMaxTime' is null");
    Exception windSpeedMaxTimeOutOfBoundsException =
        new Exception ("'windSpeedMaxTime' out of bounds: " +
            WIND_SPEED_MAX_TIME_MN + " - " + WIND_SPEED_MAX_TIME_MX);
    Exception windSpeedMaxLengthOutOfBoundsException =
        new Exception ("'windSpeedMaxLength' out of bounds: " +
            WIND_SPEED_MAX_LENGTH_MN + " - " + WIND_SPEED_MAX_LENGTH_MX);
    Exception windSpeedMaxDirOutOfBoundsException =
        new Exception ("'windSpeedMaxDir' out of bounds: " +
            WIND_SPEED_MAX_DIR_MN + " - " + WIND_SPEED_MAX_DIR_MX);
    Exception windSpeedStdOutOfBoundsException =
        new Exception ("'windSpeedStd' out of bounds: " +
            WIND_SPEED_STD_MN + " - " + WIND_SPEED_STD_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public WetData() {
        clearVars();
        if (dbg) System.out.println ("<br>in WetData constructor 1"); // debug
    } // WetData constructor

    /**
     * Instantiate a WetData object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public WetData(
            String stationId) {
        this();
        setStationId          (stationId         );
        if (dbg) System.out.println ("<br>in WetData constructor 2"); // debug
    } // WetData constructor

    /**
     * Instantiate a WetData object and initialize the instance variables.
     * @param stationId           The stationId          (String)
     * @param periodCode          The periodCode         (int)
     * @param dateTime            The dateTime           (java.util.Date)
     * @param airTempAve          The airTempAve         (float)
     * @param airTempMin          The airTempMin         (float)
     * @param airTempMinTime      The airTempMinTime     (java.util.Date)
     * @param airTempMax          The airTempMax         (float)
     * @param airTempMaxTime      The airTempMaxTime     (java.util.Date)
     * @param barometricPressure  The barometricPressure (float)
     * @param fog                 The fog                (float)
     * @param rainfall            The rainfall           (float)
     * @param relativeHumidity    The relativeHumidity   (float)
     * @param solarRadiation      The solarRadiation     (float)
     * @param solarRadiationMax   The solarRadiationMax  (float)
     * @param windDir             The windDir            (float)
     * @param windSpeedAve        The windSpeedAve       (float)
     * @param windSpeedMin        The windSpeedMin       (float)
     * @param windSpeedMax        The windSpeedMax       (float)
     * @param windSpeedMaxTime    The windSpeedMaxTime   (java.util.Date)
     * @param windSpeedMaxLength  The windSpeedMaxLength (int)
     * @param windSpeedMaxDir     The windSpeedMaxDir    (float)
     * @param windSpeedStd        The windSpeedStd       (float)
     */
    public WetData(
            String         stationId,
            int            periodCode,
            java.util.Date dateTime,
            float          airTempAve,
            float          airTempMin,
            java.util.Date airTempMinTime,
            float          airTempMax,
            java.util.Date airTempMaxTime,
            float          barometricPressure,
            float          fog,
            float          rainfall,
            float          relativeHumidity,
            float          solarRadiation,
            float          solarRadiationMax,
            float          windDir,
            float          windSpeedAve,
            float          windSpeedMin,
            float          windSpeedMax,
            java.util.Date windSpeedMaxTime,
            int            windSpeedMaxLength,
            float          windSpeedMaxDir,
            float          windSpeedStd) {
        this();
        setStationId          (stationId         );
        setPeriodCode         (periodCode        );
        setDateTime           (dateTime          );
        setAirTempAve         (airTempAve        );
        setAirTempMin         (airTempMin        );
        setAirTempMinTime     (airTempMinTime    );
        setAirTempMax         (airTempMax        );
        setAirTempMaxTime     (airTempMaxTime    );
        setBarometricPressure (barometricPressure);
        setFog                (fog               );
        setRainfall           (rainfall          );
        setRelativeHumidity   (relativeHumidity  );
        setSolarRadiation     (solarRadiation    );
        setSolarRadiationMax  (solarRadiationMax );
        setWindDir            (windDir           );
        setWindSpeedAve       (windSpeedAve      );
        setWindSpeedMin       (windSpeedMin      );
        setWindSpeedMax       (windSpeedMax      );
        setWindSpeedMaxTime   (windSpeedMaxTime  );
        setWindSpeedMaxLength (windSpeedMaxLength);
        setWindSpeedMaxDir    (windSpeedMaxDir   );
        setWindSpeedStd       (windSpeedStd      );
        if (dbg) System.out.println ("<br>in WetData constructor 3"); // debug
    } // WetData constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId          (CHARNULL );
        setPeriodCode         (INTNULL  );
        setDateTime           (DATENULL );
        setAirTempAve         (FLOATNULL);
        setAirTempMin         (FLOATNULL);
        setAirTempMinTime     (DATENULL );
        setAirTempMax         (FLOATNULL);
        setAirTempMaxTime     (DATENULL );
        setBarometricPressure (FLOATNULL);
        setFog                (FLOATNULL);
        setRainfall           (FLOATNULL);
        setRelativeHumidity   (FLOATNULL);
        setSolarRadiation     (FLOATNULL);
        setSolarRadiationMax  (FLOATNULL);
        setWindDir            (FLOATNULL);
        setWindSpeedAve       (FLOATNULL);
        setWindSpeedMin       (FLOATNULL);
        setWindSpeedMax       (FLOATNULL);
        setWindSpeedMaxTime   (DATENULL );
        setWindSpeedMaxLength (INTNULL  );
        setWindSpeedMaxDir    (FLOATNULL);
        setWindSpeedStd       (FLOATNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

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
     * Set the 'periodCode' class variable
     * @param  periodCode (int)
     */
    public int setPeriodCode(int periodCode) {
        try {
            if ( ((periodCode == INTNULL) ||
                  (periodCode == INTNULL2)) ||
                !((periodCode < PERIOD_CODE_MN) ||
                  (periodCode > PERIOD_CODE_MX)) ) {
                this.periodCode = periodCode;
                periodCodeError = ERROR_NORMAL;
            } else {
                throw periodCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPeriodCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return periodCodeError;
    } // method setPeriodCode

    /**
     * Set the 'periodCode' class variable
     * @param  periodCode (Integer)
     */
    public int setPeriodCode(Integer periodCode) {
        try {
            setPeriodCode(periodCode.intValue());
        } catch (Exception e) {
            setPeriodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return periodCodeError;
    } // method setPeriodCode

    /**
     * Set the 'periodCode' class variable
     * @param  periodCode (Float)
     */
    public int setPeriodCode(Float periodCode) {
        try {
            if (periodCode.floatValue() == FLOATNULL) {
                setPeriodCode(INTNULL);
            } else {
                setPeriodCode(periodCode.intValue());
            } // if (periodCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPeriodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return periodCodeError;
    } // method setPeriodCode

    /**
     * Set the 'periodCode' class variable
     * @param  periodCode (String)
     */
    public int setPeriodCode(String periodCode) {
        try {
            setPeriodCode(new Integer(periodCode).intValue());
        } catch (Exception e) {
            setPeriodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return periodCodeError;
    } // method setPeriodCode

    /**
     * Called when an exception has occured
     * @param  periodCode (String)
     */
    private void setPeriodCodeError (int periodCode, Exception e, int error) {
        this.periodCode = periodCode;
        periodCodeErrorMessage = e.toString();
        periodCodeError = error;
    } // method setPeriodCodeError


    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (Timestamp)
     */
    public int setDateTime(Timestamp dateTime) {
        try {
            if (dateTime == null) { this.dateTime = DATENULL; }
            if ( (dateTime.equals(DATENULL) ||
                  dateTime.equals(DATENULL2)) ||
                !(dateTime.before(DATE_TIME_MN) ||
                  dateTime.after(DATE_TIME_MX)) ) {
                this.dateTime = dateTime;
                dateTimeError = ERROR_NORMAL;
            } else {
                throw dateTimeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (java.util.Date)
     */
    public int setDateTime(java.util.Date dateTime) {
        try {
            setDateTime(new Timestamp(dateTime.getTime()));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (String)
     */
    public int setDateTime(String dateTime) {
        try {
            int len = dateTime.length();
            switch (len) {
            // date &/or times
                case 4: dateTime += "-01";
                case 7: dateTime += "-01";
                case 10: dateTime += " 00";
                case 13: dateTime += ":00";
                case 16: dateTime += ":00"; break;
                // times only
                case 5: dateTime = "1800-01-01 " + dateTime + ":00"; break;
                case 8: dateTime = "1800-01-01 " + dateTime; break;
            } // switch
            if (dbg) System.out.println ("dateTime = " + dateTime);
            setDateTime(Timestamp.valueOf(dateTime));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Called when an exception has occured
     * @param  dateTime (String)
     */
    private void setDateTimeError (Timestamp dateTime, Exception e, int error) {
        this.dateTime = dateTime;
        dateTimeErrorMessage = e.toString();
        dateTimeError = error;
    } // method setDateTimeError


    /**
     * Set the 'airTempAve' class variable
     * @param  airTempAve (float)
     */
    public int setAirTempAve(float airTempAve) {
        try {
            if ( ((airTempAve == FLOATNULL) ||
                  (airTempAve == FLOATNULL2)) ||
                !((airTempAve < AIR_TEMP_AVE_MN) ||
                  (airTempAve > AIR_TEMP_AVE_MX)) ) {
                this.airTempAve = airTempAve;
                airTempAveError = ERROR_NORMAL;
            } else {
                throw airTempAveOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAirTempAveError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return airTempAveError;
    } // method setAirTempAve

    /**
     * Set the 'airTempAve' class variable
     * @param  airTempAve (Integer)
     */
    public int setAirTempAve(Integer airTempAve) {
        try {
            setAirTempAve(airTempAve.floatValue());
        } catch (Exception e) {
            setAirTempAveError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return airTempAveError;
    } // method setAirTempAve

    /**
     * Set the 'airTempAve' class variable
     * @param  airTempAve (Float)
     */
    public int setAirTempAve(Float airTempAve) {
        try {
            setAirTempAve(airTempAve.floatValue());
        } catch (Exception e) {
            setAirTempAveError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return airTempAveError;
    } // method setAirTempAve

    /**
     * Set the 'airTempAve' class variable
     * @param  airTempAve (String)
     */
    public int setAirTempAve(String airTempAve) {
        try {
            setAirTempAve(new Float(airTempAve).floatValue());
        } catch (Exception e) {
            setAirTempAveError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return airTempAveError;
    } // method setAirTempAve

    /**
     * Called when an exception has occured
     * @param  airTempAve (String)
     */
    private void setAirTempAveError (float airTempAve, Exception e, int error) {
        this.airTempAve = airTempAve;
        airTempAveErrorMessage = e.toString();
        airTempAveError = error;
    } // method setAirTempAveError


    /**
     * Set the 'airTempMin' class variable
     * @param  airTempMin (float)
     */
    public int setAirTempMin(float airTempMin) {
        try {
            if ( ((airTempMin == FLOATNULL) ||
                  (airTempMin == FLOATNULL2)) ||
                !((airTempMin < AIR_TEMP_MIN_MN) ||
                  (airTempMin > AIR_TEMP_MIN_MX)) ) {
                this.airTempMin = airTempMin;
                airTempMinError = ERROR_NORMAL;
            } else {
                throw airTempMinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAirTempMinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return airTempMinError;
    } // method setAirTempMin

    /**
     * Set the 'airTempMin' class variable
     * @param  airTempMin (Integer)
     */
    public int setAirTempMin(Integer airTempMin) {
        try {
            setAirTempMin(airTempMin.floatValue());
        } catch (Exception e) {
            setAirTempMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return airTempMinError;
    } // method setAirTempMin

    /**
     * Set the 'airTempMin' class variable
     * @param  airTempMin (Float)
     */
    public int setAirTempMin(Float airTempMin) {
        try {
            setAirTempMin(airTempMin.floatValue());
        } catch (Exception e) {
            setAirTempMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return airTempMinError;
    } // method setAirTempMin

    /**
     * Set the 'airTempMin' class variable
     * @param  airTempMin (String)
     */
    public int setAirTempMin(String airTempMin) {
        try {
            setAirTempMin(new Float(airTempMin).floatValue());
        } catch (Exception e) {
            setAirTempMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return airTempMinError;
    } // method setAirTempMin

    /**
     * Called when an exception has occured
     * @param  airTempMin (String)
     */
    private void setAirTempMinError (float airTempMin, Exception e, int error) {
        this.airTempMin = airTempMin;
        airTempMinErrorMessage = e.toString();
        airTempMinError = error;
    } // method setAirTempMinError


    /**
     * Set the 'airTempMinTime' class variable
     * @param  airTempMinTime (Timestamp)
     */
    public int setAirTempMinTime(Timestamp airTempMinTime) {
        try {
            if (airTempMinTime == null) { this.airTempMinTime = DATENULL; }
            if ( (airTempMinTime.equals(DATENULL) ||
                  airTempMinTime.equals(DATENULL2)) ||
                !(airTempMinTime.before(AIR_TEMP_MIN_TIME_MN) ||
                  airTempMinTime.after(AIR_TEMP_MIN_TIME_MX)) ) {
                this.airTempMinTime = airTempMinTime;
                airTempMinTimeError = ERROR_NORMAL;
            } else {
                throw airTempMinTimeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAirTempMinTimeError(DATENULL, e, ERROR_LOCAL);
        } // try
        return airTempMinTimeError;
    } // method setAirTempMinTime

    /**
     * Set the 'airTempMinTime' class variable
     * @param  airTempMinTime (java.util.Date)
     */
    public int setAirTempMinTime(java.util.Date airTempMinTime) {
        try {
            setAirTempMinTime(new Timestamp(airTempMinTime.getTime()));
        } catch (Exception e) {
            setAirTempMinTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return airTempMinTimeError;
    } // method setAirTempMinTime

    /**
     * Set the 'airTempMinTime' class variable
     * @param  airTempMinTime (String)
     */
    public int setAirTempMinTime(String airTempMinTime) {
        try {
            int len = airTempMinTime.length();
            switch (len) {
            // date &/or times
                case 4: airTempMinTime += "-01";
                case 7: airTempMinTime += "-01";
                case 10: airTempMinTime += " 00";
                case 13: airTempMinTime += ":00";
                case 16: airTempMinTime += ":00"; break;
                // times only
                case 5: airTempMinTime = "1800-01-01 " + airTempMinTime + ":00"; break;
                case 8: airTempMinTime = "1800-01-01 " + airTempMinTime; break;
            } // switch
            if (dbg) System.out.println ("airTempMinTime = " + airTempMinTime);
            setAirTempMinTime(Timestamp.valueOf(airTempMinTime));
        } catch (Exception e) {
            setAirTempMinTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return airTempMinTimeError;
    } // method setAirTempMinTime

    /**
     * Called when an exception has occured
     * @param  airTempMinTime (String)
     */
    private void setAirTempMinTimeError (Timestamp airTempMinTime, Exception e, int error) {
        this.airTempMinTime = airTempMinTime;
        airTempMinTimeErrorMessage = e.toString();
        airTempMinTimeError = error;
    } // method setAirTempMinTimeError


    /**
     * Set the 'airTempMax' class variable
     * @param  airTempMax (float)
     */
    public int setAirTempMax(float airTempMax) {
        try {
            if ( ((airTempMax == FLOATNULL) ||
                  (airTempMax == FLOATNULL2)) ||
                !((airTempMax < AIR_TEMP_MAX_MN) ||
                  (airTempMax > AIR_TEMP_MAX_MX)) ) {
                this.airTempMax = airTempMax;
                airTempMaxError = ERROR_NORMAL;
            } else {
                throw airTempMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAirTempMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return airTempMaxError;
    } // method setAirTempMax

    /**
     * Set the 'airTempMax' class variable
     * @param  airTempMax (Integer)
     */
    public int setAirTempMax(Integer airTempMax) {
        try {
            setAirTempMax(airTempMax.floatValue());
        } catch (Exception e) {
            setAirTempMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return airTempMaxError;
    } // method setAirTempMax

    /**
     * Set the 'airTempMax' class variable
     * @param  airTempMax (Float)
     */
    public int setAirTempMax(Float airTempMax) {
        try {
            setAirTempMax(airTempMax.floatValue());
        } catch (Exception e) {
            setAirTempMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return airTempMaxError;
    } // method setAirTempMax

    /**
     * Set the 'airTempMax' class variable
     * @param  airTempMax (String)
     */
    public int setAirTempMax(String airTempMax) {
        try {
            setAirTempMax(new Float(airTempMax).floatValue());
        } catch (Exception e) {
            setAirTempMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return airTempMaxError;
    } // method setAirTempMax

    /**
     * Called when an exception has occured
     * @param  airTempMax (String)
     */
    private void setAirTempMaxError (float airTempMax, Exception e, int error) {
        this.airTempMax = airTempMax;
        airTempMaxErrorMessage = e.toString();
        airTempMaxError = error;
    } // method setAirTempMaxError


    /**
     * Set the 'airTempMaxTime' class variable
     * @param  airTempMaxTime (Timestamp)
     */
    public int setAirTempMaxTime(Timestamp airTempMaxTime) {
        try {
            if (airTempMaxTime == null) { this.airTempMaxTime = DATENULL; }
            if ( (airTempMaxTime.equals(DATENULL) ||
                  airTempMaxTime.equals(DATENULL2)) ||
                !(airTempMaxTime.before(AIR_TEMP_MAX_TIME_MN) ||
                  airTempMaxTime.after(AIR_TEMP_MAX_TIME_MX)) ) {
                this.airTempMaxTime = airTempMaxTime;
                airTempMaxTimeError = ERROR_NORMAL;
            } else {
                throw airTempMaxTimeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAirTempMaxTimeError(DATENULL, e, ERROR_LOCAL);
        } // try
        return airTempMaxTimeError;
    } // method setAirTempMaxTime

    /**
     * Set the 'airTempMaxTime' class variable
     * @param  airTempMaxTime (java.util.Date)
     */
    public int setAirTempMaxTime(java.util.Date airTempMaxTime) {
        try {
            setAirTempMaxTime(new Timestamp(airTempMaxTime.getTime()));
        } catch (Exception e) {
            setAirTempMaxTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return airTempMaxTimeError;
    } // method setAirTempMaxTime

    /**
     * Set the 'airTempMaxTime' class variable
     * @param  airTempMaxTime (String)
     */
    public int setAirTempMaxTime(String airTempMaxTime) {
        try {
            int len = airTempMaxTime.length();
            switch (len) {
            // date &/or times
                case 4: airTempMaxTime += "-01";
                case 7: airTempMaxTime += "-01";
                case 10: airTempMaxTime += " 00";
                case 13: airTempMaxTime += ":00";
                case 16: airTempMaxTime += ":00"; break;
                // times only
                case 5: airTempMaxTime = "1800-01-01 " + airTempMaxTime + ":00"; break;
                case 8: airTempMaxTime = "1800-01-01 " + airTempMaxTime; break;
            } // switch
            if (dbg) System.out.println ("airTempMaxTime = " + airTempMaxTime);
            setAirTempMaxTime(Timestamp.valueOf(airTempMaxTime));
        } catch (Exception e) {
            setAirTempMaxTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return airTempMaxTimeError;
    } // method setAirTempMaxTime

    /**
     * Called when an exception has occured
     * @param  airTempMaxTime (String)
     */
    private void setAirTempMaxTimeError (Timestamp airTempMaxTime, Exception e, int error) {
        this.airTempMaxTime = airTempMaxTime;
        airTempMaxTimeErrorMessage = e.toString();
        airTempMaxTimeError = error;
    } // method setAirTempMaxTimeError


    /**
     * Set the 'barometricPressure' class variable
     * @param  barometricPressure (float)
     */
    public int setBarometricPressure(float barometricPressure) {
        try {
            if ( ((barometricPressure == FLOATNULL) ||
                  (barometricPressure == FLOATNULL2)) ||
                !((barometricPressure < BAROMETRIC_PRESSURE_MN) ||
                  (barometricPressure > BAROMETRIC_PRESSURE_MX)) ) {
                this.barometricPressure = barometricPressure;
                barometricPressureError = ERROR_NORMAL;
            } else {
                throw barometricPressureOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setBarometricPressureError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return barometricPressureError;
    } // method setBarometricPressure

    /**
     * Set the 'barometricPressure' class variable
     * @param  barometricPressure (Integer)
     */
    public int setBarometricPressure(Integer barometricPressure) {
        try {
            setBarometricPressure(barometricPressure.floatValue());
        } catch (Exception e) {
            setBarometricPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return barometricPressureError;
    } // method setBarometricPressure

    /**
     * Set the 'barometricPressure' class variable
     * @param  barometricPressure (Float)
     */
    public int setBarometricPressure(Float barometricPressure) {
        try {
            setBarometricPressure(barometricPressure.floatValue());
        } catch (Exception e) {
            setBarometricPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return barometricPressureError;
    } // method setBarometricPressure

    /**
     * Set the 'barometricPressure' class variable
     * @param  barometricPressure (String)
     */
    public int setBarometricPressure(String barometricPressure) {
        try {
            setBarometricPressure(new Float(barometricPressure).floatValue());
        } catch (Exception e) {
            setBarometricPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return barometricPressureError;
    } // method setBarometricPressure

    /**
     * Called when an exception has occured
     * @param  barometricPressure (String)
     */
    private void setBarometricPressureError (float barometricPressure, Exception e, int error) {
        this.barometricPressure = barometricPressure;
        barometricPressureErrorMessage = e.toString();
        barometricPressureError = error;
    } // method setBarometricPressureError


    /**
     * Set the 'fog' class variable
     * @param  fog (float)
     */
    public int setFog(float fog) {
        try {
            if ( ((fog == FLOATNULL) ||
                  (fog == FLOATNULL2)) ||
                !((fog < FOG_MN) ||
                  (fog > FOG_MX)) ) {
                this.fog = fog;
                fogError = ERROR_NORMAL;
            } else {
                throw fogOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFogError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return fogError;
    } // method setFog

    /**
     * Set the 'fog' class variable
     * @param  fog (Integer)
     */
    public int setFog(Integer fog) {
        try {
            setFog(fog.floatValue());
        } catch (Exception e) {
            setFogError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fogError;
    } // method setFog

    /**
     * Set the 'fog' class variable
     * @param  fog (Float)
     */
    public int setFog(Float fog) {
        try {
            setFog(fog.floatValue());
        } catch (Exception e) {
            setFogError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fogError;
    } // method setFog

    /**
     * Set the 'fog' class variable
     * @param  fog (String)
     */
    public int setFog(String fog) {
        try {
            setFog(new Float(fog).floatValue());
        } catch (Exception e) {
            setFogError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fogError;
    } // method setFog

    /**
     * Called when an exception has occured
     * @param  fog (String)
     */
    private void setFogError (float fog, Exception e, int error) {
        this.fog = fog;
        fogErrorMessage = e.toString();
        fogError = error;
    } // method setFogError


    /**
     * Set the 'rainfall' class variable
     * @param  rainfall (float)
     */
    public int setRainfall(float rainfall) {
        try {
            if ( ((rainfall == FLOATNULL) ||
                  (rainfall == FLOATNULL2)) ||
                !((rainfall < RAINFALL_MN) ||
                  (rainfall > RAINFALL_MX)) ) {
                this.rainfall = rainfall;
                rainfallError = ERROR_NORMAL;
            } else {
                throw rainfallOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setRainfallError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return rainfallError;
    } // method setRainfall

    /**
     * Set the 'rainfall' class variable
     * @param  rainfall (Integer)
     */
    public int setRainfall(Integer rainfall) {
        try {
            setRainfall(rainfall.floatValue());
        } catch (Exception e) {
            setRainfallError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return rainfallError;
    } // method setRainfall

    /**
     * Set the 'rainfall' class variable
     * @param  rainfall (Float)
     */
    public int setRainfall(Float rainfall) {
        try {
            setRainfall(rainfall.floatValue());
        } catch (Exception e) {
            setRainfallError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return rainfallError;
    } // method setRainfall

    /**
     * Set the 'rainfall' class variable
     * @param  rainfall (String)
     */
    public int setRainfall(String rainfall) {
        try {
            setRainfall(new Float(rainfall).floatValue());
        } catch (Exception e) {
            setRainfallError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return rainfallError;
    } // method setRainfall

    /**
     * Called when an exception has occured
     * @param  rainfall (String)
     */
    private void setRainfallError (float rainfall, Exception e, int error) {
        this.rainfall = rainfall;
        rainfallErrorMessage = e.toString();
        rainfallError = error;
    } // method setRainfallError


    /**
     * Set the 'relativeHumidity' class variable
     * @param  relativeHumidity (float)
     */
    public int setRelativeHumidity(float relativeHumidity) {
        try {
            if ( ((relativeHumidity == FLOATNULL) ||
                  (relativeHumidity == FLOATNULL2)) ||
                !((relativeHumidity < RELATIVE_HUMIDITY_MN) ||
                  (relativeHumidity > RELATIVE_HUMIDITY_MX)) ) {
                this.relativeHumidity = relativeHumidity;
                relativeHumidityError = ERROR_NORMAL;
            } else {
                throw relativeHumidityOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setRelativeHumidityError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return relativeHumidityError;
    } // method setRelativeHumidity

    /**
     * Set the 'relativeHumidity' class variable
     * @param  relativeHumidity (Integer)
     */
    public int setRelativeHumidity(Integer relativeHumidity) {
        try {
            setRelativeHumidity(relativeHumidity.floatValue());
        } catch (Exception e) {
            setRelativeHumidityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return relativeHumidityError;
    } // method setRelativeHumidity

    /**
     * Set the 'relativeHumidity' class variable
     * @param  relativeHumidity (Float)
     */
    public int setRelativeHumidity(Float relativeHumidity) {
        try {
            setRelativeHumidity(relativeHumidity.floatValue());
        } catch (Exception e) {
            setRelativeHumidityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return relativeHumidityError;
    } // method setRelativeHumidity

    /**
     * Set the 'relativeHumidity' class variable
     * @param  relativeHumidity (String)
     */
    public int setRelativeHumidity(String relativeHumidity) {
        try {
            setRelativeHumidity(new Float(relativeHumidity).floatValue());
        } catch (Exception e) {
            setRelativeHumidityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return relativeHumidityError;
    } // method setRelativeHumidity

    /**
     * Called when an exception has occured
     * @param  relativeHumidity (String)
     */
    private void setRelativeHumidityError (float relativeHumidity, Exception e, int error) {
        this.relativeHumidity = relativeHumidity;
        relativeHumidityErrorMessage = e.toString();
        relativeHumidityError = error;
    } // method setRelativeHumidityError


    /**
     * Set the 'solarRadiation' class variable
     * @param  solarRadiation (float)
     */
    public int setSolarRadiation(float solarRadiation) {
        try {
            if ( ((solarRadiation == FLOATNULL) ||
                  (solarRadiation == FLOATNULL2)) ||
                !((solarRadiation < SOLAR_RADIATION_MN) ||
                  (solarRadiation > SOLAR_RADIATION_MX)) ) {
                this.solarRadiation = solarRadiation;
                solarRadiationError = ERROR_NORMAL;
            } else {
                throw solarRadiationOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSolarRadiationError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return solarRadiationError;
    } // method setSolarRadiation

    /**
     * Set the 'solarRadiation' class variable
     * @param  solarRadiation (Integer)
     */
    public int setSolarRadiation(Integer solarRadiation) {
        try {
            setSolarRadiation(solarRadiation.floatValue());
        } catch (Exception e) {
            setSolarRadiationError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return solarRadiationError;
    } // method setSolarRadiation

    /**
     * Set the 'solarRadiation' class variable
     * @param  solarRadiation (Float)
     */
    public int setSolarRadiation(Float solarRadiation) {
        try {
            setSolarRadiation(solarRadiation.floatValue());
        } catch (Exception e) {
            setSolarRadiationError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return solarRadiationError;
    } // method setSolarRadiation

    /**
     * Set the 'solarRadiation' class variable
     * @param  solarRadiation (String)
     */
    public int setSolarRadiation(String solarRadiation) {
        try {
            setSolarRadiation(new Float(solarRadiation).floatValue());
        } catch (Exception e) {
            setSolarRadiationError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return solarRadiationError;
    } // method setSolarRadiation

    /**
     * Called when an exception has occured
     * @param  solarRadiation (String)
     */
    private void setSolarRadiationError (float solarRadiation, Exception e, int error) {
        this.solarRadiation = solarRadiation;
        solarRadiationErrorMessage = e.toString();
        solarRadiationError = error;
    } // method setSolarRadiationError


    /**
     * Set the 'solarRadiationMax' class variable
     * @param  solarRadiationMax (float)
     */
    public int setSolarRadiationMax(float solarRadiationMax) {
        try {
            if ( ((solarRadiationMax == FLOATNULL) ||
                  (solarRadiationMax == FLOATNULL2)) ||
                !((solarRadiationMax < SOLAR_RADIATION_MAX_MN) ||
                  (solarRadiationMax > SOLAR_RADIATION_MAX_MX)) ) {
                this.solarRadiationMax = solarRadiationMax;
                solarRadiationMaxError = ERROR_NORMAL;
            } else {
                throw solarRadiationMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSolarRadiationMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return solarRadiationMaxError;
    } // method setSolarRadiationMax

    /**
     * Set the 'solarRadiationMax' class variable
     * @param  solarRadiationMax (Integer)
     */
    public int setSolarRadiationMax(Integer solarRadiationMax) {
        try {
            setSolarRadiationMax(solarRadiationMax.floatValue());
        } catch (Exception e) {
            setSolarRadiationMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return solarRadiationMaxError;
    } // method setSolarRadiationMax

    /**
     * Set the 'solarRadiationMax' class variable
     * @param  solarRadiationMax (Float)
     */
    public int setSolarRadiationMax(Float solarRadiationMax) {
        try {
            setSolarRadiationMax(solarRadiationMax.floatValue());
        } catch (Exception e) {
            setSolarRadiationMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return solarRadiationMaxError;
    } // method setSolarRadiationMax

    /**
     * Set the 'solarRadiationMax' class variable
     * @param  solarRadiationMax (String)
     */
    public int setSolarRadiationMax(String solarRadiationMax) {
        try {
            setSolarRadiationMax(new Float(solarRadiationMax).floatValue());
        } catch (Exception e) {
            setSolarRadiationMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return solarRadiationMaxError;
    } // method setSolarRadiationMax

    /**
     * Called when an exception has occured
     * @param  solarRadiationMax (String)
     */
    private void setSolarRadiationMaxError (float solarRadiationMax, Exception e, int error) {
        this.solarRadiationMax = solarRadiationMax;
        solarRadiationMaxErrorMessage = e.toString();
        solarRadiationMaxError = error;
    } // method setSolarRadiationMaxError


    /**
     * Set the 'windDir' class variable
     * @param  windDir (float)
     */
    public int setWindDir(float windDir) {
        try {
            if ( ((windDir == FLOATNULL) ||
                  (windDir == FLOATNULL2)) ||
                !((windDir < WIND_DIR_MN) ||
                  (windDir > WIND_DIR_MX)) ) {
                this.windDir = windDir;
                windDirError = ERROR_NORMAL;
            } else {
                throw windDirOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindDirError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return windDirError;
    } // method setWindDir

    /**
     * Set the 'windDir' class variable
     * @param  windDir (Integer)
     */
    public int setWindDir(Integer windDir) {
        try {
            setWindDir(windDir.floatValue());
        } catch (Exception e) {
            setWindDirError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windDirError;
    } // method setWindDir

    /**
     * Set the 'windDir' class variable
     * @param  windDir (Float)
     */
    public int setWindDir(Float windDir) {
        try {
            setWindDir(windDir.floatValue());
        } catch (Exception e) {
            setWindDirError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windDirError;
    } // method setWindDir

    /**
     * Set the 'windDir' class variable
     * @param  windDir (String)
     */
    public int setWindDir(String windDir) {
        try {
            setWindDir(new Float(windDir).floatValue());
        } catch (Exception e) {
            setWindDirError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windDirError;
    } // method setWindDir

    /**
     * Called when an exception has occured
     * @param  windDir (String)
     */
    private void setWindDirError (float windDir, Exception e, int error) {
        this.windDir = windDir;
        windDirErrorMessage = e.toString();
        windDirError = error;
    } // method setWindDirError


    /**
     * Set the 'windSpeedAve' class variable
     * @param  windSpeedAve (float)
     */
    public int setWindSpeedAve(float windSpeedAve) {
        try {
            if ( ((windSpeedAve == FLOATNULL) ||
                  (windSpeedAve == FLOATNULL2)) ||
                !((windSpeedAve < WIND_SPEED_AVE_MN) ||
                  (windSpeedAve > WIND_SPEED_AVE_MX)) ) {
                this.windSpeedAve = windSpeedAve;
                windSpeedAveError = ERROR_NORMAL;
            } else {
                throw windSpeedAveOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindSpeedAveError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return windSpeedAveError;
    } // method setWindSpeedAve

    /**
     * Set the 'windSpeedAve' class variable
     * @param  windSpeedAve (Integer)
     */
    public int setWindSpeedAve(Integer windSpeedAve) {
        try {
            setWindSpeedAve(windSpeedAve.floatValue());
        } catch (Exception e) {
            setWindSpeedAveError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedAveError;
    } // method setWindSpeedAve

    /**
     * Set the 'windSpeedAve' class variable
     * @param  windSpeedAve (Float)
     */
    public int setWindSpeedAve(Float windSpeedAve) {
        try {
            setWindSpeedAve(windSpeedAve.floatValue());
        } catch (Exception e) {
            setWindSpeedAveError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedAveError;
    } // method setWindSpeedAve

    /**
     * Set the 'windSpeedAve' class variable
     * @param  windSpeedAve (String)
     */
    public int setWindSpeedAve(String windSpeedAve) {
        try {
            setWindSpeedAve(new Float(windSpeedAve).floatValue());
        } catch (Exception e) {
            setWindSpeedAveError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedAveError;
    } // method setWindSpeedAve

    /**
     * Called when an exception has occured
     * @param  windSpeedAve (String)
     */
    private void setWindSpeedAveError (float windSpeedAve, Exception e, int error) {
        this.windSpeedAve = windSpeedAve;
        windSpeedAveErrorMessage = e.toString();
        windSpeedAveError = error;
    } // method setWindSpeedAveError


    /**
     * Set the 'windSpeedMin' class variable
     * @param  windSpeedMin (float)
     */
    public int setWindSpeedMin(float windSpeedMin) {
        try {
            if ( ((windSpeedMin == FLOATNULL) ||
                  (windSpeedMin == FLOATNULL2)) ||
                !((windSpeedMin < WIND_SPEED_MIN_MN) ||
                  (windSpeedMin > WIND_SPEED_MIN_MX)) ) {
                this.windSpeedMin = windSpeedMin;
                windSpeedMinError = ERROR_NORMAL;
            } else {
                throw windSpeedMinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindSpeedMinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return windSpeedMinError;
    } // method setWindSpeedMin

    /**
     * Set the 'windSpeedMin' class variable
     * @param  windSpeedMin (Integer)
     */
    public int setWindSpeedMin(Integer windSpeedMin) {
        try {
            setWindSpeedMin(windSpeedMin.floatValue());
        } catch (Exception e) {
            setWindSpeedMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMinError;
    } // method setWindSpeedMin

    /**
     * Set the 'windSpeedMin' class variable
     * @param  windSpeedMin (Float)
     */
    public int setWindSpeedMin(Float windSpeedMin) {
        try {
            setWindSpeedMin(windSpeedMin.floatValue());
        } catch (Exception e) {
            setWindSpeedMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMinError;
    } // method setWindSpeedMin

    /**
     * Set the 'windSpeedMin' class variable
     * @param  windSpeedMin (String)
     */
    public int setWindSpeedMin(String windSpeedMin) {
        try {
            setWindSpeedMin(new Float(windSpeedMin).floatValue());
        } catch (Exception e) {
            setWindSpeedMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMinError;
    } // method setWindSpeedMin

    /**
     * Called when an exception has occured
     * @param  windSpeedMin (String)
     */
    private void setWindSpeedMinError (float windSpeedMin, Exception e, int error) {
        this.windSpeedMin = windSpeedMin;
        windSpeedMinErrorMessage = e.toString();
        windSpeedMinError = error;
    } // method setWindSpeedMinError


    /**
     * Set the 'windSpeedMax' class variable
     * @param  windSpeedMax (float)
     */
    public int setWindSpeedMax(float windSpeedMax) {
        try {
            if ( ((windSpeedMax == FLOATNULL) ||
                  (windSpeedMax == FLOATNULL2)) ||
                !((windSpeedMax < WIND_SPEED_MAX_MN) ||
                  (windSpeedMax > WIND_SPEED_MAX_MX)) ) {
                this.windSpeedMax = windSpeedMax;
                windSpeedMaxError = ERROR_NORMAL;
            } else {
                throw windSpeedMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindSpeedMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return windSpeedMaxError;
    } // method setWindSpeedMax

    /**
     * Set the 'windSpeedMax' class variable
     * @param  windSpeedMax (Integer)
     */
    public int setWindSpeedMax(Integer windSpeedMax) {
        try {
            setWindSpeedMax(windSpeedMax.floatValue());
        } catch (Exception e) {
            setWindSpeedMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxError;
    } // method setWindSpeedMax

    /**
     * Set the 'windSpeedMax' class variable
     * @param  windSpeedMax (Float)
     */
    public int setWindSpeedMax(Float windSpeedMax) {
        try {
            setWindSpeedMax(windSpeedMax.floatValue());
        } catch (Exception e) {
            setWindSpeedMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxError;
    } // method setWindSpeedMax

    /**
     * Set the 'windSpeedMax' class variable
     * @param  windSpeedMax (String)
     */
    public int setWindSpeedMax(String windSpeedMax) {
        try {
            setWindSpeedMax(new Float(windSpeedMax).floatValue());
        } catch (Exception e) {
            setWindSpeedMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxError;
    } // method setWindSpeedMax

    /**
     * Called when an exception has occured
     * @param  windSpeedMax (String)
     */
    private void setWindSpeedMaxError (float windSpeedMax, Exception e, int error) {
        this.windSpeedMax = windSpeedMax;
        windSpeedMaxErrorMessage = e.toString();
        windSpeedMaxError = error;
    } // method setWindSpeedMaxError


    /**
     * Set the 'windSpeedMaxTime' class variable
     * @param  windSpeedMaxTime (Timestamp)
     */
    public int setWindSpeedMaxTime(Timestamp windSpeedMaxTime) {
        try {
            if (windSpeedMaxTime == null) { this.windSpeedMaxTime = DATENULL; }
            if ( (windSpeedMaxTime.equals(DATENULL) ||
                  windSpeedMaxTime.equals(DATENULL2)) ||
                !(windSpeedMaxTime.before(WIND_SPEED_MAX_TIME_MN) ||
                  windSpeedMaxTime.after(WIND_SPEED_MAX_TIME_MX)) ) {
                this.windSpeedMaxTime = windSpeedMaxTime;
                windSpeedMaxTimeError = ERROR_NORMAL;
            } else {
                throw windSpeedMaxTimeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindSpeedMaxTimeError(DATENULL, e, ERROR_LOCAL);
        } // try
        return windSpeedMaxTimeError;
    } // method setWindSpeedMaxTime

    /**
     * Set the 'windSpeedMaxTime' class variable
     * @param  windSpeedMaxTime (java.util.Date)
     */
    public int setWindSpeedMaxTime(java.util.Date windSpeedMaxTime) {
        try {
            setWindSpeedMaxTime(new Timestamp(windSpeedMaxTime.getTime()));
        } catch (Exception e) {
            setWindSpeedMaxTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxTimeError;
    } // method setWindSpeedMaxTime

    /**
     * Set the 'windSpeedMaxTime' class variable
     * @param  windSpeedMaxTime (String)
     */
    public int setWindSpeedMaxTime(String windSpeedMaxTime) {
        try {
            int len = windSpeedMaxTime.length();
            switch (len) {
            // date &/or times
                case 4: windSpeedMaxTime += "-01";
                case 7: windSpeedMaxTime += "-01";
                case 10: windSpeedMaxTime += " 00";
                case 13: windSpeedMaxTime += ":00";
                case 16: windSpeedMaxTime += ":00"; break;
                // times only
                case 5: windSpeedMaxTime = "1800-01-01 " + windSpeedMaxTime + ":00"; break;
                case 8: windSpeedMaxTime = "1800-01-01 " + windSpeedMaxTime; break;
            } // switch
            if (dbg) System.out.println ("windSpeedMaxTime = " + windSpeedMaxTime);
            setWindSpeedMaxTime(Timestamp.valueOf(windSpeedMaxTime));
        } catch (Exception e) {
            setWindSpeedMaxTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxTimeError;
    } // method setWindSpeedMaxTime

    /**
     * Called when an exception has occured
     * @param  windSpeedMaxTime (String)
     */
    private void setWindSpeedMaxTimeError (Timestamp windSpeedMaxTime, Exception e, int error) {
        this.windSpeedMaxTime = windSpeedMaxTime;
        windSpeedMaxTimeErrorMessage = e.toString();
        windSpeedMaxTimeError = error;
    } // method setWindSpeedMaxTimeError


    /**
     * Set the 'windSpeedMaxLength' class variable
     * @param  windSpeedMaxLength (int)
     */
    public int setWindSpeedMaxLength(int windSpeedMaxLength) {
        try {
            if ( ((windSpeedMaxLength == INTNULL) ||
                  (windSpeedMaxLength == INTNULL2)) ||
                !((windSpeedMaxLength < WIND_SPEED_MAX_LENGTH_MN) ||
                  (windSpeedMaxLength > WIND_SPEED_MAX_LENGTH_MX)) ) {
                this.windSpeedMaxLength = windSpeedMaxLength;
                windSpeedMaxLengthError = ERROR_NORMAL;
            } else {
                throw windSpeedMaxLengthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindSpeedMaxLengthError(INTNULL, e, ERROR_LOCAL);
        } // try
        return windSpeedMaxLengthError;
    } // method setWindSpeedMaxLength

    /**
     * Set the 'windSpeedMaxLength' class variable
     * @param  windSpeedMaxLength (Integer)
     */
    public int setWindSpeedMaxLength(Integer windSpeedMaxLength) {
        try {
            setWindSpeedMaxLength(windSpeedMaxLength.intValue());
        } catch (Exception e) {
            setWindSpeedMaxLengthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxLengthError;
    } // method setWindSpeedMaxLength

    /**
     * Set the 'windSpeedMaxLength' class variable
     * @param  windSpeedMaxLength (Float)
     */
    public int setWindSpeedMaxLength(Float windSpeedMaxLength) {
        try {
            if (windSpeedMaxLength.floatValue() == FLOATNULL) {
                setWindSpeedMaxLength(INTNULL);
            } else {
                setWindSpeedMaxLength(windSpeedMaxLength.intValue());
            } // if (windSpeedMaxLength.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWindSpeedMaxLengthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxLengthError;
    } // method setWindSpeedMaxLength

    /**
     * Set the 'windSpeedMaxLength' class variable
     * @param  windSpeedMaxLength (String)
     */
    public int setWindSpeedMaxLength(String windSpeedMaxLength) {
        try {
            setWindSpeedMaxLength(new Integer(windSpeedMaxLength).intValue());
        } catch (Exception e) {
            setWindSpeedMaxLengthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxLengthError;
    } // method setWindSpeedMaxLength

    /**
     * Called when an exception has occured
     * @param  windSpeedMaxLength (String)
     */
    private void setWindSpeedMaxLengthError (int windSpeedMaxLength, Exception e, int error) {
        this.windSpeedMaxLength = windSpeedMaxLength;
        windSpeedMaxLengthErrorMessage = e.toString();
        windSpeedMaxLengthError = error;
    } // method setWindSpeedMaxLengthError


    /**
     * Set the 'windSpeedMaxDir' class variable
     * @param  windSpeedMaxDir (float)
     */
    public int setWindSpeedMaxDir(float windSpeedMaxDir) {
        try {
            if ( ((windSpeedMaxDir == FLOATNULL) ||
                  (windSpeedMaxDir == FLOATNULL2)) ||
                !((windSpeedMaxDir < WIND_SPEED_MAX_DIR_MN) ||
                  (windSpeedMaxDir > WIND_SPEED_MAX_DIR_MX)) ) {
                this.windSpeedMaxDir = windSpeedMaxDir;
                windSpeedMaxDirError = ERROR_NORMAL;
            } else {
                throw windSpeedMaxDirOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindSpeedMaxDirError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return windSpeedMaxDirError;
    } // method setWindSpeedMaxDir

    /**
     * Set the 'windSpeedMaxDir' class variable
     * @param  windSpeedMaxDir (Integer)
     */
    public int setWindSpeedMaxDir(Integer windSpeedMaxDir) {
        try {
            setWindSpeedMaxDir(windSpeedMaxDir.floatValue());
        } catch (Exception e) {
            setWindSpeedMaxDirError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxDirError;
    } // method setWindSpeedMaxDir

    /**
     * Set the 'windSpeedMaxDir' class variable
     * @param  windSpeedMaxDir (Float)
     */
    public int setWindSpeedMaxDir(Float windSpeedMaxDir) {
        try {
            setWindSpeedMaxDir(windSpeedMaxDir.floatValue());
        } catch (Exception e) {
            setWindSpeedMaxDirError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxDirError;
    } // method setWindSpeedMaxDir

    /**
     * Set the 'windSpeedMaxDir' class variable
     * @param  windSpeedMaxDir (String)
     */
    public int setWindSpeedMaxDir(String windSpeedMaxDir) {
        try {
            setWindSpeedMaxDir(new Float(windSpeedMaxDir).floatValue());
        } catch (Exception e) {
            setWindSpeedMaxDirError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedMaxDirError;
    } // method setWindSpeedMaxDir

    /**
     * Called when an exception has occured
     * @param  windSpeedMaxDir (String)
     */
    private void setWindSpeedMaxDirError (float windSpeedMaxDir, Exception e, int error) {
        this.windSpeedMaxDir = windSpeedMaxDir;
        windSpeedMaxDirErrorMessage = e.toString();
        windSpeedMaxDirError = error;
    } // method setWindSpeedMaxDirError


    /**
     * Set the 'windSpeedStd' class variable
     * @param  windSpeedStd (float)
     */
    public int setWindSpeedStd(float windSpeedStd) {
        try {
            if ( ((windSpeedStd == FLOATNULL) ||
                  (windSpeedStd == FLOATNULL2)) ||
                !((windSpeedStd < WIND_SPEED_STD_MN) ||
                  (windSpeedStd > WIND_SPEED_STD_MX)) ) {
                this.windSpeedStd = windSpeedStd;
                windSpeedStdError = ERROR_NORMAL;
            } else {
                throw windSpeedStdOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindSpeedStdError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return windSpeedStdError;
    } // method setWindSpeedStd

    /**
     * Set the 'windSpeedStd' class variable
     * @param  windSpeedStd (Integer)
     */
    public int setWindSpeedStd(Integer windSpeedStd) {
        try {
            setWindSpeedStd(windSpeedStd.floatValue());
        } catch (Exception e) {
            setWindSpeedStdError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedStdError;
    } // method setWindSpeedStd

    /**
     * Set the 'windSpeedStd' class variable
     * @param  windSpeedStd (Float)
     */
    public int setWindSpeedStd(Float windSpeedStd) {
        try {
            setWindSpeedStd(windSpeedStd.floatValue());
        } catch (Exception e) {
            setWindSpeedStdError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedStdError;
    } // method setWindSpeedStd

    /**
     * Set the 'windSpeedStd' class variable
     * @param  windSpeedStd (String)
     */
    public int setWindSpeedStd(String windSpeedStd) {
        try {
            setWindSpeedStd(new Float(windSpeedStd).floatValue());
        } catch (Exception e) {
            setWindSpeedStdError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedStdError;
    } // method setWindSpeedStd

    /**
     * Called when an exception has occured
     * @param  windSpeedStd (String)
     */
    private void setWindSpeedStdError (float windSpeedStd, Exception e, int error) {
        this.windSpeedStd = windSpeedStd;
        windSpeedStdErrorMessage = e.toString();
        windSpeedStdError = error;
    } // method setWindSpeedStdError


    //=================//
    // All the getters //
    //=================//

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
     * Return the 'periodCode' class variable
     * @return periodCode (int)
     */
    public int getPeriodCode() {
        return periodCode;
    } // method getPeriodCode

    /**
     * Return the 'periodCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPeriodCode methods
     * @return periodCode (String)
     */
    public String getPeriodCode(String s) {
        return ((periodCode != INTNULL) ? new Integer(periodCode).toString() : "");
    } // method getPeriodCode


    /**
     * Return the 'dateTime' class variable
     * @return dateTime (Timestamp)
     */
    public Timestamp getDateTime() {
        return dateTime;
    } // method getDateTime

    /**
     * Return the 'dateTime' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateTime methods
     * @return dateTime (String)
     */
    public String getDateTime(String s) {
        if (dateTime.equals(DATENULL)) {
            return ("");
        } else {
            String dateTimeStr = dateTime.toString();
            return dateTimeStr.substring(0,dateTimeStr.indexOf('.'));
        } // if
    } // method getDateTime


    /**
     * Return the 'airTempAve' class variable
     * @return airTempAve (float)
     */
    public float getAirTempAve() {
        return airTempAve;
    } // method getAirTempAve

    /**
     * Return the 'airTempAve' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAirTempAve methods
     * @return airTempAve (String)
     */
    public String getAirTempAve(String s) {
        return ((airTempAve != FLOATNULL) ? new Float(airTempAve).toString() : "");
    } // method getAirTempAve


    /**
     * Return the 'airTempMin' class variable
     * @return airTempMin (float)
     */
    public float getAirTempMin() {
        return airTempMin;
    } // method getAirTempMin

    /**
     * Return the 'airTempMin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAirTempMin methods
     * @return airTempMin (String)
     */
    public String getAirTempMin(String s) {
        return ((airTempMin != FLOATNULL) ? new Float(airTempMin).toString() : "");
    } // method getAirTempMin


    /**
     * Return the 'airTempMinTime' class variable
     * @return airTempMinTime (Timestamp)
     */
    public Timestamp getAirTempMinTime() {
        return airTempMinTime;
    } // method getAirTempMinTime

    /**
     * Return the 'airTempMinTime' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAirTempMinTime methods
     * @return airTempMinTime (String)
     */
    public String getAirTempMinTime(String s) {
        if (airTempMinTime.equals(DATENULL)) {
            return ("");
        } else {
            String airTempMinTimeStr = airTempMinTime.toString();
            return airTempMinTimeStr.substring(0,airTempMinTimeStr.indexOf('.'));
        } // if
    } // method getAirTempMinTime


    /**
     * Return the 'airTempMax' class variable
     * @return airTempMax (float)
     */
    public float getAirTempMax() {
        return airTempMax;
    } // method getAirTempMax

    /**
     * Return the 'airTempMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAirTempMax methods
     * @return airTempMax (String)
     */
    public String getAirTempMax(String s) {
        return ((airTempMax != FLOATNULL) ? new Float(airTempMax).toString() : "");
    } // method getAirTempMax


    /**
     * Return the 'airTempMaxTime' class variable
     * @return airTempMaxTime (Timestamp)
     */
    public Timestamp getAirTempMaxTime() {
        return airTempMaxTime;
    } // method getAirTempMaxTime

    /**
     * Return the 'airTempMaxTime' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAirTempMaxTime methods
     * @return airTempMaxTime (String)
     */
    public String getAirTempMaxTime(String s) {
        if (airTempMaxTime.equals(DATENULL)) {
            return ("");
        } else {
            String airTempMaxTimeStr = airTempMaxTime.toString();
            return airTempMaxTimeStr.substring(0,airTempMaxTimeStr.indexOf('.'));
        } // if
    } // method getAirTempMaxTime


    /**
     * Return the 'barometricPressure' class variable
     * @return barometricPressure (float)
     */
    public float getBarometricPressure() {
        return barometricPressure;
    } // method getBarometricPressure

    /**
     * Return the 'barometricPressure' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getBarometricPressure methods
     * @return barometricPressure (String)
     */
    public String getBarometricPressure(String s) {
        return ((barometricPressure != FLOATNULL) ? new Float(barometricPressure).toString() : "");
    } // method getBarometricPressure


    /**
     * Return the 'fog' class variable
     * @return fog (float)
     */
    public float getFog() {
        return fog;
    } // method getFog

    /**
     * Return the 'fog' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFog methods
     * @return fog (String)
     */
    public String getFog(String s) {
        return ((fog != FLOATNULL) ? new Float(fog).toString() : "");
    } // method getFog


    /**
     * Return the 'rainfall' class variable
     * @return rainfall (float)
     */
    public float getRainfall() {
        return rainfall;
    } // method getRainfall

    /**
     * Return the 'rainfall' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRainfall methods
     * @return rainfall (String)
     */
    public String getRainfall(String s) {
        return ((rainfall != FLOATNULL) ? new Float(rainfall).toString() : "");
    } // method getRainfall


    /**
     * Return the 'relativeHumidity' class variable
     * @return relativeHumidity (float)
     */
    public float getRelativeHumidity() {
        return relativeHumidity;
    } // method getRelativeHumidity

    /**
     * Return the 'relativeHumidity' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRelativeHumidity methods
     * @return relativeHumidity (String)
     */
    public String getRelativeHumidity(String s) {
        return ((relativeHumidity != FLOATNULL) ? new Float(relativeHumidity).toString() : "");
    } // method getRelativeHumidity


    /**
     * Return the 'solarRadiation' class variable
     * @return solarRadiation (float)
     */
    public float getSolarRadiation() {
        return solarRadiation;
    } // method getSolarRadiation

    /**
     * Return the 'solarRadiation' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSolarRadiation methods
     * @return solarRadiation (String)
     */
    public String getSolarRadiation(String s) {
        return ((solarRadiation != FLOATNULL) ? new Float(solarRadiation).toString() : "");
    } // method getSolarRadiation


    /**
     * Return the 'solarRadiationMax' class variable
     * @return solarRadiationMax (float)
     */
    public float getSolarRadiationMax() {
        return solarRadiationMax;
    } // method getSolarRadiationMax

    /**
     * Return the 'solarRadiationMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSolarRadiationMax methods
     * @return solarRadiationMax (String)
     */
    public String getSolarRadiationMax(String s) {
        return ((solarRadiationMax != FLOATNULL) ? new Float(solarRadiationMax).toString() : "");
    } // method getSolarRadiationMax


    /**
     * Return the 'windDir' class variable
     * @return windDir (float)
     */
    public float getWindDir() {
        return windDir;
    } // method getWindDir

    /**
     * Return the 'windDir' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindDir methods
     * @return windDir (String)
     */
    public String getWindDir(String s) {
        return ((windDir != FLOATNULL) ? new Float(windDir).toString() : "");
    } // method getWindDir


    /**
     * Return the 'windSpeedAve' class variable
     * @return windSpeedAve (float)
     */
    public float getWindSpeedAve() {
        return windSpeedAve;
    } // method getWindSpeedAve

    /**
     * Return the 'windSpeedAve' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSpeedAve methods
     * @return windSpeedAve (String)
     */
    public String getWindSpeedAve(String s) {
        return ((windSpeedAve != FLOATNULL) ? new Float(windSpeedAve).toString() : "");
    } // method getWindSpeedAve


    /**
     * Return the 'windSpeedMin' class variable
     * @return windSpeedMin (float)
     */
    public float getWindSpeedMin() {
        return windSpeedMin;
    } // method getWindSpeedMin

    /**
     * Return the 'windSpeedMin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSpeedMin methods
     * @return windSpeedMin (String)
     */
    public String getWindSpeedMin(String s) {
        return ((windSpeedMin != FLOATNULL) ? new Float(windSpeedMin).toString() : "");
    } // method getWindSpeedMin


    /**
     * Return the 'windSpeedMax' class variable
     * @return windSpeedMax (float)
     */
    public float getWindSpeedMax() {
        return windSpeedMax;
    } // method getWindSpeedMax

    /**
     * Return the 'windSpeedMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSpeedMax methods
     * @return windSpeedMax (String)
     */
    public String getWindSpeedMax(String s) {
        return ((windSpeedMax != FLOATNULL) ? new Float(windSpeedMax).toString() : "");
    } // method getWindSpeedMax


    /**
     * Return the 'windSpeedMaxTime' class variable
     * @return windSpeedMaxTime (Timestamp)
     */
    public Timestamp getWindSpeedMaxTime() {
        return windSpeedMaxTime;
    } // method getWindSpeedMaxTime

    /**
     * Return the 'windSpeedMaxTime' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSpeedMaxTime methods
     * @return windSpeedMaxTime (String)
     */
    public String getWindSpeedMaxTime(String s) {
        if (windSpeedMaxTime.equals(DATENULL)) {
            return ("");
        } else {
            String windSpeedMaxTimeStr = windSpeedMaxTime.toString();
            return windSpeedMaxTimeStr.substring(0,windSpeedMaxTimeStr.indexOf('.'));
        } // if
    } // method getWindSpeedMaxTime


    /**
     * Return the 'windSpeedMaxLength' class variable
     * @return windSpeedMaxLength (int)
     */
    public int getWindSpeedMaxLength() {
        return windSpeedMaxLength;
    } // method getWindSpeedMaxLength

    /**
     * Return the 'windSpeedMaxLength' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSpeedMaxLength methods
     * @return windSpeedMaxLength (String)
     */
    public String getWindSpeedMaxLength(String s) {
        return ((windSpeedMaxLength != INTNULL) ? new Integer(windSpeedMaxLength).toString() : "");
    } // method getWindSpeedMaxLength


    /**
     * Return the 'windSpeedMaxDir' class variable
     * @return windSpeedMaxDir (float)
     */
    public float getWindSpeedMaxDir() {
        return windSpeedMaxDir;
    } // method getWindSpeedMaxDir

    /**
     * Return the 'windSpeedMaxDir' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSpeedMaxDir methods
     * @return windSpeedMaxDir (String)
     */
    public String getWindSpeedMaxDir(String s) {
        return ((windSpeedMaxDir != FLOATNULL) ? new Float(windSpeedMaxDir).toString() : "");
    } // method getWindSpeedMaxDir


    /**
     * Return the 'windSpeedStd' class variable
     * @return windSpeedStd (float)
     */
    public float getWindSpeedStd() {
        return windSpeedStd;
    } // method getWindSpeedStd

    /**
     * Return the 'windSpeedStd' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSpeedStd methods
     * @return windSpeedStd (String)
     */
    public String getWindSpeedStd(String s) {
        return ((windSpeedStd != FLOATNULL) ? new Float(windSpeedStd).toString() : "");
    } // method getWindSpeedStd


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
        if ((stationId == CHARNULL) &&
            (periodCode == INTNULL) &&
            (dateTime.equals(DATENULL)) &&
            (airTempAve == FLOATNULL) &&
            (airTempMin == FLOATNULL) &&
            (airTempMinTime.equals(DATENULL)) &&
            (airTempMax == FLOATNULL) &&
            (airTempMaxTime.equals(DATENULL)) &&
            (barometricPressure == FLOATNULL) &&
            (fog == FLOATNULL) &&
            (rainfall == FLOATNULL) &&
            (relativeHumidity == FLOATNULL) &&
            (solarRadiation == FLOATNULL) &&
            (solarRadiationMax == FLOATNULL) &&
            (windDir == FLOATNULL) &&
            (windSpeedAve == FLOATNULL) &&
            (windSpeedMin == FLOATNULL) &&
            (windSpeedMax == FLOATNULL) &&
            (windSpeedMaxTime.equals(DATENULL)) &&
            (windSpeedMaxLength == INTNULL) &&
            (windSpeedMaxDir == FLOATNULL) &&
            (windSpeedStd == FLOATNULL)) {
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
        int sumError = stationIdError +
            periodCodeError +
            dateTimeError +
            airTempAveError +
            airTempMinError +
            airTempMinTimeError +
            airTempMaxError +
            airTempMaxTimeError +
            barometricPressureError +
            fogError +
            rainfallError +
            relativeHumidityError +
            solarRadiationError +
            solarRadiationMaxError +
            windDirError +
            windSpeedAveError +
            windSpeedMinError +
            windSpeedMaxError +
            windSpeedMaxTimeError +
            windSpeedMaxLengthError +
            windSpeedMaxDirError +
            windSpeedStdError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


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
     * Gets the errorcode for the periodCode instance variable
     * @return errorcode (int)
     */
    public int getPeriodCodeError() {
        return periodCodeError;
    } // method getPeriodCodeError

    /**
     * Gets the errorMessage for the periodCode instance variable
     * @return errorMessage (String)
     */
    public String getPeriodCodeErrorMessage() {
        return periodCodeErrorMessage;
    } // method getPeriodCodeErrorMessage


    /**
     * Gets the errorcode for the dateTime instance variable
     * @return errorcode (int)
     */
    public int getDateTimeError() {
        return dateTimeError;
    } // method getDateTimeError

    /**
     * Gets the errorMessage for the dateTime instance variable
     * @return errorMessage (String)
     */
    public String getDateTimeErrorMessage() {
        return dateTimeErrorMessage;
    } // method getDateTimeErrorMessage


    /**
     * Gets the errorcode for the airTempAve instance variable
     * @return errorcode (int)
     */
    public int getAirTempAveError() {
        return airTempAveError;
    } // method getAirTempAveError

    /**
     * Gets the errorMessage for the airTempAve instance variable
     * @return errorMessage (String)
     */
    public String getAirTempAveErrorMessage() {
        return airTempAveErrorMessage;
    } // method getAirTempAveErrorMessage


    /**
     * Gets the errorcode for the airTempMin instance variable
     * @return errorcode (int)
     */
    public int getAirTempMinError() {
        return airTempMinError;
    } // method getAirTempMinError

    /**
     * Gets the errorMessage for the airTempMin instance variable
     * @return errorMessage (String)
     */
    public String getAirTempMinErrorMessage() {
        return airTempMinErrorMessage;
    } // method getAirTempMinErrorMessage


    /**
     * Gets the errorcode for the airTempMinTime instance variable
     * @return errorcode (int)
     */
    public int getAirTempMinTimeError() {
        return airTempMinTimeError;
    } // method getAirTempMinTimeError

    /**
     * Gets the errorMessage for the airTempMinTime instance variable
     * @return errorMessage (String)
     */
    public String getAirTempMinTimeErrorMessage() {
        return airTempMinTimeErrorMessage;
    } // method getAirTempMinTimeErrorMessage


    /**
     * Gets the errorcode for the airTempMax instance variable
     * @return errorcode (int)
     */
    public int getAirTempMaxError() {
        return airTempMaxError;
    } // method getAirTempMaxError

    /**
     * Gets the errorMessage for the airTempMax instance variable
     * @return errorMessage (String)
     */
    public String getAirTempMaxErrorMessage() {
        return airTempMaxErrorMessage;
    } // method getAirTempMaxErrorMessage


    /**
     * Gets the errorcode for the airTempMaxTime instance variable
     * @return errorcode (int)
     */
    public int getAirTempMaxTimeError() {
        return airTempMaxTimeError;
    } // method getAirTempMaxTimeError

    /**
     * Gets the errorMessage for the airTempMaxTime instance variable
     * @return errorMessage (String)
     */
    public String getAirTempMaxTimeErrorMessage() {
        return airTempMaxTimeErrorMessage;
    } // method getAirTempMaxTimeErrorMessage


    /**
     * Gets the errorcode for the barometricPressure instance variable
     * @return errorcode (int)
     */
    public int getBarometricPressureError() {
        return barometricPressureError;
    } // method getBarometricPressureError

    /**
     * Gets the errorMessage for the barometricPressure instance variable
     * @return errorMessage (String)
     */
    public String getBarometricPressureErrorMessage() {
        return barometricPressureErrorMessage;
    } // method getBarometricPressureErrorMessage


    /**
     * Gets the errorcode for the fog instance variable
     * @return errorcode (int)
     */
    public int getFogError() {
        return fogError;
    } // method getFogError

    /**
     * Gets the errorMessage for the fog instance variable
     * @return errorMessage (String)
     */
    public String getFogErrorMessage() {
        return fogErrorMessage;
    } // method getFogErrorMessage


    /**
     * Gets the errorcode for the rainfall instance variable
     * @return errorcode (int)
     */
    public int getRainfallError() {
        return rainfallError;
    } // method getRainfallError

    /**
     * Gets the errorMessage for the rainfall instance variable
     * @return errorMessage (String)
     */
    public String getRainfallErrorMessage() {
        return rainfallErrorMessage;
    } // method getRainfallErrorMessage


    /**
     * Gets the errorcode for the relativeHumidity instance variable
     * @return errorcode (int)
     */
    public int getRelativeHumidityError() {
        return relativeHumidityError;
    } // method getRelativeHumidityError

    /**
     * Gets the errorMessage for the relativeHumidity instance variable
     * @return errorMessage (String)
     */
    public String getRelativeHumidityErrorMessage() {
        return relativeHumidityErrorMessage;
    } // method getRelativeHumidityErrorMessage


    /**
     * Gets the errorcode for the solarRadiation instance variable
     * @return errorcode (int)
     */
    public int getSolarRadiationError() {
        return solarRadiationError;
    } // method getSolarRadiationError

    /**
     * Gets the errorMessage for the solarRadiation instance variable
     * @return errorMessage (String)
     */
    public String getSolarRadiationErrorMessage() {
        return solarRadiationErrorMessage;
    } // method getSolarRadiationErrorMessage


    /**
     * Gets the errorcode for the solarRadiationMax instance variable
     * @return errorcode (int)
     */
    public int getSolarRadiationMaxError() {
        return solarRadiationMaxError;
    } // method getSolarRadiationMaxError

    /**
     * Gets the errorMessage for the solarRadiationMax instance variable
     * @return errorMessage (String)
     */
    public String getSolarRadiationMaxErrorMessage() {
        return solarRadiationMaxErrorMessage;
    } // method getSolarRadiationMaxErrorMessage


    /**
     * Gets the errorcode for the windDir instance variable
     * @return errorcode (int)
     */
    public int getWindDirError() {
        return windDirError;
    } // method getWindDirError

    /**
     * Gets the errorMessage for the windDir instance variable
     * @return errorMessage (String)
     */
    public String getWindDirErrorMessage() {
        return windDirErrorMessage;
    } // method getWindDirErrorMessage


    /**
     * Gets the errorcode for the windSpeedAve instance variable
     * @return errorcode (int)
     */
    public int getWindSpeedAveError() {
        return windSpeedAveError;
    } // method getWindSpeedAveError

    /**
     * Gets the errorMessage for the windSpeedAve instance variable
     * @return errorMessage (String)
     */
    public String getWindSpeedAveErrorMessage() {
        return windSpeedAveErrorMessage;
    } // method getWindSpeedAveErrorMessage


    /**
     * Gets the errorcode for the windSpeedMin instance variable
     * @return errorcode (int)
     */
    public int getWindSpeedMinError() {
        return windSpeedMinError;
    } // method getWindSpeedMinError

    /**
     * Gets the errorMessage for the windSpeedMin instance variable
     * @return errorMessage (String)
     */
    public String getWindSpeedMinErrorMessage() {
        return windSpeedMinErrorMessage;
    } // method getWindSpeedMinErrorMessage


    /**
     * Gets the errorcode for the windSpeedMax instance variable
     * @return errorcode (int)
     */
    public int getWindSpeedMaxError() {
        return windSpeedMaxError;
    } // method getWindSpeedMaxError

    /**
     * Gets the errorMessage for the windSpeedMax instance variable
     * @return errorMessage (String)
     */
    public String getWindSpeedMaxErrorMessage() {
        return windSpeedMaxErrorMessage;
    } // method getWindSpeedMaxErrorMessage


    /**
     * Gets the errorcode for the windSpeedMaxTime instance variable
     * @return errorcode (int)
     */
    public int getWindSpeedMaxTimeError() {
        return windSpeedMaxTimeError;
    } // method getWindSpeedMaxTimeError

    /**
     * Gets the errorMessage for the windSpeedMaxTime instance variable
     * @return errorMessage (String)
     */
    public String getWindSpeedMaxTimeErrorMessage() {
        return windSpeedMaxTimeErrorMessage;
    } // method getWindSpeedMaxTimeErrorMessage


    /**
     * Gets the errorcode for the windSpeedMaxLength instance variable
     * @return errorcode (int)
     */
    public int getWindSpeedMaxLengthError() {
        return windSpeedMaxLengthError;
    } // method getWindSpeedMaxLengthError

    /**
     * Gets the errorMessage for the windSpeedMaxLength instance variable
     * @return errorMessage (String)
     */
    public String getWindSpeedMaxLengthErrorMessage() {
        return windSpeedMaxLengthErrorMessage;
    } // method getWindSpeedMaxLengthErrorMessage


    /**
     * Gets the errorcode for the windSpeedMaxDir instance variable
     * @return errorcode (int)
     */
    public int getWindSpeedMaxDirError() {
        return windSpeedMaxDirError;
    } // method getWindSpeedMaxDirError

    /**
     * Gets the errorMessage for the windSpeedMaxDir instance variable
     * @return errorMessage (String)
     */
    public String getWindSpeedMaxDirErrorMessage() {
        return windSpeedMaxDirErrorMessage;
    } // method getWindSpeedMaxDirErrorMessage


    /**
     * Gets the errorcode for the windSpeedStd instance variable
     * @return errorcode (int)
     */
    public int getWindSpeedStdError() {
        return windSpeedStdError;
    } // method getWindSpeedStdError

    /**
     * Gets the errorMessage for the windSpeedStd instance variable
     * @return errorMessage (String)
     */
    public String getWindSpeedStdErrorMessage() {
        return windSpeedStdErrorMessage;
    } // method getWindSpeedStdErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of WetData. e.g.<pre>
     * WetData wetData = new WetData(<val1>);
     * WetData wetDataArray[] = wetData.get();</pre>
     * will get the WetData record where stationId = <val1>.
     * @return Array of WetData (WetData[])
     */
    public WetData[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * WetData wetDataArray[] =
     *     new WetData().get(WetData.STATION_ID+"=<val1>");</pre>
     * will get the WetData record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of WetData (WetData[])
     */
    public WetData[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * WetData wetDataArray[] =
     *     new WetData().get("1=1",wetData.STATION_ID);</pre>
     * will get the all the WetData records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetData (WetData[])
     */
    public WetData[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = WetData.STATION_ID,WetData.PERIOD_CODE;
     * String where = WetData.STATION_ID + "=<val1";
     * String order = WetData.STATION_ID;
     * WetData wetDataArray[] =
     *     new WetData().get(columns, where, order);</pre>
     * will get the stationId and periodCode colums of all WetData records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetData (WetData[])
     */
    public WetData[] get (String fields, String where, String order) {
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
     * and transforms it into an array of WetData.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private WetData[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol          = db.getColNumber(STATION_ID);
        int periodCodeCol         = db.getColNumber(PERIOD_CODE);
        int dateTimeCol           = db.getColNumber(DATE_TIME);
        int airTempAveCol         = db.getColNumber(AIR_TEMP_AVE);
        int airTempMinCol         = db.getColNumber(AIR_TEMP_MIN);
        int airTempMinTimeCol     = db.getColNumber(AIR_TEMP_MIN_TIME);
        int airTempMaxCol         = db.getColNumber(AIR_TEMP_MAX);
        int airTempMaxTimeCol     = db.getColNumber(AIR_TEMP_MAX_TIME);
        int barometricPressureCol = db.getColNumber(BAROMETRIC_PRESSURE);
        int fogCol                = db.getColNumber(FOG);
        int rainfallCol           = db.getColNumber(RAINFALL);
        int relativeHumidityCol   = db.getColNumber(RELATIVE_HUMIDITY);
        int solarRadiationCol     = db.getColNumber(SOLAR_RADIATION);
        int solarRadiationMaxCol  = db.getColNumber(SOLAR_RADIATION_MAX);
        int windDirCol            = db.getColNumber(WIND_DIR);
        int windSpeedAveCol       = db.getColNumber(WIND_SPEED_AVE);
        int windSpeedMinCol       = db.getColNumber(WIND_SPEED_MIN);
        int windSpeedMaxCol       = db.getColNumber(WIND_SPEED_MAX);
        int windSpeedMaxTimeCol   = db.getColNumber(WIND_SPEED_MAX_TIME);
        int windSpeedMaxLengthCol = db.getColNumber(WIND_SPEED_MAX_LENGTH);
        int windSpeedMaxDirCol    = db.getColNumber(WIND_SPEED_MAX_DIR);
        int windSpeedStdCol       = db.getColNumber(WIND_SPEED_STD);
        WetData[] cArray = new WetData[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new WetData();
            if (stationIdCol != -1)
                cArray[i].setStationId         ((String) row.elementAt(stationIdCol));
            if (periodCodeCol != -1)
                cArray[i].setPeriodCode        ((String) row.elementAt(periodCodeCol));
            if (dateTimeCol != -1)
                cArray[i].setDateTime          ((String) row.elementAt(dateTimeCol));
            if (airTempAveCol != -1)
                cArray[i].setAirTempAve        ((String) row.elementAt(airTempAveCol));
            if (airTempMinCol != -1)
                cArray[i].setAirTempMin        ((String) row.elementAt(airTempMinCol));
            if (airTempMinTimeCol != -1)
                cArray[i].setAirTempMinTime    ((String) row.elementAt(airTempMinTimeCol));
            if (airTempMaxCol != -1)
                cArray[i].setAirTempMax        ((String) row.elementAt(airTempMaxCol));
            if (airTempMaxTimeCol != -1)
                cArray[i].setAirTempMaxTime    ((String) row.elementAt(airTempMaxTimeCol));
            if (barometricPressureCol != -1)
                cArray[i].setBarometricPressure((String) row.elementAt(barometricPressureCol));
            if (fogCol != -1)
                cArray[i].setFog               ((String) row.elementAt(fogCol));
            if (rainfallCol != -1)
                cArray[i].setRainfall          ((String) row.elementAt(rainfallCol));
            if (relativeHumidityCol != -1)
                cArray[i].setRelativeHumidity  ((String) row.elementAt(relativeHumidityCol));
            if (solarRadiationCol != -1)
                cArray[i].setSolarRadiation    ((String) row.elementAt(solarRadiationCol));
            if (solarRadiationMaxCol != -1)
                cArray[i].setSolarRadiationMax ((String) row.elementAt(solarRadiationMaxCol));
            if (windDirCol != -1)
                cArray[i].setWindDir           ((String) row.elementAt(windDirCol));
            if (windSpeedAveCol != -1)
                cArray[i].setWindSpeedAve      ((String) row.elementAt(windSpeedAveCol));
            if (windSpeedMinCol != -1)
                cArray[i].setWindSpeedMin      ((String) row.elementAt(windSpeedMinCol));
            if (windSpeedMaxCol != -1)
                cArray[i].setWindSpeedMax      ((String) row.elementAt(windSpeedMaxCol));
            if (windSpeedMaxTimeCol != -1)
                cArray[i].setWindSpeedMaxTime  ((String) row.elementAt(windSpeedMaxTimeCol));
            if (windSpeedMaxLengthCol != -1)
                cArray[i].setWindSpeedMaxLength((String) row.elementAt(windSpeedMaxLengthCol));
            if (windSpeedMaxDirCol != -1)
                cArray[i].setWindSpeedMaxDir   ((String) row.elementAt(windSpeedMaxDirCol));
            if (windSpeedStdCol != -1)
                cArray[i].setWindSpeedStd      ((String) row.elementAt(windSpeedStdCol));
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
     *     new WetData(
     *         <val1>,
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
     *         <val17>,
     *         <val18>,
     *         <val19>,
     *         <val20>,
     *         <val21>,
     *         <val22>).put();</pre>
     * will insert a record with:
     *     stationId          = <val1>,
     *     periodCode         = <val2>,
     *     dateTime           = <val3>,
     *     airTempAve         = <val4>,
     *     airTempMin         = <val5>,
     *     airTempMinTime     = <val6>,
     *     airTempMax         = <val7>,
     *     airTempMaxTime     = <val8>,
     *     barometricPressure = <val9>,
     *     fog                = <val10>,
     *     rainfall           = <val11>,
     *     relativeHumidity   = <val12>,
     *     solarRadiation     = <val13>,
     *     solarRadiationMax  = <val14>,
     *     windDir            = <val15>,
     *     windSpeedAve       = <val16>,
     *     windSpeedMin       = <val17>,
     *     windSpeedMax       = <val18>,
     *     windSpeedMaxTime   = <val19>,
     *     windSpeedMaxLength = <val20>,
     *     windSpeedMaxDir    = <val21>,
     *     windSpeedStd       = <val22>.
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
     * Boolean success = new WetData(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.DATENULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where periodCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WetData wetData = new WetData();
     * success = wetData.del(WetData.STATION_ID+"=<val1>");</pre>
     * will delete all records where stationId = <val1>.
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
     * update are taken from the WetData argument, .e.g.<pre>
     * Boolean success
     * WetData updWetData = new WetData();
     * updWetData.setPeriodCode(<val2>);
     * WetData whereWetData = new WetData(<val1>);
     * success = whereWetData.upd(updWetData);</pre>
     * will update PeriodCode to <val2> for all records where
     * stationId = <val1>.
     * @param  wetData  A WetData variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(WetData wetData) {
        return db.update (TABLE, createColVals(wetData), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WetData updWetData = new WetData();
     * updWetData.setPeriodCode(<val2>);
     * WetData whereWetData = new WetData();
     * success = whereWetData.upd(
     *     updWetData, WetData.STATION_ID+"=<val1>");</pre>
     * will update PeriodCode to <val2> for all records where
     * stationId = <val1>.
     * @param  wetData  A WetData variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(WetData wetData, String where) {
        return db.update (TABLE, createColVals(wetData), where);
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
        if (getStationId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATION_ID + "='" + getStationId() + "'";
        } // if getStationId
        if (getPeriodCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PERIOD_CODE + "=" + getPeriodCode("");
        } // if getPeriodCode
        if (!getDateTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME +
                "=" + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getAirTempAve() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + AIR_TEMP_AVE + "=" + getAirTempAve("");
        } // if getAirTempAve
        if (getAirTempMin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + AIR_TEMP_MIN + "=" + getAirTempMin("");
        } // if getAirTempMin
        if (!getAirTempMinTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + AIR_TEMP_MIN_TIME +
                "=" + Tables.getDateFormat(getAirTempMinTime());
        } // if getAirTempMinTime
        if (getAirTempMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + AIR_TEMP_MAX + "=" + getAirTempMax("");
        } // if getAirTempMax
        if (!getAirTempMaxTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + AIR_TEMP_MAX_TIME +
                "=" + Tables.getDateFormat(getAirTempMaxTime());
        } // if getAirTempMaxTime
        if (getBarometricPressure() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + BAROMETRIC_PRESSURE + "=" + getBarometricPressure("");
        } // if getBarometricPressure
        if (getFog() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FOG + "=" + getFog("");
        } // if getFog
        if (getRainfall() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RAINFALL + "=" + getRainfall("");
        } // if getRainfall
        if (getRelativeHumidity() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RELATIVE_HUMIDITY + "=" + getRelativeHumidity("");
        } // if getRelativeHumidity
        if (getSolarRadiation() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SOLAR_RADIATION + "=" + getSolarRadiation("");
        } // if getSolarRadiation
        if (getSolarRadiationMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SOLAR_RADIATION_MAX + "=" + getSolarRadiationMax("");
        } // if getSolarRadiationMax
        if (getWindDir() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_DIR + "=" + getWindDir("");
        } // if getWindDir
        if (getWindSpeedAve() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED_AVE + "=" + getWindSpeedAve("");
        } // if getWindSpeedAve
        if (getWindSpeedMin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED_MIN + "=" + getWindSpeedMin("");
        } // if getWindSpeedMin
        if (getWindSpeedMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED_MAX + "=" + getWindSpeedMax("");
        } // if getWindSpeedMax
        if (!getWindSpeedMaxTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED_MAX_TIME +
                "=" + Tables.getDateFormat(getWindSpeedMaxTime());
        } // if getWindSpeedMaxTime
        if (getWindSpeedMaxLength() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED_MAX_LENGTH + "=" + getWindSpeedMaxLength("");
        } // if getWindSpeedMaxLength
        if (getWindSpeedMaxDir() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED_MAX_DIR + "=" + getWindSpeedMaxDir("");
        } // if getWindSpeedMaxDir
        if (getWindSpeedStd() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED_STD + "=" + getWindSpeedStd("");
        } // if getWindSpeedStd
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(WetData aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getPeriodCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PERIOD_CODE +"=";
            colVals += (aVar.getPeriodCode() == INTNULL2 ?
                "null" : aVar.getPeriodCode(""));
        } // if aVar.getPeriodCode
        if (!aVar.getDateTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME +"=";
            colVals += (aVar.getDateTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTime()));
        } // if aVar.getDateTime
        if (aVar.getAirTempAve() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += AIR_TEMP_AVE +"=";
            colVals += (aVar.getAirTempAve() == FLOATNULL2 ?
                "null" : aVar.getAirTempAve(""));
        } // if aVar.getAirTempAve
        if (aVar.getAirTempMin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += AIR_TEMP_MIN +"=";
            colVals += (aVar.getAirTempMin() == FLOATNULL2 ?
                "null" : aVar.getAirTempMin(""));
        } // if aVar.getAirTempMin
        if (!aVar.getAirTempMinTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += AIR_TEMP_MIN_TIME +"=";
            colVals += (aVar.getAirTempMinTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getAirTempMinTime()));
        } // if aVar.getAirTempMinTime
        if (aVar.getAirTempMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += AIR_TEMP_MAX +"=";
            colVals += (aVar.getAirTempMax() == FLOATNULL2 ?
                "null" : aVar.getAirTempMax(""));
        } // if aVar.getAirTempMax
        if (!aVar.getAirTempMaxTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += AIR_TEMP_MAX_TIME +"=";
            colVals += (aVar.getAirTempMaxTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getAirTempMaxTime()));
        } // if aVar.getAirTempMaxTime
        if (aVar.getBarometricPressure() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += BAROMETRIC_PRESSURE +"=";
            colVals += (aVar.getBarometricPressure() == FLOATNULL2 ?
                "null" : aVar.getBarometricPressure(""));
        } // if aVar.getBarometricPressure
        if (aVar.getFog() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FOG +"=";
            colVals += (aVar.getFog() == FLOATNULL2 ?
                "null" : aVar.getFog(""));
        } // if aVar.getFog
        if (aVar.getRainfall() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RAINFALL +"=";
            colVals += (aVar.getRainfall() == FLOATNULL2 ?
                "null" : aVar.getRainfall(""));
        } // if aVar.getRainfall
        if (aVar.getRelativeHumidity() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RELATIVE_HUMIDITY +"=";
            colVals += (aVar.getRelativeHumidity() == FLOATNULL2 ?
                "null" : aVar.getRelativeHumidity(""));
        } // if aVar.getRelativeHumidity
        if (aVar.getSolarRadiation() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SOLAR_RADIATION +"=";
            colVals += (aVar.getSolarRadiation() == FLOATNULL2 ?
                "null" : aVar.getSolarRadiation(""));
        } // if aVar.getSolarRadiation
        if (aVar.getSolarRadiationMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SOLAR_RADIATION_MAX +"=";
            colVals += (aVar.getSolarRadiationMax() == FLOATNULL2 ?
                "null" : aVar.getSolarRadiationMax(""));
        } // if aVar.getSolarRadiationMax
        if (aVar.getWindDir() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_DIR +"=";
            colVals += (aVar.getWindDir() == FLOATNULL2 ?
                "null" : aVar.getWindDir(""));
        } // if aVar.getWindDir
        if (aVar.getWindSpeedAve() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED_AVE +"=";
            colVals += (aVar.getWindSpeedAve() == FLOATNULL2 ?
                "null" : aVar.getWindSpeedAve(""));
        } // if aVar.getWindSpeedAve
        if (aVar.getWindSpeedMin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED_MIN +"=";
            colVals += (aVar.getWindSpeedMin() == FLOATNULL2 ?
                "null" : aVar.getWindSpeedMin(""));
        } // if aVar.getWindSpeedMin
        if (aVar.getWindSpeedMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED_MAX +"=";
            colVals += (aVar.getWindSpeedMax() == FLOATNULL2 ?
                "null" : aVar.getWindSpeedMax(""));
        } // if aVar.getWindSpeedMax
        if (!aVar.getWindSpeedMaxTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED_MAX_TIME +"=";
            colVals += (aVar.getWindSpeedMaxTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getWindSpeedMaxTime()));
        } // if aVar.getWindSpeedMaxTime
        if (aVar.getWindSpeedMaxLength() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED_MAX_LENGTH +"=";
            colVals += (aVar.getWindSpeedMaxLength() == INTNULL2 ?
                "null" : aVar.getWindSpeedMaxLength(""));
        } // if aVar.getWindSpeedMaxLength
        if (aVar.getWindSpeedMaxDir() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED_MAX_DIR +"=";
            colVals += (aVar.getWindSpeedMaxDir() == FLOATNULL2 ?
                "null" : aVar.getWindSpeedMaxDir(""));
        } // if aVar.getWindSpeedMaxDir
        if (aVar.getWindSpeedStd() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED_STD +"=";
            colVals += (aVar.getWindSpeedStd() == FLOATNULL2 ?
                "null" : aVar.getWindSpeedStd(""));
        } // if aVar.getWindSpeedStd
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (getPeriodCode() != INTNULL) {
            columns = columns + "," + PERIOD_CODE;
        } // if getPeriodCode
        if (!getDateTime().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME;
        } // if getDateTime
        if (getAirTempAve() != FLOATNULL) {
            columns = columns + "," + AIR_TEMP_AVE;
        } // if getAirTempAve
        if (getAirTempMin() != FLOATNULL) {
            columns = columns + "," + AIR_TEMP_MIN;
        } // if getAirTempMin
        if (!getAirTempMinTime().equals(DATENULL)) {
            columns = columns + "," + AIR_TEMP_MIN_TIME;
        } // if getAirTempMinTime
        if (getAirTempMax() != FLOATNULL) {
            columns = columns + "," + AIR_TEMP_MAX;
        } // if getAirTempMax
        if (!getAirTempMaxTime().equals(DATENULL)) {
            columns = columns + "," + AIR_TEMP_MAX_TIME;
        } // if getAirTempMaxTime
        if (getBarometricPressure() != FLOATNULL) {
            columns = columns + "," + BAROMETRIC_PRESSURE;
        } // if getBarometricPressure
        if (getFog() != FLOATNULL) {
            columns = columns + "," + FOG;
        } // if getFog
        if (getRainfall() != FLOATNULL) {
            columns = columns + "," + RAINFALL;
        } // if getRainfall
        if (getRelativeHumidity() != FLOATNULL) {
            columns = columns + "," + RELATIVE_HUMIDITY;
        } // if getRelativeHumidity
        if (getSolarRadiation() != FLOATNULL) {
            columns = columns + "," + SOLAR_RADIATION;
        } // if getSolarRadiation
        if (getSolarRadiationMax() != FLOATNULL) {
            columns = columns + "," + SOLAR_RADIATION_MAX;
        } // if getSolarRadiationMax
        if (getWindDir() != FLOATNULL) {
            columns = columns + "," + WIND_DIR;
        } // if getWindDir
        if (getWindSpeedAve() != FLOATNULL) {
            columns = columns + "," + WIND_SPEED_AVE;
        } // if getWindSpeedAve
        if (getWindSpeedMin() != FLOATNULL) {
            columns = columns + "," + WIND_SPEED_MIN;
        } // if getWindSpeedMin
        if (getWindSpeedMax() != FLOATNULL) {
            columns = columns + "," + WIND_SPEED_MAX;
        } // if getWindSpeedMax
        if (!getWindSpeedMaxTime().equals(DATENULL)) {
            columns = columns + "," + WIND_SPEED_MAX_TIME;
        } // if getWindSpeedMaxTime
        if (getWindSpeedMaxLength() != INTNULL) {
            columns = columns + "," + WIND_SPEED_MAX_LENGTH;
        } // if getWindSpeedMaxLength
        if (getWindSpeedMaxDir() != FLOATNULL) {
            columns = columns + "," + WIND_SPEED_MAX_DIR;
        } // if getWindSpeedMaxDir
        if (getWindSpeedStd() != FLOATNULL) {
            columns = columns + "," + WIND_SPEED_STD;
        } // if getWindSpeedStd
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (getPeriodCode() != INTNULL) {
            values  = values  + "," + getPeriodCode("");
        } // if getPeriodCode
        if (!getDateTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getAirTempAve() != FLOATNULL) {
            values  = values  + "," + getAirTempAve("");
        } // if getAirTempAve
        if (getAirTempMin() != FLOATNULL) {
            values  = values  + "," + getAirTempMin("");
        } // if getAirTempMin
        if (!getAirTempMinTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getAirTempMinTime());
        } // if getAirTempMinTime
        if (getAirTempMax() != FLOATNULL) {
            values  = values  + "," + getAirTempMax("");
        } // if getAirTempMax
        if (!getAirTempMaxTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getAirTempMaxTime());
        } // if getAirTempMaxTime
        if (getBarometricPressure() != FLOATNULL) {
            values  = values  + "," + getBarometricPressure("");
        } // if getBarometricPressure
        if (getFog() != FLOATNULL) {
            values  = values  + "," + getFog("");
        } // if getFog
        if (getRainfall() != FLOATNULL) {
            values  = values  + "," + getRainfall("");
        } // if getRainfall
        if (getRelativeHumidity() != FLOATNULL) {
            values  = values  + "," + getRelativeHumidity("");
        } // if getRelativeHumidity
        if (getSolarRadiation() != FLOATNULL) {
            values  = values  + "," + getSolarRadiation("");
        } // if getSolarRadiation
        if (getSolarRadiationMax() != FLOATNULL) {
            values  = values  + "," + getSolarRadiationMax("");
        } // if getSolarRadiationMax
        if (getWindDir() != FLOATNULL) {
            values  = values  + "," + getWindDir("");
        } // if getWindDir
        if (getWindSpeedAve() != FLOATNULL) {
            values  = values  + "," + getWindSpeedAve("");
        } // if getWindSpeedAve
        if (getWindSpeedMin() != FLOATNULL) {
            values  = values  + "," + getWindSpeedMin("");
        } // if getWindSpeedMin
        if (getWindSpeedMax() != FLOATNULL) {
            values  = values  + "," + getWindSpeedMax("");
        } // if getWindSpeedMax
        if (!getWindSpeedMaxTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getWindSpeedMaxTime());
        } // if getWindSpeedMaxTime
        if (getWindSpeedMaxLength() != INTNULL) {
            values  = values  + "," + getWindSpeedMaxLength("");
        } // if getWindSpeedMaxLength
        if (getWindSpeedMaxDir() != FLOATNULL) {
            values  = values  + "," + getWindSpeedMaxDir("");
        } // if getWindSpeedMaxDir
        if (getWindSpeedStd() != FLOATNULL) {
            values  = values  + "," + getWindSpeedStd("");
        } // if getWindSpeedStd
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getStationId("")          + "|" +
            getPeriodCode("")         + "|" +
            getDateTime("")           + "|" +
            getAirTempAve("")         + "|" +
            getAirTempMin("")         + "|" +
            getAirTempMinTime("")     + "|" +
            getAirTempMax("")         + "|" +
            getAirTempMaxTime("")     + "|" +
            getBarometricPressure("") + "|" +
            getFog("")                + "|" +
            getRainfall("")           + "|" +
            getRelativeHumidity("")   + "|" +
            getSolarRadiation("")     + "|" +
            getSolarRadiationMax("")  + "|" +
            getWindDir("")            + "|" +
            getWindSpeedAve("")       + "|" +
            getWindSpeedMin("")       + "|" +
            getWindSpeedMax("")       + "|" +
            getWindSpeedMaxTime("")   + "|" +
            getWindSpeedMaxLength("") + "|" +
            getWindSpeedMaxDir("")    + "|" +
            getWindSpeedStd("")       + "|";
    } // method toString

} // class WetData
