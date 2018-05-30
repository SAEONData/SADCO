import sadco.ExtractCURDepthsData;
import sadco.ExtractMRNCurData;
import sadco.ExtractMRNCurDataODV;
import sadco.ExtractMRNCurDataComponent;            //ub01
import sadco.ExtractMRNCurDataODVComponent;         //ub01
import sadco.ExtractMRNPhysNutData;
import sadco.ExtractMRNPhysNutDataODV;
import sadco.ExtractMRNSedChmData;
import sadco.ExtractMRNSedPhyData;
import sadco.ExtractMRNSedPolData;
import sadco.ExtractMRNTisPolData;
import sadco.ExtractMRNWatChmData;
import sadco.ExtractMRNWatPolData;
import sadco.ExtractVOSData;
import sadco.ExtractWAVData;
import sadco.ExtractWETData;
import sadco.SadConstants;
import sadinv.SadInvCommon;
import sadinv.SendEmail;

/**
 * An application that calls all the other extraction programs, and sends
 * an email when they have completed.
 *
 * @author  091210 - SIT Group
 * @version
 * 091210 - Ursula von St Ange - create class                           <br>
 * 121106 - Ursula von St Ange - add odv currents                       <br>
 * 150319 - Ursula von St Ange - add current components (ub01)          <br>
 */
public class SadInvApps {


    // values for in this application
    static String thisClass   = "SadInvApps"; // class name

    // contains the method to parse URL-type arguments
    static common.edmCommonPC ec = new common.edmCommonPC();
    // url parameters names & applications
    static SadInvCommon sc = new SadInvCommon();
    static SadConstants sc2 = new SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    String screen = "";
    String dataType = "";
    int    extrType = 0;    // hydro

    /**
     * The only constructor.
     * @param args  array of URL-like name=value parameter pairs - from the command line
     */
    public SadInvApps (String args[]) {

        // get the parameters - must always be argumemnts
        String screen   = ec.getArgument(args, sc.SCREEN).toLowerCase();
        String dataType = ec.getArgument(args, sc.DATATYPE);

        if (dbg) System.out.println("SadInvApps: screen = " + screen);
        //if (dbg) System.out.println("SadInvApps: version = " + version);

        // the body of the 'menu'
        if (screen.equals("extract")) {

            if ("1".equals(dataType)) {

                int extrType = Integer.parseInt(ec.getArgument(args,sc.EXTRTYPE));

                switch(extrType) {
                    case 0: new ExtractMRNPhysNutDataODV(args); break;
                    case 1: new ExtractMRNPhysNutData(args); break;
                    case 2: new ExtractMRNWatChmData(args); break;
                    case 3: new ExtractMRNWatPolData(args); break;
                    case 4: new ExtractMRNSedPhyData(args); break;
                    case 5: new ExtractMRNSedChmData(args); break;
                    case 6: new ExtractMRNSedPolData(args); break;
                    case 7: new ExtractMRNTisPolData(args); break;
                    case 8: new ExtractMRNCurDataODV(args); break;
                    case 9: new ExtractMRNCurData(args); break;
                    case 10: new ExtractMRNCurDataODVComponent(args); break;//ub01
                    case 11: new ExtractMRNCurDataComponent(args); break;   //ub01
                } // switch(extrType)

            } else if ("2".equals(dataType) || "6".equals(dataType)) {

                 new ExtractCURDepthsData(args);

             } else if ("3".equals(dataType)) {

                 new ExtractWETData(args);

             } else if ("4".equals(dataType)) {

                 new ExtractWAVData(args);

             } else if ("7".equals(dataType)) {

                 new ExtractVOSData(args);

             } // if ("1".equals(dataType))

             SendEmail mail = new SendEmail(args);

        }// if (screen.equals("extract"))

        common2.DbAccessC.close();

    } // SadInvApps constructor

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */

    public static void main(String args[]) {

        try {
            SadInvApps local= new SadInvApps(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "Constructor", "");
            // close the connection to the database
        } // try-catch
        common2.DbAccessC.close();

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

} // class SadInvApps
