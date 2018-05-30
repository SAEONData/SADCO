package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SURVEY_CONSTITUENT table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnSurveyConstituent extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SURVEY_CONSTITUENT";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The disciplineCode field name */
    public static final String DISCIPLINE_CODE = "DISCIPLINE_CODE";
    /** The constituentCode field name */
    public static final String CONSTITUENT_CODE = "CONSTITUENT_CODE";
    /** The fractionCode field name */
    public static final String FRACTION_CODE = "FRACTION_CODE";
    /** The deviceCode field name */
    public static final String DEVICE_CODE = "DEVICE_CODE";
    /** The methodCode field name */
    public static final String METHOD_CODE = "METHOD_CODE";
    /** The standardCode field name */
    public static final String STANDARD_CODE = "STANDARD_CODE";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    surveyId;
    private int       disciplineCode;
    private int       constituentCode;
    private int       fractionCode;
    private int       deviceCode;
    private int       methodCode;
    private int       standardCode;

    /** The error variables  */
    private int surveyIdError        = ERROR_NORMAL;
    private int disciplineCodeError  = ERROR_NORMAL;
    private int constituentCodeError = ERROR_NORMAL;
    private int fractionCodeError    = ERROR_NORMAL;
    private int deviceCodeError      = ERROR_NORMAL;
    private int methodCodeError      = ERROR_NORMAL;
    private int standardCodeError    = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String surveyIdErrorMessage        = "";
    private String disciplineCodeErrorMessage  = "";
    private String constituentCodeErrorMessage = "";
    private String fractionCodeErrorMessage    = "";
    private String deviceCodeErrorMessage      = "";
    private String methodCodeErrorMessage      = "";
    private String standardCodeErrorMessage    = "";

    /** The min-max constants for all numerics */
    public static final int       DISCIPLINE_CODE_MN = INTMIN;
    public static final int       DISCIPLINE_CODE_MX = INTMAX;
    public static final int       CONSTITUENT_CODE_MN = INTMIN;
    public static final int       CONSTITUENT_CODE_MX = INTMAX;
    public static final int       FRACTION_CODE_MN = INTMIN;
    public static final int       FRACTION_CODE_MX = INTMAX;
    public static final int       DEVICE_CODE_MN = INTMIN;
    public static final int       DEVICE_CODE_MX = INTMAX;
    public static final int       METHOD_CODE_MN = INTMIN;
    public static final int       METHOD_CODE_MX = INTMAX;
    public static final int       STANDARD_CODE_MN = INTMIN;
    public static final int       STANDARD_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception disciplineCodeOutOfBoundsException =
        new Exception ("'disciplineCode' out of bounds: " +
            DISCIPLINE_CODE_MN + " - " + DISCIPLINE_CODE_MX);
    Exception constituentCodeOutOfBoundsException =
        new Exception ("'constituentCode' out of bounds: " +
            CONSTITUENT_CODE_MN + " - " + CONSTITUENT_CODE_MX);
    Exception fractionCodeOutOfBoundsException =
        new Exception ("'fractionCode' out of bounds: " +
            FRACTION_CODE_MN + " - " + FRACTION_CODE_MX);
    Exception deviceCodeOutOfBoundsException =
        new Exception ("'deviceCode' out of bounds: " +
            DEVICE_CODE_MN + " - " + DEVICE_CODE_MX);
    Exception methodCodeOutOfBoundsException =
        new Exception ("'methodCode' out of bounds: " +
            METHOD_CODE_MN + " - " + METHOD_CODE_MX);
    Exception standardCodeOutOfBoundsException =
        new Exception ("'standardCode' out of bounds: " +
            STANDARD_CODE_MN + " - " + STANDARD_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnSurveyConstituent() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnSurveyConstituent constructor 1"); // debug
    } // MrnSurveyConstituent constructor

    /**
     * Instantiate a MrnSurveyConstituent object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     */
    public MrnSurveyConstituent(
            String surveyId) {
        this();
        setSurveyId        (surveyId       );
        if (dbg) System.out.println ("<br>in MrnSurveyConstituent constructor 2"); // debug
    } // MrnSurveyConstituent constructor

    /**
     * Instantiate a MrnSurveyConstituent object and initialize the instance variables.
     * @param surveyId         The surveyId        (String)
     * @param disciplineCode   The disciplineCode  (int)
     * @param constituentCode  The constituentCode (int)
     * @param fractionCode     The fractionCode    (int)
     * @param deviceCode       The deviceCode      (int)
     * @param methodCode       The methodCode      (int)
     * @param standardCode     The standardCode    (int)
     */
    public MrnSurveyConstituent(
            String surveyId,
            int    disciplineCode,
            int    constituentCode,
            int    fractionCode,
            int    deviceCode,
            int    methodCode,
            int    standardCode) {
        this();
        setSurveyId        (surveyId       );
        setDisciplineCode  (disciplineCode );
        setConstituentCode (constituentCode);
        setFractionCode    (fractionCode   );
        setDeviceCode      (deviceCode     );
        setMethodCode      (methodCode     );
        setStandardCode    (standardCode   );
        if (dbg) System.out.println ("<br>in MrnSurveyConstituent constructor 3"); // debug
    } // MrnSurveyConstituent constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSurveyId        (CHARNULL);
        setDisciplineCode  (INTNULL );
        setConstituentCode (INTNULL );
        setFractionCode    (INTNULL );
        setDeviceCode      (INTNULL );
        setMethodCode      (INTNULL );
        setStandardCode    (INTNULL );
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
     * Set the 'disciplineCode' class variable
     * @param  disciplineCode (int)
     */
    public int setDisciplineCode(int disciplineCode) {
        try {
            if ( ((disciplineCode == INTNULL) ||
                  (disciplineCode == INTNULL2)) ||
                !((disciplineCode < DISCIPLINE_CODE_MN) ||
                  (disciplineCode > DISCIPLINE_CODE_MX)) ) {
                this.disciplineCode = disciplineCode;
                disciplineCodeError = ERROR_NORMAL;
            } else {
                throw disciplineCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDisciplineCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return disciplineCodeError;
    } // method setDisciplineCode

    /**
     * Set the 'disciplineCode' class variable
     * @param  disciplineCode (Integer)
     */
    public int setDisciplineCode(Integer disciplineCode) {
        try {
            setDisciplineCode(disciplineCode.intValue());
        } catch (Exception e) {
            setDisciplineCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return disciplineCodeError;
    } // method setDisciplineCode

    /**
     * Set the 'disciplineCode' class variable
     * @param  disciplineCode (Float)
     */
    public int setDisciplineCode(Float disciplineCode) {
        try {
            if (disciplineCode.floatValue() == FLOATNULL) {
                setDisciplineCode(INTNULL);
            } else {
                setDisciplineCode(disciplineCode.intValue());
            } // if (disciplineCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDisciplineCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return disciplineCodeError;
    } // method setDisciplineCode

    /**
     * Set the 'disciplineCode' class variable
     * @param  disciplineCode (String)
     */
    public int setDisciplineCode(String disciplineCode) {
        try {
            setDisciplineCode(new Integer(disciplineCode).intValue());
        } catch (Exception e) {
            setDisciplineCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return disciplineCodeError;
    } // method setDisciplineCode

    /**
     * Called when an exception has occured
     * @param  disciplineCode (String)
     */
    private void setDisciplineCodeError (int disciplineCode, Exception e, int error) {
        this.disciplineCode = disciplineCode;
        disciplineCodeErrorMessage = e.toString();
        disciplineCodeError = error;
    } // method setDisciplineCodeError


    /**
     * Set the 'constituentCode' class variable
     * @param  constituentCode (int)
     */
    public int setConstituentCode(int constituentCode) {
        try {
            if ( ((constituentCode == INTNULL) ||
                  (constituentCode == INTNULL2)) ||
                !((constituentCode < CONSTITUENT_CODE_MN) ||
                  (constituentCode > CONSTITUENT_CODE_MX)) ) {
                this.constituentCode = constituentCode;
                constituentCodeError = ERROR_NORMAL;
            } else {
                throw constituentCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setConstituentCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return constituentCodeError;
    } // method setConstituentCode

    /**
     * Set the 'constituentCode' class variable
     * @param  constituentCode (Integer)
     */
    public int setConstituentCode(Integer constituentCode) {
        try {
            setConstituentCode(constituentCode.intValue());
        } catch (Exception e) {
            setConstituentCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return constituentCodeError;
    } // method setConstituentCode

    /**
     * Set the 'constituentCode' class variable
     * @param  constituentCode (Float)
     */
    public int setConstituentCode(Float constituentCode) {
        try {
            if (constituentCode.floatValue() == FLOATNULL) {
                setConstituentCode(INTNULL);
            } else {
                setConstituentCode(constituentCode.intValue());
            } // if (constituentCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setConstituentCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return constituentCodeError;
    } // method setConstituentCode

    /**
     * Set the 'constituentCode' class variable
     * @param  constituentCode (String)
     */
    public int setConstituentCode(String constituentCode) {
        try {
            setConstituentCode(new Integer(constituentCode).intValue());
        } catch (Exception e) {
            setConstituentCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return constituentCodeError;
    } // method setConstituentCode

    /**
     * Called when an exception has occured
     * @param  constituentCode (String)
     */
    private void setConstituentCodeError (int constituentCode, Exception e, int error) {
        this.constituentCode = constituentCode;
        constituentCodeErrorMessage = e.toString();
        constituentCodeError = error;
    } // method setConstituentCodeError


    /**
     * Set the 'fractionCode' class variable
     * @param  fractionCode (int)
     */
    public int setFractionCode(int fractionCode) {
        try {
            if ( ((fractionCode == INTNULL) ||
                  (fractionCode == INTNULL2)) ||
                !((fractionCode < FRACTION_CODE_MN) ||
                  (fractionCode > FRACTION_CODE_MX)) ) {
                this.fractionCode = fractionCode;
                fractionCodeError = ERROR_NORMAL;
            } else {
                throw fractionCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFractionCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return fractionCodeError;
    } // method setFractionCode

    /**
     * Set the 'fractionCode' class variable
     * @param  fractionCode (Integer)
     */
    public int setFractionCode(Integer fractionCode) {
        try {
            setFractionCode(fractionCode.intValue());
        } catch (Exception e) {
            setFractionCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return fractionCodeError;
    } // method setFractionCode

    /**
     * Set the 'fractionCode' class variable
     * @param  fractionCode (Float)
     */
    public int setFractionCode(Float fractionCode) {
        try {
            if (fractionCode.floatValue() == FLOATNULL) {
                setFractionCode(INTNULL);
            } else {
                setFractionCode(fractionCode.intValue());
            } // if (fractionCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setFractionCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return fractionCodeError;
    } // method setFractionCode

    /**
     * Set the 'fractionCode' class variable
     * @param  fractionCode (String)
     */
    public int setFractionCode(String fractionCode) {
        try {
            setFractionCode(new Integer(fractionCode).intValue());
        } catch (Exception e) {
            setFractionCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return fractionCodeError;
    } // method setFractionCode

    /**
     * Called when an exception has occured
     * @param  fractionCode (String)
     */
    private void setFractionCodeError (int fractionCode, Exception e, int error) {
        this.fractionCode = fractionCode;
        fractionCodeErrorMessage = e.toString();
        fractionCodeError = error;
    } // method setFractionCodeError


    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (int)
     */
    public int setDeviceCode(int deviceCode) {
        try {
            if ( ((deviceCode == INTNULL) ||
                  (deviceCode == INTNULL2)) ||
                !((deviceCode < DEVICE_CODE_MN) ||
                  (deviceCode > DEVICE_CODE_MX)) ) {
                this.deviceCode = deviceCode;
                deviceCodeError = ERROR_NORMAL;
            } else {
                throw deviceCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (Integer)
     */
    public int setDeviceCode(Integer deviceCode) {
        try {
            setDeviceCode(deviceCode.intValue());
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (Float)
     */
    public int setDeviceCode(Float deviceCode) {
        try {
            if (deviceCode.floatValue() == FLOATNULL) {
                setDeviceCode(INTNULL);
            } else {
                setDeviceCode(deviceCode.intValue());
            } // if (deviceCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (String)
     */
    public int setDeviceCode(String deviceCode) {
        try {
            setDeviceCode(new Integer(deviceCode).intValue());
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Called when an exception has occured
     * @param  deviceCode (String)
     */
    private void setDeviceCodeError (int deviceCode, Exception e, int error) {
        this.deviceCode = deviceCode;
        deviceCodeErrorMessage = e.toString();
        deviceCodeError = error;
    } // method setDeviceCodeError


    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (int)
     */
    public int setMethodCode(int methodCode) {
        try {
            if ( ((methodCode == INTNULL) ||
                  (methodCode == INTNULL2)) ||
                !((methodCode < METHOD_CODE_MN) ||
                  (methodCode > METHOD_CODE_MX)) ) {
                this.methodCode = methodCode;
                methodCodeError = ERROR_NORMAL;
            } else {
                throw methodCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (Integer)
     */
    public int setMethodCode(Integer methodCode) {
        try {
            setMethodCode(methodCode.intValue());
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (Float)
     */
    public int setMethodCode(Float methodCode) {
        try {
            if (methodCode.floatValue() == FLOATNULL) {
                setMethodCode(INTNULL);
            } else {
                setMethodCode(methodCode.intValue());
            } // if (methodCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (String)
     */
    public int setMethodCode(String methodCode) {
        try {
            setMethodCode(new Integer(methodCode).intValue());
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Called when an exception has occured
     * @param  methodCode (String)
     */
    private void setMethodCodeError (int methodCode, Exception e, int error) {
        this.methodCode = methodCode;
        methodCodeErrorMessage = e.toString();
        methodCodeError = error;
    } // method setMethodCodeError


    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (int)
     */
    public int setStandardCode(int standardCode) {
        try {
            if ( ((standardCode == INTNULL) ||
                  (standardCode == INTNULL2)) ||
                !((standardCode < STANDARD_CODE_MN) ||
                  (standardCode > STANDARD_CODE_MX)) ) {
                this.standardCode = standardCode;
                standardCodeError = ERROR_NORMAL;
            } else {
                throw standardCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (Integer)
     */
    public int setStandardCode(Integer standardCode) {
        try {
            setStandardCode(standardCode.intValue());
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (Float)
     */
    public int setStandardCode(Float standardCode) {
        try {
            if (standardCode.floatValue() == FLOATNULL) {
                setStandardCode(INTNULL);
            } else {
                setStandardCode(standardCode.intValue());
            } // if (standardCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (String)
     */
    public int setStandardCode(String standardCode) {
        try {
            setStandardCode(new Integer(standardCode).intValue());
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Called when an exception has occured
     * @param  standardCode (String)
     */
    private void setStandardCodeError (int standardCode, Exception e, int error) {
        this.standardCode = standardCode;
        standardCodeErrorMessage = e.toString();
        standardCodeError = error;
    } // method setStandardCodeError


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
     * Return the 'disciplineCode' class variable
     * @return disciplineCode (int)
     */
    public int getDisciplineCode() {
        return disciplineCode;
    } // method getDisciplineCode

    /**
     * Return the 'disciplineCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDisciplineCode methods
     * @return disciplineCode (String)
     */
    public String getDisciplineCode(String s) {
        return ((disciplineCode != INTNULL) ? new Integer(disciplineCode).toString() : "");
    } // method getDisciplineCode


    /**
     * Return the 'constituentCode' class variable
     * @return constituentCode (int)
     */
    public int getConstituentCode() {
        return constituentCode;
    } // method getConstituentCode

    /**
     * Return the 'constituentCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getConstituentCode methods
     * @return constituentCode (String)
     */
    public String getConstituentCode(String s) {
        return ((constituentCode != INTNULL) ? new Integer(constituentCode).toString() : "");
    } // method getConstituentCode


    /**
     * Return the 'fractionCode' class variable
     * @return fractionCode (int)
     */
    public int getFractionCode() {
        return fractionCode;
    } // method getFractionCode

    /**
     * Return the 'fractionCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFractionCode methods
     * @return fractionCode (String)
     */
    public String getFractionCode(String s) {
        return ((fractionCode != INTNULL) ? new Integer(fractionCode).toString() : "");
    } // method getFractionCode


    /**
     * Return the 'deviceCode' class variable
     * @return deviceCode (int)
     */
    public int getDeviceCode() {
        return deviceCode;
    } // method getDeviceCode

    /**
     * Return the 'deviceCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDeviceCode methods
     * @return deviceCode (String)
     */
    public String getDeviceCode(String s) {
        return ((deviceCode != INTNULL) ? new Integer(deviceCode).toString() : "");
    } // method getDeviceCode


    /**
     * Return the 'methodCode' class variable
     * @return methodCode (int)
     */
    public int getMethodCode() {
        return methodCode;
    } // method getMethodCode

    /**
     * Return the 'methodCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMethodCode methods
     * @return methodCode (String)
     */
    public String getMethodCode(String s) {
        return ((methodCode != INTNULL) ? new Integer(methodCode).toString() : "");
    } // method getMethodCode


    /**
     * Return the 'standardCode' class variable
     * @return standardCode (int)
     */
    public int getStandardCode() {
        return standardCode;
    } // method getStandardCode

    /**
     * Return the 'standardCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStandardCode methods
     * @return standardCode (String)
     */
    public String getStandardCode(String s) {
        return ((standardCode != INTNULL) ? new Integer(standardCode).toString() : "");
    } // method getStandardCode


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
            (disciplineCode == INTNULL) &&
            (constituentCode == INTNULL) &&
            (fractionCode == INTNULL) &&
            (deviceCode == INTNULL) &&
            (methodCode == INTNULL) &&
            (standardCode == INTNULL)) {
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
            disciplineCodeError +
            constituentCodeError +
            fractionCodeError +
            deviceCodeError +
            methodCodeError +
            standardCodeError;
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
     * Gets the errorcode for the disciplineCode instance variable
     * @return errorcode (int)
     */
    public int getDisciplineCodeError() {
        return disciplineCodeError;
    } // method getDisciplineCodeError

    /**
     * Gets the errorMessage for the disciplineCode instance variable
     * @return errorMessage (String)
     */
    public String getDisciplineCodeErrorMessage() {
        return disciplineCodeErrorMessage;
    } // method getDisciplineCodeErrorMessage


    /**
     * Gets the errorcode for the constituentCode instance variable
     * @return errorcode (int)
     */
    public int getConstituentCodeError() {
        return constituentCodeError;
    } // method getConstituentCodeError

    /**
     * Gets the errorMessage for the constituentCode instance variable
     * @return errorMessage (String)
     */
    public String getConstituentCodeErrorMessage() {
        return constituentCodeErrorMessage;
    } // method getConstituentCodeErrorMessage


    /**
     * Gets the errorcode for the fractionCode instance variable
     * @return errorcode (int)
     */
    public int getFractionCodeError() {
        return fractionCodeError;
    } // method getFractionCodeError

    /**
     * Gets the errorMessage for the fractionCode instance variable
     * @return errorMessage (String)
     */
    public String getFractionCodeErrorMessage() {
        return fractionCodeErrorMessage;
    } // method getFractionCodeErrorMessage


    /**
     * Gets the errorcode for the deviceCode instance variable
     * @return errorcode (int)
     */
    public int getDeviceCodeError() {
        return deviceCodeError;
    } // method getDeviceCodeError

    /**
     * Gets the errorMessage for the deviceCode instance variable
     * @return errorMessage (String)
     */
    public String getDeviceCodeErrorMessage() {
        return deviceCodeErrorMessage;
    } // method getDeviceCodeErrorMessage


    /**
     * Gets the errorcode for the methodCode instance variable
     * @return errorcode (int)
     */
    public int getMethodCodeError() {
        return methodCodeError;
    } // method getMethodCodeError

    /**
     * Gets the errorMessage for the methodCode instance variable
     * @return errorMessage (String)
     */
    public String getMethodCodeErrorMessage() {
        return methodCodeErrorMessage;
    } // method getMethodCodeErrorMessage


    /**
     * Gets the errorcode for the standardCode instance variable
     * @return errorcode (int)
     */
    public int getStandardCodeError() {
        return standardCodeError;
    } // method getStandardCodeError

    /**
     * Gets the errorMessage for the standardCode instance variable
     * @return errorMessage (String)
     */
    public String getStandardCodeErrorMessage() {
        return standardCodeErrorMessage;
    } // method getStandardCodeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnSurveyConstituent. e.g.<pre>
     * MrnSurveyConstituent surveyConstituent = new MrnSurveyConstituent(<val1>);
     * MrnSurveyConstituent surveyConstituentArray[] = surveyConstituent.get();</pre>
     * will get the MrnSurveyConstituent record where surveyId = <val1>.
     * @return Array of MrnSurveyConstituent (MrnSurveyConstituent[])
     */
    public MrnSurveyConstituent[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnSurveyConstituent surveyConstituentArray[] =
     *     new MrnSurveyConstituent().get(MrnSurveyConstituent.SURVEY_ID+"=<val1>");</pre>
     * will get the MrnSurveyConstituent record where surveyId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnSurveyConstituent (MrnSurveyConstituent[])
     */
    public MrnSurveyConstituent[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnSurveyConstituent surveyConstituentArray[] =
     *     new MrnSurveyConstituent().get("1=1",surveyConstituent.SURVEY_ID);</pre>
     * will get the all the MrnSurveyConstituent records, and order them by surveyId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSurveyConstituent (MrnSurveyConstituent[])
     */
    public MrnSurveyConstituent[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnSurveyConstituent.SURVEY_ID,MrnSurveyConstituent.DISCIPLINE_CODE;
     * String where = MrnSurveyConstituent.SURVEY_ID + "=<val1";
     * String order = MrnSurveyConstituent.SURVEY_ID;
     * MrnSurveyConstituent surveyConstituentArray[] =
     *     new MrnSurveyConstituent().get(columns, where, order);</pre>
     * will get the surveyId and disciplineCode colums of all MrnSurveyConstituent records,
     * where surveyId = <val1>, and order them by surveyId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSurveyConstituent (MrnSurveyConstituent[])
     */
    public MrnSurveyConstituent[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnSurveyConstituent.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnSurveyConstituent[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int surveyIdCol        = db.getColNumber(SURVEY_ID);
        int disciplineCodeCol  = db.getColNumber(DISCIPLINE_CODE);
        int constituentCodeCol = db.getColNumber(CONSTITUENT_CODE);
        int fractionCodeCol    = db.getColNumber(FRACTION_CODE);
        int deviceCodeCol      = db.getColNumber(DEVICE_CODE);
        int methodCodeCol      = db.getColNumber(METHOD_CODE);
        int standardCodeCol    = db.getColNumber(STANDARD_CODE);
        MrnSurveyConstituent[] cArray = new MrnSurveyConstituent[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnSurveyConstituent();
            if (surveyIdCol != -1)
                cArray[i].setSurveyId       ((String) row.elementAt(surveyIdCol));
            if (disciplineCodeCol != -1)
                cArray[i].setDisciplineCode ((String) row.elementAt(disciplineCodeCol));
            if (constituentCodeCol != -1)
                cArray[i].setConstituentCode((String) row.elementAt(constituentCodeCol));
            if (fractionCodeCol != -1)
                cArray[i].setFractionCode   ((String) row.elementAt(fractionCodeCol));
            if (deviceCodeCol != -1)
                cArray[i].setDeviceCode     ((String) row.elementAt(deviceCodeCol));
            if (methodCodeCol != -1)
                cArray[i].setMethodCode     ((String) row.elementAt(methodCodeCol));
            if (standardCodeCol != -1)
                cArray[i].setStandardCode   ((String) row.elementAt(standardCodeCol));
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
     *     new MrnSurveyConstituent(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>).put();</pre>
     * will insert a record with:
     *     surveyId        = <val1>,
     *     disciplineCode  = <val2>,
     *     constituentCode = <val3>,
     *     fractionCode    = <val4>,
     *     deviceCode      = <val5>,
     *     methodCode      = <val6>,
     *     standardCode    = <val7>.
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
     * Boolean success = new MrnSurveyConstituent(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where disciplineCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSurveyConstituent surveyConstituent = new MrnSurveyConstituent();
     * success = surveyConstituent.del(MrnSurveyConstituent.SURVEY_ID+"=<val1>");</pre>
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
     * update are taken from the MrnSurveyConstituent argument, .e.g.<pre>
     * Boolean success
     * MrnSurveyConstituent updMrnSurveyConstituent = new MrnSurveyConstituent();
     * updMrnSurveyConstituent.setDisciplineCode(<val2>);
     * MrnSurveyConstituent whereMrnSurveyConstituent = new MrnSurveyConstituent(<val1>);
     * success = whereMrnSurveyConstituent.upd(updMrnSurveyConstituent);</pre>
     * will update DisciplineCode to <val2> for all records where
     * surveyId = <val1>.
     * @param  surveyConstituent  A MrnSurveyConstituent variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSurveyConstituent surveyConstituent) {
        return db.update (TABLE, createColVals(surveyConstituent), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSurveyConstituent updMrnSurveyConstituent = new MrnSurveyConstituent();
     * updMrnSurveyConstituent.setDisciplineCode(<val2>);
     * MrnSurveyConstituent whereMrnSurveyConstituent = new MrnSurveyConstituent();
     * success = whereMrnSurveyConstituent.upd(
     *     updMrnSurveyConstituent, MrnSurveyConstituent.SURVEY_ID+"=<val1>");</pre>
     * will update DisciplineCode to <val2> for all records where
     * surveyId = <val1>.
     * @param  surveyConstituent  A MrnSurveyConstituent variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSurveyConstituent surveyConstituent, String where) {
        return db.update (TABLE, createColVals(surveyConstituent), where);
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
        if (getDisciplineCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DISCIPLINE_CODE + "=" + getDisciplineCode("");
        } // if getDisciplineCode
        if (getConstituentCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CONSTITUENT_CODE + "=" + getConstituentCode("");
        } // if getConstituentCode
        if (getFractionCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FRACTION_CODE + "=" + getFractionCode("");
        } // if getFractionCode
        if (getDeviceCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEVICE_CODE + "=" + getDeviceCode("");
        } // if getDeviceCode
        if (getMethodCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + METHOD_CODE + "=" + getMethodCode("");
        } // if getMethodCode
        if (getStandardCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STANDARD_CODE + "=" + getStandardCode("");
        } // if getStandardCode
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnSurveyConstituent aVar) {
        String colVals = "";
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getDisciplineCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DISCIPLINE_CODE +"=";
            colVals += (aVar.getDisciplineCode() == INTNULL2 ?
                "null" : aVar.getDisciplineCode(""));
        } // if aVar.getDisciplineCode
        if (aVar.getConstituentCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CONSTITUENT_CODE +"=";
            colVals += (aVar.getConstituentCode() == INTNULL2 ?
                "null" : aVar.getConstituentCode(""));
        } // if aVar.getConstituentCode
        if (aVar.getFractionCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FRACTION_CODE +"=";
            colVals += (aVar.getFractionCode() == INTNULL2 ?
                "null" : aVar.getFractionCode(""));
        } // if aVar.getFractionCode
        if (aVar.getDeviceCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEVICE_CODE +"=";
            colVals += (aVar.getDeviceCode() == INTNULL2 ?
                "null" : aVar.getDeviceCode(""));
        } // if aVar.getDeviceCode
        if (aVar.getMethodCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += METHOD_CODE +"=";
            colVals += (aVar.getMethodCode() == INTNULL2 ?
                "null" : aVar.getMethodCode(""));
        } // if aVar.getMethodCode
        if (aVar.getStandardCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STANDARD_CODE +"=";
            colVals += (aVar.getStandardCode() == INTNULL2 ?
                "null" : aVar.getStandardCode(""));
        } // if aVar.getStandardCode
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SURVEY_ID;
        if (getDisciplineCode() != INTNULL) {
            columns = columns + "," + DISCIPLINE_CODE;
        } // if getDisciplineCode
        if (getConstituentCode() != INTNULL) {
            columns = columns + "," + CONSTITUENT_CODE;
        } // if getConstituentCode
        if (getFractionCode() != INTNULL) {
            columns = columns + "," + FRACTION_CODE;
        } // if getFractionCode
        if (getDeviceCode() != INTNULL) {
            columns = columns + "," + DEVICE_CODE;
        } // if getDeviceCode
        if (getMethodCode() != INTNULL) {
            columns = columns + "," + METHOD_CODE;
        } // if getMethodCode
        if (getStandardCode() != INTNULL) {
            columns = columns + "," + STANDARD_CODE;
        } // if getStandardCode
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getSurveyId() + "'";
        if (getDisciplineCode() != INTNULL) {
            values  = values  + "," + getDisciplineCode("");
        } // if getDisciplineCode
        if (getConstituentCode() != INTNULL) {
            values  = values  + "," + getConstituentCode("");
        } // if getConstituentCode
        if (getFractionCode() != INTNULL) {
            values  = values  + "," + getFractionCode("");
        } // if getFractionCode
        if (getDeviceCode() != INTNULL) {
            values  = values  + "," + getDeviceCode("");
        } // if getDeviceCode
        if (getMethodCode() != INTNULL) {
            values  = values  + "," + getMethodCode("");
        } // if getMethodCode
        if (getStandardCode() != INTNULL) {
            values  = values  + "," + getStandardCode("");
        } // if getStandardCode
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getSurveyId("")        + "|" +
            getDisciplineCode("")  + "|" +
            getConstituentCode("") + "|" +
            getFractionCode("")    + "|" +
            getDeviceCode("")      + "|" +
            getMethodCode("")      + "|" +
            getStandardCode("")    + "|";
    } // method toString

} // class MrnSurveyConstituent
