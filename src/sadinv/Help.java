package sadinv;

import oracle.html.*;

/**
 * This class contains help that pops up on the user's request
 *
 * SadInv
 * pscreen = help
 *
 * @author 20091202 - SIT Group.
 * @version
 * 20091202 - Ursula von St Ange - create program                       <br>
 */
public class Help extends CompoundItem {

    /** For debugging code: true/false */
    static boolean dbg = false;
    //static boolean dbg = true;
    static boolean dbg2 = false;
    //static boolean dbg2 = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    /**
     * main constructor
     */
    public Help (String args[]) {

        // get the argument values
        //String version  = ec.getArgument(args,sc.VERSION);
        //if (dbg) System.out.println("<br>" + thisClass + " version = " + version);

        // create the form with the button  to close the window
        // create the login form
        Form form = new Form("","");
        form.addItem(new Submit("\" onclick=\"window.close()","Close Window"));

        // create the container to hook everything on to
        this.addItem(SimpleItem.LineBreak)
            .addItem("Help on the SADCO Inventory")
            .addItem(SimpleItem.LineBreak)
            .addItem("<b>Extractions</b>")
            .addItem("<ul>")
            .addItem("<li>Extract online - smaller than 10,000 records, " +
                "and can be extracted directly</li>")
            .addItem("<li>Request offline - more than 10,000 records, " +
                "and a request will to be submitted to the data centre</li>")
            .addItem("</ul>")
            .addItem(SimpleItem.LineBreak)
            .addItem(form);

    } // constructor Help


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("Help local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new Help(args));
            bd
                .setActivatedLinkColor   ("#000088")
                .setFollowedLinkColor    ("#663366")
                .setUnfollowedLinkColor  ("#666666")
                ;
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            ec.processErrorStatic(e, "Help", "Constructor", "");
        } // try-catch
        // close the connection to the database
        //common2.DbAccessC.close();

    } // main

} // class Help