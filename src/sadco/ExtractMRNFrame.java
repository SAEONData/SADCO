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
 * 001115 - Ursula von St Ange - create class                               <br>
 * 020527 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020527 - Ursula von St Ange - update for exception catching              <br>
 * 020709 - Ursula von St Ange - display data file name on screen           <br>
 * 030509 - Mario August       - test for Reqnumber equals blank.           <br>
 * 040609 - Ursula von St Ange - add for currents extractions (ub01)        <br>
 * 121106 - Ursula von St Ange - add for currents odv extractions (ub02)    <br>
 * 150324 - Ursula von St Ange - add underway current components (ub03)   <br>
 */
public class ExtractMRNFrame extends CompoundItem {

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    SadConstants sc = new SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ExtractMRNFrame (String args[]) {

        try {
            ExtractMRNFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ExtractMRNFrame constructor

    /**
    * Creates a page with a banner and an entry form
    * @param args the string array of url-type name-value parameter pairs
    */
    void ExtractMRNFrameActual(String args[]) {

        // get the arguments
        String sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        String reqNumber = ec.getArgument(args, sc.REQNUMBER);
        String extrType = ec.getArgument(args, sc.EXTRTYPE);
        String format   = ec.getArgument(args, sc.FORMAT);
        String surveyId = ec.getArgument(args, sc.SURVEYID);
        String flagType = ec.getArgument(args, sc.FLAGTYPE);
        String dataSet = ec.getArgument(args, sc.DATASET);
        String password = ec.getArgument(args, sc.PASSWORD);
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

        String extension = ".ddd";
        //if (dbg)
        System.out.println("<br>extrType = " + extrType);
        //if (dbg)
        System.out.println("<br>format = " + format);
        switch (Integer.parseInt(extrType)) {
            case  0: //extrType = "physnut";
                     //extension = ".dbm";
                     if ("0".equals(format)) {
                         extrType = "physnut";
                         extension = ".dbm";
                     } else {
                        extrType = "physnutodv";
                        extension = ".txt";
                     } //if ("1".equals(format)) {
                     break;
            case  1: extrType = "watpol"; extension = ".dbp"; break;
            case  3: extrType = "sedpol"; extension = ".dbp"; break;
            case  5: extrType = "tispol"; extension = ".dbp"; break;
            //case  8: extrType = "current"; extension = ".dbc"; break;   // ub01
            case  8: //extrType = "physnut";
                     //extension = ".dbm";
                     if ("0".equals(format)) {
                         extrType = "current";
                         extension = ".dbc";
                     } else {
                        extrType = "currentodv";
                        extension = ".txt";
                     } //if ("1".equals(format)) {
                     break;
            case  9: if ("0".equals(format)) {                              //ub03
                         extrType = "currentcomp";                          //ub03
                         extension = ".dbc";                                //ub03
                     } else {                                               //ub03
                        extrType = "currentodvcomp";                        //ub03
                        extension = ".txt";                                 //ub03
                     } //if ("1".equals(format)) {                          //ub03
                     break;                                                 //ub03
            case  10: extrType = "station"; extension = ".stn"; break;      //ub03
            default: extrType = "unknown"; break;
        } // case

        // .....Submit command to Oracle ...... (running in background)
        String command = sc.JAVA + " " + sc.MRN_APP + "Apps " +
            sc.SCREEN + "=extract " +
            sc.VERSION + "=doextract " +
            sc.EXTRTYPE + "=" + extrType + " " +
            sc.SESSIONCODE + "=" + sessionCode + " " +
            sc.REQNUMBER + "=" + reqNumber + " " +
            sc.FILENAME + "=" + fileName + " " +
            sc.FLAGTYPE + "=" + flagType + " " +
            sc.DATASET + "=" + dataSet + " " +
            sc.PASSWORD + "=" + password + " " +
            sc.SURVEYID + "=" + surveyId + " " +
            sc.STARTDATE + "=" + startDate + " " +
            sc.ENDDATE + "=" + endDate + " " +
            sc.LATITUDENORTH + "=" + latitudeNorth + " " +
            sc.LATITUDESOUTH + "=" + latitudeSouth + " " +
            sc.LONGITUDEWEST + "=" + longitudeWest + " " +
            sc.LONGITUDEEAST + "=" + longitudeEast;
        //if (dbg) System.out.println("<br>command = " + command);
        System.err.println("<br>command = " + command);
        //if ("morph".equals(ec.getHost())) {
        //    ec.submitJob(command);
        //} // if ("morph".equals(ec.getHost()))
        System.out.println(ec.getHost());
        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob(command);
        } // if host


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
        Form form = new Form("POST", sc.MRN_APP);
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

} // ExtractMRNFrame
