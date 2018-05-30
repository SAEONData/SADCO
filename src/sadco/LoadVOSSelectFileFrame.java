package sadco;

import common2.*;
import java.io.*;
import oracle.html.*;

/**
 * Creates a drop-down of file names to select
 *
 * SadcoVOS
 * screen.equals("load")
 * version.equals("preload")
 *
 * @author  001123 - SIT Group
 * @version
 * 001123 - Ursula von St Ange - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020906 - Ursula von St Ange - Added a form name 'thisForm'. Took away the
 *                               target for the form.  Move the boolean
 *                               variable 'optional' to the javascript stuff.<br>
 */
public class LoadVOSSelectFileFrame extends CompoundItem{

    boolean     dbg = false;
    //boolean     dbg = true;

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    // file stuff
    String      pathName;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoadVOSSelectFileFrame (String args[]) {

        try {
            LoadVOSSelectFileFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadVOSSelectFileFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadVOSSelectFileFrameActual(String args[]) {

        // for the top menu bar
        String sessionCode= ec.getArgument(args, sc.SESSIONCODE);
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // get the correct path name
        //if (ec.getHost().equals("morph")) {
        //    pathName = sc.MORPHL + "vos/";
        //} else {
        //    pathName = sc.LOCAL;
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL + "vos/";
        } else {
            pathName = sc.LOCALDIR;
        } // if host


        // build up the file name with wild cards
        String filter = ".inp";
        if (dbg) System.out.println ("<br>filter = " + filter);

        // get the file list
        File path = new File(pathName);
        String[] fileList = path.list();
        if (dbg) System.out.println ("<br>fileList.length = " + fileList.length);

        // create the pull-down box
        Select files = createFileListSelect(fileList, filter);

        // Create main table
        DynamicTable mainTable = new DynamicTable(2);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        mainTable.addRow(ec.crSpanColsRow(
            "<font color=blue size=+2>Load Data</font>",2));
        // write help info
        ec.writeHelpInfo(mainTable);
        mainTable.addRow(ec.crSpanColsRow("<br>",2));

        if (files.size() > 0) {

            // add JavaScript stuff
            this.addItem(JavaScript());

            // create form
            JSLLib.formName = "thisForm";
            Form form = new Form("POST\" name=\"thisForm", sc.VOS_APP);
            mainTable.addRow(ec.cr2ColRow(
                "Load ID (Lyy/mm/seqNo):",sc.LOADID,10,10,"","*"));
            mainTable.addRow(ec.cr2ColRow(
                "VOS Load ID (yymm):",sc.VOSLOADID,4,4,"","*"));
            mainTable.addRow(ec.cr2ColRow("Source:",sc.SOURCE,20,20,"SAWS","*"));
            mainTable.addRow(ec.cr2ColRow(
                "Responsible person:",sc.RESPPERSON,20,20,"Louise Watt","*"));
            mainTable.addRow(ec.cr2ColRow("File Name:",files));
            mainTable.addRow(ec.crSpanColsRow("<br>",2));
            form.addItem (mainTable);
            form.addItem(JSLLib.OnClickButton("Go!","btnIFI")).setCenter();
            form.addItem(new Hidden(sc.SCREEN,"load"));
            form.addItem(new Hidden(sc.VERSION,"load"));
            // for the menu system
            form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
            form.addItem(new Hidden(sc.USERTYPE, userType));
            form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
            form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

            this.addItem(form.setCenter());
        } else {
            mainTable.addRow(ec.crSpan2ColRow(
                "<br>There are no load files"));
            this.addItem(mainTable.setCenter());
        } // if (files.size() > 0) {

    } // LoadVOSSelectFileFrameActual


    /**
     * create the combo-box (pull-down box) for the file names
     */
    Select createFileListSelect(String[] fileList, String filter) {
        fileList = ec.bubbleSortIgnoreCase(fileList, fileList.length);
        Select sel = new Select(sc.FILENAME);
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].endsWith(filter)) {
                sel.addOption(new Option(fileList[i]));
            } // if (fileList[i].endsWith(filter)) {
        } // for i
        return sel;
    } // createFileListSelect

    /**
     * Add the javascript stuff to the page
     */
    Container JavaScript() {
        Container con = new Container();
        con.addItem(JSLLib.OpenScript());
        con.addItem(JSLLib.RtnNotNull());

        con.addItem(JSLLib.OpenEvent("INPUT","Validate"));
        con.addItem(JSLLib.CallNotNull(sc.LOADID, "Load ID"));
        con.addItem(JSLLib.CallNotNull(sc.VOSLOADID, "VOS Load ID"));
        con.addItem(JSLLib.CallNotNull(sc.SOURCE, "Source"));
        con.addItem(JSLLib.CallNotNull(sc.RESPPERSON, "Responsible person"));
        con.addItem(JSLLib.CloseEvent());

        con.addItem(JSLLib.OpenEvent("btnIFI","OnClick"));
        con.addItem(JSLLib.CallValidate("INPUT"));
        con.addItem(JSLLib.StandardSubmit(false));
        con.addItem(JSLLib.CloseEvent());

        con.addItem(JSLLib.CloseScript());

        return con;
    } // JavaScript



} // LoadVOSSelectFileFrame class
