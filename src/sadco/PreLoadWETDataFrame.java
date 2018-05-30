package sadco;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import oracle.html.*;

/**
 * This class does some pre-processing of weather data, and displays some
 * information on the screen.
 *
 * SadcoWET
 * pScreen.equals("load")
 * pVersion.equals("preload")
 *
 * @author 000719 - SIT Group
 * @version
 * 000719 - SIT Group - create class                                        <br>
 * 001009 - Ursula von St Ange - ub01 - take out check for one month - IPOSS
 *          data now always starts at 22:00 on last day of previous month   <br>
 * 001009 - Ursula von St Ange - ub02 - add test to see whether data
 *          partially loaded.                                               <br>
 * 001009 - Ursula von St Ange - ub03 - add test for current record date <
 *          previous record date.                                           <br>
 * 20060405 - Ursula von St Ange - add timezone for date/time correction
 *                                   to UTC (ub04)                          <br>
 * 20070612 - Ursula von St Ange - add code for testing of zero values (ub05)<br>
 * 20080519 - Ursula von St Ange - copy from weather and adapt for sadco    <br>
 * 20090508 - Ursula von St Ange - add institute address & new scientist(ub07)<br>
 */
public class PreLoadWETDataFrame extends CompoundItem {

    //boolean     dbg = false;
    boolean     dbg = true;

    static common.edmCommon ec = new common.edmCommon();

    //WeatherConstants wc = new WeatherConstants();
    SadConstants sc = new SadConstants();

    int         CODEN = Tables.CODENULL;
    int         INTN = Tables.INTNULL;
    String      CHARN = Tables.CHARNULL;
    float       FLOATN = Tables.FLOATNULL;
    Timestamp   DATEN = Tables.DATENULL;
    String      DATENSTR = DATEN.toString();

    // parameters
    String      fileName;
    String      timeZone = "";     // ub04
    //String      institCode = "";   // ub06
    String      menuNo = "";
    String      mVersion = "";
    String      sessionCode = "";
    String      userType = "";

    // WetPeriod variables
    String      stationId;
    int         instrumentCode = INTN;
    //int         heightSurface = INTN;
    float       heightSurface = FLOATN;
    //int         heightMSL = INTN;
    float       heightMSL = FLOATN;
    float       speedCorrFactor = FLOATN;
    String      speedAverMethod = CHARN;
    String      dirAverMethod = CHARN;
    int         windSamplingInterval = INTN;
    String      startDate = "";
    String      endDate = "";
    int         sampleInterval = INTN;
    int         numberRecords = 0;
    int         numberNullRecords = 0;

    // WetPeriodNotes variables
    String[]    notes = new String[15];
    int         noteCounter = -1;

    // WetEditLog variables
    String      editLog = "";

    // WetData variables
    int         periodCode = CODEN;
    String      dateTime;
    float       airTempAve;
    float       airTempMin;
    String      airTempMinTime = DATEN.toString();
    float       airTempMax;
    String      airTempMaxTime = DATEN.toString();
    float       barometricPressure;
    float       fog;
    float       rainfall;
    float       relativeHumidity;
    float       solarRadiation;
    float       solarRadiationMax;
    float       windDir;
    float       windSpeedAve;
    float       windSpeedMin;
    float       windSpeedMax;
    String      windSpeedMaxTime = DATEN.toString();
    int         windSpeedMaxLength;
    float       windSpeedMaxDir;
    float       windSpeedStd;

    // other work variables
    int[] startCol = new int[30];
    int[] endCol = new int[30];
    String[][] fieldNames =
        {{"wdtyy", "date year"},                          /* 0 */
         {"wdtmm", "date month"},                         /* 1 */
         {"wdtdd", "date day"},                           /* 2 */
         {"wtmhh", "time hour"},                          /* 3 */
         {"wtmmm", "time minute"},                        /* 4 */
         {"ata",   "Air temperature average"},            /* 5 */
         {"atmn",  "Air temperature min"},                /* 6 */
         {"atmnt", "Air temperature min time"},           /* 7 */
         {"atmx",  "Air temperature max"},                /* 8 */
         {"atmxt", "Air temperature max time"},           /* 9 */
         {"bp",    "Barometric Pressure"},                /* 10 */
         {"fg",    "Fog"},                                /* 11 */
         {"rf",    "Rainfall"},                           /* 12 */
         {"rh",    "Relative humidity"},                  /* 13 */
         {"sr",    "Solar radiation"},                    /* 14 */
         {"srmx",  "Solar radiation max"},                /* 15 */
         {"wd",    "Wind Direction"},                     /* 16 */
         {"wsa",   "Wind speed average"},                 /* 17 */
         {"wsmn",  "Wind speed min"},                     /* 18 */
         {"wsmx",  "Wind speed max (gust)"},              /* 19 */
         {"wsmxd", "Wind speed max (gust) duration"},     /* 20 */
         {"wsmxt", "Wind speed max (gust) time"},         /* 21 */
         {"wssd",  "Wind speed standard deviation"},      /* 22 */
         {"wsmxwd","Wind speed max (gust) direction"}};   /* 23 */
    int[] fieldNulls = {0, 0, 0, 0, 0, 90, 90, 24, 90, 24, 9000,
        900, 900, 900, 9000, 9000, 900, 90, 90, 90, 90, 24, 90, 900};
    boolean[] fieldZeroes =                                                     //ub05
        {false, false, false, false, false, false, false, false, false, false,  //ub05
         false, true, true, true, true, true, true, false, false, false,        //ub05
         false, false, false, false};                                           //ub05
    boolean[] fieldPresent = {false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false};

    // file stuff
    String      pathName;
    //String      pathName     = "/oracle/users/edm/data/weather/load/";
    RandomAccessFile lfile;
    String      line;

    boolean endOfData = false;
    boolean stationOK = true;
    boolean fieldsOK = true;
    boolean intervalOK = true;
    boolean intervalCheck = true;
    boolean dataLoaded = false;
    boolean dataPartLoaded = false;                        // ub02
    String codesLoaded = "";                               // ub02
    // boolean oneMonth = true;                            // ub01
    WetStation[]    stationA;
    WetPeriod[]     periodA;
    WetData[]       dataA;                                 // ub02
    EdmInstrument2[] instrumentA;


    Timestamp firstTs = DATEN;
    Timestamp secondTs = DATEN;
    long firstInterval = 0;
    long secondInterval = 0;
    int numberPossibleRecords = INTN;

    public PreLoadWETDataFrame() {
    } // PreLoadWETDataFrame

    public PreLoadWETDataFrame(String args[]) {

        doActual(args);

    } // constructor


    void doActual(String args[]) {

        // get the correct path name
        //if (ec.getHost().equals("morph")) {
        //    pathName = sc.MORPH + "load/";
        //} else {
        //    pathName = sc.LOCAL;
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL + "weather/";
        } else {
            pathName = sc.LOCALDIR + "weather/";
        } // if host

        if (dbg) System.out.println("PreLoadWETDataFrame pathName = " + pathName);

        // Create main table
        DynamicTable mainTable = new DynamicTable(0);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);

        // create the html page

        mainTable.addRow(ec.crSpanColsRow("Load Data", 2, true, "blue",
            IHAlign.CENTER, "+2"));
        //mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));

        // get the argument values
        getArgParms(args);

        // Does file exist?
        if (!ec.fileExists(pathName + fileName)) {
            mainTable = ec.displayFileNotFound(pathName + fileName);
        } else {
            processInputFile(mainTable);
        } // !fileExists

        // Main container                                            //
        Container con = new Container();
        con.addItem(mainTable.setCenter());
        addItem(con);

    } // doActual


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        fileName    = ec.getArgument(args,sc.FILENAME);
        timeZone    = ec.getArgument(args,sc.TIMEZONE).toLowerCase();   // ub04
        //institCode  = ec.getArgument(args,sc.INSTITCODE);               // ub06
        sessionCode = ec.getArgument(args,sc.SESSIONCODE);
        userType    = ec.getArgument(args, sc.USERTYPE);

        mVersion    = ec.getArgument(args,sc.MVERSION);
        menuNo      = ec.getArgument(args,sc.MENUNO);

        if (dbg) System.out.println ("<br>PreLoadWETDataFrame: fileName = " + fileName);
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

        } catch (Exception e) {
            System.out.println("<br>PreLoadWETDataFrame Error: " + e.getMessage());
            e.printStackTrace();
        }

        readHeaderLines(table);
        if (!fieldsOK) {
            displayFieldsNotOK(table);
        } else if (!stationOK) {
            displayStationNotOK(table);
        } else {
            headerDefaults();
            while (!endOfData) {
                readData(table);
            } // while
            checkDates();
            checkDataLoaded();
            if (!dataPartLoaded) {                     // ub02
                checkInstrument();
                displayPage(table);
            } else {                                   // ub02
                displayPartLoaded(table);              // ub02
            } // if (!dataPartLoaded)                  // ub02
        } // if (!fieldsOK) {
    } // void processInputFile() {


    /**
     * Process the header lines.
     */
    void readHeaderLines(DynamicTable table) {

        line = ec.getNextValidLine(lfile);
        String dataID = line.substring(0,3);
        if (dbg) System.out.println ("<br>dataID = " + dataID);
        while (!dataID.equals("DAT")) {
            // notes
            if ((dataID.equals("-- ")) || (dataID.equals("   "))) {
                noteCounter++;
                notes[noteCounter] = line;
                if (notes[noteCounter].length() > 79) {
                    notes[noteCounter] = notes[noteCounter].substring(0,79);
                } // if length > 79

                if (dbg) System.out.println ("<br>notes = " + line);
            } // if ((dataID.equals("-- ") || (dataID.equals("   ")) {

            // processing log
            if (dataID.equals("Dat")) {
                editLog = line;
            } // if (dataID.equals("Dat") {

            //  stationId
            if (dataID.equals("STA")) {
                //STATION CODE: JA01
                if (line.substring(8,12).equals("CODE")) {
                    stationId = line.substring(14,18);
                    if (dbg) System.out.println ("<br>stationId = " + stationId);
                    stationA = new WetStation(stationId).get();
                    if (stationA.length == 0) {
                        stationOK = false;
                    }
                } // if (line.substring(8,12).equals("CODE")) {
            } // if (dataID.equals("STA") {

            // header fields
            if (dataID.substring(0,1).equals(">")) {
                //int spaceIndex = line.indexOf(' ');

                int spaceIndex = line.indexOf(' ');
                if (spaceIndex == -1) {
                    spaceIndex = line.indexOf('\t');
                } //if ( (line.indexOf(' ') {

                if (dbg) System.out.println ("<br> spaceIndex "+spaceIndex+" dataID = "+dataID);
                if (dbg) System.out.println ("<br> line "+line);

                String name = line.substring(1, spaceIndex);
                String data = line.substring(spaceIndex).trim();
                if (dbg) System.out.println ("<br>name, data = *" + name +
                    "*" + data + "*");
                storeHeader(name, data);
            } // if (dataID.substring(0,1) == '>') {

            // field names
            if (dataID.substring(0,1).equals(":")) {
                int colonIx1 = line.indexOf(':',1);
                int colonIx2 = line.indexOf(':', colonIx1+1);
                int gtIx = line.indexOf('>');
                int start = new Integer(
                    line.substring(1, colonIx1).trim()).intValue() - 1;
                int end = start + new Integer(
                    line.substring(colonIx1+1, colonIx2).trim()).intValue();
                String name = line.substring(gtIx+1).trim();
                if (dbg) System.out.println ("<br>start, end, name = *" + start +
                    "*" + end + "*" + name + "*");
                if (!checkStoreFieldNames(start, end, name)) {
                    fieldsOK = false;
                    table.addRow(ec.cr1ColRow("Unrecognized field code: " + name,
                        false,"red",2));
                } // if !checkStoreFieldNames
            } // if (dataID.substring(0,1) == ':') {

            line = ec.getNextValidLine(lfile);
            dataID = line.substring(0,3);
        } // while (!dataID.equals("DAT") {

    }   //  void readHeaderLines()

    /**
     * Process the data lines.
     */
    boolean readData(DynamicTable table) {

        boolean dataOK = true;                             // ub03
        line = ec.getNextValidLine(lfile);
        String dataID = line.substring(0,3);
        if (dbg) System.out.println ("<br>=== dataID = " + dataID);
        try {
        if (!dataID.equals("END")) {
            numberRecords++;
            dateTime = line.substring(startCol[0],endCol[0]) + "-" +
                       line.substring(startCol[1]+1,endCol[1]) + "-" +
                       line.substring(startCol[2]+1,endCol[2]) + "@" +
                       line.substring(startCol[3]+1,endCol[3]) + ":" +
                       line.substring(startCol[4]+1,endCol[4]) + ":00";
            dateTime = dateTime.replace(' ','0');
            dateTime = dateTime.replace('@',' ');

            if (dbg) System.out.println("<br>=== dateTime = " + dateTime);

            if ("sast".equals(timeZone)) {              // ub04
                java.util.GregorianCalendar calDate = new java.util.GregorianCalendar();
                //calDate.setTime(Timestamp.valueOf(dateTime + ".0"));
                calDate.setTime(Timestamp.valueOf(dateTime));
                calDate.add(java.util.Calendar.HOUR, -2);
                java.text.SimpleDateFormat formatter =
                    new java.text.SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                dateTime = formatter.format(calDate.getTime());
                if (dbg) System.out.println("<br>=== dateTime2 = " + dateTime);
            } // if ('sast'.equals(timeZone))

            if (numberRecords == 1) {
                startDate = dateTime;
                firstTs = Timestamp.valueOf(dateTime);
            } else { //if (numberRecords > 1) {
                secondTs = Timestamp.valueOf(dateTime);
                if (firstTs.before(secondTs)) {
                    // get difference in milliseconds and convert to minutes
                    secondInterval =
                        (secondTs.getTime() - firstTs.getTime()) / 60000;

                    if (numberRecords == 2) {
                        sampleInterval = (int) secondInterval;
                    } // if
                    if (dbg) System.out.println ("<br> secondInterval = " +
                        secondInterval);

                    if ((numberRecords > 2) && intervalCheck) {
                        if (firstInterval != secondInterval) {
                            intervalOK = false;
                        } // if first <> second
                    } // if (numberRecords > 2) {

                    firstTs = secondTs;
                    firstInterval =  secondInterval;
                } else {
                    dataOK = false;
                    numberRecords--;
                } // if (firstTs.before(secondTs))
            }//  if (numberRecords == 1) {
            endDate = dateTime;

            if (dataOK) {
                airTempAve = floatValue(line, 5);
                airTempMin = floatValue(line, 6);
                airTempMinTime = timeValue(line, 7);
                airTempMax = floatValue(line, 8);
                airTempMaxTime = timeValue(line, 9);
                barometricPressure = floatValue(line, 10);
                fog = floatValue(line, 11);
                rainfall = floatValue(line, 12);
                relativeHumidity = floatValue(line, 13);
                solarRadiation = floatValue(line, 14);
                solarRadiationMax = floatValue(line, 15);
                windDir = floatValue(line, 16);
                windSpeedAve = floatValue(line, 17);
                windSpeedMin = floatValue(line, 18);
                windSpeedMax = floatValue(line, 19);
                windSpeedMaxLength = intValue(line, 20);
                windSpeedMaxTime = timeValue(line, 21);
                windSpeedStd = floatValue(line, 22);
                windSpeedMaxDir = floatValue(line, 23);
                numberNullRecords += nullRecord();
                if (nullRecord() == 1) {
                    dataOK = false;
                    //numberRecords--;
                } // if (nullRecord() == 1) {
            } // if dataOK
            if (dbg) printDebug();

        } else {

            endOfData = true;

        } // if (!dataID.equals("END") {
        return dataOK;

    }   //  void readData()

    /**
     * check whether data in one month, and get number of possible records
     */
    void checkDates () {

        // get number of possible records
        firstTs = Timestamp.valueOf(startDate);
        secondTs = Timestamp.valueOf(endDate);
        secondInterval =  (secondTs.getTime() - firstTs.getTime()) / 60000;
        numberPossibleRecords = (int) (secondInterval / sampleInterval) + 1;

        // check if dates in one month
        //if (!startDate.substring(5,7).equals(endDate.substring(5,7))) {// ub01
        //    oneMonth = false;                                          // ub01
        //}                                                              // ub01
        //if (dbg) System.out.println ("<br> oneMonth = " + oneMonth);   // ub01

    } // checkDates


    /**
     * store the header field data values in the header array
     */
    void storeHeader(String name, String data) {
        int intData     = new Float(data).intValue();
        float floatData = new Float(data).floatValue();

        //int intData = Float.parseFloat(data).intValue();

        if (name.equals("win"))  instrumentCode = intData;
        if (name.equals("whcf")) speedCorrFactor = intData;
        if (name.equals("wsma")) speedAverMethod = data;
        if (name.equals("wdma")) dirAverMethod = data;
        if (name.equals("wsp"))  windSamplingInterval = intData;
        if (name.equals("wsh"))  heightSurface = floatData;
        if (name.equals("wshm")) heightMSL = floatData;


    } // storeHeader


    /**
     * set some defaults for header items if they have not been set
     */
    void headerDefaults() {
        if ((instrumentCode == INTN) ||
            //(heightSurface == INTN) ||
            (heightSurface == FLOATN) ||
            //(heightMSL == INTN) ||
            (heightMSL == FLOATN) ||
            (windSamplingInterval == INTN)) {

            String where = WetPeriod.STATION_ID + "='" + stationId + "'";
            String order = WetPeriod.CODE;
            periodA = new WetPeriod().get(where, order);
            if (periodA.length > 0) {
                if (instrumentCode == INTN) {
                    instrumentCode = periodA[periodA.length-1].getInstrumentCode();
                } // instrumentCode
                //if (heightSurface == INTN) {
                if (heightSurface == FLOATN) {
                    heightSurface = periodA[periodA.length-1].getHeightSurface();
                } // heightSurface
                //if (heightMSL == INTN) {
                if (heightMSL == FLOATN) {
                    heightMSL = periodA[periodA.length-1].getHeightMsl();
                } // heightMSL
                if (windSamplingInterval == INTN) {
                    windSamplingInterval =
                        periodA[periodA.length-1].getWindSamplingInterval();
                } // windSamplingInterval
            } // if (periodA.length > 0) {

        } // if ...

    } // headerDefaults


    /**
     * check and store field names
     */
    boolean checkStoreFieldNames(int start, int end, String name) {

        boolean found = true;
        int i;
        for (i = 0; i < fieldNames.length; i++) {
            if (name.equals(fieldNames[i][0])) break;
        } // for int i
        if (i <  fieldNames.length) {
            //if (dbg) System.out.println ("<br> i, fieldNames = " + i + " " +
            //    fieldNames[i][0]);

            startCol[i] = start;
            endCol[i] = end;
            fieldPresent[i] = true;

            if (dbg) System.out.println ("<br> fields status : i "+i+
                " fieldName "+fieldNames[i][0]+" start "+startCol[i]+
                " end "+endCol[i]+" "+fieldPresent[i]);
        } else {
            found = false;
        } // if (i < ...

        return found;

    } // checkStoreFieldNames


    /**
     * check whether this is a null record
     */
    int nullRecord() {
        int count = 0;
        if ((airTempAve == FLOATN) &&
            (airTempMin == FLOATN) &&
            (airTempMinTime.equals(DATENSTR)) &&
            (airTempMax == FLOATN) &&
            (airTempMaxTime.equals(DATENSTR)) &&
            (barometricPressure == FLOATN) &&
            (fog == FLOATN) &&
            (rainfall == FLOATN) &&
            (relativeHumidity == FLOATN) &&
            (solarRadiation == FLOATN) &&
            (solarRadiationMax == FLOATN) &&
            (windDir == FLOATN) &&
            (windSpeedAve == FLOATN) &&
            (windSpeedMin == FLOATN) &&
            (windSpeedMax == FLOATN) &&
            (windSpeedMaxTime.equals(DATENSTR)) &&
            (windSpeedMaxLength == INTN) &&
            (windSpeedMaxDir == FLOATN) &&
            (windSpeedStd == FLOATN)) {
            count = 1;
        }
        return count;
    } // nullRecord


    /**
     * check to see whether this period has been loaded
     */
    void checkDataLoaded() {

        // get start of month
        // ub - just use start and end date of input data because
        // iposs data always starts at 22:00 on last day of last
        // month
        String start = startDate;
        String end = endDate;
        //String start = startDate.substring(0,7) + "-01 00:00:00"; //ub01
        ////String start = "1999-12-01 00:00:00";                   //ub01
        //                                                          //ub01
        //// get the end of the month                               //ub01
        //int month = Integer.parseInt(start.substring(5,7)) + 1;   //ub01
        //String newYear = start.substring(0,4);                    //ub01
        //if (month == 13) {                                        //ub01
        //    month = 1;                                            //ub01
        //    newYear = String.valueOf(Integer.parseInt(newYear) + 1);//ub01
        //} // month = 13                                           //ub01
        //String newMonth = "";                                     //ub01
        //if (month < 10) {                                         //ub01
        //    newMonth = "0" + String.valueOf(month);               //ub01
        //} else {                                                  //ub01
        //    newMonth =  String.valueOf(month);                    //ub01
        //} // if month < 10                                        //ub01
        //if (dbg) System.out.println ("<br>newMonth = " + newMonth);//ub01
        //String newDate = newYear + "-" + newMonth + start.substring(7,10)//ub01
        //    + " 23:59:59";                                        //ub01
        //if (dbg) System.out.println ("<br>newDate = *" + newDate + "*");//ub01
        //Timestamp endTS = Timestamp.valueOf(newDate);             //ub01
        //// 86400000 == 1 day in milliseconds                      //ub01
        //endTS = new Timestamp(endTS.getTime() - 86400000);        //ub01
        //String end = endTS.toString().substring(0,19);            //ub01

        // get the end of the month - for JDK 116
        //GregorianCalendar calDate = new GregorianCalendar();
        //calDate.setTime(Timestamp.valueOf(startDate));
        //calDate.set(Calendar.DATE, calDate.getActualMaximum(Calendar.DATE));
        //SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        //String end = formatter.format(calDate.getTime());
        if (dbg) System.out.println ("<br>start = " + start);
        if (dbg) System.out.println ("<br>end = " + end);

        // do the periods match exactly?
        // set up where clause and get data
        String where = WetPeriod.STATION_ID + "='" + stationId +
            "' and " + WetPeriod.START_DATE + " = " + Tables.getDateFormat(start) +
            " and " + WetPeriod.END_DATE + " = " + Tables.getDateFormat(end);
        if (dbg) System.out.println ("<br>where = " + where);
        periodA = new WetPeriod().get(where);
        if (periodA.length != 0) {
            periodCode = periodA[0].getCode();
            dataLoaded = true;
        } else {                                                 // ub02
            // how many periodcodes are involved?
            where = WetData.STATION_ID + "='" + stationId +      // ub02
                "' and " + WetData.DATE_TIME + " >= " +          // ub02
                Tables.getDateFormat(start) +                    // ub02
                " and " + WetData.DATE_TIME + " <= " +           // ub02
                Tables.getDateFormat(end);                       // ub02
            dataA = new WetData().get(where);                    // ub02
            if (dataA.length > 0) {                              // ub02
                codesLoaded += dataA[0].getPeriodCode("");       // ub02
                for (int i=1; i<dataA.length; i++) {             // ub02
                    if (dataA[i-1].getPeriodCode() !=            // ub02
                            dataA[i].getPeriodCode()) {          // ub02
                        codesLoaded += ", " + dataA[i].getPeriodCode("");
                    } // if                                      // ub02
                } // for i                                       // ub02
                dataPartLoaded = true;                           // ub02
            } // if data                                         // ub02
        } // if (!periodA.length == 0) {

    } // checkDataLoaded


    /**
     * check the instrument code
     */
    void checkInstrument() {
        // default instrument is none/unknown (code = 11)
        if (instrumentCode < 10) {
            instrumentCode = instrumentCode + 10;
        } else if (instrumentCode == INTN) {
            instrumentCode = 11;
        } // if (instrumentCode < 10) {
        instrumentA = new EdmInstrument2(instrumentCode).get();
    } // checkInstrument


    /**
     * message to be displayed when the run is aborted
     */
    void displayFieldsNotOK(DynamicTable table) {
        table.addRow(ec.cr1ColRow("&nbsp;"));
        table.addRow(ec.cr1ColRow("There are unidentified field codes", true, "", 2));
        table.addRow(ec.cr1ColRow("The run has aborted ...", true, "", 2));
        table.addRow(ec.cr1ColRow("&nbsp;"));
        table.addRow(ec.cr1ColRow("The valid codes are:"));
        DynamicTable sub = new DynamicTable(0);
        for (int i = 0; i < fieldNames.length; i++) {
            sub.addRow(ec.cr2ColRow(fieldNames[i][0] + ": ",fieldNames[i][1]));
        } // for i
        table.addRow(ec.cr1ColRow(sub.toHTML()));

    } // displayFieldsNotOK


    /**
     * message to be displayed when the run is aborted
     */
    void displayStationNotOK(DynamicTable table) {

        table.addRow(ec.cr1ColRow("&nbsp;"));
        table.addRow(ec.cr1ColRow(
            "This data file contains data for Station-ID " + stationId,
            true, "", 2));
        table.addRow(ec.cr1ColRow(
            "This station ID is not registered!", true, "", 2));
        table.addRow(ec.cr1ColRow("The run has aborted ...", true, "", 2));

    } // displayStationNotOK


    /**
     * message to be displayed when the run is aborted
     */
    void displayNotOneMonth(DynamicTable table) {

        table.addRow(ec.cr1ColRow("&nbsp;"));
        table.addRow(ec.cr1ColRow(
            "This data file contains data for more than one month " + stationId,
            true, "", 2));
        table.addRow(ec.cr1ColRow("The run has aborted ...", true, "", 2));

    } // displayNotOneMonth


    /**
     * message to be displayed when the run is aborted
     */
    void displayPartLoaded(DynamicTable table) {                // ub02

        table.addRow(ec.cr1ColRow("&nbsp;"));
        displayLoadInfo(table);
//            sub.addRow(ec.crSpanColsRow("WARNING: The sampling intervals " +
//                "are not consistent.",2,true,"red",2));
        table.addRow(ec.crSpanColsRow(
            "Some or all of the data is already loaded, but the date range " +
            "is not exactly the same as an existing period.",
            2, true, "red", 1));
        table.addRow(ec.crSpanColsRow(
            "The period code(s) for the loaded data: " + codesLoaded,
            2, true, "", 1));
        table.addRow(ec.crSpanColsRow(
            "Use the inventories to check the data ranges for the period " +
            "code(s) above. ", 2, true, "", 1));
        table.addRow(ec.crSpanColsRow(
            "Change the load file by either adding dummy data, or " +
            "deleting data at the beginning and/or end of the file.",
            2, true, "", 1));
        table.addRow(ec.crSpanColsRow("The run has aborted ...",
            2, true, "red", 2));

    } // displayNotOneMonth


    /**
     *  display the page with warnings
     */
    void displayPage(DynamicTable table) {

        //Form form = new Form("get","EdmWeather","middle");
        Form form = new Form("get",sc.WET_APP);
        DynamicTable sub = new DynamicTable(0);
        sub.setFrame(ITableFrame.VOLD);
        sub.setRules(ITableRules.NONE);
        sub.setBorder(0);
        //sub.setFrame(ITableFrame.BOX);

        displayLoadInfo(sub);
        sub.addRow(ec.crSpan2ColRow("<br>"));
        sub.addRow(ec.crSpan2ColRow("If the Instrument is not correct, " +
            "choose another existing instrument or enter a new one"));
        sub.addRow(ec.cr2ColRow(
            "Instrument:", createInstrumentSelect(instrumentCode)));
        sub.addRow(ec.cr2ColRow("New Instrument",sc.NEWINSTRUMENT,30));
        sub.addRow(ec.crSpan2ColRow("<br>"));
        sub.addRow(ec.crSpan2ColRow("Choose an existing institute or enter a new one"));    // ub06
        sub.addRow(ec.cr2ColRow("Institute:",createInstituteSelect(sc.INSTITCODE)));                     // ub06
        sub.addRow(ec.cr2ColRow("New Institute",sc.NEWINSTITUTE,50));
        sub.addRow(ec.cr2ColRow("New Institute Address",sc.NEWINSTADDRESS,50));             // ub07
        sub.addRow(ec.crSpan2ColRow("Choose an existing scientist or enter a new one"));    // ub06
        sub.addRow(ec.cr2ColRow("Scientist:",createScientistSelect()));                     // ub07
        sub.addRow(ec.cr2ColRow("New Scientist Title",sc.NEWSCTITLE,10));                   // ub07
        sub.addRow(ec.cr2ColRow("New Scientist Initials",sc.NEWSCFIRSTNAME,10));            // ub07
        sub.addRow(ec.cr2ColRow("New Scientist Surname",sc.NEWSCSURNAME,50));               // ub07
        sub.addRow(ec.cr2ColRow("New Scientist Institute:",                                 // ub07
            createInstituteSelect(sc.NEWSCINST)));                                          // ub07
        if (!intervalOK) {
            sub.addRow(ec.crSpanColsRow("<br>WARNING: The sampling intervals " +
                "are not consistent.",2,true,"red",2));
        } // !intervalOK
        if (dataLoaded) {
            sub.addRow(ec.crSpanColsRow("<br>WARNING: The data is already " +
                "loaded. (period code " + periodCode + ")<br>If you " +
                "continue, the old data will be overwritten",2,true,"red",2));
        } // dataLoaded

        sub.addRow(ec.crSpan2ColRow("<br>"));
        sub.addRow(ec.crSpan2ColRow("If you want to continue loading, " +
            "press the 'Go!' button"));
        sub.addRow(ec.crSpan2ColRow("<br>"));

        form.addItem(sub);
        form.addItem(new Submit("","Go!").setCenter());
        form.addItem(new Hidden(sc.SCREEN,"load"));
        form.addItem(new Hidden(sc.VERSION,"load"));
        form.addItem(new Hidden(sc.USERTYPE,userType));
        form.addItem(new Hidden(sc.SESSIONCODE,sessionCode));
        //form.addItem(new Hidden("pversion","preload"));
        form.addItem(new Hidden(sc.FILENAME,fileName));
        form.addItem(new Hidden(sc.TIMEZONE,timeZone));
        if (dataLoaded) {
            form.addItem(new Hidden(sc.CODE,String.valueOf(periodCode)));
        } // dataLoaded
        form.addItem(new Hidden(sc.STARTDATE,startDate));
        form.addItem(new Hidden(sc.ENDDATE,endDate));
        form.addItem(new Hidden(sc.INTERVAL,String.valueOf(sampleInterval)));
        form.addItem(new Hidden(sc.RECORDS,String.valueOf(numberRecords)));
        form.addItem(new Hidden(sc.NULLS,String.valueOf(numberNullRecords)));
        // for the menu system
        form.addItem(new Hidden(sc.MENUNO,menuNo));
        form.addItem(new Hidden(sc.MVERSION,mVersion));

        table.addRow(ec.cr1ColRow(form.toHTML()));

    } // displayPage


    /**
     *  display the lod information
     */
    void displayLoadInfo(DynamicTable sub) {

        sub.addRow(ec.crSpan2ColRow("<br>"));
        sub.addRow(ec.cr2ColRow("Station:",
            stationId + " - " + stationA[0].getName()));
        sub.addRow(ec.cr2ColRow("Position:", stationA[0].getLatitude("") +
            "S, " + stationA[0].getLongitude("") + "E"));
        sub.addRow(ec.crSpan2ColRow("<br>"));
        sub.addRow(ec.cr2ColRow("Start Date:", startDate));
        sub.addRow(ec.cr2ColRow("End Date:", endDate));
        sub.addRow(ec.cr2ColRow("Height above Surface:", heightSurface+" "));
        sub.addRow(ec.cr2ColRow("Height above MSL:", String.valueOf(heightMSL)));
        sub.addRow(ec.cr2ColRow("Measuring Interval:", sampleInterval + " minutes"));
        sub.addRow(ec.cr2ColRow("Max No of Records Possible:", numberPossibleRecords));
        sub.addRow(ec.cr2ColRow("No of Records (excl Null):",
            numberRecords - numberNullRecords));
        sub.addRow(ec.cr2ColRow("No of Null Records:",
            numberNullRecords));
        sub.addRow(ec.cr2ColRow("No of Missing Records:",
            numberPossibleRecords - numberRecords));

    } // displayLoadInfo


    /**
     * create the combo-box (pull-down box) for instruments
     */
    Select createInstrumentSelect(int code) {
        Select sel = new Select(sc.INSTRUMENTCODE);
        EdmInstrument2[] instrumentA =
            new EdmInstrument2().get("1=1",EdmInstrument2.CODE);
        for (int i = 0; i < instrumentA.length; i++) {
            boolean defaultOption = false;
            if (instrumentA[i].getCode() == code) { defaultOption = true; }
            sel.addOption(new Option(
                instrumentA[i].getName() + "("+instrumentA[i].getCode("")+")",
                instrumentA[i].getCode(""),
                defaultOption
                ));
        } // for i
        return sel;
    } // createInstrumentSelect


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
        Select sel = new Select(sc.SCIENTCODE);                         //ub07
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


    /**
     * get the float value
     */
    float floatValue (String line, int index) {
        //if (dbg) System.out.println("<br>line, index = " + line + " " + index);
        float value = FLOATN;
        if (fieldPresent[index]) {
            String temp = line.substring(startCol[index],endCol[index]).trim();

            value =  new Float(temp).floatValue();
            //if (dbg) System.out.println("<br> value "+value+" line "+line+
            //    " index "+index+" temp "+temp+ " fieldNulls[index] "+fieldNulls[index]);

            if (value >= fieldNulls[index]) {
                value = FLOATN;
            } // if value
            if (!fieldZeroes[index]) {              //ub05
                if (value <= 0f) value = FLOATN;    //ub05
            } // if (!fieldZeroes[index])           //ub05
        } // if fieldPresent

        return value;
    } // floatValue


    /**
     * get the int value
     */
    int intValue (String line, int index) {
        int value = INTN;
        if (fieldPresent[index]) {
            String temp = line.substring(startCol[index],endCol[index]).trim();
            value =  new Integer(temp).intValue();
            if (value >= fieldNulls[index]) {
                value = INTN;
            } // if value
            if (!fieldZeroes[index]) {              //ub05
                if (value <= 0) value = INTN;       //ub05
            } // if (!fieldZeroes[index])           //ub05
        } // if fieldPresent
        return value;
    } // intValue


    /**
     * get the time value
     */
    String timeValue (String line, int index) {
        String value = DATENSTR;
        if (fieldPresent[index]) {
            String temp = line.substring(startCol[index],endCol[index]).trim();
            float tmp = new Float(temp).floatValue();
            if (tmp < fieldNulls[index]) {
                int inx = 11;
                if (tmp < 10) inx = 12;
                value =  value.substring(0,inx) + temp.replace('.',':') + ":00";
            } // if tmp
        } // if fieldPresent
        return value;
    } // timeValue


    /**
     * print the WetData values for debug
     */
    void printDebug() {
        System.out.println ("<br>dateTime = " + dateTime);
        System.out.println ("<br>airTempAve = " + airTempAve);
        System.out.println ("<br>airTempMin = " + airTempMin);
        System.out.println ("<br>airTempMinTime = " + airTempMinTime);
        System.out.println ("<br>airTempMax = " + airTempMax);
        System.out.println ("<br>airTempMaxTime = " + airTempMaxTime);
        System.out.println ("<br>barometricPressure = " + barometricPressure);
        System.out.println ("<br>fog = " + fog);
        System.out.println ("<br>rainfall = " + rainfall);
        System.out.println ("<br>relativeHumidity = " + relativeHumidity);
        System.out.println ("<br>solarRadiation = " + solarRadiation);
        System.out.println ("<br>solarRadiationMax = " + solarRadiationMax);
        System.out.println ("<br>windDir = " + windDir);
        System.out.println ("<br>windSpeedAve = " + windSpeedAve);
        System.out.println ("<br>windSpeedMin = " + windSpeedMin);
        System.out.println ("<br>windSpeedMax = " + windSpeedMax);
        System.out.println ("<br>windSpeedMaxTime = " + windSpeedMaxTime);
        System.out.println ("<br>windSpeedMaxLength = " + windSpeedMaxLength);
        System.out.println ("<br>windSpeedMaxDir = " + windSpeedMaxDir);
        System.out.println ("<br>windSpeedStd = " + windSpeedStd);
    } // printDebug

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("PreLoadWETDataFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PrePreLoadWETDataFrame local= new PrePreLoadWETDataFrame(args);
            bd.addItem(new PreLoadWETDataFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "PreLoadWETDataFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class PreLoadWETDataFrame
