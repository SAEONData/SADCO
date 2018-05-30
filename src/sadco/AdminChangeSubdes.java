package sadco;

import oracle.html.*;
//import java.sql.*;
//import java.io.*;

/**
 * This class changes the subdes for all the watphy records for a requested
 * survey to a user-specified value.                                        <br>
 * It's parameters are the following:                                       <br>
 *
 * SadcoMRN                                                                 <br>
 * screen.equals("admin")                                                   <br>
 * version.equals("subdes")                                                 <br>
 *
 * It's parameters are the following:                                       <br>
 * pscreen   - used by SadcoMRN program. Value must be 'admin'.             <br>
 * pversion  - used by SadcoMRN program. Value must be 'speeds'.            <br>
 * surveyId  - argument value from url (string)                             <br>
 * subdes    - argument value from url (string)                             <br>
 *
 * @author 2004/02/11 - SIT Group.
 * @version
 * 2004/02/11 - Ursula von St Ange - created class<br>
 */
public class AdminChangeSubdes extends CompoundItem {

    /** A boolean variable for debugging purposes */
    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommonPC ec = new common.edmCommonPC();
    sadco.SadConstants sc = new sadco.SadConstants();


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public AdminChangeSubdes(String args[]) {

        try {
            AdminChangeSubdesActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // AdminChangeSubdes constructor

    /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void AdminChangeSubdesActual(String args[]) {

        // get the arguments
        String surveyId = ec.getArgument(args, sc.SURVEYID);
        String subdes   = ec.getArgument(args, sc.SUBDES);

        // get the station records
        MrnStation[] stations = getStationRecords(surveyId);
        //int[] numRecords = new int[stations.length];
        int numRecords = 0;

        // update the watphy records
        for (int i = 0; i < stations.length; i++) {
            //numRecords[i] = updateWatphyRecords(stations[i].getStationId(""), subdes);
            numRecords += updateWatphyRecords(stations[i].getStationId(""), subdes);
        } // for (int i = 0; i < stations.length; i++)

        // tell the user
        this.addItem("\n<center><br>");
        this.addItem("<b><font color=blue><font size=+2>Change subdes per survey"+
                    "</font></font></b>\n");

        DynamicTable table = new DynamicTable(1);
        table.setFrame(ITableFrame.VOLD);
        table.setRules(ITableRules.NONE);
        table.setBorder(0);

        TableRow row = new TableRow();
        row.addCell(
            new TableDataCell("&nbsp").setColSpan(2).setHAlign(IHAlign.CENTER));
        table.addRow(row);
        //table.addRow(ec.crSpanColsRow("&nbsp",2));

        row = new TableRow();
        row.addCell(new TableHeaderCell("Survey Id: ").setHAlign(IHAlign.RIGHT))
            .addCell(new TableDataCell(surveyId));
        table.addRow(row);

        row = new TableRow();
        row.addCell(new TableHeaderCell("Subdes: ").setHAlign(IHAlign.RIGHT))
            .addCell(new TableDataCell(subdes));
        table.addRow(row);

        row = new TableRow();
        row.addCell(new TableHeaderCell("No of stations: ").setHAlign(IHAlign.RIGHT))
            .addCell(new TableDataCell(String.valueOf(stations.length)));
        table.addRow(row);

        row = new TableRow();
        row.addCell(new TableHeaderCell("No of watphy<br>records updated: ")
            .setHAlign(IHAlign.RIGHT))
            .addCell(new TableDataCell(String.valueOf(numRecords)));
        table.addRow(row);

        //table.addRow(ec.cr2ColRow("Survey Id: ",surveyId));
        //table.addRow(ec.cr2ColRow("Subdes: ",subdes));
        //table.addRow(ec.cr2ColRow("No of stations: ",String.valueOf(stations.length)));
        //table.addRow(ec.cr2ColRow("No of watphy<br>records updated: ",String.valueOf(numRecords)));

        row = new TableRow();
        row.addCell(
            new TableDataCell("&nbsp").setColSpan(2).setHAlign(IHAlign.CENTER));
        table.addRow(row);

        this.addItem(table);


    } // AdminChangeSubdesActual


    /**
     * Get Station records from Database for the @param surveyId
     * @param surveyId : argument value from url (string)
     * @return array value stations
     */
    MrnStation[] getStationRecords(String surveyId) {

        if (dbg) System.out.println("<br<getStationRecords ...");

        // construct the table names
        String fields = MrnStation.STATION_ID;

        // construct the where clause
        String where = MrnStation.SURVEY_ID +"='"+ surveyId +"'";

        // construct the order by cluase
        String order = MrnStation.STATION_ID;
        MrnStation[] stations = new MrnStation().get(fields, where, order);

        return stations;
    } //MrnStation[] getStationRecords


    /**
     * Update watphy records from Database for the @param stationId
     * @param stationId : argument value from url (string)
     * @return array value watphyRecs
     */
    int updateWatphyRecords(String stationId, String subdes) {

        if (dbg) System.out.println("<br>updateWatphyRecords ...");

        MrnWatphy whereWatphy = new MrnWatphy();
        whereWatphy.setStationId(stationId);

        MrnWatphy updWatphy = new MrnWatphy();
        updWatphy.setSubdes(subdes);

//        whereWatphy.upd(updWatphy);

        if (dbg) System.out.println("<br>updStr = " + whereWatphy.getUpdStr());

        return whereWatphy.getNumRecords();

    } // updateWatphyRecords


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("AdminChangeSubdes local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //AdminChangeSubdes local= new AdminChangeSubdes(args);
            bd.addItem(new AdminChangeSubdes(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "AdminChangeSubdes", "Main", "");
            // close the connection to the database
        } // try-catch
        common2.DbAccessC.close();


    } // main


} // class AdminChangeSubdes