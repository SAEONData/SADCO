package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WATCURRENTS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnWatcurrents extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WATCURRENTS";
    /** The watphyCode field name */
    public static final String WATPHY_CODE = "WATPHY_CODE";
    /** The currentDir field name */
    public static final String CURRENT_DIR = "CURRENT_DIR";
    /** The currentSpeed field name */
    public static final String CURRENT_SPEED = "CURRENT_SPEED";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       watphyCode;
    private int       currentDir;
    private float     currentSpeed;

    /** The error variables  */
    private int watphyCodeError   = ERROR_NORMAL;
    private int currentDirError   = ERROR_NORMAL;
    private int currentSpeedError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String watphyCodeErrorMessage   = "";
    private String currentDirErrorMessage   = "";
    private String currentSpeedErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final int       WATPHY_CODE_MN = INTMIN;
    public static final int       WATPHY_CODE_MX = INTMAX;
    public static final int       CURRENT_DIR_MN = INTMIN;
    public static final int       CURRENT_DIR_MX = INTMAX;
    public static final float     CURRENT_SPEED_MN = FLOATMIN;
    public static final float     CURRENT_SPEED_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception watphyCodeOutOfBoundsException =
        new Exception ("'watphyCode' out of bounds: " +
            WATPHY_CODE_MN + " - " + WATPHY_CODE_MX);
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
    public MrnWatcurrents() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnWatcurrents constructor 1"); // debug
    } // MrnWatcurrents constructor

    /**
     * Instantiate a MrnWatcurrents object and initialize the instance variables.
     * @param watphyCode  The watphyCode (int)
     */
    public MrnWatcurrents(
            int watphyCode) {
        this();
        setWatphyCode   (watphyCode  );
        if (dbg) System.out.println ("<br>in MrnWatcurrents constructor 2"); // debug
    } // MrnWatcurrents constructor

    /**
     * Instantiate a MrnWatcurrents object and initialize the instance variables.
     * @param watphyCode    The watphyCode   (int)
     * @param currentDir    The currentDir   (int)
     * @param currentSpeed  The currentSpeed (float)
     */
    public MrnWatcurrents(
            int   watphyCode,
            int   currentDir,
            float currentSpeed) {
        this();
        setWatphyCode   (watphyCode  );
        setCurrentDir   (currentDir  );
        setCurrentSpeed (currentSpeed);
        if (dbg) System.out.println ("<br>in MrnWatcurrents constructor 3"); // debug
    } // MrnWatcurrents constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setWatphyCode   (INTNULL  );
        setCurrentDir   (INTNULL  );
        setCurrentSpeed (FLOATNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'watphyCode' class variable
     * @param  watphyCode (int)
     */
    public int setWatphyCode(int watphyCode) {
        try {
            if ( ((watphyCode == INTNULL) ||
                  (watphyCode == INTNULL2)) ||
                !((watphyCode < WATPHY_CODE_MN) ||
                  (watphyCode > WATPHY_CODE_MX)) ) {
                this.watphyCode = watphyCode;
                watphyCodeError = ERROR_NORMAL;
            } else {
                throw watphyCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatphyCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watphyCodeError;
    } // method setWatphyCode

    /**
     * Set the 'watphyCode' class variable
     * @param  watphyCode (Integer)
     */
    public int setWatphyCode(Integer watphyCode) {
        try {
            setWatphyCode(watphyCode.intValue());
        } catch (Exception e) {
            setWatphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCodeError;
    } // method setWatphyCode

    /**
     * Set the 'watphyCode' class variable
     * @param  watphyCode (Float)
     */
    public int setWatphyCode(Float watphyCode) {
        try {
            if (watphyCode.floatValue() == FLOATNULL) {
                setWatphyCode(INTNULL);
            } else {
                setWatphyCode(watphyCode.intValue());
            } // if (watphyCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCodeError;
    } // method setWatphyCode

    /**
     * Set the 'watphyCode' class variable
     * @param  watphyCode (String)
     */
    public int setWatphyCode(String watphyCode) {
        try {
            setWatphyCode(new Integer(watphyCode).intValue());
        } catch (Exception e) {
            setWatphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCodeError;
    } // method setWatphyCode

    /**
     * Called when an exception has occured
     * @param  watphyCode (String)
     */
    private void setWatphyCodeError (int watphyCode, Exception e, int error) {
        this.watphyCode = watphyCode;
        watphyCodeErrorMessage = e.toString();
        watphyCodeError = error;
    } // method setWatphyCodeError


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


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'watphyCode' class variable
     * @return watphyCode (int)
     */
    public int getWatphyCode() {
        return watphyCode;
    } // method getWatphyCode

    /**
     * Return the 'watphyCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatphyCode methods
     * @return watphyCode (String)
     */
    public String getWatphyCode(String s) {
        return ((watphyCode != INTNULL) ? new Integer(watphyCode).toString() : "");
    } // method getWatphyCode


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
        if ((watphyCode == INTNULL) &&
            (currentDir == INTNULL) &&
            (currentSpeed == FLOATNULL)) {
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
        int sumError = watphyCodeError +
            currentDirError +
            currentSpeedError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the watphyCode instance variable
     * @return errorcode (int)
     */
    public int getWatphyCodeError() {
        return watphyCodeError;
    } // method getWatphyCodeError

    /**
     * Gets the errorMessage for the watphyCode instance variable
     * @return errorMessage (String)
     */
    public String getWatphyCodeErrorMessage() {
        return watphyCodeErrorMessage;
    } // method getWatphyCodeErrorMessage


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


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnWatcurrents. e.g.<pre>
     * MrnWatcurrents watcurrents = new MrnWatcurrents(<val1>);
     * MrnWatcurrents watcurrentsArray[] = watcurrents.get();</pre>
     * will get the MrnWatcurrents record where watphyCode = <val1>.
     * @return Array of MrnWatcurrents (MrnWatcurrents[])
     */
    public MrnWatcurrents[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnWatcurrents watcurrentsArray[] =
     *     new MrnWatcurrents().get(MrnWatcurrents.WATPHY_CODE+"=<val1>");</pre>
     * will get the MrnWatcurrents record where watphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnWatcurrents (MrnWatcurrents[])
     */
    public MrnWatcurrents[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnWatcurrents watcurrentsArray[] =
     *     new MrnWatcurrents().get("1=1",watcurrents.WATPHY_CODE);</pre>
     * will get the all the MrnWatcurrents records, and order them by watphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatcurrents (MrnWatcurrents[])
     */
    public MrnWatcurrents[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnWatcurrents.WATPHY_CODE,MrnWatcurrents.CURRENT_DIR;
     * String where = MrnWatcurrents.WATPHY_CODE + "=<val1";
     * String order = MrnWatcurrents.WATPHY_CODE;
     * MrnWatcurrents watcurrentsArray[] =
     *     new MrnWatcurrents().get(columns, where, order);</pre>
     * will get the watphyCode and currentDir colums of all MrnWatcurrents records,
     * where watphyCode = <val1>, and order them by watphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatcurrents (MrnWatcurrents[])
     */
    public MrnWatcurrents[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnWatcurrents.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnWatcurrents[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int watphyCodeCol   = db.getColNumber(WATPHY_CODE);
        int currentDirCol   = db.getColNumber(CURRENT_DIR);
        int currentSpeedCol = db.getColNumber(CURRENT_SPEED);
        MrnWatcurrents[] cArray = new MrnWatcurrents[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnWatcurrents();
            if (watphyCodeCol != -1)
                cArray[i].setWatphyCode  ((String) row.elementAt(watphyCodeCol));
            if (currentDirCol != -1)
                cArray[i].setCurrentDir  ((String) row.elementAt(currentDirCol));
            if (currentSpeedCol != -1)
                cArray[i].setCurrentSpeed((String) row.elementAt(currentSpeedCol));
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
     *     new MrnWatcurrents(
     *         <val1>,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     watphyCode   = <val1>,
     *     currentDir   = <val2>,
     *     currentSpeed = <val3>.
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
     * Boolean success = new MrnWatcurrents(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where currentDir = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatcurrents watcurrents = new MrnWatcurrents();
     * success = watcurrents.del(MrnWatcurrents.WATPHY_CODE+"=<val1>");</pre>
     * will delete all records where watphyCode = <val1>.
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
     * update are taken from the MrnWatcurrents argument, .e.g.<pre>
     * Boolean success
     * MrnWatcurrents updMrnWatcurrents = new MrnWatcurrents();
     * updMrnWatcurrents.setCurrentDir(<val2>);
     * MrnWatcurrents whereMrnWatcurrents = new MrnWatcurrents(<val1>);
     * success = whereMrnWatcurrents.upd(updMrnWatcurrents);</pre>
     * will update CurrentDir to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watcurrents  A MrnWatcurrents variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatcurrents watcurrents) {
        return db.update (TABLE, createColVals(watcurrents), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatcurrents updMrnWatcurrents = new MrnWatcurrents();
     * updMrnWatcurrents.setCurrentDir(<val2>);
     * MrnWatcurrents whereMrnWatcurrents = new MrnWatcurrents();
     * success = whereMrnWatcurrents.upd(
     *     updMrnWatcurrents, MrnWatcurrents.WATPHY_CODE+"=<val1>");</pre>
     * will update CurrentDir to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watcurrents  A MrnWatcurrents variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatcurrents watcurrents, String where) {
        return db.update (TABLE, createColVals(watcurrents), where);
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
        if (getWatphyCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPHY_CODE + "=" + getWatphyCode("");
        } // if getWatphyCode
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
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnWatcurrents aVar) {
        String colVals = "";
        if (aVar.getWatphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPHY_CODE +"=";
            colVals += (aVar.getWatphyCode() == INTNULL2 ?
                "null" : aVar.getWatphyCode(""));
        } // if aVar.getWatphyCode
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
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = WATPHY_CODE;
        if (getCurrentDir() != INTNULL) {
            columns = columns + "," + CURRENT_DIR;
        } // if getCurrentDir
        if (getCurrentSpeed() != FLOATNULL) {
            columns = columns + "," + CURRENT_SPEED;
        } // if getCurrentSpeed
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getWatphyCode("");
        if (getCurrentDir() != INTNULL) {
            values  = values  + "," + getCurrentDir("");
        } // if getCurrentDir
        if (getCurrentSpeed() != FLOATNULL) {
            values  = values  + "," + getCurrentSpeed("");
        } // if getCurrentSpeed
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getWatphyCode("")   + "|" +
            getCurrentDir("")   + "|" +
            getCurrentSpeed("") + "|";
    } // method toString

} // class MrnWatcurrents
