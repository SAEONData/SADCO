import common2.*;
import oracle.html.*;
import sadco.*;
import menusp.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Handles the general Sadco stuff.  Login and user administration will be
 * used by both VOS and Marine data users.
 *
 * @author  001102 - SIT Group
 * @version
 * 001102 - Ursula von St Ange - create class                               <br>
 * 020527 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020722 - Ursula von St Ange - add timeout feature                        <br>
 * 020724 - Ursula von St Ange - make non-frames the default                <br>
 * 030719 - Ursula von St Ange - Change to servlets                         <br>
 */
public class Sadco extends HttpServlet {

    // values for args2 variable
    static String application = "SadcoMenu";  // menu application
    static String owner       = "Sadco";      // Oracle schema owner
    static String title       = "Sadco";      // title in the top bar
    static String callType    = "non-menu";   // indicate non-menu app
    static String date        = "27 June 2002"; // last update date

    // values for in this application
    static String thisClass   = "Sadco"; // class name
    static String headTitle   = "Sadco"; // title in head part of page
    static String password    = "ter91qes"; // Oracle schema password

    // for the menu package
    static String[] args2 = {
        MnuConstants.APPLICATION+"="+application,
        MnuConstants.OWNER      +"="+owner,
        MnuConstants.TITLE      +"="+title,
        MnuConstants.CALLTYPE   +"="+callType,
        MnuConstants.DATE       +"="+date,
        MnuConstants.MVERSION   +"=" // must not have a value
    }; // args2

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();
    // url parameters names & applications
    sadco.SadConstants sc = new sadco.SadConstants();


    // does the actual work
    public void doWorkS (HttpServletRequest req, HttpServletResponse res) {

        // get the parameter-value pairs
        String args[] = common.edmCommon.convertReq2Args(req);

        HtmlPage hp = doWork(args);

        // write the page to screen
        try {
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.println(hp.toHTML());
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "Constructor", "");
        } // try-catch
    } // doWorkS


    public HtmlPage doWork (String args[]) {

        // the menu tables belong to user dba2, but there are synonyms for
        // them in the Sadco schema.
        menusp.Tables.userid = owner;
        menusp.Tables.password = password;

        // get the parameters
        String screen = ec.getArgument(args, sc.SCREEN).toLowerCase();
        String version = ec.getArgument(args, sc.VERSION).toLowerCase();
        // for timeout management
        String to = ec.getArgument(args, sc.TIMEOUT);
        String sessionCode = ec.getArgument(args,sc.SESSIONCODE);
        // for the top menu bar
        String menuNo = ec.getArgument(args, MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, MnuConstants.MVERSION);

        // complete args2 for top bar
        args2[args2.length-1] = MnuConstants.MVERSION + "=" + mVersion;


        // create the page head
        HtmlHead hd = new HtmlHead(headTitle);

        // the timeout argument will have a value if the user has timed out
        // also test if sessioncode has a value - else it will be a new login anyway
        boolean timeout = false;
        if (!"".equals(sessionCode) && "".equals(to)) {
            UserTimeoutFrame uto = new UserTimeoutFrame(args, headTitle);
            timeout = uto.getTimeout();
            if (timeout) hd.addMetaInfo(uto.getMetaInfo());
        } // if ("".equals(to))

        // create the body and the page itself
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        // only continue if the user has not timed out
        // if user has timed out - the page will refresh to a logon screen.
        if (!timeout) {
            if ("".equals(screen))   {
                screen = "security";
                version = "login";
            } // if ("".equals(screen))

            // the body of the 'menu'
            if (screen.equals("security")) {

                if (version.equals("login")) {
                    bd.addItem(new LoginFrame(args));
                } else if (version.equals("confirm")) {
                    bd.addItem(new LoginConfirmFrame(args, hd));
                } // if (version.equals("login"))

            } else if (screen.equals("user")) {

                bd.addItem(new menusp.MnuMenuTopFrame(args, args2));
                bd.addItem("\n<hr>\n");

                if (version.equals("select")) {
                    bd.addItem(new UserSelectFrame(args));
                } else if (version.equals("user")) {
                    bd.addItem(new UserFrame(args));
                } else if (version.equals("confirm")) {
                    bd.addItem(new UserConfirmFrame(args));
                } else if (version.equals("delete")) {
                    bd.addItem(new UserDeleteFrame(args));
                } else if (version.equals("loglist")) {
                    bd.addItem(new UserLogListFrame(args));
                } // else if (screen.equals("user"))

                bd.addItem("\n<hr>\n");
                bd.addItem(new common2.BottomFrame(args2));

            } // if (screen.equals("security"))

        } // if (!timeout)

        // want this outside the time-out test
        if (screen.equals("matrix")) {
            new SadConstants(args);
        } // if (screen.equals("security"))

        // close any open connections
        DbAccessC.close();
        return hp;

    } // DoWork

    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            doWorkS(req, res);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "doGet", "");
            common2.DbAccessC.close();
        } // try-catch
    } // doGet


    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            doWorkS(req, res);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "doPost", "");
            common2.DbAccessC.close();
        } // try-catch
    } // doGet

    // to test as an application - uncomment the constructor and main method
    /**
     * The only constructor.
     * @param args  array of URL-like name=value parameter pairs - from the command line
     */
/*
    public Sadco (String args[]) {
        HtmlPage hp = doWork(args);
        hp.printHeader();
        hp.print();
    } // Sadco
*/
    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
/*
    public static void main(String args[]) {
        try {
            Sadco local= new Sadco(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "main", "");
            common2.DbAccessC.close();
        } // try-catch

    } // main
*/

} // class Sadco






