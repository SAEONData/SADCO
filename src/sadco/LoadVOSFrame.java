package sadco;

import oracle.html.*;
import java.io.*;

/**
 * Creates a drop-down of file names to select
 *
 * SadcoVOS
 * screen.equals("load")
 * version.equals("load")
 *
 * @author  001123 - SIT Group
 * @version
 * 001123 - Ursula von St Ange - create class<br>
 * 020528 - Ursula von St Ange - update for non-frame menus<br>
 * 020528 - Ursula von St Ange - update for exception catching<br>
 */
public class LoadVOSFrame extends CompoundItem {

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoadVOSFrame (String args[]) {

        try {
            LoadVOSFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadVOSFrame constructor

    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadVOSFrameActual(String args[]) {

        // get arguments used more than once
        String fileName   = ec.getArgument(args,sc.FILENAME);
        // for the top menu bar
        String sessionCode= ec.getArgument(args, sc.SESSIONCODE);
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // submit the actual load
        String command = sc.JAVA + " " + sc.VOS_APP + "Apps " +
            sc.SCREEN + "=load " +
            sc.VERSION + "=doload " +
            sc.LOADID + "=" + ec.getArgument(args,sc.LOADID) + " " +
            sc.VOSLOADID + "=" + ec.getArgument(args,sc.VOSLOADID) + " " +
            sc.SOURCE + "=" + ec.getArgument(args,sc.SOURCE) + " " +
            sc.RESPPERSON + "=" + ec.getArgument(args,sc.RESPPERSON) + " " +
            sc.FILENAME + "=" + fileName + " " +
            sc.TIMEOUT + "=n"; // +
            //" > /oracle/users/sadco/loads/vos/load.log";

        if (dbg) System.out.println("<br>command = " + command);
        System.err.println("** LoadVOSFrame command = " + command);

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

        // create the form
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST", sc.VOS_APP, target);
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

    } // LoadVOSFrameActual

} // LoadVOSFrame class









