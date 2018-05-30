package sadco;

import java.io.*;
import oracle.html.*;
import java.sql.*;
import common.*;

/**
 * This class deletes the requested weather data period, and confirms that it
 * has been deleted.
 *
 * SadcoWET
 * pScreen.equals("remove")
 * pVersion.equals("confirm")
 *
 * @author 19991103 - SIT Group
 * @version
 * 19991103 - SIT Group - create class                                      <br>
 * 20080516 - Ursula von St Ange - copy from weather and adapt for sadco    <br>
 */
public class ConfirmRemoveWETDataFrame extends CompoundItem{

    static common.edmCommon ec = new common.edmCommon();
    SadConstants sc = new SadConstants();

    String periodCode     ;
    String stationId      ;
    String stationName    ;
    String instrumentCode ;
    String instrumentName ;
    String startDate      ;
    String endDate        ;
    String recNum         ;
    boolean success       ;

    boolean dbg = false;
    //boolean dbg = true;

    public ConfirmRemoveWETDataFrame(String args[]) {

        getArgParms(args);

        int pCode = new Integer(periodCode).intValue();

        //Get the Period class
        WetPeriod wetPeriod[] = new WetPeriod(pCode).get();
        if (dbg) System.out.println("<br>wetPeriod[0] : " + wetPeriod[0]);


        //Get the Station class
        WetStation station[] = new WetStation().get(
                                   WetStation.STATION_ID + "='" + wetPeriod[0].getStationId() + "'");
        if (dbg) System.out.println("<br>station[0] : " + station[0]);

        //Get the Period Notes class
        EdmInstrument2 edmInstrument[] = new EdmInstrument2().get(
                        EdmInstrument2.CODE + "="  + wetPeriod[0].getInstrumentCode(""));

        /*
         * Get the single period details
         */

        stationId        = wetPeriod[0].getStationId("");
        stationName      = station[0].getName();
        instrumentCode   = wetPeriod[0].getInstrumentCode("");
        instrumentName   = edmInstrument[0].getName();
        startDate        = wetPeriod[0].getStartDate("");
        endDate          = wetPeriod[0].getEndDate("");
        recNum           = wetPeriod[0].getNumberRecords("");

        //Delete the WetData class
        success = new WetData().del(
                        WetData.STATION_ID + "='" + stationId + "'"
                        + " AND " +
                        WetData.PERIOD_CODE + "=" + periodCode
                        + " AND " +
                        WetData.DATE_TIME + ">=" + Tables.getDateFormat(startDate,"")
                        + " AND " +
                        WetData.DATE_TIME + "<=" + Tables.getDateFormat(endDate,"")
                        );

        //Delete the WetEditLog class
//        success = new WetEditLog().del(
//                                   WetEditLog.PERIOD_CODE + "=" + wetPeriod[0].getCode());

        //Delete the Period Notes class
        success = new WetPeriodNotes().del(
                        WetPeriodNotes.PERIOD_CODE + "="  + wetPeriod[0].getCode());

//        //Delete the Period Notes class
//        success = new EdmInstrument2().del(
//                        EdmInstrument2.CODE + "="  + wetPeriod[0].getInstrumentCode(""));

        //Get the Period class
        success = new WetPeriod(pCode).del();

        if (dbg) System.out.println("<br>wetPeriod[0] : " + wetPeriod[0]);

//        if (dbg) System.out.println("<br>wetPeriodNotes[0] : " + wetPeriodNotes[0]);

        DynamicTable mainTab = new DynamicTable(2);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();

        // all the extraction details
        mainTab.addRow(ec.crSpan2ColRow(
            "The following data has been succesfully deleted<br><br>"));
        mainTab.addRow(ec.cr2ColRow("Station: ", stationName + "(" + stationId + ")"));
        mainTab.addRow(ec.cr2ColRow("Instrument: ", instrumentName + "(" + instrumentCode + ")"));
        mainTab.addRow(ec.cr2ColRow("Start Date: ", startDate));
        mainTab.addRow(ec.cr2ColRow("End Date: ", endDate));
        mainTab.addRow(ec.cr2ColRow("Records Found : ", recNum));


        // Main container                                            //
        Container con = new Container();
        con.addItem(mainTab);
        addItem(con);

    } // constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param  args[]       (String)
     */
    private void getArgParms(String args[])   {

        periodCode       = ec.getArgument(args, sc.PERIODCODE);


    }   //  public void getArgParms()


} // class ConfirmRemoveWETDataFrame




