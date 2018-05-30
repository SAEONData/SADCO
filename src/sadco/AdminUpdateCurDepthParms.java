package sadco;

//import java.sql.*;
//import java.util.*;
//import java.io.*;
//import java.lang.*;

/**
 * This class updates the new parameters column in the cur_depth table  <br>
 *                                                                      <br>
 * It's parameters are the following:                                   <br>
 *                                                                      <br>
 * SadcoMRN                                                             <br>
 * screen.equals("admin")                                               <br>
 * version.equals("") (not in program yet)                              <br>
 *                                                                      <br>
 * @author 2009/05/12 - SIT Group.                                      <br>
 * @version                                                             <br>
 * 2009/05/12 - Ursula von St Ange - created class                      <br>
 */
public class AdminUpdateCurDepthParms {

    boolean dbg = false;
    //boolean dbg = true;
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommonPC ec = new common.edmCommonPC();
    sadco.SadConstants sc = new sadco.SadConstants();

    // the paramters
    String dataParameters[] = {"SPEED", "TEMPERATURE", "PRESSURE"};
    String dataParameterCodes[] = {"V ", "T ", "P "};
    String watphyParameters[] = {"PH", "SALINITY", "DIS_OXY"};
    String watphyParameterCodes[] = {"Ph ", "S ", "O "};

    AdminUpdateCurDepthParms(String args[]) throws Exception {


        // +------------------------------------------------------------+
        // | Get input arguments                                        |
        // +------------------------------------------------------------+
        String surveyId = args[0];

        // just in case the argument was 'psurveyid=<surveyID>'
        int dotPos = surveyId.indexOf('=');
        if (dotPos > -1) {
            surveyId = surveyId.substring(dotPos+1);
        } // if (dotPos > -1)
        if (dbg) System.out.println("surveyId = " + surveyId);

        // get the mooring code
        CurMooring[] mooring = getMooringRecord(surveyId);

        // get all the depths
        CurDepth[] depths = getDepthRecords(mooring[0].getCode());
        int depthsCount = depths.length;

        // check which parameters are present
        for (int i = 0; i < depthsCount; i++) {
        //for (int i = 0; i < 1; i++) {

            int depthCode = depths[i].getCode();
            boolean found = false;
            StringBuffer parmsExist = new StringBuffer();

            for (int j = 0; j < dataParameters.length; j++) {
                found = checkDataParameter(depthCode, dataParameters[j]);
                parmsExist.append((found ? dataParameterCodes[j] : ""));
            } // for (int j = 0; j < dataParameters.length; j++)

            for (int j = 0; j < watphyParameters.length; j++) {
                found = checkWatphyParameter(depthCode, watphyParameters[j]);
                parmsExist.append((found ? watphyParameterCodes[j] : ""));
            } // for (int j = 0; j < watphyParameters.length; j++)

            // update the inventory stats table
            updateCurDepthParameters(depthCode, parmsExist.toString().trim());
            if (dbg) System.out.println("parameters = " + depthCode + " " +
                parmsExist.toString());

        } // for (i = 0; i < depthsCount; i++)

    } // AdminUpdateCurDepthParms constructor


    /**
     * Get mooring records from database for the surveyId
     * @param surveyId : argument value from url (string)
     * @return array of mooring records
     */
    CurMooring[] getMooringRecord(String surveyId) {
        //if (dbg) System.out.println("<br>getDepthRecords");

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
     * @param surveyId : mooring code
     * @return array of depth records
     */
    CurDepth[] getDepthRecords(int mooring_code) {
        //if (dbg) System.out.println("<br>getDepthRecords");

        // construct fields, and where and order by clauses
        String fields = CurDepth.CODE;
        String where = CurDepth.MOORING_CODE +"="+ mooring_code;
        String order = CurDepth.CODE;

        CurDepth[] depths = new CurDepth().get(fields, where, order);

        return depths;
    } // getDepthRecords


    /**
     * Check whether a parameter in the cur_data table is present
     * @param   depthCode   the code of the depth record
     * @param   parameter   the parameter name
     * return   true/false
     */
    boolean checkDataParameter(int depthCode, String parameter) {

        String where = CurData.DEPTH_CODE +"="+ depthCode + " and " +
            parameter + " is not null";
        CurData data = new CurData();
        int records = data.getRecCnt(where);
        if (dbg) System.out.println("checkDataParameter: sql = " + data.getSelStr());
        if (dbg) System.out.println("checkDataParameter: records = " + records);
        return (records == 0 ? false : true);

    } // checkDataParameter


    /**
     * Check whether a parameter in the cur_watphy table is present
     * @param   depthCode   the code of the depth record
     * @param   parameter   the parameter name
     * return   true/false
     */
    boolean checkWatphyParameter(int depthCode, String parameter) {

        String where = CurWatphy.DEPTH_CODE +"="+ depthCode +" and " +
            parameter + " is not null";
        CurWatphy data = new CurWatphy();
        int records = data.getRecCnt(where);
        if (dbg) System.out.println("checkWatphyParameter: sql = " + data.getSelStr());
        if (dbg) System.out.println("checkWatphyParameter: records = " + records);
        return (records == 0 ? false : true);

    } // checkWatphyParameter


    /**
     * Update the cur_depth record with the parameters
     * @param   depthCode   code of cur_depth record
     * @param   parameters  string of parameters present
     */
    void updateCurDepthParameters(int depthCode, String parameters) {

        CurDepth depth = new CurDepth();

        depth.setParameters(parameters);

        // check if depth exists
        CurDepth depthWhere = new CurDepth();
        depthWhere.setCode(depthCode);
        depthWhere.upd(depth);
        if (dbg) System.out.println("depth updated: " +depth);
        if (dbg) System.out.println("updateCurDepthParameters: sql = " + depthWhere.getUpdStr());

    } // updateCurDepthParameters


    /**
     * The main procedure for processing in standalone mode
     * @param   args[]  array of command-line / URL arguments
     */
    public static void main(String args[]) {
        try {
            AdminUpdateCurDepthParms local = new AdminUpdateCurDepthParms(args);
        } catch (Exception e) {
            ec.processError(e, "AdminUpdateCurDepthParms", "constructor", "");
        } // try-catch
        common2.DbAccessC.close();
    } // main


} // public class AdminUpdateCurDepthParms
