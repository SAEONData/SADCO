package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WOATLAS5 table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 040720 - SIT Group
 * @version
 * 040720 - GenTableClassB class - create class<br>
 */
public class MrnWoatlas5 extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WOATLAS5";
    /** The latitude field name */
    public static final String LATITUDE = "LATITUDE";
    /** The longitude field name */
    public static final String LONGITUDE = "LONGITUDE";
    /** The depth field name */
    public static final String DEPTH = "DEPTH";
    /** The temperatureMin field name */
    public static final String TEMPERATURE_MIN = "TEMPERATURE_MIN";
    /** The temperatureMax field name */
    public static final String TEMPERATURE_MAX = "TEMPERATURE_MAX";
    /** The salinityMin field name */
    public static final String SALINITY_MIN = "SALINITY_MIN";
    /** The salinityMax field name */
    public static final String SALINITY_MAX = "SALINITY_MAX";
    /** The oxygenMin field name */
    public static final String OXYGEN_MIN = "OXYGEN_MIN";
    /** The oxygenMax field name */
    public static final String OXYGEN_MAX = "OXYGEN_MAX";
    /** The nitrateMin field name */
    public static final String NITRATE_MIN = "NITRATE_MIN";
    /** The nitrateMax field name */
    public static final String NITRATE_MAX = "NITRATE_MAX";
    /** The phosphateMin field name */
    public static final String PHOSPHATE_MIN = "PHOSPHATE_MIN";
    /** The phosphateMax field name */
    public static final String PHOSPHATE_MAX = "PHOSPHATE_MAX";
    /** The silicateMin field name */
    public static final String SILICATE_MIN = "SILICATE_MIN";
    /** The silicateMax field name */
    public static final String SILICATE_MAX = "SILICATE_MAX";
    /** The chlorophyllMin field name */
    public static final String CHLOROPHYLL_MIN = "CHLOROPHYLL_MIN";
    /** The chlorophyllMax field name */
    public static final String CHLOROPHYLL_MAX = "CHLOROPHYLL_MAX";

    /**
     * The instance variables corresponding to the table fields
     */
    private float     latitude;
    private float     longitude;
    private float     depth;
    private float     temperatureMin;
    private float     temperatureMax;
    private float     salinityMin;
    private float     salinityMax;
    private float     oxygenMin;
    private float     oxygenMax;
    private float     nitrateMin;
    private float     nitrateMax;
    private float     phosphateMin;
    private float     phosphateMax;
    private float     silicateMin;
    private float     silicateMax;
    private float     chlorophyllMin;
    private float     chlorophyllMax;

    /** The error variables  */
    private int latitudeError       = ERROR_NORMAL;
    private int longitudeError      = ERROR_NORMAL;
    private int depthError          = ERROR_NORMAL;
    private int temperatureMinError = ERROR_NORMAL;
    private int temperatureMaxError = ERROR_NORMAL;
    private int salinityMinError    = ERROR_NORMAL;
    private int salinityMaxError    = ERROR_NORMAL;
    private int oxygenMinError      = ERROR_NORMAL;
    private int oxygenMaxError      = ERROR_NORMAL;
    private int nitrateMinError     = ERROR_NORMAL;
    private int nitrateMaxError     = ERROR_NORMAL;
    private int phosphateMinError   = ERROR_NORMAL;
    private int phosphateMaxError   = ERROR_NORMAL;
    private int silicateMinError    = ERROR_NORMAL;
    private int silicateMaxError    = ERROR_NORMAL;
    private int chlorophyllMinError = ERROR_NORMAL;
    private int chlorophyllMaxError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String latitudeErrorMessage       = "";
    private String longitudeErrorMessage      = "";
    private String depthErrorMessage          = "";
    private String temperatureMinErrorMessage = "";
    private String temperatureMaxErrorMessage = "";
    private String salinityMinErrorMessage    = "";
    private String salinityMaxErrorMessage    = "";
    private String oxygenMinErrorMessage      = "";
    private String oxygenMaxErrorMessage      = "";
    private String nitrateMinErrorMessage     = "";
    private String nitrateMaxErrorMessage     = "";
    private String phosphateMinErrorMessage   = "";
    private String phosphateMaxErrorMessage   = "";
    private String silicateMinErrorMessage    = "";
    private String silicateMaxErrorMessage    = "";
    private String chlorophyllMinErrorMessage = "";
    private String chlorophyllMaxErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final float     LATITUDE_MN = FLOATMIN;
    public static final float     LATITUDE_MX = FLOATMAX;
    public static final float     LONGITUDE_MN = FLOATMIN;
    public static final float     LONGITUDE_MX = FLOATMAX;
    public static final float     DEPTH_MN = FLOATMIN;
    public static final float     DEPTH_MX = FLOATMAX;
    public static final float     TEMPERATURE_MIN_MN = FLOATMIN;
    public static final float     TEMPERATURE_MIN_MX = FLOATMAX;
    public static final float     TEMPERATURE_MAX_MN = FLOATMIN;
    public static final float     TEMPERATURE_MAX_MX = FLOATMAX;
    public static final float     SALINITY_MIN_MN = FLOATMIN;
    public static final float     SALINITY_MIN_MX = FLOATMAX;
    public static final float     SALINITY_MAX_MN = FLOATMIN;
    public static final float     SALINITY_MAX_MX = FLOATMAX;
    public static final float     OXYGEN_MIN_MN = FLOATMIN;
    public static final float     OXYGEN_MIN_MX = FLOATMAX;
    public static final float     OXYGEN_MAX_MN = FLOATMIN;
    public static final float     OXYGEN_MAX_MX = FLOATMAX;
    public static final float     NITRATE_MIN_MN = FLOATMIN;
    public static final float     NITRATE_MIN_MX = FLOATMAX;
    public static final float     NITRATE_MAX_MN = FLOATMIN;
    public static final float     NITRATE_MAX_MX = FLOATMAX;
    public static final float     PHOSPHATE_MIN_MN = FLOATMIN;
    public static final float     PHOSPHATE_MIN_MX = FLOATMAX;
    public static final float     PHOSPHATE_MAX_MN = FLOATMIN;
    public static final float     PHOSPHATE_MAX_MX = FLOATMAX;
    public static final float     SILICATE_MIN_MN = FLOATMIN;
    public static final float     SILICATE_MIN_MX = FLOATMAX;
    public static final float     SILICATE_MAX_MN = FLOATMIN;
    public static final float     SILICATE_MAX_MX = FLOATMAX;
    public static final float     CHLOROPHYLL_MIN_MN = FLOATMIN;
    public static final float     CHLOROPHYLL_MIN_MX = FLOATMAX;
    public static final float     CHLOROPHYLL_MAX_MN = FLOATMIN;
    public static final float     CHLOROPHYLL_MAX_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception latitudeOutOfBoundsException =
        new Exception ("'latitude' out of bounds: " +
            LATITUDE_MN + " - " + LATITUDE_MX);
    Exception longitudeOutOfBoundsException =
        new Exception ("'longitude' out of bounds: " +
            LONGITUDE_MN + " - " + LONGITUDE_MX);
    Exception depthOutOfBoundsException =
        new Exception ("'depth' out of bounds: " +
            DEPTH_MN + " - " + DEPTH_MX);
    Exception temperatureMinOutOfBoundsException =
        new Exception ("'temperatureMin' out of bounds: " +
            TEMPERATURE_MIN_MN + " - " + TEMPERATURE_MIN_MX);
    Exception temperatureMaxOutOfBoundsException =
        new Exception ("'temperatureMax' out of bounds: " +
            TEMPERATURE_MAX_MN + " - " + TEMPERATURE_MAX_MX);
    Exception salinityMinOutOfBoundsException =
        new Exception ("'salinityMin' out of bounds: " +
            SALINITY_MIN_MN + " - " + SALINITY_MIN_MX);
    Exception salinityMaxOutOfBoundsException =
        new Exception ("'salinityMax' out of bounds: " +
            SALINITY_MAX_MN + " - " + SALINITY_MAX_MX);
    Exception oxygenMinOutOfBoundsException =
        new Exception ("'oxygenMin' out of bounds: " +
            OXYGEN_MIN_MN + " - " + OXYGEN_MIN_MX);
    Exception oxygenMaxOutOfBoundsException =
        new Exception ("'oxygenMax' out of bounds: " +
            OXYGEN_MAX_MN + " - " + OXYGEN_MAX_MX);
    Exception nitrateMinOutOfBoundsException =
        new Exception ("'nitrateMin' out of bounds: " +
            NITRATE_MIN_MN + " - " + NITRATE_MIN_MX);
    Exception nitrateMaxOutOfBoundsException =
        new Exception ("'nitrateMax' out of bounds: " +
            NITRATE_MAX_MN + " - " + NITRATE_MAX_MX);
    Exception phosphateMinOutOfBoundsException =
        new Exception ("'phosphateMin' out of bounds: " +
            PHOSPHATE_MIN_MN + " - " + PHOSPHATE_MIN_MX);
    Exception phosphateMaxOutOfBoundsException =
        new Exception ("'phosphateMax' out of bounds: " +
            PHOSPHATE_MAX_MN + " - " + PHOSPHATE_MAX_MX);
    Exception silicateMinOutOfBoundsException =
        new Exception ("'silicateMin' out of bounds: " +
            SILICATE_MIN_MN + " - " + SILICATE_MIN_MX);
    Exception silicateMaxOutOfBoundsException =
        new Exception ("'silicateMax' out of bounds: " +
            SILICATE_MAX_MN + " - " + SILICATE_MAX_MX);
    Exception chlorophyllMinOutOfBoundsException =
        new Exception ("'chlorophyllMin' out of bounds: " +
            CHLOROPHYLL_MIN_MN + " - " + CHLOROPHYLL_MIN_MX);
    Exception chlorophyllMaxOutOfBoundsException =
        new Exception ("'chlorophyllMax' out of bounds: " +
            CHLOROPHYLL_MAX_MN + " - " + CHLOROPHYLL_MAX_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnWoatlas5() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnWoatlas5 constructor 1"); // debug
    } // MrnWoatlas5 constructor

    /**
     * Instantiate a MrnWoatlas5 object and initialize the instance variables.
     * @param latitude  The latitude (float)
     */
    public MrnWoatlas5(
            float latitude) {
        this();
        setLatitude       (latitude      );
        if (dbg) System.out.println ("<br>in MrnWoatlas5 constructor 2"); // debug
    } // MrnWoatlas5 constructor

    /**
     * Instantiate a MrnWoatlas5 object and initialize the instance variables.
     * @param latitude        The latitude       (float)
     * @param longitude       The longitude      (float)
     * @param depth           The depth          (float)
     * @param temperatureMin  The temperatureMin (float)
     * @param temperatureMax  The temperatureMax (float)
     * @param salinityMin     The salinityMin    (float)
     * @param salinityMax     The salinityMax    (float)
     * @param oxygenMin       The oxygenMin      (float)
     * @param oxygenMax       The oxygenMax      (float)
     * @param nitrateMin      The nitrateMin     (float)
     * @param nitrateMax      The nitrateMax     (float)
     * @param phosphateMin    The phosphateMin   (float)
     * @param phosphateMax    The phosphateMax   (float)
     * @param silicateMin     The silicateMin    (float)
     * @param silicateMax     The silicateMax    (float)
     * @param chlorophyllMin  The chlorophyllMin (float)
     * @param chlorophyllMax  The chlorophyllMax (float)
     */
    public MrnWoatlas5(
            float latitude,
            float longitude,
            float depth,
            float temperatureMin,
            float temperatureMax,
            float salinityMin,
            float salinityMax,
            float oxygenMin,
            float oxygenMax,
            float nitrateMin,
            float nitrateMax,
            float phosphateMin,
            float phosphateMax,
            float silicateMin,
            float silicateMax,
            float chlorophyllMin,
            float chlorophyllMax) {
        this();
        setLatitude       (latitude      );
        setLongitude      (longitude     );
        setDepth          (depth         );
        setTemperatureMin (temperatureMin);
        setTemperatureMax (temperatureMax);
        setSalinityMin    (salinityMin   );
        setSalinityMax    (salinityMax   );
        setOxygenMin      (oxygenMin     );
        setOxygenMax      (oxygenMax     );
        setNitrateMin     (nitrateMin    );
        setNitrateMax     (nitrateMax    );
        setPhosphateMin   (phosphateMin  );
        setPhosphateMax   (phosphateMax  );
        setSilicateMin    (silicateMin   );
        setSilicateMax    (silicateMax   );
        setChlorophyllMin (chlorophyllMin);
        setChlorophyllMax (chlorophyllMax);
        if (dbg) System.out.println ("<br>in MrnWoatlas5 constructor 3"); // debug
    } // MrnWoatlas5 constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setLatitude       (FLOATNULL);
        setLongitude      (FLOATNULL);
        setDepth          (FLOATNULL);
        setTemperatureMin (FLOATNULL);
        setTemperatureMax (FLOATNULL);
        setSalinityMin    (FLOATNULL);
        setSalinityMax    (FLOATNULL);
        setOxygenMin      (FLOATNULL);
        setOxygenMax      (FLOATNULL);
        setNitrateMin     (FLOATNULL);
        setNitrateMax     (FLOATNULL);
        setPhosphateMin   (FLOATNULL);
        setPhosphateMax   (FLOATNULL);
        setSilicateMin    (FLOATNULL);
        setSilicateMax    (FLOATNULL);
        setChlorophyllMin (FLOATNULL);
        setChlorophyllMax (FLOATNULL);
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
     * Set the 'depth' class variable
     * @param  depth (float)
     */
    public int setDepth(float depth) {
        try {
            if ( ((depth == FLOATNULL) ||
                  (depth == FLOATNULL2)) ||
                !((depth < DEPTH_MN) ||
                  (depth > DEPTH_MX)) ) {
                this.depth = depth;
                depthError = ERROR_NORMAL;
            } else {
                throw depthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDepthError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return depthError;
    } // method setDepth

    /**
     * Set the 'depth' class variable
     * @param  depth (Integer)
     */
    public int setDepth(Integer depth) {
        try {
            setDepth(depth.floatValue());
        } catch (Exception e) {
            setDepthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return depthError;
    } // method setDepth

    /**
     * Set the 'depth' class variable
     * @param  depth (Float)
     */
    public int setDepth(Float depth) {
        try {
            setDepth(depth.floatValue());
        } catch (Exception e) {
            setDepthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return depthError;
    } // method setDepth

    /**
     * Set the 'depth' class variable
     * @param  depth (String)
     */
    public int setDepth(String depth) {
        try {
            setDepth(new Float(depth).floatValue());
        } catch (Exception e) {
            setDepthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return depthError;
    } // method setDepth

    /**
     * Called when an exception has occured
     * @param  depth (String)
     */
    private void setDepthError (float depth, Exception e, int error) {
        this.depth = depth;
        depthErrorMessage = e.toString();
        depthError = error;
    } // method setDepthError


    /**
     * Set the 'temperatureMin' class variable
     * @param  temperatureMin (float)
     */
    public int setTemperatureMin(float temperatureMin) {
        try {
            if ( ((temperatureMin == FLOATNULL) ||
                  (temperatureMin == FLOATNULL2)) ||
                !((temperatureMin < TEMPERATURE_MIN_MN) ||
                  (temperatureMin > TEMPERATURE_MIN_MX)) ) {
                this.temperatureMin = temperatureMin;
                temperatureMinError = ERROR_NORMAL;
            } else {
                throw temperatureMinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTemperatureMinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return temperatureMinError;
    } // method setTemperatureMin

    /**
     * Set the 'temperatureMin' class variable
     * @param  temperatureMin (Integer)
     */
    public int setTemperatureMin(Integer temperatureMin) {
        try {
            setTemperatureMin(temperatureMin.floatValue());
        } catch (Exception e) {
            setTemperatureMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureMinError;
    } // method setTemperatureMin

    /**
     * Set the 'temperatureMin' class variable
     * @param  temperatureMin (Float)
     */
    public int setTemperatureMin(Float temperatureMin) {
        try {
            setTemperatureMin(temperatureMin.floatValue());
        } catch (Exception e) {
            setTemperatureMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureMinError;
    } // method setTemperatureMin

    /**
     * Set the 'temperatureMin' class variable
     * @param  temperatureMin (String)
     */
    public int setTemperatureMin(String temperatureMin) {
        try {
            setTemperatureMin(new Float(temperatureMin).floatValue());
        } catch (Exception e) {
            setTemperatureMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureMinError;
    } // method setTemperatureMin

    /**
     * Called when an exception has occured
     * @param  temperatureMin (String)
     */
    private void setTemperatureMinError (float temperatureMin, Exception e, int error) {
        this.temperatureMin = temperatureMin;
        temperatureMinErrorMessage = e.toString();
        temperatureMinError = error;
    } // method setTemperatureMinError


    /**
     * Set the 'temperatureMax' class variable
     * @param  temperatureMax (float)
     */
    public int setTemperatureMax(float temperatureMax) {
        try {
            if ( ((temperatureMax == FLOATNULL) ||
                  (temperatureMax == FLOATNULL2)) ||
                !((temperatureMax < TEMPERATURE_MAX_MN) ||
                  (temperatureMax > TEMPERATURE_MAX_MX)) ) {
                this.temperatureMax = temperatureMax;
                temperatureMaxError = ERROR_NORMAL;
            } else {
                throw temperatureMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTemperatureMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return temperatureMaxError;
    } // method setTemperatureMax

    /**
     * Set the 'temperatureMax' class variable
     * @param  temperatureMax (Integer)
     */
    public int setTemperatureMax(Integer temperatureMax) {
        try {
            setTemperatureMax(temperatureMax.floatValue());
        } catch (Exception e) {
            setTemperatureMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureMaxError;
    } // method setTemperatureMax

    /**
     * Set the 'temperatureMax' class variable
     * @param  temperatureMax (Float)
     */
    public int setTemperatureMax(Float temperatureMax) {
        try {
            setTemperatureMax(temperatureMax.floatValue());
        } catch (Exception e) {
            setTemperatureMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureMaxError;
    } // method setTemperatureMax

    /**
     * Set the 'temperatureMax' class variable
     * @param  temperatureMax (String)
     */
    public int setTemperatureMax(String temperatureMax) {
        try {
            setTemperatureMax(new Float(temperatureMax).floatValue());
        } catch (Exception e) {
            setTemperatureMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureMaxError;
    } // method setTemperatureMax

    /**
     * Called when an exception has occured
     * @param  temperatureMax (String)
     */
    private void setTemperatureMaxError (float temperatureMax, Exception e, int error) {
        this.temperatureMax = temperatureMax;
        temperatureMaxErrorMessage = e.toString();
        temperatureMaxError = error;
    } // method setTemperatureMaxError


    /**
     * Set the 'salinityMin' class variable
     * @param  salinityMin (float)
     */
    public int setSalinityMin(float salinityMin) {
        try {
            if ( ((salinityMin == FLOATNULL) ||
                  (salinityMin == FLOATNULL2)) ||
                !((salinityMin < SALINITY_MIN_MN) ||
                  (salinityMin > SALINITY_MIN_MX)) ) {
                this.salinityMin = salinityMin;
                salinityMinError = ERROR_NORMAL;
            } else {
                throw salinityMinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSalinityMinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return salinityMinError;
    } // method setSalinityMin

    /**
     * Set the 'salinityMin' class variable
     * @param  salinityMin (Integer)
     */
    public int setSalinityMin(Integer salinityMin) {
        try {
            setSalinityMin(salinityMin.floatValue());
        } catch (Exception e) {
            setSalinityMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return salinityMinError;
    } // method setSalinityMin

    /**
     * Set the 'salinityMin' class variable
     * @param  salinityMin (Float)
     */
    public int setSalinityMin(Float salinityMin) {
        try {
            setSalinityMin(salinityMin.floatValue());
        } catch (Exception e) {
            setSalinityMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return salinityMinError;
    } // method setSalinityMin

    /**
     * Set the 'salinityMin' class variable
     * @param  salinityMin (String)
     */
    public int setSalinityMin(String salinityMin) {
        try {
            setSalinityMin(new Float(salinityMin).floatValue());
        } catch (Exception e) {
            setSalinityMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return salinityMinError;
    } // method setSalinityMin

    /**
     * Called when an exception has occured
     * @param  salinityMin (String)
     */
    private void setSalinityMinError (float salinityMin, Exception e, int error) {
        this.salinityMin = salinityMin;
        salinityMinErrorMessage = e.toString();
        salinityMinError = error;
    } // method setSalinityMinError


    /**
     * Set the 'salinityMax' class variable
     * @param  salinityMax (float)
     */
    public int setSalinityMax(float salinityMax) {
        try {
            if ( ((salinityMax == FLOATNULL) ||
                  (salinityMax == FLOATNULL2)) ||
                !((salinityMax < SALINITY_MAX_MN) ||
                  (salinityMax > SALINITY_MAX_MX)) ) {
                this.salinityMax = salinityMax;
                salinityMaxError = ERROR_NORMAL;
            } else {
                throw salinityMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSalinityMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return salinityMaxError;
    } // method setSalinityMax

    /**
     * Set the 'salinityMax' class variable
     * @param  salinityMax (Integer)
     */
    public int setSalinityMax(Integer salinityMax) {
        try {
            setSalinityMax(salinityMax.floatValue());
        } catch (Exception e) {
            setSalinityMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return salinityMaxError;
    } // method setSalinityMax

    /**
     * Set the 'salinityMax' class variable
     * @param  salinityMax (Float)
     */
    public int setSalinityMax(Float salinityMax) {
        try {
            setSalinityMax(salinityMax.floatValue());
        } catch (Exception e) {
            setSalinityMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return salinityMaxError;
    } // method setSalinityMax

    /**
     * Set the 'salinityMax' class variable
     * @param  salinityMax (String)
     */
    public int setSalinityMax(String salinityMax) {
        try {
            setSalinityMax(new Float(salinityMax).floatValue());
        } catch (Exception e) {
            setSalinityMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return salinityMaxError;
    } // method setSalinityMax

    /**
     * Called when an exception has occured
     * @param  salinityMax (String)
     */
    private void setSalinityMaxError (float salinityMax, Exception e, int error) {
        this.salinityMax = salinityMax;
        salinityMaxErrorMessage = e.toString();
        salinityMaxError = error;
    } // method setSalinityMaxError


    /**
     * Set the 'oxygenMin' class variable
     * @param  oxygenMin (float)
     */
    public int setOxygenMin(float oxygenMin) {
        try {
            if ( ((oxygenMin == FLOATNULL) ||
                  (oxygenMin == FLOATNULL2)) ||
                !((oxygenMin < OXYGEN_MIN_MN) ||
                  (oxygenMin > OXYGEN_MIN_MX)) ) {
                this.oxygenMin = oxygenMin;
                oxygenMinError = ERROR_NORMAL;
            } else {
                throw oxygenMinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setOxygenMinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return oxygenMinError;
    } // method setOxygenMin

    /**
     * Set the 'oxygenMin' class variable
     * @param  oxygenMin (Integer)
     */
    public int setOxygenMin(Integer oxygenMin) {
        try {
            setOxygenMin(oxygenMin.floatValue());
        } catch (Exception e) {
            setOxygenMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return oxygenMinError;
    } // method setOxygenMin

    /**
     * Set the 'oxygenMin' class variable
     * @param  oxygenMin (Float)
     */
    public int setOxygenMin(Float oxygenMin) {
        try {
            setOxygenMin(oxygenMin.floatValue());
        } catch (Exception e) {
            setOxygenMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return oxygenMinError;
    } // method setOxygenMin

    /**
     * Set the 'oxygenMin' class variable
     * @param  oxygenMin (String)
     */
    public int setOxygenMin(String oxygenMin) {
        try {
            setOxygenMin(new Float(oxygenMin).floatValue());
        } catch (Exception e) {
            setOxygenMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return oxygenMinError;
    } // method setOxygenMin

    /**
     * Called when an exception has occured
     * @param  oxygenMin (String)
     */
    private void setOxygenMinError (float oxygenMin, Exception e, int error) {
        this.oxygenMin = oxygenMin;
        oxygenMinErrorMessage = e.toString();
        oxygenMinError = error;
    } // method setOxygenMinError


    /**
     * Set the 'oxygenMax' class variable
     * @param  oxygenMax (float)
     */
    public int setOxygenMax(float oxygenMax) {
        try {
            if ( ((oxygenMax == FLOATNULL) ||
                  (oxygenMax == FLOATNULL2)) ||
                !((oxygenMax < OXYGEN_MAX_MN) ||
                  (oxygenMax > OXYGEN_MAX_MX)) ) {
                this.oxygenMax = oxygenMax;
                oxygenMaxError = ERROR_NORMAL;
            } else {
                throw oxygenMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setOxygenMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return oxygenMaxError;
    } // method setOxygenMax

    /**
     * Set the 'oxygenMax' class variable
     * @param  oxygenMax (Integer)
     */
    public int setOxygenMax(Integer oxygenMax) {
        try {
            setOxygenMax(oxygenMax.floatValue());
        } catch (Exception e) {
            setOxygenMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return oxygenMaxError;
    } // method setOxygenMax

    /**
     * Set the 'oxygenMax' class variable
     * @param  oxygenMax (Float)
     */
    public int setOxygenMax(Float oxygenMax) {
        try {
            setOxygenMax(oxygenMax.floatValue());
        } catch (Exception e) {
            setOxygenMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return oxygenMaxError;
    } // method setOxygenMax

    /**
     * Set the 'oxygenMax' class variable
     * @param  oxygenMax (String)
     */
    public int setOxygenMax(String oxygenMax) {
        try {
            setOxygenMax(new Float(oxygenMax).floatValue());
        } catch (Exception e) {
            setOxygenMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return oxygenMaxError;
    } // method setOxygenMax

    /**
     * Called when an exception has occured
     * @param  oxygenMax (String)
     */
    private void setOxygenMaxError (float oxygenMax, Exception e, int error) {
        this.oxygenMax = oxygenMax;
        oxygenMaxErrorMessage = e.toString();
        oxygenMaxError = error;
    } // method setOxygenMaxError


    /**
     * Set the 'nitrateMin' class variable
     * @param  nitrateMin (float)
     */
    public int setNitrateMin(float nitrateMin) {
        try {
            if ( ((nitrateMin == FLOATNULL) ||
                  (nitrateMin == FLOATNULL2)) ||
                !((nitrateMin < NITRATE_MIN_MN) ||
                  (nitrateMin > NITRATE_MIN_MX)) ) {
                this.nitrateMin = nitrateMin;
                nitrateMinError = ERROR_NORMAL;
            } else {
                throw nitrateMinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNitrateMinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return nitrateMinError;
    } // method setNitrateMin

    /**
     * Set the 'nitrateMin' class variable
     * @param  nitrateMin (Integer)
     */
    public int setNitrateMin(Integer nitrateMin) {
        try {
            setNitrateMin(nitrateMin.floatValue());
        } catch (Exception e) {
            setNitrateMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nitrateMinError;
    } // method setNitrateMin

    /**
     * Set the 'nitrateMin' class variable
     * @param  nitrateMin (Float)
     */
    public int setNitrateMin(Float nitrateMin) {
        try {
            setNitrateMin(nitrateMin.floatValue());
        } catch (Exception e) {
            setNitrateMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nitrateMinError;
    } // method setNitrateMin

    /**
     * Set the 'nitrateMin' class variable
     * @param  nitrateMin (String)
     */
    public int setNitrateMin(String nitrateMin) {
        try {
            setNitrateMin(new Float(nitrateMin).floatValue());
        } catch (Exception e) {
            setNitrateMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nitrateMinError;
    } // method setNitrateMin

    /**
     * Called when an exception has occured
     * @param  nitrateMin (String)
     */
    private void setNitrateMinError (float nitrateMin, Exception e, int error) {
        this.nitrateMin = nitrateMin;
        nitrateMinErrorMessage = e.toString();
        nitrateMinError = error;
    } // method setNitrateMinError


    /**
     * Set the 'nitrateMax' class variable
     * @param  nitrateMax (float)
     */
    public int setNitrateMax(float nitrateMax) {
        try {
            if ( ((nitrateMax == FLOATNULL) ||
                  (nitrateMax == FLOATNULL2)) ||
                !((nitrateMax < NITRATE_MAX_MN) ||
                  (nitrateMax > NITRATE_MAX_MX)) ) {
                this.nitrateMax = nitrateMax;
                nitrateMaxError = ERROR_NORMAL;
            } else {
                throw nitrateMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNitrateMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return nitrateMaxError;
    } // method setNitrateMax

    /**
     * Set the 'nitrateMax' class variable
     * @param  nitrateMax (Integer)
     */
    public int setNitrateMax(Integer nitrateMax) {
        try {
            setNitrateMax(nitrateMax.floatValue());
        } catch (Exception e) {
            setNitrateMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nitrateMaxError;
    } // method setNitrateMax

    /**
     * Set the 'nitrateMax' class variable
     * @param  nitrateMax (Float)
     */
    public int setNitrateMax(Float nitrateMax) {
        try {
            setNitrateMax(nitrateMax.floatValue());
        } catch (Exception e) {
            setNitrateMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nitrateMaxError;
    } // method setNitrateMax

    /**
     * Set the 'nitrateMax' class variable
     * @param  nitrateMax (String)
     */
    public int setNitrateMax(String nitrateMax) {
        try {
            setNitrateMax(new Float(nitrateMax).floatValue());
        } catch (Exception e) {
            setNitrateMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nitrateMaxError;
    } // method setNitrateMax

    /**
     * Called when an exception has occured
     * @param  nitrateMax (String)
     */
    private void setNitrateMaxError (float nitrateMax, Exception e, int error) {
        this.nitrateMax = nitrateMax;
        nitrateMaxErrorMessage = e.toString();
        nitrateMaxError = error;
    } // method setNitrateMaxError


    /**
     * Set the 'phosphateMin' class variable
     * @param  phosphateMin (float)
     */
    public int setPhosphateMin(float phosphateMin) {
        try {
            if ( ((phosphateMin == FLOATNULL) ||
                  (phosphateMin == FLOATNULL2)) ||
                !((phosphateMin < PHOSPHATE_MIN_MN) ||
                  (phosphateMin > PHOSPHATE_MIN_MX)) ) {
                this.phosphateMin = phosphateMin;
                phosphateMinError = ERROR_NORMAL;
            } else {
                throw phosphateMinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPhosphateMinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return phosphateMinError;
    } // method setPhosphateMin

    /**
     * Set the 'phosphateMin' class variable
     * @param  phosphateMin (Integer)
     */
    public int setPhosphateMin(Integer phosphateMin) {
        try {
            setPhosphateMin(phosphateMin.floatValue());
        } catch (Exception e) {
            setPhosphateMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phosphateMinError;
    } // method setPhosphateMin

    /**
     * Set the 'phosphateMin' class variable
     * @param  phosphateMin (Float)
     */
    public int setPhosphateMin(Float phosphateMin) {
        try {
            setPhosphateMin(phosphateMin.floatValue());
        } catch (Exception e) {
            setPhosphateMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phosphateMinError;
    } // method setPhosphateMin

    /**
     * Set the 'phosphateMin' class variable
     * @param  phosphateMin (String)
     */
    public int setPhosphateMin(String phosphateMin) {
        try {
            setPhosphateMin(new Float(phosphateMin).floatValue());
        } catch (Exception e) {
            setPhosphateMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phosphateMinError;
    } // method setPhosphateMin

    /**
     * Called when an exception has occured
     * @param  phosphateMin (String)
     */
    private void setPhosphateMinError (float phosphateMin, Exception e, int error) {
        this.phosphateMin = phosphateMin;
        phosphateMinErrorMessage = e.toString();
        phosphateMinError = error;
    } // method setPhosphateMinError


    /**
     * Set the 'phosphateMax' class variable
     * @param  phosphateMax (float)
     */
    public int setPhosphateMax(float phosphateMax) {
        try {
            if ( ((phosphateMax == FLOATNULL) ||
                  (phosphateMax == FLOATNULL2)) ||
                !((phosphateMax < PHOSPHATE_MAX_MN) ||
                  (phosphateMax > PHOSPHATE_MAX_MX)) ) {
                this.phosphateMax = phosphateMax;
                phosphateMaxError = ERROR_NORMAL;
            } else {
                throw phosphateMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPhosphateMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return phosphateMaxError;
    } // method setPhosphateMax

    /**
     * Set the 'phosphateMax' class variable
     * @param  phosphateMax (Integer)
     */
    public int setPhosphateMax(Integer phosphateMax) {
        try {
            setPhosphateMax(phosphateMax.floatValue());
        } catch (Exception e) {
            setPhosphateMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phosphateMaxError;
    } // method setPhosphateMax

    /**
     * Set the 'phosphateMax' class variable
     * @param  phosphateMax (Float)
     */
    public int setPhosphateMax(Float phosphateMax) {
        try {
            setPhosphateMax(phosphateMax.floatValue());
        } catch (Exception e) {
            setPhosphateMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phosphateMaxError;
    } // method setPhosphateMax

    /**
     * Set the 'phosphateMax' class variable
     * @param  phosphateMax (String)
     */
    public int setPhosphateMax(String phosphateMax) {
        try {
            setPhosphateMax(new Float(phosphateMax).floatValue());
        } catch (Exception e) {
            setPhosphateMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phosphateMaxError;
    } // method setPhosphateMax

    /**
     * Called when an exception has occured
     * @param  phosphateMax (String)
     */
    private void setPhosphateMaxError (float phosphateMax, Exception e, int error) {
        this.phosphateMax = phosphateMax;
        phosphateMaxErrorMessage = e.toString();
        phosphateMaxError = error;
    } // method setPhosphateMaxError


    /**
     * Set the 'silicateMin' class variable
     * @param  silicateMin (float)
     */
    public int setSilicateMin(float silicateMin) {
        try {
            if ( ((silicateMin == FLOATNULL) ||
                  (silicateMin == FLOATNULL2)) ||
                !((silicateMin < SILICATE_MIN_MN) ||
                  (silicateMin > SILICATE_MIN_MX)) ) {
                this.silicateMin = silicateMin;
                silicateMinError = ERROR_NORMAL;
            } else {
                throw silicateMinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSilicateMinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return silicateMinError;
    } // method setSilicateMin

    /**
     * Set the 'silicateMin' class variable
     * @param  silicateMin (Integer)
     */
    public int setSilicateMin(Integer silicateMin) {
        try {
            setSilicateMin(silicateMin.floatValue());
        } catch (Exception e) {
            setSilicateMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return silicateMinError;
    } // method setSilicateMin

    /**
     * Set the 'silicateMin' class variable
     * @param  silicateMin (Float)
     */
    public int setSilicateMin(Float silicateMin) {
        try {
            setSilicateMin(silicateMin.floatValue());
        } catch (Exception e) {
            setSilicateMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return silicateMinError;
    } // method setSilicateMin

    /**
     * Set the 'silicateMin' class variable
     * @param  silicateMin (String)
     */
    public int setSilicateMin(String silicateMin) {
        try {
            setSilicateMin(new Float(silicateMin).floatValue());
        } catch (Exception e) {
            setSilicateMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return silicateMinError;
    } // method setSilicateMin

    /**
     * Called when an exception has occured
     * @param  silicateMin (String)
     */
    private void setSilicateMinError (float silicateMin, Exception e, int error) {
        this.silicateMin = silicateMin;
        silicateMinErrorMessage = e.toString();
        silicateMinError = error;
    } // method setSilicateMinError


    /**
     * Set the 'silicateMax' class variable
     * @param  silicateMax (float)
     */
    public int setSilicateMax(float silicateMax) {
        try {
            if ( ((silicateMax == FLOATNULL) ||
                  (silicateMax == FLOATNULL2)) ||
                !((silicateMax < SILICATE_MAX_MN) ||
                  (silicateMax > SILICATE_MAX_MX)) ) {
                this.silicateMax = silicateMax;
                silicateMaxError = ERROR_NORMAL;
            } else {
                throw silicateMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSilicateMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return silicateMaxError;
    } // method setSilicateMax

    /**
     * Set the 'silicateMax' class variable
     * @param  silicateMax (Integer)
     */
    public int setSilicateMax(Integer silicateMax) {
        try {
            setSilicateMax(silicateMax.floatValue());
        } catch (Exception e) {
            setSilicateMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return silicateMaxError;
    } // method setSilicateMax

    /**
     * Set the 'silicateMax' class variable
     * @param  silicateMax (Float)
     */
    public int setSilicateMax(Float silicateMax) {
        try {
            setSilicateMax(silicateMax.floatValue());
        } catch (Exception e) {
            setSilicateMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return silicateMaxError;
    } // method setSilicateMax

    /**
     * Set the 'silicateMax' class variable
     * @param  silicateMax (String)
     */
    public int setSilicateMax(String silicateMax) {
        try {
            setSilicateMax(new Float(silicateMax).floatValue());
        } catch (Exception e) {
            setSilicateMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return silicateMaxError;
    } // method setSilicateMax

    /**
     * Called when an exception has occured
     * @param  silicateMax (String)
     */
    private void setSilicateMaxError (float silicateMax, Exception e, int error) {
        this.silicateMax = silicateMax;
        silicateMaxErrorMessage = e.toString();
        silicateMaxError = error;
    } // method setSilicateMaxError


    /**
     * Set the 'chlorophyllMin' class variable
     * @param  chlorophyllMin (float)
     */
    public int setChlorophyllMin(float chlorophyllMin) {
        try {
            if ( ((chlorophyllMin == FLOATNULL) ||
                  (chlorophyllMin == FLOATNULL2)) ||
                !((chlorophyllMin < CHLOROPHYLL_MIN_MN) ||
                  (chlorophyllMin > CHLOROPHYLL_MIN_MX)) ) {
                this.chlorophyllMin = chlorophyllMin;
                chlorophyllMinError = ERROR_NORMAL;
            } else {
                throw chlorophyllMinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setChlorophyllMinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return chlorophyllMinError;
    } // method setChlorophyllMin

    /**
     * Set the 'chlorophyllMin' class variable
     * @param  chlorophyllMin (Integer)
     */
    public int setChlorophyllMin(Integer chlorophyllMin) {
        try {
            setChlorophyllMin(chlorophyllMin.floatValue());
        } catch (Exception e) {
            setChlorophyllMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlorophyllMinError;
    } // method setChlorophyllMin

    /**
     * Set the 'chlorophyllMin' class variable
     * @param  chlorophyllMin (Float)
     */
    public int setChlorophyllMin(Float chlorophyllMin) {
        try {
            setChlorophyllMin(chlorophyllMin.floatValue());
        } catch (Exception e) {
            setChlorophyllMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlorophyllMinError;
    } // method setChlorophyllMin

    /**
     * Set the 'chlorophyllMin' class variable
     * @param  chlorophyllMin (String)
     */
    public int setChlorophyllMin(String chlorophyllMin) {
        try {
            setChlorophyllMin(new Float(chlorophyllMin).floatValue());
        } catch (Exception e) {
            setChlorophyllMinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlorophyllMinError;
    } // method setChlorophyllMin

    /**
     * Called when an exception has occured
     * @param  chlorophyllMin (String)
     */
    private void setChlorophyllMinError (float chlorophyllMin, Exception e, int error) {
        this.chlorophyllMin = chlorophyllMin;
        chlorophyllMinErrorMessage = e.toString();
        chlorophyllMinError = error;
    } // method setChlorophyllMinError


    /**
     * Set the 'chlorophyllMax' class variable
     * @param  chlorophyllMax (float)
     */
    public int setChlorophyllMax(float chlorophyllMax) {
        try {
            if ( ((chlorophyllMax == FLOATNULL) ||
                  (chlorophyllMax == FLOATNULL2)) ||
                !((chlorophyllMax < CHLOROPHYLL_MAX_MN) ||
                  (chlorophyllMax > CHLOROPHYLL_MAX_MX)) ) {
                this.chlorophyllMax = chlorophyllMax;
                chlorophyllMaxError = ERROR_NORMAL;
            } else {
                throw chlorophyllMaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setChlorophyllMaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return chlorophyllMaxError;
    } // method setChlorophyllMax

    /**
     * Set the 'chlorophyllMax' class variable
     * @param  chlorophyllMax (Integer)
     */
    public int setChlorophyllMax(Integer chlorophyllMax) {
        try {
            setChlorophyllMax(chlorophyllMax.floatValue());
        } catch (Exception e) {
            setChlorophyllMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlorophyllMaxError;
    } // method setChlorophyllMax

    /**
     * Set the 'chlorophyllMax' class variable
     * @param  chlorophyllMax (Float)
     */
    public int setChlorophyllMax(Float chlorophyllMax) {
        try {
            setChlorophyllMax(chlorophyllMax.floatValue());
        } catch (Exception e) {
            setChlorophyllMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlorophyllMaxError;
    } // method setChlorophyllMax

    /**
     * Set the 'chlorophyllMax' class variable
     * @param  chlorophyllMax (String)
     */
    public int setChlorophyllMax(String chlorophyllMax) {
        try {
            setChlorophyllMax(new Float(chlorophyllMax).floatValue());
        } catch (Exception e) {
            setChlorophyllMaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chlorophyllMaxError;
    } // method setChlorophyllMax

    /**
     * Called when an exception has occured
     * @param  chlorophyllMax (String)
     */
    private void setChlorophyllMaxError (float chlorophyllMax, Exception e, int error) {
        this.chlorophyllMax = chlorophyllMax;
        chlorophyllMaxErrorMessage = e.toString();
        chlorophyllMaxError = error;
    } // method setChlorophyllMaxError


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
        return ((latitude != FLOATNULL) ? new Float(latitude).toString() : "");
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
        return ((longitude != FLOATNULL) ? new Float(longitude).toString() : "");
    } // method getLongitude


    /**
     * Return the 'depth' class variable
     * @return depth (float)
     */
    public float getDepth() {
        return depth;
    } // method getDepth

    /**
     * Return the 'depth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDepth methods
     * @return depth (String)
     */
    public String getDepth(String s) {
        return ((depth != FLOATNULL) ? new Float(depth).toString() : "");
    } // method getDepth


    /**
     * Return the 'temperatureMin' class variable
     * @return temperatureMin (float)
     */
    public float getTemperatureMin() {
        return temperatureMin;
    } // method getTemperatureMin

    /**
     * Return the 'temperatureMin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTemperatureMin methods
     * @return temperatureMin (String)
     */
    public String getTemperatureMin(String s) {
        return ((temperatureMin != FLOATNULL) ? new Float(temperatureMin).toString() : "");
    } // method getTemperatureMin


    /**
     * Return the 'temperatureMax' class variable
     * @return temperatureMax (float)
     */
    public float getTemperatureMax() {
        return temperatureMax;
    } // method getTemperatureMax

    /**
     * Return the 'temperatureMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTemperatureMax methods
     * @return temperatureMax (String)
     */
    public String getTemperatureMax(String s) {
        return ((temperatureMax != FLOATNULL) ? new Float(temperatureMax).toString() : "");
    } // method getTemperatureMax


    /**
     * Return the 'salinityMin' class variable
     * @return salinityMin (float)
     */
    public float getSalinityMin() {
        return salinityMin;
    } // method getSalinityMin

    /**
     * Return the 'salinityMin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSalinityMin methods
     * @return salinityMin (String)
     */
    public String getSalinityMin(String s) {
        return ((salinityMin != FLOATNULL) ? new Float(salinityMin).toString() : "");
    } // method getSalinityMin


    /**
     * Return the 'salinityMax' class variable
     * @return salinityMax (float)
     */
    public float getSalinityMax() {
        return salinityMax;
    } // method getSalinityMax

    /**
     * Return the 'salinityMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSalinityMax methods
     * @return salinityMax (String)
     */
    public String getSalinityMax(String s) {
        return ((salinityMax != FLOATNULL) ? new Float(salinityMax).toString() : "");
    } // method getSalinityMax


    /**
     * Return the 'oxygenMin' class variable
     * @return oxygenMin (float)
     */
    public float getOxygenMin() {
        return oxygenMin;
    } // method getOxygenMin

    /**
     * Return the 'oxygenMin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getOxygenMin methods
     * @return oxygenMin (String)
     */
    public String getOxygenMin(String s) {
        return ((oxygenMin != FLOATNULL) ? new Float(oxygenMin).toString() : "");
    } // method getOxygenMin


    /**
     * Return the 'oxygenMax' class variable
     * @return oxygenMax (float)
     */
    public float getOxygenMax() {
        return oxygenMax;
    } // method getOxygenMax

    /**
     * Return the 'oxygenMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getOxygenMax methods
     * @return oxygenMax (String)
     */
    public String getOxygenMax(String s) {
        return ((oxygenMax != FLOATNULL) ? new Float(oxygenMax).toString() : "");
    } // method getOxygenMax


    /**
     * Return the 'nitrateMin' class variable
     * @return nitrateMin (float)
     */
    public float getNitrateMin() {
        return nitrateMin;
    } // method getNitrateMin

    /**
     * Return the 'nitrateMin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNitrateMin methods
     * @return nitrateMin (String)
     */
    public String getNitrateMin(String s) {
        return ((nitrateMin != FLOATNULL) ? new Float(nitrateMin).toString() : "");
    } // method getNitrateMin


    /**
     * Return the 'nitrateMax' class variable
     * @return nitrateMax (float)
     */
    public float getNitrateMax() {
        return nitrateMax;
    } // method getNitrateMax

    /**
     * Return the 'nitrateMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNitrateMax methods
     * @return nitrateMax (String)
     */
    public String getNitrateMax(String s) {
        return ((nitrateMax != FLOATNULL) ? new Float(nitrateMax).toString() : "");
    } // method getNitrateMax


    /**
     * Return the 'phosphateMin' class variable
     * @return phosphateMin (float)
     */
    public float getPhosphateMin() {
        return phosphateMin;
    } // method getPhosphateMin

    /**
     * Return the 'phosphateMin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPhosphateMin methods
     * @return phosphateMin (String)
     */
    public String getPhosphateMin(String s) {
        return ((phosphateMin != FLOATNULL) ? new Float(phosphateMin).toString() : "");
    } // method getPhosphateMin


    /**
     * Return the 'phosphateMax' class variable
     * @return phosphateMax (float)
     */
    public float getPhosphateMax() {
        return phosphateMax;
    } // method getPhosphateMax

    /**
     * Return the 'phosphateMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPhosphateMax methods
     * @return phosphateMax (String)
     */
    public String getPhosphateMax(String s) {
        return ((phosphateMax != FLOATNULL) ? new Float(phosphateMax).toString() : "");
    } // method getPhosphateMax


    /**
     * Return the 'silicateMin' class variable
     * @return silicateMin (float)
     */
    public float getSilicateMin() {
        return silicateMin;
    } // method getSilicateMin

    /**
     * Return the 'silicateMin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSilicateMin methods
     * @return silicateMin (String)
     */
    public String getSilicateMin(String s) {
        return ((silicateMin != FLOATNULL) ? new Float(silicateMin).toString() : "");
    } // method getSilicateMin


    /**
     * Return the 'silicateMax' class variable
     * @return silicateMax (float)
     */
    public float getSilicateMax() {
        return silicateMax;
    } // method getSilicateMax

    /**
     * Return the 'silicateMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSilicateMax methods
     * @return silicateMax (String)
     */
    public String getSilicateMax(String s) {
        return ((silicateMax != FLOATNULL) ? new Float(silicateMax).toString() : "");
    } // method getSilicateMax


    /**
     * Return the 'chlorophyllMin' class variable
     * @return chlorophyllMin (float)
     */
    public float getChlorophyllMin() {
        return chlorophyllMin;
    } // method getChlorophyllMin

    /**
     * Return the 'chlorophyllMin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getChlorophyllMin methods
     * @return chlorophyllMin (String)
     */
    public String getChlorophyllMin(String s) {
        return ((chlorophyllMin != FLOATNULL) ? new Float(chlorophyllMin).toString() : "");
    } // method getChlorophyllMin


    /**
     * Return the 'chlorophyllMax' class variable
     * @return chlorophyllMax (float)
     */
    public float getChlorophyllMax() {
        return chlorophyllMax;
    } // method getChlorophyllMax

    /**
     * Return the 'chlorophyllMax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getChlorophyllMax methods
     * @return chlorophyllMax (String)
     */
    public String getChlorophyllMax(String s) {
        return ((chlorophyllMax != FLOATNULL) ? new Float(chlorophyllMax).toString() : "");
    } // method getChlorophyllMax


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
        if ((latitude == FLOATNULL) &&
            (longitude == FLOATNULL) &&
            (depth == FLOATNULL) &&
            (temperatureMin == FLOATNULL) &&
            (temperatureMax == FLOATNULL) &&
            (salinityMin == FLOATNULL) &&
            (salinityMax == FLOATNULL) &&
            (oxygenMin == FLOATNULL) &&
            (oxygenMax == FLOATNULL) &&
            (nitrateMin == FLOATNULL) &&
            (nitrateMax == FLOATNULL) &&
            (phosphateMin == FLOATNULL) &&
            (phosphateMax == FLOATNULL) &&
            (silicateMin == FLOATNULL) &&
            (silicateMax == FLOATNULL) &&
            (chlorophyllMin == FLOATNULL) &&
            (chlorophyllMax == FLOATNULL)) {
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
            depthError +
            temperatureMinError +
            temperatureMaxError +
            salinityMinError +
            salinityMaxError +
            oxygenMinError +
            oxygenMaxError +
            nitrateMinError +
            nitrateMaxError +
            phosphateMinError +
            phosphateMaxError +
            silicateMinError +
            silicateMaxError +
            chlorophyllMinError +
            chlorophyllMaxError;
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
     * Gets the errorcode for the depth instance variable
     * @return errorcode (int)
     */
    public int getDepthError() {
        return depthError;
    } // method getDepthError

    /**
     * Gets the errorMessage for the depth instance variable
     * @return errorMessage (String)
     */
    public String getDepthErrorMessage() {
        return depthErrorMessage;
    } // method getDepthErrorMessage


    /**
     * Gets the errorcode for the temperatureMin instance variable
     * @return errorcode (int)
     */
    public int getTemperatureMinError() {
        return temperatureMinError;
    } // method getTemperatureMinError

    /**
     * Gets the errorMessage for the temperatureMin instance variable
     * @return errorMessage (String)
     */
    public String getTemperatureMinErrorMessage() {
        return temperatureMinErrorMessage;
    } // method getTemperatureMinErrorMessage


    /**
     * Gets the errorcode for the temperatureMax instance variable
     * @return errorcode (int)
     */
    public int getTemperatureMaxError() {
        return temperatureMaxError;
    } // method getTemperatureMaxError

    /**
     * Gets the errorMessage for the temperatureMax instance variable
     * @return errorMessage (String)
     */
    public String getTemperatureMaxErrorMessage() {
        return temperatureMaxErrorMessage;
    } // method getTemperatureMaxErrorMessage


    /**
     * Gets the errorcode for the salinityMin instance variable
     * @return errorcode (int)
     */
    public int getSalinityMinError() {
        return salinityMinError;
    } // method getSalinityMinError

    /**
     * Gets the errorMessage for the salinityMin instance variable
     * @return errorMessage (String)
     */
    public String getSalinityMinErrorMessage() {
        return salinityMinErrorMessage;
    } // method getSalinityMinErrorMessage


    /**
     * Gets the errorcode for the salinityMax instance variable
     * @return errorcode (int)
     */
    public int getSalinityMaxError() {
        return salinityMaxError;
    } // method getSalinityMaxError

    /**
     * Gets the errorMessage for the salinityMax instance variable
     * @return errorMessage (String)
     */
    public String getSalinityMaxErrorMessage() {
        return salinityMaxErrorMessage;
    } // method getSalinityMaxErrorMessage


    /**
     * Gets the errorcode for the oxygenMin instance variable
     * @return errorcode (int)
     */
    public int getOxygenMinError() {
        return oxygenMinError;
    } // method getOxygenMinError

    /**
     * Gets the errorMessage for the oxygenMin instance variable
     * @return errorMessage (String)
     */
    public String getOxygenMinErrorMessage() {
        return oxygenMinErrorMessage;
    } // method getOxygenMinErrorMessage


    /**
     * Gets the errorcode for the oxygenMax instance variable
     * @return errorcode (int)
     */
    public int getOxygenMaxError() {
        return oxygenMaxError;
    } // method getOxygenMaxError

    /**
     * Gets the errorMessage for the oxygenMax instance variable
     * @return errorMessage (String)
     */
    public String getOxygenMaxErrorMessage() {
        return oxygenMaxErrorMessage;
    } // method getOxygenMaxErrorMessage


    /**
     * Gets the errorcode for the nitrateMin instance variable
     * @return errorcode (int)
     */
    public int getNitrateMinError() {
        return nitrateMinError;
    } // method getNitrateMinError

    /**
     * Gets the errorMessage for the nitrateMin instance variable
     * @return errorMessage (String)
     */
    public String getNitrateMinErrorMessage() {
        return nitrateMinErrorMessage;
    } // method getNitrateMinErrorMessage


    /**
     * Gets the errorcode for the nitrateMax instance variable
     * @return errorcode (int)
     */
    public int getNitrateMaxError() {
        return nitrateMaxError;
    } // method getNitrateMaxError

    /**
     * Gets the errorMessage for the nitrateMax instance variable
     * @return errorMessage (String)
     */
    public String getNitrateMaxErrorMessage() {
        return nitrateMaxErrorMessage;
    } // method getNitrateMaxErrorMessage


    /**
     * Gets the errorcode for the phosphateMin instance variable
     * @return errorcode (int)
     */
    public int getPhosphateMinError() {
        return phosphateMinError;
    } // method getPhosphateMinError

    /**
     * Gets the errorMessage for the phosphateMin instance variable
     * @return errorMessage (String)
     */
    public String getPhosphateMinErrorMessage() {
        return phosphateMinErrorMessage;
    } // method getPhosphateMinErrorMessage


    /**
     * Gets the errorcode for the phosphateMax instance variable
     * @return errorcode (int)
     */
    public int getPhosphateMaxError() {
        return phosphateMaxError;
    } // method getPhosphateMaxError

    /**
     * Gets the errorMessage for the phosphateMax instance variable
     * @return errorMessage (String)
     */
    public String getPhosphateMaxErrorMessage() {
        return phosphateMaxErrorMessage;
    } // method getPhosphateMaxErrorMessage


    /**
     * Gets the errorcode for the silicateMin instance variable
     * @return errorcode (int)
     */
    public int getSilicateMinError() {
        return silicateMinError;
    } // method getSilicateMinError

    /**
     * Gets the errorMessage for the silicateMin instance variable
     * @return errorMessage (String)
     */
    public String getSilicateMinErrorMessage() {
        return silicateMinErrorMessage;
    } // method getSilicateMinErrorMessage


    /**
     * Gets the errorcode for the silicateMax instance variable
     * @return errorcode (int)
     */
    public int getSilicateMaxError() {
        return silicateMaxError;
    } // method getSilicateMaxError

    /**
     * Gets the errorMessage for the silicateMax instance variable
     * @return errorMessage (String)
     */
    public String getSilicateMaxErrorMessage() {
        return silicateMaxErrorMessage;
    } // method getSilicateMaxErrorMessage


    /**
     * Gets the errorcode for the chlorophyllMin instance variable
     * @return errorcode (int)
     */
    public int getChlorophyllMinError() {
        return chlorophyllMinError;
    } // method getChlorophyllMinError

    /**
     * Gets the errorMessage for the chlorophyllMin instance variable
     * @return errorMessage (String)
     */
    public String getChlorophyllMinErrorMessage() {
        return chlorophyllMinErrorMessage;
    } // method getChlorophyllMinErrorMessage


    /**
     * Gets the errorcode for the chlorophyllMax instance variable
     * @return errorcode (int)
     */
    public int getChlorophyllMaxError() {
        return chlorophyllMaxError;
    } // method getChlorophyllMaxError

    /**
     * Gets the errorMessage for the chlorophyllMax instance variable
     * @return errorMessage (String)
     */
    public String getChlorophyllMaxErrorMessage() {
        return chlorophyllMaxErrorMessage;
    } // method getChlorophyllMaxErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnWoatlas5. e.g.<pre>
     * MrnWoatlas5 woatlas5 = new MrnWoatlas5(<val1>);
     * MrnWoatlas5 woatlas5Array[] = woatlas5.get();</pre>
     * will get the MrnWoatlas5 record where latitude = <val1>.
     * @return Array of MrnWoatlas5 (MrnWoatlas5[])
     */
    public MrnWoatlas5[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnWoatlas5 woatlas5Array[] =
     *     new MrnWoatlas5().get(MrnWoatlas5.LATITUDE+"=<val1>");</pre>
     * will get the MrnWoatlas5 record where latitude = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnWoatlas5 (MrnWoatlas5[])
     */
    public MrnWoatlas5[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnWoatlas5 woatlas5Array[] =
     *     new MrnWoatlas5().get("1=1",woatlas5.LATITUDE);</pre>
     * will get the all the MrnWoatlas5 records, and order them by latitude.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWoatlas5 (MrnWoatlas5[])
     */
    public MrnWoatlas5[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnWoatlas5.LATITUDE,MrnWoatlas5.LONGITUDE;
     * String where = MrnWoatlas5.LATITUDE + "=<val1";
     * String order = MrnWoatlas5.LATITUDE;
     * MrnWoatlas5 woatlas5Array[] =
     *     new MrnWoatlas5().get(columns, where, order);</pre>
     * will get the latitude and longitude colums of all MrnWoatlas5 records,
     * where latitude = <val1>, and order them by latitude.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWoatlas5 (MrnWoatlas5[])
     */
    public MrnWoatlas5[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnWoatlas5.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnWoatlas5[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int latitudeCol       = db.getColNumber(LATITUDE);
        int longitudeCol      = db.getColNumber(LONGITUDE);
        int depthCol          = db.getColNumber(DEPTH);
        int temperatureMinCol = db.getColNumber(TEMPERATURE_MIN);
        int temperatureMaxCol = db.getColNumber(TEMPERATURE_MAX);
        int salinityMinCol    = db.getColNumber(SALINITY_MIN);
        int salinityMaxCol    = db.getColNumber(SALINITY_MAX);
        int oxygenMinCol      = db.getColNumber(OXYGEN_MIN);
        int oxygenMaxCol      = db.getColNumber(OXYGEN_MAX);
        int nitrateMinCol     = db.getColNumber(NITRATE_MIN);
        int nitrateMaxCol     = db.getColNumber(NITRATE_MAX);
        int phosphateMinCol   = db.getColNumber(PHOSPHATE_MIN);
        int phosphateMaxCol   = db.getColNumber(PHOSPHATE_MAX);
        int silicateMinCol    = db.getColNumber(SILICATE_MIN);
        int silicateMaxCol    = db.getColNumber(SILICATE_MAX);
        int chlorophyllMinCol = db.getColNumber(CHLOROPHYLL_MIN);
        int chlorophyllMaxCol = db.getColNumber(CHLOROPHYLL_MAX);
        MrnWoatlas5[] cArray = new MrnWoatlas5[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnWoatlas5();
            if (latitudeCol != -1)
                cArray[i].setLatitude      ((String) row.elementAt(latitudeCol));
            if (longitudeCol != -1)
                cArray[i].setLongitude     ((String) row.elementAt(longitudeCol));
            if (depthCol != -1)
                cArray[i].setDepth         ((String) row.elementAt(depthCol));
            if (temperatureMinCol != -1)
                cArray[i].setTemperatureMin((String) row.elementAt(temperatureMinCol));
            if (temperatureMaxCol != -1)
                cArray[i].setTemperatureMax((String) row.elementAt(temperatureMaxCol));
            if (salinityMinCol != -1)
                cArray[i].setSalinityMin   ((String) row.elementAt(salinityMinCol));
            if (salinityMaxCol != -1)
                cArray[i].setSalinityMax   ((String) row.elementAt(salinityMaxCol));
            if (oxygenMinCol != -1)
                cArray[i].setOxygenMin     ((String) row.elementAt(oxygenMinCol));
            if (oxygenMaxCol != -1)
                cArray[i].setOxygenMax     ((String) row.elementAt(oxygenMaxCol));
            if (nitrateMinCol != -1)
                cArray[i].setNitrateMin    ((String) row.elementAt(nitrateMinCol));
            if (nitrateMaxCol != -1)
                cArray[i].setNitrateMax    ((String) row.elementAt(nitrateMaxCol));
            if (phosphateMinCol != -1)
                cArray[i].setPhosphateMin  ((String) row.elementAt(phosphateMinCol));
            if (phosphateMaxCol != -1)
                cArray[i].setPhosphateMax  ((String) row.elementAt(phosphateMaxCol));
            if (silicateMinCol != -1)
                cArray[i].setSilicateMin   ((String) row.elementAt(silicateMinCol));
            if (silicateMaxCol != -1)
                cArray[i].setSilicateMax   ((String) row.elementAt(silicateMaxCol));
            if (chlorophyllMinCol != -1)
                cArray[i].setChlorophyllMin((String) row.elementAt(chlorophyllMinCol));
            if (chlorophyllMaxCol != -1)
                cArray[i].setChlorophyllMax((String) row.elementAt(chlorophyllMaxCol));
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
     *     new MrnWoatlas5(
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
     *     latitude       = <val1>,
     *     longitude      = <val2>,
     *     depth          = <val3>,
     *     temperatureMin = <val4>,
     *     temperatureMax = <val5>,
     *     salinityMin    = <val6>,
     *     salinityMax    = <val7>,
     *     oxygenMin      = <val8>,
     *     oxygenMax      = <val9>,
     *     nitrateMin     = <val10>,
     *     nitrateMax     = <val11>,
     *     phosphateMin   = <val12>,
     *     phosphateMax   = <val13>,
     *     silicateMin    = <val14>,
     *     silicateMax    = <val15>,
     *     chlorophyllMin = <val16>,
     *     chlorophyllMax = <val17>.
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
     * Boolean success = new MrnWoatlas5(
     *     Tables.FLOATNULL,
     *     <val2>,
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
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where longitude = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWoatlas5 woatlas5 = new MrnWoatlas5();
     * success = woatlas5.del(MrnWoatlas5.LATITUDE+"=<val1>");</pre>
     * will delete all records where latitude = <val1>.
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
     * update are taken from the MrnWoatlas5 argument, .e.g.<pre>
     * Boolean success
     * MrnWoatlas5 updMrnWoatlas5 = new MrnWoatlas5();
     * updMrnWoatlas5.setLongitude(<val2>);
     * MrnWoatlas5 whereMrnWoatlas5 = new MrnWoatlas5(<val1>);
     * success = whereMrnWoatlas5.upd(updMrnWoatlas5);</pre>
     * will update Longitude to <val2> for all records where
     * latitude = <val1>.
     * @param  woatlas5  A MrnWoatlas5 variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWoatlas5 woatlas5) {
        return db.update (TABLE, createColVals(woatlas5), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWoatlas5 updMrnWoatlas5 = new MrnWoatlas5();
     * updMrnWoatlas5.setLongitude(<val2>);
     * MrnWoatlas5 whereMrnWoatlas5 = new MrnWoatlas5();
     * success = whereMrnWoatlas5.upd(
     *     updMrnWoatlas5, MrnWoatlas5.LATITUDE+"=<val1>");</pre>
     * will update Longitude to <val2> for all records where
     * latitude = <val1>.
     * @param  woatlas5  A MrnWoatlas5 variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWoatlas5 woatlas5, String where) {
        return db.update (TABLE, createColVals(woatlas5), where);
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
        if (getDepth() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEPTH + "=" + getDepth("");
        } // if getDepth
        if (getTemperatureMin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TEMPERATURE_MIN + "=" + getTemperatureMin("");
        } // if getTemperatureMin
        if (getTemperatureMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TEMPERATURE_MAX + "=" + getTemperatureMax("");
        } // if getTemperatureMax
        if (getSalinityMin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SALINITY_MIN + "=" + getSalinityMin("");
        } // if getSalinityMin
        if (getSalinityMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SALINITY_MAX + "=" + getSalinityMax("");
        } // if getSalinityMax
        if (getOxygenMin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + OXYGEN_MIN + "=" + getOxygenMin("");
        } // if getOxygenMin
        if (getOxygenMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + OXYGEN_MAX + "=" + getOxygenMax("");
        } // if getOxygenMax
        if (getNitrateMin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NITRATE_MIN + "=" + getNitrateMin("");
        } // if getNitrateMin
        if (getNitrateMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NITRATE_MAX + "=" + getNitrateMax("");
        } // if getNitrateMax
        if (getPhosphateMin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PHOSPHATE_MIN + "=" + getPhosphateMin("");
        } // if getPhosphateMin
        if (getPhosphateMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PHOSPHATE_MAX + "=" + getPhosphateMax("");
        } // if getPhosphateMax
        if (getSilicateMin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SILICATE_MIN + "=" + getSilicateMin("");
        } // if getSilicateMin
        if (getSilicateMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SILICATE_MAX + "=" + getSilicateMax("");
        } // if getSilicateMax
        if (getChlorophyllMin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CHLOROPHYLL_MIN + "=" + getChlorophyllMin("");
        } // if getChlorophyllMin
        if (getChlorophyllMax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CHLOROPHYLL_MAX + "=" + getChlorophyllMax("");
        } // if getChlorophyllMax
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnWoatlas5 aVar) {
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
        if (aVar.getDepth() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEPTH +"=";
            colVals += (aVar.getDepth() == FLOATNULL2 ?
                "null" : aVar.getDepth(""));
        } // if aVar.getDepth
        if (aVar.getTemperatureMin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TEMPERATURE_MIN +"=";
            colVals += (aVar.getTemperatureMin() == FLOATNULL2 ?
                "null" : aVar.getTemperatureMin(""));
        } // if aVar.getTemperatureMin
        if (aVar.getTemperatureMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TEMPERATURE_MAX +"=";
            colVals += (aVar.getTemperatureMax() == FLOATNULL2 ?
                "null" : aVar.getTemperatureMax(""));
        } // if aVar.getTemperatureMax
        if (aVar.getSalinityMin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SALINITY_MIN +"=";
            colVals += (aVar.getSalinityMin() == FLOATNULL2 ?
                "null" : aVar.getSalinityMin(""));
        } // if aVar.getSalinityMin
        if (aVar.getSalinityMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SALINITY_MAX +"=";
            colVals += (aVar.getSalinityMax() == FLOATNULL2 ?
                "null" : aVar.getSalinityMax(""));
        } // if aVar.getSalinityMax
        if (aVar.getOxygenMin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += OXYGEN_MIN +"=";
            colVals += (aVar.getOxygenMin() == FLOATNULL2 ?
                "null" : aVar.getOxygenMin(""));
        } // if aVar.getOxygenMin
        if (aVar.getOxygenMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += OXYGEN_MAX +"=";
            colVals += (aVar.getOxygenMax() == FLOATNULL2 ?
                "null" : aVar.getOxygenMax(""));
        } // if aVar.getOxygenMax
        if (aVar.getNitrateMin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NITRATE_MIN +"=";
            colVals += (aVar.getNitrateMin() == FLOATNULL2 ?
                "null" : aVar.getNitrateMin(""));
        } // if aVar.getNitrateMin
        if (aVar.getNitrateMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NITRATE_MAX +"=";
            colVals += (aVar.getNitrateMax() == FLOATNULL2 ?
                "null" : aVar.getNitrateMax(""));
        } // if aVar.getNitrateMax
        if (aVar.getPhosphateMin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PHOSPHATE_MIN +"=";
            colVals += (aVar.getPhosphateMin() == FLOATNULL2 ?
                "null" : aVar.getPhosphateMin(""));
        } // if aVar.getPhosphateMin
        if (aVar.getPhosphateMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PHOSPHATE_MAX +"=";
            colVals += (aVar.getPhosphateMax() == FLOATNULL2 ?
                "null" : aVar.getPhosphateMax(""));
        } // if aVar.getPhosphateMax
        if (aVar.getSilicateMin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SILICATE_MIN +"=";
            colVals += (aVar.getSilicateMin() == FLOATNULL2 ?
                "null" : aVar.getSilicateMin(""));
        } // if aVar.getSilicateMin
        if (aVar.getSilicateMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SILICATE_MAX +"=";
            colVals += (aVar.getSilicateMax() == FLOATNULL2 ?
                "null" : aVar.getSilicateMax(""));
        } // if aVar.getSilicateMax
        if (aVar.getChlorophyllMin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CHLOROPHYLL_MIN +"=";
            colVals += (aVar.getChlorophyllMin() == FLOATNULL2 ?
                "null" : aVar.getChlorophyllMin(""));
        } // if aVar.getChlorophyllMin
        if (aVar.getChlorophyllMax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CHLOROPHYLL_MAX +"=";
            colVals += (aVar.getChlorophyllMax() == FLOATNULL2 ?
                "null" : aVar.getChlorophyllMax(""));
        } // if aVar.getChlorophyllMax
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = LATITUDE;
        if (getLongitude() != FLOATNULL) {
            columns = columns + "," + LONGITUDE;
        } // if getLongitude
        if (getDepth() != FLOATNULL) {
            columns = columns + "," + DEPTH;
        } // if getDepth
        if (getTemperatureMin() != FLOATNULL) {
            columns = columns + "," + TEMPERATURE_MIN;
        } // if getTemperatureMin
        if (getTemperatureMax() != FLOATNULL) {
            columns = columns + "," + TEMPERATURE_MAX;
        } // if getTemperatureMax
        if (getSalinityMin() != FLOATNULL) {
            columns = columns + "," + SALINITY_MIN;
        } // if getSalinityMin
        if (getSalinityMax() != FLOATNULL) {
            columns = columns + "," + SALINITY_MAX;
        } // if getSalinityMax
        if (getOxygenMin() != FLOATNULL) {
            columns = columns + "," + OXYGEN_MIN;
        } // if getOxygenMin
        if (getOxygenMax() != FLOATNULL) {
            columns = columns + "," + OXYGEN_MAX;
        } // if getOxygenMax
        if (getNitrateMin() != FLOATNULL) {
            columns = columns + "," + NITRATE_MIN;
        } // if getNitrateMin
        if (getNitrateMax() != FLOATNULL) {
            columns = columns + "," + NITRATE_MAX;
        } // if getNitrateMax
        if (getPhosphateMin() != FLOATNULL) {
            columns = columns + "," + PHOSPHATE_MIN;
        } // if getPhosphateMin
        if (getPhosphateMax() != FLOATNULL) {
            columns = columns + "," + PHOSPHATE_MAX;
        } // if getPhosphateMax
        if (getSilicateMin() != FLOATNULL) {
            columns = columns + "," + SILICATE_MIN;
        } // if getSilicateMin
        if (getSilicateMax() != FLOATNULL) {
            columns = columns + "," + SILICATE_MAX;
        } // if getSilicateMax
        if (getChlorophyllMin() != FLOATNULL) {
            columns = columns + "," + CHLOROPHYLL_MIN;
        } // if getChlorophyllMin
        if (getChlorophyllMax() != FLOATNULL) {
            columns = columns + "," + CHLOROPHYLL_MAX;
        } // if getChlorophyllMax
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getLatitude("");
        if (getLongitude() != FLOATNULL) {
            values  = values  + "," + getLongitude("");
        } // if getLongitude
        if (getDepth() != FLOATNULL) {
            values  = values  + "," + getDepth("");
        } // if getDepth
        if (getTemperatureMin() != FLOATNULL) {
            values  = values  + "," + getTemperatureMin("");
        } // if getTemperatureMin
        if (getTemperatureMax() != FLOATNULL) {
            values  = values  + "," + getTemperatureMax("");
        } // if getTemperatureMax
        if (getSalinityMin() != FLOATNULL) {
            values  = values  + "," + getSalinityMin("");
        } // if getSalinityMin
        if (getSalinityMax() != FLOATNULL) {
            values  = values  + "," + getSalinityMax("");
        } // if getSalinityMax
        if (getOxygenMin() != FLOATNULL) {
            values  = values  + "," + getOxygenMin("");
        } // if getOxygenMin
        if (getOxygenMax() != FLOATNULL) {
            values  = values  + "," + getOxygenMax("");
        } // if getOxygenMax
        if (getNitrateMin() != FLOATNULL) {
            values  = values  + "," + getNitrateMin("");
        } // if getNitrateMin
        if (getNitrateMax() != FLOATNULL) {
            values  = values  + "," + getNitrateMax("");
        } // if getNitrateMax
        if (getPhosphateMin() != FLOATNULL) {
            values  = values  + "," + getPhosphateMin("");
        } // if getPhosphateMin
        if (getPhosphateMax() != FLOATNULL) {
            values  = values  + "," + getPhosphateMax("");
        } // if getPhosphateMax
        if (getSilicateMin() != FLOATNULL) {
            values  = values  + "," + getSilicateMin("");
        } // if getSilicateMin
        if (getSilicateMax() != FLOATNULL) {
            values  = values  + "," + getSilicateMax("");
        } // if getSilicateMax
        if (getChlorophyllMin() != FLOATNULL) {
            values  = values  + "," + getChlorophyllMin("");
        } // if getChlorophyllMin
        if (getChlorophyllMax() != FLOATNULL) {
            values  = values  + "," + getChlorophyllMax("");
        } // if getChlorophyllMax
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getLatitude("")       + "|" +
            getLongitude("")      + "|" +
            getDepth("")          + "|" +
            getTemperatureMin("") + "|" +
            getTemperatureMax("") + "|" +
            getSalinityMin("")    + "|" +
            getSalinityMax("")    + "|" +
            getOxygenMin("")      + "|" +
            getOxygenMax("")      + "|" +
            getNitrateMin("")     + "|" +
            getNitrateMax("")     + "|" +
            getPhosphateMin("")   + "|" +
            getPhosphateMax("")   + "|" +
            getSilicateMin("")    + "|" +
            getSilicateMax("")    + "|" +
            getChlorophyllMin("") + "|" +
            getChlorophyllMax("") + "|";
    } // method toString

} // class MrnWoatlas5
