package sadco;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.sql.SQLException;

/**
 * This class splits cur_depth records that are longer than 1 year into <br>
 * one for each calendar year.
 *                                                                      <br>
 * It's parameters are the following:                                   <br>
 *                                                                      <br>
 * SadcoMRN                                                             <br>
 * screen.equals("admin")                                               <br>
 * version.equals("") (not in program yet)                              <br>
 *                                                                      <br>
 * @author 2009/06/04 - SIT Group.                                      <br>
 * @version                                                             <br>
 * 2009/06/04 - Ursula von St Ange - created class                      <br>
 */
public class AdminSplitCurDepth {

    boolean dbg = false;
    //boolean dbg = true;
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommonPC ec = new common.edmCommonPC();
    sadco.SadConstants sc = new sadco.SadConstants();

    final static long MILLISECS_PER_DAY = 1000 * 60 * 60 * 24;
    final static int MAX_DELTA = 366;



    //

    AdminSplitCurDepth(String args[]) throws Exception {

        // +------------------------------------------------------------+
        // | Get input arguments                                        |
        // +------------------------------------------------------------+
        String surveyId = "2008/0642";
        if (args.length > 0) surveyId = args[0];

        // just in case the argument was 'psurveyid=<surveyID>'
        int dotPos = surveyId.indexOf('=');
        if (dotPos > -1) {
            surveyId = surveyId.substring(dotPos+1);
        } // if (dotPos > -1)
        System.out.println("\n\nConstructor: surveyId = " + surveyId);

        // get the mooring code
        CurMooring[] mooring = getMooringRecord(surveyId);

        // get all the depths
        CurDepth[] depths = getDepthRecords(mooring[0].getCode());
        int depthsCount = depths.length;
        System.out.println("Constructor: depthsCount = " + depthsCount);

        // loop through all depth records
        for (int i = 0; i < depthsCount; i++) {
        //for (int i = 0; i < 5; i++) {

            System.out.println("\nConstructor: original depth = " + depths[i]);
            // keep depth code, start and end dates for later use
            int depthCode = depths[i].getCode();
            Date startDate = depths[i].getDateTimeStart();
            Date endDate = depths[i].getDateTimeEnd();

            // get the period length in days
            int deltaDays = getDaysDiff(startDate, endDate);
            System.out.println("\nConstructor: " +
                "depthCode, startDate, endDate, deltaDays = " +
                depthCode + " " + startDate + " " +
                endDate + " " + deltaDays);

            // get the start and end year
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(startDate);
            int startYear = cal.get(Calendar.YEAR);
            cal.setTime(endDate);
            int endYear = cal.get(Calendar.YEAR);
            if (dbg) System.out.println("Constructor: start, end year = " +
                startYear + " " + endYear);

            // check whether there is a depth_qc record
            CurDepthQc[] depthsQC = getDepthsQC(depthCode);
            int depthQCCount = depthsQC.length;
            System.out.println("Constructor: depthQCCount = " + depthQCCount);

            // if longer than 1 year, split
            if ((deltaDays > MAX_DELTA) && (startYear < endYear)) {

                for (int year = startYear+1; year <= endYear; year++) {

                    if (dbg) System.out.println("");

                    // create a new depth record with new dates
                    int newDepthCode = createNewDepth(depths[i], year, depthCode);

                    if (depthQCCount > 0) {
                        createNewDepthQC(depthsQC, newDepthCode);
                    } // if (depthQCCount > 0)

                    // update the data records for the new depth
                    updateDataRecords(depthCode, newDepthCode, year);

                } // for (int year = startYear+1; year <= endYear; year++)

                // update the original depth record for new end date
                if (dbg) System.out.println("");
                updateDepthRecord(depthCode, startYear);

            } // if ((deltaDays > MAX_DELTA) && (startYear < endYear))

        } // for (i = 0; i < depthsCount; i++)

    } // AdminSplitCurDepth constructor


    /**
     * Get mooring records from database for the surveyId
     * @param surveyId : argument value from url (string)
     * @return array of mooring records
     */
    CurMooring[] getMooringRecord(String surveyId) {
        //if (dbg) System.out.println("<br>getMooringRecord");

        // construct fields, and where and order by clauses
        String fields = CurMooring.CODE;
        String where = CurMooring.SURVEY_ID +"='"+ surveyId +"'";
        String order = CurMooring.CODE;

        CurMooring mooring = new CurMooring();
        CurMooring[] moorings = mooring.get(fields, where, order);
        if (dbg) System.out.println("getMooringRecord: sql = " + mooring.getSelStr());

        return moorings;
    } // getMooringRecord


    /**
     * Get depth records from database for the mooring code
     * @param mooringCode : mooring code
     * @return array of depth records
     */
    CurDepth[] getDepthRecords(int mooringCode) {
        //if (dbg) System.out.println("<br>getDepthRecords");

        // construct fields, and where and order by clauses
        String fields = "*";
        String where = CurDepth.MOORING_CODE +"="+ mooringCode;
        String order = CurDepth.CODE;

        CurDepth[] depths = new CurDepth().get(fields, where, order);

        return depths;
    } // getDepthRecords


    /**
     * get the number od days between two dates
     * @param   startDate   the start date of the period
     * @param   endDate     the end date of the period
     * @return  the difference between the two dates in days
     */
    int getDaysDiff(Date startDate, Date endDate) {

        // Get msec from each, and subtract.
        long diff = endDate.getTime() - startDate.getTime();
        return (int) (diff / MILLISECS_PER_DAY);

    } // getDaysDiff


    /**
     * Get depthQC records from database for the depth code
     * @param depthCode : the depth code
     * @return array of depthQC records
     */
    CurDepthQc[] getDepthsQC(int depthCode) {

        // construct fields, and where and order by clauses
        String fields = "*";
        String where = CurDepthQc.DEPTH_CODE +"="+ depthCode;
        String order = CurDepthQc.DEPTH_CODE;

        CurDepthQc[] depthsQC = new CurDepthQc().get(fields, where, order);

        return depthsQC;

    } // getDepthsQC


    /** create a new depth record with the same fields as the current
     * depth record, but with new start and end times
     * @param   depth   the current depth record
     * @return  the new record's depthCode
     */
    int createNewDepth(CurDepth depth, int year, int depthCode) {

        // get the minimum and maximum dates for the year from the data
        Date minDate = getMinMaxDate("min", year, depthCode);
        Date maxDate = getMinMaxDate("max", year, depthCode);
        if (dbg) System.out.println("createNewDepth: dates = " + minDate + " " +
            maxDate);

        // get the number of records
        String where = CurData.DEPTH_CODE +" = "+ depthCode+" and " +
            "to_char("+CurData.DATETIME+",'yyyy') = "+year;
        int numRecs = new CurData().getRecCnt(where);
        if (dbg) System.out.println("createNewDepth: number of Records = " + numRecs);

        // create new record, and transfer old values
        CurDepth newDepth = new CurDepth();
        newDepth.setMooringCode(depth.getMooringCode());
        newDepth.setSpldep(depth.getSpldep());
        newDepth.setInstrumentNumber(depth.getInstrumentNumber());
        newDepth.setDeploymentNumber(depth.getDeploymentNumber());
        newDepth.setTimeInterval(depth.getTimeInterval());
        newDepth.setPasskey(depth.getPasskey());
        newDepth.setDateLoaded(depth.getDateLoaded());
        newDepth.setParameters(depth.getParameters());

        // set new values
        newDepth.setDateTimeStart(minDate);
        newDepth.setDateTimeEnd(maxDate);
        newDepth.setNumberOfRecords(numRecs);

        // insert record, and get new depth code
        newDepth.put();
        int newDepthCode = newDepth.getCode();
//        int newDepthCode = -1;

        if (dbg) System.out.println("createNewDepth: original depth: " + depth);
        if (dbg) System.out.println("createNewDepth: new depth: " + newDepth);
        System.out.println("createNewDepth: depth inserted: " + newDepth);
        if (dbg) System.out.println("createNewDepth: sql = " + newDepth.getInsStr());

        return newDepthCode;

    } // createNewDepth


    /** create a new depth record with the same fields as the current
     * depth record, but with new start and end times
     * @param   depth   the current depth record
     * @return  the new record's depthCode
     */
    void createNewDepthQC(CurDepthQc depthQC[], int newDepthCode) {

        for (int i = 0; i < depthQC.length; i++) {

            // create new record, and transfer old values
            CurDepthQc newDepthQC = new CurDepthQc();
            newDepthQC.setDepthCode(newDepthCode);
            newDepthQC.setParameterCode(depthQC[i].getParameterCode());
            newDepthQC.setPersonChecked(depthQC[i].getPersonChecked());
            newDepthQC.setDateChecked(depthQC[i].getDateChecked());
            newDepthQC.setBroadrange(depthQC[i].getBroadrange());
            newDepthQC.setSpikes(depthQC[i].getSpikes());
            newDepthQC.setSensordrift(depthQC[i].getSensordrift());
            newDepthQC.setGaps(depthQC[i].getGaps());
            newDepthQC.setLeadertrailer(depthQC[i].getLeadertrailer());

            // insert record, and get new depth code
            newDepthQC.put();

            System.out.println("createNewDepthQC: original depthQC: " + depthQC[i]);
            if (dbg) System.out.println("createNewDepthQC: new depthQC: " + newDepthQC);
            System.out.println("createNewDepthQC: depthQC inserted: " + newDepthQC);
            if (dbg) System.out.println("createNewDepthQC: sql = " + newDepthQC.getInsStr());

        } // for (int i = 0; i < depthQC.length, i++)


    } // createNewDepthQC


    /**
     * Get the minimum or maximum date from cur_data for a particular year
     * @param   minMax  min / max
     * @param   year    the year of the date
     * @return  the minimum/maximum date
     */
    Date getMinMaxDate(String minMax, int year, int depthCode) {

        String fields = minMax+"("+CurData.DATETIME+") "+CurData.DATETIME;
        String where = CurData.DEPTH_CODE +" = "+ depthCode+" and " +
            "to_char("+CurData.DATETIME+",'yyyy') = "+year;
        String order = CurData.DATETIME;

        CurData data = new CurData();
        CurData[] datas = null;
        datas = data.get(fields, where, order);
        Date datum = datas[0].getDatetime();
        //if (dbg) System.out.println("getMinMaxDate: sql = " + data.getSelStr());
        //if (dbg) System.out.println("getMinMaxDate: datum = " + datum);
        return datum;

    } // getMinMaxDate


    /**
     * Update the cur_data records for the new depthCode
     * @param   depthCode       code of the old cur_depth record
     * @param   newDepthCode    code of the new cur_depth record
     * @param   year            the year of the depth record
     */
    void updateDataRecords(int depthCode, int newDepthCode, int year) {

        CurData updData = new CurData();
        updData.setDepthCode(newDepthCode);

        CurData whereData = new CurData();
        String where = CurData.DEPTH_CODE +"="+ depthCode+" and " +
            "to_char("+CurData.DATETIME+",'yyyy') = "+year;
        whereData.upd(updData, where);
        if (dbg) System.out.println("updateDataRecords: where = " + where);
        System.out.println("updateDataRecords: data updated: " + updData);
        if (dbg) System.out.println("updateDataRecords: sql = " + whereData.getUpdStr());
        System.out.println("updateDataRecords: numRecs updated = " +
            whereData.getNumRecords());

    } // updateDataRecords


    /**
     * update the original cur_depth record with a new end date
     * @param   depthCode   the depthCode of the record
     */
    void updateDepthRecord(int depthCode, int year) {

        // get the number of records
        String where = CurData.DEPTH_CODE +" = "+ depthCode+" and " +
            "to_char("+CurData.DATETIME+",'yyyy') = "+year;
        int numRecs = new CurData().getRecCnt(where);
        if (dbg) System.out.println("updateDepthRecord: number of Records = " + numRecs);

        // set the record with the new max date
        CurDepth updDepth = new CurDepth();
        updDepth.setDateTimeEnd(getMinMaxDate("max", year, depthCode));
        updDepth.setNumberOfRecords(numRecs);

        CurDepth whereDepth = new CurDepth();
        whereDepth.setCode(depthCode);
        whereDepth.upd(updDepth);
        if (dbg) System.out.println("updateDepthRecord: depth updated: " + updDepth);
        if (dbg) System.out.println("updateDepthRecord: whereDepth = " + whereDepth);
        if (dbg) System.out.println("updateDepthRecord: sql = " + whereDepth.getUpdStr());

   } // updateDepthRecord


    /**
     * The main procedure for processing in standalone mode
     * @param   args[]  array of command-line / URL arguments
     */
    public static void main(String args[]) {
        try {
            AdminSplitCurDepth local = new AdminSplitCurDepth(args);
        } catch (Exception e) {
            ec.processError(e, "AdminSplitCurDepth", "constructor", "");
        } // try-catch
        common2.DbAccessC.close();
    } // main


} // public class AdminSplitCurDepth
