package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the CUR_DEPTH table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 090512 - SIT Group
 * @version
 * 090512 - GenTableClassB class - create class<br>
 */
public class CurDepth extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "CUR_DEPTH";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The mooringCode field name */
    public static final String MOORING_CODE = "MOORING_CODE";
    /** The spldep field name */
    public static final String SPLDEP = "SPLDEP";
    /** The instrumentNumber field name */
    public static final String INSTRUMENT_NUMBER = "INSTRUMENT_NUMBER";
    /** The deploymentNumber field name */
    public static final String DEPLOYMENT_NUMBER = "DEPLOYMENT_NUMBER";
    /** The dateTimeStart field name */
    public static final String DATE_TIME_START = "DATE_TIME_START";
    /** The dateTimeEnd field name */
    public static final String DATE_TIME_END = "DATE_TIME_END";
    /** The timeInterval field name */
    public static final String TIME_INTERVAL = "TIME_INTERVAL";
    /** The numberOfRecords field name */
    public static final String NUMBER_OF_RECORDS = "NUMBER_OF_RECORDS";
    /** The passkey field name */
    public static final String PASSKEY = "PASSKEY";
    /** The dateLoaded field name */
    public static final String DATE_LOADED = "DATE_LOADED";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The parameters field name */
    public static final String PARAMETERS = "PARAMETERS";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private int       mooringCode;
    private float     spldep;
    private int       instrumentNumber;
    private String    deploymentNumber;
    private Timestamp dateTimeStart;
    private Timestamp dateTimeEnd;
    private int       timeInterval;
    private int       numberOfRecords;
    private String    passkey;
    private Timestamp dateLoaded;
    private String    surveyId;
    private String    parameters;

    /** The error variables  */
    private int codeError             = ERROR_NORMAL;
    private int mooringCodeError      = ERROR_NORMAL;
    private int spldepError           = ERROR_NORMAL;
    private int instrumentNumberError = ERROR_NORMAL;
    private int deploymentNumberError = ERROR_NORMAL;
    private int dateTimeStartError    = ERROR_NORMAL;
    private int dateTimeEndError      = ERROR_NORMAL;
    private int timeIntervalError     = ERROR_NORMAL;
    private int numberOfRecordsError  = ERROR_NORMAL;
    private int passkeyError          = ERROR_NORMAL;
    private int dateLoadedError       = ERROR_NORMAL;
    private int surveyIdError         = ERROR_NORMAL;
    private int parametersError       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage             = "";
    private String mooringCodeErrorMessage      = "";
    private String spldepErrorMessage           = "";
    private String instrumentNumberErrorMessage = "";
    private String deploymentNumberErrorMessage = "";
    private String dateTimeStartErrorMessage    = "";
    private String dateTimeEndErrorMessage      = "";
    private String timeIntervalErrorMessage     = "";
    private String numberOfRecordsErrorMessage  = "";
    private String passkeyErrorMessage          = "";
    private String dateLoadedErrorMessage       = "";
    private String surveyIdErrorMessage         = "";
    private String parametersErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       MOORING_CODE_MN = INTMIN;
    public static final int       MOORING_CODE_MX = INTMAX;
    public static final float     SPLDEP_MN = FLOATMIN;
    public static final float     SPLDEP_MX = FLOATMAX;
    public static final int       INSTRUMENT_NUMBER_MN = INTMIN;
    public static final int       INSTRUMENT_NUMBER_MX = INTMAX;
    public static final Timestamp DATE_TIME_START_MN = DATEMIN;
    public static final Timestamp DATE_TIME_START_MX = DATEMAX;
    public static final Timestamp DATE_TIME_END_MN = DATEMIN;
    public static final Timestamp DATE_TIME_END_MX = DATEMAX;
    public static final int       TIME_INTERVAL_MN = INTMIN;
    public static final int       TIME_INTERVAL_MX = INTMAX;
    public static final int       NUMBER_OF_RECORDS_MN = INTMIN;
    public static final int       NUMBER_OF_RECORDS_MX = INTMAX;
    public static final Timestamp DATE_LOADED_MN = DATEMIN;
    public static final Timestamp DATE_LOADED_MX = DATEMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception mooringCodeOutOfBoundsException =
        new Exception ("'mooringCode' out of bounds: " +
            MOORING_CODE_MN + " - " + MOORING_CODE_MX);
    Exception spldepOutOfBoundsException =
        new Exception ("'spldep' out of bounds: " +
            SPLDEP_MN + " - " + SPLDEP_MX);
    Exception instrumentNumberOutOfBoundsException =
        new Exception ("'instrumentNumber' out of bounds: " +
            INSTRUMENT_NUMBER_MN + " - " + INSTRUMENT_NUMBER_MX);
    Exception dateTimeStartException =
        new Exception ("'dateTimeStart' is null");
    Exception dateTimeStartOutOfBoundsException =
        new Exception ("'dateTimeStart' out of bounds: " +
            DATE_TIME_START_MN + " - " + DATE_TIME_START_MX);
    Exception dateTimeEndException =
        new Exception ("'dateTimeEnd' is null");
    Exception dateTimeEndOutOfBoundsException =
        new Exception ("'dateTimeEnd' out of bounds: " +
            DATE_TIME_END_MN + " - " + DATE_TIME_END_MX);
    Exception timeIntervalOutOfBoundsException =
        new Exception ("'timeInterval' out of bounds: " +
            TIME_INTERVAL_MN + " - " + TIME_INTERVAL_MX);
    Exception numberOfRecordsOutOfBoundsException =
        new Exception ("'numberOfRecords' out of bounds: " +
            NUMBER_OF_RECORDS_MN + " - " + NUMBER_OF_RECORDS_MX);
    Exception dateLoadedException =
        new Exception ("'dateLoaded' is null");
    Exception dateLoadedOutOfBoundsException =
        new Exception ("'dateLoaded' out of bounds: " +
            DATE_LOADED_MN + " - " + DATE_LOADED_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public CurDepth() {
        clearVars();
        if (dbg) System.out.println ("<br>in CurDepth constructor 1"); // debug
    } // CurDepth constructor

    /**
     * Instantiate a CurDepth object and initialize the instance variables.
     * @param code  The code (int)
     */
    public CurDepth(
            int code) {
        this();
        setCode             (code            );
        if (dbg) System.out.println ("<br>in CurDepth constructor 2"); // debug
    } // CurDepth constructor

    /**
     * Instantiate a CurDepth object and initialize the instance variables.
     * @param code              The code             (int)
     * @param mooringCode       The mooringCode      (int)
     * @param spldep            The spldep           (float)
     * @param instrumentNumber  The instrumentNumber (int)
     * @param deploymentNumber  The deploymentNumber (String)
     * @param dateTimeStart     The dateTimeStart    (java.util.Date)
     * @param dateTimeEnd       The dateTimeEnd      (java.util.Date)
     * @param timeInterval      The timeInterval     (int)
     * @param numberOfRecords   The numberOfRecords  (int)
     * @param passkey           The passkey          (String)
     * @param dateLoaded        The dateLoaded       (java.util.Date)
     * @param surveyId          The surveyId         (String)
     * @param parameters        The parameters       (String)
     * @return A CurDepth object
     */
    public CurDepth(
            int            code,
            int            mooringCode,
            float          spldep,
            int            instrumentNumber,
            String         deploymentNumber,
            java.util.Date dateTimeStart,
            java.util.Date dateTimeEnd,
            int            timeInterval,
            int            numberOfRecords,
            String         passkey,
            java.util.Date dateLoaded,
            String         surveyId,
            String         parameters) {
        this();
        setCode             (code            );
        setMooringCode      (mooringCode     );
        setSpldep           (spldep          );
        setInstrumentNumber (instrumentNumber);
        setDeploymentNumber (deploymentNumber);
        setDateTimeStart    (dateTimeStart   );
        setDateTimeEnd      (dateTimeEnd     );
        setTimeInterval     (timeInterval    );
        setNumberOfRecords  (numberOfRecords );
        setPasskey          (passkey         );
        setDateLoaded       (dateLoaded      );
        setSurveyId         (surveyId        );
        setParameters       (parameters      );
        if (dbg) System.out.println ("<br>in CurDepth constructor 3"); // debug
    } // CurDepth constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode             (INTNULL  );
        setMooringCode      (INTNULL  );
        setSpldep           (FLOATNULL);
        setInstrumentNumber (INTNULL  );
        setDeploymentNumber (CHARNULL );
        setDateTimeStart    (DATENULL );
        setDateTimeEnd      (DATENULL );
        setTimeInterval     (INTNULL  );
        setNumberOfRecords  (INTNULL  );
        setPasskey          (CHARNULL );
        setDateLoaded       (DATENULL );
        setSurveyId         (CHARNULL );
        setParameters       (CHARNULL );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'code' class variable
     * @param  code (int)
     */
    public int setCode(int code) {
        try {
            if ( ((code == INTNULL) ||
                  (code == INTNULL2)) ||
                !((code < CODE_MN) ||
                  (code > CODE_MX)) ) {
                this.code = code;
                codeError = ERROR_NORMAL;
            } else {
                throw codeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (Integer)
     */
    public int setCode(Integer code) {
        try {
            setCode(code.intValue());
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (Float)
     */
    public int setCode(Float code) {
        try {
            if (code.floatValue() == FLOATNULL) {
                setCode(INTNULL);
            } else {
                setCode(code.intValue());
            } // if (code.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (String)
     */
    public int setCode(String code) {
        try {
            setCode(new Integer(code).intValue());
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Called when an exception has occured
     * @param  code (String)
     */
    private void setCodeError (int code, Exception e, int error) {
        this.code = code;
        codeErrorMessage = e.toString();
        codeError = error;
    } // method setCodeError


    /**
     * Set the 'mooringCode' class variable
     * @param  mooringCode (int)
     */
    public int setMooringCode(int mooringCode) {
        try {
            if ( ((mooringCode == INTNULL) ||
                  (mooringCode == INTNULL2)) ||
                !((mooringCode < MOORING_CODE_MN) ||
                  (mooringCode > MOORING_CODE_MX)) ) {
                this.mooringCode = mooringCode;
                mooringCodeError = ERROR_NORMAL;
            } else {
                throw mooringCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMooringCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return mooringCodeError;
    } // method setMooringCode

    /**
     * Set the 'mooringCode' class variable
     * @param  mooringCode (Integer)
     */
    public int setMooringCode(Integer mooringCode) {
        try {
            setMooringCode(mooringCode.intValue());
        } catch (Exception e) {
            setMooringCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return mooringCodeError;
    } // method setMooringCode

    /**
     * Set the 'mooringCode' class variable
     * @param  mooringCode (Float)
     */
    public int setMooringCode(Float mooringCode) {
        try {
            if (mooringCode.floatValue() == FLOATNULL) {
                setMooringCode(INTNULL);
            } else {
                setMooringCode(mooringCode.intValue());
            } // if (mooringCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMooringCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return mooringCodeError;
    } // method setMooringCode

    /**
     * Set the 'mooringCode' class variable
     * @param  mooringCode (String)
     */
    public int setMooringCode(String mooringCode) {
        try {
            setMooringCode(new Integer(mooringCode).intValue());
        } catch (Exception e) {
            setMooringCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return mooringCodeError;
    } // method setMooringCode

    /**
     * Called when an exception has occured
     * @param  mooringCode (String)
     */
    private void setMooringCodeError (int mooringCode, Exception e, int error) {
        this.mooringCode = mooringCode;
        mooringCodeErrorMessage = e.toString();
        mooringCodeError = error;
    } // method setMooringCodeError


    /**
     * Set the 'spldep' class variable
     * @param  spldep (float)
     */
    public int setSpldep(float spldep) {
        try {
            if ( ((spldep == FLOATNULL) ||
                  (spldep == FLOATNULL2)) ||
                !((spldep < SPLDEP_MN) ||
                  (spldep > SPLDEP_MX)) ) {
                this.spldep = spldep;
                spldepError = ERROR_NORMAL;
            } else {
                throw spldepOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (Integer)
     */
    public int setSpldep(Integer spldep) {
        try {
            setSpldep(spldep.floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (Float)
     */
    public int setSpldep(Float spldep) {
        try {
            setSpldep(spldep.floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (String)
     */
    public int setSpldep(String spldep) {
        try {
            setSpldep(new Float(spldep).floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Called when an exception has occured
     * @param  spldep (String)
     */
    private void setSpldepError (float spldep, Exception e, int error) {
        this.spldep = spldep;
        spldepErrorMessage = e.toString();
        spldepError = error;
    } // method setSpldepError


    /**
     * Set the 'instrumentNumber' class variable
     * @param  instrumentNumber (int)
     */
    public int setInstrumentNumber(int instrumentNumber) {
        try {
            if ( ((instrumentNumber == INTNULL) ||
                  (instrumentNumber == INTNULL2)) ||
                !((instrumentNumber < INSTRUMENT_NUMBER_MN) ||
                  (instrumentNumber > INSTRUMENT_NUMBER_MX)) ) {
                this.instrumentNumber = instrumentNumber;
                instrumentNumberError = ERROR_NORMAL;
            } else {
                throw instrumentNumberOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setInstrumentNumberError(INTNULL, e, ERROR_LOCAL);
        } // try
        return instrumentNumberError;
    } // method setInstrumentNumber

    /**
     * Set the 'instrumentNumber' class variable
     * @param  instrumentNumber (Integer)
     */
    public int setInstrumentNumber(Integer instrumentNumber) {
        try {
            setInstrumentNumber(instrumentNumber.intValue());
        } catch (Exception e) {
            setInstrumentNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentNumberError;
    } // method setInstrumentNumber

    /**
     * Set the 'instrumentNumber' class variable
     * @param  instrumentNumber (Float)
     */
    public int setInstrumentNumber(Float instrumentNumber) {
        try {
            if (instrumentNumber.floatValue() == FLOATNULL) {
                setInstrumentNumber(INTNULL);
            } else {
                setInstrumentNumber(instrumentNumber.intValue());
            } // if (instrumentNumber.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setInstrumentNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentNumberError;
    } // method setInstrumentNumber

    /**
     * Set the 'instrumentNumber' class variable
     * @param  instrumentNumber (String)
     */
    public int setInstrumentNumber(String instrumentNumber) {
        try {
            setInstrumentNumber(new Integer(instrumentNumber).intValue());
        } catch (Exception e) {
            setInstrumentNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentNumberError;
    } // method setInstrumentNumber

    /**
     * Called when an exception has occured
     * @param  instrumentNumber (String)
     */
    private void setInstrumentNumberError (int instrumentNumber, Exception e, int error) {
        this.instrumentNumber = instrumentNumber;
        instrumentNumberErrorMessage = e.toString();
        instrumentNumberError = error;
    } // method setInstrumentNumberError


    /**
     * Set the 'deploymentNumber' class variable
     * @param  deploymentNumber (String)
     */
    public int setDeploymentNumber(String deploymentNumber) {
        try {
            this.deploymentNumber = deploymentNumber;
            if (this.deploymentNumber != CHARNULL) {
                this.deploymentNumber = stripCRLF(this.deploymentNumber.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>deploymentNumber = " + this.deploymentNumber);
        } catch (Exception e) {
            setDeploymentNumberError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return deploymentNumberError;
    } // method setDeploymentNumber

    /**
     * Called when an exception has occured
     * @param  deploymentNumber (String)
     */
    private void setDeploymentNumberError (String deploymentNumber, Exception e, int error) {
        this.deploymentNumber = deploymentNumber;
        deploymentNumberErrorMessage = e.toString();
        deploymentNumberError = error;
    } // method setDeploymentNumberError


    /**
     * Set the 'dateTimeStart' class variable
     * @param  dateTimeStart (Timestamp)
     */
    public int setDateTimeStart(Timestamp dateTimeStart) {
        try {
            if (dateTimeStart == null) { this.dateTimeStart = DATENULL; }
            if ( (dateTimeStart.equals(DATENULL) ||
                  dateTimeStart.equals(DATENULL2)) ||
                !(dateTimeStart.before(DATE_TIME_START_MN) ||
                  dateTimeStart.after(DATE_TIME_START_MX)) ) {
                this.dateTimeStart = dateTimeStart;
                dateTimeStartError = ERROR_NORMAL;
            } else {
                throw dateTimeStartOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateTimeStartError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateTimeStartError;
    } // method setDateTimeStart

    /**
     * Set the 'dateTimeStart' class variable
     * @param  dateTimeStart (java.util.Date)
     */
    public int setDateTimeStart(java.util.Date dateTimeStart) {
        try {
            setDateTimeStart(new Timestamp(dateTimeStart.getTime()));
        } catch (Exception e) {
            setDateTimeStartError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeStartError;
    } // method setDateTimeStart

    /**
     * Set the 'dateTimeStart' class variable
     * @param  dateTimeStart (String)
     */
    public int setDateTimeStart(String dateTimeStart) {
        try {
            int len = dateTimeStart.length();
            switch (len) {
            // date &/or times
                case 4: dateTimeStart += "-01";
                case 7: dateTimeStart += "-01";
                case 10: dateTimeStart += " 00";
                case 13: dateTimeStart += ":00";
                case 16: dateTimeStart += ":00"; break;
                // times only
                case 5: dateTimeStart = "1800-01-01 " + dateTimeStart + ":00"; break;
                case 8: dateTimeStart = "1800-01-01 " + dateTimeStart; break;
            } // switch
            if (dbg) System.out.println ("dateTimeStart = " + dateTimeStart);
            setDateTimeStart(Timestamp.valueOf(dateTimeStart));
        } catch (Exception e) {
            setDateTimeStartError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeStartError;
    } // method setDateTimeStart

    /**
     * Called when an exception has occured
     * @param  dateTimeStart (String)
     */
    private void setDateTimeStartError (Timestamp dateTimeStart, Exception e, int error) {
        this.dateTimeStart = dateTimeStart;
        dateTimeStartErrorMessage = e.toString();
        dateTimeStartError = error;
    } // method setDateTimeStartError


    /**
     * Set the 'dateTimeEnd' class variable
     * @param  dateTimeEnd (Timestamp)
     */
    public int setDateTimeEnd(Timestamp dateTimeEnd) {
        try {
            if (dateTimeEnd == null) { this.dateTimeEnd = DATENULL; }
            if ( (dateTimeEnd.equals(DATENULL) ||
                  dateTimeEnd.equals(DATENULL2)) ||
                !(dateTimeEnd.before(DATE_TIME_END_MN) ||
                  dateTimeEnd.after(DATE_TIME_END_MX)) ) {
                this.dateTimeEnd = dateTimeEnd;
                dateTimeEndError = ERROR_NORMAL;
            } else {
                throw dateTimeEndOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateTimeEndError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateTimeEndError;
    } // method setDateTimeEnd

    /**
     * Set the 'dateTimeEnd' class variable
     * @param  dateTimeEnd (java.util.Date)
     */
    public int setDateTimeEnd(java.util.Date dateTimeEnd) {
        try {
            setDateTimeEnd(new Timestamp(dateTimeEnd.getTime()));
        } catch (Exception e) {
            setDateTimeEndError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeEndError;
    } // method setDateTimeEnd

    /**
     * Set the 'dateTimeEnd' class variable
     * @param  dateTimeEnd (String)
     */
    public int setDateTimeEnd(String dateTimeEnd) {
        try {
            int len = dateTimeEnd.length();
            switch (len) {
            // date &/or times
                case 4: dateTimeEnd += "-01";
                case 7: dateTimeEnd += "-01";
                case 10: dateTimeEnd += " 00";
                case 13: dateTimeEnd += ":00";
                case 16: dateTimeEnd += ":00"; break;
                // times only
                case 5: dateTimeEnd = "1800-01-01 " + dateTimeEnd + ":00"; break;
                case 8: dateTimeEnd = "1800-01-01 " + dateTimeEnd; break;
            } // switch
            if (dbg) System.out.println ("dateTimeEnd = " + dateTimeEnd);
            setDateTimeEnd(Timestamp.valueOf(dateTimeEnd));
        } catch (Exception e) {
            setDateTimeEndError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeEndError;
    } // method setDateTimeEnd

    /**
     * Called when an exception has occured
     * @param  dateTimeEnd (String)
     */
    private void setDateTimeEndError (Timestamp dateTimeEnd, Exception e, int error) {
        this.dateTimeEnd = dateTimeEnd;
        dateTimeEndErrorMessage = e.toString();
        dateTimeEndError = error;
    } // method setDateTimeEndError


    /**
     * Set the 'timeInterval' class variable
     * @param  timeInterval (int)
     */
    public int setTimeInterval(int timeInterval) {
        try {
            if ( ((timeInterval == INTNULL) ||
                  (timeInterval == INTNULL2)) ||
                !((timeInterval < TIME_INTERVAL_MN) ||
                  (timeInterval > TIME_INTERVAL_MX)) ) {
                this.timeInterval = timeInterval;
                timeIntervalError = ERROR_NORMAL;
            } else {
                throw timeIntervalOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTimeIntervalError(INTNULL, e, ERROR_LOCAL);
        } // try
        return timeIntervalError;
    } // method setTimeInterval

    /**
     * Set the 'timeInterval' class variable
     * @param  timeInterval (Integer)
     */
    public int setTimeInterval(Integer timeInterval) {
        try {
            setTimeInterval(timeInterval.intValue());
        } catch (Exception e) {
            setTimeIntervalError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return timeIntervalError;
    } // method setTimeInterval

    /**
     * Set the 'timeInterval' class variable
     * @param  timeInterval (Float)
     */
    public int setTimeInterval(Float timeInterval) {
        try {
            if (timeInterval.floatValue() == FLOATNULL) {
                setTimeInterval(INTNULL);
            } else {
                setTimeInterval(timeInterval.intValue());
            } // if (timeInterval.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTimeIntervalError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return timeIntervalError;
    } // method setTimeInterval

    /**
     * Set the 'timeInterval' class variable
     * @param  timeInterval (String)
     */
    public int setTimeInterval(String timeInterval) {
        try {
            setTimeInterval(new Integer(timeInterval).intValue());
        } catch (Exception e) {
            setTimeIntervalError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return timeIntervalError;
    } // method setTimeInterval

    /**
     * Called when an exception has occured
     * @param  timeInterval (String)
     */
    private void setTimeIntervalError (int timeInterval, Exception e, int error) {
        this.timeInterval = timeInterval;
        timeIntervalErrorMessage = e.toString();
        timeIntervalError = error;
    } // method setTimeIntervalError


    /**
     * Set the 'numberOfRecords' class variable
     * @param  numberOfRecords (int)
     */
    public int setNumberOfRecords(int numberOfRecords) {
        try {
            if ( ((numberOfRecords == INTNULL) ||
                  (numberOfRecords == INTNULL2)) ||
                !((numberOfRecords < NUMBER_OF_RECORDS_MN) ||
                  (numberOfRecords > NUMBER_OF_RECORDS_MX)) ) {
                this.numberOfRecords = numberOfRecords;
                numberOfRecordsError = ERROR_NORMAL;
            } else {
                throw numberOfRecordsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNumberOfRecordsError(INTNULL, e, ERROR_LOCAL);
        } // try
        return numberOfRecordsError;
    } // method setNumberOfRecords

    /**
     * Set the 'numberOfRecords' class variable
     * @param  numberOfRecords (Integer)
     */
    public int setNumberOfRecords(Integer numberOfRecords) {
        try {
            setNumberOfRecords(numberOfRecords.intValue());
        } catch (Exception e) {
            setNumberOfRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberOfRecordsError;
    } // method setNumberOfRecords

    /**
     * Set the 'numberOfRecords' class variable
     * @param  numberOfRecords (Float)
     */
    public int setNumberOfRecords(Float numberOfRecords) {
        try {
            if (numberOfRecords.floatValue() == FLOATNULL) {
                setNumberOfRecords(INTNULL);
            } else {
                setNumberOfRecords(numberOfRecords.intValue());
            } // if (numberOfRecords.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setNumberOfRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberOfRecordsError;
    } // method setNumberOfRecords

    /**
     * Set the 'numberOfRecords' class variable
     * @param  numberOfRecords (String)
     */
    public int setNumberOfRecords(String numberOfRecords) {
        try {
            setNumberOfRecords(new Integer(numberOfRecords).intValue());
        } catch (Exception e) {
            setNumberOfRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberOfRecordsError;
    } // method setNumberOfRecords

    /**
     * Called when an exception has occured
     * @param  numberOfRecords (String)
     */
    private void setNumberOfRecordsError (int numberOfRecords, Exception e, int error) {
        this.numberOfRecords = numberOfRecords;
        numberOfRecordsErrorMessage = e.toString();
        numberOfRecordsError = error;
    } // method setNumberOfRecordsError


    /**
     * Set the 'passkey' class variable
     * @param  passkey (String)
     */
    public int setPasskey(String passkey) {
        try {
            this.passkey = passkey;
            if (this.passkey != CHARNULL) {
                this.passkey = stripCRLF(this.passkey.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>passkey = " + this.passkey);
        } catch (Exception e) {
            setPasskeyError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return passkeyError;
    } // method setPasskey

    /**
     * Called when an exception has occured
     * @param  passkey (String)
     */
    private void setPasskeyError (String passkey, Exception e, int error) {
        this.passkey = passkey;
        passkeyErrorMessage = e.toString();
        passkeyError = error;
    } // method setPasskeyError


    /**
     * Set the 'dateLoaded' class variable
     * @param  dateLoaded (Timestamp)
     */
    public int setDateLoaded(Timestamp dateLoaded) {
        try {
            if (dateLoaded == null) { this.dateLoaded = DATENULL; }
            if ( (dateLoaded.equals(DATENULL) ||
                  dateLoaded.equals(DATENULL2)) ||
                !(dateLoaded.before(DATE_LOADED_MN) ||
                  dateLoaded.after(DATE_LOADED_MX)) ) {
                this.dateLoaded = dateLoaded;
                dateLoadedError = ERROR_NORMAL;
            } else {
                throw dateLoadedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateLoadedError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateLoadedError;
    } // method setDateLoaded

    /**
     * Set the 'dateLoaded' class variable
     * @param  dateLoaded (java.util.Date)
     */
    public int setDateLoaded(java.util.Date dateLoaded) {
        try {
            setDateLoaded(new Timestamp(dateLoaded.getTime()));
        } catch (Exception e) {
            setDateLoadedError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateLoadedError;
    } // method setDateLoaded

    /**
     * Set the 'dateLoaded' class variable
     * @param  dateLoaded (String)
     */
    public int setDateLoaded(String dateLoaded) {
        try {
            int len = dateLoaded.length();
            switch (len) {
            // date &/or times
                case 4: dateLoaded += "-01";
                case 7: dateLoaded += "-01";
                case 10: dateLoaded += " 00";
                case 13: dateLoaded += ":00";
                case 16: dateLoaded += ":00"; break;
                // times only
                case 5: dateLoaded = "1800-01-01 " + dateLoaded + ":00"; break;
                case 8: dateLoaded = "1800-01-01 " + dateLoaded; break;
            } // switch
            if (dbg) System.out.println ("dateLoaded = " + dateLoaded);
            setDateLoaded(Timestamp.valueOf(dateLoaded));
        } catch (Exception e) {
            setDateLoadedError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateLoadedError;
    } // method setDateLoaded

    /**
     * Called when an exception has occured
     * @param  dateLoaded (String)
     */
    private void setDateLoadedError (Timestamp dateLoaded, Exception e, int error) {
        this.dateLoaded = dateLoaded;
        dateLoadedErrorMessage = e.toString();
        dateLoadedError = error;
    } // method setDateLoadedError


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
     * Set the 'parameters' class variable
     * @param  parameters (String)
     */
    public int setParameters(String parameters) {
        try {
            this.parameters = parameters;
            if (this.parameters != CHARNULL) {
                this.parameters = stripCRLF(this.parameters.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>parameters = " + this.parameters);
        } catch (Exception e) {
            setParametersError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return parametersError;
    } // method setParameters

    /**
     * Called when an exception has occured
     * @param  parameters (String)
     */
    private void setParametersError (String parameters, Exception e, int error) {
        this.parameters = parameters;
        parametersErrorMessage = e.toString();
        parametersError = error;
    } // method setParametersError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'code' class variable
     * @return code (int)
     */
    public int getCode() {
        return code;
    } // method getCode

    /**
     * Return the 'code' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCode methods
     * @return code (String)
     */
    public String getCode(String s) {
        return ((code != INTNULL) ? new Integer(code).toString() : "");
    } // method getCode


    /**
     * Return the 'mooringCode' class variable
     * @return mooringCode (int)
     */
    public int getMooringCode() {
        return mooringCode;
    } // method getMooringCode

    /**
     * Return the 'mooringCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMooringCode methods
     * @return mooringCode (String)
     */
    public String getMooringCode(String s) {
        return ((mooringCode != INTNULL) ? new Integer(mooringCode).toString() : "");
    } // method getMooringCode


    /**
     * Return the 'spldep' class variable
     * @return spldep (float)
     */
    public float getSpldep() {
        return spldep;
    } // method getSpldep

    /**
     * Return the 'spldep' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpldep methods
     * @return spldep (String)
     */
    public String getSpldep(String s) {
        return ((spldep != FLOATNULL) ? new Float(spldep).toString() : "");
    } // method getSpldep


    /**
     * Return the 'instrumentNumber' class variable
     * @return instrumentNumber (int)
     */
    public int getInstrumentNumber() {
        return instrumentNumber;
    } // method getInstrumentNumber

    /**
     * Return the 'instrumentNumber' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getInstrumentNumber methods
     * @return instrumentNumber (String)
     */
    public String getInstrumentNumber(String s) {
        return ((instrumentNumber != INTNULL) ? new Integer(instrumentNumber).toString() : "");
    } // method getInstrumentNumber


    /**
     * Return the 'deploymentNumber' class variable
     * @return deploymentNumber (String)
     */
    public String getDeploymentNumber() {
        return deploymentNumber;
    } // method getDeploymentNumber

    /**
     * Return the 'deploymentNumber' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDeploymentNumber methods
     * @return deploymentNumber (String)
     */
    public String getDeploymentNumber(String s) {
        return (deploymentNumber != CHARNULL ? deploymentNumber.replace('"','\'') : "");
    } // method getDeploymentNumber


    /**
     * Return the 'dateTimeStart' class variable
     * @return dateTimeStart (Timestamp)
     */
    public Timestamp getDateTimeStart() {
        return dateTimeStart;
    } // method getDateTimeStart

    /**
     * Return the 'dateTimeStart' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateTimeStart methods
     * @return dateTimeStart (String)
     */
    public String getDateTimeStart(String s) {
        if (dateTimeStart.equals(DATENULL)) {
            return ("");
        } else {
            String dateTimeStartStr = dateTimeStart.toString();
            return dateTimeStartStr.substring(0,dateTimeStartStr.indexOf('.'));
        } // if
    } // method getDateTimeStart


    /**
     * Return the 'dateTimeEnd' class variable
     * @return dateTimeEnd (Timestamp)
     */
    public Timestamp getDateTimeEnd() {
        return dateTimeEnd;
    } // method getDateTimeEnd

    /**
     * Return the 'dateTimeEnd' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateTimeEnd methods
     * @return dateTimeEnd (String)
     */
    public String getDateTimeEnd(String s) {
        if (dateTimeEnd.equals(DATENULL)) {
            return ("");
        } else {
            String dateTimeEndStr = dateTimeEnd.toString();
            return dateTimeEndStr.substring(0,dateTimeEndStr.indexOf('.'));
        } // if
    } // method getDateTimeEnd


    /**
     * Return the 'timeInterval' class variable
     * @return timeInterval (int)
     */
    public int getTimeInterval() {
        return timeInterval;
    } // method getTimeInterval

    /**
     * Return the 'timeInterval' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTimeInterval methods
     * @return timeInterval (String)
     */
    public String getTimeInterval(String s) {
        return ((timeInterval != INTNULL) ? new Integer(timeInterval).toString() : "");
    } // method getTimeInterval


    /**
     * Return the 'numberOfRecords' class variable
     * @return numberOfRecords (int)
     */
    public int getNumberOfRecords() {
        return numberOfRecords;
    } // method getNumberOfRecords

    /**
     * Return the 'numberOfRecords' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNumberOfRecords methods
     * @return numberOfRecords (String)
     */
    public String getNumberOfRecords(String s) {
        return ((numberOfRecords != INTNULL) ? new Integer(numberOfRecords).toString() : "");
    } // method getNumberOfRecords


    /**
     * Return the 'passkey' class variable
     * @return passkey (String)
     */
    public String getPasskey() {
        return passkey;
    } // method getPasskey

    /**
     * Return the 'passkey' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPasskey methods
     * @return passkey (String)
     */
    public String getPasskey(String s) {
        return (passkey != CHARNULL ? passkey.replace('"','\'') : "");
    } // method getPasskey


    /**
     * Return the 'dateLoaded' class variable
     * @return dateLoaded (Timestamp)
     */
    public Timestamp getDateLoaded() {
        return dateLoaded;
    } // method getDateLoaded

    /**
     * Return the 'dateLoaded' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateLoaded methods
     * @return dateLoaded (String)
     */
    public String getDateLoaded(String s) {
        if (dateLoaded.equals(DATENULL)) {
            return ("");
        } else {
            String dateLoadedStr = dateLoaded.toString();
            return dateLoadedStr.substring(0,dateLoadedStr.indexOf('.'));
        } // if
    } // method getDateLoaded


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
     * Return the 'parameters' class variable
     * @return parameters (String)
     */
    public String getParameters() {
        return parameters;
    } // method getParameters

    /**
     * Return the 'parameters' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getParameters methods
     * @return parameters (String)
     */
    public String getParameters(String s) {
        return (parameters != CHARNULL ? parameters.replace('"','\'') : "");
    } // method getParameters


    /**
     * Gets the maximum value for the code from the table
     * @return maximum code value (int)
     */
    public int getMaxCode() {
        Vector result = db.select ("max(" + CODE + ")",TABLE,"1=1");
        Vector row = (Vector) result.elementAt(0);
        return (new Integer((String) row.elementAt(0)).intValue());
    } // method getMaxCode


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
        if ((code == INTNULL) &&
            (mooringCode == INTNULL) &&
            (spldep == FLOATNULL) &&
            (instrumentNumber == INTNULL) &&
            (deploymentNumber == CHARNULL) &&
            (dateTimeStart.equals(DATENULL)) &&
            (dateTimeEnd.equals(DATENULL)) &&
            (timeInterval == INTNULL) &&
            (numberOfRecords == INTNULL) &&
            (passkey == CHARNULL) &&
            (dateLoaded.equals(DATENULL)) &&
            (surveyId == CHARNULL) &&
            (parameters == CHARNULL)) {
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
        int sumError = codeError +
            mooringCodeError +
            spldepError +
            instrumentNumberError +
            deploymentNumberError +
            dateTimeStartError +
            dateTimeEndError +
            timeIntervalError +
            numberOfRecordsError +
            passkeyError +
            dateLoadedError +
            surveyIdError +
            parametersError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the code instance variable
     * @return errorcode (int)
     */
    public int getCodeError() {
        return codeError;
    } // method getCodeError

    /**
     * Gets the errorMessage for the code instance variable
     * @return errorMessage (String)
     */
    public String getCodeErrorMessage() {
        return codeErrorMessage;
    } // method getCodeErrorMessage


    /**
     * Gets the errorcode for the mooringCode instance variable
     * @return errorcode (int)
     */
    public int getMooringCodeError() {
        return mooringCodeError;
    } // method getMooringCodeError

    /**
     * Gets the errorMessage for the mooringCode instance variable
     * @return errorMessage (String)
     */
    public String getMooringCodeErrorMessage() {
        return mooringCodeErrorMessage;
    } // method getMooringCodeErrorMessage


    /**
     * Gets the errorcode for the spldep instance variable
     * @return errorcode (int)
     */
    public int getSpldepError() {
        return spldepError;
    } // method getSpldepError

    /**
     * Gets the errorMessage for the spldep instance variable
     * @return errorMessage (String)
     */
    public String getSpldepErrorMessage() {
        return spldepErrorMessage;
    } // method getSpldepErrorMessage


    /**
     * Gets the errorcode for the instrumentNumber instance variable
     * @return errorcode (int)
     */
    public int getInstrumentNumberError() {
        return instrumentNumberError;
    } // method getInstrumentNumberError

    /**
     * Gets the errorMessage for the instrumentNumber instance variable
     * @return errorMessage (String)
     */
    public String getInstrumentNumberErrorMessage() {
        return instrumentNumberErrorMessage;
    } // method getInstrumentNumberErrorMessage


    /**
     * Gets the errorcode for the deploymentNumber instance variable
     * @return errorcode (int)
     */
    public int getDeploymentNumberError() {
        return deploymentNumberError;
    } // method getDeploymentNumberError

    /**
     * Gets the errorMessage for the deploymentNumber instance variable
     * @return errorMessage (String)
     */
    public String getDeploymentNumberErrorMessage() {
        return deploymentNumberErrorMessage;
    } // method getDeploymentNumberErrorMessage


    /**
     * Gets the errorcode for the dateTimeStart instance variable
     * @return errorcode (int)
     */
    public int getDateTimeStartError() {
        return dateTimeStartError;
    } // method getDateTimeStartError

    /**
     * Gets the errorMessage for the dateTimeStart instance variable
     * @return errorMessage (String)
     */
    public String getDateTimeStartErrorMessage() {
        return dateTimeStartErrorMessage;
    } // method getDateTimeStartErrorMessage


    /**
     * Gets the errorcode for the dateTimeEnd instance variable
     * @return errorcode (int)
     */
    public int getDateTimeEndError() {
        return dateTimeEndError;
    } // method getDateTimeEndError

    /**
     * Gets the errorMessage for the dateTimeEnd instance variable
     * @return errorMessage (String)
     */
    public String getDateTimeEndErrorMessage() {
        return dateTimeEndErrorMessage;
    } // method getDateTimeEndErrorMessage


    /**
     * Gets the errorcode for the timeInterval instance variable
     * @return errorcode (int)
     */
    public int getTimeIntervalError() {
        return timeIntervalError;
    } // method getTimeIntervalError

    /**
     * Gets the errorMessage for the timeInterval instance variable
     * @return errorMessage (String)
     */
    public String getTimeIntervalErrorMessage() {
        return timeIntervalErrorMessage;
    } // method getTimeIntervalErrorMessage


    /**
     * Gets the errorcode for the numberOfRecords instance variable
     * @return errorcode (int)
     */
    public int getNumberOfRecordsError() {
        return numberOfRecordsError;
    } // method getNumberOfRecordsError

    /**
     * Gets the errorMessage for the numberOfRecords instance variable
     * @return errorMessage (String)
     */
    public String getNumberOfRecordsErrorMessage() {
        return numberOfRecordsErrorMessage;
    } // method getNumberOfRecordsErrorMessage


    /**
     * Gets the errorcode for the passkey instance variable
     * @return errorcode (int)
     */
    public int getPasskeyError() {
        return passkeyError;
    } // method getPasskeyError

    /**
     * Gets the errorMessage for the passkey instance variable
     * @return errorMessage (String)
     */
    public String getPasskeyErrorMessage() {
        return passkeyErrorMessage;
    } // method getPasskeyErrorMessage


    /**
     * Gets the errorcode for the dateLoaded instance variable
     * @return errorcode (int)
     */
    public int getDateLoadedError() {
        return dateLoadedError;
    } // method getDateLoadedError

    /**
     * Gets the errorMessage for the dateLoaded instance variable
     * @return errorMessage (String)
     */
    public String getDateLoadedErrorMessage() {
        return dateLoadedErrorMessage;
    } // method getDateLoadedErrorMessage


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
     * Gets the errorcode for the parameters instance variable
     * @return errorcode (int)
     */
    public int getParametersError() {
        return parametersError;
    } // method getParametersError

    /**
     * Gets the errorMessage for the parameters instance variable
     * @return errorMessage (String)
     */
    public String getParametersErrorMessage() {
        return parametersErrorMessage;
    } // method getParametersErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of CurDepth. e.g.<pre>
     * CurDepth curDepth = new CurDepth(<val1>);
     * CurDepth curDepthArray[] = curDepth.get();</pre>
     * will get the CurDepth record where code = <val1>.
     * @return Array of CurDepth (CurDepth[])
     */
    public CurDepth[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * CurDepth curDepthArray[] =
     *     new CurDepth().get(CurDepth.CODE+"=<val1>");</pre>
     * will get the CurDepth record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of CurDepth (CurDepth[])
     */
    public CurDepth[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * CurDepth curDepthArray[] =
     *     new CurDepth().get("1=1",curDepth.CODE);</pre>
     * will get the all the CurDepth records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurDepth (CurDepth[])
     */
    public CurDepth[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = CurDepth.CODE,CurDepth.MOORING_CODE;
     * String where = CurDepth.CODE + "=<val1";
     * String order = CurDepth.CODE;
     * CurDepth curDepthArray[] =
     *     new CurDepth().get(columns, where, order);</pre>
     * will get the code and mooringCode colums of all CurDepth records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurDepth (CurDepth[])
     */
    public CurDepth[] get (String fields, String where, String order) {
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
     * and transforms it into an array of CurDepth.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private CurDepth[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol             = db.getColNumber(CODE);
        int mooringCodeCol      = db.getColNumber(MOORING_CODE);
        int spldepCol           = db.getColNumber(SPLDEP);
        int instrumentNumberCol = db.getColNumber(INSTRUMENT_NUMBER);
        int deploymentNumberCol = db.getColNumber(DEPLOYMENT_NUMBER);
        int dateTimeStartCol    = db.getColNumber(DATE_TIME_START);
        int dateTimeEndCol      = db.getColNumber(DATE_TIME_END);
        int timeIntervalCol     = db.getColNumber(TIME_INTERVAL);
        int numberOfRecordsCol  = db.getColNumber(NUMBER_OF_RECORDS);
        int passkeyCol          = db.getColNumber(PASSKEY);
        int dateLoadedCol       = db.getColNumber(DATE_LOADED);
        int surveyIdCol         = db.getColNumber(SURVEY_ID);
        int parametersCol       = db.getColNumber(PARAMETERS);
        CurDepth[] cArray = new CurDepth[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new CurDepth();
            if (codeCol != -1)
                cArray[i].setCode            ((String) row.elementAt(codeCol));
            if (mooringCodeCol != -1)
                cArray[i].setMooringCode     ((String) row.elementAt(mooringCodeCol));
            if (spldepCol != -1)
                cArray[i].setSpldep          ((String) row.elementAt(spldepCol));
            if (instrumentNumberCol != -1)
                cArray[i].setInstrumentNumber((String) row.elementAt(instrumentNumberCol));
            if (deploymentNumberCol != -1)
                cArray[i].setDeploymentNumber((String) row.elementAt(deploymentNumberCol));
            if (dateTimeStartCol != -1)
                cArray[i].setDateTimeStart   ((String) row.elementAt(dateTimeStartCol));
            if (dateTimeEndCol != -1)
                cArray[i].setDateTimeEnd     ((String) row.elementAt(dateTimeEndCol));
            if (timeIntervalCol != -1)
                cArray[i].setTimeInterval    ((String) row.elementAt(timeIntervalCol));
            if (numberOfRecordsCol != -1)
                cArray[i].setNumberOfRecords ((String) row.elementAt(numberOfRecordsCol));
            if (passkeyCol != -1)
                cArray[i].setPasskey         ((String) row.elementAt(passkeyCol));
            if (dateLoadedCol != -1)
                cArray[i].setDateLoaded      ((String) row.elementAt(dateLoadedCol));
            if (surveyIdCol != -1)
                cArray[i].setSurveyId        ((String) row.elementAt(surveyIdCol));
            if (parametersCol != -1)
                cArray[i].setParameters      ((String) row.elementAt(parametersCol));
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
     *     new CurDepth(
     *         INTNULL,
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
     *         <val13>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     mooringCode      = <val2>,
     *     spldep           = <val3>,
     *     instrumentNumber = <val4>,
     *     deploymentNumber = <val5>,
     *     dateTimeStart    = <val6>,
     *     dateTimeEnd      = <val7>,
     *     timeInterval     = <val8>,
     *     numberOfRecords  = <val9>,
     *     passkey          = <val10>,
     *     dateLoaded       = <val11>,
     *     surveyId         = <val12>,
     *     parameters       = <val13>.
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
     * Boolean success = new CurDepth(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.DATENULL,
     *     Tables.DATENULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.DATENULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where mooringCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurDepth curDepth = new CurDepth();
     * success = curDepth.del(CurDepth.CODE+"=<val1>");</pre>
     * will delete all records where code = <val1>.
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
     * update are taken from the CurDepth argument, .e.g.<pre>
     * Boolean success
     * CurDepth updCurDepth = new CurDepth();
     * updCurDepth.setMooringCode(<val2>);
     * CurDepth whereCurDepth = new CurDepth(<val1>);
     * success = whereCurDepth.upd(updCurDepth);</pre>
     * will update MooringCode to <val2> for all records where
     * code = <val1>.
     * @param curDepth  A CurDepth variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(CurDepth curDepth) {
        return db.update (TABLE, createColVals(curDepth), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurDepth updCurDepth = new CurDepth();
     * updCurDepth.setMooringCode(<val2>);
     * CurDepth whereCurDepth = new CurDepth();
     * success = whereCurDepth.upd(
     *     updCurDepth, CurDepth.CODE+"=<val1>");</pre>
     * will update MooringCode to <val2> for all records where
     * code = <val1>.
     * @param  updCurDepth  A CurDepth variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(CurDepth curDepth, String where) {
        return db.update (TABLE, createColVals(curDepth), where);
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
        if (getCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CODE + "=" + getCode("");
        } // if getCode
        if (getMooringCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MOORING_CODE + "=" + getMooringCode("");
        } // if getMooringCode
        if (getSpldep() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPLDEP + "=" + getSpldep("");
        } // if getSpldep
        if (getInstrumentNumber() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTRUMENT_NUMBER + "=" + getInstrumentNumber("");
        } // if getInstrumentNumber
        if (getDeploymentNumber() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEPLOYMENT_NUMBER + "='" + getDeploymentNumber() + "'";
        } // if getDeploymentNumber
        if (!getDateTimeStart().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME_START +
                "=" + Tables.getDateFormat(getDateTimeStart());
        } // if getDateTimeStart
        if (!getDateTimeEnd().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME_END +
                "=" + Tables.getDateFormat(getDateTimeEnd());
        } // if getDateTimeEnd
        if (getTimeInterval() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TIME_INTERVAL + "=" + getTimeInterval("");
        } // if getTimeInterval
        if (getNumberOfRecords() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NUMBER_OF_RECORDS + "=" + getNumberOfRecords("");
        } // if getNumberOfRecords
        if (getPasskey() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PASSKEY + "='" + getPasskey() + "'";
        } // if getPasskey
        if (!getDateLoaded().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_LOADED +
                "=" + Tables.getDateFormat(getDateLoaded());
        } // if getDateLoaded
        if (getSurveyId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_ID + "='" + getSurveyId() + "'";
        } // if getSurveyId
        if (getParameters() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PARAMETERS + "='" + getParameters() + "'";
        } // if getParameters
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(CurDepth aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getMooringCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MOORING_CODE +"=";
            colVals += (aVar.getMooringCode() == INTNULL2 ?
                "null" : aVar.getMooringCode(""));
        } // if aVar.getMooringCode
        if (aVar.getSpldep() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPLDEP +"=";
            colVals += (aVar.getSpldep() == FLOATNULL2 ?
                "null" : aVar.getSpldep(""));
        } // if aVar.getSpldep
        if (aVar.getInstrumentNumber() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTRUMENT_NUMBER +"=";
            colVals += (aVar.getInstrumentNumber() == INTNULL2 ?
                "null" : aVar.getInstrumentNumber(""));
        } // if aVar.getInstrumentNumber
        if (aVar.getDeploymentNumber() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEPLOYMENT_NUMBER +"=";
            colVals += (aVar.getDeploymentNumber().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDeploymentNumber() + "'");
        } // if aVar.getDeploymentNumber
        if (!aVar.getDateTimeStart().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME_START +"=";
            colVals += (aVar.getDateTimeStart().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTimeStart()));
        } // if aVar.getDateTimeStart
        if (!aVar.getDateTimeEnd().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME_END +"=";
            colVals += (aVar.getDateTimeEnd().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTimeEnd()));
        } // if aVar.getDateTimeEnd
        if (aVar.getTimeInterval() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TIME_INTERVAL +"=";
            colVals += (aVar.getTimeInterval() == INTNULL2 ?
                "null" : aVar.getTimeInterval(""));
        } // if aVar.getTimeInterval
        if (aVar.getNumberOfRecords() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NUMBER_OF_RECORDS +"=";
            colVals += (aVar.getNumberOfRecords() == INTNULL2 ?
                "null" : aVar.getNumberOfRecords(""));
        } // if aVar.getNumberOfRecords
        if (aVar.getPasskey() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PASSKEY +"=";
            colVals += (aVar.getPasskey().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPasskey() + "'");
        } // if aVar.getPasskey
        if (!aVar.getDateLoaded().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_LOADED +"=";
            colVals += (aVar.getDateLoaded().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateLoaded()));
        } // if aVar.getDateLoaded
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getParameters() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PARAMETERS +"=";
            colVals += (aVar.getParameters().equals(CHARNULL2) ?
                "null" : "'" + aVar.getParameters() + "'");
        } // if aVar.getParameters
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getMooringCode() != INTNULL) {
            columns = columns + "," + MOORING_CODE;
        } // if getMooringCode
        if (getSpldep() != FLOATNULL) {
            columns = columns + "," + SPLDEP;
        } // if getSpldep
        if (getInstrumentNumber() != INTNULL) {
            columns = columns + "," + INSTRUMENT_NUMBER;
        } // if getInstrumentNumber
        if (getDeploymentNumber() != CHARNULL) {
            columns = columns + "," + DEPLOYMENT_NUMBER;
        } // if getDeploymentNumber
        if (!getDateTimeStart().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME_START;
        } // if getDateTimeStart
        if (!getDateTimeEnd().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME_END;
        } // if getDateTimeEnd
        if (getTimeInterval() != INTNULL) {
            columns = columns + "," + TIME_INTERVAL;
        } // if getTimeInterval
        if (getNumberOfRecords() != INTNULL) {
            columns = columns + "," + NUMBER_OF_RECORDS;
        } // if getNumberOfRecords
        if (getPasskey() != CHARNULL) {
            columns = columns + "," + PASSKEY;
        } // if getPasskey
        if (!getDateLoaded().equals(DATENULL)) {
            columns = columns + "," + DATE_LOADED;
        } // if getDateLoaded
        if (getSurveyId() != CHARNULL) {
            columns = columns + "," + SURVEY_ID;
        } // if getSurveyId
        if (getParameters() != CHARNULL) {
            columns = columns + "," + PARAMETERS;
        } // if getParameters
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        setCode(getMaxCode()+1);
        String values  = getCode("");
        if (getMooringCode() != INTNULL) {
            values  = values  + "," + getMooringCode("");
        } // if getMooringCode
        if (getSpldep() != FLOATNULL) {
            values  = values  + "," + getSpldep("");
        } // if getSpldep
        if (getInstrumentNumber() != INTNULL) {
            values  = values  + "," + getInstrumentNumber("");
        } // if getInstrumentNumber
        if (getDeploymentNumber() != CHARNULL) {
            values  = values  + ",'" + getDeploymentNumber() + "'";
        } // if getDeploymentNumber
        if (!getDateTimeStart().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTimeStart());
        } // if getDateTimeStart
        if (!getDateTimeEnd().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTimeEnd());
        } // if getDateTimeEnd
        if (getTimeInterval() != INTNULL) {
            values  = values  + "," + getTimeInterval("");
        } // if getTimeInterval
        if (getNumberOfRecords() != INTNULL) {
            values  = values  + "," + getNumberOfRecords("");
        } // if getNumberOfRecords
        if (getPasskey() != CHARNULL) {
            values  = values  + ",'" + getPasskey() + "'";
        } // if getPasskey
        if (!getDateLoaded().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateLoaded());
        } // if getDateLoaded
        if (getSurveyId() != CHARNULL) {
            values  = values  + ",'" + getSurveyId() + "'";
        } // if getSurveyId
        if (getParameters() != CHARNULL) {
            values  = values  + ",'" + getParameters() + "'";
        } // if getParameters
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")             + "|" +
            getMooringCode("")      + "|" +
            getSpldep("")           + "|" +
            getInstrumentNumber("") + "|" +
            getDeploymentNumber("") + "|" +
            getDateTimeStart("")    + "|" +
            getDateTimeEnd("")      + "|" +
            getTimeInterval("")     + "|" +
            getNumberOfRecords("")  + "|" +
            getPasskey("")          + "|" +
            getDateLoaded("")       + "|" +
            getSurveyId("")         + "|" +
            getParameters("")       + "|";
    } // method toString

} // class CurDepth
