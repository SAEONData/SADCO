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
 * Display the output files for download as soon as the product has finished.
 *
 * SadcoVOS
 * screen.equals("product")
 * version.equals("progress")
 *
 * @author  010807 - SIT Group
 * @version
 * 010807 - Ursula von St Ange - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020925 - Ursula von St Ange - now also have list of products in progress <br>
 * 091215 - Ursula von St Ange - change for inventory users (ub01)          <br>
 */
public class ProductProgressFrame extends CompoundItem {

    boolean dbg = false;
    //boolean dbg = true;
    String thisClass = this.getClass().getName();

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    SadConstants sc = new SadConstants();


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ProductProgressFrame (String args[], String applic) {

        try {
            ProductProgressFrameActual(args, applic);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ProductProgressFrame constructor


    /**
     * Does the actual work.
     * @param   args    the string array of url-type name-value parameter pairs
     * @param   applic  mrn / vos
     */
    void ProductProgressFrameActual(String args[], String applic) {

        String sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        String userType = ec.getArgument(args, sc.USERTYPE);
        String reqNumber = ec.getArgument(args, sc.REQNUMBER);
        String fileName = ec.getArgument(args, sc.FILENAME);
        String productStr = ec.getArgument(args, sc.PRODUCT); // not yet implemented in menu
        if (dbg) System.out.println("<br>productStr = " + productStr);
        // for the top menu bar
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // ................ EXTRACT THE USER record from db .....................
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        if (dbg) System.out.println("<br>Session code = " + sessionCode);

        // extract the userid from the record
        String userId = sessions[0].getUserid();
        if (dbg) System.out.println("<br>userId1 = "+userId);
        if ("0".equals(userType)) {  // from inventory                  //ub01
            userId = "inv_user";                                        //ub01
        } // if ("0".equals(userType))                                  //ub01
        if (dbg) System.out.println("<br>userId2 = "+userId);           //ub01

        // .................PATH NAME: Get the correct........................
        String pathName = "";
        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIR + userId + "/";
        } else {
            pathName = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(sc.HOST))
        if (dbg) System.out.println("<br>pathName = " + pathName);

        // first filter
        String filter1 = reqNumber;
        if (dbg) System.out.println("<br>filter1 = " + filter1);

        // second filter
        String filter2 = fileName;
        int dotPos = fileName.lastIndexOf(".");
        if (!"".equals(fileName) && (dotPos > 0)) {
            filter2 = fileName.substring(0, dotPos);
        } // if (!"".equals(fileName)) {
        if (dbg) System.out.println("<br>filter2 = " + filter2);

        // third filter
        String filter3 = "";
        if (!productStr.equals("")) {
            int product = Integer.parseInt(productStr.trim());
            if ("vos".equals(applic)) {
                filter3 = sc.VOSPRODLIST[product-1].substring(0,3).toLowerCase();
            } else {
                filter3 = sc.MRNPRODLIST[product-1].substring(0,3).toLowerCase();
            } // if ("vos".equals(applic))
        } // if (!fileName.equals(""))
        if (dbg) System.out.println("<br>filter3 = " + filter3);

//        // get the right extension
//        String extension = ".dbv"; // vos database
//        if ("mrn".equals(applic)) extension = ".dbm"; // marine db

        // fourth filter
        String filter4 = ".parms.finished";
        if (dbg) System.out.println ("<br>filter4 = " + filter4);

        // the products link
        Link productsLink = new Link(
            ("mrn".equals(applic) ? sc.MRN_APP : sc.VOS_APP) + "?" +
            sc.SCREEN + "=product&" +
            sc.VERSION + "=getfile&" +
            sc.USERTYPE + "=" + userType + "&" +
            sc.SESSIONCODE + "=" + sessionCode + "&" +
            menusp.MnuConstants.MENUNO + "=" + menuNo + "&" +
            menusp.MnuConstants.MVERSION + "=" + mVersion,
            "Choose another product");

        // the microsoft support download link for hpgl converter
        Link hpglLink = new Link(
            "http://support.microsoft.com/search/preview.aspx?scid=kb;en-us;Q196506",
            "download Office 2000 HPGL converter from MicosSoft support site");

        // get the file list
        File path = new File(pathName);
        String[] fileList = path.list();
        if (dbg) System.out.println ("<br>fileList.length = " + fileList.length);

        // the main table
        DynamicTable mainTable = new DynamicTable(2);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);
        mainTable.setCellPadding(10);

        mainTable.addRow(ec.cr1ColRow("<font size=+2>Status of Products & Download</font>",
            true, "blue", IHAlign.CENTER));
        //mainTable.addRow(ec.cr1ColRow("This page is refreshed every minute",
        mainTable.addRow(ec.cr1ColRow("This page is refreshed every 15 seconds.",
            true, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow(
            "<b>Criteria for selection of extractions:</b>" +
            "<br><b>Request Number:</b> " + reqNumber +
            "<br><b>File Name</b>: *" + fileName + "*", false, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("Once the output files appear on the list, " +
          "you can download them to your PC (instructions at the end of the page). " +
          "<br> &#149;The plot files are in HPGL format, and you can either send it to " +
          "any HP laser printer, or pull it into a MS Word document. <br>" +
          "&#149; To view the text base output(s) by clicking  on the file links. "+
          "All other files are in ascii.<br>(" + hpglLink.toHTML() + ")",
          false, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow(productsLink.toHTML(), false, "",
            IHAlign.CENTER));

        // check for uncompleted products
        DynamicTable fileTable = createPFileList(filter1, filter2, filter3,
            ".parms.processing", pathName, userId);
        if (fileTable != null) {
            mainTable.addRow(ec.cr1ColRow("List of products still processing:", true, "",
                IHAlign.CENTER));
            mainTable.addRow(ec.cr1ColRow(fileTable.toHTML(),false, "", IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow("There are no products still processing " +
                "for the selection criteria", true, "", IHAlign.CENTER));
        } // if (fileTable != null)

        fileTable = createFileList(filter1, filter2, filter3, filter4,
            pathName, userId);
        if (fileTable != null) {
            mainTable.addRow(ec.cr1ColRow("List of completed products:", true, "",
                IHAlign.CENTER));
            mainTable.addRow(ec.cr1ColRow(fileTable.toHTML(),false, "", IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow("There are no completed products " +
                "for the selection criteria", true, "", IHAlign.CENTER));
        } // if (fileTable != null)

        mainTable.addRow(ec.cr1ColRow(ec.downloadFileInstructions().toHTML(),
            false, "", IHAlign.CENTER));

        this.addItem(mainTable.setCenter());

    } // ProductProgressFrameActual


    /**
     * create the list of file names to download
     * @param   filter1     normally the request number
     * @param   filter2     normally the file name
     * @param   filter3     a product identifier
     * @param   filter4     .parms.finished
     * @param   pathName    directory name
     * @param   userId      the user's userid / home directory
     * @returns an html table containing the list of file names
     */
    DynamicTable createFileList(String filter1, String filter2, String filter3,
            String filter4, String pathName, String userId) {

        if (dbg) System.out.println("");
        if (dbg) System.out.println("<br>*** start of createFileList function");
        if (dbg) System.out.println("<br>filter4 = " + filter3);

        DynamicTable fileTable = new DynamicTable(1);
        fileTable.setFrame(ITableFrame.VOLD);
        fileTable.setRules(ITableRules.NONE);
        fileTable.setBorder(0);
        //fileTable.setFrame(ITableFrame.BOX);

        File path = new File(pathName);
        String[] fileList = path.list();

        //if (fileList.length == 0) {
        //    fileTable.addRow(ec.cr1ColRow("<br>The product has not yet completed"));
        //} // if (fileList.length == 0)

        for (int i = 0; i < fileList.length; i++) {
            //if (dbg) System.out.println("<br>" + fileList[i] + " " +
            //    fileList[i].startsWith(filter1) + " " +
            //    fileList[i].indexOf(filter2) + " " +
            //    fileList[i].indexOf(filter3) + " " +
            //    fileList[i].endsWith(filter4));
            if (fileList[i].startsWith(filter1) &&
                    (fileList[i].indexOf(filter2) > -1) &&
                    (fileList[i].indexOf(filter3) > -1) &&
                    fileList[i].endsWith(filter4)) {

                if (dbg) System.out.println("<br>createFileList: fileList[i] = "+fileList[i]);

                // 'normal' product output files
                fileTable = addFile2List(fileTable, pathName, userId, fileList[i], ".plot");
                fileTable = addFile2List(fileTable, pathName, userId, fileList[i], ".list");
                fileTable = addFile2List(fileTable, pathName, userId, fileList[i], ".msg");

                // 'other formats' output files
                fileTable = addFile2List(fileTable, pathName, userId, fileList[i], ".bot");
                fileTable = addFile2List(fileTable, pathName, userId, fileList[i], ".ond");
                fileTable = addFile2List(fileTable, pathName, userId, fileList[i], ".red");
                fileTable = addFile2List(fileTable, pathName, userId, fileList[i], ".pos");
                fileTable = addFile2List(fileTable, pathName, userId, fileList[i], ".stn");

            } // if (fileList[i].startsWith(filter1) ...
        } // for i
        if (dbg) System.out.println ("<br>*** end of createFileList function");
        return ((fileList.length > 0) ? fileTable : null);
        //return fileTable;
    } // createFileListSelect


    /**
     * create the list of products that are not yet completed.
     * @param   filter1     normally the request number
     * @param   filter2     normally the file name
     * @param   filter3     a product identifier
     * @param   filter4     .parms.processing
     * @param   pathName    directory name
     * @param   userId      the user's userid / home directory
     * @returns an html table containing the list of file names
     */
    DynamicTable createPFileList(String filter1, String filter2, String filter3,
            String filter4, String pathName, String userId) {

        if (dbg) System.out.println("");
        if (dbg) System.out.println("<br>*** start of createPFileList function");
        if (dbg) System.out.println("<br>filter4 = " + filter3);

        DynamicTable fileTable = new DynamicTable(1);
        fileTable.setFrame(ITableFrame.VOLD);
        fileTable.setRules(ITableRules.NONE);
        fileTable.setBorder(0);
        //fileTable.setFrame(ITableFrame.BOX);

        File path = new File(pathName);
        String[] fileList = path.list();

        for (int i = 0; i < fileList.length; i++) {
            //if (dbg) System.out.println("<br>" + fileList[i] + " " +
            //    fileList[i].startsWith(filter1) + " " +
            //    fileList[i].indexOf(filter2) + " " +
            //    fileList[i].indexOf(filter3) + " " +
            //    fileList[i].endsWith(filter4));
            if (fileList[i].startsWith(filter1) &&
                    (fileList[i].indexOf(filter2) > -1) &&
                    (fileList[i].indexOf(filter3) > -1) &&
                    fileList[i].endsWith(filter4)) {

                if (dbg) System.out.println("<br>createPFileList: fileList[i] = "+fileList[i]);

                int dotPosition = fileList[i].lastIndexOf('.');  // ub99
                if (dbg) System.out.println("<br>createPFileList: dotPosition = " + dotPosition);

                String tempFile = fileList[i].substring(0, dotPosition);
                if (dbg) System.out.println("<br>createPFileList: tempFile = " + pathName + tempFile);
                fileTable.addRow(ec.cr1ColRow(tempFile));

            } // if (fileList[i].startsWith(filter1) ...
        } // for i
        if (dbg) System.out.println ("<br>*** end of createPFileList function");
        return ((fileList.length > 0) ? fileTable : null);
        //return fileTable;
    } // createPFileListSelect


    /**
     * add the file name to the list
     * @param   fileTable   the html table to add the file to
     * @param   userId      the user's username / home directory
     * @param   fileName    the name of the file to add to the list
     * @param   extension   the extension of the file
     * @returns an html table containing the list of file names
     */
    DynamicTable addFile2List(
            DynamicTable fileTable,
            String pathName,
            String userId,
            String fileName,
            String extension) {

        int dotPosition = fileName.lastIndexOf('.');
        dotPosition = fileName.lastIndexOf('.', dotPosition-1);
        if (dbg) System.out.println("<br>addFile2List: dotPosition = " + dotPosition);

        String tempFile = fileName.substring(0, dotPosition) + extension;
        float length = new File(pathName + tempFile).length() / 1024.0f;
        if (dbg) System.out.println("<br>addFile2List: tempFile = " + pathName + tempFile +
            " " + length);

        if (ec.fileExists(pathName + tempFile)) {
            if (dbg) System.out.println("<br>addFile2List: tempFile = " + tempFile);
            Link link =
                new Link (sc.DATA_URL + userId + "/" + tempFile, tempFile + " (" +
                ec.frm(length,10,3) + " kb)");
            fileTable.addRow(ec.cr1ColRow(link.toHTML()));
        } // if (ec.fileExists(pathName + tempFile))
        return fileTable;

    } // addFile2List


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {
        try {
            // create the page head
            HtmlHead hd = new HtmlHead("ProductProgressFrame local");

            // create the body and the page itself
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            hp.printHeader();

            bd.addItem(new ProductProgressFrame(args, "mrn"));
            hp.print();

        } catch(Exception e) {
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch
    } // main

} // ProductProgressFrame class
