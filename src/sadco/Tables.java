//+----------------------------------------------+----------------+----------+
//| CLASS: ....... Tables                        | JAVA PROGRAM   | 19991024 |
//+----------------------------------------------+----------------+----------+
//| PROGRAMMER: .. Ursula von St Ange                                        |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//| This file contains the parent class for all tables in the remvid database|
//| The parent class is called 'TablesC', and all the child classes are      |
//| by the table names (without DBN_) that they represent.                   |
//|                                                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 19991024  Ursula von St Ange   create class                              |
//| 20000119  Ursula von St Ange   ub01: change NULL value for float, add for|
//|                                      double                              |
//| 20000314  Ursula von St Ange   copied from surveys package.              |
//+--------------------------------------------------------------------------+

package sadco;

import common2.*;
import java.sql.*;
import java.util.*;

/**
 * This class is the parent class to all the classes that manage tables
 * It uses the DbAccess class.
 */
public class Tables extends TablesC {

    boolean dbg = false;
    //boolean dbg = true;
    String thisClass = this.getClass().getName();

    /**
     * Makes use of Tables constructor where the DbAccess class is
     * instantiated.
     */
    public Tables () {
        super ("sadco", "ter91qes");
        if (dbg) System.out.println ("<br>" + thisClass + ".constructor (Tables)"); // debug
    } // Tables constructor

}   // class Tables
