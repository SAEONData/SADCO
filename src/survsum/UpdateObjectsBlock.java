package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update the objectives block
*
* @author  20070821 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070821 - Ursula von St Ange - change to package class              <br>
*/
public class UpdateObjectsBlock extends CompoundItem {

    /** A general purpose class used to extract the url-type parameters*/
    common.edmCommon ec = new common.edmCommon();
    boolean dbg = false;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();
    static SadCommon sc = new SadCommon();

    // Create java.sql stuff
//    String USER = "sadco";
//    String PWD = "ter91qes";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rset = null;
    String sql = "";

    // arguments
    String pSurveyId        = "";
    String pNewObjective    = "";

    // work variables
    String pLines[]     = new String[10];
    int pRecordcount    = 0;
    int numNotesRecords = 0;

    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public UpdateObjectsBlock(String args[]) {

        TableRow rows = new TableRow();

        // +------------------------------------------------------------+
        // | Print SADCO logo                                           |
        // +------------------------------------------------------------+
        Image slogo = new Image
            ("http://sadco.int.ocean.gov.za/sadco-img/sadlogo.gif",
            "sadlog.gif", IVAlign.TOP, false);
        this.addItem(slogo.setCenter());

        // +------------------------------------------------------------+
        // | Load the JDBC driver and connect to the database           |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass);
        /*
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = java.sql.DriverManager.getConnection
                ("jdbc:oracle:thin:" + USER + "/" + PWD +
                    "@146.64.23.200:1522:etek8");          // steamer
                    //"@morph.csir.co.za:1522:etek8");
            conn.setAutoCommit(false);
        } catch (Exception e) {
            this.addItem("Logon to SADCO fails: " + e.getMessage());
            return;
        } // try-catch
        */

        // +------------------------------------------------------------+
        // | Are there any input parameters                             |
        // +------------------------------------------------------------+
        if (args.length < 1) {
            this .addItem(new SimpleItem("There are no input parameters"))   ;
            return;
        }  //  if (args.length < 1)

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        String pSurveyId = sc.getArgument(args,"surveyid");
        String pNewObjective = sc.getArgument(args,"objective");
        //System.err.println("after pNewObjective " + pNewObjective);
        for (int i = 0; i < 10; i++) pLines[i] = "";

        if (pNewObjective.length() > 0) {

            // break the survey_notes up into blocks of 70 chars each
            String pNewObjective2 = pNewObjective.replace('\'','"');
            int totLength = pNewObjective2.length();
            int start = 0;
            int lastI = 0;
            for (int i = 0; i < 10 && start < totLength; i++) {
                int end = start + 70;
                if (end > totLength) end = totLength;
                pLines[i] = pNewObjective2.substring(start, end);
                start = end;
                lastI = i;
            } // for int i

            if (dbg) System.out.println("<br>pNewObjective2 = " + pNewObjective2);
            if (dbg) System.out.println("<br>pNewObjective2.length() = " + pNewObjective2.length());
            if (dbg) System.out.println("<br>lastI = " + lastI);

            // add or update the survey notes
            addUpdateObjectsBlock(pSurveyId, pLines, lastI);

        }  //  if (pUpdated.length() > 0)

        //System.err.println("before display_survey_notes - try");
        // get the notes again
        displaySurveyNotes(pSurveyId);
//        try {
//            //System.err.println("before display_survey_notes - in try " + pSurveyId);
//            sadsql.display_survey_notes(
//                pSurveyId         ,
//                pLines[0]         ,
//                pLines[1]         ,
//                pLines[2]         ,
//                pLines[3]         ,
//                pLines[4]         ,
//                pLines[5]         ,
//                pLines[6]         ,
//                pLines[7]         ,
//                pLines[8]         ,
//                pLines[9]
//            );
//            //System.err.println("after display_survey_notes - in try");
//        } catch (ServerException e) {
//            // no records found also returns an error, code = 100
//            //System.err.println("in catch - getSqlcode = " + e.getSqlcode());
//            if (e.getSqlcode() != 100) {
//                bd.addItem("Could not process display_survey_notes: survey_id = " +
//                    pSurveyId + "<br>");
//                bd.addItem("Sqlcode = " + e.getSqlcode() + "<br>");
//                bd.addItem("Sqlerrm = " + e.getSqlerrm() + "<br>");
//                hp.print();
//                closeSession(session);
//                return;
//            } //
//        } catch (Exception e) {
            // in case of other error types
//            System.err.println("in catch - Exception = " + e.getMessage());
//        }  //  try - catch
        //System.err.println("after display_survey_notes - try");

        pNewObjective = "";
        for (int i = 0; i < 10; i++) {
            pNewObjective += pLines[i];
        } // for (int i = 0; i < 10; i++) {
        pNewObjective = pNewObjective.replace('"','\'');

        // +------------------------------------------------------------+
        // | Create Main table                                          |
        // +------------------------------------------------------------+
        DynamicTable tab_main  = new DynamicTable(1);
        tab_main
            .setBorder(1)
            .setFrame(ITableFrame.BOX)
            .setCellSpacing(1)
            .setWidth("100%")
            .setBackgroundColor("#33CCFF")
            ;
        //System.err.println("after tab_main");

        // +------------------------------------------------------------+
        // | Create title table                                         |
        // +------------------------------------------------------------+
        DynamicTable tab_titl  = new DynamicTable(2);
        tab_titl
            .setBorder(0)
            .setFrame(ITableFrame.BOX)
            .setCellSpacing(0)
            .setWidth("100%")
            ;
        //System.err.println("after tab_titl");

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(sc.formatStrBig(
                "<b>SURVEY SUMMARY REPORT</b>")).setColSpan(80))
            .addCell(new TableHeaderCell(sc.formatStrBig(
                "<b>" + pSurveyId.toString() + "</b")))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));
        //System.err.println("after tab_titl - 2");

        // +------------------------------------------------------------+
        // | Create Objectives      table                               |
        // +------------------------------------------------------------+
        DynamicTable tab_narr  = new DynamicTable(2);
        tab_narr
            .setBorder(0)
            .setFrame(ITableFrame.BOX)
            .setCellSpacing(0)
            .setWidth("100%")
            ;
        //System.err.println("after tab_narr");

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig(
            "<b><u>Objectives and Brief Narrative of Cruise</u></b>")).setColSpan(80));
        tab_narr.addRow(rows);
        //System.err.println("after tab_narr - 2");

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp; <b>" + pNewObjective + "</b>"
            )))
        ;
        tab_narr.addRow(rows.setIVAlign(IVAlign.TOP));
        //System.err.println("after tab_narr - 3");

        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_narr).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());
        //System.err.println("hp.addItem(tab_main.setCenter())");


        // +------------------------------------------------------------+
        // | Create the form to update the data block                   |
        // +------------------------------------------------------------+
        Form updateProjectFrm = new Form("GET", "SurvSum");
        updateProjectFrm
            .addItem(new Hidden("pscreen", "UpdateObjectsBlock"))
            .addItem(new Hidden("updated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))

            .addItem(new SimpleItem("Objectives: <br>"))
            .addItem(new TextArea("objective", 70, 10, pNewObjective))

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("", "Submit New Objectives!") )
            .addItem(new Reset   ("Reset") )
            ;


        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateProjectBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update Project Block")   )
            ;
        //System.err.println("after form nextBlock");

        // +------------------------------------------------------------+
        // | Create the completed button                                |
        // +------------------------------------------------------------+
        Form close = new Form("GET", "SurvSum");
        close
            .addItem(new Hidden("pscreen", "DisplaySurvsum"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("passcode", "ter91qes"))
            .addItem(new Submit("submit", "<<< Survey Summary")   )
            ;
        //System.err.println("after form close");

        DynamicTable tab_buttons = new DynamicTable(2);
        tab_buttons
            .setBorder(0)
            .setFrame(ITableFrame.BOX)
            .setCellSpacing(0)
            ;

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(close))
            .addCell(new TableHeaderCell(nextBlock))
            ;
        tab_buttons.addRow(rows.setIVAlign(IVAlign.TOP));
        //System.err.println("after tab_buttons");


        // +------------------------------------------------------------+
        // | Create Update table                                        |
        // +------------------------------------------------------------+
        DynamicTable tab_update  = new DynamicTable(2);
        tab_update
            .setBorder(2)
            .setFrame(ITableFrame.BOX)
            .setCellSpacing(2)
            .setWidth("100%")
            ;

        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Add new Objectives"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(updateProjectFrm));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        //System.err.println("after tab_update");
//          rows = new TableRow();
//          rows

//         .addCell(new TableDataCell(existfrm))
//         .addCell(new TableDataCell(newfrm))
//         ;
//      tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(tab_buttons).setBackgroundColor(Color.cyan))
            .addCell(new TableHeaderCell("&nbsp;").setBackgroundColor(Color.cyan))
            ;
        tab_update.addRow(rows);


        this.addItem(tab_update.setCenter());


        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
        try {
            if (dbg) System.err.println("Before conn.close()");
            //conn.close();
            sc.disConnect(stmt, rset, conn);
            if (dbg) System.err.println("After conn.close()");
        }  catch (Exception e){}

    }  //  public static void main(String args[])

    /**
     * count the number of survey notes
     * @param   pSurveyId
     * @returns number of survey notes
     */
    void addUpdateObjectsBlock(String pSurveyId, String pLines[], int lastI) {

        try {

            // are there any notes?
            sql = "select count(*) from survey_notes " +
                  "where survey_id='" + pSurveyId + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                numNotesRecords = (int)rset.getFloat(1);
            } // while (rset.next())
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // set up the sql
            if (numNotesRecords == 0) {
                // insert the record
                sql = "insert into survey_notes values ('" + pSurveyId + "',";
                for (int i = 0; i <= lastI; i++) {
                    sql += "'" + pLines[i] + "'" + (i<lastI?", ":"");
                } // for (int i = 0; i < 10; i++)
                for (int i = lastI+1; i < pLines.length; i++) {
                    sql += ", NULL";
                } // for (int i = lastI+1; i < pLines.length; i++)
                sql += ")";
            } else {
                // update the records
                sql = "update survey_notes set ";
                for (int i = 0; i <= lastI; i++) {
                    sql += "line" + (i+1) + "='" + pLines[i] + "'" + (i<lastI?", ":"");
                } // for (int i = 0; i < 10; i++)
                for (int i = lastI+1; i < pLines.length; i++) {
                    sql += ", line" + (i+1) + "=NULL";
                } // for (int i = lastI+1; i < pLines.length; i++)
                sql += " where survey_id = '" + pSurveyId + "'";
            } // if (numNotesRecords == 0)

            // do the update / insert
            if (dbg) System.out.println("<br>sql = " + sql);
            stmt = conn.createStatement();
            int numRecords = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;
            if (dbg) System.out.println("<br>numRecords = " + numRecords);
            if (dbg) System.out.println("<br>stmt.getWarnings() = " + stmt.getWarnings());

        } catch (Exception e) {
            this.addItem("<br>Could not process addUpdateObjectsBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch
    } // addUpdateObjectsBlock


    /**
     *
     */
    void displaySurveyNotes(String pSurveyId) {

        try {

            sql = "select line1, line2, line3, line4, line5, " +
                  "line6, line7, line8, line9, line10 " +
                  "from survey_notes " +
                  "where survey_id='" + pSurveyId + "'";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
                for (int i = 0; i < 10; i++) {
                    pLines[i] = sc.spaceIfNull(rset.getString(i+1));
                } // for (int i = 0; i < 10; i++)
            } // while (rset.next())

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process displaySurveyNotes: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // displaySurveyNotes


    /**
     * Look up a URL parameter
     * @param   args    list of url name-value parameter pairs
     * @param   name    name of parameter to find value for
     */
//    private static String getArgument(String args[], String name) {
//        String prefix = name + "=";
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].startsWith(prefix) ) {
//                return args[i].substring(prefix.length());
//            }  //  if (args[i].startsWith(prefix) )
//        }  //  for (int i = 0; i < args.length; i++)
//        return "";
//    } // getArgument


    /**
     * Format text strings for html
     * @param   oldStr  the text to format
     */
//    private static Container formatStr(String oldStr) {
//        Container newStrc = new Container();
//        newStrc.addItem(new SimpleItem(oldStr).setFontSize(2));
//        return newStrc;
//    }  //  private static Container formatStr(String oldStr)


    /**
     * Format text strings for html (big)
     * @param   oldStr  the text to format
     */
//    private static Container formatStrBig(String oldStr) {
//        Container newStrc = new Container();
//        newStrc.addItem(new SimpleItem(oldStr).setFontSize(3));
//        return newStrc;
//    } // formatStrBig


    /**
     * Return space iso null
     * @param   text    text to test for null
     */
//    public String spaceIfNull(String text) {
//        return (text == null ? "" : text);
//    } // spaceIfNull


    /**
     * main class for testing purposes
     * @param   args    list of URL name-value parameters
     */
    public static void main(String args[]) {

        HtmlHead hd = new HtmlHead("UpdateObjectsBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateObjectsBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateObjectsBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

}  //  public class UpdateObjectsBlock
