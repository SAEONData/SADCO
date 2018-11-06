import common2.*;
import oracle.html.*;
import menusp.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * The menu application that manages the SADCO databases.  VOS and Marine.
 * It makes use of the non-forms menus package.
 *
 * @author  000424 - SIT Group
 * @version
 * 000424 - Ursula von St Ange - create class<br>
 * 020527 - Ursula von St Ange - changed to non-forms (like EdmMenuP)<br>
 * 020722 - Ursula von St Ange - add timeout feature<br>
 * 030719 - Ursula von St Ange - Change to servlets                         <br>
 */
public class SadcoMenu extends HttpServlet {

    //static boolean dbg = true;
    static boolean dbg = false;

     // values for args2 variable
    static String thisClass = "SadcoMenu";    // class name
    static String owner     = "sadco";        // Oracle schema userid
    static String title     = "SADCO";        // title on top bar
    static String date      = "27 June 2002"; // last update date

    // values for in this application
    static String headTitle = "Sadco";      // title in head part of page
    static String password  = "ter91qes";   // Oracle schema password

    // parameters
    String version = "";

    String[] args2 = {
        MnuConstants.APPLICATION    + "=" + thisClass,
        MnuConstants.OWNER          + "=" + owner,
        MnuConstants.TITLE          + "=" + title,
        MnuConstants.DATE           + "=" + date
    }; // args2

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();

    // url parameters names & applications
    sadco.SadConstants sc = new sadco.SadConstants();


    /**
     * The only constructor.  Depending on the value of the 'pversion' parameter,
     * it calls the correct class in the menus package.
     * The <b>args</b> variable must contain the following parameters: <br>
     * MnuConstants.VERSION - values = menu / form<br>
     * @param args  array of URL-like name=value parameter pairs - from the command line
     */
    //public void DoWork (HttpServletRequest req, HttpServletResponse res) {
    public void doWorkS (HttpServletRequest req, HttpServletResponse res) {
        // get the parameter-value pairs
        String args[] = common.edmCommon.convertReq2Args(req);

        HtmlPage hp = doWork(args);

        // get the 'printer' to write to
        try {
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.println(hp.toHTML());
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "Constructor", "");
        } // try-catch

    } //public void doWorkS (HttpServletRequest req,


    public HtmlPage doWork (String args[]) {

        // the menu tables belong to user dba2, but there are synonyms for
        // them in the sadco schema.
        menusp.Tables.userid = owner;
        menusp.Tables.password = password;

        // get/set the argument values
        if (args.length == 0)   {
            version = "menu";
        } else {
            version = ec.getArgument(args, sc.VERSION).toLowerCase();
        } // if args
        if (dbg) System.out.println ("version = " + version);
        // for timeout management

        /*****************/
        String screen = ec.getArgument(args, sc.SCREEN);
        /*****************/

        // create the page head
        HtmlHead hd = new HtmlHead(headTitle);

        // check to see whether the user has timed out - to is a special
        // parameter so that we can do testing
        String to = ec.getArgument(args, sc.TIMEOUT);
        if (dbg) System.out.println("<br>to = " + to);
        boolean timeout = false;
        /*
        if ("".equals(to)) {
            sadco.UserTimeoutFrame uto = new sadco.UserTimeoutFrame(args, headTitle);
            timeout = uto.getTimeout();
            if (timeout) hd.addMetaInfo(uto.getMetaInfo());
            if (dbg) System.out.println("<br>timeout = " + timeout);
        } // if ("".equals(to))
        */

        // create the body and the page itself
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);
        //hp.printHeader();

        // only continue if the user has not timed out
        // if user has timed out - the page will refresh to a logon screen.
        if (!timeout) {
            /*****************
            if (!"".equals(screen)) {

                if (screen.equals("panel")) {
                    hp = new HtmlPage
                        (hd,new menusf.MnuMenuPanel(args, args2).getFrameset());
                    // crook a bit to get the title
                    out.println("<HTML>");
                    out.println(hp.getHead().toString());
                } else if (screen.equals("top")) {
                    bd.addItem(new menusf.MnuMenuTopFrame(args, args2));
                } else if (screen.equals("middle")) {
                    if (version.equals("menu")) {
                        bd.addItem(new menusf.MnuMenuFrame(args, args2));
                    } else if (version.equals("form")) {
                        bd.addItem(new menusf.MnuMenuFormFrame(args, args2));
                    } // if version
                } else if (screen.equals("bottom")) {
                    bd.addItem(new BottomFrame(args));
                } // if screen

            } else {
            *****************/

            // the body of the 'menu'
            bd.addItem(new MnuMenuTopFrame(args, args2));
            bd.addItem("\n<hr>\n");

            // the body of the 'menu'
            if (version.equals("menu")) {
                bd.addItem(new MnuMenuFrame(args, args2));
            } else if (version.equals("form")) {
                bd.addItem(new MnuMenuFormFrame(args, args2));
            } // if version

            // add the bottom menu bar
            bd.addItem("\n<hr>\n");
            bd.addItem(new BottomFrame(args2));

            /*****************/
            //} // if ("".equals(screen))
            /*****************/

        } // if (!timeout)

        // get the 'printer' to write to
        //try {
            //res.setContentType("text/html");
            //PrintWriter out = res.getWriter();
            //out.println(hp.toHTML());
        //} catch(Exception e) {
        //    ec.processErrorStatic(e, thisClass, "Constructor", "");
        //} // try-catch
        //hp.printHeader();
        //hp.print();

        DbAccessC.close();
        return hp;

    } // public HtmlPage doWork (String args[]) { // SadcoMenu constructor

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args  array of URL-like name=value parameter pairs - from the command line
     */
//    public static void main(String args[]) {
//
//        try {
//            SadcoMenu local= new SadcoMenu(args);
//        } catch(Exception e) {
//            ec.processErrorStatic(e, thisClass, "Constructor", "");
//        } // try-catch
//
//    } // main

    public void doGet(HttpServletRequest req, HttpServletResponse res) {
            //throws ServletException, IOException {
        try {
            //DoWork(req, res);
            doWorkS(req, res);
            //Sadco local= new Sadco(req, res);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "doGet", "");
        } // try-catch
    } // doGet


    public void doPost(HttpServletRequest req, HttpServletResponse res) {
            //throws ServletException, IOException {
        try {
            doWorkS(req, res);
            //DoWork(req, res);
            //Sadco local= new Sadco(req, res);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "doPost", "");
        } // try-catch
    } // doGet


} // class SadcoMenu