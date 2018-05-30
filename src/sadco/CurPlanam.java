package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the CUR_PLANAM table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class CurPlanam extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "CUR_PLANAM";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The name field name */
    public static final String NAME = "NAME";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    name;

    /** The error variables  */
    private int codeError = ERROR_NORMAL;
    private int nameError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage = "";
    private String nameErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public CurPlanam() {
        clearVars();
        if (dbg) System.out.println ("<br>in CurPlanam constructor 1"); // debug
    } // CurPlanam constructor

    /**
     * Instantiate a CurPlanam object and initialize the instance variables.
     * @param code  The code (int)
     */
    public CurPlanam(
            int code) {
        this();
        setCode (code);
        if (dbg) System.out.println ("<br>in CurPlanam constructor 2"); // debug
    } // CurPlanam constructor

    /**
     * Instantiate a CurPlanam object and initialize the instance variables.
     * @param code  The code (int)
     * @param name  The name (String)
     */
    public CurPlanam(
            int    code,
            String name) {
        this();
        setCode (code);
        setName (name);
        if (dbg) System.out.println ("<br>in CurPlanam constructor 3"); // debug
    } // CurPlanam constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode (INTNULL );
        setName (CHARNULL);
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
            (name == CHARNULL)) {
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
            nameError;
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


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of CurPlanam. e.g.<pre>
     * CurPlanam curPlanam = new CurPlanam(<val1>);
     * CurPlanam curPlanamArray[] = curPlanam.get();</pre>
     * will get the CurPlanam record where code = <val1>.
     * @return Array of CurPlanam (CurPlanam[])
     */
    public CurPlanam[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * CurPlanam curPlanamArray[] =
     *     new CurPlanam().get(CurPlanam.CODE+"=<val1>");</pre>
     * will get the CurPlanam record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of CurPlanam (CurPlanam[])
     */
    public CurPlanam[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * CurPlanam curPlanamArray[] =
     *     new CurPlanam().get("1=1",curPlanam.CODE);</pre>
     * will get the all the CurPlanam records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurPlanam (CurPlanam[])
     */
    public CurPlanam[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = CurPlanam.CODE,CurPlanam.NAME;
     * String where = CurPlanam.CODE + "=<val1";
     * String order = CurPlanam.CODE;
     * CurPlanam curPlanamArray[] =
     *     new CurPlanam().get(columns, where, order);</pre>
     * will get the code and name colums of all CurPlanam records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurPlanam (CurPlanam[])
     */
    public CurPlanam[] get (String fields, String where, String order) {
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
     * and transforms it into an array of CurPlanam.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private CurPlanam[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol = db.getColNumber(CODE);
        int nameCol = db.getColNumber(NAME);
        CurPlanam[] cArray = new CurPlanam[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new CurPlanam();
            if (codeCol != -1)
                cArray[i].setCode((String) row.elementAt(codeCol));
            if (nameCol != -1)
                cArray[i].setName((String) row.elementAt(nameCol));
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
     *     new CurPlanam(
     *         INTNULL,
     *         <val2>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     name = <val2>.
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
     * Boolean success = new CurPlanam(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where name = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurPlanam curPlanam = new CurPlanam();
     * success = curPlanam.del(CurPlanam.CODE+"=<val1>");</pre>
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
     * update are taken from the CurPlanam argument, .e.g.<pre>
     * Boolean success
     * CurPlanam updCurPlanam = new CurPlanam();
     * updCurPlanam.setName(<val2>);
     * CurPlanam whereCurPlanam = new CurPlanam(<val1>);
     * success = whereCurPlanam.upd(updCurPlanam);</pre>
     * will update Name to <val2> for all records where
     * code = <val1>.
     * @param  curPlanam  A CurPlanam variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(CurPlanam curPlanam) {
        return db.update (TABLE, createColVals(curPlanam), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurPlanam updCurPlanam = new CurPlanam();
     * updCurPlanam.setName(<val2>);
     * CurPlanam whereCurPlanam = new CurPlanam();
     * success = whereCurPlanam.upd(
     *     updCurPlanam, CurPlanam.CODE+"=<val1>");</pre>
     * will update Name to <val2> for all records where
     * code = <val1>.
     * @param  curPlanam  A CurPlanam variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(CurPlanam curPlanam, String where) {
        return db.update (TABLE, createColVals(curPlanam), where);
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
        if (getName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NAME + "='" + getName() + "'";
        } // if getName
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(CurPlanam aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NAME +"=";
            colVals += (aVar.getName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getName() + "'");
        } // if aVar.getName
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getName() != CHARNULL) {
            columns = columns + "," + NAME;
        } // if getName
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
        if (getName() != CHARNULL) {
            values  = values  + ",'" + getName() + "'";
        } // if getName
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("") + "|" +
            getName("") + "|";
    } // method toString

} // class CurPlanam
