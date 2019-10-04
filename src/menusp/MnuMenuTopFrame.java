package menusp;

import common2.*;
import oracle.html.*;

/**
 * Displays a Bar with a title in the format '<title>: ...'.
 *
 * @author 000424 - SIT Group
 * @version
 * 000424 - Ursula von St Ange - create class<br>
 * 020527 - Ursula von St Ange - changed ptitle to pttitle, added
 *                               "Back to:" before menu buttons, made
 *                               top bar bold and font size 4, took out
 *                               the target in the Form statement.
 *                               Added the version parameter to the menu
 *                               buttons.  The last gets the same
 *                               version as the one it came in with,
 *                               all the others get a version of menu.  <br>
 * 020625 - Ursula von St Ange - If this class is called from - non-menu
 *                               application, the pversion come through
 *                               args2 as pmversion.                    <br>
 * 020625 - Ursula von St Ange - pscreen not used by non-frames - take out<br>
 * 020625 - Ursula von St Ange - Add callType for pmversion             <br>
 * 100210 - Ursula von St Ange - don't want buttons if inventory user
 *                               (type 0) (ub01)                        <br>
 */
public class MnuMenuTopFrame extends CompoundItem {

    /** contains the method to parse URL-type arguments */
    common.edmCommon ec = new common.edmCommon();

    //boolean dbg = true;
    boolean dbg = false;

    // parameters
    String version = "menu";
    String menuNo = "";
    String userType = "";
    String sessionCode = "";

    // 'local' paramaters
    String title = "";
    String application = "";
    String callType = "";


    /**
     * Constructor called from an application-type class
        * The <b>args</b> variable must contain the following parameters: <br>
     * MnuConstants.VERSION - values = menu / form<br>
     * MnuConstants.MENUNO - the menu-no of the menu to be generated<br>
     * MnuConstants.USERTYPE - values = 1 (super-user) / 2 (normal user)<br>
     * MnuConstants.SESSIONCODE - the current session code - used by the
     *    SADCO applications<p>
     * The <b>args2</b> variable must contain the following parameters: <br>
     * MnuConstants.OWNER - the owner of the application<br>
     * MnuConstants.APPLICATION - the calling application<br>
     * MnuConstants.CALLTYPE - only used if the calling application is not non-menu type
     *                         application, and then it's value can be anyting<p>
     * @param args  array of URL-like name=value parameter pairs - from the command line
     * @param args2 array of URL-like name=value parameter pairs - must be defined in
     *              the calling class
     */
    public MnuMenuTopFrame(String args[], String[] args2) {

        // get the parameters off the command line
        version = ec.getArgument(args, MnuConstants.VERSION);
        menuNo = ec.getArgument(args, MnuConstants.MENUNO);
        userType = ec.getArgument(args, MnuConstants.USERTYPE);
        sessionCode = ec.getArgument(args, MnuConstants.SESSIONCODE);
        if (dbg) System.out.println(menuNo);

        // get the locally set parameters
        title = ec.getArgument(args2, MnuConstants.TITLE) + " Menu System";
        application = ec.getArgument(args2, MnuConstants.APPLICATION);
        callType = ec.getArgument(args2, MnuConstants.CALLTYPE);

        // check for version - if this class was not called from a menu application
        // (e.g. EdmMenu) but from another (e.g. EdmWaves), it comes accross in args2
        // as pmversion
        if (!"".equals(callType)) version = ec.getArgument(args2, MnuConstants.MVERSION);

        // array to keep menu-no's of parent menus
        String[] childOf = new String[10];
        String[] text = new String[10];
        int cntParents = 0;

        // get the titles and menu numbers of previous menus
        if (!menuNo.equals("")) {
            childOf[cntParents++] = menuNo; // added 010712
            String menuNoStr = menuNo;

            // do until main menu reached
            while (!menuNoStr.equals("") && !menuNoStr.equals("0")) {
                MnuMenuItems[] mnuMenuItems =
                    new MnuMenuItems().get(MnuMenuItems.CODE+"="+menuNoStr);

                if (dbg) System.out.println("<br>" + mnuMenuItems[0]);
                menuNoStr = mnuMenuItems[0].getChildOf("");
                if (dbg) System.out.println("<br>menuNoStr = " + menuNoStr);

                // keep the menu numbers and text for the title and return buttons
                text[cntParents] = mnuMenuItems[0].getText("");
                childOf[cntParents++] = menuNoStr;

            } // while !menuNoStr
        } // if menuNo
        //text[cntParents] = mnuMenuItems[0].getText("");
        //childOf[cntParents++] = menuNoStr;
        if (dbg) System.out.println("<br> cntParents = " + cntParents);

        // create the title
        for (int i = cntParents-1; i > 0 ; i--) {
            if (dbg) System.out.println ("<br> text[i] = " + text[i] + " " + i);
            title = title + ": " + text[i];
        } // for i

        // add the title to the class itself
        this.addItem(new Heading(1, new SimpleItem(title).setBold().setFontSize(4).toHTML()));

        // only create if usertype is not 0, i.e. not inventory user
        if (!"0".equals(userType)) {                                    //ub01
            // create the button table
            DynamicTable formTable = new DynamicTable(cntParents);
            formTable.setFrame(ITableFrame.VOLD);
            formTable.setRules(ITableRules.NONE);
            formTable.setBorder(0);
            TableRow row = new TableRow();

            // create the return buttons
            if (!menuNo.equals("")) {
                row.addCell(new TableHeaderCell(new SimpleItem("Back to: ")));
                // main menu button
                Form form = new Form("GET", application);
                form.addItem(new Submit("", "Main Menu").setCenter());
                //form.addItem(new Hidden(MnuConstants.SCREEN, screen));
                form.addItem(new Hidden(MnuConstants.VERSION, "menu"));
                form.addItem(new Hidden(MnuConstants.USERTYPE, userType));
                form.addItem(new Hidden(MnuConstants.SESSIONCODE, sessionCode));
                row.addCell(new TableDataCell(form));
                // sub-menu buttons
                for (int i = cntParents-1; i > 0 ; i--) {
                    form = new Form("GET", application);
                    form.addItem(new Submit("", text[i]).setCenter());
                    //form.addItem(new Hidden(MnuConstants.SCREEN, screen));
                    // the last one must have the version that comes in
                    if (i > 1) {
                        form.addItem(new Hidden(MnuConstants.VERSION, "menu"));
                    } else {
                        form.addItem(new Hidden(MnuConstants.VERSION, version));
                    } // if (i > 1)
                    form.addItem(new Hidden(MnuConstants.MENUNO, childOf[i-1]));
                    form.addItem(new Hidden(MnuConstants.USERTYPE, userType));
                    form.addItem(new Hidden(MnuConstants.SESSIONCODE, sessionCode));
                    if (dbg) System.out.println ("<br> childOf[i] = " + childOf[i] + " " + i +
                        " " + text[i]);
                    row.addCell(new TableDataCell(form));
                } // for i
                formTable.addRow(row);
            } // if (!menuNo.equals(""))

            // add the buttons to the class itself
            this.addItem(formTable.setCenter());
        } // if (!"0".equals(userType))

    } // MnuMenuTopFrame constructor

} // class MnuMenuTopFrame









