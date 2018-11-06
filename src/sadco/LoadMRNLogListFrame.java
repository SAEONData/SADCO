package sadco;

import oracle.html.*;
import java.io.*;
import menusp.*;

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
public class LoadMRNLogListFrame extends CompoundItem {

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
    public LoadMRNLogListFrame (String args[]) {

        try {
            LoadMRNLogListFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadMRNLogListFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadMRNLogListFrameActual(String args[]) {

        // get the argument values
        String loadId     = ec.getArgument(args,sc.LOADID);
        if (dbg) System.out.println("<br>loadId = " + loadId);

        // set up the order by and where clauses
        String order = LogMrnLoads.LOADID + "," + LogMrnLoads.SURVEY_ID;
        String where = "";
        if (!"".equals(loadId)) {
            where = LogMrnLoads.LOADID + " like '" + loadId + "%'";
        } // if (loadId.equals(""))
        if (dbg) System.out.println("<br>where = " + where);

        // get the data
        LogMrnLoads[] logs = new LogMrnLoads().get(where, order);
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
        mainTable.addRow(ec.cr1ColRow(createFileList(logs, args).toHTML()));
        mainTable.addRow(ec.cr1ColRow("<br>"));

        this.addItem(mainTable.setCenter());

    } // constructor


    /**
     * create the combo-box (pull-down box) for the file names
     */
    DynamicTable createFileList(LogMrnLoads[] logs, String[] args) {

        DynamicTable fileTable = new DynamicTable(11);
        fileTable.setBorder(1);
        fileTable.setFrame(ITableFrame.BOX);

        TableRow row = new TableRow();
        row.addCell(new TableHeaderCell("Load ID"));
        row.addCell(new TableHeaderCell("Survey ID"));
        row.addCell(new TableHeaderCell("Date Loaded"));
        row.addCell(new TableHeaderCell("Flagged"));
        row.addCell(new TableHeaderCell("Stations Loaded"));
        row.addCell(new TableHeaderCell("Source"));
        row.addCell(new TableHeaderCell("Resp<br>Person"));
        fileTable.addRow(row);

        String linkCommon = sc.MRN_APP + "?" + sc.SCREEN + "=load" +
            "&" + sc.VERSION + "=logdetail" +
            "&" + sc.USERTYPE + "=" + ec.getArgument(args,sc.USERTYPE) +
            "&" + sc.SESSIONCODE + "=" + ec.getArgument(args,sc.SESSIONCODE) +
            "&" + MnuConstants.MENUNO + "=" +
                ec.getArgument(args, MnuConstants.MENUNO) +
            "&" + MnuConstants.MVERSION + "=" +
                ec.getArgument(args, MnuConstants.MVERSION);

        for (int i = 0; i < logs.length; i++) {
            String dateLoaded = logs[i].getLoadDate("");
            if (dateLoaded.length() > 10) {
                dateLoaded = dateLoaded.substring(0,10);
            } // if (dateLoaded.length() > 10)
            Link link =
                new Link (linkCommon +
                    "&" + sc.LOADID + "=" +logs[i].getLoadid() +
                    "&" + sc.SURVEYID + "=" + logs[i].getSurveyId(),
                    logs[i].getLoadid());
            row = new TableRow();
            row.addCell(new TableDataCell(link.toHTML()));
            row.addCell(new TableDataCell(logs[i].getSurveyId("")));
            row.addCell(new TableDataCell(dateLoaded));
            row.addCell(new TableDataCell(logs[i].getFlagged("")));
            row.addCell(new TableDataCell(logs[i].getStationsLoaded("")));
            row.addCell(new TableDataCell(logs[i].getSource("")));
            row.addCell(new TableDataCell(logs[i].getRespPerson("")));

            if (i%2 == 0) {
                row.setBackgroundColor("#CCFFFF");
            } // if (i%2 == 0)

            fileTable.addRow(row);
        } // for i
        return fileTable;
    } // createFileListSelect


} // class LoadMRNLogListFrame
