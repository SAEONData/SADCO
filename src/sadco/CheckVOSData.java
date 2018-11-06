package sadco;

import java.io.*;
import java.sql.*;
import java.util.*; // for Calendar
import oracle.html.*;


/**
 * Does the actual loading of the VOS data.
 *
 * SadcoVOS
 * screen.equals("load")
 * version.equals("doload")
 *
 * @author  001123 - SIT Group
 * @version
 * 001123 - Ursula von St Ange - create class<br>
 * 020528 - Ursula von St Ange - update for non-frame menus<br>
 * 020528 - Ursula von St Ange - update for exception catching<br>
 */
public class CheckVOSData extends CompoundItem {


    /** some common functions */
    static common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    //sadco.SadConstants sc = new sadco.SadConstants();
    SadConstants sc = new SadConstants();

    //boolean dbg = false;
    boolean dbg = true;
    String thisClass = this.getClass().getName();

    // arguments
    String vosLoadId = "";
    String loadId = "";
    String source = "";
    String respPerson = "";
    String fileName = "";
    boolean doLoad = false;

    String latNorth = "";
    String latSouth = "";
    String longWest = "";
    String lonEast  = "";

    float latitude  = 0;
    float longitude = 0;

    Timestamp dateTime;
    String callSign = "";

    String startLat = "";
    String endLat   = "";
    String incr     = "";
    String database = "";

    // file stuff
    String      pathName;
    RandomAccessFile lfile;
    RandomAccessFile ofile1;
    RandomAccessFile ofile2;
    RandomAccessFile ofile3;
    RandomAccessFile warningFile;
    RandomAccessFile invalidRecs;
    RandomAccessFile dbgf;
    String      line;


    // the data
    /* Change to VosMain */
    //VosTable data;
    VosMain data;


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
    boolean     readErrorFlag  = false;  // date, lat, lon, q, overland testing.
    boolean     tempErrorFlag  = false;  // data testing.
    boolean     errorFlag      = false;  // all errors.
    boolean     nonNumericFlag = false;  // to catch the nonNumeric Fields.
    boolean     rejected       = false;  // flag for writting of rejected data.
    boolean     updateVosMain  = false;  // flag for updating vos_main values.

    // working variables
    int         recordCount  = 0;
    int         loadCount    = 0;
    int         dayCount     = 0;
    int         fatalCount   = 0;
    int         mainDupCount = 0;
    int         archDupCount = 0;
    int         mainCount    = 0;
    int         archCount    = 0;
    int         skipCount    = 0;
    int         rejectCount = 0;
    String      loadStat = "";
    Timestamp   currTime;
    Timestamp   prevTime = Timestamp.valueOf("1850-01-01 00:00:00.0");
    java.util.Date  dateStart = Timestamp.valueOf("2050-01-01 00:00:00.0");
    java.util.Date  dateEnd   = Timestamp.valueOf("1850-01-01 00:00:00.0");

    String vosMaincolums = "";
    String vosMainWhere  = "";

    int         EVAL = -9999999;

    public CheckVOSData(String args[]) {

        // get the argument values
        getArgParms(args);

        //if (dbg) System.out.println("<br>after getArgParms ");
        pathName = "d:/myfiles/java/data/vos/";

        // delete the old the dummy file
        String dummyFile = pathName + fileName + ".finished";
        ec.deleteOldFile(dummyFile);


        try {

            int sLat = new Integer(startLat).intValue();
            int eLat = new Integer(endLat).intValue();
            float incrVal = new Float(incr).floatValue();

            String fileName = "LatBand"+sLat+"_to_"+eLat;
            String of1 = fileName+ ".warning";
            ec.deleteOldFile(pathName+of1);

            // Open file with error report.
            try {
                 warningFile = new RandomAccessFile(pathName+of1,"rw");
            } catch (Exception e) {
                if (dbg) System.out.println("<br>" + thisClass + "open files error: " +
                    e.getMessage());
                e.printStackTrace();
            } // try .. catch

            if (dbg) System.out.println("<br> before latitude");
            if (dbg) System.out.println("<br> before latitude"+sLat);
            if (dbg) System.out.println("<br> before latitude"+eLat);
            if (dbg) System.out.println("<br> before latitude"+incrVal);

            for (float lower = sLat; lower < eLat; lower+=incrVal) {

                if (dbg) System.out.println("<br> in for latitude");

                float upper = lower+incrVal-0.00001f;
                //float upper = 75;

                if (dbg) System.out.println("<br> lower"+lower);
                if (dbg) System.out.println("<br> upper"+upper);

                // add pk values.

                // longitude

                //for (float lowerlong = -35;lowerlong < 70;lowerlong+=incrVal) { // total range
                for (float lowerlong = 0;lowerlong < 0.5 ;lowerlong+=incrVal) {
                //for (float lowerlong = 2;lowerlong < 2.5 ;lowerlong+=incrVal) {

                    float upperlong = lowerlong+incrVal-0.00001f;
                    //float upperlong = 70;

                    vosMaincolums = "LATITUDE, LONGITUDE, DATE_TIME, "+
                        "CALLSIGN, LOAD_ID, ATMOSPHERIC_PRESSURE, "+
                        "SURFACE_TEMPERATURE, DRYBULB, WETBULB, "+
                        "DEWPOINT, SWELL_DIRECTION, SWELL_HEIGHT, "+
                        "SWELL_PERIOD, WAVE_HEIGHT, WAVE_PERIOD, "+
                        "WIND_DIRECTION, WIND_SPEED";


                    /*

                    // setting up the where clause for vos_main or vos_arch.
                    if (database.equals("main")) {

                        vosMainWhere = VosMain.LATITUDE+" between "+lower
                            +" and "+upper+" and "+VosMain.LONGITUDE+
                            " between "+lowerlong+" and "+upperlong;

                    } else {
                        vosArchWhere = VosArch.LATITUDE+" between "+lower
                            +" and "+upper+" and "+VosArch.LONGITUDE+
                            " between "+lowerlong+" and "+upperlong;
                    }

                    */

                    vosMainWhere =
                        VosMain.LATITUDE+" between "+lower +" and "+
                        upper+" and "+VosMain.LONGITUDE+
                        " between "+lowerlong+" and "+upperlong;

                    System.out.println("<br> vosMaincolums "+vosMaincolums);
                    System.out.println("<br> vosMainWhere "+vosMainWhere);


                    String order   = "";
                    VosMain main   = new VosMain();
                    VosMain recs[] = main.get(vosMaincolums,vosMainWhere,order);

                    int arraySize = recs.length;
                    System.out.println("<br>** tot recs "+arraySize);

                    String sql = main.getSelStr();
                    System.out.println("<br>** sql "+sql);

                    // loop to set Vos_Main invalid values to null and corresponding
                    // field in vos reject to invalid value.

                    for (int i= 0;i < recs.length; i++) {

                        recordCount++;

                        if (dbg) System.out.println("<br> Val of i  "+i);
                        if (dbg) System.out.println("<br> latitude  "+recs[i].getLatitude());
                        if (dbg) System.out.println("<br> longitude "+recs[i].getLongitude());

                        if (dbg) System.out.println("<br>*** recs "+ recs[i] );

                        rejected      = false;
                        updateVosMain = false;

                        int longIndex = 0;
                        if (recs[i].getLongitude() > 25) {
                            longIndex = 1;
                        } //if (recs[i].getLongitude() > 25) {

                        int latIndex  = ((int)recs[i].getLatitude()+ 10) / 5;
                        if (dbg) System.out.println("<br> @@latIndex "+latIndex);

                        VosReject reject = new VosReject();
                        boolean invalidValue = false;

                        int testno, testno2;
                        float ftestno, ftestno2;
                        int itestno;

                        //String position = (getValue(latinS + latin) / 10f) + "S/"+
                        //    (getValue(loninS + longin) / 10f) + "W";

                        String position = recs[i].getLatitude() + " S " +
                            recs[i].getLatitude() +" W";

                        // get...Records vir reject velde......
                        int loadId = 0;
                        if (recs[i].getLoadId() != VosMain.INTNULL) {
                            // rejected = true; ub
                            loadId = recs[i].getLoadId();
                        } //if (recs[i].getLoadId()) {

                        // indexes for range checking
                        String nullVal = null;

                        data = new VosMain();

                        // SEA TEMPERATURE - Check if valid
                        if (recs[i].getSurfaceTemperature() != VosMain.FLOATNULL) {

                            int[][][] seaTemp = {
                                { {18,37},{19,37},{16,37},{12,36},{8 ,33},{7 ,33},{7 ,33},{7 ,33},{6 ,33}, //west
                                  {5 ,33},{-2,28},{-2,20},{-2,18},{-2,16},{-2,14},{-2, 8},{-2, 8},{-2, 8} },
                                { {15,37},{18,36},{20,36},{19,37},{19,37},{19,37},{15,36},{13,35},{6 ,34}, //east
                                  {7 ,30},{-2,28},{-2,20},{-2,18},{-2,16},{-2,14},{-2, 8},{-2, 8},{-2, 8} }};

                            testTempErrorValue3(recs[i].getSurfaceTemperature(),
                                seaTemp[longIndex][latIndex], "SST    ",
                                "Invalid Sea Temperature CSIR Limits ("+position+")");

                            if (dbg) System.out.println("<br> ######### ");
                            if (dbg) System.out.println("<br> SST Value "+recs[i].getSurfaceTemperature());
                            if (dbg) System.out.println("<br> CSIR Limits ");//+seaTemp[0][0]);
                            if (dbg) System.out.println(" seaTemp[longIndex][latIndex][0] = "+seaTemp[longIndex][latIndex][0]);
                            if (dbg) System.out.println(" seaTemp[longIndex][latIndex][1] = "+seaTemp[longIndex][latIndex][1]);

                            if (tempErrorFlag) {

                                // test value according to wmo limits.
                                int[][][] seaTemp2 = {{{-2,37}}};
                                testTempErrorValue3(recs[i].getSurfaceTemperature(),
                                    seaTemp2[0][0], "SST   ",
                                    "Invalid Sea Temperature WMO Limits ("+position+")");

                                if (!tempErrorFlag) {

                                    // put PK plus seaTemp2 into vos_reject table.
                                    rejected = true;
                                    reject.setSurfaceTemperature(recs[i].getSurfaceTemperature());

                                    if (dbg) System.out.println("<br> WMO limits ");//+seaTemp2[0][0]);
                                    if (dbg) System.out.println(" seaTemp2[0][0][0] = "+seaTemp2[0][0][0]);
                                    if (dbg) System.out.println(" seaTemp2[0][0][1] = "+seaTemp2[0][0][1]);

                                    if (dbg) System.out.println("<br> add to vos_reject "+
                                        recs[i].getSurfaceTemperature()+" plus rec Pk.");
                                } else {
                                    updateVosMain=true;
                                    //Update vos_main setting recs[i].getSurfaceTemperature with FLOATNULL
                                    data.setSurfaceTemperature(VosMain.FLOATNULL2);
                                    // No entries made to vos_reject table.

                                    if (dbg) System.out.println("<br> WMO limits ");//+seaTemp2[0][0]);
                                    if (dbg) System.out.println("<br> seaTemp2[0][0][0] = "+seaTemp2[0][0][0]);
                                    if (dbg) System.out.println("<br> seaTemp2[0][0][1] = "+seaTemp2[0][0][1]);
                                    if (dbg) System.out.println("<br> Update vos_main SurfaceTemp = "+
                                        recs[i].getSurfaceTemperature()+" with Floatnull2.");
                                    if (dbg) System.out.println("<br> Value of Floatnull2 "+VosMain.FLOATNULL2);

                                } //if (!tempErrorFlag) {

                            } //if (tempErrorFlag) {

                         } //if (recs[i].getSurfaceTempreature() != VosMain.FLOATNULL) {

                        boolean valid_drybulb = false;
                        float drybCheck  = EVAL;

                        // DRYBULB
                        if (recs[i].getDrybulb() != VosMain.FLOATNULL) {

                            int[][][] drybulb = {
                                { {19,38},{19,37},{16,38},{12,40},{7 ,33},{7 ,33},{7 ,33},{7 ,33},{2 ,30}, //west
                                  {0 ,28},{-10,27},{-15,25},{-15,17},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} },
                                { {17,37},{19,35},{19,35},{19,37},{19,37},{14,36},{13,37},{9 ,35},{5 ,33},{2 ,30}, //east
                                  {-10, 25},{-15, 25},{-15, 17},{-19, 17},{-25, 15},{-25, 15},{-25, 15},{-25, 15} }};

                            testTempErrorValue3(recs[i].getDrybulb(),
                                drybulb[longIndex][latIndex], "AT   ",
                                "Invalid Air Temperature CSIR Limits ("+position+")");

                            drybCheck = recs[i].getDrybulb();
                            if (dbg) System.out.println("<br><br> drybCheck "+drybCheck);
                            if (dbg) System.out.println("<br> drybulb[0][0][0] "+drybulb[0][0][0]);
                            if (dbg) System.out.println("<br> drybulb[0][0][1] "+drybulb[0][0][1]);

                            if (dbg) System.out.println("<br> before ");
                            if (dbg) System.out.println("<br> drybCheck "+drybCheck);


                            if (tempErrorFlag) {

                                int[][][] drybulb2 = {{{-25,40}}};
                                testTempErrorValue3(recs[i].getDrybulb(),
                                    drybulb2[0][0], "AT   ",
                                    "Invalid air temperature WMO Limits ("+position+")");

                                if (!tempErrorFlag) { // not failed
                                    if (dbg) System.out.println("<br> WMO limits ");//+seaTemp2[0][0]);
                                    if (dbg) System.out.println(" seaTemp2[0][0][0] = "+drybulb2[0][0][0]);
                                    if (dbg) System.out.println(" seaTemp2[0][0][1] = "+drybulb2[0][0][1]);

                                    if (dbg) System.out.println("<br> add to vos_reject "+
                                        recs[i].getSurfaceTemperature()+" plus rec Pk.");

                                    // put PK plus drybulb2 into vos_reject table.
                                    rejected = true;
                                    reject.setDrybulb(recs[i].getDrybulb());

                                    if (dbg) System.out.println("<br> recs[i].getDrybulb()"+
                                        recs[i].getDrybulb());
                                } //else {

                                    updateVosMain=true;
                                    //Update vos_main setting recs[i].recs[i].getDrybulb() with FLOATNULL
                                    data.setDrybulb(VosMain.FLOATNULL2);

                                    // No entries made to vos_reject table.
                                    //if (dbg) System.out.println("<br> recs[i].getDrybulb()"+
                                    //    recs[i].getDrybulb());

                                //} //if (!tempErrorFlag) {

                            } else {
                                valid_drybulb = true;
                            } // if (tempErrorFlag)

                        } // if (recs[i].getDrybulb() != VosMain.FLOATNULL) {

                        // WETBULB
                        if (recs[i].getWetbulb() != VosMain.FLOATNULL) {

                            //Have to change these latitudes, longitudes,
                            //reason it's drybulb's values.

                            int[][][] wetbulb = {
                                { {16,38},{16,37},{15,38},{12,40},{8 ,33},{4 ,33},{4 ,33},{3 ,33},{2 ,30}, //west
                                  {-1 ,30},{-10,27},{-15,25},{-15,19},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} },
                                { {17,37},{17,35},{17,35},{17,37},{16,37},{13,36},{7 ,37},{6 ,35},{2 ,33}, //east
                                  {0 ,34},{-10,27},{-15,25},{-15,19},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} }};

                            testTempErrorValue3(recs[i].getWetbulb(),
                                wetbulb[longIndex][latIndex], "WT   ",
                                "Invalid wetbulb CSIR Limits ("+position+")");

                            if (tempErrorFlag) {

                                int[][][] wetbulb2 = {{{-25,40}}};
                                testTempErrorValue3(recs[i].getWetbulb(), wetbulb2[0][0],
                                        "WT   ", "Invalid wetbulb WMO Limits ("+position+")");

                                if (!tempErrorFlag) { //not failed

                                    // put PK plus drybulb2 into vos_reject table.
                                    rejected = true;
                                    reject.setWetbulb(recs[i].getWetbulb());

                                    if (dbg) System.out.println("<br> recs[i].getDrybulb()"+
                                        recs[i].getWetbulb());

                                } //else {
                                    updateVosMain=true;
                                    //Update vos_main setting recs[i].recs[i].getWetbulb() with FLOATNULL
                                    data.setWetbulb(VosMain.FLOATNULL2);

                                    // No entries made to vos_reject table.
                                    if (dbg) System.out.println("<br> recs[i].getWetbulb()"+
                                        recs[i].getWetbulb());

                                //} //if (!tempErrorFlag) {
                             } //if (tempErrorFlag) {

                        } // if (recs[i].getWetbulb() != VosMain.FLOATNULL) {

                        float dewptCheck = EVAL;

                        // DEW-POINT TEMPERATURE - Check if valid
                        if (recs[i].getDewpoint() != VosMain.FLOATNULL) {

                            int[][][] dewpoint = {
                                { {16,38},{14,37},{12,38},{10,40},{7 ,33},{3 ,33},{0 ,33},{0 ,33},{-3,30},  //west
                                  {-7,28},{-10,27},{-15,25},{-15,19},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} },
                                { {16,37},{16,35},{14,35},{14,37},{11,37},{9 ,36},{3 ,37},{2 ,35},{-2,33},  //east
                                  {-5,30},{-10,27},{-15,25},{-15,19},{-18,17},{-25,15},{-25,15},{-25,15},{-25,15} }};

                            testTempErrorValue3(recs[i].getDewpoint(),
                                dewpoint[longIndex][latIndex], "DW   ",
                                "Invalid dew-point temperature CSIR Limits ("+position+")");

                            dewptCheck = recs[i].getDewpoint();

                            if (dbg) System.out.println("<br> dewpoint Check : dewptCheck < drybCheck ");
                            if (dbg) System.out.println("<br> dewptCheck "+dewptCheck);
                            if (dbg) System.out.println("<br> dewpoint[0][0][0] "+dewpoint[0][0][0]);
                            if (dbg) System.out.println("<br> dewpoint[0][0][1] "+dewpoint[0][0][1]);
                                               if (dbg) System.out.println("<br><br> valid_drybulb "+valid_drybulb);
                            if (dbg) System.out.println("<br> tempErrorFlag "+tempErrorFlag);

                            if (tempErrorFlag) { // tempErroFlag = failed

                                int[][][] dewpoint2 = {{{-25,40}}};
                                testTempErrorValue3(recs[i].getDewpoint(),
                                    dewpoint2[0][0], "DW   ",
                                    "Invalid dew-point temperature WMO Limits ("+position+")");

                                if (dbg) System.out.println("<br> dewptCheck "+dewptCheck);
                                if (dbg) System.out.println("<br> drybCheck "+drybCheck);
                                if (dbg) System.out.println("<br> valid_drybulb "+valid_drybulb);


                                if (!tempErrorFlag) {
                                    // test for valid ttt
                                    // put PK plus drybulb2 into vos_reject table.
                                    rejected = true;
                                    reject.setDewpoint(recs[i].getDewpoint());

                                } //if (!tempErrorFlag) {

                                updateVosMain=true;
                                data.setWetbulb(VosMain.FLOATNULL2);

                            } else {

                                if (valid_drybulb) {
                                    if ( dewptCheck > drybCheck) {
                                        // put PK plus drybulb2 into vos_reject table.
                                        rejected = true;

                                        updateVosMain=true;
                                        //data.setWetbulb(VosMain.FLOATNULL2);  ub
                                        data.setDewpoint(VosMain.FLOATNULL2);

                                        reject.setDewpoint(recs[i].getDewpoint());

                                        if (dbg) System.out.println("<br> reject: dewptCheck > drybCheck "+
                                            dewptCheck+" > "+drybCheck);
                                        setTempErrorFlags("DW     ", recs[i].getDewpoint(),
                                            "Invalid dew-point temperature dewpt > dryb: "+drybCheck+") ("+position+") ");

                                    } // if ( dewptCheck > drybCheck) {

                                } //if (valid_drybulb) {

                            } //if (tempErrorFlag) {

                        } //if (recs[i].getDrybulb() != VosMain.FLOATNULL) {

                        // SEA-LEVEL PRESSURE - convert to a numeric value, but the
                        // atmopheric pressure
                        if (recs[i].getAtmosphericPressure() != VosMain.FLOATNULL) {

                            double[][][] atmosPress = {{{930,1050}}};

                            testTempErrorValue4(recs[i].getAtmosphericPressure(),
                                atmosPress[0][0], "SLP  ",
                                "Invalid sea-level pressure WMO Limits ("+position+")");

                            if (tempErrorFlag) {
                               updateVosMain=true;
                               data.setAtmosphericPressure(VosMain.FLOATNULL2);
                            } //if (tempErrorFlag) {
                        } //if (recs[i].getAtmosphericPressure() != VosMain.FLOATNULL)

                        // SWELL Height - Check if valid
                        //int[][][] swHeight = {{{0,35}}};
                        if (recs[i].getSwellHeight() != VosMain.FLOATNULL) {

                            double[][][] swHeight = {{{0 ,17.5}}};

                            testTempErrorValue4((int)recs[i].getSwellHeight(),
                                swHeight[0][0], "SW1H ",
                                "Invalid swell height WMO Limits ("+position+")");

                            if (tempErrorFlag) {
                               updateVosMain=true;
                               data.setSwellHeight(VosMain.FLOATNULL2);
                            } //if (tempErrorFlag) {
                        } // if (recs[i].getSwellHeight() != VosMain.FLOATNULL)

                        // SWELL PERIOD - Check if valid
                        if (recs[i].getSwellPeriod() != VosMain.INTNULL) {

                            int[][][] swPeriod = {{{0 ,25}}};

                            testTempErrorValue2((int)recs[i].getSwellPeriod(),
                                swPeriod[0][0], "SW1P ",
                                "Invalid swell period WMO Limits ("+position+")");

                            if (tempErrorFlag) {
                               updateVosMain=true;
                               data.setSwellPeriod(VosMain.INTNULL2);
                            } //if (tempErrorFlag) {
                        }


                        // WAVE PERIOD - Check if valid
                        if (recs[i].getWavePeriod() != VosMain.INTNULL) {

                            int[][][] wvPeriod = {{{0, 20}}};

                            testTempErrorValue2(recs[i].getWavePeriod(),
                                wvPeriod[0][0], "WWP  ",
                                "Invalid wave period WMO Limits ("+position+")");

                            if (tempErrorFlag) { // failed
                               updateVosMain=true;
                               data.setWavePeriod(VosMain.INTNULL2);
                            } //if (tempErrorFlag) {

                        } //if (recs[i].getWavePeriod() != VosMain.INTNULL) {

                        // WAVE HEIGHT - Check if valid
                        if (recs[i].getWaveHeight() != VosMain.FLOATNULL) {

                            double[][][] wvHeight = {{{0 ,17.5}}};

                            testTempErrorValue4(recs[i].getWaveHeight(),
                                wvHeight[0][0], "WWH  ",
                                "Invalid wave height WMO Limits ("+position+")");

                            if (tempErrorFlag) { // failed
                                updateVosMain=true;
                                data.setWaveHeight(VosMain.FLOATNULL2);
                            } //if (waveHeight[i] < 0) || (waveHeight[i] > 35)) {
                        }

                        // WIND SPEED - Check if valid
                        if (recs[i].getWindSpeed() != VosMain.FLOATNULL) {

                            double[][][] wndSpeed = {{{0,40}}};

                            testTempErrorValue4(recs[i].getWindSpeed(),
                                wndSpeed[0][0], "WSP  ",
                                "Invalid wind speed WMO Limits ("+position+")");

                            if (tempErrorFlag) {
                                updateVosMain=true;
                                data.setWindSpeed(VosMain.FLOATNULL2);
                            } //if (tempErrorFlag) {

                        } //if (recs[i].getWindSpeed() != VosMain.FLOATNULL) {

                        boolean success = false;
                        // Write records to VosReject if there where invalid range checks.

                        if (rejected) {
                            reject.setDateTime (recs[i].getDateTime());
                            reject.setLatitude (recs[i].getLatitude());
                            reject.setLongitude(recs[i].getLongitude());
                            reject.setCallsign (recs[i].getCallsign());
                            reject.setLoadId (loadId);

                            //SAWS
                            reject.setDataId("WB");
                            reject.setQualityControl("9");
                            reject.setSource1("2");

                            reject.put();
                            rejectCount++;
                            if (dbg) if (dbg) System.out.println("rejectCount equals :"+rejectCount);
                            if (dbg) System.out.println("<br> reject "+reject);

                        } //if (rejected) {


                        String where =
                            VosMain.DATE_TIME +" = "+
                            Tables.getDateFormat(recs[i].getDateTime())
                            +" and "+ VosMain.LATITUDE  +" = "+
                            recs[i].getLatitude() +" and "+
                            VosMain.LONGITUDE +" = "+
                            recs[i].getLongitude()+" and "+
                            VosMain.CALLSIGN  +" = '"+recs[i].getCallsign()+"'";

                       System.out.println("<br> Update: where "+where);
                       System.out.println("<br> data: "+data);

                       if (updateVosMain) {

                            // Update of VosMain.
                            //VosMain updVosMain = new VosMain();
                            //VosMain vosMainWhere = new VosMain();

                            if (dbg) System.out.println("<br> Update rec with null where = "+where);
                            success = new VosMain().upd(data,where); //use this one

                            // set PK values for invalidValue field with invalid range checks
                            if (dbg) System.out.println("<br> data "+data);
                       } // if (updateVosMain)

                    } //for (int i= 0;i < recs.length; i++) {
                } // for (float lowerlong = ....
            } // for (float lower = sLat; lower < eLat; lower+=incrVal) {


        } catch (Exception e) {
            if (dbg) System.out.println("<br> error ");
            if (dbg) System.out.println("<br>" + thisClass + "open files error: " +
                e.getMessage());
            e.printStackTrace();
        } // try .. catch

        try {
            warningFile.close();
        } catch (Exception e) {
            if (dbg) System.out.println("<br>" + thisClass + "close files error: " +
                e.getMessage());
            e.printStackTrace();
        } // try .. catch


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

        //*** args for check prog
        startLat    = ec.getArgument(args,"pstartlat");
        endLat      = ec.getArgument(args,"pendlat");
        incr        = ec.getArgument(args,"pincr");
        database    = ec.getArgument(args,"pdatabase");

        if (dbg) System.out.println("<br>startLat = "+startLat);
        if (dbg) System.out.println("<br>endLat   = "+endLat);
        if (dbg) System.out.println("<br>incr     = "+incr);
        if (dbg) System.out.println("<br>database = "+database);

    }   //  public void getArgParms()


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
    } // testTempErrorValue

    /**
     * test for a valid value, and set errorFlags and write an error message
     * if not valid
     * @param  var    variable to be tested (String)
     * @param  minmax minimum and maximum value (int)
     * @param  txt1   first part of error message (String)
     * @param  txt2   first part of error message (String)
     * @return value  value of variable, or -1 of invalid
     */
    int testTempErrorValue2 (int var, int[] minmax, String txt1, String txt2) {
    //int testTempErrorValue2 (String var, int[] minmax, String txt1, String txt2) {
        tempErrorFlag = false;
        //int value = getValue(var);
        int value = var;
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

        //if (dbg) System.out.println("<br>In testTempErrorValue3 ");
        //if (dbg) System.out.println("<br>value "+value);
        //if (dbg) System.out.println("<br>minmax "+minmax[i]);
        //if (dbg) System.out.println("<br>txt1 "+txt1);
        //if (dbg) System.out.println("<br>txt2 "+txt2);

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

        //if (dbg) System.out.println("<br>In testTempErrorValue3 ");
        //if (dbg) System.out.println("<br>value "+value);
        //if (dbg) System.out.println("<br>minmax "+minmax[i]);
        //if (dbg) System.out.println("<br>txt1 "+txt1);
        //if (dbg) System.out.println("<br>txt2 "+txt2);

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
        writeError (ofile1, txt1, txt2, "invalidValue - " + txt3);
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
    } // nullZero0


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
    } // nullZero1

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            CheckVOSData local= new CheckVOSData(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, "CheckVOSData", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class CheckVOSData
