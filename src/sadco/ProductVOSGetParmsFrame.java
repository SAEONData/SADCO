package sadco;

import common2.*;
import java.util.*;
import java.io.*;
import oracle.html.*;

/**
 * Choose an output file for your products.
 *
 * SadcoVOS
 * screen.equals("product")
 * version.equals("getparms")
 *
 * @author  001206 - SIT Group
 * @version
 * 001206 - Dave McKelly       - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 091215 - Ursula von St Ange - change for inventory users (ub01)          <br>
 */
public class ProductVOSGetParmsFrame extends CompoundItem{

    boolean     dbg = false;
    //boolean     dbg = true;
    String thisClass = this.getClass().getName();

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    // work variables
    float   areaDifference;
    String [] trueFalse = new String[sc.MAX1];

    // variables on first line
    float latitudeMinO = 0.0f;
    float longitudeMinO = 0.0f;
    float latitudeMaxO = 0.0f;
    float longitudeMaxO = 0.0f;
    String startDateO = "";
    String endDateO = "";
    int numRecords = 0;

    // Product constants
    static final int INVEN = 1;
    static final int MEANS = 2;
    static final int ROSES = 3;
    static final int HISTO = 4;
    static final int SCATT = 5;
    static final int TIMES = 6;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ProductVOSGetParmsFrame (String args[]) {

        try {
            ProductVOSGetParmsFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ProductVOSGetParmsFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void ProductVOSGetParmsFrameActual(String args[]) {

        // get url parameter values
        String sessionCode = ec.getArgument(args,sc.SESSIONCODE);
        String productStr = ec.getArgument(args,sc.PRODUCT);
        int product = Integer.parseInt(productStr.trim());
        String fileName = ec.getArgument(args,sc.FILENAME);
        // for the top menu bar
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // ................ EXTRACT THE USER record from db .....................
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        if (dbg) System.out.println("<br>Session code = " + session);
        String userId = sessions[0].getUserid();

        // .................PATH NAME: Get the correct........................
        String pathName = "";
        //if (ec.getHost().equals("morph")) {
        //    pathName = sc.MORPH + userId + "/";
        //} else {
        //    pathName = sc.LOCAL;
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                pathName = sc.HOSTDIR + "inv_user";                     //ub01
            } else {                                                    //ub01
                pathName = sc.HOSTDIR + userId;                         //ub01
            } // if ("0".equals(userType))                              //ub01
            //pathName = sc.HOSTDIR + userId;
        } else {
            pathName = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(sc.HOST)) {

        // ...........OPEN THE DATA FILE AND READ FIRST LINE ................
        String line = "";
        try {
            RandomAccessFile ifile =
                new RandomAccessFile(pathName + "/" + fileName, "r");
            line = ifile.readLine();
            ifile.close();
        } catch (Exception e) {
            System.out.println("<br>" + thisClass + ".constructor: " +
                "open file error: " + e.getMessage());
            e.printStackTrace();
        }  // END try
        if (dbg) System.out.println("<br>" + pathName + " / " + fileName);
        if (dbg) System.out.println("<br>" + line);

        //................READ DATA from FIRST LINE.............
        StringTokenizer t = new StringTokenizer (line, " ");
        int i = 0;
        while (t.hasMoreTokens()) {
            String token = t.nextToken().trim();
            switch (i) {
                case 1: latitudeMinO = Float.valueOf(token).floatValue(); break;
                case 2: latitudeMaxO = Float.valueOf(token).floatValue(); break;
                case 3: longitudeMinO = Float.valueOf(token).floatValue(); break;
                case 4: longitudeMaxO = Float.valueOf(token).floatValue(); break;
                case 5: startDateO = token; break;
                case 6: endDateO = token; break;

                case 8: numRecords = Integer.valueOf(token).intValue(); break;
                default: break;
            } // switch
            i++;
        } // while

        //................CREATE TABLES.................
        DynamicTable leftTable = new DynamicTable(2);
        leftTable.setBorder(0).setFrame(ITableFrame.VOLD).setRules(ITableRules.NONE);

        DynamicTable rightTable = new DynamicTable(2);
        rightTable.setBorder(0).setFrame(ITableFrame.VOLD).setRules(ITableRules.NONE);

        /*
        // Enter year and month ranges
        leftTable.addRow(ec.cr2ColRow(
            chFSize("Start Year<i>(yyyy)</i>:","-1"), sc.STARTYEAR, 5, 5,
            startDateO.substring(0,4)));
        leftTable.addRow(ec.cr2ColRow(
            chFSize("End Year<i>(yyyy)</i>:","-1"), sc.ENDYEAR, 5, 5,
            endDateO.substring(0,4)));
        //enter month
        leftTable.addRow(ec.cr2ColRow(
            chFSize("Start Month <i>(mm)</i>:","-1"), sc.STARTMONTH, 5, 5, "1"));
        leftTable.addRow(ec.cr2ColRow(
            chFSize("End Month<i>(mm)</i>:","-1"), sc.ENDMONTH, 5, 5, "12"));

        //BREAK
        leftTable.addRow(ec.crSpanColsRow("<br>",2));
        rightTable.addRow(ec.crSpanColsRow("<br>",2));
        */

        // Enter year and month ranges
        leftTable.addRow(ec.cr2ColRow(
            chFSize("Year Range :","-1"),
            new TextField(sc.STARTYEAR,5,5,startDateO.substring(0,4)).toHTML() +
            chFSize(" to ","-1") +
            new TextField(sc.ENDYEAR,5,5,endDateO.substring(0,4)).toHTML()));

        leftTable.addRow(ec.cr2ColRow(
            chFSize("Month Range :","-1"),
            new TextField(sc.STARTMONTH,5,5,"1").toHTML() +
            chFSize(" to ","-1") +
            new TextField(sc.ENDMONTH,5,5,"12").toHTML()));


        //................SWITCH....................................
        switch (product){
            case(INVEN): //inventories
                parameterSelection(leftTable);
                diagramSelection(rightTable, line, product);

                //................PLOT TYPE SELECTION--Add DROP-DOWN MENU ....................
                Select plotTypeSelect = new Select(sc.PLOTTYPE);
                plotTypeSelect.addOption(new Option(
                    "Grid with totals in each sector", "1", false));
                plotTypeSelect.addOption(new Option("Symbolic", "2", false));

                //................PLOT TYPE SELECTION input box....................
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Plot Type Selection:","-1"), plotTypeSelect));
                rightTable.addRow(ec.crSpanColsRow("<br>",2));
                break;

            case(MEANS): //means
                parameterSelection(leftTable);
                diagramSelection(rightTable, line, product);
                multiSelection(rightTable, "table");

                //................TABLE FORMAT SELECTION--Add DROP-DOWN MENU ....................
                Select tableTypeSelect = new Select(sc.TABLETYPE);
                tableTypeSelect.addOption(new Option("Block Tables","0", false));
                tableTypeSelect.addOption(new Option("Column Tables", "1", false));

                //................TABLE TYPE SELECTION input box....................
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Table Selection:","-1"), tableTypeSelect));
                rightTable.addRow(ec.crSpanColsRow("<br>",2));
                break;

            case(ROSES): //rose
                //................ROSE ELEMENT:--Add DROP-DOWN MENU ....................
                Select elementTypeSelect = new Select(sc.ELEMENTTYPE);
                elementTypeSelect.addOption(new Option("Wind","W", false));
                elementTypeSelect.addOption(new Option("Swell", "S", false));
                elementTypeSelect.addOption(new Option("Windwave", "Z", false));

                //................ROSE ELEMENT: input box....................
                leftTable.addRow(ec.crSpanColsRow("<br>",2));
                leftTable.addRow(ec.cr2ColRow(
                    chFSize("Rose Element:","-1"), elementTypeSelect));
                multiSelection(leftTable, "rose");

                diagramSelection(rightTable, line, product);
                rightTable.addRow(ec.crSpanColsRow("<br>",2));
                break;

            case(HISTO): //histograms
                parameterSelectionOneOf(leftTable);
                diagramSelection(rightTable, line, product);
                rightTable.addRow(ec.crSpanColsRow(
                    "Axis range " + chFSize("<i>(optional)</i>:","-1.5"),2));
                rightTable.addRow(ec.crSpanColsRow(chFSize("Range: ","-1") +
                    new TextField(sc.STARTRANGE,5,5,"0").toHTML() +
                    chFSize(" to ","-1") +
                    new TextField(sc.ENDRANGE,5,5,"0").toHTML() + "<br>",
                    2, true, "", IHAlign.RIGHT));

                rightTable.addRow(ec.crSpanColsRow("<br>",2));
                rightTable.addRow(ec.crSpanColsRow("<br><br>",2));
                break;

            case(SCATT): //scattergrams
                parameterSelectionXY(leftTable);
                diagramSelection(rightTable, line, product);
                rightTable.addRow(ec.crSpanColsRow(
                    "Axes ranges " + chFSize("<i>(optional)</i>:","-1.5"),2));
                rightTable.addRow(ec.cr2ColRow(chFSize("X Axis Range: ","-1"),
                    new TextField(sc.XSTARTRANGE,5,5,"0").toHTML() +
                    chFSize(" to ","-1") +
                    new TextField(sc.XENDRANGE,5,5,"0").toHTML()));
                rightTable.addRow(ec.cr2ColRow(chFSize("Y Axis Range: ","-1"),
                    new TextField(sc.YSTARTRANGE,5,5,"0").toHTML() +
                    chFSize(" to ","-1") +
                    new TextField(sc.YENDRANGE,5,5,"0").toHTML() + "<br><br>"));
                multiSelection(rightTable, "scattergram");
                break;

            case(TIMES): //time series
                parameterSelectionOneOf(leftTable);
                diagramSelection(rightTable, line, product);

                //................ TIME UNITS:--Add DROP-DOWN MENU ....................
                Select timeTypeSelect = new Select(sc.TIMEUNITS);
                timeTypeSelect.addOption(new Option("Default","0", false));
                timeTypeSelect.addOption(new Option("Day", "D", false));
                timeTypeSelect.addOption(new Option("Month", "M", false));
                timeTypeSelect.addOption(new Option("Year", "Y", false));

                //................ Std deviation:--Add DROP-DOWN MENU ....................
                Select stdTypeSelect = new Select(sc.STDDEV);
                stdTypeSelect.addOption(new Option("No","N", false));
                stdTypeSelect.addOption(new Option("Yes", "Y", false));

                //................TIME SERIES ELEMENT:--Add DROP-DOWN MENU ....................
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Days Range:","-1"),
                    new TextField(sc.DAYRLOW,5,5,"0").toHTML() +
                    chFSize("to ","-1") +
                    new TextField(sc.DAYRHIGH,5,5,"31").toHTML()));
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Hours Range:","-1"),
                    new TextField(sc.HOURRLOW,5,5,"0").toHTML() +
                    chFSize("to ","-1") +
                    new TextField(sc.HOURRHIGH,5,5,"23").toHTML()));
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Axis Range:","-1"),
                    new TextField(sc.AXISRLOW,5,5,"0").toHTML() +
                    chFSize("to ","-1") +
                    new TextField(sc.AXISRHIGH,5,5,"0").toHTML()));
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Time units: ","-1"), timeTypeSelect));
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Units per cell: ","-1"),sc.UNITPCELL,5,5,"0"));
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Std. deviation: ","-1"), stdTypeSelect));
                break;

            default:
                //showError();
        } // switch

        //................COMBINE MAIN + left + right TABLE..........
        DynamicTable mainTable = new DynamicTable(2);
        mainTable.setBorder(1).setFrame(ITableFrame.BOX);
        mainTable.addRow(ec.cr2ColRow(leftTable.toHTML(), rightTable.toHTML()));

        //................FORM................................
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST\" name=\"myForm", sc.VOS_APP, target);
        form.addItem (mainTable);
        form.addItem("<br>");
        form.addItem(new Submit("", "Go!").setCenter());
        form.addItem(new Hidden(sc.SCREEN, "product"));// parms to next page
        form.addItem(new Hidden(sc.VERSION, "product"));// parms to next page
        form.addItem(new Hidden(sc.PRODUCT, productStr));// parms to next page
        form.addItem(new Hidden(sc.FILENAME, fileName));// parms to next page
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));// parms to next page
        form.addItem(new Hidden(sc.TABLE, String.valueOf(areaDifference)));
        // for the menu system
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        //................ADD JAVASCRIPT STUFF................
        if ( (product == INVEN) || (product == MEANS) ) { // inventories, means
            JSLLib.formName = "myForm";
            this.addItem(JSLLib.BodyOnload());
            this.addItem(JSLLib.OpenScript());
            this.addItem(moreJavaScript());
            this.addItem(JSLLib.RtnDynamicSelect("myForm",sc.TABLESIZE,sc.BLOCKSIZE));
            //writeIt(plongitudewest.value,platitudenorth.value,ptablesize.value));
            this.addItem(JSLLib.CloseScript());
        } // inventories, means

        // ................FIRST LINE (in blue)................
        this.addItem("<center><font color=blue size=+2><b>" +
            sc.VOSPRODLIST[product-1] + "</b> for </font><font color=green size=+1> "  +
            fileName + "</font></center>");

        //mainTable.addRow(ec.crSpanColsRow("<br>",2));

        this.addItem("<br><center><font color=blue size=-1><u>Date Range:</u> " +
            startDateO + " to " + endDateO + ", <u>Number of Records:</u> " +
            numRecords + "<br> <u>Area Range</u>: " +
            latitudeMinO + " to " + latitudeMaxO + " S, " +
            longitudeMinO + " to " + longitudeMaxO + " E </font></center><br>");
        //mainTable.addRow(ec.crSpanColsRow("<br>",2));
        this.addItem(form.setCenter());

    } // ProductVOSGetParmsFrameActual


    // +-------------------------------------------------------+
    // | Weather parameter selection (one or more)             |
    // +-------------------------------------------------------+
    void parameterSelection(DynamicTable table) {

        // Add checkbox to form
        table.addRow(ec.crSpanColsRow("<br>Weather&nbsp;Parameter&nbsp;Selection",2));
        table.addRow(ec.crSpanColsRow("<font color=green size=2>" +
            "Select ONE or MORE of the following: </font>",2));
        String seqNum[] = {"1","2","3","4","5","6","7","8","9","10"};
        boolean tf = true;
        String parms = "";
        for (int i = 0; i < 10; i++) {
            parms += "\n" + chFSize(sc.VOSPARMSLIST[i],"-1") + " " +
                new CheckBox(sc.PARMS+seqNum[i], seqNum[i], tf).toHTML() +
                "&nbsp;&nbsp;<br>";
            tf = false;
        } // for int i
        table.addRow(ec.crSpanColsRow(parms,2,false,"",IHAlign.RIGHT));
    } // parameterSelection


    // +-------------------------------------------------------+
    // | Weather parameter selection (one only )               |
    // +-------------------------------------------------------+
    void parameterSelectionOneOf(DynamicTable table) {

        // Add RadioButton to form
        table.addRow(ec.crSpanColsRow("<br>Weather&nbsp;Parameter&nbsp;Selection",2));
        table.addRow(ec.crSpanColsRow("<font color=green size=2>" +
            "Select ONE of the following:<br>",2,true,"",IHAlign.RIGHT));
        String seqNum[] = {"1","2","3","4","5","6","7","8","9","10"};
        boolean tf = true;
        String parms = "";
        for (int i = 0; i < 10; i++) {
            parms += "\n" + chFSize(sc.VOSPARMSLIST[i],"-1") + " " +
                new Radio(sc.PARMS,seqNum[i],tf).toHTML() +
                "&nbsp;&nbsp;<br>";
            tf = false;
        } // for int i
        table.addRow(ec.crSpanColsRow(parms,2,false,"",IHAlign.RIGHT));
    } // parameterSelectionOneOf


    // +-------------------------------------------------------+
    // | METHOD for Weather parameter selection (x and y only) |
    // +-------------------------------------------------------+
    void parameterSelectionXY(DynamicTable table) {

        // Add RadioButton to form
        // I'm using a table to put the radio buttons in to get the X and Y
        // above the radio buttons
        table.addRow(ec.crSpanColsRow("<br>Weather&nbsp;Parameter&nbsp;Selection",3));
        table.addRow(ec.crSpanColsRow("<font color=green size=2>" +
            "Select one X and one Y:<br>",3,true,"",IHAlign.RIGHT));
        String seqNum[] = {"1","2","3","4","5","6","7","8","9","10"};
        boolean tf = true;
        String parms = "";
        DynamicTable tempTable = new DynamicTable(3);
        tempTable.setBorder(0).setCellSpacing(0).setCellPadding(0);
        tempTable.setFrame(ITableFrame.VOLD).setRules(ITableRules.NONE);
        TableRow row = new TableRow();
        row.addCell(new TableHeaderCell("&nbsp"));
        row.addCell(new TableHeaderCell("X"));
        row.addCell(new TableHeaderCell("Y"));
        tempTable.addRow(row);
        for (int i = 0; i < 10; i++) {
            row = new TableRow();
            row.addCell(new TableDataCell(chFSize(sc.VOSPARMSLIST[i],"-1"))
                .setHAlign(IHAlign.RIGHT));
            row.addCell(new Radio(sc.PARMS+"x",seqNum[i], tf).toHTML());
            row.addCell(new Radio(sc.PARMS+"y",seqNum[i], tf).toHTML());
            tempTable.addRow(row);
            tf = false;
        } // for int i
        table.addRow(ec.crSpanColsRow(tempTable.toHTML(),2,false,"",IHAlign.RIGHT));
    }

    // +---------------------------------------------------+
    // | METHOD to CREATE DIAGRAM, TABLE SIZE & BLOCK SIZE |
    // +---------------------------------------------------+

    void diagramSelection(DynamicTable table, String line, int product) {

        float longDif = longitudeMaxO - longitudeMinO;
        float latDif = latitudeMaxO - latitudeMinO;

        if (longDif > latDif){
            areaDifference = longDif;
        } else {
            areaDifference = latDif;
        }
        areaDifference = ec.ceiling(areaDifference,0);

        //................FLOOR VALUE - ........................
        float latitudeMin = ec.floor(latitudeMinO,0);
        float longitudeMin = ec.floor(longitudeMinO,0);

        //................CEILING ......................
        float latitudeMax = ec.ceiling(latitudeMaxO,0);
        float longitudeMax = ec.ceiling(longitudeMaxO,0);

        // ....... Initialize the value of the areadifference ARRAY[] .............
        for (int tf = 0; tf < sc.MAX1; tf++) {
            if ((tf+1) == areaDifference) {
                trueFalse[tf] = "true";
            } else {
                trueFalse[tf] = "";
            } // if (tf == areaDifference)
        } // for int tf

        //................CREATE SELECT BOXES.................
        Select tableSelect = new Select(sc.TABLESIZE +
            "\" onChange=\"updateMenus(this); updatePTable(this); " +
            "writeIt(plongitudewest.value,platitudenorth.value,ptable.value)");
        tableSelect.addOption(new Option("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " +
            "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " +
            "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " +
            "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"));
        tableSelect.addOption(new Option(""));
        tableSelect.addOption(new Option(""));

        Select blockSelect = new Select(sc.BLOCKSIZE);
        blockSelect.addOption(new Option("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " +
            "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " +
            "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " +
            "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"));
        blockSelect.addOption(new Option(""));
        blockSelect.addOption(new Option(""));
        if (dbg) System.out.println("blocksize <br>");

        //................IF STATEMENT for DIAGRAM................

        if ( (product == INVEN) || (product == MEANS) ) { // inventories, means

            //................DIAGRAM:................
            String latNJavaScript = "\" onChange=\"writeIt(" + sc.LONGITUDEWEST +
                ".value," + sc.LATITUDENORTH + ".value,ptable.value);\"\n";
            String latSJavaScript = "\" onFocus=\"this.blur()";
            String lonWJavaScript = "\" onChange=\"writeIt(" + sc.LONGITUDEWEST +
                ".value," + sc.LATITUDENORTH + ".value,ptable.value);\"\n";
            String lonEJavaScript = "\" onFocus=\"this.blur()";

            table.addRow(ec.latlonRangeInput(
                "Select Top right Corner",
                6,
                new Float (latitudeMin).toString(),
                new Float (latitudeMax).toString(),
                new Float (longitudeMin).toString(),
                new Float (longitudeMax).toString(),
                "<b><font color=green>N</font></b>", "<b>S</b>",
                "<b><font color=green>W</font></b>", "<b>E</b>",
                latNJavaScript, latSJavaScript,
                latNJavaScript, latSJavaScript,
                ""));
            //BREAKS
            table.addRow(ec.crSpanColsRow("<br>",2));

            //TABLE SIZE input box
            //TextField txt2 = new TextField(sc.TABLESIZE, 10, 10, "");
            table.addRow(ec.cr2ColRow(
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                chFSize("Table Size:<br><i>(degrees square)</i>","-1"),
                tableSelect.toHTML()));

            //BLOCK SIZE Size input box
            //txt2 = new TextField(sc.BLOCKSIZE, 10, 10, "");
            table.addRow(ec.cr2ColRow(
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                chFSize("Block Size: <br><i>(minutes square)</i>","-1"),
                blockSelect.toHTML()));
            //BREAKS
            table.addRow(ec.crSpanColsRow("<br>",2));

            if (dbg) System.out.println("blockSelect = " + blockSelect + "<br>");

        } else { // ROSES, HISTO, SCATT, TIMES

            //................DIAGRAM:................
            table.addRow(ec.latlonRangeInput(
                "Enter Range Selection",
                6,
                new Float (latitudeMin).toString(),
                new Float (latitudeMax).toString(),
                new Float (longitudeMin).toString(),
                new Float (longitudeMax).toString(),
                "<b><font color=green>N</font></b>",
                "<b><font color=green>S</font></b>",
                "<b><font color=green>W</font></b>",
                "<b><font color=green>E</font></b>",
                "", "", "", "", ""));
            table.addRow(ec.crSpanColsRow("<br>",2));

        } // if ((product == 1) ...
    } //diagramSelection

    // +---------------------------------------------------+
    // | METHOD to CREATE MULTITABLE SELECTION             |
    // +---------------------------------------------------+
    void multiSelection(DynamicTable table, String element)  {
        //String upElement = element.substring(0,1).toUpperCase() +
        //    element.substring(1);
        //................MULTIPLE SELECTION--Add DROP-DOWN MENU ....................
        Select multiTypeSelect = new Select("pmultitype");
        multiTypeSelect.addOption(new Option("Only 1 "+element+" required","1", false));
        multiTypeSelect.addOption(new Option("12 month "+element+"s", "2", false));
        multiTypeSelect.addOption(new Option("4 seasonal "+element+"", "3", false));
        multiTypeSelect.addOption(new Option("A "+element+" / month (yrs*12)", "4", false));

        //................PLOT TYPE SELECTION input box....................
        table.addRow(ec.cr2ColRow(
            chFSize("Multi-"+element+" Selection: ","-1"), multiTypeSelect));

    } // multiSelection


    //................INFO FOR JAVASCRIPT   - drop-down menu................
    Container moreJavaScript(){

        Container con = new Container();
        con.addItem("/*****************************************************************/\n");
        con.addItem("function writeIt(the_long,the_lat,the_table)\n" +
                    "{\n" +
                    "  var new_long = the_long - 0;\n" +
                    "  var new_lat = the_lat - 0;\n" +
                    "  var new_table = the_table - 0;\n" +
                    "  var word_long_return = new_long + new_table;\n" +
                    "  window.document.myForm.plongitudeeast.value = word_long_return;\n" +
                    "  var word_lat_return = new_lat + new_table;\n" +
                    "  window.document.myForm.platitudesouth.value = word_lat_return;\n" +
                    "}\n");
        con.addItem("function updatePTable(what)\n" +
                    "{\n" +
                    "  var n = what.selectedIndex;\n" +
                    "  /* window.alert(\"n = \" + n); */\n" +
                    "  what.form.ptable.value = what.form.ptablesize.options[n].value;\n" +
                    "}\n");
        con.addItem("/*****************************************************************/\n");

        con.addItem("var menu = new Array (\n");
        for (int i = 0; i < sc.MAX1; i++) {
            con.addItem("\"" + (i+1) + "|" + (i+1) + "|" + trueFalse[i] + "*");
            for (int j = 0; j < sc.MAX2; j ++) {
                if (sc.BSIZE[i][j] != 0) {
                    String quote = "\"";
                    if (j == 0) quote = "";
                    String truef = "";
                    if (j == 5) truef = "|true";
                    String plus = "+";
                    if (sc.MINSQ[i][j] == 20) plus = ",";
                    if ((i == (sc.MAX1-1)) && (sc.MINSQ[i][j] == 20)) plus = ")";
                    con.addItem(quote + i + "-" + j + "|" + sc.BSIZE[i][j] + "(" +
                        sc.MINSQ[i][j] + "x" + sc.MINSQ[i][j] + ")" +
                        truef + "#\"" + plus + "\n");
                } // if (sc.BSIZE[i][j] != 0)
            } // for (int j = 0; j < MAX2; j ++)
        } // for (int i = 0; i < MAX1; i++)

        return con;

    } // moreJavaScript

            // +------------------------------------------+
            // | METHOD to return tables with area input |
            // +------------------------------------------+

    /**
     * Return a row for lat/lon range input
     */
/*    public TableRow latlonRangeInput(
            String prompt,
            int length,
            String latNdefault,
            String latSdefault,
            String lonWdefault,
            String lonEdefault,
            String compulsory,
            int product)   {
        // constants for area
        String latitudeNorthName = "latitudenorth";
        String latitudeSouthName = "latitudesouth";
        String longitudeWestName = "longitudewest";
        String longitudeEastName = "longitudeeast";
        String areaPrompt = "Enter latitude (N/S) and longitude (E/W) " +
            "limits in decimal degrees<br>" +
            "(South = POSITIVE latitude, West = NEGATIVE longitude)";

        // geographical area
        TableRow row = new TableRow();

        // prompt
        String cellContents = prompt;
        if ((cellContents.equals("p")) || (cellContents.equals(""))) {
            cellContents = areaPrompt;
        }
        cellContents = "<b>" + cellContents + "</b><br>";
        if (!compulsory.equals("*")) compulsory = "&nbsp;";

        // ...................CREATE SELECT BOXES.................
        Select wsSelect = new Select(sc.LONGITUDEWEST +
            "\" onChange=\"updateMenus(this)");
        wsSelect.addOption(new Option("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " +
            "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " +
            "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " +
            "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"));
        wsSelect.addOption(new Option(""));
        wsSelect.addOption(new Option(""));

        // latitude WEST and EAST input box
        for (int i = 0; i < length; i++) cellContents += "&nbsp;&nbsp;";

        if ((product == INVEN) || (product == MEANS)) { // inventories, means
            cellContents += compulsory + " " +
                new TextField(sc.LONGITUDEWEST + "\" onChange=\"" +
                    "writeIt(" + sc.LONGITUDEWEST + ".value," + sc.LATITUDENORTH +
                    ".value,ptable.value);\"\n",length,length,
                    lonWdefault) + "&nbsp;&nbsp;&nbsp;&nbsp;" +
                new TextField(sc.LONGITUDEEAST + "\" onFocus=\"this.blur()",
                    length,length,lonEdefault) + " <br>";
        } else { // roses
            cellContents += compulsory + " " +
                new TextField(sc.LONGITUDEWEST,length,length,lonWdefault) +
                "&nbsp;&nbsp;&nbsp;&nbsp;" +
                new TextField(sc.LONGITUDEEAST,length,length,lonEdefault) +
                " " + compulsory + "<br>";
        } // if ((product == 1) || (product == 2))


        // longitude West and East names
        cellContents += "&nbsp;&nbsp;&nbsp;";
        for (int i = 0; i < length; i++) cellContents += "&nbsp;&nbsp;";

        if ((product == 1) || (product == 2)) { // inventories, means
            cellContents += "<font color = green><b>W</b></font>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;E<br>\n";
        } else {
            cellContents += "<font color = green><b>W</font></b>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<font color = green><b>E</b></font><br>\n";
        } // if ((product == 1) || (product == 2))

        // latitude North input box
        if ((product == 1) || (product == 2)) { // inventories, means
            cellContents += compulsory + " " +
                new TextField(sc.LATITUDENORTH + "\" onChange=\"writeIt(" +
                    sc.LONGITUDEWEST + ".value," + sc.LATITUDENORTH +
                    ".value,ptable.value);\"\n",length, length,
                    latNdefault).toHTML() +
                "<font color = green><b>N</b></font>-|-------------|--<br>\n";
            // vertical bars
            for (int j = 0; j < 2; j++) {
                cellContents += "&nbsp;&nbsp;&nbsp;&nbsp;";
                for (int i = 0; i < length; i++) cellContents += "&nbsp;&nbsp;";
                cellContents += "&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>\n";
            } // for int j
            // latitude South input box
            cellContents += "&nbsp;" + " " +
                new TextField(sc.LATITUDESOUTH + "\" onFocus=\"this.blur()",
                    length, length, latSdefault).toHTML() +
                "S-|-------------|--<br>\n";
        } else { // roses
            cellContents += compulsory + " " +
                new TextField(
                    sc.LATITUDENORTH,length,length,latNdefault).toHTML() +
                "<font color = green><b>N</font></b>-|-------------|--<br>\n";
            // vertical bars
            for (int j = 0; j < 2; j++) {
                cellContents += "&nbsp;&nbsp;&nbsp;&nbsp;";
                for (int i = 0; i < length; i++) cellContents += "&nbsp;&nbsp;";
                cellContents += "&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>\n";
            } // for int j
            // latitude South input box
            cellContents += compulsory + " " +
                new TextField(
                    sc.LATITUDESOUTH,length,length,latSdefault).toHTML() +
                "<font color = green><b>S</b></font>-|-------------|--<br>\n";
        } // if ((product == 1) || (product == 2))

        row.addCell(new TableDataCell(cellContents)
            .setColSpan(2).setHAlign(IHAlign.CENTER));
        return row;
    }  // latlonRangeInput
*/
    /**
     * Change the font size of html text
     */
    String chFSize(String text, String size) {
        return "<font size=" + size + ">" + text + "</font>";
    } // chFSize

} // ProductVOSGetParmsFrame class



