package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the TISANIMAL table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnTisanimal extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "TISANIMAL";
    /** The aniseq field name */
    public static final String ANISEQ = "ANISEQ";
    /** The tisphyCode field name */
    public static final String TISPHY_CODE = "TISPHY_CODE";
    /** The taxcod field name */
    public static final String TAXCOD = "TAXCOD";
    /** The anvol field name */
    public static final String ANVOL = "ANVOL";
    /** The anmass field name */
    public static final String ANMASS = "ANMASS";
    /** The ansize field name */
    public static final String ANSIZE = "ANSIZE";
    /** The calval field name */
    public static final String CALVAL = "CALVAL";
    /** The gender field name */
    public static final String GENDER = "GENDER";
    /** The maturity field name */
    public static final String MATURITY = "MATURITY";
    /** The taxcnt field name */
    public static final String TAXCNT = "TAXCNT";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       aniseq;
    private int       tisphyCode;
    private String    taxcod;
    private float     anvol;
    private float     anmass;
    private float     ansize;
    private float     calval;
    private String    gender;
    private String    maturity;
    private int       taxcnt;

    /** The error variables  */
    private int aniseqError     = ERROR_NORMAL;
    private int tisphyCodeError = ERROR_NORMAL;
    private int taxcodError     = ERROR_NORMAL;
    private int anvolError      = ERROR_NORMAL;
    private int anmassError     = ERROR_NORMAL;
    private int ansizeError     = ERROR_NORMAL;
    private int calvalError     = ERROR_NORMAL;
    private int genderError     = ERROR_NORMAL;
    private int maturityError   = ERROR_NORMAL;
    private int taxcntError     = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String aniseqErrorMessage     = "";
    private String tisphyCodeErrorMessage = "";
    private String taxcodErrorMessage     = "";
    private String anvolErrorMessage      = "";
    private String anmassErrorMessage     = "";
    private String ansizeErrorMessage     = "";
    private String calvalErrorMessage     = "";
    private String genderErrorMessage     = "";
    private String maturityErrorMessage   = "";
    private String taxcntErrorMessage     = "";

    /** The min-max constants for all numerics */
    public static final int       ANISEQ_MN = INTMIN;
    public static final int       ANISEQ_MX = INTMAX;
    public static final int       TISPHY_CODE_MN = INTMIN;
    public static final int       TISPHY_CODE_MX = INTMAX;
    public static final float     ANVOL_MN = FLOATMIN;
    public static final float     ANVOL_MX = FLOATMAX;
    public static final float     ANMASS_MN = FLOATMIN;
    public static final float     ANMASS_MX = FLOATMAX;
    public static final float     ANSIZE_MN = FLOATMIN;
    public static final float     ANSIZE_MX = FLOATMAX;
    public static final float     CALVAL_MN = FLOATMIN;
    public static final float     CALVAL_MX = FLOATMAX;
    public static final int       TAXCNT_MN = INTMIN;
    public static final int       TAXCNT_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception aniseqOutOfBoundsException =
        new Exception ("'aniseq' out of bounds: " +
            ANISEQ_MN + " - " + ANISEQ_MX);
    Exception tisphyCodeOutOfBoundsException =
        new Exception ("'tisphyCode' out of bounds: " +
            TISPHY_CODE_MN + " - " + TISPHY_CODE_MX);
    Exception anvolOutOfBoundsException =
        new Exception ("'anvol' out of bounds: " +
            ANVOL_MN + " - " + ANVOL_MX);
    Exception anmassOutOfBoundsException =
        new Exception ("'anmass' out of bounds: " +
            ANMASS_MN + " - " + ANMASS_MX);
    Exception ansizeOutOfBoundsException =
        new Exception ("'ansize' out of bounds: " +
            ANSIZE_MN + " - " + ANSIZE_MX);
    Exception calvalOutOfBoundsException =
        new Exception ("'calval' out of bounds: " +
            CALVAL_MN + " - " + CALVAL_MX);
    Exception taxcntOutOfBoundsException =
        new Exception ("'taxcnt' out of bounds: " +
            TAXCNT_MN + " - " + TAXCNT_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnTisanimal() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnTisanimal constructor 1"); // debug
    } // MrnTisanimal constructor

    /**
     * Instantiate a MrnTisanimal object and initialize the instance variables.
     * @param aniseq  The aniseq (int)
     */
    public MrnTisanimal(
            int aniseq) {
        this();
        setAniseq     (aniseq    );
        if (dbg) System.out.println ("<br>in MrnTisanimal constructor 2"); // debug
    } // MrnTisanimal constructor

    /**
     * Instantiate a MrnTisanimal object and initialize the instance variables.
     * @param aniseq      The aniseq     (int)
     * @param tisphyCode  The tisphyCode (int)
     * @param taxcod      The taxcod     (String)
     * @param anvol       The anvol      (float)
     * @param anmass      The anmass     (float)
     * @param ansize      The ansize     (float)
     * @param calval      The calval     (float)
     * @param gender      The gender     (String)
     * @param maturity    The maturity   (String)
     * @param taxcnt      The taxcnt     (int)
     */
    public MrnTisanimal(
            int    aniseq,
            int    tisphyCode,
            String taxcod,
            float  anvol,
            float  anmass,
            float  ansize,
            float  calval,
            String gender,
            String maturity,
            int    taxcnt) {
        this();
        setAniseq     (aniseq    );
        setTisphyCode (tisphyCode);
        setTaxcod     (taxcod    );
        setAnvol      (anvol     );
        setAnmass     (anmass    );
        setAnsize     (ansize    );
        setCalval     (calval    );
        setGender     (gender    );
        setMaturity   (maturity  );
        setTaxcnt     (taxcnt    );
        if (dbg) System.out.println ("<br>in MrnTisanimal constructor 3"); // debug
    } // MrnTisanimal constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setAniseq     (INTNULL  );
        setTisphyCode (INTNULL  );
        setTaxcod     (CHARNULL );
        setAnvol      (FLOATNULL);
        setAnmass     (FLOATNULL);
        setAnsize     (FLOATNULL);
        setCalval     (FLOATNULL);
        setGender     (CHARNULL );
        setMaturity   (CHARNULL );
        setTaxcnt     (INTNULL  );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'aniseq' class variable
     * @param  aniseq (int)
     */
    public int setAniseq(int aniseq) {
        try {
            if ( ((aniseq == INTNULL) ||
                  (aniseq == INTNULL2)) ||
                !((aniseq < ANISEQ_MN) ||
                  (aniseq > ANISEQ_MX)) ) {
                this.aniseq = aniseq;
                aniseqError = ERROR_NORMAL;
            } else {
                throw aniseqOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAniseqError(INTNULL, e, ERROR_LOCAL);
        } // try
        return aniseqError;
    } // method setAniseq

    /**
     * Set the 'aniseq' class variable
     * @param  aniseq (Integer)
     */
    public int setAniseq(Integer aniseq) {
        try {
            setAniseq(aniseq.intValue());
        } catch (Exception e) {
            setAniseqError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aniseqError;
    } // method setAniseq

    /**
     * Set the 'aniseq' class variable
     * @param  aniseq (Float)
     */
    public int setAniseq(Float aniseq) {
        try {
            if (aniseq.floatValue() == FLOATNULL) {
                setAniseq(INTNULL);
            } else {
                setAniseq(aniseq.intValue());
            } // if (aniseq.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setAniseqError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aniseqError;
    } // method setAniseq

    /**
     * Set the 'aniseq' class variable
     * @param  aniseq (String)
     */
    public int setAniseq(String aniseq) {
        try {
            setAniseq(new Integer(aniseq).intValue());
        } catch (Exception e) {
            setAniseqError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aniseqError;
    } // method setAniseq

    /**
     * Called when an exception has occured
     * @param  aniseq (String)
     */
    private void setAniseqError (int aniseq, Exception e, int error) {
        this.aniseq = aniseq;
        aniseqErrorMessage = e.toString();
        aniseqError = error;
    } // method setAniseqError


    /**
     * Set the 'tisphyCode' class variable
     * @param  tisphyCode (int)
     */
    public int setTisphyCode(int tisphyCode) {
        try {
            if ( ((tisphyCode == INTNULL) ||
                  (tisphyCode == INTNULL2)) ||
                !((tisphyCode < TISPHY_CODE_MN) ||
                  (tisphyCode > TISPHY_CODE_MX)) ) {
                this.tisphyCode = tisphyCode;
                tisphyCodeError = ERROR_NORMAL;
            } else {
                throw tisphyCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTisphyCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return tisphyCodeError;
    } // method setTisphyCode

    /**
     * Set the 'tisphyCode' class variable
     * @param  tisphyCode (Integer)
     */
    public int setTisphyCode(Integer tisphyCode) {
        try {
            setTisphyCode(tisphyCode.intValue());
        } catch (Exception e) {
            setTisphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyCodeError;
    } // method setTisphyCode

    /**
     * Set the 'tisphyCode' class variable
     * @param  tisphyCode (Float)
     */
    public int setTisphyCode(Float tisphyCode) {
        try {
            if (tisphyCode.floatValue() == FLOATNULL) {
                setTisphyCode(INTNULL);
            } else {
                setTisphyCode(tisphyCode.intValue());
            } // if (tisphyCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTisphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyCodeError;
    } // method setTisphyCode

    /**
     * Set the 'tisphyCode' class variable
     * @param  tisphyCode (String)
     */
    public int setTisphyCode(String tisphyCode) {
        try {
            setTisphyCode(new Integer(tisphyCode).intValue());
        } catch (Exception e) {
            setTisphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return tisphyCodeError;
    } // method setTisphyCode

    /**
     * Called when an exception has occured
     * @param  tisphyCode (String)
     */
    private void setTisphyCodeError (int tisphyCode, Exception e, int error) {
        this.tisphyCode = tisphyCode;
        tisphyCodeErrorMessage = e.toString();
        tisphyCodeError = error;
    } // method setTisphyCodeError


    /**
     * Set the 'taxcod' class variable
     * @param  taxcod (String)
     */
    public int setTaxcod(String taxcod) {
        try {
            this.taxcod = taxcod;
            if (this.taxcod != CHARNULL) {
                this.taxcod = stripCRLF(this.taxcod.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>taxcod = " + this.taxcod);
        } catch (Exception e) {
            setTaxcodError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return taxcodError;
    } // method setTaxcod

    /**
     * Called when an exception has occured
     * @param  taxcod (String)
     */
    private void setTaxcodError (String taxcod, Exception e, int error) {
        this.taxcod = taxcod;
        taxcodErrorMessage = e.toString();
        taxcodError = error;
    } // method setTaxcodError


    /**
     * Set the 'anvol' class variable
     * @param  anvol (float)
     */
    public int setAnvol(float anvol) {
        try {
            if ( ((anvol == FLOATNULL) ||
                  (anvol == FLOATNULL2)) ||
                !((anvol < ANVOL_MN) ||
                  (anvol > ANVOL_MX)) ) {
                this.anvol = anvol;
                anvolError = ERROR_NORMAL;
            } else {
                throw anvolOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAnvolError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return anvolError;
    } // method setAnvol

    /**
     * Set the 'anvol' class variable
     * @param  anvol (Integer)
     */
    public int setAnvol(Integer anvol) {
        try {
            setAnvol(anvol.floatValue());
        } catch (Exception e) {
            setAnvolError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return anvolError;
    } // method setAnvol

    /**
     * Set the 'anvol' class variable
     * @param  anvol (Float)
     */
    public int setAnvol(Float anvol) {
        try {
            setAnvol(anvol.floatValue());
        } catch (Exception e) {
            setAnvolError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return anvolError;
    } // method setAnvol

    /**
     * Set the 'anvol' class variable
     * @param  anvol (String)
     */
    public int setAnvol(String anvol) {
        try {
            setAnvol(new Float(anvol).floatValue());
        } catch (Exception e) {
            setAnvolError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return anvolError;
    } // method setAnvol

    /**
     * Called when an exception has occured
     * @param  anvol (String)
     */
    private void setAnvolError (float anvol, Exception e, int error) {
        this.anvol = anvol;
        anvolErrorMessage = e.toString();
        anvolError = error;
    } // method setAnvolError


    /**
     * Set the 'anmass' class variable
     * @param  anmass (float)
     */
    public int setAnmass(float anmass) {
        try {
            if ( ((anmass == FLOATNULL) ||
                  (anmass == FLOATNULL2)) ||
                !((anmass < ANMASS_MN) ||
                  (anmass > ANMASS_MX)) ) {
                this.anmass = anmass;
                anmassError = ERROR_NORMAL;
            } else {
                throw anmassOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAnmassError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return anmassError;
    } // method setAnmass

    /**
     * Set the 'anmass' class variable
     * @param  anmass (Integer)
     */
    public int setAnmass(Integer anmass) {
        try {
            setAnmass(anmass.floatValue());
        } catch (Exception e) {
            setAnmassError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return anmassError;
    } // method setAnmass

    /**
     * Set the 'anmass' class variable
     * @param  anmass (Float)
     */
    public int setAnmass(Float anmass) {
        try {
            setAnmass(anmass.floatValue());
        } catch (Exception e) {
            setAnmassError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return anmassError;
    } // method setAnmass

    /**
     * Set the 'anmass' class variable
     * @param  anmass (String)
     */
    public int setAnmass(String anmass) {
        try {
            setAnmass(new Float(anmass).floatValue());
        } catch (Exception e) {
            setAnmassError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return anmassError;
    } // method setAnmass

    /**
     * Called when an exception has occured
     * @param  anmass (String)
     */
    private void setAnmassError (float anmass, Exception e, int error) {
        this.anmass = anmass;
        anmassErrorMessage = e.toString();
        anmassError = error;
    } // method setAnmassError


    /**
     * Set the 'ansize' class variable
     * @param  ansize (float)
     */
    public int setAnsize(float ansize) {
        try {
            if ( ((ansize == FLOATNULL) ||
                  (ansize == FLOATNULL2)) ||
                !((ansize < ANSIZE_MN) ||
                  (ansize > ANSIZE_MX)) ) {
                this.ansize = ansize;
                ansizeError = ERROR_NORMAL;
            } else {
                throw ansizeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setAnsizeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return ansizeError;
    } // method setAnsize

    /**
     * Set the 'ansize' class variable
     * @param  ansize (Integer)
     */
    public int setAnsize(Integer ansize) {
        try {
            setAnsize(ansize.floatValue());
        } catch (Exception e) {
            setAnsizeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ansizeError;
    } // method setAnsize

    /**
     * Set the 'ansize' class variable
     * @param  ansize (Float)
     */
    public int setAnsize(Float ansize) {
        try {
            setAnsize(ansize.floatValue());
        } catch (Exception e) {
            setAnsizeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ansizeError;
    } // method setAnsize

    /**
     * Set the 'ansize' class variable
     * @param  ansize (String)
     */
    public int setAnsize(String ansize) {
        try {
            setAnsize(new Float(ansize).floatValue());
        } catch (Exception e) {
            setAnsizeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ansizeError;
    } // method setAnsize

    /**
     * Called when an exception has occured
     * @param  ansize (String)
     */
    private void setAnsizeError (float ansize, Exception e, int error) {
        this.ansize = ansize;
        ansizeErrorMessage = e.toString();
        ansizeError = error;
    } // method setAnsizeError


    /**
     * Set the 'calval' class variable
     * @param  calval (float)
     */
    public int setCalval(float calval) {
        try {
            if ( ((calval == FLOATNULL) ||
                  (calval == FLOATNULL2)) ||
                !((calval < CALVAL_MN) ||
                  (calval > CALVAL_MX)) ) {
                this.calval = calval;
                calvalError = ERROR_NORMAL;
            } else {
                throw calvalOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCalvalError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return calvalError;
    } // method setCalval

    /**
     * Set the 'calval' class variable
     * @param  calval (Integer)
     */
    public int setCalval(Integer calval) {
        try {
            setCalval(calval.floatValue());
        } catch (Exception e) {
            setCalvalError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return calvalError;
    } // method setCalval

    /**
     * Set the 'calval' class variable
     * @param  calval (Float)
     */
    public int setCalval(Float calval) {
        try {
            setCalval(calval.floatValue());
        } catch (Exception e) {
            setCalvalError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return calvalError;
    } // method setCalval

    /**
     * Set the 'calval' class variable
     * @param  calval (String)
     */
    public int setCalval(String calval) {
        try {
            setCalval(new Float(calval).floatValue());
        } catch (Exception e) {
            setCalvalError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return calvalError;
    } // method setCalval

    /**
     * Called when an exception has occured
     * @param  calval (String)
     */
    private void setCalvalError (float calval, Exception e, int error) {
        this.calval = calval;
        calvalErrorMessage = e.toString();
        calvalError = error;
    } // method setCalvalError


    /**
     * Set the 'gender' class variable
     * @param  gender (String)
     */
    public int setGender(String gender) {
        try {
            this.gender = gender;
            if (this.gender != CHARNULL) {
                this.gender = stripCRLF(this.gender.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>gender = " + this.gender);
        } catch (Exception e) {
            setGenderError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return genderError;
    } // method setGender

    /**
     * Called when an exception has occured
     * @param  gender (String)
     */
    private void setGenderError (String gender, Exception e, int error) {
        this.gender = gender;
        genderErrorMessage = e.toString();
        genderError = error;
    } // method setGenderError


    /**
     * Set the 'maturity' class variable
     * @param  maturity (String)
     */
    public int setMaturity(String maturity) {
        try {
            this.maturity = maturity;
            if (this.maturity != CHARNULL) {
                this.maturity = stripCRLF(this.maturity.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>maturity = " + this.maturity);
        } catch (Exception e) {
            setMaturityError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return maturityError;
    } // method setMaturity

    /**
     * Called when an exception has occured
     * @param  maturity (String)
     */
    private void setMaturityError (String maturity, Exception e, int error) {
        this.maturity = maturity;
        maturityErrorMessage = e.toString();
        maturityError = error;
    } // method setMaturityError


    /**
     * Set the 'taxcnt' class variable
     * @param  taxcnt (int)
     */
    public int setTaxcnt(int taxcnt) {
        try {
            if ( ((taxcnt == INTNULL) ||
                  (taxcnt == INTNULL2)) ||
                !((taxcnt < TAXCNT_MN) ||
                  (taxcnt > TAXCNT_MX)) ) {
                this.taxcnt = taxcnt;
                taxcntError = ERROR_NORMAL;
            } else {
                throw taxcntOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTaxcntError(INTNULL, e, ERROR_LOCAL);
        } // try
        return taxcntError;
    } // method setTaxcnt

    /**
     * Set the 'taxcnt' class variable
     * @param  taxcnt (Integer)
     */
    public int setTaxcnt(Integer taxcnt) {
        try {
            setTaxcnt(taxcnt.intValue());
        } catch (Exception e) {
            setTaxcntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return taxcntError;
    } // method setTaxcnt

    /**
     * Set the 'taxcnt' class variable
     * @param  taxcnt (Float)
     */
    public int setTaxcnt(Float taxcnt) {
        try {
            if (taxcnt.floatValue() == FLOATNULL) {
                setTaxcnt(INTNULL);
            } else {
                setTaxcnt(taxcnt.intValue());
            } // if (taxcnt.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTaxcntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return taxcntError;
    } // method setTaxcnt

    /**
     * Set the 'taxcnt' class variable
     * @param  taxcnt (String)
     */
    public int setTaxcnt(String taxcnt) {
        try {
            setTaxcnt(new Integer(taxcnt).intValue());
        } catch (Exception e) {
            setTaxcntError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return taxcntError;
    } // method setTaxcnt

    /**
     * Called when an exception has occured
     * @param  taxcnt (String)
     */
    private void setTaxcntError (int taxcnt, Exception e, int error) {
        this.taxcnt = taxcnt;
        taxcntErrorMessage = e.toString();
        taxcntError = error;
    } // method setTaxcntError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'aniseq' class variable
     * @return aniseq (int)
     */
    public int getAniseq() {
        return aniseq;
    } // method getAniseq

    /**
     * Return the 'aniseq' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAniseq methods
     * @return aniseq (String)
     */
    public String getAniseq(String s) {
        return ((aniseq != INTNULL) ? new Integer(aniseq).toString() : "");
    } // method getAniseq


    /**
     * Return the 'tisphyCode' class variable
     * @return tisphyCode (int)
     */
    public int getTisphyCode() {
        return tisphyCode;
    } // method getTisphyCode

    /**
     * Return the 'tisphyCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTisphyCode methods
     * @return tisphyCode (String)
     */
    public String getTisphyCode(String s) {
        return ((tisphyCode != INTNULL) ? new Integer(tisphyCode).toString() : "");
    } // method getTisphyCode


    /**
     * Return the 'taxcod' class variable
     * @return taxcod (String)
     */
    public String getTaxcod() {
        return taxcod;
    } // method getTaxcod

    /**
     * Return the 'taxcod' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTaxcod methods
     * @return taxcod (String)
     */
    public String getTaxcod(String s) {
        return (taxcod != CHARNULL ? taxcod.replace('"','\'') : "");
    } // method getTaxcod


    /**
     * Return the 'anvol' class variable
     * @return anvol (float)
     */
    public float getAnvol() {
        return anvol;
    } // method getAnvol

    /**
     * Return the 'anvol' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAnvol methods
     * @return anvol (String)
     */
    public String getAnvol(String s) {
        return ((anvol != FLOATNULL) ? new Float(anvol).toString() : "");
    } // method getAnvol


    /**
     * Return the 'anmass' class variable
     * @return anmass (float)
     */
    public float getAnmass() {
        return anmass;
    } // method getAnmass

    /**
     * Return the 'anmass' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAnmass methods
     * @return anmass (String)
     */
    public String getAnmass(String s) {
        return ((anmass != FLOATNULL) ? new Float(anmass).toString() : "");
    } // method getAnmass


    /**
     * Return the 'ansize' class variable
     * @return ansize (float)
     */
    public float getAnsize() {
        return ansize;
    } // method getAnsize

    /**
     * Return the 'ansize' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAnsize methods
     * @return ansize (String)
     */
    public String getAnsize(String s) {
        return ((ansize != FLOATNULL) ? new Float(ansize).toString() : "");
    } // method getAnsize


    /**
     * Return the 'calval' class variable
     * @return calval (float)
     */
    public float getCalval() {
        return calval;
    } // method getCalval

    /**
     * Return the 'calval' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCalval methods
     * @return calval (String)
     */
    public String getCalval(String s) {
        return ((calval != FLOATNULL) ? new Float(calval).toString() : "");
    } // method getCalval


    /**
     * Return the 'gender' class variable
     * @return gender (String)
     */
    public String getGender() {
        return gender;
    } // method getGender

    /**
     * Return the 'gender' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getGender methods
     * @return gender (String)
     */
    public String getGender(String s) {
        return (gender != CHARNULL ? gender.replace('"','\'') : "");
    } // method getGender


    /**
     * Return the 'maturity' class variable
     * @return maturity (String)
     */
    public String getMaturity() {
        return maturity;
    } // method getMaturity

    /**
     * Return the 'maturity' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMaturity methods
     * @return maturity (String)
     */
    public String getMaturity(String s) {
        return (maturity != CHARNULL ? maturity.replace('"','\'') : "");
    } // method getMaturity


    /**
     * Return the 'taxcnt' class variable
     * @return taxcnt (int)
     */
    public int getTaxcnt() {
        return taxcnt;
    } // method getTaxcnt

    /**
     * Return the 'taxcnt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTaxcnt methods
     * @return taxcnt (String)
     */
    public String getTaxcnt(String s) {
        return ((taxcnt != INTNULL) ? new Integer(taxcnt).toString() : "");
    } // method getTaxcnt


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
        if ((aniseq == INTNULL) &&
            (tisphyCode == INTNULL) &&
            (taxcod == CHARNULL) &&
            (anvol == FLOATNULL) &&
            (anmass == FLOATNULL) &&
            (ansize == FLOATNULL) &&
            (calval == FLOATNULL) &&
            (gender == CHARNULL) &&
            (maturity == CHARNULL) &&
            (taxcnt == INTNULL)) {
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
        int sumError = aniseqError +
            tisphyCodeError +
            taxcodError +
            anvolError +
            anmassError +
            ansizeError +
            calvalError +
            genderError +
            maturityError +
            taxcntError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the aniseq instance variable
     * @return errorcode (int)
     */
    public int getAniseqError() {
        return aniseqError;
    } // method getAniseqError

    /**
     * Gets the errorMessage for the aniseq instance variable
     * @return errorMessage (String)
     */
    public String getAniseqErrorMessage() {
        return aniseqErrorMessage;
    } // method getAniseqErrorMessage


    /**
     * Gets the errorcode for the tisphyCode instance variable
     * @return errorcode (int)
     */
    public int getTisphyCodeError() {
        return tisphyCodeError;
    } // method getTisphyCodeError

    /**
     * Gets the errorMessage for the tisphyCode instance variable
     * @return errorMessage (String)
     */
    public String getTisphyCodeErrorMessage() {
        return tisphyCodeErrorMessage;
    } // method getTisphyCodeErrorMessage


    /**
     * Gets the errorcode for the taxcod instance variable
     * @return errorcode (int)
     */
    public int getTaxcodError() {
        return taxcodError;
    } // method getTaxcodError

    /**
     * Gets the errorMessage for the taxcod instance variable
     * @return errorMessage (String)
     */
    public String getTaxcodErrorMessage() {
        return taxcodErrorMessage;
    } // method getTaxcodErrorMessage


    /**
     * Gets the errorcode for the anvol instance variable
     * @return errorcode (int)
     */
    public int getAnvolError() {
        return anvolError;
    } // method getAnvolError

    /**
     * Gets the errorMessage for the anvol instance variable
     * @return errorMessage (String)
     */
    public String getAnvolErrorMessage() {
        return anvolErrorMessage;
    } // method getAnvolErrorMessage


    /**
     * Gets the errorcode for the anmass instance variable
     * @return errorcode (int)
     */
    public int getAnmassError() {
        return anmassError;
    } // method getAnmassError

    /**
     * Gets the errorMessage for the anmass instance variable
     * @return errorMessage (String)
     */
    public String getAnmassErrorMessage() {
        return anmassErrorMessage;
    } // method getAnmassErrorMessage


    /**
     * Gets the errorcode for the ansize instance variable
     * @return errorcode (int)
     */
    public int getAnsizeError() {
        return ansizeError;
    } // method getAnsizeError

    /**
     * Gets the errorMessage for the ansize instance variable
     * @return errorMessage (String)
     */
    public String getAnsizeErrorMessage() {
        return ansizeErrorMessage;
    } // method getAnsizeErrorMessage


    /**
     * Gets the errorcode for the calval instance variable
     * @return errorcode (int)
     */
    public int getCalvalError() {
        return calvalError;
    } // method getCalvalError

    /**
     * Gets the errorMessage for the calval instance variable
     * @return errorMessage (String)
     */
    public String getCalvalErrorMessage() {
        return calvalErrorMessage;
    } // method getCalvalErrorMessage


    /**
     * Gets the errorcode for the gender instance variable
     * @return errorcode (int)
     */
    public int getGenderError() {
        return genderError;
    } // method getGenderError

    /**
     * Gets the errorMessage for the gender instance variable
     * @return errorMessage (String)
     */
    public String getGenderErrorMessage() {
        return genderErrorMessage;
    } // method getGenderErrorMessage


    /**
     * Gets the errorcode for the maturity instance variable
     * @return errorcode (int)
     */
    public int getMaturityError() {
        return maturityError;
    } // method getMaturityError

    /**
     * Gets the errorMessage for the maturity instance variable
     * @return errorMessage (String)
     */
    public String getMaturityErrorMessage() {
        return maturityErrorMessage;
    } // method getMaturityErrorMessage


    /**
     * Gets the errorcode for the taxcnt instance variable
     * @return errorcode (int)
     */
    public int getTaxcntError() {
        return taxcntError;
    } // method getTaxcntError

    /**
     * Gets the errorMessage for the taxcnt instance variable
     * @return errorMessage (String)
     */
    public String getTaxcntErrorMessage() {
        return taxcntErrorMessage;
    } // method getTaxcntErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnTisanimal. e.g.<pre>
     * MrnTisanimal tisanimal = new MrnTisanimal(<val1>);
     * MrnTisanimal tisanimalArray[] = tisanimal.get();</pre>
     * will get the MrnTisanimal record where aniseq = <val1>.
     * @return Array of MrnTisanimal (MrnTisanimal[])
     */
    public MrnTisanimal[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnTisanimal tisanimalArray[] =
     *     new MrnTisanimal().get(MrnTisanimal.ANISEQ+"=<val1>");</pre>
     * will get the MrnTisanimal record where aniseq = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnTisanimal (MrnTisanimal[])
     */
    public MrnTisanimal[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnTisanimal tisanimalArray[] =
     *     new MrnTisanimal().get("1=1",tisanimal.ANISEQ);</pre>
     * will get the all the MrnTisanimal records, and order them by aniseq.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnTisanimal (MrnTisanimal[])
     */
    public MrnTisanimal[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnTisanimal.ANISEQ,MrnTisanimal.TISPHY_CODE;
     * String where = MrnTisanimal.ANISEQ + "=<val1";
     * String order = MrnTisanimal.ANISEQ;
     * MrnTisanimal tisanimalArray[] =
     *     new MrnTisanimal().get(columns, where, order);</pre>
     * will get the aniseq and tisphyCode colums of all MrnTisanimal records,
     * where aniseq = <val1>, and order them by aniseq.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnTisanimal (MrnTisanimal[])
     */
    public MrnTisanimal[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnTisanimal.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnTisanimal[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int aniseqCol     = db.getColNumber(ANISEQ);
        int tisphyCodeCol = db.getColNumber(TISPHY_CODE);
        int taxcodCol     = db.getColNumber(TAXCOD);
        int anvolCol      = db.getColNumber(ANVOL);
        int anmassCol     = db.getColNumber(ANMASS);
        int ansizeCol     = db.getColNumber(ANSIZE);
        int calvalCol     = db.getColNumber(CALVAL);
        int genderCol     = db.getColNumber(GENDER);
        int maturityCol   = db.getColNumber(MATURITY);
        int taxcntCol     = db.getColNumber(TAXCNT);
        MrnTisanimal[] cArray = new MrnTisanimal[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnTisanimal();
            if (aniseqCol != -1)
                cArray[i].setAniseq    ((String) row.elementAt(aniseqCol));
            if (tisphyCodeCol != -1)
                cArray[i].setTisphyCode((String) row.elementAt(tisphyCodeCol));
            if (taxcodCol != -1)
                cArray[i].setTaxcod    ((String) row.elementAt(taxcodCol));
            if (anvolCol != -1)
                cArray[i].setAnvol     ((String) row.elementAt(anvolCol));
            if (anmassCol != -1)
                cArray[i].setAnmass    ((String) row.elementAt(anmassCol));
            if (ansizeCol != -1)
                cArray[i].setAnsize    ((String) row.elementAt(ansizeCol));
            if (calvalCol != -1)
                cArray[i].setCalval    ((String) row.elementAt(calvalCol));
            if (genderCol != -1)
                cArray[i].setGender    ((String) row.elementAt(genderCol));
            if (maturityCol != -1)
                cArray[i].setMaturity  ((String) row.elementAt(maturityCol));
            if (taxcntCol != -1)
                cArray[i].setTaxcnt    ((String) row.elementAt(taxcntCol));
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
     *     new MrnTisanimal(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>,
     *         <val9>,
     *         <val10>).put();</pre>
     * will insert a record with:
     *     aniseq     = <val1>,
     *     tisphyCode = <val2>,
     *     taxcod     = <val3>,
     *     anvol      = <val4>,
     *     anmass     = <val5>,
     *     ansize     = <val6>,
     *     calval     = <val7>,
     *     gender     = <val8>,
     *     maturity   = <val9>,
     *     taxcnt     = <val10>.
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
     * Boolean success = new MrnTisanimal(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where tisphyCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnTisanimal tisanimal = new MrnTisanimal();
     * success = tisanimal.del(MrnTisanimal.ANISEQ+"=<val1>");</pre>
     * will delete all records where aniseq = <val1>.
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
     * update are taken from the MrnTisanimal argument, .e.g.<pre>
     * Boolean success
     * MrnTisanimal updMrnTisanimal = new MrnTisanimal();
     * updMrnTisanimal.setTisphyCode(<val2>);
     * MrnTisanimal whereMrnTisanimal = new MrnTisanimal(<val1>);
     * success = whereMrnTisanimal.upd(updMrnTisanimal);</pre>
     * will update TisphyCode to <val2> for all records where
     * aniseq = <val1>.
     * @param  tisanimal  A MrnTisanimal variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnTisanimal tisanimal) {
        return db.update (TABLE, createColVals(tisanimal), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnTisanimal updMrnTisanimal = new MrnTisanimal();
     * updMrnTisanimal.setTisphyCode(<val2>);
     * MrnTisanimal whereMrnTisanimal = new MrnTisanimal();
     * success = whereMrnTisanimal.upd(
     *     updMrnTisanimal, MrnTisanimal.ANISEQ+"=<val1>");</pre>
     * will update TisphyCode to <val2> for all records where
     * aniseq = <val1>.
     * @param  tisanimal  A MrnTisanimal variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnTisanimal tisanimal, String where) {
        return db.update (TABLE, createColVals(tisanimal), where);
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
        if (getAniseq() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ANISEQ + "=" + getAniseq("");
        } // if getAniseq
        if (getTisphyCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TISPHY_CODE + "=" + getTisphyCode("");
        } // if getTisphyCode
        if (getTaxcod() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TAXCOD + "='" + getTaxcod() + "'";
        } // if getTaxcod
        if (getAnvol() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ANVOL + "=" + getAnvol("");
        } // if getAnvol
        if (getAnmass() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ANMASS + "=" + getAnmass("");
        } // if getAnmass
        if (getAnsize() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ANSIZE + "=" + getAnsize("");
        } // if getAnsize
        if (getCalval() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CALVAL + "=" + getCalval("");
        } // if getCalval
        if (getGender() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + GENDER + "='" + getGender() + "'";
        } // if getGender
        if (getMaturity() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MATURITY + "='" + getMaturity() + "'";
        } // if getMaturity
        if (getTaxcnt() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TAXCNT + "=" + getTaxcnt("");
        } // if getTaxcnt
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnTisanimal aVar) {
        String colVals = "";
        if (aVar.getAniseq() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ANISEQ +"=";
            colVals += (aVar.getAniseq() == INTNULL2 ?
                "null" : aVar.getAniseq(""));
        } // if aVar.getAniseq
        if (aVar.getTisphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TISPHY_CODE +"=";
            colVals += (aVar.getTisphyCode() == INTNULL2 ?
                "null" : aVar.getTisphyCode(""));
        } // if aVar.getTisphyCode
        if (aVar.getTaxcod() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TAXCOD +"=";
            colVals += (aVar.getTaxcod().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTaxcod() + "'");
        } // if aVar.getTaxcod
        if (aVar.getAnvol() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ANVOL +"=";
            colVals += (aVar.getAnvol() == FLOATNULL2 ?
                "null" : aVar.getAnvol(""));
        } // if aVar.getAnvol
        if (aVar.getAnmass() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ANMASS +"=";
            colVals += (aVar.getAnmass() == FLOATNULL2 ?
                "null" : aVar.getAnmass(""));
        } // if aVar.getAnmass
        if (aVar.getAnsize() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ANSIZE +"=";
            colVals += (aVar.getAnsize() == FLOATNULL2 ?
                "null" : aVar.getAnsize(""));
        } // if aVar.getAnsize
        if (aVar.getCalval() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CALVAL +"=";
            colVals += (aVar.getCalval() == FLOATNULL2 ?
                "null" : aVar.getCalval(""));
        } // if aVar.getCalval
        if (aVar.getGender() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += GENDER +"=";
            colVals += (aVar.getGender().equals(CHARNULL2) ?
                "null" : "'" + aVar.getGender() + "'");
        } // if aVar.getGender
        if (aVar.getMaturity() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MATURITY +"=";
            colVals += (aVar.getMaturity().equals(CHARNULL2) ?
                "null" : "'" + aVar.getMaturity() + "'");
        } // if aVar.getMaturity
        if (aVar.getTaxcnt() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TAXCNT +"=";
            colVals += (aVar.getTaxcnt() == INTNULL2 ?
                "null" : aVar.getTaxcnt(""));
        } // if aVar.getTaxcnt
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = ANISEQ;
        if (getTisphyCode() != INTNULL) {
            columns = columns + "," + TISPHY_CODE;
        } // if getTisphyCode
        if (getTaxcod() != CHARNULL) {
            columns = columns + "," + TAXCOD;
        } // if getTaxcod
        if (getAnvol() != FLOATNULL) {
            columns = columns + "," + ANVOL;
        } // if getAnvol
        if (getAnmass() != FLOATNULL) {
            columns = columns + "," + ANMASS;
        } // if getAnmass
        if (getAnsize() != FLOATNULL) {
            columns = columns + "," + ANSIZE;
        } // if getAnsize
        if (getCalval() != FLOATNULL) {
            columns = columns + "," + CALVAL;
        } // if getCalval
        if (getGender() != CHARNULL) {
            columns = columns + "," + GENDER;
        } // if getGender
        if (getMaturity() != CHARNULL) {
            columns = columns + "," + MATURITY;
        } // if getMaturity
        if (getTaxcnt() != INTNULL) {
            columns = columns + "," + TAXCNT;
        } // if getTaxcnt
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getAniseq("");
        if (getTisphyCode() != INTNULL) {
            values  = values  + "," + getTisphyCode("");
        } // if getTisphyCode
        if (getTaxcod() != CHARNULL) {
            values  = values  + ",'" + getTaxcod() + "'";
        } // if getTaxcod
        if (getAnvol() != FLOATNULL) {
            values  = values  + "," + getAnvol("");
        } // if getAnvol
        if (getAnmass() != FLOATNULL) {
            values  = values  + "," + getAnmass("");
        } // if getAnmass
        if (getAnsize() != FLOATNULL) {
            values  = values  + "," + getAnsize("");
        } // if getAnsize
        if (getCalval() != FLOATNULL) {
            values  = values  + "," + getCalval("");
        } // if getCalval
        if (getGender() != CHARNULL) {
            values  = values  + ",'" + getGender() + "'";
        } // if getGender
        if (getMaturity() != CHARNULL) {
            values  = values  + ",'" + getMaturity() + "'";
        } // if getMaturity
        if (getTaxcnt() != INTNULL) {
            values  = values  + "," + getTaxcnt("");
        } // if getTaxcnt
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getAniseq("")     + "|" +
            getTisphyCode("") + "|" +
            getTaxcod("")     + "|" +
            getAnvol("")      + "|" +
            getAnmass("")     + "|" +
            getAnsize("")     + "|" +
            getCalval("")     + "|" +
            getGender("")     + "|" +
            getMaturity("")   + "|" +
            getTaxcnt("")     + "|";
    } // method toString

} // class MrnTisanimal
