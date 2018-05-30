package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update ocean areas
*
* @author  20070822 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070822 - Ursula von St Ange - change to package class              <br>
*/
public class UpdateOcAreasBlock extends CompoundItem {

    boolean dbg = false;
    //boolean dbg = true;

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
    String  pSurveyId       = "";
    String  pUpdated        = "";
    String  pNewLatNorth    = "";
    String  pNewLatSouth    = "";
    String  pNewLongWest    = "";
    String  pNewLongEast    = "";

    // work variables
    float   pLatNorth       = 0;
    float   pLatSouth       = 0;
    float   pLongWest       = 0;
    float   pLongEast       = 0;

    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public UpdateOcAreasBlock(String args[]){

        TableRow rows = new TableRow();

        // +------------------------------------------------------------+
        // | Print SADCO logo                                           |
        // +------------------------------------------------------------+
        Image slogo = new Image
            ("http://fred.csir.co.za/sadco-img/sadlogo.gif",
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
        pSurveyId       = sc.getArgument(args,"surveyid");
        pUpdated        = sc.getArgument(args,"updated");
        pNewLatNorth    = sc.getArgument(args,"newlatnorth");
        pNewLatSouth    = sc.getArgument(args,"newlatsouth");
        pNewLongWest    = sc.getArgument(args,"newlongwest");
        pNewLongEast    = sc.getArgument(args,"newlongeast");

        if (pUpdated.length() > 0) {
            updateOcareaBlock(pSurveyId, pNewLatNorth, pNewLatSouth,
                pNewLongWest, pNewLongEast);
         }  //  if (pUpdated.length() > 0)


        // +------------------------------------------------------------+
        // | Retrieve the data from the packaged PL/SQL                 |
        // | using procedure get_data_block                             |
        // +------------------------------------------------------------+
        getOcareaBlock(pSurveyId);

        // +--------------------------+
        // | Generate the ocean area  |
        // +--------------------------+
        String oceanArea = "";

        if (pLongWest < 10) {
            if (pLongWest < -20 & pLongEast >= -20) {
                oceanArea = new String("SW and SE Atlantic");
            } else {
                if (pLongWest >= -20) {
                    oceanArea = new String("SE Atlantic");
                } else {
                    oceanArea = new String("SW Atlantic");
                }  //  if (pLongWest >= -20)
            }  //  if (pLongWest < -20 & pLongEast >= -20)
        }  //  if (pLongWest < 10)

        if (pLongEast > 20) {
            if (oceanArea.length() < 1) {
                oceanArea = new String("SW Indian");
            } else {
                oceanArea = new String(oceanArea + ", SW Indian");
            }  //  if (oceanArea.length() < 1)
        }  //  if (lonEat > 20)

        if (pLatSouth > 40) {
            if (oceanArea.length() < 1) {
                oceanArea = new String("Southern");
            } else {
                oceanArea = new String(oceanArea + ", Southern");
            }  //  if (oceanArea.length() < 1)
        }  //  if (pLatSouth > 40)

        // +------------------------------------------------------------+
        // | Create Main table                                          |
        // +------------------------------------------------------------+
        DynamicTable tab_main  = new DynamicTable(1);
        tab_main
            .setBorder(1)
            .setCellSpacing(1)
            .setWidth("100%")
            .setBackgroundColor("#33CCFF")
            .setFrame(ITableFrame.BOX)
            ;

        // +------------------------------------------------------------+
        // | Create title   table                                       |
        // +------------------------------------------------------------+
        DynamicTable tab_titl  = new DynamicTable(2);
        tab_titl
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>SURVEY SUMMARY REPORT</b>")).setColSpan(80))
            .addCell(new TableHeaderCell(sc.formatStrBig("<b>" + pSurveyId + "</b")))
            ;
        tab_titl.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Create OC Area         table                               |
        // +------------------------------------------------------------+
        DynamicTable tab_acar  = new DynamicTable(2);
        tab_acar
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>General Ocean Areas</u></b>")).setColSpan(80));
        tab_acar.addRow(rows);

        rows = new TableRow();
        rows
            .addCell(new TableDataCell(sc.formatStr(
                "&nbsp;&nbsp; <b>" + oceanArea + "</b>"
                )))
            ;
        tab_acar.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_acar).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        this.addItem(tab_main.setCenter());

        // +------------------------------------------------------------+
        // | Create the form to update the data block                   |
        // +------------------------------------------------------------+
        Form existfrm = new Form("GET", "SurvSum");
        existfrm
            .addItem(new Hidden("pscreen", "UpdateOcAreasBlock"))
            .addItem(new Hidden("updated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))

            .addItem(new SimpleItem("Longitudes: "))
            .addItem(new TextField("newlongwest", 8, 8, String.valueOf(pLongWest)))
            .addItem(new SimpleItem("--------"))
            .addItem(new TextField("newlongeast", 8, 8, String.valueOf(pLongEast)))
            .addItem(new SimpleItem("<br>"))
            .addItem(new SimpleItem("Latitudes: <br>"))
            .addItem(new TextField("newlatnorth", 8, 8, String.valueOf(pLatNorth)))
            .addItem(new SimpleItem("<br>|"))
            .addItem(new SimpleItem("<br>|"))
            .addItem(new SimpleItem("<br>|"))
            .addItem(new SimpleItem("<br>|"))
            .addItem(new TextField("newlatsouth", 8, 8, String.valueOf(pLatSouth)))

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit("submit", "Submit!") )
            .addItem(new Reset ("Reset") )
            ;


        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateZonesBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update Zones Block")   )
            ;

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


        DynamicTable tab_buttons = new DynamicTable(2);
        tab_buttons
            .setBorder(0)
            .setCellSpacing(0)
            .setFrame(ITableFrame.BOX)
            ;


        rows = new TableRow();
        rows
            .addCell(new TableHeaderCell(close))
            .addCell(new TableHeaderCell(nextBlock))
            ;
        tab_buttons.addRow(rows.setIVAlign(IVAlign.TOP));



        // +------------------------------------------------------------+
        // | Create Update table                                        |
        // +------------------------------------------------------------+
        DynamicTable tab_update  = new DynamicTable(1);
        tab_update
            .setBorder(2)
            .setCellSpacing(2)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Update the area co-ordinates"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));


        rows = new TableRow();
        rows.addCell(new TableDataCell(existfrm));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Add closing buttons                                        |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell(tab_buttons));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));


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

    } // constructor UpdateOcAreasBlock


    /**
     * Update Ocean Area block
     * @param   pSurveyId
     * @param   pNewTrackChart
     */
    void updateOcareaBlock(String pSurveyId, String pNewLatNorth,
            String pNewLatSouth, String pNewLongWest, String pNewLongEast) {

        try {

            sql = "update inventory set lat_north = " + pNewLatNorth +
                  ", lat_south = " + pNewLatSouth +
                  ", long_west = " + pNewLongWest +
                  ", long_east = " + pNewLongEast +
                  " where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.out.println("<br>updateOcareaBlock: num = " + num);
            if (dbg) System.out.println("<br>updateOcareaBlock: sql = " + sql);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateOcareaBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updateOcareaBlock


    /**
     * Get track chart block
     * @param   pSurveyId
     */
    void getOcareaBlock(String pSurveyId) {

        try {

            sql = "select lat_north, lat_south, long_west, long_east " +
                  "from inventory " +
                  "where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
                int j = 1;
                pLatNorth = rset.getFloat(j++);
                pLatSouth = rset.getFloat(j++);
                pLongWest = rset.getFloat(j++);
                pLongEast = rset.getFloat(j++);
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process getOcareaBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try {

    } // getOcareaBlock


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

        HtmlHead hd = new HtmlHead("UpdateOcAreasBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateOcAreasBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateOcAreasBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdateOcAreasBlock
