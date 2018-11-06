package sadco;

import oracle.html.*;
import common2.*;

/**
 * display a drop-down list for administrator to update a user.
 *
 * Sadco
 * screen.equals("user")
 * version.equals("select")
 *
 * @author 011003 - SIT Group
 * @version
 * 011003 - Ursula von St Ange - create class<br>
 * 020528 - Ursula von St Ange - update for non-frame menus<br>
 * 020528 - Ursula von St Ange - update for exception catching<br>
 */
public class UserSelectFrame extends UserFrame {

    boolean dbg = false;
    //boolean dbg = true;

    /** some common functions */
    //common.edmCommon ec = new common.edmCommon(); - inherited from LoginFrame
    /** url parameters names & applications */
    //sadco.SadConstants sc = new sadco.SadConstants(); - inherited from LoginFrame

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public UserSelectFrame (String args[]) {

        try {
            UserSelectFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // UserSelectFrame constructor

    /**
     * Creates an entry form with user drop-down list
     * @param args the string array of url-type name-value parameter pairs
     */
    void UserSelectFrameActual(String args[]) {

        // get URL paramter
        String session = ec.getArgument(args, sc.SESSIONCODE);
        // for the top menu bar
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // add header
        String header = "<font size=+2 color=blue>" +
            "Change Other User Profile" + "</font><br><br>";
        this.addItem(new SimpleItem(header).setCenter().setBold());

        // Create table
        DynamicTable tab = new DynamicTable(2);
        tab.setFrame(ITableFrame.VOLD);
        tab.setRules(ITableRules.NONE);
        tab.setBorder(0);

        // write help info
        ec.writeHelpInfo(tab);

        // existing users
        Select userSelect = createUserSelect(
            sc.USERID, SadUsers.USER_TYPE + "!=" + sc.UTDATAS);
        tab.addRow(ec.cr2ColRow("Existing Users:", userSelect));

        // create the form
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST", sc.LOGIN_USER_APP, target);
        form.addItem(new Hidden(sc.SCREEN, "user"));
        form.addItem(new Hidden(sc.VERSION, "user"));
        form.addItem(new Hidden(sc.ACTION, "updatesu"));
        form.addItem(new Hidden(sc.SESSIONCODE, session));
        // for the menu system
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        form.addItem(tab);
        form.addItem(SimpleItem.LineBreak);
        form.addItem(new Submit("submit", "Go!")).setCenter();

        this.addItem(form.setCenter());

    } // UserSelectFrameActual

} // UserSelectFrame class
