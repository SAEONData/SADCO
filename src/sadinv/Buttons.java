package sadinv;

import oracle.html.*;

/**
 * This class generates the buttons section of the sadco inventory.
 *
 * SadInv
 * pscreen = buttons
 *
 * @author 20090104 - SIT Group.
 * @version
 * 20090104 - Ursula von St Ange - create program                       <br>
 */
public class Buttons extends CompoundItem {

    /** For debugging code: true/false */
    static boolean dbg = false;
    //static boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    //url's for button images
/*
    String src[] = {
        "http://sadco.int.ocean.gov.za/sadco-img/button_surveys.jpg",
        "http://sadco.int.ocean.gov.za/sadco-img/button_dates.jpg",
        "http://sadco.int.ocean.gov.za/sadco-img/button_institutes.jpg",
        "http://sadco.int.ocean.gov.za/sadco-img/button_scientists.jpg",
        "http://sadco.int.ocean.gov.za/sadco-img/button_platforms.jpg",
        "http://sadco.int.ocean.gov.zaa/sadco-img/button_areas.jpg",
        "http://sadco.int.ocean.gov.za/sadco-img/button_datatypes.jpg",
        "http://sadco.int.ocean.gov.za/sadco-img/button_projects.jpg"};
*/

    // root url for button jpgs
    static String ROOT = "http://sadco.int.ocean.gov.za/sadco-img/button_";
    static String EXT = ".jpg";

    // alternate text and titles for buttons
    String alt[] = {sc.SURVEYS, sc.DATES, sc.INSTITUTES, sc.SCIENTISTS,
        sc.PLATFORMS, sc.AREAS, sc.DATATYPES, sc.PROJECTS};


    /**
     * main constructor
     */
    public Buttons (String args[]) {

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String sessionCode  = ec.getArgument(args,sc.PSC);
        if (dbg) System.out.println("<br>" + thisClass +
            ": sessionCode = " + sessionCode);

        // the table to contain the buttons
        DynamicTable buttons = new DynamicTable(1);
        buttons
            .setBorder(0)
            .setRules(ITableRules.NONE)
            .setFrame(ITableFrame.VOLD)
            ;

        // add the rows with buttons to the table
        for (int i = 0; i < alt.length; i++) {

            // create the image tag - do it manually because oracle's
            // version does not have a 'title' option
            String image = "<img title=\"search " + alt[i] +
                "\" alt=\"" + alt[i] +
                "\" src=\"" + ROOT + alt[i] + EXT +
                "\" border=\"0\">";// +
                //"height=\"31\" width=\"150\">";

            // create the url to go to
            String linkText = sc.APP + "?pscreen=" + alt[i] +
                "&psc=" + sessionCode + "\" target=\"_body";

            // add the rows to the table
            buttons.addRow(ec.cr1ColRow(new Link(linkText,image).toHTML()));

        } // for (int i = 0; i < alt.length; i++)

        this.addItem(buttons);
    }  //  public Buttons (String pgm_name)


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("Buttons local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new Buttons(args));
            bd
                .setActivatedLinkColor   ("#000088")
                .setFollowedLinkColor    ("#663366")
                .setUnfollowedLinkColor  ("#666666")
                ;
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            ec.processErrorStatic(e, "Buttons", "Constructor", "");
        } // try-catch
        // close the connection to the database
        //common2.DbAccessC.close();

    } // main

} // class Buttons