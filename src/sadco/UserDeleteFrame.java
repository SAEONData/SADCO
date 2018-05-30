package sadco;

import oracle.html.*;
import java.io.*;

/**
 * Creates a page which request the USERID or DATASET name to be deleted,
 * and then display a CONFIRM page with the final option to DELETE the user or
 * dataset.
 *
 * Sadco
 * screen.equals("user")
 * version.equals("delete")
 *
 * @author  001107 - SIT Group
 * @version
 * 001107 - Ursula von St Ange - create class<br>
 * 011004 - Ursula von St Ange - Add deletion of datasets
 * 020527 - Ursula von St Ange - update for non-frame menus<br>
 * 020527 - Ursula von St Ange - update for exception catching<br>
 */
public class UserDeleteFrame extends UserFrame {

    boolean dbg = false;
    //boolean dbg = true;

    /**
     * Empty constructor needed for inheritance
     */
    public UserDeleteFrame()  {
    }

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
     public UserDeleteFrame (String args[])  {

        try {
            UserDeleteFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // UserDeleteFrame constructor

   /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void UserDeleteFrameActual(String args[])  {

        // get the arguments
        String userid = ec.getArgument(args, sc.USERID);
        String action = ec.getArgument(args, sc.ACTION);
        String uds = action.substring(0,1);
        String userTxt = "User";
        if ("d".equals(uds)) userTxt = "Dataset";

        String message = "";
        String newAction = "";
        String buttonText = "";

        // add the header to the page
        String header = "<font size=+2 color=blue>Remove a " + userTxt +
            "</font><br><br>";
        this.addItem(new SimpleItem(header).setCenter().setBold());

        // decide on what to do
        if (action.equals("user")) {

            // just display the entry form
            newAction = "uconfirm";
            buttonText = "Go!";
            this.addItem(userMenuForm(args, newAction, buttonText, userid).setCenter());

        } else if (action.equals("dataset")) {

            // just display the entry form
            newAction = "dconfirm";
            buttonText = "Go!";
            this.addItem(userMenuForm(args, newAction, buttonText, userid).setCenter());

        } else if ("uconfirm".equals(action) || "dconfirm".equals(action)) {

            message = "<font color = red>Are you sure you want to delete " +
                "<br>" + userTxt + " <b>" + userid +
                "</b> from the Database?</font>";
            newAction =  uds + "delete";
            buttonText = "Yes!";

            this.addItem(new SimpleItem(message).setCenter());
            this.addItem(userMenuForm(args, newAction, buttonText, userid).setCenter());

        } else if ("udelete".equals(action) || "ddelete".equals(action)) {

            SadUsers user = new SadUsers();
            user.setUserid(userid);
            user.del();

            message = "<font color = green>" + userTxt + " <b>" + userid +
                "</b> successfully DELETED !!!!!</font>";
            this.addItem(new SimpleItem(message).setCenter());

            if ("udelete".equals(action)) {
                // rename the user's path to earmark it for deletion by the cron job
                // get the correct path name - used while debugging
                String rootPath = "";
                //if (ec.getHost().startsWith("morph")) {
                //    rootPath = sc.MORPH;
                //} else {
                //    rootPath = sc.LOCAL;
                //} // if host

                if (ec.getHost().startsWith(sc.HOST)) {
                    rootPath = sc.HOSTDIR;
                } else {
                    rootPath = sc.LOCALDIR;
                } // if (ec.getHost().startsWith(sc.HOST)) {


                File oldDir = new File(rootPath + userid);
                File delDir = new File(rootPath + userid + "DEL");
                boolean success = oldDir.renameTo(delDir);
            } // deletesu

        }// if (action.equals("delete")) {

    } // constructor with args

    /**
    *  Create a User Menu for the Delete User Page
    */
    public Form userMenuForm(
                String args[], String newAction, String buttonText, String userid){

        // for the top menu bar
        String sessionCode= ec.getArgument(args, sc.SESSIONCODE);
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // Create table
        DynamicTable tab = new DynamicTable(2);
        tab.setFrame(ITableFrame.VOLD);
        tab.setRules(ITableRules.NONE);
        tab.setBorder(0);

        if (newAction.equals("uconfirm")) {
            Select userSelect = createUserSelect(
                sc.USERID, SadUsers.USER_TYPE + "!=" + sc.UTDATAS);
            tab.addRow(ec.cr2ColRow("Existing Users:", userSelect));
            //tab.addRow( ec.cr2ColRow("User Id:", sc.USERID, 20, 10, userid) );
        } else if (newAction.equals("dconfirm")) {
            Select dataSelect = createUserSelect(
                sc.USERID, SadUsers.USER_TYPE + "=" + sc.UTDATAS);
            tab.addRow(ec.cr2ColRow("Existing Datasets:", dataSelect));
            //tab.addRow( ec.cr2ColRow("User Id:", sc.USERID, 20, 10, userid) );
        } // if
        tab.addRow(ec.cr2ColRow("<br>", " "));
        tab.addRow(ec.crSpanColsRow(new Submit("", buttonText).toHTML(),2));

        // create the form
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST", sc.LOGIN_USER_APP, target);
        form.addItem(new Hidden(sc.SCREEN, "user"));
        form.addItem(new Hidden(sc.VERSION, "delete"));
        form.addItem(new Hidden(sc.ACTION, newAction));
        if ("udelete".equals(newAction) || "ddelete".equals(newAction)) {
            form.addItem(new Hidden(sc.USERID, userid));
        } // if newaction = delete
        // for the menu system
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));
        form.addItem(tab);

        return form;
    } // class userMenu(String args[])

} // class UserDeleteFrame
