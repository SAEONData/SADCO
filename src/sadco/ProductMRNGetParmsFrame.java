package sadco;

import common2.*;
import java.util.*;
import java.io.*;
import oracle.html.*;

/**
 * Display the correct forms to input parameters for the products.          <br>
 *
 * SadcoMRN
 * screen.equals("product")
 * version.equals("getparms")
 *
 * @author 001206 - SIT Group
 * @version
 * 001206 - Dave McKelly       - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020709 - Ursula von St Ange - dtspl not 1 deg square, but n deg square.
 *                               Change input to same as CreateOtherFormats <br>
 * 091215 - Ursula von St Ange - change for inventory users (ub01)          <br>
 */
public class ProductMRNGetParmsFrame extends CompoundItem{

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

    // vairables on first line
    float latitudeMinO = 0.0f;
    float longitudeMinO = 0.0f;
    float latitudeMaxO = 0.0f;
    float longitudeMaxO = 0.0f;
    String startDateO = "";
    String endDateO = "";
    String discipline = "";
    String surveyId = "";
    int numRecords = 0;


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ProductMRNGetParmsFrame (String args[]) {

        try {
            ProductMRNGetParmsFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ProductMRNGetParmsFrame constructor

    /**
    * Creates a page with a banner and an entry form
    * @param args the string array of url-type name-value parameter pairs
    */
    void ProductMRNGetParmsFrameActual(String args[]) {

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
                pathName = sc.HOSTDIR + "inv_user/";                    //ub01
            } else {                                                    //ub01
                pathName = sc.HOSTDIR + userId + "/";                   //ub01
            } // if ("0".equals(userType))                              //ub01
            //pathName = sc.HOSTDIR + userId + "/";                     //ub01
        } else {
            pathName = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(sc.HOST)) {


        // ...........OPEN THE DATA FILE AND READ FIRST LINE ................
        String line = "";
        try {
            RandomAccessFile ifile =
                new RandomAccessFile(pathName + fileName, "r");
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
                case 7: discipline = token; break;
                case 8: numRecords = Integer.valueOf(token).intValue(); break;
                case 9: surveyId = token; break;
                default: break;
            } // switch
            i++;
        } // while

        //................CREATE TABLES.................
        DynamicTable leftTable = new DynamicTable(2);
        leftTable.setBorder(0).setFrame(ITableFrame.VOLD).setRules(ITableRules.NONE);

        DynamicTable rightTable = new DynamicTable(2);
        rightTable.setBorder(0).setFrame(ITableFrame.VOLD).setRules(ITableRules.NONE);

        // Enter year and month ranges
        leftTable.addRow(ec.cr2ColRow(
            chFSize(" Year Range :","-1"),
            new TextField(sc.STARTYEAR,5,5,startDateO.substring(0,4)).toHTML() +
            chFSize(" to ","-1") +
            new TextField(sc.ENDYEAR,5,5,endDateO.substring(0,4)).toHTML()));

        leftTable.addRow(ec.cr2ColRow(
            chFSize(" Month Range :","-1"),
            new TextField(sc.STARTMONTH,5,5,"1").toHTML() +
            chFSize(" to ","-1") +
            new TextField(sc.ENDMONTH,5,5,"12").toHTML()));

        if ( product != SadConstants.MOTHR) {
            //enter depth
            leftTable.addRow(ec.cr2ColRow(
                chFSize(" Depth Range :","-1"),
                new TextField(sc.STARTDEPTH,5,5,"0").toHTML() +
                chFSize(" to ","-1") +
                new TextField(sc.ENDDEPTH,5,5,"9999").toHTML()));
            leftTable.addRow(ec.crSpanColsRow("<br>",2));
            rightTable.addRow(ec.crSpanColsRow("<br>",2));
        } // if ( product != SadConstants.MOTHR) {

        //................SWITCH....................................
        switch (product){
            case(SadConstants.MINVN): //inventories
                parameterSelection(leftTable, product);
                diagramSelection(rightTable, line, product);

                //................PLOT TYPE SELECTION--Add DROP-DOWN MENU ....................
                Select plotTypeSelect = new Select(sc.PLOTTYPE);
                plotTypeSelect.addOption(new Option(
                    "Grid with totals in each sector", "1", false));
                plotTypeSelect.addOption(new Option("Symbolic", "2", false));

                //................PLOT TYPE SELECTION input box....................
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Plot Type Selection:","-1"), plotTypeSelect));
                rightTable.addRow(ec.crSpanColsRow("<br><br><br><br>",2));
                break;

            case(SadConstants.MTSPL): //Temperature Salinity Curves
                diagramSelection(rightTable, line, product);

                leftTable.addRow(ec.crSpanColsRow(" Axes Limits ",3,true,""
                    ,IHAlign.CENTER,"+0"));
                leftTable.addRow(ec.cr2ColRow(
                    chFSize("Temperature :","-1"),
                    new TextField(sc.TEMPLOW,5,5,"-5").toHTML() +
                    chFSize("to ","-1") +
                    new TextField(sc.TEMPHIGH,5,5,"30").toHTML()));
                leftTable.addRow(ec.cr2ColRow(
                    chFSize("Salinity :","-1"),
                    new TextField(sc.SALINLOW,5,5,"33.5").toHTML() +
                    chFSize("to ","-1") +
                    new TextField(sc.SALINHIGH,5,5,"36").toHTML()));

                //................Multitable SELECTION--Add DROP-DOWN MENU ....................
                multiSelection(rightTable, "plot");
                break;

            case(SadConstants.MMEAN): //means
                parameterSelection(leftTable, product);
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

            case(SadConstants.MOTHR): // Create other formats
                diagramSelection(rightTable, line, product);

                //................ TIME UNITS:--Add DROP-DOWN MENU ....................
                leftTable.addRow(ec.crSpanColsRow("<br>Min depth for bottom values selection:",
                                   2,true,"",IHAlign.CENTER,"+0"));
                Select timeTypeSelect7 = new Select(sc.MINDEPPAR);
                timeTypeSelect7.addOption(new Option("Meters","0", false));
                timeTypeSelect7.addOption(new Option("Percentage", "1", false));

                leftTable.addRow(ec.cr2ColRow(
                timeTypeSelect7.toHTML() + ":", sc.MINDEPVAL,5,5,"10"));
                break;

            case(SadConstants.MTHRM): // thermo means
                parameterSelectionThermo(leftTable);
                diagramSelection(rightTable, line, product);
                multiSelection(rightTable, "table");

                //................TABLE FORMAT SELECTION--Add DROP-DOWN MENU ....................
                Select tableTypeSelectTh = new Select(sc.TABLETYPE);
                tableTypeSelectTh.addOption(new Option("Block Tables","0", false));
                tableTypeSelectTh.addOption(new Option("Column Tables", "1", false));

                //................TABLE TYPE SELECTION input box....................
                rightTable.addRow(ec.cr2ColRow(
                    chFSize("Table Selection:","-1"), tableTypeSelectTh));
                leftTable.addRow(ec.crSpanColsRow("<br>",2));
                //leftTable.addRow(ec.crSpanColsRow("<br><br><br><br>",2));

                //................input box....................
                leftTable.addRow(ec.crSpanColsRow("Limits for start, end, of the themocline<br>"+
                      "(in Degrees per 100 Meter)",3,true,"",IHAlign.CENTER,"+0"));
                leftTable.addRow(ec.cr2ColRow(
                    chFSize("Limits :","-1"),
                    new TextField(sc.LIMIT1,5,5,"6").toHTML() +
                    chFSize("to ","-1") +
                    new TextField(sc.LIMIT2,5,5,"3").toHTML()));
                leftTable.addRow(ec.crSpanColsRow("(typical values : west coast 10,5)"
                              ,3,true,"",IHAlign.CENTER,"-1"));
                leftTable.addRow(ec.crSpanColsRow("(typical values : east coast 6,3)"
                              ,3,true,"",IHAlign.CENTER,"-1"));
                break;
            default:
            //showError();
        } // switch

        //................COMBINE MAIN + RIGHT + LEFT TABLE..........
        DynamicTable mainTable = new DynamicTable(2);
        mainTable.setBorder(1).setFrame(ITableFrame.BOX);
        mainTable.addRow(ec.cr2ColRow(leftTable.toHTML(), rightTable.toHTML()));

        //................FORM................................
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST\" name=\"myForm", sc.MRN_APP, target);
        form.addItem (mainTable);
        form.addItem("<br>");
        form.addItem(new Submit("", "Go!").setCenter());
        form.addItem(new Hidden(sc.SCREEN, "product"));  // parms to next page
        form.addItem(new Hidden(sc.VERSION, "product")); // parms to next page
        form.addItem(new Hidden(sc.PRODUCT, productStr));// parms to next page
        form.addItem(new Hidden(sc.FILENAME, fileName)); // parms to next page
        form.addItem(new Hidden(sc.SURVEYID, surveyId)); // parms to next page
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));// parms to next page
        form.addItem(new Hidden(sc.TABLE, String.valueOf(areaDifference)));
        // for the menu system
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        //................ADD JAVASCRIPT STUFF................
        if ( (product == sc.MINVN)|| (product == sc.MMEAN) ||
                (product == sc.MTHRM )) { // inventories, means, thermo
            JSLLib.formName = "myForm";
            this.addItem(JSLLib.BodyOnload());
            this.addItem(JSLLib.OpenScript());
            this.addItem(moreJavaScript());
            this.addItem(JSLLib.RtnDynamicSelect("myForm",sc.TABLESIZE,sc.BLOCKSIZE));
            //writeIt(plongitudewest.value,platitudenorth.value,ptablesize.value));
            this.addItem(JSLLib.CloseScript());
        } // if inventories, means, thermo

        // ................FIRST LINE (in blue)................
        this.addItem("<center><font color=blue size=+2><b>" +
            sc.MRNPRODLIST[product-1] + "</b> for </font><font color=green size=+1> "  +
            fileName + " - " + discipline + " : " + surveyId + "</font></center>");
        //mainTable.addRow(ec.crSpanColsRow("<br>",2));

        this.addItem("<br><center><font color=blue size=-1><u>Date Range:</u> " +
            startDateO + " to " + endDateO + ", <u>Number of Records:</u> " +
            numRecords + "<br> <u>Area Range</u>: " +
            latitudeMinO + " to " + latitudeMaxO + " S, " +
            longitudeMinO + " to " + longitudeMaxO + " E </font></center><br>");
        //mainTable.addRow(ec.crSpanColsRow("<br>",2));
        this.addItem(form.setCenter());

    } // ProductMRNGetParmsFrameActual


    // +-------------------------------------------------------+
    // | Physical/Nutrient parameter selection (one or more)   |
    // +-------------------------------------------------------+
    void parameterSelection(DynamicTable table, int product) {

        // Add checkbox to form
        table.addRow(ec.crSpanColsRow("Physical/Nutrient&nbsp;Parameter&nbsp;Selection",2));
        table.addRow(ec.crSpanColsRow("<font color=green size=2>" +
            "Select ONE or MORE of the following: </font>",2));
        String seqNum[] = {"1","2","3","4","5","6","7","8","9","10","11"};

        boolean tf = true;
        //boolean tf = false;

        String parms = "";

        int stop = sc.MRNPARMSLIST.length;
        if ( product == sc.MMEAN ) { stop -= 1; }  // no stations for means

        for (int i = 0; i < stop; i++) {
            parms += "\n" + chFSize(sc.MRNPARMSLIST[i],"-1") + " " +
                new CheckBox(sc.PARMS+seqNum[i], seqNum[i], tf).toHTML() +
                //new CheckBox(sc.PARMS+seqNum[i],
                //(product == sc.MMEAN?seqNum[i-1]:seqNum[i]),tf).toHTML() +
                "&nbsp;&nbsp;<br>";
            tf = false;
        } // for (int i = 0; i < 10; i++)

        table.addRow(ec.crSpanColsRow(parms,2,false,"",IHAlign.RIGHT));
    } // parameterSelection

     // +-------------------------------------------------------+
     // | Physical/Nutrient parameter selection (one or more)   |
     // +-------------------------------------------------------+
     void parameterSelectionThermo(DynamicTable table) {

            // Add checkbox to form
            table.addRow(ec.crSpanColsRow("Physical/Nutrient&nbsp;Parameter&nbsp;Selection",2));
            table.addRow(ec.crSpanColsRow("<font color=green size=2>" +
                "Select ONE or MORE of the following: </font>",2));
                        String seqNum[] = {"1","2","3"};
            boolean tf = true;
            String parms = "";
            for (int i = 0; i < sc.THRMPARMSLIST.length; i++) {
                //parms += "\n" + chFSize(sc.MRNPARMSLIST[i],"-1") + " " +
                parms += "\n" + chFSize(sc.THRMPARMSLIST[i],"-1") + " " +
                    new CheckBox(sc.PARMS+seqNum[i], seqNum[i], tf).toHTML() +
                    "&nbsp;&nbsp;<br>";
                tf = false;
            } // for int i
            table.addRow(ec.crSpanColsRow(parms,2,false,"",IHAlign.RIGHT));
        } // parameterSelection

    // +-------------------------------------------------------+
    // | Physical/Nutrient parameter selection (one only )     |
    // +-------------------------------------------------------+
    void parameterSelectionOneOf(DynamicTable table) {

        // Add RadioButton to form
        table.addRow(ec.crSpanColsRow("Physical/Nutrient&nbsp;Parameter&nbsp;Selection",2));
        table.addRow(ec.crSpanColsRow("<font color=green size=2>" +
            "Select ONE of the following:<br>",2,true,"",IHAlign.RIGHT));
        String seqNum[] = {"1","2","3","4","5","6","7","8","9","10"};
        boolean tf = true;
        String parms = "";
        for (int i = 0; i < 10; i++) {
            parms += "\n" + chFSize(sc.MRNPARMSLIST[i],"-1") + " " +
                new Radio(sc.PARMS,seqNum[i],tf).toHTML() +
                "&nbsp;&nbsp;<br>";
            tf = false;
        } // for int i
        table.addRow(ec.crSpanColsRow(parms,2,false,"",IHAlign.RIGHT));
    } // parameterSelectionOneOf


    // +------------------------------------------------------------+
    // | METHOD for Physical/Nutrient parameter selection (x and y) |
    // +------------------------------------------------------------+
    void parameterSelectionXY(DynamicTable table) {

        // Add RadioButton to form
        // I'm using a table to put the radio buttons in to get the X and Y
        // above the radio buttons
        table.addRow(ec.crSpanColsRow("Weather&nbsp;Parameter&nbsp;Selection",3));
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
            row.addCell(new TableDataCell(chFSize(sc.MRNPARMSLIST[i],"-1"))
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
        if ( (product == sc.MINVN) || (product == sc.MMEAN) ||
             (product == sc.MTHRM )) { // inventories, means, thermo

            //................DIAGRAM:................
            String latNJavaScript = "\" onChange=\"writeIt(" + sc.LONGITUDEWEST +
                ".value," + sc.LATITUDENORTH + ".value,ptable.value);\"\n";
            String latSJavaScript = "\" onFocus=\"this.blur()";
            String lonWJavaScript = "\" onChange=\"writeIt(" + sc.LONGITUDEWEST +
                ".value," + sc.LATITUDENORTH + ".value,ptable.value);\"\n";
            String lonEJavaScript = "\" onFocus=\"this.blur()";

            //if (product == sc.MTSPL) {
            //   latitudeMax    = latitudeMin + 1;
            //   longitudeMax   = longitudeMin + 1;
            //   areaDifference = 1.0f;
            //} // if (product == sc.MTSPL)

            table.addRow(ec.latlonRangeInput(
                "Select Top Left Corner",
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
            table.addRow(ec.crSpanColsRow("<br>",2));

            //if (product == sc.MTSPL) {
            //  //TABLE SIZE input box
            //  table.addRow(ec.cr2ColRow(chFSize("Table Size:<br><i>(degrees square)</i>","-1"),
            //      sc.TABLESIZE + "\" onFocus=\"this.blur()", 10, 10, "1"));
            //} else {
                //TABLE SIZE input box
                table.addRow(ec.cr2ColRow(
                    chFSize("Table Size:<br><i>(degrees square)</i>","-1"),
                    tableSelect.toHTML()));

                //BLOCK SIZE Size input box
                table.addRow(ec.cr2ColRow(
                    chFSize("Block Size: <br><i>(minutes square)</i>","-1"),
                    blockSelect.toHTML()));
                table.addRow(ec.crSpanColsRow("<br>",2));
                if (dbg) System.out.println("blockSelect = " + blockSelect + "<br>");
            //} // if (product == sc.MTSPL)

        } else if ((product == sc.MOTHR) || (product == sc.MTSPL)) {
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

       } //if (MOTHR) || (MTSPL))
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

        if ((product == 1) || (product == 2)) { // inventories, means
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

} // class



