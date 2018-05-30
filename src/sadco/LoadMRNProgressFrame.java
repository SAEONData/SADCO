package sadco;

import oracle.html.*;
import java.io.*;

/**
 * Displays the report files for download if the loading has finished.
 *
 * SadcoVOS
 * screen.equals("load")
 * version.equals("progress")
 *
 * @author  001123 - SIT Group
 * @version
 * 001123 - Ursula von St Ange - create class<br>
 * 020528 - Ursula von St Ange - update for non-frame menus<br>
 * 020528 - Ursula von St Ange - update for exception catching<br>
 */
public class LoadMRNProgressFrame extends CompoundItem {

    /** some common functions */
    static common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    // file stuff
    String      pathName;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoadMRNProgressFrame (String args[]) {

        try {
            LoadMRNProgressFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadMRNProgressFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadMRNProgressFrameActual(String args[]) {

        String fileName   = ec.getArgument(args,sc.FILENAME);

        // get the correct path name
        //if (ec.getHost().startsWith("morph")) {
        //    pathName = sc.MORPHL + "vos/";
        //} else {
        //    pathName = sc.LOCAL;
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL + "marine/";
        } else {
            pathName = sc.LOCALDIR + "marine/";
        } // if host

        if (dbg) System.out.println ("<br>Pathname = " + pathName);

        // first filter
        String filter1 = fileName;
        int dotPos = fileName.indexOf(".");
        if (!"".equals(fileName) && (dotPos > 0)) {
            filter1 = fileName.substring(0, dotPos);
        } // if (!"".equals(fileName)) {
        if (dbg) System.out.println("<br>filter1 = " + filter1);

        // second filter
        //String filter2= ".dat.finished";
        //if (dbg) System.out.println ("<br>filter2 = " + filter2);

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

        mainTable.addRow(ec.cr1ColRow("<font size=+2>Status of Loading & Download</font>",
            true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow("This page refreshes itself every 15 seconds.",
            true, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("When your load / load report is finished, " +
            "the report will be availabe for download", true, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));

        DynamicTable fileTable = createFileList(fileList, filter1, ".processing", "proc");
        //if (dbg) System.out.println("<br>fileTable = " + fileTable.toHTML());
        if (fileTable != null) {
            mainTable.addRow(ec.cr1ColRow("List of loads / load reports " +
                "still processing:", true, "", IHAlign.CENTER));
            mainTable.addRow(ec.cr1ColRow(fileTable.toHTML(),false, "", IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow("There are no loads / load reports " +
                "still processing for the selection criteria", true, "",
                IHAlign.CENTER));
        } // if (fileTable != null)
        mainTable.addRow(ec.cr1ColRow("<br>"));

        fileTable = createFileList(fileList, filter1, ".finished", "fin");
        if (fileTable != null) {
            mainTable.addRow(ec.cr1ColRow("List of completed loads / load reports:",
                true, "", IHAlign.CENTER));
            mainTable.addRow(ec.cr1ColRow(fileTable.toHTML(),false, "",
                IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow("There are no completed loads / " +
                "load reports for the selection criteria", true, "",
                IHAlign.CENTER));
        } // if (fileTable != null)
        mainTable.addRow(ec.cr1ColRow("<br>"));

        mainTable.addRow(ec.cr1ColRow("When your load / load report is finished, the " +
            "report will be availabe for download", false, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow(ec.downloadFileInstructions().toHTML(),
            false, "", IHAlign.CENTER));

        this.addItem(mainTable.setCenter());

    } // LoadMRNProgressFrameActual


    /**
     * create the combo-box (pull-down box) for the file names
     */
    DynamicTable createFileList(String[] fileList, String filter1, String filter2,
            String action) {

        DynamicTable fileTable = new DynamicTable(1);
        fileTable.setFrame(ITableFrame.VOLD);
        fileTable.setRules(ITableRules.NONE);
        fileTable.setBorder(0);
        //fileTable.setFrame(ITableFrame.BOX);

        if (dbg) System.out.println("<br>createFileList: filter1 = " +
            filter1 + ", filter2 = " + filter2);

        boolean foundFiles = false;
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].startsWith(filter1) &&
                    fileList[i].endsWith(filter2)) {
                int dotPosition = fileList[i].indexOf('.');
                if (dbg) System.out.println(
                    "<br>createFileList: dotPosition = " + dotPosition);
                foundFiles = true;

                // report file for preload report
                String tempFile = fileList[i].substring(0, dotPosition) +
                    ".dat.preload_report";
                fileTable = addLinkText(tempFile, action, fileTable);

                // report file for final report
                tempFile = fileList[i].substring(0, dotPosition) + ".dat.load_report";
                fileTable = addLinkText(tempFile, action, fileTable);

                // report file for physnut report
                tempFile = fileList[i].substring(0, dotPosition) + ".physnut.loadrep";
                fileTable = addLinkText(tempFile, action, fileTable);

                // report file for watchm1 report
                tempFile = fileList[i].substring(0, dotPosition) + ".watchm1.loadrep";
                fileTable = addLinkText(tempFile, action, fileTable);

            } // if (fileList[i].startsWith(filter1)

        } // for i
        if (dbg) System.out.println("<br>createFileList: foundFIles = " + foundFiles);
        return (foundFiles ? fileTable : null);
    } // createFileListSelect


    /**
     * depending on the action, create the table row contents
     * @param tempFile  the file to add
     * @param action    = "proc" of "fin"
     * @param fileTable the table to add the row to
     * @return table
     */
    DynamicTable addLinkText(String tempFile, String action, DynamicTable fileTable) {
        if (dbg) System.out.println(
            "<br>createFileList.addLinkText: tempFile = " + pathName + tempFile);
        if (ec.fileExists(pathName + tempFile)) {
            float length = new File(pathName + tempFile).length() / 1024.0f;

            if (dbg) System.out.println("<br>createFileList.addLinkText: file exists");
            if ("fin".equals(action)) {
                Link link =
                    new Link (sc.LOADS_URL + "marine/" + tempFile, tempFile + " (" +
                        ec.frm(length,10,3) + " kb)");
                fileTable.addRow(ec.cr1ColRow(link.toHTML()));
            } else {
                fileTable.addRow(ec.cr1ColRow(tempFile + " (" +
                    ec.frm(length,10,3) + " kb)"));
            } // if ("fin".equals(action))
        } // if (ec.fileExists(pathName + tempFile))
        return fileTable;
    } //  addLinkText



    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("LoadMRNProgressFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //LoadMRNProgressFrame local= new LoadMRNProgressFrame(args);
            bd.addItem(new LoadMRNProgressFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "LoadMRNProgressFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class LoadMRNProgressFrame
