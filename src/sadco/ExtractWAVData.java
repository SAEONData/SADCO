package sadco;

import common2.Queue;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class extracts the SADCO waves data from the database
 *
 * SadcoWAVApps
 * screen.equals("extract")
 * version.equals("doextract")
 *
 * @author 080222 - SIT Group
 * @version
 * 080222 - Ursula von St Ange - create class                               <br>
 * 091214 - Ursula von St Ange - change for inventory user (ub01)           <br>
 * 100105 - Ursula von St Ange - change for queuing of job (ub02)           <br>
 * 100615 - Ursula von St Ange - non-inventory users don't queue (ub03)     <br>
 * 100624 - Ursula von St Ange - change logging of job to beginning with
 *                               update at end (ub04)                       <br>
 */

public class ExtractWAVData {

    boolean dbg = false;
    //boolean dbg = true;
    boolean debug = false;
    //boolean debug = true;
    boolean dbgQ = false;
    //boolean dbgQ = true;

    String thisClass = this.getClass().getName();

    // contains the method to parse URL-type arguments
    common.edmCommonPC ec = new common.edmCommonPC();

    // url parameters names & applications
    sadco.SadConstants sc = new sadco.SadConstants();

    // parameters from html page
    String sessionCode    = "";
    String reqno          = "";
    String fileNameIn     = "";                                         //ub02
    String fileName       = "";
    String startDate      = "";
    String endDate        = "";
    String surveyId       = "";
    String latitudeNorth  = "";
    String latitudeSouth  = "";
    String longitudeWest  = "";
    String longitudeEast  = "";
    String userType       = "";                                         //ub01

    // date header info
    long datePosition[]   = null;
    Timestamp realStartDate[] = null;
    Timestamp realEndDate[] = null;
    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");

    // work variables
    WavStation station[] = null;
    WavData data[]       = null;
    WavData wData        = new WavData();
    int noRecords        = 0;
    String latitudeHeader = "";
    String longitudeHeader = "";

    // the files
    RandomAccessFile file;
    RandomAccessFile debugFile;

    static Queue queue = null;                                          // ub02


    /**
     *  constructor
     */
    public ExtractWAVData(String args[]) {

        // get 'commandline' arguments
        getArgParms(args);

        // extract the user record from db
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        String userid = sessions[0].getUserid();

        // get the correct PATH NAME
        String rootPath = "";
        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                rootPath = sc.HOSTDIR;                    //ub01
            } else {                                                    //ub01
                rootPath = sc.HOSTDIR;                   //ub01
            } // if ("0".equals(userType))                              //ub01
        } else {
            rootPath = sc.LOCALDIR + "waves/";
        } // if host
        if (dbg) System.out.println("rootPath "+rootPath);

        // new filename, but keep the original file name                //ub02
        fileNameIn = fileName;                                          //ub02
        if (!"".equals(reqno)) {
            fileName = reqno + "_" + fileName;
            if (dbg) System.out.println("<br> fileName "+fileName);
        } // if (!"".equals(reqno))

        fileName = fileName + ".wav";
        if (dbg) System.out.println("fileName " + fileName);

        // delete an old dummy file for Progress purposes ......
        String dummyFile = rootPath + fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        // delete old debug file
        String dbgFile   = rootPath + fileName + ".debug";
        if (debug) ec.deleteOldFile(dbgFile);

        // create .queued file for Progress purposes ......             //ub02
        String dummyFile2 = rootPath + fileName + ".queued";            //ub02
        //sadinv.Queue queue = null;

        Timestamp startQDate = new Timestamp(new Date().getTime());     //ub04
        if ("0".equals(userType)) {  // from inventory                  //ub03
            // create .queued file for Progress purposes ......         //ub02
            ec.createNewFile(dummyFile2);                               //ub02

            // queue the extraction                                     //ub02
            if (!dbgQ) {
                queue = new Queue(userid,thisClass+" "+fileNameIn,args);//ub02
                queue.enQueue();                                        //ub02
                queue.wait4Turn();                                      //ub02
            } // if (!dbgQ)

            // delete .queued file, and create .progressing file        //ub02
            ec.deleteOldFile(dummyFile2);                               //ub02
        } // if ("0".equals(userType)) {                                //ub03

        // create the user_log entry                                    //ub04
        Timestamp endQDate = new Timestamp(new Date().getTime());       //ub04
        LogUsers log = createLogUsers(                                  //ub04
            userid, endQDate, ec.timeDiff(endQDate, startQDate));       //ub04

        // create the 'processing' file
        dummyFile2 = rootPath + fileName + ".processing";
        ec.createNewFile(dummyFile2);

        // delete any old data file.
        ec.deleteOldFile(rootPath + fileName);

        // open output file & debug file
        if (debug) debugFile = ec.openFile(rootPath+fileName+".debug", "rw");
        if (debug) ec.writeFileLine(debugFile,"chmod 644 " + rootPath + fileName);
        file = ec.openFile(rootPath + fileName , "rw");
        if (ec.getHost().startsWith(sc.HOST)) {
            System.out.println("chmod 644 " + fileName);
            ec.submitJob("chmod 644 " + rootPath + fileName);
        } //if (ec.getHost().startsWith(sc.HOST)) {

        if (debug) {
            ec.writeFileLine(debugFile,"Debugfile class ExtractWAVData ");
            ec.writeFileLine(debugFile,"");
            ec.writeFileLine(debugFile,"fileName  : " + fileName);
            ec.writeFileLine(debugFile,"startDate : " + startDate);
            ec.writeFileLine(debugFile,"endDate   : " + endDate);
            ec.writeFileLine(debugFile,"surveyID  : " + surveyId);
            ec.writeFileLine(debugFile,"lat1      : " + latitudeNorth);
            ec.writeFileLine(debugFile,"lat2      : " + latitudeSouth);
            ec.writeFileLine(debugFile,"long1     : " + longitudeWest);
            ec.writeFileLine(debugFile,"long2     : " + longitudeEast);
        } // if (dbg)

        // get the actual wave data
        if (dbg) System.out.println(" before getWaveData function ");
        getWaveData();

        // call header function.
        if (dbg) System.out.println(" before printDataHeader function ");
//        updateHeader();

        // close files.
        ec.closeFile(file);
        if (debug) ec.closeFile(debugFile);

        Timestamp endJDate = new Timestamp(new Date().getTime());       //ub04
        updateLogUsers(log, ec.timeDiff(endJDate, endQDate));           //ub04

        // delete the .processing file
        ec.deleteOldFile(dummyFile2);
        ec.createNewFile(dummyFile);

        // dequeue extraction                                           //ub02
        if ("0".equals(userType)) {  // from inventory                  //ub03
            if (!dbgQ) {
                queue.deQueue();                                        //ub02
            } // if (!dbgQ)
        } // if ("0".equals(userType))                                  //ub03

        // close the connection to the database
        common2.DbAccessC.close();

    } // constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    private void getArgParms(String args[])   {
        sessionCode   = ec.getArgument(args, SadConstants.SESSIONCODE);
        reqno         = ec.getArgument(args,SadConstants.REQNUMBER);
        fileName      = ec.getArgument(args,SadConstants.FILENAME);
        startDate     = ec.getArgument(args,SadConstants.STARTDATE);
        endDate       = ec.getArgument(args,SadConstants.ENDDATE);
        surveyId      = ec.getArgument(args,SadConstants.SURVEYID);
        latitudeNorth = ec.getArgument(args,SadConstants.LATITUDENORTH);
        latitudeSouth = ec.getArgument(args,SadConstants.LATITUDESOUTH);
        longitudeWest = ec.getArgument(args,SadConstants.LONGITUDEWEST);
        longitudeEast = ec.getArgument(args,SadConstants.LONGITUDEEAST);
        userType      = ec.getArgument(args,SadConstants.USERTYPE);     //ub01
    }   //  public void getArgParms()


    /**
     * Get the wave data
     */
    void getWaveData() {

        String wavStationWhere = "";

        if (dbg) System.out.println("<br>surveyId : " + surveyId);
        if (surveyId.length() > 0)  {
            wavStationWhere = WavStation.SURVEY_ID + " = '" + surveyId + "'";
        } //  if (surveyId.length() > 0)  {

        // If there are area co-ordinates
        if (latitudeNorth.length() > 0)  {
            wavStationWhere =
                "LATITUDE BETWEEN " + latitudeNorth + " and " + latitudeSouth +
                " and " +
                "LONGITUDE BETWEEN " + longitudeWest + " and " + longitudeEast;
        }   //  if (latitudeNorth.length() > 0 )

        if (dbg) System.out.println("wavStationWhere = " + wavStationWhere);
        station = new WavStation().get(wavStationWhere);
        if (dbg) System.out.println("station.length = " + station.length);

        createAndPrintHeaders(station);

        // get the data
        int startYear = Integer.parseInt(startDate.substring(0,4));
        int endYear   = Integer.parseInt(  endDate.substring(0,4));

        Timestamp startTVal = Timestamp.valueOf(startDate+" 00:00:00.0");
        Timestamp endTVal   = Timestamp.valueOf(endDate+" 23:59:00.0");

        Timestamp newStartTVal;
        Timestamp newEndTVal;

        realStartDate = new Timestamp[station.length];
        realEndDate = new Timestamp[station.length];

        String newStart  = "";
        String newEnd    = "";

        // station loop
        for (int i = 0; i < station.length; i++) {
            if (dbg) System.out.println("<br>ExtractWAVData:  i value in station loop "+i);

            realStartDate[i] = endTVal;
            realEndDate[i] = startTVal;

            for (int year = startYear; year <= endYear; year++) {
                if (dbg) System.out.println("<br>ExtractWAVData:  year value in year loop "+year);

                for (int subYear = 0; subYear < 4; subYear++) {         //ub02

                    switch (subYear) {                                    //ub02
                        case 0:                                           //ub02
                                newStart = String.valueOf(year)+"-01-01"; //ub02
                                newEnd   = String.valueOf(year)+"-03-31"; //ub02
                                break;                                    //ub02
                        case 1:                                           //ub02
                                newStart = String.valueOf(year)+"-04-01"; //ub02
                                newEnd   = String.valueOf(year)+"-06-30"; //ub02
                                break;                                    //ub02
                        case 2:                                           //ub02
                                newStart = String.valueOf(year)+"-07-01"; //ub02
                                newEnd   = String.valueOf(year)+"-09-30"; //ub02
                                break;                                    //ub02
                        case 3:                                           //ub02
                                newStart = String.valueOf(year)+"-10-01"; //ub02
                                newEnd   = String.valueOf(year)+"-12-31"; //ub02
                                break;                                    //ub02
                    } // end switch                                       //ub02

                    //newStartTVal = Integer.parseInt(newStart);
                    newStartTVal = Timestamp.valueOf(newStart+" 00:00:00.0");
                    newEndTVal   = Timestamp.valueOf(newEnd+" 23:59:00.0");

                    if (newStartTVal.before(startTVal)) {
                        newStart = startDate;
                        if (dbg) System.out.println("<br>ExtractWAVData: newStart "+newStart);
                    } // if (newStartTVal.before(startTVal))

                    if (newEndTVal.after(endTVal))     {
                        newEnd   = endDate;
                        if (dbg) System.out.println("<br>ExtractWAVData: newEnd "+newEnd);
                    } // if (newEndTVal.after(endTVal))

                    if (newEndTVal.after(startTVal) && newStartTVal.before(endTVal)) {

                        String stationID = station[i].getStationId();

                        String newWhere =
                            WavData.STATION_ID + " = '" + stationID + "' and " +
                            WavData.DATE_TIME  + " between " +
                            Tables.getDateFormat(newStart+" 00:00:00","") + " and " +
                            Tables.getDateFormat(newEnd+" 23:59:59","");
                        if (dbg) System.out.println("ExtractWAVData: ** newWhere " + newWhere);

                        // print the data list
                        data = wData.get(newWhere, WavData.DATE_TIME);
                        for (int j = 0; j < data.length; j++) {
                            printDataLine(data[j]);
                        } // for (int j = 0; j < data.length; j++)
                        noRecords += data.length;

                        // get the real start and end dates
                        if (data.length > 0) {
                            if (data[0].getDateTime().before(realStartDate[i])) {
                                realStartDate[i] = data[0].getDateTime();
                            } // if (data[0].getDateTime().after(realStartDate))
                            if (data[data.length-1].getDateTime().after(realEndDate[i])) {
                                realEndDate[i] = data[data.length-1].getDateTime();
                            } // if (data[0].getDateTime().after(realStartDate))
                        } // if (data.lenth > 0)

                    } // if (newEnd.after(startDate) && newStart.before(endDate))
                } // for (int subYear = 0; subYear = 2; subYear++)
            } // for (int year = startYear; year <= endYear; year++)
        } // for (int i = 0; i < station.length; i++)

        // Place the END OF DATA marker at the end of the file.
        ec.writeFileLine(file,"END OF DATA");

        // print the legend at the bottom
        printLegend();

        // update the start and end dates and times
        updateDateTimes();

    } // getWaveData


    /**
     * Create and print the headers
     * @param  station  list of station records
     */
    private void createAndPrintHeaders(WavStation[] station) {

        datePosition = new long[station.length];

        ec.writeFileLine(file, "Details of data extraction request:");
        ec.writeFileLine(file, "Date range     : " + startDate + " to " + endDate);
        if ("".equals(surveyId)) {
            ec.writeFileLine(file, "Latitude Range : " +
                latitudeNorth + " to " + latitudeSouth);
            ec.writeFileLine(file, "Longitude Range: " +
                longitudeWest + " to " + longitudeEast);
        } else {
            ec.writeFileLine(file, "Survey ID      : " + surveyId);
        } // if ("".equals(surveyId))


        for (int i = 0; i < station.length; i++) {

            WavSponsor sponsor[] = new WavSponsor(station[i].getStationId()).get();

            ec.writeFileLine(file, "");
            ec.writeFileLine(file, "Details of extracted station " + ec.frm(i+1,2) + ":");
            String stationHeader       = station[i].getStationId() + " - " +
                station[i].getName() + " (" + station[i].getSurveyId() + ")";;
            latitudeHeader      = station[i].getLatitude(" ");
            longitudeHeader     = station[i].getLongitude(" ");
            //clientHeader        = client [0].getName();
            //stationCode         = station[i].getStationId();
            String waterDepthHeader    = station[i].getWaterDepth("");
            ec.writeFileLine(file, "Station code   : " + stationHeader);

            for (int j = 0; j < sponsor.length; j++) {
                EdmClient2 client[] = new EdmClient2(sponsor[j].getClientCode()).get();
                ec.writeFileLine(file, "Sponsor " + ec.frm(j+1,2) + "     : from " +
                    sponsor[j].getDateTime("").substring(0, 10) + " " + client[0].getName());
            } // for (int j = 0; j < sponsor.length; j++)

            ec.writeFileLine(file, "Latitude       : " + latitudeHeader);
            ec.writeFileLine(file, "Longitude      : " + longitudeHeader);
            ec.writeFileLine(file, "Water Depth    : " + waterDepthHeader);
            datePosition[i] = getFilePointer(file);
            ec.writeFileLine(file, "Date range     : " + startDate + " to " + endDate);
            ec.writeFileLine(file, "HMO range      : No range selected");
            ec.writeFileLine(file, "Instrument Type: All Instruments");

        } // for (int i = 0; i < station.length; i++)

        ec.writeFileLine(file, "");
        ec.writeFileLine(file, "Notes          : Date/times are UTC");
        ec.writeFileLine(file, "               : Directions are TN");
        ec.writeFileLine(file, "               : Complete legend at bottom of file");
        ec.writeFileLine(file, "");

        String head1 = "StID  Date         Time ";
        String line1 = "----- ------------ ---- ";
        String head2 = "    Hmo      H1      Tp      Tz      Tc      Tb ";
        String line2 = "------- ------- ------- ------- ------- ------- ";
        String head3 = "Directn SpreadF      Hs    Hmax Instrument";
        String line3 = "------- ------- ------- ------- ----------";
        ec.writeFileLine(file, head1 + head2 + head3);
        ec.writeFileLine(file, line1 + line2 + line3);
        ec.writeFileLine(file, "DATA");

    } // createAndPrintHeaders


    /**
     * Print the data record
     * @param   data    the WavData record
     */
    void printDataLine (WavData data) {

        float hmo = data.getHmo();
        //System.out.println("hmo = " + hmo);
        if (hmo == Tables.FLOATNULL) {
            hmo = data.getHs();
        } // if (hmo == Tables.FLOATNULL)

        float h1 = data.getH1();
        //System.out.println("h1 = " + h1);
        if (h1 == Tables.FLOATNULL) {
            h1 = data.getHmax();
        } // if (h1 == Tables.FLOATNULL)

        float aveDirection = data.getAveDirection();
        if (aveDirection == Tables.FLOATNULL) {
            aveDirection = data.getMeanDirection();
        } // if (aveDirection == Tables.FLOATNULL)

        float aveSpreading = data.getAveSpreading();
        if (aveSpreading == Tables.FLOATNULL) {
            aveSpreading = data.getMeanSpreading();
        } // if (aveSpreading == Tables.FLOATNULL)

        EdmInstrument2 instr[] = new EdmInstrument2(data.getInstrumentCode()).get();

        ec.writeFileLine(file,
            data.getStationId()   + "  " +
            data.getDateTime("").substring( 0, 4) + "  " +
            data.getDateTime("").substring( 5, 7) + "  " +
            data.getDateTime("").substring( 8,10) + " " +
            data.getDateTime("").substring(11,13) +
            data.getDateTime("").substring(14,16) +
            ec.frm(ec.nullToNines(hmo          ),8,2)      +
            ec.frm(ec.nullToNines(h1           ),8,2)      +
            ec.frm(ec.nullToNines(data.getTp ()),8,2)      +
            ec.frm(ec.nullToNines(data.getTz ()),8,2)      +
            ec.frm(ec.nullToNines(data.getTc ()),8,2)      +
            ec.frm(ec.nullToNines(data.getTb ()),8,2)      +
            ec.frm(ec.nullToNines(aveDirection),8,2) +
            ec.frm(ec.nullToNines(aveSpreading),8,2) +
            ec.frm(ec.nullToNines(data.getHs()),8,2) +
            ec.frm(ec.nullToNines(data.getHmax()),8,2) + " " +
            instr[0].getName()
            );

    } // printDataLine


    /**
     * Print the legend
     */
    void printLegend() {
        ec.writeFileLine(file,"\nLEGEND:");
        ec.writeFileLine(file,"    Latitude: Latitude (south positive) (decimal degrees)");
        ec.writeFileLine(file,"    Longitude: Longitude (east positive) (decimal degrees)");
        ec.writeFileLine(file,"    StID : Unique 4-character station id");
        ec.writeFileLine(file,"    Date/Time: Date and time (UTC)");
        ec.writeFileLine(file,"    Hmo: Significant wave height (hm0) (meters)");
        ec.writeFileLine(file,"    H1: Extreme wave height (h1) sum of highest peak and deepest trough (meters)");
        ec.writeFileLine(file,"    Tp: Spectral peak wave period (seconds)");
        ec.writeFileLine(file,"    Tz: Average zero down-crossing wave period (seconds)");
        ec.writeFileLine(file,"    Tc: Spectral crest period (tm24) (seconds)");
        ec.writeFileLine(file,"    Tb: Average spectral period (tm01) (seconds)");
        ec.writeFileLine(file,"    Directn: Peak direction (degree TN)");
        ec.writeFileLine(file,"    SpreadF: Peak spreading factor (degrees)");
        ec.writeFileLine(file,"    Hs: Average height of highest 1/3 of all waves (meters)");
        ec.writeFileLine(file,"    Hmax: Maximun wave height (meters)");
        ec.writeFileLine(file,"    Instrument: Wave measuring instrument");
    } // printLegend


    void updateDateTimes() {
        for (int i = 0; i < station.length; i++) {
            seekFilePointer(datePosition[i], "Date range     : " +
                formatter.format(realStartDate[i]) + " to " + formatter.format(realEndDate[i]));
        } // for (int i = 0; i < station.length; i++)
    } // updateDateTimes


    /**
     * get the file position
     * @param   file    file handler
     * @returns the current position in the file
     */
    long getFilePointer(RandomAccessFile file) {
        long position = 0;
        try {
            position = file.getFilePointer();
        } catch (Exception e) {
            System.out.println("getFilePointer Error: " + e.getMessage());
            e.printStackTrace();
        } // try - catch
        return position;
    } // getFilePointer


    /**
     * get position in the file and print the text
     * @param   position    the file position
     * @param   text        the text to print
     */
    void seekFilePointer(long position, String text) {

        try {
            file.seek(position);
            file.writeBytes(text);
        } catch (Exception e) {
            System.out.println("seekFilePointer Error: " + e.getMessage());
            e.printStackTrace();
        } //try-catch

    } // seekFilePointer


    /**                                                                 //ub04
     * Create the LOG_USERS table entry for this extraction.            //ub04
     */                                                                 //ub04
    LogUsers createLogUsers(String userid, Timestamp start, String timeDiff) {//ub04
        LogUsers logs = new LogUsers();                                 //ub04
        logs.setUserid(userid);                                         //ub04
        logs.setDateTime(start);                                        //ub04
        logs.setDatabase("WAVES");                                      //ub04
        logs.setDiscipline("waves");                                    //ub04
        logs.setProduct("Extraction");                                  //ub04
        logs.setFileName(fileName);                                     //ub04
        logs.setQueueDuration(timeDiff);                                //ub04
        logs.setSurveyId(surveyId);                                     //ub04
        boolean success = logs.put();                                   //ub04
                                                                        //ub04
        // commit this log - in case the job bombs out                  //ub04
        common2.DbAccessC.commit();                                     //ub04
                                                                        //ub04
        return logs;                                                    //ub04
                                                                        //ub04
    } // createLogUsers                                                 //ub04


    /**
     * Update the LOG_USERS table for this extraction.
     */
/*
    void updateLogUsers(String userid, String timeDiff) {
        LogUsers logs = new LogUsers();
        logs.setUserid(userid);
        logs.setDateTime(new Timestamp(new java.util.Date().getTime()));
        logs.setDatabase("WAVES");
        logs.setDiscipline("waves");
        logs.setProduct("Extraction");
        logs.setFileName(fileName);
        logs.setLatitudeNorth(latitudeHeader);
        logs.setLatitudeSouth(latitudeHeader);
        logs.setLongitudeWest(longitudeHeader);
        logs.setLongitudeEast(longitudeHeader);
        logs.setDateStart(startDate);
        logs.setDateEnd(endDate);
        logs.setSurveyId(surveyId);
        logs.setNumRecords(noRecords);
        logs.setJobDuration(timeDiff);
        boolean success = logs.put();
    } // updateLogUsers
*/


    /**                                                                 //ub04
     * Update the LOG_USERS table for this extraction.                  //ub04
     */                                                                 //ub04
    void updateLogUsers(LogUsers logs, String timeDiff) {               //ub04
        // define the value that should be updated                      //ub04
        LogUsers updateLogs = new LogUsers();                           //ub04
        updateLogs.setLatitudeNorth(latitudeHeader);                    //ub04
        updateLogs.setLatitudeSouth(latitudeHeader);                    //ub04
        updateLogs.setLongitudeWest(longitudeHeader);                   //ub04
        updateLogs.setLongitudeEast(longitudeHeader);                   //ub04
        updateLogs.setDateStart(startDate);                             //ub04
        updateLogs.setDateEnd(endDate);                                 //ub04
        updateLogs.setNumRecords(noRecords);                            //ub04
        updateLogs.setJobDuration(timeDiff);                            //ub04
                                                                        //ub04
        // define the 'where' clause                                    //ub04
        LogUsers whereLogs = new LogUsers();                            //ub04
        whereLogs.setDateTime(logs.getUserid());                        //ub04
        whereLogs.setDateTime(logs.getDateTime());                      //ub04
                                                                        //ub04
        // do the update                                                //ub04
        whereLogs.upd(updateLogs);                                      //ub04
                                                                        //ub04
    } // updateLogUsers                                                 //ub04


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            ExtractWAVData local =
                new ExtractWAVData(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractWAVData", "Constructor", "");
        } // try-catch
        // make sure the job is taken out of the queue, especially after crash
        //queue.deQueue();                                                //**//
        // dequeue extraction                                           //ub02
        // close the connection to the database
        common2.DbAccessC.close();

    } // main

} // class ExtractWAVData
