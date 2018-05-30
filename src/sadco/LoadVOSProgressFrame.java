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
public class LoadVOSProgressFrame extends CompoundItem {

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
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
    public LoadVOSProgressFrame (String args[]) {

        try {
            LoadVOSProgressFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadVOSProgressFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadVOSProgressFrameActual(String args[]) {

        String fileName   = ec.getArgument(args,sc.FILENAME);

        // get the correct path name
        //if (ec.getHost().equals("morph")) {
        //    pathName = sc.MORPHL + "vos/";
        //} else {
        //    pathName = sc.LOCAL;
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL + "vos/";
        } else {
            pathName = sc.LOCALDIR + "vos/";
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
        String filter2= ".inp.finished";
        if (dbg) System.out.println ("<br>filter2 = " + filter2);

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
        mainTable.addRow(ec.cr1ColRow("When your load is finished, the " +
            "report will be availabe for download", true, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));

        DynamicTable fileTable = createFileList(fileList, filter1, ".inp.processing");
        //if (dbg) System.out.println("fileTable = *" + fileTable.toHTML() + "*");
        if (fileTable != null) {
            mainTable.addRow(ec.cr1ColRow("List of loads still processing:", true, "",
                IHAlign.CENTER));
            mainTable.addRow(ec.cr1ColRow(fileTable.toHTML(),false, "", IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow("There are no loads still processing " +
                "for the selection criteria", true, "", IHAlign.CENTER));
        } // if (fileTable != null)
        mainTable.addRow(ec.cr1ColRow("<br>"));

        fileTable = createFileList(fileList, filter1, filter2);
        if (fileTable != null) {
            mainTable.addRow(ec.cr1ColRow("List of completed Loads:", true, "",
                IHAlign.CENTER));
            mainTable.addRow(ec.cr1ColRow(fileTable.toHTML(),false, "", IHAlign.CENTER));
        } else {
            mainTable.addRow(ec.cr1ColRow("There are no completed loads " +
                "for the selection criteria", true, "", IHAlign.CENTER));
        } // if (fileTable != null)

        mainTable.addRow(ec.cr1ColRow("When your load is finished, the " +
            "report will be availabe for download", false, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow(ec.downloadFileInstructions().toHTML(),
            false, "", IHAlign.CENTER));

        this.addItem(mainTable.setCenter());

    } // LoadVOSProgressFrameActual


    /**
     * create the combo-box (pull-down box) for the file names
     */
    DynamicTable createFileList(String[] fileList, String filter1, String filter2) {

        DynamicTable fileTable = new DynamicTable(1);
        fileTable.setFrame(ITableFrame.VOLD);
        fileTable.setRules(ITableRules.NONE);
        fileTable.setBorder(0);
        //fileTable.setFrame(ITableFrame.BOX);

        boolean foundFiles = false;
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].startsWith(filter1) &&
                    fileList[i].endsWith(filter2)) {
                int dotPosition = fileList[i].indexOf('.');
                if (dbg) System.out.println("<br>dotPosition = " + dotPosition);
                foundFiles = true;

                // report file
                String tempFile = fileList[i].substring(0, dotPosition) + ".load.report";
                if (dbg) System.out.println("<br>tempFile = " + pathName + tempFile);
                if (ec.fileExists(pathName + tempFile)) {
                    Link link =
                        new Link (sc.LOADS_URL + "vos/" + tempFile, tempFile);
                    fileTable.addRow(ec.cr1ColRow(link.toHTML()));
                } // if (ec.fileExists(pathName + tempFile))

                // daily stats file
                tempFile = fileList[i].substring(0, dotPosition) + ".daily";
                if (dbg) System.out.println("<br>tempFile = " + pathName + tempFile);
                if (ec.fileExists(pathName + tempFile)) {
                    Link link =
                        new Link (sc.LOADS_URL + "vos/" + tempFile, tempFile);
                    fileTable.addRow(ec.cr1ColRow(link.toHTML()));
                } // if (ec.fileExists(pathName + tempFile))

                // error file method for rejected and warning data
                //tempFile = fileList[i].substring(0, dotPosition) + ".errors";
                //if (dbg) System.out.println("<br>tempFile = " + pathName + tempFile);
                //if (ec.fileExists(pathName + tempFile)) {
                //    Link link =
                //        new Link (sc.LOADS_URL + "vos/" + tempFile, tempFile);
                //    fileTable.addRow(ec.cr1ColRow(link.toHTML()));

                // error file method for rejected and warning data
                tempFile = fileList[i].substring(0, dotPosition) + ".rejected";
                if (dbg) System.out.println("<br>tempFile = " + pathName + tempFile);
                if (ec.fileExists(pathName + tempFile)) {
                    Link link =
                        new Link (sc.LOADS_URL + "vos/" + tempFile, tempFile);
                    fileTable.addRow(ec.cr1ColRow(link.toHTML()));
                } // if (ec.fileExists(pathName + tempFile))

                // error file method for rejected and warning data
                tempFile = fileList[i].substring(0, dotPosition) + ".warning";
                if (dbg) System.out.println("<br>tempFile = " + pathName + tempFile);
                if (ec.fileExists(pathName + tempFile)) {
                    Link link =
                        new Link (sc.LOADS_URL + "vos/" + tempFile, tempFile);
                    fileTable.addRow(ec.cr1ColRow(link.toHTML()));

                } // if (ec.fileExists(pathName + tempFile))

            } // if (fileList[i].endsWith(filter)) {
        } // for i
        return (foundFiles ? fileTable : null);
    } // createFileListSelect


} // class LoadVOSProgressFrame
