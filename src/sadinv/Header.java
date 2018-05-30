package sadinv;

import oracle.html.*;

/**
 * This class generates the header section of the sadco inventory.
 * It is called by SadInv directly.
 *
 * @author 20090104 - SIT Group.
 * @version
 * 20090104 - Ursula von St Ange - create program                       <br>
 */
public class Header extends CompoundItem {

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommonPC ec = new common.edmCommonPC();

    public Header (String args[]) {

        Image banner = new Image
            ("http://fred.csir.co.za/sadco-img/banner.jpg",
            "banner.jpg",
            IVAlign.TOP, false);

        this.addItem(banner);
        this.addItem(new SimpleItem(
            "<br>Click on one of the buttons below to search for a survey:<br>"));
        this.addItem(SimpleItem.HorizontalRule);

    }  //  public Header (String pgm_name)


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("Header local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new Header(args));
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


} // class Header