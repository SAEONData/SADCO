package sadinv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.html.*;


/**
 * This class handles the search on scientists - displays the list of
 * surveys for the scientist
 *
 * SadInv
 * pscreen = scientists2
 * pinstitute = <scientist>
 * psurveyid = <survey id>
 *
 * @author 20090112 - SIT Group.
 * @version
 * 20090112 - Ursula von St Ange - create program                       <br>
 * 20090907 - Ursula von St Ange - add code to correct year tables(ub01)<br>
 * 20090602 - Ursula von St Ange - chnge cruise name to survey name ub02<br>
 * 20091203 - Ursula von St Ange - add sessionCode, 'data available' and
 *                                 altern rows different colours (ub03) <br>
 * 20100201 - Ursula von St Ange - if dataRecorded = N,
 *                                 make available = N (ub04)            <br>
 * 20100507 - Ursula von St Ange - change for postgres             ub05 <br>
 * 20111103 - Ursula von St Ange - save survey_id list on db       ub06 <br>
 */

public class ListScientist extends CompoundItem {


    boolean dbg = false;
    //boolean dbg = true;

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    static private Connection conn = null;
    static private java.sql.Statement stmt = null;
    static private ResultSet rset = null;

    static private int MAX_RECS = 20;

    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public ListScientist(String args[]) {

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String scientistCode = ec.getArgument(args,sc.CODE);
        String startDate = ec.getArgument(args,sc.DATESTART);
        String surveyId = ec.getArgument(args,sc.SURVEYID);
        if ("".equals(surveyId)) surveyId = " ";
        String sessionCode  = ec.getArgument(args,sc.PSC);              //ub03

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass, sessionCode);
        /*
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName(sc.DRIVER);                                   //ub05
            //conn = DriverManager.getConnection
            //    ("jdbc:oracle:thin:" + sc.USER + "/" + sc.PWD +
            //     sc.CONNECTION);          // steamer
            //     //"@morph.csir.co.za:1522:etek8");
            conn = DriverManager.getConnection(                         //ub05
                sc.CONNECTION, sc.USER,  sc.PWD);                       //ub05
            conn.setAutoCommit(false);
        } catch (Exception e) {
            return;
        } // try-catch
        */


        // Create a statement
        String sql = "";
        String sql2 = "";

        int yearArray[]    = new int[200];
        boolean rowArray[] = new boolean[20] ;

        // +-------------------------------------------------------------------+
        // | Get a listing of the scientist if there is a scientist name input |
        // +-------------------------------------------------------------------+
        if (!"".equals(scientistCode)) {

            // +------------------------------------------------------------+
            // | Prepare for PL/SQL values                                  |
            // +------------------------------------------------------------+
            String  dateStartRange= "";
            String  dateEndRange  = "";
            String  surname       = "";
            String  fName         = "";
            String  title         = "";
            String  institute     = "";
            int     totalRecords  = 0;
            int     numRecs       = 0;

            // find min and max date values, and count number of records for
            // relevant scientist
            try {

                sql =
                    " select to_char(min(date_start),'YYYY-MM-DD'),"+
                    " to_char(max(date_end),'YYYY-MM-DD'), count(*) "+       //ub01
                    " from inventory "+
                    " where sci_code_1 = "+scientistCode;

                if (dbg) System.out.println("<br> sql "+sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                while (rset.next()) {

                    dateStartRange = checkNull(rset.getString(1));
                    dateEndRange   = checkNull(rset.getString(2));
                    totalRecords = (int)rset.getFloat(3);

                    if (dbg) System.out.println("<br>dateEndRange "+dateEndRange);
                    if (dbg) System.out.println("<br>dateStartRange "+dateStartRange);
                    if (dbg) System.out.println("<br> **totalRecords "+totalRecords);

                } //while (rset.next()) {

                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

            } catch (SQLException e) {}

            // get the scientist details
            try {

                sql =
                    " select surname, f_name, title, name "+
                    " from scientists, institutes "+
                    " where scientists.code = "+scientistCode+
                    " and institutes.code = scientists.instit_code";

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                while (rset.next()) {
                    surname   = checkNull(rset.getString(1));
                    fName     = checkNull(rset.getString(2));
                    title     = checkNull(rset.getString(3));
                    institute = checkNull(rset.getString(4));
                } //while (rset.next()) {

                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

            } catch (SQLException e) {}


            // +------------------------------------------------------------+
            // | Create the main listing table                              |
            // +------------------------------------------------------------+
            DynamicTable header_tab = new DynamicTable(1);
            header_tab
                .setBorder(1)
                .setCellSpacing(1)
                .setBackgroundColor(new Color("FFFF99"))
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL);

            // +------------------------------------------------------------+
            // | Create the headings                                        |
            // +------------------------------------------------------------+
            TableRow rows = new TableRow();
            rows.addCell(new TableHeaderCell(new SimpleItem(
                title + " " + fName + " " + surname + "<br>" +
                institute).setFontSize(5)));
            header_tab.addRow(rows);

            // +------------------------------------------------------------+
            // | Build a new row and add to the list table                  |
            // +------------------------------------------------------------+
            rows = new TableRow();
            rows.addCell(new TableDataCell(
                    "Date range:" + dateStartRange + " to " + dateEndRange +
                    "<br>Number of surveys: " + totalRecords));
            header_tab.addRow(rows);

            this.addItem(header_tab .setCenter());


            // if no start date was specified
            if ("".equals(startDate)) {
                // +------------------------------------------------------------+
                // | When there are many surveys, construct a table of years    |
                // | so that the user can choose a starting year.               |
                // +------------------------------------------------------------+
                if (totalRecords > MAX_RECS) {
                    // +------------------------------------------------------------+
                    // | Create starting and ending years in integer format         |
                    // +------------------------------------------------------------+
                    int startYear = Integer.parseInt(dateStartRange.substring(0,4));
                    int endYear   = Integer.parseInt(dateEndRange  .substring(0,4));

                    // +------------------------------------------------------------+
                    // | The table have rows of decades. The first decade needs to  |
                    // | be calculated                                              |
                    // +------------------------------------------------------------+
                    int startdecade = (startYear / 10 * 10);

                    // +------------------------------------------------------------+
                    // | Calculate the numbe of decades covered                     |
                    // +------------------------------------------------------------+
                    int decades = (endYear / 10 - startdecade / 10);

                    // +------------------------------------------------------------+
                    // | Report Heading                                             |
                    // +------------------------------------------------------------+
                    this.addItem(new SimpleItem(
                        "Click on a year to start listing: ").setCenter() .setHeading(3));

                    // +------------------------------------------------------------+
                    // | Create the table to display the years                      |
                    // +------------------------------------------------------------+
                    DynamicTable yearTab  = new DynamicTable(10);
                    yearTab
                        .setBorder(3)
                        .setBackgroundColor(new Color("FFFF99"))
                        .setFrame(ITableFrame.BOX)
                        .setRules(ITableRules.ALL);
                    rows = new TableRow();

                    // get all the start dates of all the surveys for the scientist
                    // and create the array of all the years
                    int year1 = 0;                                          //ub01
                    int year2 = 0;                                          //ub01

                    try {

                        sql = "select distinct(to_char(date_start,'YYYY')), " +//ub01
                              "to_char(date_end,'yyyy') from " +            //ub01
                              "inventory where sci_code_1 = "+scientistCode;
                        if (dbg) System.out.println("<br>sql = " + sql);

                        stmt = conn.createStatement();
                        rset = stmt.executeQuery(sql);

                        String date1 = "";                                  //ub01
                        String date2 = "";                                  //ub01

                        if (dbg) System.out.println("<br> try body ");
                        while (rset.next()) {

                            if (dbg) System.out.println("<br> while ");
                            date1 = checkNull(rset.getString(1));           //ub01
                            date2 = checkNull(rset.getString(2));           //ub01
                            if (dbg) System.out.println("<br><br>dates *"+  //ub01
                                date1+"*"+date2+"*");                       //ub01

                            if (!"".equals(date1) && !"".equals(date2)) {   //ub01
                                year1 = Integer.parseInt(date1);            //ub01
                                year2 = Integer.parseInt(date2);            //ub01
                            } // if (!"".equals(date1) && !"".equals(date2))//ub01
                            if (dbg) System.out.println("<br>years "+       //ub01
                                year1+" "+year2);                           //ub01

                            for (int year = year1; year <= year2; year++) { //ub01

                                yearArray[year - 1850]   = year;
                                if (dbg) System.out.println(
                                    "<br>yearArray[year - 1850] = "+
                                    yearArray[year - 1850]);

                                rowArray[(year - 1850) / 10] = true;
                                if (dbg) System.out.println(
                                    "<br>rowArray[year - 1850/10] = "+
                                    rowArray[(year - 1850) / 10]);

                            } // for (int year = year1; i <= year2; i++)    //ub01

                        } //while (rset.next()) {

                        rset.close();
                        stmt.close();
                        rset = null;
                        stmt = null;

                    } catch (Exception e) {
                        System.err.println("<br>sql = " +sql+
                            "\n years = "+year1+" "+year2+                  //ub01
                            "\n message = "+e.getMessage());
                        e.printStackTrace();
                    } // try-catch

                    // create the html table
                    for (int j = 0; j < rowArray.length; j++) {

                        rows = new TableRow();

                        if (rowArray[j]) {

                            int startIndex = j * 10;
                            for (int i2 = startIndex; i2 < startIndex + 10; i2++) {

                                if (yearArray[i2] != 0) {

                                    String link = new String(
                                        sc.APP + "?" +
                                        sc.SCREEN + "=" + sc.SCIENTISTS2 + "&" +
                                        sc.CODE + "=" + scientistCode + "&" +
                                        sc.PSC + "=" + sessionCode + "&" +  //ub03
                                        sc.DATESTART + "=" + yearArray[i2] + "-01-01");
                                    rows.addCell(new TableDataCell(new
                                    Link(link,new SimpleItem(yearArray[i2]))) );

                                } else {
                                    int actualYear = 1850 + i2;
                                    rows.addCell(new TableDataCell(
                                        new SimpleItem(actualYear)));
                                } // if (yearArray[i2] != 0) {
                            } // for (int i2= startIndex; i2 < startIndex + 10; i2++) {

                            yearTab.addRow(rows);

                        } // if if (yearArray[i2] != 0)
                        if (dbg) System.out.println("<br> after if (rowArray[j]) "+j);

                    } //for (int j = 0; j < 10; j++) {
                    if (dbg) System.out.println("<br> after for loop ");

                    this.addItem(yearTab .setCenter());

                } else {
                    // if the number of records are smaller than MAX_RECS, create
                    // the list of surveys.
                    startDate = dateStartRange;

                } // if (totalRecords > MAX_RECS)

            } // if ("".equals(startDate))


            // create the list of surveys
            if (!"".equals(startDate)) {
                // +------------------------------------------------------------+
                // | Prepare for PL/SQL values                                  |
                // +------------------------------------------------------------+

                if (dbg) System.out.println("<br> 1 startDate "+startDate);
                if (dbg) System.out.println("<br> Prepare for PL/SQL values ");

                // +------------------------------------------------------------+
                // | Build the empty arrays for holding survey  data            |
                // +------------------------------------------------------------+
                String[] surveyIds       = new String[MAX_RECS];
                String[] projectNames    = new String[MAX_RECS];
                String[] cruiseNames     = new String[MAX_RECS];
                String[] institutes      = new String[MAX_RECS];
                String[] datesStart      = new String[MAX_RECS];
                String[] datesEnd        = new String[MAX_RECS];
                String[] surveyTypeNames = new String[MAX_RECS];
                String[] dataRecorded    = new String[MAX_RECS];
                String[] available       = new String[MAX_RECS];            //ub03
                StringBuffer allSurveys  = new StringBuffer("");            //ub06
                String   tmpSurveyId     = "";                              //ub06
                String   tmpAvailable    = "";                              //ub06
                String   tmpDataRecorded = "";                              //ub06

                // +------------------------------------------------------------+
                // | Retrieve the survey information from the packaged PL/SQL   |
                // | using procedure display_survey                             |
                // +------------------------------------------------------------+
                if (dbg) System.out.println("<br> list startDate "+startDate);

                try {

                    sql =
                        " select survey_id, project_name, cruise_name, "+
                        " plan.name, to_char(date_start,'YYYY-MM-DD'), "+
                        " to_char(date_end,'YYYY-MM-DD'), "+
                        " data_recorded, st.name, data_available " + //ub03
                        " from inventory inv, planam plan, survey_type st "+
                        " where inv.sci_code_1 = "+ scientistCode+
                        " and (inv.date_start >= to_timestamp('"+startDate+"','YYYY-MM-DD')"+ //ub01
                        " or (inv.date_start <= to_timestamp('"+startDate+"','YYYY-MM-DD') "+ //ub01
                        " and to_timestamp('"+startDate+"','YYYY-MM-DD') <= inv.date_end)) "+ //ub01
                        " and plan.code = inv.planam_code "+
                        " and inv.survey_type_code = st.code "+
                        " order by inv.date_start ";

                    if (dbg) System.out.println("<br> sql "+sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    int i = 0;
                    boolean found = false;
                    if (" ".equals(surveyId)) found = true;
                    //while (rset.next() && (i < MAX_RECS)) {                 //ub06
                    while (rset.next()) {                                     //ub06
                        if (i < MAX_RECS) {                                   //ub06
                            surveyIds[i]     = checkNull(rset.getString(1));
                            if (surveyIds[i].equals(surveyId)) found = true;
                            if (found) {
                                projectNames[i] = checkNull(rset.getString(2));
                                cruiseNames[i]  = checkNull(rset.getString(3));
                                institutes[i]   = checkNull(rset.getString(4));
                                datesStart[i]   = checkNull(rset.getString(5));
                                datesEnd[i]     = checkNull(rset.getString(6));
                                dataRecorded[i] = checkNull(rset.getString(7)).toUpperCase();
                                surveyTypeNames[i]=checkNull(rset.getString(8));
                                available[i]    = checkNull(rset.getString(9)).toUpperCase();//ub03
                                if ("N".equals(dataRecorded[i])) available[i] = "N";     //ub04
                                allSurveys.append(surveyIds[i] + " " + available[i] + ",");//ub06

                                if (dbg) System.out.println("<br> surveyIds["+i+"]"+surveyIds[i]);
                                i++;
                            } // if (found)
                        } else {                                                          //ub06
                            tmpSurveyId     = checkNull(rset.getString(1));               //ub06
                            tmpDataRecorded = checkNull(rset.getString(7)).toUpperCase();//ub06
                            tmpAvailable    = checkNull(rset.getString(9)).toUpperCase();//ub06
                            if ("N".equals(tmpDataRecorded)) tmpAvailable = "N";          //ub06
                            allSurveys.append(tmpSurveyId + " " + tmpAvailable + ",");    //ub06
                        } // if (i < MAX_RECS)                                            //ub06
                    } //while (rset.next()) {

                    numRecs = i;
                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (SQLException e) {
                    //bd.addItem(new SimpleItem("Could not process display_survey: " + e.getSqlerrm() ));
                    //hp.print();
                    //return;
                    if (dbg) System.out.println("<br>**error");
                }  //  catch (SQLException e)

                // +------------------------------------------------------------+
                // | Save survey list on the db                                 |
                // +------------------------------------------------------------+
                if (dbg) System.out.println("allSurveysLenght = " +             //ub06
                    allSurveys.toString().length());                            //ub06
                sc.storeSurveyList(conn, sessionCode, allSurveys.toString());   //ub06

                // +------------------------------------------------------------+
                // | Prompt user to click on survey ID to select                |
                // +------------------------------------------------------------+
                this
                    //.addItem(SimpleItem.LineBreak)
                    .addItem(new SimpleItem("Click on a survey ID for more information."))
                    ;

                // +------------------------------------------------------------+
                // | Create the main listing table                              |
                // +------------------------------------------------------------+
                DynamicTable tab = new DynamicTable(7);
                tab
                    .setBorder(1)
                    .setCellSpacing(1)
                    .setBackgroundColor(new Color("FFFF99"))
                    .setFrame(ITableFrame.BOX)
                    .setRules(ITableRules.ALL)
                    ;

                // +------------------------------------------------------------+
                // | Create the headings                                        |
                // +------------------------------------------------------------+
                rows = new TableRow();
                rows
                    .addCell(new TableHeaderCell(formatStr("Survey ID")))
                    .addCell(new TableHeaderCell(formatStr("Project Name")))
                    .addCell(new TableHeaderCell(formatStr("Survey/Station Name")))// ub02
                    .addCell(new TableHeaderCell(formatStr("Platform Name")))
                    .addCell(new TableHeaderCell(formatStr("Start")))
                    .addCell(new TableHeaderCell(formatStr("End")))
                    .addCell(new TableHeaderCell(formatStr("Survey Type")))
                    .addCell(new TableHeaderCell(formatStr("Stored?")))         //ub03
                    .addCell(new TableHeaderCell(formatStr("Available?")))      //ub03
                ;
                rows.setBackgroundColor(new Color("FFCC66"));
                tab.addRow(rows);

                // +------------------------------------------------------------+
                // | create the table rows                                      |
                // +------------------------------------------------------------+
                boolean lightYellow = true;                                     //ub03
                for (int i = 0; i < numRecs; i++) {

                    if (dbg) System.out.println("<br> Debug! surveyIds["+i+"] "+surveyIds[i]);

                    // +------------------------------------------------------------+
                    // | Create the link to call this page again and pass the last  |
                    // | survey ID to it, which will become the start survey ID in  |
                    // | new page.                                                  |
                    // +------------------------------------------------------------+
                    String link1 = new String(
                        sc.APP + "?" + sc.SCREEN + "=" + sc.DISPLAYSURVEY + "&" +
                        sc.SURVEYID + "=" + surveyIds[i] + "&" +            //ub01
                        sc.DATESTART + "=" + startDate + "&" +              //ub01
                        sc.PSC + "=" + sessionCode + "&" +                  //ub03
                        sc.AVAILABLE + "=" + available[i]);                 //ub03

                    // +------------------------------------------------------------+
                    // | Build a new row and add to the list table                  |
                    // +------------------------------------------------------------+
                    if (dbg) System.out.println("<br> ending off startDate "+startDate);

                    rows = new TableRow();
                    rows
                        .addCell(new TableDataCell(new Link(link1,new SimpleItem(surveyIds[i]).setFontSize(1))) )
                        .addCell(new TableDataCell(formatStr(projectNames  [i])))
                        .addCell(new TableDataCell(formatStr(cruiseNames   [i])))
                        .addCell(new TableDataCell(formatStr(institutes   [i])))
                        .addCell(new TableDataCell(formatStr(datesStart    [i])))
                        .addCell(new TableDataCell(formatStr(datesEnd      [i])))
                        .addCell(new TableDataCell(formatStr(surveyTypeNames[i]))   //ub03
                            .setHAlign(IHAlign.CENTER))                             //ub03
                        .addCell(new TableDataCell(formatStr(dataRecorded  [i]))    //ub03
                            .setHAlign(IHAlign.CENTER))                             //ub03
                        .addCell(new TableDataCell(formatStr(available     [i]))    //ub03
                            .setHAlign(IHAlign.CENTER))                             //ub03
                        ;

                    lightYellow = ("N".equals(available[i]) ? false : true);        //ub03
                    if (lightYellow) rows.setBackgroundColor(new Color("FFFFCC"));  //ub03

                    tab.addRow(rows);

                }  //  for (int i = 0; i < numRecs; i++)

                this.addItem(tab .setCenter() );

                // +------------------------------------------------------------+
                // | If there are more surveys, create a button for the user to |
                // | click for the next stations on the list.                   |
                // +------------------------------------------------------------+
                if (numRecs >= MAX_RECS) {
                    Form moresurvs = new Form("GET", sc.APP);
                    moresurvs
                        .addItem(new Hidden(sc.SCREEN, sc.SCIENTISTS2))
                        .addItem(new Hidden(sc.CODE, scientistCode))
                        .addItem(new Hidden(sc.PSC, sessionCode))       //ub03
                        .addItem(new Hidden(sc.DATESTART, datesStart[numRecs-1]) )
                        .addItem(new Hidden(sc.SURVEYID, surveyIds[numRecs-1]) )
                        .addItem(new Submit("Submit", "More") )
                        ;
                    this.addItem(moresurvs);

                } // if (numRecs >= MAX_RECS)

            } // if (!"".equals(startDate))

        } // if (!"".equals(scientistCode))


        // +------------------------------------------------------------+
        // | Close down the Oracle database session - with commit       |
        // +------------------------------------------------------------+
        sc.disConnect2(stmt, rset, conn);                               //ub06

    } // ListScientist


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
     * check if the value is null
     * returns  value / empty string
     */
    String checkNull(String value) {
        return (value != null ? value : "");
    } // checkNull


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("ListScientist local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new ListScientist(args));
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

}  //  class ListScientist
