package sadco;

import oracle.html.*;
import java.util.*;
import java.sql.*;
import java.io.*;

/**
 * This class confirm's the station's record's before deleting it.
 *
 * SadcoMRN
 * screen.equals("admin")
 * version.equals("confirm")
 *
 * It's parameters are the following:
 * <br>pscreen   - used by SadcoMRN program. Value must be 'admin'.
 * <br>pversion  - used by SadcoMRN program. Value must be 'confirm'.
 * <br>surveyId  - the ID of the survey that the station belongs to.
 * <br>stationId - the ID of the station to be deleted.
 *
 * @author 2002/02/13 - SIT Group.
 * @version
 * 2002/02/13 - Mario August       - created class<br>
 */
public class AdminDisplStation extends CompoundItem {

    /** A boolean variable for debugging purposes */
    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();

    /** a class that contains all the constants used in the sadco application */
    sadco.SadConstants sc = new sadco.SadConstants();

    // arguments
    String surveyId = "";
    String stationId = "";

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public AdminDisplStation (String args[]) {

        try {
            AdminDisplStationActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // AdminDisplStation constructor


    /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void AdminDisplStationActual(String args[]) {

        getArgParms(args);
        MrnStation[] stations = getStationRecords(stationId);

        Form form = new Form("POST", sc.MRN_APP);
        form.addItem(new Hidden(sc.SCREEN,"admin"));
        form.addItem(new Hidden(sc.VERSION,"delstn"));
        form.addItem(new Hidden(sc.SURVEYID,surveyId));
        form.addItem(new Hidden(sc.STATIONID,stationId));

        // for the menu system
        String sessionCode= ec.getArgument(args, sc.SESSIONCODE);
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        DynamicTable table = displayReport(stations, args);
        form.addItem(table.setCenter());

        this.addItem(form);

    } // AdminDisplStationActual


    /**
     * Get the parameters from the arguments in the URL
     * @param args
     * @return void
     */
    void getArgParms(String args[])   {
        for (int i = 0; i < args.length; i++) {
            if (dbg) System.out.println("<br>" + args[i]);
        } // for i
        surveyId = ec.getArgument(args, sc.SURVEYID);
        stationId = ec.getArgument(args, sc.STATIONID);

    } // void getArgParms(String args[])

    /**
     * Get Stattion records from Database for the stationId
     * @param stationId : argument value from url (string)
     * @return array value stations
     */
    MrnStation[] getStationRecords(String stationId) {
        if (dbg) System.out.println("getStationRecords       ");
        // construct the table names
        String fields = MrnStation.SURVEY_ID +","+ MrnStation.STATION_ID +","+
            MrnStation.LATITUDE +","+MrnStation.LONGITUDE +","+
            MrnStation.DATE_START+","+MrnStation.DATE_END;

        // construct the where clause
        String where = MrnStation.STATION_ID +"='"+ stationId +"'";

        // construct the order by cluase
        String order = MrnStation.DATE_START;
        MrnStation[] stations = new MrnStation().get(fields, where, order);

        return stations;
    } //MrnStation[] getStationRecords(String stationId)


    /**
     * Dipslay's report on recorde's deleted.
     * @param stations : argument value from URL (string)
     * @return form
     */
    DynamicTable displayReport(MrnStation[] stations, String args[]) {

        DynamicTable table = new DynamicTable(1);
        table.setFrame(ITableFrame.VOLD);
        table.setRules(ITableRules.NONE);
        //table.setFrame(ITableFrame.BOX);
        table.setBorder(0);

        table.addRow(ec.crSpanColsRow("<b><font color=blue><font size=+2>"+
            "Delete a station</font></font></b><p>",4));

        table.addRow(ec.cr2ColRow("Station ID :", stationId));
        table.addRow(ec.cr2ColRow("Survey ID  :", surveyId));
        table.addRow(ec.cr2ColRow("Latitude   :", stations[0].getLatitude("")));
        table.addRow(ec.cr2ColRow("Longitude  :", stations[0].getLongitude("")));
        table.addRow(ec.cr2ColRow("Date start :", stations[0].getDateStart("")));
        table.addRow(ec.cr2ColRow("Date end   :", stations[0].getDateEnd("")));
        table.addRow(ec.crSpanColsRow("&nbsp",2));
        table.addRow(ec.crSpanColsRow(
            "<b> Are you sure you want to delete the above station? </b>",2));
        table.addRow(ec.crSpanColsRow("&nbsp",2));
        table.addRow(ec.crSpanColsRow("<center>"+new Submit("","Yes")+"</center>",2));

        return table;

    } //Form displayReport(MrnStation[] stations) {

} // class AdminDisplStation