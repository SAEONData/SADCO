package sadco;

import java.sql.Timestamp;
import java.util.Vector;

/**
 * This class manages the currents table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 20110523 - SIT Group
 * @version
 * 20110523 - GenTableClassB class - create class<br>
 */
public class MrnCurrents extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "currents";
    /** The stationId field name */
    public static final String STATION_ID = "station_id";
    /** The subdes field name */
    public static final String SUBDES = "subdes";
    /** The spldattim field name */
    public static final String SPLDATTIM = "spldattim";
    /** The spldep field name */
    public static final String SPLDEP = "spldep";
    /** The currentDir field name */
    public static final String CURRENT_DIR = "current_dir";
    /** The currentSpeed field name */
    public static final String CURRENT_SPEED = "current_speed";
    /** The percGood field name */
    public static final String PERC_GOOD = "perc_good";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private String    subdes;
    private Timestamp spldattim;
    private float     spldep;
    private int       currentDir;
    private float     currentSpeed;
    private String    percGood;

    /** The error variables  */
    private int stationIdError    = ERROR_NORMAL;
    private int subdesError       = ERROR_NORMAL;
    private int spldattimError    = ERROR_NORMAL;
    private int spldepError       = ERROR_NORMAL;
    private int currentDirError   = ERROR_NORMAL;
    private int currentSpeedError = ERROR_NORMAL;
    private int percGoodError     = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage    = "";
    private String subdesErrorMessage       = "";
    private String spldattimErrorMessage    = "";
    private String spldepErrorMessage       = "";
    private String currentDirErrorMessage   = "";
    private String currentSpeedErrorMessage = "";
    private String percGoodErrorMessage     = "";

    /** The min-max constants for all numerics */
    public static final Timestamp SPLDATTIM_MN = DATEMIN;
    public static final Timestamp SPLDATTIM_MX = DATEMAX;
    public static final float     SPLDEP_MN = FLOATMIN;
    public static final float     SPLDEP_MX = FLOATMAX;
    public static final int       CURRENT_DIR_MN = INTMIN;
    public static final int       CURRENT_DIR_MX = INTMAX;
    public static final float     CURRENT_SPEED_MN = FLOATMIN;
    public static final float     CURRENT_SPEED_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception spldattimException =
        new Exception ("'spldattim' is null");
    Exception spldattimOutOfBoundsException =
        new Exception ("'spldattim' out of bounds: " +
            SPLDATTIM_MN + " - " + SPLDATTIM_MX);
    Exception spldepOutOfBoundsException =
        new Exception ("'spldep' out of bounds: " +
            SPLDEP_MN + " - " + SPLDEP_MX);
    Exception currentDirOutOfBoundsException =
        new Exception ("'currentDir' out of bounds: " +
            CURRENT_DIR_MN + " - " + CURRENT_DIR_MX);
    Exception currentSpeedOutOfBoundsException =
        new Exception ("'currentSpeed' out of bounds: " +
            CURRENT_SPEED_MN + " - " + CURRENT_SPEED_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnCurrents() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnCurrents constructor 1"); // debug
    } // MrnCurrents constructor

    /**
     * Instantiate a MrnCurrents object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public MrnCurrents(
            String stationId) {
        this();
        setStationId    (stationId   );
        if (dbg) System.out.println ("<br>in MrnCurrents constructor 2"); // debug
    } // MrnCurrents constructor

    /**
     * Instantiate a MrnCurrents object and initialize the instance variables.
     * @param stationId     The stationId    (String)
     * @param subdes        The subdes       (String)
     * @param spldattim     The spldattim    (java.util.Date)
     * @param spldep        The spldep       (float)
     * @param currentDir    The currentDir   (int)
     * @param currentSpeed  The currentSpeed (float)
     * @param percGood      The percGood     (String)
     * @return A MrnCurrents object
     */
    public MrnCurrents(
            String         stationId,
            String         subdes,
            java.util.Date spldattim,
            float          spldep,
            int            currentDir,
            float          currentSpeed,
            String         percGood) {
        this();
        setStationId    (stationId   );
        setSubdes       (subdes      );
        setSpldattim    (spldattim   );
        setSpldep       (spldep      );
        setCurrentDir   (currentDir  );
        setCurrentSpeed (currentSpeed);
        setPercGood     (percGood    );
        if (dbg) System.out.println ("<br>in MrnCurrents constructor 3"); // debug
    } // MrnCurrents constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId    (CHARNULL );
        setSubdes       (CHARNULL );
        setSpldattim    (DATENULL );
        setSpldep       (FLOATNULL);
        setCurrentDir   (INTNULL  );
        setCurrentSpeed (FLOATNULL);
        setPercGood     (CHARNULL );
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
     * Set the 'subdes' class variable
     * @param  subdes (String)
     */
    public int setSubdes(String subdes) {
        try {
            this.subdes = subdes;
            if (this.subdes != CHARNULL) {
                this.subdes = stripCRLF(this.subdes.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>subdes = " + this.subdes);
        } catch (Exception e) {
            setSubdesError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return subdesError;
    } // method setSubdes

    /**
     * Called when an exception has occured
     * @param  subdes (String)
     */
    private void setSubdesError (String subdes, Exception e, int error) {
        this.subdes = subdes;
        subdesErrorMessage = e.toString();
        subdesError = error;
    } // method setSubdesError


    /**
     * Set the 'spldattim' class variable
     * @param  spldattim (Timestamp)
     */
    public int setSpldattim(Timestamp spldattim) {
        try {
            if (spldattim == null) { this.spldattim = DATENULL; }
            if ( (spldattim.equals(DATENULL) ||
                  spldattim.equals(DATENULL2)) ||
                !(spldattim.before(SPLDATTIM_MN) ||
                  spldattim.after(SPLDATTIM_MX)) ) {
                this.spldattim = spldattim;
                spldattimError = ERROR_NORMAL;
            } else {
                throw spldattimOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpldattimError(DATENULL, e, ERROR_LOCAL);
        } // try
        return spldattimError;
    } // method setSpldattim

    /**
     * Set the 'spldattim' class variable
     * @param  spldattim (java.util.Date)
     */
    public int setSpldattim(java.util.Date spldattim) {
        try {
            setSpldattim(new Timestamp(spldattim.getTime()));
        } catch (Exception e) {
            setSpldattimError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return spldattimError;
    } // method setSpldattim

    /**
     * Set the 'spldattim' class variable
     * @param  spldattim (String)
     */
    public int setSpldattim(String spldattim) {
        try {
            int len = spldattim.length();
            switch (len) {
            // date &/or times
                case 4: spldattim += "-01";
                case 7: spldattim += "-01";
                case 10: spldattim += " 00";
                case 13: spldattim += ":00";
                case 16: spldattim += ":00"; break;
                // times only
                case 5: spldattim = "1800-01-01 " + spldattim + ":00"; break;
                case 8: spldattim = "1800-01-01 " + spldattim; break;
            } // switch
            if (dbg) System.out.println ("spldattim = " + spldattim);
            setSpldattim(Timestamp.valueOf(spldattim));
        } catch (Exception e) {
            setSpldattimError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return spldattimError;
    } // method setSpldattim

    /**
     * Called when an exception has occured
     * @param  spldattim (String)
     */
    private void setSpldattimError (Timestamp spldattim, Exception e, int error) {
        this.spldattim = spldattim;
        spldattimErrorMessage = e.toString();
        spldattimError = error;
    } // method setSpldattimError


    /**
     * Set the 'spldep' class variable
     * @param  spldep (float)
     */
    public int setSpldep(float spldep) {
        try {
            if ( ((spldep == FLOATNULL) || 
                  (spldep == FLOATNULL2)) ||
                !((spldep < SPLDEP_MN) ||
                  (spldep > SPLDEP_MX)) ) {
                this.spldep = spldep;
                spldepError = ERROR_NORMAL;
            } else {
                throw spldepOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (Integer)
     */
    public int setSpldep(Integer spldep) {
        try {
            setSpldep(spldep.floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (Float)
     */
    public int setSpldep(Float spldep) {
        try {
            setSpldep(spldep.floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (String)
     */
    public int setSpldep(String spldep) {
        try {
            setSpldep(new Float(spldep).floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Called when an exception has occured
     * @param  spldep (String)
     */
    private void setSpldepError (float spldep, Exception e, int error) {
        this.spldep = spldep;
        spldepErrorMessage = e.toString();
        spldepError = error;
    } // method setSpldepError


    /**
     * Set the 'currentDir' class variable
     * @param  currentDir (int)
     */
    public int setCurrentDir(int currentDir) {
        try {
            if ( ((currentDir == INTNULL) || 
                  (currentDir == INTNULL2)) ||
                !((currentDir < CURRENT_DIR_MN) ||
                  (currentDir > CURRENT_DIR_MX)) ) {
                this.currentDir = currentDir;
                currentDirError = ERROR_NORMAL;
            } else {
                throw currentDirOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCurrentDirError(INTNULL, e, ERROR_LOCAL);
        } // try
        return currentDirError;
    } // method setCurrentDir

    /**
     * Set the 'currentDir' class variable
     * @param  currentDir (Integer)
     */
    public int setCurrentDir(Integer currentDir) {
        try {
            setCurrentDir(currentDir.intValue());
        } catch (Exception e) {
            setCurrentDirError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return currentDirError;
    } // method setCurrentDir

    /**
     * Set the 'currentDir' class variable
     * @param  currentDir (Float)
     */
    public int setCurrentDir(Float currentDir) {
        try {
            if (currentDir.floatValue() == FLOATNULL) {
                setCurrentDir(INTNULL);
            } else {
                setCurrentDir(currentDir.intValue());
            } // if (currentDir.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setCurrentDirError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return currentDirError;
    } // method setCurrentDir

    /**
     * Set the 'currentDir' class variable
     * @param  currentDir (String)
     */
    public int setCurrentDir(String currentDir) {
        try {
            setCurrentDir(new Integer(currentDir).intValue());
        } catch (Exception e) {
            setCurrentDirError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return currentDirError;
    } // method setCurrentDir

    /**
     * Called when an exception has occured
     * @param  currentDir (String)
     */
    private void setCurrentDirError (int currentDir, Exception e, int error) {
        this.currentDir = currentDir;
        currentDirErrorMessage = e.toString();
        currentDirError = error;
    } // method setCurrentDirError


    /**
     * Set the 'currentSpeed' class variable
     * @param  currentSpeed (float)
     */
    public int setCurrentSpeed(float currentSpeed) {
        try {
            if ( ((currentSpeed == FLOATNULL) || 
                  (currentSpeed == FLOATNULL2)) ||
                !((currentSpeed < CURRENT_SPEED_MN) ||
                  (currentSpeed > CURRENT_SPEED_MX)) ) {
                this.currentSpeed = currentSpeed;
                currentSpeedError = ERROR_NORMAL;
            } else {
                throw currentSpeedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCurrentSpeedError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return currentSpeedError;
    } // method setCurrentSpeed

    /**
     * Set the 'currentSpeed' class variable
     * @param  currentSpeed (Integer)
     */
    public int setCurrentSpeed(Integer currentSpeed) {
        try {
            setCurrentSpeed(currentSpeed.floatValue());
        } catch (Exception e) {
            setCurrentSpeedError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return currentSpeedError;
    } // method setCurrentSpeed

    /**
     * Set the 'currentSpeed' class variable
     * @param  currentSpeed (Float)
     */
    public int setCurrentSpeed(Float currentSpeed) {
        try {
            setCurrentSpeed(currentSpeed.floatValue());
        } catch (Exception e) {
            setCurrentSpeedError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return currentSpeedError;
    } // method setCurrentSpeed

    /**
     * Set the 'currentSpeed' class variable
     * @param  currentSpeed (String)
     */
    public int setCurrentSpeed(String currentSpeed) {
        try {
            setCurrentSpeed(new Float(currentSpeed).floatValue());
        } catch (Exception e) {
            setCurrentSpeedError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return currentSpeedError;
    } // method setCurrentSpeed

    /**
     * Called when an exception has occured
     * @param  currentSpeed (String)
     */
    private void setCurrentSpeedError (float currentSpeed, Exception e, int error) {
        this.currentSpeed = currentSpeed;
        currentSpeedErrorMessage = e.toString();
        currentSpeedError = error;
    } // method setCurrentSpeedError


    /**
     * Set the 'percGood' class variable
     * @param  percGood (String)
     */
    public int setPercGood(String percGood) {
        try {
            this.percGood = percGood;
            if (this.percGood != CHARNULL) {
                this.percGood = stripCRLF(this.percGood.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>percGood = " + this.percGood);
        } catch (Exception e) {
            setPercGoodError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return percGoodError;
    } // method setPercGood

    /**
     * Called when an exception has occured
     * @param  percGood (String)
     */
    private void setPercGoodError (String percGood, Exception e, int error) {
        this.percGood = percGood;
        percGoodErrorMessage = e.toString();
        percGoodError = error;
    } // method setPercGoodError


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
     * Return the 'subdes' class variable
     * @return subdes (String)
     */
    public String getSubdes() {
        return subdes;
    } // method getSubdes

    /**
     * Return the 'subdes' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSubdes methods
     * @return subdes (String)
     */
    public String getSubdes(String s) {
        return (subdes != CHARNULL ? subdes.replace('"','\'') : "");
    } // method getSubdes


    /**
     * Return the 'spldattim' class variable
     * @return spldattim (Timestamp)
     */
    public Timestamp getSpldattim() {
        return spldattim;
    } // method getSpldattim

    /**
     * Return the 'spldattim' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpldattim methods
     * @return spldattim (String)
     */
    public String getSpldattim(String s) {
        if (spldattim.equals(DATENULL)) {
            return ("");
        } else {
            String spldattimStr = spldattim.toString();
            return spldattimStr.substring(0,spldattimStr.indexOf('.'));
        } // if
    } // method getSpldattim


    /**
     * Return the 'spldep' class variable
     * @return spldep (float)
     */
    public float getSpldep() {
        return spldep;
    } // method getSpldep

    /**
     * Return the 'spldep' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpldep methods
     * @return spldep (String)
     */
    public String getSpldep(String s) {
        return ((spldep != FLOATNULL) ? new Float(spldep).toString() : "");
    } // method getSpldep


    /**
     * Return the 'currentDir' class variable
     * @return currentDir (int)
     */
    public int getCurrentDir() {
        return currentDir;
    } // method getCurrentDir

    /**
     * Return the 'currentDir' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCurrentDir methods
     * @return currentDir (String)
     */
    public String getCurrentDir(String s) {
        return ((currentDir != INTNULL) ? new Integer(currentDir).toString() : "");
    } // method getCurrentDir


    /**
     * Return the 'currentSpeed' class variable
     * @return currentSpeed (float)
     */
    public float getCurrentSpeed() {
        return currentSpeed;
    } // method getCurrentSpeed

    /**
     * Return the 'currentSpeed' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCurrentSpeed methods
     * @return currentSpeed (String)
     */
    public String getCurrentSpeed(String s) {
        return ((currentSpeed != FLOATNULL) ? new Float(currentSpeed).toString() : "");
    } // method getCurrentSpeed


    /**
     * Return the 'percGood' class variable
     * @return percGood (String)
     */
    public String getPercGood() {
        return percGood;
    } // method getPercGood

    /**
     * Return the 'percGood' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPercGood methods
     * @return percGood (String)
     */
    public String getPercGood(String s) {
        return (percGood != CHARNULL ? percGood.replace('"','\'') : "");
    } // method getPercGood


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
            (subdes == CHARNULL) &&
            (spldattim.equals(DATENULL)) &&
            (spldep == FLOATNULL) &&
            (currentDir == INTNULL) &&
            (currentSpeed == FLOATNULL) &&
            (percGood == CHARNULL)) {
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
            subdesError +
            spldattimError +
            spldepError +
            currentDirError +
            currentSpeedError +
            percGoodError;
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
     * Gets the errorcode for the subdes instance variable
     * @return errorcode (int)
     */
    public int getSubdesError() {
        return subdesError;
    } // method getSubdesError

    /**
     * Gets the errorMessage for the subdes instance variable
     * @return errorMessage (String)
     */
    public String getSubdesErrorMessage() {
        return subdesErrorMessage;
    } // method getSubdesErrorMessage


    /**
     * Gets the errorcode for the spldattim instance variable
     * @return errorcode (int)
     */
    public int getSpldattimError() {
        return spldattimError;
    } // method getSpldattimError

    /**
     * Gets the errorMessage for the spldattim instance variable
     * @return errorMessage (String)
     */
    public String getSpldattimErrorMessage() {
        return spldattimErrorMessage;
    } // method getSpldattimErrorMessage


    /**
     * Gets the errorcode for the spldep instance variable
     * @return errorcode (int)
     */
    public int getSpldepError() {
        return spldepError;
    } // method getSpldepError

    /**
     * Gets the errorMessage for the spldep instance variable
     * @return errorMessage (String)
     */
    public String getSpldepErrorMessage() {
        return spldepErrorMessage;
    } // method getSpldepErrorMessage


    /**
     * Gets the errorcode for the currentDir instance variable
     * @return errorcode (int)
     */
    public int getCurrentDirError() {
        return currentDirError;
    } // method getCurrentDirError

    /**
     * Gets the errorMessage for the currentDir instance variable
     * @return errorMessage (String)
     */
    public String getCurrentDirErrorMessage() {
        return currentDirErrorMessage;
    } // method getCurrentDirErrorMessage


    /**
     * Gets the errorcode for the currentSpeed instance variable
     * @return errorcode (int)
     */
    public int getCurrentSpeedError() {
        return currentSpeedError;
    } // method getCurrentSpeedError

    /**
     * Gets the errorMessage for the currentSpeed instance variable
     * @return errorMessage (String)
     */
    public String getCurrentSpeedErrorMessage() {
        return currentSpeedErrorMessage;
    } // method getCurrentSpeedErrorMessage


    /**
     * Gets the errorcode for the percGood instance variable
     * @return errorcode (int)
     */
    public int getPercGoodError() {
        return percGoodError;
    } // method getPercGoodError

    /**
     * Gets the errorMessage for the percGood instance variable
     * @return errorMessage (String)
     */
    public String getPercGoodErrorMessage() {
        return percGoodErrorMessage;
    } // method getPercGoodErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnCurrents. e.g.<pre>
     * MrnCurrents currents = new MrnCurrents(<val1>);
     * MrnCurrents currentsArray[] = currents.get();</pre>
     * will get the MrnCurrents record where stationId = <val1>.
     * @return Array of MrnCurrents (MrnCurrents[])
     */
    public MrnCurrents[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnCurrents currentsArray[] = 
     *     new MrnCurrents().get(MrnCurrents.STATION_ID+"=<val1>");</pre>
     * will get the MrnCurrents record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnCurrents (MrnCurrents[])
     */
    public MrnCurrents[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnCurrents currentsArray[] = 
     *     new MrnCurrents().get("1=1",currents.STATION_ID);</pre>
     * will get the all the MrnCurrents records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnCurrents (MrnCurrents[])
     */
    public MrnCurrents[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnCurrents.STATION_ID,MrnCurrents.SUBDES;
     * String where = MrnCurrents.STATION_ID + "=<val1";
     * String order = MrnCurrents.STATION_ID;
     * MrnCurrents currentsArray[] = 
     *     new MrnCurrents().get(columns, where, order);</pre>
     * will get the stationId and subdes colums of all MrnCurrents records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnCurrents (MrnCurrents[])
     */
    public MrnCurrents[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnCurrents.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnCurrents[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol    = db.getColNumber(STATION_ID);
        int subdesCol       = db.getColNumber(SUBDES);
        int spldattimCol    = db.getColNumber(SPLDATTIM);
        int spldepCol       = db.getColNumber(SPLDEP);
        int currentDirCol   = db.getColNumber(CURRENT_DIR);
        int currentSpeedCol = db.getColNumber(CURRENT_SPEED);
        int percGoodCol     = db.getColNumber(PERC_GOOD);
        MrnCurrents[] cArray = new MrnCurrents[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnCurrents();
            if (stationIdCol != -1)
                cArray[i].setStationId   ((String) row.elementAt(stationIdCol));
            if (subdesCol != -1)
                cArray[i].setSubdes      ((String) row.elementAt(subdesCol));
            if (spldattimCol != -1)
                cArray[i].setSpldattim   ((String) row.elementAt(spldattimCol));
            if (spldepCol != -1)
                cArray[i].setSpldep      ((String) row.elementAt(spldepCol));
            if (currentDirCol != -1)
                cArray[i].setCurrentDir  ((String) row.elementAt(currentDirCol));
            if (currentSpeedCol != -1)
                cArray[i].setCurrentSpeed((String) row.elementAt(currentSpeedCol));
            if (percGoodCol != -1)
                cArray[i].setPercGood    ((String) row.elementAt(percGoodCol));
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
     *     new MrnCurrents(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>).put();</pre>
     * will insert a record with:
     *     stationId    = <val1>,
     *     subdes       = <val2>,
     *     spldattim    = <val3>,
     *     spldep       = <val4>,
     *     currentDir   = <val5>,
     *     currentSpeed = <val6>,
     *     percGood     = <val7>.
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
     * Boolean success = new MrnCurrents(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where subdes = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnCurrents currents = new MrnCurrents();
     * success = currents.del(MrnCurrents.STATION_ID+"=<val1>");</pre>
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
     * update are taken from the MrnCurrents argument, .e.g.<pre>
     * Boolean success
     * MrnCurrents updMrnCurrents = new MrnCurrents();
     * updMrnCurrents.setSubdes(<val2>);
     * MrnCurrents whereMrnCurrents = new MrnCurrents(<val1>);
     * success = whereMrnCurrents.upd(updMrnCurrents);</pre>
     * will update Subdes to <val2> for all records where 
     * stationId = <val1>.
     * @param currents  A MrnCurrents variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnCurrents currents) {
        return db.update (TABLE, createColVals(currents), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnCurrents updMrnCurrents = new MrnCurrents();
     * updMrnCurrents.setSubdes(<val2>);
     * MrnCurrents whereMrnCurrents = new MrnCurrents();
     * success = whereMrnCurrents.upd(
     *     updMrnCurrents, MrnCurrents.STATION_ID+"=<val1>");</pre>
     * will update Subdes to <val2> for all records where 
     * stationId = <val1>.
     * @param  updMrnCurrents  A MrnCurrents variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnCurrents currents, String where) {
        return db.update (TABLE, createColVals(currents), where);
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
        if (getSubdes() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SUBDES + "='" + getSubdes() + "'";
        } // if getSubdes
        if (!getSpldattim().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPLDATTIM +
                "=" + Tables.getDateFormat(getSpldattim());
        } // if getSpldattim
        if (getSpldep() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPLDEP + "=" + getSpldep("");
        } // if getSpldep
        if (getCurrentDir() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CURRENT_DIR + "=" + getCurrentDir("");
        } // if getCurrentDir
        if (getCurrentSpeed() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CURRENT_SPEED + "=" + getCurrentSpeed("");
        } // if getCurrentSpeed
        if (getPercGood() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PERC_GOOD + "='" + getPercGood() + "'";
        } // if getPercGood
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnCurrents aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getSubdes() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SUBDES +"=";
            colVals += (aVar.getSubdes().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSubdes() + "'");
        } // if aVar.getSubdes
        if (!aVar.getSpldattim().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPLDATTIM +"=";
            colVals += (aVar.getSpldattim().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getSpldattim()));
        } // if aVar.getSpldattim
        if (aVar.getSpldep() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPLDEP +"=";
            colVals += (aVar.getSpldep() == FLOATNULL2 ?
                "null" : aVar.getSpldep(""));
        } // if aVar.getSpldep
        if (aVar.getCurrentDir() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CURRENT_DIR +"=";
            colVals += (aVar.getCurrentDir() == INTNULL2 ?
                "null" : aVar.getCurrentDir(""));
        } // if aVar.getCurrentDir
        if (aVar.getCurrentSpeed() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CURRENT_SPEED +"=";
            colVals += (aVar.getCurrentSpeed() == FLOATNULL2 ?
                "null" : aVar.getCurrentSpeed(""));
        } // if aVar.getCurrentSpeed
        if (aVar.getPercGood() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PERC_GOOD +"=";
            colVals += (aVar.getPercGood().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPercGood() + "'");
        } // if aVar.getPercGood
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (getSubdes() != CHARNULL) {
            columns = columns + "," + SUBDES;
        } // if getSubdes
        if (!getSpldattim().equals(DATENULL)) {
            columns = columns + "," + SPLDATTIM;
        } // if getSpldattim
        if (getSpldep() != FLOATNULL) {
            columns = columns + "," + SPLDEP;
        } // if getSpldep
        if (getCurrentDir() != INTNULL) {
            columns = columns + "," + CURRENT_DIR;
        } // if getCurrentDir
        if (getCurrentSpeed() != FLOATNULL) {
            columns = columns + "," + CURRENT_SPEED;
        } // if getCurrentSpeed
        if (getPercGood() != CHARNULL) {
            columns = columns + "," + PERC_GOOD;
        } // if getPercGood
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (getSubdes() != CHARNULL) {
            values  = values  + ",'" + getSubdes() + "'";
        } // if getSubdes
        if (!getSpldattim().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getSpldattim());
        } // if getSpldattim
        if (getSpldep() != FLOATNULL) {
            values  = values  + "," + getSpldep("");
        } // if getSpldep
        if (getCurrentDir() != INTNULL) {
            values  = values  + "," + getCurrentDir("");
        } // if getCurrentDir
        if (getCurrentSpeed() != FLOATNULL) {
            values  = values  + "," + getCurrentSpeed("");
        } // if getCurrentSpeed
        if (getPercGood() != CHARNULL) {
            values  = values  + ",'" + getPercGood() + "'";
        } // if getPercGood
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
            getSubdes("")       + "|" +
            getSpldattim("")    + "|" +
            getSpldep("")       + "|" +
            getCurrentDir("")   + "|" +
            getCurrentSpeed("") + "|" +
            getPercGood("")     + "|";
    } // method toString

} // class MrnCurrents
