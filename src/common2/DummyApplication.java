import common2.*;
import oracle.html.*;
//import dummyapplication.*;

/**
 * A full description of what the application is all about.
 *
 * @author  2002/02/20 - SIT Group
 * @version
 * 2002/02/20 - Ursula von St Ange - created class<br>
 * 2002/02/21 - Ursula von St Ange - changed for ...<br>
 * 2002/02/22 - Ursula von St Ange - changed for ...<br>
 */
public class DummyApplication {

    /** A general purpose class used to extract the url-type parameters*/
    static common.edmCommon ec = new common.edmCommon();

    boolean dbg = false;
    //boolean dbg = true;

    /** the application name */
    static String thisClass = "DummyApplication";

    // arguments
    String argument1;

    /**
     * Gets the values for the pscreen and pversion parameters, and then
     * decides which class in the dummyapplication package to call.
     */
    public DummyApplication(String args[]) {

        // get the argument values
        getArgParms(args);

        HtmlHead hd = new HtmlHead(thisClass);
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        // add metatags if neccessary
        //if (true) {
        //    hd.addMetaInfo(new MetaInfo("refresh", "60"));
        //}

        hp.printHeader();

        // add stuff to this class
        bd.addItem(new SimpleItem("<b>" + thisClass + " Class</b>").setCenter());
        bd.addItem(new SimpleItem("<br>argument1 = " + argument1).setCenter());
        bd.addItem(new SimpleItem("<p>"));

        bd.addItem(new common2.DummyFrame(args));

        hp.print();
        common2.DbAccessC.close();

    } // DummyApplication constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param args the string array of url-type name-value parameter pairs
     */
    void getArgParms(String args[])   {
        argument1     = ec.getArgument(args,"pargument1");
        if (dbg) System.out.println ("<br>argument1 = " + argument1);
    } // getArgParms


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            DummyApplication local = new DummyApplication(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "constructor", "more text");
        } // try - catch

    } // main

} // class DummyApplication