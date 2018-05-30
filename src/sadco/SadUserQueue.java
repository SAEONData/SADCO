package sadco;

import java.sql.Timestamp;
import java.util.Vector;

/**
 * This class manages the SAD_USER_QUEUE table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 091222 - SIT Group
 * @version
 * 091222 - GenTableClassB class - create class<br>
 */
public class SadUserQueue extends Tables {

    boolean dbg = false;
    //boolean dbg = true;
    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SAD_USER_QUEUE";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The userid field name */
    public static final String USERID = "USERID";
    /** The extraction field name */
    public static final String EXTRACTION = "EXTRACTION";
    /** The dateTime field name */
    public static final String DATE_TIME = "DATE_TIME";
    /** The arguments field name */
    public static final String ARGUMENTS = "ARGUMENTS";
    /** The dateTime2 field name */
    public static final String DATE_TIME2 = "DATE_TIME2";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    userid;
    private String    extraction;
    private Timestamp dateTime;
    private String    arguments;
    private Timestamp dateTime2;

    /** The error variables  */
    private int codeError       = ERROR_NORMAL;
    private int useridError     = ERROR_NORMAL;
    private int extractionError = ERROR_NORMAL;
    private int dateTimeError   = ERROR_NORMAL;
    private int argumentsError  = ERROR_NORMAL;
    private int dateTime2Error  = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage       = "";
    private String useridErrorMessage     = "";
    private String extractionErrorMessage = "";
    private String dateTimeErrorMessage   = "";
    private String argumentsErrorMessage  = "";
    private String dateTime2ErrorMessage  = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN       = INTMIN;
    public static final int       CODE_MX       = INTMAX;
    public static final Timestamp DATE_TIME_MN  = DATEMIN;
    public static final Timestamp DATE_TIME_MX  = DATEMAX;
    public static final Timestamp DATE_TIME2_MN = DATEMIN;
    public static final Timestamp DATE_TIME2_MX = DATEMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception dateTimeException =
        new Exception ("'dateTime' is null");
    Exception dateTimeOutOfBoundsException =
        new Exception ("'dateTime' out of bounds: " +
            DATE_TIME_MN + " - " + DATE_TIME_MX);
    Exception dateTime2Exception =
        new Exception ("'dateTime2' is null");
    Exception dateTime2OutOfBoundsException =
        new Exception ("'dateTime2' out of bounds: " +
            DATE_TIME2_MN + " - " + DATE_TIME2_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public SadUserQueue() {
        clearVars();
        if (dbg) System.out.println ("<br>in SadUserQueue constructor 1"); // debug
    } // SadUserQueue constructor

    /**
     * Instantiate a SadUserQueue object and initialize the instance variables.
     * @param code  The code (int)
     */
    public SadUserQueue(
            int code) {
        this();
        setCode       (code      );
        if (dbg) System.out.println ("<br>in SadUserQueue constructor 2"); // debug
    } // SadUserQueue constructor

    /**
     * Instantiate a SadUserQueue object and initialize the instance variables.
     * @param code        The code       (int)
     * @param userid      The userid     (String)
     * @param extraction  The extraction (String)
     * @param dateTime    The dateTime   (java.util.Date)
     * @param arguments   The arguments  (String)
     * @param dateTime2   The dateTime2  (java.util.Date)
     * @return A SadUserQueue object
     */
    public SadUserQueue(
            int    code,
            String userid,
            String extraction,
            java.util.Date dateTime,
            String arguments,
            java.util.Date dateTime2) {
        this();
        setCode       (code      );
        setUserid     (userid    );
        setExtraction (extraction);
        setDateTime   (dateTime  );
        setExtraction (arguments );
        setDateTime2  (dateTime2 );
        if (dbg) System.out.println ("<br>in SadUserQueue constructor 3"); // debug
    } // SadUserQueue constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode       (INTNULL );
        setUserid     (CHARNULL);
        setExtraction (CHARNULL);
        setDateTime   (DATENULL);
        setArguments  (CHARNULL);
        setDateTime2  (DATENULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'code' class variable
     * @param  code (int)
     */
    public int setCode(int code) {
        try {
            if ( ((code == INTNULL) ||
                  (code == INTNULL2)) ||
                !((code < CODE_MN) ||
                  (code > CODE_MX)) ) {
                this.code = code;
                codeError = ERROR_NORMAL;
            } else {
                throw codeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (Integer)
     */
    public int setCode(Integer code) {
        try {
            setCode(code.intValue());
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (Float)
     */
    public int setCode(Float code) {
        try {
            if (code.floatValue() == FLOATNULL) {
                setCode(INTNULL);
            } else {
                setCode(code.intValue());
            } // if (code.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (String)
     */
    public int setCode(String code) {
        try {
            setCode(new Integer(code).intValue());
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Called when an exception has occured
     * @param  code (String)
     */
    private void setCodeError (int code, Exception e, int error) {
        this.code = code;
        codeErrorMessage = e.toString();
        codeError = error;
    } // method setCodeError


    /**
     * Set the 'userid' class variable
     * @param  userid (String)
     */
    public int setUserid(String userid) {
        try {
            this.userid = userid;
            if (this.userid != CHARNULL) {
                this.userid = stripCRLF(this.userid.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>userid = " + this.userid);
        } catch (Exception e) {
            setUseridError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return useridError;
    } // method setUserid

    /**
     * Called when an exception has occured
     * @param  userid (String)
     */
    private void setUseridError (String userid, Exception e, int error) {
        this.userid = userid;
        useridErrorMessage = e.toString();
        useridError = error;
    } // method setUseridError


    /**
     * Set the 'extraction' class variable
     * @param  extraction (String)
     */
    public int setExtraction(String extraction) {
        try {
            this.extraction = extraction;
            if (this.extraction != CHARNULL) {
                this.extraction = stripCRLF(this.extraction.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>extraction = " + this.extraction);
        } catch (Exception e) {
            setExtractionError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return extractionError;
    } // method setExtraction

    /**
     * Called when an exception has occured
     * @param  extraction (String)
     */
    private void setExtractionError (String extraction, Exception e, int error) {
        this.extraction = extraction;
        extractionErrorMessage = e.toString();
        extractionError = error;
    } // method setExtractionError


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
            if (dbg) System.out.println ("dateTime = " + dateTime);
            setDateTime(Timestamp.valueOf(dateTime));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
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
     * Set the 'arguments' class variable
     * @param  arguments (String)
     */
    public int setArguments(String arguments) {
        try {
            this.arguments = arguments;
            if (this.arguments != CHARNULL) {
                this.arguments = stripCRLF(this.arguments.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>arguments = " + this.arguments);
        } catch (Exception e) {
            setArgumentsError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return argumentsError;
    } // method setArguments

    /**
     * Called when an exception has occured
     * @param  arguments (String)
     */
    private void setArgumentsError (String arguments, Exception e, int error) {
        this.arguments = arguments;
        argumentsErrorMessage = e.toString();
        argumentsError = error;
    } // method setArgumentsError


    /**
     * Set the 'dateTime2' class variable
     * @param  dateTime2 (Timestamp)
     */
    public int setDateTime2(Timestamp dateTime2) {
        try {
            if (dateTime2 == null) { this.dateTime2 = DATENULL; }
            if ( (dateTime2.equals(DATENULL) ||
                  dateTime2.equals(DATENULL2)) ||
                !(dateTime2.before(DATE_TIME_MN) ||
                  dateTime2.after(DATE_TIME_MX)) ) {
                this.dateTime2 = dateTime2;
                dateTime2Error = ERROR_NORMAL;
            } else {
                throw dateTime2OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateTime2Error(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateTime2Error;
    } // method setDateTime2

    /**
     * Set the 'dateTime2' class variable
     * @param  dateTime2 (java.util.Date)
     */
    public int setDateTime2(java.util.Date dateTime2) {
        try {
            setDateTime2(new Timestamp(dateTime2.getTime()));
        } catch (Exception e) {
            setDateTime2Error(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTime2Error;
    } // method setDateTime2

    /**
     * Set the 'dateTime2' class variable
     * @param  dateTime2 (String)
     */
    public int setDateTime2(String dateTime2) {
        try {
            int len = dateTime2.length();
            switch (len) {
            // date &/or times
                case 4: dateTime2 += "-01";
                case 7: dateTime2 += "-01";
                case 10: dateTime2 += " 00";
                case 13: dateTime2 += ":00";
                case 16: dateTime2 += ":00"; break;
                // times only
                case 5: dateTime2 = "1800-01-01 " + dateTime2 + ":00"; break;
                case 8: dateTime2 = "1800-01-01 " + dateTime2; break;
            } // switch
            if (dbg) System.out.println ("dateTime2 = " + dateTime2);
            setDateTime2(Timestamp.valueOf(dateTime2));
        } catch (Exception e) {
            setDateTime2Error(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTime2Error;
    } // method setDateTime2

    /**
     * Called when an exception has occured
     * @param  dateTime2 (String)
     */
    private void setDateTime2Error (Timestamp dateTime2, Exception e, int error) {
        this.dateTime2 = dateTime2;
        dateTime2ErrorMessage = e.toString();
        dateTime2Error = error;
    } // method setDateTime2Error


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'code' class variable
     * @return code (int)
     */
    public int getCode() {
        return code;
    } // method getCode

    /**
     * Return the 'code' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCode methods
     * @return code (String)
     */
    public String getCode(String s) {
        return ((code != INTNULL) ? new Integer(code).toString() : "");
    } // method getCode


    /**
     * Return the 'userid' class variable
     * @return userid (String)
     */
    public String getUserid() {
        return userid;
    } // method getUserid

    /**
     * Return the 'userid' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getUserid methods
     * @return userid (String)
     */
    public String getUserid(String s) {
        return (userid != CHARNULL ? userid.replace('"','\'') : "");
    } // method getUserid


    /**
     * Return the 'extraction' class variable
     * @return extraction (String)
     */
    public String getExtraction() {
        return extraction;
    } // method getExtraction

    /**
     * Return the 'extraction' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getExtraction methods
     * @return extraction (String)
     */
    public String getExtraction(String s) {
        return (extraction != CHARNULL ? extraction.replace('"','\'') : "");
    } // method getExtraction


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
     * Return the 'arguments' class variable
     * @return arguments (String)
     */
    public String getArguments() {
        return arguments;
    } // method getArguments

    /**
     * Return the 'arguments' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getArguments methods
     * @return arguments (String)
     */
    public String getArguments(String s) {
        return (arguments != CHARNULL ? arguments.replace('"','\'') : "");
    } // method getArguments


    /**
     * Return the 'dateTime2' class variable
     * @return dateTime2 (Timestamp)
     */
    public Timestamp getDateTime2() {
        return dateTime2;
    } // method getDateTime2

    /**
     * Return the 'dateTime2' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateTime2 methods
     * @return dateTime2 (String)
     */
    public String getDateTime2(String s) {
        if (dateTime2.equals(DATENULL)) {
            return ("");
        } else {
            String dateTime2Str = dateTime2.toString();
            return dateTime2Str.substring(0,dateTime2Str.indexOf('.'));
        } // if
    } // method getDateTime2


    /**
     * Gets the maximum value for the code from the table
     * @return maximum code value (int)
     */
    public int getMaxCode() {
        Vector result = db.select ("max(" + CODE + ")",TABLE,"1=1");
        int maxCode = 0;
        Vector row = (Vector) result.elementAt(0);
        if (row.firstElement() != null){
            maxCode = new Integer((String) row.elementAt(0)).intValue();
        } // if (row.firstElement() != null)
        return maxCode;
    } // method getMaxCode


    /**
     * Gets the maximum value for the code from the table
     * @return maximum code value (int)
     */
    public int getMinCode() {
        Vector result = db.select ("min(" + CODE + ")",TABLE,"1=1");
        int minCode = 0;
        Vector row = (Vector) result.elementAt(0);
        if (row.firstElement() != null){
            minCode = new Integer((String) row.elementAt(0)).intValue();
        } // if (row.firstElement() != null)
        return minCode;
    } // method getMaxCode


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
        if ((code == INTNULL) &&
            (userid == CHARNULL) &&
            (extraction == CHARNULL) &&
            (dateTime == DATENULL) &&
            (arguments == CHARNULL) &&
            (dateTime2 == DATENULL)) {
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
        int sumError = codeError +
            useridError +
            extractionError +
            dateTimeError +
            argumentsError +
            dateTime2Error;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the code instance variable
     * @return errorcode (int)
     */
    public int getCodeError() {
        return codeError;
    } // method getCodeError

    /**
     * Gets the errorMessage for the code instance variable
     * @return errorMessage (String)
     */
    public String getCodeErrorMessage() {
        return codeErrorMessage;
    } // method getCodeErrorMessage


    /**
     * Gets the errorcode for the userid instance variable
     * @return errorcode (int)
     */
    public int getUseridError() {
        return useridError;
    } // method getUseridError

    /**
     * Gets the errorMessage for the userid instance variable
     * @return errorMessage (String)
     */
    public String getUseridErrorMessage() {
        return useridErrorMessage;
    } // method getUseridErrorMessage


    /**
     * Gets the errorcode for the extraction instance variable
     * @return errorcode (int)
     */
    public int getExtractionError() {
        return extractionError;
    } // method getExtractionError

    /**
     * Gets the errorMessage for the extraction instance variable
     * @return errorMessage (String)
     */
    public String getExtractionErrorMessage() {
        return extractionErrorMessage;
    } // method getExtractionErrorMessage

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
     * Gets the errorcode for the arguments instance variable
     * @return errorcode (int)
     */
    public int getArgumentsError() {
        return argumentsError;
    } // method getArgumentsError

    /**
     * Gets the errorMessage for the arguments instance variable
     * @return errorMessage (String)
     */
    public String getArgumentsErrorMessage() {
        return argumentsErrorMessage;
    } // method getArgumentsErrorMessage

    /**
     * Gets the errorcode for the dateTime2 instance variable
     * @return errorcode (int)
     */
    public int getDateTime2Error() {
        return dateTime2Error;
    } // method getDateTime2Error

    /**
     * Gets the errorMessage for the dateTime2 instance variable
     * @return errorMessage (String)
     */
    public String getDateTime2ErrorMessage() {
        return dateTime2ErrorMessage;
    } // method getDateTime2ErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of SadUserQueue. e.g.<pre>
     * SadUserQueue sadUserQueue = new SadUserQueue(<val1>);
     * SadUserQueue sadUserQueueArray[] = sadUserQueue.get();</pre>
     * will get the SadUserQueue record where code = <val1>.
     * @return Array of SadUserQueue (SadUserQueue[])
     */
    public SadUserQueue[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * SadUserQueue sadUserQueueArray[] =
     *     new SadUserQueue().get(SadUserQueue.CODE+"=<val1>");</pre>
     * will get the SadUserQueue record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of SadUserQueue (SadUserQueue[])
     */
    public SadUserQueue[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * SadUserQueue sadUserQueueArray[] =
     *     new SadUserQueue().get("1=1",sadUserQueue.CODE);</pre>
     * will get the all the SadUserQueue records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of SadUserQueue (SadUserQueue[])
     */
    public SadUserQueue[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = SadUserQueue.CODE,SadUserQueue.USERID;
     * String where = SadUserQueue.CODE + "=<val1";
     * String order = SadUserQueue.CODE;
     * SadUserQueue sadUserQueueArray[] =
     *     new SadUserQueue().get(columns, where, order);</pre>
     * will get the code and userid colums of all SadUserQueue records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of SadUserQueue (SadUserQueue[])
     */
    public SadUserQueue[] get (String fields, String where, String order) {
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
     * and transforms it into an array of SadUserQueue.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private SadUserQueue[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol       = db.getColNumber(CODE);
        int useridCol     = db.getColNumber(USERID);
        int extractionCol = db.getColNumber(EXTRACTION);
        int dateTimeCol   = db.getColNumber(DATE_TIME);
        int argumentsCol  = db.getColNumber(ARGUMENTS);
        int dateTime2Col  = db.getColNumber(DATE_TIME2);
        SadUserQueue[] cArray = new SadUserQueue[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new SadUserQueue();
            if (codeCol != -1)
                cArray[i].setCode      ((String) row.elementAt(codeCol));
            if (useridCol != -1)
                cArray[i].setUserid    ((String) row.elementAt(useridCol));
            if (extractionCol != -1)
                cArray[i].setExtraction((String) row.elementAt(extractionCol));
            if (dateTimeCol != -1)
                cArray[i].setDateTime((String) row.elementAt(dateTimeCol));
            if (argumentsCol != -1)
                cArray[i].setArguments((String) row.elementAt(argumentsCol));
            if (dateTime2Col != -1)
                cArray[i].setDateTime2((String) row.elementAt(dateTime2Col));
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
     *     new SadUserQueue(
     *         INTNULL,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     userid     = <val2>,
     *     extraction = <val3>.
     * @return success = true/false (boolean)
     */
    public boolean put() {
        return db.insert (TABLE, createColumns(), createValues());
    } // method put


    /**
     * Insert a record into the table.  The table is locked first,
     * and afterwards committed.  The values are taken from the current
     * value of the instance variables. .e.g.<pre>
     * String    CHARNULL  = Tables.CHARNULL;
     * Timestamp DATENULL  = Tables.DATENULL;
     * int       INTNULL   = Tables.INTNULL;
     * float     FLOATNULL = Tables.FLOATNULL;
     * Boolean success =
     *     new SadUserQueue(
     *         INTNULL,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     userid     = <val2>,
     *     extraction = <val3>.
     * @return success = true/false (boolean)
     */
    public boolean putLock() {
        //System.out.println("<br>SadUserQueue.putLock: start");
        db.lock(TABLE);
        //System.out.println("<br>SadUserQueue.putLock: after db.lock");
        //System.out.println("<br>SadUserQueue.putLock: createColumns() = " + createColumns());
        //System.out.println("<br>SadUserQueue.putLock: createValues() = " + createValues());
        boolean success = db.insert (TABLE, createColumns(), createValues());
        //System.out.println("<br>SadUserQueue.putLock: after insert: " + success);
        db.commit();
        //System.out.println("<br>SadUserQueue.putLock: after commit");
        return success;
    } // method putLock


    //=====================//
    // All the del methods //
    //=====================//

    /**
     * Delete record(s) from the table, The where clause is created from the
     * current values of the instance variables. .e.g.<pre>
     * Boolean success = new SadUserQueue(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where userid = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        //return db.delete (TABLE, createWhere());
        boolean success = db.delete (TABLE, createWhere());
        db.commit();
        return success;
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * SadUserQueue sadUserQueue = new SadUserQueue();
     * success = sadUserQueue.del(SadUserQueue.CODE+"=<val1>");</pre>
     * will delete all records where code = <val1>.
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
     * update are taken from the SadUserQueue argument, .e.g.<pre>
     * Boolean success
     * SadUserQueue updSadUserQueue = new SadUserQueue();
     * updSadUserQueue.setUserid(<val2>);
     * SadUserQueue whereSadUserQueue = new SadUserQueue(<val1>);
     * success = whereSadUserQueue.upd(updSadUserQueue);</pre>
     * will update Userid to <val2> for all records where
     * code = <val1>.
     * @param sadUserQueue  A SadUserQueue variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(SadUserQueue sadUserQueue) {
        //return db.update (TABLE, createColVals(sadUserQueue), createWhere());
        boolean success = db.update (TABLE, createColVals(sadUserQueue), createWhere());
        db.commit();
        return success;
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * SadUserQueue updSadUserQueue = new SadUserQueue();
     * updSadUserQueue.setUserid(<val2>);
     * SadUserQueue whereSadUserQueue = new SadUserQueue();
     * success = whereSadUserQueue.upd(
     *     updSadUserQueue, SadUserQueue.CODE+"=<val1>");</pre>
     * will update Userid to <val2> for all records where
     * code = <val1>.
     * @param  updSadUserQueue  A SadUserQueue variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(SadUserQueue sadUserQueue, String where) {
        //return db.update (TABLE, createColVals(sadUserQueue), where);
        boolean success = db.update (TABLE, createColVals(sadUserQueue), where);
        db.commit();
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
    private String createWhere() {
        String where = "";
        if (getCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CODE + "=" + getCode("");
        } // if getCode
        if (getUserid() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + USERID + "='" + getUserid() + "'";
        } // if getUserid
        if (getExtraction() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + EXTRACTION + "='" + getExtraction() + "'";
        } // if getExtraction
        if (!getDateTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME +
                "=" + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getArguments() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ARGUMENTS + "='" + getArguments() + "'";
        } // if getArguments
        if (!getDateTime2().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME2 +
                "=" + Tables.getDateFormat(getDateTime2());
        } // if getDateTime
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(SadUserQueue aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getUserid() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += USERID +"=";
            colVals += (aVar.getUserid().equals(CHARNULL2) ?
                "null" : "'" + aVar.getUserid() + "'");
        } // if aVar.getUserid
        if (aVar.getExtraction() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += EXTRACTION +"=";
            colVals += (aVar.getExtraction().equals(CHARNULL2) ?
                "null" : "'" + aVar.getExtraction() + "'");
        } // if aVar.getExtraction
        if (!aVar.getDateTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME +"=";
            colVals += (aVar.getDateTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTime()));
        } // if aVar.getDateTime
        if (aVar.getArguments() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ARGUMENTS +"=";
            colVals += (aVar.getArguments().equals(CHARNULL2) ?
                "null" : "'" + aVar.getArguments() + "'");
        } // if aVar.getArguments
        if (!aVar.getDateTime2().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME2 +"=";
            colVals += (aVar.getDateTime2().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTime2()));
        } // if aVar.getDateTime2
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getUserid() != CHARNULL) {
            columns = columns + "," + USERID;
        } // if getUserid
        if (getExtraction() != CHARNULL) {
            columns = columns + "," + EXTRACTION;
        } // if getExtraction
        if (!getDateTime().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME;
        } // if getDateTime
        if (getArguments() != CHARNULL) {
            columns = columns + "," + ARGUMENTS;
        } // if getArguments
        if (!getDateTime2().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME2;
        } // if getDateTime2
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        //System.out.println ("<br>SadUserQueue.createValues: start");   // debug
        setCode(getMaxCode()+1);
        //System.out.println ("<br>SadUserQueue.createValues: code = " + getCode());   // debug
        String values  = getCode("");
        if (getUserid() != CHARNULL) {
            values  = values  + ",'" + getUserid() + "'";
        } // if getUserid
        if (getExtraction() != CHARNULL) {
            values  = values  + ",'" + getExtraction() + "'";
        } // if getExtraction
        if (!getDateTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getArguments() != CHARNULL) {
            values  = values  + ",'" + getArguments() + "'";
        } // if getArguments
        if (!getDateTime2().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTime2());
        } // if getDateTime2
        if (dbg)System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")       + "|" +
            getUserid("")     + "|" +
            getExtraction("") + "|" +
            getDateTime("")   + "|" +
            getArguments("")  + "|" +
            getDateTime2("")  + "|";
    } // method toString

} // class SadUserQueue
