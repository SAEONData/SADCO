package sadco;

import oracle.html.*;

/**
 * Creates a page with a banner and a login form
 *
 * Sadco
 * screen.equals("security")
 * version.equals("login")
 *
 * @author  001106 - SIT Group
 * @version
 * 001106 - Ursula von St Ange - create class<br>
 * 020527 - Ursula von St Ange - update for non-frame menus<br>
 * 020527 - Ursula von St Ange - update for exception catching<br>
 */
public class LoginFrame extends CompoundItem{

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    SadConstants sc = new SadConstants();

    String thisClass = this.getClass().getName();

    java.io.RandomAccessFile dbgF = null;
    boolean dbg = false;
    //boolean dbg = true;

    /**
     * Empty constructor needed for inheritance
     */
    public LoginFrame() {}


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoginFrame (String args[])  {

        try {

            if (dbg) dbgF = new java.io.RandomAccessFile(
                    "/usr2/people/sadco/www/debugs/" + thisClass + ".dbg", "rw");
            if (dbg) dbgF.writeBytes("in LoginFrame constructor\n");

            LoginFrameActual(args);

            if (dbg) dbgF.writeBytes("**** this.toHTML()\n");
            if (dbg) dbgF.writeBytes("\n**** end of this.toHTML()\n");
            if (dbg) dbgF.close();
        } catch(Exception e) {
            try {
                dbgF.writeBytes("Error in " + thisClass + " somewhere\n");
                dbgF.writeBytes("e.getMessage() = " + e.getMessage() + "\n");
                dbgF.close();
            } catch(Exception e2) {}
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoginFrame constructor

   /**
     * Creates a page with a banner and a login form
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoginFrameActual(String args[])  {

        if (dbg) printLine("in LoginFrameActual method");

        this.addItem(createBannerTable());
        this.addItem(createLoginForm(args));

        if (dbg) printLine("end of LoginFrameActual method");

    } // constructor


    /**
     * create the html table with the SADCO banner
     */
    DynamicTable createBannerTable() {
    //String createBannerTable() {

        //con.addItem("<br>in createBannerTable method\n");
        if (dbg) printLine("in createBannerTable method\n");

        // Create the table
        DynamicTable tab = new DynamicTable(2);
        if (dbg) printLine("after DynamicTable tab = new DynamicTable(2)\n");

        tab.setFrame(ITableFrame.VOLD);
        tab.setRules(ITableRules.NONE);
        tab.setBorder(0);
        if (dbg) printLine("after tab.setBorder(0);\n");

        tab.setCenter();
        if (dbg) printLine("after tab.setCenter();\n");
        if (dbg) printLine("tab = " + tab.toHTML());

        tab.addRow(
            ec.crSpanColsRow("<font size=+4>S A D C O</font>",
                2, true, "blue", IHAlign.CENTER) );
        if (dbg) printLine("after tab.addRow SADCO");
        if (dbg) printLine("tab = " + tab.toHTML());


        tab.addRow(
            ec.crSpanColsRow(
                "<font size=+2>Southern African Data Centre for Oceanography</font>",
                2, true, "blue", IHAlign.CENTER) );
        if (dbg) printLine("after tab.addRow Southern");
        if (dbg) printLine(tab.toHTML());

        tab.addRow( ec.crSpanColsRow("<br>",2) );
        if (dbg) printLine("after tab.addRow br");
        if (dbg) printLine(tab.toHTML());

        if (dbg) printLine("end of createBannerTable method\n");
        return tab;
        //return tab.toHTML();
    } // createBannerTable

    /**
     * create the html form with the login variables
     */
    Form createLoginForm(String args[]) {

        String userid = ec.getArgument(args, sc.USERID);
        String timeout = ec.getArgument(args, sc.TIMEOUT);
        /*****************/
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        /*****************/


        // Create table
        DynamicTable tab = new DynamicTable(2);
        tab.setFrame(ITableFrame.VOLD);
        tab.setRules(ITableRules.NONE);
        tab.setBorder(0);
        tab.setCenter();

        if (!"".equals(timeout)) {
            tab.addRow(ec.crSpanColsRow("<font size=+1>" +
                "Your session has timed out.  Please login again.<br><br>" +
                "</font>", 2, true, "red", IHAlign.CENTER));
        } // if (!"".equals(timeout))

        tab.addRow(ec.cr2ColRow("User Id:", sc.USERID, 30, 50, userid) );

        PasswordField password = new PasswordField(sc.PASSWORD, 10, 10, "");
        tab.addRow(ec.cr2ColRow("Password:", password.toHTML()));

        tab.addRow(ec.crSpanColsRow(new Submit("submit", "Go!").toHTML(),2) );

        // create the form
        Form form = new Form("POST", sc.LOGIN_USER_APP);
        form.addItem(new Hidden(sc.SCREEN, "security"));
        form.addItem(new Hidden(sc.VERSION, "confirm"));
        /*****************/
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        /*****************/
        form.addItem(tab);

        return form;

    } // createLoginForm


    void printLine (String text) {
        try {
            dbgF.writeBytes(text + "\n");
        } catch (Exception e) {}
    } // printLine

} // class LoginFrame
