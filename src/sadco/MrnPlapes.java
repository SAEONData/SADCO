package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the PLAPES table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 090108 - SIT Group
 * @version
 * 090108 - GenTableClassB class - create class<br>
 */
public class MrnPlapes extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "PLAPES";
    /** The plaphyCode field name */
    public static final String PLAPHY_CODE = "PLAPHY_CODE";
    /** The dde field name */
    public static final String DDE = "DDE";
    /** The ddt field name */
    public static final String DDT = "DDT";
    /** The dieldrin field name */
    public static final String DIELDRIN = "DIELDRIN";
    /** The lindane field name */
    public static final String LINDANE = "LINDANE";
    /** The pcb field name */
    public static final String PCB = "PCB";
    /** The tde field name */
    public static final String TDE = "TDE";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       plaphyCode;
    private float     dde;
    private float     ddt;
    private float     dieldrin;
    private float     lindane;
    private float     pcb;
    private float     tde;

    /** The error variables  */
    private int plaphyCodeError = ERROR_NORMAL;
    private int ddeError        = ERROR_NORMAL;
    private int ddtError        = ERROR_NORMAL;
    private int dieldrinError   = ERROR_NORMAL;
    private int lindaneError    = ERROR_NORMAL;
    private int pcbError        = ERROR_NORMAL;
    private int tdeError        = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String plaphyCodeErrorMessage = "";
    private String ddeErrorMessage        = "";
    private String ddtErrorMessage        = "";
    private String dieldrinErrorMessage   = "";
    private String lindaneErrorMessage    = "";
    private String pcbErrorMessage        = "";
    private String tdeErrorMessage        = "";

    /** The min-max constants for all numerics */
    public static final int       PLAPHY_CODE_MN = INTMIN;
    public static final int       PLAPHY_CODE_MX = INTMAX;
    public static final float     DDE_MN = FLOATMIN;
    public static final float     DDE_MX = FLOATMAX;
    public static final float     DDT_MN = FLOATMIN;
    public static final float     DDT_MX = FLOATMAX;
    public static final float     DIELDRIN_MN = FLOATMIN;
    public static final float     DIELDRIN_MX = FLOATMAX;
    public static final float     LINDANE_MN = FLOATMIN;
    public static final float     LINDANE_MX = FLOATMAX;
    public static final float     PCB_MN = FLOATMIN;
    public static final float     PCB_MX = FLOATMAX;
    public static final float     TDE_MN = FLOATMIN;
    public static final float     TDE_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception plaphyCodeOutOfBoundsException =
        new Exception ("'plaphyCode' out of bounds: " +
            PLAPHY_CODE_MN + " - " + PLAPHY_CODE_MX);
    Exception ddeOutOfBoundsException =
        new Exception ("'dde' out of bounds: " +
            DDE_MN + " - " + DDE_MX);
    Exception ddtOutOfBoundsException =
        new Exception ("'ddt' out of bounds: " +
            DDT_MN + " - " + DDT_MX);
    Exception dieldrinOutOfBoundsException =
        new Exception ("'dieldrin' out of bounds: " +
            DIELDRIN_MN + " - " + DIELDRIN_MX);
    Exception lindaneOutOfBoundsException =
        new Exception ("'lindane' out of bounds: " +
            LINDANE_MN + " - " + LINDANE_MX);
    Exception pcbOutOfBoundsException =
        new Exception ("'pcb' out of bounds: " +
            PCB_MN + " - " + PCB_MX);
    Exception tdeOutOfBoundsException =
        new Exception ("'tde' out of bounds: " +
            TDE_MN + " - " + TDE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnPlapes() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnPlapes constructor 1"); // debug
    } // MrnPlapes constructor

    /**
     * Instantiate a MrnPlapes object and initialize the instance variables.
     * @param plaphyCode  The plaphyCode (int)
     */
    public MrnPlapes(
            int plaphyCode) {
        this();
        setPlaphyCode (plaphyCode);
        if (dbg) System.out.println ("<br>in MrnPlapes constructor 2"); // debug
    } // MrnPlapes constructor

    /**
     * Instantiate a MrnPlapes object and initialize the instance variables.
     * @param plaphyCode  The plaphyCode (int)
     * @param dde         The dde        (float)
     * @param ddt         The ddt        (float)
     * @param dieldrin    The dieldrin   (float)
     * @param lindane     The lindane    (float)
     * @param pcb         The pcb        (float)
     * @param tde         The tde        (float)
     * @return A MrnPlapes object
     */
    public MrnPlapes(
            int   plaphyCode,
            float dde,
            float ddt,
            float dieldrin,
            float lindane,
            float pcb,
            float tde) {
        this();
        setPlaphyCode (plaphyCode);
        setDde        (dde       );
        setDdt        (ddt       );
        setDieldrin   (dieldrin  );
        setLindane    (lindane   );
        setPcb        (pcb       );
        setTde        (tde       );
        if (dbg) System.out.println ("<br>in MrnPlapes constructor 3"); // debug
    } // MrnPlapes constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setPlaphyCode (INTNULL  );
        setDde        (FLOATNULL);
        setDdt        (FLOATNULL);
        setDieldrin   (FLOATNULL);
        setLindane    (FLOATNULL);
        setPcb        (FLOATNULL);
        setTde        (FLOATNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'plaphyCode' class variable
     * @param  plaphyCode (int)
     */
    public int setPlaphyCode(int plaphyCode) {
        try {
            if ( ((plaphyCode == INTNULL) || 
                  (plaphyCode == INTNULL2)) ||
                !((plaphyCode < PLAPHY_CODE_MN) ||
                  (plaphyCode > PLAPHY_CODE_MX)) ) {
                this.plaphyCode = plaphyCode;
                plaphyCodeError = ERROR_NORMAL;
            } else {
                throw plaphyCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlaphyCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return plaphyCodeError;
    } // method setPlaphyCode

    /**
     * Set the 'plaphyCode' class variable
     * @param  plaphyCode (Integer)
     */
    public int setPlaphyCode(Integer plaphyCode) {
        try {
            setPlaphyCode(plaphyCode.intValue());
        } catch (Exception e) {
            setPlaphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCodeError;
    } // method setPlaphyCode

    /**
     * Set the 'plaphyCode' class variable
     * @param  plaphyCode (Float)
     */
    public int setPlaphyCode(Float plaphyCode) {
        try {
            if (plaphyCode.floatValue() == FLOATNULL) {
                setPlaphyCode(INTNULL);
            } else {
                setPlaphyCode(plaphyCode.intValue());
            } // if (plaphyCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlaphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCodeError;
    } // method setPlaphyCode

    /**
     * Set the 'plaphyCode' class variable
     * @param  plaphyCode (String)
     */
    public int setPlaphyCode(String plaphyCode) {
        try {
            setPlaphyCode(new Integer(plaphyCode).intValue());
        } catch (Exception e) {
            setPlaphyCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return plaphyCodeError;
    } // method setPlaphyCode

    /**
     * Called when an exception has occured
     * @param  plaphyCode (String)
     */
    private void setPlaphyCodeError (int plaphyCode, Exception e, int error) {
        this.plaphyCode = plaphyCode;
        plaphyCodeErrorMessage = e.toString();
        plaphyCodeError = error;
    } // method setPlaphyCodeError


    /**
     * Set the 'dde' class variable
     * @param  dde (float)
     */
    public int setDde(float dde) {
        try {
            if ( ((dde == FLOATNULL) || 
                  (dde == FLOATNULL2)) ||
                !((dde < DDE_MN) ||
                  (dde > DDE_MX)) ) {
                this.dde = dde;
                ddeError = ERROR_NORMAL;
            } else {
                throw ddeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDdeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return ddeError;
    } // method setDde

    /**
     * Set the 'dde' class variable
     * @param  dde (Integer)
     */
    public int setDde(Integer dde) {
        try {
            setDde(dde.floatValue());
        } catch (Exception e) {
            setDdeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ddeError;
    } // method setDde

    /**
     * Set the 'dde' class variable
     * @param  dde (Float)
     */
    public int setDde(Float dde) {
        try {
            setDde(dde.floatValue());
        } catch (Exception e) {
            setDdeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ddeError;
    } // method setDde

    /**
     * Set the 'dde' class variable
     * @param  dde (String)
     */
    public int setDde(String dde) {
        try {
            setDde(new Float(dde).floatValue());
        } catch (Exception e) {
            setDdeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ddeError;
    } // method setDde

    /**
     * Called when an exception has occured
     * @param  dde (String)
     */
    private void setDdeError (float dde, Exception e, int error) {
        this.dde = dde;
        ddeErrorMessage = e.toString();
        ddeError = error;
    } // method setDdeError


    /**
     * Set the 'ddt' class variable
     * @param  ddt (float)
     */
    public int setDdt(float ddt) {
        try {
            if ( ((ddt == FLOATNULL) || 
                  (ddt == FLOATNULL2)) ||
                !((ddt < DDT_MN) ||
                  (ddt > DDT_MX)) ) {
                this.ddt = ddt;
                ddtError = ERROR_NORMAL;
            } else {
                throw ddtOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDdtError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return ddtError;
    } // method setDdt

    /**
     * Set the 'ddt' class variable
     * @param  ddt (Integer)
     */
    public int setDdt(Integer ddt) {
        try {
            setDdt(ddt.floatValue());
        } catch (Exception e) {
            setDdtError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ddtError;
    } // method setDdt

    /**
     * Set the 'ddt' class variable
     * @param  ddt (Float)
     */
    public int setDdt(Float ddt) {
        try {
            setDdt(ddt.floatValue());
        } catch (Exception e) {
            setDdtError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ddtError;
    } // method setDdt

    /**
     * Set the 'ddt' class variable
     * @param  ddt (String)
     */
    public int setDdt(String ddt) {
        try {
            setDdt(new Float(ddt).floatValue());
        } catch (Exception e) {
            setDdtError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ddtError;
    } // method setDdt

    /**
     * Called when an exception has occured
     * @param  ddt (String)
     */
    private void setDdtError (float ddt, Exception e, int error) {
        this.ddt = ddt;
        ddtErrorMessage = e.toString();
        ddtError = error;
    } // method setDdtError


    /**
     * Set the 'dieldrin' class variable
     * @param  dieldrin (float)
     */
    public int setDieldrin(float dieldrin) {
        try {
            if ( ((dieldrin == FLOATNULL) || 
                  (dieldrin == FLOATNULL2)) ||
                !((dieldrin < DIELDRIN_MN) ||
                  (dieldrin > DIELDRIN_MX)) ) {
                this.dieldrin = dieldrin;
                dieldrinError = ERROR_NORMAL;
            } else {
                throw dieldrinOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDieldrinError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return dieldrinError;
    } // method setDieldrin

    /**
     * Set the 'dieldrin' class variable
     * @param  dieldrin (Integer)
     */
    public int setDieldrin(Integer dieldrin) {
        try {
            setDieldrin(dieldrin.floatValue());
        } catch (Exception e) {
            setDieldrinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dieldrinError;
    } // method setDieldrin

    /**
     * Set the 'dieldrin' class variable
     * @param  dieldrin (Float)
     */
    public int setDieldrin(Float dieldrin) {
        try {
            setDieldrin(dieldrin.floatValue());
        } catch (Exception e) {
            setDieldrinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dieldrinError;
    } // method setDieldrin

    /**
     * Set the 'dieldrin' class variable
     * @param  dieldrin (String)
     */
    public int setDieldrin(String dieldrin) {
        try {
            setDieldrin(new Float(dieldrin).floatValue());
        } catch (Exception e) {
            setDieldrinError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dieldrinError;
    } // method setDieldrin

    /**
     * Called when an exception has occured
     * @param  dieldrin (String)
     */
    private void setDieldrinError (float dieldrin, Exception e, int error) {
        this.dieldrin = dieldrin;
        dieldrinErrorMessage = e.toString();
        dieldrinError = error;
    } // method setDieldrinError


    /**
     * Set the 'lindane' class variable
     * @param  lindane (float)
     */
    public int setLindane(float lindane) {
        try {
            if ( ((lindane == FLOATNULL) || 
                  (lindane == FLOATNULL2)) ||
                !((lindane < LINDANE_MN) ||
                  (lindane > LINDANE_MX)) ) {
                this.lindane = lindane;
                lindaneError = ERROR_NORMAL;
            } else {
                throw lindaneOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLindaneError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return lindaneError;
    } // method setLindane

    /**
     * Set the 'lindane' class variable
     * @param  lindane (Integer)
     */
    public int setLindane(Integer lindane) {
        try {
            setLindane(lindane.floatValue());
        } catch (Exception e) {
            setLindaneError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return lindaneError;
    } // method setLindane

    /**
     * Set the 'lindane' class variable
     * @param  lindane (Float)
     */
    public int setLindane(Float lindane) {
        try {
            setLindane(lindane.floatValue());
        } catch (Exception e) {
            setLindaneError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return lindaneError;
    } // method setLindane

    /**
     * Set the 'lindane' class variable
     * @param  lindane (String)
     */
    public int setLindane(String lindane) {
        try {
            setLindane(new Float(lindane).floatValue());
        } catch (Exception e) {
            setLindaneError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return lindaneError;
    } // method setLindane

    /**
     * Called when an exception has occured
     * @param  lindane (String)
     */
    private void setLindaneError (float lindane, Exception e, int error) {
        this.lindane = lindane;
        lindaneErrorMessage = e.toString();
        lindaneError = error;
    } // method setLindaneError


    /**
     * Set the 'pcb' class variable
     * @param  pcb (float)
     */
    public int setPcb(float pcb) {
        try {
            if ( ((pcb == FLOATNULL) || 
                  (pcb == FLOATNULL2)) ||
                !((pcb < PCB_MN) ||
                  (pcb > PCB_MX)) ) {
                this.pcb = pcb;
                pcbError = ERROR_NORMAL;
            } else {
                throw pcbOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPcbError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return pcbError;
    } // method setPcb

    /**
     * Set the 'pcb' class variable
     * @param  pcb (Integer)
     */
    public int setPcb(Integer pcb) {
        try {
            setPcb(pcb.floatValue());
        } catch (Exception e) {
            setPcbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pcbError;
    } // method setPcb

    /**
     * Set the 'pcb' class variable
     * @param  pcb (Float)
     */
    public int setPcb(Float pcb) {
        try {
            setPcb(pcb.floatValue());
        } catch (Exception e) {
            setPcbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pcbError;
    } // method setPcb

    /**
     * Set the 'pcb' class variable
     * @param  pcb (String)
     */
    public int setPcb(String pcb) {
        try {
            setPcb(new Float(pcb).floatValue());
        } catch (Exception e) {
            setPcbError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pcbError;
    } // method setPcb

    /**
     * Called when an exception has occured
     * @param  pcb (String)
     */
    private void setPcbError (float pcb, Exception e, int error) {
        this.pcb = pcb;
        pcbErrorMessage = e.toString();
        pcbError = error;
    } // method setPcbError


    /**
     * Set the 'tde' class variable
     * @param  tde (float)
     */
    public int setTde(float tde) {
        try {
            if ( ((tde == FLOATNULL) || 
                  (tde == FLOATNULL2)) ||
                !((tde < TDE_MN) ||
                  (tde > TDE_MX)) ) {
                this.tde = tde;
                tdeError = ERROR_NORMAL;
            } else {
                throw tdeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTdeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return tdeError;
    } // method setTde

    /**
     * Set the 'tde' class variable
     * @param  tde (Integer)
     */
    public int setTde(Integer tde) {
        try {
            setTde(tde.floatValue());
        } catch (Exception e) {
            setTdeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tdeError;
    } // method setTde

    /**
     * Set the 'tde' class variable
     * @param  tde (Float)
     */
    public int setTde(Float tde) {
        try {
            setTde(tde.floatValue());
        } catch (Exception e) {
            setTdeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tdeError;
    } // method setTde

    /**
     * Set the 'tde' class variable
     * @param  tde (String)
     */
    public int setTde(String tde) {
        try {
            setTde(new Float(tde).floatValue());
        } catch (Exception e) {
            setTdeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return tdeError;
    } // method setTde

    /**
     * Called when an exception has occured
     * @param  tde (String)
     */
    private void setTdeError (float tde, Exception e, int error) {
        this.tde = tde;
        tdeErrorMessage = e.toString();
        tdeError = error;
    } // method setTdeError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'plaphyCode' class variable
     * @return plaphyCode (int)
     */
    public int getPlaphyCode() {
        return plaphyCode;
    } // method getPlaphyCode

    /**
     * Return the 'plaphyCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlaphyCode methods
     * @return plaphyCode (String)
     */
    public String getPlaphyCode(String s) {
        return ((plaphyCode != INTNULL) ? new Integer(plaphyCode).toString() : "");
    } // method getPlaphyCode


    /**
     * Return the 'dde' class variable
     * @return dde (float)
     */
    public float getDde() {
        return dde;
    } // method getDde

    /**
     * Return the 'dde' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDde methods
     * @return dde (String)
     */
    public String getDde(String s) {
        return ((dde != FLOATNULL) ? new Float(dde).toString() : "");
    } // method getDde


    /**
     * Return the 'ddt' class variable
     * @return ddt (float)
     */
    public float getDdt() {
        return ddt;
    } // method getDdt

    /**
     * Return the 'ddt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDdt methods
     * @return ddt (String)
     */
    public String getDdt(String s) {
        return ((ddt != FLOATNULL) ? new Float(ddt).toString() : "");
    } // method getDdt


    /**
     * Return the 'dieldrin' class variable
     * @return dieldrin (float)
     */
    public float getDieldrin() {
        return dieldrin;
    } // method getDieldrin

    /**
     * Return the 'dieldrin' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDieldrin methods
     * @return dieldrin (String)
     */
    public String getDieldrin(String s) {
        return ((dieldrin != FLOATNULL) ? new Float(dieldrin).toString() : "");
    } // method getDieldrin


    /**
     * Return the 'lindane' class variable
     * @return lindane (float)
     */
    public float getLindane() {
        return lindane;
    } // method getLindane

    /**
     * Return the 'lindane' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLindane methods
     * @return lindane (String)
     */
    public String getLindane(String s) {
        return ((lindane != FLOATNULL) ? new Float(lindane).toString() : "");
    } // method getLindane


    /**
     * Return the 'pcb' class variable
     * @return pcb (float)
     */
    public float getPcb() {
        return pcb;
    } // method getPcb

    /**
     * Return the 'pcb' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPcb methods
     * @return pcb (String)
     */
    public String getPcb(String s) {
        return ((pcb != FLOATNULL) ? new Float(pcb).toString() : "");
    } // method getPcb


    /**
     * Return the 'tde' class variable
     * @return tde (float)
     */
    public float getTde() {
        return tde;
    } // method getTde

    /**
     * Return the 'tde' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTde methods
     * @return tde (String)
     */
    public String getTde(String s) {
        return ((tde != FLOATNULL) ? new Float(tde).toString() : "");
    } // method getTde


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
        if ((plaphyCode == INTNULL) &&
            (dde == FLOATNULL) &&
            (ddt == FLOATNULL) &&
            (dieldrin == FLOATNULL) &&
            (lindane == FLOATNULL) &&
            (pcb == FLOATNULL) &&
            (tde == FLOATNULL)) {
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
        int sumError = plaphyCodeError +
            ddeError +
            ddtError +
            dieldrinError +
            lindaneError +
            pcbError +
            tdeError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the plaphyCode instance variable
     * @return errorcode (int)
     */
    public int getPlaphyCodeError() {
        return plaphyCodeError;
    } // method getPlaphyCodeError

    /**
     * Gets the errorMessage for the plaphyCode instance variable
     * @return errorMessage (String)
     */
    public String getPlaphyCodeErrorMessage() {
        return plaphyCodeErrorMessage;
    } // method getPlaphyCodeErrorMessage


    /**
     * Gets the errorcode for the dde instance variable
     * @return errorcode (int)
     */
    public int getDdeError() {
        return ddeError;
    } // method getDdeError

    /**
     * Gets the errorMessage for the dde instance variable
     * @return errorMessage (String)
     */
    public String getDdeErrorMessage() {
        return ddeErrorMessage;
    } // method getDdeErrorMessage


    /**
     * Gets the errorcode for the ddt instance variable
     * @return errorcode (int)
     */
    public int getDdtError() {
        return ddtError;
    } // method getDdtError

    /**
     * Gets the errorMessage for the ddt instance variable
     * @return errorMessage (String)
     */
    public String getDdtErrorMessage() {
        return ddtErrorMessage;
    } // method getDdtErrorMessage


    /**
     * Gets the errorcode for the dieldrin instance variable
     * @return errorcode (int)
     */
    public int getDieldrinError() {
        return dieldrinError;
    } // method getDieldrinError

    /**
     * Gets the errorMessage for the dieldrin instance variable
     * @return errorMessage (String)
     */
    public String getDieldrinErrorMessage() {
        return dieldrinErrorMessage;
    } // method getDieldrinErrorMessage


    /**
     * Gets the errorcode for the lindane instance variable
     * @return errorcode (int)
     */
    public int getLindaneError() {
        return lindaneError;
    } // method getLindaneError

    /**
     * Gets the errorMessage for the lindane instance variable
     * @return errorMessage (String)
     */
    public String getLindaneErrorMessage() {
        return lindaneErrorMessage;
    } // method getLindaneErrorMessage


    /**
     * Gets the errorcode for the pcb instance variable
     * @return errorcode (int)
     */
    public int getPcbError() {
        return pcbError;
    } // method getPcbError

    /**
     * Gets the errorMessage for the pcb instance variable
     * @return errorMessage (String)
     */
    public String getPcbErrorMessage() {
        return pcbErrorMessage;
    } // method getPcbErrorMessage


    /**
     * Gets the errorcode for the tde instance variable
     * @return errorcode (int)
     */
    public int getTdeError() {
        return tdeError;
    } // method getTdeError

    /**
     * Gets the errorMessage for the tde instance variable
     * @return errorMessage (String)
     */
    public String getTdeErrorMessage() {
        return tdeErrorMessage;
    } // method getTdeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnPlapes. e.g.<pre>
     * MrnPlapes plapes = new MrnPlapes(<val1>);
     * MrnPlapes plapesArray[] = plapes.get();</pre>
     * will get the MrnPlapes record where plaphyCode = <val1>.
     * @return Array of MrnPlapes (MrnPlapes[])
     */
    public MrnPlapes[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnPlapes plapesArray[] = 
     *     new MrnPlapes().get(MrnPlapes.PLAPHY_CODE+"=<val1>");</pre>
     * will get the MrnPlapes record where plaphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnPlapes (MrnPlapes[])
     */
    public MrnPlapes[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnPlapes plapesArray[] = 
     *     new MrnPlapes().get("1=1",plapes.PLAPHY_CODE);</pre>
     * will get the all the MrnPlapes records, and order them by plaphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlapes (MrnPlapes[])
     */
    public MrnPlapes[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnPlapes.PLAPHY_CODE,MrnPlapes.DDE;
     * String where = MrnPlapes.PLAPHY_CODE + "=<val1";
     * String order = MrnPlapes.PLAPHY_CODE;
     * MrnPlapes plapesArray[] = 
     *     new MrnPlapes().get(columns, where, order);</pre>
     * will get the plaphyCode and dde colums of all MrnPlapes records,
     * where plaphyCode = <val1>, and order them by plaphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlapes (MrnPlapes[])
     */
    public MrnPlapes[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnPlapes.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnPlapes[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int plaphyCodeCol = db.getColNumber(PLAPHY_CODE);
        int ddeCol        = db.getColNumber(DDE);
        int ddtCol        = db.getColNumber(DDT);
        int dieldrinCol   = db.getColNumber(DIELDRIN);
        int lindaneCol    = db.getColNumber(LINDANE);
        int pcbCol        = db.getColNumber(PCB);
        int tdeCol        = db.getColNumber(TDE);
        MrnPlapes[] cArray = new MrnPlapes[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnPlapes();
            if (plaphyCodeCol != -1)
                cArray[i].setPlaphyCode((String) row.elementAt(plaphyCodeCol));
            if (ddeCol != -1)
                cArray[i].setDde       ((String) row.elementAt(ddeCol));
            if (ddtCol != -1)
                cArray[i].setDdt       ((String) row.elementAt(ddtCol));
            if (dieldrinCol != -1)
                cArray[i].setDieldrin  ((String) row.elementAt(dieldrinCol));
            if (lindaneCol != -1)
                cArray[i].setLindane   ((String) row.elementAt(lindaneCol));
            if (pcbCol != -1)
                cArray[i].setPcb       ((String) row.elementAt(pcbCol));
            if (tdeCol != -1)
                cArray[i].setTde       ((String) row.elementAt(tdeCol));
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
     *     new MrnPlapes(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>).put();</pre>
     * will insert a record with:
     *     plaphyCode = <val1>,
     *     dde        = <val2>,
     *     ddt        = <val3>,
     *     dieldrin   = <val4>,
     *     lindane    = <val5>,
     *     pcb        = <val6>,
     *     tde        = <val7>.
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
     * Boolean success = new MrnPlapes(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where dde = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPlapes plapes = new MrnPlapes();
     * success = plapes.del(MrnPlapes.PLAPHY_CODE+"=<val1>");</pre>
     * will delete all records where plaphyCode = <val1>.
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
     * update are taken from the MrnPlapes argument, .e.g.<pre>
     * Boolean success
     * MrnPlapes updMrnPlapes = new MrnPlapes();
     * updMrnPlapes.setDde(<val2>);
     * MrnPlapes whereMrnPlapes = new MrnPlapes(<val1>);
     * success = whereMrnPlapes.upd(updMrnPlapes);</pre>
     * will update Dde to <val2> for all records where 
     * plaphyCode = <val1>.
     * @param plapes  A MrnPlapes variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlapes plapes) {
        return db.update (TABLE, createColVals(plapes), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPlapes updMrnPlapes = new MrnPlapes();
     * updMrnPlapes.setDde(<val2>);
     * MrnPlapes whereMrnPlapes = new MrnPlapes();
     * success = whereMrnPlapes.upd(
     *     updMrnPlapes, MrnPlapes.PLAPHY_CODE+"=<val1>");</pre>
     * will update Dde to <val2> for all records where 
     * plaphyCode = <val1>.
     * @param  updMrnPlapes  A MrnPlapes variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlapes plapes, String where) {
        return db.update (TABLE, createColVals(plapes), where);
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
        if (getPlaphyCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLAPHY_CODE + "=" + getPlaphyCode("");
        } // if getPlaphyCode
        if (getDde() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DDE + "=" + getDde("");
        } // if getDde
        if (getDdt() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DDT + "=" + getDdt("");
        } // if getDdt
        if (getDieldrin() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DIELDRIN + "=" + getDieldrin("");
        } // if getDieldrin
        if (getLindane() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LINDANE + "=" + getLindane("");
        } // if getLindane
        if (getPcb() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PCB + "=" + getPcb("");
        } // if getPcb
        if (getTde() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TDE + "=" + getTde("");
        } // if getTde
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnPlapes aVar) {
        String colVals = "";
        if (aVar.getPlaphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLAPHY_CODE +"=";
            colVals += (aVar.getPlaphyCode() == INTNULL2 ?
                "null" : aVar.getPlaphyCode(""));
        } // if aVar.getPlaphyCode
        if (aVar.getDde() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DDE +"=";
            colVals += (aVar.getDde() == FLOATNULL2 ?
                "null" : aVar.getDde(""));
        } // if aVar.getDde
        if (aVar.getDdt() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DDT +"=";
            colVals += (aVar.getDdt() == FLOATNULL2 ?
                "null" : aVar.getDdt(""));
        } // if aVar.getDdt
        if (aVar.getDieldrin() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DIELDRIN +"=";
            colVals += (aVar.getDieldrin() == FLOATNULL2 ?
                "null" : aVar.getDieldrin(""));
        } // if aVar.getDieldrin
        if (aVar.getLindane() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LINDANE +"=";
            colVals += (aVar.getLindane() == FLOATNULL2 ?
                "null" : aVar.getLindane(""));
        } // if aVar.getLindane
        if (aVar.getPcb() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PCB +"=";
            colVals += (aVar.getPcb() == FLOATNULL2 ?
                "null" : aVar.getPcb(""));
        } // if aVar.getPcb
        if (aVar.getTde() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TDE +"=";
            colVals += (aVar.getTde() == FLOATNULL2 ?
                "null" : aVar.getTde(""));
        } // if aVar.getTde
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = PLAPHY_CODE;
        if (getDde() != FLOATNULL) {
            columns = columns + "," + DDE;
        } // if getDde
        if (getDdt() != FLOATNULL) {
            columns = columns + "," + DDT;
        } // if getDdt
        if (getDieldrin() != FLOATNULL) {
            columns = columns + "," + DIELDRIN;
        } // if getDieldrin
        if (getLindane() != FLOATNULL) {
            columns = columns + "," + LINDANE;
        } // if getLindane
        if (getPcb() != FLOATNULL) {
            columns = columns + "," + PCB;
        } // if getPcb
        if (getTde() != FLOATNULL) {
            columns = columns + "," + TDE;
        } // if getTde
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getPlaphyCode("");
        if (getDde() != FLOATNULL) {
            values  = values  + "," + getDde("");
        } // if getDde
        if (getDdt() != FLOATNULL) {
            values  = values  + "," + getDdt("");
        } // if getDdt
        if (getDieldrin() != FLOATNULL) {
            values  = values  + "," + getDieldrin("");
        } // if getDieldrin
        if (getLindane() != FLOATNULL) {
            values  = values  + "," + getLindane("");
        } // if getLindane
        if (getPcb() != FLOATNULL) {
            values  = values  + "," + getPcb("");
        } // if getPcb
        if (getTde() != FLOATNULL) {
            values  = values  + "," + getTde("");
        } // if getTde
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getPlaphyCode("") + "|" +
            getDde("")        + "|" +
            getDdt("")        + "|" +
            getDieldrin("")   + "|" +
            getLindane("")    + "|" +
            getPcb("")        + "|" +
            getTde("")        + "|";
    } // method toString

} // class MrnPlapes
