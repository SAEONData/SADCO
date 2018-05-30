package menusp;

import common2.*;
import oracle.html.*;

/**
 * Generates a menu.
 *
 * @author 000424 - SIT Group
 * @version
 * 000424 - Ursula von St Ange - create class<br>
 * 020528 - Ursula von St Ange - Take out the 'target' in the form<br>
 * 020625 - Ursula von St Ange - Implement MnuConstants. If a non-menu
 *                               application is called, and there are no
 *                               parameters, or all are hidden, then also
 *                               add the menuNo and version (as mVersion) to
 *                               the URL.<br>
 * 020625 - Ursula von St Ange - pscreen not used by non-frames - take out<br>
 * 030718 - Ursula von St Ange - (ub01) When a menu-item has no parameters
 *                               or has only hidden parameters, send
 *                               through the menu no of the item, and
 *                               not of the menu itself                 <br>
 * 030718 - Ursula von St Ange - (ub01) Put previous change back as was
 *                               When you click on the last button, it
 *                               tells there are no menu items - not
 *                               what you want!                         <br>
 */
public class MnuMenuFrame extends CompoundItem {

    /** contains the method to parse URL-type arguments */
    common.edmCommon ec = new common.edmCommon();

    // parameters
    String version = "";
    String menuNo = "";
    String userType = "";
    String sessionCode = "";

    // 'local' paramaters
    String owner = "";
    String application = "";

    boolean dbg = false;
    //boolean dbg = true;

    /** empty constructor needed for inheritance */
    public MnuMenuFrame() {}

    /** actual constructor
     * The <b>args</b> variable must contain the following parameters: <br>
     * MnuConstants.VERSION - values = menu / form<br>
     * MnuConstants.MENUNO - the menu-no of the menu to be generated<br>
     * MnuConstants.USERTYPE - values = 1 (super-user) / 2 (normal user)<br>
     * MnuConstants.SESSIONCODE - the current session code - used by the
     *    SADCO applications<p>
     * The <b>args2</b> variable must contain the following parameters: <br>
     * MnuConstants.OWNER - the owner of the application<br>
     * MnuConstants.APPLICATION - the calling application<br>
     * MnuConstants.USERTYPE - values = 1 (super-user) / 2 (normal user) - only
     *                         used if the calling application is not a menu-type
     *                         application<p>
     * @param args  array of URL-like name=value parameter pairs - from the command line
     * @param args2 array of URL-like name=value parameter pairs - must be defined in
     *              the calling class
     */
    public MnuMenuFrame(String args[], String args2[]) {

        // get any parameters off the command line
        version = ec.getArgument(args, MnuConstants.VERSION);
        menuNo = ec.getArgument(args, MnuConstants.MENUNO);
        userType = ec.getArgument(args, MnuConstants.USERTYPE);
        sessionCode = ec.getArgument(args, MnuConstants.SESSIONCODE);

        // get the locally set parameters
        owner = ec.getArgument(args2, MnuConstants.OWNER);
        application = ec.getArgument(args2, MnuConstants.APPLICATION);
        String userType2 = ec.getArgument(args2, MnuConstants.USERTYPE);

        if (dbg) System.out.println("<br>menuNo = " + menuNo);
        if (dbg) System.out.println("<br>userType2 = " + userType2);

        // get default value for userType == 'normal user'
        if (userType.equals("")) {
            userType = ("".equals(userType2)? "2" : userType2);
        } // if (userType.equals(""))
        if (dbg) System.out.println("<br>userType = " + userType);

        // set up the where clause
        String where = "";
        if (menuNo.equals("")) {
            // only need an owner on the first select - thereafter
            // all others are linked by 'CHILD_OF'.
            menuNo = "0";
            where = MnuMenuItems.OWNER+ "='" + owner + "'" + " and ";
        }
        where += MnuMenuItems.CHILD_OF + "=" + menuNo + " and " +
            MnuMenuItems.USER_TYPE + ">=" + userType;

        if (dbg) System.out.println("<br>where = " + where);

        // get menu items
        String order = MnuMenuItems.SEQUENCE_NUMBER;
        //where = MnuMenuItems.CHILD_OF + "=" + menuNo;
        MnuMenuItems[] mnuMenuItems = new MnuMenuItems().get(where, order);
        //MnuMenuItems[] mnuMenuItems = new MnuMenuItems().get(where);

        if (mnuMenuItems.length > 0) {

            // Create a container and add menu items to it
            Container content = new Container();
            String commonURL = application + "?" + MnuConstants.MENUNO + "=";
            String URL = "";
            //String target = "";

            // go through all the menu items for this menu
            for (int i = 0; i < mnuMenuItems.length; i++) {

                URL = "";
                if (dbg) System.out.println("<br>mnuMenuItems[i] = " + i + " " + mnuMenuItems[i]);
                // get right URL
                if (!mnuMenuItems[i].getUrl("").equals("")) {
                    // this menu item calls another application

                    // check to see whether there are parameters
                    where = MnuParameters.MENU_ITEM_CODE + "=" +
                        mnuMenuItems[i].getCode("");
                    MnuParameters[] mnuParameters = new MnuParameters().get(where);
                    if (dbg) System.out.println("<br>" +
                        "mnuParameters.length = " + mnuParameters.length);
                    if (dbg) System.out.println("<br>" +
                        "mnuMenuItems[i].getScriptTypeCode() = " +
                        mnuMenuItems[i].getScriptTypeCode());
                    // get right default path
                    if ((mnuMenuItems[i].getScriptTypeCode() != 3) &&  // menu all
                        (mnuMenuItems[i].getScriptTypeCode() != 4)) {  // java all
                        MnuScriptType[] mnuScriptType = new MnuScriptType().get(
                            MnuScriptType.CODE+"="+
                            mnuMenuItems[i].getScriptTypeCode(""));
                        if (dbg) System.out.println("<br>In if for script type " +
                            mnuScriptType[0].getPath());
                        URL = mnuScriptType[0].getPath() + URL;
                    } // getScriptTypeCode

                    if (dbg) System.out.println("<br>mnuParameters.length = " + mnuParameters.length);
                    if (mnuParameters.length == 0) {
                        // no parameters - non-menu application 'called' directly -
                        // send the menuNo and version as well
                        URL += mnuMenuItems[i].getUrl("") + "?" +
                            MnuConstants.MENUNO + "=" + // mnuMenuItems[i].getCode("") + // ub01
                            menuNo + // ub01
                            "&" + MnuConstants.MVERSION + "=" + version;
                        //target = "middle";

                    } else {
                        // check if all the parameters are the 'hidden' type
                        if (dbg) System.out.println("<br>there are parameters");
                        boolean allHidden = true;
                        for (int j = 0; j < mnuParameters.length; j++) {
                            if (!mnuParameters[j].getInputType().equals("hidden")) {
                                allHidden = false;
                            } // if (!mnuParameters[j].getInputType().equals("hidden"))
                        } // for int j
                        if (!allHidden) {
                            // have to create a form before 'calling' the non-menu
                            // application
                            URL += commonURL + mnuMenuItems[i].getCode("") +
                                "&" + MnuConstants.VERSION + "=" + "form";
                            if (dbg) System.out.println("<br>not all hidden - create a form " + URL);
                        } else { // (allHidden)
                            // build up the URL
                            URL += mnuMenuItems[i].getUrl("");
                            for (int j = 0; j < mnuParameters.length; j++) {
                                String qa = "&";
                                if (j == 0) qa = "?";
                                URL += qa + "p" + mnuParameters[j].getName() +
                                    "=" + mnuParameters[j].getDefaultValue();
                            } // for int j
                            // all parameters are hidden - non-menu application 'called'
                            // directly - send the menuNo and version as well
                            URL += "&" + MnuConstants.MENUNO + "=" +
                                //mnuMenuItems[i].getCode("") + // ub01
                                menuNo +    // ub01
                                "&" + MnuConstants.MVERSION + "=" + version;
                            if (dbg) System.out.println("<br>all hidden parameters " + URL);
                        } // (allHidden)

                    } // if (mnuParameters.length == 0)
                } else {
                    // the menu item is a menu
                    URL = commonURL + mnuMenuItems[i].getCode("") +
                        "&" + MnuConstants.VERSION + "=" + "menu";
                } // if (!mnuMenuItems[i].getUrl("").equals(""))
                String qa = "?";
                if (URL.indexOf("?") > 0) { qa = "&"; }
                URL += qa + MnuConstants.USERTYPE + "=" + userType + "&" +
                    MnuConstants.SESSIONCODE + "=" + sessionCode;

                if (URL.indexOf("morph") > 0) {
                    URL += "&phost=" + ec.getHost();
                } // if (URL.indexOf("morph") > 0)

                content.addItem(new MenuItem(URL, mnuMenuItems[i].getText("")));
            } // for i


            // Create menu table
            DynamicTable menuTable = new DynamicTable(1);
            menuTable.setFrame(ITableFrame.VOLD);
            menuTable.setRules(ITableRules.NONE);
            menuTable.setBorder(0);
            menuTable.addRow(ec.cr1ColRow("<b>Select an Item:</b>"));
            menuTable.addRow(ec.cr1ColRow(content.toHTML()));
            menuTable.addRow(ec.cr1ColRow("<br><br><br>"));

            // add main table to class itself
            this.addItem(menuTable.setCenter());
        } else {
            this.addItem(new SimpleItem(
                "<br><b>There are no menu items for this menu</b><br><br>").setCenter());
        } // mnuMenuItems.length > 0

    } // MnuMenuFrame constructor


    /**
     * set the value of the owner variable
     * @param  owner    The owner of the Oracle schema
     */
    public void setOwner(String owner) {
         this.owner = owner;
    } // setOwner


    /**
     * set the value of the application variable
     * @param  application  The calling application name
     */
    public void setApplication(String application) {
        this.application = application;
    } // setApplication


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        // values for args2 variable
        String thisClass = "SadcoMenu";    // class name
        String owner     = "sadco";        // Oracle schema userid
        String title     = "SADCO";        // title on top bar
        String date      = "27 June 2002"; // last update date

        String[] args2 = {
            MnuConstants.APPLICATION    + "=" + thisClass,
            MnuConstants.OWNER          + "=" + owner,
            MnuConstants.TITLE          + "=" + title,
            MnuConstants.DATE           + "=" + date
        }; // args2

        try {
            // create the page head
            HtmlHead hd = new HtmlHead("headTitle");

            // create the body and the page itself
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            hp.printHeader();

            bd.addItem(new MnuMenuFrame(args, args2));

            hp.print();


        } catch(Exception e) {
        //  ec.processErrorStatic(e, thisClass, "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class MnuMenuFrame









