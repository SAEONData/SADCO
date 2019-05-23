package sadco;

/**
 * Contains all the constants used in the sadco package.
 *
 * Sadco
 * screen.equals("matrix")
 *
 * @author 20001107 - SIT Group
 * @version
 * 001107 - Ursula von St Ange - create class                             <br>
 * 020527 - Ursula von St Ange - update for non-frame menus               <br>
 * 020527 - Ursula von St Ange - update for exception catching            <br>
 * 030729 - Ursula von St Ange - update for fred                          <br>
 * 040609 - Ursula von St Ange - add stuff for loading of underway
 *                             currents (ub01)                            <br>
 * 060316 - Ursula von St Ange - add stuff for loading of sediment data
 *                             (ub02)                                     <br>
 * 080519 - Ursula von St Ange - add stuff for weather                    <br>
 * 150324 - Ursula von St Ange - add underway current components (ub03)   <br>
 */
public class SadConstants {

    /** A general purpose class */
    static common.edmCommonPC ec = new common.edmCommonPC();

    boolean dbg = false;
    //boolean dbg = true;

    /** general purpose variables */
    static final public String CR = System.getProperty("line.separator");

    /** the applications */
    static final public String LOGIN_USER_APP   = "Sadco";
    static final public String MENU_APP         = "SadcoMenu";
    static final public String VOS_APP          = "SadcoVOS";
    static final public String MRN_APP          = "SadcoMRN";
    static final public String CUR_APP          = "SadcoCUR";
    static final public String WET_APP          = "SadcoWET";
    static final public String WAV_APP          = "SadcoWAV";
    public static final boolean LIVE            = true;

    /** host name */
    //static final public String HOST = "freddy";
    static final public String HOST = SadConstants.LIVE ? "sadco.ocean.gov.za" : "sadco.int.ocean.gov.za";
    static final public String HOST2 = SadConstants.LIVE ? "sadco.ocean.gov.za" : "sadco.int.ocean.gov.za";
    //static final public String HOST     = "fred";
    //static final public String HOST_ST = "steamer";

    /** root path names */
    //static final public String DISK = "/usr2";
    static final public String DISK = "/home";
    static final public String HOSTDIR = DISK+"/localuser/sadco/output/";
    static final public String LOCALDIR = DISK+"/localuser/sadco/output/"; // "/home/localuser/Desktop/";
    static final public String HOSTDIRL = DISK+"/localuser/sadco_constants/loads/";
    static final public String HOSTDIRLOG = DISK+"/localuser/sadco_constants/logs/";

    /** script names */
    static final public String JAVA = DISK+"/localuser/sadco/sh_scripts/java.sh";
    static final public String FORTRAN = DISK+"/localuser/sadco/sh_scripts/fortran.sh";

    /** data download path */
    static final public String DATA_URL  = "http://"+HOST2+"/sadco-data/";
    static final public String LOADS_URL = "http://"+HOST2+"/sadco-loads/";


    /** url parameters names */
    /** general */
    static final public String SCREEN      = "pscreen";
    static final public String ACTION      = "paction";
    static final public String VERSION     = "pversion";
    static final public String MENUNO      = "pmenuno";
    static final public String MVERSION    = "pmversion";

    /** security */
    static final public String SESSIONCODE = "psc";
    static final public String USERTYPE    = "put";
    static final public String TIMEOUT     = "pto";
    static final public String[] USERTYPELIST = {
        "",               // 0
        "Administrator",  // 1
        "Normal user",    // 2
        "Data Set"};      // 3
    static final public int UTADMIN            = 1;
    static final public int UTNORML            = 2;
    static final public int UTDATAS            = 3;
    static final public String[] FLAGTYPELIST = {
        "",               // 0
        "allowed",        // 1
        "not allowed",    // 2
        "not applicable"};// 3
    static final public int FTALLOW            = 1;
    static final public int FTNOTAL            = 2;
    static final public int FTNOTAP            = 3;

    /** user authentication / administration */
    static final public String USERID      = "puserid";
    static final public String NUSERTYPE   = "pnewut";
    static final public String OUSERTYPE   = "poldut";
    static final public String FLAGTYPE    = "pflagtype";
    static final public String OFLAGTYPE   = "poldflagtype";
    static final public String DATASET     = "pdataset";
    static final public String PASSWORD    = "ppassword";
    static final public String PASSWORD2   = "ppassword2";
    static final public String OPASSWORD   = "popassword";
    static final public String FPASSWORD   = "pfpassword";
    static final public String FPASSWORD2  = "pfpassword2";
    static final public String OFPASSWORD  = "pofpassword";

    /** loads / extractions / products */
    static final public String BEGINLONG       = "pbeginlong";
    static final public String DATABASE        = "pdatabase";
    static final public String ELEMENTTYPE     = "pelementtype";
    static final public String ENDDATE         = "penddate";
    static final public String ENDLONG         = "pendlong";
    static final public String EXTRTYPE        = "pextrtype";
    static final public String FILENAME        = "pfilename";
    static final public String FORMAT          = "pformat";
    static final public String LATITUDENORTH   = "platitudenorth";
    static final public String LATITUDESOUTH   = "platitudesouth";
    static final public String LOADID          = "ploadid";
    static final public String LONGITUDEEAST   = "plongitudeeast";
    static final public String LONGITUDEWEST   = "plongitudewest";
    static final public String MULTITYPE       = "pmultitype";
    static final public String PLOTTYPE        = "pplottype";
    static final public String REQNUMBER       = "preqnumber";
    static final public String RESPPERSON      = "prespperson";
    static final public String SOURCE          = "psource";
    static final public String STARTDATE       = "pstartdate";
    static final public String STATIONID       = "pstationid";
    static final public String SURVEYID        = "psurveyid";
    static final public String TABLE           = "ptable";
    static final public String TABLETYPE       = "ptabletype";
    static final public String TIMEUNITS       = "ptimeunits";
    static final public String VOSLOADID       = "pvosloadid";

    static final public String CONFIRM         = "pconfirm";
    static final public String KEEPINV         = "pkeepinv";

    static final public String LOADFLAG        = "ploadflag";
    static final public String LOADTYPE        = "ploadtype";           // ub01
    static final public String TIMEZONE        = "ptimezone";

    static final public String AREANAME        = "pareaname";
    static final public String COUNTRYCODE     = "pcountrycode";
    static final public String CRUISENAME      = "pcruisename";
    static final public String DOMAIN          = "pdomain";
    static final public String INSTITCODE      = "pinstitcode";
    static final public String INSTITUTE       = "pinstitute";
    static final public String INVENTORYEXIST  = "pinventoryexist";
    static final public String PASSKEY         = "ppasskey";
    static final public String PLANAMCODE      = "pplanamcode";
    static final public String PROJECTNAME     = "pprojectname";
    static final public String REPLACEDATA     = "preplacedata";
    static final public String SCIENTCODE      = "pscientcode";
    static final public String SUBMITSC        = "psubmitsc";
    static final public String UPDATESTATION   = "pupdatestation";
    static final public String DEPTHCODE       = "pdepthcode";

    static final public String SUBDES          = "psubdes";

    static final public String APPLIC          = "papplic";

    // sadcoWET
    static final public String CLIENTCODE      = "pclientcode";
    static final public String CLIENTNAME      = "pclientname";
    static final public String CODE            = "pcode";
    static final public String INSTRUMENTCODE  = "pinstrumentcode";
    static final public String INTERVAL        = "pinterval";
    static final public String LATITUDE        = "platitude";
    static final public String LONGITUDE       = "plongitude";
    static final public String NAME            = "pname";
    static final public String NEWCLIENT       = "pnewclient";
    static final public String NEWINSTADDRESS  = "pnewinstaddress";
    static final public String NEWINSTITUTE    = "pnewinstitute";
    static final public String NEWINSTRUMENT   = "pnewinstrument";
    static final public String NEWSCFIRSTNAME  = "pnewscfirstname";
    static final public String NEWSCINST       = "pnewscinst";
    static final public String NEWSCSURNAME    = "pnewscsurname";
    static final public String NEWSCTITLE      = "pnewsctitle";
    static final public String NEWSTATIONID    = "pnewstationid";
    static final public String NULLS           = "pnulls";
    static final public String PERIODCODE      = "pperiodcode";
    static final public String RECORDS         = "precords";

    // sadcoCUR
    static final public String ARENAM             = "parenam";
    static final public String CHECKINTERVAL      = "pcheckinterval";
    static final public String DESCRIPTION        = "pdescription";
    static final public String EXTENSION          = "pextension";
    static final public String MOORINGCODE        = "pmooringcode";
    static final public String NEWCLIENTNAME      = "pnewclientname";
    static final public String NEWMOORINGEND      = "pnewmooringend";
    static final public String NEWMOORINGSTART    = "pnewmooringstart";
    static final public String NEWPLATFORMNAME    = "pnewplatformname";
    static final public String PARTDEPTH          = "ppartdepth";
    static final public String PRJNAM             = "pprjnam";
    static final public String PUBLREF            = "ppublref";
    static final public String STNNAM             = "pstnnam";
    static final public String OPTION             = "poption";
    static final public String DATA_TYPE          = "pdatatype";
    static final public String DELINV             = "pdelinv";


    static final public int    INCR            = 100;
    static final public int    MAX_BEARING_DEV = 20;                    //ub03

    static final public String[][] MRNLODLIST  =  {                     // ub01
        {"Water Physical Data (CTD)","water"},                          // ub01
        {"WOD Water Physical Data (CTD)","waterWOD"},                   // ub01
        {"Underway Currents Data","currents"},                          // ub01
        {"Sediment (sedphy, -chem, -pol)","sediment"}};                 // ub02

    static final public String[] MRNEXTRLIST   =  {
        "Physical/Nutrient Oceanography",   // 0
        //"Physical/Nutrient ODV Format",   //
        "Water Pollution",                  // 1
        "Water Chemistry 1 (n/a)",          // 2
        "Sediment Pollution",               // 3
        "Sediment Chemistry 1 (n/a)",       // 4
        "Tissue Pollution",                 // 5
        "Biology (n/a)",                    // 6
        "Weather (n/a)",                    // 7
        "Underway Currents",                // 8                        // ub01
        "Underway Currents Components",     // 9                        // ub03
        "Station Information Only"};         // 10

    static final public String[] MRNFORMATLIST   =  {
        "Standard", // 0
        "ODV"   // 1 //"Physical/Nutrient ODV Format",   // 2
    };

    static final public int PNOCE              = 1;
    static final public int PNODV              = 2;
    static final public int WPOL1              = 3;
    static final public int WCHE1              = 4;
    static final public int SPOL1              = 5;
    static final public int SCHE1              = 6;
    static final public int BIOLO              = 7;
    static final public int WEATH              = 8;
    static final public int UNDCR              = 9;

    static final public String PRODUCT         = "pproduct";
    static final public String[] VOSPRODLIST   = {
        "Inventories",   // 1
        "Means",         // 2
        "Roses",         // 3
        "Histograms",    // 4
        "Scattergrams",  // 5
        "Time Series"};  // 6
    static final public int VINVN              = 1;
    static final public int VMEAN              = 2;
    static final public int VROSE              = 3;
    static final public int VHIST              = 4;
    static final public int VSCAT              = 5;
    static final public int VTIME              = 6;

    static final public String[] MRNPRODLIST   = {
        "Inventories",                   // 1
        "Temperature Salinity Curves",   // 2
        "Means",                         // 3
        //"Scattergrams",                     //4
        //"Time Series",                      //5
        //"Profiles",                         //6
        "Create other formats ",         // 4
        "Thermo Means"};                 // 5
    static final public int MINVN              = 1;
    static final public int MTSPL              = 2;
    static final public int MMEAN              = 3;
    static final public int MOTHR              = 4;
    static final public int MTHRM              = 5;

    static final public String STARTYEAR       = "pstartyear";
    static final public String ENDYEAR         = "pendyear";
    static final public String STARTMONTH      = "pstartmonth";
    static final public String ENDMONTH        = "pendmonth";
    static final public String STARTDEPTH      = "pstartdepth";
    static final public String ENDDEPTH        = "penddepth";

    static final public String STARTRANGE      = "pstartrange";
    static final public String ENDRANGE        = "pendrange";
    static final public String XSTARTRANGE     = "pxstartrange";
    static final public String XENDRANGE       = "pxendrange";
    static final public String YSTARTRANGE     = "pystartrange";
    static final public String YENDRANGE       = "pyendrange";

    static final public String DEPTHLOW        = "pdepthlow";
    static final public String DEPTHHIGH       = "pdepthhigh";
    static final public String PROTEMPLOW      = "pprotemplow";
    static final public String PROTEMPHIGH     = "pprotemphigh";
    static final public String PROSALINLOW     = "pprosalinlow";
    static final public String PROSALINHIGH    = "pprosalinhigh";
    static final public String DISSOXYLOW      = "pdissoxylow";
    static final public String DISSOXYHIGH     = "pdissoxyhigh";

    static final public String DAYRLOW         = "pdayrlow";
    static final public String DAYRHIGH        = "pdayrhigh";
    static final public String HOURRLOW        = "phourrlow";
    static final public String HOURRHIGH       = "phourrhigh";
    static final public String AXISRLOW        = "paxisrlow";
    static final public String AXISRHIGH       = "paxisrhigh";
    static final public String STDDEV          = "pstddev";
    static final public String UNITPCELL       = "punitpcell";

    static final public String TABLESIZE       = "ptablesize";
    static final public String BLOCKSIZE       = "pblocksize";

    static final public String TEMPLOW         = "ptemplow";
    static final public String TEMPHIGH        = "ptemphigh";
    static final public String SALINLOW        = "psalinlow";
    static final public String SALINHIGH       = "psalinhigh";

    static final public String MINDEPPAR       = "pmindeppar";
    static final public String MINDEPVAL       = "pmindepval";
    static final public String LIMIT1          = "plimit1";
    static final public String LIMIT2          = "plimit2";

    //static final public String STATIONLIST     = "pstationlist";

    static final public String PARMS           = "pparms";
    //static final public int VOSPARMMAX         = 10;
    static final public String[] VOSPARMSLIST  = {
        "Water Surface Temp (deg C)",
        "Atmospheric Pressure (kPa)",
        "Swell Height (m)",
        "Wind Speed (m/s)",
        "Air Temperature (Dry Bulb) (deg C)",
        "Air Temperature (Wet Bulb) (deg C)",
        "Dew Point Temperature (deg C)",
        "Wind Wave Height (m)",
        "Cloud Cover (%)",
        "Sigma T"};
    static final public int MRNPARMMAX         = 11;
    static final public String[] MRNPARMSLIST  = {
        "Water Temperature (deg C)",
        "Salinity (ppt)",
        "Dissolved Oxygen (ml/l)",
        "Nitrite (NO2) (ug atom/l)",
        "Nitrate (NO3) (ug atom/l)",
        "Phosphate (PO4) (ug atom/l)",
        "Total Phosphorus (ug atom/l)",
        "Silicate (SIO3) (ug atom/l)",
        "pH",
        "Sound Velocity (m/s)",
        "Station"};
    static final public String[] MRNINSTRLIST = {
        "ALL instruments types",
        "CTD",
        "XBT",
        "MBT",
        "STD",
        "BOTTLE"};

    static final public String[] THRMPARMSLIST = {
                        "Thermocline Depth",
                        "Mixed Layer depth",
                        "Thermocline Intensity"};

    /** Inventory options */
    static final public String[] INVOPTIONS =
        {"Extract Data",
         "Select on Dates",
         "Select on Platform Name",
         "Select on Client",
         "Select on Area",
         "Remove a Data Load",
         "Add an Instrument",
         "Extract Status & Download"
         };

    static final public int MAX1 = 30;
    static final public int MAX2 = 13;
    static final public int[][] BSIZE = {
        {60,30,20,15,12,10,6,5,4,3,0,0,0},
        {120,60,40,30,24,20,15,12,10,8,6,0,0},
        {180,90,60,45,36,30,20,18,15,12,9,0,0},
        {240,120,80,60,48,40,30,24,20,16,12,0,0},
        {300,150,100,75,60,50,30,25,20,15,0,0,0},
        {360,180,120,90,72,60,45,40,36,30,24,18,0},
        {420,210,140,105,84,70,60,42,35,30,28,21,0},
        {480,240,160,120,96,80,60,48,40,32,24,0,0},
        {540,270,180,135,108,90,60,54,45,36,27,0,0},
        {600,300,200,150,120,100,75,60,50,40,30,0,0},
        {660,330,220,165,132,110,66,60,55,44,33,0,0},
        {720,360,240,180,144,120,90,80,72,60,48,36,0},
        {780,390,260,195,156,130,78,65,60,52,39,0,0},
        {840,420,280,210,168,140,120,105,84,70,60,56,42},
        {900,450,300,225,180,150,100,90,75,60,45,0,0},
        {960,480,320,240,192,160,120,96,80,64,48,0,0},
        {1020,510,340,255,204,170,102,85,68,51,0,0,0},
        {1080,540,360,270,216,180,135,120,108,90,72,54,0},
        {1140,570,380,285,228,190,114,95,76,57,0,0,0},
        {1200,600,400,300,240,200,150,120,100,80,60,0,0},
        {1260,630,420,315,252,210,180,140,126,105,90,84,63},
        {1320,660,440,330,264,220,165,132,120,110,88,66,0},
        {1380,690,460,345,276,230,138,115,92,69,0,0,0},
        {1440,720,480,360,288,240,180,160,144,120,96,72,0},
        {1500,750,500,375,300,250,150,125,100,75,0,0,0},
        {1560,780,520,390,312,260,195,156,130,120,104,78,0},
        {1620,810,540,405,324,270,180,162,135,108,81,0,0},
        {1680,840,560,420,336,280,240,210,168,140,120,112,84},
        {1740,870,580,435,348,290,174,145,116,87,0,0,0},
        {1800,900,600,450,360,300,225,200,180,150,120,90,0}
        };

    static final public int[][] MINSQ = {
        {1,2,3,4,5,6,10,12,15,20,0,0,0},
        {1,2,3,4,5,6,8,10,12,15,20,0,0},
        {1,2,3,4,5,6,9,10,12,15,20,0,0},
        {1,2,3,4,5,6,8,10,12,15,20,0,0},
        {1,2,3,4,5,6,10,12,15,20,0,0,0},
        {1,2,3,4,5,6,8,9,10,12,15,20,0},
        {1,2,3,4,5,6,7,10,12,14,15,20,0},
        {1,2,3,4,5,6,8,10,12,15,20,0,0},
        {1,2,3,4,5,6,9,10,12,15,20,0,0},
        {1,2,3,4,5,6,8,10,12,15,20,0,0},
        {1,2,3,4,5,6,10,11,12,15,20,0,0},
        {1,2,3,4,5,6,8,9,10,12,15,20,0},
        {1,2,3,4,5,6,10,12,13,15,20,0,0},
        {1,2,3,4,5,6,7,8,10,12,14,15,20},
        {1,2,3,4,5,6,9,10,12,15,20,0,0},
        {1,2,3,4,5,6,8,10,12,15,20,0,0},
        {1,2,3,4,5,6,10,12,15,20,0,0,0},
        {1,2,3,4,5,6,8,9,10,12,15,20,0},
        {1,2,3,4,5,6,10,12,15,20,0,0,0},
        {1,2,3,4,5,6,8,10,12,15,20,0,0},
        {1,2,3,4,5,6,7,9,10,12,14,15,20},
        {1,2,3,4,5,6,8,10,11,12,15,20,0},
        {1,2,3,4,5,6,10,12,15,20,0,0,0},
        {1,2,3,4,5,6,8,9,10,12,15,20,0},
        {1,2,3,4,5,6,10,12,15,20,0,0,0},
        {1,2,3,4,5,6,8,10,12,13,15,20,0},
        {1,2,3,4,5,6,9,10,12,15,20,0,0},
        {1,2,3,4,5,6,7,8,10,12,14,15,20},
        {1,2,3,4,5,6,10,12,15,20,0,0,0},
        {1,2,3,4,5,6,8,9,10,12,15,20,0}
        };


    public SadConstants () {
    } // SadConstants


    /**
     * The code used to generate the above matrices
     * To run, call SadcoVOS with pscreen=matrix
     */
    public SadConstants (String args[]) {

        // set maxima
        int max1 = 30, // maximum number for tablesize
            max2 = 13, // maximum number of blocksizes
            max3 = 15; // except for 20, max for blocsize

        // generate  matrices for blocksizes and minutes_square
        int[][] bsize = new int[max1][max2];
        int[][] minsq = new int[max1][max2];

        for (int i = 0; i < max1; i ++) {
            int k = 0;
            double minutes = (i+1) * 60f;
            bsize[i][k] = (int) minutes;
            minsq[i][k++] = 1;
            for (int j = 2; j <= max3; j ++) {
                double aa = minutes / j;
                if (Math.ceil(aa) == aa) {
                    bsize[i][k] = (int) aa;
                    minsq[i][k++] = j;
                } // if
            } // for
            int j = 20;
            double aa = minutes / j;
            if (Math.ceil(aa) == aa) {
                bsize[i][k] = (int) aa;
                minsq[i][k] = j;
            } // if
        } // for

        // print the maxima
        System.out.println("    static final public int MAX1 = " + max1 + ";");
        System.out.println("    static final public int MAX2 = " + max2 + ";");

        // print the blocksize matrix
        String comma = ",";
        System.out.println ("    static final public int[][] BSIZE = {");
        for (int i = 0; i < max1; i++) {
            System.out.print("        {");
            for (int j = 0; j < max2-1; j++) {
                System.out.print(bsize[i][j] + ",");
            } // for int j
            if (i == (max1-1)) comma = "";
            System.out.println(bsize[i][max2-1] + "}" + comma);
        } // for i
        System.out.println("        };");

        //print the minutes_square matrix
        comma = ",";
        System.out.println ("    static final public int[][] MINSQ = {");
        for (int i = 0; i < max1; i++) {
            System.out.print("        {");
            for (int j = 0; j < max2-1; j++) {
                System.out.print(minsq[i][j] + ",");
            } // for int j
            if (i == (max1-1)) comma = "";
            System.out.println(minsq[i][max2-1] + "}" + comma);
        } // for i
        System.out.println("        };");

    } // SadConstants contructor

    /**
     * get the etopo2 depth
     * @param   station the relavant MrnStation record
     * @returns the depth of the point closest to the station position,
     *          returned as positive.  Heights are now negative
     */
    public float getEtopo2(MrnStation station) {

        if (dbg) System.out.println("\n" + new java.util.Date());

        float latitude = station.getLatitude();
        float longitude = station.getLongitude();
        float stndep = station.getStndep();
        float etopoLat[] = null;
        float etopoLon[] = null;
        int etopoDepth[] = null;
        float etopoDis[] = null;
        String selStr = "";
        String table = "";

        // using etopo and etopo2 tables
        float difference = 0.01667f;   // .03334/2
        String where = MrnEtopo.LATITUDE + " between " +
            (latitude - difference) + " and " +
            (latitude + difference) + " and " +
            MrnEtopo.LONGITUDE + " between " +
            (longitude - difference) + " and " +
            (longitude + difference);

        // find out which etopo table to use
        if ((latitude >= -10) && (longitude >= -30) && (longitude <= 70)) {

            MrnEtopo etop = new MrnEtopo();
            MrnEtopo etopo[] = etop.get(where);
            table = "etopo";                // for debugging
            selStr = etop.getSelStr();      // for debugging

            if (etopo.length > 0) {
                etopoLat = new float[etopo.length];
                etopoLon = new float[etopo.length];
                etopoDepth = new int[etopo.length];
                for (int i = 0; i < etopo.length; i++) {
                    etopoLat[i] = etopo[i].getLatitude();
                    etopoLon[i] = etopo[i].getLongitude();
                    etopoDepth[i] = -etopo[i].getHeight();
                } // for (int i = 1; i < etopo.length; i++)
            } // if (etopo.length > 0)

        } else {

            MrnEtopo2 etop = new MrnEtopo2();
            MrnEtopo2 etopo[] = etop.get(where);
            table = "etopo2";           // for debugging
            selStr = etop.getSelStr();  // for debugging

            if (etopo.length > 0) {
                etopoLat = new float[etopo.length];
                etopoLon = new float[etopo.length];
                etopoDepth = new int[etopo.length];
                for (int i = 0; i < etopo.length; i++) {
                    etopoLat[i] = etopo[i].getLatitude();
                    etopoLon[i] = etopo[i].getLongitude();
                    etopoDepth[i] = -etopo[i].getHeight();
                } // for (int i = 1; i < etopo.length; i++)
            } // if (etopo.length > 0)

        } // if ((latitude <= -10) &&

        int depth = -999999;
        //int depth2 = 0;
        if (etopoLat.length > 0) {
            depth = etopoDepth[0];
            //depth2 = depth;
            float depthDiff = Math.abs(stndep - depth);
            //float d1 = Math.abs(etopoLat[0] - latitude);
            //float d2 = Math.abs(etopoLon[0] - longitude);
            //float distance = (float)Math.sqrt(d1*d1 + d2*d2);
            if (dbg) System.out.println("stndep, etopo2, depthdiff, distance = " +
                ec.frm(stndep,8,2) + ec.frm(etopoDepth[0],8,2) +
                ec.frm(depthDiff,8,2)); // + ec.frm(distance,15,10));
            for (int i = 1; i < etopoLat.length; i++) {
                float temp = Math.abs(stndep - etopoDepth[i]);
                //d1 = Math.abs(etopoLat[i] - latitude);
                //d2 = Math.abs(etopoLon[i] - longitude);
                //float temp2 = (float)Math.sqrt(d1*d1 + d2*d2);
                if (dbg) System.out.println("stndep, etopo2, depthdiff, distance = " +
                    ec.frm(stndep,8,2) + ec.frm(etopoDepth[i],8,2) +
                    ec.frm(temp,8,2)); // + ec.frm(temp2,15,10));
                //if (dbg) System.out.println(depthDiff + " " + temp + " " + etopoDepth[i]);
                if (temp < depthDiff) {
                    depth = etopoDepth[i];
                    depthDiff = temp;
                } // if (temp < depthDiff)
                //if (temp2 < distance) {
                //    depth2 = etopoDepth[i];
                //    distance = temp2;
                //} // if (temp < depthDiff)
            } // for (int i = 1; i < etopoLat.length; i++)
        } // if (etopoLat.length > 0)

        if (dbg) System.out.println(table);
        if (dbg) System.out.println("selStr = " + selStr);
        if (dbg) System.out.println("etopo.length = " + etopoLat.length);
        if (dbg) System.out.println("depth = " + depth); // + ", depth2 = " + depth2);
        if (dbg) System.out.println("   latitude, longitude = " +
            ec.frm(latitude,13,5) + ", " + ec.frm(longitude,10,5) + ", " +
            ec.frm(stndep,10,2));
        if (dbg) for (int i = 0; i < etopoLat.length; i++)
            System.out.println("i, latitude, longitude = " + i +
            ", " + ec.frm(etopoLat[i],10,5) +
            ", " + ec.frm(etopoLon[i],10,5) +
            ", " + ec.frm(etopoDepth[i],10,2));

        return depth;

    } // getEtopo2

} // class SadConstants
