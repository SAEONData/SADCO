import sadco.*;
//import menusp.*;
//import java.io.*;

/**
 * An Application class that manages the SADCO Marine application.
 *
 * @author  001102 - SIT Group
 * @version
 * 001102 - Ursula von St Ange - create class                           <br>
 * 020628 - Ursula von St Ange - update for non-frame menus             <br>
 * 020722 - Ursula von St Ange - add timeout feature                    <br>
 * 040609 - Ursula von St Ange - add currents extraction (ub01)         <br>
 * 040609 - Ursula von St Ange - add currents loading (ub02)            <br>
 * 121106 - Ursula von St Ange - add currents odv extraction (ub03)     <br>
 * 150324 - Ursula von St Ange - add underway current components (ub04) <br>
 */
public class SadcoMRNApps {


    // values for in this application
    static String thisClass   = "SadcoMRNApps"; // class name

    // contains the method to parse URL-type arguments
    static common.edmCommonPC ec = new common.edmCommonPC();
    // url parameters names & applications
    sadco.SadConstants sc = new sadco.SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

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

    } //public void doWorkS (HttpServletRequest req, HttpServletResponse res) {
    */

    //public HtmlPage doWork (String args[]) {
    public SadcoMRNApps (String args[]) {

        // get the parameters - must always be argumemnts
        String screen = ec.getArgument(args, sc.SCREEN).toLowerCase();
        String version = ec.getArgument(args, sc.VERSION).toLowerCase();

        //if (dbg)
        System.out.println("SadcoMRNApps: screen = " + screen);
        //if (dbg)
        System.out.println("SadcoMRNApps: version = " + version);


        // the body of the 'menu'
        if (screen.equals("extract")) {
            if (version.equals("doextract")) {

                String extrType =
                    ec.getArgument(args, sc.EXTRTYPE).toLowerCase();
                //if (dbg)
                System.out.println("SadcoMRNApps: extrType = " + extrType);

                if (extrType.equals("physnut")) {
                    new ExtractMRNPhysNutData(args);
                } else if (extrType.equals("physnutodv")) {
                    new ExtractMRNPhysNutDataODV(args);
                } else if (extrType.equals("watpol")) {
                    new ExtractMRNWatPolData(args);
                } else if (extrType.equals("sedpol")) {
                    new ExtractMRNSedPolData(args);
                } else if (extrType.equals("tispol")) {
                    new ExtractMRNTisPolData(args);
                } else if (extrType.equals("current")) {                // ub01
                    new ExtractMRNCurData(args);                        // ub01
                } else if (extrType.equals("currentodv")) {             // ub03
                    new ExtractMRNCurDataODV(args);                     // ub03
                } else if (extrType.equals("currentcomp")) {            // ub04
                    new ExtractMRNCurDataComponent(args);               // ub04
                } else if (extrType.equals("currentodvcomp")) {         // ub04
                    new ExtractMRNCurDataODVComponent(args);            // ub04
                } else if (extrType.equals("station")) {
                    new ExtractMRNStationData(args);
                } // if (extrType.equals("physnut"))

            } // if (version.equals("doextract"))

        } else if (screen.equals("load")) {
            if (version.equals("doload")) {
                String loadType =                                       // ub02
                    ec.getArgument(args, sc.LOADTYPE).toLowerCase();    // ub02
                if (dbg) System.out.println("SadcoMRNApps: loadType = " + loadType);
                    if (loadType.equals("water") || loadType.equals("waterwod")) { // ub02
                    new LoadMRNWatData(args);                           // ub02
                } else if (loadType.equals("currents")) {               // ub02
                    new LoadMRNCurData(args);                           // ub02
                } else if (loadType.equals("sediment")) {               // ub02
                    new LoadMRNSedData(args);                           // ub02
                } // if (loadType.equals("water")                       // ub02
            } else if (version.equals("reports")) {
                new LoadMRNReports(args);
            } // if (version.equals("doload"))
        }// if (screen.equals("extract"))

        common2.DbAccessC.close();

    } // SadcoMRNApps constructor

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */

    public static void main(String args[]) {

        try {
            SadcoMRNApps local= new SadcoMRNApps(args);
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

} // class SadcoMRNApps
