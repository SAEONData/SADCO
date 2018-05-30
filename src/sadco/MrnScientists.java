package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SCIENTISTS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 070112 - SIT Group
 * @version
 * 070112 - GenTableClassB class - create class<br>
 */
public class MrnScientists extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SCIENTISTS";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The surname field name */
    public static final String SURNAME = "SURNAME";
    /** The fName field name */
    public static final String F_NAME = "F_NAME";
    /** The title field name */
    public static final String TITLE = "TITLE";
    /** The institCode field name */
    public static final String INSTIT_CODE = "INSTIT_CODE";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    surname;
    private String    fName;
    private String    title;
    private int       institCode;

    /** The error variables  */
    private int codeError       = ERROR_NORMAL;
    private int surnameError    = ERROR_NORMAL;
    private int fNameError      = ERROR_NORMAL;
    private int titleError      = ERROR_NORMAL;
    private int institCodeError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage       = "";
    private String surnameErrorMessage    = "";
    private String fNameErrorMessage      = "";
    private String titleErrorMessage      = "";
    private String institCodeErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       INSTIT_CODE_MN = INTMIN;
    public static final int       INSTIT_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception institCodeOutOfBoundsException =
        new Exception ("'institCode' out of bounds: " +
            INSTIT_CODE_MN + " - " + INSTIT_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnScientists() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnScientists constructor 1"); // debug
    } // MrnScientists constructor

    /**
     * Instantiate a MrnScientists object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MrnScientists(
            int code) {
        this();
        setCode       (code      );
        if (dbg) System.out.println ("<br>in MrnScientists constructor 2"); // debug
    } // MrnScientists constructor

    /**
     * Instantiate a MrnScientists object and initialize the instance variables.
     * @param code        The code       (int)
     * @param surname     The surname    (String)
     * @param fName       The fName      (String)
     * @param title       The title      (String)
     * @param institCode  The institCode (int)
     * @return A MrnScientists object
     */
    public MrnScientists(
            int    code,
            String surname,
            String fName,
            String title,
            int    institCode) {
        this();
        setCode       (code      );
        setSurname    (surname   );
        setFName      (fName     );
        setTitle      (title     );
        setInstitCode (institCode);
        if (dbg) System.out.println ("<br>in MrnScientists constructor 3"); // debug
    } // MrnScientists constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode       (INTNULL );
        setSurname    (CHARNULL);
        setFName      (CHARNULL);
        setTitle      (CHARNULL);
        setInstitCode (INTNULL );
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
     * Set the 'surname' class variable
     * @param  surname (String)
     */
    public int setSurname(String surname) {
        try {
            this.surname = surname;
            if (this.surname != CHARNULL) {
                this.surname = stripCRLF(this.surname.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>surname = " + this.surname);
        } catch (Exception e) {
            setSurnameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return surnameError;
    } // method setSurname

    /**
     * Called when an exception has occured
     * @param  surname (String)
     */
    private void setSurnameError (String surname, Exception e, int error) {
        this.surname = surname;
        surnameErrorMessage = e.toString();
        surnameError = error;
    } // method setSurnameError


    /**
     * Set the 'fName' class variable
     * @param  fName (String)
     */
    public int setFName(String fName) {
        try {
            this.fName = fName;
            if (this.fName != CHARNULL) {
                this.fName = stripCRLF(this.fName.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>fName = " + this.fName);
        } catch (Exception e) {
            setFNameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return fNameError;
    } // method setFName

    /**
     * Called when an exception has occured
     * @param  fName (String)
     */
    private void setFNameError (String fName, Exception e, int error) {
        this.fName = fName;
        fNameErrorMessage = e.toString();
        fNameError = error;
    } // method setFNameError


    /**
     * Set the 'title' class variable
     * @param  title (String)
     */
    public int setTitle(String title) {
        try {
            this.title = title;
            if (this.title != CHARNULL) {
                this.title = stripCRLF(this.title.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>title = " + this.title);
        } catch (Exception e) {
            setTitleError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return titleError;
    } // method setTitle

    /**
     * Called when an exception has occured
     * @param  title (String)
     */
    private void setTitleError (String title, Exception e, int error) {
        this.title = title;
        titleErrorMessage = e.toString();
        titleError = error;
    } // method setTitleError


    /**
     * Set the 'institCode' class variable
     * @param  institCode (int)
     */
    public int setInstitCode(int institCode) {
        try {
            if ( ((institCode == INTNULL) || 
                  (institCode == INTNULL2)) ||
                !((institCode < INSTIT_CODE_MN) ||
                  (institCode > INSTIT_CODE_MX)) ) {
                this.institCode = institCode;
                institCodeError = ERROR_NORMAL;
            } else {
                throw institCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setInstitCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return institCodeError;
    } // method setInstitCode

    /**
     * Set the 'institCode' class variable
     * @param  institCode (Integer)
     */
    public int setInstitCode(Integer institCode) {
        try {
            setInstitCode(institCode.intValue());
        } catch (Exception e) {
            setInstitCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return institCodeError;
    } // method setInstitCode

    /**
     * Set the 'institCode' class variable
     * @param  institCode (Float)
     */
    public int setInstitCode(Float institCode) {
        try {
            if (institCode.floatValue() == FLOATNULL) {
                setInstitCode(INTNULL);
            } else {
                setInstitCode(institCode.intValue());
            } // if (institCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setInstitCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return institCodeError;
    } // method setInstitCode

    /**
     * Set the 'institCode' class variable
     * @param  institCode (String)
     */
    public int setInstitCode(String institCode) {
        try {
            setInstitCode(new Integer(institCode).intValue());
        } catch (Exception e) {
            setInstitCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return institCodeError;
    } // method setInstitCode

    /**
     * Called when an exception has occured
     * @param  institCode (String)
     */
    private void setInstitCodeError (int institCode, Exception e, int error) {
        this.institCode = institCode;
        institCodeErrorMessage = e.toString();
        institCodeError = error;
    } // method setInstitCodeError


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
     * Return the 'surname' class variable
     * @return surname (String)
     */
    public String getSurname() {
        return surname;
    } // method getSurname

    /**
     * Return the 'surname' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurname methods
     * @return surname (String)
     */
    public String getSurname(String s) {
        return (surname != CHARNULL ? surname.replace('"','\'') : "");
    } // method getSurname


    /**
     * Return the 'fName' class variable
     * @return fName (String)
     */
    public String getFName() {
        return fName;
    } // method getFName

    /**
     * Return the 'fName' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFName methods
     * @return fName (String)
     */
    public String getFName(String s) {
        return (fName != CHARNULL ? fName.replace('"','\'') : "");
    } // method getFName


    /**
     * Return the 'title' class variable
     * @return title (String)
     */
    public String getTitle() {
        return title;
    } // method getTitle

    /**
     * Return the 'title' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTitle methods
     * @return title (String)
     */
    public String getTitle(String s) {
        return (title != CHARNULL ? title.replace('"','\'') : "");
    } // method getTitle


    /**
     * Return the 'institCode' class variable
     * @return institCode (int)
     */
    public int getInstitCode() {
        return institCode;
    } // method getInstitCode

    /**
     * Return the 'institCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getInstitCode methods
     * @return institCode (String)
     */
    public String getInstitCode(String s) {
        return ((institCode != INTNULL) ? new Integer(institCode).toString() : "");
    } // method getInstitCode


    /**
     * Gets the maximum value for the code from the table
     * @return maximum code value (int)
     */
    public int getMaxCode() {
        Vector result = db.select ("max(" + CODE + ")",TABLE,"1=1");
        Vector row = (Vector) result.elementAt(0);
        return (new Integer((String) row.elementAt(0)).intValue());
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
            (surname == CHARNULL) &&
            (fName == CHARNULL) &&
            (title == CHARNULL) &&
            (institCode == INTNULL)) {
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
            surnameError +
            fNameError +
            titleError +
            institCodeError;
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
     * Gets the errorcode for the surname instance variable
     * @return errorcode (int)
     */
    public int getSurnameError() {
        return surnameError;
    } // method getSurnameError

    /**
     * Gets the errorMessage for the surname instance variable
     * @return errorMessage (String)
     */
    public String getSurnameErrorMessage() {
        return surnameErrorMessage;
    } // method getSurnameErrorMessage


    /**
     * Gets the errorcode for the fName instance variable
     * @return errorcode (int)
     */
    public int getFNameError() {
        return fNameError;
    } // method getFNameError

    /**
     * Gets the errorMessage for the fName instance variable
     * @return errorMessage (String)
     */
    public String getFNameErrorMessage() {
        return fNameErrorMessage;
    } // method getFNameErrorMessage


    /**
     * Gets the errorcode for the title instance variable
     * @return errorcode (int)
     */
    public int getTitleError() {
        return titleError;
    } // method getTitleError

    /**
     * Gets the errorMessage for the title instance variable
     * @return errorMessage (String)
     */
    public String getTitleErrorMessage() {
        return titleErrorMessage;
    } // method getTitleErrorMessage


    /**
     * Gets the errorcode for the institCode instance variable
     * @return errorcode (int)
     */
    public int getInstitCodeError() {
        return institCodeError;
    } // method getInstitCodeError

    /**
     * Gets the errorMessage for the institCode instance variable
     * @return errorMessage (String)
     */
    public String getInstitCodeErrorMessage() {
        return institCodeErrorMessage;
    } // method getInstitCodeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnScientists. e.g.<pre>
     * MrnScientists scientists = new MrnScientists(<val1>);
     * MrnScientists scientistsArray[] = scientists.get();</pre>
     * will get the MrnScientists record where code = <val1>.
     * @return Array of MrnScientists (MrnScientists[])
     */
    public MrnScientists[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnScientists scientistsArray[] = 
     *     new MrnScientists().get(MrnScientists.CODE+"=<val1>");</pre>
     * will get the MrnScientists record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnScientists (MrnScientists[])
     */
    public MrnScientists[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnScientists scientistsArray[] = 
     *     new MrnScientists().get("1=1",scientists.CODE);</pre>
     * will get the all the MrnScientists records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnScientists (MrnScientists[])
     */
    public MrnScientists[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnScientists.CODE,MrnScientists.SURNAME;
     * String where = MrnScientists.CODE + "=<val1";
     * String order = MrnScientists.CODE;
     * MrnScientists scientistsArray[] = 
     *     new MrnScientists().get(columns, where, order);</pre>
     * will get the code and surname colums of all MrnScientists records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnScientists (MrnScientists[])
     */
    public MrnScientists[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnScientists.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnScientists[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol       = db.getColNumber(CODE);
        int surnameCol    = db.getColNumber(SURNAME);
        int fNameCol      = db.getColNumber(F_NAME);
        int titleCol      = db.getColNumber(TITLE);
        int institCodeCol = db.getColNumber(INSTIT_CODE);
        MrnScientists[] cArray = new MrnScientists[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnScientists();
            if (codeCol != -1)
                cArray[i].setCode      ((String) row.elementAt(codeCol));
            if (surnameCol != -1)
                cArray[i].setSurname   ((String) row.elementAt(surnameCol));
            if (fNameCol != -1)
                cArray[i].setFName     ((String) row.elementAt(fNameCol));
            if (titleCol != -1)
                cArray[i].setTitle     ((String) row.elementAt(titleCol));
            if (institCodeCol != -1)
                cArray[i].setInstitCode((String) row.elementAt(institCodeCol));
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
     *     new MrnScientists(
     *         INTNULL,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     surname    = <val2>,
     *     fName      = <val3>,
     *     title      = <val4>,
     *     institCode = <val5>.
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
     * Boolean success = new MrnScientists(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where surname = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnScientists scientists = new MrnScientists();
     * success = scientists.del(MrnScientists.CODE+"=<val1>");</pre>
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
     * update are taken from the MrnScientists argument, .e.g.<pre>
     * Boolean success
     * MrnScientists updMrnScientists = new MrnScientists();
     * updMrnScientists.setSurname(<val2>);
     * MrnScientists whereMrnScientists = new MrnScientists(<val1>);
     * success = whereMrnScientists.upd(updMrnScientists);</pre>
     * will update Surname to <val2> for all records where 
     * code = <val1>.
     * @param scientists  A MrnScientists variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnScientists scientists) {
        return db.update (TABLE, createColVals(scientists), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnScientists updMrnScientists = new MrnScientists();
     * updMrnScientists.setSurname(<val2>);
     * MrnScientists whereMrnScientists = new MrnScientists();
     * success = whereMrnScientists.upd(
     *     updMrnScientists, MrnScientists.CODE+"=<val1>");</pre>
     * will update Surname to <val2> for all records where 
     * code = <val1>.
     * @param  updMrnScientists  A MrnScientists variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnScientists scientists, String where) {
        return db.update (TABLE, createColVals(scientists), where);
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
        if (getSurname() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURNAME + "='" + getSurname() + "'";
        } // if getSurname
        if (getFName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + F_NAME + "='" + getFName() + "'";
        } // if getFName
        if (getTitle() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TITLE + "='" + getTitle() + "'";
        } // if getTitle
        if (getInstitCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTIT_CODE + "=" + getInstitCode("");
        } // if getInstitCode
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnScientists aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getSurname() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURNAME +"=";
            colVals += (aVar.getSurname().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurname() + "'");
        } // if aVar.getSurname
        if (aVar.getFName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += F_NAME +"=";
            colVals += (aVar.getFName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getFName() + "'");
        } // if aVar.getFName
        if (aVar.getTitle() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TITLE +"=";
            colVals += (aVar.getTitle().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTitle() + "'");
        } // if aVar.getTitle
        if (aVar.getInstitCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTIT_CODE +"=";
            colVals += (aVar.getInstitCode() == INTNULL2 ?
                "null" : aVar.getInstitCode(""));
        } // if aVar.getInstitCode
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getSurname() != CHARNULL) {
            columns = columns + "," + SURNAME;
        } // if getSurname
        if (getFName() != CHARNULL) {
            columns = columns + "," + F_NAME;
        } // if getFName
        if (getTitle() != CHARNULL) {
            columns = columns + "," + TITLE;
        } // if getTitle
        if (getInstitCode() != INTNULL) {
            columns = columns + "," + INSTIT_CODE;
        } // if getInstitCode
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        setCode(getMaxCode()+1);
        String values  = getCode("");
        if (getSurname() != CHARNULL) {
            values  = values  + ",'" + getSurname() + "'";
        } // if getSurname
        if (getFName() != CHARNULL) {
            values  = values  + ",'" + getFName() + "'";
        } // if getFName
        if (getTitle() != CHARNULL) {
            values  = values  + ",'" + getTitle() + "'";
        } // if getTitle
        if (getInstitCode() != INTNULL) {
            values  = values  + "," + getInstitCode("");
        } // if getInstitCode
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")       + "|" +
            getSurname("")    + "|" +
            getFName("")      + "|" +
            getTitle("")      + "|" +
            getInstitCode("") + "|";
    } // method toString

} // class MrnScientists
