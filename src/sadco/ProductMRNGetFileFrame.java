package sadco;

import java.io.*;
import oracle.html.*;
import java.util.StringTokenizer;

/**
 * ????
 *
 * SadcoMRN
 * screen.equals("product")
 * version.equals("getfile")
 *
 * @author 001206 - SIT Group
 * @version
 * 001206 - Ursula von St Ange - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 091215 - Ursula von St Ange - change for inventory users (ub01)          <br>
 */
public class ProductMRNGetFileFrame extends CompoundItem{

    boolean     dbg = false;
    //boolean     dbg = true;
    String thisClass = this.getClass().getName();

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    // file stuff
    //String userId = "";
    String pathName;


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ProductMRNGetFileFrame (String args[]) {

        try {
            ProductMRNGetFileFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ProductMRNGetFileFrame constructor

    /**
    * Creates a page with a banner and an entry form
    * @param args the string array of url-type name-value parameter pairs
    */
    void ProductMRNGetFileFrameActual(String args[]) {

        String screen = ec.getArgument(args,sc.SCREEN);
        String version = ec.getArgument(args,sc.VERSION);
        String sessionCode = ec.getArgument(args,sc.SESSIONCODE);
        // for the top menu bar
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // ................ EXTRACT THE USER record from db .....................
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        if (dbg) System.out.println("<br>Session code = " + session);
        String userId = sessions[0].getUserid();

        // .................Get the correct PATH NAME........................
        //if (ec.getHost().equals("morph")) {
        //    pathName = sc.MORPH + userId;
        //} else {
        //    pathName = sc.LOCAL;
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                pathName = sc.HOSTDIR + "inv_user";                     //ub01
            } else {                                                    //ub01
                pathName = sc.HOSTDIR + userId;                         //ub01
            } // if ("0".equals(userType))                              //ub01
            //pathName = sc.HOSTDIR + userId;
        } else {
            pathName = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(sc.HOST)) {

        // get the email name (especially for inventory users
        String emailName = "";                                          //ub01
        if ("0".equals(userType)) {                                     //ub01
            StringTokenizer t = new StringTokenizer(userId,"@");        //ub01
            emailName = t.nextToken();                                  //ub01
            // make sure there is not . in the email name               //ub01
            t = new StringTokenizer(emailName,".");                     //ub01
            emailName = t.nextToken();                                  //ub01
        } // if ("0".equals(userType))                                  //ub01

        // .................Build up the file name with WILD CARDS..............
        String filter = ".dbm";
        String filter2 = emailName;                                     //ub01
        if (dbg) System.out.println ("<br>filter = " + filter);

        // .................Get the FILE LIST...................................
        File path = new File(pathName);
        String[] fileList = path.list();
        if (dbg) System.out.println ("<br>fileList.length = " + fileList.length);

        // ................Create the PULL-DOWN BOX.............................
        Select files = createFileListSelect(fileList, filter, filter2); //ub01

        // ................Add DROP-DOWN MENU -- ProductType....................
        Select productType = new Select(sc.PRODUCT);
        for (int i = 0; i < sc.MRNPRODLIST.length; i++) {
            productType.addOption(
                new Option(sc.MRNPRODLIST[i], String.valueOf(i+1), false));
        } // for (int i

        // ................CREATE MAIN TABLE.................
        DynamicTable mainTable = new DynamicTable(2);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        mainTable.addRow(ec.crSpanColsRow(
            "<font color=blue size=+2>Products</font>",2));
        mainTable.addRow(ec.crSpanColsRow("<br>",2));

        // .................CREATE FORM......................
        //cr2ColRow: create 2 columns
        //crSpanColsRow("<br>",2): span over 2 columns

        if (files.size() > 0) {

            String target = "_top";
            /*****************/
            if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
            /*****************/
            Form form = new Form("POST", sc.MRN_APP, target);
            mainTable.addRow(ec.cr2ColRow("Select File:",files));
            mainTable.addRow(ec.cr2ColRow("Product:",productType));

            mainTable.addRow(ec.crSpanColsRow("<br>",2));

            form.addItem (mainTable);
            // parms to next page
            form.addItem(new Hidden(sc.SCREEN,"product"));
            form.addItem(new Hidden(sc.VERSION,"getparms"));
            form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
            // for the menu system
            form.addItem(new Hidden(sc.USERTYPE, userType));
            form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
            form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

            form.addItem(new Submit("","Go!").setCenter());

            this.addItem(form.setCenter());
        } else {
            mainTable.addRow(ec.crSpan2ColRow(
                "<br>There are no data files"));
            this.addItem(mainTable.setCenter());
        } // if (files.size() > 0) {

    } // ProductMRNGetFileFrameActual

    /**
     * CREATE THE COMBO-BOX (pull-down box) for the file names
     */
    Select createFileListSelect(String[] fileList, String filter, String filter2) { //ub01
        Select sel = new Select(sc.FILENAME);
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].endsWith(filter) && fileList[i].startsWith(filter2)) { //ub01
                sel.addOption(new Option(fileList[i]));
            } // if (fileList[i].endsWith(filter)) {
        } // for i
        return sel;
    } // createFileListSelect


} // class
