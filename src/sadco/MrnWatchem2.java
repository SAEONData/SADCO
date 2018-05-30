package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WATCHEM2 table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnWatchem2 extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WATCHEM2";
    /** The watphyCode field name */
    public static final String WATPHY_CODE = "WATPHY_CODE";
    /** The calcium field name */
    public static final String CALCIUM = "CALCIUM";
    /** The cesium field name */
    public static final String CESIUM = "CESIUM";
    /** The hydrocarbons field name */
    public static final String HYDROCARBONS = "HYDROCARBONS";
    /** The magnesium field name */
    public static final String MAGNESIUM = "MAGNESIUM";
    /** The pah field name */
    public static final String PAH = "PAH";
    /** The potassium field name */
    public static final String POTASSIUM = "POTASSIUM";
    /** The rubidium field name */
    public static final String RUBIDIUM = "RUBIDIUM";
    /** The sodium field name */
    public static final String SODIUM = "SODIUM";
    /** The strontium field name */
    public static final String STRONTIUM = "STRONTIUM";
    /** The so4 field name */
    public static final String SO4 = "SO4";
    /** The sussol field name */
    public static final String SUSSOL = "SUSSOL";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       watphyCode;
    private float     calcium;
    private float     cesium;
    private float     hydrocarbons;
    private float     magnesium;
    private float     pah;
    private float     potassium;
    private float     rubidium;
    private float     sodium;
    private float     strontium;
    private float     so4;
    private float     sussol;

    /** The error variables  */
    private int watphyCodeError   = ERROR_NORMAL;
    private int calciumError      = ERROR_NORMAL;
    private int cesiumError       = ERROR_NORMAL;
    private int hydrocarbonsError = ERROR_NORMAL;
    private int magnesiumError    = ERROR_NORMAL;
    private int pahError          = ERROR_NORMAL;
    private int potassiumError    = ERROR_NORMAL;
    private int rubidiumError     = ERROR_NORMAL;
    private int sodiumError       = ERROR_NORMAL;
    private int strontiumError    = ERROR_NORMAL;
    private int so4Error          = ERROR_NORMAL;
    private int sussolError       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String watphyCodeErrorMessage   = "";
    private String calciumErrorMessage      = "";
    private String cesiumErrorMessage       = "";
    private String hydrocarbonsErrorMessage = "";
    private String magnesiumErrorMessage    = "";
    private String pahErrorMessage          = "";
    private String potassiumErrorMessage    = "";
    private String rubidiumErrorMessage     = "";
    private String sodiumErrorMessage       = "";
    private String strontiumErrorMessage    = "";
    private String so4ErrorMessage          = "";
    private String sussolErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       WATPHY_CODE_MN = INTMIN;
    public static final int       WATPHY_CODE_MX = INTMAX;
    public static final float     CALCIUM_MN = FLOATMIN;
    public static final float     CALCIUM_MX = FLOATMAX;
    public static final float     CESIUM_MN = FLOATMIN;
    public static final float     CESIUM_MX = FLOATMAX;
    public static final float     HYDROCARBONS_MN = FLOATMIN;
    public static final float     HYDROCARBONS_MX = FLOATMAX;
    public static final float     MAGNESIUM_MN = FLOATMIN;
    public static final float     MAGNESIUM_MX = FLOATMAX;
    public static final float     PAH_MN = FLOATMIN;
    public static final float     PAH_MX = FLOATMAX;
    public static final float     POTASSIUM_MN = FLOATMIN;
    public static final float     POTASSIUM_MX = FLOATMAX;
    public static final float     RUBIDIUM_MN = FLOATMIN;
    public static final float     RUBIDIUM_MX = FLOATMAX;
    public static final float     SODIUM_MN = FLOATMIN;
    public static final float     SODIUM_MX = FLOATMAX;
    public static final float     STRONTIUM_MN = FLOATMIN;
    public static final float     STRONTIUM_MX = FLOATMAX;
    public static final float     SO4_MN = FLOATMIN;
    public static final float     SO4_MX = FLOATMAX;
    public static final float     SUSSOL_MN = FLOATMIN;
    public static final float     SUSSOL_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception watphyCodeOutOfBoundsException =
        new Exception ("'watphyCode' out of bounds: " +
            WATPHY_CODE_MN + " - " + WATPHY_CODE_MX);
    Exception calciumOutOfBoundsException =
        new Exception ("'calcium' out of bounds: " +
            CALCIUM_MN + " - " + CALCIUM_MX);
    Exception cesiumOutOfBoundsException =
        new Exception ("'cesium' out of bounds: " +
            CESIUM_MN + " - " + CESIUM_MX);
    Exception hydrocarbonsOutOfBoundsException =
        new Exception ("'hydrocarbons' out of bounds: " +
            HYDROCARBONS_MN + " - " + HYDROCARBONS_MX);
    Exception magnesiumOutOfBoundsException =
        new Exception ("'magnesium' out of bounds: " +
            MAGNESIUM_MN + " - " + MAGNESIUM_MX);
    Exception pahOutOfBoundsException =
        new Exception ("'pah' out of bounds: " +
            PAH_MN + " - " + PAH_MX);
    Exception potassiumOutOfBoundsException =
        new Exception ("'potassium' out of bounds: " +
            POTASSIUM_MN + " - " + POTASSIUM_MX);
    Exception rubidiumOutOfBoundsException =
        new Exception ("'rubidium' out of bounds: " +
            RUBIDIUM_MN + " - " + RUBIDIUM_MX);
    Exception sodiumOutOfBoundsException =
        new Exception ("'sodium' out of bounds: " +
            SODIUM_MN + " - " + SODIUM_MX);
    Exception strontiumOutOfBoundsException =
        new Exception ("'strontium' out of bounds: " +
            STRONTIUM_MN + " - " + STRONTIUM_MX);
    Exception so4OutOfBoundsException =
        new Exception ("'so4' out of bounds: " +
            SO4_MN + " - " + SO4_MX);
    Exception sussolOutOfBoundsException =
        new Exception ("'sussol' out of bounds: " +
            SUSSOL_MN + " - " + SUSSOL_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnWatchem2() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnWatchem2 constructor 1"); // debug
    } // MrnWatchem2 constructor

    /**
     * Instantiate a MrnWatchem2 object and initialize the instance variables.
     * @param watphyCode  The watphyCode (int)
     */
    public MrnWatchem2(
            int watphyCode) {
        this();
        setWatphyCode   (watphyCode  );
        if (dbg) System.out.println ("<br>in MrnWatchem2 constructor 2"); // debug
    } // MrnWatchem2 constructor

    /**
     * Instantiate a MrnWatchem2 object and initialize the instance variables.
     * @param watphyCode    The watphyCode   (int)
     * @param calcium       The calcium      (float)
     * @param cesium        The cesium       (float)
     * @param hydrocarbons  The hydrocarbons (float)
     * @param magnesium     The magnesium    (float)
     * @param pah           The pah          (float)
     * @param potassium     The potassium    (float)
     * @param rubidium      The rubidium     (float)
     * @param sodium        The sodium       (float)
     * @param strontium     The strontium    (float)
     * @param so4           The so4          (float)
     * @param sussol        The sussol       (float)
     */
    public MrnWatchem2(
            int   watphyCode,
            float calcium,
            float cesium,
            float hydrocarbons,
            float magnesium,
            float pah,
            float potassium,
            float rubidium,
            float sodium,
            float strontium,
            float so4,
            float sussol) {
        this();
        setWatphyCode   (watphyCode  );
        setCalcium      (calcium     );
        setCesium       (cesium      );
        setHydrocarbons (hydrocarbons);
        setMagnesium    (magnesium   );
        setPah          (pah         );
        setPotassium    (potassium   );
        setRubidium     (rubidium    );
        setSodium       (sodium      );
        setStrontium    (strontium   );
        setSo4          (so4         );
        setSussol       (sussol      );
        if (dbg) System.out.println ("<br>in MrnWatchem2 constructor 3"); // debug
    } // MrnWatchem2 constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setWatphyCode   (INTNULL  );
        setCalcium      (FLOATNULL);
        setCesium       (FLOATNULL);
        setHydrocarbons (FLOATNULL);
        setMagnesium    (FLOATNULL);
        setPah          (FLOATNULL);
        setPotassium    (FLOATNULL);
        setRubidium     (FLOATNULL);
        setSodium       (FLOATNULL);
        setStrontium    (FLOATNULL);
        setSo4          (FLOATNULL);
        setSussol       (FLOATNULL);
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
     * Set the 'cesium' class variable
     * @param  cesium (float)
     */
    public int setCesium(float cesium) {
        try {
            if ( ((cesium == FLOATNULL) ||
                  (cesium == FLOATNULL2)) ||
                !((cesium < CESIUM_MN) ||
                  (cesium > CESIUM_MX)) ) {
                this.cesium = cesium;
                cesiumError = ERROR_NORMAL;
            } else {
                throw cesiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCesiumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return cesiumError;
    } // method setCesium

    /**
     * Set the 'cesium' class variable
     * @param  cesium (Integer)
     */
    public int setCesium(Integer cesium) {
        try {
            setCesium(cesium.floatValue());
        } catch (Exception e) {
            setCesiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return cesiumError;
    } // method setCesium

    /**
     * Set the 'cesium' class variable
     * @param  cesium (Float)
     */
    public int setCesium(Float cesium) {
        try {
            setCesium(cesium.floatValue());
        } catch (Exception e) {
            setCesiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return cesiumError;
    } // method setCesium

    /**
     * Set the 'cesium' class variable
     * @param  cesium (String)
     */
    public int setCesium(String cesium) {
        try {
            setCesium(new Float(cesium).floatValue());
        } catch (Exception e) {
            setCesiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return cesiumError;
    } // method setCesium

    /**
     * Called when an exception has occured
     * @param  cesium (String)
     */
    private void setCesiumError (float cesium, Exception e, int error) {
        this.cesium = cesium;
        cesiumErrorMessage = e.toString();
        cesiumError = error;
    } // method setCesiumError


    /**
     * Set the 'hydrocarbons' class variable
     * @param  hydrocarbons (float)
     */
    public int setHydrocarbons(float hydrocarbons) {
        try {
            if ( ((hydrocarbons == FLOATNULL) ||
                  (hydrocarbons == FLOATNULL2)) ||
                !((hydrocarbons < HYDROCARBONS_MN) ||
                  (hydrocarbons > HYDROCARBONS_MX)) ) {
                this.hydrocarbons = hydrocarbons;
                hydrocarbonsError = ERROR_NORMAL;
            } else {
                throw hydrocarbonsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setHydrocarbonsError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return hydrocarbonsError;
    } // method setHydrocarbons

    /**
     * Set the 'hydrocarbons' class variable
     * @param  hydrocarbons (Integer)
     */
    public int setHydrocarbons(Integer hydrocarbons) {
        try {
            setHydrocarbons(hydrocarbons.floatValue());
        } catch (Exception e) {
            setHydrocarbonsError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hydrocarbonsError;
    } // method setHydrocarbons

    /**
     * Set the 'hydrocarbons' class variable
     * @param  hydrocarbons (Float)
     */
    public int setHydrocarbons(Float hydrocarbons) {
        try {
            setHydrocarbons(hydrocarbons.floatValue());
        } catch (Exception e) {
            setHydrocarbonsError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hydrocarbonsError;
    } // method setHydrocarbons

    /**
     * Set the 'hydrocarbons' class variable
     * @param  hydrocarbons (String)
     */
    public int setHydrocarbons(String hydrocarbons) {
        try {
            setHydrocarbons(new Float(hydrocarbons).floatValue());
        } catch (Exception e) {
            setHydrocarbonsError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return hydrocarbonsError;
    } // method setHydrocarbons

    /**
     * Called when an exception has occured
     * @param  hydrocarbons (String)
     */
    private void setHydrocarbonsError (float hydrocarbons, Exception e, int error) {
        this.hydrocarbons = hydrocarbons;
        hydrocarbonsErrorMessage = e.toString();
        hydrocarbonsError = error;
    } // method setHydrocarbonsError


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
     * Set the 'pah' class variable
     * @param  pah (float)
     */
    public int setPah(float pah) {
        try {
            if ( ((pah == FLOATNULL) ||
                  (pah == FLOATNULL2)) ||
                !((pah < PAH_MN) ||
                  (pah > PAH_MX)) ) {
                this.pah = pah;
                pahError = ERROR_NORMAL;
            } else {
                throw pahOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPahError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return pahError;
    } // method setPah

    /**
     * Set the 'pah' class variable
     * @param  pah (Integer)
     */
    public int setPah(Integer pah) {
        try {
            setPah(pah.floatValue());
        } catch (Exception e) {
            setPahError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pahError;
    } // method setPah

    /**
     * Set the 'pah' class variable
     * @param  pah (Float)
     */
    public int setPah(Float pah) {
        try {
            setPah(pah.floatValue());
        } catch (Exception e) {
            setPahError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pahError;
    } // method setPah

    /**
     * Set the 'pah' class variable
     * @param  pah (String)
     */
    public int setPah(String pah) {
        try {
            setPah(new Float(pah).floatValue());
        } catch (Exception e) {
            setPahError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pahError;
    } // method setPah

    /**
     * Called when an exception has occured
     * @param  pah (String)
     */
    private void setPahError (float pah, Exception e, int error) {
        this.pah = pah;
        pahErrorMessage = e.toString();
        pahError = error;
    } // method setPahError


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
     * Set the 'rubidium' class variable
     * @param  rubidium (float)
     */
    public int setRubidium(float rubidium) {
        try {
            if ( ((rubidium == FLOATNULL) ||
                  (rubidium == FLOATNULL2)) ||
                !((rubidium < RUBIDIUM_MN) ||
                  (rubidium > RUBIDIUM_MX)) ) {
                this.rubidium = rubidium;
                rubidiumError = ERROR_NORMAL;
            } else {
                throw rubidiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setRubidiumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return rubidiumError;
    } // method setRubidium

    /**
     * Set the 'rubidium' class variable
     * @param  rubidium (Integer)
     */
    public int setRubidium(Integer rubidium) {
        try {
            setRubidium(rubidium.floatValue());
        } catch (Exception e) {
            setRubidiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return rubidiumError;
    } // method setRubidium

    /**
     * Set the 'rubidium' class variable
     * @param  rubidium (Float)
     */
    public int setRubidium(Float rubidium) {
        try {
            setRubidium(rubidium.floatValue());
        } catch (Exception e) {
            setRubidiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return rubidiumError;
    } // method setRubidium

    /**
     * Set the 'rubidium' class variable
     * @param  rubidium (String)
     */
    public int setRubidium(String rubidium) {
        try {
            setRubidium(new Float(rubidium).floatValue());
        } catch (Exception e) {
            setRubidiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return rubidiumError;
    } // method setRubidium

    /**
     * Called when an exception has occured
     * @param  rubidium (String)
     */
    private void setRubidiumError (float rubidium, Exception e, int error) {
        this.rubidium = rubidium;
        rubidiumErrorMessage = e.toString();
        rubidiumError = error;
    } // method setRubidiumError


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
     * Set the 'so4' class variable
     * @param  so4 (float)
     */
    public int setSo4(float so4) {
        try {
            if ( ((so4 == FLOATNULL) ||
                  (so4 == FLOATNULL2)) ||
                !((so4 < SO4_MN) ||
                  (so4 > SO4_MX)) ) {
                this.so4 = so4;
                so4Error = ERROR_NORMAL;
            } else {
                throw so4OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSo4Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return so4Error;
    } // method setSo4

    /**
     * Set the 'so4' class variable
     * @param  so4 (Integer)
     */
    public int setSo4(Integer so4) {
        try {
            setSo4(so4.floatValue());
        } catch (Exception e) {
            setSo4Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return so4Error;
    } // method setSo4

    /**
     * Set the 'so4' class variable
     * @param  so4 (Float)
     */
    public int setSo4(Float so4) {
        try {
            setSo4(so4.floatValue());
        } catch (Exception e) {
            setSo4Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return so4Error;
    } // method setSo4

    /**
     * Set the 'so4' class variable
     * @param  so4 (String)
     */
    public int setSo4(String so4) {
        try {
            setSo4(new Float(so4).floatValue());
        } catch (Exception e) {
            setSo4Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return so4Error;
    } // method setSo4

    /**
     * Called when an exception has occured
     * @param  so4 (String)
     */
    private void setSo4Error (float so4, Exception e, int error) {
        this.so4 = so4;
        so4ErrorMessage = e.toString();
        so4Error = error;
    } // method setSo4Error


    /**
     * Set the 'sussol' class variable
     * @param  sussol (float)
     */
    public int setSussol(float sussol) {
        try {
            if ( ((sussol == FLOATNULL) ||
                  (sussol == FLOATNULL2)) ||
                !((sussol < SUSSOL_MN) ||
                  (sussol > SUSSOL_MX)) ) {
                this.sussol = sussol;
                sussolError = ERROR_NORMAL;
            } else {
                throw sussolOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSussolError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return sussolError;
    } // method setSussol

    /**
     * Set the 'sussol' class variable
     * @param  sussol (Integer)
     */
    public int setSussol(Integer sussol) {
        try {
            setSussol(sussol.floatValue());
        } catch (Exception e) {
            setSussolError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sussolError;
    } // method setSussol

    /**
     * Set the 'sussol' class variable
     * @param  sussol (Float)
     */
    public int setSussol(Float sussol) {
        try {
            setSussol(sussol.floatValue());
        } catch (Exception e) {
            setSussolError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sussolError;
    } // method setSussol

    /**
     * Set the 'sussol' class variable
     * @param  sussol (String)
     */
    public int setSussol(String sussol) {
        try {
            setSussol(new Float(sussol).floatValue());
        } catch (Exception e) {
            setSussolError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sussolError;
    } // method setSussol

    /**
     * Called when an exception has occured
     * @param  sussol (String)
     */
    private void setSussolError (float sussol, Exception e, int error) {
        this.sussol = sussol;
        sussolErrorMessage = e.toString();
        sussolError = error;
    } // method setSussolError


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
     * Return the 'cesium' class variable
     * @return cesium (float)
     */
    public float getCesium() {
        return cesium;
    } // method getCesium

    /**
     * Return the 'cesium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCesium methods
     * @return cesium (String)
     */
    public String getCesium(String s) {
        return ((cesium != FLOATNULL) ? new Float(cesium).toString() : "");
    } // method getCesium


    /**
     * Return the 'hydrocarbons' class variable
     * @return hydrocarbons (float)
     */
    public float getHydrocarbons() {
        return hydrocarbons;
    } // method getHydrocarbons

    /**
     * Return the 'hydrocarbons' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getHydrocarbons methods
     * @return hydrocarbons (String)
     */
    public String getHydrocarbons(String s) {
        return ((hydrocarbons != FLOATNULL) ? new Float(hydrocarbons).toString() : "");
    } // method getHydrocarbons


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
     * Return the 'pah' class variable
     * @return pah (float)
     */
    public float getPah() {
        return pah;
    } // method getPah

    /**
     * Return the 'pah' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPah methods
     * @return pah (String)
     */
    public String getPah(String s) {
        return ((pah != FLOATNULL) ? new Float(pah).toString() : "");
    } // method getPah


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
     * Return the 'rubidium' class variable
     * @return rubidium (float)
     */
    public float getRubidium() {
        return rubidium;
    } // method getRubidium

    /**
     * Return the 'rubidium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRubidium methods
     * @return rubidium (String)
     */
    public String getRubidium(String s) {
        return ((rubidium != FLOATNULL) ? new Float(rubidium).toString() : "");
    } // method getRubidium


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
     * Return the 'so4' class variable
     * @return so4 (float)
     */
    public float getSo4() {
        return so4;
    } // method getSo4

    /**
     * Return the 'so4' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSo4 methods
     * @return so4 (String)
     */
    public String getSo4(String s) {
        return ((so4 != FLOATNULL) ? new Float(so4).toString() : "");
    } // method getSo4


    /**
     * Return the 'sussol' class variable
     * @return sussol (float)
     */
    public float getSussol() {
        return sussol;
    } // method getSussol

    /**
     * Return the 'sussol' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSussol methods
     * @return sussol (String)
     */
    public String getSussol(String s) {
        return ((sussol != FLOATNULL) ? new Float(sussol).toString() : "");
    } // method getSussol


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
            (calcium == FLOATNULL) &&
            (cesium == FLOATNULL) &&
            (hydrocarbons == FLOATNULL) &&
            (magnesium == FLOATNULL) &&
            (pah == FLOATNULL) &&
            (potassium == FLOATNULL) &&
            (rubidium == FLOATNULL) &&
            (sodium == FLOATNULL) &&
            (strontium == FLOATNULL) &&
            (so4 == FLOATNULL) &&
            (sussol == FLOATNULL)) {
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
            calciumError +
            cesiumError +
            hydrocarbonsError +
            magnesiumError +
            pahError +
            potassiumError +
            rubidiumError +
            sodiumError +
            strontiumError +
            so4Error +
            sussolError;
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
     * Gets the errorcode for the cesium instance variable
     * @return errorcode (int)
     */
    public int getCesiumError() {
        return cesiumError;
    } // method getCesiumError

    /**
     * Gets the errorMessage for the cesium instance variable
     * @return errorMessage (String)
     */
    public String getCesiumErrorMessage() {
        return cesiumErrorMessage;
    } // method getCesiumErrorMessage


    /**
     * Gets the errorcode for the hydrocarbons instance variable
     * @return errorcode (int)
     */
    public int getHydrocarbonsError() {
        return hydrocarbonsError;
    } // method getHydrocarbonsError

    /**
     * Gets the errorMessage for the hydrocarbons instance variable
     * @return errorMessage (String)
     */
    public String getHydrocarbonsErrorMessage() {
        return hydrocarbonsErrorMessage;
    } // method getHydrocarbonsErrorMessage


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
     * Gets the errorcode for the pah instance variable
     * @return errorcode (int)
     */
    public int getPahError() {
        return pahError;
    } // method getPahError

    /**
     * Gets the errorMessage for the pah instance variable
     * @return errorMessage (String)
     */
    public String getPahErrorMessage() {
        return pahErrorMessage;
    } // method getPahErrorMessage


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
     * Gets the errorcode for the rubidium instance variable
     * @return errorcode (int)
     */
    public int getRubidiumError() {
        return rubidiumError;
    } // method getRubidiumError

    /**
     * Gets the errorMessage for the rubidium instance variable
     * @return errorMessage (String)
     */
    public String getRubidiumErrorMessage() {
        return rubidiumErrorMessage;
    } // method getRubidiumErrorMessage


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
     * Gets the errorcode for the so4 instance variable
     * @return errorcode (int)
     */
    public int getSo4Error() {
        return so4Error;
    } // method getSo4Error

    /**
     * Gets the errorMessage for the so4 instance variable
     * @return errorMessage (String)
     */
    public String getSo4ErrorMessage() {
        return so4ErrorMessage;
    } // method getSo4ErrorMessage


    /**
     * Gets the errorcode for the sussol instance variable
     * @return errorcode (int)
     */
    public int getSussolError() {
        return sussolError;
    } // method getSussolError

    /**
     * Gets the errorMessage for the sussol instance variable
     * @return errorMessage (String)
     */
    public String getSussolErrorMessage() {
        return sussolErrorMessage;
    } // method getSussolErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnWatchem2. e.g.<pre>
     * MrnWatchem2 watchem2 = new MrnWatchem2(<val1>);
     * MrnWatchem2 watchem2Array[] = watchem2.get();</pre>
     * will get the MrnWatchem2 record where watphyCode = <val1>.
     * @return Array of MrnWatchem2 (MrnWatchem2[])
     */
    public MrnWatchem2[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnWatchem2 watchem2Array[] =
     *     new MrnWatchem2().get(MrnWatchem2.WATPHY_CODE+"=<val1>");</pre>
     * will get the MrnWatchem2 record where watphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnWatchem2 (MrnWatchem2[])
     */
    public MrnWatchem2[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnWatchem2 watchem2Array[] =
     *     new MrnWatchem2().get("1=1",watchem2.WATPHY_CODE);</pre>
     * will get the all the MrnWatchem2 records, and order them by watphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatchem2 (MrnWatchem2[])
     */
    public MrnWatchem2[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnWatchem2.WATPHY_CODE,MrnWatchem2.CALCIUM;
     * String where = MrnWatchem2.WATPHY_CODE + "=<val1";
     * String order = MrnWatchem2.WATPHY_CODE;
     * MrnWatchem2 watchem2Array[] =
     *     new MrnWatchem2().get(columns, where, order);</pre>
     * will get the watphyCode and calcium colums of all MrnWatchem2 records,
     * where watphyCode = <val1>, and order them by watphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatchem2 (MrnWatchem2[])
     */
    public MrnWatchem2[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnWatchem2.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnWatchem2[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int watphyCodeCol   = db.getColNumber(WATPHY_CODE);
        int calciumCol      = db.getColNumber(CALCIUM);
        int cesiumCol       = db.getColNumber(CESIUM);
        int hydrocarbonsCol = db.getColNumber(HYDROCARBONS);
        int magnesiumCol    = db.getColNumber(MAGNESIUM);
        int pahCol          = db.getColNumber(PAH);
        int potassiumCol    = db.getColNumber(POTASSIUM);
        int rubidiumCol     = db.getColNumber(RUBIDIUM);
        int sodiumCol       = db.getColNumber(SODIUM);
        int strontiumCol    = db.getColNumber(STRONTIUM);
        int so4Col          = db.getColNumber(SO4);
        int sussolCol       = db.getColNumber(SUSSOL);
        MrnWatchem2[] cArray = new MrnWatchem2[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnWatchem2();
            if (watphyCodeCol != -1)
                cArray[i].setWatphyCode  ((String) row.elementAt(watphyCodeCol));
            if (calciumCol != -1)
                cArray[i].setCalcium     ((String) row.elementAt(calciumCol));
            if (cesiumCol != -1)
                cArray[i].setCesium      ((String) row.elementAt(cesiumCol));
            if (hydrocarbonsCol != -1)
                cArray[i].setHydrocarbons((String) row.elementAt(hydrocarbonsCol));
            if (magnesiumCol != -1)
                cArray[i].setMagnesium   ((String) row.elementAt(magnesiumCol));
            if (pahCol != -1)
                cArray[i].setPah         ((String) row.elementAt(pahCol));
            if (potassiumCol != -1)
                cArray[i].setPotassium   ((String) row.elementAt(potassiumCol));
            if (rubidiumCol != -1)
                cArray[i].setRubidium    ((String) row.elementAt(rubidiumCol));
            if (sodiumCol != -1)
                cArray[i].setSodium      ((String) row.elementAt(sodiumCol));
            if (strontiumCol != -1)
                cArray[i].setStrontium   ((String) row.elementAt(strontiumCol));
            if (so4Col != -1)
                cArray[i].setSo4         ((String) row.elementAt(so4Col));
            if (sussolCol != -1)
                cArray[i].setSussol      ((String) row.elementAt(sussolCol));
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
     *     new MrnWatchem2(
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
     *         <val11>,
     *         <val12>).put();</pre>
     * will insert a record with:
     *     watphyCode   = <val1>,
     *     calcium      = <val2>,
     *     cesium       = <val3>,
     *     hydrocarbons = <val4>,
     *     magnesium    = <val5>,
     *     pah          = <val6>,
     *     potassium    = <val7>,
     *     rubidium     = <val8>,
     *     sodium       = <val9>,
     *     strontium    = <val10>,
     *     so4          = <val11>,
     *     sussol       = <val12>.
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
     * Boolean success = new MrnWatchem2(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
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
     * MrnWatchem2 watchem2 = new MrnWatchem2();
     * success = watchem2.del(MrnWatchem2.WATPHY_CODE+"=<val1>");</pre>
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
     * update are taken from the MrnWatchem2 argument, .e.g.<pre>
     * Boolean success
     * MrnWatchem2 updMrnWatchem2 = new MrnWatchem2();
     * updMrnWatchem2.setCalcium(<val2>);
     * MrnWatchem2 whereMrnWatchem2 = new MrnWatchem2(<val1>);
     * success = whereMrnWatchem2.upd(updMrnWatchem2);</pre>
     * will update Calcium to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watchem2  A MrnWatchem2 variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatchem2 watchem2) {
        return db.update (TABLE, createColVals(watchem2), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatchem2 updMrnWatchem2 = new MrnWatchem2();
     * updMrnWatchem2.setCalcium(<val2>);
     * MrnWatchem2 whereMrnWatchem2 = new MrnWatchem2();
     * success = whereMrnWatchem2.upd(
     *     updMrnWatchem2, MrnWatchem2.WATPHY_CODE+"=<val1>");</pre>
     * will update Calcium to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watchem2  A MrnWatchem2 variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatchem2 watchem2, String where) {
        return db.update (TABLE, createColVals(watchem2), where);
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
        if (getCalcium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CALCIUM + "=" + getCalcium("");
        } // if getCalcium
        if (getCesium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CESIUM + "=" + getCesium("");
        } // if getCesium
        if (getHydrocarbons() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + HYDROCARBONS + "=" + getHydrocarbons("");
        } // if getHydrocarbons
        if (getMagnesium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MAGNESIUM + "=" + getMagnesium("");
        } // if getMagnesium
        if (getPah() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PAH + "=" + getPah("");
        } // if getPah
        if (getPotassium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + POTASSIUM + "=" + getPotassium("");
        } // if getPotassium
        if (getRubidium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RUBIDIUM + "=" + getRubidium("");
        } // if getRubidium
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
        if (getSo4() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SO4 + "=" + getSo4("");
        } // if getSo4
        if (getSussol() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SUSSOL + "=" + getSussol("");
        } // if getSussol
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnWatchem2 aVar) {
        String colVals = "";
        if (aVar.getWatphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPHY_CODE +"=";
            colVals += (aVar.getWatphyCode() == INTNULL2 ?
                "null" : aVar.getWatphyCode(""));
        } // if aVar.getWatphyCode
        if (aVar.getCalcium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CALCIUM +"=";
            colVals += (aVar.getCalcium() == FLOATNULL2 ?
                "null" : aVar.getCalcium(""));
        } // if aVar.getCalcium
        if (aVar.getCesium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CESIUM +"=";
            colVals += (aVar.getCesium() == FLOATNULL2 ?
                "null" : aVar.getCesium(""));
        } // if aVar.getCesium
        if (aVar.getHydrocarbons() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += HYDROCARBONS +"=";
            colVals += (aVar.getHydrocarbons() == FLOATNULL2 ?
                "null" : aVar.getHydrocarbons(""));
        } // if aVar.getHydrocarbons
        if (aVar.getMagnesium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MAGNESIUM +"=";
            colVals += (aVar.getMagnesium() == FLOATNULL2 ?
                "null" : aVar.getMagnesium(""));
        } // if aVar.getMagnesium
        if (aVar.getPah() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PAH +"=";
            colVals += (aVar.getPah() == FLOATNULL2 ?
                "null" : aVar.getPah(""));
        } // if aVar.getPah
        if (aVar.getPotassium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += POTASSIUM +"=";
            colVals += (aVar.getPotassium() == FLOATNULL2 ?
                "null" : aVar.getPotassium(""));
        } // if aVar.getPotassium
        if (aVar.getRubidium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RUBIDIUM +"=";
            colVals += (aVar.getRubidium() == FLOATNULL2 ?
                "null" : aVar.getRubidium(""));
        } // if aVar.getRubidium
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
        if (aVar.getSo4() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SO4 +"=";
            colVals += (aVar.getSo4() == FLOATNULL2 ?
                "null" : aVar.getSo4(""));
        } // if aVar.getSo4
        if (aVar.getSussol() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SUSSOL +"=";
            colVals += (aVar.getSussol() == FLOATNULL2 ?
                "null" : aVar.getSussol(""));
        } // if aVar.getSussol
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = WATPHY_CODE;
        if (getCalcium() != FLOATNULL) {
            columns = columns + "," + CALCIUM;
        } // if getCalcium
        if (getCesium() != FLOATNULL) {
            columns = columns + "," + CESIUM;
        } // if getCesium
        if (getHydrocarbons() != FLOATNULL) {
            columns = columns + "," + HYDROCARBONS;
        } // if getHydrocarbons
        if (getMagnesium() != FLOATNULL) {
            columns = columns + "," + MAGNESIUM;
        } // if getMagnesium
        if (getPah() != FLOATNULL) {
            columns = columns + "," + PAH;
        } // if getPah
        if (getPotassium() != FLOATNULL) {
            columns = columns + "," + POTASSIUM;
        } // if getPotassium
        if (getRubidium() != FLOATNULL) {
            columns = columns + "," + RUBIDIUM;
        } // if getRubidium
        if (getSodium() != FLOATNULL) {
            columns = columns + "," + SODIUM;
        } // if getSodium
        if (getStrontium() != FLOATNULL) {
            columns = columns + "," + STRONTIUM;
        } // if getStrontium
        if (getSo4() != FLOATNULL) {
            columns = columns + "," + SO4;
        } // if getSo4
        if (getSussol() != FLOATNULL) {
            columns = columns + "," + SUSSOL;
        } // if getSussol
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getWatphyCode("");
        if (getCalcium() != FLOATNULL) {
            values  = values  + "," + getCalcium("");
        } // if getCalcium
        if (getCesium() != FLOATNULL) {
            values  = values  + "," + getCesium("");
        } // if getCesium
        if (getHydrocarbons() != FLOATNULL) {
            values  = values  + "," + getHydrocarbons("");
        } // if getHydrocarbons
        if (getMagnesium() != FLOATNULL) {
            values  = values  + "," + getMagnesium("");
        } // if getMagnesium
        if (getPah() != FLOATNULL) {
            values  = values  + "," + getPah("");
        } // if getPah
        if (getPotassium() != FLOATNULL) {
            values  = values  + "," + getPotassium("");
        } // if getPotassium
        if (getRubidium() != FLOATNULL) {
            values  = values  + "," + getRubidium("");
        } // if getRubidium
        if (getSodium() != FLOATNULL) {
            values  = values  + "," + getSodium("");
        } // if getSodium
        if (getStrontium() != FLOATNULL) {
            values  = values  + "," + getStrontium("");
        } // if getStrontium
        if (getSo4() != FLOATNULL) {
            values  = values  + "," + getSo4("");
        } // if getSo4
        if (getSussol() != FLOATNULL) {
            values  = values  + "," + getSussol("");
        } // if getSussol
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getWatphyCode("")   + "|" +
            getCalcium("")      + "|" +
            getCesium("")       + "|" +
            getHydrocarbons("") + "|" +
            getMagnesium("")    + "|" +
            getPah("")          + "|" +
            getPotassium("")    + "|" +
            getRubidium("")     + "|" +
            getSodium("")       + "|" +
            getStrontium("")    + "|" +
            getSo4("")          + "|" +
            getSussol("")       + "|";
    } // method toString

} // class MrnWatchem2
