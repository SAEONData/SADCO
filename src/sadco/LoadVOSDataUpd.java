package sadco;

import java.io.*;
import java.sql.*;
import java.util.*; // for Calendar
//import oracle.html.*;

/**
 * Does the actual loading of the VOS data.
 *
 * This programme check for the negative sea surface temp value's.
 * On the db an update theses values with the corrected values given
 * by Jan Vermeulen" <meulen@weathersa.co.za> 18-09-2003 10:45
 *
 * SadcoVOS
 * screen.equals("load")
 * version.equals("update")
 *
 * @author  001123 - SIT Group
 * @version
 * 030922 - MA change existing LoadVOSData prog - to update negative values.
 */
public class LoadVOSDataUpd { // extends CompoundItem {

    /** some common functions */
    common.edmCommonPC ec = new common.edmCommonPC();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    //boolean write = false;
    boolean write = true;

    String thisClass = this.getClass().getName();

    // arguments
    String vosLoadId = "";
    String loadId = "";
    String source = "";
    String respPerson = "";
    String fileName = "";
    boolean doLoad = false;

    // file stuff
    String      pathName;
    RandomAccessFile lfile;
    RandomAccessFile ofile1;
    RandomAccessFile ofile2;
    RandomAccessFile ofile3;
    RandomAccessFile warningFile;
    RandomAccessFile dbgf;
    String      line;

    // the data
    VosTable data;

    Float SurfaceTemp = new Float(0f);
    //float SurfaceTemp = new float(0f);
    Float SurfaceTempType = new Float(0f);

    float temp = 0;

    // data variables - sector0
    // group 0
    String      callsign;  // Ship's Call sign (c_callsn)
    // group 1
    String      day;       // Day of the month (GMT) (dag)
    String      gg;        // time of observation to the nearest whole hour GMT (tyd)
    String      wi;        // Wind indicator (wndc)
    // group 2
    //String    shiposi=99;  // Indicator for Ship's position
    String      latin;     // Latitude in tenths of a degree  (slat)
    // group 3
    String      q;         // Quadrant of the globe (qud)
    String      longin;    // Longitude in tenths of a degree (slog)

    // data variables - sector1
    // group 4
    //String      r=4;       // Indicator for inclusion or ommission of precipitation data
    //String      operi;     // Indicator for station operation and for present and past  weather data.
    String      clht;      // Height of the base of the lowest cloud in the sky (stts)
    String      vv;        // Horizontal visibility at the surface
    // group 5
    String      clfr;      // Fraction of the celestial dome covered by cloud (windg)
    String      wdir;      // True direction from which wind is blowing
    String      wsp;       // Wind Speed
    // group 6
    //String      ati=1;     // Indicator for the air temperature
    String      ats;       // Sign of air temperature (tts)
    String      at;        // Air Temperature in tenths of a degree Celsius
    // group 7
    //String      dwi=2;     // Indicator for the dew-point temperature
    String      dws;       // Sign of dew-point temperature (tds)
    String      dw;        // dew-point Temperature in tenths of a degree Celsius
    // group 8
    //String      slpi=4;    // Indicator for sea-level pressure (c_press)
    String      slp;       // Pressure at sea-level in tenths of a millibar (= hectopascal),
                           //    omitting the thousands digit of the pressure value.
    // group 9
    //String      pci=5;     // Indicator for characteristic of pressure change (c_app)
    //String      pcc;       // Characteristic of pressure tendency during the three hours |
                             //    preceding time of observation
    //String      pc;        // Amount of pressure change during the three hours preceding |
                             //    the time of observation, in tenths of a millibar
    // group 10
    //String      ppwi=7;      // Indicator for past and present weather
    String      ppwn;      // Present weather (c_ww)
    //String      ppwp1;     // Past weather code W1
    //String      ppwp2;     // Past weather code W2

    // group 11
    //String      cti=8;     // Indicator for cloud types (cloud)
    String      ctf;       // Fraction of celestial dome covered by cloud(s) reported for
                           // CTL or, if no CTL cloud is present, for CTM.
    String      ctl;       // Clouds: stratocumulus, stratus, cumulus, cumulonimbus
    String      ctm;       // Clouds: altocumulus, altostratus, nimbostratus
    String      cth;       // Clouds: cirrus, cirrocumulus and cirrostratus

    // data variables - sector2
    // group 12
    //String      scsi=222;    // Indicator for ship's course and speed (c_courc)
    //String      cscs;      // Course (true) made good during three hours preceding
    //                       // time of observation
    //String      scss;      // Ship's avarage speed during thethree hours preceding
    //                       // time  of observation.
    // group 13
    //String      sti=0;     // Indicator for sea-surface temperature
    String      sts;       // Sign of sea-surface temperature (stemp 00 ?)
    String      st;        // Sea-surface temperature in tenths of a degree Celsius,
                           // its  sign being given by STS
    // group 14
    //String      wwi=2;     // Indicator for wind waves
    String      wwp;       // Period of wind waves in seconds (c_wavp)
    String      wwh;       // Height of wind waves in units of half metres (c_wavh)
    // group 15
    //String      swi=3;       // Swell direction indicator
    String      sw1d;      // Swell 1 true direction, in tens of degrees, from which
                           // swell waves are coming (c_swld1)
    //String      sw2d;      // Swell 2 true direction, in tens of degrees, from which
    //                       // swell waves are coming (c_swld2)
    // group 16
    //String      sw1i=4;    // Swell 1 indicator
    String      sw1p;      // Swell 1 period of swell waves in seconds (c_swsec1)
    String      sw1h;      // Swell 1 height of swell waves in units of half metres (c_swht1)
    // group 17
    //String      sw2i=5;    // Swell 2 indicator
    //String      sw2p;      // Swell 2 period of swell waves in seconds
    //String      sw2h;      // Swell 2 height of swell waves in units of half metres
    // group 18
    //String      ici=6;       // Indicator or ice accretion
    //String      ica ;      // Type of ice accretion
    //String      ict ;      // Thickness of ice in centimetres
    //String      icr ;      // Rate of ice accretion

    // data variables - date
    String      year;      // (styd)
    String      month;     // (c_fapr)

    // data variables - processLoadLine
    //String      latinD, latinM, latinS;
    //String      loninQ, loninD, loninM, loninS;
    String      latinS;
    String      loninS;
    String      dBase;
    String      winspType;
    float       atpres;
    String      platform;
    int         winsp;
    int         windir;
    int         wavht;
    int         swdir;
    int         swht;

    // error flags
    boolean     readErrorFlag = false;  // date, lat, lon, q, overland testing
    boolean     tempErrorFlag = false;  // data testing
    boolean     errorFlag     = false;  // all errors
    boolean     nonNumericFlag = false; // to catch the nonNumeric Fields

    // working variables
    int         recordCount = 0;
    int         loadCount = 0;
    int         dayCount = 0;
    int         fatalCount = 0;
    int         mainDupCount = 0;
    int         archDupCount = 0;
    int         mainCount = 0;
    int         archCount = 0;
    int         skipCount = 0;
    int         rejectCount = 0;
    String      loadStat = "";
    Timestamp   currTime;
    Timestamp   prevTime = Timestamp.valueOf("1850-01-01 00:00:00.0");
    java.util.Date  dateStart = Timestamp.valueOf("2050-01-01 00:00:00.0");
    java.util.Date  dateEnd   = Timestamp.valueOf("1850-01-01 00:00:00.0");

    int         EVAL = -9999999;

    public LoadVOSDataUpd(String args[]) {

        // get the argument values
        getArgParms(args);

        // get the correct path name
        //if ("morph".equals(ec.getHost())) {
        //    pathName = sc.MORPHL + "vos/";
        //} else {
        //    pathName = sc.LOCAL + "vos/";
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL+ "vos/";
        } else {
            pathName = sc.LOCALDIR;
        } // if host


        // delete the old the dummy file
        String dummyFile = pathName + fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        //load the data
        processInputFile();
        // update log_vos_loads table
        if (dbg) System.out.println("<br>Before updateLogVOSLoads: vosLoadId = " + vosLoadId);

//      updateLogVOSLoads();
        if (dbg) System.out.println("<br>After updateLogVOSLoads: vosLoadId = " + vosLoadId);

        // create the dummy file
        ec.createNewFile(dummyFile);

    } // constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        for (int i = 0; i < args.length; i++) {
            System.out.println("<br>" + args[i]);
        } // for i

        vosLoadId  = ec.getArgument(args,sc.VOSLOADID);
        loadId     = ec.getArgument(args,sc.LOADID);
        fileName   = ec.getArgument(args,sc.FILENAME);
        source     = ec.getArgument(args,sc.SOURCE);
        respPerson = ec.getArgument(args,sc.RESPPERSON);
        String temp = ec.getArgument(args, "pdoload");
        if ("".equals(temp)) { doLoad = true; }

        System.out.println("<br>");
        System.out.println("<br>vosLoadId = " + vosLoadId);
        System.out.println("<br>loadId = " + loadId);
        System.out.println("<br>fileName = " + fileName);
        System.out.println("<br>source = " + source);
        System.out.println("<br>respPerson = " + respPerson);
        System.out.println("<br>doLoad = " + doLoad);
    }   //  public void getArgParms()


    /**
     * process the input file
     */
    void processInputFile() {

        // Create other output file names
        //String of1 = fileName.substring(0,fileName.indexOf('.')) + ".errors";
        // above line changed to ...
        String of1 = fileName.substring(0,fileName.indexOf('.')) + ".rejected";
        String of2 = fileName.substring(0,fileName.indexOf('.')) + ".load.report";
        String of3 = fileName.substring(0,fileName.indexOf('.')) + ".daily";
        String of4 = fileName.substring(0,fileName.indexOf('.')) + ".debug";
        String of5 = fileName.substring(0,fileName.indexOf('.')) + ".warning";

        ec.deleteOldFile(pathName+of1);
        ec.deleteOldFile(pathName+of2);
        ec.deleteOldFile(pathName+of3);
        ec.deleteOldFile(pathName+of4);
        ec.deleteOldFile(pathName+of5);

        // Open the data file
        try {
            lfile = new RandomAccessFile(pathName+fileName, "r");
            ofile1 = new RandomAccessFile(pathName+of1, "rw");
            ofile2 = new RandomAccessFile(pathName+of2, "rw");
            ofile3 = new RandomAccessFile(pathName+of3, "rw");
            dbgf   = new RandomAccessFile(pathName+of4, "rw");
            warningFile = new RandomAccessFile(pathName+of5,"rw");

            if (ec.getHost().startsWith(sc.HOST)) {
                ec.submitJob("chmod 644 " + pathName+fileName);
                ec.submitJob("chmod 644 " + pathName+of1);
                ec.submitJob("chmod 644 " + pathName+of2);
                ec.submitJob("chmod 644 " + pathName+of3);
                ec.submitJob("chmod 644 " + pathName+of4);
                ec.submitJob("chmod 644 " + pathName+of5);
            } //if (ec.getHost().startsWith(sc.HOST)) {


        } catch (Exception e) {
            System.out.println("<br>" + thisClass + "open files error: " +
                e.getMessage());
            e.printStackTrace();
        } // try .. catch

        // read the data
        while(!ec.eof(lfile)) {
            line = ec.getNextValidLine(lfile);
            recordCount++;
            if (line.length() >= 125) {
                if (dbg) System.out.println("<br>line = " + line);
                readErrorFlag = false;

                readData();

                // check primary key - callsign, datetime, lat, lon
                processPK();

                // if OK, check for over land
                if (!readErrorFlag) {
                    testOverLand();

                    // if OK, check if record exists
                    if (!readErrorFlag) {

                        //System.out.println("Uncomment code for duplicate check !!");
                        if ("main".equals(dBase)) {
                            recordExists(new VosMain());
                        } else {
                            recordExists(new VosArch());
                        } // if ("main".equals(dBase))

                        System.out.println("<br>** readErrorFlag "+readErrorFlag);
                        // if OK, read and process rest of record
                        if (!readErrorFlag) {
                            loadCount++;
                            loadStat = "loaded";
                            dayCount++;
                            if ("main".equals(dBase)) {
                                processRest(new VosMain());
                                mainCount++;
                            } else {
                                processRest(new VosArch());
                                archCount++;
                            } // if ("main".equals(dBase))

                            System.out.println("<br>** mainCount "+mainCount);
                            System.out.println("<br>** dBase "+dBase);

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

        /*
        if (errorFlag) {

            ec.writeFileLine(ofile1, "");
            ec.writeFileLine(ofile1, "");
            ec.writeFileLine(ofile1, "+---------------------------+");
            ec.writeFileLine(ofile1, "| Errors have been detected |");
            ec.writeFileLine(ofile1, "+---------------------------+");

        } // if (readErrorFlag) */

        try {
            lfile.close();
            ofile1.close();
            ofile2.close();
            ofile3.close();
            dbgf.close();
            warningFile.close();
        } catch (Exception e) {
            System.out.println("<br>" + thisClass + "close files error: " +
                e.getMessage());
            e.printStackTrace();
        } // try .. catch

    } // void processInputFile() {


    /**
     * read all the data from the line
     */
    void readData() {

        // GROUP 0
        callsign = line.substring(0,6);    // c_callsn

        // GROUP 1
        day      = line.substring(7,9);    // dag
        gg       = line.substring(9,11);   // tyd
        wi       = line.substring(11,12);  // wndc

        // GROUP 2
        latin    = line.substring(15,18);  // slat

        // GROUP 3
        q        = line.substring(19,20);  // qud
        longin   = line.substring(20,24);  // slog

        if (dbg) System.out.print("<br>callsign = " + callsign);
        if (dbg) System.out.print(", day      = " + day);
        if (dbg) System.out.print(", gg       = " + gg);
        if (dbg) System.out.print(", wi       = " + wi);
        if (dbg) System.out.print(", latin    = " + latin);
        if (dbg) System.out.print(", q        = " + q);
        if (dbg) System.out.print(", longin   = " + longin);
        if (dbg) System.out.println();

        // GROUP 4
        clht  = line.substring(27,28);    // stts
        vv    = line.substring(28,30);

        // GROUP 5
        clfr  = line.substring(31,32);    // windg
        wdir  = line.substring(32,34);
        wsp   = line.substring(34,36);

        // GROUP 6
        ats   = line.substring(38,39);    // tts
        at    = line.substring(39,42);

        // GROUP 7
        dws   = line.substring(44,45);    // tds
        dw    = line.substring(45,48);

        // GROUP 8
        slp   = line.substring(50,54);    // c_press

        // GROUP 10
        ppwn  = line.substring(62,64);    // c_app

        // GROUP 11
        ctf   = line.substring(68,69);    // c_ww
        ctl   = line.substring(69,70);
        ctm   = line.substring(70,71);
        cth   = line.substring(71,72);

        if (dbg) System.out.print("<br>clht  = " + clht);
        if (dbg) System.out.print(", vv    = " + vv);
        if (dbg) System.out.print(", clfr  = " + clfr);
        if (dbg) System.out.print(", wdir  = " + wdir);
        if (dbg) System.out.print(", wsp   = " + wsp);
        if (dbg) System.out.print(", ats   = " + ats);
        if (dbg) System.out.print(", at    = " + at);
        if (dbg) System.out.print(", dws   = " + dws);
        if (dbg) System.out.print(", dw    = " + dw);
        if (dbg) System.out.print(", slp   = " + slp);
        if (dbg) System.out.print(", ppwn  = " + ppwn);
        if (dbg) System.out.print(", ctf   = " + ctf);
        if (dbg) System.out.print(", ctl   = " + ctl);
        if (dbg) System.out.print(", ctm   = " + ctm);
        if (dbg) System.out.print(", cth   = " + cth);
        if (dbg) System.out.println();

        // GROUP 13
        sts  = line.substring(80,81);
        st   = line.substring(81,84);

        // GROUP 14
        wwp  = line.substring(86,88);
        wwh  = line.substring(88,90);

        // GROUP 15
        sw1d = line.substring(92,94);

        // GROUP 16
        sw1p = line.substring(98,100);
        sw1h = line.substring(100,102);

        //if (dbg)
        System.out.print("<br> sts  = " + sts );
        //if (dbg)
        System.out.print(", st   = " + st  );
        //if (dbg)
        System.out.print(", wwp  = " + wwp );
        //if (dbg)
        System.out.print(", wwh  = " + wwh );
        //if (dbg)
        System.out.print(", sw1d = " + sw1d);
        //if (dbg)
        System.out.print(", sw1p = " + sw1p);
        //if (dbg)
        System.out.print(", sw1h = " + sw1h);
        //if (dbg)
        System.out.println();

        // YEAR & MONTH
        year = line.substring(120,122);
        month = line.substring(122,124);

        if (dbg) System.out.print("<br>year  = " + year);
        if (dbg) System.out.print(", month   = " + month);
        if (dbg) System.out.println();

    } // readData


    /**
     * process the inputted line for the primary key
     */
    void processPK() {

        int testno, testno2;

        // Make the year Y2K compatible
        testno= getValue(year);
        if (testno < 50) year = "20" + year;
        else             year = "19" + year;

        // Check that the year and month are correct
        testReadErrorValue(year, 1985, 2050, "YEAR   ", "Invalid year");
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
        testno = testReadErrorValue(gg, 0, 23, "HOURS  ", "Invalid hour");
        //testno = testReadErrorValue(gg, 0, 24, "HOURS  ", "Invalid hour");
        gg = gg + ":00";

        //if (testno == 24) {
        //    gg = "23:59";
        //} else {
        //    gg = gg + ":00";
        //} // if (testno == 0)


        // QUADRANT - Check if valid
        // 1 == North, East
        // 3 == South, East
        // 5 == South, West
        // 7 == North, West
        int intQ = getValue(q);
        if ((intQ != 1) && (intQ != 3) && (intQ != 5) && (intQ != 7)) {
            setReadErrorFlags("Q      ", q, "Invalid quadrant");
        } // if ((intQ != 1) && (intQ != 3) && (intQ != 5) && (intQ != 7))
        String quad = "";
        switch (intQ) {
            case 1: quad = "NE"; break;
            case 3: quad = "SE"; break;
            case 5: quad = "SW"; break;
            case 7: quad = "NW"; break;
        } // switch


        // LATITUDE - separate degrees and tenths of degrees
        //latinD = latin.substring(0,2);
        //latinM = latin.substring(2,3);

        // LATITUDE - check if valid
        // database officially only from 10N till 80S
        int max = 80;  // south
        if ((intQ == 1) || (intQ  == 7)) max = 10; // north

        /*testno = testReadErrorValue(latinD, 0, max, "LATIND ",
            "Invalid latitude degrees (" +quad+")");
        testno = testReadErrorValue(latinM, 0, 9, "LATINM ",
            "Invalid latitude minutes"); */

        // also test two together, in case of spaces
        //max = max * 10 + 9;
        max = max * 10;

        testno2 = testReadErrorValue(latin, 0, max, "LATIN  ",
            "Invalid latitude (lat*10)(" +quad+")");
        // LATITUDE - if quadrant is north (Q=1 or 7) the value must be -ve
        latinS = "";
        if ((intQ == 1) || (intQ  == 7)) {
            latinS = "-";
        } // if ((intQ == 1) || (intQ  == 7))


        // LONGITUDE - separate quadrant, degrees and tenths of degrees
        //loninD = longin.substring(0,3);
        //loninM = longin.substring(3,4);
        // LONGITUDE - Check if valid
        // database officially only from 30W till 70E
        max = 70;  // east
        if ((intQ == 5) || (intQ  == 7)) max = 30;  // west

        /*
        testno = testReadErrorValue(loninD, 0, max, "LONIND ",
            "Invalid longitude degrees (" +quad+")");
        testno2 = testReadErrorValue(loninM, 0, 9, "LONINM ",
            "Invalid longitude minutes");
        */

        // also test two together, in case of spaces
        //max = max * 10 + 9;
        max = max * 10;
        testno2 = testReadErrorValue(longin, 0, max, "LONGIN ",
            "Invalid longitude (lon*10)(" +quad+")");
        // LONGITUDE - if quadrant is north (Q=5 or 7) the value must be -ve
        loninS = "";
        if ((intQ == 5) || (intQ == 7)) { // east
            loninS = "-";
        } //

        // QUADRANT - decide whether data to be loaded in coastal
        //   or oceanic database.
        if ((intQ == 1) || (intQ == 3)) { // east
            if (testno < 50) {
                dBase = "main";
            } else {
                dBase = "arch";
            } // if (testno < 50)
        } else { // west
            dBase = "arch";
        } // if ((intQ == 1) || (intQ == 3))

        System.out.println("<br>** dBase "+dBase);

    } // processPK


    /**
     * test whether the current record exists
     * @param data VosMain or VosArch data record
     */
    void recordExists(VosTable data) {

       //if (dbg)
       System.out.println("<br> in recordExists funct doLoad "+doLoad);

        // set PK values
        data.setDateTime(year + "-" + month + "-" + day + " " + gg);
        data.setLatitude(getValue(latinS + latin) / 10f);
        data.setLongitude(getValue(loninS + longin) / 10f);
        data.setCallsign(callsign.trim().toUpperCase());

        if (dbg) System.out.println(new java.util.Date());
        // check whether the record exists
        int reccnt = 0;
        Float vosLoadId2 = new Float(0f);
        //Debug

            //reccnt = data.getRecCnt("");
        Vector result = data.getV();
        reccnt = result.size();

        // loop through all returned records
        common2.DbAccessC db = data.db;

        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            vosLoadId2 = (Float) row.elementAt(db.getColNumber(VosTable.LOAD_ID));
            SurfaceTemp =
                 (Float) row.elementAt(db.getColNumber(VosTable.SURFACE_TEMPERATURE));
            //SurfaceTempType =
            //     (Float) row.elementAt(db.getColNumber(VosTable.SURFACE_TEMPERATURE_TYPE));

            //if (dbg)
            System.out.println("<br>SurfaceTemp = "+ SurfaceTemp);


        } // for (int i = 0; i < result.size(); i++) {

        if (dbg) System.out.println("<br>" + thisClass +
            ".recordExists: reccnt = " + reccnt);

        if (dbg) System.out.println(new java.util.Date());
        if (dbg) System.out.println("<br>dateTime = " + data.getDateTime());
        if (dbg) System.out.println("<br>latitude = " + data.getLatitude());
        if (dbg) System.out.println("<br>longitude = " + data.getLongitude());
        if (dbg) System.out.println("<br>callsign = " + data.getCallsign());

        if (reccnt > 0) {

            //** For updating of negative sea temp values. Put back for normal run!

            //setReadErrorFlags("DUP REC", String.valueOf(reccnt), dBase +
            //    " - duplicate record");


            if ("main".equals(dBase)) {
                mainDupCount++;
            } else {
                archDupCount++;
            } // if ("main".equals(dBase))
            ec.setFrmFiller("0");
            loadStat = "already loaded (" +
                ec.frm(vosLoadId2.intValue(),4) + ")";
            ec.setFrmFiller(" ");
            recordStats(data);
        } // if (reccnt > 0)

    } // recordExists

    void processRest(VosTable data) {

        System.out.println("<br> in processRest funct doLoad "+doLoad);

        // Declairing object of VosReject Table with boolean variable
        // to determine whether to write the rejected records.
        VosReject reject = new VosReject();
        boolean rejected = false;

        int testno, testno2;
        float ftestno, ftestno2;
        int itestno;

        // set PK values again
        data.setDateTime(year + "-" + month + "-" + day + " " + gg);
        data.setLatitude(getValue(latinS + latin) / 10f);
        data.setLongitude(getValue(loninS + longin) / 10f);
        data.setCallsign(callsign.trim().toUpperCase());
        String position = (getValue(latinS + latin) / 10f) + "S/"+
            (getValue(loninS + longin) / 10f) + "W";

        // set PK values for rejected field with invalid range checks
        reject.setDateTime(year + "-" + month + "-" + day + " " + gg);
        reject.setLatitude(getValue(latinS + latin) / 10f);
        reject.setLongitude(getValue(loninS + longin) / 10f);
        reject.setCallsign(callsign.trim().toUpperCase());

        // indexes for range checking
        int longIndex = 0;
//        if (getValue(loninS + longin) >  250) { longIndex = 1; }
//        int latIndex = (getValue(latinS + latin) + 100) / 50;
        int latIndex = 0;

        // WIND DIRECTION - Check if valid
        testno = testTempErrorValue(wdir, 0, 36, "WDIR   ", "Invalid wind direction");
        if (!tempErrorFlag) {
            data.setWindDirection(nullZero0(testno * 10));
        } else {
            if(!nonNumericFlag) {
                if (testno == 36) {
                    testno=0;
                } //if (testno == 36) {
                reject.setWindDirection(nullZero0(testno * 10));
                rejected = true;
            } //if(!nonNumericFlag) {
        } //if (!tempErrorFlag) {

        // WIND SPEED TYPE - Check if valid
        //wi = wi.replace('/', ' ');
        testno = getValue(wi);
        if ((testno != 0) && (testno != 1) && (testno != 3) && (testno != 4)) {
            setTempErrorFlags("WI      ", wi, "Invalid Wind speed Type");

        } else {

            // WIND SPEED TYPE - Codes 0 or 3 indicate estimated reading
            // Codes 1 and 4 are anenometer readings. Use code E for
            // estimated  or M for measured. Leave blank if neither.

            winspType = Tables.CHARNULL;
            if ((testno == 0) || (testno == 3)) winspType = "E";
            if ((testno == 1) || (testno == 4)) winspType = "M";

            data.setWindSpeedType(winspType);

        } // if ((testno != 0) && (testno != 1) && (testno != 3) && (testno != 4)) {


        // WIND SPEED - Check if valid
        int[][][] wndSpeed = { { {0, 99} } };
//        int[][][] wndSpeed = {
//            { {0,16},{0,21},{0,19},{0,16},{0,21},{0,27},{0,29},{0,32},{0,40},   // west
//              {0,31},{0,27},{0,26},{0,25},{0,23},{0,21},{0,22},{0,16},{0,16} },
//            { {0,21},{0,21},{0,23},{0,23},{0,23},{0,27},{0,29},{0,32},{0,35},   // east
//              {0,29},{0,26},{0,26},{0,26},{0,22},{0,21},{0,28},{0,28},{0,28} } };

        if (!tempErrorFlag) {

            // do test for non-numeric range check is a bonus here
            //testno2 = testTempErrorValue(wsp, 0, 99, "WSP    ", "Invalid wind speed");
            ftestno = testTempErrorValue(wsp, 0, 99, "WSP    ", "Non-numeric wind speed");

            // WIND SPEED - If the wind indicator code is 3 or 4 the reading is in
            // knots - it is then converted to metres per second
            if (!tempErrorFlag) {

                // Change knots to m/s.
                if ((testno == 3) || (testno == 4)) ftestno *= 0.51f;
                //testno2 *= 0.51;
                //testno2 = testTempErrorValue(wsp, 0, 40, "WSP    ",
                //"Invalid wind speed("+position+")");
                //testno2 = testTempErrorValue2(wsp, wndSpeed[longIndex][latIndex],
                //                    "WSP    ", "Invalid wind speed("+position+")");

                testTempErrorValue3(ftestno, wndSpeed[longIndex][latIndex],
                        "WSP    ", "Invalid wind speed("+position+")");

                if (!tempErrorFlag) {
                    data.setWindSpeed(nullZero1(ftestno));
                } else {
                    if(!nonNumericFlag) {
                        reject.setWindSpeed(nullZero1(ftestno));
                        rejected = true;
                    }//if(!nonNumericFlag) {
                } //if (!tempErrorFlag) {
            } // if (!tempErrorFlag) {
        } //if (!tempErrorFlag) {


        // AIR TEMPERATURE - If the temperature sign code is 0 the
        // temperature is positive or zero and if it is 1 it is
        // negative. If the code is 1 it is changed to a minus
        // sign.

        //ats = ats.replace(' ', '0').replace('/', '0');
        testno = testTempErrorValue(ats, 0, 1, "ATS    ",
            "Invalid air temperature sign");
        //boolean signErrorFlag = tempErrorFlag;

        // AIR TEMPERATURE (drybulb) - Check if valid
        int[][][] drybulb = {{{-25, 50}}};
//        int[][][] drybulb = {
//            {{20,36},{18,37},{11,37},{15,40},{10,35},{6,32},{4,31},{8,32},{6,31}, // west
//             {6,30},{4,25},{-10,19},{-7,20},{-10,17},{-7,7},{-19,7},{-25,0},{-25,0} },
//            {{19,34},{20,36},{20,37},{20,37},{20,37},{17,35},{16,35},{10,34},{9,33},// east
//             {6,30},{1,23},{-10,19},{-4,11},{-3,7},{-5,5},{-25,12},{-25,12},{-25,12} } };

        //int[][][] drybulb = {
        //    {{200,360},{180,370},{110,370},{150,400},{100,350},{60,320},{40,310},{80,320},{60,310}, // west
        //     {60,300},{40,250},{-100,190},{-70,200},{-100,170},{-70,70},{-190,70},{-250,0},{-250,00} },
        //    {{190,340},{200,360},{200,370},{200,370},{200,370},{170,350},{160,350},{100,340},{90,330},// east
        //     {60,300},{10,230},{-100,190},{-40,110},{-30,70},{-50,50},{-250,120},{-250,120},{-250,120} } };


        //int ttt = EVAL;
        float ttt = EVAL;

        boolean valid_ttt = false;

        // Testing for numeric/non-numeric values.
        if (!tempErrorFlag ) {
            // do test for non-numeric range check is a bonus here
            //testno = testTempErrorValue(at,0,400,"AT     ","Invalid air temperature");
            ftestno = testTempErrorValue(at,0,999,"AT     ","Non-numeric air temperature");
            if (!tempErrorFlag ) {
                //ats = ats.replace('1','-');
                //at = at.replace(' ', '0').replace('/', '0');
                if ("1".equals(ats)) {
                    ftestno *= -1f;
                } //if ("1".equals(ats)) {

                ftestno /= 10f;
                //now test range
                //testno = testTempErrorValue2(ats + at, drybulb[longIndex][latIndex],
                //    "AT     ", "Invalid air temperature("+position+")");
                testTempErrorValue3(ftestno, drybulb[longIndex][latIndex],
                    "AT     ", "Invalid air temperature("+position+")");

                if (!tempErrorFlag ) {
                    data.setDrybulb(nullZero1(ftestno));
                    // to check whether the testno where in valid value range.
                    // if true set valid_ttt flag true.
                    valid_ttt = true;
                    ttt = ftestno;
                } else {
                    if (!nonNumericFlag) {
                        //reject.setDrybulb(nullZero1(testno / 10f));
                        reject.setDrybulb(nullZero1(ftestno));
                        rejected = true;
                    } //if (!nonNumericFlag) {
                }//if (!tempErrorFlag )
            } else {
                if (!nonNumericFlag) {
                    ftestno /= 10f;
                    reject.setDrybulb(nullZero1(ftestno));
                    rejected = true;
                } //if (!tempErrorFlag ) {

            }//if (!tempErrorFlag )

         } // if (!tempErrorFlag ) {

        // DEW-POINT TEMPERATURE - If the temperature sign code is 0 the
        // temperature is positive or zero and if it is 1 it is
        // negative. If the code is 1 it is changed to a minus
        // sign.

        // do test for non-numeric range check is a bonus here
        testno = testTempErrorValue(dws, 0, 1, "DWS    ",
                    "Invalid dew-point temperature sign");
        //int dd = EVAL;
        float dd = EVAL;

        // DEW-POINT TEMPERATURE - Check if valid
        int[][][] dewpoint = {{{-25, 50}}};
//        int[][][] dewpoint = {
//            {{12,30},{9,32},{7,31},{8,30},{2,30},{1,27},{-2,28},{-5,29},{-5,30}, // west
//             {-10,29},{-13,20},{-10,15},{-9,16},{-9,13},{-11,7},{-22,8},{-25,-1},{-25,-1} },
//            {{16,30},{16,31},{15,32},{15,32},{10,31},{8,32},{2,32},{-4,32},{-1,30},// east
//             {-1,25},{-14,21},{-10,15},{-4,7},{-6,5},{-8,5},{-25,12},{-25,12},{-25,12} } };

        if (!tempErrorFlag) {
            ftestno = testTempErrorValue(dw,0,999,"DW     ","Non-numeric dew-point temperature");

            if (!tempErrorFlag) {
                // check if sign is negative
                if ("1".equals(dws)) {
                    ftestno *= -1f;
                } //if ("1".equals(ats)) {

                ftestno /= 10f;
                testTempErrorValue3(ftestno , dewpoint[longIndex][latIndex],
                                "DW     ", "Invalid dew-point temperature("+position+")");

            } //if (!tempErrorFlag) {

            dd = ftestno;

            if (dbg) System.out.println("before !tempErrorFlag "+dd+" "+ttt);
            // test if tempErrorFlag equals false
            if (!tempErrorFlag) {

                // test for valid ttt
                if (valid_ttt) {

                    if (dbg) System.out.println("before if "+dd);
                    // test for dd < ttt
                    if ( dd < ttt) {
                        data.setDewpoint(nullZero1(dd));
                    } else {
                        setTempErrorFlags("DW     ", dw,
                            "dew-point temperature rejected (dd > ttt)");
                    } //if ( dd < ttt)
                } else {
                    // if ttt not valid write dd
                    data.setDewpoint(nullZero1(dd));
                } // if (valid_ttt)

            } else {
                if (!nonNumericFlag) {
                    reject.setDewpoint(nullZero1(dd));
                    rejected = true;
                } //if (!nonNumericFlag) {

            }//if (!tempErrorFlag)

        } //if (!tempErrorFlag) {

        // SEA-LEVEL PRESSURE - Check if valid
        //slp = slp.replace(' ', '0').replace('/', '0');
        tempErrorFlag = false;
        testno = getValue(slp);
        String dum = slp.substring(0,1);
        if ((!"0".equals(dum) && !"9".equals(dum)) || (testno == EVAL)) {
            setTempErrorFlags("SLP    ", slp, "Invalid sea-level pressure");
        } // if ((!"0".equals(dum) && !"9".equals(dum)) || (testno == EVAL)) {

        // SEA-LEVEL PRESSURE - convert to a numeric value, but the
        // value remains in tenths of a millibar. If the first digit
        // is a 0, the value must be increased by 1000
        if (!tempErrorFlag && (testno != 0)) {
            if ("0".equals(slp.substring(0,1))) {
                //ftestno += 1000f;
                slp ="1"+slp;
            } // if ("0".equals(slp.substring(0,1))) {
            testno=testTempErrorValue(slp,9300,10500,"SLP   ","Invalid sea-level pressure");

            if(!tempErrorFlag) {
                data.setAtmosphericPressure(testno / 10f);
            }
             /*
             else {
                if (!nonNumericFlag) {
                    reject.setAtmosphericPressure(testno);
                    rejected = true;
                } //if (!nonNumericFlag)
            }//if(!tempErrorFlag)
            */
        } // if (!tempErrorFlag && (testno != 0))

        // PRESENT WEATHER CODE - Check if valid
        //ppwn = ppwn.replace('/', ' ');
        testTempErrorValue(ppwn, 0, 99, "PPWN   ", "Invalid weather code");
        if (!tempErrorFlag) {
            data.setWeatherCode(ppwn);
        } //if (!tempErrorFlag) {

        // CLOUD AMOUNT - Check if valid and reduce to 0 if invalid
        //clfr = clfr.replace('/', ' ');
        testTempErrorValue(clfr, 0, 9, "CLFR   ", "Invalid cloud amount");
        if (!tempErrorFlag) {
            data.setCloudAmount(clfr);
        } //if (!tempErrorFlag) {

        // CLOUD1 - Check if valid and reduce to 0 if invalid
        //ctf = ctf.replace('/', ' ');
        testTempErrorValue(ctf, 0, 9, "CTF   ", "Invalid cloud 1");
        if (!tempErrorFlag) {
            data.setCloud1(ctf);
        } //if (!tempErrorFlag) {

        // CLOUD2 - Check if valid and reduce to 0 if invalid
        //ctl = ctl.replace('/', ' ');
        testTempErrorValue(ctl, 0, 9, "CTL   ", "Invalid cloud 2");
        if (!tempErrorFlag) {
            data.setCloud2(ctl);
        } //if (!tempErrorFlag) {

        // CLOUD3 - Check if valid and reduce to 0 if invalid
        //clht = clht.replace('/', ' ');
        testTempErrorValue(clht, 0, 9, "CLHT   ", "Invalid cloud 3");
        if (!tempErrorFlag) {
            data.setCloud3(clht);
        } //if (!tempErrorFlag) {

        // CLOUD4 - Check if valid and reduce to 0 if invalid
        //ctm = ctm.replace('/', ' ');
        testTempErrorValue(ctm, 0, 9, "CTM   ", "Invalid cloud 4");
        if (!tempErrorFlag) {
            data.setCloud4(ctm);
        } //if (!tempErrorFlag) {

        // CLOUD5 - Check if valid and reduce to 0 if invalid
        //cth = cth.replace('/', ' ');
        testTempErrorValue(cth, 0, 9, "CTH   ", "Invalid cloud 5");
        if (!tempErrorFlag) {
            data.setCloud5(cth);
        } //if (!tempErrorFlag) {

        // VISIBILITY CODE - Check if valid
        //vv = vv.replace('/', ' ');
        testTempErrorValue(vv, 90, 99, "VV    ", "Invalid visibility code");
        if (!tempErrorFlag) {
            data.setVisibilityCode(vv);
        } //if (!tempErrorFlag) {



        // SEA TEMPERATURE - If the temperature sign code is 0 the
        // temperature is positive or zero and if it is 1 it is
        // negative. If the code is 1 it is changed to a minus
        // sign.
        //sts = sts.replace(' ', '0').replace('/', '0');
        testno = testTempErrorValue(sts, 0, 1, "STS   ",
            "Invalid sea temperature sign");

        // made change as in Air temp
        // SEA TEMPERATURE - Check if valid
        int[][][] seaTemp = {{{-50, 50}}};
//        int[][][] seaTemp = {
//            { {18,34},{15,34},{15,35},{14,34},{14,33},{10,31},{6,30},{4,30},{6,31}, // west
//              {6,28},{3,26},{0,21},{-2,18},{-2,17},{-2,10},{-2,3},{-2,2},{-2,2} },
//            { {16,33},{18,34},{31,34},{20,34},{20,35},{18,24},{17,34},{12,33},{11,32},// east
//              {9,30},{10,25},{0,17},{-1,14},{-2,5},{-2,4},{-2,6},{-2,6},{-2,6} } };

        if (!tempErrorFlag) {
            // do test for non-numeric range check is a bonus here
            //testno = testTempErrorValue(st, 0, 370, "ST    ","Invalid sea temperature");
            ftestno = testTempErrorValue(st, 0, 999, "ST    ","Non-numeric sea temperature");

              if (!tempErrorFlag) {
                    //check for negative sign
                    if ("1".equals(sts)) {   // was dws
                        ftestno *= -1f;
                    } //if ("1".equals(sts)) {

                    ftestno /= 10f;

                    testTempErrorValue3(ftestno, seaTemp[longIndex][latIndex],
                            "ST    ", "Invalid sea temperature("+position+")");

                    // test     SurfaceTemp vs
                    //float temp = new float().SurfaceTemp;
                    //new Float(s1).floatValue();

                    if (!tempErrorFlag) {
                        //data.setSurfaceTemperature(nullZero1(testno / 10f));
                        data.setSurfaceTemperature(ftestno);
                    } else {
                        if (!nonNumericFlag) {
                            //reject.setSurfaceTemperature(nullZero1(testno / 10f));
                            reject.setSurfaceTemperature(ftestno);
                            rejected = true;
                        }//if (!nonNumericFlag) {
                    }// if (!tempErrorFlag)

            } else {
                if (!nonNumericFlag) {
                    //reject.setSurfaceTemperature(nullZero1(testno / 10f));
                    reject.setSurfaceTemperature(ftestno);
                    rejected = true;
                }//if (!nonNumericFlag) {
            }// if (!tempErrorFlag)
        } // if (!tempErrorFlag) {

        // WAVE PERIOD - Check if valid
        int[][][] wvPeriod = {{{0, 30}}};
//        int[][][] wvPeriod = {
//            { {0,11},{0,15},{0,15},{0,15},{0,16},{0,17},{0,19},{0,19},{0,20},   // west
//              {0,17},{0,13},{0,11},{0,11},{0,11},{0,6},{0,9},{0,9},{0,9} },
//            { {0,13},{0,12},{0,15},{0,15},{0,15},{0,16},{0,19},{0,20},{0,20},   // east
//              {0,16},{0,16},{0,14},{0,14},{0,12},{0,6},{0,13},{0,13},{0,13} } };

        //wwp = wwp.replace(' ', '0').replace('/', '0');
        //testno = testTempErrorValue(wwp, 0, 30, "WWP   ", "Invalid wave period");
        //testno = testTempErrorValue(wwp, 0, 20, "WWP   ", "Invalid wave period");
        testno = testTempErrorValue2(wwp, wvPeriod[longIndex][latIndex],
                    "WWP   ", "Invalid wave period("+position+")");

        if (!tempErrorFlag) {
            data.setWavePeriod(nullZero0(testno));
        } else {
            if (!nonNumericFlag) {
                reject.setWavePeriod(nullZero0(testno));
                rejected = true;
            }//if (!nonNumericFlag) {
        }// if (!tempErrorFlag)


        // WAVE HEIGHT - Check if valid
        //int[][][] wvHeight = {
        //    { {0,4},{0,5},{0,6},{0,6},{0,7},{0,8},{0,11},{0,11},{0,11},   // west
        //      {0,12},{0,8},{0,9},{0,8},{0,6},{0,6},{0,5},{0,5},{0,5} },
        //    { {0,6},{0,6},{0,6},{0,7},{0,7},{0,8},{0,10},{0,13},{0,13},   // east
        //      {0,10},{0,9},{0,9},{0,8},{0,6},{0,6},{0,9},{0,9},{0,9} } };

        int[][][] wvHeight = {{{0 ,35}}};
//        int[][][] wvHeight = { // Wave height measured in half meters units.
//            { {0,8},{0,10},{0,12},{0,12},{0,14},{0,16},{0,22},{0,22},{0,22},   // west
//              {0,24},{0,16},{0,18},{0,16},{0,12},{0,12},{0,10},{0,10},{0,10} },
//            { {0,12},{0,12},{0,12},{0,14},{0,14},{0,16},{0,20},{0,26},{0,26},   // east
//              {0,20},{0,18},{0,18},{0,16},{0,12},{0,12},{0,18},{0,18},{0,18} } };

        //wwh = wwh.replace('/', ' ');
        //testno = testTempErrorValue(wwh, 0, 99, "WWH   ", "Invalid wave height");
        //testno = testTempErrorValue(wwh, 0, 35, "WWH   ", "Invalid wave height");
        testno = testTempErrorValue2(wwh, wvHeight[longIndex][latIndex],
            "WWH   ", "Invalid wave height");
        if (!tempErrorFlag) {
            data.setWaveHeight(nullZero1(testno * 0.5f));
        } else {
            if (!nonNumericFlag) {
                reject.setWaveHeight(nullZero1(testno * 0.5f));
                rejected = true;
            } //if (!nonNumericFlag) {
        }// if (!tempErrorFlag)


        // SWELL DIRECTION - Check if valid
        //sw1d = sw1d.replace('/', ' ');
        testno = testTempErrorValue(sw1d, 0, 36, "SW1D   ",
            "Invalid swell direction");
        if (!tempErrorFlag) {
            if (testno == 36) {
                testno=0;
            } //if (testno == 36) {
            data.setSwellDirection(nullZero0(testno * 10));
        } else {
            if (!nonNumericFlag) {
                reject.setSwellDirection(nullZero0(testno * 10));
                rejected = true;
            } //if (!nonNumericFlag) {
        }// if (!tempErrorFlag)

        // SWELL PERIOD - Check if valid
        int[][][] swPeriod = {{{0 ,30}}};
//        int[][][] swPeriod = {
//            { {0,15},{0,16},{0,19},{0,17},{0,17},{0,19},{0,19},{0,19},{0,19},   // west
//              {0,19},{0,15},{0,15},{0,15},{0,15},{0,15},{0,15},{0,14},{0,14} },
//            { {0,15},{0,16},{0,17},{0,17},{0,17},{0,17},{0,19},{0,20},{0,20},   // east
//              {0,17},{0,17},{0,17},{0,16},{0,13},{0,13},{0,14},{0,14},{0,14} } };

        //sw1p = sw1p.replace(' ', '0').replace('/', '0');
        //testno = testTempErrorValue(sw1p, 0, 30, "SW1P  ", "Invalid swell period");
        testno = testTempErrorValue2(sw1p, swPeriod[longIndex][latIndex],
            "SW1P  ", "Invalid swell period("+position+")");
        //testno = testTempErrorValue(sw1p, 0, 25, "SW1P  ", "Invalid swell period");
        if (!tempErrorFlag) {
            data.setSwellPeriod(nullZero0(testno));
        } else {
            if (!nonNumericFlag) {
                reject.setSwellPeriod(nullZero0(testno));
                rejected = true;
            }
        }// if (!tempErrorFlag)

        // SWELL HEIGHT - Check if valid
        //int[][][] swHeight = {
        //    { {0,5},{0,6},{0,8},{0,8},{0,11},{0,11},{0,11},{0,13},{0,16},   // west
        //      {0,13},{0,11},{0,8},{0,8},{0,7},{0,8},{0,8},{0,8},{0,8} },
        //    { {0,7},{0,6},{0,8},{0,10},{0,13},{0,11},{0,12},{0,14},{0,16},   // east
        //      {0,11},{0,11},{0,9},{0,8},{0,9},{0,8},{0,8},{0,8},{0,8} } };

        int[][][] swHeight = {{{0,35}}};
//        int[][][] swHeight = { // Wave height measured in half meters units.
//            { {0,10},{0,12},{0,16},{0,16},{0,22},{0,22},{0,22},{0,26},{0,32},   // west
//              {0,23},{0,22},{0,16},{0,16},{0,14},{0,16},{0,16},{0,16},{0,16} },
//            { {0,14},{0,12},{0,16},{0,20},{0,26},{0,22},{0,24},{0,28},{0,32},   // east
//              {0,22},{0,22},{0,18},{0,16},{0,18},{0,16},{0,16},{0,16},{0,16} } };


        //sw1h = sw1h.replace('/', ' ');
        //testno = testTempErrorValue(sw1h, 0, 99, "SW1H  ", "Invalid swell height");
        //testno = testTempErrorValue(sw1h, 0, 35, "SW1H  ", "Invalid swell height");
        testno = testTempErrorValue2(sw1h, swHeight[longIndex][latIndex],
                    "SW1H  ", "Invalid swell height("+position+")");
        if (!tempErrorFlag) {
            data.setSwellHeight(nullZero1(testno * 0.5f));
        } else {
            if (!nonNumericFlag) {
                reject.setSwellHeight(nullZero1(testno * 0.5f));
                rejected = true;
            } //if (!nonNumericFlag) {
        }// if (!tempErrorFlag)


        // PLATFORM TYPE - generate code
        platform = "1";
        testno = getValue(callsign);

        // If the callsign is all numeric and begins with a 6 it is a yacht
        // otherwise an all numeric callsign is a buoy
        if (testno != EVAL) {
            if ("6".equals(callsign.substring(0,1))) {
                platform = "9";
            } else {
                platform = "4";
            } // if ("6".equals(callsign.substring(0,1)))
        } else {
            if ("RIGG".equals(callsign) || "PLAT".equals(callsign)) {
                platform = "5";
            } // if ("RIGG".equals(callsign) || "PLAT".equals(callsign))
        } // if (testno != EVAL)


        data.setPlatform(platform);

        // write the data
        data.setLoadId(vosLoadId);

        data.setDataId("WB");
        data.setQualityControl("1");
        data.setSource1("2");
        if (dbg) System.out.println("<br>" + thisClass + ".processRest: dBase = " +
            dBase);

        if (dbg) System.out.println("** doLoad "+doLoad);

        if (doLoad) {
//            data.put();

//***   Stuk for update.
        float surfTemp = SurfaceTemp.floatValue();
        //if (dbg)

        System.out.println("<br> surfTemp in processRest funct "+surfTemp);

         ec.writeFileLine(dbgf,
             " Rec exist funct before if getSurfaceTemp Val (loadfile) )"+
             ec.frm(data.getSurfaceTemperature(),10,4)
             +" temp db val "+surfTemp);

        if (data.getSurfaceTemperature() != surfTemp) {

            System.out.println
                ("data.getSurfaceTemperature() "+data.getSurfaceTemperature()
                +" "+surfTemp);

             ec.writeFileLine(dbgf,
                 " Rec exist funct in if getSurfaceTemp Val (loadfile) )"+
                 ec.frm(data.getSurfaceTemperature(),10,4)
                 +" temp db val "+surfTemp);

            VosMain whereVosMain = new VosMain();

            //set values
            whereVosMain.setDateTime(year + "-" + month + "-" + day + " " + gg);
            whereVosMain.setLatitude(getValue(latinS + latin) / 10f);
            whereVosMain.setLongitude(getValue(loninS + longin) / 10f);
            whereVosMain.setCallsign(callsign.trim().toUpperCase());


            VosMain updVosMain = new VosMain();
            updVosMain.setSurfaceTemperature(data.getSurfaceTemperature());


            whereVosMain.upd(updVosMain);
            //if (dbg) System.out.println ("<br> whereUpdate: "+ whereVosMain.getUpdStr());

            //set (replace) value of db with load file.
            //updVosMain.setSurfaceTemperature(data.getSurfaceTemperature() );
            //if (success) { //success =
            //} //if (success) {

        } //if (ftestno != temp) {

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

            reject.setDataId("WB");
            //SAWS
            reject.setQualityControl("9");
            reject.setSource1("2");
            reject.setPlatform(platform);

            if (dbg) System.out.println("<br>" + thisClass + ".processRest: dBase = " +
                dBase);
            if (doLoad) {
                System.out.println("Reject "+reject.toString());
//                reject.put();
            } //if (doLoad) {

            int nr = reject.getNumRecords();
            if (nr == 0) {
                ec.writeFileLine(dbgf, "nr inserted = " + nr + " reject " +
                    ec.frm(recordCount,6));
                ec.writeFileLine(dbgf, reject.getMessages());
                ec.writeFileLine(dbgf, reject.getInsStr());
                ec.writeFileLine(dbgf, reject.toString());
            } // if (nr == 0)
            rejectCount++;
            if (dbg) System.out.println("rejectCount equals :"+rejectCount);

        } //if (rejected) {

        if (dbg) System.out.println("<br>*** WRITE TO DB *** " + dBase);

        // check for change of day
        currTime = ec.setDateTime(year + "-" + month + "-" + day);
        if (!currTime.equals(prevTime) &&  (loadCount > 1)) {
            dailyStats();
        } // if (!data.getDateTime().equals(prevTime) &&  (loadCount > 1))
        prevTime = currTime;


        // write info message
        recordStats(data);
        //ec.writeFileLine(ofile2, ec.frm(loadCount,6) + " " +
        //    data.getDateTime("") + " " +
        //    ec.frm(data.getLatitude(),9,5) +
        //    ec.frm(data.getLongitude(),9,5) + " " +
        //    ec.frm(data.getCallsign(),6) + " " + dBase + " " + loadStat);

        // check for date min and max
        if (data.getDateTime().before(dateStart)) dateStart = data.getDateTime();
        if (data.getDateTime().after(dateEnd))    dateEnd = data.getDateTime();

    } // processRest


    /**
     * Test for overland data
     */
    void testOverLand() {

        int intQ = getValue(q);
        int lat = getValue(latin);
        int lon = getValue(longin);

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
/*
            if (checkLatLong(lat, lon, 45,  47, 72,  77)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 47,  50, 67,  85)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 50,  52, 60,  90)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 50,  52, 17,  27)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 52,  55,  7,  90)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 55,  57,  5,  95)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 57,  60,  0,  97)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 60,  62,  0, 100)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 62,  65,  0, 105)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 65,  67,  0, 107)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 67,  70,  0, 110)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 70,  72,  0, 115)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 72,  75,  0, 120)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 75,  77,  0, 120)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 77,  80,  0, 125)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 80,  82,  0, 127)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 82,  85,  0, 130)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 85,  87,  0, 127)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 87,  90,  0, 130)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 90,  92,  0, 130)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 92,  95,  0, 132)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 95,  97,  0, 135)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 97, 100,  0, 135)) olErrorFlag = true;
*/
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

            /*
            if (checkLatLong(lat, lon,  0,   2, 100, 427)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  2,   5,  97, 430)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  5,   7,  97, 432)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  7,  10,  97, 435)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 10,  12,  97, 437)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 12,  15,  97, 440)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 15,  17,  97, 445)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 17,  20, 100, 447)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 20,  22, 100, 452)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 22,  25, 100, 457)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 25,  27, 100, 460)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 27,  30, 100, 462)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 30,  32, 100, 465)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 32,  35, 100, 467)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 35,  37,  97, 470)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 37,  40, 100, 470)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 40,  42, 100, 475)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 42,  45,  90, 475)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 45,  47,  57,  65)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 45,  47,  90, 477)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 47,  50,  57,  80)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 47,  50,  87, 480)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 50,  52,  57, 482)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 52,  55,  57, 482)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 55,  57,  55, 485)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 57,  60,  57, 487)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 60,  60,   0,  10)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 60,  62,  50, 487)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 62,  62,   0,  15)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 62,  65,  47, 490)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 65,  65,   0,  32)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 65,  67,  40, 490)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 67,  70,   0, 492)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 70,  72,   0, 492)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 72,  75,   0, 495)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 75,  77,   0, 495)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 77,  80,   0, 497)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 80,  82,   0, 497)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 82,  85,   0, 500)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 85,  87,   0, 502)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 87,  90,   0, 502)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 90,  92,   0, 505)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 92,  95,   0, 505)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 95,  97,   0, 505)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 97, 100,   0, 507)) olErrorFlag = true;

            */

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

            /*
            if (checkLatLong(lat, lon,   0,   2,  95, 425)) olErrorFlag = true;
            if (checkLatLong(lat, lon,   2,   5,  95, 420)) olErrorFlag = true;
            if (checkLatLong(lat, lon,   5,   7,  92, 422)) olErrorFlag = true;
            if (checkLatLong(lat, lon,   7,  10,  92, 420)) olErrorFlag = true;

            if (checkLatLong(lat, lon,  10,  12,  92, 417)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  12,  15,  92, 415)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  15,  17,  95, 415)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  17,  20,  95, 410)) olErrorFlag = true;

            if (checkLatLong(lat, lon,  20,  22,  97, 407)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  22,  25, 100, 402)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  25,  27, 102, 400)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  27,  30, 105, 400)) olErrorFlag = true;

            if (checkLatLong(lat, lon,  30,  32, 107, 400)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  32,  35, 110, 397)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  35,  37, 112, 397)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  37,  40, 115, 397)) olErrorFlag = true;

            if (checkLatLong(lat, lon,  40,  42, 117, 395)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  42,  45, 120, 395)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  45,  47, 120, 392)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  47,  50, 122, 390)) olErrorFlag = true;

            if (checkLatLong(lat, lon,  50,  52, 125, 390)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  52,  55, 125, 390)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  55,  57, 125, 387)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  57,  60, 127, 387)) olErrorFlag = true;

            if (checkLatLong(lat, lon,  60,  62, 127, 387)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  62,  62, 127, 387)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  65,  65, 130, 390)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  67,  70, 132, 392)) olErrorFlag = true;

            if (checkLatLong(lat, lon,  70,  72, 132, 392)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  72,  75, 132, 392)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  75,  77, 132, 392)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  77,  80, 135, 392)) olErrorFlag = true;

            if (checkLatLong(lat, lon,  80,  82, 135, 392)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  82,  85, 135, 392)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  85,  87, 135, 392)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  87,  90, 135, 392)) olErrorFlag = true;

            if (checkLatLong(lat, lon,  90,  92, 135, 395)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  92,  95, 135, 395)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  95,  97, 135, 395)) olErrorFlag = true;
            if (checkLatLong(lat, lon,  97, 100, 135, 395)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 100, 102, 137, 397)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 102, 105, 140, 400)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 105, 107, 140, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 107, 110, 140, 405)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 110, 112, 140, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 112, 115, 140, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 115, 117, 140, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 117, 120, 140, 405)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 120, 122, 140, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 122, 125, 140, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 125, 127, 137, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 127, 130, 132, 402)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 130, 132, 132, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 132, 135, 130, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 135, 137, 127, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 137, 140, 122, 405)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 140, 142, 127, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 142, 145, 125, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 145, 147, 125, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 147, 150, 125, 405)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 150, 152, 125, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 152, 155, 122, 405)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 155, 157, 122, 402)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 157, 160, 120, 400)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 160, 162, 120, 395)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 162, 165, 120, 395)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 165, 167, 120, 392)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 167, 170, 120, 390)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 170, 172, 120, 380)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 172, 175, 120, 375)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 175, 177, 120, 370)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 177, 180, 120, 367)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 180, 182, 122, 365)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 182, 185, 122, 365)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 185, 187, 125, 360)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 187, 190, 127, 357)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 190, 192, 127, 355)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 192, 195, 130, 352)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 195, 197, 130, 350)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 197, 200, 132, 347)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 200, 202, 135, 345)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 202, 205, 135, 345)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 205, 207, 137, 345)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 207, 210, 137, 350)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 210, 212, 140, 350)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 212, 215, 140, 350)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 215, 217, 142, 352)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 217, 220, 145, 352)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 220, 222, 145, 352)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 222, 225, 147, 355)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 225, 227, 147, 355)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 227, 230, 147, 355)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 230, 232, 147, 355)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 232, 235, 147, 352)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 235, 237, 147, 352)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 237, 240, 147, 352)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 240, 242, 147, 352)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 242, 245, 147, 352)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 245, 247, 150, 347)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 247, 250, 150, 342)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 250, 252, 150, 332)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 252, 255, 150, 327)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 255, 257, 150, 327)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 257, 260, 152, 325)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 260, 262, 152, 325)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 262, 265, 152, 327)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 265, 267, 152, 327)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 267, 270, 155, 327)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 270, 272, 155, 327)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 272, 275, 155, 325)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 275, 277, 157, 325)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 277, 280, 160, 325)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 280, 282, 162, 325)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 282, 285, 167, 322)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 285, 287, 167, 320)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 287, 290, 170, 315)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 290, 292, 170, 312)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 292, 295, 172, 312)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 295, 297, 172, 310)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 297, 300, 172, 307)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 300, 302, 175, 307)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 302, 305, 175, 305)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 305, 307, 177, 302)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 307, 310, 177, 302)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 310, 312, 180, 300)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 312, 315, 182, 295)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 315, 317, 182, 292)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 317, 320, 185, 290)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 320, 322, 185, 287)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 322, 325, 185, 285)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 325, 327, 185, 282)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 327, 330, 182, 280)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 330, 332, 182, 275)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 332, 335, 185, 270)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 335, 337, 185, 257)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 337, 337, 187, 232)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 337, 340, 240, 250)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 340, 342, 190, 217)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 342, 345, 195, 202)) olErrorFlag = true;

            */

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
            /*
            if (checkLatLong(lat, lon, 122, 125, 492, 495)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 125, 127, 492, 495)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 127, 130, 492, 497)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 130, 132, 492, 497)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 132, 135, 490, 500)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 135, 137, 485, 500)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 137, 140, 485, 500)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 140, 142, 482, 500)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 142, 145, 482, 502)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 145, 147, 482, 502)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 147, 150, 477, 502)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 150, 152, 472, 502)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 152, 155, 475, 497)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 155, 157, 475, 497)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 157, 160, 467, 497)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 160, 162, 457, 462)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 160, 162, 467, 497)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 162, 165, 447, 497)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 165, 167, 445, 497)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 167, 170, 445, 495)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 170, 172, 445, 495)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 172, 175, 442, 495)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 175, 177, 442, 495)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 177, 180, 442, 492)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 180, 182, 442, 492)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 182, 185, 442, 492)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 185, 187, 445, 490)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 187, 190, 445, 490)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 190, 192, 445, 490)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 192, 195, 447, 487)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 195, 197, 447, 487)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 197, 200, 447, 487)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 200, 202, 445, 485)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 202, 205, 445, 485)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 205, 207, 442, 482)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 207, 210, 440, 482)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 210, 212, 440, 482)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 212, 215, 440, 482)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 215, 217, 435, 480)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 217, 220, 435, 480)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 220, 222, 435, 477)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 222, 225, 435, 477)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 225, 227, 435, 477)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 227, 230, 435, 477)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 230, 232, 437, 475)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 232, 235, 437, 475)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 235, 237, 437, 475)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 237, 240, 437, 475)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 240, 242, 437, 472)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 242, 245, 440, 472)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 245, 247, 440, 470)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 247, 250, 442, 467)) olErrorFlag = true;

            if (checkLatLong(lat, lon, 250, 252, 447, 457)) olErrorFlag = true;
            if (checkLatLong(lat, lon, 252, 255, 450, 455)) olErrorFlag = true;
            */

            if (olErrorFlag) {
                setReadErrorFlags("Lat/Lon", latin + "/" + longin, "Over Madagascar");
            } // if (olErrorFlag == 4)

        } // if (intQ == 7)

     } // testOverLand

    /**
     * write record stats
     */
    void recordStats(VosTable data) {
        ec.writeFileLine(ofile2, ec.frm(recordCount,5) + " " +
            data.getDateTime("").substring(0,16) + " " +
            ec.frm(data.getLatitude(),9,5) +
            ec.frm(data.getLongitude(),10,5) + " " +
            ec.frm(data.getCallsign(),6) + " " + dBase + " " +
            loadStat
            +" Debug "+
            ec.frm(SurfaceTemp.toString(),10)
            //+" "+ec.frm(SurfaceTempType.toString(),4)
            +" getSurfaceTemp Val (loadfile) )"+
            ec.frm(data.getSurfaceTemperature(),10,4)
            +" temp db val "+data.getSurfaceTemperature()  );

         //ec.writeFileLine(dbgf,
         //    " recordStats getSurfaceTemp Val (loadfile) )"+
         //    ec.frm(data.getSurfaceTemperature(),10,6)
         //    +" temp db val "+data.getSurfaceTemperature() );

    } // recordStats

    void recordStats() {
        ec.writeFileLine(ofile2, ec.frm(recordCount,5) + " " + loadStat);
    } // recordStats

    /**
     * write daily stats
     */
    void dailyStats() {
        ec.writeFileLine(ofile3, ec.frm(recordCount,5) +
            ec.frm(loadCount,6) + " " + prevTime.toString().substring(0,10) +
            ec.frm(dayCount,5));
        dayCount = 0;
    } // dailyStats


    /**
     * write closing stats
     * @param  ofile    RandomAccessFile to write to
     */
    void closeStats(RandomAccessFile ofile) {
        ec.writeFileLine(ofile, "");
        ec.writeFileLine(ofile, "---------------------------------------------");
        ec.writeFileLine(ofile, "Closing statistics - loadid: " +  vosLoadId);
        ec.writeFileLine(ofile, "");
        ec.writeFileLine(ofile, "  Number of records - " + ec.frm(recordCount,6));
        ec.writeFileLine(ofile, "       Fatal errors - " + ec.frm(fatalCount,6));
        ec.writeFileLine(ofile, "    Records skipped - " + ec.frm(skipCount,6));
        ec.writeFileLine(ofile, "   Records rejected - " + ec.frm(rejectCount,6));
        ec.writeFileLine(ofile, "(Records that contained invalid range checks)");
        ec.writeFileLine(ofile, "---------------------------------------------");
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
        ec.writeFileLine(ofile, "---------------------------------------------");

    } // closeStats


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
        ec.writeFileLine(errorStatus, ec.frm(recordCount,5) + ": " +
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

        if (dbg) System.out.println("<br>Within updateLogVOSLoads: vosLoadId = " + vosLoadId);
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
        if (dbg) System.out.println("putting record in updateLogVOSLoads");
        logVosLoads.setRespPerson(respPerson);

        // Keep count of number records rejected this is records
        // with invalid range checks.
        logVosLoads.setRecsRejected(rejectCount);
        if (dbg) System.out.println("<br>loadId = " + loadId);

        if (doLoad) {
//            logVosLoads.put();
        } // if (doLoad)

        if (dbg) System.out.println(logVosLoads);
    } // updateLogVOSLoads

} // class LoadVOSDataUpd
