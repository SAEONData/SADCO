package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WAV_DATA table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 080222 - SIT Group
 * @version
 * 080222 - GenTableClassB class - create class<br>
 */
public class WavData extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WAV_DATA";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The dateTime field name */
    public static final String DATE_TIME = "DATE_TIME";
    /** The numberReadings field name */
    public static final String NUMBER_READINGS = "NUMBER_READINGS";
    /** The recordLength field name */
    public static final String RECORD_LENGTH = "RECORD_LENGTH";
    /** The deltaf field name */
    public static final String DELTAF = "DELTAF";
    /** The deltat field name */
    public static final String DELTAT = "DELTAT";
    /** The frequency field name */
    public static final String FREQUENCY = "FREQUENCY";
    /** The qp field name */
    public static final String QP = "QP";
    /** The tb field name */
    public static final String TB = "TB";
    /** The te field name */
    public static final String TE = "TE";
    /** The wap field name */
    public static final String WAP = "WAP";
    /** The eps field name */
    public static final String EPS = "EPS";
    /** The hmo field name */
    public static final String HMO = "HMO";
    /** The h1 field name */
    public static final String H1 = "H1";
    /** The hs field name */
    public static final String HS = "HS";
    /** The hmax field name */
    public static final String HMAX = "HMAX";
    /** The tc field name */
    public static final String TC = "TC";
    /** The tp field name */
    public static final String TP = "TP";
    /** The tz field name */
    public static final String TZ = "TZ";
    /** The aveDirection field name */
    public static final String AVE_DIRECTION = "AVE_DIRECTION";
    /** The aveSpreading field name */
    public static final String AVE_SPREADING = "AVE_SPREADING";
    /** The instrumentCode field name */
    public static final String INSTRUMENT_CODE = "INSTRUMENT_CODE";
    /** The meanDirection field name */
    public static final String MEAN_DIRECTION = "MEAN_DIRECTION";
    /** The meanSpreading field name */
    public static final String MEAN_SPREADING = "MEAN_SPREADING";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    stationId;
    private Timestamp dateTime;
    private int       numberReadings;
    private float     recordLength;
    private float     deltaf;
    private float     deltat;
    private float     frequency;
    private float     qp;
    private float     tb;
    private float     te;
    private float     wap;
    private float     eps;
    private float     hmo;
    private float     h1;
    private float     hs;
    private float     hmax;
    private float     tc;
    private float     tp;
    private float     tz;
    private float     aveDirection;
    private float     aveSpreading;
    private int       instrumentCode;
    private float     meanDirection;
    private float     meanSpreading;

    /** The error variables  */
    private int codeError           = ERROR_NORMAL;
    private int stationIdError      = ERROR_NORMAL;
    private int dateTimeError       = ERROR_NORMAL;
    private int numberReadingsError = ERROR_NORMAL;
    private int recordLengthError   = ERROR_NORMAL;
    private int deltafError         = ERROR_NORMAL;
    private int deltatError         = ERROR_NORMAL;
    private int frequencyError      = ERROR_NORMAL;
    private int qpError             = ERROR_NORMAL;
    private int tbError             = ERROR_NORMAL;
    private int teError             = ERROR_NORMAL;
    private int wapError            = ERROR_NORMAL;
    private int epsError            = ERROR_NORMAL;
    private int hmoError            = ERROR_NORMAL;
    private int h1Error             = ERROR_NORMAL;
    private int hsError             = ERROR_NORMAL;
    private int hmaxError           = ERROR_NORMAL;
    private int tcError             = ERROR_NORMAL;
    private int tpError             = ERROR_NORMAL;
    private int tzError             = ERROR_NORMAL;
    private int aveDirectionError   = ERROR_NORMAL;
    private int aveSpreadingError   = ERROR_NORMAL;
    private int instrumentCodeError = ERROR_NORMAL;
    private int meanDirectionError  = ERROR_NORMAL;
    private int meanSpreadingError  = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage           = "";
    private String stationIdErrorMessage      = "";
    private String dateTimeErrorMessage       = "";
    private String numberReadingsErrorMessage = "";
    private String recordLengthErrorMessage   = "";
    private String deltafErrorMessage         = "";
    private String deltatErrorMessage         = "";
    private String frequencyErrorMessage      = "";
    private String qpErrorMessage             = "";
    private String tbErrorMessage             = "";
    private String teErrorMessage             = "";
    private String wapErrorMessage            = "";
    private String epsErrorMessage            = "";
    private String hmoErrorMessage            = "";
    private String h1ErrorMessage             = "";
    private String hsErrorMessage             = "";
    private String hmaxErrorMessage           = "";
    private String tcErrorMessage             = "";
    private String tpErrorMessage             = "";
    private String tzErrorMessage             = "";
    private String aveDirectionErrorMessage   = "";
    private String aveSpreadingErrorMessage   = "";
    private String instrumentCodeErrorMessage = "";
    private String meanDirectionErrorMessage  = "";
    private String meanSpreadingErrorMessage  = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final Timestamp DATE_TIME_MN = DATEMIN;
    public static final Timestamp DATE_TIME_MX = DATEMAX;
    public static final int       NUMBER_READINGS_MN = INTMIN;
    public static final int       NUMBER_READINGS_MX = INTMAX;
    public static final float     RECORD_LENGTH_MN = FLOATMIN;
    public static final float     RECORD_LENGTH_MX = FLOATMAX;
    public static final float     DELTAF_MN = FLOATMIN;
    public static final float     DELTAF_MX = FLOATMAX;
    public static final float     DELTAT_MN = FLOATMIN;
    public static final float     DELTAT_MX = FLOATMAX;
    public static final float     FREQUENCY_MN = FLOATMIN;
    public static final float     FREQUENCY_MX = FLOATMAX;
    public static final float     QP_MN = FLOATMIN;
    public static final float     QP_MX = FLOATMAX;
    public static final float     TB_MN = FLOATMIN;
    public static final float     TB_MX = FLOATMAX;
    public static final float     TE_MN = FLOATMIN;
    public static final float     TE_MX = FLOATMAX;
    public static final float     WAP_MN = FLOATMIN;
    public static final float     WAP_MX = FLOATMAX;
    public static final float     EPS_MN = FLOATMIN;
    public static final float     EPS_MX = FLOATMAX;
    public static final float     HMO_MN = FLOATMIN;
    public static final float     HMO_MX = FLOATMAX;
    public static final float     H1_MN = FLOATMIN;
    public static final float     H1_MX = FLOATMAX;
    public static final float     HS_MN = FLOATMIN;
    public static final float     HS_MX = FLOATMAX;
    public static final float     HMAX_MN = FLOATMIN;
    public static final float     HMAX_MX = FLOATMAX;
    public static final float     TC_MN = FLOATMIN;
    public static final float     TC_MX = FLOATMAX;
    public static final float     TP_MN = FLOATMIN;
    public static final float     TP_MX = FLOATMAX;
    public static final float     TZ_MN = FLOATMIN;
    public static final float     TZ_MX = FLOATMAX;
    public static final float     AVE_DIRECTION_MN = FLOATMIN;
    public static final float     AVE_DIRECTION_MX = FLOATMAX;
    public static final float     AVE_SPREADING_MN = FLOATMIN;
    public static final float     AVE_SPREADING_MX = FLOATMAX;
    public static final int       INSTRUMENT_CODE_MN = INTMIN;
    public static final int       INSTRUMENT_CODE_MX = INTMAX;
    public static final float     MEAN_DIRECTION_MN = FLOATMIN;
    public static final float     MEAN_DIRECTION_MX = FLOATMAX;
    public static final float     MEAN_SPREADING_MN = FLOATMIN;
    public static final float     MEAN_SPREADING_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception dateTimeException =
        new Exception ("'dateTime' is null");
    Exception dateTimeOutOfBoundsException =
        new Exception ("'dateTime' out of bounds: " +
            DATE_TIME_MN + " - " + DATE_TIME_MX);
    Exception numberReadingsOutOfBoundsException =
        new Exception ("'numberReadings' out of bounds: " +
            NUMBER_READINGS_MN + " - " + NUMBER_READINGS_MX);
    Exception recordLengthOutOfBoundsException =
        new Exception ("'recordLength' out of bounds: " +
            RECORD_LENGTH_MN + " - " + RECORD_LENGTH_MX);
    Exception deltafOutOfBoundsException =
        new Exception ("'deltaf' out of bounds: " +
            DELTAF_MN + " - " + DELTAF_MX);
    Exception deltatOutOfBoundsException =
        new Exception ("'deltat' out of bounds: " +
            DELTAT_MN + " - " + DELTAT_MX);
    Exception frequencyOutOfBoundsException =
        new Exception ("'frequency' out of bounds: " +
            FREQUENCY_MN + " - " + FREQUENCY_MX);
    Exception qpOutOfBoundsException =
        new Exception ("'qp' out of bounds: " +
            QP_MN + " - " + QP_MX);
    Exception tbOutOfBoundsException =
        new Exception ("'tb' out of bounds: " +
            TB_MN + " - " + TB_MX);
    Exception teOutOfBoundsException =
        new Exception ("'te' out of bounds: " +
            TE_MN + " - " + TE_MX);
    Exception wapOutOfBoundsException =
        new Exception ("'wap' out of bounds: " +
            WAP_MN + " - " + WAP_MX);
    Exception epsOutOfBoundsException =
        new Exception ("'eps' out of bounds: " +
            EPS_MN + " - " + EPS_MX);
    Exception hmoOutOfBoundsException =
        new Exception ("'hmo' out of bounds: " +
            HMO_MN + " - " + HMO_MX);
    Exception h1OutOfBoundsException =
        new Exception ("'h1' out of bounds: " +
            H1_MN + " - " + H1_MX);
    Exception hsOutOfBoundsException =
        new Exception ("'hs' out of bounds: " +
            HS_MN + " - " + HS_MX);
    Exception hmaxOutOfBoundsException =
        new Exception ("'hmax' out of bounds: " +
            HMAX_MN + " - " + HMAX_MX);
    Exception tcOutOfBoundsException =
        new Exception ("'tc' out of bounds: " +
            TC_MN + " - " + TC_MX);
    Exception tpOutOfBoundsException =
        new Exception ("'tp' out of bounds: " +
            TP_MN + " - " + TP_MX);
    Exception tzOutOfBoundsException =
        new Exception ("'tz' out of bounds: " +
            TZ_MN + " - " + TZ_MX);
    Exception aveDirectionOutOfBoundsException =
        new Exception ("'aveDirection' out of bounds: " +
            AVE_DIRECTION_MN + " - " + AVE_DIRECTION_MX);
    Exception aveSpreadingOutOfBoundsException =
        new Exception ("'aveSpreading' out of bounds: " +
            AVE_SPREADING_MN + " - " + AVE_SPREADING_MX);
    Exception instrumentCodeOutOfBoundsException =
        new Exception ("'instrumentCode' out of bounds: " +
            INSTRUMENT_CODE_MN + " - " + INSTRUMENT_CODE_MX);
    Exception meanDirectionOutOfBoundsException =
        new Exception ("'meanDirection' out of bounds: " +
            MEAN_DIRECTION_MN + " - " + MEAN_DIRECTION_MX);
    Exception meanSpreadingOutOfBoundsException =
        new Exception ("'meanSpreading' out of bounds: " +
            MEAN_SPREADING_MN + " - " + MEAN_SPREADING_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public WavData() {
        clearVars();
        if (dbg) System.out.println ("<br>in WavData constructor 1"); // debug
    } // WavData constructor

    /**
     * Instantiate a WavData object and initialize the instance variables.
     * @param code  The code (int)
     */
    public WavData(
            int code) {
        this();
        setCode           (code          );
        if (dbg) System.out.println ("<br>in WavData constructor 2"); // debug
    } // WavData constructor

    /**
     * Instantiate a WavData object and initialize the instance variables.
     * @param code            The code           (int)
     * @param stationId       The stationId      (String)
     * @param dateTime        The dateTime       (java.util.Date)
     * @param numberReadings  The numberReadings (int)
     * @param recordLength    The recordLength   (float)
     * @param deltaf          The deltaf         (float)
     * @param deltat          The deltat         (float)
     * @param frequency       The frequency      (float)
     * @param qp              The qp             (float)
     * @param tb              The tb             (float)
     * @param te              The te             (float)
     * @param wap             The wap            (float)
     * @param eps             The eps            (float)
     * @param hmo             The hmo            (float)
     * @param h1              The h1             (float)
     * @param hs              The hs             (float)
     * @param hmax            The hmax           (float)
     * @param tc              The tc             (float)
     * @param tp              The tp             (float)
     * @param tz              The tz             (float)
     * @param aveDirection    The aveDirection   (float)
     * @param aveSpreading    The aveSpreading   (float)
     * @param instrumentCode  The instrumentCode (int)
     * @param meanDirection   The meanDirection  (float)
     * @param meanSpreading   The meanSpreading  (float)
     * @return A WavData object
     */
    public WavData(
            int            code,
            String         stationId,
            java.util.Date dateTime,
            int            numberReadings,
            float          recordLength,
            float          deltaf,
            float          deltat,
            float          frequency,
            float          qp,
            float          tb,
            float          te,
            float          wap,
            float          eps,
            float          hmo,
            float          h1,
            float          hs,
            float          hmax,
            float          tc,
            float          tp,
            float          tz,
            float          aveDirection,
            float          aveSpreading,
            int            instrumentCode,
            float          meanDirection,
            float          meanSpreading) {
        this();
        setCode           (code          );
        setStationId      (stationId     );
        setDateTime       (dateTime      );
        setNumberReadings (numberReadings);
        setRecordLength   (recordLength  );
        setDeltaf         (deltaf        );
        setDeltat         (deltat        );
        setFrequency      (frequency     );
        setQp             (qp            );
        setTb             (tb            );
        setTe             (te            );
        setWap            (wap           );
        setEps            (eps           );
        setHmo            (hmo           );
        setH1             (h1            );
        setHs             (hs            );
        setHmax           (hmax          );
        setTc             (tc            );
        setTp             (tp            );
        setTz             (tz            );
        setAveDirection   (aveDirection  );
        setAveSpreading   (aveSpreading  );
        setInstrumentCode (instrumentCode);
        setMeanDirection  (meanDirection );
        setMeanSpreading  (meanSpreading );
        if (dbg) System.out.println ("<br>in WavData constructor 3"); // debug
    } // WavData constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode           (INTNULL  );
        setStationId      (CHARNULL );
        setDateTime       (DATENULL );
        setNumberReadings (INTNULL  );
        setRecordLength   (FLOATNULL);
        setDeltaf         (FLOATNULL);
        setDeltat         (FLOATNULL);
        setFrequency      (FLOATNULL);
        setQp             (FLOATNULL);
        setTb             (FLOATNULL);
        setTe             (FLOATNULL);
        setWap            (FLOATNULL);
        setEps            (FLOATNULL);
        setHmo            (FLOATNULL);
        setH1             (FLOATNULL);
        setHs             (FLOATNULL);
        setHmax           (FLOATNULL);
        setTc             (FLOATNULL);
        setTp             (FLOATNULL);
        setTz             (FLOATNULL);
        setAveDirection   (FLOATNULL);
        setAveSpreading   (FLOATNULL);
        setInstrumentCode (INTNULL  );
        setMeanDirection  (FLOATNULL);
        setMeanSpreading  (FLOATNULL);
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
     * Set the 'dateTime' class variable
     * @param  dateTime (Timestamp)
     */
    public int setDateTime(Timestamp dateTime) {
        try {
            if (dateTime == null) { this.dateTime = DATENULL; }
            if ( (dateTime.equals(DATENULL) || 
                  dateTime.equals(DATENULL2)) ||
                !(dateTime.before(DATE_TIME_MN) ||
                  dateTime.after(DATE_TIME_MX)) ) {
                this.dateTime = dateTime;
                dateTimeError = ERROR_NORMAL;
            } else {
                throw dateTimeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (java.util.Date)
     */
    public int setDateTime(java.util.Date dateTime) {
        try {
            setDateTime(new Timestamp(dateTime.getTime()));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (String)
     */
    public int setDateTime(String dateTime) {
        try {
            int len = dateTime.length();
            switch (len) {
            // date &/or times
                case 4: dateTime += "-01";
                case 7: dateTime += "-01";
                case 10: dateTime += " 00";
                case 13: dateTime += ":00";
                case 16: dateTime += ":00"; break;
                // times only
                case 5: dateTime = "1800-01-01 " + dateTime + ":00"; break;
                case 8: dateTime = "1800-01-01 " + dateTime; break;
            } // switch
            if (dbg) System.out.println ("dateTime = " + dateTime);
            setDateTime(Timestamp.valueOf(dateTime));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Called when an exception has occured
     * @param  dateTime (String)
     */
    private void setDateTimeError (Timestamp dateTime, Exception e, int error) {
        this.dateTime = dateTime;
        dateTimeErrorMessage = e.toString();
        dateTimeError = error;
    } // method setDateTimeError


    /**
     * Set the 'numberReadings' class variable
     * @param  numberReadings (int)
     */
    public int setNumberReadings(int numberReadings) {
        try {
            if ( ((numberReadings == INTNULL) || 
                  (numberReadings == INTNULL2)) ||
                !((numberReadings < NUMBER_READINGS_MN) ||
                  (numberReadings > NUMBER_READINGS_MX)) ) {
                this.numberReadings = numberReadings;
                numberReadingsError = ERROR_NORMAL;
            } else {
                throw numberReadingsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNumberReadingsError(INTNULL, e, ERROR_LOCAL);
        } // try
        return numberReadingsError;
    } // method setNumberReadings

    /**
     * Set the 'numberReadings' class variable
     * @param  numberReadings (Integer)
     */
    public int setNumberReadings(Integer numberReadings) {
        try {
            setNumberReadings(numberReadings.intValue());
        } catch (Exception e) {
            setNumberReadingsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberReadingsError;
    } // method setNumberReadings

    /**
     * Set the 'numberReadings' class variable
     * @param  numberReadings (Float)
     */
    public int setNumberReadings(Float numberReadings) {
        try {
            if (numberReadings.floatValue() == FLOATNULL) {
                setNumberReadings(INTNULL);
            } else {
                setNumberReadings(numberReadings.intValue());
            } // if (numberReadings.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setNumberReadingsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberReadingsError;
    } // method setNumberReadings

    /**
     * Set the 'numberReadings' class variable
     * @param  numberReadings (String)
     */
    public int setNumberReadings(String numberReadings) {
        try {
            setNumberReadings(new Integer(numberReadings).intValue());
        } catch (Exception e) {
            setNumberReadingsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberReadingsError;
    } // method setNumberReadings

    /**
     * Called when an exception has occured
     * @param  numberReadings (String)
     */
    private void setNumberReadingsError (int numberReadings, Exception e, int error) {
        this.numberReadings = numberReadings;
        numberReadingsErrorMessage = e.toString();
        numberReadingsError = error;
    } // method setNumberReadingsError


    /**
     * Set the 'recordLength' class variable
     * @param  recordLength (float)
     */
    public int setRecordLength(float recordLength) {
        try {
            if ( ((recordLength == FLOATNULL) || 
                  (recordLength == FLOATNULL2)) ||
                !((recordLength < RECORD_LENGTH_MN) ||
                  (recordLength > RECORD_LENGTH_MX)) ) {
                this.recordLength = recordLength;
                recordLengthError = ERROR_NORMAL;
            } else {
                throw recordLengthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setRecordLengthError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return recordLengthError;
    } // method setRecordLength

    /**
     * Set the 'recordLength' class variable
     * @param  recordLength (Integer)
     */
    public int setRecordLength(Integer recordLength) {
        try {
            setRecordLength(recordLength.floatValue());
        } catch (Exception e) {
            setRecordLengthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return recordLengthError;
    } // method setRecordLength

    /**
     * Set the 'recordLength' class variable
     * @param  recordLength (Float)
     */
    public int setRecordLength(Float recordLength) {
        try {
            setRecordLength(recordLength.floatValue());
        } catch (Exception e) {
            setRecordLengthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return recordLengthError;
    } // method setRecordLength

    /**
     * Set the 'recordLength' class variable
     * @param  recordLength (String)
     */
    public int setRecordLength(String recordLength) {
        try {
            setRecordLength(new Float(recordLength).floatValue());
        } catch (Exception e) {
            setRecordLengthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return recordLengthError;
    } // method setRecordLength

    /**
     * Called when an exception has occured
     * @param  recordLength (String)
     */
    private void setRecordLengthError (float recordLength, Exception e, int error) {
        this.recordLength = recordLength;
        recordLengthErrorMessage = e.toString();
        recordLengthError = error;
    } // method setRecordLengthError


    /**
     * Set the 'deltaf' class variable
     * @param  deltaf (float)
     */
    public int setDeltaf(float deltaf) {
        try {
            if ( ((deltaf == FLOATNULL) || 
                  (deltaf == FLOATNULL2)) ||
                !((deltaf < DELTAF_MN) ||
                  (deltaf > DELTAF_MX)) ) {
                this.deltaf = deltaf;
                deltafError = ERROR_NORMAL;
            } else {
                throw deltafOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDeltafError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return deltafError;
    } // method setDeltaf

    /**
     * Set the 'deltaf' class variable
     * @param  deltaf (Integer)
     */
    public int setDeltaf(Integer deltaf) {
        try {
            setDeltaf(deltaf.floatValue());
        } catch (Exception e) {
            setDeltafError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return deltafError;
    } // method setDeltaf

    /**
     * Set the 'deltaf' class variable
     * @param  deltaf (Float)
     */
    public int setDeltaf(Float deltaf) {
        try {
            setDeltaf(deltaf.floatValue());
        } catch (Exception e) {
            setDeltafError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return deltafError;
    } // method setDeltaf

    /**
     * Set the 'deltaf' class variable
     * @param  deltaf (String)
     */
    public int setDeltaf(String deltaf) {
        try {
            setDeltaf(new Float(deltaf).floatValue());
        } catch (Exception e) {
            setDeltafError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return deltafError;
    } // method setDeltaf

    /**
     * Called when an exception has occured
     * @param  deltaf (String)
     */
    private void setDeltafError (float deltaf, Exception e, int error) {
        this.deltaf = deltaf;
        deltafErrorMessage = e.toString();
        deltafError = error;
    } // method setDeltafError


    /**
     * Set the 'deltat' class variable
     * @param  deltat (float)
     */
    public int setDeltat(float deltat) {
        try {
            if ( ((deltat == FLOATNULL) || 
                  (deltat == FLOATNULL2)) ||
                !((deltat < DELTAT_MN) ||
                  (deltat > DELTAT_MX)) ) {
                this.deltat = deltat;
                deltatError = ERROR_NORMAL;
            } else {
                throw deltatOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDeltatError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return deltatError;
    } // method setDeltat

    /**
     * Set the 'deltat' class variable
     * @param  deltat (Integer)
     */
    public int setDeltat(Integer deltat) {
        try {
            setDeltat(deltat.floatValue());
        } catch (Exception e) {
            setDeltatError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return deltatError;
    } // method setDeltat

    /**
     * Set the 'deltat' class variable
     * @param  deltat (Float)
     */
    public int setDeltat(Float deltat) {
        try {
            setDeltat(deltat.floatValue());
        } catch (Exception e) {
            setDeltatError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return deltatError;
    } // method setDeltat

    /**
     * Set the 'deltat' class variable
     * @param  deltat (String)
     */
    public int setDeltat(String deltat) {
        try {
            setDeltat(new Float(deltat).floatValue());
        } catch (Exception e) {
            setDeltatError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return deltatError;
    } // method setDeltat

    /**
     * Called when an exception has occured
     * @param  deltat (String)
     */
    private void setDeltatError (float deltat, Exception e, int error) {
        this.deltat = deltat;
        deltatErrorMessage = e.toString();
        deltatError = error;
    } // method setDeltatError


    /**
     * Set the 'frequency' class variable
     * @param  frequency (float)
     */
    public int setFrequency(float frequency) {
        try {
            if ( ((frequency == FLOATNULL) || 
                  (frequency == FLOATNULL2)) ||
                !((frequency < FREQUENCY_MN) ||
                  (frequency > FREQUENCY_MX)) ) {
                this.frequency = frequency;
                frequencyError = ERROR_NORMAL;
            } else {
                throw frequencyOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFrequencyError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return frequencyError;
    } // method setFrequency

    /**
     * Set the 'frequency' class variable
     * @param  frequency (Integer)
     */
    public int setFrequency(Integer frequency) {
        try {
            setFrequency(frequency.floatValue());
        } catch (Exception e) {
            setFrequencyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return frequencyError;
    } // method setFrequency

    /**
     * Set the 'frequency' class variable
     * @param  frequency (Float)
     */
    public int setFrequency(Float frequency) {
        try {
            setFrequency(frequency.floatValue());
        } catch (Exception e) {
            setFrequencyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return frequencyError;
    } // method setFrequency

    /**
     * Set the 'frequency' class variable
     * @param  frequency (String)
     */
    public int setFrequency(String frequency) {
        try {
            setFrequency(new Float(frequency).floatValue());
        } catch (Exception e) {
            setFrequencyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return frequencyError;
    } // method setFrequency

    /**
     * Called when an exception has occured
     * @param  frequency (String)
     */
    private void setFrequencyError (float frequency, Exception e, int error) {
        this.frequency = frequency;
        frequencyErrorMessage = e.toString();
        frequencyError = error;
    } // method setFrequencyError


    /**
     * Set the 'qp' class variable
     * @param  qp (float)
     */
    public int setQp(float qp) {
        try {
            if ( ((qp == FLOATNULL) || 
                  (qp == FLOATNULL2)) ||
                !((qp < QP_MN) ||
                  (qp > QP_MX)) ) {
                this.qp = qp;
                qpError = ERROR_NORMAL;
            } else {
                throw qpOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setQpError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return qpError;
    } // method setQp

    /**
     * Set the 'qp' class variable
     * @param  qp (Integer)
     */
    public int setQp(Integer qp) {
        try {
            setQp(qp.floatValue());
        } catch (Exception e) {
            setQpError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return qpError;
    } // method setQp

    /**
     * Set the 'qp' class variable
     * @param  qp (Float)
     */
    public int setQp(Float qp) {
        try {
            setQp(qp.floatValue());
        } catch (Exception e) {
            setQpError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return qpError;
    } // method setQp

    /**
     * Set the 'qp' class variable
     * @param  qp (String)
     */
    public int setQp(String qp) {
        try {
            setQp(new Float(qp).floatValue());
        } catch (Exception e) {
            setQpError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return qpError;
    } // method setQp

    /**
     * Called when an exception has occured
     * @param  qp (String)
     */
    private void setQpError (float qp, Exception e, int error) {
        this.qp = qp;
        qpErrorMessage = e.toString();
        qpError = error;
    } // method setQpError


    /**
     * Set the 'tb' class variable
     * @param  tb (float)
     */
    public int setTb(float tb) {
        try {
            if ( ((tb == FLOATNULL) || 
                  (tb == FLOATNULL2)) ||
                !((tb < TB_MN) ||
                  (tb > TB_MX)) ) {
                this.tb = tb;
                tbError = ERROR_NORMAL;
            } else {
                throw tbOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTbError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return tbError;
    } // method setTb

    /**
     * Set the 'tb' class variable
     * @param  tb (Integer)
     */
    public int setTb(Integer tb) {
        try {
            setTb(tb.floatValue());
        } catch (Exception e) {
            setTbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tbError;
    } // method setTb

    /**
     * Set the 'tb' class variable
     * @param  tb (Float)
     */
    public int setTb(Float tb) {
        try {
            setTb(tb.floatValue());
        } catch (Exception e) {
            setTbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tbError;
    } // method setTb

    /**
     * Set the 'tb' class variable
     * @param  tb (String)
     */
    public int setTb(String tb) {
        try {
            setTb(new Float(tb).floatValue());
        } catch (Exception e) {
            setTbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tbError;
    } // method setTb

    /**
     * Called when an exception has occured
     * @param  tb (String)
     */
    private void setTbError (float tb, Exception e, int error) {
        this.tb = tb;
        tbErrorMessage = e.toString();
        tbError = error;
    } // method setTbError


    /**
     * Set the 'te' class variable
     * @param  te (float)
     */
    public int setTe(float te) {
        try {
            if ( ((te == FLOATNULL) || 
                  (te == FLOATNULL2)) ||
                !((te < TE_MN) ||
                  (te > TE_MX)) ) {
                this.te = te;
                teError = ERROR_NORMAL;
            } else {
                throw teOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return teError;
    } // method setTe

    /**
     * Set the 'te' class variable
     * @param  te (Integer)
     */
    public int setTe(Integer te) {
        try {
            setTe(te.floatValue());
        } catch (Exception e) {
            setTeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return teError;
    } // method setTe

    /**
     * Set the 'te' class variable
     * @param  te (Float)
     */
    public int setTe(Float te) {
        try {
            setTe(te.floatValue());
        } catch (Exception e) {
            setTeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return teError;
    } // method setTe

    /**
     * Set the 'te' class variable
     * @param  te (String)
     */
    public int setTe(String te) {
        try {
            setTe(new Float(te).floatValue());
        } catch (Exception e) {
            setTeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return teError;
    } // method setTe

    /**
     * Called when an exception has occured
     * @param  te (String)
     */
    private void setTeError (float te, Exception e, int error) {
        this.te = te;
        teErrorMessage = e.toString();
        teError = error;
    } // method setTeError


    /**
     * Set the 'wap' class variable
     * @param  wap (float)
     */
    public int setWap(float wap) {
        try {
            if ( ((wap == FLOATNULL) || 
                  (wap == FLOATNULL2)) ||
                !((wap < WAP_MN) ||
                  (wap > WAP_MX)) ) {
                this.wap = wap;
                wapError = ERROR_NORMAL;
            } else {
                throw wapOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWapError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return wapError;
    } // method setWap

    /**
     * Set the 'wap' class variable
     * @param  wap (Integer)
     */
    public int setWap(Integer wap) {
        try {
            setWap(wap.floatValue());
        } catch (Exception e) {
            setWapError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return wapError;
    } // method setWap

    /**
     * Set the 'wap' class variable
     * @param  wap (Float)
     */
    public int setWap(Float wap) {
        try {
            setWap(wap.floatValue());
        } catch (Exception e) {
            setWapError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return wapError;
    } // method setWap

    /**
     * Set the 'wap' class variable
     * @param  wap (String)
     */
    public int setWap(String wap) {
        try {
            setWap(new Float(wap).floatValue());
        } catch (Exception e) {
            setWapError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return wapError;
    } // method setWap

    /**
     * Called when an exception has occured
     * @param  wap (String)
     */
    private void setWapError (float wap, Exception e, int error) {
        this.wap = wap;
        wapErrorMessage = e.toString();
        wapError = error;
    } // method setWapError


    /**
     * Set the 'eps' class variable
     * @param  eps (float)
     */
    public int setEps(float eps) {
        try {
            if ( ((eps == FLOATNULL) || 
                  (eps == FLOATNULL2)) ||
                !((eps < EPS_MN) ||
                  (eps > EPS_MX)) ) {
                this.eps = eps;
                epsError = ERROR_NORMAL;
            } else {
                throw epsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setEpsError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return epsError;
    } // method setEps

    /**
     * Set the 'eps' class variable
     * @param  eps (Integer)
     */
    public int setEps(Integer eps) {
        try {
            setEps(eps.floatValue());
        } catch (Exception e) {
            setEpsError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return epsError;
    } // method setEps

    /**
     * Set the 'eps' class variable
     * @param  eps (Float)
     */
    public int setEps(Float eps) {
        try {
            setEps(eps.floatValue());
        } catch (Exception e) {
            setEpsError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return epsError;
    } // method setEps

    /**
     * Set the 'eps' class variable
     * @param  eps (String)
     */
    public int setEps(String eps) {
        try {
            setEps(new Float(eps).floatValue());
        } catch (Exception e) {
            setEpsError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return epsError;
    } // method setEps

    /**
     * Called when an exception has occured
     * @param  eps (String)
     */
    private void setEpsError (float eps, Exception e, int error) {
        this.eps = eps;
        epsErrorMessage = e.toString();
        epsError = error;
    } // method setEpsError


    /**
     * Set the 'hmo' class variable
     * @param  hmo (float)
     */
    public int setHmo(float hmo) {
        try {
            if ( ((hmo == FLOATNULL) || 
                  (hmo == FLOATNULL2)) ||
                !((hmo < HMO_MN) ||
                  (hmo > HMO_MX)) ) {
                this.hmo = hmo;
                hmoError = ERROR_NORMAL;
            } else {
                throw hmoOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setHmoError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return hmoError;
    } // method setHmo

    /**
     * Set the 'hmo' class variable
     * @param  hmo (Integer)
     */
    public int setHmo(Integer hmo) {
        try {
            setHmo(hmo.floatValue());
        } catch (Exception e) {
            setHmoError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hmoError;
    } // method setHmo

    /**
     * Set the 'hmo' class variable
     * @param  hmo (Float)
     */
    public int setHmo(Float hmo) {
        try {
            setHmo(hmo.floatValue());
        } catch (Exception e) {
            setHmoError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hmoError;
    } // method setHmo

    /**
     * Set the 'hmo' class variable
     * @param  hmo (String)
     */
    public int setHmo(String hmo) {
        try {
            setHmo(new Float(hmo).floatValue());
        } catch (Exception e) {
            setHmoError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hmoError;
    } // method setHmo

    /**
     * Called when an exception has occured
     * @param  hmo (String)
     */
    private void setHmoError (float hmo, Exception e, int error) {
        this.hmo = hmo;
        hmoErrorMessage = e.toString();
        hmoError = error;
    } // method setHmoError


    /**
     * Set the 'h1' class variable
     * @param  h1 (float)
     */
    public int setH1(float h1) {
        try {
            if ( ((h1 == FLOATNULL) || 
                  (h1 == FLOATNULL2)) ||
                !((h1 < H1_MN) ||
                  (h1 > H1_MX)) ) {
                this.h1 = h1;
                h1Error = ERROR_NORMAL;
            } else {
                throw h1OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setH1Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return h1Error;
    } // method setH1

    /**
     * Set the 'h1' class variable
     * @param  h1 (Integer)
     */
    public int setH1(Integer h1) {
        try {
            setH1(h1.floatValue());
        } catch (Exception e) {
            setH1Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return h1Error;
    } // method setH1

    /**
     * Set the 'h1' class variable
     * @param  h1 (Float)
     */
    public int setH1(Float h1) {
        try {
            setH1(h1.floatValue());
        } catch (Exception e) {
            setH1Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return h1Error;
    } // method setH1

    /**
     * Set the 'h1' class variable
     * @param  h1 (String)
     */
    public int setH1(String h1) {
        try {
            setH1(new Float(h1).floatValue());
        } catch (Exception e) {
            setH1Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return h1Error;
    } // method setH1

    /**
     * Called when an exception has occured
     * @param  h1 (String)
     */
    private void setH1Error (float h1, Exception e, int error) {
        this.h1 = h1;
        h1ErrorMessage = e.toString();
        h1Error = error;
    } // method setH1Error


    /**
     * Set the 'hs' class variable
     * @param  hs (float)
     */
    public int setHs(float hs) {
        try {
            if ( ((hs == FLOATNULL) || 
                  (hs == FLOATNULL2)) ||
                !((hs < HS_MN) ||
                  (hs > HS_MX)) ) {
                this.hs = hs;
                hsError = ERROR_NORMAL;
            } else {
                throw hsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setHsError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return hsError;
    } // method setHs

    /**
     * Set the 'hs' class variable
     * @param  hs (Integer)
     */
    public int setHs(Integer hs) {
        try {
            setHs(hs.floatValue());
        } catch (Exception e) {
            setHsError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hsError;
    } // method setHs

    /**
     * Set the 'hs' class variable
     * @param  hs (Float)
     */
    public int setHs(Float hs) {
        try {
            setHs(hs.floatValue());
        } catch (Exception e) {
            setHsError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hsError;
    } // method setHs

    /**
     * Set the 'hs' class variable
     * @param  hs (String)
     */
    public int setHs(String hs) {
        try {
            setHs(new Float(hs).floatValue());
        } catch (Exception e) {
            setHsError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hsError;
    } // method setHs

    /**
     * Called when an exception has occured
     * @param  hs (String)
     */
    private void setHsError (float hs, Exception e, int error) {
        this.hs = hs;
        hsErrorMessage = e.toString();
        hsError = error;
    } // method setHsError


    /**
     * Set the 'hmax' class variable
     * @param  hmax (float)
     */
    public int setHmax(float hmax) {
        try {
            if ( ((hmax == FLOATNULL) || 
                  (hmax == FLOATNULL2)) ||
                !((hmax < HMAX_MN) ||
                  (hmax > HMAX_MX)) ) {
                this.hmax = hmax;
                hmaxError = ERROR_NORMAL;
            } else {
                throw hmaxOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setHmaxError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return hmaxError;
    } // method setHmax

    /**
     * Set the 'hmax' class variable
     * @param  hmax (Integer)
     */
    public int setHmax(Integer hmax) {
        try {
            setHmax(hmax.floatValue());
        } catch (Exception e) {
            setHmaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hmaxError;
    } // method setHmax

    /**
     * Set the 'hmax' class variable
     * @param  hmax (Float)
     */
    public int setHmax(Float hmax) {
        try {
            setHmax(hmax.floatValue());
        } catch (Exception e) {
            setHmaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hmaxError;
    } // method setHmax

    /**
     * Set the 'hmax' class variable
     * @param  hmax (String)
     */
    public int setHmax(String hmax) {
        try {
            setHmax(new Float(hmax).floatValue());
        } catch (Exception e) {
            setHmaxError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hmaxError;
    } // method setHmax

    /**
     * Called when an exception has occured
     * @param  hmax (String)
     */
    private void setHmaxError (float hmax, Exception e, int error) {
        this.hmax = hmax;
        hmaxErrorMessage = e.toString();
        hmaxError = error;
    } // method setHmaxError


    /**
     * Set the 'tc' class variable
     * @param  tc (float)
     */
    public int setTc(float tc) {
        try {
            if ( ((tc == FLOATNULL) || 
                  (tc == FLOATNULL2)) ||
                !((tc < TC_MN) ||
                  (tc > TC_MX)) ) {
                this.tc = tc;
                tcError = ERROR_NORMAL;
            } else {
                throw tcOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTcError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return tcError;
    } // method setTc

    /**
     * Set the 'tc' class variable
     * @param  tc (Integer)
     */
    public int setTc(Integer tc) {
        try {
            setTc(tc.floatValue());
        } catch (Exception e) {
            setTcError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tcError;
    } // method setTc

    /**
     * Set the 'tc' class variable
     * @param  tc (Float)
     */
    public int setTc(Float tc) {
        try {
            setTc(tc.floatValue());
        } catch (Exception e) {
            setTcError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tcError;
    } // method setTc

    /**
     * Set the 'tc' class variable
     * @param  tc (String)
     */
    public int setTc(String tc) {
        try {
            setTc(new Float(tc).floatValue());
        } catch (Exception e) {
            setTcError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tcError;
    } // method setTc

    /**
     * Called when an exception has occured
     * @param  tc (String)
     */
    private void setTcError (float tc, Exception e, int error) {
        this.tc = tc;
        tcErrorMessage = e.toString();
        tcError = error;
    } // method setTcError


    /**
     * Set the 'tp' class variable
     * @param  tp (float)
     */
    public int setTp(float tp) {
        try {
            if ( ((tp == FLOATNULL) || 
                  (tp == FLOATNULL2)) ||
                !((tp < TP_MN) ||
                  (tp > TP_MX)) ) {
                this.tp = tp;
                tpError = ERROR_NORMAL;
            } else {
                throw tpOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTpError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return tpError;
    } // method setTp

    /**
     * Set the 'tp' class variable
     * @param  tp (Integer)
     */
    public int setTp(Integer tp) {
        try {
            setTp(tp.floatValue());
        } catch (Exception e) {
            setTpError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tpError;
    } // method setTp

    /**
     * Set the 'tp' class variable
     * @param  tp (Float)
     */
    public int setTp(Float tp) {
        try {
            setTp(tp.floatValue());
        } catch (Exception e) {
            setTpError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tpError;
    } // method setTp

    /**
     * Set the 'tp' class variable
     * @param  tp (String)
     */
    public int setTp(String tp) {
        try {
            setTp(new Float(tp).floatValue());
        } catch (Exception e) {
            setTpError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tpError;
    } // method setTp

    /**
     * Called when an exception has occured
     * @param  tp (String)
     */
    private void setTpError (float tp, Exception e, int error) {
        this.tp = tp;
        tpErrorMessage = e.toString();
        tpError = error;
    } // method setTpError


    /**
     * Set the 'tz' class variable
     * @param  tz (float)
     */
    public int setTz(float tz) {
        try {
            if ( ((tz == FLOATNULL) || 
                  (tz == FLOATNULL2)) ||
                !((tz < TZ_MN) ||
                  (tz > TZ_MX)) ) {
                this.tz = tz;
                tzError = ERROR_NORMAL;
            } else {
                throw tzOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTzError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return tzError;
    } // method setTz

    /**
     * Set the 'tz' class variable
     * @param  tz (Integer)
     */
    public int setTz(Integer tz) {
        try {
            setTz(tz.floatValue());
        } catch (Exception e) {
            setTzError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tzError;
    } // method setTz

    /**
     * Set the 'tz' class variable
     * @param  tz (Float)
     */
    public int setTz(Float tz) {
        try {
            setTz(tz.floatValue());
        } catch (Exception e) {
            setTzError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tzError;
    } // method setTz

    /**
     * Set the 'tz' class variable
     * @param  tz (String)
     */
    public int setTz(String tz) {
        try {
            setTz(new Float(tz).floatValue());
        } catch (Exception e) {
            setTzError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tzError;
    } // method setTz

    /**
     * Called when an exception has occured
     * @param  tz (String)
     */
    private void setTzError (float tz, Exception e, int error) {
        this.tz = tz;
        tzErrorMessage = e.toString();
        tzError = error;
    } // method setTzError


    /**
     * Set the 'aveDirection' class variable
     * @param  aveDirection (float)
     */
    public int setAveDirection(float aveDirection) {
        try {
            if ( ((aveDirection == FLOATNULL) || 
                  (aveDirection == FLOATNULL2)) ||
                !((aveDirection < AVE_DIRECTION_MN) ||
                  (aveDirection > AVE_DIRECTION_MX)) ) {
                this.aveDirection = aveDirection;
                aveDirectionError = ERROR_NORMAL;
            } else {
                throw aveDirectionOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAveDirectionError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return aveDirectionError;
    } // method setAveDirection

    /**
     * Set the 'aveDirection' class variable
     * @param  aveDirection (Integer)
     */
    public int setAveDirection(Integer aveDirection) {
        try {
            setAveDirection(aveDirection.floatValue());
        } catch (Exception e) {
            setAveDirectionError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return aveDirectionError;
    } // method setAveDirection

    /**
     * Set the 'aveDirection' class variable
     * @param  aveDirection (Float)
     */
    public int setAveDirection(Float aveDirection) {
        try {
            setAveDirection(aveDirection.floatValue());
        } catch (Exception e) {
            setAveDirectionError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return aveDirectionError;
    } // method setAveDirection

    /**
     * Set the 'aveDirection' class variable
     * @param  aveDirection (String)
     */
    public int setAveDirection(String aveDirection) {
        try {
            setAveDirection(new Float(aveDirection).floatValue());
        } catch (Exception e) {
            setAveDirectionError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return aveDirectionError;
    } // method setAveDirection

    /**
     * Called when an exception has occured
     * @param  aveDirection (String)
     */
    private void setAveDirectionError (float aveDirection, Exception e, int error) {
        this.aveDirection = aveDirection;
        aveDirectionErrorMessage = e.toString();
        aveDirectionError = error;
    } // method setAveDirectionError


    /**
     * Set the 'aveSpreading' class variable
     * @param  aveSpreading (float)
     */
    public int setAveSpreading(float aveSpreading) {
        try {
            if ( ((aveSpreading == FLOATNULL) || 
                  (aveSpreading == FLOATNULL2)) ||
                !((aveSpreading < AVE_SPREADING_MN) ||
                  (aveSpreading > AVE_SPREADING_MX)) ) {
                this.aveSpreading = aveSpreading;
                aveSpreadingError = ERROR_NORMAL;
            } else {
                throw aveSpreadingOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAveSpreadingError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return aveSpreadingError;
    } // method setAveSpreading

    /**
     * Set the 'aveSpreading' class variable
     * @param  aveSpreading (Integer)
     */
    public int setAveSpreading(Integer aveSpreading) {
        try {
            setAveSpreading(aveSpreading.floatValue());
        } catch (Exception e) {
            setAveSpreadingError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return aveSpreadingError;
    } // method setAveSpreading

    /**
     * Set the 'aveSpreading' class variable
     * @param  aveSpreading (Float)
     */
    public int setAveSpreading(Float aveSpreading) {
        try {
            setAveSpreading(aveSpreading.floatValue());
        } catch (Exception e) {
            setAveSpreadingError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return aveSpreadingError;
    } // method setAveSpreading

    /**
     * Set the 'aveSpreading' class variable
     * @param  aveSpreading (String)
     */
    public int setAveSpreading(String aveSpreading) {
        try {
            setAveSpreading(new Float(aveSpreading).floatValue());
        } catch (Exception e) {
            setAveSpreadingError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return aveSpreadingError;
    } // method setAveSpreading

    /**
     * Called when an exception has occured
     * @param  aveSpreading (String)
     */
    private void setAveSpreadingError (float aveSpreading, Exception e, int error) {
        this.aveSpreading = aveSpreading;
        aveSpreadingErrorMessage = e.toString();
        aveSpreadingError = error;
    } // method setAveSpreadingError


    /**
     * Set the 'instrumentCode' class variable
     * @param  instrumentCode (int)
     */
    public int setInstrumentCode(int instrumentCode) {
        try {
            if ( ((instrumentCode == INTNULL) || 
                  (instrumentCode == INTNULL2)) ||
                !((instrumentCode < INSTRUMENT_CODE_MN) ||
                  (instrumentCode > INSTRUMENT_CODE_MX)) ) {
                this.instrumentCode = instrumentCode;
                instrumentCodeError = ERROR_NORMAL;
            } else {
                throw instrumentCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setInstrumentCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return instrumentCodeError;
    } // method setInstrumentCode

    /**
     * Set the 'instrumentCode' class variable
     * @param  instrumentCode (Integer)
     */
    public int setInstrumentCode(Integer instrumentCode) {
        try {
            setInstrumentCode(instrumentCode.intValue());
        } catch (Exception e) {
            setInstrumentCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentCodeError;
    } // method setInstrumentCode

    /**
     * Set the 'instrumentCode' class variable
     * @param  instrumentCode (Float)
     */
    public int setInstrumentCode(Float instrumentCode) {
        try {
            if (instrumentCode.floatValue() == FLOATNULL) {
                setInstrumentCode(INTNULL);
            } else {
                setInstrumentCode(instrumentCode.intValue());
            } // if (instrumentCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setInstrumentCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentCodeError;
    } // method setInstrumentCode

    /**
     * Set the 'instrumentCode' class variable
     * @param  instrumentCode (String)
     */
    public int setInstrumentCode(String instrumentCode) {
        try {
            setInstrumentCode(new Integer(instrumentCode).intValue());
        } catch (Exception e) {
            setInstrumentCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return instrumentCodeError;
    } // method setInstrumentCode

    /**
     * Called when an exception has occured
     * @param  instrumentCode (String)
     */
    private void setInstrumentCodeError (int instrumentCode, Exception e, int error) {
        this.instrumentCode = instrumentCode;
        instrumentCodeErrorMessage = e.toString();
        instrumentCodeError = error;
    } // method setInstrumentCodeError


    /**
     * Set the 'meanDirection' class variable
     * @param  meanDirection (float)
     */
    public int setMeanDirection(float meanDirection) {
        try {
            if ( ((meanDirection == FLOATNULL) || 
                  (meanDirection == FLOATNULL2)) ||
                !((meanDirection < MEAN_DIRECTION_MN) ||
                  (meanDirection > MEAN_DIRECTION_MX)) ) {
                this.meanDirection = meanDirection;
                meanDirectionError = ERROR_NORMAL;
            } else {
                throw meanDirectionOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMeanDirectionError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return meanDirectionError;
    } // method setMeanDirection

    /**
     * Set the 'meanDirection' class variable
     * @param  meanDirection (Integer)
     */
    public int setMeanDirection(Integer meanDirection) {
        try {
            setMeanDirection(meanDirection.floatValue());
        } catch (Exception e) {
            setMeanDirectionError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return meanDirectionError;
    } // method setMeanDirection

    /**
     * Set the 'meanDirection' class variable
     * @param  meanDirection (Float)
     */
    public int setMeanDirection(Float meanDirection) {
        try {
            setMeanDirection(meanDirection.floatValue());
        } catch (Exception e) {
            setMeanDirectionError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return meanDirectionError;
    } // method setMeanDirection

    /**
     * Set the 'meanDirection' class variable
     * @param  meanDirection (String)
     */
    public int setMeanDirection(String meanDirection) {
        try {
            setMeanDirection(new Float(meanDirection).floatValue());
        } catch (Exception e) {
            setMeanDirectionError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return meanDirectionError;
    } // method setMeanDirection

    /**
     * Called when an exception has occured
     * @param  meanDirection (String)
     */
    private void setMeanDirectionError (float meanDirection, Exception e, int error) {
        this.meanDirection = meanDirection;
        meanDirectionErrorMessage = e.toString();
        meanDirectionError = error;
    } // method setMeanDirectionError


    /**
     * Set the 'meanSpreading' class variable
     * @param  meanSpreading (float)
     */
    public int setMeanSpreading(float meanSpreading) {
        try {
            if ( ((meanSpreading == FLOATNULL) || 
                  (meanSpreading == FLOATNULL2)) ||
                !((meanSpreading < MEAN_SPREADING_MN) ||
                  (meanSpreading > MEAN_SPREADING_MX)) ) {
                this.meanSpreading = meanSpreading;
                meanSpreadingError = ERROR_NORMAL;
            } else {
                throw meanSpreadingOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMeanSpreadingError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return meanSpreadingError;
    } // method setMeanSpreading

    /**
     * Set the 'meanSpreading' class variable
     * @param  meanSpreading (Integer)
     */
    public int setMeanSpreading(Integer meanSpreading) {
        try {
            setMeanSpreading(meanSpreading.floatValue());
        } catch (Exception e) {
            setMeanSpreadingError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return meanSpreadingError;
    } // method setMeanSpreading

    /**
     * Set the 'meanSpreading' class variable
     * @param  meanSpreading (Float)
     */
    public int setMeanSpreading(Float meanSpreading) {
        try {
            setMeanSpreading(meanSpreading.floatValue());
        } catch (Exception e) {
            setMeanSpreadingError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return meanSpreadingError;
    } // method setMeanSpreading

    /**
     * Set the 'meanSpreading' class variable
     * @param  meanSpreading (String)
     */
    public int setMeanSpreading(String meanSpreading) {
        try {
            setMeanSpreading(new Float(meanSpreading).floatValue());
        } catch (Exception e) {
            setMeanSpreadingError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return meanSpreadingError;
    } // method setMeanSpreading

    /**
     * Called when an exception has occured
     * @param  meanSpreading (String)
     */
    private void setMeanSpreadingError (float meanSpreading, Exception e, int error) {
        this.meanSpreading = meanSpreading;
        meanSpreadingErrorMessage = e.toString();
        meanSpreadingError = error;
    } // method setMeanSpreadingError


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
     * Return the 'dateTime' class variable
     * @return dateTime (Timestamp)
     */
    public Timestamp getDateTime() {
        return dateTime;
    } // method getDateTime

    /**
     * Return the 'dateTime' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateTime methods
     * @return dateTime (String)
     */
    public String getDateTime(String s) {
        if (dateTime.equals(DATENULL)) {
            return ("");
        } else {
            String dateTimeStr = dateTime.toString();
            return dateTimeStr.substring(0,dateTimeStr.indexOf('.'));
        } // if
    } // method getDateTime


    /**
     * Return the 'numberReadings' class variable
     * @return numberReadings (int)
     */
    public int getNumberReadings() {
        return numberReadings;
    } // method getNumberReadings

    /**
     * Return the 'numberReadings' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNumberReadings methods
     * @return numberReadings (String)
     */
    public String getNumberReadings(String s) {
        return ((numberReadings != INTNULL) ? new Integer(numberReadings).toString() : "");
    } // method getNumberReadings


    /**
     * Return the 'recordLength' class variable
     * @return recordLength (float)
     */
    public float getRecordLength() {
        return recordLength;
    } // method getRecordLength

    /**
     * Return the 'recordLength' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRecordLength methods
     * @return recordLength (String)
     */
    public String getRecordLength(String s) {
        return ((recordLength != FLOATNULL) ? new Float(recordLength).toString() : "");
    } // method getRecordLength


    /**
     * Return the 'deltaf' class variable
     * @return deltaf (float)
     */
    public float getDeltaf() {
        return deltaf;
    } // method getDeltaf

    /**
     * Return the 'deltaf' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDeltaf methods
     * @return deltaf (String)
     */
    public String getDeltaf(String s) {
        return ((deltaf != FLOATNULL) ? new Float(deltaf).toString() : "");
    } // method getDeltaf


    /**
     * Return the 'deltat' class variable
     * @return deltat (float)
     */
    public float getDeltat() {
        return deltat;
    } // method getDeltat

    /**
     * Return the 'deltat' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDeltat methods
     * @return deltat (String)
     */
    public String getDeltat(String s) {
        return ((deltat != FLOATNULL) ? new Float(deltat).toString() : "");
    } // method getDeltat


    /**
     * Return the 'frequency' class variable
     * @return frequency (float)
     */
    public float getFrequency() {
        return frequency;
    } // method getFrequency

    /**
     * Return the 'frequency' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFrequency methods
     * @return frequency (String)
     */
    public String getFrequency(String s) {
        return ((frequency != FLOATNULL) ? new Float(frequency).toString() : "");
    } // method getFrequency


    /**
     * Return the 'qp' class variable
     * @return qp (float)
     */
    public float getQp() {
        return qp;
    } // method getQp

    /**
     * Return the 'qp' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getQp methods
     * @return qp (String)
     */
    public String getQp(String s) {
        return ((qp != FLOATNULL) ? new Float(qp).toString() : "");
    } // method getQp


    /**
     * Return the 'tb' class variable
     * @return tb (float)
     */
    public float getTb() {
        return tb;
    } // method getTb

    /**
     * Return the 'tb' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTb methods
     * @return tb (String)
     */
    public String getTb(String s) {
        return ((tb != FLOATNULL) ? new Float(tb).toString() : "");
    } // method getTb


    /**
     * Return the 'te' class variable
     * @return te (float)
     */
    public float getTe() {
        return te;
    } // method getTe

    /**
     * Return the 'te' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTe methods
     * @return te (String)
     */
    public String getTe(String s) {
        return ((te != FLOATNULL) ? new Float(te).toString() : "");
    } // method getTe


    /**
     * Return the 'wap' class variable
     * @return wap (float)
     */
    public float getWap() {
        return wap;
    } // method getWap

    /**
     * Return the 'wap' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWap methods
     * @return wap (String)
     */
    public String getWap(String s) {
        return ((wap != FLOATNULL) ? new Float(wap).toString() : "");
    } // method getWap


    /**
     * Return the 'eps' class variable
     * @return eps (float)
     */
    public float getEps() {
        return eps;
    } // method getEps

    /**
     * Return the 'eps' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getEps methods
     * @return eps (String)
     */
    public String getEps(String s) {
        return ((eps != FLOATNULL) ? new Float(eps).toString() : "");
    } // method getEps


    /**
     * Return the 'hmo' class variable
     * @return hmo (float)
     */
    public float getHmo() {
        return hmo;
    } // method getHmo

    /**
     * Return the 'hmo' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getHmo methods
     * @return hmo (String)
     */
    public String getHmo(String s) {
        return ((hmo != FLOATNULL) ? new Float(hmo).toString() : "");
    } // method getHmo


    /**
     * Return the 'h1' class variable
     * @return h1 (float)
     */
    public float getH1() {
        return h1;
    } // method getH1

    /**
     * Return the 'h1' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getH1 methods
     * @return h1 (String)
     */
    public String getH1(String s) {
        return ((h1 != FLOATNULL) ? new Float(h1).toString() : "");
    } // method getH1


    /**
     * Return the 'hs' class variable
     * @return hs (float)
     */
    public float getHs() {
        return hs;
    } // method getHs

    /**
     * Return the 'hs' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getHs methods
     * @return hs (String)
     */
    public String getHs(String s) {
        return ((hs != FLOATNULL) ? new Float(hs).toString() : "");
    } // method getHs


    /**
     * Return the 'hmax' class variable
     * @return hmax (float)
     */
    public float getHmax() {
        return hmax;
    } // method getHmax

    /**
     * Return the 'hmax' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getHmax methods
     * @return hmax (String)
     */
    public String getHmax(String s) {
        return ((hmax != FLOATNULL) ? new Float(hmax).toString() : "");
    } // method getHmax


    /**
     * Return the 'tc' class variable
     * @return tc (float)
     */
    public float getTc() {
        return tc;
    } // method getTc

    /**
     * Return the 'tc' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTc methods
     * @return tc (String)
     */
    public String getTc(String s) {
        return ((tc != FLOATNULL) ? new Float(tc).toString() : "");
    } // method getTc


    /**
     * Return the 'tp' class variable
     * @return tp (float)
     */
    public float getTp() {
        return tp;
    } // method getTp

    /**
     * Return the 'tp' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTp methods
     * @return tp (String)
     */
    public String getTp(String s) {
        return ((tp != FLOATNULL) ? new Float(tp).toString() : "");
    } // method getTp


    /**
     * Return the 'tz' class variable
     * @return tz (float)
     */
    public float getTz() {
        return tz;
    } // method getTz

    /**
     * Return the 'tz' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTz methods
     * @return tz (String)
     */
    public String getTz(String s) {
        return ((tz != FLOATNULL) ? new Float(tz).toString() : "");
    } // method getTz


    /**
     * Return the 'aveDirection' class variable
     * @return aveDirection (float)
     */
    public float getAveDirection() {
        return aveDirection;
    } // method getAveDirection

    /**
     * Return the 'aveDirection' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAveDirection methods
     * @return aveDirection (String)
     */
    public String getAveDirection(String s) {
        return ((aveDirection != FLOATNULL) ? new Float(aveDirection).toString() : "");
    } // method getAveDirection


    /**
     * Return the 'aveSpreading' class variable
     * @return aveSpreading (float)
     */
    public float getAveSpreading() {
        return aveSpreading;
    } // method getAveSpreading

    /**
     * Return the 'aveSpreading' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAveSpreading methods
     * @return aveSpreading (String)
     */
    public String getAveSpreading(String s) {
        return ((aveSpreading != FLOATNULL) ? new Float(aveSpreading).toString() : "");
    } // method getAveSpreading


    /**
     * Return the 'instrumentCode' class variable
     * @return instrumentCode (int)
     */
    public int getInstrumentCode() {
        return instrumentCode;
    } // method getInstrumentCode

    /**
     * Return the 'instrumentCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getInstrumentCode methods
     * @return instrumentCode (String)
     */
    public String getInstrumentCode(String s) {
        return ((instrumentCode != INTNULL) ? new Integer(instrumentCode).toString() : "");
    } // method getInstrumentCode


    /**
     * Return the 'meanDirection' class variable
     * @return meanDirection (float)
     */
    public float getMeanDirection() {
        return meanDirection;
    } // method getMeanDirection

    /**
     * Return the 'meanDirection' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMeanDirection methods
     * @return meanDirection (String)
     */
    public String getMeanDirection(String s) {
        return ((meanDirection != FLOATNULL) ? new Float(meanDirection).toString() : "");
    } // method getMeanDirection


    /**
     * Return the 'meanSpreading' class variable
     * @return meanSpreading (float)
     */
    public float getMeanSpreading() {
        return meanSpreading;
    } // method getMeanSpreading

    /**
     * Return the 'meanSpreading' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMeanSpreading methods
     * @return meanSpreading (String)
     */
    public String getMeanSpreading(String s) {
        return ((meanSpreading != FLOATNULL) ? new Float(meanSpreading).toString() : "");
    } // method getMeanSpreading


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
            (stationId == CHARNULL) &&
            (dateTime.equals(DATENULL)) &&
            (numberReadings == INTNULL) &&
            (recordLength == FLOATNULL) &&
            (deltaf == FLOATNULL) &&
            (deltat == FLOATNULL) &&
            (frequency == FLOATNULL) &&
            (qp == FLOATNULL) &&
            (tb == FLOATNULL) &&
            (te == FLOATNULL) &&
            (wap == FLOATNULL) &&
            (eps == FLOATNULL) &&
            (hmo == FLOATNULL) &&
            (h1 == FLOATNULL) &&
            (hs == FLOATNULL) &&
            (hmax == FLOATNULL) &&
            (tc == FLOATNULL) &&
            (tp == FLOATNULL) &&
            (tz == FLOATNULL) &&
            (aveDirection == FLOATNULL) &&
            (aveSpreading == FLOATNULL) &&
            (instrumentCode == INTNULL) &&
            (meanDirection == FLOATNULL) &&
            (meanSpreading == FLOATNULL)) {
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
            stationIdError +
            dateTimeError +
            numberReadingsError +
            recordLengthError +
            deltafError +
            deltatError +
            frequencyError +
            qpError +
            tbError +
            teError +
            wapError +
            epsError +
            hmoError +
            h1Error +
            hsError +
            hmaxError +
            tcError +
            tpError +
            tzError +
            aveDirectionError +
            aveSpreadingError +
            instrumentCodeError +
            meanDirectionError +
            meanSpreadingError;
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
     * Gets the errorcode for the dateTime instance variable
     * @return errorcode (int)
     */
    public int getDateTimeError() {
        return dateTimeError;
    } // method getDateTimeError

    /**
     * Gets the errorMessage for the dateTime instance variable
     * @return errorMessage (String)
     */
    public String getDateTimeErrorMessage() {
        return dateTimeErrorMessage;
    } // method getDateTimeErrorMessage


    /**
     * Gets the errorcode for the numberReadings instance variable
     * @return errorcode (int)
     */
    public int getNumberReadingsError() {
        return numberReadingsError;
    } // method getNumberReadingsError

    /**
     * Gets the errorMessage for the numberReadings instance variable
     * @return errorMessage (String)
     */
    public String getNumberReadingsErrorMessage() {
        return numberReadingsErrorMessage;
    } // method getNumberReadingsErrorMessage


    /**
     * Gets the errorcode for the recordLength instance variable
     * @return errorcode (int)
     */
    public int getRecordLengthError() {
        return recordLengthError;
    } // method getRecordLengthError

    /**
     * Gets the errorMessage for the recordLength instance variable
     * @return errorMessage (String)
     */
    public String getRecordLengthErrorMessage() {
        return recordLengthErrorMessage;
    } // method getRecordLengthErrorMessage


    /**
     * Gets the errorcode for the deltaf instance variable
     * @return errorcode (int)
     */
    public int getDeltafError() {
        return deltafError;
    } // method getDeltafError

    /**
     * Gets the errorMessage for the deltaf instance variable
     * @return errorMessage (String)
     */
    public String getDeltafErrorMessage() {
        return deltafErrorMessage;
    } // method getDeltafErrorMessage


    /**
     * Gets the errorcode for the deltat instance variable
     * @return errorcode (int)
     */
    public int getDeltatError() {
        return deltatError;
    } // method getDeltatError

    /**
     * Gets the errorMessage for the deltat instance variable
     * @return errorMessage (String)
     */
    public String getDeltatErrorMessage() {
        return deltatErrorMessage;
    } // method getDeltatErrorMessage


    /**
     * Gets the errorcode for the frequency instance variable
     * @return errorcode (int)
     */
    public int getFrequencyError() {
        return frequencyError;
    } // method getFrequencyError

    /**
     * Gets the errorMessage for the frequency instance variable
     * @return errorMessage (String)
     */
    public String getFrequencyErrorMessage() {
        return frequencyErrorMessage;
    } // method getFrequencyErrorMessage


    /**
     * Gets the errorcode for the qp instance variable
     * @return errorcode (int)
     */
    public int getQpError() {
        return qpError;
    } // method getQpError

    /**
     * Gets the errorMessage for the qp instance variable
     * @return errorMessage (String)
     */
    public String getQpErrorMessage() {
        return qpErrorMessage;
    } // method getQpErrorMessage


    /**
     * Gets the errorcode for the tb instance variable
     * @return errorcode (int)
     */
    public int getTbError() {
        return tbError;
    } // method getTbError

    /**
     * Gets the errorMessage for the tb instance variable
     * @return errorMessage (String)
     */
    public String getTbErrorMessage() {
        return tbErrorMessage;
    } // method getTbErrorMessage


    /**
     * Gets the errorcode for the te instance variable
     * @return errorcode (int)
     */
    public int getTeError() {
        return teError;
    } // method getTeError

    /**
     * Gets the errorMessage for the te instance variable
     * @return errorMessage (String)
     */
    public String getTeErrorMessage() {
        return teErrorMessage;
    } // method getTeErrorMessage


    /**
     * Gets the errorcode for the wap instance variable
     * @return errorcode (int)
     */
    public int getWapError() {
        return wapError;
    } // method getWapError

    /**
     * Gets the errorMessage for the wap instance variable
     * @return errorMessage (String)
     */
    public String getWapErrorMessage() {
        return wapErrorMessage;
    } // method getWapErrorMessage


    /**
     * Gets the errorcode for the eps instance variable
     * @return errorcode (int)
     */
    public int getEpsError() {
        return epsError;
    } // method getEpsError

    /**
     * Gets the errorMessage for the eps instance variable
     * @return errorMessage (String)
     */
    public String getEpsErrorMessage() {
        return epsErrorMessage;
    } // method getEpsErrorMessage


    /**
     * Gets the errorcode for the hmo instance variable
     * @return errorcode (int)
     */
    public int getHmoError() {
        return hmoError;
    } // method getHmoError

    /**
     * Gets the errorMessage for the hmo instance variable
     * @return errorMessage (String)
     */
    public String getHmoErrorMessage() {
        return hmoErrorMessage;
    } // method getHmoErrorMessage


    /**
     * Gets the errorcode for the h1 instance variable
     * @return errorcode (int)
     */
    public int getH1Error() {
        return h1Error;
    } // method getH1Error

    /**
     * Gets the errorMessage for the h1 instance variable
     * @return errorMessage (String)
     */
    public String getH1ErrorMessage() {
        return h1ErrorMessage;
    } // method getH1ErrorMessage


    /**
     * Gets the errorcode for the hs instance variable
     * @return errorcode (int)
     */
    public int getHsError() {
        return hsError;
    } // method getHsError

    /**
     * Gets the errorMessage for the hs instance variable
     * @return errorMessage (String)
     */
    public String getHsErrorMessage() {
        return hsErrorMessage;
    } // method getHsErrorMessage


    /**
     * Gets the errorcode for the hmax instance variable
     * @return errorcode (int)
     */
    public int getHmaxError() {
        return hmaxError;
    } // method getHmaxError

    /**
     * Gets the errorMessage for the hmax instance variable
     * @return errorMessage (String)
     */
    public String getHmaxErrorMessage() {
        return hmaxErrorMessage;
    } // method getHmaxErrorMessage


    /**
     * Gets the errorcode for the tc instance variable
     * @return errorcode (int)
     */
    public int getTcError() {
        return tcError;
    } // method getTcError

    /**
     * Gets the errorMessage for the tc instance variable
     * @return errorMessage (String)
     */
    public String getTcErrorMessage() {
        return tcErrorMessage;
    } // method getTcErrorMessage


    /**
     * Gets the errorcode for the tp instance variable
     * @return errorcode (int)
     */
    public int getTpError() {
        return tpError;
    } // method getTpError

    /**
     * Gets the errorMessage for the tp instance variable
     * @return errorMessage (String)
     */
    public String getTpErrorMessage() {
        return tpErrorMessage;
    } // method getTpErrorMessage


    /**
     * Gets the errorcode for the tz instance variable
     * @return errorcode (int)
     */
    public int getTzError() {
        return tzError;
    } // method getTzError

    /**
     * Gets the errorMessage for the tz instance variable
     * @return errorMessage (String)
     */
    public String getTzErrorMessage() {
        return tzErrorMessage;
    } // method getTzErrorMessage


    /**
     * Gets the errorcode for the aveDirection instance variable
     * @return errorcode (int)
     */
    public int getAveDirectionError() {
        return aveDirectionError;
    } // method getAveDirectionError

    /**
     * Gets the errorMessage for the aveDirection instance variable
     * @return errorMessage (String)
     */
    public String getAveDirectionErrorMessage() {
        return aveDirectionErrorMessage;
    } // method getAveDirectionErrorMessage


    /**
     * Gets the errorcode for the aveSpreading instance variable
     * @return errorcode (int)
     */
    public int getAveSpreadingError() {
        return aveSpreadingError;
    } // method getAveSpreadingError

    /**
     * Gets the errorMessage for the aveSpreading instance variable
     * @return errorMessage (String)
     */
    public String getAveSpreadingErrorMessage() {
        return aveSpreadingErrorMessage;
    } // method getAveSpreadingErrorMessage


    /**
     * Gets the errorcode for the instrumentCode instance variable
     * @return errorcode (int)
     */
    public int getInstrumentCodeError() {
        return instrumentCodeError;
    } // method getInstrumentCodeError

    /**
     * Gets the errorMessage for the instrumentCode instance variable
     * @return errorMessage (String)
     */
    public String getInstrumentCodeErrorMessage() {
        return instrumentCodeErrorMessage;
    } // method getInstrumentCodeErrorMessage


    /**
     * Gets the errorcode for the meanDirection instance variable
     * @return errorcode (int)
     */
    public int getMeanDirectionError() {
        return meanDirectionError;
    } // method getMeanDirectionError

    /**
     * Gets the errorMessage for the meanDirection instance variable
     * @return errorMessage (String)
     */
    public String getMeanDirectionErrorMessage() {
        return meanDirectionErrorMessage;
    } // method getMeanDirectionErrorMessage


    /**
     * Gets the errorcode for the meanSpreading instance variable
     * @return errorcode (int)
     */
    public int getMeanSpreadingError() {
        return meanSpreadingError;
    } // method getMeanSpreadingError

    /**
     * Gets the errorMessage for the meanSpreading instance variable
     * @return errorMessage (String)
     */
    public String getMeanSpreadingErrorMessage() {
        return meanSpreadingErrorMessage;
    } // method getMeanSpreadingErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of WavData. e.g.<pre>
     * WavData wavData = new WavData(<val1>);
     * WavData wavDataArray[] = wavData.get();</pre>
     * will get the WavData record where code = <val1>.
     * @return Array of WavData (WavData[])
     */
    public WavData[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * WavData wavDataArray[] = 
     *     new WavData().get(WavData.CODE+"=<val1>");</pre>
     * will get the WavData record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of WavData (WavData[])
     */
    public WavData[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * WavData wavDataArray[] = 
     *     new WavData().get("1=1",wavData.CODE);</pre>
     * will get the all the WavData records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WavData (WavData[])
     */
    public WavData[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = WavData.CODE,WavData.STATION_ID;
     * String where = WavData.CODE + "=<val1";
     * String order = WavData.CODE;
     * WavData wavDataArray[] = 
     *     new WavData().get(columns, where, order);</pre>
     * will get the code and stationId colums of all WavData records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WavData (WavData[])
     */
    public WavData[] get (String fields, String where, String order) {
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
     * and transforms it into an array of WavData.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private WavData[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol           = db.getColNumber(CODE);
        int stationIdCol      = db.getColNumber(STATION_ID);
        int dateTimeCol       = db.getColNumber(DATE_TIME);
        int numberReadingsCol = db.getColNumber(NUMBER_READINGS);
        int recordLengthCol   = db.getColNumber(RECORD_LENGTH);
        int deltafCol         = db.getColNumber(DELTAF);
        int deltatCol         = db.getColNumber(DELTAT);
        int frequencyCol      = db.getColNumber(FREQUENCY);
        int qpCol             = db.getColNumber(QP);
        int tbCol             = db.getColNumber(TB);
        int teCol             = db.getColNumber(TE);
        int wapCol            = db.getColNumber(WAP);
        int epsCol            = db.getColNumber(EPS);
        int hmoCol            = db.getColNumber(HMO);
        int h1Col             = db.getColNumber(H1);
        int hsCol             = db.getColNumber(HS);
        int hmaxCol           = db.getColNumber(HMAX);
        int tcCol             = db.getColNumber(TC);
        int tpCol             = db.getColNumber(TP);
        int tzCol             = db.getColNumber(TZ);
        int aveDirectionCol   = db.getColNumber(AVE_DIRECTION);
        int aveSpreadingCol   = db.getColNumber(AVE_SPREADING);
        int instrumentCodeCol = db.getColNumber(INSTRUMENT_CODE);
        int meanDirectionCol  = db.getColNumber(MEAN_DIRECTION);
        int meanSpreadingCol  = db.getColNumber(MEAN_SPREADING);
        WavData[] cArray = new WavData[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new WavData();
            if (codeCol != -1)
                cArray[i].setCode          ((String) row.elementAt(codeCol));
            if (stationIdCol != -1)
                cArray[i].setStationId     ((String) row.elementAt(stationIdCol));
            if (dateTimeCol != -1)
                cArray[i].setDateTime      ((String) row.elementAt(dateTimeCol));
            if (numberReadingsCol != -1)
                cArray[i].setNumberReadings((String) row.elementAt(numberReadingsCol));
            if (recordLengthCol != -1)
                cArray[i].setRecordLength  ((String) row.elementAt(recordLengthCol));
            if (deltafCol != -1)
                cArray[i].setDeltaf        ((String) row.elementAt(deltafCol));
            if (deltatCol != -1)
                cArray[i].setDeltat        ((String) row.elementAt(deltatCol));
            if (frequencyCol != -1)
                cArray[i].setFrequency     ((String) row.elementAt(frequencyCol));
            if (qpCol != -1)
                cArray[i].setQp            ((String) row.elementAt(qpCol));
            if (tbCol != -1)
                cArray[i].setTb            ((String) row.elementAt(tbCol));
            if (teCol != -1)
                cArray[i].setTe            ((String) row.elementAt(teCol));
            if (wapCol != -1)
                cArray[i].setWap           ((String) row.elementAt(wapCol));
            if (epsCol != -1)
                cArray[i].setEps           ((String) row.elementAt(epsCol));
            if (hmoCol != -1)
                cArray[i].setHmo           ((String) row.elementAt(hmoCol));
            if (h1Col != -1)
                cArray[i].setH1            ((String) row.elementAt(h1Col));
            if (hsCol != -1)
                cArray[i].setHs            ((String) row.elementAt(hsCol));
            if (hmaxCol != -1)
                cArray[i].setHmax          ((String) row.elementAt(hmaxCol));
            if (tcCol != -1)
                cArray[i].setTc            ((String) row.elementAt(tcCol));
            if (tpCol != -1)
                cArray[i].setTp            ((String) row.elementAt(tpCol));
            if (tzCol != -1)
                cArray[i].setTz            ((String) row.elementAt(tzCol));
            if (aveDirectionCol != -1)
                cArray[i].setAveDirection  ((String) row.elementAt(aveDirectionCol));
            if (aveSpreadingCol != -1)
                cArray[i].setAveSpreading  ((String) row.elementAt(aveSpreadingCol));
            if (instrumentCodeCol != -1)
                cArray[i].setInstrumentCode((String) row.elementAt(instrumentCodeCol));
            if (meanDirectionCol != -1)
                cArray[i].setMeanDirection ((String) row.elementAt(meanDirectionCol));
            if (meanSpreadingCol != -1)
                cArray[i].setMeanSpreading ((String) row.elementAt(meanSpreadingCol));
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
     *     new WavData(
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
     *         <val25>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     stationId      = <val2>,
     *     dateTime       = <val3>,
     *     numberReadings = <val4>,
     *     recordLength   = <val5>,
     *     deltaf         = <val6>,
     *     deltat         = <val7>,
     *     frequency      = <val8>,
     *     qp             = <val9>,
     *     tb             = <val10>,
     *     te             = <val11>,
     *     wap            = <val12>,
     *     eps            = <val13>,
     *     hmo            = <val14>,
     *     h1             = <val15>,
     *     hs             = <val16>,
     *     hmax           = <val17>,
     *     tc             = <val18>,
     *     tp             = <val19>,
     *     tz             = <val20>,
     *     aveDirection   = <val21>,
     *     aveSpreading   = <val22>,
     *     instrumentCode = <val23>,
     *     meanDirection  = <val24>,
     *     meanSpreading  = <val25>.
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
     * Boolean success = new WavData(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.DATENULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where stationId = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WavData wavData = new WavData();
     * success = wavData.del(WavData.CODE+"=<val1>");</pre>
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
     * update are taken from the WavData argument, .e.g.<pre>
     * Boolean success
     * WavData updWavData = new WavData();
     * updWavData.setStationId(<val2>);
     * WavData whereWavData = new WavData(<val1>);
     * success = whereWavData.upd(updWavData);</pre>
     * will update StationId to <val2> for all records where 
     * code = <val1>.
     * @param wavData  A WavData variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(WavData wavData) {
        return db.update (TABLE, createColVals(wavData), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WavData updWavData = new WavData();
     * updWavData.setStationId(<val2>);
     * WavData whereWavData = new WavData();
     * success = whereWavData.upd(
     *     updWavData, WavData.CODE+"=<val1>");</pre>
     * will update StationId to <val2> for all records where 
     * code = <val1>.
     * @param  updWavData  A WavData variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(WavData wavData, String where) {
        return db.update (TABLE, createColVals(wavData), where);
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
        if (getStationId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATION_ID + "='" + getStationId() + "'";
        } // if getStationId
        if (!getDateTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME +
                "=" + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getNumberReadings() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NUMBER_READINGS + "=" + getNumberReadings("");
        } // if getNumberReadings
        if (getRecordLength() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RECORD_LENGTH + "=" + getRecordLength("");
        } // if getRecordLength
        if (getDeltaf() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DELTAF + "=" + getDeltaf("");
        } // if getDeltaf
        if (getDeltat() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DELTAT + "=" + getDeltat("");
        } // if getDeltat
        if (getFrequency() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FREQUENCY + "=" + getFrequency("");
        } // if getFrequency
        if (getQp() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + QP + "=" + getQp("");
        } // if getQp
        if (getTb() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TB + "=" + getTb("");
        } // if getTb
        if (getTe() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TE + "=" + getTe("");
        } // if getTe
        if (getWap() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WAP + "=" + getWap("");
        } // if getWap
        if (getEps() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + EPS + "=" + getEps("");
        } // if getEps
        if (getHmo() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + HMO + "=" + getHmo("");
        } // if getHmo
        if (getH1() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + H1 + "=" + getH1("");
        } // if getH1
        if (getHs() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + HS + "=" + getHs("");
        } // if getHs
        if (getHmax() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + HMAX + "=" + getHmax("");
        } // if getHmax
        if (getTc() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TC + "=" + getTc("");
        } // if getTc
        if (getTp() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TP + "=" + getTp("");
        } // if getTp
        if (getTz() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TZ + "=" + getTz("");
        } // if getTz
        if (getAveDirection() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + AVE_DIRECTION + "=" + getAveDirection("");
        } // if getAveDirection
        if (getAveSpreading() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + AVE_SPREADING + "=" + getAveSpreading("");
        } // if getAveSpreading
        if (getInstrumentCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTRUMENT_CODE + "=" + getInstrumentCode("");
        } // if getInstrumentCode
        if (getMeanDirection() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MEAN_DIRECTION + "=" + getMeanDirection("");
        } // if getMeanDirection
        if (getMeanSpreading() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MEAN_SPREADING + "=" + getMeanSpreading("");
        } // if getMeanSpreading
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(WavData aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (!aVar.getDateTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME +"=";
            colVals += (aVar.getDateTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTime()));
        } // if aVar.getDateTime
        if (aVar.getNumberReadings() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NUMBER_READINGS +"=";
            colVals += (aVar.getNumberReadings() == INTNULL2 ?
                "null" : aVar.getNumberReadings(""));
        } // if aVar.getNumberReadings
        if (aVar.getRecordLength() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RECORD_LENGTH +"=";
            colVals += (aVar.getRecordLength() == FLOATNULL2 ?
                "null" : aVar.getRecordLength(""));
        } // if aVar.getRecordLength
        if (aVar.getDeltaf() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DELTAF +"=";
            colVals += (aVar.getDeltaf() == FLOATNULL2 ?
                "null" : aVar.getDeltaf(""));
        } // if aVar.getDeltaf
        if (aVar.getDeltat() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DELTAT +"=";
            colVals += (aVar.getDeltat() == FLOATNULL2 ?
                "null" : aVar.getDeltat(""));
        } // if aVar.getDeltat
        if (aVar.getFrequency() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FREQUENCY +"=";
            colVals += (aVar.getFrequency() == FLOATNULL2 ?
                "null" : aVar.getFrequency(""));
        } // if aVar.getFrequency
        if (aVar.getQp() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += QP +"=";
            colVals += (aVar.getQp() == FLOATNULL2 ?
                "null" : aVar.getQp(""));
        } // if aVar.getQp
        if (aVar.getTb() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TB +"=";
            colVals += (aVar.getTb() == FLOATNULL2 ?
                "null" : aVar.getTb(""));
        } // if aVar.getTb
        if (aVar.getTe() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TE +"=";
            colVals += (aVar.getTe() == FLOATNULL2 ?
                "null" : aVar.getTe(""));
        } // if aVar.getTe
        if (aVar.getWap() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WAP +"=";
            colVals += (aVar.getWap() == FLOATNULL2 ?
                "null" : aVar.getWap(""));
        } // if aVar.getWap
        if (aVar.getEps() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += EPS +"=";
            colVals += (aVar.getEps() == FLOATNULL2 ?
                "null" : aVar.getEps(""));
        } // if aVar.getEps
        if (aVar.getHmo() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += HMO +"=";
            colVals += (aVar.getHmo() == FLOATNULL2 ?
                "null" : aVar.getHmo(""));
        } // if aVar.getHmo
        if (aVar.getH1() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += H1 +"=";
            colVals += (aVar.getH1() == FLOATNULL2 ?
                "null" : aVar.getH1(""));
        } // if aVar.getH1
        if (aVar.getHs() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += HS +"=";
            colVals += (aVar.getHs() == FLOATNULL2 ?
                "null" : aVar.getHs(""));
        } // if aVar.getHs
        if (aVar.getHmax() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += HMAX +"=";
            colVals += (aVar.getHmax() == FLOATNULL2 ?
                "null" : aVar.getHmax(""));
        } // if aVar.getHmax
        if (aVar.getTc() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TC +"=";
            colVals += (aVar.getTc() == FLOATNULL2 ?
                "null" : aVar.getTc(""));
        } // if aVar.getTc
        if (aVar.getTp() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TP +"=";
            colVals += (aVar.getTp() == FLOATNULL2 ?
                "null" : aVar.getTp(""));
        } // if aVar.getTp
        if (aVar.getTz() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TZ +"=";
            colVals += (aVar.getTz() == FLOATNULL2 ?
                "null" : aVar.getTz(""));
        } // if aVar.getTz
        if (aVar.getAveDirection() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += AVE_DIRECTION +"=";
            colVals += (aVar.getAveDirection() == FLOATNULL2 ?
                "null" : aVar.getAveDirection(""));
        } // if aVar.getAveDirection
        if (aVar.getAveSpreading() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += AVE_SPREADING +"=";
            colVals += (aVar.getAveSpreading() == FLOATNULL2 ?
                "null" : aVar.getAveSpreading(""));
        } // if aVar.getAveSpreading
        if (aVar.getInstrumentCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTRUMENT_CODE +"=";
            colVals += (aVar.getInstrumentCode() == INTNULL2 ?
                "null" : aVar.getInstrumentCode(""));
        } // if aVar.getInstrumentCode
        if (aVar.getMeanDirection() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MEAN_DIRECTION +"=";
            colVals += (aVar.getMeanDirection() == FLOATNULL2 ?
                "null" : aVar.getMeanDirection(""));
        } // if aVar.getMeanDirection
        if (aVar.getMeanSpreading() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MEAN_SPREADING +"=";
            colVals += (aVar.getMeanSpreading() == FLOATNULL2 ?
                "null" : aVar.getMeanSpreading(""));
        } // if aVar.getMeanSpreading
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getStationId() != CHARNULL) {
            columns = columns + "," + STATION_ID;
        } // if getStationId
        if (!getDateTime().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME;
        } // if getDateTime
        if (getNumberReadings() != INTNULL) {
            columns = columns + "," + NUMBER_READINGS;
        } // if getNumberReadings
        if (getRecordLength() != FLOATNULL) {
            columns = columns + "," + RECORD_LENGTH;
        } // if getRecordLength
        if (getDeltaf() != FLOATNULL) {
            columns = columns + "," + DELTAF;
        } // if getDeltaf
        if (getDeltat() != FLOATNULL) {
            columns = columns + "," + DELTAT;
        } // if getDeltat
        if (getFrequency() != FLOATNULL) {
            columns = columns + "," + FREQUENCY;
        } // if getFrequency
        if (getQp() != FLOATNULL) {
            columns = columns + "," + QP;
        } // if getQp
        if (getTb() != FLOATNULL) {
            columns = columns + "," + TB;
        } // if getTb
        if (getTe() != FLOATNULL) {
            columns = columns + "," + TE;
        } // if getTe
        if (getWap() != FLOATNULL) {
            columns = columns + "," + WAP;
        } // if getWap
        if (getEps() != FLOATNULL) {
            columns = columns + "," + EPS;
        } // if getEps
        if (getHmo() != FLOATNULL) {
            columns = columns + "," + HMO;
        } // if getHmo
        if (getH1() != FLOATNULL) {
            columns = columns + "," + H1;
        } // if getH1
        if (getHs() != FLOATNULL) {
            columns = columns + "," + HS;
        } // if getHs
        if (getHmax() != FLOATNULL) {
            columns = columns + "," + HMAX;
        } // if getHmax
        if (getTc() != FLOATNULL) {
            columns = columns + "," + TC;
        } // if getTc
        if (getTp() != FLOATNULL) {
            columns = columns + "," + TP;
        } // if getTp
        if (getTz() != FLOATNULL) {
            columns = columns + "," + TZ;
        } // if getTz
        if (getAveDirection() != FLOATNULL) {
            columns = columns + "," + AVE_DIRECTION;
        } // if getAveDirection
        if (getAveSpreading() != FLOATNULL) {
            columns = columns + "," + AVE_SPREADING;
        } // if getAveSpreading
        if (getInstrumentCode() != INTNULL) {
            columns = columns + "," + INSTRUMENT_CODE;
        } // if getInstrumentCode
        if (getMeanDirection() != FLOATNULL) {
            columns = columns + "," + MEAN_DIRECTION;
        } // if getMeanDirection
        if (getMeanSpreading() != FLOATNULL) {
            columns = columns + "," + MEAN_SPREADING;
        } // if getMeanSpreading
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
        if (getStationId() != CHARNULL) {
            values  = values  + ",'" + getStationId() + "'";
        } // if getStationId
        if (!getDateTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getNumberReadings() != INTNULL) {
            values  = values  + "," + getNumberReadings("");
        } // if getNumberReadings
        if (getRecordLength() != FLOATNULL) {
            values  = values  + "," + getRecordLength("");
        } // if getRecordLength
        if (getDeltaf() != FLOATNULL) {
            values  = values  + "," + getDeltaf("");
        } // if getDeltaf
        if (getDeltat() != FLOATNULL) {
            values  = values  + "," + getDeltat("");
        } // if getDeltat
        if (getFrequency() != FLOATNULL) {
            values  = values  + "," + getFrequency("");
        } // if getFrequency
        if (getQp() != FLOATNULL) {
            values  = values  + "," + getQp("");
        } // if getQp
        if (getTb() != FLOATNULL) {
            values  = values  + "," + getTb("");
        } // if getTb
        if (getTe() != FLOATNULL) {
            values  = values  + "," + getTe("");
        } // if getTe
        if (getWap() != FLOATNULL) {
            values  = values  + "," + getWap("");
        } // if getWap
        if (getEps() != FLOATNULL) {
            values  = values  + "," + getEps("");
        } // if getEps
        if (getHmo() != FLOATNULL) {
            values  = values  + "," + getHmo("");
        } // if getHmo
        if (getH1() != FLOATNULL) {
            values  = values  + "," + getH1("");
        } // if getH1
        if (getHs() != FLOATNULL) {
            values  = values  + "," + getHs("");
        } // if getHs
        if (getHmax() != FLOATNULL) {
            values  = values  + "," + getHmax("");
        } // if getHmax
        if (getTc() != FLOATNULL) {
            values  = values  + "," + getTc("");
        } // if getTc
        if (getTp() != FLOATNULL) {
            values  = values  + "," + getTp("");
        } // if getTp
        if (getTz() != FLOATNULL) {
            values  = values  + "," + getTz("");
        } // if getTz
        if (getAveDirection() != FLOATNULL) {
            values  = values  + "," + getAveDirection("");
        } // if getAveDirection
        if (getAveSpreading() != FLOATNULL) {
            values  = values  + "," + getAveSpreading("");
        } // if getAveSpreading
        if (getInstrumentCode() != INTNULL) {
            values  = values  + "," + getInstrumentCode("");
        } // if getInstrumentCode
        if (getMeanDirection() != FLOATNULL) {
            values  = values  + "," + getMeanDirection("");
        } // if getMeanDirection
        if (getMeanSpreading() != FLOATNULL) {
            values  = values  + "," + getMeanSpreading("");
        } // if getMeanSpreading
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")           + "|" +
            getStationId("")      + "|" +
            getDateTime("")       + "|" +
            getNumberReadings("") + "|" +
            getRecordLength("")   + "|" +
            getDeltaf("")         + "|" +
            getDeltat("")         + "|" +
            getFrequency("")      + "|" +
            getQp("")             + "|" +
            getTb("")             + "|" +
            getTe("")             + "|" +
            getWap("")            + "|" +
            getEps("")            + "|" +
            getHmo("")            + "|" +
            getH1("")             + "|" +
            getHs("")             + "|" +
            getHmax("")           + "|" +
            getTc("")             + "|" +
            getTp("")             + "|" +
            getTz("")             + "|" +
            getAveDirection("")   + "|" +
            getAveSpreading("")   + "|" +
            getInstrumentCode("") + "|" +
            getMeanDirection("")  + "|" +
            getMeanSpreading("")  + "|";
    } // method toString

} // class WavData
