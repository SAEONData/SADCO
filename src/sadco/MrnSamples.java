package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SAMPLES table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnSamples extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SAMPLES";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The piCode field name */
    public static final String PI_CODE = "PI_CODE";
    /** The sampleNumber field name */
    public static final String SAMPLE_NUMBER = "SAMPLE_NUMBER";
    /** The sampleUnits field name */
    public static final String SAMPLE_UNITS = "SAMPLE_UNITS";
    /** The typeCode field name */
    public static final String TYPE_CODE = "TYPE_CODE";
    /** The sampleFormat field name */
    public static final String SAMPLE_FORMAT = "SAMPLE_FORMAT";
    /** The remarks field name */
    public static final String REMARKS = "REMARKS";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    surveyId;
    private String    piCode;
    private int       sampleNumber;
    private String    sampleUnits;
    private String    typeCode;
    private String    sampleFormat;
    private String    remarks;

    /** The error variables  */
    private int codeError         = ERROR_NORMAL;
    private int surveyIdError     = ERROR_NORMAL;
    private int piCodeError       = ERROR_NORMAL;
    private int sampleNumberError = ERROR_NORMAL;
    private int sampleUnitsError  = ERROR_NORMAL;
    private int typeCodeError     = ERROR_NORMAL;
    private int sampleFormatError = ERROR_NORMAL;
    private int remarksError      = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage         = "";
    private String surveyIdErrorMessage     = "";
    private String piCodeErrorMessage       = "";
    private String sampleNumberErrorMessage = "";
    private String sampleUnitsErrorMessage  = "";
    private String typeCodeErrorMessage     = "";
    private String sampleFormatErrorMessage = "";
    private String remarksErrorMessage      = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       SAMPLE_NUMBER_MN = INTMIN;
    public static final int       SAMPLE_NUMBER_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception sampleNumberOutOfBoundsException =
        new Exception ("'sampleNumber' out of bounds: " +
            SAMPLE_NUMBER_MN + " - " + SAMPLE_NUMBER_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnSamples() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnSamples constructor 1"); // debug
    } // MrnSamples constructor

    /**
     * Instantiate a MrnSamples object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MrnSamples(
            int code) {
        this();
        setCode         (code        );
        if (dbg) System.out.println ("<br>in MrnSamples constructor 2"); // debug
    } // MrnSamples constructor

    /**
     * Instantiate a MrnSamples object and initialize the instance variables.
     * @param code          The code         (int)
     * @param surveyId      The surveyId     (String)
     * @param piCode        The piCode       (String)
     * @param sampleNumber  The sampleNumber (int)
     * @param sampleUnits   The sampleUnits  (String)
     * @param typeCode      The typeCode     (String)
     * @param sampleFormat  The sampleFormat (String)
     * @param remarks       The remarks      (String)
     */
    public MrnSamples(
            int    code,
            String surveyId,
            String piCode,
            int    sampleNumber,
            String sampleUnits,
            String typeCode,
            String sampleFormat,
            String remarks) {
        this();
        setCode         (code        );
        setSurveyId     (surveyId    );
        setPiCode       (piCode      );
        setSampleNumber (sampleNumber);
        setSampleUnits  (sampleUnits );
        setTypeCode     (typeCode    );
        setSampleFormat (sampleFormat);
        setRemarks      (remarks     );
        if (dbg) System.out.println ("<br>in MrnSamples constructor 3"); // debug
    } // MrnSamples constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode         (INTNULL );
        setSurveyId     (CHARNULL);
        setPiCode       (CHARNULL);
        setSampleNumber (INTNULL );
        setSampleUnits  (CHARNULL);
        setTypeCode     (CHARNULL);
        setSampleFormat (CHARNULL);
        setRemarks      (CHARNULL);
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
     * Set the 'piCode' class variable
     * @param  piCode (String)
     */
    public int setPiCode(String piCode) {
        try {
            this.piCode = piCode;
            if (this.piCode != CHARNULL) {
                this.piCode = stripCRLF(this.piCode.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>piCode = " + this.piCode);
        } catch (Exception e) {
            setPiCodeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return piCodeError;
    } // method setPiCode

    /**
     * Called when an exception has occured
     * @param  piCode (String)
     */
    private void setPiCodeError (String piCode, Exception e, int error) {
        this.piCode = piCode;
        piCodeErrorMessage = e.toString();
        piCodeError = error;
    } // method setPiCodeError


    /**
     * Set the 'sampleNumber' class variable
     * @param  sampleNumber (int)
     */
    public int setSampleNumber(int sampleNumber) {
        try {
            if ( ((sampleNumber == INTNULL) ||
                  (sampleNumber == INTNULL2)) ||
                !((sampleNumber < SAMPLE_NUMBER_MN) ||
                  (sampleNumber > SAMPLE_NUMBER_MX)) ) {
                this.sampleNumber = sampleNumber;
                sampleNumberError = ERROR_NORMAL;
            } else {
                throw sampleNumberOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSampleNumberError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sampleNumberError;
    } // method setSampleNumber

    /**
     * Set the 'sampleNumber' class variable
     * @param  sampleNumber (Integer)
     */
    public int setSampleNumber(Integer sampleNumber) {
        try {
            setSampleNumber(sampleNumber.intValue());
        } catch (Exception e) {
            setSampleNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sampleNumberError;
    } // method setSampleNumber

    /**
     * Set the 'sampleNumber' class variable
     * @param  sampleNumber (Float)
     */
    public int setSampleNumber(Float sampleNumber) {
        try {
            if (sampleNumber.floatValue() == FLOATNULL) {
                setSampleNumber(INTNULL);
            } else {
                setSampleNumber(sampleNumber.intValue());
            } // if (sampleNumber.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSampleNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sampleNumberError;
    } // method setSampleNumber

    /**
     * Set the 'sampleNumber' class variable
     * @param  sampleNumber (String)
     */
    public int setSampleNumber(String sampleNumber) {
        try {
            setSampleNumber(new Integer(sampleNumber).intValue());
        } catch (Exception e) {
            setSampleNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sampleNumberError;
    } // method setSampleNumber

    /**
     * Called when an exception has occured
     * @param  sampleNumber (String)
     */
    private void setSampleNumberError (int sampleNumber, Exception e, int error) {
        this.sampleNumber = sampleNumber;
        sampleNumberErrorMessage = e.toString();
        sampleNumberError = error;
    } // method setSampleNumberError


    /**
     * Set the 'sampleUnits' class variable
     * @param  sampleUnits (String)
     */
    public int setSampleUnits(String sampleUnits) {
        try {
            this.sampleUnits = sampleUnits;
            if (this.sampleUnits != CHARNULL) {
                this.sampleUnits = stripCRLF(this.sampleUnits.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>sampleUnits = " + this.sampleUnits);
        } catch (Exception e) {
            setSampleUnitsError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return sampleUnitsError;
    } // method setSampleUnits

    /**
     * Called when an exception has occured
     * @param  sampleUnits (String)
     */
    private void setSampleUnitsError (String sampleUnits, Exception e, int error) {
        this.sampleUnits = sampleUnits;
        sampleUnitsErrorMessage = e.toString();
        sampleUnitsError = error;
    } // method setSampleUnitsError


    /**
     * Set the 'typeCode' class variable
     * @param  typeCode (String)
     */
    public int setTypeCode(String typeCode) {
        try {
            this.typeCode = typeCode;
            if (this.typeCode != CHARNULL) {
                this.typeCode = stripCRLF(this.typeCode.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>typeCode = " + this.typeCode);
        } catch (Exception e) {
            setTypeCodeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return typeCodeError;
    } // method setTypeCode

    /**
     * Called when an exception has occured
     * @param  typeCode (String)
     */
    private void setTypeCodeError (String typeCode, Exception e, int error) {
        this.typeCode = typeCode;
        typeCodeErrorMessage = e.toString();
        typeCodeError = error;
    } // method setTypeCodeError


    /**
     * Set the 'sampleFormat' class variable
     * @param  sampleFormat (String)
     */
    public int setSampleFormat(String sampleFormat) {
        try {
            this.sampleFormat = sampleFormat;
            if (this.sampleFormat != CHARNULL) {
                this.sampleFormat = stripCRLF(this.sampleFormat.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>sampleFormat = " + this.sampleFormat);
        } catch (Exception e) {
            setSampleFormatError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return sampleFormatError;
    } // method setSampleFormat

    /**
     * Called when an exception has occured
     * @param  sampleFormat (String)
     */
    private void setSampleFormatError (String sampleFormat, Exception e, int error) {
        this.sampleFormat = sampleFormat;
        sampleFormatErrorMessage = e.toString();
        sampleFormatError = error;
    } // method setSampleFormatError


    /**
     * Set the 'remarks' class variable
     * @param  remarks (String)
     */
    public int setRemarks(String remarks) {
        try {
            this.remarks = remarks;
            if (this.remarks != CHARNULL) {
                this.remarks = stripCRLF(this.remarks.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>remarks = " + this.remarks);
        } catch (Exception e) {
            setRemarksError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return remarksError;
    } // method setRemarks

    /**
     * Called when an exception has occured
     * @param  remarks (String)
     */
    private void setRemarksError (String remarks, Exception e, int error) {
        this.remarks = remarks;
        remarksErrorMessage = e.toString();
        remarksError = error;
    } // method setRemarksError


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
     * Return the 'piCode' class variable
     * @return piCode (String)
     */
    public String getPiCode() {
        return piCode;
    } // method getPiCode

    /**
     * Return the 'piCode' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPiCode methods
     * @return piCode (String)
     */
    public String getPiCode(String s) {
        return (piCode != CHARNULL ? piCode.replace('"','\'') : "");
    } // method getPiCode


    /**
     * Return the 'sampleNumber' class variable
     * @return sampleNumber (int)
     */
    public int getSampleNumber() {
        return sampleNumber;
    } // method getSampleNumber

    /**
     * Return the 'sampleNumber' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSampleNumber methods
     * @return sampleNumber (String)
     */
    public String getSampleNumber(String s) {
        return ((sampleNumber != INTNULL) ? new Integer(sampleNumber).toString() : "");
    } // method getSampleNumber


    /**
     * Return the 'sampleUnits' class variable
     * @return sampleUnits (String)
     */
    public String getSampleUnits() {
        return sampleUnits;
    } // method getSampleUnits

    /**
     * Return the 'sampleUnits' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSampleUnits methods
     * @return sampleUnits (String)
     */
    public String getSampleUnits(String s) {
        return (sampleUnits != CHARNULL ? sampleUnits.replace('"','\'') : "");
    } // method getSampleUnits


    /**
     * Return the 'typeCode' class variable
     * @return typeCode (String)
     */
    public String getTypeCode() {
        return typeCode;
    } // method getTypeCode

    /**
     * Return the 'typeCode' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTypeCode methods
     * @return typeCode (String)
     */
    public String getTypeCode(String s) {
        return (typeCode != CHARNULL ? typeCode.replace('"','\'') : "");
    } // method getTypeCode


    /**
     * Return the 'sampleFormat' class variable
     * @return sampleFormat (String)
     */
    public String getSampleFormat() {
        return sampleFormat;
    } // method getSampleFormat

    /**
     * Return the 'sampleFormat' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSampleFormat methods
     * @return sampleFormat (String)
     */
    public String getSampleFormat(String s) {
        return (sampleFormat != CHARNULL ? sampleFormat.replace('"','\'') : "");
    } // method getSampleFormat


    /**
     * Return the 'remarks' class variable
     * @return remarks (String)
     */
    public String getRemarks() {
        return remarks;
    } // method getRemarks

    /**
     * Return the 'remarks' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRemarks methods
     * @return remarks (String)
     */
    public String getRemarks(String s) {
        return (remarks != CHARNULL ? remarks.replace('"','\'') : "");
    } // method getRemarks


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
            (surveyId == CHARNULL) &&
            (piCode == CHARNULL) &&
            (sampleNumber == INTNULL) &&
            (sampleUnits == CHARNULL) &&
            (typeCode == CHARNULL) &&
            (sampleFormat == CHARNULL) &&
            (remarks == CHARNULL)) {
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
            surveyIdError +
            piCodeError +
            sampleNumberError +
            sampleUnitsError +
            typeCodeError +
            sampleFormatError +
            remarksError;
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
     * Gets the errorcode for the piCode instance variable
     * @return errorcode (int)
     */
    public int getPiCodeError() {
        return piCodeError;
    } // method getPiCodeError

    /**
     * Gets the errorMessage for the piCode instance variable
     * @return errorMessage (String)
     */
    public String getPiCodeErrorMessage() {
        return piCodeErrorMessage;
    } // method getPiCodeErrorMessage


    /**
     * Gets the errorcode for the sampleNumber instance variable
     * @return errorcode (int)
     */
    public int getSampleNumberError() {
        return sampleNumberError;
    } // method getSampleNumberError

    /**
     * Gets the errorMessage for the sampleNumber instance variable
     * @return errorMessage (String)
     */
    public String getSampleNumberErrorMessage() {
        return sampleNumberErrorMessage;
    } // method getSampleNumberErrorMessage


    /**
     * Gets the errorcode for the sampleUnits instance variable
     * @return errorcode (int)
     */
    public int getSampleUnitsError() {
        return sampleUnitsError;
    } // method getSampleUnitsError

    /**
     * Gets the errorMessage for the sampleUnits instance variable
     * @return errorMessage (String)
     */
    public String getSampleUnitsErrorMessage() {
        return sampleUnitsErrorMessage;
    } // method getSampleUnitsErrorMessage


    /**
     * Gets the errorcode for the typeCode instance variable
     * @return errorcode (int)
     */
    public int getTypeCodeError() {
        return typeCodeError;
    } // method getTypeCodeError

    /**
     * Gets the errorMessage for the typeCode instance variable
     * @return errorMessage (String)
     */
    public String getTypeCodeErrorMessage() {
        return typeCodeErrorMessage;
    } // method getTypeCodeErrorMessage


    /**
     * Gets the errorcode for the sampleFormat instance variable
     * @return errorcode (int)
     */
    public int getSampleFormatError() {
        return sampleFormatError;
    } // method getSampleFormatError

    /**
     * Gets the errorMessage for the sampleFormat instance variable
     * @return errorMessage (String)
     */
    public String getSampleFormatErrorMessage() {
        return sampleFormatErrorMessage;
    } // method getSampleFormatErrorMessage


    /**
     * Gets the errorcode for the remarks instance variable
     * @return errorcode (int)
     */
    public int getRemarksError() {
        return remarksError;
    } // method getRemarksError

    /**
     * Gets the errorMessage for the remarks instance variable
     * @return errorMessage (String)
     */
    public String getRemarksErrorMessage() {
        return remarksErrorMessage;
    } // method getRemarksErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnSamples. e.g.<pre>
     * MrnSamples samples = new MrnSamples(<val1>);
     * MrnSamples samplesArray[] = samples.get();</pre>
     * will get the MrnSamples record where code = <val1>.
     * @return Array of MrnSamples (MrnSamples[])
     */
    public MrnSamples[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnSamples samplesArray[] =
     *     new MrnSamples().get(MrnSamples.CODE+"=<val1>");</pre>
     * will get the MrnSamples record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnSamples (MrnSamples[])
     */
    public MrnSamples[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnSamples samplesArray[] =
     *     new MrnSamples().get("1=1",samples.CODE);</pre>
     * will get the all the MrnSamples records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSamples (MrnSamples[])
     */
    public MrnSamples[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnSamples.CODE,MrnSamples.SURVEY_ID;
     * String where = MrnSamples.CODE + "=<val1";
     * String order = MrnSamples.CODE;
     * MrnSamples samplesArray[] =
     *     new MrnSamples().get(columns, where, order);</pre>
     * will get the code and surveyId colums of all MrnSamples records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSamples (MrnSamples[])
     */
    public MrnSamples[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnSamples.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnSamples[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol         = db.getColNumber(CODE);
        int surveyIdCol     = db.getColNumber(SURVEY_ID);
        int piCodeCol       = db.getColNumber(PI_CODE);
        int sampleNumberCol = db.getColNumber(SAMPLE_NUMBER);
        int sampleUnitsCol  = db.getColNumber(SAMPLE_UNITS);
        int typeCodeCol     = db.getColNumber(TYPE_CODE);
        int sampleFormatCol = db.getColNumber(SAMPLE_FORMAT);
        int remarksCol      = db.getColNumber(REMARKS);
        MrnSamples[] cArray = new MrnSamples[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnSamples();
            if (codeCol != -1)
                cArray[i].setCode        ((String) row.elementAt(codeCol));
            if (surveyIdCol != -1)
                cArray[i].setSurveyId    ((String) row.elementAt(surveyIdCol));
            if (piCodeCol != -1)
                cArray[i].setPiCode      ((String) row.elementAt(piCodeCol));
            if (sampleNumberCol != -1)
                cArray[i].setSampleNumber((String) row.elementAt(sampleNumberCol));
            if (sampleUnitsCol != -1)
                cArray[i].setSampleUnits ((String) row.elementAt(sampleUnitsCol));
            if (typeCodeCol != -1)
                cArray[i].setTypeCode    ((String) row.elementAt(typeCodeCol));
            if (sampleFormatCol != -1)
                cArray[i].setSampleFormat((String) row.elementAt(sampleFormatCol));
            if (remarksCol != -1)
                cArray[i].setRemarks     ((String) row.elementAt(remarksCol));
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
     *     new MrnSamples(
     *         INTNULL,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     surveyId     = <val2>,
     *     piCode       = <val3>,
     *     sampleNumber = <val4>,
     *     sampleUnits  = <val5>,
     *     typeCode     = <val6>,
     *     sampleFormat = <val7>,
     *     remarks      = <val8>.
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
     * Boolean success = new MrnSamples(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where surveyId = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSamples samples = new MrnSamples();
     * success = samples.del(MrnSamples.CODE+"=<val1>");</pre>
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
     * update are taken from the MrnSamples argument, .e.g.<pre>
     * Boolean success
     * MrnSamples updMrnSamples = new MrnSamples();
     * updMrnSamples.setSurveyId(<val2>);
     * MrnSamples whereMrnSamples = new MrnSamples(<val1>);
     * success = whereMrnSamples.upd(updMrnSamples);</pre>
     * will update SurveyId to <val2> for all records where
     * code = <val1>.
     * @param  samples  A MrnSamples variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSamples samples) {
        return db.update (TABLE, createColVals(samples), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSamples updMrnSamples = new MrnSamples();
     * updMrnSamples.setSurveyId(<val2>);
     * MrnSamples whereMrnSamples = new MrnSamples();
     * success = whereMrnSamples.upd(
     *     updMrnSamples, MrnSamples.CODE+"=<val1>");</pre>
     * will update SurveyId to <val2> for all records where
     * code = <val1>.
     * @param  samples  A MrnSamples variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSamples samples, String where) {
        return db.update (TABLE, createColVals(samples), where);
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
        if (getSurveyId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_ID + "='" + getSurveyId() + "'";
        } // if getSurveyId
        if (getPiCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PI_CODE + "='" + getPiCode() + "'";
        } // if getPiCode
        if (getSampleNumber() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SAMPLE_NUMBER + "=" + getSampleNumber("");
        } // if getSampleNumber
        if (getSampleUnits() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SAMPLE_UNITS + "='" + getSampleUnits() + "'";
        } // if getSampleUnits
        if (getTypeCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TYPE_CODE + "='" + getTypeCode() + "'";
        } // if getTypeCode
        if (getSampleFormat() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SAMPLE_FORMAT + "='" + getSampleFormat() + "'";
        } // if getSampleFormat
        if (getRemarks() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + REMARKS + "='" + getRemarks() + "'";
        } // if getRemarks
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnSamples aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getPiCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PI_CODE +"=";
            colVals += (aVar.getPiCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPiCode() + "'");
        } // if aVar.getPiCode
        if (aVar.getSampleNumber() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SAMPLE_NUMBER +"=";
            colVals += (aVar.getSampleNumber() == INTNULL2 ?
                "null" : aVar.getSampleNumber(""));
        } // if aVar.getSampleNumber
        if (aVar.getSampleUnits() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SAMPLE_UNITS +"=";
            colVals += (aVar.getSampleUnits().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSampleUnits() + "'");
        } // if aVar.getSampleUnits
        if (aVar.getTypeCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TYPE_CODE +"=";
            colVals += (aVar.getTypeCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTypeCode() + "'");
        } // if aVar.getTypeCode
        if (aVar.getSampleFormat() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SAMPLE_FORMAT +"=";
            colVals += (aVar.getSampleFormat().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSampleFormat() + "'");
        } // if aVar.getSampleFormat
        if (aVar.getRemarks() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += REMARKS +"=";
            colVals += (aVar.getRemarks().equals(CHARNULL2) ?
                "null" : "'" + aVar.getRemarks() + "'");
        } // if aVar.getRemarks
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getSurveyId() != CHARNULL) {
            columns = columns + "," + SURVEY_ID;
        } // if getSurveyId
        if (getPiCode() != CHARNULL) {
            columns = columns + "," + PI_CODE;
        } // if getPiCode
        if (getSampleNumber() != INTNULL) {
            columns = columns + "," + SAMPLE_NUMBER;
        } // if getSampleNumber
        if (getSampleUnits() != CHARNULL) {
            columns = columns + "," + SAMPLE_UNITS;
        } // if getSampleUnits
        if (getTypeCode() != CHARNULL) {
            columns = columns + "," + TYPE_CODE;
        } // if getTypeCode
        if (getSampleFormat() != CHARNULL) {
            columns = columns + "," + SAMPLE_FORMAT;
        } // if getSampleFormat
        if (getRemarks() != CHARNULL) {
            columns = columns + "," + REMARKS;
        } // if getRemarks
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
        if (getSurveyId() != CHARNULL) {
            values  = values  + ",'" + getSurveyId() + "'";
        } // if getSurveyId
        if (getPiCode() != CHARNULL) {
            values  = values  + ",'" + getPiCode() + "'";
        } // if getPiCode
        if (getSampleNumber() != INTNULL) {
            values  = values  + "," + getSampleNumber("");
        } // if getSampleNumber
        if (getSampleUnits() != CHARNULL) {
            values  = values  + ",'" + getSampleUnits() + "'";
        } // if getSampleUnits
        if (getTypeCode() != CHARNULL) {
            values  = values  + ",'" + getTypeCode() + "'";
        } // if getTypeCode
        if (getSampleFormat() != CHARNULL) {
            values  = values  + ",'" + getSampleFormat() + "'";
        } // if getSampleFormat
        if (getRemarks() != CHARNULL) {
            values  = values  + ",'" + getRemarks() + "'";
        } // if getRemarks
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")         + "|" +
            getSurveyId("")     + "|" +
            getPiCode("")       + "|" +
            getSampleNumber("") + "|" +
            getSampleUnits("")  + "|" +
            getTypeCode("")     + "|" +
            getSampleFormat("") + "|" +
            getRemarks("")      + "|";
    } // method toString

} // class MrnSamples
