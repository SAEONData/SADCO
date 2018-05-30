package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SURVEY_NOTES table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnSurveyNotes extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SURVEY_NOTES";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The line1 field name */
    public static final String LINE1 = "LINE1";
    /** The line2 field name */
    public static final String LINE2 = "LINE2";
    /** The line3 field name */
    public static final String LINE3 = "LINE3";
    /** The line4 field name */
    public static final String LINE4 = "LINE4";
    /** The line5 field name */
    public static final String LINE5 = "LINE5";
    /** The line6 field name */
    public static final String LINE6 = "LINE6";
    /** The line7 field name */
    public static final String LINE7 = "LINE7";
    /** The line8 field name */
    public static final String LINE8 = "LINE8";
    /** The line9 field name */
    public static final String LINE9 = "LINE9";
    /** The line10 field name */
    public static final String LINE10 = "LINE10";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    surveyId;
    private String    line1;
    private String    line2;
    private String    line3;
    private String    line4;
    private String    line5;
    private String    line6;
    private String    line7;
    private String    line8;
    private String    line9;
    private String    line10;

    /** The error variables  */
    private int surveyIdError = ERROR_NORMAL;
    private int line1Error    = ERROR_NORMAL;
    private int line2Error    = ERROR_NORMAL;
    private int line3Error    = ERROR_NORMAL;
    private int line4Error    = ERROR_NORMAL;
    private int line5Error    = ERROR_NORMAL;
    private int line6Error    = ERROR_NORMAL;
    private int line7Error    = ERROR_NORMAL;
    private int line8Error    = ERROR_NORMAL;
    private int line9Error    = ERROR_NORMAL;
    private int line10Error   = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String surveyIdErrorMessage = "";
    private String line1ErrorMessage    = "";
    private String line2ErrorMessage    = "";
    private String line3ErrorMessage    = "";
    private String line4ErrorMessage    = "";
    private String line5ErrorMessage    = "";
    private String line6ErrorMessage    = "";
    private String line7ErrorMessage    = "";
    private String line8ErrorMessage    = "";
    private String line9ErrorMessage    = "";
    private String line10ErrorMessage   = "";

    /** The min-max constants for all numerics */

    /** The exceptions for non-Strings */


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnSurveyNotes() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnSurveyNotes constructor 1"); // debug
    } // MrnSurveyNotes constructor

    /**
     * Instantiate a MrnSurveyNotes object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     */
    public MrnSurveyNotes(
            String surveyId) {
        this();
        setSurveyId (surveyId);
        if (dbg) System.out.println ("<br>in MrnSurveyNotes constructor 2"); // debug
    } // MrnSurveyNotes constructor

    /**
     * Instantiate a MrnSurveyNotes object and initialize the instance variables.
     * @param surveyId  The surveyId (String)
     * @param line1     The line1    (String)
     * @param line2     The line2    (String)
     * @param line3     The line3    (String)
     * @param line4     The line4    (String)
     * @param line5     The line5    (String)
     * @param line6     The line6    (String)
     * @param line7     The line7    (String)
     * @param line8     The line8    (String)
     * @param line9     The line9    (String)
     * @param line10    The line10   (String)
     */
    public MrnSurveyNotes(
            String surveyId,
            String line1,
            String line2,
            String line3,
            String line4,
            String line5,
            String line6,
            String line7,
            String line8,
            String line9,
            String line10) {
        this();
        setSurveyId (surveyId);
        setLine1    (line1   );
        setLine2    (line2   );
        setLine3    (line3   );
        setLine4    (line4   );
        setLine5    (line5   );
        setLine6    (line6   );
        setLine7    (line7   );
        setLine8    (line8   );
        setLine9    (line9   );
        setLine10   (line10  );
        if (dbg) System.out.println ("<br>in MrnSurveyNotes constructor 3"); // debug
    } // MrnSurveyNotes constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSurveyId (CHARNULL);
        setLine1    (CHARNULL);
        setLine2    (CHARNULL);
        setLine3    (CHARNULL);
        setLine4    (CHARNULL);
        setLine5    (CHARNULL);
        setLine6    (CHARNULL);
        setLine7    (CHARNULL);
        setLine8    (CHARNULL);
        setLine9    (CHARNULL);
        setLine10   (CHARNULL);
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
     * Set the 'line1' class variable
     * @param  line1 (String)
     */
    public int setLine1(String line1) {
        try {
            this.line1 = line1;
            if (this.line1 != CHARNULL) {
                this.line1 = stripCRLF(this.line1.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line1 = " + this.line1);
        } catch (Exception e) {
            setLine1Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line1Error;
    } // method setLine1

    /**
     * Called when an exception has occured
     * @param  line1 (String)
     */
    private void setLine1Error (String line1, Exception e, int error) {
        this.line1 = line1;
        line1ErrorMessage = e.toString();
        line1Error = error;
    } // method setLine1Error


    /**
     * Set the 'line2' class variable
     * @param  line2 (String)
     */
    public int setLine2(String line2) {
        try {
            this.line2 = line2;
            if (this.line2 != CHARNULL) {
                this.line2 = stripCRLF(this.line2.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line2 = " + this.line2);
        } catch (Exception e) {
            setLine2Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line2Error;
    } // method setLine2

    /**
     * Called when an exception has occured
     * @param  line2 (String)
     */
    private void setLine2Error (String line2, Exception e, int error) {
        this.line2 = line2;
        line2ErrorMessage = e.toString();
        line2Error = error;
    } // method setLine2Error


    /**
     * Set the 'line3' class variable
     * @param  line3 (String)
     */
    public int setLine3(String line3) {
        try {
            this.line3 = line3;
            if (this.line3 != CHARNULL) {
                this.line3 = stripCRLF(this.line3.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line3 = " + this.line3);
        } catch (Exception e) {
            setLine3Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line3Error;
    } // method setLine3

    /**
     * Called when an exception has occured
     * @param  line3 (String)
     */
    private void setLine3Error (String line3, Exception e, int error) {
        this.line3 = line3;
        line3ErrorMessage = e.toString();
        line3Error = error;
    } // method setLine3Error


    /**
     * Set the 'line4' class variable
     * @param  line4 (String)
     */
    public int setLine4(String line4) {
        try {
            this.line4 = line4;
            if (this.line4 != CHARNULL) {
                this.line4 = stripCRLF(this.line4.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line4 = " + this.line4);
        } catch (Exception e) {
            setLine4Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line4Error;
    } // method setLine4

    /**
     * Called when an exception has occured
     * @param  line4 (String)
     */
    private void setLine4Error (String line4, Exception e, int error) {
        this.line4 = line4;
        line4ErrorMessage = e.toString();
        line4Error = error;
    } // method setLine4Error


    /**
     * Set the 'line5' class variable
     * @param  line5 (String)
     */
    public int setLine5(String line5) {
        try {
            this.line5 = line5;
            if (this.line5 != CHARNULL) {
                this.line5 = stripCRLF(this.line5.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line5 = " + this.line5);
        } catch (Exception e) {
            setLine5Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line5Error;
    } // method setLine5

    /**
     * Called when an exception has occured
     * @param  line5 (String)
     */
    private void setLine5Error (String line5, Exception e, int error) {
        this.line5 = line5;
        line5ErrorMessage = e.toString();
        line5Error = error;
    } // method setLine5Error


    /**
     * Set the 'line6' class variable
     * @param  line6 (String)
     */
    public int setLine6(String line6) {
        try {
            this.line6 = line6;
            if (this.line6 != CHARNULL) {
                this.line6 = stripCRLF(this.line6.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line6 = " + this.line6);
        } catch (Exception e) {
            setLine6Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line6Error;
    } // method setLine6

    /**
     * Called when an exception has occured
     * @param  line6 (String)
     */
    private void setLine6Error (String line6, Exception e, int error) {
        this.line6 = line6;
        line6ErrorMessage = e.toString();
        line6Error = error;
    } // method setLine6Error


    /**
     * Set the 'line7' class variable
     * @param  line7 (String)
     */
    public int setLine7(String line7) {
        try {
            this.line7 = line7;
            if (this.line7 != CHARNULL) {
                this.line7 = stripCRLF(this.line7.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line7 = " + this.line7);
        } catch (Exception e) {
            setLine7Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line7Error;
    } // method setLine7

    /**
     * Called when an exception has occured
     * @param  line7 (String)
     */
    private void setLine7Error (String line7, Exception e, int error) {
        this.line7 = line7;
        line7ErrorMessage = e.toString();
        line7Error = error;
    } // method setLine7Error


    /**
     * Set the 'line8' class variable
     * @param  line8 (String)
     */
    public int setLine8(String line8) {
        try {
            this.line8 = line8;
            if (this.line8 != CHARNULL) {
                this.line8 = stripCRLF(this.line8.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line8 = " + this.line8);
        } catch (Exception e) {
            setLine8Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line8Error;
    } // method setLine8

    /**
     * Called when an exception has occured
     * @param  line8 (String)
     */
    private void setLine8Error (String line8, Exception e, int error) {
        this.line8 = line8;
        line8ErrorMessage = e.toString();
        line8Error = error;
    } // method setLine8Error


    /**
     * Set the 'line9' class variable
     * @param  line9 (String)
     */
    public int setLine9(String line9) {
        try {
            this.line9 = line9;
            if (this.line9 != CHARNULL) {
                this.line9 = stripCRLF(this.line9.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line9 = " + this.line9);
        } catch (Exception e) {
            setLine9Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line9Error;
    } // method setLine9

    /**
     * Called when an exception has occured
     * @param  line9 (String)
     */
    private void setLine9Error (String line9, Exception e, int error) {
        this.line9 = line9;
        line9ErrorMessage = e.toString();
        line9Error = error;
    } // method setLine9Error


    /**
     * Set the 'line10' class variable
     * @param  line10 (String)
     */
    public int setLine10(String line10) {
        try {
            this.line10 = line10;
            if (this.line10 != CHARNULL) {
                this.line10 = stripCRLF(this.line10.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>line10 = " + this.line10);
        } catch (Exception e) {
            setLine10Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return line10Error;
    } // method setLine10

    /**
     * Called when an exception has occured
     * @param  line10 (String)
     */
    private void setLine10Error (String line10, Exception e, int error) {
        this.line10 = line10;
        line10ErrorMessage = e.toString();
        line10Error = error;
    } // method setLine10Error


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
     * Return the 'line1' class variable
     * @return line1 (String)
     */
    public String getLine1() {
        return line1;
    } // method getLine1

    /**
     * Return the 'line1' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine1 methods
     * @return line1 (String)
     */
    public String getLine1(String s) {
        return (line1 != CHARNULL ? line1.replace('"','\'') : "");
    } // method getLine1


    /**
     * Return the 'line2' class variable
     * @return line2 (String)
     */
    public String getLine2() {
        return line2;
    } // method getLine2

    /**
     * Return the 'line2' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine2 methods
     * @return line2 (String)
     */
    public String getLine2(String s) {
        return (line2 != CHARNULL ? line2.replace('"','\'') : "");
    } // method getLine2


    /**
     * Return the 'line3' class variable
     * @return line3 (String)
     */
    public String getLine3() {
        return line3;
    } // method getLine3

    /**
     * Return the 'line3' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine3 methods
     * @return line3 (String)
     */
    public String getLine3(String s) {
        return (line3 != CHARNULL ? line3.replace('"','\'') : "");
    } // method getLine3


    /**
     * Return the 'line4' class variable
     * @return line4 (String)
     */
    public String getLine4() {
        return line4;
    } // method getLine4

    /**
     * Return the 'line4' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine4 methods
     * @return line4 (String)
     */
    public String getLine4(String s) {
        return (line4 != CHARNULL ? line4.replace('"','\'') : "");
    } // method getLine4


    /**
     * Return the 'line5' class variable
     * @return line5 (String)
     */
    public String getLine5() {
        return line5;
    } // method getLine5

    /**
     * Return the 'line5' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine5 methods
     * @return line5 (String)
     */
    public String getLine5(String s) {
        return (line5 != CHARNULL ? line5.replace('"','\'') : "");
    } // method getLine5


    /**
     * Return the 'line6' class variable
     * @return line6 (String)
     */
    public String getLine6() {
        return line6;
    } // method getLine6

    /**
     * Return the 'line6' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine6 methods
     * @return line6 (String)
     */
    public String getLine6(String s) {
        return (line6 != CHARNULL ? line6.replace('"','\'') : "");
    } // method getLine6


    /**
     * Return the 'line7' class variable
     * @return line7 (String)
     */
    public String getLine7() {
        return line7;
    } // method getLine7

    /**
     * Return the 'line7' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine7 methods
     * @return line7 (String)
     */
    public String getLine7(String s) {
        return (line7 != CHARNULL ? line7.replace('"','\'') : "");
    } // method getLine7


    /**
     * Return the 'line8' class variable
     * @return line8 (String)
     */
    public String getLine8() {
        return line8;
    } // method getLine8

    /**
     * Return the 'line8' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine8 methods
     * @return line8 (String)
     */
    public String getLine8(String s) {
        return (line8 != CHARNULL ? line8.replace('"','\'') : "");
    } // method getLine8


    /**
     * Return the 'line9' class variable
     * @return line9 (String)
     */
    public String getLine9() {
        return line9;
    } // method getLine9

    /**
     * Return the 'line9' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine9 methods
     * @return line9 (String)
     */
    public String getLine9(String s) {
        return (line9 != CHARNULL ? line9.replace('"','\'') : "");
    } // method getLine9


    /**
     * Return the 'line10' class variable
     * @return line10 (String)
     */
    public String getLine10() {
        return line10;
    } // method getLine10

    /**
     * Return the 'line10' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLine10 methods
     * @return line10 (String)
     */
    public String getLine10(String s) {
        return (line10 != CHARNULL ? line10.replace('"','\'') : "");
    } // method getLine10


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
            (line1 == CHARNULL) &&
            (line2 == CHARNULL) &&
            (line3 == CHARNULL) &&
            (line4 == CHARNULL) &&
            (line5 == CHARNULL) &&
            (line6 == CHARNULL) &&
            (line7 == CHARNULL) &&
            (line8 == CHARNULL) &&
            (line9 == CHARNULL) &&
            (line10 == CHARNULL)) {
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
            line1Error +
            line2Error +
            line3Error +
            line4Error +
            line5Error +
            line6Error +
            line7Error +
            line8Error +
            line9Error +
            line10Error;
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
     * Gets the errorcode for the line1 instance variable
     * @return errorcode (int)
     */
    public int getLine1Error() {
        return line1Error;
    } // method getLine1Error

    /**
     * Gets the errorMessage for the line1 instance variable
     * @return errorMessage (String)
     */
    public String getLine1ErrorMessage() {
        return line1ErrorMessage;
    } // method getLine1ErrorMessage


    /**
     * Gets the errorcode for the line2 instance variable
     * @return errorcode (int)
     */
    public int getLine2Error() {
        return line2Error;
    } // method getLine2Error

    /**
     * Gets the errorMessage for the line2 instance variable
     * @return errorMessage (String)
     */
    public String getLine2ErrorMessage() {
        return line2ErrorMessage;
    } // method getLine2ErrorMessage


    /**
     * Gets the errorcode for the line3 instance variable
     * @return errorcode (int)
     */
    public int getLine3Error() {
        return line3Error;
    } // method getLine3Error

    /**
     * Gets the errorMessage for the line3 instance variable
     * @return errorMessage (String)
     */
    public String getLine3ErrorMessage() {
        return line3ErrorMessage;
    } // method getLine3ErrorMessage


    /**
     * Gets the errorcode for the line4 instance variable
     * @return errorcode (int)
     */
    public int getLine4Error() {
        return line4Error;
    } // method getLine4Error

    /**
     * Gets the errorMessage for the line4 instance variable
     * @return errorMessage (String)
     */
    public String getLine4ErrorMessage() {
        return line4ErrorMessage;
    } // method getLine4ErrorMessage


    /**
     * Gets the errorcode for the line5 instance variable
     * @return errorcode (int)
     */
    public int getLine5Error() {
        return line5Error;
    } // method getLine5Error

    /**
     * Gets the errorMessage for the line5 instance variable
     * @return errorMessage (String)
     */
    public String getLine5ErrorMessage() {
        return line5ErrorMessage;
    } // method getLine5ErrorMessage


    /**
     * Gets the errorcode for the line6 instance variable
     * @return errorcode (int)
     */
    public int getLine6Error() {
        return line6Error;
    } // method getLine6Error

    /**
     * Gets the errorMessage for the line6 instance variable
     * @return errorMessage (String)
     */
    public String getLine6ErrorMessage() {
        return line6ErrorMessage;
    } // method getLine6ErrorMessage


    /**
     * Gets the errorcode for the line7 instance variable
     * @return errorcode (int)
     */
    public int getLine7Error() {
        return line7Error;
    } // method getLine7Error

    /**
     * Gets the errorMessage for the line7 instance variable
     * @return errorMessage (String)
     */
    public String getLine7ErrorMessage() {
        return line7ErrorMessage;
    } // method getLine7ErrorMessage


    /**
     * Gets the errorcode for the line8 instance variable
     * @return errorcode (int)
     */
    public int getLine8Error() {
        return line8Error;
    } // method getLine8Error

    /**
     * Gets the errorMessage for the line8 instance variable
     * @return errorMessage (String)
     */
    public String getLine8ErrorMessage() {
        return line8ErrorMessage;
    } // method getLine8ErrorMessage


    /**
     * Gets the errorcode for the line9 instance variable
     * @return errorcode (int)
     */
    public int getLine9Error() {
        return line9Error;
    } // method getLine9Error

    /**
     * Gets the errorMessage for the line9 instance variable
     * @return errorMessage (String)
     */
    public String getLine9ErrorMessage() {
        return line9ErrorMessage;
    } // method getLine9ErrorMessage


    /**
     * Gets the errorcode for the line10 instance variable
     * @return errorcode (int)
     */
    public int getLine10Error() {
        return line10Error;
    } // method getLine10Error

    /**
     * Gets the errorMessage for the line10 instance variable
     * @return errorMessage (String)
     */
    public String getLine10ErrorMessage() {
        return line10ErrorMessage;
    } // method getLine10ErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnSurveyNotes. e.g.<pre>
     * MrnSurveyNotes surveyNotes = new MrnSurveyNotes(<val1>);
     * MrnSurveyNotes surveyNotesArray[] = surveyNotes.get();</pre>
     * will get the MrnSurveyNotes record where surveyId = <val1>.
     * @return Array of MrnSurveyNotes (MrnSurveyNotes[])
     */
    public MrnSurveyNotes[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnSurveyNotes surveyNotesArray[] =
     *     new MrnSurveyNotes().get(MrnSurveyNotes.SURVEY_ID+"=<val1>");</pre>
     * will get the MrnSurveyNotes record where surveyId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnSurveyNotes (MrnSurveyNotes[])
     */
    public MrnSurveyNotes[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnSurveyNotes surveyNotesArray[] =
     *     new MrnSurveyNotes().get("1=1",surveyNotes.SURVEY_ID);</pre>
     * will get the all the MrnSurveyNotes records, and order them by surveyId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSurveyNotes (MrnSurveyNotes[])
     */
    public MrnSurveyNotes[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnSurveyNotes.SURVEY_ID,MrnSurveyNotes.LINE1;
     * String where = MrnSurveyNotes.SURVEY_ID + "=<val1";
     * String order = MrnSurveyNotes.SURVEY_ID;
     * MrnSurveyNotes surveyNotesArray[] =
     *     new MrnSurveyNotes().get(columns, where, order);</pre>
     * will get the surveyId and line1 colums of all MrnSurveyNotes records,
     * where surveyId = <val1>, and order them by surveyId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSurveyNotes (MrnSurveyNotes[])
     */
    public MrnSurveyNotes[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnSurveyNotes.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnSurveyNotes[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int surveyIdCol = db.getColNumber(SURVEY_ID);
        int line1Col    = db.getColNumber(LINE1);
        int line2Col    = db.getColNumber(LINE2);
        int line3Col    = db.getColNumber(LINE3);
        int line4Col    = db.getColNumber(LINE4);
        int line5Col    = db.getColNumber(LINE5);
        int line6Col    = db.getColNumber(LINE6);
        int line7Col    = db.getColNumber(LINE7);
        int line8Col    = db.getColNumber(LINE8);
        int line9Col    = db.getColNumber(LINE9);
        int line10Col   = db.getColNumber(LINE10);
        MrnSurveyNotes[] cArray = new MrnSurveyNotes[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnSurveyNotes();
            if (surveyIdCol != -1)
                cArray[i].setSurveyId((String) row.elementAt(surveyIdCol));
            if (line1Col != -1)
                cArray[i].setLine1   ((String) row.elementAt(line1Col));
            if (line2Col != -1)
                cArray[i].setLine2   ((String) row.elementAt(line2Col));
            if (line3Col != -1)
                cArray[i].setLine3   ((String) row.elementAt(line3Col));
            if (line4Col != -1)
                cArray[i].setLine4   ((String) row.elementAt(line4Col));
            if (line5Col != -1)
                cArray[i].setLine5   ((String) row.elementAt(line5Col));
            if (line6Col != -1)
                cArray[i].setLine6   ((String) row.elementAt(line6Col));
            if (line7Col != -1)
                cArray[i].setLine7   ((String) row.elementAt(line7Col));
            if (line8Col != -1)
                cArray[i].setLine8   ((String) row.elementAt(line8Col));
            if (line9Col != -1)
                cArray[i].setLine9   ((String) row.elementAt(line9Col));
            if (line10Col != -1)
                cArray[i].setLine10  ((String) row.elementAt(line10Col));
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
     *     new MrnSurveyNotes(
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
     *     surveyId = <val1>,
     *     line1    = <val2>,
     *     line2    = <val3>,
     *     line3    = <val4>,
     *     line4    = <val5>,
     *     line5    = <val6>,
     *     line6    = <val7>,
     *     line7    = <val8>,
     *     line8    = <val9>,
     *     line9    = <val10>,
     *     line10   = <val11>.
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
     * Boolean success = new MrnSurveyNotes(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where line1 = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSurveyNotes surveyNotes = new MrnSurveyNotes();
     * success = surveyNotes.del(MrnSurveyNotes.SURVEY_ID+"=<val1>");</pre>
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
     * update are taken from the MrnSurveyNotes argument, .e.g.<pre>
     * Boolean success
     * MrnSurveyNotes updMrnSurveyNotes = new MrnSurveyNotes();
     * updMrnSurveyNotes.setLine1(<val2>);
     * MrnSurveyNotes whereMrnSurveyNotes = new MrnSurveyNotes(<val1>);
     * success = whereMrnSurveyNotes.upd(updMrnSurveyNotes);</pre>
     * will update Line1 to <val2> for all records where
     * surveyId = <val1>.
     * @param  surveyNotes  A MrnSurveyNotes variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSurveyNotes surveyNotes) {
        return db.update (TABLE, createColVals(surveyNotes), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSurveyNotes updMrnSurveyNotes = new MrnSurveyNotes();
     * updMrnSurveyNotes.setLine1(<val2>);
     * MrnSurveyNotes whereMrnSurveyNotes = new MrnSurveyNotes();
     * success = whereMrnSurveyNotes.upd(
     *     updMrnSurveyNotes, MrnSurveyNotes.SURVEY_ID+"=<val1>");</pre>
     * will update Line1 to <val2> for all records where
     * surveyId = <val1>.
     * @param  surveyNotes  A MrnSurveyNotes variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSurveyNotes surveyNotes, String where) {
        return db.update (TABLE, createColVals(surveyNotes), where);
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
        if (getLine1() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE1 + "='" + getLine1() + "'";
        } // if getLine1
        if (getLine2() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE2 + "='" + getLine2() + "'";
        } // if getLine2
        if (getLine3() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE3 + "='" + getLine3() + "'";
        } // if getLine3
        if (getLine4() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE4 + "='" + getLine4() + "'";
        } // if getLine4
        if (getLine5() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE5 + "='" + getLine5() + "'";
        } // if getLine5
        if (getLine6() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE6 + "='" + getLine6() + "'";
        } // if getLine6
        if (getLine7() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE7 + "='" + getLine7() + "'";
        } // if getLine7
        if (getLine8() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE8 + "='" + getLine8() + "'";
        } // if getLine8
        if (getLine9() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE9 + "='" + getLine9() + "'";
        } // if getLine9
        if (getLine10() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINE10 + "='" + getLine10() + "'";
        } // if getLine10
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnSurveyNotes aVar) {
        String colVals = "";
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getLine1() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE1 +"=";
            colVals += (aVar.getLine1().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine1() + "'");
        } // if aVar.getLine1
        if (aVar.getLine2() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE2 +"=";
            colVals += (aVar.getLine2().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine2() + "'");
        } // if aVar.getLine2
        if (aVar.getLine3() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE3 +"=";
            colVals += (aVar.getLine3().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine3() + "'");
        } // if aVar.getLine3
        if (aVar.getLine4() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE4 +"=";
            colVals += (aVar.getLine4().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine4() + "'");
        } // if aVar.getLine4
        if (aVar.getLine5() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE5 +"=";
            colVals += (aVar.getLine5().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine5() + "'");
        } // if aVar.getLine5
        if (aVar.getLine6() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE6 +"=";
            colVals += (aVar.getLine6().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine6() + "'");
        } // if aVar.getLine6
        if (aVar.getLine7() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE7 +"=";
            colVals += (aVar.getLine7().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine7() + "'");
        } // if aVar.getLine7
        if (aVar.getLine8() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE8 +"=";
            colVals += (aVar.getLine8().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine8() + "'");
        } // if aVar.getLine8
        if (aVar.getLine9() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE9 +"=";
            colVals += (aVar.getLine9().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine9() + "'");
        } // if aVar.getLine9
        if (aVar.getLine10() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINE10 +"=";
            colVals += (aVar.getLine10().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLine10() + "'");
        } // if aVar.getLine10
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SURVEY_ID;
        if (getLine1() != CHARNULL) {
            columns = columns + "," + LINE1;
        } // if getLine1
        if (getLine2() != CHARNULL) {
            columns = columns + "," + LINE2;
        } // if getLine2
        if (getLine3() != CHARNULL) {
            columns = columns + "," + LINE3;
        } // if getLine3
        if (getLine4() != CHARNULL) {
            columns = columns + "," + LINE4;
        } // if getLine4
        if (getLine5() != CHARNULL) {
            columns = columns + "," + LINE5;
        } // if getLine5
        if (getLine6() != CHARNULL) {
            columns = columns + "," + LINE6;
        } // if getLine6
        if (getLine7() != CHARNULL) {
            columns = columns + "," + LINE7;
        } // if getLine7
        if (getLine8() != CHARNULL) {
            columns = columns + "," + LINE8;
        } // if getLine8
        if (getLine9() != CHARNULL) {
            columns = columns + "," + LINE9;
        } // if getLine9
        if (getLine10() != CHARNULL) {
            columns = columns + "," + LINE10;
        } // if getLine10
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getSurveyId() + "'";
        if (getLine1() != CHARNULL) {
            values  = values  + ",'" + getLine1() + "'";
        } // if getLine1
        if (getLine2() != CHARNULL) {
            values  = values  + ",'" + getLine2() + "'";
        } // if getLine2
        if (getLine3() != CHARNULL) {
            values  = values  + ",'" + getLine3() + "'";
        } // if getLine3
        if (getLine4() != CHARNULL) {
            values  = values  + ",'" + getLine4() + "'";
        } // if getLine4
        if (getLine5() != CHARNULL) {
            values  = values  + ",'" + getLine5() + "'";
        } // if getLine5
        if (getLine6() != CHARNULL) {
            values  = values  + ",'" + getLine6() + "'";
        } // if getLine6
        if (getLine7() != CHARNULL) {
            values  = values  + ",'" + getLine7() + "'";
        } // if getLine7
        if (getLine8() != CHARNULL) {
            values  = values  + ",'" + getLine8() + "'";
        } // if getLine8
        if (getLine9() != CHARNULL) {
            values  = values  + ",'" + getLine9() + "'";
        } // if getLine9
        if (getLine10() != CHARNULL) {
            values  = values  + ",'" + getLine10() + "'";
        } // if getLine10
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getSurveyId("") + "|" +
            getLine1("")    + "|" +
            getLine2("")    + "|" +
            getLine3("")    + "|" +
            getLine4("")    + "|" +
            getLine5("")    + "|" +
            getLine6("")    + "|" +
            getLine7("")    + "|" +
            getLine8("")    + "|" +
            getLine9("")    + "|" +
            getLine10("")   + "|";
    } // method toString

} // class MrnSurveyNotes
