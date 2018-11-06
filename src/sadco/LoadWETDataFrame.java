package sadco;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import oracle.html.*;

/**
 * This class loads the weather data.
 *
 * SadcoWET
 * pScreen.equals("load")
 * pVersion.equals("load")
 *
 * @author 20000719 - SIT Group
 * @version
 * 20000719 - SIT Group - create class                                      <br>
 * 20080519 - Ursula von St Ange - copy from weather and adapt for sadco    <br>
 * 20090508 - Ursula von St Ange - add institute address & new scientist(ub07)<br>
 */
public class LoadWETDataFrame extends PreLoadWETDataFrame{

    boolean     dbg = false;
    //boolean     dbg = true;
    int maxCode = 50;


    //weather.WeatherConstants sc = new weather.WeatherConstants();
    SadConstants sc = new SadConstants();

    // new parameters
    String newInstrument = "";
    int    instrumentCode2 = 0;
    int    institCode = 0;      // ub06
    String newInstitute = "";   // ub06
    String newInstAddress = ""; // ub07
    int    scientistCode = 0;   // ub07
    String newSciTitle = "";    // ub07
    String newSciFName = "";    // ub07
    String newSciSurname = "";  // ub07
    int    newSciInstitCode = 0;// ub07

    int    periodCode2 = CODEN;
    int    numberRecords2 = 0;
    int    numberNullRecords2 = 0;

    // some work variables
    boolean removeData = false;
    boolean dataOK = false;

    public LoadWETDataFrame(String args[]) {

        if (dbg) System.out.println("<br>sadco.LoadWETDataFrame: DATEN = " + DATEN);

        // get the correct path name
        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL + "weather/";
        } else {
            pathName = sc.LOCALDIR + "weather/";
        } // if host
        if (dbg) System.out.println("LoadWETDataFrame pathName = " + pathName);

        // get the argument values
        getArgParms(args);

        // Create main table                                         //
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);

        // create the html page
        mainTable.addRow(ec.crSpanColsRow("Load Data", 2, true, "blue",
            IHAlign.CENTER, "+2"));

        // remove existing data
        if (periodCode2 != CODEN) {
            removeOldPeriod();
        } // if (periodCode2 != CODEN) {

        checkNewInstrument();
        checkNewInstitute();
        checkNewScientist();

        // assume file exists - so don't check for it
        processInputFile(mainTable);

        // update sadco inventory with new dates
        updateInventory();

        // update the period counts table
        updatePeriodCounts();

        // Main container                                            //
        Container con = new Container();
        con.addItem(mainTable.setCenter());
        addItem(con);

    } // constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        fileName = ec.getArgument(args, sc.FILENAME);
        timeZone = ec.getArgument(args, sc.TIMEZONE).toLowerCase();   // ub04

        instrumentCode2 = Integer.parseInt(ec.getArgument(args, sc.INSTRUMENTCODE));
        newInstrument  = ec.getArgument(args, sc.NEWINSTRUMENT);

        institCode = Integer.parseInt(ec.getArgument(args,sc.INSTITCODE));// ub06
        newInstitute = ec.getArgument(args,sc.NEWINSTITUTE);            // ub06
        newInstAddress = ec.getArgument(args, sc.NEWINSTADDRESS);       // ub07

        scientistCode = Integer.parseInt(ec.getArgument(args, sc.SCIENTCODE));// ub07
        newSciTitle = ec.getArgument(args, sc.NEWSCTITLE);              // ub07
        newSciFName = ec.getArgument(args, sc.NEWSCFIRSTNAME);          // ub07
        newSciSurname = ec.getArgument(args, sc.NEWSCSURNAME);          // ub07
        newSciInstitCode = Integer.parseInt(ec.getArgument(args, sc.NEWSCINST));// ub07

        if (!"".equals(ec.getArgument(args, sc.CODE))) {
            periodCode2    = Integer.parseInt(ec.getArgument(args, sc.CODE));
        } // if (!"".equals(ec.getArgument(args, WeatherConstants.CODE)))

        startDate          = ec.getArgument(args, sc.STARTDATE);
        endDate            = ec.getArgument(args, sc.ENDDATE);
        sampleInterval     = Integer.parseInt(ec.getArgument(args, sc.INTERVAL));
        numberRecords2     = Integer.parseInt(ec.getArgument(args, sc.RECORDS));
        numberNullRecords2 = Integer.parseInt(ec.getArgument(args, sc.NULLS));

        if (dbg) {
            System.out.println ("<br>fileName        = " + fileName);
            System.out.println ("<br>instrumentCode2 = " + instrumentCode2);
            System.out.println ("<br>newInstrument = " + newInstrument);
            System.out.println ("<br>institCode = " + institCode);          // ub06
            System.out.println ("<br>newInstitute = " + newInstitute);      // ub06
            System.out.println ("<br>newInstAddress = " + newInstAddress);  // ub07
            System.out.println ("<br>scientistCode = " + scientistCode);    // ub07
            System.out.println ("<br>newSciTitle = " + newSciTitle);        // ub07
            System.out.println ("<br>newSciFName = " + newSciFName);        // ub07
            System.out.println ("<br>newSciSurname = " + newSciSurname);    // ub07
            System.out.println ("<br>newSciInstitCode = " + newSciInstitCode);// ub07
            System.out.println ("<br>periodCode2 = " + periodCode2);
            System.out.println ("<br>startDate = " + startDate);
            System.out.println ("<br>endDate = " + endDate);
            System.out.println ("<br>sampleInterval = " + sampleInterval);
            System.out.println ("<br>numberRecords2 = " + numberRecords2);
            System.out.println ("<br>numberNullRecords2 = " + numberNullRecords2);
        } // if (dbg)
    }   //  public void getArgParms()

    /**
     * process the input file
     */
    void processInputFile(DynamicTable table) {

        // Open the data file
        try {
            lfile = new RandomAccessFile(pathName+fileName, "r");

            if (ec.getHost().startsWith(sc.HOST)) {
                //System.out.println("chmod 644 " + rootPath+fileName);
                ec.submitJob("chmod 644 " + pathName + fileName);
            } //if (ec.getHost().startsWith(sc.HOST)) {


            if (ec.getHost().startsWith(sc.HOST)) {
                //System.out.println("chmod 644 " + rootPath+fileName);
                ec.submitJob("chmod 644 " + pathName + fileName);
            } //if (ec.getHost().startsWith(sc.HOST)) {


        } catch (Exception e) {
            System.out.println("<br>LoadWETDataFrame Error: " + e.getMessage());
            e.printStackTrace();
        }

        // assume everything OK - dont test
        readHeaderLines(table);
        headerDefaults();
        writeHeaderData();
        dataOK = readData(table);
        while (!endOfData) {
            if (dataOK) writeData();
            if (dbg) System.out.println ("<br>*** new record added: datetime = " +
                dateTime);
            dataOK = readData(table);
        } // while
        checkDates(); // get no of possible records
        displayPage(table);
    } // void processInputFile() {


    /**
     * remove old data for same period
     */
    void removeOldPeriod() {
        // delete wetPeriodNotes
        String where = WetPeriodNotes.PERIOD_CODE + "=" + String.valueOf(periodCode2);
        boolean success = new WetPeriodNotes().del(where);
        if (dbg) System.out.println ("<br>old wetPeriodNotes deleted " + success);
        // delete WetEditLog
//        where = WetEditLog.PERIOD_CODE + "=" + String.valueOf(periodCode2);
//        success = new WetEditLog().del(where);
//        if (dbg) System.out.println ("<br>old WetEditLog deleted " + success);
        // delete WetData
        where = WetData.PERIOD_CODE + "=" + String.valueOf(periodCode2);
        success = new WetData().del(where);
        if (dbg) System.out.println ("<br>old WetData deleted " + success);
        // delete period
        where = WetPeriod.CODE + "=" + String.valueOf(periodCode2);
        success = new WetPeriod().del(where);
        if (dbg) System.out.println ("<br>old WetPeriod deleted " + success);
        if (dbg) System.out.println ("<br>old data deleted");
    } // removeOldPeriod


    /**
     * write the header data to the database
     * wetPeriod, wetPeriodNotes and wetEditLog tables
     */
    void writeHeaderData() {

        java.util.Date start = Timestamp.valueOf(startDate + ".00");
        java.util.Date end = Timestamp.valueOf(endDate + ".00");

        // write the wetPeriod
        WetPeriod period = new WetPeriod(
            INTN,
            stationId,
            instrumentCode2,
            heightSurface,
            heightMSL,
            speedCorrFactor,
            speedAverMethod,
            dirAverMethod,
            windSamplingInterval,
            start,
            end,
            sampleInterval,
            numberRecords2,
            numberNullRecords2,
            new java.util.Date()
        );

        if (dbg) System.out.println ("<br>wetPeriod period = " + period);

        boolean success = period.put();
        if (dbg) System.out.println ("<br>wetPeriod success = " + success);

        // get the new periodCode
        //String where = WetPeriod.STATION_ID + "='" + stationId + "'";
        //String order = WetPeriod.CODE;
        //periodA = new WetPeriod().get(where, order);
        //periodCode = periodA[periodA.length-1].getCode();
        periodCode = period.getCode();
        if (dbg) System.out.println ("<br>wetPeriod periodCode = " + periodCode);

        // write the wetEditLog
//        if (!editLog.equals("")) {
//            success = new WetEditLog(periodCode, editLog).put();
//            if (dbg) System.out.println ("<br>wetEditLog success = "
//            + success);
//        } // if (!editLog.equals("")) {

        // write the wetPeriodNotes
        if (noteCounter > -1) {
            for (int i = 0; i <= noteCounter; i++) {
                if (dbg) System.out.println ("<br>wetPeriodNotes = " + notes);
                WetPeriodNotes periodNotes = new WetPeriodNotes(periodCode, i+1, notes[i]);
                success = periodNotes.put();
                if (dbg) System.out.println ("<br>wetPeriodNotes success = " + success);
            } // for i
        } // if (noteCounter > -1) {

    } // writeHeaderData

    /**
     * write data to the wetData table
     */
    void writeData() {

        // convert date & times to java.util.Date
        java.util.Date dateTimeDate         = Timestamp.valueOf(dateTime);
        java.util.Date airTempMinTimeDate   = Timestamp.valueOf(airTempMinTime);
        java.util.Date airTempMaxTimeDate   = Timestamp.valueOf(airTempMaxTime);
        java.util.Date windSpeedMaxTimeDate = Timestamp.valueOf(windSpeedMaxTime);

        if (dbg) System.out.println("<br>writeData: airTempMinTime = " + airTempMinTime);

        // save the data
        WetData data = new WetData(
            stationId,
            periodCode,
            dateTimeDate,
            airTempAve,
            airTempMin,
            airTempMinTimeDate,
            airTempMax,
            airTempMaxTimeDate,
            barometricPressure,
            fog,
            rainfall,
            relativeHumidity,
            solarRadiation,
            solarRadiationMax,
            windDir,
            windSpeedAve,
            windSpeedMin,
            windSpeedMax,
            windSpeedMaxTimeDate,
            windSpeedMaxLength,
            windSpeedMaxDir,
            windSpeedStd
        );
        if (dbg) System.out.println ("<br>WetData = " + data);
        boolean success = data.put();
        if (dbg) System.out.println ("<br>WetData success = " + success);

    } // writeData


    /**
     *  display the page with warnings
     */
    void displayPage(DynamicTable table) {

        table.addRow(ec.cr1ColRow("<br>"));
        table.addRow(ec.cr1ColRow(
            "The following data has been successfully loaded", true, "", 2));
        if (periodCode2 != CODEN) {
            table.addRow(ec.cr1ColRow(
                "The old data has been removed (old periodCode was " +
                periodCode2 + ")", true, "", 2));
        } // if removeData
        table.addRow(ec.cr1ColRow("<br>"));

        DynamicTable sub = new DynamicTable(2);
        sub.setFrame(ITableFrame.VOLD);
        sub.setRules(ITableRules.NONE);
        sub.setBorder(0);
        sub.addRow(ec.cr2ColRow("New PERIOD Code:", periodCode));
        displayLoadInfo(sub);
        table.addRow(ec.cr1ColRow(sub.toHTML()));
    } // displayPage


    /**
     * check if instrument exists, and insert if really new
     */
    void checkNewInstrument() {

        if (!"".equals(newInstrument)) {

            // get the instrument list to check whether it exists
            EdmInstrument2 instrument[] =
                new EdmInstrument2().get(
                    EdmInstrument2.CODE + "<" + maxCode, EdmInstrument2.CODE);
            if (dbg) System.out.println("<br>instrument.length : " + instrument.length);
            boolean found = false;
            int code = 0;
            for (int i = 0; i < instrument.length; i++) {
               if(instrument[i].getName().equalsIgnoreCase(newInstrument)) {
                  found = true;
               } // if
            } // for i

            // insert the new instrument if not found
            if (!found) {
                // Waves and weather codes are only allowed to take up the first
                // 49 records from the Edm_Instrument table
                if (instrument.length < maxCode) {
                    instrumentCode2 = instrument.length;
                    EdmInstrument2 instr =
                        new EdmInstrument2(instrumentCode2, newInstrument);
                    if (dbg) System.out.println("<br>instr = " + instr);
                    instr.put();
                    if (dbg) System.out.println("<br>insStr = " + instr.getInsStr());
                    if (dbg) System.out.println("<br>numRecords = " + instr.getNumRecords());
                } // if (instrument.length < maxCode)
            } // if (!found))

        } // if (!"".equals(newInstrument))

    } // checkNewInstrument


    /**
     * check if institute exists, and insert if really new
     */
    void checkNewInstitute() {

        if (!"".equals(newInstitute)) {

            // get the instrument list to check whether it exists
            MrnInstitutes institute[] = new MrnInstitutes().get("1=1", MrnInstitutes.CODE);
            boolean found = false;
            if (dbg) System.out.println("<br>institute.length : " + institute.length);
            int code = 0;
            for (int i = 0; i < institute.length; i++) {
               if(institute[i].getName().equalsIgnoreCase(newInstitute)) {
                  found = true;
               } // if(institute[i].getName().equalsIgnoreCase(newInstitute))
            } // for (int i = 0; i < institute.length; i++)

            // insert the new institute if not found
            if (!found) {
                institCode = institute[institute.length-1].getCode() + 1;
                if ("".equals(newInstAddress))
                    newInstAddress = MrnInstitutes.CHARNULL;
                MrnInstitutes instit = new MrnInstitutes(
                    institCode, newInstitute, newInstAddress);
                if (dbg) System.out.println("<br>instit = " + instit);
                instit.put();
                if (dbg) System.out.println("<br>insStr = " + instit.getInsStr());
                if (dbg) System.out.println("<br>numRecords = " + instit.getNumRecords());
            } // if (!found))

        } // if (!"".equals(newInstitute))

    } // checkNewInstitute


    /**
     * check if scientist exists, and insert if really new
     */
    void checkNewScientist() {

        if (!"".equals(newSciSurname)) {

            // get the instrument list to check whether it exists
            MrnScientists scientist[] = new MrnScientists().get("1=1", MrnScientists.CODE);
            boolean found = false;
            if (dbg) System.out.println("<br>scientist.length : " + scientist.length);
            int code = 0;
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
                if (dbg) System.out.println("<br>scient = " + scient);
                scient.put();
                if (dbg) System.out.println("<br>insStr = " + scient.getInsStr());
                if (dbg) System.out.println("<br>numRecords = " + scient.getNumRecords());
            } // if (!found))

        } // if (!"".equals(newSciSurname))

    } // checkNewScientist


    /**
     * update the inventory for the new last date
     */
    void updateInventory() {

        MrnInventory inv = new MrnInventory();
        inv.setSurveyId(stationA[0].getSurveyId());
        MrnInventory invA[] = inv.get();
        if (invA.length > 0) {
            MrnInventory updInv = new MrnInventory();
            updInv.setDateEnd(secondTs);
            inv.upd(updInv);
        } else {
            inv.setDataCentre        ("SADCO");
            inv.setProjectName       (stationA[0].getName());
            //inv.setCruiseName        (cruiseName       );
            //inv.setNationalPgm       (nationalPgm      );
            //inv.setExchangeRestrict  (exchangeRestrict );
            //inv.setCoopPgm           (coopPgm          );
            //inv.setCoordinatedInt    (coordinatedInt   );
            inv.setPlanamCode        (457);
            //inv.setPortStart         (portStart        );
            //inv.setPortEnd           (portEnd          );
            inv.setCountryCode       (1);
            inv.setInstitCode        (institCode);
            inv.setCoordCode         (institCode);
            inv.setSciCode1          (scientistCode);
            inv.setSciCode2          (1);
            inv.setDateStart         (startDate);
            inv.setDateEnd           (endDate);
            inv.setLatNorth          (stationA[0].getLatitude());
            inv.setLatSouth          (stationA[0].getLatitude());
            inv.setLongWest          (stationA[0].getLongitude());
            inv.setLongEast          (stationA[0].getLongitude());
            //inv.setAreaname          (areaname         );
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
            inv.setSurveyTypeCode    (3);

            inv.put();
//insert into inventory (survey_id, data_centre, project_name,
//planam_code, country_code, instit_code, coord_code, sci_code_1,sci_code_2,
//date_start, date_end,
//lat_north, lat_south, long_west, long_east,
//track_chart, target_country_code, gmt_diff, gmt_freeze,
//projection_code, spheroid_code, datum_code, data_recorded, survey_type_code)
//values ('2008/0553','SADCO','RICHARDS BAY PORT CONTROL',
//--457,1,175,175,462,1,
//457,1,221,221,1,1,
//to_date('1993-02-18','yyyy-mm-dd'),
//to_date('2005-12-31','yyyy-mm-dd'),
// 28.79704,28.79704,32.09915,32.09915,
//'N',1,0,'N',
//1,1,1,'Y',3);

        } // if (invA.length > 0)

    } // updateInventory


    /**
     * update the wet_period_counts table for this dataset
     */
    void updatePeriodCounts() {

        // keep the counts for the year
        int monthDayList[] = {31,28,31,30,31,30,31,31,30,31,30,31};
        int cnt[] = new int[12];
        String begin = "01";
        String where = "";

        // get the start year and months
        StringTokenizer t = new StringTokenizer(startDate, "-");
        int startYear = Integer.parseInt(t.nextToken());
        int startMonth = Integer.parseInt(t.nextToken());
        t = new StringTokenizer(endDate, "-");
        int endYear = Integer.parseInt(t.nextToken());
        int endMonth = Integer.parseInt(t.nextToken());

        // get and store the counts
        for (int i = startYear; i <= endYear; i++) {

            int yearTotal = 0;

            // find out month start and ends
            for (int m=0; m < 12; m++) {

                // get the WetData details.
                ec.setFrmFiller("0");
                where = WetData.STATION_ID +" = '"+stationId+ "' and "+
                    WetData.DATE_TIME+ " between "+
                    Tables.getDateFormat(i+"-"+ec.frm(m+1,2)+"-"+begin+" 00:00")+" and "+
                    Tables.getDateFormat(i+"-"+ec.frm(m+1,2)+"-"+monthDayList[m]+" 23:59");
                ec.setFrmFiller(" ");

                int numRecs     = new WetData().getRecCnt(where);

                if (dbg) System.out.println("<br>StationId "+stationId+" Year "+i+
                    " year cnt (month) "+(m+1)+" NumRecs records per month "+numRecs);

                cnt[m] =  numRecs;
                yearTotal += numRecs;

            } //for ( int m=0; m < 12; m++) {

            // only insert / update if there were records
            if (yearTotal > 0) {

                // code for Update.
                // first check if record exist, other wise insert record.
                // fields = stationId, year, (i+1), instrumentCode, numRecs

                where  = WetPeriodCounts.YEARP + " = " + i + " and " +
                         WetPeriodCounts.STATION_ID + " = '" + stationId + "'";

                int checkRecord = new WetPeriodCounts().getRecCnt(where);
                WetPeriodCounts wetRec = new WetPeriodCounts();

                // Set fields for both Update and Insert.
                wetRec.setM01(cnt[0]);
                wetRec.setM02(cnt[1]);
                wetRec.setM03(cnt[2]);
                wetRec.setM04(cnt[3]);
                wetRec.setM05(cnt[4]);
                wetRec.setM06(cnt[5]);
                wetRec.setM07(cnt[6]);
                wetRec.setM08(cnt[7]);
                wetRec.setM09(cnt[8]);
                wetRec.setM10(cnt[9]);
                wetRec.setM11(cnt[10]);
                wetRec.setM12(cnt[11]);

                if (checkRecord == 0 ) { // insert record
                    wetRec.setStationId(stationId);
                    wetRec.setYearp(i);
                    //System.out.println("<br>wetRec = " + wetRec);
                    wetRec.put();

                } else {                 // Search record = where,update record.

                    new WetPeriodCounts().upd(wetRec,where);

                } //if (checkRecord < 1)

            } // if (yearTotal > 0)

        } // for ( int i = start; i < end; i++)

    } // updatePeriodCounts

} // class
