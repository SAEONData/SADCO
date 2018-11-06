
// change the package name
package common2;

//import common2.*;
import oracle.html.*;

/**
 * A full description of what the class is all about.
 *
 * @author  2002/02/20  SIT Group
 * @version
 * 2002/02/20 - Ursula von St Ange - created class<br>
 * 2002/02/21 - Ursula von St Ange - changed for ...<br>
 * 2002/02/22 - Ursula von St Ange - changed for ...<br>
 */
public class DummyFrame extends CompoundItem {

    /** A general purpose class used to extract the url-type parameters*/
    common.edmCommon ec = new common.edmCommon();

    /** used for debugging - manages output statements */
    boolean dbg = false;
    //boolean dbg = true;

    /** the class name */
    String thisClass = this.getClass().getName();

    /* some description for the argument */
    String argument1;

    /**
     * Only constructor.  Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public DummyFrame(String args[]) {

        try {
            DummyFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, thisClass, "constructor", "more text");
        } // try-catch

    } // DummyFrame constructor

    /**
     * does the actual work
     * @param args the string array of url-type name-value parameter pairs
     */
    void DummyFrameActual(String args[]) {

        // get the argument values
        getArgParms(args);

        // create main container
        Container con = new Container();
        con.addItem(new SimpleItem("<b>" + thisClass + " Class</b>").setCenter());
        con.addItem(new SimpleItem("<br>argument1 = " + argument1).setCenter());
        this.addItem(con);
/*
        // or add to class itself
        this.addItem(new SimpleItem("<b>" + thisClass + " Class</b>").setCenter());
        this.addItem(new SimpleItem("<br>"));
        this.addItem(new SimpleItem("<br>argument1 = " + argument1).setCenter());
*/
    } // constructor DummyFrame


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        argument1     = ec.getArgument(args,"pargument1");
        if (dbg) System.out.println ("<br>argument1 = " + argument1);
    }   //  public void getArgParms()


} // class DummyFrame









