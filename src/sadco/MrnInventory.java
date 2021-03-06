package sadco;

import java.sql.Timestamp;
import java.util.Vector;

/**
 * This class manages the INVENTORY table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 20100528 - SIT Group
 * @version
 * 20100528 - GenTableClassB class - create class<br>
 */
public class MrnInventory extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "INVENTORY";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The dataCentre field name */
    public static final String DATA_CENTRE = "DATA_CENTRE";
    /** The projectName field name */
    public static final String PROJECT_NAME = "PROJECT_NAME";
    /** The cruiseName field name */
    public static final String CRUISE_NAME = "CRUISE_NAME";
    /** The nationalPgm field name */
    public static final String NATIONAL_PGM = "NATIONAL_PGM";
    /** The exchangeRestrict field name */
    public static final String EXCHANGE_RESTRICT = "EXCHANGE_RESTRICT";
    /** The coopPgm field name */
    public static final String COOP_PGM = "COOP_PGM";
    /** The coordinatedInt field name */
    public static final String COORDINATED_INT = "COORDINATED_INT";
    /** The planamCode field name */
    public static final String PLANAM_CODE = "PLANAM_CODE";
    /** The portStart field name */
    public static final String PORT_START = "PORT_START";
    /** The portEnd field name */
    public static final String PORT_END = "PORT_END";
    /** The countryCode field name */
    public static final String COUNTRY_CODE = "COUNTRY_CODE";
    /** The institCode field name */
    public static final String INSTIT_CODE = "INSTIT_CODE";
    /** The coordCode field name */
    public static final String COORD_CODE = "COORD_CODE";
    /** The sciCode1 field name */
    public static final String SCI_CODE_1 = "SCI_CODE_1";
    /** The sciCode2 field name */
    public static final String SCI_CODE_2 = "SCI_CODE_2";
    /** The dateStart field name */
    public static final String DATE_START = "DATE_START";
    /** The dateEnd field name */
    public static final String DATE_END = "DATE_END";
    /** The latNorth field name */
    public static final String LAT_NORTH = "LAT_NORTH";
    /** The latSouth field name */
    public static final String LAT_SOUTH = "LAT_SOUTH";
    /** The longWest field name */
    public static final String LONG_WEST = "LONG_WEST";
    /** The longEast field name */
    public static final String LONG_EAST = "LONG_EAST";
    /** The areaname field name */
    public static final String AREANAME = "AREANAME";
    /** The domain field name */
    public static final String DOMAIN = "DOMAIN";
    /** The trackChart field name */
    public static final String TRACK_CHART = "TRACK_CHART";
    /** The targetCountryCode field name */
    public static final String TARGET_COUNTRY_CODE = "TARGET_COUNTRY_CODE";
    /** The stnidPrefix field name */
    public static final String STNID_PREFIX = "STNID_PREFIX";
    /** The gmtDiff field name */
    public static final String GMT_DIFF = "GMT_DIFF";
    /** The gmtFreeze field name */
    public static final String GMT_FREEZE = "GMT_FREEZE";
    /** The projectionCode field name */
    public static final String PROJECTION_CODE = "PROJECTION_CODE";
    /** The spheroidCode field name */
    public static final String SPHEROID_CODE = "SPHEROID_CODE";
    /** The datumCode field name */
    public static final String DATUM_CODE = "DATUM_CODE";
    /** The dataRecorded field name */
    public static final String DATA_RECORDED = "DATA_RECORDED";
    /** The surveyTypeCode field name */
    public static final String SURVEY_TYPE_CODE = "SURVEY_TYPE_CODE";
    /** The dataAvailable field name */
    public static final String DATA_AVAILABLE = "DATA_AVAILABLE";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    surveyId;
    private String    dataCentre;
    private String    projectName;
    private String    cruiseName;
    private String    nationalPgm;
    private String    exchangeRestrict;
    private String    coopPgm;
    private String    coordinatedInt;
    private int       planamCode;
    private String    portStart;
    private String    portEnd;
    private int       countryCode;
    private int       institCode;
    private int       coordCode;
    private int       sciCode1;
    private int       sciCode2;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private float     latNorth;
    private float     latSouth;
    private float     longWest;
    private float     longEast;
    private String    areaname;
    private String    domain;
    private String    trackChart;
    private int       targetCountryCode;
    private String    stnidPrefix;
    private int       gmtDiff;
    private String    gmtFreeze;
    private int       projectionCode;
    private int       spheroidCode;
    private int       datumCode;
    private String    dataRecorded;
    private int       surveyTypeCode;
    private String    dataAvailable;

    /** The error variables  */
    private int surveyIdError          = ERROR_NORMAL;
    private int dataCentreError        = ERROR_NORMAL;
    private int projectNameError       = ERROR_NORMAL;
    private int cruiseNameError        = ERROR_NORMAL;
    private int nationalPgmError       = ERROR_NORMAL;
    private int exchangeRestrictError  = ERROR_NORMAL;
    private int coopPgmError           = ERROR_NORMAL;
    private int coordinatedIntError    = ERROR_NORMAL;
    private int planamCodeError        = ERROR_NORMAL;
    private int portStartError         = ERROR_NORMAL;
    private int portEndError           = ERROR_NORMAL;
    private int countryCodeError       = ERROR_NORMAL;
    private int institCodeError        = ERROR_NORMAL;
    private int coordCodeError         = ERROR_NORMAL;
    private int sciCode1Error          = ERROR_NORMAL;
    private int sciCode2Error          = ERROR_NORMAL;
    private int dateStartError         = ERROR_NORMAL;
    private int dateEndError           = ERROR_NORMAL;
    private int latNorthError          = ERROR_NORMAL;
    private int latSouthError          = ERROR_NORMAL;
    private int longWestError          = ERROR_NORMAL;
    private int longEastError          = ERROR_NORMAL;
    private int areanameError          = ERROR_NORMAL;
    private int domainError            = ERROR_NORMAL;
    private int trackChartError        = ERROR_NORMAL;
    private int targetCountryCodeError = ERROR_NORMAL;
    private int stnidPrefixError       = ERROR_NORMAL;
    private int gmtDiffError           = ERROR_NORMAL;
    private int gmtFreezeError         = ERROR_NORMAL;
    private int projectionCodeError    = ERROR_NORMAL;
    private int spheroidCodeError      = ERROR_NORMAL;
    private int datumCodeError         = ERROR_NORMAL;
    private int dataRecordedError      = ERROR_NORMAL;
    private int surveyTypeCodeError    = ERROR_NORMAL;
    private int dataAvailableError     = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String surveyIdErrorMessage          = "";
    private String dataCentreErrorMessage        = "";
    private String projectNameErrorMessage       = "";
    private String cruiseNameErrorMessage        = "";
    private String nationalPgmErrorMessage       = "";
    private String exchangeRestrictErrorMessage  = "";
    private String coopPgmErrorMessage           = "";
    private String coordinatedIntErrorMessage    = "";
    private String planamCodeErrorMessage        = "";
    private String portStartErrorMessage         = "";
    private String portEndErrorMessage           = "";
    private String countryCodeErrorMessage       = "";
    private String institCodeErrorMessage        = "";
    private String coordCodeErrorMessage         = "";
    private String sciCode1ErrorMessage          = "";
    private String sciCode2ErrorMessage          = "";
    private String dateStartErrorMessage         = "";
    private String dateEndErrorMessage           = "";
    private String latNorthErrorMessage          = "";
    private String latSouthErrorMessage          = "";
    private String longWestErrorMessage          = "";
    private String longEastErrorMessage          = "";
    private String areanameErrorMessage          = "";
    private String domainErrorMessage            = "";
    private String trackChartErrorMessage        = "";
    private String targetCountryCodeErrorMessage = "";
    private String stnidPrefixErrorMessage       = "";
    private String gmtDiffErrorMessage           = "";
    private String gmtFreezeErrorMessage         = "";
    private String projectionCodeErrorMessage    = "";
    private String spheroidCodeErrorMessage      = "";
    private String datumCodeErrorMessage         = "";
    private String dataRecordedErrorMessage      = "";
    private String surveyTypeCodeErrorMessage    = "";
    private String dataAvailableErrorMessage     = "";

    /** The min-max constants for all numerics */
    public static final int       PLANAM_CODE_MN = INTMIN;
    public static final int       PLANAM_CODE_MX = INTMAX;
    public static final int       COUNTRY_CODE_MN = INTMIN;
    public static final int       COUNTRY_CODE_MX = INTMAX;
    public static final int       INSTIT_CODE_MN = INTMIN;
    public static final int       INSTIT_CODE_MX = INTMAX;
    public static final int       COORD_CODE_MN = INTMIN;
    public static final int       COORD_CODE_MX = INTMAX;
    public static final int       SCI_CODE_1_MN = INTMIN;
    public static final int       SCI_CODE_1_MX = INTMAX;
    public static final int       SCI_CODE_2_MN = INTMIN;
    public static final int       SCI_CODE_2_MX = INTMAX;
    public static final Timestamp DATE_START_MN = DATEMIN;
    public static final Timestamp DATE_START_MX = DATEMAX;
    public static final Timestamp DATE_END_MN = DATEMIN;
    public static final Timestamp DATE_END_MX = DATEMAX;
    public static final float     LAT_NORTH_MN = FLOATMIN;
    public static final float     LAT_NORTH_MX = FLOATMAX;
    public static final float     LAT_SOUTH_MN = FLOATMIN;
    public static final float     LAT_SOUTH_MX = FLOATMAX;
    public static final float     LONG_WEST_MN = FLOATMIN;
    public static final float     LONG_WEST_MX = FLOATMAX;
    public static final float     LONG_EAST_MN = FLOATMIN;
    public static final float     LONG_EAST_MX = FLOATMAX;
    public static final int       TARGET_COUNTRY_CODE_MN = INTMIN;
    public static final int       TARGET_COUNTRY_CODE_MX = INTMAX;
    public static final int       GMT_DIFF_MN = INTMIN;
    public static final int       GMT_DIFF_MX = INTMAX;
    public static final int       PROJECTION_CODE_MN = INTMIN;
    public static final int       PROJECTION_CODE_MX = INTMAX;
    public static final int       SPHEROID_CODE_MN = INTMIN;
    public static final int       SPHEROID_CODE_MX = INTMAX;
    public static final int       DATUM_CODE_MN = INTMIN;
    public static final int       DATUM_CODE_MX = INTMAX;
    public static final int       SURVEY_TYPE_CODE_MN = INTMIN;
    public static final int       SURVEY_TYPE_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception planamCodeOutOfBoundsException =
        new Exception ("'planamCode' out of bounds: " +
            PLANAM_CODE_MN + " - " + PLANAM_CODE_MX);
    Exception countryCodeOutOfBoundsException =
        new Exception ("'countryCode' out of bounds: " +
            COUNTRY_CODE_MN + " - " + COUNTRY_CODE_MX);
    Exception institCodeOutOfBoundsException =
        new Exception ("'institCode' out of bounds: " +
            INSTIT_CODE_MN + " - " + INSTIT_CODE_MX);
    Exception coordCodeOutOfBoundsException =
        new Exception ("'coordCode' out of bounds: " +
            COORD_CODE_MN + " - " + COORD_CODE_MX);
    Exception sciCode1OutOfBoundsException =
        new Exception ("'sciCode1' out of bounds: " +
            SCI_CODE_1_MN + " - " + SCI_CODE_1_MX);
    Exception sciCode2OutOfBoundsException =
        new Exception ("'sciCode2' out of bounds: " +
            SCI_CODE_2_MN + " - " + SCI_CODE_2_MX);
    Exception dateStartException =
        new Exception ("'dateStart' is null");
    Exception dateStartOutOfBoundsException =
        new Exception ("'dateStart' out of bounds: " +
            DATE_START_MN + " - " + DATE_START_MX);
    Exception dateEndException =
        new Exception ("'dateEnd' is null");
    Exception dateEndOutOfBoundsException =
        new Exception ("'dateEnd' out of bounds: " +
            DATE_END_MN + " - " + DATE_END_MX);
    Exception latNorthOutOfBoundsException =
        new Exception ("'latNorth' out of bounds: " +
            LAT_NORTH_MN + " - " + LAT_NORTH_MX);
    Exception latSouthOutOfBoundsException =
        new Exception ("'latSouth' out of bounds: " +
            LAT_SOUTH_MN + " - " + LAT_SOUTH_MX);
    Exception longWestOutOfBoundsException =
        new Exception ("'longWest' out of bounds: " +
            LONG_WEST_MN + " - " + LONG_WEST_MX);
    Exception longEastOutOfBoundsException =
        new Exception ("'longEast' out of bounds: " +
            LONG_EAST_MN + " - " + LONG_EAST_MX);
    Exception targetCountryCodeOutOfBoundsException =
        new Exception ("'targetCountryCode' out of bounds: " +
            TARGET_COUNTRY_CODE_MN + " - " + TARGET_COUNTRY_CODE_MX);
    Exception gmtDiffOutOfBoundsException =
        new Exception ("'gmtDiff' out of bounds: " +
            GMT_DIFF_MN + " - " + GMT_DIFF_MX);
    Exception projectionCodeOutOfBoundsException =
        new Exception ("'projectionCode' out of bounds: " +
            PROJECTION_CODE_MN + " - " + PROJECTION_CODE_MX);
    Exception spheroidCodeOutOfBoundsException =
        new Exception ("'spheroidCode' out of bounds: " +
            SPHEROID_CODE_MN + " - " + SPHEROID_CODE_MX);
    Exception datumCodeOutOfBoundsException =
        new Exception ("'datumCode' out of bounds: " +
            DATUM_CODE_MN + " - " + DATUM_CODE_MX);
    Exception surveyTypeCodeOutOfBoundsException =
        new Exception ("'surveyTypeCode' out of bounds: " +
            SURVEY_TYPE_CODE_MN + " - " + SURVEY_TYPE_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnInventory() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnInventory constructor 1"); // debug
    } // MrnInventory constructor

    /**
     * Instantiate a MrnInventory object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     */
    public MrnInventory(
            String surveyId) {
        this();
        setSurveyId          (surveyId         );
        if (dbg) System.out.println ("<br>in MrnInventory constructor 2"); // debug
    } // MrnInventory constructor

    /**
     * Instantiate a MrnInventory object and initialize the instance variables.
     * @param surveyId           The surveyId          (String)
     * @param dataCentre         The dataCentre        (String)
     * @param projectName        The projectName       (String)
     * @param cruiseName         The cruiseName        (String)
     * @param nationalPgm        The nationalPgm       (String)
     * @param exchangeRestrict   The exchangeRestrict  (String)
     * @param coopPgm            The coopPgm           (String)
     * @param coordinatedInt     The coordinatedInt    (String)
     * @param planamCode         The planamCode        (int)
     * @param portStart          The portStart         (String)
     * @param portEnd            The portEnd           (String)
     * @param countryCode        The countryCode       (int)
     * @param institCode         The institCode        (int)
     * @param coordCode          The coordCode         (int)
     * @param sciCode1           The sciCode1          (int)
     * @param sciCode2           The sciCode2          (int)
     * @param dateStart          The dateStart         (java.util.Date)
     * @param dateEnd            The dateEnd           (java.util.Date)
     * @param latNorth           The latNorth          (float)
     * @param latSouth           The latSouth          (float)
     * @param longWest           The longWest          (float)
     * @param longEast           The longEast          (float)
     * @param areaname           The areaname          (String)
     * @param domain             The domain            (String)
     * @param trackChart         The trackChart        (String)
     * @param targetCountryCode  The targetCountryCode (int)
     * @param stnidPrefix        The stnidPrefix       (String)
     * @param gmtDiff            The gmtDiff           (int)
     * @param gmtFreeze          The gmtFreeze         (String)
     * @param projectionCode     The projectionCode    (int)
     * @param spheroidCode       The spheroidCode      (int)
     * @param datumCode          The datumCode         (int)
     * @param dataRecorded       The dataRecorded      (String)
     * @param surveyTypeCode     The surveyTypeCode    (int)
     * @param dataAvailable      The dataAvailable     (String)
     * @return A MrnInventory object
     */
    public MrnInventory(
            String         surveyId,
            String         dataCentre,
            String         projectName,
            String         cruiseName,
            String         nationalPgm,
            String         exchangeRestrict,
            String         coopPgm,
            String         coordinatedInt,
            int            planamCode,
            String         portStart,
            String         portEnd,
            int            countryCode,
            int            institCode,
            int            coordCode,
            int            sciCode1,
            int            sciCode2,
            java.util.Date dateStart,
            java.util.Date dateEnd,
            float          latNorth,
            float          latSouth,
            float          longWest,
            float          longEast,
            String         areaname,
            String         domain,
            String         trackChart,
            int            targetCountryCode,
            String         stnidPrefix,
            int            gmtDiff,
            String         gmtFreeze,
            int            projectionCode,
            int            spheroidCode,
            int            datumCode,
            String         dataRecorded,
            int            surveyTypeCode,
            String         dataAvailable) {
        this();
        setSurveyId          (surveyId         );
        setDataCentre        (dataCentre       );
        setProjectName       (projectName      );
        setCruiseName        (cruiseName       );
        setNationalPgm       (nationalPgm      );
        setExchangeRestrict  (exchangeRestrict );
        setCoopPgm           (coopPgm          );
        setCoordinatedInt    (coordinatedInt   );
        setPlanamCode        (planamCode       );
        setPortStart         (portStart        );
        setPortEnd           (portEnd          );
        setCountryCode       (countryCode      );
        setInstitCode        (institCode       );
        setCoordCode         (coordCode        );
        setSciCode1          (sciCode1         );
        setSciCode2          (sciCode2         );
        setDateStart         (dateStart        );
        setDateEnd           (dateEnd          );
        setLatNorth          (latNorth         );
        setLatSouth          (latSouth         );
        setLongWest          (longWest         );
        setLongEast          (longEast         );
        setAreaname          (areaname         );
        setDomain            (domain           );
        setTrackChart        (trackChart       );
        setTargetCountryCode (targetCountryCode);
        setStnidPrefix       (stnidPrefix      );
        setGmtDiff           (gmtDiff          );
        setGmtFreeze         (gmtFreeze        );
        setProjectionCode    (projectionCode   );
        setSpheroidCode      (spheroidCode     );
        setDatumCode         (datumCode        );
        setDataRecorded      (dataRecorded     );
        setSurveyTypeCode    (surveyTypeCode   );
        setDataAvailable     (dataAvailable    );
        if (dbg) System.out.println ("<br>in MrnInventory constructor 3"); // debug
    } // MrnInventory constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSurveyId          (CHARNULL );
        setDataCentre        (CHARNULL );
        setProjectName       (CHARNULL );
        setCruiseName        (CHARNULL );
        setNationalPgm       (CHARNULL );
        setExchangeRestrict  (CHARNULL );
        setCoopPgm           (CHARNULL );
        setCoordinatedInt    (CHARNULL );
        setPlanamCode        (INTNULL  );
        setPortStart         (CHARNULL );
        setPortEnd           (CHARNULL );
        setCountryCode       (INTNULL  );
        setInstitCode        (INTNULL  );
        setCoordCode         (INTNULL  );
        setSciCode1          (INTNULL  );
        setSciCode2          (INTNULL  );
        setDateStart         (DATENULL );
        setDateEnd           (DATENULL );
        setLatNorth          (FLOATNULL);
        setLatSouth          (FLOATNULL);
        setLongWest          (FLOATNULL);
        setLongEast          (FLOATNULL);
        setAreaname          (CHARNULL );
        setDomain            (CHARNULL );
        setTrackChart        (CHARNULL );
        setTargetCountryCode (INTNULL  );
        setStnidPrefix       (CHARNULL );
        setGmtDiff           (INTNULL  );
        setGmtFreeze         (CHARNULL );
        setProjectionCode    (INTNULL  );
        setSpheroidCode      (INTNULL  );
        setDatumCode         (INTNULL  );
        setDataRecorded      (CHARNULL );
        setSurveyTypeCode    (INTNULL  );
        setDataAvailable     (CHARNULL );
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
     * Set the 'dataCentre' class variable
     * @param  dataCentre (String)
     */
    public int setDataCentre(String dataCentre) {
        try {
            this.dataCentre = dataCentre;
            if (this.dataCentre != CHARNULL) {
                this.dataCentre = stripCRLF(this.dataCentre.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>dataCentre = " + this.dataCentre);
        } catch (Exception e) {
            setDataCentreError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return dataCentreError;
    } // method setDataCentre

    /**
     * Called when an exception has occured
     * @param  dataCentre (String)
     */
    private void setDataCentreError (String dataCentre, Exception e, int error) {
        this.dataCentre = dataCentre;
        dataCentreErrorMessage = e.toString();
        dataCentreError = error;
    } // method setDataCentreError


    /**
     * Set the 'projectName' class variable
     * @param  projectName (String)
     */
    public int setProjectName(String projectName) {
        try {
            this.projectName = projectName;
            if (this.projectName != CHARNULL) {
                this.projectName = stripCRLF(this.projectName.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>projectName = " + this.projectName);
        } catch (Exception e) {
            setProjectNameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return projectNameError;
    } // method setProjectName

    /**
     * Called when an exception has occured
     * @param  projectName (String)
     */
    private void setProjectNameError (String projectName, Exception e, int error) {
        this.projectName = projectName;
        projectNameErrorMessage = e.toString();
        projectNameError = error;
    } // method setProjectNameError


    /**
     * Set the 'cruiseName' class variable
     * @param  cruiseName (String)
     */
    public int setCruiseName(String cruiseName) {
        try {
            this.cruiseName = cruiseName;
            if (this.cruiseName != CHARNULL) {
                this.cruiseName = stripCRLF(this.cruiseName.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>cruiseName = " + this.cruiseName);
        } catch (Exception e) {
            setCruiseNameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return cruiseNameError;
    } // method setCruiseName

    /**
     * Called when an exception has occured
     * @param  cruiseName (String)
     */
    private void setCruiseNameError (String cruiseName, Exception e, int error) {
        this.cruiseName = cruiseName;
        cruiseNameErrorMessage = e.toString();
        cruiseNameError = error;
    } // method setCruiseNameError


    /**
     * Set the 'nationalPgm' class variable
     * @param  nationalPgm (String)
     */
    public int setNationalPgm(String nationalPgm) {
        try {
            this.nationalPgm = nationalPgm;
            if (this.nationalPgm != CHARNULL) {
                this.nationalPgm = stripCRLF(this.nationalPgm.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>nationalPgm = " + this.nationalPgm);
        } catch (Exception e) {
            setNationalPgmError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return nationalPgmError;
    } // method setNationalPgm

    /**
     * Called when an exception has occured
     * @param  nationalPgm (String)
     */
    private void setNationalPgmError (String nationalPgm, Exception e, int error) {
        this.nationalPgm = nationalPgm;
        nationalPgmErrorMessage = e.toString();
        nationalPgmError = error;
    } // method setNationalPgmError


    /**
     * Set the 'exchangeRestrict' class variable
     * @param  exchangeRestrict (String)
     */
    public int setExchangeRestrict(String exchangeRestrict) {
        try {
            this.exchangeRestrict = exchangeRestrict;
            if (this.exchangeRestrict != CHARNULL) {
                this.exchangeRestrict = stripCRLF(this.exchangeRestrict.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>exchangeRestrict = " + this.exchangeRestrict);
        } catch (Exception e) {
            setExchangeRestrictError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return exchangeRestrictError;
    } // method setExchangeRestrict

    /**
     * Called when an exception has occured
     * @param  exchangeRestrict (String)
     */
    private void setExchangeRestrictError (String exchangeRestrict, Exception e, int error) {
        this.exchangeRestrict = exchangeRestrict;
        exchangeRestrictErrorMessage = e.toString();
        exchangeRestrictError = error;
    } // method setExchangeRestrictError


    /**
     * Set the 'coopPgm' class variable
     * @param  coopPgm (String)
     */
    public int setCoopPgm(String coopPgm) {
        try {
            this.coopPgm = coopPgm;
            if (this.coopPgm != CHARNULL) {
                this.coopPgm = stripCRLF(this.coopPgm.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>coopPgm = " + this.coopPgm);
        } catch (Exception e) {
            setCoopPgmError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return coopPgmError;
    } // method setCoopPgm

    /**
     * Called when an exception has occured
     * @param  coopPgm (String)
     */
    private void setCoopPgmError (String coopPgm, Exception e, int error) {
        this.coopPgm = coopPgm;
        coopPgmErrorMessage = e.toString();
        coopPgmError = error;
    } // method setCoopPgmError


    /**
     * Set the 'coordinatedInt' class variable
     * @param  coordinatedInt (String)
     */
    public int setCoordinatedInt(String coordinatedInt) {
        try {
            this.coordinatedInt = coordinatedInt;
            if (this.coordinatedInt != CHARNULL) {
                this.coordinatedInt = stripCRLF(this.coordinatedInt.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>coordinatedInt = " + this.coordinatedInt);
        } catch (Exception e) {
            setCoordinatedIntError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return coordinatedIntError;
    } // method setCoordinatedInt

    /**
     * Called when an exception has occured
     * @param  coordinatedInt (String)
     */
    private void setCoordinatedIntError (String coordinatedInt, Exception e, int error) {
        this.coordinatedInt = coordinatedInt;
        coordinatedIntErrorMessage = e.toString();
        coordinatedIntError = error;
    } // method setCoordinatedIntError


    /**
     * Set the 'planamCode' class variable
     * @param  planamCode (int)
     */
    public int setPlanamCode(int planamCode) {
        try {
            if ( ((planamCode == INTNULL) ||
                  (planamCode == INTNULL2)) ||
                !((planamCode < PLANAM_CODE_MN) ||
                  (planamCode > PLANAM_CODE_MX)) ) {
                this.planamCode = planamCode;
                planamCodeError = ERROR_NORMAL;
            } else {
                throw planamCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlanamCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return planamCodeError;
    } // method setPlanamCode

    /**
     * Set the 'planamCode' class variable
     * @param  planamCode (Integer)
     */
    public int setPlanamCode(Integer planamCode) {
        try {
            setPlanamCode(planamCode.intValue());
        } catch (Exception e) {
            setPlanamCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return planamCodeError;
    } // method setPlanamCode

    /**
     * Set the 'planamCode' class variable
     * @param  planamCode (Float)
     */
    public int setPlanamCode(Float planamCode) {
        try {
            if (planamCode.floatValue() == FLOATNULL) {
                setPlanamCode(INTNULL);
            } else {
                setPlanamCode(planamCode.intValue());
            } // if (planamCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlanamCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return planamCodeError;
    } // method setPlanamCode

    /**
     * Set the 'planamCode' class variable
     * @param  planamCode (String)
     */
    public int setPlanamCode(String planamCode) {
        try {
            setPlanamCode(new Integer(planamCode).intValue());
        } catch (Exception e) {
            setPlanamCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return planamCodeError;
    } // method setPlanamCode

    /**
     * Called when an exception has occured
     * @param  planamCode (String)
     */
    private void setPlanamCodeError (int planamCode, Exception e, int error) {
        this.planamCode = planamCode;
        planamCodeErrorMessage = e.toString();
        planamCodeError = error;
    } // method setPlanamCodeError


    /**
     * Set the 'portStart' class variable
     * @param  portStart (String)
     */
    public int setPortStart(String portStart) {
        try {
            this.portStart = portStart;
            if (this.portStart != CHARNULL) {
                this.portStart = stripCRLF(this.portStart.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>portStart = " + this.portStart);
        } catch (Exception e) {
            setPortStartError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return portStartError;
    } // method setPortStart

    /**
     * Called when an exception has occured
     * @param  portStart (String)
     */
    private void setPortStartError (String portStart, Exception e, int error) {
        this.portStart = portStart;
        portStartErrorMessage = e.toString();
        portStartError = error;
    } // method setPortStartError


    /**
     * Set the 'portEnd' class variable
     * @param  portEnd (String)
     */
    public int setPortEnd(String portEnd) {
        try {
            this.portEnd = portEnd;
            if (this.portEnd != CHARNULL) {
                this.portEnd = stripCRLF(this.portEnd.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>portEnd = " + this.portEnd);
        } catch (Exception e) {
            setPortEndError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return portEndError;
    } // method setPortEnd

    /**
     * Called when an exception has occured
     * @param  portEnd (String)
     */
    private void setPortEndError (String portEnd, Exception e, int error) {
        this.portEnd = portEnd;
        portEndErrorMessage = e.toString();
        portEndError = error;
    } // method setPortEndError


    /**
     * Set the 'countryCode' class variable
     * @param  countryCode (int)
     */
    public int setCountryCode(int countryCode) {
        try {
            if ( ((countryCode == INTNULL) ||
                  (countryCode == INTNULL2)) ||
                !((countryCode < COUNTRY_CODE_MN) ||
                  (countryCode > COUNTRY_CODE_MX)) ) {
                this.countryCode = countryCode;
                countryCodeError = ERROR_NORMAL;
            } else {
                throw countryCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCountryCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return countryCodeError;
    } // method setCountryCode

    /**
     * Set the 'countryCode' class variable
     * @param  countryCode (Integer)
     */
    public int setCountryCode(Integer countryCode) {
        try {
            setCountryCode(countryCode.intValue());
        } catch (Exception e) {
            setCountryCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return countryCodeError;
    } // method setCountryCode

    /**
     * Set the 'countryCode' class variable
     * @param  countryCode (Float)
     */
    public int setCountryCode(Float countryCode) {
        try {
            if (countryCode.floatValue() == FLOATNULL) {
                setCountryCode(INTNULL);
            } else {
                setCountryCode(countryCode.intValue());
            } // if (countryCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setCountryCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return countryCodeError;
    } // method setCountryCode

    /**
     * Set the 'countryCode' class variable
     * @param  countryCode (String)
     */
    public int setCountryCode(String countryCode) {
        try {
            setCountryCode(new Integer(countryCode).intValue());
        } catch (Exception e) {
            setCountryCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return countryCodeError;
    } // method setCountryCode

    /**
     * Called when an exception has occured
     * @param  countryCode (String)
     */
    private void setCountryCodeError (int countryCode, Exception e, int error) {
        this.countryCode = countryCode;
        countryCodeErrorMessage = e.toString();
        countryCodeError = error;
    } // method setCountryCodeError


    /**
     * Set the 'institCode' class variable
     * @param  institCode (int)
     */
    public int setInstitCode(int institCode) {
        try {
            if ( ((institCode == INTNULL) ||
                  (institCode == INTNULL2)) ||
                !((institCode < INSTIT_CODE_MN) ||
                  (institCode > INSTIT_CODE_MX)) ) {
                this.institCode = institCode;
                institCodeError = ERROR_NORMAL;
            } else {
                throw institCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setInstitCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return institCodeError;
    } // method setInstitCode

    /**
     * Set the 'institCode' class variable
     * @param  institCode (Integer)
     */
    public int setInstitCode(Integer institCode) {
        try {
            setInstitCode(institCode.intValue());
        } catch (Exception e) {
            setInstitCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return institCodeError;
    } // method setInstitCode

    /**
     * Set the 'institCode' class variable
     * @param  institCode (Float)
     */
    public int setInstitCode(Float institCode) {
        try {
            if (institCode.floatValue() == FLOATNULL) {
                setInstitCode(INTNULL);
            } else {
                setInstitCode(institCode.intValue());
            } // if (institCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setInstitCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return institCodeError;
    } // method setInstitCode

    /**
     * Set the 'institCode' class variable
     * @param  institCode (String)
     */
    public int setInstitCode(String institCode) {
        try {
            setInstitCode(new Integer(institCode).intValue());
        } catch (Exception e) {
            setInstitCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return institCodeError;
    } // method setInstitCode

    /**
     * Called when an exception has occured
     * @param  institCode (String)
     */
    private void setInstitCodeError (int institCode, Exception e, int error) {
        this.institCode = institCode;
        institCodeErrorMessage = e.toString();
        institCodeError = error;
    } // method setInstitCodeError


    /**
     * Set the 'coordCode' class variable
     * @param  coordCode (int)
     */
    public int setCoordCode(int coordCode) {
        try {
            if ( ((coordCode == INTNULL) ||
                  (coordCode == INTNULL2)) ||
                !((coordCode < COORD_CODE_MN) ||
                  (coordCode > COORD_CODE_MX)) ) {
                this.coordCode = coordCode;
                coordCodeError = ERROR_NORMAL;
            } else {
                throw coordCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCoordCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return coordCodeError;
    } // method setCoordCode

    /**
     * Set the 'coordCode' class variable
     * @param  coordCode (Integer)
     */
    public int setCoordCode(Integer coordCode) {
        try {
            setCoordCode(coordCode.intValue());
        } catch (Exception e) {
            setCoordCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return coordCodeError;
    } // method setCoordCode

    /**
     * Set the 'coordCode' class variable
     * @param  coordCode (Float)
     */
    public int setCoordCode(Float coordCode) {
        try {
            if (coordCode.floatValue() == FLOATNULL) {
                setCoordCode(INTNULL);
            } else {
                setCoordCode(coordCode.intValue());
            } // if (coordCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setCoordCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return coordCodeError;
    } // method setCoordCode

    /**
     * Set the 'coordCode' class variable
     * @param  coordCode (String)
     */
    public int setCoordCode(String coordCode) {
        try {
            setCoordCode(new Integer(coordCode).intValue());
        } catch (Exception e) {
            setCoordCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return coordCodeError;
    } // method setCoordCode

    /**
     * Called when an exception has occured
     * @param  coordCode (String)
     */
    private void setCoordCodeError (int coordCode, Exception e, int error) {
        this.coordCode = coordCode;
        coordCodeErrorMessage = e.toString();
        coordCodeError = error;
    } // method setCoordCodeError


    /**
     * Set the 'sciCode1' class variable
     * @param  sciCode1 (int)
     */
    public int setSciCode1(int sciCode1) {
        try {
            if ( ((sciCode1 == INTNULL) ||
                  (sciCode1 == INTNULL2)) ||
                !((sciCode1 < SCI_CODE_1_MN) ||
                  (sciCode1 > SCI_CODE_1_MX)) ) {
                this.sciCode1 = sciCode1;
                sciCode1Error = ERROR_NORMAL;
            } else {
                throw sciCode1OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSciCode1Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return sciCode1Error;
    } // method setSciCode1

    /**
     * Set the 'sciCode1' class variable
     * @param  sciCode1 (Integer)
     */
    public int setSciCode1(Integer sciCode1) {
        try {
            setSciCode1(sciCode1.intValue());
        } catch (Exception e) {
            setSciCode1Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sciCode1Error;
    } // method setSciCode1

    /**
     * Set the 'sciCode1' class variable
     * @param  sciCode1 (Float)
     */
    public int setSciCode1(Float sciCode1) {
        try {
            if (sciCode1.floatValue() == FLOATNULL) {
                setSciCode1(INTNULL);
            } else {
                setSciCode1(sciCode1.intValue());
            } // if (sciCode1.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSciCode1Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sciCode1Error;
    } // method setSciCode1

    /**
     * Set the 'sciCode1' class variable
     * @param  sciCode1 (String)
     */
    public int setSciCode1(String sciCode1) {
        try {
            setSciCode1(new Integer(sciCode1).intValue());
        } catch (Exception e) {
            setSciCode1Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sciCode1Error;
    } // method setSciCode1

    /**
     * Called when an exception has occured
     * @param  sciCode1 (String)
     */
    private void setSciCode1Error (int sciCode1, Exception e, int error) {
        this.sciCode1 = sciCode1;
        sciCode1ErrorMessage = e.toString();
        sciCode1Error = error;
    } // method setSciCode1Error


    /**
     * Set the 'sciCode2' class variable
     * @param  sciCode2 (int)
     */
    public int setSciCode2(int sciCode2) {
        try {
            if ( ((sciCode2 == INTNULL) ||
                  (sciCode2 == INTNULL2)) ||
                !((sciCode2 < SCI_CODE_2_MN) ||
                  (sciCode2 > SCI_CODE_2_MX)) ) {
                this.sciCode2 = sciCode2;
                sciCode2Error = ERROR_NORMAL;
            } else {
                throw sciCode2OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSciCode2Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return sciCode2Error;
    } // method setSciCode2

    /**
     * Set the 'sciCode2' class variable
     * @param  sciCode2 (Integer)
     */
    public int setSciCode2(Integer sciCode2) {
        try {
            setSciCode2(sciCode2.intValue());
        } catch (Exception e) {
            setSciCode2Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sciCode2Error;
    } // method setSciCode2

    /**
     * Set the 'sciCode2' class variable
     * @param  sciCode2 (Float)
     */
    public int setSciCode2(Float sciCode2) {
        try {
            if (sciCode2.floatValue() == FLOATNULL) {
                setSciCode2(INTNULL);
            } else {
                setSciCode2(sciCode2.intValue());
            } // if (sciCode2.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSciCode2Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sciCode2Error;
    } // method setSciCode2

    /**
     * Set the 'sciCode2' class variable
     * @param  sciCode2 (String)
     */
    public int setSciCode2(String sciCode2) {
        try {
            setSciCode2(new Integer(sciCode2).intValue());
        } catch (Exception e) {
            setSciCode2Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sciCode2Error;
    } // method setSciCode2

    /**
     * Called when an exception has occured
     * @param  sciCode2 (String)
     */
    private void setSciCode2Error (int sciCode2, Exception e, int error) {
        this.sciCode2 = sciCode2;
        sciCode2ErrorMessage = e.toString();
        sciCode2Error = error;
    } // method setSciCode2Error


    /**
     * Set the 'dateStart' class variable
     * @param  dateStart (Timestamp)
     */
    public int setDateStart(Timestamp dateStart) {
        try {
            if (dateStart == null) { this.dateStart = DATENULL; }
            if ( (dateStart.equals(DATENULL) ||
                  dateStart.equals(DATENULL2)) ||
                !(dateStart.before(DATE_START_MN) ||
                  dateStart.after(DATE_START_MX)) ) {
                this.dateStart = dateStart;
                dateStartError = ERROR_NORMAL;
            } else {
                throw dateStartOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateStartError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateStartError;
    } // method setDateStart

    /**
     * Set the 'dateStart' class variable
     * @param  dateStart (java.util.Date)
     */
    public int setDateStart(java.util.Date dateStart) {
        try {
            setDateStart(new Timestamp(dateStart.getTime()));
        } catch (Exception e) {
            setDateStartError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateStartError;
    } // method setDateStart

    /**
     * Set the 'dateStart' class variable
     * @param  dateStart (String)
     */
    public int setDateStart(String dateStart) {
        try {
            int len = dateStart.length();
            switch (len) {
            // date &/or times
                case 4: dateStart += "-01";
                case 7: dateStart += "-01";
                case 10: dateStart += " 00";
                case 13: dateStart += ":00";
                case 16: dateStart += ":00"; break;
                // times only
                case 5: dateStart = "1800-01-01 " + dateStart + ":00"; break;
                case 8: dateStart = "1800-01-01 " + dateStart; break;
            } // switch
            if (dbg) System.out.println ("dateStart = " + dateStart);
            setDateStart(Timestamp.valueOf(dateStart));
        } catch (Exception e) {
            setDateStartError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateStartError;
    } // method setDateStart

    /**
     * Called when an exception has occured
     * @param  dateStart (String)
     */
    private void setDateStartError (Timestamp dateStart, Exception e, int error) {
        this.dateStart = dateStart;
        dateStartErrorMessage = e.toString();
        dateStartError = error;
    } // method setDateStartError


    /**
     * Set the 'dateEnd' class variable
     * @param  dateEnd (Timestamp)
     */
    public int setDateEnd(Timestamp dateEnd) {
        try {
            if (dateEnd == null) { this.dateEnd = DATENULL; }
            if ( (dateEnd.equals(DATENULL) ||
                  dateEnd.equals(DATENULL2)) ||
                !(dateEnd.before(DATE_END_MN) ||
                  dateEnd.after(DATE_END_MX)) ) {
                this.dateEnd = dateEnd;
                dateEndError = ERROR_NORMAL;
            } else {
                throw dateEndOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateEndError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateEndError;
    } // method setDateEnd

    /**
     * Set the 'dateEnd' class variable
     * @param  dateEnd (java.util.Date)
     */
    public int setDateEnd(java.util.Date dateEnd) {
        try {
            setDateEnd(new Timestamp(dateEnd.getTime()));
        } catch (Exception e) {
            setDateEndError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateEndError;
    } // method setDateEnd

    /**
     * Set the 'dateEnd' class variable
     * @param  dateEnd (String)
     */
    public int setDateEnd(String dateEnd) {
        try {
            int len = dateEnd.length();
            switch (len) {
            // date &/or times
                case 4: dateEnd += "-01";
                case 7: dateEnd += "-01";
                case 10: dateEnd += " 00";
                case 13: dateEnd += ":00";
                case 16: dateEnd += ":00"; break;
                // times only
                case 5: dateEnd = "1800-01-01 " + dateEnd + ":00"; break;
                case 8: dateEnd = "1800-01-01 " + dateEnd; break;
            } // switch
            if (dbg) System.out.println ("dateEnd = " + dateEnd);
            setDateEnd(Timestamp.valueOf(dateEnd));
        } catch (Exception e) {
            setDateEndError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateEndError;
    } // method setDateEnd

    /**
     * Called when an exception has occured
     * @param  dateEnd (String)
     */
    private void setDateEndError (Timestamp dateEnd, Exception e, int error) {
        this.dateEnd = dateEnd;
        dateEndErrorMessage = e.toString();
        dateEndError = error;
    } // method setDateEndError


    /**
     * Set the 'latNorth' class variable
     * @param  latNorth (float)
     */
    public int setLatNorth(float latNorth) {
        try {
            if ( ((latNorth == FLOATNULL) ||
                  (latNorth == FLOATNULL2)) ||
                !((latNorth < LAT_NORTH_MN) ||
                  (latNorth > LAT_NORTH_MX)) ) {
                this.latNorth = latNorth;
                latNorthError = ERROR_NORMAL;
            } else {
                throw latNorthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatNorthError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return latNorthError;
    } // method setLatNorth

    /**
     * Set the 'latNorth' class variable
     * @param  latNorth (Integer)
     */
    public int setLatNorth(Integer latNorth) {
        try {
            setLatNorth(latNorth.floatValue());
        } catch (Exception e) {
            setLatNorthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latNorthError;
    } // method setLatNorth

    /**
     * Set the 'latNorth' class variable
     * @param  latNorth (Float)
     */
    public int setLatNorth(Float latNorth) {
        try {
            setLatNorth(latNorth.floatValue());
        } catch (Exception e) {
            setLatNorthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latNorthError;
    } // method setLatNorth

    /**
     * Set the 'latNorth' class variable
     * @param  latNorth (String)
     */
    public int setLatNorth(String latNorth) {
        try {
            setLatNorth(new Float(latNorth).floatValue());
        } catch (Exception e) {
            setLatNorthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latNorthError;
    } // method setLatNorth

    /**
     * Called when an exception has occured
     * @param  latNorth (String)
     */
    private void setLatNorthError (float latNorth, Exception e, int error) {
        this.latNorth = latNorth;
        latNorthErrorMessage = e.toString();
        latNorthError = error;
    } // method setLatNorthError


    /**
     * Set the 'latSouth' class variable
     * @param  latSouth (float)
     */
    public int setLatSouth(float latSouth) {
        try {
            if ( ((latSouth == FLOATNULL) ||
                  (latSouth == FLOATNULL2)) ||
                !((latSouth < LAT_SOUTH_MN) ||
                  (latSouth > LAT_SOUTH_MX)) ) {
                this.latSouth = latSouth;
                latSouthError = ERROR_NORMAL;
            } else {
                throw latSouthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatSouthError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return latSouthError;
    } // method setLatSouth

    /**
     * Set the 'latSouth' class variable
     * @param  latSouth (Integer)
     */
    public int setLatSouth(Integer latSouth) {
        try {
            setLatSouth(latSouth.floatValue());
        } catch (Exception e) {
            setLatSouthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latSouthError;
    } // method setLatSouth

    /**
     * Set the 'latSouth' class variable
     * @param  latSouth (Float)
     */
    public int setLatSouth(Float latSouth) {
        try {
            setLatSouth(latSouth.floatValue());
        } catch (Exception e) {
            setLatSouthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latSouthError;
    } // method setLatSouth

    /**
     * Set the 'latSouth' class variable
     * @param  latSouth (String)
     */
    public int setLatSouth(String latSouth) {
        try {
            setLatSouth(new Float(latSouth).floatValue());
        } catch (Exception e) {
            setLatSouthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latSouthError;
    } // method setLatSouth

    /**
     * Called when an exception has occured
     * @param  latSouth (String)
     */
    private void setLatSouthError (float latSouth, Exception e, int error) {
        this.latSouth = latSouth;
        latSouthErrorMessage = e.toString();
        latSouthError = error;
    } // method setLatSouthError


    /**
     * Set the 'longWest' class variable
     * @param  longWest (float)
     */
    public int setLongWest(float longWest) {
        try {
            if ( ((longWest == FLOATNULL) ||
                  (longWest == FLOATNULL2)) ||
                !((longWest < LONG_WEST_MN) ||
                  (longWest > LONG_WEST_MX)) ) {
                this.longWest = longWest;
                longWestError = ERROR_NORMAL;
            } else {
                throw longWestOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLongWestError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return longWestError;
    } // method setLongWest

    /**
     * Set the 'longWest' class variable
     * @param  longWest (Integer)
     */
    public int setLongWest(Integer longWest) {
        try {
            setLongWest(longWest.floatValue());
        } catch (Exception e) {
            setLongWestError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longWestError;
    } // method setLongWest

    /**
     * Set the 'longWest' class variable
     * @param  longWest (Float)
     */
    public int setLongWest(Float longWest) {
        try {
            setLongWest(longWest.floatValue());
        } catch (Exception e) {
            setLongWestError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longWestError;
    } // method setLongWest

    /**
     * Set the 'longWest' class variable
     * @param  longWest (String)
     */
    public int setLongWest(String longWest) {
        try {
            setLongWest(new Float(longWest).floatValue());
        } catch (Exception e) {
            setLongWestError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longWestError;
    } // method setLongWest

    /**
     * Called when an exception has occured
     * @param  longWest (String)
     */
    private void setLongWestError (float longWest, Exception e, int error) {
        this.longWest = longWest;
        longWestErrorMessage = e.toString();
        longWestError = error;
    } // method setLongWestError


    /**
     * Set the 'longEast' class variable
     * @param  longEast (float)
     */
    public int setLongEast(float longEast) {
        try {
            if ( ((longEast == FLOATNULL) ||
                  (longEast == FLOATNULL2)) ||
                !((longEast < LONG_EAST_MN) ||
                  (longEast > LONG_EAST_MX)) ) {
                this.longEast = longEast;
                longEastError = ERROR_NORMAL;
            } else {
                throw longEastOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLongEastError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return longEastError;
    } // method setLongEast

    /**
     * Set the 'longEast' class variable
     * @param  longEast (Integer)
     */
    public int setLongEast(Integer longEast) {
        try {
            setLongEast(longEast.floatValue());
        } catch (Exception e) {
            setLongEastError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longEastError;
    } // method setLongEast

    /**
     * Set the 'longEast' class variable
     * @param  longEast (Float)
     */
    public int setLongEast(Float longEast) {
        try {
            setLongEast(longEast.floatValue());
        } catch (Exception e) {
            setLongEastError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longEastError;
    } // method setLongEast

    /**
     * Set the 'longEast' class variable
     * @param  longEast (String)
     */
    public int setLongEast(String longEast) {
        try {
            setLongEast(new Float(longEast).floatValue());
        } catch (Exception e) {
            setLongEastError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longEastError;
    } // method setLongEast

    /**
     * Called when an exception has occured
     * @param  longEast (String)
     */
    private void setLongEastError (float longEast, Exception e, int error) {
        this.longEast = longEast;
        longEastErrorMessage = e.toString();
        longEastError = error;
    } // method setLongEastError


    /**
     * Set the 'areaname' class variable
     * @param  areaname (String)
     */
    public int setAreaname(String areaname) {
        try {
            this.areaname = areaname;
            if (this.areaname != CHARNULL) {
                this.areaname = stripCRLF(this.areaname.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>areaname = " + this.areaname);
        } catch (Exception e) {
            setAreanameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return areanameError;
    } // method setAreaname

    /**
     * Called when an exception has occured
     * @param  areaname (String)
     */
    private void setAreanameError (String areaname, Exception e, int error) {
        this.areaname = areaname;
        areanameErrorMessage = e.toString();
        areanameError = error;
    } // method setAreanameError


    /**
     * Set the 'domain' class variable
     * @param  domain (String)
     */
    public int setDomain(String domain) {
        try {
            this.domain = domain;
            if (this.domain != CHARNULL) {
                this.domain = stripCRLF(this.domain.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>domain = " + this.domain);
        } catch (Exception e) {
            setDomainError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return domainError;
    } // method setDomain

    /**
     * Called when an exception has occured
     * @param  domain (String)
     */
    private void setDomainError (String domain, Exception e, int error) {
        this.domain = domain;
        domainErrorMessage = e.toString();
        domainError = error;
    } // method setDomainError


    /**
     * Set the 'trackChart' class variable
     * @param  trackChart (String)
     */
    public int setTrackChart(String trackChart) {
        try {
            this.trackChart = trackChart;
            if (this.trackChart != CHARNULL) {
                this.trackChart = stripCRLF(this.trackChart.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>trackChart = " + this.trackChart);
        } catch (Exception e) {
            setTrackChartError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return trackChartError;
    } // method setTrackChart

    /**
     * Called when an exception has occured
     * @param  trackChart (String)
     */
    private void setTrackChartError (String trackChart, Exception e, int error) {
        this.trackChart = trackChart;
        trackChartErrorMessage = e.toString();
        trackChartError = error;
    } // method setTrackChartError


    /**
     * Set the 'targetCountryCode' class variable
     * @param  targetCountryCode (int)
     */
    public int setTargetCountryCode(int targetCountryCode) {
        try {
            if ( ((targetCountryCode == INTNULL) ||
                  (targetCountryCode == INTNULL2)) ||
                !((targetCountryCode < TARGET_COUNTRY_CODE_MN) ||
                  (targetCountryCode > TARGET_COUNTRY_CODE_MX)) ) {
                this.targetCountryCode = targetCountryCode;
                targetCountryCodeError = ERROR_NORMAL;
            } else {
                throw targetCountryCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTargetCountryCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return targetCountryCodeError;
    } // method setTargetCountryCode

    /**
     * Set the 'targetCountryCode' class variable
     * @param  targetCountryCode (Integer)
     */
    public int setTargetCountryCode(Integer targetCountryCode) {
        try {
            setTargetCountryCode(targetCountryCode.intValue());
        } catch (Exception e) {
            setTargetCountryCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return targetCountryCodeError;
    } // method setTargetCountryCode

    /**
     * Set the 'targetCountryCode' class variable
     * @param  targetCountryCode (Float)
     */
    public int setTargetCountryCode(Float targetCountryCode) {
        try {
            if (targetCountryCode.floatValue() == FLOATNULL) {
                setTargetCountryCode(INTNULL);
            } else {
                setTargetCountryCode(targetCountryCode.intValue());
            } // if (targetCountryCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTargetCountryCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return targetCountryCodeError;
    } // method setTargetCountryCode

    /**
     * Set the 'targetCountryCode' class variable
     * @param  targetCountryCode (String)
     */
    public int setTargetCountryCode(String targetCountryCode) {
        try {
            setTargetCountryCode(new Integer(targetCountryCode).intValue());
        } catch (Exception e) {
            setTargetCountryCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return targetCountryCodeError;
    } // method setTargetCountryCode

    /**
     * Called when an exception has occured
     * @param  targetCountryCode (String)
     */
    private void setTargetCountryCodeError (int targetCountryCode, Exception e, int error) {
        this.targetCountryCode = targetCountryCode;
        targetCountryCodeErrorMessage = e.toString();
        targetCountryCodeError = error;
    } // method setTargetCountryCodeError


    /**
     * Set the 'stnidPrefix' class variable
     * @param  stnidPrefix (String)
     */
    public int setStnidPrefix(String stnidPrefix) {
        try {
            this.stnidPrefix = stnidPrefix;
            if (this.stnidPrefix != CHARNULL) {
                this.stnidPrefix = stripCRLF(this.stnidPrefix.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>stnidPrefix = " + this.stnidPrefix);
        } catch (Exception e) {
            setStnidPrefixError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return stnidPrefixError;
    } // method setStnidPrefix

    /**
     * Called when an exception has occured
     * @param  stnidPrefix (String)
     */
    private void setStnidPrefixError (String stnidPrefix, Exception e, int error) {
        this.stnidPrefix = stnidPrefix;
        stnidPrefixErrorMessage = e.toString();
        stnidPrefixError = error;
    } // method setStnidPrefixError


    /**
     * Set the 'gmtDiff' class variable
     * @param  gmtDiff (int)
     */
    public int setGmtDiff(int gmtDiff) {
        try {
            if ( ((gmtDiff == INTNULL) ||
                  (gmtDiff == INTNULL2)) ||
                !((gmtDiff < GMT_DIFF_MN) ||
                  (gmtDiff > GMT_DIFF_MX)) ) {
                this.gmtDiff = gmtDiff;
                gmtDiffError = ERROR_NORMAL;
            } else {
                throw gmtDiffOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setGmtDiffError(INTNULL, e, ERROR_LOCAL);
        } // try
        return gmtDiffError;
    } // method setGmtDiff

    /**
     * Set the 'gmtDiff' class variable
     * @param  gmtDiff (Integer)
     */
    public int setGmtDiff(Integer gmtDiff) {
        try {
            setGmtDiff(gmtDiff.intValue());
        } catch (Exception e) {
            setGmtDiffError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return gmtDiffError;
    } // method setGmtDiff

    /**
     * Set the 'gmtDiff' class variable
     * @param  gmtDiff (Float)
     */
    public int setGmtDiff(Float gmtDiff) {
        try {
            if (gmtDiff.floatValue() == FLOATNULL) {
                setGmtDiff(INTNULL);
            } else {
                setGmtDiff(gmtDiff.intValue());
            } // if (gmtDiff.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setGmtDiffError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return gmtDiffError;
    } // method setGmtDiff

    /**
     * Set the 'gmtDiff' class variable
     * @param  gmtDiff (String)
     */
    public int setGmtDiff(String gmtDiff) {
        try {
            setGmtDiff(new Integer(gmtDiff).intValue());
        } catch (Exception e) {
            setGmtDiffError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return gmtDiffError;
    } // method setGmtDiff

    /**
     * Called when an exception has occured
     * @param  gmtDiff (String)
     */
    private void setGmtDiffError (int gmtDiff, Exception e, int error) {
        this.gmtDiff = gmtDiff;
        gmtDiffErrorMessage = e.toString();
        gmtDiffError = error;
    } // method setGmtDiffError


    /**
     * Set the 'gmtFreeze' class variable
     * @param  gmtFreeze (String)
     */
    public int setGmtFreeze(String gmtFreeze) {
        try {
            this.gmtFreeze = gmtFreeze;
            if (this.gmtFreeze != CHARNULL) {
                this.gmtFreeze = stripCRLF(this.gmtFreeze.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>gmtFreeze = " + this.gmtFreeze);
        } catch (Exception e) {
            setGmtFreezeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return gmtFreezeError;
    } // method setGmtFreeze

    /**
     * Called when an exception has occured
     * @param  gmtFreeze (String)
     */
    private void setGmtFreezeError (String gmtFreeze, Exception e, int error) {
        this.gmtFreeze = gmtFreeze;
        gmtFreezeErrorMessage = e.toString();
        gmtFreezeError = error;
    } // method setGmtFreezeError


    /**
     * Set the 'projectionCode' class variable
     * @param  projectionCode (int)
     */
    public int setProjectionCode(int projectionCode) {
        try {
            if ( ((projectionCode == INTNULL) ||
                  (projectionCode == INTNULL2)) ||
                !((projectionCode < PROJECTION_CODE_MN) ||
                  (projectionCode > PROJECTION_CODE_MX)) ) {
                this.projectionCode = projectionCode;
                projectionCodeError = ERROR_NORMAL;
            } else {
                throw projectionCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setProjectionCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return projectionCodeError;
    } // method setProjectionCode

    /**
     * Set the 'projectionCode' class variable
     * @param  projectionCode (Integer)
     */
    public int setProjectionCode(Integer projectionCode) {
        try {
            setProjectionCode(projectionCode.intValue());
        } catch (Exception e) {
            setProjectionCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return projectionCodeError;
    } // method setProjectionCode

    /**
     * Set the 'projectionCode' class variable
     * @param  projectionCode (Float)
     */
    public int setProjectionCode(Float projectionCode) {
        try {
            if (projectionCode.floatValue() == FLOATNULL) {
                setProjectionCode(INTNULL);
            } else {
                setProjectionCode(projectionCode.intValue());
            } // if (projectionCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setProjectionCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return projectionCodeError;
    } // method setProjectionCode

    /**
     * Set the 'projectionCode' class variable
     * @param  projectionCode (String)
     */
    public int setProjectionCode(String projectionCode) {
        try {
            setProjectionCode(new Integer(projectionCode).intValue());
        } catch (Exception e) {
            setProjectionCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return projectionCodeError;
    } // method setProjectionCode

    /**
     * Called when an exception has occured
     * @param  projectionCode (String)
     */
    private void setProjectionCodeError (int projectionCode, Exception e, int error) {
        this.projectionCode = projectionCode;
        projectionCodeErrorMessage = e.toString();
        projectionCodeError = error;
    } // method setProjectionCodeError


    /**
     * Set the 'spheroidCode' class variable
     * @param  spheroidCode (int)
     */
    public int setSpheroidCode(int spheroidCode) {
        try {
            if ( ((spheroidCode == INTNULL) ||
                  (spheroidCode == INTNULL2)) ||
                !((spheroidCode < SPHEROID_CODE_MN) ||
                  (spheroidCode > SPHEROID_CODE_MX)) ) {
                this.spheroidCode = spheroidCode;
                spheroidCodeError = ERROR_NORMAL;
            } else {
                throw spheroidCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpheroidCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return spheroidCodeError;
    } // method setSpheroidCode

    /**
     * Set the 'spheroidCode' class variable
     * @param  spheroidCode (Integer)
     */
    public int setSpheroidCode(Integer spheroidCode) {
        try {
            setSpheroidCode(spheroidCode.intValue());
        } catch (Exception e) {
            setSpheroidCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return spheroidCodeError;
    } // method setSpheroidCode

    /**
     * Set the 'spheroidCode' class variable
     * @param  spheroidCode (Float)
     */
    public int setSpheroidCode(Float spheroidCode) {
        try {
            if (spheroidCode.floatValue() == FLOATNULL) {
                setSpheroidCode(INTNULL);
            } else {
                setSpheroidCode(spheroidCode.intValue());
            } // if (spheroidCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSpheroidCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return spheroidCodeError;
    } // method setSpheroidCode

    /**
     * Set the 'spheroidCode' class variable
     * @param  spheroidCode (String)
     */
    public int setSpheroidCode(String spheroidCode) {
        try {
            setSpheroidCode(new Integer(spheroidCode).intValue());
        } catch (Exception e) {
            setSpheroidCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return spheroidCodeError;
    } // method setSpheroidCode

    /**
     * Called when an exception has occured
     * @param  spheroidCode (String)
     */
    private void setSpheroidCodeError (int spheroidCode, Exception e, int error) {
        this.spheroidCode = spheroidCode;
        spheroidCodeErrorMessage = e.toString();
        spheroidCodeError = error;
    } // method setSpheroidCodeError


    /**
     * Set the 'datumCode' class variable
     * @param  datumCode (int)
     */
    public int setDatumCode(int datumCode) {
        try {
            if ( ((datumCode == INTNULL) ||
                  (datumCode == INTNULL2)) ||
                !((datumCode < DATUM_CODE_MN) ||
                  (datumCode > DATUM_CODE_MX)) ) {
                this.datumCode = datumCode;
                datumCodeError = ERROR_NORMAL;
            } else {
                throw datumCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDatumCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return datumCodeError;
    } // method setDatumCode

    /**
     * Set the 'datumCode' class variable
     * @param  datumCode (Integer)
     */
    public int setDatumCode(Integer datumCode) {
        try {
            setDatumCode(datumCode.intValue());
        } catch (Exception e) {
            setDatumCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return datumCodeError;
    } // method setDatumCode

    /**
     * Set the 'datumCode' class variable
     * @param  datumCode (Float)
     */
    public int setDatumCode(Float datumCode) {
        try {
            if (datumCode.floatValue() == FLOATNULL) {
                setDatumCode(INTNULL);
            } else {
                setDatumCode(datumCode.intValue());
            } // if (datumCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDatumCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return datumCodeError;
    } // method setDatumCode

    /**
     * Set the 'datumCode' class variable
     * @param  datumCode (String)
     */
    public int setDatumCode(String datumCode) {
        try {
            setDatumCode(new Integer(datumCode).intValue());
        } catch (Exception e) {
            setDatumCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return datumCodeError;
    } // method setDatumCode

    /**
     * Called when an exception has occured
     * @param  datumCode (String)
     */
    private void setDatumCodeError (int datumCode, Exception e, int error) {
        this.datumCode = datumCode;
        datumCodeErrorMessage = e.toString();
        datumCodeError = error;
    } // method setDatumCodeError


    /**
     * Set the 'dataRecorded' class variable
     * @param  dataRecorded (String)
     */
    public int setDataRecorded(String dataRecorded) {
        try {
            this.dataRecorded = dataRecorded;
            if (this.dataRecorded != CHARNULL) {
                this.dataRecorded = stripCRLF(this.dataRecorded.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>dataRecorded = " + this.dataRecorded);
        } catch (Exception e) {
            setDataRecordedError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return dataRecordedError;
    } // method setDataRecorded

    /**
     * Called when an exception has occured
     * @param  dataRecorded (String)
     */
    private void setDataRecordedError (String dataRecorded, Exception e, int error) {
        this.dataRecorded = dataRecorded;
        dataRecordedErrorMessage = e.toString();
        dataRecordedError = error;
    } // method setDataRecordedError


    /**
     * Set the 'surveyTypeCode' class variable
     * @param  surveyTypeCode (int)
     */
    public int setSurveyTypeCode(int surveyTypeCode) {
        try {
            if ( ((surveyTypeCode == INTNULL) ||
                  (surveyTypeCode == INTNULL2)) ||
                !((surveyTypeCode < SURVEY_TYPE_CODE_MN) ||
                  (surveyTypeCode > SURVEY_TYPE_CODE_MX)) ) {
                this.surveyTypeCode = surveyTypeCode;
                surveyTypeCodeError = ERROR_NORMAL;
            } else {
                throw surveyTypeCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSurveyTypeCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return surveyTypeCodeError;
    } // method setSurveyTypeCode

    /**
     * Set the 'surveyTypeCode' class variable
     * @param  surveyTypeCode (Integer)
     */
    public int setSurveyTypeCode(Integer surveyTypeCode) {
        try {
            setSurveyTypeCode(surveyTypeCode.intValue());
        } catch (Exception e) {
            setSurveyTypeCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return surveyTypeCodeError;
    } // method setSurveyTypeCode

    /**
     * Set the 'surveyTypeCode' class variable
     * @param  surveyTypeCode (Float)
     */
    public int setSurveyTypeCode(Float surveyTypeCode) {
        try {
            if (surveyTypeCode.floatValue() == FLOATNULL) {
                setSurveyTypeCode(INTNULL);
            } else {
                setSurveyTypeCode(surveyTypeCode.intValue());
            } // if (surveyTypeCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSurveyTypeCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return surveyTypeCodeError;
    } // method setSurveyTypeCode

    /**
     * Set the 'surveyTypeCode' class variable
     * @param  surveyTypeCode (String)
     */
    public int setSurveyTypeCode(String surveyTypeCode) {
        try {
            setSurveyTypeCode(new Integer(surveyTypeCode).intValue());
        } catch (Exception e) {
            setSurveyTypeCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return surveyTypeCodeError;
    } // method setSurveyTypeCode

    /**
     * Called when an exception has occured
     * @param  surveyTypeCode (String)
     */
    private void setSurveyTypeCodeError (int surveyTypeCode, Exception e, int error) {
        this.surveyTypeCode = surveyTypeCode;
        surveyTypeCodeErrorMessage = e.toString();
        surveyTypeCodeError = error;
    } // method setSurveyTypeCodeError


    /**
     * Set the 'dataAvailable' class variable
     * @param  dataAvailable (String)
     */
    public int setDataAvailable(String dataAvailable) {
        try {
            this.dataAvailable = dataAvailable;
            if (this.dataAvailable != CHARNULL) {
                this.dataAvailable = stripCRLF(this.dataAvailable.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>dataAvailable = " + this.dataAvailable);
        } catch (Exception e) {
            setDataAvailableError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return dataAvailableError;
    } // method setDataAvailable

    /**
     * Called when an exception has occured
     * @param  dataAvailable (String)
     */
    private void setDataAvailableError (String dataAvailable, Exception e, int error) {
        this.dataAvailable = dataAvailable;
        dataAvailableErrorMessage = e.toString();
        dataAvailableError = error;
    } // method setDataAvailableError


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
     * Return the 'dataCentre' class variable
     * @return dataCentre (String)
     */
    public String getDataCentre() {
        return dataCentre;
    } // method getDataCentre

    /**
     * Return the 'dataCentre' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataCentre methods
     * @return dataCentre (String)
     */
    public String getDataCentre(String s) {
        return (dataCentre != CHARNULL ? dataCentre.replace('"','\'') : "");
    } // method getDataCentre


    /**
     * Return the 'projectName' class variable
     * @return projectName (String)
     */
    public String getProjectName() {
        return projectName;
    } // method getProjectName

    /**
     * Return the 'projectName' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getProjectName methods
     * @return projectName (String)
     */
    public String getProjectName(String s) {
        return (projectName != CHARNULL ? projectName.replace('"','\'') : "");
    } // method getProjectName


    /**
     * Return the 'cruiseName' class variable
     * @return cruiseName (String)
     */
    public String getCruiseName() {
        return cruiseName;
    } // method getCruiseName

    /**
     * Return the 'cruiseName' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCruiseName methods
     * @return cruiseName (String)
     */
    public String getCruiseName(String s) {
        return (cruiseName != CHARNULL ? cruiseName.replace('"','\'') : "");
    } // method getCruiseName


    /**
     * Return the 'nationalPgm' class variable
     * @return nationalPgm (String)
     */
    public String getNationalPgm() {
        return nationalPgm;
    } // method getNationalPgm

    /**
     * Return the 'nationalPgm' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNationalPgm methods
     * @return nationalPgm (String)
     */
    public String getNationalPgm(String s) {
        return (nationalPgm != CHARNULL ? nationalPgm.replace('"','\'') : "");
    } // method getNationalPgm


    /**
     * Return the 'exchangeRestrict' class variable
     * @return exchangeRestrict (String)
     */
    public String getExchangeRestrict() {
        return exchangeRestrict;
    } // method getExchangeRestrict

    /**
     * Return the 'exchangeRestrict' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getExchangeRestrict methods
     * @return exchangeRestrict (String)
     */
    public String getExchangeRestrict(String s) {
        return (exchangeRestrict != CHARNULL ? exchangeRestrict.replace('"','\'') : "");
    } // method getExchangeRestrict


    /**
     * Return the 'coopPgm' class variable
     * @return coopPgm (String)
     */
    public String getCoopPgm() {
        return coopPgm;
    } // method getCoopPgm

    /**
     * Return the 'coopPgm' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCoopPgm methods
     * @return coopPgm (String)
     */
    public String getCoopPgm(String s) {
        return (coopPgm != CHARNULL ? coopPgm.replace('"','\'') : "");
    } // method getCoopPgm


    /**
     * Return the 'coordinatedInt' class variable
     * @return coordinatedInt (String)
     */
    public String getCoordinatedInt() {
        return coordinatedInt;
    } // method getCoordinatedInt

    /**
     * Return the 'coordinatedInt' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCoordinatedInt methods
     * @return coordinatedInt (String)
     */
    public String getCoordinatedInt(String s) {
        return (coordinatedInt != CHARNULL ? coordinatedInt.replace('"','\'') : "");
    } // method getCoordinatedInt


    /**
     * Return the 'planamCode' class variable
     * @return planamCode (int)
     */
    public int getPlanamCode() {
        return planamCode;
    } // method getPlanamCode

    /**
     * Return the 'planamCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlanamCode methods
     * @return planamCode (String)
     */
    public String getPlanamCode(String s) {
        return ((planamCode != INTNULL) ? new Integer(planamCode).toString() : "");
    } // method getPlanamCode


    /**
     * Return the 'portStart' class variable
     * @return portStart (String)
     */
    public String getPortStart() {
        return portStart;
    } // method getPortStart

    /**
     * Return the 'portStart' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPortStart methods
     * @return portStart (String)
     */
    public String getPortStart(String s) {
        return (portStart != CHARNULL ? portStart.replace('"','\'') : "");
    } // method getPortStart


    /**
     * Return the 'portEnd' class variable
     * @return portEnd (String)
     */
    public String getPortEnd() {
        return portEnd;
    } // method getPortEnd

    /**
     * Return the 'portEnd' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPortEnd methods
     * @return portEnd (String)
     */
    public String getPortEnd(String s) {
        return (portEnd != CHARNULL ? portEnd.replace('"','\'') : "");
    } // method getPortEnd


    /**
     * Return the 'countryCode' class variable
     * @return countryCode (int)
     */
    public int getCountryCode() {
        return countryCode;
    } // method getCountryCode

    /**
     * Return the 'countryCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCountryCode methods
     * @return countryCode (String)
     */
    public String getCountryCode(String s) {
        return ((countryCode != INTNULL) ? new Integer(countryCode).toString() : "");
    } // method getCountryCode


    /**
     * Return the 'institCode' class variable
     * @return institCode (int)
     */
    public int getInstitCode() {
        return institCode;
    } // method getInstitCode

    /**
     * Return the 'institCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getInstitCode methods
     * @return institCode (String)
     */
    public String getInstitCode(String s) {
        return ((institCode != INTNULL) ? new Integer(institCode).toString() : "");
    } // method getInstitCode


    /**
     * Return the 'coordCode' class variable
     * @return coordCode (int)
     */
    public int getCoordCode() {
        return coordCode;
    } // method getCoordCode

    /**
     * Return the 'coordCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCoordCode methods
     * @return coordCode (String)
     */
    public String getCoordCode(String s) {
        return ((coordCode != INTNULL) ? new Integer(coordCode).toString() : "");
    } // method getCoordCode


    /**
     * Return the 'sciCode1' class variable
     * @return sciCode1 (int)
     */
    public int getSciCode1() {
        return sciCode1;
    } // method getSciCode1

    /**
     * Return the 'sciCode1' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSciCode1 methods
     * @return sciCode1 (String)
     */
    public String getSciCode1(String s) {
        return ((sciCode1 != INTNULL) ? new Integer(sciCode1).toString() : "");
    } // method getSciCode1


    /**
     * Return the 'sciCode2' class variable
     * @return sciCode2 (int)
     */
    public int getSciCode2() {
        return sciCode2;
    } // method getSciCode2

    /**
     * Return the 'sciCode2' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSciCode2 methods
     * @return sciCode2 (String)
     */
    public String getSciCode2(String s) {
        return ((sciCode2 != INTNULL) ? new Integer(sciCode2).toString() : "");
    } // method getSciCode2


    /**
     * Return the 'dateStart' class variable
     * @return dateStart (Timestamp)
     */
    public Timestamp getDateStart() {
        return dateStart;
    } // method getDateStart

    /**
     * Return the 'dateStart' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateStart methods
     * @return dateStart (String)
     */
    public String getDateStart(String s) {
        if (dateStart.equals(DATENULL)) {
            return ("");
        } else {
            String dateStartStr = dateStart.toString();
            return dateStartStr.substring(0,dateStartStr.indexOf('.'));
        } // if
    } // method getDateStart


    /**
     * Return the 'dateEnd' class variable
     * @return dateEnd (Timestamp)
     */
    public Timestamp getDateEnd() {
        return dateEnd;
    } // method getDateEnd

    /**
     * Return the 'dateEnd' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateEnd methods
     * @return dateEnd (String)
     */
    public String getDateEnd(String s) {
        if (dateEnd.equals(DATENULL)) {
            return ("");
        } else {
            String dateEndStr = dateEnd.toString();
            return dateEndStr.substring(0,dateEndStr.indexOf('.'));
        } // if
    } // method getDateEnd


    /**
     * Return the 'latNorth' class variable
     * @return latNorth (float)
     */
    public float getLatNorth() {
        return latNorth;
    } // method getLatNorth

    /**
     * Return the 'latNorth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLatNorth methods
     * @return latNorth (String)
     */
    public String getLatNorth(String s) {
        return ((latNorth != FLOATNULL) ? new Float(latNorth).toString() : "");
    } // method getLatNorth


    /**
     * Return the 'latSouth' class variable
     * @return latSouth (float)
     */
    public float getLatSouth() {
        return latSouth;
    } // method getLatSouth

    /**
     * Return the 'latSouth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLatSouth methods
     * @return latSouth (String)
     */
    public String getLatSouth(String s) {
        return ((latSouth != FLOATNULL) ? new Float(latSouth).toString() : "");
    } // method getLatSouth


    /**
     * Return the 'longWest' class variable
     * @return longWest (float)
     */
    public float getLongWest() {
        return longWest;
    } // method getLongWest

    /**
     * Return the 'longWest' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLongWest methods
     * @return longWest (String)
     */
    public String getLongWest(String s) {
        return ((longWest != FLOATNULL) ? new Float(longWest).toString() : "");
    } // method getLongWest


    /**
     * Return the 'longEast' class variable
     * @return longEast (float)
     */
    public float getLongEast() {
        return longEast;
    } // method getLongEast

    /**
     * Return the 'longEast' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLongEast methods
     * @return longEast (String)
     */
    public String getLongEast(String s) {
        return ((longEast != FLOATNULL) ? new Float(longEast).toString() : "");
    } // method getLongEast


    /**
     * Return the 'areaname' class variable
     * @return areaname (String)
     */
    public String getAreaname() {
        return areaname;
    } // method getAreaname

    /**
     * Return the 'areaname' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAreaname methods
     * @return areaname (String)
     */
    public String getAreaname(String s) {
        return (areaname != CHARNULL ? areaname.replace('"','\'') : "");
    } // method getAreaname


    /**
     * Return the 'domain' class variable
     * @return domain (String)
     */
    public String getDomain() {
        return domain;
    } // method getDomain

    /**
     * Return the 'domain' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDomain methods
     * @return domain (String)
     */
    public String getDomain(String s) {
        return (domain != CHARNULL ? domain.replace('"','\'') : "");
    } // method getDomain


    /**
     * Return the 'trackChart' class variable
     * @return trackChart (String)
     */
    public String getTrackChart() {
        return trackChart;
    } // method getTrackChart

    /**
     * Return the 'trackChart' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTrackChart methods
     * @return trackChart (String)
     */
    public String getTrackChart(String s) {
        return (trackChart != CHARNULL ? trackChart.replace('"','\'') : "");
    } // method getTrackChart


    /**
     * Return the 'targetCountryCode' class variable
     * @return targetCountryCode (int)
     */
    public int getTargetCountryCode() {
        return targetCountryCode;
    } // method getTargetCountryCode

    /**
     * Return the 'targetCountryCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTargetCountryCode methods
     * @return targetCountryCode (String)
     */
    public String getTargetCountryCode(String s) {
        return ((targetCountryCode != INTNULL) ? new Integer(targetCountryCode).toString() : "");
    } // method getTargetCountryCode


    /**
     * Return the 'stnidPrefix' class variable
     * @return stnidPrefix (String)
     */
    public String getStnidPrefix() {
        return stnidPrefix;
    } // method getStnidPrefix

    /**
     * Return the 'stnidPrefix' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStnidPrefix methods
     * @return stnidPrefix (String)
     */
    public String getStnidPrefix(String s) {
        return (stnidPrefix != CHARNULL ? stnidPrefix.replace('"','\'') : "");
    } // method getStnidPrefix


    /**
     * Return the 'gmtDiff' class variable
     * @return gmtDiff (int)
     */
    public int getGmtDiff() {
        return gmtDiff;
    } // method getGmtDiff

    /**
     * Return the 'gmtDiff' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getGmtDiff methods
     * @return gmtDiff (String)
     */
    public String getGmtDiff(String s) {
        return ((gmtDiff != INTNULL) ? new Integer(gmtDiff).toString() : "");
    } // method getGmtDiff


    /**
     * Return the 'gmtFreeze' class variable
     * @return gmtFreeze (String)
     */
    public String getGmtFreeze() {
        return gmtFreeze;
    } // method getGmtFreeze

    /**
     * Return the 'gmtFreeze' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getGmtFreeze methods
     * @return gmtFreeze (String)
     */
    public String getGmtFreeze(String s) {
        return (gmtFreeze != CHARNULL ? gmtFreeze.replace('"','\'') : "");
    } // method getGmtFreeze


    /**
     * Return the 'projectionCode' class variable
     * @return projectionCode (int)
     */
    public int getProjectionCode() {
        return projectionCode;
    } // method getProjectionCode

    /**
     * Return the 'projectionCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getProjectionCode methods
     * @return projectionCode (String)
     */
    public String getProjectionCode(String s) {
        return ((projectionCode != INTNULL) ? new Integer(projectionCode).toString() : "");
    } // method getProjectionCode


    /**
     * Return the 'spheroidCode' class variable
     * @return spheroidCode (int)
     */
    public int getSpheroidCode() {
        return spheroidCode;
    } // method getSpheroidCode

    /**
     * Return the 'spheroidCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpheroidCode methods
     * @return spheroidCode (String)
     */
    public String getSpheroidCode(String s) {
        return ((spheroidCode != INTNULL) ? new Integer(spheroidCode).toString() : "");
    } // method getSpheroidCode


    /**
     * Return the 'datumCode' class variable
     * @return datumCode (int)
     */
    public int getDatumCode() {
        return datumCode;
    } // method getDatumCode

    /**
     * Return the 'datumCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDatumCode methods
     * @return datumCode (String)
     */
    public String getDatumCode(String s) {
        return ((datumCode != INTNULL) ? new Integer(datumCode).toString() : "");
    } // method getDatumCode


    /**
     * Return the 'dataRecorded' class variable
     * @return dataRecorded (String)
     */
    public String getDataRecorded() {
        return dataRecorded;
    } // method getDataRecorded

    /**
     * Return the 'dataRecorded' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataRecorded methods
     * @return dataRecorded (String)
     */
    public String getDataRecorded(String s) {
        return (dataRecorded != CHARNULL ? dataRecorded.replace('"','\'') : "");
    } // method getDataRecorded


    /**
     * Return the 'surveyTypeCode' class variable
     * @return surveyTypeCode (int)
     */
    public int getSurveyTypeCode() {
        return surveyTypeCode;
    } // method getSurveyTypeCode

    /**
     * Return the 'surveyTypeCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurveyTypeCode methods
     * @return surveyTypeCode (String)
     */
    public String getSurveyTypeCode(String s) {
        return ((surveyTypeCode != INTNULL) ? new Integer(surveyTypeCode).toString() : "");
    } // method getSurveyTypeCode


    /**
     * Return the 'dataAvailable' class variable
     * @return dataAvailable (String)
     */
    public String getDataAvailable() {
        return dataAvailable;
    } // method getDataAvailable

    /**
     * Return the 'dataAvailable' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataAvailable methods
     * @return dataAvailable (String)
     */
    public String getDataAvailable(String s) {
        return (dataAvailable != CHARNULL ? dataAvailable.replace('"','\'') : "");
    } // method getDataAvailable


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
            (dataCentre == CHARNULL) &&
            (projectName == CHARNULL) &&
            (cruiseName == CHARNULL) &&
            (nationalPgm == CHARNULL) &&
            (exchangeRestrict == CHARNULL) &&
            (coopPgm == CHARNULL) &&
            (coordinatedInt == CHARNULL) &&
            (planamCode == INTNULL) &&
            (portStart == CHARNULL) &&
            (portEnd == CHARNULL) &&
            (countryCode == INTNULL) &&
            (institCode == INTNULL) &&
            (coordCode == INTNULL) &&
            (sciCode1 == INTNULL) &&
            (sciCode2 == INTNULL) &&
            (dateStart.equals(DATENULL)) &&
            (dateEnd.equals(DATENULL)) &&
            (latNorth == FLOATNULL) &&
            (latSouth == FLOATNULL) &&
            (longWest == FLOATNULL) &&
            (longEast == FLOATNULL) &&
            (areaname == CHARNULL) &&
            (domain == CHARNULL) &&
            (trackChart == CHARNULL) &&
            (targetCountryCode == INTNULL) &&
            (stnidPrefix == CHARNULL) &&
            (gmtDiff == INTNULL) &&
            (gmtFreeze == CHARNULL) &&
            (projectionCode == INTNULL) &&
            (spheroidCode == INTNULL) &&
            (datumCode == INTNULL) &&
            (dataRecorded == CHARNULL) &&
            (surveyTypeCode == INTNULL) &&
            (dataAvailable == CHARNULL)) {
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
            dataCentreError +
            projectNameError +
            cruiseNameError +
            nationalPgmError +
            exchangeRestrictError +
            coopPgmError +
            coordinatedIntError +
            planamCodeError +
            portStartError +
            portEndError +
            countryCodeError +
            institCodeError +
            coordCodeError +
            sciCode1Error +
            sciCode2Error +
            dateStartError +
            dateEndError +
            latNorthError +
            latSouthError +
            longWestError +
            longEastError +
            areanameError +
            domainError +
            trackChartError +
            targetCountryCodeError +
            stnidPrefixError +
            gmtDiffError +
            gmtFreezeError +
            projectionCodeError +
            spheroidCodeError +
            datumCodeError +
            dataRecordedError +
            surveyTypeCodeError +
            dataAvailableError;
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
     * Gets the errorcode for the dataCentre instance variable
     * @return errorcode (int)
     */
    public int getDataCentreError() {
        return dataCentreError;
    } // method getDataCentreError

    /**
     * Gets the errorMessage for the dataCentre instance variable
     * @return errorMessage (String)
     */
    public String getDataCentreErrorMessage() {
        return dataCentreErrorMessage;
    } // method getDataCentreErrorMessage


    /**
     * Gets the errorcode for the projectName instance variable
     * @return errorcode (int)
     */
    public int getProjectNameError() {
        return projectNameError;
    } // method getProjectNameError

    /**
     * Gets the errorMessage for the projectName instance variable
     * @return errorMessage (String)
     */
    public String getProjectNameErrorMessage() {
        return projectNameErrorMessage;
    } // method getProjectNameErrorMessage


    /**
     * Gets the errorcode for the cruiseName instance variable
     * @return errorcode (int)
     */
    public int getCruiseNameError() {
        return cruiseNameError;
    } // method getCruiseNameError

    /**
     * Gets the errorMessage for the cruiseName instance variable
     * @return errorMessage (String)
     */
    public String getCruiseNameErrorMessage() {
        return cruiseNameErrorMessage;
    } // method getCruiseNameErrorMessage


    /**
     * Gets the errorcode for the nationalPgm instance variable
     * @return errorcode (int)
     */
    public int getNationalPgmError() {
        return nationalPgmError;
    } // method getNationalPgmError

    /**
     * Gets the errorMessage for the nationalPgm instance variable
     * @return errorMessage (String)
     */
    public String getNationalPgmErrorMessage() {
        return nationalPgmErrorMessage;
    } // method getNationalPgmErrorMessage


    /**
     * Gets the errorcode for the exchangeRestrict instance variable
     * @return errorcode (int)
     */
    public int getExchangeRestrictError() {
        return exchangeRestrictError;
    } // method getExchangeRestrictError

    /**
     * Gets the errorMessage for the exchangeRestrict instance variable
     * @return errorMessage (String)
     */
    public String getExchangeRestrictErrorMessage() {
        return exchangeRestrictErrorMessage;
    } // method getExchangeRestrictErrorMessage


    /**
     * Gets the errorcode for the coopPgm instance variable
     * @return errorcode (int)
     */
    public int getCoopPgmError() {
        return coopPgmError;
    } // method getCoopPgmError

    /**
     * Gets the errorMessage for the coopPgm instance variable
     * @return errorMessage (String)
     */
    public String getCoopPgmErrorMessage() {
        return coopPgmErrorMessage;
    } // method getCoopPgmErrorMessage


    /**
     * Gets the errorcode for the coordinatedInt instance variable
     * @return errorcode (int)
     */
    public int getCoordinatedIntError() {
        return coordinatedIntError;
    } // method getCoordinatedIntError

    /**
     * Gets the errorMessage for the coordinatedInt instance variable
     * @return errorMessage (String)
     */
    public String getCoordinatedIntErrorMessage() {
        return coordinatedIntErrorMessage;
    } // method getCoordinatedIntErrorMessage


    /**
     * Gets the errorcode for the planamCode instance variable
     * @return errorcode (int)
     */
    public int getPlanamCodeError() {
        return planamCodeError;
    } // method getPlanamCodeError

    /**
     * Gets the errorMessage for the planamCode instance variable
     * @return errorMessage (String)
     */
    public String getPlanamCodeErrorMessage() {
        return planamCodeErrorMessage;
    } // method getPlanamCodeErrorMessage


    /**
     * Gets the errorcode for the portStart instance variable
     * @return errorcode (int)
     */
    public int getPortStartError() {
        return portStartError;
    } // method getPortStartError

    /**
     * Gets the errorMessage for the portStart instance variable
     * @return errorMessage (String)
     */
    public String getPortStartErrorMessage() {
        return portStartErrorMessage;
    } // method getPortStartErrorMessage


    /**
     * Gets the errorcode for the portEnd instance variable
     * @return errorcode (int)
     */
    public int getPortEndError() {
        return portEndError;
    } // method getPortEndError

    /**
     * Gets the errorMessage for the portEnd instance variable
     * @return errorMessage (String)
     */
    public String getPortEndErrorMessage() {
        return portEndErrorMessage;
    } // method getPortEndErrorMessage


    /**
     * Gets the errorcode for the countryCode instance variable
     * @return errorcode (int)
     */
    public int getCountryCodeError() {
        return countryCodeError;
    } // method getCountryCodeError

    /**
     * Gets the errorMessage for the countryCode instance variable
     * @return errorMessage (String)
     */
    public String getCountryCodeErrorMessage() {
        return countryCodeErrorMessage;
    } // method getCountryCodeErrorMessage


    /**
     * Gets the errorcode for the institCode instance variable
     * @return errorcode (int)
     */
    public int getInstitCodeError() {
        return institCodeError;
    } // method getInstitCodeError

    /**
     * Gets the errorMessage for the institCode instance variable
     * @return errorMessage (String)
     */
    public String getInstitCodeErrorMessage() {
        return institCodeErrorMessage;
    } // method getInstitCodeErrorMessage


    /**
     * Gets the errorcode for the coordCode instance variable
     * @return errorcode (int)
     */
    public int getCoordCodeError() {
        return coordCodeError;
    } // method getCoordCodeError

    /**
     * Gets the errorMessage for the coordCode instance variable
     * @return errorMessage (String)
     */
    public String getCoordCodeErrorMessage() {
        return coordCodeErrorMessage;
    } // method getCoordCodeErrorMessage


    /**
     * Gets the errorcode for the sciCode1 instance variable
     * @return errorcode (int)
     */
    public int getSciCode1Error() {
        return sciCode1Error;
    } // method getSciCode1Error

    /**
     * Gets the errorMessage for the sciCode1 instance variable
     * @return errorMessage (String)
     */
    public String getSciCode1ErrorMessage() {
        return sciCode1ErrorMessage;
    } // method getSciCode1ErrorMessage


    /**
     * Gets the errorcode for the sciCode2 instance variable
     * @return errorcode (int)
     */
    public int getSciCode2Error() {
        return sciCode2Error;
    } // method getSciCode2Error

    /**
     * Gets the errorMessage for the sciCode2 instance variable
     * @return errorMessage (String)
     */
    public String getSciCode2ErrorMessage() {
        return sciCode2ErrorMessage;
    } // method getSciCode2ErrorMessage


    /**
     * Gets the errorcode for the dateStart instance variable
     * @return errorcode (int)
     */
    public int getDateStartError() {
        return dateStartError;
    } // method getDateStartError

    /**
     * Gets the errorMessage for the dateStart instance variable
     * @return errorMessage (String)
     */
    public String getDateStartErrorMessage() {
        return dateStartErrorMessage;
    } // method getDateStartErrorMessage


    /**
     * Gets the errorcode for the dateEnd instance variable
     * @return errorcode (int)
     */
    public int getDateEndError() {
        return dateEndError;
    } // method getDateEndError

    /**
     * Gets the errorMessage for the dateEnd instance variable
     * @return errorMessage (String)
     */
    public String getDateEndErrorMessage() {
        return dateEndErrorMessage;
    } // method getDateEndErrorMessage


    /**
     * Gets the errorcode for the latNorth instance variable
     * @return errorcode (int)
     */
    public int getLatNorthError() {
        return latNorthError;
    } // method getLatNorthError

    /**
     * Gets the errorMessage for the latNorth instance variable
     * @return errorMessage (String)
     */
    public String getLatNorthErrorMessage() {
        return latNorthErrorMessage;
    } // method getLatNorthErrorMessage


    /**
     * Gets the errorcode for the latSouth instance variable
     * @return errorcode (int)
     */
    public int getLatSouthError() {
        return latSouthError;
    } // method getLatSouthError

    /**
     * Gets the errorMessage for the latSouth instance variable
     * @return errorMessage (String)
     */
    public String getLatSouthErrorMessage() {
        return latSouthErrorMessage;
    } // method getLatSouthErrorMessage


    /**
     * Gets the errorcode for the longWest instance variable
     * @return errorcode (int)
     */
    public int getLongWestError() {
        return longWestError;
    } // method getLongWestError

    /**
     * Gets the errorMessage for the longWest instance variable
     * @return errorMessage (String)
     */
    public String getLongWestErrorMessage() {
        return longWestErrorMessage;
    } // method getLongWestErrorMessage


    /**
     * Gets the errorcode for the longEast instance variable
     * @return errorcode (int)
     */
    public int getLongEastError() {
        return longEastError;
    } // method getLongEastError

    /**
     * Gets the errorMessage for the longEast instance variable
     * @return errorMessage (String)
     */
    public String getLongEastErrorMessage() {
        return longEastErrorMessage;
    } // method getLongEastErrorMessage


    /**
     * Gets the errorcode for the areaname instance variable
     * @return errorcode (int)
     */
    public int getAreanameError() {
        return areanameError;
    } // method getAreanameError

    /**
     * Gets the errorMessage for the areaname instance variable
     * @return errorMessage (String)
     */
    public String getAreanameErrorMessage() {
        return areanameErrorMessage;
    } // method getAreanameErrorMessage


    /**
     * Gets the errorcode for the domain instance variable
     * @return errorcode (int)
     */
    public int getDomainError() {
        return domainError;
    } // method getDomainError

    /**
     * Gets the errorMessage for the domain instance variable
     * @return errorMessage (String)
     */
    public String getDomainErrorMessage() {
        return domainErrorMessage;
    } // method getDomainErrorMessage


    /**
     * Gets the errorcode for the trackChart instance variable
     * @return errorcode (int)
     */
    public int getTrackChartError() {
        return trackChartError;
    } // method getTrackChartError

    /**
     * Gets the errorMessage for the trackChart instance variable
     * @return errorMessage (String)
     */
    public String getTrackChartErrorMessage() {
        return trackChartErrorMessage;
    } // method getTrackChartErrorMessage


    /**
     * Gets the errorcode for the targetCountryCode instance variable
     * @return errorcode (int)
     */
    public int getTargetCountryCodeError() {
        return targetCountryCodeError;
    } // method getTargetCountryCodeError

    /**
     * Gets the errorMessage for the targetCountryCode instance variable
     * @return errorMessage (String)
     */
    public String getTargetCountryCodeErrorMessage() {
        return targetCountryCodeErrorMessage;
    } // method getTargetCountryCodeErrorMessage


    /**
     * Gets the errorcode for the stnidPrefix instance variable
     * @return errorcode (int)
     */
    public int getStnidPrefixError() {
        return stnidPrefixError;
    } // method getStnidPrefixError

    /**
     * Gets the errorMessage for the stnidPrefix instance variable
     * @return errorMessage (String)
     */
    public String getStnidPrefixErrorMessage() {
        return stnidPrefixErrorMessage;
    } // method getStnidPrefixErrorMessage


    /**
     * Gets the errorcode for the gmtDiff instance variable
     * @return errorcode (int)
     */
    public int getGmtDiffError() {
        return gmtDiffError;
    } // method getGmtDiffError

    /**
     * Gets the errorMessage for the gmtDiff instance variable
     * @return errorMessage (String)
     */
    public String getGmtDiffErrorMessage() {
        return gmtDiffErrorMessage;
    } // method getGmtDiffErrorMessage


    /**
     * Gets the errorcode for the gmtFreeze instance variable
     * @return errorcode (int)
     */
    public int getGmtFreezeError() {
        return gmtFreezeError;
    } // method getGmtFreezeError

    /**
     * Gets the errorMessage for the gmtFreeze instance variable
     * @return errorMessage (String)
     */
    public String getGmtFreezeErrorMessage() {
        return gmtFreezeErrorMessage;
    } // method getGmtFreezeErrorMessage


    /**
     * Gets the errorcode for the projectionCode instance variable
     * @return errorcode (int)
     */
    public int getProjectionCodeError() {
        return projectionCodeError;
    } // method getProjectionCodeError

    /**
     * Gets the errorMessage for the projectionCode instance variable
     * @return errorMessage (String)
     */
    public String getProjectionCodeErrorMessage() {
        return projectionCodeErrorMessage;
    } // method getProjectionCodeErrorMessage


    /**
     * Gets the errorcode for the spheroidCode instance variable
     * @return errorcode (int)
     */
    public int getSpheroidCodeError() {
        return spheroidCodeError;
    } // method getSpheroidCodeError

    /**
     * Gets the errorMessage for the spheroidCode instance variable
     * @return errorMessage (String)
     */
    public String getSpheroidCodeErrorMessage() {
        return spheroidCodeErrorMessage;
    } // method getSpheroidCodeErrorMessage


    /**
     * Gets the errorcode for the datumCode instance variable
     * @return errorcode (int)
     */
    public int getDatumCodeError() {
        return datumCodeError;
    } // method getDatumCodeError

    /**
     * Gets the errorMessage for the datumCode instance variable
     * @return errorMessage (String)
     */
    public String getDatumCodeErrorMessage() {
        return datumCodeErrorMessage;
    } // method getDatumCodeErrorMessage


    /**
     * Gets the errorcode for the dataRecorded instance variable
     * @return errorcode (int)
     */
    public int getDataRecordedError() {
        return dataRecordedError;
    } // method getDataRecordedError

    /**
     * Gets the errorMessage for the dataRecorded instance variable
     * @return errorMessage (String)
     */
    public String getDataRecordedErrorMessage() {
        return dataRecordedErrorMessage;
    } // method getDataRecordedErrorMessage


    /**
     * Gets the errorcode for the surveyTypeCode instance variable
     * @return errorcode (int)
     */
    public int getSurveyTypeCodeError() {
        return surveyTypeCodeError;
    } // method getSurveyTypeCodeError

    /**
     * Gets the errorMessage for the surveyTypeCode instance variable
     * @return errorMessage (String)
     */
    public String getSurveyTypeCodeErrorMessage() {
        return surveyTypeCodeErrorMessage;
    } // method getSurveyTypeCodeErrorMessage


    /**
     * Gets the errorcode for the dataAvailable instance variable
     * @return errorcode (int)
     */
    public int getDataAvailableError() {
        return dataAvailableError;
    } // method getDataAvailableError

    /**
     * Gets the errorMessage for the dataAvailable instance variable
     * @return errorMessage (String)
     */
    public String getDataAvailableErrorMessage() {
        return dataAvailableErrorMessage;
    } // method getDataAvailableErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnInventory. e.g.<pre>
     * MrnInventory inventory = new MrnInventory(<val1>);
     * MrnInventory inventoryArray[] = inventory.get();</pre>
     * will get the MrnInventory record where surveyId = <val1>.
     * @return Array of MrnInventory (MrnInventory[])
     */
    public MrnInventory[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnInventory inventoryArray[] =
     *     new MrnInventory().get(MrnInventory.SURVEY_ID+"=<val1>");</pre>
     * will get the MrnInventory record where surveyId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnInventory (MrnInventory[])
     */
    public MrnInventory[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnInventory inventoryArray[] =
     *     new MrnInventory().get("1=1",inventory.SURVEY_ID);</pre>
     * will get the all the MrnInventory records, and order them by surveyId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnInventory (MrnInventory[])
     */
    public MrnInventory[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnInventory.SURVEY_ID,MrnInventory.DATA_CENTRE;
     * String where = MrnInventory.SURVEY_ID + "=<val1";
     * String order = MrnInventory.SURVEY_ID;
     * MrnInventory inventoryArray[] =
     *     new MrnInventory().get(columns, where, order);</pre>
     * will get the surveyId and dataCentre colums of all MrnInventory records,
     * where surveyId = <val1>, and order them by surveyId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnInventory (MrnInventory[])
     */
    public MrnInventory[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnInventory.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnInventory[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int surveyIdCol          = db.getColNumber(SURVEY_ID);
        int dataCentreCol        = db.getColNumber(DATA_CENTRE);
        int projectNameCol       = db.getColNumber(PROJECT_NAME);
        int cruiseNameCol        = db.getColNumber(CRUISE_NAME);
        int nationalPgmCol       = db.getColNumber(NATIONAL_PGM);
        int exchangeRestrictCol  = db.getColNumber(EXCHANGE_RESTRICT);
        int coopPgmCol           = db.getColNumber(COOP_PGM);
        int coordinatedIntCol    = db.getColNumber(COORDINATED_INT);
        int planamCodeCol        = db.getColNumber(PLANAM_CODE);
        int portStartCol         = db.getColNumber(PORT_START);
        int portEndCol           = db.getColNumber(PORT_END);
        int countryCodeCol       = db.getColNumber(COUNTRY_CODE);
        int institCodeCol        = db.getColNumber(INSTIT_CODE);
        int coordCodeCol         = db.getColNumber(COORD_CODE);
        int sciCode1Col          = db.getColNumber(SCI_CODE_1);
        int sciCode2Col          = db.getColNumber(SCI_CODE_2);
        int dateStartCol         = db.getColNumber(DATE_START);
        int dateEndCol           = db.getColNumber(DATE_END);
        int latNorthCol          = db.getColNumber(LAT_NORTH);
        int latSouthCol          = db.getColNumber(LAT_SOUTH);
        int longWestCol          = db.getColNumber(LONG_WEST);
        int longEastCol          = db.getColNumber(LONG_EAST);
        int areanameCol          = db.getColNumber(AREANAME);
        int domainCol            = db.getColNumber(DOMAIN);
        int trackChartCol        = db.getColNumber(TRACK_CHART);
        int targetCountryCodeCol = db.getColNumber(TARGET_COUNTRY_CODE);
        int stnidPrefixCol       = db.getColNumber(STNID_PREFIX);
        int gmtDiffCol           = db.getColNumber(GMT_DIFF);
        int gmtFreezeCol         = db.getColNumber(GMT_FREEZE);
        int projectionCodeCol    = db.getColNumber(PROJECTION_CODE);
        int spheroidCodeCol      = db.getColNumber(SPHEROID_CODE);
        int datumCodeCol         = db.getColNumber(DATUM_CODE);
        int dataRecordedCol      = db.getColNumber(DATA_RECORDED);
        int surveyTypeCodeCol    = db.getColNumber(SURVEY_TYPE_CODE);
        int dataAvailableCol     = db.getColNumber(DATA_AVAILABLE);
        MrnInventory[] cArray = new MrnInventory[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnInventory();
            if (surveyIdCol != -1)
                cArray[i].setSurveyId         ((String) row.elementAt(surveyIdCol));
            if (dataCentreCol != -1)
                cArray[i].setDataCentre       ((String) row.elementAt(dataCentreCol));
            if (projectNameCol != -1)
                cArray[i].setProjectName      ((String) row.elementAt(projectNameCol));
            if (cruiseNameCol != -1)
                cArray[i].setCruiseName       ((String) row.elementAt(cruiseNameCol));
            if (nationalPgmCol != -1)
                cArray[i].setNationalPgm      ((String) row.elementAt(nationalPgmCol));
            if (exchangeRestrictCol != -1)
                cArray[i].setExchangeRestrict ((String) row.elementAt(exchangeRestrictCol));
            if (coopPgmCol != -1)
                cArray[i].setCoopPgm          ((String) row.elementAt(coopPgmCol));
            if (coordinatedIntCol != -1)
                cArray[i].setCoordinatedInt   ((String) row.elementAt(coordinatedIntCol));
            if (planamCodeCol != -1)
                cArray[i].setPlanamCode       ((String) row.elementAt(planamCodeCol));
            if (portStartCol != -1)
                cArray[i].setPortStart        ((String) row.elementAt(portStartCol));
            if (portEndCol != -1)
                cArray[i].setPortEnd          ((String) row.elementAt(portEndCol));
            if (countryCodeCol != -1)
                cArray[i].setCountryCode      ((String) row.elementAt(countryCodeCol));
            if (institCodeCol != -1)
                cArray[i].setInstitCode       ((String) row.elementAt(institCodeCol));
            if (coordCodeCol != -1)
                cArray[i].setCoordCode        ((String) row.elementAt(coordCodeCol));
            if (sciCode1Col != -1)
                cArray[i].setSciCode1         ((String) row.elementAt(sciCode1Col));
            if (sciCode2Col != -1)
                cArray[i].setSciCode2         ((String) row.elementAt(sciCode2Col));
            if (dateStartCol != -1)
                cArray[i].setDateStart        ((String) row.elementAt(dateStartCol));
            if (dateEndCol != -1)
                cArray[i].setDateEnd          ((String) row.elementAt(dateEndCol));
            if (latNorthCol != -1)
                cArray[i].setLatNorth         ((String) row.elementAt(latNorthCol));
            if (latSouthCol != -1)
                cArray[i].setLatSouth         ((String) row.elementAt(latSouthCol));
            if (longWestCol != -1)
                cArray[i].setLongWest         ((String) row.elementAt(longWestCol));
            if (longEastCol != -1)
                cArray[i].setLongEast         ((String) row.elementAt(longEastCol));
            if (areanameCol != -1)
                cArray[i].setAreaname         ((String) row.elementAt(areanameCol));
            if (domainCol != -1)
                cArray[i].setDomain           ((String) row.elementAt(domainCol));
            if (trackChartCol != -1)
                cArray[i].setTrackChart       ((String) row.elementAt(trackChartCol));
            if (targetCountryCodeCol != -1)
                cArray[i].setTargetCountryCode((String) row.elementAt(targetCountryCodeCol));
            if (stnidPrefixCol != -1)
                cArray[i].setStnidPrefix      ((String) row.elementAt(stnidPrefixCol));
            if (gmtDiffCol != -1)
                cArray[i].setGmtDiff          ((String) row.elementAt(gmtDiffCol));
            if (gmtFreezeCol != -1)
                cArray[i].setGmtFreeze        ((String) row.elementAt(gmtFreezeCol));
            if (projectionCodeCol != -1)
                cArray[i].setProjectionCode   ((String) row.elementAt(projectionCodeCol));
            if (spheroidCodeCol != -1)
                cArray[i].setSpheroidCode     ((String) row.elementAt(spheroidCodeCol));
            if (datumCodeCol != -1)
                cArray[i].setDatumCode        ((String) row.elementAt(datumCodeCol));
            if (dataRecordedCol != -1)
                cArray[i].setDataRecorded     ((String) row.elementAt(dataRecordedCol));
            if (surveyTypeCodeCol != -1)
                cArray[i].setSurveyTypeCode   ((String) row.elementAt(surveyTypeCodeCol));
            if (dataAvailableCol != -1)
                cArray[i].setDataAvailable    ((String) row.elementAt(dataAvailableCol));
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
     *     new MrnInventory(
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
     *         <val35>).put();</pre>
     * will insert a record with:
     *     surveyId          = <val1>,
     *     dataCentre        = <val2>,
     *     projectName       = <val3>,
     *     cruiseName        = <val4>,
     *     nationalPgm       = <val5>,
     *     exchangeRestrict  = <val6>,
     *     coopPgm           = <val7>,
     *     coordinatedInt    = <val8>,
     *     planamCode        = <val9>,
     *     portStart         = <val10>,
     *     portEnd           = <val11>,
     *     countryCode       = <val12>,
     *     institCode        = <val13>,
     *     coordCode         = <val14>,
     *     sciCode1          = <val15>,
     *     sciCode2          = <val16>,
     *     dateStart         = <val17>,
     *     dateEnd           = <val18>,
     *     latNorth          = <val19>,
     *     latSouth          = <val20>,
     *     longWest          = <val21>,
     *     longEast          = <val22>,
     *     areaname          = <val23>,
     *     domain            = <val24>,
     *     trackChart        = <val25>,
     *     targetCountryCode = <val26>,
     *     stnidPrefix       = <val27>,
     *     gmtDiff           = <val28>,
     *     gmtFreeze         = <val29>,
     *     projectionCode    = <val30>,
     *     spheroidCode      = <val31>,
     *     datumCode         = <val32>,
     *     dataRecorded      = <val33>,
     *     surveyTypeCode    = <val34>,
     *     dataAvailable     = <val35>.
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
     * Boolean success = new MrnInventory(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.DATENULL,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where dataCentre = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnInventory inventory = new MrnInventory();
     * success = inventory.del(MrnInventory.SURVEY_ID+"=<val1>");</pre>
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
     * update are taken from the MrnInventory argument, .e.g.<pre>
     * Boolean success
     * MrnInventory updMrnInventory = new MrnInventory();
     * updMrnInventory.setDataCentre(<val2>);
     * MrnInventory whereMrnInventory = new MrnInventory(<val1>);
     * success = whereMrnInventory.upd(updMrnInventory);</pre>
     * will update DataCentre to <val2> for all records where
     * surveyId = <val1>.
     * @param inventory  A MrnInventory variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnInventory inventory) {
        return db.update (TABLE, createColVals(inventory), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnInventory updMrnInventory = new MrnInventory();
     * updMrnInventory.setDataCentre(<val2>);
     * MrnInventory whereMrnInventory = new MrnInventory();
     * success = whereMrnInventory.upd(
     *     updMrnInventory, MrnInventory.SURVEY_ID+"=<val1>");</pre>
     * will update DataCentre to <val2> for all records where
     * surveyId = <val1>.
     * @param  updMrnInventory  A MrnInventory variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnInventory inventory, String where) {
        return db.update (TABLE, createColVals(inventory), where);
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
        if (getDataCentre() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATA_CENTRE + "='" + getDataCentre() + "'";
        } // if getDataCentre
        if (getProjectName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PROJECT_NAME + "='" + getProjectName() + "'";
        } // if getProjectName
        if (getCruiseName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CRUISE_NAME + "='" + getCruiseName() + "'";
        } // if getCruiseName
        if (getNationalPgm() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NATIONAL_PGM + "='" + getNationalPgm() + "'";
        } // if getNationalPgm
        if (getExchangeRestrict() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + EXCHANGE_RESTRICT + "='" + getExchangeRestrict() + "'";
        } // if getExchangeRestrict
        if (getCoopPgm() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + COOP_PGM + "='" + getCoopPgm() + "'";
        } // if getCoopPgm
        if (getCoordinatedInt() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + COORDINATED_INT + "='" + getCoordinatedInt() + "'";
        } // if getCoordinatedInt
        if (getPlanamCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLANAM_CODE + "=" + getPlanamCode("");
        } // if getPlanamCode
        if (getPortStart() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PORT_START + "='" + getPortStart() + "'";
        } // if getPortStart
        if (getPortEnd() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PORT_END + "='" + getPortEnd() + "'";
        } // if getPortEnd
        if (getCountryCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + COUNTRY_CODE + "=" + getCountryCode("");
        } // if getCountryCode
        if (getInstitCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTIT_CODE + "=" + getInstitCode("");
        } // if getInstitCode
        if (getCoordCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + COORD_CODE + "=" + getCoordCode("");
        } // if getCoordCode
        if (getSciCode1() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SCI_CODE_1 + "=" + getSciCode1("");
        } // if getSciCode1
        if (getSciCode2() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SCI_CODE_2 + "=" + getSciCode2("");
        } // if getSciCode2
        if (!getDateStart().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_START +
                "=" + Tables.getDateFormat(getDateStart());
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_END +
                "=" + Tables.getDateFormat(getDateEnd());
        } // if getDateEnd
        if (getLatNorth() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LAT_NORTH + "=" + getLatNorth("");
        } // if getLatNorth
        if (getLatSouth() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LAT_SOUTH + "=" + getLatSouth("");
        } // if getLatSouth
        if (getLongWest() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LONG_WEST + "=" + getLongWest("");
        } // if getLongWest
        if (getLongEast() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LONG_EAST + "=" + getLongEast("");
        } // if getLongEast
        if (getAreaname() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + AREANAME + "='" + getAreaname() + "'";
        } // if getAreaname
        if (getDomain() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DOMAIN + "='" + getDomain() + "'";
        } // if getDomain
        if (getTrackChart() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TRACK_CHART + "='" + getTrackChart() + "'";
        } // if getTrackChart
        if (getTargetCountryCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TARGET_COUNTRY_CODE + "=" + getTargetCountryCode("");
        } // if getTargetCountryCode
        if (getStnidPrefix() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STNID_PREFIX + "='" + getStnidPrefix() + "'";
        } // if getStnidPrefix
        if (getGmtDiff() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + GMT_DIFF + "=" + getGmtDiff("");
        } // if getGmtDiff
        if (getGmtFreeze() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + GMT_FREEZE + "='" + getGmtFreeze() + "'";
        } // if getGmtFreeze
        if (getProjectionCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PROJECTION_CODE + "=" + getProjectionCode("");
        } // if getProjectionCode
        if (getSpheroidCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPHEROID_CODE + "=" + getSpheroidCode("");
        } // if getSpheroidCode
        if (getDatumCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATUM_CODE + "=" + getDatumCode("");
        } // if getDatumCode
        if (getDataRecorded() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATA_RECORDED + "='" + getDataRecorded() + "'";
        } // if getDataRecorded
        if (getSurveyTypeCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_TYPE_CODE + "=" + getSurveyTypeCode("");
        } // if getSurveyTypeCode
        if (getDataAvailable() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATA_AVAILABLE + "='" + getDataAvailable() + "'";
        } // if getDataAvailable
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnInventory aVar) {
        String colVals = "";
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getDataCentre() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATA_CENTRE +"=";
            colVals += (aVar.getDataCentre().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDataCentre() + "'");
        } // if aVar.getDataCentre
        if (aVar.getProjectName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PROJECT_NAME +"=";
            colVals += (aVar.getProjectName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getProjectName() + "'");
        } // if aVar.getProjectName
        if (aVar.getCruiseName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CRUISE_NAME +"=";
            colVals += (aVar.getCruiseName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCruiseName() + "'");
        } // if aVar.getCruiseName
        if (aVar.getNationalPgm() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NATIONAL_PGM +"=";
            colVals += (aVar.getNationalPgm().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNationalPgm() + "'");
        } // if aVar.getNationalPgm
        if (aVar.getExchangeRestrict() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += EXCHANGE_RESTRICT +"=";
            colVals += (aVar.getExchangeRestrict().equals(CHARNULL2) ?
                "null" : "'" + aVar.getExchangeRestrict() + "'");
        } // if aVar.getExchangeRestrict
        if (aVar.getCoopPgm() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += COOP_PGM +"=";
            colVals += (aVar.getCoopPgm().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCoopPgm() + "'");
        } // if aVar.getCoopPgm
        if (aVar.getCoordinatedInt() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += COORDINATED_INT +"=";
            colVals += (aVar.getCoordinatedInt().equals(CHARNULL2) ?
                "null" : "'" + aVar.getCoordinatedInt() + "'");
        } // if aVar.getCoordinatedInt
        if (aVar.getPlanamCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLANAM_CODE +"=";
            colVals += (aVar.getPlanamCode() == INTNULL2 ?
                "null" : aVar.getPlanamCode(""));
        } // if aVar.getPlanamCode
        if (aVar.getPortStart() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PORT_START +"=";
            colVals += (aVar.getPortStart().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPortStart() + "'");
        } // if aVar.getPortStart
        if (aVar.getPortEnd() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PORT_END +"=";
            colVals += (aVar.getPortEnd().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPortEnd() + "'");
        } // if aVar.getPortEnd
        if (aVar.getCountryCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += COUNTRY_CODE +"=";
            colVals += (aVar.getCountryCode() == INTNULL2 ?
                "null" : aVar.getCountryCode(""));
        } // if aVar.getCountryCode
        if (aVar.getInstitCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTIT_CODE +"=";
            colVals += (aVar.getInstitCode() == INTNULL2 ?
                "null" : aVar.getInstitCode(""));
        } // if aVar.getInstitCode
        if (aVar.getCoordCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += COORD_CODE +"=";
            colVals += (aVar.getCoordCode() == INTNULL2 ?
                "null" : aVar.getCoordCode(""));
        } // if aVar.getCoordCode
        if (aVar.getSciCode1() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SCI_CODE_1 +"=";
            colVals += (aVar.getSciCode1() == INTNULL2 ?
                "null" : aVar.getSciCode1(""));
        } // if aVar.getSciCode1
        if (aVar.getSciCode2() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SCI_CODE_2 +"=";
            colVals += (aVar.getSciCode2() == INTNULL2 ?
                "null" : aVar.getSciCode2(""));
        } // if aVar.getSciCode2
        if (!aVar.getDateStart().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_START +"=";
            colVals += (aVar.getDateStart().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateStart()));
        } // if aVar.getDateStart
        if (!aVar.getDateEnd().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_END +"=";
            colVals += (aVar.getDateEnd().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateEnd()));
        } // if aVar.getDateEnd
        if (aVar.getLatNorth() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LAT_NORTH +"=";
            colVals += (aVar.getLatNorth() == FLOATNULL2 ?
                "null" : aVar.getLatNorth(""));
        } // if aVar.getLatNorth
        if (aVar.getLatSouth() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LAT_SOUTH +"=";
            colVals += (aVar.getLatSouth() == FLOATNULL2 ?
                "null" : aVar.getLatSouth(""));
        } // if aVar.getLatSouth
        if (aVar.getLongWest() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LONG_WEST +"=";
            colVals += (aVar.getLongWest() == FLOATNULL2 ?
                "null" : aVar.getLongWest(""));
        } // if aVar.getLongWest
        if (aVar.getLongEast() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LONG_EAST +"=";
            colVals += (aVar.getLongEast() == FLOATNULL2 ?
                "null" : aVar.getLongEast(""));
        } // if aVar.getLongEast
        if (aVar.getAreaname() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += AREANAME +"=";
            colVals += (aVar.getAreaname().equals(CHARNULL2) ?
                "null" : "'" + aVar.getAreaname() + "'");
        } // if aVar.getAreaname
        if (aVar.getDomain() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DOMAIN +"=";
            colVals += (aVar.getDomain().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDomain() + "'");
        } // if aVar.getDomain
        if (aVar.getTrackChart() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TRACK_CHART +"=";
            colVals += (aVar.getTrackChart().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTrackChart() + "'");
        } // if aVar.getTrackChart
        if (aVar.getTargetCountryCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TARGET_COUNTRY_CODE +"=";
            colVals += (aVar.getTargetCountryCode() == INTNULL2 ?
                "null" : aVar.getTargetCountryCode(""));
        } // if aVar.getTargetCountryCode
        if (aVar.getStnidPrefix() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STNID_PREFIX +"=";
            colVals += (aVar.getStnidPrefix().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStnidPrefix() + "'");
        } // if aVar.getStnidPrefix
        if (aVar.getGmtDiff() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += GMT_DIFF +"=";
            colVals += (aVar.getGmtDiff() == INTNULL2 ?
                "null" : aVar.getGmtDiff(""));
        } // if aVar.getGmtDiff
        if (aVar.getGmtFreeze() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += GMT_FREEZE +"=";
            colVals += (aVar.getGmtFreeze().equals(CHARNULL2) ?
                "null" : "'" + aVar.getGmtFreeze() + "'");
        } // if aVar.getGmtFreeze
        if (aVar.getProjectionCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PROJECTION_CODE +"=";
            colVals += (aVar.getProjectionCode() == INTNULL2 ?
                "null" : aVar.getProjectionCode(""));
        } // if aVar.getProjectionCode
        if (aVar.getSpheroidCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPHEROID_CODE +"=";
            colVals += (aVar.getSpheroidCode() == INTNULL2 ?
                "null" : aVar.getSpheroidCode(""));
        } // if aVar.getSpheroidCode
        if (aVar.getDatumCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATUM_CODE +"=";
            colVals += (aVar.getDatumCode() == INTNULL2 ?
                "null" : aVar.getDatumCode(""));
        } // if aVar.getDatumCode
        if (aVar.getDataRecorded() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATA_RECORDED +"=";
            colVals += (aVar.getDataRecorded().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDataRecorded() + "'");
        } // if aVar.getDataRecorded
        if (aVar.getSurveyTypeCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_TYPE_CODE +"=";
            colVals += (aVar.getSurveyTypeCode() == INTNULL2 ?
                "null" : aVar.getSurveyTypeCode(""));
        } // if aVar.getSurveyTypeCode
        if (aVar.getDataAvailable() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATA_AVAILABLE +"=";
            colVals += (aVar.getDataAvailable().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDataAvailable() + "'");
        } // if aVar.getDataAvailable
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SURVEY_ID;
        if (getDataCentre() != CHARNULL) {
            columns = columns + "," + DATA_CENTRE;
        } // if getDataCentre
        if (getProjectName() != CHARNULL) {
            columns = columns + "," + PROJECT_NAME;
        } // if getProjectName
        if (getCruiseName() != CHARNULL) {
            columns = columns + "," + CRUISE_NAME;
        } // if getCruiseName
        if (getNationalPgm() != CHARNULL) {
            columns = columns + "," + NATIONAL_PGM;
        } // if getNationalPgm
        if (getExchangeRestrict() != CHARNULL) {
            columns = columns + "," + EXCHANGE_RESTRICT;
        } // if getExchangeRestrict
        if (getCoopPgm() != CHARNULL) {
            columns = columns + "," + COOP_PGM;
        } // if getCoopPgm
        if (getCoordinatedInt() != CHARNULL) {
            columns = columns + "," + COORDINATED_INT;
        } // if getCoordinatedInt
        if (getPlanamCode() != INTNULL) {
            columns = columns + "," + PLANAM_CODE;
        } // if getPlanamCode
        if (getPortStart() != CHARNULL) {
            columns = columns + "," + PORT_START;
        } // if getPortStart
        if (getPortEnd() != CHARNULL) {
            columns = columns + "," + PORT_END;
        } // if getPortEnd
        if (getCountryCode() != INTNULL) {
            columns = columns + "," + COUNTRY_CODE;
        } // if getCountryCode
        if (getInstitCode() != INTNULL) {
            columns = columns + "," + INSTIT_CODE;
        } // if getInstitCode
        if (getCoordCode() != INTNULL) {
            columns = columns + "," + COORD_CODE;
        } // if getCoordCode
        if (getSciCode1() != INTNULL) {
            columns = columns + "," + SCI_CODE_1;
        } // if getSciCode1
        if (getSciCode2() != INTNULL) {
            columns = columns + "," + SCI_CODE_2;
        } // if getSciCode2
        if (!getDateStart().equals(DATENULL)) {
            columns = columns + "," + DATE_START;
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            columns = columns + "," + DATE_END;
        } // if getDateEnd
        if (getLatNorth() != FLOATNULL) {
            columns = columns + "," + LAT_NORTH;
        } // if getLatNorth
        if (getLatSouth() != FLOATNULL) {
            columns = columns + "," + LAT_SOUTH;
        } // if getLatSouth
        if (getLongWest() != FLOATNULL) {
            columns = columns + "," + LONG_WEST;
        } // if getLongWest
        if (getLongEast() != FLOATNULL) {
            columns = columns + "," + LONG_EAST;
        } // if getLongEast
        if (getAreaname() != CHARNULL) {
            columns = columns + "," + AREANAME;
        } // if getAreaname
        if (getDomain() != CHARNULL) {
            columns = columns + "," + DOMAIN;
        } // if getDomain
        if (getTrackChart() != CHARNULL) {
            columns = columns + "," + TRACK_CHART;
        } // if getTrackChart
        if (getTargetCountryCode() != INTNULL) {
            columns = columns + "," + TARGET_COUNTRY_CODE;
        } // if getTargetCountryCode
        if (getStnidPrefix() != CHARNULL) {
            columns = columns + "," + STNID_PREFIX;
        } // if getStnidPrefix
        if (getGmtDiff() != INTNULL) {
            columns = columns + "," + GMT_DIFF;
        } // if getGmtDiff
        if (getGmtFreeze() != CHARNULL) {
            columns = columns + "," + GMT_FREEZE;
        } // if getGmtFreeze
        if (getProjectionCode() != INTNULL) {
            columns = columns + "," + PROJECTION_CODE;
        } // if getProjectionCode
        if (getSpheroidCode() != INTNULL) {
            columns = columns + "," + SPHEROID_CODE;
        } // if getSpheroidCode
        if (getDatumCode() != INTNULL) {
            columns = columns + "," + DATUM_CODE;
        } // if getDatumCode
        if (getDataRecorded() != CHARNULL) {
            columns = columns + "," + DATA_RECORDED;
        } // if getDataRecorded
        if (getSurveyTypeCode() != INTNULL) {
            columns = columns + "," + SURVEY_TYPE_CODE;
        } // if getSurveyTypeCode
        if (getDataAvailable() != CHARNULL) {
            columns = columns + "," + DATA_AVAILABLE;
        } // if getDataAvailable
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getSurveyId() + "'";
        if (getDataCentre() != CHARNULL) {
            values  = values  + ",'" + getDataCentre() + "'";
        } // if getDataCentre
        if (getProjectName() != CHARNULL) {
            values  = values  + ",'" + getProjectName() + "'";
        } // if getProjectName
        if (getCruiseName() != CHARNULL) {
            values  = values  + ",'" + getCruiseName() + "'";
        } // if getCruiseName
        if (getNationalPgm() != CHARNULL) {
            values  = values  + ",'" + getNationalPgm() + "'";
        } // if getNationalPgm
        if (getExchangeRestrict() != CHARNULL) {
            values  = values  + ",'" + getExchangeRestrict() + "'";
        } // if getExchangeRestrict
        if (getCoopPgm() != CHARNULL) {
            values  = values  + ",'" + getCoopPgm() + "'";
        } // if getCoopPgm
        if (getCoordinatedInt() != CHARNULL) {
            values  = values  + ",'" + getCoordinatedInt() + "'";
        } // if getCoordinatedInt
        if (getPlanamCode() != INTNULL) {
            values  = values  + "," + getPlanamCode("");
        } // if getPlanamCode
        if (getPortStart() != CHARNULL) {
            values  = values  + ",'" + getPortStart() + "'";
        } // if getPortStart
        if (getPortEnd() != CHARNULL) {
            values  = values  + ",'" + getPortEnd() + "'";
        } // if getPortEnd
        if (getCountryCode() != INTNULL) {
            values  = values  + "," + getCountryCode("");
        } // if getCountryCode
        if (getInstitCode() != INTNULL) {
            values  = values  + "," + getInstitCode("");
        } // if getInstitCode
        if (getCoordCode() != INTNULL) {
            values  = values  + "," + getCoordCode("");
        } // if getCoordCode
        if (getSciCode1() != INTNULL) {
            values  = values  + "," + getSciCode1("");
        } // if getSciCode1
        if (getSciCode2() != INTNULL) {
            values  = values  + "," + getSciCode2("");
        } // if getSciCode2
        if (!getDateStart().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateStart());
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateEnd());
        } // if getDateEnd
        if (getLatNorth() != FLOATNULL) {
            values  = values  + "," + getLatNorth("");
        } // if getLatNorth
        if (getLatSouth() != FLOATNULL) {
            values  = values  + "," + getLatSouth("");
        } // if getLatSouth
        if (getLongWest() != FLOATNULL) {
            values  = values  + "," + getLongWest("");
        } // if getLongWest
        if (getLongEast() != FLOATNULL) {
            values  = values  + "," + getLongEast("");
        } // if getLongEast
        if (getAreaname() != CHARNULL) {
            values  = values  + ",'" + getAreaname() + "'";
        } // if getAreaname
        if (getDomain() != CHARNULL) {
            values  = values  + ",'" + getDomain() + "'";
        } // if getDomain
        if (getTrackChart() != CHARNULL) {
            values  = values  + ",'" + getTrackChart() + "'";
        } // if getTrackChart
        if (getTargetCountryCode() != INTNULL) {
            values  = values  + "," + getTargetCountryCode("");
        } // if getTargetCountryCode
        if (getStnidPrefix() != CHARNULL) {
            values  = values  + ",'" + getStnidPrefix() + "'";
        } // if getStnidPrefix
        if (getGmtDiff() != INTNULL) {
            values  = values  + "," + getGmtDiff("");
        } // if getGmtDiff
        if (getGmtFreeze() != CHARNULL) {
            values  = values  + ",'" + getGmtFreeze() + "'";
        } // if getGmtFreeze
        if (getProjectionCode() != INTNULL) {
            values  = values  + "," + getProjectionCode("");
        } // if getProjectionCode
        if (getSpheroidCode() != INTNULL) {
            values  = values  + "," + getSpheroidCode("");
        } // if getSpheroidCode
        if (getDatumCode() != INTNULL) {
            values  = values  + "," + getDatumCode("");
        } // if getDatumCode
        if (getDataRecorded() != CHARNULL) {
            values  = values  + ",'" + getDataRecorded() + "'";
        } // if getDataRecorded
        if (getSurveyTypeCode() != INTNULL) {
            values  = values  + "," + getSurveyTypeCode("");
        } // if getSurveyTypeCode
        if (getDataAvailable() != CHARNULL) {
            values  = values  + ",'" + getDataAvailable() + "'";
        } // if getDataAvailable
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
            getDataCentre("")        + "|" +
            getProjectName("")       + "|" +
            getCruiseName("")        + "|" +
            getNationalPgm("")       + "|" +
            getExchangeRestrict("")  + "|" +
            getCoopPgm("")           + "|" +
            getCoordinatedInt("")    + "|" +
            getPlanamCode("")        + "|" +
            getPortStart("")         + "|" +
            getPortEnd("")           + "|" +
            getCountryCode("")       + "|" +
            getInstitCode("")        + "|" +
            getCoordCode("")         + "|" +
            getSciCode1("")          + "|" +
            getSciCode2("")          + "|" +
            getDateStart("")         + "|" +
            getDateEnd("")           + "|" +
            getLatNorth("")          + "|" +
            getLatSouth("")          + "|" +
            getLongWest("")          + "|" +
            getLongEast("")          + "|" +
            getAreaname("")          + "|" +
            getDomain("")            + "|" +
            getTrackChart("")        + "|" +
            getTargetCountryCode("") + "|" +
            getStnidPrefix("")       + "|" +
            getGmtDiff("")           + "|" +
            getGmtFreeze("")         + "|" +
            getProjectionCode("")    + "|" +
            getSpheroidCode("")      + "|" +
            getDatumCode("")         + "|" +
            getDataRecorded("")      + "|" +
            getSurveyTypeCode("")    + "|" +
            getDataAvailable("")     + "|";
    } // method toString

} // class MrnInventory
