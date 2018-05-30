package sadco;

import java.io.File;
import oracle.html.CompoundItem;
import oracle.html.DynamicTable;
import oracle.html.HtmlBody;
import oracle.html.HtmlHead;
import oracle.html.HtmlPage;
import oracle.html.IHAlign;
import oracle.html.ITableFrame;
import oracle.html.ITableRules;
import oracle.html.Link;

/**
 * Generates a page with url's to download the files with the extracted data.
 *
 * SadcoVOS
 * screen.equals("extract")
 * version.equals("progress")
 *
 * @author  001123 - SIT Group
 * @version
 * 001123 - Ursula von St Ange - create class                               <br>
 * 021123 - Ursula von St Ange - add link to products page                  <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020925 - Ursula von St Ange - now also have list of products in progress <br>
 * 040609 - Ursula von St Ange - add .dbc for underway currents for mrn ub01<br>
 * 091215 - Ursula von St Ange - change for inventory users (ub02)          <br>
 */
public class ExtractProgressFrame extends CompoundItem {

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    SadConstants sc = new SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    // file stuff
    String pathName;

    // extensions for each data type
    String PRD_EXTENSIONS[] = {".dbm",".dbv"}; // extensions for products
    String VOS_EXTENSIONS[] = {".dbv"}; // vos weather
    String MRN_EXTENSIONS[] = {".dbm",".txt",".dbwc",".dbwp",   // marine
        ".dbs",".dbsc",".dbsp",".dbtp",".dbc",".stn"};
    String CUR_EXTENSIONS[] = {".cur",".dbc"}; // currents
    String WET_EXTENSIONS[] = {".wet"}; // weather
    String WAV_EXTENSIONS[] = {".wav"}; // waves


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ExtractProgressFrame (String args[], String applic) {

        try {
            ExtractProgressFrameActual(args, applic);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ExtractProgressFrame constructor


    /**
     * Does the actual work
     * @param args the string array of url-type name-value parameter pairs
     */
    void ExtractProgressFrameActual(String args[], String applic) {
    //void ExtractProgressFrameActual(String args[]) {

        // get the argument values
        String sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        String userType    = ec.getArgument(args, sc.USERTYPE);
        String reqNumber   = ec.getArgument(args, sc.REQNUMBER);
        String fileName    = ec.getArgument(args, sc.FILENAME);
        //String applic      = ec.getArgument(args, sc.APPLIC);

        if (dbg) System.out.println("<br>sessionCode = "+ sessionCode);
        if (dbg) System.out.println("<br>userType    = "+ userType);
        if (dbg) System.out.println("<br>reqNumber   = "+ reqNumber);
        if (dbg) System.out.println("<br>fileName    = "+ fileName);
        if (dbg) System.out.println("<br>applic      = "+ applic);

        // for the top menu bar
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // extract the user record from db
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        if (dbg) System.out.println("<br>Session code = " + sessionCode);

        // extract the userid from the record
        String userId = sessions[0].getUserid();
        if (dbg) System.out.println("<br>userId1 = "+userId);
        if ("0".equals(userType)) {  // from inventory                  //ub02
            userId = "inv_user";                                        //ub02
        } // if ("0".equals(userType))                                  //ub02
        if (dbg) System.out.println("<br>userId2 = "+userId);           //ub02

        // get the right extension
        String extensions[] = VOS_EXTENSIONS; // VOS
        if ("mrn".equals(applic)) extensions = MRN_EXTENSIONS; // marine
        if ("cur".equals(applic)) extensions = CUR_EXTENSIONS; // currents
        //if ("cur2".equals(applic)) extension = ".dbc"; // marine db
        if ("wet".equals(applic)) extensions = WET_EXTENSIONS; // weather
        if ("wav".equals(applic)) extensions = WAV_EXTENSIONS; // waves

        // get the right path name
        String pathName = "";
        if (dbg) System.out.println("<br>host = " + ec.getHost() + " " + sc.HOST);
        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIR + userId + "/";
        } else {
            pathName = sc.LOCALDIR;
        } // if host
        if (dbg) System.out.println("<br>pathName = " + pathName);

        // use either reqNumber or fileName as filter
        // filter 1
        String filter1 = reqNumber;
        if (dbg) System.out.println("<br>filter1 = " + filter1);

        // second filter
        String filter2 = fileName;
        int dotPos = fileName.indexOf(".");
        if (!"".equals(fileName) && (dotPos > 0)) {
            filter2 = fileName.substring(0, dotPos);
        } // if (!"".equals(fileName)) {
        if (dbg) System.out.println("<br>filter2 = " + filter2);

        // check to see whether there are any .dbm or .dbv files
        String fileListRow = createFileList(
            filter1, filter2, "", pathName, PRD_EXTENSIONS, userId);
        if (dbg) System.out.println(fileListRow);

        Link productsLink = null;
        //if ("mrn".equals(applic) || "vos".equals(applic) ) {
        if (!"".equals(fileListRow)) {   // there were .dbm or .dbv files
            // the products link
            productsLink = new Link (
                ("mrn".equals(applic) ? sc.MRN_APP : sc.VOS_APP) + "?" +
                sc.SCREEN + "=product&" +
                sc.VERSION + "=getfile&" +
                sc.USERTYPE + "=" + userType + "&" +
                sc.SESSIONCODE + "=" + sessionCode + "&" +
                menusp.MnuConstants.MENUNO + "=" + menuNo + "&" +
                menusp.MnuConstants.MVERSION + "=" + mVersion, "Products");
        } //if ("mrn".equals(applic)) {

        // the main table
        DynamicTable mainTable = new DynamicTable(2);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);
        mainTable.setCellPadding(10);

        // info for user
        mainTable.addRow(ec.cr1ColRow(
            "<font size=+2>Status of Extraction & Download</font>",
            true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow(
            //"This page is refreshed every minute", true, "", IHAlign.CENTER));
            "This page is refreshed every 15 seconds.", true, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow(
            "<b>Criteria for selection of extractions:</b>" +
            "<br><b>Request Number:</b> " + reqNumber +
            "<br><b>File Name</b>: " +
                ("".equals(fileName) ? "" : "*" + fileName + "*"),
                false, "", IHAlign.CENTER));

        // more info for the user
        //if ("mrn".equals(applic) || "vos".equals(applic) ) {
        if (!"".equals(fileListRow)) {   // there were .dbm or .dbv files
            mainTable.addRow(ec.cr1ColRow(
                "Once your file appears on the list, you can either<br>" +
                "&#149; download it to your PC (instructions at bottom of page), " +
                "or<br> &#149; go to the " + productsLink.toHTML() + " page to run a " +
                "product on your extracted file. <br> &#149; View the extracted raw data "+
                " directly by clicking on the file.", false, "", IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow(
                "Once your file appears on the list, you can " +
                "download it to your PC (instructions at bottom of page). <br>" +
                "View the extracted raw data directly by clicking on the file.",
                false, "", IHAlign.CENTER));
        }

        // make text representations of the file lists for 'queued' extractions
        fileListRow = createFileList(
            filter1, filter2, ".queued", pathName, extensions, userId);

        // check whether there were any files, and add to table.
        if (!"".equals(fileListRow)) {
            mainTable.addRow(ec.cr1ColRow("List of queued extractions:", true, "",
                IHAlign.CENTER));
            mainTable.addRow(ec.cr1ColRow(fileListRow, false, "", IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow("There are no queued extractions " +
                "for the selection criteria", true, "", IHAlign.CENTER));
        } // if (!"".equals(fileListRow))


        // make text representations of the file lists for 'in progress' extractions
        fileListRow = createFileList(
            filter1, filter2, ".processing", pathName, extensions, userId);

        // check whether there were any files, and add to table.
        if (!"".equals(fileListRow)) {
            mainTable.addRow(ec.cr1ColRow("List of extractions still processing:", true, "",
                IHAlign.CENTER));
            mainTable.addRow(ec.cr1ColRow(fileListRow, false, "", IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow("There are no extractions still processing " +
                "for the selection criteria", true, "", IHAlign.CENTER));
        } // if (!"".equals(fileListRow))

        // make text representations of the file lists for completed extractions
        fileListRow = createFileList(
            filter1, filter2, ".finished", pathName, extensions, userId);

        // check whether there were any files, and add to table.
        if (!"".equals(fileListRow)) {
            mainTable.addRow(ec.cr1ColRow("List of completed extractions:", true,
                "", IHAlign.CENTER));
            mainTable.addRow(ec.cr1ColRow(fileListRow, false, "", IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow("There are no completed extractions " +
                "for the selection criteria", true,
                "", IHAlign.CENTER));
        } // if (!"".equals(fileListRow))

        // add file lists to main table
        mainTable.addRow(ec.cr1ColRow(ec.downloadFileInstructions().toHTML(),
            false, "", IHAlign.CENTER));

        // add table to screen
        this.addItem(mainTable.setCenter());

    } // constructor


    /**
     * Create the list of file names depending on the various filters
     * @param   filter1     normally the request number
     * @param   filter2     normally the file name
     * @param   fileType    .queued, .processing or .finished
     * @param   pathName    directory name
     * @param   extensions  an array of extensions for the data type
     * @param   userId      the user's userid / home directory
     * @returns a string representing the list of files in a html table
     */
    String createFileList(String filter1, String filter2, String fileType,
            String pathName, String extensions[], String userId) {

        if (dbg) System.out.println("");
        if (dbg) System.out.println("<br>*** start of createFileList function");

        DynamicTable fileTable = new DynamicTable(1);
        fileTable.setFrame(ITableFrame.VOLD);
        fileTable.setRules(ITableRules.NONE);
        fileTable.setBorder(0);
        //fileTable.setFrame(ITableFrame.BOX);

        File path = new File(pathName);
        String[] fileList = path.list();

        boolean foundFiles = false;

        for (int j = 0; j < extensions.length; j++) {

            String filter3 = extensions[j] + fileType;
            if (dbg) System.out.println("<br>filter3 = " + filter3);

            for (int i = 0; i < fileList.length; i++) {

                if (fileList[i].startsWith(filter1) &&
                        (fileList[i].indexOf(filter2) > -1) &&
                        fileList[i].endsWith(filter3)) {

                    foundFiles = true;

                    int dotPosition = fileList[i].indexOf(filter3);
                    if (dbg) System.out.println("<br>dotPosition = " + dotPosition);

                    String tempFile = fileList[i].substring(0, dotPosition) + extensions[j];
                    if (dbg) System.out.println("<br>tempFile = " + tempFile);

                    // get file size only for .processing and .finished files
                    String fileSize = "";
                    if (!".queued".equals(fileType)) {
                        float length = new File(pathName + tempFile).length() / 1024.0f;
                        fileSize = " (" + ec.frm(length,10,3) + " kb)";
                    } // if (!".queued".equals(fileType))

                    if (".finished".equals(fileType)) {
                        Link link = new Link (
                            sc.DATA_URL + userId + "/" + tempFile, tempFile + fileSize);
                        fileTable.addRow(ec.cr1ColRow(link.toHTML()));
                    } else {
                        fileTable.addRow(ec.cr1ColRow(tempFile + fileSize));
                    } // if (".processing".equals(fileType))

                } // if (fileList[i].endsWith(filter)) {
            } // for i
        } // for j

        if (dbg) System.out.println ("<br>*** end of createFileList function");

        return (foundFiles ? fileTable.toHTML() : "");
    } // createFileList


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {
        try {
            // create the page head
            HtmlHead hd = new HtmlHead("ExtractProgressFrame local");

            // create the body and the page itself
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            hp.printHeader();

            //ExtractCURFrame local= new ExtractCURFrame(args,"wet");
            bd.addItem(new ExtractProgressFrame(args, "mrn"));

            hp.print();


        } catch(Exception e) {
        //  ec.processErrorStatic(e, thisClass, "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class ExtractProgressFrame
