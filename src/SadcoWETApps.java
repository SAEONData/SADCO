import common2.*;
import sadco.*;
import menusp.*;
import java.io.*;

/**
 * An Application class that manages the SADCO Marine application.
 *
 * @author  001102 - SIT Group
 * @version
 * 001102 - Ursula von St Ange - create class                               <br>
 * 020628 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020722 - Ursula von St Ange - add timeout feature                        <br>
 * 030725 - Mario August - Use SadcoWET as base - change to application     <br>
 */
public class SadcoWETApps { //extends HttpServlet {

    // values for args2 variable
    static String application = "SadcoMenu";  // menu application
    static String owner       = "sadco";      // Oracle schema owner
    static String title       = "SADCO";      // title in the top bar
    static String callType    = "non-menu";   // indicate non-menu app
    static String date        = "27 June 2002"; // last update date

    // values for in this application
    static String thisClass   = "SadcoWETApps"; // class name
    static String headTitle   = "SadcoWETApps"; // title in head part of page
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
    static common.edmCommonPC ec = new common.edmCommonPC();
    // url parameters names & applications
    sadco.SadConstants sc = new sadco.SadConstants();

    /**
     * The only constructor.
     * @param args  array of URL-like name=value parameter pairs - from the command line
     */

    /*
    public void doWorkS (HttpServletRequest req, HttpServletResponse res) {
        // get the parameter-value pairs
        String args[] = common.edmCommonPC.convertReq2Args(req);
        //if (dbg) out.println("args.length = " + args.length);

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
    */

    public SadcoWETApps (String args[]) {
    //public HtmlPage doWork (String args[]) {

        // the menu tables belong to user dba2, but there are synonyms for
        // them in the sadco schema.
        menusp.Tables.userid = owner;
        menusp.Tables.password = password;

        // get the parameters - must always be argumemnts
        String screen = ec.getArgument(args, sc.SCREEN).toLowerCase();
        String version = ec.getArgument(args, sc.VERSION).toLowerCase();
        // for the top menu bar
        String menuNo = ec.getArgument(args, MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, MnuConstants.MVERSION);

        //System.out.println("<br>screen = " + screen);
        //System.out.println("<br>version = " + version);

        // complete args2 for top bar
        args2[args2.length-1] = MnuConstants.MVERSION + "=" + mVersion;

        // create the page head
        //HtmlHead hd = new HtmlHead(headTitle);

        // check to see whether the user has timed out - to is a special
        // parameter so that we can do testing
        /*
        String to = ec.getArgument(args, sc.TIMEOUT);
        boolean timeout = false;
        if ("".equals(to)) {
            sadco.UserTimeoutFrame uto = new sadco.UserTimeoutFrame(args, thisClass);
            timeout = uto.getTimeout();
            if (timeout) //hd.addMetaInfo(uto.getMetaInfo());
            uto.getMetaInfo();
        } // if ("".equals(to))
        */

        // only continue if the user has not timed out
        // if user has timed out - the page will refresh to a logon screen.
        /*
        if (!timeout) {
            // add the refresh meta-tag if progress
            if (version.equals("progress")) {
                //hd.addMetaInfo(new MetaInfo("refresh", "60"));
                //hd.addMetaInfo(
                    new MetaInfo("refresh",
                    "15;URL=" + sc.WET_APP + "?" +
                    sc.SCREEN + "=" + screen + "&" +
                    sc.VERSION + "=progress&" +
                    sc.SESSIONCODE + "=" + ec.getArgument(args,sc.SESSIONCODE) + "&" +
                    sc.USERTYPE + "=" + ec.getArgument(args,sc.USERTYPE) + "&" +
                    sc.FILENAME + "=" + ec.getArgument(args,sc.FILENAME) + "&" +
                    sc.REQNUMBER + "=" + ec.getArgument(args,sc.REQNUMBER) + "&" +
                    sc.PRODUCT + "=" + ec.getArgument(args,sc.PRODUCT) + "&" +
                    MnuConstants.MENUNO + "=" + menuNo + "&" +
                    MnuConstants.MVERSION + "=" + mVersion);//);
            } // if (version.equals("progress")) {

            if (screen.equals("extract") && version.equals("extract")) {
                //hd.addMetaInfo(
                    new MetaInfo("refresh",
                    "15;URL=" + sc.WET_APP + "?" +
                    sc.SCREEN + "=extract&" +
                    sc.VERSION + "=progress&" +
                    sc.USERTYPE + "=" + ec.getArgument(args,sc.USERTYPE) + "&" +
                    sc.SESSIONCODE + "=" + ec.getArgument(args,sc.SESSIONCODE) + "&" +
                    sc.FILENAME + "=" + ec.getArgument(args,sc.FILENAME) + "&" +
                    sc.REQNUMBER + "=" + ec.getArgument(args,sc.REQNUMBER) + "&" +
                    "pursula=dummy" + "&" +
                    MnuConstants.MENUNO + "=" + menuNo + "&" +
                    MnuConstants.MVERSION + "=" + mVersion);//);
            } // if (screen.equals("extract") && version.equals("extract"))

        } // if (!timeout)
        */

        // create the body and the page itself
        //HtmlBody bd = new HtmlBody();
        //HtmlPage hp = new HtmlPage(hd,bd);
        //hp.printHeader();

        // only continue if the user has not timed out
        // if user has timed out - the page will refresh to a logon screen.
        //if (!timeout) {
            // add the top menu bar
            //bd.addItem(new menusp.MnuMenuTopFrame(args, args2));
            //bd.addItem("\n<hr>\n");

            // the body of the 'menu'
            if (screen.equals("extract")) {
                //if (version.equals("preextract")) {
                //    bd.addItem(new ExtractMRNSelectFrame(args));
                //} else
                if (version.equals("extract")) {
                    //bd.addItem(new ExtractWETFrame(args));
                    new ExtractWETFrame(args);
                } else if (version.equals("doextract")) {
                    //System.out.println("<br>version = " + version);
                    //bd.addItem(new ExtractWETData(args));
                    new ExtractWETData(args);
                } //else if (version.equals("progress")) {
                    //bd.addItem(new ExtractProgressFrame(args, "wet"));
                //} // else if (version.equals("extract"))
            } //if (screen.equals("extract")) {
            // add the bottom menu bar
            //bd.addItem("\n<hr>\n");
            //bd.addItem(new common2.BottomFrame(args2));
        //} // if (!timeout)

        //hp.print();

        DbAccessC.close();
        //return hp;

    } // SadcoWETApps constructor

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */

    public static void main(String args[]) {

        try {
            SadcoWETApps local= new SadcoWETApps(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main



    /*
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
    */



} // class SadcoWETApps

