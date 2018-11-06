package menusp;

import common2.*;
import java.sql.*;
import java.util.*;

/**
 * This file contains the parent class for all tables in this package
 * The parent class is called 'TablesC', and all the child classes are
 * called by the table names that they represent.
 *
 * @author  991024 - SIT Group
 * @version
 * 19991024 - Ursula von St Ange - create class
 * 20000119 - Ursula von St Ange - ub01: change NULL value for float, add for
 *                                       double<br>
 * 20000314 - Ursula von St Ange - copied from surveys package.<br>
 * 20020528 - Ursula von St Ange - added second contructor so that one can
 *                                 specify the userid and password.  Needed
 *                                 because the menu system gets called by
 *                                 various users (edm & sadco).<br>
 */
public class Tables extends TablesC {

    boolean dbg = false;
    //boolean dbg = true;

    public static String userid = "dba2";
    public static String password = "ter91qes";

    /**
     * Makes use of Tables constructor where the DbAccess class is
     * instantiated.
     */
    public Tables () {
        super (userid, password);
        if (dbg) System.out.println ("<br>in Tables constructor " + userid + " " + password); // debug

    } // Tables constructor

    /**
     * A second constructor which uses the userid and password variables.  These
     * can be set by the application before accessing any of the table classes.
     * @param any Any string - just used to distinguish between the two constructors.
     */
    public Tables(String any) {
        super(userid, password);
        if (dbg) System.out.println ("<br>in Tables constructor2 " + userid + " " + password);
    } // Tables constructor2

}   // class Tables
