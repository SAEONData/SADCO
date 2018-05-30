package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WET_PERIOD_COUNTS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 080526 - SIT Group
 * @version
 * 080526 - GenTableClassB class - create class<br>
 */
public class WetPeriodCounts extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WET_PERIOD_COUNTS";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The yearp field name */
    public static final String YEARP = "YEARP";
    /** The m01 field name */
    public static final String M01 = "M01";
    /** The m02 field name */
    public static final String M02 = "M02";
    /** The m03 field name */
    public static final String M03 = "M03";
    /** The m04 field name */
    public static final String M04 = "M04";
    /** The m05 field name */
    public static final String M05 = "M05";
    /** The m06 field name */
    public static final String M06 = "M06";
    /** The m07 field name */
    public static final String M07 = "M07";
    /** The m08 field name */
    public static final String M08 = "M08";
    /** The m09 field name */
    public static final String M09 = "M09";
    /** The m10 field name */
    public static final String M10 = "M10";
    /** The m11 field name */
    public static final String M11 = "M11";
    /** The m12 field name */
    public static final String M12 = "M12";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private int       yearp;
    private int       m01;
    private int       m02;
    private int       m03;
    private int       m04;
    private int       m05;
    private int       m06;
    private int       m07;
    private int       m08;
    private int       m09;
    private int       m10;
    private int       m11;
    private int       m12;

    /** The error variables  */
    private int stationIdError = ERROR_NORMAL;
    private int yearpError     = ERROR_NORMAL;
    private int m01Error       = ERROR_NORMAL;
    private int m02Error       = ERROR_NORMAL;
    private int m03Error       = ERROR_NORMAL;
    private int m04Error       = ERROR_NORMAL;
    private int m05Error       = ERROR_NORMAL;
    private int m06Error       = ERROR_NORMAL;
    private int m07Error       = ERROR_NORMAL;
    private int m08Error       = ERROR_NORMAL;
    private int m09Error       = ERROR_NORMAL;
    private int m10Error       = ERROR_NORMAL;
    private int m11Error       = ERROR_NORMAL;
    private int m12Error       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage = "";
    private String yearpErrorMessage     = "";
    private String m01ErrorMessage       = "";
    private String m02ErrorMessage       = "";
    private String m03ErrorMessage       = "";
    private String m04ErrorMessage       = "";
    private String m05ErrorMessage       = "";
    private String m06ErrorMessage       = "";
    private String m07ErrorMessage       = "";
    private String m08ErrorMessage       = "";
    private String m09ErrorMessage       = "";
    private String m10ErrorMessage       = "";
    private String m11ErrorMessage       = "";
    private String m12ErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       YEARP_MN = INTMIN;
    public static final int       YEARP_MX = INTMAX;
    public static final int       M01_MN = INTMIN;
    public static final int       M01_MX = INTMAX;
    public static final int       M02_MN = INTMIN;
    public static final int       M02_MX = INTMAX;
    public static final int       M03_MN = INTMIN;
    public static final int       M03_MX = INTMAX;
    public static final int       M04_MN = INTMIN;
    public static final int       M04_MX = INTMAX;
    public static final int       M05_MN = INTMIN;
    public static final int       M05_MX = INTMAX;
    public static final int       M06_MN = INTMIN;
    public static final int       M06_MX = INTMAX;
    public static final int       M07_MN = INTMIN;
    public static final int       M07_MX = INTMAX;
    public static final int       M08_MN = INTMIN;
    public static final int       M08_MX = INTMAX;
    public static final int       M09_MN = INTMIN;
    public static final int       M09_MX = INTMAX;
    public static final int       M10_MN = INTMIN;
    public static final int       M10_MX = INTMAX;
    public static final int       M11_MN = INTMIN;
    public static final int       M11_MX = INTMAX;
    public static final int       M12_MN = INTMIN;
    public static final int       M12_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception yearpOutOfBoundsException =
        new Exception ("'yearp' out of bounds: " +
            YEARP_MN + " - " + YEARP_MX);
    Exception m01OutOfBoundsException =
        new Exception ("'m01' out of bounds: " +
            M01_MN + " - " + M01_MX);
    Exception m02OutOfBoundsException =
        new Exception ("'m02' out of bounds: " +
            M02_MN + " - " + M02_MX);
    Exception m03OutOfBoundsException =
        new Exception ("'m03' out of bounds: " +
            M03_MN + " - " + M03_MX);
    Exception m04OutOfBoundsException =
        new Exception ("'m04' out of bounds: " +
            M04_MN + " - " + M04_MX);
    Exception m05OutOfBoundsException =
        new Exception ("'m05' out of bounds: " +
            M05_MN + " - " + M05_MX);
    Exception m06OutOfBoundsException =
        new Exception ("'m06' out of bounds: " +
            M06_MN + " - " + M06_MX);
    Exception m07OutOfBoundsException =
        new Exception ("'m07' out of bounds: " +
            M07_MN + " - " + M07_MX);
    Exception m08OutOfBoundsException =
        new Exception ("'m08' out of bounds: " +
            M08_MN + " - " + M08_MX);
    Exception m09OutOfBoundsException =
        new Exception ("'m09' out of bounds: " +
            M09_MN + " - " + M09_MX);
    Exception m10OutOfBoundsException =
        new Exception ("'m10' out of bounds: " +
            M10_MN + " - " + M10_MX);
    Exception m11OutOfBoundsException =
        new Exception ("'m11' out of bounds: " +
            M11_MN + " - " + M11_MX);
    Exception m12OutOfBoundsException =
        new Exception ("'m12' out of bounds: " +
            M12_MN + " - " + M12_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public WetPeriodCounts() {
        clearVars();
        if (dbg) System.out.println ("<br>in WetPeriodCounts constructor 1"); // debug
    } // WetPeriodCounts constructor

    /**
     * Instantiate a WetPeriodCounts object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public WetPeriodCounts(
            String stationId) {
        this();
        setStationId (stationId);
        if (dbg) System.out.println ("<br>in WetPeriodCounts constructor 2"); // debug
    } // WetPeriodCounts constructor

    /**
     * Instantiate a WetPeriodCounts object and initialize the instance variables.
     * @param stationId  The stationId (String)
     * @param yearp      The yearp     (int)
     * @param m01        The m01       (int)
     * @param m02        The m02       (int)
     * @param m03        The m03       (int)
     * @param m04        The m04       (int)
     * @param m05        The m05       (int)
     * @param m06        The m06       (int)
     * @param m07        The m07       (int)
     * @param m08        The m08       (int)
     * @param m09        The m09       (int)
     * @param m10        The m10       (int)
     * @param m11        The m11       (int)
     * @param m12        The m12       (int)
     * @return A WetPeriodCounts object
     */
    public WetPeriodCounts(
            String stationId,
            int    yearp,
            int    m01,
            int    m02,
            int    m03,
            int    m04,
            int    m05,
            int    m06,
            int    m07,
            int    m08,
            int    m09,
            int    m10,
            int    m11,
            int    m12) {
        this();
        setStationId (stationId);
        setYearp     (yearp    );
        setM01       (m01      );
        setM02       (m02      );
        setM03       (m03      );
        setM04       (m04      );
        setM05       (m05      );
        setM06       (m06      );
        setM07       (m07      );
        setM08       (m08      );
        setM09       (m09      );
        setM10       (m10      );
        setM11       (m11      );
        setM12       (m12      );
        if (dbg) System.out.println ("<br>in WetPeriodCounts constructor 3"); // debug
    } // WetPeriodCounts constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId (CHARNULL);
        setYearp     (INTNULL );
        setM01       (INTNULL );
        setM02       (INTNULL );
        setM03       (INTNULL );
        setM04       (INTNULL );
        setM05       (INTNULL );
        setM06       (INTNULL );
        setM07       (INTNULL );
        setM08       (INTNULL );
        setM09       (INTNULL );
        setM10       (INTNULL );
        setM11       (INTNULL );
        setM12       (INTNULL );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'stationId' class variable
     * @param  stationId (String)
     */
    public int setStationId(String stationId) {
        try {
            this.stationId = stationId;
            if (this.stationId != CHARNULL) {
                this.stationId = stripCRLF(this.stationId.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>stationId = " + this.stationId);
        } catch (Exception e) {
            setStationIdError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return stationIdError;
    } // method setStationId

    /**
     * Called when an exception has occured
     * @param  stationId (String)
     */
    private void setStationIdError (String stationId, Exception e, int error) {
        this.stationId = stationId;
        stationIdErrorMessage = e.toString();
        stationIdError = error;
    } // method setStationIdError


    /**
     * Set the 'yearp' class variable
     * @param  yearp (int)
     */
    public int setYearp(int yearp) {
        try {
            if ( ((yearp == INTNULL) || 
                  (yearp == INTNULL2)) ||
                !((yearp < YEARP_MN) ||
                  (yearp > YEARP_MX)) ) {
                this.yearp = yearp;
                yearpError = ERROR_NORMAL;
            } else {
                throw yearpOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setYearpError(INTNULL, e, ERROR_LOCAL);
        } // try
        return yearpError;
    } // method setYearp

    /**
     * Set the 'yearp' class variable
     * @param  yearp (Integer)
     */
    public int setYearp(Integer yearp) {
        try {
            setYearp(yearp.intValue());
        } catch (Exception e) {
            setYearpError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return yearpError;
    } // method setYearp

    /**
     * Set the 'yearp' class variable
     * @param  yearp (Float)
     */
    public int setYearp(Float yearp) {
        try {
            if (yearp.floatValue() == FLOATNULL) {
                setYearp(INTNULL);
            } else {
                setYearp(yearp.intValue());
            } // if (yearp.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setYearpError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return yearpError;
    } // method setYearp

    /**
     * Set the 'yearp' class variable
     * @param  yearp (String)
     */
    public int setYearp(String yearp) {
        try {
            setYearp(new Integer(yearp).intValue());
        } catch (Exception e) {
            setYearpError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return yearpError;
    } // method setYearp

    /**
     * Called when an exception has occured
     * @param  yearp (String)
     */
    private void setYearpError (int yearp, Exception e, int error) {
        this.yearp = yearp;
        yearpErrorMessage = e.toString();
        yearpError = error;
    } // method setYearpError


    /**
     * Set the 'm01' class variable
     * @param  m01 (int)
     */
    public int setM01(int m01) {
        try {
            if ( ((m01 == INTNULL) || 
                  (m01 == INTNULL2)) ||
                !((m01 < M01_MN) ||
                  (m01 > M01_MX)) ) {
                this.m01 = m01;
                m01Error = ERROR_NORMAL;
            } else {
                throw m01OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM01Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m01Error;
    } // method setM01

    /**
     * Set the 'm01' class variable
     * @param  m01 (Integer)
     */
    public int setM01(Integer m01) {
        try {
            setM01(m01.intValue());
        } catch (Exception e) {
            setM01Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m01Error;
    } // method setM01

    /**
     * Set the 'm01' class variable
     * @param  m01 (Float)
     */
    public int setM01(Float m01) {
        try {
            if (m01.floatValue() == FLOATNULL) {
                setM01(INTNULL);
            } else {
                setM01(m01.intValue());
            } // if (m01.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM01Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m01Error;
    } // method setM01

    /**
     * Set the 'm01' class variable
     * @param  m01 (String)
     */
    public int setM01(String m01) {
        try {
            setM01(new Integer(m01).intValue());
        } catch (Exception e) {
            setM01Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m01Error;
    } // method setM01

    /**
     * Called when an exception has occured
     * @param  m01 (String)
     */
    private void setM01Error (int m01, Exception e, int error) {
        this.m01 = m01;
        m01ErrorMessage = e.toString();
        m01Error = error;
    } // method setM01Error


    /**
     * Set the 'm02' class variable
     * @param  m02 (int)
     */
    public int setM02(int m02) {
        try {
            if ( ((m02 == INTNULL) || 
                  (m02 == INTNULL2)) ||
                !((m02 < M02_MN) ||
                  (m02 > M02_MX)) ) {
                this.m02 = m02;
                m02Error = ERROR_NORMAL;
            } else {
                throw m02OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM02Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m02Error;
    } // method setM02

    /**
     * Set the 'm02' class variable
     * @param  m02 (Integer)
     */
    public int setM02(Integer m02) {
        try {
            setM02(m02.intValue());
        } catch (Exception e) {
            setM02Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m02Error;
    } // method setM02

    /**
     * Set the 'm02' class variable
     * @param  m02 (Float)
     */
    public int setM02(Float m02) {
        try {
            if (m02.floatValue() == FLOATNULL) {
                setM02(INTNULL);
            } else {
                setM02(m02.intValue());
            } // if (m02.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM02Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m02Error;
    } // method setM02

    /**
     * Set the 'm02' class variable
     * @param  m02 (String)
     */
    public int setM02(String m02) {
        try {
            setM02(new Integer(m02).intValue());
        } catch (Exception e) {
            setM02Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m02Error;
    } // method setM02

    /**
     * Called when an exception has occured
     * @param  m02 (String)
     */
    private void setM02Error (int m02, Exception e, int error) {
        this.m02 = m02;
        m02ErrorMessage = e.toString();
        m02Error = error;
    } // method setM02Error


    /**
     * Set the 'm03' class variable
     * @param  m03 (int)
     */
    public int setM03(int m03) {
        try {
            if ( ((m03 == INTNULL) || 
                  (m03 == INTNULL2)) ||
                !((m03 < M03_MN) ||
                  (m03 > M03_MX)) ) {
                this.m03 = m03;
                m03Error = ERROR_NORMAL;
            } else {
                throw m03OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM03Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m03Error;
    } // method setM03

    /**
     * Set the 'm03' class variable
     * @param  m03 (Integer)
     */
    public int setM03(Integer m03) {
        try {
            setM03(m03.intValue());
        } catch (Exception e) {
            setM03Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m03Error;
    } // method setM03

    /**
     * Set the 'm03' class variable
     * @param  m03 (Float)
     */
    public int setM03(Float m03) {
        try {
            if (m03.floatValue() == FLOATNULL) {
                setM03(INTNULL);
            } else {
                setM03(m03.intValue());
            } // if (m03.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM03Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m03Error;
    } // method setM03

    /**
     * Set the 'm03' class variable
     * @param  m03 (String)
     */
    public int setM03(String m03) {
        try {
            setM03(new Integer(m03).intValue());
        } catch (Exception e) {
            setM03Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m03Error;
    } // method setM03

    /**
     * Called when an exception has occured
     * @param  m03 (String)
     */
    private void setM03Error (int m03, Exception e, int error) {
        this.m03 = m03;
        m03ErrorMessage = e.toString();
        m03Error = error;
    } // method setM03Error


    /**
     * Set the 'm04' class variable
     * @param  m04 (int)
     */
    public int setM04(int m04) {
        try {
            if ( ((m04 == INTNULL) || 
                  (m04 == INTNULL2)) ||
                !((m04 < M04_MN) ||
                  (m04 > M04_MX)) ) {
                this.m04 = m04;
                m04Error = ERROR_NORMAL;
            } else {
                throw m04OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM04Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m04Error;
    } // method setM04

    /**
     * Set the 'm04' class variable
     * @param  m04 (Integer)
     */
    public int setM04(Integer m04) {
        try {
            setM04(m04.intValue());
        } catch (Exception e) {
            setM04Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m04Error;
    } // method setM04

    /**
     * Set the 'm04' class variable
     * @param  m04 (Float)
     */
    public int setM04(Float m04) {
        try {
            if (m04.floatValue() == FLOATNULL) {
                setM04(INTNULL);
            } else {
                setM04(m04.intValue());
            } // if (m04.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM04Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m04Error;
    } // method setM04

    /**
     * Set the 'm04' class variable
     * @param  m04 (String)
     */
    public int setM04(String m04) {
        try {
            setM04(new Integer(m04).intValue());
        } catch (Exception e) {
            setM04Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m04Error;
    } // method setM04

    /**
     * Called when an exception has occured
     * @param  m04 (String)
     */
    private void setM04Error (int m04, Exception e, int error) {
        this.m04 = m04;
        m04ErrorMessage = e.toString();
        m04Error = error;
    } // method setM04Error


    /**
     * Set the 'm05' class variable
     * @param  m05 (int)
     */
    public int setM05(int m05) {
        try {
            if ( ((m05 == INTNULL) || 
                  (m05 == INTNULL2)) ||
                !((m05 < M05_MN) ||
                  (m05 > M05_MX)) ) {
                this.m05 = m05;
                m05Error = ERROR_NORMAL;
            } else {
                throw m05OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM05Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m05Error;
    } // method setM05

    /**
     * Set the 'm05' class variable
     * @param  m05 (Integer)
     */
    public int setM05(Integer m05) {
        try {
            setM05(m05.intValue());
        } catch (Exception e) {
            setM05Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m05Error;
    } // method setM05

    /**
     * Set the 'm05' class variable
     * @param  m05 (Float)
     */
    public int setM05(Float m05) {
        try {
            if (m05.floatValue() == FLOATNULL) {
                setM05(INTNULL);
            } else {
                setM05(m05.intValue());
            } // if (m05.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM05Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m05Error;
    } // method setM05

    /**
     * Set the 'm05' class variable
     * @param  m05 (String)
     */
    public int setM05(String m05) {
        try {
            setM05(new Integer(m05).intValue());
        } catch (Exception e) {
            setM05Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m05Error;
    } // method setM05

    /**
     * Called when an exception has occured
     * @param  m05 (String)
     */
    private void setM05Error (int m05, Exception e, int error) {
        this.m05 = m05;
        m05ErrorMessage = e.toString();
        m05Error = error;
    } // method setM05Error


    /**
     * Set the 'm06' class variable
     * @param  m06 (int)
     */
    public int setM06(int m06) {
        try {
            if ( ((m06 == INTNULL) || 
                  (m06 == INTNULL2)) ||
                !((m06 < M06_MN) ||
                  (m06 > M06_MX)) ) {
                this.m06 = m06;
                m06Error = ERROR_NORMAL;
            } else {
                throw m06OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM06Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m06Error;
    } // method setM06

    /**
     * Set the 'm06' class variable
     * @param  m06 (Integer)
     */
    public int setM06(Integer m06) {
        try {
            setM06(m06.intValue());
        } catch (Exception e) {
            setM06Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m06Error;
    } // method setM06

    /**
     * Set the 'm06' class variable
     * @param  m06 (Float)
     */
    public int setM06(Float m06) {
        try {
            if (m06.floatValue() == FLOATNULL) {
                setM06(INTNULL);
            } else {
                setM06(m06.intValue());
            } // if (m06.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM06Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m06Error;
    } // method setM06

    /**
     * Set the 'm06' class variable
     * @param  m06 (String)
     */
    public int setM06(String m06) {
        try {
            setM06(new Integer(m06).intValue());
        } catch (Exception e) {
            setM06Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m06Error;
    } // method setM06

    /**
     * Called when an exception has occured
     * @param  m06 (String)
     */
    private void setM06Error (int m06, Exception e, int error) {
        this.m06 = m06;
        m06ErrorMessage = e.toString();
        m06Error = error;
    } // method setM06Error


    /**
     * Set the 'm07' class variable
     * @param  m07 (int)
     */
    public int setM07(int m07) {
        try {
            if ( ((m07 == INTNULL) || 
                  (m07 == INTNULL2)) ||
                !((m07 < M07_MN) ||
                  (m07 > M07_MX)) ) {
                this.m07 = m07;
                m07Error = ERROR_NORMAL;
            } else {
                throw m07OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM07Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m07Error;
    } // method setM07

    /**
     * Set the 'm07' class variable
     * @param  m07 (Integer)
     */
    public int setM07(Integer m07) {
        try {
            setM07(m07.intValue());
        } catch (Exception e) {
            setM07Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m07Error;
    } // method setM07

    /**
     * Set the 'm07' class variable
     * @param  m07 (Float)
     */
    public int setM07(Float m07) {
        try {
            if (m07.floatValue() == FLOATNULL) {
                setM07(INTNULL);
            } else {
                setM07(m07.intValue());
            } // if (m07.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM07Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m07Error;
    } // method setM07

    /**
     * Set the 'm07' class variable
     * @param  m07 (String)
     */
    public int setM07(String m07) {
        try {
            setM07(new Integer(m07).intValue());
        } catch (Exception e) {
            setM07Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m07Error;
    } // method setM07

    /**
     * Called when an exception has occured
     * @param  m07 (String)
     */
    private void setM07Error (int m07, Exception e, int error) {
        this.m07 = m07;
        m07ErrorMessage = e.toString();
        m07Error = error;
    } // method setM07Error


    /**
     * Set the 'm08' class variable
     * @param  m08 (int)
     */
    public int setM08(int m08) {
        try {
            if ( ((m08 == INTNULL) || 
                  (m08 == INTNULL2)) ||
                !((m08 < M08_MN) ||
                  (m08 > M08_MX)) ) {
                this.m08 = m08;
                m08Error = ERROR_NORMAL;
            } else {
                throw m08OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM08Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m08Error;
    } // method setM08

    /**
     * Set the 'm08' class variable
     * @param  m08 (Integer)
     */
    public int setM08(Integer m08) {
        try {
            setM08(m08.intValue());
        } catch (Exception e) {
            setM08Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m08Error;
    } // method setM08

    /**
     * Set the 'm08' class variable
     * @param  m08 (Float)
     */
    public int setM08(Float m08) {
        try {
            if (m08.floatValue() == FLOATNULL) {
                setM08(INTNULL);
            } else {
                setM08(m08.intValue());
            } // if (m08.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM08Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m08Error;
    } // method setM08

    /**
     * Set the 'm08' class variable
     * @param  m08 (String)
     */
    public int setM08(String m08) {
        try {
            setM08(new Integer(m08).intValue());
        } catch (Exception e) {
            setM08Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m08Error;
    } // method setM08

    /**
     * Called when an exception has occured
     * @param  m08 (String)
     */
    private void setM08Error (int m08, Exception e, int error) {
        this.m08 = m08;
        m08ErrorMessage = e.toString();
        m08Error = error;
    } // method setM08Error


    /**
     * Set the 'm09' class variable
     * @param  m09 (int)
     */
    public int setM09(int m09) {
        try {
            if ( ((m09 == INTNULL) || 
                  (m09 == INTNULL2)) ||
                !((m09 < M09_MN) ||
                  (m09 > M09_MX)) ) {
                this.m09 = m09;
                m09Error = ERROR_NORMAL;
            } else {
                throw m09OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM09Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m09Error;
    } // method setM09

    /**
     * Set the 'm09' class variable
     * @param  m09 (Integer)
     */
    public int setM09(Integer m09) {
        try {
            setM09(m09.intValue());
        } catch (Exception e) {
            setM09Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m09Error;
    } // method setM09

    /**
     * Set the 'm09' class variable
     * @param  m09 (Float)
     */
    public int setM09(Float m09) {
        try {
            if (m09.floatValue() == FLOATNULL) {
                setM09(INTNULL);
            } else {
                setM09(m09.intValue());
            } // if (m09.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM09Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m09Error;
    } // method setM09

    /**
     * Set the 'm09' class variable
     * @param  m09 (String)
     */
    public int setM09(String m09) {
        try {
            setM09(new Integer(m09).intValue());
        } catch (Exception e) {
            setM09Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m09Error;
    } // method setM09

    /**
     * Called when an exception has occured
     * @param  m09 (String)
     */
    private void setM09Error (int m09, Exception e, int error) {
        this.m09 = m09;
        m09ErrorMessage = e.toString();
        m09Error = error;
    } // method setM09Error


    /**
     * Set the 'm10' class variable
     * @param  m10 (int)
     */
    public int setM10(int m10) {
        try {
            if ( ((m10 == INTNULL) || 
                  (m10 == INTNULL2)) ||
                !((m10 < M10_MN) ||
                  (m10 > M10_MX)) ) {
                this.m10 = m10;
                m10Error = ERROR_NORMAL;
            } else {
                throw m10OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM10Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m10Error;
    } // method setM10

    /**
     * Set the 'm10' class variable
     * @param  m10 (Integer)
     */
    public int setM10(Integer m10) {
        try {
            setM10(m10.intValue());
        } catch (Exception e) {
            setM10Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m10Error;
    } // method setM10

    /**
     * Set the 'm10' class variable
     * @param  m10 (Float)
     */
    public int setM10(Float m10) {
        try {
            if (m10.floatValue() == FLOATNULL) {
                setM10(INTNULL);
            } else {
                setM10(m10.intValue());
            } // if (m10.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM10Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m10Error;
    } // method setM10

    /**
     * Set the 'm10' class variable
     * @param  m10 (String)
     */
    public int setM10(String m10) {
        try {
            setM10(new Integer(m10).intValue());
        } catch (Exception e) {
            setM10Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m10Error;
    } // method setM10

    /**
     * Called when an exception has occured
     * @param  m10 (String)
     */
    private void setM10Error (int m10, Exception e, int error) {
        this.m10 = m10;
        m10ErrorMessage = e.toString();
        m10Error = error;
    } // method setM10Error


    /**
     * Set the 'm11' class variable
     * @param  m11 (int)
     */
    public int setM11(int m11) {
        try {
            if ( ((m11 == INTNULL) || 
                  (m11 == INTNULL2)) ||
                !((m11 < M11_MN) ||
                  (m11 > M11_MX)) ) {
                this.m11 = m11;
                m11Error = ERROR_NORMAL;
            } else {
                throw m11OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM11Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m11Error;
    } // method setM11

    /**
     * Set the 'm11' class variable
     * @param  m11 (Integer)
     */
    public int setM11(Integer m11) {
        try {
            setM11(m11.intValue());
        } catch (Exception e) {
            setM11Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m11Error;
    } // method setM11

    /**
     * Set the 'm11' class variable
     * @param  m11 (Float)
     */
    public int setM11(Float m11) {
        try {
            if (m11.floatValue() == FLOATNULL) {
                setM11(INTNULL);
            } else {
                setM11(m11.intValue());
            } // if (m11.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM11Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m11Error;
    } // method setM11

    /**
     * Set the 'm11' class variable
     * @param  m11 (String)
     */
    public int setM11(String m11) {
        try {
            setM11(new Integer(m11).intValue());
        } catch (Exception e) {
            setM11Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m11Error;
    } // method setM11

    /**
     * Called when an exception has occured
     * @param  m11 (String)
     */
    private void setM11Error (int m11, Exception e, int error) {
        this.m11 = m11;
        m11ErrorMessage = e.toString();
        m11Error = error;
    } // method setM11Error


    /**
     * Set the 'm12' class variable
     * @param  m12 (int)
     */
    public int setM12(int m12) {
        try {
            if ( ((m12 == INTNULL) || 
                  (m12 == INTNULL2)) ||
                !((m12 < M12_MN) ||
                  (m12 > M12_MX)) ) {
                this.m12 = m12;
                m12Error = ERROR_NORMAL;
            } else {
                throw m12OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setM12Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return m12Error;
    } // method setM12

    /**
     * Set the 'm12' class variable
     * @param  m12 (Integer)
     */
    public int setM12(Integer m12) {
        try {
            setM12(m12.intValue());
        } catch (Exception e) {
            setM12Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m12Error;
    } // method setM12

    /**
     * Set the 'm12' class variable
     * @param  m12 (Float)
     */
    public int setM12(Float m12) {
        try {
            if (m12.floatValue() == FLOATNULL) {
                setM12(INTNULL);
            } else {
                setM12(m12.intValue());
            } // if (m12.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setM12Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m12Error;
    } // method setM12

    /**
     * Set the 'm12' class variable
     * @param  m12 (String)
     */
    public int setM12(String m12) {
        try {
            setM12(new Integer(m12).intValue());
        } catch (Exception e) {
            setM12Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return m12Error;
    } // method setM12

    /**
     * Called when an exception has occured
     * @param  m12 (String)
     */
    private void setM12Error (int m12, Exception e, int error) {
        this.m12 = m12;
        m12ErrorMessage = e.toString();
        m12Error = error;
    } // method setM12Error


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'stationId' class variable
     * @return stationId (String)
     */
    public String getStationId() {
        return stationId;
    } // method getStationId

    /**
     * Return the 'stationId' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStationId methods
     * @return stationId (String)
     */
    public String getStationId(String s) {
        return (stationId != CHARNULL ? stationId.replace('"','\'') : "");
    } // method getStationId


    /**
     * Return the 'yearp' class variable
     * @return yearp (int)
     */
    public int getYearp() {
        return yearp;
    } // method getYearp

    /**
     * Return the 'yearp' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getYearp methods
     * @return yearp (String)
     */
    public String getYearp(String s) {
        return ((yearp != INTNULL) ? new Integer(yearp).toString() : "");
    } // method getYearp


    /**
     * Return the 'm01' class variable
     * @return m01 (int)
     */
    public int getM01() {
        return m01;
    } // method getM01

    /**
     * Return the 'm01' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM01 methods
     * @return m01 (String)
     */
    public String getM01(String s) {
        return ((m01 != INTNULL) ? new Integer(m01).toString() : "");
    } // method getM01


    /**
     * Return the 'm02' class variable
     * @return m02 (int)
     */
    public int getM02() {
        return m02;
    } // method getM02

    /**
     * Return the 'm02' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM02 methods
     * @return m02 (String)
     */
    public String getM02(String s) {
        return ((m02 != INTNULL) ? new Integer(m02).toString() : "");
    } // method getM02


    /**
     * Return the 'm03' class variable
     * @return m03 (int)
     */
    public int getM03() {
        return m03;
    } // method getM03

    /**
     * Return the 'm03' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM03 methods
     * @return m03 (String)
     */
    public String getM03(String s) {
        return ((m03 != INTNULL) ? new Integer(m03).toString() : "");
    } // method getM03


    /**
     * Return the 'm04' class variable
     * @return m04 (int)
     */
    public int getM04() {
        return m04;
    } // method getM04

    /**
     * Return the 'm04' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM04 methods
     * @return m04 (String)
     */
    public String getM04(String s) {
        return ((m04 != INTNULL) ? new Integer(m04).toString() : "");
    } // method getM04


    /**
     * Return the 'm05' class variable
     * @return m05 (int)
     */
    public int getM05() {
        return m05;
    } // method getM05

    /**
     * Return the 'm05' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM05 methods
     * @return m05 (String)
     */
    public String getM05(String s) {
        return ((m05 != INTNULL) ? new Integer(m05).toString() : "");
    } // method getM05


    /**
     * Return the 'm06' class variable
     * @return m06 (int)
     */
    public int getM06() {
        return m06;
    } // method getM06

    /**
     * Return the 'm06' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM06 methods
     * @return m06 (String)
     */
    public String getM06(String s) {
        return ((m06 != INTNULL) ? new Integer(m06).toString() : "");
    } // method getM06


    /**
     * Return the 'm07' class variable
     * @return m07 (int)
     */
    public int getM07() {
        return m07;
    } // method getM07

    /**
     * Return the 'm07' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM07 methods
     * @return m07 (String)
     */
    public String getM07(String s) {
        return ((m07 != INTNULL) ? new Integer(m07).toString() : "");
    } // method getM07


    /**
     * Return the 'm08' class variable
     * @return m08 (int)
     */
    public int getM08() {
        return m08;
    } // method getM08

    /**
     * Return the 'm08' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM08 methods
     * @return m08 (String)
     */
    public String getM08(String s) {
        return ((m08 != INTNULL) ? new Integer(m08).toString() : "");
    } // method getM08


    /**
     * Return the 'm09' class variable
     * @return m09 (int)
     */
    public int getM09() {
        return m09;
    } // method getM09

    /**
     * Return the 'm09' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM09 methods
     * @return m09 (String)
     */
    public String getM09(String s) {
        return ((m09 != INTNULL) ? new Integer(m09).toString() : "");
    } // method getM09


    /**
     * Return the 'm10' class variable
     * @return m10 (int)
     */
    public int getM10() {
        return m10;
    } // method getM10

    /**
     * Return the 'm10' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM10 methods
     * @return m10 (String)
     */
    public String getM10(String s) {
        return ((m10 != INTNULL) ? new Integer(m10).toString() : "");
    } // method getM10


    /**
     * Return the 'm11' class variable
     * @return m11 (int)
     */
    public int getM11() {
        return m11;
    } // method getM11

    /**
     * Return the 'm11' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM11 methods
     * @return m11 (String)
     */
    public String getM11(String s) {
        return ((m11 != INTNULL) ? new Integer(m11).toString() : "");
    } // method getM11


    /**
     * Return the 'm12' class variable
     * @return m12 (int)
     */
    public int getM12() {
        return m12;
    } // method getM12

    /**
     * Return the 'm12' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getM12 methods
     * @return m12 (String)
     */
    public String getM12(String s) {
        return ((m12 != INTNULL) ? new Integer(m12).toString() : "");
    } // method getM12


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
        if ((stationId == CHARNULL) &&
            (yearp == INTNULL) &&
            (m01 == INTNULL) &&
            (m02 == INTNULL) &&
            (m03 == INTNULL) &&
            (m04 == INTNULL) &&
            (m05 == INTNULL) &&
            (m06 == INTNULL) &&
            (m07 == INTNULL) &&
            (m08 == INTNULL) &&
            (m09 == INTNULL) &&
            (m10 == INTNULL) &&
            (m11 == INTNULL) &&
            (m12 == INTNULL)) {
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
        int sumError = stationIdError +
            yearpError +
            m01Error +
            m02Error +
            m03Error +
            m04Error +
            m05Error +
            m06Error +
            m07Error +
            m08Error +
            m09Error +
            m10Error +
            m11Error +
            m12Error;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the stationId instance variable
     * @return errorcode (int)
     */
    public int getStationIdError() {
        return stationIdError;
    } // method getStationIdError

    /**
     * Gets the errorMessage for the stationId instance variable
     * @return errorMessage (String)
     */
    public String getStationIdErrorMessage() {
        return stationIdErrorMessage;
    } // method getStationIdErrorMessage


    /**
     * Gets the errorcode for the yearp instance variable
     * @return errorcode (int)
     */
    public int getYearpError() {
        return yearpError;
    } // method getYearpError

    /**
     * Gets the errorMessage for the yearp instance variable
     * @return errorMessage (String)
     */
    public String getYearpErrorMessage() {
        return yearpErrorMessage;
    } // method getYearpErrorMessage


    /**
     * Gets the errorcode for the m01 instance variable
     * @return errorcode (int)
     */
    public int getM01Error() {
        return m01Error;
    } // method getM01Error

    /**
     * Gets the errorMessage for the m01 instance variable
     * @return errorMessage (String)
     */
    public String getM01ErrorMessage() {
        return m01ErrorMessage;
    } // method getM01ErrorMessage


    /**
     * Gets the errorcode for the m02 instance variable
     * @return errorcode (int)
     */
    public int getM02Error() {
        return m02Error;
    } // method getM02Error

    /**
     * Gets the errorMessage for the m02 instance variable
     * @return errorMessage (String)
     */
    public String getM02ErrorMessage() {
        return m02ErrorMessage;
    } // method getM02ErrorMessage


    /**
     * Gets the errorcode for the m03 instance variable
     * @return errorcode (int)
     */
    public int getM03Error() {
        return m03Error;
    } // method getM03Error

    /**
     * Gets the errorMessage for the m03 instance variable
     * @return errorMessage (String)
     */
    public String getM03ErrorMessage() {
        return m03ErrorMessage;
    } // method getM03ErrorMessage


    /**
     * Gets the errorcode for the m04 instance variable
     * @return errorcode (int)
     */
    public int getM04Error() {
        return m04Error;
    } // method getM04Error

    /**
     * Gets the errorMessage for the m04 instance variable
     * @return errorMessage (String)
     */
    public String getM04ErrorMessage() {
        return m04ErrorMessage;
    } // method getM04ErrorMessage


    /**
     * Gets the errorcode for the m05 instance variable
     * @return errorcode (int)
     */
    public int getM05Error() {
        return m05Error;
    } // method getM05Error

    /**
     * Gets the errorMessage for the m05 instance variable
     * @return errorMessage (String)
     */
    public String getM05ErrorMessage() {
        return m05ErrorMessage;
    } // method getM05ErrorMessage


    /**
     * Gets the errorcode for the m06 instance variable
     * @return errorcode (int)
     */
    public int getM06Error() {
        return m06Error;
    } // method getM06Error

    /**
     * Gets the errorMessage for the m06 instance variable
     * @return errorMessage (String)
     */
    public String getM06ErrorMessage() {
        return m06ErrorMessage;
    } // method getM06ErrorMessage


    /**
     * Gets the errorcode for the m07 instance variable
     * @return errorcode (int)
     */
    public int getM07Error() {
        return m07Error;
    } // method getM07Error

    /**
     * Gets the errorMessage for the m07 instance variable
     * @return errorMessage (String)
     */
    public String getM07ErrorMessage() {
        return m07ErrorMessage;
    } // method getM07ErrorMessage


    /**
     * Gets the errorcode for the m08 instance variable
     * @return errorcode (int)
     */
    public int getM08Error() {
        return m08Error;
    } // method getM08Error

    /**
     * Gets the errorMessage for the m08 instance variable
     * @return errorMessage (String)
     */
    public String getM08ErrorMessage() {
        return m08ErrorMessage;
    } // method getM08ErrorMessage


    /**
     * Gets the errorcode for the m09 instance variable
     * @return errorcode (int)
     */
    public int getM09Error() {
        return m09Error;
    } // method getM09Error

    /**
     * Gets the errorMessage for the m09 instance variable
     * @return errorMessage (String)
     */
    public String getM09ErrorMessage() {
        return m09ErrorMessage;
    } // method getM09ErrorMessage


    /**
     * Gets the errorcode for the m10 instance variable
     * @return errorcode (int)
     */
    public int getM10Error() {
        return m10Error;
    } // method getM10Error

    /**
     * Gets the errorMessage for the m10 instance variable
     * @return errorMessage (String)
     */
    public String getM10ErrorMessage() {
        return m10ErrorMessage;
    } // method getM10ErrorMessage


    /**
     * Gets the errorcode for the m11 instance variable
     * @return errorcode (int)
     */
    public int getM11Error() {
        return m11Error;
    } // method getM11Error

    /**
     * Gets the errorMessage for the m11 instance variable
     * @return errorMessage (String)
     */
    public String getM11ErrorMessage() {
        return m11ErrorMessage;
    } // method getM11ErrorMessage


    /**
     * Gets the errorcode for the m12 instance variable
     * @return errorcode (int)
     */
    public int getM12Error() {
        return m12Error;
    } // method getM12Error

    /**
     * Gets the errorMessage for the m12 instance variable
     * @return errorMessage (String)
     */
    public String getM12ErrorMessage() {
        return m12ErrorMessage;
    } // method getM12ErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of WetPeriodCounts. e.g.<pre>
     * WetPeriodCounts wetPeriodCounts = new WetPeriodCounts(<val1>);
     * WetPeriodCounts wetPeriodCountsArray[] = wetPeriodCounts.get();</pre>
     * will get the WetPeriodCounts record where stationId = <val1>.
     * @return Array of WetPeriodCounts (WetPeriodCounts[])
     */
    public WetPeriodCounts[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * WetPeriodCounts wetPeriodCountsArray[] = 
     *     new WetPeriodCounts().get(WetPeriodCounts.STATION_ID+"=<val1>");</pre>
     * will get the WetPeriodCounts record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of WetPeriodCounts (WetPeriodCounts[])
     */
    public WetPeriodCounts[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * WetPeriodCounts wetPeriodCountsArray[] = 
     *     new WetPeriodCounts().get("1=1",wetPeriodCounts.STATION_ID);</pre>
     * will get the all the WetPeriodCounts records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetPeriodCounts (WetPeriodCounts[])
     */
    public WetPeriodCounts[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = WetPeriodCounts.STATION_ID,WetPeriodCounts.YEARP;
     * String where = WetPeriodCounts.STATION_ID + "=<val1";
     * String order = WetPeriodCounts.STATION_ID;
     * WetPeriodCounts wetPeriodCountsArray[] = 
     *     new WetPeriodCounts().get(columns, where, order);</pre>
     * will get the stationId and yearp colums of all WetPeriodCounts records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of WetPeriodCounts (WetPeriodCounts[])
     */
    public WetPeriodCounts[] get (String fields, String where, String order) {
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
     * and transforms it into an array of WetPeriodCounts.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private WetPeriodCounts[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol = db.getColNumber(STATION_ID);
        int yearpCol     = db.getColNumber(YEARP);
        int m01Col       = db.getColNumber(M01);
        int m02Col       = db.getColNumber(M02);
        int m03Col       = db.getColNumber(M03);
        int m04Col       = db.getColNumber(M04);
        int m05Col       = db.getColNumber(M05);
        int m06Col       = db.getColNumber(M06);
        int m07Col       = db.getColNumber(M07);
        int m08Col       = db.getColNumber(M08);
        int m09Col       = db.getColNumber(M09);
        int m10Col       = db.getColNumber(M10);
        int m11Col       = db.getColNumber(M11);
        int m12Col       = db.getColNumber(M12);
        WetPeriodCounts[] cArray = new WetPeriodCounts[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new WetPeriodCounts();
            if (stationIdCol != -1)
                cArray[i].setStationId((String) row.elementAt(stationIdCol));
            if (yearpCol != -1)
                cArray[i].setYearp    ((String) row.elementAt(yearpCol));
            if (m01Col != -1)
                cArray[i].setM01      ((String) row.elementAt(m01Col));
            if (m02Col != -1)
                cArray[i].setM02      ((String) row.elementAt(m02Col));
            if (m03Col != -1)
                cArray[i].setM03      ((String) row.elementAt(m03Col));
            if (m04Col != -1)
                cArray[i].setM04      ((String) row.elementAt(m04Col));
            if (m05Col != -1)
                cArray[i].setM05      ((String) row.elementAt(m05Col));
            if (m06Col != -1)
                cArray[i].setM06      ((String) row.elementAt(m06Col));
            if (m07Col != -1)
                cArray[i].setM07      ((String) row.elementAt(m07Col));
            if (m08Col != -1)
                cArray[i].setM08      ((String) row.elementAt(m08Col));
            if (m09Col != -1)
                cArray[i].setM09      ((String) row.elementAt(m09Col));
            if (m10Col != -1)
                cArray[i].setM10      ((String) row.elementAt(m10Col));
            if (m11Col != -1)
                cArray[i].setM11      ((String) row.elementAt(m11Col));
            if (m12Col != -1)
                cArray[i].setM12      ((String) row.elementAt(m12Col));
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
     *     new WetPeriodCounts(
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
     *         <val13>,
     *         <val14>).put();</pre>
     * will insert a record with:
     *     stationId = <val1>,
     *     yearp     = <val2>,
     *     m01       = <val3>,
     *     m02       = <val4>,
     *     m03       = <val5>,
     *     m04       = <val6>,
     *     m05       = <val7>,
     *     m06       = <val8>,
     *     m07       = <val9>,
     *     m08       = <val10>,
     *     m09       = <val11>,
     *     m10       = <val12>,
     *     m11       = <val13>,
     *     m12       = <val14>.
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
     * Boolean success = new WetPeriodCounts(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where yearp = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WetPeriodCounts wetPeriodCounts = new WetPeriodCounts();
     * success = wetPeriodCounts.del(WetPeriodCounts.STATION_ID+"=<val1>");</pre>
     * will delete all records where stationId = <val1>.
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
     * update are taken from the WetPeriodCounts argument, .e.g.<pre>
     * Boolean success
     * WetPeriodCounts updWetPeriodCounts = new WetPeriodCounts();
     * updWetPeriodCounts.setYearp(<val2>);
     * WetPeriodCounts whereWetPeriodCounts = new WetPeriodCounts(<val1>);
     * success = whereWetPeriodCounts.upd(updWetPeriodCounts);</pre>
     * will update Yearp to <val2> for all records where 
     * stationId = <val1>.
     * @param wetPeriodCounts  A WetPeriodCounts variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(WetPeriodCounts wetPeriodCounts) {
        return db.update (TABLE, createColVals(wetPeriodCounts), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * WetPeriodCounts updWetPeriodCounts = new WetPeriodCounts();
     * updWetPeriodCounts.setYearp(<val2>);
     * WetPeriodCounts whereWetPeriodCounts = new WetPeriodCounts();
     * success = whereWetPeriodCounts.upd(
     *     updWetPeriodCounts, WetPeriodCounts.STATION_ID+"=<val1>");</pre>
     * will update Yearp to <val2> for all records where 
     * stationId = <val1>.
     * @param  updWetPeriodCounts  A WetPeriodCounts variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(WetPeriodCounts wetPeriodCounts, String where) {
        return db.update (TABLE, createColVals(wetPeriodCounts), where);
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
        if (getStationId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATION_ID + "='" + getStationId() + "'";
        } // if getStationId
        if (getYearp() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + YEARP + "=" + getYearp("");
        } // if getYearp
        if (getM01() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M01 + "=" + getM01("");
        } // if getM01
        if (getM02() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M02 + "=" + getM02("");
        } // if getM02
        if (getM03() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M03 + "=" + getM03("");
        } // if getM03
        if (getM04() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M04 + "=" + getM04("");
        } // if getM04
        if (getM05() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M05 + "=" + getM05("");
        } // if getM05
        if (getM06() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M06 + "=" + getM06("");
        } // if getM06
        if (getM07() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M07 + "=" + getM07("");
        } // if getM07
        if (getM08() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M08 + "=" + getM08("");
        } // if getM08
        if (getM09() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M09 + "=" + getM09("");
        } // if getM09
        if (getM10() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M10 + "=" + getM10("");
        } // if getM10
        if (getM11() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M11 + "=" + getM11("");
        } // if getM11
        if (getM12() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + M12 + "=" + getM12("");
        } // if getM12
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(WetPeriodCounts aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getYearp() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += YEARP +"=";
            colVals += (aVar.getYearp() == INTNULL2 ?
                "null" : aVar.getYearp(""));
        } // if aVar.getYearp
        if (aVar.getM01() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M01 +"=";
            colVals += (aVar.getM01() == INTNULL2 ?
                "null" : aVar.getM01(""));
        } // if aVar.getM01
        if (aVar.getM02() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M02 +"=";
            colVals += (aVar.getM02() == INTNULL2 ?
                "null" : aVar.getM02(""));
        } // if aVar.getM02
        if (aVar.getM03() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M03 +"=";
            colVals += (aVar.getM03() == INTNULL2 ?
                "null" : aVar.getM03(""));
        } // if aVar.getM03
        if (aVar.getM04() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M04 +"=";
            colVals += (aVar.getM04() == INTNULL2 ?
                "null" : aVar.getM04(""));
        } // if aVar.getM04
        if (aVar.getM05() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M05 +"=";
            colVals += (aVar.getM05() == INTNULL2 ?
                "null" : aVar.getM05(""));
        } // if aVar.getM05
        if (aVar.getM06() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M06 +"=";
            colVals += (aVar.getM06() == INTNULL2 ?
                "null" : aVar.getM06(""));
        } // if aVar.getM06
        if (aVar.getM07() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M07 +"=";
            colVals += (aVar.getM07() == INTNULL2 ?
                "null" : aVar.getM07(""));
        } // if aVar.getM07
        if (aVar.getM08() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M08 +"=";
            colVals += (aVar.getM08() == INTNULL2 ?
                "null" : aVar.getM08(""));
        } // if aVar.getM08
        if (aVar.getM09() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M09 +"=";
            colVals += (aVar.getM09() == INTNULL2 ?
                "null" : aVar.getM09(""));
        } // if aVar.getM09
        if (aVar.getM10() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M10 +"=";
            colVals += (aVar.getM10() == INTNULL2 ?
                "null" : aVar.getM10(""));
        } // if aVar.getM10
        if (aVar.getM11() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M11 +"=";
            colVals += (aVar.getM11() == INTNULL2 ?
                "null" : aVar.getM11(""));
        } // if aVar.getM11
        if (aVar.getM12() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += M12 +"=";
            colVals += (aVar.getM12() == INTNULL2 ?
                "null" : aVar.getM12(""));
        } // if aVar.getM12
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (getYearp() != INTNULL) {
            columns = columns + "," + YEARP;
        } // if getYearp
        if (getM01() != INTNULL) {
            columns = columns + "," + M01;
        } // if getM01
        if (getM02() != INTNULL) {
            columns = columns + "," + M02;
        } // if getM02
        if (getM03() != INTNULL) {
            columns = columns + "," + M03;
        } // if getM03
        if (getM04() != INTNULL) {
            columns = columns + "," + M04;
        } // if getM04
        if (getM05() != INTNULL) {
            columns = columns + "," + M05;
        } // if getM05
        if (getM06() != INTNULL) {
            columns = columns + "," + M06;
        } // if getM06
        if (getM07() != INTNULL) {
            columns = columns + "," + M07;
        } // if getM07
        if (getM08() != INTNULL) {
            columns = columns + "," + M08;
        } // if getM08
        if (getM09() != INTNULL) {
            columns = columns + "," + M09;
        } // if getM09
        if (getM10() != INTNULL) {
            columns = columns + "," + M10;
        } // if getM10
        if (getM11() != INTNULL) {
            columns = columns + "," + M11;
        } // if getM11
        if (getM12() != INTNULL) {
            columns = columns + "," + M12;
        } // if getM12
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (getYearp() != INTNULL) {
            values  = values  + "," + getYearp("");
        } // if getYearp
        if (getM01() != INTNULL) {
            values  = values  + "," + getM01("");
        } // if getM01
        if (getM02() != INTNULL) {
            values  = values  + "," + getM02("");
        } // if getM02
        if (getM03() != INTNULL) {
            values  = values  + "," + getM03("");
        } // if getM03
        if (getM04() != INTNULL) {
            values  = values  + "," + getM04("");
        } // if getM04
        if (getM05() != INTNULL) {
            values  = values  + "," + getM05("");
        } // if getM05
        if (getM06() != INTNULL) {
            values  = values  + "," + getM06("");
        } // if getM06
        if (getM07() != INTNULL) {
            values  = values  + "," + getM07("");
        } // if getM07
        if (getM08() != INTNULL) {
            values  = values  + "," + getM08("");
        } // if getM08
        if (getM09() != INTNULL) {
            values  = values  + "," + getM09("");
        } // if getM09
        if (getM10() != INTNULL) {
            values  = values  + "," + getM10("");
        } // if getM10
        if (getM11() != INTNULL) {
            values  = values  + "," + getM11("");
        } // if getM11
        if (getM12() != INTNULL) {
            values  = values  + "," + getM12("");
        } // if getM12
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getStationId("") + "|" +
            getYearp("")     + "|" +
            getM01("")       + "|" +
            getM02("")       + "|" +
            getM03("")       + "|" +
            getM04("")       + "|" +
            getM05("")       + "|" +
            getM06("")       + "|" +
            getM07("")       + "|" +
            getM08("")       + "|" +
            getM09("")       + "|" +
            getM10("")       + "|" +
            getM11("")       + "|" +
            getM12("")       + "|";
    } // method toString

} // class WetPeriodCounts
