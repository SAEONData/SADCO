package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SEDCHEM2 table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 060127 - SIT Group
 * @version
 * 060127 - GenTableClassB class - create class<br>
 */
public class MrnSedchem2 extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SEDCHEM2";
    /** The sedphyCode field name */
    public static final String SEDPHY_CODE = "SEDPHY_CODE";
    /** The calcium field name */
    public static final String CALCIUM = "CALCIUM";
    /** The magnesium field name */
    public static final String MAGNESIUM = "MAGNESIUM";
    /** The potassium field name */
    public static final String POTASSIUM = "POTASSIUM";
    /** The sodium field name */
    public static final String SODIUM = "SODIUM";
    /** The strontium field name */
    public static final String STRONTIUM = "STRONTIUM";
    /** The so3 field name */
    public static final String SO3 = "SO3";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       sedphyCode;
    private float     calcium;
    private float     magnesium;
    private float     potassium;
    private float     sodium;
    private float     strontium;
    private float     so3;

    /** The error variables  */
    private int sedphyCodeError = ERROR_NORMAL;
    private int calciumError    = ERROR_NORMAL;
    private int magnesiumError  = ERROR_NORMAL;
    private int potassiumError  = ERROR_NORMAL;
    private int sodiumError     = ERROR_NORMAL;
    private int strontiumError  = ERROR_NORMAL;
    private int so3Error        = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String sedphyCodeErrorMessage = "";
    private String calciumErrorMessage    = "";
    private String magnesiumErrorMessage  = "";
    private String potassiumErrorMessage  = "";
    private String sodiumErrorMessage     = "";
    private String strontiumErrorMessage  = "";
    private String so3ErrorMessage        = "";

    /** The min-max constants for all numerics */
    public static final int       SEDPHY_CODE_MN = INTMIN;
    public static final int       SEDPHY_CODE_MX = INTMAX;
    public static final float     CALCIUM_MN = FLOATMIN;
    public static final float     CALCIUM_MX = FLOATMAX;
    public static final float     MAGNESIUM_MN = FLOATMIN;
    public static final float     MAGNESIUM_MX = FLOATMAX;
    public static final float     POTASSIUM_MN = FLOATMIN;
    public static final float     POTASSIUM_MX = FLOATMAX;
    public static final float     SODIUM_MN = FLOATMIN;
    public static final float     SODIUM_MX = FLOATMAX;
    public static final float     STRONTIUM_MN = FLOATMIN;
    public static final float     STRONTIUM_MX = FLOATMAX;
    public static final float     SO3_MN = FLOATMIN;
    public static final float     SO3_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception sedphyCodeOutOfBoundsException =
        new Exception ("'sedphyCode' out of bounds: " +
            SEDPHY_CODE_MN + " - " + SEDPHY_CODE_MX);
    Exception calciumOutOfBoundsException =
        new Exception ("'calcium' out of bounds: " +
            CALCIUM_MN + " - " + CALCIUM_MX);
    Exception magnesiumOutOfBoundsException =
        new Exception ("'magnesium' out of bounds: " +
            MAGNESIUM_MN + " - " + MAGNESIUM_MX);
    Exception potassiumOutOfBoundsException =
        new Exception ("'potassium' out of bounds: " +
            POTASSIUM_MN + " - " + POTASSIUM_MX);
    Exception sodiumOutOfBoundsException =
        new Exception ("'sodium' out of bounds: " +
            SODIUM_MN + " - " + SODIUM_MX);
    Exception strontiumOutOfBoundsException =
        new Exception ("'strontium' out of bounds: " +
            STRONTIUM_MN + " - " + STRONTIUM_MX);
    Exception so3OutOfBoundsException =
        new Exception ("'so3' out of bounds: " +
            SO3_MN + " - " + SO3_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnSedchem2() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnSedchem2 constructor 1"); // debug
    } // MrnSedchem2 constructor

    /**
     * Instantiate a MrnSedchem2 object and initialize the instance variables.
     * @param sedphyCode  The sedphyCode (int)
     */
    public MrnSedchem2(
            int sedphyCode) {
        this();
        setSedphyCode (sedphyCode);
        if (dbg) System.out.println ("<br>in MrnSedchem2 constructor 2"); // debug
    } // MrnSedchem2 constructor

    /**
     * Instantiate a MrnSedchem2 object and initialize the instance variables.
     * @param sedphyCode  The sedphyCode (int)
     * @param calcium     The calcium    (float)
     * @param magnesium   The magnesium  (float)
     * @param potassium   The potassium  (float)
     * @param sodium      The sodium     (float)
     * @param strontium   The strontium  (float)
     * @param so3         The so3        (float)
     * @return A MrnSedchem2 object
     */
    public MrnSedchem2(
            int   sedphyCode,
            float calcium,
            float magnesium,
            float potassium,
            float sodium,
            float strontium,
            float so3) {
        this();
        setSedphyCode (sedphyCode);
        setCalcium    (calcium   );
        setMagnesium  (magnesium );
        setPotassium  (potassium );
        setSodium     (sodium    );
        setStrontium  (strontium );
        setSo3        (so3       );
        if (dbg) System.out.println ("<br>in MrnSedchem2 constructor 3"); // debug
    } // MrnSedchem2 constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSedphyCode (INTNULL  );
        setCalcium    (FLOATNULL);
        setMagnesium  (FLOATNULL);
        setPotassium  (FLOATNULL);
        setSodium     (FLOATNULL);
        setStrontium  (FLOATNULL);
        setSo3        (FLOATNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'sedphyCode' class variable
     * @param  sedphyCode (int)
     */
    public int setSedphyCode(int sedphyCode) {
        try {
            if ( ((sedphyCode == INTNULL) || 
                  (sedphyCode == INTNULL2)) ||
                !((sedphyCode < SEDPHY_CODE_MN) ||
                  (sedphyCode > SEDPHY_CODE_MX)) ) {
                this.sedphyCode = sedphyCode;
                sedphyCodeError = ERROR_NORMAL;
            } else {
                throw sedphyCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSedphyCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sedphyCodeError;
    } // method setSedphyCode

    /**
     * Set the 'sedphyCode' class variable
     * @param  sedphyCode (Integer)
     */
    public int setSedphyCode(Integer sedphyCode) {
        try {
            setSedphyCode(sedphyCode.intValue());
        } catch (Exception e) {
            setSedphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyCodeError;
    } // method setSedphyCode

    /**
     * Set the 'sedphyCode' class variable
     * @param  sedphyCode (Float)
     */
    public int setSedphyCode(Float sedphyCode) {
        try {
            if (sedphyCode.floatValue() == FLOATNULL) {
                setSedphyCode(INTNULL);
            } else {
                setSedphyCode(sedphyCode.intValue());
            } // if (sedphyCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSedphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyCodeError;
    } // method setSedphyCode

    /**
     * Set the 'sedphyCode' class variable
     * @param  sedphyCode (String)
     */
    public int setSedphyCode(String sedphyCode) {
        try {
            setSedphyCode(new Integer(sedphyCode).intValue());
        } catch (Exception e) {
            setSedphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sedphyCodeError;
    } // method setSedphyCode

    /**
     * Called when an exception has occured
     * @param  sedphyCode (String)
     */
    private void setSedphyCodeError (int sedphyCode, Exception e, int error) {
        this.sedphyCode = sedphyCode;
        sedphyCodeErrorMessage = e.toString();
        sedphyCodeError = error;
    } // method setSedphyCodeError


    /**
     * Set the 'calcium' class variable
     * @param  calcium (float)
     */
    public int setCalcium(float calcium) {
        try {
            if ( ((calcium == FLOATNULL) || 
                  (calcium == FLOATNULL2)) ||
                !((calcium < CALCIUM_MN) ||
                  (calcium > CALCIUM_MX)) ) {
                this.calcium = calcium;
                calciumError = ERROR_NORMAL;
            } else {
                throw calciumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCalciumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return calciumError;
    } // method setCalcium

    /**
     * Set the 'calcium' class variable
     * @param  calcium (Integer)
     */
    public int setCalcium(Integer calcium) {
        try {
            setCalcium(calcium.floatValue());
        } catch (Exception e) {
            setCalciumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return calciumError;
    } // method setCalcium

    /**
     * Set the 'calcium' class variable
     * @param  calcium (Float)
     */
    public int setCalcium(Float calcium) {
        try {
            setCalcium(calcium.floatValue());
        } catch (Exception e) {
            setCalciumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return calciumError;
    } // method setCalcium

    /**
     * Set the 'calcium' class variable
     * @param  calcium (String)
     */
    public int setCalcium(String calcium) {
        try {
            setCalcium(new Float(calcium).floatValue());
        } catch (Exception e) {
            setCalciumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return calciumError;
    } // method setCalcium

    /**
     * Called when an exception has occured
     * @param  calcium (String)
     */
    private void setCalciumError (float calcium, Exception e, int error) {
        this.calcium = calcium;
        calciumErrorMessage = e.toString();
        calciumError = error;
    } // method setCalciumError


    /**
     * Set the 'magnesium' class variable
     * @param  magnesium (float)
     */
    public int setMagnesium(float magnesium) {
        try {
            if ( ((magnesium == FLOATNULL) || 
                  (magnesium == FLOATNULL2)) ||
                !((magnesium < MAGNESIUM_MN) ||
                  (magnesium > MAGNESIUM_MX)) ) {
                this.magnesium = magnesium;
                magnesiumError = ERROR_NORMAL;
            } else {
                throw magnesiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMagnesiumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return magnesiumError;
    } // method setMagnesium

    /**
     * Set the 'magnesium' class variable
     * @param  magnesium (Integer)
     */
    public int setMagnesium(Integer magnesium) {
        try {
            setMagnesium(magnesium.floatValue());
        } catch (Exception e) {
            setMagnesiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return magnesiumError;
    } // method setMagnesium

    /**
     * Set the 'magnesium' class variable
     * @param  magnesium (Float)
     */
    public int setMagnesium(Float magnesium) {
        try {
            setMagnesium(magnesium.floatValue());
        } catch (Exception e) {
            setMagnesiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return magnesiumError;
    } // method setMagnesium

    /**
     * Set the 'magnesium' class variable
     * @param  magnesium (String)
     */
    public int setMagnesium(String magnesium) {
        try {
            setMagnesium(new Float(magnesium).floatValue());
        } catch (Exception e) {
            setMagnesiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return magnesiumError;
    } // method setMagnesium

    /**
     * Called when an exception has occured
     * @param  magnesium (String)
     */
    private void setMagnesiumError (float magnesium, Exception e, int error) {
        this.magnesium = magnesium;
        magnesiumErrorMessage = e.toString();
        magnesiumError = error;
    } // method setMagnesiumError


    /**
     * Set the 'potassium' class variable
     * @param  potassium (float)
     */
    public int setPotassium(float potassium) {
        try {
            if ( ((potassium == FLOATNULL) || 
                  (potassium == FLOATNULL2)) ||
                !((potassium < POTASSIUM_MN) ||
                  (potassium > POTASSIUM_MX)) ) {
                this.potassium = potassium;
                potassiumError = ERROR_NORMAL;
            } else {
                throw potassiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPotassiumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return potassiumError;
    } // method setPotassium

    /**
     * Set the 'potassium' class variable
     * @param  potassium (Integer)
     */
    public int setPotassium(Integer potassium) {
        try {
            setPotassium(potassium.floatValue());
        } catch (Exception e) {
            setPotassiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return potassiumError;
    } // method setPotassium

    /**
     * Set the 'potassium' class variable
     * @param  potassium (Float)
     */
    public int setPotassium(Float potassium) {
        try {
            setPotassium(potassium.floatValue());
        } catch (Exception e) {
            setPotassiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return potassiumError;
    } // method setPotassium

    /**
     * Set the 'potassium' class variable
     * @param  potassium (String)
     */
    public int setPotassium(String potassium) {
        try {
            setPotassium(new Float(potassium).floatValue());
        } catch (Exception e) {
            setPotassiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return potassiumError;
    } // method setPotassium

    /**
     * Called when an exception has occured
     * @param  potassium (String)
     */
    private void setPotassiumError (float potassium, Exception e, int error) {
        this.potassium = potassium;
        potassiumErrorMessage = e.toString();
        potassiumError = error;
    } // method setPotassiumError


    /**
     * Set the 'sodium' class variable
     * @param  sodium (float)
     */
    public int setSodium(float sodium) {
        try {
            if ( ((sodium == FLOATNULL) || 
                  (sodium == FLOATNULL2)) ||
                !((sodium < SODIUM_MN) ||
                  (sodium > SODIUM_MX)) ) {
                this.sodium = sodium;
                sodiumError = ERROR_NORMAL;
            } else {
                throw sodiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSodiumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return sodiumError;
    } // method setSodium

    /**
     * Set the 'sodium' class variable
     * @param  sodium (Integer)
     */
    public int setSodium(Integer sodium) {
        try {
            setSodium(sodium.floatValue());
        } catch (Exception e) {
            setSodiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sodiumError;
    } // method setSodium

    /**
     * Set the 'sodium' class variable
     * @param  sodium (Float)
     */
    public int setSodium(Float sodium) {
        try {
            setSodium(sodium.floatValue());
        } catch (Exception e) {
            setSodiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sodiumError;
    } // method setSodium

    /**
     * Set the 'sodium' class variable
     * @param  sodium (String)
     */
    public int setSodium(String sodium) {
        try {
            setSodium(new Float(sodium).floatValue());
        } catch (Exception e) {
            setSodiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sodiumError;
    } // method setSodium

    /**
     * Called when an exception has occured
     * @param  sodium (String)
     */
    private void setSodiumError (float sodium, Exception e, int error) {
        this.sodium = sodium;
        sodiumErrorMessage = e.toString();
        sodiumError = error;
    } // method setSodiumError


    /**
     * Set the 'strontium' class variable
     * @param  strontium (float)
     */
    public int setStrontium(float strontium) {
        try {
            if ( ((strontium == FLOATNULL) || 
                  (strontium == FLOATNULL2)) ||
                !((strontium < STRONTIUM_MN) ||
                  (strontium > STRONTIUM_MX)) ) {
                this.strontium = strontium;
                strontiumError = ERROR_NORMAL;
            } else {
                throw strontiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStrontiumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return strontiumError;
    } // method setStrontium

    /**
     * Set the 'strontium' class variable
     * @param  strontium (Integer)
     */
    public int setStrontium(Integer strontium) {
        try {
            setStrontium(strontium.floatValue());
        } catch (Exception e) {
            setStrontiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return strontiumError;
    } // method setStrontium

    /**
     * Set the 'strontium' class variable
     * @param  strontium (Float)
     */
    public int setStrontium(Float strontium) {
        try {
            setStrontium(strontium.floatValue());
        } catch (Exception e) {
            setStrontiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return strontiumError;
    } // method setStrontium

    /**
     * Set the 'strontium' class variable
     * @param  strontium (String)
     */
    public int setStrontium(String strontium) {
        try {
            setStrontium(new Float(strontium).floatValue());
        } catch (Exception e) {
            setStrontiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return strontiumError;
    } // method setStrontium

    /**
     * Called when an exception has occured
     * @param  strontium (String)
     */
    private void setStrontiumError (float strontium, Exception e, int error) {
        this.strontium = strontium;
        strontiumErrorMessage = e.toString();
        strontiumError = error;
    } // method setStrontiumError


    /**
     * Set the 'so3' class variable
     * @param  so3 (float)
     */
    public int setSo3(float so3) {
        try {
            if ( ((so3 == FLOATNULL) || 
                  (so3 == FLOATNULL2)) ||
                !((so3 < SO3_MN) ||
                  (so3 > SO3_MX)) ) {
                this.so3 = so3;
                so3Error = ERROR_NORMAL;
            } else {
                throw so3OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSo3Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return so3Error;
    } // method setSo3

    /**
     * Set the 'so3' class variable
     * @param  so3 (Integer)
     */
    public int setSo3(Integer so3) {
        try {
            setSo3(so3.floatValue());
        } catch (Exception e) {
            setSo3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return so3Error;
    } // method setSo3

    /**
     * Set the 'so3' class variable
     * @param  so3 (Float)
     */
    public int setSo3(Float so3) {
        try {
            setSo3(so3.floatValue());
        } catch (Exception e) {
            setSo3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return so3Error;
    } // method setSo3

    /**
     * Set the 'so3' class variable
     * @param  so3 (String)
     */
    public int setSo3(String so3) {
        try {
            setSo3(new Float(so3).floatValue());
        } catch (Exception e) {
            setSo3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return so3Error;
    } // method setSo3

    /**
     * Called when an exception has occured
     * @param  so3 (String)
     */
    private void setSo3Error (float so3, Exception e, int error) {
        this.so3 = so3;
        so3ErrorMessage = e.toString();
        so3Error = error;
    } // method setSo3Error


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'sedphyCode' class variable
     * @return sedphyCode (int)
     */
    public int getSedphyCode() {
        return sedphyCode;
    } // method getSedphyCode

    /**
     * Return the 'sedphyCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSedphyCode methods
     * @return sedphyCode (String)
     */
    public String getSedphyCode(String s) {
        return ((sedphyCode != INTNULL) ? new Integer(sedphyCode).toString() : "");
    } // method getSedphyCode


    /**
     * Return the 'calcium' class variable
     * @return calcium (float)
     */
    public float getCalcium() {
        return calcium;
    } // method getCalcium

    /**
     * Return the 'calcium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCalcium methods
     * @return calcium (String)
     */
    public String getCalcium(String s) {
        return ((calcium != FLOATNULL) ? new Float(calcium).toString() : "");
    } // method getCalcium


    /**
     * Return the 'magnesium' class variable
     * @return magnesium (float)
     */
    public float getMagnesium() {
        return magnesium;
    } // method getMagnesium

    /**
     * Return the 'magnesium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMagnesium methods
     * @return magnesium (String)
     */
    public String getMagnesium(String s) {
        return ((magnesium != FLOATNULL) ? new Float(magnesium).toString() : "");
    } // method getMagnesium


    /**
     * Return the 'potassium' class variable
     * @return potassium (float)
     */
    public float getPotassium() {
        return potassium;
    } // method getPotassium

    /**
     * Return the 'potassium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPotassium methods
     * @return potassium (String)
     */
    public String getPotassium(String s) {
        return ((potassium != FLOATNULL) ? new Float(potassium).toString() : "");
    } // method getPotassium


    /**
     * Return the 'sodium' class variable
     * @return sodium (float)
     */
    public float getSodium() {
        return sodium;
    } // method getSodium

    /**
     * Return the 'sodium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSodium methods
     * @return sodium (String)
     */
    public String getSodium(String s) {
        return ((sodium != FLOATNULL) ? new Float(sodium).toString() : "");
    } // method getSodium


    /**
     * Return the 'strontium' class variable
     * @return strontium (float)
     */
    public float getStrontium() {
        return strontium;
    } // method getStrontium

    /**
     * Return the 'strontium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStrontium methods
     * @return strontium (String)
     */
    public String getStrontium(String s) {
        return ((strontium != FLOATNULL) ? new Float(strontium).toString() : "");
    } // method getStrontium


    /**
     * Return the 'so3' class variable
     * @return so3 (float)
     */
    public float getSo3() {
        return so3;
    } // method getSo3

    /**
     * Return the 'so3' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSo3 methods
     * @return so3 (String)
     */
    public String getSo3(String s) {
        return ((so3 != FLOATNULL) ? new Float(so3).toString() : "");
    } // method getSo3


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
        if ((sedphyCode == INTNULL) &&
            (calcium == FLOATNULL) &&
            (magnesium == FLOATNULL) &&
            (potassium == FLOATNULL) &&
            (sodium == FLOATNULL) &&
            (strontium == FLOATNULL) &&
            (so3 == FLOATNULL)) {
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
        int sumError = sedphyCodeError +
            calciumError +
            magnesiumError +
            potassiumError +
            sodiumError +
            strontiumError +
            so3Error;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the sedphyCode instance variable
     * @return errorcode (int)
     */
    public int getSedphyCodeError() {
        return sedphyCodeError;
    } // method getSedphyCodeError

    /**
     * Gets the errorMessage for the sedphyCode instance variable
     * @return errorMessage (String)
     */
    public String getSedphyCodeErrorMessage() {
        return sedphyCodeErrorMessage;
    } // method getSedphyCodeErrorMessage


    /**
     * Gets the errorcode for the calcium instance variable
     * @return errorcode (int)
     */
    public int getCalciumError() {
        return calciumError;
    } // method getCalciumError

    /**
     * Gets the errorMessage for the calcium instance variable
     * @return errorMessage (String)
     */
    public String getCalciumErrorMessage() {
        return calciumErrorMessage;
    } // method getCalciumErrorMessage


    /**
     * Gets the errorcode for the magnesium instance variable
     * @return errorcode (int)
     */
    public int getMagnesiumError() {
        return magnesiumError;
    } // method getMagnesiumError

    /**
     * Gets the errorMessage for the magnesium instance variable
     * @return errorMessage (String)
     */
    public String getMagnesiumErrorMessage() {
        return magnesiumErrorMessage;
    } // method getMagnesiumErrorMessage


    /**
     * Gets the errorcode for the potassium instance variable
     * @return errorcode (int)
     */
    public int getPotassiumError() {
        return potassiumError;
    } // method getPotassiumError

    /**
     * Gets the errorMessage for the potassium instance variable
     * @return errorMessage (String)
     */
    public String getPotassiumErrorMessage() {
        return potassiumErrorMessage;
    } // method getPotassiumErrorMessage


    /**
     * Gets the errorcode for the sodium instance variable
     * @return errorcode (int)
     */
    public int getSodiumError() {
        return sodiumError;
    } // method getSodiumError

    /**
     * Gets the errorMessage for the sodium instance variable
     * @return errorMessage (String)
     */
    public String getSodiumErrorMessage() {
        return sodiumErrorMessage;
    } // method getSodiumErrorMessage


    /**
     * Gets the errorcode for the strontium instance variable
     * @return errorcode (int)
     */
    public int getStrontiumError() {
        return strontiumError;
    } // method getStrontiumError

    /**
     * Gets the errorMessage for the strontium instance variable
     * @return errorMessage (String)
     */
    public String getStrontiumErrorMessage() {
        return strontiumErrorMessage;
    } // method getStrontiumErrorMessage


    /**
     * Gets the errorcode for the so3 instance variable
     * @return errorcode (int)
     */
    public int getSo3Error() {
        return so3Error;
    } // method getSo3Error

    /**
     * Gets the errorMessage for the so3 instance variable
     * @return errorMessage (String)
     */
    public String getSo3ErrorMessage() {
        return so3ErrorMessage;
    } // method getSo3ErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnSedchem2. e.g.<pre>
     * MrnSedchem2 sedchem2 = new MrnSedchem2(<val1>);
     * MrnSedchem2 sedchem2Array[] = sedchem2.get();</pre>
     * will get the MrnSedchem2 record where sedphyCode = <val1>.
     * @return Array of MrnSedchem2 (MrnSedchem2[])
     */
    public MrnSedchem2[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnSedchem2 sedchem2Array[] = 
     *     new MrnSedchem2().get(MrnSedchem2.SEDPHY_CODE+"=<val1>");</pre>
     * will get the MrnSedchem2 record where sedphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnSedchem2 (MrnSedchem2[])
     */
    public MrnSedchem2[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnSedchem2 sedchem2Array[] = 
     *     new MrnSedchem2().get("1=1",sedchem2.SEDPHY_CODE);</pre>
     * will get the all the MrnSedchem2 records, and order them by sedphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSedchem2 (MrnSedchem2[])
     */
    public MrnSedchem2[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnSedchem2.SEDPHY_CODE,MrnSedchem2.CALCIUM;
     * String where = MrnSedchem2.SEDPHY_CODE + "=<val1";
     * String order = MrnSedchem2.SEDPHY_CODE;
     * MrnSedchem2 sedchem2Array[] = 
     *     new MrnSedchem2().get(columns, where, order);</pre>
     * will get the sedphyCode and calcium colums of all MrnSedchem2 records,
     * where sedphyCode = <val1>, and order them by sedphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSedchem2 (MrnSedchem2[])
     */
    public MrnSedchem2[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnSedchem2.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnSedchem2[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int sedphyCodeCol = db.getColNumber(SEDPHY_CODE);
        int calciumCol    = db.getColNumber(CALCIUM);
        int magnesiumCol  = db.getColNumber(MAGNESIUM);
        int potassiumCol  = db.getColNumber(POTASSIUM);
        int sodiumCol     = db.getColNumber(SODIUM);
        int strontiumCol  = db.getColNumber(STRONTIUM);
        int so3Col        = db.getColNumber(SO3);
        MrnSedchem2[] cArray = new MrnSedchem2[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnSedchem2();
            if (sedphyCodeCol != -1)
                cArray[i].setSedphyCode((String) row.elementAt(sedphyCodeCol));
            if (calciumCol != -1)
                cArray[i].setCalcium   ((String) row.elementAt(calciumCol));
            if (magnesiumCol != -1)
                cArray[i].setMagnesium ((String) row.elementAt(magnesiumCol));
            if (potassiumCol != -1)
                cArray[i].setPotassium ((String) row.elementAt(potassiumCol));
            if (sodiumCol != -1)
                cArray[i].setSodium    ((String) row.elementAt(sodiumCol));
            if (strontiumCol != -1)
                cArray[i].setStrontium ((String) row.elementAt(strontiumCol));
            if (so3Col != -1)
                cArray[i].setSo3       ((String) row.elementAt(so3Col));
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
     *     new MrnSedchem2(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>).put();</pre>
     * will insert a record with:
     *     sedphyCode = <val1>,
     *     calcium    = <val2>,
     *     magnesium  = <val3>,
     *     potassium  = <val4>,
     *     sodium     = <val5>,
     *     strontium  = <val6>,
     *     so3        = <val7>.
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
     * Boolean success = new MrnSedchem2(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where calcium = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSedchem2 sedchem2 = new MrnSedchem2();
     * success = sedchem2.del(MrnSedchem2.SEDPHY_CODE+"=<val1>");</pre>
     * will delete all records where sedphyCode = <val1>.
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
     * update are taken from the MrnSedchem2 argument, .e.g.<pre>
     * Boolean success
     * MrnSedchem2 updMrnSedchem2 = new MrnSedchem2();
     * updMrnSedchem2.setCalcium(<val2>);
     * MrnSedchem2 whereMrnSedchem2 = new MrnSedchem2(<val1>);
     * success = whereMrnSedchem2.upd(updMrnSedchem2);</pre>
     * will update Calcium to <val2> for all records where 
     * sedphyCode = <val1>.
     * @param sedchem2  A MrnSedchem2 variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSedchem2 sedchem2) {
        return db.update (TABLE, createColVals(sedchem2), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSedchem2 updMrnSedchem2 = new MrnSedchem2();
     * updMrnSedchem2.setCalcium(<val2>);
     * MrnSedchem2 whereMrnSedchem2 = new MrnSedchem2();
     * success = whereMrnSedchem2.upd(
     *     updMrnSedchem2, MrnSedchem2.SEDPHY_CODE+"=<val1>");</pre>
     * will update Calcium to <val2> for all records where 
     * sedphyCode = <val1>.
     * @param  updMrnSedchem2  A MrnSedchem2 variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSedchem2 sedchem2, String where) {
        return db.update (TABLE, createColVals(sedchem2), where);
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
        if (getSedphyCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEDPHY_CODE + "=" + getSedphyCode("");
        } // if getSedphyCode
        if (getCalcium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CALCIUM + "=" + getCalcium("");
        } // if getCalcium
        if (getMagnesium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MAGNESIUM + "=" + getMagnesium("");
        } // if getMagnesium
        if (getPotassium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + POTASSIUM + "=" + getPotassium("");
        } // if getPotassium
        if (getSodium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SODIUM + "=" + getSodium("");
        } // if getSodium
        if (getStrontium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STRONTIUM + "=" + getStrontium("");
        } // if getStrontium
        if (getSo3() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SO3 + "=" + getSo3("");
        } // if getSo3
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnSedchem2 aVar) {
        String colVals = "";
        if (aVar.getSedphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPHY_CODE +"=";
            colVals += (aVar.getSedphyCode() == INTNULL2 ?
                "null" : aVar.getSedphyCode(""));
        } // if aVar.getSedphyCode
        if (aVar.getCalcium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CALCIUM +"=";
            colVals += (aVar.getCalcium() == FLOATNULL2 ?
                "null" : aVar.getCalcium(""));
        } // if aVar.getCalcium
        if (aVar.getMagnesium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MAGNESIUM +"=";
            colVals += (aVar.getMagnesium() == FLOATNULL2 ?
                "null" : aVar.getMagnesium(""));
        } // if aVar.getMagnesium
        if (aVar.getPotassium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += POTASSIUM +"=";
            colVals += (aVar.getPotassium() == FLOATNULL2 ?
                "null" : aVar.getPotassium(""));
        } // if aVar.getPotassium
        if (aVar.getSodium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SODIUM +"=";
            colVals += (aVar.getSodium() == FLOATNULL2 ?
                "null" : aVar.getSodium(""));
        } // if aVar.getSodium
        if (aVar.getStrontium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STRONTIUM +"=";
            colVals += (aVar.getStrontium() == FLOATNULL2 ?
                "null" : aVar.getStrontium(""));
        } // if aVar.getStrontium
        if (aVar.getSo3() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SO3 +"=";
            colVals += (aVar.getSo3() == FLOATNULL2 ?
                "null" : aVar.getSo3(""));
        } // if aVar.getSo3
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SEDPHY_CODE;
        if (getCalcium() != FLOATNULL) {
            columns = columns + "," + CALCIUM;
        } // if getCalcium
        if (getMagnesium() != FLOATNULL) {
            columns = columns + "," + MAGNESIUM;
        } // if getMagnesium
        if (getPotassium() != FLOATNULL) {
            columns = columns + "," + POTASSIUM;
        } // if getPotassium
        if (getSodium() != FLOATNULL) {
            columns = columns + "," + SODIUM;
        } // if getSodium
        if (getStrontium() != FLOATNULL) {
            columns = columns + "," + STRONTIUM;
        } // if getStrontium
        if (getSo3() != FLOATNULL) {
            columns = columns + "," + SO3;
        } // if getSo3
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getSedphyCode("");
        if (getCalcium() != FLOATNULL) {
            values  = values  + "," + getCalcium("");
        } // if getCalcium
        if (getMagnesium() != FLOATNULL) {
            values  = values  + "," + getMagnesium("");
        } // if getMagnesium
        if (getPotassium() != FLOATNULL) {
            values  = values  + "," + getPotassium("");
        } // if getPotassium
        if (getSodium() != FLOATNULL) {
            values  = values  + "," + getSodium("");
        } // if getSodium
        if (getStrontium() != FLOATNULL) {
            values  = values  + "," + getStrontium("");
        } // if getStrontium
        if (getSo3() != FLOATNULL) {
            values  = values  + "," + getSo3("");
        } // if getSo3
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getSedphyCode("") + "|" +
            getCalcium("")    + "|" +
            getMagnesium("")  + "|" +
            getPotassium("")  + "|" +
            getSodium("")     + "|" +
            getStrontium("")  + "|" +
            getSo3("")        + "|";
    } // method toString

} // class MrnSedchem2
