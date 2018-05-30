package sadco;

import oracle.html.*;
import java.io.*;
import java.sql.*;

/**
 * This class loads the marine CTD data into the database
 *
 * SadcoMRN
 * screen.equals("load")
 * version.equals("load")
 * loadType.equals("currents")
 *
 * @author 030309 - SIT Group
 * @version
 * 030309 - Ursula von St Ange - create class                               <br>
 * 040610 - Ursula von St Ange - add loadtype for SadcoMRNApps (ub01)       <br>
 * 040625 - Ursula von St Ange - adapt for currents from LoadMRNWatFrame    <br>
 */
public class LoadMRNCurFrame extends CompoundItem {

    /** A general purpose class used to extract the url-type parameters*/
    static common.edmCommon ec = new common.edmCommon();
    SadConstants sc = new SadConstants();
    //LoadMRNCurCommon lc = new LoadMRNCurCommon();
    LoadMRNCommon lc = new LoadMRNCommon();

    //boolean dbg = false;
    boolean dbg = true;


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoadMRNCurFrame (String args[]) {

        try {
            LoadMRNCurFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadMRNCurFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadMRNCurFrameActual(String args[]) {

        // get the argument values
        String fileName   = ec.getArgument(args,sc.FILENAME);
        // for the top menu bar
        String sessionCode= ec.getArgument(args, sc.SESSIONCODE);
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);


        // get the corect path
        String rootPath = "";
        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIRL;
        } else {
            rootPath = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(ec.HOST))
        String fileName2 = rootPath + "marine/" + fileName;

        writeArgsToFile(args, fileName2);


        // submit the actual load
        String command = sc.JAVA + " " + sc.MRN_APP + "Apps " +
            sc.SCREEN + "=load " +
            sc.VERSION + "=doload " +
            sc.LOADTYPE + "=" + ec.getArgument(args, sc.LOADTYPE) + " " +    // ub01
            sc.FILENAME + "=" + fileName;

        if (dbg) System.out.println("<br>command = " + command);
        if (dbg) System.err.println("** LoadMRNCurFrame command = " + command);

        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob(command);
        } //if (ec.getHost().startsWith(sc.HOST)) {

        // the main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        // create the html page
        mainTable.addRow(ec.cr1ColRow("<font size=+2>Load Data</font>",
            true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow("File <b>" + fileName + "</b> is being" +
            " processed", false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("The <b>'Load Status'</b> page will "+
            "appear in 15 seconds", false, "", IHAlign.CENTER));

        Form form = new Form("POST", sc.MRN_APP);
        form.addItem(new Hidden(sc.SCREEN, "load"));
        form.addItem(new Hidden(sc.VERSION, "progress"));
        form.addItem(new Hidden(sc.FILENAME, fileName));
        // for the menu system
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        form.addItem(new Submit("", "OK"));

        // add components to screen
        this.addItem(mainTable.setCenter());
        this.addItem(form.setCenter());

    } // LoadMRNCurFrameActual


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void writeArgsToFile(String args[], String fileName2)   {

        RandomAccessFile workFile = lc.openOutputFile(fileName2 + ".work");
        ec.writeFileLine(workFile, String.valueOf(args.length));
        for (int i = 0; i < args.length; i++) {
            ec.writeFileLine(workFile, args[i]);
        } // for (int i = 0; i < numberOfLines; i++)

        try {
            workFile.close();
        } catch(Exception e) { } //

    }   //  writeArgsToFile


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("LoadMRNCurFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreLoadMRNCurFrame local= new PreLoadMRNCurFrame(args);
            bd.addItem(new LoadMRNCurFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "LoadMRNCurFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class LoadMRNCurFrame
