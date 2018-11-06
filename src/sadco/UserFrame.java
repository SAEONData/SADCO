package sadco;

import oracle.html.*;
import common2.*;

/**
 * Displays a page for new users / datasets, updating of users / datasets, and
 * registering of users for datasets.
 *
 * Sadco                                                                    |
 * screen.equals("user")                                                    |
 * version.equals("user")                                                   |
 *
 * @author  001106 - SIT Group
 * @version
 * 001106 - Ursula von St Ange - create class<br>
 * 020527 - Ursula von St Ange - update for non-frame menus<br>
 * 020527 - Ursula von St Ange - update for exception catching<br>
 */
public class UserFrame extends CompoundItem{

    boolean dbg = false;
    //boolean dbg = true;

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    /**
     * Empty constructor needed for inheritance
     */
    public UserFrame() {

    } // UserFrame constructor


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
     public UserFrame (String args[])  {

        try {
            UserFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // UserFrame constructor

   /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void UserFrameActual(String args[])  {
        this.addItem(printBanner(args));
        this.addItem(createUserForm(args).setCenter());
    } // constructor


    /**
     * Create a header for the entry form
     * @param args  array of URL-like name=value parameter pairs - from the command line
     */
    Container printBanner(String args[]) {

        String action = ec.getArgument(args, sc.ACTION).toLowerCase();
        String header = "";

        // printing headers
        if ("new".equals(action)) {
            header = "Register New User";
        } else if ("update".equals(action)) {
            header = "Change Passwords";
        } else if ("updatesu".equals(action)) {
            header = "Change Other User Profile";

        } else if ("newds".equals(action)) {
            header = "Register New Dataset";
        } else if ("updateds".equals(action)) {
            header = "Change Dataset Passkey";

        } else if ("newuds".equals(action)) {
            header = "Register User for Dataset";
        } // if action
        header = "<font size=+2 color=blue>" + header + "</font><br><br>";

        Container con = new Container();
        con.addItem(new SimpleItem(header).setCenter().setBold());
        return con;
    } // printBanner */

    /**
     * Create the html form for adding / updating of users / datasets
     */
    Form createUserForm(String args[]) {

        String userid = ec.getArgument(args, sc.USERID).toLowerCase();
        String dataset = ec.getArgument(args, sc.DATASET).toLowerCase();
        String version = ec.getArgument(args, sc.VERSION).toLowerCase();
        String action = ec.getArgument(args, sc.ACTION).toLowerCase();
        String session = ec.getArgument(args, sc.SESSIONCODE);
        String uType = ec.getArgument(args, sc.NUSERTYPE);
        String fType = ec.getArgument(args, sc.FLAGTYPE);
        int userType = 0;
        int flagType = 0;
        // for the top menu bar
        String userTypeM = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // create the form
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST\" name=\"thisForm", sc.LOGIN_USER_APP, target);
        form.addItem(new Hidden(sc.SCREEN, "user"));
        form.addItem(new Hidden(sc.VERSION, "confirm"));
        form.addItem(new Hidden(sc.ACTION, action));
        form.addItem(new Hidden(sc.SESSIONCODE, session));
        // for the menu system
        form.addItem(new Hidden(sc.USERTYPE, userTypeM));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO,
            ec.getArgument(args,menusp.MnuConstants.MENUNO)));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION,
            ec.getArgument(args,menusp.MnuConstants.MVERSION)));

        // Create table
        DynamicTable tab = new DynamicTable(2);
        tab.setFrame(ITableFrame.VOLD);
        tab.setRules(ITableRules.NONE);
        tab.setBorder(0);
        //tab.setCenter();

        // write help info
        ec.writeHelpInfo(tab);

        // add javascript stuff
        JSLLib.formName = "thisForm";
        this.addItem(JSLLib.OpenScript());
        this.addItem(JSLLib.RtnNotNull());
        this.addItem(JSLLib.RtnStripMask());
        this.addItem(JSLLib.RtnReplace());
        this.addItem(JSLLib.RtnGetValue());
        this.addItem(JSLLib.RtnY2K());
        this.addItem(JSLLib.RtnChkDate());
        this.addItem(JSLLib.RtnCmpDates());
        this.addItem(JSLLib.RtnCmpDateTimes());
        this.addItem(JSLLib.RtnChkTime());
        this.addItem(JSLLib.RtnChkRange());
        this.addItem(JSLLib.RtnChkInteger());
        this.addItem(JSLLib.RtnChkNumPrecision());
        this.addItem(JSLLib.RtnChkNumScale());
        this.addItem(JSLLib.RtnLower());
        this.addItem(JSLLib.RtnUpper());

        this.addItem(JSLLib.OpenEvent("INPUT","Validate"));

        // prompts
        String euPrompt = "Existing Users";
        String uPrompt = "User Id";
        String nuPrompt = "New " + uPrompt;
        String pwPrompt = "Password";
        String opwPrompt = "Old " + pwPrompt;
        String npwPrompt = "New " + pwPrompt;
        String cpwPrompt = "Confirm " + pwPrompt;
        String utPrompt = "Type of User";
        String ftPrompt = "Flagged Data Extractions";
        String fdpwPrompt = "Flagged Data " + pwPrompt;
        String ofdpwPrompt = "Old " + fdpwPrompt;
        String nfdpwPrompt = "New " + fdpwPrompt;
        String ufdpwPrompt = "User's " + fdpwPrompt;
        String edsPrompt = "Existing Datasets";
        String dsPrompt = "Dataset Name";
        String ndsPrompt = "New " + dsPrompt;
        String pkPrompt = "Passkey";
        String opkPrompt = "Old " + pkPrompt;
        String npkPrompt = "New " + pkPrompt;
        String cpkPrompt = "Confirm " + pkPrompt;

        //-------------------------------------//
        // new user or update by administrator //
        //-------------------------------------//
        if (action.equals("new") || "updatesu".equals(action)) {

            // get the * for compulsory items
            String cmp = "*";
            if ("updatesu".equals(action)) cmp = "";

            // get the rigth userid prompt
            if ("updatesu".equals(action)) {
                // get the current profile
                SadUsers[] user = new SadUsers(userid).get();
                userType = user[0].getUserType();
                flagType = user[0].getFlagType();
                // user id
                tab.addRow(ec.cr2ColRow(uPrompt+":", userid));
            } else { // "new"
                userType = sc.UTNORML;
                flagType = sc.FTNOTAL;
                // existing users
                Select userSelect = createUserSelect(
                    "ptemp", SadUsers.USER_TYPE + "!=" + sc.UTDATAS);
                tab.addRow(ec.cr2ColRow(euPrompt+":", userSelect));
                // user id
                tab.addRow(ec.cr2ColRow(nuPrompt+":",sc.USERID,20,10,userid,cmp));
            }  // if ("updatesu".equals(action))

            // create the drop-down menus for user Types and flag Types
            Select userTypeS = createSelect(sc.NUSERTYPE, sc.USERTYPELIST, userType);
            Select flagTypeS = createSelect(sc.FLAGTYPE, sc.FLAGTYPELIST, flagType);

            // First Password
            PasswordField password = new PasswordField(sc.PASSWORD, 20, 10, "");
            tab.addRow(ec.cr2ColRow(pwPrompt+":", password.toHTML() + cmp));

            // Second Password - for confirmation
            password = new PasswordField(sc.PASSWORD2, 20, 10, "");
            tab.addRow(ec.cr2ColRow(cpwPrompt+":", password.toHTML() + cmp));

            // Dropdown menu for UserType
            tab.addRow(ec.cr2ColRow(utPrompt+":", userTypeS.toHTML() + cmp));

            // Dropdown menu for FlagType
            tab.addRow(ec.cr2ColRow(ftPrompt+":", flagTypeS.toHTML() + cmp));

            // message
            tab.addRow(ec.crSpanColsRow(
                "If the user is allowed to extract FLAGGED data, you can " +
                "enter the following:<br>(It will be ignored if the user " +
                "is NOT allowed to extract FLAGGED data)",2 , false, "blue",
                IHAlign.CENTER, "-1"));
            tab.addRow(ec.crSpanColsRow(
                "Use the 'Register User for Dataset' menu option to register " +
                "the user for the various Datasets", 2, false, "blue",
                IHAlign.CENTER, "-1"));

            // First Flag Data Password
            password = new PasswordField(sc.FPASSWORD, 20, 10, "");
            tab.addRow(ec.cr2ColRow(fdpwPrompt+":", password.toHTML()));

            // Second Flag Data Password - for confirmation
            password = new PasswordField(sc.FPASSWORD2, 20, 10, "");
            tab.addRow(ec.cr2ColRow(cpwPrompt+":", password.toHTML()));

            // add Javascript stuff
            if ("new".equals(action)) {
                this.addItem(JSLLib.CallNotNull(sc.USERID, nuPrompt));
                this.addItem(JSLLib.CallNotNull(sc.PASSWORD, pwPrompt));
                this.addItem(JSLLib.CallNotNull(sc.PASSWORD2, cpwPrompt));
                this.addItem(JSLLib.CallNotNull(sc.NUSERTYPE, utPrompt));
                this.addItem(JSLLib.CallNotNull(sc.FLAGTYPE, ftPrompt));
            } // if ("new".equals(action))

        //-------------------------------//
        // update the user's own details //
        //-------------------------------//
        } else if ("update".equals(action)) {

            // get the current user
            int sessionI = Integer.parseInt(session);
            LogSessions sessions[] = new LogSessions(sessionI).get();
            userid = sessions[0].getUserid();

            // does he have flag update rights?
            SadUsers[] user = new SadUsers(userid).get();
            flagType = user[0].getFlagType();

            // user id
            tab.addRow(ec.cr2ColRow(uPrompt+":", userid));

            // Old Password
            PasswordField password = new PasswordField(sc.OPASSWORD, 20, 10, "");
            tab.addRow(ec.cr2ColRow(opwPrompt+":", password.toHTML()));

            // First Password
            password = new PasswordField(sc.PASSWORD, 20, 10, "");
            tab.addRow(ec.cr2ColRow(npwPrompt+":", password.toHTML()));

            // Second Password - for confirmation
            password = new PasswordField(sc.PASSWORD2, 20, 10, "");
            tab.addRow(ec.cr2ColRow(cpwPrompt+":", password.toHTML()));

            if (flagType == 1) {

                // a space between the two sets of passwords
                tab.addRow(ec.crSpanColsRow("<br>",2));

                // Old Flag Data Password
                password = new PasswordField(sc.OFPASSWORD, 20, 10, "");
                tab.addRow(ec.cr2ColRow(ofdpwPrompt+":", password.toHTML()));

                // First Flag Data Password
                password = new PasswordField(sc.FPASSWORD, 20, 10, "");
                tab.addRow(ec.cr2ColRow(nfdpwPrompt+":", password.toHTML()));

                // Second Flag Data Password - for confirmation
                password = new PasswordField(sc.FPASSWORD2, 20, 10, "");
                tab.addRow(ec.cr2ColRow(cpwPrompt+":", password.toHTML()));
            } // if (flagType == 1)

        //---------------//
        // a new dataset //
        //---------------//
        } else if (action.equals("newds")) {

            Select dataSelect = createUserSelect(
                sc.DATASET, SadUsers.USER_TYPE + "=" + sc.UTDATAS);

            // Existing Dataset Name
            tab.addRow(ec.cr2ColRow(edsPrompt+":", dataSelect.toHTML()));

            // Dataset Name
            tab.addRow(ec.cr2ColRow(ndsPrompt+":",sc.USERID,20,10,userid,"*"));

            // First Password
            PasswordField password = new PasswordField(sc.PASSWORD, 20, 10, "");
            tab.addRow(ec.cr2ColRow(pkPrompt+":", password.toHTML() + "*"));

            // Second Password - for confirmation
            PasswordField password2 = new PasswordField(sc.PASSWORD2, 20, 10, "");
            tab.addRow(ec.cr2ColRow(cpkPrompt+":", password2.toHTML() + "*"));

            // add Javascript stuff
            this.addItem(JSLLib.CallNotNull(sc.USERID, ndsPrompt));
            this.addItem(JSLLib.CallNotNull(sc.PASSWORD, pkPrompt));
            this.addItem(JSLLib.CallNotNull(sc.PASSWORD2, cpkPrompt));

            // add hidden variables
            form.addItem(new Hidden(sc.NUSERTYPE, String.valueOf(sc.UTDATAS)));
            form.addItem(new Hidden(sc.FLAGTYPE, String.valueOf(sc.FTNOTAP)));

        //---------------------------//
        // update a dataset password //
        //---------------------------//
        } else if (action.equals("updateds")) {

            Select dataSelect = createUserSelect(
                sc.USERID, SadUsers.USER_TYPE + "=" + sc.UTDATAS);

            // Dataset Name
            tab.addRow(ec.cr2ColRow(dsPrompt+":", dataSelect.toHTML() + "*"));

            // Old Password
            PasswordField opassword = new PasswordField(sc.OPASSWORD, 20, 10, "");
            tab.addRow(ec.cr2ColRow(opkPrompt+":", opassword.toHTML() + "*"));

            // First Password
            PasswordField password = new PasswordField(sc.PASSWORD, 20, 10, "");
            tab.addRow(ec.cr2ColRow(npkPrompt+":", password.toHTML() + "*"));

            // Second Password - for confirmation
            PasswordField password2 = new PasswordField(sc.PASSWORD2, 20, 10, "");
            tab.addRow(ec.cr2ColRow(cpkPrompt+":", password2.toHTML() + "*"));

            // add Javascript stuff
            this.addItem(JSLLib.CallNotNull(sc.USERID, dsPrompt));
            this.addItem(JSLLib.CallNotNull(sc.OPASSWORD, opkPrompt));
            this.addItem(JSLLib.CallNotNull(sc.PASSWORD, npkPrompt));
            this.addItem(JSLLib.CallNotNull(sc.PASSWORD2, cpkPrompt));

        //-------------------------------//
        // register a user for a dataset //
        //-------------------------------//
        } else if (action.equals("newuds")) {

            Select userSelect = createUserSelect(
                sc.USERID, SadUsers.FLAG_TYPE + "=" + sc.FTALLOW);
            Select dataSelect = createUserSelect(
                sc.DATASET, SadUsers.USER_TYPE + "=" + sc.UTDATAS);

            // user id
            tab.addRow(ec.cr2ColRow(uPrompt+":", userSelect.toHTML() + "*"));

            // Dataset Name
            tab.addRow(ec.cr2ColRow(dsPrompt+":", dataSelect.toHTML() + "*"));

            tab.addRow(ec.crSpanColsRow(
                "If a user is not the list, go to <b>Change Other User " +
                "Profile</b>, change the 'flag type', and provide the " +
                "'flagged data password'",2 , false, "blue",
                IHAlign.CENTER, "-1"));

            // add Javascript stuff
            this.addItem(JSLLib.CallNotNull(sc.USERID, uPrompt));
            this.addItem(JSLLib.CallNotNull(sc.DATASET, dsPrompt));

        } //if (action.equals(

        // add som more Javascript stuff
        this.addItem(JSLLib.CloseEvent());
        this.addItem(JSLLib.OpenEvent("btnIFI","OnClick"));
        this.addItem(JSLLib.CallValidate("INPUT"));
        this.addItem(JSLLib.StandardSubmit(false));
        this.addItem(JSLLib.CloseEvent());
        this.addItem(JSLLib.CloseScript());

        if (action.equals("update") || "updatesu".equals(action)) {
            form.addItem(new Hidden(sc.USERID, userid));
        } // if (action
        if ("updatesu".equals(action)) {
            form.addItem(new Hidden(sc.OUSERTYPE, String.valueOf(userType)));
            form.addItem(new Hidden(sc.OFLAGTYPE, String.valueOf(flagType)));
        } // if (action

        form.addItem(tab);
        form.addItem(SimpleItem.LineBreak);
        form.addItem(JSLLib.OnClickButton("Go!","btnIFI")).setCenter();

        return form;

    } // createUserForm


    Select createSelect(String name, String[] list, int def) {
        Select select = new Select(name);
        for (int i = 1; i < list.length-1; i++) {
            select.addOption(new Option(list[i],
                String.valueOf(i), (i==def?true:false)));
        } // for (int i
        return select;

    } // Select createUserTypeSelect()


    Select createUserSelect(String name, String condition) {
        Select select = new Select(name);
        SadUsers[] users = new SadUsers().get(condition, SadUsers.USERID);
        for (int i = 0; i < users.length; i++) {
            select.addOption(new Option(users[i].getUserid(),
                users[i].getUserid(), false));
        } // for int i
        return select;
    } // Select createUserSelect()

} // class UserFrame
