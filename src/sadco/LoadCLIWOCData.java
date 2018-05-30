package sadco;

import java.io.*;
import java.sql.*;
import java.util.*; // for Calendar
import oracle.html.*;

/**
 * Does the actual loading of the CLIWOC data.
 *
 * standalone program to load once-off data
 *
 * @author  001123 - SIT Group
 * @version
 * 20080704 - Ursula von St Ange - adapted from LoadVOSData2            <br>
 */
public class LoadCLIWOCData {

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    //boolean dbg = false;
    boolean dbg = true;
    boolean dbg2 = false;
    //boolean dbg2 = true;

    boolean csirCheck = false;
    //boolean csirCheck = true;

    String thisClass = this.getClass().getName();

    // arguments
    String vosLoadId  = "";
    String loadId     = "";
    String source     = "";
    String respPerson = "";
    String fileName   = "";
    boolean doLoad    = false;

    // file stuff
    String      pathName;
    RandomAccessFile lfile;
    RandomAccessFile ofile1;
    RandomAccessFile ofile2;
    RandomAccessFile ofile3;
    RandomAccessFile warningFile;
    RandomAccessFile dbgf;
    RandomAccessFile validRecs;
    String      line;

    // the data
    VosTable data;

    // data variables - sector0
    // group 0
    String      callsign = "";  // Ship's Call sign (c_callsn)
    // group 1
    String      day = "";       // Day of the month (GMT) (dag)
    String      hour = "";        // hour of observation to the nearest whole hour GMT (tyd)
    //String      wi = "";        // Wind indicator (wndc)
    // group 2
    //String    shiposi=99;     // Indicator for Ship's position
    String      latin = "";     // Latitude in hundredths of a degree  (slat)
    // group 3
    //String      q = "";         // Quadrant of the globe (qud)
    String      longin = "";    // Longitude in hundredths of a degree (slog)

    // data variables - sector1
    // group 4
    //String      r=4;          // Indicator for inclusion or ommission of precipitation data
    //String      operi;        // Indicator for station operation and for present and past  weather data.
    //String      clht = "";      // Height of the base of the lowest cloud in the sky (stts)
    //String      vv = "";        // Horizontal visibility at the surface
    // group 5
    //String      clfr = "";      // Fraction of the celestial dome covered by cloud (windg)
    String      wdir = "";      // True direction from which wind is blowing
    String      wsp = "";       // Wind Speed (in tenths of m/s)
    // group 6
    //String      ati=1;        // Indicator for the air temperature
    //String      ats = "";       // Sign of air temperature (tts)
    String      at = "";        // Air Temperature in tenths of a degree Celsius
    // group 7
    //String      dwi=2;        // Indicator for the dew-point temperature
    //String      dws = "";       // Sign of dew-point temperature (tds)
    //String      dw = "";        // dew-point Temperature in tenths of a degree Celsius
    // group 8
    //String      slpi=4;       // Indicator for sea-level pressure (c_press)
    String      slp = "";       // Pressure at sea-level in tenths of a millibar (= hectopascal),
                                //    omitting the thousands digit of the pressure value.
    // group 9
    //String      pci=5;        // Indicator for characteristic of pressure change (c_app)
    //String      pcc;          // Characteristic of pressure tendency during the three hours |
                                //    preceding hour of observation
    //String      pc;           // Amount of pressure change during the three hours preceding |
                                //    the hour of observation, in tenths of a millibar
    // group 10
    //String      ppwi=7;       // Indicator for past and present weather
    //String      ppwn = "";      // Present weather (c_ww)
    //String      ppwp1;        // Past weather code W1
    //String      ppwp2;        // Past weather code W2

    // group 11
    //String      cti=8;        // Indicator for cloud types (cloud)
    //String      ctf = "";       // Fraction of celestial dome covered by cloud(s) reported for
                                // CTL or, if no CTL cloud is present, for CTM.
    //String      ctl = "";       // Clouds: stratocumulus, stratus, cumulus, cumulonimbus
    //String      ctm = "";       // Clouds: altocumulus, altostratus, nimbostratus
    //String      cth = "";       // Clouds: cirrus, cirrocumulus and cirrostratus

    // data variables - sector2
    // group 12
    //String      scsi=222;     // Indicator for ship's course and speed (c_courc)
    //String      cscs;         // Course (true) made good during three hours preceding
    //                          // hour of observation
    //String      scss;         // Ship's avarage speed during thethree hours preceding
    //                          // hour  of observation.
    // group 13
    //String      sti=0;        // Indicator for sea-surface temperature
    //String      sts = "";       // Sign of sea-surface temperature (stemp 00 ?)
    String      st = "";        // Sea-surface temperature in tenths of a degree Celsius,
                                // its  sign being given by STS
    // group 14
    //String      wwi=2;        // Indicator for wind waves
    //String      wwp = "";       // Period of wind waves in seconds (c_wavp)
    //String      wwh = "";       // Height of wind waves in units of half metres (c_wavh)
    // group 15
    //String      swi=3;        // Swell direction indicator
    //String      sw1d = "";      // Swell 1 true direction, in tens of degrees, from which
                                // swell waves are coming (c_swld1)
    //String      sw2d;         // Swell 2 true direction, in tens of degrees, from which
    //                          // swell waves are coming (c_swld2)
    // group 16
    //String      sw1i=4;       // Swell 1 indicator
    //String      sw1p = "";      // Swell 1 period of swell waves in seconds (c_swsec1)
    //String      sw1h = "";      // Swell 1 height of swell waves in units of half metres (c_swht1)
    // group 17
    //String      sw2i=5;       // Swell 2 indicator
    //String      sw2p;         // Swell 2 period of swell waves in seconds
    //String      sw2h;         // Swell 2 height of swell waves in units of half metres
    // group 18
    //String      ici=6;        // Indicator or ice accretion
    //String      ica ;         // Type of ice accretion
    //String      ict ;         // Thickness of ice in centimetres
    //String      icr ;         // Rate of ice accretion

    // data variables - date
    String      year = "";      // (styd)
    String      month = "";     // (c_fapr)

    String      country = "";
    String      logBookId = "";

    // data variables - processLoadLine
    //String      latinD, latinM, latinS;
    //String      loninQ, loninD, loninM, loninS;
    //String      latinS = "";
    //String      loninS = "";
    String      dBase = "";
    //String      winspType = "";
    //float       atpres = 0;
    String      platform = "";
    //int         winsp = 0;
    //int         windir = 0;
    //int         wavht = 0;
    //int         swdir = 0;
    //int         swht = 0;

    String      dateTime = "";
    float       latitude = 0;
    float       longitude = 0;



    // error flags
    boolean     readErrorFlag = false;  // date, lat, lon, overland testing
    boolean     tempErrorFlag = false;  // data testing
    boolean     errorFlag     = false;  // all errors
    boolean     nonNumericFlag = false; // to catch the nonNumeric Fields
    boolean     isSADCO       = false;  // for overland checking

    // working variables
    int         recordCount = 0;
    int         loadCount   = 0;
    int         dayCount    = 0;
    int         fatalCount  = 0;
    int         mainDupCount= 0;
    int         archDupCount= 0;
    int         mainCount   = 0;
    int         archCount   = 0;
    int         skipCount   = 0;
    int         rejectCount = 0;
    int      wmoRejectCount = 0;
    int         sadcoCount  = 0;
    int       overlandCount = 0;
    int        PKerrorCount = 0;

    String      loadStat    = "";
    Timestamp   currTime;
    Timestamp   prevTime      = Timestamp.valueOf("1660-01-01 00:00:00.0");
    java.util.Date  dateStart = Timestamp.valueOf("1860-01-01 00:00:00.0");
    java.util.Date  dateEnd   = Timestamp.valueOf("1660-01-01 00:00:00.0");

    /*
      0 = Total records cnt
      1 = Dataset Loaded cnt
      2 = WMO cnt
      3 = CSIR cnt
      4 = Between CSIR & WMO cnt
    */

    int[] drybulbCount       = {0,0,0,0,0};
    int[] dewPointCount      = {0,0,0,0,0};
    int[] surfaceTempCount   = {0,0,0,0,0};

    /*
      0 = Total records cnt
      1 = Dataset Loaded cnt
      2 = WMO cnt
    */

    int[] atmosCount         = {0,0,0};
    int[] windDirectionCount = {0,0,0};
    int[] windSpeedCount     = {0,0,0};
    int[] wavePeriodCount    = {0,0,0};
    int[] waveHeightCount    = {0,0,0};
    int[] swellDirCount      = {0,0,0};
    int[] swellHeightCount   = {0,0,0};
    int[] swellPeriodCount   = {0,0,0};

    int         EVAL = -9999999;

    String[] shipNames = new String[1900];
    int noOfShipLogbooks = 0;

    public LoadCLIWOCData(String args[]) {

        if (dbg) System.out.println("<br>LoadCLIWOCData: before getParms");
        // get the argument values
        getArgParms(args);

        if (dbg) System.out.println("<br>LoadCLIWOCData After getParms");

        // get the correct path name
        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL + "vos/"; //sc.MORPHL
        } else {
            pathName = sc.LOCALDIR + "vos/";
            //pathName = "d:/myfiles/java/data/vos/";
        } // if host

        // delete the old the dummy file
        String dummyFile = pathName + fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        // get the shiplogbookID's
        readShipLogbooks();

        //load the data
        processInputFile();

        // update log_vos_loads table
        updateLogVOSLoads();

        // create the dummy file
        ec.createNewFile(dummyFile);

    } // constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        for (int i = 0; i < args.length; i++) {
            if (dbg) System.out.println("<br>" + args[i]);
        } // for i

        fileName   = ec.getArgument(args,sc.FILENAME);
        loadId     = ec.getArgument(args,sc.LOADID);
        vosLoadId  = ec.getArgument(args,sc.VOSLOADID);
        respPerson = ec.getArgument(args,sc.RESPPERSON);
        source     = ec.getArgument(args,sc.SOURCE);
        String temp = ec.getArgument(args, "pdoload");
        if ("".equals(temp)) { doLoad = true; }

        if (dbg) System.out.println("<br>");
        if (dbg) System.out.println("<br>getArgParms: vosLoadId = " + vosLoadId);
        if (dbg) System.out.println("<br>getArgParms: loadId = " + loadId);
        if (dbg) System.out.println("<br>getArgParms: fileName = " + fileName);
        if (dbg) System.out.println("<br>getArgParms: source = " + source);
        if (dbg) System.out.println("<br>getArgParms: respPerson = " + respPerson);
        if (dbg) System.out.println("<br>getArgParms: doLoad = " + doLoad);
    }   //  public void getArgParms()


    /**
     * read the ship log books to get the ship names
     */
    void readShipLogbooks() {
        RandomAccessFile shipFile =
            ec.openFile(pathName + "ShipLogbookID.txt", "r");
        noOfShipLogbooks = 0;
        while(!ec.eof(shipFile)) {
            shipNames[noOfShipLogbooks++] =
                ec.getNextValidLine(shipFile).substring(0,30).trim();
        } // while(!ec.eof(shipFile))
        ec.closeFile(shipFile);
    } // readShipLogbooks


    /**
     * process the input file
     */
    void processInputFile() {

        if (dbg) System.out.println("<br> processInputFile");

        // Create other output file names
        String baseName = pathName + fileName.substring(0,fileName.indexOf('.'));
        String of1 = baseName + ".rejected";
        String of2 = baseName + ".load.report";
        String of3 = baseName + ".daily";
        String of4 = baseName + ".debug";
        String of5 = baseName + ".warning";
        String of6 = baseName + ".valid";
        fileName = pathName + fileName;

        ec.deleteOldFile(of1);
        ec.deleteOldFile(of2);
        ec.deleteOldFile(of3);
        ec.deleteOldFile(of4);
        ec.deleteOldFile(of5);
        ec.deleteOldFile(of6);

        if (dbg) System.out.println("<br>processInputFile: Before File Open");
        // Open the data file
        lfile = ec.openFile(fileName, "r");
        ofile1 = ec.openFile(of1, "rw");
        ofile2 = ec.openFile(of2, "rw");
        ofile3 = ec.openFile(of3, "rw");
        dbgf   = ec.openFile(of4, "rw");
        warningFile = ec.openFile(of5,"rw");
        validRecs = ec.openFile(of6,"rw");

        // read the data
        while(!ec.eof(lfile)) {
            line = ec.getNextValidLine(lfile);
            recordCount++;

            if (line.length() >= 90) {
                if (dbg) System.out.println(
                    "\n<br>processInputFile: recordCount = " +
                    recordCount);
                if (dbg) System.out.println("<br>processInputFile: line = " +
                    line);
                readErrorFlag = false;

                readData();

                // check primary key - callsign, datetime, lat, lon
                processPK();

                // if OK, check for over land
                if (!readErrorFlag) {
                    if (dbg2) System.out.println(
                        "<br>processInputFile: readErrorFlag check1 "+
                        readErrorFlag);

                    if (isSADCO) testOverLand();

                    // if OK, check if record exists
                    if (!readErrorFlag) {
                        if (dbg2) System.out.println(
                            "<br>processInputFile: readErrorFlag check2 "+
                            readErrorFlag);

                        if (dbg) System.out.println("<br>processInputFile: dBase "+dBase);
                        if ("main".equals(dBase)) {
                            recordExists(new VosMain2());
                        } else {
                            recordExists(new VosArch2());
                        } // if ("main".equals(dBase))
                        if (dbg) System.out.println(
                            "<br>processInputFile: readErrorFlag after recordExists " +
                            readErrorFlag);

                        // if OK, read and process rest of record
                        if (!readErrorFlag) {

                            loadCount++;
                            loadStat = "loaded";
                            dayCount++;

                            if ("main".equals(dBase)) {
                                processRest(new VosMain2());
                                mainCount++;
                            } else {
                                processRest(new VosArch2());
                                archCount++;
                            } // if ("main".equals(dBase))

                        } // if (!readErrorFlag)
                    } // if (!readErrorFlag)

                } // if (!readErrorFlag)

                // increase fatalCount if any error found
                if (readErrorFlag) {
                    fatalCount++;
                } // if (readErrorFlag)

            } else {
                skipCount++;
            }// if (line.length() >= 125)

        } // while(!ec.eof(lfile))

        dailyStats();
        closeStats(ofile2);
        closeStats(ofile3);

        ec.closeFile(lfile);
        ec.closeFile(ofile1);
        ec.closeFile(ofile2);
        ec.closeFile(ofile3);
        ec.closeFile(dbgf);
        ec.closeFile(warningFile);
        ec.closeFile(validRecs);

    } // void processInputFile() {


    /**
     * read all the data from the line
     */
    void readData() {

        // YEAR & MONTH
        year    = line.substring(0,4);
        month   = line.substring(4,6).replace(' ','0');
        day     = line.substring(6,8).replace(' ','0');
        hour    = line.substring(8,10).replace(' ','0');

        if (dbg) System.out.print("<br>readData: year = " + year);
        if (dbg) System.out.print(", month = " + month);
        if (dbg) System.out.print(", day = " + day);
        if (dbg) System.out.print(", hour = " + hour);
        if (dbg) System.out.println();

        latin     = line.substring(12,17);
        longin    = line.substring(17,23);

        if (dbg) System.out.print("<br>readData: latin = " + latin);
        if (dbg) System.out.print(", longin = " + longin);
        if (dbg) System.out.println();

        country = line.substring(43,45);
        logBookId = line.substring(34,43).trim();
        callsign = getShip(logBookId);

        if (dbg) System.out.print("<br>readData: country  = " + country);
        if (dbg) System.out.print(", logBookId = " + logBookId);
        if (dbg) System.out.print(", callsign = " + callsign);
        if (dbg) System.out.println();

        wdir    = line.substring(46,49);  // wind speed
        wsp     = line.substring(50,53);  // wind direction
        slp     = line.substring(59,64);  // air pressure
        at      = line.substring(69,73);  // air temperature
        st     = line.substring(85,89);   // sea surface temperature



        if (dbg) System.out.print("<br>wdir = " + wdir);
        if (dbg) System.out.print(", wsp = " + wsp);
        if (dbg) System.out.print(", slp = " + slp);
        if (dbg) System.out.print(", at = " + at);
        if (dbg) System.out.print(", st = " + st  );
        if (dbg) System.out.println();


    } // readData


    /**
     * process the inputted line for the primary key
     */
    void processPK() {

        int testno;
        //float testno2;

        // Check that the year and month are correct
        testReadErrorValue(year, 1660, 1860, "YEAR   ", "Invalid year");
        testReadErrorValue(month, 1, 12, "MONTH  ", "Invalid month");

        // DAY - check if valid
        if (!readErrorFlag) {
            GregorianCalendar calDate = new GregorianCalendar();
            calDate.setTime(
                Timestamp.valueOf(year + "-" + month + "-" + "01 00:00:00.0"));
            // get last day
            int lastDay = calDate.getMaximum(Calendar.DATE);
            testReadErrorValue(day, 1, lastDay, "DAY    ", "Invalid day");
        } // if (!readErrorFlag)

        // HOURS - check valid
        if ("00".equals(hour)) hour = "12";
        testno = testReadErrorValue(hour, 0, 23, "HOURS  ", "Invalid hour");
        hour = hour + ":00";

        dateTime = year + "-" + month + "-" + day + " " + hour;

        // LATITUDE - check if valid - Marten wants us to include all data
        int min = -9000;  // south
        int max = 9000;
        testno = testReadErrorValue(latin, min, max, "LATIN  ",
            "Invalid latitude (lat*100)");
        latitude = testno;
        if (testno != EVAL) latitude = -1 * latitude / 100f;
        latitude = new Float(ec.frm(latitude,7,2).trim()).floatValue();

        // LONGITUDE - Check if valid
        // database officially only from 30W till 70E
        // longitude 0 - 360 from Greenwich, so test twice
        // only test if there was a longitude
        min = 0;
        max = 36000;
        testno = testReadErrorValue(longin, min, max, "LONGIN ",
            "Invalid longitude (lon*100)");
        longitude = testno;

        if (testno != EVAL) {
            longitude /= 100f;
            if (longitude >= 180f) {
                longitude  -= 360f;
            } // if (longitude >= 180f)
        } // if (testno != EVAL)
        longitude = new Float(ec.frm(longitude,7,2).trim()).floatValue();

        // decide whether data to be loaded in coastal or oceanic database
        // all data before 1960 goes into main or arch database
        // QUADRANT - decide whether data to be loaded in coastal
        //   or oceanic database.
        if ((longitude >= 0f) && (longitude < 50f)) {
            dBase = "main";
        } else {
            dBase = "arch";
        } // if ((longitude >= 0f) && (longitude < 50f))
/*
        if ((intQ == 1) || (intQ == 3)) { // east
            if (testno2 < 500f) {                                       // ub01
                dBase = "main";
            } else {
                dBase = "arch";
            } // if (testno < 50)
        } else { // west
            dBase = "arch";
        } // if ((intQ == 1) || (intQ == 3))
*/
//        dBase = "arch";

        //check whether in SADCO area
        isSADCO = false;
        if (latitude > -10) {
            if ((longitude > -30) && (longitude < 70)) {
                isSADCO = true;
                sadcoCount++;
            } // if ((longitude > -30) && (longitude < 70))
        } // if (latitude > -10)

        if (dbg) System.out.println(
            "<br>ProcessPK: dateTime: " + dateTime +
            ", lat: " + ec.frm((latitude),7,2) +
            ", lon: " + ec.frm((longitude),7,2) +
            ", dBase: " + dBase + ", isSADCO: " + isSADCO +
            ", readErrorFlag = " + readErrorFlag);

        if (readErrorFlag) PKerrorCount++;

    } // processPK

    /**
     * test whether the current record exists
     * @param data VosMain or VosArch data record
     */
    void recordExists(VosTable data) {

        // set PK values
        data.setDateTime(dateTime);
        data.setLatitude(latitude);
        data.setLongitude(longitude);
        data.setCallsign(callsign.trim());

        if (dbg) System.out.println("<br>recordExists: data "+data);
        if (dbg) System.out.println("<br>recordExists: dateTime = " + data.getDateTime());
        if (dbg) System.out.println("<br>recordExists: latitude = " + data.getLatitude());
        if (dbg) System.out.println("<br>recordExists: longitude = " + data.getLongitude());
        if (dbg) System.out.println("<br>recordExists: callsign = " + data.getCallsign());

        //if (dbg) System.out.println("<br>recordExists: now1 = " + new java.util.Date());
        // check whether the record exists
        int reccnt = 0;
        String vosLoadId2 = "";

        //reccnt = data.getRecCnt("");
        Vector result = data.getV();
        reccnt = result.size();

        if (dbg) System.out.println("<br>recordExists: record "+reccnt+" PK "+data);
        if (dbg) System.out.println("<br>selStr = " + data.getSelStr());
        //ec.writeFileLine(dbgf,"record "+reccnt+" PK "+data);// data.getMessages());
        //ec.writeFileLine(dbgf, data.getMessages());

        // loop through all returned records
        common2.DbAccessC db = data.db;

        if (dbg) System.out.println("<br>recordExists: before result.size() for ");
        for (int i = 0; i < result.size(); i++) {
            if (dbg) System.out.println("<br>recordExists: before vector row ");
            Vector row = (Vector) result.elementAt(i);
            if (dbg) System.out.println("<br>recordExists: after vector ");
            if (dbg) System.out.println("<br>recordExists: "+row);

            vosLoadId2 =
                //(Float) row.elementAt(db.getColNumber(VosTable.LOAD_ID));
                (String)row.elementAt(db.getColNumber(VosTable.LOAD_ID));

            if (dbg) System.out.println("<br>recordExists: vosLoadId2 "+vosLoadId2);
        } // for i

        if (dbg) System.out.println("<br>" + thisClass +
            ".recordExists: reccnt = " + reccnt);

        //if (dbg) System.out.println("<br>recordExists: now2 = " + new java.util.Date());

        if (reccnt > 0) {

            // set readErrorFlag true
            setReadErrorFlags("DUP REC", String.valueOf(reccnt), dBase +
                " - duplicate record");

            if (dbg2) System.out.println("recordExists: db trace1 dBase "+dBase);

            if ("main".equals(dBase)) {
                mainDupCount++;
                if (dbg2) System.out.println("recordExists: db trace2 dBase "+dBase);
            } else {
                archDupCount++;
                if (dbg2) System.out.println("recordExists: db trace3 dBase "+dBase);
            } // if ("main".equals(dBase))

            ec.setFrmFiller("0");
            loadStat = "already loaded (" +
                ec.frm(Integer.parseInt(vosLoadId2),4) + ")";
            ec.setFrmFiller(" ");
            recordStats(data);

            if (dbg) System.out.println("<br>recordExists: data "+data);
        } // if (reccnt > 0)

    } // recordExists

    void processRest(VosTable data) {

        // Declairing object of VosReject Table with boolean variable
        // to determine whether to write the rejected records.
        VosReject reject = new VosReject();
        boolean rejected = false;

        int testno, testno2;
        float ftestno, ftestno2;
        int itestno;
        Float ftestno3;

        if (dbg2) System.out.println("processRest: db trace4 ");
        // set PK values again
        data.setDateTime(dateTime);
        data.setLatitude(latitude);
        data.setLongitude(longitude);
        data.setCallsign(callsign.trim());
        String position = latitude + "S/" + longitude + "W";

        // set PK values for rejected field with invalid range checks
        reject.setDateTime(dateTime);
        reject.setLatitude(latitude);
        reject.setLongitude(longitude);
        reject.setCallsign(callsign.trim().toUpperCase());

        if (dbg2) System.out.println("processRest: db trace5 ");

        int longIndex = 0;
        int latIndex  = 0;

        if (longitude >  25) { longIndex = 1; }
        //latIndex = (getValue(latinS + latin) + 100) / 50;
        latIndex = ((int)latitude + 10) / 5;

        if (dbg) System.out.println("<br>processRest: longin = " + longin);
        if (dbg) System.out.println("<br>processRest: latin = " + latin);
        if (dbg) System.out.println("<br>processRest: LatIndex = " + latIndex +
            ", LongIndex = " + longIndex);
        if (dbg2) System.out.println("processRest: db trace7 ");


        //* WIND DIRECTION - Check if valid
        // Testing for numeric/non-numeric values.
        testTempErrorValue(wdir, 0, 999, "WDIR   ", "Non-numeric wind direction");

        if (!tempErrorFlag) {

            windDirectionCount[0]++;

            int[][][] wndDir = {{{0,360}}};
            testno = testTempErrorValue2(wdir, wndDir[0][0],
                    "WDIR   ", "Invalid wind direction ("+position+")");

            if (!tempErrorFlag) {
                if (dbg2) System.out.println("processRest: db trace6 ");
                data.setWindDirection(nullZero0(testno));
                windDirectionCount[1]++;
            } else {
                windDirectionCount[2]++;
            } //if (!tempErrorFlag)

        } //if (!tempErrorFlag)

        if (dbg2) System.out.println("processRest: **db trace7 ");


        //* WIND SPEED - Check if valid
        // Testing for numeric/non-numeric values.
        ftestno = testTempErrorValue(wsp, 0, 999, "WSP    ", "Non-numeric wind speed");
        if (dbg2) System.out.println("processRest: *3db trace7");

        if (!tempErrorFlag) {

            windSpeedCount[0]++;
            ftestno /= 10f;

            int[][][] wndSpeed = {{{0,40}}};
            testTempErrorValue3(ftestno, wndSpeed[0][0], //wndSpeed[longIndex][latIndex],
                    "WSP    ", "Invalid wind speed ("+position+")");

            if (!tempErrorFlag) {
                data.setWindSpeed(nullZero1(ftestno));
                windSpeedCount[1]++;
            } else {
                windSpeedCount[2]++;
            }// // if (!tempErrorFlag) {

        } // if (!tempErrorFlag) {

        if (dbg2) System.out.println("processRest: *3db trace7 ");


        //* AIR TEMPERATURE (drybulb) - Check if valid
        // Testing for numeric/non-numeric values.
        ftestno = testTempErrorValue(at,-999,999,"AT     ",
            "Non-numeric air temperature ("+position+")");

        if (!tempErrorFlag ) {  // numeric value

            drybulbCount[0]++;
            ftestno /= 10f;

            int[][][] drybulb = {
                { {19,38},{19,37},{16,38},{12,40},{7 ,33},{7 ,33},{7 ,33},{7 ,33},{2 ,30}, //west
                  {0 ,28},{-10,27},{-15,25},{-15,17},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} },
                { {17,37},{19,35},{19,35},{19,37},{19,37},{14,36},{13,37},{9 ,35},{5 ,33},{2 ,30}, //east
                  {-10, 25},{-15, 25},{-15, 17},{-19, 17},{-25, 15},{-25, 15},{-25, 15},{-25, 15} }};
            int[][][] drybulb2 = {{{-25,40}}};

            if (isSADCO) {
                testTempErrorValue3(ftestno, drybulb[longIndex][latIndex],
                    "AT     ", "Invalid Air Temperature  CSIR Limits ("+position+")");
            } else {
                testTempErrorValue3(ftestno, drybulb2[0][0],
                    "AT     ", "Invalid Air Temperature WMO Limits ("+position+")");
            } // if (isSADCO)

            if (!tempErrorFlag) {

                data.setDrybulb(nullZero1(ftestno));
                drybulbCount[1]++;

                if (csirCheck) System.out.println(" ");
                if (csirCheck) System.out.println(" Drybulb");
                if (csirCheck) System.out.println(" --------");
                if (csirCheck) System.out.println(" Latitude  Index : "+longIndex);
                if (csirCheck) System.out.println(" Longitude Index : "+latIndex);
                if (csirCheck) System.out.println(" drybulb range   : "+drybulb[0][0][0]);
                if (csirCheck) System.out.println(" drybulb range   : "+drybulb[0][0][1]);
                if (csirCheck) System.out.println(" testno "+ ftestno);
                if (csirCheck) System.out.println(" ");

            } else { // failed CSIR

                if (isSADCO) {
                    drybulbCount[3]++;
                    testTempErrorValue3(ftestno, drybulb2[0][0],
                        "AT     ", "Invalid Air Temperature WMO Limits ("+position+")");
                } // if (isSadco)

                if (!tempErrorFlag ) { // between CSIR and WMO
                    reject.setDrybulb(nullZero1(ftestno));
                    rejected = true;
                    drybulbCount[4]++;
                } else { // fail both
                    drybulbCount[2]++;
                }//if (!tempErrorFlag )

            } //if (!tempErrorFlag )

        } //if (!tempErrorFlag )


        //* SEA-LEVEL PRESSURE - Check if valid
        // Testing for numeric/non-numeric values.
        ftestno = testTempErrorValue(slp,-99999,99999,"SLP    ",
            "Non-numeric sea-level pressure ("+position+")");

        if (!tempErrorFlag) {

            atmosCount[0]++;
            ftestno /= 10f;

            double[][][] atmosPress = {{{930,1050}}};

            testTempErrorValue4(ftestno, atmosPress[0][0],
                "SLP    ", "Invalid sea-level pressure ("+position+")");

            if(!tempErrorFlag) { // not failed
                data.setAtmosphericPressure(ftestno);
                atmosCount[1]++;
            } else {
                atmosCount[2]++;
            } // if(!tempErrorFlag)

        } // if (!tempErrorFlag)


        //* SEA TEMPERATURE - check if valid
        // do test for non-numeric range check is a bonus here
        ftestno = testTempErrorValue(st, 0, 999, "SST    ",
            "Non-numeric sea temperature ("+position+")");

        if (!tempErrorFlag) {

            surfaceTempCount[0]++;
            ftestno /= 10f;

            // made change as in Air temp
            // SEA TEMPERATURE - Check if valid
            int[][][] seaTemp = {
                { {18,37},{19,37},{16,37},{12,36},{8 ,33},{7 ,33},{7 ,33},{7 ,33},{6 ,33}, //west
                  {5 ,33},{-2,28},{-2,20},{-2,18},{-2,16},{-2,14},{-2, 8},{-2, 8},{-2, 8} },
                { {15,37},{18,36},{20,36},{19,37},{19,37},{19,37},{15,36},{13,35},{6 ,34}, //east
                  {7 ,30},{-2,28},{-2,20},{-2,18},{-2,16},{-2,14},{-2, 8},{-2, 8},{-2, 8} }};
            int[][][] seaTemp2 = {{{-2,37}}};

            if (isSADCO) {
                testTempErrorValue3(ftestno, seaTemp[longIndex][latIndex],
                    "SST   ", "Invalid Sea Temperature CSIR Limits ("+position+")");
            } else {
                testTempErrorValue3(ftestno, seaTemp2[0][0],
                    "SST   ", "Invalid Sea Temperature CSIR Limits ("+position+")");
            } // if (isSADCO)

            if (!tempErrorFlag) {

                data.setSurfaceTemperature(ftestno);
                surfaceTempCount[1]++;

                if (csirCheck) System.out.println(" ");
                if (csirCheck) System.out.println(" seaTemp");
                if (csirCheck) System.out.println(" -------");
                if (csirCheck) System.out.println(" Latitude  Index : "+longIndex);
                if (csirCheck) System.out.println(" Longitude Index : "+latIndex);
                if (csirCheck) System.out.println(" seaTemp range   : "+seaTemp[0][0][0]);
                if (csirCheck) System.out.println(" seaTemp range   : "+seaTemp[0][0][1]);
                if (csirCheck) System.out.println(" ftestno   : "+ftestno);
                if (csirCheck) System.out.println(" ");

            } else {

                if (isSADCO) {
                    surfaceTempCount[3]++;
                    testTempErrorValue3(ftestno, seaTemp2[0][0],
                        "SST   ", "Invalid Sea Temperature WMO Limits ("+position+")");
                } // if (!isSADCO)

                if (!tempErrorFlag) { // between CSIR & WMO
                    rejected = true;
                    reject.setSurfaceTemperature(ftestno);
                    surfaceTempCount[4]++;
                } else { // fail both CSIR & WMO
                    surfaceTempCount[2]++;
                }/// if (!tempErrorFlag)

            } //if (tempErrorFlag) {

        } // if (!tempErrorFlag) {


        // *PLATFORM TYPE - generate code
        platform = "1";
        data.setPlatform(platform);

        // write the data
        data.setLoadId(vosLoadId);
        data.setCountry(country);
        data.setDataId("CL");   //CLIWOC
        data.setQualityControl("1");
        data.setSource1("2");
        if (dbg) System.out.println("<br>processRest: data loaded = " +
            data.toString());

        if (doLoad) {

            System.out.println(data);
            data.put();
            if (csirCheck) ec.writeFileLine(validRecs,data.toString());
            if (dbg) System.out.println(data.getInsStr());

            int nr = data.getNumRecords();
            if (nr == 0) {
                ec.writeFileLine(dbgf, "nr inserted = " + nr + " " + dBase +
                    ec.frm(recordCount,6));
                ec.writeFileLine(dbgf, data.getMessages());
                ec.writeFileLine(dbgf, data.getInsStr());
                ec.writeFileLine(dbgf, data.toString());
            } // if (nr == 0)
        } // if (doLoad)

        // Write records to VosReject if there where invalid range checks.
        if (rejected) {

            reject.setLoadId(vosLoadId);
            reject.setDataId("CL");   //CLIWOC
            reject.setQualityControl("9");
            reject.setSource1("2");
            reject.setPlatform(platform);
            if (dbg) System.out.println("<br>processRest: reject loaded " +
                reject.toString());

            if (doLoad) {
                reject.put();
            } //if (doLoad) {

            int nr = reject.getNumRecords();
            if (nr == 0) {
                ec.writeFileLine(dbgf, "nr inserted = " + nr + " reject " +
                    ec.frm(recordCount,6));
                ec.writeFileLine(dbgf, reject.getMessages());
                ec.writeFileLine(dbgf, reject.getInsStr());
                ec.writeFileLine(dbgf, reject.getUpdStr());
                ec.writeFileLine(dbgf, reject.toString());
            } // if (nr == 0)
            rejectCount++;
            if (dbg) System.out.println("<br>processRest: rejectCount equals :"+rejectCount);

        } //if (rejected) {

        if (dbg) System.out.println("<br>processRest: *** WRITE TO DB *** " + dBase);

        // check for change of day
        currTime = ec.setDateTime(year + "-01-01");
        if (!currTime.equals(prevTime) &&  (loadCount > 1)) {
            dailyStats();
        } // if (!data.getDateTime().equals(prevTime) &&  (loadCount > 1))
        prevTime = currTime;

        // write info message
        recordStats(data);

        // check for date min and max
        if (data.getDateTime().before(dateStart)) dateStart = data.getDateTime();
        if (data.getDateTime().after(dateEnd))    dateEnd = data.getDateTime();

    } // processRest


    /**
     * Test for overland data
     */
    void testOverLand() {

        // figure out quadrant
        int intQ = 0;
        if (longitude > 0) {
            intQ = (latitude > 0 ? 3 : 1);
        } else {
            intQ = (latitude > 0 ? 5 : 7);
        } // if (longitude > 0)

        //int intQ = getValue(q);
        int lat = (int)latitude * 10;
        if (lat < 0) lat *= -1;
        int lon = (int)longitude * 10;
        if (lon < 0) lon *= -1;

        boolean olErrorFlag = false;     // overland testing
        if (intQ == 7) {
            // test for overland data in the NW quadrant
            //------------------------------------------
            int africa7[][] =
                {{45,  47, 72,  77}, {47,  50, 67,  85}, {50,  52, 60,  90},
                 {50,  52, 17,  27}, {52,  55,  7,  90}, {55,  57,  5,  95},
                 {57,  60,  0,  97}, {60,  62,  0, 100}, {62,  65,  0, 105},
                 {65,  67,  0, 107}, {67,  70,  0, 110}, {70,  72,  0, 115},
                 {72,  75,  0, 120}, {75,  77,  0, 120}, {77,  80,  0, 125},
                 {80,  82,  0, 127}, {82,  85,  0, 130}, {85,  87,  0, 127},
                 {87,  90,  0, 130}, {90,  92,  0, 130}, {92,  95,  0, 132},
                 {95,  97,  0, 135}, {97, 100,  0, 135}
            };  // float africa7[][]

            // Africa
            for (int i = 0; i < africa7.length; i++) {
                if (checkLatLong2(lat, lon, africa7[i])) olErrorFlag = true;
            } // for (int i = 0; i < africa7.length; i++)
            if (olErrorFlag) {
                setReadErrorFlags ("Lat/Lon", latin + "/" + longin, "Over Africa (NW)");
            } // if (olErrorFlag == 3)

        } else if (intQ == 1) {
            // test for overland data in the NE quadrant
            //------------------------------------------
            int africa1[][] =
                {{0,   2, 100, 427}, { 2,   5,  97, 430}, { 5,   7,  97, 432},
                 {7,  10,  97, 435}, {10,  12,  97, 437}, {12,  15,  97, 440},
                 {15,  17,  97, 445},{17,  20, 100, 447}, {20,  22, 100, 452},
                 {22,  25, 100, 457},{25,  27, 100, 460}, {27,  30, 100, 462},
                 {30,  32, 100, 465}, {32,  35, 100, 467}, {35,  37,  97, 470},
                 {37,  40, 100, 470}, {40,  42, 100, 475}, {42,  45,  90, 475},
                 {45,  47,  57,  65}, {45,  47,  90, 477}, {47,  50,  57,  80},
                 {47,  50,  87, 480}, {50,  52,  57, 482}, {52,  55,  57, 482},
                 {55,  57,  55, 485}, {57,  60,  57, 487}, {60,  62,   0,  10},
                 {60,  62,  50, 487}, {62,  65,   0,  15}, {62,  65,  47, 490},
                 {65,  65,   0,  32}, {65,  67,  40, 490}, {67,  70,   0, 492},
                 {70,  72,   0, 492}, {72,  75,   0, 495}, {75,  77,   0, 495},
                 {77,  80,   0, 497}, {80,  82,   0, 497}, {82,  85,   0, 500},
                 {85,  87,   0, 502}, {87,  90,   0, 502}, {90,  92,   0, 505},
                 {92,  95,   0, 505}, {95,  97,   0, 505}, {97, 100,   0, 507},

            };  // float africa7[][]

            // Africa
            for (int i = 0; i < africa1.length; i++) {
                if (checkLatLong2(lat, lon, africa1[i])) olErrorFlag = true;
            } // for (int i = 0; i < africa1.length; i

            if (olErrorFlag) {
                setReadErrorFlags ("Lat/Lon", latin + "/" + longin, "Over Africa (NE)");
            } // if (olErrorFlag == 4)

        } else if (intQ == 3) {
            // test for overland data in the SE quadrant
            //------------------------------------------
            int africa3[][] =
               {{ 0,   2,  95, 425}, { 2,   5,  95, 420}, { 5,   7,  92, 422},
                { 7,  10,  92, 420}, {10,  12,  92, 417}, {12,  15,  92, 415},
                {15,  17,  95, 415}, {17,  20,  95, 410}, {20,  22,  97, 407},
                {22,  25, 100, 402}, {25,  27, 102, 400}, {27,  30, 105, 400},
                {30,  32, 107, 400}, {32,  35, 110, 397}, {35,  37, 112, 397},
                {37,  40, 115, 397}, {40,  42, 117, 395}, {42,  45, 120, 395},
                {45,  47, 120, 392}, {47,  50, 122, 390}, {50,  52, 125, 390},
                {52,  55, 125, 390}, {55,  57, 125, 387}, {57,  60, 127, 387},
                {60,  62, 127, 387}, {62,  62, 127, 387}, {65,  65, 130, 390},
                {67,  70, 132, 392}, {70,  72, 132, 392}, {72,  75, 132, 392},
                {75,  77, 132, 392}, {77,  80, 135, 392}, {80,  82, 135, 392},
                {82,  85, 135, 392}, {85,  87, 135, 392}, {87,  90, 135, 392},
                {90,  92, 135, 395}, {92,  95, 135, 395}, {95,  97, 135, 395},
                {97, 100, 135, 395}, {100, 102, 137, 397},{102, 105, 140, 400},
                {105, 107, 140, 405},{107, 110, 140, 405},{110, 112, 140, 405},
                {112, 115, 140, 405},{115, 117, 140, 405},{117, 120, 140, 405},
                {120, 122, 140, 405},{122, 125, 140, 405},{125, 127, 137, 405},
                {127, 130, 132, 402},{130, 132, 132, 405},{132, 135, 130, 405},
                {135, 137, 127, 405},{137, 140, 122, 405},{140, 142, 127, 405},
                {142, 145, 125, 405},{145, 147, 125, 405},{147, 150, 125, 405},
                {150, 152, 125, 405},{152, 155, 122, 405},{155, 157, 122, 402},
                {157, 160, 120, 400},{160, 162, 120, 395},{162, 165, 120, 395},
                {165, 167, 120, 392},{167, 170, 120, 390},{170, 172, 120, 380},
                {172, 175, 120, 375},{175, 177, 120, 370},{177, 180, 120, 367},
                {180, 182, 122, 365},{182, 185, 122, 365},{185, 187, 125, 360},
                {187, 190, 127, 357},{190, 192, 127, 355},{192, 195, 130, 352},
                {195, 197, 130, 350},{197, 200, 132, 347},{200, 202, 135, 345},
                {202, 205, 135, 345},{205, 207, 137, 345},{207, 210, 137, 350},
                {210, 212, 140, 350},{212, 215, 140, 350},{215, 217, 142, 352},
                {217, 220, 145, 352},{220, 222, 145, 352},{222, 225, 147, 355},
                {225, 227, 147, 355},{227, 230, 147, 355},{230, 232, 147, 355},
                {232, 235, 147, 352},{235, 237, 147, 352},{237, 240, 147, 352},
                {240, 242, 147, 352},{242, 245, 147, 352},{245, 247, 150, 347},
                {247, 250, 150, 342},{250, 252, 150, 332},{252, 255, 150, 327},
                {255, 257, 150, 327},{257, 260, 152, 325},{260, 262, 152, 325},
                {262, 265, 152, 327},{265, 267, 152, 327},{267, 270, 155, 327},
                {270, 272, 155, 327},{272, 275, 155, 325},{275, 277, 157, 325},
                {277, 280, 160, 325},{280, 282, 162, 325},{282, 285, 167, 322},
                {285, 287, 167, 320},{287, 290, 170, 315},{290, 292, 170, 312},
                {292, 295, 172, 312},{295, 297, 172, 310},{297, 300, 172, 307},
                {300, 302, 175, 307},{302, 305, 175, 305},{305, 307, 177, 302},
                {307, 310, 177, 302},{310, 312, 180, 300},{312, 315, 182, 295},
                {315, 317, 182, 292},{317, 320, 185, 290},{320, 322, 185, 287},
                {322, 325, 185, 285},{325, 327, 185, 282},{327, 330, 182, 280},
                {330, 332, 182, 275},{332, 335, 185, 270},{335, 337, 185, 257},
                {337, 337, 187, 232},{337, 340, 240, 250},{340, 342, 190, 217},
                {342, 345, 195, 202},
            };

            // Africa
            for (int i = 0; i < africa3.length; i++) {
                if (checkLatLong2(lat, lon, africa3[i])) olErrorFlag = true;
            } // for (int i = 0; i < africa3.length; i

            if (olErrorFlag) {
                setReadErrorFlags ("Lat/Lon", latin + "/" + longin, "Over Africa (S)");
            } // if (olErrorFlag == 4)
            int madagascar[][] =
               {{122, 125, 492, 495},{125, 127, 492, 495},{127, 130, 492, 497},
                {130, 132, 492, 497},{132, 135, 490, 500},{135, 137, 485, 500},
                {137, 140, 485, 500},{140, 142, 482, 500},{142, 145, 482, 502},
                {145, 147, 482, 502},{147, 150, 477, 502},{150, 152, 472, 502},
                {152, 155, 475, 497},{155, 157, 475, 497},{157, 160, 467, 497},
                {160, 162, 457, 462},{160, 162, 467, 497},{162, 165, 447, 497},
                {165, 167, 445, 497},{167, 170, 445, 495},{170, 172, 445, 495},
                {172, 175, 442, 495},{175, 177, 442, 495},{177, 180, 442, 492},
                {180, 182, 442, 492},{182, 185, 442, 492},{185, 187, 445, 490},
                {187, 190, 445, 490},{190, 192, 445, 490},{192, 195, 447, 487},
                {195, 197, 447, 487},{197, 200, 447, 487},{200, 202, 445, 485},
                {202, 205, 445, 485},{205, 207, 442, 482},{207, 210, 440, 482},
                {210, 212, 440, 482},{212, 215, 440, 482},{215, 217, 435, 480},
                {217, 220, 435, 480},{220, 222, 435, 477},{222, 225, 435, 477},
                {225, 227, 435, 477},{227, 230, 435, 477},{230, 232, 437, 475},
                {232, 235, 437, 475},{235, 237, 437, 475},{237, 240, 437, 475},
                {240, 242, 437, 472},{242, 245, 440, 472},{245, 247, 440, 470},
                {247, 250, 442, 467},{250, 252, 447, 457},{252, 255, 450, 455},
            };

            // Madagascar
            for (int i = 0; i < madagascar.length; i++) {
                if (checkLatLong2(lat, lon, madagascar[i])) olErrorFlag = true;
            } // for (int i = 0; i < africa3.length; i

            olErrorFlag = false;

            if (olErrorFlag) {
                setReadErrorFlags("Lat/Lon", latin + "/" + longin, "Over Madagascar");
            } // if (olErrorFlag == 4)

        } // if (intQ == 7)

        if (dbg) System.out.println("<br>testOverLand: readErrorFlag = " + readErrorFlag);
        if (readErrorFlag) overlandCount++;

     } // testOverLand

    /**
     * write record stats
     */
    void recordStats(VosTable data) {
        //if (dbg) System.out.println("<br>recordStats: data "+data);
        ec.writeFileLine(ofile2, ec.frm(recordCount,6) + " " +
            data.getDateTime("").substring(0,16) + " " +
            ec.frm(data.getLatitude(),9,5) +
            ec.frm(data.getLongitude(),10,5) + " " +
            ec.frm(data.getCallsign(),30) + " " + dBase + " " + loadStat);
    } // recordStats

    void recordStats() {
        ec.writeFileLine(ofile2, ec.frm(recordCount,6) + " " + loadStat);
    } // recordStats

    /**
     * write daily stats
     */
    void dailyStats() {
        ec.writeFileLine(ofile3, ec.frm(recordCount,7) +
            ec.frm(loadCount,7) + " " + prevTime.toString().substring(0,10) +
            ec.frm(dayCount,7));
        dayCount = 0;
    } // dailyStats


    /**
     * write closing stats
     * @param  ofile    RandomAccessFile to write to
     */
    void closeStats(RandomAccessFile ofile) {

        wmoRejectCount =
            drybulbCount[2] + dewPointCount[2] + surfaceTempCount[2] +
            atmosCount[2] + windDirectionCount[2] + windSpeedCount[2] +
            wavePeriodCount[2] + waveHeightCount[2] +
            swellDirCount[2] + swellHeightCount[2] + swellPeriodCount[2];

        ec.writeFileLine(ofile, "");
        ec.writeFileLine(ofile, "------------------------------------------------");
        ec.writeFileLine(ofile, "Closing statistics - loadid: " +  vosLoadId);
        ec.writeFileLine(ofile, "");
        ec.writeFileLine(ofile, "  Number of records - " + ec.frm(recordCount,6));
        ec.writeFileLine(ofile, "       Fatal errors - " + ec.frm(fatalCount,6));
        ec.writeFileLine(ofile, "    Records skipped - " + ec.frm(skipCount,6));
        ec.writeFileLine(ofile, "   Records rejected - " + ec.frm(rejectCount,6));
        ec.writeFileLine(ofile, " Recs in SADCO area - " + ec.frm(sadcoCount,6));
        ec.writeFileLine(ofile, "   Records overland - " + ec.frm(overlandCount,6));
        ec.writeFileLine(ofile, " Recs with PK error - " + ec.frm(PKerrorCount,6));
        ec.writeFileLine(ofile, "   WMO rejected cnt - " + ec.frm(wmoRejectCount,6));
        ec.writeFileLine(ofile, "(Records that contained invalid range checks)");
        ec.writeFileLine(ofile, "------------------------------------------------");
        ec.writeFileLine(ofile, "");
        ec.writeFileLine(ofile, "  LOAD DETAILS");
        ec.writeFileLine(ofile, "  ============");
        ec.writeFileLine(ofile, "          Date loaded: " + new java.util.Date());
        ec.writeFileLine(ofile, " Total records loaded: " + ec.frm(loadCount, 6));
        ec.writeFileLine(ofile, "");
        ec.writeFileLine(ofile, "  Arch records loaded: " + ec.frm(archCount, 6));
        ec.writeFileLine(ofile, "Arch dupls not loaded: " + ec.frm(archDupCount, 6));
        ec.writeFileLine(ofile, "");
        ec.writeFileLine(ofile, "  Main records loaded: " + ec.frm(mainCount, 6));
        ec.writeFileLine(ofile, "Main dupls not loaded: " + ec.frm(mainDupCount, 6));
        ec.writeFileLine(ofile, "");
        ec.writeFileLine(ofile, "--------------------------------------------------------------------------------");
        ec.writeFileLine(ofile, "                         Records   Loaded      WMO     CSIR   Between CSIR & WMO");
        ec.writeFileLine(ofile, "--------------------------------------------------------------------------------");
        writeCounts(ofile, ":Drybulb", drybulbCount);
        writeCounts(ofile, ":Dewpoint", dewPointCount);
        writeCounts(ofile, ":Surface Temperature", surfaceTempCount);

        ec.writeFileLine(ofile, "--------------------------------------------------------------------------------");
        ec.writeFileLine(ofile, "");
        ec.writeFileLine(ofile, "                         Records   Loaded      WMO");
        ec.writeFileLine(ofile, "--------------------------------------------------------------------------------");
        writeCounts(ofile, ":Atmospheric Pressure", atmosCount);
        writeCounts(ofile, ":Wind Direction", windDirectionCount);
        writeCounts(ofile, ":Wind Speed", windSpeedCount);
        writeCounts(ofile, ":Wave Period", wavePeriodCount);
        writeCounts(ofile, ":Wave Height", waveHeightCount);
        writeCounts(ofile, ":Swell Direction", swellDirCount);
        writeCounts(ofile, ":Swell Height", swellHeightCount);
        writeCounts(ofile, ":Swell Period", swellPeriodCount);
        ec.writeFileLine(ofile, "--------------------------------------------------------------------------------");

    } // closeStats

    void writeCounts(RandomAccessFile ofile, String text, int[] counts) {
        StringBuffer line = new StringBuffer(ec.frm(text,23));
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                line.append(ec.frm(counts[i],9));
            } else {
                line.append("        -");
            } // if (counts[i] > 0)
        } // for (int i = 0; i < counts.length; i++)
        ec.writeFileLine(ofile, line.toString());
    } // writeCounts


    /**
     * get the ship as use it as callsign
     */
    String getShip(String logbookId) {
        int id = Integer.parseInt(logbookId);
        return shipNames[id-1];
    } // getShip


    /**
     * return the next value.  Checks for empty values.
     */
    String getNextValue(StringTokenizer t) {
        String value = "";
        if (t.hasMoreTokens()) {
            value = t.nextToken();
            if (dbg) System.out.println("getNextValue: value1 = " + value);
            if (value.equals(";")) {
                value = "";
            } else {
                // get rid of delimiter in line
                if (t.hasMoreTokens()) t.nextToken();
            } // if (t.equals(DELIM))
        } else {
            value = "";
        } // if (t.hasMoreTokens())
        if (dbg) System.out.println("getNextValue: value2 = " + value);
        return value;
    } // getNextValue


    /**
     * Check whether the latitude & longitude fall inside the limits, and
     * if so, set olErrorFlag to true
     */
    boolean checkLatLong(int lat, int lon,
            int latmin, int latmax, int lonmin, int lonmax) {
        boolean flag = false;
        if ((lat >= latmin) && (lat <= latmax) &&
            (lon >= lonmin) && (lon <= lonmax)) {
            flag = true;
        } // if
        return flag;
    } // checkLatLong


    /**
     * Check whether the latitude & longitude fall inside the limits, and
     * if so, set olErrorFlag to true
     */
    boolean checkLatLong2(int lat, int lon, int[] latlon) {
        boolean flag = false;
        if ((lat >= latlon[0]) && (lat <= latlon[1]) &&
            (lon >= latlon[2]) && (lon <= latlon[3])) {
            flag = true;
        } // if
        return flag;
    } // checkLatLong2

    /**
     * return the value of the variable, or -1 if invalid
     * @param  var    variable to be tested (String)
     * @return value  value of variable, or -1 if invalid
     */
    int getValue (String var) {
        int value = EVAL;
        nonNumericFlag = false;
        try {
            value = new Integer(var.trim()).intValue();
        } catch (Exception e) {
            // set non numeric flag
            nonNumericFlag = true;
        } // empty or had a character
        return value;
    } // getValue


    /**
     * test for a valid value, and set errorFlags and write an error message
     * if not valid
     * @param  var    variable to be tested (String)
     * @param  min    minimum value (int)
     * @param  max    maximum value (int)
     * @param  txt1   first part of error message (String)
     * @param  txt2   first part of error message (String)
     * @return value  value of variable, or -1 of invalid
     */
    int testReadErrorValue (String var, int min, int max, String txt1, String txt2) {
        int value = getValue(var);
        if ((value < min) || (value > max) || (value == EVAL)) {
            setReadErrorFlags (txt1, var, txt2 +"(" + min + "/" + max + ")");
        } // if ((value < min) || (value > max))
        return value;
    } // testReadErrorValue


    /**
     * test for a valid value, and set errorFlags and write an error message
     * if not valid
     * @param  var    variable to be tested (String)
     * @param  min    minimum value (int)
     * @param  max    maximum value (int)
     * @param  txt1   first part of error message (String)
     * @param  txt2   first part of error message (String)
     * @return value  value of variable, or -1 of invalid
     */
    int testTempErrorValue (String var, int min, int max, String txt1, String txt2) {
        tempErrorFlag = false;
        int value = getValue(var);
        if ((value < min) || (value > max) || (value == EVAL)) {
            setTempErrorFlags(txt1, var, txt2 +"(" + min + "/" + max + ")");
        } // if ((value < min) || (value > max))
        return value;
    } // testValue

    /**
     * test for a valid value, and set errorFlags and write an error message
     * if not valid
     * @param  var    variable to be tested (String)
     * @param  minmax minimum and maximum value (int)
     * @param  txt1   first part of error message (String)
     * @param  txt2   first part of error message (String)
     * @return value  value of variable, or -1 of invalid
     */
    int testTempErrorValue2 (String var, int[] minmax, String txt1, String txt2) {
        tempErrorFlag = false;
        int value = getValue(var);
        if ((value < minmax[0]) || (value > minmax[1]) || (value == EVAL)) {
            setTempErrorFlags(txt1, var, txt2 +"(" + minmax[0] + "/" + minmax[1] + ")");
        } // if ((value < minmax[0]) || (value > minmax[1]))
        return value;
    } // testValue

    /**
     * test for a valid value, and set errorFlags and write an error message
     * if not valid
     * @param  value
     * @param  minmax minimum and maximum value (float)
     * @param  txt1   first part of error message (String)
     * @param  txt2   first part of error message (String)
     * @return value  value of variable, or -1 of invalid
     */
    void testTempErrorValue3 (float value, int[] minmax, String txt1, String txt2) {
        tempErrorFlag = false;
        if ((value < minmax[0]) || (value > minmax[1]) || (value == EVAL)) {
            setTempErrorFlags(txt1, value, txt2 +"(" + minmax[0] + "/" + minmax[1] + ")");
        } // if ((value < minmax[0]) || (value > minmax[1]))
    } // testValue


    /**
     * test for a valid value, and set errorFlags and write an error message
     * if not valid
     * @param  value
     * @param  minmax minimum and maximum value (float)
     * @param  txt1   first part of error message (String)
     * @param  txt2   first part of error message (String)
     * @return value  value of variable, or -1 of invalid
     */
    void testTempErrorValue4 (float value, double[] minmax, String txt1, String txt2) {

        if (dbg) System.out.println("<br>testTempErrorValue ");
        if (dbg) System.out.println("<br>testTempErrorValue4: value "+value);
        //System.out.println("<br>minmax "+minmax[i]);
        if (dbg) System.out.println("<br>testTempErrorValue4: txt1 "+txt1);
        if (dbg) System.out.println("<br>testTempErrorValue4: txt2 "+txt2);
        if (dbg) System.out.println("<br>testTempErrorValue4: tempErrorFlag "+tempErrorFlag);

        tempErrorFlag = false;
        if ((value < minmax[0]) || (value > minmax[1]) || (value == EVAL)) {
            setTempErrorFlags(txt1, value, txt2 +"(" + minmax[0] + "/" + minmax[1] + ")");
        } // if ((value < minmax[0]) || (value > minmax[1]))

        if (dbg) System.out.println("<br>testTempErrorValue4: tempErrorFlag "+tempErrorFlag);
    } // testValue

    /**
     * set the error flags, and call the error message routine
     */
    void setTempErrorFlags (String txt1, String txt2, String txt3) {
        tempErrorFlag = true;
        writeError (warningFile, txt1, txt2, "warning - " + txt3);
    } // setFlags

    /**
     * set the error flags, and call the error message routine
     */
    void setTempErrorFlags (String txt1, float val, String txt3) {
        tempErrorFlag = true;
        String fvalues = String.valueOf(val);
        writeError (warningFile, txt1, fvalues, "warning - " + txt3);
    } // setFlags


   /**
     * set the error flags, and call the error message routine
     */
    void setReadErrorFlags (String txt1, String txt2, String txt3) {
        readErrorFlag = true;
        writeError (ofile1, txt1, txt2, "rejected - " + txt3);
    } // setFlags

    /**
     * write the error message
     */
    void writeError (RandomAccessFile errorStatus, String txt1, String txt2, String txt3) {
        //errorFlag = true; // flag for error file footer
        ec.writeFileLine(errorStatus, ec.frm(recordCount,7) + ": " +
            ec.frm(txt1,7) + ec.frm("*" + txt2 + "*",10) + " - " + txt3);
    } // writeError

    /**
     * readings with 0 decimal place: convert zeros to null and 1 to zero
     */
    int nullZero0(int invar) {
        int outvar = invar;
        if (invar == 0) {
            outvar = VosTable.INTNULL;
        } else if (invar == 1) {
            outvar = 0;
        } // if (invar == 0)
        return outvar;
    } // nullzero_1


    /**
     * readings with 1 decimal place: convert zeros to null and .1 to zero
     */
    float nullZero1(float invar) {
        float outvar = invar;
        if (invar == 0.0) {
            outvar = VosTable.FLOATNULL;
        } else if (invar == 0.1) {
            outvar = 0.0f;
        } // if (invar == 0.0)
        return outvar;
    } // nullzero_1


    /**
     * update the log_vos_loads table
     */
    void updateLogVOSLoads() {
        //TimeZone tz = TimeZone.getTimeZone("GMT+2");
        //TimeZone.setDefault(tz);

        if (dbg) System.out.println("<br>updateLogVOSLoads: vosLoadId = " + vosLoadId);
        // delete any existing record that might exist
        LogVosLoads logVosLoads = new LogVosLoads();
        logVosLoads.setLoadid(loadId);
        logVosLoads.setVosLoadId(vosLoadId);
        logVosLoads.del();

        // put the new record
        logVosLoads.setSource(source);
        logVosLoads.setDateLoaded(new java.util.Date());
        logVosLoads.setMRecsLoaded(mainCount);
        logVosLoads.setARecsLoaded(archCount);
        logVosLoads.setMRecsDup(mainDupCount);
        logVosLoads.setARecsDup(archDupCount);
        logVosLoads.setDateStart(dateStart);
        logVosLoads.setDateEnd(dateEnd);
        if (dbg) System.out.println("updateLogVOSLoads: putting record in updateLogVOSLoads");
        logVosLoads.setRespPerson(respPerson);

        // Keep count of number records rejected this is records
        // with invalid range checks.
        logVosLoads.setRecsRejected(rejectCount);
        logVosLoads.setWmoRejected(wmoRejectCount);

        if (doLoad) {
            logVosLoads.put();
        } // if (doLoad)

        if (dbg) System.out.println("<br>updateLogVOSLoads: logVosLoads = " + logVosLoads);
    } // updateLogVOSLoads

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            LoadCLIWOCData local= new LoadCLIWOCData(args);
        } catch(Exception e) {
            //ec.processErrorStatic(e, "LoadCLIWOCData", "Constructor", "");
            e.printStackTrace();
            e.printStackTrace(System.out);
        } // try-catch
        // close the connection to the database
        common2.DbAccessC.close();

    } // main

} // class LoadCLIWOCData
