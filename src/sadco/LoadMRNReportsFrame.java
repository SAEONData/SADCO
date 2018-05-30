package sadco;

import oracle.html.*;
import java.io.*;
import java.sql.*;

/**
 * This class loads the marine CTD data into the database
 *
 * @author 030309 - SIT Group
 * @version
 * 030309 - Ursula von St Ange - create class<br>
 */
public class LoadMRNReportsFrame extends CompoundItem {

    /** A general purpose class used to extract the url-type parameters*/
    static common.edmCommon ec = new common.edmCommon();
    SadConstants sc = new SadConstants();
    LoadMRNWatCommon lc = new LoadMRNWatCommon();

    boolean dbg = false;
    //boolean dbg = true;


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoadMRNReportsFrame (String args[]) {

        try {
            LoadMRNReportsFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadMRNReportsFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadMRNReportsFrameActual(String args[]) {

        // get the argument values
        String surveyId = ec.getArgument(args,sc.SURVEYID);
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
        String surveyId2 = surveyId.replace('/','_');
        String fileName = rootPath + "marine/" + surveyId2;
        String physnutFileName = fileName + ".physnut.loadrep";
        String watchm1FileName = fileName + ".watchm1.loadrep";
        //String fileName2 = rootPath + "marine/" + fileName;

        // submit the actual load
        String command = sc.JAVA + " " + sc.MRN_APP + "Apps " +
            sc.SCREEN + "=load " +
            sc.VERSION + "=reports " +
            sc.SURVEYID + "=" + surveyId;

        if (dbg) System.out.println("<br>command = " + command);
        if (dbg) System.err.println("** LoadMRNReportsFrame command = " + command);

        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob(command);
        } //if (ec.getHost().startsWith(sc.HOST)) {

        // the main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setBorder(0);
        mainTable.setFrame(ITableFrame.BOX);

        // create the html page
        mainTable.addRow(ec.cr1ColRow("<font size=+2>Generate Load Reports</font>",
            true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow("The load reports for survey <b>" + surveyId +
            "</b> are being processed", false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("The <b>'Report Generation Status'</b> page will "+
            "appear in 15 seconds", false, "", IHAlign.CENTER));

        Form form = new Form("POST", sc.MRN_APP);
        form.addItem(new Hidden(sc.SCREEN, "load"));
        form.addItem(new Hidden(sc.VERSION, "progress"));
        form.addItem(new Hidden(sc.FILENAME, surveyId2));
        // for the menu system
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        form.addItem(new Submit("", "OK"));

        // add components to screen
        this.addItem(mainTable.setCenter());
        this.addItem(form.setCenter());

    } // LoadMRNReportsFrameActual


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("LoadMRNReportsFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreLoadMRNReportsFrame local= new PreLoadMRNReportsFrame(args);
            bd.addItem(new LoadMRNReportsFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "LoadMRNReportsFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class LoadMRNReportsFrame
