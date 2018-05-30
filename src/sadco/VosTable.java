package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the VOS_TABLE table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 001120 - SIT Group
 * @version
 * 001120 - GenTableClassB class - create class<br>
 */
public class VosTable extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public String TABLE = "VOS_TABLE";
    /** The latitude field name */
    public static final String LATITUDE = "LATITUDE";
    /** The longitude field name */
    public static final String LONGITUDE = "LONGITUDE";
    /** The dateTime field name */
    public static final String DATE_TIME = "DATE_TIME";
    /** The daynull field name */
    public static final String DAYNULL = "DAYNULL";
    /** The callsign field name */
    public static final String CALLSIGN = "CALLSIGN";
    /** The country field name */
    public static final String COUNTRY = "COUNTRY";
    /** The platform field name */
    public static final String PLATFORM = "PLATFORM";
    /** The dataId field name */
    public static final String DATA_ID = "DATA_ID";
    /** The qualityControl field name */
    public static final String QUALITY_CONTROL = "QUALITY_CONTROL";
    /** The source1 field name */
    public static final String SOURCE1 = "SOURCE1";
    /** The loadId field name */
    public static final String LOAD_ID = "LOAD_ID";
    /** The dupflag field name */
    public static final String DUPFLAG = "DUPFLAG";
    /** The atmosphericPressure field name */
    public static final String ATMOSPHERIC_PRESSURE = "ATMOSPHERIC_PRESSURE";
    /** The surfaceTemperature field name */
    public static final String SURFACE_TEMPERATURE = "SURFACE_TEMPERATURE";
    /** The surfaceTemperatureType field name */
    public static final String SURFACE_TEMPERATURE_TYPE = "SURFACE_TEMPERATURE_TYPE";
    /** The drybulb field name */
    public static final String DRYBULB = "DRYBULB";
    /** The wetbulb field name */
    public static final String WETBULB = "WETBULB";
    /** The wetbulbIce field name */
    public static final String WETBULB_ICE = "WETBULB_ICE";
    /** The dewpoint field name */
    public static final String DEWPOINT = "DEWPOINT";
    /** The cloudAmount field name */
    public static final String CLOUD_AMOUNT = "CLOUD_AMOUNT";
    /** The cloud1 field name */
    public static final String CLOUD1 = "CLOUD1";
    /** The cloud2 field name */
    public static final String CLOUD2 = "CLOUD2";
    /** The cloud3 field name */
    public static final String CLOUD3 = "CLOUD3";
    /** The cloud4 field name */
    public static final String CLOUD4 = "CLOUD4";
    /** The cloud5 field name */
    public static final String CLOUD5 = "CLOUD5";
    /** The visibilityCode field name */
    public static final String VISIBILITY_CODE = "VISIBILITY_CODE";
    /** The weatherCode field name */
    public static final String WEATHER_CODE = "WEATHER_CODE";
    /** The swellDirection field name */
    public static final String SWELL_DIRECTION = "SWELL_DIRECTION";
    /** The swellHeight field name */
    public static final String SWELL_HEIGHT = "SWELL_HEIGHT";
    /** The swellPeriod field name */
    public static final String SWELL_PERIOD = "SWELL_PERIOD";
    /** The waveHeight field name */
    public static final String WAVE_HEIGHT = "WAVE_HEIGHT";
    /** The wavePeriod field name */
    public static final String WAVE_PERIOD = "WAVE_PERIOD";
    /** The windDirection field name */
    public static final String WIND_DIRECTION = "WIND_DIRECTION";
    /** The windSpeed field name */
    public static final String WIND_SPEED = "WIND_SPEED";
    /** The windSpeedType field name */
    public static final String WIND_SPEED_TYPE = "WIND_SPEED_TYPE";

    /**
     * The instance variables corresponding to the table fields
     */
    private float     latitude;
    private float     longitude;
    private Timestamp dateTime;
    private String    daynull;
    private String    callsign;
    private String    country;
    private String    platform;
    private String    dataId;
    private String    qualityControl;
    private String    source1;
    private int       loadId;
    private String    dupflag;
    private float     atmosphericPressure;
    private float     surfaceTemperature;
    private String    surfaceTemperatureType;
    private float     drybulb;
    private float     wetbulb;
    private String    wetbulbIce;
    private float     dewpoint;
    private String    cloudAmount;
    private String    cloud1;
    private String    cloud2;
    private String    cloud3;
    private String    cloud4;
    private String    cloud5;
    private String    visibilityCode;
    private String    weatherCode;
    private int       swellDirection;
    private float     swellHeight;
    private int       swellPeriod;
    private float     waveHeight;
    private int       wavePeriod;
    private int       windDirection;
    private float     windSpeed;
    private String    windSpeedType;

    /** The error variables  */
    private int latitudeError               = ERROR_NORMAL;
    private int longitudeError              = ERROR_NORMAL;
    private int dateTimeError               = ERROR_NORMAL;
    private int daynullError                = ERROR_NORMAL;
    private int callsignError               = ERROR_NORMAL;
    private int countryError                = ERROR_NORMAL;
    private int platformError               = ERROR_NORMAL;
    private int dataIdError                 = ERROR_NORMAL;
    private int qualityControlError         = ERROR_NORMAL;
    private int source1Error                = ERROR_NORMAL;
    private int loadIdError                 = ERROR_NORMAL;
    private int dupflagError                = ERROR_NORMAL;
    private int atmosphericPressureError    = ERROR_NORMAL;
    private int surfaceTemperatureError     = ERROR_NORMAL;
    private int surfaceTemperatureTypeError = ERROR_NORMAL;
    private int drybulbError                = ERROR_NORMAL;
    private int wetbulbError                = ERROR_NORMAL;
    private int wetbulbIceError             = ERROR_NORMAL;
    private int dewpointError               = ERROR_NORMAL;
    private int cloudAmountError            = ERROR_NORMAL;
    private int cloud1Error                 = ERROR_NORMAL;
    private int cloud2Error                 = ERROR_NORMAL;
    private int cloud3Error                 = ERROR_NORMAL;
    private int cloud4Error                 = ERROR_NORMAL;
    private int cloud5Error                 = ERROR_NORMAL;
    private int visibilityCodeError         = ERROR_NORMAL;
    private int weatherCodeError            = ERROR_NORMAL;
    private int swellDirectionError         = ERROR_NORMAL;
    private int swellHeightError            = ERROR_NORMAL;
    private int swellPeriodError            = ERROR_NORMAL;
    private int waveHeightError             = ERROR_NORMAL;
    private int wavePeriodError             = ERROR_NORMAL;
    private int windDirectionError          = ERROR_NORMAL;
    private int windSpeedError              = ERROR_NORMAL;
    private int windSpeedTypeError          = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String latitudeErrorMessage               = "";
    private String longitudeErrorMessage              = "";
    private String dateTimeErrorMessage               = "";
    private String daynullErrorMessage                = "";
    private String callsignErrorMessage               = "";
    private String countryErrorMessage                = "";
    private String platformErrorMessage               = "";
    private String dataIdErrorMessage                 = "";
    private String qualityControlErrorMessage         = "";
    private String source1ErrorMessage                = "";
    private String loadIdErrorMessage                 = "";
    private String dupflagErrorMessage                = "";
    private String atmosphericPressureErrorMessage    = "";
    private String surfaceTemperatureErrorMessage     = "";
    private String surfaceTemperatureTypeErrorMessage = "";
    private String drybulbErrorMessage                = "";
    private String wetbulbErrorMessage                = "";
    private String wetbulbIceErrorMessage             = "";
    private String dewpointErrorMessage               = "";
    private String cloudAmountErrorMessage            = "";
    private String cloud1ErrorMessage                 = "";
    private String cloud2ErrorMessage                 = "";
    private String cloud3ErrorMessage                 = "";
    private String cloud4ErrorMessage                 = "";
    private String cloud5ErrorMessage                 = "";
    private String visibilityCodeErrorMessage         = "";
    private String weatherCodeErrorMessage            = "";
    private String swellDirectionErrorMessage         = "";
    private String swellHeightErrorMessage            = "";
    private String swellPeriodErrorMessage            = "";
    private String waveHeightErrorMessage             = "";
    private String wavePeriodErrorMessage             = "";
    private String windDirectionErrorMessage          = "";
    private String windSpeedErrorMessage              = "";
    private String windSpeedTypeErrorMessage          = "";

    /** The min-max constants for all numerics */
    public static final float     LATITUDE_MN = FLOATMIN;
    public static final float     LATITUDE_MX = FLOATMAX;
    public static final float     LONGITUDE_MN = FLOATMIN;
    public static final float     LONGITUDE_MX = FLOATMAX;
    public static final Timestamp DATE_TIME_MN = DATEMIN;
    public static final Timestamp DATE_TIME_MX = DATEMAX;
    public static final int       LOAD_ID_MN = INTMIN;
    public static final int       LOAD_ID_MX = INTMAX;
    public static final float     ATMOSPHERIC_PRESSURE_MN = FLOATMIN;
    public static final float     ATMOSPHERIC_PRESSURE_MX = FLOATMAX;
    public static final float     SURFACE_TEMPERATURE_MN = FLOATMIN;
    public static final float     SURFACE_TEMPERATURE_MX = FLOATMAX;
    public static final float     DRYBULB_MN = FLOATMIN;
    public static final float     DRYBULB_MX = FLOATMAX;
    public static final float     WETBULB_MN = FLOATMIN;
    public static final float     WETBULB_MX = FLOATMAX;
    public static final float     DEWPOINT_MN = FLOATMIN;
    public static final float     DEWPOINT_MX = FLOATMAX;
    public static final int       SWELL_DIRECTION_MN = INTMIN;
    public static final int       SWELL_DIRECTION_MX = INTMAX;
    public static final float     SWELL_HEIGHT_MN = FLOATMIN;
    public static final float     SWELL_HEIGHT_MX = FLOATMAX;
    public static final int       SWELL_PERIOD_MN = INTMIN;
    public static final int       SWELL_PERIOD_MX = INTMAX;
    public static final float     WAVE_HEIGHT_MN = FLOATMIN;
    public static final float     WAVE_HEIGHT_MX = FLOATMAX;
    public static final int       WAVE_PERIOD_MN = INTMIN;
    public static final int       WAVE_PERIOD_MX = INTMAX;
    public static final int       WIND_DIRECTION_MN = INTMIN;
    public static final int       WIND_DIRECTION_MX = INTMAX;
    public static final float     WIND_SPEED_MN = FLOATMIN;
    public static final float     WIND_SPEED_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception latitudeOutOfBoundsException =
        new Exception ("'latitude' out of bounds: " +
            LATITUDE_MN + " - " + LATITUDE_MX);
    Exception longitudeOutOfBoundsException =
        new Exception ("'longitude' out of bounds: " +
            LONGITUDE_MN + " - " + LONGITUDE_MX);
    Exception dateTimeException =
        new Exception ("'dateTime' is null");
    Exception dateTimeOutOfBoundsException =
        new Exception ("'dateTime' out of bounds: " +
            DATE_TIME_MN + " - " + DATE_TIME_MX);
    Exception loadIdOutOfBoundsException =
        new Exception ("'loadId' out of bounds: " +
            LOAD_ID_MN + " - " + LOAD_ID_MX);
    Exception atmosphericPressureOutOfBoundsException =
        new Exception ("'atmosphericPressure' out of bounds: " +
            ATMOSPHERIC_PRESSURE_MN + " - " + ATMOSPHERIC_PRESSURE_MX);
    Exception surfaceTemperatureOutOfBoundsException =
        new Exception ("'surfaceTemperature' out of bounds: " +
            SURFACE_TEMPERATURE_MN + " - " + SURFACE_TEMPERATURE_MX);
    Exception drybulbOutOfBoundsException =
        new Exception ("'drybulb' out of bounds: " +
            DRYBULB_MN + " - " + DRYBULB_MX);
    Exception wetbulbOutOfBoundsException =
        new Exception ("'wetbulb' out of bounds: " +
            WETBULB_MN + " - " + WETBULB_MX);
    Exception dewpointOutOfBoundsException =
        new Exception ("'dewpoint' out of bounds: " +
            DEWPOINT_MN + " - " + DEWPOINT_MX);
    Exception swellDirectionOutOfBoundsException =
        new Exception ("'swellDirection' out of bounds: " +
            SWELL_DIRECTION_MN + " - " + SWELL_DIRECTION_MX);
    Exception swellHeightOutOfBoundsException =
        new Exception ("'swellHeight' out of bounds: " +
            SWELL_HEIGHT_MN + " - " + SWELL_HEIGHT_MX);
    Exception swellPeriodOutOfBoundsException =
        new Exception ("'swellPeriod' out of bounds: " +
            SWELL_PERIOD_MN + " - " + SWELL_PERIOD_MX);
    Exception waveHeightOutOfBoundsException =
        new Exception ("'waveHeight' out of bounds: " +
            WAVE_HEIGHT_MN + " - " + WAVE_HEIGHT_MX);
    Exception wavePeriodOutOfBoundsException =
        new Exception ("'wavePeriod' out of bounds: " +
            WAVE_PERIOD_MN + " - " + WAVE_PERIOD_MX);
    Exception windDirectionOutOfBoundsException =
        new Exception ("'windDirection' out of bounds: " +
            WIND_DIRECTION_MN + " - " + WIND_DIRECTION_MX);
    Exception windSpeedOutOfBoundsException =
        new Exception ("'windSpeed' out of bounds: " +
            WIND_SPEED_MN + " - " + WIND_SPEED_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public VosTable() {
        clearVars();
        if (dbg) System.out.println ("<br>in VosTable constructor 1"); // debug
    } // VosTable constructor

    /**
     * Instantiate a VosTable object and initialize the instance variables.
     * @param latitude  The latitude (float)
     */
    public VosTable(
            float latitude) {
        this();
        setLatitude               (latitude              );
        if (dbg) System.out.println ("<br>in VosTable constructor 2"); // debug
    } // VosTable constructor

    /**
     * Instantiate a VosTable object and initialize the instance variables.
     * @param latitude                The latitude               (float)
     * @param longitude               The longitude              (float)
     * @param dateTime                The dateTime               (java.util.Date)
     * @param daynull                 The daynull                (String)
     * @param callsign                The callsign               (String)
     * @param country                 The country                (String)
     * @param platform                The platform               (String)
     * @param dataId                  The dataId                 (String)
     * @param qualityControl          The qualityControl         (String)
     * @param source1                 The source1                (String)
     * @param loadId                  The loadId                 (int)
     * @param dupflag                 The dupflag                (String)
     * @param atmosphericPressure     The atmosphericPressure    (float)
     * @param surfaceTemperature      The surfaceTemperature     (float)
     * @param surfaceTemperatureType  The surfaceTemperatureType (String)
     * @param drybulb                 The drybulb                (float)
     * @param wetbulb                 The wetbulb                (float)
     * @param wetbulbIce              The wetbulbIce             (String)
     * @param dewpoint                The dewpoint               (float)
     * @param cloudAmount             The cloudAmount            (String)
     * @param cloud1                  The cloud1                 (String)
     * @param cloud2                  The cloud2                 (String)
     * @param cloud3                  The cloud3                 (String)
     * @param cloud4                  The cloud4                 (String)
     * @param cloud5                  The cloud5                 (String)
     * @param visibilityCode          The visibilityCode         (String)
     * @param weatherCode             The weatherCode            (String)
     * @param swellDirection          The swellDirection         (int)
     * @param swellHeight             The swellHeight            (float)
     * @param swellPeriod             The swellPeriod            (int)
     * @param waveHeight              The waveHeight             (float)
     * @param wavePeriod              The wavePeriod             (int)
     * @param windDirection           The windDirection          (int)
     * @param windSpeed               The windSpeed              (float)
     * @param windSpeedType           The windSpeedType          (String)
     */
    public VosTable(
            float          latitude,
            float          longitude,
            java.util.Date dateTime,
            String         daynull,
            String         callsign,
            String         country,
            String         platform,
            String         dataId,
            String         qualityControl,
            String         source1,
            int            loadId,
            String         dupflag,
            float          atmosphericPressure,
            float          surfaceTemperature,
            String         surfaceTemperatureType,
            float          drybulb,
            float          wetbulb,
            String         wetbulbIce,
            float          dewpoint,
            String         cloudAmount,
            String         cloud1,
            String         cloud2,
            String         cloud3,
            String         cloud4,
            String         cloud5,
            String         visibilityCode,
            String         weatherCode,
            int            swellDirection,
            float          swellHeight,
            int            swellPeriod,
            float          waveHeight,
            int            wavePeriod,
            int            windDirection,
            float          windSpeed,
            String         windSpeedType) {
        this();
        setLatitude               (latitude              );
        setLongitude              (longitude             );
        setDateTime               (dateTime              );
        setDaynull                (daynull               );
        setCallsign               (callsign              );
        setCountry                (country               );
        setPlatform               (platform              );
        setDataId                 (dataId                );
        setQualityControl         (qualityControl        );
        setSource1                (source1               );
        setLoadId                 (loadId                );
        setDupflag                (dupflag               );
        setAtmosphericPressure    (atmosphericPressure   );
        setSurfaceTemperature     (surfaceTemperature    );
        setSurfaceTemperatureType (surfaceTemperatureType);
        setDrybulb                (drybulb               );
        setWetbulb                (wetbulb               );
        setWetbulbIce             (wetbulbIce            );
        setDewpoint               (dewpoint              );
        setCloudAmount            (cloudAmount           );
        setCloud1                 (cloud1                );
        setCloud2                 (cloud2                );
        setCloud3                 (cloud3                );
        setCloud4                 (cloud4                );
        setCloud5                 (cloud5                );
        setVisibilityCode         (visibilityCode        );
        setWeatherCode            (weatherCode           );
        setSwellDirection         (swellDirection        );
        setSwellHeight            (swellHeight           );
        setSwellPeriod            (swellPeriod           );
        setWaveHeight             (waveHeight            );
        setWavePeriod             (wavePeriod            );
        setWindDirection          (windDirection         );
        setWindSpeed              (windSpeed             );
        setWindSpeedType          (windSpeedType         );
        if (dbg) System.out.println ("<br>in VosTable constructor 3"); // debug
    } // VosTable constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setLatitude               (FLOATNULL);
        setLongitude              (FLOATNULL);
        setDateTime               (DATENULL );
        setDaynull                (CHARNULL );
        setCallsign               (CHARNULL );
        setCountry                (CHARNULL );
        setPlatform               (CHARNULL );
        setDataId                 (CHARNULL );
        setQualityControl         (CHARNULL );
        setSource1                (CHARNULL );
        setLoadId                 (INTNULL  );
        setDupflag                (CHARNULL );
        setAtmosphericPressure    (FLOATNULL);
        setSurfaceTemperature     (FLOATNULL);
        setSurfaceTemperatureType (CHARNULL );
        setDrybulb                (FLOATNULL);
        setWetbulb                (FLOATNULL);
        setWetbulbIce             (CHARNULL );
        setDewpoint               (FLOATNULL);
        setCloudAmount            (CHARNULL );
        setCloud1                 (CHARNULL );
        setCloud2                 (CHARNULL );
        setCloud3                 (CHARNULL );
        setCloud4                 (CHARNULL );
        setCloud5                 (CHARNULL );
        setVisibilityCode         (CHARNULL );
        setWeatherCode            (CHARNULL );
        setSwellDirection         (INTNULL  );
        setSwellHeight            (FLOATNULL);
        setSwellPeriod            (INTNULL  );
        setWaveHeight             (FLOATNULL);
        setWavePeriod             (INTNULL  );
        setWindDirection          (INTNULL  );
        setWindSpeed              (FLOATNULL);
        setWindSpeedType          (CHARNULL );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'latitude' class variable
     * @param  latitude (float)
     */
    public int setLatitude(float latitude) {
        try {
            if ( ((latitude == FLOATNULL) ||
                  (latitude == FLOATNULL2)) ||
                !((latitude < LATITUDE_MN) ||
                  (latitude > LATITUDE_MX)) ) {
                this.latitude = latitude;
                latitudeError = ERROR_NORMAL;
            } else {
                throw latitudeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Set the 'latitude' class variable
     * @param  latitude (Integer)
     */
    public int setLatitude(Integer latitude) {
        try {
            setLatitude(latitude.floatValue());
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Set the 'latitude' class variable
     * @param  latitude (Float)
     */
    public int setLatitude(Float latitude) {
        try {
            setLatitude(latitude.floatValue());
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Set the 'latitude' class variable
     * @param  latitude (String)
     */
    public int setLatitude(String latitude) {
        try {
            setLatitude(new Float(latitude).floatValue());
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Called when an exception has occured
     * @param  latitude (String)
     */
    private void setLatitudeError (float latitude, Exception e, int error) {
        this.latitude = latitude;
        latitudeErrorMessage = e.toString();
        latitudeError = error;
    } // method setLatitudeError


    /**
     * Set the 'longitude' class variable
     * @param  longitude (float)
     */
    public int setLongitude(float longitude) {
        try {
            if ( ((longitude == FLOATNULL) ||
                  (longitude == FLOATNULL2)) ||
                !((longitude < LONGITUDE_MN) ||
                  (longitude > LONGITUDE_MX)) ) {
                this.longitude = longitude;
                longitudeError = ERROR_NORMAL;
            } else {
                throw longitudeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Set the 'longitude' class variable
     * @param  longitude (Integer)
     */
    public int setLongitude(Integer longitude) {
        try {
            setLongitude(longitude.floatValue());
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Set the 'longitude' class variable
     * @param  longitude (Float)
     */
    public int setLongitude(Float longitude) {
        try {
            setLongitude(longitude.floatValue());
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Set the 'longitude' class variable
     * @param  longitude (String)
     */
    public int setLongitude(String longitude) {
        if (dbg) System.out.println("<br> VosTable : Longitude "+longitude);
        try {
            setLongitude(new Float(longitude).floatValue());
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Called when an exception has occured
     * @param  longitude (String)
     */
    private void setLongitudeError (float longitude, Exception e, int error) {
        this.longitude = longitude;
        longitudeErrorMessage = e.toString();
        longitudeError = error;
    } // method setLongitudeError


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
            if (dbg) System.out.println ("VosTable.setDateTime: dateTime1 = " + dateTime);
            if (dbg) System.out.println ("VosTable.setDateTime: dateTime2 = " + Timestamp.valueOf(dateTime));
            setDateTime(Timestamp.valueOf(dateTime));
            if (dbg) System.out.println ("VosTable.setDateTime: dateTime3 = " + getDateTime(""));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
            if (dbg) System.out.println("VosTable.setDateTime: error = " + dateTimeErrorMessage);
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
     * Set the 'daynull' class variable
     * @param  daynull (String)
     */
    public int setDaynull(String daynull) {
        try {
            this.daynull = daynull;
            if (this.daynull != CHARNULL) {
                this.daynull = stripCRLF(this.daynull.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>daynull = " + this.daynull);
        } catch (Exception e) {
            setDaynullError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return daynullError;
    } // method setDaynull

    /**
     * Called when an exception has occured
     * @param  daynull (String)
     */
    private void setDaynullError (String daynull, Exception e, int error) {
        this.daynull = daynull;
        daynullErrorMessage = e.toString();
        daynullError = error;
    } // method setDaynullError


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
     * Set the 'country' class variable
     * @param  country (String)
     */
    public int setCountry(String country) {
        try {
            this.country = country;
            if (this.country != CHARNULL) {
                this.country = stripCRLF(this.country.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>country = " + this.country);
        } catch (Exception e) {
            setCountryError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return countryError;
    } // method setCountry

    /**
     * Called when an exception has occured
     * @param  country (String)
     */
    private void setCountryError (String country, Exception e, int error) {
        this.country = country;
        countryErrorMessage = e.toString();
        countryError = error;
    } // method setCountryError


    /**
     * Set the 'platform' class variable
     * @param  platform (String)
     */
    public int setPlatform(String platform) {
        try {
            this.platform = platform;
            if (this.platform != CHARNULL) {
                this.platform = stripCRLF(this.platform.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>platform = " + this.platform);
        } catch (Exception e) {
            setPlatformError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return platformError;
    } // method setPlatform

    /**
     * Called when an exception has occured
     * @param  platform (String)
     */
    private void setPlatformError (String platform, Exception e, int error) {
        this.platform = platform;
        platformErrorMessage = e.toString();
        platformError = error;
    } // method setPlatformError


    /**
     * Set the 'dataId' class variable
     * @param  dataId (String)
     */
    public int setDataId(String dataId) {
        try {
            this.dataId = dataId;
            if (this.dataId != CHARNULL) {
                this.dataId = stripCRLF(this.dataId.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>dataId = " + this.dataId);
        } catch (Exception e) {
            setDataIdError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return dataIdError;
    } // method setDataId

    /**
     * Called when an exception has occured
     * @param  dataId (String)
     */
    private void setDataIdError (String dataId, Exception e, int error) {
        this.dataId = dataId;
        dataIdErrorMessage = e.toString();
        dataIdError = error;
    } // method setDataIdError


    /**
     * Set the 'qualityControl' class variable
     * @param  qualityControl (String)
     */
    public int setQualityControl(String qualityControl) {
        try {
            this.qualityControl = qualityControl;
            if (this.qualityControl != CHARNULL) {
                this.qualityControl = stripCRLF(this.qualityControl.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>qualityControl = " + this.qualityControl);
        } catch (Exception e) {
            setQualityControlError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return qualityControlError;
    } // method setQualityControl

    /**
     * Called when an exception has occured
     * @param  qualityControl (String)
     */
    private void setQualityControlError (String qualityControl, Exception e, int error) {
        this.qualityControl = qualityControl;
        qualityControlErrorMessage = e.toString();
        qualityControlError = error;
    } // method setQualityControlError


    /**
     * Set the 'source1' class variable
     * @param  source1 (String)
     */
    public int setSource1(String source1) {
        try {
            this.source1 = source1;
            if (this.source1 != CHARNULL) {
                this.source1 = stripCRLF(this.source1.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>source1 = " + this.source1);
        } catch (Exception e) {
            setSource1Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return source1Error;
    } // method setSource1

    /**
     * Called when an exception has occured
     * @param  source1 (String)
     */
    private void setSource1Error (String source1, Exception e, int error) {
        this.source1 = source1;
        source1ErrorMessage = e.toString();
        source1Error = error;
    } // method setSource1Error


    /**
     * Set the 'loadId' class variable
     * @param  loadId (int)
     */
    public int setLoadId(int loadId) {
        try {
            if ( ((loadId == INTNULL) ||
                  (loadId == INTNULL2)) ||
                !((loadId < LOAD_ID_MN) ||
                  (loadId > LOAD_ID_MX)) ) {
                this.loadId = loadId;
                loadIdError = ERROR_NORMAL;
            } else {
                throw loadIdOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLoadIdError(INTNULL, e, ERROR_LOCAL);
        } // try
        return loadIdError;
    } // method setLoadId

    /**
     * Set the 'loadId' class variable
     * @param  loadId (Integer)
     */
    public int setLoadId(Integer loadId) {
        try {
            setLoadId(loadId.intValue());
        } catch (Exception e) {
            setLoadIdError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return loadIdError;
    } // method setLoadId

    /**
     * Set the 'loadId' class variable
     * @param  loadId (Float)
     */
    public int setLoadId(Float loadId) {
        try {
            if (loadId.floatValue() == FLOATNULL) {
                setLoadId(INTNULL);
            } else {
                setLoadId(loadId.intValue());
            } // if (loadId.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setLoadIdError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return loadIdError;
    } // method setLoadId

    /**
     * Set the 'loadId' class variable
     * @param  loadId (String)
     */
    public int setLoadId(String loadId) {
        try {
            setLoadId(new Integer(loadId).intValue());
        } catch (Exception e) {
            setLoadIdError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return loadIdError;
    } // method setLoadId

    /**
     * Called when an exception has occured
     * @param  loadId (String)
     */
    private void setLoadIdError (int loadId, Exception e, int error) {
        this.loadId = loadId;
        loadIdErrorMessage = e.toString();
        loadIdError = error;
    } // method setLoadIdError


    /**
     * Set the 'dupflag' class variable
     * @param  dupflag (String)
     */
    public int setDupflag(String dupflag) {
        try {
            this.dupflag = dupflag;
            if (this.dupflag != CHARNULL) {
                this.dupflag = stripCRLF(this.dupflag.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>dupflag = " + this.dupflag);
        } catch (Exception e) {
            setDupflagError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return dupflagError;
    } // method setDupflag

    /**
     * Called when an exception has occured
     * @param  dupflag (String)
     */
    private void setDupflagError (String dupflag, Exception e, int error) {
        this.dupflag = dupflag;
        dupflagErrorMessage = e.toString();
        dupflagError = error;
    } // method setDupflagError


    /**
     * Set the 'atmosphericPressure' class variable
     * @param  atmosphericPressure (float)
     */
    public int setAtmosphericPressure(float atmosphericPressure) {
        try {
            if ( ((atmosphericPressure == FLOATNULL) ||
                  (atmosphericPressure == FLOATNULL2)) ||
                !((atmosphericPressure < ATMOSPHERIC_PRESSURE_MN) ||
                  (atmosphericPressure > ATMOSPHERIC_PRESSURE_MX)) ) {
                this.atmosphericPressure = atmosphericPressure;
                atmosphericPressureError = ERROR_NORMAL;
            } else {
                throw atmosphericPressureOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAtmosphericPressureError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return atmosphericPressureError;
    } // method setAtmosphericPressure

    /**
     * Set the 'atmosphericPressure' class variable
     * @param  atmosphericPressure (Integer)
     */
    public int setAtmosphericPressure(Integer atmosphericPressure) {
        try {
            setAtmosphericPressure(atmosphericPressure.floatValue());
        } catch (Exception e) {
            setAtmosphericPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return atmosphericPressureError;
    } // method setAtmosphericPressure

    /**
     * Set the 'atmosphericPressure' class variable
     * @param  atmosphericPressure (Float)
     */
    public int setAtmosphericPressure(Float atmosphericPressure) {
        try {
            setAtmosphericPressure(atmosphericPressure.floatValue());
        } catch (Exception e) {
            setAtmosphericPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return atmosphericPressureError;
    } // method setAtmosphericPressure

    /**
     * Set the 'atmosphericPressure' class variable
     * @param  atmosphericPressure (String)
     */
    public int setAtmosphericPressure(String atmosphericPressure) {
        try {
            setAtmosphericPressure(new Float(atmosphericPressure).floatValue());
        } catch (Exception e) {
            setAtmosphericPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return atmosphericPressureError;
    } // method setAtmosphericPressure

    /**
     * Called when an exception has occured
     * @param  atmosphericPressure (String)
     */
    private void setAtmosphericPressureError (float atmosphericPressure, Exception e, int error) {
        this.atmosphericPressure = atmosphericPressure;
        atmosphericPressureErrorMessage = e.toString();
        atmosphericPressureError = error;
    } // method setAtmosphericPressureError


    /**
     * Set the 'surfaceTemperature' class variable
     * @param  surfaceTemperature (float)
     */
    public int setSurfaceTemperature(float surfaceTemperature) {
        try {
            if ( ((surfaceTemperature == FLOATNULL) ||
                  (surfaceTemperature == FLOATNULL2)) ||
                !((surfaceTemperature < SURFACE_TEMPERATURE_MN) ||
                  (surfaceTemperature > SURFACE_TEMPERATURE_MX)) ) {
                this.surfaceTemperature = surfaceTemperature;
                surfaceTemperatureError = ERROR_NORMAL;
            } else {
                throw surfaceTemperatureOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSurfaceTemperatureError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return surfaceTemperatureError;
    } // method setSurfaceTemperature

    /**
     * Set the 'surfaceTemperature' class variable
     * @param  surfaceTemperature (Integer)
     */
    public int setSurfaceTemperature(Integer surfaceTemperature) {
        try {
            setSurfaceTemperature(surfaceTemperature.floatValue());
        } catch (Exception e) {
            setSurfaceTemperatureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return surfaceTemperatureError;
    } // method setSurfaceTemperature

    /**
     * Set the 'surfaceTemperature' class variable
     * @param  surfaceTemperature (Float)
     */
    public int setSurfaceTemperature(Float surfaceTemperature) {
        try {
            setSurfaceTemperature(surfaceTemperature.floatValue());
        } catch (Exception e) {
            setSurfaceTemperatureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return surfaceTemperatureError;
    } // method setSurfaceTemperature

    /**
     * Set the 'surfaceTemperature' class variable
     * @param  surfaceTemperature (String)
     */
    public int setSurfaceTemperature(String surfaceTemperature) {
        try {
            setSurfaceTemperature(new Float(surfaceTemperature).floatValue());
        } catch (Exception e) {
            setSurfaceTemperatureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return surfaceTemperatureError;
    } // method setSurfaceTemperature

    /**
     * Called when an exception has occured
     * @param  surfaceTemperature (String)
     */
    private void setSurfaceTemperatureError (float surfaceTemperature, Exception e, int error) {
        this.surfaceTemperature = surfaceTemperature;
        surfaceTemperatureErrorMessage = e.toString();
        surfaceTemperatureError = error;
    } // method setSurfaceTemperatureError


    /**
     * Set the 'surfaceTemperatureType' class variable
     * @param  surfaceTemperatureType (String)
     */
    public int setSurfaceTemperatureType(String surfaceTemperatureType) {
        try {
            this.surfaceTemperatureType = surfaceTemperatureType;
            if (this.surfaceTemperatureType != CHARNULL) {
                this.surfaceTemperatureType = stripCRLF(this.surfaceTemperatureType.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>surfaceTemperatureType = " + this.surfaceTemperatureType);
        } catch (Exception e) {
            setSurfaceTemperatureTypeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return surfaceTemperatureTypeError;
    } // method setSurfaceTemperatureType

    /**
     * Called when an exception has occured
     * @param  surfaceTemperatureType (String)
     */
    private void setSurfaceTemperatureTypeError (String surfaceTemperatureType, Exception e, int error) {
        this.surfaceTemperatureType = surfaceTemperatureType;
        surfaceTemperatureTypeErrorMessage = e.toString();
        surfaceTemperatureTypeError = error;
    } // method setSurfaceTemperatureTypeError


    /**
     * Set the 'drybulb' class variable
     * @param  drybulb (float)
     */
    public int setDrybulb(float drybulb) {
        try {
            if ( ((drybulb == FLOATNULL) ||
                  (drybulb == FLOATNULL2)) ||
                !((drybulb < DRYBULB_MN) ||
                  (drybulb > DRYBULB_MX)) ) {
                this.drybulb = drybulb;
                drybulbError = ERROR_NORMAL;
            } else {
                throw drybulbOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDrybulbError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return drybulbError;
    } // method setDrybulb

    /**
     * Set the 'drybulb' class variable
     * @param  drybulb (Integer)
     */
    public int setDrybulb(Integer drybulb) {
        try {
            setDrybulb(drybulb.floatValue());
        } catch (Exception e) {
            setDrybulbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return drybulbError;
    } // method setDrybulb

    /**
     * Set the 'drybulb' class variable
     * @param  drybulb (Float)
     */
    public int setDrybulb(Float drybulb) {
        try {
            setDrybulb(drybulb.floatValue());
        } catch (Exception e) {
            setDrybulbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return drybulbError;
    } // method setDrybulb

    /**
     * Set the 'drybulb' class variable
     * @param  drybulb (String)
     */
    public int setDrybulb(String drybulb) {
        try {
            setDrybulb(new Float(drybulb).floatValue());
        } catch (Exception e) {
            setDrybulbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return drybulbError;
    } // method setDrybulb

    /**
     * Called when an exception has occured
     * @param  drybulb (String)
     */
    private void setDrybulbError (float drybulb, Exception e, int error) {
        this.drybulb = drybulb;
        drybulbErrorMessage = e.toString();
        drybulbError = error;
    } // method setDrybulbError


    /**
     * Set the 'wetbulb' class variable
     * @param  wetbulb (float)
     */
    public int setWetbulb(float wetbulb) {
        try {
            if ( ((wetbulb == FLOATNULL) ||
                  (wetbulb == FLOATNULL2)) ||
                !((wetbulb < WETBULB_MN) ||
                  (wetbulb > WETBULB_MX)) ) {
                this.wetbulb = wetbulb;
                wetbulbError = ERROR_NORMAL;
            } else {
                throw wetbulbOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWetbulbError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return wetbulbError;
    } // method setWetbulb

    /**
     * Set the 'wetbulb' class variable
     * @param  wetbulb (Integer)
     */
    public int setWetbulb(Integer wetbulb) {
        try {
            setWetbulb(wetbulb.floatValue());
        } catch (Exception e) {
            setWetbulbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return wetbulbError;
    } // method setWetbulb

    /**
     * Set the 'wetbulb' class variable
     * @param  wetbulb (Float)
     */
    public int setWetbulb(Float wetbulb) {
        try {
            setWetbulb(wetbulb.floatValue());
        } catch (Exception e) {
            setWetbulbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return wetbulbError;
    } // method setWetbulb

    /**
     * Set the 'wetbulb' class variable
     * @param  wetbulb (String)
     */
    public int setWetbulb(String wetbulb) {
        try {
            setWetbulb(new Float(wetbulb).floatValue());
        } catch (Exception e) {
            setWetbulbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return wetbulbError;
    } // method setWetbulb

    /**
     * Called when an exception has occured
     * @param  wetbulb (String)
     */
    private void setWetbulbError (float wetbulb, Exception e, int error) {
        this.wetbulb = wetbulb;
        wetbulbErrorMessage = e.toString();
        wetbulbError = error;
    } // method setWetbulbError


    /**
     * Set the 'wetbulbIce' class variable
     * @param  wetbulbIce (String)
     */
    public int setWetbulbIce(String wetbulbIce) {
        try {
            this.wetbulbIce = wetbulbIce;
            if (this.wetbulbIce != CHARNULL) {
                this.wetbulbIce = stripCRLF(this.wetbulbIce.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>wetbulbIce = " + this.wetbulbIce);
        } catch (Exception e) {
            setWetbulbIceError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return wetbulbIceError;
    } // method setWetbulbIce

    /**
     * Called when an exception has occured
     * @param  wetbulbIce (String)
     */
    private void setWetbulbIceError (String wetbulbIce, Exception e, int error) {
        this.wetbulbIce = wetbulbIce;
        wetbulbIceErrorMessage = e.toString();
        wetbulbIceError = error;
    } // method setWetbulbIceError


    /**
     * Set the 'dewpoint' class variable
     * @param  dewpoint (float)
     */
    public int setDewpoint(float dewpoint) {
        try {
            if ( ((dewpoint == FLOATNULL) ||
                  (dewpoint == FLOATNULL2)) ||
                !((dewpoint < DEWPOINT_MN) ||
                  (dewpoint > DEWPOINT_MX)) ) {
                this.dewpoint = dewpoint;
                dewpointError = ERROR_NORMAL;
            } else {
                throw dewpointOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDewpointError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return dewpointError;
    } // method setDewpoint

    /**
     * Set the 'dewpoint' class variable
     * @param  dewpoint (Integer)
     */
    public int setDewpoint(Integer dewpoint) {
        try {
            setDewpoint(dewpoint.floatValue());
        } catch (Exception e) {
            setDewpointError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dewpointError;
    } // method setDewpoint

    /**
     * Set the 'dewpoint' class variable
     * @param  dewpoint (Float)
     */
    public int setDewpoint(Float dewpoint) {
        try {
            setDewpoint(dewpoint.floatValue());
        } catch (Exception e) {
            setDewpointError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dewpointError;
    } // method setDewpoint

    /**
     * Set the 'dewpoint' class variable
     * @param  dewpoint (String)
     */
    public int setDewpoint(String dewpoint) {
        try {
            setDewpoint(new Float(dewpoint).floatValue());
        } catch (Exception e) {
            setDewpointError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dewpointError;
    } // method setDewpoint

    /**
     * Called when an exception has occured
     * @param  dewpoint (String)
     */
    private void setDewpointError (float dewpoint, Exception e, int error) {
        this.dewpoint = dewpoint;
        dewpointErrorMessage = e.toString();
        dewpointError = error;
    } // method setDewpointError


    /**
     * Set the 'cloudAmount' class variable
     * @param  cloudAmount (String)
     */
    public int setCloudAmount(String cloudAmount) {
        try {
            this.cloudAmount = cloudAmount;
            if (this.cloudAmount != CHARNULL) {
                this.cloudAmount = stripCRLF(this.cloudAmount.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>cloudAmount = " + this.cloudAmount);
        } catch (Exception e) {
            setCloudAmountError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return cloudAmountError;
    } // method setCloudAmount

    /**
     * Called when an exception has occured
     * @param  cloudAmount (String)
     */
    private void setCloudAmountError (String cloudAmount, Exception e, int error) {
        this.cloudAmount = cloudAmount;
        cloudAmountErrorMessage = e.toString();
        cloudAmountError = error;
    } // method setCloudAmountError


    /**
     * Set the 'cloud1' class variable
     * @param  cloud1 (String)
     */
    public int setCloud1(String cloud1) {
        try {
            this.cloud1 = cloud1;
            if (this.cloud1 != CHARNULL) {
                this.cloud1 = stripCRLF(this.cloud1.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>cloud1 = " + this.cloud1);
        } catch (Exception e) {
            setCloud1Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return cloud1Error;
    } // method setCloud1

    /**
     * Called when an exception has occured
     * @param  cloud1 (String)
     */
    private void setCloud1Error (String cloud1, Exception e, int error) {
        this.cloud1 = cloud1;
        cloud1ErrorMessage = e.toString();
        cloud1Error = error;
    } // method setCloud1Error


    /**
     * Set the 'cloud2' class variable
     * @param  cloud2 (String)
     */
    public int setCloud2(String cloud2) {
        try {
            this.cloud2 = cloud2;
            if (this.cloud2 != CHARNULL) {
                this.cloud2 = stripCRLF(this.cloud2.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>cloud2 = " + this.cloud2);
        } catch (Exception e) {
            setCloud2Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return cloud2Error;
    } // method setCloud2

    /**
     * Called when an exception has occured
     * @param  cloud2 (String)
     */
    private void setCloud2Error (String cloud2, Exception e, int error) {
        this.cloud2 = cloud2;
        cloud2ErrorMessage = e.toString();
        cloud2Error = error;
    } // method setCloud2Error


    /**
     * Set the 'cloud3' class variable
     * @param  cloud3 (String)
     */
    public int setCloud3(String cloud3) {
        try {
            this.cloud3 = cloud3;
            if (this.cloud3 != CHARNULL) {
                this.cloud3 = stripCRLF(this.cloud3.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>cloud3 = " + this.cloud3);
        } catch (Exception e) {
            setCloud3Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return cloud3Error;
    } // method setCloud3

    /**
     * Called when an exception has occured
     * @param  cloud3 (String)
     */
    private void setCloud3Error (String cloud3, Exception e, int error) {
        this.cloud3 = cloud3;
        cloud3ErrorMessage = e.toString();
        cloud3Error = error;
    } // method setCloud3Error


    /**
     * Set the 'cloud4' class variable
     * @param  cloud4 (String)
     */
    public int setCloud4(String cloud4) {
        try {
            this.cloud4 = cloud4;
            if (this.cloud4 != CHARNULL) {
                this.cloud4 = stripCRLF(this.cloud4.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>cloud4 = " + this.cloud4);
        } catch (Exception e) {
            setCloud4Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return cloud4Error;
    } // method setCloud4

    /**
     * Called when an exception has occured
     * @param  cloud4 (String)
     */
    private void setCloud4Error (String cloud4, Exception e, int error) {
        this.cloud4 = cloud4;
        cloud4ErrorMessage = e.toString();
        cloud4Error = error;
    } // method setCloud4Error


    /**
     * Set the 'cloud5' class variable
     * @param  cloud5 (String)
     */
    public int setCloud5(String cloud5) {
        try {
            this.cloud5 = cloud5;
            if (this.cloud5 != CHARNULL) {
                this.cloud5 = stripCRLF(this.cloud5.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>cloud5 = " + this.cloud5);
        } catch (Exception e) {
            setCloud5Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return cloud5Error;
    } // method setCloud5

    /**
     * Called when an exception has occured
     * @param  cloud5 (String)
     */
    private void setCloud5Error (String cloud5, Exception e, int error) {
        this.cloud5 = cloud5;
        cloud5ErrorMessage = e.toString();
        cloud5Error = error;
    } // method setCloud5Error


    /**
     * Set the 'visibilityCode' class variable
     * @param  visibilityCode (String)
     */
    public int setVisibilityCode(String visibilityCode) {
        try {
            this.visibilityCode = visibilityCode;
            if (this.visibilityCode != CHARNULL) {
                this.visibilityCode = stripCRLF(this.visibilityCode.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>visibilityCode = " + this.visibilityCode);
        } catch (Exception e) {
            setVisibilityCodeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return visibilityCodeError;
    } // method setVisibilityCode

    /**
     * Called when an exception has occured
     * @param  visibilityCode (String)
     */
    private void setVisibilityCodeError (String visibilityCode, Exception e, int error) {
        this.visibilityCode = visibilityCode;
        visibilityCodeErrorMessage = e.toString();
        visibilityCodeError = error;
    } // method setVisibilityCodeError


    /**
     * Set the 'weatherCode' class variable
     * @param  weatherCode (String)
     */
    public int setWeatherCode(String weatherCode) {
        try {
            this.weatherCode = weatherCode;
            if (this.weatherCode != CHARNULL) {
                this.weatherCode = stripCRLF(this.weatherCode.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>weatherCode = " + this.weatherCode);
        } catch (Exception e) {
            setWeatherCodeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return weatherCodeError;
    } // method setWeatherCode

    /**
     * Called when an exception has occured
     * @param  weatherCode (String)
     */
    private void setWeatherCodeError (String weatherCode, Exception e, int error) {
        this.weatherCode = weatherCode;
        weatherCodeErrorMessage = e.toString();
        weatherCodeError = error;
    } // method setWeatherCodeError


    /**
     * Set the 'swellDirection' class variable
     * @param  swellDirection (int)
     */
    public int setSwellDirection(int swellDirection) {
        try {
            if ( ((swellDirection == INTNULL) ||
                  (swellDirection == INTNULL2)) ||
                !((swellDirection < SWELL_DIRECTION_MN) ||
                  (swellDirection > SWELL_DIRECTION_MX)) ) {
                this.swellDirection = swellDirection;
                swellDirectionError = ERROR_NORMAL;
            } else {
                throw swellDirectionOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSwellDirectionError(INTNULL, e, ERROR_LOCAL);
        } // try
        return swellDirectionError;
    } // method setSwellDirection

    /**
     * Set the 'swellDirection' class variable
     * @param  swellDirection (Integer)
     */
    public int setSwellDirection(Integer swellDirection) {
        try {
            setSwellDirection(swellDirection.intValue());
        } catch (Exception e) {
            setSwellDirectionError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return swellDirectionError;
    } // method setSwellDirection

    /**
     * Set the 'swellDirection' class variable
     * @param  swellDirection (Float)
     */
    public int setSwellDirection(Float swellDirection) {
        try {
            if (swellDirection.floatValue() == FLOATNULL) {
                setSwellDirection(INTNULL);
            } else {
                setSwellDirection(swellDirection.intValue());
            } // if (swellDirection.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSwellDirectionError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return swellDirectionError;
    } // method setSwellDirection

    /**
     * Set the 'swellDirection' class variable
     * @param  swellDirection (String)
     */
    public int setSwellDirection(String swellDirection) {
        try {
            setSwellDirection(new Integer(swellDirection).intValue());
        } catch (Exception e) {
            setSwellDirectionError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return swellDirectionError;
    } // method setSwellDirection

    /**
     * Called when an exception has occured
     * @param  swellDirection (String)
     */
    private void setSwellDirectionError (int swellDirection, Exception e, int error) {
        this.swellDirection = swellDirection;
        swellDirectionErrorMessage = e.toString();
        swellDirectionError = error;
    } // method setSwellDirectionError


    /**
     * Set the 'swellHeight' class variable
     * @param  swellHeight (float)
     */
    public int setSwellHeight(float swellHeight) {
        try {
            if ( ((swellHeight == FLOATNULL) ||
                  (swellHeight == FLOATNULL2)) ||
                !((swellHeight < SWELL_HEIGHT_MN) ||
                  (swellHeight > SWELL_HEIGHT_MX)) ) {
                this.swellHeight = swellHeight;
                swellHeightError = ERROR_NORMAL;
            } else {
                throw swellHeightOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSwellHeightError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return swellHeightError;
    } // method setSwellHeight

    /**
     * Set the 'swellHeight' class variable
     * @param  swellHeight (Integer)
     */
    public int setSwellHeight(Integer swellHeight) {
        try {
            setSwellHeight(swellHeight.floatValue());
        } catch (Exception e) {
            setSwellHeightError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return swellHeightError;
    } // method setSwellHeight

    /**
     * Set the 'swellHeight' class variable
     * @param  swellHeight (Float)
     */
    public int setSwellHeight(Float swellHeight) {
        try {
            setSwellHeight(swellHeight.floatValue());
        } catch (Exception e) {
            setSwellHeightError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return swellHeightError;
    } // method setSwellHeight

    /**
     * Set the 'swellHeight' class variable
     * @param  swellHeight (String)
     */
    public int setSwellHeight(String swellHeight) {
        try {
            setSwellHeight(new Float(swellHeight).floatValue());
        } catch (Exception e) {
            setSwellHeightError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return swellHeightError;
    } // method setSwellHeight

    /**
     * Called when an exception has occured
     * @param  swellHeight (String)
     */
    private void setSwellHeightError (float swellHeight, Exception e, int error) {
        this.swellHeight = swellHeight;
        swellHeightErrorMessage = e.toString();
        swellHeightError = error;
    } // method setSwellHeightError


    /**
     * Set the 'swellPeriod' class variable
     * @param  swellPeriod (int)
     */
    public int setSwellPeriod(int swellPeriod) {
        try {
            if ( ((swellPeriod == INTNULL) ||
                  (swellPeriod == INTNULL2)) ||
                !((swellPeriod < SWELL_PERIOD_MN) ||
                  (swellPeriod > SWELL_PERIOD_MX)) ) {
                this.swellPeriod = swellPeriod;
                swellPeriodError = ERROR_NORMAL;
            } else {
                throw swellPeriodOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSwellPeriodError(INTNULL, e, ERROR_LOCAL);
        } // try
        return swellPeriodError;
    } // method setSwellPeriod

    /**
     * Set the 'swellPeriod' class variable
     * @param  swellPeriod (Integer)
     */
    public int setSwellPeriod(Integer swellPeriod) {
        try {
            setSwellPeriod(swellPeriod.intValue());
        } catch (Exception e) {
            setSwellPeriodError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return swellPeriodError;
    } // method setSwellPeriod

    /**
     * Set the 'swellPeriod' class variable
     * @param  swellPeriod (Float)
     */
    public int setSwellPeriod(Float swellPeriod) {
        try {
            if (swellPeriod.floatValue() == FLOATNULL) {
                setSwellPeriod(INTNULL);
            } else {
                setSwellPeriod(swellPeriod.intValue());
            } // if (swellPeriod.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSwellPeriodError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return swellPeriodError;
    } // method setSwellPeriod

    /**
     * Set the 'swellPeriod' class variable
     * @param  swellPeriod (String)
     */
    public int setSwellPeriod(String swellPeriod) {
        try {
            setSwellPeriod(new Integer(swellPeriod).intValue());
        } catch (Exception e) {
            setSwellPeriodError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return swellPeriodError;
    } // method setSwellPeriod

    /**
     * Called when an exception has occured
     * @param  swellPeriod (String)
     */
    private void setSwellPeriodError (int swellPeriod, Exception e, int error) {
        this.swellPeriod = swellPeriod;
        swellPeriodErrorMessage = e.toString();
        swellPeriodError = error;
    } // method setSwellPeriodError


    /**
     * Set the 'waveHeight' class variable
     * @param  waveHeight (float)
     */
    public int setWaveHeight(float waveHeight) {
        try {
            if ( ((waveHeight == FLOATNULL) ||
                  (waveHeight == FLOATNULL2)) ||
                !((waveHeight < WAVE_HEIGHT_MN) ||
                  (waveHeight > WAVE_HEIGHT_MX)) ) {
                this.waveHeight = waveHeight;
                waveHeightError = ERROR_NORMAL;
            } else {
                throw waveHeightOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWaveHeightError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return waveHeightError;
    } // method setWaveHeight

    /**
     * Set the 'waveHeight' class variable
     * @param  waveHeight (Integer)
     */
    public int setWaveHeight(Integer waveHeight) {
        try {
            setWaveHeight(waveHeight.floatValue());
        } catch (Exception e) {
            setWaveHeightError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return waveHeightError;
    } // method setWaveHeight

    /**
     * Set the 'waveHeight' class variable
     * @param  waveHeight (Float)
     */
    public int setWaveHeight(Float waveHeight) {
        try {
            setWaveHeight(waveHeight.floatValue());
        } catch (Exception e) {
            setWaveHeightError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return waveHeightError;
    } // method setWaveHeight

    /**
     * Set the 'waveHeight' class variable
     * @param  waveHeight (String)
     */
    public int setWaveHeight(String waveHeight) {
        try {
            setWaveHeight(new Float(waveHeight).floatValue());
        } catch (Exception e) {
            setWaveHeightError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return waveHeightError;
    } // method setWaveHeight

    /**
     * Called when an exception has occured
     * @param  waveHeight (String)
     */
    private void setWaveHeightError (float waveHeight, Exception e, int error) {
        this.waveHeight = waveHeight;
        waveHeightErrorMessage = e.toString();
        waveHeightError = error;
    } // method setWaveHeightError


    /**
     * Set the 'wavePeriod' class variable
     * @param  wavePeriod (int)
     */
    public int setWavePeriod(int wavePeriod) {
        try {
            if ( ((wavePeriod == INTNULL) ||
                  (wavePeriod == INTNULL2)) ||
                !((wavePeriod < WAVE_PERIOD_MN) ||
                  (wavePeriod > WAVE_PERIOD_MX)) ) {
                this.wavePeriod = wavePeriod;
                wavePeriodError = ERROR_NORMAL;
            } else {
                throw wavePeriodOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWavePeriodError(INTNULL, e, ERROR_LOCAL);
        } // try
        return wavePeriodError;
    } // method setWavePeriod

    /**
     * Set the 'wavePeriod' class variable
     * @param  wavePeriod (Integer)
     */
    public int setWavePeriod(Integer wavePeriod) {
        try {
            setWavePeriod(wavePeriod.intValue());
        } catch (Exception e) {
            setWavePeriodError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return wavePeriodError;
    } // method setWavePeriod

    /**
     * Set the 'wavePeriod' class variable
     * @param  wavePeriod (Float)
     */
    public int setWavePeriod(Float wavePeriod) {
        try {
            if (wavePeriod.floatValue() == FLOATNULL) {
                setWavePeriod(INTNULL);
            } else {
                setWavePeriod(wavePeriod.intValue());
            } // if (wavePeriod.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWavePeriodError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return wavePeriodError;
    } // method setWavePeriod

    /**
     * Set the 'wavePeriod' class variable
     * @param  wavePeriod (String)
     */
    public int setWavePeriod(String wavePeriod) {
        try {
            setWavePeriod(new Integer(wavePeriod).intValue());
        } catch (Exception e) {
            setWavePeriodError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return wavePeriodError;
    } // method setWavePeriod

    /**
     * Called when an exception has occured
     * @param  wavePeriod (String)
     */
    private void setWavePeriodError (int wavePeriod, Exception e, int error) {
        this.wavePeriod = wavePeriod;
        wavePeriodErrorMessage = e.toString();
        wavePeriodError = error;
    } // method setWavePeriodError


    /**
     * Set the 'windDirection' class variable
     * @param  windDirection (int)
     */
    public int setWindDirection(int windDirection) {
        try {
            if ( ((windDirection == INTNULL) ||
                  (windDirection == INTNULL2)) ||
                !((windDirection < WIND_DIRECTION_MN) ||
                  (windDirection > WIND_DIRECTION_MX)) ) {
                this.windDirection = windDirection;
                windDirectionError = ERROR_NORMAL;
            } else {
                throw windDirectionOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindDirectionError(INTNULL, e, ERROR_LOCAL);
        } // try
        return windDirectionError;
    } // method setWindDirection

    /**
     * Set the 'windDirection' class variable
     * @param  windDirection (Integer)
     */
    public int setWindDirection(Integer windDirection) {
        try {
            setWindDirection(windDirection.intValue());
        } catch (Exception e) {
            setWindDirectionError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windDirectionError;
    } // method setWindDirection

    /**
     * Set the 'windDirection' class variable
     * @param  windDirection (Float)
     */
    public int setWindDirection(Float windDirection) {
        try {
            if (windDirection.floatValue() == FLOATNULL) {
                setWindDirection(INTNULL);
            } else {
                setWindDirection(windDirection.intValue());
            } // if (windDirection.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWindDirectionError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windDirectionError;
    } // method setWindDirection

    /**
     * Set the 'windDirection' class variable
     * @param  windDirection (String)
     */
    public int setWindDirection(String windDirection) {
        try {
            setWindDirection(new Integer(windDirection).intValue());
        } catch (Exception e) {
            setWindDirectionError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windDirectionError;
    } // method setWindDirection

    /**
     * Called when an exception has occured
     * @param  windDirection (String)
     */
    private void setWindDirectionError (int windDirection, Exception e, int error) {
        this.windDirection = windDirection;
        windDirectionErrorMessage = e.toString();
        windDirectionError = error;
    } // method setWindDirectionError


    /**
     * Set the 'windSpeed' class variable
     * @param  windSpeed (float)
     */
    public int setWindSpeed(float windSpeed) {
        try {
            if ( ((windSpeed == FLOATNULL) ||
                  (windSpeed == FLOATNULL2)) ||
                !((windSpeed < WIND_SPEED_MN) ||
                  (windSpeed > WIND_SPEED_MX)) ) {
                this.windSpeed = windSpeed;
                windSpeedError = ERROR_NORMAL;
            } else {
                throw windSpeedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindSpeedError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return windSpeedError;
    } // method setWindSpeed

    /**
     * Set the 'windSpeed' class variable
     * @param  windSpeed (Integer)
     */
    public int setWindSpeed(Integer windSpeed) {
        try {
            setWindSpeed(windSpeed.floatValue());
        } catch (Exception e) {
            setWindSpeedError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedError;
    } // method setWindSpeed

    /**
     * Set the 'windSpeed' class variable
     * @param  windSpeed (Float)
     */
    public int setWindSpeed(Float windSpeed) {
        try {
            setWindSpeed(windSpeed.floatValue());
        } catch (Exception e) {
            setWindSpeedError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedError;
    } // method setWindSpeed

    /**
     * Set the 'windSpeed' class variable
     * @param  windSpeed (String)
     */
    public int setWindSpeed(String windSpeed) {
        try {
            setWindSpeed(new Float(windSpeed).floatValue());
        } catch (Exception e) {
            setWindSpeedError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedError;
    } // method setWindSpeed

    /**
     * Called when an exception has occured
     * @param  windSpeed (String)
     */
    private void setWindSpeedError (float windSpeed, Exception e, int error) {
        this.windSpeed = windSpeed;
        windSpeedErrorMessage = e.toString();
        windSpeedError = error;
    } // method setWindSpeedError


    /**
     * Set the 'windSpeedType' class variable
     * @param  windSpeedType (String)
     */
    public int setWindSpeedType(String windSpeedType) {
        try {
            this.windSpeedType = windSpeedType;
            if (this.windSpeedType != CHARNULL) {
                this.windSpeedType = stripCRLF(this.windSpeedType.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>windSpeedType = " + this.windSpeedType);
        } catch (Exception e) {
            setWindSpeedTypeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return windSpeedTypeError;
    } // method setWindSpeedType

    /**
     * Called when an exception has occured
     * @param  windSpeedType (String)
     */
    private void setWindSpeedTypeError (String windSpeedType, Exception e, int error) {
        this.windSpeedType = windSpeedType;
        windSpeedTypeErrorMessage = e.toString();
        windSpeedTypeError = error;
    } // method setWindSpeedTypeError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'latitude' class variable
     * @return latitude (float)
     */
    public float getLatitude() {
        return latitude;
    } // method getLatitude

    /**
     * Return the 'latitude' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLatitude methods
     * @return latitude (String)
     */
    public String getLatitude(String s) {
        return (!(latitude == FLOATNULL) ? new Float(latitude).toString() : "");
    } // method getLatitude


    /**
     * Return the 'longitude' class variable
     * @return longitude (float)
     */
    public float getLongitude() {
        return longitude;
    } // method getLongitude

    /**
     * Return the 'longitude' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLongitude methods
     * @return longitude (String)
     */
    public String getLongitude(String s) {
        return (!(longitude == FLOATNULL) ? new Float(longitude).toString() : "");
    } // method getLongitude


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
     * Return the 'daynull' class variable
     * @return daynull (String)
     */
    public String getDaynull() {
        return daynull;
    } // method getDaynull

    /**
     * Return the 'daynull' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDaynull methods
     * @return daynull (String)
     */
    public String getDaynull(String s) {
        return (daynull != CHARNULL ? daynull.replace('"','\'') : "");
    } // method getDaynull


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
     * Return the 'country' class variable
     * @return country (String)
     */
    public String getCountry() {
        return country;
    } // method getCountry

    /**
     * Return the 'country' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCountry methods
     * @return country (String)
     */
    public String getCountry(String s) {
        return (country != CHARNULL ? country.replace('"','\'') : "");
    } // method getCountry


    /**
     * Return the 'platform' class variable
     * @return platform (String)
     */
    public String getPlatform() {
        return platform;
    } // method getPlatform

    /**
     * Return the 'platform' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlatform methods
     * @return platform (String)
     */
    public String getPlatform(String s) {
        return (platform != CHARNULL ? platform.replace('"','\'') : "");
    } // method getPlatform


    /**
     * Return the 'dataId' class variable
     * @return dataId (String)
     */
    public String getDataId() {
        return dataId;
    } // method getDataId

    /**
     * Return the 'dataId' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataId methods
     * @return dataId (String)
     */
    public String getDataId(String s) {
        return (dataId != CHARNULL ? dataId.replace('"','\'') : "");
    } // method getDataId


    /**
     * Return the 'qualityControl' class variable
     * @return qualityControl (String)
     */
    public String getQualityControl() {
        return qualityControl;
    } // method getQualityControl

    /**
     * Return the 'qualityControl' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getQualityControl methods
     * @return qualityControl (String)
     */
    public String getQualityControl(String s) {
        return (qualityControl != CHARNULL ? qualityControl.replace('"','\'') : "");
    } // method getQualityControl


    /**
     * Return the 'source1' class variable
     * @return source1 (String)
     */
    public String getSource1() {
        return source1;
    } // method getSource1

    /**
     * Return the 'source1' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSource1 methods
     * @return source1 (String)
     */
    public String getSource1(String s) {
        return (source1 != CHARNULL ? source1.replace('"','\'') : "");
    } // method getSource1


    /**
     * Return the 'loadId' class variable
     * @return loadId (int)
     */
    public int getLoadId() {
        return loadId;
    } // method getLoadId

    /**
     * Return the 'loadId' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLoadId methods
     * @return loadId (String)
     */
    public String getLoadId(String s) {
        return (!(loadId == INTNULL) ? new Integer(loadId).toString() : "");
    } // method getLoadId


    /**
     * Return the 'dupflag' class variable
     * @return dupflag (String)
     */
    public String getDupflag() {
        return dupflag;
    } // method getDupflag

    /**
     * Return the 'dupflag' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDupflag methods
     * @return dupflag (String)
     */
    public String getDupflag(String s) {
        return (dupflag != CHARNULL ? dupflag.replace('"','\'') : "");
    } // method getDupflag


    /**
     * Return the 'atmosphericPressure' class variable
     * @return atmosphericPressure (float)
     */
    public float getAtmosphericPressure() {
        return atmosphericPressure;
    } // method getAtmosphericPressure

    /**
     * Return the 'atmosphericPressure' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAtmosphericPressure methods
     * @return atmosphericPressure (String)
     */
    public String getAtmosphericPressure(String s) {
        return (!(atmosphericPressure == FLOATNULL) ? new Float(atmosphericPressure).toString() : "");
    } // method getAtmosphericPressure


    /**
     * Return the 'surfaceTemperature' class variable
     * @return surfaceTemperature (float)
     */
    public float getSurfaceTemperature() {
        return surfaceTemperature;
    } // method getSurfaceTemperature

    /**
     * Return the 'surfaceTemperature' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurfaceTemperature methods
     * @return surfaceTemperature (String)
     */
    public String getSurfaceTemperature(String s) {
        return (!(surfaceTemperature == FLOATNULL) ? new Float(surfaceTemperature).toString() : "");
    } // method getSurfaceTemperature


    /**
     * Return the 'surfaceTemperatureType' class variable
     * @return surfaceTemperatureType (String)
     */
    public String getSurfaceTemperatureType() {
        return surfaceTemperatureType;
    } // method getSurfaceTemperatureType

    /**
     * Return the 'surfaceTemperatureType' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurfaceTemperatureType methods
     * @return surfaceTemperatureType (String)
     */
    public String getSurfaceTemperatureType(String s) {
        return (surfaceTemperatureType != CHARNULL ? surfaceTemperatureType.replace('"','\'') : "");
    } // method getSurfaceTemperatureType


    /**
     * Return the 'drybulb' class variable
     * @return drybulb (float)
     */
    public float getDrybulb() {
        return drybulb;
    } // method getDrybulb

    /**
     * Return the 'drybulb' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDrybulb methods
     * @return drybulb (String)
     */
    public String getDrybulb(String s) {
        return (!(drybulb == FLOATNULL) ? new Float(drybulb).toString() : "");
    } // method getDrybulb


    /**
     * Return the 'wetbulb' class variable
     * @return wetbulb (float)
     */
    public float getWetbulb() {
        return wetbulb;
    } // method getWetbulb

    /**
     * Return the 'wetbulb' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWetbulb methods
     * @return wetbulb (String)
     */
    public String getWetbulb(String s) {
        return (wetbulb != FLOATNULL ? new Float(wetbulb).toString() : "");
    } // method getWetbulb


    /**
     * Return the 'wetbulbIce' class variable
     * @return wetbulbIce (String)
     */
    public String getWetbulbIce() {
        return wetbulbIce;
    } // method getWetbulbIce

    /**
     * Return the 'wetbulbIce' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWetbulbIce methods
     * @return wetbulbIce (String)
     */
    public String getWetbulbIce(String s) {
        return (wetbulbIce != CHARNULL ? wetbulbIce.replace('"','\'') : "");
    } // method getWetbulbIce


    /**
     * Return the 'dewpoint' class variable
     * @return dewpoint (float)
     */
    public float getDewpoint() {
        return dewpoint;
    } // method getDewpoint

    /**
     * Return the 'dewpoint' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDewpoint methods
     * @return dewpoint (String)
     */
    public String getDewpoint(String s) {
        return (!(dewpoint == FLOATNULL) ? new Float(dewpoint).toString() : "");
    } // method getDewpoint


    /**
     * Return the 'cloudAmount' class variable
     * @return cloudAmount (String)
     */
    public String getCloudAmount() {
        return cloudAmount;
    } // method getCloudAmount

    /**
     * Return the 'cloudAmount' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCloudAmount methods
     * @return cloudAmount (String)
     */
    public String getCloudAmount(String s) {
        return (cloudAmount != CHARNULL ? cloudAmount.replace('"','\'') : "");
    } // method getCloudAmount


    /**
     * Return the 'cloud1' class variable
     * @return cloud1 (String)
     */
    public String getCloud1() {
        return cloud1;
    } // method getCloud1

    /**
     * Return the 'cloud1' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCloud1 methods
     * @return cloud1 (String)
     */
    public String getCloud1(String s) {
        return (cloud1 != CHARNULL ? cloud1.replace('"','\'') : "");
    } // method getCloud1


    /**
     * Return the 'cloud2' class variable
     * @return cloud2 (String)
     */
    public String getCloud2() {
        return cloud2;
    } // method getCloud2

    /**
     * Return the 'cloud2' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCloud2 methods
     * @return cloud2 (String)
     */
    public String getCloud2(String s) {
        return (cloud2 != CHARNULL ? cloud2.replace('"','\'') : "");
    } // method getCloud2


    /**
     * Return the 'cloud3' class variable
     * @return cloud3 (String)
     */
    public String getCloud3() {
        return cloud3;
    } // method getCloud3

    /**
     * Return the 'cloud3' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCloud3 methods
     * @return cloud3 (String)
     */
    public String getCloud3(String s) {
        return (cloud3 != CHARNULL ? cloud3.replace('"','\'') : "");
    } // method getCloud3


    /**
     * Return the 'cloud4' class variable
     * @return cloud4 (String)
     */
    public String getCloud4() {
        return cloud4;
    } // method getCloud4

    /**
     * Return the 'cloud4' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCloud4 methods
     * @return cloud4 (String)
     */
    public String getCloud4(String s) {
        return (cloud4 != CHARNULL ? cloud4.replace('"','\'') : "");
    } // method getCloud4


    /**
     * Return the 'cloud5' class variable
     * @return cloud5 (String)
     */
    public String getCloud5() {
        return cloud5;
    } // method getCloud5

    /**
     * Return the 'cloud5' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCloud5 methods
     * @return cloud5 (String)
     */
    public String getCloud5(String s) {
        return (cloud5 != CHARNULL ? cloud5.replace('"','\'') : "");
    } // method getCloud5


    /**
     * Return the 'visibilityCode' class variable
     * @return visibilityCode (String)
     */
    public String getVisibilityCode() {
        return visibilityCode;
    } // method getVisibilityCode

    /**
     * Return the 'visibilityCode' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getVisibilityCode methods
     * @return visibilityCode (String)
     */
    public String getVisibilityCode(String s) {
        return (visibilityCode != CHARNULL ? visibilityCode.replace('"','\'') : "");
    } // method getVisibilityCode


    /**
     * Return the 'weatherCode' class variable
     * @return weatherCode (String)
     */
    public String getWeatherCode() {
        return weatherCode;
    } // method getWeatherCode

    /**
     * Return the 'weatherCode' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWeatherCode methods
     * @return weatherCode (String)
     */
    public String getWeatherCode(String s) {
        return (weatherCode != CHARNULL ? weatherCode.replace('"','\'') : "");
    } // method getWeatherCode


    /**
     * Return the 'swellDirection' class variable
     * @return swellDirection (int)
     */
    public int getSwellDirection() {
        return swellDirection;
    } // method getSwellDirection

    /**
     * Return the 'swellDirection' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSwellDirection methods
     * @return swellDirection (String)
     */
    public String getSwellDirection(String s) {
        return (!(swellDirection == INTNULL) ? new Integer(swellDirection).toString() : "");
    } // method getSwellDirection


    /**
     * Return the 'swellHeight' class variable
     * @return swellHeight (float)
     */
    public float getSwellHeight() {
        return swellHeight;
    } // method getSwellHeight

    /**
     * Return the 'swellHeight' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSwellHeight methods
     * @return swellHeight (String)
     */
    public String getSwellHeight(String s) {
        return (!(swellHeight == FLOATNULL) ? new Float(swellHeight).toString() : "");
    } // method getSwellHeight


    /**
     * Return the 'swellPeriod' class variable
     * @return swellPeriod (int)
     */
    public int getSwellPeriod() {
        return swellPeriod;
    } // method getSwellPeriod

    /**
     * Return the 'swellPeriod' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSwellPeriod methods
     * @return swellPeriod (String)
     */
    public String getSwellPeriod(String s) {
        return (!(swellPeriod == INTNULL) ? new Integer(swellPeriod).toString() : "");
    } // method getSwellPeriod


    /**
     * Return the 'waveHeight' class variable
     * @return waveHeight (float)
     */
    public float getWaveHeight() {
        return waveHeight;
    } // method getWaveHeight

    /**
     * Return the 'waveHeight' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWaveHeight methods
     * @return waveHeight (String)
     */
    public String getWaveHeight(String s) {
        return (!(waveHeight == FLOATNULL) ? new Float(waveHeight).toString() : "");
    } // method getWaveHeight


    /**
     * Return the 'wavePeriod' class variable
     * @return wavePeriod (int)
     */
    public int getWavePeriod() {
        return wavePeriod;
    } // method getWavePeriod

    /**
     * Return the 'wavePeriod' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWavePeriod methods
     * @return wavePeriod (String)
     */
    public String getWavePeriod(String s) {
        return (!(wavePeriod == INTNULL) ? new Integer(wavePeriod).toString() : "");
    } // method getWavePeriod


    /**
     * Return the 'windDirection' class variable
     * @return windDirection (int)
     */
    public int getWindDirection() {
        return windDirection;
    } // method getWindDirection

    /**
     * Return the 'windDirection' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindDirection methods
     * @return windDirection (String)
     */
    public String getWindDirection(String s) {
        return (!(windDirection == INTNULL) ? new Integer(windDirection).toString() : "");
    } // method getWindDirection


    /**
     * Return the 'windSpeed' class variable
     * @return windSpeed (float)
     */
    public float getWindSpeed() {
        return windSpeed;
    } // method getWindSpeed

    /**
     * Return the 'windSpeed' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSpeed methods
     * @return windSpeed (String)
     */
    public String getWindSpeed(String s) {
        return (!(windSpeed == FLOATNULL) ? new Float(windSpeed).toString() : "");
    } // method getWindSpeed


    /**
     * Return the 'windSpeedType' class variable
     * @return windSpeedType (String)
     */
    public String getWindSpeedType() {
        return windSpeedType;
    } // method getWindSpeedType

    /**
     * Return the 'windSpeedType' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindSpeedType methods
     * @return windSpeedType (String)
     */
    public String getWindSpeedType(String s) {
        return (windSpeedType != CHARNULL ? windSpeedType.replace('"','\'') : "");
    } // method getWindSpeedType


    /**
     * Gets the number of records in the table
     * @return number of records (int)
     */
    public int getRecCnt (String table) {
        return (getRecCnt(table, "1=1"));
    } // method getRecCnt


    /**
     * Gets the number of records in the table
     * If 'where' is an empty string, the where clause is set up from the
     * current class variables.
     * @param  where  The where clause (String)
     * @return number of records (int)
     */
    public int getRecCnt (String table, String where) {
        if ("".equals(where)) where = createWhere();
        Vector result = db.select ("count(*)", table, where);
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
        if ((latitude == FLOATNULL) &&
            (longitude == FLOATNULL) &&
            (dateTime.equals(DATENULL)) &&
            (daynull == CHARNULL) &&
            (callsign == CHARNULL) &&
            (country == CHARNULL) &&
            (platform == CHARNULL) &&
            (dataId == CHARNULL) &&
            (qualityControl == CHARNULL) &&
            (source1 == CHARNULL) &&
            (loadId == INTNULL) &&
            (dupflag == CHARNULL) &&
            (atmosphericPressure == FLOATNULL) &&
            (surfaceTemperature == FLOATNULL) &&
            (surfaceTemperatureType == CHARNULL) &&
            (drybulb == FLOATNULL) &&
            (wetbulb == FLOATNULL) &&
            (wetbulbIce == CHARNULL) &&
            (dewpoint == FLOATNULL) &&
            (cloudAmount == CHARNULL) &&
            (cloud1 == CHARNULL) &&
            (cloud2 == CHARNULL) &&
            (cloud3 == CHARNULL) &&
            (cloud4 == CHARNULL) &&
            (cloud5 == CHARNULL) &&
            (visibilityCode == CHARNULL) &&
            (weatherCode == CHARNULL) &&
            (swellDirection == INTNULL) &&
            (swellHeight == FLOATNULL) &&
            (swellPeriod == INTNULL) &&
            (waveHeight == FLOATNULL) &&
            (wavePeriod == INTNULL) &&
            (windDirection == INTNULL) &&
            (windSpeed == FLOATNULL) &&
            (windSpeedType == CHARNULL)) {
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
        int sumError = latitudeError +
            longitudeError +
            dateTimeError +
            daynullError +
            callsignError +
            countryError +
            platformError +
            dataIdError +
            qualityControlError +
            source1Error +
            loadIdError +
            dupflagError +
            atmosphericPressureError +
            surfaceTemperatureError +
            surfaceTemperatureTypeError +
            drybulbError +
            wetbulbError +
            wetbulbIceError +
            dewpointError +
            cloudAmountError +
            cloud1Error +
            cloud2Error +
            cloud3Error +
            cloud4Error +
            cloud5Error +
            visibilityCodeError +
            weatherCodeError +
            swellDirectionError +
            swellHeightError +
            swellPeriodError +
            waveHeightError +
            wavePeriodError +
            windDirectionError +
            windSpeedError +
            windSpeedTypeError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the latitude instance variable
     * @return errorcode (int)
     */
    public int getLatitudeError() {
        return latitudeError;
    } // method getLatitudeError

    /**
     * Gets the errorMessage for the latitude instance variable
     * @return errorMessage (String)
     */
    public String getLatitudeErrorMessage() {
        return latitudeErrorMessage;
    } // method getLatitudeErrorMessage


    /**
     * Gets the errorcode for the longitude instance variable
     * @return errorcode (int)
     */
    public int getLongitudeError() {
        return longitudeError;
    } // method getLongitudeError

    /**
     * Gets the errorMessage for the longitude instance variable
     * @return errorMessage (String)
     */
    public String getLongitudeErrorMessage() {
        return longitudeErrorMessage;
    } // method getLongitudeErrorMessage


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
     * Gets the errorcode for the daynull instance variable
     * @return errorcode (int)
     */
    public int getDaynullError() {
        return daynullError;
    } // method getDaynullError

    /**
     * Gets the errorMessage for the daynull instance variable
     * @return errorMessage (String)
     */
    public String getDaynullErrorMessage() {
        return daynullErrorMessage;
    } // method getDaynullErrorMessage


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
     * Gets the errorcode for the country instance variable
     * @return errorcode (int)
     */
    public int getCountryError() {
        return countryError;
    } // method getCountryError

    /**
     * Gets the errorMessage for the country instance variable
     * @return errorMessage (String)
     */
    public String getCountryErrorMessage() {
        return countryErrorMessage;
    } // method getCountryErrorMessage


    /**
     * Gets the errorcode for the platform instance variable
     * @return errorcode (int)
     */
    public int getPlatformError() {
        return platformError;
    } // method getPlatformError

    /**
     * Gets the errorMessage for the platform instance variable
     * @return errorMessage (String)
     */
    public String getPlatformErrorMessage() {
        return platformErrorMessage;
    } // method getPlatformErrorMessage


    /**
     * Gets the errorcode for the dataId instance variable
     * @return errorcode (int)
     */
    public int getDataIdError() {
        return dataIdError;
    } // method getDataIdError

    /**
     * Gets the errorMessage for the dataId instance variable
     * @return errorMessage (String)
     */
    public String getDataIdErrorMessage() {
        return dataIdErrorMessage;
    } // method getDataIdErrorMessage


    /**
     * Gets the errorcode for the qualityControl instance variable
     * @return errorcode (int)
     */
    public int getQualityControlError() {
        return qualityControlError;
    } // method getQualityControlError

    /**
     * Gets the errorMessage for the qualityControl instance variable
     * @return errorMessage (String)
     */
    public String getQualityControlErrorMessage() {
        return qualityControlErrorMessage;
    } // method getQualityControlErrorMessage


    /**
     * Gets the errorcode for the source1 instance variable
     * @return errorcode (int)
     */
    public int getSource1Error() {
        return source1Error;
    } // method getSource1Error

    /**
     * Gets the errorMessage for the source1 instance variable
     * @return errorMessage (String)
     */
    public String getSource1ErrorMessage() {
        return source1ErrorMessage;
    } // method getSource1ErrorMessage


    /**
     * Gets the errorcode for the loadId instance variable
     * @return errorcode (int)
     */
    public int getLoadIdError() {
        return loadIdError;
    } // method getLoadIdError

    /**
     * Gets the errorMessage for the loadId instance variable
     * @return errorMessage (String)
     */
    public String getLoadIdErrorMessage() {
        return loadIdErrorMessage;
    } // method getLoadIdErrorMessage


    /**
     * Gets the errorcode for the dupflag instance variable
     * @return errorcode (int)
     */
    public int getDupflagError() {
        return dupflagError;
    } // method getDupflagError

    /**
     * Gets the errorMessage for the dupflag instance variable
     * @return errorMessage (String)
     */
    public String getDupflagErrorMessage() {
        return dupflagErrorMessage;
    } // method getDupflagErrorMessage


    /**
     * Gets the errorcode for the atmosphericPressure instance variable
     * @return errorcode (int)
     */
    public int getAtmosphericPressureError() {
        return atmosphericPressureError;
    } // method getAtmosphericPressureError

    /**
     * Gets the errorMessage for the atmosphericPressure instance variable
     * @return errorMessage (String)
     */
    public String getAtmosphericPressureErrorMessage() {
        return atmosphericPressureErrorMessage;
    } // method getAtmosphericPressureErrorMessage


    /**
     * Gets the errorcode for the surfaceTemperature instance variable
     * @return errorcode (int)
     */
    public int getSurfaceTemperatureError() {
        return surfaceTemperatureError;
    } // method getSurfaceTemperatureError

    /**
     * Gets the errorMessage for the surfaceTemperature instance variable
     * @return errorMessage (String)
     */
    public String getSurfaceTemperatureErrorMessage() {
        return surfaceTemperatureErrorMessage;
    } // method getSurfaceTemperatureErrorMessage


    /**
     * Gets the errorcode for the surfaceTemperatureType instance variable
     * @return errorcode (int)
     */
    public int getSurfaceTemperatureTypeError() {
        return surfaceTemperatureTypeError;
    } // method getSurfaceTemperatureTypeError

    /**
     * Gets the errorMessage for the surfaceTemperatureType instance variable
     * @return errorMessage (String)
     */
    public String getSurfaceTemperatureTypeErrorMessage() {
        return surfaceTemperatureTypeErrorMessage;
    } // method getSurfaceTemperatureTypeErrorMessage


    /**
     * Gets the errorcode for the drybulb instance variable
     * @return errorcode (int)
     */
    public int getDrybulbError() {
        return drybulbError;
    } // method getDrybulbError

    /**
     * Gets the errorMessage for the drybulb instance variable
     * @return errorMessage (String)
     */
    public String getDrybulbErrorMessage() {
        return drybulbErrorMessage;
    } // method getDrybulbErrorMessage


    /**
     * Gets the errorcode for the wetbulb instance variable
     * @return errorcode (int)
     */
    public int getWetbulbError() {
        return wetbulbError;
    } // method getWetbulbError

    /**
     * Gets the errorMessage for the wetbulb instance variable
     * @return errorMessage (String)
     */
    public String getWetbulbErrorMessage() {
        return wetbulbErrorMessage;
    } // method getWetbulbErrorMessage


    /**
     * Gets the errorcode for the wetbulbIce instance variable
     * @return errorcode (int)
     */
    public int getWetbulbIceError() {
        return wetbulbIceError;
    } // method getWetbulbIceError

    /**
     * Gets the errorMessage for the wetbulbIce instance variable
     * @return errorMessage (String)
     */
    public String getWetbulbIceErrorMessage() {
        return wetbulbIceErrorMessage;
    } // method getWetbulbIceErrorMessage


    /**
     * Gets the errorcode for the dewpoint instance variable
     * @return errorcode (int)
     */
    public int getDewpointError() {
        return dewpointError;
    } // method getDewpointError

    /**
     * Gets the errorMessage for the dewpoint instance variable
     * @return errorMessage (String)
     */
    public String getDewpointErrorMessage() {
        return dewpointErrorMessage;
    } // method getDewpointErrorMessage


    /**
     * Gets the errorcode for the cloudAmount instance variable
     * @return errorcode (int)
     */
    public int getCloudAmountError() {
        return cloudAmountError;
    } // method getCloudAmountError

    /**
     * Gets the errorMessage for the cloudAmount instance variable
     * @return errorMessage (String)
     */
    public String getCloudAmountErrorMessage() {
        return cloudAmountErrorMessage;
    } // method getCloudAmountErrorMessage


    /**
     * Gets the errorcode for the cloud1 instance variable
     * @return errorcode (int)
     */
    public int getCloud1Error() {
        return cloud1Error;
    } // method getCloud1Error

    /**
     * Gets the errorMessage for the cloud1 instance variable
     * @return errorMessage (String)
     */
    public String getCloud1ErrorMessage() {
        return cloud1ErrorMessage;
    } // method getCloud1ErrorMessage


    /**
     * Gets the errorcode for the cloud2 instance variable
     * @return errorcode (int)
     */
    public int getCloud2Error() {
        return cloud2Error;
    } // method getCloud2Error

    /**
     * Gets the errorMessage for the cloud2 instance variable
     * @return errorMessage (String)
     */
    public String getCloud2ErrorMessage() {
        return cloud2ErrorMessage;
    } // method getCloud2ErrorMessage


    /**
     * Gets the errorcode for the cloud3 instance variable
     * @return errorcode (int)
     */
    public int getCloud3Error() {
        return cloud3Error;
    } // method getCloud3Error

    /**
     * Gets the errorMessage for the cloud3 instance variable
     * @return errorMessage (String)
     */
    public String getCloud3ErrorMessage() {
        return cloud3ErrorMessage;
    } // method getCloud3ErrorMessage


    /**
     * Gets the errorcode for the cloud4 instance variable
     * @return errorcode (int)
     */
    public int getCloud4Error() {
        return cloud4Error;
    } // method getCloud4Error

    /**
     * Gets the errorMessage for the cloud4 instance variable
     * @return errorMessage (String)
     */
    public String getCloud4ErrorMessage() {
        return cloud4ErrorMessage;
    } // method getCloud4ErrorMessage


    /**
     * Gets the errorcode for the cloud5 instance variable
     * @return errorcode (int)
     */
    public int getCloud5Error() {
        return cloud5Error;
    } // method getCloud5Error

    /**
     * Gets the errorMessage for the cloud5 instance variable
     * @return errorMessage (String)
     */
    public String getCloud5ErrorMessage() {
        return cloud5ErrorMessage;
    } // method getCloud5ErrorMessage


    /**
     * Gets the errorcode for the visibilityCode instance variable
     * @return errorcode (int)
     */
    public int getVisibilityCodeError() {
        return visibilityCodeError;
    } // method getVisibilityCodeError

    /**
     * Gets the errorMessage for the visibilityCode instance variable
     * @return errorMessage (String)
     */
    public String getVisibilityCodeErrorMessage() {
        return visibilityCodeErrorMessage;
    } // method getVisibilityCodeErrorMessage


    /**
     * Gets the errorcode for the weatherCode instance variable
     * @return errorcode (int)
     */
    public int getWeatherCodeError() {
        return weatherCodeError;
    } // method getWeatherCodeError

    /**
     * Gets the errorMessage for the weatherCode instance variable
     * @return errorMessage (String)
     */
    public String getWeatherCodeErrorMessage() {
        return weatherCodeErrorMessage;
    } // method getWeatherCodeErrorMessage


    /**
     * Gets the errorcode for the swellDirection instance variable
     * @return errorcode (int)
     */
    public int getSwellDirectionError() {
        return swellDirectionError;
    } // method getSwellDirectionError

    /**
     * Gets the errorMessage for the swellDirection instance variable
     * @return errorMessage (String)
     */
    public String getSwellDirectionErrorMessage() {
        return swellDirectionErrorMessage;
    } // method getSwellDirectionErrorMessage


    /**
     * Gets the errorcode for the swellHeight instance variable
     * @return errorcode (int)
     */
    public int getSwellHeightError() {
        return swellHeightError;
    } // method getSwellHeightError

    /**
     * Gets the errorMessage for the swellHeight instance variable
     * @return errorMessage (String)
     */
    public String getSwellHeightErrorMessage() {
        return swellHeightErrorMessage;
    } // method getSwellHeightErrorMessage


    /**
     * Gets the errorcode for the swellPeriod instance variable
     * @return errorcode (int)
     */
    public int getSwellPeriodError() {
        return swellPeriodError;
    } // method getSwellPeriodError

    /**
     * Gets the errorMessage for the swellPeriod instance variable
     * @return errorMessage (String)
     */
    public String getSwellPeriodErrorMessage() {
        return swellPeriodErrorMessage;
    } // method getSwellPeriodErrorMessage


    /**
     * Gets the errorcode for the waveHeight instance variable
     * @return errorcode (int)
     */
    public int getWaveHeightError() {
        return waveHeightError;
    } // method getWaveHeightError

    /**
     * Gets the errorMessage for the waveHeight instance variable
     * @return errorMessage (String)
     */
    public String getWaveHeightErrorMessage() {
        return waveHeightErrorMessage;
    } // method getWaveHeightErrorMessage


    /**
     * Gets the errorcode for the wavePeriod instance variable
     * @return errorcode (int)
     */
    public int getWavePeriodError() {
        return wavePeriodError;
    } // method getWavePeriodError

    /**
     * Gets the errorMessage for the wavePeriod instance variable
     * @return errorMessage (String)
     */
    public String getWavePeriodErrorMessage() {
        return wavePeriodErrorMessage;
    } // method getWavePeriodErrorMessage


    /**
     * Gets the errorcode for the windDirection instance variable
     * @return errorcode (int)
     */
    public int getWindDirectionError() {
        return windDirectionError;
    } // method getWindDirectionError

    /**
     * Gets the errorMessage for the windDirection instance variable
     * @return errorMessage (String)
     */
    public String getWindDirectionErrorMessage() {
        return windDirectionErrorMessage;
    } // method getWindDirectionErrorMessage


    /**
     * Gets the errorcode for the windSpeed instance variable
     * @return errorcode (int)
     */
    public int getWindSpeedError() {
        return windSpeedError;
    } // method getWindSpeedError

    /**
     * Gets the errorMessage for the windSpeed instance variable
     * @return errorMessage (String)
     */
    public String getWindSpeedErrorMessage() {
        return windSpeedErrorMessage;
    } // method getWindSpeedErrorMessage


    /**
     * Gets the errorcode for the windSpeedType instance variable
     * @return errorcode (int)
     */
    public int getWindSpeedTypeError() {
        return windSpeedTypeError;
    } // method getWindSpeedTypeError

    /**
     * Gets the errorMessage for the windSpeedType instance variable
     * @return errorMessage (String)
     */
    public String getWindSpeedTypeErrorMessage() {
        return windSpeedTypeErrorMessage;
    } // method getWindSpeedTypeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of VosTable. e.g.<pre>
     * VosTable vosTable = new VosTable(<val1>);
     * VosTable vosTableArray[] = vosTable.get();</pre>
     * will get the VosTable record where latitude = <val1>.
     * @return Array of VosTable (VosTable[])
     */
    public Vector getV() {
        //System.out.println (TABLE);
        //return new Vector();
        return db.select(TABLE, createWhere());
    } // method getV

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * VosTable vosTableArray[] =
     *     new VosTable().get(VosTable.LATITUDE+"=<val1>");</pre>
     * will get the VosTable record where latitude = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of VosTable (VosTable[])
     */
    public Vector getV(String where) {
        return db.select(TABLE, where);
    } // method getV

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * VosTable vosTableArray[] =
     *     new VosTable().get("1=1",vosTable.LATITUDE);</pre>
     * will get the all the VosTable records, and order them by latitude.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of VosTable (VosTable[])
     */
    public Vector getV(String where, String order) {
        return db.select("*", TABLE, where, order);
    } // method getV

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = VosTable.LATITUDE,VosTable.LONGITUDE;
     * String where = VosTable.LATITUDE + "=<val1";
     * String order = VosTable.LATITUDE;
     * VosTable vosTableArray[] =
     *     new VosTable().get(columns, where, order);</pre>
     * will get the latitude and longitude colums of all VosTable records,
     * where latitude = <val1>, and order them by latitude.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of VosTable (VosTable[])
     */

    public Vector getV(String fields, String where, String order) {
        // create the column list from the instance variables if neccessary
        try {
            if (fields.equals("")) { fields = null; }
        } catch (NullPointerException e) {}
        if (fields == null) {
            fields = createColumns();
        } // if (fields != null)

        return db.select(fields, TABLE, where, order);
    } // method get

    /**
     * Receives the result of the select statement from DbAccess,
     * and transforms it into an array of VosTable.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    public VosTable[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int latitudeCol               = db.getColNumber(LATITUDE);
        int longitudeCol              = db.getColNumber(LONGITUDE);
        int dateTimeCol               = db.getColNumber(DATE_TIME);
        int daynullCol                = db.getColNumber(DAYNULL);
        int callsignCol               = db.getColNumber(CALLSIGN);
        int countryCol                = db.getColNumber(COUNTRY);
        int platformCol               = db.getColNumber(PLATFORM);
        int dataIdCol                 = db.getColNumber(DATA_ID);
        int qualityControlCol         = db.getColNumber(QUALITY_CONTROL);
        int source1Col                = db.getColNumber(SOURCE1);
        int loadIdCol                 = db.getColNumber(LOAD_ID);
        int dupflagCol                = db.getColNumber(DUPFLAG);
        int atmosphericPressureCol    = db.getColNumber(ATMOSPHERIC_PRESSURE);
        int surfaceTemperatureCol     = db.getColNumber(SURFACE_TEMPERATURE);
        int surfaceTemperatureTypeCol = db.getColNumber(SURFACE_TEMPERATURE_TYPE);
        int drybulbCol                = db.getColNumber(DRYBULB);
        int wetbulbCol                = db.getColNumber(WETBULB);
        int wetbulbIceCol             = db.getColNumber(WETBULB_ICE);
        int dewpointCol               = db.getColNumber(DEWPOINT);
        int cloudAmountCol            = db.getColNumber(CLOUD_AMOUNT);
        int cloud1Col                 = db.getColNumber(CLOUD1);
        int cloud2Col                 = db.getColNumber(CLOUD2);
        int cloud3Col                 = db.getColNumber(CLOUD3);
        int cloud4Col                 = db.getColNumber(CLOUD4);
        int cloud5Col                 = db.getColNumber(CLOUD5);
        int visibilityCodeCol         = db.getColNumber(VISIBILITY_CODE);
        int weatherCodeCol            = db.getColNumber(WEATHER_CODE);
        int swellDirectionCol         = db.getColNumber(SWELL_DIRECTION);
        int swellHeightCol            = db.getColNumber(SWELL_HEIGHT);
        int swellPeriodCol            = db.getColNumber(SWELL_PERIOD);
        int waveHeightCol             = db.getColNumber(WAVE_HEIGHT);
        int wavePeriodCol             = db.getColNumber(WAVE_PERIOD);
        int windDirectionCol          = db.getColNumber(WIND_DIRECTION);
        int windSpeedCol              = db.getColNumber(WIND_SPEED);
        int windSpeedTypeCol          = db.getColNumber(WIND_SPEED_TYPE);
        VosTable[] cArray = new VosTable[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new VosTable();
            if (latitudeCol != -1)
                cArray[i].setLatitude              ((String) row.elementAt(latitudeCol));
            if (longitudeCol != -1)
                cArray[i].setLongitude             ((String) row.elementAt(longitudeCol));
            if (dateTimeCol != -1)
                cArray[i].setDateTime              ((String) row.elementAt(dateTimeCol));
            if (daynullCol != -1)
                cArray[i].setDaynull               ((String) row.elementAt(daynullCol));
            if (callsignCol != -1)
                cArray[i].setCallsign              ((String) row.elementAt(callsignCol));
            if (countryCol != -1)
                cArray[i].setCountry               ((String) row.elementAt(countryCol));
            if (platformCol != -1)
                cArray[i].setPlatform              ((String) row.elementAt(platformCol));
            if (dataIdCol != -1)
                cArray[i].setDataId                ((String) row.elementAt(dataIdCol));
            if (qualityControlCol != -1)
                cArray[i].setQualityControl        ((String) row.elementAt(qualityControlCol));
            if (source1Col != -1)
                cArray[i].setSource1               ((String) row.elementAt(source1Col));
            if (loadIdCol != -1)
                cArray[i].setLoadId                ((String) row.elementAt(loadIdCol));
            if (dupflagCol != -1)
                cArray[i].setDupflag               ((String) row.elementAt(dupflagCol));
            if (atmosphericPressureCol != -1)
                cArray[i].setAtmosphericPressure   ((String) row.elementAt(atmosphericPressureCol));
            if (surfaceTemperatureCol != -1)
                cArray[i].setSurfaceTemperature    ((String) row.elementAt(surfaceTemperatureCol));
            if (surfaceTemperatureTypeCol != -1)
                cArray[i].setSurfaceTemperatureType((String) row.elementAt(surfaceTemperatureTypeCol));
            if (drybulbCol != -1)
                cArray[i].setDrybulb               ((String) row.elementAt(drybulbCol));
            if (wetbulbCol != -1)
                cArray[i].setWetbulb               ((String) row.elementAt(wetbulbCol));
            if (wetbulbIceCol != -1)
                cArray[i].setWetbulbIce            ((String) row.elementAt(wetbulbIceCol));
            if (dewpointCol != -1)
                cArray[i].setDewpoint              ((String) row.elementAt(dewpointCol));
            if (cloudAmountCol != -1)
                cArray[i].setCloudAmount           ((String) row.elementAt(cloudAmountCol));
            if (cloud1Col != -1)
                cArray[i].setCloud1                ((String) row.elementAt(cloud1Col));
            if (cloud2Col != -1)
                cArray[i].setCloud2                ((String) row.elementAt(cloud2Col));
            if (cloud3Col != -1)
                cArray[i].setCloud3                ((String) row.elementAt(cloud3Col));
            if (cloud4Col != -1)
                cArray[i].setCloud4                ((String) row.elementAt(cloud4Col));
            if (cloud5Col != -1)
                cArray[i].setCloud5                ((String) row.elementAt(cloud5Col));
            if (visibilityCodeCol != -1)
                cArray[i].setVisibilityCode        ((String) row.elementAt(visibilityCodeCol));
            if (weatherCodeCol != -1)
                cArray[i].setWeatherCode           ((String) row.elementAt(weatherCodeCol));
            if (swellDirectionCol != -1)
                cArray[i].setSwellDirection        ((String) row.elementAt(swellDirectionCol));
            if (swellHeightCol != -1)
                cArray[i].setSwellHeight           ((String) row.elementAt(swellHeightCol));
            if (swellPeriodCol != -1)
                cArray[i].setSwellPeriod           ((String) row.elementAt(swellPeriodCol));
            if (waveHeightCol != -1)
                cArray[i].setWaveHeight            ((String) row.elementAt(waveHeightCol));
            if (wavePeriodCol != -1)
                cArray[i].setWavePeriod            ((String) row.elementAt(wavePeriodCol));
            if (windDirectionCol != -1)
                cArray[i].setWindDirection         ((String) row.elementAt(windDirectionCol));
            if (windSpeedCol != -1)
                cArray[i].setWindSpeed             ((String) row.elementAt(windSpeedCol));
            if (windSpeedTypeCol != -1)
                cArray[i].setWindSpeedType         ((String) row.elementAt(windSpeedTypeCol));
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
     *     new VosMain(
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
     *         <val22>,
     *         <val23>,
     *         <val24>,
     *         <val25>,
     *         <val26>,
     *         <val27>,
     *         <val28>,
     *         <val29>,
     *         <val30>,
     *         <val31>,
     *         <val32>,
     *         <val33>,
     *         <val34>,
     *         <val35>).put();</pre>
     * will insert a record with:
     *     latitude               = <val1>,
     *     longitude              = <val2>,
     *     dateTime               = <val3>,
     *     daynull                = <val4>,
     *     callsign               = <val5>,
     *     country                = <val6>,
     *     platform               = <val7>,
     *     dataId                 = <val8>,
     *     qualityControl         = <val9>,
     *     source1                = <val10>,
     *     loadId                 = <val11>,
     *     dupflag                = <val12>,
     *     atmosphericPressure    = <val13>,
     *     surfaceTemperature     = <val14>,
     *     surfaceTemperatureType = <val15>,
     *     drybulb                = <val16>,
     *     wetbulb                = <val17>,
     *     wetbulbIce             = <val18>,
     *     dewpoint               = <val19>,
     *     cloudAmount            = <val20>,
     *     cloud1                 = <val21>,
     *     cloud2                 = <val22>,
     *     cloud3                 = <val23>,
     *     cloud4                 = <val24>,
     *     cloud5                 = <val25>,
     *     visibilityCode         = <val26>,
     *     weatherCode            = <val27>,
     *     swellDirection         = <val28>,
     *     swellHeight            = <val29>,
     *     swellPeriod            = <val30>,
     *     waveHeight             = <val31>,
     *     wavePeriod             = <val32>,
     *     windDirection          = <val33>,
     *     windSpeed              = <val34>,
     *     windSpeedType          = <val35>.
     * @return success = true/false (boolean)
     */
    // overloaded by methods in VosMain/2, VosArch/2
    public boolean put() {
        return true;
    } // method put


    /**
     * Insert a record into the table.  The values are taken from the current
     * value of the instance variables. .e.g.<pre>
     * String    CHARNULL  = Tables.CHARNULL;
     * Timestamp DATENULL  = Tables.DATENULL;
     * int       INTNULL   = Tables.INTNULL;
     * float     FLOATNULL = Tables.FLOATNULL;
     * Boolean success =
     *     new VosTable(
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
     *         <val22>,
     *         <val23>,
     *         <val24>,
     *         <val25>,
     *         <val26>,
     *         <val27>,
     *         <val28>,
     *         <val29>,
     *         <val30>,
     *         <val31>,
     *         <val32>,
     *         <val33>,
     *         <val34>,
     *         <val35>).put();</pre>
     * will insert a record with:
     *     latitude               = <val1>,
     *     longitude              = <val2>,
     *     dateTime               = <val3>,
     *     daynull                = <val4>,
     *     callsign               = <val5>,
     *     country                = <val6>,
     *     platform               = <val7>,
     *     dataId                 = <val8>,
     *     qualityControl         = <val9>,
     *     source1                = <val10>,
     *     loadId                 = <val11>,
     *     dupflag                = <val12>,
     *     atmosphericPressure    = <val13>,
     *     surfaceTemperature     = <val14>,
     *     surfaceTemperatureType = <val15>,
     *     drybulb                = <val16>,
     *     wetbulb                = <val17>,
     *     wetbulbIce             = <val18>,
     *     dewpoint               = <val19>,
     *     cloudAmount            = <val20>,
     *     cloud1                 = <val21>,
     *     cloud2                 = <val22>,
     *     cloud3                 = <val23>,
     *     cloud4                 = <val24>,
     *     cloud5                 = <val25>,
     *     visibilityCode         = <val26>,
     *     weatherCode            = <val27>,
     *     swellDirection         = <val28>,
     *     swellHeight            = <val29>,
     *     swellPeriod            = <val30>,
     *     waveHeight             = <val31>,
     *     wavePeriod             = <val32>,
     *     windDirection          = <val33>,
     *     windSpeed              = <val34>,
     *     windSpeedType          = <val35>.
     * @return success = true/false (boolean)
     */
    public boolean doPut (String table) {
        boolean success = db.insert (table, createColumns(), createValues());
//        numRecords = db.getNumRecords();
        //if (!success) messages = db.getMessages();
//        messages = db.getMessages();
        return success;
    } // method put


    //=====================//
    // All the del methods //
    //=====================//

    /**
     * Delete record(s) from the table, The where clause is created from the
     * current values of the instance variables. .e.g.<pre>
     * Boolean success = new VosTable(
     *     Tables.FLOATNULL,
     *     <val2>,
     *     Tables.DATENULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where longitude = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean doDel(String table, String where) {
        boolean success = db.delete (table, where);
//        numRecords = db.getNumRecords();
//        if (!success) messages = db.getMessages();
        return success;
    } // method del


    //=====================//
    // All the upd methods //
    //=====================//

    /**
     * Update record(s) from the table, The fields and values to use for the
     * update are taken from the VosTable argument, .e.g.<pre>
     * Boolean success
     * VosTable updVosTable = new VosTable();
     * updVosTable.setLongitude(<val2>);
     * VosTable whereVosTable = new VosTable(<val1>);
     * success = whereVosTable.upd(updJobs);</pre>
     * will update Longitude to <val2> for all records where
     * latitude = <val1>.
     * @param  vosTable  A VosTable variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean doUpd(String table, VosTable vosTable, String where) {
        boolean success = db.update (table, createColVals(vosTable), where);
//        numRecords = db.getNumRecords();
//        if (!success) messages = db.getMessages();
//        System.out.println("Update string "+getUpdStr());
        return success;
    } // method upd


    //======================//
    // Other helper methods //
    //======================//

    /**
     * Creates the where clause from the current values of the
     * instance variables.
     * @return  where clause (String)
     */
    protected String createWhere() {
        String where = "";
        if (getLatitude() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LATITUDE + "=" + getLatitude("");
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LONGITUDE + "=" + getLongitude("");
        } // if getLongitude
        if (!getDateTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME +
                "=" + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getDaynull() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DAYNULL + "='" + getDaynull() + "'";
        } // if getDaynull
        if (getCallsign() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CALLSIGN + "='" + getCallsign() + "'";
        } // if getCallsign
        if (getCountry() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + COUNTRY + "='" + getCountry() + "'";
        } // if getCountry
        if (getPlatform() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLATFORM + "='" + getPlatform() + "'";
        } // if getPlatform
        if (getDataId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATA_ID + "='" + getDataId() + "'";
        } // if getDataId
        if (getQualityControl() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + QUALITY_CONTROL + "='" + getQualityControl() + "'";
        } // if getQualityControl
        if (getSource1() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SOURCE1 + "='" + getSource1() + "'";
        } // if getSource1
        if (getLoadId() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LOAD_ID + "=" + getLoadId("");
        } // if getLoadId
        if (getDupflag() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DUPFLAG + "='" + getDupflag() + "'";
        } // if getDupflag
        if (getAtmosphericPressure() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ATMOSPHERIC_PRESSURE + "=" + getAtmosphericPressure("");
        } // if getAtmosphericPressure
        if (getSurfaceTemperature() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURFACE_TEMPERATURE + "=" + getSurfaceTemperature("");
        } // if getSurfaceTemperature
        if (getSurfaceTemperatureType() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURFACE_TEMPERATURE_TYPE + "='" + getSurfaceTemperatureType() + "'";
        } // if getSurfaceTemperatureType
        if (getDrybulb() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DRYBULB + "=" + getDrybulb("");
        } // if getDrybulb
        if (getWetbulb() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WETBULB + "=" + getWetbulb("");
        } // if getWetbulb
        if (getWetbulbIce() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WETBULB_ICE + "='" + getWetbulbIce() + "'";
        } // if getWetbulbIce
        if (getDewpoint() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEWPOINT + "=" + getDewpoint("");
        } // if getDewpoint
        if (getCloudAmount() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLOUD_AMOUNT + "='" + getCloudAmount() + "'";
        } // if getCloudAmount
        if (getCloud1() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLOUD1 + "='" + getCloud1() + "'";
        } // if getCloud1
        if (getCloud2() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLOUD2 + "='" + getCloud2() + "'";
        } // if getCloud2
        if (getCloud3() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLOUD3 + "='" + getCloud3() + "'";
        } // if getCloud3
        if (getCloud4() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLOUD4 + "='" + getCloud4() + "'";
        } // if getCloud4
        if (getCloud5() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLOUD5 + "='" + getCloud5() + "'";
        } // if getCloud5
        if (getVisibilityCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + VISIBILITY_CODE + "='" + getVisibilityCode() + "'";
        } // if getVisibilityCode
        if (getWeatherCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WEATHER_CODE + "='" + getWeatherCode() + "'";
        } // if getWeatherCode
        if (getSwellDirection() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SWELL_DIRECTION + "=" + getSwellDirection("");
        } // if getSwellDirection
        if (getSwellHeight() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SWELL_HEIGHT + "=" + getSwellHeight("");
        } // if getSwellHeight
        if (getSwellPeriod() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SWELL_PERIOD + "=" + getSwellPeriod("");
        } // if getSwellPeriod
        if (getWaveHeight() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WAVE_HEIGHT + "=" + getWaveHeight("");
        } // if getWaveHeight
        if (getWavePeriod() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WAVE_PERIOD + "=" + getWavePeriod("");
        } // if getWavePeriod
        if (getWindDirection() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_DIRECTION + "=" + getWindDirection("");
        } // if getWindDirection
        if (getWindSpeed() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED + "=" + getWindSpeed("");
        } // if getWindSpeed
        if (getWindSpeedType() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED_TYPE + "='" + getWindSpeedType() + "'";
        } // if getWindSpeedType
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    protected String createColVals(VosTable aVar) {
        String colVals = "";
        if (aVar.getLatitude() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LATITUDE +"=";
            colVals += (aVar.getLatitude() == FLOATNULL2 ?
                "null" : aVar.getLatitude(""));
        } // if aVar.getLatitude
        if (aVar.getLongitude() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LONGITUDE +"=";
            colVals += (aVar.getLongitude() == FLOATNULL2 ?
                "null" : aVar.getLongitude(""));
        } // if aVar.getLongitude
        if (!aVar.getDateTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME +"=";
            colVals += (aVar.getDateTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTime()));
        } // if aVar.getDateTime
        if (aVar.getDaynull() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DAYNULL +"=";
            colVals += (aVar.getDaynull().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDaynull() + "'");
        } // if aVar.getDaynull
        if (aVar.getCallsign() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CALLSIGN +"=";
            colVals += (aVar.getCallsign().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCallsign() + "'");
        } // if aVar.getCallsign
        if (aVar.getCountry() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += COUNTRY +"=";
            colVals += (aVar.getCountry().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCountry() + "'");
        } // if aVar.getCountry
        if (aVar.getPlatform() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLATFORM +"=";
            colVals += (aVar.getPlatform().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPlatform() + "'");
        } // if aVar.getPlatform
        if (aVar.getDataId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATA_ID +"=";
            colVals += (aVar.getDataId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDataId() + "'");
        } // if aVar.getDataId
        if (aVar.getQualityControl() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += QUALITY_CONTROL +"=";
            colVals += (aVar.getQualityControl().equals(CHARNULL2) ?
                "null" : "'" + aVar.getQualityControl() + "'");
        } // if aVar.getQualityControl
        if (aVar.getSource1() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SOURCE1 +"=";
            colVals += (aVar.getSource1().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSource1() + "'");
        } // if aVar.getSource1
        if (aVar.getLoadId() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LOAD_ID +"=";
            colVals += (aVar.getLoadId() == INTNULL2 ?
                "null" : aVar.getLoadId(""));
        } // if aVar.getLoadId
        if (aVar.getDupflag() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DUPFLAG +"=";
            colVals += (aVar.getDupflag().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDupflag() + "'");
        } // if aVar.getDupflag
        if (aVar.getAtmosphericPressure() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ATMOSPHERIC_PRESSURE +"=";
            colVals += (aVar.getAtmosphericPressure() == FLOATNULL2 ?
                "null" : aVar.getAtmosphericPressure(""));
        } // if aVar.getAtmosphericPressure
        if (aVar.getSurfaceTemperature() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURFACE_TEMPERATURE +"=";
            colVals += (aVar.getSurfaceTemperature() == FLOATNULL2 ?
                "null" : aVar.getSurfaceTemperature(""));
        } // if aVar.getSurfaceTemperature
        if (aVar.getSurfaceTemperatureType() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURFACE_TEMPERATURE_TYPE +"=";
            colVals += (aVar.getSurfaceTemperatureType().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurfaceTemperatureType() + "'");
        } // if aVar.getSurfaceTemperatureType
        if (aVar.getDrybulb() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DRYBULB +"=";
            colVals += (aVar.getDrybulb() == FLOATNULL2 ?
                "null" : aVar.getDrybulb(""));
        } // if aVar.getDrybulb
        if (aVar.getWetbulb() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WETBULB +"=";
            colVals += (aVar.getWetbulb() == FLOATNULL2 ?
                "null" : aVar.getWetbulb(""));
        } // if aVar.getWetbulb
        if (aVar.getWetbulbIce() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WETBULB_ICE +"=";
            colVals += (aVar.getWetbulbIce().equals(CHARNULL2) ?
                "null" : "'" + aVar.getWetbulbIce() + "'");
        } // if aVar.getWetbulbIce
        if (aVar.getDewpoint() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEWPOINT +"=";
            colVals += (aVar.getDewpoint() == FLOATNULL2 ?
                "null" : aVar.getDewpoint(""));
        } // if aVar.getDewpoint
        if (aVar.getCloudAmount() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLOUD_AMOUNT +"=";
            colVals += (aVar.getCloudAmount().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCloudAmount() + "'");
        } // if aVar.getCloudAmount
        if (aVar.getCloud1() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLOUD1 +"=";
            colVals += (aVar.getCloud1().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCloud1() + "'");
        } // if aVar.getCloud1
        if (aVar.getCloud2() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLOUD2 +"=";
            colVals += (aVar.getCloud2().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCloud2() + "'");
        } // if aVar.getCloud2
        if (aVar.getCloud3() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLOUD3 +"=";
            colVals += (aVar.getCloud3().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCloud3() + "'");
        } // if aVar.getCloud3
        if (aVar.getCloud4() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLOUD4 +"=";
            colVals += (aVar.getCloud4().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCloud4() + "'");
        } // if aVar.getCloud4
        if (aVar.getCloud5() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLOUD5 +"=";
            colVals += (aVar.getCloud5().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCloud5() + "'");
        } // if aVar.getCloud5
        if (aVar.getVisibilityCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += VISIBILITY_CODE +"=";
            colVals += (aVar.getVisibilityCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getVisibilityCode() + "'");
        } // if aVar.getVisibilityCode
        if (aVar.getWeatherCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WEATHER_CODE +"=";
            colVals += (aVar.getWeatherCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getWeatherCode() + "'");
        } // if aVar.getWeatherCode
        if (aVar.getSwellDirection() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SWELL_DIRECTION +"=";
            colVals += (aVar.getSwellDirection() == INTNULL2 ?
                "null" : aVar.getSwellDirection(""));
        } // if aVar.getSwellDirection
        if (aVar.getSwellHeight() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SWELL_HEIGHT +"=";
            colVals += (aVar.getSwellHeight() == FLOATNULL2 ?
                "null" : aVar.getSwellHeight(""));
        } // if aVar.getSwellHeight
        if (aVar.getSwellPeriod() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SWELL_PERIOD +"=";
            colVals += (aVar.getSwellPeriod() == INTNULL2 ?
                "null" : aVar.getSwellPeriod(""));
        } // if aVar.getSwellPeriod
        if (aVar.getWaveHeight() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WAVE_HEIGHT +"=";
            colVals += (aVar.getWaveHeight() == FLOATNULL2 ?
                "null" : aVar.getWaveHeight(""));
        } // if aVar.getWaveHeight
        if (aVar.getWavePeriod() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WAVE_PERIOD +"=";
            colVals += (aVar.getWavePeriod() == INTNULL2 ?
                "null" : aVar.getWavePeriod(""));
        } // if aVar.getWavePeriod
        if (aVar.getWindDirection() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_DIRECTION +"=";
            colVals += (aVar.getWindDirection() == INTNULL2 ?
                "null" : aVar.getWindDirection(""));
        } // if aVar.getWindDirection
        if (aVar.getWindSpeed() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED +"=";
            colVals += (aVar.getWindSpeed() == FLOATNULL2 ?
                "null" : aVar.getWindSpeed(""));
        } // if aVar.getWindSpeed
        if (aVar.getWindSpeedType() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED_TYPE +"=";
            colVals += (aVar.getWindSpeedType().equals(CHARNULL2) ?
                "null" : "'" + aVar.getWindSpeedType() + "'");
        } // if aVar.getWindSpeedType
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    protected String createColumns() {
        String columns = LATITUDE;
        if (getLongitude() != FLOATNULL) {
            columns = columns + "," + LONGITUDE;
        } // if getLongitude
        if (!getDateTime().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME;
        } // if getDateTime
        if (getDaynull() != CHARNULL) {
            columns = columns + "," + DAYNULL;
        } // if getDaynull
        if (getCallsign() != CHARNULL) {
            columns = columns + "," + CALLSIGN;
        } // if getCallsign
        if (getCountry() != CHARNULL) {
            columns = columns + "," + COUNTRY;
        } // if getCountry
        if (getPlatform() != CHARNULL) {
            columns = columns + "," + PLATFORM;
        } // if getPlatform
        if (getDataId() != CHARNULL) {
            columns = columns + "," + DATA_ID;
        } // if getDataId
        if (getQualityControl() != CHARNULL) {
            columns = columns + "," + QUALITY_CONTROL;
        } // if getQualityControl
        if (getSource1() != CHARNULL) {
            columns = columns + "," + SOURCE1;
        } // if getSource1
        if (getLoadId() != INTNULL) {
            columns = columns + "," + LOAD_ID;
        } // if getLoadId
        if (getDupflag() != CHARNULL) {
            columns = columns + "," + DUPFLAG;
        } // if getDupflag
        if (getAtmosphericPressure() != FLOATNULL) {
            columns = columns + "," + ATMOSPHERIC_PRESSURE;
        } // if getAtmosphericPressure
        if (getSurfaceTemperature() != FLOATNULL) {
            columns = columns + "," + SURFACE_TEMPERATURE;
        } // if getSurfaceTemperature
        if (getSurfaceTemperatureType() != CHARNULL) {
            columns = columns + "," + SURFACE_TEMPERATURE_TYPE;
        } // if getSurfaceTemperatureType
        if (getDrybulb() != FLOATNULL) {
            columns = columns + "," + DRYBULB;
        } // if getDrybulb
        if (getWetbulb() != FLOATNULL) {
            columns = columns + "," + WETBULB;
        } // if getWetbulb
        if (getWetbulbIce() != CHARNULL) {
            columns = columns + "," + WETBULB_ICE;
        } // if getWetbulbIce
        if (getDewpoint() != FLOATNULL) {
            columns = columns + "," + DEWPOINT;
        } // if getDewpoint
        if (getCloudAmount() != CHARNULL) {
            columns = columns + "," + CLOUD_AMOUNT;
        } // if getCloudAmount
        if (getCloud1() != CHARNULL) {
            columns = columns + "," + CLOUD1;
        } // if getCloud1
        if (getCloud2() != CHARNULL) {
            columns = columns + "," + CLOUD2;
        } // if getCloud2
        if (getCloud3() != CHARNULL) {
            columns = columns + "," + CLOUD3;
        } // if getCloud3
        if (getCloud4() != CHARNULL) {
            columns = columns + "," + CLOUD4;
        } // if getCloud4
        if (getCloud5() != CHARNULL) {
            columns = columns + "," + CLOUD5;
        } // if getCloud5
        if (getVisibilityCode() != CHARNULL) {
            columns = columns + "," + VISIBILITY_CODE;
        } // if getVisibilityCode
        if (getWeatherCode() != CHARNULL) {
            columns = columns + "," + WEATHER_CODE;
        } // if getWeatherCode
        if (getSwellDirection() != INTNULL) {
            columns = columns + "," + SWELL_DIRECTION;
        } // if getSwellDirection
        if (getSwellHeight() != FLOATNULL) {
            columns = columns + "," + SWELL_HEIGHT;
        } // if getSwellHeight
        if (getSwellPeriod() != INTNULL) {
            columns = columns + "," + SWELL_PERIOD;
        } // if getSwellPeriod
        if (getWaveHeight() != FLOATNULL) {
            columns = columns + "," + WAVE_HEIGHT;
        } // if getWaveHeight
        if (getWavePeriod() != INTNULL) {
            columns = columns + "," + WAVE_PERIOD;
        } // if getWavePeriod
        if (getWindDirection() != INTNULL) {
            columns = columns + "," + WIND_DIRECTION;
        } // if getWindDirection
        if (getWindSpeed() != FLOATNULL) {
            columns = columns + "," + WIND_SPEED;
        } // if getWindSpeed
        if (getWindSpeedType() != CHARNULL) {
            columns = columns + "," + WIND_SPEED_TYPE;
        } // if getWindSpeedType
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    protected String createValues() {
        String values  = getLatitude("");
        if (getLongitude() != FLOATNULL) {
            values  = values  + "," + getLongitude("");
        } // if getLongitude
        if (!getDateTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getDaynull() != CHARNULL) {
            values  = values  + ",'" + getDaynull() + "'";
        } // if getDaynull
        if (getCallsign() != CHARNULL) {
            values  = values  + ",'" + getCallsign() + "'";
        } // if getCallsign
        if (getCountry() != CHARNULL) {
            values  = values  + ",'" + getCountry() + "'";
        } // if getCountry
        if (getPlatform() != CHARNULL) {
            values  = values  + ",'" + getPlatform() + "'";
        } // if getPlatform
        if (getDataId() != CHARNULL) {
            values  = values  + ",'" + getDataId() + "'";
        } // if getDataId
        if (getQualityControl() != CHARNULL) {
            values  = values  + ",'" + getQualityControl() + "'";
        } // if getQualityControl
        if (getSource1() != CHARNULL) {
            values  = values  + ",'" + getSource1() + "'";
        } // if getSource1
        if (getLoadId() != INTNULL) {
            values  = values  + "," + getLoadId("");
        } // if getLoadId
        if (getDupflag() != CHARNULL) {
            values  = values  + ",'" + getDupflag() + "'";
        } // if getDupflag
        if (getAtmosphericPressure() != FLOATNULL) {
            values  = values  + "," + getAtmosphericPressure("");
        } // if getAtmosphericPressure
        if (getSurfaceTemperature() != FLOATNULL) {
            values  = values  + "," + getSurfaceTemperature("");
        } // if getSurfaceTemperature
        if (getSurfaceTemperatureType() != CHARNULL) {
            values  = values  + ",'" + getSurfaceTemperatureType() + "'";
        } // if getSurfaceTemperatureType
        if (getDrybulb() != FLOATNULL) {
            values  = values  + "," + getDrybulb("");
        } // if getDrybulb
        if (getWetbulb() != FLOATNULL) {
            values  = values  + "," + getWetbulb("");
        } // if getWetbulb
        if (getWetbulbIce() != CHARNULL) {
            values  = values  + ",'" + getWetbulbIce() + "'";
        } // if getWetbulbIce
        if (getDewpoint() != FLOATNULL) {
            values  = values  + "," + getDewpoint("");
        } // if getDewpoint
        if (getCloudAmount() != CHARNULL) {
            values  = values  + ",'" + getCloudAmount() + "'";
        } // if getCloudAmount
        if (getCloud1() != CHARNULL) {
            values  = values  + ",'" + getCloud1() + "'";
        } // if getCloud1
        if (getCloud2() != CHARNULL) {
            values  = values  + ",'" + getCloud2() + "'";
        } // if getCloud2
        if (getCloud3() != CHARNULL) {
            values  = values  + ",'" + getCloud3() + "'";
        } // if getCloud3
        if (getCloud4() != CHARNULL) {
            values  = values  + ",'" + getCloud4() + "'";
        } // if getCloud4
        if (getCloud5() != CHARNULL) {
            values  = values  + ",'" + getCloud5() + "'";
        } // if getCloud5
        if (getVisibilityCode() != CHARNULL) {
            values  = values  + ",'" + getVisibilityCode() + "'";
        } // if getVisibilityCode
        if (getWeatherCode() != CHARNULL) {
            values  = values  + ",'" + getWeatherCode() + "'";
        } // if getWeatherCode
        if (getSwellDirection() != INTNULL) {
            values  = values  + "," + getSwellDirection("");
        } // if getSwellDirection
        if (getSwellHeight() != FLOATNULL) {
            values  = values  + "," + getSwellHeight("");
        } // if getSwellHeight
        if (getSwellPeriod() != INTNULL) {
            values  = values  + "," + getSwellPeriod("");
        } // if getSwellPeriod
        if (getWaveHeight() != FLOATNULL) {
            values  = values  + "," + getWaveHeight("");
        } // if getWaveHeight
        if (getWavePeriod() != INTNULL) {
            values  = values  + "," + getWavePeriod("");
        } // if getWavePeriod
        if (getWindDirection() != INTNULL) {
            values  = values  + "," + getWindDirection("");
        } // if getWindDirection
        if (getWindSpeed() != FLOATNULL) {
            values  = values  + "," + getWindSpeed("");
        } // if getWindSpeed
        if (getWindSpeedType() != CHARNULL) {
            values  = values  + ",'" + getWindSpeedType() + "'";
        } // if getWindSpeedType
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getLatitude("")               + "|" +
            getLongitude("")              + "|" +
            getDateTime("")               + "|" +
            getDaynull("")                + "|" +
            getCallsign("")               + "|" +
            getCountry("")                + "|" +
            getPlatform("")               + "|" +
            getDataId("")                 + "|" +
            getQualityControl("")         + "|" +
            getSource1("")                + "|" +
            getLoadId("")                 + "|" +
            getDupflag("")                + "|" +
            getAtmosphericPressure("")    + "|" +
            getSurfaceTemperature("")     + "|" +
            getSurfaceTemperatureType("") + "|" +
            getDrybulb("")                + "|" +
            getWetbulb("")                + "|" +
            getWetbulbIce("")             + "|" +
            getDewpoint("")               + "|" +
            getCloudAmount("")            + "|" +
            getCloud1("")                 + "|" +
            getCloud2("")                 + "|" +
            getCloud3("")                 + "|" +
            getCloud4("")                 + "|" +
            getCloud5("")                 + "|" +
            getVisibilityCode("")         + "|" +
            getWeatherCode("")            + "|" +
            getSwellDirection("")         + "|" +
            getSwellHeight("")            + "|" +
            getSwellPeriod("")            + "|" +
            getWaveHeight("")             + "|" +
            getWavePeriod("")             + "|" +
            getWindDirection("")          + "|" +
            getWindSpeed("")              + "|" +
            getWindSpeedType("")          + "|";
    } // method toString

} // class VosTable
