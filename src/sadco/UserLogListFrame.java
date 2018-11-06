//+------------------------------------------------+----------------+--------+
//| CLASS: ....... UserLogListFrame                | JAVA PROGRAM   | 010819 |
//+------------------------------------------------+----------------+--------+
//| PROGRAMMER: .. SIT Group                                                 |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//| Sadco                                                                    |
//| screen.equals("user")                                                    |
//| version.equals("loglist")                                                |
//|                                                                          |
//|                                                                          |
//|                                                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 010819  Ursula von St Ange   create class                                |
//+--------------------------------------------------------------------------+

package sadco;

import oracle.html.*;
import java.io.*;

/**
 */
public class UserLogListFrame extends CompoundItem {

    /** some common functions */
    static common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    public UserLogListFrame(String args[]) {

        // get the argument values
        String userId     = ec.getArgument(args,sc.USERID);
        String startDate  = ec.getArgument(args,sc.STARTDATE);
        if (dbg) System.out.println("<br>userId = " + userId);

        String where = "";
        LogUsers[] logs;
        if (!"".equals(userId)) {
            where += LogUsers.USERID + " like '" + userId + "%'";
        } // if (!"".equals(userId))

        if (!"".equals(startDate)) {
            if (!"".equals(where)) where += " and ";
            where += LogUsers.DATE_TIME + " > " + Tables.getDateFormat(startDate);
        } // if (!"".equals(startDate))

        String order = LogUsers.USERID + "," + LogUsers.DATE_TIME + "," +
            LogUsers.PRODUCT;

        if ("".equals(where)) {
            where = "1=1";
        } // if ("".equals(where))

        if (dbg) System.out.println("<br>where = " + where);
        if (dbg) System.out.println("<br>order = " + order);
        logs = new LogUsers().get(where, order);
        if (dbg) System.out.println("<br>logs.length = " + logs.length);

        // the main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        mainTable.addRow(ec.cr1ColRow(
            "<font size=+2>User Logs</font>", true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow(createLogList(logs).toHTML()));
        mainTable.addRow(ec.cr1ColRow("<br>"));

        this.addItem(mainTable.setCenter());

    } // constructor


    /**
     * create the combo-box (pull-down box) for the file names
     */
    DynamicTable createLogList(LogUsers[] logs) {

        DynamicTable fileTable = new DynamicTable(11);
        fileTable.setBorder(1);
        fileTable.setFrame(ITableFrame.BOX);

        TableRow row = new TableRow();
        row.addCell(new TableHeaderCell("User ID"));
        row.addCell(new TableHeaderCell("Date"));
        row.addCell(new TableHeaderCell("Database<br>Discipline"));
        row.addCell(new TableHeaderCell("Product"));
        row.addCell(new TableHeaderCell("File Name"));
        row.addCell(new TableHeaderCell("Area"));
        row.addCell(new TableHeaderCell("Dates"));
        row.addCell(new TableHeaderCell("Survey ID"));
        row.addCell(new TableHeaderCell("Number<br>Records"));
        row.addCell(new TableHeaderCell("Job<br>Duration"));
        fileTable.addRow(row);

        for (int i = 0; i < logs.length; i++) {
            //Link link =
            //    new Link ("SadcoVOS?pscreen=load&pversion=logs&ploadid=" +
            //        logs[i].getLoadid(), logs[i].getLoadid());
            if (dbg) System.out.println("<br>logs[" + i + "] = " + logs[i]);
            row = new TableRow();
            row.addCell(new TableDataCell(logs[i].getUserid()));
            row.addCell(new TableDataCell(
                logs[i].getDateTime("").substring(0,10)));
            row.addCell(new TableDataCell(logs[i].getDatabase() + "<br>" +
                logs[i].getDiscipline()));
            row.addCell(new TableDataCell(logs[i].getProduct()));
            row.addCell(new TableDataCell(logs[i].getFileName()));
            row.addCell(new TableDataCell(logs[i].getLatitudeNorth("") + " to " +
                logs[i].getLatitudeSouth("") + " S<br>" +
                logs[i].getLongitudeWest("") + " to " +
                logs[i].getLongitudeEast("") + " E"));
            if (!"".equals(logs[i].getDateStart(""))) {
                String dateStart = logs[i].getDateStart("").substring(0,10);
                String dateEnd = logs[i].getDateEnd("");
                if (!"".equals(dateEnd)) dateEnd = dateEnd.substring(0,10);
                row.addCell(new TableDataCell(dateStart + " to<br>" + dateEnd));
            } else {
                row.addCell(new TableDataCell(""));
            }
            row.addCell(new TableDataCell(logs[i].getSurveyId("")));
            row.addCell(new TableDataCell(logs[i].getNumRecords("")));
            row.addCell(new TableDataCell(logs[i].getJobDuration("")));

            if (i%2 == 0) {
                row.setBackgroundColor("#CCFFFF");
            } // if (i%2 == 0)

            fileTable.addRow(row);
        } // for i
        return fileTable;
    } // createLogListSelect


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("UserLogListFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreLoadMRNFrame local= new PreLoadMRNFrame(args);
            bd.addItem(new UserLogListFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "UserLogListFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class UserLogListFrame
