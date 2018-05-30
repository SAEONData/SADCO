//+------------------------------------------------+----------------+--------+
//| CLASS: ....... CodesTablesC                     | JAVA PROGRAM   | 991115 |
//+------------------------------------------------+----------------+--------+
//| PROGRAMMER: .. Ursula von St Ange                                        |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//| Superclass to hanlde all codes tables.                                   |
//| Subclasses: Country, CoordinateSystem, Projection, Ellipsoid, Datum      |
//|             Survey_type, Client                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 991115  Ursula von St Ange   create class                                |
//+--------------------------------------------------------------------------+

package common2;

import java.sql.*;
import java.util.*;

/**
 * This class manages all the codes tables.  It has as subclasses Country,
 * CoordinateSystem, Projection, Ellipsoid, Datum, Survey_type, and Client.
 * It has <b>doGet, doPut, doDel</b> and <b>doUpd</b> methods that are
 * called by the subclasses to <b>select, insert, delete</b> and <b>update</b>
 * the table.
 */
public class CodesTablesC extends TablesC {
//public class CodesTablesC {

    //boolean dbg = true;
    boolean dbg = false;

    ///** The null value for all 'code' fields */
    //public static final int    CODENULL = -1;
    ///** The null value for all character fields */
    //public static final String CHARNULL = null;

    /** The table name */
    public static String TABLE = "CODES TABLES C";
    /** The Code field name */
    public static final String CODE = "CODE";
    /** The Name field name */
    public static final String NAME = "NAME";

    ///** The database accessor class that does all the actual work. */
    //public DbAccessC db;
    //public static int driver;

    /**
     * The class variables corresponding to the table fields
     */
    int     code;
    String  name;

    /** user */
    String  user1;
    /** password */
    String  pwd1;

    /**
     * Makes use of Tables constructor where the DbAccess class is
     * instantiated.
     */
    public CodesTablesC (String user, String pwd) {
        //db = new DbAccessC(user, pwd);
        //driver = db.getDriver();
        super(user, pwd);
        clearVars();
        user1 = user;
        pwd1 = pwd;
        if (dbg) System.out.println ("common2.CodesTablesC constructor1: TABLE = " +
            TABLE); // debug
    } // CodesTablesC constructor

    /**
     * Instantiate a CodesTablesC object and initialize the class variables.
     * @param user   The userid
     * @param pwd    The password
     * @param aCode  The code (int)
     */
    public CodesTablesC (String user, String pwd, int aCode) {
        this(user, pwd);
        clearVars();
        setCode (aCode);
        if (dbg) System.out.println ("common2.CodesTablesC constructor2: TABLE = " +
            TABLE); // debug
    } // CodesTablesC constructor

    /**
     * Instantiate a CodesTablesC object and initialize the class variables.
     * @param user   The userid
     * @param pwd    The password
     * @param aCode  The code (int)
     * @param aName  The name (String)
     */
    public CodesTablesC (String user, String pwd, int aCode, String aName) {
        this(user, pwd);
        setCode (aCode);
        setName (aName);
        if (dbg) System.out.println ("common2.CodesTablesC constructor3: TABLE = " +
            TABLE); // debug
    } // CodesTablesC constructor

    /**
     * Instantiate a CodesTablesC object and initialize the class variables.
     * @param user   The userid
     * @param pwd    The password
     * @param aCode  The code (Integer)
     * @param aName  The name (String)
     */
    public CodesTablesC (String user, String pwd, Integer aCode, String aName) {
        this(user, pwd);
        setCode (aCode);
        setName (aName);
        if (dbg) System.out.println ("common2.CodesTablesC constructor4: TABLE = " +
            TABLE); // debug
    } // CodesTablesC constructor

    /**
     * Instantiate a CodesTablesC object and initialize the class variables.
     * @param user   The userid
     * @param pwd    The password
     * @param aCode  The code (String)
     * @param aName  The name (String)
     */
    public CodesTablesC (String user, String pwd, String aCode, String aName) {
        this(user, pwd);
        setCode (aCode);
        setName (aName);
        if (dbg) System.out.println ("common2.CodesTablesC constructor5: TABLE = " +
            TABLE); // debug
    } // CodesTablesC constructor


    /**
     * Initialise the instance variables to null
     */
    public void clearVars() {
        setCode (CODENULL);
        setName (CHARNULL);
    }  // method clearVars


    //===================================================================//
    // all the getters and setters                                       //
    //===================================================================//

    /**
     * Return the code instance variable
     * @return code (int)
     */
    public int getCode () {
        return code;
    } // method getCode

    /**
     * Return the code instance variable
     * @param  aString Any String - used to differentiate the 2 getCode methods.
     * @return code (String)
     */
    public String getCode (String aString) {
        return new Integer (code).toString();
    } // method getCode

    /**
     * Return the instance variable
     * @return Name (String)
     */
    public String getName () {
        return name;
    } // method getName

    /**
     * Set the code instance variable
     * @param  aCode (int)
     */
    public void setCode (int aCode) {
        code = aCode;
    } // method setCode

    /**
     * Set the code instance variable
     * @param  aCode (Integer)
     */
    public void setCode (Integer aCode) {
        code = aCode.intValue();
    } // method setCode

    /**
     * Set the code instance variable
     * @param  aCode (Float)
     */
    public void setCode (Float aCode) {
        code = aCode.intValue();
    } // method setCode

    /**
     * Set the code instance variable
     * @param  aCode (String)
     */
    public void setCode (String aCode) {
        code = new Integer(aCode).intValue();
    } // method setCode

    /**
     * Set the Name instance variable
     * @param  aName (String)
     */
    public void setName (String aName) {
        name = aName;
    } // method setName


    /**
     * Gets the maximum value for the Code from the table
     * @return mamimum Code value (int)
     */
    public int getMaxCode (String table) {
        Vector result = db.select ("max(" + CODE + ")",table,"1=1");
        Vector row = (Vector) result.elementAt(0);
        return ((Float) row.elementAt(0)).intValue();
    } // method getMaxCode


    /**
     * This function receives the result of the select statement from DbAccess,
     * and transforms it into an array of CodesTablesC.
     * @param  result (Vector (rows) of Vectors (columns)).
     * @return Array of CodesTablesC (CodesTablesC[])
     */
    public CodesTablesC[] doGet (Vector result) {
        int codeCol = db.getColNumber(CODE);
        int nameCol = db.getColNumber(NAME);
        CodesTablesC[] cArray = new CodesTablesC[result.size()];
        if (dbg) System.out.println ("<br>common2.CodesTablesC:doGet: " +
            result.size());  // debug
        for (int i=0; i<result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new CodesTablesC (user1, pwd1);
            cArray[i].setCode((Float) row.elementAt(codeCol));
            cArray[i].setName((String) row.elementAt(nameCol));
        } // for
        return cArray;
    } // method doGet


    /**
     * Insert a record into the table.  The values are taken from the current
     * value of the instance variables. .e.g.<pre>
     * Boolean success = new CodesTablesC
     *   (CodesTablesC.CODENULL, "newCodesTablesC").put();</pre>
     * will insert a record with Code the maximum Code+1, and
     * Name = 'newCodesTablesC'.
     * @return success = true/false (boolean)
     */
    public boolean doPut (String table) {
        if (getCode() == CODENULL) {
            setCode(getMaxCode(table)+1);
        } // if (getCode() != CODENULL)
        String columns = CODE;
        String values  = getCode("");
        if (getName() != CHARNULL) {
            columns = columns + "," + NAME;
            values  = values  + ",'" + getName() + "'";
        }   // if
        return (db.insert (table, columns, values));
    } // method doPut


    /**
     * Delete record(s) from the table.  The where clause is creaed from the
     * current values of the instance variables. .e.g.<pre>
     * Boolean success = new CodesTablesC (CodesTablesC.CODENULL, "newCodesTablesC").del();</pre>
     * will delete all records where Name = 'newCodesTablesC'.
     * @param  where The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean doDel (String table, String where) {
        return db.delete(table, where);
    } // method doDel


    /**
     * Update the record(s) in the table.  The fields and values to use for
     * the update are taken from the CodesTablesC parameter, .e.g.<pre>
     * Boolean success;
     * CodesTablesC upd_codes = new CodesTablesC(CodesTablesC.CODENULL, "updCodesTablesC");
     * CodesTablesC where_codes = new CodesTablesC(3, CodesTablesC.CHARNULL);
     * success = where_codes.upd(upd_codes); </pre>
     * will update the Name to 'updCodesTablesC' of all records where
     * Code=3.
     * @param  table    The table name
     * @param ct        A CodesTablesC variable in which the fields to be used for
     *                  the update are set
     * @param  where The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean doUpd (String table, CodesTablesC ct, String where) {
        return db.update (table, createColVals(ct), where);
    }   // method upd


    /**
     * This function creates the where clause from the current values of the
     * instance variables Code and Name.
     * @return where clause (String)
     */
    public String createWhere () {
        String where = "";
        if (getCode() != CODENULL) {
            where = CODE + "=" + getCode("");
        }
        if (getName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            }
            where = where + NAME + "='" + getName() + "'";
        }   // if getName
        if (dbg) System.out.println ("<br>common2.CodesTablesC.createWhere: where = " +
            where);   // debug
        return (where.equals("") ? null : where);
    } // method createWhere

    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the class variables.
     * @return colVals (String)
     */
    public String createColVals (CodesTablesC ct) {
        String colVals = "";
        if (ct.getCode() != CODENULL) {
            colVals = CODE + "=" + ct.getCode("");
        }
        if (ct.getName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals = colVals + ",";
            }
            colVals = colVals + NAME + "='" + ct.getName() + "'";
        }   // if (ct.getName()
        return colVals;
    } // method createColVals

    /**
     * Returns the String version of this class.
     * @return toString (String)
     */
    public String toString() {
        return getCode("") + " | " + getName() + " | ";
    } // toString

} // class CodesTablesC
