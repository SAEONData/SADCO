package sadco;

import java.io.*;
import java.sql.*;
import java.util.*; // for Calendar
import oracle.html.*;
import java.lang.*;

/**
 * Does the actual loading of the VOS data.
 *
 * SadcoVOS
 * screen.equals("load")
 * version.equals("doRejectLoad")
 *
 * @author  001123 - SIT Group
 * @version
 * 001123 - Ursula von St Ange - create class                               <br>
 */
public class LoadVOSReject {

    /** some common functions */

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();

    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    static final String PWD            = "ter91qes";
    static final String USER           = "SADCO";

    //boolean dbg = false;
    boolean dbg = true;
    String thisClass = this.getClass().getName();

    // arguments
    String fileName  = "";
    String beginLong = "";
    String endLong   = "";
    String dataBase  = "";
    boolean doRejectLoad = false;

    // file stuff
    String      pathName;
    RandomAccessFile lfile;
    RandomAccessFile ofile1;
    RandomAccessFile ofile2;
    RandomAccessFile ofile3;
    RandomAccessFile warningFile;
    RandomAccessFile dbgf;
    String      line;

    //My variables
    float surfaceTemperature  = 0;
    float latitude            = 0;
    float longitude           = 0;
    float dateTime            = 0;
    float atmosphericPressure = 0;
    float drybulb             = 0;
    float wetbulb             = 0;
    float dewpoint            = 0;
    float swellDirection      = 0;
    float swellHeight         = 0;
    float swellPeriod         = 0;
    float waveHeight          = 0;
    float wavePeriod          = 0;
    float windDirection       = 0;
    float windSpeed           = 0;

    int numValues             = 15;

    // the data
    VosTable data;

    // data variables - sector0
    // group 0
    String      callsign;  // Ship's Call sign (c_callsn)
    // group 1
    String      day;       // Day of the month (GMT) (dag)
    String      gg;        // time of observation to the nearest whole hour GMT (tyd)
    String      wi;        // Wind indicator (wndc)
    // group 2
    //String    shiposi=99;  // Indicator for Ship's position
    String      latin;     // Latitude in tenths of a degree  (slat)
    // group 3
    String      q;         // Quadrant of the globe (qud)
    String      longin;    // Longitude in tenths of a degree (slog)

    // data variables - sector1
    // group 4
    //String      r=4;       // Indicator for inclusion or ommission of precipitation data
    //String      operi;     // Indicator for station operation and for present and past  weather data.
    String      clht;      // Height of the base of the lowest cloud in the sky (stts)
    String      vv;        // Horizontal visibility at the surface
    // group 5
    String      clfr;      // Fraction of the celestial dome covered by cloud (windg)
    String      wdir;      // True direction from which wind is blowing
    String      wsp;       // Wind Speed
    // group 6
    //String      ati=1;     // Indicator for the air temperature
    String      ats;       // Sign of air temperature (tts)
    String      at;        // Air Temperature in tenths of a degree Celsius
    // group 7
    //String      dwi=2;     // Indicator for the dew-point temperature
    String      dws;       // Sign of dew-point temperature (tds)
    String      dw;        // dew-point Temperature in tenths of a degree Celsius
    // group 8
    //String      slpi=4;    // Indicator for sea-level pressure (c_press)
    String      slp;       // Pressure at sea-level in tenths of a millibar (= hectopascal),
                           //    omitting the thousands digit of the pressure value.
    // group 9
    //String      pci=5;     // Indicator for characteristic of pressure change (c_app)
    //String      pcc;       // Characteristic of pressure tendency during the three hours |
                             //    preceding time of observation
    //String      pc;        // Amount of pressure change during the three hours preceding |
                             //    the time of observation, in tenths of a millibar
    // group 10
    //String      ppwi=7;      // Indicator for past and present weather
    String      ppwn;      // Present weather (c_ww)
    //String      ppwp1;     // Past weather code W1
    //String      ppwp2;     // Past weather code W2

    // group 11
    //String      cti=8;     // Indicator for cloud types (cloud)
    String      ctf;       // Fraction of celestial dome covered by cloud(s) reported for
                           // CTL or, if no CTL cloud is present, for CTM.
    String      ctl;       // Clouds: stratocumulus, stratus, cumulus, cumulonimbus
    String      ctm;       // Clouds: altocumulus, altostratus, nimbostratus
    String      cth;       // Clouds: cirrus, cirrocumulus and cirrostratus

    // data variables - sector2
    // group 12
    //String      scsi=222;    // Indicator for ship's course and speed (c_courc)
    //String      cscs;      // Course (true) made good during three hours preceding
    //                       // time of observation
    //String      scss;      // Ship's avarage speed during thethree hours preceding
    //                       // time  of observation.
    // group 13
    //String      sti=0;     // Indicator for sea-surface temperature
    String      sts;       // Sign of sea-surface temperature (stemp 00 ?)
    String      st;        // Sea-surface temperature in tenths of a degree Celsius,
                           // its  sign being given by STS
    // group 14
    //String      wwi=2;     // Indicator for wind waves
    String      wwp;       // Period of wind waves in seconds (c_wavp)
    String      wwh;       // Height of wind waves in units of half metres (c_wavh)
    // group 15
    //String      swi=3;       // Swell direction indicator
    String      sw1d;      // Swell 1 true direction, in tens of degrees, from which
                           // swell waves are coming (c_swld1)
    //String      sw2d;      // Swell 2 true direction, in tens of degrees, from which
    //                       // swell waves are coming (c_swld2)
    // group 16
    //String      sw1i=4;    // Swell 1 indicator
    String      sw1p;      // Swell 1 period of swell waves in seconds (c_swsec1)
    String      sw1h;      // Swell 1 height of swell waves in units of half metres (c_swht1)
    // group 17
    //String      sw2i=5;    // Swell 2 indicator
    //String      sw2p;      // Swell 2 period of swell waves in seconds
    //String      sw2h;      // Swell 2 height of swell waves in units of half metres
    // group 18
    //String      ici=6;       // Indicator or ice accretion
    //String      ica ;      // Type of ice accretion
    //String      ict ;      // Thickness of ice in centimetres
    //String      icr ;      // Rate of ice accretion

    // data variables - date
    String      year;      // (styd)
    String      month;     // (c_fapr)

    // data variables - processLoadLine
    //String      latinD, latinM, latinS;
    //String      loninQ, loninD, loninM, loninS;
    String      latinS;
    String      loninS;
    String      dBase;
    String      winspType;
    float       atpres;
    String      platform;
    int         winsp;
    int         windir;
    int         wavht;
    int         swdir;
    int         swht;

    // error flags
    boolean     readErrorFlag = false;  // date, lat, lon, q, overland testing
    boolean     tempErrorFlag = false;  // data testing
    boolean     errorFlag     = false;  // all errors
    boolean     nonNumericFlag = false; // to catch the nonNumeric Fields

    // working variables
    int         recordCount = 0;
    int         loadCount = 0;
    int         dayCount = 0;
    int         fatalCount = 0;
    int         mainDupCount = 0;
    int         archDupCount = 0;
    int         mainCount = 0;
    int         archCount = 0;
    int         skipCount = 0;
    int         rejectCount = 0;
    String      loadStat = "";
    Timestamp   currTime;
    Timestamp   prevTime = Timestamp.valueOf("1850-01-01 00:00:00.0");
    java.util.Date  dateStart = Timestamp.valueOf("2050-01-01 00:00:00.0");
    java.util.Date  dateEnd   = Timestamp.valueOf("1850-01-01 00:00:00.0");

    int         EVAL = -9999999;

    LoadVOSReject(String args[]) throws Exception {

        // get the correct path name
        //if ("morph".equals(ec.getHost())) {
        //    pathName = sc.MORPHL + "vos/";
        //} else {
        //    pathName = sc.LOCAL + "vos/";
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL + "vos/";
        } else {
            pathName = sc.LOCALDIR;
        } // if host


        // establish connection to database
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection
            ("jdbc:oracle:thin:" + USER + "/" + PWD +
                "@morph.csir.co.za:1522:etek8");
                conn.setAutoCommit(false);
        } catch (SQLException e) {
            ec.processError(e, thisClass, "constructor",
                "Error connecting to Postmortem Database");
            return;
        } // try-catch

        // Variables to create a SQL statement
        Statement stmt;
        ResultSet rset;
        String sql  = "";

        // +------------------------------------------------------------+
        // | Get input arguments                                        |
        // +------------------------------------------------------------+
        String fileName  = args[0];
        String beginLong = args[1];
        String endLong   = args[2];
        String incrVal   = args[3];
        String dataBase  = args[4];

        //fileName   = ec.getArgument(args,sc.FILENAME);
        //beginLong  = ec.getArgument(args,sc.BEGINLONG);
        //endLong    = ec.getArgument(args,sc.ENDLONG);
        //dataBase   = ec.getArgument(args,sc.DATABASE);

        int sLong = new Integer(beginLong).intValue();
        int eLong   = new Integer(endLong).intValue();
        int incr  = new Integer(incrVal).intValue();


        String temp = ec.getArgument(args, "pdoRejectLoad");
        if ("".equals(temp)) { doRejectLoad = true; }

        System.out.println("<br>");
        System.out.println("<br>fileName = " + fileName);
        System.out.println("<br>beginLong = " + beginLong);
        System.out.println("<br>endLong = " + endLong);
        System.out.println("<br>dataBase = " + dataBase);

        // Create report file.

        //openReportFile();

        // Function to loop through datBase
        //getRejectRecs();
        String colums = "LATITUDE,LONGITUDE,DATE_TIME,ATMOSPHERIC_PRESSURE,"+
                        "SURFACE_TEMPERATURE,DRYBULB, WETBULB, DEWPOINT, "+
                        "SWELL_DIRECTION, SWELL_HEIGHT, SWELL_PERIOD,"+
                        "WAVE_HEIGHT, WAVE_PERIOD, WIND_DIRECTION, WIND_SPEED";

        for (int i = sLong; i < eLong; i+=incr) {

            //float valueArray[]     = new float[numValues];
            //boolean valueIsNull[]  = new boolean[numValues];
            //float cutOffPointsU[]  = {37.00f,40.00f,40.00f};
            //float cutOffPointsL[]   = {-2.00f,-25.00f,-25.00f};

            float j = i+incr;

            sql = "select "+ colums +" from "+ dataBase +
                  //" where Latitude between 0 and 0.1";
                  " where Latitude between "+i+" and "+j;

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            int cnt = 0;

            while (rset.next()) {

                if (rset.wasNull() == false) {
                    surfaceTemperature  = new Float(rset.getFloat(1)).floatValue();
                } //if (rset.wasNull() == false) {

                // No need to test for Null values.
                // Fields part of Pk.
                latitude            = new Float(rset.getFloat(2)).floatValue();
                longitude           = new Float(rset.getFloat(3)).floatValue();
                dateTime            = new Float(rset.getFloat(4)).floatValue();

                if (rset.wasNull() == false) {
                    atmosphericPressure = new Float(rset.getFloat(5)).floatValue();
                }

                if (rset.wasNull() == false) {
                    drybulb = new Float(rset.getFloat(6)).floatValue();
                }

                if (rset.wasNull() == false) {
                    wetbulb = new Float(rset.getFloat(7)).floatValue();
                }

                if (rset.wasNull() == false) {
                    dewpoint = new Float(rset.getFloat(8)).floatValue();
                }

                if (rset.wasNull() == false) {
                    swellDirection = new Float(rset.getFloat(9)).floatValue();
                }

                if (rset.wasNull() == false) {
                    swellHeight = new Float(rset.getFloat(10)).floatValue();
                }

                if (rset.wasNull() == false) {
                    swellPeriod = new Float(rset.getFloat(11)).floatValue();
                }

                if (rset.wasNull() == false) {
                    waveHeight = new Float(rset.getFloat(12)).floatValue();
                }

                if (rset.wasNull() == false) {
                    wavePeriod = new Float(rset.getFloat(13)).floatValue();
                }

                if (rset.wasNull() == false) {
                    windDirection = new Float(rset.getFloat(14)).floatValue();
                }

                if (rset.wasNull() == false) {
                    windSpeed = new Float(rset.getFloat(15)).floatValue();
                }
                cnt++;
                System.out.println("Num records for latitude between "+sLong+" and "+eLong);

             } //while (rset.next()) {
        } //for (int i = startLong; i < endLong; i++) {

    } // LoadVOSReject constructor


    public static void main(String args[]) {
        try {
            LoadVOSReject local = new LoadVOSReject(args);
        } catch (Exception e) {
            ec.processError(e, "LoadVOSReject", "constructor", "");
        } // try-catch
    } // main

} // public class LoadVOSReject

