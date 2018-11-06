package sadco;

import java.io.*;
import oracle.html.*;
import java.sql.*;
import common.*;

/**
 * Program displays the station details for confirmations of insert / update.
 *
 * SadcoWET
 * pScreen.equals("station")
 * pVersion.equals("maintain")
 *
 * @author 19991103 - SIT Group
 * @version
 * 20031217 - Ursula von St Ange - 'paction' somehow omitted from screen -
 *                                  put back                                <br>
 * 20031217 - Ursula von St Ange - Added input of SADCO survey-id           <br>
 * 20080516 - Ursula von St Ange - copy from weather and adapt for sadco    <br>
 */
public class MaintainStationsFrame extends CompoundItem{

    static common.edmCommon ec = new common.edmCommon();
    SadConstants sc = new SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    String stationId       ;
    String newStationID    ;
    String latitude        ;
    String longitude       ;
    String surveyID        ;
    String name            ;
    String myButton        ;
    String clientName      ;
    String clientCode      ;
    String action          ;
    TableRow row           ;

    /**
     * contructor
     */
    public MaintainStationsFrame(String args[])  {

        getParms(args);

        // Create main table
        DynamicTable mainTab = new DynamicTable(1);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();
        mainTab.setWidth("100%");

        // Create subTab table
        DynamicTable subTab = new DynamicTable(2);
        subTab.setFrame(ITableFrame.VOLD);
        subTab.setRules(ITableRules.NONE);
        subTab.setBorder(0);

        // add header to page
        row = new TableRow();
        mainTab.addRow(ec.crSpanColsRow(" Add / Maintain Station ",
            2,true,"blue",IHAlign.CENTER,"+2" ));
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));

        if (dbg) System.out.println("<br> newStationID : " + newStationID);
        if (dbg) System.out.println("<br> 1 : name : " + name);

        //
        WetStation station[];
        if (!"".equals(newStationID)) {

            //check whether the station already exists
            station = new WetStation(newStationID).get();
            stationId =  newStationID;

            if (dbg) System.out.println("<br> station.length : " + station.length);

            //if new station not in the database
            if (station.length == 0) {
                 myButton = "Insert";
                 InsertRecord(subTab);
            } else {
                 myButton = "Update";
                 UpdateRecord(station, subTab);
            } // if (station.length == 0)

         } else {

            //Get the Station class
            station = new WetStation(stationId).get();

            myButton = "Update";
            UpdateRecord(station, subTab);

         } // if (!"".equals(newStationID))



        // get all clients, and create drop-down box
        EdmClient2 client[] =
            new EdmClient2().get("1=1",EdmClient2.NAME);
        if (dbg) System.out.println("<br>client.length   :" + client.length);

        Select selectClient = new Select(sc.CLIENTCODE);
        selectClient.addOption(new Option("", "", false));

        for (int i = 0; i < client.length; i++) {

            if (dbg) System.out.println("<br>client " + i + " : " +
                station[0].getClientCode("") + " = " + client[i].getCode());

            String clientHeader = client[i].getName() + " ( " +
                client[i].getCode("") + " ) ";

            if (!clientCode.equals(client[i].getCode(""))) {
                selectClient.addOption(new Option(
                    clientHeader,
                    client[i].getCode(""),
                    false
                    ));
            } else {
                selectClient.addOption(new Option(
                    clientHeader,
                    client[i].getCode(""),
                    true
                    ));
            } // if (!clientCode.equals(client[i].getCode("")))


        }  //  for (int i = 0; i < client.length; i++)


        if (dbg) System.out.println("<br> name : " + name);
/*
        for (int i = 0; i < args.length; i++)   {

           row = new TableRow();
           row.addCell(new TableDataCell(new SimpleItem(args[i])));
           mainTab.addRow(row);

        } //  for (int i = 0; i < args.length; i++)
*/


        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Latitude : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new TextField(sc.LATITUDE, 10, 10, latitude)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Longitude : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new TextField(sc.LONGITUDE, 10, 10, longitude)))
           ;
        subTab.addRow(row);

        if (dbg) System.out.println("<br> 3 : name : " + name);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Name : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new TextField(sc.NAME, 30, 20, name)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Client : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(selectClient))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("SADCO Survey ID : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new TextField(sc.SURVEYID, 9, 9, surveyID)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableDataCell("<br><br>"))
           .addCell(new TableDataCell("<br><br>"))
           ;
        subTab.addRow(row);

        //row = new TableRow();
        //row
        //   .addCell(new TableDataCell(new Hidden ("paction", myButton)))
        //   .addCell(new TableDataCell(new Submit ("submit", myButton)))
        //   ;
        //subTab.addRow(row);

        Form form = new Form("GET", "SadcoWET");
        form.addItem(subTab.setCenter());

        form.addItem(new Hidden(sc.SCREEN, "station"));
        form.addItem(new Hidden(sc.VERSION, "confirm"));
        form.addItem(new Hidden (sc.ACTION, myButton));
        form.addItem(new Hidden(sc.USERTYPE,ec.getArgument(args, sc.USERTYPE)));
        form.addItem(new Hidden(sc.SESSIONCODE,ec.getArgument(args,sc.SESSIONCODE)));

        // for the menu system
        form.addItem(new Hidden(sc.MENUNO,ec.getArgument(args,sc.MENUNO)));
        form.addItem(new Hidden(sc.MVERSION,ec.getArgument(args,sc.MVERSION)));
        form.addItem(new Submit("submit", myButton).setCenter());

        this.addItem(mainTab);
        this.addItem(form);

    } // constructor


    public void getParms(String args[]) {

        // get the argument values
        stationId        = ec.getArgument(args, sc.STATIONID   );
        newStationID     = ec.getArgument(args, sc.NEWSTATIONID);
        latitude         = ec.getArgument(args, sc.LATITUDE    );
        longitude        = ec.getArgument(args, sc.LONGITUDE   );
        name             = ec.getArgument(args, sc.NAME        );
        clientCode       = ec.getArgument(args, sc.CLIENTCODE  );
        surveyID         = ec.getArgument(args, sc.SURVEYID    );

    } // getParms

    public void InsertRecord(DynamicTable subTab) {

        row = new TableRow();
        row
           .addCell(new TableDataCell(new SimpleItem(" ")))
           .addCell(new TableDataCell(new Hidden(sc.STATIONID, stationId)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Station Id : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new SimpleItem(stationId)))
           ;
        subTab.addRow(row);

    } // InsertRecord

    public void UpdateRecord(WetStation station[], DynamicTable subTab) {

        latitude         =    station[0].getLatitude("");
        longitude        =    station[0].getLongitude("");
        name             =    station[0].getName();
        clientCode       =    station[0].getClientCode("");
        surveyID         =    station[0].getSurveyId("");
        myButton         =    "Update";

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Station Id : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new SimpleItem(stationId)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableDataCell(new SimpleItem(" ")))
           .addCell(new TableDataCell(new Hidden (sc.STATIONID, stationId)))
           ;
        subTab.addRow(row);


        if (dbg) System.out.println("<br> Upd - name : " + name);

    } // UpdateRecord

} // class MaintainStationsFrame
