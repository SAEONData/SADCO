package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the POSITIONS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnPositions extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "POSITIONS";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The piCode field name */
    public static final String PI_CODE = "PI_CODE";
    /** The typeCode field name */
    public static final String TYPE_CODE = "TYPE_CODE";
    /** The index1010 field name */
    public static final String INDEX10_10 = "INDEX10_10";
    /** The index11 field name */
    public static final String INDEX1_1 = "INDEX1_1";
    /** The latitude field name */
    public static final String LATITUDE = "LATITUDE";
    /** The longitude field name */
    public static final String LONGITUDE = "LONGITUDE";
    /** The remarks field name */
    public static final String REMARKS = "REMARKS";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    surveyId;
    private String    piCode;
    private String    typeCode;
    private String    index1010;
    private String    index11;
    private float     latitude;
    private float     longitude;
    private String    remarks;

    /** The error variables  */
    private int codeError      = ERROR_NORMAL;
    private int surveyIdError  = ERROR_NORMAL;
    private int piCodeError    = ERROR_NORMAL;
    private int typeCodeError  = ERROR_NORMAL;
    private int index1010Error = ERROR_NORMAL;
    private int index11Error   = ERROR_NORMAL;
    private int latitudeError  = ERROR_NORMAL;
    private int longitudeError = ERROR_NORMAL;
    private int remarksError   = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage      = "";
    private String surveyIdErrorMessage  = "";
    private String piCodeErrorMessage    = "";
    private String typeCodeErrorMessage  = "";
    private String index1010ErrorMessage = "";
    private String index11ErrorMessage   = "";
    private String latitudeErrorMessage  = "";
    private String longitudeErrorMessage = "";
    private String remarksErrorMessage   = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final float     LATITUDE_MN = FLOATMIN;
    public static final float     LATITUDE_MX = FLOATMAX;
    public static final float     LONGITUDE_MN = FLOATMIN;
    public static final float     LONGITUDE_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception latitudeOutOfBoundsException =
        new Exception ("'latitude' out of bounds: " +
            LATITUDE_MN + " - " + LATITUDE_MX);
    Exception longitudeOutOfBoundsException =
        new Exception ("'longitude' out of bounds: " +
            LONGITUDE_MN + " - " + LONGITUDE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnPositions() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnPositions constructor 1"); // debug
    } // MrnPositions constructor

    /**
     * Instantiate a MrnPositions object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MrnPositions(
            int code) {
        this();
        setCode      (code     );
        if (dbg) System.out.println ("<br>in MrnPositions constructor 2"); // debug
    } // MrnPositions constructor

    /**
     * Instantiate a MrnPositions object and initialize the instance variables.
     * @param code       The code      (int)
     * @param surveyId   The surveyId  (String)
     * @param piCode     The piCode    (String)
     * @param typeCode   The typeCode  (String)
     * @param index1010  The index1010 (String)
     * @param index11    The index11   (String)
     * @param latitude   The latitude  (float)
     * @param longitude  The longitude (float)
     * @param remarks    The remarks   (String)
     */
    public MrnPositions(
            int    code,
            String surveyId,
            String piCode,
            String typeCode,
            String index1010,
            String index11,
            float  latitude,
            float  longitude,
            String remarks) {
        this();
        setCode      (code     );
        setSurveyId  (surveyId );
        setPiCode    (piCode   );
        setTypeCode  (typeCode );
        setIndex1010 (index1010);
        setIndex11   (index11  );
        setLatitude  (latitude );
        setLongitude (longitude);
        setRemarks   (remarks  );
        if (dbg) System.out.println ("<br>in MrnPositions constructor 3"); // debug
    } // MrnPositions constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode      (INTNULL  );
        setSurveyId  (CHARNULL );
        setPiCode    (CHARNULL );
        setTypeCode  (CHARNULL );
        setIndex1010 (CHARNULL );
        setIndex11   (CHARNULL );
        setLatitude  (FLOATNULL);
        setLongitude (FLOATNULL);
        setRemarks   (CHARNULL );
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
     * Set the 'index1010' class variable
     * @param  index1010 (String)
     */
    public int setIndex1010(String index1010) {
        try {
            this.index1010 = index1010;
            if (this.index1010 != CHARNULL) {
                this.index1010 = stripCRLF(this.index1010.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>index1010 = " + this.index1010);
        } catch (Exception e) {
            setIndex1010Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return index1010Error;
    } // method setIndex1010

    /**
     * Called when an exception has occured
     * @param  index1010 (String)
     */
    private void setIndex1010Error (String index1010, Exception e, int error) {
        this.index1010 = index1010;
        index1010ErrorMessage = e.toString();
        index1010Error = error;
    } // method setIndex1010Error


    /**
     * Set the 'index11' class variable
     * @param  index11 (String)
     */
    public int setIndex11(String index11) {
        try {
            this.index11 = index11;
            if (this.index11 != CHARNULL) {
                this.index11 = stripCRLF(this.index11.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>index11 = " + this.index11);
        } catch (Exception e) {
            setIndex11Error(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return index11Error;
    } // method setIndex11

    /**
     * Called when an exception has occured
     * @param  index11 (String)
     */
    private void setIndex11Error (String index11, Exception e, int error) {
        this.index11 = index11;
        index11ErrorMessage = e.toString();
        index11Error = error;
    } // method setIndex11Error


    /**
     * Set the 'latitude' class variable
     * @param  latitude (float)
     */
    public int setLatitude(float latitude) {
        try {
            if ( ((latitude == FLOATNULL) ||
                  (latitude == FLOATNULL2)) ||
                !((latitude < LATITUDE_MN) ||
                  (latitude > LATITUDE_MX)) ) {
                this.latitude = latitude;
                latitudeError = ERROR_NORMAL;
            } else {
                throw latitudeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Set the 'latitude' class variable
     * @param  latitude (Integer)
     */
    public int setLatitude(Integer latitude) {
        try {
            setLatitude(latitude.floatValue());
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Set the 'latitude' class variable
     * @param  latitude (Float)
     */
    public int setLatitude(Float latitude) {
        try {
            setLatitude(latitude.floatValue());
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Set the 'latitude' class variable
     * @param  latitude (String)
     */
    public int setLatitude(String latitude) {
        try {
            setLatitude(new Float(latitude).floatValue());
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Called when an exception has occured
     * @param  latitude (String)
     */
    private void setLatitudeError (float latitude, Exception e, int error) {
        this.latitude = latitude;
        latitudeErrorMessage = e.toString();
        latitudeError = error;
    } // method setLatitudeError


    /**
     * Set the 'longitude' class variable
     * @param  longitude (float)
     */
    public int setLongitude(float longitude) {
        try {
            if ( ((longitude == FLOATNULL) ||
                  (longitude == FLOATNULL2)) ||
                !((longitude < LONGITUDE_MN) ||
                  (longitude > LONGITUDE_MX)) ) {
                this.longitude = longitude;
                longitudeError = ERROR_NORMAL;
            } else {
                throw longitudeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Set the 'longitude' class variable
     * @param  longitude (Integer)
     */
    public int setLongitude(Integer longitude) {
        try {
            setLongitude(longitude.floatValue());
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Set the 'longitude' class variable
     * @param  longitude (Float)
     */
    public int setLongitude(Float longitude) {
        try {
            setLongitude(longitude.floatValue());
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Set the 'longitude' class variable
     * @param  longitude (String)
     */
    public int setLongitude(String longitude) {
        try {
            setLongitude(new Float(longitude).floatValue());
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Called when an exception has occured
     * @param  longitude (String)
     */
    private void setLongitudeError (float longitude, Exception e, int error) {
        this.longitude = longitude;
        longitudeErrorMessage = e.toString();
        longitudeError = error;
    } // method setLongitudeError


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
     * Return the 'index1010' class variable
     * @return index1010 (String)
     */
    public String getIndex1010() {
        return index1010;
    } // method getIndex1010

    /**
     * Return the 'index1010' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getIndex1010 methods
     * @return index1010 (String)
     */
    public String getIndex1010(String s) {
        return (index1010 != CHARNULL ? index1010.replace('"','\'') : "");
    } // method getIndex1010


    /**
     * Return the 'index11' class variable
     * @return index11 (String)
     */
    public String getIndex11() {
        return index11;
    } // method getIndex11

    /**
     * Return the 'index11' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getIndex11 methods
     * @return index11 (String)
     */
    public String getIndex11(String s) {
        return (index11 != CHARNULL ? index11.replace('"','\'') : "");
    } // method getIndex11


    /**
     * Return the 'latitude' class variable
     * @return latitude (float)
     */
    public float getLatitude() {
        return latitude;
    } // method getLatitude

    /**
     * Return the 'latitude' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLatitude methods
     * @return latitude (String)
     */
    public String getLatitude(String s) {
        return ((latitude != FLOATNULL) ? new Float(latitude).toString() : "");
    } // method getLatitude


    /**
     * Return the 'longitude' class variable
     * @return longitude (float)
     */
    public float getLongitude() {
        return longitude;
    } // method getLongitude

    /**
     * Return the 'longitude' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLongitude methods
     * @return longitude (String)
     */
    public String getLongitude(String s) {
        return ((longitude != FLOATNULL) ? new Float(longitude).toString() : "");
    } // method getLongitude


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
            (typeCode == CHARNULL) &&
            (index1010 == CHARNULL) &&
            (index11 == CHARNULL) &&
            (latitude == FLOATNULL) &&
            (longitude == FLOATNULL) &&
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
            typeCodeError +
            index1010Error +
            index11Error +
            latitudeError +
            longitudeError +
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
     * Gets the errorcode for the index1010 instance variable
     * @return errorcode (int)
     */
    public int getIndex1010Error() {
        return index1010Error;
    } // method getIndex1010Error

    /**
     * Gets the errorMessage for the index1010 instance variable
     * @return errorMessage (String)
     */
    public String getIndex1010ErrorMessage() {
        return index1010ErrorMessage;
    } // method getIndex1010ErrorMessage


    /**
     * Gets the errorcode for the index11 instance variable
     * @return errorcode (int)
     */
    public int getIndex11Error() {
        return index11Error;
    } // method getIndex11Error

    /**
     * Gets the errorMessage for the index11 instance variable
     * @return errorMessage (String)
     */
    public String getIndex11ErrorMessage() {
        return index11ErrorMessage;
    } // method getIndex11ErrorMessage


    /**
     * Gets the errorcode for the latitude instance variable
     * @return errorcode (int)
     */
    public int getLatitudeError() {
        return latitudeError;
    } // method getLatitudeError

    /**
     * Gets the errorMessage for the latitude instance variable
     * @return errorMessage (String)
     */
    public String getLatitudeErrorMessage() {
        return latitudeErrorMessage;
    } // method getLatitudeErrorMessage


    /**
     * Gets the errorcode for the longitude instance variable
     * @return errorcode (int)
     */
    public int getLongitudeError() {
        return longitudeError;
    } // method getLongitudeError

    /**
     * Gets the errorMessage for the longitude instance variable
     * @return errorMessage (String)
     */
    public String getLongitudeErrorMessage() {
        return longitudeErrorMessage;
    } // method getLongitudeErrorMessage


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
     * are returned in an array of MrnPositions. e.g.<pre>
     * MrnPositions positions = new MrnPositions(<val1>);
     * MrnPositions positionsArray[] = positions.get();</pre>
     * will get the MrnPositions record where code = <val1>.
     * @return Array of MrnPositions (MrnPositions[])
     */
    public MrnPositions[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnPositions positionsArray[] =
     *     new MrnPositions().get(MrnPositions.CODE+"=<val1>");</pre>
     * will get the MrnPositions record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnPositions (MrnPositions[])
     */
    public MrnPositions[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnPositions positionsArray[] =
     *     new MrnPositions().get("1=1",positions.CODE);</pre>
     * will get the all the MrnPositions records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPositions (MrnPositions[])
     */
    public MrnPositions[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnPositions.CODE,MrnPositions.SURVEY_ID;
     * String where = MrnPositions.CODE + "=<val1";
     * String order = MrnPositions.CODE;
     * MrnPositions positionsArray[] =
     *     new MrnPositions().get(columns, where, order);</pre>
     * will get the code and surveyId colums of all MrnPositions records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPositions (MrnPositions[])
     */
    public MrnPositions[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnPositions.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnPositions[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol      = db.getColNumber(CODE);
        int surveyIdCol  = db.getColNumber(SURVEY_ID);
        int piCodeCol    = db.getColNumber(PI_CODE);
        int typeCodeCol  = db.getColNumber(TYPE_CODE);
        int index1010Col = db.getColNumber(INDEX10_10);
        int index11Col   = db.getColNumber(INDEX1_1);
        int latitudeCol  = db.getColNumber(LATITUDE);
        int longitudeCol = db.getColNumber(LONGITUDE);
        int remarksCol   = db.getColNumber(REMARKS);
        MrnPositions[] cArray = new MrnPositions[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnPositions();
            if (codeCol != -1)
                cArray[i].setCode     ((String) row.elementAt(codeCol));
            if (surveyIdCol != -1)
                cArray[i].setSurveyId ((String) row.elementAt(surveyIdCol));
            if (piCodeCol != -1)
                cArray[i].setPiCode   ((String) row.elementAt(piCodeCol));
            if (typeCodeCol != -1)
                cArray[i].setTypeCode ((String) row.elementAt(typeCodeCol));
            if (index1010Col != -1)
                cArray[i].setIndex1010((String) row.elementAt(index1010Col));
            if (index11Col != -1)
                cArray[i].setIndex11  ((String) row.elementAt(index11Col));
            if (latitudeCol != -1)
                cArray[i].setLatitude ((String) row.elementAt(latitudeCol));
            if (longitudeCol != -1)
                cArray[i].setLongitude((String) row.elementAt(longitudeCol));
            if (remarksCol != -1)
                cArray[i].setRemarks  ((String) row.elementAt(remarksCol));
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
     *     new MrnPositions(
     *         INTNULL,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>,
     *         <val9>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     surveyId  = <val2>,
     *     piCode    = <val3>,
     *     typeCode  = <val4>,
     *     index1010 = <val5>,
     *     index11   = <val6>,
     *     latitude  = <val7>,
     *     longitude = <val8>,
     *     remarks   = <val9>.
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
     * Boolean success = new MrnPositions(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
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
     * MrnPositions positions = new MrnPositions();
     * success = positions.del(MrnPositions.CODE+"=<val1>");</pre>
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
     * update are taken from the MrnPositions argument, .e.g.<pre>
     * Boolean success
     * MrnPositions updMrnPositions = new MrnPositions();
     * updMrnPositions.setSurveyId(<val2>);
     * MrnPositions whereMrnPositions = new MrnPositions(<val1>);
     * success = whereMrnPositions.upd(updMrnPositions);</pre>
     * will update SurveyId to <val2> for all records where
     * code = <val1>.
     * @param  positions  A MrnPositions variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPositions positions) {
        return db.update (TABLE, createColVals(positions), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPositions updMrnPositions = new MrnPositions();
     * updMrnPositions.setSurveyId(<val2>);
     * MrnPositions whereMrnPositions = new MrnPositions();
     * success = whereMrnPositions.upd(
     *     updMrnPositions, MrnPositions.CODE+"=<val1>");</pre>
     * will update SurveyId to <val2> for all records where
     * code = <val1>.
     * @param  positions  A MrnPositions variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPositions positions, String where) {
        return db.update (TABLE, createColVals(positions), where);
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
        if (getTypeCode() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TYPE_CODE + "='" + getTypeCode() + "'";
        } // if getTypeCode
        if (getIndex1010() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INDEX10_10 + "='" + getIndex1010() + "'";
        } // if getIndex1010
        if (getIndex11() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INDEX1_1 + "='" + getIndex11() + "'";
        } // if getIndex11
        if (getLatitude() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LATITUDE + "=" + getLatitude("");
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LONGITUDE + "=" + getLongitude("");
        } // if getLongitude
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
    private String createColVals(MrnPositions aVar) {
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
        if (aVar.getTypeCode() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TYPE_CODE +"=";
            colVals += (aVar.getTypeCode().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTypeCode() + "'");
        } // if aVar.getTypeCode
        if (aVar.getIndex1010() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INDEX10_10 +"=";
            colVals += (aVar.getIndex1010().equals(CHARNULL2) ?
                "null" : "'" + aVar.getIndex1010() + "'");
        } // if aVar.getIndex1010
        if (aVar.getIndex11() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INDEX1_1 +"=";
            colVals += (aVar.getIndex11().equals(CHARNULL2) ?
                "null" : "'" + aVar.getIndex11() + "'");
        } // if aVar.getIndex11
        if (aVar.getLatitude() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LATITUDE +"=";
            colVals += (aVar.getLatitude() == FLOATNULL2 ?
                "null" : aVar.getLatitude(""));
        } // if aVar.getLatitude
        if (aVar.getLongitude() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LONGITUDE +"=";
            colVals += (aVar.getLongitude() == FLOATNULL2 ?
                "null" : aVar.getLongitude(""));
        } // if aVar.getLongitude
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
        if (getTypeCode() != CHARNULL) {
            columns = columns + "," + TYPE_CODE;
        } // if getTypeCode
        if (getIndex1010() != CHARNULL) {
            columns = columns + "," + INDEX10_10;
        } // if getIndex1010
        if (getIndex11() != CHARNULL) {
            columns = columns + "," + INDEX1_1;
        } // if getIndex11
        if (getLatitude() != FLOATNULL) {
            columns = columns + "," + LATITUDE;
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            columns = columns + "," + LONGITUDE;
        } // if getLongitude
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
        if (getTypeCode() != CHARNULL) {
            values  = values  + ",'" + getTypeCode() + "'";
        } // if getTypeCode
        if (getIndex1010() != CHARNULL) {
            values  = values  + ",'" + getIndex1010() + "'";
        } // if getIndex1010
        if (getIndex11() != CHARNULL) {
            values  = values  + ",'" + getIndex11() + "'";
        } // if getIndex11
        if (getLatitude() != FLOATNULL) {
            values  = values  + "," + getLatitude("");
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            values  = values  + "," + getLongitude("");
        } // if getLongitude
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
            getCode("")      + "|" +
            getSurveyId("")  + "|" +
            getPiCode("")    + "|" +
            getTypeCode("")  + "|" +
            getIndex1010("") + "|" +
            getIndex11("")   + "|" +
            getLatitude("")  + "|" +
            getLongitude("") + "|" +
            getRemarks("")   + "|";
    } // method toString

} // class MrnPositions
