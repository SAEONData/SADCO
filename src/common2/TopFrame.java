//+----------------------------------------------+----------------+----------+
//| CLASS: ....... TopFrame                      | JAVA PROGRAM   | 19991026 |
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
 * Displays a Bar with a title
 */
public class TopFrame extends CompoundItem{

    /** The title of the Bar */
    String TITLE = "ptitle";
    String title = "";
    /** A general purpose class used to extract the url-type parameters*/
    common.edmCommon ec = new common.edmCommon();

    /**
     * Instantiates a TopFrame object.
     */
    public TopFrame() {
        addItem(createCon());
    } // constructor

    /**
     * Instantiates a TopFrame object.  This constructor takes only
     * one argument: <b>String[] args</b>, the string of parameters passed along
     * from the URL.  All these parameters have the format 'name=value'.  The
     * URL-type parameters are the following:
     * @param args The arguments from the URL
     */
    public TopFrame(String[] args) {
        // get any parameters off the command line
        title = ec.getArgument(args, TITLE);
        addItem(createCon());
    } // constructor

    /**
     * Instantiates a TopFrame object.  This constructor takes only
     * one argument: <b>HttpServletRequest req</b>, the string of parameters
     * passed along from the URL.
     * URL-type parameters are the following:
     * @param ptitle The title to be displayed in the Bar. The default is
     *             'Page Maintained by: SIT Group     Last Update on date'
     * @param pdate The date to be displayed in the Bar. The default is '26
     *              October 1999'.
     * @return A TopFrame object (CompoundItem)
     */
//    public TopFrame(HttpServletRequest req) {
//        // get any parameters off the command line
//        title = new String(ec.getArgument(req, TITLE));
//        addItem(createCon());
//    } // constructor

    /**
    * Create the container
    */
    Container createCon()  {
        if (title.equals("")) { setTitle(); }
        Container con = new Container();
        con.addItem(new Bar(title));
        return con;
    } // createCon()

    /**
    * Sets the default title if none is present in <b>args</b>
    */
    void setTitle() {
        title = "No Title given";
    } // setTitle

} //class TopFrame








