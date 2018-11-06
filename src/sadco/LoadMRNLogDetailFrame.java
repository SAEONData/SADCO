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
public class LoadMRNLogDetailFrame extends CompoundItem {

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
    public LoadMRNLogDetailFrame (String args[]) {

        try {
            LoadMRNLogDetailFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadMRNLogDetailFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadMRNLogDetailFrameActual(String args[]) {

        // get the argument values
        String loadId     = ec.getArgument(args,sc.LOADID);
        String surveyId     = ec.getArgument(args,sc.SURVEYID);
        if (dbg) System.out.println("<br>loadId = " + loadId);
        if (dbg) System.out.println("<br>surveyId = " + surveyId);

        // set up the order by and where clauses
        String where = LogMrnLoads.LOADID + " = '" + loadId + "' and " +
            LogMrnLoads.SURVEY_ID + " = '" + surveyId + "'";
        if (dbg) System.out.println("<br>where = " + where);

        // get the data
        LogMrnLoads[] logs = new LogMrnLoads().get(where);
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
        mainTable.addRow(ec.cr1ColRow("Load ID: " + logs[0].getLoadid(),
            true, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("Survey ID: " + logs[0].getSurveyId(),
            true, "", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow(createDetailList(logs).toHTML()));
        mainTable.addRow(ec.cr1ColRow("<br>"));

        this.addItem(mainTable.setCenter());

    } // constructor


    /**
     * create the combo-box (pull-down box) for the file names
     */
    DynamicTable createDetailList(LogMrnLoads[] logs) {

        DynamicTable fileTable = new DynamicTable(2);
        fileTable.setFrame(ITableFrame.VOLD);
        fileTable.setRules(ITableRules.NONE);
        fileTable.setBorder(0);
        //fileTable.setFrame(ITableFrame.BOX);

        String dateLoaded = logs[0].getLoadDate("");
        if (dateLoaded.length() > 10) {
            dateLoaded = dateLoaded.substring(0,10);
        } // if (dateLoaded.length() > 10)
        String dateStart = logs[0].getDateStart("");
        if (dateStart.length() > 10) {
            dateStart = dateStart.substring(0,10);
        } // if (dateStart.length() > 10)
        String dateEnd = logs[0].getDateEnd("");
        if (dateEnd.length() > 10) {
            dateEnd = dateEnd.substring(0,10);
        } // if (dateEnd.length() > 10)

        //fileTable.addRow(ec.cr2ColRow("Load ID:", logs[0].getLoadid()));
        //fileTable.addRow(ec.cr2ColRow("Survey ID:", logs[0].getSurveyId()));
        fileTable.addRow(ec.cr2ColRow("Date Loaded:", dateLoaded));
        fileTable.addRow(ec.cr2ColRow("Institute:", logs[0].getInstitute("")));
        fileTable.addRow(ec.cr2ColRow("Expedition Name:", logs[0].getExpnam("")));
        fileTable.addRow(ec.cr2ColRow("Flagged:", logs[0].getFlagged("")));
        fileTable.addRow(ec.cr2ColRow("Stations Loaded:", logs[0].getStationsLoaded("")));
        fileTable.addRow(ec.cr2ColRow("Date Range:",
            dateStart + " <b>to</b> " + dateEnd));
        fileTable.addRow(ec.cr2ColRow("Latitude Range:",
            logs[0].getLatNorth("") + " <b>to</b> " +logs[0].getLatSouth("")));
        fileTable.addRow(ec.cr2ColRow("Longitude Range:",
            logs[0].getLongWest("") + " <b>to</b> " +logs[0].getLongEast("")));
        fileTable.addRow(ec.cr2ColRow("Watphy Code Range:",
            logs[0].getRecnumStart("") + " <b>to</b> " +logs[0].getRecnumEnd("")));
        fileTable.addRow(ec.cr2ColRow("Records Loaded:", logs[0].getRecordsLoaded("")));
        fileTable.addRow(ec.cr2ColRow("Records Rejected:", logs[0].getRecordsRejected("")));
        fileTable.addRow(ec.cr2ColRow("Source:", logs[0].getSource("")));
        fileTable.addRow(ec.cr2ColRow("Responsible Person:", logs[0].getRespPerson("")));
/*
        TableRow row = new TableRow();
        row.addCell(new TableHeaderCell("Load ID"));
        row.addCell(new TableHeaderCell("Survey ID"));
        row.addCell(new TableHeaderCell("Date Loaded"));
        row.addCell(new TableHeaderCell("Flagged"));
        row.addCell(new TableHeaderCell("Stations Loaded"));
        row.addCell(new TableHeaderCell("Source"));
        row.addCell(new TableHeaderCell("Resp<br>Person"));
        fileTable.addRow(row);

        for (int i = 0; i < logs.length; i++) {
            String dateLoaded = logs[i].getLoadDate("");
            if (dateLoaded.length() > 10) {
                dateLoaded = dateLoaded.substring(0,10);
            } // if (dateLoaded.length() > 10)
            Link link =
                new Link ("SadcoMrn?pscreen=load&pversion=logdetail&ploadid=" +
                    logs[i].getLoadid()+"&psurveyid="+logs[i].getSurveyId(),
                    logs[i].getLoadid());
            row = new TableRow();
            row.addCell(new TableDataCell(link.toHTML()));
            row.addCell(new TableDataCell(logs[i].getSurveyId("")));
            row.addCell(new TableDataCell(dateLoaded));
            row.addCell(new TableDataCell(logs[i].getFlagged("")));
            row.addCell(new TableDataCell(logs[i].getStationsLoaded("")));
            row.addCell(new TableDataCell(logs[i].getSource()));
            row.addCell(new TableDataCell(logs[i].getRespPerson()));

            if (i%2 == 0) {
                row.setBackgroundColor("#CCFFFF");
            } // if (i%2 == 0)

            fileTable.addRow(row);
        } // for i
*/
        return fileTable;
    } // createFileListSelect


} // class LoadMRNLogDetailFrame
