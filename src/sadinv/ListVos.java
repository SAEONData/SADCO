package sadinv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import oracle.html.*;

/**
 * select count(*) from vos_main where
 * latitude between 34 and 34.99999 and
 * longitude between 23 and 23.99999 and
 * date_time between '1964-01-01' and '1964-12-31';
 * This class handles the initial VOS extraction
 *
 * SadInv
 * pscreen = DATATYPEVOS
 * pdatatype = <datatype code>
 *
 * @author 20100414 - SIT Group.
 * @version
 * 20100414 - Ursula von St Ange - create program                       <br>
 * 20100507 - Ursula von St Ange - change for postgres             ub01 <br>
 */

public class ListVos extends CompoundItem {

    //boolean dbg = true;
    boolean dbg = false;
    //boolean dbg2 = true;
    boolean dbg2 = false;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    static private Connection conn = null;
    static private java.sql.Statement stmt = null;
    static private ResultSet rset = null;

    boolean error = false;
    boolean second = false;
    float value = 0;
    String method = "";

    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public ListVos (String args[]) {

        // prepare the sql string
        String sql = "";

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String datatypeCode = ec.getArgument(args,sc.CODE);
        String sessionCode  = ec.getArgument(args,sc.PSC);              //ub03
        String latitudeNorth = ec.getArgument(args,sc.LATITUDENORTH);
        String latitudeSouth = ec.getArgument(args,sc.LATITUDESOUTH);
        String longitudeWest = ec.getArgument(args,sc.LONGITUDEWEST);
        String longitudeEast = ec.getArgument(args,sc.LONGITUDEEAST);
        String hemNorth = ec.getArgument(args,sc.HEMNORTH);
        String hemSouth = ec.getArgument(args,sc.HEMSOUTH);
        String hemWest = ec.getArgument(args,sc.HEMWEST);
        String hemEast = ec.getArgument(args,sc.HEMEAST);
        String startDate = ec.getArgument(args,sc.STARTDATE);
        String endDate = ec.getArgument(args,sc.ENDDATE);
        //if ("".equals(datatypeCode)) datatypeCode = "7";
        if (dbg2) {
            datatypeCode = "7" ;
            sessionCode  = "1234";              //ub03
            //latitudeNorth = "";
            //latitudeSouth = "3";
            //longitudeWest = "2";
            //longitudeEast = "3";
            //hemNorth = "S";
            //hemSouth = "S";
            //hemWest = "E";
            //hemEast = "E";
            //startDate = "1980";
            //endDate = "1990";
        } // if (dbg2)

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass, sessionCode);

        // +------------------------------------------------------------+
        // | Get the datatype details                                  |
        // +------------------------------------------------------------+
        String  datatypeName    = "";
        String  datatypeDesc    = "";
        try {

            sql =
                " select name, description "+
                " from survey_type "+
                " where code = "+datatypeCode;

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (dbg) System.out.println("<br>" + thisClass + " **sql "+sql);

            while (rset.next()) {
                datatypeName  = sc.checkNull(rset.getString(1));
                datatypeDesc  = sc.checkNull(rset.getString(2));
                if (dbg) System.out.println("<br>" + thisClass + " **datatypeName "+datatypeName);

            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (SQLException e) {
            System.err.println("<br> "+e.getMessage());
                e.printStackTrace();
        } // try-catch

        // +------------------------------------------------------------+
        // | Is this the second call to ListVos - check for valid inputs|
        // +------------------------------------------------------------+
        if (!"".equals(latitudeNorth) || !"".equals(latitudeSouth) ||
            !"".equals(longitudeWest) || !"".equals(longitudeEast) ||
            !"".equals(startDate) || !"".equals(endDate)) {
            second = true;
            error = checkLatLon(latitudeNorth);
            if (!error) error = checkLatLon(latitudeSouth);
            if (!error) error = checkLatLon(longitudeWest);
            if (!error) error = checkLatLon(longitudeEast);
            if (!error) error = checkDate(startDate);
            if (!error) error = checkDate(endDate);
            if (dbg) System.out.println("<br>" + thisClass + " error = "+error);
        } // if (!"".equals(latitudeNorth) || !"".equals(latitudeSouth) ||
        if (dbg) System.out.println("<br>" + thisClass + " second = "+second);


        // +------------------------------------------------------------+
        // | first time round, or input errors                          |
        // +------------------------------------------------------------+
        if (!second || (second && error)) {
            // +------------------------------------------------------------+
            // | Create the main listing table                              |
            // +------------------------------------------------------------+
            DynamicTable header_tab = new DynamicTable(1);
            header_tab
                .setBorder(1)
                .setCellSpacing(1)
                .setBackgroundColor(new Color("FFFF99"))
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL)
            ;

            // +------------------------------------------------------------+
            // | Create the headings                                        |
            // +------------------------------------------------------------+
            TableRow rows = new TableRow();
            rows.addCell(new TableHeaderCell(new SimpleItem(
                datatypeName + "<br>" +datatypeDesc).setFontSize(5)));
            header_tab.addRow(rows);

            // +------------------------------------------------------------+
            // | Build a new row and add to the list table                  |
            // +------------------------------------------------------------+
            rows = new TableRow();
            rows.addCell(new TableDataCell(
                "Date range : from 1662 till present<br>" +
                "Number of records : > 7.25 million<br>" +
                "Area : 10°N - 80°S, 30°W - 70°E"));
            header_tab.addRow(rows);
            this.addItem(header_tab.setCenter());

            // +------------------------------------------------------------+
            // | Info Heading                                               |
            // +------------------------------------------------------------+
            this.addItem(new SimpleItem("<br>"));

            if (!"".equals(sessionCode)) {

                // +------------------------------------------------------------+
                // | Create the table to display allowable extractions          |
                // +------------------------------------------------------------+
                DynamicTable infoTab  = new DynamicTable(10);
                infoTab
                    .setBorder(3)
                    .setBackgroundColor(new Color("FFFF99"))
                    .setFrame(ITableFrame.BOX)
                    .setRules(ITableRules.ALL);
                infoTab.addRow(ec.cr1ColRow(
                    "The following extractions are allowed on-line:<br>" +
                    " (total no of degree blocks) * (no of years) <= 1000, e.g.<br>" +
                    " 10 * 10 degrees and 10 years <b>or</b> " +
                    " 5 x 5 degrees and 40 years <b>or</b> " +
                    " 4 x 4 degrees and 62 years<br>" +
                    "For any other extraction size an off-line request will " +
                    "be submitted on your behalf"));
                this.addItem(infoTab.setCenter());
                this.addItem(new SimpleItem("<br>"));

                // +------------------------------------------------------------+
                // | Enter a input parameters                                   |
                // +------------------------------------------------------------+
                DynamicTable table = new DynamicTable(1);
                table.setBorder(0).setCellPadding(0).setCellSpacing(0);
                table.setFrame(ITableFrame.BOX).setRules(ITableRules.NONE);
                if (error) {
                    table.addRow(ec.crSpanColsRow(
                        "erroneous input - please correct and re-submit<br><br>",
                        2,true,"red",IHAlign.CENTER));
                } // if (error)
                table.addRow(ec.latlonRangeInput("", 6,
                    latitudeNorth, latitudeSouth, longitudeWest, longitudeEast,
                    hemNorth, hemSouth, hemWest, hemEast, "N", "S", "W", "E",
                    "", "", "", "", ""));
                table.addRow(ec.cr1ColRow("<br>"));
                dateRangeInput(table, 12, startDate, endDate, "");

                Form frm = new Form("GET", sc.APP);
                frm
                    .addItem(table.setCenter())
                    .addItem(new Hidden(sc.SCREEN, sc.DATATYPEVOS))
                    .addItem(new Hidden(sc.CODE, datatypeCode))
                    .addItem(new Hidden(sc.PSC, sessionCode))                   //ub03
                    .addItem("<br>")
                    .addItem(new Submit("Submit", "Go!").setCenter())
                    ;

                this.addItem(frm);

            } // if (!"".equals(sessionCode))
        } else {
            // +------------------------------------------------------------+
            // | second time, no errors                                     |
            // +------------------------------------------------------------+
            // online or offline?
            float latRange = Math.abs(Float.parseFloat(latitudeSouth) -
                Float.parseFloat(latitudeNorth));
            if (latRange == 0) latRange = 1;
            float lonRange = Math.abs(Float.parseFloat(longitudeEast) -
                Float.parseFloat(longitudeWest));
            if (lonRange == 0) lonRange = 1;
            int dateRange = Integer.parseInt(endDate) -
                Integer.parseInt(startDate) + 1;
            if (dateRange == 0) dateRange = 1;
            String method = sc.OFFLINE;
            float areaDate = latRange * lonRange * dateRange;
            if (areaDate <= sc.MAX_VOS) method = sc.ONLINE;
            if (dbg) System.out.println("<br>" + thisClass + " method = "+method +
                " areaDate = "+areaDate);

            // call extractRequest
            String args2[] = new String[args.length+5];
            args2[0] = sc.SCREEN + "=" + sc.EXTRACT;
            args2[1] = sc.DATATYPE + "=" + datatypeCode;
            args2[2] = sc.DATANAME + "=" + datatypeName;
            args2[3] = sc.SURVEYID + "=VOS";
            args2[4] = sc.ACTION + "=" + method;
            args = changeArgument(args, sc.STARTDATE, "-01-01");
            args = changeArgument(args, sc.ENDDATE, "-12-31");
            for (int i = 0; i < args.length; i++) args2[i+5] = args[i];
            if (dbg) for (int i = 0; i < args2.length; i++)
                System.out.println("<br>" + thisClass + " args2["+i+"] = "+args2[i]);

            //ExtractRequest eRequest = new ExtractRequest(args2);
            this.addItem(new ExtractRequest(args2));

        } // if (!second || (second && error))

        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            } // if (stmt != null)
            if (rset != null) {
                rset.close();
                rset = null;
            } // if (rset != null)
            if (conn != null) {
                conn.close();
                conn = null;
            } // if (conn != null)
        }  catch (SQLException e) { }

    } // ListVos


    /**
     * Format text strings
     */
    private static Container formatStr(String oldStr) {
        Container newStrc = new Container();
        newStrc
            .addItem(new SimpleItem(oldStr).setFontSize(1))
            ;
        return newStrc;

    } // formatStr


    /**
     * check if valid latitude / longitude
     * return true / false
     */
    boolean checkLatLon(String latLon) {
        boolean error = false;
        if (dbg) System.out.println("<br>" + thisClass +
            " checkLatLon: latLon = " + latLon);
        if ("".equals(latLon)){
            error = true;
        } else if (!isNumeric(latLon)) {
            error = true;
//        } else {
//            float value = Float.parseFloat(latLon);
//            if (limit < 0) {
//                if (value < limit) error = true;
//                if (dbg) System.out.println("<br>" + thisClass +
//                    " checkLatLon: limit < 0: error = " + error);
//            } else {
//                if (value > limit) error = true;
//                if (dbg) System.out.println("<br>" + thisClass +
//                    " checkLatLon: limit > 0: error = " + error);
//            } // if (limit < 0)
        } // if ("".equals(latLon))
        return error;
    } // checkLatLon


    /**
     * check if valid startdate / endate
     * return true / false
     */
    boolean checkDate(String datum) {
        boolean error = false;
        if (dbg) System.out.println("<br>" + thisClass +
            " checkDate: datum = " + datum);
        if ("".equals(datum)){
            error = true;
        } else if (!isNumeric(datum)) {
            error = true;
        } else {
            try {
                Timestamp ts = Timestamp.valueOf(datum + "-01-01 00:00:00.0");
            } catch (Exception e){
                error = true;
            } // try-catch
        } // if ("".equals(datum))
        return error;
    } // checkDate


    /**
     * Check whether the value is numeric
     */
    boolean isNumeric(String input)  {
        try {
            Float.parseFloat(input);
            return true;
        } catch(Exception e) {
          return false;
        } // try-catch
    } // isNumeric


    /**
     * Return a row for lat/lon range input
     * @param  prompt      the prompt for the table
     * @param  length      the length for the text input boxes
     * @param  latNdefault the default value for latitude North
     * @param  latSdefault the default value for latitude South
     * @param  lonWdefault the default value for longtidue West
     * @param  lonEdefault the default value for longtidue East
     * @param  latNPrompt  the prompt for latitude North
     * @param  latSPrompt  the prompt for latitude South
     * @param  lonWPrompt  the prompt for longtidue West
     * @param  lonEPrompt  the prompt for longtidue East
     * @param  latNNameAdd any additional javascript stuff for latitude North
     * @param  latSNameAdd any additional javascript stuff for latitude South
     * @param  lonWNameAdd any additional javascript stuff for longtidue West
     * @param  lonENameAdd any additional javascript stuff for longtidue East
     * @param  compulsory  a "*" if the field is a compulsory field
     * @return the table row
     */
/*
    public TableRow latlonRangeInput(
            String prompt,
            int length,
            String latNdefault,
            String latSdefault,
            String lonWdefault,
            String lonEdefault,
            String hemNdefault,
            String hemSdefault,
            String hemWdefault,
            String hemEdefault,
            String latNPrompt,
            String latSPrompt,
            String lonWPrompt,
            String lonEPrompt,
            String latNNameAdd,
            String latSNameAdd,
            String lonWNameAdd,
            String lonENameAdd,
            String compulsory)   {
        // constants for area
        String latitudeNorthName = "latitudenorth" + latNNameAdd;
        String latitudeSouthName = "latitudesouth" + latSNameAdd;
        String longitudeWestName = "longitudewest" + lonWNameAdd;
        String longitudeEastName = "longitudeeast" + lonENameAdd;
        String areaPrompt = "Enter latitude (N/S) and longitude (E/W) " +
            "limits in decimal degrees";

        if (!compulsory.equals("*")) compulsory = "&nbsp;";

        // geographical area
        DynamicTable subTable = new DynamicTable(8);
        subTable.setBorder(0).setCellPadding(0).setCellSpacing(0);
        subTable.setFrame(ITableFrame.VOLD);
        subTable.setRules(ITableRules.NONE);

        // longitude input boxes
        TableRow row = new TableRow();
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(compulsory + new TextField(
            "p" + longitudeWestName,length,length,lonWdefault).toHTML() +
            createSelect("phemwest","E","W",hemWdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(compulsory + new TextField(
            "p" + longitudeEastName,length,length,lonEdefault).toHTML() +
            createSelect("phemeast","E","W",hemEdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);

        // latitude North input box
        row = new TableRow();
        row.addCell(new TableDataCell(compulsory + new TextField(
            "p" + latitudeNorthName,length,length,latNdefault).toHTML() +
            createSelect("phemnorth","S","N",hemNdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----------+----------<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("----")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----------+----------<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);

        // latitude South input box
        row = new TableRow();
        row.addCell(new TableDataCell(compulsory + new TextField(
            "p" + latitudeSouthName,length,length,latSdefault).toHTML() +
            createSelect("phemsouth","S","N",hemSdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----------+----------<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("----")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----------+----------<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);


        // main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setBorder(0).setCellPadding(0).setCellSpacing(0);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);

        //prompt
        String cellContents = prompt;
        if ((cellContents.equals("p")) || (cellContents.equals(""))) {
            cellContents = areaPrompt;
        } // if ((cellContents.

        row = new TableRow();
        row.addCell(new TableDataCell(
            "<b>" + cellContents + "</b><br><br>").setHAlign(IHAlign.CENTER));
        mainTable.addRow(row);

        row = new TableRow();
        row.addCell(new TableDataCell(subTable.toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        mainTable.addRow(row);

        cellContents = mainTable.toHTML();

        row = new TableRow();
        row.addCell(new TableDataCell(cellContents)
            .setColSpan(2).setHAlign(IHAlign.CENTER));
        return row;

    }  // latlonRangeInput

*/
    /**
     * return a table for the daterange or datetimerange input
     * @param table  the html table to which the rows should be added
     * @param length  the lenght of the input fields
     * @param dateStartDefault the default start date
     * @param dateEndDefault the default end date
     * @param  compulsory  a "*" if the field is a compulsory field
     * @return the html table with the rows added
     */
    public DynamicTable dateRangeInput(
            DynamicTable table,
            int length,
            String dateStartDefault,
            String dateEndDefault,
            String compulsory)   {

        if (dbg) System.out.println("<br>" + thisClass +
            " dateStartDefault = " + dateStartDefault);
        if (dbg) System.out.println("<br>" + thisClass +
            " dateEndDefault = " + dateEndDefault);

        // constants for date/time
        String dateStartPrompt = "Start Year (yyyy)";
        String dateEndPrompt   = "End Year (yyyy)";

        // start date
        TableRow row = new TableRow();
        row.addCell(new TableDataCell
            ("<b>" + dateStartPrompt + ":</b>").setHAlign(IHAlign.RIGHT));
        row.addCell(new TableDataCell(
            new TextField(sc.STARTDATE, length, length,
                dateStartDefault) + compulsory));
        table.addRow(row);
        // end date
        row = new TableRow();
        row.addCell(new TableDataCell
            ("<b>" + dateEndPrompt + ":</b>").setHAlign(IHAlign.RIGHT));
        row.addCell(new TableDataCell(
            new TextField(sc.ENDDATE, length, length,
                dateEndDefault) + compulsory));
        table.addRow(row);
        return table;
    } // dateRangeInput


    /**
     * Add options FOR N/S and E/W selection
     * @param   option1   the first option
     * @param   option2   the second option
     * @param   name      the variable name
     */
/*
    Select createSelect(String name, String option1, String option2, String defOpt) {
        Select select = new Select(name);
        select.addOption(new Option(option1, option1, (defOpt.equals(option1)?true:false)));
        select.addOption(new Option(option2, option2, (defOpt.equals(option2)?true:false)));
        return select;
    } // createSelect
*/


    /**
     * change the value of a URL parameter
     * @param  args  the string array of arguments from the form or URL
     * @param  name  the argument name
     * @return the argument value or empty string if the parameter does not exist
     */
    public String[] changeArgument(String args[], String name, String add) {
        String prefix = name + "=";
        for (int i = 0; i < args.length; i++)   {
            if (args[i].startsWith(prefix)) {
                args[i] += add;
            } // if (args[i].startsWith(prefix))
        } // for (int i = 0; i < args.length; i++)
        return args;
    } // changeArgument


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("ListVos local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new ListVos(args));
            hp.printHeader();
            hp.print();
            //common2.DbAccessC.close();
        } catch(Exception e) {
            e.printStackTrace();
            // make sure everything is closed and null
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                } // if (stmt != null)
                if (rset != null) {
                    rset.close();
                    rset = null;
                } // if (rset != null)
                if (conn != null) {
                    conn.close();
                    conn = null;
                } // if (conn != null)
            }  catch (SQLException se){}
        } // try-catch

    } // main

} // class ListVos
