//+----------------------------------------------+----------------+----------+
//| CLASS: ....... BottomFrame                   | JAVA PROGRAM   | 19991026 |
//+----------------------------------------------+----------------+----------+
//| PROGRAMMER: .. SIT Group                                                 |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//|                                                                          |
//|                                                                          |
//|                                                                          |
//|                                                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 20000209  SIT Group            create class                              |
//+--------------------------------------------------------------------------+

package common2;

//import java.io.*;
//import java.sql.*;
//import javax.servlet.*;
//import javax.servlet.http.*;
import oracle.html.*;

/**
 * Displays a Bar with "Page Maintained by: SIT Group  Last Update on {date}"
 */
public class BottomFrame extends CompoundItem{

    /** The title of the Bar */
    String TITLE = "ptitle";
    String title = "";
    /** The date the last update took place */
    String DATE = "pdate";
    String date = "";
    /** A general purpose class used to extract the url-type parameters*/
    common.edmCommon edmcommon = new common.edmCommon();

    /**
     * Instantiates a BottomFrame object.
     */
    public BottomFrame() {
        addItem(createCon());
    } // constructor

    /**
     * Instantiates a BottomFrame object.  This constructor takes only
     * one argument: <b>String[] args</b>, the string of parameters passed along
     * from the URL.  All these parameters have the format 'name=value'.  The
     * URL-type parameters are the following:
     * @param args the string of parameters passed along from the URL.
     */
    public BottomFrame(String[] args) {

        // get any parameters off the command line
        date  = new String(edmcommon.getArgument(args, DATE));
        title = new String(edmcommon.getArgument(args, TITLE));

        addItem(createCon());
    } // constructor

    /**
     * Instantiates a BottomFrame object.  This constructor takes only
     * one argument: <b>HttpServletRequest req</b>, the string of parameters
     * passed along from the URL.
     * URL-type parameters are the following:
     * @param args the string of parameters passed along from the URL.
     */
//    public BottomFrame(HttpServletRequest req) {
//
//        // get any parameters off the command line
//        date  = new String(edmcommon.getArgument(req, DATE));
//        title = new String(edmcommon.getArgument(req, TITLE));
//
//        addItem(createCon());
//    } // constructor

    /**
    * Create the container
    */
    Container createCon()  {

        if (date.equals("")) { setDate(); }
        if (title.equals("")) { setTitle(); }

        Container con = new Container();
        con.addItem(new Bar(title + date));
        return con;
    } // createCon()

    /**
    * Sets the default title if none is present in <b>args</b>
    */
    void setTitle() {
        title = "Page Maintained by: SIT Group " +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Update on: ";
    }

    /**
    * Sets the default date if none is present in <b>args</b>
    */
    void setDate() {
        date = "26 October 1999";
    }

} //class GeneralBottomFrame








