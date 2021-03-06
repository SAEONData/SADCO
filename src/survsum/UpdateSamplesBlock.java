package survsum;

import oracle.html.*;
//import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
* Update samples
*
* @author  20070822 - SIT Group
* @version
* 19980512 - Neville Paynter - create class                            <br>
* 20070822 - Ursula von St Ange - change to package class              <br>
*/
public class UpdateSamplesBlock extends CompoundItem {

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
    String  samplesErr      = "";
    int     selRecnum       = -1;
    String  pRecUpdated     = "";
    String  pNewUpdated     = "";
    String  pESamplesCode   = "";
    String  pEPiCode        = "";
    String  pETypeCode      = "";
    String  pESampleNumber  = "";
    String  pESampleUnit    = "";
    String  pERemarks       = "";

    // work variables
    int     pSamplesCodeS[];
    String  pPiCodeS    [];
    String  pTypeCodeS  [];
    String  pTypeS      [];
    int     pSampleNumber[];
    String  pSampleUnit [];
    String  pRemarksS   [];

    String  plPiCode[]  = new String[6];
    int     plSciCode[] = new int[6];
    String  plSurname[] = new String[6];
    String  plFName[]   = new String[6];
    String  plTitle[]   = new String[6];

    String  plTypeCode[];
    String  plGroupName[];
    String  plTypeName[];
    int     pSamplesCount      = 0;


   // +============================================================+
   // | void main(String args[])                                   |
   // +============================================================+
   // | MAIN PROGRAM                                               |
   // +------------------------------------------------------------+
   public UpdateSamplesBlock(String args[]) {

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
        samplesErr      = sc.getArgument(args,"sampleserr");
        selRecnum       = Integer.parseInt(sc.getArgument(args,"recnum")) - 1;

        pRecUpdated     = sc.getArgument(args,"recUpdated");
        pNewUpdated     = sc.getArgument(args,"newUpdated");
        pESamplesCode   = sc.getArgument(args,"samplesCode");
        pEPiCode        = sc.getArgument(args,"existPiPcode");
        pETypeCode      = sc.getArgument(args,"existDTCode");
        pESampleNumber  = sc.getArgument(args,"existSampleNumber");
        pESampleUnit    = sc.getArgument(args,"existSampleUnit");
        pERemarks       = sc.getArgument(args,"remarks");

        // +------------------------------------------------------------+
        // | Retrieve the scientist names    from the packaged PL/SQL   |
        // | using procedure list_all_scientists                        |
        // +------------------------------------------------------------+
        listSurveyInvestigators(pSurveyId);

        // +-------------------------------------+
        // | Count the number data type records  |
        // +-------------------------------------+
        int dataTypesCount = countDataTypes();

        // +------------------------------------------------------------+
        // | Build the empty arrays for holding data type     data      |
        // +------------------------------------------------------------+
        plTypeCode     = new String[dataTypesCount];
        plGroupName    = new String[dataTypesCount];
        plTypeName     = new String[dataTypesCount];

        // +------------------------------------------------------------+
        // | Retrieve the data type data from the packaged PL/SQL       |
        // | using procedure get_chiefsc_block                          |
        // +------------------------------------------------------------+
        listAllDataTypes();

        // +------------------------------------------------------------+
        // | Update the samples   record of required                    |
        // +------------------------------------------------------------+
        if (pRecUpdated.length() > 0) {
            updateSamplesBlock(pESamplesCode, pSurveyId, pEPiCode, pETypeCode,
                pESampleNumber, pESampleUnit, pERemarks);
        } // if (pExistUpdated.length() > 0)

        // +------------------------------------------------------------+
        // | Add    the samples   record if required                    |
        // +------------------------------------------------------------+
        samplesErr = "N";
        if (pNewUpdated.length() > 0) {
            addSamplesBlock(pSurveyId, pEPiCode, pETypeCode,
                pESampleNumber, pESampleUnit, pERemarks);
            if (pSamplesCount == 1) samplesErr = "Y";
        }  //  if (pExistUpdated.length() > 0)

        // +---------------------------------+
        // | Count the number sample records |
        // +---------------------------------+
        int sampleCount = countSamples(pSurveyId);

        // +------------------------------------------------------------+
        // | Build the empty arrays for holding investigators data      |
        // +------------------------------------------------------------+
        pSamplesCodeS  = new int   [sampleCount];
        pPiCodeS       = new String[sampleCount];
        pTypeCodeS     = new String[sampleCount];
        pTypeS         = new String[sampleCount];
        pSampleNumber  = new int   [sampleCount];
        pSampleUnit    = new String[sampleCount];
        pRemarksS      = new String[sampleCount];

        // +------------------------------------------------------------+
        // | Create the select lists of sample records                  |
        // +------------------------------------------------------------+
        Select selectRecList = new Select("recnum");
        for (int i = 0; i < sampleCount; i++) {
            String selVar = String.valueOf(i + 1);
            selectRecList.addOption(new Option(selVar,selVar,false));
        }  //  for (int i = 0; i < sampleCount; i++)

        // +------------------------------------------------------------+
        // | Retrieve the samples   data from the packaged PL/SQL       |
        // | using procedure list_samples                               |
        // +------------------------------------------------------------+
        if (sampleCount > 0) {
            listSamples(pSurveyId);
        }  //  if (sampleCount > 0)


        // +------------------------------------------------------------+
        // | Create the select lists of survey Investigators            |
        // +------------------------------------------------------------+
        Select selectInvList = new Select("existPiPcode");

        // +------------------------------------------------------------+
        // | Include the current PI as default it the record is to be   |
        // | updated                                                    |
        // +------------------------------------------------------------+
        if (selRecnum >= 0) {
            selectInvList.addOption(new Option(
                pPiCodeS[selRecnum], //+ " - " +
//                pSurnameP[i].trim() + ", " +
//                pFNameS[i],
                pPiCodeS[selRecnum],false));
        } else {
            selectInvList.addOption(new Option(" "," ",false));
        } // if (selRecnum >= 0)


        for (int i = 0; i < 6; i++) {
            //if (plSurname[i].length() == 0) break;
            if (plSurname[i] == null) break;
            selectInvList.addOption(new Option(
                plPiCode[i] + " - " + plSurname[i].trim() + ", " + plFName[i],
                plPiCode[ i],false));
        } // for (int i = 0; i < 6; i++)

        // +------------------------------------------------------------+
        // | Create the select lists of data types                      |
        // +------------------------------------------------------------+
        Select selectDTList = new Select("existDTCode");

        // +------------------------------------------------------------+
        // | Include the current data type as default if the record is  |
        // | to be updated                                              |
        // +------------------------------------------------------------+
        if (selRecnum >= 0) {
            selectDTList.addOption(new Option(
                pTypeCodeS[selRecnum] + " - " +
//                pGroupName[selRecnum].trim() + " - " +
                pTypeS[selRecnum].trim(),
                pTypeCodeS[selRecnum],false));
        } else {
            selectDTList.addOption(new Option(" "," ",false));
        } // if (selRecnum >= 0)


        for (int i = 0; i < dataTypesCount; i++) {
            selectDTList.addOption(new Option(
                plTypeCode[i] + " - " +
                plGroupName[i].trim() + " - " +
                plTypeName[i].trim(),
                plTypeCode[i],false));
        }  //  for (int i = 0; i < sciCount; i++)

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
        // | Create title table                                         |
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
        // | Create samples         table                               |
        // +------------------------------------------------------------+
        DynamicTable tab_samp  = new DynamicTable(2);
        tab_samp
            .setBorder(0)
            .setCellSpacing(0)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        DynamicTable tab_samp1 = new DynamicTable(5);
        tab_samp1
            .setBorder(1)
            .setCellSpacing(1)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        rows = new TableRow();
        rows.addCell(new TableDataCell(sc.formatStrBig(
            "<b><u>Summary of Measurements and Samples Taken</u></b>")).setColSpan(80));
        tab_samp.addRow(rows);

        // +-----------------------------------+
        // | Only process if there are records |
        // +-----------------------------------+
        if (sampleCount > 0) {
            rows = new TableRow();
            rows
                .addCell(new TableDataCell(sc.formatStrBig("Rec.<br>No.")))
                .addCell(new TableDataCell(sc.formatStrBig("Invstgr<br>Code")))
                .addCell(new TableDataCell(sc.formatStrBig("Number")))
                .addCell(new TableDataCell(sc.formatStrBig("Units")))
                .addCell(new TableDataCell(sc.formatStrBig("Type<br>Code")))
                .addCell(new TableDataCell(sc.formatStrBig("Type")))
                .addCell(new TableDataCell(sc.formatStrBig("Remarks")))
                ;
            tab_samp1.addRow(rows);

            // +--------------------------------+
            // | Process each sample    record  |
            // +--------------------------------+
            for (int i = 0; i < sampleCount; i++) {
                int recnum = i + 1;
                rows = new TableRow();
                rows
                    .addCell(new TableDataCell(sc.formatStr("<b>" + recnum + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b><FONT COLOR=#3333FF>" + pPiCodeS[i] + "</font></b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pSampleNumber[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pSampleUnit[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pTypeCodeS[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pTypeS[i] + "</b>" )))
                    .addCell(new TableDataCell(sc.formatStr("<b>" + pRemarksS[i] + "</b>" )))
                    ;
                tab_samp1.addRow(rows.setIVAlign(IVAlign.TOP));
            }  //  for (int i = 0; i < sampleCount; i++)

            rows = new TableRow();
            rows.addCell(new TableDataCell(tab_samp1).setVAlign(IVAlign.TOP));
            tab_samp.addRow(rows);
        }  //  if (sampleCount > 0)



        // +------------------------------------------------------------+
        // | Insert the information panel                               |
        // +------------------------------------------------------------+
        rows = new TableRow();
        rows.addCell(new TableDataCell(tab_titl).setVAlign(IVAlign.TOP));
        tab_main.addRow(rows);

        // +------------------------------------------------------------+
        // | Print table only if there is data                          |
        // +------------------------------------------------------------+
        if (sampleCount > 0) {
            rows = new TableRow();
            rows.addCell(new TableDataCell(tab_samp).setVAlign(IVAlign.TOP));
            tab_main.addRow(rows);
        }  //  if (positionCount > 0)

        this.addItem(tab_main.setCenter());


        // +------------------------------------------------------------+
        // | Create the form to update  samples                         |
        // +------------------------------------------------------------+
        Form existfrm = new Form("GET", "SurvSum");
        existfrm
            .addItem(new Hidden("pscreen", "UpdateSamplesBlock"))
            .addItem(new Hidden("existUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))

            .addItem(new SimpleItem("Select record no.: "))
            .addItem(selectRecList)

            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Get Record") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Create the form to update  samples                         |
        // +------------------------------------------------------------+
        Form updfrm = new Form("GET", "SurvSum");
        if (selRecnum >= 0) {
            updfrm
                .addItem(new Hidden("pscreen", "UpdateSamplesBlock"))
                .addItem(new Hidden("recUpdated", "Y"))
                .addItem(new Hidden("surveyid", pSurveyId))
                .addItem(new Hidden("recnum", "0"))
                .addItem(new Hidden("samplesCode", String.valueOf(pSamplesCodeS[selRecnum])))

                .addItem(new SimpleItem("<b>Record No.: " + (selRecnum+1)))
                .addItem(new SimpleItem("<br>Investigator: "))
                .addItem(selectInvList)
                .addItem(new SimpleItem("<br>Number: "))
                .addItem(new TextField("existSampleNumber", 10, 10, String.valueOf(pSampleNumber[selRecnum])))
                .addItem(new SimpleItem("<br>Units: "))
                .addItem(new TextField("existSampleUnit", 10, 10, pSampleUnit[selRecnum]))
                .addItem(new SimpleItem("<br>Data Types: "))
                .addItem(selectDTList)
                .addItem(new SimpleItem("<br>Remarks: "))
                .addItem(new TextField("remarks", 40, 40, pRemarksS[selRecnum]))
                ;

        }  //  if (aSelRecnum.length() > 0)

        // +------------------------------------------------------------+
        // | Produce buttons to submit or reset                         |
        // +------------------------------------------------------------+
        updfrm
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit updated record") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Create the form to Add new moorings                        |
        // +------------------------------------------------------------+
        Form newfrm = new Form("GET", "SurvSum");
        newfrm
            .addItem(new Hidden("pscreen", "UpdateSamplesBlock"))
            .addItem(new Hidden("newUpdated", "Y"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Hidden("sampleserr", samplesErr))
            .addItem(new Hidden("recnum", "0"))

            .addItem(new SimpleItem("Investigator: "))
            .addItem(selectInvList)
            .addItem(new SimpleItem("<br>Number: "))
            .addItem(new TextField("existSampleNumber", 10, 10, " "))
            .addItem(new SimpleItem("<br>Units: "))
            .addItem(new TextField("existSampleUnit", 10, 10, " "))
            .addItem(new SimpleItem("<br>Data Types: "))
            .addItem(selectDTList)
            .addItem(new SimpleItem("<br>Remarks: "))
            .addItem(new TextField("remarks", 40, 40, " "))
            ;

        // +------------------------------------------------------------+
        // | Display record duplicate error if there is a duplicate     |
        // +------------------------------------------------------------+
        if (samplesErr.equals("Y")) {
            newfrm .addItem(new SimpleItem(
                "<br><FONT COLOR=#FF6666>ERR: Duplicate Record</font><br>").setBold()   );
        }  //  if (sampleonsErr.equals("Y"))

        // +------------------------------------------------------------+
        // | Produce buttons to submit or reset                         |
        // +------------------------------------------------------------+
        newfrm
            .addItem(new SimpleItem("<br><br>"))
            .addItem(new Submit  ("submit", "Submit new record") )
            .addItem(new Reset   ("Reset") )
            ;

        // +------------------------------------------------------------+
        // | Go to next block                                           |
        // +------------------------------------------------------------+
        Form nextBlock = new Form("GET", "SurvSum");
        nextBlock
            .addItem(new Hidden("pscreen", "UpdateTrackChBlock"))
            .addItem(new Hidden("surveyid", pSurveyId))
            .addItem(new Submit("submit", ">>> Update Track Chart Block")   )
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
        DynamicTable tab_update  = new DynamicTable(2);
        tab_update
            .setBorder(2)
            .setCellSpacing(2)
            .setWidth("100%")
            .setFrame(ITableFrame.BOX)
            ;

        // +------------------------------------------------------------+
        // | Only print title if there is data to change                |
        // +------------------------------------------------------------+
        if (sampleCount > 0) {
            rows = new TableRow();
            rows.addCell(new TableHeaderCell("Change Samples Records"));
            tab_update.addRow(rows.setBackgroundColor(Color.cyan));
        }  //  if (sampleCount > 0)

        rows = new TableRow();

        // +------------------------------------------------------------+
        // | If a record has been chosen for updating, display the      |
        // | update record panel, otherwise display the panels to       |
        // | select a record to update, or add a new record             |
        // +------------------------------------------------------------+
        if (selRecnum >= 0) {
            rows.addCell(new TableDataCell(updfrm));
        } else {
            // +------------------------------------------------------------+
            // | print sample      selection box only if there are records  |
            // +------------------------------------------------------------+
            if (sampleCount > 0) {
                rows.addCell(new TableDataCell(existfrm));
                tab_update.addRow(rows.setIVAlign(IVAlign.TOP));
            }  //  if (sampleCount > 0)

            rows = new TableRow();
            rows.addCell(new TableHeaderCell("Add Samples Records"));
            tab_update.addRow(rows.setBackgroundColor(Color.cyan));

            rows = new TableRow();
            rows.addCell(new TableDataCell(newfrm));
        }  //  if (selRecnum >= 0)

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

    } // constructor UpdateSamplesBlock


    /**
     * List investigators for selected survey
     * @param   pSurveyId
     */
    void listSurveyInvestigators(String pSurveyId) {

        try {

            sql = "select pi_code, investigators.sci_code, surname, " +
                  "f_name, title from investigators, scientists " +
                  "where survey_id = '" + pSurveyId + "' and " +
                  "investigators.sci_code = scientists.code " +
                  "order by pi_code";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {

                int j = 1;
                plPiCode[i]  = sc.spaceIfNull(rset.getString(j++));
                plSciCode[i] = (int)rset.getFloat(j++);
                plSurname[i] = sc.spaceIfNull(rset.getString(j++));
                plFName[i]   = sc.spaceIfNull(rset.getString(j++));
                plTitle[i]   = sc.spaceIfNull(rset.getString(j++));
                i++;

            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listSurveyInvestigators: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // listSurveyInvestigators


    /**
     * Get the number of data types
     * @returns number of data types
     */
    int countDataTypes() {

        int count = 0;

        try {

            sql = "select count(*) from data_types";

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
            this.addItem("<br>Could not process countDataTypes: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return count;

    } // countDataTypes


    /**
     * List all data types
     */
    void listAllDataTypes() {

        try {

            sql = "select data_types.code, data_groups.name, data_types.name " +
                  "from data_types, data_groups where " +
                  "data_types.group_code = data_groups.code " +
                  "order by data_types.code";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                int j = 1;
                plTypeCode[i]  = sc.spaceIfNull(rset.getString(j++));
                plGroupName[i] = sc.spaceIfNull(rset.getString(j++));
                plTypeName[i]  = sc.spaceIfNull(rset.getString(j++));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listAllDataTypes: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listAllDataTypes


    /**
     * Update samples record
     * @param   pESamplesCode
     * @param   pSurveyId
     * @param   pEPiCode
     * @param   pETypeCode
     * @param   pESampleNumber
     * @param   pESampleUnit
     * @param   pERemarks
     */
    void updateSamplesBlock(String pESamplesCode, String pSurveyId,
            String pEPiCode, String pETypeCode, String pESampleNumber,
            String pESampleUnit, String pERemarks) {
        try {

            sql = "update samples set survey_id = '" + pSurveyId +
                "', pi_code = '" + pEPiCode + "', type_code = '" + pETypeCode +
                "', sample_number = " + pESampleNumber +
                ", sample_units = '" + pESampleUnit +
                "', remarks = '" + pERemarks + "' where code = " + pESamplesCode;

            stmt = conn.createStatement();
            int num = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;

            if (dbg) System.out.println("<br>updateSamplesBlock: num = " + num);
            if (dbg) System.out.println("<br>updateSamplesBlock: sql = " + sql);

        } catch (Exception e) {
            this.addItem("<br>Could not process updateSamplesBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // updateSamplesBlock


    /**
     * Add new samples record
     * @param   pSurveyId
     * @param   pEPiCode
     * @param   pETypeCode
     * @param   pESampleNumber
     * @param   pESampleUnit
     * @param   pERemarks
     */
     void addSamplesBlock(String pSurveyId, String pEPiCode, String pETypeCode,
            String pESampleNumber, String pESampleUnit, String pERemarks) {

        pSamplesCount = 0;

        try {

            // is the record there?
            sql = "select count(*) from samples " +
                  "where survey_id = '" + pSurveyId + "' " +
                  "and pi_code = '" + pEPiCode + "' " +
                  "and type_code =  '" + pETypeCode + "' " +
                  "and sample_number = " + pESampleNumber + " " +
                  "and sample_units = '" + pESampleUnit + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                pSamplesCount = (int)(rset.getFloat(1)) ;
            } //while (rset.next()) {
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

            if (pSamplesCount == 0) {

                // get code of next record
                int code = 0;
                sql = "select max(code) from samples";
                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);
                while (rset.next()) {
                    code = (int)(rset.getFloat(1)) ;
                } //while (rset.next()) {
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

                // insert record
                code++;
                sql = "insert into samples values (" + code + ", '" +
                    pSurveyId + "', '" + pEPiCode + "', " +
                    pESampleNumber + ", '" + pESampleUnit + "', '" +
                    pETypeCode + "', null, '" + pERemarks + "')";
                stmt = conn.createStatement();
                int num = stmt.executeUpdate(sql);
                stmt.close();
                stmt = null;
                if (dbg) System.out.println("<br>addSamplesBlock: num = " + num);
                if (dbg) System.out.println("<br>addSamplesBlock: sql = " + sql);

            } // if (pPositionsCount == 0)

        } catch (Exception e) {
            this.addItem("<br>Could not process addSamplesBlock: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

    } // addSamplesBlock


    /**
     * Get the number of samples
     * @returns number of data types
     */
    int countSamples(String pSurveyId) {

        int count = 0;

        try {

            sql = "select count(*) from samples " +
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
            this.addItem("<br>Could not process countSamples: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try-catch

        return count;

    } // countSamples


    /**
     * List samples
     */
    void listSamples(String pSurveyId) {

        try {

            sql = "select samples.code, pi_code, " +
                  "sample_number, sample_units, samples.type_code, " +
                  "name, remarks " +
                  "from samples, data_types " +
                  "where survey_id = '" + pSurveyId + "' and " +
                  "data_types.code =  samples.type_code " +
                  "order by upper(pi_code)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int i = 0;
            while (rset.next()) {
                int j = 1;
                pSamplesCodeS[i]  = (int)rset.getFloat(j++);
                pPiCodeS[i]       = sc.spaceIfNull(rset.getString(j++));
                pSampleNumber[i]  = (int)rset.getFloat(j++);
                pSampleUnit[i]    = sc.spaceIfNull(rset.getString(j++));
                pTypeCodeS[i]     = sc.spaceIfNull(rset.getString(j++));
                pTypeS[i]         = sc.spaceIfNull(rset.getString(j++));
                pRemarksS[i]      = sc.spaceIfNull(rset.getString(j++));
                i++;
            } //while (rset.next()) {

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            this.addItem("<br>Could not process listSamples: " +
                e.getMessage());
            this.addItem("<br>sql = " + sql);
            e.printStackTrace();
        } // try - catch

    } // listSamples


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

        HtmlHead hd = new HtmlHead("UpdateSamplesBlock local");
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
        .setActivatedLinkColor   ("#ff3300")
        .setBackgroundColor      ("#fffff2")
        .setFollowedLinkColor    ("#000055")
        .setUnfollowedLinkColor  ("#cc0000")
        ;

        try {

            bd.addItem(new UpdateSamplesBlock(args));
            //common2.DbAccessC.close();
        } catch(Exception e) {
            bd.addItem("Error in UpdateSamplesBlock: " + e.getMessage());
            e.printStackTrace();
        } // try-catch

        hp.printHeader();
        hp.print();
    } // main

} // class UpdateSamplesBlock
