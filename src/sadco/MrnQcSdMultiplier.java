package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the QC_SD_MULTIPLIER table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 070710 - SIT Group
 * @version
 * 070710 - GenTableClassB class - create class<br>
 */
public class MrnQcSdMultiplier extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "QC_SD_MULTIPLIER";
    /** The latitude field name */
    public static final String LATITUDE = "LATITUDE";
    /** The longitude field name */
    public static final String LONGITUDE = "LONGITUDE";
    /** The multiplier field name */
    public static final String MULTIPLIER = "MULTIPLIER";

    /**
     * The instance variables corresponding to the table fields
     */
    private float     latitude;
    private float     longitude;
    private int       multiplier;

    /** The error variables  */
    private int latitudeError   = ERROR_NORMAL;
    private int longitudeError  = ERROR_NORMAL;
    private int multiplierError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String latitudeErrorMessage   = "";
    private String longitudeErrorMessage  = "";
    private String multiplierErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final float     LATITUDE_MN = FLOATMIN;
    public static final float     LATITUDE_MX = FLOATMAX;
    public static final float     LONGITUDE_MN = FLOATMIN;
    public static final float     LONGITUDE_MX = FLOATMAX;
    public static final int       MULTIPLIER_MN = INTMIN;
    public static final int       MULTIPLIER_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception latitudeOutOfBoundsException =
        new Exception ("'latitude' out of bounds: " +
            LATITUDE_MN + " - " + LATITUDE_MX);
    Exception longitudeOutOfBoundsException =
        new Exception ("'longitude' out of bounds: " +
            LONGITUDE_MN + " - " + LONGITUDE_MX);
    Exception multiplierOutOfBoundsException =
        new Exception ("'multiplier' out of bounds: " +
            MULTIPLIER_MN + " - " + MULTIPLIER_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnQcSdMultiplier() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnQcSdMultiplier constructor 1"); // debug
    } // MrnQcSdMultiplier constructor

    /**
     * Instantiate a MrnQcSdMultiplier object and initialize the instance variables.
     * @param latitude  The latitude (float)
     */
    public MrnQcSdMultiplier(
            float latitude) {
        this();
        setLatitude   (latitude  );
        if (dbg) System.out.println ("<br>in MrnQcSdMultiplier constructor 2"); // debug
    } // MrnQcSdMultiplier constructor

    /**
     * Instantiate a MrnQcSdMultiplier object and initialize the instance variables.
     * @param latitude    The latitude   (float)
     * @param longitude   The longitude  (float)
     * @param multiplier  The multiplier (int)
     * @return A MrnQcSdMultiplier object
     */
    public MrnQcSdMultiplier(
            float latitude,
            float longitude,
            int   multiplier) {
        this();
        setLatitude   (latitude  );
        setLongitude  (longitude );
        setMultiplier (multiplier);
        if (dbg) System.out.println ("<br>in MrnQcSdMultiplier constructor 3"); // debug
    } // MrnQcSdMultiplier constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setLatitude   (FLOATNULL);
        setLongitude  (FLOATNULL);
        setMultiplier (INTNULL  );
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
     * Set the 'multiplier' class variable
     * @param  multiplier (int)
     */
    public int setMultiplier(int multiplier) {
        try {
            if ( ((multiplier == INTNULL) || 
                  (multiplier == INTNULL2)) ||
                !((multiplier < MULTIPLIER_MN) ||
                  (multiplier > MULTIPLIER_MX)) ) {
                this.multiplier = multiplier;
                multiplierError = ERROR_NORMAL;
            } else {
                throw multiplierOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMultiplierError(INTNULL, e, ERROR_LOCAL);
        } // try
        return multiplierError;
    } // method setMultiplier

    /**
     * Set the 'multiplier' class variable
     * @param  multiplier (Integer)
     */
    public int setMultiplier(Integer multiplier) {
        try {
            setMultiplier(multiplier.intValue());
        } catch (Exception e) {
            setMultiplierError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return multiplierError;
    } // method setMultiplier

    /**
     * Set the 'multiplier' class variable
     * @param  multiplier (Float)
     */
    public int setMultiplier(Float multiplier) {
        try {
            if (multiplier.floatValue() == FLOATNULL) {
                setMultiplier(INTNULL);
            } else {
                setMultiplier(multiplier.intValue());
            } // if (multiplier.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMultiplierError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return multiplierError;
    } // method setMultiplier

    /**
     * Set the 'multiplier' class variable
     * @param  multiplier (String)
     */
    public int setMultiplier(String multiplier) {
        try {
            setMultiplier(new Integer(multiplier).intValue());
        } catch (Exception e) {
            setMultiplierError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return multiplierError;
    } // method setMultiplier

    /**
     * Called when an exception has occured
     * @param  multiplier (String)
     */
    private void setMultiplierError (int multiplier, Exception e, int error) {
        this.multiplier = multiplier;
        multiplierErrorMessage = e.toString();
        multiplierError = error;
    } // method setMultiplierError


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
     * Return the 'multiplier' class variable
     * @return multiplier (int)
     */
    public int getMultiplier() {
        return multiplier;
    } // method getMultiplier

    /**
     * Return the 'multiplier' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMultiplier methods
     * @return multiplier (String)
     */
    public String getMultiplier(String s) {
        return ((multiplier != INTNULL) ? new Integer(multiplier).toString() : "");
    } // method getMultiplier


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
            (multiplier == INTNULL)) {
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
            multiplierError;
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
     * Gets the errorcode for the multiplier instance variable
     * @return errorcode (int)
     */
    public int getMultiplierError() {
        return multiplierError;
    } // method getMultiplierError

    /**
     * Gets the errorMessage for the multiplier instance variable
     * @return errorMessage (String)
     */
    public String getMultiplierErrorMessage() {
        return multiplierErrorMessage;
    } // method getMultiplierErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnQcSdMultiplier. e.g.<pre>
     * MrnQcSdMultiplier qcSdMultiplier = new MrnQcSdMultiplier(<val1>);
     * MrnQcSdMultiplier qcSdMultiplierArray[] = qcSdMultiplier.get();</pre>
     * will get the MrnQcSdMultiplier record where latitude = <val1>.
     * @return Array of MrnQcSdMultiplier (MrnQcSdMultiplier[])
     */
    public MrnQcSdMultiplier[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnQcSdMultiplier qcSdMultiplierArray[] = 
     *     new MrnQcSdMultiplier().get(MrnQcSdMultiplier.LATITUDE+"=<val1>");</pre>
     * will get the MrnQcSdMultiplier record where latitude = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnQcSdMultiplier (MrnQcSdMultiplier[])
     */
    public MrnQcSdMultiplier[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnQcSdMultiplier qcSdMultiplierArray[] = 
     *     new MrnQcSdMultiplier().get("1=1",qcSdMultiplier.LATITUDE);</pre>
     * will get the all the MrnQcSdMultiplier records, and order them by latitude.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnQcSdMultiplier (MrnQcSdMultiplier[])
     */
    public MrnQcSdMultiplier[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnQcSdMultiplier.LATITUDE,MrnQcSdMultiplier.LONGITUDE;
     * String where = MrnQcSdMultiplier.LATITUDE + "=<val1";
     * String order = MrnQcSdMultiplier.LATITUDE;
     * MrnQcSdMultiplier qcSdMultiplierArray[] = 
     *     new MrnQcSdMultiplier().get(columns, where, order);</pre>
     * will get the latitude and longitude colums of all MrnQcSdMultiplier records,
     * where latitude = <val1>, and order them by latitude.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnQcSdMultiplier (MrnQcSdMultiplier[])
     */
    public MrnQcSdMultiplier[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnQcSdMultiplier.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnQcSdMultiplier[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int latitudeCol   = db.getColNumber(LATITUDE);
        int longitudeCol  = db.getColNumber(LONGITUDE);
        int multiplierCol = db.getColNumber(MULTIPLIER);
        MrnQcSdMultiplier[] cArray = new MrnQcSdMultiplier[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnQcSdMultiplier();
            if (latitudeCol != -1)
                cArray[i].setLatitude  ((String) row.elementAt(latitudeCol));
            if (longitudeCol != -1)
                cArray[i].setLongitude ((String) row.elementAt(longitudeCol));
            if (multiplierCol != -1)
                cArray[i].setMultiplier((String) row.elementAt(multiplierCol));
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
     *     new MrnQcSdMultiplier(
     *         <val1>,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     latitude   = <val1>,
     *     longitude  = <val2>,
     *     multiplier = <val3>.
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
     * Boolean success = new MrnQcSdMultiplier(
     *     Tables.FLOATNULL,
     *     <val2>,
     *     Tables.INTNULL).del();</pre>
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
     * MrnQcSdMultiplier qcSdMultiplier = new MrnQcSdMultiplier();
     * success = qcSdMultiplier.del(MrnQcSdMultiplier.LATITUDE+"=<val1>");</pre>
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
     * update are taken from the MrnQcSdMultiplier argument, .e.g.<pre>
     * Boolean success
     * MrnQcSdMultiplier updMrnQcSdMultiplier = new MrnQcSdMultiplier();
     * updMrnQcSdMultiplier.setLongitude(<val2>);
     * MrnQcSdMultiplier whereMrnQcSdMultiplier = new MrnQcSdMultiplier(<val1>);
     * success = whereMrnQcSdMultiplier.upd(updMrnQcSdMultiplier);</pre>
     * will update Longitude to <val2> for all records where 
     * latitude = <val1>.
     * @param qcSdMultiplier  A MrnQcSdMultiplier variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnQcSdMultiplier qcSdMultiplier) {
        return db.update (TABLE, createColVals(qcSdMultiplier), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnQcSdMultiplier updMrnQcSdMultiplier = new MrnQcSdMultiplier();
     * updMrnQcSdMultiplier.setLongitude(<val2>);
     * MrnQcSdMultiplier whereMrnQcSdMultiplier = new MrnQcSdMultiplier();
     * success = whereMrnQcSdMultiplier.upd(
     *     updMrnQcSdMultiplier, MrnQcSdMultiplier.LATITUDE+"=<val1>");</pre>
     * will update Longitude to <val2> for all records where 
     * latitude = <val1>.
     * @param  updMrnQcSdMultiplier  A MrnQcSdMultiplier variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnQcSdMultiplier qcSdMultiplier, String where) {
        return db.update (TABLE, createColVals(qcSdMultiplier), where);
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
        if (getMultiplier() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MULTIPLIER + "=" + getMultiplier("");
        } // if getMultiplier
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnQcSdMultiplier aVar) {
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
        if (aVar.getMultiplier() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MULTIPLIER +"=";
            colVals += (aVar.getMultiplier() == INTNULL2 ?
                "null" : aVar.getMultiplier(""));
        } // if aVar.getMultiplier
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
        if (getMultiplier() != INTNULL) {
            columns = columns + "," + MULTIPLIER;
        } // if getMultiplier
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
        if (getMultiplier() != INTNULL) {
            values  = values  + "," + getMultiplier("");
        } // if getMultiplier
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getLatitude("")   + "|" +
            getLongitude("")  + "|" +
            getMultiplier("") + "|";
    } // method toString

} // class MrnQcSdMultiplier
