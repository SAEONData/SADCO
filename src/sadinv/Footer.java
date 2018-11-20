package sadinv;

import oracle.html.*;

/**
 * This class generates the Footer section of the sadco inventory.
 * It is called by SadInv directly.
 *
 * @author 20090104 - SIT Group.
 * @version
 * 20090104 - Ursula von St Ange - create program                       <br>
 */
public class Footer extends CompoundItem {

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommonPC ec = new common.edmCommonPC();

    public Footer (String args[]) {

        // create the email-to link
        Link email = new Link ("mailto:data@ocean.gov.za", "Webmaster");
        email.setItal();

        // create the csir copyright, add the email link
        SimpleItem csir = new SimpleItem(
            "Copyright 2008 &copy; All Rights Reserved - " +
            "Updated: January 2009<br>" + email.toHTML());
        csir.setFontColor(Color.gray);
        csir.setFontSize(1);
        csir.setCenter();

        this.addItem(SimpleItem.HorizontalRule);
        this.addItem(csir);
    }  //  public Footer (String pgm_name)


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("Footer local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new Footer(args));
            bd
                .setActivatedLinkColor   ("#000088")
                .setFollowedLinkColor    ("#663366")
                .setUnfollowedLinkColor  ("#666666")
                ;
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            ec.processErrorStatic(e, "InvListDates", "Constructor", "");
        } // try-catch
        // close the connection to the database
        //common2.DbAccessC.close();

    } // main


} // class Footer