package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WAV_INST_STATS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 080222 - SIT Group
 * @version
 * 080222 - GenTableClassB class - create class<br>
 */
public class WavInstStats extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WAV_INST_STATS";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The yearp field name */
    public static final String YEARP = "YEARP";
    /** The monthp field name */
    public static final String MONTHP = "MONTHP";
    /** The instrumentCode field name */
    public static final String INSTRUMENT_CODE = "INSTRUMENT_CODE";
    /** The stats field name */
    public static final String STATS = "STATS";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private int       yearp;
    private int       monthp;
    private int       instrumentCode;
    private int       stats;

    /** The error variables  */
    private int stationIdError      = ERROR_NORMAL;
    private int yearpError          = ERROR_NORMAL;
    private int monthpError         = ERROR_NORMAL;
    private int instrumentCodeError = ERROR_NORMAL;
    private int statsError          = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage      = "";
    private String yearpErrorMessage          = "";
    private String monthpErrorMessage         = "";
    private String instrumentCodeErrorMessage = "";
    private String statsErrorMessage          = "";

    /** The min-max constants for all numerics */
    public static final int       YEARP_MN = INTMIN;
    public static final int       YEARP_MX = INTMAX;
    public static final int       MONTHP_MN = INTMIN;
    public static final int       MONTHP_MX = INTMAX;
    public static final int       INSTRUMENT_CODE_MN = INTMIN;
    public static final int       INSTRUMENT_CODE_MX = INTMAX;
    public static final int       STATS_MN = INTMIN;
    public static final int       STATS_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception yearpOutOfBoundsException =
        new Exception ("'yearp' out of bounds: " +
            YEARP_MN + " - " + YEARP_MX);
    Exception monthpOutOfBoundsException =
        new Exception ("'monthp' out of bounds: " +
            MONTHP_MN + " - " + MONTHP_MX);
    Exception instrumentCodeOutOfBoundsException =
        new Exception ("'instrumentCode' out of bounds: " +
            INSTRUMENT_CODE_MN + " - " + INSTRUMENT_CODE_MX);
    Exception statsOutOfBoundsException =
        new Exception ("'stats' out of bounds: " +
            STATS_MN + " - " + STATS_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public WavInstStats() {
        clearVars();
        if (dbg) System.out.println ("<br>in WavInstStats constructor 1"); // debug
    } // WavInstStats constructor

    /**
     * Instantiate a WavInstStats object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public WavInstStats(
            String stationId) {
        this();
        setStationId      (stationId     );
        if (dbg) System.out.println ("<br>in WavInstStats constructor 2"); // debug
    } // WavInstStats constructor

    /**
     * Instantiate a WavInstStats object and initialize the instance variables.
     * @param stationId       The stationId      (String)
     * @param yearp           The yearp          (int)
     * @param monthp          The monthp         (int)
     * @param instrumentCode  The instrumentCode (int)
     * @param stats           The stats          (int)
     * @return A WavInstStats object
     */
    public WavInstStats(
            String stationId,
            int    yearp,
            int    monthp,
            int    instrumentCode,
            int    stats) {
        this();
        setStationId      (stationId     );
        setYearp          (yearp         );
        setMonthp         (monthp        );
        setInstrumentCode (instrumentCode);
        setStats          (stats         );
        if (dbg) System.out.println ("<br>in WavInstStats constructor 3"); // debug
    } // WavInstStats constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId      (CHARNULL);
        setYearp          (INTNULL );
        setMonthp         (INTNULL );
        setInstrumentCode (INTNULL );
        setStats          (INTNULL );
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
     * Set the 'yearp' class variable
     * @param  yearp (int)
     */
    public int setYearp(int yearp) {
        try {
            if ( ((yearp == INTNULL) || 
                  (yearp == INTNULL2)) ||
                !((yearp < YEARP_MN) ||
                  (yearp > YEARP_MX)) ) {
                this.yearp = yearp;
                yearpError = ERROR_NORMAL;
            } else {
                throw yearpOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setYearpError(INTNULL, e, ERROR_LOCAL);
        } // try
        return yearpError;
    } // method setYearp

    /**
     * Set the 'yearp' class variable
     * @param  yearp (Integer)
     */
    public int setYearp(Integer yearp) {
        try {
            setYearp(yearp.intValue());
        } catch (Exception e) {
            setYearpError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return yearpError;
    } // method setYearp

    /**
     * Set the 'yearp' class variable
     * @param  yearp (Float)
     */
    public int setYearp(Float yearp) {
        try {
            if (yearp.floatValue() == FLOATNULL) {
                setYearp(INTNULL);
            } else {
                setYearp(yearp.intValue());
            } // if (yearp.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setYearpError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return yearpError;
    } // method setYearp

    /**
     * Set the 'yearp' class variable
     * @param  yearp (String)
     */
    public int setYearp(String yearp) {
        try {
            setYearp(new Integer(yearp).intValue());
        } catch (Exception e) {
            setYearpError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return yearpError;
    } // method setYearp

    /**
     * Called when an exception has occured
     * @param  yearp (String)
     */
    private void setYearpError (int yearp, Exception e, int error) {
        this.yearp = yearp;
        yearpErrorMessage = e.toString();
        yearpError = error;
    } // method setYearpError


    /**
     * Set the 'monthp' class variable
     * @param  monthp (int)
     */
    public int setMonthp(int monthp) {
        try {
            if ( ((monthp == INTNULL) || 
                  (monthp == INTNULL2)) ||
                !((monthp < MONTHP_MN) ||
                  (monthp > MONTHP_MX)) ) {
                this.monthp = monthp;
                monthpError = ERROR_NORMAL;
            } else {
                throw monthpOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMonthpError(INTNULL, e, ERROR_LOCAL);
        } // try
        return monthpError;
    } // method setMonthp

    /**
     * Set the 'monthp' class variable
     * @param  monthp (Integer)
     */
    public int setMonthp(Integer monthp) {
        try {
            setMonthp(monthp.intValue());
        } catch (Exception e) {
            setMonthpError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return monthpError;
    } // method setMonthp

    /**
     * Set the 'monthp' class variable
     * @param  monthp (Float)
     */
    public int setMonthp(Float monthp) {
        try {
            if (monthp.floatValue() == FLOATNULL) {
                setMonthp(INTNULL);
            } else {
                setMonthp(monthp.intValue());
            } // if (monthp.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMonthpError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return monthpError;
    } // method setMonthp

    /**
     * Set the 'monthp' class variable
     * @param  monthp (String)
     */
    public int setMonthp(String monthp) {
        try {
            setMonthp(new Integer(monthp).intValue());
        } catch (Exception e) {
            setMonthpError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return monthpError;
    } // method setMonthp

    /**
     * Called when an exception has occured
     * @param  monthp (String)
     */
    private void setMonthpError (int monthp, Exception e, int error) {
        this.monthp = monthp;
        monthpErrorMessage = e.toString();
        monthpError = error;
    } // method setMonthpError


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
     * Set the 'stats' class variable
     * @param  stats (int)
     */
    public int setStats(int stats) {
        try {
            if ( ((stats == INTNULL) || 
                  (stats == INTNULL2)) ||
                !((stats < STATS_MN) ||
                  (stats > STATS_MX)) ) {
                this.stats = stats;
                statsError = ERROR_NORMAL;
            } else {
                throw statsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStatsError(INTNULL, e, ERROR_LOCAL);
        } // try
        return statsError;
    } // method setStats

    /**
     * Set the 'stats' class variable
     * @param  stats (Integer)
     */
    public int setStats(Integer stats) {
        try {
            setStats(stats.intValue());
        } catch (Exception e) {
            setStatsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return statsError;
    } // method setStats

    /**
     * Set the 'stats' class variable
     * @param  stats (Float)
     */
    public int setStats(Float stats) {
        try {
            if (stats.floatValue() == FLOATNULL) {
                setStats(INTNULL);
            } else {
                setStats(stats.intValue());
            } // if (stats.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setStatsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return statsError;
    } // method setStats

    /**
     * Set the 'stats' class variable
     * @param  stats (String)
     */
    public int setStats(String stats) {
        try {
            setStats(new Integer(stats).intValue());
        } catch (Exception e) {
            setStatsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return statsError;
    } // method setStats

    /**
     * Called when an exception has occured
     * @param  stats (String)
     */
    private void setStatsError (int stats, Exception e, int error) {
        this.stats = stats;
        statsErrorMessage = e.toString();
        statsError = error;
    } // method setStatsError


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
     * Return the 'yearp' class variable
     * @return yearp (int)
     */
    public int getYearp() {
        return yearp;
    } // method getYearp

    /**
     * Return the 'yearp' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getYearp methods
     * @return yearp (String)
     */
    public String getYearp(String s) {
        return ((yearp != INTNULL) ? new Integer(yearp).toString() : "");
    } // method getYearp


    /**
     * Return the 'monthp' class variable
     * @return monthp (int)
     */
    public int getMonthp() {
        return monthp;
    } // method getMonthp

    /**
     * Return the 'monthp' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMonthp methods
     * @return monthp (String)
     */
    public String getMonthp(String s) {
        return ((monthp != INTNULL) ? new Integer(monthp).toString() : "");
    } // method getMonthp


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
     * Return the 'stats' class variable
     * @return stats (int)
     */
    public int getStats() {
        return stats;
    } // method getStats

    /**
     * Return the 'stats' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStats methods
     * @return stats (String)
     */
    public String getStats(String s) {
        return ((stats != INTNULL) ? new Integer(stats).toString() : "");
    } // method getStats


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
            (yearp == INTNULL) &&
            (monthp == INTNULL) &&
            (instrumentCode == INTNULL) &&
            (stats == INTNULL)) {
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
            yearpError +
            monthpError +
            instrumentCodeError +
            statsError;
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
     * Gets the errorcode for the yearp instance variable
     * @return errorcode (int)
     */
    public int getYearpError() {
        return yearpError;
    } // method getYearpError

    /**
     * Gets the errorMessage for the yearp instance variable
     * @return errorMessage (String)
     */
    public String getYearpErrorMessage() {
        return yearpErrorMessage;
    } // method getYearpErrorMessage


    /**
     * Gets the errorcode for the monthp instance variable
     * @return errorcode (int)
     */
    public int getMonthpError() {
        return monthpError;
    } // method getMonthpError

    /**
     * Gets the errorMessage for the monthp instance variable
     * @return errorMessage (String)
     */
    public String getMonthpErrorMessage() {
        return monthpErrorMessage;
    } // method getMonthpErrorMessage


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
     * Gets the errorcode for the stats instance variable
     * @return errorcode (int)
     */
    public int getStatsError() {
        return statsError;
    } // method getStatsError

    /**
     * Gets the errorMessage for the stats instance variable
     * @return errorMessage (String)
     */
    public String getStatsErrorMessage() {
        return statsErrorMessage;
    } // method getStatsErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of WavInstStats. e.g.<pre>
     * WavInstStats wavInstStats = new WavInstStats(<val1>);
     * WavInstStats wavInstStatsArray[] = wavInstStats.get();</pre>
     * will get the WavInstStats record where stationId = <val1>.
     * @return Array of WavInstStats (WavInstStats[])
     */
    public WavInstStats[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * WavInstStats wavInstStatsArray[] = 
     *     new WavInstStats().get(WavInstStats.STATION_ID+"=<val1>");</pre>
     * will get the WavInstStats record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of WavInstStats (WavInstStats[])
     */
    public WavInstStats[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * WavInstStats wavInstStatsArray[] = 
     *     new WavInstStats().get("1=1",wavInstStats.STATION_ID);</pre>
     * will get the all the WavInstStats records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WavInstStats (WavInstStats[])
     */
    public WavInstStats[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = WavInstStats.STATION_ID,WavInstStats.YEARP;
     * String where = WavInstStats.STATION_ID + "=<val1";
     * String order = WavInstStats.STATION_ID;
     * WavInstStats wavInstStatsArray[] = 
     *     new WavInstStats().get(columns, where, order);</pre>
     * will get the stationId and yearp colums of all WavInstStats records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WavInstStats (WavInstStats[])
     */
    public WavInstStats[] get (String fields, String where, String order) {
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
     * and transforms it into an array of WavInstStats.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private WavInstStats[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol      = db.getColNumber(STATION_ID);
        int yearpCol          = db.getColNumber(YEARP);
        int monthpCol         = db.getColNumber(MONTHP);
        int instrumentCodeCol = db.getColNumber(INSTRUMENT_CODE);
        int statsCol          = db.getColNumber(STATS);
        WavInstStats[] cArray = new WavInstStats[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new WavInstStats();
            if (stationIdCol != -1)
                cArray[i].setStationId     ((String) row.elementAt(stationIdCol));
            if (yearpCol != -1)
                cArray[i].setYearp         ((String) row.elementAt(yearpCol));
            if (monthpCol != -1)
                cArray[i].setMonthp        ((String) row.elementAt(monthpCol));
            if (instrumentCodeCol != -1)
                cArray[i].setInstrumentCode((String) row.elementAt(instrumentCodeCol));
            if (statsCol != -1)
                cArray[i].setStats         ((String) row.elementAt(statsCol));
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
     *     new WavInstStats(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>).put();</pre>
     * will insert a record with:
     *     stationId      = <val1>,
     *     yearp          = <val2>,
     *     monthp         = <val3>,
     *     instrumentCode = <val4>,
     *     stats          = <val5>.
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
     * Boolean success = new WavInstStats(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where yearp = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WavInstStats wavInstStats = new WavInstStats();
     * success = wavInstStats.del(WavInstStats.STATION_ID+"=<val1>");</pre>
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
     * update are taken from the WavInstStats argument, .e.g.<pre>
     * Boolean success
     * WavInstStats updWavInstStats = new WavInstStats();
     * updWavInstStats.setYearp(<val2>);
     * WavInstStats whereWavInstStats = new WavInstStats(<val1>);
     * success = whereWavInstStats.upd(updWavInstStats);</pre>
     * will update Yearp to <val2> for all records where 
     * stationId = <val1>.
     * @param wavInstStats  A WavInstStats variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(WavInstStats wavInstStats) {
        return db.update (TABLE, createColVals(wavInstStats), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WavInstStats updWavInstStats = new WavInstStats();
     * updWavInstStats.setYearp(<val2>);
     * WavInstStats whereWavInstStats = new WavInstStats();
     * success = whereWavInstStats.upd(
     *     updWavInstStats, WavInstStats.STATION_ID+"=<val1>");</pre>
     * will update Yearp to <val2> for all records where 
     * stationId = <val1>.
     * @param  updWavInstStats  A WavInstStats variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(WavInstStats wavInstStats, String where) {
        return db.update (TABLE, createColVals(wavInstStats), where);
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
        if (getYearp() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + YEARP + "=" + getYearp("");
        } // if getYearp
        if (getMonthp() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MONTHP + "=" + getMonthp("");
        } // if getMonthp
        if (getInstrumentCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTRUMENT_CODE + "=" + getInstrumentCode("");
        } // if getInstrumentCode
        if (getStats() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATS + "=" + getStats("");
        } // if getStats
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(WavInstStats aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getYearp() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += YEARP +"=";
            colVals += (aVar.getYearp() == INTNULL2 ?
                "null" : aVar.getYearp(""));
        } // if aVar.getYearp
        if (aVar.getMonthp() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MONTHP +"=";
            colVals += (aVar.getMonthp() == INTNULL2 ?
                "null" : aVar.getMonthp(""));
        } // if aVar.getMonthp
        if (aVar.getInstrumentCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTRUMENT_CODE +"=";
            colVals += (aVar.getInstrumentCode() == INTNULL2 ?
                "null" : aVar.getInstrumentCode(""));
        } // if aVar.getInstrumentCode
        if (aVar.getStats() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATS +"=";
            colVals += (aVar.getStats() == INTNULL2 ?
                "null" : aVar.getStats(""));
        } // if aVar.getStats
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (getYearp() != INTNULL) {
            columns = columns + "," + YEARP;
        } // if getYearp
        if (getMonthp() != INTNULL) {
            columns = columns + "," + MONTHP;
        } // if getMonthp
        if (getInstrumentCode() != INTNULL) {
            columns = columns + "," + INSTRUMENT_CODE;
        } // if getInstrumentCode
        if (getStats() != INTNULL) {
            columns = columns + "," + STATS;
        } // if getStats
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (getYearp() != INTNULL) {
            values  = values  + "," + getYearp("");
        } // if getYearp
        if (getMonthp() != INTNULL) {
            values  = values  + "," + getMonthp("");
        } // if getMonthp
        if (getInstrumentCode() != INTNULL) {
            values  = values  + "," + getInstrumentCode("");
        } // if getInstrumentCode
        if (getStats() != INTNULL) {
            values  = values  + "," + getStats("");
        } // if getStats
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getStationId("")      + "|" +
            getYearp("")          + "|" +
            getMonthp("")         + "|" +
            getInstrumentCode("") + "|" +
            getStats("")          + "|";
    } // method toString

} // class WavInstStats
