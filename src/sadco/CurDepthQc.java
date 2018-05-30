package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the CUR_DEPTH_QC table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 090309 - SIT Group
 * @version
 * 090309 - GenTableClassB class - create class<br>
 */
public class CurDepthQc extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "CUR_DEPTH_QC";
    /** The depthCode field name */
    public static final String DEPTH_CODE = "DEPTH_CODE";
    /** The parameterCode field name */
    public static final String PARAMETER_CODE = "PARAMETER_CODE";
    /** The personChecked field name */
    public static final String PERSON_CHECKED = "PERSON_CHECKED";
    /** The dateChecked field name */
    public static final String DATE_CHECKED = "DATE_CHECKED";
    /** The broadrange field name */
    public static final String BROADRANGE = "BROADRANGE";
    /** The spikes field name */
    public static final String SPIKES = "SPIKES";
    /** The sensordrift field name */
    public static final String SENSORDRIFT = "SENSORDRIFT";
    /** The gaps field name */
    public static final String GAPS = "GAPS";
    /** The leadertrailer field name */
    public static final String LEADERTRAILER = "LEADERTRAILER";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       depthCode;
    private int       parameterCode;
    private String    personChecked;
    private Timestamp dateChecked;
    private String    broadrange;
    private String    spikes;
    private String    sensordrift;
    private String    gaps;
    private String    leadertrailer;

    /** The error variables  */
    private int depthCodeError     = ERROR_NORMAL;
    private int parameterCodeError = ERROR_NORMAL;
    private int personCheckedError = ERROR_NORMAL;
    private int dateCheckedError   = ERROR_NORMAL;
    private int broadrangeError    = ERROR_NORMAL;
    private int spikesError        = ERROR_NORMAL;
    private int sensordriftError   = ERROR_NORMAL;
    private int gapsError          = ERROR_NORMAL;
    private int leadertrailerError = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String depthCodeErrorMessage     = "";
    private String parameterCodeErrorMessage = "";
    private String personCheckedErrorMessage = "";
    private String dateCheckedErrorMessage   = "";
    private String broadrangeErrorMessage    = "";
    private String spikesErrorMessage        = "";
    private String sensordriftErrorMessage   = "";
    private String gapsErrorMessage          = "";
    private String leadertrailerErrorMessage = "";

    /** The min-max constants for all numerics */
    public static final int       DEPTH_CODE_MN = INTMIN;
    public static final int       DEPTH_CODE_MX = INTMAX;
    public static final int       PARAMETER_CODE_MN = INTMIN;
    public static final int       PARAMETER_CODE_MX = INTMAX;
    public static final Timestamp DATE_CHECKED_MN = DATEMIN;
    public static final Timestamp DATE_CHECKED_MX = DATEMAX;

    /** The exceptions for non-Strings */
    Exception depthCodeOutOfBoundsException =
        new Exception ("'depthCode' out of bounds: " +
            DEPTH_CODE_MN + " - " + DEPTH_CODE_MX);
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
    public CurDepthQc() {
        clearVars();
        if (dbg) System.out.println ("<br>in CurDepthQc constructor 1"); // debug
    } // CurDepthQc constructor

    /**
     * Instantiate a CurDepthQc object and initialize the instance variables.
     * @param depthCode  The depthCode (int)
     */
    public CurDepthQc(
            int depthCode) {
        this();
        setDepthCode     (depthCode    );
        if (dbg) System.out.println ("<br>in CurDepthQc constructor 2"); // debug
    } // CurDepthQc constructor

    /**
     * Instantiate a CurDepthQc object and initialize the instance variables.
     * @param depthCode      The depthCode     (int)
     * @param parameterCode  The parameterCode (int)
     * @param personChecked  The personChecked (String)
     * @param dateChecked    The dateChecked   (java.util.Date)
     * @param broadrange     The broadrange    (String)
     * @param spikes         The spikes        (String)
     * @param sensordrift    The sensordrift   (String)
     * @param gaps           The gaps          (String)
     * @param leadertrailer  The leadertrailer (String)
     * @return A CurDepthQc object
     */
    public CurDepthQc(
            int            depthCode,
            int            parameterCode,
            String         personChecked,
            java.util.Date dateChecked,
            String         broadrange,
            String         spikes,
            String         sensordrift,
            String         gaps,
            String         leadertrailer) {
        this();
        setDepthCode     (depthCode    );
        setParameterCode (parameterCode);
        setPersonChecked (personChecked);
        setDateChecked   (dateChecked  );
        setBroadrange    (broadrange   );
        setSpikes        (spikes       );
        setSensordrift   (sensordrift  );
        setGaps          (gaps         );
        setLeadertrailer (leadertrailer);
        if (dbg) System.out.println ("<br>in CurDepthQc constructor 3"); // debug
    } // CurDepthQc constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setDepthCode     (INTNULL );
        setParameterCode (INTNULL );
        setPersonChecked (CHARNULL);
        setDateChecked   (DATENULL);
        setBroadrange    (CHARNULL);
        setSpikes        (CHARNULL);
        setSensordrift   (CHARNULL);
        setGaps          (CHARNULL);
        setLeadertrailer (CHARNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (int)
     */
    public int setDepthCode(int depthCode) {
        try {
            if ( ((depthCode == INTNULL) ||
                  (depthCode == INTNULL2)) ||
                !((depthCode < DEPTH_CODE_MN) ||
                  (depthCode > DEPTH_CODE_MX)) ) {
                this.depthCode = depthCode;
                depthCodeError = ERROR_NORMAL;
            } else {
                throw depthCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (Integer)
     */
    public int setDepthCode(Integer depthCode) {
        try {
            setDepthCode(depthCode.intValue());
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (Float)
     */
    public int setDepthCode(Float depthCode) {
        try {
            if (depthCode.floatValue() == FLOATNULL) {
                setDepthCode(INTNULL);
            } else {
                setDepthCode(depthCode.intValue());
            } // if (depthCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (String)
     */
    public int setDepthCode(String depthCode) {
        try {
            setDepthCode(new Integer(depthCode).intValue());
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Called when an exception has occured
     * @param  depthCode (String)
     */
    private void setDepthCodeError (int depthCode, Exception e, int error) {
        this.depthCode = depthCode;
        depthCodeErrorMessage = e.toString();
        depthCodeError = error;
    } // method setDepthCodeError


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
     * Set the 'leadertrailer' class variable
     * @param  leadertrailer (String)
     */
    public int setLeadertrailer(String leadertrailer) {
        try {
            this.leadertrailer = leadertrailer;
            if (this.leadertrailer != CHARNULL) {
                this.leadertrailer = stripCRLF(this.leadertrailer.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>leadertrailer = " + this.leadertrailer);
        } catch (Exception e) {
            setLeadertrailerError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return leadertrailerError;
    } // method setLeadertrailer

    /**
     * Called when an exception has occured
     * @param  leadertrailer (String)
     */
    private void setLeadertrailerError (String leadertrailer, Exception e, int error) {
        this.leadertrailer = leadertrailer;
        leadertrailerErrorMessage = e.toString();
        leadertrailerError = error;
    } // method setLeadertrailerError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'depthCode' class variable
     * @return depthCode (int)
     */
    public int getDepthCode() {
        return depthCode;
    } // method getDepthCode

    /**
     * Return the 'depthCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDepthCode methods
     * @return depthCode (String)
     */
    public String getDepthCode(String s) {
        return ((depthCode != INTNULL) ? new Integer(depthCode).toString() : "");
    } // method getDepthCode


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
     * Return the 'leadertrailer' class variable
     * @return leadertrailer (String)
     */
    public String getLeadertrailer() {
        return leadertrailer;
    } // method getLeadertrailer

    /**
     * Return the 'leadertrailer' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLeadertrailer methods
     * @return leadertrailer (String)
     */
    public String getLeadertrailer(String s) {
        return (leadertrailer != CHARNULL ? leadertrailer.replace('"','\'') : "");
    } // method getLeadertrailer


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
        if ((depthCode == INTNULL) &&
            (parameterCode == INTNULL) &&
            (personChecked == CHARNULL) &&
            (dateChecked.equals(DATENULL)) &&
            (broadrange == CHARNULL) &&
            (spikes == CHARNULL) &&
            (sensordrift == CHARNULL) &&
            (gaps == CHARNULL) &&
            (leadertrailer == CHARNULL)) {
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
        int sumError = depthCodeError +
            parameterCodeError +
            personCheckedError +
            dateCheckedError +
            broadrangeError +
            spikesError +
            sensordriftError +
            gapsError +
            leadertrailerError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the depthCode instance variable
     * @return errorcode (int)
     */
    public int getDepthCodeError() {
        return depthCodeError;
    } // method getDepthCodeError

    /**
     * Gets the errorMessage for the depthCode instance variable
     * @return errorMessage (String)
     */
    public String getDepthCodeErrorMessage() {
        return depthCodeErrorMessage;
    } // method getDepthCodeErrorMessage


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
     * Gets the errorcode for the leadertrailer instance variable
     * @return errorcode (int)
     */
    public int getLeadertrailerError() {
        return leadertrailerError;
    } // method getLeadertrailerError

    /**
     * Gets the errorMessage for the leadertrailer instance variable
     * @return errorMessage (String)
     */
    public String getLeadertrailerErrorMessage() {
        return leadertrailerErrorMessage;
    } // method getLeadertrailerErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of CurDepthQc. e.g.<pre>
     * CurDepthQc curDepthQc = new CurDepthQc(<val1>);
     * CurDepthQc curDepthQcArray[] = curDepthQc.get();</pre>
     * will get the CurDepthQc record where depthCode = <val1>.
     * @return Array of CurDepthQc (CurDepthQc[])
     */
    public CurDepthQc[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * CurDepthQc curDepthQcArray[] =
     *     new CurDepthQc().get(CurDepthQc.DEPTH_CODE+"=<val1>");</pre>
     * will get the CurDepthQc record where depthCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of CurDepthQc (CurDepthQc[])
     */
    public CurDepthQc[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * CurDepthQc curDepthQcArray[] =
     *     new CurDepthQc().get("1=1",curDepthQc.DEPTH_CODE);</pre>
     * will get the all the CurDepthQc records, and order them by depthCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurDepthQc (CurDepthQc[])
     */
    public CurDepthQc[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = CurDepthQc.DEPTH_CODE,CurDepthQc.PARAMETER_CODE;
     * String where = CurDepthQc.DEPTH_CODE + "=<val1";
     * String order = CurDepthQc.DEPTH_CODE;
     * CurDepthQc curDepthQcArray[] =
     *     new CurDepthQc().get(columns, where, order);</pre>
     * will get the depthCode and parameterCode colums of all CurDepthQc records,
     * where depthCode = <val1>, and order them by depthCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurDepthQc (CurDepthQc[])
     */
    public CurDepthQc[] get (String fields, String where, String order) {
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
     * and transforms it into an array of CurDepthQc.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private CurDepthQc[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int depthCodeCol     = db.getColNumber(DEPTH_CODE);
        int parameterCodeCol = db.getColNumber(PARAMETER_CODE);
        int personCheckedCol = db.getColNumber(PERSON_CHECKED);
        int dateCheckedCol   = db.getColNumber(DATE_CHECKED);
        int broadrangeCol    = db.getColNumber(BROADRANGE);
        int spikesCol        = db.getColNumber(SPIKES);
        int sensordriftCol   = db.getColNumber(SENSORDRIFT);
        int gapsCol          = db.getColNumber(GAPS);
        int leadertrailerCol = db.getColNumber(LEADERTRAILER);
        CurDepthQc[] cArray = new CurDepthQc[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new CurDepthQc();
            if (depthCodeCol != -1)
                cArray[i].setDepthCode    ((String) row.elementAt(depthCodeCol));
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
            if (leadertrailerCol != -1)
                cArray[i].setLeadertrailer((String) row.elementAt(leadertrailerCol));
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
     *     new CurDepthQc(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>,
     *         <val9>).put();</pre>
     * will insert a record with:
     *     depthCode     = <val1>,
     *     parameterCode = <val2>,
     *     personChecked = <val3>,
     *     dateChecked   = <val4>,
     *     broadrange    = <val5>,
     *     spikes        = <val6>,
     *     sensordrift   = <val7>,
     *     gaps          = <val8>,
     *     leadertrailer = <val9>.
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
     * Boolean success = new CurDepthQc(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.DATENULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where parameterCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurDepthQc curDepthQc = new CurDepthQc();
     * success = curDepthQc.del(CurDepthQc.DEPTH_CODE+"=<val1>");</pre>
     * will delete all records where depthCode = <val1>.
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
     * update are taken from the CurDepthQc argument, .e.g.<pre>
     * Boolean success
     * CurDepthQc updCurDepthQc = new CurDepthQc();
     * updCurDepthQc.setParameterCode(<val2>);
     * CurDepthQc whereCurDepthQc = new CurDepthQc(<val1>);
     * success = whereCurDepthQc.upd(updCurDepthQc);</pre>
     * will update ParameterCode to <val2> for all records where
     * depthCode = <val1>.
     * @param curDepthQc  A CurDepthQc variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(CurDepthQc curDepthQc) {
        return db.update (TABLE, createColVals(curDepthQc), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurDepthQc updCurDepthQc = new CurDepthQc();
     * updCurDepthQc.setParameterCode(<val2>);
     * CurDepthQc whereCurDepthQc = new CurDepthQc();
     * success = whereCurDepthQc.upd(
     *     updCurDepthQc, CurDepthQc.DEPTH_CODE+"=<val1>");</pre>
     * will update ParameterCode to <val2> for all records where
     * depthCode = <val1>.
     * @param  updCurDepthQc  A CurDepthQc variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(CurDepthQc curDepthQc, String where) {
        return db.update (TABLE, createColVals(curDepthQc), where);
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
        if (getDepthCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEPTH_CODE + "=" + getDepthCode("");
        } // if getDepthCode
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
        if (getLeadertrailer() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LEADERTRAILER + "='" + getLeadertrailer() + "'";
        } // if getLeadertrailer
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(CurDepthQc aVar) {
        String colVals = "";
        if (aVar.getDepthCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEPTH_CODE +"=";
            colVals += (aVar.getDepthCode() == INTNULL2 ?
                "null" : aVar.getDepthCode(""));
        } // if aVar.getDepthCode
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
        if (aVar.getLeadertrailer() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LEADERTRAILER +"=";
            colVals += (aVar.getLeadertrailer().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLeadertrailer() + "'");
        } // if aVar.getLeadertrailer
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = DEPTH_CODE;
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
        if (getLeadertrailer() != CHARNULL) {
            columns = columns + "," + LEADERTRAILER;
        } // if getLeadertrailer
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getDepthCode("");
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
        if (getLeadertrailer() != CHARNULL) {
            values  = values  + ",'" + getLeadertrailer() + "'";
        } // if getLeadertrailer
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getDepthCode("")     + "|" +
            getParameterCode("") + "|" +
            getPersonChecked("") + "|" +
            getDateChecked("")   + "|" +
            getBroadrange("")    + "|" +
            getSpikes("")        + "|" +
            getSensordrift("")   + "|" +
            getGaps("")          + "|" +
            getLeadertrailer("") + "|";
    } // method toString

} // class CurDepthQc
