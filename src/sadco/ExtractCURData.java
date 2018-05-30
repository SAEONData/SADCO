//+------------------------------------------------+----------------+--------+
//| CLASS: ... ExtractCURData                      | JAVA PROGRAM   | 010326 |
//+------------------------------------------------+----------------+--------+
//| PROGRAMMER: .. SIT Group                                                 |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//|                                                                          |
//|                                                                          |
//|                                                                          |
//|                                                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 991103  SIT Group            create class                                |
//+--------------------------------------------------------------------------+

package sadco;

import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Does the actual extraction of the requested data.
 *
 * SadcoCURApps
 * screen.equals("extract")
 * version.equals("extract")
 *
 * @author 010326 - SIT Group
 * @version
 * 040811 - Ursula von St Ange - add flagType stuff                         <br>
 */
public class ExtractCURData {

    boolean dbg = false;
    //boolean dbg = true;

    /** some common functions */
    static common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    SadConstants sc = new SadConstants();

    // parameters from html page
    String sessionCode    = "";
    String reqno          = "";
    String fileName       = "";
    String startDate      = "";
    String endDate        = "";
    float  latitudeNorth  = 0;
    float  latitudeSouth  = 0;
    float  longitudeWest  = 0;
    float  longitudeEast  = 0;
    String flagType       = "";
    String dataSet        = "";
    String password       = "";
    int    startdepth     = 0;
    int    enddepth       = 0;

    // data fields.
    int    endDepthInitial= 9999;
    String passKey        = "";
    int    noRecords      = 0;
    long   headerPos      = 0;

    // for the sql
    String field          = "";
    String where          = "";
    String order          = "";

    // minimum and maximum's.
    float maxlatitude  = -20.0f;
    float minlatitude  =  99.0f;
    float maxlongitude = -40.0f;
    float minlongitude =  99.0f;
    Timestamp minDate  = Timestamp.valueOf("2100-01-01 00:00:00.0");
    Timestamp maxDate  = Timestamp.valueOf("1900-01-01 00:00:00.0");

    // the files
    RandomAccessFile file;
    RandomAccessFile debugFile;


    /**
     * Main constructor ExtractCURData
     * @param   args   String array of arguments as from form or URL
     */
    public ExtractCURData(String args[]) {

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
            rootPath = sc.HOSTDIR + userid + "/";
        } else {
            rootPath = sc.LOCALDIR + "currents/";
        } // if host
        if (dbg) System.out.println("rootPath "+rootPath);

        fileName = rootPath + reqno + "_" + fileName + ".dbc";
        if (dbg) System.out.println("fileName "+fileName);

        // delete an old dummy file for Progress purposes ......
        String dummyFile = fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        // delete old debug file
        String dbgFile   = fileName + ".debug";
        ec.deleteOldFile(dbgFile);

        // create the 'processing' file
        String dummyFile2 = fileName + ".processing";
        ec.createNewFile(dummyFile2);

        // delete any old data file.
        ec.deleteOldFile(fileName);

        // open output file & debug file
        try {
            debugFile = new RandomAccessFile(fileName+".debug", "rw");
            file = new RandomAccessFile(fileName , "rw");
            if (ec.getHost().startsWith(sc.HOST)) {
                //System.out.println("chmod 644 " + fileName);
                ec.submitJob("chmod 644 " + fileName);
            } //if (ec.getHost().startsWith(sc.HOST)) {

            // get the header position for later
            file.writeBytes("");
            headerPos = file.getFilePointer();
         } catch (Exception e) {
            System.out.println("<br>ExtractCURData Error: " + e.toString());
            e.printStackTrace();
         }  //  catch (Exception e)

        ec.writeFileLine(debugFile,"Debugfile class ExtractCURData ");
        ec.writeFileLine(debugFile,"");
        ec.writeFileLine(debugFile,"fileName  : "+fileName);
        ec.writeFileLine(debugFile,"startDate : "+startDate);
        ec.writeFileLine(debugFile,"endDate   : "+endDate);
        ec.writeFileLine(debugFile,"lat       : "+latitudeNorth);
        ec.writeFileLine(debugFile,"lat       : "+latitudeSouth);
        ec.writeFileLine(debugFile,"long      : "+longitudeWest);
        ec.writeFileLine(debugFile,"long      : "+longitudeEast);
        ec.writeFileLine(debugFile,"startdepth: "+startdepth);
        ec.writeFileLine(debugFile,"enddepth  : "+enddepth);
        ec.writeFileLine(debugFile,"flagType  : "+flagType);
        ec.writeFileLine(debugFile,"dataSet   : "+dataSet);
        ec.writeFileLine(debugFile,"password  : "+password);

        // write empty line for header
        ec.writeFileLine(file,ec.frm("",80));

        // write column headings
        ec.writeFileLine(file,
            ec.frm("MoorCod",9)+
            ec.frm("DepCod",8)+
            //ec.frm("DatCod",8)+
            ec.frm("Lat",10)+
            ec.frm("Lon",11)+
            ec.frm("Spldep",8)+
            ec.frm("Date",21)+
            ec.frm("Speed",6)+
            ec.frm("Dir",8)+
            ec.frm("Temp",5)+
            ec.frm("PercGood",8));

        // check the flagType combination
        checkFlagType(userid);
        if (dbg) System.out.println("<br>flagType = " + flagType);

        // get the actual current data
        if (dbg) System.out.println(" before getCurrentData function ");
        getCurrentData();

        // call header function.
        if (dbg) System.out.println(" before printHeader function ");
        printHeader();

        // close files.
        try {
            file.close();
            debugFile.close();
         } catch (Exception e) {
            System.out.println("<br>ExtractCURData Error: " + e.toString());
            e.printStackTrace();
         }  //  catch (Exception e)

        // delete the .processing file
        ec.deleteOldFile(dummyFile2);
        ec.createNewFile(dummyFile);

    } // constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    private void getArgParms(String args[])   {
        sessionCode = ec.getArgument(args, SadConstants.SESSIONCODE);
        reqno         = ec.getArgument(args,SadConstants.REQNUMBER);
        fileName      = ec.getArgument(args,SadConstants.FILENAME);
        startDate     = ec.getArgument(args,SadConstants.STARTDATE);
        endDate       = ec.getArgument(args,SadConstants.ENDDATE);
        latitudeNorth = Float.parseFloat(ec.getArgument(args,SadConstants.LATITUDENORTH));
        latitudeSouth = Float.parseFloat(ec.getArgument(args,SadConstants.LATITUDESOUTH));
        longitudeWest = Float.parseFloat(ec.getArgument(args,SadConstants.LONGITUDEWEST));
        longitudeEast = Float.parseFloat(ec.getArgument(args,SadConstants.LONGITUDEEAST));

        flagType = ec.getArgument(args, sc.FLAGTYPE);
        dataSet = ec.getArgument(args, sc.DATASET);

        password = ec.getArgument(args, sc.PASSWORD);

        String test = ec.getArgument(args,SadConstants.STARTDEPTH);
        if (!"".equals(test)) {
            startdepth = Integer.parseInt(test);
        } else {
            startdepth = 0;
        }//if ("".equals(test)){

        test = ec.getArgument(args,SadConstants.ENDDEPTH);
        if (!"".equals(test)) {
            enddepth = Integer.parseInt(test);
        } else {
            enddepth = endDepthInitial;
        }//if ("".equals(enddepth)) {

    } // getArgParms


    /**
     * check if the flagType combination is valid
     */
    void checkFlagType(String userid) {

        // check if flagType combination is valid.  If not, revert back to
        // unflagged data extraction only
        if (dbg) System.out.println("<br>flagType = " + flagType +
            ", dataSet = " + dataSet + ", password = " + password);
        if (!"".equals(flagType)) {
            // was there a dataSet or a Password
            if ("".equals(dataSet) || "".equals(password)) {
                if (dbg) System.out.println("<br>password test 1:true (dataset or password blank) ");
                flagType = "1";
            } else {
                if (dbg) System.out.println("<br>password test 1:false (dataset or password blank)");
                // is this user registered for the dataset
                SadUserDset[] userDset = new SadUserDset(userid, dataSet).get();
                if (dbg) System.out.println("<br>userDset.length = " +
                    userDset.length);
                if (userDset.length == 0) {
                    if (dbg) System.out.println("<br>password test 2:true (userDset.length == 0)");
                    flagType = "1";
                } else {
                    if (dbg) System.out.println("<br>password test 2:false (userDset.length == 0)");
                    // did the user give the right password?
                    SadUsers[] users = new SadUsers().get(
                        SadUsers.FLAG_PASSWORD,
                        SadUsers.USERID + "='" + userid + "'", "");
                    if (dbg) System.out.println("<br>password = " + password +
                        ", users[0].getFlagPassword() = " +
                        users[0].getFlagPassword());
                    if (dbg) System.out.println("<br>users[0] = " + users[0]);
                    if (!users[0].getFlagPassword().equals(password)) {
                        if (dbg) System.out.println("<br>password test 3:true " +
                            users[0].getFlagPassword() + " " + password);
                        flagType = "1";
                    } else {
                        if (dbg) System.out.println("<br>password test 3:false " +
                            users[0].getFlagPassword() + " " + password);
                        // get the dataset password
                        SadUsers[] dataset = new SadUsers().get(
                            SadUsers.PASSWORD,
                            SadUsers.USERID + "='" + dataSet + "'", "");
                        if (dataset.length == 0) {
                            if (dbg) System.out.println("<br>password test 4:true " +
                                " (dataset.length == 0)");
                            flagType = "1";
                        } else {
                            if (dbg) System.out.println("<br>password test 4:false " +
                                " (dataset.length == 0)");
                            passKey = dataset[0].getPassword(); //.toUpperCase();
                        } // if (dataset.length > 0)
                    }// if (!users[0].getPassword.equals(password)
                } // if (userDSet.length == 0)
            } // if ("".equals(dataSet) || "".equals(password))
        } else {
            flagType = "1";
        } // if (!"".equals(flagType))

    } // checkFlagType


    /**
     * get the current data, and write to the output file
     */
    void getCurrentData() {

        if (dbg) System.out.println("in getCurrentData function ");

        // get Cur_Mooring details.
        field  = "*";
        where  = "("+CurMooring.DATE_TIME_START+" BETWEEN "+
             Tables.getDateFormat(startDate+" 00:00:00")+
             " and "+Tables.getDateFormat(endDate+" 23:59:59")+")"+
             " and ("+CurMooring.LATITUDE+" between "+latitudeNorth+
             " and "+latitudeSouth+" and "+CurMooring.LONGITUDE+
             " between "+longitudeWest+" and "+longitudeEast+")";
        order  = CurMooring.DATE_TIME_START;

        CurMooring mooring[] = new CurMooring().get(field,where,order);
        if (dbg) System.out.println("get mooring details " + where);
        if (dbg) System.out.println("mooring.length : " + mooring.length);

        ec.writeFileLine(debugFile,"\nget mooring records where  : " + where);
        ec.writeFileLine(debugFile,"mooring length " + mooring.length);

        if (dbg) System.out.println("mooring record "+maxlatitude+" "+
            minlatitude+" "+maxlongitude+" "+minlongitude);

        // if there was data, process further
        boolean isData = true;
        if (mooring.length == 0) {
            isData = false;
        } // if (mooring.length == 0)

        // process each mooring
        for (int i = 0; i < mooring.length; i++) {

            float lat    = mooring[i].getLatitude();
            float lon    = mooring[i].getLongitude();
            maxlatitude  = ec.max(maxlatitude,lat);
            minlatitude  = ec.min(minlatitude,lat);
            maxlongitude = ec.max(maxlongitude,lon);
            minlongitude = ec.min(minlongitude,lon);

            where = CurDepth.MOORING_CODE + " = " + mooring[i].getCode() + " and " +
                CurDepth.SPLDEP + " between " + startdepth + " and " + enddepth +
                " and " + CurDepth.SURVEY_ID + " is not NULL";
            if ("1".equals(flagType)) {
                where += " and " + CurDepth.PASSKEY + " is NULL";
            } else if ("2".equals(flagType)) {
                where += " and " + CurDepth.PASSKEY + "='" + passKey + "'";
            } else {
                where += " and (" + CurDepth.PASSKEY + " is NULL";
                where += " or " + CurDepth.PASSKEY + "='" + passKey + "')";
            } // if ("1".equals(flagType))

            CurDepth depth[] = new CurDepth().get(where);
            if (dbg) System.out.println("get depth(loop) where " + where);
            if (dbg) System.out.println("\ncurDepth length "+depth.length);
            ec.writeFileLine(debugFile,"\nget depth records where  : " + where);
            ec.writeFileLine(debugFile,"curDepth length " + depth.length);

            // loop through all depths for mooring
            for (int j = 0; j < depth.length; j++) {

                float spldep = depth[j].getSpldep();

                // get start and end dates
                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
                GregorianCalendar calDate = new GregorianCalendar();
                calDate.setTime(depth[i].getDateTimeEnd());
                long dateTimeEnd = calDate.getTimeInMillis();
                calDate.setTime(depth[i].getDateTimeStart());
                long dateTimeStart = calDate.getTimeInMillis();

                if (dbg) System.out.println("dateTimeStart = " + dateTimeStart +
                    " * " + depth[i].getDateTimeStart(""));
                if (dbg) System.out.println("dateTimeEnd = " + dateTimeEnd +
                    " * " + depth[i].getDateTimeEnd(""));

                // do in loop from start till end date
                while (dateTimeStart <= dateTimeEnd) {

                    String datum = formatter.format(calDate.getTime());
                    where = CurData.DATETIME + " between '" + datum +
                        " 00:00:00' and '" + datum + " 23:59:59' and " +
                        CurData.DEPTH_CODE +" = "+depth[i].getCode();

                    if (dbg) System.out.println("where = " + where);

                    CurData curData[] = new CurData().get(where);
                    if (dbg) System.out.println("<br>curData.length = "+curData.length);

                    // loop through all data records for this set
                    for (int c=0; c < curData.length;c++) {

                        if (dbg) System.out.println("record line " +c+ " " +lat+ " " +lon+
                            " " +spldep);
                        if (dbg) System.out.println("record line "+curData[c].getSpeed()+
                            "*" +curData[c].getDatetime("") + " * " +curData[c].getCode());

                        String date     = curData[c].getDatetime("").substring(0,16);

                        Timestamp dateTime   = curData[c].getDatetime();
                        maxDate   = ec.max(maxDate, dateTime);
                        minDate   = ec.min(minDate, dateTime);

                        ec.writeFileLine(file,
                            // Take these out later on
                            ec.frm(mooring[i].getCode(),7) +
                            ec.frm(depth[j].getCode(),8) +
                            //ec.frm(curData[c].getCode(),8)+
                            ec.frm(lat,10,5)+
                            ec.frm(lon,10,5)+
                            ec.frm(spldep,9,2) + "  " +
                            ec.frm(date,17) +
                            ec.frm(curData[c].getSpeed(),8,2)+
                            ec.frm(curData[c].getDirection(),7,1)+
                            ec.frm(curData[c].getTemperature(),7,2));//+
                            //"  " + curData[c].getPercGood());

                    } //for (int c=0;c < data.length;c++) {

                    calDate.add(Calendar.DAY_OF_MONTH, 1);
                    dateTimeStart = calDate.getTimeInMillis();

                    if (dbg) System.out.println("dateTimeStart = " + dateTimeStart);
                    if (dbg) System.out.println("dateTimeEnd = " + dateTimeEnd);
                    if (dbg) System.out.println(formatter.format(calDate.getTime()));

                } // while (dateTImeStart <= dateTimeEnd)
/*
                //Get the code for first and last cur_data records
                field   = "min(code) as code";
                where   = CurData.DEPTH_CODE + " = " + depth[j].getCode();
                order   = "";
                CurData curData[] = new CurData().get(field,where,order);
                int minCode = curData[0].getCode();

                field       = "max(code) as code ";
                curData     = new CurData().get(field,where,order);
                int maxCode = curData[0].getCode();

                if (dbg) System.out.println("<br> curData where "+where);
                if (dbg) System.out.println("<br> minCode "+minCode);
                if (dbg) System.out.println("<br> maxCode "+maxCode);

                ec.writeFileLine(debugFile,"\nget data records where  : " + where +
                    ", passkey = " + depth[j].getPasskey(""));
                ec.writeFileLine(debugFile,"curData minCode  : "+minCode);
                ec.writeFileLine(debugFile,"curData maxCode  : "+maxCode);

                // Loop through the data_code...
                // To get data in packets of 100 records per packet.
                for (int l = minCode; l < maxCode; l+=SadConstants.INCR) {

                    if (dbg) System.out.println("l count "+ l + " " + curData.length);

                    where = CurData.DEPTH_CODE + " = " + depth[j].getCode() + " and " +
                        CurData.CODE + " between " + l + " and ";
                    if ((l+SadConstants.INCR-1) > maxCode) {
                        where += (maxCode);
                    } else {
                        where += (l+SadConstants.INCR-1);
                    } //if (l > maxCode) {

                    curData = new CurData().get(where);

                    if (dbg) System.out.println("\nCurData where "+where);
                    if (dbg) System.out.println("CurData length "+curData.length);

                    for (int c=0; c < curData.length;c++) {

                        if (dbg) System.out.println("record line " +c+ " " +lat+ " " +lon+
                            " " +spldep);
                        if (dbg) System.out.println("record line "+curData[c].getSpeed()+
                            "*" +curData[c].getDatetime("") + " * " +curData[c].getCode());

                        String date     = curData[c].getDatetime("").substring(0,16);

                        Timestamp dateTime   = curData[c].getDatetime();
                        maxDate   = ec.max(maxDate, dateTime);
                        minDate   = ec.min(minDate, dateTime);

                        ec.writeFileLine(file,
                            // Take these out later on
                            ec.frm(mooring[i].getCode(),7) +
                            ec.frm(depth[j].getCode(),8) +
                            //ec.frm(curData[c].getCode(),8)+
                            ec.frm(lat,10,5)+
                            ec.frm(lon,10,5)+
                            ec.frm(spldep,9,2) + "  " +
                            ec.frm(date,17) +
                            ec.frm(curData[c].getSpeed(),8,2)+
                            ec.frm(curData[c].getDirection(),7,1)+
                            ec.frm(curData[c].getTemperature(),7,2));

                    } //for (int c=0;c < data.length;c++) {
                } //for (int l=minCode; l < maxCode; l+=SadConstants.INCR) {

*/
            } //for (int j=0; j < depth; j +) {

            noRecords += 1;

        } //for ( int i=0; i < mooring.length; i++) {

        if (noRecords == 0) {
            ec.writeFileLine(file, "\n\nNo data found for Criteria specified" +
                 "\nstartDate     : " + ec.frm(startDate,16) +
                 "\nendDate       : " + ec.frm(endDate,7) +
                 "\nlatitudeNorth : " + ec.frm(latitudeNorth,8,5) +
                 "\nlatitudeSouth : " + ec.frm(latitudeSouth,8,5)+
                 "\nlongitudeWest : " + ec.frm(longitudeWest,8,5) +
                 "\nlongitudeEast : " + ec.frm(longitudeEast,8,5));
        } // if (noRecords == 0)

        ec.writeFileLine(file,"\nLegend\n"+
            ec.frm("MoorCode= Mooring Code ",23)+"\n"+
            ec.frm("DepCod  = Depth Code   ",23)+"\n"+
            ec.frm("DatCod  = Data Code    ",23)+"\n"+
            ec.frm("Lat     = Latitude     ",23)+"\n"+
            ec.frm("Lon     = Longitude    ",23)+"\n"+
            ec.frm("Spldep  = Sample Depth ",23)+"\n"+
            ec.frm("Date    = Date         ",23)+"\n"+
            ec.frm("Speed   = Current Speed ",24)+"\n"+
            ec.frm("Dir     = Current Direction ",28)+"\n"+
            ec.frm("Temp    = Current Temp ",23));

   } // getCurrentData

    void printHeader() {
        //EDM-CURR 29 29  31  31     0  9999 1972-01-01 1972-12-31

        if (dbg) System.out.println(" in printHeader function ");

        Timestamp sDate  = Timestamp.valueOf(startDate+" 00:00:00.0");
        Timestamp eDate  = Timestamp.valueOf(endDate+" 00:00:00.0");

        if (noRecords == 0 ) {
            minlatitude  = latitudeNorth;
            maxlatitude  = latitudeSouth;
            minlongitude = longitudeWest;
            maxlongitude = longitudeEast;
            //minDate      = startDateNoRecord;
            //maxDate      = endDateNoRecord;
            minDate      = sDate;
            maxDate      = eDate;
        } //if (noRecords == 0 ) {

        ec.writeFileLine(debugFile,"");
        ec.writeFileLine(debugFile,"minlatitude = " + minlatitude);
        ec.writeFileLine(debugFile,"maxlatitude = " + maxlatitude);
        ec.writeFileLine(debugFile,"minlongitude = " + minlongitude);
        ec.writeFileLine(debugFile,"maxlongitude = " + maxlongitude);

//        String nminlatitude  = String.valueOf(minlatitude);
//        String nmaxlatitude  = String.valueOf(maxlatitude);
//        String nminlongitude = String.valueOf(minlongitude);
//        String nmaxlongitude = String.valueOf(maxlongitude);

//        int nminlatitude  = (int) minlatitude;
//        int nmaxlatitude  = (int) (maxlatitude + .99f);
//        int nminlongitude = (int) minlongitude;
//        int nmaxlongitude = (int) (maxlongitude + .99f);

        int nminlatitude  = (int) minlatitude;
        int nmaxlatitude  = (int) Math.ceil(maxlatitude);
        int nminlongitude = (int) minlongitude;
        int nmaxlongitude = (int) Math.ceil(maxlongitude);

        if (dbg) System.out.println("<br> min(lat) "+nminlatitude);
        if (dbg) System.out.println("<br> max(lat) "+nmaxlatitude);
        if (dbg) System.out.println("<br> min(lon) "+nminlongitude);
        if (dbg) System.out.println("<br> max(lon) "+nmaxlongitude);
        if (dbg) System.out.println("<br> min(date) "+minDate);
        if (dbg) System.out.println("<br> max(date) "+maxDate);

        String stringMinDate = minDate.toString();
        String stringMaxDate = maxDate.toString();

        // header line.
        //EDM_CURR 30 40  30  40    10    20 1980-01-01 1999-12-31
        String line = " CURRENTS "+
              ec.frm(nminlatitude,2)  +" "+
              ec.frm(nmaxlatitude,2)  +" "+
              ec.frm(nminlongitude,2) +" "+
              ec.frm(nmaxlongitude,2) +" "+
              ec.frm(startdepth,4)    +" "+
              ec.frm(enddepth,4)      +" "+
              ec.frm(stringMinDate.substring(0,10),10)+" "+
              ec.frm(stringMaxDate.substring(0,10),10);

        //Print the cur_mooring header.
        try {
            file.seek(headerPos);
            file.writeBytes(line);
        } catch (Exception e) {
            System.out.println("Write error ExtractCURData "+e.getMessage());
            e.printStackTrace();
        } //try-catch
        //ec.writeFileLine(file, line);
        ec.writeFileLine(debugFile, "\n" + line);

    } // printHeader


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {
        try {
            ExtractCURData local= new ExtractCURData(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, "ExtractCURData", "Constructor", "");
        } // try-catch

        // close the connection to the database
        common2.DbAccessC.close();

    } // main

} // class ExtractCURData
