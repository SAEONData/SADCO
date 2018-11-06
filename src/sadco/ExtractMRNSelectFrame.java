package sadco;

import common2.*;
import oracle.html.*;

/**
 * Get the extraction details.  In the VOS system, this is handled by the menu
 * system.  In the Marine (MRN) system, it must have more intelligence too
 * decide whether to display the flagged data prompts.
 *
 * SadcoMRN
 * screen.equals("extract")
 * version.equals("preextract")
 *
 * @author 001101 - SIT Group
 * @version
 * 001101 - Ursula von St Ange - create class<br>
 * 020527 - Ursula von St Ange - update for non-frame menus<br>
 * 020527 - Ursula von St Ange - update for exception catching<br>
 */
public class ExtractMRNSelectFrame extends CompoundItem {

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
    public ExtractMRNSelectFrame (String args[]) {

        try {
            ExtractMRNSelectFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ExtractMRNSelectFrame constructor

    /**
    * Creates a page with a banner and an entry form
    * @param args the string array of url-type name-value parameter pairs
    */
    void ExtractMRNSelectFrameActual(String args[]) {

        // get the arguments
        String sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        // for the top menu bar
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // get the user's flagged data extraction rights
        int session = Integer.parseInt(sessionCode);
        LogSessions[] sessions = new LogSessions(session).get();
        String userId = sessions[0].getUserid();
        SadUsers[] users = new SadUsers(userId).get();
        int flagType = users[0].getFlagType();

        // Create form table
        DynamicTable formTable = new DynamicTable(2);
        formTable.setFrame(ITableFrame.VOLD);
        formTable.setRules(ITableRules.NONE);
        formTable.setBorder(0);
        TableRow row;

        // add the menu header
        formTable.addRow(ec.crSpanColsRow("Extract Data", 2, true,
            "blue", IHAlign.CENTER, "+2"));

        // write help info
        ec.writeHelpInfo(formTable);

        // create drop-down extraction type list
        Select extrType = new Select(sc.EXTRTYPE);
        for (int i = 0; i < sc.MRNEXTRLIST.length;  i++) {
            extrType.addOption(new Option(
                sc.MRNEXTRLIST[i], String.valueOf(i), false));
        } // for (int i = 0;

        // create drop-down format type list
        Select formatType = new Select(sc.FORMAT);
        for (int i = 0; i < sc.MRNFORMATLIST.length;  i++) {
            formatType.addOption(new Option(
                sc.MRNFORMATLIST[i], String.valueOf(i), false));
        } // for (int i = 0;

        // create drop-down list for data type
        Select dataFlagType = new Select(sc.FLAGTYPE);
        dataFlagType.addOption(new Option("Unflagged Data", "1", false));
        dataFlagType.addOption(new Option("Flagged Data", "2", false));
        dataFlagType.addOption(new Option(
            "Both Flagged and Unflagged", "3", false));

        // add the other items
        formTable.addRow(ec.cr2ColRow("Request Number:", sc.REQNUMBER, 10, ""));
        String value =
            new TextField(sc.FILENAME, 30, 30, "").toHTML() + "*";
        formTable.addRow(ec.cr2ColRow("Output File Name:", value));
        value = extrType.toHTML() + "*";
        //formTable.addRow(ec.cr2ColRow("Data Subject Type:", value));
        formTable.addRow(ec.cr2ColRow("Data Type:", value));

        value = formatType.toHTML() + "*";
        formTable.addRow(ec.crSpanColsRow(
            "If 'Physical/Nutrient Oceanography' or 'Underway Currents', please specify format.",2));
        formTable.addRow(ec.cr2ColRow("Data Format :", value));

        if (flagType == 1) { // allowed to make unflagged data extractions
            //formTable.addRow(ec.cr2ColRow("Data Type:", dataFlagType));
            formTable.addRow(ec.cr2ColRow("Flag Type:", dataFlagType));
            formTable.addRow(ec.crSpanColsRow(
                "If FLAGGED data, please enter the following",2));
            formTable.addRow(ec.cr2ColRow("Dataset:", sc.DATASET, 10, ""));
            formTable.addRow(ec.cr2ColRow("Password:", sc.PASSWORD, 10, ""));
        } //

        formTable.addRow(ec.crSpanColsRow(
            "<br>Enter EITHER the Survey ID OR the Date & Area Ranges<br><br>",2));
        formTable.addRow(ec.cr2ColRow("Survey ID:", sc.SURVEYID, 9, ""));
        formTable.addRow(ec.crSpanColsRow("---OR---",2));
        ec.datetimeRangeInput(formTable,"daterange", 10);
        formTable.addRow(ec.latlonRangeInput("", 7, "", "", "", "", ""));

        // add javascript stuff
        JSLLib.formName = "myForm";
        this.addItem(JSLLib.OpenScript());
        this.addItem(JSLLib.RtnNotNull());
        this.addItem(JSLLib.RtnStripMask());
        this.addItem(JSLLib.RtnReplace());
        this.addItem(JSLLib.RtnGetValue());
        this.addItem(JSLLib.RtnY2K());
        this.addItem(JSLLib.RtnChkDate());
        this.addItem(JSLLib.RtnCmpDates());
        this.addItem(JSLLib.RtnCmpDateTimes());
        this.addItem(JSLLib.RtnChkTime());
        this.addItem(JSLLib.RtnChkRange());
        this.addItem(JSLLib.RtnChkInteger());
        this.addItem(JSLLib.RtnChkNumPrecision());
        this.addItem(JSLLib.RtnChkNumScale());
        this.addItem(JSLLib.RtnLower());
        this.addItem(JSLLib.RtnUpper());

        this.addItem(JSLLib.OpenEvent("INPUT","Validate"));
        this.addItem(JSLLib.CallNotNull(
            sc.FILENAME, "Output File Name"));
        this.addItem(ec.datetimeRangeJS("daterange", true));
        this.addItem(ec.latlonRangeJS(4, 2, true));
        this.addItem(JSLLib.CloseEvent());
        this.addItem(JSLLib.OpenEvent("btnIFI","OnClick"));
        this.addItem(JSLLib.CallValidate("INPUT"));
        this.addItem(JSLLib.StandardSubmit(false));
        this.addItem(JSLLib.CloseEvent());
        this.addItem(JSLLib.CloseScript());

        // create the form
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST\" name=\"myForm", sc.MRN_APP, target);

        // add the hidden fields
        form.addItem(formTable.setCenter());
        form.addItem(new Hidden(sc.SCREEN, "extract"));
        form.addItem(new Hidden(sc.VERSION, "extract"));
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        // for the menu system
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));
        form.addItem(SimpleItem.LineBreak);

        form.addItem(JSLLib.OnClickButton("Go!","btnIFI")).setCenter();


        // add components to screen
        //this.addItem(formTable.setCenter());
        this.addItem(form.setCenter());

    } // ExtractMRNSelectFrameActual

} // ExtractMRNSelectFrame class
