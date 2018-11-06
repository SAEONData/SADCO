import common2.*;
import oracle.html.*;
import sadco.*;
import menusp.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * An Application class that manages the SADCO Marine application.
 *
 * @author  001102 - SIT Group
 * @version
 * 001102 - Ursula von St Ange - create class                           <br>
 * 020628 - Ursula von St Ange - update for non-frame menus             <br>
 * 020722 - Ursula von St Ange - add timeout feature                    <br>
 * 030719 - Ursula von St Ange - Change to servlets                     <br>
 * 040609 - Ursula von St Ange - add currents loading (ub01)            <br>
 */
public class SadcoMRN extends HttpServlet {

    boolean dbg = false;
    //boolean dbg = true;

    // values for args2 variable
    static String application = "SadcoMenu";  // menu application
    static String owner       = "sadco";      // Oracle schema owner
    static String title       = "SADCO";      // title in the top bar
    static String callType    = "non-menu";   // indicate non-menu app
    static String date        = "20 August 2003"; // last update date

    // values for in this application
    static String thisClass   = "SadcoMRN"; // class name
    static String headTitle   = "SadcoMRN"; // title in head part of page
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

    /**
     * The only constructor.
     * @param args  array of URL-like name=value parameter pairs - from the command line
     */
    //public SadcoMRN (String args[]) {
    public void doWorkS (HttpServletRequest req, HttpServletResponse res) {
        // get the parameter-value pairs
        String args[] = common.edmCommon.convertReq2Args(req);
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

    } //public void doWorkS (HttpServletRequest req, HttpServletResponse res) {

    public HtmlPage doWork (String args[]) {

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
        // to decide whether the top menu bar will be displayed
        String userType = ec.getArgument(args, MnuConstants.USERTYPE);
        if (dbg) System.out.println(sc.SCREEN + " = " + screen);
        if (dbg) System.out.println(sc.VERSION + " = " + version);

        // complete args2 for top bar
        args2[args2.length-1] = MnuConstants.MVERSION + "=" + mVersion;

        // create the page head, body and the page itself
        HtmlHead hd = new HtmlHead(headTitle);
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        // check to see whether the user has timed out - to is a special
        // parameter so that we can do testing
        boolean timeout = false;
/*
        String to = ec.getArgument(args, sc.TIMEOUT);
        if ("".equals(to)) {
            sadco.UserTimeoutFrame uto = new sadco.UserTimeoutFrame(args, thisClass);
            timeout = uto.getTimeout();
            if (timeout) hd.addMetaInfo(uto.getMetaInfo());
        } // if ("".equals(to))
*/

        // only continue if the user has not timed out
        // if user has timed out - the page will refresh to a logon screen.
        if (!timeout) {
            // add the refresh meta-tag if progress
            if (version.equals("progress")) {
                //hd.addMetaInfo(new MetaInfo("refresh", "60"));
                hd.addMetaInfo(new MetaInfo("refresh",
                    "15;URL=" + sc.MRN_APP + "?" +
                    sc.SCREEN + "=" + screen + "&" +
                    sc.VERSION + "=progress&" +
                    sc.SESSIONCODE + "=" + ec.getArgument(args,sc.SESSIONCODE) + "&" +
                    sc.USERTYPE + "=" + ec.getArgument(args,sc.USERTYPE) + "&" +
                    sc.FILENAME + "=" + ec.getArgument(args,sc.FILENAME) + "&" +
                    sc.REQNUMBER + "=" + ec.getArgument(args,sc.REQNUMBER) + "&" +
                    sc.PRODUCT + "=" + ec.getArgument(args,sc.PRODUCT) + "&" +
                    MnuConstants.MENUNO + "=" + menuNo + "&" +
                    MnuConstants.MVERSION + "=" + mVersion));
            } // if (version.equals("progress")) {
            if (screen.equals("load") && version.equals("load")) {
                hd.addMetaInfo(new MetaInfo("refresh",
                    "15;URL=" + sc.MRN_APP + "?" +
                    sc.SCREEN + "=load&" +
                    sc.VERSION + "=progress&" +
                    sc.SESSIONCODE + "=" + ec.getArgument(args,sc.SESSIONCODE) + "&" +
                    sc.USERTYPE + "=" + ec.getArgument(args,sc.USERTYPE) + "&" +
                    sc.FILENAME + "=" + ec.getArgument(args,sc.FILENAME) + "&" +
                    MnuConstants.MENUNO + "=" + menuNo + "&" +
                    MnuConstants.MVERSION + "=" + mVersion));
            } // if (screen.equals("load") && version.equals("load"))
            if (screen.equals("extract") && version.equals("extract")) {
                hd.addMetaInfo(new MetaInfo("refresh",
                    "15;URL=" + sc.MRN_APP + "?" +
                    sc.SCREEN + "=extract&" +
                    sc.VERSION + "=progress&" +
                    sc.USERTYPE + "=" + ec.getArgument(args,sc.USERTYPE) + "&" +
                    sc.SESSIONCODE + "=" + ec.getArgument(args,sc.SESSIONCODE) + "&" +
                    sc.FILENAME + "=" + ec.getArgument(args,sc.FILENAME) + "&" +
                    sc.REQNUMBER + "=" + ec.getArgument(args,sc.REQNUMBER) + "&" +
                    "pursula=dummy" + "&" +
                    MnuConstants.MENUNO + "=" + menuNo + "&" +
                    MnuConstants.MVERSION + "=" + mVersion));
            } // if (screen.equals("extract") && version.equals("extract"))
            if (screen.equals("product") && version.equals("product")) {
                hd.addMetaInfo(new MetaInfo("refresh",
                    "15;URL=" + sc.MRN_APP + "?" +
                    sc.SCREEN + "=product&" +
                    sc.VERSION + "=progress&" +
                    sc.USERTYPE + "=" + ec.getArgument(args,sc.USERTYPE) + "&" +
                    sc.SESSIONCODE + "=" + ec.getArgument(args,sc.SESSIONCODE) + "&" +
                    sc.FILENAME + "=" + ec.getArgument(args,sc.FILENAME) + "&" +
                    sc.PRODUCT + "=" + ec.getArgument(args,sc.PRODUCT) + "&" +
                    MnuConstants.MENUNO + "=" + menuNo + "&" +
                    MnuConstants.MVERSION + "=" + mVersion));
            } // if (screen.equals("product") && version.equals("product"))
        } // if (!timeout)


        // only continue if the user has not timed out
        // if user has timed out - the page will refresh to a logon screen.
        if (!timeout) {

            // add the top menu bar
            bd.addItem(new menusp.MnuMenuTopFrame(args, args2));
            bd.addItem("\n<hr>\n");

            // the body of the 'menu'
            if (screen.equals("extract")) {
                if (version.equals("preextract")) {
                    bd.addItem(new ExtractMRNSelectFrame(args));
                } else if (version.equals("extract")) {
                    bd.addItem(new ExtractMRNFrame(args));
                } else if (version.equals("progress")) {
                    bd.addItem(new ExtractProgressFrame(args, "mrn"));
                } // else if (version.equals("extract"))

            } else if (screen.equals("load")) {
                String loadType =                                       // ub01
                    ec.getArgument(args, sc.LOADTYPE).toLowerCase();    // ub01
                if (version.equals("getfile")) {
                    bd.addItem(new LoadMRNSelectFileFrame(args));
                } else if (version.equals("preload")) {
                    if (loadType.equals("water") || loadType.equals("waterwod")) { // ub01
                        bd.addItem(new PreLoadMRNWatFrame(args));
                    } else if (loadType.equals("currents")) {           // ub01
                        bd.addItem(new PreLoadMRNCurFrame(args));       // ub01
                    } else if (loadType.equals("sediment")) {           // ub01
                        bd.addItem(new PreLoadMRNSedFrame(args));       // ub01
                    } // if (loadType.equals("water")                   // ub01
                } else if (version.equals("load")) {
                    if (loadType.equals("water") || loadType.equals("waterwod")) {  // ub01
                        bd.addItem(new LoadMRNWatFrame(args));
                    } else if (loadType.equals("currents")) {           // ub01
                        bd.addItem(new LoadMRNCurFrame(args));          // ub01
                    } else if (loadType.equals("sediment")) {           // ub01
                        bd.addItem(new LoadMRNSedFrame(args));          // ub01
                    } // if (loadType.equals("water")                   // ub01
                } else if (version.equals("progress")) {
                    bd.addItem(new LoadMRNProgressFrame(args));
                } else if (version.equals("loglist")) {
                    bd.addItem(new LoadMRNLogListFrame(args));
                } else if (version.equals("logdetail")) {
                    bd.addItem(new LoadMRNLogDetailFrame(args));
                //} else if (version.equals("remove")) {
                //    bd.addItem(new LoadMRNRemoveDataFrame(args));
                } else if (version.equals("reports")) {
                    bd.addItem(new LoadMRNReportsFrame(args));
                } // else if (version.equals("preload"))

            } else if (screen.equals("product")) {
                if (version.equals("getfile")) {
                    bd.addItem(new ProductMRNGetFileFrame(args));
                } else if (version.equals("getparms")) {
                    bd.addItem(new ProductMRNGetParmsFrame(args));
                } else if (version.equals("product")) {
                    bd.addItem(new ProductMRNFrame(args));
                } else if (version.equals("progress")) {
                    bd.addItem(new ProductProgressFrame(args, "mrn"));
                } // if (version.equals("getfile"))

            } else if (screen.equals("admin")) {
                if (version.equals("speeds")) {
                    bd.addItem(new AdminCalcSpeeds(args));
                } else if (version.equals("displst")) {
                   bd.addItem(new AdminDisplStationId(args));
                } else if (version.equals("confirm")) {
                   bd.addItem(new AdminDisplStation(args));
                } else if (version.equals("delstn")) {
                    bd.addItem(new AdminDelStation(args));
                } else if (version.equals("delinv")) {
                   bd.addItem(new AdminDelInv(args));
                } else if (version.equals("subdes")) {
                   bd.addItem(new AdminChangeSubdes(args));
                }// if (version.equals("speeds"))

            } // if (screen.equals("admin"))

            // add the bottom menu bar
            bd.addItem("\n<hr>\n");
            bd.addItem(new common2.BottomFrame(args2));
        } // if (!timeout)

        DbAccessC.close();
        return hp;

    } // SadcoMRN constructor

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    /*
    public static void main(String args[]) {

        try {
            SadcoMRN local= new SadcoMRN(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main
    */

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

} // class SadcoMRN
