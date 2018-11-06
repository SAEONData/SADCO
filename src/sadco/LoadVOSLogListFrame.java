package sadco;

import oracle.html.*;
import java.io.*;

/**
 * Displays a details of previous loads
 *
 * SadcoVOS
 * screen.equals("load")
 * version.equals("loglist")
 *
 * @author  001129 - SIT Group
 * @version
 * 001129 - Ursula von St Ange - create class<br>
 * 020528 - Ursula von St Ange - update for non-frame menus<br>
 * 020528 - Ursula von St Ange - update for exception catching<br>
 */
public class LoadVOSLogListFrame extends CompoundItem {

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
    public LoadVOSLogListFrame (String args[]) {

        try {
            LoadVOSLogListFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadVOSLogListFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadVOSLogListFrameActual(String args[]) {

        // get the argument values
        String loadId     = ec.getArgument(args,sc.LOADID);
        if (dbg) System.out.println("<br>loadId = " + loadId);

        // set up the order by and where clauses
        String order = LogVosLoads.LOADID + "," + LogVosLoads.VOS_LOAD_ID;
        String where = "";
        if (!"".equals(loadId)) {
            where = LogVosLoads.LOADID + " like '" + loadId + "%'";
        } // if (loadId.equals(""))
        if (dbg) System.out.println("<br>where = " + where);

        // get the data
        LogVosLoads[] logs = new LogVosLoads().get(where, order);
        if (dbg) System.out.println("<br>logs.length = " + logs.length);


        // the main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        mainTable.addRow(ec.cr1ColRow(
            "<font size=+2>Load Logs</font>", true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow(createFileList(logs).toHTML()));
        mainTable.addRow(ec.cr1ColRow("<br>"));

        this.addItem(mainTable.setCenter());

    } // constructor


    /**
     * create the combo-box (pull-down box) for the file names
     */
    DynamicTable createFileList(LogVosLoads[] logs) {

        DynamicTable fileTable = new DynamicTable(11);
        fileTable.setBorder(1);
        fileTable.setFrame(ITableFrame.BOX);

        TableRow row = new TableRow();
        row.addCell(new TableHeaderCell("Load ID"));
        row.addCell(new TableHeaderCell("VOS<br>Load ID"));
        row.addCell(new TableHeaderCell("Date<br>Loaded"));
        row.addCell(new TableHeaderCell("Main<br>Recs<br>Loaded"));
        row.addCell(new TableHeaderCell("Main<br>Dup<br>Recs"));
        row.addCell(new TableHeaderCell("Arch<br>Recs<br>Loaded"));
        row.addCell(new TableHeaderCell("Arch<br>Dup<br>Recs"));
        row.addCell(new TableHeaderCell("Start<br>Date"));
        row.addCell(new TableHeaderCell("End<br>Date"));
        row.addCell(new TableHeaderCell("Source"));
        row.addCell(new TableHeaderCell("Resp<br>Person"));
        fileTable.addRow(row);

        for (int i = 0; i < logs.length; i++) {
            //Link link =
            //    new Link ("SadcoVOS?pscreen=load&pversion=logs&ploadid=" +
            //        logs[i].getLoadid(), logs[i].getLoadid());
            row = new TableRow();
            row.addCell(new TableDataCell(logs[i].getLoadid()));
            row.addCell(new TableDataCell(logs[i].getVosLoadId("")));
            row.addCell(new TableDataCell(
                logs[i].getDateLoaded("").substring(0,10)));
            row.addCell(new TableDataCell(logs[i].getMRecsLoaded("")));
            row.addCell(new TableDataCell(logs[i].getMRecsDup("")));
            row.addCell(new TableDataCell(logs[i].getARecsLoaded("")));
            row.addCell(new TableDataCell(logs[i].getARecsDup("")));
            row.addCell(new TableDataCell(logs[i].getDateStart("").substring(0,10)));
            row.addCell(new TableDataCell(logs[i].getDateEnd("").substring(0,10)));
            row.addCell(new TableDataCell(logs[i].getSource()));
            row.addCell(new TableDataCell(logs[i].getRespPerson()));

            if (i%2 == 0) {
                row.setBackgroundColor("#CCFFFF");
            } // if (i%2 == 0)

            fileTable.addRow(row);
        } // for i
        return fileTable;
    } // createFileListSelect


} // class LoadVOSLogListFrame
