package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WATNUT table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnWatnut extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WATNUT";
    /** The watphyCode field name */
    public static final String WATPHY_CODE = "WATPHY_CODE";
    /** The no2 field name */
    public static final String NO2 = "NO2";
    /** The no3 field name */
    public static final String NO3 = "NO3";
    /** The p field name */
    public static final String P = "P";
    /** The po4 field name */
    public static final String PO4 = "PO4";
    /** The ptot field name */
    public static final String PTOT = "PTOT";
    /** The sio3 field name */
    public static final String SIO3 = "SIO3";
    /** The sio4 field name */
    public static final String SIO4 = "SIO4";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       watphyCode;
    private float     no2;
    private float     no3;
    private float     p;
    private float     po4;
    private float     ptot;
    private float     sio3;
    private float     sio4;

    /** The error variables  */
    private int watphyCodeError = ERROR_NORMAL;
    private int no2Error        = ERROR_NORMAL;
    private int no3Error        = ERROR_NORMAL;
    private int pError          = ERROR_NORMAL;
    private int po4Error        = ERROR_NORMAL;
    private int ptotError       = ERROR_NORMAL;
    private int sio3Error       = ERROR_NORMAL;
    private int sio4Error       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String watphyCodeErrorMessage = "";
    private String no2ErrorMessage        = "";
    private String no3ErrorMessage        = "";
    private String pErrorMessage          = "";
    private String po4ErrorMessage        = "";
    private String ptotErrorMessage       = "";
    private String sio3ErrorMessage       = "";
    private String sio4ErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       WATPHY_CODE_MN = INTMIN;
    public static final int       WATPHY_CODE_MX = INTMAX;
    public static final float     NO2_MN = FLOATMIN;
    public static final float     NO2_MX = FLOATMAX;
    public static final float     NO3_MN = FLOATMIN;
    public static final float     NO3_MX = FLOATMAX;
    public static final float     P_MN = FLOATMIN;
    public static final float     P_MX = FLOATMAX;
    public static final float     PO4_MN = FLOATMIN;
    public static final float     PO4_MX = FLOATMAX;
    public static final float     PTOT_MN = FLOATMIN;
    public static final float     PTOT_MX = FLOATMAX;
    public static final float     SIO3_MN = FLOATMIN;
    public static final float     SIO3_MX = FLOATMAX;
    public static final float     SIO4_MN = FLOATMIN;
    public static final float     SIO4_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception watphyCodeOutOfBoundsException =
        new Exception ("'watphyCode' out of bounds: " +
            WATPHY_CODE_MN + " - " + WATPHY_CODE_MX);
    Exception no2OutOfBoundsException =
        new Exception ("'no2' out of bounds: " +
            NO2_MN + " - " + NO2_MX);
    Exception no3OutOfBoundsException =
        new Exception ("'no3' out of bounds: " +
            NO3_MN + " - " + NO3_MX);
    Exception pOutOfBoundsException =
        new Exception ("'p' out of bounds: " +
            P_MN + " - " + P_MX);
    Exception po4OutOfBoundsException =
        new Exception ("'po4' out of bounds: " +
            PO4_MN + " - " + PO4_MX);
    Exception ptotOutOfBoundsException =
        new Exception ("'ptot' out of bounds: " +
            PTOT_MN + " - " + PTOT_MX);
    Exception sio3OutOfBoundsException =
        new Exception ("'sio3' out of bounds: " +
            SIO3_MN + " - " + SIO3_MX);
    Exception sio4OutOfBoundsException =
        new Exception ("'sio4' out of bounds: " +
            SIO4_MN + " - " + SIO4_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnWatnut() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnWatnut constructor 1"); // debug
    } // MrnWatnut constructor

    /**
     * Instantiate a MrnWatnut object and initialize the instance variables.
     * @param watphyCode  The watphyCode (int)
     */
    public MrnWatnut(
            int watphyCode) {
        this();
        setWatphyCode (watphyCode);
        if (dbg) System.out.println ("<br>in MrnWatnut constructor 2"); // debug
    } // MrnWatnut constructor

    /**
     * Instantiate a MrnWatnut object and initialize the instance variables.
     * @param watphyCode  The watphyCode (int)
     * @param no2         The no2        (float)
     * @param no3         The no3        (float)
     * @param p           The p          (float)
     * @param po4         The po4        (float)
     * @param ptot        The ptot       (float)
     * @param sio3        The sio3       (float)
     * @param sio4        The sio4       (float)
     */
    public MrnWatnut(
            int   watphyCode,
            float no2,
            float no3,
            float p,
            float po4,
            float ptot,
            float sio3,
            float sio4) {
        this();
        setWatphyCode (watphyCode);
        setNo2        (no2       );
        setNo3        (no3       );
        setP          (p         );
        setPo4        (po4       );
        setPtot       (ptot      );
        setSio3       (sio3      );
        setSio4       (sio4      );
        if (dbg) System.out.println ("<br>in MrnWatnut constructor 3"); // debug
    } // MrnWatnut constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setWatphyCode (INTNULL  );
        setNo2        (FLOATNULL);
        setNo3        (FLOATNULL);
        setP          (FLOATNULL);
        setPo4        (FLOATNULL);
        setPtot       (FLOATNULL);
        setSio3       (FLOATNULL);
        setSio4       (FLOATNULL);
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
     * Set the 'no2' class variable
     * @param  no2 (float)
     */
    public int setNo2(float no2) {
        try {
            if ( ((no2 == FLOATNULL) ||
                  (no2 == FLOATNULL2)) ||
                !((no2 < NO2_MN) ||
                  (no2 > NO2_MX)) ) {
                this.no2 = no2;
                no2Error = ERROR_NORMAL;
            } else {
                throw no2OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNo2Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return no2Error;
    } // method setNo2

    /**
     * Set the 'no2' class variable
     * @param  no2 (Integer)
     */
    public int setNo2(Integer no2) {
        try {
            setNo2(no2.floatValue());
        } catch (Exception e) {
            setNo2Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return no2Error;
    } // method setNo2

    /**
     * Set the 'no2' class variable
     * @param  no2 (Float)
     */
    public int setNo2(Float no2) {
        try {
            setNo2(no2.floatValue());
        } catch (Exception e) {
            setNo2Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return no2Error;
    } // method setNo2

    /**
     * Set the 'no2' class variable
     * @param  no2 (String)
     */
    public int setNo2(String no2) {
        try {
            setNo2(new Float(no2).floatValue());
        } catch (Exception e) {
            setNo2Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return no2Error;
    } // method setNo2

    /**
     * Called when an exception has occured
     * @param  no2 (String)
     */
    private void setNo2Error (float no2, Exception e, int error) {
        this.no2 = no2;
        no2ErrorMessage = e.toString();
        no2Error = error;
    } // method setNo2Error


    /**
     * Set the 'no3' class variable
     * @param  no3 (float)
     */
    public int setNo3(float no3) {
        try {
            if ( ((no3 == FLOATNULL) ||
                  (no3 == FLOATNULL2)) ||
                !((no3 < NO3_MN) ||
                  (no3 > NO3_MX)) ) {
                this.no3 = no3;
                no3Error = ERROR_NORMAL;
            } else {
                throw no3OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNo3Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return no3Error;
    } // method setNo3

    /**
     * Set the 'no3' class variable
     * @param  no3 (Integer)
     */
    public int setNo3(Integer no3) {
        try {
            setNo3(no3.floatValue());
        } catch (Exception e) {
            setNo3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return no3Error;
    } // method setNo3

    /**
     * Set the 'no3' class variable
     * @param  no3 (Float)
     */
    public int setNo3(Float no3) {
        try {
            setNo3(no3.floatValue());
        } catch (Exception e) {
            setNo3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return no3Error;
    } // method setNo3

    /**
     * Set the 'no3' class variable
     * @param  no3 (String)
     */
    public int setNo3(String no3) {
        try {
            setNo3(new Float(no3).floatValue());
        } catch (Exception e) {
            setNo3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return no3Error;
    } // method setNo3

    /**
     * Called when an exception has occured
     * @param  no3 (String)
     */
    private void setNo3Error (float no3, Exception e, int error) {
        this.no3 = no3;
        no3ErrorMessage = e.toString();
        no3Error = error;
    } // method setNo3Error


    /**
     * Set the 'p' class variable
     * @param  p (float)
     */
    public int setP(float p) {
        try {
            if ( ((p == FLOATNULL) ||
                  (p == FLOATNULL2)) ||
                !((p < P_MN) ||
                  (p > P_MX)) ) {
                this.p = p;
                pError = ERROR_NORMAL;
            } else {
                throw pOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return pError;
    } // method setP

    /**
     * Set the 'p' class variable
     * @param  p (Integer)
     */
    public int setP(Integer p) {
        try {
            setP(p.floatValue());
        } catch (Exception e) {
            setPError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pError;
    } // method setP

    /**
     * Set the 'p' class variable
     * @param  p (Float)
     */
    public int setP(Float p) {
        try {
            setP(p.floatValue());
        } catch (Exception e) {
            setPError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pError;
    } // method setP

    /**
     * Set the 'p' class variable
     * @param  p (String)
     */
    public int setP(String p) {
        try {
            setP(new Float(p).floatValue());
        } catch (Exception e) {
            setPError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pError;
    } // method setP

    /**
     * Called when an exception has occured
     * @param  p (String)
     */
    private void setPError (float p, Exception e, int error) {
        this.p = p;
        pErrorMessage = e.toString();
        pError = error;
    } // method setPError


    /**
     * Set the 'po4' class variable
     * @param  po4 (float)
     */
    public int setPo4(float po4) {
        try {
            if ( ((po4 == FLOATNULL) ||
                  (po4 == FLOATNULL2)) ||
                !((po4 < PO4_MN) ||
                  (po4 > PO4_MX)) ) {
                this.po4 = po4;
                po4Error = ERROR_NORMAL;
            } else {
                throw po4OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPo4Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return po4Error;
    } // method setPo4

    /**
     * Set the 'po4' class variable
     * @param  po4 (Integer)
     */
    public int setPo4(Integer po4) {
        try {
            setPo4(po4.floatValue());
        } catch (Exception e) {
            setPo4Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return po4Error;
    } // method setPo4

    /**
     * Set the 'po4' class variable
     * @param  po4 (Float)
     */
    public int setPo4(Float po4) {
        try {
            setPo4(po4.floatValue());
        } catch (Exception e) {
            setPo4Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return po4Error;
    } // method setPo4

    /**
     * Set the 'po4' class variable
     * @param  po4 (String)
     */
    public int setPo4(String po4) {
        try {
            setPo4(new Float(po4).floatValue());
        } catch (Exception e) {
            setPo4Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return po4Error;
    } // method setPo4

    /**
     * Called when an exception has occured
     * @param  po4 (String)
     */
    private void setPo4Error (float po4, Exception e, int error) {
        this.po4 = po4;
        po4ErrorMessage = e.toString();
        po4Error = error;
    } // method setPo4Error


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


    /**
     * Set the 'sio3' class variable
     * @param  sio3 (float)
     */
    public int setSio3(float sio3) {
        try {
            if ( ((sio3 == FLOATNULL) ||
                  (sio3 == FLOATNULL2)) ||
                !((sio3 < SIO3_MN) ||
                  (sio3 > SIO3_MX)) ) {
                this.sio3 = sio3;
                sio3Error = ERROR_NORMAL;
            } else {
                throw sio3OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSio3Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return sio3Error;
    } // method setSio3

    /**
     * Set the 'sio3' class variable
     * @param  sio3 (Integer)
     */
    public int setSio3(Integer sio3) {
        try {
            setSio3(sio3.floatValue());
        } catch (Exception e) {
            setSio3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sio3Error;
    } // method setSio3

    /**
     * Set the 'sio3' class variable
     * @param  sio3 (Float)
     */
    public int setSio3(Float sio3) {
        try {
            setSio3(sio3.floatValue());
        } catch (Exception e) {
            setSio3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sio3Error;
    } // method setSio3

    /**
     * Set the 'sio3' class variable
     * @param  sio3 (String)
     */
    public int setSio3(String sio3) {
        try {
            setSio3(new Float(sio3).floatValue());
        } catch (Exception e) {
            setSio3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sio3Error;
    } // method setSio3

    /**
     * Called when an exception has occured
     * @param  sio3 (String)
     */
    private void setSio3Error (float sio3, Exception e, int error) {
        this.sio3 = sio3;
        sio3ErrorMessage = e.toString();
        sio3Error = error;
    } // method setSio3Error


    /**
     * Set the 'sio4' class variable
     * @param  sio4 (float)
     */
    public int setSio4(float sio4) {
        try {
            if ( ((sio4 == FLOATNULL) ||
                  (sio4 == FLOATNULL2)) ||
                !((sio4 < SIO4_MN) ||
                  (sio4 > SIO4_MX)) ) {
                this.sio4 = sio4;
                sio4Error = ERROR_NORMAL;
            } else {
                throw sio4OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSio4Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return sio4Error;
    } // method setSio4

    /**
     * Set the 'sio4' class variable
     * @param  sio4 (Integer)
     */
    public int setSio4(Integer sio4) {
        try {
            setSio4(sio4.floatValue());
        } catch (Exception e) {
            setSio4Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sio4Error;
    } // method setSio4

    /**
     * Set the 'sio4' class variable
     * @param  sio4 (Float)
     */
    public int setSio4(Float sio4) {
        try {
            setSio4(sio4.floatValue());
        } catch (Exception e) {
            setSio4Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sio4Error;
    } // method setSio4

    /**
     * Set the 'sio4' class variable
     * @param  sio4 (String)
     */
    public int setSio4(String sio4) {
        try {
            setSio4(new Float(sio4).floatValue());
        } catch (Exception e) {
            setSio4Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sio4Error;
    } // method setSio4

    /**
     * Called when an exception has occured
     * @param  sio4 (String)
     */
    private void setSio4Error (float sio4, Exception e, int error) {
        this.sio4 = sio4;
        sio4ErrorMessage = e.toString();
        sio4Error = error;
    } // method setSio4Error


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
     * Return the 'no2' class variable
     * @return no2 (float)
     */
    public float getNo2() {
        return no2;
    } // method getNo2

    /**
     * Return the 'no2' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNo2 methods
     * @return no2 (String)
     */
    public String getNo2(String s) {
        return ((no2 != FLOATNULL) ? new Float(no2).toString() : "");
    } // method getNo2


    /**
     * Return the 'no3' class variable
     * @return no3 (float)
     */
    public float getNo3() {
        return no3;
    } // method getNo3

    /**
     * Return the 'no3' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNo3 methods
     * @return no3 (String)
     */
    public String getNo3(String s) {
        return ((no3 != FLOATNULL) ? new Float(no3).toString() : "");
    } // method getNo3


    /**
     * Return the 'p' class variable
     * @return p (float)
     */
    public float getP() {
        return p;
    } // method getP

    /**
     * Return the 'p' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getP methods
     * @return p (String)
     */
    public String getP(String s) {
        return ((p != FLOATNULL) ? new Float(p).toString() : "");
    } // method getP


    /**
     * Return the 'po4' class variable
     * @return po4 (float)
     */
    public float getPo4() {
        return po4;
    } // method getPo4

    /**
     * Return the 'po4' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPo4 methods
     * @return po4 (String)
     */
    public String getPo4(String s) {
        return ((po4 != FLOATNULL) ? new Float(po4).toString() : "");
    } // method getPo4


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
     * Return the 'sio3' class variable
     * @return sio3 (float)
     */
    public float getSio3() {
        return sio3;
    } // method getSio3

    /**
     * Return the 'sio3' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSio3 methods
     * @return sio3 (String)
     */
    public String getSio3(String s) {
        return ((sio3 != FLOATNULL) ? new Float(sio3).toString() : "");
    } // method getSio3


    /**
     * Return the 'sio4' class variable
     * @return sio4 (float)
     */
    public float getSio4() {
        return sio4;
    } // method getSio4

    /**
     * Return the 'sio4' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSio4 methods
     * @return sio4 (String)
     */
    public String getSio4(String s) {
        return ((sio4 != FLOATNULL) ? new Float(sio4).toString() : "");
    } // method getSio4


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
            (no2 == FLOATNULL) &&
            (no3 == FLOATNULL) &&
            (p == FLOATNULL) &&
            (po4 == FLOATNULL) &&
            (ptot == FLOATNULL) &&
            (sio3 == FLOATNULL) &&
            (sio4 == FLOATNULL)) {
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
            no2Error +
            no3Error +
            pError +
            po4Error +
            ptotError +
            sio3Error +
            sio4Error;
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
     * Gets the errorcode for the no2 instance variable
     * @return errorcode (int)
     */
    public int getNo2Error() {
        return no2Error;
    } // method getNo2Error

    /**
     * Gets the errorMessage for the no2 instance variable
     * @return errorMessage (String)
     */
    public String getNo2ErrorMessage() {
        return no2ErrorMessage;
    } // method getNo2ErrorMessage


    /**
     * Gets the errorcode for the no3 instance variable
     * @return errorcode (int)
     */
    public int getNo3Error() {
        return no3Error;
    } // method getNo3Error

    /**
     * Gets the errorMessage for the no3 instance variable
     * @return errorMessage (String)
     */
    public String getNo3ErrorMessage() {
        return no3ErrorMessage;
    } // method getNo3ErrorMessage


    /**
     * Gets the errorcode for the p instance variable
     * @return errorcode (int)
     */
    public int getPError() {
        return pError;
    } // method getPError

    /**
     * Gets the errorMessage for the p instance variable
     * @return errorMessage (String)
     */
    public String getPErrorMessage() {
        return pErrorMessage;
    } // method getPErrorMessage


    /**
     * Gets the errorcode for the po4 instance variable
     * @return errorcode (int)
     */
    public int getPo4Error() {
        return po4Error;
    } // method getPo4Error

    /**
     * Gets the errorMessage for the po4 instance variable
     * @return errorMessage (String)
     */
    public String getPo4ErrorMessage() {
        return po4ErrorMessage;
    } // method getPo4ErrorMessage


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


    /**
     * Gets the errorcode for the sio3 instance variable
     * @return errorcode (int)
     */
    public int getSio3Error() {
        return sio3Error;
    } // method getSio3Error

    /**
     * Gets the errorMessage for the sio3 instance variable
     * @return errorMessage (String)
     */
    public String getSio3ErrorMessage() {
        return sio3ErrorMessage;
    } // method getSio3ErrorMessage


    /**
     * Gets the errorcode for the sio4 instance variable
     * @return errorcode (int)
     */
    public int getSio4Error() {
        return sio4Error;
    } // method getSio4Error

    /**
     * Gets the errorMessage for the sio4 instance variable
     * @return errorMessage (String)
     */
    public String getSio4ErrorMessage() {
        return sio4ErrorMessage;
    } // method getSio4ErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnWatnut. e.g.<pre>
     * MrnWatnut watnut = new MrnWatnut(<val1>);
     * MrnWatnut watnutArray[] = watnut.get();</pre>
     * will get the MrnWatnut record where watphyCode = <val1>.
     * @return Array of MrnWatnut (MrnWatnut[])
     */
    public MrnWatnut[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnWatnut watnutArray[] =
     *     new MrnWatnut().get(MrnWatnut.WATPHY_CODE+"=<val1>");</pre>
     * will get the MrnWatnut record where watphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnWatnut (MrnWatnut[])
     */
    public MrnWatnut[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnWatnut watnutArray[] =
     *     new MrnWatnut().get("1=1",watnut.WATPHY_CODE);</pre>
     * will get the all the MrnWatnut records, and order them by watphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatnut (MrnWatnut[])
     */
    public MrnWatnut[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnWatnut.WATPHY_CODE,MrnWatnut.NO2;
     * String where = MrnWatnut.WATPHY_CODE + "=<val1";
     * String order = MrnWatnut.WATPHY_CODE;
     * MrnWatnut watnutArray[] =
     *     new MrnWatnut().get(columns, where, order);</pre>
     * will get the watphyCode and no2 colums of all MrnWatnut records,
     * where watphyCode = <val1>, and order them by watphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatnut (MrnWatnut[])
     */
    public MrnWatnut[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnWatnut.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnWatnut[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int watphyCodeCol = db.getColNumber(WATPHY_CODE);
        int no2Col        = db.getColNumber(NO2);
        int no3Col        = db.getColNumber(NO3);
        int pCol          = db.getColNumber(P);
        int po4Col        = db.getColNumber(PO4);
        int ptotCol       = db.getColNumber(PTOT);
        int sio3Col       = db.getColNumber(SIO3);
        int sio4Col       = db.getColNumber(SIO4);
        MrnWatnut[] cArray = new MrnWatnut[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnWatnut();
            if (watphyCodeCol != -1)
                cArray[i].setWatphyCode((String) row.elementAt(watphyCodeCol));
            if (no2Col != -1)
                cArray[i].setNo2       ((String) row.elementAt(no2Col));
            if (no3Col != -1)
                cArray[i].setNo3       ((String) row.elementAt(no3Col));
            if (pCol != -1)
                cArray[i].setP         ((String) row.elementAt(pCol));
            if (po4Col != -1)
                cArray[i].setPo4       ((String) row.elementAt(po4Col));
            if (ptotCol != -1)
                cArray[i].setPtot      ((String) row.elementAt(ptotCol));
            if (sio3Col != -1)
                cArray[i].setSio3      ((String) row.elementAt(sio3Col));
            if (sio4Col != -1)
                cArray[i].setSio4      ((String) row.elementAt(sio4Col));
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
     *     new MrnWatnut(
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
     *     no2        = <val2>,
     *     no3        = <val3>,
     *     p          = <val4>,
     *     po4        = <val5>,
     *     ptot       = <val6>,
     *     sio3       = <val7>,
     *     sio4       = <val8>.
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
     * Boolean success = new MrnWatnut(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where no2 = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatnut watnut = new MrnWatnut();
     * success = watnut.del(MrnWatnut.WATPHY_CODE+"=<val1>");</pre>
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
     * update are taken from the MrnWatnut argument, .e.g.<pre>
     * Boolean success
     * MrnWatnut updMrnWatnut = new MrnWatnut();
     * updMrnWatnut.setNo2(<val2>);
     * MrnWatnut whereMrnWatnut = new MrnWatnut(<val1>);
     * success = whereMrnWatnut.upd(updMrnWatnut);</pre>
     * will update No2 to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watnut  A MrnWatnut variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatnut watnut) {
        return db.update (TABLE, createColVals(watnut), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatnut updMrnWatnut = new MrnWatnut();
     * updMrnWatnut.setNo2(<val2>);
     * MrnWatnut whereMrnWatnut = new MrnWatnut();
     * success = whereMrnWatnut.upd(
     *     updMrnWatnut, MrnWatnut.WATPHY_CODE+"=<val1>");</pre>
     * will update No2 to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watnut  A MrnWatnut variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatnut watnut, String where) {
        return db.update (TABLE, createColVals(watnut), where);
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
        if (getNo2() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NO2 + "=" + getNo2("");
        } // if getNo2
        if (getNo3() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NO3 + "=" + getNo3("");
        } // if getNo3
        if (getP() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + P + "=" + getP("");
        } // if getP
        if (getPo4() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PO4 + "=" + getPo4("");
        } // if getPo4
        if (getPtot() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PTOT + "=" + getPtot("");
        } // if getPtot
        if (getSio3() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SIO3 + "=" + getSio3("");
        } // if getSio3
        if (getSio4() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SIO4 + "=" + getSio4("");
        } // if getSio4
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnWatnut aVar) {
        String colVals = "";
        if (aVar.getWatphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPHY_CODE +"=";
            colVals += (aVar.getWatphyCode() == INTNULL2 ?
                "null" : aVar.getWatphyCode(""));
        } // if aVar.getWatphyCode
        if (aVar.getNo2() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NO2 +"=";
            colVals += (aVar.getNo2() == FLOATNULL2 ?
                "null" : aVar.getNo2(""));
        } // if aVar.getNo2
        if (aVar.getNo3() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NO3 +"=";
            colVals += (aVar.getNo3() == FLOATNULL2 ?
                "null" : aVar.getNo3(""));
        } // if aVar.getNo3
        if (aVar.getP() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += P +"=";
            colVals += (aVar.getP() == FLOATNULL2 ?
                "null" : aVar.getP(""));
        } // if aVar.getP
        if (aVar.getPo4() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PO4 +"=";
            colVals += (aVar.getPo4() == FLOATNULL2 ?
                "null" : aVar.getPo4(""));
        } // if aVar.getPo4
        if (aVar.getPtot() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PTOT +"=";
            colVals += (aVar.getPtot() == FLOATNULL2 ?
                "null" : aVar.getPtot(""));
        } // if aVar.getPtot
        if (aVar.getSio3() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SIO3 +"=";
            colVals += (aVar.getSio3() == FLOATNULL2 ?
                "null" : aVar.getSio3(""));
        } // if aVar.getSio3
        if (aVar.getSio4() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SIO4 +"=";
            colVals += (aVar.getSio4() == FLOATNULL2 ?
                "null" : aVar.getSio4(""));
        } // if aVar.getSio4
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = WATPHY_CODE;
        if (getNo2() != FLOATNULL) {
            columns = columns + "," + NO2;
        } // if getNo2
        if (getNo3() != FLOATNULL) {
            columns = columns + "," + NO3;
        } // if getNo3
        if (getP() != FLOATNULL) {
            columns = columns + "," + P;
        } // if getP
        if (getPo4() != FLOATNULL) {
            columns = columns + "," + PO4;
        } // if getPo4
        if (getPtot() != FLOATNULL) {
            columns = columns + "," + PTOT;
        } // if getPtot
        if (getSio3() != FLOATNULL) {
            columns = columns + "," + SIO3;
        } // if getSio3
        if (getSio4() != FLOATNULL) {
            columns = columns + "," + SIO4;
        } // if getSio4
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getWatphyCode("");
        if (getNo2() != FLOATNULL) {
            values  = values  + "," + getNo2("");
        } // if getNo2
        if (getNo3() != FLOATNULL) {
            values  = values  + "," + getNo3("");
        } // if getNo3
        if (getP() != FLOATNULL) {
            values  = values  + "," + getP("");
        } // if getP
        if (getPo4() != FLOATNULL) {
            values  = values  + "," + getPo4("");
        } // if getPo4
        if (getPtot() != FLOATNULL) {
            values  = values  + "," + getPtot("");
        } // if getPtot
        if (getSio3() != FLOATNULL) {
            values  = values  + "," + getSio3("");
        } // if getSio3
        if (getSio4() != FLOATNULL) {
            values  = values  + "," + getSio4("");
        } // if getSio4
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
            getNo2("")        + "|" +
            getNo3("")        + "|" +
            getP("")          + "|" +
            getPo4("")        + "|" +
            getPtot("")       + "|" +
            getSio3("")       + "|" +
            getSio4("")       + "|";
    } // method toString

} // class MrnWatnut
