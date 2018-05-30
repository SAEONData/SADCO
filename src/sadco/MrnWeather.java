package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WEATHER table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnWeather extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WEATHER";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The navEquipType field name */
    public static final String NAV_EQUIP_TYPE = "NAV_EQUIP_TYPE";
    /** The atmosphPres field name */
    public static final String ATMOSPH_PRES = "ATMOSPH_PRES";
    /** The surfaceTmp field name */
    public static final String SURFACE_TMP = "SURFACE_TMP";
    /** The drybulb field name */
    public static final String DRYBULB = "DRYBULB";
    /** The wetbulb field name */
    public static final String WETBULB = "WETBULB";
    /** The cloud field name */
    public static final String CLOUD = "CLOUD";
    /** The visCode field name */
    public static final String VIS_CODE = "VIS_CODE";
    /** The weatherCode field name */
    public static final String WEATHER_CODE = "WEATHER_CODE";
    /** The waterColor field name */
    public static final String WATER_COLOR = "WATER_COLOR";
    /** The transparency field name */
    public static final String TRANSPARENCY = "TRANSPARENCY";
    /** The windDir field name */
    public static final String WIND_DIR = "WIND_DIR";
    /** The windSpeed field name */
    public static final String WIND_SPEED = "WIND_SPEED";
    /** The swellDir field name */
    public static final String SWELL_DIR = "SWELL_DIR";
    /** The swellHeight field name */
    public static final String SWELL_HEIGHT = "SWELL_HEIGHT";
    /** The swellPeriod field name */
    public static final String SWELL_PERIOD = "SWELL_PERIOD";
    /** The dupflag field name */
    public static final String DUPFLAG = "DUPFLAG";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private String    navEquipType;
    private float     atmosphPres;
    private float     surfaceTmp;
    private float     drybulb;
    private float     wetbulb;
    private String    cloud;
    private String    visCode;
    private String    weatherCode;
    private int       waterColor;
    private int       transparency;
    private int       windDir;
    private float     windSpeed;
    private int       swellDir;
    private float     swellHeight;
    private int       swellPeriod;
    private String    dupflag;

    /** The error variables  */
    private int stationIdError    = ERROR_NORMAL;
    private int navEquipTypeError = ERROR_NORMAL;
    private int atmosphPresError  = ERROR_NORMAL;
    private int surfaceTmpError   = ERROR_NORMAL;
    private int drybulbError      = ERROR_NORMAL;
    private int wetbulbError      = ERROR_NORMAL;
    private int cloudError        = ERROR_NORMAL;
    private int visCodeError      = ERROR_NORMAL;
    private int weatherCodeError  = ERROR_NORMAL;
    private int waterColorError   = ERROR_NORMAL;
    private int transparencyError = ERROR_NORMAL;
    private int windDirError      = ERROR_NORMAL;
    private int windSpeedError    = ERROR_NORMAL;
    private int swellDirError     = ERROR_NORMAL;
    private int swellHeightError  = ERROR_NORMAL;
    private int swellPeriodError  = ERROR_NORMAL;
    private int dupflagError      = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage    = "";
    private String navEquipTypeErrorMessage = "";
    private String atmosphPresErrorMessage  = "";
    private String surfaceTmpErrorMessage   = "";
    private String drybulbErrorMessage      = "";
    private String wetbulbErrorMessage      = "";
    private String cloudErrorMessage        = "";
    private String visCodeErrorMessage      = "";
    private String weatherCodeErrorMessage  = "";
    private String waterColorErrorMessage   = "";
    private String transparencyErrorMessage = "";
    private String windDirErrorMessage      = "";
    private String windSpeedErrorMessage    = "";
    private String swellDirErrorMessage     = "";
    private String swellHeightErrorMessage  = "";
    private String swellPeriodErrorMessage  = "";
    private String dupflagErrorMessage      = "";

    /** The min-max constants for all numerics */
    public static final float     ATMOSPH_PRES_MN = FLOATMIN;
    public static final float     ATMOSPH_PRES_MX = FLOATMAX;
    public static final float     SURFACE_TMP_MN = FLOATMIN;
    public static final float     SURFACE_TMP_MX = FLOATMAX;
    public static final float     DRYBULB_MN = FLOATMIN;
    public static final float     DRYBULB_MX = FLOATMAX;
    public static final float     WETBULB_MN = FLOATMIN;
    public static final float     WETBULB_MX = FLOATMAX;
    public static final int       WATER_COLOR_MN = INTMIN;
    public static final int       WATER_COLOR_MX = INTMAX;
    public static final int       TRANSPARENCY_MN = INTMIN;
    public static final int       TRANSPARENCY_MX = INTMAX;
    public static final int       WIND_DIR_MN = INTMIN;
    public static final int       WIND_DIR_MX = INTMAX;
    public static final float     WIND_SPEED_MN = FLOATMIN;
    public static final float     WIND_SPEED_MX = FLOATMAX;
    public static final int       SWELL_DIR_MN = INTMIN;
    public static final int       SWELL_DIR_MX = INTMAX;
    public static final float     SWELL_HEIGHT_MN = FLOATMIN;
    public static final float     SWELL_HEIGHT_MX = FLOATMAX;
    public static final int       SWELL_PERIOD_MN = INTMIN;
    public static final int       SWELL_PERIOD_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception atmosphPresOutOfBoundsException =
        new Exception ("'atmosphPres' out of bounds: " +
            ATMOSPH_PRES_MN + " - " + ATMOSPH_PRES_MX);
    Exception surfaceTmpOutOfBoundsException =
        new Exception ("'surfaceTmp' out of bounds: " +
            SURFACE_TMP_MN + " - " + SURFACE_TMP_MX);
    Exception drybulbOutOfBoundsException =
        new Exception ("'drybulb' out of bounds: " +
            DRYBULB_MN + " - " + DRYBULB_MX);
    Exception wetbulbOutOfBoundsException =
        new Exception ("'wetbulb' out of bounds: " +
            WETBULB_MN + " - " + WETBULB_MX);
    Exception waterColorOutOfBoundsException =
        new Exception ("'waterColor' out of bounds: " +
            WATER_COLOR_MN + " - " + WATER_COLOR_MX);
    Exception transparencyOutOfBoundsException =
        new Exception ("'transparency' out of bounds: " +
            TRANSPARENCY_MN + " - " + TRANSPARENCY_MX);
    Exception windDirOutOfBoundsException =
        new Exception ("'windDir' out of bounds: " +
            WIND_DIR_MN + " - " + WIND_DIR_MX);
    Exception windSpeedOutOfBoundsException =
        new Exception ("'windSpeed' out of bounds: " +
            WIND_SPEED_MN + " - " + WIND_SPEED_MX);
    Exception swellDirOutOfBoundsException =
        new Exception ("'swellDir' out of bounds: " +
            SWELL_DIR_MN + " - " + SWELL_DIR_MX);
    Exception swellHeightOutOfBoundsException =
        new Exception ("'swellHeight' out of bounds: " +
            SWELL_HEIGHT_MN + " - " + SWELL_HEIGHT_MX);
    Exception swellPeriodOutOfBoundsException =
        new Exception ("'swellPeriod' out of bounds: " +
            SWELL_PERIOD_MN + " - " + SWELL_PERIOD_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnWeather() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnWeather constructor 1"); // debug
    } // MrnWeather constructor

    /**
     * Instantiate a MrnWeather object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public MrnWeather(
            String stationId) {
        this();
        setStationId    (stationId   );
        if (dbg) System.out.println ("<br>in MrnWeather constructor 2"); // debug
    } // MrnWeather constructor

    /**
     * Instantiate a MrnWeather object and initialize the instance variables.
     * @param stationId     The stationId    (String)
     * @param navEquipType  The navEquipType (String)
     * @param atmosphPres   The atmosphPres  (float)
     * @param surfaceTmp    The surfaceTmp   (float)
     * @param drybulb       The drybulb      (float)
     * @param wetbulb       The wetbulb      (float)
     * @param cloud         The cloud        (String)
     * @param visCode       The visCode      (String)
     * @param weatherCode   The weatherCode  (String)
     * @param waterColor    The waterColor   (int)
     * @param transparency  The transparency (int)
     * @param windDir       The windDir      (int)
     * @param windSpeed     The windSpeed    (float)
     * @param swellDir      The swellDir     (int)
     * @param swellHeight   The swellHeight  (float)
     * @param swellPeriod   The swellPeriod  (int)
     * @param dupflag       The dupflag      (String)
     */
    public MrnWeather(
            String stationId,
            String navEquipType,
            float  atmosphPres,
            float  surfaceTmp,
            float  drybulb,
            float  wetbulb,
            String cloud,
            String visCode,
            String weatherCode,
            int    waterColor,
            int    transparency,
            int    windDir,
            float  windSpeed,
            int    swellDir,
            float  swellHeight,
            int    swellPeriod,
            String dupflag) {
        this();
        setStationId    (stationId   );
        setNavEquipType (navEquipType);
        setAtmosphPres  (atmosphPres );
        setSurfaceTmp   (surfaceTmp  );
        setDrybulb      (drybulb     );
        setWetbulb      (wetbulb     );
        setCloud        (cloud       );
        setVisCode      (visCode     );
        setWeatherCode  (weatherCode );
        setWaterColor   (waterColor  );
        setTransparency (transparency);
        setWindDir      (windDir     );
        setWindSpeed    (windSpeed   );
        setSwellDir     (swellDir    );
        setSwellHeight  (swellHeight );
        setSwellPeriod  (swellPeriod );
        setDupflag      (dupflag     );
        if (dbg) System.out.println ("<br>in MrnWeather constructor 3"); // debug
    } // MrnWeather constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId    (CHARNULL );
        setNavEquipType (CHARNULL );
        setAtmosphPres  (FLOATNULL);
        setSurfaceTmp   (FLOATNULL);
        setDrybulb      (FLOATNULL);
        setWetbulb      (FLOATNULL);
        setCloud        (CHARNULL );
        setVisCode      (CHARNULL );
        setWeatherCode  (CHARNULL );
        setWaterColor   (INTNULL  );
        setTransparency (INTNULL  );
        setWindDir      (INTNULL  );
        setWindSpeed    (FLOATNULL);
        setSwellDir     (INTNULL  );
        setSwellHeight  (FLOATNULL);
        setSwellPeriod  (INTNULL  );
        setDupflag      (CHARNULL );
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
     * Set the 'navEquipType' class variable
     * @param  navEquipType (String)
     */
    public int setNavEquipType(String navEquipType) {
        try {
            this.navEquipType = navEquipType;
            if (this.navEquipType != CHARNULL) {
                this.navEquipType = stripCRLF(this.navEquipType.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>navEquipType = " + this.navEquipType);
        } catch (Exception e) {
            setNavEquipTypeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return navEquipTypeError;
    } // method setNavEquipType

    /**
     * Called when an exception has occured
     * @param  navEquipType (String)
     */
    private void setNavEquipTypeError (String navEquipType, Exception e, int error) {
        this.navEquipType = navEquipType;
        navEquipTypeErrorMessage = e.toString();
        navEquipTypeError = error;
    } // method setNavEquipTypeError


    /**
     * Set the 'atmosphPres' class variable
     * @param  atmosphPres (float)
     */
    public int setAtmosphPres(float atmosphPres) {
        try {
            if ( ((atmosphPres == FLOATNULL) ||
                  (atmosphPres == FLOATNULL2)) ||
                !((atmosphPres < ATMOSPH_PRES_MN) ||
                  (atmosphPres > ATMOSPH_PRES_MX)) ) {
                this.atmosphPres = atmosphPres;
                atmosphPresError = ERROR_NORMAL;
            } else {
                throw atmosphPresOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAtmosphPresError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return atmosphPresError;
    } // method setAtmosphPres

    /**
     * Set the 'atmosphPres' class variable
     * @param  atmosphPres (Integer)
     */
    public int setAtmosphPres(Integer atmosphPres) {
        try {
            setAtmosphPres(atmosphPres.floatValue());
        } catch (Exception e) {
            setAtmosphPresError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return atmosphPresError;
    } // method setAtmosphPres

    /**
     * Set the 'atmosphPres' class variable
     * @param  atmosphPres (Float)
     */
    public int setAtmosphPres(Float atmosphPres) {
        try {
            setAtmosphPres(atmosphPres.floatValue());
        } catch (Exception e) {
            setAtmosphPresError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return atmosphPresError;
    } // method setAtmosphPres

    /**
     * Set the 'atmosphPres' class variable
     * @param  atmosphPres (String)
     */
    public int setAtmosphPres(String atmosphPres) {
        try {
            setAtmosphPres(new Float(atmosphPres).floatValue());
        } catch (Exception e) {
            setAtmosphPresError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return atmosphPresError;
    } // method setAtmosphPres

    /**
     * Called when an exception has occured
     * @param  atmosphPres (String)
     */
    private void setAtmosphPresError (float atmosphPres, Exception e, int error) {
        this.atmosphPres = atmosphPres;
        atmosphPresErrorMessage = e.toString();
        atmosphPresError = error;
    } // method setAtmosphPresError


    /**
     * Set the 'surfaceTmp' class variable
     * @param  surfaceTmp (float)
     */
    public int setSurfaceTmp(float surfaceTmp) {
        try {
            if ( ((surfaceTmp == FLOATNULL) ||
                  (surfaceTmp == FLOATNULL2)) ||
                !((surfaceTmp < SURFACE_TMP_MN) ||
                  (surfaceTmp > SURFACE_TMP_MX)) ) {
                this.surfaceTmp = surfaceTmp;
                surfaceTmpError = ERROR_NORMAL;
            } else {
                throw surfaceTmpOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSurfaceTmpError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return surfaceTmpError;
    } // method setSurfaceTmp

    /**
     * Set the 'surfaceTmp' class variable
     * @param  surfaceTmp (Integer)
     */
    public int setSurfaceTmp(Integer surfaceTmp) {
        try {
            setSurfaceTmp(surfaceTmp.floatValue());
        } catch (Exception e) {
            setSurfaceTmpError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return surfaceTmpError;
    } // method setSurfaceTmp

    /**
     * Set the 'surfaceTmp' class variable
     * @param  surfaceTmp (Float)
     */
    public int setSurfaceTmp(Float surfaceTmp) {
        try {
            setSurfaceTmp(surfaceTmp.floatValue());
        } catch (Exception e) {
            setSurfaceTmpError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return surfaceTmpError;
    } // method setSurfaceTmp

    /**
     * Set the 'surfaceTmp' class variable
     * @param  surfaceTmp (String)
     */
    public int setSurfaceTmp(String surfaceTmp) {
        try {
            setSurfaceTmp(new Float(surfaceTmp).floatValue());
        } catch (Exception e) {
            setSurfaceTmpError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return surfaceTmpError;
    } // method setSurfaceTmp

    /**
     * Called when an exception has occured
     * @param  surfaceTmp (String)
     */
    private void setSurfaceTmpError (float surfaceTmp, Exception e, int error) {
        this.surfaceTmp = surfaceTmp;
        surfaceTmpErrorMessage = e.toString();
        surfaceTmpError = error;
    } // method setSurfaceTmpError


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
     * Set the 'cloud' class variable
     * @param  cloud (String)
     */
    public int setCloud(String cloud) {
        try {
            this.cloud = cloud;
            if (this.cloud != CHARNULL) {
                this.cloud = stripCRLF(this.cloud.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>cloud = " + this.cloud);
        } catch (Exception e) {
            setCloudError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return cloudError;
    } // method setCloud

    /**
     * Called when an exception has occured
     * @param  cloud (String)
     */
    private void setCloudError (String cloud, Exception e, int error) {
        this.cloud = cloud;
        cloudErrorMessage = e.toString();
        cloudError = error;
    } // method setCloudError


    /**
     * Set the 'visCode' class variable
     * @param  visCode (String)
     */
    public int setVisCode(String visCode) {
        try {
            this.visCode = visCode;
            if (this.visCode != CHARNULL) {
                this.visCode = stripCRLF(this.visCode.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>visCode = " + this.visCode);
        } catch (Exception e) {
            setVisCodeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return visCodeError;
    } // method setVisCode

    /**
     * Called when an exception has occured
     * @param  visCode (String)
     */
    private void setVisCodeError (String visCode, Exception e, int error) {
        this.visCode = visCode;
        visCodeErrorMessage = e.toString();
        visCodeError = error;
    } // method setVisCodeError


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
     * Set the 'waterColor' class variable
     * @param  waterColor (int)
     */
    public int setWaterColor(int waterColor) {
        try {
            if ( ((waterColor == INTNULL) ||
                  (waterColor == INTNULL2)) ||
                !((waterColor < WATER_COLOR_MN) ||
                  (waterColor > WATER_COLOR_MX)) ) {
                this.waterColor = waterColor;
                waterColorError = ERROR_NORMAL;
            } else {
                throw waterColorOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWaterColorError(INTNULL, e, ERROR_LOCAL);
        } // try
        return waterColorError;
    } // method setWaterColor

    /**
     * Set the 'waterColor' class variable
     * @param  waterColor (Integer)
     */
    public int setWaterColor(Integer waterColor) {
        try {
            setWaterColor(waterColor.intValue());
        } catch (Exception e) {
            setWaterColorError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return waterColorError;
    } // method setWaterColor

    /**
     * Set the 'waterColor' class variable
     * @param  waterColor (Float)
     */
    public int setWaterColor(Float waterColor) {
        try {
            if (waterColor.floatValue() == FLOATNULL) {
                setWaterColor(INTNULL);
            } else {
                setWaterColor(waterColor.intValue());
            } // if (waterColor.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWaterColorError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return waterColorError;
    } // method setWaterColor

    /**
     * Set the 'waterColor' class variable
     * @param  waterColor (String)
     */
    public int setWaterColor(String waterColor) {
        try {
            setWaterColor(new Integer(waterColor).intValue());
        } catch (Exception e) {
            setWaterColorError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return waterColorError;
    } // method setWaterColor

    /**
     * Called when an exception has occured
     * @param  waterColor (String)
     */
    private void setWaterColorError (int waterColor, Exception e, int error) {
        this.waterColor = waterColor;
        waterColorErrorMessage = e.toString();
        waterColorError = error;
    } // method setWaterColorError


    /**
     * Set the 'transparency' class variable
     * @param  transparency (int)
     */
    public int setTransparency(int transparency) {
        try {
            if ( ((transparency == INTNULL) ||
                  (transparency == INTNULL2)) ||
                !((transparency < TRANSPARENCY_MN) ||
                  (transparency > TRANSPARENCY_MX)) ) {
                this.transparency = transparency;
                transparencyError = ERROR_NORMAL;
            } else {
                throw transparencyOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTransparencyError(INTNULL, e, ERROR_LOCAL);
        } // try
        return transparencyError;
    } // method setTransparency

    /**
     * Set the 'transparency' class variable
     * @param  transparency (Integer)
     */
    public int setTransparency(Integer transparency) {
        try {
            setTransparency(transparency.intValue());
        } catch (Exception e) {
            setTransparencyError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return transparencyError;
    } // method setTransparency

    /**
     * Set the 'transparency' class variable
     * @param  transparency (Float)
     */
    public int setTransparency(Float transparency) {
        try {
            if (transparency.floatValue() == FLOATNULL) {
                setTransparency(INTNULL);
            } else {
                setTransparency(transparency.intValue());
            } // if (transparency.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTransparencyError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return transparencyError;
    } // method setTransparency

    /**
     * Set the 'transparency' class variable
     * @param  transparency (String)
     */
    public int setTransparency(String transparency) {
        try {
            setTransparency(new Integer(transparency).intValue());
        } catch (Exception e) {
            setTransparencyError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return transparencyError;
    } // method setTransparency

    /**
     * Called when an exception has occured
     * @param  transparency (String)
     */
    private void setTransparencyError (int transparency, Exception e, int error) {
        this.transparency = transparency;
        transparencyErrorMessage = e.toString();
        transparencyError = error;
    } // method setTransparencyError


    /**
     * Set the 'windDir' class variable
     * @param  windDir (int)
     */
    public int setWindDir(int windDir) {
        try {
            if ( ((windDir == INTNULL) ||
                  (windDir == INTNULL2)) ||
                !((windDir < WIND_DIR_MN) ||
                  (windDir > WIND_DIR_MX)) ) {
                this.windDir = windDir;
                windDirError = ERROR_NORMAL;
            } else {
                throw windDirOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWindDirError(INTNULL, e, ERROR_LOCAL);
        } // try
        return windDirError;
    } // method setWindDir

    /**
     * Set the 'windDir' class variable
     * @param  windDir (Integer)
     */
    public int setWindDir(Integer windDir) {
        try {
            setWindDir(windDir.intValue());
        } catch (Exception e) {
            setWindDirError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windDirError;
    } // method setWindDir

    /**
     * Set the 'windDir' class variable
     * @param  windDir (Float)
     */
    public int setWindDir(Float windDir) {
        try {
            if (windDir.floatValue() == FLOATNULL) {
                setWindDir(INTNULL);
            } else {
                setWindDir(windDir.intValue());
            } // if (windDir.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWindDirError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windDirError;
    } // method setWindDir

    /**
     * Set the 'windDir' class variable
     * @param  windDir (String)
     */
    public int setWindDir(String windDir) {
        try {
            setWindDir(new Integer(windDir).intValue());
        } catch (Exception e) {
            setWindDirError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return windDirError;
    } // method setWindDir

    /**
     * Called when an exception has occured
     * @param  windDir (String)
     */
    private void setWindDirError (int windDir, Exception e, int error) {
        this.windDir = windDir;
        windDirErrorMessage = e.toString();
        windDirError = error;
    } // method setWindDirError


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
     * Set the 'swellDir' class variable
     * @param  swellDir (int)
     */
    public int setSwellDir(int swellDir) {
        try {
            if ( ((swellDir == INTNULL) ||
                  (swellDir == INTNULL2)) ||
                !((swellDir < SWELL_DIR_MN) ||
                  (swellDir > SWELL_DIR_MX)) ) {
                this.swellDir = swellDir;
                swellDirError = ERROR_NORMAL;
            } else {
                throw swellDirOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSwellDirError(INTNULL, e, ERROR_LOCAL);
        } // try
        return swellDirError;
    } // method setSwellDir

    /**
     * Set the 'swellDir' class variable
     * @param  swellDir (Integer)
     */
    public int setSwellDir(Integer swellDir) {
        try {
            setSwellDir(swellDir.intValue());
        } catch (Exception e) {
            setSwellDirError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return swellDirError;
    } // method setSwellDir

    /**
     * Set the 'swellDir' class variable
     * @param  swellDir (Float)
     */
    public int setSwellDir(Float swellDir) {
        try {
            if (swellDir.floatValue() == FLOATNULL) {
                setSwellDir(INTNULL);
            } else {
                setSwellDir(swellDir.intValue());
            } // if (swellDir.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSwellDirError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return swellDirError;
    } // method setSwellDir

    /**
     * Set the 'swellDir' class variable
     * @param  swellDir (String)
     */
    public int setSwellDir(String swellDir) {
        try {
            setSwellDir(new Integer(swellDir).intValue());
        } catch (Exception e) {
            setSwellDirError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return swellDirError;
    } // method setSwellDir

    /**
     * Called when an exception has occured
     * @param  swellDir (String)
     */
    private void setSwellDirError (int swellDir, Exception e, int error) {
        this.swellDir = swellDir;
        swellDirErrorMessage = e.toString();
        swellDirError = error;
    } // method setSwellDirError


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
     * Return the 'navEquipType' class variable
     * @return navEquipType (String)
     */
    public String getNavEquipType() {
        return navEquipType;
    } // method getNavEquipType

    /**
     * Return the 'navEquipType' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNavEquipType methods
     * @return navEquipType (String)
     */
    public String getNavEquipType(String s) {
        return (navEquipType != CHARNULL ? navEquipType.replace('"','\'') : "");
    } // method getNavEquipType


    /**
     * Return the 'atmosphPres' class variable
     * @return atmosphPres (float)
     */
    public float getAtmosphPres() {
        return atmosphPres;
    } // method getAtmosphPres

    /**
     * Return the 'atmosphPres' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAtmosphPres methods
     * @return atmosphPres (String)
     */
    public String getAtmosphPres(String s) {
        return ((atmosphPres != FLOATNULL) ? new Float(atmosphPres).toString() : "");
    } // method getAtmosphPres


    /**
     * Return the 'surfaceTmp' class variable
     * @return surfaceTmp (float)
     */
    public float getSurfaceTmp() {
        return surfaceTmp;
    } // method getSurfaceTmp

    /**
     * Return the 'surfaceTmp' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurfaceTmp methods
     * @return surfaceTmp (String)
     */
    public String getSurfaceTmp(String s) {
        return ((surfaceTmp != FLOATNULL) ? new Float(surfaceTmp).toString() : "");
    } // method getSurfaceTmp


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
        return ((drybulb != FLOATNULL) ? new Float(drybulb).toString() : "");
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
        return ((wetbulb != FLOATNULL) ? new Float(wetbulb).toString() : "");
    } // method getWetbulb


    /**
     * Return the 'cloud' class variable
     * @return cloud (String)
     */
    public String getCloud() {
        return cloud;
    } // method getCloud

    /**
     * Return the 'cloud' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCloud methods
     * @return cloud (String)
     */
    public String getCloud(String s) {
        return (cloud != CHARNULL ? cloud.replace('"','\'') : "");
    } // method getCloud


    /**
     * Return the 'visCode' class variable
     * @return visCode (String)
     */
    public String getVisCode() {
        return visCode;
    } // method getVisCode

    /**
     * Return the 'visCode' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getVisCode methods
     * @return visCode (String)
     */
    public String getVisCode(String s) {
        return (visCode != CHARNULL ? visCode.replace('"','\'') : "");
    } // method getVisCode


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
     * Return the 'waterColor' class variable
     * @return waterColor (int)
     */
    public int getWaterColor() {
        return waterColor;
    } // method getWaterColor

    /**
     * Return the 'waterColor' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWaterColor methods
     * @return waterColor (String)
     */
    public String getWaterColor(String s) {
        return ((waterColor != INTNULL) ? new Integer(waterColor).toString() : "");
    } // method getWaterColor


    /**
     * Return the 'transparency' class variable
     * @return transparency (int)
     */
    public int getTransparency() {
        return transparency;
    } // method getTransparency

    /**
     * Return the 'transparency' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTransparency methods
     * @return transparency (String)
     */
    public String getTransparency(String s) {
        return ((transparency != INTNULL) ? new Integer(transparency).toString() : "");
    } // method getTransparency


    /**
     * Return the 'windDir' class variable
     * @return windDir (int)
     */
    public int getWindDir() {
        return windDir;
    } // method getWindDir

    /**
     * Return the 'windDir' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWindDir methods
     * @return windDir (String)
     */
    public String getWindDir(String s) {
        return ((windDir != INTNULL) ? new Integer(windDir).toString() : "");
    } // method getWindDir


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
        return ((windSpeed != FLOATNULL) ? new Float(windSpeed).toString() : "");
    } // method getWindSpeed


    /**
     * Return the 'swellDir' class variable
     * @return swellDir (int)
     */
    public int getSwellDir() {
        return swellDir;
    } // method getSwellDir

    /**
     * Return the 'swellDir' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSwellDir methods
     * @return swellDir (String)
     */
    public String getSwellDir(String s) {
        return ((swellDir != INTNULL) ? new Integer(swellDir).toString() : "");
    } // method getSwellDir


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
        return ((swellHeight != FLOATNULL) ? new Float(swellHeight).toString() : "");
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
        return ((swellPeriod != INTNULL) ? new Integer(swellPeriod).toString() : "");
    } // method getSwellPeriod


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
            (navEquipType == CHARNULL) &&
            (atmosphPres == FLOATNULL) &&
            (surfaceTmp == FLOATNULL) &&
            (drybulb == FLOATNULL) &&
            (wetbulb == FLOATNULL) &&
            (cloud == CHARNULL) &&
            (visCode == CHARNULL) &&
            (weatherCode == CHARNULL) &&
            (waterColor == INTNULL) &&
            (transparency == INTNULL) &&
            (windDir == INTNULL) &&
            (windSpeed == FLOATNULL) &&
            (swellDir == INTNULL) &&
            (swellHeight == FLOATNULL) &&
            (swellPeriod == INTNULL) &&
            (dupflag == CHARNULL)) {
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
            navEquipTypeError +
            atmosphPresError +
            surfaceTmpError +
            drybulbError +
            wetbulbError +
            cloudError +
            visCodeError +
            weatherCodeError +
            waterColorError +
            transparencyError +
            windDirError +
            windSpeedError +
            swellDirError +
            swellHeightError +
            swellPeriodError +
            dupflagError;
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
     * Gets the errorcode for the navEquipType instance variable
     * @return errorcode (int)
     */
    public int getNavEquipTypeError() {
        return navEquipTypeError;
    } // method getNavEquipTypeError

    /**
     * Gets the errorMessage for the navEquipType instance variable
     * @return errorMessage (String)
     */
    public String getNavEquipTypeErrorMessage() {
        return navEquipTypeErrorMessage;
    } // method getNavEquipTypeErrorMessage


    /**
     * Gets the errorcode for the atmosphPres instance variable
     * @return errorcode (int)
     */
    public int getAtmosphPresError() {
        return atmosphPresError;
    } // method getAtmosphPresError

    /**
     * Gets the errorMessage for the atmosphPres instance variable
     * @return errorMessage (String)
     */
    public String getAtmosphPresErrorMessage() {
        return atmosphPresErrorMessage;
    } // method getAtmosphPresErrorMessage


    /**
     * Gets the errorcode for the surfaceTmp instance variable
     * @return errorcode (int)
     */
    public int getSurfaceTmpError() {
        return surfaceTmpError;
    } // method getSurfaceTmpError

    /**
     * Gets the errorMessage for the surfaceTmp instance variable
     * @return errorMessage (String)
     */
    public String getSurfaceTmpErrorMessage() {
        return surfaceTmpErrorMessage;
    } // method getSurfaceTmpErrorMessage


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
     * Gets the errorcode for the cloud instance variable
     * @return errorcode (int)
     */
    public int getCloudError() {
        return cloudError;
    } // method getCloudError

    /**
     * Gets the errorMessage for the cloud instance variable
     * @return errorMessage (String)
     */
    public String getCloudErrorMessage() {
        return cloudErrorMessage;
    } // method getCloudErrorMessage


    /**
     * Gets the errorcode for the visCode instance variable
     * @return errorcode (int)
     */
    public int getVisCodeError() {
        return visCodeError;
    } // method getVisCodeError

    /**
     * Gets the errorMessage for the visCode instance variable
     * @return errorMessage (String)
     */
    public String getVisCodeErrorMessage() {
        return visCodeErrorMessage;
    } // method getVisCodeErrorMessage


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
     * Gets the errorcode for the waterColor instance variable
     * @return errorcode (int)
     */
    public int getWaterColorError() {
        return waterColorError;
    } // method getWaterColorError

    /**
     * Gets the errorMessage for the waterColor instance variable
     * @return errorMessage (String)
     */
    public String getWaterColorErrorMessage() {
        return waterColorErrorMessage;
    } // method getWaterColorErrorMessage


    /**
     * Gets the errorcode for the transparency instance variable
     * @return errorcode (int)
     */
    public int getTransparencyError() {
        return transparencyError;
    } // method getTransparencyError

    /**
     * Gets the errorMessage for the transparency instance variable
     * @return errorMessage (String)
     */
    public String getTransparencyErrorMessage() {
        return transparencyErrorMessage;
    } // method getTransparencyErrorMessage


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
     * Gets the errorcode for the swellDir instance variable
     * @return errorcode (int)
     */
    public int getSwellDirError() {
        return swellDirError;
    } // method getSwellDirError

    /**
     * Gets the errorMessage for the swellDir instance variable
     * @return errorMessage (String)
     */
    public String getSwellDirErrorMessage() {
        return swellDirErrorMessage;
    } // method getSwellDirErrorMessage


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


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnWeather. e.g.<pre>
     * MrnWeather weather = new MrnWeather(<val1>);
     * MrnWeather weatherArray[] = weather.get();</pre>
     * will get the MrnWeather record where stationId = <val1>.
     * @return Array of MrnWeather (MrnWeather[])
     */
    public MrnWeather[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnWeather weatherArray[] =
     *     new MrnWeather().get(MrnWeather.STATION_ID+"=<val1>");</pre>
     * will get the MrnWeather record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnWeather (MrnWeather[])
     */
    public MrnWeather[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnWeather weatherArray[] =
     *     new MrnWeather().get("1=1",weather.STATION_ID);</pre>
     * will get the all the MrnWeather records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWeather (MrnWeather[])
     */
    public MrnWeather[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnWeather.STATION_ID,MrnWeather.NAV_EQUIP_TYPE;
     * String where = MrnWeather.STATION_ID + "=<val1";
     * String order = MrnWeather.STATION_ID;
     * MrnWeather weatherArray[] =
     *     new MrnWeather().get(columns, where, order);</pre>
     * will get the stationId and navEquipType colums of all MrnWeather records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWeather (MrnWeather[])
     */
    public MrnWeather[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnWeather.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnWeather[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol    = db.getColNumber(STATION_ID);
        int navEquipTypeCol = db.getColNumber(NAV_EQUIP_TYPE);
        int atmosphPresCol  = db.getColNumber(ATMOSPH_PRES);
        int surfaceTmpCol   = db.getColNumber(SURFACE_TMP);
        int drybulbCol      = db.getColNumber(DRYBULB);
        int wetbulbCol      = db.getColNumber(WETBULB);
        int cloudCol        = db.getColNumber(CLOUD);
        int visCodeCol      = db.getColNumber(VIS_CODE);
        int weatherCodeCol  = db.getColNumber(WEATHER_CODE);
        int waterColorCol   = db.getColNumber(WATER_COLOR);
        int transparencyCol = db.getColNumber(TRANSPARENCY);
        int windDirCol      = db.getColNumber(WIND_DIR);
        int windSpeedCol    = db.getColNumber(WIND_SPEED);
        int swellDirCol     = db.getColNumber(SWELL_DIR);
        int swellHeightCol  = db.getColNumber(SWELL_HEIGHT);
        int swellPeriodCol  = db.getColNumber(SWELL_PERIOD);
        int dupflagCol      = db.getColNumber(DUPFLAG);
        MrnWeather[] cArray = new MrnWeather[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnWeather();
            if (stationIdCol != -1)
                cArray[i].setStationId   ((String) row.elementAt(stationIdCol));
            if (navEquipTypeCol != -1)
                cArray[i].setNavEquipType((String) row.elementAt(navEquipTypeCol));
            if (atmosphPresCol != -1)
                cArray[i].setAtmosphPres ((String) row.elementAt(atmosphPresCol));
            if (surfaceTmpCol != -1)
                cArray[i].setSurfaceTmp  ((String) row.elementAt(surfaceTmpCol));
            if (drybulbCol != -1)
                cArray[i].setDrybulb     ((String) row.elementAt(drybulbCol));
            if (wetbulbCol != -1)
                cArray[i].setWetbulb     ((String) row.elementAt(wetbulbCol));
            if (cloudCol != -1)
                cArray[i].setCloud       ((String) row.elementAt(cloudCol));
            if (visCodeCol != -1)
                cArray[i].setVisCode     ((String) row.elementAt(visCodeCol));
            if (weatherCodeCol != -1)
                cArray[i].setWeatherCode ((String) row.elementAt(weatherCodeCol));
            if (waterColorCol != -1)
                cArray[i].setWaterColor  ((String) row.elementAt(waterColorCol));
            if (transparencyCol != -1)
                cArray[i].setTransparency((String) row.elementAt(transparencyCol));
            if (windDirCol != -1)
                cArray[i].setWindDir     ((String) row.elementAt(windDirCol));
            if (windSpeedCol != -1)
                cArray[i].setWindSpeed   ((String) row.elementAt(windSpeedCol));
            if (swellDirCol != -1)
                cArray[i].setSwellDir    ((String) row.elementAt(swellDirCol));
            if (swellHeightCol != -1)
                cArray[i].setSwellHeight ((String) row.elementAt(swellHeightCol));
            if (swellPeriodCol != -1)
                cArray[i].setSwellPeriod ((String) row.elementAt(swellPeriodCol));
            if (dupflagCol != -1)
                cArray[i].setDupflag     ((String) row.elementAt(dupflagCol));
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
     *     new MrnWeather(
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
     *         <val17>).put();</pre>
     * will insert a record with:
     *     stationId    = <val1>,
     *     navEquipType = <val2>,
     *     atmosphPres  = <val3>,
     *     surfaceTmp   = <val4>,
     *     drybulb      = <val5>,
     *     wetbulb      = <val6>,
     *     cloud        = <val7>,
     *     visCode      = <val8>,
     *     weatherCode  = <val9>,
     *     waterColor   = <val10>,
     *     transparency = <val11>,
     *     windDir      = <val12>,
     *     windSpeed    = <val13>,
     *     swellDir     = <val14>,
     *     swellHeight  = <val15>,
     *     swellPeriod  = <val16>,
     *     dupflag      = <val17>.
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
     * Boolean success = new MrnWeather(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where navEquipType = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWeather weather = new MrnWeather();
     * success = weather.del(MrnWeather.STATION_ID+"=<val1>");</pre>
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
     * update are taken from the MrnWeather argument, .e.g.<pre>
     * Boolean success
     * MrnWeather updMrnWeather = new MrnWeather();
     * updMrnWeather.setNavEquipType(<val2>);
     * MrnWeather whereMrnWeather = new MrnWeather(<val1>);
     * success = whereMrnWeather.upd(updMrnWeather);</pre>
     * will update NavEquipType to <val2> for all records where
     * stationId = <val1>.
     * @param  weather  A MrnWeather variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWeather weather) {
        return db.update (TABLE, createColVals(weather), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWeather updMrnWeather = new MrnWeather();
     * updMrnWeather.setNavEquipType(<val2>);
     * MrnWeather whereMrnWeather = new MrnWeather();
     * success = whereMrnWeather.upd(
     *     updMrnWeather, MrnWeather.STATION_ID+"=<val1>");</pre>
     * will update NavEquipType to <val2> for all records where
     * stationId = <val1>.
     * @param  weather  A MrnWeather variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWeather weather, String where) {
        return db.update (TABLE, createColVals(weather), where);
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
        if (getNavEquipType() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NAV_EQUIP_TYPE + "='" + getNavEquipType() + "'";
        } // if getNavEquipType
        if (getAtmosphPres() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ATMOSPH_PRES + "=" + getAtmosphPres("");
        } // if getAtmosphPres
        if (getSurfaceTmp() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURFACE_TMP + "=" + getSurfaceTmp("");
        } // if getSurfaceTmp
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
        if (getCloud() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLOUD + "='" + getCloud() + "'";
        } // if getCloud
        if (getVisCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + VIS_CODE + "='" + getVisCode() + "'";
        } // if getVisCode
        if (getWeatherCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WEATHER_CODE + "='" + getWeatherCode() + "'";
        } // if getWeatherCode
        if (getWaterColor() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATER_COLOR + "=" + getWaterColor("");
        } // if getWaterColor
        if (getTransparency() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TRANSPARENCY + "=" + getTransparency("");
        } // if getTransparency
        if (getWindDir() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_DIR + "=" + getWindDir("");
        } // if getWindDir
        if (getWindSpeed() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WIND_SPEED + "=" + getWindSpeed("");
        } // if getWindSpeed
        if (getSwellDir() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SWELL_DIR + "=" + getSwellDir("");
        } // if getSwellDir
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
        if (getDupflag() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DUPFLAG + "='" + getDupflag() + "'";
        } // if getDupflag
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnWeather aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getNavEquipType() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NAV_EQUIP_TYPE +"=";
            colVals += (aVar.getNavEquipType().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNavEquipType() + "'");
        } // if aVar.getNavEquipType
        if (aVar.getAtmosphPres() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ATMOSPH_PRES +"=";
            colVals += (aVar.getAtmosphPres() == FLOATNULL2 ?
                "null" : aVar.getAtmosphPres(""));
        } // if aVar.getAtmosphPres
        if (aVar.getSurfaceTmp() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURFACE_TMP +"=";
            colVals += (aVar.getSurfaceTmp() == FLOATNULL2 ?
                "null" : aVar.getSurfaceTmp(""));
        } // if aVar.getSurfaceTmp
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
        if (aVar.getCloud() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLOUD +"=";
            colVals += (aVar.getCloud().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCloud() + "'");
        } // if aVar.getCloud
        if (aVar.getVisCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += VIS_CODE +"=";
            colVals += (aVar.getVisCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getVisCode() + "'");
        } // if aVar.getVisCode
        if (aVar.getWeatherCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WEATHER_CODE +"=";
            colVals += (aVar.getWeatherCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getWeatherCode() + "'");
        } // if aVar.getWeatherCode
        if (aVar.getWaterColor() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATER_COLOR +"=";
            colVals += (aVar.getWaterColor() == INTNULL2 ?
                "null" : aVar.getWaterColor(""));
        } // if aVar.getWaterColor
        if (aVar.getTransparency() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TRANSPARENCY +"=";
            colVals += (aVar.getTransparency() == INTNULL2 ?
                "null" : aVar.getTransparency(""));
        } // if aVar.getTransparency
        if (aVar.getWindDir() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_DIR +"=";
            colVals += (aVar.getWindDir() == INTNULL2 ?
                "null" : aVar.getWindDir(""));
        } // if aVar.getWindDir
        if (aVar.getWindSpeed() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WIND_SPEED +"=";
            colVals += (aVar.getWindSpeed() == FLOATNULL2 ?
                "null" : aVar.getWindSpeed(""));
        } // if aVar.getWindSpeed
        if (aVar.getSwellDir() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SWELL_DIR +"=";
            colVals += (aVar.getSwellDir() == INTNULL2 ?
                "null" : aVar.getSwellDir(""));
        } // if aVar.getSwellDir
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
        if (aVar.getDupflag() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DUPFLAG +"=";
            colVals += (aVar.getDupflag().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDupflag() + "'");
        } // if aVar.getDupflag
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (getNavEquipType() != CHARNULL) {
            columns = columns + "," + NAV_EQUIP_TYPE;
        } // if getNavEquipType
        if (getAtmosphPres() != FLOATNULL) {
            columns = columns + "," + ATMOSPH_PRES;
        } // if getAtmosphPres
        if (getSurfaceTmp() != FLOATNULL) {
            columns = columns + "," + SURFACE_TMP;
        } // if getSurfaceTmp
        if (getDrybulb() != FLOATNULL) {
            columns = columns + "," + DRYBULB;
        } // if getDrybulb
        if (getWetbulb() != FLOATNULL) {
            columns = columns + "," + WETBULB;
        } // if getWetbulb
        if (getCloud() != CHARNULL) {
            columns = columns + "," + CLOUD;
        } // if getCloud
        if (getVisCode() != CHARNULL) {
            columns = columns + "," + VIS_CODE;
        } // if getVisCode
        if (getWeatherCode() != CHARNULL) {
            columns = columns + "," + WEATHER_CODE;
        } // if getWeatherCode
        if (getWaterColor() != INTNULL) {
            columns = columns + "," + WATER_COLOR;
        } // if getWaterColor
        if (getTransparency() != INTNULL) {
            columns = columns + "," + TRANSPARENCY;
        } // if getTransparency
        if (getWindDir() != INTNULL) {
            columns = columns + "," + WIND_DIR;
        } // if getWindDir
        if (getWindSpeed() != FLOATNULL) {
            columns = columns + "," + WIND_SPEED;
        } // if getWindSpeed
        if (getSwellDir() != INTNULL) {
            columns = columns + "," + SWELL_DIR;
        } // if getSwellDir
        if (getSwellHeight() != FLOATNULL) {
            columns = columns + "," + SWELL_HEIGHT;
        } // if getSwellHeight
        if (getSwellPeriod() != INTNULL) {
            columns = columns + "," + SWELL_PERIOD;
        } // if getSwellPeriod
        if (getDupflag() != CHARNULL) {
            columns = columns + "," + DUPFLAG;
        } // if getDupflag
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (getNavEquipType() != CHARNULL) {
            values  = values  + ",'" + getNavEquipType() + "'";
        } // if getNavEquipType
        if (getAtmosphPres() != FLOATNULL) {
            values  = values  + "," + getAtmosphPres("");
        } // if getAtmosphPres
        if (getSurfaceTmp() != FLOATNULL) {
            values  = values  + "," + getSurfaceTmp("");
        } // if getSurfaceTmp
        if (getDrybulb() != FLOATNULL) {
            values  = values  + "," + getDrybulb("");
        } // if getDrybulb
        if (getWetbulb() != FLOATNULL) {
            values  = values  + "," + getWetbulb("");
        } // if getWetbulb
        if (getCloud() != CHARNULL) {
            values  = values  + ",'" + getCloud() + "'";
        } // if getCloud
        if (getVisCode() != CHARNULL) {
            values  = values  + ",'" + getVisCode() + "'";
        } // if getVisCode
        if (getWeatherCode() != CHARNULL) {
            values  = values  + ",'" + getWeatherCode() + "'";
        } // if getWeatherCode
        if (getWaterColor() != INTNULL) {
            values  = values  + "," + getWaterColor("");
        } // if getWaterColor
        if (getTransparency() != INTNULL) {
            values  = values  + "," + getTransparency("");
        } // if getTransparency
        if (getWindDir() != INTNULL) {
            values  = values  + "," + getWindDir("");
        } // if getWindDir
        if (getWindSpeed() != FLOATNULL) {
            values  = values  + "," + getWindSpeed("");
        } // if getWindSpeed
        if (getSwellDir() != INTNULL) {
            values  = values  + "," + getSwellDir("");
        } // if getSwellDir
        if (getSwellHeight() != FLOATNULL) {
            values  = values  + "," + getSwellHeight("");
        } // if getSwellHeight
        if (getSwellPeriod() != INTNULL) {
            values  = values  + "," + getSwellPeriod("");
        } // if getSwellPeriod
        if (getDupflag() != CHARNULL) {
            values  = values  + ",'" + getDupflag() + "'";
        } // if getDupflag
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getStationId("")    + "|" +
            getNavEquipType("") + "|" +
            getAtmosphPres("")  + "|" +
            getSurfaceTmp("")   + "|" +
            getDrybulb("")      + "|" +
            getWetbulb("")      + "|" +
            getCloud("")        + "|" +
            getVisCode("")      + "|" +
            getWeatherCode("")  + "|" +
            getWaterColor("")   + "|" +
            getTransparency("") + "|" +
            getWindDir("")      + "|" +
            getWindSpeed("")    + "|" +
            getSwellDir("")     + "|" +
            getSwellHeight("")  + "|" +
            getSwellPeriod("")  + "|" +
            getDupflag("")      + "|";
    } // method toString

} // class MrnWeather
