package sadco;

import common2.*;
import java.io.*;
import oracle.html.*;

/**
 * Creates a drop-down of file names to select
 *
 * SadcoMrn
 * screen.equals("load")
 * version.equals("getfile")
 *
 * @author  001123 - SIT Group
 * @version
 * 001123 - Ursula von St Ange - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020906 - Ursula von St Ange - Added a form name 'thisForm'. Took away the
 *                               target for the form.  Move the boolean
 *                               variable 'optional' to the javascript stuff<br>
 * 040610 - Ursula von St Ange - add loadtype drop-down box (ub01)          <br>
 */
public class LoadMRNSelectFileFrame extends CompoundItem{

    boolean     dbg = false;
    //boolean     dbg = true;

    /** some common functions */
    static common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    // file stuff
    String      pathName;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoadMRNSelectFileFrame (String args[]) {

        try {
            LoadMRNSelectFileFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadMRNSelectFileFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadMRNSelectFileFrameActual(String args[]) {

        // for the top menu bar
        String sessionCode= ec.getArgument(args, sc.SESSIONCODE);
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL;
        } else {
            pathName = sc.LOCALDIR;
        } // if host
        pathName += "marine/";


        // build up the file name with wild cards
        String filter = ".dat";
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
            //JSLLib.formName = "thisForm";
            Form form = new Form("POST\" name=\"thisForm", sc.MRN_APP);
            //mainTable.addRow(ec.cr2ColRow(
            //    "Load ID (Lyy/mm/seqNo):",sc.LOADID,10,10,"","*"));
            //mainTable.addRow(ec.cr2ColRow(
            //    "Data Time Zone (SAST/UTC):",sc.TIMEZONE,4,4,"","*"));
            mainTable.addRow(ec.cr2ColRow("Data Type:",createDataTypeSelect()));//ub01
            mainTable.addRow(ec.cr2ColRow(
                "Time Zone of Data to be Loaded:",createTimeZoneSelect()));
            mainTable.addRow(ec.cr2ColRow("File Name:",files));
            mainTable.addRow(ec.crSpanColsRow("<br>",2));
            form.addItem (mainTable);
            form.addItem(JSLLib.OnClickButton("Go!","btnIFI")).setCenter();
            form.addItem(new Hidden(sc.SCREEN,"load"));
            form.addItem(new Hidden(sc.VERSION,"preload"));
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

    } // LoadMRNSelectFileFrameActual


    /**
     * create the combo-box (pull-down box) for the file names
     */
    Select createDataTypeSelect() {                                     //ub01
        Select sel = new Select(sc.LOADTYPE);                           //ub01
        for (int i = 0; i < sc.MRNLODLIST.length; i++) {                //ub01
            sel.addOption(new Option(                                   //ub01
                sc.MRNLODLIST[i][0],sc.MRNLODLIST[i][1],false));        //ub01
        } // for (int i = 0; i < sc.MRNLODLIST.length; i++)             //ub01
        return sel;                                                     //ub01
    } // createDataTypeSelect                                           //ub01


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
     * create the combo-box (pull-down box) for the file names
     */
    Select createTimeZoneSelect() {
        Select sel = new Select(sc.TIMEZONE);
        sel.addOption(new Option(""));
        sel.addOption(new Option("SAST"));
        sel.addOption(new Option("UTC"));
        return sel;
    } // createTimeZoneSelect


    /**
     * Add the javascript stuff to the page
     */
    Container JavaScript() {
        Container con = new Container();
        con.addItem(JSLLib.OpenScript());
        con.addItem(JSLLib.RtnNotNull());
        JSLLib.formName = "thisForm";
        con.addItem(JSLLib.OpenEvent("INPUT","Validate"));
        //con.addItem(JSLLib.CallNotNull(sc.LOADID, "Load ID"));
//        con.addItem(JSLLib.CallNotNull(sc.TIMEZONE, "Time Zone"));
        con.addItem(JSLLib.CloseEvent());

        con.addItem(JSLLib.OpenEvent("btnIFI","OnClick"));
        con.addItem(JSLLib.CallValidate("INPUT"));
        con.addItem(JSLLib.StandardSubmit(false));
        con.addItem(JSLLib.CloseEvent());

        con.addItem(JSLLib.CloseScript());

        return con;
    } // JavaScript


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("LoadMRNSelectFileFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreLoadMRNFrame local= new PreLoadMRNFrame(args);
            bd.addItem(new LoadMRNSelectFileFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "LoadMRNSelectFileFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // LoadMRNSelectFileFrame class
