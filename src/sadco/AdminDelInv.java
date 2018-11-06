package sadco;
import oracle.html.*;

/**
 * This class delete's a Inventory from the database.  It's parameters are the
 * following:
 * <br>pscreen   - used by SadcoMRN program. Value must be 'admin'.
 * <br>pversion  - used by SadcoMRN program. Value must be 'delinv'.
 * <br>surveyId  - the ID of the survey that the station belongs to.
 * @author  SIT Group.
 * @version 2002/02/13    Mario August (created class)
 */
public class AdminDelInv extends CompoundItem {

    /** A boolean variable for debugging purposes */
    boolean dbg = false;
    //boolean dbg = true;

    /** A boolean variable for deleting & updating process */
    boolean invStatsExist = false;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters. */
    static common.edmCommon ec = new common.edmCommon();

    /** a class that contains all the constants used in the sadco application */
    sadco.SadConstants sc = new sadco.SadConstants();


    // arguments
    String surveyId    = "";

    String screen      = "";
    String version     = "";
    String confirm     = "";
    String keepInv     = "";

    String menuNo      = "";
    String mVersion    = "";

    String sessionCode = "";
    String userType    = "";


    String projectName = "";
    String cruiseName  = "";
    String dateStart   = "";
    String dateEnd     = "";
    String latNorth    = "";
    String latSouth    = "";
    String longEast    = "";
    String longWest    = "";
    String dataStored  = "";


    /** the SQL error message if the deletion was unsuccessfull */
    String errorMessages;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public AdminDelInv (String args[]) {

        try {
            AdminDelInvActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
            for (int i = 0; i < args.length; i++) {
                System.err.println(args[i]);
            } // for i

        } // try - catch

    } // AdminDelInv constructor

    /**
     * The constructor.  Everything happens here.
     * @param args an array of String that contains the URL parameter-value pairs.
     */
    void AdminDelInvActual(String args[]) {

        getArgParms(args);
        if (dbg) System.err.println(surveyId);

        //check whether the survey exists
        boolean found = checkSurveyId(surveyId);

        if (dbg) System.out.println(" surveyId = "+surveyId+
            " is valid : validSurvey = "+found);

        DynamicTable table = new DynamicTable(1);
        table.setFrame(ITableFrame.VOLD);
        table.setRules(ITableRules.NONE);
        //table.setFrame(ITableFrame.BOX);
        table.setBorder(0);

        // test if survey id exist
        if (!found) {

            this.addItem("\n<center>");
            this.addItem("<b><font color=blue><font size=+2>Delete a survey"+
                    "</font></font></b><br><br>");
            this.addItem("There is no survey with a Survey-ID of "+surveyId);
            this.addItem("\n</center>");
            return;

        } else {

            // get the inventory record
            getInventoryRecords(surveyId);

            // call confirmation form...
            Form form = confirmDelete();
            this.addItem(form).setCenter();

            if (!"".equals(confirm)) {

                int numRecsDel = 0;

                // delete inventory and/or survey
                if ("Yes".equals(keepInv)) {
                    numRecsDel = deleteSurvey(surveyId);
                    if (dbg)System.out.println("after del Survey programme !!" + numRecsDel);
                } else {
                    numRecsDel = deleteInventory(surveyId);
                    if (dbg)System.out.println("after del Inventory programme !!" + numRecsDel);
                } // if ("Yes".equals(keepInv))


                // get the string for the user ready
                String text = "successful";
                String color = "green";
                if (numRecsDel == 0) {
                    text = "un" + text;
                    color = "red";
                } //if (numRecsDel == 0)
                this.addItem("\n<center>");
                this.addItem("\n<b><font color=" + color + "><font size=+1>" +
                    "Deletion was "+ text + ".<br><br></font></font></b>");
                this.addItem("\n</center>");

            } // if (!"".equals(confirm))
        } //if (!found)
    } //AdminDelInv

    /**
     * Get the parameters from the arguments in the URL.
     * @return void
     */
   void getArgParms(String args[])   {
        if (dbg) for (int i = 0; i < args.length; i++) {
            System.err.println("<br>" + args[i]);
        } // for i

        version  = ec.getArgument(args, sc.VERSION);
        screen   = ec.getArgument(args, sc.SCREEN);
        surveyId = ec.getArgument(args, sc.SURVEYID);
        confirm  = ec.getArgument(args, sc.CONFIRM);
        keepInv  = ec.getArgument(args, sc.KEEPINV);
        menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        userType = ec.getArgument(args, sc.USERTYPE);
        if (dbg) System.out.println("confirm "+confirm+" keepInv "+keepInv);

    } // getArgParms


   /**
     * The function check for valid Survey-ID.
     * @param surveyId : argument value from url (String)
     * @return boolean surveyId exists or not
     */
    boolean checkSurveyId(String surveyId) {

        String where = MrnInventory.SURVEY_ID +"='"+surveyId+"'";
        int surveyCnt = new MrnInventory().getRecCnt(where);
        return (surveyCnt > 0 ? true : false);

    } //boolean checkSurveyId(String surveyId) {


   /**
     * The function count stations records.
     * @param surveyId : argument value from url (String)
     * @return numStationRecs
     */
/*    int getRecordsCount(String surveyId) {
        String where =  MrnStation.SURVEY_ID +"='"+ surveyId+"'";
        int numStationRecs = new MrnInventory().getRecCnt(where);
        return numStationRecs;
    } //boolean getRecordsCount(String surveyId) {
*/

    /**
    * Get all the inventory record from database for the surveyId.
    * @param surveyId : argument value from url (string)
    */
   void getInventoryRecords(String surveyId) {

        String where = MrnInventory.SURVEY_ID +"='"+surveyId+"'";
        MrnInventory[] invRecs = new MrnInventory().get(where);

        cruiseName   = invRecs[0].getCruiseName("");
        projectName  = invRecs[0].getProjectName("");
        dateStart    = invRecs[0].getDateStart("");
        if (!"".equals(dateStart)) dateStart = dateStart.substring(0,10);
        dateEnd      = invRecs[0].getDateEnd("");
        if (!"".equals(dateEnd)) dateEnd = dateEnd.substring(0,10);
        latNorth     = invRecs[0].getLatNorth("");
        latSouth     = invRecs[0].getLatSouth("");
        longEast     = invRecs[0].getLongEast("");
        longWest     = invRecs[0].getLongWest("");
        dataStored   = invRecs[0].getDataRecorded("");

    } // getInventoryRecords


   /**
     * The function delete records from Inventory table.
     * @param surveyId : argument value from url (String)
     * @return int number of records deleted
     */
    int deleteInventory(String surveyId) {

        if (dbg) System.err.println("SurveyId before where "+surveyId);

        String where = MrnInventory.SURVEY_ID +"='"+surveyId+"'";
        MrnInventory inventory = new MrnInventory();
        boolean success = inventory.del(where);

        // get number of records deleted
        int numRecsDel = 0;
        if (success) {
            numRecsDel = inventory.getNumRecords();
        } else {
            errorMessages = inventory.getMessages();
        } //if (success) {

        return numRecsDel;

    } // deleteInventory


    /**
     * The function delete records from Survey (String)
     * @return int number of records deleted
     */
    int deleteSurvey(String surveyId) {

        // delete the survey
        String where = MrnSurvey.SURVEY_ID +"='"+ surveyId+"'";
        MrnSurvey survey = new MrnSurvey();
        boolean success = survey.del(where);

//        // delete the inv_stats record
//        MrnInvStats invStats = new MrnInvStats();
//        invStats.del(where);

        // get number of records deleted
        int numRecsDel = 0;
        if (success) {
            numRecsDel = survey.getNumRecords();
        } else {
            errorMessages = survey.getMessages();
        } //if (success)

        if (numRecsDel > 0) {

            // delete the inv_stats record
            where = MrnInvStats.SURVEY_ID +"='"+ surveyId+"'";
            MrnInvStats invStats = new MrnInvStats();
            //boolean success = new MrnSurvey().del(where);
            invStats.del(where);

            // update the inventory record
            where = MrnInventory.SURVEY_ID +"='"+surveyId+"'";
            MrnInventory inventory = new MrnInventory();
            MrnInventory updInventory = new MrnInventory();
            updInventory.setDataRecorded("N");
            inventory.upd(updInventory, where);
        } // if (numRecsDel > 0)

        return numRecsDel;

    } // deleteSurvey


    /**
     * build the form with inventory details
     */
    Form confirmDelete() {
        DynamicTable table = new DynamicTable(1);
        table.setFrame(ITableFrame.VOLD);
        table.setRules(ITableRules.NONE);
        //table.setFrame(ITableFrame.BOX);
        table.setBorder(0);

        table.addRow(ec.crSpanColsRow("<b><font color=blue><font size=+2>"+
        "Delete a survey</font></font><b><br><br>",4));
        table.addRow(ec.cr2ColRow("Survey Id: ",surveyId));
        table.addRow(ec.cr2ColRow("Project Name: ",projectName));
        table.addRow(ec.cr2ColRow("Cruise Name: ",cruiseName));
        table.addRow(ec.cr2ColRow("Date Range: ",dateStart + " to " + dateEnd ));
        table.addRow(ec.cr2ColRow("Latitude Range: ",latNorth+" to "+latSouth));
        table.addRow(ec.cr2ColRow("Longitude Range: ",longWest+" to "+longWest));
        table.addRow(ec.cr2ColRow("Data Stored: ",dataStored));
        table.addRow(ec.crSpanColsRow("&nbsp",2));

        // build up the message to the user
        String delState = "";
        if ("".equals(confirm)) {
            delState = " will be";
        } else {
            delState = " was";
        } // if ("".equals(confirm))

        String delState2 = "";
        if ("Yes".equals(keepInv)) {
            //form.addItem(new SimpleItem("<b> be deleted"));
            delState2 = " kept.";
        } else {
            //table.addRow(ec.crSpanColsRow(" be deleted",10));
            delState2 = " deleted.";
        } //if ("Y".equals(delInv)) {

        table.addRow(ec.crSpanColsRow("The inventory record" + delState + delState2,10));

        if ("".equals(confirm)) {
            table.addRow(ec.crSpanColsRow("&nbsp",1));
            table.addRow(ec.cr2ColRow("Confirm delete",": "+new Submit(sc.CONFIRM,"Yes")));
        } //if (!"".equal(confirm))

        Form form = new Form("POST",sc.MRN_APP);
        form.addItem(new Hidden(sc.VERSION,version));
        form.addItem(new Hidden(sc.SCREEN,screen));
        form.addItem(new Hidden(sc.KEEPINV,keepInv));
        form.addItem(new Hidden(sc.SURVEYID,surveyId));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(table);

        return form;
    } // confirmDelete

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("AdminDelInv local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //AdminDelInv local= new AdminDelInv(args);
            bd.addItem(new AdminDelInv(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "AdminDelInv", "Constructor", "");
            // close the connection to the database
        } // try-catch
        common2.DbAccessC.close();

    } // main


} // class AdminDelInv









