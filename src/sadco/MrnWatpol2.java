package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WATPOL2 table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnWatpol2 extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WATPOL2";
    /** The watphyCode field name */
    public static final String WATPHY_CODE = "WATPHY_CODE";
    /** The aluminium field name */
    public static final String ALUMINIUM = "ALUMINIUM";
    /** The antimony field name */
    public static final String ANTIMONY = "ANTIMONY";
    /** The bismuth field name */
    public static final String BISMUTH = "BISMUTH";
    /** The molybdenum field name */
    public static final String MOLYBDENUM = "MOLYBDENUM";
    /** The silver field name */
    public static final String SILVER = "SILVER";
    /** The titanium field name */
    public static final String TITANIUM = "TITANIUM";
    /** The vanadium field name */
    public static final String VANADIUM = "VANADIUM";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       watphyCode;
    private int       aluminium;
    private float     antimony;
    private float     bismuth;
    private float     molybdenum;
    private float     silver;
    private int       titanium;
    private float     vanadium;

    /** The error variables  */
    private int watphyCodeError = ERROR_NORMAL;
    private int aluminiumError  = ERROR_NORMAL;
    private int antimonyError   = ERROR_NORMAL;
    private int bismuthError    = ERROR_NORMAL;
    private int molybdenumError = ERROR_NORMAL;
    private int silverError     = ERROR_NORMAL;
    private int titaniumError   = ERROR_NORMAL;
    private int vanadiumError   = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String watphyCodeErrorMessage = "";
    private String aluminiumErrorMessage  = "";
    private String antimonyErrorMessage   = "";
    private String bismuthErrorMessage    = "";
    private String molybdenumErrorMessage = "";
    private String silverErrorMessage     = "";
    private String titaniumErrorMessage   = "";
    private String vanadiumErrorMessage   = "";

    /** The min-max constants for all numerics */
    public static final int       WATPHY_CODE_MN = INTMIN;
    public static final int       WATPHY_CODE_MX = INTMAX;
    public static final int       ALUMINIUM_MN = INTMIN;
    public static final int       ALUMINIUM_MX = INTMAX;
    public static final float     ANTIMONY_MN = FLOATMIN;
    public static final float     ANTIMONY_MX = FLOATMAX;
    public static final float     BISMUTH_MN = FLOATMIN;
    public static final float     BISMUTH_MX = FLOATMAX;
    public static final float     MOLYBDENUM_MN = FLOATMIN;
    public static final float     MOLYBDENUM_MX = FLOATMAX;
    public static final float     SILVER_MN = FLOATMIN;
    public static final float     SILVER_MX = FLOATMAX;
    public static final int       TITANIUM_MN = INTMIN;
    public static final int       TITANIUM_MX = INTMAX;
    public static final float     VANADIUM_MN = FLOATMIN;
    public static final float     VANADIUM_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception watphyCodeOutOfBoundsException =
        new Exception ("'watphyCode' out of bounds: " +
            WATPHY_CODE_MN + " - " + WATPHY_CODE_MX);
    Exception aluminiumOutOfBoundsException =
        new Exception ("'aluminium' out of bounds: " +
            ALUMINIUM_MN + " - " + ALUMINIUM_MX);
    Exception antimonyOutOfBoundsException =
        new Exception ("'antimony' out of bounds: " +
            ANTIMONY_MN + " - " + ANTIMONY_MX);
    Exception bismuthOutOfBoundsException =
        new Exception ("'bismuth' out of bounds: " +
            BISMUTH_MN + " - " + BISMUTH_MX);
    Exception molybdenumOutOfBoundsException =
        new Exception ("'molybdenum' out of bounds: " +
            MOLYBDENUM_MN + " - " + MOLYBDENUM_MX);
    Exception silverOutOfBoundsException =
        new Exception ("'silver' out of bounds: " +
            SILVER_MN + " - " + SILVER_MX);
    Exception titaniumOutOfBoundsException =
        new Exception ("'titanium' out of bounds: " +
            TITANIUM_MN + " - " + TITANIUM_MX);
    Exception vanadiumOutOfBoundsException =
        new Exception ("'vanadium' out of bounds: " +
            VANADIUM_MN + " - " + VANADIUM_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnWatpol2() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnWatpol2 constructor 1"); // debug
    } // MrnWatpol2 constructor

    /**
     * Instantiate a MrnWatpol2 object and initialize the instance variables.
     * @param watphyCode  The watphyCode (int)
     */
    public MrnWatpol2(
            int watphyCode) {
        this();
        setWatphyCode (watphyCode);
        if (dbg) System.out.println ("<br>in MrnWatpol2 constructor 2"); // debug
    } // MrnWatpol2 constructor

    /**
     * Instantiate a MrnWatpol2 object and initialize the instance variables.
     * @param watphyCode  The watphyCode (int)
     * @param aluminium   The aluminium  (int)
     * @param antimony    The antimony   (float)
     * @param bismuth     The bismuth    (float)
     * @param molybdenum  The molybdenum (float)
     * @param silver      The silver     (float)
     * @param titanium    The titanium   (int)
     * @param vanadium    The vanadium   (float)
     */
    public MrnWatpol2(
            int   watphyCode,
            int   aluminium,
            float antimony,
            float bismuth,
            float molybdenum,
            float silver,
            int   titanium,
            float vanadium) {
        this();
        setWatphyCode (watphyCode);
        setAluminium  (aluminium );
        setAntimony   (antimony  );
        setBismuth    (bismuth   );
        setMolybdenum (molybdenum);
        setSilver     (silver    );
        setTitanium   (titanium  );
        setVanadium   (vanadium  );
        if (dbg) System.out.println ("<br>in MrnWatpol2 constructor 3"); // debug
    } // MrnWatpol2 constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setWatphyCode (INTNULL  );
        setAluminium  (INTNULL  );
        setAntimony   (FLOATNULL);
        setBismuth    (FLOATNULL);
        setMolybdenum (FLOATNULL);
        setSilver     (FLOATNULL);
        setTitanium   (INTNULL  );
        setVanadium   (FLOATNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'watphyCode' class variable
     * @param  watphyCode (int)
     */
    public int setWatphyCode(int watphyCode) {
        try {
            if ( ((watphyCode == INTNULL) ||
                  (watphyCode == INTNULL2)) ||
                !((watphyCode < WATPHY_CODE_MN) ||
                  (watphyCode > WATPHY_CODE_MX)) ) {
                this.watphyCode = watphyCode;
                watphyCodeError = ERROR_NORMAL;
            } else {
                throw watphyCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWatphyCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return watphyCodeError;
    } // method setWatphyCode

    /**
     * Set the 'watphyCode' class variable
     * @param  watphyCode (Integer)
     */
    public int setWatphyCode(Integer watphyCode) {
        try {
            setWatphyCode(watphyCode.intValue());
        } catch (Exception e) {
            setWatphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCodeError;
    } // method setWatphyCode

    /**
     * Set the 'watphyCode' class variable
     * @param  watphyCode (Float)
     */
    public int setWatphyCode(Float watphyCode) {
        try {
            if (watphyCode.floatValue() == FLOATNULL) {
                setWatphyCode(INTNULL);
            } else {
                setWatphyCode(watphyCode.intValue());
            } // if (watphyCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWatphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCodeError;
    } // method setWatphyCode

    /**
     * Set the 'watphyCode' class variable
     * @param  watphyCode (String)
     */
    public int setWatphyCode(String watphyCode) {
        try {
            setWatphyCode(new Integer(watphyCode).intValue());
        } catch (Exception e) {
            setWatphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return watphyCodeError;
    } // method setWatphyCode

    /**
     * Called when an exception has occured
     * @param  watphyCode (String)
     */
    private void setWatphyCodeError (int watphyCode, Exception e, int error) {
        this.watphyCode = watphyCode;
        watphyCodeErrorMessage = e.toString();
        watphyCodeError = error;
    } // method setWatphyCodeError


    /**
     * Set the 'aluminium' class variable
     * @param  aluminium (int)
     */
    public int setAluminium(int aluminium) {
        try {
            if ( ((aluminium == INTNULL) ||
                  (aluminium == INTNULL2)) ||
                !((aluminium < ALUMINIUM_MN) ||
                  (aluminium > ALUMINIUM_MX)) ) {
                this.aluminium = aluminium;
                aluminiumError = ERROR_NORMAL;
            } else {
                throw aluminiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAluminiumError(INTNULL, e, ERROR_LOCAL);
        } // try
        return aluminiumError;
    } // method setAluminium

    /**
     * Set the 'aluminium' class variable
     * @param  aluminium (Integer)
     */
    public int setAluminium(Integer aluminium) {
        try {
            setAluminium(aluminium.intValue());
        } catch (Exception e) {
            setAluminiumError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aluminiumError;
    } // method setAluminium

    /**
     * Set the 'aluminium' class variable
     * @param  aluminium (Float)
     */
    public int setAluminium(Float aluminium) {
        try {
            if (aluminium.floatValue() == FLOATNULL) {
                setAluminium(INTNULL);
            } else {
                setAluminium(aluminium.intValue());
            } // if (aluminium.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setAluminiumError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aluminiumError;
    } // method setAluminium

    /**
     * Set the 'aluminium' class variable
     * @param  aluminium (String)
     */
    public int setAluminium(String aluminium) {
        try {
            setAluminium(new Integer(aluminium).intValue());
        } catch (Exception e) {
            setAluminiumError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aluminiumError;
    } // method setAluminium

    /**
     * Called when an exception has occured
     * @param  aluminium (String)
     */
    private void setAluminiumError (int aluminium, Exception e, int error) {
        this.aluminium = aluminium;
        aluminiumErrorMessage = e.toString();
        aluminiumError = error;
    } // method setAluminiumError


    /**
     * Set the 'antimony' class variable
     * @param  antimony (float)
     */
    public int setAntimony(float antimony) {
        try {
            if ( ((antimony == FLOATNULL) ||
                  (antimony == FLOATNULL2)) ||
                !((antimony < ANTIMONY_MN) ||
                  (antimony > ANTIMONY_MX)) ) {
                this.antimony = antimony;
                antimonyError = ERROR_NORMAL;
            } else {
                throw antimonyOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAntimonyError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return antimonyError;
    } // method setAntimony

    /**
     * Set the 'antimony' class variable
     * @param  antimony (Integer)
     */
    public int setAntimony(Integer antimony) {
        try {
            setAntimony(antimony.floatValue());
        } catch (Exception e) {
            setAntimonyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return antimonyError;
    } // method setAntimony

    /**
     * Set the 'antimony' class variable
     * @param  antimony (Float)
     */
    public int setAntimony(Float antimony) {
        try {
            setAntimony(antimony.floatValue());
        } catch (Exception e) {
            setAntimonyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return antimonyError;
    } // method setAntimony

    /**
     * Set the 'antimony' class variable
     * @param  antimony (String)
     */
    public int setAntimony(String antimony) {
        try {
            setAntimony(new Float(antimony).floatValue());
        } catch (Exception e) {
            setAntimonyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return antimonyError;
    } // method setAntimony

    /**
     * Called when an exception has occured
     * @param  antimony (String)
     */
    private void setAntimonyError (float antimony, Exception e, int error) {
        this.antimony = antimony;
        antimonyErrorMessage = e.toString();
        antimonyError = error;
    } // method setAntimonyError


    /**
     * Set the 'bismuth' class variable
     * @param  bismuth (float)
     */
    public int setBismuth(float bismuth) {
        try {
            if ( ((bismuth == FLOATNULL) ||
                  (bismuth == FLOATNULL2)) ||
                !((bismuth < BISMUTH_MN) ||
                  (bismuth > BISMUTH_MX)) ) {
                this.bismuth = bismuth;
                bismuthError = ERROR_NORMAL;
            } else {
                throw bismuthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setBismuthError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return bismuthError;
    } // method setBismuth

    /**
     * Set the 'bismuth' class variable
     * @param  bismuth (Integer)
     */
    public int setBismuth(Integer bismuth) {
        try {
            setBismuth(bismuth.floatValue());
        } catch (Exception e) {
            setBismuthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return bismuthError;
    } // method setBismuth

    /**
     * Set the 'bismuth' class variable
     * @param  bismuth (Float)
     */
    public int setBismuth(Float bismuth) {
        try {
            setBismuth(bismuth.floatValue());
        } catch (Exception e) {
            setBismuthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return bismuthError;
    } // method setBismuth

    /**
     * Set the 'bismuth' class variable
     * @param  bismuth (String)
     */
    public int setBismuth(String bismuth) {
        try {
            setBismuth(new Float(bismuth).floatValue());
        } catch (Exception e) {
            setBismuthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return bismuthError;
    } // method setBismuth

    /**
     * Called when an exception has occured
     * @param  bismuth (String)
     */
    private void setBismuthError (float bismuth, Exception e, int error) {
        this.bismuth = bismuth;
        bismuthErrorMessage = e.toString();
        bismuthError = error;
    } // method setBismuthError


    /**
     * Set the 'molybdenum' class variable
     * @param  molybdenum (float)
     */
    public int setMolybdenum(float molybdenum) {
        try {
            if ( ((molybdenum == FLOATNULL) ||
                  (molybdenum == FLOATNULL2)) ||
                !((molybdenum < MOLYBDENUM_MN) ||
                  (molybdenum > MOLYBDENUM_MX)) ) {
                this.molybdenum = molybdenum;
                molybdenumError = ERROR_NORMAL;
            } else {
                throw molybdenumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMolybdenumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return molybdenumError;
    } // method setMolybdenum

    /**
     * Set the 'molybdenum' class variable
     * @param  molybdenum (Integer)
     */
    public int setMolybdenum(Integer molybdenum) {
        try {
            setMolybdenum(molybdenum.floatValue());
        } catch (Exception e) {
            setMolybdenumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return molybdenumError;
    } // method setMolybdenum

    /**
     * Set the 'molybdenum' class variable
     * @param  molybdenum (Float)
     */
    public int setMolybdenum(Float molybdenum) {
        try {
            setMolybdenum(molybdenum.floatValue());
        } catch (Exception e) {
            setMolybdenumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return molybdenumError;
    } // method setMolybdenum

    /**
     * Set the 'molybdenum' class variable
     * @param  molybdenum (String)
     */
    public int setMolybdenum(String molybdenum) {
        try {
            setMolybdenum(new Float(molybdenum).floatValue());
        } catch (Exception e) {
            setMolybdenumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return molybdenumError;
    } // method setMolybdenum

    /**
     * Called when an exception has occured
     * @param  molybdenum (String)
     */
    private void setMolybdenumError (float molybdenum, Exception e, int error) {
        this.molybdenum = molybdenum;
        molybdenumErrorMessage = e.toString();
        molybdenumError = error;
    } // method setMolybdenumError


    /**
     * Set the 'silver' class variable
     * @param  silver (float)
     */
    public int setSilver(float silver) {
        try {
            if ( ((silver == FLOATNULL) ||
                  (silver == FLOATNULL2)) ||
                !((silver < SILVER_MN) ||
                  (silver > SILVER_MX)) ) {
                this.silver = silver;
                silverError = ERROR_NORMAL;
            } else {
                throw silverOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSilverError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return silverError;
    } // method setSilver

    /**
     * Set the 'silver' class variable
     * @param  silver (Integer)
     */
    public int setSilver(Integer silver) {
        try {
            setSilver(silver.floatValue());
        } catch (Exception e) {
            setSilverError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return silverError;
    } // method setSilver

    /**
     * Set the 'silver' class variable
     * @param  silver (Float)
     */
    public int setSilver(Float silver) {
        try {
            setSilver(silver.floatValue());
        } catch (Exception e) {
            setSilverError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return silverError;
    } // method setSilver

    /**
     * Set the 'silver' class variable
     * @param  silver (String)
     */
    public int setSilver(String silver) {
        try {
            setSilver(new Float(silver).floatValue());
        } catch (Exception e) {
            setSilverError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return silverError;
    } // method setSilver

    /**
     * Called when an exception has occured
     * @param  silver (String)
     */
    private void setSilverError (float silver, Exception e, int error) {
        this.silver = silver;
        silverErrorMessage = e.toString();
        silverError = error;
    } // method setSilverError


    /**
     * Set the 'titanium' class variable
     * @param  titanium (int)
     */
    public int setTitanium(int titanium) {
        try {
            if ( ((titanium == INTNULL) ||
                  (titanium == INTNULL2)) ||
                !((titanium < TITANIUM_MN) ||
                  (titanium > TITANIUM_MX)) ) {
                this.titanium = titanium;
                titaniumError = ERROR_NORMAL;
            } else {
                throw titaniumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTitaniumError(INTNULL, e, ERROR_LOCAL);
        } // try
        return titaniumError;
    } // method setTitanium

    /**
     * Set the 'titanium' class variable
     * @param  titanium (Integer)
     */
    public int setTitanium(Integer titanium) {
        try {
            setTitanium(titanium.intValue());
        } catch (Exception e) {
            setTitaniumError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return titaniumError;
    } // method setTitanium

    /**
     * Set the 'titanium' class variable
     * @param  titanium (Float)
     */
    public int setTitanium(Float titanium) {
        try {
            if (titanium.floatValue() == FLOATNULL) {
                setTitanium(INTNULL);
            } else {
                setTitanium(titanium.intValue());
            } // if (titanium.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTitaniumError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return titaniumError;
    } // method setTitanium

    /**
     * Set the 'titanium' class variable
     * @param  titanium (String)
     */
    public int setTitanium(String titanium) {
        try {
            setTitanium(new Integer(titanium).intValue());
        } catch (Exception e) {
            setTitaniumError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return titaniumError;
    } // method setTitanium

    /**
     * Called when an exception has occured
     * @param  titanium (String)
     */
    private void setTitaniumError (int titanium, Exception e, int error) {
        this.titanium = titanium;
        titaniumErrorMessage = e.toString();
        titaniumError = error;
    } // method setTitaniumError


    /**
     * Set the 'vanadium' class variable
     * @param  vanadium (float)
     */
    public int setVanadium(float vanadium) {
        try {
            if ( ((vanadium == FLOATNULL) ||
                  (vanadium == FLOATNULL2)) ||
                !((vanadium < VANADIUM_MN) ||
                  (vanadium > VANADIUM_MX)) ) {
                this.vanadium = vanadium;
                vanadiumError = ERROR_NORMAL;
            } else {
                throw vanadiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setVanadiumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return vanadiumError;
    } // method setVanadium

    /**
     * Set the 'vanadium' class variable
     * @param  vanadium (Integer)
     */
    public int setVanadium(Integer vanadium) {
        try {
            setVanadium(vanadium.floatValue());
        } catch (Exception e) {
            setVanadiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return vanadiumError;
    } // method setVanadium

    /**
     * Set the 'vanadium' class variable
     * @param  vanadium (Float)
     */
    public int setVanadium(Float vanadium) {
        try {
            setVanadium(vanadium.floatValue());
        } catch (Exception e) {
            setVanadiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return vanadiumError;
    } // method setVanadium

    /**
     * Set the 'vanadium' class variable
     * @param  vanadium (String)
     */
    public int setVanadium(String vanadium) {
        try {
            setVanadium(new Float(vanadium).floatValue());
        } catch (Exception e) {
            setVanadiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return vanadiumError;
    } // method setVanadium

    /**
     * Called when an exception has occured
     * @param  vanadium (String)
     */
    private void setVanadiumError (float vanadium, Exception e, int error) {
        this.vanadium = vanadium;
        vanadiumErrorMessage = e.toString();
        vanadiumError = error;
    } // method setVanadiumError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'watphyCode' class variable
     * @return watphyCode (int)
     */
    public int getWatphyCode() {
        return watphyCode;
    } // method getWatphyCode

    /**
     * Return the 'watphyCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWatphyCode methods
     * @return watphyCode (String)
     */
    public String getWatphyCode(String s) {
        return ((watphyCode != INTNULL) ? new Integer(watphyCode).toString() : "");
    } // method getWatphyCode


    /**
     * Return the 'aluminium' class variable
     * @return aluminium (int)
     */
    public int getAluminium() {
        return aluminium;
    } // method getAluminium

    /**
     * Return the 'aluminium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAluminium methods
     * @return aluminium (String)
     */
    public String getAluminium(String s) {
        return ((aluminium != INTNULL) ? new Integer(aluminium).toString() : "");
    } // method getAluminium


    /**
     * Return the 'antimony' class variable
     * @return antimony (float)
     */
    public float getAntimony() {
        return antimony;
    } // method getAntimony

    /**
     * Return the 'antimony' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAntimony methods
     * @return antimony (String)
     */
    public String getAntimony(String s) {
        return ((antimony != FLOATNULL) ? new Float(antimony).toString() : "");
    } // method getAntimony


    /**
     * Return the 'bismuth' class variable
     * @return bismuth (float)
     */
    public float getBismuth() {
        return bismuth;
    } // method getBismuth

    /**
     * Return the 'bismuth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getBismuth methods
     * @return bismuth (String)
     */
    public String getBismuth(String s) {
        return ((bismuth != FLOATNULL) ? new Float(bismuth).toString() : "");
    } // method getBismuth


    /**
     * Return the 'molybdenum' class variable
     * @return molybdenum (float)
     */
    public float getMolybdenum() {
        return molybdenum;
    } // method getMolybdenum

    /**
     * Return the 'molybdenum' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMolybdenum methods
     * @return molybdenum (String)
     */
    public String getMolybdenum(String s) {
        return ((molybdenum != FLOATNULL) ? new Float(molybdenum).toString() : "");
    } // method getMolybdenum


    /**
     * Return the 'silver' class variable
     * @return silver (float)
     */
    public float getSilver() {
        return silver;
    } // method getSilver

    /**
     * Return the 'silver' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSilver methods
     * @return silver (String)
     */
    public String getSilver(String s) {
        return ((silver != FLOATNULL) ? new Float(silver).toString() : "");
    } // method getSilver


    /**
     * Return the 'titanium' class variable
     * @return titanium (int)
     */
    public int getTitanium() {
        return titanium;
    } // method getTitanium

    /**
     * Return the 'titanium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTitanium methods
     * @return titanium (String)
     */
    public String getTitanium(String s) {
        return ((titanium != INTNULL) ? new Integer(titanium).toString() : "");
    } // method getTitanium


    /**
     * Return the 'vanadium' class variable
     * @return vanadium (float)
     */
    public float getVanadium() {
        return vanadium;
    } // method getVanadium

    /**
     * Return the 'vanadium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getVanadium methods
     * @return vanadium (String)
     */
    public String getVanadium(String s) {
        return ((vanadium != FLOATNULL) ? new Float(vanadium).toString() : "");
    } // method getVanadium


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
        if ((watphyCode == INTNULL) &&
            (aluminium == INTNULL) &&
            (antimony == FLOATNULL) &&
            (bismuth == FLOATNULL) &&
            (molybdenum == FLOATNULL) &&
            (silver == FLOATNULL) &&
            (titanium == INTNULL) &&
            (vanadium == FLOATNULL)) {
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
        int sumError = watphyCodeError +
            aluminiumError +
            antimonyError +
            bismuthError +
            molybdenumError +
            silverError +
            titaniumError +
            vanadiumError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the watphyCode instance variable
     * @return errorcode (int)
     */
    public int getWatphyCodeError() {
        return watphyCodeError;
    } // method getWatphyCodeError

    /**
     * Gets the errorMessage for the watphyCode instance variable
     * @return errorMessage (String)
     */
    public String getWatphyCodeErrorMessage() {
        return watphyCodeErrorMessage;
    } // method getWatphyCodeErrorMessage


    /**
     * Gets the errorcode for the aluminium instance variable
     * @return errorcode (int)
     */
    public int getAluminiumError() {
        return aluminiumError;
    } // method getAluminiumError

    /**
     * Gets the errorMessage for the aluminium instance variable
     * @return errorMessage (String)
     */
    public String getAluminiumErrorMessage() {
        return aluminiumErrorMessage;
    } // method getAluminiumErrorMessage


    /**
     * Gets the errorcode for the antimony instance variable
     * @return errorcode (int)
     */
    public int getAntimonyError() {
        return antimonyError;
    } // method getAntimonyError

    /**
     * Gets the errorMessage for the antimony instance variable
     * @return errorMessage (String)
     */
    public String getAntimonyErrorMessage() {
        return antimonyErrorMessage;
    } // method getAntimonyErrorMessage


    /**
     * Gets the errorcode for the bismuth instance variable
     * @return errorcode (int)
     */
    public int getBismuthError() {
        return bismuthError;
    } // method getBismuthError

    /**
     * Gets the errorMessage for the bismuth instance variable
     * @return errorMessage (String)
     */
    public String getBismuthErrorMessage() {
        return bismuthErrorMessage;
    } // method getBismuthErrorMessage


    /**
     * Gets the errorcode for the molybdenum instance variable
     * @return errorcode (int)
     */
    public int getMolybdenumError() {
        return molybdenumError;
    } // method getMolybdenumError

    /**
     * Gets the errorMessage for the molybdenum instance variable
     * @return errorMessage (String)
     */
    public String getMolybdenumErrorMessage() {
        return molybdenumErrorMessage;
    } // method getMolybdenumErrorMessage


    /**
     * Gets the errorcode for the silver instance variable
     * @return errorcode (int)
     */
    public int getSilverError() {
        return silverError;
    } // method getSilverError

    /**
     * Gets the errorMessage for the silver instance variable
     * @return errorMessage (String)
     */
    public String getSilverErrorMessage() {
        return silverErrorMessage;
    } // method getSilverErrorMessage


    /**
     * Gets the errorcode for the titanium instance variable
     * @return errorcode (int)
     */
    public int getTitaniumError() {
        return titaniumError;
    } // method getTitaniumError

    /**
     * Gets the errorMessage for the titanium instance variable
     * @return errorMessage (String)
     */
    public String getTitaniumErrorMessage() {
        return titaniumErrorMessage;
    } // method getTitaniumErrorMessage


    /**
     * Gets the errorcode for the vanadium instance variable
     * @return errorcode (int)
     */
    public int getVanadiumError() {
        return vanadiumError;
    } // method getVanadiumError

    /**
     * Gets the errorMessage for the vanadium instance variable
     * @return errorMessage (String)
     */
    public String getVanadiumErrorMessage() {
        return vanadiumErrorMessage;
    } // method getVanadiumErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnWatpol2. e.g.<pre>
     * MrnWatpol2 watpol2 = new MrnWatpol2(<val1>);
     * MrnWatpol2 watpol2Array[] = watpol2.get();</pre>
     * will get the MrnWatpol2 record where watphyCode = <val1>.
     * @return Array of MrnWatpol2 (MrnWatpol2[])
     */
    public MrnWatpol2[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnWatpol2 watpol2Array[] =
     *     new MrnWatpol2().get(MrnWatpol2.WATPHY_CODE+"=<val1>");</pre>
     * will get the MrnWatpol2 record where watphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnWatpol2 (MrnWatpol2[])
     */
    public MrnWatpol2[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnWatpol2 watpol2Array[] =
     *     new MrnWatpol2().get("1=1",watpol2.WATPHY_CODE);</pre>
     * will get the all the MrnWatpol2 records, and order them by watphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatpol2 (MrnWatpol2[])
     */
    public MrnWatpol2[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnWatpol2.WATPHY_CODE,MrnWatpol2.ALUMINIUM;
     * String where = MrnWatpol2.WATPHY_CODE + "=<val1";
     * String order = MrnWatpol2.WATPHY_CODE;
     * MrnWatpol2 watpol2Array[] =
     *     new MrnWatpol2().get(columns, where, order);</pre>
     * will get the watphyCode and aluminium colums of all MrnWatpol2 records,
     * where watphyCode = <val1>, and order them by watphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatpol2 (MrnWatpol2[])
     */
    public MrnWatpol2[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnWatpol2.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnWatpol2[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int watphyCodeCol = db.getColNumber(WATPHY_CODE);
        int aluminiumCol  = db.getColNumber(ALUMINIUM);
        int antimonyCol   = db.getColNumber(ANTIMONY);
        int bismuthCol    = db.getColNumber(BISMUTH);
        int molybdenumCol = db.getColNumber(MOLYBDENUM);
        int silverCol     = db.getColNumber(SILVER);
        int titaniumCol   = db.getColNumber(TITANIUM);
        int vanadiumCol   = db.getColNumber(VANADIUM);
        MrnWatpol2[] cArray = new MrnWatpol2[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnWatpol2();
            if (watphyCodeCol != -1)
                cArray[i].setWatphyCode((String) row.elementAt(watphyCodeCol));
            if (aluminiumCol != -1)
                cArray[i].setAluminium ((String) row.elementAt(aluminiumCol));
            if (antimonyCol != -1)
                cArray[i].setAntimony  ((String) row.elementAt(antimonyCol));
            if (bismuthCol != -1)
                cArray[i].setBismuth   ((String) row.elementAt(bismuthCol));
            if (molybdenumCol != -1)
                cArray[i].setMolybdenum((String) row.elementAt(molybdenumCol));
            if (silverCol != -1)
                cArray[i].setSilver    ((String) row.elementAt(silverCol));
            if (titaniumCol != -1)
                cArray[i].setTitanium  ((String) row.elementAt(titaniumCol));
            if (vanadiumCol != -1)
                cArray[i].setVanadium  ((String) row.elementAt(vanadiumCol));
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
     *     new MrnWatpol2(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>).put();</pre>
     * will insert a record with:
     *     watphyCode = <val1>,
     *     aluminium  = <val2>,
     *     antimony   = <val3>,
     *     bismuth    = <val4>,
     *     molybdenum = <val5>,
     *     silver     = <val6>,
     *     titanium   = <val7>,
     *     vanadium   = <val8>.
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
     * Boolean success = new MrnWatpol2(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where aluminium = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatpol2 watpol2 = new MrnWatpol2();
     * success = watpol2.del(MrnWatpol2.WATPHY_CODE+"=<val1>");</pre>
     * will delete all records where watphyCode = <val1>.
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
     * update are taken from the MrnWatpol2 argument, .e.g.<pre>
     * Boolean success
     * MrnWatpol2 updMrnWatpol2 = new MrnWatpol2();
     * updMrnWatpol2.setAluminium(<val2>);
     * MrnWatpol2 whereMrnWatpol2 = new MrnWatpol2(<val1>);
     * success = whereMrnWatpol2.upd(updMrnWatpol2);</pre>
     * will update Aluminium to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watpol2  A MrnWatpol2 variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatpol2 watpol2) {
        return db.update (TABLE, createColVals(watpol2), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatpol2 updMrnWatpol2 = new MrnWatpol2();
     * updMrnWatpol2.setAluminium(<val2>);
     * MrnWatpol2 whereMrnWatpol2 = new MrnWatpol2();
     * success = whereMrnWatpol2.upd(
     *     updMrnWatpol2, MrnWatpol2.WATPHY_CODE+"=<val1>");</pre>
     * will update Aluminium to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watpol2  A MrnWatpol2 variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatpol2 watpol2, String where) {
        return db.update (TABLE, createColVals(watpol2), where);
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
        if (getWatphyCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WATPHY_CODE + "=" + getWatphyCode("");
        } // if getWatphyCode
        if (getAluminium() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ALUMINIUM + "=" + getAluminium("");
        } // if getAluminium
        if (getAntimony() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ANTIMONY + "=" + getAntimony("");
        } // if getAntimony
        if (getBismuth() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + BISMUTH + "=" + getBismuth("");
        } // if getBismuth
        if (getMolybdenum() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MOLYBDENUM + "=" + getMolybdenum("");
        } // if getMolybdenum
        if (getSilver() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SILVER + "=" + getSilver("");
        } // if getSilver
        if (getTitanium() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TITANIUM + "=" + getTitanium("");
        } // if getTitanium
        if (getVanadium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + VANADIUM + "=" + getVanadium("");
        } // if getVanadium
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnWatpol2 aVar) {
        String colVals = "";
        if (aVar.getWatphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPHY_CODE +"=";
            colVals += (aVar.getWatphyCode() == INTNULL2 ?
                "null" : aVar.getWatphyCode(""));
        } // if aVar.getWatphyCode
        if (aVar.getAluminium() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ALUMINIUM +"=";
            colVals += (aVar.getAluminium() == INTNULL2 ?
                "null" : aVar.getAluminium(""));
        } // if aVar.getAluminium
        if (aVar.getAntimony() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ANTIMONY +"=";
            colVals += (aVar.getAntimony() == FLOATNULL2 ?
                "null" : aVar.getAntimony(""));
        } // if aVar.getAntimony
        if (aVar.getBismuth() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += BISMUTH +"=";
            colVals += (aVar.getBismuth() == FLOATNULL2 ?
                "null" : aVar.getBismuth(""));
        } // if aVar.getBismuth
        if (aVar.getMolybdenum() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MOLYBDENUM +"=";
            colVals += (aVar.getMolybdenum() == FLOATNULL2 ?
                "null" : aVar.getMolybdenum(""));
        } // if aVar.getMolybdenum
        if (aVar.getSilver() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SILVER +"=";
            colVals += (aVar.getSilver() == FLOATNULL2 ?
                "null" : aVar.getSilver(""));
        } // if aVar.getSilver
        if (aVar.getTitanium() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TITANIUM +"=";
            colVals += (aVar.getTitanium() == INTNULL2 ?
                "null" : aVar.getTitanium(""));
        } // if aVar.getTitanium
        if (aVar.getVanadium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += VANADIUM +"=";
            colVals += (aVar.getVanadium() == FLOATNULL2 ?
                "null" : aVar.getVanadium(""));
        } // if aVar.getVanadium
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = WATPHY_CODE;
        if (getAluminium() != INTNULL) {
            columns = columns + "," + ALUMINIUM;
        } // if getAluminium
        if (getAntimony() != FLOATNULL) {
            columns = columns + "," + ANTIMONY;
        } // if getAntimony
        if (getBismuth() != FLOATNULL) {
            columns = columns + "," + BISMUTH;
        } // if getBismuth
        if (getMolybdenum() != FLOATNULL) {
            columns = columns + "," + MOLYBDENUM;
        } // if getMolybdenum
        if (getSilver() != FLOATNULL) {
            columns = columns + "," + SILVER;
        } // if getSilver
        if (getTitanium() != INTNULL) {
            columns = columns + "," + TITANIUM;
        } // if getTitanium
        if (getVanadium() != FLOATNULL) {
            columns = columns + "," + VANADIUM;
        } // if getVanadium
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getWatphyCode("");
        if (getAluminium() != INTNULL) {
            values  = values  + "," + getAluminium("");
        } // if getAluminium
        if (getAntimony() != FLOATNULL) {
            values  = values  + "," + getAntimony("");
        } // if getAntimony
        if (getBismuth() != FLOATNULL) {
            values  = values  + "," + getBismuth("");
        } // if getBismuth
        if (getMolybdenum() != FLOATNULL) {
            values  = values  + "," + getMolybdenum("");
        } // if getMolybdenum
        if (getSilver() != FLOATNULL) {
            values  = values  + "," + getSilver("");
        } // if getSilver
        if (getTitanium() != INTNULL) {
            values  = values  + "," + getTitanium("");
        } // if getTitanium
        if (getVanadium() != FLOATNULL) {
            values  = values  + "," + getVanadium("");
        } // if getVanadium
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getWatphyCode("") + "|" +
            getAluminium("")  + "|" +
            getAntimony("")   + "|" +
            getBismuth("")    + "|" +
            getMolybdenum("") + "|" +
            getSilver("")     + "|" +
            getTitanium("")   + "|" +
            getVanadium("")   + "|";
    } // method toString

} // class MrnWatpol2
