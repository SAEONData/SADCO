package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the LOG_VOS_LOADS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 041124 - SIT Group
 * @version
 * 041124 - GenTableClassB class - create class<br>
 */
public class LogVosLoads extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "LOG_VOS_LOADS";
    /** The loadid field name */
    public static final String LOADID = "LOADID";
    /** The vosLoadId field name */
    public static final String VOS_LOAD_ID = "VOS_LOAD_ID";
    /** The source field name */
    public static final String SOURCE = "SOURCE";
    /** The dateLoaded field name */
    public static final String DATE_LOADED = "DATE_LOADED";
    /** The mRecsLoaded field name */
    public static final String M_RECS_LOADED = "M_RECS_LOADED";
    /** The mRecsDup field name */
    public static final String M_RECS_DUP = "M_RECS_DUP";
    /** The aRecsLoaded field name */
    public static final String A_RECS_LOADED = "A_RECS_LOADED";
    /** The aRecsDup field name */
    public static final String A_RECS_DUP = "A_RECS_DUP";
    /** The dateStart field name */
    public static final String DATE_START = "DATE_START";
    /** The dateEnd field name */
    public static final String DATE_END = "DATE_END";
    /** The respPerson field name */
    public static final String RESP_PERSON = "RESP_PERSON";
    /** The recsRejected field name */
    public static final String RECS_REJECTED = "RECS_REJECTED";
    /** The wmoRejected field name */
    public static final String WMO_REJECTED = "WMO_REJECTED";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    loadid;
    private int       vosLoadId;
    private String    source;
    private Timestamp dateLoaded;
    private int       mRecsLoaded;
    private int       mRecsDup;
    private int       aRecsLoaded;
    private int       aRecsDup;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private String    respPerson;
    private int       recsRejected;
    private int       wmoRejected;

    /** The error variables  */
    private int loadidError       = ERROR_NORMAL;
    private int vosLoadIdError    = ERROR_NORMAL;
    private int sourceError       = ERROR_NORMAL;
    private int dateLoadedError   = ERROR_NORMAL;
    private int mRecsLoadedError  = ERROR_NORMAL;
    private int mRecsDupError     = ERROR_NORMAL;
    private int aRecsLoadedError  = ERROR_NORMAL;
    private int aRecsDupError     = ERROR_NORMAL;
    private int dateStartError    = ERROR_NORMAL;
    private int dateEndError      = ERROR_NORMAL;
    private int respPersonError   = ERROR_NORMAL;
    private int recsRejectedError = ERROR_NORMAL;
    private int wmoRejectedError  = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String loadidErrorMessage       = "";
    private String vosLoadIdErrorMessage    = "";
    private String sourceErrorMessage       = "";
    private String dateLoadedErrorMessage   = "";
    private String mRecsLoadedErrorMessage  = "";
    private String mRecsDupErrorMessage     = "";
    private String aRecsLoadedErrorMessage  = "";
    private String aRecsDupErrorMessage     = "";
    private String dateStartErrorMessage    = "";
    private String dateEndErrorMessage      = "";
    private String respPersonErrorMessage   = "";
    private String recsRejectedErrorMessage = "";
    private String wmoRejectedErrorMessage  = "";

    /** The min-max constants for all numerics */
    public static final int       VOS_LOAD_ID_MN = INTMIN;
    public static final int       VOS_LOAD_ID_MX = INTMAX;
    public static final Timestamp DATE_LOADED_MN = DATEMIN;
    public static final Timestamp DATE_LOADED_MX = DATEMAX;
    public static final int       M_RECS_LOADED_MN = INTMIN;
    public static final int       M_RECS_LOADED_MX = INTMAX;
    public static final int       M_RECS_DUP_MN = INTMIN;
    public static final int       M_RECS_DUP_MX = INTMAX;
    public static final int       A_RECS_LOADED_MN = INTMIN;
    public static final int       A_RECS_LOADED_MX = INTMAX;
    public static final int       A_RECS_DUP_MN = INTMIN;
    public static final int       A_RECS_DUP_MX = INTMAX;
    public static final Timestamp DATE_START_MN = DATEMIN;
    public static final Timestamp DATE_START_MX = DATEMAX;
    public static final Timestamp DATE_END_MN = DATEMIN;
    public static final Timestamp DATE_END_MX = DATEMAX;
    public static final int       RECS_REJECTED_MN = INTMIN;
    public static final int       RECS_REJECTED_MX = INTMAX;
    public static final int       WMO_REJECTED_MN = INTMIN;
    public static final int       WMO_REJECTED_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception vosLoadIdOutOfBoundsException =
        new Exception ("'vosLoadId' out of bounds: " +
            VOS_LOAD_ID_MN + " - " + VOS_LOAD_ID_MX);
    Exception dateLoadedException =
        new Exception ("'dateLoaded' is null");
    Exception dateLoadedOutOfBoundsException =
        new Exception ("'dateLoaded' out of bounds: " +
            DATE_LOADED_MN + " - " + DATE_LOADED_MX);
    Exception mRecsLoadedOutOfBoundsException =
        new Exception ("'mRecsLoaded' out of bounds: " +
            M_RECS_LOADED_MN + " - " + M_RECS_LOADED_MX);
    Exception mRecsDupOutOfBoundsException =
        new Exception ("'mRecsDup' out of bounds: " +
            M_RECS_DUP_MN + " - " + M_RECS_DUP_MX);
    Exception aRecsLoadedOutOfBoundsException =
        new Exception ("'aRecsLoaded' out of bounds: " +
            A_RECS_LOADED_MN + " - " + A_RECS_LOADED_MX);
    Exception aRecsDupOutOfBoundsException =
        new Exception ("'aRecsDup' out of bounds: " +
            A_RECS_DUP_MN + " - " + A_RECS_DUP_MX);
    Exception dateStartException =
        new Exception ("'dateStart' is null");
    Exception dateStartOutOfBoundsException =
        new Exception ("'dateStart' out of bounds: " +
            DATE_START_MN + " - " + DATE_START_MX);
    Exception dateEndException =
        new Exception ("'dateEnd' is null");
    Exception dateEndOutOfBoundsException =
        new Exception ("'dateEnd' out of bounds: " +
            DATE_END_MN + " - " + DATE_END_MX);
    Exception recsRejectedOutOfBoundsException =
        new Exception ("'recsRejected' out of bounds: " +
            RECS_REJECTED_MN + " - " + RECS_REJECTED_MX);
    Exception wmoRejectedOutOfBoundsException =
        new Exception ("'wmoRejected' out of bounds: " +
            WMO_REJECTED_MN + " - " + WMO_REJECTED_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public LogVosLoads() {
        clearVars();
        if (dbg) System.out.println ("<br>in LogVosLoads constructor 1"); // debug
    } // LogVosLoads constructor

    /**
     * Instantiate a LogVosLoads object and initialize the instance variables.
     * @param loadid  The loadid (String)
     */
    public LogVosLoads(
            String loadid) {
        this();
        setLoadid       (loadid      );
        if (dbg) System.out.println ("<br>in LogVosLoads constructor 2"); // debug
    } // LogVosLoads constructor

    /**
     * Instantiate a LogVosLoads object and initialize the instance variables.
     * @param loadid        The loadid       (String)
     * @param vosLoadId     The vosLoadId    (int)
     * @param source        The source       (String)
     * @param dateLoaded    The dateLoaded   (java.util.Date)
     * @param mRecsLoaded   The mRecsLoaded  (int)
     * @param mRecsDup      The mRecsDup     (int)
     * @param aRecsLoaded   The aRecsLoaded  (int)
     * @param aRecsDup      The aRecsDup     (int)
     * @param dateStart     The dateStart    (java.util.Date)
     * @param dateEnd       The dateEnd      (java.util.Date)
     * @param respPerson    The respPerson   (String)
     * @param recsRejected  The recsRejected (int)
     * @param wmoRejected   The wmoRejected  (int)
     */
    public LogVosLoads(
            String         loadid,
            int            vosLoadId,
            String         source,
            java.util.Date dateLoaded,
            int            mRecsLoaded,
            int            mRecsDup,
            int            aRecsLoaded,
            int            aRecsDup,
            java.util.Date dateStart,
            java.util.Date dateEnd,
            String         respPerson,
            int            recsRejected,
            int            wmoRejected) {
        this();
        setLoadid       (loadid      );
        setVosLoadId    (vosLoadId   );
        setSource       (source      );
        setDateLoaded   (dateLoaded  );
        setMRecsLoaded  (mRecsLoaded );
        setMRecsDup     (mRecsDup    );
        setARecsLoaded  (aRecsLoaded );
        setARecsDup     (aRecsDup    );
        setDateStart    (dateStart   );
        setDateEnd      (dateEnd     );
        setRespPerson   (respPerson  );
        setRecsRejected (recsRejected);
        setWmoRejected  (wmoRejected );
        if (dbg) System.out.println ("<br>in LogVosLoads constructor 3"); // debug
    } // LogVosLoads constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setLoadid       (CHARNULL);
        setVosLoadId    (INTNULL );
        setSource       (CHARNULL);
        setDateLoaded   (DATENULL);
        setMRecsLoaded  (INTNULL );
        setMRecsDup     (INTNULL );
        setARecsLoaded  (INTNULL );
        setARecsDup     (INTNULL );
        setDateStart    (DATENULL);
        setDateEnd      (DATENULL);
        setRespPerson   (CHARNULL);
        setRecsRejected (INTNULL );
        setWmoRejected  (INTNULL );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'loadid' class variable
     * @param  loadid (String)
     */
    public int setLoadid(String loadid) {
        try {
            this.loadid = loadid;
            if (this.loadid != CHARNULL) {
                this.loadid = stripCRLF(this.loadid.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>loadid = " + this.loadid);
        } catch (Exception e) {
            setLoadidError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return loadidError;
    } // method setLoadid

    /**
     * Called when an exception has occured
     * @param  loadid (String)
     */
    private void setLoadidError (String loadid, Exception e, int error) {
        this.loadid = loadid;
        loadidErrorMessage = e.toString();
        loadidError = error;
    } // method setLoadidError


    /**
     * Set the 'vosLoadId' class variable
     * @param  vosLoadId (int)
     */
    public int setVosLoadId(int vosLoadId) {
        try {
            if ( ((vosLoadId == INTNULL) ||
                  (vosLoadId == INTNULL2)) ||
                !((vosLoadId < VOS_LOAD_ID_MN) ||
                  (vosLoadId > VOS_LOAD_ID_MX)) ) {
                this.vosLoadId = vosLoadId;
                vosLoadIdError = ERROR_NORMAL;
            } else {
                throw vosLoadIdOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setVosLoadIdError(INTNULL, e, ERROR_LOCAL);
        } // try
        return vosLoadIdError;
    } // method setVosLoadId

    /**
     * Set the 'vosLoadId' class variable
     * @param  vosLoadId (Integer)
     */
    public int setVosLoadId(Integer vosLoadId) {
        try {
            setVosLoadId(vosLoadId.intValue());
        } catch (Exception e) {
            setVosLoadIdError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return vosLoadIdError;
    } // method setVosLoadId

    /**
     * Set the 'vosLoadId' class variable
     * @param  vosLoadId (Float)
     */
    public int setVosLoadId(Float vosLoadId) {
        try {
            if (vosLoadId.floatValue() == FLOATNULL) {
                setVosLoadId(INTNULL);
            } else {
                setVosLoadId(vosLoadId.intValue());
            } // if (vosLoadId.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setVosLoadIdError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return vosLoadIdError;
    } // method setVosLoadId

    /**
     * Set the 'vosLoadId' class variable
     * @param  vosLoadId (String)
     */
    public int setVosLoadId(String vosLoadId) {
        try {
            setVosLoadId(new Integer(vosLoadId).intValue());
        } catch (Exception e) {
            setVosLoadIdError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return vosLoadIdError;
    } // method setVosLoadId

    /**
     * Called when an exception has occured
     * @param  vosLoadId (String)
     */
    private void setVosLoadIdError (int vosLoadId, Exception e, int error) {
        this.vosLoadId = vosLoadId;
        vosLoadIdErrorMessage = e.toString();
        vosLoadIdError = error;
    } // method setVosLoadIdError


    /**
     * Set the 'source' class variable
     * @param  source (String)
     */
    public int setSource(String source) {
        try {
            this.source = source;
            if (this.source != CHARNULL) {
                this.source = stripCRLF(this.source.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>source = " + this.source);
        } catch (Exception e) {
            setSourceError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return sourceError;
    } // method setSource

    /**
     * Called when an exception has occured
     * @param  source (String)
     */
    private void setSourceError (String source, Exception e, int error) {
        this.source = source;
        sourceErrorMessage = e.toString();
        sourceError = error;
    } // method setSourceError


    /**
     * Set the 'dateLoaded' class variable
     * @param  dateLoaded (Timestamp)
     */
    public int setDateLoaded(Timestamp dateLoaded) {
        try {
            if (dateLoaded == null) { this.dateLoaded = DATENULL; }
            if ( (dateLoaded.equals(DATENULL) ||
                  dateLoaded.equals(DATENULL2)) ||
                !(dateLoaded.before(DATE_LOADED_MN) ||
                  dateLoaded.after(DATE_LOADED_MX)) ) {
                this.dateLoaded = dateLoaded;
                dateLoadedError = ERROR_NORMAL;
            } else {
                throw dateLoadedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateLoadedError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateLoadedError;
    } // method setDateLoaded

    /**
     * Set the 'dateLoaded' class variable
     * @param  dateLoaded (java.util.Date)
     */
    public int setDateLoaded(java.util.Date dateLoaded) {
        try {
            setDateLoaded(new Timestamp(dateLoaded.getTime()));
        } catch (Exception e) {
            setDateLoadedError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateLoadedError;
    } // method setDateLoaded

    /**
     * Set the 'dateLoaded' class variable
     * @param  dateLoaded (String)
     */
    public int setDateLoaded(String dateLoaded) {
        try {
            int len = dateLoaded.length();
            switch (len) {
            // date &/or times
                case 4: dateLoaded += "-01";
                case 7: dateLoaded += "-01";
                case 10: dateLoaded += " 00";
                case 13: dateLoaded += ":00";
                case 16: dateLoaded += ":00"; break;
                // times only
                case 5: dateLoaded = "1800-01-01 " + dateLoaded + ":00"; break;
                case 8: dateLoaded = "1800-01-01 " + dateLoaded; break;
            } // switch
            if (dbg) System.out.println ("dateLoaded = " + dateLoaded);
            setDateLoaded(Timestamp.valueOf(dateLoaded));
        } catch (Exception e) {
            setDateLoadedError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateLoadedError;
    } // method setDateLoaded

    /**
     * Called when an exception has occured
     * @param  dateLoaded (String)
     */
    private void setDateLoadedError (Timestamp dateLoaded, Exception e, int error) {
        this.dateLoaded = dateLoaded;
        dateLoadedErrorMessage = e.toString();
        dateLoadedError = error;
    } // method setDateLoadedError


    /**
     * Set the 'mRecsLoaded' class variable
     * @param  mRecsLoaded (int)
     */
    public int setMRecsLoaded(int mRecsLoaded) {
        try {
            if ( ((mRecsLoaded == INTNULL) ||
                  (mRecsLoaded == INTNULL2)) ||
                !((mRecsLoaded < M_RECS_LOADED_MN) ||
                  (mRecsLoaded > M_RECS_LOADED_MX)) ) {
                this.mRecsLoaded = mRecsLoaded;
                mRecsLoadedError = ERROR_NORMAL;
            } else {
                throw mRecsLoadedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMRecsLoadedError(INTNULL, e, ERROR_LOCAL);
        } // try
        return mRecsLoadedError;
    } // method setMRecsLoaded

    /**
     * Set the 'mRecsLoaded' class variable
     * @param  mRecsLoaded (Integer)
     */
    public int setMRecsLoaded(Integer mRecsLoaded) {
        try {
            setMRecsLoaded(mRecsLoaded.intValue());
        } catch (Exception e) {
            setMRecsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return mRecsLoadedError;
    } // method setMRecsLoaded

    /**
     * Set the 'mRecsLoaded' class variable
     * @param  mRecsLoaded (Float)
     */
    public int setMRecsLoaded(Float mRecsLoaded) {
        try {
            if (mRecsLoaded.floatValue() == FLOATNULL) {
                setMRecsLoaded(INTNULL);
            } else {
                setMRecsLoaded(mRecsLoaded.intValue());
            } // if (mRecsLoaded.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMRecsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return mRecsLoadedError;
    } // method setMRecsLoaded

    /**
     * Set the 'mRecsLoaded' class variable
     * @param  mRecsLoaded (String)
     */
    public int setMRecsLoaded(String mRecsLoaded) {
        try {
            setMRecsLoaded(new Integer(mRecsLoaded).intValue());
        } catch (Exception e) {
            setMRecsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return mRecsLoadedError;
    } // method setMRecsLoaded

    /**
     * Called when an exception has occured
     * @param  mRecsLoaded (String)
     */
    private void setMRecsLoadedError (int mRecsLoaded, Exception e, int error) {
        this.mRecsLoaded = mRecsLoaded;
        mRecsLoadedErrorMessage = e.toString();
        mRecsLoadedError = error;
    } // method setMRecsLoadedError


    /**
     * Set the 'mRecsDup' class variable
     * @param  mRecsDup (int)
     */
    public int setMRecsDup(int mRecsDup) {
        try {
            if ( ((mRecsDup == INTNULL) ||
                  (mRecsDup == INTNULL2)) ||
                !((mRecsDup < M_RECS_DUP_MN) ||
                  (mRecsDup > M_RECS_DUP_MX)) ) {
                this.mRecsDup = mRecsDup;
                mRecsDupError = ERROR_NORMAL;
            } else {
                throw mRecsDupOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMRecsDupError(INTNULL, e, ERROR_LOCAL);
        } // try
        return mRecsDupError;
    } // method setMRecsDup

    /**
     * Set the 'mRecsDup' class variable
     * @param  mRecsDup (Integer)
     */
    public int setMRecsDup(Integer mRecsDup) {
        try {
            setMRecsDup(mRecsDup.intValue());
        } catch (Exception e) {
            setMRecsDupError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return mRecsDupError;
    } // method setMRecsDup

    /**
     * Set the 'mRecsDup' class variable
     * @param  mRecsDup (Float)
     */
    public int setMRecsDup(Float mRecsDup) {
        try {
            if (mRecsDup.floatValue() == FLOATNULL) {
                setMRecsDup(INTNULL);
            } else {
                setMRecsDup(mRecsDup.intValue());
            } // if (mRecsDup.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMRecsDupError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return mRecsDupError;
    } // method setMRecsDup

    /**
     * Set the 'mRecsDup' class variable
     * @param  mRecsDup (String)
     */
    public int setMRecsDup(String mRecsDup) {
        try {
            setMRecsDup(new Integer(mRecsDup).intValue());
        } catch (Exception e) {
            setMRecsDupError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return mRecsDupError;
    } // method setMRecsDup

    /**
     * Called when an exception has occured
     * @param  mRecsDup (String)
     */
    private void setMRecsDupError (int mRecsDup, Exception e, int error) {
        this.mRecsDup = mRecsDup;
        mRecsDupErrorMessage = e.toString();
        mRecsDupError = error;
    } // method setMRecsDupError


    /**
     * Set the 'aRecsLoaded' class variable
     * @param  aRecsLoaded (int)
     */
    public int setARecsLoaded(int aRecsLoaded) {
        try {
            if ( ((aRecsLoaded == INTNULL) ||
                  (aRecsLoaded == INTNULL2)) ||
                !((aRecsLoaded < A_RECS_LOADED_MN) ||
                  (aRecsLoaded > A_RECS_LOADED_MX)) ) {
                this.aRecsLoaded = aRecsLoaded;
                aRecsLoadedError = ERROR_NORMAL;
            } else {
                throw aRecsLoadedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setARecsLoadedError(INTNULL, e, ERROR_LOCAL);
        } // try
        return aRecsLoadedError;
    } // method setARecsLoaded

    /**
     * Set the 'aRecsLoaded' class variable
     * @param  aRecsLoaded (Integer)
     */
    public int setARecsLoaded(Integer aRecsLoaded) {
        try {
            setARecsLoaded(aRecsLoaded.intValue());
        } catch (Exception e) {
            setARecsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aRecsLoadedError;
    } // method setARecsLoaded

    /**
     * Set the 'aRecsLoaded' class variable
     * @param  aRecsLoaded (Float)
     */
    public int setARecsLoaded(Float aRecsLoaded) {
        try {
            if (aRecsLoaded.floatValue() == FLOATNULL) {
                setARecsLoaded(INTNULL);
            } else {
                setARecsLoaded(aRecsLoaded.intValue());
            } // if (aRecsLoaded.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setARecsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aRecsLoadedError;
    } // method setARecsLoaded

    /**
     * Set the 'aRecsLoaded' class variable
     * @param  aRecsLoaded (String)
     */
    public int setARecsLoaded(String aRecsLoaded) {
        try {
            setARecsLoaded(new Integer(aRecsLoaded).intValue());
        } catch (Exception e) {
            setARecsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aRecsLoadedError;
    } // method setARecsLoaded

    /**
     * Called when an exception has occured
     * @param  aRecsLoaded (String)
     */
    private void setARecsLoadedError (int aRecsLoaded, Exception e, int error) {
        this.aRecsLoaded = aRecsLoaded;
        aRecsLoadedErrorMessage = e.toString();
        aRecsLoadedError = error;
    } // method setARecsLoadedError


    /**
     * Set the 'aRecsDup' class variable
     * @param  aRecsDup (int)
     */
    public int setARecsDup(int aRecsDup) {
        try {
            if ( ((aRecsDup == INTNULL) ||
                  (aRecsDup == INTNULL2)) ||
                !((aRecsDup < A_RECS_DUP_MN) ||
                  (aRecsDup > A_RECS_DUP_MX)) ) {
                this.aRecsDup = aRecsDup;
                aRecsDupError = ERROR_NORMAL;
            } else {
                throw aRecsDupOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setARecsDupError(INTNULL, e, ERROR_LOCAL);
        } // try
        return aRecsDupError;
    } // method setARecsDup

    /**
     * Set the 'aRecsDup' class variable
     * @param  aRecsDup (Integer)
     */
    public int setARecsDup(Integer aRecsDup) {
        try {
            setARecsDup(aRecsDup.intValue());
        } catch (Exception e) {
            setARecsDupError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aRecsDupError;
    } // method setARecsDup

    /**
     * Set the 'aRecsDup' class variable
     * @param  aRecsDup (Float)
     */
    public int setARecsDup(Float aRecsDup) {
        try {
            if (aRecsDup.floatValue() == FLOATNULL) {
                setARecsDup(INTNULL);
            } else {
                setARecsDup(aRecsDup.intValue());
            } // if (aRecsDup.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setARecsDupError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aRecsDupError;
    } // method setARecsDup

    /**
     * Set the 'aRecsDup' class variable
     * @param  aRecsDup (String)
     */
    public int setARecsDup(String aRecsDup) {
        try {
            setARecsDup(new Integer(aRecsDup).intValue());
        } catch (Exception e) {
            setARecsDupError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return aRecsDupError;
    } // method setARecsDup

    /**
     * Called when an exception has occured
     * @param  aRecsDup (String)
     */
    private void setARecsDupError (int aRecsDup, Exception e, int error) {
        this.aRecsDup = aRecsDup;
        aRecsDupErrorMessage = e.toString();
        aRecsDupError = error;
    } // method setARecsDupError


    /**
     * Set the 'dateStart' class variable
     * @param  dateStart (Timestamp)
     */
    public int setDateStart(Timestamp dateStart) {
        try {
            if (dateStart == null) { this.dateStart = DATENULL; }
            if ( (dateStart.equals(DATENULL) ||
                  dateStart.equals(DATENULL2)) ||
                !(dateStart.before(DATE_START_MN) ||
                  dateStart.after(DATE_START_MX)) ) {
                this.dateStart = dateStart;
                dateStartError = ERROR_NORMAL;
            } else {
                throw dateStartOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateStartError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateStartError;
    } // method setDateStart

    /**
     * Set the 'dateStart' class variable
     * @param  dateStart (java.util.Date)
     */
    public int setDateStart(java.util.Date dateStart) {
        try {
            setDateStart(new Timestamp(dateStart.getTime()));
        } catch (Exception e) {
            setDateStartError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateStartError;
    } // method setDateStart

    /**
     * Set the 'dateStart' class variable
     * @param  dateStart (String)
     */
    public int setDateStart(String dateStart) {
        try {
            int len = dateStart.length();
            switch (len) {
            // date &/or times
                case 4: dateStart += "-01";
                case 7: dateStart += "-01";
                case 10: dateStart += " 00";
                case 13: dateStart += ":00";
                case 16: dateStart += ":00"; break;
                // times only
                case 5: dateStart = "1800-01-01 " + dateStart + ":00"; break;
                case 8: dateStart = "1800-01-01 " + dateStart; break;
            } // switch
            if (dbg) System.out.println ("dateStart = " + dateStart);
            setDateStart(Timestamp.valueOf(dateStart));
        } catch (Exception e) {
            setDateStartError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateStartError;
    } // method setDateStart

    /**
     * Called when an exception has occured
     * @param  dateStart (String)
     */
    private void setDateStartError (Timestamp dateStart, Exception e, int error) {
        this.dateStart = dateStart;
        dateStartErrorMessage = e.toString();
        dateStartError = error;
    } // method setDateStartError


    /**
     * Set the 'dateEnd' class variable
     * @param  dateEnd (Timestamp)
     */
    public int setDateEnd(Timestamp dateEnd) {
        try {
            if (dateEnd == null) { this.dateEnd = DATENULL; }
            if ( (dateEnd.equals(DATENULL) ||
                  dateEnd.equals(DATENULL2)) ||
                !(dateEnd.before(DATE_END_MN) ||
                  dateEnd.after(DATE_END_MX)) ) {
                this.dateEnd = dateEnd;
                dateEndError = ERROR_NORMAL;
            } else {
                throw dateEndOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateEndError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateEndError;
    } // method setDateEnd

    /**
     * Set the 'dateEnd' class variable
     * @param  dateEnd (java.util.Date)
     */
    public int setDateEnd(java.util.Date dateEnd) {
        try {
            setDateEnd(new Timestamp(dateEnd.getTime()));
        } catch (Exception e) {
            setDateEndError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateEndError;
    } // method setDateEnd

    /**
     * Set the 'dateEnd' class variable
     * @param  dateEnd (String)
     */
    public int setDateEnd(String dateEnd) {
        try {
            int len = dateEnd.length();
            switch (len) {
            // date &/or times
                case 4: dateEnd += "-01";
                case 7: dateEnd += "-01";
                case 10: dateEnd += " 00";
                case 13: dateEnd += ":00";
                case 16: dateEnd += ":00"; break;
                // times only
                case 5: dateEnd = "1800-01-01 " + dateEnd + ":00"; break;
                case 8: dateEnd = "1800-01-01 " + dateEnd; break;
            } // switch
            if (dbg) System.out.println ("dateEnd = " + dateEnd);
            setDateEnd(Timestamp.valueOf(dateEnd));
        } catch (Exception e) {
            setDateEndError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateEndError;
    } // method setDateEnd

    /**
     * Called when an exception has occured
     * @param  dateEnd (String)
     */
    private void setDateEndError (Timestamp dateEnd, Exception e, int error) {
        this.dateEnd = dateEnd;
        dateEndErrorMessage = e.toString();
        dateEndError = error;
    } // method setDateEndError


    /**
     * Set the 'respPerson' class variable
     * @param  respPerson (String)
     */
    public int setRespPerson(String respPerson) {
        try {
            this.respPerson = respPerson;
            if (this.respPerson != CHARNULL) {
                this.respPerson = stripCRLF(this.respPerson.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>respPerson = " + this.respPerson);
        } catch (Exception e) {
            setRespPersonError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return respPersonError;
    } // method setRespPerson

    /**
     * Called when an exception has occured
     * @param  respPerson (String)
     */
    private void setRespPersonError (String respPerson, Exception e, int error) {
        this.respPerson = respPerson;
        respPersonErrorMessage = e.toString();
        respPersonError = error;
    } // method setRespPersonError


    /**
     * Set the 'recsRejected' class variable
     * @param  recsRejected (int)
     */
    public int setRecsRejected(int recsRejected) {
        try {
            if ( ((recsRejected == INTNULL) ||
                  (recsRejected == INTNULL2)) ||
                !((recsRejected < RECS_REJECTED_MN) ||
                  (recsRejected > RECS_REJECTED_MX)) ) {
                this.recsRejected = recsRejected;
                recsRejectedError = ERROR_NORMAL;
            } else {
                throw recsRejectedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setRecsRejectedError(INTNULL, e, ERROR_LOCAL);
        } // try
        return recsRejectedError;
    } // method setRecsRejected

    /**
     * Set the 'recsRejected' class variable
     * @param  recsRejected (Integer)
     */
    public int setRecsRejected(Integer recsRejected) {
        try {
            setRecsRejected(recsRejected.intValue());
        } catch (Exception e) {
            setRecsRejectedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recsRejectedError;
    } // method setRecsRejected

    /**
     * Set the 'recsRejected' class variable
     * @param  recsRejected (Float)
     */
    public int setRecsRejected(Float recsRejected) {
        try {
            if (recsRejected.floatValue() == FLOATNULL) {
                setRecsRejected(INTNULL);
            } else {
                setRecsRejected(recsRejected.intValue());
            } // if (recsRejected.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setRecsRejectedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recsRejectedError;
    } // method setRecsRejected

    /**
     * Set the 'recsRejected' class variable
     * @param  recsRejected (String)
     */
    public int setRecsRejected(String recsRejected) {
        try {
            setRecsRejected(new Integer(recsRejected).intValue());
        } catch (Exception e) {
            setRecsRejectedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recsRejectedError;
    } // method setRecsRejected

    /**
     * Called when an exception has occured
     * @param  recsRejected (String)
     */
    private void setRecsRejectedError (int recsRejected, Exception e, int error) {
        this.recsRejected = recsRejected;
        recsRejectedErrorMessage = e.toString();
        recsRejectedError = error;
    } // method setRecsRejectedError


    /**
     * Set the 'wmoRejected' class variable
     * @param  wmoRejected (int)
     */
    public int setWmoRejected(int wmoRejected) {
        try {
            if ( ((wmoRejected == INTNULL) ||
                  (wmoRejected == INTNULL2)) ||
                !((wmoRejected < WMO_REJECTED_MN) ||
                  (wmoRejected > WMO_REJECTED_MX)) ) {
                this.wmoRejected = wmoRejected;
                wmoRejectedError = ERROR_NORMAL;
            } else {
                throw wmoRejectedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setWmoRejectedError(INTNULL, e, ERROR_LOCAL);
        } // try
        return wmoRejectedError;
    } // method setWmoRejected

    /**
     * Set the 'wmoRejected' class variable
     * @param  wmoRejected (Integer)
     */
    public int setWmoRejected(Integer wmoRejected) {
        try {
            setWmoRejected(wmoRejected.intValue());
        } catch (Exception e) {
            setWmoRejectedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return wmoRejectedError;
    } // method setWmoRejected

    /**
     * Set the 'wmoRejected' class variable
     * @param  wmoRejected (Float)
     */
    public int setWmoRejected(Float wmoRejected) {
        try {
            if (wmoRejected.floatValue() == FLOATNULL) {
                setWmoRejected(INTNULL);
            } else {
                setWmoRejected(wmoRejected.intValue());
            } // if (wmoRejected.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setWmoRejectedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return wmoRejectedError;
    } // method setWmoRejected

    /**
     * Set the 'wmoRejected' class variable
     * @param  wmoRejected (String)
     */
    public int setWmoRejected(String wmoRejected) {
        try {
            setWmoRejected(new Integer(wmoRejected).intValue());
        } catch (Exception e) {
            setWmoRejectedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return wmoRejectedError;
    } // method setWmoRejected

    /**
     * Called when an exception has occured
     * @param  wmoRejected (String)
     */
    private void setWmoRejectedError (int wmoRejected, Exception e, int error) {
        this.wmoRejected = wmoRejected;
        wmoRejectedErrorMessage = e.toString();
        wmoRejectedError = error;
    } // method setWmoRejectedError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'loadid' class variable
     * @return loadid (String)
     */
    public String getLoadid() {
        return loadid;
    } // method getLoadid

    /**
     * Return the 'loadid' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLoadid methods
     * @return loadid (String)
     */
    public String getLoadid(String s) {
        return (loadid != CHARNULL ? loadid.replace('"','\'') : "");
    } // method getLoadid


    /**
     * Return the 'vosLoadId' class variable
     * @return vosLoadId (int)
     */
    public int getVosLoadId() {
        return vosLoadId;
    } // method getVosLoadId

    /**
     * Return the 'vosLoadId' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getVosLoadId methods
     * @return vosLoadId (String)
     */
    public String getVosLoadId(String s) {
        return ((vosLoadId != INTNULL) ? new Integer(vosLoadId).toString() : "");
    } // method getVosLoadId


    /**
     * Return the 'source' class variable
     * @return source (String)
     */
    public String getSource() {
        return source;
    } // method getSource

    /**
     * Return the 'source' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSource methods
     * @return source (String)
     */
    public String getSource(String s) {
        return (source != CHARNULL ? source.replace('"','\'') : "");
    } // method getSource


    /**
     * Return the 'dateLoaded' class variable
     * @return dateLoaded (Timestamp)
     */
    public Timestamp getDateLoaded() {
        return dateLoaded;
    } // method getDateLoaded

    /**
     * Return the 'dateLoaded' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateLoaded methods
     * @return dateLoaded (String)
     */
    public String getDateLoaded(String s) {
        if (dateLoaded.equals(DATENULL)) {
            return ("");
        } else {
            String dateLoadedStr = dateLoaded.toString();
            return dateLoadedStr.substring(0,dateLoadedStr.indexOf('.'));
        } // if
    } // method getDateLoaded


    /**
     * Return the 'mRecsLoaded' class variable
     * @return mRecsLoaded (int)
     */
    public int getMRecsLoaded() {
        return mRecsLoaded;
    } // method getMRecsLoaded

    /**
     * Return the 'mRecsLoaded' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMRecsLoaded methods
     * @return mRecsLoaded (String)
     */
    public String getMRecsLoaded(String s) {
        return ((mRecsLoaded != INTNULL) ? new Integer(mRecsLoaded).toString() : "");
    } // method getMRecsLoaded


    /**
     * Return the 'mRecsDup' class variable
     * @return mRecsDup (int)
     */
    public int getMRecsDup() {
        return mRecsDup;
    } // method getMRecsDup

    /**
     * Return the 'mRecsDup' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMRecsDup methods
     * @return mRecsDup (String)
     */
    public String getMRecsDup(String s) {
        return ((mRecsDup != INTNULL) ? new Integer(mRecsDup).toString() : "");
    } // method getMRecsDup


    /**
     * Return the 'aRecsLoaded' class variable
     * @return aRecsLoaded (int)
     */
    public int getARecsLoaded() {
        return aRecsLoaded;
    } // method getARecsLoaded

    /**
     * Return the 'aRecsLoaded' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getARecsLoaded methods
     * @return aRecsLoaded (String)
     */
    public String getARecsLoaded(String s) {
        return ((aRecsLoaded != INTNULL) ? new Integer(aRecsLoaded).toString() : "");
    } // method getARecsLoaded


    /**
     * Return the 'aRecsDup' class variable
     * @return aRecsDup (int)
     */
    public int getARecsDup() {
        return aRecsDup;
    } // method getARecsDup

    /**
     * Return the 'aRecsDup' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getARecsDup methods
     * @return aRecsDup (String)
     */
    public String getARecsDup(String s) {
        return ((aRecsDup != INTNULL) ? new Integer(aRecsDup).toString() : "");
    } // method getARecsDup


    /**
     * Return the 'dateStart' class variable
     * @return dateStart (Timestamp)
     */
    public Timestamp getDateStart() {
        return dateStart;
    } // method getDateStart

    /**
     * Return the 'dateStart' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateStart methods
     * @return dateStart (String)
     */
    public String getDateStart(String s) {
        if (dateStart.equals(DATENULL)) {
            return ("");
        } else {
            String dateStartStr = dateStart.toString();
            return dateStartStr.substring(0,dateStartStr.indexOf('.'));
        } // if
    } // method getDateStart


    /**
     * Return the 'dateEnd' class variable
     * @return dateEnd (Timestamp)
     */
    public Timestamp getDateEnd() {
        return dateEnd;
    } // method getDateEnd

    /**
     * Return the 'dateEnd' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateEnd methods
     * @return dateEnd (String)
     */
    public String getDateEnd(String s) {
        if (dateEnd.equals(DATENULL)) {
            return ("");
        } else {
            String dateEndStr = dateEnd.toString();
            return dateEndStr.substring(0,dateEndStr.indexOf('.'));
        } // if
    } // method getDateEnd


    /**
     * Return the 'respPerson' class variable
     * @return respPerson (String)
     */
    public String getRespPerson() {
        return respPerson;
    } // method getRespPerson

    /**
     * Return the 'respPerson' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRespPerson methods
     * @return respPerson (String)
     */
    public String getRespPerson(String s) {
        return (respPerson != CHARNULL ? respPerson.replace('"','\'') : "");
    } // method getRespPerson


    /**
     * Return the 'recsRejected' class variable
     * @return recsRejected (int)
     */
    public int getRecsRejected() {
        return recsRejected;
    } // method getRecsRejected

    /**
     * Return the 'recsRejected' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRecsRejected methods
     * @return recsRejected (String)
     */
    public String getRecsRejected(String s) {
        return ((recsRejected != INTNULL) ? new Integer(recsRejected).toString() : "");
    } // method getRecsRejected


    /**
     * Return the 'wmoRejected' class variable
     * @return wmoRejected (int)
     */
    public int getWmoRejected() {
        return wmoRejected;
    } // method getWmoRejected

    /**
     * Return the 'wmoRejected' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getWmoRejected methods
     * @return wmoRejected (String)
     */
    public String getWmoRejected(String s) {
        return ((wmoRejected != INTNULL) ? new Integer(wmoRejected).toString() : "");
    } // method getWmoRejected


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
        if ((loadid == CHARNULL) &&
            (vosLoadId == INTNULL) &&
            (source == CHARNULL) &&
            (dateLoaded.equals(DATENULL)) &&
            (mRecsLoaded == INTNULL) &&
            (mRecsDup == INTNULL) &&
            (aRecsLoaded == INTNULL) &&
            (aRecsDup == INTNULL) &&
            (dateStart.equals(DATENULL)) &&
            (dateEnd.equals(DATENULL)) &&
            (respPerson == CHARNULL) &&
            (recsRejected == INTNULL) &&
            (wmoRejected == INTNULL)) {
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
        int sumError = loadidError +
            vosLoadIdError +
            sourceError +
            dateLoadedError +
            mRecsLoadedError +
            mRecsDupError +
            aRecsLoadedError +
            aRecsDupError +
            dateStartError +
            dateEndError +
            respPersonError +
            recsRejectedError +
            wmoRejectedError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the loadid instance variable
     * @return errorcode (int)
     */
    public int getLoadidError() {
        return loadidError;
    } // method getLoadidError

    /**
     * Gets the errorMessage for the loadid instance variable
     * @return errorMessage (String)
     */
    public String getLoadidErrorMessage() {
        return loadidErrorMessage;
    } // method getLoadidErrorMessage


    /**
     * Gets the errorcode for the vosLoadId instance variable
     * @return errorcode (int)
     */
    public int getVosLoadIdError() {
        return vosLoadIdError;
    } // method getVosLoadIdError

    /**
     * Gets the errorMessage for the vosLoadId instance variable
     * @return errorMessage (String)
     */
    public String getVosLoadIdErrorMessage() {
        return vosLoadIdErrorMessage;
    } // method getVosLoadIdErrorMessage


    /**
     * Gets the errorcode for the source instance variable
     * @return errorcode (int)
     */
    public int getSourceError() {
        return sourceError;
    } // method getSourceError

    /**
     * Gets the errorMessage for the source instance variable
     * @return errorMessage (String)
     */
    public String getSourceErrorMessage() {
        return sourceErrorMessage;
    } // method getSourceErrorMessage


    /**
     * Gets the errorcode for the dateLoaded instance variable
     * @return errorcode (int)
     */
    public int getDateLoadedError() {
        return dateLoadedError;
    } // method getDateLoadedError

    /**
     * Gets the errorMessage for the dateLoaded instance variable
     * @return errorMessage (String)
     */
    public String getDateLoadedErrorMessage() {
        return dateLoadedErrorMessage;
    } // method getDateLoadedErrorMessage


    /**
     * Gets the errorcode for the mRecsLoaded instance variable
     * @return errorcode (int)
     */
    public int getMRecsLoadedError() {
        return mRecsLoadedError;
    } // method getMRecsLoadedError

    /**
     * Gets the errorMessage for the mRecsLoaded instance variable
     * @return errorMessage (String)
     */
    public String getMRecsLoadedErrorMessage() {
        return mRecsLoadedErrorMessage;
    } // method getMRecsLoadedErrorMessage


    /**
     * Gets the errorcode for the mRecsDup instance variable
     * @return errorcode (int)
     */
    public int getMRecsDupError() {
        return mRecsDupError;
    } // method getMRecsDupError

    /**
     * Gets the errorMessage for the mRecsDup instance variable
     * @return errorMessage (String)
     */
    public String getMRecsDupErrorMessage() {
        return mRecsDupErrorMessage;
    } // method getMRecsDupErrorMessage


    /**
     * Gets the errorcode for the aRecsLoaded instance variable
     * @return errorcode (int)
     */
    public int getARecsLoadedError() {
        return aRecsLoadedError;
    } // method getARecsLoadedError

    /**
     * Gets the errorMessage for the aRecsLoaded instance variable
     * @return errorMessage (String)
     */
    public String getARecsLoadedErrorMessage() {
        return aRecsLoadedErrorMessage;
    } // method getARecsLoadedErrorMessage


    /**
     * Gets the errorcode for the aRecsDup instance variable
     * @return errorcode (int)
     */
    public int getARecsDupError() {
        return aRecsDupError;
    } // method getARecsDupError

    /**
     * Gets the errorMessage for the aRecsDup instance variable
     * @return errorMessage (String)
     */
    public String getARecsDupErrorMessage() {
        return aRecsDupErrorMessage;
    } // method getARecsDupErrorMessage


    /**
     * Gets the errorcode for the dateStart instance variable
     * @return errorcode (int)
     */
    public int getDateStartError() {
        return dateStartError;
    } // method getDateStartError

    /**
     * Gets the errorMessage for the dateStart instance variable
     * @return errorMessage (String)
     */
    public String getDateStartErrorMessage() {
        return dateStartErrorMessage;
    } // method getDateStartErrorMessage


    /**
     * Gets the errorcode for the dateEnd instance variable
     * @return errorcode (int)
     */
    public int getDateEndError() {
        return dateEndError;
    } // method getDateEndError

    /**
     * Gets the errorMessage for the dateEnd instance variable
     * @return errorMessage (String)
     */
    public String getDateEndErrorMessage() {
        return dateEndErrorMessage;
    } // method getDateEndErrorMessage


    /**
     * Gets the errorcode for the respPerson instance variable
     * @return errorcode (int)
     */
    public int getRespPersonError() {
        return respPersonError;
    } // method getRespPersonError

    /**
     * Gets the errorMessage for the respPerson instance variable
     * @return errorMessage (String)
     */
    public String getRespPersonErrorMessage() {
        return respPersonErrorMessage;
    } // method getRespPersonErrorMessage


    /**
     * Gets the errorcode for the recsRejected instance variable
     * @return errorcode (int)
     */
    public int getRecsRejectedError() {
        return recsRejectedError;
    } // method getRecsRejectedError

    /**
     * Gets the errorMessage for the recsRejected instance variable
     * @return errorMessage (String)
     */
    public String getRecsRejectedErrorMessage() {
        return recsRejectedErrorMessage;
    } // method getRecsRejectedErrorMessage


    /**
     * Gets the errorcode for the wmoRejected instance variable
     * @return errorcode (int)
     */
    public int getWmoRejectedError() {
        return wmoRejectedError;
    } // method getWmoRejectedError

    /**
     * Gets the errorMessage for the wmoRejected instance variable
     * @return errorMessage (String)
     */
    public String getWmoRejectedErrorMessage() {
        return wmoRejectedErrorMessage;
    } // method getWmoRejectedErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of LogVosLoads. e.g.<pre>
     * LogVosLoads logVosLoads = new LogVosLoads(<val1>);
     * LogVosLoads logVosLoadsArray[] = logVosLoads.get();</pre>
     * will get the LogVosLoads record where loadid = <val1>.
     * @return Array of LogVosLoads (LogVosLoads[])
     */
    public LogVosLoads[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * LogVosLoads logVosLoadsArray[] =
     *     new LogVosLoads().get(LogVosLoads.LOADID+"=<val1>");</pre>
     * will get the LogVosLoads record where loadid = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of LogVosLoads (LogVosLoads[])
     */
    public LogVosLoads[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * LogVosLoads logVosLoadsArray[] =
     *     new LogVosLoads().get("1=1",logVosLoads.LOADID);</pre>
     * will get the all the LogVosLoads records, and order them by loadid.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of LogVosLoads (LogVosLoads[])
     */
    public LogVosLoads[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = LogVosLoads.LOADID,LogVosLoads.VOS_LOAD_ID;
     * String where = LogVosLoads.LOADID + "=<val1";
     * String order = LogVosLoads.LOADID;
     * LogVosLoads logVosLoadsArray[] =
     *     new LogVosLoads().get(columns, where, order);</pre>
     * will get the loadid and vosLoadId colums of all LogVosLoads records,
     * where loadid = <val1>, and order them by loadid.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of LogVosLoads (LogVosLoads[])
     */
    public LogVosLoads[] get (String fields, String where, String order) {
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
     * and transforms it into an array of LogVosLoads.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private LogVosLoads[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int loadidCol       = db.getColNumber(LOADID);
        int vosLoadIdCol    = db.getColNumber(VOS_LOAD_ID);
        int sourceCol       = db.getColNumber(SOURCE);
        int dateLoadedCol   = db.getColNumber(DATE_LOADED);
        int mRecsLoadedCol  = db.getColNumber(M_RECS_LOADED);
        int mRecsDupCol     = db.getColNumber(M_RECS_DUP);
        int aRecsLoadedCol  = db.getColNumber(A_RECS_LOADED);
        int aRecsDupCol     = db.getColNumber(A_RECS_DUP);
        int dateStartCol    = db.getColNumber(DATE_START);
        int dateEndCol      = db.getColNumber(DATE_END);
        int respPersonCol   = db.getColNumber(RESP_PERSON);
        int recsRejectedCol = db.getColNumber(RECS_REJECTED);
        int wmoRejectedCol  = db.getColNumber(WMO_REJECTED);
        LogVosLoads[] cArray = new LogVosLoads[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new LogVosLoads();
            if (loadidCol != -1)
                cArray[i].setLoadid      ((String) row.elementAt(loadidCol));
            if (vosLoadIdCol != -1)
                cArray[i].setVosLoadId   ((String) row.elementAt(vosLoadIdCol));
            if (sourceCol != -1)
                cArray[i].setSource      ((String) row.elementAt(sourceCol));
            if (dateLoadedCol != -1)
                cArray[i].setDateLoaded  ((String) row.elementAt(dateLoadedCol));
            if (mRecsLoadedCol != -1)
                cArray[i].setMRecsLoaded ((String) row.elementAt(mRecsLoadedCol));
            if (mRecsDupCol != -1)
                cArray[i].setMRecsDup    ((String) row.elementAt(mRecsDupCol));
            if (aRecsLoadedCol != -1)
                cArray[i].setARecsLoaded ((String) row.elementAt(aRecsLoadedCol));
            if (aRecsDupCol != -1)
                cArray[i].setARecsDup    ((String) row.elementAt(aRecsDupCol));
            if (dateStartCol != -1)
                cArray[i].setDateStart   ((String) row.elementAt(dateStartCol));
            if (dateEndCol != -1)
                cArray[i].setDateEnd     ((String) row.elementAt(dateEndCol));
            if (respPersonCol != -1)
                cArray[i].setRespPerson  ((String) row.elementAt(respPersonCol));
            if (recsRejectedCol != -1)
                cArray[i].setRecsRejected((String) row.elementAt(recsRejectedCol));
            if (wmoRejectedCol != -1)
                cArray[i].setWmoRejected ((String) row.elementAt(wmoRejectedCol));
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
     *     new LogVosLoads(
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
     *         <val12>,
     *         <val13>).put();</pre>
     * will insert a record with:
     *     loadid       = <val1>,
     *     vosLoadId    = <val2>,
     *     source       = <val3>,
     *     dateLoaded   = <val4>,
     *     mRecsLoaded  = <val5>,
     *     mRecsDup     = <val6>,
     *     aRecsLoaded  = <val7>,
     *     aRecsDup     = <val8>,
     *     dateStart    = <val9>,
     *     dateEnd      = <val10>,
     *     respPerson   = <val11>,
     *     recsRejected = <val12>,
     *     wmoRejected  = <val13>.
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
     * Boolean success = new LogVosLoads(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.DATENULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.DATENULL,
     *     Tables.DATENULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where vosLoadId = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * LogVosLoads logVosLoads = new LogVosLoads();
     * success = logVosLoads.del(LogVosLoads.LOADID+"=<val1>");</pre>
     * will delete all records where loadid = <val1>.
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
     * update are taken from the LogVosLoads argument, .e.g.<pre>
     * Boolean success
     * LogVosLoads updLogVosLoads = new LogVosLoads();
     * updLogVosLoads.setVosLoadId(<val2>);
     * LogVosLoads whereLogVosLoads = new LogVosLoads(<val1>);
     * success = whereLogVosLoads.upd(updLogVosLoads);</pre>
     * will update VosLoadId to <val2> for all records where
     * loadid = <val1>.
     * @param  logVosLoads  A LogVosLoads variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(LogVosLoads logVosLoads) {
        return db.update (TABLE, createColVals(logVosLoads), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * LogVosLoads updLogVosLoads = new LogVosLoads();
     * updLogVosLoads.setVosLoadId(<val2>);
     * LogVosLoads whereLogVosLoads = new LogVosLoads();
     * success = whereLogVosLoads.upd(
     *     updLogVosLoads, LogVosLoads.LOADID+"=<val1>");</pre>
     * will update VosLoadId to <val2> for all records where
     * loadid = <val1>.
     * @param  logVosLoads  A LogVosLoads variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(LogVosLoads logVosLoads, String where) {
        return db.update (TABLE, createColVals(logVosLoads), where);
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
        if (getLoadid() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LOADID + "='" + getLoadid() + "'";
        } // if getLoadid
        if (getVosLoadId() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + VOS_LOAD_ID + "=" + getVosLoadId("");
        } // if getVosLoadId
        if (getSource() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SOURCE + "='" + getSource() + "'";
        } // if getSource
        if (!getDateLoaded().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_LOADED +
                "=" + Tables.getDateFormat(getDateLoaded());
        } // if getDateLoaded
        if (getMRecsLoaded() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M_RECS_LOADED + "=" + getMRecsLoaded("");
        } // if getMRecsLoaded
        if (getMRecsDup() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M_RECS_DUP + "=" + getMRecsDup("");
        } // if getMRecsDup
        if (getARecsLoaded() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + A_RECS_LOADED + "=" + getARecsLoaded("");
        } // if getARecsLoaded
        if (getARecsDup() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + A_RECS_DUP + "=" + getARecsDup("");
        } // if getARecsDup
        if (!getDateStart().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_START +
                "=" + Tables.getDateFormat(getDateStart());
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_END +
                "=" + Tables.getDateFormat(getDateEnd());
        } // if getDateEnd
        if (getRespPerson() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RESP_PERSON + "='" + getRespPerson() + "'";
        } // if getRespPerson
        if (getRecsRejected() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RECS_REJECTED + "=" + getRecsRejected("");
        } // if getRecsRejected
        if (getWmoRejected() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + WMO_REJECTED + "=" + getWmoRejected("");
        } // if getWmoRejected
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(LogVosLoads aVar) {
        String colVals = "";
        if (aVar.getLoadid() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LOADID +"=";
            colVals += (aVar.getLoadid().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLoadid() + "'");
        } // if aVar.getLoadid
        if (aVar.getVosLoadId() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += VOS_LOAD_ID +"=";
            colVals += (aVar.getVosLoadId() == INTNULL2 ?
                "null" : aVar.getVosLoadId(""));
        } // if aVar.getVosLoadId
        if (aVar.getSource() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SOURCE +"=";
            colVals += (aVar.getSource().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSource() + "'");
        } // if aVar.getSource
        if (!aVar.getDateLoaded().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_LOADED +"=";
            colVals += (aVar.getDateLoaded().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateLoaded()));
        } // if aVar.getDateLoaded
        if (aVar.getMRecsLoaded() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M_RECS_LOADED +"=";
            colVals += (aVar.getMRecsLoaded() == INTNULL2 ?
                "null" : aVar.getMRecsLoaded(""));
        } // if aVar.getMRecsLoaded
        if (aVar.getMRecsDup() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M_RECS_DUP +"=";
            colVals += (aVar.getMRecsDup() == INTNULL2 ?
                "null" : aVar.getMRecsDup(""));
        } // if aVar.getMRecsDup
        if (aVar.getARecsLoaded() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += A_RECS_LOADED +"=";
            colVals += (aVar.getARecsLoaded() == INTNULL2 ?
                "null" : aVar.getARecsLoaded(""));
        } // if aVar.getARecsLoaded
        if (aVar.getARecsDup() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += A_RECS_DUP +"=";
            colVals += (aVar.getARecsDup() == INTNULL2 ?
                "null" : aVar.getARecsDup(""));
        } // if aVar.getARecsDup
        if (!aVar.getDateStart().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_START +"=";
            colVals += (aVar.getDateStart().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateStart()));
        } // if aVar.getDateStart
        if (!aVar.getDateEnd().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_END +"=";
            colVals += (aVar.getDateEnd().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateEnd()));
        } // if aVar.getDateEnd
        if (aVar.getRespPerson() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RESP_PERSON +"=";
            colVals += (aVar.getRespPerson().equals(CHARNULL2) ?
                "null" : "'" + aVar.getRespPerson() + "'");
        } // if aVar.getRespPerson
        if (aVar.getRecsRejected() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RECS_REJECTED +"=";
            colVals += (aVar.getRecsRejected() == INTNULL2 ?
                "null" : aVar.getRecsRejected(""));
        } // if aVar.getRecsRejected
        if (aVar.getWmoRejected() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += WMO_REJECTED +"=";
            colVals += (aVar.getWmoRejected() == INTNULL2 ?
                "null" : aVar.getWmoRejected(""));
        } // if aVar.getWmoRejected
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = LOADID;
        if (getVosLoadId() != INTNULL) {
            columns = columns + "," + VOS_LOAD_ID;
        } // if getVosLoadId
        if (getSource() != CHARNULL) {
            columns = columns + "," + SOURCE;
        } // if getSource
        if (!getDateLoaded().equals(DATENULL)) {
            columns = columns + "," + DATE_LOADED;
        } // if getDateLoaded
        if (getMRecsLoaded() != INTNULL) {
            columns = columns + "," + M_RECS_LOADED;
        } // if getMRecsLoaded
        if (getMRecsDup() != INTNULL) {
            columns = columns + "," + M_RECS_DUP;
        } // if getMRecsDup
        if (getARecsLoaded() != INTNULL) {
            columns = columns + "," + A_RECS_LOADED;
        } // if getARecsLoaded
        if (getARecsDup() != INTNULL) {
            columns = columns + "," + A_RECS_DUP;
        } // if getARecsDup
        if (!getDateStart().equals(DATENULL)) {
            columns = columns + "," + DATE_START;
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            columns = columns + "," + DATE_END;
        } // if getDateEnd
        if (getRespPerson() != CHARNULL) {
            columns = columns + "," + RESP_PERSON;
        } // if getRespPerson
        if (getRecsRejected() != INTNULL) {
            columns = columns + "," + RECS_REJECTED;
        } // if getRecsRejected
        if (getWmoRejected() != INTNULL) {
            columns = columns + "," + WMO_REJECTED;
        } // if getWmoRejected
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getLoadid() + "'";
        if (getVosLoadId() != INTNULL) {
            values  = values  + "," + getVosLoadId("");
        } // if getVosLoadId
        if (getSource() != CHARNULL) {
            values  = values  + ",'" + getSource() + "'";
        } // if getSource
        if (!getDateLoaded().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateLoaded());
        } // if getDateLoaded
        if (getMRecsLoaded() != INTNULL) {
            values  = values  + "," + getMRecsLoaded("");
        } // if getMRecsLoaded
        if (getMRecsDup() != INTNULL) {
            values  = values  + "," + getMRecsDup("");
        } // if getMRecsDup
        if (getARecsLoaded() != INTNULL) {
            values  = values  + "," + getARecsLoaded("");
        } // if getARecsLoaded
        if (getARecsDup() != INTNULL) {
            values  = values  + "," + getARecsDup("");
        } // if getARecsDup
        if (!getDateStart().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateStart());
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateEnd());
        } // if getDateEnd
        if (getRespPerson() != CHARNULL) {
            values  = values  + ",'" + getRespPerson() + "'";
        } // if getRespPerson
        if (getRecsRejected() != INTNULL) {
            values  = values  + "," + getRecsRejected("");
        } // if getRecsRejected
        if (getWmoRejected() != INTNULL) {
            values  = values  + "," + getWmoRejected("");
        } // if getWmoRejected
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getLoadid("")       + "|" +
            getVosLoadId("")    + "|" +
            getSource("")       + "|" +
            getDateLoaded("")   + "|" +
            getMRecsLoaded("")  + "|" +
            getMRecsDup("")     + "|" +
            getARecsLoaded("")  + "|" +
            getARecsDup("")     + "|" +
            getDateStart("")    + "|" +
            getDateEnd("")      + "|" +
            getRespPerson("")   + "|" +
            getRecsRejected("") + "|" +
            getWmoRejected("")  + "|";
    } // method toString

} // class LogVosLoads
