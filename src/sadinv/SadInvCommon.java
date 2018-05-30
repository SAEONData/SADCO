package sadinv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import oracle.html.*;

/**
 * Contains all the constants used in the currents package.
 * @author  2002/02/20  SIT Group
 * @version 2002/02/20  Ursula von St Ange  created class               <br>
 * 030729 - Ursula von St Ange - update for fred                        <br>
 * 150319 - Ursula von St Ange - add current component extractions ub01 <br>
 *
 */
public class SadInvCommon {

    boolean dbg = false;
    //boolean dbg = true;
    String thisClass = this.getClass().getName();

    /** scripts */
    //static final public String DISK = "/usr2";
    static final public String DISK = "/home";
    static final public String SENDMAIL = DISK+"/localuser/sadco/sh_scripts/sendMail.sh";

    /** general purpose variables */
    static final public String CR = System.getProperty("line.separator");

    /** for user registration */
    static final public String ERROR_NONE = "0";
    static final public String ERROR_USER = "1";
    static final public String ERROR_PASSW = "2";

    /** for extraction */
    static final public int MAX_HYDRO       = 20000;
    static final public int MAX_CURRENTS    = 10000;
    static final public int MAX_WEATHER     = 60000;
    static final public int MAX_WAVES       = 10000;
    static final public int MAX_VOS         =  1000;
    static final public int MAX_STATIONS    =  1000;


    /** for extractions */
    static final public String DEG = "�";
    static final public String ONLINE = "Extract_online";
    static final public String OFFLINE = "Request_offline";
    static final String HYDRO_EXTR[] = {
        "Water Physical/Nutrient: ODV",     // 0
        "Water Physical/Nutrient: standard",// 1
        "Water Chemistry",                  // 2
        "Water Pollution",                  // 3
        "Sediment Physical",                // 4
        "Sediment Chemistry",               // 5
        "Sediment Pollution",               // 6
        "Tissue Pollution",                 // 7
        "Underway Currents: ODV",           // 8
        "Underway Currents: standard",      // 9
        "Underway Currents Components: ODV", // 10          //ub01
        "Underway Currents Components: standard"}; // 11    //ub01
    static final String HYDRO_EXT[] = {
        ".txt", // 0
        ".dbm", // 1
        ".dbwc",// 2
        ".dbwp",// 3
        ".dbs", // 4
        ".dbsc",// 5
        ".dbsp",// 6
        ".dbtp",// 7
        ".txt", // 8
        ".dbc"};// 9
     static final String OCCUPATIONS[] = {
         "",
         "professional",
         "scholar (at school)",
         "student",
         "teacher",
         "other"};


    /** login details */
    static final public String USER          = "sadco";
    static final public String PWD           = "ter91qes";
    //static final public String DRIVER        = "oracle.jdbc.driver.OracleDriver";
    static final public String DRIVER        = "org.postgresql.Driver";
    //static final public String CONNECTION1   = "jdbc:oracle:thin:";
    //static final public String CONNECTION1   = "jdbc:postgresql:";
    static final public String CONNECTION    = "jdbc:postgresql://localhost:5432/sadco"; // stel-oradb
    //static final public String CONNECTION    = "@steamer.csir.co.za:1522:etek8";
    //Class.forName("org.postgresql.Driver");
    //conn = DriverManager.getConnection(
    //    "jdbc:postgresql://146.64.123.216:5432/etek8", user,  pwd);


    /** application */
    static final public String APP           = "SadInv";
    static final public String APPS          = "SadInvApps";

    /** buttons and pscreen values */
    static final public String AREAS         = "areas";
    static final public String BUTTONS       = "buttons";
    static final public String CHECK         = "check";
    static final public String DATATYPES     = "datatypes";
    static final public String DATATYPES2    = "datatypes2";
    static final public String DATATYPEVOS   = "datatypevos";
    static final public String DATES         = "dates";
    static final public String DISPLAYSURVEY = "displaysurvey";
    static final public String EXTRACT       = "extract";
    static final public String FOOTER        = "footer";
    static final public String HEADER        = "header";
    static final public String HELP          = "help";
    static final public String INSTITUTES    = "institutes";
    static final public String INSTITUTES2   = "institutes2";
    static final public String KML           = "kml";
    static final public String LOGIN         = "login";
    static final public String PLATFORMS     = "platforms";
    static final public String PLATFORMS2    = "platforms2";
    static final public String PROJECTS      = "projects";
    static final public String PROJECTS2     = "projects2";
    static final public String REGISTER      = "register";
    static final public String SCIENTISTS    = "scientists";
    static final public String SCIENTISTS2   = "scientists2";
    static final public String SENDEMAIL     = "sendemail";
    static final public String STARTUP       = "startup";
    static final public String SURVEYS       = "surveys";

    /** url parameters names */
    static final public String ACTION        = "paction";
    static final public String ADDRESS       = "paddress";
    static final public String AFFIL         = "paffil";
    static final public String AVAILABLE     = "pavailable";
    static final public String CODE          = "pcode";
    static final public String DATANAME      = "pdataname";
    static final public String DATATYPE      = "pdatatype";
    static final public String DATESTART     = "pdatestart";
    static final public String DEPTHCODE     = "pdepthcode";
    static final public String EMAIL         = "pemail";
    static final public String ENDDATE       = "penddate";
    static final public String EXTRTYPE      = "pextrtype";
    static final public String FNAME         = "pfname";
    static final public String FORMAT        = "pformat";
    static final public String HEMEAST       = "phemeast";
    static final public String HEMNORTH      = "phemnorth";
    static final public String HEMSOUTH      = "phemsouth";
    static final public String HEMWEST       = "phemwest";
    static final public String INSTITUTE     = "pinstitute";
    static final public String LATITUDENORTH = "platitudenorth";
    static final public String LATITUDESOUTH = "platitudesouth";
    static final public String LONGITUDEEAST = "plongitudeeast";
    static final public String LONGITUDEWEST = "plongitudewest";
    static final public String METHOD        = "pmethod";
    static final public String OCCUPATION    = "poccupation";
    static final public String PASSWORD      = "ppassword";
    static final public String PLATFORM      = "pplatform";
    static final public String PROJECT       = "pproject";
    static final public String PROJECTPART   = "pprojectpart";
    static final public String PSC           = "psc";
    static final public String SCREEN        = "pscreen";
    static final public String STARTDATE     = "pstartdate";
    static final public String SURNAME       = "psurname";
    static final public String SURVEYID      = "psurveyid";
    static final public String USEROK        = "puserok";
    static final public String VERSION       = "pversion";
    static final public String VERSION2      = "pversion2";
    static final public String YEAR          = "pyear";

    public SadInvCommon () {
    } // SadInvCommon contructor


    /**
     * get connected to the database
     */
    Connection getConnected(String callingClass) {
        return getConnected(callingClass, "");
    } // getConnected


    /**
     * get connected to the database
     */
    Connection getConnected(String callingClass, String sessionCode) {
        Connection conn = null;
        try {
            //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            //conn = DriverManager.getConnection
            //("jdbc:oracle:oci8:@etek8.csir.co.za","sadco","ter91qes");
            Class.forName(DRIVER);                                      //ub05
            //conn = DriverManager.getConnection
            //    ("jdbc:oracle:thin:" + sc.USER + "/" + sc.PWD +
            //     sc.CONNECTION);          // steamer
                 //"@morph.csir.co.za:1522:etek8");
            conn = DriverManager.getConnection(                         //ub05
                CONNECTION, USER,  PWD);                                //ub05
            System.out.println(callingClass + ":p:" +
                getDateTime() + "-" + getUser(conn, sessionCode));
            conn.setAutoCommit(false);
        } catch (Exception e) {
            return null;
        } // try-catch
        return conn;
    } // getConnected


    /**
     * get the current date and time
     */
    private String getDateTime() {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd't'HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    } // getDateTime


    /**
     * get the current user
     */
    private String getUser(Connection conn, String sessionCode) {

        // get the user's e-mail address
        String email = "";
        if (!"".equals(sessionCode)) {
            try {
                String sql =
                    "select userid from log_sessions " +
                    "where code = " + sessionCode;
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery(sql);
                while (rset.next()) {
                    email = checkNull(rset.getString(1));
                } // while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;
            } catch (SQLException e) { }
        } // if (!"".equals(sessionCode))

        return email;
    } // getUser


    /**
     * store the sruvey id list for current user
     */
    void storeSurveyList(Connection conn, String sessionCode, String surveyList) {

        String email = getUser(conn, sessionCode);
        String sql = "";

        // first delete old record
        if (!"".equals(sessionCode)) {
            try {
                sql = "delete from inv_add where userid = '" + email + "'";
                Statement stmt = conn.createStatement();
                int numRecords = stmt.executeUpdate(sql);
                if (dbg) System.out.println("SadinvCommon.storeSurveyList sqlDel = " + sql);
                if (dbg) System.out.println("SadinvCommon.storeSurveyList numRecords = " + numRecords);
                stmt.close();
                stmt = null;
            } catch (SQLException e) {
                System.out.println("error: SadinvCommon.storeSurveyList sqlDel = " + sql);
            } // try

            // now insert new record
            try {
                sql = "insert into inv_add (userid, survey_list) values ('" +
                    email + "','" + surveyList + "')";;
                Statement stmt = conn.createStatement();
                int numRecords = stmt.executeUpdate(sql);
                if (dbg) System.out.println("SadinvCommon.storeSurveyList sqlIns = " + sql);
                if (dbg) System.out.println("SadinvCommon.storeSurveyList numRecords = " + numRecords);
                stmt.close();
                stmt = null;
            } catch (SQLException e) {
                System.out.println("error: SadinvCommon.storeSurveyList sqlIns = " + sql);
            } // try
        } // if (!"".equals(sessionCode))

    } // storeSurveyList

    /**
     * get next survey from list on db
     */
    String getNextSurvey(Connection conn, String sessionCode,
            String surveyId, String available) {

        if (dbg) System.out.println(thisClass + ".getNextSurvey: sessionCode = " + sessionCode);
        if (dbg) System.out.println(thisClass + ".getNextSurvey: surveyId = " + surveyId);
        if (dbg) System.out.println(thisClass + ".getNextSurvey: available = " + available);

        String email = getUser(conn, sessionCode);
        String surveyString = "";
        String nextSurvey = "";
        if (dbg) System.out.println(thisClass + ".getNextSurvey: email = " + email);
        if (dbg) System.out.println(thisClass + ".getNextSurvey: xxx = *" +
            surveyId + " " + available + "*");

        // get the list of surveys
        if (!"".equals(sessionCode)) {
            try {
                String sql =
                    "select survey_list from inv_add " +
                    "where userid = '" + email + "'";
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery(sql);
                while (rset.next()) {
                    surveyString = checkNull(rset.getString(1));
                } // while (rset.next())
                rset.close();
                stmt.close();
                rset = null;
                stmt = null;
            } catch (SQLException e) { }

            //find then next survey
            String surveyArray[] = surveyString.split(",");
            ArrayList surveyList = new ArrayList(Arrays.asList(surveyArray));
            int index = surveyList.indexOf(surveyId + " " + available);
            if (dbg) System.out.println(thisClass + ".getNextSurvey: index = " + index);
            if (dbg) System.out.println(thisClass + ".getNextSurvey: 1 = " + (String)surveyList.get(0));
            if (dbg) System.out.println(thisClass + ".getNextSurvey: 1 = " + surveyArray[0]);
            if ((index+1) < surveyList.size()) nextSurvey = (String)surveyList.get(index+1);
        } // if (!"".equals(sessionCode))

        return nextSurvey;

    } // getNextSurvey


    /**
     * check if the value is null
     * returns  value / empty string
     */
    String checkNull(String value) {
        return (value != null ? value : "");
    } // checkNull

    /**
     * check if value is null
     * returns  value / "0"
     */
    String checkNull0(String value) {
        return (value != null ? value : "0");
    } // sc.checkNull0


    /**
     * disconnect from the database
     */
    void disConnect(Statement stmt, ResultSet rset, Connection conn) {
        // make sure everything is closed and null
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            } // if (stmt != null)
            if (rset != null) {
                rset.close();
                rset = null;
            } // if (rset != null)
            if (conn != null) {
                conn.close();
                conn = null;
            } // if (conn != null)
        }  catch (SQLException se){}
    } // disConnect

    /**
     * disconnect from the database - with commit
     */
    void disConnect2(Statement stmt, ResultSet rset, Connection conn) {
        // make sure everything is closed and null
        try {
            if (conn != null) {
                conn.commit();
            } // if (conn != null)
        } catch (SQLException se){}
        disConnect(stmt, rset, conn);
    } // disConnect

} // class SadInvCommon
