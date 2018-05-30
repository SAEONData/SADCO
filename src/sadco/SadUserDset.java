package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SAD_USER_DSET table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class SadUserDset extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SAD_USER_DSET";
    /** The userid field name */
    public static final String USERID = "USERID";
    /** The dataset field name */
    public static final String DATASET = "DATASET";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    userid;
    private String    dataset;

    /** The error variables  */
    private int useridError  = ERROR_NORMAL;
    private int datasetError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String useridErrorMessage  = "";
    private String datasetErrorMessage = "";

    /** The min-max constants for all numerics */

    /** The exceptions for non-Strings */


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public SadUserDset() {
        clearVars();
        if (dbg) System.out.println ("<br>in SadUserDset constructor 1"); // debug
    } // SadUserDset constructor

    /**
     * Instantiate a SadUserDset object and initialize the instance variables.
     * @param userid  The userid (String)
     */
    public SadUserDset(
            String userid) {
        this();
        setUserid  (userid );
        if (dbg) System.out.println ("<br>in SadUserDset constructor 2"); // debug
    } // SadUserDset constructor

    /**
     * Instantiate a SadUserDset object and initialize the instance variables.
     * @param userid   The userid  (String)
     * @param dataset  The dataset (String)
     */
    public SadUserDset(
            String userid,
            String dataset) {
        this();
        setUserid  (userid );
        setDataset (dataset);
        if (dbg) System.out.println ("<br>in SadUserDset constructor 3"); // debug
    } // SadUserDset constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setUserid  (CHARNULL);
        setDataset (CHARNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

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
     * Set the 'dataset' class variable
     * @param  dataset (String)
     */
    public int setDataset(String dataset) {
        try {
            this.dataset = dataset;
            if (this.dataset != CHARNULL) {
                this.dataset = stripCRLF(this.dataset.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>dataset = " + this.dataset);
        } catch (Exception e) {
            setDatasetError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return datasetError;
    } // method setDataset

    /**
     * Called when an exception has occured
     * @param  dataset (String)
     */
    private void setDatasetError (String dataset, Exception e, int error) {
        this.dataset = dataset;
        datasetErrorMessage = e.toString();
        datasetError = error;
    } // method setDatasetError


    //=================//
    // All the getters //
    //=================//

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
     * Return the 'dataset' class variable
     * @return dataset (String)
     */
    public String getDataset() {
        return dataset;
    } // method getDataset

    /**
     * Return the 'dataset' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataset methods
     * @return dataset (String)
     */
    public String getDataset(String s) {
        return (dataset != CHARNULL ? dataset.replace('"','\'') : "");
    } // method getDataset


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
        if ((userid == CHARNULL) &&
            (dataset == CHARNULL)) {
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
        int sumError = useridError +
            datasetError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


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
     * Gets the errorcode for the dataset instance variable
     * @return errorcode (int)
     */
    public int getDatasetError() {
        return datasetError;
    } // method getDatasetError

    /**
     * Gets the errorMessage for the dataset instance variable
     * @return errorMessage (String)
     */
    public String getDatasetErrorMessage() {
        return datasetErrorMessage;
    } // method getDatasetErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of SadUserDset. e.g.<pre>
     * SadUserDset sadUserDset = new SadUserDset(<val1>);
     * SadUserDset sadUserDsetArray[] = sadUserDset.get();</pre>
     * will get the SadUserDset record where userid = <val1>.
     * @return Array of SadUserDset (SadUserDset[])
     */
    public SadUserDset[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * SadUserDset sadUserDsetArray[] =
     *     new SadUserDset().get(SadUserDset.USERID+"=<val1>");</pre>
     * will get the SadUserDset record where userid = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of SadUserDset (SadUserDset[])
     */
    public SadUserDset[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * SadUserDset sadUserDsetArray[] =
     *     new SadUserDset().get("1=1",sadUserDset.USERID);</pre>
     * will get the all the SadUserDset records, and order them by userid.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of SadUserDset (SadUserDset[])
     */
    public SadUserDset[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = SadUserDset.USERID,SadUserDset.DATASET;
     * String where = SadUserDset.USERID + "=<val1";
     * String order = SadUserDset.USERID;
     * SadUserDset sadUserDsetArray[] =
     *     new SadUserDset().get(columns, where, order);</pre>
     * will get the userid and dataset colums of all SadUserDset records,
     * where userid = <val1>, and order them by userid.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of SadUserDset (SadUserDset[])
     */
    public SadUserDset[] get (String fields, String where, String order) {
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
     * and transforms it into an array of SadUserDset.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private SadUserDset[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int useridCol  = db.getColNumber(USERID);
        int datasetCol = db.getColNumber(DATASET);
        SadUserDset[] cArray = new SadUserDset[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new SadUserDset();
            if (useridCol != -1)
                cArray[i].setUserid ((String) row.elementAt(useridCol));
            if (datasetCol != -1)
                cArray[i].setDataset((String) row.elementAt(datasetCol));
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
     *     new SadUserDset(
     *         <val1>,
     *         <val2>).put();</pre>
     * will insert a record with:
     *     userid  = <val1>,
     *     dataset = <val2>.
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
     * Boolean success = new SadUserDset(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where dataset = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * SadUserDset sadUserDset = new SadUserDset();
     * success = sadUserDset.del(SadUserDset.USERID+"=<val1>");</pre>
     * will delete all records where userid = <val1>.
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
     * update are taken from the SadUserDset argument, .e.g.<pre>
     * Boolean success
     * SadUserDset updSadUserDset = new SadUserDset();
     * updSadUserDset.setDataset(<val2>);
     * SadUserDset whereSadUserDset = new SadUserDset(<val1>);
     * success = whereSadUserDset.upd(updSadUserDset);</pre>
     * will update Dataset to <val2> for all records where
     * userid = <val1>.
     * @param  sadUserDset  A SadUserDset variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(SadUserDset sadUserDset) {
        return db.update (TABLE, createColVals(sadUserDset), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * SadUserDset updSadUserDset = new SadUserDset();
     * updSadUserDset.setDataset(<val2>);
     * SadUserDset whereSadUserDset = new SadUserDset();
     * success = whereSadUserDset.upd(
     *     updSadUserDset, SadUserDset.USERID+"=<val1>");</pre>
     * will update Dataset to <val2> for all records where
     * userid = <val1>.
     * @param  sadUserDset  A SadUserDset variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(SadUserDset sadUserDset, String where) {
        return db.update (TABLE, createColVals(sadUserDset), where);
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
        if (getUserid() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + USERID + "='" + getUserid() + "'";
        } // if getUserid
        if (getDataset() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATASET + "='" + getDataset() + "'";
        } // if getDataset
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(SadUserDset aVar) {
        String colVals = "";
        if (aVar.getUserid() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += USERID +"=";
            colVals += (aVar.getUserid().equals(CHARNULL2) ?
                "null" : "'" + aVar.getUserid() + "'");
        } // if aVar.getUserid
        if (aVar.getDataset() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATASET +"=";
            colVals += (aVar.getDataset().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDataset() + "'");
        } // if aVar.getDataset
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = USERID;
        if (getDataset() != CHARNULL) {
            columns = columns + "," + DATASET;
        } // if getDataset
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getUserid() + "'";
        if (getDataset() != CHARNULL) {
            values  = values  + ",'" + getDataset() + "'";
        } // if getDataset
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getUserid("")  + "|" +
            getDataset("") + "|";
    } // method toString

} // class SadUserDset
