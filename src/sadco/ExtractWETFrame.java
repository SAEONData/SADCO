package sadco;

import oracle.html.*;

/**
 * Uses the information to submit a job in the background to do the actual
 * extraction of the data.
 *
 * SadcoMRN
 * screen.equals("extract")
 * version.equals("extract")
 *
 * @author 001115 - SIT Group
 * @version
 * 030509 - Mario August       - test for Reqnumber equals blank.           <br>
 */
public class ExtractWETFrame extends CompoundItem {

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ExtractWETFrame (String args[]) {

        try {
            ExtractWETFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ExtractWETFrame constructor

    /**
    * Creates a page with a banner and an entry form
    * @param args the string array of url-type name-value parameter pairs
    */
    void ExtractWETFrameActual(String args[]) {

        // get the arguments
        String sessionCode   = ec.getArgument(args, sc.SESSIONCODE);
        String reqNumber     = ec.getArgument(args, sc.REQNUMBER);
        String extrType      = ""; //ec.getArgument(args, sc.EXTRTYPE);
        String format        = ec.getArgument(args, sc.FORMAT);
        String surveyId      = ec.getArgument(args, sc.SURVEYID);
        String fileName      = ec.getArgument(args, sc.FILENAME);
        String startDate     = ec.getArgument(args, sc.STARTDATE);
        String endDate       = ec.getArgument(args, sc.ENDDATE);
        String latitudeNorth = ec.getArgument(args, sc.LATITUDENORTH);
        String latitudeSouth = ec.getArgument(args, sc.LATITUDESOUTH);
        String longitudeWest = ec.getArgument(args, sc.LONGITUDEWEST);
        String longitudeEast = ec.getArgument(args, sc.LONGITUDEEAST);

        // for the top menu bar
        String userType  = ec.getArgument(args, sc.USERTYPE);
        String menuNo    = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion  = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        String extension = ".wet";
        //switch (Integer.parseInt()) {
        //    case  0: //extrType = "physnut"; // test surveyId  if blank = dbc else cur
                     //extension = ".dbm";
         /*
         if ("".equals(surveyId)) {
             extrType = "dbc";
             //extension = "";
         } else {
            extrType = "cur";
            //extension = "";
         } //if ("".equals(surveyId)) {

         */
                     //break;
            //case  1: extrType = "physnutodv"; extension = ".txt"; break;
            //default: extrType = "unknown"; break;
        //} // case


        // .....Submit command to Oracle ...... (running in background)
        /*
        String command = sc.JAVA + " " +
        "/oracle/users/edm/www/java/waves/currents.ExtractDepthsData " +
            currents.CurConstants.SCREEN+"=extract "+
            currents.CurConstants.VERSION+"=dodepths "+
            currents.CurConstants.NUMDEPTHS+"=1 "+
            currents.CurConstants.MOORINGCODE+"=583 "+
            currents.CurConstants.DEPTHCODE+(1)+"=2301 "+
            currents.CurConstants.INITIALS+"=ma ";

            sc.SCREEN + "=extract " +
            sc.VERSION + "=doextract " +
            sc.EXTRTYPE + "=" + extrType + " " +
            sc.SESSIONCODE + "=" + sessionCode + " " +
            sc.REQNUMBER + "=" + reqNumber + " " +
            sc.FILENAME + "=" + fileName + " " +
            sc.SURVEYID + "=" + surveyId + " " +
            sc.STARTDATE + "=" + startDate + " " +
            sc.ENDDATE + "=" + endDate + " " +
            sc.LATITUDENORTH + "=" + latitudeNorth + " " +
            sc.LATITUDESOUTH + "=" + latitudeSouth + " " +
            sc.LONGITUDEWEST + "=" + longitudeWest + " " +
            sc.LONGITUDEEAST + "=" + longitudeEast;
        */

       //extrType = "wet";

       String command =
            SadConstants.JAVA          + " " +
            SadConstants.WET_APP       + "Apps " +
            SadConstants.SCREEN        + "=extract "+
            SadConstants.VERSION       + "=doextract "+
            //SadConstants.EXTRTYPE + "=" + extrType + " " +
            SadConstants.SESSIONCODE   + "=" + sessionCode + " " +
            SadConstants.REQNUMBER     + "=" + reqNumber + " " +
            SadConstants.FILENAME      + "=" + fileName  + " " +
            SadConstants.SURVEYID      + "=" + surveyId  + " " +
            SadConstants.STARTDATE     + "=" + startDate + " " +
            SadConstants.ENDDATE       + "=" + endDate   + " " +
            SadConstants.LATITUDENORTH + "=" + latitudeNorth + " " +
            SadConstants.LATITUDESOUTH + "=" + latitudeSouth + " " +
            SadConstants.LONGITUDEWEST + "=" + longitudeWest + " " +
            SadConstants.LONGITUDEEAST + "=" + longitudeEast;

        if (dbg) System.out.println("<br>command = " + command);

        //if ("morph".equals(ec.getHost())) {
        //    ec.submitJob(command);
        //} // if ("morph".equals(ec.getHost()))

        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob(command);
        } // if (ec.getHost().startsWith(sc.HOST)) {


        // the main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        // create the html page
        mainTable.addRow(ec.cr1ColRow("<font size=+2>Extract Data</font>",
            true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow("The Query has been submitted!",
            false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("The data file will be called <b>" +
            ("".equals(reqNumber)?fileName:reqNumber+"_"+ fileName) +
           extension, false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("The <b>'Status of Extraction'</b> page will "+
            "appear in 15 seconds", false, "", IHAlign.CENTER));
        //mainTable.addRow(ec.cr1ColRow("<br>"));

        // create the form
        Form form = new Form("POST", sc.WET_APP);
        form.addItem(new Hidden(sc.SCREEN, "extract"));
        form.addItem(new Hidden(sc.VERSION, "progress"));
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.REQNUMBER, reqNumber));
        form.addItem(new Hidden(sc.FILENAME, fileName));
        // for the menu system
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        form.addItem(new Submit("", "OK"));

        // add components to screen
        this.addItem(mainTable.setCenter());
        this.addItem(form.setCenter());

    } // constructor

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {
        try {
            // create the page head
            HtmlHead hd = new HtmlHead("headTitle");

            // create the body and the page itself
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            hp.printHeader();

            // ExtractWETFrame local= new ExtractWETFrame(args);
            bd.addItem(new ExtractWETFrame(args));

            hp.print();

        } catch(Exception e) {
        //  ec.processErrorStatic(e, thisClass, "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main
} // ExtractWETFrame
