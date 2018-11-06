package sadco;

import common2.Queue;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Extracts the specified depth of a current deployment.
 *
 * SadcoCurApps
 * screen.equals("extract")
 * version.equals("doextract")
 * extrType.equals("cur")
 *
 * @author 020803 - SIT Group
 * @version
 * 091214 - Ursula von St Ange - change for inventory user (ub01)           <br>
 * 100105 - Ursula von St Ange - change for queuing of job (ub02)           <br>
 * 100615 - Ursula von St Ange - non-inventory users don't queue (ub03)     <br>
 * 100624 - Ursula von St Ange - change logging of job to beginning with
 *                               update at enc (ub04)                       <br>
 * 140124 - Ursula von St Ange - add pressure (ub05)                        <br>
 * 140127 - Ursula von St Ange - add QC stuff (ub05)                        <br>
 */
public class ExtractCURDepthsData {

    boolean dbg = false;
    //boolean dbg = true;
    boolean dbgQ = false;
    //boolean dbgQ = true;

    // a set of common general purpose routines
    static common.edmCommonPC ec = new common.edmCommonPC();
    SadConstants sc = new SadConstants();
    String thisClass = this.getClass().getName();

    // parameters from html page
    //int numdepths            = 0;
    //String depthCode[]       = new String[50];

    String surveyId     = "";
    String reqno        = "";
    String fileNameIn   = "";
    String sessionCode  = "";
    String depthCode    = "";
    String userType     = "";                                           //ub01

    // the output file
    RandomAccessFile file;

    // Data fields.
    float ph          = 0;
    float salinity    = 0;
    float disOxy      = 0;

    // sql variables
    String field      = "";
    String where      = "";
    String order      = "";

    // for logusers
    CurMooring mooring[];

    // the list of variables in the output file
//    String varName[] = {"YEAR",                                //0
//                        "MONTH",                               //1
//                        "DAY",                                 //2
//                        "HOUR",                                //3
//                        "MINUTE",                              //4
//                        "Current Speed (m/s)",                 //5
//                        "Current Direction (deg TN)",          //6
//                        "Water Temperature (deg C)",           //7
//                        "Vertical Velocity (m/s)",             //8
//                        "Current Speed filt. - 9h cut",        //9
//                        "Current Direc filt. - 9h cut",        //10
//                        "Current Speed filt. 14.5h cut",       //11
//                        "Current Direc filt. 14.5h cut",       //12
//                        "pH",                                  //13
//                        "Salinity (ppt)",                      //14
//                        "Disolved oxygen (mls/l)",             //15
//                        "Pressure"};                           //16
    String varName[] = {"YEAR",                                     //0
                        "MONTH",                                    //1
                        "DAY",                                      //2
                        "HOUR",                                     //3
                        "MINUTE",                                   //4
                        "Current Speed (m/s)",                      //5
                        "Current Direction (deg TN)",               //6
                        "Water Temperature (deg C)",                //7
                        "Vertical Velocity (m/s)",                  //8
                        "Current Speed filt. - 9h cut (m/s)",       //9
                        "Current Direc filt. - 9h cut (deg TN)",    //10
                        "Current Speed filt. 14.5h cut (m/s)",      //11
                        "Current Direc filt. 14.5h cut (deg TN)",   //12
                        "pH",                                       //13
                        "Salinity (ppt)",                           //14
                        "Disolved oxygen (ml/l)",                   //15
                        "Pressure"};                                //16 //ub05
    // the total length of the variables
    int varLength[] =  {4, 2, 2, 2, 2,
                        8, //"Current Speed",
                        8, //"Current Direction",
                        8, //"Water Temperature",
                        8, //"Vertical Velocity",
                        8, //"Current Speed filt. - 9h cut",
                        8, //"Current Direc filt. - 9h cut",
                        8, //"Current Speed filt. 14.5h cut"
                        8, //"Current Direc filt. 14.5h cut"
                        8, //"pH",
                        8, //"Salinity",
                        8, //"Disolved oxygen"
                        8};//"Pressure"                                 //ub05
    // the number of digits after the decimal point of the variables
    int varDecimal[] = {0, 0, 0, 0, 0,
                        2, //"Current Speed",
                        1, //"Current Direction",
                        2, //"Water Temperature",
                        2, //"Vertical Velocity",
                        2, //"Current Speed filt. - 9h cut",
                        1, //"Current Direc filt. - 9h cut",
                        2, //"Current Speed filt. 14.5h cut"
                        1, //"Current Direc filt. 14.5h cut"
                        2, //"pH",
                        2, //"Salinity",
                        2, //"Disolved oxygen"
                        2};//"Pressure"                                 //ub05
    // the default values of the variables
    float varDef[] =   {0f, 0f, 0f, 0f, 0f,
                        999.99f, //"Current Speed",
                        999.9f,  //"Current Direction",
                        99.99f,  //"Water Temperature",
                        99.99f,  //"Vertical Velocity",
                        99.99f,  //"Current Speed filt. - 9h cut",
                        999.9f,  //"Current Direc filt. - 9h cut",
                        99.99f,  //"Current Speed filt. 14.5h cut"
                        999.9f,  //"Current Direc filt. 14.5h cut"
                        99.99f,  //"pH",
                        99.99f,  //"Salinity",
                        99.99f,  //"Disolved oxygen"
                        99.99f};//"Pressure"                          //ub05
    // the identifier for the variables - used by the products
    String varIdentifier[] = {">cdtyy  <",  //"YEAR",
                        ">cdtmm  <",  //"MONTH",
                        ">cdtdd  <",  //"DAY",
                        ">ctmhh  <",  //"HOUR",
                        ">ctmmm  <",  //"MINUTE",
                        ">cs     <",  //"Current Speed",
                        ">cd     <",  //"Current Direction",
                        ">wt     <",  //"Water Temperature",
                        ">vv     <",  //"Vertical Velocity",
                        ">csf1   <",  //"Current Speed filt. - 9h cut",
                        ">cdf1   <",  //"Current Direc filt. - 9h cut",
                        ">csf2   <",  //"Current Speed filt. 14.5h cut"
                        ">cdf2   <",  //"Current Direc filt. 14.5h cut"
                        ">cwph   <",  //"pH",
                        ">cws    <",  //"Salinity",
                        ">cwdo   <",  //"Disolved oxygen"
                        ">press  <"}; //"Pressure"                      //ub05

    /**
     *  The main class
     */
    public ExtractCURDepthsData(String args[]) {

        // get 'commandline' arguments
        getArgParms(args);
        //format = new Integer(sFormat).intValue();

        // extract the user record from db
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        String userid = sessions[0].getUserid();

        // get the correct PATH NAME - used while debugging
        String rootPath = "";
        //if (ec.getHost().equals("morph")) {
        //if (ec.getHost().equals("fred")) {
        //    //rootPath = SadConstants.MORPH; //"/oracle/users/edm/data/currents/";
        //    rootPath = SadConstants.MORPH + userid + "/";
        //    if (dbg) System.out.println("<br> userid "+userid);
        //} else {
        //    rootPath = SadConstants.LOCAL; //"d:/myfiles/java/data/";
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            if ("0".equals(userType)) {  // from inventory              //ub01
                rootPath = sc.HOSTDIR + "inv_user/";                    //ub01
            } else {                                                    //ub01
                rootPath = sc.HOSTDIR + userid + "/";                   //ub01
            } // if ("0".equals(userType))                              //ub01
        } else {
            rootPath = sc.LOCALDIR;
        } // if host
        if (dbg) System.out.println("rootPath = " + rootPath);

        // build up the file name
        //ec.setFrmFiller("0");
        String fileName = rootPath + reqno + "_" + fileNameIn + ".cur";
        //fileName += ec.frm(depth[0].getInstrumentNumber(),5) + "_" +
        //    depth[0].getDeploymentNumber() + ".cur";
        //fileName += reqno + "_" + fileNameIn + ".cur";
        //ec.setFrmFiller(" ");
        if (dbg) System.out.println("fileName = " + fileName);

        // delete an old dummy file for Progress purposes ......
        String dummyFile = fileName + ".finished";
        ec.deleteOldFile(dummyFile);
        String dummyFile2 = fileName + ".queued";                       //ub02
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
        dummyFile2 = fileName + ".processing";
        ec.createNewFile(dummyFile2);

        // Open the data file.
        ec.deleteOldFile(fileName);
        file = ec.openFile(fileName , "rw");

        if (ec.getHost().startsWith(sc.HOST)) {
            //System.out.println("chmod 644 " + fileName);
            ec.submitJob("chmod 644 " + fileName);
        } //if (ec.getHost().startsWith(sc.HOST)) {

        // get the mooring code
        String where = CurMooring.SURVEY_ID + " = '"+ surveyId+"'";
        mooring = new CurMooring().get(where);
        if (dbg) System.out.println("<br>mooring.length = " + mooring.length);

        // check wether there was any data
        boolean surveyDataCheck = false;
        if (mooring.length < 1) {
            ec.writeFileLine(file, "No Data for survey_id = "+surveyId);
        } else {
            surveyDataCheck= true;
        } // if (depth.length < 1)

        // get all the depths for that survey ID
        where = CurDepth.MOORING_CODE + " = " + mooring[0].getCode("");
        if (!"".equals(depthCode)) {
            where += " and " + CurDepth.CODE + " = " + depthCode;
        } // if (!"".equals(depthCode))
        if (dbg) System.out.println("<br>CurDepth where = " + where);
        String order = CurDepth.DATE_TIME_START;
        CurDepth depth[] = new CurDepth().get(where, order);
        if (dbg) System.out.println("<br>depth.length = " + depth.length);

        // only do if there was data
        if (surveyDataCheck) {

            if (dbg) System.out.println ("<br>" + new Date());

            // Select Mooring details for Input Mooring code.
            //String mooringWhere = CurMooring.CODE +" = "+depth[0].getMooringCode();
            //CurMooring mooring[] = new CurMooring().get(mooringWhere);
            //if (dbg) System.out.println("<br>mooring "+mooring.length);

            //Get client records.
            EdmClient2 client[] = new EdmClient2().get(
                EdmClient2.CODE + " = "+mooring[0].getClientCode());
            if (dbg) System.out.println("<br>client.length = "+client.length);
            if (dbg) System.out.println("<br>clname = "+client[0].getName());

            //Get planam records.
            CurPlanam planam[] = new CurPlanam().get(
                CurPlanam.CODE + " = "+mooring[0].getPlanamCode());
            if (dbg) System.out.println("<br>planam.length = "+planam.length);

            //Get EdmInstrument records.
            EdmInstrument2 intru[] = new EdmInstrument2().get(
                EdmInstrument2.CODE + " = "+depth[0].getInstrumentNumber());

            // add up all the number of records
            int totalRecords = depth[0].getNumberOfRecords();
            for (int i = 1; i < depth.length; i++)
                totalRecords += depth[i].getNumberOfRecords();

            // write the cur file header
            ec.writeFileLine(file, "MOORING CODE : "+
                ec.frm(depth[0].getMooringCode(),5));
            ec.writeFileLine(file, "MOORING NAME : "+mooring[0].getStnnam());
            ec.writeFileLine(file, "MOORING DEPTH: "+ec.frm(mooring[0].getStndep(),7,2));
            ec.writeFileLine(file, "MOORING START: "+mooring[0].
                getDateTimeStart("").substring(0,16));
            ec.writeFileLine(file, "MOORING END  : "+mooring[0].
                getDateTimeEnd("").substring(0,16));
            ec.writeFileLine(file, "LATITUDE     : "+ec.frm(mooring[0].getLatitude(),8,4));
            ec.writeFileLine(file, "LONGITUDE    : "+ec.frm(mooring[0].getLongitude(),9,4));
            ec.writeFileLine(file, "NO OF DEPTHS : "+ec.frm(1,2));
            ec.writeFileLine(file, "CLIENT       : "+client[0].getName());
            ec.writeFileLine(file, "PLATFORM     : "+planam[0].getName());
            ec.writeFileLine(file, "INSTR. DEPTH : "+ec.frm(depth[0].getSpldep(),6,2));
            ec.writeFileLine(file, "INSTR. START : "+depth[0].
                getDateTimeStart("").substring(0,16));
            ec.writeFileLine(file, "INSTR. END   : "+depth[depth.length-1].
                getDateTimeEnd("").substring(0,16));
            ec.writeFileLine(file, "SAMPLE INTERV: "+ec.frm(depth[0].getTimeInterval(),4));
            ec.setFrmFiller("0");
            ec.writeFileLine(file, "INSTRUMENT NO: "+ec.frm(depth[0].getInstrumentNumber(),6));
            ec.setFrmFiller( " ");
            ec.writeFileLine(file, "DEPLOYMENT NO: "+depth[0].getDeploymentNumber());
            ec.writeFileLine(file, "INSTRUMENT TP: "+intru[0].getName());
            ec.writeFileLine(file, "NO OF RECORDS: "+ec.frm(totalRecords,6));
            ec.writeFileLine(file, "SURVEY ID    : "+mooring[0].getSurveyId());

            // Get Cur_Dep_notes - for SADCO only use the notes of first depth
            field = CurDepthNotes.NOTES;
            where = CurDepthNotes.DEPTH_CODE+ " = "+ depth[0].getCode();//depthCode;
            order = CurDepthNotes.SEQUENCE_NUMBER;
            CurDepthNotes depthnotes[] = new CurDepthNotes().get(field,where,order);
            ec.writeFileLine(file, "NOTES:");

            //loop though depthnotes records.
            for (int i=0; i < depthnotes.length;i++) {
                ec.writeFileLine(file, "-- "+ec.frm(depthnotes[i].getNotes().trim(),79));
            } //for (int i=0; i < depthnotes.length;i++) {

            // Write empty notes.
            for (int j=depthnotes.length; j < 5; j++) {
                ec.writeFileLine(file, "--");
            } //for (int j=depthnotes.length; j < 5; j++) {

            // Get Cur_Dep_quality notes.
            field = CurDepthQuality.NOTES;
            where = CurDepthQuality.DEPTH_CODE+ " = "+ depth[0].getCode();//depthCode;
            order = CurDepthQuality.SEQUENCE_NUMBER;
            CurDepthQuality depthquality[] = new CurDepthQuality().get(
                field,where,order);
            ec.writeFileLine(file, "QUALITY NOTES:");

            //loop though quality notes records.
            for (int i=0; i < depthquality.length;i++) {
                ec.writeFileLine(file, "-- "+ec.frm(depthquality[i].getNotes().trim(),79));
            } //for (int i=0; i < depthnotes.length;i++) {

            // write empty quality notes
            for (int j=depthquality.length; j < 3; j++) {
                ec.writeFileLine(file, "--");
            } //for (int j=depthquality.length; j < 3; j++) {

            // get the list of parameterList for Quality control
            CurDepthQcParmcodes parmCodes[] = new CurDepthQcParmcodes().get();  //ub05

            // get cur_depth_qc record
            field = "*";                                                        //ub05
            where = CurDepthQc.DEPTH_CODE + " = " + depth[0].getCode();//depthCode; //ub05
            order = CurDepthQc.PARAMETER_CODE;                                  //ub05
            CurDepthQc depthQc[] = new CurDepthQc().get(field,where,order);     //ub05
            if (depthQc.length > 0) {                                           //ub05
                                                                                //ub05
                // get the parameter codes                                      //ub05
                ec.writeFileLine(file, "QUALITY CONTROL CHECKS:");              //ub05
                ec.writeFileLine(file, "  " + ec.frm("Parameter",12) +          //ub05
                    "Broad_Range  Spikes  Sensor_Drift  Data_Gaps  " +          //ub05
                    "Leader/Trailer  Person              Date");                //ub05
                for (int i = 0; i < depthQc.length; i++) {                      //ub05
                    int code = depthQc[i].getParameterCode();                   //ub05
                    String name = parmCodes[code].getName();                    //ub05
                    ec.writeFileLine(file, "  " + ec.frm(name,15) +             //ub05
                        ec.frm(depthQc[i].getBroadrange(),13) +                 //ub05
                        ec.frm(depthQc[i].getSpikes(),8) +                      //ub05
                        ec.frm(depthQc[i].getSensordrift(),14) +                //ub05
                        ec.frm(depthQc[i].getGaps(),11) +                       //ub05
                        ec.frm(depthQc[i].getLeadertrailer(),13) +              //ub05
                        //ec.frm(" ",11) +                                      //ub05
                        ec.frm(depthQc[i].getPersonChecked(),20) +              //ub05
                        depthQc[i].getDateChecked("").substring(0,10));         //ub05
                } // for (int i = 0; i < depthQc.length; i++)                   //ub05
                ec.writeFileLine(file, "  (1 = failed quality control check)"); //ub05
            } // if (depthQc.length > 0)                                        //ub05

            // write the format part
            ec.writeFileLine(file, "FORMAT:");
            int startPos = 1;
            for (int i = 0; i < varName.length; i++) {
                // add common stuff
                String line = ":" +
                    ec.frm(startPos,4) + ":" +
                    ec.frm(varLength[i],2) + ": " +
                    //ec.frm(varName[i],29) + ":";
                    ec.frm(varName[i],38) + ":";
                // check if it has a default
                if (varDef[i] == 0) {
                    line += ec.frm(" ",21);
                } else {
                    line += ec.frm(varDef[i],10,varDecimal[i]) + ":" + ec.frm(" ",10);
                } // if (varDef[i] == 0)
                // add identifier
                line += varIdentifier[i];
                ec.writeFileLine(file, line);
                startPos += varLength[i];
                if (i < 5) startPos += 1;
            } // for (int i = 0; i < varName.length, i++)
            ec.writeFileLine(file, "DATA:");

            // extract all the data
            for (int k = 0; k < depth.length; k++) {

                if (dbg) System.out.println("");
                if (dbg) System.out.println("<br>k = " + k);

                // get start and end dates
                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
                GregorianCalendar calDate = new GregorianCalendar();
                calDate.setTime(depth[k].getDateTimeEnd());
                long dateTimeEnd = calDate.getTimeInMillis();
                calDate.setTime(depth[k].getDateTimeStart());
                long dateTimeStart = calDate.getTimeInMillis();

                if (dbg) System.out.println("dateTimeStart = " + dateTimeStart +
                    " * " + depth[k].getDateTimeStart(""));
                if (dbg) System.out.println("dateTimeEnd = " + dateTimeEnd +
                    " * " + depth[k].getDateTimeEnd(""));

                // do in loop from start till end date
                while (dateTimeStart <= dateTimeEnd) {

                    String datum = formatter.format(calDate.getTime());
                    where = CurData.DATETIME + " between '" + datum +
                        " 00:00:00' and '" + datum + " 23:59:59' and " +
                        CurData.DEPTH_CODE +" = "+depth[k].getCode();

                    if (dbg) System.out.println("where = " + where);

                    CurData curData[] = new CurData().get(where);
                    if (dbg) System.out.println("<br>curData.length = "+curData.length);

                    // loop through all records
                    for (int i=0; i < curData.length;i++) {

                        //Get cur_watphy details for matching cur_data details.
                        where = CurWatphy.DATA_CODE+" = "+curData[i].getCode();
                        CurWatphy watphy[] = new CurWatphy().get(where);
                        //if (dbg) System.out.println("<br>watphyl.length = "+watphy.length);

                        // check the watphy variables for null
                        if (watphy.length > 0 ) {
                            ph       = watphy[0].getPh();
                            salinity = watphy[0].getSalinity();
                            disOxy   = watphy[0].getDisOxy();
                        } else {
                            ph        = varDef[13];  //99.99f;
                            salinity  = varDef[14];  //99.99f;
                            disOxy    = varDef[15];  //99.99f;
                        } //if (Watphy.length > 0 ) {

                        if (dbg) System.out.println("<br>code = "+curData[i].getCode());
                        if (dbg) System.out.println("<br>depthCode = "+curData[i].getDepthCode());
                        if (dbg) System.out.println("<br>curData[i].getDatetime() = "+
                            curData[i].getDatetime(""));

                        String date = curData[i].getDatetime("").substring(0,10);
                        String time = curData[i].getDatetime("").substring(11,16);
                        if (dbg) System.out.println("<br>date/time = "+date + " " + time);

                        int index = 5;
                        ec.writeFileLine(file,
                            ec.frm(date,10)+" "+ec.frm(time,5)+
                            addVar2Line(curData[i].getSpeed(), index++) +
                            addVar2Line(curData[i].getDirection(), index++) +
                            addVar2Line(curData[i].getTemperature(), index++) +
                            addVar2Line(curData[i].getVertVelocity(), index++) +
                            addVar2Line(curData[i].getFSpeed9(), index++) +
                            addVar2Line(curData[i].getFDirection9(), index++) +
                            addVar2Line(curData[i].getFSpeed14(), index++)+
                            addVar2Line(curData[i].getFDirection14(), index++) +
                            addVar2Line(ph, index++) +
                            addVar2Line(salinity, index++) +
                            addVar2Line(disOxy, index++) +
                            addVar2Line(curData[i].getPressure(), index++));  //ub05

                    } // for (int i=0; i < curData.length;i++)

                    calDate.add(Calendar.DAY_OF_MONTH, 1);
                    dateTimeStart = calDate.getTimeInMillis();

                    if (dbg) System.out.println("dateTimeStart = " + dateTimeStart);
                    if (dbg) System.out.println("dateTimeEnd = " + dateTimeEnd);
                    if (dbg) System.out.println(formatter.format(calDate.getTime()));

                } // while (dateTImeStart <= dateTimeEnd)

/*

                //Get Cur_Data details.
                //field   = "max(code) as code ";
                //where   = CurData.DEPTH_CODE +" = "+depth[k].getCode();
                //order   = "";
                field   = CurData.CODE;
                where   = CurData.DEPTH_CODE +" = "+depth[k].getCode();
                order   = CurData.CODE + " DESC LIMIT 1";
                CurData data = new CurData();
                System.out.println("<br>" + thisClass + ": time before max = " + new Date());
                CurData curData[] = data.get(field,where,order);
                //curData = data.get(field,where,order);
                System.out.println("<br>" + thisClass + ": time after max = " + new Date());
                System.out.println("<br>" + thisClass + ": data.getSelStr() = "+data.getSelStr());
                int maxCode = curData[0].getCode();
                if (dbg) System.out.println("<br>maxCode = "+maxCode);
//                SELECT col FROM table ORDER BY col DESC LIMIT 1

//                field   = "min(code) as code";
//                where   = CurData.DEPTH_CODE +" = "+depth[k].getCode();
//                order   = "";
                order   = CurData.CODE + " ASC LIMIT 1";
//                CurData data = new CurData();
                System.out.println("<br>" + thisClass + ": time before min = " + new Date());
                //CurData curData[] = data.get(field,where,order);
                curData = data.get(field,where,order);
                System.out.println("<br>" + thisClass + ": time after min = " + new Date());
                System.out.println("<br>" + thisClass + ": data.getSelStr() = "+data.getSelStr());
                int minCode = curData[0].getCode();
                if (dbg) System.out.println("<br>minCode = "+minCode);

//                field   = "max(code) as code ";
//                System.out.println("<br>" + thisClass + ": time before max = " + new Date());
//                curData = data.get(field,where,order);
//                System.out.println("<br>" + thisClass + ": time after max = " + new Date());
//                System.out.println("<br>" + thisClass + ": data.getSelStr() = "+data.getSelStr());
//                int maxCode = curData[0].getCode();
//                if (dbg) System.out.println("<br>maxCode = "+maxCode);

                // Loop through the data_code...
                for (int l=minCode; l < maxCode; l+=SadConstants.INCR) {

                    // check for correct maximum data code.
                    int end = l+SadConstants.INCR-1;
                    if (end > maxCode) { end = maxCode; }

                    // construct the where clause
                    where = CurData.CODE + " between " + l + " and " + end +
                        " and " + CurData.DEPTH_CODE +" = "+depth[k].getCode();

                    if (dbg) System.out.println("");
                    if (dbg) System.out.println("<br>CurData where = " + where);

                    curData = new CurData().get(where);
                    if (dbg) System.out.println("<br>curData.length = "+curData.length);

                    // loop through all records
                    for (int i=0; i < curData.length;i++) {

                        //Get cur_watphy details for matching cur_data details.
                        where = CurWatphy.DATA_CODE+" = "+curData[i].getCode();
                        CurWatphy watphy[] = new CurWatphy().get(where);
                        //if (dbg) System.out.println("<br>watphyl.length = "+watphy.length);

                        // check the watphy variables for null
                        if (watphy.length > 0 ) {
                            ph       = watphy[0].getPh();
                            salinity = watphy[0].getSalinity();
                            disOxy   = watphy[0].getDisOxy();
                        } else {
                            ph        = varDef[13];  //99.99f;
                            salinity  = varDef[14];  //99.99f;
                            disOxy    = varDef[15];  //99.99f;
                        } //if (Watphy.length > 0 ) {

                        if (dbg) System.out.println("<br>code = "+curData[i].getCode());
                        if (dbg) System.out.println("<br>depthCode = "+curData[i].getDepthCode());
                        if (dbg) System.out.println("<br>curData[i].getDatetime() = "+
                            curData[i].getDatetime(""));

                        String date = curData[i].getDatetime("").substring(0,10);
                        String time = curData[i].getDatetime("").substring(11,16);
                        if (dbg) System.out.println("<br>date/time = "+date + " " + time);

                        int index = 5;
                        ec.writeFileLine(file,
                            ec.frm(date,10)+" "+ec.frm(time,5)+
                            addVar2Line(curData[i].getSpeed(), index++) +
                            addVar2Line(curData[i].getDirection(), index++) +
                            addVar2Line(curData[i].getTemperature(), index++) +
                            addVar2Line(curData[i].getVertVelocity(), index++) +
                            addVar2Line(curData[i].getFSpeed9(), index++) +
                            addVar2Line(curData[i].getFDirection9(), index++) +
                            addVar2Line(curData[i].getFSpeed14(), index++)+
                            addVar2Line(curData[i].getFDirection14(), index++) +
                            addVar2Line(ph, index++) +
                            addVar2Line(salinity, index++) +
                            addVar2Line(disOxy, index++));

                    } // for (int i=0; i < curData.length;i++)

                } // for (int l=minCode; l < maxCode; l+=SadConstants.INCR)
*/

            } // for (int k = 0; k < depth.length; k++)
            ec.writeFileLine(file, "END:");

            // close file
            ec.closeFile(file);

        } // if ( surveyDataCheck

        Timestamp endJDate = new Timestamp(new Date().getTime());       //ub04
        updateLogUsers(log, ec.timeDiff(endJDate, endQDate),            //ub04
            mooring[0], depth[0]);                                      //ub04

        // delete .progressing file ......
        ec.deleteOldFile(dummyFile2);
        // create the finished file ......
        ec.createNewFile(dummyFile);

        // dequeue extraction                                           //ub02
        if ("0".equals(userType)) {  // from inventory                  //ub03
            if (!dbgQ) {
                queue.deQueue();                                        //ub02
            } // if (!dbgQ)
        } // if ("0".equals(userType))                                  //ub03

    } // constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    private void getArgParms(String args[])   {
        //numdepths       = Integer.parseInt(ec.getArgument(args,SadConstants.NUMDEPTHS));
        sessionCode = ec.getArgument(args, SadConstants.SESSIONCODE);
        surveyId    = ec.getArgument(args,SadConstants.SURVEYID);
        fileNameIn  = ec.getArgument(args,SadConstants.FILENAME);
        reqno       = ec.getArgument(args,SadConstants.REQNUMBER);
        depthCode   = ec.getArgument(args,SadConstants.DEPTHCODE);
        userType    = ec.getArgument(args,SadConstants.USERTYPE);       //ub01

        if (dbg) System.out.println("<br>sessionCode = "+sessionCode);
        if (dbg) System.out.println("<br>surveyId = "+surveyId);
        if (dbg) System.out.println("<br>fileNameIn = "+fileNameIn);
        if (dbg) System.out.println("<br>reqno = "+reqno);


        //for (int d =0; d < numdepths;d++) {
        //    depthCode[d]  = ec.getArgument(args,SadConstants.DEPTHCODE+(d+1));
        //} //for (int d =0; d < numdepths;d++) {
    }   //  public void getArgParms()

    /**
     * Generate the text for adding a variable to the output line
     * @param  value    The value of the variable   (float)
     * @param  index    The index of the variable in the varName array
     * @returns text    The generate text
     */
    private String addVar2Line (float value, int index) {
        String text =
            ec.frm((value == Tables.FLOATNULL ? varDef[index] :
                value), varLength[index],varDecimal[index]);
        return text;
    } // addVar2Line


    /**                                                                 //ub04
     * Create the LOG_USERS table entry for this extraction.            //ub04
     */                                                                 //ub04
    LogUsers createLogUsers(String userid, Timestamp start, String timeDiff) {//ub04
        LogUsers logs = new LogUsers();                                 //ub04
        logs.setUserid(userid);                                         //ub04
        logs.setDateTime(start);                                        //ub04
        logs.setDatabase("CURRENT");                                    //ub04
        logs.setDiscipline("curdep");                                   //ub04
        logs.setProduct("Extraction");                                  //ub04
        logs.setFileName(fileNameIn);                                   //ub04
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


    /**                                                                 //ub04
     * Update the LOG_USERS table for this extraction.                  //ub04
     */                                                                 //ub04
    void updateLogUsers(LogUsers logs, String timeDiff,                 //ub04
            CurMooring mooring, CurDepth depth) {                       //ub04
        // define the value that should be updated                      //ub04
        LogUsers updateLogs = new LogUsers();                           //ub04
        updateLogs.setLatitudeNorth(mooring.getLatitude());             //ub04
        updateLogs.setLatitudeSouth(mooring.getLatitude());             //ub04
        updateLogs.setLongitudeWest(mooring.getLongitude());            //ub04
        updateLogs.setLongitudeEast(mooring.getLongitude());            //ub04
        updateLogs.setDateStart(depth.getDateTimeStart("").substring(0,16));//ub04
        updateLogs.setDateEnd(depth.getDateTimeEnd("").substring(0,16));//ub04
        updateLogs.setNumRecords(depth.getNumberOfRecords());           //ub04
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
     * Update the LOG_USERS table for this extraction.
     */
/*
    void updateLogUsers(String userid, String timeDiff,
            CurMooring mooring, CurDepth depth) {
        LogUsers logs = new LogUsers();
        logs.setUserid(userid);
        logs.setDateTime(new Timestamp(new java.util.Date().getTime()));
        logs.setDatabase("CURRENT");
        logs.setDiscipline("curdep");
        logs.setProduct("Extraction");
        logs.setFileName(fileNameIn);
        logs.setLatitudeNorth(mooring.getLatitude());
        logs.setLatitudeSouth(mooring.getLatitude());
        logs.setLongitudeWest(mooring.getLongitude());
        logs.setLongitudeEast(mooring.getLongitude());
        logs.setDateStart(depth.getDateTimeStart("").substring(0,16));
        logs.setDateEnd(depth.getDateTimeEnd("").substring(0,16));
        logs.setSurveyId(surveyId);
        logs.setNumRecords(depth.getNumberOfRecords());
        logs.setJobDuration(timeDiff);
        boolean success = logs.put();

    } // updateLogUsers
*/

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {
        try {
            ExtractCURDepthsData local= new ExtractCURDepthsData(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, "ExtractCURDepthsData", "Constructor", "");
        } // try-catch

        // close the connection to the database
        common2.DbAccessC.close();

    } //public static void main(String args[]) {

} // class ExtractCURDepthsData