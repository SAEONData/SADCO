package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WAV_STATION table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 080222 - SIT Group
 * @version
 * 080222 - GenTableClassB class - create class<br>
 */
public class WavStation extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WAV_STATION";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The latitude field name */
    public static final String LATITUDE = "LATITUDE";
    /** The longitude field name */
    public static final String LONGITUDE = "LONGITUDE";
    /** The instrumentDepth field name */
    public static final String INSTRUMENT_DEPTH = "INSTRUMENT_DEPTH";
    /** The name field name */
    public static final String NAME = "NAME";
    /** The waterDepth field name */
    public static final String WATER_DEPTH = "WATER_DEPTH";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private float     latitude;
    private float     longitude;
    private int       instrumentDepth;
    private String    name;
    private int       waterDepth;
    private String    surveyId;

    /** The error variables  */
    private int stationIdError       = ERROR_NORMAL;
    private int latitudeError        = ERROR_NORMAL;
    private int longitudeError       = ERROR_NORMAL;
    private int instrumentDepthError = ERROR_NORMAL;
    private int nameError            = ERROR_NORMAL;
    private int waterDepthError      = ERROR_NORMAL;
    private int surveyIdError        = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage       = "";
    private String latitudeErrorMessage        = "";
    private String longitudeErrorMessage       = "";
    private String instrumentDepthErrorMessage = "";
    private String nameErrorMessage            = "";
    private String waterDepthErrorMessage      = "";
    private String surveyIdErrorMessage        = "";

    /** The min-max constants for all numerics */
    public static final float     LATITUDE_MN = FLOATMIN;
    public static final float     LATITUDE_MX = FLOATMAX;
    public static final float     LONGITUDE_MN = FLOATMIN;
    public static final float     LONGITUDE_MX = FLOATMAX;
    public static final int       INSTRUMENT_DEPTH_MN = INTMIN;
    public static final int       INSTRUMENT_DEPTH_MX = INTMAX;
    public static final int       WATER_DEPTH_MN = INTMIN;
    public static final int       WATER_DEPTH_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception latitudeOutOfBoundsException =
        new Exception ("'latitude' out of bounds: " +
            LATITUDE_MN + " - " + LATITUDE_MX);
    Exception longitudeOutOfBoundsException =
        new Exception ("'longitude' out of bounds: " +
            LONGITUDE_MN + " - " + LONGITUDE_MX);
    Exception instrumentDepthOutOfBoundsException =
        new Exception ("'instrumentDepth' out of bounds: " +
            INSTRUMENT_DEPTH_MN + " - " + INSTRUMENT_DEPTH_MX);
    Exception waterDepthOutOfBoundsException =
        new Exception ("'waterDepth' out of bounds: " +
            WATER_DEPTH_MN + " - " + WATER_DEPTH_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public WavStation() {
        clearVars();
        if (dbg) System.out.println ("<br>in WavStation constructor 1"); // debug
    } // WavStation constructor

    /**
     * Instantiate a WavStation object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public WavStation(
            String stationId) {
        this();
        setStationId       (stationId      );
        if (dbg) System.out.println ("<br>in WavStation constructor 2"); // debug
    } // WavStation constructor

    /**
     * Instantiate a WavStation object and initialize the instance variables.
     * @param stationId        The stationId       (String)
     * @param latitude         The latitude        (float)
     * @param longitude        The longitude       (float)
     * @param instrumentDepth  The instrumentDepth (int)
     * @param name             The name            (String)
     * @param waterDepth       The waterDepth      (int)
     * @param surveyId         The surveyId        (String)
     * @return A WavStation object
     */
    public WavStation(
            String stationId,
            float  latitude,
            float  longitude,
            int    instrumentDepth,
            String name,
            int    waterDepth,
            String surveyId) {
        this();
        setStationId       (stationId      );
        setLatitude        (latitude       );
        setLongitude       (longitude      );
        setInstrumentDepth (instrumentDepth);
        setName            (name           );
        setWaterDepth      (waterDepth     );
        setSurveyId        (surveyId       );
        if (dbg) System.out.println ("<br>in WavStation constructor 3"); // debug
    } // WavStation constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId       (CHARNULL );
        setLatitude        (FLOATNULL);
        setLongitude       (FLOATNULL);
        setInstrumentDepth (INTNULL  );
        setName            (CHARNULL );
        setWaterDepth      (INTNULL  );
        setSurveyId        (CHARNULL );
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
     * Set the 'instrumentDepth' class variable
     * @param  instrumentDepth (int)
     */
    public int setInstrumentDepth(int instrumentDepth) {
        try {
            if ( ((instrumentDepth == INTNULL) || 
                  (instrumentDepth == INTNULL2)) ||
                !((instrumentDepth < INSTRUMENT_DEPTH_MN) ||
                  (instrumentDepth > INSTRUMENT_DEPTH_MX)) ) {
                this.instrumentDepth = instrumentDepth;
                instrumentDepthError = ERROR_NORMAL;
            } else {
                throw instrumentDepthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setInstrumentDepthError(INTNULL, e, ERROR_LOCAL);
        } // try
        return instrumentDepthError;
    } // method setInstrumentDepth

    /**
     * Set the 'instrumentDepth' class variable
     * @param  instrumentDepth (Integer)
     */
    public int setInstrumentDepth(Integer instrumentDepth) {
        try {
            setInstrumentDepth(instrumentDepth.intValue());
        } catch (Exception e) {
            setInstrumentDepthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentDepthError;
    } // method setInstrumentDepth

    /**
     * Set the 'instrumentDepth' class variable
     * @param  instrumentDepth (Float)
     */
    public int setInstrumentDepth(Float instrumentDepth) {
        try {
            if (instrumentDepth.floatValue() == FLOATNULL) {
                setInstrumentDepth(INTNULL);
            } else {
                setInstrumentDepth(instrumentDepth.intValue());
            } // if (instrumentDepth.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setInstrumentDepthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentDepthError;
    } // method setInstrumentDepth

    /**
     * Set the 'instrumentDepth' class variable
     * @param  instrumentDepth (String)
     */
    public int setInstrumentDepth(String instrumentDepth) {
        try {
            setInstrumentDepth(new Integer(instrumentDepth).intValue());
        } catch (Exception e) {
            setInstrumentDepthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentDepthError;
    } // method setInstrumentDepth

    /**
     * Called when an exception has occured
     * @param  instrumentDepth (String)
     */
    private void setInstrumentDepthError (int instrumentDepth, Exception e, int error) {
        this.instrumentDepth = instrumentDepth;
        instrumentDepthErrorMessage = e.toString();
        instrumentDepthError = error;
    } // method setInstrumentDepthError


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
     * Set the 'waterDepth' class variable
     * @param  waterDepth (int)
     */
    public int setWaterDepth(int waterDepth) {
        try {
            if ( ((waterDepth == INTNULL) || 
                  (waterDepth == INTNULL2)) ||
                !((waterDepth < WATER_DEPTH_MN) ||
                  (waterDepth > WATER_DEPTH_MX)) ) {
                this.waterDepth = waterDepth;
                waterDepthError = ERROR_NORMAL;
            } else {
                throw waterDepthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWaterDepthError(INTNULL, e, ERROR_LOCAL);
        } // try
        return waterDepthError;
    } // method setWaterDepth

    /**
     * Set the 'waterDepth' class variable
     * @param  waterDepth (Integer)
     */
    public int setWaterDepth(Integer waterDepth) {
        try {
            setWaterDepth(waterDepth.intValue());
        } catch (Exception e) {
            setWaterDepthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return waterDepthError;
    } // method setWaterDepth

    /**
     * Set the 'waterDepth' class variable
     * @param  waterDepth (Float)
     */
    public int setWaterDepth(Float waterDepth) {
        try {
            if (waterDepth.floatValue() == FLOATNULL) {
                setWaterDepth(INTNULL);
            } else {
                setWaterDepth(waterDepth.intValue());
            } // if (waterDepth.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWaterDepthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return waterDepthError;
    } // method setWaterDepth

    /**
     * Set the 'waterDepth' class variable
     * @param  waterDepth (String)
     */
    public int setWaterDepth(String waterDepth) {
        try {
            setWaterDepth(new Integer(waterDepth).intValue());
        } catch (Exception e) {
            setWaterDepthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return waterDepthError;
    } // method setWaterDepth

    /**
     * Called when an exception has occured
     * @param  waterDepth (String)
     */
    private void setWaterDepthError (int waterDepth, Exception e, int error) {
        this.waterDepth = waterDepth;
        waterDepthErrorMessage = e.toString();
        waterDepthError = error;
    } // method setWaterDepthError


    /**
     * Set the 'surveyId' class variable
     * @param  surveyId (String)
     */
    public int setSurveyId(String surveyId) {
        try {
            this.surveyId = surveyId;
            if (this.surveyId != CHARNULL) {
                this.surveyId = stripCRLF(this.surveyId.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>surveyId = " + this.surveyId);
        } catch (Exception e) {
            setSurveyIdError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return surveyIdError;
    } // method setSurveyId

    /**
     * Called when an exception has occured
     * @param  surveyId (String)
     */
    private void setSurveyIdError (String surveyId, Exception e, int error) {
        this.surveyId = surveyId;
        surveyIdErrorMessage = e.toString();
        surveyIdError = error;
    } // method setSurveyIdError


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
     * Return the 'instrumentDepth' class variable
     * @return instrumentDepth (int)
     */
    public int getInstrumentDepth() {
        return instrumentDepth;
    } // method getInstrumentDepth

    /**
     * Return the 'instrumentDepth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getInstrumentDepth methods
     * @return instrumentDepth (String)
     */
    public String getInstrumentDepth(String s) {
        return ((instrumentDepth != INTNULL) ? new Integer(instrumentDepth).toString() : "");
    } // method getInstrumentDepth


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
     * Return the 'waterDepth' class variable
     * @return waterDepth (int)
     */
    public int getWaterDepth() {
        return waterDepth;
    } // method getWaterDepth

    /**
     * Return the 'waterDepth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWaterDepth methods
     * @return waterDepth (String)
     */
    public String getWaterDepth(String s) {
        return ((waterDepth != INTNULL) ? new Integer(waterDepth).toString() : "");
    } // method getWaterDepth


    /**
     * Return the 'surveyId' class variable
     * @return surveyId (String)
     */
    public String getSurveyId() {
        return surveyId;
    } // method getSurveyId

    /**
     * Return the 'surveyId' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurveyId methods
     * @return surveyId (String)
     */
    public String getSurveyId(String s) {
        return (surveyId != CHARNULL ? surveyId.replace('"','\'') : "");
    } // method getSurveyId


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
            (latitude == FLOATNULL) &&
            (longitude == FLOATNULL) &&
            (instrumentDepth == INTNULL) &&
            (name == CHARNULL) &&
            (waterDepth == INTNULL) &&
            (surveyId == CHARNULL)) {
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
            latitudeError +
            longitudeError +
            instrumentDepthError +
            nameError +
            waterDepthError +
            surveyIdError;
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
     * Gets the errorcode for the instrumentDepth instance variable
     * @return errorcode (int)
     */
    public int getInstrumentDepthError() {
        return instrumentDepthError;
    } // method getInstrumentDepthError

    /**
     * Gets the errorMessage for the instrumentDepth instance variable
     * @return errorMessage (String)
     */
    public String getInstrumentDepthErrorMessage() {
        return instrumentDepthErrorMessage;
    } // method getInstrumentDepthErrorMessage


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
     * Gets the errorcode for the waterDepth instance variable
     * @return errorcode (int)
     */
    public int getWaterDepthError() {
        return waterDepthError;
    } // method getWaterDepthError

    /**
     * Gets the errorMessage for the waterDepth instance variable
     * @return errorMessage (String)
     */
    public String getWaterDepthErrorMessage() {
        return waterDepthErrorMessage;
    } // method getWaterDepthErrorMessage


    /**
     * Gets the errorcode for the surveyId instance variable
     * @return errorcode (int)
     */
    public int getSurveyIdError() {
        return surveyIdError;
    } // method getSurveyIdError

    /**
     * Gets the errorMessage for the surveyId instance variable
     * @return errorMessage (String)
     */
    public String getSurveyIdErrorMessage() {
        return surveyIdErrorMessage;
    } // method getSurveyIdErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of WavStation. e.g.<pre>
     * WavStation wavStation = new WavStation(<val1>);
     * WavStation wavStationArray[] = wavStation.get();</pre>
     * will get the WavStation record where stationId = <val1>.
     * @return Array of WavStation (WavStation[])
     */
    public WavStation[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * WavStation wavStationArray[] = 
     *     new WavStation().get(WavStation.STATION_ID+"=<val1>");</pre>
     * will get the WavStation record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of WavStation (WavStation[])
     */
    public WavStation[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * WavStation wavStationArray[] = 
     *     new WavStation().get("1=1",wavStation.STATION_ID);</pre>
     * will get the all the WavStation records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WavStation (WavStation[])
     */
    public WavStation[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = WavStation.STATION_ID,WavStation.LATITUDE;
     * String where = WavStation.STATION_ID + "=<val1";
     * String order = WavStation.STATION_ID;
     * WavStation wavStationArray[] = 
     *     new WavStation().get(columns, where, order);</pre>
     * will get the stationId and latitude colums of all WavStation records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WavStation (WavStation[])
     */
    public WavStation[] get (String fields, String where, String order) {
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
     * and transforms it into an array of WavStation.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private WavStation[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol       = db.getColNumber(STATION_ID);
        int latitudeCol        = db.getColNumber(LATITUDE);
        int longitudeCol       = db.getColNumber(LONGITUDE);
        int instrumentDepthCol = db.getColNumber(INSTRUMENT_DEPTH);
        int nameCol            = db.getColNumber(NAME);
        int waterDepthCol      = db.getColNumber(WATER_DEPTH);
        int surveyIdCol        = db.getColNumber(SURVEY_ID);
        WavStation[] cArray = new WavStation[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new WavStation();
            if (stationIdCol != -1)
                cArray[i].setStationId      ((String) row.elementAt(stationIdCol));
            if (latitudeCol != -1)
                cArray[i].setLatitude       ((String) row.elementAt(latitudeCol));
            if (longitudeCol != -1)
                cArray[i].setLongitude      ((String) row.elementAt(longitudeCol));
            if (instrumentDepthCol != -1)
                cArray[i].setInstrumentDepth((String) row.elementAt(instrumentDepthCol));
            if (nameCol != -1)
                cArray[i].setName           ((String) row.elementAt(nameCol));
            if (waterDepthCol != -1)
                cArray[i].setWaterDepth     ((String) row.elementAt(waterDepthCol));
            if (surveyIdCol != -1)
                cArray[i].setSurveyId       ((String) row.elementAt(surveyIdCol));
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
     *     new WavStation(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>).put();</pre>
     * will insert a record with:
     *     stationId       = <val1>,
     *     latitude        = <val2>,
     *     longitude       = <val3>,
     *     instrumentDepth = <val4>,
     *     name            = <val5>,
     *     waterDepth      = <val6>,
     *     surveyId        = <val7>.
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
     * Boolean success = new WavStation(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where latitude = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WavStation wavStation = new WavStation();
     * success = wavStation.del(WavStation.STATION_ID+"=<val1>");</pre>
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
     * update are taken from the WavStation argument, .e.g.<pre>
     * Boolean success
     * WavStation updWavStation = new WavStation();
     * updWavStation.setLatitude(<val2>);
     * WavStation whereWavStation = new WavStation(<val1>);
     * success = whereWavStation.upd(updWavStation);</pre>
     * will update Latitude to <val2> for all records where 
     * stationId = <val1>.
     * @param wavStation  A WavStation variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(WavStation wavStation) {
        return db.update (TABLE, createColVals(wavStation), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WavStation updWavStation = new WavStation();
     * updWavStation.setLatitude(<val2>);
     * WavStation whereWavStation = new WavStation();
     * success = whereWavStation.upd(
     *     updWavStation, WavStation.STATION_ID+"=<val1>");</pre>
     * will update Latitude to <val2> for all records where 
     * stationId = <val1>.
     * @param  updWavStation  A WavStation variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(WavStation wavStation, String where) {
        return db.update (TABLE, createColVals(wavStation), where);
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
        if (getInstrumentDepth() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTRUMENT_DEPTH + "=" + getInstrumentDepth("");
        } // if getInstrumentDepth
        if (getName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NAME + "='" + getName() + "'";
        } // if getName
        if (getWaterDepth() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATER_DEPTH + "=" + getWaterDepth("");
        } // if getWaterDepth
        if (getSurveyId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_ID + "='" + getSurveyId() + "'";
        } // if getSurveyId
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(WavStation aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
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
        if (aVar.getInstrumentDepth() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTRUMENT_DEPTH +"=";
            colVals += (aVar.getInstrumentDepth() == INTNULL2 ?
                "null" : aVar.getInstrumentDepth(""));
        } // if aVar.getInstrumentDepth
        if (aVar.getName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NAME +"=";
            colVals += (aVar.getName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getName() + "'");
        } // if aVar.getName
        if (aVar.getWaterDepth() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATER_DEPTH +"=";
            colVals += (aVar.getWaterDepth() == INTNULL2 ?
                "null" : aVar.getWaterDepth(""));
        } // if aVar.getWaterDepth
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (getLatitude() != FLOATNULL) {
            columns = columns + "," + LATITUDE;
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            columns = columns + "," + LONGITUDE;
        } // if getLongitude
        if (getInstrumentDepth() != INTNULL) {
            columns = columns + "," + INSTRUMENT_DEPTH;
        } // if getInstrumentDepth
        if (getName() != CHARNULL) {
            columns = columns + "," + NAME;
        } // if getName
        if (getWaterDepth() != INTNULL) {
            columns = columns + "," + WATER_DEPTH;
        } // if getWaterDepth
        if (getSurveyId() != CHARNULL) {
            columns = columns + "," + SURVEY_ID;
        } // if getSurveyId
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (getLatitude() != FLOATNULL) {
            values  = values  + "," + getLatitude("");
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            values  = values  + "," + getLongitude("");
        } // if getLongitude
        if (getInstrumentDepth() != INTNULL) {
            values  = values  + "," + getInstrumentDepth("");
        } // if getInstrumentDepth
        if (getName() != CHARNULL) {
            values  = values  + ",'" + getName() + "'";
        } // if getName
        if (getWaterDepth() != INTNULL) {
            values  = values  + "," + getWaterDepth("");
        } // if getWaterDepth
        if (getSurveyId() != CHARNULL) {
            values  = values  + ",'" + getSurveyId() + "'";
        } // if getSurveyId
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getStationId("")       + "|" +
            getLatitude("")        + "|" +
            getLongitude("")       + "|" +
            getInstrumentDepth("") + "|" +
            getName("")            + "|" +
            getWaterDepth("")      + "|" +
            getSurveyId("")        + "|";
    } // method toString

} // class WavStation
