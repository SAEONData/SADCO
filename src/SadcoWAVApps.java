import sadco.ExtractWAVData;
/**
 * An Application class that manages the SADCO Waves application.
 *
 * @author  080222 - SIT Group
 * @version
 * 080222 - Ursula von St Ange - create class using SadcoCURApps            <br>
 */
public class SadcoWAVApps {

    // values for in this application
    static String thisClass   = "SadcoWAVApps"; // class name

    // contains the method to parse URL-type arguments
    static common.edmCommonPC ec = new common.edmCommonPC();

    // url parameters names & applications
    sadco.SadConstants sc = new sadco.SadConstants();

    /**
     * The function that does all the work.
     */
    public SadcoWAVApps (String args[]) {

        // get the parameters - must always be argumemnts
        String screen = ec.getArgument(args, sc.SCREEN).toLowerCase();
        String version = ec.getArgument(args, sc.VERSION).toLowerCase();

        // call the relevant program
        if (screen.equals("extract")) {
            if (version.equals("doextract")) {
                new ExtractWAVData(args);
            } // if (version.equals("doextract"))
        } //if (screen.equals("extract")) {

        // make sure the database is closed
        common2.DbAccessC.close();

    } // SadcoWAVApps

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */

    public static void main(String args[]) {
        try {
            SadcoWAVApps local= new SadcoWAVApps(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "main", "");
        } // try-catch
        common2.DbAccessC.close();


    } // main

} // class SadcoWAVApps

