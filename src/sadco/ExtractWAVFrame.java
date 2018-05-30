package sadco;

/**
 * Uses the information to submit a job in the background to do the actual
 * extraction of the data.
 *
 * SadcoWAV
 * screen.equals("extract")
 * version.equals("extract")
 *
 * @author 080226 - SIT Group
 * @version
 * 080226 - Ursula von St Ange - create class.                          <br>
 */

import oracle.html.*;

public class ExtractWAVFrame extends CompoundItem {

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    //boolean dbg = false;
    boolean dbg = true;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ExtractWAVFrame (String args[]) {

        try {
            ExtractWAVFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ExtractWAVFrame constructor

    /**
    * Creates a page with a banner and an entry form
    * @param args the string array of url-type name-value parameter pairs
    */
    void ExtractWAVFrameActual(String args[]) {

        // get the arguments
        String sessionCode   = ec.getArgument(args, sc.SESSIONCODE);
        String reqNumber     = ec.getArgument(args, sc.REQNUMBER);
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

        String extension = ".wav";

        String command =
            SadConstants.JAVA          + " " +
            SadConstants.WAV_APP       + "Apps " +
            SadConstants.SCREEN        + "=extract "+
            SadConstants.VERSION       + "=doextract "+
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
        Form form = new Form("POST", sc.WAV_APP);
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
            HtmlHead hd = new HtmlHead("ExtractWAVFrame");

            // create the body and the page itself
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            hp.printHeader();

            // ExtractWAVFrame local= new ExtractWAVFrame(args);
            bd.addItem(new ExtractWAVFrame(args));

            hp.print();

        } catch(Exception e) {
        //  ec.processErrorStatic(e, thisClass, "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main
} // ExtractWAVFrame
