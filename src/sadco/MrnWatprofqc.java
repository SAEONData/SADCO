package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the WATPROFQC table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 070807 - SIT Group
 * @version
 * 070807 - GenTableClassB class - create class<br>
 */
public class MrnWatprofqc extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "WATPROFQC";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The subdes field name */
    public static final String SUBDES = "SUBDES";
    /** The disoxygen field name */
    public static final String DISOXYGEN = "DISOXYGEN";
    /** The salinity field name */
    public static final String SALINITY = "SALINITY";
    /** The temperature field name */
    public static final String TEMPERATURE = "TEMPERATURE";
    /** The no3 field name */
    public static final String NO3 = "NO3";
    /** The po4 field name */
    public static final String PO4 = "PO4";
    /** The sio3 field name */
    public static final String SIO3 = "SIO3";
    /** The chla field name */
    public static final String CHLA = "CHLA";
    /** The dic field name */
    public static final String DIC = "DIC";
    /** The ph field name */
    public static final String PH = "PH";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private String    subdes;
    private int       disoxygen;
    private int       salinity;
    private int       temperature;
    private int       no3;
    private int       po4;
    private int       sio3;
    private int       chla;
    private int       dic;
    private int       ph;

    /** The error variables  */
    private int stationIdError   = ERROR_NORMAL;
    private int subdesError      = ERROR_NORMAL;
    private int disoxygenError   = ERROR_NORMAL;
    private int salinityError    = ERROR_NORMAL;
    private int temperatureError = ERROR_NORMAL;
    private int no3Error         = ERROR_NORMAL;
    private int po4Error         = ERROR_NORMAL;
    private int sio3Error        = ERROR_NORMAL;
    private int chlaError        = ERROR_NORMAL;
    private int dicError         = ERROR_NORMAL;
    private int phError          = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage   = "";
    private String subdesErrorMessage      = "";
    private String disoxygenErrorMessage   = "";
    private String salinityErrorMessage    = "";
    private String temperatureErrorMessage = "";
    private String no3ErrorMessage         = "";
    private String po4ErrorMessage         = "";
    private String sio3ErrorMessage        = "";
    private String chlaErrorMessage        = "";
    private String dicErrorMessage         = "";
    private String phErrorMessage          = "";

    /** The min-max constants for all numerics */
    public static final int       DISOXYGEN_MN = INTMIN;
    public static final int       DISOXYGEN_MX = INTMAX;
    public static final int       SALINITY_MN = INTMIN;
    public static final int       SALINITY_MX = INTMAX;
    public static final int       TEMPERATURE_MN = INTMIN;
    public static final int       TEMPERATURE_MX = INTMAX;
    public static final int       NO3_MN = INTMIN;
    public static final int       NO3_MX = INTMAX;
    public static final int       PO4_MN = INTMIN;
    public static final int       PO4_MX = INTMAX;
    public static final int       SIO3_MN = INTMIN;
    public static final int       SIO3_MX = INTMAX;
    public static final int       CHLA_MN = INTMIN;
    public static final int       CHLA_MX = INTMAX;
    public static final int       DIC_MN = INTMIN;
    public static final int       DIC_MX = INTMAX;
    public static final int       PH_MN = INTMIN;
    public static final int       PH_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception disoxygenOutOfBoundsException =
        new Exception ("'disoxygen' out of bounds: " +
            DISOXYGEN_MN + " - " + DISOXYGEN_MX);
    Exception salinityOutOfBoundsException =
        new Exception ("'salinity' out of bounds: " +
            SALINITY_MN + " - " + SALINITY_MX);
    Exception temperatureOutOfBoundsException =
        new Exception ("'temperature' out of bounds: " +
            TEMPERATURE_MN + " - " + TEMPERATURE_MX);
    Exception no3OutOfBoundsException =
        new Exception ("'no3' out of bounds: " +
            NO3_MN + " - " + NO3_MX);
    Exception po4OutOfBoundsException =
        new Exception ("'po4' out of bounds: " +
            PO4_MN + " - " + PO4_MX);
    Exception sio3OutOfBoundsException =
        new Exception ("'sio3' out of bounds: " +
            SIO3_MN + " - " + SIO3_MX);
    Exception chlaOutOfBoundsException =
        new Exception ("'chla' out of bounds: " +
            CHLA_MN + " - " + CHLA_MX);
    Exception dicOutOfBoundsException =
        new Exception ("'dic' out of bounds: " +
            DIC_MN + " - " + DIC_MX);
    Exception phOutOfBoundsException =
        new Exception ("'ph' out of bounds: " +
            PH_MN + " - " + PH_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnWatprofqc() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnWatprofqc constructor 1"); // debug
    } // MrnWatprofqc constructor

    /**
     * Instantiate a MrnWatprofqc object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public MrnWatprofqc(
            String stationId) {
        this();
        setStationId   (stationId  );
        if (dbg) System.out.println ("<br>in MrnWatprofqc constructor 2"); // debug
    } // MrnWatprofqc constructor

    /**
     * Instantiate a MrnWatprofqc object and initialize the instance variables.
     * @param stationId    The stationId   (String)
     * @param subdes       The subdes      (String)
     * @param disoxygen    The disoxygen   (int)
     * @param salinity     The salinity    (int)
     * @param temperature  The temperature (int)
     * @param no3          The no3         (int)
     * @param po4          The po4         (int)
     * @param sio3         The sio3        (int)
     * @param chla         The chla        (int)
     * @param dic          The dic         (int)
     * @param ph           The ph          (int)
     * @return A MrnWatprofqc object
     */
    public MrnWatprofqc(
            String stationId,
            String subdes,
            int    disoxygen,
            int    salinity,
            int    temperature,
            int    no3,
            int    po4,
            int    sio3,
            int    chla,
            int    dic,
            int    ph) {
        this();
        setStationId   (stationId  );
        setSubdes      (subdes     );
        setDisoxygen   (disoxygen  );
        setSalinity    (salinity   );
        setTemperature (temperature);
        setNo3         (no3        );
        setPo4         (po4        );
        setSio3        (sio3       );
        setChla        (chla       );
        setDic         (dic        );
        setPh          (ph         );
        if (dbg) System.out.println ("<br>in MrnWatprofqc constructor 3"); // debug
    } // MrnWatprofqc constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId   (CHARNULL);
        setSubdes      (CHARNULL);
        setDisoxygen   (INTNULL );
        setSalinity    (INTNULL );
        setTemperature (INTNULL );
        setNo3         (INTNULL );
        setPo4         (INTNULL );
        setSio3        (INTNULL );
        setChla        (INTNULL );
        setDic         (INTNULL );
        setPh          (INTNULL );
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
     * Set the 'subdes' class variable
     * @param  subdes (String)
     */
    public int setSubdes(String subdes) {
        try {
            this.subdes = subdes;
            if (this.subdes != CHARNULL) {
                this.subdes = stripCRLF(this.subdes.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>subdes = " + this.subdes);
        } catch (Exception e) {
            setSubdesError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return subdesError;
    } // method setSubdes

    /**
     * Called when an exception has occured
     * @param  subdes (String)
     */
    private void setSubdesError (String subdes, Exception e, int error) {
        this.subdes = subdes;
        subdesErrorMessage = e.toString();
        subdesError = error;
    } // method setSubdesError


    /**
     * Set the 'disoxygen' class variable
     * @param  disoxygen (int)
     */
    public int setDisoxygen(int disoxygen) {
        try {
            if ( ((disoxygen == INTNULL) ||
                  (disoxygen == INTNULL2)) ||
                !((disoxygen < DISOXYGEN_MN) ||
                  (disoxygen > DISOXYGEN_MX)) ) {
                this.disoxygen = disoxygen;
                disoxygenError = ERROR_NORMAL;
            } else {
                throw disoxygenOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDisoxygenError(INTNULL, e, ERROR_LOCAL);
        } // try
        return disoxygenError;
    } // method setDisoxygen

    /**
     * Set the 'disoxygen' class variable
     * @param  disoxygen (Integer)
     */
    public int setDisoxygen(Integer disoxygen) {
        try {
            setDisoxygen(disoxygen.intValue());
        } catch (Exception e) {
            setDisoxygenError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return disoxygenError;
    } // method setDisoxygen

    /**
     * Set the 'disoxygen' class variable
     * @param  disoxygen (Float)
     */
    public int setDisoxygen(Float disoxygen) {
        try {
            if (disoxygen.floatValue() == FLOATNULL) {
                setDisoxygen(INTNULL);
            } else {
                setDisoxygen(disoxygen.intValue());
            } // if (disoxygen.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDisoxygenError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return disoxygenError;
    } // method setDisoxygen

    /**
     * Set the 'disoxygen' class variable
     * @param  disoxygen (String)
     */
    public int setDisoxygen(String disoxygen) {
        try {
            setDisoxygen(new Integer(disoxygen).intValue());
        } catch (Exception e) {
            setDisoxygenError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return disoxygenError;
    } // method setDisoxygen

    /**
     * Called when an exception has occured
     * @param  disoxygen (String)
     */
    private void setDisoxygenError (int disoxygen, Exception e, int error) {
        this.disoxygen = disoxygen;
        disoxygenErrorMessage = e.toString();
        disoxygenError = error;
    } // method setDisoxygenError


    /**
     * Set the 'salinity' class variable
     * @param  salinity (int)
     */
    public int setSalinity(int salinity) {
        try {
            if ( ((salinity == INTNULL) ||
                  (salinity == INTNULL2)) ||
                !((salinity < SALINITY_MN) ||
                  (salinity > SALINITY_MX)) ) {
                this.salinity = salinity;
                salinityError = ERROR_NORMAL;
            } else {
                throw salinityOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSalinityError(INTNULL, e, ERROR_LOCAL);
        } // try
        return salinityError;
    } // method setSalinity

    /**
     * Set the 'salinity' class variable
     * @param  salinity (Integer)
     */
    public int setSalinity(Integer salinity) {
        try {
            setSalinity(salinity.intValue());
        } catch (Exception e) {
            setSalinityError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return salinityError;
    } // method setSalinity

    /**
     * Set the 'salinity' class variable
     * @param  salinity (Float)
     */
    public int setSalinity(Float salinity) {
        try {
            if (salinity.floatValue() == FLOATNULL) {
                setSalinity(INTNULL);
            } else {
                setSalinity(salinity.intValue());
            } // if (salinity.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSalinityError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return salinityError;
    } // method setSalinity

    /**
     * Set the 'salinity' class variable
     * @param  salinity (String)
     */
    public int setSalinity(String salinity) {
        try {
            setSalinity(new Integer(salinity).intValue());
        } catch (Exception e) {
            setSalinityError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return salinityError;
    } // method setSalinity

    /**
     * Called when an exception has occured
     * @param  salinity (String)
     */
    private void setSalinityError (int salinity, Exception e, int error) {
        this.salinity = salinity;
        salinityErrorMessage = e.toString();
        salinityError = error;
    } // method setSalinityError


    /**
     * Set the 'temperature' class variable
     * @param  temperature (int)
     */
    public int setTemperature(int temperature) {
        try {
            if ( ((temperature == INTNULL) ||
                  (temperature == INTNULL2)) ||
                !((temperature < TEMPERATURE_MN) ||
                  (temperature > TEMPERATURE_MX)) ) {
                this.temperature = temperature;
                temperatureError = ERROR_NORMAL;
            } else {
                throw temperatureOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTemperatureError(INTNULL, e, ERROR_LOCAL);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Set the 'temperature' class variable
     * @param  temperature (Integer)
     */
    public int setTemperature(Integer temperature) {
        try {
            setTemperature(temperature.intValue());
        } catch (Exception e) {
            setTemperatureError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Set the 'temperature' class variable
     * @param  temperature (Float)
     */
    public int setTemperature(Float temperature) {
        try {
            if (temperature.floatValue() == FLOATNULL) {
                setTemperature(INTNULL);
            } else {
                setTemperature(temperature.intValue());
            } // if (temperature.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTemperatureError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Set the 'temperature' class variable
     * @param  temperature (String)
     */
    public int setTemperature(String temperature) {
        try {
            setTemperature(new Integer(temperature).intValue());
        } catch (Exception e) {
            setTemperatureError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Called when an exception has occured
     * @param  temperature (String)
     */
    private void setTemperatureError (int temperature, Exception e, int error) {
        this.temperature = temperature;
        temperatureErrorMessage = e.toString();
        temperatureError = error;
    } // method setTemperatureError


    /**
     * Set the 'no3' class variable
     * @param  no3 (int)
     */
    public int setNo3(int no3) {
        try {
            if ( ((no3 == INTNULL) ||
                  (no3 == INTNULL2)) ||
                !((no3 < NO3_MN) ||
                  (no3 > NO3_MX)) ) {
                this.no3 = no3;
                no3Error = ERROR_NORMAL;
            } else {
                throw no3OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNo3Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return no3Error;
    } // method setNo3

    /**
     * Set the 'no3' class variable
     * @param  no3 (Integer)
     */
    public int setNo3(Integer no3) {
        try {
            setNo3(no3.intValue());
        } catch (Exception e) {
            setNo3Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return no3Error;
    } // method setNo3

    /**
     * Set the 'no3' class variable
     * @param  no3 (Float)
     */
    public int setNo3(Float no3) {
        try {
            if (no3.floatValue() == FLOATNULL) {
                setNo3(INTNULL);
            } else {
                setNo3(no3.intValue());
            } // if (no3.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setNo3Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return no3Error;
    } // method setNo3

    /**
     * Set the 'no3' class variable
     * @param  no3 (String)
     */
    public int setNo3(String no3) {
        try {
            setNo3(new Integer(no3).intValue());
        } catch (Exception e) {
            setNo3Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return no3Error;
    } // method setNo3

    /**
     * Called when an exception has occured
     * @param  no3 (String)
     */
    private void setNo3Error (int no3, Exception e, int error) {
        this.no3 = no3;
        no3ErrorMessage = e.toString();
        no3Error = error;
    } // method setNo3Error


    /**
     * Set the 'po4' class variable
     * @param  po4 (int)
     */
    public int setPo4(int po4) {
        try {
            if ( ((po4 == INTNULL) ||
                  (po4 == INTNULL2)) ||
                !((po4 < PO4_MN) ||
                  (po4 > PO4_MX)) ) {
                this.po4 = po4;
                po4Error = ERROR_NORMAL;
            } else {
                throw po4OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPo4Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return po4Error;
    } // method setPo4

    /**
     * Set the 'po4' class variable
     * @param  po4 (Integer)
     */
    public int setPo4(Integer po4) {
        try {
            setPo4(po4.intValue());
        } catch (Exception e) {
            setPo4Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return po4Error;
    } // method setPo4

    /**
     * Set the 'po4' class variable
     * @param  po4 (Float)
     */
    public int setPo4(Float po4) {
        try {
            if (po4.floatValue() == FLOATNULL) {
                setPo4(INTNULL);
            } else {
                setPo4(po4.intValue());
            } // if (po4.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPo4Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return po4Error;
    } // method setPo4

    /**
     * Set the 'po4' class variable
     * @param  po4 (String)
     */
    public int setPo4(String po4) {
        try {
            setPo4(new Integer(po4).intValue());
        } catch (Exception e) {
            setPo4Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return po4Error;
    } // method setPo4

    /**
     * Called when an exception has occured
     * @param  po4 (String)
     */
    private void setPo4Error (int po4, Exception e, int error) {
        this.po4 = po4;
        po4ErrorMessage = e.toString();
        po4Error = error;
    } // method setPo4Error


    /**
     * Set the 'sio3' class variable
     * @param  sio3 (int)
     */
    public int setSio3(int sio3) {
        try {
            if ( ((sio3 == INTNULL) ||
                  (sio3 == INTNULL2)) ||
                !((sio3 < SIO3_MN) ||
                  (sio3 > SIO3_MX)) ) {
                this.sio3 = sio3;
                sio3Error = ERROR_NORMAL;
            } else {
                throw sio3OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSio3Error(INTNULL, e, ERROR_LOCAL);
        } // try
        return sio3Error;
    } // method setSio3

    /**
     * Set the 'sio3' class variable
     * @param  sio3 (Integer)
     */
    public int setSio3(Integer sio3) {
        try {
            setSio3(sio3.intValue());
        } catch (Exception e) {
            setSio3Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sio3Error;
    } // method setSio3

    /**
     * Set the 'sio3' class variable
     * @param  sio3 (Float)
     */
    public int setSio3(Float sio3) {
        try {
            if (sio3.floatValue() == FLOATNULL) {
                setSio3(INTNULL);
            } else {
                setSio3(sio3.intValue());
            } // if (sio3.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSio3Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sio3Error;
    } // method setSio3

    /**
     * Set the 'sio3' class variable
     * @param  sio3 (String)
     */
    public int setSio3(String sio3) {
        try {
            setSio3(new Integer(sio3).intValue());
        } catch (Exception e) {
            setSio3Error(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sio3Error;
    } // method setSio3

    /**
     * Called when an exception has occured
     * @param  sio3 (String)
     */
    private void setSio3Error (int sio3, Exception e, int error) {
        this.sio3 = sio3;
        sio3ErrorMessage = e.toString();
        sio3Error = error;
    } // method setSio3Error


    /**
     * Set the 'chla' class variable
     * @param  chla (int)
     */
    public int setChla(int chla) {
        try {
            if ( ((chla == INTNULL) ||
                  (chla == INTNULL2)) ||
                !((chla < CHLA_MN) ||
                  (chla > CHLA_MX)) ) {
                this.chla = chla;
                chlaError = ERROR_NORMAL;
            } else {
                throw chlaOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setChlaError(INTNULL, e, ERROR_LOCAL);
        } // try
        return chlaError;
    } // method setChla

    /**
     * Set the 'chla' class variable
     * @param  chla (Integer)
     */
    public int setChla(Integer chla) {
        try {
            setChla(chla.intValue());
        } catch (Exception e) {
            setChlaError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return chlaError;
    } // method setChla

    /**
     * Set the 'chla' class variable
     * @param  chla (Float)
     */
    public int setChla(Float chla) {
        try {
            if (chla.floatValue() == FLOATNULL) {
                setChla(INTNULL);
            } else {
                setChla(chla.intValue());
            } // if (chla.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setChlaError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return chlaError;
    } // method setChla

    /**
     * Set the 'chla' class variable
     * @param  chla (String)
     */
    public int setChla(String chla) {
        try {
            setChla(new Integer(chla).intValue());
        } catch (Exception e) {
            setChlaError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return chlaError;
    } // method setChla

    /**
     * Called when an exception has occured
     * @param  chla (String)
     */
    private void setChlaError (int chla, Exception e, int error) {
        this.chla = chla;
        chlaErrorMessage = e.toString();
        chlaError = error;
    } // method setChlaError


    /**
     * Set the 'dic' class variable
     * @param  dic (int)
     */
    public int setDic(int dic) {
        try {
            if ( ((dic == INTNULL) ||
                  (dic == INTNULL2)) ||
                !((dic < DIC_MN) ||
                  (dic > DIC_MX)) ) {
                this.dic = dic;
                dicError = ERROR_NORMAL;
            } else {
                throw dicOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDicError(INTNULL, e, ERROR_LOCAL);
        } // try
        return dicError;
    } // method setDic

    /**
     * Set the 'dic' class variable
     * @param  dic (Integer)
     */
    public int setDic(Integer dic) {
        try {
            setDic(dic.intValue());
        } catch (Exception e) {
            setDicError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dicError;
    } // method setDic

    /**
     * Set the 'dic' class variable
     * @param  dic (Float)
     */
    public int setDic(Float dic) {
        try {
            if (dic.floatValue() == FLOATNULL) {
                setDic(INTNULL);
            } else {
                setDic(dic.intValue());
            } // if (dic.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDicError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dicError;
    } // method setDic

    /**
     * Set the 'dic' class variable
     * @param  dic (String)
     */
    public int setDic(String dic) {
        try {
            setDic(new Integer(dic).intValue());
        } catch (Exception e) {
            setDicError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dicError;
    } // method setDic

    /**
     * Called when an exception has occured
     * @param  dic (String)
     */
    private void setDicError (int dic, Exception e, int error) {
        this.dic = dic;
        dicErrorMessage = e.toString();
        dicError = error;
    } // method setDicError


    /**
     * Set the 'ph' class variable
     * @param  ph (int)
     */
    public int setPh(int ph) {
        try {
            if ( ((ph == INTNULL) ||
                  (ph == INTNULL2)) ||
                !((ph < PH_MN) ||
                  (ph > PH_MX)) ) {
                this.ph = ph;
                phError = ERROR_NORMAL;
            } else {
                throw phOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPhError(INTNULL, e, ERROR_LOCAL);
        } // try
        return phError;
    } // method setPh

    /**
     * Set the 'ph' class variable
     * @param  ph (Integer)
     */
    public int setPh(Integer ph) {
        try {
            setPh(ph.intValue());
        } catch (Exception e) {
            setPhError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return phError;
    } // method setPh

    /**
     * Set the 'ph' class variable
     * @param  ph (Float)
     */
    public int setPh(Float ph) {
        try {
            if (ph.floatValue() == FLOATNULL) {
                setPh(INTNULL);
            } else {
                setPh(ph.intValue());
            } // if (ph.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPhError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return phError;
    } // method setPh

    /**
     * Set the 'ph' class variable
     * @param  ph (String)
     */
    public int setPh(String ph) {
        try {
            setPh(new Integer(ph).intValue());
        } catch (Exception e) {
            setPhError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return phError;
    } // method setPh

    /**
     * Called when an exception has occured
     * @param  ph (String)
     */
    private void setPhError (int ph, Exception e, int error) {
        this.ph = ph;
        phErrorMessage = e.toString();
        phError = error;
    } // method setPhError


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
     * Return the 'subdes' class variable
     * @return subdes (String)
     */
    public String getSubdes() {
        return subdes;
    } // method getSubdes

    /**
     * Return the 'subdes' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSubdes methods
     * @return subdes (String)
     */
    public String getSubdes(String s) {
        return (subdes != CHARNULL ? subdes.replace('"','\'') : "");
    } // method getSubdes


    /**
     * Return the 'disoxygen' class variable
     * @return disoxygen (int)
     */
    public int getDisoxygen() {
        return disoxygen;
    } // method getDisoxygen

    /**
     * Return the 'disoxygen' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDisoxygen methods
     * @return disoxygen (String)
     */
    public String getDisoxygen(String s) {
        return ((disoxygen != INTNULL) ? new Integer(disoxygen).toString() : "");
    } // method getDisoxygen


    /**
     * Return the 'salinity' class variable
     * @return salinity (int)
     */
    public int getSalinity() {
        return salinity;
    } // method getSalinity

    /**
     * Return the 'salinity' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSalinity methods
     * @return salinity (String)
     */
    public String getSalinity(String s) {
        return ((salinity != INTNULL) ? new Integer(salinity).toString() : "");
    } // method getSalinity


    /**
     * Return the 'temperature' class variable
     * @return temperature (int)
     */
    public int getTemperature() {
        return temperature;
    } // method getTemperature

    /**
     * Return the 'temperature' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTemperature methods
     * @return temperature (String)
     */
    public String getTemperature(String s) {
        return ((temperature != INTNULL) ? new Integer(temperature).toString() : "");
    } // method getTemperature


    /**
     * Return the 'no3' class variable
     * @return no3 (int)
     */
    public int getNo3() {
        return no3;
    } // method getNo3

    /**
     * Return the 'no3' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNo3 methods
     * @return no3 (String)
     */
    public String getNo3(String s) {
        return ((no3 != INTNULL) ? new Integer(no3).toString() : "");
    } // method getNo3


    /**
     * Return the 'po4' class variable
     * @return po4 (int)
     */
    public int getPo4() {
        return po4;
    } // method getPo4

    /**
     * Return the 'po4' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPo4 methods
     * @return po4 (String)
     */
    public String getPo4(String s) {
        return ((po4 != INTNULL) ? new Integer(po4).toString() : "");
    } // method getPo4


    /**
     * Return the 'sio3' class variable
     * @return sio3 (int)
     */
    public int getSio3() {
        return sio3;
    } // method getSio3

    /**
     * Return the 'sio3' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSio3 methods
     * @return sio3 (String)
     */
    public String getSio3(String s) {
        return ((sio3 != INTNULL) ? new Integer(sio3).toString() : "");
    } // method getSio3


    /**
     * Return the 'chla' class variable
     * @return chla (int)
     */
    public int getChla() {
        return chla;
    } // method getChla

    /**
     * Return the 'chla' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getChla methods
     * @return chla (String)
     */
    public String getChla(String s) {
        return ((chla != INTNULL) ? new Integer(chla).toString() : "");
    } // method getChla


    /**
     * Return the 'dic' class variable
     * @return dic (int)
     */
    public int getDic() {
        return dic;
    } // method getDic

    /**
     * Return the 'dic' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDic methods
     * @return dic (String)
     */
    public String getDic(String s) {
        return ((dic != INTNULL) ? new Integer(dic).toString() : "");
    } // method getDic


    /**
     * Return the 'ph' class variable
     * @return ph (int)
     */
    public int getPh() {
        return ph;
    } // method getPh

    /**
     * Return the 'ph' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPh methods
     * @return ph (String)
     */
    public String getPh(String s) {
        return ((ph != INTNULL) ? new Integer(ph).toString() : "");
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
        if ((stationId == CHARNULL) &&
            (subdes == CHARNULL) &&
            (disoxygen == INTNULL) &&
            (salinity == INTNULL) &&
            (temperature == INTNULL) &&
            (no3 == INTNULL) &&
            (po4 == INTNULL) &&
            (sio3 == INTNULL) &&
            (chla == INTNULL) &&
            (dic == INTNULL) &&
            (ph == INTNULL)) {
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
            subdesError +
            disoxygenError +
            salinityError +
            temperatureError +
            no3Error +
            po4Error +
            sio3Error +
            chlaError +
            dicError +
            phError;
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
     * Gets the errorcode for the subdes instance variable
     * @return errorcode (int)
     */
    public int getSubdesError() {
        return subdesError;
    } // method getSubdesError

    /**
     * Gets the errorMessage for the subdes instance variable
     * @return errorMessage (String)
     */
    public String getSubdesErrorMessage() {
        return subdesErrorMessage;
    } // method getSubdesErrorMessage


    /**
     * Gets the errorcode for the disoxygen instance variable
     * @return errorcode (int)
     */
    public int getDisoxygenError() {
        return disoxygenError;
    } // method getDisoxygenError

    /**
     * Gets the errorMessage for the disoxygen instance variable
     * @return errorMessage (String)
     */
    public String getDisoxygenErrorMessage() {
        return disoxygenErrorMessage;
    } // method getDisoxygenErrorMessage


    /**
     * Gets the errorcode for the salinity instance variable
     * @return errorcode (int)
     */
    public int getSalinityError() {
        return salinityError;
    } // method getSalinityError

    /**
     * Gets the errorMessage for the salinity instance variable
     * @return errorMessage (String)
     */
    public String getSalinityErrorMessage() {
        return salinityErrorMessage;
    } // method getSalinityErrorMessage


    /**
     * Gets the errorcode for the temperature instance variable
     * @return errorcode (int)
     */
    public int getTemperatureError() {
        return temperatureError;
    } // method getTemperatureError

    /**
     * Gets the errorMessage for the temperature instance variable
     * @return errorMessage (String)
     */
    public String getTemperatureErrorMessage() {
        return temperatureErrorMessage;
    } // method getTemperatureErrorMessage


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
     * Gets the errorcode for the chla instance variable
     * @return errorcode (int)
     */
    public int getChlaError() {
        return chlaError;
    } // method getChlaError

    /**
     * Gets the errorMessage for the chla instance variable
     * @return errorMessage (String)
     */
    public String getChlaErrorMessage() {
        return chlaErrorMessage;
    } // method getChlaErrorMessage


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
     * are returned in an array of MrnWatprofqc. e.g.<pre>
     * MrnWatprofqc watprofqc = new MrnWatprofqc(<val1>);
     * MrnWatprofqc watprofqcArray[] = watprofqc.get();</pre>
     * will get the MrnWatprofqc record where stationId = <val1>.
     * @return Array of MrnWatprofqc (MrnWatprofqc[])
     */
    public MrnWatprofqc[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnWatprofqc watprofqcArray[] =
     *     new MrnWatprofqc().get(MrnWatprofqc.STATION_ID+"=<val1>");</pre>
     * will get the MrnWatprofqc record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnWatprofqc (MrnWatprofqc[])
     */
    public MrnWatprofqc[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnWatprofqc watprofqcArray[] =
     *     new MrnWatprofqc().get("1=1",watprofqc.STATION_ID);</pre>
     * will get the all the MrnWatprofqc records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatprofqc (MrnWatprofqc[])
     */
    public MrnWatprofqc[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnWatprofqc.STATION_ID,MrnWatprofqc.SUBDES;
     * String where = MrnWatprofqc.STATION_ID + "=<val1";
     * String order = MrnWatprofqc.STATION_ID;
     * MrnWatprofqc watprofqcArray[] =
     *     new MrnWatprofqc().get(columns, where, order);</pre>
     * will get the stationId and subdes colums of all MrnWatprofqc records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnWatprofqc (MrnWatprofqc[])
     */
    public MrnWatprofqc[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnWatprofqc.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnWatprofqc[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol   = db.getColNumber(STATION_ID);
        int subdesCol      = db.getColNumber(SUBDES);
        int disoxygenCol   = db.getColNumber(DISOXYGEN);
        int salinityCol    = db.getColNumber(SALINITY);
        int temperatureCol = db.getColNumber(TEMPERATURE);
        int no3Col         = db.getColNumber(NO3);
        int po4Col         = db.getColNumber(PO4);
        int sio3Col        = db.getColNumber(SIO3);
        int chlaCol        = db.getColNumber(CHLA);
        int dicCol         = db.getColNumber(DIC);
        int phCol          = db.getColNumber(PH);
        MrnWatprofqc[] cArray = new MrnWatprofqc[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnWatprofqc();
            if (stationIdCol != -1)
                cArray[i].setStationId  ((String) row.elementAt(stationIdCol));
            if (subdesCol != -1)
                cArray[i].setSubdes     ((String) row.elementAt(subdesCol));
            if (disoxygenCol != -1)
                cArray[i].setDisoxygen  ((String) row.elementAt(disoxygenCol));
            if (salinityCol != -1)
                cArray[i].setSalinity   ((String) row.elementAt(salinityCol));
            if (temperatureCol != -1)
                cArray[i].setTemperature((String) row.elementAt(temperatureCol));
            if (no3Col != -1)
                cArray[i].setNo3        ((String) row.elementAt(no3Col));
            if (po4Col != -1)
                cArray[i].setPo4        ((String) row.elementAt(po4Col));
            if (sio3Col != -1)
                cArray[i].setSio3       ((String) row.elementAt(sio3Col));
            if (chlaCol != -1)
                cArray[i].setChla       ((String) row.elementAt(chlaCol));
            if (dicCol != -1)
                cArray[i].setDic        ((String) row.elementAt(dicCol));
            if (phCol != -1)
                cArray[i].setPh         ((String) row.elementAt(phCol));
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
     *     new MrnWatprofqc(
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
     *     stationId   = <val1>,
     *     subdes      = <val2>,
     *     disoxygen   = <val3>,
     *     salinity    = <val4>,
     *     temperature = <val5>,
     *     no3         = <val6>,
     *     po4         = <val7>,
     *     sio3        = <val8>,
     *     chla        = <val9>,
     *     dic         = <val10>,
     *     ph          = <val11>.
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
     * Boolean success = new MrnWatprofqc(
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
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where subdes = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatprofqc watprofqc = new MrnWatprofqc();
     * success = watprofqc.del(MrnWatprofqc.STATION_ID+"=<val1>");</pre>
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
     * update are taken from the MrnWatprofqc argument, .e.g.<pre>
     * Boolean success
     * MrnWatprofqc updMrnWatprofqc = new MrnWatprofqc();
     * updMrnWatprofqc.setSubdes(<val2>);
     * MrnWatprofqc whereMrnWatprofqc = new MrnWatprofqc(<val1>);
     * success = whereMrnWatprofqc.upd(updMrnWatprofqc);</pre>
     * will update Subdes to <val2> for all records where
     * stationId = <val1>.
     * @param watprofqc  A MrnWatprofqc variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatprofqc watprofqc) {
        return db.update (TABLE, createColVals(watprofqc), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnWatprofqc updMrnWatprofqc = new MrnWatprofqc();
     * updMrnWatprofqc.setSubdes(<val2>);
     * MrnWatprofqc whereMrnWatprofqc = new MrnWatprofqc();
     * success = whereMrnWatprofqc.upd(
     *     updMrnWatprofqc, MrnWatprofqc.STATION_ID+"=<val1>");</pre>
     * will update Subdes to <val2> for all records where
     * stationId = <val1>.
     * @param  updMrnWatprofqc  A MrnWatprofqc variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnWatprofqc watprofqc, String where) {
        return db.update (TABLE, createColVals(watprofqc), where);
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
        if (getSubdes() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SUBDES + "='" + getSubdes() + "'";
        } // if getSubdes
        if (getDisoxygen() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DISOXYGEN + "=" + getDisoxygen("");
        } // if getDisoxygen
        if (getSalinity() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SALINITY + "=" + getSalinity("");
        } // if getSalinity
        if (getTemperature() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TEMPERATURE + "=" + getTemperature("");
        } // if getTemperature
        if (getNo3() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NO3 + "=" + getNo3("");
        } // if getNo3
        if (getPo4() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PO4 + "=" + getPo4("");
        } // if getPo4
        if (getSio3() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SIO3 + "=" + getSio3("");
        } // if getSio3
        if (getChla() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CHLA + "=" + getChla("");
        } // if getChla
        if (getDic() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DIC + "=" + getDic("");
        } // if getDic
        if (getPh() != INTNULL) {
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
    private String createColVals(MrnWatprofqc aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getSubdes() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SUBDES +"=";
            colVals += (aVar.getSubdes().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSubdes() + "'");
        } // if aVar.getSubdes
        if (aVar.getDisoxygen() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DISOXYGEN +"=";
            colVals += (aVar.getDisoxygen() == INTNULL2 ?
                "null" : aVar.getDisoxygen(""));
        } // if aVar.getDisoxygen
        if (aVar.getSalinity() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SALINITY +"=";
            colVals += (aVar.getSalinity() == INTNULL2 ?
                "null" : aVar.getSalinity(""));
        } // if aVar.getSalinity
        if (aVar.getTemperature() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TEMPERATURE +"=";
            colVals += (aVar.getTemperature() == INTNULL2 ?
                "null" : aVar.getTemperature(""));
        } // if aVar.getTemperature
        if (aVar.getNo3() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NO3 +"=";
            colVals += (aVar.getNo3() == INTNULL2 ?
                "null" : aVar.getNo3(""));
        } // if aVar.getNo3
        if (aVar.getPo4() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PO4 +"=";
            colVals += (aVar.getPo4() == INTNULL2 ?
                "null" : aVar.getPo4(""));
        } // if aVar.getPo4
        if (aVar.getSio3() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SIO3 +"=";
            colVals += (aVar.getSio3() == INTNULL2 ?
                "null" : aVar.getSio3(""));
        } // if aVar.getSio3
        if (aVar.getChla() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CHLA +"=";
            colVals += (aVar.getChla() == INTNULL2 ?
                "null" : aVar.getChla(""));
        } // if aVar.getChla
        if (aVar.getDic() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DIC +"=";
            colVals += (aVar.getDic() == INTNULL2 ?
                "null" : aVar.getDic(""));
        } // if aVar.getDic
        if (aVar.getPh() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PH +"=";
            colVals += (aVar.getPh() == INTNULL2 ?
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
        String columns = STATION_ID;
        if (getSubdes() != CHARNULL) {
            columns = columns + "," + SUBDES;
        } // if getSubdes
        if (getDisoxygen() != INTNULL) {
            columns = columns + "," + DISOXYGEN;
        } // if getDisoxygen
        if (getSalinity() != INTNULL) {
            columns = columns + "," + SALINITY;
        } // if getSalinity
        if (getTemperature() != INTNULL) {
            columns = columns + "," + TEMPERATURE;
        } // if getTemperature
        if (getNo3() != INTNULL) {
            columns = columns + "," + NO3;
        } // if getNo3
        if (getPo4() != INTNULL) {
            columns = columns + "," + PO4;
        } // if getPo4
        if (getSio3() != INTNULL) {
            columns = columns + "," + SIO3;
        } // if getSio3
        if (getChla() != INTNULL) {
            columns = columns + "," + CHLA;
        } // if getChla
        if (getDic() != INTNULL) {
            columns = columns + "," + DIC;
        } // if getDic
        if (getPh() != INTNULL) {
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
        String values  = "'" + getStationId() + "'";
        if (getSubdes() != CHARNULL) {
            values  = values  + ",'" + getSubdes() + "'";
        } // if getSubdes
        if (getDisoxygen() != INTNULL) {
            values  = values  + "," + getDisoxygen("");
        } // if getDisoxygen
        if (getSalinity() != INTNULL) {
            values  = values  + "," + getSalinity("");
        } // if getSalinity
        if (getTemperature() != INTNULL) {
            values  = values  + "," + getTemperature("");
        } // if getTemperature
        if (getNo3() != INTNULL) {
            values  = values  + "," + getNo3("");
        } // if getNo3
        if (getPo4() != INTNULL) {
            values  = values  + "," + getPo4("");
        } // if getPo4
        if (getSio3() != INTNULL) {
            values  = values  + "," + getSio3("");
        } // if getSio3
        if (getChla() != INTNULL) {
            values  = values  + "," + getChla("");
        } // if getChla
        if (getDic() != INTNULL) {
            values  = values  + "," + getDic("");
        } // if getDic
        if (getPh() != INTNULL) {
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
            getStationId("")   + "|" +
            getSubdes("")      + "|" +
            getDisoxygen("")   + "|" +
            getSalinity("")    + "|" +
            getTemperature("") + "|" +
            getNo3("")         + "|" +
            getPo4("")         + "|" +
            getSio3("")        + "|" +
            getChla("")        + "|" +
            getDic("")         + "|" +
            getPh("")          + "|";
    } // method toString

} // class MrnWatprofqc
