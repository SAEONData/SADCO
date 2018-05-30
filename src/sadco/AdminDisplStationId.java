package sadco;

import oracle.html.*;

/**
 * This class display's a list of station id's in a drop down box.
 *
 * SadcoMRN
 * screen.equals("admin")
 * version.equals("displstn")
 *
 * It's parameters are the following:
 * <br>pscreen   - used by SadcoMRN program. Value must be 'admin'.
 * <br>pversion  - used by SadcoMRN program. Value must be 'displstn'.
 * <br>surveyId  - the ID of the survey that the station belongs to.
 * <br>stationId - the ID of the station to be deleted.
 *
 * @author 2002/02/13 - SIT Group.
 * @version
 * 2002/02/13 - Mario August       - created class<br>
 */
public class AdminDisplStationId extends CompoundItem {

    /** A boolean variable for debugging purposes */
    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();

    /** a class that contains all the constants used in the sadco application */
    sadco.SadConstants sc = new sadco.SadConstants();

    // arguments
    String surveyId = "";

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public AdminDisplStationId (String args[]) {

        try {
            AdminDisplStationIdActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // AdminDisplStationId constructor


    /**
    * Creates a page with a banner and an entry form
    * @param args the string array of url-type name-value parameter pairs
    */
    void AdminDisplStationIdActual(String args[]) {

        getArgParms(args);
        MrnStation[] stations = getStationId(surveyId);

        this.addItem("\n<center><br>");
        this.addItem("<b><font color=blue><font size=+2>Delete a station"+
                    "</font></font></b>");
        this.addItem("\n<p><b>Survey ID: </b>" + surveyId);

        //if ("".equals(stations.length)) {
        if (stations.length < 1) {
            this.addItem("\n<b><font color=red><font size=+1>" +
                "<p>There are no station record(s) for this survey<p>" +
                "</font></font></b>");
            this.addItem("");
        } else {
            Form form = displayStations(stations, args);
            this.addItem(form);
        } // if (stations.length < 1)

        this.addItem("</center>");


    } // AdminDisplStationIdActual


    /**
     * Get the parameters from the arguments in the URL
     * @param args
     * @return void
     */
    void getArgParms(String args[])   {
        for (int i = 0; i < args.length; i++) {
            if (dbg) System.out.println("<br>" + args[i]);
        } // for i
        surveyId = ec.getArgument(args, sc.SURVEYID);

    } // void getArgParms(String args[])


    /**
     * Get Stattion records from Database for the stationId
     * @param surveyId : argument value from url (string)
     * @return array value stations
     */
    MrnStation[] getStationId(String surveyId) {
        if (dbg) System.out.println("getStationRecords       ");
        // construct the table names
        String fields = MrnStation.STATION_ID;

        // construct the where clause
        String where = MrnStation.SURVEY_ID +"='"+ surveyId +"'";

        // construct the order by cluase
        String order = MrnStation.DATE_START;
        MrnStation[] stations = new MrnStation().get(fields, where, order);

        return stations;
    } //MrnStation[] getStationRecords(String stationId)


    /**
     * Display's all the station Id's for given survey id.
     * @param stations : argument value from URL (string)
     * @return form
     */
    Form displayStations(MrnStation[] stations, String args[]) {

        // for the top menu bar
        String sessionCode= ec.getArgument(args, sc.SESSIONCODE);
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // create the form
        //String target = "_top";
        /*****************/
        //if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        //Form form = new Form("POST", sc.MRN_APP, target);
        Form form = new Form("POST", sc.MRN_APP);
        form.addItem(new Hidden(sc.SCREEN,"admin"));
        form.addItem(new Hidden(sc.VERSION,"confirm"));
        form.addItem(new Hidden(sc.SURVEYID,surveyId));
        // for the menu system
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        //form.addItem("<b><font color=blue><font size=+4>Delete a station"+
        //    "</font></font>");
        form.addItem("<b>Station IDs:</b> "+createSelect(stations).toHTML());
        form.addItem("<p>"+new Submit("","Submit!"));

        return form;
    }//Form displayStations(MrnStation[] stations) {

    /**
     * The function create a drop down box with all survey id's.
     * @param stations : argument value from URL (string)
     * @return stationIdSelect
     */
    Select createSelect (MrnStation[] stations) {
            Select stationIdSelect = new Select(sc.STATIONID);

            for (int i = 0; i < stations.length; i++) {
                stationIdSelect.addOption(new Option(stations[i].getStationId()));

            } //for (int i=0;i < count; i++) {

            return stationIdSelect;
     } //Select createSelect (MrnStation[] stations) {


    //public static void main(String args[]) throws Exception {
    //    try {
    //        AdminDisplStationId local = new AdminDisplStationId(args);
    //    } catch (Exception e) {
    //        ec.processError(e, "AdminDisplStationId", "constructor", "");
    //    } // try-catch
    //} // public static void main(String args[])

} // class AdminDisplStationId