package sadco;

import java.util.StringTokenizer;
import java.sql.Timestamp;
import java.io.RandomAccessFile;
import oracle.html.CompoundItem;
import oracle.html.DynamicTable;
import oracle.html.Form;
import oracle.html.Hidden;
import oracle.html.HtmlBody;
import oracle.html.HtmlHead;
import oracle.html.HtmlPage;
import oracle.html.IHAlign;
import oracle.html.ITableFrame;
import oracle.html.ITableRules;
import oracle.html.Link;
import oracle.html.Option;
import oracle.html.Select;
import oracle.html.SimpleItem;
import oracle.html.Submit;
import oracle.html.TableDataCell;
import oracle.html.TableHeaderCell;
import oracle.html.TableRow;
import oracle.html.TextField;

/**
 * This class does some pre-processing for the currents load
 *
 * SadcoCUR
 * pScreen.equals("load")
 * pVersion.equals("preload")
 *
 * @author 021025 - SIT Group
 * @version
 * 021025 - Mario August        - create class                              <br>
 * 040614 - Ursula von St Ange  - now uses StringTokenizer for getting
 *                                deployment number, latitude and longitude.<br>
 * 040614 - Ursula von St Ange  - Change latitude, longitude to have 5
 *                                decimal places only                       <br>
 * 040614 - Ursula von St Ange  - station name now default for input box    <br>
 * 060201 - Ursula von St Ange  - add timezone for date/time correction to UTC<br>
 * 081028 - Ursula von St Ange  - change to only send new stuff to next pgm <br>
 * 090513 - Ursula von St Ange  - add input of project name (ub01)          <br>
 * 110725 - Ursula von St Ange  - copy from currents.PreLoadDataFrame       <br>
 */
public class PreLoadCURDataFrame extends CompoundItem {

    boolean dbg        = false;
    //boolean dbg        = true;
    boolean dbg2       = false;
    //boolean dbg2       = true;

    boolean now          = false;
    boolean fileDB       = false;
    //boolean fileDB     = true;
    boolean tmp          = false;
    //boolean tmp          = true;

    boolean loadFlag     = false;
    boolean ok           = false;
    boolean timeDiff     = false;
    boolean error        = false;
    boolean isOvelap     = false;
    boolean mooringExist = false;
    boolean headerError  = false;
    boolean partDepth    = false;
    boolean depthLoaded  = false;
    boolean InstrExists  = false;
    boolean displayTable = false;

    static common.edmCommon ec  = new common.edmCommon();

    //currents.CurConstants cc = new currents.CurConstants();

    /** A class with all the constants used in SADCO.*/
    SadConstants cc = new SadConstants();

    // parameters from html page
    String rootPath      = "";
    String fileName      = "";

    RandomAccessFile file;
    RandomAccessFile progressFile;
    String line          = "";

    // Variables for arguments.
    String screen           = "";
    String version          = "";
    String sessionCode      = "";
    String userType         = "";
    String extension        = "";
    String checkinterval    = "";
    String timeZone         = "";
    String menuno           = "";
    String mversion         = "";

    // Variables for where clause.
    String field            = "";
    String where            = "";
    String order            = "";

    // Header data fields
    String stationName      = "";
    String instrType        = "";
    String instrNo          = "";
    String deploymentNo     = "";

    String latitudeD        = "";
    String latitudeM        = "";
    float  latitude         = 0;

    String longitudeD       = "";
    String longitudeM       = "";
    float  longitude        = 0;

    String stnnam           = "";
    String arenam           = "";
    String pubRef           = "";
    String surveyId         = "";

    Timestamp dataStart;
    Timestamp dataEnd;
    String dataStartS       = "";
    String dataEndS         = "";

    float  meterDepth       = -999;
    int    bottomDepth      = -999;

    String newMooringStart  = "";
    String newMooringEnd    = "";

    int    mooringCode      = 0;
    int    depthCode        = 0;
    int    clientCode       = 0;  // default  = None/Unknown
    int    planamCode       = 1;  // default  = none
    String clientName       = "";
    String platformName     = "";

    long  timeInterval     = 0;
    int   lineCnt          = 0;

    String header          = "";
    String colour          = "";
    String leftText        = "";

    Timestamp mooringStart;
    Timestamp mooringEnd;
    String mooringStartS    = "";
    String mooringEndS      = "";

    String[] notes          = new String[10];

    // Create main table
    DynamicTable mainTab = new DynamicTable(2);
    DynamicTable subTab  = new DynamicTable(1);

    public PreLoadCURDataFrame(String args[]) {

        String sub = "<br>currents.PreLoadCURDataFrame: ";

        if (ec.getHost().startsWith(cc.HOST)) {
            rootPath = cc.HOSTDIRL + "currents/";
        } else {
            rootPath = cc.LOCALDIR + "currents/";
        } // if host

        if (dbg) System.out.println(sub+"pathName = " + rootPath);

        // get 'commandline' arguments
        getArgParms(args);

        String fileName2 = rootPath+fileName;
        if (dbg) System.out.println(sub+"fileName2 "+fileName2);
        if (fileDB) System.out.println(sub+"fileName = "+fileName);

        //DynamicTable mainTab = new DynamicTable(2);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();

        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTab.addRow(ec.cr1ColRow("Load Data",true,"blue",IHAlign.CENTER,"+2" ));

        ec.deleteOldFile(fileName2+".progress");
        progressFile = ec.openFile(fileName2+".progress","rw");
        if (ec.getHost().startsWith(cc.HOST)) {
            ec.submitJob("chmod 644 " + fileName2+".progress");
        } // if (ec.getHost().startsWith(cc.HOST))

        openInputFile(fileName2);

        // get header info
        ec.writeFileLine(progressFile, "getStd/CurHeaderInfo: extension = " + extension);
        if (".std".equals(extension)) {
            getStdHeaderInfo();
        } else {
            getCurHeaderInfo();
        } // if ("std".equals(extension))
        subTab = checkHeaderInfo();
        if (headerError) {
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));
        } // if (headerError) {

        if (fileDB) System.out.println(sub+"fileName = "+fileName);

        // get start and end interval
        ec.writeFileLine(progressFile, "startEndInteval");
        subTab = startEndInteval();
        if (fileDB) System.out.println(sub+"fileName = "+fileName);
        if (timeDiff) {
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));
            //checkLastRecord();
        } //if (timeDiff) {

        ec.writeFileLine(progressFile, "isMooringStored");
        subTab = isMooringStored();

        if (displayTable) {
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));
        } //if (displayTable) {

        ec.writeFileLine(progressFile, "isDataLoaded");
        subTab = isDataLoaded();
        if (loadFlag) { // record already loaded.
            //mainTab.addRow(subTab);
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));
        } else { //if (loadFlag) {

            displayTable = false;
            subTab = isPartDepth();
            if (displayTable) {
                mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
                mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));
            } // if (displayTable)
        } //if (loadFlag) {

        //if (dbg) System.out.println(sub+"platform "+platformName);
        if (dbg) System.out.println(sub+"stnnam   "+stnnam);
        if (dbg) System.out.println(sub+"arenam   "+arenam);
        if (dbg) System.out.println(sub+"pubRef   "+pubRef);

        if (!mooringExist) {
            subTab = getMooringData();
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));

            // mooring does not exist
            subTab = getDateTime();
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));
        } else {
            // mooring exists
            subTab = displayMooringData();
            //Form
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));
        } // if (!mooringExist)

        checkIfInstrumentExist();

        if (!InstrExists) {
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow(
                "Fatal Error: Insufficient instrument details supplied " +
                "or instrument not found in database.",
                4,true,"red",IHAlign.CENTER,"+1" ));
        } // if (!exist)

        subTab = displayInstrument();
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));

        subTab = displayDepths();
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));

        subTab = getSurveyID();
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTab.addRow(ec.crSpanColsRow(subTab.toHTML(),2));

        if (now) System.out.println(sub+""+mainTab.toHTML());

        Form mainForm = new Form("get", cc.CUR_APP);

        mainForm.addItem(new Hidden(cc.SCREEN,"load"));
        mainForm.addItem(new Hidden(cc.VERSION,"load"));
        mainForm.addItem(new Hidden(cc.USERTYPE,userType));
        mainForm.addItem(new Hidden(cc.SESSIONCODE,sessionCode));

        mainForm.addItem(new Hidden(cc.FILENAME,fileName));

        // get from header
        //- if (!InstrExists) {
        //-     mainForm.addItem(new Hidden(cc.NEWINSTRUMENTNO,String.valueOf(instrNo)));
        //-     mainForm.addItem(new Hidden(cc.NEWINSTRUMENTTYPE,instrType));
        //- } else {
        //-     // sent instrument code through as hidden
        //-     mainForm.addItem(new Hidden(cc.INSTRUMENTNUMBER,String.valueOf(instrNo)));
        //-     mainForm.addItem(new Hidden(cc.INSTRUMENTTYPE,instrType));
        //- } // if (!InstrExists)
        //- mainForm.addItem(new Hidden(cc.DEPLOYMENTNUMBER,deploymentNo));
        //- mainForm.addItem(new Hidden(cc.SPLDEP,String.valueOf(meterDepth)));

        if (dbg) System.out.println(sub+"@@@ dataStartS   = "+dataStartS);
        if (dbg) System.out.println(sub+"@@@ dataEndS     = "+dataEndS);

        // get from data
        //- mainForm.addItem(new Hidden(cc.STARTDATE,dataStartS));
        //- mainForm.addItem(new Hidden(cc.ENDDATE,dataEndS));
        //- mainForm.addItem(new Hidden(cc.TIMEINTERVAL,String.valueOf(timeInterval/60000)));
        //- mainForm.addItem(new Hidden(cc.NUMBEROFRECORDS,String.valueOf(lineCnt)));

        if (tmp) System.out.println(sub+"newMooringStart "+newMooringStart);
        if (tmp) System.out.println(sub+"newis "+newMooringEnd);

        // if mooring exist send as hidden to LoadDataFrame
        if (mooringExist) {
            mainForm.addItem(new Hidden(cc.MOORINGCODE,String.valueOf(mooringCode)));
            // get rest from mooring
            //- mainForm.addItem(new Hidden(cc.CLIENTCODE,String.valueOf(clientCode)));
            //- mainForm.addItem(new Hidden(cc.CLIENTNAME,clientName));
            //- mainForm.addItem(new Hidden(cc.PLANAMCODE,String.valueOf(planamCode)));
            //- mainForm.addItem(new Hidden(cc.PLATFORMNAME,platformName));
            //- mainForm.addItem(new Hidden(cc.STNNAM,stnnam));
            //- mainForm.addItem(new Hidden(cc.ARENAM,arenam));
            //- mainForm.addItem(new Hidden(cc.PUBLREF,pubRef));

            if (!"".equals(newMooringStart)) {
            // get from mooring
            //-     mainForm.addItem(new Hidden(cc.NEWMOORINGSTART,mooringStartS));
            //- } else {
                mainForm.addItem(new Hidden(cc.NEWMOORINGSTART,newMooringStart));
            } // if ("".equals(newMooringStart)) {

            if (!"".equals(newMooringEnd)) {
            // get from mooring
            //-     mainForm.addItem(new Hidden(cc.NEWMOORINGEND,mooringEndS));
            //- } else {
                mainForm.addItem(new Hidden(cc.NEWMOORINGEND,newMooringEnd));
            } // if ("".equals(newMooringEnd)) {

        } // if (mooringExist)

        // get from header
        //- mainForm.addItem(new Hidden(cc.DESCRIPTION,String.valueOf(stationName)));
        //- mainForm.addItem(new Hidden(cc.LATITUDE,String.valueOf(latitude)));
        //- mainForm.addItem(new Hidden(cc.LONGITUDE,String.valueOf(longitude)));
        //- mainForm.addItem(new Hidden(cc.STNDEP,String.valueOf(bottomDepth)));

        //- mainForm.addItem(new Hidden(cc.NOTESA,notes[0]));
        //- mainForm.addItem(new Hidden(cc.NOTESB,notes[1]));
        //- mainForm.addItem(new Hidden(cc.NOTESC,notes[2]));
        //- mainForm.addItem(new Hidden(cc.NOTESD,notes[3]));
        //- mainForm.addItem(new Hidden(cc.NOTESE,notes[4]));

        mainForm.addItem(new Hidden(cc.CHECKINTERVAL,checkinterval));
        mainForm.addItem(new Hidden(cc.TIMEZONE,timeZone));
        mainForm.addItem(new Hidden(cc.EXTENSION,extension));

        mainForm.addItem(new Hidden("pmenuno",menuno));
        mainForm.addItem(new Hidden("pmversion",mversion));

        if (partDepth) {
            mainForm.addItem(new Hidden(cc.PARTDEPTH,"Y"));
        } //if (partDepth) {

        if (loadFlag) {
            //- mainForm.addItem(new Hidden(cc.RELOAD,"Y"));
            mainForm.addItem(new Hidden(cc.DEPTHCODE,String.valueOf(depthCode)));
        } //if (loadFlag) {

        mainForm.addItem(mainTab);
        DynamicTable errorTab  = new DynamicTable(1);
        errorTab.setFrame(ITableFrame.VOLD);
        errorTab.setRules(ITableRules.NONE);
        errorTab.setBorder(0);
        errorTab.setCenter();
        errorTab.setFrame(ITableFrame.BOX);

        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
        if (!error) {
            errorTab.addRow(ec.cr2ColRow("Passkey:",cc.PASSKEY,30));

            errorTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            errorTab.addRow(ec.crSpanColsRow("Taking into account all the warnings"+
                " (green),<br>click on go to continue to load the data.",2
                    ,true,"green",IHAlign.CENTER,"+1" ));

            //errorTab.addRow(ec.crSpanColsRow("Click on go to load data."
            //    ,2,true,"blue",IHAlign.CENTER,"+1" ));

            mainForm.addItem(errorTab);
            mainForm.addItem(new Submit("","Go!").setCenter());
        } else {
            errorTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            errorTab.addRow(ec.crSpanColsRow("The data cannot be loaded with the fatal"+
                " errors.<br>Please correct the data and try again.",2,true,
                    "red",IHAlign.CENTER,"+1"));
            mainForm.addItem(errorTab.toHTML());
        } //if (error)

        this.addItem(mainForm);

        closeInputFile();
        ec.closeFile(progressFile);
        //this.addItem(mainTab);
    } // constructor

    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     * @return  void
     */
    private void getArgParms(String args[])   {

        String sub = "<br>getArgParms: ";

        fileName    = ec.getArgument(args,cc.FILENAME);
        screen      = ec.getArgument(args,cc.SCREEN);
        version     = ec.getArgument(args,cc.VERSION);
        sessionCode = ec.getArgument(args,cc.SESSIONCODE);
        userType    = ec.getArgument(args,cc.USERTYPE);
        extension   = ec.getArgument(args,cc.EXTENSION);
        checkinterval = ec.getArgument(args,cc.CHECKINTERVAL);
        timeZone    = ec.getArgument(args,cc.TIMEZONE).toLowerCase();

        menuno   = ec.getArgument(args,menusp.MnuConstants.MENUNO);
        mversion = ec.getArgument(args,menusp.MnuConstants.MVERSION);
        if (dbg) System.out.println(sub+"fileName = "+fileName);
        if (dbg) System.out.println(sub+"screen   = "+screen);
        if (dbg) System.out.println(sub+"version  = "+version);
        if (dbg) System.out.println(sub+"extension  = "+extension);
    } // public void getArgParms()

    /**
     * Get the parameters infomation from data file (std format)
     * @return  void
     */
    void getStdHeaderInfo() {

        String sub = "<br>getStdHeaderInfo: ";

        // intialise notes
        for (int i = 0; i < notes.length; i++) notes[i] = "";

        line = ec.getNextValidLine(file) + "      ";
        //String text = line.substring(0,3);
        int notesNum = 0;
        while (!line.startsWith("DAT")) {

            if (dbg) System.out.println(sub+"1line = " + line);
            //if (dbg) System.out.println(sub+"text = " + text);

            if (line.startsWith("(a)") || line.startsWith("(b)") ||     //ub02
                line.startsWith("(c)") || line.startsWith("(d)") ||     //ub02
                line.startsWith("(e)") || line.startsWith("--")) {      //ub02
                notes[notesNum] = line.substring(3).trim();             //ub02
                if ((notes[notesNum].length() > 2) && notes[notesNum].startsWith("--")) {   //ub02
                    notes[notesNum] = notes[notesNum].substring(2).trim();                  //ub02
                } // if ((notes[notesNum].length() > 2) || notes[notesNum].startsWith("--"))//ub02
                if (dbg) System.out.println(sub+"notes[notesNum] = " +    //ub02
                    notesNum + " " + notes[notesNum]);                  //ub02
                notesNum++;                                             //ub02
            } // if (line.startsWith("(a)") ...                         //ub02

/*
            if (line.startsWith("(a)")) {
                notes[0] = line.substring(3).trim();
                if ((notes[0].length() > 2) || notes[0].startsWith("--")) {
                    notes[0] = notes[0].substring(2).trim();
                } // if ((notes[0].length() > 2) || notes[0].startsWith("--"))
                if (dbg) System.out.println(sub+"notes[0] = " + notes[0]);
            } // if ("(a)".equals(text))

            if (line.startsWith("(b)")) {
                notes[1] = line.substring(3).trim();
                if ((notes[1].length() > 2) || notes[1].startsWith("--")) {
                    notes[1] = notes[1].substring(2).trim();
                } // if ("--".equals(notes[1].substring(0,2))
                if (dbg) System.out.println(sub+"notes[1] = " + notes[1]);
            } // if ("(a)".equals(text))

            if (line.startsWith("(c)")) {
                notes[2] = line.substring(3).trim();
                if ((notes[2].length() > 2) || notes[2].startsWith("--")) {
                    notes[2] = notes[2].substring(2).trim();
                } // if ("--".equals(notes[2].substring(0,2))
                if (dbg) System.out.println(sub+"notes[2] = " + notes[2]);
            } // if ("(a)".equals(text))

            if (line.startsWith("(d)")) {
                notes[3] = line.substring(3).trim();
                if ((notes[3].length() > 2) || notes[3].startsWith("--")) {
                    notes[3] = notes[3].substring(2);
                } // if ("--".equals(notes[3].substring(0,2))
                if (dbg) System.out.println(sub+"notes[3] = " + notes[3]);
            } // if ("(a)".equals(text))

            if (line.startsWith("(e)")) {
                notes[4] = line.substring(3).trim();
                if ((notes[4].length() > 2) || notes[4].startsWith("--")) {
                    notes[4] = notes[4].substring(2).trim();
                } // if ("--".equals(notes[4].substring(0,2))
                if (dbg) System.out.println(sub+"notes[4] = " + notes[4]);
            } // if ("(a)".equals(text))
*/

            // publication ref
            if (line.startsWith("REP")) {
                pubRef = line.substring(13).trim();
            } // if ("REP".equals(text))

            // station name
            if (line.startsWith("STA")) {
                stationName = line.substring(13).trim();
                if (dbg) System.out.println(sub+"stationName = "+stationName);
            } // if ("STA".equals(text))

            // instrument type
            if (line.startsWith("Ins")) {
                instrType = line.substring(13).trim();
                if (dbg) System.out.println(sub+"instrType = "+instrType);
            } // if ("Ins".equals(text))

            // deployment
            if (line.startsWith("Dep")) {
                String temp = line.substring(13).trim();
                StringTokenizer t = new StringTokenizer(temp, "-");
                instrNo = t.nextToken();
                deploymentNo = t.nextToken();
                //instrNo = line.substring(13,20).trim();
                //deploymentNo = line.substring(21).trim();
                if (dbg) System.out.println(sub+"instrNo "+instrNo);
                if (dbg) System.out.println(sub+"deploymentNo "+deploymentNo);
            } // if ("Dep".equals(text))

            // latitude
            if (line.startsWith("Lat")) {
                //latitudeD = line.substring(13,18).trim();
                //latitudeM = line.substring(18).trim();
                String temp = line.substring(13).trim();
                StringTokenizer t = new StringTokenizer(temp, " ");
                latitudeD = t.nextToken();
                latitudeM = t.nextToken();
                if (dbg) System.out.println(sub+"latitudeD "+latitudeD);
                if (dbg) System.out.println(sub+"latitudeM "+latitudeM);
                // convert
                int latitudeDegrees = new Integer(latitudeD).intValue();
                float latitudeMin   = new Float(latitudeM).floatValue();
                latitude = latitudeDegrees + (latitudeMin / 60);
                //change latitude to 5 decimal places only
                if (dbg) System.out.println(sub+"##latitude "+latitude);
                latitude = Float.parseFloat(ec.frm(latitude,7,5));
                if (dbg) System.out.println(sub+"##latitude "+latitude);
            } // if ("Lat".equals(text))

            // longitude
            if (line.startsWith("Lon")) {
                //longitudeD = line.substring(13,18).trim();
                //longitudeM = line.substring(18).trim();
                String temp = line.substring(13).trim();
                StringTokenizer t = new StringTokenizer(temp, " ");
                longitudeD = t.nextToken();
                longitudeM = t.nextToken();
                if (dbg) System.out.println(sub+"longitudeD "+longitudeD);
                if (dbg) System.out.println(sub+"longitudeM "+longitudeM);
                // convert
                int longitudeDegrees = new Integer(longitudeD).intValue();
                float longitudeMin   = new Float(longitudeM).floatValue();
                longitude = longitudeDegrees + (longitudeMin / 60);
                //change longitude to 5 decimal places only
                if (dbg) System.out.println(sub+"##longitude "+longitude);
                longitude = Float.parseFloat(ec.frm(longitude,7,5));
                if (dbg) System.out.println(sub+"##longitude "+longitude);
            } // if ("Lat".equals(text))

            // meter depth
            if (line.startsWith("Met")) {
                String temp = line.substring(13).trim();
                StringTokenizer t = new StringTokenizer(temp, " m");
                meterDepth = Float.parseFloat(t.nextToken());
                //if (dbg) System.out.println(sub+"t "+t);
                //meterDepth = java.lang.Math.round(t);
                if (dbg) System.out.println(sub+"meterDepth "+meterDepth);
            } // if ("Met".equals(text)) {

            // bottom depth
            if (line.startsWith("Bot")) {
                String temp = line.substring(13).trim();
                StringTokenizer t = new StringTokenizer(temp, " m");
                float tt = Float.parseFloat(t.nextToken());
                if (dbg) System.out.println(sub+"t "+tt);
                bottomDepth = java.lang.Math.round(tt);
                if (dbg) System.out.println(sub+"bottomDepth "+bottomDepth);
            } // if ("Bot".equals(text))

            line = ec.getNextValidLine(file) + "      ";
            //text = line.substring(0,3);
        } // while (!"DAT".equals(text))
    } // getStdHeaderInfo()


    /**
     * Get the parameters infomation from data file. (cur format)
     * @return  void
     */
    void getCurHeaderInfo() {

        String sub = "<br>getCurHeaderInfo: ";

        // intialise notes
        for (int i = 0; i < notes.length; i++) notes[i] = "";
        int notesCount = 0;

        line = ec.getNextValidLine(file) + "              ";
        //String text = line.substring(0,3);
        //String text2 = line.substring(0,13);

        //while (!"DAT".equals(text)) {
        while (!line.startsWith("DAT")) {

            if (dbg) System.out.println(sub+"1line = " + line);
            //if (dbg) System.out.println(sub+"text = " + text + "/" + text2);
            //ec.writeFileLine(progressFile, "getCurHeaderInfo: line = " + line);
            //ec.writeFileLine(progressFile, "getCurHeaderInfo: text = " + text + "/" + text2);
            //if ("-- ".equals(text)) {
            if (line.startsWith("-- ")) {
                notes[notesCount] = line.substring(3).trim();
                if (dbg) System.out.println(sub+"notes[notesCount] = " +
                    notes[notesCount] + " " + notesCount);
                notesCount++;
                //if (notes[notesCount].length() > 2) {
                //    notes[notesCount] = notes[notesCount].substring(2);
                //} // if ("--".equals(notes[0].substring(0,2))
            } // if (line.startsWith("-- "))

            // station name
            //if ("MOORING NAME ".equals(text2)) {
            if (line.startsWith("MOORING NAME ")) {
                stationName = line.substring(14).trim();
                if (dbg) System.out.println(sub+"stationName = "+stationName);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: stationName = " + stationName);
            } // if (line.startsWith("MOORING NAME "))

            // instrument type
            //if ("INSTRUMENT TP".equals(text2)) {
            if (line.startsWith("INSTRUMENT TP")) {
                instrType = line.substring(14).trim();
                if (dbg) System.out.println(sub+"instrType = "+instrType);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: instrType = " + instrType);
            } // if (line.startsWith("INSTRUMENT TP"))

            // instrument number
            //if ("INSTRUMENT NO".equals(text2)) {
            if (line.startsWith("INSTRUMENT NO")) {
                instrNo = line.substring(14).trim();
                if (dbg) System.out.println(sub+"instrNo "+instrNo);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: instrNo = " + instrNo);
            } // if (line.startsWith("INSTRUMENT NO"))

            // deployment
            //if ("DEPLOYMENT NO".equals(text2)) {
            if (line.startsWith("DEPLOYMENT NO")) {
                deploymentNo = line.substring(14).trim();
                if (dbg) System.out.println(sub+"deploymentNo "+deploymentNo);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: deploymentNo = " + deploymentNo);
            } // if (line.startsWith("DEPLOYMENT NO"))

            // latitude
            //if ("LAT".equals(text)) {
            if (line.startsWith("LAT")) {
                String temp = line.substring(14).trim();
                latitude = Float.parseFloat(temp);
                int latDegrees = (int)latitude;
                float latMinutes   = (latitude - latDegrees) * 60f;
                latitudeD = String.valueOf(latDegrees);
                latitudeM = String.valueOf(latMinutes);
                //change latitude to 5 decimal places only
                //if (dbg) System.out.println(sub+"##latitude "+latitude);
                //latitude = Float.parseFloat(ec.frm(latitude,7,5));
                //if (dbg) System.out.println(sub+"##latitude "+latitude);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: latitude = " + latitude);
            } // if (line.startsWith("LAT")) {

            // longitude
            //if ("LON".equals(text)) {
            if (line.startsWith("LON")) {
                String temp = line.substring(14).trim();
                longitude = Float.parseFloat(temp);
                int lonDegrees = (int)longitude;
                float lonMinutes   = (longitude - lonDegrees) * 60f;
                longitudeD = String.valueOf(lonDegrees);
                longitudeM = String.valueOf(lonMinutes);
                //change longitude to 5 decimal places only
                //if (dbg) System.out.println(sub+"##longitude "+longitude);
                //longitude = Float.parseFloat(ec.frm(longitude,7,5));
                //if (dbg) System.out.println(sub+"##longitude "+longitude);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: longitude = " + longitude);
            } // if (line.startsWith("LAT"))

            // meter depth
            //if ("INSTR. DEPTH ".equals(text2)) {
            if (line.startsWith("INSTR. DEPTH ")) {
                String temp = line.substring(14).trim();
                meterDepth = Float.parseFloat(temp);
                //if (dbg) System.out.println(sub+"t = " + t);
                //meterDepth = java.lang.Math.round(t);
                if (dbg) System.out.println(sub+"meterDepth = " + meterDepth);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: meterDepth = " + meterDepth);
            } // if (line.startsWith("INSTR. DEPTH "))

            // bottom depth
            //if ("MOORING DEPTH".equals(text2)) {
            if (line.startsWith("MOORING DEPTH")) {
                String temp = line.substring(14).trim();
                float t = Float.parseFloat(temp);
                if (dbg) System.out.println(sub+"t "+t);
                bottomDepth = java.lang.Math.round(t);
                if (dbg) System.out.println(sub+"bottomDepth "+bottomDepth);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: bottomDepth = " + bottomDepth);
            } // if (line.startsWith("MOORING DEPTH"))

            //if ("CLIENT       ".equals(text2)) {
            if (line.startsWith("CLIENT       ")) {
                clientName = line.substring(14).trim();
                if (dbg) System.out.println(sub+"clientName "+clientName);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: clientName = " + clientName);
                // try and get the client code
                where = EdmClient2.NAME +" like '"+ clientName + "'";
                if (dbg) System.out.println(sub+"EdmClient2 where = " + where);
                EdmClient2[] client = new EdmClient2().get(where);
                if (client.length > 0) {
                    if (dbg) System.out.println(sub+"client = " + client[0]);
                    clientCode = client[0].getCode();
                    if (dbg) System.out.println(sub+"clientCode = " + clientCode);
                } // if (client.length > 0)
            } // if (line.startsWith("CLIENT       "))

            line = ec.getNextValidLine(file) + "              ";
            //text = line.substring(0,3);
            //text2 = line.substring(0,13);
        } // while (!line.startsWith("DAT"))

    } // getCurHeaderInfo()


    /**
     * check with header parameters were not supplied
     */
    DynamicTable checkHeaderInfo() {

        DynamicTable subTab0 = new DynamicTable(1);
        subTab0.setBorder(1);
        subTab0.setCenter();
        subTab0.setFrame(ITableFrame.BOX);

        subTab0.addRow(ec.crSpanColsRow("Fatal Error: Header errors.",2,true
            ,"red", IHAlign.CENTER,"+1" ));

        TableRow row0 = new TableRow();
        row0 = new TableRow();

        row0.addCell(new TableHeaderCell(new SimpleItem("Field Name.")));
        row0.addCell(new TableHeaderCell(new SimpleItem("Field Status.")));
        subTab0.addRow(row0);

        //check which parameters were not supplied
        int headCnt = 0;

        if ("".equals(stationName)) {
            headCnt++;
            row0 = new TableRow();
            row0.addCell(new TableDataCell(new SimpleItem("station name").setCenter()));
            row0.addCell(new TableDataCell(new SimpleItem("not supplied").setCenter()));
            subTab0.addRow(row0);
        } // if ("".equals(stationName))

        if ("".equals(instrType)) {
            headCnt++;
            row0 = new TableRow();
            row0.addCell(new TableDataCell(new SimpleItem("instrument type").setCenter()));
            row0.addCell(new TableDataCell(new SimpleItem("not supplied").setCenter()));
            subTab0.addRow(row0);
        } // if ("".equals(instrType))

        if ("".equals(instrNo)) {
            headCnt++;
            row0 = new TableRow();
            row0.addCell(new TableDataCell(new SimpleItem("instrument no").setCenter()));
            row0.addCell(new TableDataCell(new SimpleItem("not supplied").setCenter()));
            subTab0.addRow(row0);
        } // if ("".equals(instrNo))

        if ("".equals(deploymentNo)) {
            headCnt++;
            row0 = new TableRow();
            row0.addCell(new TableDataCell(new SimpleItem("deployment no").setCenter()));
            row0.addCell(new TableDataCell(new SimpleItem("not supplied").setCenter()));
            subTab0.addRow(row0);
        } // if ("".equals(deploymentNo))

        if ("".equals(latitudeD)) {
            headCnt++;
            row0 = new TableRow();
            row0.addCell(new TableDataCell(new SimpleItem("latitude").setCenter()));
            row0.addCell(new TableDataCell(new SimpleItem("not supplied").setCenter()));
            subTab0.addRow(row0);
        } // if ("".equals(latitudeD))

        if ("".equals(longitudeD)) {
            headCnt++;
            row0 = new TableRow();
            row0.addCell(new TableDataCell(new SimpleItem("deployment no").setCenter()));
            row0.addCell(new TableDataCell(new SimpleItem("not supplied").setCenter()));
            subTab0.addRow(row0);
        } // if ("".equals(deploymentNo))

        if (meterDepth == -999) {
            headCnt++;
            row0 = new TableRow();
            row0.addCell(new TableDataCell(new SimpleItem("meter depth").setCenter()));
            row0.addCell(new TableDataCell(new SimpleItem("not supplied").setCenter()));
            subTab0.addRow(row0);
        } // if ("".equals(longitudeD))

        if (bottomDepth == -999) {
            headCnt++;
            row0 = new TableRow();
            row0.addCell(new TableDataCell(new SimpleItem("bottom depth").setCenter()));
            row0.addCell(new TableDataCell(new SimpleItem("not supplied").setCenter()));
            subTab0.addRow(row0);
        } // if ("".equals(longitudeD))


        if (headCnt > 0 ) {
            headerError = true;
            error = true;
        } //if (headCnt > 0 ) {

        return subTab0;
    } // checkHeaderInfo


    /**
     * read input file *.std
     * @return  void
     */
    private void openInputFile(String fileName2)   {
        try {
            file = new RandomAccessFile(fileName2, "r");
        } catch (Exception e) {
            System.out.println("<br>openInputFile: PreLoadCURDataFrame Error: " + e.toString());
            e.printStackTrace();
        }  //  try-catch (Exception e)
    } // private void openInputFile()   {


    /**
     * close file
     * @return  void
     */
    private void closeInputFile()   {
        try {
            file.close();
        } catch (Exception e) {
            System.out.println("<br>closeInputFile: PreLoadCURDataFrame Error: " + e.toString());
            e.printStackTrace();
        }  // try-catch
    } // private void closeInputFile()   {


    /**
     * Function calculate the time interval between the two records lines.
     * @return  void
     */
    private DynamicTable startEndInteval()   {

        String sub = "<br>startEndInteval: ";

        Timestamp dateTimeFirst;
        Timestamp dateTimeSecond;

        // Create sub table                                         //
        DynamicTable subTab1  = new DynamicTable(1);
        subTab1.setBorder(1);
        subTab1.setCenter();
        subTab1.setFrame(ITableFrame.BOX);

        if ("Y".equals(checkinterval)) {
            subTab1.addRow(ec.crSpanColsRow("Fatal Error: Time intervals differ.",
                4,true,"red",IHAlign.CENTER,"+1" ));
        } else {
            subTab1.addRow(ec.crSpanColsRow("Warning: Time intervals differ.",
                4,true,"green",IHAlign.CENTER,"+1" ));
        }

        TableRow row = new TableRow();
        row = new TableRow();

        row.addCell(new TableHeaderCell(new SimpleItem("Record no.")));
        row.addCell(new TableHeaderCell(new SimpleItem("Previous Date")));
        row.addCell(new TableHeaderCell(new SimpleItem("Date")));
        row.addCell(new TableHeaderCell(new SimpleItem("Time Interval")));
        subTab1.addRow(row);

        //get first date-time
        line = ec.getNextValidLine(file);
        if (dbg2) System.out.println(sub+"1line "+line);
        //ec.writeFileLine(progressFile, "startEndInteval: line1 = " + line);

        dateTimeFirst = getTSDateTime();
        long d1 = dateTimeFirst.getTime();

        lineCnt++;
        if (dbg2) System.out.println(sub+"record counter "+lineCnt);

        if (dbg2) System.out.println(sub+"2line "+line);

        // keep the first date
        dataStart = dateTimeFirst;
        dataStartS = dataStart.toString().substring(0,16);

        //get 2nd date-time
        line = ec.getNextValidLine(file);
        if (dbg2) System.out.println(sub+"3line "+line);
        //ec.writeFileLine(progressFile, "startEndInteval: line2 = " + line);
        dateTimeSecond = getTSDateTime();
        long d2 = dateTimeSecond.getTime();

        lineCnt++;
        if (dbg2) System.out.println(sub+"dateTimeSecond "+dateTimeSecond);
        if (dbg2) System.out.println(sub+"d1="+d1+" : d2="+d2);

        timeInterval = d2 - d1;      // get time interval between 1st & second date
        if (dbg2) System.out.println(sub+"timeInterval = "+timeInterval);

        // keep for the last date
        dataEnd = dateTimeSecond;
        dataEndS   = dataEnd.toString().substring(0,16);

        while (!ec.eof(file)) {

            dateTimeFirst = dateTimeSecond;
            d1 = d2;
            long prevtimeInterval    = timeInterval;

            line = ec.getNextValidLine(file);
            if (dbg2) System.out.println(sub+"line  = *"+line+"*");
            //ec.writeFileLine(progressFile, "startEndInteval: line = " + line);

            // getTime variable...the number of milliseconds
            //long dWs = 0;
            //Timestamp nextRecordDateWs;

            //long timeIntervalWs = 0;

            if (dbg2) System.out.println(sub+"before if line = END *"+line);
            if ("END".equals(line.substring(0,3))) {
                if (dbg2) System.out.println(sub+"END reached - break");
                //exit;
                break;
            } else {

                if (lineCnt >= 2  && (line.length() > 12) ) {

                    lineCnt++;

                    if (dbg2) System.out.println(sub+"** lineCnt >= 2 "+lineCnt);
                    dateTimeSecond = getTSDateTime();
                    if (dbg2) System.out.println(sub+"dateTimeSecond2 "+dateTimeSecond);

                    //dStart = nextRecordDateWs.toString().substring(0,16);

                    d2 = dateTimeSecond.getTime();
                    //dateTimeFirst = dateTimeSecond;
                    //long dateTimeSecondWs = dateTimeFirst.getTime();

                    //dateTimeSecond = nextRecordDateWs;

                    if (dbg2) System.out.println(sub+"d2 "+d2);

                    // get timeInterval...
                    timeInterval = d2 - d1;

                    if (dbg2) System.out.println(sub+"d2="+d2+" : d1="+d1);
                    if (dbg2) System.out.println(sub+"lineCnt = "+lineCnt+" "+
                        timeInterval);

                    //check if new timeInterval are there same as the first two
                    //if not abort programme

                    if (timeInterval != prevtimeInterval) {
                        if (dbg2) System.out.println(sub+"lineCnt = "+lineCnt);

                        timeDiff=true;

                        String startDate = String.valueOf(dateTimeFirst).substring(0,16);
                        String endDate   = String.valueOf(dateTimeSecond).substring(0,16);

                        TableRow nrow = new TableRow();
                        nrow.addCell(new TableDataCell(new SimpleItem(lineCnt).setCenter()));
                        nrow.addCell(new TableDataCell(
                            new SimpleItem(startDate).setCenter()));
                        nrow.addCell(new TableDataCell(
                            new SimpleItem(endDate).setCenter()));
                        nrow.addCell(new TableDataCell(
                            new SimpleItem(timeInterval / 60000)));

                        subTab1.addRow(nrow);

                        if (dbg2) System.out.println(sub+"rejected record "+lineCnt+
                            " dateTimeFirst->"+dateTimeFirst+" dateTimeSecond->"+
                                dateTimeSecond+" "+timeInterval);

                        if (dbg2) System.out.println(sub+"rejected record line "+line);

                    } //if (timeIntervalWs != prevtimeInterval) {

                    // keep for the last date
                    dataEnd = dateTimeSecond;
                    if (dbg2) System.out.println(sub+"** dataEnd "+dataEnd);

                    dataEndS   = dataEnd.toString().substring(0,16);
                    if (dbg2) System.out.println(sub+"** dataEndS "+dataEndS);

                } //if (lineCnt > 2) {
            } //if ("END".equals(line.substring(0,3))) {
        } // while (!ec.eof(file)) {
        if (dbg2) System.out.println(sub+"** at end "+lineCnt);

        if ("Y".equals(checkinterval) && timeDiff) {
            error = true;
        } //if ("Y".equals(chechinterval) && timeDiff) {

        return subTab1;
    } // startEndInteval()   {


    /**
     * get the Timestamp for date and time, and convert to UTC
     */
    Timestamp getTSDateTime() {

        String sub = "<br>getTSDateTime: ";

        Timestamp date;

        StringTokenizer token = new StringTokenizer(line," \t");

        if (".std".equals(extension)) {
            String year  = token.nextToken(); //line.substring( 0, 4);
            String month = token.nextToken().replace(' ','0');//line.substring( 5, 7).replace(' ','0');
            String day   = token.nextToken().replace(' ','0');//line.substring( 8,10).replace(' ','0');
            String hour  = token.nextToken().replace(' ','0');//line.substring(11,13).replace(' ','0');
            String min   = token.nextToken().replace(' ','0');//line.substring(14,16).replace(' ','0');
            date = Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+
                ":"+min+":00.0");
            if (dbg2) System.out.println(sub+"year "+year+" month "+month+" day "+
                day+" hour "+hour+" min "+min);
        } else {
            String datum = token.nextToken();
            String time = token.nextToken();
            date = Timestamp.valueOf(datum+" "+time+":00.0");
        } // if (".std".equals(extension))
        if (dbg2) System.out.println(sub+"** date1 "+date);

        if ("sast".equals(timeZone)) {
            date = getUTCDateTIme(date);
        } // if ('sast'.equals(timeZone))
        if (dbg2) System.out.println(sub+"** date2 "+date);

        return date;

    } // getTSDateTime


    /**
     * Check if data has been loaded
     * @return the DynamicTable
     */
    private DynamicTable isDataLoaded() {

        String sub = "<br>isDataLoaded: ";

        DynamicTable subTab3 = new DynamicTable(1);
        subTab3.setBorder(1);
        subTab3.setCenter();
        subTab3.setFrame(ITableFrame.BOX);

        if (dbg) System.out.println(sub+"## dataEnd: "+dataEnd);
        //if (dbg) System.out.println(sub+"## endTimeDate: "+endTimeDate);

        if (dbg) System.out.println(sub+"## dataStart: "+dataStart+":00.0");
        if (dbg) System.out.println(sub+"## dataEnd: "+dataEnd+":00.0");

        /*
        where  =
            CurDepth.SPLDEP+" = "+meterDepth+" and "+
            CurDepth.DATE_TIME_START+" = "+Tables.getDateFormat(dataEnd)+
            " and "+CurDepth.DATE_TIME_END+" = "+Tables.getDateFormat(endTimeDate);
        */

        where  =
            CurDepth.SPLDEP+" = "+meterDepth+" and "+
            CurDepth.DATE_TIME_START+" = "+Tables.getDateFormat(dataStart)+
            " and "+CurDepth.DATE_TIME_END+" = "+Tables.getDateFormat(dataEnd);

        //Tables.getDateFormat(startDate+" 00:00:00")

        if (dbg) System.out.println(sub+"where clause isDataLoaded(): "+where);
        CurDepth depth[] = new CurDepth().get(where);


        if (depth.length > 0 ) {
            //set loadFlag
            loadFlag = true;
            depthLoaded = true;

            depthCode = depth[0].getCode();

            // get depth code

            //write message to screen "Data already loaded.
            // create the html page
            subTab3.addRow(ec.crSpanColsRow("Warning: This data set has already been loaded."+
                " <br>If you continue this data will be overwritten!",2,true,"green",
                IHAlign.CENTER,"+1" ));

            //this.addItem(subTab3);
        } //if (depth.length > 0 ) {

        return subTab3;
    } //private void isDataLoaded() {

    /**
     * Check if data has been loaded
     * @return DynamicTable
     */
    DynamicTable isMooringStored() {

        String sub = "<br>isMooringStored: ";

        DynamicTable subTab4 = new DynamicTable(1);
        subTab4.setBorder(1);
        subTab4.setCenter();
        subTab4.setFrame(ITableFrame.BOX);

        // check this depth time range got more than one mooring range.
        if (tmp) System.out.println(sub+"dataStartS: "+dataStartS+":00.0");
        if (tmp) System.out.println(sub+"dataEndS: "+dataEndS+":00.0");

        where =
            CurMooring.LATITUDE +" = "+ec.frm(latitude,7,5) +" and "+
            CurMooring.LONGITUDE+" = "+ec.frm(longitude,7,5)+" and "+
            CurMooring.STNDEP   +" = "+bottomDepth+" and (("+

            /*
            CurMooring.DATE_TIME_START+" <= "+
                Tables.getDateFormat(dataEnd)+" and "+
            CurMooring.DATE_TIME_END+" >= "+
                Tables.getDateFormat(dataEnd)+") or ("+

            CurMooring.DATE_TIME_START+" <= "+
                Tables.getDateFormat(endTimeDate)+" and "+
            CurMooring.DATE_TIME_END+" >= "+
                Tables.getDateFormat(endTimeDate)+") or ("+

            CurMooring.DATE_TIME_START+" > "+
                Tables.getDateFormat(dataEnd)+" and "+
            CurMooring.DATE_TIME_END+" < "+
                Tables.getDateFormat(endTimeDate)+") )";
            */

            CurMooring.DATE_TIME_START+" <= "+
                Tables.getDateFormat(dataStart)+" and "+
            CurMooring.DATE_TIME_END+" >= "+
                Tables.getDateFormat(dataStart)+") or ("+

            CurMooring.DATE_TIME_START+" <= "+
                Tables.getDateFormat(dataEnd)+" and "+
            CurMooring.DATE_TIME_END+" >= "+
                Tables.getDateFormat(dataEnd)+") or ("+

            CurMooring.DATE_TIME_START+" > "+
                Tables.getDateFormat(dataStart)+" and "+
            CurMooring.DATE_TIME_END+" < "+
                Tables.getDateFormat(dataEnd)+") )";

        if (tmp) System.out.println(sub+"1st where clause: "+where);

        //CurMooring mooringDateTimeRange[] = new CurMooring().get(where);
        CurMooring tmpMoor = new CurMooring();
        CurMooring mooringDateTimeRange[] = tmpMoor.get(where);
        if (dbg) System.out.println(sub+"tmp.getSelStr() = " + tmpMoor.getSelStr());
        if (tmp) System.out.println(sub+"mooringDateTimeRange.length "+
            mooringDateTimeRange.length);

        if (dbg) System.out.println(sub+"before mooringDateRange if: length = " +
            mooringDateTimeRange.length);

        if (mooringDateTimeRange.length > 0) {
            if (dbg) System.out.println(sub+"within mooringDateRange if ");

            if (dbg) System.out.println(sub+"mooringDateTimeRange: "+
                mooringDateTimeRange.length);

            mooringExist   = true;

            mooringStart     = mooringDateTimeRange[0].getDateTimeStart();
            mooringEnd       = mooringDateTimeRange[0].getDateTimeEnd();
            mooringStartS    = String.valueOf(mooringStart).substring(0,16);
            mooringEndS      = String.valueOf(mooringEnd).substring(0,16);

            if (dbg) System.out.println(sub+"Mooring start :"+mooringStart);
            if (dbg) System.out.println(sub+"Mooring end   :"+mooringEnd);
            if (dbg) System.out.println(sub+"dataStart  :"+dataStart);
            if (dbg) System.out.println(sub+"dataEnd    :"+dataEnd);
            if (dbg) System.out.println(sub+"mooringStartS :"+mooringStartS);
            if (dbg) System.out.println(sub+"mooringEndS   :"+mooringEndS);
            if (dbg) System.out.println(sub+"dataStartS    :"+dataStartS);
            if (dbg) System.out.println(sub+"dataEndS      :"+dataEndS);

            mooringCode      = mooringDateTimeRange[0].getCode();
            clientCode       = mooringDateTimeRange[0].getClientCode();
            planamCode       = mooringDateTimeRange[0].getPlanamCode();
            //stndep           = mooringDateTimeRange[0].getStndep();
            stnnam           = mooringDateTimeRange[0].getStnnam("");
            arenam           = mooringDateTimeRange[0].getArenam("");
            pubRef           = mooringDateTimeRange[0].getPublicationRef("");
            surveyId         = mooringDateTimeRange[0].getSurveyId("");

            getClientNamePlatform(clientCode,planamCode);

            if (dbg) System.out.println(sub+"stnnam   :"+stnnam);
            if (dbg) System.out.println(sub+"arenam   :"+arenam);
            if (dbg) System.out.println(sub+"pubRef   :"+pubRef);

            // if there is more than 1 mooring display message to user and
            // abort programme.
            if (mooringDateTimeRange.length > 1) {
                if (dbg) System.out.println(sub+"mooringDateTimeRange: "+
                    mooringDateTimeRange.length);
                subTab4.addRow(ec.crSpanColsRow("Fatal Error: This data set straddles"+
                    " over more than one mooring.",2,true,"red", IHAlign.CENTER,"+1" ));

                // set error flag true.
                error = true;
                displayTable = true;

            } else if (mooringDateTimeRange.length <= 1)  {

                if (tmp) System.out.println(sub+"tmp mooringStart "+mooringStart);
                if (tmp) System.out.println(sub+"tmp mooringEnd "+mooringEnd);

                if (tmp) System.out.println(sub+"tmp dataStartS "+dataStartS );
                if (tmp) System.out.println(sub+"tmp dataEndS "+dataEndS );

                header          = "Warning: The date-time range is different:";
                colour          = "green";
                leftText        = "Stored Mooring:";
                String bottomText = "";

                if ((!mooringStart.equals(dataStart)) || (!mooringEnd.equals(dataEnd))) {

                    // data range values.
                    if ((mooringStart.after(dataStart)) &&
                        (mooringEnd.before(dataEnd)) ) {

                        header += "<br>If you continue, the mooring start and "+
                            "end date-time will be updated.";
                        bottomText +=
                            "Both data start and end dates overlap the mooring range.";
                        newMooringStart = dataStartS;
                        newMooringEnd   = dataEndS;

                    } else if (mooringStart.after(dataStart)) {

                        header += "<br>If you continue, the mooring start "+
                            "date-time will be updated.";
                        bottomText +=
                        "The data start date-time overlaps the mooring range.";
                        newMooringStart = dataStartS;

                    } else if (mooringEnd.before(dataEnd)) {

                        header += "<br>If you continue, the mooring "+
                            "end date-time will be updated.";
                        bottomText += "The data end date-time overlaps the "+
                            "mooring range.";
                        newMooringEnd = dataEndS;

                    } // if ((mooringStart.after(end

                    if (dbg) System.out.println(sub+"bottomText"+bottomText);

                    subTab4 = dateTimeTable(
                        header,colour,leftText,mooringStart,mooringEnd,dataStart,dataEnd,bottomText);

                    displayTable = true;

                    if (dbg) System.out.println(sub+"mooringStartS :"+mooringStartS);
                    if (dbg) System.out.println(sub+"mooringEndS   :"+mooringEndS);
                    if (dbg) System.out.println(sub+"dataStartS    :"+dataStartS);
                    if (dbg) System.out.println(sub+"dataEndS      :"+dataEndS);

               }//if ((!mooringStart.equals(

            } // mooring == 1
        } //if (mooringDateTimeRange.length > 0) {

        return subTab4;
    } // DynamicTable isMooringStored() {


    /**
     * Check if depth range not overlap the already stored ranges.
     * @return DynamicTable
     */
    DynamicTable isPartDepth() {

        String sub = "<br>isPartDepth: ";

        DynamicTable subTab5 = new DynamicTable(1);
        subTab5.setBorder(1);
        subTab5.setCenter();
        subTab5.setFrame(ITableFrame.BOX);

        where = CurDepth.SPLDEP+" = "+meterDepth  +" and "+
                CurDepth.MOORING_CODE+" = "+mooringCode;

        CurDepth depth[] = new CurDepth().get(where);

        if (tmp) System.out.println(sub+"tmp depth where "+where);
        if (tmp) System.out.println(sub+"tmp depth.length "+depth.length);

        // is_Ovelap testing.
        if (depth.length > 0) {

            Timestamp depthStart = depth[0].getDateTimeStart();
            Timestamp depthEnd   = depth[0].getDateTimeEnd();

            if (tmp) System.out.println(sub+"tmp depthStart : "+depthStart);
            if (tmp) System.out.println(sub+"tmp depthEnd : "+depthEnd);

            if (tmp) System.out.println(sub+"tmp dataStartS : "+dataStartS);
            if (tmp) System.out.println(sub+"tmp dataEndS : "+dataEndS);

            if (tmp) System.out.println(sub+"tmp !dataStart.before(depthStart) = " + !dataStart.before(depthStart));
            if (tmp) System.out.println(sub+"tmp !dataStart.after(depthEnd) = " + !dataStart.after(depthEnd));
            if (tmp) System.out.println(sub+"tmp !dataEnd.before(depthStart) = " + !dataEnd.before(depthStart));
            if (tmp) System.out.println(sub+"tmp !dataEnd.after(depthEnd) = " + !dataEnd.after(depthEnd));
            if (tmp) System.out.println(sub+"tmp dataStart.before(depthStart) = " + dataStart.before(depthStart));
            if (tmp) System.out.println(sub+"tmp dataEnd.after(depthEnd) = " + dataEnd.after(depthEnd));

            if ( (!dataStart.before(depthStart)) && (!dataStart.after(depthEnd))
                 || (!dataEnd.before(depthStart)) && (!dataEnd.after(depthEnd))
                 || (dataStart.before(depthStart)) && (dataEnd.after(depthEnd)) ) {

                header          =
                    "Fatal Error: This data overlaps with with a loaded depth";
                colour          = "red";
                leftText        = "Stored depth:";

                subTab5 =
                dateTimeTable(
                    header,colour,leftText,depthStart,depthEnd,dataStart,
                    dataEnd,"");
                displayTable = true;
                error=true;

            } //if ((!depthStart.before(dataEnd)) &&

            partDepth = true;

        } //if (depth.length > 0) {

        return subTab5;
    } // DynamicTable isPartDepth() {


    /**
     * Get mooring data (info) from database.
     * @return DynamicTable
     */
    //Form getMooringData() {
    DynamicTable getMooringData() {
        DynamicTable subTab6 = new DynamicTable(1);
        subTab6.setBorder(1);
        subTab6.setCenter();
        subTab6.setFrame(ITableFrame.BOX);

        subTab6.addRow(ec.crSpanColsRow("Please Complete the Mooring Details.",
            2,true,"blue",IHAlign.CENTER,"+1" ));
        subTab6.addRow(ec.cr2ColRow("Description       : ",stationName));
        subTab6.addRow(ec.cr2ColRow("Latitude          : ",latitudeD+"° "+latitudeM+
            "\" = "+ec.frm(latitude,7,5)));
        subTab6.addRow(ec.cr2ColRow("Longitude         : ",longitudeD+"° "+longitudeM+
            "\" = "+ec.frm(longitude,7,5)));
        subTab6.addRow(ec.cr2ColRow("Bottom Depth      : ",bottomDepth));

        subTab6.addRow(ec.crSpanColsRow("Choose a client from the drop-down list, "+
            "or enter a new one.",2,true,"green",IHAlign.CENTER,"+1" ));
        subTab6.addRow(ec.cr2ColRow(
            "Existing Client:", createClientSelect(clientCode)));
        subTab6.addRow(ec.cr2ColRow("New Client:",cc.NEWCLIENTNAME,30));

        subTab6.addRow(ec.crSpanColsRow("Choose a platform from the drop-down list, "+
            "or enter a new one.",2,true,"green",IHAlign.CENTER,"+1" ));
        subTab6.addRow(ec.cr2ColRow(
            "Existing Platform:", createPlanamSelect(planamCode)));
        subTab6.addRow(ec.cr2ColRow("New Planam:",cc.NEWPLATFORMNAME,30));

        subTab6.addRow(ec.crSpanColsRow("Choose an institute from the drop-down list, "+
            "or enter a new one.",2,true,"green",IHAlign.CENTER,"+1" ));
        subTab6.addRow(ec.cr2ColRow("Institute:",createInstituteSelect(cc.INSTITCODE)));
        subTab6.addRow(ec.cr2ColRow("New Institute",cc.NEWINSTITUTE,50));
        subTab6.addRow(ec.cr2ColRow("New Institute Address",cc.NEWINSTADDRESS,50));

        subTab6.addRow(ec.crSpanColsRow("Choose a scientist from the drop-down list, "+
            "or enter a new one.",2,true,"green",IHAlign.CENTER,"+1" ));
        subTab6.addRow(ec.cr2ColRow("Scientist:",createScientistSelect()));
//        subTab6.addRow(ec.cr2ColRow("New Scientist Title",cc.NEWSCTITLE,10));
        subTab6.addRow(ec.cr2ColRow("New Scientist Initials",cc.NEWSCFIRSTNAME,10,10,"","e.g. Y Z"));
        subTab6.addRow(ec.cr2ColRow("New Scientist Surname",cc.NEWSCSURNAME,50));
        subTab6.addRow(ec.cr2ColRow("New Scientist Institute:",
            createInstituteSelect(cc.NEWSCINST)));

        subTab6.addRow(ec.crSpanColsRow("Choose a data type from the drop-down list",
            2,true,"green",IHAlign.CENTER,"+1" ));
        subTab6.addRow(ec.cr2ColRow("Data Type:",createDataTypeSelect()));

        subTab6.addRow(ec.crSpanColsRow("Please enter the following fields.",
            2,true,"green",IHAlign.CENTER,"+1" ));
        subTab6.addRow(ec.cr2ColRow("Project name:",cc.PRJNAM,30,100,""));  //ub01
        subTab6.addRow(ec.cr2ColRow("Station name:",cc.STNNAM,30,30,stationName));
        subTab6.addRow(ec.cr2ColRow("Area name:",cc.ARENAM,30));
        subTab6.addRow(ec.cr2ColRow("Publication Ref:",cc.PUBLREF,30, pubRef));

        return subTab6;
        //return form;
    } //DynamicTable getMooringData()


    /**
     * Display Mooring record.
     * @return DynamicTable.
     */
    DynamicTable displayMooringData() {

        String sub = "<br>displayMooringData: ";

        DynamicTable subTab7 = new DynamicTable(1);
        subTab7.setBorder(1);
        subTab7.setCenter();
        subTab7.setFrame(ITableFrame.BOX);

        if (dbg) System.out.println(sub+"mooringStartS "+mooringStartS);
        if (dbg) System.out.println(sub+"mooringEndS "+mooringEndS);

        if (dbg) System.out.println(sub+"platformName "+platformName);
        if (dbg) System.out.println(sub+"clientName "+clientName);

        subTab7.addRow(ec.crSpanColsRow("Stored Mooring Details.",
            2,true,"blue",IHAlign.CENTER,"+1" ));
        subTab7.addRow(ec.cr2ColRow("Description       : ",stationName));
        subTab7.addRow(ec.cr2ColRow("Latitude          : ",latitudeD+"° "+latitudeM+
            "\" = "+String.valueOf(latitude)));
        subTab7.addRow(ec.cr2ColRow("Longitude         : ",longitudeD+"° "+longitudeM+
            "\" = "+String.valueOf(longitude)));
        subTab7.addRow(ec.cr2ColRow("Bottom Depth      : ",bottomDepth));
        subTab7.addRow(ec.cr2ColRow("Client            : ",clientName));
        subTab7.addRow(ec.cr2ColRow("Platform          : ",platformName));
        subTab7.addRow(ec.cr2ColRow("Station name      : ",stnnam));
        subTab7.addRow(ec.cr2ColRow("Area name         : ",arenam));
        subTab7.addRow(ec.cr2ColRow("Publication Ref   : ",pubRef));

        subTab7.addRow(ec.cr2ColRow("Start Date        : ",String.valueOf(mooringStartS)));
        subTab7.addRow(ec.cr2ColRow("End Date          : ",String.valueOf(mooringEndS)));
        //subTab7.addRow(ec.cr2ColRow("Start Date        : ",String.valueOf(dataStartS)));
        //subTab7.addRow(ec.cr2ColRow("End Date          : ",String.valueOf(dataEndS)));

        if (dbg) System.out.println(sub+"mooringStartS "+mooringStartS);
        if (dbg) System.out.println(sub+"mooringEndS "+mooringEndS);
        if (dbg) System.out.println(sub+"dataStartS "+dataStartS);
        if (dbg) System.out.println(sub+"dataEndS "+dataEndS);

        //subTab7.addRow(ec.cr2ColRow("Number Of Records : ",String.valueOf(lineCnt)));
        //subTab7.addRow(ec.cr2ColRow("Time Interval     : ",
        //    String.valueOf(prevtimeInterval / 60000)));

        return subTab7;

    } //DynamicTable displayMooringData() {


    /**
     * Display depth records
     * @return DynamicTable
     */
    DynamicTable displayDepths() {
        DynamicTable subTab9 = new DynamicTable(1);
        subTab9.setBorder(1);
        subTab9.setCenter();
        subTab9.setFrame(ITableFrame.BOX);

        TableRow depthsHead = new TableRow();
        depthsHead.addCell(new TableHeaderCell(new SimpleItem("Depths")));
        depthsHead.addCell(new TableHeaderCell(new SimpleItem("Records")));
        depthsHead.addCell(new TableHeaderCell(new SimpleItem("Start")));
        depthsHead.addCell(new TableHeaderCell(new SimpleItem("End")));
        depthsHead.addCell(new TableHeaderCell(new SimpleItem("Int")));
        subTab9.addRow(depthsHead);

        TableRow depthsValues = new TableRow();
        depthsValues.addCell(new TableDataCell(String.valueOf(meterDepth)));
        depthsValues.addCell(new TableDataCell(String.valueOf(lineCnt)));
        //depthsValues.addCell(new TableDataCell(String.valueOf(startDate)));
        depthsValues.addCell(new TableDataCell(dataStartS));
        //depthsValues.addCell(new TableDataCell(String.valueOf(endDate)));
        depthsValues.addCell(new TableDataCell(dataEndS));
        depthsValues.addCell(new TableDataCell(String.valueOf(timeInterval/60000)));
        subTab9.addRow(depthsValues);

        // construct table to diplay depth data for this load.
        field = "*";
        where = CurDepth.MOORING_CODE+" = "+mooringCode;
        order = CurDepth.SPLDEP+", "+CurDepth.DATE_TIME_START;

        //float prevSpldep = -10f;
        int spldep = 0;
        int prevSpldep = -10;

        CurDepth depth2[] = new CurDepth().get(field,where,order);
//        if(dbg) System.out.println(sub+"depth2.length "+depth2.length);

//            if(dbg) System.out.println(sub+"mooringCode "+mooringCode);

            for (int i =0; i < depth2.length; i++) {

                // only write header for first depth found
                if (i == 0) {
                    subTab9.addRow(ec.crSpanColsRow("Details of other Depths.",
                    5,true,"blue",IHAlign.CENTER,"+1" ));
                } // if (i == 0)


//                if(dbg) System.out.println(sub+"mooringCode "+mooringCode);

//                if(dbg) System.out.println(sub+"depth2.length "+depth2[i]);

                //java.lang.Math.round(t)
                spldep = java.lang.Math.round(depth2[i].getSpldep());

                //spldep = depth2[i].getSpldep();
                Timestamp depthDateStart = depth2[i].getDateTimeStart();
                Timestamp depthDateEnd   = depth2[i].getDateTimeEnd();
                int numberRecords        = depth2[i].getNumberOfRecords();
                int timeInt              = depth2[i].getTimeInterval();

                if (spldep != prevSpldep) {
                    depthsValues = new TableRow();
                    depthsValues.addCell(new TableDataCell(
                        String.valueOf(spldep)));
                    depthsValues.addCell(new TableDataCell(
                        String.valueOf(numberRecords)));
                    depthsValues.addCell(new TableDataCell(
                        String.valueOf(depthDateStart).substring(0,16)));
                    depthsValues.addCell(new TableDataCell(
                        String.valueOf(depthDateEnd).substring(0,16)));
                    depthsValues.addCell(new TableDataCell(
                        String.valueOf(timeInt)));
                    subTab9.addRow(depthsValues);
                } //if (spldep != depth) {

            prevSpldep = spldep;
        } //for (int i =0; i < depth2.length; i++) {

        return subTab9;
    } // DynamicTable displayDepths() {


    /**
     * Function check if instrument exist if not the instrument will be added.
     * @return DynamicTable
     */
    DynamicTable displayInstrument() {
        DynamicTable subTab8 = new DynamicTable(1);
        subTab8.setBorder(1);
        subTab8.setCenter();
        subTab8.setFrame(ITableFrame.BOX);

        //subTab8.addRow(ec.crSpanColsRow("Select Instrument if not found in drop"+
        //    " down enter new Instrument <br>and Instrument no to add to database.",
        //        2,true,"blue",IHAlign.CENTER,"+1" ));


        subTab8.addRow(ec.crSpanColsRow("Present Data Set Details",
                2,true,"blue",IHAlign.CENTER,"+1" ));

        subTab8.addRow(ec.cr2ColRow("Instrument       : ",instrNo+"-"+instrType));
        subTab8.addRow(ec.cr2ColRow("Deployment       : ",deploymentNo));


        return subTab8;
    } // DynamicTable displayInstrument()


    /**
     * Function for the Date Time Table.
     * @return DynamicTable
     */
    DynamicTable dateTimeTable(
            String header,
            String colour,
            String leftText,
            Timestamp mooringStart,
            Timestamp mooringEnd,
            Timestamp dataStart,
            Timestamp dataEnd,
            String bottomText) {

        String sub = "<br>dateTimeTable: ";

        String SmooringStartS = String.valueOf(mooringStart).substring(0,16);
        String SmooringEndS   = String.valueOf(mooringEnd).substring(0,16);
        String SdataStartS    = String.valueOf(dataStart).substring(0,16);
        String SdataEndS      = String.valueOf(dataEnd).substring(0,16);

        DynamicTable subTab10 = new DynamicTable(1);
        subTab10.setBorder(1);
        subTab10.setCenter();
        subTab10.setFrame(ITableFrame.BOX);

        subTab10.addRow(ec.crSpanColsRow(
            header,5,true,colour,IHAlign.CENTER,"+1" ));

        TableRow rangeRow = new TableRow();
        TableRow row1     = new TableRow();

        row1.addCell(new TableHeaderCell(new SimpleItem("&nbsp;")));
        row1.addCell(new TableHeaderCell("Start Date").setColSpan(2));
        row1.addCell(new TableHeaderCell("End Date").setColSpan(2));

        //mooring or depth data row.
        TableRow row2 = new TableRow();
        //Data set row.
        TableRow row3 = new TableRow();

        row2.addCell(new TableHeaderCell(leftText));
        row3.addCell(new TableHeaderCell("This Data Set:"));
        subTab10.addRow(row1);

        if (tmp) System.out.println(sub+"@dt tmp mooringStartS :"+SmooringStartS);
        if (tmp) System.out.println(sub+"#dt tmp mooringEndS   :"+SmooringEndS);
        if (tmp) System.out.println(sub+"$dt tmp dataStartS    :"+SdataStartS);
        if (tmp) System.out.println(sub+"*dt tmp dataEndS      :"+SdataEndS);

        if (dataStart.after(mooringStart)) {
            row2.addCell(new TableDataCell(SmooringStartS));
            row2.addCell(new TableDataCell("&nbsp;"));
            row3.addCell(new TableDataCell("&nbsp;"));
            row3.addCell(new TableDataCell(SdataStartS));
        } else if (dataStart.before(mooringStart)) {
            row2.addCell(new TableDataCell("&nbsp;"));
            row2.addCell(new TableDataCell(SmooringStartS));
            row3.addCell(new TableDataCell(SdataStartS));
            row3.addCell(new TableDataCell("&nbsp;"));
        } else {
            row2.addCell(new TableDataCell(SmooringStartS).setColSpan(2));
            row3.addCell(new TableDataCell(SdataStartS).setColSpan(2));

        } //if (dataStart.after(mooringStart)) {

        if (dataEnd.after(mooringEnd)) {
        //if (Timestamp.valueOf(dEnd).after(mooringEnd)) {
            if (tmp) System.out.println(sub+"INSIDE IF AFTER newMooringEnd          :"+newMooringEnd);
            row2.addCell(new TableDataCell(SmooringEndS));
            row2.addCell(new TableDataCell("&nbsp;"));
            row3.addCell(new TableDataCell("&nbsp;"));
            row3.addCell(new TableDataCell(SdataEndS));
            //row3.addCell(new TableDataCell(dEnd));
        } else if (dataEnd.before(mooringEnd)) {
            row2.addCell(new TableDataCell("&nbsp;"));
            row2.addCell(new TableDataCell(SmooringEndS));
            row3.addCell(new TableDataCell(SdataEndS));
            row3.addCell(new TableDataCell("&nbsp;"));
        } else {
            row2.addCell(new TableDataCell(SmooringEndS).setColSpan(2));
            row3.addCell(new TableDataCell(SdataEndS).setColSpan(2));
        } // if (dataEnd.after(mooringEnd)) {

        subTab10.addRow(row2);
        subTab10.addRow(row3);

        //footer row.
        TableRow row4 = new TableRow();
        if (dbg) System.out.println(sub+"bottomText"+bottomText);
        if (!"".equals(bottomText)) {
            row4.addCell(new TableHeaderCell(bottomText).setColSpan(5));
            subTab10.addRow(row4);
        } //if (!"".equals(bottomText)) {

        return subTab10;
   } //DynamicTable dateTimeTable() {


    /**
     * Function for the to getDateTime - mooring exist.
     * @return DynamicTable
     */
    DynamicTable getDateTime() {

        String sub = "<br>getDateTime: ";

        DynamicTable subtab4 = new DynamicTable(1);
        subtab4.setBorder(1);
        subtab4.setCenter();
        subtab4.setFrame(ITableFrame.BOX);

        subtab4.addRow(ec.crSpanColsRow("<font color=blue size=+1>Mooring date-time range:</font>"+
            "<br>It is presumed that the mooring date-time range is <br>the same as the "+
            "date-time range found in the <br>present data set. <font color=green size=+1>"+
            "Please edit if it should differ!</font>",3,true,"",IHAlign.CENTER,"+1" ));

        TableRow head  = new TableRow();

        head.addCell(new TableHeaderCell(new SimpleItem("&nbsp;")));
        head.addCell(new TableHeaderCell("Start Date"));
        head.addCell(new TableHeaderCell("End Date"));

        TableRow dataSet = new TableRow();
        dataSet.addCell(new TableHeaderCell("Data set:"));
        dataSet.addCell(new TableDataCell(dataStartS));
        dataSet.addCell(new TableDataCell(dataEndS));

        //if (dbg) System.out.println(sub+"dStart "+dStart);
        //if (dbg) System.out.println(sub+"eStart "+eStart);

        TableRow mooringValue = new TableRow();
        mooringValue.addCell(new TableHeaderCell("Mooring:"));
        mooringValue.addCell(new TableDataCell(new TextField(cc.NEWMOORINGSTART,16,16,dataStartS)));
        mooringValue.addCell(new TableDataCell(new TextField(cc.NEWMOORINGEND,16,16,dataEndS)));

        if (dbg) System.out.println(sub+"mooringStartS "+mooringStartS);
        if (dbg) System.out.println(sub+"mooringEndS "+mooringEndS);

        subtab4.addRow(head);
        subtab4.addRow(dataSet);
        subtab4.addRow(mooringValue);

        return subtab4;
    } //DynamicTable dateTimeTable() {


    /**
     * Get SADCO Survey IS
     * @return DynamicTable
     */
    DynamicTable getSurveyID() {

        DynamicTable subTab = new DynamicTable(1);
        subTab.setBorder(1);
        subTab.setCenter();
        subTab.setFrame(ITableFrame.BOX);

        subTab.addRow(ec.crSpanColsRow("Please Complete the Mooring Details",
            2,true,"blue",IHAlign.CENTER,"+1" ));
        subTab.addRow(ec.cr2ColRow("SADCO survey ID:",cc.SURVEYID,9,9,surveyId));

        return subTab;
    } // getSurveyID()


    /**
     * Function to check whether instrument exists.
     * @param  planam code     (int)
     * @param  client code     (int)
     */
    private void checkIfInstrumentExist()   {

        if (!"".equals(instrNo)) {
            String where = EdmInstrument2.CODE +" = "+ instrNo;
                //+" and "+EdmInstrument2.NAME +" = "+instrType;
            EdmInstrument2[] exist = new EdmInstrument2().get(where);

            if (exist.length > 0) {
                InstrExists = true;
            } // if (exists.length > 0) {
        } // if (!"".equals(instrNo)) {
    } //private void checkIfInstrumentExist()   {


    /**
     * Function go and get the name that match the code.
     * @param  planam code     (int)
     * @param  client code     (int)
     */
    private void getClientNamePlatform(int clientCode, int planamCode)   {

        String sub = "<br>getClientNamePlatform: ";

        where = EdmClient2.CODE +" = "+ clientCode;
        EdmClient2[] client = new EdmClient2().get(where);
        clientName = client[0].getName();

        where = CurPlanam.CODE+" = "+planamCode;
        CurPlanam [] planam = new CurPlanam().get(where);
        platformName = planam[0].getName();

        if (dbg) System.out.println(sub+"platformName "+platformName+" : clientName "+clientName);
    } //private void getClientNamePlatform(int clientCode, int planamCode)   {

    /**
     * create the combo-box (pull-down box) for client
     * @param  clientCode client code     (int)
     */
    Select createClientSelect(int clientCode) {

        String sub = "<br>createClientSelect: ";

        if (dbg) System.out.println(sub+"clientCode = " + clientCode);
        Select cl = new Select(cc.CLIENTCODE);
        EdmClient2[] client =
            new EdmClient2().get("1=1",EdmClient2.NAME);
        for (int i = 0; i < client.length; i++) {
            boolean defaultOption = false;
            if (client[i].getCode() == clientCode) { defaultOption = true; }
            cl.addOption(new Option(
                client[i].getName() + "("+client[i].getCode("")+")",
                client[i].getCode(""),
                defaultOption
                ));
        } // for (int i = 0; i < intstrument.length; i++) {
        return cl;
    } // Select createClientSelect(int clientCode) {

    /**
     * create the combo-box (pull-down box) for client
     * @param planamCode planam code     (int)
     */
    Select createPlanamSelect(int planamCode) {
        Select pla = new Select(cc.PLANAMCODE);
        CurPlanam [] planam =
            //new CurPlanam().get("1=1","CODE");
            new CurPlanam().get("1=1",CurPlanam.NAME);
        for (int i = 0; i < planam.length; i++) {
            boolean defaultOption = false;
            if (planam[i].getCode() == planamCode) { defaultOption = true; }
            pla.addOption(new Option(
                planam[i].getName() + "("+planam[i].getCode("")+")",
                planam[i].getCode(""),
                defaultOption
                ));
        } // for (int i = 0; i < planam.length; i++) {
        return pla;
    } // Select createPlanamSelect(int planamCode) {

    /**
     * create the combo-box (pull-down box) for instrument.
     * @param instrType intrument type        (String)
     */
    Select createInstrumentSelect(String instrType) {
        where = "";
        //where = EdmInstrument2.NAME +" = "+ instrType;

        Select instr = new Select(cc.INSTRUMENTCODE);
        EdmInstrument2[] intstrument =
            new EdmInstrument2().get("1=1",EdmInstrument2.NAME);

        for (int i = 0; i < intstrument.length; i++) {
            int code = intstrument[i].getCode();

            boolean defaultOption = false;
            if (intstrument[i].getCode() == code) { defaultOption = true; }
            instr.addOption(new Option(
                intstrument[i].getName() + "("+intstrument[i].getCode("")+")",
                intstrument[i].getCode(""),
                defaultOption
                ));
        } // for (int i = 0; i < intstrument.length; i++) {
        return instr;
    } // createInstrumentSelect(String instrType) {


    /**
     * create the combo-box (pull-down box) for the institutes
     */
    Select createInstituteSelect(String code) {
        Select sel = new Select(code);
        MrnInstitutes[] institList = new MrnInstitutes().get("1=1",MrnInstitutes.NAME);
        for (int i = 0; i < institList.length; i++) {
            sel.addOption(new Option(
                institList[i].getName() + "("+institList[i].getCode("")+")",
                institList[i].getCode(""), false));
        } // for (int i = 0; i < institList.length; i++)
        return sel;
    } // createInstituteSelect


    /**                                                                 //ub07
     * create the combo-box (pull-down box) for the scientists          //ub07
     */                                                                 //ub07
    Select createScientistSelect() {                                    //ub07
        Select sel = new Select(cc.SCIENTCODE);                         //ub07
        MrnScientists[] scientistList =                                 //ub07
            new MrnScientists().get("1=1",MrnScientists.SURNAME);       //ub07
        for (int i = 0; i < scientistList.length; i++) {                //ub07
            sel.addOption(new Option(                                   //ub07
                scientistList[i].getSurname() + ", " +                  //ub07
                scientistList[i].getFName("") +                         //ub07
                "("+scientistList[i].getCode("")+")",                   //ub07
                scientistList[i].getCode(""), false));                  //ub07
        } // for (int i = 0; i < scientistList.length; i++)             //ub07
        return sel;                                                     //ub07
    } // createScientistSelect                                          //ub07


    /**                                                                 //ub07
     * create the combo-box (pull-down box) for the data type           //ub07
     */                                                                 //ub07
    Select createDataTypeSelect() {                                     //ub07
        Select sel = new Select(cc.DATA_TYPE);                          //ub07
            sel.addOption(new Option("Currents","2", false));           //ub07
            sel.addOption(new Option("UTR","6", false));                //ub07
        return sel;                                                     //ub07
    } // createDataTypeSelect                                           //ub07



    /**
     * correct date/time for sast to utc
     * @param   date/time in SAST
     * @returns date/time in UTC
     */
    Timestamp getUTCDateTIme(Timestamp dateTime) {
        java.util.GregorianCalendar calDate = new java.util.GregorianCalendar();
        calDate.setTime(dateTime);
        calDate.add(java.util.Calendar.HOUR, -2);
        java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.s");
        dateTime = Timestamp.valueOf(formatter.format(calDate.getTime()));
        return dateTime;
    } // getUTCDateTIme


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("PreLoadCURDataFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PrePreLoadCURDataFrame local= new PrePreLoadCURDataFrame(args);
            bd.addItem(new PreLoadCURDataFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "PreLoadCURDataFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class PreLoadCURDataFrame