//+------------------------------------------------+----------------+--------+
//| CLASS: ... ExtractCURAllDepthsData             | JAVA PROGRAM   | 010326 |
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
//| 020803  SIT Group            create class                                |
//+--------------------------------------------------------------------------+

//package waves;
package sadco;

import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExtractCURAllDepthsData {

    //boolean dbg = false;
    boolean dbg = true;

    // a set of common general purpose routines
    static common.edmCommonPC ec = new common.edmCommonPC();

    SadConstants sc = new SadConstants();

    // parameters from html page
    String surveyId          = "";
    String reqno             = "";
    String sessionCode       = "";
    int    mooringCode       = 0;
    String depthCode[]       = new String[50];

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
                        "Disolved oxygen (ml/l)"};                  //15
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
                        8};//"Pressure"
    // the number of digits after the decimal point of the variables
    int varDecimal[] = {0, 0, 0, 0, 0,
                        3, //"Current Speed",
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
                        2};//"Pressure oxygen"
    // the default values of the variables
    float varDef[] =   {0f, 0f, 0f, 0f, 0f,
                        999.999f,//"Current Speed",
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
                        9999.99f};//"Pressure"
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
                        ">pres   <"}; //"Pressure"

    /**
     *  The main class
     */
    public ExtractCURAllDepthsData(String args[]) {

        // get 'commandline' arguments
        getArgParms(args);

        // extract the user record from db
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        String userid = sessions[0].getUserid();
        if (dbg) System.out.println("userid = " + userid);

        // get the correct PATH NAME - used while debugging
        String rootPath = "";
        //if (ec.getHost().startsWith("morph")) {
        //    rootPath = SadConstants.MORPH; //"/oracle/users/edm/data/currents/";
        //} else {
        //    rootPath = SadConstants.LOCAL; //"d:/myfiles/java/data/";
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIR;
        } else {
            rootPath = sc.LOCALDIR;
        } // if host

        // Select Mooring details for Input Mooring code.
        String mooringWhere = CurMooring.SURVEY_ID +" = '"+surveyId+"'";
        CurMooring mooring[] = new CurMooring().get(mooringWhere);
        if (dbg) System.out.println(" mooring "+mooring.length);
        mooringCode = mooring[0].getCode();
        //format = new Integer(sFormat).intValue();
        if (dbg) System.out.println(" mooringCode = "+mooringCode);
        if (dbg) System.out.println ("<br>" + new java.util.Date());

        // get cur_depth details.
        CurDepth depth[] = new CurDepth().get(
            //CurDepth.CODE +" = '"+mooring[0].getCode()+"'");
            CurDepth.MOORING_CODE+" = "+mooringCode, CurDepth.SPLDEP);


        //Get client records.
        EdmClient client[] = new EdmClient().get(
            EdmClient.CODE + " = "+mooring[0].getClientCode());
        if (dbg) System.out.println(" client "+client.length);
        if (dbg) System.out.println("<br>clname "+client[0].getName());

        //Get planam records.
        CurPlanam planam[] = new CurPlanam().get(
            CurPlanam.CODE + " = "+mooring[0].getPlanamCode());
        if (dbg) System.out.println(" planam "+planam.length);

        // loop through all the depths, but only process the non-null ones
        for (int i = 0; i < depth.length; i++) {

//            // get total number recs in cur_depth.
//            CurDepth depth[] = new CurDepth().get(
//                CurDepth.CODE + " = "+ depthCode[c] );
//                //CurDepth.MOORING_CODE + " = "+mooringCode);
            if (dbg) System.out.println("<br>stnCnt "+depth.length);

            // build up the file name
            ec.setFrmFiller("0");
            String fileName = rootPath;
            if (!"".equals(reqno)) {
                fileName += reqno + "_";
            } // if (!"".equals(initials))
//            fileName += ec.frm(depth[i].getInstrumentNumber(),5) + "_" +
//                depth[i].getDeploymentNumber() + ".cur";
            fileName += ec.frm(mooringCode,4) + "_" +
                ec.frm((int)depth[i].getSpldep(),4) + "m_" +
                ec.frm(depth[i].getCode(),4) + ".cur";
            ec.setFrmFiller(" ");
            if (dbg) System.out.println("fileName = " + fileName);

            // delete an old dummy file for Progress purposes ......
            String dummyFile = fileName + ".finished";
            ec.deleteOldFile(dummyFile);

            // create .progressing file for Progress purposes ......
            String dummyFile2 = fileName + ".processing";
            ec.createNewFile(dummyFile2);

            // Open the data file.
            ec.deleteOldFile(fileName);
            ec.openFile(fileName , "rw");

            if (ec.getHost().startsWith(sc.HOST)) {
                //System.out.println("chmod 644 " + rootPath+fileName);
                ec.submitJob("chmod 644 " + fileName);
            } //if (ec.getHost().startsWith(sc.HOST)) {

            //Get EdmInstrument records.
            EdmInstrument intru[] = new EdmInstrument().get(
                EdmInstrument.CODE + " = "+depth[i].getInstrumentNumber());

            ec.writeFileLine(file, "MOORING CODE : "+ec.frm(mooringCode,5));
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
            ec.writeFileLine(file, "INSTR. DEPTH : "+ec.frm(depth[i].getSpldep(),6,2));
            ec.writeFileLine(file, "INSTR. START : "+depth[i].
                getDateTimeStart("").substring(0,16));
            ec.writeFileLine(file, "INSTR. END   : "+depth[i].
                getDateTimeEnd("").substring(0,16));
            ec.writeFileLine(file, "SAMPLE INTERV: "+ec.frm(depth[i].getTimeInterval(),4));
            ec.setFrmFiller("0");
            ec.writeFileLine(file, "INSTRUMENT NO: "+ec.frm(depth[i].getInstrumentNumber(),6));
            ec.setFrmFiller( " ");
            ec.writeFileLine(file, "DEPLOYMENT NO: "+depth[i].getDeploymentNumber());
            ec.writeFileLine(file, "INSTRUMENT TP: "+intru[0].getName());
            ec.writeFileLine(file, "NO OF RECORDS: "+ec.frm(depth[i].getNumberOfRecords(),6));

            // Get Cur_Dep_notes.
            field = CurDepthNotes.NOTES;
            where = CurDepthNotes.DEPTH_CODE+ " = "+ depth[i].getCode();//depthCode;
            order = CurDepthNotes.SEQUENCE_NUMBER;
            CurDepthNotes depthnotes[] = new CurDepthNotes().get(field,where,order);
            ec.writeFileLine(file, "NOTES:");

            //loop though depthnotes records.
            for (int j=0; j < depthnotes.length; j++) {
                ec.writeFileLine(file, "--"+ec.frm(depthnotes[j].getNotes(),79));
            } // for (int j=0; j < depthnotes.length; j++)

            // Write empty notes.
            for (int j=depthnotes.length; j < 5; j++) {
                ec.writeFileLine(file, "--");
            } //for (int j=depthnotes.length; j < 5; j++) {

            // Get Cur_Dep_quality notes.
            field = CurDepthQuality.NOTES;
            where = CurDepthQuality.DEPTH_CODE+ " = "+ depth[i].getCode();//depthCode;
            order = CurDepthQuality.SEQUENCE_NUMBER;
            CurDepthQuality depthquality[] = new CurDepthQuality().get(
                field,where,order);
            ec.writeFileLine(file, "QUALITY NOTES:");

            //loop though quality notes records.
            for (int j=0; j < depthquality.length; j++) {
                ec.writeFileLine(file, "--"+ec.frm(depthquality[j].getNotes(),79));
            } // for (int j=0; j < depthquality.length; j++)

            // write empty quality notes
            for (int j=depthquality.length; j < 3; j++) {
                ec.writeFileLine(file, "--");
            } //for (int j=depthquality.length; j < 3; j++) {

            // write the format part
            ec.writeFileLine(file, "FORMAT:");
            int startPos = 1;
            for (int j = 0; j < varName.length; j++) {
                // add common stuff
                String line = ":" +
                    ec.frm(startPos,4) + ":" +
                    ec.frm(varLength[j],2) + ": " +
                    //ec.frm(varName[i],29) + ":";
                    ec.frm(varName[i],38) + ":";
                // check if it has a default
                if (varDef[j] == 0) {
                    line += ec.frm(" ",21);
                } else {
                    line += ec.frm(varDef[j],10,varDecimal[j]) + ":" + ec.frm(" ",10);
                } // if (varDef[j] == 0)
                // add identifier
                line += varIdentifier[j];
                ec.writeFileLine(file, line);
                startPos += varLength[j];
                if (j < 4) startPos += 1;
            } // for (int i = 0; i < varName.length, i++)
            ec.writeFileLine(file, "DATA:");

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

                // loop through all records
                for (int j=0; j < curData.length;j++) {

                    //Get cur_watphy details for matching cur_data details.
                    where = CurWatphy.DATA_CODE+" = "+curData[j].getCode();
                    CurWatphy watphy[] = new CurWatphy().get(where);
                    if (dbg) System.out.println("watphy "+watphy.length);

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

                    if (dbg) System.out.println(" code "+curData[j].getCode());
                    if (dbg) System.out.println(" depthCode "+curData[j].getDepthCode());
                    if (dbg) System.out.println(" curData[j].getDatetime() "+
                        curData[j].getDatetime(""));

                    String date = curData[j].getDatetime("").substring(0,10);
                    if (dbg) System.out.println(" date "+date);

                    String time = curData[j].getDatetime("").substring(11,16);
                    if (dbg) System.out.println(" time "+time);

                    int index = 5;
                    ec.writeFileLine(file,
                        ec.frm(date,10)+" "+ec.frm(time,5)+
                        addVar2Line(curData[j].getSpeed(), index++) +
                        addVar2Line(curData[j].getDirection(), index++) +
                        addVar2Line(curData[j].getTemperature(), index++) +
                        addVar2Line(curData[j].getVertVelocity(), index++) +
                        addVar2Line(curData[j].getFSpeed9(), index++) +
                        addVar2Line(curData[j].getFDirection9(), index++) +
                        addVar2Line(curData[j].getFSpeed14(), index++)+
                        addVar2Line(curData[j].getFDirection14(), index++) +
                        addVar2Line(ph, index++) +
                        addVar2Line(salinity, index++) +
                        addVar2Line(disOxy, index++) +
                        addVar2Line(curData[j].getPressure(), index++));
                } //for (int i=0; i < CurData.length;i++) {

                calDate.add(Calendar.DAY_OF_MONTH, 1);
                dateTimeStart = calDate.getTimeInMillis();

                if (dbg) System.out.println("dateTimeStart = " + dateTimeStart);
                if (dbg) System.out.println("dateTimeEnd = " + dateTimeEnd);
                if (dbg) System.out.println(formatter.format(calDate.getTime()));

            } // while (dateTImeStart <= dateTimeEnd)


/*
            //Get Cur_Data details.
            field   = "min(code) as code";
            where   = CurData.DEPTH_CODE +" = "+depth[i].getCode();
            order   = "";
            CurData curData[] = new CurData().get(field,where,order);
            int minCode = curData[0].getCode();

            field   = "max(code) as code ";
            curData = new CurData().get(field,where,order);
            int maxCode = curData[0].getCode();

            if (dbg) System.out.println("minCode "+minCode);
            if (dbg) System.out.println("maxCode "+maxCode);

            // Loop through the data_code...
            for (int l=minCode; l < maxCode; l+=SadConstants.INCR) {

                // check for correct maximum data code.
                int end = l+SadConstants.INCR-1;
                if (end > maxCode) { end = maxCode; }

                // construct the where clause
                where = CurData.CODE + " between " + l + " and " + end +
                    " and " + CurData.DEPTH_CODE + " = " + depth[i].getCode();
                if (dbg) System.out.println("where = " + where);

                curData = new CurData().get(where);
                if (dbg) System.out.println("curData "+curData.length);

                // loop through all records
                for (int j=0; j < curData.length;j++) {

                    //Get cur_watphy details for matching cur_data details.
                    where = CurWatphy.DATA_CODE+" = "+curData[j].getCode();
                    CurWatphy watphy[] = new CurWatphy().get(where);
                    if (dbg) System.out.println("watphy "+watphy.length);

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

                    if (dbg) System.out.println(" code "+curData[j].getCode());
                    if (dbg) System.out.println(" depthCode "+curData[j].getDepthCode());
                    if (dbg) System.out.println(" curData[j].getDatetime() "+
                        curData[j].getDatetime(""));

                    String date = curData[j].getDatetime("").substring(0,10);
                    if (dbg) System.out.println(" date "+date);

                    String time = curData[j].getDatetime("").substring(11,16);
                    if (dbg) System.out.println(" time "+time);

                    int index = 5;
                    ec.writeFileLine(file,
                        ec.frm(date,10)+" "+ec.frm(time,5)+
                        addVar2Line(curData[j].getSpeed(), index++) +
                        addVar2Line(curData[j].getDirection(), index++) +
                        addVar2Line(curData[j].getTemperature(), index++) +
                        addVar2Line(curData[j].getVertVelocity(), index++) +
                        addVar2Line(curData[j].getFSpeed9(), index++) +
                        addVar2Line(curData[j].getFDirection9(), index++) +
                        addVar2Line(curData[j].getFSpeed14(), index++)+
                        addVar2Line(curData[j].getFDirection14(), index++) +
                        addVar2Line(ph, index++) +
                        addVar2Line(salinity, index++) +
                        addVar2Line(disOxy, index++) +
                        addVar2Line(curData[j].getPressure(), index++));
                } //for (int i=0; i < CurData.length;i++) {

            } //for (int j=minCode; j < maxCode; j++) {

*/

            ec.writeFileLine(file, "END:");

            // close file
            ec.closeFile(file);

            // create the dummy file for Progress purposes ......
            ec.createNewFile(dummyFile);
            // delete .progressing file for Progress purposes ......
            ec.deleteOldFile(dummyFile2);

         } // for (int j = 0; j < depth.length;j++)
     } // constructor

    /**
     * Get the parameters from the arguments in the URL
     * @param  args[]       (String)
     */
    private void getArgParms(String args[])   {
        sessionCode = ec.getArgument(args, SadConstants.SESSIONCODE);
        surveyId    = ec.getArgument(args,SadConstants.SURVEYID);
        reqno       = ec.getArgument(args,SadConstants.REQNUMBER);

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

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {
        try {
            ExtractCURAllDepthsData local= new ExtractCURAllDepthsData(args);
        } catch(Exception e) {
            ec.processError(e, "ExtractCURAllDepthsData", "Constructor", "");
        } // try-catch

        // close the connection to the database
        common2.DbAccessC.close();

    } // main

} // class ExtractCURAllDepthsData