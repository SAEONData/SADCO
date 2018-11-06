package sadinv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.html.*;


/**
 * This class handles the search on datatype - displays the list of
 * surveys for the datatype
 *
 * SadInv
 * pscreen = datatypes2
 * pdatatype = <datatype code>
 * psurveyid = <survey id>
 *
 * @author 20090113 - SIT Group.
 * @version
 * 20090113 - Ursula von St Ange - create program                       <br>
 * 20090907 - Ursula von St Ange - add code to correct year tables(ub01)<br>
 * 20090602 - Ursula von St Ange - chnge cruise name to survey name ub02<br>
 * 20091203 - Ursula von St Ange - add seessionCode, 'data available' and
 *                                 altern rows different colours (ub03) <br>
 * 20100201 - Ursula von St Ange - if dataRecorded = N,
 *                                 make available = N (ub04)            <br>
 * 20100507 - Ursula von St Ange - change for postgres             ub05 <br>
 * 20111103 - Ursula von St Ange - save survey_id list on db       ub06 <br>
 */

public class ListDataType extends CompoundItem {

    //boolean dbg = true;
    boolean dbg = false;

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
    public ListDataType (String args[]) {

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String datatypeCode = ec.getArgument(args,sc.CODE);
        if (dbg) System.out.println("<br>datatypeCode "+datatypeCode);
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

        // prepare the sql string
        String sql = "";

        int yearArray[]    = new int[200];
        boolean rowArray[] = new boolean[20] ;

        // +-------------------------------------------------------------------+
        // | Get a listing of the scientist if there is a datatype name input |
        // +-------------------------------------------------------------------+
        if (!"".equals(datatypeCode)) {

            // +------------------------------------------------------------+
            // | Prepare for PL/SQL values                                  |
            // +------------------------------------------------------------+
            String  datesStartRange = "";
            String  datesEndRange   = "";
            String  datatypeName    = "";
            String  datatypeDesc    = "";
            int     totalRecords    = 0;

            // +------------------------------------------------------------+
            // | get the start and end date for the datatype, and get      |
            // | the number of records                                      |
            // +------------------------------------------------------------+
            try {
                sql =
                    " select to_char(min(date_start),'YYYY-MM-DD'),"+
                    " to_char(max(date_end),'YYYY-MM-DD'), count(*) "+      //ub01
                    " from inventory "+
                    " where survey_type_code = "+datatypeCode;

                if (dbg) System.out.println("<br>sql "+sql);

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                while (rset.next()) {

                    if (dbg) System.out.println("<br>In rset ");
                    datesStartRange = checkNull(rset.getString(1));
                    datesEndRange   = checkNull(rset.getString(2));
                    totalRecords = (int)rset.getFloat(3);

                    if (dbg) System.out.println("<br>datesEndRange "+datesEndRange);
                    if (dbg) System.out.println("<br>datesStartRange "+datesStartRange);
                    if (dbg) System.out.println("<br> **totalRecords "+totalRecords);

                } //while (rset.next()) {

                if (dbg) System.out.println("<br> **totalRecords after while "+totalRecords);

                rset.close();
                stmt.close();
                rset = null;
                stmt = null;
            } catch (SQLException e) {
                System.err.println("<br> "+e.getMessage());
                    e.printStackTrace();
            } // try-catch


            // +------------------------------------------------------------+
            // | Get the datatype details                                  |
            // +------------------------------------------------------------+
            try {

                sql =
                    " select name, description "+
                    " from survey_type "+
                    " where code = "+datatypeCode;

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                if (dbg) System.out.println("<br> **sql "+sql);

                while (rset.next()) {
                    datatypeName  = checkNull(rset.getString(1));
                    datatypeDesc  = checkNull(rset.getString(2));
                    if (dbg) System.out.println("<br> **datatypeName "+datatypeName);

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
                "Date range:" + datesStartRange + " to " + datesEndRange +
                "<br>Number of surveys: " + totalRecords));
            header_tab.addRow(rows);

            this.addItem(header_tab .setCenter());


            if ("".equals(startDate)) {

                // +------------------------------------------------------------+
                // | When there are many surveys, construct a table of years    |
                // | so that the user can choose a starting year.               |
                // +------------------------------------------------------------+
                if (totalRecords > MAX_RECS) {
                    // +------------------------------------------------------------+
                    // | Create starting and ending years in integer format         |
                    // +------------------------------------------------------------+
                    int startYear = Integer.parseInt(datesStartRange.substring(0,4));
                    int endYear   = Integer.parseInt(datesEndRange.substring(0,4));

                    // +------------------------------------------------------------+
                    // | The table have rows of decades. The first decade needs to  |
                    // | be calculated                                              |
                    // +------------------------------------------------------------+
                    int startdecade = (startYear / 10 * 10);

                    // +------------------------------------------------------------+
                    // | Calculate the number of decades covered                    |
                    // +------------------------------------------------------------+
                    int decades = (endYear / 10 - startdecade / 10);

                    // +------------------------------------------------------------+
                    // | Report Heading                                             |
                    // +------------------------------------------------------------+
                    this.addItem(new SimpleItem("Click on a year to start listing: ")
                        .setCenter().setHeading(3));

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


                    // +------------------------------------------------------------+
                    // | Find out which years have surveys for datatype             |
                    // +------------------------------------------------------------+
                    int year1 = 0;                                          //ub01
                    int year2 = 0;                                          //ub01
                    try {

                        sql = "select distinct to_char(date_start,'YYYY'), " +//ub01
                              "to_char(date_end,'yyyy') from " +            //ub01
                              "inventory where survey_type_code = "+datatypeCode;

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

                                yearArray[year - 1850] = year;
                                if (dbg) System.out.println(
                                    "<br> yearArray[year - 1850] "+
                                    yearArray[year - 1850]);

                                rowArray[(year - 1850) / 10] = true;
                                if (dbg) System.out.println(
                                    "<br> rowArray[year - 1850/10] "+
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


                    // +------------------------------------------------------------+
                    // | Create the actual table                                    |
                    // +------------------------------------------------------------+
                    for (int j = 0; j < rowArray.length; j++) {

                        rows = new TableRow();

                        if (rowArray[j]) {

                            int startIndex = j * 10;
                            for (int i2 = startIndex; i2 < startIndex + 10; i2++) {
                                if (dbg) System.out.println("<br> in for (int i2= startIndex");
                                //System.err.println("<br> in for (int i2= startIndex");

                                if (yearArray[i2] != 0) {

                                    if (dbg) System.out.println("<br> in if (yearArray[i2] != 0 )"+
                                        yearArray[i2]);

                                    String link = new String(
                                        sc.APP + "?" +
                                        sc.SCREEN + "=" + sc.DATATYPES2 + "&" +
                                        sc.CODE + "=" + datatypeCode + "&" +
                                        sc.PSC + "=" + sessionCode + "&" +      //ub03
                                        sc.DATESTART + "=" + yearArray[i2] + "-01-01");
                                    rows.addCell(new TableDataCell(new
                                        Link(link,new SimpleItem(yearArray[i2]))) );

                                } else {
                                    int actualYear = 1850 + i2;
                                    rows.addCell(new TableDataCell(
                                        new SimpleItem(actualYear)));
                                    if (dbg) System.out.println(
                                        "<br> in else if (yearArray[i2] != 0)"+actualYear);
                                } // if (yearArray[i2] != 0) {

                            } // for (int i2= startIndex; i2 < startIndex + 10; i2++) {
                            yearTab.addRow(rows);

                        } // if if (yearArray[i2] != 0)
                        if (dbg) System.out.println("<br> after if (rowArray[j]) "+j);

                    } //for (int j = 0; j < rowArray.length; j++) {
                    if (dbg) System.out.println("<br> after for loop ");

                    this.addItem(yearTab .setCenter());

                } else {
                    // +------------------------------------------------------------+
                    // | Store the starting date for survey listing                 |
                    // +------------------------------------------------------------+
                    startDate = datesStartRange;
                } // if (totalRecords > MAX_RECS)

            } // if ("".equals(startDate))

            if (!"".equals(startDate)) {

                // +------------------------------------------------------------+
                // | Prepare for PL/SQL values                                  |
                // +------------------------------------------------------------+
                //String  pStartDateIn   = startDate;
                String surveyIds[]    = new String[MAX_RECS];
                String projectNames[] = new String[MAX_RECS];
                String surnames[]     = new String[MAX_RECS];
                String fNames[]       = new String[MAX_RECS];
                String titles[]       = new String[MAX_RECS];
                String cruiseNames[]  = new String[MAX_RECS];
                String planamNames[]  = new String[MAX_RECS];
                String datesStart[]   = new String[MAX_RECS];
                String datesEnd[]     = new String[MAX_RECS];
                String institutes[]   = new String[MAX_RECS];
                String dataRecorded[] = new String[MAX_RECS];
                String available[]    = new String[MAX_RECS];               //ub03
                StringBuffer allSurveys  = new StringBuffer("");            //ub06
                String   tmpSurveyId     = "";                              //ub06
                String   tmpAvailable    = "";                              //ub06
                String   tmpDataRecorded = "";                              //ub06

                if (dbg) System.err.println("<br>datatypeCode = "+datatypeCode);
                if (dbg) System.err.println("<br>startDate  = "+startDate);
                if (dbg) System.err.println("<br>before list_survey_datatype:");

                // +------------------------------------------------------------+
                // | Retrieve the survey information from the packaged PL/SQL   |
                // | using procedure list_survey_datatype                      |
                // +------------------------------------------------------------+

                int numRecs = 0;
                try {

                    if (dbg) System.out.println("<br>startDate "+startDate);

                    sql =
                        " select survey_id, project_name, plan.name,"+
                        " cruise_name, surname, f_name, title,"+
                        " institutes.name, to_char(date_start,'YYYY-MM-DD'),"+
                        " to_char(date_end  ,'YYYY-MM-DD'), data_recorded, " +
                        " data_available " +                                    //ub03"
                        " from inventory, planam plan, scientists, institutes"+
                        " where survey_type_code = "+datatypeCode+
                        " and (inventory.date_start >= to_timestamp('"+startDate+"','YYYY-MM-DD')"+ //ub01
                        " or (inventory.date_start <= to_timestamp('"+startDate+"','YYYY-MM-DD') "+ //ub01
                        " and to_timestamp('"+startDate+"','YYYY-MM-DD') <= inventory.date_end)) "+ //ub01
                        " and plan.code = inventory.planam_code "+
                        " and scientists.code = inventory.sci_code_1 "+
                        " and institutes.code  = inventory.instit_code"+
                        " order by inventory.date_start, survey_id ";

                    if (dbg) System.out.println("<br>sql "+sql);

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
                                projectNames[i]  = checkNull(rset.getString(2));
                                planamNames[i]   = checkNull(rset.getString(3));
                                cruiseNames[i]   = checkNull(rset.getString(4));
                                surnames[i]      = checkNull(rset.getString(5));
                                fNames[i]        = checkNull(rset.getString(6));
                                titles[i]        = checkNull(rset.getString(7));
                                institutes[i]    = checkNull(rset.getString(8));
                                datesStart[i]    = checkNull(rset.getString(9));
                                datesEnd[i]      = checkNull(rset.getString(10));
                                dataRecorded[i]  = checkNull(rset.getString(11)).toUpperCase();
                                available[i]     = checkNull(rset.getString(12)).toUpperCase();//ub03
                                if ("N".equals(dataRecorded[i])) available[i] = "N";     //ub04
                                if (dbg) System.out.println("<br> surveyIds["+i+"]"+surveyIds[i]);
                                allSurveys.append(surveyIds[i] + " " + available[i] + ",");//ub06
                                i++;
                            } // if (found)
                        } else {                                                          //ub06
                            tmpSurveyId     = checkNull(rset.getString(1));               //ub06
                            tmpDataRecorded = checkNull(rset.getString(11)).toUpperCase();//ub06
                            tmpAvailable    = checkNull(rset.getString(12)).toUpperCase();//ub06
                            if ("N".equals(tmpDataRecorded)) tmpAvailable = "N";          //ub06
                            allSurveys.append(tmpSurveyId + " " + tmpAvailable + ",");    //ub06
                        } // if (i < MAX_RECS)                                            //ub06
                    } // while (rset.next())

                    numRecs = i;
                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (SQLException e) {
                    System.err.println("<br>sql = " +sql);
                    System.err.println("<br>message = "+e.getMessage());
                    e.printStackTrace();
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
                DynamicTable tab = new DynamicTable(8);
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
                    .addCell(new TableHeaderCell(formatStr("Chief Scientist")))
                    .addCell(new TableHeaderCell(formatStr("Institute")))
                    .addCell(new TableHeaderCell(formatStr("Start")))
                    .addCell(new TableHeaderCell(formatStr("End")))
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

                    if (dbg) System.err.println("** surveyIds[i] "+surveyIds[i]+" i "+ i);
                    if (dbg) System.err.println("**surveyIds[i].length = "+surveyIds[i].length());
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

                    if (dbg) {
                        System.err.println(" datesStart [i] "+datesStart[i]+" i "+ i);
                        System.err.println(" datesEnd "+datesEnd[i]+" i "+ i);
                        System.err.println(" surveyIds[i] "+surveyIds[i]+" i "+ i);
                        System.err.println(" projectNames  [i] "+projectNames[i]+" i "+ i);
                        System.err.println(" titles        [i] "+titles[i]+" i "+ i);
                        System.err.println(" fNames        [i] "+fNames[i]+" i "+ i);
                        System.err.println(" surnames      [i] "+surnames      [i]+" i "+ i);
                        System.err.println(" planamNames   [i] "+surnames      [i]+" i "+ i);
                        System.err.println(" cruiseNames   [i] "+cruiseNames   [i]+" i "+ i);
                        System.err.println(" dataRecorded [i] "+dataRecorded[i]+" i "+ i);
                    } // if (dbg)

                    rows = new TableRow();
                    rows
                        .addCell(new TableDataCell(new Link(link1,new SimpleItem(surveyIds[i]).setFontSize(1))) )
                        .addCell(new TableDataCell(formatStr(projectNames  [i])))
                        .addCell(new TableDataCell(formatStr(cruiseNames   [i])))
                        .addCell(new TableDataCell(formatStr(planamNames   [i])))
                        .addCell(new TableDataCell(formatStr(titles        [i].trim() +
                            " " + fNames[i].trim() + " " + surnames[i])))
                        .addCell(new TableDataCell(formatStr(institutes    [i])))
                        .addCell(new TableDataCell(formatStr(datesStart    [i])))
                        .addCell(new TableDataCell(formatStr(datesEnd      [i])))
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
                if (numRecs == MAX_RECS) {

                   if (dbg) System.err.println(" datatypeCode "+datatypeCode);

                   //Form moresurvs = new Form("GET", "ListDataType");
                   Form moresurvs = new Form("GET", sc.APP);
                   moresurvs
                      .addItem(new Hidden(sc.SCREEN, sc.DATATYPES2))
                      .addItem(new Hidden(sc.CODE, datatypeCode))
                      .addItem(new Hidden(sc.PSC, sessionCode))         //ub03
                      .addItem(new Hidden(sc.DATESTART, datesStart[numRecs-1]) )
                      .addItem(new Hidden(sc.SURVEYID, surveyIds[numRecs-1]) )
                      .addItem(new Submit("Submit", "More") )
                      ;
                   this.addItem(moresurvs);

                } // if (numRecs == MAX_RECS)

            } // if (!"".equals(startDate))

        } // if (!"".equals(datatypeCode))

        // +------------------------------------------------------------+
        // | Close down the Oracle database session - with commit       |
        // +------------------------------------------------------------+
        sc.disConnect2(stmt, rset, conn);                               //ub06

    } // ListDataType


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
            HtmlHead hd = new HtmlHead("ListDataType local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new ListDataType(args));
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

} // class ListDataType
