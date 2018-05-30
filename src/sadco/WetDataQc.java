package sadco;

import java.sql.Timestamp;
import java.util.Vector;

/**
 * This class manages the wet_data_qc table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 20140625 - SIT Group
 * @version
 * 20140625 - GenTableClassB class - create class<br>
 */
public class WetDataQc extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "wet_data_qc";
    /** The stationId field name */
    public static final String STATION_ID = "station_id";
    /** The year field name */
    public static final String YEAR = "year";
    /** The parameterCode field name */
    public static final String PARAMETER_CODE = "parameter_code";
    /** The personChecked field name */
    public static final String PERSON_CHECKED = "person_checked";
    /** The dateChecked field name */
    public static final String DATE_CHECKED = "date_checked";
    /** The broadrange field name */
    public static final String BROADRANGE = "broadrange";
    /** The spikes field name */
    public static final String SPIKES = "spikes";
    /** The sensordrift field name */
    public static final String SENSORDRIFT = "sensordrift";
    /** The gaps field name */
    public static final String GAPS = "gaps";
    /** The shift field name */
    public static final String SHIFT = "shift";
    /** The sensorjam field name */
    public static final String SENSORJAM = "sensorjam";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private int       year;
    private int       parameterCode;
    private String    personChecked;
    private Timestamp dateChecked;
    private String    broadrange;
    private String    spikes;
    private String    sensordrift;
    private String    gaps;
    private String    shift;
    private String    sensorjam;

    /** The error variables  */
    private int stationIdError     = ERROR_NORMAL;
    private int yearError          = ERROR_NORMAL;
    private int parameterCodeError = ERROR_NORMAL;
    private int personCheckedError = ERROR_NORMAL;
    private int dateCheckedError   = ERROR_NORMAL;
    private int broadrangeError    = ERROR_NORMAL;
    private int spikesError        = ERROR_NORMAL;
    private int sensordriftError   = ERROR_NORMAL;
    private int gapsError          = ERROR_NORMAL;
    private int shiftError         = ERROR_NORMAL;
    private int sensorjamError     = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage     = "";
    private String yearErrorMessage          = "";
    private String parameterCodeErrorMessage = "";
    private String personCheckedErrorMessage = "";
    private String dateCheckedErrorMessage   = "";
    private String broadrangeErrorMessage    = "";
    private String spikesErrorMessage        = "";
    private String sensordriftErrorMessage   = "";
    private String gapsErrorMessage          = "";
    private String shiftErrorMessage         = "";
    private String sensorjamErrorMessage     = "";

    /** The min-max constants for all numerics */
    public static final int       YEAR_MN = INTMIN;
    public static final int       YEAR_MX = INTMAX;
    public static final int       PARAMETER_CODE_MN = INTMIN;
    public static final int       PARAMETER_CODE_MX = INTMAX;
    public static final Timestamp DATE_CHECKED_MN = DATEMIN;
    public static final Timestamp DATE_CHECKED_MX = DATEMAX;

    /** The exceptions for non-Strings */
    Exception yearOutOfBoundsException =
        new Exception ("'year' out of bounds: " +
            YEAR_MN + " - " + YEAR_MX);
    Exception parameterCodeOutOfBoundsException =
        new Exception ("'parameterCode' out of bounds: " +
            PARAMETER_CODE_MN + " - " + PARAMETER_CODE_MX);
    Exception dateCheckedException =
        new Exception ("'dateChecked' is null");
    Exception dateCheckedOutOfBoundsException =
        new Exception ("'dateChecked' out of bounds: " +
            DATE_CHECKED_MN + " - " + DATE_CHECKED_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public WetDataQc() {
        clearVars();
        if (dbg) System.out.println ("<br>in WetDataQc constructor 1"); // debug
    } // WetDataQc constructor

    /**
     * Instantiate a WetDataQc object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public WetDataQc(
            String stationId) {
        this();
        setStationId     (stationId    );
        if (dbg) System.out.println ("<br>in WetDataQc constructor 2"); // debug
    } // WetDataQc constructor

    /**
     * Instantiate a WetDataQc object and initialize the instance variables.
     * @param stationId      The stationId     (String)
     * @param year           The year          (int)
     * @param parameterCode  The parameterCode (int)
     * @param personChecked  The personChecked (String)
     * @param dateChecked    The dateChecked   (java.util.Date)
     * @param broadrange     The broadrange    (String)
     * @param spikes         The spikes        (String)
     * @param sensordrift    The sensordrift   (String)
     * @param gaps           The gaps          (String)
     * @param shift          The shift         (String)
     * @param sensorjam      The sensorjam     (String)
     * @return A WetDataQc object
     */
    public WetDataQc(
            String         stationId,
            int            year,
            int            parameterCode,
            String         personChecked,
            java.util.Date dateChecked,
            String         broadrange,
            String         spikes,
            String         sensordrift,
            String         gaps,
            String         shift,
            String         sensorjam) {
        this();
        setStationId     (stationId    );
        setYear          (year         );
        setParameterCode (parameterCode);
        setPersonChecked (personChecked);
        setDateChecked   (dateChecked  );
        setBroadrange    (broadrange   );
        setSpikes        (spikes       );
        setSensordrift   (sensordrift  );
        setGaps          (gaps         );
        setShift         (shift        );
        setSensorjam     (sensorjam    );
        if (dbg) System.out.println ("<br>in WetDataQc constructor 3"); // debug
    } // WetDataQc constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId     (CHARNULL);
        setYear          (INTNULL );
        setParameterCode (INTNULL );
        setPersonChecked (CHARNULL);
        setDateChecked   (DATENULL);
        setBroadrange    (CHARNULL);
        setSpikes        (CHARNULL);
        setSensordrift   (CHARNULL);
        setGaps          (CHARNULL);
        setShift         (CHARNULL);
        setSensorjam     (CHARNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'stationId' class variable
     * @param  stationId (String)
     */
    public int setStationId(String stationId) {
        try {
            this.stationId = stationId;
            if (this.stationId != CHARNULL) {
                this.stationId = stripCRLF(this.stationId.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>stationId = " + this.stationId);
        } catch (Exception e) {
            setStationIdError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return stationIdError;
    } // method setStationId

    /**
     * Called when an exception has occured
     * @param  stationId (String)
     */
    private void setStationIdError (String stationId, Exception e, int error) {
        this.stationId = stationId;
        stationIdErrorMessage = e.toString();
        stationIdError = error;
    } // method setStationIdError


    /**
     * Set the 'year' class variable
     * @param  year (int)
     */
    public int setYear(int year) {
        try {
            if ( ((year == INTNULL) ||
                  (year == INTNULL2)) ||
                !((year < YEAR_MN) ||
                  (year > YEAR_MX)) ) {
                this.year = year;
                yearError = ERROR_NORMAL;
            } else {
                throw yearOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setYearError(INTNULL, e, ERROR_LOCAL);
        } // try
        return yearError;
    } // method setYear

    /**
     * Set the 'year' class variable
     * @param  year (Integer)
     */
    public int setYear(Integer year) {
        try {
            setYear(year.intValue());
        } catch (Exception e) {
            setYearError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return yearError;
    } // method setYear

    /**
     * Set the 'year' class variable
     * @param  year (Float)
     */
    public int setYear(Float year) {
        try {
            if (year.floatValue() == FLOATNULL) {
                setYear(INTNULL);
            } else {
                setYear(year.intValue());
            } // if (year.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setYearError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return yearError;
    } // method setYear

    /**
     * Set the 'year' class variable
     * @param  year (String)
     */
    public int setYear(String year) {
        try {
            setYear(new Integer(year).intValue());
        } catch (Exception e) {
            setYearError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return yearError;
    } // method setYear

    /**
     * Called when an exception has occured
     * @param  year (String)
     */
    private void setYearError (int year, Exception e, int error) {
        this.year = year;
        yearErrorMessage = e.toString();
        yearError = error;
    } // method setYearError


    /**
     * Set the 'parameterCode' class variable
     * @param  parameterCode (int)
     */
    public int setParameterCode(int parameterCode) {
        try {
            if ( ((parameterCode == INTNULL) ||
                  (parameterCode == INTNULL2)) ||
                !((parameterCode < PARAMETER_CODE_MN) ||
                  (parameterCode > PARAMETER_CODE_MX)) ) {
                this.parameterCode = parameterCode;
                parameterCodeError = ERROR_NORMAL;
            } else {
                throw parameterCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setParameterCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return parameterCodeError;
    } // method setParameterCode

    /**
     * Set the 'parameterCode' class variable
     * @param  parameterCode (Integer)
     */
    public int setParameterCode(Integer parameterCode) {
        try {
            setParameterCode(parameterCode.intValue());
        } catch (Exception e) {
            setParameterCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return parameterCodeError;
    } // method setParameterCode

    /**
     * Set the 'parameterCode' class variable
     * @param  parameterCode (Float)
     */
    public int setParameterCode(Float parameterCode) {
        try {
            if (parameterCode.floatValue() == FLOATNULL) {
                setParameterCode(INTNULL);
            } else {
                setParameterCode(parameterCode.intValue());
            } // if (parameterCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setParameterCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return parameterCodeError;
    } // method setParameterCode

    /**
     * Set the 'parameterCode' class variable
     * @param  parameterCode (String)
     */
    public int setParameterCode(String parameterCode) {
        try {
            setParameterCode(new Integer(parameterCode).intValue());
        } catch (Exception e) {
            setParameterCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return parameterCodeError;
    } // method setParameterCode

    /**
     * Called when an exception has occured
     * @param  parameterCode (String)
     */
    private void setParameterCodeError (int parameterCode, Exception e, int error) {
        this.parameterCode = parameterCode;
        parameterCodeErrorMessage = e.toString();
        parameterCodeError = error;
    } // method setParameterCodeError


    /**
     * Set the 'personChecked' class variable
     * @param  personChecked (String)
     */
    public int setPersonChecked(String personChecked) {
        try {
            this.personChecked = personChecked;
            if (this.personChecked != CHARNULL) {
                this.personChecked = stripCRLF(this.personChecked.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>personChecked = " + this.personChecked);
        } catch (Exception e) {
            setPersonCheckedError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return personCheckedError;
    } // method setPersonChecked

    /**
     * Called when an exception has occured
     * @param  personChecked (String)
     */
    private void setPersonCheckedError (String personChecked, Exception e, int error) {
        this.personChecked = personChecked;
        personCheckedErrorMessage = e.toString();
        personCheckedError = error;
    } // method setPersonCheckedError


    /**
     * Set the 'dateChecked' class variable
     * @param  dateChecked (Timestamp)
     */
    public int setDateChecked(Timestamp dateChecked) {
        try {
            if (dateChecked == null) { this.dateChecked = DATENULL; }
            if ( (dateChecked.equals(DATENULL) ||
                  dateChecked.equals(DATENULL2)) ||
                !(dateChecked.before(DATE_CHECKED_MN) ||
                  dateChecked.after(DATE_CHECKED_MX)) ) {
                this.dateChecked = dateChecked;
                dateCheckedError = ERROR_NORMAL;
            } else {
                throw dateCheckedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateCheckedError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateCheckedError;
    } // method setDateChecked

    /**
     * Set the 'dateChecked' class variable
     * @param  dateChecked (java.util.Date)
     */
    public int setDateChecked(java.util.Date dateChecked) {
        try {
            setDateChecked(new Timestamp(dateChecked.getTime()));
        } catch (Exception e) {
            setDateCheckedError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateCheckedError;
    } // method setDateChecked

    /**
     * Set the 'dateChecked' class variable
     * @param  dateChecked (String)
     */
    public int setDateChecked(String dateChecked) {
        try {
            int len = dateChecked.length();
            switch (len) {
            // date &/or times
                case 4: dateChecked += "-01";
                case 7: dateChecked += "-01";
                case 10: dateChecked += " 00";
                case 13: dateChecked += ":00";
                case 16: dateChecked += ":00"; break;
                // times only
                case 5: dateChecked = "1800-01-01 " + dateChecked + ":00"; break;
                case 8: dateChecked = "1800-01-01 " + dateChecked; break;
            } // switch
            if (dbg) System.out.println ("dateChecked = " + dateChecked);
            setDateChecked(Timestamp.valueOf(dateChecked));
        } catch (Exception e) {
            setDateCheckedError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateCheckedError;
    } // method setDateChecked

    /**
     * Called when an exception has occured
     * @param  dateChecked (String)
     */
    private void setDateCheckedError (Timestamp dateChecked, Exception e, int error) {
        this.dateChecked = dateChecked;
        dateCheckedErrorMessage = e.toString();
        dateCheckedError = error;
    } // method setDateCheckedError


    /**
     * Set the 'broadrange' class variable
     * @param  broadrange (String)
     */
    public int setBroadrange(String broadrange) {
        try {
            this.broadrange = broadrange;
            if (this.broadrange != CHARNULL) {
                this.broadrange = stripCRLF(this.broadrange.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>broadrange = " + this.broadrange);
        } catch (Exception e) {
            setBroadrangeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return broadrangeError;
    } // method setBroadrange

    /**
     * Called when an exception has occured
     * @param  broadrange (String)
     */
    private void setBroadrangeError (String broadrange, Exception e, int error) {
        this.broadrange = broadrange;
        broadrangeErrorMessage = e.toString();
        broadrangeError = error;
    } // method setBroadrangeError


    /**
     * Set the 'spikes' class variable
     * @param  spikes (String)
     */
    public int setSpikes(String spikes) {
        try {
            this.spikes = spikes;
            if (this.spikes != CHARNULL) {
                this.spikes = stripCRLF(this.spikes.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>spikes = " + this.spikes);
        } catch (Exception e) {
            setSpikesError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return spikesError;
    } // method setSpikes

    /**
     * Called when an exception has occured
     * @param  spikes (String)
     */
    private void setSpikesError (String spikes, Exception e, int error) {
        this.spikes = spikes;
        spikesErrorMessage = e.toString();
        spikesError = error;
    } // method setSpikesError


    /**
     * Set the 'sensordrift' class variable
     * @param  sensordrift (String)
     */
    public int setSensordrift(String sensordrift) {
        try {
            this.sensordrift = sensordrift;
            if (this.sensordrift != CHARNULL) {
                this.sensordrift = stripCRLF(this.sensordrift.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>sensordrift = " + this.sensordrift);
        } catch (Exception e) {
            setSensordriftError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return sensordriftError;
    } // method setSensordrift

    /**
     * Called when an exception has occured
     * @param  sensordrift (String)
     */
    private void setSensordriftError (String sensordrift, Exception e, int error) {
        this.sensordrift = sensordrift;
        sensordriftErrorMessage = e.toString();
        sensordriftError = error;
    } // method setSensordriftError


    /**
     * Set the 'gaps' class variable
     * @param  gaps (String)
     */
    public int setGaps(String gaps) {
        try {
            this.gaps = gaps;
            if (this.gaps != CHARNULL) {
                this.gaps = stripCRLF(this.gaps.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>gaps = " + this.gaps);
        } catch (Exception e) {
            setGapsError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return gapsError;
    } // method setGaps

    /**
     * Called when an exception has occured
     * @param  gaps (String)
     */
    private void setGapsError (String gaps, Exception e, int error) {
        this.gaps = gaps;
        gapsErrorMessage = e.toString();
        gapsError = error;
    } // method setGapsError


    /**
     * Set the 'shift' class variable
     * @param  shift (String)
     */
    public int setShift(String shift) {
        try {
            this.shift = shift;
            if (this.shift != CHARNULL) {
                this.shift = stripCRLF(this.shift.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>shift = " + this.shift);
        } catch (Exception e) {
            setShiftError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return shiftError;
    } // method setShift

    /**
     * Called when an exception has occured
     * @param  shift (String)
     */
    private void setShiftError (String shift, Exception e, int error) {
        this.shift = shift;
        shiftErrorMessage = e.toString();
        shiftError = error;
    } // method setShiftError


    /**
     * Set the 'sensorjam' class variable
     * @param  sensorjam (String)
     */
    public int setSensorjam(String sensorjam) {
        try {
            this.sensorjam = sensorjam;
            if (this.sensorjam != CHARNULL) {
                this.sensorjam = stripCRLF(this.sensorjam.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>sensorjam = " + this.sensorjam);
        } catch (Exception e) {
            setSensorjamError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return sensorjamError;
    } // method setSensorjam

    /**
     * Called when an exception has occured
     * @param  sensorjam (String)
     */
    private void setSensorjamError (String sensorjam, Exception e, int error) {
        this.sensorjam = sensorjam;
        sensorjamErrorMessage = e.toString();
        sensorjamError = error;
    } // method setSensorjamError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'stationId' class variable
     * @return stationId (String)
     */
    public String getStationId() {
        return stationId;
    } // method getStationId

    /**
     * Return the 'stationId' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStationId methods
     * @return stationId (String)
     */
    public String getStationId(String s) {
        return (stationId != CHARNULL ? stationId.replace('"','\'') : "");
    } // method getStationId


    /**
     * Return the 'year' class variable
     * @return year (int)
     */
    public int getYear() {
        return year;
    } // method getYear

    /**
     * Return the 'year' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getYear methods
     * @return year (String)
     */
    public String getYear(String s) {
        return ((year != INTNULL) ? new Integer(year).toString() : "");
    } // method getYear


    /**
     * Return the 'parameterCode' class variable
     * @return parameterCode (int)
     */
    public int getParameterCode() {
        return parameterCode;
    } // method getParameterCode

    /**
     * Return the 'parameterCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getParameterCode methods
     * @return parameterCode (String)
     */
    public String getParameterCode(String s) {
        return ((parameterCode != INTNULL) ? new Integer(parameterCode).toString() : "");
    } // method getParameterCode


    /**
     * Return the 'personChecked' class variable
     * @return personChecked (String)
     */
    public String getPersonChecked() {
        return personChecked;
    } // method getPersonChecked

    /**
     * Return the 'personChecked' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPersonChecked methods
     * @return personChecked (String)
     */
    public String getPersonChecked(String s) {
        return (personChecked != CHARNULL ? personChecked.replace('"','\'') : "");
    } // method getPersonChecked


    /**
     * Return the 'dateChecked' class variable
     * @return dateChecked (Timestamp)
     */
    public Timestamp getDateChecked() {
        return dateChecked;
    } // method getDateChecked

    /**
     * Return the 'dateChecked' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateChecked methods
     * @return dateChecked (String)
     */
    public String getDateChecked(String s) {
        if (dateChecked.equals(DATENULL)) {
            return ("");
        } else {
            String dateCheckedStr = dateChecked.toString();
            return dateCheckedStr.substring(0,dateCheckedStr.indexOf('.'));
        } // if
    } // method getDateChecked


    /**
     * Return the 'broadrange' class variable
     * @return broadrange (String)
     */
    public String getBroadrange() {
        return broadrange;
    } // method getBroadrange

    /**
     * Return the 'broadrange' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getBroadrange methods
     * @return broadrange (String)
     */
    public String getBroadrange(String s) {
        return (broadrange != CHARNULL ? broadrange.replace('"','\'') : "");
    } // method getBroadrange


    /**
     * Return the 'spikes' class variable
     * @return spikes (String)
     */
    public String getSpikes() {
        return spikes;
    } // method getSpikes

    /**
     * Return the 'spikes' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpikes methods
     * @return spikes (String)
     */
    public String getSpikes(String s) {
        return (spikes != CHARNULL ? spikes.replace('"','\'') : "");
    } // method getSpikes


    /**
     * Return the 'sensordrift' class variable
     * @return sensordrift (String)
     */
    public String getSensordrift() {
        return sensordrift;
    } // method getSensordrift

    /**
     * Return the 'sensordrift' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSensordrift methods
     * @return sensordrift (String)
     */
    public String getSensordrift(String s) {
        return (sensordrift != CHARNULL ? sensordrift.replace('"','\'') : "");
    } // method getSensordrift


    /**
     * Return the 'gaps' class variable
     * @return gaps (String)
     */
    public String getGaps() {
        return gaps;
    } // method getGaps

    /**
     * Return the 'gaps' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getGaps methods
     * @return gaps (String)
     */
    public String getGaps(String s) {
        return (gaps != CHARNULL ? gaps.replace('"','\'') : "");
    } // method getGaps


    /**
     * Return the 'shift' class variable
     * @return shift (String)
     */
    public String getShift() {
        return shift;
    } // method getShift

    /**
     * Return the 'shift' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getShift methods
     * @return shift (String)
     */
    public String getShift(String s) {
        return (shift != CHARNULL ? shift.replace('"','\'') : "");
    } // method getShift


    /**
     * Return the 'sensorjam' class variable
     * @return sensorjam (String)
     */
    public String getSensorjam() {
        return sensorjam;
    } // method getSensorjam

    /**
     * Return the 'sensorjam' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSensorjam methods
     * @return sensorjam (String)
     */
    public String getSensorjam(String s) {
        return (sensorjam != CHARNULL ? sensorjam.replace('"','\'') : "");
    } // method getSensorjam


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
        if ((stationId == CHARNULL) &&
            (year == INTNULL) &&
            (parameterCode == INTNULL) &&
            (personChecked == CHARNULL) &&
            (dateChecked.equals(DATENULL)) &&
            (broadrange == CHARNULL) &&
            (spikes == CHARNULL) &&
            (sensordrift == CHARNULL) &&
            (gaps == CHARNULL) &&
            (shift == CHARNULL) &&
            (sensorjam == CHARNULL)) {
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
        int sumError = stationIdError +
            yearError +
            parameterCodeError +
            personCheckedError +
            dateCheckedError +
            broadrangeError +
            spikesError +
            sensordriftError +
            gapsError +
            shiftError +
            sensorjamError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the stationId instance variable
     * @return errorcode (int)
     */
    public int getStationIdError() {
        return stationIdError;
    } // method getStationIdError

    /**
     * Gets the errorMessage for the stationId instance variable
     * @return errorMessage (String)
     */
    public String getStationIdErrorMessage() {
        return stationIdErrorMessage;
    } // method getStationIdErrorMessage


    /**
     * Gets the errorcode for the year instance variable
     * @return errorcode (int)
     */
    public int getYearError() {
        return yearError;
    } // method getYearError

    /**
     * Gets the errorMessage for the year instance variable
     * @return errorMessage (String)
     */
    public String getYearErrorMessage() {
        return yearErrorMessage;
    } // method getYearErrorMessage


    /**
     * Gets the errorcode for the parameterCode instance variable
     * @return errorcode (int)
     */
    public int getParameterCodeError() {
        return parameterCodeError;
    } // method getParameterCodeError

    /**
     * Gets the errorMessage for the parameterCode instance variable
     * @return errorMessage (String)
     */
    public String getParameterCodeErrorMessage() {
        return parameterCodeErrorMessage;
    } // method getParameterCodeErrorMessage


    /**
     * Gets the errorcode for the personChecked instance variable
     * @return errorcode (int)
     */
    public int getPersonCheckedError() {
        return personCheckedError;
    } // method getPersonCheckedError

    /**
     * Gets the errorMessage for the personChecked instance variable
     * @return errorMessage (String)
     */
    public String getPersonCheckedErrorMessage() {
        return personCheckedErrorMessage;
    } // method getPersonCheckedErrorMessage


    /**
     * Gets the errorcode for the dateChecked instance variable
     * @return errorcode (int)
     */
    public int getDateCheckedError() {
        return dateCheckedError;
    } // method getDateCheckedError

    /**
     * Gets the errorMessage for the dateChecked instance variable
     * @return errorMessage (String)
     */
    public String getDateCheckedErrorMessage() {
        return dateCheckedErrorMessage;
    } // method getDateCheckedErrorMessage


    /**
     * Gets the errorcode for the broadrange instance variable
     * @return errorcode (int)
     */
    public int getBroadrangeError() {
        return broadrangeError;
    } // method getBroadrangeError

    /**
     * Gets the errorMessage for the broadrange instance variable
     * @return errorMessage (String)
     */
    public String getBroadrangeErrorMessage() {
        return broadrangeErrorMessage;
    } // method getBroadrangeErrorMessage


    /**
     * Gets the errorcode for the spikes instance variable
     * @return errorcode (int)
     */
    public int getSpikesError() {
        return spikesError;
    } // method getSpikesError

    /**
     * Gets the errorMessage for the spikes instance variable
     * @return errorMessage (String)
     */
    public String getSpikesErrorMessage() {
        return spikesErrorMessage;
    } // method getSpikesErrorMessage


    /**
     * Gets the errorcode for the sensordrift instance variable
     * @return errorcode (int)
     */
    public int getSensordriftError() {
        return sensordriftError;
    } // method getSensordriftError

    /**
     * Gets the errorMessage for the sensordrift instance variable
     * @return errorMessage (String)
     */
    public String getSensordriftErrorMessage() {
        return sensordriftErrorMessage;
    } // method getSensordriftErrorMessage


    /**
     * Gets the errorcode for the gaps instance variable
     * @return errorcode (int)
     */
    public int getGapsError() {
        return gapsError;
    } // method getGapsError

    /**
     * Gets the errorMessage for the gaps instance variable
     * @return errorMessage (String)
     */
    public String getGapsErrorMessage() {
        return gapsErrorMessage;
    } // method getGapsErrorMessage


    /**
     * Gets the errorcode for the shift instance variable
     * @return errorcode (int)
     */
    public int getShiftError() {
        return shiftError;
    } // method getShiftError

    /**
     * Gets the errorMessage for the shift instance variable
     * @return errorMessage (String)
     */
    public String getShiftErrorMessage() {
        return shiftErrorMessage;
    } // method getShiftErrorMessage


    /**
     * Gets the errorcode for the sensorjam instance variable
     * @return errorcode (int)
     */
    public int getSensorjamError() {
        return sensorjamError;
    } // method getSensorjamError

    /**
     * Gets the errorMessage for the sensorjam instance variable
     * @return errorMessage (String)
     */
    public String getSensorjamErrorMessage() {
        return sensorjamErrorMessage;
    } // method getSensorjamErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of WetDataQc. e.g.<pre>
     * WetDataQc wetDataQc = new WetDataQc(<val1>);
     * WetDataQc wetDataQcArray[] = wetDataQc.get();</pre>
     * will get the WetDataQc record where stationId = <val1>.
     * @return Array of WetDataQc (WetDataQc[])
     */
    public WetDataQc[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * WetDataQc wetDataQcArray[] =
     *     new WetDataQc().get(WetDataQc.STATION_ID+"=<val1>");</pre>
     * will get the WetDataQc record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of WetDataQc (WetDataQc[])
     */
    public WetDataQc[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * WetDataQc wetDataQcArray[] =
     *     new WetDataQc().get("1=1",wetDataQc.STATION_ID);</pre>
     * will get the all the WetDataQc records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetDataQc (WetDataQc[])
     */
    public WetDataQc[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = WetDataQc.STATION_ID,WetDataQc.YEAR;
     * String where = WetDataQc.STATION_ID + "=<val1";
     * String order = WetDataQc.STATION_ID;
     * WetDataQc wetDataQcArray[] =
     *     new WetDataQc().get(columns, where, order);</pre>
     * will get the stationId and year colums of all WetDataQc records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetDataQc (WetDataQc[])
     */
    public WetDataQc[] get (String fields, String where, String order) {
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
     * and transforms it into an array of WetDataQc.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private WetDataQc[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol     = db.getColNumber(STATION_ID);
        int yearCol          = db.getColNumber(YEAR);
        int parameterCodeCol = db.getColNumber(PARAMETER_CODE);
        int personCheckedCol = db.getColNumber(PERSON_CHECKED);
        int dateCheckedCol   = db.getColNumber(DATE_CHECKED);
        int broadrangeCol    = db.getColNumber(BROADRANGE);
        int spikesCol        = db.getColNumber(SPIKES);
        int sensordriftCol   = db.getColNumber(SENSORDRIFT);
        int gapsCol          = db.getColNumber(GAPS);
        int shiftCol         = db.getColNumber(SHIFT);
        int sensorjamCol     = db.getColNumber(SENSORJAM);
        WetDataQc[] cArray = new WetDataQc[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new WetDataQc();
            if (stationIdCol != -1)
                cArray[i].setStationId    ((String) row.elementAt(stationIdCol));
            if (yearCol != -1)
                cArray[i].setYear         ((String) row.elementAt(yearCol));
            if (parameterCodeCol != -1)
                cArray[i].setParameterCode((String) row.elementAt(parameterCodeCol));
            if (personCheckedCol != -1)
                cArray[i].setPersonChecked((String) row.elementAt(personCheckedCol));
            if (dateCheckedCol != -1)
                cArray[i].setDateChecked  ((String) row.elementAt(dateCheckedCol));
            if (broadrangeCol != -1)
                cArray[i].setBroadrange   ((String) row.elementAt(broadrangeCol));
            if (spikesCol != -1)
                cArray[i].setSpikes       ((String) row.elementAt(spikesCol));
            if (sensordriftCol != -1)
                cArray[i].setSensordrift  ((String) row.elementAt(sensordriftCol));
            if (gapsCol != -1)
                cArray[i].setGaps         ((String) row.elementAt(gapsCol));
            if (shiftCol != -1)
                cArray[i].setShift        ((String) row.elementAt(shiftCol));
            if (sensorjamCol != -1)
                cArray[i].setSensorjam    ((String) row.elementAt(sensorjamCol));
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
     *     new WetDataQc(
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
     *         <val11>).put();</pre>
     * will insert a record with:
     *     stationId     = <val1>,
     *     year          = <val2>,
     *     parameterCode = <val3>,
     *     personChecked = <val4>,
     *     dateChecked   = <val5>,
     *     broadrange    = <val6>,
     *     spikes        = <val7>,
     *     sensordrift   = <val8>,
     *     gaps          = <val9>,
     *     shift         = <val10>,
     *     sensorjam     = <val11>.
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
     * Boolean success = new WetDataQc(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.DATENULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where year = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WetDataQc wetDataQc = new WetDataQc();
     * success = wetDataQc.del(WetDataQc.STATION_ID+"=<val1>");</pre>
     * will delete all records where stationId = <val1>.
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
     * update are taken from the WetDataQc argument, .e.g.<pre>
     * Boolean success
     * WetDataQc updWetDataQc = new WetDataQc();
     * updWetDataQc.setYear(<val2>);
     * WetDataQc whereWetDataQc = new WetDataQc(<val1>);
     * success = whereWetDataQc.upd(updWetDataQc);</pre>
     * will update Year to <val2> for all records where
     * stationId = <val1>.
     * @param wetDataQc  A WetDataQc variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(WetDataQc wetDataQc) {
        return db.update (TABLE, createColVals(wetDataQc), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WetDataQc updWetDataQc = new WetDataQc();
     * updWetDataQc.setYear(<val2>);
     * WetDataQc whereWetDataQc = new WetDataQc();
     * success = whereWetDataQc.upd(
     *     updWetDataQc, WetDataQc.STATION_ID+"=<val1>");</pre>
     * will update Year to <val2> for all records where
     * stationId = <val1>.
     * @param  updWetDataQc  A WetDataQc variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(WetDataQc wetDataQc, String where) {
        return db.update (TABLE, createColVals(wetDataQc), where);
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
        if (getStationId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATION_ID + "='" + getStationId() + "'";
        } // if getStationId
        if (getYear() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + YEAR + "=" + getYear("");
        } // if getYear
        if (getParameterCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PARAMETER_CODE + "=" + getParameterCode("");
        } // if getParameterCode
        if (getPersonChecked() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PERSON_CHECKED + "='" + getPersonChecked() + "'";
        } // if getPersonChecked
        if (!getDateChecked().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_CHECKED +
                "=" + Tables.getDateFormat(getDateChecked());
        } // if getDateChecked
        if (getBroadrange() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + BROADRANGE + "='" + getBroadrange() + "'";
        } // if getBroadrange
        if (getSpikes() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPIKES + "='" + getSpikes() + "'";
        } // if getSpikes
        if (getSensordrift() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SENSORDRIFT + "='" + getSensordrift() + "'";
        } // if getSensordrift
        if (getGaps() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + GAPS + "='" + getGaps() + "'";
        } // if getGaps
        if (getShift() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SHIFT + "='" + getShift() + "'";
        } // if getShift
        if (getSensorjam() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SENSORJAM + "='" + getSensorjam() + "'";
        } // if getSensorjam
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(WetDataQc aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getYear() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += YEAR +"=";
            colVals += (aVar.getYear() == INTNULL2 ?
                "null" : aVar.getYear(""));
        } // if aVar.getYear
        if (aVar.getParameterCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PARAMETER_CODE +"=";
            colVals += (aVar.getParameterCode() == INTNULL2 ?
                "null" : aVar.getParameterCode(""));
        } // if aVar.getParameterCode
        if (aVar.getPersonChecked() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PERSON_CHECKED +"=";
            colVals += (aVar.getPersonChecked().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPersonChecked() + "'");
        } // if aVar.getPersonChecked
        if (!aVar.getDateChecked().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_CHECKED +"=";
            colVals += (aVar.getDateChecked().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateChecked()));
        } // if aVar.getDateChecked
        if (aVar.getBroadrange() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += BROADRANGE +"=";
            colVals += (aVar.getBroadrange().equals(CHARNULL2) ?
                "null" : "'" + aVar.getBroadrange() + "'");
        } // if aVar.getBroadrange
        if (aVar.getSpikes() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPIKES +"=";
            colVals += (aVar.getSpikes().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSpikes() + "'");
        } // if aVar.getSpikes
        if (aVar.getSensordrift() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SENSORDRIFT +"=";
            colVals += (aVar.getSensordrift().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSensordrift() + "'");
        } // if aVar.getSensordrift
        if (aVar.getGaps() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += GAPS +"=";
            colVals += (aVar.getGaps().equals(CHARNULL2) ?
                "null" : "'" + aVar.getGaps() + "'");
        } // if aVar.getGaps
        if (aVar.getShift() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SHIFT +"=";
            colVals += (aVar.getShift().equals(CHARNULL2) ?
                "null" : "'" + aVar.getShift() + "'");
        } // if aVar.getShift
        if (aVar.getSensorjam() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SENSORJAM +"=";
            colVals += (aVar.getSensorjam().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSensorjam() + "'");
        } // if aVar.getSensorjam
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (getYear() != INTNULL) {
            columns = columns + "," + YEAR;
        } // if getYear
        if (getParameterCode() != INTNULL) {
            columns = columns + "," + PARAMETER_CODE;
        } // if getParameterCode
        if (getPersonChecked() != CHARNULL) {
            columns = columns + "," + PERSON_CHECKED;
        } // if getPersonChecked
        if (!getDateChecked().equals(DATENULL)) {
            columns = columns + "," + DATE_CHECKED;
        } // if getDateChecked
        if (getBroadrange() != CHARNULL) {
            columns = columns + "," + BROADRANGE;
        } // if getBroadrange
        if (getSpikes() != CHARNULL) {
            columns = columns + "," + SPIKES;
        } // if getSpikes
        if (getSensordrift() != CHARNULL) {
            columns = columns + "," + SENSORDRIFT;
        } // if getSensordrift
        if (getGaps() != CHARNULL) {
            columns = columns + "," + GAPS;
        } // if getGaps
        if (getShift() != CHARNULL) {
            columns = columns + "," + SHIFT;
        } // if getShift
        if (getSensorjam() != CHARNULL) {
            columns = columns + "," + SENSORJAM;
        } // if getSensorjam
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (getYear() != INTNULL) {
            values  = values  + "," + getYear("");
        } // if getYear
        if (getParameterCode() != INTNULL) {
            values  = values  + "," + getParameterCode("");
        } // if getParameterCode
        if (getPersonChecked() != CHARNULL) {
            values  = values  + ",'" + getPersonChecked() + "'";
        } // if getPersonChecked
        if (!getDateChecked().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateChecked());
        } // if getDateChecked
        if (getBroadrange() != CHARNULL) {
            values  = values  + ",'" + getBroadrange() + "'";
        } // if getBroadrange
        if (getSpikes() != CHARNULL) {
            values  = values  + ",'" + getSpikes() + "'";
        } // if getSpikes
        if (getSensordrift() != CHARNULL) {
            values  = values  + ",'" + getSensordrift() + "'";
        } // if getSensordrift
        if (getGaps() != CHARNULL) {
            values  = values  + ",'" + getGaps() + "'";
        } // if getGaps
        if (getShift() != CHARNULL) {
            values  = values  + ",'" + getShift() + "'";
        } // if getShift
        if (getSensorjam() != CHARNULL) {
            values  = values  + ",'" + getSensorjam() + "'";
        } // if getSensorjam
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getStationId("")     + "|" +
            getYear("")          + "|" +
            getParameterCode("") + "|" +
            getPersonChecked("") + "|" +
            getDateChecked("")   + "|" +
            getBroadrange("")    + "|" +
            getSpikes("")        + "|" +
            getSensordrift("")   + "|" +
            getGaps("")          + "|" +
            getShift("")         + "|" +
            getSensorjam("")     + "|";
    } // method toString

} // class WetDataQc
