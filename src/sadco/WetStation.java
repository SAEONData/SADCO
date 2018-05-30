package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WET_STATION table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class WetStation extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WET_STATION";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The latitude field name */
    public static final String LATITUDE = "LATITUDE";
    /** The longitude field name */
    public static final String LONGITUDE = "LONGITUDE";
    /** The name field name */
    public static final String NAME = "NAME";
    /** The clientCode field name */
    public static final String CLIENT_CODE = "CLIENT_CODE";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private float     latitude;
    private float     longitude;
    private String    name;
    private int       clientCode;
    private String    surveyId;

    /** The error variables  */
    private int stationIdError  = ERROR_NORMAL;
    private int latitudeError   = ERROR_NORMAL;
    private int longitudeError  = ERROR_NORMAL;
    private int nameError       = ERROR_NORMAL;
    private int clientCodeError = ERROR_NORMAL;
    private int surveyIdError   = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage  = "";
    private String latitudeErrorMessage   = "";
    private String longitudeErrorMessage  = "";
    private String nameErrorMessage       = "";
    private String clientCodeErrorMessage = "";
    private String surveyIdErrorMessage   = "";

    /** The min-max constants for all numerics */
    public static final float     LATITUDE_MN = FLOATMIN;
    public static final float     LATITUDE_MX = FLOATMAX;
    public static final float     LONGITUDE_MN = FLOATMIN;
    public static final float     LONGITUDE_MX = FLOATMAX;
    public static final int       CLIENT_CODE_MN = INTMIN;
    public static final int       CLIENT_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception latitudeOutOfBoundsException =
        new Exception ("'latitude' out of bounds: " +
            LATITUDE_MN + " - " + LATITUDE_MX);
    Exception longitudeOutOfBoundsException =
        new Exception ("'longitude' out of bounds: " +
            LONGITUDE_MN + " - " + LONGITUDE_MX);
    Exception clientCodeOutOfBoundsException =
        new Exception ("'clientCode' out of bounds: " +
            CLIENT_CODE_MN + " - " + CLIENT_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public WetStation() {
        clearVars();
        if (dbg) System.out.println ("<br>in WetStation constructor 1"); // debug
    } // WetStation constructor

    /**
     * Instantiate a WetStation object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public WetStation(
            String stationId) {
        this();
        setStationId  (stationId );
        if (dbg) System.out.println ("<br>in WetStation constructor 2"); // debug
    } // WetStation constructor

    /**
     * Instantiate a WetStation object and initialize the instance variables.
     * @param stationId   The stationId  (String)
     * @param latitude    The latitude   (float)
     * @param longitude   The longitude  (float)
     * @param name        The name       (String)
     * @param clientCode  The clientCode (int)
     * @param surveyId    The surveyId   (String)
     */
    public WetStation(
            String stationId,
            float  latitude,
            float  longitude,
            String name,
            int    clientCode,
            String surveyId) {
        this();
        setStationId  (stationId );
        setLatitude   (latitude  );
        setLongitude  (longitude );
        setName       (name      );
        setClientCode (clientCode);
        setSurveyId   (surveyId  );
        if (dbg) System.out.println ("<br>in WetStation constructor 3"); // debug
    } // WetStation constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId  (CHARNULL );
        setLatitude   (FLOATNULL);
        setLongitude  (FLOATNULL);
        setName       (CHARNULL );
        setClientCode (INTNULL  );
        setSurveyId   (CHARNULL );
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
     * Set the 'clientCode' class variable
     * @param  clientCode (int)
     */
    public int setClientCode(int clientCode) {
        try {
            if ( ((clientCode == INTNULL) ||
                  (clientCode == INTNULL2)) ||
                !((clientCode < CLIENT_CODE_MN) ||
                  (clientCode > CLIENT_CODE_MX)) ) {
                this.clientCode = clientCode;
                clientCodeError = ERROR_NORMAL;
            } else {
                throw clientCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (Integer)
     */
    public int setClientCode(Integer clientCode) {
        try {
            setClientCode(clientCode.intValue());
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (Float)
     */
    public int setClientCode(Float clientCode) {
        try {
            if (clientCode.floatValue() == FLOATNULL) {
                setClientCode(INTNULL);
            } else {
                setClientCode(clientCode.intValue());
            } // if (clientCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (String)
     */
    public int setClientCode(String clientCode) {
        try {
            setClientCode(new Integer(clientCode).intValue());
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Called when an exception has occured
     * @param  clientCode (String)
     */
    private void setClientCodeError (int clientCode, Exception e, int error) {
        this.clientCode = clientCode;
        clientCodeErrorMessage = e.toString();
        clientCodeError = error;
    } // method setClientCodeError


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
     * Return the 'clientCode' class variable
     * @return clientCode (int)
     */
    public int getClientCode() {
        return clientCode;
    } // method getClientCode

    /**
     * Return the 'clientCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getClientCode methods
     * @return clientCode (String)
     */
    public String getClientCode(String s) {
        return ((clientCode != INTNULL) ? new Integer(clientCode).toString() : "");
    } // method getClientCode


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
            (name == CHARNULL) &&
            (clientCode == INTNULL) &&
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
            nameError +
            clientCodeError +
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
     * Gets the errorcode for the clientCode instance variable
     * @return errorcode (int)
     */
    public int getClientCodeError() {
        return clientCodeError;
    } // method getClientCodeError

    /**
     * Gets the errorMessage for the clientCode instance variable
     * @return errorMessage (String)
     */
    public String getClientCodeErrorMessage() {
        return clientCodeErrorMessage;
    } // method getClientCodeErrorMessage


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
     * are returned in an array of WetStation. e.g.<pre>
     * WetStation wetStation = new WetStation(<val1>);
     * WetStation wetStationArray[] = wetStation.get();</pre>
     * will get the WetStation record where stationId = <val1>.
     * @return Array of WetStation (WetStation[])
     */
    public WetStation[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * WetStation wetStationArray[] =
     *     new WetStation().get(WetStation.STATION_ID+"=<val1>");</pre>
     * will get the WetStation record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of WetStation (WetStation[])
     */
    public WetStation[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * WetStation wetStationArray[] =
     *     new WetStation().get("1=1",wetStation.STATION_ID);</pre>
     * will get the all the WetStation records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetStation (WetStation[])
     */
    public WetStation[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = WetStation.STATION_ID,WetStation.LATITUDE;
     * String where = WetStation.STATION_ID + "=<val1";
     * String order = WetStation.STATION_ID;
     * WetStation wetStationArray[] =
     *     new WetStation().get(columns, where, order);</pre>
     * will get the stationId and latitude colums of all WetStation records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetStation (WetStation[])
     */
    public WetStation[] get (String fields, String where, String order) {
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
     * and transforms it into an array of WetStation.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private WetStation[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol  = db.getColNumber(STATION_ID);
        int latitudeCol   = db.getColNumber(LATITUDE);
        int longitudeCol  = db.getColNumber(LONGITUDE);
        int nameCol       = db.getColNumber(NAME);
        int clientCodeCol = db.getColNumber(CLIENT_CODE);
        int surveyIdCol   = db.getColNumber(SURVEY_ID);
        WetStation[] cArray = new WetStation[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new WetStation();
            if (stationIdCol != -1)
                cArray[i].setStationId ((String) row.elementAt(stationIdCol));
            if (latitudeCol != -1)
                cArray[i].setLatitude  ((String) row.elementAt(latitudeCol));
            if (longitudeCol != -1)
                cArray[i].setLongitude ((String) row.elementAt(longitudeCol));
            if (nameCol != -1)
                cArray[i].setName      ((String) row.elementAt(nameCol));
            if (clientCodeCol != -1)
                cArray[i].setClientCode((String) row.elementAt(clientCodeCol));
            if (surveyIdCol != -1)
                cArray[i].setSurveyId  ((String) row.elementAt(surveyIdCol));
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
     *     new WetStation(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>).put();</pre>
     * will insert a record with:
     *     stationId  = <val1>,
     *     latitude   = <val2>,
     *     longitude  = <val3>,
     *     name       = <val4>,
     *     clientCode = <val5>,
     *     surveyId   = <val6>.
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
     * Boolean success = new WetStation(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
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
     * WetStation wetStation = new WetStation();
     * success = wetStation.del(WetStation.STATION_ID+"=<val1>");</pre>
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
     * update are taken from the WetStation argument, .e.g.<pre>
     * Boolean success
     * WetStation updWetStation = new WetStation();
     * updWetStation.setLatitude(<val2>);
     * WetStation whereWetStation = new WetStation(<val1>);
     * success = whereWetStation.upd(updWetStation);</pre>
     * will update Latitude to <val2> for all records where
     * stationId = <val1>.
     * @param  wetStation  A WetStation variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(WetStation wetStation) {
        return db.update (TABLE, createColVals(wetStation), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WetStation updWetStation = new WetStation();
     * updWetStation.setLatitude(<val2>);
     * WetStation whereWetStation = new WetStation();
     * success = whereWetStation.upd(
     *     updWetStation, WetStation.STATION_ID+"=<val1>");</pre>
     * will update Latitude to <val2> for all records where
     * stationId = <val1>.
     * @param  wetStation  A WetStation variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(WetStation wetStation, String where) {
        return db.update (TABLE, createColVals(wetStation), where);
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
        if (getName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NAME + "='" + getName() + "'";
        } // if getName
        if (getClientCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLIENT_CODE + "=" + getClientCode("");
        } // if getClientCode
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
    private String createColVals(WetStation aVar) {
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
        if (aVar.getName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NAME +"=";
            colVals += (aVar.getName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getName() + "'");
        } // if aVar.getName
        if (aVar.getClientCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLIENT_CODE +"=";
            colVals += (aVar.getClientCode() == INTNULL2 ?
                "null" : aVar.getClientCode(""));
        } // if aVar.getClientCode
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
        if (getName() != CHARNULL) {
            columns = columns + "," + NAME;
        } // if getName
        if (getClientCode() != INTNULL) {
            columns = columns + "," + CLIENT_CODE;
        } // if getClientCode
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
        if (getName() != CHARNULL) {
            values  = values  + ",'" + getName() + "'";
        } // if getName
        if (getClientCode() != INTNULL) {
            values  = values  + "," + getClientCode("");
        } // if getClientCode
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
            getStationId("")  + "|" +
            getLatitude("")   + "|" +
            getLongitude("")  + "|" +
            getName("")       + "|" +
            getClientCode("") + "|" +
            getSurveyId("")   + "|";
    } // method toString

} // class WetStation
