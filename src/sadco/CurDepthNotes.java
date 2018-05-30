package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the CUR_DEPTH_NOTES table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class CurDepthNotes extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "CUR_DEPTH_NOTES";
    /** The depthCode field name */
    public static final String DEPTH_CODE = "DEPTH_CODE";
    /** The sequenceNumber field name */
    public static final String SEQUENCE_NUMBER = "SEQUENCE_NUMBER";
    /** The notes field name */
    public static final String NOTES = "NOTES";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       depthCode;
    private int       sequenceNumber;
    private String    notes;

    /** The error variables  */
    private int depthCodeError      = ERROR_NORMAL;
    private int sequenceNumberError = ERROR_NORMAL;
    private int notesError          = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String depthCodeErrorMessage      = "";
    private String sequenceNumberErrorMessage = "";
    private String notesErrorMessage          = "";

    /** The min-max constants for all numerics */
    public static final int       DEPTH_CODE_MN = INTMIN;
    public static final int       DEPTH_CODE_MX = INTMAX;
    public static final int       SEQUENCE_NUMBER_MN = INTMIN;
    public static final int       SEQUENCE_NUMBER_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception depthCodeOutOfBoundsException =
        new Exception ("'depthCode' out of bounds: " +
            DEPTH_CODE_MN + " - " + DEPTH_CODE_MX);
    Exception sequenceNumberOutOfBoundsException =
        new Exception ("'sequenceNumber' out of bounds: " +
            SEQUENCE_NUMBER_MN + " - " + SEQUENCE_NUMBER_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public CurDepthNotes() {
        clearVars();
        if (dbg) System.out.println ("<br>in CurDepthNotes constructor 1"); // debug
    } // CurDepthNotes constructor

    /**
     * Instantiate a CurDepthNotes object and initialize the instance variables.
     * @param depthCode  The depthCode (int)
     */
    public CurDepthNotes(
            int depthCode) {
        this();
        setDepthCode      (depthCode     );
        if (dbg) System.out.println ("<br>in CurDepthNotes constructor 2"); // debug
    } // CurDepthNotes constructor

    /**
     * Instantiate a CurDepthNotes object and initialize the instance variables.
     * @param depthCode       The depthCode      (int)
     * @param sequenceNumber  The sequenceNumber (int)
     * @param notes           The notes          (String)
     */
    public CurDepthNotes(
            int    depthCode,
            int    sequenceNumber,
            String notes) {
        this();
        setDepthCode      (depthCode     );
        setSequenceNumber (sequenceNumber);
        setNotes          (notes         );
        if (dbg) System.out.println ("<br>in CurDepthNotes constructor 3"); // debug
    } // CurDepthNotes constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setDepthCode      (INTNULL );
        setSequenceNumber (INTNULL );
        setNotes          (CHARNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (int)
     */
    public int setDepthCode(int depthCode) {
        try {
            if ( ((depthCode == INTNULL) ||
                  (depthCode == INTNULL2)) ||
                !((depthCode < DEPTH_CODE_MN) ||
                  (depthCode > DEPTH_CODE_MX)) ) {
                this.depthCode = depthCode;
                depthCodeError = ERROR_NORMAL;
            } else {
                throw depthCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (Integer)
     */
    public int setDepthCode(Integer depthCode) {
        try {
            setDepthCode(depthCode.intValue());
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (Float)
     */
    public int setDepthCode(Float depthCode) {
        try {
            if (depthCode.floatValue() == FLOATNULL) {
                setDepthCode(INTNULL);
            } else {
                setDepthCode(depthCode.intValue());
            } // if (depthCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (String)
     */
    public int setDepthCode(String depthCode) {
        try {
            setDepthCode(new Integer(depthCode).intValue());
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Called when an exception has occured
     * @param  depthCode (String)
     */
    private void setDepthCodeError (int depthCode, Exception e, int error) {
        this.depthCode = depthCode;
        depthCodeErrorMessage = e.toString();
        depthCodeError = error;
    } // method setDepthCodeError


    /**
     * Set the 'sequenceNumber' class variable
     * @param  sequenceNumber (int)
     */
    public int setSequenceNumber(int sequenceNumber) {
        try {
            if ( ((sequenceNumber == INTNULL) ||
                  (sequenceNumber == INTNULL2)) ||
                !((sequenceNumber < SEQUENCE_NUMBER_MN) ||
                  (sequenceNumber > SEQUENCE_NUMBER_MX)) ) {
                this.sequenceNumber = sequenceNumber;
                sequenceNumberError = ERROR_NORMAL;
            } else {
                throw sequenceNumberOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSequenceNumberError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sequenceNumberError;
    } // method setSequenceNumber

    /**
     * Set the 'sequenceNumber' class variable
     * @param  sequenceNumber (Integer)
     */
    public int setSequenceNumber(Integer sequenceNumber) {
        try {
            setSequenceNumber(sequenceNumber.intValue());
        } catch (Exception e) {
            setSequenceNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sequenceNumberError;
    } // method setSequenceNumber

    /**
     * Set the 'sequenceNumber' class variable
     * @param  sequenceNumber (Float)
     */
    public int setSequenceNumber(Float sequenceNumber) {
        try {
            if (sequenceNumber.floatValue() == FLOATNULL) {
                setSequenceNumber(INTNULL);
            } else {
                setSequenceNumber(sequenceNumber.intValue());
            } // if (sequenceNumber.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSequenceNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sequenceNumberError;
    } // method setSequenceNumber

    /**
     * Set the 'sequenceNumber' class variable
     * @param  sequenceNumber (String)
     */
    public int setSequenceNumber(String sequenceNumber) {
        try {
            setSequenceNumber(new Integer(sequenceNumber).intValue());
        } catch (Exception e) {
            setSequenceNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sequenceNumberError;
    } // method setSequenceNumber

    /**
     * Called when an exception has occured
     * @param  sequenceNumber (String)
     */
    private void setSequenceNumberError (int sequenceNumber, Exception e, int error) {
        this.sequenceNumber = sequenceNumber;
        sequenceNumberErrorMessage = e.toString();
        sequenceNumberError = error;
    } // method setSequenceNumberError


    /**
     * Set the 'notes' class variable
     * @param  notes (String)
     */
    public int setNotes(String notes) {
        try {
            this.notes = notes;
            if (this.notes != CHARNULL) {
                this.notes = stripCRLF(this.notes.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>notes = " + this.notes);
        } catch (Exception e) {
            setNotesError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return notesError;
    } // method setNotes

    /**
     * Called when an exception has occured
     * @param  notes (String)
     */
    private void setNotesError (String notes, Exception e, int error) {
        this.notes = notes;
        notesErrorMessage = e.toString();
        notesError = error;
    } // method setNotesError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'depthCode' class variable
     * @return depthCode (int)
     */
    public int getDepthCode() {
        return depthCode;
    } // method getDepthCode

    /**
     * Return the 'depthCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDepthCode methods
     * @return depthCode (String)
     */
    public String getDepthCode(String s) {
        return ((depthCode != INTNULL) ? new Integer(depthCode).toString() : "");
    } // method getDepthCode


    /**
     * Return the 'sequenceNumber' class variable
     * @return sequenceNumber (int)
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    } // method getSequenceNumber

    /**
     * Return the 'sequenceNumber' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSequenceNumber methods
     * @return sequenceNumber (String)
     */
    public String getSequenceNumber(String s) {
        return ((sequenceNumber != INTNULL) ? new Integer(sequenceNumber).toString() : "");
    } // method getSequenceNumber


    /**
     * Return the 'notes' class variable
     * @return notes (String)
     */
    public String getNotes() {
        return notes;
    } // method getNotes

    /**
     * Return the 'notes' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNotes methods
     * @return notes (String)
     */
    public String getNotes(String s) {
        return (notes != CHARNULL ? notes.replace('"','\'') : "");
    } // method getNotes


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
        if ((depthCode == INTNULL) &&
            (sequenceNumber == INTNULL) &&
            (notes == CHARNULL)) {
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
        int sumError = depthCodeError +
            sequenceNumberError +
            notesError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the depthCode instance variable
     * @return errorcode (int)
     */
    public int getDepthCodeError() {
        return depthCodeError;
    } // method getDepthCodeError

    /**
     * Gets the errorMessage for the depthCode instance variable
     * @return errorMessage (String)
     */
    public String getDepthCodeErrorMessage() {
        return depthCodeErrorMessage;
    } // method getDepthCodeErrorMessage


    /**
     * Gets the errorcode for the sequenceNumber instance variable
     * @return errorcode (int)
     */
    public int getSequenceNumberError() {
        return sequenceNumberError;
    } // method getSequenceNumberError

    /**
     * Gets the errorMessage for the sequenceNumber instance variable
     * @return errorMessage (String)
     */
    public String getSequenceNumberErrorMessage() {
        return sequenceNumberErrorMessage;
    } // method getSequenceNumberErrorMessage


    /**
     * Gets the errorcode for the notes instance variable
     * @return errorcode (int)
     */
    public int getNotesError() {
        return notesError;
    } // method getNotesError

    /**
     * Gets the errorMessage for the notes instance variable
     * @return errorMessage (String)
     */
    public String getNotesErrorMessage() {
        return notesErrorMessage;
    } // method getNotesErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of CurDepthNotes. e.g.<pre>
     * CurDepthNotes curDepthNotes = new CurDepthNotes(<val1>);
     * CurDepthNotes curDepthNotesArray[] = curDepthNotes.get();</pre>
     * will get the CurDepthNotes record where depthCode = <val1>.
     * @return Array of CurDepthNotes (CurDepthNotes[])
     */
    public CurDepthNotes[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * CurDepthNotes curDepthNotesArray[] =
     *     new CurDepthNotes().get(CurDepthNotes.DEPTH_CODE+"=<val1>");</pre>
     * will get the CurDepthNotes record where depthCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of CurDepthNotes (CurDepthNotes[])
     */
    public CurDepthNotes[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * CurDepthNotes curDepthNotesArray[] =
     *     new CurDepthNotes().get("1=1",curDepthNotes.DEPTH_CODE);</pre>
     * will get the all the CurDepthNotes records, and order them by depthCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurDepthNotes (CurDepthNotes[])
     */
    public CurDepthNotes[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = CurDepthNotes.DEPTH_CODE,CurDepthNotes.SEQUENCE_NUMBER;
     * String where = CurDepthNotes.DEPTH_CODE + "=<val1";
     * String order = CurDepthNotes.DEPTH_CODE;
     * CurDepthNotes curDepthNotesArray[] =
     *     new CurDepthNotes().get(columns, where, order);</pre>
     * will get the depthCode and sequenceNumber colums of all CurDepthNotes records,
     * where depthCode = <val1>, and order them by depthCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurDepthNotes (CurDepthNotes[])
     */
    public CurDepthNotes[] get (String fields, String where, String order) {
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
     * and transforms it into an array of CurDepthNotes.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private CurDepthNotes[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int depthCodeCol      = db.getColNumber(DEPTH_CODE);
        int sequenceNumberCol = db.getColNumber(SEQUENCE_NUMBER);
        int notesCol          = db.getColNumber(NOTES);
        CurDepthNotes[] cArray = new CurDepthNotes[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new CurDepthNotes();
            if (depthCodeCol != -1)
                cArray[i].setDepthCode     ((String) row.elementAt(depthCodeCol));
            if (sequenceNumberCol != -1)
                cArray[i].setSequenceNumber((String) row.elementAt(sequenceNumberCol));
            if (notesCol != -1)
                cArray[i].setNotes         ((String) row.elementAt(notesCol));
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
     *     new CurDepthNotes(
     *         <val1>,
     *         <val2>,
     *         <val3>).put();</pre>
     * will insert a record with:
     *     depthCode      = <val1>,
     *     sequenceNumber = <val2>,
     *     notes          = <val3>.
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
     * Boolean success = new CurDepthNotes(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where sequenceNumber = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurDepthNotes curDepthNotes = new CurDepthNotes();
     * success = curDepthNotes.del(CurDepthNotes.DEPTH_CODE+"=<val1>");</pre>
     * will delete all records where depthCode = <val1>.
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
     * update are taken from the CurDepthNotes argument, .e.g.<pre>
     * Boolean success
     * CurDepthNotes updCurDepthNotes = new CurDepthNotes();
     * updCurDepthNotes.setSequenceNumber(<val2>);
     * CurDepthNotes whereCurDepthNotes = new CurDepthNotes(<val1>);
     * success = whereCurDepthNotes.upd(updCurDepthNotes);</pre>
     * will update SequenceNumber to <val2> for all records where
     * depthCode = <val1>.
     * @param  curDepthNotes  A CurDepthNotes variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(CurDepthNotes curDepthNotes) {
        return db.update (TABLE, createColVals(curDepthNotes), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurDepthNotes updCurDepthNotes = new CurDepthNotes();
     * updCurDepthNotes.setSequenceNumber(<val2>);
     * CurDepthNotes whereCurDepthNotes = new CurDepthNotes();
     * success = whereCurDepthNotes.upd(
     *     updCurDepthNotes, CurDepthNotes.DEPTH_CODE+"=<val1>");</pre>
     * will update SequenceNumber to <val2> for all records where
     * depthCode = <val1>.
     * @param  curDepthNotes  A CurDepthNotes variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(CurDepthNotes curDepthNotes, String where) {
        return db.update (TABLE, createColVals(curDepthNotes), where);
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
        if (getDepthCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEPTH_CODE + "=" + getDepthCode("");
        } // if getDepthCode
        if (getSequenceNumber() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEQUENCE_NUMBER + "=" + getSequenceNumber("");
        } // if getSequenceNumber
        if (getNotes() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NOTES + "='" + getNotes() + "'";
        } // if getNotes
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(CurDepthNotes aVar) {
        String colVals = "";
        if (aVar.getDepthCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEPTH_CODE +"=";
            colVals += (aVar.getDepthCode() == INTNULL2 ?
                "null" : aVar.getDepthCode(""));
        } // if aVar.getDepthCode
        if (aVar.getSequenceNumber() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEQUENCE_NUMBER +"=";
            colVals += (aVar.getSequenceNumber() == INTNULL2 ?
                "null" : aVar.getSequenceNumber(""));
        } // if aVar.getSequenceNumber
        if (aVar.getNotes() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NOTES +"=";
            colVals += (aVar.getNotes().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNotes() + "'");
        } // if aVar.getNotes
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = DEPTH_CODE;
        if (getSequenceNumber() != INTNULL) {
            columns = columns + "," + SEQUENCE_NUMBER;
        } // if getSequenceNumber
        if (getNotes() != CHARNULL) {
            columns = columns + "," + NOTES;
        } // if getNotes
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getDepthCode("");
        if (getSequenceNumber() != INTNULL) {
            values  = values  + "," + getSequenceNumber("");
        } // if getSequenceNumber
        if (getNotes() != CHARNULL) {
            values  = values  + ",'" + getNotes() + "'";
        } // if getNotes
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getDepthCode("")      + "|" +
            getSequenceNumber("") + "|" +
            getNotes("")          + "|";
    } // method toString

} // class CurDepthNotes
