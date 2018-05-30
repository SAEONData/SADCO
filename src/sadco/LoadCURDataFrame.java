package sadco;

import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.StringTokenizer;
import oracle.html.CompoundItem;
import oracle.html.Container;
import oracle.html.DynamicTable;
import oracle.html.HtmlBody;
import oracle.html.HtmlHead;
import oracle.html.HtmlPage;
import oracle.html.IHAlign;
import oracle.html.ITableFrame;
import oracle.html.ITableRules;
import oracle.html.Link;

/**
 * This class loads the current data.
 *
 * SadcoCUR
 * pScreen.equals("load")
 * pVersion.equals("load")
 *
 * @author 021025 - SIT Group
 * @version
 * 021025 - Mario August        - create class                              <br>
 * 040614 - Ursula von St Ange  - change testing of all nullValues in load
 *                                to 9999.99                                <br>
 * 041102 - Ursula von St Ange  - change testing of all nullValues in load
 *                                to > 9999.                                <br>
 * 060201 - Ursula von St Ange  - add timezone for date/time correction to UTC<br>
 * 080606 - Ursula von St Ange  - add code to load .cur files               <br>
 * 090513 - Ursula von St Ange  - add input of project name (ub01)          <br>
 * 090513 - Ursula von St Ange  - move survey_id from depth to mooring (ub02)<br>
 * 090513 - Ursula von St Ange  - add code to find parameters present in
 *                                data (ub03)                               <br>
 * 110725 - Ursula von St Ange  - copy from currents.LoadDataFrame          <br>
 * 140813 - Ursula von St Ange  - also check for more tokens after first 2  <br>
 */
public class LoadCURDataFrame extends CompoundItem {//extends PreLoadCURDataFrame{//

    boolean dbg               = false;
    //boolean dbg               = true;

    boolean done              = false;
    boolean mooringUpdate     = false;
    boolean removeData        = false;
    boolean dataOK            = false;
    boolean fileFound         = false;
    boolean loadMooring       = false;
    boolean loaddata          = false;
    boolean endOfData         = false;
    boolean timeDiff          = false;
    static boolean standalone = false;

    static common.edmCommon ec = new common.edmCommon();

    /** A class with all the constants used in Currents.*/
    SadConstants cc = new SadConstants();

    // arguments
    String pathName           = "";
    String fileName           = "";
    String screen             = "";
    String version            = "";
    String menuno             = "";
    String mversion           = "";
    //String reload             = "";
    String partDepth          = "";
    String surveyID           = "";
    String notes[]           = new String[10];
    String checkinterval      = "";
    String timeZone           = "";
    String extension          = "";

    // Working varibles.
    String description        = "";
    String newClientName      = "";
    String newPlatformName    = "";
    String prjnam             = "";                                     // ub01
    String stnnam             = "";
    String arenam             = "";
    String publicationRef     = "";

    int    mooringCodeVal     = 0;
    int    clientCodeVal      = 0;
    int    planamCodeVal      = 1;  // == none
    int    stndep             = 0;
    int    numberOfDepths     = 0;
    String clientName         = "";
    String platformName       = "";
    int    institCodeVal      = 0;
    String instituteName      = "";
    String newInstitute        = "";
    String newInstAddress     = "";
    int    scientistCode      = 0;
    String newSciTitle        = "";
    String newSciFName        = "";
    String newSciSurname      = "";
    int    newSciInstitCode   = 0;
    String dataType           = "";


    //Timestamp dateTimeFirst = null;
    //Timestamp dateTimeSecond = null;
    Timestamp dataStart = null;
    Timestamp dataEnd;
    String dataStartS       = "";
    String dataEndS         = "";

    //Timestamp dateTimeStart;
    //Timestamp dateTimeEnd;

    String latitudeD          = "";
    String latitudeM          = "";
    float  latitude           = 0;

    String longitudeD         = "";
    String longitudeM         = "";
    float  longitude          = 0;

    //cur_depth variables.
    int    depthCodeVal       = 0;

    // one to be deleted or reload.
    String  reloadDepthCodeVal= "";

    float  spldepVal          = 0;
    int    recordsLoaded      = 0;
    int    instrumentNumber   = 0;
    int    timeInterval       = 0;
    int    numberOfRecords    = 0;

    String deploymentNumber   = "";
    String passkey            = "";

    Timestamp mooringStart    = Tables.DATENULL;
    Timestamp mooringEnd      = Tables.DATENULL;
    Timestamp newMooringStart = Tables.DATENULL;
    Timestamp newMooringEnd   = Tables.DATENULL;
    String newMooringStartS   = "";
    String newMooringEndS     = "";

    Timestamp dateLoaded;

    // testing of null values
    float  nullValue          = 9999.0f;    // ub00

    // Table default testing variable's.
    int    INTN               = Tables.INTNULL;
    float  FLOATN             = Tables.FLOATNULL;

    // new parameters
    String newInstrumentType  = "";
    String newInstrumentNo    = "";
    String instrumentType     = "";

    int    numberRecords2     = 0;
    int    numberNullRecords2 = 0;
    int    numberRecords      = 0;

    String where              = "";
    String field              = "";
    String order              = "";

    float min                 = 9999.9f;
    float max                 = -9999.9f;
    float total               = 0f;

    int   speedCnt            = 0;
    float currentSpeedMin     = FLOATN;
    float currentSpeedMax     = FLOATN;
    float currentSpeedAvg     = FLOATN;
    //float currentSpeedTot     = FLOATN;

    int   directionCnt        = 0;
    float directionMin        = FLOATN;
    float directionMax        = FLOATN;
    float directionAvg        = FLOATN;
    //float directiont          = FLOATN;

    float temperature         = FLOATN;
    int   tempCnt             = 0;
    float temperatureMin      = FLOATN;
    float temperatureMax      = FLOATN;
    float temperatureAvg      = FLOATN;
    //float temperatureTot      = FLOATN;

    float vertVelocity        = FLOATN;
    int   velocityCnt         = 0;
    float vertVelocityMin     = FLOATN;
    float vertVelocityMax     = FLOATN;
    float vertVelocityAvg     = FLOATN;
    //float velocityTot         = FLOATN;

    float fSpeed9             = FLOATN;
    int   fSpeed9Cnt          = 0;
    float fSpeed9Min          = FLOATN;
    float fSpeed9Max          = FLOATN;
    float fSpeed9Avg          = FLOATN;
    //float fSpeed9Tot          = FLOATN;

    float fDirection9      = FLOATN;
    int fDirection9Cnt   = 0;
    float fDirection9Min = FLOATN;
    float fDirection9Max = FLOATN;
    float fDirection9Avg = FLOATN;
    //float fDirection9Tot = FLOATN;

    float fSpeed14          = FLOATN;
    int fSpeed14Cnt       = 0;
    float fSpeed14Min     = FLOATN;
    float fSpeed14Max     = FLOATN;
    float fSpeed14Avg     = FLOATN;
    //float fSpeed14Tot     = FLOATN;

    float fDirection14      = FLOATN;
    int fDirection14Cnt   = 0;
    float fDirection14Min = FLOATN;
    float fDirection14Max = FLOATN;
    float fDirection14Avg = FLOATN;
    //float fDirection14Tot = FLOATN;

    float ph              = FLOATN;
    int phCnt             = 0;
    float phMin           = FLOATN;
    float phMax           = FLOATN;
    float phAvg           = FLOATN;
    //float phTot           = FLOATN;

    float salinity          = FLOATN;
    int salinityCnt         = 0;
    float salinityMin       = FLOATN;
    float salinityMax       = FLOATN;
    float salinityAvg       = FLOATN;
    //float salinityTot       = FLOATN;

    float   disOxy          = FLOATN;
    int   disOxyCnt       = 0;
    float disOxyMin       = FLOATN;
    float disOxyMax       = FLOATN;
    float disOxyAvg       = FLOATN;
    //float disOxyTot       = FLOATN;

    float   pressure      = FLOATN;
    int   pressureCnt     = 0;
    float pressureMin     = FLOATN;
    float pressureMax     = FLOATN;
    float pressureAvg     = FLOATN;
    //float pressureTot     = FLOATN;

    RandomAccessFile lfile;
    RandomAccessFile reportFile;
    String line               = "";

    int   recCnt          = 0;
    int   invalidValueCnt = 0;
    int   loadTotal       = 0;

//    CurMooring getMooring[]

    public LoadCURDataFrame(String args[]) {

        String sub = "<br>currents.LoadCURDataFrame: ";

        if (ec.getHost().startsWith(cc.HOST)) {
            pathName = cc.HOSTDIRL + "currents/";
        } else {
            pathName = cc.LOCALDIR + "currents/";
        } // if host
        if (dbg) System.out.println(sub+"pathName = " + pathName);

        // Create main table                                         //
        DynamicTable mainTable = new DynamicTable(0);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        mainTable.setCenter();
        mainTable.setFrame(ITableFrame.BOX);

        // get the argument values
        getArgParms(args);

        // get the header info
        if (".std".equals(extension)) {
            getStdHeaderInfo();
        } else {
            getCurHeaderInfo();
        } // if ("std".equals(extension))

        // is this a standalone call to load data?
        if ("".equals(screen)) {
            if (dbg) System.out.println(sub+"fileName = " + fileName +
                ", mooringcode = " + mooringCodeVal + ", extension = " + extension);
            //if (!"".equals(fileName) && (mooringCodeVal > 0) && ".cur".equals(extension)) {
                standalone = true;
                // getCurHeaderInfo();
            //} else {
            //    mainTable.addRow(ec.crSpanColsRow("Error loading data. " +
            //        "Some parameters are missing.",
            //        2,true,"red",IHAlign.CENTER,"+1" ));
            //    mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
            //} // if ("".equals(fileName) && (mooringCodeVal > 0))
        } // if ("".equals(screen))

        // if reload status equal Y remove depth record.
        if (dbg) System.out.println(sub+"reloadDepthCodeVal = " + reloadDepthCodeVal);
        //if ("Y".equals(reload)) {
        if (!"".equals(reloadDepthCodeVal)) {
            where = CurDepth.MOORING_CODE+" = "+mooringCodeVal
                    +" and "+CurDepth.CODE+" = "+reloadDepthCodeVal;
            if (dbg) System.out.println(sub+"reload=Y: where = " + where);
            CurDepth dpth = new CurDepth();
            dpth.del(where);
            if (dbg) System.out.println(sub+"reload=Y: dpth.getNumRecords()"  + dpth.getNumRecords());
            if (dbg) System.out.println(sub+"reload=Y: dpth.getMessages()"  + dpth.getMessages());
            if (dbg) System.out.println(sub+"reload=Y: dpth.getDelStr()"  + dpth.getDelStr());
        } //if ("Y".equals(reload)) {

        // is it a new mooring?
        if (dbg) System.out.println(sub+"BEFORE mooring if: mooringCodeVal = " + mooringCodeVal);
        if (mooringCodeVal != 0) {  //not a new mooring

            CurMooring updateMooring = new CurMooring();
            boolean doUpdate = false;

            // check for changing of date's.
            if (!"".equals(newMooringStartS)) {
                updateMooring.setDateTimeStart(newMooringStart);
                doUpdate = true;
            } // if (!"".equals(newMooringStartS))

            if (!"".equals(newMooringEndS)) {
                updateMooring.setDateTimeEnd(newMooringEnd);
                doUpdate = true;
            } // if (!"".equals(newMooringEndS))

            where = CurMooring.CODE+"="+mooringCodeVal;
            if (doUpdate) {
                boolean success = new CurMooring().upd(updateMooring,where);
            } // if (doUpdate)

            //get the rest of the info.
            CurMooring getMooring[] = new CurMooring().get(where);
            numberOfDepths = getMooring[0].getNumberOfDepths();
            //latitude = getMooring[0].getLatitude();
            //longitude = getMooring[0].getLongitude();
            //stndep = getMooring[0].getStndep();
            newMooringStart = getMooring[0].getDateTimeStart();
            newMooringEnd = getMooring[0].getDateTimeEnd();
            //stnnam = getMooring[0].getDescription();
            stnnam = getMooring[0].getStnnam();
            clientCodeVal = getMooring[0].getClientCode();
            EdmClient2 cli[] = new EdmClient2().get(EdmClient2.CODE+"="+clientCodeVal);
            if (cli.length > 0) clientName = cli[0].getName();

            // get sadco survey ID for later
            surveyID = getMooring[0].getSurveyId();

            if(dbg) {
                System.out.println(sub+"mooringCodeVal = "+mooringCodeVal);
                System.out.println(sub+"getMooring.length = "+getMooring.length);
                System.out.println(sub+"getMooring[0] = "+getMooring[0]);
                System.out.println(sub+"before if numberOfDepths "+numberOfDepths);
            } // if(dbg)


        } else { //if (mooringCode == INTN) {//if (!"".equals(mooringcode)) {

            loadMooring = true;

            checkNewInstitute();
            checkNewScientist();

            loadClientPlanam();
            loadNewMooring();

        } //if (mooringCodeVal != 0)

        //========== check on instrument in header and insert if neccessary  ??
        //if (dbg) System.out.println(sub+"BEFORE instrument testing ");
        //if ( (!"".equals(newInstrumentNo)) && (!"".equals(newInstrumentType)) ) {
        //    if (dbg) System.out.println(sub+"IN instrument testing ");
        //    instrumentNumber = new Integer(newInstrumentNo).intValue();
        //    insertInstrument();
        //} // if ( (!"".equals
        insertInstrumentIfNotOnDB();

        // load the depth record
        loadDepth();
        loadDepthNotes();
        if ("".equals(checkinterval)) loadDepthQuality();

        // update the number of depths
        //if ( ("".equals(partDepth)) && ("".equals(reload)) ) {
        if ( ("".equals(partDepth)) && ("".equals(reloadDepthCodeVal)) ) {

            numberOfDepths +=1;
            if(dbg) System.out.println(sub+"if !partDepth: "+numberOfDepths);

            // update number of depths
            CurMooring updateMooring = new CurMooring();
            updateMooring.setNumberOfDepths(numberOfDepths);
            where = CurMooring.CODE+"="+mooringCodeVal;
            new CurMooring().upd(updateMooring,where);

        } //if ("Y".equals(partDepth)) {

        if(dbg) System.out.println(sub+"after if !partDepth: "+numberOfDepths);

        // loop through input file and load data into cur_data.
        processInputFile();

        updateMooringDepth();

        // update sadco inventory with new dates
        updateInventory();

        //if(dbg) System.out.println(sub+"done status "+done);
        getStats();

        writeReportFile();
        //mainTab.addRow(ec.crSpanColsRow(sub.toHTML(),2));
        //if(dbg) System.out.println(sub+"done status "+done);
        //} // if (done) {

        mainTable.addRow(ec.crSpanColsRow("Load Data ",2,true,"blue",IHAlign.CENTER,"+2" ));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));

        if (loadTotal > 0) {
            mainTable.addRow(ec.crSpanColsRow("The data has been successfully loaded. ",
                2,true,"green",IHAlign.CENTER,"+1" ));
            mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        } else {
            mainTable.addRow(ec.crSpanColsRow("Error loading data. " +
                "Check input File for valid values.",
                2,true,"red",IHAlign.CENTER,"+1" ));
            mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        }

        DynamicTable subTab = new DynamicTable(0);
        subTab.setFrame(ITableFrame.VOLD);
        subTab.setRules(ITableRules.NONE);
        subTab.setBorder(0);
        subTab.setCenter();
        //subTab.setFrame(ITableFrame.BOX);

        subTab.addRow(ec.cr2ColRow("Mooring Code             : ",mooringCodeVal));
        subTab.addRow(ec.cr2ColRow("Depth Code               : ",depthCodeVal));
        subTab.addRow(ec.cr2ColRow("Total number of records. : ",recCnt));
        subTab.addRow(ec.cr2ColRow("Records loaded           : ",recordsLoaded));
        subTab.addRow(ec.cr2ColRow("Null records not loaded. : ",invalidValueCnt));


        // the link to download the file, and instructions
        String fileName2 = fileName+".report";
        String link = new Link(cc.LOADS_URL+"currents/"+fileName2, fileName2).toHTML();
        String instructions = ec.downloadFileInstructions().toHTML();

        mainTable.addRow(ec.crSpanColsRow(subTab.toHTML(),2));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTable.addRow(ec.crSpanColsRow("The load report has been "+
            "successfully generated.",2,true,"green",IHAlign.CENTER,"+1" ));
        mainTable.addRow(ec.crSpanColsRow(link,2));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTable.addRow(ec.crSpanColsRow(instructions,2));

        // Main container                                            //
        Container con = new Container();
        con.addItem(mainTable.setCenter());
        addItem(con);

    } // constructor

    /**
     * Get the parameters from the arguments in the URL
     * Only get values that are not in the header
     * @param  args       (String)
     */
    void getArgParms(String args[])   {

        String sub = "<br>getArgParms: ";

        String check = "";

        fileName          = ec.getArgument(args,cc.FILENAME);
        screen            = ec.getArgument(args,cc.SCREEN);
        version           = ec.getArgument(args,cc.VERSION);
        menuno            = ec.getArgument(args,menusp.MnuConstants.MENUNO);
        mversion          = ec.getArgument(args,menusp.MnuConstants.MVERSION);
        //mooringStatus     = ec.getArgument(args,cc.MOORINGSTATUS);

        // optional
        // normally = station name, but could be different
        description = ec.getArgument(args,cc.DESCRIPTION);

        //String startTemp  = ec.getArgument(args,cc.STARTDATE);
        if (dbg) System.out.println(sub+"mooringStart = "+ec.getArgument(args,cc.STARTDATE));
        if (dbg) System.out.println(sub+"mooringEnd = "+ec.getArgument(args,cc.ENDDATE));

        // get from data
        //- check = ec.getArgument(args,cc.STARTDATE);
        //- if (!"".equals(check)) mooringStart = Timestamp.valueOf(check+":00.0");
        //- check = ec.getArgument(args,cc.ENDDATE);
        //- if (!"".equals(check)) mooringEnd = Timestamp.valueOf(check+":00.0");

        // optional
        newMooringStartS  = ec.getArgument(args,cc.NEWMOORINGSTART);
        if (dbg) System.out.println(sub+"newMooringStart check = "+newMooringStartS);
        if (!"".equals(newMooringStartS)) {
            newMooringStart = Timestamp.valueOf(newMooringStartS+":00.0");
        } // if (!"".equals(newMooringStartS))

        // optional
        newMooringEndS = ec.getArgument(args,cc.NEWMOORINGEND);
        if (!"".equals(newMooringEndS)) {
            newMooringEnd = Timestamp.valueOf(newMooringEndS+":00.0");
        } // if (!"".equals(newMooringEndS))

        // get from header
        //- check = ec.getArgument(args,cc.LATITUDE);
        //- if (!"".equals(check)) latitude = new Float(check).floatValue();
        //- check = ec.getArgument(args,cc.LONGITUDE);
        //- if (!"".equals(check)) longitude = new Float(check).floatValue();
        //- check = ec.getArgument(args,cc.stndep);
        //- if (!"".equals(check)) stndep = Integer.parseInt(check);

        // needed for existing moorings
        check = ec.getArgument(args,cc.MOORINGCODE);
        if (!"".equals(check)) mooringCodeVal = Integer.parseInt(check);

        // needed for new moorings - client, existing or new
        check = ec.getArgument(args,cc.CLIENTCODE);
        if (dbg) System.out.println("client code check = " + check);
        if (!"".equals(check)) clientCodeVal = Integer.parseInt(check);
        newClientName = ec.getArgument(args,cc.NEWCLIENTNAME);
        //if ("".equals(newClientName)) {
        //    EdmClient2 cli[] = new EdmClient2().get(EdmClient2.CODE+"="+clientCodeVal);
        //    if (cli.length > 0) clientName = cli[0].getName();
        //} // if ("".equals(newClientName))

        // needed for new moorings, platform code - but defaults to 1
        check = ec.getArgument(args,cc.PLANAMCODE);
        if (!"".equals(check)) planamCodeVal = Integer.parseInt(check);
        newPlatformName = ec.getArgument(args,cc.NEWPLATFORMNAME);
        //if ("".equals(newPlatformName)) {
        //    CurPlanam pla[] = new CurPlanam().get(CurPlanam.CODE+"="+planamCodeVal);
        //    if (pla.length > 0) platformName = pla[0].getName();
        //} // if ("".equals(newPlatformName))

        // new stuff for sadco inventory
        // needed for new moorings, institute, existing or new
        check = ec.getArgument(args,cc.INSTITCODE);
        if (!"".equals(check)) institCodeVal = Integer.parseInt(check);
        newInstitute = ec.getArgument(args,cc.NEWINSTITUTE);
        newInstAddress = ec.getArgument(args, cc.NEWINSTADDRESS);
        //if ("".equals(newInstitute)) {
        //    MrnInstitutes inst[] = new MrnInstitutes()
        //        .get(MrnInstitutes.CODE+"="+institCodeVal);
        //    if (inst.length > 0) instituteName = inst[0].getName();
        //} // if ("".equals(newInstitute))

        // needed for new moorings, scientists, existing or new
        check = ec.getArgument(args,cc.SCIENTCODE);
        if (!"".equals(check)) scientistCode = Integer.parseInt(check);
        newSciFName = ec.getArgument(args, cc.NEWSCFIRSTNAME);          // ub07
        newSciSurname = ec.getArgument(args, cc.NEWSCSURNAME);          // ub07
        check = ec.getArgument(args,cc.NEWSCINST);
        if (!"".equals(check)) newSciInstitCode = Integer.parseInt(check);

        // data type (2 = currents, 6 - UTR)
        dataType = ec.getArgument(args, cc.DATA_TYPE);

        // get from header
        //- check = ec.getArgument(args,cc.SPLDEP);
        //- if (!"".equals(check)) spldepVal = new Float(check).floatValue();

        // get from header
        //- check = ec.getArgument(args,cc.INSTRUMENTNUMBER);
        //- if (!"".equals(check)) instrumentNumber = Integer.parseInt(check);
        //- instrumentType    = ec.getArgument(args,cc.INSTRUMENTTYPE);

        // check what is in header, if not in db, insert
        //- newInstrumentType = ec.getArgument(args,cc.NEWINSTRUMENTTYPE);
        //- newInstrumentNo   = ec.getArgument(args,cc.NEWINSTRUMENTNO);

        // get from header
        //- deploymentNumber  = ec.getArgument(args,cc.DEPLOYMENTNUMBER);

        // get from data
        //- check = ec.getArgument(args,cc.TIMEINTERVAL);
        //- if (!"".equals(check)) timeInterval = Integer.parseInt(check);
        //- check = ec.getArgument(args,cc.NUMBEROFRECORDS);
        //- if (!"".equals(check)) numberOfRecords = Integer.parseInt(check);


        // get from header
        //- stnnam            = ec.getArgument(args,cc.STNNAM);

        // needed for new moorings
        prjnam            = ec.getArgument(args,cc.PRJNAM);             // ub01
        stnnam            = ec.getArgument(args,cc.STNNAM);
        arenam            = ec.getArgument(args,cc.ARENAM);
        publicationRef    = ec.getArgument(args,cc.PUBLREF);

        // needed for SADCO depths
        passkey           = ec.getArgument(args,cc.PASSKEY);
        surveyID          = ec.getArgument(args,cc.SURVEYID);


        // get from header
        //- notes[0]         = ec.getArgument(args,cc.NOTESA);
        //- notes[1]         = ec.getArgument(args,cc.NOTESB);
        //- notes[2]         = ec.getArgument(args,cc.NOTESC);
        //- notes[3]         = ec.getArgument(args,cc.NOTESD);
        //- notes[4]         = ec.getArgument(args,cc.NOTESE);

        // needed
        timeZone          = ec.getArgument(args,cc.TIMEZONE).toLowerCase();
        extension         = ec.getArgument(args,cc.EXTENSION);

        // optional
        checkinterval     = ec.getArgument(args,cc.CHECKINTERVAL);
        // if reload == Y delete and continue with programme
        //reload            = ec.getArgument(args,cc.RELOAD);
        reloadDepthCodeVal= ec.getArgument(args,cc.DEPTHCODE);

        partDepth = ec.getArgument(args,cc.PARTDEPTH);

        dateLoaded = new Timestamp(new java.util.Date().getTime());

        if (dbg) System.out.println(sub+"pathName         = "+pathName);
        if (dbg) System.out.println(sub+"fileName         = "+fileName);
        if (dbg) System.out.println(sub+"screen           = "+screen);
        if (dbg) System.out.println(sub+"version          = "+version);
        if (dbg) System.out.println(sub+"startDate        = "+mooringStart);
        if (dbg) System.out.println(sub+"endDate          = "+mooringEnd);
        if (dbg) System.out.println(sub+"description      = "+description);
        if (dbg) System.out.println(sub+"clientCode       = "+clientCodeVal);
        if (dbg) System.out.println(sub+"planamCode       = "+planamCodeVal);
        if (dbg) System.out.println(sub+"latitude         = "+latitude);
        if (dbg) System.out.println(sub+"longitude        = "+longitude);
        if (dbg) System.out.println(sub+"prjnam           = "+prjnam); // ub01
        if (dbg) System.out.println(sub+"arenam           = "+arenam);
        if (dbg) System.out.println(sub+"stnnam           = "+stnnam);
        if (dbg) System.out.println(sub+"stndep           = "+stndep);
        if (dbg) System.out.println(sub+"menuno           = "+menuno);
        if (dbg) System.out.println(sub+"mversion         = "+mversion);
        if (dbg) System.out.println(sub+"spldep           = "+spldepVal);
        //if (dbg) System.out.println(sub+"instrumentNumber = "+instrumentNumber);
        if (dbg) System.out.println(sub+"deploymentNumber = "+deploymentNumber);
        if (dbg) System.out.println(sub+"timeInterval     = "+timeInterval);
        if (dbg) System.out.println(sub+"numberOfRecords  = "+numberOfRecords);
        if (dbg) System.out.println(sub+"passkey          = "+passkey);
        if (dbg) System.out.println(sub+"dateLoaded       = "+dateLoaded);
        if (dbg) System.out.println(sub+"publref          = "+publicationRef);
        //if (dbg) System.out.println(sub+"reload           = "+reload);
        if (dbg) System.out.println(sub+"mooringCodeVal   = "+mooringCodeVal);
        if (dbg) System.out.println(sub+"reloadDepthCodeVal= "+reloadDepthCodeVal);
        if (dbg) System.out.println(sub+"partDepth        = "+partDepth);
        if (dbg) System.out.println(sub+"extension        = "+extension);

    } //  public void getArgParms()


    /**
     * process the input file
     */
    void processInputFile() {

        String sub = "<br>processInputFile: ";

        // Open the data file
        lfile = ec.openFile(pathName+fileName,"r");
        if (lfile != null) fileFound = true;
        //try {
        //    lfile = new RandomAccessFile(pathName+fileName,"r");
        //
        //    fileFound = true;
        //    if (dbg) System.out.println(sub+"fileFound = " +fileFound);
        //    if (dbg) System.out.println(sub+"fileName = " +pathName+fileName);
        //} catch (Exception e) {
        //    System.out.println(sub+"Error: " + e.getMessage());
        //    e.printStackTrace();
        //} // try-catch

        ec.deleteOldFile(pathName+fileName+".progress");
        RandomAccessFile progressFile = ec.openFile(pathName+fileName+".progress","rw");
        if (ec.getHost().startsWith(cc.HOST)) {
            ec.submitJob("chmod 644 " + pathName+fileName+".progress");
        } // if (ec.getHost().startsWith(cc.HOST))

        if (dbg) System.out.println(sub+"fileFound before if = " +fileFound);
        if (fileFound) {
            if (dbg) System.out.println(sub+"fileFound before while loop. = " +
                fileFound);

            // skip header part of data file.
            line = ec.getNextValidLine(lfile) + "      ";
            String startData = line.substring(0,4);
            if (dbg) System.out.println("startData = " + startData);
            while (!"DATA".equals(startData)) {
                line = ec.getNextValidLine(lfile) + "      ";
                startData = line.substring(0,4);
                if (dbg) System.out.println("startData = " + startData);
            } // while (!"DATA".equals(startData)

            // loop through data file if fileFound equal true.
            long d1 = 0, d2 = 0;
            long prevTimeInterval = 0, timeInterval = 0;
            while (!ec.eof(lfile)) {
                if (dbg) System.out.print(".");
                if (dbg) System.out.println(sub+"in while loop. " +fileFound);

                d1 = d2;
                prevTimeInterval = timeInterval;

                line = ec.getNextValidLine(lfile) + "      ";
                if (dbg) System.out.println(sub+"line = " +line);
                if (dbg) System.out.println(sub+"fileFound = " +fileFound);

                StringTokenizer token = new StringTokenizer(line," \t");

                String dataID = line.substring(0,3);
//                if (dbg) System.out.println("dataId = " + dataID + " " + numberRecords);
                // also test for '   ' dataID in case of empty line at end
                if (!dataID.equals("END") && !dataID.equals("   ")) {

                    numberRecords++;

                    Timestamp date = getTSDateTime(token);
                    if ("".equals(dataStartS)) {
                        d1 = date.getTime();
                        dataStartS = date.toString().substring(0,16);
                    } // if (dateTimeFirst == null

                    // keep for the last date
                    d2 = date.getTime();
                    timeInterval = d2 - d1;
                    dataEndS = date.toString().substring(0,16);

                    if (timeInterval != prevTimeInterval) {
                        timeDiff=true;
                    } // if (timeInterval != prevTimeInterval)

                    String currentSpeed     = token.nextToken();//line.substring(18,23);
                    String currentDirection = token.nextToken();//line.substring(24,29);

                    if (dbg) System.out.println(sub+"currentSpeed     = "+currentSpeed);
                    if (dbg) System.out.println(sub+"currentDirection = "+currentDirection);


                    // current speed
                    float speed = new Float(currentSpeed).floatValue();
                    if ( speed < nullValue) {
                        speedCnt++;
                        if (dbg) System.out.println(sub+"speedCnt      = "+speedCnt);
                    } else {
                        speed = FLOATN;
                    } // if ( speed < nullValue)

                    // current direction
                    float direction = new Float(currentDirection).floatValue();
                    if ( direction < nullValue) {
                        directionCnt++;
                        if (dbg) System.out.println(sub+"directionCnt      = "+directionCnt);
                    } else {
                        direction = FLOATN;
                    } // if ( direction < nullValue)

                    if (dbg) System.out.println(sub+"speed     "+speed);
                    if (dbg) System.out.println(sub+"direction "+direction);

                    // test for null values
                    if ((speed == FLOATN) || (direction == FLOATN)) {
                        speed = FLOATN;
                        direction = FLOATN;
                    } //if ( (speed == 0f) && (direction == 0f) ) {

                    if (dbg) System.out.println(sub+"speed     "+speed);
                    if (dbg) System.out.println(sub+"direction "+direction);

                    if (token.hasMoreTokens()) {

                        // water temperature
                        temperature = new Float(token.nextToken()).floatValue();
                        if ((temperature < nullValue)) {
                            tempCnt++;
                        } else {
                            temperature = FLOATN;
                        } // if ((temperature < nullValue))
                    } // if (token.hasMoreTokens())

                    if (dbg) System.out.println(sub+"before 1st parseFloat used. ");

                    if (token.hasMoreTokens()) {

                        //vertVelocity = Float.parseFloat(token.nextToken());
                        vertVelocity = new Float(token.nextToken()).floatValue();
                        //if (vertVelocity != INTN) { velocityCnt++;}
                        if (dbg) System.out.println(sub+"vertVelocity "+vertVelocity);
                        if (vertVelocity < nullValue) {
                            if (dbg) System.out.println(sub+"vertVelocity "+vertVelocity);
                            velocityCnt++;
                        } else {
                            vertVelocity = FLOATN;
                        } // if (vertVelocity < nullValue)

                        //fSpeed9 = Float.parseFloat(token.nextToken());
                        fSpeed9 = new Float(token.nextToken()).floatValue();
                        if (fSpeed9 < nullValue) {
                            fSpeed9Cnt++;
                        } else {
                            fSpeed9 = FLOATN;
                        }

                        //fDirection9 = Float.parseFloat(token.nextToken());
                        fDirection9 = new Float(token.nextToken()).floatValue();
                        if (fDirection9 < nullValue) {
                            fDirection9Cnt++;
                        } else {
                            fDirection9 = FLOATN;
                        }

                        //fSpeed14 = Float.parseFloat(token.nextToken());
                        fSpeed14 = new Float(token.nextToken()).floatValue();
                        if (fSpeed14 < nullValue) {
                            fSpeed14Cnt++;
                        } else {
                            fSpeed14 = FLOATN;
                        }

                        //fDirection14 = Float.parseFloat(token.nextToken());
                        fDirection14 = new Float(token.nextToken()).floatValue();
                        if (fDirection14 < nullValue) {
                            fDirection14Cnt++;
                        } else {
                            fDirection14 = FLOATN;
                        } // if (fDirection14 != 999.9f) {

                    } //if (token.hasMoreTokens()) {

                    if (token.hasMoreTokens()) {
                        //ph = Float.parseFloat(token.nextToken());
                        ph = new Float(token.nextToken()).floatValue();
                        if (ph < nullValue) {
                            phCnt++;
                        } else {
                          ph = FLOATN;
                        }

                        //salinity = Float.parseFloat(token.nextToken());
                        salinity = new Float(token.nextToken()).floatValue();
                        if (salinity < nullValue) {
                            salinityCnt++;
                        } else {
                            salinity = FLOATN;
                        }

                        //disOxy   = Float.parseFloat(token.nextToken());
                        disOxy   = new Float(token.nextToken()).floatValue();

                        if (disOxy < nullValue) {
                            disOxyCnt++;
                        } else {
                            disOxy = FLOATN;
                        }

                        //String ph       = line.substring(66,71);
                        //String salinity = line.substring(72,77);
                        //String disOxy   = line.substring(78,83);

                    } //if (token.hasMoreTokens()) {

                    if (token.hasMoreTokens()) {
                        //String pressure = line.substring(84);

                        //pressure = Float.parseFloat(token.nextToken());
                        pressure = new Float(token.nextToken()).floatValue();

                        if (pressure < nullValue) {
                            pressureCnt++;
                        } else {
                            pressure = FLOATN;
                        }

                    } //if (token.hasMoreTokens()) {
                    //done = true;

                    int curDataCode= 0;
                    if (dbg) System.out.println(sub+"FLOATN = "+FLOATN);

                    if (dbg) System.out.println(sub+"CurData load data "+
                        curDataCode+"|"+ depthCodeVal+"|"+date+"|"+speed+"|"+
                        direction+"|"+temperature+"|"+vertVelocity+"|"+fSpeed9+"|"+
                        fDirection9+"|"+fSpeed14+"|"+fDirection14+"|"+pressure);
                    ec.writeFileLine(progressFile, "CurData load data "+
                        curDataCode+"|"+ depthCodeVal+"|"+date+"|"+speed+"|"+
                        direction+"|"+temperature+"|"+vertVelocity+"|"+fSpeed9+"|"+
                        fDirection9+"|"+fSpeed14+"|"+fDirection14+"|"+pressure);

                    //if ((speed != FLOATN) && (direction != FLOATN) && (temperature != FLOATN)) {
                    //    invalidValueCnt++;
                    //} //if ((speed != FLOATN) &&

                    if ((speed != FLOATN) || (direction != FLOATN) || (temperature != FLOATN)
                        || (vertVelocity != FLOATN) || (fSpeed9 !=FLOATN)
                        || (fDirection9 != FLOATN) ||(fSpeed14 != FLOATN)
                        || (fDirection14 != FLOATN) || (pressure != FLOATN)) {

                        CurData curData = new CurData(
                            INTN,             // CODE,
                            depthCodeVal,     // DEPTH_CODE,
                            date,             // DATETIME,
                            speed,            // SPEED,
                            direction,        // DIRECTION,
                            temperature,      // TEMPERATURE,
                            vertVelocity,     // VERT_VELOCITY,
                            fSpeed9,          // F_SPEED_9,
                            fDirection9,      // F_DIRECTION_9,
                            fSpeed14,         // F_SPEED_14,
                            fDirection14,     // F_DIRECTION_14,
                            pressure          //PRESSURE,
                            );

                        boolean success = curData.put();
                        curDataCode = curData.getCode();
                        recordsLoaded++;
                        if (dbg) System.out.println(sub+"** CurData load data "+curData);
                        if (dbg) System.out.println(sub+"** recordsLoaded "+recordsLoaded);

                    } else {//if ((speed !=
                        invalidValueCnt++;
                        if (dbg) System.out.println(sub+"invalidValueCnt "+invalidValueCnt);
                    }

                    // debug
                    if (dbg) System.out.println(sub+"Watphy load data "+
                        depthCodeVal+"|"+curDataCode+"|"+ph+"|"+salinity+"|"+disOxy);
                    // check whether ph, salinity and disOxy containe valid values.
                    if ((ph != FLOATN) || (salinity != FLOATN) || (disOxy != FLOATN)) {
                        //if ((ph != 99.99) && (salinity != 99.99) && (disOxy != 99.99)) {

                        CurWatphy watphy = new CurWatphy(
                            depthCodeVal,     // DEPTH_CODE
                            curDataCode,      // DATA_CODE
                            ph,               // PH
                            salinity,         // SALINITY
                            disOxy            // DIS_OXY
                            );

                        boolean success = watphy.put();
                        if (dbg) System.out.println(sub+"Watphy load data "+watphy);
                    } //if ((ph != INTN) &&

                    recCnt++;
                    loadTotal = speedCnt + directionCnt + tempCnt + velocityCnt +
                    fSpeed9Cnt + fDirection9Cnt + fSpeed14Cnt + fDirection14Cnt +
                    phCnt + salinityCnt + disOxyCnt + pressureCnt;


                } //if (!dataID.equals("END")) {

            } //while (!ec.eof(lfile)) {
            if (dbg) System.out.println("-");

            ec.closeFile(lfile);
            ec.closeFile(progressFile);
        } //fileFound

    } // processInputFile


    /**
     * get the Timestamp for date and time, and convert to UTC
     */
    Timestamp getTSDateTime(StringTokenizer token) {

        String sub = "<br>getTSDateTime: ";

        Timestamp date;

        if (".std".equals(extension)) {
            String year  = token.nextToken(); //line.substring( 0, 4);
            String month = token.nextToken().replace(' ','0');//line.substring( 5, 7).replace(' ','0');
            String day   = token.nextToken().replace(' ','0');//line.substring( 8,10).replace(' ','0');
            String hour  = token.nextToken().replace(' ','0');//line.substring(11,13).replace(' ','0');
            String min   = token.nextToken().replace(' ','0');//line.substring(14,16).replace(' ','0');
            date = Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+
                ":"+min+":00.0");
            if (dbg) System.out.println(sub+"year "+year+" month "+month+" day "+
                day+" hour "+hour+" min "+min);
        } else {
            String datum = token.nextToken();
            String time = token.nextToken();
            date = Timestamp.valueOf(datum+" "+time+":00.0");
        } // if (".std".equals(extension))
//        if (dbg) System.out.println(sub+"** date1 "+date);

        if ("sast".equals(timeZone)) {
            date = getUTCDateTIme(date);
        } // if ('sast'.equals(timeZone))
//        if (dbg) System.out.println(sub+"** date2 "+date);

        return date;

    } // getTSDateTime


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
     * update the mooring and depth for start and end dates, and
     * update the depth for number of records and time interval
     */
    void updateMooringDepth() {

        // only do if it was a new mooring, and no new mooring start and
        // end dates were specified - defaults to dates in file (from
        // PreLoadDataFrame), but user could have deleted out of form!
        if (loadMooring) {
            CurMooring updateMooring = new CurMooring();
            boolean doUpdate = false;
            // check for changing of date's.
            if ("".equals(newMooringStartS)) {
                updateMooring.setDateTimeStart(dataStartS);
                doUpdate = true;
            } // if (!"".equals(newMooringStartS))

            if ("".equals(newMooringEndS)) {
                updateMooring.setDateTimeEnd(dataEndS);
                doUpdate = true;
            } // if (!"".equals(newMooringEndS))

            where = CurMooring.CODE+"="+mooringCodeVal;
            if (doUpdate) {
                boolean success = new CurMooring().upd(updateMooring,where);
            } // if (doUpdate)

        } // if (loadMooring)

        // create parameter field
        StringBuffer parametersPresent = new StringBuffer();        // ub03
        parametersPresent.append((speedCnt > 0 ? "V " : ""));       // ub03
        parametersPresent.append((tempCnt > 0 ? "T " : ""));        // ub03
        parametersPresent.append((pressureCnt > 0 ? "P " : ""));    // ub03
        parametersPresent.append((phCnt > 0 ? "Ph " : ""));         // ub03
        parametersPresent.append((salinityCnt > 0 ? "S " : ""));    // ub03
        parametersPresent.append((disOxyCnt > 0 ? "O " : ""));      // ub03

        CurDepth updateDepth = new CurDepth();
        updateDepth.setDateTimeStart(dataStartS);
        updateDepth.setDateTimeEnd(dataEndS);
        updateDepth.setNumberOfRecords(numberRecords);
        updateDepth.setTimeInterval(timeInterval);
        updateDepth.setParameters(parametersPresent.toString().trim()); // ub03
        where = CurDepth.CODE+"="+depthCodeVal;
        new CurDepth().upd(updateDepth,where);

    } // updateMooringDepth


    /**
     * load client and planam data
     */
    void loadClientPlanam() {

        String sub = "<br>loadClientPlanam: ";

        // load new client, get code
        if (!"".equals(newClientName)) {

            EdmClient2 client = new EdmClient2(
                INTN,
                newClientName
                );

            boolean success = client.put();
            clientCodeVal = client.getCode();
            if (dbg) System.out.println(sub+"clientCode "+clientCodeVal);
            if (dbg) System.out.println (sub+"client = "+client);

        } // if (!"".equals(newClientName))

        // load new platform, get code
        if (!"".equals(newPlatformName)) {

            CurPlanam planam = new CurPlanam(
                INTN,
                newPlatformName
                );

            planamCodeVal = planam.getCode();
            boolean success = planam.put();
            if (dbg) System.out.println (sub+"Planam = "+planam);

        } //if (!"".equals(newPlatformName)) {

    } //void loadClientPlanam() {


    /**
     * load mooring data
     */
    void loadNewMooring() {

        String sub = "<br>loadNewMooring: ";

        if ("".equals(description)) description = stnnam;

        if (dbg) System.out.println(sub+"loading.... mooring ");
        CurMooring mooring = new CurMooring(
            INTN,
            clientCodeVal,
            planamCodeVal,
            stnnam,
            arenam,
            description,
            latitude,
            longitude,
            stndep,
            newMooringStart,    // dateNull - updated later
            newMooringEnd,      // dateNull - updated later
            numberOfDepths,
            publicationRef,
            surveyID,                                                   // ub02
            prjnam                                                      // ub01
        );
        if (dbg) System.out.println(sub+"mooring load data line "+mooring);
        if (dbg) System.out.println(sub+"after new CurMooring");
        boolean success = mooring.put();
        if (dbg) System.out.println(sub+"after mooring.put" + success);
        if (dbg) System.out.println(sub+"sql = " + mooring.getInsStr());


        mooringCodeVal = mooring.getCode();

        /*
        if (dbg) System.out.println(sub+"mooring load data line "+mooringCodeVal+"|"+
            clientCode+"|"+planamCode+"|"+stnnam+"|"+arenam+"|"+description+"|"+
            latitude+"|"+longitude+"|"+stndep+"|"+mooringStart+"|"+mooringEnd+"|"+
            numberOfDepths+"|"+publicationRef);
        */
    } // loadNewMooring


    /**
     * Get the parameters infomation from data file (std format)
     * @return  void
     */
    void getStdHeaderInfo() {

        String sub = "<br>getStdHeaderInfo: ";

        lfile = ec.openFile(pathName+fileName,"r");

        // intialise notes
        for (int i = 0; i < notes.length; i++) notes[i] = "";

        line = ec.getNextValidLine(lfile) + "      ";
        //String text = line.substring(0,3);
        int notesNum = 0;
        //while (!"DAT".equals(text)) {
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
                //if ((notes[4].length() > 2) || notes[4].startsWith("--")) {
                if ((notes[4].length() > 2) && notes[4].startsWith("--")) {
                    notes[4] = notes[4].substring(2).trim();
                } // if ("--".equals(notes[4].substring(0,2))
                if (dbg) System.out.println(sub+"notes[4] = " + notes[4]);
            } // if ("(a)".equals(text))
*/

            // publication ref
            //if ("REP".equals(text)) {
            if (line.startsWith("REP")) {
                publicationRef = line.substring(13).trim();
            } // if (line.startsWith("REP"))

            // station name
            //if ("STA".equals(text)) {
            if (line.startsWith("STA")) {
                stnnam = line.substring(13).trim();
                if (dbg) System.out.println(sub+"stationName = "+stnnam);
            } // if (line.startsWith("STA"))

            // instrument type
            //if ("Ins".equals(text)) {
            if (line.startsWith("Ins")) {
                instrumentType = line.substring(13).trim();
                if (dbg) System.out.println(sub+"instrumentType = "+instrumentType);
            } // if (line.startsWith("Ins"))

            // deployment
            //if ("Dep".equals(text)) {
            if (line.startsWith("Dep")) {
                String temp = line.substring(13).trim();
                StringTokenizer t = new StringTokenizer(temp, "-");
                instrumentNumber = Integer.parseInt(t.nextToken());
                deploymentNumber = t.nextToken();
                //instrNo = line.substring(13,20).trim();
                //deploymentNo = line.substring(21).trim();
                if (dbg) System.out.println(sub+"instrumentNumber "+instrumentNumber);
                if (dbg) System.out.println(sub+"deploymentNumber "+deploymentNumber);
            } // if (line.startsWith("Dep"))

            // latitude
            //if ("Lat".equals(text)) {
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
            } // if (line.startsWith("Lat"))

            // longitude
            //if ("Lon".equals(text)) {
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
            } // if (line.startsWith("Lon"))

            // meter depth
            //if ("Met".equals(text)) {
            if (line.startsWith("Met")) {
                String temp = line.substring(13).trim();
                StringTokenizer token = new StringTokenizer(temp, " m");
                spldepVal = Float.parseFloat(token.nextToken());
                //if (dbg) System.out.println(sub+"t "+t);
                //meterDepth = java.lang.Math.round(t);
                if (dbg) System.out.println(sub+"spldepVal "+spldepVal);
            } // if (line.startsWith("Met"))

            // bottom depth
            //if ("Bot".equals(text)) {
            if (line.startsWith("Bot")) {
                String temp = line.substring(13).trim();
                StringTokenizer token = new StringTokenizer(temp, " m");
                float t = Float.parseFloat(token.nextToken());
                if (dbg) System.out.println(sub+"t "+t);
                stndep = java.lang.Math.round(t);
                if (dbg) System.out.println(sub+"stndep "+stndep);
            } // if (line.startsWith("Bot"))

            line = ec.getNextValidLine(lfile) + "      ";
            //text = line.substring(0,3);
        } // while (!line.startsWith("DAT"))

        ec.closeFile(lfile);

    } // getStdHeaderInfo()


    /**
     * Get the parameters infomation from data file. (cur format)
     * @return  void
     */
    void getCurHeaderInfo() {

        String sub = "<br>getCurHeaderInfo: ";

        lfile = ec.openFile(pathName+fileName,"r");

        // intialise notes
        for (int i = 0; i < notes.length; i++) notes[i] = "";
        int notesCount = 0;

        line = ec.getNextValidLine(lfile) + "              ";
        //String text = line.substring(0,3);
        //String text2 = line.substring(0,13);

        //while (!"DAT".equals(text)) {
        while (!line.startsWith("DAT")) {

            if (dbg) System.out.println(sub+"1line = " + line);
            //if (dbg) System.out.println(sub+"text = " + text + "/" + text2);
            //ec.writeFileLine(progressFile, "getCurHeaderInfo: line = " + line);
            //ec.writeFileLine(progressFile,
            //    "getCurHeaderInfo: text = " + text + "/" + text2);
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
                stnnam = line.substring(14).trim();
                if (dbg) System.out.println(sub+"stnnam = "+stnnam);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: stationName = " + stationName);
            } // if (line.startsWith("MOORING NAME "))

            // latitude
            //if ("LAT".equals(text)) {
            if (line.startsWith("LAT")) {
                String temp = line.substring(14).trim();
                latitude = Float.parseFloat(temp);
                int latDegrees = (int)latitude;
                float latMinutes   = (latitude - latDegrees) * 60f;
                latitudeD = String.valueOf(latDegrees);
                latitudeM = String.valueOf(latMinutes);
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
            } // if (line.startsWith("LAT"))

            // mooring start
            //if ("MOORING START".equals(text2)) {
            //    String temp = line.substring(14).trim();
            //    mooringStart = Timestamp.valueOf(temp+":00.0");
            //    if (dbg) System.out.println(sub+"mooringStart "+mooringStart);
            //    //ec.writeFileLine(progressFile,
            //    //    "getCurHeaderInfo: mooringStart = " + mooringStart);
            //} // if ("MOORING START".equals(text2))

            // mooring start
            //if ("MOORING END  ".equals(text2)) {
            //    String temp = line.substring(14).trim();
            //    mooringEnd = Timestamp.valueOf(temp+":00.0");
            //    if (dbg) System.out.println(sub+"mooringEnd "+mooringEnd);
            //    //ec.writeFileLine(progressFile,
            //    //    "getCurHeaderInfo: mooringEnd = " + mooringEnd);
            //} // if ("MOORING START".equals(text2))

            // meter depth
            //if ("INSTR. DEPTH ".equals(text2)) {
            if (line.startsWith("INSTR. DEPTH ")) {
                String temp = line.substring(14).trim();
                spldepVal = Float.parseFloat(temp);
                if (dbg) System.out.println(sub+"spldepVal = " + spldepVal);
                //ec.writeFileLine(progressFile,
                //    "getCurHeaderInfo: spldepVal = " + spldepVal);
            } // if (line.startsWith("INSTR. DEPTH "))

            // bottom depth
            //if ("MOORING DEPTH".equals(text2)) {
            if (line.startsWith("MOORING DEPTH")) {
                String temp = line.substring(14).trim();
                float t = Float.parseFloat(temp);
                if (dbg) System.out.println(sub+"t "+t);
                stndep = java.lang.Math.round(t);
                if (dbg) System.out.println(sub+"stndep "+stndep);
                //ec.writeFileLine(progressFile, "getCurHeaderInfo: stndep = " + stndep);
            } // if (line.startsWith("MOORING DEPTH"))

            // instrument number
            //if ("INSTRUMENT NO".equals(text2)) {
            if (line.startsWith("INSTRUMENT NO")) {
                instrumentNumber = Integer.parseInt(line.substring(14).trim());
                if (dbg) System.out.println(sub+"instrumentNumber "+instrumentNumber);
                //ec.writeFileLine(progressFile,
                //    "getCurHeaderInfo: instrumentNumber = " + instrumentNumber);
            } // if (line.startsWith("INSTRUMENT NO"))

            // deployment
            //if ("INSTRUMENT TP".equals(text2)) {
            if (line.startsWith("INSTRUMENT TP")) {
                instrumentType = line.substring(14).trim();
                if (dbg) System.out.println(sub+"instrumentType "+instrumentType);
                //ec.writeFileLine(progressFile,
                //    "getCurHeaderInfo: instrumentType = " + instrumentType);
            } // if (line.startsWith("INSTRUMENT TP"))

            // deployment
            //if ("DEPLOYMENT NO".equals(text2)) {
            if (line.startsWith("DEPLOYMENT NO")) {
                deploymentNumber = line.substring(14).trim();
                if (dbg) System.out.println(sub+"deploymentNumber "+deploymentNumber);
                //ec.writeFileLine(progressFile,
                //    "getCurHeaderInfo: deploymentNumber = " + deploymentNumber);
            } // if (line.startsWith("DEPLOYMENT NO"))

            // time interval
            //if ("SAMPLE INTERV".equals(text2)) {
            if (line.startsWith("SAMPLE INTERV")) {
                timeInterval = Integer.parseInt(line.substring(14).trim());
                if (dbg) System.out.println(sub+"timeInterval "+timeInterval);
                //ec.writeFileLine(progressFile,
                //    "getCurHeaderInfo: timeInterval = " + timeInterval);
            } // if (line.startsWith("SAMPLE INTERV"))

            // no of records
            //if ("NO OF RECORDS".equals(text2)) {
            //    numberOfRecords = Integer.parseInt(line.substring(14).trim());
            //    if (dbg) System.out.println(sub+"numberOfRecords "+numberOfRecords);
            //    //ec.writeFileLine(progressFile,
            //    //    "getCurHeaderInfo: numberOfRecords = " + numberOfRecords);
            //} // if ("Dep".equals(text))

            line = ec.getNextValidLine(lfile) + "              ";
            //text = line.substring(0,3);
            //text2 = line.substring(0,13);
        } // while (!"DAT".equals(text))

        ec.closeFile(lfile);

    } // getCurHeaderInfo()


    void loadDepth() {

        String sub = "<br>loadDepth: ";

        if (dbg) System.out.println(sub+"dateLoaded       = "+dateLoaded);

        // put method
        CurDepth depth = new CurDepth(
            INTN,
            mooringCodeVal,
            spldepVal,
            instrumentNumber,
            deploymentNumber,
            mooringStart,      // dateNull - updated later
            mooringEnd,        // dateNull - updated later
            timeInterval,      // 0 - updated later
            numberOfRecords,   // 0 - updated later
            passkey,
            dateLoaded,
            CurDepth.CHARNULL, // surveyId - use one in cur_mooring     // ub02
            CurDepth.CHARNULL  // parameters present - updated later    // ub03
        );

        depth.put();
        depthCodeVal = depth.getCode();

        if (dbg) System.out.println(sub+"depth = "+depth);
        if (dbg) System.out.println(sub+"insStr = " + depth.getInsStr());
        if (dbg) System.out.println(sub+"mesages = " + depth.getMessages());
    } // void loadDepth() {

    /**
     * load Depth Notes records.
     */
    void loadDepthNotes() {

        String sub = "<br>loadDepthNotes: ";

        // put method
        CurDepthNotes depthNotes = new CurDepthNotes();
        depthNotes.setDepthCode(depthCodeVal);

        int seqNo = 0;
        for (int i = 0; i < notes.length; i++) {
            if (!"".equals(notes[i])) {
                seqNo++;
                depthNotes.setSequenceNumber(seqNo);
                depthNotes.setNotes(notes[i]);
                depthNotes.put();
                if (dbg) System.out.println (sub+"depthNotes = "+depthNotes);
            } // if (!"".equals(notes[i]))
        } // for (int i = 0; i < notes.length; i++)

    } // void loadDepth() {

    /**
     * load Depth Quality records.
     */
    void loadDepthQuality() {
        // put method
        CurDepthQuality quality = new CurDepthQuality();
        quality.setDepthCode(depthCodeVal);

        quality.setSequenceNumber(1);
        quality.setNotes("The time interval might be inconsistent.");
        quality.put();
    } // void loadDepthQuality() {


    /**
     * check if institute exists, and insert if really new
     */
    void checkNewInstitute() {

    //int    institCodeVal      = 0;
    //String instituteName      = "";
    //String newInstitute        = "";
    //String newInstAddress     = "";

        String sub = "<br>checkNewInstitute: ";

        if (!"".equals(newInstitute)) {

            // get the institute list to check whether it exists
            MrnInstitutes institute[] = new MrnInstitutes().get("1=1", MrnInstitutes.CODE);
            boolean found = false;
            if (dbg) System.out.println(sub+"institute.length : " + institute.length);
            for (int i = 0; i < institute.length; i++) {
               if(institute[i].getName().equalsIgnoreCase(newInstitute)) {
                  found = true;
               } // if(institute[i].getName().equalsIgnoreCase(newInstitute))
            } // for (int i = 0; i < institute.length; i++)

            // insert the new institute if not found
            if (!found) {
                institCodeVal = institute[institute.length-1].getCode() + 1;
                if ("".equals(newInstAddress)) {
                    newInstAddress = MrnInstitutes.CHARNULL;
                } // if ("".equals(newInstAddress))
                MrnInstitutes instit = new MrnInstitutes(
                    institCodeVal, newInstitute, newInstAddress);
                if (dbg) System.out.println(sub+"instit = " + instit);
                instit.put();
                if (dbg) System.out.println(sub+"insStr = " + instit.getInsStr());
                if (dbg) System.out.println(sub+"numRecords = " + instit.getNumRecords());
            } // if (!found))

        } // if (!"".equals(newInstitute))

    } // checkNewInstitute


    /**
     * check if scientist exists, and insert if really new
     */
    void checkNewScientist() {

//    int    scientistCode      = 0;
//    String newSciTitle        = "";
//    String newSciFName        = "";
//    String newSciSurname      = "";
//    int    newSciInstitCode   = 0;

        String sub = "<br>checkNewScientist: ";

        if (!"".equals(newSciSurname)) {

            // get the instrument list to check whether it exists
            MrnScientists scientist[] = new MrnScientists().get("1=1", MrnScientists.CODE);
            boolean found = false;
            if (dbg) System.out.println(sub+"scientist.length : " + scientist.length);
            //int code = 0;
            for (int i = 0; i < scientist.length; i++) {
                if (scientist[i].getSurname().equalsIgnoreCase(newSciSurname) &&
                    scientist[i].getFName().equalsIgnoreCase(newSciFName)) {
                  found = true;
               } // if (scientist[i].getSurname().equalsIgnoreCase(newSciSurname) ...
            } // for (int i = 0; i < scientist.length; i++)

            // insert the new scientist if not found
            if (!found) {
                scientistCode = scientist[scientist.length-1].getCode() + 1;;
                if ("".equals(newSciTitle)) newSciTitle = MrnScientists.CHARNULL;
                if ("".equals(newSciFName)) newSciFName = MrnScientists.CHARNULL;
                MrnScientists scient = new MrnScientists(
                    scientistCode, newSciSurname, newSciFName, newSciTitle,
                    newSciInstitCode);
                if (dbg) System.out.println(sub+"scient = " + scient);
                scient.put();
                if (dbg) System.out.println(sub+"insStr = " + scient.getInsStr());
                if (dbg) System.out.println(sub+"numRecords = " + scient.getNumRecords());
            } // if (!found))

        } // if (!"".equals(newSciSurname))

    } // checkNewScientist


    /**
     * Function to check whether instrument exists,and insert if not.
     */
    private void insertInstrumentIfNotOnDB()   {

        String sub = "<br>insertInstrumentIfNotOnDB: ";

        if (instrumentNumber > 0) {
            String where = EdmInstrument2.CODE +" = "+ instrumentNumber;
            EdmInstrument2[] exist = new EdmInstrument2().get(where);
            if (exist.length == 0) {
            //    InstrExists = true;
            //} else {
                EdmInstrument2 instru = new EdmInstrument2(
                    instrumentNumber,
                    newInstrumentType
                    );
                boolean success = instru.put();
                if (dbg) System.out.println (sub+"instrument = " + instru);
            } // if (exists.length > 0) {
        } // if (instrumentNumber > 0)

    } // insertInstrumentIfNotOnDB()   {


    /**
     *  the function get average, min, max and record count.
     */
    void getStats() {

        // get depth details.
        // where = CurDepth.MOORING_CODE+"="+mooringCodeVal+" and "+
        //    CurDepth.DATE_TIME_START+"="+start+" and "+
        //    CurDepth.DATE_TIME_END+"="+end+" and "+
        //    CurDepth.SPLDEP+"="+spldepVal;

        //CurDepth depth[] = new CurDepth().get(where);
        //int depthCodeVal = depth[0].getCode();

        String sub = "<br>getStats: ";

        if (dbg) System.out.println(sub);

        field = "";
        where = "";
        order = "";

        // get CurData Speed SPEED stats.
        field = "min("+CurData.SPEED+") as "+CurData.SPEED;
        where = CurData.DEPTH_CODE+"="+depthCodeVal;
        CurData data[] = new CurData().get(field,where,order);

        //if (speedCnt > 0) {
            currentSpeedMin = data[0].getSpeed();
            if (dbg) System.out.println(sub+"currentSpeedMin "+currentSpeedMin);

            field = "max("+CurData.SPEED+") as "+CurData.SPEED;
            data = new CurData().get(field,where,order);
            currentSpeedMax = data[0].getSpeed();
            if (dbg) System.out.println(sub+"currentSpeedMax "+currentSpeedMax);

            field = "avg("+CurData.SPEED+") as "+CurData.SPEED;;
            data = new CurData().get(field,where,order);
            currentSpeedAvg = data[0].getSpeed();
            if (dbg) System.out.println(sub+"currentSpeedAvg "+currentSpeedAvg);
        //} //if (speedCnt > 0) {

        // get CurData DIRECTION stats.
        //if (directionCnt > 0) {
            field = "min("+CurData.DIRECTION+") as "+CurData.DIRECTION;

            data = new CurData().get(field,where,order);
            directionMin = data[0].getDirection();
            if (dbg) System.out.println(sub+"directionMin "+directionMin);

            field = "max("+CurData.DIRECTION+") as "+CurData.DIRECTION;
            data   = new CurData().get(field,where,order);
            directionMax = data[0].getDirection();
            if (dbg) System.out.println(sub+"directionMax "+directionMax);

            field = "avg("+CurData.DIRECTION+") as "+CurData.DIRECTION;
            data   = new CurData().get(field,where,order);
            directionAvg = data[0].getDirection();
            if (dbg) System.out.println(sub+"directionAvg "+directionAvg);
        //} //if (speedCnt > 0) {

        // get CurData Speed Temperature stats.
        if (tempCnt > 0) {
            field = "min("+CurData.TEMPERATURE+") as "+CurData.TEMPERATURE;

            data = new CurData().get(field,where,order);
            temperatureMin = data[0].getTemperature();

            field = "max("+CurData.TEMPERATURE+") as "+CurData.TEMPERATURE;
            data   = new CurData().get(field,where,order);
            temperatureMax = data[0].getTemperature();

            field = "avg("+CurData.TEMPERATURE+") as "+CurData.TEMPERATURE;
            data   = new CurData().get(field,where,order);
            temperatureAvg = data[0].getTemperature();
        } //if (tempCnt > 0) {

        // get CurData VER_VELOCITY stats.
        if (velocityCnt > 0) {
            field = "min("+CurData.VERT_VELOCITY+") as "+CurData.VERT_VELOCITY;

            data = new CurData().get(field,where,order);
            vertVelocityMin = data[0].getVertVelocity();

            field = "max("+CurData.VERT_VELOCITY+") as "+CurData.VERT_VELOCITY;
            data   = new CurData().get(field,where,order);
            vertVelocityMax = data[0].getVertVelocity();

            field = "avg("+CurData.VERT_VELOCITY+") as "+CurData.VERT_VELOCITY;
            data   = new CurData().get(field,where,order);
            vertVelocityAvg = data[0].getVertVelocity();
        } //if (velocityCnt > 0) {

        // get CurData F_SPEED_9 stats.
        if (fSpeed9Cnt > 0) {
            field = "min("+CurData.F_SPEED_9+") as "+CurData.F_SPEED_9;

            data = new CurData().get(field,where,order);
            fSpeed9Min = data[0].getFSpeed9();

            field = "max("+CurData.F_SPEED_9+") as "+CurData.F_SPEED_9;
            data   = new CurData().get(field,where,order);
            fSpeed9Max = data[0].getFSpeed9();

            field = "avg("+CurData.F_SPEED_9+") as "+CurData.F_SPEED_9;
            data   = new CurData().get(field,where,order);
            fSpeed9Avg = data[0].getFSpeed9();
        } //if (fspeed9Cnt > 0) {

        // get CurData F_DIRECTION_9 stats.
        if (fDirection9Cnt > 0) {
            field = "min("+CurData.F_DIRECTION_9+") as "+CurData.F_DIRECTION_9;

            data = new CurData().get(field,where,order);
            fDirection9Min = data[0].getFDirection9();

            field = "max("+CurData.F_DIRECTION_9+") as "+CurData.F_DIRECTION_9;
            data   = new CurData().get(field,where,order);
            fDirection9Max = data[0].getFDirection9();

            field = "avg("+CurData.F_DIRECTION_9+") as "+CurData.F_DIRECTION_9;
            data   = new CurData().get(field,where,order);
            fDirection9Avg = data[0].getFDirection9();
        } //if (fDirection14Cnt > 0) {

        // get CurData F_SPEED_14 stats.
        if (fSpeed14Cnt > 0) {
            field = "min("+CurData.F_SPEED_14+") as "+CurData.F_SPEED_14;

            data = new CurData().get(field,where,order);
            fSpeed14Min = data[0].getFSpeed14();

            field = "max("+CurData.F_SPEED_14+") as "+CurData.F_SPEED_14;
            data   = new CurData().get(field,where,order);
            fSpeed14Max = data[0].getFSpeed14();

            field = "avg("+CurData.F_SPEED_14+") as "+CurData.F_SPEED_14;
            data   = new CurData().get(field,where,order);
            fSpeed14Avg = data[0].getFSpeed14();
        } //if (fDirection14Cnt > 0) {

        // get CurData F_DIRECTION_14 stats.
        if (fDirection14Cnt > 0) {
            field = "min("+CurData.F_DIRECTION_14+") as "+CurData.F_DIRECTION_14;

            data = new CurData().get(field,where,order);
            fDirection14Min = data[0].getFDirection14();

            field = "max("+CurData.F_DIRECTION_14+") as "+CurData.F_DIRECTION_14;
            data   = new CurData().get(field,where,order);
            fDirection14Max = data[0].getFDirection14();

            field = "avg("+CurData.F_DIRECTION_14+") as "+CurData.F_DIRECTION_14;
            data   = new CurData().get(field,where,order);
            fDirection14Avg = data[0].getFDirection14();
        } //if (fDirection14Cnt > 0) {

        // whatphy
        // get CurData pH stats.
        field = "min("+CurWatphy.PH+") as "+CurWatphy.PH;

        CurWatphy watphy[] = new CurWatphy().get(field,where,order);
        if (phCnt > 0) {
            phMin = watphy[0].getPh();

            field = "max("+CurWatphy.PH+") as "+CurWatphy.PH;
            watphy   = new CurWatphy().get(field,where,order);
            phMax = watphy[0].getPh();

            field = "avg("+CurWatphy.PH+") as "+CurWatphy.PH;
            watphy   = new CurWatphy().get(field,where,order);
            phAvg = watphy[0].getPh();
        } //if (phCnt > 0) {

        // get CurData Salinity stats.
        if (salinityCnt > 0) {
            field = "min("+CurWatphy.SALINITY+") as "+CurWatphy.SALINITY;

            //CurWatphy
            watphy = new CurWatphy().get(field,where,order);
            salinityMin = watphy[0].getSalinity();

            field = "max("+CurWatphy.SALINITY+") as "+CurWatphy.SALINITY;
            watphy   = new CurWatphy().get(field,where,order);
            salinityMax = watphy[0].getSalinity();

            field = "avg("+CurWatphy.SALINITY+") as "+CurWatphy.SALINITY;
            watphy   = new CurWatphy().get(field,where,order);
            salinityAvg = watphy[0].getSalinity();
        } //if (salinityCnt > 0) {

        // get CurData DIS_OXY stats.
        if (disOxyCnt > 0 ) {
            field = "min("+CurWatphy.DIS_OXY+") as "+CurWatphy.DIS_OXY;

            watphy = new CurWatphy().get(field,where,order);
            disOxyMin = watphy[0].getDisOxy();

            field = "max("+CurWatphy.DIS_OXY+") as "+CurWatphy.DIS_OXY;
            watphy   = new CurWatphy().get(field,where,order);
            disOxyMax = watphy[0].getDisOxy();

            field = "avg("+CurWatphy.DIS_OXY+") as "+CurWatphy.DIS_OXY;
            watphy   = new CurWatphy().get(field,where,order);
            disOxyAvg = watphy[0].getDisOxy();
        } //if (disOxyCnt > 0 ) {

        // get CurData PRESSURE stats.
        if (pressureCnt > 0) {
            field = "min("+CurData.PRESSURE+") as "+CurData.PRESSURE;

            data = new CurData().get(field,where,order);
            pressureMin = data[0].getPressure();

            field = "max("+CurData.PRESSURE+") as "+CurData.PRESSURE;
            data   = new CurData().get(field,where,order);
            pressureMax = data[0].getPressure();

            field = "avg("+CurData.PRESSURE+") as "+CurData.PRESSURE;
            //CurData
            data   = new CurData().get(field,where,order);
            pressureAvg = data[0].getPressure();
        } //if (pressureCnt > 0) {
    } // getStats


    /**
     *  display the load Report.
     */
    //DynamicTable loadReport() {
    void writeReportFile() {

        String sub = "<br>writeReportFile: ";

        DynamicTable reportTable = new DynamicTable(1);
        reportTable.setFrame(ITableFrame.VOLD);
        reportTable.setRules(ITableRules.NONE);
        reportTable.setBorder(0);
        reportTable.setCenter();
        reportTable.setFrame(ITableFrame.BOX);

        if (dbg) System.out.println(sub);
        if (dbg) System.out.println(sub+"startDate        "+newMooringStart);
        if (dbg) System.out.println(sub+"endDate          "+newMooringEnd);
        if (dbg) System.out.println(sub+"pathName         "+pathName);
        if (dbg) System.out.println(sub+"fileName         "+fileName);
        if (dbg) System.out.println(sub+"invalidValueCnt  "+invalidValueCnt);

        //System.out.println(sub+"frmFiller = *" + ec.getFrmFiller() + "*");
        ec.setFrmFiller(" ");

        // Open the report file.
        ec.deleteOldFile(pathName+fileName+".report");
        reportFile = ec.openFile(pathName+fileName+".report", "rw");
        //try {
        //    reportFile = new RandomAccessFile(pathName+fileName+".report", "rw");
        //} catch (Exception e) {
        //    System.out.println(sub+"Open reportFile Error: " + e.getMessage());
        //    e.printStackTrace();
        //} // try-catch

        if (ec.getHost().startsWith(cc.HOST)) {
            ec.submitJob("chmod 644 " + pathName+fileName+".report");
        } // if (ec.getHost().startsWith(cc.HOST))

        if (dbg) System.out.println(sub+"in try ");
        if (dbg) System.out.println(sub+"startDate        "+newMooringStart);
        if (dbg) System.out.println(sub+"endDate          "+newMooringEnd);

        ec.writeFileLine(reportFile, " Code Depth Start            End              Latitude  Longitude Station Name          Stns Client          Platform");
        ec.writeFileLine(reportFile, "----- ----- ---------------- ---------------- --------- --------- -------------------- ----- --------------- ----------------");

        //reportFile.writeBytes(
        ec.writeFileLine(reportFile,
            ec.frm(mooringCodeVal,5)+
            ec.frm(stndep,6)+" "+
            ec.frm(String.valueOf(newMooringStart),16)+" "+
            ec.frm(String.valueOf(newMooringEnd),16)+
            ec.frm(latitude,10,5)+
            ec.frm(longitude,10,5)+" "+
            ec.frm(stnnam,20)+
            ec.frm(numberOfDepths,6)+" "+
            ec.frm(("".equals(clientName)?newClientName:clientName),15)+" "+
            ec.frm(("".equals(platformName)?newPlatformName:platformName),16)+"\n");

        // Print the cur_mooring header.
        ec.writeFileLine(reportFile, "Depth Sample                                         Instr                                No.of");
        ec.writeFileLine(reportFile, " Code  Depth Start            End              Intv     No. Depl No.     Instr. Type          Rcrds");
        ec.writeFileLine(reportFile, "----- ------ ---------------- ---------------- ----  ------ ------------ -------------------- -----");

        ec.writeFileLine(reportFile,
            ec.frm(depthCodeVal,5)+
            ec.frm(spldepVal,7,2)+" "+
            //ec.frm(String.valueOf(mooringStart),16)+" "+
            //ec.frm(String.valueOf(mooringEnd),16)+
            dataStartS+" "+
            dataEndS+
            ec.frm(timeInterval,5)+
            ec.frm(instrumentNumber,8)+" "+
            ec.frm(deploymentNumber,12)+" "+
            ec.frm(instrumentType,20)+
            //ec.frm(String.valueOf(numberOfRecords),9)+"\n\n" );
            ec.frm(recordsLoaded,6)+"\n" );

        // Print Loading stats header.
        ec.writeFileLine(reportFile, "Loading statistics:");
        ec.writeFileLine(reportFile, "-------------------");
        ec.writeFileLine(reportFile, "                            average      min      max    count");
        ec.writeFileLine(reportFile, "                           -------- -------- -------- --------");

        ec.writeFileLine(reportFile,"            data records: "+ec.frm(recCnt,36));
        ec.writeFileLine(reportFile,"                   speed: "+
            (currentSpeedAvg == FLOATN?ec.frm("",9):ec.frm(currentSpeedAvg,9,2))+
            (currentSpeedMin == FLOATN?ec.frm("",9):ec.frm(currentSpeedMin,9,2))+
            (currentSpeedMax == FLOATN?ec.frm("",9):ec.frm(currentSpeedMax,9,2))+
            (speedCnt == 0?ec.frm("",9):ec.frm(speedCnt,9)));
        ec.writeFileLine(reportFile,"               direction: "+
            (directionAvg == FLOATN?ec.frm("",9):ec.frm(directionAvg,9,2))+
            (directionMin == FLOATN?ec.frm("",9):ec.frm(directionMin,9,2))+
            (directionMax == FLOATN?ec.frm("",9):ec.frm(directionMax,9,2))+
            (directionCnt == 0?ec.frm("",9):ec.frm(directionCnt,9)));
        ec.writeFileLine(reportFile,"             temperature: "+
            (temperatureAvg == FLOATN?ec.frm("",9):ec.frm(temperatureAvg,9,2))+
            (temperatureMin == FLOATN?ec.frm("",9):ec.frm(temperatureMin,9,2))+
            (temperatureMax == FLOATN?ec.frm("",9):ec.frm(temperatureMax,9,2))+
            (tempCnt == 0?ec.frm("",9):ec.frm(tempCnt,9)));
        ec.writeFileLine(reportFile,"       vertical velocity: "+
            (vertVelocityAvg == FLOATN?ec.frm("",9):ec.frm(vertVelocityAvg,9,2))+
            (vertVelocityMin == FLOATN?ec.frm("",9):ec.frm(vertVelocityMin,9,2))+
            (vertVelocityMax == FLOATN?ec.frm("",9):ec.frm(vertVelocityMax,9,2))+
            (velocityCnt == 0?ec.frm("",9):ec.frm(velocityCnt,9)));
        ec.writeFileLine(reportFile,"       filtered speed 9h: "+
            (fSpeed9Avg == FLOATN?ec.frm("",9):ec.frm(fSpeed9Avg,9,2))+
            (fSpeed9Min == FLOATN?ec.frm("",9):ec.frm(fSpeed9Min,9,2))+
            (fSpeed9Max == FLOATN?ec.frm("",9):ec.frm(fSpeed9Max,9,2))+
            (fSpeed9Cnt == 0?ec.frm("",9):ec.frm(fSpeed9Cnt,9)));
        ec.writeFileLine(reportFile,"   filtered direction 9h: "+
            (fDirection9Avg == FLOATN?ec.frm("",9):ec.frm(fDirection9Avg,9,2))+
            (fDirection9Min == FLOATN?ec.frm("",9):ec.frm(fDirection9Min,9,2))+
            (fDirection9Max == FLOATN?ec.frm("",9):ec.frm(fDirection9Max,9,2))+
            (fDirection9Cnt == 0?ec.frm("",9):ec.frm(fDirection9Cnt,9)));
        ec.writeFileLine(reportFile,"    filtered speed 14.5h: "+
            (fSpeed14Avg == FLOATN?ec.frm("",9):ec.frm(fSpeed14Avg,9,2))+
            (fSpeed14Min == FLOATN?ec.frm("",9):ec.frm(fSpeed14Min,9,2))+
            (fSpeed14Max == FLOATN?ec.frm("",9):ec.frm(fSpeed14Max,9,2))+
            (fSpeed14Cnt == 0?ec.frm("",9):ec.frm(fSpeed14Cnt,9)));
        ec.writeFileLine(reportFile,"filtered direction 14.5h: "+
            (fDirection14Avg == FLOATN?ec.frm("",9):ec.frm(fDirection14Avg,9,2))+
            (fDirection14Min == FLOATN?ec.frm("",9):ec.frm(fDirection14Min,9,2))+
            (fDirection14Max == FLOATN?ec.frm("",9):ec.frm(fDirection14Max,9,2))+
            (fDirection14Cnt == 0?ec.frm("",9):ec.frm(fDirection14Cnt,9)));
        ec.writeFileLine(reportFile,"                      pH: "+
            (phAvg == FLOATN?ec.frm("",9):ec.frm(phAvg,9,2))+
            (phMin == FLOATN?ec.frm("",9):ec.frm(phMin,9,2))+
            (phMax == FLOATN?ec.frm("",9):ec.frm(phMax,9,2))+
            (phCnt == 0?ec.frm("",9):ec.frm(phCnt,9)));
        ec.writeFileLine(reportFile,"                salinity: "+
            (salinityAvg == FLOATN?ec.frm("",9):ec.frm(salinityAvg,9,2))+
            (salinityMin == FLOATN?ec.frm("",9):ec.frm(salinityMin,9,2))+
            (salinityMax == FLOATN?ec.frm("",9):ec.frm(salinityMax,9,2))+
            (salinityCnt == 0?ec.frm("",9):ec.frm(salinityCnt,9)));
        ec.writeFileLine(reportFile,"        dissolved oxygen: "+
            (disOxyAvg == FLOATN?ec.frm("",9):ec.frm(disOxyAvg,9,2))+
            (disOxyMin == FLOATN?ec.frm("",9):ec.frm(disOxyMin,9,2))+
            (disOxyMax == FLOATN?ec.frm("",9):ec.frm(disOxyMax,9,2))+
            (disOxyCnt == 0?ec.frm("",9):ec.frm(disOxyCnt,9)));
        ec.writeFileLine(reportFile,"                pressure: "+
            (pressureAvg == FLOATN?ec.frm("",9):ec.frm(pressureAvg,9,2))+
            (pressureMin == FLOATN?ec.frm("",9):ec.frm(pressureMin,9,2))+
            (pressureMax == FLOATN?ec.frm("",9):ec.frm(pressureMax,9,2))+
            (pressureCnt == 0?ec.frm("",9):ec.frm(pressureCnt,9)));
        ec.writeFileLine(reportFile,"Notes:");
        for (int i = 0; i < notes.length; i++) {
            ec.writeFileLine(reportFile,"  "+notes[i]);
        } // for (int i = 0; i < notes.length; i++)

        // close report file.
        ec.closeFile(reportFile);

    } // void reportFile() {


    /**
     * update the inventory for the new last date
     */
    void updateInventory() {

        MrnInventory inv = new MrnInventory();
        inv.setSurveyId(surveyID);
        MrnInventory invA[] = inv.get();
        if (invA.length > 0) {
            MrnInventory updInv = new MrnInventory();
            updInv.setDateEnd(newMooringStart);
            updInv.setDateEnd(newMooringEnd);
            updInv.setDataRecorded("Y");
            inv.upd(updInv);
        } else {
            inv.setDataCentre        ("SADCO");
            inv.setProjectName       (prjnam);
            inv.setCruiseName        (stnnam);
            //inv.setNationalPgm       (nationalPgm      );
            //inv.setExchangeRestrict  (exchangeRestrict );
            //inv.setCoopPgm           (coopPgm          );
            //inv.setCoordinatedInt    (coordinatedInt   );
            inv.setPlanamCode        (planamCodeVal);
            //inv.setPortStart         (portStart        );
            //inv.setPortEnd           (portEnd          );
            inv.setCountryCode       (1);
            inv.setInstitCode        (institCodeVal);
            inv.setCoordCode         (institCodeVal);
            inv.setSciCode1          (scientistCode);
            inv.setSciCode2          (1);
            inv.setDateStart         (newMooringStart);
            inv.setDateEnd           (newMooringEnd);
            inv.setLatNorth          (latitude);
            inv.setLatSouth          (latitude);
            inv.setLongWest          (longitude);
            inv.setLongEast          (longitude);
            inv.setAreaname          (arenam);
            //inv.setDomain            (domain           );
            inv.setTrackChart        ("N");
            inv.setTargetCountryCode (1);
            //inv.setStnidPrefix       (stnidPrefix      );
            inv.setGmtDiff           (0);
            inv.setGmtFreeze         ("N");
            inv.setProjectionCode    (1);
            inv.setSpheroidCode      (1);
            inv.setDatumCode         (1);
            inv.setDataRecorded      ("Y");
            inv.setSurveyTypeCode    (dataType);
            if ("".equals(passkey)) {
                inv.setDataAvailable     ("Y");
            } else {
                inv.setDataAvailable     ("N");
            } // if ("".equals(passkey))

            inv.put();
//insert into inventory (survey_id, data_centre,
//project_name, cruise_name, planam_code, country_code,
//instit_code, coord_code, sci_code_1, sci_code_2,
//date_start, date_end,
//lat_north, lat_south, long_west, long_east,
//areaname, track_chart, target_country_code, gmt_diff, gmt_freeze,
//projection_code, spheroid_code, datum_code, data_recorded, data_available,
//survey_type_code)
//values ('2009/0185','SADCO',
//'Longterm monitoring network UTR','Aliwal Shoal UTR',1,1,
//175,1,168,1,
//'2001-10-20 09:00:00','2010-01-21 10:00:00',
//30.2643,30.2643,30.828,30.828,
//'SE Coast','N',1,0,'N',
//1,1,1,'Y','Y',2);

        } // if (invA.length > 0)

    } // updateInventory


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("LoadCURDataFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreLoadCURDataFrame local= new PreLoadCURDataFrame(args);
            bd.addItem(new LoadCURDataFrame(args));
            hp.printHeader();
            hp.print();
            if (standalone) common2.DbAccessC.close();
        } catch(Exception e) {
            ec.processErrorStatic(e, "LoadCURDataFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class

