package sadinv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.html.*;


/**
 * This class handles the search on surveyid

 * SadInv
 * pscreen = surveys
 * psurveyid = <survey Id>
 *
 * @author 20090105 - SIT Group.
 * @version
 * 20090105 - Ursula von St Ange - create program                       <br>
 * 20090602 - Ursula von St Ange - chnge cruise name to survey name ub02<br>
 * 20091203 - Ursula von St Ange - add sessionCode, 'data available' and
 *                                 altern rows different colours (ub03) <br>
 * 20100201 - Ursula von St Ange - if dataRecorded = N,
 *                                 make available = N (ub04)            <br>
 * 20100507 - Ursula von St Ange - change for postgres             ub05 <br>
 * 20111103 - Ursula von St Ange - save survey_id list on db       ub06 <br>
 */

public class ListSurveyId extends CompoundItem {

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

    static private int MAX_RECS = 25;

    // +------------------------------------------------------------+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public ListSurveyId(String args[]) {

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String surveyId = ec.getArgument(args, sc.SURVEYID);
        if (dbg) System.out.println("surveyId = "+surveyId);
        String sessionCode = ec.getArgument(args, sc.PSC);              //ub03
        if (dbg) System.out.println("sessionCode = "+sessionCode);      //ub03
        if (dbg) for (int i = 0; i < args.length; i++)
            System.out.println("args[" + i + "] = "+args[i]);

        // +------------------------------------------------------------+
        // | Enter a Survey ID                                          |
        // +------------------------------------------------------------+
        Form frm = new Form("GET", sc.APP);

        frm
            .addItem(new Hidden(sc.SCREEN, "surveys"))
            .addItem(new Hidden(sc.PSC, sessionCode))                   //ub03
            .addItem("Enter first few characters of a Survey ID and press <b>ENTER</b>: ")
            .addItem(new TextField(sc.SURVEYID, 9, 9, ""))
            .addItem(" (YYYY/xxxx)")
            ;
        this.addItem(frm);

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        String sql = "";
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


        // +------------------------------------------------------------+
        // | Get a listing of the surveys if there is a survey ID input |
        // +------------------------------------------------------------+
        if (!"".equals(surveyId)) {

            // +------------------------------------------------------------+
            // | Build the empty arrays for holding survey  data            |
            // +------------------------------------------------------------+
            String[] surveyIds       = new String[MAX_RECS];
            String[] projectNames    = new String[MAX_RECS];
            String[] cruiseNames     = new String[MAX_RECS];
            String[] planamNames     = new String[MAX_RECS];
            String[] surnames        = new String[MAX_RECS];
            String[] fNames          = new String[MAX_RECS];
            String[] titles          = new String[MAX_RECS];
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
            // | Retrieve the survey information                            |
            // +------------------------------------------------------------+
            int numRecs = 0;

            try {

                sql =
                    "select inv.survey_id, inv.project_name, "  +
                    "inv.cruise_name, plan.name, sc.surname, "  +
                    "sc.f_name, sc.title, inst.name, " +
                    "to_char(inv.date_start,'YYYY-MM-DD'), " +
                    "to_char(inv.date_end,'YYYY-MM-DD'), " +
                    "inv.data_recorded, st.name, inv.data_available " + //ub03
                    "from inventory inv, institutes inst, " +
                    "planam plan, scientists sc, " +
                    "survey_type st " +
                    "where inv.survey_id >= '"+surveyId+"' " +
                    "and plan.code = inv.planam_code " +
                    //"and inst.code(+) = inv.instit_code "+
                    "and inst.code = inv.instit_code "+
                    "and sc.code = inv.sci_code_1 "+
                    "and inv.survey_type_code = st.code "+
                    "order by inv.survey_id ";

                if (dbg) System.out.println("<br>*** sql = "+sql);
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                int i = 0;
                int j = 0;
                //String prevSurveyId = "";                               //ub03
                //String currSurveyId = "";                               //ub03
                //int passkeyCnt = 0;                                     //ub03
                //boolean hasNull = false;
                //while (rset.next() && (i < MAX_RECS)) {
                while (rset.next()) {                                     //ub06
                    if (dbg) System.out.println("5 "+i);
                    if (i < MAX_RECS) {                                   //ub06
                        surveyIds     [i] = checkNull(rset.getString(1));
                        projectNames  [i] = checkNull(rset.getString(2));
                        cruiseNames   [i] = checkNull(rset.getString(3));
                        planamNames   [i] = checkNull(rset.getString(4));
                        surnames      [i] = checkNull(rset.getString(5));
                        fNames        [i] = checkNull(rset.getString(6));
                        titles        [i] = checkNull(rset.getString(7));
                        institutes    [i] = checkNull(rset.getString(8));
                        datesStart    [i] = checkNull(rset.getString(9));
                        datesEnd      [i] = checkNull(rset.getString(10));
                        dataRecorded  [i] = checkNull(rset.getString(11)).toUpperCase();
                        surveyTypeNames[i]= checkNull(rset.getString(12));
                        available     [i] = checkNull(rset.getString(13)).toUpperCase();//ub03
                        if (dbg) System.out.println("<br> surveyIds ["+i+"] "+surveyIds[i]);
                        if (dbg) System.out.println("<br> projectNames ["+i+"] "+projectNames[i]);
                        if ("N".equals(dataRecorded[i])) available[i] = "N";     //ub04

                        allSurveys.append(surveyIds[i] + " " + available[i] + ",");//ub06
                        i++;
                    } else {                                                          //ub06
                        tmpSurveyId     = checkNull(rset.getString(1));               //ub06
                        tmpDataRecorded = checkNull(rset.getString(11)).toUpperCase();//ub06
                        tmpAvailable    = checkNull(rset.getString(13)).toUpperCase();//ub06
                        if ("N".equals(tmpDataRecorded)) tmpAvailable = "N";          //ub06
                        allSurveys.append(tmpSurveyId + " " + tmpAvailable + ",");    //ub06
                    } // if (i < MAX_RECS)                                            //ub06
                    j++;
                } // while (rset.next()) {

                numRecs = i;
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

            } catch (SQLException e) {
                //pc.writeErrorMessage(e, thisClass, "last_download_date", "N/A",
                //    sql, " ");
                System.err.println("<br>Debug "+sql +" "+ e.getMessage());
                e.printStackTrace();
            } // try-catch

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
            DynamicTable tab = new DynamicTable(9);
            tab
                .setBorder(1)
                .setCellSpacing(1) // default
                .setBackgroundColor(new Color("FFFF99"))
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL)
                ;

            // +------------------------------------------------------------+
            // | Create the headings                                        |
            // +------------------------------------------------------------+
            TableRow rows = new TableRow();
            rows
                .addCell(new TableHeaderCell(formatStr("Survey ID")))
                .addCell(new TableHeaderCell(formatStr("Project Name")))
                .addCell(new TableHeaderCell(formatStr("Survey/Station Name")))// ub02
                .addCell(new TableHeaderCell(formatStr("Platform Name")))
                .addCell(new TableHeaderCell(formatStr("Chief Scientist")))
                .addCell(new TableHeaderCell(formatStr("Institute")))
                .addCell(new TableHeaderCell(formatStr("Start")))
                .addCell(new TableHeaderCell(formatStr("End")))
                .addCell(new TableHeaderCell(formatStr("Survey Type")))
                .addCell(new TableHeaderCell(formatStr("Stored?")))         //ub03
                .addCell(new TableHeaderCell(formatStr("Available?")))      //ub03
                ;
            rows.setBackgroundColor(new Color("FFCC66"));
            tab.addRow(rows);


            // +------------------------------------------------------------+
            // | add the rows with survey details                           |
            // +------------------------------------------------------------+
            boolean lightYellow = true;                                     //ub03
            for (int i = 0; i < numRecs; i++) {

                // +------------------------------------------------------------+
                // | Create the link to call this page again and pass the last  |
                // | survey ID to it, which will become the start survey ID in  |
                // | new page.                                                  |
                // +------------------------------------------------------------+
                String link1 = new String(
                    sc.APP + "?" + sc.SCREEN + "=" + sc.DISPLAYSURVEY + "&" +
                    sc.SURVEYID + "=" + surveyIds[i] + "&" +
                    sc.PSC + "=" + sessionCode + "&" +                          //ub03
                    sc.AVAILABLE + "=" + available[i]);                         //ub03

                // +------------------------------------------------------------+
                // | Build a new row and add to the list table                  |
                // +------------------------------------------------------------+
                rows = new TableRow();
                rows.addCell(new TableDataCell(new Link(link1,new SimpleItem(surveyIds[i]).setFontSize(1))) );
                rows.addCell(new TableDataCell(formatStr(projectNames  [i])));
                rows.addCell(new TableDataCell(formatStr(cruiseNames   [i])));
                rows.addCell(new TableDataCell(formatStr(planamNames   [i])));
                rows.addCell(new TableDataCell(formatStr(titles        [i].trim()
                    + " " + fNames[i].trim() + " " +surnames[i])));
                rows.addCell(new TableDataCell(formatStr(institutes    [i])));
                rows.addCell(new TableDataCell(formatStr(datesStart    [i])));
                rows.addCell(new TableDataCell(formatStr(datesEnd      [i])));
                rows.addCell(new TableDataCell(formatStr(surveyTypeNames[i]))   //ub03
                    .setHAlign(IHAlign.CENTER));                                //ub03
                rows.addCell(new TableDataCell(formatStr(dataRecorded  [i]))    //ub03
                    .setHAlign(IHAlign.CENTER));                                //ub03
                rows.addCell(new TableDataCell(formatStr(available     [i]))    //ub03
                    .setHAlign(IHAlign.CENTER));                                //ub03

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
                Form moresurvs = new Form("GET", sc.APP);
                moresurvs
                    .addItem(new Hidden(sc.SURVEYID, surveyIds[numRecs-1]) )
                    .addItem(new Hidden(sc.PSC, sessionCode))               //ub03
                    .addItem(new Hidden(sc.SCREEN, "surveys"))
                    //.addItem(new Hidden(sc.VERSION, "next"))
                    .addItem(new Submit("Submit", "More") )
                    ;
                this.addItem(moresurvs);

            } // if (numRecs == MAX_RECS)

        } // if (surveyId != null)

        // +------------------------------------------------------------+
        // | Close down the Oracle database session - with commit       |
        // +------------------------------------------------------------+
        sc.disConnect2(stmt, rset, conn);                               //ub06

    }  //  public static void main(String args[])

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
            HtmlHead hd = new HtmlHead("ListSurveyId local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new ListSurveyId(args));
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


} //  class ListSurveyId
