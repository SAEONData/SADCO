package sadco;

import common2.*;   // for JSLLib
import oracle.html.*;
import java.io.*;
import java.sql.*;

/**
 * This class loads the marine CTD data into the database
 *
 * SadcoMRN
 * screen.equals("load")
 * version.equals("preload")
 * loadType.equals("currents")
 *
 * @author 030309 - SIT Group
 * @version
 * 030309 - Ursula von St Ange - create class                               <br>
 * 030618 - Ursula von St Ange - change to pre-process                      <br>
 * 030811 - Ursula von St Ange - change input format                        <br>
 * 040610 - Ursula von St Ange - add ploadtype hidden variable (ub01)       <br>
 * 040625 - Ursula von St Ange - adapt for currents from PreLoadMRNWatFrame <br>
 */
public class PreLoadMRNCurFrame extends CompoundItem {

    // values for in this application
    String thisClass   = this.getClass().getName(); // class name

    /** A general purpose class used to extract the url-type parameters*/
    static common.edmCommon ec = new common.edmCommon();
    SadConstants sc = new SadConstants();
    //LoadMRNCurCommon lc = new LoadMRNCurCommon();
    LoadMRNCommon lc = new LoadMRNCommon();

    boolean dbg = false;
    //boolean dbg = true;
    boolean dbg2 = false;
    //boolean dbg2 = true;


    // arguments
    String fileName = "";
    String sessionCode = "";
    String userType = "";
    String mVersion = "";
    String menuNo = "";

    //DynamicTable fatalErrorsTable = new DynamicTable(1);
    DynamicTable dupStationsTable = new DynamicTable(1);


    /**
     *
     */
    public PreLoadMRNCurFrame(String args[]) {

        // set the data type
        lc.dataType = lc.CURRENTS;

        // get the argument values
        getArgParms(args);

        // get the corect path
        String rootPath = "";
        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIRL;
        } else {
            rootPath = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(ec.HOST))
        String fileName2 = rootPath + "marine/" + fileName;

        // open input files
        lc.inputFile = lc.openInputFile(fileName2);

        // open output files
        lc.debugFile = lc.openOutputFile(fileName2 + ".debug");
        lc.reportFile = lc.openOutputFile(fileName2 + ".preload_report");

        if (dbg) System.out.println("<br>fileName2 = " + fileName2);
        if (dbg) System.out.println("<br>lc.debugFile = " + fileName2 + ".debug");
        if (dbg) System.out.println("<br>lc.reportFile = " + fileName2 + ".preload_report");

        //String line = ec.getNextValidLine(lc.inputFile);

        // loop through file
        while (!ec.eof(lc.inputFile)) {

            String line = ec.getNextValidLine(lc.inputFile);

            // this is for when there are extra empty lines at the end of the data file
            if ("".equals(line)) {
                lc.code = 99;
            } else {
                // make sure that I don't get StringIndex out of bounds error
                line += "                                        " +
                    "                                        ";
                lc.lineCount++;
                lc.code = lc.toInteger(line.substring(0,2));
            } // if (ec.eof(lc.inputFile))


            switch (lc.code) {
                case 1: // survey data
                        lc.format01(line, lc.lineCount);
                        break;

                case 2: // survey note
                        lc.checkSurveyExists();
                        lc.format02(line, lc.lineCount);
                        break;

                case 3: // station data
                        lc.outputReport(lc.reportFile, false);
                        if (lc.stationExists) {
                            displayStationDetails();
                        } // if (lc.stationExists)
                        lc.resetStationStatistics();
                        lc.currents = new MrnCurrents();
                        lc.format03(line, lc.lineCount);
                        break;

                case 4: // weather data
                        int subCode = lc.format04(line, lc.lineCount);
                        if (subCode == 1) {
                            // station Id must match that of previous station record
                            lc.checkStationId(lc.lineCount, lc.stationId04);
                            // check if the station unique index (UI) is already in use
                            lc.checkStationUIExists();
                            // is the station Id already in use or does the station exist ?
                            lc.checkStationExists();
                        } // if (subCode == 1)
                        break;

                case 5: // currents data
                        lc.format05(line, lc.lineCount);
                        break;

                case 99: // eof reached
                        break;

                default: // all other codes
                        lc.outputError(lc.lineCount + " - Fatal - " +
                            "Incorrect lc.code: " + lc.code);
                        break;

            } // end switch

            lc.prevCode = lc.code;

            //line = ec.getNextValidLine(lc.inputFile);

        } // while (!ec.eof(file))

        // report on last station details
        lc.outputReport(lc.reportFile, true);
        if (dbg) System.out.println("<br>lc.stationExists = " + lc.stationExists);
        if (lc.stationExists) {
            displayStationDetails();
        } //

        if (dbg) System.out.println("<br>lc.dateMin, lc.dateMax = " +
            lc.dateMin + " " + lc.dateMax);
        if (dbg) System.out.println("<br>lc.latitudeMin, lc.latitudeMax = " +
            lc.latitudeMin + " " + lc.latitudeMax);
        if (dbg) System.out.println("<br>lc.longitudeMin, lc.longitudeMax = " +
            lc.longitudeMin + " " + lc.longitudeMax);
        if (dbg) System.out.println("<br>lc.newStationCount = " + lc.newStationCount);
        if (dbg) System.out.println("<br>lc.fatalCount = " + lc.fatalCount);


        //*** check if inventory exists - have to do it here because I need
        // it in JavaScript
        /*********** still sort this out ******/
        MrnInventory[] checkInventory =
            new MrnInventory(lc.survey.getSurveyId()).get();
        // add JavaScript stuff
        this.addItem(JavaScript(checkInventory.length));
        if (dbg) System.out.println("<br>checkInventory.length = " + checkInventory.length);

        // create the form
        //JSLLib.formName = "thisForm";
        Form form = new Form("POST\" name=\"thisForm", sc.MRN_APP);
        //Form form = new Form("get",sc.MRN_APP);
        form.addItem(new Hidden(sc.SCREEN, "load"));
        form.addItem(new Hidden(sc.VERSION, "load"));
        form.addItem(new Hidden(sc.LOADTYPE, ec.getArgument(args,sc.LOADTYPE)));//ub01
        //form.addItem(new Hidden(sc.LOADFLAG, "y"));
        form.addItem(new Hidden(sc.FILENAME, fileName));
        form.addItem(new Hidden(sc.TIMEZONE, lc.timeZone));
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));

        // the main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX).setCenter();

        // create the html page
        mainTable.addRow(ec.cr1ColRow("<font size=+2>Load Data</font>",
            true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow("File Name: " + fileName,
            true, "", IHAlign.CENTER));

        // set other tables attributes
        //lc.fatalErrorsTable.setFrame(ITableFrame.BOX);
        dupStationsTable.setBorder(0);
        dupStationsTable.setCenter();

        // only add fatal errors table if there were errors
        if (lc.fatalCount > 0) {
            if (dbg) System.out.println("<br>in if(lc.fatalCount > 0)");
            mainTable.addRow(ec.cr1ColRow("<br>"));
            //mainTable.addRow(ec.cr1ColRow(lc.fatalErrorsTable.toHTML()));
            mainTable.addRow(ec.cr1ColRow("Fatal error thing"));
        } // if (lc.fatalCount > 0)

        //mainTable.addRow(ec.cr1ColRow(debugTable.toHTML()));
        //mainTable.addRow(ec.cr1ColRow("<br>"));
        if (lc.duplicateStations) {
            mainTable.addRow(ec.cr1ColRow("<br>"));
            mainTable.addRow(ec.cr1ColRow(dupStationsTable.toHTML()));
        } // if (duplicateStations)

        //if (lc.fatalCount == 0 && !loadFlag) {
        if (lc.fatalCount == 0) {

            //*** check if inventory exists
            //MrnInventory[] checkInventory =
            //    new MrnInventory(lc.survey.getSurveyId()).get();
            if (checkInventory.length == 0) {
                DynamicTable invTable = new DynamicTable(2);
                //invTable.setBorder(0);
                invTable.setFrame(ITableFrame.BOX).setCenter();
                invTable.addRow(ec.crSpanColsRow(
                    "Please enter / update the inventory details",2));
                invTable.addRow(ec.cr2ColRow("Survey Id:",
                    lc.survey.getSurveyId()));
                invTable.addRow(ec.cr2ColRow("Project Name:",
                    sc.PROJECTNAME, 20, 20, lc.survey.getPrjnam(""), "*"));
                invTable.addRow(ec.cr2ColRow("Cruise Name:",
                    sc.CRUISENAME, 20, 20, lc.survey.getExpnam(""), "*"));
                invTable.addRow(ec.cr2ColRow("Area Name:",
                    sc.AREANAME, 20, 20, lc.inventory.getAreaname(""), "*"));
                invTable.addRow(ec.cr2ColRow("Domain:",
                    sc.DOMAIN, 20, 20, lc.inventory.getDomain(""), "*"));
                invTable.addRow(ec.cr2ColRow("Institute:",
                    createInstituteSelect(sc.INSTITCODE).toHTML()));
                invTable.addRow(ec.cr2ColRow("Platform:",
                    createPlatformSelect(sc.PLANAMCODE, lc.survey.getPlanam()).toHTML()));
                invTable.addRow(ec.cr2ColRow("Country:",
                    createCountrySelect(sc.COUNTRYCODE).toHTML()));
                //invTable.addRow(ec.cr2ColRow("Pass Key:",
                //    sc.PASSKEY, 20, 20, "", ""));

                mainTable.addRow(ec.cr1ColRow("<br>"));
                mainTable.addRow(ec.cr1ColRow(invTable.toHTML()));
            } else {
                form.addItem(new Hidden(sc.INVENTORYEXIST, "y"));
            } // if (checkInventory.length == 0)

            // add the table for passkey
            DynamicTable passTable = new DynamicTable(2);
            passTable.setFrame(ITableFrame.BOX).setCenter();

            passTable.addRow(ec.crSpanColsRow(
                "Please enter the password for FLAGGED data",2));
            passTable.addRow(ec.cr2ColRow("Pass Key:",
                sc.PASSKEY, 20, 20, "", ""));

            mainTable.addRow(ec.cr1ColRow("<br>"));
            mainTable.addRow(ec.cr1ColRow(passTable.toHTML()));

            // add the table for loadlog details
            DynamicTable logTable = new DynamicTable(2);
            logTable.setFrame(ITableFrame.BOX).setCenter();

            logTable.addRow(ec.crSpanColsRow(
                "Please enter / update the loadlog details",2));
            logTable.addRow(ec.cr2ColRow("Load Id (Lyy/mm/seqNo):",
                sc.LOADID, 10, 10, "", "*"));
            logTable.addRow(ec.cr2ColRow("Source of data:",
                sc.SOURCE, 30, 30, "Marcel van den Bergh", "*"));
            logTable.addRow(ec.cr2ColRow("Responsible person:",
                sc.RESPPERSON, 30, 30, "Louise Watt", "*"));
            logTable.addRow(ec.cr2ColRow("Institute:",
                sc.INSTITUTE, 30, 30, lc.institute, "*"));

            mainTable.addRow(ec.cr1ColRow("<br>"));
            mainTable.addRow(ec.cr1ColRow(logTable.toHTML()));
            //*** if yes = display fields only
            //*** if no = input boxes for some fields
            //*** add submit button
            //*** add some hidden variables?

            if ("sast".equals(lc.timeZone)) {
                mainTable.addRow(ec.cr1ColRow("<br>"));
                mainTable.addRow(ec.crSpanColsRow(
                    "Date/times will be converted from SAST to UTC", 1,
                    true, "red", IHAlign.CENTER));
            } // if ("sast".equals(lc.timeZone))
            mainTable.addRow(ec.cr1ColRow("<br>"));

            // put link of load report on page
            String tempFile = fileName + ".preload_report";
            Link link =
                new Link (sc.LOADS_URL + "marine/" + tempFile, tempFile);
            mainTable.addRow(ec.cr1ColRow(
                "The preload report: " + link.toHTML()));
            mainTable.addRow(ec.cr1ColRow("<br>"));

            //form.addItem(JSLLib.OnClickButton("Go!","btnIFI")).setCenter();
            mainTable.addRow(ec.cr1ColRow(
                JSLLib.OnClickButton("Go!","btnIFI").setCenter().toHTML()));
            //mainTable.addRow(ec.cr1ColRow(new Submit("","Go!").setCenter().toHTML()));
            //form.addItem(new Submit("","Go!").setCenter());

        } // if (lc.fatalCount == 0)

        form.addItem(mainTable);
        this.addItem(form.setCenter());

        // close the files
        lc.closeFiles();
/*
        try {
            inputFile.close();
            lc.debugFile.close();
            lc.reportFile.close();
        } catch(Exception e) {
        } //
*/
    } // constructor


    /**
     * Add the javascript stuff to the page
     */
    Container JavaScript(int checkInventoryLength) {
        if (dbg) System.out.println("<br>checkInventoryLength = " + checkInventoryLength);
        Container con = new Container();
        con.addItem(JSLLib.OpenScript());
        con.addItem(JSLLib.RtnNotNull());

        con.addItem(JSLLib.OpenEvent("INPUT","Validate"));
        JSLLib.formName = "thisForm";
        if (checkInventoryLength == 0) {
            con.addItem(JSLLib.CallNotNull(sc.PROJECTNAME,"Project Name"));
            con.addItem(JSLLib.CallNotNull(sc.CRUISENAME,"Cruise Name"));
            con.addItem(JSLLib.CallNotNull(sc.AREANAME,"Area Name"));
            con.addItem(JSLLib.CallNotNull(sc.DOMAIN,"Domain"));
        } // if (checkInventoryLength > 0)
        con.addItem(JSLLib.CallNotNull(sc.LOADID, "Load ID"));
        con.addItem(JSLLib.CallNotNull(sc.SOURCE, "Source of data"));
        con.addItem(JSLLib.CallNotNull(sc.RESPPERSON, "Responsible person"));
        con.addItem(JSLLib.CallNotNull(sc.INSTITUTE, "Institute"));
        con.addItem(JSLLib.CloseEvent());

        con.addItem(JSLLib.OpenEvent("btnIFI","OnClick"));
        con.addItem(JSLLib.CallValidate("INPUT"));
        con.addItem(JSLLib.StandardSubmit(false));
        con.addItem(JSLLib.CloseEvent());

        con.addItem(JSLLib.CloseScript());

        return con;
    } // JavaScript


    /**
     * Update the inventory with the start date-time
     */
    void closeStatistics() {
        //*** done elsewhere ?

    } // closeStatistics


    /**
     * Create a drop-down box of institutes
     * @param  name   the parameter name
     * @return  the html Select statement
     */
    Select createInstituteSelect(String name) {
        Select list = new Select(name);
        //list.addOption(new Option("Institutes"));
        MrnInstitutes institutes[] = new MrnInstitutes().get(
            "1=1",MrnInstitutes.NAME);
        for (int i = 0; i < institutes.length; i++) {
            list.addOption(new Option(
                institutes[i].getName(""), institutes[i].getCode(""), false));
        } // for (int i = 0; i < institutes.length; i++)
        return list;
    } //createInstituteSelect


    /**
     * Create a drop-down box of platform names
     * @param  name   the parameter name
     * @return  the html Select statement
     */
    Select createPlatformSelect(String name, String platformName) {
        Select list = new Select(name);
        //list.addOption((new Option("Platform Names")));
        MrnPlanam planam[] = new MrnPlanam().get(
            "1=1",MrnPlanam.NAME);
        for (int i = 0; i < planam.length; i++) {
            boolean defaultOption = false;
            if (platformName.equals(planam[i].getName(""))) defaultOption = true;
            list.addOption(new Option(
                planam[i].getName(""), planam[i].getCode(""), defaultOption));
        } // for (int i = 0; i < planam.length; i++)
        return list;
    } // createPlatformSelect


    /**
     * Create a drop-down box of countries
     * @param  name   the parameter name
     * @return  the html Select statement
     */
    Select createCountrySelect(String name) {
        Select list = new Select(name);
        //list.addOption((new Option("Countries")));
        MrnCountry country[] = new MrnCountry().get(
            "1=1",MrnCountry.NAME);
        for (int i = 0; i < country.length; i++) {
            list.addOption(new Option(
                country[i].getName(""), country[i].getCode(""), false));
        } // for (int i = 0; i < country.length; i++)
        return list;
    } // createCountrySelect


    /**
     * Display the station details on the screen
     */
    void displayStationDetails() {

        DynamicTable dupStationTable = new DynamicTable(1);
        dupStationTable.setFrame(ITableFrame.BOX)
                       .setBorder(2)
                       .setBorderColor("blue");

        for (int i = 0; i < lc.tStation.length; i++) {

            if (lc.stationExistsArray[i]) {

                DynamicTable dupTable = new DynamicTable(3);
                dupTable.setFrame(ITableFrame.BOX); //.setCellPadding(5);
                String sp = "&nbsp;";

                // sort out the header
                String head = "Duplicate ";
                head += (lc.stationStatusDB[i].startsWith("di")
                    ? "Station-Id" : "station (date,lat,lon,time,subdes)");
                if (dbg) System.out.println("<br>displayStationDetails: " +
                    "lc.stationStatus[i] = " +
                    lc.stationStatusDB[i] + " " + head);

                //String colour = (lc.loadFlag ? "green" : "red");
                String colour = "red";

                if (dbg) System.out.println("<br>displayStationDetails: dupTable: " +
                    "lc.tStation[i] = " + lc.tStation[i]);

                // build up the table
                dupTable.addRow(ec.crSpanColsRow(
                    head, 3, true, colour, IHAlign.CENTER));
                dupTable.addRow(ec.cr3ColRow("<br>",
                    formatStr("<b>Existing Details</b>"),
                    formatStr("<b>New Details</b>")));
                dupTable.addRow(ec.cr3ColRow(
                    formatStr("Survey Id:"+sp),
                    formatStr(sp+lc.tStation[i].getSurveyId("")),
                    formatStr(sp+lc.station.getSurveyId(""))));
                dupTable.addRow(ec.cr3ColRow(
                    formatStr("Station Id:"+sp),
                    formatStr(sp+lc.tStation[i].getStationId("")),
                    formatStr(sp+lc.station.getStationId(""))));
                dupTable.addRow(ec.cr3ColRow(
                    formatStr("Latitude:"+sp),
                    formatStr(sp+lc.tStation[i].getLatitude("")),
                    formatStr(sp+lc.station.getLatitude(""))));
                dupTable.addRow(ec.cr3ColRow(
                    formatStr("Longitude:"+sp),
                    formatStr(sp+lc.tStation[i].getLongitude("")),
                    formatStr(sp+lc.station.getLongitude(""))));
                dupTable.addRow(ec.cr3ColRow(
                    formatStr("Start Date/Time:"+sp),
                    //formatStr(sp+lc.tStation[i].getDateStart("").substring(0,10)),
                    //formatStr(sp+lc.station.getDateStart("").substring(0,10))));
                    formatStr(sp+lc.spldattimArray[i].substring(0,16)),
                    formatStr(lc.startDateTime.substring(0,16))));
                dupTable.addRow(ec.cr3ColRow(
                    formatStr("Station Name:"+sp),
                    formatStr(sp+lc.tStation[i].getStnnam("")),
                    formatStr(sp+lc.station.getStnnam(""))));
                dupTable.addRow(ec.cr3ColRow(
                    formatStr("Station Depth:"+sp),
                    formatStr(sp+lc.tStation[i].getStndep("")),
                    formatStr(sp+lc.station.getStndep(""))));
                dupTable.addRow(ec.cr3ColRow(
                    formatStr("No of Current Records:"+sp),
                    formatStr(sp+lc.currentsRecordCountArray[i]),
                    formatStr(sp+(lc.stationSampleCount-lc.stationSampleRejectCount))));
                dupTable.addRow(ec.cr3ColRow(
                    formatStr("No of Watphy Records:"+sp),
                    formatStr(sp+lc.watphyRecordCountArray[i]),
                    formatStr(sp+0)));
        //        if (lc.stationStatus2.startsWith("ds")) {
        //            dupTable.addRow(ec.cr3ColRow(
        //                formatStr("Sample Time:"+sp),
        //                formatStr(sp+lc.tWatphy[0].getSpldattim("")),
        //                formatStr(sp+watphy.getSpldattim(""))));
        //        } // if (lc.stationStatus2.startswith("ds"))
                dupTable.addRow(ec.crSpanColsRow(
                    formatStr("Number of records with same SUBDES </b>(" +
                    lc.currents.getSubdes() + ") : " + lc.subdesCount[i] + "<b>"), 3));
                dupTable.addRow(ec.crSpanColsRow(
                    formatStr("Update the STATION record? " +
                    new CheckBox(sc.UPDATESTATION, lc.station.getStationId("")+i)), 3));
                if (lc.subdesCount[i] > 0) {
                    dupTable.addRow(ec.crSpanColsRow(formatStr(
                        "Replace the DATA (currents) records for this station? " +
                        new CheckBox(sc.REPLACEDATA, lc.station.getStationId("")+i)), 3));
                } // if (lc.subdesCount > 0)

                dupStationTable.addRow(ec.cr1ColRow(dupTable.toHTML()));

            } // if (lc.stationExistsArray[i])

        } // for (int i = 0; i < lc.tStation.length; i++)

        dupStationsTable.addRow(ec.cr1ColRow(dupStationTable.toHTML()));
        dupStationsTable.addRow(ec.cr1ColRow("<br>"));

    } // displayStationDetails

    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {

        if (dbg) for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);
        } // if dbg

        fileName    = ec.getArgument(args,sc.FILENAME);
        lc.timeZone = ec.getArgument(args,sc.TIMEZONE).toLowerCase();
        sessionCode= ec.getArgument(args, sc.SESSIONCODE);
        userType = ec.getArgument(args, sc.USERTYPE);
        menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

    }   //  getArgParms





/** utility functions


    /**
     * Format text strings for a html page, setting the font size = 2
     * @param  oldStr  the text string
     * @return the Container containing the formatted string
     */
    String formatStr(String oldStr)   {
        return new SimpleItem(oldStr).setFontSize(2).toHTML();
    }  //  formatStr


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("PreLoadMRNCurFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreLoadMRNCurFrame local= new PreLoadMRNCurFrame(args);
            bd.addItem(new PreLoadMRNCurFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "PreLoadMRNCurFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class PreLoadMRNCurFrame
