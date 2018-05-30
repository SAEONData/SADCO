package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the INV_STATS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 091026 - SIT Group
 * @version
 * 091026 - GenTableClassB class - create class<br>
 */
public class MrnInvStats extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "INV_STATS";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The stationCnt field name */
    public static final String STATION_CNT = "STATION_CNT";
    /** The watphyCnt field name */
    public static final String WATPHY_CNT = "WATPHY_CNT";
    /** The watnutCnt field name */
    public static final String WATNUT_CNT = "WATNUT_CNT";
    /** The watpol1Cnt field name */
    public static final String WATPOL1_CNT = "WATPOL1_CNT";
    /** The watpol2Cnt field name */
    public static final String WATPOL2_CNT = "WATPOL2_CNT";
    /** The watchem1Cnt field name */
    public static final String WATCHEM1_CNT = "WATCHEM1_CNT";
    /** The watchem2Cnt field name */
    public static final String WATCHEM2_CNT = "WATCHEM2_CNT";
    /** The watchlCnt field name */
    public static final String WATCHL_CNT = "WATCHL_CNT";
    /** The sedphyCnt field name */
    public static final String SEDPHY_CNT = "SEDPHY_CNT";
    /** The sedpesCnt field name */
    public static final String SEDPES_CNT = "SEDPES_CNT";
    /** The sedpol1Cnt field name */
    public static final String SEDPOL1_CNT = "SEDPOL1_CNT";
    /** The sedpol2Cnt field name */
    public static final String SEDPOL2_CNT = "SEDPOL2_CNT";
    /** The sedchem1Cnt field name */
    public static final String SEDCHEM1_CNT = "SEDCHEM1_CNT";
    /** The sedchem2Cnt field name */
    public static final String SEDCHEM2_CNT = "SEDCHEM2_CNT";
    /** The sedtaxCnt field name */
    public static final String SEDTAX_CNT = "SEDTAX_CNT";
    /** The plaphyCnt field name */
    public static final String PLAPHY_CNT = "PLAPHY_CNT";
    /** The plapesCnt field name */
    public static final String PLAPES_CNT = "PLAPES_CNT";
    /** The plapol1Cnt field name */
    public static final String PLAPOL1_CNT = "PLAPOL1_CNT";
    /** The plapol2Cnt field name */
    public static final String PLAPOL2_CNT = "PLAPOL2_CNT";
    /** The plataxCnt field name */
    public static final String PLATAX_CNT = "PLATAX_CNT";
    /** The plachlCnt field name */
    public static final String PLACHL_CNT = "PLACHL_CNT";
    /** The tisphyCnt field name */
    public static final String TISPHY_CNT = "TISPHY_CNT";
    /** The tispesCnt field name */
    public static final String TISPES_CNT = "TISPES_CNT";
    /** The tispol1Cnt field name */
    public static final String TISPOL1_CNT = "TISPOL1_CNT";
    /** The tispol2Cnt field name */
    public static final String TISPOL2_CNT = "TISPOL2_CNT";
    /** The tisanimalCnt field name */
    public static final String TISANIMAL_CNT = "TISANIMAL_CNT";
    /** The weatherCnt field name */
    public static final String WEATHER_CNT = "WEATHER_CNT";
    /** The watcurrentsCnt field name */
    public static final String WATCURRENTS_CNT = "WATCURRENTS_CNT";
    /** The watosdCnt field name */
    public static final String WATOSD_CNT = "WATOSD_CNT";
    /** The watctdCnt field name */
    public static final String WATCTD_CNT = "WATCTD_CNT";
    /** The watxbtCnt field name */
    public static final String WATXBT_CNT = "WATXBT_CNT";
    /** The watmbtCnt field name */
    public static final String WATMBT_CNT = "WATMBT_CNT";
    /** The watpflCnt field name */
    public static final String WATPFL_CNT = "WATPFL_CNT";
    /** The watphyStnCnt field name */
    public static final String WATPHY_STN_CNT = "WATPHY_STN_CNT";
    /** The watosdStnCnt field name */
    public static final String WATOSD_STN_CNT = "WATOSD_STN_CNT";
    /** The watctdStnCnt field name */
    public static final String WATCTD_STN_CNT = "WATCTD_STN_CNT";
    /** The watxbtStnCnt field name */
    public static final String WATXBT_STN_CNT = "WATXBT_STN_CNT";
    /** The watmbtStnCnt field name */
    public static final String WATMBT_STN_CNT = "WATMBT_STN_CNT";
    /** The watpflStnCnt field name */
    public static final String WATPFL_STN_CNT = "WATPFL_STN_CNT";
    /** The watnutStnCnt field name */
    public static final String WATNUT_STN_CNT = "WATNUT_STN_CNT";
    /** The watpolStnCnt field name */
    public static final String WATPOL_STN_CNT = "WATPOL_STN_CNT";
    /** The watchemStnCnt field name */
    public static final String WATCHEM_STN_CNT = "WATCHEM_STN_CNT";
    /** The watchlStnCnt field name */
    public static final String WATCHL_STN_CNT = "WATCHL_STN_CNT";
    /** The sedphyStnCnt field name */
    public static final String SEDPHY_STN_CNT = "SEDPHY_STN_CNT";
    /** The sedpesStnCnt field name */
    public static final String SEDPES_STN_CNT = "SEDPES_STN_CNT";
    /** The sedpolStnCnt field name */
    public static final String SEDPOL_STN_CNT = "SEDPOL_STN_CNT";
    /** The sedchemStnCnt field name */
    public static final String SEDCHEM_STN_CNT = "SEDCHEM_STN_CNT";
    /** The sedtaxStnCnt field name */
    public static final String SEDTAX_STN_CNT = "SEDTAX_STN_CNT";
    /** The plaphyStnCnt field name */
    public static final String PLAPHY_STN_CNT = "PLAPHY_STN_CNT";
    /** The plapesStnCnt field name */
    public static final String PLAPES_STN_CNT = "PLAPES_STN_CNT";
    /** The plapolStnCnt field name */
    public static final String PLAPOL_STN_CNT = "PLAPOL_STN_CNT";
    /** The plataxStnCnt field name */
    public static final String PLATAX_STN_CNT = "PLATAX_STN_CNT";
    /** The plachlStnCnt field name */
    public static final String PLACHL_STN_CNT = "PLACHL_STN_CNT";
    /** The tisphyStnCnt field name */
    public static final String TISPHY_STN_CNT = "TISPHY_STN_CNT";
    /** The tispesStnCnt field name */
    public static final String TISPES_STN_CNT = "TISPES_STN_CNT";
    /** The tispolStnCnt field name */
    public static final String TISPOL_STN_CNT = "TISPOL_STN_CNT";
    /** The tisanimalStnCnt field name */
    public static final String TISANIMAL_STN_CNT = "TISANIMAL_STN_CNT";
    /** The watcurrentsStnCnt field name */
    public static final String WATCURRENTS_STN_CNT = "WATCURRENTS_STN_CNT";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    surveyId;
    private int       stationCnt;
    private int       watphyCnt;
    private int       watnutCnt;
    private int       watpol1Cnt;
    private int       watpol2Cnt;
    private int       watchem1Cnt;
    private int       watchem2Cnt;
    private int       watchlCnt;
    private int       sedphyCnt;
    private int       sedpesCnt;
    private int       sedpol1Cnt;
    private int       sedpol2Cnt;
    private int       sedchem1Cnt;
    private int       sedchem2Cnt;
    private int       sedtaxCnt;
    private int       plaphyCnt;
    private int       plapesCnt;
    private int       plapol1Cnt;
    private int       plapol2Cnt;
    private int       plataxCnt;
    private int       plachlCnt;
    private int       tisphyCnt;
    private int       tispesCnt;
    private int       tispol1Cnt;
    private int       tispol2Cnt;
    private int       tisanimalCnt;
    private int       weatherCnt;
    private int       watcurrentsCnt;
    private int       watosdCnt;
    private int       watctdCnt;
    private int       watxbtCnt;
    private int       watmbtCnt;
    private int       watpflCnt;
    private int       watphyStnCnt;
    private int       watosdStnCnt;
    private int       watctdStnCnt;
    private int       watxbtStnCnt;
    private int       watmbtStnCnt;
    private int       watpflStnCnt;
    private int       watnutStnCnt;
    private int       watpolStnCnt;
    private int       watchemStnCnt;
    private int       watchlStnCnt;
    private int       sedphyStnCnt;
    private int       sedpesStnCnt;
    private int       sedpolStnCnt;
    private int       sedchemStnCnt;
    private int       sedtaxStnCnt;
    private int       plaphyStnCnt;
    private int       plapesStnCnt;
    private int       plapolStnCnt;
    private int       plataxStnCnt;
    private int       plachlStnCnt;
    private int       tisphyStnCnt;
    private int       tispesStnCnt;
    private int       tispolStnCnt;
    private int       tisanimalStnCnt;
    private int       watcurrentsStnCnt;

    /** The error variables  */
    private int surveyIdError          = ERROR_NORMAL;
    private int stationCntError        = ERROR_NORMAL;
    private int watphyCntError         = ERROR_NORMAL;
    private int watnutCntError         = ERROR_NORMAL;
    private int watpol1CntError        = ERROR_NORMAL;
    private int watpol2CntError        = ERROR_NORMAL;
    private int watchem1CntError       = ERROR_NORMAL;
    private int watchem2CntError       = ERROR_NORMAL;
    private int watchlCntError         = ERROR_NORMAL;
    private int sedphyCntError         = ERROR_NORMAL;
    private int sedpesCntError         = ERROR_NORMAL;
    private int sedpol1CntError        = ERROR_NORMAL;
    private int sedpol2CntError        = ERROR_NORMAL;
    private int sedchem1CntError       = ERROR_NORMAL;
    private int sedchem2CntError       = ERROR_NORMAL;
    private int sedtaxCntError         = ERROR_NORMAL;
    private int plaphyCntError         = ERROR_NORMAL;
    private int plapesCntError         = ERROR_NORMAL;
    private int plapol1CntError        = ERROR_NORMAL;
    private int plapol2CntError        = ERROR_NORMAL;
    private int plataxCntError         = ERROR_NORMAL;
    private int plachlCntError         = ERROR_NORMAL;
    private int tisphyCntError         = ERROR_NORMAL;
    private int tispesCntError         = ERROR_NORMAL;
    private int tispol1CntError        = ERROR_NORMAL;
    private int tispol2CntError        = ERROR_NORMAL;
    private int tisanimalCntError      = ERROR_NORMAL;
    private int weatherCntError        = ERROR_NORMAL;
    private int watcurrentsCntError    = ERROR_NORMAL;
    private int watosdCntError         = ERROR_NORMAL;
    private int watctdCntError         = ERROR_NORMAL;
    private int watxbtCntError         = ERROR_NORMAL;
    private int watmbtCntError         = ERROR_NORMAL;
    private int watpflCntError         = ERROR_NORMAL;
    private int watphyStnCntError      = ERROR_NORMAL;
    private int watosdStnCntError      = ERROR_NORMAL;
    private int watctdStnCntError      = ERROR_NORMAL;
    private int watxbtStnCntError      = ERROR_NORMAL;
    private int watmbtStnCntError      = ERROR_NORMAL;
    private int watpflStnCntError      = ERROR_NORMAL;
    private int watnutStnCntError      = ERROR_NORMAL;
    private int watpolStnCntError      = ERROR_NORMAL;
    private int watchemStnCntError     = ERROR_NORMAL;
    private int watchlStnCntError      = ERROR_NORMAL;
    private int sedphyStnCntError      = ERROR_NORMAL;
    private int sedpesStnCntError      = ERROR_NORMAL;
    private int sedpolStnCntError      = ERROR_NORMAL;
    private int sedchemStnCntError     = ERROR_NORMAL;
    private int sedtaxStnCntError      = ERROR_NORMAL;
    private int plaphyStnCntError      = ERROR_NORMAL;
    private int plapesStnCntError      = ERROR_NORMAL;
    private int plapolStnCntError      = ERROR_NORMAL;
    private int plataxStnCntError      = ERROR_NORMAL;
    private int plachlStnCntError      = ERROR_NORMAL;
    private int tisphyStnCntError      = ERROR_NORMAL;
    private int tispesStnCntError      = ERROR_NORMAL;
    private int tispolStnCntError      = ERROR_NORMAL;
    private int tisanimalStnCntError   = ERROR_NORMAL;
    private int watcurrentsStnCntError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String surveyIdErrorMessage          = "";
    private String stationCntErrorMessage        = "";
    private String watphyCntErrorMessage         = "";
    private String watnutCntErrorMessage         = "";
    private String watpol1CntErrorMessage        = "";
    private String watpol2CntErrorMessage        = "";
    private String watchem1CntErrorMessage       = "";
    private String watchem2CntErrorMessage       = "";
    private String watchlCntErrorMessage         = "";
    private String sedphyCntErrorMessage         = "";
    private String sedpesCntErrorMessage         = "";
    private String sedpol1CntErrorMessage        = "";
    private String sedpol2CntErrorMessage        = "";
    private String sedchem1CntErrorMessage       = "";
    private String sedchem2CntErrorMessage       = "";
    private String sedtaxCntErrorMessage         = "";
    private String plaphyCntErrorMessage         = "";
    private String plapesCntErrorMessage         = "";
    private String plapol1CntErrorMessage        = "";
    private String plapol2CntErrorMessage        = "";
    private String plataxCntErrorMessage         = "";
    private String plachlCntErrorMessage         = "";
    private String tisphyCntErrorMessage         = "";
    private String tispesCntErrorMessage         = "";
    private String tispol1CntErrorMessage        = "";
    private String tispol2CntErrorMessage        = "";
    private String tisanimalCntErrorMessage      = "";
    private String weatherCntErrorMessage        = "";
    private String watcurrentsCntErrorMessage    = "";
    private String watosdCntErrorMessage         = "";
    private String watctdCntErrorMessage         = "";
    private String watxbtCntErrorMessage         = "";
    private String watmbtCntErrorMessage         = "";
    private String watpflCntErrorMessage         = "";
    private String watphyStnCntErrorMessage      = "";
    private String watosdStnCntErrorMessage      = "";
    private String watctdStnCntErrorMessage      = "";
    private String watxbtStnCntErrorMessage      = "";
    private String watmbtStnCntErrorMessage      = "";
    private String watpflStnCntErrorMessage      = "";
    private String watnutStnCntErrorMessage      = "";
    private String watpolStnCntErrorMessage      = "";
    private String watchemStnCntErrorMessage     = "";
    private String watchlStnCntErrorMessage      = "";
    private String sedphyStnCntErrorMessage      = "";
    private String sedpesStnCntErrorMessage      = "";
    private String sedpolStnCntErrorMessage      = "";
    private String sedchemStnCntErrorMessage     = "";
    private String sedtaxStnCntErrorMessage      = "";
    private String plaphyStnCntErrorMessage      = "";
    private String plapesStnCntErrorMessage      = "";
    private String plapolStnCntErrorMessage      = "";
    private String plataxStnCntErrorMessage      = "";
    private String plachlStnCntErrorMessage      = "";
    private String tisphyStnCntErrorMessage      = "";
    private String tispesStnCntErrorMessage      = "";
    private String tispolStnCntErrorMessage      = "";
    private String tisanimalStnCntErrorMessage   = "";
    private String watcurrentsStnCntErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final int       STATION_CNT_MN = INTMIN;
    public static final int       STATION_CNT_MX = INTMAX;
    public static final int       WATPHY_CNT_MN = INTMIN;
    public static final int       WATPHY_CNT_MX = INTMAX;
    public static final int       WATNUT_CNT_MN = INTMIN;
    public static final int       WATNUT_CNT_MX = INTMAX;
    public static final int       WATPOL1_CNT_MN = INTMIN;
    public static final int       WATPOL1_CNT_MX = INTMAX;
    public static final int       WATPOL2_CNT_MN = INTMIN;
    public static final int       WATPOL2_CNT_MX = INTMAX;
    public static final int       WATCHEM1_CNT_MN = INTMIN;
    public static final int       WATCHEM1_CNT_MX = INTMAX;
    public static final int       WATCHEM2_CNT_MN = INTMIN;
    public static final int       WATCHEM2_CNT_MX = INTMAX;
    public static final int       WATCHL_CNT_MN = INTMIN;
    public static final int       WATCHL_CNT_MX = INTMAX;
    public static final int       SEDPHY_CNT_MN = INTMIN;
    public static final int       SEDPHY_CNT_MX = INTMAX;
    public static final int       SEDPES_CNT_MN = INTMIN;
    public static final int       SEDPES_CNT_MX = INTMAX;
    public static final int       SEDPOL1_CNT_MN = INTMIN;
    public static final int       SEDPOL1_CNT_MX = INTMAX;
    public static final int       SEDPOL2_CNT_MN = INTMIN;
    public static final int       SEDPOL2_CNT_MX = INTMAX;
    public static final int       SEDCHEM1_CNT_MN = INTMIN;
    public static final int       SEDCHEM1_CNT_MX = INTMAX;
    public static final int       SEDCHEM2_CNT_MN = INTMIN;
    public static final int       SEDCHEM2_CNT_MX = INTMAX;
    public static final int       SEDTAX_CNT_MN = INTMIN;
    public static final int       SEDTAX_CNT_MX = INTMAX;
    public static final int       PLAPHY_CNT_MN = INTMIN;
    public static final int       PLAPHY_CNT_MX = INTMAX;
    public static final int       PLAPES_CNT_MN = INTMIN;
    public static final int       PLAPES_CNT_MX = INTMAX;
    public static final int       PLAPOL1_CNT_MN = INTMIN;
    public static final int       PLAPOL1_CNT_MX = INTMAX;
    public static final int       PLAPOL2_CNT_MN = INTMIN;
    public static final int       PLAPOL2_CNT_MX = INTMAX;
    public static final int       PLATAX_CNT_MN = INTMIN;
    public static final int       PLATAX_CNT_MX = INTMAX;
    public static final int       PLACHL_CNT_MN = INTMIN;
    public static final int       PLACHL_CNT_MX = INTMAX;
    public static final int       TISPHY_CNT_MN = INTMIN;
    public static final int       TISPHY_CNT_MX = INTMAX;
    public static final int       TISPES_CNT_MN = INTMIN;
    public static final int       TISPES_CNT_MX = INTMAX;
    public static final int       TISPOL1_CNT_MN = INTMIN;
    public static final int       TISPOL1_CNT_MX = INTMAX;
    public static final int       TISPOL2_CNT_MN = INTMIN;
    public static final int       TISPOL2_CNT_MX = INTMAX;
    public static final int       TISANIMAL_CNT_MN = INTMIN;
    public static final int       TISANIMAL_CNT_MX = INTMAX;
    public static final int       WEATHER_CNT_MN = INTMIN;
    public static final int       WEATHER_CNT_MX = INTMAX;
    public static final int       WATCURRENTS_CNT_MN = INTMIN;
    public static final int       WATCURRENTS_CNT_MX = INTMAX;
    public static final int       WATOSD_CNT_MN = INTMIN;
    public static final int       WATOSD_CNT_MX = INTMAX;
    public static final int       WATCTD_CNT_MN = INTMIN;
    public static final int       WATCTD_CNT_MX = INTMAX;
    public static final int       WATXBT_CNT_MN = INTMIN;
    public static final int       WATXBT_CNT_MX = INTMAX;
    public static final int       WATMBT_CNT_MN = INTMIN;
    public static final int       WATMBT_CNT_MX = INTMAX;
    public static final int       WATPFL_CNT_MN = INTMIN;
    public static final int       WATPFL_CNT_MX = INTMAX;
    public static final int       WATPHY_STN_CNT_MN = INTMIN;
    public static final int       WATPHY_STN_CNT_MX = INTMAX;
    public static final int       WATOSD_STN_CNT_MN = INTMIN;
    public static final int       WATOSD_STN_CNT_MX = INTMAX;
    public static final int       WATCTD_STN_CNT_MN = INTMIN;
    public static final int       WATCTD_STN_CNT_MX = INTMAX;
    public static final int       WATXBT_STN_CNT_MN = INTMIN;
    public static final int       WATXBT_STN_CNT_MX = INTMAX;
    public static final int       WATMBT_STN_CNT_MN = INTMIN;
    public static final int       WATMBT_STN_CNT_MX = INTMAX;
    public static final int       WATPFL_STN_CNT_MN = INTMIN;
    public static final int       WATPFL_STN_CNT_MX = INTMAX;
    public static final int       WATNUT_STN_CNT_MN = INTMIN;
    public static final int       WATNUT_STN_CNT_MX = INTMAX;
    public static final int       WATPOL_STN_CNT_MN = INTMIN;
    public static final int       WATPOL_STN_CNT_MX = INTMAX;
    public static final int       WATCHEM_STN_CNT_MN = INTMIN;
    public static final int       WATCHEM_STN_CNT_MX = INTMAX;
    public static final int       WATCHL_STN_CNT_MN = INTMIN;
    public static final int       WATCHL_STN_CNT_MX = INTMAX;
    public static final int       SEDPHY_STN_CNT_MN = INTMIN;
    public static final int       SEDPHY_STN_CNT_MX = INTMAX;
    public static final int       SEDPES_STN_CNT_MN = INTMIN;
    public static final int       SEDPES_STN_CNT_MX = INTMAX;
    public static final int       SEDPOL_STN_CNT_MN = INTMIN;
    public static final int       SEDPOL_STN_CNT_MX = INTMAX;
    public static final int       SEDCHEM_STN_CNT_MN = INTMIN;
    public static final int       SEDCHEM_STN_CNT_MX = INTMAX;
    public static final int       SEDTAX_STN_CNT_MN = INTMIN;
    public static final int       SEDTAX_STN_CNT_MX = INTMAX;
    public static final int       PLAPHY_STN_CNT_MN = INTMIN;
    public static final int       PLAPHY_STN_CNT_MX = INTMAX;
    public static final int       PLAPES_STN_CNT_MN = INTMIN;
    public static final int       PLAPES_STN_CNT_MX = INTMAX;
    public static final int       PLAPOL_STN_CNT_MN = INTMIN;
    public static final int       PLAPOL_STN_CNT_MX = INTMAX;
    public static final int       PLATAX_STN_CNT_MN = INTMIN;
    public static final int       PLATAX_STN_CNT_MX = INTMAX;
    public static final int       PLACHL_STN_CNT_MN = INTMIN;
    public static final int       PLACHL_STN_CNT_MX = INTMAX;
    public static final int       TISPHY_STN_CNT_MN = INTMIN;
    public static final int       TISPHY_STN_CNT_MX = INTMAX;
    public static final int       TISPES_STN_CNT_MN = INTMIN;
    public static final int       TISPES_STN_CNT_MX = INTMAX;
    public static final int       TISPOL_STN_CNT_MN = INTMIN;
    public static final int       TISPOL_STN_CNT_MX = INTMAX;
    public static final int       TISANIMAL_STN_CNT_MN = INTMIN;
    public static final int       TISANIMAL_STN_CNT_MX = INTMAX;
    public static final int       WATCURRENTS_STN_CNT_MN = INTMIN;
    public static final int       WATCURRENTS_STN_CNT_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception stationCntOutOfBoundsException =
        new Exception ("'stationCnt' out of bounds: " +
            STATION_CNT_MN + " - " + STATION_CNT_MX);
    Exception watphyCntOutOfBoundsException =
        new Exception ("'watphyCnt' out of bounds: " +
            WATPHY_CNT_MN + " - " + WATPHY_CNT_MX);
    Exception watnutCntOutOfBoundsException =
        new Exception ("'watnutCnt' out of bounds: " +
            WATNUT_CNT_MN + " - " + WATNUT_CNT_MX);
    Exception watpol1CntOutOfBoundsException =
        new Exception ("'watpol1Cnt' out of bounds: " +
            WATPOL1_CNT_MN + " - " + WATPOL1_CNT_MX);
    Exception watpol2CntOutOfBoundsException =
        new Exception ("'watpol2Cnt' out of bounds: " +
            WATPOL2_CNT_MN + " - " + WATPOL2_CNT_MX);
    Exception watchem1CntOutOfBoundsException =
        new Exception ("'watchem1Cnt' out of bounds: " +
            WATCHEM1_CNT_MN + " - " + WATCHEM1_CNT_MX);
    Exception watchem2CntOutOfBoundsException =
        new Exception ("'watchem2Cnt' out of bounds: " +
            WATCHEM2_CNT_MN + " - " + WATCHEM2_CNT_MX);
    Exception watchlCntOutOfBoundsException =
        new Exception ("'watchlCnt' out of bounds: " +
            WATCHL_CNT_MN + " - " + WATCHL_CNT_MX);
    Exception sedphyCntOutOfBoundsException =
        new Exception ("'sedphyCnt' out of bounds: " +
            SEDPHY_CNT_MN + " - " + SEDPHY_CNT_MX);
    Exception sedpesCntOutOfBoundsException =
        new Exception ("'sedpesCnt' out of bounds: " +
            SEDPES_CNT_MN + " - " + SEDPES_CNT_MX);
    Exception sedpol1CntOutOfBoundsException =
        new Exception ("'sedpol1Cnt' out of bounds: " +
            SEDPOL1_CNT_MN + " - " + SEDPOL1_CNT_MX);
    Exception sedpol2CntOutOfBoundsException =
        new Exception ("'sedpol2Cnt' out of bounds: " +
            SEDPOL2_CNT_MN + " - " + SEDPOL2_CNT_MX);
    Exception sedchem1CntOutOfBoundsException =
        new Exception ("'sedchem1Cnt' out of bounds: " +
            SEDCHEM1_CNT_MN + " - " + SEDCHEM1_CNT_MX);
    Exception sedchem2CntOutOfBoundsException =
        new Exception ("'sedchem2Cnt' out of bounds: " +
            SEDCHEM2_CNT_MN + " - " + SEDCHEM2_CNT_MX);
    Exception sedtaxCntOutOfBoundsException =
        new Exception ("'sedtaxCnt' out of bounds: " +
            SEDTAX_CNT_MN + " - " + SEDTAX_CNT_MX);
    Exception plaphyCntOutOfBoundsException =
        new Exception ("'plaphyCnt' out of bounds: " +
            PLAPHY_CNT_MN + " - " + PLAPHY_CNT_MX);
    Exception plapesCntOutOfBoundsException =
        new Exception ("'plapesCnt' out of bounds: " +
            PLAPES_CNT_MN + " - " + PLAPES_CNT_MX);
    Exception plapol1CntOutOfBoundsException =
        new Exception ("'plapol1Cnt' out of bounds: " +
            PLAPOL1_CNT_MN + " - " + PLAPOL1_CNT_MX);
    Exception plapol2CntOutOfBoundsException =
        new Exception ("'plapol2Cnt' out of bounds: " +
            PLAPOL2_CNT_MN + " - " + PLAPOL2_CNT_MX);
    Exception plataxCntOutOfBoundsException =
        new Exception ("'plataxCnt' out of bounds: " +
            PLATAX_CNT_MN + " - " + PLATAX_CNT_MX);
    Exception plachlCntOutOfBoundsException =
        new Exception ("'plachlCnt' out of bounds: " +
            PLACHL_CNT_MN + " - " + PLACHL_CNT_MX);
    Exception tisphyCntOutOfBoundsException =
        new Exception ("'tisphyCnt' out of bounds: " +
            TISPHY_CNT_MN + " - " + TISPHY_CNT_MX);
    Exception tispesCntOutOfBoundsException =
        new Exception ("'tispesCnt' out of bounds: " +
            TISPES_CNT_MN + " - " + TISPES_CNT_MX);
    Exception tispol1CntOutOfBoundsException =
        new Exception ("'tispol1Cnt' out of bounds: " +
            TISPOL1_CNT_MN + " - " + TISPOL1_CNT_MX);
    Exception tispol2CntOutOfBoundsException =
        new Exception ("'tispol2Cnt' out of bounds: " +
            TISPOL2_CNT_MN + " - " + TISPOL2_CNT_MX);
    Exception tisanimalCntOutOfBoundsException =
        new Exception ("'tisanimalCnt' out of bounds: " +
            TISANIMAL_CNT_MN + " - " + TISANIMAL_CNT_MX);
    Exception weatherCntOutOfBoundsException =
        new Exception ("'weatherCnt' out of bounds: " +
            WEATHER_CNT_MN + " - " + WEATHER_CNT_MX);
    Exception watcurrentsCntOutOfBoundsException =
        new Exception ("'watcurrentsCnt' out of bounds: " +
            WATCURRENTS_CNT_MN + " - " + WATCURRENTS_CNT_MX);
    Exception watosdCntOutOfBoundsException =
        new Exception ("'watosdCnt' out of bounds: " +
            WATOSD_CNT_MN + " - " + WATOSD_CNT_MX);
    Exception watctdCntOutOfBoundsException =
        new Exception ("'watctdCnt' out of bounds: " +
            WATCTD_CNT_MN + " - " + WATCTD_CNT_MX);
    Exception watxbtCntOutOfBoundsException =
        new Exception ("'watxbtCnt' out of bounds: " +
            WATXBT_CNT_MN + " - " + WATXBT_CNT_MX);
    Exception watmbtCntOutOfBoundsException =
        new Exception ("'watmbtCnt' out of bounds: " +
            WATMBT_CNT_MN + " - " + WATMBT_CNT_MX);
    Exception watpflCntOutOfBoundsException =
        new Exception ("'watpflCnt' out of bounds: " +
            WATPFL_CNT_MN + " - " + WATPFL_CNT_MX);
    Exception watphyStnCntOutOfBoundsException =
        new Exception ("'watphyStnCnt' out of bounds: " +
            WATPHY_STN_CNT_MN + " - " + WATPHY_STN_CNT_MX);
    Exception watosdStnCntOutOfBoundsException =
        new Exception ("'watosdStnCnt' out of bounds: " +
            WATOSD_STN_CNT_MN + " - " + WATOSD_STN_CNT_MX);
    Exception watctdStnCntOutOfBoundsException =
        new Exception ("'watctdStnCnt' out of bounds: " +
            WATCTD_STN_CNT_MN + " - " + WATCTD_STN_CNT_MX);
    Exception watxbtStnCntOutOfBoundsException =
        new Exception ("'watxbtStnCnt' out of bounds: " +
            WATXBT_STN_CNT_MN + " - " + WATXBT_STN_CNT_MX);
    Exception watmbtStnCntOutOfBoundsException =
        new Exception ("'watmbtStnCnt' out of bounds: " +
            WATMBT_STN_CNT_MN + " - " + WATMBT_STN_CNT_MX);
    Exception watpflStnCntOutOfBoundsException =
        new Exception ("'watpflStnCnt' out of bounds: " +
            WATPFL_STN_CNT_MN + " - " + WATPFL_STN_CNT_MX);
    Exception watnutStnCntOutOfBoundsException =
        new Exception ("'watnutStnCnt' out of bounds: " +
            WATNUT_STN_CNT_MN + " - " + WATNUT_STN_CNT_MX);
    Exception watpolStnCntOutOfBoundsException =
        new Exception ("'watpolStnCnt' out of bounds: " +
            WATPOL_STN_CNT_MN + " - " + WATPOL_STN_CNT_MX);
    Exception watchemStnCntOutOfBoundsException =
        new Exception ("'watchemStnCnt' out of bounds: " +
            WATCHEM_STN_CNT_MN + " - " + WATCHEM_STN_CNT_MX);
    Exception watchlStnCntOutOfBoundsException =
        new Exception ("'watchlStnCnt' out of bounds: " +
            WATCHL_STN_CNT_MN + " - " + WATCHL_STN_CNT_MX);
    Exception sedphyStnCntOutOfBoundsException =
        new Exception ("'sedphyStnCnt' out of bounds: " +
            SEDPHY_STN_CNT_MN + " - " + SEDPHY_STN_CNT_MX);
    Exception sedpesStnCntOutOfBoundsException =
        new Exception ("'sedpesStnCnt' out of bounds: " +
            SEDPES_STN_CNT_MN + " - " + SEDPES_STN_CNT_MX);
    Exception sedpolStnCntOutOfBoundsException =
        new Exception ("'sedpolStnCnt' out of bounds: " +
            SEDPOL_STN_CNT_MN + " - " + SEDPOL_STN_CNT_MX);
    Exception sedchemStnCntOutOfBoundsException =
        new Exception ("'sedchemStnCnt' out of bounds: " +
            SEDCHEM_STN_CNT_MN + " - " + SEDCHEM_STN_CNT_MX);
    Exception sedtaxStnCntOutOfBoundsException =
        new Exception ("'sedtaxStnCnt' out of bounds: " +
            SEDTAX_STN_CNT_MN + " - " + SEDTAX_STN_CNT_MX);
    Exception plaphyStnCntOutOfBoundsException =
        new Exception ("'plaphyStnCnt' out of bounds: " +
            PLAPHY_STN_CNT_MN + " - " + PLAPHY_STN_CNT_MX);
    Exception plapesStnCntOutOfBoundsException =
        new Exception ("'plapesStnCnt' out of bounds: " +
            PLAPES_STN_CNT_MN + " - " + PLAPES_STN_CNT_MX);
    Exception plapolStnCntOutOfBoundsException =
        new Exception ("'plapolStnCnt' out of bounds: " +
            PLAPOL_STN_CNT_MN + " - " + PLAPOL_STN_CNT_MX);
    Exception plataxStnCntOutOfBoundsException =
        new Exception ("'plataxStnCnt' out of bounds: " +
            PLATAX_STN_CNT_MN + " - " + PLATAX_STN_CNT_MX);
    Exception plachlStnCntOutOfBoundsException =
        new Exception ("'plachlStnCnt' out of bounds: " +
            PLACHL_STN_CNT_MN + " - " + PLACHL_STN_CNT_MX);
    Exception tisphyStnCntOutOfBoundsException =
        new Exception ("'tisphyStnCnt' out of bounds: " +
            TISPHY_STN_CNT_MN + " - " + TISPHY_STN_CNT_MX);
    Exception tispesStnCntOutOfBoundsException =
        new Exception ("'tispesStnCnt' out of bounds: " +
            TISPES_STN_CNT_MN + " - " + TISPES_STN_CNT_MX);
    Exception tispolStnCntOutOfBoundsException =
        new Exception ("'tispolStnCnt' out of bounds: " +
            TISPOL_STN_CNT_MN + " - " + TISPOL_STN_CNT_MX);
    Exception tisanimalStnCntOutOfBoundsException =
        new Exception ("'tisanimalStnCnt' out of bounds: " +
            TISANIMAL_STN_CNT_MN + " - " + TISANIMAL_STN_CNT_MX);
    Exception watcurrentsStnCntOutOfBoundsException =
        new Exception ("'watcurrentsStnCnt' out of bounds: " +
            WATCURRENTS_STN_CNT_MN + " - " + WATCURRENTS_STN_CNT_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnInvStats() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnInvStats constructor 1"); // debug
    } // MrnInvStats constructor

    /**
     * Instantiate a MrnInvStats object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     */
    public MrnInvStats(
            String surveyId) {
        this();
        setSurveyId          (surveyId         );
        if (dbg) System.out.println ("<br>in MrnInvStats constructor 2"); // debug
    } // MrnInvStats constructor

    /**
     * Instantiate a MrnInvStats object and initialize the instance variables.
     * @param surveyId           The surveyId          (String)
     * @param stationCnt         The stationCnt        (int)
     * @param watphyCnt          The watphyCnt         (int)
     * @param watnutCnt          The watnutCnt         (int)
     * @param watpol1Cnt         The watpol1Cnt        (int)
     * @param watpol2Cnt         The watpol2Cnt        (int)
     * @param watchem1Cnt        The watchem1Cnt       (int)
     * @param watchem2Cnt        The watchem2Cnt       (int)
     * @param watchlCnt          The watchlCnt         (int)
     * @param sedphyCnt          The sedphyCnt         (int)
     * @param sedpesCnt          The sedpesCnt         (int)
     * @param sedpol1Cnt         The sedpol1Cnt        (int)
     * @param sedpol2Cnt         The sedpol2Cnt        (int)
     * @param sedchem1Cnt        The sedchem1Cnt       (int)
     * @param sedchem2Cnt        The sedchem2Cnt       (int)
     * @param sedtaxCnt          The sedtaxCnt         (int)
     * @param plaphyCnt          The plaphyCnt         (int)
     * @param plapesCnt          The plapesCnt         (int)
     * @param plapol1Cnt         The plapol1Cnt        (int)
     * @param plapol2Cnt         The plapol2Cnt        (int)
     * @param plataxCnt          The plataxCnt         (int)
     * @param plachlCnt          The plachlCnt         (int)
     * @param tisphyCnt          The tisphyCnt         (int)
     * @param tispesCnt          The tispesCnt         (int)
     * @param tispol1Cnt         The tispol1Cnt        (int)
     * @param tispol2Cnt         The tispol2Cnt        (int)
     * @param tisanimalCnt       The tisanimalCnt      (int)
     * @param weatherCnt         The weatherCnt        (int)
     * @param watcurrentsCnt     The watcurrentsCnt    (int)
     * @param watosdCnt          The watosdCnt         (int)
     * @param watctdCnt          The watctdCnt         (int)
     * @param watxbtCnt          The watxbtCnt         (int)
     * @param watmbtCnt          The watmbtCnt         (int)
     * @param watpflCnt          The watpflCnt         (int)
     * @param watphyStnCnt       The watphyStnCnt      (int)
     * @param watosdStnCnt       The watosdStnCnt      (int)
     * @param watctdStnCnt       The watctdStnCnt      (int)
     * @param watxbtStnCnt       The watxbtStnCnt      (int)
     * @param watmbtStnCnt       The watmbtStnCnt      (int)
     * @param watpflStnCnt       The watpflStnCnt      (int)
     * @param watnutStnCnt       The watnutStnCnt      (int)
     * @param watpolStnCnt       The watpolStnCnt      (int)
     * @param watchemStnCnt      The watchemStnCnt     (int)
     * @param watchlStnCnt       The watchlStnCnt      (int)
     * @param sedphyStnCnt       The sedphyStnCnt      (int)
     * @param sedpesStnCnt       The sedpesStnCnt      (int)
     * @param sedpolStnCnt       The sedpolStnCnt      (int)
     * @param sedchemStnCnt      The sedchemStnCnt     (int)
     * @param sedtaxStnCnt       The sedtaxStnCnt      (int)
     * @param plaphyStnCnt       The plaphyStnCnt      (int)
     * @param plapesStnCnt       The plapesStnCnt      (int)
     * @param plapolStnCnt       The plapolStnCnt      (int)
     * @param plataxStnCnt       The plataxStnCnt      (int)
     * @param plachlStnCnt       The plachlStnCnt      (int)
     * @param tisphyStnCnt       The tisphyStnCnt      (int)
     * @param tispesStnCnt       The tispesStnCnt      (int)
     * @param tispolStnCnt       The tispolStnCnt      (int)
     * @param tisanimalStnCnt    The tisanimalStnCnt   (int)
     * @param watcurrentsStnCnt  The watcurrentsStnCnt (int)
     * @return A MrnInvStats object
     */
    public MrnInvStats(
            String surveyId,
            int    stationCnt,
            int    watphyCnt,
            int    watnutCnt,
            int    watpol1Cnt,
            int    watpol2Cnt,
            int    watchem1Cnt,
            int    watchem2Cnt,
            int    watchlCnt,
            int    sedphyCnt,
            int    sedpesCnt,
            int    sedpol1Cnt,
            int    sedpol2Cnt,
            int    sedchem1Cnt,
            int    sedchem2Cnt,
            int    sedtaxCnt,
            int    plaphyCnt,
            int    plapesCnt,
            int    plapol1Cnt,
            int    plapol2Cnt,
            int    plataxCnt,
            int    plachlCnt,
            int    tisphyCnt,
            int    tispesCnt,
            int    tispol1Cnt,
            int    tispol2Cnt,
            int    tisanimalCnt,
            int    weatherCnt,
            int    watcurrentsCnt,
            int    watosdCnt,
            int    watctdCnt,
            int    watxbtCnt,
            int    watmbtCnt,
            int    watpflCnt,
            int    watphyStnCnt,
            int    watosdStnCnt,
            int    watctdStnCnt,
            int    watxbtStnCnt,
            int    watmbtStnCnt,
            int    watpflStnCnt,
            int    watnutStnCnt,
            int    watpolStnCnt,
            int    watchemStnCnt,
            int    watchlStnCnt,
            int    sedphyStnCnt,
            int    sedpesStnCnt,
            int    sedpolStnCnt,
            int    sedchemStnCnt,
            int    sedtaxStnCnt,
            int    plaphyStnCnt,
            int    plapesStnCnt,
            int    plapolStnCnt,
            int    plataxStnCnt,
            int    plachlStnCnt,
            int    tisphyStnCnt,
            int    tispesStnCnt,
            int    tispolStnCnt,
            int    tisanimalStnCnt,
            int    watcurrentsStnCnt) {
        this();
        setSurveyId          (surveyId         );
        setStationCnt        (stationCnt       );
        setWatphyCnt         (watphyCnt        );
        setWatnutCnt         (watnutCnt        );
        setWatpol1Cnt        (watpol1Cnt       );
        setWatpol2Cnt        (watpol2Cnt       );
        setWatchem1Cnt       (watchem1Cnt      );
        setWatchem2Cnt       (watchem2Cnt      );
        setWatchlCnt         (watchlCnt        );
        setSedphyCnt         (sedphyCnt        );
        setSedpesCnt         (sedpesCnt        );
        setSedpol1Cnt        (sedpol1Cnt       );
        setSedpol2Cnt        (sedpol2Cnt       );
        setSedchem1Cnt       (sedchem1Cnt      );
        setSedchem2Cnt       (sedchem2Cnt      );
        setSedtaxCnt         (sedtaxCnt        );
        setPlaphyCnt         (plaphyCnt        );
        setPlapesCnt         (plapesCnt        );
        setPlapol1Cnt        (plapol1Cnt       );
        setPlapol2Cnt        (plapol2Cnt       );
        setPlataxCnt         (plataxCnt        );
        setPlachlCnt         (plachlCnt        );
        setTisphyCnt         (tisphyCnt        );
        setTispesCnt         (tispesCnt        );
        setTispol1Cnt        (tispol1Cnt       );
        setTispol2Cnt        (tispol2Cnt       );
        setTisanimalCnt      (tisanimalCnt     );
        setWeatherCnt        (weatherCnt       );
        setWatcurrentsCnt    (watcurrentsCnt   );
        setWatosdCnt         (watosdCnt        );
        setWatctdCnt         (watctdCnt        );
        setWatxbtCnt         (watxbtCnt        );
        setWatmbtCnt         (watmbtCnt        );
        setWatpflCnt         (watpflCnt        );
        setWatphyStnCnt      (watphyStnCnt     );
        setWatosdStnCnt      (watosdStnCnt     );
        setWatctdStnCnt      (watctdStnCnt     );
        setWatxbtStnCnt      (watxbtStnCnt     );
        setWatmbtStnCnt      (watmbtStnCnt     );
        setWatpflStnCnt      (watpflStnCnt     );
        setWatnutStnCnt      (watnutStnCnt     );
        setWatpolStnCnt      (watpolStnCnt     );
        setWatchemStnCnt     (watchemStnCnt    );
        setWatchlStnCnt      (watchlStnCnt     );
        setSedphyStnCnt      (sedphyStnCnt     );
        setSedpesStnCnt      (sedpesStnCnt     );
        setSedpolStnCnt      (sedpolStnCnt     );
        setSedchemStnCnt     (sedchemStnCnt    );
        setSedtaxStnCnt      (sedtaxStnCnt     );
        setPlaphyStnCnt      (plaphyStnCnt     );
        setPlapesStnCnt      (plapesStnCnt     );
        setPlapolStnCnt      (plapolStnCnt     );
        setPlataxStnCnt      (plataxStnCnt     );
        setPlachlStnCnt      (plachlStnCnt     );
        setTisphyStnCnt      (tisphyStnCnt     );
        setTispesStnCnt      (tispesStnCnt     );
        setTispolStnCnt      (tispolStnCnt     );
        setTisanimalStnCnt   (tisanimalStnCnt  );
        setWatcurrentsStnCnt (watcurrentsStnCnt);
        if (dbg) System.out.println ("<br>in MrnInvStats constructor 3"); // debug
    } // MrnInvStats constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSurveyId          (CHARNULL);
        setStationCnt        (INTNULL );
        setWatphyCnt         (INTNULL );
        setWatnutCnt         (INTNULL );
        setWatpol1Cnt        (INTNULL );
        setWatpol2Cnt        (INTNULL );
        setWatchem1Cnt       (INTNULL );
        setWatchem2Cnt       (INTNULL );
        setWatchlCnt         (INTNULL );
        setSedphyCnt         (INTNULL );
        setSedpesCnt         (INTNULL );
        setSedpol1Cnt        (INTNULL );
        setSedpol2Cnt        (INTNULL );
        setSedchem1Cnt       (INTNULL );
        setSedchem2Cnt       (INTNULL );
        setSedtaxCnt         (INTNULL );
        setPlaphyCnt         (INTNULL );
        setPlapesCnt         (INTNULL );
        setPlapol1Cnt        (INTNULL );
        setPlapol2Cnt        (INTNULL );
        setPlataxCnt         (INTNULL );
        setPlachlCnt         (INTNULL );
        setTisphyCnt         (INTNULL );
        setTispesCnt         (INTNULL );
        setTispol1Cnt        (INTNULL );
        setTispol2Cnt        (INTNULL );
        setTisanimalCnt      (INTNULL );
        setWeatherCnt        (INTNULL );
        setWatcurrentsCnt    (INTNULL );
        setWatosdCnt         (INTNULL );
        setWatctdCnt         (INTNULL );
        setWatxbtCnt         (INTNULL );
        setWatmbtCnt         (INTNULL );
        setWatpflCnt         (INTNULL );
        setWatphyStnCnt      (INTNULL );
        setWatosdStnCnt      (INTNULL );
        setWatctdStnCnt      (INTNULL );
        setWatxbtStnCnt      (INTNULL );
        setWatmbtStnCnt      (INTNULL );
        setWatpflStnCnt      (INTNULL );
        setWatnutStnCnt      (INTNULL );
        setWatpolStnCnt      (INTNULL );
        setWatchemStnCnt     (INTNULL );
        setWatchlStnCnt      (INTNULL );
        setSedphyStnCnt      (INTNULL );
        setSedpesStnCnt      (INTNULL );
        setSedpolStnCnt      (INTNULL );
        setSedchemStnCnt     (INTNULL );
        setSedtaxStnCnt      (INTNULL );
        setPlaphyStnCnt      (INTNULL );
        setPlapesStnCnt      (INTNULL );
        setPlapolStnCnt      (INTNULL );
        setPlataxStnCnt      (INTNULL );
        setPlachlStnCnt      (INTNULL );
        setTisphyStnCnt      (INTNULL );
        setTispesStnCnt      (INTNULL );
        setTispolStnCnt      (INTNULL );
        setTisanimalStnCnt   (INTNULL );
        setWatcurrentsStnCnt (INTNULL );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'surveyId' class variable
     * @param  surveyId (String)
     */
    public int setSurveyId(String surveyId) {
        try {
            this.surveyId = surveyId;
            if (this.surveyId != CHARNULL) {
                this.surveyId = stripCRLF(this.surveyId.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>surveyId = " + this.surveyId);
        } catch (Exception e) {
            setSurveyIdError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return surveyIdError;
    } // method setSurveyId

    /**
     * Called when an exception has occured
     * @param  surveyId (String)
     */
    private void setSurveyIdError (String surveyId, Exception e, int error) {
        this.surveyId = surveyId;
        surveyIdErrorMessage = e.toString();
        surveyIdError = error;
    } // method setSurveyIdError


    /**
     * Set the 'stationCnt' class variable
     * @param  stationCnt (int)
     */
    public int setStationCnt(int stationCnt) {
        try {
            if ( ((stationCnt == INTNULL) ||
                  (stationCnt == INTNULL2)) ||
                !((stationCnt < STATION_CNT_MN) ||
                  (stationCnt > STATION_CNT_MX)) ) {
                this.stationCnt = stationCnt;
                stationCntError = ERROR_NORMAL;
            } else {
                throw stationCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStationCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return stationCntError;
    } // method setStationCnt

    /**
     * Set the 'stationCnt' class variable
     * @param  stationCnt (Integer)
     */
    public int setStationCnt(Integer stationCnt) {
        try {
            setStationCnt(stationCnt.intValue());
        } catch (Exception e) {
            setStationCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return stationCntError;
    } // method setStationCnt

    /**
     * Set the 'stationCnt' class variable
     * @param  stationCnt (Float)
     */
    public int setStationCnt(Float stationCnt) {
        try {
            if (stationCnt.floatValue() == FLOATNULL) {
                setStationCnt(INTNULL);
            } else {
                setStationCnt(stationCnt.intValue());
            } // if (stationCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setStationCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return stationCntError;
    } // method setStationCnt

    /**
     * Set the 'stationCnt' class variable
     * @param  stationCnt (String)
     */
    public int setStationCnt(String stationCnt) {
        try {
            setStationCnt(new Integer(stationCnt).intValue());
        } catch (Exception e) {
            setStationCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return stationCntError;
    } // method setStationCnt

    /**
     * Called when an exception has occured
     * @param  stationCnt (String)
     */
    private void setStationCntError (int stationCnt, Exception e, int error) {
        this.stationCnt = stationCnt;
        stationCntErrorMessage = e.toString();
        stationCntError = error;
    } // method setStationCntError


    /**
     * Set the 'watphyCnt' class variable
     * @param  watphyCnt (int)
     */
    public int setWatphyCnt(int watphyCnt) {
        try {
            if ( ((watphyCnt == INTNULL) ||
                  (watphyCnt == INTNULL2)) ||
                !((watphyCnt < WATPHY_CNT_MN) ||
                  (watphyCnt > WATPHY_CNT_MX)) ) {
                this.watphyCnt = watphyCnt;
                watphyCntError = ERROR_NORMAL;
            } else {
                throw watphyCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatphyCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watphyCntError;
    } // method setWatphyCnt

    /**
     * Set the 'watphyCnt' class variable
     * @param  watphyCnt (Integer)
     */
    public int setWatphyCnt(Integer watphyCnt) {
        try {
            setWatphyCnt(watphyCnt.intValue());
        } catch (Exception e) {
            setWatphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCntError;
    } // method setWatphyCnt

    /**
     * Set the 'watphyCnt' class variable
     * @param  watphyCnt (Float)
     */
    public int setWatphyCnt(Float watphyCnt) {
        try {
            if (watphyCnt.floatValue() == FLOATNULL) {
                setWatphyCnt(INTNULL);
            } else {
                setWatphyCnt(watphyCnt.intValue());
            } // if (watphyCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCntError;
    } // method setWatphyCnt

    /**
     * Set the 'watphyCnt' class variable
     * @param  watphyCnt (String)
     */
    public int setWatphyCnt(String watphyCnt) {
        try {
            setWatphyCnt(new Integer(watphyCnt).intValue());
        } catch (Exception e) {
            setWatphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCntError;
    } // method setWatphyCnt

    /**
     * Called when an exception has occured
     * @param  watphyCnt (String)
     */
    private void setWatphyCntError (int watphyCnt, Exception e, int error) {
        this.watphyCnt = watphyCnt;
        watphyCntErrorMessage = e.toString();
        watphyCntError = error;
    } // method setWatphyCntError


    /**
     * Set the 'watnutCnt' class variable
     * @param  watnutCnt (int)
     */
    public int setWatnutCnt(int watnutCnt) {
        try {
            if ( ((watnutCnt == INTNULL) ||
                  (watnutCnt == INTNULL2)) ||
                !((watnutCnt < WATNUT_CNT_MN) ||
                  (watnutCnt > WATNUT_CNT_MX)) ) {
                this.watnutCnt = watnutCnt;
                watnutCntError = ERROR_NORMAL;
            } else {
                throw watnutCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatnutCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watnutCntError;
    } // method setWatnutCnt

    /**
     * Set the 'watnutCnt' class variable
     * @param  watnutCnt (Integer)
     */
    public int setWatnutCnt(Integer watnutCnt) {
        try {
            setWatnutCnt(watnutCnt.intValue());
        } catch (Exception e) {
            setWatnutCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watnutCntError;
    } // method setWatnutCnt

    /**
     * Set the 'watnutCnt' class variable
     * @param  watnutCnt (Float)
     */
    public int setWatnutCnt(Float watnutCnt) {
        try {
            if (watnutCnt.floatValue() == FLOATNULL) {
                setWatnutCnt(INTNULL);
            } else {
                setWatnutCnt(watnutCnt.intValue());
            } // if (watnutCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatnutCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watnutCntError;
    } // method setWatnutCnt

    /**
     * Set the 'watnutCnt' class variable
     * @param  watnutCnt (String)
     */
    public int setWatnutCnt(String watnutCnt) {
        try {
            setWatnutCnt(new Integer(watnutCnt).intValue());
        } catch (Exception e) {
            setWatnutCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watnutCntError;
    } // method setWatnutCnt

    /**
     * Called when an exception has occured
     * @param  watnutCnt (String)
     */
    private void setWatnutCntError (int watnutCnt, Exception e, int error) {
        this.watnutCnt = watnutCnt;
        watnutCntErrorMessage = e.toString();
        watnutCntError = error;
    } // method setWatnutCntError


    /**
     * Set the 'watpol1Cnt' class variable
     * @param  watpol1Cnt (int)
     */
    public int setWatpol1Cnt(int watpol1Cnt) {
        try {
            if ( ((watpol1Cnt == INTNULL) ||
                  (watpol1Cnt == INTNULL2)) ||
                !((watpol1Cnt < WATPOL1_CNT_MN) ||
                  (watpol1Cnt > WATPOL1_CNT_MX)) ) {
                this.watpol1Cnt = watpol1Cnt;
                watpol1CntError = ERROR_NORMAL;
            } else {
                throw watpol1CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatpol1CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watpol1CntError;
    } // method setWatpol1Cnt

    /**
     * Set the 'watpol1Cnt' class variable
     * @param  watpol1Cnt (Integer)
     */
    public int setWatpol1Cnt(Integer watpol1Cnt) {
        try {
            setWatpol1Cnt(watpol1Cnt.intValue());
        } catch (Exception e) {
            setWatpol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpol1CntError;
    } // method setWatpol1Cnt

    /**
     * Set the 'watpol1Cnt' class variable
     * @param  watpol1Cnt (Float)
     */
    public int setWatpol1Cnt(Float watpol1Cnt) {
        try {
            if (watpol1Cnt.floatValue() == FLOATNULL) {
                setWatpol1Cnt(INTNULL);
            } else {
                setWatpol1Cnt(watpol1Cnt.intValue());
            } // if (watpol1Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatpol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpol1CntError;
    } // method setWatpol1Cnt

    /**
     * Set the 'watpol1Cnt' class variable
     * @param  watpol1Cnt (String)
     */
    public int setWatpol1Cnt(String watpol1Cnt) {
        try {
            setWatpol1Cnt(new Integer(watpol1Cnt).intValue());
        } catch (Exception e) {
            setWatpol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpol1CntError;
    } // method setWatpol1Cnt

    /**
     * Called when an exception has occured
     * @param  watpol1Cnt (String)
     */
    private void setWatpol1CntError (int watpol1Cnt, Exception e, int error) {
        this.watpol1Cnt = watpol1Cnt;
        watpol1CntErrorMessage = e.toString();
        watpol1CntError = error;
    } // method setWatpol1CntError


    /**
     * Set the 'watpol2Cnt' class variable
     * @param  watpol2Cnt (int)
     */
    public int setWatpol2Cnt(int watpol2Cnt) {
        try {
            if ( ((watpol2Cnt == INTNULL) ||
                  (watpol2Cnt == INTNULL2)) ||
                !((watpol2Cnt < WATPOL2_CNT_MN) ||
                  (watpol2Cnt > WATPOL2_CNT_MX)) ) {
                this.watpol2Cnt = watpol2Cnt;
                watpol2CntError = ERROR_NORMAL;
            } else {
                throw watpol2CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatpol2CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watpol2CntError;
    } // method setWatpol2Cnt

    /**
     * Set the 'watpol2Cnt' class variable
     * @param  watpol2Cnt (Integer)
     */
    public int setWatpol2Cnt(Integer watpol2Cnt) {
        try {
            setWatpol2Cnt(watpol2Cnt.intValue());
        } catch (Exception e) {
            setWatpol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpol2CntError;
    } // method setWatpol2Cnt

    /**
     * Set the 'watpol2Cnt' class variable
     * @param  watpol2Cnt (Float)
     */
    public int setWatpol2Cnt(Float watpol2Cnt) {
        try {
            if (watpol2Cnt.floatValue() == FLOATNULL) {
                setWatpol2Cnt(INTNULL);
            } else {
                setWatpol2Cnt(watpol2Cnt.intValue());
            } // if (watpol2Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatpol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpol2CntError;
    } // method setWatpol2Cnt

    /**
     * Set the 'watpol2Cnt' class variable
     * @param  watpol2Cnt (String)
     */
    public int setWatpol2Cnt(String watpol2Cnt) {
        try {
            setWatpol2Cnt(new Integer(watpol2Cnt).intValue());
        } catch (Exception e) {
            setWatpol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpol2CntError;
    } // method setWatpol2Cnt

    /**
     * Called when an exception has occured
     * @param  watpol2Cnt (String)
     */
    private void setWatpol2CntError (int watpol2Cnt, Exception e, int error) {
        this.watpol2Cnt = watpol2Cnt;
        watpol2CntErrorMessage = e.toString();
        watpol2CntError = error;
    } // method setWatpol2CntError


    /**
     * Set the 'watchem1Cnt' class variable
     * @param  watchem1Cnt (int)
     */
    public int setWatchem1Cnt(int watchem1Cnt) {
        try {
            if ( ((watchem1Cnt == INTNULL) ||
                  (watchem1Cnt == INTNULL2)) ||
                !((watchem1Cnt < WATCHEM1_CNT_MN) ||
                  (watchem1Cnt > WATCHEM1_CNT_MX)) ) {
                this.watchem1Cnt = watchem1Cnt;
                watchem1CntError = ERROR_NORMAL;
            } else {
                throw watchem1CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatchem1CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watchem1CntError;
    } // method setWatchem1Cnt

    /**
     * Set the 'watchem1Cnt' class variable
     * @param  watchem1Cnt (Integer)
     */
    public int setWatchem1Cnt(Integer watchem1Cnt) {
        try {
            setWatchem1Cnt(watchem1Cnt.intValue());
        } catch (Exception e) {
            setWatchem1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchem1CntError;
    } // method setWatchem1Cnt

    /**
     * Set the 'watchem1Cnt' class variable
     * @param  watchem1Cnt (Float)
     */
    public int setWatchem1Cnt(Float watchem1Cnt) {
        try {
            if (watchem1Cnt.floatValue() == FLOATNULL) {
                setWatchem1Cnt(INTNULL);
            } else {
                setWatchem1Cnt(watchem1Cnt.intValue());
            } // if (watchem1Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatchem1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchem1CntError;
    } // method setWatchem1Cnt

    /**
     * Set the 'watchem1Cnt' class variable
     * @param  watchem1Cnt (String)
     */
    public int setWatchem1Cnt(String watchem1Cnt) {
        try {
            setWatchem1Cnt(new Integer(watchem1Cnt).intValue());
        } catch (Exception e) {
            setWatchem1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchem1CntError;
    } // method setWatchem1Cnt

    /**
     * Called when an exception has occured
     * @param  watchem1Cnt (String)
     */
    private void setWatchem1CntError (int watchem1Cnt, Exception e, int error) {
        this.watchem1Cnt = watchem1Cnt;
        watchem1CntErrorMessage = e.toString();
        watchem1CntError = error;
    } // method setWatchem1CntError


    /**
     * Set the 'watchem2Cnt' class variable
     * @param  watchem2Cnt (int)
     */
    public int setWatchem2Cnt(int watchem2Cnt) {
        try {
            if ( ((watchem2Cnt == INTNULL) ||
                  (watchem2Cnt == INTNULL2)) ||
                !((watchem2Cnt < WATCHEM2_CNT_MN) ||
                  (watchem2Cnt > WATCHEM2_CNT_MX)) ) {
                this.watchem2Cnt = watchem2Cnt;
                watchem2CntError = ERROR_NORMAL;
            } else {
                throw watchem2CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatchem2CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watchem2CntError;
    } // method setWatchem2Cnt

    /**
     * Set the 'watchem2Cnt' class variable
     * @param  watchem2Cnt (Integer)
     */
    public int setWatchem2Cnt(Integer watchem2Cnt) {
        try {
            setWatchem2Cnt(watchem2Cnt.intValue());
        } catch (Exception e) {
            setWatchem2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchem2CntError;
    } // method setWatchem2Cnt

    /**
     * Set the 'watchem2Cnt' class variable
     * @param  watchem2Cnt (Float)
     */
    public int setWatchem2Cnt(Float watchem2Cnt) {
        try {
            if (watchem2Cnt.floatValue() == FLOATNULL) {
                setWatchem2Cnt(INTNULL);
            } else {
                setWatchem2Cnt(watchem2Cnt.intValue());
            } // if (watchem2Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatchem2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchem2CntError;
    } // method setWatchem2Cnt

    /**
     * Set the 'watchem2Cnt' class variable
     * @param  watchem2Cnt (String)
     */
    public int setWatchem2Cnt(String watchem2Cnt) {
        try {
            setWatchem2Cnt(new Integer(watchem2Cnt).intValue());
        } catch (Exception e) {
            setWatchem2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchem2CntError;
    } // method setWatchem2Cnt

    /**
     * Called when an exception has occured
     * @param  watchem2Cnt (String)
     */
    private void setWatchem2CntError (int watchem2Cnt, Exception e, int error) {
        this.watchem2Cnt = watchem2Cnt;
        watchem2CntErrorMessage = e.toString();
        watchem2CntError = error;
    } // method setWatchem2CntError


    /**
     * Set the 'watchlCnt' class variable
     * @param  watchlCnt (int)
     */
    public int setWatchlCnt(int watchlCnt) {
        try {
            if ( ((watchlCnt == INTNULL) ||
                  (watchlCnt == INTNULL2)) ||
                !((watchlCnt < WATCHL_CNT_MN) ||
                  (watchlCnt > WATCHL_CNT_MX)) ) {
                this.watchlCnt = watchlCnt;
                watchlCntError = ERROR_NORMAL;
            } else {
                throw watchlCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatchlCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watchlCntError;
    } // method setWatchlCnt

    /**
     * Set the 'watchlCnt' class variable
     * @param  watchlCnt (Integer)
     */
    public int setWatchlCnt(Integer watchlCnt) {
        try {
            setWatchlCnt(watchlCnt.intValue());
        } catch (Exception e) {
            setWatchlCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchlCntError;
    } // method setWatchlCnt

    /**
     * Set the 'watchlCnt' class variable
     * @param  watchlCnt (Float)
     */
    public int setWatchlCnt(Float watchlCnt) {
        try {
            if (watchlCnt.floatValue() == FLOATNULL) {
                setWatchlCnt(INTNULL);
            } else {
                setWatchlCnt(watchlCnt.intValue());
            } // if (watchlCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatchlCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchlCntError;
    } // method setWatchlCnt

    /**
     * Set the 'watchlCnt' class variable
     * @param  watchlCnt (String)
     */
    public int setWatchlCnt(String watchlCnt) {
        try {
            setWatchlCnt(new Integer(watchlCnt).intValue());
        } catch (Exception e) {
            setWatchlCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchlCntError;
    } // method setWatchlCnt

    /**
     * Called when an exception has occured
     * @param  watchlCnt (String)
     */
    private void setWatchlCntError (int watchlCnt, Exception e, int error) {
        this.watchlCnt = watchlCnt;
        watchlCntErrorMessage = e.toString();
        watchlCntError = error;
    } // method setWatchlCntError


    /**
     * Set the 'sedphyCnt' class variable
     * @param  sedphyCnt (int)
     */
    public int setSedphyCnt(int sedphyCnt) {
        try {
            if ( ((sedphyCnt == INTNULL) ||
                  (sedphyCnt == INTNULL2)) ||
                !((sedphyCnt < SEDPHY_CNT_MN) ||
                  (sedphyCnt > SEDPHY_CNT_MX)) ) {
                this.sedphyCnt = sedphyCnt;
                sedphyCntError = ERROR_NORMAL;
            } else {
                throw sedphyCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedphyCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedphyCntError;
    } // method setSedphyCnt

    /**
     * Set the 'sedphyCnt' class variable
     * @param  sedphyCnt (Integer)
     */
    public int setSedphyCnt(Integer sedphyCnt) {
        try {
            setSedphyCnt(sedphyCnt.intValue());
        } catch (Exception e) {
            setSedphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyCntError;
    } // method setSedphyCnt

    /**
     * Set the 'sedphyCnt' class variable
     * @param  sedphyCnt (Float)
     */
    public int setSedphyCnt(Float sedphyCnt) {
        try {
            if (sedphyCnt.floatValue() == FLOATNULL) {
                setSedphyCnt(INTNULL);
            } else {
                setSedphyCnt(sedphyCnt.intValue());
            } // if (sedphyCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyCntError;
    } // method setSedphyCnt

    /**
     * Set the 'sedphyCnt' class variable
     * @param  sedphyCnt (String)
     */
    public int setSedphyCnt(String sedphyCnt) {
        try {
            setSedphyCnt(new Integer(sedphyCnt).intValue());
        } catch (Exception e) {
            setSedphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyCntError;
    } // method setSedphyCnt

    /**
     * Called when an exception has occured
     * @param  sedphyCnt (String)
     */
    private void setSedphyCntError (int sedphyCnt, Exception e, int error) {
        this.sedphyCnt = sedphyCnt;
        sedphyCntErrorMessage = e.toString();
        sedphyCntError = error;
    } // method setSedphyCntError


    /**
     * Set the 'sedpesCnt' class variable
     * @param  sedpesCnt (int)
     */
    public int setSedpesCnt(int sedpesCnt) {
        try {
            if ( ((sedpesCnt == INTNULL) ||
                  (sedpesCnt == INTNULL2)) ||
                !((sedpesCnt < SEDPES_CNT_MN) ||
                  (sedpesCnt > SEDPES_CNT_MX)) ) {
                this.sedpesCnt = sedpesCnt;
                sedpesCntError = ERROR_NORMAL;
            } else {
                throw sedpesCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedpesCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedpesCntError;
    } // method setSedpesCnt

    /**
     * Set the 'sedpesCnt' class variable
     * @param  sedpesCnt (Integer)
     */
    public int setSedpesCnt(Integer sedpesCnt) {
        try {
            setSedpesCnt(sedpesCnt.intValue());
        } catch (Exception e) {
            setSedpesCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpesCntError;
    } // method setSedpesCnt

    /**
     * Set the 'sedpesCnt' class variable
     * @param  sedpesCnt (Float)
     */
    public int setSedpesCnt(Float sedpesCnt) {
        try {
            if (sedpesCnt.floatValue() == FLOATNULL) {
                setSedpesCnt(INTNULL);
            } else {
                setSedpesCnt(sedpesCnt.intValue());
            } // if (sedpesCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedpesCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpesCntError;
    } // method setSedpesCnt

    /**
     * Set the 'sedpesCnt' class variable
     * @param  sedpesCnt (String)
     */
    public int setSedpesCnt(String sedpesCnt) {
        try {
            setSedpesCnt(new Integer(sedpesCnt).intValue());
        } catch (Exception e) {
            setSedpesCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpesCntError;
    } // method setSedpesCnt

    /**
     * Called when an exception has occured
     * @param  sedpesCnt (String)
     */
    private void setSedpesCntError (int sedpesCnt, Exception e, int error) {
        this.sedpesCnt = sedpesCnt;
        sedpesCntErrorMessage = e.toString();
        sedpesCntError = error;
    } // method setSedpesCntError


    /**
     * Set the 'sedpol1Cnt' class variable
     * @param  sedpol1Cnt (int)
     */
    public int setSedpol1Cnt(int sedpol1Cnt) {
        try {
            if ( ((sedpol1Cnt == INTNULL) ||
                  (sedpol1Cnt == INTNULL2)) ||
                !((sedpol1Cnt < SEDPOL1_CNT_MN) ||
                  (sedpol1Cnt > SEDPOL1_CNT_MX)) ) {
                this.sedpol1Cnt = sedpol1Cnt;
                sedpol1CntError = ERROR_NORMAL;
            } else {
                throw sedpol1CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedpol1CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedpol1CntError;
    } // method setSedpol1Cnt

    /**
     * Set the 'sedpol1Cnt' class variable
     * @param  sedpol1Cnt (Integer)
     */
    public int setSedpol1Cnt(Integer sedpol1Cnt) {
        try {
            setSedpol1Cnt(sedpol1Cnt.intValue());
        } catch (Exception e) {
            setSedpol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpol1CntError;
    } // method setSedpol1Cnt

    /**
     * Set the 'sedpol1Cnt' class variable
     * @param  sedpol1Cnt (Float)
     */
    public int setSedpol1Cnt(Float sedpol1Cnt) {
        try {
            if (sedpol1Cnt.floatValue() == FLOATNULL) {
                setSedpol1Cnt(INTNULL);
            } else {
                setSedpol1Cnt(sedpol1Cnt.intValue());
            } // if (sedpol1Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedpol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpol1CntError;
    } // method setSedpol1Cnt

    /**
     * Set the 'sedpol1Cnt' class variable
     * @param  sedpol1Cnt (String)
     */
    public int setSedpol1Cnt(String sedpol1Cnt) {
        try {
            setSedpol1Cnt(new Integer(sedpol1Cnt).intValue());
        } catch (Exception e) {
            setSedpol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpol1CntError;
    } // method setSedpol1Cnt

    /**
     * Called when an exception has occured
     * @param  sedpol1Cnt (String)
     */
    private void setSedpol1CntError (int sedpol1Cnt, Exception e, int error) {
        this.sedpol1Cnt = sedpol1Cnt;
        sedpol1CntErrorMessage = e.toString();
        sedpol1CntError = error;
    } // method setSedpol1CntError


    /**
     * Set the 'sedpol2Cnt' class variable
     * @param  sedpol2Cnt (int)
     */
    public int setSedpol2Cnt(int sedpol2Cnt) {
        try {
            if ( ((sedpol2Cnt == INTNULL) ||
                  (sedpol2Cnt == INTNULL2)) ||
                !((sedpol2Cnt < SEDPOL2_CNT_MN) ||
                  (sedpol2Cnt > SEDPOL2_CNT_MX)) ) {
                this.sedpol2Cnt = sedpol2Cnt;
                sedpol2CntError = ERROR_NORMAL;
            } else {
                throw sedpol2CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedpol2CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedpol2CntError;
    } // method setSedpol2Cnt

    /**
     * Set the 'sedpol2Cnt' class variable
     * @param  sedpol2Cnt (Integer)
     */
    public int setSedpol2Cnt(Integer sedpol2Cnt) {
        try {
            setSedpol2Cnt(sedpol2Cnt.intValue());
        } catch (Exception e) {
            setSedpol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpol2CntError;
    } // method setSedpol2Cnt

    /**
     * Set the 'sedpol2Cnt' class variable
     * @param  sedpol2Cnt (Float)
     */
    public int setSedpol2Cnt(Float sedpol2Cnt) {
        try {
            if (sedpol2Cnt.floatValue() == FLOATNULL) {
                setSedpol2Cnt(INTNULL);
            } else {
                setSedpol2Cnt(sedpol2Cnt.intValue());
            } // if (sedpol2Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedpol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpol2CntError;
    } // method setSedpol2Cnt

    /**
     * Set the 'sedpol2Cnt' class variable
     * @param  sedpol2Cnt (String)
     */
    public int setSedpol2Cnt(String sedpol2Cnt) {
        try {
            setSedpol2Cnt(new Integer(sedpol2Cnt).intValue());
        } catch (Exception e) {
            setSedpol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpol2CntError;
    } // method setSedpol2Cnt

    /**
     * Called when an exception has occured
     * @param  sedpol2Cnt (String)
     */
    private void setSedpol2CntError (int sedpol2Cnt, Exception e, int error) {
        this.sedpol2Cnt = sedpol2Cnt;
        sedpol2CntErrorMessage = e.toString();
        sedpol2CntError = error;
    } // method setSedpol2CntError


    /**
     * Set the 'sedchem1Cnt' class variable
     * @param  sedchem1Cnt (int)
     */
    public int setSedchem1Cnt(int sedchem1Cnt) {
        try {
            if ( ((sedchem1Cnt == INTNULL) ||
                  (sedchem1Cnt == INTNULL2)) ||
                !((sedchem1Cnt < SEDCHEM1_CNT_MN) ||
                  (sedchem1Cnt > SEDCHEM1_CNT_MX)) ) {
                this.sedchem1Cnt = sedchem1Cnt;
                sedchem1CntError = ERROR_NORMAL;
            } else {
                throw sedchem1CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedchem1CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedchem1CntError;
    } // method setSedchem1Cnt

    /**
     * Set the 'sedchem1Cnt' class variable
     * @param  sedchem1Cnt (Integer)
     */
    public int setSedchem1Cnt(Integer sedchem1Cnt) {
        try {
            setSedchem1Cnt(sedchem1Cnt.intValue());
        } catch (Exception e) {
            setSedchem1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedchem1CntError;
    } // method setSedchem1Cnt

    /**
     * Set the 'sedchem1Cnt' class variable
     * @param  sedchem1Cnt (Float)
     */
    public int setSedchem1Cnt(Float sedchem1Cnt) {
        try {
            if (sedchem1Cnt.floatValue() == FLOATNULL) {
                setSedchem1Cnt(INTNULL);
            } else {
                setSedchem1Cnt(sedchem1Cnt.intValue());
            } // if (sedchem1Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedchem1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedchem1CntError;
    } // method setSedchem1Cnt

    /**
     * Set the 'sedchem1Cnt' class variable
     * @param  sedchem1Cnt (String)
     */
    public int setSedchem1Cnt(String sedchem1Cnt) {
        try {
            setSedchem1Cnt(new Integer(sedchem1Cnt).intValue());
        } catch (Exception e) {
            setSedchem1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedchem1CntError;
    } // method setSedchem1Cnt

    /**
     * Called when an exception has occured
     * @param  sedchem1Cnt (String)
     */
    private void setSedchem1CntError (int sedchem1Cnt, Exception e, int error) {
        this.sedchem1Cnt = sedchem1Cnt;
        sedchem1CntErrorMessage = e.toString();
        sedchem1CntError = error;
    } // method setSedchem1CntError


    /**
     * Set the 'sedchem2Cnt' class variable
     * @param  sedchem2Cnt (int)
     */
    public int setSedchem2Cnt(int sedchem2Cnt) {
        try {
            if ( ((sedchem2Cnt == INTNULL) ||
                  (sedchem2Cnt == INTNULL2)) ||
                !((sedchem2Cnt < SEDCHEM2_CNT_MN) ||
                  (sedchem2Cnt > SEDCHEM2_CNT_MX)) ) {
                this.sedchem2Cnt = sedchem2Cnt;
                sedchem2CntError = ERROR_NORMAL;
            } else {
                throw sedchem2CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedchem2CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedchem2CntError;
    } // method setSedchem2Cnt

    /**
     * Set the 'sedchem2Cnt' class variable
     * @param  sedchem2Cnt (Integer)
     */
    public int setSedchem2Cnt(Integer sedchem2Cnt) {
        try {
            setSedchem2Cnt(sedchem2Cnt.intValue());
        } catch (Exception e) {
            setSedchem2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedchem2CntError;
    } // method setSedchem2Cnt

    /**
     * Set the 'sedchem2Cnt' class variable
     * @param  sedchem2Cnt (Float)
     */
    public int setSedchem2Cnt(Float sedchem2Cnt) {
        try {
            if (sedchem2Cnt.floatValue() == FLOATNULL) {
                setSedchem2Cnt(INTNULL);
            } else {
                setSedchem2Cnt(sedchem2Cnt.intValue());
            } // if (sedchem2Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedchem2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedchem2CntError;
    } // method setSedchem2Cnt

    /**
     * Set the 'sedchem2Cnt' class variable
     * @param  sedchem2Cnt (String)
     */
    public int setSedchem2Cnt(String sedchem2Cnt) {
        try {
            setSedchem2Cnt(new Integer(sedchem2Cnt).intValue());
        } catch (Exception e) {
            setSedchem2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedchem2CntError;
    } // method setSedchem2Cnt

    /**
     * Called when an exception has occured
     * @param  sedchem2Cnt (String)
     */
    private void setSedchem2CntError (int sedchem2Cnt, Exception e, int error) {
        this.sedchem2Cnt = sedchem2Cnt;
        sedchem2CntErrorMessage = e.toString();
        sedchem2CntError = error;
    } // method setSedchem2CntError


    /**
     * Set the 'sedtaxCnt' class variable
     * @param  sedtaxCnt (int)
     */
    public int setSedtaxCnt(int sedtaxCnt) {
        try {
            if ( ((sedtaxCnt == INTNULL) ||
                  (sedtaxCnt == INTNULL2)) ||
                !((sedtaxCnt < SEDTAX_CNT_MN) ||
                  (sedtaxCnt > SEDTAX_CNT_MX)) ) {
                this.sedtaxCnt = sedtaxCnt;
                sedtaxCntError = ERROR_NORMAL;
            } else {
                throw sedtaxCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedtaxCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedtaxCntError;
    } // method setSedtaxCnt

    /**
     * Set the 'sedtaxCnt' class variable
     * @param  sedtaxCnt (Integer)
     */
    public int setSedtaxCnt(Integer sedtaxCnt) {
        try {
            setSedtaxCnt(sedtaxCnt.intValue());
        } catch (Exception e) {
            setSedtaxCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedtaxCntError;
    } // method setSedtaxCnt

    /**
     * Set the 'sedtaxCnt' class variable
     * @param  sedtaxCnt (Float)
     */
    public int setSedtaxCnt(Float sedtaxCnt) {
        try {
            if (sedtaxCnt.floatValue() == FLOATNULL) {
                setSedtaxCnt(INTNULL);
            } else {
                setSedtaxCnt(sedtaxCnt.intValue());
            } // if (sedtaxCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedtaxCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedtaxCntError;
    } // method setSedtaxCnt

    /**
     * Set the 'sedtaxCnt' class variable
     * @param  sedtaxCnt (String)
     */
    public int setSedtaxCnt(String sedtaxCnt) {
        try {
            setSedtaxCnt(new Integer(sedtaxCnt).intValue());
        } catch (Exception e) {
            setSedtaxCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedtaxCntError;
    } // method setSedtaxCnt

    /**
     * Called when an exception has occured
     * @param  sedtaxCnt (String)
     */
    private void setSedtaxCntError (int sedtaxCnt, Exception e, int error) {
        this.sedtaxCnt = sedtaxCnt;
        sedtaxCntErrorMessage = e.toString();
        sedtaxCntError = error;
    } // method setSedtaxCntError


    /**
     * Set the 'plaphyCnt' class variable
     * @param  plaphyCnt (int)
     */
    public int setPlaphyCnt(int plaphyCnt) {
        try {
            if ( ((plaphyCnt == INTNULL) ||
                  (plaphyCnt == INTNULL2)) ||
                !((plaphyCnt < PLAPHY_CNT_MN) ||
                  (plaphyCnt > PLAPHY_CNT_MX)) ) {
                this.plaphyCnt = plaphyCnt;
                plaphyCntError = ERROR_NORMAL;
            } else {
                throw plaphyCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlaphyCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plaphyCntError;
    } // method setPlaphyCnt

    /**
     * Set the 'plaphyCnt' class variable
     * @param  plaphyCnt (Integer)
     */
    public int setPlaphyCnt(Integer plaphyCnt) {
        try {
            setPlaphyCnt(plaphyCnt.intValue());
        } catch (Exception e) {
            setPlaphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCntError;
    } // method setPlaphyCnt

    /**
     * Set the 'plaphyCnt' class variable
     * @param  plaphyCnt (Float)
     */
    public int setPlaphyCnt(Float plaphyCnt) {
        try {
            if (plaphyCnt.floatValue() == FLOATNULL) {
                setPlaphyCnt(INTNULL);
            } else {
                setPlaphyCnt(plaphyCnt.intValue());
            } // if (plaphyCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlaphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCntError;
    } // method setPlaphyCnt

    /**
     * Set the 'plaphyCnt' class variable
     * @param  plaphyCnt (String)
     */
    public int setPlaphyCnt(String plaphyCnt) {
        try {
            setPlaphyCnt(new Integer(plaphyCnt).intValue());
        } catch (Exception e) {
            setPlaphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCntError;
    } // method setPlaphyCnt

    /**
     * Called when an exception has occured
     * @param  plaphyCnt (String)
     */
    private void setPlaphyCntError (int plaphyCnt, Exception e, int error) {
        this.plaphyCnt = plaphyCnt;
        plaphyCntErrorMessage = e.toString();
        plaphyCntError = error;
    } // method setPlaphyCntError


    /**
     * Set the 'plapesCnt' class variable
     * @param  plapesCnt (int)
     */
    public int setPlapesCnt(int plapesCnt) {
        try {
            if ( ((plapesCnt == INTNULL) ||
                  (plapesCnt == INTNULL2)) ||
                !((plapesCnt < PLAPES_CNT_MN) ||
                  (plapesCnt > PLAPES_CNT_MX)) ) {
                this.plapesCnt = plapesCnt;
                plapesCntError = ERROR_NORMAL;
            } else {
                throw plapesCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlapesCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plapesCntError;
    } // method setPlapesCnt

    /**
     * Set the 'plapesCnt' class variable
     * @param  plapesCnt (Integer)
     */
    public int setPlapesCnt(Integer plapesCnt) {
        try {
            setPlapesCnt(plapesCnt.intValue());
        } catch (Exception e) {
            setPlapesCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapesCntError;
    } // method setPlapesCnt

    /**
     * Set the 'plapesCnt' class variable
     * @param  plapesCnt (Float)
     */
    public int setPlapesCnt(Float plapesCnt) {
        try {
            if (plapesCnt.floatValue() == FLOATNULL) {
                setPlapesCnt(INTNULL);
            } else {
                setPlapesCnt(plapesCnt.intValue());
            } // if (plapesCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlapesCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapesCntError;
    } // method setPlapesCnt

    /**
     * Set the 'plapesCnt' class variable
     * @param  plapesCnt (String)
     */
    public int setPlapesCnt(String plapesCnt) {
        try {
            setPlapesCnt(new Integer(plapesCnt).intValue());
        } catch (Exception e) {
            setPlapesCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapesCntError;
    } // method setPlapesCnt

    /**
     * Called when an exception has occured
     * @param  plapesCnt (String)
     */
    private void setPlapesCntError (int plapesCnt, Exception e, int error) {
        this.plapesCnt = plapesCnt;
        plapesCntErrorMessage = e.toString();
        plapesCntError = error;
    } // method setPlapesCntError


    /**
     * Set the 'plapol1Cnt' class variable
     * @param  plapol1Cnt (int)
     */
    public int setPlapol1Cnt(int plapol1Cnt) {
        try {
            if ( ((plapol1Cnt == INTNULL) ||
                  (plapol1Cnt == INTNULL2)) ||
                !((plapol1Cnt < PLAPOL1_CNT_MN) ||
                  (plapol1Cnt > PLAPOL1_CNT_MX)) ) {
                this.plapol1Cnt = plapol1Cnt;
                plapol1CntError = ERROR_NORMAL;
            } else {
                throw plapol1CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlapol1CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plapol1CntError;
    } // method setPlapol1Cnt

    /**
     * Set the 'plapol1Cnt' class variable
     * @param  plapol1Cnt (Integer)
     */
    public int setPlapol1Cnt(Integer plapol1Cnt) {
        try {
            setPlapol1Cnt(plapol1Cnt.intValue());
        } catch (Exception e) {
            setPlapol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapol1CntError;
    } // method setPlapol1Cnt

    /**
     * Set the 'plapol1Cnt' class variable
     * @param  plapol1Cnt (Float)
     */
    public int setPlapol1Cnt(Float plapol1Cnt) {
        try {
            if (plapol1Cnt.floatValue() == FLOATNULL) {
                setPlapol1Cnt(INTNULL);
            } else {
                setPlapol1Cnt(plapol1Cnt.intValue());
            } // if (plapol1Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlapol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapol1CntError;
    } // method setPlapol1Cnt

    /**
     * Set the 'plapol1Cnt' class variable
     * @param  plapol1Cnt (String)
     */
    public int setPlapol1Cnt(String plapol1Cnt) {
        try {
            setPlapol1Cnt(new Integer(plapol1Cnt).intValue());
        } catch (Exception e) {
            setPlapol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapol1CntError;
    } // method setPlapol1Cnt

    /**
     * Called when an exception has occured
     * @param  plapol1Cnt (String)
     */
    private void setPlapol1CntError (int plapol1Cnt, Exception e, int error) {
        this.plapol1Cnt = plapol1Cnt;
        plapol1CntErrorMessage = e.toString();
        plapol1CntError = error;
    } // method setPlapol1CntError


    /**
     * Set the 'plapol2Cnt' class variable
     * @param  plapol2Cnt (int)
     */
    public int setPlapol2Cnt(int plapol2Cnt) {
        try {
            if ( ((plapol2Cnt == INTNULL) ||
                  (plapol2Cnt == INTNULL2)) ||
                !((plapol2Cnt < PLAPOL2_CNT_MN) ||
                  (plapol2Cnt > PLAPOL2_CNT_MX)) ) {
                this.plapol2Cnt = plapol2Cnt;
                plapol2CntError = ERROR_NORMAL;
            } else {
                throw plapol2CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlapol2CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plapol2CntError;
    } // method setPlapol2Cnt

    /**
     * Set the 'plapol2Cnt' class variable
     * @param  plapol2Cnt (Integer)
     */
    public int setPlapol2Cnt(Integer plapol2Cnt) {
        try {
            setPlapol2Cnt(plapol2Cnt.intValue());
        } catch (Exception e) {
            setPlapol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapol2CntError;
    } // method setPlapol2Cnt

    /**
     * Set the 'plapol2Cnt' class variable
     * @param  plapol2Cnt (Float)
     */
    public int setPlapol2Cnt(Float plapol2Cnt) {
        try {
            if (plapol2Cnt.floatValue() == FLOATNULL) {
                setPlapol2Cnt(INTNULL);
            } else {
                setPlapol2Cnt(plapol2Cnt.intValue());
            } // if (plapol2Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlapol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapol2CntError;
    } // method setPlapol2Cnt

    /**
     * Set the 'plapol2Cnt' class variable
     * @param  plapol2Cnt (String)
     */
    public int setPlapol2Cnt(String plapol2Cnt) {
        try {
            setPlapol2Cnt(new Integer(plapol2Cnt).intValue());
        } catch (Exception e) {
            setPlapol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapol2CntError;
    } // method setPlapol2Cnt

    /**
     * Called when an exception has occured
     * @param  plapol2Cnt (String)
     */
    private void setPlapol2CntError (int plapol2Cnt, Exception e, int error) {
        this.plapol2Cnt = plapol2Cnt;
        plapol2CntErrorMessage = e.toString();
        plapol2CntError = error;
    } // method setPlapol2CntError


    /**
     * Set the 'plataxCnt' class variable
     * @param  plataxCnt (int)
     */
    public int setPlataxCnt(int plataxCnt) {
        try {
            if ( ((plataxCnt == INTNULL) ||
                  (plataxCnt == INTNULL2)) ||
                !((plataxCnt < PLATAX_CNT_MN) ||
                  (plataxCnt > PLATAX_CNT_MX)) ) {
                this.plataxCnt = plataxCnt;
                plataxCntError = ERROR_NORMAL;
            } else {
                throw plataxCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlataxCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plataxCntError;
    } // method setPlataxCnt

    /**
     * Set the 'plataxCnt' class variable
     * @param  plataxCnt (Integer)
     */
    public int setPlataxCnt(Integer plataxCnt) {
        try {
            setPlataxCnt(plataxCnt.intValue());
        } catch (Exception e) {
            setPlataxCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plataxCntError;
    } // method setPlataxCnt

    /**
     * Set the 'plataxCnt' class variable
     * @param  plataxCnt (Float)
     */
    public int setPlataxCnt(Float plataxCnt) {
        try {
            if (plataxCnt.floatValue() == FLOATNULL) {
                setPlataxCnt(INTNULL);
            } else {
                setPlataxCnt(plataxCnt.intValue());
            } // if (plataxCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlataxCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plataxCntError;
    } // method setPlataxCnt

    /**
     * Set the 'plataxCnt' class variable
     * @param  plataxCnt (String)
     */
    public int setPlataxCnt(String plataxCnt) {
        try {
            setPlataxCnt(new Integer(plataxCnt).intValue());
        } catch (Exception e) {
            setPlataxCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plataxCntError;
    } // method setPlataxCnt

    /**
     * Called when an exception has occured
     * @param  plataxCnt (String)
     */
    private void setPlataxCntError (int plataxCnt, Exception e, int error) {
        this.plataxCnt = plataxCnt;
        plataxCntErrorMessage = e.toString();
        plataxCntError = error;
    } // method setPlataxCntError


    /**
     * Set the 'plachlCnt' class variable
     * @param  plachlCnt (int)
     */
    public int setPlachlCnt(int plachlCnt) {
        try {
            if ( ((plachlCnt == INTNULL) ||
                  (plachlCnt == INTNULL2)) ||
                !((plachlCnt < PLACHL_CNT_MN) ||
                  (plachlCnt > PLACHL_CNT_MX)) ) {
                this.plachlCnt = plachlCnt;
                plachlCntError = ERROR_NORMAL;
            } else {
                throw plachlCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlachlCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plachlCntError;
    } // method setPlachlCnt

    /**
     * Set the 'plachlCnt' class variable
     * @param  plachlCnt (Integer)
     */
    public int setPlachlCnt(Integer plachlCnt) {
        try {
            setPlachlCnt(plachlCnt.intValue());
        } catch (Exception e) {
            setPlachlCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plachlCntError;
    } // method setPlachlCnt

    /**
     * Set the 'plachlCnt' class variable
     * @param  plachlCnt (Float)
     */
    public int setPlachlCnt(Float plachlCnt) {
        try {
            if (plachlCnt.floatValue() == FLOATNULL) {
                setPlachlCnt(INTNULL);
            } else {
                setPlachlCnt(plachlCnt.intValue());
            } // if (plachlCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlachlCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plachlCntError;
    } // method setPlachlCnt

    /**
     * Set the 'plachlCnt' class variable
     * @param  plachlCnt (String)
     */
    public int setPlachlCnt(String plachlCnt) {
        try {
            setPlachlCnt(new Integer(plachlCnt).intValue());
        } catch (Exception e) {
            setPlachlCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plachlCntError;
    } // method setPlachlCnt

    /**
     * Called when an exception has occured
     * @param  plachlCnt (String)
     */
    private void setPlachlCntError (int plachlCnt, Exception e, int error) {
        this.plachlCnt = plachlCnt;
        plachlCntErrorMessage = e.toString();
        plachlCntError = error;
    } // method setPlachlCntError


    /**
     * Set the 'tisphyCnt' class variable
     * @param  tisphyCnt (int)
     */
    public int setTisphyCnt(int tisphyCnt) {
        try {
            if ( ((tisphyCnt == INTNULL) ||
                  (tisphyCnt == INTNULL2)) ||
                !((tisphyCnt < TISPHY_CNT_MN) ||
                  (tisphyCnt > TISPHY_CNT_MX)) ) {
                this.tisphyCnt = tisphyCnt;
                tisphyCntError = ERROR_NORMAL;
            } else {
                throw tisphyCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTisphyCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tisphyCntError;
    } // method setTisphyCnt

    /**
     * Set the 'tisphyCnt' class variable
     * @param  tisphyCnt (Integer)
     */
    public int setTisphyCnt(Integer tisphyCnt) {
        try {
            setTisphyCnt(tisphyCnt.intValue());
        } catch (Exception e) {
            setTisphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyCntError;
    } // method setTisphyCnt

    /**
     * Set the 'tisphyCnt' class variable
     * @param  tisphyCnt (Float)
     */
    public int setTisphyCnt(Float tisphyCnt) {
        try {
            if (tisphyCnt.floatValue() == FLOATNULL) {
                setTisphyCnt(INTNULL);
            } else {
                setTisphyCnt(tisphyCnt.intValue());
            } // if (tisphyCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTisphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyCntError;
    } // method setTisphyCnt

    /**
     * Set the 'tisphyCnt' class variable
     * @param  tisphyCnt (String)
     */
    public int setTisphyCnt(String tisphyCnt) {
        try {
            setTisphyCnt(new Integer(tisphyCnt).intValue());
        } catch (Exception e) {
            setTisphyCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyCntError;
    } // method setTisphyCnt

    /**
     * Called when an exception has occured
     * @param  tisphyCnt (String)
     */
    private void setTisphyCntError (int tisphyCnt, Exception e, int error) {
        this.tisphyCnt = tisphyCnt;
        tisphyCntErrorMessage = e.toString();
        tisphyCntError = error;
    } // method setTisphyCntError


    /**
     * Set the 'tispesCnt' class variable
     * @param  tispesCnt (int)
     */
    public int setTispesCnt(int tispesCnt) {
        try {
            if ( ((tispesCnt == INTNULL) ||
                  (tispesCnt == INTNULL2)) ||
                !((tispesCnt < TISPES_CNT_MN) ||
                  (tispesCnt > TISPES_CNT_MX)) ) {
                this.tispesCnt = tispesCnt;
                tispesCntError = ERROR_NORMAL;
            } else {
                throw tispesCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTispesCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tispesCntError;
    } // method setTispesCnt

    /**
     * Set the 'tispesCnt' class variable
     * @param  tispesCnt (Integer)
     */
    public int setTispesCnt(Integer tispesCnt) {
        try {
            setTispesCnt(tispesCnt.intValue());
        } catch (Exception e) {
            setTispesCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispesCntError;
    } // method setTispesCnt

    /**
     * Set the 'tispesCnt' class variable
     * @param  tispesCnt (Float)
     */
    public int setTispesCnt(Float tispesCnt) {
        try {
            if (tispesCnt.floatValue() == FLOATNULL) {
                setTispesCnt(INTNULL);
            } else {
                setTispesCnt(tispesCnt.intValue());
            } // if (tispesCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTispesCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispesCntError;
    } // method setTispesCnt

    /**
     * Set the 'tispesCnt' class variable
     * @param  tispesCnt (String)
     */
    public int setTispesCnt(String tispesCnt) {
        try {
            setTispesCnt(new Integer(tispesCnt).intValue());
        } catch (Exception e) {
            setTispesCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispesCntError;
    } // method setTispesCnt

    /**
     * Called when an exception has occured
     * @param  tispesCnt (String)
     */
    private void setTispesCntError (int tispesCnt, Exception e, int error) {
        this.tispesCnt = tispesCnt;
        tispesCntErrorMessage = e.toString();
        tispesCntError = error;
    } // method setTispesCntError


    /**
     * Set the 'tispol1Cnt' class variable
     * @param  tispol1Cnt (int)
     */
    public int setTispol1Cnt(int tispol1Cnt) {
        try {
            if ( ((tispol1Cnt == INTNULL) ||
                  (tispol1Cnt == INTNULL2)) ||
                !((tispol1Cnt < TISPOL1_CNT_MN) ||
                  (tispol1Cnt > TISPOL1_CNT_MX)) ) {
                this.tispol1Cnt = tispol1Cnt;
                tispol1CntError = ERROR_NORMAL;
            } else {
                throw tispol1CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTispol1CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tispol1CntError;
    } // method setTispol1Cnt

    /**
     * Set the 'tispol1Cnt' class variable
     * @param  tispol1Cnt (Integer)
     */
    public int setTispol1Cnt(Integer tispol1Cnt) {
        try {
            setTispol1Cnt(tispol1Cnt.intValue());
        } catch (Exception e) {
            setTispol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispol1CntError;
    } // method setTispol1Cnt

    /**
     * Set the 'tispol1Cnt' class variable
     * @param  tispol1Cnt (Float)
     */
    public int setTispol1Cnt(Float tispol1Cnt) {
        try {
            if (tispol1Cnt.floatValue() == FLOATNULL) {
                setTispol1Cnt(INTNULL);
            } else {
                setTispol1Cnt(tispol1Cnt.intValue());
            } // if (tispol1Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTispol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispol1CntError;
    } // method setTispol1Cnt

    /**
     * Set the 'tispol1Cnt' class variable
     * @param  tispol1Cnt (String)
     */
    public int setTispol1Cnt(String tispol1Cnt) {
        try {
            setTispol1Cnt(new Integer(tispol1Cnt).intValue());
        } catch (Exception e) {
            setTispol1CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispol1CntError;
    } // method setTispol1Cnt

    /**
     * Called when an exception has occured
     * @param  tispol1Cnt (String)
     */
    private void setTispol1CntError (int tispol1Cnt, Exception e, int error) {
        this.tispol1Cnt = tispol1Cnt;
        tispol1CntErrorMessage = e.toString();
        tispol1CntError = error;
    } // method setTispol1CntError


    /**
     * Set the 'tispol2Cnt' class variable
     * @param  tispol2Cnt (int)
     */
    public int setTispol2Cnt(int tispol2Cnt) {
        try {
            if ( ((tispol2Cnt == INTNULL) ||
                  (tispol2Cnt == INTNULL2)) ||
                !((tispol2Cnt < TISPOL2_CNT_MN) ||
                  (tispol2Cnt > TISPOL2_CNT_MX)) ) {
                this.tispol2Cnt = tispol2Cnt;
                tispol2CntError = ERROR_NORMAL;
            } else {
                throw tispol2CntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTispol2CntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tispol2CntError;
    } // method setTispol2Cnt

    /**
     * Set the 'tispol2Cnt' class variable
     * @param  tispol2Cnt (Integer)
     */
    public int setTispol2Cnt(Integer tispol2Cnt) {
        try {
            setTispol2Cnt(tispol2Cnt.intValue());
        } catch (Exception e) {
            setTispol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispol2CntError;
    } // method setTispol2Cnt

    /**
     * Set the 'tispol2Cnt' class variable
     * @param  tispol2Cnt (Float)
     */
    public int setTispol2Cnt(Float tispol2Cnt) {
        try {
            if (tispol2Cnt.floatValue() == FLOATNULL) {
                setTispol2Cnt(INTNULL);
            } else {
                setTispol2Cnt(tispol2Cnt.intValue());
            } // if (tispol2Cnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTispol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispol2CntError;
    } // method setTispol2Cnt

    /**
     * Set the 'tispol2Cnt' class variable
     * @param  tispol2Cnt (String)
     */
    public int setTispol2Cnt(String tispol2Cnt) {
        try {
            setTispol2Cnt(new Integer(tispol2Cnt).intValue());
        } catch (Exception e) {
            setTispol2CntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispol2CntError;
    } // method setTispol2Cnt

    /**
     * Called when an exception has occured
     * @param  tispol2Cnt (String)
     */
    private void setTispol2CntError (int tispol2Cnt, Exception e, int error) {
        this.tispol2Cnt = tispol2Cnt;
        tispol2CntErrorMessage = e.toString();
        tispol2CntError = error;
    } // method setTispol2CntError


    /**
     * Set the 'tisanimalCnt' class variable
     * @param  tisanimalCnt (int)
     */
    public int setTisanimalCnt(int tisanimalCnt) {
        try {
            if ( ((tisanimalCnt == INTNULL) ||
                  (tisanimalCnt == INTNULL2)) ||
                !((tisanimalCnt < TISANIMAL_CNT_MN) ||
                  (tisanimalCnt > TISANIMAL_CNT_MX)) ) {
                this.tisanimalCnt = tisanimalCnt;
                tisanimalCntError = ERROR_NORMAL;
            } else {
                throw tisanimalCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTisanimalCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tisanimalCntError;
    } // method setTisanimalCnt

    /**
     * Set the 'tisanimalCnt' class variable
     * @param  tisanimalCnt (Integer)
     */
    public int setTisanimalCnt(Integer tisanimalCnt) {
        try {
            setTisanimalCnt(tisanimalCnt.intValue());
        } catch (Exception e) {
            setTisanimalCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisanimalCntError;
    } // method setTisanimalCnt

    /**
     * Set the 'tisanimalCnt' class variable
     * @param  tisanimalCnt (Float)
     */
    public int setTisanimalCnt(Float tisanimalCnt) {
        try {
            if (tisanimalCnt.floatValue() == FLOATNULL) {
                setTisanimalCnt(INTNULL);
            } else {
                setTisanimalCnt(tisanimalCnt.intValue());
            } // if (tisanimalCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTisanimalCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisanimalCntError;
    } // method setTisanimalCnt

    /**
     * Set the 'tisanimalCnt' class variable
     * @param  tisanimalCnt (String)
     */
    public int setTisanimalCnt(String tisanimalCnt) {
        try {
            setTisanimalCnt(new Integer(tisanimalCnt).intValue());
        } catch (Exception e) {
            setTisanimalCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisanimalCntError;
    } // method setTisanimalCnt

    /**
     * Called when an exception has occured
     * @param  tisanimalCnt (String)
     */
    private void setTisanimalCntError (int tisanimalCnt, Exception e, int error) {
        this.tisanimalCnt = tisanimalCnt;
        tisanimalCntErrorMessage = e.toString();
        tisanimalCntError = error;
    } // method setTisanimalCntError


    /**
     * Set the 'weatherCnt' class variable
     * @param  weatherCnt (int)
     */
    public int setWeatherCnt(int weatherCnt) {
        try {
            if ( ((weatherCnt == INTNULL) ||
                  (weatherCnt == INTNULL2)) ||
                !((weatherCnt < WEATHER_CNT_MN) ||
                  (weatherCnt > WEATHER_CNT_MX)) ) {
                this.weatherCnt = weatherCnt;
                weatherCntError = ERROR_NORMAL;
            } else {
                throw weatherCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWeatherCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return weatherCntError;
    } // method setWeatherCnt

    /**
     * Set the 'weatherCnt' class variable
     * @param  weatherCnt (Integer)
     */
    public int setWeatherCnt(Integer weatherCnt) {
        try {
            setWeatherCnt(weatherCnt.intValue());
        } catch (Exception e) {
            setWeatherCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return weatherCntError;
    } // method setWeatherCnt

    /**
     * Set the 'weatherCnt' class variable
     * @param  weatherCnt (Float)
     */
    public int setWeatherCnt(Float weatherCnt) {
        try {
            if (weatherCnt.floatValue() == FLOATNULL) {
                setWeatherCnt(INTNULL);
            } else {
                setWeatherCnt(weatherCnt.intValue());
            } // if (weatherCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWeatherCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return weatherCntError;
    } // method setWeatherCnt

    /**
     * Set the 'weatherCnt' class variable
     * @param  weatherCnt (String)
     */
    public int setWeatherCnt(String weatherCnt) {
        try {
            setWeatherCnt(new Integer(weatherCnt).intValue());
        } catch (Exception e) {
            setWeatherCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return weatherCntError;
    } // method setWeatherCnt

    /**
     * Called when an exception has occured
     * @param  weatherCnt (String)
     */
    private void setWeatherCntError (int weatherCnt, Exception e, int error) {
        this.weatherCnt = weatherCnt;
        weatherCntErrorMessage = e.toString();
        weatherCntError = error;
    } // method setWeatherCntError


    /**
     * Set the 'watcurrentsCnt' class variable
     * @param  watcurrentsCnt (int)
     */
    public int setWatcurrentsCnt(int watcurrentsCnt) {
        try {
            if ( ((watcurrentsCnt == INTNULL) ||
                  (watcurrentsCnt == INTNULL2)) ||
                !((watcurrentsCnt < WATCURRENTS_CNT_MN) ||
                  (watcurrentsCnt > WATCURRENTS_CNT_MX)) ) {
                this.watcurrentsCnt = watcurrentsCnt;
                watcurrentsCntError = ERROR_NORMAL;
            } else {
                throw watcurrentsCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatcurrentsCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watcurrentsCntError;
    } // method setWatcurrentsCnt

    /**
     * Set the 'watcurrentsCnt' class variable
     * @param  watcurrentsCnt (Integer)
     */
    public int setWatcurrentsCnt(Integer watcurrentsCnt) {
        try {
            setWatcurrentsCnt(watcurrentsCnt.intValue());
        } catch (Exception e) {
            setWatcurrentsCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watcurrentsCntError;
    } // method setWatcurrentsCnt

    /**
     * Set the 'watcurrentsCnt' class variable
     * @param  watcurrentsCnt (Float)
     */
    public int setWatcurrentsCnt(Float watcurrentsCnt) {
        try {
            if (watcurrentsCnt.floatValue() == FLOATNULL) {
                setWatcurrentsCnt(INTNULL);
            } else {
                setWatcurrentsCnt(watcurrentsCnt.intValue());
            } // if (watcurrentsCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatcurrentsCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watcurrentsCntError;
    } // method setWatcurrentsCnt

    /**
     * Set the 'watcurrentsCnt' class variable
     * @param  watcurrentsCnt (String)
     */
    public int setWatcurrentsCnt(String watcurrentsCnt) {
        try {
            setWatcurrentsCnt(new Integer(watcurrentsCnt).intValue());
        } catch (Exception e) {
            setWatcurrentsCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watcurrentsCntError;
    } // method setWatcurrentsCnt

    /**
     * Called when an exception has occured
     * @param  watcurrentsCnt (String)
     */
    private void setWatcurrentsCntError (int watcurrentsCnt, Exception e, int error) {
        this.watcurrentsCnt = watcurrentsCnt;
        watcurrentsCntErrorMessage = e.toString();
        watcurrentsCntError = error;
    } // method setWatcurrentsCntError


    /**
     * Set the 'watosdCnt' class variable
     * @param  watosdCnt (int)
     */
    public int setWatosdCnt(int watosdCnt) {
        try {
            if ( ((watosdCnt == INTNULL) ||
                  (watosdCnt == INTNULL2)) ||
                !((watosdCnt < WATOSD_CNT_MN) ||
                  (watosdCnt > WATOSD_CNT_MX)) ) {
                this.watosdCnt = watosdCnt;
                watosdCntError = ERROR_NORMAL;
            } else {
                throw watosdCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatosdCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watosdCntError;
    } // method setWatosdCnt

    /**
     * Set the 'watosdCnt' class variable
     * @param  watosdCnt (Integer)
     */
    public int setWatosdCnt(Integer watosdCnt) {
        try {
            setWatosdCnt(watosdCnt.intValue());
        } catch (Exception e) {
            setWatosdCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watosdCntError;
    } // method setWatosdCnt

    /**
     * Set the 'watosdCnt' class variable
     * @param  watosdCnt (Float)
     */
    public int setWatosdCnt(Float watosdCnt) {
        try {
            if (watosdCnt.floatValue() == FLOATNULL) {
                setWatosdCnt(INTNULL);
            } else {
                setWatosdCnt(watosdCnt.intValue());
            } // if (watosdCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatosdCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watosdCntError;
    } // method setWatosdCnt

    /**
     * Set the 'watosdCnt' class variable
     * @param  watosdCnt (String)
     */
    public int setWatosdCnt(String watosdCnt) {
        try {
            setWatosdCnt(new Integer(watosdCnt).intValue());
        } catch (Exception e) {
            setWatosdCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watosdCntError;
    } // method setWatosdCnt

    /**
     * Called when an exception has occured
     * @param  watosdCnt (String)
     */
    private void setWatosdCntError (int watosdCnt, Exception e, int error) {
        this.watosdCnt = watosdCnt;
        watosdCntErrorMessage = e.toString();
        watosdCntError = error;
    } // method setWatosdCntError


    /**
     * Set the 'watctdCnt' class variable
     * @param  watctdCnt (int)
     */
    public int setWatctdCnt(int watctdCnt) {
        try {
            if ( ((watctdCnt == INTNULL) ||
                  (watctdCnt == INTNULL2)) ||
                !((watctdCnt < WATCTD_CNT_MN) ||
                  (watctdCnt > WATCTD_CNT_MX)) ) {
                this.watctdCnt = watctdCnt;
                watctdCntError = ERROR_NORMAL;
            } else {
                throw watctdCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatctdCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watctdCntError;
    } // method setWatctdCnt

    /**
     * Set the 'watctdCnt' class variable
     * @param  watctdCnt (Integer)
     */
    public int setWatctdCnt(Integer watctdCnt) {
        try {
            setWatctdCnt(watctdCnt.intValue());
        } catch (Exception e) {
            setWatctdCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watctdCntError;
    } // method setWatctdCnt

    /**
     * Set the 'watctdCnt' class variable
     * @param  watctdCnt (Float)
     */
    public int setWatctdCnt(Float watctdCnt) {
        try {
            if (watctdCnt.floatValue() == FLOATNULL) {
                setWatctdCnt(INTNULL);
            } else {
                setWatctdCnt(watctdCnt.intValue());
            } // if (watctdCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatctdCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watctdCntError;
    } // method setWatctdCnt

    /**
     * Set the 'watctdCnt' class variable
     * @param  watctdCnt (String)
     */
    public int setWatctdCnt(String watctdCnt) {
        try {
            setWatctdCnt(new Integer(watctdCnt).intValue());
        } catch (Exception e) {
            setWatctdCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watctdCntError;
    } // method setWatctdCnt

    /**
     * Called when an exception has occured
     * @param  watctdCnt (String)
     */
    private void setWatctdCntError (int watctdCnt, Exception e, int error) {
        this.watctdCnt = watctdCnt;
        watctdCntErrorMessage = e.toString();
        watctdCntError = error;
    } // method setWatctdCntError


    /**
     * Set the 'watxbtCnt' class variable
     * @param  watxbtCnt (int)
     */
    public int setWatxbtCnt(int watxbtCnt) {
        try {
            if ( ((watxbtCnt == INTNULL) ||
                  (watxbtCnt == INTNULL2)) ||
                !((watxbtCnt < WATXBT_CNT_MN) ||
                  (watxbtCnt > WATXBT_CNT_MX)) ) {
                this.watxbtCnt = watxbtCnt;
                watxbtCntError = ERROR_NORMAL;
            } else {
                throw watxbtCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatxbtCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watxbtCntError;
    } // method setWatxbtCnt

    /**
     * Set the 'watxbtCnt' class variable
     * @param  watxbtCnt (Integer)
     */
    public int setWatxbtCnt(Integer watxbtCnt) {
        try {
            setWatxbtCnt(watxbtCnt.intValue());
        } catch (Exception e) {
            setWatxbtCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watxbtCntError;
    } // method setWatxbtCnt

    /**
     * Set the 'watxbtCnt' class variable
     * @param  watxbtCnt (Float)
     */
    public int setWatxbtCnt(Float watxbtCnt) {
        try {
            if (watxbtCnt.floatValue() == FLOATNULL) {
                setWatxbtCnt(INTNULL);
            } else {
                setWatxbtCnt(watxbtCnt.intValue());
            } // if (watxbtCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatxbtCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watxbtCntError;
    } // method setWatxbtCnt

    /**
     * Set the 'watxbtCnt' class variable
     * @param  watxbtCnt (String)
     */
    public int setWatxbtCnt(String watxbtCnt) {
        try {
            setWatxbtCnt(new Integer(watxbtCnt).intValue());
        } catch (Exception e) {
            setWatxbtCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watxbtCntError;
    } // method setWatxbtCnt

    /**
     * Called when an exception has occured
     * @param  watxbtCnt (String)
     */
    private void setWatxbtCntError (int watxbtCnt, Exception e, int error) {
        this.watxbtCnt = watxbtCnt;
        watxbtCntErrorMessage = e.toString();
        watxbtCntError = error;
    } // method setWatxbtCntError


    /**
     * Set the 'watmbtCnt' class variable
     * @param  watmbtCnt (int)
     */
    public int setWatmbtCnt(int watmbtCnt) {
        try {
            if ( ((watmbtCnt == INTNULL) ||
                  (watmbtCnt == INTNULL2)) ||
                !((watmbtCnt < WATMBT_CNT_MN) ||
                  (watmbtCnt > WATMBT_CNT_MX)) ) {
                this.watmbtCnt = watmbtCnt;
                watmbtCntError = ERROR_NORMAL;
            } else {
                throw watmbtCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatmbtCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watmbtCntError;
    } // method setWatmbtCnt

    /**
     * Set the 'watmbtCnt' class variable
     * @param  watmbtCnt (Integer)
     */
    public int setWatmbtCnt(Integer watmbtCnt) {
        try {
            setWatmbtCnt(watmbtCnt.intValue());
        } catch (Exception e) {
            setWatmbtCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watmbtCntError;
    } // method setWatmbtCnt

    /**
     * Set the 'watmbtCnt' class variable
     * @param  watmbtCnt (Float)
     */
    public int setWatmbtCnt(Float watmbtCnt) {
        try {
            if (watmbtCnt.floatValue() == FLOATNULL) {
                setWatmbtCnt(INTNULL);
            } else {
                setWatmbtCnt(watmbtCnt.intValue());
            } // if (watmbtCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatmbtCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watmbtCntError;
    } // method setWatmbtCnt

    /**
     * Set the 'watmbtCnt' class variable
     * @param  watmbtCnt (String)
     */
    public int setWatmbtCnt(String watmbtCnt) {
        try {
            setWatmbtCnt(new Integer(watmbtCnt).intValue());
        } catch (Exception e) {
            setWatmbtCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watmbtCntError;
    } // method setWatmbtCnt

    /**
     * Called when an exception has occured
     * @param  watmbtCnt (String)
     */
    private void setWatmbtCntError (int watmbtCnt, Exception e, int error) {
        this.watmbtCnt = watmbtCnt;
        watmbtCntErrorMessage = e.toString();
        watmbtCntError = error;
    } // method setWatmbtCntError


    /**
     * Set the 'watpflCnt' class variable
     * @param  watpflCnt (int)
     */
    public int setWatpflCnt(int watpflCnt) {
        try {
            if ( ((watpflCnt == INTNULL) ||
                  (watpflCnt == INTNULL2)) ||
                !((watpflCnt < WATPFL_CNT_MN) ||
                  (watpflCnt > WATPFL_CNT_MX)) ) {
                this.watpflCnt = watpflCnt;
                watpflCntError = ERROR_NORMAL;
            } else {
                throw watpflCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatpflCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watpflCntError;
    } // method setWatpflCnt

    /**
     * Set the 'watpflCnt' class variable
     * @param  watpflCnt (Integer)
     */
    public int setWatpflCnt(Integer watpflCnt) {
        try {
            setWatpflCnt(watpflCnt.intValue());
        } catch (Exception e) {
            setWatpflCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpflCntError;
    } // method setWatpflCnt

    /**
     * Set the 'watpflCnt' class variable
     * @param  watpflCnt (Float)
     */
    public int setWatpflCnt(Float watpflCnt) {
        try {
            if (watpflCnt.floatValue() == FLOATNULL) {
                setWatpflCnt(INTNULL);
            } else {
                setWatpflCnt(watpflCnt.intValue());
            } // if (watpflCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatpflCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpflCntError;
    } // method setWatpflCnt

    /**
     * Set the 'watpflCnt' class variable
     * @param  watpflCnt (String)
     */
    public int setWatpflCnt(String watpflCnt) {
        try {
            setWatpflCnt(new Integer(watpflCnt).intValue());
        } catch (Exception e) {
            setWatpflCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpflCntError;
    } // method setWatpflCnt

    /**
     * Called when an exception has occured
     * @param  watpflCnt (String)
     */
    private void setWatpflCntError (int watpflCnt, Exception e, int error) {
        this.watpflCnt = watpflCnt;
        watpflCntErrorMessage = e.toString();
        watpflCntError = error;
    } // method setWatpflCntError


    /**
     * Set the 'watphyStnCnt' class variable
     * @param  watphyStnCnt (int)
     */
    public int setWatphyStnCnt(int watphyStnCnt) {
        try {
            if ( ((watphyStnCnt == INTNULL) ||
                  (watphyStnCnt == INTNULL2)) ||
                !((watphyStnCnt < WATPHY_STN_CNT_MN) ||
                  (watphyStnCnt > WATPHY_STN_CNT_MX)) ) {
                this.watphyStnCnt = watphyStnCnt;
                watphyStnCntError = ERROR_NORMAL;
            } else {
                throw watphyStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatphyStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watphyStnCntError;
    } // method setWatphyStnCnt

    /**
     * Set the 'watphyStnCnt' class variable
     * @param  watphyStnCnt (Integer)
     */
    public int setWatphyStnCnt(Integer watphyStnCnt) {
        try {
            setWatphyStnCnt(watphyStnCnt.intValue());
        } catch (Exception e) {
            setWatphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyStnCntError;
    } // method setWatphyStnCnt

    /**
     * Set the 'watphyStnCnt' class variable
     * @param  watphyStnCnt (Float)
     */
    public int setWatphyStnCnt(Float watphyStnCnt) {
        try {
            if (watphyStnCnt.floatValue() == FLOATNULL) {
                setWatphyStnCnt(INTNULL);
            } else {
                setWatphyStnCnt(watphyStnCnt.intValue());
            } // if (watphyStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyStnCntError;
    } // method setWatphyStnCnt

    /**
     * Set the 'watphyStnCnt' class variable
     * @param  watphyStnCnt (String)
     */
    public int setWatphyStnCnt(String watphyStnCnt) {
        try {
            setWatphyStnCnt(new Integer(watphyStnCnt).intValue());
        } catch (Exception e) {
            setWatphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyStnCntError;
    } // method setWatphyStnCnt

    /**
     * Called when an exception has occured
     * @param  watphyStnCnt (String)
     */
    private void setWatphyStnCntError (int watphyStnCnt, Exception e, int error) {
        this.watphyStnCnt = watphyStnCnt;
        watphyStnCntErrorMessage = e.toString();
        watphyStnCntError = error;
    } // method setWatphyStnCntError


    /**
     * Set the 'watosdStnCnt' class variable
     * @param  watosdStnCnt (int)
     */
    public int setWatosdStnCnt(int watosdStnCnt) {
        try {
            if ( ((watosdStnCnt == INTNULL) ||
                  (watosdStnCnt == INTNULL2)) ||
                !((watosdStnCnt < WATOSD_STN_CNT_MN) ||
                  (watosdStnCnt > WATOSD_STN_CNT_MX)) ) {
                this.watosdStnCnt = watosdStnCnt;
                watosdStnCntError = ERROR_NORMAL;
            } else {
                throw watosdStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatosdStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watosdStnCntError;
    } // method setWatosdStnCnt

    /**
     * Set the 'watosdStnCnt' class variable
     * @param  watosdStnCnt (Integer)
     */
    public int setWatosdStnCnt(Integer watosdStnCnt) {
        try {
            setWatosdStnCnt(watosdStnCnt.intValue());
        } catch (Exception e) {
            setWatosdStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watosdStnCntError;
    } // method setWatosdStnCnt

    /**
     * Set the 'watosdStnCnt' class variable
     * @param  watosdStnCnt (Float)
     */
    public int setWatosdStnCnt(Float watosdStnCnt) {
        try {
            if (watosdStnCnt.floatValue() == FLOATNULL) {
                setWatosdStnCnt(INTNULL);
            } else {
                setWatosdStnCnt(watosdStnCnt.intValue());
            } // if (watosdStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatosdStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watosdStnCntError;
    } // method setWatosdStnCnt

    /**
     * Set the 'watosdStnCnt' class variable
     * @param  watosdStnCnt (String)
     */
    public int setWatosdStnCnt(String watosdStnCnt) {
        try {
            setWatosdStnCnt(new Integer(watosdStnCnt).intValue());
        } catch (Exception e) {
            setWatosdStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watosdStnCntError;
    } // method setWatosdStnCnt

    /**
     * Called when an exception has occured
     * @param  watosdStnCnt (String)
     */
    private void setWatosdStnCntError (int watosdStnCnt, Exception e, int error) {
        this.watosdStnCnt = watosdStnCnt;
        watosdStnCntErrorMessage = e.toString();
        watosdStnCntError = error;
    } // method setWatosdStnCntError


    /**
     * Set the 'watctdStnCnt' class variable
     * @param  watctdStnCnt (int)
     */
    public int setWatctdStnCnt(int watctdStnCnt) {
        try {
            if ( ((watctdStnCnt == INTNULL) ||
                  (watctdStnCnt == INTNULL2)) ||
                !((watctdStnCnt < WATCTD_STN_CNT_MN) ||
                  (watctdStnCnt > WATCTD_STN_CNT_MX)) ) {
                this.watctdStnCnt = watctdStnCnt;
                watctdStnCntError = ERROR_NORMAL;
            } else {
                throw watctdStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatctdStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watctdStnCntError;
    } // method setWatctdStnCnt

    /**
     * Set the 'watctdStnCnt' class variable
     * @param  watctdStnCnt (Integer)
     */
    public int setWatctdStnCnt(Integer watctdStnCnt) {
        try {
            setWatctdStnCnt(watctdStnCnt.intValue());
        } catch (Exception e) {
            setWatctdStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watctdStnCntError;
    } // method setWatctdStnCnt

    /**
     * Set the 'watctdStnCnt' class variable
     * @param  watctdStnCnt (Float)
     */
    public int setWatctdStnCnt(Float watctdStnCnt) {
        try {
            if (watctdStnCnt.floatValue() == FLOATNULL) {
                setWatctdStnCnt(INTNULL);
            } else {
                setWatctdStnCnt(watctdStnCnt.intValue());
            } // if (watctdStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatctdStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watctdStnCntError;
    } // method setWatctdStnCnt

    /**
     * Set the 'watctdStnCnt' class variable
     * @param  watctdStnCnt (String)
     */
    public int setWatctdStnCnt(String watctdStnCnt) {
        try {
            setWatctdStnCnt(new Integer(watctdStnCnt).intValue());
        } catch (Exception e) {
            setWatctdStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watctdStnCntError;
    } // method setWatctdStnCnt

    /**
     * Called when an exception has occured
     * @param  watctdStnCnt (String)
     */
    private void setWatctdStnCntError (int watctdStnCnt, Exception e, int error) {
        this.watctdStnCnt = watctdStnCnt;
        watctdStnCntErrorMessage = e.toString();
        watctdStnCntError = error;
    } // method setWatctdStnCntError


    /**
     * Set the 'watxbtStnCnt' class variable
     * @param  watxbtStnCnt (int)
     */
    public int setWatxbtStnCnt(int watxbtStnCnt) {
        try {
            if ( ((watxbtStnCnt == INTNULL) ||
                  (watxbtStnCnt == INTNULL2)) ||
                !((watxbtStnCnt < WATXBT_STN_CNT_MN) ||
                  (watxbtStnCnt > WATXBT_STN_CNT_MX)) ) {
                this.watxbtStnCnt = watxbtStnCnt;
                watxbtStnCntError = ERROR_NORMAL;
            } else {
                throw watxbtStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatxbtStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watxbtStnCntError;
    } // method setWatxbtStnCnt

    /**
     * Set the 'watxbtStnCnt' class variable
     * @param  watxbtStnCnt (Integer)
     */
    public int setWatxbtStnCnt(Integer watxbtStnCnt) {
        try {
            setWatxbtStnCnt(watxbtStnCnt.intValue());
        } catch (Exception e) {
            setWatxbtStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watxbtStnCntError;
    } // method setWatxbtStnCnt

    /**
     * Set the 'watxbtStnCnt' class variable
     * @param  watxbtStnCnt (Float)
     */
    public int setWatxbtStnCnt(Float watxbtStnCnt) {
        try {
            if (watxbtStnCnt.floatValue() == FLOATNULL) {
                setWatxbtStnCnt(INTNULL);
            } else {
                setWatxbtStnCnt(watxbtStnCnt.intValue());
            } // if (watxbtStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatxbtStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watxbtStnCntError;
    } // method setWatxbtStnCnt

    /**
     * Set the 'watxbtStnCnt' class variable
     * @param  watxbtStnCnt (String)
     */
    public int setWatxbtStnCnt(String watxbtStnCnt) {
        try {
            setWatxbtStnCnt(new Integer(watxbtStnCnt).intValue());
        } catch (Exception e) {
            setWatxbtStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watxbtStnCntError;
    } // method setWatxbtStnCnt

    /**
     * Called when an exception has occured
     * @param  watxbtStnCnt (String)
     */
    private void setWatxbtStnCntError (int watxbtStnCnt, Exception e, int error) {
        this.watxbtStnCnt = watxbtStnCnt;
        watxbtStnCntErrorMessage = e.toString();
        watxbtStnCntError = error;
    } // method setWatxbtStnCntError


    /**
     * Set the 'watmbtStnCnt' class variable
     * @param  watmbtStnCnt (int)
     */
    public int setWatmbtStnCnt(int watmbtStnCnt) {
        try {
            if ( ((watmbtStnCnt == INTNULL) ||
                  (watmbtStnCnt == INTNULL2)) ||
                !((watmbtStnCnt < WATMBT_STN_CNT_MN) ||
                  (watmbtStnCnt > WATMBT_STN_CNT_MX)) ) {
                this.watmbtStnCnt = watmbtStnCnt;
                watmbtStnCntError = ERROR_NORMAL;
            } else {
                throw watmbtStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatmbtStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watmbtStnCntError;
    } // method setWatmbtStnCnt

    /**
     * Set the 'watmbtStnCnt' class variable
     * @param  watmbtStnCnt (Integer)
     */
    public int setWatmbtStnCnt(Integer watmbtStnCnt) {
        try {
            setWatmbtStnCnt(watmbtStnCnt.intValue());
        } catch (Exception e) {
            setWatmbtStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watmbtStnCntError;
    } // method setWatmbtStnCnt

    /**
     * Set the 'watmbtStnCnt' class variable
     * @param  watmbtStnCnt (Float)
     */
    public int setWatmbtStnCnt(Float watmbtStnCnt) {
        try {
            if (watmbtStnCnt.floatValue() == FLOATNULL) {
                setWatmbtStnCnt(INTNULL);
            } else {
                setWatmbtStnCnt(watmbtStnCnt.intValue());
            } // if (watmbtStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatmbtStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watmbtStnCntError;
    } // method setWatmbtStnCnt

    /**
     * Set the 'watmbtStnCnt' class variable
     * @param  watmbtStnCnt (String)
     */
    public int setWatmbtStnCnt(String watmbtStnCnt) {
        try {
            setWatmbtStnCnt(new Integer(watmbtStnCnt).intValue());
        } catch (Exception e) {
            setWatmbtStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watmbtStnCntError;
    } // method setWatmbtStnCnt

    /**
     * Called when an exception has occured
     * @param  watmbtStnCnt (String)
     */
    private void setWatmbtStnCntError (int watmbtStnCnt, Exception e, int error) {
        this.watmbtStnCnt = watmbtStnCnt;
        watmbtStnCntErrorMessage = e.toString();
        watmbtStnCntError = error;
    } // method setWatmbtStnCntError


    /**
     * Set the 'watpflStnCnt' class variable
     * @param  watpflStnCnt (int)
     */
    public int setWatpflStnCnt(int watpflStnCnt) {
        try {
            if ( ((watpflStnCnt == INTNULL) ||
                  (watpflStnCnt == INTNULL2)) ||
                !((watpflStnCnt < WATPFL_STN_CNT_MN) ||
                  (watpflStnCnt > WATPFL_STN_CNT_MX)) ) {
                this.watpflStnCnt = watpflStnCnt;
                watpflStnCntError = ERROR_NORMAL;
            } else {
                throw watpflStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatpflStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watpflStnCntError;
    } // method setWatpflStnCnt

    /**
     * Set the 'watpflStnCnt' class variable
     * @param  watpflStnCnt (Integer)
     */
    public int setWatpflStnCnt(Integer watpflStnCnt) {
        try {
            setWatpflStnCnt(watpflStnCnt.intValue());
        } catch (Exception e) {
            setWatpflStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpflStnCntError;
    } // method setWatpflStnCnt

    /**
     * Set the 'watpflStnCnt' class variable
     * @param  watpflStnCnt (Float)
     */
    public int setWatpflStnCnt(Float watpflStnCnt) {
        try {
            if (watpflStnCnt.floatValue() == FLOATNULL) {
                setWatpflStnCnt(INTNULL);
            } else {
                setWatpflStnCnt(watpflStnCnt.intValue());
            } // if (watpflStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatpflStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpflStnCntError;
    } // method setWatpflStnCnt

    /**
     * Set the 'watpflStnCnt' class variable
     * @param  watpflStnCnt (String)
     */
    public int setWatpflStnCnt(String watpflStnCnt) {
        try {
            setWatpflStnCnt(new Integer(watpflStnCnt).intValue());
        } catch (Exception e) {
            setWatpflStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpflStnCntError;
    } // method setWatpflStnCnt

    /**
     * Called when an exception has occured
     * @param  watpflStnCnt (String)
     */
    private void setWatpflStnCntError (int watpflStnCnt, Exception e, int error) {
        this.watpflStnCnt = watpflStnCnt;
        watpflStnCntErrorMessage = e.toString();
        watpflStnCntError = error;
    } // method setWatpflStnCntError


    /**
     * Set the 'watnutStnCnt' class variable
     * @param  watnutStnCnt (int)
     */
    public int setWatnutStnCnt(int watnutStnCnt) {
        try {
            if ( ((watnutStnCnt == INTNULL) ||
                  (watnutStnCnt == INTNULL2)) ||
                !((watnutStnCnt < WATNUT_STN_CNT_MN) ||
                  (watnutStnCnt > WATNUT_STN_CNT_MX)) ) {
                this.watnutStnCnt = watnutStnCnt;
                watnutStnCntError = ERROR_NORMAL;
            } else {
                throw watnutStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatnutStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watnutStnCntError;
    } // method setWatnutStnCnt

    /**
     * Set the 'watnutStnCnt' class variable
     * @param  watnutStnCnt (Integer)
     */
    public int setWatnutStnCnt(Integer watnutStnCnt) {
        try {
            setWatnutStnCnt(watnutStnCnt.intValue());
        } catch (Exception e) {
            setWatnutStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watnutStnCntError;
    } // method setWatnutStnCnt

    /**
     * Set the 'watnutStnCnt' class variable
     * @param  watnutStnCnt (Float)
     */
    public int setWatnutStnCnt(Float watnutStnCnt) {
        try {
            if (watnutStnCnt.floatValue() == FLOATNULL) {
                setWatnutStnCnt(INTNULL);
            } else {
                setWatnutStnCnt(watnutStnCnt.intValue());
            } // if (watnutStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatnutStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watnutStnCntError;
    } // method setWatnutStnCnt

    /**
     * Set the 'watnutStnCnt' class variable
     * @param  watnutStnCnt (String)
     */
    public int setWatnutStnCnt(String watnutStnCnt) {
        try {
            setWatnutStnCnt(new Integer(watnutStnCnt).intValue());
        } catch (Exception e) {
            setWatnutStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watnutStnCntError;
    } // method setWatnutStnCnt

    /**
     * Called when an exception has occured
     * @param  watnutStnCnt (String)
     */
    private void setWatnutStnCntError (int watnutStnCnt, Exception e, int error) {
        this.watnutStnCnt = watnutStnCnt;
        watnutStnCntErrorMessage = e.toString();
        watnutStnCntError = error;
    } // method setWatnutStnCntError


    /**
     * Set the 'watpolStnCnt' class variable
     * @param  watpolStnCnt (int)
     */
    public int setWatpolStnCnt(int watpolStnCnt) {
        try {
            if ( ((watpolStnCnt == INTNULL) ||
                  (watpolStnCnt == INTNULL2)) ||
                !((watpolStnCnt < WATPOL_STN_CNT_MN) ||
                  (watpolStnCnt > WATPOL_STN_CNT_MX)) ) {
                this.watpolStnCnt = watpolStnCnt;
                watpolStnCntError = ERROR_NORMAL;
            } else {
                throw watpolStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatpolStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watpolStnCntError;
    } // method setWatpolStnCnt

    /**
     * Set the 'watpolStnCnt' class variable
     * @param  watpolStnCnt (Integer)
     */
    public int setWatpolStnCnt(Integer watpolStnCnt) {
        try {
            setWatpolStnCnt(watpolStnCnt.intValue());
        } catch (Exception e) {
            setWatpolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpolStnCntError;
    } // method setWatpolStnCnt

    /**
     * Set the 'watpolStnCnt' class variable
     * @param  watpolStnCnt (Float)
     */
    public int setWatpolStnCnt(Float watpolStnCnt) {
        try {
            if (watpolStnCnt.floatValue() == FLOATNULL) {
                setWatpolStnCnt(INTNULL);
            } else {
                setWatpolStnCnt(watpolStnCnt.intValue());
            } // if (watpolStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatpolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpolStnCntError;
    } // method setWatpolStnCnt

    /**
     * Set the 'watpolStnCnt' class variable
     * @param  watpolStnCnt (String)
     */
    public int setWatpolStnCnt(String watpolStnCnt) {
        try {
            setWatpolStnCnt(new Integer(watpolStnCnt).intValue());
        } catch (Exception e) {
            setWatpolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watpolStnCntError;
    } // method setWatpolStnCnt

    /**
     * Called when an exception has occured
     * @param  watpolStnCnt (String)
     */
    private void setWatpolStnCntError (int watpolStnCnt, Exception e, int error) {
        this.watpolStnCnt = watpolStnCnt;
        watpolStnCntErrorMessage = e.toString();
        watpolStnCntError = error;
    } // method setWatpolStnCntError


    /**
     * Set the 'watchemStnCnt' class variable
     * @param  watchemStnCnt (int)
     */
    public int setWatchemStnCnt(int watchemStnCnt) {
        try {
            if ( ((watchemStnCnt == INTNULL) ||
                  (watchemStnCnt == INTNULL2)) ||
                !((watchemStnCnt < WATCHEM_STN_CNT_MN) ||
                  (watchemStnCnt > WATCHEM_STN_CNT_MX)) ) {
                this.watchemStnCnt = watchemStnCnt;
                watchemStnCntError = ERROR_NORMAL;
            } else {
                throw watchemStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatchemStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watchemStnCntError;
    } // method setWatchemStnCnt

    /**
     * Set the 'watchemStnCnt' class variable
     * @param  watchemStnCnt (Integer)
     */
    public int setWatchemStnCnt(Integer watchemStnCnt) {
        try {
            setWatchemStnCnt(watchemStnCnt.intValue());
        } catch (Exception e) {
            setWatchemStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchemStnCntError;
    } // method setWatchemStnCnt

    /**
     * Set the 'watchemStnCnt' class variable
     * @param  watchemStnCnt (Float)
     */
    public int setWatchemStnCnt(Float watchemStnCnt) {
        try {
            if (watchemStnCnt.floatValue() == FLOATNULL) {
                setWatchemStnCnt(INTNULL);
            } else {
                setWatchemStnCnt(watchemStnCnt.intValue());
            } // if (watchemStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatchemStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchemStnCntError;
    } // method setWatchemStnCnt

    /**
     * Set the 'watchemStnCnt' class variable
     * @param  watchemStnCnt (String)
     */
    public int setWatchemStnCnt(String watchemStnCnt) {
        try {
            setWatchemStnCnt(new Integer(watchemStnCnt).intValue());
        } catch (Exception e) {
            setWatchemStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchemStnCntError;
    } // method setWatchemStnCnt

    /**
     * Called when an exception has occured
     * @param  watchemStnCnt (String)
     */
    private void setWatchemStnCntError (int watchemStnCnt, Exception e, int error) {
        this.watchemStnCnt = watchemStnCnt;
        watchemStnCntErrorMessage = e.toString();
        watchemStnCntError = error;
    } // method setWatchemStnCntError


    /**
     * Set the 'watchlStnCnt' class variable
     * @param  watchlStnCnt (int)
     */
    public int setWatchlStnCnt(int watchlStnCnt) {
        try {
            if ( ((watchlStnCnt == INTNULL) ||
                  (watchlStnCnt == INTNULL2)) ||
                !((watchlStnCnt < WATCHL_STN_CNT_MN) ||
                  (watchlStnCnt > WATCHL_STN_CNT_MX)) ) {
                this.watchlStnCnt = watchlStnCnt;
                watchlStnCntError = ERROR_NORMAL;
            } else {
                throw watchlStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatchlStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watchlStnCntError;
    } // method setWatchlStnCnt

    /**
     * Set the 'watchlStnCnt' class variable
     * @param  watchlStnCnt (Integer)
     */
    public int setWatchlStnCnt(Integer watchlStnCnt) {
        try {
            setWatchlStnCnt(watchlStnCnt.intValue());
        } catch (Exception e) {
            setWatchlStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchlStnCntError;
    } // method setWatchlStnCnt

    /**
     * Set the 'watchlStnCnt' class variable
     * @param  watchlStnCnt (Float)
     */
    public int setWatchlStnCnt(Float watchlStnCnt) {
        try {
            if (watchlStnCnt.floatValue() == FLOATNULL) {
                setWatchlStnCnt(INTNULL);
            } else {
                setWatchlStnCnt(watchlStnCnt.intValue());
            } // if (watchlStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatchlStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchlStnCntError;
    } // method setWatchlStnCnt

    /**
     * Set the 'watchlStnCnt' class variable
     * @param  watchlStnCnt (String)
     */
    public int setWatchlStnCnt(String watchlStnCnt) {
        try {
            setWatchlStnCnt(new Integer(watchlStnCnt).intValue());
        } catch (Exception e) {
            setWatchlStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watchlStnCntError;
    } // method setWatchlStnCnt

    /**
     * Called when an exception has occured
     * @param  watchlStnCnt (String)
     */
    private void setWatchlStnCntError (int watchlStnCnt, Exception e, int error) {
        this.watchlStnCnt = watchlStnCnt;
        watchlStnCntErrorMessage = e.toString();
        watchlStnCntError = error;
    } // method setWatchlStnCntError


    /**
     * Set the 'sedphyStnCnt' class variable
     * @param  sedphyStnCnt (int)
     */
    public int setSedphyStnCnt(int sedphyStnCnt) {
        try {
            if ( ((sedphyStnCnt == INTNULL) ||
                  (sedphyStnCnt == INTNULL2)) ||
                !((sedphyStnCnt < SEDPHY_STN_CNT_MN) ||
                  (sedphyStnCnt > SEDPHY_STN_CNT_MX)) ) {
                this.sedphyStnCnt = sedphyStnCnt;
                sedphyStnCntError = ERROR_NORMAL;
            } else {
                throw sedphyStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedphyStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedphyStnCntError;
    } // method setSedphyStnCnt

    /**
     * Set the 'sedphyStnCnt' class variable
     * @param  sedphyStnCnt (Integer)
     */
    public int setSedphyStnCnt(Integer sedphyStnCnt) {
        try {
            setSedphyStnCnt(sedphyStnCnt.intValue());
        } catch (Exception e) {
            setSedphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyStnCntError;
    } // method setSedphyStnCnt

    /**
     * Set the 'sedphyStnCnt' class variable
     * @param  sedphyStnCnt (Float)
     */
    public int setSedphyStnCnt(Float sedphyStnCnt) {
        try {
            if (sedphyStnCnt.floatValue() == FLOATNULL) {
                setSedphyStnCnt(INTNULL);
            } else {
                setSedphyStnCnt(sedphyStnCnt.intValue());
            } // if (sedphyStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyStnCntError;
    } // method setSedphyStnCnt

    /**
     * Set the 'sedphyStnCnt' class variable
     * @param  sedphyStnCnt (String)
     */
    public int setSedphyStnCnt(String sedphyStnCnt) {
        try {
            setSedphyStnCnt(new Integer(sedphyStnCnt).intValue());
        } catch (Exception e) {
            setSedphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyStnCntError;
    } // method setSedphyStnCnt

    /**
     * Called when an exception has occured
     * @param  sedphyStnCnt (String)
     */
    private void setSedphyStnCntError (int sedphyStnCnt, Exception e, int error) {
        this.sedphyStnCnt = sedphyStnCnt;
        sedphyStnCntErrorMessage = e.toString();
        sedphyStnCntError = error;
    } // method setSedphyStnCntError


    /**
     * Set the 'sedpesStnCnt' class variable
     * @param  sedpesStnCnt (int)
     */
    public int setSedpesStnCnt(int sedpesStnCnt) {
        try {
            if ( ((sedpesStnCnt == INTNULL) ||
                  (sedpesStnCnt == INTNULL2)) ||
                !((sedpesStnCnt < SEDPES_STN_CNT_MN) ||
                  (sedpesStnCnt > SEDPES_STN_CNT_MX)) ) {
                this.sedpesStnCnt = sedpesStnCnt;
                sedpesStnCntError = ERROR_NORMAL;
            } else {
                throw sedpesStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedpesStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedpesStnCntError;
    } // method setSedpesStnCnt

    /**
     * Set the 'sedpesStnCnt' class variable
     * @param  sedpesStnCnt (Integer)
     */
    public int setSedpesStnCnt(Integer sedpesStnCnt) {
        try {
            setSedpesStnCnt(sedpesStnCnt.intValue());
        } catch (Exception e) {
            setSedpesStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpesStnCntError;
    } // method setSedpesStnCnt

    /**
     * Set the 'sedpesStnCnt' class variable
     * @param  sedpesStnCnt (Float)
     */
    public int setSedpesStnCnt(Float sedpesStnCnt) {
        try {
            if (sedpesStnCnt.floatValue() == FLOATNULL) {
                setSedpesStnCnt(INTNULL);
            } else {
                setSedpesStnCnt(sedpesStnCnt.intValue());
            } // if (sedpesStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedpesStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpesStnCntError;
    } // method setSedpesStnCnt

    /**
     * Set the 'sedpesStnCnt' class variable
     * @param  sedpesStnCnt (String)
     */
    public int setSedpesStnCnt(String sedpesStnCnt) {
        try {
            setSedpesStnCnt(new Integer(sedpesStnCnt).intValue());
        } catch (Exception e) {
            setSedpesStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpesStnCntError;
    } // method setSedpesStnCnt

    /**
     * Called when an exception has occured
     * @param  sedpesStnCnt (String)
     */
    private void setSedpesStnCntError (int sedpesStnCnt, Exception e, int error) {
        this.sedpesStnCnt = sedpesStnCnt;
        sedpesStnCntErrorMessage = e.toString();
        sedpesStnCntError = error;
    } // method setSedpesStnCntError


    /**
     * Set the 'sedpolStnCnt' class variable
     * @param  sedpolStnCnt (int)
     */
    public int setSedpolStnCnt(int sedpolStnCnt) {
        try {
            if ( ((sedpolStnCnt == INTNULL) ||
                  (sedpolStnCnt == INTNULL2)) ||
                !((sedpolStnCnt < SEDPOL_STN_CNT_MN) ||
                  (sedpolStnCnt > SEDPOL_STN_CNT_MX)) ) {
                this.sedpolStnCnt = sedpolStnCnt;
                sedpolStnCntError = ERROR_NORMAL;
            } else {
                throw sedpolStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedpolStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedpolStnCntError;
    } // method setSedpolStnCnt

    /**
     * Set the 'sedpolStnCnt' class variable
     * @param  sedpolStnCnt (Integer)
     */
    public int setSedpolStnCnt(Integer sedpolStnCnt) {
        try {
            setSedpolStnCnt(sedpolStnCnt.intValue());
        } catch (Exception e) {
            setSedpolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpolStnCntError;
    } // method setSedpolStnCnt

    /**
     * Set the 'sedpolStnCnt' class variable
     * @param  sedpolStnCnt (Float)
     */
    public int setSedpolStnCnt(Float sedpolStnCnt) {
        try {
            if (sedpolStnCnt.floatValue() == FLOATNULL) {
                setSedpolStnCnt(INTNULL);
            } else {
                setSedpolStnCnt(sedpolStnCnt.intValue());
            } // if (sedpolStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedpolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpolStnCntError;
    } // method setSedpolStnCnt

    /**
     * Set the 'sedpolStnCnt' class variable
     * @param  sedpolStnCnt (String)
     */
    public int setSedpolStnCnt(String sedpolStnCnt) {
        try {
            setSedpolStnCnt(new Integer(sedpolStnCnt).intValue());
        } catch (Exception e) {
            setSedpolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedpolStnCntError;
    } // method setSedpolStnCnt

    /**
     * Called when an exception has occured
     * @param  sedpolStnCnt (String)
     */
    private void setSedpolStnCntError (int sedpolStnCnt, Exception e, int error) {
        this.sedpolStnCnt = sedpolStnCnt;
        sedpolStnCntErrorMessage = e.toString();
        sedpolStnCntError = error;
    } // method setSedpolStnCntError


    /**
     * Set the 'sedchemStnCnt' class variable
     * @param  sedchemStnCnt (int)
     */
    public int setSedchemStnCnt(int sedchemStnCnt) {
        try {
            if ( ((sedchemStnCnt == INTNULL) ||
                  (sedchemStnCnt == INTNULL2)) ||
                !((sedchemStnCnt < SEDCHEM_STN_CNT_MN) ||
                  (sedchemStnCnt > SEDCHEM_STN_CNT_MX)) ) {
                this.sedchemStnCnt = sedchemStnCnt;
                sedchemStnCntError = ERROR_NORMAL;
            } else {
                throw sedchemStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedchemStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedchemStnCntError;
    } // method setSedchemStnCnt

    /**
     * Set the 'sedchemStnCnt' class variable
     * @param  sedchemStnCnt (Integer)
     */
    public int setSedchemStnCnt(Integer sedchemStnCnt) {
        try {
            setSedchemStnCnt(sedchemStnCnt.intValue());
        } catch (Exception e) {
            setSedchemStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedchemStnCntError;
    } // method setSedchemStnCnt

    /**
     * Set the 'sedchemStnCnt' class variable
     * @param  sedchemStnCnt (Float)
     */
    public int setSedchemStnCnt(Float sedchemStnCnt) {
        try {
            if (sedchemStnCnt.floatValue() == FLOATNULL) {
                setSedchemStnCnt(INTNULL);
            } else {
                setSedchemStnCnt(sedchemStnCnt.intValue());
            } // if (sedchemStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedchemStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedchemStnCntError;
    } // method setSedchemStnCnt

    /**
     * Set the 'sedchemStnCnt' class variable
     * @param  sedchemStnCnt (String)
     */
    public int setSedchemStnCnt(String sedchemStnCnt) {
        try {
            setSedchemStnCnt(new Integer(sedchemStnCnt).intValue());
        } catch (Exception e) {
            setSedchemStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedchemStnCntError;
    } // method setSedchemStnCnt

    /**
     * Called when an exception has occured
     * @param  sedchemStnCnt (String)
     */
    private void setSedchemStnCntError (int sedchemStnCnt, Exception e, int error) {
        this.sedchemStnCnt = sedchemStnCnt;
        sedchemStnCntErrorMessage = e.toString();
        sedchemStnCntError = error;
    } // method setSedchemStnCntError


    /**
     * Set the 'sedtaxStnCnt' class variable
     * @param  sedtaxStnCnt (int)
     */
    public int setSedtaxStnCnt(int sedtaxStnCnt) {
        try {
            if ( ((sedtaxStnCnt == INTNULL) ||
                  (sedtaxStnCnt == INTNULL2)) ||
                !((sedtaxStnCnt < SEDTAX_STN_CNT_MN) ||
                  (sedtaxStnCnt > SEDTAX_STN_CNT_MX)) ) {
                this.sedtaxStnCnt = sedtaxStnCnt;
                sedtaxStnCntError = ERROR_NORMAL;
            } else {
                throw sedtaxStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedtaxStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedtaxStnCntError;
    } // method setSedtaxStnCnt

    /**
     * Set the 'sedtaxStnCnt' class variable
     * @param  sedtaxStnCnt (Integer)
     */
    public int setSedtaxStnCnt(Integer sedtaxStnCnt) {
        try {
            setSedtaxStnCnt(sedtaxStnCnt.intValue());
        } catch (Exception e) {
            setSedtaxStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedtaxStnCntError;
    } // method setSedtaxStnCnt

    /**
     * Set the 'sedtaxStnCnt' class variable
     * @param  sedtaxStnCnt (Float)
     */
    public int setSedtaxStnCnt(Float sedtaxStnCnt) {
        try {
            if (sedtaxStnCnt.floatValue() == FLOATNULL) {
                setSedtaxStnCnt(INTNULL);
            } else {
                setSedtaxStnCnt(sedtaxStnCnt.intValue());
            } // if (sedtaxStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedtaxStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedtaxStnCntError;
    } // method setSedtaxStnCnt

    /**
     * Set the 'sedtaxStnCnt' class variable
     * @param  sedtaxStnCnt (String)
     */
    public int setSedtaxStnCnt(String sedtaxStnCnt) {
        try {
            setSedtaxStnCnt(new Integer(sedtaxStnCnt).intValue());
        } catch (Exception e) {
            setSedtaxStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedtaxStnCntError;
    } // method setSedtaxStnCnt

    /**
     * Called when an exception has occured
     * @param  sedtaxStnCnt (String)
     */
    private void setSedtaxStnCntError (int sedtaxStnCnt, Exception e, int error) {
        this.sedtaxStnCnt = sedtaxStnCnt;
        sedtaxStnCntErrorMessage = e.toString();
        sedtaxStnCntError = error;
    } // method setSedtaxStnCntError


    /**
     * Set the 'plaphyStnCnt' class variable
     * @param  plaphyStnCnt (int)
     */
    public int setPlaphyStnCnt(int plaphyStnCnt) {
        try {
            if ( ((plaphyStnCnt == INTNULL) ||
                  (plaphyStnCnt == INTNULL2)) ||
                !((plaphyStnCnt < PLAPHY_STN_CNT_MN) ||
                  (plaphyStnCnt > PLAPHY_STN_CNT_MX)) ) {
                this.plaphyStnCnt = plaphyStnCnt;
                plaphyStnCntError = ERROR_NORMAL;
            } else {
                throw plaphyStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlaphyStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plaphyStnCntError;
    } // method setPlaphyStnCnt

    /**
     * Set the 'plaphyStnCnt' class variable
     * @param  plaphyStnCnt (Integer)
     */
    public int setPlaphyStnCnt(Integer plaphyStnCnt) {
        try {
            setPlaphyStnCnt(plaphyStnCnt.intValue());
        } catch (Exception e) {
            setPlaphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyStnCntError;
    } // method setPlaphyStnCnt

    /**
     * Set the 'plaphyStnCnt' class variable
     * @param  plaphyStnCnt (Float)
     */
    public int setPlaphyStnCnt(Float plaphyStnCnt) {
        try {
            if (plaphyStnCnt.floatValue() == FLOATNULL) {
                setPlaphyStnCnt(INTNULL);
            } else {
                setPlaphyStnCnt(plaphyStnCnt.intValue());
            } // if (plaphyStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlaphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyStnCntError;
    } // method setPlaphyStnCnt

    /**
     * Set the 'plaphyStnCnt' class variable
     * @param  plaphyStnCnt (String)
     */
    public int setPlaphyStnCnt(String plaphyStnCnt) {
        try {
            setPlaphyStnCnt(new Integer(plaphyStnCnt).intValue());
        } catch (Exception e) {
            setPlaphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyStnCntError;
    } // method setPlaphyStnCnt

    /**
     * Called when an exception has occured
     * @param  plaphyStnCnt (String)
     */
    private void setPlaphyStnCntError (int plaphyStnCnt, Exception e, int error) {
        this.plaphyStnCnt = plaphyStnCnt;
        plaphyStnCntErrorMessage = e.toString();
        plaphyStnCntError = error;
    } // method setPlaphyStnCntError


    /**
     * Set the 'plapesStnCnt' class variable
     * @param  plapesStnCnt (int)
     */
    public int setPlapesStnCnt(int plapesStnCnt) {
        try {
            if ( ((plapesStnCnt == INTNULL) ||
                  (plapesStnCnt == INTNULL2)) ||
                !((plapesStnCnt < PLAPES_STN_CNT_MN) ||
                  (plapesStnCnt > PLAPES_STN_CNT_MX)) ) {
                this.plapesStnCnt = plapesStnCnt;
                plapesStnCntError = ERROR_NORMAL;
            } else {
                throw plapesStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlapesStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plapesStnCntError;
    } // method setPlapesStnCnt

    /**
     * Set the 'plapesStnCnt' class variable
     * @param  plapesStnCnt (Integer)
     */
    public int setPlapesStnCnt(Integer plapesStnCnt) {
        try {
            setPlapesStnCnt(plapesStnCnt.intValue());
        } catch (Exception e) {
            setPlapesStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapesStnCntError;
    } // method setPlapesStnCnt

    /**
     * Set the 'plapesStnCnt' class variable
     * @param  plapesStnCnt (Float)
     */
    public int setPlapesStnCnt(Float plapesStnCnt) {
        try {
            if (plapesStnCnt.floatValue() == FLOATNULL) {
                setPlapesStnCnt(INTNULL);
            } else {
                setPlapesStnCnt(plapesStnCnt.intValue());
            } // if (plapesStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlapesStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapesStnCntError;
    } // method setPlapesStnCnt

    /**
     * Set the 'plapesStnCnt' class variable
     * @param  plapesStnCnt (String)
     */
    public int setPlapesStnCnt(String plapesStnCnt) {
        try {
            setPlapesStnCnt(new Integer(plapesStnCnt).intValue());
        } catch (Exception e) {
            setPlapesStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapesStnCntError;
    } // method setPlapesStnCnt

    /**
     * Called when an exception has occured
     * @param  plapesStnCnt (String)
     */
    private void setPlapesStnCntError (int plapesStnCnt, Exception e, int error) {
        this.plapesStnCnt = plapesStnCnt;
        plapesStnCntErrorMessage = e.toString();
        plapesStnCntError = error;
    } // method setPlapesStnCntError


    /**
     * Set the 'plapolStnCnt' class variable
     * @param  plapolStnCnt (int)
     */
    public int setPlapolStnCnt(int plapolStnCnt) {
        try {
            if ( ((plapolStnCnt == INTNULL) ||
                  (plapolStnCnt == INTNULL2)) ||
                !((plapolStnCnt < PLAPOL_STN_CNT_MN) ||
                  (plapolStnCnt > PLAPOL_STN_CNT_MX)) ) {
                this.plapolStnCnt = plapolStnCnt;
                plapolStnCntError = ERROR_NORMAL;
            } else {
                throw plapolStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlapolStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plapolStnCntError;
    } // method setPlapolStnCnt

    /**
     * Set the 'plapolStnCnt' class variable
     * @param  plapolStnCnt (Integer)
     */
    public int setPlapolStnCnt(Integer plapolStnCnt) {
        try {
            setPlapolStnCnt(plapolStnCnt.intValue());
        } catch (Exception e) {
            setPlapolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapolStnCntError;
    } // method setPlapolStnCnt

    /**
     * Set the 'plapolStnCnt' class variable
     * @param  plapolStnCnt (Float)
     */
    public int setPlapolStnCnt(Float plapolStnCnt) {
        try {
            if (plapolStnCnt.floatValue() == FLOATNULL) {
                setPlapolStnCnt(INTNULL);
            } else {
                setPlapolStnCnt(plapolStnCnt.intValue());
            } // if (plapolStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlapolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapolStnCntError;
    } // method setPlapolStnCnt

    /**
     * Set the 'plapolStnCnt' class variable
     * @param  plapolStnCnt (String)
     */
    public int setPlapolStnCnt(String plapolStnCnt) {
        try {
            setPlapolStnCnt(new Integer(plapolStnCnt).intValue());
        } catch (Exception e) {
            setPlapolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plapolStnCntError;
    } // method setPlapolStnCnt

    /**
     * Called when an exception has occured
     * @param  plapolStnCnt (String)
     */
    private void setPlapolStnCntError (int plapolStnCnt, Exception e, int error) {
        this.plapolStnCnt = plapolStnCnt;
        plapolStnCntErrorMessage = e.toString();
        plapolStnCntError = error;
    } // method setPlapolStnCntError


    /**
     * Set the 'plataxStnCnt' class variable
     * @param  plataxStnCnt (int)
     */
    public int setPlataxStnCnt(int plataxStnCnt) {
        try {
            if ( ((plataxStnCnt == INTNULL) ||
                  (plataxStnCnt == INTNULL2)) ||
                !((plataxStnCnt < PLATAX_STN_CNT_MN) ||
                  (plataxStnCnt > PLATAX_STN_CNT_MX)) ) {
                this.plataxStnCnt = plataxStnCnt;
                plataxStnCntError = ERROR_NORMAL;
            } else {
                throw plataxStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlataxStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plataxStnCntError;
    } // method setPlataxStnCnt

    /**
     * Set the 'plataxStnCnt' class variable
     * @param  plataxStnCnt (Integer)
     */
    public int setPlataxStnCnt(Integer plataxStnCnt) {
        try {
            setPlataxStnCnt(plataxStnCnt.intValue());
        } catch (Exception e) {
            setPlataxStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plataxStnCntError;
    } // method setPlataxStnCnt

    /**
     * Set the 'plataxStnCnt' class variable
     * @param  plataxStnCnt (Float)
     */
    public int setPlataxStnCnt(Float plataxStnCnt) {
        try {
            if (plataxStnCnt.floatValue() == FLOATNULL) {
                setPlataxStnCnt(INTNULL);
            } else {
                setPlataxStnCnt(plataxStnCnt.intValue());
            } // if (plataxStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlataxStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plataxStnCntError;
    } // method setPlataxStnCnt

    /**
     * Set the 'plataxStnCnt' class variable
     * @param  plataxStnCnt (String)
     */
    public int setPlataxStnCnt(String plataxStnCnt) {
        try {
            setPlataxStnCnt(new Integer(plataxStnCnt).intValue());
        } catch (Exception e) {
            setPlataxStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plataxStnCntError;
    } // method setPlataxStnCnt

    /**
     * Called when an exception has occured
     * @param  plataxStnCnt (String)
     */
    private void setPlataxStnCntError (int plataxStnCnt, Exception e, int error) {
        this.plataxStnCnt = plataxStnCnt;
        plataxStnCntErrorMessage = e.toString();
        plataxStnCntError = error;
    } // method setPlataxStnCntError


    /**
     * Set the 'plachlStnCnt' class variable
     * @param  plachlStnCnt (int)
     */
    public int setPlachlStnCnt(int plachlStnCnt) {
        try {
            if ( ((plachlStnCnt == INTNULL) ||
                  (plachlStnCnt == INTNULL2)) ||
                !((plachlStnCnt < PLACHL_STN_CNT_MN) ||
                  (plachlStnCnt > PLACHL_STN_CNT_MX)) ) {
                this.plachlStnCnt = plachlStnCnt;
                plachlStnCntError = ERROR_NORMAL;
            } else {
                throw plachlStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlachlStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plachlStnCntError;
    } // method setPlachlStnCnt

    /**
     * Set the 'plachlStnCnt' class variable
     * @param  plachlStnCnt (Integer)
     */
    public int setPlachlStnCnt(Integer plachlStnCnt) {
        try {
            setPlachlStnCnt(plachlStnCnt.intValue());
        } catch (Exception e) {
            setPlachlStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plachlStnCntError;
    } // method setPlachlStnCnt

    /**
     * Set the 'plachlStnCnt' class variable
     * @param  plachlStnCnt (Float)
     */
    public int setPlachlStnCnt(Float plachlStnCnt) {
        try {
            if (plachlStnCnt.floatValue() == FLOATNULL) {
                setPlachlStnCnt(INTNULL);
            } else {
                setPlachlStnCnt(plachlStnCnt.intValue());
            } // if (plachlStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlachlStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plachlStnCntError;
    } // method setPlachlStnCnt

    /**
     * Set the 'plachlStnCnt' class variable
     * @param  plachlStnCnt (String)
     */
    public int setPlachlStnCnt(String plachlStnCnt) {
        try {
            setPlachlStnCnt(new Integer(plachlStnCnt).intValue());
        } catch (Exception e) {
            setPlachlStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plachlStnCntError;
    } // method setPlachlStnCnt

    /**
     * Called when an exception has occured
     * @param  plachlStnCnt (String)
     */
    private void setPlachlStnCntError (int plachlStnCnt, Exception e, int error) {
        this.plachlStnCnt = plachlStnCnt;
        plachlStnCntErrorMessage = e.toString();
        plachlStnCntError = error;
    } // method setPlachlStnCntError


    /**
     * Set the 'tisphyStnCnt' class variable
     * @param  tisphyStnCnt (int)
     */
    public int setTisphyStnCnt(int tisphyStnCnt) {
        try {
            if ( ((tisphyStnCnt == INTNULL) ||
                  (tisphyStnCnt == INTNULL2)) ||
                !((tisphyStnCnt < TISPHY_STN_CNT_MN) ||
                  (tisphyStnCnt > TISPHY_STN_CNT_MX)) ) {
                this.tisphyStnCnt = tisphyStnCnt;
                tisphyStnCntError = ERROR_NORMAL;
            } else {
                throw tisphyStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTisphyStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tisphyStnCntError;
    } // method setTisphyStnCnt

    /**
     * Set the 'tisphyStnCnt' class variable
     * @param  tisphyStnCnt (Integer)
     */
    public int setTisphyStnCnt(Integer tisphyStnCnt) {
        try {
            setTisphyStnCnt(tisphyStnCnt.intValue());
        } catch (Exception e) {
            setTisphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyStnCntError;
    } // method setTisphyStnCnt

    /**
     * Set the 'tisphyStnCnt' class variable
     * @param  tisphyStnCnt (Float)
     */
    public int setTisphyStnCnt(Float tisphyStnCnt) {
        try {
            if (tisphyStnCnt.floatValue() == FLOATNULL) {
                setTisphyStnCnt(INTNULL);
            } else {
                setTisphyStnCnt(tisphyStnCnt.intValue());
            } // if (tisphyStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTisphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyStnCntError;
    } // method setTisphyStnCnt

    /**
     * Set the 'tisphyStnCnt' class variable
     * @param  tisphyStnCnt (String)
     */
    public int setTisphyStnCnt(String tisphyStnCnt) {
        try {
            setTisphyStnCnt(new Integer(tisphyStnCnt).intValue());
        } catch (Exception e) {
            setTisphyStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyStnCntError;
    } // method setTisphyStnCnt

    /**
     * Called when an exception has occured
     * @param  tisphyStnCnt (String)
     */
    private void setTisphyStnCntError (int tisphyStnCnt, Exception e, int error) {
        this.tisphyStnCnt = tisphyStnCnt;
        tisphyStnCntErrorMessage = e.toString();
        tisphyStnCntError = error;
    } // method setTisphyStnCntError


    /**
     * Set the 'tispesStnCnt' class variable
     * @param  tispesStnCnt (int)
     */
    public int setTispesStnCnt(int tispesStnCnt) {
        try {
            if ( ((tispesStnCnt == INTNULL) ||
                  (tispesStnCnt == INTNULL2)) ||
                !((tispesStnCnt < TISPES_STN_CNT_MN) ||
                  (tispesStnCnt > TISPES_STN_CNT_MX)) ) {
                this.tispesStnCnt = tispesStnCnt;
                tispesStnCntError = ERROR_NORMAL;
            } else {
                throw tispesStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTispesStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tispesStnCntError;
    } // method setTispesStnCnt

    /**
     * Set the 'tispesStnCnt' class variable
     * @param  tispesStnCnt (Integer)
     */
    public int setTispesStnCnt(Integer tispesStnCnt) {
        try {
            setTispesStnCnt(tispesStnCnt.intValue());
        } catch (Exception e) {
            setTispesStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispesStnCntError;
    } // method setTispesStnCnt

    /**
     * Set the 'tispesStnCnt' class variable
     * @param  tispesStnCnt (Float)
     */
    public int setTispesStnCnt(Float tispesStnCnt) {
        try {
            if (tispesStnCnt.floatValue() == FLOATNULL) {
                setTispesStnCnt(INTNULL);
            } else {
                setTispesStnCnt(tispesStnCnt.intValue());
            } // if (tispesStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTispesStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispesStnCntError;
    } // method setTispesStnCnt

    /**
     * Set the 'tispesStnCnt' class variable
     * @param  tispesStnCnt (String)
     */
    public int setTispesStnCnt(String tispesStnCnt) {
        try {
            setTispesStnCnt(new Integer(tispesStnCnt).intValue());
        } catch (Exception e) {
            setTispesStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispesStnCntError;
    } // method setTispesStnCnt

    /**
     * Called when an exception has occured
     * @param  tispesStnCnt (String)
     */
    private void setTispesStnCntError (int tispesStnCnt, Exception e, int error) {
        this.tispesStnCnt = tispesStnCnt;
        tispesStnCntErrorMessage = e.toString();
        tispesStnCntError = error;
    } // method setTispesStnCntError


    /**
     * Set the 'tispolStnCnt' class variable
     * @param  tispolStnCnt (int)
     */
    public int setTispolStnCnt(int tispolStnCnt) {
        try {
            if ( ((tispolStnCnt == INTNULL) ||
                  (tispolStnCnt == INTNULL2)) ||
                !((tispolStnCnt < TISPOL_STN_CNT_MN) ||
                  (tispolStnCnt > TISPOL_STN_CNT_MX)) ) {
                this.tispolStnCnt = tispolStnCnt;
                tispolStnCntError = ERROR_NORMAL;
            } else {
                throw tispolStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTispolStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tispolStnCntError;
    } // method setTispolStnCnt

    /**
     * Set the 'tispolStnCnt' class variable
     * @param  tispolStnCnt (Integer)
     */
    public int setTispolStnCnt(Integer tispolStnCnt) {
        try {
            setTispolStnCnt(tispolStnCnt.intValue());
        } catch (Exception e) {
            setTispolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispolStnCntError;
    } // method setTispolStnCnt

    /**
     * Set the 'tispolStnCnt' class variable
     * @param  tispolStnCnt (Float)
     */
    public int setTispolStnCnt(Float tispolStnCnt) {
        try {
            if (tispolStnCnt.floatValue() == FLOATNULL) {
                setTispolStnCnt(INTNULL);
            } else {
                setTispolStnCnt(tispolStnCnt.intValue());
            } // if (tispolStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTispolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispolStnCntError;
    } // method setTispolStnCnt

    /**
     * Set the 'tispolStnCnt' class variable
     * @param  tispolStnCnt (String)
     */
    public int setTispolStnCnt(String tispolStnCnt) {
        try {
            setTispolStnCnt(new Integer(tispolStnCnt).intValue());
        } catch (Exception e) {
            setTispolStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tispolStnCntError;
    } // method setTispolStnCnt

    /**
     * Called when an exception has occured
     * @param  tispolStnCnt (String)
     */
    private void setTispolStnCntError (int tispolStnCnt, Exception e, int error) {
        this.tispolStnCnt = tispolStnCnt;
        tispolStnCntErrorMessage = e.toString();
        tispolStnCntError = error;
    } // method setTispolStnCntError


    /**
     * Set the 'tisanimalStnCnt' class variable
     * @param  tisanimalStnCnt (int)
     */
    public int setTisanimalStnCnt(int tisanimalStnCnt) {
        try {
            if ( ((tisanimalStnCnt == INTNULL) ||
                  (tisanimalStnCnt == INTNULL2)) ||
                !((tisanimalStnCnt < TISANIMAL_STN_CNT_MN) ||
                  (tisanimalStnCnt > TISANIMAL_STN_CNT_MX)) ) {
                this.tisanimalStnCnt = tisanimalStnCnt;
                tisanimalStnCntError = ERROR_NORMAL;
            } else {
                throw tisanimalStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTisanimalStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tisanimalStnCntError;
    } // method setTisanimalStnCnt

    /**
     * Set the 'tisanimalStnCnt' class variable
     * @param  tisanimalStnCnt (Integer)
     */
    public int setTisanimalStnCnt(Integer tisanimalStnCnt) {
        try {
            setTisanimalStnCnt(tisanimalStnCnt.intValue());
        } catch (Exception e) {
            setTisanimalStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisanimalStnCntError;
    } // method setTisanimalStnCnt

    /**
     * Set the 'tisanimalStnCnt' class variable
     * @param  tisanimalStnCnt (Float)
     */
    public int setTisanimalStnCnt(Float tisanimalStnCnt) {
        try {
            if (tisanimalStnCnt.floatValue() == FLOATNULL) {
                setTisanimalStnCnt(INTNULL);
            } else {
                setTisanimalStnCnt(tisanimalStnCnt.intValue());
            } // if (tisanimalStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTisanimalStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisanimalStnCntError;
    } // method setTisanimalStnCnt

    /**
     * Set the 'tisanimalStnCnt' class variable
     * @param  tisanimalStnCnt (String)
     */
    public int setTisanimalStnCnt(String tisanimalStnCnt) {
        try {
            setTisanimalStnCnt(new Integer(tisanimalStnCnt).intValue());
        } catch (Exception e) {
            setTisanimalStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisanimalStnCntError;
    } // method setTisanimalStnCnt

    /**
     * Called when an exception has occured
     * @param  tisanimalStnCnt (String)
     */
    private void setTisanimalStnCntError (int tisanimalStnCnt, Exception e, int error) {
        this.tisanimalStnCnt = tisanimalStnCnt;
        tisanimalStnCntErrorMessage = e.toString();
        tisanimalStnCntError = error;
    } // method setTisanimalStnCntError


    /**
     * Set the 'watcurrentsStnCnt' class variable
     * @param  watcurrentsStnCnt (int)
     */
    public int setWatcurrentsStnCnt(int watcurrentsStnCnt) {
        try {
            if ( ((watcurrentsStnCnt == INTNULL) ||
                  (watcurrentsStnCnt == INTNULL2)) ||
                !((watcurrentsStnCnt < WATCURRENTS_STN_CNT_MN) ||
                  (watcurrentsStnCnt > WATCURRENTS_STN_CNT_MX)) ) {
                this.watcurrentsStnCnt = watcurrentsStnCnt;
                watcurrentsStnCntError = ERROR_NORMAL;
            } else {
                throw watcurrentsStnCntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatcurrentsStnCntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watcurrentsStnCntError;
    } // method setWatcurrentsStnCnt

    /**
     * Set the 'watcurrentsStnCnt' class variable
     * @param  watcurrentsStnCnt (Integer)
     */
    public int setWatcurrentsStnCnt(Integer watcurrentsStnCnt) {
        try {
            setWatcurrentsStnCnt(watcurrentsStnCnt.intValue());
        } catch (Exception e) {
            setWatcurrentsStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watcurrentsStnCntError;
    } // method setWatcurrentsStnCnt

    /**
     * Set the 'watcurrentsStnCnt' class variable
     * @param  watcurrentsStnCnt (Float)
     */
    public int setWatcurrentsStnCnt(Float watcurrentsStnCnt) {
        try {
            if (watcurrentsStnCnt.floatValue() == FLOATNULL) {
                setWatcurrentsStnCnt(INTNULL);
            } else {
                setWatcurrentsStnCnt(watcurrentsStnCnt.intValue());
            } // if (watcurrentsStnCnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatcurrentsStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watcurrentsStnCntError;
    } // method setWatcurrentsStnCnt

    /**
     * Set the 'watcurrentsStnCnt' class variable
     * @param  watcurrentsStnCnt (String)
     */
    public int setWatcurrentsStnCnt(String watcurrentsStnCnt) {
        try {
            setWatcurrentsStnCnt(new Integer(watcurrentsStnCnt).intValue());
        } catch (Exception e) {
            setWatcurrentsStnCntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watcurrentsStnCntError;
    } // method setWatcurrentsStnCnt

    /**
     * Called when an exception has occured
     * @param  watcurrentsStnCnt (String)
     */
    private void setWatcurrentsStnCntError (int watcurrentsStnCnt, Exception e, int error) {
        this.watcurrentsStnCnt = watcurrentsStnCnt;
        watcurrentsStnCntErrorMessage = e.toString();
        watcurrentsStnCntError = error;
    } // method setWatcurrentsStnCntError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'surveyId' class variable
     * @return surveyId (String)
     */
    public String getSurveyId() {
        return surveyId;
    } // method getSurveyId

    /**
     * Return the 'surveyId' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurveyId methods
     * @return surveyId (String)
     */
    public String getSurveyId(String s) {
        return (surveyId != CHARNULL ? surveyId.replace('"','\'') : "");
    } // method getSurveyId


    /**
     * Return the 'stationCnt' class variable
     * @return stationCnt (int)
     */
    public int getStationCnt() {
        return stationCnt;
    } // method getStationCnt

    /**
     * Return the 'stationCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStationCnt methods
     * @return stationCnt (String)
     */
    public String getStationCnt(String s) {
        return ((stationCnt != INTNULL) ? new Integer(stationCnt).toString() : "");
    } // method getStationCnt


    /**
     * Return the 'watphyCnt' class variable
     * @return watphyCnt (int)
     */
    public int getWatphyCnt() {
        return watphyCnt;
    } // method getWatphyCnt

    /**
     * Return the 'watphyCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatphyCnt methods
     * @return watphyCnt (String)
     */
    public String getWatphyCnt(String s) {
        return ((watphyCnt != INTNULL) ? new Integer(watphyCnt).toString() : "");
    } // method getWatphyCnt


    /**
     * Return the 'watnutCnt' class variable
     * @return watnutCnt (int)
     */
    public int getWatnutCnt() {
        return watnutCnt;
    } // method getWatnutCnt

    /**
     * Return the 'watnutCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatnutCnt methods
     * @return watnutCnt (String)
     */
    public String getWatnutCnt(String s) {
        return ((watnutCnt != INTNULL) ? new Integer(watnutCnt).toString() : "");
    } // method getWatnutCnt


    /**
     * Return the 'watpol1Cnt' class variable
     * @return watpol1Cnt (int)
     */
    public int getWatpol1Cnt() {
        return watpol1Cnt;
    } // method getWatpol1Cnt

    /**
     * Return the 'watpol1Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatpol1Cnt methods
     * @return watpol1Cnt (String)
     */
    public String getWatpol1Cnt(String s) {
        return ((watpol1Cnt != INTNULL) ? new Integer(watpol1Cnt).toString() : "");
    } // method getWatpol1Cnt


    /**
     * Return the 'watpol2Cnt' class variable
     * @return watpol2Cnt (int)
     */
    public int getWatpol2Cnt() {
        return watpol2Cnt;
    } // method getWatpol2Cnt

    /**
     * Return the 'watpol2Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatpol2Cnt methods
     * @return watpol2Cnt (String)
     */
    public String getWatpol2Cnt(String s) {
        return ((watpol2Cnt != INTNULL) ? new Integer(watpol2Cnt).toString() : "");
    } // method getWatpol2Cnt


    /**
     * Return the 'watchem1Cnt' class variable
     * @return watchem1Cnt (int)
     */
    public int getWatchem1Cnt() {
        return watchem1Cnt;
    } // method getWatchem1Cnt

    /**
     * Return the 'watchem1Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatchem1Cnt methods
     * @return watchem1Cnt (String)
     */
    public String getWatchem1Cnt(String s) {
        return ((watchem1Cnt != INTNULL) ? new Integer(watchem1Cnt).toString() : "");
    } // method getWatchem1Cnt


    /**
     * Return the 'watchem2Cnt' class variable
     * @return watchem2Cnt (int)
     */
    public int getWatchem2Cnt() {
        return watchem2Cnt;
    } // method getWatchem2Cnt

    /**
     * Return the 'watchem2Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatchem2Cnt methods
     * @return watchem2Cnt (String)
     */
    public String getWatchem2Cnt(String s) {
        return ((watchem2Cnt != INTNULL) ? new Integer(watchem2Cnt).toString() : "");
    } // method getWatchem2Cnt


    /**
     * Return the 'watchlCnt' class variable
     * @return watchlCnt (int)
     */
    public int getWatchlCnt() {
        return watchlCnt;
    } // method getWatchlCnt

    /**
     * Return the 'watchlCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatchlCnt methods
     * @return watchlCnt (String)
     */
    public String getWatchlCnt(String s) {
        return ((watchlCnt != INTNULL) ? new Integer(watchlCnt).toString() : "");
    } // method getWatchlCnt


    /**
     * Return the 'sedphyCnt' class variable
     * @return sedphyCnt (int)
     */
    public int getSedphyCnt() {
        return sedphyCnt;
    } // method getSedphyCnt

    /**
     * Return the 'sedphyCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedphyCnt methods
     * @return sedphyCnt (String)
     */
    public String getSedphyCnt(String s) {
        return ((sedphyCnt != INTNULL) ? new Integer(sedphyCnt).toString() : "");
    } // method getSedphyCnt


    /**
     * Return the 'sedpesCnt' class variable
     * @return sedpesCnt (int)
     */
    public int getSedpesCnt() {
        return sedpesCnt;
    } // method getSedpesCnt

    /**
     * Return the 'sedpesCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedpesCnt methods
     * @return sedpesCnt (String)
     */
    public String getSedpesCnt(String s) {
        return ((sedpesCnt != INTNULL) ? new Integer(sedpesCnt).toString() : "");
    } // method getSedpesCnt


    /**
     * Return the 'sedpol1Cnt' class variable
     * @return sedpol1Cnt (int)
     */
    public int getSedpol1Cnt() {
        return sedpol1Cnt;
    } // method getSedpol1Cnt

    /**
     * Return the 'sedpol1Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedpol1Cnt methods
     * @return sedpol1Cnt (String)
     */
    public String getSedpol1Cnt(String s) {
        return ((sedpol1Cnt != INTNULL) ? new Integer(sedpol1Cnt).toString() : "");
    } // method getSedpol1Cnt


    /**
     * Return the 'sedpol2Cnt' class variable
     * @return sedpol2Cnt (int)
     */
    public int getSedpol2Cnt() {
        return sedpol2Cnt;
    } // method getSedpol2Cnt

    /**
     * Return the 'sedpol2Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedpol2Cnt methods
     * @return sedpol2Cnt (String)
     */
    public String getSedpol2Cnt(String s) {
        return ((sedpol2Cnt != INTNULL) ? new Integer(sedpol2Cnt).toString() : "");
    } // method getSedpol2Cnt


    /**
     * Return the 'sedchem1Cnt' class variable
     * @return sedchem1Cnt (int)
     */
    public int getSedchem1Cnt() {
        return sedchem1Cnt;
    } // method getSedchem1Cnt

    /**
     * Return the 'sedchem1Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedchem1Cnt methods
     * @return sedchem1Cnt (String)
     */
    public String getSedchem1Cnt(String s) {
        return ((sedchem1Cnt != INTNULL) ? new Integer(sedchem1Cnt).toString() : "");
    } // method getSedchem1Cnt


    /**
     * Return the 'sedchem2Cnt' class variable
     * @return sedchem2Cnt (int)
     */
    public int getSedchem2Cnt() {
        return sedchem2Cnt;
    } // method getSedchem2Cnt

    /**
     * Return the 'sedchem2Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedchem2Cnt methods
     * @return sedchem2Cnt (String)
     */
    public String getSedchem2Cnt(String s) {
        return ((sedchem2Cnt != INTNULL) ? new Integer(sedchem2Cnt).toString() : "");
    } // method getSedchem2Cnt


    /**
     * Return the 'sedtaxCnt' class variable
     * @return sedtaxCnt (int)
     */
    public int getSedtaxCnt() {
        return sedtaxCnt;
    } // method getSedtaxCnt

    /**
     * Return the 'sedtaxCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedtaxCnt methods
     * @return sedtaxCnt (String)
     */
    public String getSedtaxCnt(String s) {
        return ((sedtaxCnt != INTNULL) ? new Integer(sedtaxCnt).toString() : "");
    } // method getSedtaxCnt


    /**
     * Return the 'plaphyCnt' class variable
     * @return plaphyCnt (int)
     */
    public int getPlaphyCnt() {
        return plaphyCnt;
    } // method getPlaphyCnt

    /**
     * Return the 'plaphyCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlaphyCnt methods
     * @return plaphyCnt (String)
     */
    public String getPlaphyCnt(String s) {
        return ((plaphyCnt != INTNULL) ? new Integer(plaphyCnt).toString() : "");
    } // method getPlaphyCnt


    /**
     * Return the 'plapesCnt' class variable
     * @return plapesCnt (int)
     */
    public int getPlapesCnt() {
        return plapesCnt;
    } // method getPlapesCnt

    /**
     * Return the 'plapesCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlapesCnt methods
     * @return plapesCnt (String)
     */
    public String getPlapesCnt(String s) {
        return ((plapesCnt != INTNULL) ? new Integer(plapesCnt).toString() : "");
    } // method getPlapesCnt


    /**
     * Return the 'plapol1Cnt' class variable
     * @return plapol1Cnt (int)
     */
    public int getPlapol1Cnt() {
        return plapol1Cnt;
    } // method getPlapol1Cnt

    /**
     * Return the 'plapol1Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlapol1Cnt methods
     * @return plapol1Cnt (String)
     */
    public String getPlapol1Cnt(String s) {
        return ((plapol1Cnt != INTNULL) ? new Integer(plapol1Cnt).toString() : "");
    } // method getPlapol1Cnt


    /**
     * Return the 'plapol2Cnt' class variable
     * @return plapol2Cnt (int)
     */
    public int getPlapol2Cnt() {
        return plapol2Cnt;
    } // method getPlapol2Cnt

    /**
     * Return the 'plapol2Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlapol2Cnt methods
     * @return plapol2Cnt (String)
     */
    public String getPlapol2Cnt(String s) {
        return ((plapol2Cnt != INTNULL) ? new Integer(plapol2Cnt).toString() : "");
    } // method getPlapol2Cnt


    /**
     * Return the 'plataxCnt' class variable
     * @return plataxCnt (int)
     */
    public int getPlataxCnt() {
        return plataxCnt;
    } // method getPlataxCnt

    /**
     * Return the 'plataxCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlataxCnt methods
     * @return plataxCnt (String)
     */
    public String getPlataxCnt(String s) {
        return ((plataxCnt != INTNULL) ? new Integer(plataxCnt).toString() : "");
    } // method getPlataxCnt


    /**
     * Return the 'plachlCnt' class variable
     * @return plachlCnt (int)
     */
    public int getPlachlCnt() {
        return plachlCnt;
    } // method getPlachlCnt

    /**
     * Return the 'plachlCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlachlCnt methods
     * @return plachlCnt (String)
     */
    public String getPlachlCnt(String s) {
        return ((plachlCnt != INTNULL) ? new Integer(plachlCnt).toString() : "");
    } // method getPlachlCnt


    /**
     * Return the 'tisphyCnt' class variable
     * @return tisphyCnt (int)
     */
    public int getTisphyCnt() {
        return tisphyCnt;
    } // method getTisphyCnt

    /**
     * Return the 'tisphyCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTisphyCnt methods
     * @return tisphyCnt (String)
     */
    public String getTisphyCnt(String s) {
        return ((tisphyCnt != INTNULL) ? new Integer(tisphyCnt).toString() : "");
    } // method getTisphyCnt


    /**
     * Return the 'tispesCnt' class variable
     * @return tispesCnt (int)
     */
    public int getTispesCnt() {
        return tispesCnt;
    } // method getTispesCnt

    /**
     * Return the 'tispesCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTispesCnt methods
     * @return tispesCnt (String)
     */
    public String getTispesCnt(String s) {
        return ((tispesCnt != INTNULL) ? new Integer(tispesCnt).toString() : "");
    } // method getTispesCnt


    /**
     * Return the 'tispol1Cnt' class variable
     * @return tispol1Cnt (int)
     */
    public int getTispol1Cnt() {
        return tispol1Cnt;
    } // method getTispol1Cnt

    /**
     * Return the 'tispol1Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTispol1Cnt methods
     * @return tispol1Cnt (String)
     */
    public String getTispol1Cnt(String s) {
        return ((tispol1Cnt != INTNULL) ? new Integer(tispol1Cnt).toString() : "");
    } // method getTispol1Cnt


    /**
     * Return the 'tispol2Cnt' class variable
     * @return tispol2Cnt (int)
     */
    public int getTispol2Cnt() {
        return tispol2Cnt;
    } // method getTispol2Cnt

    /**
     * Return the 'tispol2Cnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTispol2Cnt methods
     * @return tispol2Cnt (String)
     */
    public String getTispol2Cnt(String s) {
        return ((tispol2Cnt != INTNULL) ? new Integer(tispol2Cnt).toString() : "");
    } // method getTispol2Cnt


    /**
     * Return the 'tisanimalCnt' class variable
     * @return tisanimalCnt (int)
     */
    public int getTisanimalCnt() {
        return tisanimalCnt;
    } // method getTisanimalCnt

    /**
     * Return the 'tisanimalCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTisanimalCnt methods
     * @return tisanimalCnt (String)
     */
    public String getTisanimalCnt(String s) {
        return ((tisanimalCnt != INTNULL) ? new Integer(tisanimalCnt).toString() : "");
    } // method getTisanimalCnt


    /**
     * Return the 'weatherCnt' class variable
     * @return weatherCnt (int)
     */
    public int getWeatherCnt() {
        return weatherCnt;
    } // method getWeatherCnt

    /**
     * Return the 'weatherCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWeatherCnt methods
     * @return weatherCnt (String)
     */
    public String getWeatherCnt(String s) {
        return ((weatherCnt != INTNULL) ? new Integer(weatherCnt).toString() : "");
    } // method getWeatherCnt


    /**
     * Return the 'watcurrentsCnt' class variable
     * @return watcurrentsCnt (int)
     */
    public int getWatcurrentsCnt() {
        return watcurrentsCnt;
    } // method getWatcurrentsCnt

    /**
     * Return the 'watcurrentsCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatcurrentsCnt methods
     * @return watcurrentsCnt (String)
     */
    public String getWatcurrentsCnt(String s) {
        return ((watcurrentsCnt != INTNULL) ? new Integer(watcurrentsCnt).toString() : "");
    } // method getWatcurrentsCnt


    /**
     * Return the 'watosdCnt' class variable
     * @return watosdCnt (int)
     */
    public int getWatosdCnt() {
        return watosdCnt;
    } // method getWatosdCnt

    /**
     * Return the 'watosdCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatosdCnt methods
     * @return watosdCnt (String)
     */
    public String getWatosdCnt(String s) {
        return ((watosdCnt != INTNULL) ? new Integer(watosdCnt).toString() : "");
    } // method getWatosdCnt


    /**
     * Return the 'watctdCnt' class variable
     * @return watctdCnt (int)
     */
    public int getWatctdCnt() {
        return watctdCnt;
    } // method getWatctdCnt

    /**
     * Return the 'watctdCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatctdCnt methods
     * @return watctdCnt (String)
     */
    public String getWatctdCnt(String s) {
        return ((watctdCnt != INTNULL) ? new Integer(watctdCnt).toString() : "");
    } // method getWatctdCnt


    /**
     * Return the 'watxbtCnt' class variable
     * @return watxbtCnt (int)
     */
    public int getWatxbtCnt() {
        return watxbtCnt;
    } // method getWatxbtCnt

    /**
     * Return the 'watxbtCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatxbtCnt methods
     * @return watxbtCnt (String)
     */
    public String getWatxbtCnt(String s) {
        return ((watxbtCnt != INTNULL) ? new Integer(watxbtCnt).toString() : "");
    } // method getWatxbtCnt


    /**
     * Return the 'watmbtCnt' class variable
     * @return watmbtCnt (int)
     */
    public int getWatmbtCnt() {
        return watmbtCnt;
    } // method getWatmbtCnt

    /**
     * Return the 'watmbtCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatmbtCnt methods
     * @return watmbtCnt (String)
     */
    public String getWatmbtCnt(String s) {
        return ((watmbtCnt != INTNULL) ? new Integer(watmbtCnt).toString() : "");
    } // method getWatmbtCnt


    /**
     * Return the 'watpflCnt' class variable
     * @return watpflCnt (int)
     */
    public int getWatpflCnt() {
        return watpflCnt;
    } // method getWatpflCnt

    /**
     * Return the 'watpflCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatpflCnt methods
     * @return watpflCnt (String)
     */
    public String getWatpflCnt(String s) {
        return ((watpflCnt != INTNULL) ? new Integer(watpflCnt).toString() : "");
    } // method getWatpflCnt


    /**
     * Return the 'watphyStnCnt' class variable
     * @return watphyStnCnt (int)
     */
    public int getWatphyStnCnt() {
        return watphyStnCnt;
    } // method getWatphyStnCnt

    /**
     * Return the 'watphyStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatphyStnCnt methods
     * @return watphyStnCnt (String)
     */
    public String getWatphyStnCnt(String s) {
        return ((watphyStnCnt != INTNULL) ? new Integer(watphyStnCnt).toString() : "");
    } // method getWatphyStnCnt


    /**
     * Return the 'watosdStnCnt' class variable
     * @return watosdStnCnt (int)
     */
    public int getWatosdStnCnt() {
        return watosdStnCnt;
    } // method getWatosdStnCnt

    /**
     * Return the 'watosdStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatosdStnCnt methods
     * @return watosdStnCnt (String)
     */
    public String getWatosdStnCnt(String s) {
        return ((watosdStnCnt != INTNULL) ? new Integer(watosdStnCnt).toString() : "");
    } // method getWatosdStnCnt


    /**
     * Return the 'watctdStnCnt' class variable
     * @return watctdStnCnt (int)
     */
    public int getWatctdStnCnt() {
        return watctdStnCnt;
    } // method getWatctdStnCnt

    /**
     * Return the 'watctdStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatctdStnCnt methods
     * @return watctdStnCnt (String)
     */
    public String getWatctdStnCnt(String s) {
        return ((watctdStnCnt != INTNULL) ? new Integer(watctdStnCnt).toString() : "");
    } // method getWatctdStnCnt


    /**
     * Return the 'watxbtStnCnt' class variable
     * @return watxbtStnCnt (int)
     */
    public int getWatxbtStnCnt() {
        return watxbtStnCnt;
    } // method getWatxbtStnCnt

    /**
     * Return the 'watxbtStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatxbtStnCnt methods
     * @return watxbtStnCnt (String)
     */
    public String getWatxbtStnCnt(String s) {
        return ((watxbtStnCnt != INTNULL) ? new Integer(watxbtStnCnt).toString() : "");
    } // method getWatxbtStnCnt


    /**
     * Return the 'watmbtStnCnt' class variable
     * @return watmbtStnCnt (int)
     */
    public int getWatmbtStnCnt() {
        return watmbtStnCnt;
    } // method getWatmbtStnCnt

    /**
     * Return the 'watmbtStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatmbtStnCnt methods
     * @return watmbtStnCnt (String)
     */
    public String getWatmbtStnCnt(String s) {
        return ((watmbtStnCnt != INTNULL) ? new Integer(watmbtStnCnt).toString() : "");
    } // method getWatmbtStnCnt


    /**
     * Return the 'watpflStnCnt' class variable
     * @return watpflStnCnt (int)
     */
    public int getWatpflStnCnt() {
        return watpflStnCnt;
    } // method getWatpflStnCnt

    /**
     * Return the 'watpflStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatpflStnCnt methods
     * @return watpflStnCnt (String)
     */
    public String getWatpflStnCnt(String s) {
        return ((watpflStnCnt != INTNULL) ? new Integer(watpflStnCnt).toString() : "");
    } // method getWatpflStnCnt


    /**
     * Return the 'watnutStnCnt' class variable
     * @return watnutStnCnt (int)
     */
    public int getWatnutStnCnt() {
        return watnutStnCnt;
    } // method getWatnutStnCnt

    /**
     * Return the 'watnutStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatnutStnCnt methods
     * @return watnutStnCnt (String)
     */
    public String getWatnutStnCnt(String s) {
        return ((watnutStnCnt != INTNULL) ? new Integer(watnutStnCnt).toString() : "");
    } // method getWatnutStnCnt


    /**
     * Return the 'watpolStnCnt' class variable
     * @return watpolStnCnt (int)
     */
    public int getWatpolStnCnt() {
        return watpolStnCnt;
    } // method getWatpolStnCnt

    /**
     * Return the 'watpolStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatpolStnCnt methods
     * @return watpolStnCnt (String)
     */
    public String getWatpolStnCnt(String s) {
        return ((watpolStnCnt != INTNULL) ? new Integer(watpolStnCnt).toString() : "");
    } // method getWatpolStnCnt


    /**
     * Return the 'watchemStnCnt' class variable
     * @return watchemStnCnt (int)
     */
    public int getWatchemStnCnt() {
        return watchemStnCnt;
    } // method getWatchemStnCnt

    /**
     * Return the 'watchemStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatchemStnCnt methods
     * @return watchemStnCnt (String)
     */
    public String getWatchemStnCnt(String s) {
        return ((watchemStnCnt != INTNULL) ? new Integer(watchemStnCnt).toString() : "");
    } // method getWatchemStnCnt


    /**
     * Return the 'watchlStnCnt' class variable
     * @return watchlStnCnt (int)
     */
    public int getWatchlStnCnt() {
        return watchlStnCnt;
    } // method getWatchlStnCnt

    /**
     * Return the 'watchlStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatchlStnCnt methods
     * @return watchlStnCnt (String)
     */
    public String getWatchlStnCnt(String s) {
        return ((watchlStnCnt != INTNULL) ? new Integer(watchlStnCnt).toString() : "");
    } // method getWatchlStnCnt


    /**
     * Return the 'sedphyStnCnt' class variable
     * @return sedphyStnCnt (int)
     */
    public int getSedphyStnCnt() {
        return sedphyStnCnt;
    } // method getSedphyStnCnt

    /**
     * Return the 'sedphyStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedphyStnCnt methods
     * @return sedphyStnCnt (String)
     */
    public String getSedphyStnCnt(String s) {
        return ((sedphyStnCnt != INTNULL) ? new Integer(sedphyStnCnt).toString() : "");
    } // method getSedphyStnCnt


    /**
     * Return the 'sedpesStnCnt' class variable
     * @return sedpesStnCnt (int)
     */
    public int getSedpesStnCnt() {
        return sedpesStnCnt;
    } // method getSedpesStnCnt

    /**
     * Return the 'sedpesStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedpesStnCnt methods
     * @return sedpesStnCnt (String)
     */
    public String getSedpesStnCnt(String s) {
        return ((sedpesStnCnt != INTNULL) ? new Integer(sedpesStnCnt).toString() : "");
    } // method getSedpesStnCnt


    /**
     * Return the 'sedpolStnCnt' class variable
     * @return sedpolStnCnt (int)
     */
    public int getSedpolStnCnt() {
        return sedpolStnCnt;
    } // method getSedpolStnCnt

    /**
     * Return the 'sedpolStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedpolStnCnt methods
     * @return sedpolStnCnt (String)
     */
    public String getSedpolStnCnt(String s) {
        return ((sedpolStnCnt != INTNULL) ? new Integer(sedpolStnCnt).toString() : "");
    } // method getSedpolStnCnt


    /**
     * Return the 'sedchemStnCnt' class variable
     * @return sedchemStnCnt (int)
     */
    public int getSedchemStnCnt() {
        return sedchemStnCnt;
    } // method getSedchemStnCnt

    /**
     * Return the 'sedchemStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedchemStnCnt methods
     * @return sedchemStnCnt (String)
     */
    public String getSedchemStnCnt(String s) {
        return ((sedchemStnCnt != INTNULL) ? new Integer(sedchemStnCnt).toString() : "");
    } // method getSedchemStnCnt


    /**
     * Return the 'sedtaxStnCnt' class variable
     * @return sedtaxStnCnt (int)
     */
    public int getSedtaxStnCnt() {
        return sedtaxStnCnt;
    } // method getSedtaxStnCnt

    /**
     * Return the 'sedtaxStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedtaxStnCnt methods
     * @return sedtaxStnCnt (String)
     */
    public String getSedtaxStnCnt(String s) {
        return ((sedtaxStnCnt != INTNULL) ? new Integer(sedtaxStnCnt).toString() : "");
    } // method getSedtaxStnCnt


    /**
     * Return the 'plaphyStnCnt' class variable
     * @return plaphyStnCnt (int)
     */
    public int getPlaphyStnCnt() {
        return plaphyStnCnt;
    } // method getPlaphyStnCnt

    /**
     * Return the 'plaphyStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlaphyStnCnt methods
     * @return plaphyStnCnt (String)
     */
    public String getPlaphyStnCnt(String s) {
        return ((plaphyStnCnt != INTNULL) ? new Integer(plaphyStnCnt).toString() : "");
    } // method getPlaphyStnCnt


    /**
     * Return the 'plapesStnCnt' class variable
     * @return plapesStnCnt (int)
     */
    public int getPlapesStnCnt() {
        return plapesStnCnt;
    } // method getPlapesStnCnt

    /**
     * Return the 'plapesStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlapesStnCnt methods
     * @return plapesStnCnt (String)
     */
    public String getPlapesStnCnt(String s) {
        return ((plapesStnCnt != INTNULL) ? new Integer(plapesStnCnt).toString() : "");
    } // method getPlapesStnCnt


    /**
     * Return the 'plapolStnCnt' class variable
     * @return plapolStnCnt (int)
     */
    public int getPlapolStnCnt() {
        return plapolStnCnt;
    } // method getPlapolStnCnt

    /**
     * Return the 'plapolStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlapolStnCnt methods
     * @return plapolStnCnt (String)
     */
    public String getPlapolStnCnt(String s) {
        return ((plapolStnCnt != INTNULL) ? new Integer(plapolStnCnt).toString() : "");
    } // method getPlapolStnCnt


    /**
     * Return the 'plataxStnCnt' class variable
     * @return plataxStnCnt (int)
     */
    public int getPlataxStnCnt() {
        return plataxStnCnt;
    } // method getPlataxStnCnt

    /**
     * Return the 'plataxStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlataxStnCnt methods
     * @return plataxStnCnt (String)
     */
    public String getPlataxStnCnt(String s) {
        return ((plataxStnCnt != INTNULL) ? new Integer(plataxStnCnt).toString() : "");
    } // method getPlataxStnCnt


    /**
     * Return the 'plachlStnCnt' class variable
     * @return plachlStnCnt (int)
     */
    public int getPlachlStnCnt() {
        return plachlStnCnt;
    } // method getPlachlStnCnt

    /**
     * Return the 'plachlStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlachlStnCnt methods
     * @return plachlStnCnt (String)
     */
    public String getPlachlStnCnt(String s) {
        return ((plachlStnCnt != INTNULL) ? new Integer(plachlStnCnt).toString() : "");
    } // method getPlachlStnCnt


    /**
     * Return the 'tisphyStnCnt' class variable
     * @return tisphyStnCnt (int)
     */
    public int getTisphyStnCnt() {
        return tisphyStnCnt;
    } // method getTisphyStnCnt

    /**
     * Return the 'tisphyStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTisphyStnCnt methods
     * @return tisphyStnCnt (String)
     */
    public String getTisphyStnCnt(String s) {
        return ((tisphyStnCnt != INTNULL) ? new Integer(tisphyStnCnt).toString() : "");
    } // method getTisphyStnCnt


    /**
     * Return the 'tispesStnCnt' class variable
     * @return tispesStnCnt (int)
     */
    public int getTispesStnCnt() {
        return tispesStnCnt;
    } // method getTispesStnCnt

    /**
     * Return the 'tispesStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTispesStnCnt methods
     * @return tispesStnCnt (String)
     */
    public String getTispesStnCnt(String s) {
        return ((tispesStnCnt != INTNULL) ? new Integer(tispesStnCnt).toString() : "");
    } // method getTispesStnCnt


    /**
     * Return the 'tispolStnCnt' class variable
     * @return tispolStnCnt (int)
     */
    public int getTispolStnCnt() {
        return tispolStnCnt;
    } // method getTispolStnCnt

    /**
     * Return the 'tispolStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTispolStnCnt methods
     * @return tispolStnCnt (String)
     */
    public String getTispolStnCnt(String s) {
        return ((tispolStnCnt != INTNULL) ? new Integer(tispolStnCnt).toString() : "");
    } // method getTispolStnCnt


    /**
     * Return the 'tisanimalStnCnt' class variable
     * @return tisanimalStnCnt (int)
     */
    public int getTisanimalStnCnt() {
        return tisanimalStnCnt;
    } // method getTisanimalStnCnt

    /**
     * Return the 'tisanimalStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTisanimalStnCnt methods
     * @return tisanimalStnCnt (String)
     */
    public String getTisanimalStnCnt(String s) {
        return ((tisanimalStnCnt != INTNULL) ? new Integer(tisanimalStnCnt).toString() : "");
    } // method getTisanimalStnCnt


    /**
     * Return the 'watcurrentsStnCnt' class variable
     * @return watcurrentsStnCnt (int)
     */
    public int getWatcurrentsStnCnt() {
        return watcurrentsStnCnt;
    } // method getWatcurrentsStnCnt

    /**
     * Return the 'watcurrentsStnCnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatcurrentsStnCnt methods
     * @return watcurrentsStnCnt (String)
     */
    public String getWatcurrentsStnCnt(String s) {
        return ((watcurrentsStnCnt != INTNULL) ? new Integer(watcurrentsStnCnt).toString() : "");
    } // method getWatcurrentsStnCnt


    /**
     * Gets the number of records in the table
     * @return number of records (int)
     */
    public int getRecCnt () {
        return (getRecCnt("1=1"));
    } // method getRecCnt


    /**
     * Gets the number of records in the table
     * @param  where  The where clause (String)
     * @return number of records (int)
     */
    public int getRecCnt (String where) {
        Vector result = db.select ("count(*)",TABLE, where);
        Vector row = (Vector) result.elementAt(0);
        return (new Integer((String) row.elementAt(0)).intValue());
    } // method getRecCnt


    //=========================//
    // The isNullRecord method //
    //=========================//

    /**
     * Checks whether all the instance variables are NULL
     * @return true/false (boolean)
     */
    public boolean isNullRecord () {
        if ((surveyId == CHARNULL) &&
            (stationCnt == INTNULL) &&
            (watphyCnt == INTNULL) &&
            (watnutCnt == INTNULL) &&
            (watpol1Cnt == INTNULL) &&
            (watpol2Cnt == INTNULL) &&
            (watchem1Cnt == INTNULL) &&
            (watchem2Cnt == INTNULL) &&
            (watchlCnt == INTNULL) &&
            (sedphyCnt == INTNULL) &&
            (sedpesCnt == INTNULL) &&
            (sedpol1Cnt == INTNULL) &&
            (sedpol2Cnt == INTNULL) &&
            (sedchem1Cnt == INTNULL) &&
            (sedchem2Cnt == INTNULL) &&
            (sedtaxCnt == INTNULL) &&
            (plaphyCnt == INTNULL) &&
            (plapesCnt == INTNULL) &&
            (plapol1Cnt == INTNULL) &&
            (plapol2Cnt == INTNULL) &&
            (plataxCnt == INTNULL) &&
            (plachlCnt == INTNULL) &&
            (tisphyCnt == INTNULL) &&
            (tispesCnt == INTNULL) &&
            (tispol1Cnt == INTNULL) &&
            (tispol2Cnt == INTNULL) &&
            (tisanimalCnt == INTNULL) &&
            (weatherCnt == INTNULL) &&
            (watcurrentsCnt == INTNULL) &&
            (watosdCnt == INTNULL) &&
            (watctdCnt == INTNULL) &&
            (watxbtCnt == INTNULL) &&
            (watmbtCnt == INTNULL) &&
            (watpflCnt == INTNULL) &&
            (watphyStnCnt == INTNULL) &&
            (watosdStnCnt == INTNULL) &&
            (watctdStnCnt == INTNULL) &&
            (watxbtStnCnt == INTNULL) &&
            (watmbtStnCnt == INTNULL) &&
            (watpflStnCnt == INTNULL) &&
            (watnutStnCnt == INTNULL) &&
            (watpolStnCnt == INTNULL) &&
            (watchemStnCnt == INTNULL) &&
            (watchlStnCnt == INTNULL) &&
            (sedphyStnCnt == INTNULL) &&
            (sedpesStnCnt == INTNULL) &&
            (sedpolStnCnt == INTNULL) &&
            (sedchemStnCnt == INTNULL) &&
            (sedtaxStnCnt == INTNULL) &&
            (plaphyStnCnt == INTNULL) &&
            (plapesStnCnt == INTNULL) &&
            (plapolStnCnt == INTNULL) &&
            (plataxStnCnt == INTNULL) &&
            (plachlStnCnt == INTNULL) &&
            (tisphyStnCnt == INTNULL) &&
            (tispesStnCnt == INTNULL) &&
            (tispolStnCnt == INTNULL) &&
            (tisanimalStnCnt == INTNULL) &&
            (watcurrentsStnCnt == INTNULL)) {
            return true;
        } else {
            return false;
        } // if ...
    } // method isNullRecord


    //===================//
    // The error methods //
    //===================//

    /**
     * Checks whether all the instance variables were valid
     * @return true/false (boolean)
     */
    public boolean isValidRecord () {
        int sumError = surveyIdError +
            stationCntError +
            watphyCntError +
            watnutCntError +
            watpol1CntError +
            watpol2CntError +
            watchem1CntError +
            watchem2CntError +
            watchlCntError +
            sedphyCntError +
            sedpesCntError +
            sedpol1CntError +
            sedpol2CntError +
            sedchem1CntError +
            sedchem2CntError +
            sedtaxCntError +
            plaphyCntError +
            plapesCntError +
            plapol1CntError +
            plapol2CntError +
            plataxCntError +
            plachlCntError +
            tisphyCntError +
            tispesCntError +
            tispol1CntError +
            tispol2CntError +
            tisanimalCntError +
            weatherCntError +
            watcurrentsCntError +
            watosdCntError +
            watctdCntError +
            watxbtCntError +
            watmbtCntError +
            watpflCntError +
            watphyStnCntError +
            watosdStnCntError +
            watctdStnCntError +
            watxbtStnCntError +
            watmbtStnCntError +
            watpflStnCntError +
            watnutStnCntError +
            watpolStnCntError +
            watchemStnCntError +
            watchlStnCntError +
            sedphyStnCntError +
            sedpesStnCntError +
            sedpolStnCntError +
            sedchemStnCntError +
            sedtaxStnCntError +
            plaphyStnCntError +
            plapesStnCntError +
            plapolStnCntError +
            plataxStnCntError +
            plachlStnCntError +
            tisphyStnCntError +
            tispesStnCntError +
            tispolStnCntError +
            tisanimalStnCntError +
            watcurrentsStnCntError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the surveyId instance variable
     * @return errorcode (int)
     */
    public int getSurveyIdError() {
        return surveyIdError;
    } // method getSurveyIdError

    /**
     * Gets the errorMessage for the surveyId instance variable
     * @return errorMessage (String)
     */
    public String getSurveyIdErrorMessage() {
        return surveyIdErrorMessage;
    } // method getSurveyIdErrorMessage


    /**
     * Gets the errorcode for the stationCnt instance variable
     * @return errorcode (int)
     */
    public int getStationCntError() {
        return stationCntError;
    } // method getStationCntError

    /**
     * Gets the errorMessage for the stationCnt instance variable
     * @return errorMessage (String)
     */
    public String getStationCntErrorMessage() {
        return stationCntErrorMessage;
    } // method getStationCntErrorMessage


    /**
     * Gets the errorcode for the watphyCnt instance variable
     * @return errorcode (int)
     */
    public int getWatphyCntError() {
        return watphyCntError;
    } // method getWatphyCntError

    /**
     * Gets the errorMessage for the watphyCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatphyCntErrorMessage() {
        return watphyCntErrorMessage;
    } // method getWatphyCntErrorMessage


    /**
     * Gets the errorcode for the watnutCnt instance variable
     * @return errorcode (int)
     */
    public int getWatnutCntError() {
        return watnutCntError;
    } // method getWatnutCntError

    /**
     * Gets the errorMessage for the watnutCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatnutCntErrorMessage() {
        return watnutCntErrorMessage;
    } // method getWatnutCntErrorMessage


    /**
     * Gets the errorcode for the watpol1Cnt instance variable
     * @return errorcode (int)
     */
    public int getWatpol1CntError() {
        return watpol1CntError;
    } // method getWatpol1CntError

    /**
     * Gets the errorMessage for the watpol1Cnt instance variable
     * @return errorMessage (String)
     */
    public String getWatpol1CntErrorMessage() {
        return watpol1CntErrorMessage;
    } // method getWatpol1CntErrorMessage


    /**
     * Gets the errorcode for the watpol2Cnt instance variable
     * @return errorcode (int)
     */
    public int getWatpol2CntError() {
        return watpol2CntError;
    } // method getWatpol2CntError

    /**
     * Gets the errorMessage for the watpol2Cnt instance variable
     * @return errorMessage (String)
     */
    public String getWatpol2CntErrorMessage() {
        return watpol2CntErrorMessage;
    } // method getWatpol2CntErrorMessage


    /**
     * Gets the errorcode for the watchem1Cnt instance variable
     * @return errorcode (int)
     */
    public int getWatchem1CntError() {
        return watchem1CntError;
    } // method getWatchem1CntError

    /**
     * Gets the errorMessage for the watchem1Cnt instance variable
     * @return errorMessage (String)
     */
    public String getWatchem1CntErrorMessage() {
        return watchem1CntErrorMessage;
    } // method getWatchem1CntErrorMessage


    /**
     * Gets the errorcode for the watchem2Cnt instance variable
     * @return errorcode (int)
     */
    public int getWatchem2CntError() {
        return watchem2CntError;
    } // method getWatchem2CntError

    /**
     * Gets the errorMessage for the watchem2Cnt instance variable
     * @return errorMessage (String)
     */
    public String getWatchem2CntErrorMessage() {
        return watchem2CntErrorMessage;
    } // method getWatchem2CntErrorMessage


    /**
     * Gets the errorcode for the watchlCnt instance variable
     * @return errorcode (int)
     */
    public int getWatchlCntError() {
        return watchlCntError;
    } // method getWatchlCntError

    /**
     * Gets the errorMessage for the watchlCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatchlCntErrorMessage() {
        return watchlCntErrorMessage;
    } // method getWatchlCntErrorMessage


    /**
     * Gets the errorcode for the sedphyCnt instance variable
     * @return errorcode (int)
     */
    public int getSedphyCntError() {
        return sedphyCntError;
    } // method getSedphyCntError

    /**
     * Gets the errorMessage for the sedphyCnt instance variable
     * @return errorMessage (String)
     */
    public String getSedphyCntErrorMessage() {
        return sedphyCntErrorMessage;
    } // method getSedphyCntErrorMessage


    /**
     * Gets the errorcode for the sedpesCnt instance variable
     * @return errorcode (int)
     */
    public int getSedpesCntError() {
        return sedpesCntError;
    } // method getSedpesCntError

    /**
     * Gets the errorMessage for the sedpesCnt instance variable
     * @return errorMessage (String)
     */
    public String getSedpesCntErrorMessage() {
        return sedpesCntErrorMessage;
    } // method getSedpesCntErrorMessage


    /**
     * Gets the errorcode for the sedpol1Cnt instance variable
     * @return errorcode (int)
     */
    public int getSedpol1CntError() {
        return sedpol1CntError;
    } // method getSedpol1CntError

    /**
     * Gets the errorMessage for the sedpol1Cnt instance variable
     * @return errorMessage (String)
     */
    public String getSedpol1CntErrorMessage() {
        return sedpol1CntErrorMessage;
    } // method getSedpol1CntErrorMessage


    /**
     * Gets the errorcode for the sedpol2Cnt instance variable
     * @return errorcode (int)
     */
    public int getSedpol2CntError() {
        return sedpol2CntError;
    } // method getSedpol2CntError

    /**
     * Gets the errorMessage for the sedpol2Cnt instance variable
     * @return errorMessage (String)
     */
    public String getSedpol2CntErrorMessage() {
        return sedpol2CntErrorMessage;
    } // method getSedpol2CntErrorMessage


    /**
     * Gets the errorcode for the sedchem1Cnt instance variable
     * @return errorcode (int)
     */
    public int getSedchem1CntError() {
        return sedchem1CntError;
    } // method getSedchem1CntError

    /**
     * Gets the errorMessage for the sedchem1Cnt instance variable
     * @return errorMessage (String)
     */
    public String getSedchem1CntErrorMessage() {
        return sedchem1CntErrorMessage;
    } // method getSedchem1CntErrorMessage


    /**
     * Gets the errorcode for the sedchem2Cnt instance variable
     * @return errorcode (int)
     */
    public int getSedchem2CntError() {
        return sedchem2CntError;
    } // method getSedchem2CntError

    /**
     * Gets the errorMessage for the sedchem2Cnt instance variable
     * @return errorMessage (String)
     */
    public String getSedchem2CntErrorMessage() {
        return sedchem2CntErrorMessage;
    } // method getSedchem2CntErrorMessage


    /**
     * Gets the errorcode for the sedtaxCnt instance variable
     * @return errorcode (int)
     */
    public int getSedtaxCntError() {
        return sedtaxCntError;
    } // method getSedtaxCntError

    /**
     * Gets the errorMessage for the sedtaxCnt instance variable
     * @return errorMessage (String)
     */
    public String getSedtaxCntErrorMessage() {
        return sedtaxCntErrorMessage;
    } // method getSedtaxCntErrorMessage


    /**
     * Gets the errorcode for the plaphyCnt instance variable
     * @return errorcode (int)
     */
    public int getPlaphyCntError() {
        return plaphyCntError;
    } // method getPlaphyCntError

    /**
     * Gets the errorMessage for the plaphyCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlaphyCntErrorMessage() {
        return plaphyCntErrorMessage;
    } // method getPlaphyCntErrorMessage


    /**
     * Gets the errorcode for the plapesCnt instance variable
     * @return errorcode (int)
     */
    public int getPlapesCntError() {
        return plapesCntError;
    } // method getPlapesCntError

    /**
     * Gets the errorMessage for the plapesCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlapesCntErrorMessage() {
        return plapesCntErrorMessage;
    } // method getPlapesCntErrorMessage


    /**
     * Gets the errorcode for the plapol1Cnt instance variable
     * @return errorcode (int)
     */
    public int getPlapol1CntError() {
        return plapol1CntError;
    } // method getPlapol1CntError

    /**
     * Gets the errorMessage for the plapol1Cnt instance variable
     * @return errorMessage (String)
     */
    public String getPlapol1CntErrorMessage() {
        return plapol1CntErrorMessage;
    } // method getPlapol1CntErrorMessage


    /**
     * Gets the errorcode for the plapol2Cnt instance variable
     * @return errorcode (int)
     */
    public int getPlapol2CntError() {
        return plapol2CntError;
    } // method getPlapol2CntError

    /**
     * Gets the errorMessage for the plapol2Cnt instance variable
     * @return errorMessage (String)
     */
    public String getPlapol2CntErrorMessage() {
        return plapol2CntErrorMessage;
    } // method getPlapol2CntErrorMessage


    /**
     * Gets the errorcode for the plataxCnt instance variable
     * @return errorcode (int)
     */
    public int getPlataxCntError() {
        return plataxCntError;
    } // method getPlataxCntError

    /**
     * Gets the errorMessage for the plataxCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlataxCntErrorMessage() {
        return plataxCntErrorMessage;
    } // method getPlataxCntErrorMessage


    /**
     * Gets the errorcode for the plachlCnt instance variable
     * @return errorcode (int)
     */
    public int getPlachlCntError() {
        return plachlCntError;
    } // method getPlachlCntError

    /**
     * Gets the errorMessage for the plachlCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlachlCntErrorMessage() {
        return plachlCntErrorMessage;
    } // method getPlachlCntErrorMessage


    /**
     * Gets the errorcode for the tisphyCnt instance variable
     * @return errorcode (int)
     */
    public int getTisphyCntError() {
        return tisphyCntError;
    } // method getTisphyCntError

    /**
     * Gets the errorMessage for the tisphyCnt instance variable
     * @return errorMessage (String)
     */
    public String getTisphyCntErrorMessage() {
        return tisphyCntErrorMessage;
    } // method getTisphyCntErrorMessage


    /**
     * Gets the errorcode for the tispesCnt instance variable
     * @return errorcode (int)
     */
    public int getTispesCntError() {
        return tispesCntError;
    } // method getTispesCntError

    /**
     * Gets the errorMessage for the tispesCnt instance variable
     * @return errorMessage (String)
     */
    public String getTispesCntErrorMessage() {
        return tispesCntErrorMessage;
    } // method getTispesCntErrorMessage


    /**
     * Gets the errorcode for the tispol1Cnt instance variable
     * @return errorcode (int)
     */
    public int getTispol1CntError() {
        return tispol1CntError;
    } // method getTispol1CntError

    /**
     * Gets the errorMessage for the tispol1Cnt instance variable
     * @return errorMessage (String)
     */
    public String getTispol1CntErrorMessage() {
        return tispol1CntErrorMessage;
    } // method getTispol1CntErrorMessage


    /**
     * Gets the errorcode for the tispol2Cnt instance variable
     * @return errorcode (int)
     */
    public int getTispol2CntError() {
        return tispol2CntError;
    } // method getTispol2CntError

    /**
     * Gets the errorMessage for the tispol2Cnt instance variable
     * @return errorMessage (String)
     */
    public String getTispol2CntErrorMessage() {
        return tispol2CntErrorMessage;
    } // method getTispol2CntErrorMessage


    /**
     * Gets the errorcode for the tisanimalCnt instance variable
     * @return errorcode (int)
     */
    public int getTisanimalCntError() {
        return tisanimalCntError;
    } // method getTisanimalCntError

    /**
     * Gets the errorMessage for the tisanimalCnt instance variable
     * @return errorMessage (String)
     */
    public String getTisanimalCntErrorMessage() {
        return tisanimalCntErrorMessage;
    } // method getTisanimalCntErrorMessage


    /**
     * Gets the errorcode for the weatherCnt instance variable
     * @return errorcode (int)
     */
    public int getWeatherCntError() {
        return weatherCntError;
    } // method getWeatherCntError

    /**
     * Gets the errorMessage for the weatherCnt instance variable
     * @return errorMessage (String)
     */
    public String getWeatherCntErrorMessage() {
        return weatherCntErrorMessage;
    } // method getWeatherCntErrorMessage


    /**
     * Gets the errorcode for the watcurrentsCnt instance variable
     * @return errorcode (int)
     */
    public int getWatcurrentsCntError() {
        return watcurrentsCntError;
    } // method getWatcurrentsCntError

    /**
     * Gets the errorMessage for the watcurrentsCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatcurrentsCntErrorMessage() {
        return watcurrentsCntErrorMessage;
    } // method getWatcurrentsCntErrorMessage


    /**
     * Gets the errorcode for the watosdCnt instance variable
     * @return errorcode (int)
     */
    public int getWatosdCntError() {
        return watosdCntError;
    } // method getWatosdCntError

    /**
     * Gets the errorMessage for the watosdCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatosdCntErrorMessage() {
        return watosdCntErrorMessage;
    } // method getWatosdCntErrorMessage


    /**
     * Gets the errorcode for the watctdCnt instance variable
     * @return errorcode (int)
     */
    public int getWatctdCntError() {
        return watctdCntError;
    } // method getWatctdCntError

    /**
     * Gets the errorMessage for the watctdCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatctdCntErrorMessage() {
        return watctdCntErrorMessage;
    } // method getWatctdCntErrorMessage


    /**
     * Gets the errorcode for the watxbtCnt instance variable
     * @return errorcode (int)
     */
    public int getWatxbtCntError() {
        return watxbtCntError;
    } // method getWatxbtCntError

    /**
     * Gets the errorMessage for the watxbtCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatxbtCntErrorMessage() {
        return watxbtCntErrorMessage;
    } // method getWatxbtCntErrorMessage


    /**
     * Gets the errorcode for the watmbtCnt instance variable
     * @return errorcode (int)
     */
    public int getWatmbtCntError() {
        return watmbtCntError;
    } // method getWatmbtCntError

    /**
     * Gets the errorMessage for the watmbtCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatmbtCntErrorMessage() {
        return watmbtCntErrorMessage;
    } // method getWatmbtCntErrorMessage


    /**
     * Gets the errorcode for the watpflCnt instance variable
     * @return errorcode (int)
     */
    public int getWatpflCntError() {
        return watpflCntError;
    } // method getWatpflCntError

    /**
     * Gets the errorMessage for the watpflCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatpflCntErrorMessage() {
        return watpflCntErrorMessage;
    } // method getWatpflCntErrorMessage


    /**
     * Gets the errorcode for the watphyStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatphyStnCntError() {
        return watphyStnCntError;
    } // method getWatphyStnCntError

    /**
     * Gets the errorMessage for the watphyStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatphyStnCntErrorMessage() {
        return watphyStnCntErrorMessage;
    } // method getWatphyStnCntErrorMessage


    /**
     * Gets the errorcode for the watosdStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatosdStnCntError() {
        return watosdStnCntError;
    } // method getWatosdStnCntError

    /**
     * Gets the errorMessage for the watosdStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatosdStnCntErrorMessage() {
        return watosdStnCntErrorMessage;
    } // method getWatosdStnCntErrorMessage


    /**
     * Gets the errorcode for the watctdStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatctdStnCntError() {
        return watctdStnCntError;
    } // method getWatctdStnCntError

    /**
     * Gets the errorMessage for the watctdStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatctdStnCntErrorMessage() {
        return watctdStnCntErrorMessage;
    } // method getWatctdStnCntErrorMessage


    /**
     * Gets the errorcode for the watxbtStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatxbtStnCntError() {
        return watxbtStnCntError;
    } // method getWatxbtStnCntError

    /**
     * Gets the errorMessage for the watxbtStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatxbtStnCntErrorMessage() {
        return watxbtStnCntErrorMessage;
    } // method getWatxbtStnCntErrorMessage


    /**
     * Gets the errorcode for the watmbtStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatmbtStnCntError() {
        return watmbtStnCntError;
    } // method getWatmbtStnCntError

    /**
     * Gets the errorMessage for the watmbtStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatmbtStnCntErrorMessage() {
        return watmbtStnCntErrorMessage;
    } // method getWatmbtStnCntErrorMessage


    /**
     * Gets the errorcode for the watpflStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatpflStnCntError() {
        return watpflStnCntError;
    } // method getWatpflStnCntError

    /**
     * Gets the errorMessage for the watpflStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatpflStnCntErrorMessage() {
        return watpflStnCntErrorMessage;
    } // method getWatpflStnCntErrorMessage


    /**
     * Gets the errorcode for the watnutStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatnutStnCntError() {
        return watnutStnCntError;
    } // method getWatnutStnCntError

    /**
     * Gets the errorMessage for the watnutStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatnutStnCntErrorMessage() {
        return watnutStnCntErrorMessage;
    } // method getWatnutStnCntErrorMessage


    /**
     * Gets the errorcode for the watpolStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatpolStnCntError() {
        return watpolStnCntError;
    } // method getWatpolStnCntError

    /**
     * Gets the errorMessage for the watpolStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatpolStnCntErrorMessage() {
        return watpolStnCntErrorMessage;
    } // method getWatpolStnCntErrorMessage


    /**
     * Gets the errorcode for the watchemStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatchemStnCntError() {
        return watchemStnCntError;
    } // method getWatchemStnCntError

    /**
     * Gets the errorMessage for the watchemStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatchemStnCntErrorMessage() {
        return watchemStnCntErrorMessage;
    } // method getWatchemStnCntErrorMessage


    /**
     * Gets the errorcode for the watchlStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatchlStnCntError() {
        return watchlStnCntError;
    } // method getWatchlStnCntError

    /**
     * Gets the errorMessage for the watchlStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatchlStnCntErrorMessage() {
        return watchlStnCntErrorMessage;
    } // method getWatchlStnCntErrorMessage


    /**
     * Gets the errorcode for the sedphyStnCnt instance variable
     * @return errorcode (int)
     */
    public int getSedphyStnCntError() {
        return sedphyStnCntError;
    } // method getSedphyStnCntError

    /**
     * Gets the errorMessage for the sedphyStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getSedphyStnCntErrorMessage() {
        return sedphyStnCntErrorMessage;
    } // method getSedphyStnCntErrorMessage


    /**
     * Gets the errorcode for the sedpesStnCnt instance variable
     * @return errorcode (int)
     */
    public int getSedpesStnCntError() {
        return sedpesStnCntError;
    } // method getSedpesStnCntError

    /**
     * Gets the errorMessage for the sedpesStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getSedpesStnCntErrorMessage() {
        return sedpesStnCntErrorMessage;
    } // method getSedpesStnCntErrorMessage


    /**
     * Gets the errorcode for the sedpolStnCnt instance variable
     * @return errorcode (int)
     */
    public int getSedpolStnCntError() {
        return sedpolStnCntError;
    } // method getSedpolStnCntError

    /**
     * Gets the errorMessage for the sedpolStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getSedpolStnCntErrorMessage() {
        return sedpolStnCntErrorMessage;
    } // method getSedpolStnCntErrorMessage


    /**
     * Gets the errorcode for the sedchemStnCnt instance variable
     * @return errorcode (int)
     */
    public int getSedchemStnCntError() {
        return sedchemStnCntError;
    } // method getSedchemStnCntError

    /**
     * Gets the errorMessage for the sedchemStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getSedchemStnCntErrorMessage() {
        return sedchemStnCntErrorMessage;
    } // method getSedchemStnCntErrorMessage


    /**
     * Gets the errorcode for the sedtaxStnCnt instance variable
     * @return errorcode (int)
     */
    public int getSedtaxStnCntError() {
        return sedtaxStnCntError;
    } // method getSedtaxStnCntError

    /**
     * Gets the errorMessage for the sedtaxStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getSedtaxStnCntErrorMessage() {
        return sedtaxStnCntErrorMessage;
    } // method getSedtaxStnCntErrorMessage


    /**
     * Gets the errorcode for the plaphyStnCnt instance variable
     * @return errorcode (int)
     */
    public int getPlaphyStnCntError() {
        return plaphyStnCntError;
    } // method getPlaphyStnCntError

    /**
     * Gets the errorMessage for the plaphyStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlaphyStnCntErrorMessage() {
        return plaphyStnCntErrorMessage;
    } // method getPlaphyStnCntErrorMessage


    /**
     * Gets the errorcode for the plapesStnCnt instance variable
     * @return errorcode (int)
     */
    public int getPlapesStnCntError() {
        return plapesStnCntError;
    } // method getPlapesStnCntError

    /**
     * Gets the errorMessage for the plapesStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlapesStnCntErrorMessage() {
        return plapesStnCntErrorMessage;
    } // method getPlapesStnCntErrorMessage


    /**
     * Gets the errorcode for the plapolStnCnt instance variable
     * @return errorcode (int)
     */
    public int getPlapolStnCntError() {
        return plapolStnCntError;
    } // method getPlapolStnCntError

    /**
     * Gets the errorMessage for the plapolStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlapolStnCntErrorMessage() {
        return plapolStnCntErrorMessage;
    } // method getPlapolStnCntErrorMessage


    /**
     * Gets the errorcode for the plataxStnCnt instance variable
     * @return errorcode (int)
     */
    public int getPlataxStnCntError() {
        return plataxStnCntError;
    } // method getPlataxStnCntError

    /**
     * Gets the errorMessage for the plataxStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlataxStnCntErrorMessage() {
        return plataxStnCntErrorMessage;
    } // method getPlataxStnCntErrorMessage


    /**
     * Gets the errorcode for the plachlStnCnt instance variable
     * @return errorcode (int)
     */
    public int getPlachlStnCntError() {
        return plachlStnCntError;
    } // method getPlachlStnCntError

    /**
     * Gets the errorMessage for the plachlStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getPlachlStnCntErrorMessage() {
        return plachlStnCntErrorMessage;
    } // method getPlachlStnCntErrorMessage


    /**
     * Gets the errorcode for the tisphyStnCnt instance variable
     * @return errorcode (int)
     */
    public int getTisphyStnCntError() {
        return tisphyStnCntError;
    } // method getTisphyStnCntError

    /**
     * Gets the errorMessage for the tisphyStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getTisphyStnCntErrorMessage() {
        return tisphyStnCntErrorMessage;
    } // method getTisphyStnCntErrorMessage


    /**
     * Gets the errorcode for the tispesStnCnt instance variable
     * @return errorcode (int)
     */
    public int getTispesStnCntError() {
        return tispesStnCntError;
    } // method getTispesStnCntError

    /**
     * Gets the errorMessage for the tispesStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getTispesStnCntErrorMessage() {
        return tispesStnCntErrorMessage;
    } // method getTispesStnCntErrorMessage


    /**
     * Gets the errorcode for the tispolStnCnt instance variable
     * @return errorcode (int)
     */
    public int getTispolStnCntError() {
        return tispolStnCntError;
    } // method getTispolStnCntError

    /**
     * Gets the errorMessage for the tispolStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getTispolStnCntErrorMessage() {
        return tispolStnCntErrorMessage;
    } // method getTispolStnCntErrorMessage


    /**
     * Gets the errorcode for the tisanimalStnCnt instance variable
     * @return errorcode (int)
     */
    public int getTisanimalStnCntError() {
        return tisanimalStnCntError;
    } // method getTisanimalStnCntError

    /**
     * Gets the errorMessage for the tisanimalStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getTisanimalStnCntErrorMessage() {
        return tisanimalStnCntErrorMessage;
    } // method getTisanimalStnCntErrorMessage


    /**
     * Gets the errorcode for the watcurrentsStnCnt instance variable
     * @return errorcode (int)
     */
    public int getWatcurrentsStnCntError() {
        return watcurrentsStnCntError;
    } // method getWatcurrentsStnCntError

    /**
     * Gets the errorMessage for the watcurrentsStnCnt instance variable
     * @return errorMessage (String)
     */
    public String getWatcurrentsStnCntErrorMessage() {
        return watcurrentsStnCntErrorMessage;
    } // method getWatcurrentsStnCntErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnInvStats. e.g.<pre>
     * MrnInvStats invStats = new MrnInvStats(<val1>);
     * MrnInvStats invStatsArray[] = invStats.get();</pre>
     * will get the MrnInvStats record where surveyId = <val1>.
     * @return Array of MrnInvStats (MrnInvStats[])
     */
    public MrnInvStats[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnInvStats invStatsArray[] =
     *     new MrnInvStats().get(MrnInvStats.SURVEY_ID+"=<val1>");</pre>
     * will get the MrnInvStats record where surveyId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnInvStats (MrnInvStats[])
     */
    public MrnInvStats[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnInvStats invStatsArray[] =
     *     new MrnInvStats().get("1=1",invStats.SURVEY_ID);</pre>
     * will get the all the MrnInvStats records, and order them by surveyId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnInvStats (MrnInvStats[])
     */
    public MrnInvStats[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnInvStats.SURVEY_ID,MrnInvStats.STATION_CNT;
     * String where = MrnInvStats.SURVEY_ID + "=<val1";
     * String order = MrnInvStats.SURVEY_ID;
     * MrnInvStats invStatsArray[] =
     *     new MrnInvStats().get(columns, where, order);</pre>
     * will get the surveyId and stationCnt colums of all MrnInvStats records,
     * where surveyId = <val1>, and order them by surveyId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnInvStats (MrnInvStats[])
     */
    public MrnInvStats[] get (String fields, String where, String order) {
        // create the column list from the instance variables if neccessary
        try {
            if (fields.equals("")) { fields = null; }
        } catch (NullPointerException e) {}
        if (fields == null) {
            fields = createColumns();
        } // if (fields != null)
        return doGet(db.select(fields, TABLE, where, order));
    } // method get

    /**
     * Receives the result of the select statement from DbAccess,
     * and transforms it into an array of MrnInvStats.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnInvStats[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int surveyIdCol          = db.getColNumber(SURVEY_ID);
        int stationCntCol        = db.getColNumber(STATION_CNT);
        int watphyCntCol         = db.getColNumber(WATPHY_CNT);
        int watnutCntCol         = db.getColNumber(WATNUT_CNT);
        int watpol1CntCol        = db.getColNumber(WATPOL1_CNT);
        int watpol2CntCol        = db.getColNumber(WATPOL2_CNT);
        int watchem1CntCol       = db.getColNumber(WATCHEM1_CNT);
        int watchem2CntCol       = db.getColNumber(WATCHEM2_CNT);
        int watchlCntCol         = db.getColNumber(WATCHL_CNT);
        int sedphyCntCol         = db.getColNumber(SEDPHY_CNT);
        int sedpesCntCol         = db.getColNumber(SEDPES_CNT);
        int sedpol1CntCol        = db.getColNumber(SEDPOL1_CNT);
        int sedpol2CntCol        = db.getColNumber(SEDPOL2_CNT);
        int sedchem1CntCol       = db.getColNumber(SEDCHEM1_CNT);
        int sedchem2CntCol       = db.getColNumber(SEDCHEM2_CNT);
        int sedtaxCntCol         = db.getColNumber(SEDTAX_CNT);
        int plaphyCntCol         = db.getColNumber(PLAPHY_CNT);
        int plapesCntCol         = db.getColNumber(PLAPES_CNT);
        int plapol1CntCol        = db.getColNumber(PLAPOL1_CNT);
        int plapol2CntCol        = db.getColNumber(PLAPOL2_CNT);
        int plataxCntCol         = db.getColNumber(PLATAX_CNT);
        int plachlCntCol         = db.getColNumber(PLACHL_CNT);
        int tisphyCntCol         = db.getColNumber(TISPHY_CNT);
        int tispesCntCol         = db.getColNumber(TISPES_CNT);
        int tispol1CntCol        = db.getColNumber(TISPOL1_CNT);
        int tispol2CntCol        = db.getColNumber(TISPOL2_CNT);
        int tisanimalCntCol      = db.getColNumber(TISANIMAL_CNT);
        int weatherCntCol        = db.getColNumber(WEATHER_CNT);
        int watcurrentsCntCol    = db.getColNumber(WATCURRENTS_CNT);
        int watosdCntCol         = db.getColNumber(WATOSD_CNT);
        int watctdCntCol         = db.getColNumber(WATCTD_CNT);
        int watxbtCntCol         = db.getColNumber(WATXBT_CNT);
        int watmbtCntCol         = db.getColNumber(WATMBT_CNT);
        int watpflCntCol         = db.getColNumber(WATPFL_CNT);
        int watphyStnCntCol      = db.getColNumber(WATPHY_STN_CNT);
        int watosdStnCntCol      = db.getColNumber(WATOSD_STN_CNT);
        int watctdStnCntCol      = db.getColNumber(WATCTD_STN_CNT);
        int watxbtStnCntCol      = db.getColNumber(WATXBT_STN_CNT);
        int watmbtStnCntCol      = db.getColNumber(WATMBT_STN_CNT);
        int watpflStnCntCol      = db.getColNumber(WATPFL_STN_CNT);
        int watnutStnCntCol      = db.getColNumber(WATNUT_STN_CNT);
        int watpolStnCntCol      = db.getColNumber(WATPOL_STN_CNT);
        int watchemStnCntCol     = db.getColNumber(WATCHEM_STN_CNT);
        int watchlStnCntCol      = db.getColNumber(WATCHL_STN_CNT);
        int sedphyStnCntCol      = db.getColNumber(SEDPHY_STN_CNT);
        int sedpesStnCntCol      = db.getColNumber(SEDPES_STN_CNT);
        int sedpolStnCntCol      = db.getColNumber(SEDPOL_STN_CNT);
        int sedchemStnCntCol     = db.getColNumber(SEDCHEM_STN_CNT);
        int sedtaxStnCntCol      = db.getColNumber(SEDTAX_STN_CNT);
        int plaphyStnCntCol      = db.getColNumber(PLAPHY_STN_CNT);
        int plapesStnCntCol      = db.getColNumber(PLAPES_STN_CNT);
        int plapolStnCntCol      = db.getColNumber(PLAPOL_STN_CNT);
        int plataxStnCntCol      = db.getColNumber(PLATAX_STN_CNT);
        int plachlStnCntCol      = db.getColNumber(PLACHL_STN_CNT);
        int tisphyStnCntCol      = db.getColNumber(TISPHY_STN_CNT);
        int tispesStnCntCol      = db.getColNumber(TISPES_STN_CNT);
        int tispolStnCntCol      = db.getColNumber(TISPOL_STN_CNT);
        int tisanimalStnCntCol   = db.getColNumber(TISANIMAL_STN_CNT);
        int watcurrentsStnCntCol = db.getColNumber(WATCURRENTS_STN_CNT);
        MrnInvStats[] cArray = new MrnInvStats[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnInvStats();
            if (surveyIdCol != -1)
                cArray[i].setSurveyId         ((String) row.elementAt(surveyIdCol));
            if (stationCntCol != -1)
                cArray[i].setStationCnt       ((String) row.elementAt(stationCntCol));
            if (watphyCntCol != -1)
                cArray[i].setWatphyCnt        ((String) row.elementAt(watphyCntCol));
            if (watnutCntCol != -1)
                cArray[i].setWatnutCnt        ((String) row.elementAt(watnutCntCol));
            if (watpol1CntCol != -1)
                cArray[i].setWatpol1Cnt       ((String) row.elementAt(watpol1CntCol));
            if (watpol2CntCol != -1)
                cArray[i].setWatpol2Cnt       ((String) row.elementAt(watpol2CntCol));
            if (watchem1CntCol != -1)
                cArray[i].setWatchem1Cnt      ((String) row.elementAt(watchem1CntCol));
            if (watchem2CntCol != -1)
                cArray[i].setWatchem2Cnt      ((String) row.elementAt(watchem2CntCol));
            if (watchlCntCol != -1)
                cArray[i].setWatchlCnt        ((String) row.elementAt(watchlCntCol));
            if (sedphyCntCol != -1)
                cArray[i].setSedphyCnt        ((String) row.elementAt(sedphyCntCol));
            if (sedpesCntCol != -1)
                cArray[i].setSedpesCnt        ((String) row.elementAt(sedpesCntCol));
            if (sedpol1CntCol != -1)
                cArray[i].setSedpol1Cnt       ((String) row.elementAt(sedpol1CntCol));
            if (sedpol2CntCol != -1)
                cArray[i].setSedpol2Cnt       ((String) row.elementAt(sedpol2CntCol));
            if (sedchem1CntCol != -1)
                cArray[i].setSedchem1Cnt      ((String) row.elementAt(sedchem1CntCol));
            if (sedchem2CntCol != -1)
                cArray[i].setSedchem2Cnt      ((String) row.elementAt(sedchem2CntCol));
            if (sedtaxCntCol != -1)
                cArray[i].setSedtaxCnt        ((String) row.elementAt(sedtaxCntCol));
            if (plaphyCntCol != -1)
                cArray[i].setPlaphyCnt        ((String) row.elementAt(plaphyCntCol));
            if (plapesCntCol != -1)
                cArray[i].setPlapesCnt        ((String) row.elementAt(plapesCntCol));
            if (plapol1CntCol != -1)
                cArray[i].setPlapol1Cnt       ((String) row.elementAt(plapol1CntCol));
            if (plapol2CntCol != -1)
                cArray[i].setPlapol2Cnt       ((String) row.elementAt(plapol2CntCol));
            if (plataxCntCol != -1)
                cArray[i].setPlataxCnt        ((String) row.elementAt(plataxCntCol));
            if (plachlCntCol != -1)
                cArray[i].setPlachlCnt        ((String) row.elementAt(plachlCntCol));
            if (tisphyCntCol != -1)
                cArray[i].setTisphyCnt        ((String) row.elementAt(tisphyCntCol));
            if (tispesCntCol != -1)
                cArray[i].setTispesCnt        ((String) row.elementAt(tispesCntCol));
            if (tispol1CntCol != -1)
                cArray[i].setTispol1Cnt       ((String) row.elementAt(tispol1CntCol));
            if (tispol2CntCol != -1)
                cArray[i].setTispol2Cnt       ((String) row.elementAt(tispol2CntCol));
            if (tisanimalCntCol != -1)
                cArray[i].setTisanimalCnt     ((String) row.elementAt(tisanimalCntCol));
            if (weatherCntCol != -1)
                cArray[i].setWeatherCnt       ((String) row.elementAt(weatherCntCol));
            if (watcurrentsCntCol != -1)
                cArray[i].setWatcurrentsCnt   ((String) row.elementAt(watcurrentsCntCol));
            if (watosdCntCol != -1)
                cArray[i].setWatosdCnt        ((String) row.elementAt(watosdCntCol));
            if (watctdCntCol != -1)
                cArray[i].setWatctdCnt        ((String) row.elementAt(watctdCntCol));
            if (watxbtCntCol != -1)
                cArray[i].setWatxbtCnt        ((String) row.elementAt(watxbtCntCol));
            if (watmbtCntCol != -1)
                cArray[i].setWatmbtCnt        ((String) row.elementAt(watmbtCntCol));
            if (watpflCntCol != -1)
                cArray[i].setWatpflCnt        ((String) row.elementAt(watpflCntCol));
            if (watphyStnCntCol != -1)
                cArray[i].setWatphyStnCnt     ((String) row.elementAt(watphyStnCntCol));
            if (watosdStnCntCol != -1)
                cArray[i].setWatosdStnCnt     ((String) row.elementAt(watosdStnCntCol));
            if (watctdStnCntCol != -1)
                cArray[i].setWatctdStnCnt     ((String) row.elementAt(watctdStnCntCol));
            if (watxbtStnCntCol != -1)
                cArray[i].setWatxbtStnCnt     ((String) row.elementAt(watxbtStnCntCol));
            if (watmbtStnCntCol != -1)
                cArray[i].setWatmbtStnCnt     ((String) row.elementAt(watmbtStnCntCol));
            if (watpflStnCntCol != -1)
                cArray[i].setWatpflStnCnt     ((String) row.elementAt(watpflStnCntCol));
            if (watnutStnCntCol != -1)
                cArray[i].setWatnutStnCnt     ((String) row.elementAt(watnutStnCntCol));
            if (watpolStnCntCol != -1)
                cArray[i].setWatpolStnCnt     ((String) row.elementAt(watpolStnCntCol));
            if (watchemStnCntCol != -1)
                cArray[i].setWatchemStnCnt    ((String) row.elementAt(watchemStnCntCol));
            if (watchlStnCntCol != -1)
                cArray[i].setWatchlStnCnt     ((String) row.elementAt(watchlStnCntCol));
            if (sedphyStnCntCol != -1)
                cArray[i].setSedphyStnCnt     ((String) row.elementAt(sedphyStnCntCol));
            if (sedpesStnCntCol != -1)
                cArray[i].setSedpesStnCnt     ((String) row.elementAt(sedpesStnCntCol));
            if (sedpolStnCntCol != -1)
                cArray[i].setSedpolStnCnt     ((String) row.elementAt(sedpolStnCntCol));
            if (sedchemStnCntCol != -1)
                cArray[i].setSedchemStnCnt    ((String) row.elementAt(sedchemStnCntCol));
            if (sedtaxStnCntCol != -1)
                cArray[i].setSedtaxStnCnt     ((String) row.elementAt(sedtaxStnCntCol));
            if (plaphyStnCntCol != -1)
                cArray[i].setPlaphyStnCnt     ((String) row.elementAt(plaphyStnCntCol));
            if (plapesStnCntCol != -1)
                cArray[i].setPlapesStnCnt     ((String) row.elementAt(plapesStnCntCol));
            if (plapolStnCntCol != -1)
                cArray[i].setPlapolStnCnt     ((String) row.elementAt(plapolStnCntCol));
            if (plataxStnCntCol != -1)
                cArray[i].setPlataxStnCnt     ((String) row.elementAt(plataxStnCntCol));
            if (plachlStnCntCol != -1)
                cArray[i].setPlachlStnCnt     ((String) row.elementAt(plachlStnCntCol));
            if (tisphyStnCntCol != -1)
                cArray[i].setTisphyStnCnt     ((String) row.elementAt(tisphyStnCntCol));
            if (tispesStnCntCol != -1)
                cArray[i].setTispesStnCnt     ((String) row.elementAt(tispesStnCntCol));
            if (tispolStnCntCol != -1)
                cArray[i].setTispolStnCnt     ((String) row.elementAt(tispolStnCntCol));
            if (tisanimalStnCntCol != -1)
                cArray[i].setTisanimalStnCnt  ((String) row.elementAt(tisanimalStnCntCol));
            if (watcurrentsStnCntCol != -1)
                cArray[i].setWatcurrentsStnCnt((String) row.elementAt(watcurrentsStnCntCol));
        } // for i
        return cArray;
    } // method doGet


    //=====================//
    // All the put methods //
    //=====================//

    /**
     * Insert a record into the table.  The values are taken from the current
     * value of the instance variables. .e.g.<pre>
     * String    CHARNULL  = Tables.CHARNULL;
     * Timestamp DATENULL  = Tables.DATENULL;
     * int       INTNULL   = Tables.INTNULL;
     * float     FLOATNULL = Tables.FLOATNULL;
     * Boolean success =
     *     new MrnInvStats(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>,
     *         <val9>,
     *         <val10>,
     *         <val11>,
     *         <val12>,
     *         <val13>,
     *         <val14>,
     *         <val15>,
     *         <val16>,
     *         <val17>,
     *         <val18>,
     *         <val19>,
     *         <val20>,
     *         <val21>,
     *         <val22>,
     *         <val23>,
     *         <val24>,
     *         <val25>,
     *         <val26>,
     *         <val27>,
     *         <val28>,
     *         <val29>,
     *         <val30>,
     *         <val31>,
     *         <val32>,
     *         <val33>,
     *         <val34>,
     *         <val35>,
     *         <val36>,
     *         <val37>,
     *         <val38>,
     *         <val39>,
     *         <val40>,
     *         <val41>,
     *         <val42>,
     *         <val43>,
     *         <val44>,
     *         <val45>,
     *         <val46>,
     *         <val47>,
     *         <val48>,
     *         <val49>,
     *         <val50>,
     *         <val51>,
     *         <val52>,
     *         <val53>,
     *         <val54>,
     *         <val55>,
     *         <val56>,
     *         <val57>,
     *         <val58>,
     *         <val59>).put();</pre>
     * will insert a record with:
     *     surveyId          = <val1>,
     *     stationCnt        = <val2>,
     *     watphyCnt         = <val3>,
     *     watnutCnt         = <val4>,
     *     watpol1Cnt        = <val5>,
     *     watpol2Cnt        = <val6>,
     *     watchem1Cnt       = <val7>,
     *     watchem2Cnt       = <val8>,
     *     watchlCnt         = <val9>,
     *     sedphyCnt         = <val10>,
     *     sedpesCnt         = <val11>,
     *     sedpol1Cnt        = <val12>,
     *     sedpol2Cnt        = <val13>,
     *     sedchem1Cnt       = <val14>,
     *     sedchem2Cnt       = <val15>,
     *     sedtaxCnt         = <val16>,
     *     plaphyCnt         = <val17>,
     *     plapesCnt         = <val18>,
     *     plapol1Cnt        = <val19>,
     *     plapol2Cnt        = <val20>,
     *     plataxCnt         = <val21>,
     *     plachlCnt         = <val22>,
     *     tisphyCnt         = <val23>,
     *     tispesCnt         = <val24>,
     *     tispol1Cnt        = <val25>,
     *     tispol2Cnt        = <val26>,
     *     tisanimalCnt      = <val27>,
     *     weatherCnt        = <val28>,
     *     watcurrentsCnt    = <val29>,
     *     watosdCnt         = <val30>,
     *     watctdCnt         = <val31>,
     *     watxbtCnt         = <val32>,
     *     watmbtCnt         = <val33>,
     *     watpflCnt         = <val34>,
     *     watphyStnCnt      = <val35>,
     *     watosdStnCnt      = <val36>,
     *     watctdStnCnt      = <val37>,
     *     watxbtStnCnt      = <val38>,
     *     watmbtStnCnt      = <val39>,
     *     watpflStnCnt      = <val40>,
     *     watnutStnCnt      = <val41>,
     *     watpolStnCnt      = <val42>,
     *     watchemStnCnt     = <val43>,
     *     watchlStnCnt      = <val44>,
     *     sedphyStnCnt      = <val45>,
     *     sedpesStnCnt      = <val46>,
     *     sedpolStnCnt      = <val47>,
     *     sedchemStnCnt     = <val48>,
     *     sedtaxStnCnt      = <val49>,
     *     plaphyStnCnt      = <val50>,
     *     plapesStnCnt      = <val51>,
     *     plapolStnCnt      = <val52>,
     *     plataxStnCnt      = <val53>,
     *     plachlStnCnt      = <val54>,
     *     tisphyStnCnt      = <val55>,
     *     tispesStnCnt      = <val56>,
     *     tispolStnCnt      = <val57>,
     *     tisanimalStnCnt   = <val58>,
     *     watcurrentsStnCnt = <val59>.
     * @return success = true/false (boolean)
     */
    public boolean put() {
        return db.insert (TABLE, createColumns(), createValues());
    } // method put


    //=====================//
    // All the del methods //
    //=====================//

    /**
     * Delete record(s) from the table, The where clause is created from the
     * current values of the instance variables. .e.g.<pre>
     * Boolean success = new MrnInvStats(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where stationCnt = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnInvStats invStats = new MrnInvStats();
     * success = invStats.del(MrnInvStats.SURVEY_ID+"=<val1>");</pre>
     * will delete all records where surveyId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean del(String where) {
        return db.delete (TABLE, where);
    } // method del


    //=====================//
    // All the upd methods //
    //=====================//

    /**
     * Update record(s) from the table, The fields and values to use for the
     * update are taken from the MrnInvStats argument, .e.g.<pre>
     * Boolean success
     * MrnInvStats updMrnInvStats = new MrnInvStats();
     * updMrnInvStats.setStationCnt(<val2>);
     * MrnInvStats whereMrnInvStats = new MrnInvStats(<val1>);
     * success = whereMrnInvStats.upd(updMrnInvStats);</pre>
     * will update StationCnt to <val2> for all records where
     * surveyId = <val1>.
     * @param invStats  A MrnInvStats variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnInvStats invStats) {
        return db.update (TABLE, createColVals(invStats), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnInvStats updMrnInvStats = new MrnInvStats();
     * updMrnInvStats.setStationCnt(<val2>);
     * MrnInvStats whereMrnInvStats = new MrnInvStats();
     * success = whereMrnInvStats.upd(
     *     updMrnInvStats, MrnInvStats.SURVEY_ID+"=<val1>");</pre>
     * will update StationCnt to <val2> for all records where
     * surveyId = <val1>.
     * @param  updMrnInvStats  A MrnInvStats variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnInvStats invStats, String where) {
        return db.update (TABLE, createColVals(invStats), where);
    } // method upd


    //======================//
    // Other helper methods //
    //======================//

    /**
     * Creates the where clause from the current values of the
     * instance variables.
     * @return  where clause (String)
     */
    private String createWhere() {
        String where = "";
        if (getSurveyId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_ID + "='" + getSurveyId() + "'";
        } // if getSurveyId
        if (getStationCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATION_CNT + "=" + getStationCnt("");
        } // if getStationCnt
        if (getWatphyCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPHY_CNT + "=" + getWatphyCnt("");
        } // if getWatphyCnt
        if (getWatnutCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATNUT_CNT + "=" + getWatnutCnt("");
        } // if getWatnutCnt
        if (getWatpol1Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPOL1_CNT + "=" + getWatpol1Cnt("");
        } // if getWatpol1Cnt
        if (getWatpol2Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPOL2_CNT + "=" + getWatpol2Cnt("");
        } // if getWatpol2Cnt
        if (getWatchem1Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATCHEM1_CNT + "=" + getWatchem1Cnt("");
        } // if getWatchem1Cnt
        if (getWatchem2Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATCHEM2_CNT + "=" + getWatchem2Cnt("");
        } // if getWatchem2Cnt
        if (getWatchlCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATCHL_CNT + "=" + getWatchlCnt("");
        } // if getWatchlCnt
        if (getSedphyCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDPHY_CNT + "=" + getSedphyCnt("");
        } // if getSedphyCnt
        if (getSedpesCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDPES_CNT + "=" + getSedpesCnt("");
        } // if getSedpesCnt
        if (getSedpol1Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDPOL1_CNT + "=" + getSedpol1Cnt("");
        } // if getSedpol1Cnt
        if (getSedpol2Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDPOL2_CNT + "=" + getSedpol2Cnt("");
        } // if getSedpol2Cnt
        if (getSedchem1Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDCHEM1_CNT + "=" + getSedchem1Cnt("");
        } // if getSedchem1Cnt
        if (getSedchem2Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDCHEM2_CNT + "=" + getSedchem2Cnt("");
        } // if getSedchem2Cnt
        if (getSedtaxCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDTAX_CNT + "=" + getSedtaxCnt("");
        } // if getSedtaxCnt
        if (getPlaphyCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPHY_CNT + "=" + getPlaphyCnt("");
        } // if getPlaphyCnt
        if (getPlapesCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPES_CNT + "=" + getPlapesCnt("");
        } // if getPlapesCnt
        if (getPlapol1Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPOL1_CNT + "=" + getPlapol1Cnt("");
        } // if getPlapol1Cnt
        if (getPlapol2Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPOL2_CNT + "=" + getPlapol2Cnt("");
        } // if getPlapol2Cnt
        if (getPlataxCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLATAX_CNT + "=" + getPlataxCnt("");
        } // if getPlataxCnt
        if (getPlachlCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLACHL_CNT + "=" + getPlachlCnt("");
        } // if getPlachlCnt
        if (getTisphyCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISPHY_CNT + "=" + getTisphyCnt("");
        } // if getTisphyCnt
        if (getTispesCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISPES_CNT + "=" + getTispesCnt("");
        } // if getTispesCnt
        if (getTispol1Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISPOL1_CNT + "=" + getTispol1Cnt("");
        } // if getTispol1Cnt
        if (getTispol2Cnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISPOL2_CNT + "=" + getTispol2Cnt("");
        } // if getTispol2Cnt
        if (getTisanimalCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISANIMAL_CNT + "=" + getTisanimalCnt("");
        } // if getTisanimalCnt
        if (getWeatherCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WEATHER_CNT + "=" + getWeatherCnt("");
        } // if getWeatherCnt
        if (getWatcurrentsCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATCURRENTS_CNT + "=" + getWatcurrentsCnt("");
        } // if getWatcurrentsCnt
        if (getWatosdCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATOSD_CNT + "=" + getWatosdCnt("");
        } // if getWatosdCnt
        if (getWatctdCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATCTD_CNT + "=" + getWatctdCnt("");
        } // if getWatctdCnt
        if (getWatxbtCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATXBT_CNT + "=" + getWatxbtCnt("");
        } // if getWatxbtCnt
        if (getWatmbtCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATMBT_CNT + "=" + getWatmbtCnt("");
        } // if getWatmbtCnt
        if (getWatpflCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPFL_CNT + "=" + getWatpflCnt("");
        } // if getWatpflCnt
        if (getWatphyStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPHY_STN_CNT + "=" + getWatphyStnCnt("");
        } // if getWatphyStnCnt
        if (getWatosdStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATOSD_STN_CNT + "=" + getWatosdStnCnt("");
        } // if getWatosdStnCnt
        if (getWatctdStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATCTD_STN_CNT + "=" + getWatctdStnCnt("");
        } // if getWatctdStnCnt
        if (getWatxbtStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATXBT_STN_CNT + "=" + getWatxbtStnCnt("");
        } // if getWatxbtStnCnt
        if (getWatmbtStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATMBT_STN_CNT + "=" + getWatmbtStnCnt("");
        } // if getWatmbtStnCnt
        if (getWatpflStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPFL_STN_CNT + "=" + getWatpflStnCnt("");
        } // if getWatpflStnCnt
        if (getWatnutStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATNUT_STN_CNT + "=" + getWatnutStnCnt("");
        } // if getWatnutStnCnt
        if (getWatpolStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPOL_STN_CNT + "=" + getWatpolStnCnt("");
        } // if getWatpolStnCnt
        if (getWatchemStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATCHEM_STN_CNT + "=" + getWatchemStnCnt("");
        } // if getWatchemStnCnt
        if (getWatchlStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATCHL_STN_CNT + "=" + getWatchlStnCnt("");
        } // if getWatchlStnCnt
        if (getSedphyStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDPHY_STN_CNT + "=" + getSedphyStnCnt("");
        } // if getSedphyStnCnt
        if (getSedpesStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDPES_STN_CNT + "=" + getSedpesStnCnt("");
        } // if getSedpesStnCnt
        if (getSedpolStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDPOL_STN_CNT + "=" + getSedpolStnCnt("");
        } // if getSedpolStnCnt
        if (getSedchemStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDCHEM_STN_CNT + "=" + getSedchemStnCnt("");
        } // if getSedchemStnCnt
        if (getSedtaxStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDTAX_STN_CNT + "=" + getSedtaxStnCnt("");
        } // if getSedtaxStnCnt
        if (getPlaphyStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPHY_STN_CNT + "=" + getPlaphyStnCnt("");
        } // if getPlaphyStnCnt
        if (getPlapesStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPES_STN_CNT + "=" + getPlapesStnCnt("");
        } // if getPlapesStnCnt
        if (getPlapolStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPOL_STN_CNT + "=" + getPlapolStnCnt("");
        } // if getPlapolStnCnt
        if (getPlataxStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLATAX_STN_CNT + "=" + getPlataxStnCnt("");
        } // if getPlataxStnCnt
        if (getPlachlStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLACHL_STN_CNT + "=" + getPlachlStnCnt("");
        } // if getPlachlStnCnt
        if (getTisphyStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISPHY_STN_CNT + "=" + getTisphyStnCnt("");
        } // if getTisphyStnCnt
        if (getTispesStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISPES_STN_CNT + "=" + getTispesStnCnt("");
        } // if getTispesStnCnt
        if (getTispolStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISPOL_STN_CNT + "=" + getTispolStnCnt("");
        } // if getTispolStnCnt
        if (getTisanimalStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISANIMAL_STN_CNT + "=" + getTisanimalStnCnt("");
        } // if getTisanimalStnCnt
        if (getWatcurrentsStnCnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATCURRENTS_STN_CNT + "=" + getWatcurrentsStnCnt("");
        } // if getWatcurrentsStnCnt
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnInvStats aVar) {
        String colVals = "";
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getStationCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_CNT +"=";
            colVals += (aVar.getStationCnt() == INTNULL2 ?
                "null" : aVar.getStationCnt(""));
        } // if aVar.getStationCnt
        if (aVar.getWatphyCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPHY_CNT +"=";
            colVals += (aVar.getWatphyCnt() == INTNULL2 ?
                "null" : aVar.getWatphyCnt(""));
        } // if aVar.getWatphyCnt
        if (aVar.getWatnutCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATNUT_CNT +"=";
            colVals += (aVar.getWatnutCnt() == INTNULL2 ?
                "null" : aVar.getWatnutCnt(""));
        } // if aVar.getWatnutCnt
        if (aVar.getWatpol1Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPOL1_CNT +"=";
            colVals += (aVar.getWatpol1Cnt() == INTNULL2 ?
                "null" : aVar.getWatpol1Cnt(""));
        } // if aVar.getWatpol1Cnt
        if (aVar.getWatpol2Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPOL2_CNT +"=";
            colVals += (aVar.getWatpol2Cnt() == INTNULL2 ?
                "null" : aVar.getWatpol2Cnt(""));
        } // if aVar.getWatpol2Cnt
        if (aVar.getWatchem1Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATCHEM1_CNT +"=";
            colVals += (aVar.getWatchem1Cnt() == INTNULL2 ?
                "null" : aVar.getWatchem1Cnt(""));
        } // if aVar.getWatchem1Cnt
        if (aVar.getWatchem2Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATCHEM2_CNT +"=";
            colVals += (aVar.getWatchem2Cnt() == INTNULL2 ?
                "null" : aVar.getWatchem2Cnt(""));
        } // if aVar.getWatchem2Cnt
        if (aVar.getWatchlCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATCHL_CNT +"=";
            colVals += (aVar.getWatchlCnt() == INTNULL2 ?
                "null" : aVar.getWatchlCnt(""));
        } // if aVar.getWatchlCnt
        if (aVar.getSedphyCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPHY_CNT +"=";
            colVals += (aVar.getSedphyCnt() == INTNULL2 ?
                "null" : aVar.getSedphyCnt(""));
        } // if aVar.getSedphyCnt
        if (aVar.getSedpesCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPES_CNT +"=";
            colVals += (aVar.getSedpesCnt() == INTNULL2 ?
                "null" : aVar.getSedpesCnt(""));
        } // if aVar.getSedpesCnt
        if (aVar.getSedpol1Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPOL1_CNT +"=";
            colVals += (aVar.getSedpol1Cnt() == INTNULL2 ?
                "null" : aVar.getSedpol1Cnt(""));
        } // if aVar.getSedpol1Cnt
        if (aVar.getSedpol2Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPOL2_CNT +"=";
            colVals += (aVar.getSedpol2Cnt() == INTNULL2 ?
                "null" : aVar.getSedpol2Cnt(""));
        } // if aVar.getSedpol2Cnt
        if (aVar.getSedchem1Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDCHEM1_CNT +"=";
            colVals += (aVar.getSedchem1Cnt() == INTNULL2 ?
                "null" : aVar.getSedchem1Cnt(""));
        } // if aVar.getSedchem1Cnt
        if (aVar.getSedchem2Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDCHEM2_CNT +"=";
            colVals += (aVar.getSedchem2Cnt() == INTNULL2 ?
                "null" : aVar.getSedchem2Cnt(""));
        } // if aVar.getSedchem2Cnt
        if (aVar.getSedtaxCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDTAX_CNT +"=";
            colVals += (aVar.getSedtaxCnt() == INTNULL2 ?
                "null" : aVar.getSedtaxCnt(""));
        } // if aVar.getSedtaxCnt
        if (aVar.getPlaphyCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPHY_CNT +"=";
            colVals += (aVar.getPlaphyCnt() == INTNULL2 ?
                "null" : aVar.getPlaphyCnt(""));
        } // if aVar.getPlaphyCnt
        if (aVar.getPlapesCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPES_CNT +"=";
            colVals += (aVar.getPlapesCnt() == INTNULL2 ?
                "null" : aVar.getPlapesCnt(""));
        } // if aVar.getPlapesCnt
        if (aVar.getPlapol1Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPOL1_CNT +"=";
            colVals += (aVar.getPlapol1Cnt() == INTNULL2 ?
                "null" : aVar.getPlapol1Cnt(""));
        } // if aVar.getPlapol1Cnt
        if (aVar.getPlapol2Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPOL2_CNT +"=";
            colVals += (aVar.getPlapol2Cnt() == INTNULL2 ?
                "null" : aVar.getPlapol2Cnt(""));
        } // if aVar.getPlapol2Cnt
        if (aVar.getPlataxCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLATAX_CNT +"=";
            colVals += (aVar.getPlataxCnt() == INTNULL2 ?
                "null" : aVar.getPlataxCnt(""));
        } // if aVar.getPlataxCnt
        if (aVar.getPlachlCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLACHL_CNT +"=";
            colVals += (aVar.getPlachlCnt() == INTNULL2 ?
                "null" : aVar.getPlachlCnt(""));
        } // if aVar.getPlachlCnt
        if (aVar.getTisphyCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISPHY_CNT +"=";
            colVals += (aVar.getTisphyCnt() == INTNULL2 ?
                "null" : aVar.getTisphyCnt(""));
        } // if aVar.getTisphyCnt
        if (aVar.getTispesCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISPES_CNT +"=";
            colVals += (aVar.getTispesCnt() == INTNULL2 ?
                "null" : aVar.getTispesCnt(""));
        } // if aVar.getTispesCnt
        if (aVar.getTispol1Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISPOL1_CNT +"=";
            colVals += (aVar.getTispol1Cnt() == INTNULL2 ?
                "null" : aVar.getTispol1Cnt(""));
        } // if aVar.getTispol1Cnt
        if (aVar.getTispol2Cnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISPOL2_CNT +"=";
            colVals += (aVar.getTispol2Cnt() == INTNULL2 ?
                "null" : aVar.getTispol2Cnt(""));
        } // if aVar.getTispol2Cnt
        if (aVar.getTisanimalCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISANIMAL_CNT +"=";
            colVals += (aVar.getTisanimalCnt() == INTNULL2 ?
                "null" : aVar.getTisanimalCnt(""));
        } // if aVar.getTisanimalCnt
        if (aVar.getWeatherCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WEATHER_CNT +"=";
            colVals += (aVar.getWeatherCnt() == INTNULL2 ?
                "null" : aVar.getWeatherCnt(""));
        } // if aVar.getWeatherCnt
        if (aVar.getWatcurrentsCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATCURRENTS_CNT +"=";
            colVals += (aVar.getWatcurrentsCnt() == INTNULL2 ?
                "null" : aVar.getWatcurrentsCnt(""));
        } // if aVar.getWatcurrentsCnt
        if (aVar.getWatosdCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATOSD_CNT +"=";
            colVals += (aVar.getWatosdCnt() == INTNULL2 ?
                "null" : aVar.getWatosdCnt(""));
        } // if aVar.getWatosdCnt
        if (aVar.getWatctdCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATCTD_CNT +"=";
            colVals += (aVar.getWatctdCnt() == INTNULL2 ?
                "null" : aVar.getWatctdCnt(""));
        } // if aVar.getWatctdCnt
        if (aVar.getWatxbtCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATXBT_CNT +"=";
            colVals += (aVar.getWatxbtCnt() == INTNULL2 ?
                "null" : aVar.getWatxbtCnt(""));
        } // if aVar.getWatxbtCnt
        if (aVar.getWatmbtCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATMBT_CNT +"=";
            colVals += (aVar.getWatmbtCnt() == INTNULL2 ?
                "null" : aVar.getWatmbtCnt(""));
        } // if aVar.getWatmbtCnt
        if (aVar.getWatpflCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPFL_CNT +"=";
            colVals += (aVar.getWatpflCnt() == INTNULL2 ?
                "null" : aVar.getWatpflCnt(""));
        } // if aVar.getWatpflCnt
        if (aVar.getWatphyStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPHY_STN_CNT +"=";
            colVals += (aVar.getWatphyStnCnt() == INTNULL2 ?
                "null" : aVar.getWatphyStnCnt(""));
        } // if aVar.getWatphyStnCnt
        if (aVar.getWatosdStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATOSD_STN_CNT +"=";
            colVals += (aVar.getWatosdStnCnt() == INTNULL2 ?
                "null" : aVar.getWatosdStnCnt(""));
        } // if aVar.getWatosdStnCnt
        if (aVar.getWatctdStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATCTD_STN_CNT +"=";
            colVals += (aVar.getWatctdStnCnt() == INTNULL2 ?
                "null" : aVar.getWatctdStnCnt(""));
        } // if aVar.getWatctdStnCnt
        if (aVar.getWatxbtStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATXBT_STN_CNT +"=";
            colVals += (aVar.getWatxbtStnCnt() == INTNULL2 ?
                "null" : aVar.getWatxbtStnCnt(""));
        } // if aVar.getWatxbtStnCnt
        if (aVar.getWatmbtStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATMBT_STN_CNT +"=";
            colVals += (aVar.getWatmbtStnCnt() == INTNULL2 ?
                "null" : aVar.getWatmbtStnCnt(""));
        } // if aVar.getWatmbtStnCnt
        if (aVar.getWatpflStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPFL_STN_CNT +"=";
            colVals += (aVar.getWatpflStnCnt() == INTNULL2 ?
                "null" : aVar.getWatpflStnCnt(""));
        } // if aVar.getWatpflStnCnt
        if (aVar.getWatnutStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATNUT_STN_CNT +"=";
            colVals += (aVar.getWatnutStnCnt() == INTNULL2 ?
                "null" : aVar.getWatnutStnCnt(""));
        } // if aVar.getWatnutStnCnt
        if (aVar.getWatpolStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPOL_STN_CNT +"=";
            colVals += (aVar.getWatpolStnCnt() == INTNULL2 ?
                "null" : aVar.getWatpolStnCnt(""));
        } // if aVar.getWatpolStnCnt
        if (aVar.getWatchemStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATCHEM_STN_CNT +"=";
            colVals += (aVar.getWatchemStnCnt() == INTNULL2 ?
                "null" : aVar.getWatchemStnCnt(""));
        } // if aVar.getWatchemStnCnt
        if (aVar.getWatchlStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATCHL_STN_CNT +"=";
            colVals += (aVar.getWatchlStnCnt() == INTNULL2 ?
                "null" : aVar.getWatchlStnCnt(""));
        } // if aVar.getWatchlStnCnt
        if (aVar.getSedphyStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPHY_STN_CNT +"=";
            colVals += (aVar.getSedphyStnCnt() == INTNULL2 ?
                "null" : aVar.getSedphyStnCnt(""));
        } // if aVar.getSedphyStnCnt
        if (aVar.getSedpesStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPES_STN_CNT +"=";
            colVals += (aVar.getSedpesStnCnt() == INTNULL2 ?
                "null" : aVar.getSedpesStnCnt(""));
        } // if aVar.getSedpesStnCnt
        if (aVar.getSedpolStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPOL_STN_CNT +"=";
            colVals += (aVar.getSedpolStnCnt() == INTNULL2 ?
                "null" : aVar.getSedpolStnCnt(""));
        } // if aVar.getSedpolStnCnt
        if (aVar.getSedchemStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDCHEM_STN_CNT +"=";
            colVals += (aVar.getSedchemStnCnt() == INTNULL2 ?
                "null" : aVar.getSedchemStnCnt(""));
        } // if aVar.getSedchemStnCnt
        if (aVar.getSedtaxStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDTAX_STN_CNT +"=";
            colVals += (aVar.getSedtaxStnCnt() == INTNULL2 ?
                "null" : aVar.getSedtaxStnCnt(""));
        } // if aVar.getSedtaxStnCnt
        if (aVar.getPlaphyStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPHY_STN_CNT +"=";
            colVals += (aVar.getPlaphyStnCnt() == INTNULL2 ?
                "null" : aVar.getPlaphyStnCnt(""));
        } // if aVar.getPlaphyStnCnt
        if (aVar.getPlapesStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPES_STN_CNT +"=";
            colVals += (aVar.getPlapesStnCnt() == INTNULL2 ?
                "null" : aVar.getPlapesStnCnt(""));
        } // if aVar.getPlapesStnCnt
        if (aVar.getPlapolStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPOL_STN_CNT +"=";
            colVals += (aVar.getPlapolStnCnt() == INTNULL2 ?
                "null" : aVar.getPlapolStnCnt(""));
        } // if aVar.getPlapolStnCnt
        if (aVar.getPlataxStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLATAX_STN_CNT +"=";
            colVals += (aVar.getPlataxStnCnt() == INTNULL2 ?
                "null" : aVar.getPlataxStnCnt(""));
        } // if aVar.getPlataxStnCnt
        if (aVar.getPlachlStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLACHL_STN_CNT +"=";
            colVals += (aVar.getPlachlStnCnt() == INTNULL2 ?
                "null" : aVar.getPlachlStnCnt(""));
        } // if aVar.getPlachlStnCnt
        if (aVar.getTisphyStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISPHY_STN_CNT +"=";
            colVals += (aVar.getTisphyStnCnt() == INTNULL2 ?
                "null" : aVar.getTisphyStnCnt(""));
        } // if aVar.getTisphyStnCnt
        if (aVar.getTispesStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISPES_STN_CNT +"=";
            colVals += (aVar.getTispesStnCnt() == INTNULL2 ?
                "null" : aVar.getTispesStnCnt(""));
        } // if aVar.getTispesStnCnt
        if (aVar.getTispolStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISPOL_STN_CNT +"=";
            colVals += (aVar.getTispolStnCnt() == INTNULL2 ?
                "null" : aVar.getTispolStnCnt(""));
        } // if aVar.getTispolStnCnt
        if (aVar.getTisanimalStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISANIMAL_STN_CNT +"=";
            colVals += (aVar.getTisanimalStnCnt() == INTNULL2 ?
                "null" : aVar.getTisanimalStnCnt(""));
        } // if aVar.getTisanimalStnCnt
        if (aVar.getWatcurrentsStnCnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATCURRENTS_STN_CNT +"=";
            colVals += (aVar.getWatcurrentsStnCnt() == INTNULL2 ?
                "null" : aVar.getWatcurrentsStnCnt(""));
        } // if aVar.getWatcurrentsStnCnt
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SURVEY_ID;
        if (getStationCnt() != INTNULL) {
            columns = columns + "," + STATION_CNT;
        } // if getStationCnt
        if (getWatphyCnt() != INTNULL) {
            columns = columns + "," + WATPHY_CNT;
        } // if getWatphyCnt
        if (getWatnutCnt() != INTNULL) {
            columns = columns + "," + WATNUT_CNT;
        } // if getWatnutCnt
        if (getWatpol1Cnt() != INTNULL) {
            columns = columns + "," + WATPOL1_CNT;
        } // if getWatpol1Cnt
        if (getWatpol2Cnt() != INTNULL) {
            columns = columns + "," + WATPOL2_CNT;
        } // if getWatpol2Cnt
        if (getWatchem1Cnt() != INTNULL) {
            columns = columns + "," + WATCHEM1_CNT;
        } // if getWatchem1Cnt
        if (getWatchem2Cnt() != INTNULL) {
            columns = columns + "," + WATCHEM2_CNT;
        } // if getWatchem2Cnt
        if (getWatchlCnt() != INTNULL) {
            columns = columns + "," + WATCHL_CNT;
        } // if getWatchlCnt
        if (getSedphyCnt() != INTNULL) {
            columns = columns + "," + SEDPHY_CNT;
        } // if getSedphyCnt
        if (getSedpesCnt() != INTNULL) {
            columns = columns + "," + SEDPES_CNT;
        } // if getSedpesCnt
        if (getSedpol1Cnt() != INTNULL) {
            columns = columns + "," + SEDPOL1_CNT;
        } // if getSedpol1Cnt
        if (getSedpol2Cnt() != INTNULL) {
            columns = columns + "," + SEDPOL2_CNT;
        } // if getSedpol2Cnt
        if (getSedchem1Cnt() != INTNULL) {
            columns = columns + "," + SEDCHEM1_CNT;
        } // if getSedchem1Cnt
        if (getSedchem2Cnt() != INTNULL) {
            columns = columns + "," + SEDCHEM2_CNT;
        } // if getSedchem2Cnt
        if (getSedtaxCnt() != INTNULL) {
            columns = columns + "," + SEDTAX_CNT;
        } // if getSedtaxCnt
        if (getPlaphyCnt() != INTNULL) {
            columns = columns + "," + PLAPHY_CNT;
        } // if getPlaphyCnt
        if (getPlapesCnt() != INTNULL) {
            columns = columns + "," + PLAPES_CNT;
        } // if getPlapesCnt
        if (getPlapol1Cnt() != INTNULL) {
            columns = columns + "," + PLAPOL1_CNT;
        } // if getPlapol1Cnt
        if (getPlapol2Cnt() != INTNULL) {
            columns = columns + "," + PLAPOL2_CNT;
        } // if getPlapol2Cnt
        if (getPlataxCnt() != INTNULL) {
            columns = columns + "," + PLATAX_CNT;
        } // if getPlataxCnt
        if (getPlachlCnt() != INTNULL) {
            columns = columns + "," + PLACHL_CNT;
        } // if getPlachlCnt
        if (getTisphyCnt() != INTNULL) {
            columns = columns + "," + TISPHY_CNT;
        } // if getTisphyCnt
        if (getTispesCnt() != INTNULL) {
            columns = columns + "," + TISPES_CNT;
        } // if getTispesCnt
        if (getTispol1Cnt() != INTNULL) {
            columns = columns + "," + TISPOL1_CNT;
        } // if getTispol1Cnt
        if (getTispol2Cnt() != INTNULL) {
            columns = columns + "," + TISPOL2_CNT;
        } // if getTispol2Cnt
        if (getTisanimalCnt() != INTNULL) {
            columns = columns + "," + TISANIMAL_CNT;
        } // if getTisanimalCnt
        if (getWeatherCnt() != INTNULL) {
            columns = columns + "," + WEATHER_CNT;
        } // if getWeatherCnt
        if (getWatcurrentsCnt() != INTNULL) {
            columns = columns + "," + WATCURRENTS_CNT;
        } // if getWatcurrentsCnt
        if (getWatosdCnt() != INTNULL) {
            columns = columns + "," + WATOSD_CNT;
        } // if getWatosdCnt
        if (getWatctdCnt() != INTNULL) {
            columns = columns + "," + WATCTD_CNT;
        } // if getWatctdCnt
        if (getWatxbtCnt() != INTNULL) {
            columns = columns + "," + WATXBT_CNT;
        } // if getWatxbtCnt
        if (getWatmbtCnt() != INTNULL) {
            columns = columns + "," + WATMBT_CNT;
        } // if getWatmbtCnt
        if (getWatpflCnt() != INTNULL) {
            columns = columns + "," + WATPFL_CNT;
        } // if getWatpflCnt
        if (getWatphyStnCnt() != INTNULL) {
            columns = columns + "," + WATPHY_STN_CNT;
        } // if getWatphyStnCnt
        if (getWatosdStnCnt() != INTNULL) {
            columns = columns + "," + WATOSD_STN_CNT;
        } // if getWatosdStnCnt
        if (getWatctdStnCnt() != INTNULL) {
            columns = columns + "," + WATCTD_STN_CNT;
        } // if getWatctdStnCnt
        if (getWatxbtStnCnt() != INTNULL) {
            columns = columns + "," + WATXBT_STN_CNT;
        } // if getWatxbtStnCnt
        if (getWatmbtStnCnt() != INTNULL) {
            columns = columns + "," + WATMBT_STN_CNT;
        } // if getWatmbtStnCnt
        if (getWatpflStnCnt() != INTNULL) {
            columns = columns + "," + WATPFL_STN_CNT;
        } // if getWatpflStnCnt
        if (getWatnutStnCnt() != INTNULL) {
            columns = columns + "," + WATNUT_STN_CNT;
        } // if getWatnutStnCnt
        if (getWatpolStnCnt() != INTNULL) {
            columns = columns + "," + WATPOL_STN_CNT;
        } // if getWatpolStnCnt
        if (getWatchemStnCnt() != INTNULL) {
            columns = columns + "," + WATCHEM_STN_CNT;
        } // if getWatchemStnCnt
        if (getWatchlStnCnt() != INTNULL) {
            columns = columns + "," + WATCHL_STN_CNT;
        } // if getWatchlStnCnt
        if (getSedphyStnCnt() != INTNULL) {
            columns = columns + "," + SEDPHY_STN_CNT;
        } // if getSedphyStnCnt
        if (getSedpesStnCnt() != INTNULL) {
            columns = columns + "," + SEDPES_STN_CNT;
        } // if getSedpesStnCnt
        if (getSedpolStnCnt() != INTNULL) {
            columns = columns + "," + SEDPOL_STN_CNT;
        } // if getSedpolStnCnt
        if (getSedchemStnCnt() != INTNULL) {
            columns = columns + "," + SEDCHEM_STN_CNT;
        } // if getSedchemStnCnt
        if (getSedtaxStnCnt() != INTNULL) {
            columns = columns + "," + SEDTAX_STN_CNT;
        } // if getSedtaxStnCnt
        if (getPlaphyStnCnt() != INTNULL) {
            columns = columns + "," + PLAPHY_STN_CNT;
        } // if getPlaphyStnCnt
        if (getPlapesStnCnt() != INTNULL) {
            columns = columns + "," + PLAPES_STN_CNT;
        } // if getPlapesStnCnt
        if (getPlapolStnCnt() != INTNULL) {
            columns = columns + "," + PLAPOL_STN_CNT;
        } // if getPlapolStnCnt
        if (getPlataxStnCnt() != INTNULL) {
            columns = columns + "," + PLATAX_STN_CNT;
        } // if getPlataxStnCnt
        if (getPlachlStnCnt() != INTNULL) {
            columns = columns + "," + PLACHL_STN_CNT;
        } // if getPlachlStnCnt
        if (getTisphyStnCnt() != INTNULL) {
            columns = columns + "," + TISPHY_STN_CNT;
        } // if getTisphyStnCnt
        if (getTispesStnCnt() != INTNULL) {
            columns = columns + "," + TISPES_STN_CNT;
        } // if getTispesStnCnt
        if (getTispolStnCnt() != INTNULL) {
            columns = columns + "," + TISPOL_STN_CNT;
        } // if getTispolStnCnt
        if (getTisanimalStnCnt() != INTNULL) {
            columns = columns + "," + TISANIMAL_STN_CNT;
        } // if getTisanimalStnCnt
        if (getWatcurrentsStnCnt() != INTNULL) {
            columns = columns + "," + WATCURRENTS_STN_CNT;
        } // if getWatcurrentsStnCnt
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getSurveyId() + "'";
        if (getStationCnt() != INTNULL) {
            values  = values  + "," + getStationCnt("");
        } // if getStationCnt
        if (getWatphyCnt() != INTNULL) {
            values  = values  + "," + getWatphyCnt("");
        } // if getWatphyCnt
        if (getWatnutCnt() != INTNULL) {
            values  = values  + "," + getWatnutCnt("");
        } // if getWatnutCnt
        if (getWatpol1Cnt() != INTNULL) {
            values  = values  + "," + getWatpol1Cnt("");
        } // if getWatpol1Cnt
        if (getWatpol2Cnt() != INTNULL) {
            values  = values  + "," + getWatpol2Cnt("");
        } // if getWatpol2Cnt
        if (getWatchem1Cnt() != INTNULL) {
            values  = values  + "," + getWatchem1Cnt("");
        } // if getWatchem1Cnt
        if (getWatchem2Cnt() != INTNULL) {
            values  = values  + "," + getWatchem2Cnt("");
        } // if getWatchem2Cnt
        if (getWatchlCnt() != INTNULL) {
            values  = values  + "," + getWatchlCnt("");
        } // if getWatchlCnt
        if (getSedphyCnt() != INTNULL) {
            values  = values  + "," + getSedphyCnt("");
        } // if getSedphyCnt
        if (getSedpesCnt() != INTNULL) {
            values  = values  + "," + getSedpesCnt("");
        } // if getSedpesCnt
        if (getSedpol1Cnt() != INTNULL) {
            values  = values  + "," + getSedpol1Cnt("");
        } // if getSedpol1Cnt
        if (getSedpol2Cnt() != INTNULL) {
            values  = values  + "," + getSedpol2Cnt("");
        } // if getSedpol2Cnt
        if (getSedchem1Cnt() != INTNULL) {
            values  = values  + "," + getSedchem1Cnt("");
        } // if getSedchem1Cnt
        if (getSedchem2Cnt() != INTNULL) {
            values  = values  + "," + getSedchem2Cnt("");
        } // if getSedchem2Cnt
        if (getSedtaxCnt() != INTNULL) {
            values  = values  + "," + getSedtaxCnt("");
        } // if getSedtaxCnt
        if (getPlaphyCnt() != INTNULL) {
            values  = values  + "," + getPlaphyCnt("");
        } // if getPlaphyCnt
        if (getPlapesCnt() != INTNULL) {
            values  = values  + "," + getPlapesCnt("");
        } // if getPlapesCnt
        if (getPlapol1Cnt() != INTNULL) {
            values  = values  + "," + getPlapol1Cnt("");
        } // if getPlapol1Cnt
        if (getPlapol2Cnt() != INTNULL) {
            values  = values  + "," + getPlapol2Cnt("");
        } // if getPlapol2Cnt
        if (getPlataxCnt() != INTNULL) {
            values  = values  + "," + getPlataxCnt("");
        } // if getPlataxCnt
        if (getPlachlCnt() != INTNULL) {
            values  = values  + "," + getPlachlCnt("");
        } // if getPlachlCnt
        if (getTisphyCnt() != INTNULL) {
            values  = values  + "," + getTisphyCnt("");
        } // if getTisphyCnt
        if (getTispesCnt() != INTNULL) {
            values  = values  + "," + getTispesCnt("");
        } // if getTispesCnt
        if (getTispol1Cnt() != INTNULL) {
            values  = values  + "," + getTispol1Cnt("");
        } // if getTispol1Cnt
        if (getTispol2Cnt() != INTNULL) {
            values  = values  + "," + getTispol2Cnt("");
        } // if getTispol2Cnt
        if (getTisanimalCnt() != INTNULL) {
            values  = values  + "," + getTisanimalCnt("");
        } // if getTisanimalCnt
        if (getWeatherCnt() != INTNULL) {
            values  = values  + "," + getWeatherCnt("");
        } // if getWeatherCnt
        if (getWatcurrentsCnt() != INTNULL) {
            values  = values  + "," + getWatcurrentsCnt("");
        } // if getWatcurrentsCnt
        if (getWatosdCnt() != INTNULL) {
            values  = values  + "," + getWatosdCnt("");
        } // if getWatosdCnt
        if (getWatctdCnt() != INTNULL) {
            values  = values  + "," + getWatctdCnt("");
        } // if getWatctdCnt
        if (getWatxbtCnt() != INTNULL) {
            values  = values  + "," + getWatxbtCnt("");
        } // if getWatxbtCnt
        if (getWatmbtCnt() != INTNULL) {
            values  = values  + "," + getWatmbtCnt("");
        } // if getWatmbtCnt
        if (getWatpflCnt() != INTNULL) {
            values  = values  + "," + getWatpflCnt("");
        } // if getWatpflCnt
        if (getWatphyStnCnt() != INTNULL) {
            values  = values  + "," + getWatphyStnCnt("");
        } // if getWatphyStnCnt
        if (getWatosdStnCnt() != INTNULL) {
            values  = values  + "," + getWatosdStnCnt("");
        } // if getWatosdStnCnt
        if (getWatctdStnCnt() != INTNULL) {
            values  = values  + "," + getWatctdStnCnt("");
        } // if getWatctdStnCnt
        if (getWatxbtStnCnt() != INTNULL) {
            values  = values  + "," + getWatxbtStnCnt("");
        } // if getWatxbtStnCnt
        if (getWatmbtStnCnt() != INTNULL) {
            values  = values  + "," + getWatmbtStnCnt("");
        } // if getWatmbtStnCnt
        if (getWatpflStnCnt() != INTNULL) {
            values  = values  + "," + getWatpflStnCnt("");
        } // if getWatpflStnCnt
        if (getWatnutStnCnt() != INTNULL) {
            values  = values  + "," + getWatnutStnCnt("");
        } // if getWatnutStnCnt
        if (getWatpolStnCnt() != INTNULL) {
            values  = values  + "," + getWatpolStnCnt("");
        } // if getWatpolStnCnt
        if (getWatchemStnCnt() != INTNULL) {
            values  = values  + "," + getWatchemStnCnt("");
        } // if getWatchemStnCnt
        if (getWatchlStnCnt() != INTNULL) {
            values  = values  + "," + getWatchlStnCnt("");
        } // if getWatchlStnCnt
        if (getSedphyStnCnt() != INTNULL) {
            values  = values  + "," + getSedphyStnCnt("");
        } // if getSedphyStnCnt
        if (getSedpesStnCnt() != INTNULL) {
            values  = values  + "," + getSedpesStnCnt("");
        } // if getSedpesStnCnt
        if (getSedpolStnCnt() != INTNULL) {
            values  = values  + "," + getSedpolStnCnt("");
        } // if getSedpolStnCnt
        if (getSedchemStnCnt() != INTNULL) {
            values  = values  + "," + getSedchemStnCnt("");
        } // if getSedchemStnCnt
        if (getSedtaxStnCnt() != INTNULL) {
            values  = values  + "," + getSedtaxStnCnt("");
        } // if getSedtaxStnCnt
        if (getPlaphyStnCnt() != INTNULL) {
            values  = values  + "," + getPlaphyStnCnt("");
        } // if getPlaphyStnCnt
        if (getPlapesStnCnt() != INTNULL) {
            values  = values  + "," + getPlapesStnCnt("");
        } // if getPlapesStnCnt
        if (getPlapolStnCnt() != INTNULL) {
            values  = values  + "," + getPlapolStnCnt("");
        } // if getPlapolStnCnt
        if (getPlataxStnCnt() != INTNULL) {
            values  = values  + "," + getPlataxStnCnt("");
        } // if getPlataxStnCnt
        if (getPlachlStnCnt() != INTNULL) {
            values  = values  + "," + getPlachlStnCnt("");
        } // if getPlachlStnCnt
        if (getTisphyStnCnt() != INTNULL) {
            values  = values  + "," + getTisphyStnCnt("");
        } // if getTisphyStnCnt
        if (getTispesStnCnt() != INTNULL) {
            values  = values  + "," + getTispesStnCnt("");
        } // if getTispesStnCnt
        if (getTispolStnCnt() != INTNULL) {
            values  = values  + "," + getTispolStnCnt("");
        } // if getTispolStnCnt
        if (getTisanimalStnCnt() != INTNULL) {
            values  = values  + "," + getTisanimalStnCnt("");
        } // if getTisanimalStnCnt
        if (getWatcurrentsStnCnt() != INTNULL) {
            values  = values  + "," + getWatcurrentsStnCnt("");
        } // if getWatcurrentsStnCnt
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getSurveyId("")          + "|" +
            getStationCnt("")        + "|" +
            getWatphyCnt("")         + "|" +
            getWatnutCnt("")         + "|" +
            getWatpol1Cnt("")        + "|" +
            getWatpol2Cnt("")        + "|" +
            getWatchem1Cnt("")       + "|" +
            getWatchem2Cnt("")       + "|" +
            getWatchlCnt("")         + "|" +
            getSedphyCnt("")         + "|" +
            getSedpesCnt("")         + "|" +
            getSedpol1Cnt("")        + "|" +
            getSedpol2Cnt("")        + "|" +
            getSedchem1Cnt("")       + "|" +
            getSedchem2Cnt("")       + "|" +
            getSedtaxCnt("")         + "|" +
            getPlaphyCnt("")         + "|" +
            getPlapesCnt("")         + "|" +
            getPlapol1Cnt("")        + "|" +
            getPlapol2Cnt("")        + "|" +
            getPlataxCnt("")         + "|" +
            getPlachlCnt("")         + "|" +
            getTisphyCnt("")         + "|" +
            getTispesCnt("")         + "|" +
            getTispol1Cnt("")        + "|" +
            getTispol2Cnt("")        + "|" +
            getTisanimalCnt("")      + "|" +
            getWeatherCnt("")        + "|" +
            getWatcurrentsCnt("")    + "|" +
            getWatosdCnt("")         + "|" +
            getWatctdCnt("")         + "|" +
            getWatxbtCnt("")         + "|" +
            getWatmbtCnt("")         + "|" +
            getWatpflCnt("")         + "|" +
            getWatphyStnCnt("")      + "|" +
            getWatosdStnCnt("")      + "|" +
            getWatctdStnCnt("")      + "|" +
            getWatxbtStnCnt("")      + "|" +
            getWatmbtStnCnt("")      + "|" +
            getWatpflStnCnt("")      + "|" +
            getWatnutStnCnt("")      + "|" +
            getWatpolStnCnt("")      + "|" +
            getWatchemStnCnt("")     + "|" +
            getWatchlStnCnt("")      + "|" +
            getSedphyStnCnt("")      + "|" +
            getSedpesStnCnt("")      + "|" +
            getSedpolStnCnt("")      + "|" +
            getSedchemStnCnt("")     + "|" +
            getSedtaxStnCnt("")      + "|" +
            getPlaphyStnCnt("")      + "|" +
            getPlapesStnCnt("")      + "|" +
            getPlapolStnCnt("")      + "|" +
            getPlataxStnCnt("")      + "|" +
            getPlachlStnCnt("")      + "|" +
            getTisphyStnCnt("")      + "|" +
            getTispesStnCnt("")      + "|" +
            getTispolStnCnt("")      + "|" +
            getTisanimalStnCnt("")   + "|" +
            getWatcurrentsStnCnt("") + "|";
    } // method toString

} // class MrnInvStats
