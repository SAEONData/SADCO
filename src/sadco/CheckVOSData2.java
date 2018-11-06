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
 *
 * @author  001123 - SIT Group
 * @version
 * 001123 - Ursula von St Ange - create class<br>
 */
public class CheckVOSData2 extends CompoundItem {


    /** some common functions */
    static common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    //sadco.SadConstants sc = new sadco.SadConstants();
    SadConstants sc = new SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    boolean checkDebug = false;
    //boolean checkDebug = true;

    String thisClass = this.getClass().getName();

    String startLat = "";
    String endLat   = "";
    String incr     = "";
    String database = "";

    // file stuff
    RandomAccessFile ofile1;
    RandomAccessFile ofile3;
    RandomAccessFile warningFile;
    RandomAccessFile dbgf;
    String      line;

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
    //int         fatalCount   = 0;
    //int         mainDupCount = 0;
    //int         archDupCount = 0;
    //int         mainCount    = 0;
    //int         archCount    = 0;
    //int         skipCount    = 0;
    int         rejectCount = 0;

    String loadStat = "";
    //Timestamp currTime;
    Timestamp prevTime = Timestamp.valueOf("1850-01-01 00:00:00.0");
    //java.util.Date dateStart = Timestamp.valueOf("2050-01-01 00:00:00.0");
    //java.util.Date dateEnd   = Timestamp.valueOf("1850-01-01 00:00:00.0");

    String vosColumns    = "";
    String vosWhere      = "";

    int EVAL = -9999999;
    int total = 0;

    public CheckVOSData2(String args[]) {

        // get the argument values
        getArgParms(args);
        String pathName = "d:/myfiles/java/data/vos/";

        try {

            int sLat = (new Integer(startLat).intValue()) * 100000;
            int eLat = new Integer(endLat).intValue() * 100000;
            //float incrVal = int(new Float(incr).floatValue() * 100000);
            int incrVal = (int)(new Float(incr).floatValue() * 100000);

            if (dbg) System.out.println("incr "+incr);
            //int incrVal = new Integer(incr).intValue();

            String fileName = "LatBand"+sLat+"_to_"+eLat;
            String of1 = database+"_"+fileName+ ".warning";

            ec.deleteOldFile(pathName+of1);

            // Open file with error report.
            try {
                 warningFile = new RandomAccessFile(pathName+of1,"rw");
            } catch (Exception e) {
                if (dbg) System.out.println("<br>" + thisClass + "open files error: " +
                    e.getMessage());
                e.printStackTrace();
            } // try .. catch

            if (dbg) System.out.println("<br> before latitude for loop:");
            if (dbg) System.out.println("<br> before sLat"+sLat);
            if (dbg) System.out.println("<br> before eLat"+eLat);
            if (dbg) System.out.println("<br> before incrVal"+incrVal);

            vosColumns = "LATITUDE, LONGITUDE, DATE_TIME, CALLSIGN, LOAD_ID, "+
                "ATMOSPHERIC_PRESSURE, SURFACE_TEMPERATURE, DRYBULB, WETBULB, "+
                "DEWPOINT, SWELL_DIRECTION, SWELL_HEIGHT, SWELL_PERIOD, "+
                "WAVE_HEIGHT, WAVE_PERIOD, WIND_DIRECTION, WIND_SPEED";

           String order = "";
           total = 0;


           //for (float lower = sLat; lower < eLat; lower+=incrVal) {
             for (int intlower = sLat; intlower < eLat; intlower+=incrVal) {

                if (dbg) System.out.println("<br> in for latitude incrVal = "+incrVal);

                float lower = (float)(intlower) / 100000f;
                //float upper = lower+incrVal-0.00001f;
                float upper = (float)(intlower+incrVal-1);
                upper = upper / 100000f;

                if (dbg) System.out.println("<br> lower"+lower);
                if (dbg) System.out.println("<br> upper"+upper);

                // add pk values.

                float lowerlong = 0;
                float upperlong = 0;


                //float startlong = 0;
                int startlong = 0;
                //float endlong   = 0;
                int endlong   = 0;

                // setting up the where clause for vos_main or vos_arch.
                if (database.equals("main") || database.equals("main2") ) {
                    //startlong = 0;
                    //endlong = 50;
                    //1
                    startlong = 0;
                    endlong = 22;

                    //2
                    //startlong = 22;
                    //endlong = 50;

                } else {
                    startlong = -30;
                    endlong = 0;
                } //if (database.equals("main")) {

                // longitude

                startlong *= 100000;
                endlong *= 100000;

                for (int intlowerlong = startlong;intlowerlong <= endlong ;intlowerlong+=incrVal) {

                    //upperlong = lowerlong+incrVal-0.00001f;

                    //both of type float declaired above
                    lowerlong = (float)(intlowerlong) / 100000f;
                    upperlong = (float)(intlowerlong+incrVal-1);
                    upperlong = upperlong / 100000f;

                    //upperlong = lowerlong+incrVal;


                    if (dbg) System.out.println("<br> in for 1 longitude incrVal = "+incrVal);

                    //**
                    vosWhere = "LATITUDE between "+lower +" and "+
                        upper+" and LONGITUDE "+ " between "+lowerlong+
                        " and "+upperlong;

                    if (dbg) System.out.println("<br> vosColumns "+vosColumns);
                    if (dbg) System.out.println("<br> vosWhere "+vosWhere);

                    /* ... process function....  */
                    if (database.equals("main")) {
                        VosMain main = new VosMain();
                        if (dbg) System.out.println("<br> main.TABLE = "+main.TABLE);
                        process(new VosMain(),main,main.get(vosColumns,vosWhere,order));
                    } else if (database.equals("main2")) {
                        VosMain2 main2 = new VosMain2();
                        process(new VosMain2(),main2,main2.get(vosColumns,vosWhere,order));
                    } else if (database.equals("arch")) {
                        VosArch arch = new VosArch();
                        if (dbg) System.out.println("<br> arch.TABLE = "+arch.TABLE);
                        process(new VosArch(),arch,arch.get(vosColumns,vosWhere,order));
                    } else if (database.equals("arch2")) {
                        VosArch2 arch2 = new VosArch2();
                        process(new VosArch2(),arch2,arch2.get(vosColumns,vosWhere,order));
                    } //

                } // for (float lowerlong = ....

                if ( (database.equals("arch")) || (database.equals("arch2")) ) {


                    startlong = 50;
                    endlong = 70;

                    if (dbg) System.out.println("<br> arch: startlong "+startlong);
                    if (dbg) System.out.println("<br> arch: endlong "+endlong);
                    if (dbg) System.out.println("<br> arch: startlong "+lowerlong);

                    startlong *= 100000;
                    endlong *= 100000;

                    for (int intlowerlong = startlong;intlowerlong < endlong ;intlowerlong+=incrVal) {
                        //upperlong = lowerlong+incrVal-0.00001f;
                        //upperlong = (lowerlong+incrVal) / 100000;

                        lowerlong = (float)(intlowerlong) / 100000f;
                        upperlong = (float)(intlowerlong+incrVal-1) ;
                        upperlong = upperlong / 100000f;

                        if (dbg) System.out.println("endlong "+upperlong);

                        if (dbg) System.out.println("<br> in for 2 longitude incrVal = "+incrVal);

                        vosWhere = "LATITUDE between "+lower
                            +" and "+upper+" and LONGITUDE "+
                            " between "+lowerlong+" and "+upperlong;

                        if (database.equals("arch")) {
                            VosArch arch = new VosArch();
                            process(new VosArch(),arch,
                                arch.get(vosColumns,vosWhere,order));
                        } else if (database.equals("arch2")) {
                            VosArch2 arch2 = new VosArch2();
                            process(new VosArch2(),arch2,
                                arch2.get(vosColumns,vosWhere,order));
                        } // if (database.equals("arch")) {

                    } //for (lowerlong = 50;lowerlong < 70 ;lowerlong+=incrVal) {

                } //if ( (database.equals("arch")) || (database.equals("arch2")) ) {
            } // for (float lower = sLat; lower < eLat; lower+=incrVal) {

            System.out.println("<br> Total records "+total);

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
        //ec.createNewFile(dummyFile);

    } // constructor


    /**
     * process records for vos_main, vos_main2 vos_arch & vos_arch2.
     * table - @parms data VosMain, VosMain, VosArch or VosArch.
     *
     */
    void process(VosTable data,VosTable table, VosTable[] recs) {

        int arraySize = recs.length;

        total += arraySize;

        if (dbg) System.out.println("<br>** tot recs "+arraySize);

        //String sql = table.getSelStr();
        //if (dbg) System.out.println("<br>** sql "+sql);


        // loop to set Vos_Main invalid values to null and corresponding
        // field in vos reject to invalid value.

        for (int i= 0;i < recs.length; i++) {


            recordCount++;
            rejected      = false;
            updateVosMain = false;

            int longIndex = 0;
            if (recs[i].getLongitude() > 25) {
                longIndex = 1;
            } //if (recs[i].getLongitude() > 25) {

            int latIndex  = ((int)recs[i].getLatitude()+ 10) / 5;

            if (latIndex > 17) latIndex = 17;
            if (dbg) System.out.println("<br> @@latIndex "+latIndex);

            VosReject reject = new VosReject();
            boolean invalidValue = false;

            int testno, testno2;
            float ftestno, ftestno2;
            int itestno;

            //String position = (getValue(latinS + latin) / 10f) + "S/"+
            //    (getValue(loninS + longin) / 10f) + "W";

            String position = recs[i].getLatitude() + " S " +
                recs[i].getLongitude() +" W";

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

                testTempErrorValue3(recs[i].getSurfaceTemperature(),seaTemp[longIndex][latIndex], "SST    ",
                    "Invalid Sea Temperature CSIR Limits ("+position+")");

                if (tempErrorFlag) {

                    // test value according to wmo limits.
                    int[][][] seaTemp2 = {{{-2,37}}};
                    testTempErrorValue3(recs[i].getSurfaceTemperature(), seaTemp2[0][0],
                        "SST   ","Invalid Sea Temperature WMO Limits ("+position+")");

                    if (dbg) System.out.println("<br> SST Value "+recs[i].getSurfaceTemperature());
                    if (dbg) System.out.println("<br> CSIR Limits ");//+seaTemp[0][0]);
                    if (dbg) System.out.println(" seaTemp[longIndex][latIndex][0] = "+seaTemp[longIndex][latIndex][0]);
                    if (dbg) System.out.println(" seaTemp[longIndex][latIndex][1] = "+seaTemp[longIndex][latIndex][1]);

                    if (!tempErrorFlag) {

                        // put PK plus seaTemp2 into vos_reject table.
                        rejected = true;
                        reject.setSurfaceTemperature(recs[i].getSurfaceTemperature());


                    } else {

                        if (dbg) System.out.println("<br> WMO limits ");
                        if (dbg) System.out.println("<br> seaTemp2[0][0][0] = "+
                            seaTemp2[0][0][0]);
                        if (dbg) System.out.println("<br> seaTemp2[0][0][1] = "+
                            seaTemp2[0][0][1]);

                    }

                    updateVosMain=true;
                    if (checkDebug) System.out.println("<br> SurfaceTemperature "+recs[i].getSurfaceTemperature());
                    //Update vos_main setting recs[i].getSurfaceTemperature with FLOATNULL
                    data.setSurfaceTemperature(VosMain.FLOATNULL2);
                    // No entries made to vos_reject table.

                    if (dbg) System.out.println("<br> Update vos_main SurfaceTemp = "+
                        recs[i].getSurfaceTemperature()+" with Floatnull2.");
                    if (dbg) System.out.println("<br> Value of Floatnull2 "+
                        VosMain.FLOATNULL2);

                    //} //if (!tempErrorFlag) {
                } //if (tempErrorFlag) {
             } //if (recs[i].getSurfaceTempreature() != VosMain.FLOATNULL) {

            boolean valid_drybulb = false;
            float drybCheck  = EVAL;

            // -- DRYBULB
            if (recs[i].getDrybulb() != VosMain.FLOATNULL) {

                int[][][] drybulb = {
                    { {19,38},{19,37},{16,38},{12,40},{7 ,33},{7 ,33},{7 ,33},{7 ,33},{2 ,30}, //west
                      {0 ,28},{-10,27},{-15,25},{-15,17},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} },
                    { {17,37},{19,35},{19,35},{19,37},{19,37},{14,36},{13,37},{9 ,35},{5 ,33},{2 ,30}, //east
                      {-10, 25},{-15, 25},{-15, 17},{-19, 17},{-25, 15},{-25, 15},{-25, 15},{-25, 15} }};

                testTempErrorValue3(recs[i].getDrybulb(), drybulb[longIndex][latIndex],
                    "AT   ","Invalid Air Temperature CSIR Limits ("+position+")");

                drybCheck = recs[i].getDrybulb();

                if (tempErrorFlag) {

                    int[][][] drybulb2 = {{{-25,40}}};
                        testTempErrorValue3(recs[i].getDrybulb(),drybulb2[0][0], "AT   ",
                            "Invalid air temperature WMO Limits ("+position+")");

                    if (dbg) System.out.println("<br><br> drybCheck "+drybCheck);
                    if (dbg) System.out.println("<br> drybulb[0][0][0] "+
                        drybulb[0][0][0]);
                    if (dbg) System.out.println("<br> drybulb[0][0][1] "+
                        drybulb[0][0][1]);

                    if (!tempErrorFlag) { // not failed

                        // put PK plus drybulb2 into vos_reject table.
                        rejected = true;
                        reject.setDrybulb(recs[i].getDrybulb());

                        if (dbg) System.out.println("<br> recs[i].getDrybulb()"+
                            recs[i].getDrybulb());
                    } else {
                        if (dbg) System.out.println("<br> WMO limits ");
                        if (dbg) System.out.println(" drybulb[0][0][0] = "+
                            drybulb2[0][0][0]);
                        if (dbg) System.out.println(" drybulb[0][0][1] = "+
                            drybulb2[0][0][1]);

                    }

                    if (checkDebug) System.out.println("<br> Drybulb() "+recs[i].getDrybulb());
                    updateVosMain=true;
                    //Update vos_main setting recs[i].recs[i].getDrybulb() with FLOATNULL
                    data.setDrybulb(VosMain.FLOATNULL2);

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

                testTempErrorValue3(recs[i].getWetbulb(),wetbulb[longIndex][latIndex],
                    "WT   ", "Invalid wetbulb CSIR Limits ("+position+")");

                if (tempErrorFlag) {

                    int[][][] wetbulb2 = {{{-25,40}}};
                    testTempErrorValue3(recs[i].getWetbulb(), wetbulb2[0][0],
                            "WT   ", "Invalid wetbulb WMO Limits ("+position+")");

                    if (!tempErrorFlag) { //not failed
                        // put PK plus drybulb2 into vos_reject table.
                        rejected = true;
                        reject.setWetbulb(recs[i].getWetbulb());

                        if (dbg) System.out.println("<br> recs[i].getWetbulb()"+
                            recs[i].getWetbulb());
                    } //if (!tempErrorFlag) { //not failed

                    if (checkDebug) System.out.println("<br> Wetbulb "+recs[i].getWetbulb());
                    updateVosMain=true;
                    // Update vos_main setting recs[i].recs[i].getWetbulb()
                    // with FLOATNULL.
                    data.setWetbulb(VosMain.FLOATNULL2);

                    // No entries made to vos_reject table.
                    if (dbg) System.out.println("<br> recs[i].getWetbulb()"+
                        recs[i].getWetbulb());
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

                testTempErrorValue3(recs[i].getDewpoint(),dewpoint[longIndex][latIndex],
                    "DW   ","Invalid dew-point temperature CSIR Limits ("+position+")");

                dewptCheck = recs[i].getDewpoint();


                if (tempErrorFlag) { // tempErroFlag = failed

                    int[][][] dewpoint2 = {{{-25,40}}};
                    testTempErrorValue3(recs[i].getDewpoint(),dewpoint2[0][0],
                        "DW   ","Invalid dew-point temp. WMO Limits ("+position+")");
                if (dbg) System.out.println("<br> dewptCheck: "+dewptCheck+" drybCheck: "
                    +drybCheck);
                if (dbg) System.out.println("<br> dewpoint[0][0][0] "+
                    dewpoint[0][0][0]);
                if (dbg) System.out.println("<br> dewpoint[0][0][1] "+
                    dewpoint[0][0][1]);


                    if (!tempErrorFlag) { // test for valid ttt

                        // put PK plus drybulb2 into vos_reject table.
                        rejected = true;
                        reject.setDewpoint(recs[i].getDewpoint());
                    } else {


                    }

                    updateVosMain=true;
                    if (checkDebug) System.out.println("<br> Dewpoint "+recs[i].getDewpoint());
                    data.setDewpoint(VosMain.FLOATNULL2);

                } else {

                    if (valid_drybulb) {
                        if (dewptCheck > drybCheck) {
                            // put PK plus drybulb2 into vos_reject table.
                            rejected = true;

                            updateVosMain=true;
                            //data.setWetbulb(VosMain.FLOATNULL2);  ub
                            data.setDewpoint(VosMain.FLOATNULL2);

                            reject.setDewpoint(recs[i].getDewpoint());

                            if (dbg)
                                System.out.println("<br>valid_drybulb "+valid_drybulb);
                            if (dbg)
                                System.out.println("<br>reject:dewptCheck > drybCheck "+
                                    dewptCheck+" > "+drybCheck);
                            setTempErrorFlags("DW     ", recs[i].getDewpoint(),
                                "reject: Invalid dew-point temp. dewpt > dryb: "+
                                    drybCheck+") ("+position+") ");

                        } // if ( dewptCheck > drybCheck) {

                    } //if (valid_drybulb) {

                } //if (tempErrorFlag) {

            } //if (recs[i].getDrybulb() != VosMain.FLOATNULL) {

            // SEA-LEVEL PRESSURE - convert to a numeric value, but the
            // atmopheric pressure
            if (recs[i].getAtmosphericPressure() != VosMain.FLOATNULL) {

                if (dbg)
                    System.out.println(" recs[i].getAtmosphericPressure() = "+
                        recs[i].getAtmosphericPressure());

                if (recs[i].getAtmosphericPressure() < 930) {
                    if (dbg)
                        System.out.println("<br> record: atmospheric < 930  "+recs[i]);
                    if (dbg)
                        System.out.println("<br> atmosphericVal "+recs[i].getAtmosphericPressure());
                }

                if (recs[i].getAtmosphericPressure() > 1050) {
                    if (dbg)
                        System.out.println("<br> record: atmospheric > 1050  "+recs[i]);
                    if (dbg)
                        System.out.println("<br> atmosphericVal "+recs[i].getAtmosphericPressure());
                }


                double[][][] atmosPress = {{{930,1050}}};

                testTempErrorValue4(recs[i].getAtmosphericPressure(),atmosPress[0][0],
                    "SLP  ","Invalid sea-level pressure WMO Limits ("+position+")");

                if (tempErrorFlag) {
                    if (dbg) System.out.println("<br> atmosPress[0][0][0] "+
                        atmosPress[0][0][0]);
                    if (dbg) System.out.println("<br> atmosPress[0][0][1] "+
                        atmosPress[0][0][1]);

                   if (checkDebug) System.out.println("<br> atmosphericVal "+recs[i].getAtmosphericPressure());
                   updateVosMain=true;
                   data.setAtmosphericPressure(VosMain.FLOATNULL2);
                } //if (tempErrorFlag) {
            } //if (recs[i].getAtmosphericPressure() != VosMain.FLOATNULL)

            // SWELL Height - Check if valid
            //int[][][] swHeight = {{{0,35}}};
            if (recs[i].getSwellHeight() != VosMain.FLOATNULL) {

                if (dbg) System.out.println("<br> recs[i].getSwellHeight() = "+
                    recs[i].getSwellHeight());

                double[][][] swHeight = {{{0 ,17.5}}};

                if (dbg) System.out.println("<br> swHeight[0][0][0] "+
                    swHeight[0][0][0]);
                if (dbg) System.out.println("<br> swHeight[0][0][1] "+
                    swHeight[0][0][1]);


                testTempErrorValue4((int)recs[i].getSwellHeight(),swHeight[0][0],
                    "SW1H ","Invalid swell height WMO Limits ("+position+")");

                if (tempErrorFlag) {
                   if (checkDebug) System.out.println("<br> SwellHeight "+recs[i].getSwellHeight());
                   updateVosMain=true;
                   data.setSwellHeight(VosMain.FLOATNULL2);
                } //if (tempErrorFlag) {
            } // if (recs[i].getSwellHeight() != VosMain.FLOATNULL)

            // SWELL PERIOD - Check if valid
            if (recs[i].getSwellPeriod() != VosMain.INTNULL) {

                if (dbg) System.out.println("<br> recs[i].getSwellPeriod() = "+
                    recs[i].getSwellPeriod());

                int[][][] swPeriod = {{{0 ,25}}};

                if (dbg) System.out.println("<br> swPeriod[0][0][0] "+
                    swPeriod[0][0][0]);
                if (dbg) System.out.println("<br> swPeriod[0][0][1] "+
                    swPeriod[0][0][1]);

                testTempErrorValue2((int)recs[i].getSwellPeriod(),swPeriod[0][0],
                    "SW1P ","Invalid swell period WMO Limits ("+position+")");

                if (tempErrorFlag) {
                   if (checkDebug) System.out.println("<br> SwellPeriod "+recs[i].getSwellPeriod());
                   updateVosMain=true;
                   data.setSwellPeriod(VosMain.INTNULL2);
                } //if (tempErrorFlag) {
            }


            // WAVE PERIOD - Check if valid
            if (recs[i].getWavePeriod() != VosMain.INTNULL) {

                if (dbg) System.out.println("<br> recs[i].getWavePeriod() = "+
                    recs[i].getWavePeriod());

                int[][][] wvPeriod = {{{0, 20}}};

                if (dbg) System.out.println("<br> wvPeriod[0][0][0] "+
                    wvPeriod[0][0][0]);
                if (dbg) System.out.println("<br> wvPeriod[0][0][1] "+
                    wvPeriod[0][0][1]);

                testTempErrorValue2(recs[i].getWavePeriod(),wvPeriod[0][0], "WWP  ",
                    "Invalid wave period WMO Limits ("+position+")");

                if (tempErrorFlag) { // failed
                   if (checkDebug) System.out.println("<br> WavePeriod() "+recs[i].getWavePeriod());
                   updateVosMain=true;
                   data.setWavePeriod(VosMain.INTNULL2);
                } //if (tempErrorFlag) {

            } //if (recs[i].getWavePeriod() != VosMain.INTNULL) {

            // WAVE HEIGHT - Check if valid
            if (recs[i].getWaveHeight() != VosMain.FLOATNULL) {

                if (dbg) System.out.println("<br> recs[i].getWaveHeight() "+
                    recs[i].getWaveHeight());

                double[][][] wvHeight = {{{0 ,17.5}}};

                if (dbg) System.out.println("<br> wvHeight[0][0][0] "+
                    wvHeight[0][0][0]);
                if (dbg) System.out.println("<br> wvHeight[0][0][1] "+
                    wvHeight[0][0][1]);

                testTempErrorValue4(recs[i].getWaveHeight(),wvHeight[0][0], "WWH  ",
                    "Invalid wave height WMO Limits ("+position+")");

                if (tempErrorFlag) { // failed
                    if (checkDebug) System.out.println("<br> getWaveHeight "+recs[i].getWaveHeight());
                    updateVosMain=true;
                    data.setWaveHeight(VosMain.FLOATNULL2);
                } //if (waveHeight[i] < 0) || (waveHeight[i] > 35)) {
            }

            // WIND SPEED - Check if valid
            if (recs[i].getWindSpeed() != VosMain.FLOATNULL) {

                if (dbg) System.out.println("<br> recs[i].getWindSpeed() "+
                    recs[i].getWindSpeed());

                double[][][] wndSpeed = {{{0,40}}};

                if (dbg) System.out.println("<br> wndSpeed[0][0][0] "+
                    wndSpeed[0][0][0]);
                if (dbg) System.out.println("<br> wndSpeed[0][0][1] "+
                    wndSpeed[0][0][1]);

                testTempErrorValue4(recs[i].getWindSpeed(),wndSpeed[0][0],
                    "WSP  ", "Invalid wind speed WMO Limits ("+position+")");

                if (tempErrorFlag) {
                    if (checkDebug) System.out.println("<br> getWindSpeed "+recs[i].getWindSpeed());
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
                //reject.setSource1("2");

                reject.put();
                rejectCount++;
                if (dbg) System.out.println("rejectCount equals :"+
                    rejectCount);

                if (checkDebug) System.out.println("rejectCount equals :"+
                    rejectCount);

                String rejectInsert = reject.getInsStr();
                String rejectErrorMesages = reject.getMessages();
                int rejectRecordsRecords  = reject.getNumRecords();

                if (dbg) System.out.println("<br>rejectInsert "+ rejectInsert );
                if (dbg) System.out.println("<br>rejectErrorMesages "+ rejectErrorMesages);
                if (dbg) System.out.println("<br>rejectRecordsRecords "+ rejectRecordsRecords);

                if (dbg) System.out.println("<br>(reject) recs "+ recs[i] );
                if (dbg) System.out.println("<br> reject "+ reject);

                if (checkDebug) System.out.println("<br>(reject) recs "+ recs[i]);
                if (checkDebug) System.out.println("<br> reject "+ reject);

                //if (dbg) System.out.println("<br> data "+ data);

            } //if (rejected) {


            String where =
                VosMain.DATE_TIME +" = "+Tables.getDateFormat(recs[i].getDateTime())
                +" and "+ VosMain.LATITUDE  +" = "+recs[i].getLatitude()
                +" and "+ VosMain.LONGITUDE +" = "+recs[i].getLongitude()
                +" and "+ VosMain.CALLSIGN  +" = '"+recs[i].getCallsign()+"'";

           if (dbg) System.out.println("<br> Update: where "+where);

           if (updateVosMain) {

                // Update of VosMain.
                //if (dbg) System.out.println("<br> data.TABLE = "+data.TABLE);
                //success = new VosTable().doUpd(data.TABLE,data,where); //use this one

                /*if (dbg) System.out.println("<br> Update rec with null where = "+where);
                VosMain updVosMain = new VosMain();
                VosMain vosWhere = new VosMain();
                success = new VosMain().upd(data,where);

                VosMain updVosMain = new VosMain();

                updVosMain.setLatitude(recs[i].getLatitude());
                updVosMain.setLongitude(recs[i].getLongitude());
                updVosMain.setDateTime(recs[i].getDateTime());
                updVosMain.setCallsign(recs[i].getCallsign());

                VosMain vosWhere = new VosMain();
                success = updVosMain.upd(data);
                */

                // To get Update statement from TABLE class.


                VosTable tableCheck = new VosTable();
                //tableCheck.doUpd(data.TABLE,data,where);
                tableCheck.doUpd(table.TABLE,data,where);
                String update = tableCheck.getUpdStr();
                String errorMessage = tableCheck.getMessages();
                int numRecords = tableCheck.getNumRecords();

                //if (dbg) System.out.println("<br> update String = "+update);
                //if (dbg) System.out.println("<br> errorMessage  = "+errorMessage);
                //if (dbg) System.out.println("<br> numRecords    = "+numRecords);

                if (checkDebug) System.out.println("<br> update String = "+update);
                if (checkDebug) System.out.println("<br> errorMessage  = "+errorMessage);
                if (checkDebug) System.out.println("<br> numRecords    = "+numRecords);


                if (dbg) System.out.println("<br> updateVosMain "+updateVosMain);
                if (dbg) System.out.println("<br>(update funct.) recs "+ recs[i] );
                if (dbg) System.out.println("<br> data: "+data);

                if (checkDebug) System.out.println("<br> updateVosMain "+updateVosMain);
                if (checkDebug) System.out.println("<br>(update funct.) recs "+ recs[i] );
                if (checkDebug) System.out.println("<br> data: "+data);

           } // if (updateVosMain)

           if (dbg) System.out.println("<br> <end of record> ");

        } //for (int i= 0;i < recs.length; i++) {

    } //void process(VosTable data) {

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
            CheckVOSData2 local= new CheckVOSData2(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, "CheckVOSData2", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class CheckVOSData2
