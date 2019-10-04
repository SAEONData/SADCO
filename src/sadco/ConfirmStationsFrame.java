package sadco;

import java.io.*;
import oracle.html.*;
import java.sql.*;
import common.*;
//import waves.*;

/**
 * Program does actual insert or update of the station details, and confirms it
 * to the user.
 *
 * SadcoWET
 * pScreen.equals("station")
 * pVersion.equals("confirm")
 *
 * @author 19991103 - SIT Group
 * @version
 * 20031217 - Ursula von St Ange -  ub01 - correct for new WetStation
 *                                constructor                               <br>
 * 20031217 - Ursula von St Ange -  ub02 - add SADCO survey ID stuff        <br>
 * 20080516 - Ursula von St Ange - copy from weather and adapt for sadco    <br>
 */
public class ConfirmStationsFrame extends CompoundItem{

    static common.edmCommon ec = new common.edmCommon();
    SadConstants sc = new SadConstants();

    String stationID       ;
    String newStationID    ;
    String latitude        ;
    String longitude       ;
    String surveyID        ;                                            // ub02
    String name            ;
    String myButton        ;
    String clientName      ;
    int    clientCode      ;
    String action          ;
    boolean success        ;

    boolean     dbg = false;
    //boolean     dbg = true;

    public ConfirmStationsFrame(String args[]) {

        getParms(args);

        // Main container                                            //
        Container con = new Container();

        // create table for fix number & profile number
        DynamicTable mainTab = new DynamicTable(0);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();

        DynamicTable headTab = new DynamicTable(0);
        headTab.setFrame(ITableFrame.VOLD);
        headTab.setRules(ITableRules.NONE);
        headTab.setBorder(0);
        headTab.setCenter();

        DynamicTable tab = new DynamicTable(0);
        tab.setFrame(ITableFrame.VOLD);
        tab.setRules(ITableRules.NONE);
        tab.setBorder(0);
        tab.setCenter();

        TableRow row = new TableRow();


        // Create sub table                                         //
        DynamicTable subTab = new DynamicTable(2);
        subTab.setFrame(ITableFrame.VOLD);
        subTab.setRules(ITableRules.NONE);
        subTab.setBorder(0);
        subTab.setCenter();

        headTab.addRow(ec.crSpanColsRow("Add / Maintain Stations",
                2,true, "blue", IHAlign.CENTER, "+2"));
        headTab.addRow(ec.crSpanColsRow("&nbsp;",2));


        if (dbg) System.out.println("<br> st action : " + action);
        if (action.equals("Insert")) {

            checkClient();

            if (dbg) System.out.println("<br>**Ins** clientCode : " + clientCode);

/*
            success = new WetStation(
                             stationID       ,
                             latitude        ,
                             longitude       ,
                             name            ,
                             clientCode      ,
                             ).put();
*/
            WetStation station = new WetStation();                      // ub01
            station.setStationId  (stationID);                          // ub01
            station.setLatitude   (latitude);                           // ub01
            station.setLongitude  (longitude);                          // ub01
            station.setName       (name);                               // ub01
            station.setClientCode (clientCode);                         // ub01
            if (!"".equals(surveyID.trim()))                            // ub02
                station.setSurveyId(surveyID);                          // ub02
            success = station.put();                                    // ub01

            if (dbg) System.out.println("<br>sql = " + station.getInsStr());
            if (dbg) System.out.println("<br>numRecs = " + station.getNumRecords());
            if (dbg) System.out.println("<br>messages = " + station.getMessages());

            //row = new TableRow();
            //row
            //   .addCell(new TableHeaderCell("Station " + name + " has been succesfully inserted "))
            //   ;
            //mainTab.addRow(row);

            //row = new TableRow();
            //row
            //   .addCell(new TableDataCell(new SimpleItem(" ")))
            //   ;
            //mainTab.addRow(row);
            if (station.getNumRecords() == 1) {
                tab.addRow(ec.crSpanColsRow(
                    "Station "+name+" has been succesfully inserted.",2,true,
                    "green",IHAlign.CENTER,"+1" ));
            } else {
                tab.addRow(ec.crSpanColsRow(
                    "Station "+name+" has NOT been succesfully inserted.",2,true,
                    "red",IHAlign.CENTER,"+1" ));
                tab.addRow(ec.crSpanColsRow(
                    "SQL error message: " + station.getMessages(),2,true,
                    "red",IHAlign.CENTER,"+1" ));
            } // if (station.getNumRecords() == 1)
            tab.addRow(ec.crSpanColsRow("&nbsp;",2));


        } //if (action.equals("Insert"))
        else if (action.equals("Update")) {

            checkClient();

            if (dbg) System.out.println("<br>**Upd** clientCode : " + clientCode);

            WetStation updStation = new WetStation();                   // ub01
            updStation.setLatitude   (latitude);                        // ub01
            updStation.setLongitude  (longitude);                       // ub01
            updStation.setName       (name);                            // ub01
            updStation.setClientCode (clientCode);                      // ub01
            if (!"".equals(surveyID.trim()))                            // ub02
                updStation.setSurveyId(surveyID);                       // ub02
/*
            WetStation updStation = new WetStation(
                                    Tables.CHARNULL ,
                                    latitude        ,
                                    longitude       ,
                                    name            ,
                                    clientCode
                                    );
*/
            WetStation station = new WetStation(stationID);
            success = station.upd(updStation);

            if (dbg) System.out.println ("<br>updated WetStation : success = " + success);

            if (station.getNumRecords() == 1) {
                tab.addRow(ec.crSpanColsRow(
                    "Station "+name+" has been succesfully updated.",2,true,
                    "green",IHAlign.CENTER,"+1" ));
            } else {
                tab.addRow(ec.crSpanColsRow(
                    "Station "+name+" has NOT been succesfully updated.",2,true,
                    "red",IHAlign.CENTER,"+1" ));
                tab.addRow(ec.crSpanColsRow(
                    "SQL error message: " + station.getMessages(),2,true,
                    "red",IHAlign.CENTER,"+1" ));
            } // if (station.getNumRecords() == 1)
            tab.addRow(ec.crSpanColsRow("&nbsp;",2));


        } //else if (action.equals("Update"))

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Station Id : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new SimpleItem(stationID)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Latitude : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new SimpleItem(latitude)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Longitude : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new SimpleItem(longitude)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Name : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new SimpleItem(name)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("Client Code : ").setHAlign(IHAlign.RIGHT))
           .addCell(new TableDataCell(new SimpleItem(clientCode)))
           ;
        subTab.addRow(row);

        row = new TableRow();
        row
           .addCell(new TableHeaderCell("SADCO Survey ID : ").setHAlign(IHAlign.RIGHT))// ub02
           .addCell(new TableDataCell(new SimpleItem(surveyID)))        // ub02
           ;
        subTab.addRow(row);

        con
           .addItem(headTab)
           .addItem(mainTab)
           .addItem(subTab)
           .addItem(tab)
           ;
        addItem(con);

    } // constructor


    /**
     * get the form parameters
     */
    public void getParms(String args[]) {

        // get the argument values
        stationID        = ec.getArgument(args, sc.STATIONID   );
        newStationID     = ec.getArgument(args, sc.NEWSTATIONID);
        latitude         = ec.getArgument(args, sc.LATITUDE    );
        longitude        = ec.getArgument(args, sc.LONGITUDE   );
        name             = ec.getArgument(args, sc.NAME        );
        clientCode       = Integer.parseInt(ec.getArgument(args, sc.CLIENTCODE));
        action           = ec.getArgument(args, sc.ACTION      );
        clientName       = ec.getArgument(args, sc.CLIENTNAME  );
        surveyID         = ec.getArgument(args, sc.SURVEYID    );       // ub02


        if (dbg) System.out.println("<br> gp stationID : " + stationID);
    }


    /**
     *
     */
    public void checkClient() {

        if (dbg) System.out.println("<br> clientName : " + clientName);

        if (!clientName.equals("")) {

            //Get the EdmClient2 class
            EdmClient2 client[] = new EdmClient2().get();

            if (dbg) System.out.println("<br> client.length : " + client.length);

            int addClient = 0;
            int maxCode   = 0;

            //if new station not in the database
            for (int i = 0; i < client.length; i++) {

                if (clientName.equalsIgnoreCase(client[i].getName())) {

                    if (dbg) System.out.println("<br>if clientName != sp " + client[i].getName());
                    clientCode = client[i].getCode();
                }//if (clientName.equalsIgnoreCase(client[i].getName()))
                else {

                   addClient = 1;

                }//else

            }//for (int i = 0; i < client.length; i++)

            if (addClient == 1) {

                EdmClient2 var = new EdmClient2();
                maxCode   = var.getMaxCode();

                clientCode = maxCode + 1;

                if (dbg) System.out.println("<br>if clientCode == 1 " + clientCode);

                success = new EdmClient2(
                                 maxCode,
                                 clientName
                                 ).put();


            }//if (addClient == 1)

        }//if (!clientName.equals(""))

    }//public void checkClient(String clientCode)


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("PreLoadMRNFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreLoadMRNFrame local= new PreLoadMRNFrame(args);
            bd.addItem(new ConfirmStationsFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "ConfirmStationsFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class ConfirmStationsFrame

