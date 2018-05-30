package sadco;

import oracle.html.*;

/**
 * Uses the information to submit a job in the background to do the actual
 * extraction of the data.
 *
 * SadcoVOS
 * screen.equals("extract")
 * version.equals("extract")
 *
 * @author  001115 - SIT Group
 * @version
 * 001115 - Ursula von St Ange - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020709 - Ursula von St Ange - display data file name on screen           <br>
 */
public class ExtractVOSFrame extends CompoundItem {

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
    public ExtractVOSFrame (String args[]) {

        try {
            ExtractVOSFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ExtractVOSFrame constructor


    /**
     * Main constructor ExtractVOSFrame
     * @param args the string array of url-type name-value parameter pairs
     */
    void ExtractVOSFrameActual(String args[]) {

        // get the arguments
        String sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        String reqNumber = ec.getArgument(args, sc.REQNUMBER);
        String fileName = ec.getArgument(args, sc.FILENAME);
        String startDate = ec.getArgument(args, sc.STARTDATE);
        String endDate = ec.getArgument(args, sc.ENDDATE);
        String latitudeNorth = ec.getArgument(args, sc.LATITUDENORTH);
        String latitudeSouth = ec.getArgument(args, sc.LATITUDESOUTH);
        String longitudeWest = ec.getArgument(args, sc.LONGITUDEWEST);
        String longitudeEast = ec.getArgument(args, sc.LONGITUDEEAST);
        // for the top menu bar
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // .....Submit command to Oracle ...... (running in background)
        String command = sc.JAVA + " " + sc.VOS_APP + "Apps " +
            sc.SCREEN + "=extract " +
            sc.VERSION + "=doextract " +
            sc.SESSIONCODE + "=" + sessionCode + " " +
            sc.REQNUMBER + "=" + reqNumber + " " +
            sc.FILENAME + "=" +fileName + " " +
            sc.STARTDATE + "=" + startDate + " " +
            sc.ENDDATE + "=" + endDate + " " +
            sc.LATITUDENORTH + "=" + latitudeNorth + " " +
            sc.LATITUDESOUTH + "=" + latitudeSouth + " " +
            sc.LONGITUDEWEST + "=" + longitudeWest + " " +
            sc.LONGITUDEEAST + "=" + longitudeEast;
        if (dbg) System.out.println("<br>command = " + command);
        ec.submitJob(command);

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
            reqNumber + "_" + fileName + ".dbv", false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("The <b>'Extract Status'</b> page will "+
            "appear in 15 seconds", false, "", IHAlign.CENTER));
        //mainTable.addRow(ec.cr1ColRow("<br>"));

        // create the form
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST", sc.VOS_APP, target);
        form.addItem(new Hidden(sc.SCREEN, "extract"));
        form.addItem(new Hidden(sc.VERSION, "progress"));
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.REQNUMBER, reqNumber));
        form.addItem(new Hidden(sc.FILENAME, fileName));
        form.addItem(new Submit("", "OK"));
        // for the menu system
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        // add components to screen
        this.addItem(mainTable.setCenter());
        this.addItem(form.setCenter());

    } // ExtractVOSFrameActual

} // ExtractVOSFrame class
