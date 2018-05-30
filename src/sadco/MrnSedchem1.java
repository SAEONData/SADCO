package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SEDCHEM1 table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 060127 - SIT Group
 * @version
 * 060127 - GenTableClassB class - create class<br>
 */
public class MrnSedchem1 extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SEDCHEM1";
    /** The sedphyCode field name */
    public static final String SEDPHY_CODE = "SEDPHY_CODE";
    /** The fluoride field name */
    public static final String FLUORIDE = "FLUORIDE";
    /** The kjn field name */
    public static final String KJN = "KJN";
    /** The oxa field name */
    public static final String OXA = "OXA";
    /** The toc field name */
    public static final String TOC = "TOC";
    /** The ptot field name */
    public static final String PTOT = "PTOT";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       sedphyCode;
    private float     fluoride;
    private float     kjn;
    private float     oxa;
    private float     toc;
    private float     ptot;

    /** The error variables  */
    private int sedphyCodeError = ERROR_NORMAL;
    private int fluorideError   = ERROR_NORMAL;
    private int kjnError        = ERROR_NORMAL;
    private int oxaError        = ERROR_NORMAL;
    private int tocError        = ERROR_NORMAL;
    private int ptotError       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String sedphyCodeErrorMessage = "";
    private String fluorideErrorMessage   = "";
    private String kjnErrorMessage        = "";
    private String oxaErrorMessage        = "";
    private String tocErrorMessage        = "";
    private String ptotErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       SEDPHY_CODE_MN = INTMIN;
    public static final int       SEDPHY_CODE_MX = INTMAX;
    public static final float     FLUORIDE_MN = FLOATMIN;
    public static final float     FLUORIDE_MX = FLOATMAX;
    public static final float     KJN_MN = FLOATMIN;
    public static final float     KJN_MX = FLOATMAX;
    public static final float     OXA_MN = FLOATMIN;
    public static final float     OXA_MX = FLOATMAX;
    public static final float     TOC_MN = FLOATMIN;
    public static final float     TOC_MX = FLOATMAX;
    public static final float     PTOT_MN = FLOATMIN;
    public static final float     PTOT_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception sedphyCodeOutOfBoundsException =
        new Exception ("'sedphyCode' out of bounds: " +
            SEDPHY_CODE_MN + " - " + SEDPHY_CODE_MX);
    Exception fluorideOutOfBoundsException =
        new Exception ("'fluoride' out of bounds: " +
            FLUORIDE_MN + " - " + FLUORIDE_MX);
    Exception kjnOutOfBoundsException =
        new Exception ("'kjn' out of bounds: " +
            KJN_MN + " - " + KJN_MX);
    Exception oxaOutOfBoundsException =
        new Exception ("'oxa' out of bounds: " +
            OXA_MN + " - " + OXA_MX);
    Exception tocOutOfBoundsException =
        new Exception ("'toc' out of bounds: " +
            TOC_MN + " - " + TOC_MX);
    Exception ptotOutOfBoundsException =
        new Exception ("'ptot' out of bounds: " +
            PTOT_MN + " - " + PTOT_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnSedchem1() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnSedchem1 constructor 1"); // debug
    } // MrnSedchem1 constructor

    /**
     * Instantiate a MrnSedchem1 object and initialize the instance variables.
     * @param sedphyCode  The sedphyCode (int)
     */
    public MrnSedchem1(
            int sedphyCode) {
        this();
        setSedphyCode (sedphyCode);
        if (dbg) System.out.println ("<br>in MrnSedchem1 constructor 2"); // debug
    } // MrnSedchem1 constructor

    /**
     * Instantiate a MrnSedchem1 object and initialize the instance variables.
     * @param sedphyCode  The sedphyCode (int)
     * @param fluoride    The fluoride   (float)
     * @param kjn         The kjn        (float)
     * @param oxa         The oxa        (float)
     * @param toc         The toc        (float)
     * @param ptot        The ptot       (float)
     * @return A MrnSedchem1 object
     */
    public MrnSedchem1(
            int   sedphyCode,
            float fluoride,
            float kjn,
            float oxa,
            float toc,
            float ptot) {
        this();
        setSedphyCode (sedphyCode);
        setFluoride   (fluoride  );
        setKjn        (kjn       );
        setOxa        (oxa       );
        setToc        (toc       );
        setPtot       (ptot      );
        if (dbg) System.out.println ("<br>in MrnSedchem1 constructor 3"); // debug
    } // MrnSedchem1 constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSedphyCode (INTNULL  );
        setFluoride   (FLOATNULL);
        setKjn        (FLOATNULL);
        setOxa        (FLOATNULL);
        setToc        (FLOATNULL);
        setPtot       (FLOATNULL);
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
     * Set the 'fluoride' class variable
     * @param  fluoride (float)
     */
    public int setFluoride(float fluoride) {
        try {
            if ( ((fluoride == FLOATNULL) || 
                  (fluoride == FLOATNULL2)) ||
                !((fluoride < FLUORIDE_MN) ||
                  (fluoride > FLUORIDE_MX)) ) {
                this.fluoride = fluoride;
                fluorideError = ERROR_NORMAL;
            } else {
                throw fluorideOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFluorideError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return fluorideError;
    } // method setFluoride

    /**
     * Set the 'fluoride' class variable
     * @param  fluoride (Integer)
     */
    public int setFluoride(Integer fluoride) {
        try {
            setFluoride(fluoride.floatValue());
        } catch (Exception e) {
            setFluorideError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fluorideError;
    } // method setFluoride

    /**
     * Set the 'fluoride' class variable
     * @param  fluoride (Float)
     */
    public int setFluoride(Float fluoride) {
        try {
            setFluoride(fluoride.floatValue());
        } catch (Exception e) {
            setFluorideError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fluorideError;
    } // method setFluoride

    /**
     * Set the 'fluoride' class variable
     * @param  fluoride (String)
     */
    public int setFluoride(String fluoride) {
        try {
            setFluoride(new Float(fluoride).floatValue());
        } catch (Exception e) {
            setFluorideError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fluorideError;
    } // method setFluoride

    /**
     * Called when an exception has occured
     * @param  fluoride (String)
     */
    private void setFluorideError (float fluoride, Exception e, int error) {
        this.fluoride = fluoride;
        fluorideErrorMessage = e.toString();
        fluorideError = error;
    } // method setFluorideError


    /**
     * Set the 'kjn' class variable
     * @param  kjn (float)
     */
    public int setKjn(float kjn) {
        try {
            if ( ((kjn == FLOATNULL) || 
                  (kjn == FLOATNULL2)) ||
                !((kjn < KJN_MN) ||
                  (kjn > KJN_MX)) ) {
                this.kjn = kjn;
                kjnError = ERROR_NORMAL;
            } else {
                throw kjnOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setKjnError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return kjnError;
    } // method setKjn

    /**
     * Set the 'kjn' class variable
     * @param  kjn (Integer)
     */
    public int setKjn(Integer kjn) {
        try {
            setKjn(kjn.floatValue());
        } catch (Exception e) {
            setKjnError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return kjnError;
    } // method setKjn

    /**
     * Set the 'kjn' class variable
     * @param  kjn (Float)
     */
    public int setKjn(Float kjn) {
        try {
            setKjn(kjn.floatValue());
        } catch (Exception e) {
            setKjnError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return kjnError;
    } // method setKjn

    /**
     * Set the 'kjn' class variable
     * @param  kjn (String)
     */
    public int setKjn(String kjn) {
        try {
            setKjn(new Float(kjn).floatValue());
        } catch (Exception e) {
            setKjnError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return kjnError;
    } // method setKjn

    /**
     * Called when an exception has occured
     * @param  kjn (String)
     */
    private void setKjnError (float kjn, Exception e, int error) {
        this.kjn = kjn;
        kjnErrorMessage = e.toString();
        kjnError = error;
    } // method setKjnError


    /**
     * Set the 'oxa' class variable
     * @param  oxa (float)
     */
    public int setOxa(float oxa) {
        try {
            if ( ((oxa == FLOATNULL) || 
                  (oxa == FLOATNULL2)) ||
                !((oxa < OXA_MN) ||
                  (oxa > OXA_MX)) ) {
                this.oxa = oxa;
                oxaError = ERROR_NORMAL;
            } else {
                throw oxaOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setOxaError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return oxaError;
    } // method setOxa

    /**
     * Set the 'oxa' class variable
     * @param  oxa (Integer)
     */
    public int setOxa(Integer oxa) {
        try {
            setOxa(oxa.floatValue());
        } catch (Exception e) {
            setOxaError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return oxaError;
    } // method setOxa

    /**
     * Set the 'oxa' class variable
     * @param  oxa (Float)
     */
    public int setOxa(Float oxa) {
        try {
            setOxa(oxa.floatValue());
        } catch (Exception e) {
            setOxaError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return oxaError;
    } // method setOxa

    /**
     * Set the 'oxa' class variable
     * @param  oxa (String)
     */
    public int setOxa(String oxa) {
        try {
            setOxa(new Float(oxa).floatValue());
        } catch (Exception e) {
            setOxaError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return oxaError;
    } // method setOxa

    /**
     * Called when an exception has occured
     * @param  oxa (String)
     */
    private void setOxaError (float oxa, Exception e, int error) {
        this.oxa = oxa;
        oxaErrorMessage = e.toString();
        oxaError = error;
    } // method setOxaError


    /**
     * Set the 'toc' class variable
     * @param  toc (float)
     */
    public int setToc(float toc) {
        try {
            if ( ((toc == FLOATNULL) || 
                  (toc == FLOATNULL2)) ||
                !((toc < TOC_MN) ||
                  (toc > TOC_MX)) ) {
                this.toc = toc;
                tocError = ERROR_NORMAL;
            } else {
                throw tocOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTocError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return tocError;
    } // method setToc

    /**
     * Set the 'toc' class variable
     * @param  toc (Integer)
     */
    public int setToc(Integer toc) {
        try {
            setToc(toc.floatValue());
        } catch (Exception e) {
            setTocError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tocError;
    } // method setToc

    /**
     * Set the 'toc' class variable
     * @param  toc (Float)
     */
    public int setToc(Float toc) {
        try {
            setToc(toc.floatValue());
        } catch (Exception e) {
            setTocError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tocError;
    } // method setToc

    /**
     * Set the 'toc' class variable
     * @param  toc (String)
     */
    public int setToc(String toc) {
        try {
            setToc(new Float(toc).floatValue());
        } catch (Exception e) {
            setTocError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tocError;
    } // method setToc

    /**
     * Called when an exception has occured
     * @param  toc (String)
     */
    private void setTocError (float toc, Exception e, int error) {
        this.toc = toc;
        tocErrorMessage = e.toString();
        tocError = error;
    } // method setTocError


    /**
     * Set the 'ptot' class variable
     * @param  ptot (float)
     */
    public int setPtot(float ptot) {
        try {
            if ( ((ptot == FLOATNULL) || 
                  (ptot == FLOATNULL2)) ||
                !((ptot < PTOT_MN) ||
                  (ptot > PTOT_MX)) ) {
                this.ptot = ptot;
                ptotError = ERROR_NORMAL;
            } else {
                throw ptotOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPtotError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return ptotError;
    } // method setPtot

    /**
     * Set the 'ptot' class variable
     * @param  ptot (Integer)
     */
    public int setPtot(Integer ptot) {
        try {
            setPtot(ptot.floatValue());
        } catch (Exception e) {
            setPtotError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ptotError;
    } // method setPtot

    /**
     * Set the 'ptot' class variable
     * @param  ptot (Float)
     */
    public int setPtot(Float ptot) {
        try {
            setPtot(ptot.floatValue());
        } catch (Exception e) {
            setPtotError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ptotError;
    } // method setPtot

    /**
     * Set the 'ptot' class variable
     * @param  ptot (String)
     */
    public int setPtot(String ptot) {
        try {
            setPtot(new Float(ptot).floatValue());
        } catch (Exception e) {
            setPtotError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ptotError;
    } // method setPtot

    /**
     * Called when an exception has occured
     * @param  ptot (String)
     */
    private void setPtotError (float ptot, Exception e, int error) {
        this.ptot = ptot;
        ptotErrorMessage = e.toString();
        ptotError = error;
    } // method setPtotError


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
     * Return the 'fluoride' class variable
     * @return fluoride (float)
     */
    public float getFluoride() {
        return fluoride;
    } // method getFluoride

    /**
     * Return the 'fluoride' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFluoride methods
     * @return fluoride (String)
     */
    public String getFluoride(String s) {
        return ((fluoride != FLOATNULL) ? new Float(fluoride).toString() : "");
    } // method getFluoride


    /**
     * Return the 'kjn' class variable
     * @return kjn (float)
     */
    public float getKjn() {
        return kjn;
    } // method getKjn

    /**
     * Return the 'kjn' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getKjn methods
     * @return kjn (String)
     */
    public String getKjn(String s) {
        return ((kjn != FLOATNULL) ? new Float(kjn).toString() : "");
    } // method getKjn


    /**
     * Return the 'oxa' class variable
     * @return oxa (float)
     */
    public float getOxa() {
        return oxa;
    } // method getOxa

    /**
     * Return the 'oxa' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getOxa methods
     * @return oxa (String)
     */
    public String getOxa(String s) {
        return ((oxa != FLOATNULL) ? new Float(oxa).toString() : "");
    } // method getOxa


    /**
     * Return the 'toc' class variable
     * @return toc (float)
     */
    public float getToc() {
        return toc;
    } // method getToc

    /**
     * Return the 'toc' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getToc methods
     * @return toc (String)
     */
    public String getToc(String s) {
        return ((toc != FLOATNULL) ? new Float(toc).toString() : "");
    } // method getToc


    /**
     * Return the 'ptot' class variable
     * @return ptot (float)
     */
    public float getPtot() {
        return ptot;
    } // method getPtot

    /**
     * Return the 'ptot' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPtot methods
     * @return ptot (String)
     */
    public String getPtot(String s) {
        return ((ptot != FLOATNULL) ? new Float(ptot).toString() : "");
    } // method getPtot


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
            (fluoride == FLOATNULL) &&
            (kjn == FLOATNULL) &&
            (oxa == FLOATNULL) &&
            (toc == FLOATNULL) &&
            (ptot == FLOATNULL)) {
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
            fluorideError +
            kjnError +
            oxaError +
            tocError +
            ptotError;
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
     * Gets the errorcode for the fluoride instance variable
     * @return errorcode (int)
     */
    public int getFluorideError() {
        return fluorideError;
    } // method getFluorideError

    /**
     * Gets the errorMessage for the fluoride instance variable
     * @return errorMessage (String)
     */
    public String getFluorideErrorMessage() {
        return fluorideErrorMessage;
    } // method getFluorideErrorMessage


    /**
     * Gets the errorcode for the kjn instance variable
     * @return errorcode (int)
     */
    public int getKjnError() {
        return kjnError;
    } // method getKjnError

    /**
     * Gets the errorMessage for the kjn instance variable
     * @return errorMessage (String)
     */
    public String getKjnErrorMessage() {
        return kjnErrorMessage;
    } // method getKjnErrorMessage


    /**
     * Gets the errorcode for the oxa instance variable
     * @return errorcode (int)
     */
    public int getOxaError() {
        return oxaError;
    } // method getOxaError

    /**
     * Gets the errorMessage for the oxa instance variable
     * @return errorMessage (String)
     */
    public String getOxaErrorMessage() {
        return oxaErrorMessage;
    } // method getOxaErrorMessage


    /**
     * Gets the errorcode for the toc instance variable
     * @return errorcode (int)
     */
    public int getTocError() {
        return tocError;
    } // method getTocError

    /**
     * Gets the errorMessage for the toc instance variable
     * @return errorMessage (String)
     */
    public String getTocErrorMessage() {
        return tocErrorMessage;
    } // method getTocErrorMessage


    /**
     * Gets the errorcode for the ptot instance variable
     * @return errorcode (int)
     */
    public int getPtotError() {
        return ptotError;
    } // method getPtotError

    /**
     * Gets the errorMessage for the ptot instance variable
     * @return errorMessage (String)
     */
    public String getPtotErrorMessage() {
        return ptotErrorMessage;
    } // method getPtotErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnSedchem1. e.g.<pre>
     * MrnSedchem1 sedchem1 = new MrnSedchem1(<val1>);
     * MrnSedchem1 sedchem1Array[] = sedchem1.get();</pre>
     * will get the MrnSedchem1 record where sedphyCode = <val1>.
     * @return Array of MrnSedchem1 (MrnSedchem1[])
     */
    public MrnSedchem1[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnSedchem1 sedchem1Array[] = 
     *     new MrnSedchem1().get(MrnSedchem1.SEDPHY_CODE+"=<val1>");</pre>
     * will get the MrnSedchem1 record where sedphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnSedchem1 (MrnSedchem1[])
     */
    public MrnSedchem1[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnSedchem1 sedchem1Array[] = 
     *     new MrnSedchem1().get("1=1",sedchem1.SEDPHY_CODE);</pre>
     * will get the all the MrnSedchem1 records, and order them by sedphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSedchem1 (MrnSedchem1[])
     */
    public MrnSedchem1[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnSedchem1.SEDPHY_CODE,MrnSedchem1.FLUORIDE;
     * String where = MrnSedchem1.SEDPHY_CODE + "=<val1";
     * String order = MrnSedchem1.SEDPHY_CODE;
     * MrnSedchem1 sedchem1Array[] = 
     *     new MrnSedchem1().get(columns, where, order);</pre>
     * will get the sedphyCode and fluoride colums of all MrnSedchem1 records,
     * where sedphyCode = <val1>, and order them by sedphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSedchem1 (MrnSedchem1[])
     */
    public MrnSedchem1[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnSedchem1.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnSedchem1[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int sedphyCodeCol = db.getColNumber(SEDPHY_CODE);
        int fluorideCol   = db.getColNumber(FLUORIDE);
        int kjnCol        = db.getColNumber(KJN);
        int oxaCol        = db.getColNumber(OXA);
        int tocCol        = db.getColNumber(TOC);
        int ptotCol       = db.getColNumber(PTOT);
        MrnSedchem1[] cArray = new MrnSedchem1[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnSedchem1();
            if (sedphyCodeCol != -1)
                cArray[i].setSedphyCode((String) row.elementAt(sedphyCodeCol));
            if (fluorideCol != -1)
                cArray[i].setFluoride  ((String) row.elementAt(fluorideCol));
            if (kjnCol != -1)
                cArray[i].setKjn       ((String) row.elementAt(kjnCol));
            if (oxaCol != -1)
                cArray[i].setOxa       ((String) row.elementAt(oxaCol));
            if (tocCol != -1)
                cArray[i].setToc       ((String) row.elementAt(tocCol));
            if (ptotCol != -1)
                cArray[i].setPtot      ((String) row.elementAt(ptotCol));
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
     *     new MrnSedchem1(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>).put();</pre>
     * will insert a record with:
     *     sedphyCode = <val1>,
     *     fluoride   = <val2>,
     *     kjn        = <val3>,
     *     oxa        = <val4>,
     *     toc        = <val5>,
     *     ptot       = <val6>.
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
     * Boolean success = new MrnSedchem1(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where fluoride = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSedchem1 sedchem1 = new MrnSedchem1();
     * success = sedchem1.del(MrnSedchem1.SEDPHY_CODE+"=<val1>");</pre>
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
     * update are taken from the MrnSedchem1 argument, .e.g.<pre>
     * Boolean success
     * MrnSedchem1 updMrnSedchem1 = new MrnSedchem1();
     * updMrnSedchem1.setFluoride(<val2>);
     * MrnSedchem1 whereMrnSedchem1 = new MrnSedchem1(<val1>);
     * success = whereMrnSedchem1.upd(updMrnSedchem1);</pre>
     * will update Fluoride to <val2> for all records where 
     * sedphyCode = <val1>.
     * @param sedchem1  A MrnSedchem1 variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSedchem1 sedchem1) {
        return db.update (TABLE, createColVals(sedchem1), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSedchem1 updMrnSedchem1 = new MrnSedchem1();
     * updMrnSedchem1.setFluoride(<val2>);
     * MrnSedchem1 whereMrnSedchem1 = new MrnSedchem1();
     * success = whereMrnSedchem1.upd(
     *     updMrnSedchem1, MrnSedchem1.SEDPHY_CODE+"=<val1>");</pre>
     * will update Fluoride to <val2> for all records where 
     * sedphyCode = <val1>.
     * @param  updMrnSedchem1  A MrnSedchem1 variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSedchem1 sedchem1, String where) {
        return db.update (TABLE, createColVals(sedchem1), where);
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
        if (getFluoride() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FLUORIDE + "=" + getFluoride("");
        } // if getFluoride
        if (getKjn() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + KJN + "=" + getKjn("");
        } // if getKjn
        if (getOxa() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + OXA + "=" + getOxa("");
        } // if getOxa
        if (getToc() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TOC + "=" + getToc("");
        } // if getToc
        if (getPtot() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PTOT + "=" + getPtot("");
        } // if getPtot
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnSedchem1 aVar) {
        String colVals = "";
        if (aVar.getSedphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPHY_CODE +"=";
            colVals += (aVar.getSedphyCode() == INTNULL2 ?
                "null" : aVar.getSedphyCode(""));
        } // if aVar.getSedphyCode
        if (aVar.getFluoride() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FLUORIDE +"=";
            colVals += (aVar.getFluoride() == FLOATNULL2 ?
                "null" : aVar.getFluoride(""));
        } // if aVar.getFluoride
        if (aVar.getKjn() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += KJN +"=";
            colVals += (aVar.getKjn() == FLOATNULL2 ?
                "null" : aVar.getKjn(""));
        } // if aVar.getKjn
        if (aVar.getOxa() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += OXA +"=";
            colVals += (aVar.getOxa() == FLOATNULL2 ?
                "null" : aVar.getOxa(""));
        } // if aVar.getOxa
        if (aVar.getToc() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TOC +"=";
            colVals += (aVar.getToc() == FLOATNULL2 ?
                "null" : aVar.getToc(""));
        } // if aVar.getToc
        if (aVar.getPtot() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PTOT +"=";
            colVals += (aVar.getPtot() == FLOATNULL2 ?
                "null" : aVar.getPtot(""));
        } // if aVar.getPtot
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SEDPHY_CODE;
        if (getFluoride() != FLOATNULL) {
            columns = columns + "," + FLUORIDE;
        } // if getFluoride
        if (getKjn() != FLOATNULL) {
            columns = columns + "," + KJN;
        } // if getKjn
        if (getOxa() != FLOATNULL) {
            columns = columns + "," + OXA;
        } // if getOxa
        if (getToc() != FLOATNULL) {
            columns = columns + "," + TOC;
        } // if getToc
        if (getPtot() != FLOATNULL) {
            columns = columns + "," + PTOT;
        } // if getPtot
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getSedphyCode("");
        if (getFluoride() != FLOATNULL) {
            values  = values  + "," + getFluoride("");
        } // if getFluoride
        if (getKjn() != FLOATNULL) {
            values  = values  + "," + getKjn("");
        } // if getKjn
        if (getOxa() != FLOATNULL) {
            values  = values  + "," + getOxa("");
        } // if getOxa
        if (getToc() != FLOATNULL) {
            values  = values  + "," + getToc("");
        } // if getToc
        if (getPtot() != FLOATNULL) {
            values  = values  + "," + getPtot("");
        } // if getPtot
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
            getFluoride("")   + "|" +
            getKjn("")        + "|" +
            getOxa("")        + "|" +
            getToc("")        + "|" +
            getPtot("")       + "|";
    } // method toString

} // class MrnSedchem1
