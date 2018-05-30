package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SURVEY table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnSurvey extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SURVEY";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The institute field name */
    public static final String INSTITUTE = "INSTITUTE";
    /** The institCode field name */
    public static final String INSTIT_CODE = "INSTIT_CODE";
    /** The prjnam field name */
    public static final String PRJNAM = "PRJNAM";
    /** The expnam field name */
    public static final String EXPNAM = "EXPNAM";
    /** The planam field name */
    public static final String PLANAM = "PLANAM";
    /** The planamCode field name */
    public static final String PLANAM_CODE = "PLANAM_CODE";
    /** The notes1 field name */
    public static final String NOTES_1 = "NOTES_1";
    /** The notes2 field name */
    public static final String NOTES_2 = "NOTES_2";
    /** The notes3 field name */
    public static final String NOTES_3 = "NOTES_3";
    /** The notes4 field name */
    public static final String NOTES_4 = "NOTES_4";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    surveyId;
    private String    institute;
    private int       institCode;
    private String    prjnam;
    private String    expnam;
    private String    planam;
    private int       planamCode;
    private String    notes1;
    private String    notes2;
    private String    notes3;
    private String    notes4;

    /** The error variables  */
    private int surveyIdError   = ERROR_NORMAL;
    private int instituteError  = ERROR_NORMAL;
    private int institCodeError = ERROR_NORMAL;
    private int prjnamError     = ERROR_NORMAL;
    private int expnamError     = ERROR_NORMAL;
    private int planamError     = ERROR_NORMAL;
    private int planamCodeError = ERROR_NORMAL;
    private int notes1Error     = ERROR_NORMAL;
    private int notes2Error     = ERROR_NORMAL;
    private int notes3Error     = ERROR_NORMAL;
    private int notes4Error     = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String surveyIdErrorMessage   = "";
    private String instituteErrorMessage  = "";
    private String institCodeErrorMessage = "";
    private String prjnamErrorMessage     = "";
    private String expnamErrorMessage     = "";
    private String planamErrorMessage     = "";
    private String planamCodeErrorMessage = "";
    private String notes1ErrorMessage     = "";
    private String notes2ErrorMessage     = "";
    private String notes3ErrorMessage     = "";
    private String notes4ErrorMessage     = "";

    /** The min-max constants for all numerics */
    public static final int       INSTIT_CODE_MN = INTMIN;
    public static final int       INSTIT_CODE_MX = INTMAX;
    public static final int       PLANAM_CODE_MN = INTMIN;
    public static final int       PLANAM_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception institCodeOutOfBoundsException =
        new Exception ("'institCode' out of bounds: " +
            INSTIT_CODE_MN + " - " + INSTIT_CODE_MX);
    Exception planamCodeOutOfBoundsException =
        new Exception ("'planamCode' out of bounds: " +
            PLANAM_CODE_MN + " - " + PLANAM_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnSurvey() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnSurvey constructor 1"); // debug
    } // MrnSurvey constructor

    /**
     * Instantiate a MrnSurvey object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     */
    public MrnSurvey(
            String surveyId) {
        this();
        setSurveyId   (surveyId  );
        if (dbg) System.out.println ("<br>in MrnSurvey constructor 2"); // debug
    } // MrnSurvey constructor

    /**
     * Instantiate a MrnSurvey object and initialize the instance variables.
     * @param surveyId    The surveyId   (String)
     * @param institute   The institute  (String)
     * @param institCode  The institCode (int)
     * @param prjnam      The prjnam     (String)
     * @param expnam      The expnam     (String)
     * @param planam      The planam     (String)
     * @param planamCode  The planamCode (int)
     * @param notes1      The notes1     (String)
     * @param notes2      The notes2     (String)
     * @param notes3      The notes3     (String)
     * @param notes4      The notes4     (String)
     */
    public MrnSurvey(
            String surveyId,
            String institute,
            int    institCode,
            String prjnam,
            String expnam,
            String planam,
            int    planamCode,
            String notes1,
            String notes2,
            String notes3,
            String notes4) {
        this();
        setSurveyId   (surveyId  );
        setInstitute  (institute );
        setInstitCode (institCode);
        setPrjnam     (prjnam    );
        setExpnam     (expnam    );
        setPlanam     (planam    );
        setPlanamCode (planamCode);
        setNotes1     (notes1    );
        setNotes2     (notes2    );
        setNotes3     (notes3    );
        setNotes4     (notes4    );
        if (dbg) System.out.println ("<br>in MrnSurvey constructor 3"); // debug
    } // MrnSurvey constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSurveyId   (CHARNULL);
        setInstitute  (CHARNULL);
        setInstitCode (INTNULL );
        setPrjnam     (CHARNULL);
        setExpnam     (CHARNULL);
        setPlanam     (CHARNULL);
        setPlanamCode (INTNULL );
        setNotes1     (CHARNULL);
        setNotes2     (CHARNULL);
        setNotes3     (CHARNULL);
        setNotes4     (CHARNULL);
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
     * Set the 'institute' class variable
     * @param  institute (String)
     */
    public int setInstitute(String institute) {
        try {
            this.institute = institute;
            if (this.institute != CHARNULL) {
                this.institute = stripCRLF(this.institute.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>institute = " + this.institute);
        } catch (Exception e) {
            setInstituteError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return instituteError;
    } // method setInstitute

    /**
     * Called when an exception has occured
     * @param  institute (String)
     */
    private void setInstituteError (String institute, Exception e, int error) {
        this.institute = institute;
        instituteErrorMessage = e.toString();
        instituteError = error;
    } // method setInstituteError


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
     * Set the 'prjnam' class variable
     * @param  prjnam (String)
     */
    public int setPrjnam(String prjnam) {
        try {
            this.prjnam = prjnam;
            if (this.prjnam != CHARNULL) {
                this.prjnam = stripCRLF(this.prjnam.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>prjnam = " + this.prjnam);
        } catch (Exception e) {
            setPrjnamError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return prjnamError;
    } // method setPrjnam

    /**
     * Called when an exception has occured
     * @param  prjnam (String)
     */
    private void setPrjnamError (String prjnam, Exception e, int error) {
        this.prjnam = prjnam;
        prjnamErrorMessage = e.toString();
        prjnamError = error;
    } // method setPrjnamError


    /**
     * Set the 'expnam' class variable
     * @param  expnam (String)
     */
    public int setExpnam(String expnam) {
        try {
            this.expnam = expnam;
            if (this.expnam != CHARNULL) {
                this.expnam = stripCRLF(this.expnam.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>expnam = " + this.expnam);
        } catch (Exception e) {
            setExpnamError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return expnamError;
    } // method setExpnam

    /**
     * Called when an exception has occured
     * @param  expnam (String)
     */
    private void setExpnamError (String expnam, Exception e, int error) {
        this.expnam = expnam;
        expnamErrorMessage = e.toString();
        expnamError = error;
    } // method setExpnamError


    /**
     * Set the 'planam' class variable
     * @param  planam (String)
     */
    public int setPlanam(String planam) {
        try {
            this.planam = planam;
            if (this.planam != CHARNULL) {
                this.planam = stripCRLF(this.planam.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>planam = " + this.planam);
        } catch (Exception e) {
            setPlanamError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return planamError;
    } // method setPlanam

    /**
     * Called when an exception has occured
     * @param  planam (String)
     */
    private void setPlanamError (String planam, Exception e, int error) {
        this.planam = planam;
        planamErrorMessage = e.toString();
        planamError = error;
    } // method setPlanamError


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
     * Set the 'notes1' class variable
     * @param  notes1 (String)
     */
    public int setNotes1(String notes1) {
        try {
            this.notes1 = notes1;
            if (this.notes1 != CHARNULL) {
                this.notes1 = stripCRLF(this.notes1.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>notes1 = " + this.notes1);
        } catch (Exception e) {
            setNotes1Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return notes1Error;
    } // method setNotes1

    /**
     * Called when an exception has occured
     * @param  notes1 (String)
     */
    private void setNotes1Error (String notes1, Exception e, int error) {
        this.notes1 = notes1;
        notes1ErrorMessage = e.toString();
        notes1Error = error;
    } // method setNotes1Error


    /**
     * Set the 'notes2' class variable
     * @param  notes2 (String)
     */
    public int setNotes2(String notes2) {
        try {
            this.notes2 = notes2;
            if (this.notes2 != CHARNULL) {
                this.notes2 = stripCRLF(this.notes2.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>notes2 = " + this.notes2);
        } catch (Exception e) {
            setNotes2Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return notes2Error;
    } // method setNotes2

    /**
     * Called when an exception has occured
     * @param  notes2 (String)
     */
    private void setNotes2Error (String notes2, Exception e, int error) {
        this.notes2 = notes2;
        notes2ErrorMessage = e.toString();
        notes2Error = error;
    } // method setNotes2Error


    /**
     * Set the 'notes3' class variable
     * @param  notes3 (String)
     */
    public int setNotes3(String notes3) {
        try {
            this.notes3 = notes3;
            if (this.notes3 != CHARNULL) {
                this.notes3 = stripCRLF(this.notes3.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>notes3 = " + this.notes3);
        } catch (Exception e) {
            setNotes3Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return notes3Error;
    } // method setNotes3

    /**
     * Called when an exception has occured
     * @param  notes3 (String)
     */
    private void setNotes3Error (String notes3, Exception e, int error) {
        this.notes3 = notes3;
        notes3ErrorMessage = e.toString();
        notes3Error = error;
    } // method setNotes3Error


    /**
     * Set the 'notes4' class variable
     * @param  notes4 (String)
     */
    public int setNotes4(String notes4) {
        try {
            this.notes4 = notes4;
            if (this.notes4 != CHARNULL) {
                this.notes4 = stripCRLF(this.notes4.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>notes4 = " + this.notes4);
        } catch (Exception e) {
            setNotes4Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return notes4Error;
    } // method setNotes4

    /**
     * Called when an exception has occured
     * @param  notes4 (String)
     */
    private void setNotes4Error (String notes4, Exception e, int error) {
        this.notes4 = notes4;
        notes4ErrorMessage = e.toString();
        notes4Error = error;
    } // method setNotes4Error


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
     * Return the 'institute' class variable
     * @return institute (String)
     */
    public String getInstitute() {
        return institute;
    } // method getInstitute

    /**
     * Return the 'institute' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getInstitute methods
     * @return institute (String)
     */
    public String getInstitute(String s) {
        return (institute != CHARNULL ? institute.replace('"','\'') : "");
    } // method getInstitute


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
     * Return the 'prjnam' class variable
     * @return prjnam (String)
     */
    public String getPrjnam() {
        return prjnam;
    } // method getPrjnam

    /**
     * Return the 'prjnam' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPrjnam methods
     * @return prjnam (String)
     */
    public String getPrjnam(String s) {
        return (prjnam != CHARNULL ? prjnam.replace('"','\'') : "");
    } // method getPrjnam


    /**
     * Return the 'expnam' class variable
     * @return expnam (String)
     */
    public String getExpnam() {
        return expnam;
    } // method getExpnam

    /**
     * Return the 'expnam' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getExpnam methods
     * @return expnam (String)
     */
    public String getExpnam(String s) {
        return (expnam != CHARNULL ? expnam.replace('"','\'') : "");
    } // method getExpnam


    /**
     * Return the 'planam' class variable
     * @return planam (String)
     */
    public String getPlanam() {
        return planam;
    } // method getPlanam

    /**
     * Return the 'planam' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlanam methods
     * @return planam (String)
     */
    public String getPlanam(String s) {
        return (planam != CHARNULL ? planam.replace('"','\'') : "");
    } // method getPlanam


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
     * Return the 'notes1' class variable
     * @return notes1 (String)
     */
    public String getNotes1() {
        return notes1;
    } // method getNotes1

    /**
     * Return the 'notes1' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNotes1 methods
     * @return notes1 (String)
     */
    public String getNotes1(String s) {
        return (notes1 != CHARNULL ? notes1.replace('"','\'') : "");
    } // method getNotes1


    /**
     * Return the 'notes2' class variable
     * @return notes2 (String)
     */
    public String getNotes2() {
        return notes2;
    } // method getNotes2

    /**
     * Return the 'notes2' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNotes2 methods
     * @return notes2 (String)
     */
    public String getNotes2(String s) {
        return (notes2 != CHARNULL ? notes2.replace('"','\'') : "");
    } // method getNotes2


    /**
     * Return the 'notes3' class variable
     * @return notes3 (String)
     */
    public String getNotes3() {
        return notes3;
    } // method getNotes3

    /**
     * Return the 'notes3' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNotes3 methods
     * @return notes3 (String)
     */
    public String getNotes3(String s) {
        return (notes3 != CHARNULL ? notes3.replace('"','\'') : "");
    } // method getNotes3


    /**
     * Return the 'notes4' class variable
     * @return notes4 (String)
     */
    public String getNotes4() {
        return notes4;
    } // method getNotes4

    /**
     * Return the 'notes4' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNotes4 methods
     * @return notes4 (String)
     */
    public String getNotes4(String s) {
        return (notes4 != CHARNULL ? notes4.replace('"','\'') : "");
    } // method getNotes4


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
            (institute == CHARNULL) &&
            (institCode == INTNULL) &&
            (prjnam == CHARNULL) &&
            (expnam == CHARNULL) &&
            (planam == CHARNULL) &&
            (planamCode == INTNULL) &&
            (notes1 == CHARNULL) &&
            (notes2 == CHARNULL) &&
            (notes3 == CHARNULL) &&
            (notes4 == CHARNULL)) {
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
            instituteError +
            institCodeError +
            prjnamError +
            expnamError +
            planamError +
            planamCodeError +
            notes1Error +
            notes2Error +
            notes3Error +
            notes4Error;
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
     * Gets the errorcode for the institute instance variable
     * @return errorcode (int)
     */
    public int getInstituteError() {
        return instituteError;
    } // method getInstituteError

    /**
     * Gets the errorMessage for the institute instance variable
     * @return errorMessage (String)
     */
    public String getInstituteErrorMessage() {
        return instituteErrorMessage;
    } // method getInstituteErrorMessage


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
     * Gets the errorcode for the prjnam instance variable
     * @return errorcode (int)
     */
    public int getPrjnamError() {
        return prjnamError;
    } // method getPrjnamError

    /**
     * Gets the errorMessage for the prjnam instance variable
     * @return errorMessage (String)
     */
    public String getPrjnamErrorMessage() {
        return prjnamErrorMessage;
    } // method getPrjnamErrorMessage


    /**
     * Gets the errorcode for the expnam instance variable
     * @return errorcode (int)
     */
    public int getExpnamError() {
        return expnamError;
    } // method getExpnamError

    /**
     * Gets the errorMessage for the expnam instance variable
     * @return errorMessage (String)
     */
    public String getExpnamErrorMessage() {
        return expnamErrorMessage;
    } // method getExpnamErrorMessage


    /**
     * Gets the errorcode for the planam instance variable
     * @return errorcode (int)
     */
    public int getPlanamError() {
        return planamError;
    } // method getPlanamError

    /**
     * Gets the errorMessage for the planam instance variable
     * @return errorMessage (String)
     */
    public String getPlanamErrorMessage() {
        return planamErrorMessage;
    } // method getPlanamErrorMessage


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
     * Gets the errorcode for the notes1 instance variable
     * @return errorcode (int)
     */
    public int getNotes1Error() {
        return notes1Error;
    } // method getNotes1Error

    /**
     * Gets the errorMessage for the notes1 instance variable
     * @return errorMessage (String)
     */
    public String getNotes1ErrorMessage() {
        return notes1ErrorMessage;
    } // method getNotes1ErrorMessage


    /**
     * Gets the errorcode for the notes2 instance variable
     * @return errorcode (int)
     */
    public int getNotes2Error() {
        return notes2Error;
    } // method getNotes2Error

    /**
     * Gets the errorMessage for the notes2 instance variable
     * @return errorMessage (String)
     */
    public String getNotes2ErrorMessage() {
        return notes2ErrorMessage;
    } // method getNotes2ErrorMessage


    /**
     * Gets the errorcode for the notes3 instance variable
     * @return errorcode (int)
     */
    public int getNotes3Error() {
        return notes3Error;
    } // method getNotes3Error

    /**
     * Gets the errorMessage for the notes3 instance variable
     * @return errorMessage (String)
     */
    public String getNotes3ErrorMessage() {
        return notes3ErrorMessage;
    } // method getNotes3ErrorMessage


    /**
     * Gets the errorcode for the notes4 instance variable
     * @return errorcode (int)
     */
    public int getNotes4Error() {
        return notes4Error;
    } // method getNotes4Error

    /**
     * Gets the errorMessage for the notes4 instance variable
     * @return errorMessage (String)
     */
    public String getNotes4ErrorMessage() {
        return notes4ErrorMessage;
    } // method getNotes4ErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnSurvey. e.g.<pre>
     * MrnSurvey survey = new MrnSurvey(<val1>);
     * MrnSurvey surveyArray[] = survey.get();</pre>
     * will get the MrnSurvey record where surveyId = <val1>.
     * @return Array of MrnSurvey (MrnSurvey[])
     */
    public MrnSurvey[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnSurvey surveyArray[] =
     *     new MrnSurvey().get(MrnSurvey.SURVEY_ID+"=<val1>");</pre>
     * will get the MrnSurvey record where surveyId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnSurvey (MrnSurvey[])
     */
    public MrnSurvey[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnSurvey surveyArray[] =
     *     new MrnSurvey().get("1=1",survey.SURVEY_ID);</pre>
     * will get the all the MrnSurvey records, and order them by surveyId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSurvey (MrnSurvey[])
     */
    public MrnSurvey[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnSurvey.SURVEY_ID,MrnSurvey.INSTITUTE;
     * String where = MrnSurvey.SURVEY_ID + "=<val1";
     * String order = MrnSurvey.SURVEY_ID;
     * MrnSurvey surveyArray[] =
     *     new MrnSurvey().get(columns, where, order);</pre>
     * will get the surveyId and institute colums of all MrnSurvey records,
     * where surveyId = <val1>, and order them by surveyId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSurvey (MrnSurvey[])
     */
    public MrnSurvey[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnSurvey.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnSurvey[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int surveyIdCol   = db.getColNumber(SURVEY_ID);
        int instituteCol  = db.getColNumber(INSTITUTE);
        int institCodeCol = db.getColNumber(INSTIT_CODE);
        int prjnamCol     = db.getColNumber(PRJNAM);
        int expnamCol     = db.getColNumber(EXPNAM);
        int planamCol     = db.getColNumber(PLANAM);
        int planamCodeCol = db.getColNumber(PLANAM_CODE);
        int notes1Col     = db.getColNumber(NOTES_1);
        int notes2Col     = db.getColNumber(NOTES_2);
        int notes3Col     = db.getColNumber(NOTES_3);
        int notes4Col     = db.getColNumber(NOTES_4);
        MrnSurvey[] cArray = new MrnSurvey[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnSurvey();
            if (surveyIdCol != -1)
                cArray[i].setSurveyId  ((String) row.elementAt(surveyIdCol));
            if (instituteCol != -1)
                cArray[i].setInstitute ((String) row.elementAt(instituteCol));
            if (institCodeCol != -1)
                cArray[i].setInstitCode((String) row.elementAt(institCodeCol));
            if (prjnamCol != -1)
                cArray[i].setPrjnam    ((String) row.elementAt(prjnamCol));
            if (expnamCol != -1)
                cArray[i].setExpnam    ((String) row.elementAt(expnamCol));
            if (planamCol != -1)
                cArray[i].setPlanam    ((String) row.elementAt(planamCol));
            if (planamCodeCol != -1)
                cArray[i].setPlanamCode((String) row.elementAt(planamCodeCol));
            if (notes1Col != -1)
                cArray[i].setNotes1    ((String) row.elementAt(notes1Col));
            if (notes2Col != -1)
                cArray[i].setNotes2    ((String) row.elementAt(notes2Col));
            if (notes3Col != -1)
                cArray[i].setNotes3    ((String) row.elementAt(notes3Col));
            if (notes4Col != -1)
                cArray[i].setNotes4    ((String) row.elementAt(notes4Col));
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
     *     new MrnSurvey(
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
     *     surveyId   = <val1>,
     *     institute  = <val2>,
     *     institCode = <val3>,
     *     prjnam     = <val4>,
     *     expnam     = <val5>,
     *     planam     = <val6>,
     *     planamCode = <val7>,
     *     notes1     = <val8>,
     *     notes2     = <val9>,
     *     notes3     = <val10>,
     *     notes4     = <val11>.
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
     * Boolean success = new MrnSurvey(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where institute = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSurvey survey = new MrnSurvey();
     * success = survey.del(MrnSurvey.SURVEY_ID+"=<val1>");</pre>
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
     * update are taken from the MrnSurvey argument, .e.g.<pre>
     * Boolean success
     * MrnSurvey updMrnSurvey = new MrnSurvey();
     * updMrnSurvey.setInstitute(<val2>);
     * MrnSurvey whereMrnSurvey = new MrnSurvey(<val1>);
     * success = whereMrnSurvey.upd(updMrnSurvey);</pre>
     * will update Institute to <val2> for all records where
     * surveyId = <val1>.
     * @param  survey  A MrnSurvey variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSurvey survey) {
        return db.update (TABLE, createColVals(survey), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSurvey updMrnSurvey = new MrnSurvey();
     * updMrnSurvey.setInstitute(<val2>);
     * MrnSurvey whereMrnSurvey = new MrnSurvey();
     * success = whereMrnSurvey.upd(
     *     updMrnSurvey, MrnSurvey.SURVEY_ID+"=<val1>");</pre>
     * will update Institute to <val2> for all records where
     * surveyId = <val1>.
     * @param  survey  A MrnSurvey variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSurvey survey, String where) {
        return db.update (TABLE, createColVals(survey), where);
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
        if (getInstitute() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTITUTE + "='" + getInstitute() + "'";
        } // if getInstitute
        if (getInstitCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTIT_CODE + "=" + getInstitCode("");
        } // if getInstitCode
        if (getPrjnam() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PRJNAM + "='" + getPrjnam() + "'";
        } // if getPrjnam
        if (getExpnam() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + EXPNAM + "='" + getExpnam() + "'";
        } // if getExpnam
        if (getPlanam() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLANAM + "='" + getPlanam() + "'";
        } // if getPlanam
        if (getPlanamCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLANAM_CODE + "=" + getPlanamCode("");
        } // if getPlanamCode
        if (getNotes1() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NOTES_1 + "='" + getNotes1() + "'";
        } // if getNotes1
        if (getNotes2() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NOTES_2 + "='" + getNotes2() + "'";
        } // if getNotes2
        if (getNotes3() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NOTES_3 + "='" + getNotes3() + "'";
        } // if getNotes3
        if (getNotes4() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NOTES_4 + "='" + getNotes4() + "'";
        } // if getNotes4
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnSurvey aVar) {
        String colVals = "";
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getInstitute() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTITUTE +"=";
            colVals += (aVar.getInstitute().equals(CHARNULL2) ?
                "null" : "'" + aVar.getInstitute() + "'");
        } // if aVar.getInstitute
        if (aVar.getInstitCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTIT_CODE +"=";
            colVals += (aVar.getInstitCode() == INTNULL2 ?
                "null" : aVar.getInstitCode(""));
        } // if aVar.getInstitCode
        if (aVar.getPrjnam() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PRJNAM +"=";
            colVals += (aVar.getPrjnam().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPrjnam() + "'");
        } // if aVar.getPrjnam
        if (aVar.getExpnam() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += EXPNAM +"=";
            colVals += (aVar.getExpnam().equals(CHARNULL2) ?
                "null" : "'" + aVar.getExpnam() + "'");
        } // if aVar.getExpnam
        if (aVar.getPlanam() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLANAM +"=";
            colVals += (aVar.getPlanam().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPlanam() + "'");
        } // if aVar.getPlanam
        if (aVar.getPlanamCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLANAM_CODE +"=";
            colVals += (aVar.getPlanamCode() == INTNULL2 ?
                "null" : aVar.getPlanamCode(""));
        } // if aVar.getPlanamCode
        if (aVar.getNotes1() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NOTES_1 +"=";
            colVals += (aVar.getNotes1().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNotes1() + "'");
        } // if aVar.getNotes1
        if (aVar.getNotes2() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NOTES_2 +"=";
            colVals += (aVar.getNotes2().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNotes2() + "'");
        } // if aVar.getNotes2
        if (aVar.getNotes3() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NOTES_3 +"=";
            colVals += (aVar.getNotes3().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNotes3() + "'");
        } // if aVar.getNotes3
        if (aVar.getNotes4() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NOTES_4 +"=";
            colVals += (aVar.getNotes4().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNotes4() + "'");
        } // if aVar.getNotes4
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SURVEY_ID;
        if (getInstitute() != CHARNULL) {
            columns = columns + "," + INSTITUTE;
        } // if getInstitute
        if (getInstitCode() != INTNULL) {
            columns = columns + "," + INSTIT_CODE;
        } // if getInstitCode
        if (getPrjnam() != CHARNULL) {
            columns = columns + "," + PRJNAM;
        } // if getPrjnam
        if (getExpnam() != CHARNULL) {
            columns = columns + "," + EXPNAM;
        } // if getExpnam
        if (getPlanam() != CHARNULL) {
            columns = columns + "," + PLANAM;
        } // if getPlanam
        if (getPlanamCode() != INTNULL) {
            columns = columns + "," + PLANAM_CODE;
        } // if getPlanamCode
        if (getNotes1() != CHARNULL) {
            columns = columns + "," + NOTES_1;
        } // if getNotes1
        if (getNotes2() != CHARNULL) {
            columns = columns + "," + NOTES_2;
        } // if getNotes2
        if (getNotes3() != CHARNULL) {
            columns = columns + "," + NOTES_3;
        } // if getNotes3
        if (getNotes4() != CHARNULL) {
            columns = columns + "," + NOTES_4;
        } // if getNotes4
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getSurveyId() + "'";
        if (getInstitute() != CHARNULL) {
            values  = values  + ",'" + getInstitute() + "'";
        } // if getInstitute
        if (getInstitCode() != INTNULL) {
            values  = values  + "," + getInstitCode("");
        } // if getInstitCode
        if (getPrjnam() != CHARNULL) {
            values  = values  + ",'" + getPrjnam() + "'";
        } // if getPrjnam
        if (getExpnam() != CHARNULL) {
            values  = values  + ",'" + getExpnam() + "'";
        } // if getExpnam
        if (getPlanam() != CHARNULL) {
            values  = values  + ",'" + getPlanam() + "'";
        } // if getPlanam
        if (getPlanamCode() != INTNULL) {
            values  = values  + "," + getPlanamCode("");
        } // if getPlanamCode
        if (getNotes1() != CHARNULL) {
            values  = values  + ",'" + getNotes1() + "'";
        } // if getNotes1
        if (getNotes2() != CHARNULL) {
            values  = values  + ",'" + getNotes2() + "'";
        } // if getNotes2
        if (getNotes3() != CHARNULL) {
            values  = values  + ",'" + getNotes3() + "'";
        } // if getNotes3
        if (getNotes4() != CHARNULL) {
            values  = values  + ",'" + getNotes4() + "'";
        } // if getNotes4
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getSurveyId("")   + "|" +
            getInstitute("")  + "|" +
            getInstitCode("") + "|" +
            getPrjnam("")     + "|" +
            getExpnam("")     + "|" +
            getPlanam("")     + "|" +
            getPlanamCode("") + "|" +
            getNotes1("")     + "|" +
            getNotes2("")     + "|" +
            getNotes3("")     + "|" +
            getNotes4("")     + "|";
    } // method toString

} // class MrnSurvey
