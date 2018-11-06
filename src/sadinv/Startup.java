package sadinv;

import oracle.html.*;
import common2.JSLLib;

/**
 * This class generates the initial registration and login screen for
 * the sadco inventory. I can be called again by Login or Register, in
 * which case it should have additional parameters
 *
 * SadInv
 * pscreen = startup
 * pversion = login / register
 * puserok = blank / 1 (not registered) / 2 (wrong password)
 * pemail = blank / userid

 *
 * @author 20091117 - SIT Group.
 * @version
 * 20091117 - Ursula von St Ange - create program                       <br>
 */
public class Startup extends CompoundItem {

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
    public Startup (String args[]) {

        // check whether this is called again because of an error
        String version  = ec.getArgument(args,sc.VERSION);
        String error = ec.getArgument(args,sc.USEROK);
        String email = ec.getArgument(args,sc.EMAIL);
        if (dbg2) {
            email = "uvstange@csir.co.za";
            version = sc.REGISTER;
            //version = sc.LOGIN;
            //error = sc.ERROR_PASSW;
            error = sc.ERROR_USER;
        } // if (dbg2)
        if (dbg) System.out.println("<br>" + thisClass + " error = " + error);

        // create all the tables
        DynamicTable mainTab = new DynamicTable(1);
        mainTab
            .setBorder(0)
            .setRules(ITableRules.NONE)
            .setFrame(ITableFrame.VOLD)
            ;
        DynamicTable loginTab = new DynamicTable(1);
        loginTab
            .setBorder(0)
            .setRules(ITableRules.NONE)
            .setFrame(ITableFrame.VOLD)
            ;
        DynamicTable registerTab = new DynamicTable(1);
        registerTab
            .setBorder(0)
            .setRules(ITableRules.NONE)
            .setFrame(ITableFrame.VOLD)
            ;

        // the password fields
        PasswordField password = new PasswordField(sc.PASSWORD, 10, 10, "");
        PasswordField password2 = new PasswordField(sc.PASSWORD+"2", 10, 10, "");

        // link to get password sent
        String url = sc.APP + "?" + sc.SCREEN + "=" + sc.SENDEMAIL + "&" +
            sc.VERSION + "=" + sc.STARTUP + "&" +
            sc.EMAIL + "=" + email;
        String link = "<a href=\"#\" " + "onclick=\"window.open('" +
            url + "','mywindow','width=400,height=150')\"" + ">here</a>";

        // create the input boxes for logging in
        loginTab
            .addRow(ec.cr2ColRow("E-mail:", sc.EMAIL, 30, 50, email))
            .addRow(ec.cr2ColRow("Password:", password.toHTML()))
            ;

        // create the login form
        Form loginForm = new Form("GET", sc.APP);
        loginForm
            .addItem(loginTab)
            .addItem(new Hidden(sc.SCREEN, sc.LOGIN))
            .addItem(new Submit(sc.ACTION, "Log in"));

        // create drop-down occupations list
        Select occupation = new Select(sc.OCCUPATION);
        for (int i = 0; i < sc.OCCUPATIONS.length;  i++) {
            occupation.addOption(new Option(
                sc.OCCUPATIONS[i], sc.OCCUPATIONS[i], false));
        } // for (int i = 0; i < sc.OCCUPATIONS.length;  i++)

        // create drop-down agreement list
        Select agree = new Select("pagree");
        agree.addOption(new Option("", "",false));
        agree.addOption(new Option("Yes", "Yes",false));

        // create the input boxes for registering
        registerTab
            .addRow(ec.cr2ColRow("First Name: *", sc.FNAME, 30, 50, ""))
            .addRow(ec.cr2ColRow("Surname: *", sc.SURNAME, 30, 50, ""))
            .addRow(ec.cr2ColRow("Affiliation: *", sc.AFFIL, 30, 50, ""))
            .addRow(ec.cr2ColRow("Occupation: *", occupation.toHTML()))
            .addRow(ec.cr2ColRow("Postal address: *", sc.ADDRESS, 30, 100, ""))
            .addRow(ec.cr2ColRow("E-mail: *", sc.EMAIL, 30, 50, email))
            .addRow(ec.cr2ColRow("Password: *", password.toHTML()))
            .addRow(ec.cr2ColRow("Confirm Password: *", password2.toHTML()))
            .addRow(ec.crSpan2ColRow(useAgreement().toHTML()))
            .addRow(ec.cr2ColRow("Agree to conditions: *", agree.toHTML()))
            ;

        // create the register form
        JSLLib.formName = "thisForm";
        Form registerForm = new Form("GET\" name=\"thisForm", sc.APP);
        registerForm
            .addItem(registerTab)
            .addItem(new Hidden(sc.SCREEN, sc.REGISTER))
            .addItem(JSLLib.OnClickButton("Register","btnIFI"))
            ;

        // create the container to hook everyhting on to
        Container con = new Container();

        // add the rest of the page
        if ("".equals(error)) {
            // called on inventory load
            con .addItem(new SimpleItem("Welcome to the SADCO inventory")
                    .setFontSize(6))
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("You are welcome to browse the inventory by " +
                    "clicking on one of the buttons on the left, " +
                    "but in order to extract any data you have to be " +
                    "registered and be logged in.")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                ;
        } else if (sc.REGISTER.equals(version)) {
            // from Register - only gets called for one criteria (user exists)
            con.addItem("<font color=red size=4>You are already registered. " +
                    "Please fill in the login form below</font>")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("Please click " + link + " if you have forgotton your password, " +
                    "and it will be sent to you by e-mail.")
                .addItem(SimpleItem.LineBreak)
                ;
        } else if (sc.ERROR_PASSW.equals(error)) {
            // from Login - invalid password
            con.addItem("<font color=red size=4>You have entered an invalid password " +
                    " - please re-enter</font>")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("Please click " + link + " if you have forgotton your password, " +
                    "and it will be sent to you by e-mail.")
                .addItem(SimpleItem.LineBreak)
                ;
        } else if (sc.ERROR_USER.equals(error)) {
            // from Login - user not registered
            con.addItem("<font color=red size=4>You are not yet registered.  Please " + "" +
                    "complete the registration form below. An e-mail will be " +
                    "sent to you to confirm your details.</font>")
                .addItem(SimpleItem.LineBreak)
                .addItem("Please note compulsory fields are marked with an *")
                .addItem(SimpleItem.LineBreak)
                ;
        } // if ("".equals(error))

        if ("".equals(error)) {
            con
                .addItem("If you are <b>already registered</b>, and want to " +
                    "extract data, OR if you want to <b>check whether you are " +
                    "registered</b>, please complete the log in form below.")
                .addItem(SimpleItem.LineBreak)
                ;
        } // if ("".equals(error))

        if ("".equals(error) || sc.ERROR_PASSW.equals(error) || sc.REGISTER.equals(version)) {
            // inventory load, password error, register error (user exists)
            con.addItem(loginForm);
        } // if ("".equals(error) && sc.ERROR_PASSW.equals(error))

        if ("".equals(error)) {
            con
                .addItem("If you have <b>not yet registered</b>, and want to " +
                    "extract data, please complete the registration form below. " +
                    "An e-mail will be sent to you to confirm your details.")
                .addItem(SimpleItem.LineBreak)
                ;
        } // if ("".equals(error))

        if ("".equals(error) || sc.ERROR_USER.equals(error) && sc.LOGIN.equals(version)) {
            // inventory load, user error from Login
            con.addItem(registerForm);
            //con.addItem(useAgreement());

            // add javascript stuff
            con .addItem(JSLLib.OpenScript())
                .addItem(JSLLib.RtnNotNull())
                .addItem(JSLLib.OpenEvent("INPUT","Validate"))
                .addItem(JSLLib.RtnSame())
                .addItem(JSLLib.CallNotNull(sc.FNAME,"First Name"))
                .addItem(JSLLib.CallNotNull(sc.SURNAME,"Surname"))
                .addItem(JSLLib.CallNotNull(sc.AFFIL,"Affiliation"))
                .addItem(JSLLib.CallNotNull(sc.OCCUPATION,"Occupation"))
                .addItem(JSLLib.CallNotNull(sc.ADDRESS,"Address"))
                .addItem(JSLLib.CallNotNull(sc.EMAIL,"E-mail"))
                .addItem(JSLLib.CallNotNull(sc.PASSWORD,"Password"))
                .addItem(JSLLib.CallNotNull(sc.PASSWORD+"2","Confirm Password"))
                .addItem(JSLLib.CallSame(sc.PASSWORD,sc.PASSWORD+"2","passwords"))
                .addItem(JSLLib.CallNotNull("pagree","Agree to conditions"))
                .addItem(JSLLib.CloseEvent())
                .addItem(JSLLib.OpenEvent("btnIFI","OnClick"))
                .addItem(JSLLib.CallValidate("INPUT"))
                .addItem(JSLLib.StandardSubmit(false))
                .addItem(JSLLib.CloseEvent())
                .addItem(JSLLib.CloseScript())
                ;

        } // if ("".equals(error) && sc.ERROR_PASSW.equals(error))

        // add everthing to the main table, and add to the screen
        TableRow rows = new TableRow();
        rows
            .addCell(new TableDataCell(" ").setWidth(20))
            .addCell(new TableDataCell(con))
            ;
        mainTab.addRow(rows);
        this.addItem(mainTab);

    } // constructor Startup

        /**
     * Return the instructions to download a file in a table
     * @return a html table containing the instructions
     */
    public DynamicTable useAgreement()   {
        String text =
            "<b>Agreement for use of data</b><br>" +
            "By submitting this registration I agree that any extracted data will be used<br> " +
            "for research, non-commercial purposes only, and that data will not be passed to<br>" +
            "a 3rd party.  The following acknowledgement should be used in any products:<br>" +
            //"<br>" +
            "'The data has been supplied by the Southern African Data Centre for Oceanography'.";

        DynamicTable table = new DynamicTable(1);
        table.setBorder(1);
        table.setFrame(ITableFrame.BOX);
        //table.addRow(cr1ColRow(text));

        TableRow row = new TableRow();
        row.addCell(new TableDataCell(text))
//            .setBackgroundColor(Color.cyan))
            ;
        table.addRow(row);
        return table;
    }  // public DynamicTable downloadFileInstructions


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("Startup local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new Startup(args));
            bd
                .setActivatedLinkColor   ("#000088")
                .setFollowedLinkColor    ("#663366")
                .setUnfollowedLinkColor  ("#666666")
                ;
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            ec.processErrorStatic(e, "Startup", "Constructor", "");
        } // try-catch
        // close the connection to the database
        //common2.DbAccessC.close();

    } // main

} // class Startup