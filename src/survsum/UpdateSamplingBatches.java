package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

/**
 * Update the sampling batches block
 *
 * @author  20070820 - SIT Group
 * @version
 * 19980512 - Neville Paynter - create class                            <br>
 * 20070820 - Ursula von St Ange - change to package class              <br>
 */
public class UpdateSamplingBatches extends CompoundItem {

    //boolean dbg = true;
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

    // input parameters
    String pSurveyId        = "";
    String pRecUpdated      = "";
    String pNewUpdated      = "";
    String pEBatchId        = "";
    String pEStartDateTime  = "";
    String pEEndDateTime    = "";

//    String recordErr        = "";
    String noEnd            = "";
//    String aSelRecnum       = "";
//    String convert          = "";
    String dtItem           = "";
    String sDate            = "";
    String sHour            = "";
    String sMinute          = "";
    String eDate            = "";
    String eHour            = "";
    String eMinute          = "";

    // database variables
    int     plBatchCode[];
    String  plBatchId[];
    String  plStartDateTime[];
    String  plEndDateTime[];
    int     plHours[];
    String  plDate[];

    // work variables
//    String  pDateStart      = "";
//    String  pDateEnd        = "";
    int     pGMTdiff        = 0;
    int     pBatchCount     = 0;
    String  submitTitle     = "";
    String  GMTstring       = "";

    String  dsDate          = "";
    String  dsHour          = "";
    String  dsMinute        = "";
    String  deDate          = "";
    String  deHour          = "";
    String  deMinute        = "";

    // +============================================================+
    // | void main(String args[])                                   |
    // +============================================================+
    // | MAIN PROGRAM                                               |
    // +------------------------------------------------------------+
    public UpdateSamplingBatches(String args[]) {

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
            this .addItem(new SimpleItem("There are no input parameters"));
            return;
        }  //  if (args.length < 1)

        // +------------------------------------------------------------+
        // | Get input paramaters                                       |
        // +------------------------------------------------------------+
        pSurveyId       = sc.getArgument(args,"surveyid");
        pRecUpdated     = sc.getArgument(args,"recUpdated");
        pNewUpdated     = sc.getArgument(args,"newUpdated");
        pEBatchId       = sc.getArgument(args,"batchId");
        pEStartDateTime = sc.getArgument(args,"existStartDateTime");
        pEEndDateTime   = sc.getArgument(args,"existEndDateTime");

//        recordErr       = sc.getArgument(args,"recorderr");
        noEnd           = sc.getArgument(args,"noend");
//        aSelRecnum      = sc.getArgument(args,"recnum");
        sDate           = sc.getArgument(args,"sdate");
        sHour           = sc.getArgument(args,"shour");
        sMinute         = sc.getArgument(args,"sminute");
        eDate           = sc.getArgument(args,"edate");
        eHour           = sc.getArgument(args,"ehour");
        eMinute         = sc.getArgument(args,"eminute");

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
        // | Create title table and add to the main table               |
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
        // | Create Update table                                        |
        // +------------------------------------------------------------+
        DynamicTable tab_update  = new DynamicTable(2);
        tab_update
            .setBorder(2)
            .setCellSpacing(2)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        // +------------------------------------------------------------+
        // | Create the buttons table                                   |
        // +------------------------------------------------------------+
        DynamicTable tab_buttons = new DynamicTable(1);
        tab_buttons
            .setBorder(0)
            .setCellSpacing(0)
            .setFrame(ITableFrame.BOX)
            ;

        // +------------------------------------------------------------+
        // | Create Moorings        table                               |
        // +------------------------------------------------------------+
        DynamicTable tab_list  = new DynamicTable(1);
        tab_list
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        DynamicTable tab_listbody = new DynamicTable(3);
        tab_listbody
            .setBorder(1)
            .setCellSpacing(1)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        // +------------------------------------------------------------+
        // | Add the sampling batch record if required                  |
        // +------------------------------------------------------------+
//        recordErr = "N";
        if (pNewUpdated.equals("Y")) {
            pEStartDateTime = sDate + " " + sHour + " " + sMinute;
            pEEndDateTime   = eDate + " " + eHour + " " + eMinute;
            if (dbg) System.out.println("<br>---pEStartDatetime--[" + pEStartDateTime);
            if (dbg) System.out.println("<br>---pEndDatetime--[" + pEEndDateTime);
            addSamplingBatchRecord(pSurveyId, pEBatchId,
                pEStartDateTime, pEEndDateTime);
        } // if (pNewUpdated.length() > 0)

        // +------------------------------------+
        // | Count the number batch    records  |
        // +------------------------------------+
        int batchCount = countSamplingBatches(pSurveyId);
        plBatchCode     = new int   [batchCount];
        plBatchId       = new String[batchCount];
        plStartDateTime = new String[batchCount];
        plEndDateTime   = new String[batchCount];
        plHours         = new int   [batchCount];

        // +------------------------------------+
        // | Count the number survey dates      |
        // +------------------------------------+
        int datesCount = countSurveyDates(pSurveyId);
        plDate          = new String[datesCount];

        if (dbg) System.out.println("<br>----datesCount--[" + datesCount);
        if (dbg) System.out.println("<br>-----batchCount--[" + batchCount);

        // +------------------------------------------------------------+
        // | Retrieve the Survey dates   from the packaged PL/SQL       |
        // | using procedure list_suvey_dates                           |
        // +------------------------------------------------------------+
        listSurveyDates(pSurveyId);

        // +------------------------------------------------------------+
        // | Create a string describing the GMT value                   |
        // +------------------------------------------------------------+
        if (pGMTdiff >= 0) {
            GMTstring = "&nbsp;&nbsp;(GMT +" + pGMTdiff + ")";
        } else {
            GMTstring = "&nbsp;&nbsp;(GMT " + pGMTdiff + ")";
        }  //  if (GTMdiff.intValue() >= 0)

        if (batchCount > 0) {
            // +------------------------------------------------------------+
            // | Retrieve the batch     data from the packaged PL/SQL       |
            // | using procedure list_sampling_batches                      |
            // +------------------------------------------------------------+
            listSamplingBatches(pSurveyId);

            rows = new TableRow();
            rows.addCell(new TableDataCell(sc.formatStrBig("<b><u>Sampling Batches</u></b>")).setColSpan(80));
            tab_list.addRow(rows);

            rows = new TableRow();
            rows
                .addCell(new TableDataCell(sc.formatStrBig("Batch Id")))
                .addCell(new TableDataCell(sc.formatStrBig("Start")))
                .addCell(new TableDataCell(sc.formatStrBig("End")))
                .addCell(new TableDataCell(sc.formatStrBig("Hours")))
                ;
            tab_listbody.addRow(rows);

            // +------------------------------------------------------------+
            // | Process each batch     record                              |
            // +------------------------------------------------------------+
            for (int i = 0; i < batchCount; i++) {
                int recnum = i + 1;
                rows = new TableRow();
                rows
                    .addCell(new TableDataCell(sc.formatStr("<b><FONT COLOR=#3333FF>" + plBatchId[i] + "</font></b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plStartDateTime[i] + GMTstring + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + plEndDateTime[i] + GMTstring + "</b>" )))
                    ;
                if (plHours[i] <= 0.0) {
                    rows.addCell(new TableDataCell(sc.formatStr("<b><FONT COLOR=#FF0000>" + plHours[i] + " - ERROR</font></b>" )));
                } else {
                    rows.addCell(new TableDataCell(sc.formatStr("<b>" + plHours[i] + "</b>" )));
                }  //  if (plHours <= 0.0)

                tab_listbody.addRow(rows.setIVAlign(IVAlign.TOP));
            }  //  for (int i = 0; i < batchCount; i++)

            rows = new TableRow();
            rows.addCell(new TableDataCell(tab_listbody).setVAlign(IVAlign.TOP));
            tab_list.addRow(rows);

        } // if (batchCount > 0)

        // +------------------------------------------------------------+
        // | Create date time select lists                              |
        // +------------------------------------------------------------+
        Select selectBatchId  = new Select("batchId");
        Select selectSDate    = new Select("sdate");
        Select selectSHour    = new Select("shour");
        Select selectSMinute  = new Select("sminute");
        Select selectEDate    = new Select("edate");
        Select selectEHour    = new Select("ehour");
        Select selectEMinute  = new Select("eminute");

        // +------------------------------------------------------------+
        // | Batch select listing                                       |
        // +------------------------------------------------------------+
        char cBatch = 'A';
        boolean firstNew = true;
        for (int batch = 0; batch < 10; batch++) {
            boolean used = false;
            dtItem = String.valueOf(cBatch);

            // +------------------------------------------------------------+
            // | Compare with existing Batch Ids to see if already used     |
            // +------------------------------------------------------------+
            for (int batchNo = 0; batchNo < batchCount; batchNo++) {
                if (dtItem.equals(plBatchId[batchNo])) {
                    used = true;
                }  //  if (dtItem.equals(plBatchId[batchNo])
            } // for (int batchNo = 0; batchNo < batchCount; batchNo++)

            // +------------------------------------------------------------+
            // | If the Batch Id is already used, the selection is marked   |
            // | edit. If not it is marked as new.                          |
            // +------------------------------------------------------------+
            if (used) {
                selectBatchId.addOption(new Option(dtItem + " - Edit",dtItem,false));
            } else {
                if (firstNew) {
                    firstNew = false;
                    selectBatchId.addOption(new Option(dtItem + " - New",dtItem,true));
                } else {
                    selectBatchId.addOption(new Option(dtItem + " - New",dtItem,false));
                }  //  if (firstNew)
            }  //  if (!used)

            cBatch++;
        } // for (int batch = 0; batch < 10; batch++)

        // +------------------------------------------------------------+
        // | create default select items                                |
        // +------------------------------------------------------------+
        if (dbg) System.out.println("<br>--eDate---" + eDate);
        if (!eDate.equals(" ")) {
            dsDate   = eDate    ;
            dsHour   = eHour    ;
            dsMinute = eMinute  ;
            deDate   = eDate    ;
            deHour   = eHour    ;
            deMinute = eMinute  ;
        } else if ((sDate.length() > 1) && noEnd.equals("")) {
            dsDate   = sDate    ;
            dsHour   = sHour    ;
            dsMinute = sMinute  ;
            deDate   = sDate    ;
            deHour   = sHour    ;
            deMinute = sMinute  ;
        } // if (!eDate.equals(" "))

        for (int i = 0; i < datesCount; i++) {
            dtItem = plDate[i];
            selectSDate.addOption(new Option(dtItem,dtItem,sc.getDefault(dtItem,dsDate)));
            selectEDate.addOption(new Option(dtItem,dtItem,sc.getDefault(dtItem,deDate)));
        }  //  for (int i = 0; i < datesCount; i++)

        for (int i = 100; i < 124; i++) {
            dtItem = String.valueOf(i).substring(1,3);
            selectSHour.addOption(new Option(dtItem,dtItem,sc.getDefault(dtItem,dsHour)));
            selectEHour.addOption(new Option(dtItem,dtItem,sc.getDefault(dtItem,deHour)));
        } // for (int i = 100; i < 124; i++)

        for (int i = 100; i < 160; i++) {
            dtItem = String.valueOf(i).substring(1,3);
            selectSMinute.addOption(new Option(dtItem,dtItem,sc.getDefault(dtItem,dsMinute)));
            selectEMinute.addOption(new Option(dtItem,dtItem,sc.getDefault(dtItem,deMinute)));
        } // for (int i = 100; i < 160; i++)

        // +------------------------------------------------------------+
        // | Create the form to Add new sampling batch record           |
        // +------------------------------------------------------------+
        Form newFrm = new Form("GET", "SurvSum");
        newFrm
            .addItem(new Hidden("pscreen", "UpdateSamplingBatches"))
//            .addItem(new Hidden("recorderr", recordErr))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("recnum", "0"))
            .addItem(new SimpleItem("Batch ID: "))
            .addItem(selectBatchId)
            .addItem(new SimpleItem("<br>Start Date Time: "))
            .addItem(selectSDate)
            .addItem(new SimpleItem(" "))
            .addItem(selectSHour)
            .addItem(new SimpleItem(":"))
            .addItem(selectSMinute)
            .addItem(new SimpleItem(GMTstring))
            ;

        // +------------------------------------------------------------+
        // | This is not processed in the first round.                  |
        // +------------------------------------------------------------+
        submitTitle = "Get Date Time End entry form";
        if (noEnd.equals("")) {
            submitTitle = "Submit new data record";
            newFrm
                .addItem(new Hidden("newUpdated", "Y"))
                .addItem(new SimpleItem("<br> End Date Time: "))
                .addItem(selectEDate)
                .addItem(new SimpleItem(" "))
                .addItem(selectEHour)
                .addItem(new SimpleItem(":"))
                .addItem(selectEMinute)
                .addItem(new SimpleItem(GMTstring))
                ;
        }  //  if (noEnd.equals("")

        // +------------------------------------------------------------+
        // | Produce buttons to submit or reset                         |
        // +------------------------------------------------------------+
        newFrm
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", submitTitle) )
            .addItem(new Reset   ("Reset") )
            ;

        rows = new TableRow();
        rows.addCell(new TableHeaderCell("Add/Edit Sampling Batch"));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        rows = new TableRow();
        rows.addCell(new TableDataCell(newFrm));
        tab_update.addRow(rows.setIVAlign(IVAlign.TOP));


        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateStationsBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update Sampling Stations")   )
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

        rows = new TableRow();
        rows.addCell(new TableHeaderCell(close));
        rows.addCell(new TableHeaderCell(nextBlock));
        tab_buttons.addRow(rows.setIVAlign(IVAlign.TOP));

        // +------------------------------------------------------------+
        // | Add Title table to the Main table                          |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        if (batchCount > 0) {
            // +------------------------------------------------------------+
            // | Add List  table to the Main table                          |
            // +------------------------------------------------------------+
            rows = new TableRow();
            rows.addCell(new TableDataCell(tab_list).setVAlign(IVAlign.TOP));
            tab_main.addRow(rows);
        }  //  if (batchCount > 0)

        // +------------------------------------------------------------+
        // | Add Update table   the Main table                          |
        // +------------------------------------------------------------+
        //rows = new TableRow();
        //rows.addCell(new TableDataCell(tab_update).setVAlign(IVAlign.TOP));
        //tab_main.addRow(rows);

        // +------------------------------------------------------------+
        // | Add closing buttons to the main table                      |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableHeaderCell(tab_buttons));
        tab_update.addRow(rows.setBackgroundColor(Color.cyan));

        // +------------------------------------------------------------+
        // | Add main table to the page                                 |
        // +------------------------------------------------------------+
        this.addItem(tab_main.setCenter());
        this.addItem(tab_update.setCenter());

        // +------------------------------------------------------------+
        // | Close down the Oracle database session                     |
        // +------------------------------------------------------------+
        try {
            if (dbg) System.out.println("Before conn.close()");
            //conn.close();
            sc.disConnect(stmt, rset, conn);
            if (dbg) System.out.println("After conn.close()");
        }  catch (Exception e){}

    } // constructor UpdateSamplingBatches


    /**
     * Add new Institute block
     * @param   pSurveyId            surveyId of cruise
     * @param   pEBatchId
     * @param   pEStartDateTime
     * @param   pEEndDateTime
     */
    void addSamplingBatchRecord(String pSurveyId, String pEBatchId,
            String pEStartDateTime, String pEEndDateTime) {

        int pGMTDiff = 0;

        try {

            // get GMT difference
            sql = "select GMT_diff from inventory " +
                  "where survey_id = '" + pSurveyId + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pGMTDiff = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            //are there any batches?
            sql = "select count(*) from sampling_batch " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "and batch_id = '" + pEBatchId + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pBatchCount = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            if (pBatchCount == 0) {

                // get max institute code
                sql = "select max(code) from sampling_batch";
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                int code = 0;
                while (rset.next()) {
                    code = (int)(rset.getFloat(1)) ;
                } //while (rset.next())
                rset.close();
                stmt.close();
            rset = null;
            stmt = null;

                // insert into sampling_batch
                code++;
                sql = "insert into sampling_batch values (" + code + ", '" +
                      pEBatchId + "', '" + pSurveyId + "', "+
                      "to_timestamp('" + pEStartDateTime + "','YYYY-MM-DD HH24 MI') - " +
                          pGMTDiff / 24 + ", " +
                      "to_timestamp('" + pEEndDateTime + "','YYYY-MM-DD HH24 MI') - " +
                          pGMTDiff / 24 + ")";
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

            } else {

                // update sampling_batch
                sql = "update sampling_batch set start_date_time = " +
                      "to_timestamp('" + pEStartDateTime + "','YYYY-MM-DD HH24 MI') - " +
                      (pGMTDiff / 24f) + ", " +
                      "end_date_time = " +
                      "to_timestamp('" + pEEndDateTime + "','YYYY-MM-DD HH24 MI') - " +
                      (pGMTDiff / 24f) + " " +
                      "where survey_id = '" + pSurveyId + "' " +
                      "and batch_id = '" + pEBatchId + "'";
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;

            } // if (pBatchCount > 0)

            // +------------------------------------------------------------+
            // | Freeze GMT field to prevent any further GMT changes        |
            // +------------------------------------------------------------+
            sql = "update inventory set GMT_freeze = 'Y' " +
                  "where survey_id = '" + pSurveyId + "'";
            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process addSamplingBatchRecord: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // addSamplingBatchRecord


    /**
     * Get the number of sampling batches for survey
     * @param   pSurveyId
     * @returns number of institutes
     */
    int countSamplingBatches(String pSurveyId) {

        int count = 0;

        try {

            sql = "select count(*) from sampling_batch " +
                  "where survey_id = '" + pSurveyId + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                count = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process countSamplingBatches: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return count;

    } // countSamplingBatches


    /**
     * Get the number of dates covered by the survey
     * @param   pSurveyId
     * @returns number of dates
     */
    int countSurveyDates(String pSurveyId) {

        int count = 0;
        try {

            sql = "select date_end - date_start from inventory " +
                  "where survey_id = '" + pSurveyId + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                count = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            count++;
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process countSurveyDates: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return count;

    } // countSurveyDates


    /**
     * List all sampling batches for a survey
     * @param   pSurveyId
     */
    void listSurveyDates(String pSurveyId) {

        String    tmpDate = "";
        Timestamp dateStart = null;
        Timestamp dateEnd = null;

        try {

            // get GMT difference
            sql = "select date_start, date_end, GMT_diff from inventory " +
                  "where survey_id = '" + pSurveyId + "'";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                int j = 1;
                dateStart   = Timestamp.valueOf(rset.getString(j++));
                dateEnd     = Timestamp.valueOf(rset.getString(j++));
                pGMTdiff    = (int)(rset.getFloat(j++));
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            if (dateStart != null) {
                GregorianCalendar calDate = new GregorianCalendar();
                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
                calDate.setTime(dateStart);

                int i = 0;
                plDate[i] = formatter.format(calDate.getTime());
                while (!dateStart.equals(dateEnd)) {
                    i++;
                    plDate[i] = formatter.format(calDate.getTime());
                    calDate.add(GregorianCalendar.DATE, 1);
                    dateStart = new Timestamp(calDate.getTime().getTime());
                    //dateStart = (Timestamp)calDate.getTime();
                } // while (!dateStart.equals(dateEnd))
            } // if (dateStart != null)

        } catch (Exception e) {
            this.addItem("<br>Could not process listSurveyDates: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listSurveyDates


    /**
     * List all sampling batches for a survey
     * @param   pSurveyId
     */
    void listSamplingBatches(String pSurveyId) {

        try {

            // get GMT difference
            int GMTDiff = 0;
            sql = "select GMT_diff from inventory " +
                  "where survey_id = '" + pSurveyId + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                GMTDiff = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            // get the sampling batches
            sql = "select code, batch_id, " +
                  "to_char(start_date_time + " +
                    (GMTDiff / 24) + ", 'YYYY-MM-DD HH24:MI'), " +
                  "to_char(end_date_time + " +
                    (GMTDiff / 24) + ", 'YYYY-MM-DD HH24:MI'), " +
                  "(end_date_time - start_date_time) * 24 " +
                  "from sampling_batch " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "order by batch_id ";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                int j = 1;
                plBatchCode[i]      = (int)rset.getFloat(j++);
                plBatchId[i]        = sc.spaceIfNull(rset.getString(j++));
                plStartDateTime[i]  = sc.spaceIfNull(rset.getString(j++));
                plEndDateTime[i]    = sc.spaceIfNull(rset.getString(j++));
                plHours[i]          = (int)rset.getFloat(j++);
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listSamplingBatches: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listSamplingBatches


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
//    } // formatStr


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
     * Identify the select item that is to be flagged as default
     * @param    item
     * @param    prevSelect
     * @return   true/false
     */
//    private boolean getDefault(String item, String prevSelect) {
//        return (item.equals(prevSelect) ? true : false);
//    } // getDefault


    /**
     * main class for testing purposes
     * @param   args    list of URL name-value parameters
     */
    public static void main(String args[]) {

        HtmlHead hd = new HtmlHead("UpdateSamplingBatches local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateSamplingBatches(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateSamplingBatches: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdateSamplingBatches