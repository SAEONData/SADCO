package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the TISANPART table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnTisanpart extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "TISANPART";
    /** The parseq field name */
    public static final String PARSEQ = "PARSEQ";
    /** The aniseq field name */
    public static final String ANISEQ = "ANISEQ";
    /** The drymas field name */
    public static final String DRYMAS = "DRYMAS";
    /** The dryfct field name */
    public static final String DRYFCT = "DRYFCT";
    /** The tistyp field name */
    public static final String TISTYP = "TISTYP";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       parseq;
    private int       aniseq;
    private float     drymas;
    private float     dryfct;
    private String    tistyp;

    /** The error variables  */
    private int parseqError = ERROR_NORMAL;
    private int aniseqError = ERROR_NORMAL;
    private int drymasError = ERROR_NORMAL;
    private int dryfctError = ERROR_NORMAL;
    private int tistypError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String parseqErrorMessage = "";
    private String aniseqErrorMessage = "";
    private String drymasErrorMessage = "";
    private String dryfctErrorMessage = "";
    private String tistypErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final int       PARSEQ_MN = INTMIN;
    public static final int       PARSEQ_MX = INTMAX;
    public static final int       ANISEQ_MN = INTMIN;
    public static final int       ANISEQ_MX = INTMAX;
    public static final float     DRYMAS_MN = FLOATMIN;
    public static final float     DRYMAS_MX = FLOATMAX;
    public static final float     DRYFCT_MN = FLOATMIN;
    public static final float     DRYFCT_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception parseqOutOfBoundsException =
        new Exception ("'parseq' out of bounds: " +
            PARSEQ_MN + " - " + PARSEQ_MX);
    Exception aniseqOutOfBoundsException =
        new Exception ("'aniseq' out of bounds: " +
            ANISEQ_MN + " - " + ANISEQ_MX);
    Exception drymasOutOfBoundsException =
        new Exception ("'drymas' out of bounds: " +
            DRYMAS_MN + " - " + DRYMAS_MX);
    Exception dryfctOutOfBoundsException =
        new Exception ("'dryfct' out of bounds: " +
            DRYFCT_MN + " - " + DRYFCT_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnTisanpart() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnTisanpart constructor 1"); // debug
    } // MrnTisanpart constructor

    /**
     * Instantiate a MrnTisanpart object and initialize the instance variables.
     * @param parseq  The parseq (int)
     */
    public MrnTisanpart(
            int parseq) {
        this();
        setParseq (parseq);
        if (dbg) System.out.println ("<br>in MrnTisanpart constructor 2"); // debug
    } // MrnTisanpart constructor

    /**
     * Instantiate a MrnTisanpart object and initialize the instance variables.
     * @param parseq  The parseq (int)
     * @param aniseq  The aniseq (int)
     * @param drymas  The drymas (float)
     * @param dryfct  The dryfct (float)
     * @param tistyp  The tistyp (String)
     */
    public MrnTisanpart(
            int    parseq,
            int    aniseq,
            float  drymas,
            float  dryfct,
            String tistyp) {
        this();
        setParseq (parseq);
        setAniseq (aniseq);
        setDrymas (drymas);
        setDryfct (dryfct);
        setTistyp (tistyp);
        if (dbg) System.out.println ("<br>in MrnTisanpart constructor 3"); // debug
    } // MrnTisanpart constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setParseq (INTNULL  );
        setAniseq (INTNULL  );
        setDrymas (FLOATNULL);
        setDryfct (FLOATNULL);
        setTistyp (CHARNULL );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'parseq' class variable
     * @param  parseq (int)
     */
    public int setParseq(int parseq) {
        try {
            if ( ((parseq == INTNULL) ||
                  (parseq == INTNULL2)) ||
                !((parseq < PARSEQ_MN) ||
                  (parseq > PARSEQ_MX)) ) {
                this.parseq = parseq;
                parseqError = ERROR_NORMAL;
            } else {
                throw parseqOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setParseqError(INTNULL, e, ERROR_LOCAL);
        } // try
        return parseqError;
    } // method setParseq

    /**
     * Set the 'parseq' class variable
     * @param  parseq (Integer)
     */
    public int setParseq(Integer parseq) {
        try {
            setParseq(parseq.intValue());
        } catch (Exception e) {
            setParseqError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return parseqError;
    } // method setParseq

    /**
     * Set the 'parseq' class variable
     * @param  parseq (Float)
     */
    public int setParseq(Float parseq) {
        try {
            if (parseq.floatValue() == FLOATNULL) {
                setParseq(INTNULL);
            } else {
                setParseq(parseq.intValue());
            } // if (parseq.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setParseqError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return parseqError;
    } // method setParseq

    /**
     * Set the 'parseq' class variable
     * @param  parseq (String)
     */
    public int setParseq(String parseq) {
        try {
            setParseq(new Integer(parseq).intValue());
        } catch (Exception e) {
            setParseqError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return parseqError;
    } // method setParseq

    /**
     * Called when an exception has occured
     * @param  parseq (String)
     */
    private void setParseqError (int parseq, Exception e, int error) {
        this.parseq = parseq;
        parseqErrorMessage = e.toString();
        parseqError = error;
    } // method setParseqError


    /**
     * Set the 'aniseq' class variable
     * @param  aniseq (int)
     */
    public int setAniseq(int aniseq) {
        try {
            if ( ((aniseq == INTNULL) ||
                  (aniseq == INTNULL2)) ||
                !((aniseq < ANISEQ_MN) ||
                  (aniseq > ANISEQ_MX)) ) {
                this.aniseq = aniseq;
                aniseqError = ERROR_NORMAL;
            } else {
                throw aniseqOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAniseqError(INTNULL, e, ERROR_LOCAL);
        } // try
        return aniseqError;
    } // method setAniseq

    /**
     * Set the 'aniseq' class variable
     * @param  aniseq (Integer)
     */
    public int setAniseq(Integer aniseq) {
        try {
            setAniseq(aniseq.intValue());
        } catch (Exception e) {
            setAniseqError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aniseqError;
    } // method setAniseq

    /**
     * Set the 'aniseq' class variable
     * @param  aniseq (Float)
     */
    public int setAniseq(Float aniseq) {
        try {
            if (aniseq.floatValue() == FLOATNULL) {
                setAniseq(INTNULL);
            } else {
                setAniseq(aniseq.intValue());
            } // if (aniseq.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setAniseqError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aniseqError;
    } // method setAniseq

    /**
     * Set the 'aniseq' class variable
     * @param  aniseq (String)
     */
    public int setAniseq(String aniseq) {
        try {
            setAniseq(new Integer(aniseq).intValue());
        } catch (Exception e) {
            setAniseqError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aniseqError;
    } // method setAniseq

    /**
     * Called when an exception has occured
     * @param  aniseq (String)
     */
    private void setAniseqError (int aniseq, Exception e, int error) {
        this.aniseq = aniseq;
        aniseqErrorMessage = e.toString();
        aniseqError = error;
    } // method setAniseqError


    /**
     * Set the 'drymas' class variable
     * @param  drymas (float)
     */
    public int setDrymas(float drymas) {
        try {
            if ( ((drymas == FLOATNULL) ||
                  (drymas == FLOATNULL2)) ||
                !((drymas < DRYMAS_MN) ||
                  (drymas > DRYMAS_MX)) ) {
                this.drymas = drymas;
                drymasError = ERROR_NORMAL;
            } else {
                throw drymasOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDrymasError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return drymasError;
    } // method setDrymas

    /**
     * Set the 'drymas' class variable
     * @param  drymas (Integer)
     */
    public int setDrymas(Integer drymas) {
        try {
            setDrymas(drymas.floatValue());
        } catch (Exception e) {
            setDrymasError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return drymasError;
    } // method setDrymas

    /**
     * Set the 'drymas' class variable
     * @param  drymas (Float)
     */
    public int setDrymas(Float drymas) {
        try {
            setDrymas(drymas.floatValue());
        } catch (Exception e) {
            setDrymasError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return drymasError;
    } // method setDrymas

    /**
     * Set the 'drymas' class variable
     * @param  drymas (String)
     */
    public int setDrymas(String drymas) {
        try {
            setDrymas(new Float(drymas).floatValue());
        } catch (Exception e) {
            setDrymasError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return drymasError;
    } // method setDrymas

    /**
     * Called when an exception has occured
     * @param  drymas (String)
     */
    private void setDrymasError (float drymas, Exception e, int error) {
        this.drymas = drymas;
        drymasErrorMessage = e.toString();
        drymasError = error;
    } // method setDrymasError


    /**
     * Set the 'dryfct' class variable
     * @param  dryfct (float)
     */
    public int setDryfct(float dryfct) {
        try {
            if ( ((dryfct == FLOATNULL) ||
                  (dryfct == FLOATNULL2)) ||
                !((dryfct < DRYFCT_MN) ||
                  (dryfct > DRYFCT_MX)) ) {
                this.dryfct = dryfct;
                dryfctError = ERROR_NORMAL;
            } else {
                throw dryfctOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDryfctError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return dryfctError;
    } // method setDryfct

    /**
     * Set the 'dryfct' class variable
     * @param  dryfct (Integer)
     */
    public int setDryfct(Integer dryfct) {
        try {
            setDryfct(dryfct.floatValue());
        } catch (Exception e) {
            setDryfctError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dryfctError;
    } // method setDryfct

    /**
     * Set the 'dryfct' class variable
     * @param  dryfct (Float)
     */
    public int setDryfct(Float dryfct) {
        try {
            setDryfct(dryfct.floatValue());
        } catch (Exception e) {
            setDryfctError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dryfctError;
    } // method setDryfct

    /**
     * Set the 'dryfct' class variable
     * @param  dryfct (String)
     */
    public int setDryfct(String dryfct) {
        try {
            setDryfct(new Float(dryfct).floatValue());
        } catch (Exception e) {
            setDryfctError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dryfctError;
    } // method setDryfct

    /**
     * Called when an exception has occured
     * @param  dryfct (String)
     */
    private void setDryfctError (float dryfct, Exception e, int error) {
        this.dryfct = dryfct;
        dryfctErrorMessage = e.toString();
        dryfctError = error;
    } // method setDryfctError


    /**
     * Set the 'tistyp' class variable
     * @param  tistyp (String)
     */
    public int setTistyp(String tistyp) {
        try {
            this.tistyp = tistyp;
            if (this.tistyp != CHARNULL) {
                this.tistyp = stripCRLF(this.tistyp.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>tistyp = " + this.tistyp);
        } catch (Exception e) {
            setTistypError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return tistypError;
    } // method setTistyp

    /**
     * Called when an exception has occured
     * @param  tistyp (String)
     */
    private void setTistypError (String tistyp, Exception e, int error) {
        this.tistyp = tistyp;
        tistypErrorMessage = e.toString();
        tistypError = error;
    } // method setTistypError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'parseq' class variable
     * @return parseq (int)
     */
    public int getParseq() {
        return parseq;
    } // method getParseq

    /**
     * Return the 'parseq' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getParseq methods
     * @return parseq (String)
     */
    public String getParseq(String s) {
        return ((parseq != INTNULL) ? new Integer(parseq).toString() : "");
    } // method getParseq


    /**
     * Return the 'aniseq' class variable
     * @return aniseq (int)
     */
    public int getAniseq() {
        return aniseq;
    } // method getAniseq

    /**
     * Return the 'aniseq' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAniseq methods
     * @return aniseq (String)
     */
    public String getAniseq(String s) {
        return ((aniseq != INTNULL) ? new Integer(aniseq).toString() : "");
    } // method getAniseq


    /**
     * Return the 'drymas' class variable
     * @return drymas (float)
     */
    public float getDrymas() {
        return drymas;
    } // method getDrymas

    /**
     * Return the 'drymas' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDrymas methods
     * @return drymas (String)
     */
    public String getDrymas(String s) {
        return ((drymas != FLOATNULL) ? new Float(drymas).toString() : "");
    } // method getDrymas


    /**
     * Return the 'dryfct' class variable
     * @return dryfct (float)
     */
    public float getDryfct() {
        return dryfct;
    } // method getDryfct

    /**
     * Return the 'dryfct' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDryfct methods
     * @return dryfct (String)
     */
    public String getDryfct(String s) {
        return ((dryfct != FLOATNULL) ? new Float(dryfct).toString() : "");
    } // method getDryfct


    /**
     * Return the 'tistyp' class variable
     * @return tistyp (String)
     */
    public String getTistyp() {
        return tistyp;
    } // method getTistyp

    /**
     * Return the 'tistyp' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTistyp methods
     * @return tistyp (String)
     */
    public String getTistyp(String s) {
        return (tistyp != CHARNULL ? tistyp.replace('"','\'') : "");
    } // method getTistyp


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
        if ((parseq == INTNULL) &&
            (aniseq == INTNULL) &&
            (drymas == FLOATNULL) &&
            (dryfct == FLOATNULL) &&
            (tistyp == CHARNULL)) {
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
        int sumError = parseqError +
            aniseqError +
            drymasError +
            dryfctError +
            tistypError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the parseq instance variable
     * @return errorcode (int)
     */
    public int getParseqError() {
        return parseqError;
    } // method getParseqError

    /**
     * Gets the errorMessage for the parseq instance variable
     * @return errorMessage (String)
     */
    public String getParseqErrorMessage() {
        return parseqErrorMessage;
    } // method getParseqErrorMessage


    /**
     * Gets the errorcode for the aniseq instance variable
     * @return errorcode (int)
     */
    public int getAniseqError() {
        return aniseqError;
    } // method getAniseqError

    /**
     * Gets the errorMessage for the aniseq instance variable
     * @return errorMessage (String)
     */
    public String getAniseqErrorMessage() {
        return aniseqErrorMessage;
    } // method getAniseqErrorMessage


    /**
     * Gets the errorcode for the drymas instance variable
     * @return errorcode (int)
     */
    public int getDrymasError() {
        return drymasError;
    } // method getDrymasError

    /**
     * Gets the errorMessage for the drymas instance variable
     * @return errorMessage (String)
     */
    public String getDrymasErrorMessage() {
        return drymasErrorMessage;
    } // method getDrymasErrorMessage


    /**
     * Gets the errorcode for the dryfct instance variable
     * @return errorcode (int)
     */
    public int getDryfctError() {
        return dryfctError;
    } // method getDryfctError

    /**
     * Gets the errorMessage for the dryfct instance variable
     * @return errorMessage (String)
     */
    public String getDryfctErrorMessage() {
        return dryfctErrorMessage;
    } // method getDryfctErrorMessage


    /**
     * Gets the errorcode for the tistyp instance variable
     * @return errorcode (int)
     */
    public int getTistypError() {
        return tistypError;
    } // method getTistypError

    /**
     * Gets the errorMessage for the tistyp instance variable
     * @return errorMessage (String)
     */
    public String getTistypErrorMessage() {
        return tistypErrorMessage;
    } // method getTistypErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnTisanpart. e.g.<pre>
     * MrnTisanpart tisanpart = new MrnTisanpart(<val1>);
     * MrnTisanpart tisanpartArray[] = tisanpart.get();</pre>
     * will get the MrnTisanpart record where parseq = <val1>.
     * @return Array of MrnTisanpart (MrnTisanpart[])
     */
    public MrnTisanpart[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnTisanpart tisanpartArray[] =
     *     new MrnTisanpart().get(MrnTisanpart.PARSEQ+"=<val1>");</pre>
     * will get the MrnTisanpart record where parseq = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnTisanpart (MrnTisanpart[])
     */
    public MrnTisanpart[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnTisanpart tisanpartArray[] =
     *     new MrnTisanpart().get("1=1",tisanpart.PARSEQ);</pre>
     * will get the all the MrnTisanpart records, and order them by parseq.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnTisanpart (MrnTisanpart[])
     */
    public MrnTisanpart[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnTisanpart.PARSEQ,MrnTisanpart.ANISEQ;
     * String where = MrnTisanpart.PARSEQ + "=<val1";
     * String order = MrnTisanpart.PARSEQ;
     * MrnTisanpart tisanpartArray[] =
     *     new MrnTisanpart().get(columns, where, order);</pre>
     * will get the parseq and aniseq colums of all MrnTisanpart records,
     * where parseq = <val1>, and order them by parseq.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnTisanpart (MrnTisanpart[])
     */
    public MrnTisanpart[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnTisanpart.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnTisanpart[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int parseqCol = db.getColNumber(PARSEQ);
        int aniseqCol = db.getColNumber(ANISEQ);
        int drymasCol = db.getColNumber(DRYMAS);
        int dryfctCol = db.getColNumber(DRYFCT);
        int tistypCol = db.getColNumber(TISTYP);
        MrnTisanpart[] cArray = new MrnTisanpart[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnTisanpart();
            if (parseqCol != -1)
                cArray[i].setParseq((String) row.elementAt(parseqCol));
            if (aniseqCol != -1)
                cArray[i].setAniseq((String) row.elementAt(aniseqCol));
            if (drymasCol != -1)
                cArray[i].setDrymas((String) row.elementAt(drymasCol));
            if (dryfctCol != -1)
                cArray[i].setDryfct((String) row.elementAt(dryfctCol));
            if (tistypCol != -1)
                cArray[i].setTistyp((String) row.elementAt(tistypCol));
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
     *     new MrnTisanpart(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>).put();</pre>
     * will insert a record with:
     *     parseq = <val1>,
     *     aniseq = <val2>,
     *     drymas = <val3>,
     *     dryfct = <val4>,
     *     tistyp = <val5>.
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
     * Boolean success = new MrnTisanpart(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where aniseq = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnTisanpart tisanpart = new MrnTisanpart();
     * success = tisanpart.del(MrnTisanpart.PARSEQ+"=<val1>");</pre>
     * will delete all records where parseq = <val1>.
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
     * update are taken from the MrnTisanpart argument, .e.g.<pre>
     * Boolean success
     * MrnTisanpart updMrnTisanpart = new MrnTisanpart();
     * updMrnTisanpart.setAniseq(<val2>);
     * MrnTisanpart whereMrnTisanpart = new MrnTisanpart(<val1>);
     * success = whereMrnTisanpart.upd(updMrnTisanpart);</pre>
     * will update Aniseq to <val2> for all records where
     * parseq = <val1>.
     * @param  tisanpart  A MrnTisanpart variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnTisanpart tisanpart) {
        return db.update (TABLE, createColVals(tisanpart), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnTisanpart updMrnTisanpart = new MrnTisanpart();
     * updMrnTisanpart.setAniseq(<val2>);
     * MrnTisanpart whereMrnTisanpart = new MrnTisanpart();
     * success = whereMrnTisanpart.upd(
     *     updMrnTisanpart, MrnTisanpart.PARSEQ+"=<val1>");</pre>
     * will update Aniseq to <val2> for all records where
     * parseq = <val1>.
     * @param  tisanpart  A MrnTisanpart variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnTisanpart tisanpart, String where) {
        return db.update (TABLE, createColVals(tisanpart), where);
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
        if (getParseq() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PARSEQ + "=" + getParseq("");
        } // if getParseq
        if (getAniseq() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ANISEQ + "=" + getAniseq("");
        } // if getAniseq
        if (getDrymas() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DRYMAS + "=" + getDrymas("");
        } // if getDrymas
        if (getDryfct() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DRYFCT + "=" + getDryfct("");
        } // if getDryfct
        if (getTistyp() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISTYP + "='" + getTistyp() + "'";
        } // if getTistyp
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnTisanpart aVar) {
        String colVals = "";
        if (aVar.getParseq() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PARSEQ +"=";
            colVals += (aVar.getParseq() == INTNULL2 ?
                "null" : aVar.getParseq(""));
        } // if aVar.getParseq
        if (aVar.getAniseq() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ANISEQ +"=";
            colVals += (aVar.getAniseq() == INTNULL2 ?
                "null" : aVar.getAniseq(""));
        } // if aVar.getAniseq
        if (aVar.getDrymas() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DRYMAS +"=";
            colVals += (aVar.getDrymas() == FLOATNULL2 ?
                "null" : aVar.getDrymas(""));
        } // if aVar.getDrymas
        if (aVar.getDryfct() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DRYFCT +"=";
            colVals += (aVar.getDryfct() == FLOATNULL2 ?
                "null" : aVar.getDryfct(""));
        } // if aVar.getDryfct
        if (aVar.getTistyp() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISTYP +"=";
            colVals += (aVar.getTistyp().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTistyp() + "'");
        } // if aVar.getTistyp
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = PARSEQ;
        if (getAniseq() != INTNULL) {
            columns = columns + "," + ANISEQ;
        } // if getAniseq
        if (getDrymas() != FLOATNULL) {
            columns = columns + "," + DRYMAS;
        } // if getDrymas
        if (getDryfct() != FLOATNULL) {
            columns = columns + "," + DRYFCT;
        } // if getDryfct
        if (getTistyp() != CHARNULL) {
            columns = columns + "," + TISTYP;
        } // if getTistyp
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getParseq("");
        if (getAniseq() != INTNULL) {
            values  = values  + "," + getAniseq("");
        } // if getAniseq
        if (getDrymas() != FLOATNULL) {
            values  = values  + "," + getDrymas("");
        } // if getDrymas
        if (getDryfct() != FLOATNULL) {
            values  = values  + "," + getDryfct("");
        } // if getDryfct
        if (getTistyp() != CHARNULL) {
            values  = values  + ",'" + getTistyp() + "'";
        } // if getTistyp
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getParseq("") + "|" +
            getAniseq("") + "|" +
            getDrymas("") + "|" +
            getDryfct("") + "|" +
            getTistyp("") + "|";
    } // method toString

} // class MrnTisanpart
