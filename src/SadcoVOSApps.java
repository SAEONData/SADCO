import common2.*;
import sadco.*;
import menusp.*;
import java.io.*;


/**
 * An Application class that manages the SADCO VOS application.
 *
 * @author  001102 - SIT Group
 * @version
 * 001102 - Ursula von St Ange - create class                               <br>
 * 020627 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020722 - Ursula von St Ange - add timeout feature                        <br>
 * 030725 - Mario August - Use SadcoCUR as base - change to application     <br>
 */
public class SadcoVOSApps { //extends HttpServlet {

    // values for args2 variable
    static String application = "SadcoMenu";  // menu application
    static String owner       = "sadco";      // Oracle schema owner
    static String title       = "SADCO";      // title in the top bar
    static String callType    = "non-menu";   // indicate non-menu app
    static String date        = "27 June 2002"; // last update date

    // values for in this application
    static String thisClass   = "SadcoVOSApps"; // class name
    static String headTitle   = "SadcoVOSApps"; // title in head part of page
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

    public SadcoVOSApps (String args[]) {
    //public HtmlPage doWork (String args[]) {

        // the menu tables belong to user dba2, but there are synonyms for
        // them in the sadco schema.
        menusp.Tables.userid = owner;
        menusp.Tables.password = password;

        // get the parameters - must always be argumemnts
        String screen = ec.getArgument(args, sc.SCREEN);
        String version = ec.getArgument(args, sc.VERSION);
        // for the top menu bar
        String menuNo = ec.getArgument(args, MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, MnuConstants.MVERSION);

        // complete args2 for top bar
        args2[args2.length-1] = MnuConstants.MVERSION + "=" + mVersion;

            // the body of the 'menu'
        if (screen.equals("extract")) {
            //if (version.equals("extract")) {
            //    //bd.addItem(new ExtractVOSFrame(args));
            //    new ExtractVOSFrame(args);
            //} else
            if (version.equals("doextract")) {
                //bd.addItem(new ExtractVOSData(args));
                new ExtractVOSData(args);
            }

        } //if (screen.equals("extract")) {


        if (screen.equals("load")) {
            if (version.equals("doload")) {
                new LoadVOSData2(args);
//                new LoadVOSData(args);
            //} else if (version.equals("update")) { //if (version.equals("doload")) {
            //    new LoadVOSDataUpd(args);
            }

        } else if (screen.equals("check")) {
            if (version.equals("valid")) {
               //System.out.println("<br> screen "+screen);
               //System.out.println("<br> version "+version);
               //new CheckVOSData(args);
               new CheckVOSData2(args); // all four databases where scan with this prog.
            } else if (version.equals("reject")) {
               new CheckMainReject(args);
            } else if (version.equals("vector")) {
               new CheckMainReject2(args);
            }

        } //if (version.equals("getfile")) {

        DbAccessC.close();
        //return hp;

    } // SadcoVOSApps constructor

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */

    public static void main(String args[]) {
        try {
            SadcoVOSApps local= new SadcoVOSApps(args);
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

} // class SadcoVOSApps