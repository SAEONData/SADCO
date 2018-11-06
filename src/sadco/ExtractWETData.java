package sadco;

import common2.Queue;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class extracts the SADCO weather data from the database
 *
 * SadcoWETApps
 * screen.equals("extract")
 * version.equals("doextract")
 *
 * @author 991103 - SIT Group
 * @version
 * 991103 - Ursula von St Ange - create class                               <br>
 * 021003 - Mario August       - extractions for more than one year         <br>
 * 091214 - Ursula von St Ange - change for inventory user (ub01)           <br>
 * 100105 - Ursula von St Ange - change for queuing of job (ub02)           <br>
 * 100615 - Ursula von St Ange - non-inventory users don't queue (ub03)     <br>
 * 100624 - Ursula von St Ange - change logging of job to beginning with
 *                               update at end (ub04)                       <br>
 * 140127 - Ursula von St Ange - add QC stuff (ub05)                        <br>
 */

public class ExtractWETData {

    static common.edmCommonPC ec = new common.edmCommonPC();

    boolean dbg = false;
    //boolean dbg = true;
    boolean dbgQ = false;
    //boolean dbgQ = true;

    String thisClass = this.getClass().getName();

    String fileNameIn = "";                                             //ub02
    String fileName = "";
    String rootPath = "";
    String txtLink;
    String wetDataWhere;
    String wetStationWhere;
    String periodCode = "";
    String stationId;
    String startStationId;
    String endStationId;
    String latitudeNorth;
    String latitudeSouth;
    String longitudeWest;
    String longitudeEast;
    String startDate;
    String endDate;
    String parameter;
    String startParameter;
    String endParameter;
    String startHmo;
    String endHmo;
    String instrumentCode;
    int    format = 2;
    String sFormat;
    String initials;
    String twocharstationid  = "";
    String surveyId          = "";
    String sessionCode       = "";
    String reqno             = "";
    String userType          = "";                                      //ub01

    String stationName       = "No station selected ";
    String stationCode       = "";
    String heightMsl         = " ";
    String heightSurface     = " ";
    String stationHeader     = "No station code selected";
    String surveyHeader      = "No station survey selected";
    String latitudeHeader    = "No range selected";
    String longitudeHeader   = "No range selected";
    String clientHeader      = " ";
    String dateHeader        = "No range selected";
    String notesHeader       = " ";
    int noRecords            = 0;

    public ExtractWETData(String args[]) {

        if (dbg) System.out.println("<br>before getargs : ");
        getArgParms(args);

        sadco.SadConstants sc = new sadco.SadConstants();

        // extract the user record from db
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        String userid = sessions[0].getUserid();

        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                rootPath = sc.HOSTDIR + "inv_user/";                    //ub01
            } else {                                                    //ub01
                rootPath = sc.HOSTDIR + userid + "/";                   //ub01
            } // if ("0".equals(userType))                              //ub01
        } else {
            rootPath = sc.LOCALDIR;
        } // if host
        if (dbg) System.out.println("<br>:rootpath : "+rootPath);


        // new filename, but keep the original file name                //ub02
        fileNameIn = fileName;                                          //ub02
        if (!"".equals(reqno)) {
            //fileName = initials+"_"+fileName;
            fileName = reqno+"_"+fileName;
            if (dbg) System.out.println("<br> fileName "+fileName);
        } //if (!"".equals(initials)) {

        //add filename extension.
        fileName = fileName + ".wet";
        if (dbg) System.out.println("<br> fileName "+fileName);

        // delete an old dummy file for Progress purposes ......
        String dummyFile = rootPath + fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        // create .queued file for Progress purposes ......             //ub02
        String dummyFile2 = rootPath + fileName + ".queued";            //ub02
        Queue queue = null;

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

        if (dbg) System.out.println("<br> fileName "+fileName);
        ec.deleteOldFile(fileName);
        RandomAccessFile file = ec.openFile(rootPath + fileName , "rw");
        if (ec.getHost().startsWith(sc.HOST)) {
            //System.out.println("chmod 644 " + fileName);
            ec.submitJob("chmod 644 " + rootPath + fileName);
        } //if (ec.getHost().startsWith(sc.HOST)) {


        wetStationWhere = "";
        wetDataWhere    = "";
        WetStation station[] = null;

        //first get station id
        if (dbg) System.out.println("<br>surveyId : " + surveyId);

        if (surveyId.length() > 0)  {

            wetStationWhere = WetStation.SURVEY_ID +" = '"+surveyId+"'";

            station = new WetStation().get(wetStationWhere);
            if (dbg) System.out.println("<br>stationId : " + stationId);

            if (station.length > 0) {
                stationId = station[0].getStationId();

                // A single station Id is required
                wetDataWhere = "STATION_ID = '" + stationId + "'";
                if (dbg) System.out.println("<br>wetDataWhere 2 : " +
                    wetDataWhere);

                //Get the Period class
                String periodWhere =
                    WetPeriod.STATION_ID + "='"  + stationId + "'";
                WetPeriod wetPeriod[] = new WetPeriod().get(periodWhere);
                if (dbg) System.out.println("<br>wetPeriod[0] : " +
                    wetPeriod[0]);

                if (dbg) System.out.println(" wetPeriod.length = "+wetPeriod.length);
                createHeaders(wetPeriod, station);
            } //if (station.length

        } //  if (surveyId.length() > 0)  {

        if (dbg) System.out.println("<br>stationHeader : " + stationHeader);
        if (dbg) System.out.println("<br>startDate     : " + startDate);
        if (dbg) System.out.println("<br>endDate       : " + endDate);

        // If there are area co-ordinates create the header labels
        // and station table where clause
        if (latitudeNorth.length() > 0)  {
            wetStationWhere = "LATITUDE BETWEEN "+latitudeNorth+" and "+
                latitudeSouth + " and " +"LONGITUDE BETWEEN " +longitudeWest
                +" and "+longitudeEast;

            if (dbg) System.out.println(" wetStationWhere "+wetStationWhere);

            latitudeHeader      = latitudeNorth + " to " + latitudeSouth;
            longitudeHeader     = longitudeWest + " to " + longitudeEast;

            //Get the WetStation class
            //station = new WetStation(stationId).get();
            station = new WetStation().get(wetStationWhere);
            if (dbg) System.out.println("<br> range test station.length = "+station.length);

        }   //  if (latitudeNorth.length() > 0 )

        ec.setFrmFiller(" ");
        // Print the header labels at the top of the extraction file
        ec.writeFileLine(file, "STATION CODE: " + stationCode);
        ec.writeFileLine(file, "STATION NAME: " + stationName);
        ec.writeFileLine(file, "SURVEY ID   : " + surveyId);
        ec.writeFileLine(file, "HEIGHT MSL  : " + heightMsl);
        ec.writeFileLine(file, "HEIGHT SURF : " + heightSurface);
        ec.writeFileLine(file, "LATITUDE    : " + latitudeHeader);
        ec.writeFileLine(file, "LONGITUDE   : " + longitudeHeader);
        ec.writeFileLine(file, "START DATE  : " + startDate);
        ec.writeFileLine(file, "END DATE    : " + endDate);
        ec.writeFileLine(file, "CLIENT      : " + clientHeader);
        ec.writeFileLine(file, "NOTES: ");
        ec.writeFileLine(file, "-- Date/Times were corrected to UTC (-2 hours).");
        ec.writeFileLine(file, "-- " + notesHeader);


        // get the list of parameterList for Quality control
        WetDataQcParmcodes parmCodes[] = new WetDataQcParmcodes().get();    //ub05

        int startYear = Integer.parseInt(startDate.substring(0,4));         //ub05
        int endYear   = Integer.parseInt(  endDate.substring(0,4));         //ub05

        for (int year = startYear; year <= endYear; year++) {               //ub05

            // get cur_depth_qc record
            String field = "*";                                             //ub05
            String where = WetDataQc.STATION_ID + " = '" + stationCode +    //ub05
                "' and " + WetDataQc.YEAR + " = " + year;                   //ub05
            String order = WetDataQc.PARAMETER_CODE;                        //ub05
            WetDataQc dataQc[] = new WetDataQc().get(field,where,order);    //ub05
            if (dataQc.length > 0) {                                        //ub05
                                                                            //ub05
                // get the parameter codes                                  //ub05
                ec.writeFileLine(file, "QUALITY CONTROL CHECKS: " + year);  //ub05
                ec.writeFileLine(file, "  " + ec.frm("Parameter",12) +      //ub05
                    "Broad_Range  Spikes  Sensor_Drift  Data_Gaps  " +      //ub05
                    "Person              Date");                            //ub05
                for (int i = 0; i < dataQc.length; i++) {                   //ub05
                    int code = dataQc[i].getParameterCode();                //ub05
                    String name = parmCodes[code].getName();                //ub05
                    ec.writeFileLine(file, "  " + ec.frm(name,15) +         //ub05
                        ec.frm(dataQc[i].getBroadrange(),13) +              //ub05
                        ec.frm(dataQc[i].getSpikes(),8) +                   //ub05
                        ec.frm(dataQc[i].getSensordrift(),14) +             //ub05
                        ec.frm(dataQc[i].getGaps(),8) +                    //ub05
                        //ec.frm(" ",11) +                                  //ub05
                        ec.frm(dataQc[i].getPersonChecked(),20) +           //ub05
                        dataQc[i].getDateChecked("").substring(0,10));      //ub05
                } // for (int i = 0; i < dataQc.length; i++)                //ub05
                ec.writeFileLine(file, "  (1 = failed quality control check)"); //ub05
            } // if (dataQc.length > 0)                                     //ub05

        } // for (int year = startYear; year <= endYear; year++)            //ub05


        if (dbg) System.out.println("<br>format : " + format);
        // Print the column headings
        /*
        switch (format) {
        case 1: // extraction format 1
            printHeader12(file);
            break;

        case 2: // extraction format 2
            printHeader12(file);
            break;
        }   //  switch (format) {
        */
        printHeader12(file);


        // Extract and add the data to the extraction file
        //WetExtractData WetExtractData = new WetExtractData(
        //    format,
        //    file,
        //    stationId,
        //    wetStationWhere,
        //    wetDataWhere
        //    );

        if (!periodCode.equals("")) {
            //WetExtractData WetExtractData =
            //    new WetExtractData(format,file,stationId,"",wetDataWhere);
            ExtractWETData2 extractWETData2 =
                new ExtractWETData2(format,file,stationId,"",wetDataWhere);
            noRecords = extractWETData2.countRecords();

        } else {

            //int startYear = Integer.parseInt(startDate.substring(0,4));
            //int endYear   = Integer.parseInt(  endDate.substring(0,4));

            Timestamp startTVal = Timestamp.valueOf(startDate+" 00:00:00.0");
            Timestamp endTVal   = Timestamp.valueOf(endDate+" 23:59:00.0");

            Timestamp newStartDateVal;
            Timestamp newEndDateVal;

            String newStart  = "";
            String newEnd    = "";

            if (dbg) System.out.println("<br> before station loop station.length "+station.length);

            // for calculating last day of month
            GregorianCalendar calDate = new GregorianCalendar();
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");

            // station loop
            for (int i=0; i < station.length;i++) {
                if (dbg) System.out.println(" i vanlue in station loop "+i);

                for (int year=startYear; year <= endYear; year++) {
                if (dbg) System.out.println("<br>year value in year loop "+year);

                    for (int month = 1; month <= 12; month++) {

                        String monthStr = String.valueOf(month);
                        if (monthStr.length() == 1) {
                            monthStr = "0" + monthStr;
                        } // if (monthStr.length() == 1)
                        if (dbg) System.out.println(
                            "<br>month value in month loop "+monthStr);

                        // set new start and end dates
                        newStart = String.valueOf(year)+"-"+monthStr+"-01";
                        calDate.setTime(Timestamp.valueOf(newStart+" 00:00:00.0"));
                        int lastDay = calDate.getActualMaximum(Calendar.DATE);
                        calDate.set(Calendar.DATE, lastDay);
                        newEnd = formatter.format(calDate.getTime());

                        if (dbg) System.out.println("<br>newStart = " + newStart);
                        if (dbg) System.out.println("<br>newEnd = " + newEnd);

                        //newStartDateVal = Integer.parseInt(newStart);
                        newStartDateVal = Timestamp.valueOf(newStart+" 00:00:00.0");
                        newEndDateVal   = Timestamp.valueOf(newEnd+" 23:59:59.9");
                        if (dbg) System.out.println("<br>newStartDateVal = " + newStartDateVal);
                        if (dbg) System.out.println("<br>newEndDateVal = " + newEndDateVal);

                        // is the month before the beginning of the requested startdate
                        // or after the end of the requested end date
                        if ((newEndDateVal.equals(startTVal) || newEndDateVal.after(startTVal)) &&
                            (newStartDateVal.equals(endTVal) || newStartDateVal.before(endTVal))) {

                            // correct the start and end dates
                            if (newStartDateVal.before(startTVal)) {
                                newStart = startDate;
                                if (dbg) System.out.println("<br> newStart "+newStart);
                            } // if (newStartDateVal.before(startTVal))

                            if (newEndDateVal.after(endTVal))     {
                                newEnd   = endDate;
                                if (dbg) System.out.println("<br> newEnd "+newEnd);
                            } // if (newEndDateVal.after(endTVal))

                            //String newWhere = wetDataWhere + " and DATE_TIME between "+
                            //    Tables.getDateFormat(startDate + " 00:00:00","")+" and "+
                            //    Tables.getDateFormat(endDate + " 23:59:59","");

                            String stationID = station[i].getStationId();
                            String newWhere =
                                WetData.STATION_ID +" = '"+stationID+"' and "+
                                WetData.DATE_TIME  +" between "+
                                Tables.getDateFormat(newStart+" 00:00:00","")+" and "+
                                Tables.getDateFormat(newEnd+" 23:59:59","");

                            if (dbg) System.out.println("<br> newWhere "+newWhere);
                            ExtractWETData2 extractWETData2 =
                                new ExtractWETData2(format, file,stationID,"",newWhere);
                            noRecords += extractWETData2.countRecords();

                        } // if (newStartDateVal.after(endTVal))
                    } // for (int month = 1; month <= 12; month++)
                } // for (int year
            } //for (int i
        } // perioCode end-if

        if (dbg) System.out.println("<br>END OF DATA ");
        // Place the END OF DATA marker at the end of the file.
        ec.writeFileLine(file,"END OF DATA");

        // close file
        ec.closeFile(file);

        Timestamp endJDate = new Timestamp(new Date().getTime());       //ub04
        updateLogUsers(log, ec.timeDiff(endJDate, endQDate));           //ub04

        // delete the .processing file
        ec.deleteOldFile(dummyFile2);
        // create the dummy file for Progress purposes ......
        ec.createNewFile(dummyFile);

        // dequeue extraction                                           //ub02
        if ("0".equals(userType)) {  // from inventory                  //ub03
            if (!dbgQ) {
                queue.deQueue();                                        //ub02
            } // if (!dbgQ)
        } // if ("0".equals(userType))                                  //ub03

    } // constructor


    /*
     * Print header for format 1 and 2
     * @param  file    output file name
     */
    void printHeader12 (RandomAccessFile file) {
        ec.writeFileLine(file, "FORMAT:"                                                                    );
        ec.writeFileLine(file, ":   6: 4:YEAR   :                                            >wdtyy  <"     );
        ec.writeFileLine(file, ":  10: 3:MONTH  :                                            >wdtmm  <"     );
        ec.writeFileLine(file, ":  13: 3:DAY    :                                            >wdtdd  <"     );
        ec.writeFileLine(file, ":  16: 3:HOUR   :                                            >wdmhh  <"     );
        ec.writeFileLine(file, ":  19: 3:MINUTE :                                            >wdmmm  <"     );
        ec.writeFileLine(file, ":  22: 6:Average Temperature (C)       :     99.99:          >ata    <"     );
        ec.writeFileLine(file, ":  28: 7:Minimum Temperature (C)       :     99.99:          >atmn   <"     );
        ec.writeFileLine(file, ":  35: 6:Time of Min. Temerature       :     99.99:          >atmnt  <"     );
        ec.writeFileLine(file, ":  41: 7:Maximum Temperature (C)       :     99.99:          >atmx   <"     );
        ec.writeFileLine(file, ":  48: 6:Time of Max. Temperature      :     99.99:          >atmxt  <"     );
        ec.writeFileLine(file, ":  54: 7:Barometric Pressure           :    9999.9:          >bp     <"     );
        ec.writeFileLine(file, ":  61: 7:Fog                           :     999.9:          >fg     <"     );
        ec.writeFileLine(file, ":  68: 7:Rainfall                      :     999.9:          >rf     <"     );
        ec.writeFileLine(file, ":  75: 7:Relative Humidty              :     999.9:          >rh     <"     );
        ec.writeFileLine(file, ":  82: 8:Solar Radiation               :    9999.9:          >sr     <"     );
        ec.writeFileLine(file, ":  90: 8:Solar Radiation Maximum       :    9999.9:          >srmx   <"     );
        ec.writeFileLine(file, ":  98: 7:Wind Direction                :     999.9:          >wd     <"     );
        ec.writeFileLine(file, ": 105: 7:Wind Speed Average            :     99.99:          >wsa    <"     );
        ec.writeFileLine(file, ": 112: 7:Wind Speed Minimum            :     99.99:          >wsmn   <"     );
        ec.writeFileLine(file, ": 119: 7:Wind Speed Maximum            :     99.99:          >wsmx   <"     );
        ec.writeFileLine(file, ": 126: 6:Time of Wind Speed Maximum    :     99.99:          >wsmxt  <"     );
        ec.writeFileLine(file, ": 132: 3:Duraton of Wind Speed Maximum :        99:          >wsmxd  <"     );
        ec.writeFileLine(file, ": 135: 7:Wind Speed Standard Deviation :     99.99:          >wsssd  <"     );
        ec.writeFileLine(file, ": 142: 7:Wind Speed Maximum Direction  :     999.9:          >wsmxwd <"     );
        //ec.writeFileLine(file, ": 149: 7:Tide Level                    :     999.9:          >tlev   <"     );
        //ec.writeFileLine(file, ": 156: 7:Tide Minimum                  :     999.9:          >tmin   <"     );
        //ec.writeFileLine(file, ": 173: 7:Tide Maximum                  :     999.9:          >tmax   <"     );
        //ec.writeFileLine(file, ": 180: 1:Tide Status (r=rising f=fall) :          :          >tsta   <"     );
        // Place the START OF DATA marker at the end of the file.
        ec.writeFileLine(file,"DATA:");

    } // printHeader12


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    private void getArgParms(String args[])   {

        sessionCode   = ec.getArgument(args,SadConstants.SESSIONCODE);
        reqno         = ec.getArgument(args,SadConstants.REQNUMBER);
        fileName      = ec.getArgument(args,SadConstants.FILENAME);
        surveyId      = ec.getArgument(args,SadConstants.SURVEYID);
        //stationId     = ec.getArgument(args,"pstationid");
        latitudeNorth = ec.getArgument(args,SadConstants.LATITUDENORTH);
        latitudeSouth = ec.getArgument(args,SadConstants.LATITUDESOUTH);
        longitudeWest = ec.getArgument(args,SadConstants.LONGITUDEWEST);
        longitudeEast = ec.getArgument(args,SadConstants.LONGITUDEEAST);
        startDate     = ec.getArgument(args,SadConstants.STARTDATE);
        endDate       = ec.getArgument(args,SadConstants.ENDDATE);
        userType      = ec.getArgument(args,SadConstants.USERTYPE);     //ub01
        //instrumentCode   = ec.getArgument(args,"pinstrumentcode");
        //parameter        = ec.getArgument(args,"pparameter");
        //startParameter   = ec.getArgument(args,"pstartparameter");
        //endParameter     = ec.getArgument(args,"pendparameter");
        //sFormat          = ec.getArgument(args,"pformat");
        //periodCode       = ec.getArgument(args,"pperiodcode");
        //twocharstationid = ec.getArgument(args,"ptwocharstationid");
    } // getArgParms()

    /**
     * create the headers to be printed
     * @param  wetPeriod
     * @param  station
     */
    private void createHeaders(WetPeriod[] wetPeriod, WetStation[] station) {
        //Get the EdmClient2 class
        EdmClient2 client[] =
            new EdmClient2(station[0].getClientCode()).get();
        if (dbg) System.out.println("<br>client[0] : " + client[0]);

        //Get the Period Notes class
        WetPeriodNotes wetPeriodNotes[] = new WetPeriodNotes().get(
            WetPeriodNotes.PERIOD_CODE + "="  + wetPeriod[0].getCode());

        if (dbg) System.out.println(" wetPeriodNotes.length = "+
            wetPeriodNotes.length);

        if (dbg) System.out.println("<br>station[0].getName() : " + station[0].getName());
        if (dbg) System.out.println(
            "<br>wetPeriod[wetPeriod.length].getStartDate(\"\") : " +
            wetPeriod[wetPeriod.length - 1].getStartDate(""));

        // Get the single station details and store in the file headers
        //stationHeader       = station[0].getStationId();

        stationName         = station[0].getName();
        latitudeHeader      = station[0].getLatitude(" ");
        longitudeHeader     = station[0].getLongitude(" ");
        clientHeader        = client [0].getName();
        stationCode         = station[0].getStationId();

        if (wetPeriodNotes.length > 0) {
            //notesHeader==null?" ":wetPeriodNotes[0].getNotes());
            notesHeader=wetPeriodNotes[0].getNotes();
        } else {
            notesHeader=" ";
        } // if (wetPeriodNotes.length > 0) {

        String prevHeightMsl     = wetPeriod[0].getHeightMsl("");
        String prevHeightSurface = wetPeriod[0].getHeightSurface("");
        int mslCnt     = 0;
        int surfaceCnt = 0;

        // check if heights have changed
        for (int i = 0; i < wetPeriod.length; i++) {
            heightMsl     = wetPeriod[i].getHeightMsl("");
            heightSurface = wetPeriod[i].getHeightSurface("");

            if (!prevHeightMsl.equals(heightMsl)) {
                mslCnt++;
            } // if heightMsl

            if (!prevHeightSurface.equals(heightSurface)) {
                surfaceCnt++;
            } // if heightSurface

            prevHeightMsl     = heightMsl;
            prevHeightSurface = heightSurface;
        }//for (int i = 0; i < wetPeriod.length; i++)

        if (mslCnt > 0) {
            heightMsl = heightMsl + " warning : heightMsl has changed " +
            mslCnt + " times";
        } // if mslCnt

        if (surfaceCnt > 0) {
            heightSurface = heightSurface + "warning : heightSurface has changed " +
            surfaceCnt + " times";
        } // if surfaceCnt
    } // createHeaders


    /**                                                                 //ub04
     * Create the LOG_USERS table entry for this extraction.            //ub04
     */                                                                 //ub04
    LogUsers createLogUsers(String userid, Timestamp start, String timeDiff) {//ub04
        LogUsers logs = new LogUsers();                                 //ub04
        logs.setUserid(userid);                                         //ub04
        logs.setDateTime(start);                                        //ub04
        logs.setDatabase("WEATHER");                                    //ub04
        logs.setDiscipline("weather");                                  //ub04
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
        logs.setDatabase("WEATHER");
        logs.setDiscipline("weather");
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
            ExtractWETData local = new ExtractWETData(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, "ExtractWETData ", "Constructor", "");
        } // try-catch
        // close the connection to the database
        common2.DbAccessC.close();

    } //public static void main(String args[]) {

} // class ExtractWETData


