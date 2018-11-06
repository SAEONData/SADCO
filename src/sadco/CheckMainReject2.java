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
 * @author  041207 - SIT Group
 * @version
 * 041207 - Ursula von St Ange - create class<br>
 */
public class CheckMainReject2 extends CompoundItem {

    /** some common functions */
    static common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    //sadco.SadConstants sc = new sadco.SadConstants();
    SadConstants sc = new SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    //boolean checkDebug = false;
    boolean checkDebug = true;

    String thisClass = this.getClass().getName();

    String startLat = "";
    String endLat   = "";
    String startLon = "";
    String endLon   = "";

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

    String vosColumns    = "";
    String vosWhere      = "";
    String order = "";

    int EVAL = -9999999;
    int total = 0;

    public CheckMainReject2(String args[]) {

        // get the argument values
        getArgParms(args);
        String pathName = "d:/myfiles/java/data/vos/";

        int sLat = (int)(new Float(startLat).floatValue() * 100000f);
        int eLat = (int)(new Float(endLat).floatValue() * 100000f);
        int sLon = (int)(new Float(startLon).floatValue() * 100000f);
        int eLon = (int)(new Float(endLon).floatValue() * 100000f);

        int incrVal = (int)(new Float(incr).floatValue() * 100000f);

        System.out.println("incr "+incr);

        System.out.println("<br> before latitude for loop:");
        System.out.println("<br> before sLat "+sLat);
        System.out.println("<br> before eLat "+eLat);
        System.out.println("<br> before incrVal "+incrVal);

        vosColumns = "LATITUDE, LONGITUDE, DATE_TIME, CALLSIGN, LOAD_ID, "+
            "ATMOSPHERIC_PRESSURE, SURFACE_TEMPERATURE, DRYBULB, WETBULB, "+
            "DEWPOINT, SWELL_DIRECTION, SWELL_HEIGHT, SWELL_PERIOD, "+
            "WAVE_HEIGHT, WAVE_PERIOD, WIND_DIRECTION, WIND_SPEED";

        if (dbg) System.out.println("<br> vosColumns "+vosColumns);

        total = 0;

        System.out.println("<br>latitude: " + sLat + " " + eLat);

        // Open file with error report.
        String fileName = "Lat"+sLat+"_"+eLat+"Lon"+sLon+"_"+eLon;
        String of1 = database+"_"+fileName+ ".warning";
        ec.deleteOldFile(pathName+of1);
        try {
             warningFile = new RandomAccessFile(pathName+of1,"rw");
        } catch (Exception e) {
            System.out.println("<br>" + thisClass + "open files error: " +
                e.getMessage());
            e.printStackTrace();
        } // try .. catch

        for (int intLowerLat = sLat; intLowerLat < eLat; intLowerLat+=incrVal) {

            float lower = (float)(intLowerLat) / 100000f;
            float upper = (float)(intLowerLat+incrVal-1);
            upper = upper / 100000f;

            System.err.println("Latitude = " + ec.frm(lower,10,5) + ", " + ec.frm(upper,10,5));
            //if (dbg) System.out.println("<br> lower"+lower);
            //if (dbg) System.out.println("<br> upper"+upper);

            float lowerlong = 0;
            float upperlong = 0;

            // have to repeat it here for when arch/arch2 is done
            sLon = (int)(new Float(startLon).floatValue() * 100000f);
            eLon = (int)(new Float(endLon).floatValue() * 100000f);
            System.out.println("<br>longitude: " + sLon + " " + eLon);

            for (int intLowerLong = sLon;intLowerLong < eLon ;intLowerLong+=incrVal) {

                //both of type float declaired above
                lowerlong = (float)(intLowerLong) / 100000f;
                upperlong = (float)(intLowerLong+incrVal-1);
                upperlong = upperlong / 100000f;

                //if (dbg) System.out.println("<br> lowerlong = "+lowerlong);
                //if (dbg) System.out.println("<br> upperlong = "+upperlong);

                //**
                vosWhere = "LATITUDE between "+lower +" and "+
                    upper+" and LONGITUDE "+ " between "+lowerlong+
                    " and "+upperlong;

                //if (dbg)
                System.out.println("<br> vosWhere "+vosWhere);

                /* ... process function....  */


                if (database.equals("main")) {
                    VosMain main = new VosMain();
                    if (dbg) System.out.println("<br> main.TABLE = "+main.TABLE);
                    process(new VosMain(), main, main.getV(vosColumns,vosWhere,order));
                } else if (database.equals("main2")) {
                    VosMain2 main2 = new VosMain2();
                    process(new VosMain2(), main2, main2.getV(vosColumns,vosWhere,order));
                } else if (database.equals("arch")) {
                    VosArch arch = new VosArch();
                    if (dbg) System.out.println("<br> arch.TABLE = "+arch.TABLE);
                    process(new VosArch(), arch, arch.getV(vosColumns,vosWhere,order));
                } else if (database.equals("arch2")) {
                    VosArch2 arch2 = new VosArch2();
                    process(new VosArch2(), arch2, arch2.getV(vosColumns,vosWhere,order));
                  // **** get corresponding reject records to chech if it exits
                }


            } // for (float lowerlong = ....

            if ( (database.equals("arch")) || (database.equals("arch2")) ) {
                sLon = 50;
                eLon = 70;

                if (dbg) System.out.println("<br> arch: sLon "+sLon);
                if (dbg) System.out.println("<br> arch: eLon   "+eLon);
                if (dbg) System.out.println("<br> arch: sLon "+lowerlong);

                sLon *= 100000;
                eLon *= 100000;
                System.out.println("<br>longitude: " + sLon + " " + eLon);

                for (int intLowerLong = sLon;intLowerLong < eLon ;intLowerLong+=incrVal) {
                    //upperlong = lowerlong+incrVal-0.00001f;
                    //upperlong = (lowerlong+incrVal) / 100000;

                    lowerlong = (float)(intLowerLong) / 100000f;
                    upperlong = (float)(intLowerLong+incrVal-1) ;
                    upperlong = upperlong / 100000f;

                    if (dbg) System.out.println("eLon "+upperlong);
                    if (dbg) System.out.println("<br> in for 2 longitude incrVal = "+incrVal);

                    vosWhere = "LATITUDE between "+lower
                        +" and "+upper+" and LONGITUDE "+
                        " between "+lowerlong+" and "+upperlong;

                    //if (dbg)
                    System.out.println("<br> vosWhere "+vosWhere);

                    if (database.equals("arch")) {
                        VosArch arch = new VosArch();
                        process(new VosArch(), arch,
                            arch.getV(vosColumns,vosWhere,order));
                    } else if (database.equals("arch2")) {
                       VosArch2 arch2 = new VosArch2();
                       process(new VosArch2(), arch2,
                           arch2.getV(vosColumns,vosWhere,order));
                    } // if (database.equals("arch")) {

                } //for (lowerlong = 50;lowerlong < 70 ;lowerlong+=incrVal) {
            } //if ( (database.equals("arch")) || (database.equals("arch2")) ) {
        } // for (float lower = sLat; lower < eLat; lower+=incrVal) {

        System.out.println("<br> Total records processed = "+total);
        System.out.println("<br> Total records rejected = " + rejectCount);

        try {
            warningFile.close();
        } catch (Exception e) {
            System.out.println("<br>" + thisClass + "close files error: " +
                e.getMessage());
            e.printStackTrace();
        } // try .. catch

    } // constructor


    /**
     * process records for vos_main, vos_main2 vos_arch & vos_arch2.
     * table - @parms data VosMain, VosMain, VosArch or VosArch.
     *
     */
    void process(VosTable data, VosTable table, Vector result) {

        common2.DbAccessC db = table.db;

        int arraySize = result.size();
        total += arraySize;

        //if (dbg)
        System.out.println("<br>** tot main/arch recs found = "+arraySize);

        // field in vos reject to invalid value.
        for (int i = 0; i < result.size(); i++) {

            //vosColumns = "LATITUDE, LONGITUDE, DATE_TIME, CALLSIGN, LOAD_ID, "+
            //    "ATMOSPHERIC_PRESSURE, SURFACE_TEMPERATURE, DRYBULB, WETBULB, "+
            //    "DEWPOINT, SWELL_DIRECTION, SWELL_HEIGHT, SWELL_PERIOD, "+
            //    "WAVE_HEIGHT, WAVE_PERIOD, WIND_DIRECTION, WIND_SPEED";
            Vector row = (Vector) result.elementAt(i);
            VosTable vosData = new VosTable();
            vosData.setLatitude           ((String)
                row.elementAt(db.getColNumber(VosTable.LATITUDE)));
            vosData.setLongitude          ((String)
                row.elementAt(db.getColNumber(VosTable.LONGITUDE)));
            vosData.setDateTime           ((String)
                row.elementAt(db.getColNumber(VosTable.DATE_TIME)));
            vosData.setCallsign           ((String)
                row.elementAt(db.getColNumber(VosTable.CALLSIGN)));
            vosData.setLoadId             ((String)
                row.elementAt(db.getColNumber(VosTable.LOAD_ID)));
            vosData.setAtmosphericPressure((String)
                row.elementAt(db.getColNumber(VosTable.ATMOSPHERIC_PRESSURE)));
            vosData.setSurfaceTemperature ((String)
                row.elementAt(db.getColNumber(VosTable.SURFACE_TEMPERATURE)));
            vosData.setDrybulb            ((String)
                row.elementAt(db.getColNumber(VosTable.DRYBULB)));
            vosData.setWetbulb            ((String)
                row.elementAt(db.getColNumber(VosTable.WETBULB)));
            vosData.setDewpoint           ((String)
                row.elementAt(db.getColNumber(VosTable.DEWPOINT)));
            vosData.setSwellDirection     ((String)
                row.elementAt(db.getColNumber(VosTable.SWELL_DIRECTION)));
            vosData.setSwellHeight        ((String)
                row.elementAt(db.getColNumber(VosTable.SWELL_HEIGHT)));
            vosData.setSwellPeriod        ((String)
                row.elementAt(db.getColNumber(VosTable.SWELL_PERIOD)));
            vosData.setWaveHeight         ((String)
                row.elementAt(db.getColNumber(VosTable.WAVE_HEIGHT)));
            vosData.setWavePeriod         ((String)
                row.elementAt(db.getColNumber(VosTable.WAVE_PERIOD)));
            vosData.setWindDirection      ((String)
                row.elementAt(db.getColNumber(VosTable.WIND_DIRECTION)));
            vosData.setWindSpeed          ((String)
                row.elementAt(db.getColNumber(VosTable.WIND_SPEED)));


            recordCount++;
            rejected      = false;
            updateVosMain = false;

            int longIndex = 0;
            if (vosData.getLongitude() > 25) {
                longIndex = 1;
            } //if (vosData.getLongitude() > 25) {

            int latIndex  = ((int)vosData.getLatitude()+ 10) / 5;

            if (latIndex > 17) latIndex = 17;
            if (dbg) System.out.println("<br> @@latIndex "+latIndex);

            VosReject reject = new VosReject();
            boolean invalidValue = false;

            int testno, testno2;
            float ftestno, ftestno2;
            int itestno;

            String position = vosData.getLatitude() + " S " +
                vosData.getLongitude() +" W";

            // get...Records vir reject velde......
            int loadId = 0;
            if (vosData.getLoadId() != VosMain.INTNULL) {
                // rejected = true; ub
                loadId = vosData.getLoadId();
            } //if (vosData.getLoadId()) {

            // indexes for range checking
            String nullVal = null;

            data = new VosMain();

            // SEA TEMPERATURE - Check if valid
            if (vosData.getSurfaceTemperature() != VosMain.FLOATNULL) {

                int[][][] seaTemp = {
                    { {18,37},{19,37},{16,37},{12,36},{8 ,33},{7 ,33},{7 ,33},{7 ,33},{6 ,33}, //west
                      {5 ,33},{-2,28},{-2,20},{-2,18},{-2,16},{-2,14},{-2, 8},{-2, 8},{-2, 8} },
                    { {15,37},{18,36},{20,36},{19,37},{19,37},{19,37},{15,36},{13,35},{6 ,34}, //east
                      {7 ,30},{-2,28},{-2,20},{-2,18},{-2,16},{-2,14},{-2, 8},{-2, 8},{-2, 8} }};

                testTempErrorValue3(vosData.getSurfaceTemperature(),seaTemp[longIndex][latIndex], "SST    ",
                    "Invalid Sea Temperature CSIR Limits ("+position+")");

                if (tempErrorFlag) {

                    // test value according to wmo limits.
                    int[][][] seaTemp2 = {{{-2,37}}};
                    testTempErrorValue3(vosData.getSurfaceTemperature(), seaTemp2[0][0],
                        "SST   ","Invalid Sea Temperature WMO Limits ("+position+")");

                    if (dbg) System.out.println("<br> SST Value "+vosData.getSurfaceTemperature());
                    if (dbg) System.out.println("<br> CSIR Limits ");//+seaTemp[0][0]);
                    if (dbg) System.out.println(" seaTemp[longIndex][latIndex][0] = "+seaTemp[longIndex][latIndex][0]);
                    if (dbg) System.out.println(" seaTemp[longIndex][latIndex][1] = "+seaTemp[longIndex][latIndex][1]);

                    if (!tempErrorFlag) {

                        // put PK plus seaTemp2 into vos_reject table.
                        rejected = true;
                        reject.setSurfaceTemperature(vosData.getSurfaceTemperature());
                        System.out.println("<br>rejected sst: " + vosData.getSurfaceTemperature());
                    } else {
                        if (dbg) System.out.println("<br> WMO limits ");
                        if (dbg) System.out.println("<br> seaTemp2[0][0][0] = "+
                            seaTemp2[0][0][0]);
                        if (dbg) System.out.println("<br> seaTemp2[0][0][1] = "+
                            seaTemp2[0][0][1]);
                    }

                    updateVosMain=true;
                    if (checkDebug) System.out.println("<br> SurfaceTemperature "+vosData.getSurfaceTemperature());
                    //Update vos_main setting vosData.getSurfaceTemperature with FLOATNULL
                    data.setSurfaceTemperature(VosMain.FLOATNULL2);
                    // No entries made to vos_reject table.

                    if (dbg) System.out.println("<br> Update vos_main SurfaceTemp = "+
                        vosData.getSurfaceTemperature()+" with Floatnull2.");
                    if (dbg) System.out.println("<br> Value of Floatnull2 "+
                        VosMain.FLOATNULL2);

                    //} //if (!tempErrorFlag) {
                } //if (tempErrorFlag) {
             } //if (vosData.getSurfaceTempreature() != VosMain.FLOATNULL) {

            boolean valid_drybulb = false;
            float drybCheck  = EVAL;

            // -- DRYBULB
            if (vosData.getDrybulb() != VosMain.FLOATNULL) {

                int[][][] drybulb = {
                    { {19,38},{19,37},{16,38},{12,40},{7 ,33},{7 ,33},{7 ,33},{7 ,33},{2 ,30}, //west
                      {0 ,28},{-10,27},{-15,25},{-15,17},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} },
                    { {17,37},{19,35},{19,35},{19,37},{19,37},{14,36},{13,37},{9 ,35},{5 ,33},{2 ,30}, //east
                      {-10, 25},{-15, 25},{-15, 17},{-19, 17},{-25, 15},{-25, 15},{-25, 15},{-25, 15} }};

                testTempErrorValue3(vosData.getDrybulb(), drybulb[longIndex][latIndex],
                    "AT   ","Invalid Air Temperature CSIR Limits ("+position+")");

                drybCheck = vosData.getDrybulb();

                if (tempErrorFlag) {

                    int[][][] drybulb2 = {{{-25,40}}};
                        testTempErrorValue3(vosData.getDrybulb(),drybulb2[0][0], "AT   ",
                            "Invalid air temperature WMO Limits ("+position+")");

                    if (dbg) System.out.println("<br><br> drybCheck "+drybCheck);
                    if (dbg) System.out.println("<br> drybulb[0][0][0] "+
                        drybulb[0][0][0]);
                    if (dbg) System.out.println("<br> drybulb[0][0][1] "+
                        drybulb[0][0][1]);

                    if (!tempErrorFlag) { // not failed

                        // put PK plus drybulb2 into vos_reject table.
                        rejected = true;
                        reject.setDrybulb(vosData.getDrybulb());
                        System.out.println("<br>rejected drybulb: " + vosData.getDrybulb());

                        if (dbg) System.out.println("<br> vosData.getDrybulb()"+
                            vosData.getDrybulb());
                    } else {
                        if (dbg) System.out.println("<br> WMO limits ");
                        if (dbg) System.out.println(" drybulb[0][0][0] = "+
                            drybulb2[0][0][0]);
                        if (dbg) System.out.println(" drybulb[0][0][1] = "+
                            drybulb2[0][0][1]);
                    }

                    if (checkDebug) System.out.println("<br> Drybulb() "+vosData.getDrybulb());
                    updateVosMain=true;
                    //Update vos_main setting vosData.vosData.getDrybulb() with FLOATNULL
                    data.setDrybulb(VosMain.FLOATNULL2);

                } else {
                    valid_drybulb = true;
                } // if (tempErrorFlag)

            } // if (vosData.getDrybulb() != VosMain.FLOATNULL) {

            // WETBULB
            if (vosData.getWetbulb() != VosMain.FLOATNULL) {

                //Have to change these latitudes, longitudes,
                //reason it's drybulb's values.

                int[][][] wetbulb = {
                    { {16,38},{16,37},{15,38},{12,40},{8 ,33},{4 ,33},{4 ,33},{3 ,33},{2 ,30}, //west
                      {-1 ,30},{-10,27},{-15,25},{-15,19},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} },
                    { {17,37},{17,35},{17,35},{17,37},{16,37},{13,36},{7 ,37},{6 ,35},{2 ,33}, //east
                      {0 ,34},{-10,27},{-15,25},{-15,19},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} }};

                testTempErrorValue3(vosData.getWetbulb(),wetbulb[longIndex][latIndex],
                    "WT   ", "Invalid wetbulb CSIR Limits ("+position+")");

                if (tempErrorFlag) {

                    int[][][] wetbulb2 = {{{-25,40}}};
                    testTempErrorValue3(vosData.getWetbulb(), wetbulb2[0][0],
                            "WT   ", "Invalid wetbulb WMO Limits ("+position+")");

                    if (!tempErrorFlag) { //not failed
                        // put PK plus drybulb2 into vos_reject table.
                        rejected = true;
                        reject.setWetbulb(vosData.getWetbulb());
                        System.out.println("<br>rejected wetbulb: " + vosData.getWetbulb());

                        if (dbg) System.out.println("<br> vosData.getWetbulb()"+
                            vosData.getWetbulb());
                    } //if (!tempErrorFlag) { //not failed

                    if (checkDebug) System.out.println("<br> Wetbulb "+vosData.getWetbulb());
                    updateVosMain=true;
                    // Update vos_main setting vosData.vosData.getWetbulb()
                    // with FLOATNULL.
                    data.setWetbulb(VosMain.FLOATNULL2);

                    // No entries made to vos_reject table.
                    if (dbg) System.out.println("<br> vosData.getWetbulb()"+
                        vosData.getWetbulb());
                 } //if (tempErrorFlag) {

            } // if (vosData.getWetbulb() != VosMain.FLOATNULL) {

            float dewptCheck = EVAL;

            // DEW-POINT TEMPERATURE - Check if valid
            if (vosData.getDewpoint() != VosMain.FLOATNULL) {

                int[][][] dewpoint = {
                    { {16,38},{14,37},{12,38},{10,40},{7 ,33},{3 ,33},{0 ,33},{0 ,33},{-3,30},  //west
                      {-7,28},{-10,27},{-15,25},{-15,19},{-19,17},{-25,15},{-25,15},{-25,15},{-25,15} },
                    { {16,37},{16,35},{14,35},{14,37},{11,37},{9 ,36},{3 ,37},{2 ,35},{-2,33},  //east
                      {-5,30},{-10,27},{-15,25},{-15,19},{-18,17},{-25,15},{-25,15},{-25,15},{-25,15} }};

                testTempErrorValue3(vosData.getDewpoint(),dewpoint[longIndex][latIndex],
                    "DW   ","Invalid dew-point temperature CSIR Limits ("+position+")");

                dewptCheck = vosData.getDewpoint();


                if (tempErrorFlag) { // tempErroFlag = failed

                    int[][][] dewpoint2 = {{{-25,40}}};
                    testTempErrorValue3(vosData.getDewpoint(),dewpoint2[0][0],
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
                        reject.setDewpoint(vosData.getDewpoint());
                        System.out.println("<br>rejected dewpoint1: " + vosData.getDewpoint());
                    } else {


                    }

                    updateVosMain=true;
                    if (checkDebug) System.out.println("<br> Dewpoint "+vosData.getDewpoint());
                    data.setDewpoint(VosMain.FLOATNULL2);

                } else {

                    if (valid_drybulb) {
                        if (dewptCheck > drybCheck) {
                            // put PK plus drybulb2 into vos_reject table.
                            rejected = true;

                            updateVosMain=true;
                            //data.setWetbulb(VosMain.FLOATNULL2);  ub
                            data.setDewpoint(VosMain.FLOATNULL2);

                            reject.setDewpoint(vosData.getDewpoint());
                            System.out.println("<br>rejected dewpoint2: " + vosData.getDewpoint());

                            if (dbg)
                                System.out.println("<br>valid_drybulb "+valid_drybulb);
                            if (dbg)
                                System.out.println("<br>reject:dewptCheck > drybCheck "+
                                    dewptCheck+" > "+drybCheck);
                            setTempErrorFlags("DW     ", vosData.getDewpoint(),
                                "reject: Invalid dew-point temp. dewpt > dryb: "+
                                    drybCheck+") ("+position+") ");

                        } // if ( dewptCheck > drybCheck) {

                    } //if (valid_drybulb) {

                } //if (tempErrorFlag) {

            } //if (vosData.getDrybulb() != VosMain.FLOATNULL) {

            // SEA-LEVEL PRESSURE - convert to a numeric value, but the
            // atmopheric pressure
            if (vosData.getAtmosphericPressure() != VosMain.FLOATNULL) {
                if (dbg)
                    System.out.println(" vosData.getAtmosphericPressure() = "+
                        vosData.getAtmosphericPressure());

                if (vosData.getAtmosphericPressure() < 930) {
                    if (dbg)
                        System.out.println("<br>record: atmospheric < 930  "+vosData);
                    if (dbg)
                        System.out.println("<br>atmosphericVal "+vosData.getAtmosphericPressure());
                }

                if (vosData.getAtmosphericPressure() > 1050) {
                    if (dbg)
                        System.out.println("<br>record: atmospheric > 1050  "+vosData);
                    if (dbg)
                        System.out.println("<br>atmosphericVal "+vosData.getAtmosphericPressure());
                }

                double[][][] atmosPress = {{{930,1050}}};
                testTempErrorValue4(vosData.getAtmosphericPressure(),atmosPress[0][0],
                    "SLP  ","Invalid sea-level pressure WMO Limits ("+position+")");

                if (tempErrorFlag) {
                    if (dbg) System.out.println("<br> atmosPress[0][0][0] "+atmosPress[0][0][0]);
                    if (dbg) System.out.println("<br> atmosPress[0][0][1] "+atmosPress[0][0][1]);

                   if (checkDebug) System.out.println("<br> atmosphericVal "+vosData.getAtmosphericPressure());
                   updateVosMain=true;
                   data.setAtmosphericPressure(VosMain.FLOATNULL2);
                } //if (tempErrorFlag) {
            } //if (vosData.getAtmosphericPressure() != VosMain.FLOATNULL)

            // SWELL Height - Check if valid
            //int[][][] swHeight = {{{0,35}}};
            if (vosData.getSwellHeight() != VosMain.FLOATNULL) {

                if (dbg) System.out.println("<br> vosData.getSwellHeight() = "+
                    vosData.getSwellHeight());

                double[][][] swHeight = {{{0 ,17.5}}};

                if (dbg) System.out.println("<br> swHeight[0][0][0] "+
                    swHeight[0][0][0]);
                if (dbg) System.out.println("<br> swHeight[0][0][1] "+
                    swHeight[0][0][1]);

                testTempErrorValue4((int)vosData.getSwellHeight(),swHeight[0][0],
                    "SW1H ","Invalid swell height WMO Limits ("+position+")");

                if (tempErrorFlag) {
                   if (checkDebug) System.out.println("<br> SwellHeight "+vosData.getSwellHeight());
                   updateVosMain=true;
                   data.setSwellHeight(VosMain.FLOATNULL2);
                } //if (tempErrorFlag) {
            } // if (vosData.getSwellHeight() != VosMain.FLOATNULL)

            // SWELL PERIOD - Check if valid
            if (vosData.getSwellPeriod() != VosMain.INTNULL) {

                if (dbg) System.out.println("<br> vosData.getSwellPeriod() = "+
                    vosData.getSwellPeriod());

                int[][][] swPeriod = {{{0 ,25}}};

                if (dbg) System.out.println("<br> swPeriod[0][0][0] "+
                    swPeriod[0][0][0]);
                if (dbg) System.out.println("<br> swPeriod[0][0][1] "+
                    swPeriod[0][0][1]);

                testTempErrorValue2((int)vosData.getSwellPeriod(),swPeriod[0][0],
                    "SW1P ","Invalid swell period WMO Limits ("+position+")");

                if (tempErrorFlag) {
                   if (checkDebug) System.out.println("<br> SwellPeriod "+vosData.getSwellPeriod());
                   updateVosMain=true;
                   data.setSwellPeriod(VosMain.INTNULL2);
                } //if (tempErrorFlag) {
            }

            // WAVE PERIOD - Check if valid
            if (vosData.getWavePeriod() != VosMain.INTNULL) {

                if (dbg) System.out.println("<br> vosData.getWavePeriod() = "+
                    vosData.getWavePeriod());

                int[][][] wvPeriod = {{{0, 20}}};

                if (dbg) System.out.println("<br> wvPeriod[0][0][0] "+
                    wvPeriod[0][0][0]);
                if (dbg) System.out.println("<br> wvPeriod[0][0][1] "+
                    wvPeriod[0][0][1]);

                testTempErrorValue2(vosData.getWavePeriod(),wvPeriod[0][0], "WWP  ",
                    "Invalid wave period WMO Limits ("+position+")");

                if (tempErrorFlag) { // failed
                   if (checkDebug) System.out.println("<br> WavePeriod() "+vosData.getWavePeriod());
                   updateVosMain=true;
                   data.setWavePeriod(VosMain.INTNULL2);
                } //if (tempErrorFlag) {

            } //if (vosData.getWavePeriod() != VosMain.INTNULL) {

            // WAVE HEIGHT - Check if valid
            if (vosData.getWaveHeight() != VosMain.FLOATNULL) {

                if (dbg) System.out.println("<br> vosData.getWaveHeight() "+
                    vosData.getWaveHeight());

                double[][][] wvHeight = {{{0 ,17.5}}};

                if (dbg) System.out.println("<br> wvHeight[0][0][0] "+
                    wvHeight[0][0][0]);
                if (dbg) System.out.println("<br> wvHeight[0][0][1] "+
                    wvHeight[0][0][1]);

                testTempErrorValue4(vosData.getWaveHeight(),wvHeight[0][0], "WWH  ",
                    "Invalid wave height WMO Limits ("+position+")");

                if (tempErrorFlag) { // failed
                    if (checkDebug) System.out.println("<br> getWaveHeight "+vosData.getWaveHeight());
                    updateVosMain=true;
                    data.setWaveHeight(VosMain.FLOATNULL2);
                } //if (waveHeight[i] < 0) || (waveHeight[i] > 35)) {
            }

            // WIND SPEED - Check if valid
            if (vosData.getWindSpeed() != VosMain.FLOATNULL) {

                if (dbg) System.out.println("<br> vosData.getWindSpeed() "+
                    vosData.getWindSpeed());

                double[][][] wndSpeed = {{{0,40}}};

                if (dbg) System.out.println("<br> wndSpeed[0][0][0] "+
                    wndSpeed[0][0][0]);
                if (dbg) System.out.println("<br> wndSpeed[0][0][1] "+
                    wndSpeed[0][0][1]);

                testTempErrorValue4(vosData.getWindSpeed(),wndSpeed[0][0],
                    "WSP  ", "Invalid wind speed WMO Limits ("+position+")");

                if (tempErrorFlag) {
                    if (checkDebug) System.out.println("<br> getWindSpeed "+vosData.getWindSpeed());
                    updateVosMain=true;
                    data.setWindSpeed(VosMain.FLOATNULL2);
                } //if (tempErrorFlag) {

            } //if (vosData.getWindSpeed() != VosMain.FLOATNULL) {


            //System.out.println("trace1");
            boolean success = false;
            // Write records to VosReject if there where invalid range checks.

            String where =
                VosMain.DATE_TIME +" = "+Tables.getDateFormat(vosData.getDateTime())
                +" and "+ VosMain.LATITUDE  +" = "+vosData.getLatitude()
                +" and "+ VosMain.LONGITUDE +" = "+vosData.getLongitude()
                +" and "+ VosMain.CALLSIGN  +" = '"+vosData.getCallsign()+"'";

            // check if vos_main record already exist in vos_reject.



            if (rejected) {
                if (dbg) System.out.println("<br>Before get reject " + new java.util.Date());
                VosReject rejectCheck = new VosReject();
                if (dbg) System.out.println("<br>After  get reject " + new java.util.Date());
                VosTable[] rejectRecs = reject.get(vosColumns,where,order);
                System.out.println("<br> reject "+ reject);

                if (rejectRecs.length > 0) { // then record exist in vos_reject

                    System.out.println(" Rejected True ");
                    System.out.println(" This should be the same pk + field of Vos_Main ");
                    System.out.println("<br> Vos_Main                :"+data);
                    System.out.println("<br> Vos_Reject record found :"+rejectRecs[0]);
                    System.out.println("<br> Insert vos_reject       :"+reject);

                    VosReject rejectTable = new VosReject();
                    rejectTable.doUpd(rejectTable.TABLE, reject, where);

                } else { // insert

                    reject.setDateTime (vosData.getDateTime());
                    reject.setLatitude (vosData.getLatitude());
                    reject.setLongitude(vosData.getLongitude());
                    reject.setCallsign (vosData.getCallsign());
                    reject.setLoadId (loadId);

                    //SAWS
                    reject.setDataId("WB");
                    reject.setQualityControl("9");
                    //reject.setSource1("2");

                    reject.put();
                    rejectCount++;
                    if (dbg) System.out.println("rejectCount equals :"+rejectCount);
                    //if (checkDebug) System.out.println("rejectCount equals :"+rejectCount);

                    String rejectInsert = reject.getInsStr();
                    String rejectErrorMesages = reject.getMessages();
                    int rejectRecordsRecords  = reject.getNumRecords();

                    if (dbg) System.out.println("<br>rejectInsert "+ rejectInsert );
                    if (dbg) System.out.println("<br>rejectErrorMesages "+ rejectErrorMesages);
                    if (dbg) System.out.println("<br>rejectRecordsRecords "+ rejectRecordsRecords);

                    if (dbg) System.out.println("<br>(reject) recs "+ vosData );
                    if (dbg) System.out.println("<br> reject "+ reject);

                    if (checkDebug) System.out.println("<br>(reject) recs "+ vosData);
                    if (checkDebug) System.out.println("<br> reject "+ reject);

                    //if (dbg) System.out.println("<br> data "+ data);

                } //else if

            } //if (rejected) {

            if (updateVosMain) {

                // To get Update statement from TABLE class.
                VosTable tableCheck = new VosTable();
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
                if (dbg) System.out.println("<br>(update funct.) recs "+ vosData );
                if (dbg) System.out.println("<br> data: "+data);

                //if (checkDebug) System.out.println("<br> updateVosMain "+updateVosMain);
                //if (checkDebug) System.out.println("<br>(update funct.) recs "+ vosData );
                //if (checkDebug) System.out.println("<br> data: "+data);

            } // if (updateVosMain)

            if (dbg) System.out.println("<br> <end of record> ");

        } // end of for i main loop - tot vos_main records

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
        startLon    = ec.getArgument(args,"pstartlon");
        endLon      = ec.getArgument(args,"pendlon");
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
            CheckMainReject2 local= new CheckMainReject2(args);
            common2.DbAccessC.close();
        } catch(Exception e) {
            ec.processErrorStatic(e, "CheckMainReject2", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class CheckMainReject2



                    // Update of VosMain.
                    //if (dbg) System.out.println("<br> data.TABLE = "+data.TABLE);
                    //success = new VosTable().doUpd(data.TABLE,data,where); //use this one

                    /*if (dbg) System.out.println("<br> Update rec with null where = "+where);
                    VosMain updVosMain = new VosMain();
                    VosMain vosWhere = new VosMain();
                    success = new VosMain().upd(data,where);

                    VosMain updVosMain = new VosMain();

                    updVosMain.setLatitude(vosData.getLatitude());
                    updVosMain.setLongitude(vosData.getLongitude());
                    updVosMain.setDateTime(vosData.getDateTime());
                    updVosMain.setCallsign(vosData.getCallsign());

                    VosMain vosWhere = new VosMain();
                    success = updVosMain.upd(data);
                    */
