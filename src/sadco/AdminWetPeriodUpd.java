//+------------------------------------------------+----------------+--------+
//| CLASS: ....... AdminWetPeriodUpd               | JAVA PROGRAM   | 080526 |
//+------------------------------------------------+----------------+--------+
//| PROGRAMMER: .. SIT Group                                                 |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//|                                                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 20080526  Ursula von St Ange   create class                              |
//+--------------------------------------------------------------------------+

package sadco;

import oracle.html.*;
import java.io.*;
import java.lang.*;

/**
 */
public class AdminWetPeriodUpd extends CompoundItem {

    /** A general purpose class used to extract the url-type parameters*/
    static common.edmCommon ec = new common.edmCommon();

    /** A class with all the constants used in Weather.*/
    SadConstants sc = new SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    // arguments
    String stationId    = "";
    String year         = "";
    String wetStationId = "";
    String stationName  = "";


    String field     = "";
    String where     = "";
    String order     = "";
    boolean success   = false;

    int total          = 0;
    int instrumentCode = 0;

    int monthDayList[] = {31,28,31,30,31,30,31,31,30,31,30,31};
    int monthList[]    = { 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12};
    int cnt[]          = new int[12];
    int monthCnt[]     = new int[12];

    public AdminWetPeriodUpd(String args[]) {

        // get the argument values
        getArgParms(args);

        // Create main table
        DynamicTable mainTab = new DynamicTable(0);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();

        DynamicTable subTab = new DynamicTable(13);
        subTab.setBorder(1);
        subTab .setFrame(ITableFrame.BOX);
        subTab.setCenter();

        DynamicTable messageTab = new DynamicTable(0);
        messageTab.setFrame(ITableFrame.VOLD);
        messageTab.setRules(ITableRules.NONE);
        messageTab.setBorder(0);
        messageTab.setCenter();

        // get the WetStation details.
        WetStation wetStation[] = new WetStation().get(
            WetStation.STATION_ID +" = '" + stationId+"'");
        if (dbg) System.out.println ("<br>stationId = "+stationId);
        if (dbg) System.out.println("<br> wetStation.length = "+wetStation.length);

        wetStationId = wetStation[0].getStationId();
        stationName  = wetStation[0].getName();

        String monthFiller = "";
        String begin       = "01";

        // create the html page
        mainTab.addRow(ec.crSpanColsRow(" Update Period Table "+stationId+" - "+
            stationName,2,true,"blue",IHAlign.CENTER,"+1" ));
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));

        mainTab.addRow(ec.crSpanColsRow(" Station code "+stationId+" - "+stationName,2));
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));

        TableRow rows = new TableRow();
        rows
            .addCell(new TableHeaderCell("Year"))
            .addCell(new TableHeaderCell("Jan"))
            .addCell(new TableHeaderCell("Feb"))
            .addCell(new TableHeaderCell("Mar"))
            .addCell(new TableHeaderCell("Apr"))
            .addCell(new TableHeaderCell("May"))
            .addCell(new TableHeaderCell("Jun"))
            .addCell(new TableHeaderCell("Jul"))
            .addCell(new TableHeaderCell("Aug"))
            .addCell(new TableHeaderCell("Sep"))
            .addCell(new TableHeaderCell("Oct"))
            .addCell(new TableHeaderCell("Nov"))
            .addCell(new TableHeaderCell("Dec"))
            .addCell(new TableHeaderCell("Action"))
            ;
        subTab.addRow(rows);

        // Loop through wavData records with maching stationId and year input value.
        // loop thougth year

        // If year equals blank get the min(start_year) & max(end_year) for station_id.
        // get min date_time.
        field = "to_char(min("+WetData.DATE_TIME+"), 'yyyy-mm-dd hh24:mi') as "+
            WetData.DATE_TIME;
        where = WetData.STATION_ID +" = '"+wetStationId+ "'";
        order = "";

        WetData minDate[] = new WetData().get(field,where,order);
        String minDateTime = minDate[0].getDateTime("");

        // get max date_time.
        field = "to_char(max("+WetData.DATE_TIME+"), 'yyyy-mm-dd hh24:mi') as "+
            WetData.DATE_TIME;

        WetData maxDate[] = new WetData().get(field,where, order);
        String maxDateTime = maxDate[0].getDateTime("");

        //System.out.println("<br>where = " + where);
        //System.out.println("<br>minDateTime = " + minDateTime);
        //System.out.println("<br>maxDateTime = " + maxDateTime);

        if (!"".equals(minDateTime)) {
            String startD = minDateTime.substring(0,4);
            String endD   = maxDateTime.substring(0,4);

            int start = 0;
            int end   = 0;

            if (!"".equals(year)) {
                start = new Integer(year).intValue();
                end   = new Integer(year).intValue();

            } else {
                start = new Integer(startD).intValue();
                end   = new Integer(endD).intValue();

            } //if ("".equals(year)) {

            if (dbg) System.out.println("<br> maxDateTime "+maxDateTime+
                " minDateTime "+minDateTime);

            if (dbg) System.out.println("<br> start "+start+" end "+end);

            for ( int i = start; i <= end; i++) {

                String status = "";
                int yearTotal = 0;
                for ( int m=0; m < 12; m++) {

                    // get the WetData details.
                    ec.setFrmFiller("0");
                    where = WetData.STATION_ID +" = '"+wetStationId+ "' and "+
                        WetData.DATE_TIME+ " between "+
                        Tables.getDateFormat(i+"-"+ec.frm(m+1,2)+"-"+begin+" 00:00")+" and "+
                        Tables.getDateFormat(i+"-"+ec.frm(m+1,2)+"-"+monthDayList[m]+" 23:59");
                        //Tables.getDateFormat(i+"-"+(m+1)+"-"+begin)+" and "+
                        //Tables.getDateFormat(i+"-"+(m+1)+"-"+monthDayList[m]);
                    ec.setFrmFiller(" ");

                    //System.out.println("where = " + where);

                    int numRecs     = new WetData().getRecCnt(where);

                    if (dbg) System.out.println("<br>StationId "+stationId+" Year "+year+
                        " year cnt (month) "+(m+1)+" NumRecs records per month "+numRecs);

                    cnt[m] =  numRecs;
                    total  += cnt[m];
                    yearTotal += numRecs;

                } //for ( int m=0; m < 12; m++) {

                // only insert / update if there were records
                if (yearTotal > 0) {

                    // code for Update.
                    // first check if record exist other wise insert record.
                    // fields = stationId, year, (i+1), instrumentCode, numRecs

                    where  = WetPeriodCounts.YEARP+" = "+i+ " and "+
                             WetPeriodCounts.STATION_ID+" = '"+stationId+"'";

                    int checkRecord = new WetPeriodCounts().getRecCnt(where);
                    WetPeriodCounts wetRec = new WetPeriodCounts();

                    // Set fields for both Update and Insert.
                    wetRec.setM01(cnt[0]);
                    wetRec.setM02(cnt[1]);
                    wetRec.setM03(cnt[2]);
                    wetRec.setM04(cnt[3]);
                    wetRec.setM05(cnt[4]);
                    wetRec.setM06(cnt[5]);
                    wetRec.setM07(cnt[6]);
                    wetRec.setM08(cnt[7]);
                    wetRec.setM09(cnt[8]);
                    wetRec.setM10(cnt[9]);
                    wetRec.setM11(cnt[10]);
                    wetRec.setM12(cnt[11]);

                    if (checkRecord == 0 ) { // insert record
                        wetRec.setStationId(stationId);
                        wetRec.setYearp(i);
                        //System.out.println("<br>wetRec = " + wetRec);
                        wetRec.put();
                        status = "<font color=green> Insert </font>";

                    } else {                 // Search record = where,update record.

                        success = new WetPeriodCounts().upd(wetRec,where);
                        //success = true;

                        if (success) {
                            status = "<font color=green> Update </font>";
                        } else {
                            status = "<font color=green> Update fail </font>";
                        } //if (success) {
                    } //if (checkRecord < 1) {
                } else {
                    status = "<font color=green> Ignore </font>";

                } // if (yearTotal > 0)
                    rows = new TableRow();
                    rows
                        .addCell(new TableDataCell(String.valueOf(i)))
                        .addCell(new TableDataCell(String.valueOf(cnt[0])))
                        .addCell(new TableDataCell(String.valueOf(cnt[1])))
                        .addCell(new TableDataCell(String.valueOf(cnt[2])))
                        .addCell(new TableDataCell(String.valueOf(cnt[3])))
                        .addCell(new TableDataCell(String.valueOf(cnt[4])))
                        .addCell(new TableDataCell(String.valueOf(cnt[5])))
                        .addCell(new TableDataCell(String.valueOf(cnt[6])))
                        .addCell(new TableDataCell(String.valueOf(cnt[7])))
                        .addCell(new TableDataCell(String.valueOf(cnt[8])))
                        .addCell(new TableDataCell(String.valueOf(cnt[9])))
                        .addCell(new TableDataCell(String.valueOf(cnt[10])))
                        .addCell(new TableDataCell(String.valueOf(cnt[11])))
                        .addCell(new TableDataCell(String.valueOf(status)))
                        ;
                    subTab.addRow(rows);

            } // for ( int i = start; i < end; i++) {

        } // if (!"".equals(minDateTime))

        // create the html page
        //messageTab.addRow(ec.crSpanColsRow("<br> Total number of records "+
        //    total,2,true,"blue",IHAlign.CENTER,"+1" ));

        messageTab.addRow(ec.crSpanColsRow("<br> Total number of records "+total,2));
        messageTab.addRow(ec.crSpanColsRow("&nbsp;",2));

        // create main container
        Container con = new Container();
        con.addItem(mainTab);
        con.addItem(subTab);
        con.addItem(messageTab);
        addItem(con);

    } // constructor

    /**
     * Get the parameters from the arguments in the URL
     * @param  args      (String)
     */
    void getArgParms(String args[])   {
        stationId  = ec.getArgument(args,"pstationid");
        year       = ec.getArgument(args,"pyear");

        if (dbg) System.out.println ("<br>stationId = "+stationId);
        if (dbg) System.out.println ("<br>year      = "+year);
    }   //  public void getArgParms()


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("AdminWetPeriodUpd local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //AdminWetPeriodUpd local= new AdminWetPeriodUpd(args);
            bd.addItem(new AdminWetPeriodUpd(args));
            hp.printHeader();
            hp.print();
            common2.DbAccessC.close();
        } catch(Exception e) {
            ec.processErrorStatic(e, "AdminWetPeriodUpd", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class AdminWetPeriodUpd
