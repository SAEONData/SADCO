package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WATCHEM1 table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnWatchem1 extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WATCHEM1";
    /** The watphyCode field name */
    public static final String WATPHY_CODE = "WATPHY_CODE";
    /** The dic field name */
    public static final String DIC = "DIC";
    /** The doc field name */
    public static final String DOC = "DOC";
    /** The fluoride field name */
    public static final String FLUORIDE = "FLUORIDE";
    /** The iodene field name */
    public static final String IODENE = "IODENE";
    /** The iodate field name */
    public static final String IODATE = "IODATE";
    /** The kjn field name */
    public static final String KJN = "KJN";
    /** The nh3 field name */
    public static final String NH3 = "NH3";
    /** The nitrogen field name */
    public static final String NITROGEN = "NITROGEN";
    /** The oxa field name */
    public static final String OXA = "OXA";
    /** The ph field name */
    public static final String PH = "PH";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       watphyCode;
    private float     dic;
    private float     doc;
    private float     fluoride;
    private float     iodene;
    private float     iodate;
    private float     kjn;
    private float     nh3;
    private float     nitrogen;
    private float     oxa;
    private float     ph;

    /** The error variables  */
    private int watphyCodeError = ERROR_NORMAL;
    private int dicError        = ERROR_NORMAL;
    private int docError        = ERROR_NORMAL;
    private int fluorideError   = ERROR_NORMAL;
    private int iodeneError     = ERROR_NORMAL;
    private int iodateError     = ERROR_NORMAL;
    private int kjnError        = ERROR_NORMAL;
    private int nh3Error        = ERROR_NORMAL;
    private int nitrogenError   = ERROR_NORMAL;
    private int oxaError        = ERROR_NORMAL;
    private int phError         = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String watphyCodeErrorMessage = "";
    private String dicErrorMessage        = "";
    private String docErrorMessage        = "";
    private String fluorideErrorMessage   = "";
    private String iodeneErrorMessage     = "";
    private String iodateErrorMessage     = "";
    private String kjnErrorMessage        = "";
    private String nh3ErrorMessage        = "";
    private String nitrogenErrorMessage   = "";
    private String oxaErrorMessage        = "";
    private String phErrorMessage         = "";

    /** The min-max constants for all numerics */
    public static final int       WATPHY_CODE_MN = INTMIN;
    public static final int       WATPHY_CODE_MX = INTMAX;
    public static final float     DIC_MN = FLOATMIN;
    public static final float     DIC_MX = FLOATMAX;
    public static final float     DOC_MN = FLOATMIN;
    public static final float     DOC_MX = FLOATMAX;
    public static final float     FLUORIDE_MN = FLOATMIN;
    public static final float     FLUORIDE_MX = FLOATMAX;
    public static final float     IODENE_MN = FLOATMIN;
    public static final float     IODENE_MX = FLOATMAX;
    public static final float     IODATE_MN = FLOATMIN;
    public static final float     IODATE_MX = FLOATMAX;
    public static final float     KJN_MN = FLOATMIN;
    public static final float     KJN_MX = FLOATMAX;
    public static final float     NH3_MN = FLOATMIN;
    public static final float     NH3_MX = FLOATMAX;
    public static final float     NITROGEN_MN = FLOATMIN;
    public static final float     NITROGEN_MX = FLOATMAX;
    public static final float     OXA_MN = FLOATMIN;
    public static final float     OXA_MX = FLOATMAX;
    public static final float     PH_MN = FLOATMIN;
    public static final float     PH_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception watphyCodeOutOfBoundsException =
        new Exception ("'watphyCode' out of bounds: " +
            WATPHY_CODE_MN + " - " + WATPHY_CODE_MX);
    Exception dicOutOfBoundsException =
        new Exception ("'dic' out of bounds: " +
            DIC_MN + " - " + DIC_MX);
    Exception docOutOfBoundsException =
        new Exception ("'doc' out of bounds: " +
            DOC_MN + " - " + DOC_MX);
    Exception fluorideOutOfBoundsException =
        new Exception ("'fluoride' out of bounds: " +
            FLUORIDE_MN + " - " + FLUORIDE_MX);
    Exception iodeneOutOfBoundsException =
        new Exception ("'iodene' out of bounds: " +
            IODENE_MN + " - " + IODENE_MX);
    Exception iodateOutOfBoundsException =
        new Exception ("'iodate' out of bounds: " +
            IODATE_MN + " - " + IODATE_MX);
    Exception kjnOutOfBoundsException =
        new Exception ("'kjn' out of bounds: " +
            KJN_MN + " - " + KJN_MX);
    Exception nh3OutOfBoundsException =
        new Exception ("'nh3' out of bounds: " +
            NH3_MN + " - " + NH3_MX);
    Exception nitrogenOutOfBoundsException =
        new Exception ("'nitrogen' out of bounds: " +
            NITROGEN_MN + " - " + NITROGEN_MX);
    Exception oxaOutOfBoundsException =
        new Exception ("'oxa' out of bounds: " +
            OXA_MN + " - " + OXA_MX);
    Exception phOutOfBoundsException =
        new Exception ("'ph' out of bounds: " +
            PH_MN + " - " + PH_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnWatchem1() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnWatchem1 constructor 1"); // debug
    } // MrnWatchem1 constructor

    /**
     * Instantiate a MrnWatchem1 object and initialize the instance variables.
     * @param watphyCode  The watphyCode (int)
     */
    public MrnWatchem1(
            int watphyCode) {
        this();
        setWatphyCode (watphyCode);
        if (dbg) System.out.println ("<br>in MrnWatchem1 constructor 2"); // debug
    } // MrnWatchem1 constructor

    /**
     * Instantiate a MrnWatchem1 object and initialize the instance variables.
     * @param watphyCode  The watphyCode (int)
     * @param dic         The dic        (float)
     * @param doc         The doc        (float)
     * @param fluoride    The fluoride   (float)
     * @param iodene      The iodene     (float)
     * @param iodate      The iodate     (float)
     * @param kjn         The kjn        (float)
     * @param nh3         The nh3        (float)
     * @param nitrogen    The nitrogen   (float)
     * @param oxa         The oxa        (float)
     * @param ph          The ph         (float)
     */
    public MrnWatchem1(
            int   watphyCode,
            float dic,
            float doc,
            float fluoride,
            float iodene,
            float iodate,
            float kjn,
            float nh3,
            float nitrogen,
            float oxa,
            float ph) {
        this();
        setWatphyCode (watphyCode);
        setDic        (dic       );
        setDoc        (doc       );
        setFluoride   (fluoride  );
        setIodene     (iodene    );
        setIodate     (iodate    );
        setKjn        (kjn       );
        setNh3        (nh3       );
        setNitrogen   (nitrogen  );
        setOxa        (oxa       );
        setPh         (ph        );
        if (dbg) System.out.println ("<br>in MrnWatchem1 constructor 3"); // debug
    } // MrnWatchem1 constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setWatphyCode (INTNULL  );
        setDic        (FLOATNULL);
        setDoc        (FLOATNULL);
        setFluoride   (FLOATNULL);
        setIodene     (FLOATNULL);
        setIodate     (FLOATNULL);
        setKjn        (FLOATNULL);
        setNh3        (FLOATNULL);
        setNitrogen   (FLOATNULL);
        setOxa        (FLOATNULL);
        setPh         (FLOATNULL);
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
     * Set the 'dic' class variable
     * @param  dic (float)
     */
    public int setDic(float dic) {
        try {
            if ( ((dic == FLOATNULL) ||
                  (dic == FLOATNULL2)) ||
                !((dic < DIC_MN) ||
                  (dic > DIC_MX)) ) {
                this.dic = dic;
                dicError = ERROR_NORMAL;
            } else {
                throw dicOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDicError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return dicError;
    } // method setDic

    /**
     * Set the 'dic' class variable
     * @param  dic (Integer)
     */
    public int setDic(Integer dic) {
        try {
            setDic(dic.floatValue());
        } catch (Exception e) {
            setDicError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dicError;
    } // method setDic

    /**
     * Set the 'dic' class variable
     * @param  dic (Float)
     */
    public int setDic(Float dic) {
        try {
            setDic(dic.floatValue());
        } catch (Exception e) {
            setDicError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dicError;
    } // method setDic

    /**
     * Set the 'dic' class variable
     * @param  dic (String)
     */
    public int setDic(String dic) {
        try {
            setDic(new Float(dic).floatValue());
        } catch (Exception e) {
            setDicError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dicError;
    } // method setDic

    /**
     * Called when an exception has occured
     * @param  dic (String)
     */
    private void setDicError (float dic, Exception e, int error) {
        this.dic = dic;
        dicErrorMessage = e.toString();
        dicError = error;
    } // method setDicError


    /**
     * Set the 'doc' class variable
     * @param  doc (float)
     */
    public int setDoc(float doc) {
        try {
            if ( ((doc == FLOATNULL) ||
                  (doc == FLOATNULL2)) ||
                !((doc < DOC_MN) ||
                  (doc > DOC_MX)) ) {
                this.doc = doc;
                docError = ERROR_NORMAL;
            } else {
                throw docOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDocError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return docError;
    } // method setDoc

    /**
     * Set the 'doc' class variable
     * @param  doc (Integer)
     */
    public int setDoc(Integer doc) {
        try {
            setDoc(doc.floatValue());
        } catch (Exception e) {
            setDocError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return docError;
    } // method setDoc

    /**
     * Set the 'doc' class variable
     * @param  doc (Float)
     */
    public int setDoc(Float doc) {
        try {
            setDoc(doc.floatValue());
        } catch (Exception e) {
            setDocError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return docError;
    } // method setDoc

    /**
     * Set the 'doc' class variable
     * @param  doc (String)
     */
    public int setDoc(String doc) {
        try {
            setDoc(new Float(doc).floatValue());
        } catch (Exception e) {
            setDocError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return docError;
    } // method setDoc

    /**
     * Called when an exception has occured
     * @param  doc (String)
     */
    private void setDocError (float doc, Exception e, int error) {
        this.doc = doc;
        docErrorMessage = e.toString();
        docError = error;
    } // method setDocError


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
     * Set the 'iodene' class variable
     * @param  iodene (float)
     */
    public int setIodene(float iodene) {
        try {
            if ( ((iodene == FLOATNULL) ||
                  (iodene == FLOATNULL2)) ||
                !((iodene < IODENE_MN) ||
                  (iodene > IODENE_MX)) ) {
                this.iodene = iodene;
                iodeneError = ERROR_NORMAL;
            } else {
                throw iodeneOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setIodeneError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return iodeneError;
    } // method setIodene

    /**
     * Set the 'iodene' class variable
     * @param  iodene (Integer)
     */
    public int setIodene(Integer iodene) {
        try {
            setIodene(iodene.floatValue());
        } catch (Exception e) {
            setIodeneError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return iodeneError;
    } // method setIodene

    /**
     * Set the 'iodene' class variable
     * @param  iodene (Float)
     */
    public int setIodene(Float iodene) {
        try {
            setIodene(iodene.floatValue());
        } catch (Exception e) {
            setIodeneError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return iodeneError;
    } // method setIodene

    /**
     * Set the 'iodene' class variable
     * @param  iodene (String)
     */
    public int setIodene(String iodene) {
        try {
            setIodene(new Float(iodene).floatValue());
        } catch (Exception e) {
            setIodeneError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return iodeneError;
    } // method setIodene

    /**
     * Called when an exception has occured
     * @param  iodene (String)
     */
    private void setIodeneError (float iodene, Exception e, int error) {
        this.iodene = iodene;
        iodeneErrorMessage = e.toString();
        iodeneError = error;
    } // method setIodeneError


    /**
     * Set the 'iodate' class variable
     * @param  iodate (float)
     */
    public int setIodate(float iodate) {
        try {
            if ( ((iodate == FLOATNULL) ||
                  (iodate == FLOATNULL2)) ||
                !((iodate < IODATE_MN) ||
                  (iodate > IODATE_MX)) ) {
                this.iodate = iodate;
                iodateError = ERROR_NORMAL;
            } else {
                throw iodateOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setIodateError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return iodateError;
    } // method setIodate

    /**
     * Set the 'iodate' class variable
     * @param  iodate (Integer)
     */
    public int setIodate(Integer iodate) {
        try {
            setIodate(iodate.floatValue());
        } catch (Exception e) {
            setIodateError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return iodateError;
    } // method setIodate

    /**
     * Set the 'iodate' class variable
     * @param  iodate (Float)
     */
    public int setIodate(Float iodate) {
        try {
            setIodate(iodate.floatValue());
        } catch (Exception e) {
            setIodateError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return iodateError;
    } // method setIodate

    /**
     * Set the 'iodate' class variable
     * @param  iodate (String)
     */
    public int setIodate(String iodate) {
        try {
            setIodate(new Float(iodate).floatValue());
        } catch (Exception e) {
            setIodateError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return iodateError;
    } // method setIodate

    /**
     * Called when an exception has occured
     * @param  iodate (String)
     */
    private void setIodateError (float iodate, Exception e, int error) {
        this.iodate = iodate;
        iodateErrorMessage = e.toString();
        iodateError = error;
    } // method setIodateError


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
     * Set the 'nh3' class variable
     * @param  nh3 (float)
     */
    public int setNh3(float nh3) {
        try {
            if ( ((nh3 == FLOATNULL) ||
                  (nh3 == FLOATNULL2)) ||
                !((nh3 < NH3_MN) ||
                  (nh3 > NH3_MX)) ) {
                this.nh3 = nh3;
                nh3Error = ERROR_NORMAL;
            } else {
                throw nh3OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNh3Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return nh3Error;
    } // method setNh3

    /**
     * Set the 'nh3' class variable
     * @param  nh3 (Integer)
     */
    public int setNh3(Integer nh3) {
        try {
            setNh3(nh3.floatValue());
        } catch (Exception e) {
            setNh3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nh3Error;
    } // method setNh3

    /**
     * Set the 'nh3' class variable
     * @param  nh3 (Float)
     */
    public int setNh3(Float nh3) {
        try {
            setNh3(nh3.floatValue());
        } catch (Exception e) {
            setNh3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nh3Error;
    } // method setNh3

    /**
     * Set the 'nh3' class variable
     * @param  nh3 (String)
     */
    public int setNh3(String nh3) {
        try {
            setNh3(new Float(nh3).floatValue());
        } catch (Exception e) {
            setNh3Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nh3Error;
    } // method setNh3

    /**
     * Called when an exception has occured
     * @param  nh3 (String)
     */
    private void setNh3Error (float nh3, Exception e, int error) {
        this.nh3 = nh3;
        nh3ErrorMessage = e.toString();
        nh3Error = error;
    } // method setNh3Error


    /**
     * Set the 'nitrogen' class variable
     * @param  nitrogen (float)
     */
    public int setNitrogen(float nitrogen) {
        try {
            if ( ((nitrogen == FLOATNULL) ||
                  (nitrogen == FLOATNULL2)) ||
                !((nitrogen < NITROGEN_MN) ||
                  (nitrogen > NITROGEN_MX)) ) {
                this.nitrogen = nitrogen;
                nitrogenError = ERROR_NORMAL;
            } else {
                throw nitrogenOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNitrogenError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return nitrogenError;
    } // method setNitrogen

    /**
     * Set the 'nitrogen' class variable
     * @param  nitrogen (Integer)
     */
    public int setNitrogen(Integer nitrogen) {
        try {
            setNitrogen(nitrogen.floatValue());
        } catch (Exception e) {
            setNitrogenError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nitrogenError;
    } // method setNitrogen

    /**
     * Set the 'nitrogen' class variable
     * @param  nitrogen (Float)
     */
    public int setNitrogen(Float nitrogen) {
        try {
            setNitrogen(nitrogen.floatValue());
        } catch (Exception e) {
            setNitrogenError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nitrogenError;
    } // method setNitrogen

    /**
     * Set the 'nitrogen' class variable
     * @param  nitrogen (String)
     */
    public int setNitrogen(String nitrogen) {
        try {
            setNitrogen(new Float(nitrogen).floatValue());
        } catch (Exception e) {
            setNitrogenError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nitrogenError;
    } // method setNitrogen

    /**
     * Called when an exception has occured
     * @param  nitrogen (String)
     */
    private void setNitrogenError (float nitrogen, Exception e, int error) {
        this.nitrogen = nitrogen;
        nitrogenErrorMessage = e.toString();
        nitrogenError = error;
    } // method setNitrogenError


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
     * Set the 'ph' class variable
     * @param  ph (float)
     */
    public int setPh(float ph) {
        try {
            if ( ((ph == FLOATNULL) ||
                  (ph == FLOATNULL2)) ||
                !((ph < PH_MN) ||
                  (ph > PH_MX)) ) {
                this.ph = ph;
                phError = ERROR_NORMAL;
            } else {
                throw phOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPhError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return phError;
    } // method setPh

    /**
     * Set the 'ph' class variable
     * @param  ph (Integer)
     */
    public int setPh(Integer ph) {
        try {
            setPh(ph.floatValue());
        } catch (Exception e) {
            setPhError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phError;
    } // method setPh

    /**
     * Set the 'ph' class variable
     * @param  ph (Float)
     */
    public int setPh(Float ph) {
        try {
            setPh(ph.floatValue());
        } catch (Exception e) {
            setPhError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phError;
    } // method setPh

    /**
     * Set the 'ph' class variable
     * @param  ph (String)
     */
    public int setPh(String ph) {
        try {
            setPh(new Float(ph).floatValue());
        } catch (Exception e) {
            setPhError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phError;
    } // method setPh

    /**
     * Called when an exception has occured
     * @param  ph (String)
     */
    private void setPhError (float ph, Exception e, int error) {
        this.ph = ph;
        phErrorMessage = e.toString();
        phError = error;
    } // method setPhError


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
     * Return the 'dic' class variable
     * @return dic (float)
     */
    public float getDic() {
        return dic;
    } // method getDic

    /**
     * Return the 'dic' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDic methods
     * @return dic (String)
     */
    public String getDic(String s) {
        return ((dic != FLOATNULL) ? new Float(dic).toString() : "");
    } // method getDic


    /**
     * Return the 'doc' class variable
     * @return doc (float)
     */
    public float getDoc() {
        return doc;
    } // method getDoc

    /**
     * Return the 'doc' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDoc methods
     * @return doc (String)
     */
    public String getDoc(String s) {
        return ((doc != FLOATNULL) ? new Float(doc).toString() : "");
    } // method getDoc


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
     * Return the 'iodene' class variable
     * @return iodene (float)
     */
    public float getIodene() {
        return iodene;
    } // method getIodene

    /**
     * Return the 'iodene' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getIodene methods
     * @return iodene (String)
     */
    public String getIodene(String s) {
        return ((iodene != FLOATNULL) ? new Float(iodene).toString() : "");
    } // method getIodene


    /**
     * Return the 'iodate' class variable
     * @return iodate (float)
     */
    public float getIodate() {
        return iodate;
    } // method getIodate

    /**
     * Return the 'iodate' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getIodate methods
     * @return iodate (String)
     */
    public String getIodate(String s) {
        return ((iodate != FLOATNULL) ? new Float(iodate).toString() : "");
    } // method getIodate


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
     * Return the 'nh3' class variable
     * @return nh3 (float)
     */
    public float getNh3() {
        return nh3;
    } // method getNh3

    /**
     * Return the 'nh3' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNh3 methods
     * @return nh3 (String)
     */
    public String getNh3(String s) {
        return ((nh3 != FLOATNULL) ? new Float(nh3).toString() : "");
    } // method getNh3


    /**
     * Return the 'nitrogen' class variable
     * @return nitrogen (float)
     */
    public float getNitrogen() {
        return nitrogen;
    } // method getNitrogen

    /**
     * Return the 'nitrogen' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNitrogen methods
     * @return nitrogen (String)
     */
    public String getNitrogen(String s) {
        return ((nitrogen != FLOATNULL) ? new Float(nitrogen).toString() : "");
    } // method getNitrogen


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
     * Return the 'ph' class variable
     * @return ph (float)
     */
    public float getPh() {
        return ph;
    } // method getPh

    /**
     * Return the 'ph' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPh methods
     * @return ph (String)
     */
    public String getPh(String s) {
        return ((ph != FLOATNULL) ? new Float(ph).toString() : "");
    } // method getPh


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
            (dic == FLOATNULL) &&
            (doc == FLOATNULL) &&
            (fluoride == FLOATNULL) &&
            (iodene == FLOATNULL) &&
            (iodate == FLOATNULL) &&
            (kjn == FLOATNULL) &&
            (nh3 == FLOATNULL) &&
            (nitrogen == FLOATNULL) &&
            (oxa == FLOATNULL) &&
            (ph == FLOATNULL)) {
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
            dicError +
            docError +
            fluorideError +
            iodeneError +
            iodateError +
            kjnError +
            nh3Error +
            nitrogenError +
            oxaError +
            phError;
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
     * Gets the errorcode for the dic instance variable
     * @return errorcode (int)
     */
    public int getDicError() {
        return dicError;
    } // method getDicError

    /**
     * Gets the errorMessage for the dic instance variable
     * @return errorMessage (String)
     */
    public String getDicErrorMessage() {
        return dicErrorMessage;
    } // method getDicErrorMessage


    /**
     * Gets the errorcode for the doc instance variable
     * @return errorcode (int)
     */
    public int getDocError() {
        return docError;
    } // method getDocError

    /**
     * Gets the errorMessage for the doc instance variable
     * @return errorMessage (String)
     */
    public String getDocErrorMessage() {
        return docErrorMessage;
    } // method getDocErrorMessage


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
     * Gets the errorcode for the iodene instance variable
     * @return errorcode (int)
     */
    public int getIodeneError() {
        return iodeneError;
    } // method getIodeneError

    /**
     * Gets the errorMessage for the iodene instance variable
     * @return errorMessage (String)
     */
    public String getIodeneErrorMessage() {
        return iodeneErrorMessage;
    } // method getIodeneErrorMessage


    /**
     * Gets the errorcode for the iodate instance variable
     * @return errorcode (int)
     */
    public int getIodateError() {
        return iodateError;
    } // method getIodateError

    /**
     * Gets the errorMessage for the iodate instance variable
     * @return errorMessage (String)
     */
    public String getIodateErrorMessage() {
        return iodateErrorMessage;
    } // method getIodateErrorMessage


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
     * Gets the errorcode for the nh3 instance variable
     * @return errorcode (int)
     */
    public int getNh3Error() {
        return nh3Error;
    } // method getNh3Error

    /**
     * Gets the errorMessage for the nh3 instance variable
     * @return errorMessage (String)
     */
    public String getNh3ErrorMessage() {
        return nh3ErrorMessage;
    } // method getNh3ErrorMessage


    /**
     * Gets the errorcode for the nitrogen instance variable
     * @return errorcode (int)
     */
    public int getNitrogenError() {
        return nitrogenError;
    } // method getNitrogenError

    /**
     * Gets the errorMessage for the nitrogen instance variable
     * @return errorMessage (String)
     */
    public String getNitrogenErrorMessage() {
        return nitrogenErrorMessage;
    } // method getNitrogenErrorMessage


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
     * Gets the errorcode for the ph instance variable
     * @return errorcode (int)
     */
    public int getPhError() {
        return phError;
    } // method getPhError

    /**
     * Gets the errorMessage for the ph instance variable
     * @return errorMessage (String)
     */
    public String getPhErrorMessage() {
        return phErrorMessage;
    } // method getPhErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnWatchem1. e.g.<pre>
     * MrnWatchem1 watchem1 = new MrnWatchem1(<val1>);
     * MrnWatchem1 watchem1Array[] = watchem1.get();</pre>
     * will get the MrnWatchem1 record where watphyCode = <val1>.
     * @return Array of MrnWatchem1 (MrnWatchem1[])
     */
    public MrnWatchem1[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnWatchem1 watchem1Array[] =
     *     new MrnWatchem1().get(MrnWatchem1.WATPHY_CODE+"=<val1>");</pre>
     * will get the MrnWatchem1 record where watphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnWatchem1 (MrnWatchem1[])
     */
    public MrnWatchem1[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnWatchem1 watchem1Array[] =
     *     new MrnWatchem1().get("1=1",watchem1.WATPHY_CODE);</pre>
     * will get the all the MrnWatchem1 records, and order them by watphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatchem1 (MrnWatchem1[])
     */
    public MrnWatchem1[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnWatchem1.WATPHY_CODE,MrnWatchem1.DIC;
     * String where = MrnWatchem1.WATPHY_CODE + "=<val1";
     * String order = MrnWatchem1.WATPHY_CODE;
     * MrnWatchem1 watchem1Array[] =
     *     new MrnWatchem1().get(columns, where, order);</pre>
     * will get the watphyCode and dic colums of all MrnWatchem1 records,
     * where watphyCode = <val1>, and order them by watphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatchem1 (MrnWatchem1[])
     */
    public MrnWatchem1[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnWatchem1.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnWatchem1[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int watphyCodeCol = db.getColNumber(WATPHY_CODE);
        int dicCol        = db.getColNumber(DIC);
        int docCol        = db.getColNumber(DOC);
        int fluorideCol   = db.getColNumber(FLUORIDE);
        int iodeneCol     = db.getColNumber(IODENE);
        int iodateCol     = db.getColNumber(IODATE);
        int kjnCol        = db.getColNumber(KJN);
        int nh3Col        = db.getColNumber(NH3);
        int nitrogenCol   = db.getColNumber(NITROGEN);
        int oxaCol        = db.getColNumber(OXA);
        int phCol         = db.getColNumber(PH);
        MrnWatchem1[] cArray = new MrnWatchem1[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnWatchem1();
            if (watphyCodeCol != -1)
                cArray[i].setWatphyCode((String) row.elementAt(watphyCodeCol));
            if (dicCol != -1)
                cArray[i].setDic       ((String) row.elementAt(dicCol));
            if (docCol != -1)
                cArray[i].setDoc       ((String) row.elementAt(docCol));
            if (fluorideCol != -1)
                cArray[i].setFluoride  ((String) row.elementAt(fluorideCol));
            if (iodeneCol != -1)
                cArray[i].setIodene    ((String) row.elementAt(iodeneCol));
            if (iodateCol != -1)
                cArray[i].setIodate    ((String) row.elementAt(iodateCol));
            if (kjnCol != -1)
                cArray[i].setKjn       ((String) row.elementAt(kjnCol));
            if (nh3Col != -1)
                cArray[i].setNh3       ((String) row.elementAt(nh3Col));
            if (nitrogenCol != -1)
                cArray[i].setNitrogen  ((String) row.elementAt(nitrogenCol));
            if (oxaCol != -1)
                cArray[i].setOxa       ((String) row.elementAt(oxaCol));
            if (phCol != -1)
                cArray[i].setPh        ((String) row.elementAt(phCol));
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
     *     new MrnWatchem1(
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
     *     watphyCode = <val1>,
     *     dic        = <val2>,
     *     doc        = <val3>,
     *     fluoride   = <val4>,
     *     iodene     = <val5>,
     *     iodate     = <val6>,
     *     kjn        = <val7>,
     *     nh3        = <val8>,
     *     nitrogen   = <val9>,
     *     oxa        = <val10>,
     *     ph         = <val11>.
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
     * Boolean success = new MrnWatchem1(
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
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where dic = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatchem1 watchem1 = new MrnWatchem1();
     * success = watchem1.del(MrnWatchem1.WATPHY_CODE+"=<val1>");</pre>
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
     * update are taken from the MrnWatchem1 argument, .e.g.<pre>
     * Boolean success
     * MrnWatchem1 updMrnWatchem1 = new MrnWatchem1();
     * updMrnWatchem1.setDic(<val2>);
     * MrnWatchem1 whereMrnWatchem1 = new MrnWatchem1(<val1>);
     * success = whereMrnWatchem1.upd(updMrnWatchem1);</pre>
     * will update Dic to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watchem1  A MrnWatchem1 variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatchem1 watchem1) {
        return db.update (TABLE, createColVals(watchem1), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatchem1 updMrnWatchem1 = new MrnWatchem1();
     * updMrnWatchem1.setDic(<val2>);
     * MrnWatchem1 whereMrnWatchem1 = new MrnWatchem1();
     * success = whereMrnWatchem1.upd(
     *     updMrnWatchem1, MrnWatchem1.WATPHY_CODE+"=<val1>");</pre>
     * will update Dic to <val2> for all records where
     * watphyCode = <val1>.
     * @param  watchem1  A MrnWatchem1 variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatchem1 watchem1, String where) {
        return db.update (TABLE, createColVals(watchem1), where);
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
        if (getDic() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DIC + "=" + getDic("");
        } // if getDic
        if (getDoc() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DOC + "=" + getDoc("");
        } // if getDoc
        if (getFluoride() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FLUORIDE + "=" + getFluoride("");
        } // if getFluoride
        if (getIodene() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + IODENE + "=" + getIodene("");
        } // if getIodene
        if (getIodate() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + IODATE + "=" + getIodate("");
        } // if getIodate
        if (getKjn() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + KJN + "=" + getKjn("");
        } // if getKjn
        if (getNh3() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NH3 + "=" + getNh3("");
        } // if getNh3
        if (getNitrogen() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NITROGEN + "=" + getNitrogen("");
        } // if getNitrogen
        if (getOxa() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + OXA + "=" + getOxa("");
        } // if getOxa
        if (getPh() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PH + "=" + getPh("");
        } // if getPh
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnWatchem1 aVar) {
        String colVals = "";
        if (aVar.getWatphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WATPHY_CODE +"=";
            colVals += (aVar.getWatphyCode() == INTNULL2 ?
                "null" : aVar.getWatphyCode(""));
        } // if aVar.getWatphyCode
        if (aVar.getDic() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DIC +"=";
            colVals += (aVar.getDic() == FLOATNULL2 ?
                "null" : aVar.getDic(""));
        } // if aVar.getDic
        if (aVar.getDoc() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DOC +"=";
            colVals += (aVar.getDoc() == FLOATNULL2 ?
                "null" : aVar.getDoc(""));
        } // if aVar.getDoc
        if (aVar.getFluoride() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FLUORIDE +"=";
            colVals += (aVar.getFluoride() == FLOATNULL2 ?
                "null" : aVar.getFluoride(""));
        } // if aVar.getFluoride
        if (aVar.getIodene() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += IODENE +"=";
            colVals += (aVar.getIodene() == FLOATNULL2 ?
                "null" : aVar.getIodene(""));
        } // if aVar.getIodene
        if (aVar.getIodate() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += IODATE +"=";
            colVals += (aVar.getIodate() == FLOATNULL2 ?
                "null" : aVar.getIodate(""));
        } // if aVar.getIodate
        if (aVar.getKjn() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += KJN +"=";
            colVals += (aVar.getKjn() == FLOATNULL2 ?
                "null" : aVar.getKjn(""));
        } // if aVar.getKjn
        if (aVar.getNh3() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NH3 +"=";
            colVals += (aVar.getNh3() == FLOATNULL2 ?
                "null" : aVar.getNh3(""));
        } // if aVar.getNh3
        if (aVar.getNitrogen() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NITROGEN +"=";
            colVals += (aVar.getNitrogen() == FLOATNULL2 ?
                "null" : aVar.getNitrogen(""));
        } // if aVar.getNitrogen
        if (aVar.getOxa() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += OXA +"=";
            colVals += (aVar.getOxa() == FLOATNULL2 ?
                "null" : aVar.getOxa(""));
        } // if aVar.getOxa
        if (aVar.getPh() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PH +"=";
            colVals += (aVar.getPh() == FLOATNULL2 ?
                "null" : aVar.getPh(""));
        } // if aVar.getPh
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = WATPHY_CODE;
        if (getDic() != FLOATNULL) {
            columns = columns + "," + DIC;
        } // if getDic
        if (getDoc() != FLOATNULL) {
            columns = columns + "," + DOC;
        } // if getDoc
        if (getFluoride() != FLOATNULL) {
            columns = columns + "," + FLUORIDE;
        } // if getFluoride
        if (getIodene() != FLOATNULL) {
            columns = columns + "," + IODENE;
        } // if getIodene
        if (getIodate() != FLOATNULL) {
            columns = columns + "," + IODATE;
        } // if getIodate
        if (getKjn() != FLOATNULL) {
            columns = columns + "," + KJN;
        } // if getKjn
        if (getNh3() != FLOATNULL) {
            columns = columns + "," + NH3;
        } // if getNh3
        if (getNitrogen() != FLOATNULL) {
            columns = columns + "," + NITROGEN;
        } // if getNitrogen
        if (getOxa() != FLOATNULL) {
            columns = columns + "," + OXA;
        } // if getOxa
        if (getPh() != FLOATNULL) {
            columns = columns + "," + PH;
        } // if getPh
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getWatphyCode("");
        if (getDic() != FLOATNULL) {
            values  = values  + "," + getDic("");
        } // if getDic
        if (getDoc() != FLOATNULL) {
            values  = values  + "," + getDoc("");
        } // if getDoc
        if (getFluoride() != FLOATNULL) {
            values  = values  + "," + getFluoride("");
        } // if getFluoride
        if (getIodene() != FLOATNULL) {
            values  = values  + "," + getIodene("");
        } // if getIodene
        if (getIodate() != FLOATNULL) {
            values  = values  + "," + getIodate("");
        } // if getIodate
        if (getKjn() != FLOATNULL) {
            values  = values  + "," + getKjn("");
        } // if getKjn
        if (getNh3() != FLOATNULL) {
            values  = values  + "," + getNh3("");
        } // if getNh3
        if (getNitrogen() != FLOATNULL) {
            values  = values  + "," + getNitrogen("");
        } // if getNitrogen
        if (getOxa() != FLOATNULL) {
            values  = values  + "," + getOxa("");
        } // if getOxa
        if (getPh() != FLOATNULL) {
            values  = values  + "," + getPh("");
        } // if getPh
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
            getDic("")        + "|" +
            getDoc("")        + "|" +
            getFluoride("")   + "|" +
            getIodene("")     + "|" +
            getIodate("")     + "|" +
            getKjn("")        + "|" +
            getNh3("")        + "|" +
            getNitrogen("")   + "|" +
            getOxa("")        + "|" +
            getPh("")         + "|";
    } // method toString

} // class MrnWatchem1
