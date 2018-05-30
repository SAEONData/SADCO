package sadco;

import java.sql.Timestamp;
import java.util.Vector;

/**
 * This class manages the station_stats table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 20130225 - SIT Group
 * @version
 * 20130225 - GenTableClassB class - create class<br>
 */
public class MrnStationStats extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "station_stats";
    /** The stationId field name */
    public static final String STATION_ID = "station_id";
    /** The currentsCnt field name */
    public static final String CURRENTS_CNT = "currents_cnt";
    /** The plaphyCnt field name */
    public static final String PLAPHY_CNT = "plaphy_cnt";
    /** The sedphyCnt field name */
    public static final String SEDPHY_CNT = "sedphy_cnt";
    /** The tisphyCnt field name */
    public static final String TISPHY_CNT = "tisphy_cnt";
    /** The watphyCnt field name */
    public static final String WATPHY_CNT = "watphy_cnt";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private int       currentsCnt;
    private int       plaphyCnt;
    private int       sedphyCnt;
    private int       tisphyCnt;
    private int       watphyCnt;

    /** The error variables  */
    private int stationIdError   = ERROR_NORMAL;
    private int currentsCntError = ERROR_NORMAL;
    private int plaphyCntError   = ERROR_NORMAL;
    private int sedphyCntError   = ERROR_NORMAL;
    private int tisphyCntError   = ERROR_NORMAL;
    private int watphyCntError   = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage   = "";
    private String currentsCntErrorMessage = "";
    private String plaphyCntErrorMessage   = "";
    private String sedphyCntErrorMessage   = "";
    private String tisphyCntErrorMessage   = "";
    private String watphyCntErrorMessage   = "";

    /** The min-max constants for all numerics */
    public static final int       CURRENTS_CNT_MN = INTMIN;
    public static final int       CURRENTS_CNT_MX = INTMAX;
    public static final int       PLAPHY_CNT_MN = INTMIN;
    public static final int       PLAPHY_CNT_MX = INTMAX;
    public static final int       SEDPHY_CNT_MN = INTMIN;
    public static final int       SEDPHY_CNT_MX = INTMAX;
    public static final int       TISPHY_CNT_MN = INTMIN;
    public static final int       TISPHY_CNT_MX = INTMAX;
    public static final int       WATPHY_CNT_MN = INTMIN;
    public static final int       WATPHY_CNT_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception currentsCntOutOfBoundsException =
        new Exception ("'currentsCnt' out of bounds: " +
            CURRENTS_CNT_MN + " - " + CURRENTS_CNT_MX);
    Exception plaphyCntOutOfBoundsException =
        new Exception ("'plaphyCnt' out of bounds: " +
            PLAPHY_CNT_MN + " - " + PLAPHY_CNT_MX);
    Exception sedphyCntOutOfBoundsException =
        new Exception ("'sedphyCnt' out of bounds: " +
            SEDPHY_CNT_MN + " - " + SEDPHY_CNT_MX);
    Exception tisphyCntOutOfBoundsException =
        new Exception ("'tisphyCnt' out of bounds: " +
            TISPHY_CNT_MN + " - " + TISPHY_CNT_MX);
    Exception watphyCntOutOfBoundsException =
        new Exception ("'watphyCnt' out of bounds: " +
            WATPHY_CNT_MN + " - " + WATPHY_CNT_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnStationStats() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnStationStats constructor 1"); // debug
    } // MrnStationStats constructor

    /**
     * Instantiate a MrnStationStats object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public MrnStationStats(
            String stationId) {
        this();
        setStationId   (stationId  );
        if (dbg) System.out.println ("<br>in MrnStationStats constructor 2"); // debug
    } // MrnStationStats constructor

    /**
     * Instantiate a MrnStationStats object and initialize the instance variables.
     * @param stationId    The stationId   (String)
     * @param currentsCnt  The currentsCnt (int)
     * @param plaphyCnt    The plaphyCnt   (int)
     * @param sedphyCnt    The sedphyCnt   (int)
     * @param tisphyCnt    The tisphyCnt   (int)
     * @param watphyCnt    The watphyCnt   (int)
     * @return A MrnStationStats object
     */
    public MrnStationStats(
            String stationId,
            int    currentsCnt,
            int    plaphyCnt,
            int    sedphyCnt,
            int    tisphyCnt,
            int    watphyCnt) {
        this();
        setStationId   (stationId  );
        setCurrentsCnt (currentsCnt);
        setPlaphyCnt   (plaphyCnt  );
        setSedphyCnt   (sedphyCnt  );
        setTisphyCnt   (tisphyCnt  );
        setWatphyCnt   (watphyCnt  );
        if (dbg) System.out.println ("<br>in MrnStationStats constructor 3"); // debug
    } // MrnStationStats constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId   (CHARNULL);
        setCurrentsCnt (INTNULL );
        setPlaphyCnt   (INTNULL );
        setSedphyCnt   (INTNULL );
        setTisphyCnt   (INTNULL );
        setWatphyCnt   (INTNULL );
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
     * Set the 'currentsCnt' class variable
     * @param  currentsCnt (int)
     */
    public int setCurrentsCnt(int currentsCnt) {
        try {
            if ( ((currentsCnt == INTNULL) ||
                  (currentsCnt == INTNULL2)) ||
                !((currentsCnt < CURRENTS_CNT_MN) ||
                  (currentsCnt > CURRENTS_CNT_MX)) ) {
                this.currentsCnt = currentsCnt;
                currentsCntError = ERROR_NORMAL;
            } else {
                throw currentsCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCurrentsCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return currentsCntError;
    } // method setCurrentsCnt

    /**
     * Set the 'currentsCnt' class variable
     * @param  currentsCnt (Integer)
     */
    public int setCurrentsCnt(Integer currentsCnt) {
        try {
            setCurrentsCnt(currentsCnt.intValue());
        } catch (Exception e) {
            setCurrentsCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return currentsCntError;
    } // method setCurrentsCnt

    /**
     * Set the 'currentsCnt' class variable
     * @param  currentsCnt (Float)
     */
    public int setCurrentsCnt(Float currentsCnt) {
        try {
            if (currentsCnt.floatValue() == FLOATNULL) {
                setCurrentsCnt(INTNULL);
            } else {
                setCurrentsCnt(currentsCnt.intValue());
            } // if (currentsCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setCurrentsCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return currentsCntError;
    } // method setCurrentsCnt

    /**
     * Set the 'currentsCnt' class variable
     * @param  currentsCnt (String)
     */
    public int setCurrentsCnt(String currentsCnt) {
        try {
            setCurrentsCnt(new Integer(currentsCnt).intValue());
        } catch (Exception e) {
            setCurrentsCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return currentsCntError;
    } // method setCurrentsCnt

    /**
     * Called when an exception has occured
     * @param  currentsCnt (String)
     */
    private void setCurrentsCntError (int currentsCnt, Exception e, int error) {
        this.currentsCnt = currentsCnt;
        currentsCntErrorMessage = e.toString();
        currentsCntError = error;
    } // method setCurrentsCntError


    /**
     * Set the 'plaphyCnt' class variable
     * @param  plaphyCnt (int)
     */
    public int setPlaphyCnt(int plaphyCnt) {
        try {
            if ( ((plaphyCnt == INTNULL) ||
                  (plaphyCnt == INTNULL2)) ||
                !((plaphyCnt < PLAPHY_CNT_MN) ||
                  (plaphyCnt > PLAPHY_CNT_MX)) ) {
                this.plaphyCnt = plaphyCnt;
                plaphyCntError = ERROR_NORMAL;
            } else {
                throw plaphyCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlaphyCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plaphyCntError;
    } // method setPlaphyCnt

    /**
     * Set the 'plaphyCnt' class variable
     * @param  plaphyCnt (Integer)
     */
    public int setPlaphyCnt(Integer plaphyCnt) {
        try {
            setPlaphyCnt(plaphyCnt.intValue());
        } catch (Exception e) {
            setPlaphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCntError;
    } // method setPlaphyCnt

    /**
     * Set the 'plaphyCnt' class variable
     * @param  plaphyCnt (Float)
     */
    public int setPlaphyCnt(Float plaphyCnt) {
        try {
            if (plaphyCnt.floatValue() == FLOATNULL) {
                setPlaphyCnt(INTNULL);
            } else {
                setPlaphyCnt(plaphyCnt.intValue());
            } // if (plaphyCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlaphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCntError;
    } // method setPlaphyCnt

    /**
     * Set the 'plaphyCnt' class variable
     * @param  plaphyCnt (String)
     */
    public int setPlaphyCnt(String plaphyCnt) {
        try {
            setPlaphyCnt(new Integer(plaphyCnt).intValue());
        } catch (Exception e) {
            setPlaphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCntError;
    } // method setPlaphyCnt

    /**
     * Called when an exception has occured
     * @param  plaphyCnt (String)
     */
    private void setPlaphyCntError (int plaphyCnt, Exception e, int error) {
        this.plaphyCnt = plaphyCnt;
        plaphyCntErrorMessage = e.toString();
        plaphyCntError = error;
    } // method setPlaphyCntError


    /**
     * Set the 'sedphyCnt' class variable
     * @param  sedphyCnt (int)
     */
    public int setSedphyCnt(int sedphyCnt) {
        try {
            if ( ((sedphyCnt == INTNULL) ||
                  (sedphyCnt == INTNULL2)) ||
                !((sedphyCnt < SEDPHY_CNT_MN) ||
                  (sedphyCnt > SEDPHY_CNT_MX)) ) {
                this.sedphyCnt = sedphyCnt;
                sedphyCntError = ERROR_NORMAL;
            } else {
                throw sedphyCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedphyCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedphyCntError;
    } // method setSedphyCnt

    /**
     * Set the 'sedphyCnt' class variable
     * @param  sedphyCnt (Integer)
     */
    public int setSedphyCnt(Integer sedphyCnt) {
        try {
            setSedphyCnt(sedphyCnt.intValue());
        } catch (Exception e) {
            setSedphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyCntError;
    } // method setSedphyCnt

    /**
     * Set the 'sedphyCnt' class variable
     * @param  sedphyCnt (Float)
     */
    public int setSedphyCnt(Float sedphyCnt) {
        try {
            if (sedphyCnt.floatValue() == FLOATNULL) {
                setSedphyCnt(INTNULL);
            } else {
                setSedphyCnt(sedphyCnt.intValue());
            } // if (sedphyCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyCntError;
    } // method setSedphyCnt

    /**
     * Set the 'sedphyCnt' class variable
     * @param  sedphyCnt (String)
     */
    public int setSedphyCnt(String sedphyCnt) {
        try {
            setSedphyCnt(new Integer(sedphyCnt).intValue());
        } catch (Exception e) {
            setSedphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyCntError;
    } // method setSedphyCnt

    /**
     * Called when an exception has occured
     * @param  sedphyCnt (String)
     */
    private void setSedphyCntError (int sedphyCnt, Exception e, int error) {
        this.sedphyCnt = sedphyCnt;
        sedphyCntErrorMessage = e.toString();
        sedphyCntError = error;
    } // method setSedphyCntError


    /**
     * Set the 'tisphyCnt' class variable
     * @param  tisphyCnt (int)
     */
    public int setTisphyCnt(int tisphyCnt) {
        try {
            if ( ((tisphyCnt == INTNULL) ||
                  (tisphyCnt == INTNULL2)) ||
                !((tisphyCnt < TISPHY_CNT_MN) ||
                  (tisphyCnt > TISPHY_CNT_MX)) ) {
                this.tisphyCnt = tisphyCnt;
                tisphyCntError = ERROR_NORMAL;
            } else {
                throw tisphyCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTisphyCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tisphyCntError;
    } // method setTisphyCnt

    /**
     * Set the 'tisphyCnt' class variable
     * @param  tisphyCnt (Integer)
     */
    public int setTisphyCnt(Integer tisphyCnt) {
        try {
            setTisphyCnt(tisphyCnt.intValue());
        } catch (Exception e) {
            setTisphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyCntError;
    } // method setTisphyCnt

    /**
     * Set the 'tisphyCnt' class variable
     * @param  tisphyCnt (Float)
     */
    public int setTisphyCnt(Float tisphyCnt) {
        try {
            if (tisphyCnt.floatValue() == FLOATNULL) {
                setTisphyCnt(INTNULL);
            } else {
                setTisphyCnt(tisphyCnt.intValue());
            } // if (tisphyCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTisphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyCntError;
    } // method setTisphyCnt

    /**
     * Set the 'tisphyCnt' class variable
     * @param  tisphyCnt (String)
     */
    public int setTisphyCnt(String tisphyCnt) {
        try {
            setTisphyCnt(new Integer(tisphyCnt).intValue());
        } catch (Exception e) {
            setTisphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyCntError;
    } // method setTisphyCnt

    /**
     * Called when an exception has occured
     * @param  tisphyCnt (String)
     */
    private void setTisphyCntError (int tisphyCnt, Exception e, int error) {
        this.tisphyCnt = tisphyCnt;
        tisphyCntErrorMessage = e.toString();
        tisphyCntError = error;
    } // method setTisphyCntError


    /**
     * Set the 'watphyCnt' class variable
     * @param  watphyCnt (int)
     */
    public int setWatphyCnt(int watphyCnt) {
        try {
            if ( ((watphyCnt == INTNULL) ||
                  (watphyCnt == INTNULL2)) ||
                !((watphyCnt < WATPHY_CNT_MN) ||
                  (watphyCnt > WATPHY_CNT_MX)) ) {
                this.watphyCnt = watphyCnt;
                watphyCntError = ERROR_NORMAL;
            } else {
                throw watphyCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatphyCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watphyCntError;
    } // method setWatphyCnt

    /**
     * Set the 'watphyCnt' class variable
     * @param  watphyCnt (Integer)
     */
    public int setWatphyCnt(Integer watphyCnt) {
        try {
            setWatphyCnt(watphyCnt.intValue());
        } catch (Exception e) {
            setWatphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCntError;
    } // method setWatphyCnt

    /**
     * Set the 'watphyCnt' class variable
     * @param  watphyCnt (Float)
     */
    public int setWatphyCnt(Float watphyCnt) {
        try {
            if (watphyCnt.floatValue() == FLOATNULL) {
                setWatphyCnt(INTNULL);
            } else {
                setWatphyCnt(watphyCnt.intValue());
            } // if (watphyCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCntError;
    } // method setWatphyCnt

    /**
     * Set the 'watphyCnt' class variable
     * @param  watphyCnt (String)
     */
    public int setWatphyCnt(String watphyCnt) {
        try {
            setWatphyCnt(new Integer(watphyCnt).intValue());
        } catch (Exception e) {
            setWatphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCntError;
    } // method setWatphyCnt

    /**
     * Called when an exception has occured
     * @param  watphyCnt (String)
     */
    private void setWatphyCntError (int watphyCnt, Exception e, int error) {
        this.watphyCnt = watphyCnt;
        watphyCntErrorMessage = e.toString();
        watphyCntError = error;
    } // method setWatphyCntError


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
     * Return the 'currentsCnt' class variable
     * @return currentsCnt (int)
     */
    public int getCurrentsCnt() {
        return currentsCnt;
    } // method getCurrentsCnt

    /**
     * Return the 'currentsCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCurrentsCnt methods
     * @return currentsCnt (String)
     */
    public String getCurrentsCnt(String s) {
        return ((currentsCnt != INTNULL) ? new Integer(currentsCnt).toString() : "");
    } // method getCurrentsCnt


    /**
     * Return the 'plaphyCnt' class variable
     * @return plaphyCnt (int)
     */
    public int getPlaphyCnt() {
        return plaphyCnt;
    } // method getPlaphyCnt

    /**
     * Return the 'plaphyCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlaphyCnt methods
     * @return plaphyCnt (String)
     */
    public String getPlaphyCnt(String s) {
        return ((plaphyCnt != INTNULL) ? new Integer(plaphyCnt).toString() : "");
    } // method getPlaphyCnt


    /**
     * Return the 'sedphyCnt' class variable
     * @return sedphyCnt (int)
     */
    public int getSedphyCnt() {
        return sedphyCnt;
    } // method getSedphyCnt

    /**
     * Return the 'sedphyCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedphyCnt methods
     * @return sedphyCnt (String)
     */
    public String getSedphyCnt(String s) {
        return ((sedphyCnt != INTNULL) ? new Integer(sedphyCnt).toString() : "");
    } // method getSedphyCnt


    /**
     * Return the 'tisphyCnt' class variable
     * @return tisphyCnt (int)
     */
    public int getTisphyCnt() {
        return tisphyCnt;
    } // method getTisphyCnt

    /**
     * Return the 'tisphyCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTisphyCnt methods
     * @return tisphyCnt (String)
     */
    public String getTisphyCnt(String s) {
        return ((tisphyCnt != INTNULL) ? new Integer(tisphyCnt).toString() : "");
    } // method getTisphyCnt


    /**
     * Return the 'watphyCnt' class variable
     * @return watphyCnt (int)
     */
    public int getWatphyCnt() {
        return watphyCnt;
    } // method getWatphyCnt

    /**
     * Return the 'watphyCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatphyCnt methods
     * @return watphyCnt (String)
     */
    public String getWatphyCnt(String s) {
        return ((watphyCnt != INTNULL) ? new Integer(watphyCnt).toString() : "");
    } // method getWatphyCnt


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
            (currentsCnt == INTNULL) &&
            (plaphyCnt == INTNULL) &&
            (sedphyCnt == INTNULL) &&
            (tisphyCnt == INTNULL) &&
            (watphyCnt == INTNULL)) {
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
            currentsCntError +
            plaphyCntError +
            sedphyCntError +
            tisphyCntError +
            watphyCntError;
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
     * Gets the errorcode for the currentsCnt instance variable
     * @return errorcode (int)
     */
    public int getCurrentsCntError() {
        return currentsCntError;
    } // method getCurrentsCntError

    /**
     * Gets the errorMessage for the currentsCnt instance variable
     * @return errorMessage (String)
     */
    public String getCurrentsCntErrorMessage() {
        return currentsCntErrorMessage;
    } // method getCurrentsCntErrorMessage


    /**
     * Gets the errorcode for the plaphyCnt instance variable
     * @return errorcode (int)
     */
    public int getPlaphyCntError() {
        return plaphyCntError;
    } // method getPlaphyCntError

    /**
     * Gets the errorMessage for the plaphyCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlaphyCntErrorMessage() {
        return plaphyCntErrorMessage;
    } // method getPlaphyCntErrorMessage


    /**
     * Gets the errorcode for the sedphyCnt instance variable
     * @return errorcode (int)
     */
    public int getSedphyCntError() {
        return sedphyCntError;
    } // method getSedphyCntError

    /**
     * Gets the errorMessage for the sedphyCnt instance variable
     * @return errorMessage (String)
     */
    public String getSedphyCntErrorMessage() {
        return sedphyCntErrorMessage;
    } // method getSedphyCntErrorMessage


    /**
     * Gets the errorcode for the tisphyCnt instance variable
     * @return errorcode (int)
     */
    public int getTisphyCntError() {
        return tisphyCntError;
    } // method getTisphyCntError

    /**
     * Gets the errorMessage for the tisphyCnt instance variable
     * @return errorMessage (String)
     */
    public String getTisphyCntErrorMessage() {
        return tisphyCntErrorMessage;
    } // method getTisphyCntErrorMessage


    /**
     * Gets the errorcode for the watphyCnt instance variable
     * @return errorcode (int)
     */
    public int getWatphyCntError() {
        return watphyCntError;
    } // method getWatphyCntError

    /**
     * Gets the errorMessage for the watphyCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatphyCntErrorMessage() {
        return watphyCntErrorMessage;
    } // method getWatphyCntErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnStationStats. e.g.<pre>
     * MrnStationStats stationStats = new MrnStationStats(<val1>);
     * MrnStationStats stationStatsArray[] = stationStats.get();</pre>
     * will get the MrnStationStats record where stationId = <val1>.
     * @return Array of MrnStationStats (MrnStationStats[])
     */
    public MrnStationStats[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnStationStats stationStatsArray[] =
     *     new MrnStationStats().get(MrnStationStats.STATION_ID+"=<val1>");</pre>
     * will get the MrnStationStats record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnStationStats (MrnStationStats[])
     */
    public MrnStationStats[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnStationStats stationStatsArray[] =
     *     new MrnStationStats().get("1=1",stationStats.STATION_ID);</pre>
     * will get the all the MrnStationStats records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnStationStats (MrnStationStats[])
     */
    public MrnStationStats[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnStationStats.STATION_ID,MrnStationStats.CURRENTS_CNT;
     * String where = MrnStationStats.STATION_ID + "=<val1";
     * String order = MrnStationStats.STATION_ID;
     * MrnStationStats stationStatsArray[] =
     *     new MrnStationStats().get(columns, where, order);</pre>
     * will get the stationId and currentsCnt colums of all MrnStationStats records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnStationStats (MrnStationStats[])
     */
    public MrnStationStats[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnStationStats.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnStationStats[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol   = db.getColNumber(STATION_ID);
        int currentsCntCol = db.getColNumber(CURRENTS_CNT);
        int plaphyCntCol   = db.getColNumber(PLAPHY_CNT);
        int sedphyCntCol   = db.getColNumber(SEDPHY_CNT);
        int tisphyCntCol   = db.getColNumber(TISPHY_CNT);
        int watphyCntCol   = db.getColNumber(WATPHY_CNT);
        MrnStationStats[] cArray = new MrnStationStats[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnStationStats();
            if (stationIdCol != -1)
                cArray[i].setStationId  ((String) row.elementAt(stationIdCol));
            if (currentsCntCol != -1)
                cArray[i].setCurrentsCnt((String) row.elementAt(currentsCntCol));
            if (plaphyCntCol != -1)
                cArray[i].setPlaphyCnt  ((String) row.elementAt(plaphyCntCol));
            if (sedphyCntCol != -1)
                cArray[i].setSedphyCnt  ((String) row.elementAt(sedphyCntCol));
            if (tisphyCntCol != -1)
                cArray[i].setTisphyCnt  ((String) row.elementAt(tisphyCntCol));
            if (watphyCntCol != -1)
                cArray[i].setWatphyCnt  ((String) row.elementAt(watphyCntCol));
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
     *     new MrnStationStats(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>).put();</pre>
     * will insert a record with:
     *     stationId   = <val1>,
     *     currentsCnt = <val2>,
     *     plaphyCnt   = <val3>,
     *     sedphyCnt   = <val4>,
     *     tisphyCnt   = <val5>,
     *     watphyCnt   = <val6>.
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
     * Boolean success = new MrnStationStats(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where currentsCnt = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnStationStats stationStats = new MrnStationStats();
     * success = stationStats.del(MrnStationStats.STATION_ID+"=<val1>");</pre>
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
     * update are taken from the MrnStationStats argument, .e.g.<pre>
     * Boolean success
     * MrnStationStats updMrnStationStats = new MrnStationStats();
     * updMrnStationStats.setCurrentsCnt(<val2>);
     * MrnStationStats whereMrnStationStats = new MrnStationStats(<val1>);
     * success = whereMrnStationStats.upd(updMrnStationStats);</pre>
     * will update CurrentsCnt to <val2> for all records where
     * stationId = <val1>.
     * @param stationStats  A MrnStationStats variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnStationStats stationStats) {
        return db.update (TABLE, createColVals(stationStats), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnStationStats updMrnStationStats = new MrnStationStats();
     * updMrnStationStats.setCurrentsCnt(<val2>);
     * MrnStationStats whereMrnStationStats = new MrnStationStats();
     * success = whereMrnStationStats.upd(
     *     updMrnStationStats, MrnStationStats.STATION_ID+"=<val1>");</pre>
     * will update CurrentsCnt to <val2> for all records where
     * stationId = <val1>.
     * @param  updMrnStationStats  A MrnStationStats variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnStationStats stationStats, String where) {
        return db.update (TABLE, createColVals(stationStats), where);
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
        if (getCurrentsCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CURRENTS_CNT + "=" + getCurrentsCnt("");
        } // if getCurrentsCnt
        if (getPlaphyCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPHY_CNT + "=" + getPlaphyCnt("");
        } // if getPlaphyCnt
        if (getSedphyCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDPHY_CNT + "=" + getSedphyCnt("");
        } // if getSedphyCnt
        if (getTisphyCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISPHY_CNT + "=" + getTisphyCnt("");
        } // if getTisphyCnt
        if (getWatphyCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPHY_CNT + "=" + getWatphyCnt("");
        } // if getWatphyCnt
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnStationStats aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getCurrentsCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CURRENTS_CNT +"=";
            colVals += (aVar.getCurrentsCnt() == INTNULL2 ?
                "null" : aVar.getCurrentsCnt(""));
        } // if aVar.getCurrentsCnt
        if (aVar.getPlaphyCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPHY_CNT +"=";
            colVals += (aVar.getPlaphyCnt() == INTNULL2 ?
                "null" : aVar.getPlaphyCnt(""));
        } // if aVar.getPlaphyCnt
        if (aVar.getSedphyCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPHY_CNT +"=";
            colVals += (aVar.getSedphyCnt() == INTNULL2 ?
                "null" : aVar.getSedphyCnt(""));
        } // if aVar.getSedphyCnt
        if (aVar.getTisphyCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISPHY_CNT +"=";
            colVals += (aVar.getTisphyCnt() == INTNULL2 ?
                "null" : aVar.getTisphyCnt(""));
        } // if aVar.getTisphyCnt
        if (aVar.getWatphyCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPHY_CNT +"=";
            colVals += (aVar.getWatphyCnt() == INTNULL2 ?
                "null" : aVar.getWatphyCnt(""));
        } // if aVar.getWatphyCnt
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (getCurrentsCnt() != INTNULL) {
            columns = columns + "," + CURRENTS_CNT;
        } // if getCurrentsCnt
        if (getPlaphyCnt() != INTNULL) {
            columns = columns + "," + PLAPHY_CNT;
        } // if getPlaphyCnt
        if (getSedphyCnt() != INTNULL) {
            columns = columns + "," + SEDPHY_CNT;
        } // if getSedphyCnt
        if (getTisphyCnt() != INTNULL) {
            columns = columns + "," + TISPHY_CNT;
        } // if getTisphyCnt
        if (getWatphyCnt() != INTNULL) {
            columns = columns + "," + WATPHY_CNT;
        } // if getWatphyCnt
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (getCurrentsCnt() != INTNULL) {
            values  = values  + "," + getCurrentsCnt("");
        } // if getCurrentsCnt
        if (getPlaphyCnt() != INTNULL) {
            values  = values  + "," + getPlaphyCnt("");
        } // if getPlaphyCnt
        if (getSedphyCnt() != INTNULL) {
            values  = values  + "," + getSedphyCnt("");
        } // if getSedphyCnt
        if (getTisphyCnt() != INTNULL) {
            values  = values  + "," + getTisphyCnt("");
        } // if getTisphyCnt
        if (getWatphyCnt() != INTNULL) {
            values  = values  + "," + getWatphyCnt("");
        } // if getWatphyCnt
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getStationId("")   + "|" +
            getCurrentsCnt("") + "|" +
            getPlaphyCnt("")   + "|" +
            getSedphyCnt("")   + "|" +
            getTisphyCnt("")   + "|" +
            getWatphyCnt("")   + "|";
    } // method toString

} // class MrnStationStats
