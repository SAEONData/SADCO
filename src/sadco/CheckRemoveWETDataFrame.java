package sadco;

import oracle.html.*;
import java.sql.*;

/**
 * This class displays a summary of the weather data period to be removed for
 * confirmation.
 *
 * SadcoWET
 * pScreen.equals("remove")
 * pVersion.equals("check")
 *
 * @author 19991103 - SIT Group
 * @version
 * 19991103 - SIT Group - create class                                      <br>
 * 20080516 - Ursula von St Ange - copy from weather and adapt for sadco    <br>
 */
public class CheckRemoveWETDataFrame extends CompoundItem{

    static common.edmCommon ec = new common.edmCommon();
    SadConstants sc = new SadConstants();

    String periodCode     ;
/*    String stationId      ;
    String stationName    ;
    String instrumentCode ;
    String instrumentName ;
    String startDate      ;
    String endDate        ;
    String recNum         ;  */

    boolean dbg = false;
    //boolean dbg = true;

    public CheckRemoveWETDataFrame(String args[]) {

        getArgParms(args);

        int pCode = new Integer(periodCode).intValue();

        //Get the Period details
        WetPeriod wetPeriod[] = new WetPeriod(pCode).get();
        if (dbg) System.out.println("<br>wetPeriod[0] : " + wetPeriod[0]);

        //Get the Station details
        String where =
            WetStation.STATION_ID + "='" + wetPeriod[0].getStationId() + "'";
        WetStation station[] = new WetStation().get(where);
        if (dbg) System.out.println("<br>station[0] : " + station[0]);

        //Get the instrument details
        where = EdmInstrument2.CODE + "=" + wetPeriod[0].getInstrumentCode("");
        EdmInstrument2 edmInstrument[] = new EdmInstrument2().get(where);

       /*
        * Get the single period details
        */

       String stationId        = wetPeriod[0].getStationId("");
       String stationName      = station[0].getName();
       String instrumentCode   = wetPeriod[0].getInstrumentCode("");
       String instrumentName   = edmInstrument[0].getName();
       String startDate        = wetPeriod[0].getStartDate("");
       String endDate          = wetPeriod[0].getEndDate("");
       String recNum           = wetPeriod[0].getNumberRecords("");

       if (dbg) System.out.println("<br>startDate : " + startDate);
       if (dbg) System.out.println("<br>endDate : " + endDate);



        // Create main table                                         //
        DynamicTable mainTab = new DynamicTable(2);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();

        // all the extraction details
        mainTab.addRow(ec.crSpan2ColRow(
          "The following data will be deleted<br><br>"));
        mainTab.addRow(ec.cr2ColRow("Station: ", stationName + "(" + stationId + ")"));
        mainTab.addRow(ec.cr2ColRow("Instrument: ", instrumentName + "(" + instrumentCode + ")"));
        mainTab.addRow(ec.cr2ColRow("Start Date: ", startDate));
        mainTab.addRow(ec.cr2ColRow("End Date: ", endDate));
        mainTab.addRow(ec.cr2ColRow("Records Found : ", recNum));
        mainTab.addRow(ec.crSpan2ColRow("<br>"));
        mainTab.addRow(ec.crSpan2ColRow(new Submit("", "Continue").toHTML()));

        Form form = new Form("GET", sc.WET_APP);

        form.addItem(mainTab.setCenter());

        form.addItem(new Hidden(sc.PERIODCODE, periodCode));
        form.addItem(new Hidden(sc.SCREEN, "remove"));
        form.addItem(new Hidden(sc.VERSION, "confirm"));
        form.addItem(new Hidden(sc.USERTYPE,ec.getArgument(args, sc.USERTYPE)));
        form.addItem(new Hidden(sc.SESSIONCODE,ec.getArgument(args,sc.SESSIONCODE)));

        // for the menu system
        form.addItem(new Hidden("pmenuno",ec.getArgument(args,"pmenuno")));
        form.addItem(new Hidden("pmversion",ec.getArgument(args,"pmversion")));


        // Main container
        Container con = new Container();
        con.addItem(form);
        addItem(con);


    } // constructor

    /**
     * Get the parameters from the arguments in the URL
     * @param  args[]       (String)
     */
    private void getArgParms(String args[])   {

        periodCode       = ec.getArgument(args, sc.PERIODCODE);

    }   //  public void getArgParms()



} // class CheckRemoveWETDataFrame



