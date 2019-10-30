package sadco;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.Date;

import common2.Queue;

/**
 * Does the actual extraction of the requested data in ODV format.
 *
 * SadcoMRN
 * screen.equals("extract")
 * version.equals("extract")
 * extrType.equals("physnutodv")
 *
 * @author 020618 - SIT Group
 * @version
 * 020618 - Mario August       - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020710 - Ursula von St Ange - fetch watphy records in batches of depths  <br>
 * 040401 - Ursula von St Ange - change forcing of passkey to uppercase ub1 <br>
 * 091214 - Ursula von St Ange - change for inventory user (ub01)           <br>
 * 100105 - Ursula von St Ange - change for queuing of job (ub02)           <br>
 * 100615 - Ursula von St Ange - non-inventory users don't queue (ub03)     <br>
 * 100624 - Ursula von St Ange - change logging of job to beginning with
 *                               update at end (ub04)                       <br>
 * 111013 - Ursula von St Ange - change stnnam to stnid (ub05)              <br>
 * 121113 - Ursula von St Ange - add etopo2 depth (ub06)                    <br>
 * 121204 - Ursula von St Ange - ignore flagged records on extraction (ub07)<br>
 * 150204 - Ursula von St Ange - include flagged records, add QC flags (ub08)<br>
 *
 */
public class ExtractMRNPhysNutDataODV { //extends CompoundItem {

    //boolean dbg = false;
    boolean dbg = true;
    boolean dbgQ = false;
    //boolean dbgQ = true;  // set false when HOST is known.

    String thisClass = this.getClass().getName();

    /** some common functions */
    common.edmCommonPC ec = new common.edmCommonPC();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    // file stuff
    static RandomAccessFile ofile;

    // arguments
    String sessionCode;
    String reqNumber;
    String fileNameIn;                                                  //ub02
    String fileName;
    String extrType;
    String surveyId;
    String startDate;
    String endDate;
    String latNorth;
    String latSouth;
    String longWest;
    String longEast;
    float latitudeNorth;
    float latitudeSouth;
    float longitudeWest;
    float longitudeEast;
    Timestamp dateStart;
    Timestamp dateEnd;
    String flagType;
    String dataSet;
    String password;
    String passKey = "";
    String userType = "";                                               //ub01

    // variable from database
    int             noRecords = 0;

    // minimum and maximum's
    float           latitude     = 0.000f;
    float           maxLatitude  = -20.0f;
    float           minLatitude  = 99.0f;
    float           maxLongitude = -20.0f;
    float           minLongitude = 99.0f;
    Timestamp       minDate  = Timestamp.valueOf("2100-01-01 00:00:00.0");
    Timestamp       maxDate  = Timestamp.valueOf("1900-01-01 00:00:00.0");

    // qc variables                                                     //ub07
/*
    boolean profDO = true;                                              //ub07
    boolean profSal = true;                                             //ub07
    boolean profTmp = true;                                             //ub07
    boolean profNo3 = true;                                             //ub07
    boolean profPo4 = true;                                             //ub07
    boolean profSio3 = true;                                            //ub07
    boolean profChla = true;                                            //ub07
    boolean profDic = true;                                             //ub07
    boolean profPh = true;                                              //ub07
    boolean parmDO = true;                                              //ub07
    boolean parmSal = true;                                             //ub07
    boolean parmTmp = true;                                             //ub07
    boolean parmNo3 = true;                                             //ub07
    boolean parmPo4 = true;                                             //ub07
    boolean parmSio3 = true;                                            //ub07
    boolean parmChla = true;                                            //ub07
    boolean parmDic = true;                                             //ub07
    boolean parmPh = true;                                              //ub07
*/
    // constant for extracting watphy records
    int             spldepInterval = 500;
    
    private static PrintWriter pw;
    static {
        File file = new File("/opt/tomcat/logs/javash.log");
        file.getParentFile().mkdirs();

        try {
        	pw = new PrintWriter(new FileOutputStream(file), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main constructor ExtractMRNPhysNutDataODV
     * @param   args   String array of arguments as from form or URL
     */
    public ExtractMRNPhysNutDataODV(String args[])  {

        // Get the arguments
    	pw.println("Getting arguments");
        getArgParms(args);
        pw.println("Got arguments");

        // extract the user record from db
        pw.println("Getting session stuff");
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        String userid = sessions[0].getUserid();
        pw.println("Got session stuff");
        if (dbg) pw.println("<br>userid = " + userid);

        // get the correct PATH NAME - used while debugging
        pw.println("Getting rootPath");
        String rootPath = "";
        if (dbg) pw.println("<br>" + thisClass + ": host = " +
            ec.getHost());
        //if (ec.getHost().equals("morph")) {
        //if (ec.getHost().equals("morph")) {
        //    rootPath = sc.MORPH + userid + "/";
        //} else {
        //    rootPath = sc.LOCAL + "marine/";
        //} // if host
        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                rootPath = sc.HOSTDIR;                    //ub01
            } else {                                                    //ub01
                rootPath = sc.HOSTDIR;                   //ub01
            } // if ("0".equals(userType))                              //ub01
            dbgQ = false;                                               //ub08
        } else {
            rootPath = sc.LOCALDIR + "marine/";
        } // if host

        pw.println("Got rootPath: " + rootPath);
        // new filename, but keep the original file name                //ub02
        fileNameIn = fileName;                                          //ub02
        if (!reqNumber.equals("")) {
            fileName = reqNumber + "_" + fileName;
        } // if

        //changing file exstention from dbm to txt for ODV programme.
        fileName += ".txt";
        if (dbg) pw.println("<br>" + thisClass + ": " + fileName);

        // delete an old dummy file for Progress purposes ......
        String dummyFile = rootPath + fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        // create .queued file for Progress purposes ......             //ub02
        String dummyFile2 = rootPath + fileName + ".queued";            //ub02
        Queue queue = null;
        
        pw.println("Dummyfile2: " + dummyFile2);

        Timestamp startQDate = new Timestamp(new Date().getTime());     //ub04
        if ("0".equals(userType)) {  // from inventory                  //ub03
        	pw.println("Usertype is 0");
            // create .queued file for Progress purposes ......         //ub02
        	pw.println("Creating new .queued file");
            ec.createNewFile(dummyFile2);                               //ub02
            pw.println("Done creating .queued file");

            // queue the extraction                                     //ub02
            if (!dbgQ) {
            	pw.println("Creating a new sadinv.Queue with userId[" + userid + "], filename[" + thisClass+" "+fileNameIn + ", args");
                queue = new Queue(userid,thisClass+" "+fileNameIn,args);//ub02
                pw.println("sadinv.Queue created");
                queue.enQueue(); 
                pw.println("Enqueued");//ub02
                queue.wait4Turn();  
                pw.println("Waiting for my turn");//ub02
                if (dbg) pw.println("<br>" + thisClass + ": queued");
            } // if (!dbgQ)

            // delete .queued file, and create .progressing file        //ub02
            pw.println("deleting old file: " + dummyFile2);
            ec.deleteOldFile(dummyFile2);                               //ub02
            pw.println("deleted old file: " + dummyFile2);
        } // if ("0".equals(userType)) {                                //ub03

        // create the user_log entry                                    //ub04
        pw.println("Getting timestamp");
        Timestamp endQDate = new Timestamp(new Date().getTime());       //ub04
        pw.println("Timestamp received: " + endQDate.toString());
        pw.println("Log being created");
        LogUsers log = createLogUsers(                                  //ub04
            userid, endQDate, ec.timeDiff(endQDate, startQDate));       //ub04
        pw.println("Log created");
        // create the 'processing' file
        pw.println("Creating .processing file");
        dummyFile2 = rootPath + fileName + ".processing";
        ec.createNewFile(dummyFile2);
        pw.println("Finished creating .processing file");

        // Open the data file.
        pw.println("delete old file");
        ec.deleteOldFile(rootPath + fileName);
        ofile = ec.openFile(rootPath + fileName, "rw");
        try {
			pw.println("Opened file of length: " + ofile.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob("chmod 644 " + rootPath + fileName);
        } //if (ec.getHost().startsWith(sc.HOST)) {

        // print the column headings
        ColumnHeadings();

        // check if the arguments are valid
        boolean validQuery = true;
        if ("".equals(surveyId)) {
            if ("".equals(latNorth) || "".equals(latSouth) ||
                "".equals(longWest) || "".equals(longEast) ||
                "".equals(startDate) || "".equals(endDate)) {
                printErrorMessage();
                validQuery = false;
            } else {
                latitudeNorth = new Float(latNorth).floatValue();
                latitudeSouth = new Float(latSouth).floatValue();
                longitudeWest = new Float(longWest).floatValue();
                longitudeEast = new Float(longEast).floatValue();
                // correct end latitudes and longitudes
                latitudeSouth -= .00001f;
                longitudeEast -= .00001f;
                // timestamp in format yyyy-mm-dd hh:mm:ss.fffffffff
                dateStart = Timestamp.valueOf(startDate + " 00:00:00.00");
                dateEnd = Timestamp.valueOf(endDate + " 00:00:00.00");
            } // any range values ""
        } // if ("".equals(surveyId))

        // check if flagType combination is valid.  If not, revert back to
        // unflagged data extraction only
        if (dbg) pw.println("<br>flagType = " + flagType +
            ", dataSet = " + dataSet + ", password = " + password);
        if (!"".equals(flagType)) {
            // was there a dataSet or a Password
            if ("".equals(dataSet) || "".equals(password)) {
                flagType = "1";
            } else {
                // is this user registered for the dataset
                //SadUserDset[] userDset = new SadUserDset(userid, dataSet).get();
                SadUserDset tmp = new SadUserDset(userid, dataSet);
                SadUserDset[] userDset = tmp.get();
                if (dbg) pw.println("<br>tmp.getSelStr() = " + tmp.getSelStr());
                if (dbg) pw.println("<br>userDset.length = " +
                    userDset.length);
                if (userDset.length == 0) {
                    flagType = "1";
                } else {
                    // did the user give the right password?
                    SadUsers[] users = new SadUsers().get(
                        SadUsers.FLAG_PASSWORD,
                        SadUsers.USERID + "='" + userid + "'", "");
                    if (dbg) pw.println("<br>password = " + password +
                        ", users[0].getFlagPassword() = " +
                        users[0].getFlagPassword());
                    if (dbg) pw.println("<br>users[0] = " + users[0]);
                    if (!users[0].getFlagPassword().equals(password)) {
                        flagType = "1";
                    } else {
                        // get the dataset password
                        SadUsers[] dataset = new SadUsers().get(
                        SadUsers.PASSWORD,
                        SadUsers.USERID + "='" + dataSet + "'", "");
                        if (dataset.length > 0) {
                            passKey = dataset[0].getPassword(); //.toUpperCase(); // ub1
                        } else {
                            flagType = "1";
                        } // if (dataset.length > 0)
                    }// if (!users[0].getPassword.equals(password)
                } // if (userDSet.length == 0)
            } // if ("".equals(dataSet) || "".equals(password))
        } else {
            flagType = "1";
        } // if (!"".equals(flagType))
        System.gc();

        if (dbg) pw.println("<br>flagType = " + flagType);
        if (dbg) pw.println("<br>validQuery = " + validQuery);
        //
        if (validQuery) {
            MrnStation[] stations = getStationRecords();
            System.gc();
            getDataRecords(stations);
        } // if (validQuery)

        // clean up
        ec.closeFile(ofile);

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
                if (dbg) pw.println("<br>" + thisClass + ": dequeued");
            } // if (!dbgQ)
        } // if ("0".equals(userType))                                  //ub03

    } // ExtractMRNPhysNutDataODV(String args[])


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        if (dbg) for (int i = 0; i < args.length; i++) {
            pw.println("<br>" + args[i]);
        } // for i
        sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        reqNumber = ec.getArgument(args, sc.REQNUMBER);
        fileName = ec.getArgument(args, sc.FILENAME);
        extrType = ec.getArgument(args, sc.EXTRTYPE);
        userType = ec.getArgument(args,SadConstants.USERTYPE);          //ub01

        flagType = ec.getArgument(args, sc.FLAGTYPE);
        dataSet = ec.getArgument(args, sc.DATASET);

        password = ec.getArgument(args, sc.PASSWORD);

        surveyId = ec.getArgument(args, sc.SURVEYID);

        startDate = ec.getArgument(args, sc.STARTDATE);
        endDate = ec.getArgument(args, sc.ENDDATE);

        latNorth = ec.getArgument(args,sc.LATITUDENORTH);
        latSouth = ec.getArgument(args,sc.LATITUDESOUTH);
        longWest = ec.getArgument(args,sc.LONGITUDEWEST);
        longEast = ec.getArgument(args,sc.LONGITUDEEAST);

    } // getArgParms


    /**
     * get the station records from the database
     */
    MrnStation[] getStationRecords() {

        // contruct the where clause
        String where = "";
        if (!"".equals(surveyId)) {
            where = MrnStation.SURVEY_ID + "='" + surveyId + "'";
        } else {
            surveyId = "/";
            where =
                MrnStation.LATITUDE + " between " +
                    latitudeNorth + " and " + latitudeSouth + " and " +
                MrnStation.LONGITUDE + " between " +
                    longitudeWest + " and " + longitudeEast + " and " +
                MrnStation.DATE_START + " between " +
                    "to_timestamp('" + startDate + "','yyyy-mm-dd') and " +
                    "to_timestamp('" + endDate + "','yyyy-mm-dd')";
        } // if (!"".equals(surveyId))
        if ("1".equals(flagType)) {
            where += " and " + MrnStation.PASSKEY + " is NULL";
        } else if ("2".equals(flagType)) {
            where += " and " + MrnStation.PASSKEY + "='" + passKey + "'";
        } else {
            where += " and (" + MrnStation.PASSKEY + " is NULL";
            where += " or " + MrnStation.PASSKEY + "='" + passKey + "')";
        } // if ("1".equals(flagType))
        // where += " and " + MrnStation.STNDEP + " >= 1000";    // special

        String fields =
            MrnStation.STATION_ID + "," + MrnStation.SURVEY_ID + "," +
            MrnStation.STNNAM + "," + MrnStation.LATITUDE + "," +
            MrnStation.LONGITUDE + "," + MrnStation.DATE_START + "," +
            MrnStation.STNDEP + "," + MrnStation.MAX_SPLDEP + "," +
            MrnStation.PASSKEY;
        String order = MrnStation.SURVEY_ID + "," + MrnStation.DATE_START;
        if (dbg) pw.println("<br>station where = " + where);
        MrnStation[] stations = new MrnStation().get(fields, where, order);
        if (dbg) pw.println("<br>station where = " + where);
        if (dbg) pw.println("<br>stations.length = " + stations.length);
        return stations;
    } // getStationRecords


    /**
     * get the data records from the database
     */
    int getDataRecords(MrnStation[] stations) {
        noRecords = 0;
        String fields = "", where = "", order = "";
        String surveyId2 = "";

        for (int i = 0; i < stations.length; i++) {

            if (dbg) pw.println("<br>i = " + i + " " + stations[i].getStationId());

            // get profiles QC flag                                     // ub07
//            getProfQCFlags(stations[i].getStationId());                 // ub07

            // get watphy records
            // first check what the max depth for the station is.
            fields = "max(" + MrnWatphy.SPLDEP + ") as " + MrnWatphy.SPLDEP;
            where =
                MrnWatphy.STATION_ID + "='" + stations[i].getStationId() + "'";
            order = MrnWatphy.SPLDEP;
            if (dbg) pw.println("<br>watphy where = " + where);
            MrnWatphy[] watphy2 = new MrnWatphy().get(fields, where, order);
            float maxSpldep = 0;
            if (watphy2.length > 0) {
                //if (dbg) pw.println("<br>watphy = " + watphy[0]);
                maxSpldep = watphy2[0].getSpldep();
            }
            if (dbg) pw.println("<br>watphy2.length = " + watphy2.length +
                ", maxSpldep = " + maxSpldep);

            fields =
                MrnWatphy.CODE + "," + MrnWatphy.SPLDATTIM + "," +
                MrnWatphy.SPLDEP + "," + MrnWatphy.SUBDES + "," +
                MrnWatphy.DISOXYGEN + "," + MrnWatphy.SALINITY + "," +
                MrnWatphy.TEMPERATURE + "," + MrnWatphy.SOUNDV;
            order = MrnWatphy.SPLDEP;

            // do a loop over the station depth - in intervals of 500 m
            for (int k = 0; k <= maxSpldep; k += spldepInterval) {

                where =
                    MrnWatphy.STATION_ID + "='" + stations[i].getStationId() + "' and " +
                    MrnWatphy.SPLDEP + " between " + k + " and " + (k+spldepInterval-0.01);
                //if (dbg) pw.println("<br>where = " + where);
                MrnWatphy[] watphy = new MrnWatphy().get(fields, where, order);
                if (dbg) pw.println("<br>i = " + i +
                    ", where = " + where +
                    ", watphy.length = " + watphy.length);

                for (int j = 0; j < watphy.length; j++) {
                    //if (dbg) pw.println("<br>j = " + j);
                    int code = watphy[j].getCode();

                    // get the parameter QC values                      // ub07
//                    getParmQCFlags(code);                               // ub07

                    // get the QC records
                    String fields2 = "*";                                       //ub08
                    where = MrnWatqc.WATPHY_CODE + "=" + code;                  //ub08
                    MrnWatqc[] watqc = new MrnWatqc().get(fields2, where, "");  //ub08

                    // get the water nutrients data
                    fields2 = MrnWatnut.NO2 + "," + MrnWatnut.NO3 + "," +
                        MrnWatnut.PO4 + "," + MrnWatnut.P + "," +
                        MrnWatnut.SIO3;
                    where = MrnWatnut.WATPHY_CODE + "=" + code;
                    MrnWatnut[] watnut = new MrnWatnut().get(fields2, where, "");
                    //if (dbg) pw.println("<br>watnut.length = " + watnut.length);

                    // get the water chemical data
                    fields2 = MrnWatchem1.PH;
                    where = MrnWatchem1.WATPHY_CODE + "=" + code;
                    MrnWatchem1[] watchem1 = new MrnWatchem1().get(fields2, where, "");
                    //if (dbg) pw.println("<br>watchem1.length = " + watchem1.length);

                    // get the water chlorofil data
                    fields2 = MrnWatchl.CHLA;
                    where = MrnWatchl.WATPHY_CODE + "=" + code;
                    MrnWatchl[] watchl = new MrnWatchl().get(fields2, where, "");
                    if (dbg) pw.println("<br>j = " + j +
                        ", watnut.length = " + watnut.length +
                        ", watchem1.length = " + watchem1.length +
                        ", watchl.length = " + watchl.length);

                    printRecords(stations[i], watphy[j], watqc, watnut,
                        watchem1, watchl);
                    noRecords += 1;
                } // for (int j = 0; j < watphys.length; j++)

            } // for int k

        } // for int i
        System.gc();

        return noRecords;
    } //


    /**                                                                 //ub07
     * get the profile QC flags                                         //ub07
     */                                                                 //ub07
/*
    void getProfQCFlags(String stationId) {                             //ub07
                                                                        //ub07
        String where = MrnWatprofqc.STATION_ID + "='" + stationId + "'";//ub07
        MrnWatprofqc profqc[] = new MrnWatprofqc().get(where);          //ub07
                                                                        //ub07
        if (profqc.length > 0) {                                        //ub07
            profDO = isOKValue(profqc[0].getDisoxygen());               //ub07
            profSal = isOKValue(profqc[0].getSalinity());               //ub07
            profTmp = isOKValue(profqc[0].getTemperature());            //ub07
            profNo3 = isOKValue(profqc[0].getNo3());                    //ub07
            profPo4 = isOKValue(profqc[0].getPo4());                    //ub07
            profSio3 = isOKValue(profqc[0].getSio3());                  //ub07
            profChla = isOKValue(profqc[0].getChla());                  //ub07
            profDic = isOKValue(profqc[0].getDic());                    //ub07
            profPh = isOKValue(profqc[0].getPh());                      //ub07
        } else {                                                        //ub07
            profDO = true;                                              //ub07
            profSal = true;                                             //ub07
            profTmp = true;                                             //ub07
            profNo3 = true;                                             //ub07
            profPo4 = true;                                             //ub07
            profSio3 = true;                                            //ub07
            profChla = true;                                            //ub07
            profDic = true;                                             //ub07
            profPh = true;                                              //ub07
        } // if (profqc.length > 0)                                     //ub07
        pw.println("getProfQCFlags : " + stationId +
            ": profSal = " + profSal + ", profTmp = " + profTmp +
            " " + profqc[0].getTemperature());
    } // getProfQCFlags                                                 //ub07
*/

    /**                                                                 //ub07
     * get the parameter QC flags                                       //ub07
     */                                                                 //ub07
/*
    void getParmQCFlags(int code) {                                     //ub07
                                                                        //ub07
        String where = MrnWatqc.WATPHY_CODE + "=" + code;               //ub07
        MrnWatqc qc[] = new MrnWatqc().get(where);                      //ub07
                                                                        //ub07
        if (qc.length > 0) {                                            //ub07
            parmDO = isOKValue(qc[0].getDisoxygen());                   //ub07
            parmSal = isOKValue(qc[0].getSalinity());                   //ub07
            parmTmp = isOKValue(qc[0].getTemperature());                //ub07
            parmNo3 = isOKValue(qc[0].getNo3());                        //ub07
            parmPo4 = isOKValue(qc[0].getPo4());                        //ub07
            parmSio3 = isOKValue(qc[0].getSio3());                      //ub07
            parmChla = isOKValue(qc[0].getChla());                      //ub07
            parmDic = isOKValue(qc[0].getDic());                        //ub07
            parmPh = isOKValue(qc[0].getPh());                          //ub07
        } else {                                                        //ub07
            parmDO = true;                                              //ub07
            parmSal = true;                                             //ub07
            parmTmp = true;                                             //ub07
            parmNo3 = true;                                             //ub07
            parmPo4 = true;                                             //ub07
            parmSio3 = true;                                            //ub07
            parmChla = true;                                            //ub07
            parmDic = true;                                             //ub07
            parmPh = true;                                              //ub07
        } // if (profqc.length > 0)                                     //ub07
        pw.println("getParmQCFlags : " + code +
            ": parmSal = " + parmSal + ", parmTmp = " + parmTmp +
            " " + qc[0].getTemperature());
    } // getParmQCFlags                                                 //ub07
*/

    /**
     * return true if a valid value, else false
     */
    boolean isOKValue(float value) {
        return (value == 0 ? true : false);
    } // isOKValue


    /**
     * Print the current marine data records
     * @param   station   MrnStation data
     * @param   watphy    MrnWatphy data
     * @param   watnut    MrnWatnut data
     * @param   watchem1  MrnWatchem1 data
     * @param   watchl    MrnWatchl data
     */
    void printRecords(MrnStation station, MrnWatphy watphy, MrnWatqc[] watqc, //ub08
            MrnWatnut[] watnut, MrnWatchem1[] watchem1, MrnWatchl[] watchl) {

        // get the variables for records that may be null
        //String planamName = "";
        //if (planam.length > 0) {
        //    planamName = planam[0].getName("");
        //    //ec.writeFileLine(ofile, "planam:  " + planam[0].toString());
        //} //
        //if (dbg) pw.println("<br>planamName = " + planamName);

        //ec.writeFileLine(ofile, "station: " + station.toString());
        //ec.writeFileLine(ofile, "watphy:  " + watphy.toString());

        int depthQC = -1;                                               //ub08
        int disoxQC = -1;                                               //ub08
        int salinQC = -1;                                               //ub08
        int tmpQC   = -1;                                               //ub08
        int no3QC   = -1;                                               //ub08
        int po4QC   = -1;                                               //ub08
        int sio3QC  = -1;                                               //ub08
        int chlaQC  = -1;                                               //ub08
        int phQC    = -1;                                               //ub08
        if (watqc.length > 0) {                                         //ub08
            depthQC = watqc[0].getSpldep();                             //ub08
            disoxQC = watqc[0].getDisoxygen();                          //ub08
            salinQC = watqc[0].getSalinity();                           //ub08
            tmpQC   = watqc[0].getTemperature();                        //ub08
            no3QC   = watqc[0].getNo3();                                //ub08
            po4QC   = watqc[0].getPo4();                                //ub08
            sio3QC  = watqc[0].getSio3();                               //ub08
            chlaQC  = watqc[0].getChla();                               //ub08
            phQC    = watqc[0].getPh();                                 //ub08
        } // if (watqc.length > 0)                                      //ub08

        float no2  = MrnWatnut.FLOATNULL;
        float no3  = MrnWatnut.FLOATNULL;
        float p    = MrnWatnut.FLOATNULL;
        float po4  = MrnWatnut.FLOATNULL;
        float sio3 = MrnWatnut.FLOATNULL;
        if (watnut.length > 0) {
            no2  = watnut[0].getNo2();
            no3  = watnut[0].getNo3();
            po4  = watnut[0].getPo4();
            p    = watnut[0].getP();
            sio3 = watnut[0].getSio3();
            //ec.writeFileLine(ofile, "watnut:  " + watnut[0].toString());
        } //

        float ph = MrnWatchem1.FLOATNULL;
        if (watchem1.length > 0) {
            ph = watchem1[0].getPh();
            //ec.writeFileLine(ofile, "watchem1:" + watchem1[0].toString());
        } //

        float chla = MrnWatchl.FLOATNULL;
        if (watchl.length > 0) {
            chla = watchl[0].getChla();
            //ec.writeFileLine(ofile, "watchl:  " + watchl[0].toString());
        } //

        // get the time in string format
        Timestamp dateT = watphy.getSpldattim();
        String    year = dateT.toString().substring(0,4);
        String    month = dateT.toString().substring(5,7);
        String    day = dateT.toString().substring(8,10);
        String    ftime = dateT.toString().substring(10,16);


        // get actual min and max ranges
        maxLatitude = ec.max(maxLatitude, station.getLatitude());
        minLatitude = ec.min(minLatitude, station.getLatitude());
        maxLongitude = ec.max(maxLongitude, station.getLongitude());
        minLongitude = ec.min(minLongitude, station.getLongitude());


        maxDate = ec.max(maxDate, dateT);
        minDate = ec.min(minDate, dateT);

        //get getSubdes and test fot type
        String sub = watphy.getSubdes();
        if ("bottl".equalsIgnoreCase(sub)) {
            sub = "B";
        } else if ("osd".equalsIgnoreCase(sub)) {
            sub = "B";
        } else if ("ctd".equalsIgnoreCase(sub)) {
            sub = "C";
        } else if ("xbt".equalsIgnoreCase(sub)) {
            sub = "C";
        } else if ("mbt".equalsIgnoreCase(sub)) {
            sub = "C";
        } else {
            sub = "*";
        } //if ("Bottl".equalsIgnoreCase(sub))

        if (dbg) pw.println("<br>maxLatitude = " + maxLatitude +
            ", minLatitude = " + minLatitude +
            ", maxLongitude = " + maxLongitude +
            ", minLongitude = " + minLongitude +
            ", maxDate = " + maxDate +
            ", minDate = " + minDate);
        //if (dbg) {
        //    String tLine = dateT.toString() + " " + year + " " + month + " " +
        //        day + " " + ftime;
        //    pw.println("<br>" + thisClass +  ".printRecords: " +
        //    tLine);
        //} // if (dbg)

        // Print the BODY OF THE FILE
        latitude = station.getLatitude() * -1;
        float etopo2Depth = sc.getEtopo2(station);                      //ub06

        //String stId       = station.getStationId();//ub04
        //String giveName   = stId.substring(3);     //ub04
        //String stName     = station.getStnnam();   //ub04

        //if ( ("".equals(stName) ) || (stName == MrnStation.CHARNULL) ) {//ub04
        //    stName=giveName;                                            //ub04
        //} //if ("".equals(stName)) {                                    //ub04

//        pw.println("temp: " + station.getStationId() + " " +
//            watphy.getTemperature() + " " + profTmp + " " + parmTmp + " " +
//            nullToNULL2(watphy.getTemperature(),8,2,profTmp,parmTmp));

        String tab = "\t";

/*
        String line =
        ec.frm(station.getSurveyId(),9)     +tab+
        ec.frm(station.getStationId(),12)   +tab+       //ub05
        ec.frm(sub,5)                       +tab+
        month+"/" +day+"/"+ year            +tab+
        ftime.trim()                        +tab+
        ec.frm(station.getLongitude(),8,3)  +tab+
        ec.frm(latitude,8,3)                +tab+
        nullToZERO(station.getStndep(),8,2)     +tab+
        nullToZERO(etopo2Depth,8,2)             +tab+               //ub06
        nullToNULL(watphy.getSpldep(),8,2)      +tab+
        nullToNULL2(watphy.getTemperature(),8,2,profTmp,parmTmp) +tab+ //ub07
        nullToNULL2(watphy.getSalinity(),9,3,profSal,parmSal)    +tab+ //ub07
        nullToNULL2(watphy.getDisoxygen(),8,2,profDO,parmDO)     +tab+ //ub07
        nullToNULL(no2,8,2)                     +tab+
        nullToNULL2(no3,8,2,profNo3,parmNo3)                     +tab+ //ub07
        nullToNULL2(po4,8,2,profPo4,parmPo4)                     +tab+ //ub07
        nullToNULL(p,8,2)                       +tab+
        nullToNULL2(sio3,8,2,profSio3,parmSio3)                  +tab+ //ub07
        nullToNULL2(ph,8,2,profPh,parmPh)                        +tab+ //ub07
        nullToNULL(watphy.getSoundv(),7,1)      +tab+
        nullToNULL2(chla,9,3,profChla,parmChla)                  +tab+ //ub07
        nullToNULL(station.getMaxSpldep(),8,2);
*/
        String line =
        ec.frm(station.getSurveyId(),9)     +tab+
        ec.frm(station.getStationId(),12)   +tab+       //ub05
        ec.frm(sub,5)                       +tab+
        month+"/" +day+"/"+ year            +tab+
        ftime.trim()                        +tab+
        ec.frm(station.getLongitude(),8,3)  +tab+
        ec.frm(latitude,8,3)                +tab+
        nullToZERO(station.getStndep(),8,2)         +tab+
        nullToZERO(etopo2Depth,8,2)                 +tab+ //ub06
        nullToNULL(watphy.getSpldep(),8,2)          +tab+
        checkMissing(watphy.getSpldep(),depthQC)    +tab+ //ub08
        nullToNULL(watphy.getTemperature(),8,2)     +tab+
        checkMissing(watphy.getTemperature(), tmpQC)+tab+ //ub08
        nullToNULL(watphy.getSalinity(),9,3)        +tab+
        checkMissing(watphy.getSalinity(), salinQC) +tab+ //ub08
        nullToNULL(watphy.getDisoxygen(),8,2)       +tab+
        checkMissing(watphy.getDisoxygen(), disoxQC)+tab+ //ub08
        nullToNULL(no2,8,2)                         +tab+
        nullToNULL(no3,8,2)                         +tab+
        checkMissing(no3, no3QC)                    +tab+ //ub08
        nullToNULL(po4,8,2)                         +tab+
        checkMissing(po4, po4QC)                    +tab+ //ub08
        nullToNULL(p,8,2)                           +tab+
        nullToNULL(sio3,8,2)                        +tab+
        checkMissing(sio3, sio3QC)                  +tab+ //ub08
        nullToNULL(ph,8,2)                          +tab+
        checkMissing(ph, phQC)                      +tab+ //ub08
        nullToNULL(watphy.getSoundv(),7,1)          +tab+
        nullToNULL(chla,9,3)                        +tab+
        checkMissing(chla, chlaQC)                  +tab+ //ub08
        nullToNULL(station.getMaxSpldep(),8,2);
        /*
        ec.frm(ec.nullToNines(station.getStndep(),9999.0f),8,2)    +tab+
        ec.frm(ec.nullToNines(watphy.getSpldep(),9999.0f),8,2)     +tab+
        ec.frm(ec.nullToNines(watphy.getTemperature(),9999.0f),8,2)+tab+
        ec.frm(ec.nullToNines(watphy.getSalinity(),9999.0f),9,3)   +tab+
        ec.frm(ec.nullToNines(watphy.getDisoxygen(),9999.0f),8,2)  +tab+
        ec.frm(ec.nullToNines(no2,9999.0f),8,2)                    +tab+
        ec.frm(ec.nullToNines(no3,9999.0f),8,2)                    +tab+
        ec.frm(ec.nullToNines(po4,9999.0f),8,3)                    +tab+
        ec.frm(ec.nullToNines(p,9999.0f),9,3)                      +tab+
        ec.frm(ec.nullToNines(sio3,9999.0f),8,2)                   +tab+
        ec.frm(ec.nullToNines(ph,9999.0f),8,2)                     +tab+
        ec.frm(ec.nullToNines(watphy.getSoundv(),9999.0f),7,1)     +tab+
        ec.frm(ec.nullToNines(chla,9999.0f),9,3)                   +tab+
        ec.frm(ec.nullToNines(station.getMaxSpldep(),9999.0f),8,2);
        */

        //if (dbg) pw.println("<br>" + thisClass + ".printRecords: " +
        //    line1);

        //if (dbg) pw.println("<br>" + thisClass + ".printRecords: " +
        //    line);

        ec.writeFileLine(ofile,line);

    } // printRecords

    /**
     * print the column headings
     */
    void ColumnHeadings() {
        // method to write headings of estraxted data
        String head =
            "Cruise\tStation\tType\tmon/day/yr\thh:mm\tLon (�E)\t"+
            "Lat (�N)\tBot. Depth [m]\tEtopo2 Depth [m]\tDepth [m]\tQF\t"+  //ub06 ub08
            "Temperature [deg C]\tQF\tSalinity [ppt]\tQF\tDisolved Oxygen [ml/l]\tQF\t"+ //ub08
            "Nitrite [~$m~#g atom/l]\tNitrate [~$m~#g atom/l]\tQF\t"+           //ub08
            "Phosphate [~$m~#g atom/l]\tQF\tPhosporus [~$m~#g atom/l]\t"+       //ub08
            "Silicate [~$m~#g atom/l]\tQF\tpH []\tQF\tSound Velocity [m/s]\t"+  //ub08
            "Chlorophyll A [~$m~#g/l]\tQF\tDeepest Obs. Depth [m]";             //ub08
        ec.writeFileLine(ofile,head);
    } // ColumnHeadings


    /**
     * return "0" of null value
     */
    String nullToZERO(float value, int length, int decimal) {
        return
            (value == Tables.FLOATNULL
                ? "0"
                : ec.frm(value,length,decimal));
        //(station.getStndep()==INTN?ec.frm(station.getStndep(),8,2):"1e+010") +tab+
    } // nullToZERO


    /**
     * return ODV null value if null
     */
    String nullToNULL(float value, int length, int decimal) {
        return
            (value == Tables.FLOATNULL
                ? "1e+010"
                : ec.frm(value,length,decimal));
        //(station.getStndep()==INTN?ec.frm(station.getStndep(),8,2):"1e+010") +tab+
    } // nullToNULL


    /**
     * return ODV null value if invalid value
     */
    String nullToNULL2(float value, int length, int decimal, boolean prof, boolean parm) {
        //pw.println("nullToNULL2: " + value + " " + prof + " " + parm);
        if (prof && parm)
            return
                (value == Tables.FLOATNULL
                    ? "1e+010"
                    : ec.frm(value,length,decimal));
        else return "1e+010";
        //(station.getStndep()==INTN?ec.frm(station.getStndep(),8,2):"1e+010") +tab+
    } // nullToNULL2


    /**
     * Do mapping of WOD to ODV flags, and return a 1 if the value is missing
     */
    int checkMissing(float value, int qcValue) {

        int qcValue2 = qcValue;
        if (value == Tables.FLOATNULL) qcValue2 = 1;
        if (qcValue == -1) qcValue2 = 1;
        if ((qcValue >= 1) && (qcValue <= 5)) qcValue2 = 4;
        if (qcValue >= 6) qcValue2 = 8;
        return qcValue2;

    } // checkMissing

    /**
     * print an error message if inputs not OK
     */
    void printErrorMessage() {
    } // printErrorMessage


    /**                                                                 //ub04
     * Create the LOG_USERS table entry for this extraction.            //ub04
     */                                                                 //ub04
    LogUsers createLogUsers(String userid, Timestamp start, String timeDiff) {//ub04
        LogUsers logs = new LogUsers();                                 //ub04
        logs.setUserid(userid);                                         //ub04
        logs.setDateTime(start);                                        //ub04
        logs.setDatabase("MARINE");                                     //ub04
        logs.setDiscipline("physnutodv");                               //ub04
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
        logs.setDatabase("MARINE");
        logs.setDiscipline("physnutodv");
        logs.setProduct("Extraction");
        logs.setFileName(fileName);
        logs.setLatitudeNorth(minLatitude);
        logs.setLatitudeSouth(maxLatitude);
        logs.setLongitudeWest(minLongitude);
        logs.setLongitudeEast(maxLongitude);
        logs.setDateStart(minDate);
        logs.setDateEnd(maxDate);
        logs.setSurveyId(surveyId);
        logs.setNumRecords(noRecords);
        logs.setJobDuration(timeDiff);
        pw.println(logs);
        boolean success = logs.put();
    } // updateLogUsers
*/

    /**                                                                 //ub04
     * Update the LOG_USERS table for this extraction.                  //ub04
     */                                                                 //ub04
    void updateLogUsers(LogUsers logs, String timeDiff) {               //ub04
        // define the value that should be updated                      //ub04
        LogUsers updateLogs = new LogUsers();                           //ub04
        updateLogs.setLatitudeNorth(minLatitude);                       //ub04
        updateLogs.setLatitudeSouth(maxLatitude);                       //ub04
        updateLogs.setLongitudeWest(minLongitude);                      //ub04
        updateLogs.setLongitudeEast(maxLongitude);                      //ub04
        updateLogs.setDateStart(minDate);                               //ub04
        updateLogs.setDateEnd(maxDate);                                 //ub04
        updateLogs.setNumRecords(noRecords);                            //ub04
        updateLogs.setJobDuration(timeDiff);                            //ub04
        if (dbg) pw.println("updateLogUsers: minLatitude = " + minLatitude);
        if (dbg) pw.println("updateLogUsers: maxLatitude = " + maxLatitude);
        if (dbg) pw.println("updateLogUsers: minLongitude = " + minLongitude);
        if (dbg) pw.println("updateLogUsers: maxLongitude = " + maxLongitude);
        if (dbg) pw.println("updateLogUsers: minDate = " + minDate);
        if (dbg) pw.println("updateLogUsers: maxDate = " + maxDate);
        if (dbg) pw.println("updateLogUsers: noRecords = " + noRecords);
        if (dbg) pw.println("updateLogUsers: timeDiff = " + timeDiff);
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
            ExtractMRNPhysNutDataODV local =
                new ExtractMRNPhysNutDataODV(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "ExtractMRNPhysNutDataODV", "Constructor", "");
            // close the connection to the database
        } // try-catch
        common2.DbAccessC.close();

    } // main


 }// class ExtractMRNPhysNutDataODV
