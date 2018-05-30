package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SEDPHY table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnSedphy extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SEDPHY";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The deviceCode field name */
    public static final String DEVICE_CODE = "DEVICE_CODE";
    /** The methodCode field name */
    public static final String METHOD_CODE = "METHOD_CODE";
    /** The standardCode field name */
    public static final String STANDARD_CODE = "STANDARD_CODE";
    /** The subdes field name */
    public static final String SUBDES = "SUBDES";
    /** The spldattim field name */
    public static final String SPLDATTIM = "SPLDATTIM";
    /** The spldep field name */
    public static final String SPLDEP = "SPLDEP";
    /** The spldis field name */
    public static final String SPLDIS = "SPLDIS";
    /** The splvol field name */
    public static final String SPLVOL = "SPLVOL";
    /** The sievsz field name */
    public static final String SIEVSZ = "SIEVSZ";
    /** The kurt field name */
    public static final String KURT = "KURT";
    /** The skew field name */
    public static final String SKEW = "SKEW";
    /** The meanpz field name */
    public static final String MEANPZ = "MEANPZ";
    /** The medipz field name */
    public static final String MEDIPZ = "MEDIPZ";
    /** The pctsat field name */
    public static final String PCTSAT = "PCTSAT";
    /** The pctsil field name */
    public static final String PCTSIL = "PCTSIL";
    /** The permty field name */
    public static final String PERMTY = "PERMTY";
    /** The porsty field name */
    public static final String PORSTY = "PORSTY";
    /** The dwf field name */
    public static final String DWF = "DWF";
    /** The cod field name */
    public static final String COD = "COD";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private String    stationId;
    private int       deviceCode;
    private int       methodCode;
    private int       standardCode;
    private String    subdes;
    private Timestamp spldattim;
    private float     spldep;
    private int       spldis;
    private float     splvol;
    private float     sievsz;
    private float     kurt;
    private float     skew;
    private int       meanpz;
    private int       medipz;
    private float     pctsat;
    private float     pctsil;
    private int       permty;
    private float     porsty;
    private float     dwf;
    private float     cod;

    /** The error variables  */
    private int codeError         = ERROR_NORMAL;
    private int stationIdError    = ERROR_NORMAL;
    private int deviceCodeError   = ERROR_NORMAL;
    private int methodCodeError   = ERROR_NORMAL;
    private int standardCodeError = ERROR_NORMAL;
    private int subdesError       = ERROR_NORMAL;
    private int spldattimError    = ERROR_NORMAL;
    private int spldepError       = ERROR_NORMAL;
    private int spldisError       = ERROR_NORMAL;
    private int splvolError       = ERROR_NORMAL;
    private int sievszError       = ERROR_NORMAL;
    private int kurtError         = ERROR_NORMAL;
    private int skewError         = ERROR_NORMAL;
    private int meanpzError       = ERROR_NORMAL;
    private int medipzError       = ERROR_NORMAL;
    private int pctsatError       = ERROR_NORMAL;
    private int pctsilError       = ERROR_NORMAL;
    private int permtyError       = ERROR_NORMAL;
    private int porstyError       = ERROR_NORMAL;
    private int dwfError          = ERROR_NORMAL;
    private int codError          = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage         = "";
    private String stationIdErrorMessage    = "";
    private String deviceCodeErrorMessage   = "";
    private String methodCodeErrorMessage   = "";
    private String standardCodeErrorMessage = "";
    private String subdesErrorMessage       = "";
    private String spldattimErrorMessage    = "";
    private String spldepErrorMessage       = "";
    private String spldisErrorMessage       = "";
    private String splvolErrorMessage       = "";
    private String sievszErrorMessage       = "";
    private String kurtErrorMessage         = "";
    private String skewErrorMessage         = "";
    private String meanpzErrorMessage       = "";
    private String medipzErrorMessage       = "";
    private String pctsatErrorMessage       = "";
    private String pctsilErrorMessage       = "";
    private String permtyErrorMessage       = "";
    private String porstyErrorMessage       = "";
    private String dwfErrorMessage          = "";
    private String codErrorMessage          = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       DEVICE_CODE_MN = INTMIN;
    public static final int       DEVICE_CODE_MX = INTMAX;
    public static final int       METHOD_CODE_MN = INTMIN;
    public static final int       METHOD_CODE_MX = INTMAX;
    public static final int       STANDARD_CODE_MN = INTMIN;
    public static final int       STANDARD_CODE_MX = INTMAX;
    public static final Timestamp SPLDATTIM_MN = DATEMIN;
    public static final Timestamp SPLDATTIM_MX = DATEMAX;
    public static final float     SPLDEP_MN = FLOATMIN;
    public static final float     SPLDEP_MX = FLOATMAX;
    public static final int       SPLDIS_MN = INTMIN;
    public static final int       SPLDIS_MX = INTMAX;
    public static final float     SPLVOL_MN = FLOATMIN;
    public static final float     SPLVOL_MX = FLOATMAX;
    public static final float     SIEVSZ_MN = FLOATMIN;
    public static final float     SIEVSZ_MX = FLOATMAX;
    public static final float     KURT_MN = FLOATMIN;
    public static final float     KURT_MX = FLOATMAX;
    public static final float     SKEW_MN = FLOATMIN;
    public static final float     SKEW_MX = FLOATMAX;
    public static final int       MEANPZ_MN = INTMIN;
    public static final int       MEANPZ_MX = INTMAX;
    public static final int       MEDIPZ_MN = INTMIN;
    public static final int       MEDIPZ_MX = INTMAX;
    public static final float     PCTSAT_MN = FLOATMIN;
    public static final float     PCTSAT_MX = FLOATMAX;
    public static final float     PCTSIL_MN = FLOATMIN;
    public static final float     PCTSIL_MX = FLOATMAX;
    public static final int       PERMTY_MN = INTMIN;
    public static final int       PERMTY_MX = INTMAX;
    public static final float     PORSTY_MN = FLOATMIN;
    public static final float     PORSTY_MX = FLOATMAX;
    public static final float     DWF_MN = FLOATMIN;
    public static final float     DWF_MX = FLOATMAX;
    public static final float     COD_MN = FLOATMIN;
    public static final float     COD_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception deviceCodeOutOfBoundsException =
        new Exception ("'deviceCode' out of bounds: " +
            DEVICE_CODE_MN + " - " + DEVICE_CODE_MX);
    Exception methodCodeOutOfBoundsException =
        new Exception ("'methodCode' out of bounds: " +
            METHOD_CODE_MN + " - " + METHOD_CODE_MX);
    Exception standardCodeOutOfBoundsException =
        new Exception ("'standardCode' out of bounds: " +
            STANDARD_CODE_MN + " - " + STANDARD_CODE_MX);
    Exception spldattimException =
        new Exception ("'spldattim' is null");
    Exception spldattimOutOfBoundsException =
        new Exception ("'spldattim' out of bounds: " +
            SPLDATTIM_MN + " - " + SPLDATTIM_MX);
    Exception spldepOutOfBoundsException =
        new Exception ("'spldep' out of bounds: " +
            SPLDEP_MN + " - " + SPLDEP_MX);
    Exception spldisOutOfBoundsException =
        new Exception ("'spldis' out of bounds: " +
            SPLDIS_MN + " - " + SPLDIS_MX);
    Exception splvolOutOfBoundsException =
        new Exception ("'splvol' out of bounds: " +
            SPLVOL_MN + " - " + SPLVOL_MX);
    Exception sievszOutOfBoundsException =
        new Exception ("'sievsz' out of bounds: " +
            SIEVSZ_MN + " - " + SIEVSZ_MX);
    Exception kurtOutOfBoundsException =
        new Exception ("'kurt' out of bounds: " +
            KURT_MN + " - " + KURT_MX);
    Exception skewOutOfBoundsException =
        new Exception ("'skew' out of bounds: " +
            SKEW_MN + " - " + SKEW_MX);
    Exception meanpzOutOfBoundsException =
        new Exception ("'meanpz' out of bounds: " +
            MEANPZ_MN + " - " + MEANPZ_MX);
    Exception medipzOutOfBoundsException =
        new Exception ("'medipz' out of bounds: " +
            MEDIPZ_MN + " - " + MEDIPZ_MX);
    Exception pctsatOutOfBoundsException =
        new Exception ("'pctsat' out of bounds: " +
            PCTSAT_MN + " - " + PCTSAT_MX);
    Exception pctsilOutOfBoundsException =
        new Exception ("'pctsil' out of bounds: " +
            PCTSIL_MN + " - " + PCTSIL_MX);
    Exception permtyOutOfBoundsException =
        new Exception ("'permty' out of bounds: " +
            PERMTY_MN + " - " + PERMTY_MX);
    Exception porstyOutOfBoundsException =
        new Exception ("'porsty' out of bounds: " +
            PORSTY_MN + " - " + PORSTY_MX);
    Exception dwfOutOfBoundsException =
        new Exception ("'dwf' out of bounds: " +
            DWF_MN + " - " + DWF_MX);
    Exception codOutOfBoundsException =
        new Exception ("'cod' out of bounds: " +
            COD_MN + " - " + COD_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnSedphy() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnSedphy constructor 1"); // debug
    } // MrnSedphy constructor

    /**
     * Instantiate a MrnSedphy object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MrnSedphy(
            int code) {
        this();
        setCode         (code        );
        if (dbg) System.out.println ("<br>in MrnSedphy constructor 2"); // debug
    } // MrnSedphy constructor

    /**
     * Instantiate a MrnSedphy object and initialize the instance variables.
     * @param code          The code         (int)
     * @param stationId     The stationId    (String)
     * @param deviceCode    The deviceCode   (int)
     * @param methodCode    The methodCode   (int)
     * @param standardCode  The standardCode (int)
     * @param subdes        The subdes       (String)
     * @param spldattim     The spldattim    (java.util.Date)
     * @param spldep        The spldep       (float)
     * @param spldis        The spldis       (int)
     * @param splvol        The splvol       (float)
     * @param sievsz        The sievsz       (float)
     * @param kurt          The kurt         (float)
     * @param skew          The skew         (float)
     * @param meanpz        The meanpz       (int)
     * @param medipz        The medipz       (int)
     * @param pctsat        The pctsat       (float)
     * @param pctsil        The pctsil       (float)
     * @param permty        The permty       (int)
     * @param porsty        The porsty       (float)
     * @param dwf           The dwf          (float)
     * @param cod           The cod          (float)
     */
    public MrnSedphy(
            int            code,
            String         stationId,
            int            deviceCode,
            int            methodCode,
            int            standardCode,
            String         subdes,
            java.util.Date spldattim,
            float          spldep,
            int            spldis,
            float          splvol,
            float          sievsz,
            float          kurt,
            float          skew,
            int            meanpz,
            int            medipz,
            float          pctsat,
            float          pctsil,
            int            permty,
            float          porsty,
            float          dwf,
            float          cod) {
        this();
        setCode         (code        );
        setStationId    (stationId   );
        setDeviceCode   (deviceCode  );
        setMethodCode   (methodCode  );
        setStandardCode (standardCode);
        setSubdes       (subdes      );
        setSpldattim    (spldattim   );
        setSpldep       (spldep      );
        setSpldis       (spldis      );
        setSplvol       (splvol      );
        setSievsz       (sievsz      );
        setKurt         (kurt        );
        setSkew         (skew        );
        setMeanpz       (meanpz      );
        setMedipz       (medipz      );
        setPctsat       (pctsat      );
        setPctsil       (pctsil      );
        setPermty       (permty      );
        setPorsty       (porsty      );
        setDwf          (dwf         );
        setCod          (cod         );
        if (dbg) System.out.println ("<br>in MrnSedphy constructor 3"); // debug
    } // MrnSedphy constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode         (INTNULL  );
        setStationId    (CHARNULL );
        setDeviceCode   (INTNULL  );
        setMethodCode   (INTNULL  );
        setStandardCode (INTNULL  );
        setSubdes       (CHARNULL );
        setSpldattim    (DATENULL );
        setSpldep       (FLOATNULL);
        setSpldis       (INTNULL  );
        setSplvol       (FLOATNULL);
        setSievsz       (FLOATNULL);
        setKurt         (FLOATNULL);
        setSkew         (FLOATNULL);
        setMeanpz       (INTNULL  );
        setMedipz       (INTNULL  );
        setPctsat       (FLOATNULL);
        setPctsil       (FLOATNULL);
        setPermty       (INTNULL  );
        setPorsty       (FLOATNULL);
        setDwf          (FLOATNULL);
        setCod          (FLOATNULL);
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'code' class variable
     * @param  code (int)
     */
    public int setCode(int code) {
        try {
            if ( ((code == INTNULL) ||
                  (code == INTNULL2)) ||
                !((code < CODE_MN) ||
                  (code > CODE_MX)) ) {
                this.code = code;
                codeError = ERROR_NORMAL;
            } else {
                throw codeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (Integer)
     */
    public int setCode(Integer code) {
        try {
            setCode(code.intValue());
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (Float)
     */
    public int setCode(Float code) {
        try {
            if (code.floatValue() == FLOATNULL) {
                setCode(INTNULL);
            } else {
                setCode(code.intValue());
            } // if (code.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Set the 'code' class variable
     * @param  code (String)
     */
    public int setCode(String code) {
        try {
            setCode(new Integer(code).intValue());
        } catch (Exception e) {
            setCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return codeError;
    } // method setCode

    /**
     * Called when an exception has occured
     * @param  code (String)
     */
    private void setCodeError (int code, Exception e, int error) {
        this.code = code;
        codeErrorMessage = e.toString();
        codeError = error;
    } // method setCodeError


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
     * Set the 'deviceCode' class variable
     * @param  deviceCode (int)
     */
    public int setDeviceCode(int deviceCode) {
        try {
            if ( ((deviceCode == INTNULL) ||
                  (deviceCode == INTNULL2)) ||
                !((deviceCode < DEVICE_CODE_MN) ||
                  (deviceCode > DEVICE_CODE_MX)) ) {
                this.deviceCode = deviceCode;
                deviceCodeError = ERROR_NORMAL;
            } else {
                throw deviceCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (Integer)
     */
    public int setDeviceCode(Integer deviceCode) {
        try {
            setDeviceCode(deviceCode.intValue());
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (Float)
     */
    public int setDeviceCode(Float deviceCode) {
        try {
            if (deviceCode.floatValue() == FLOATNULL) {
                setDeviceCode(INTNULL);
            } else {
                setDeviceCode(deviceCode.intValue());
            } // if (deviceCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Set the 'deviceCode' class variable
     * @param  deviceCode (String)
     */
    public int setDeviceCode(String deviceCode) {
        try {
            setDeviceCode(new Integer(deviceCode).intValue());
        } catch (Exception e) {
            setDeviceCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return deviceCodeError;
    } // method setDeviceCode

    /**
     * Called when an exception has occured
     * @param  deviceCode (String)
     */
    private void setDeviceCodeError (int deviceCode, Exception e, int error) {
        this.deviceCode = deviceCode;
        deviceCodeErrorMessage = e.toString();
        deviceCodeError = error;
    } // method setDeviceCodeError


    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (int)
     */
    public int setMethodCode(int methodCode) {
        try {
            if ( ((methodCode == INTNULL) ||
                  (methodCode == INTNULL2)) ||
                !((methodCode < METHOD_CODE_MN) ||
                  (methodCode > METHOD_CODE_MX)) ) {
                this.methodCode = methodCode;
                methodCodeError = ERROR_NORMAL;
            } else {
                throw methodCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (Integer)
     */
    public int setMethodCode(Integer methodCode) {
        try {
            setMethodCode(methodCode.intValue());
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (Float)
     */
    public int setMethodCode(Float methodCode) {
        try {
            if (methodCode.floatValue() == FLOATNULL) {
                setMethodCode(INTNULL);
            } else {
                setMethodCode(methodCode.intValue());
            } // if (methodCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Set the 'methodCode' class variable
     * @param  methodCode (String)
     */
    public int setMethodCode(String methodCode) {
        try {
            setMethodCode(new Integer(methodCode).intValue());
        } catch (Exception e) {
            setMethodCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return methodCodeError;
    } // method setMethodCode

    /**
     * Called when an exception has occured
     * @param  methodCode (String)
     */
    private void setMethodCodeError (int methodCode, Exception e, int error) {
        this.methodCode = methodCode;
        methodCodeErrorMessage = e.toString();
        methodCodeError = error;
    } // method setMethodCodeError


    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (int)
     */
    public int setStandardCode(int standardCode) {
        try {
            if ( ((standardCode == INTNULL) ||
                  (standardCode == INTNULL2)) ||
                !((standardCode < STANDARD_CODE_MN) ||
                  (standardCode > STANDARD_CODE_MX)) ) {
                this.standardCode = standardCode;
                standardCodeError = ERROR_NORMAL;
            } else {
                throw standardCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (Integer)
     */
    public int setStandardCode(Integer standardCode) {
        try {
            setStandardCode(standardCode.intValue());
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (Float)
     */
    public int setStandardCode(Float standardCode) {
        try {
            if (standardCode.floatValue() == FLOATNULL) {
                setStandardCode(INTNULL);
            } else {
                setStandardCode(standardCode.intValue());
            } // if (standardCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Set the 'standardCode' class variable
     * @param  standardCode (String)
     */
    public int setStandardCode(String standardCode) {
        try {
            setStandardCode(new Integer(standardCode).intValue());
        } catch (Exception e) {
            setStandardCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return standardCodeError;
    } // method setStandardCode

    /**
     * Called when an exception has occured
     * @param  standardCode (String)
     */
    private void setStandardCodeError (int standardCode, Exception e, int error) {
        this.standardCode = standardCode;
        standardCodeErrorMessage = e.toString();
        standardCodeError = error;
    } // method setStandardCodeError


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
     * Set the 'spldattim' class variable
     * @param  spldattim (Timestamp)
     */
    public int setSpldattim(Timestamp spldattim) {
        try {
            if (spldattim == null) { this.spldattim = DATENULL; }
            if ( (spldattim.equals(DATENULL) ||
                  spldattim.equals(DATENULL2)) ||
                !(spldattim.before(SPLDATTIM_MN) ||
                  spldattim.after(SPLDATTIM_MX)) ) {
                this.spldattim = spldattim;
                spldattimError = ERROR_NORMAL;
            } else {
                throw spldattimOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpldattimError(DATENULL, e, ERROR_LOCAL);
        } // try
        return spldattimError;
    } // method setSpldattim

    /**
     * Set the 'spldattim' class variable
     * @param  spldattim (java.util.Date)
     */
    public int setSpldattim(java.util.Date spldattim) {
        try {
            setSpldattim(new Timestamp(spldattim.getTime()));
        } catch (Exception e) {
            setSpldattimError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return spldattimError;
    } // method setSpldattim

    /**
     * Set the 'spldattim' class variable
     * @param  spldattim (String)
     */
    public int setSpldattim(String spldattim) {
        try {
            int len = spldattim.length();
            switch (len) {
            // date &/or times
                case 4: spldattim += "-01";
                case 7: spldattim += "-01";
                case 10: spldattim += " 00";
                case 13: spldattim += ":00";
                case 16: spldattim += ":00"; break;
                // times only
                case 5: spldattim = "1800-01-01 " + spldattim + ":00"; break;
                case 8: spldattim = "1800-01-01 " + spldattim; break;
            } // switch
            if (dbg) System.out.println ("spldattim = " + spldattim);
            setSpldattim(Timestamp.valueOf(spldattim));
        } catch (Exception e) {
            setSpldattimError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return spldattimError;
    } // method setSpldattim

    /**
     * Called when an exception has occured
     * @param  spldattim (String)
     */
    private void setSpldattimError (Timestamp spldattim, Exception e, int error) {
        this.spldattim = spldattim;
        spldattimErrorMessage = e.toString();
        spldattimError = error;
    } // method setSpldattimError


    /**
     * Set the 'spldep' class variable
     * @param  spldep (float)
     */
    public int setSpldep(float spldep) {
        try {
            if ( ((spldep == FLOATNULL) ||
                  (spldep == FLOATNULL2)) ||
                !((spldep < SPLDEP_MN) ||
                  (spldep > SPLDEP_MX)) ) {
                this.spldep = spldep;
                spldepError = ERROR_NORMAL;
            } else {
                throw spldepOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (Integer)
     */
    public int setSpldep(Integer spldep) {
        try {
            setSpldep(spldep.floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (Float)
     */
    public int setSpldep(Float spldep) {
        try {
            setSpldep(spldep.floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Set the 'spldep' class variable
     * @param  spldep (String)
     */
    public int setSpldep(String spldep) {
        try {
            setSpldep(new Float(spldep).floatValue());
        } catch (Exception e) {
            setSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return spldepError;
    } // method setSpldep

    /**
     * Called when an exception has occured
     * @param  spldep (String)
     */
    private void setSpldepError (float spldep, Exception e, int error) {
        this.spldep = spldep;
        spldepErrorMessage = e.toString();
        spldepError = error;
    } // method setSpldepError


    /**
     * Set the 'spldis' class variable
     * @param  spldis (int)
     */
    public int setSpldis(int spldis) {
        try {
            if ( ((spldis == INTNULL) ||
                  (spldis == INTNULL2)) ||
                !((spldis < SPLDIS_MN) ||
                  (spldis > SPLDIS_MX)) ) {
                this.spldis = spldis;
                spldisError = ERROR_NORMAL;
            } else {
                throw spldisOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpldisError(INTNULL, e, ERROR_LOCAL);
        } // try
        return spldisError;
    } // method setSpldis

    /**
     * Set the 'spldis' class variable
     * @param  spldis (Integer)
     */
    public int setSpldis(Integer spldis) {
        try {
            setSpldis(spldis.intValue());
        } catch (Exception e) {
            setSpldisError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return spldisError;
    } // method setSpldis

    /**
     * Set the 'spldis' class variable
     * @param  spldis (Float)
     */
    public int setSpldis(Float spldis) {
        try {
            if (spldis.floatValue() == FLOATNULL) {
                setSpldis(INTNULL);
            } else {
                setSpldis(spldis.intValue());
            } // if (spldis.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSpldisError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return spldisError;
    } // method setSpldis

    /**
     * Set the 'spldis' class variable
     * @param  spldis (String)
     */
    public int setSpldis(String spldis) {
        try {
            setSpldis(new Integer(spldis).intValue());
        } catch (Exception e) {
            setSpldisError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return spldisError;
    } // method setSpldis

    /**
     * Called when an exception has occured
     * @param  spldis (String)
     */
    private void setSpldisError (int spldis, Exception e, int error) {
        this.spldis = spldis;
        spldisErrorMessage = e.toString();
        spldisError = error;
    } // method setSpldisError


    /**
     * Set the 'splvol' class variable
     * @param  splvol (float)
     */
    public int setSplvol(float splvol) {
        try {
            if ( ((splvol == FLOATNULL) ||
                  (splvol == FLOATNULL2)) ||
                !((splvol < SPLVOL_MN) ||
                  (splvol > SPLVOL_MX)) ) {
                this.splvol = splvol;
                splvolError = ERROR_NORMAL;
            } else {
                throw splvolOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSplvolError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return splvolError;
    } // method setSplvol

    /**
     * Set the 'splvol' class variable
     * @param  splvol (Integer)
     */
    public int setSplvol(Integer splvol) {
        try {
            setSplvol(splvol.floatValue());
        } catch (Exception e) {
            setSplvolError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return splvolError;
    } // method setSplvol

    /**
     * Set the 'splvol' class variable
     * @param  splvol (Float)
     */
    public int setSplvol(Float splvol) {
        try {
            setSplvol(splvol.floatValue());
        } catch (Exception e) {
            setSplvolError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return splvolError;
    } // method setSplvol

    /**
     * Set the 'splvol' class variable
     * @param  splvol (String)
     */
    public int setSplvol(String splvol) {
        try {
            setSplvol(new Float(splvol).floatValue());
        } catch (Exception e) {
            setSplvolError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return splvolError;
    } // method setSplvol

    /**
     * Called when an exception has occured
     * @param  splvol (String)
     */
    private void setSplvolError (float splvol, Exception e, int error) {
        this.splvol = splvol;
        splvolErrorMessage = e.toString();
        splvolError = error;
    } // method setSplvolError


    /**
     * Set the 'sievsz' class variable
     * @param  sievsz (float)
     */
    public int setSievsz(float sievsz) {
        try {
            if ( ((sievsz == FLOATNULL) ||
                  (sievsz == FLOATNULL2)) ||
                !((sievsz < SIEVSZ_MN) ||
                  (sievsz > SIEVSZ_MX)) ) {
                this.sievsz = sievsz;
                sievszError = ERROR_NORMAL;
            } else {
                throw sievszOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSievszError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return sievszError;
    } // method setSievsz

    /**
     * Set the 'sievsz' class variable
     * @param  sievsz (Integer)
     */
    public int setSievsz(Integer sievsz) {
        try {
            setSievsz(sievsz.floatValue());
        } catch (Exception e) {
            setSievszError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sievszError;
    } // method setSievsz

    /**
     * Set the 'sievsz' class variable
     * @param  sievsz (Float)
     */
    public int setSievsz(Float sievsz) {
        try {
            setSievsz(sievsz.floatValue());
        } catch (Exception e) {
            setSievszError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sievszError;
    } // method setSievsz

    /**
     * Set the 'sievsz' class variable
     * @param  sievsz (String)
     */
    public int setSievsz(String sievsz) {
        try {
            setSievsz(new Float(sievsz).floatValue());
        } catch (Exception e) {
            setSievszError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return sievszError;
    } // method setSievsz

    /**
     * Called when an exception has occured
     * @param  sievsz (String)
     */
    private void setSievszError (float sievsz, Exception e, int error) {
        this.sievsz = sievsz;
        sievszErrorMessage = e.toString();
        sievszError = error;
    } // method setSievszError


    /**
     * Set the 'kurt' class variable
     * @param  kurt (float)
     */
    public int setKurt(float kurt) {
        try {
            if ( ((kurt == FLOATNULL) ||
                  (kurt == FLOATNULL2)) ||
                !((kurt < KURT_MN) ||
                  (kurt > KURT_MX)) ) {
                this.kurt = kurt;
                kurtError = ERROR_NORMAL;
            } else {
                throw kurtOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setKurtError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return kurtError;
    } // method setKurt

    /**
     * Set the 'kurt' class variable
     * @param  kurt (Integer)
     */
    public int setKurt(Integer kurt) {
        try {
            setKurt(kurt.floatValue());
        } catch (Exception e) {
            setKurtError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return kurtError;
    } // method setKurt

    /**
     * Set the 'kurt' class variable
     * @param  kurt (Float)
     */
    public int setKurt(Float kurt) {
        try {
            setKurt(kurt.floatValue());
        } catch (Exception e) {
            setKurtError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return kurtError;
    } // method setKurt

    /**
     * Set the 'kurt' class variable
     * @param  kurt (String)
     */
    public int setKurt(String kurt) {
        try {
            setKurt(new Float(kurt).floatValue());
        } catch (Exception e) {
            setKurtError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return kurtError;
    } // method setKurt

    /**
     * Called when an exception has occured
     * @param  kurt (String)
     */
    private void setKurtError (float kurt, Exception e, int error) {
        this.kurt = kurt;
        kurtErrorMessage = e.toString();
        kurtError = error;
    } // method setKurtError


    /**
     * Set the 'skew' class variable
     * @param  skew (float)
     */
    public int setSkew(float skew) {
        try {
            if ( ((skew == FLOATNULL) ||
                  (skew == FLOATNULL2)) ||
                !((skew < SKEW_MN) ||
                  (skew > SKEW_MX)) ) {
                this.skew = skew;
                skewError = ERROR_NORMAL;
            } else {
                throw skewOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSkewError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return skewError;
    } // method setSkew

    /**
     * Set the 'skew' class variable
     * @param  skew (Integer)
     */
    public int setSkew(Integer skew) {
        try {
            setSkew(skew.floatValue());
        } catch (Exception e) {
            setSkewError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return skewError;
    } // method setSkew

    /**
     * Set the 'skew' class variable
     * @param  skew (Float)
     */
    public int setSkew(Float skew) {
        try {
            setSkew(skew.floatValue());
        } catch (Exception e) {
            setSkewError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return skewError;
    } // method setSkew

    /**
     * Set the 'skew' class variable
     * @param  skew (String)
     */
    public int setSkew(String skew) {
        try {
            setSkew(new Float(skew).floatValue());
        } catch (Exception e) {
            setSkewError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return skewError;
    } // method setSkew

    /**
     * Called when an exception has occured
     * @param  skew (String)
     */
    private void setSkewError (float skew, Exception e, int error) {
        this.skew = skew;
        skewErrorMessage = e.toString();
        skewError = error;
    } // method setSkewError


    /**
     * Set the 'meanpz' class variable
     * @param  meanpz (int)
     */
    public int setMeanpz(int meanpz) {
        try {
            if ( ((meanpz == INTNULL) ||
                  (meanpz == INTNULL2)) ||
                !((meanpz < MEANPZ_MN) ||
                  (meanpz > MEANPZ_MX)) ) {
                this.meanpz = meanpz;
                meanpzError = ERROR_NORMAL;
            } else {
                throw meanpzOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMeanpzError(INTNULL, e, ERROR_LOCAL);
        } // try
        return meanpzError;
    } // method setMeanpz

    /**
     * Set the 'meanpz' class variable
     * @param  meanpz (Integer)
     */
    public int setMeanpz(Integer meanpz) {
        try {
            setMeanpz(meanpz.intValue());
        } catch (Exception e) {
            setMeanpzError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return meanpzError;
    } // method setMeanpz

    /**
     * Set the 'meanpz' class variable
     * @param  meanpz (Float)
     */
    public int setMeanpz(Float meanpz) {
        try {
            if (meanpz.floatValue() == FLOATNULL) {
                setMeanpz(INTNULL);
            } else {
                setMeanpz(meanpz.intValue());
            } // if (meanpz.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMeanpzError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return meanpzError;
    } // method setMeanpz

    /**
     * Set the 'meanpz' class variable
     * @param  meanpz (String)
     */
    public int setMeanpz(String meanpz) {
        try {
            setMeanpz(new Integer(meanpz).intValue());
        } catch (Exception e) {
            setMeanpzError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return meanpzError;
    } // method setMeanpz

    /**
     * Called when an exception has occured
     * @param  meanpz (String)
     */
    private void setMeanpzError (int meanpz, Exception e, int error) {
        this.meanpz = meanpz;
        meanpzErrorMessage = e.toString();
        meanpzError = error;
    } // method setMeanpzError


    /**
     * Set the 'medipz' class variable
     * @param  medipz (int)
     */
    public int setMedipz(int medipz) {
        try {
            if ( ((medipz == INTNULL) ||
                  (medipz == INTNULL2)) ||
                !((medipz < MEDIPZ_MN) ||
                  (medipz > MEDIPZ_MX)) ) {
                this.medipz = medipz;
                medipzError = ERROR_NORMAL;
            } else {
                throw medipzOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMedipzError(INTNULL, e, ERROR_LOCAL);
        } // try
        return medipzError;
    } // method setMedipz

    /**
     * Set the 'medipz' class variable
     * @param  medipz (Integer)
     */
    public int setMedipz(Integer medipz) {
        try {
            setMedipz(medipz.intValue());
        } catch (Exception e) {
            setMedipzError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return medipzError;
    } // method setMedipz

    /**
     * Set the 'medipz' class variable
     * @param  medipz (Float)
     */
    public int setMedipz(Float medipz) {
        try {
            if (medipz.floatValue() == FLOATNULL) {
                setMedipz(INTNULL);
            } else {
                setMedipz(medipz.intValue());
            } // if (medipz.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMedipzError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return medipzError;
    } // method setMedipz

    /**
     * Set the 'medipz' class variable
     * @param  medipz (String)
     */
    public int setMedipz(String medipz) {
        try {
            setMedipz(new Integer(medipz).intValue());
        } catch (Exception e) {
            setMedipzError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return medipzError;
    } // method setMedipz

    /**
     * Called when an exception has occured
     * @param  medipz (String)
     */
    private void setMedipzError (int medipz, Exception e, int error) {
        this.medipz = medipz;
        medipzErrorMessage = e.toString();
        medipzError = error;
    } // method setMedipzError


    /**
     * Set the 'pctsat' class variable
     * @param  pctsat (float)
     */
    public int setPctsat(float pctsat) {
        try {
            if ( ((pctsat == FLOATNULL) ||
                  (pctsat == FLOATNULL2)) ||
                !((pctsat < PCTSAT_MN) ||
                  (pctsat > PCTSAT_MX)) ) {
                this.pctsat = pctsat;
                pctsatError = ERROR_NORMAL;
            } else {
                throw pctsatOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPctsatError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return pctsatError;
    } // method setPctsat

    /**
     * Set the 'pctsat' class variable
     * @param  pctsat (Integer)
     */
    public int setPctsat(Integer pctsat) {
        try {
            setPctsat(pctsat.floatValue());
        } catch (Exception e) {
            setPctsatError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pctsatError;
    } // method setPctsat

    /**
     * Set the 'pctsat' class variable
     * @param  pctsat (Float)
     */
    public int setPctsat(Float pctsat) {
        try {
            setPctsat(pctsat.floatValue());
        } catch (Exception e) {
            setPctsatError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pctsatError;
    } // method setPctsat

    /**
     * Set the 'pctsat' class variable
     * @param  pctsat (String)
     */
    public int setPctsat(String pctsat) {
        try {
            setPctsat(new Float(pctsat).floatValue());
        } catch (Exception e) {
            setPctsatError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pctsatError;
    } // method setPctsat

    /**
     * Called when an exception has occured
     * @param  pctsat (String)
     */
    private void setPctsatError (float pctsat, Exception e, int error) {
        this.pctsat = pctsat;
        pctsatErrorMessage = e.toString();
        pctsatError = error;
    } // method setPctsatError


    /**
     * Set the 'pctsil' class variable
     * @param  pctsil (float)
     */
    public int setPctsil(float pctsil) {
        try {
            if ( ((pctsil == FLOATNULL) ||
                  (pctsil == FLOATNULL2)) ||
                !((pctsil < PCTSIL_MN) ||
                  (pctsil > PCTSIL_MX)) ) {
                this.pctsil = pctsil;
                pctsilError = ERROR_NORMAL;
            } else {
                throw pctsilOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPctsilError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return pctsilError;
    } // method setPctsil

    /**
     * Set the 'pctsil' class variable
     * @param  pctsil (Integer)
     */
    public int setPctsil(Integer pctsil) {
        try {
            setPctsil(pctsil.floatValue());
        } catch (Exception e) {
            setPctsilError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pctsilError;
    } // method setPctsil

    /**
     * Set the 'pctsil' class variable
     * @param  pctsil (Float)
     */
    public int setPctsil(Float pctsil) {
        try {
            setPctsil(pctsil.floatValue());
        } catch (Exception e) {
            setPctsilError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pctsilError;
    } // method setPctsil

    /**
     * Set the 'pctsil' class variable
     * @param  pctsil (String)
     */
    public int setPctsil(String pctsil) {
        try {
            setPctsil(new Float(pctsil).floatValue());
        } catch (Exception e) {
            setPctsilError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pctsilError;
    } // method setPctsil

    /**
     * Called when an exception has occured
     * @param  pctsil (String)
     */
    private void setPctsilError (float pctsil, Exception e, int error) {
        this.pctsil = pctsil;
        pctsilErrorMessage = e.toString();
        pctsilError = error;
    } // method setPctsilError


    /**
     * Set the 'permty' class variable
     * @param  permty (int)
     */
    public int setPermty(int permty) {
        try {
            if ( ((permty == INTNULL) ||
                  (permty == INTNULL2)) ||
                !((permty < PERMTY_MN) ||
                  (permty > PERMTY_MX)) ) {
                this.permty = permty;
                permtyError = ERROR_NORMAL;
            } else {
                throw permtyOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPermtyError(INTNULL, e, ERROR_LOCAL);
        } // try
        return permtyError;
    } // method setPermty

    /**
     * Set the 'permty' class variable
     * @param  permty (Integer)
     */
    public int setPermty(Integer permty) {
        try {
            setPermty(permty.intValue());
        } catch (Exception e) {
            setPermtyError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return permtyError;
    } // method setPermty

    /**
     * Set the 'permty' class variable
     * @param  permty (Float)
     */
    public int setPermty(Float permty) {
        try {
            if (permty.floatValue() == FLOATNULL) {
                setPermty(INTNULL);
            } else {
                setPermty(permty.intValue());
            } // if (permty.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPermtyError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return permtyError;
    } // method setPermty

    /**
     * Set the 'permty' class variable
     * @param  permty (String)
     */
    public int setPermty(String permty) {
        try {
            setPermty(new Integer(permty).intValue());
        } catch (Exception e) {
            setPermtyError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return permtyError;
    } // method setPermty

    /**
     * Called when an exception has occured
     * @param  permty (String)
     */
    private void setPermtyError (int permty, Exception e, int error) {
        this.permty = permty;
        permtyErrorMessage = e.toString();
        permtyError = error;
    } // method setPermtyError


    /**
     * Set the 'porsty' class variable
     * @param  porsty (float)
     */
    public int setPorsty(float porsty) {
        try {
            if ( ((porsty == FLOATNULL) ||
                  (porsty == FLOATNULL2)) ||
                !((porsty < PORSTY_MN) ||
                  (porsty > PORSTY_MX)) ) {
                this.porsty = porsty;
                porstyError = ERROR_NORMAL;
            } else {
                throw porstyOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPorstyError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return porstyError;
    } // method setPorsty

    /**
     * Set the 'porsty' class variable
     * @param  porsty (Integer)
     */
    public int setPorsty(Integer porsty) {
        try {
            setPorsty(porsty.floatValue());
        } catch (Exception e) {
            setPorstyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return porstyError;
    } // method setPorsty

    /**
     * Set the 'porsty' class variable
     * @param  porsty (Float)
     */
    public int setPorsty(Float porsty) {
        try {
            setPorsty(porsty.floatValue());
        } catch (Exception e) {
            setPorstyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return porstyError;
    } // method setPorsty

    /**
     * Set the 'porsty' class variable
     * @param  porsty (String)
     */
    public int setPorsty(String porsty) {
        try {
            setPorsty(new Float(porsty).floatValue());
        } catch (Exception e) {
            setPorstyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return porstyError;
    } // method setPorsty

    /**
     * Called when an exception has occured
     * @param  porsty (String)
     */
    private void setPorstyError (float porsty, Exception e, int error) {
        this.porsty = porsty;
        porstyErrorMessage = e.toString();
        porstyError = error;
    } // method setPorstyError


    /**
     * Set the 'dwf' class variable
     * @param  dwf (float)
     */
    public int setDwf(float dwf) {
        try {
            if ( ((dwf == FLOATNULL) ||
                  (dwf == FLOATNULL2)) ||
                !((dwf < DWF_MN) ||
                  (dwf > DWF_MX)) ) {
                this.dwf = dwf;
                dwfError = ERROR_NORMAL;
            } else {
                throw dwfOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDwfError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return dwfError;
    } // method setDwf

    /**
     * Set the 'dwf' class variable
     * @param  dwf (Integer)
     */
    public int setDwf(Integer dwf) {
        try {
            setDwf(dwf.floatValue());
        } catch (Exception e) {
            setDwfError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dwfError;
    } // method setDwf

    /**
     * Set the 'dwf' class variable
     * @param  dwf (Float)
     */
    public int setDwf(Float dwf) {
        try {
            setDwf(dwf.floatValue());
        } catch (Exception e) {
            setDwfError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dwfError;
    } // method setDwf

    /**
     * Set the 'dwf' class variable
     * @param  dwf (String)
     */
    public int setDwf(String dwf) {
        try {
            setDwf(new Float(dwf).floatValue());
        } catch (Exception e) {
            setDwfError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return dwfError;
    } // method setDwf

    /**
     * Called when an exception has occured
     * @param  dwf (String)
     */
    private void setDwfError (float dwf, Exception e, int error) {
        this.dwf = dwf;
        dwfErrorMessage = e.toString();
        dwfError = error;
    } // method setDwfError


    /**
     * Set the 'cod' class variable
     * @param  cod (float)
     */
    public int setCod(float cod) {
        try {
            if ( ((cod == FLOATNULL) ||
                  (cod == FLOATNULL2)) ||
                !((cod < COD_MN) ||
                  (cod > COD_MX)) ) {
                this.cod = cod;
                codError = ERROR_NORMAL;
            } else {
                throw codOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCodError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return codError;
    } // method setCod

    /**
     * Set the 'cod' class variable
     * @param  cod (Integer)
     */
    public int setCod(Integer cod) {
        try {
            setCod(cod.floatValue());
        } catch (Exception e) {
            setCodError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return codError;
    } // method setCod

    /**
     * Set the 'cod' class variable
     * @param  cod (Float)
     */
    public int setCod(Float cod) {
        try {
            setCod(cod.floatValue());
        } catch (Exception e) {
            setCodError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return codError;
    } // method setCod

    /**
     * Set the 'cod' class variable
     * @param  cod (String)
     */
    public int setCod(String cod) {
        try {
            setCod(new Float(cod).floatValue());
        } catch (Exception e) {
            setCodError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return codError;
    } // method setCod

    /**
     * Called when an exception has occured
     * @param  cod (String)
     */
    private void setCodError (float cod, Exception e, int error) {
        this.cod = cod;
        codErrorMessage = e.toString();
        codError = error;
    } // method setCodError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'code' class variable
     * @return code (int)
     */
    public int getCode() {
        return code;
    } // method getCode

    /**
     * Return the 'code' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCode methods
     * @return code (String)
     */
    public String getCode(String s) {
        return ((code != INTNULL) ? new Integer(code).toString() : "");
    } // method getCode


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
     * Return the 'deviceCode' class variable
     * @return deviceCode (int)
     */
    public int getDeviceCode() {
        return deviceCode;
    } // method getDeviceCode

    /**
     * Return the 'deviceCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDeviceCode methods
     * @return deviceCode (String)
     */
    public String getDeviceCode(String s) {
        return ((deviceCode != INTNULL) ? new Integer(deviceCode).toString() : "");
    } // method getDeviceCode


    /**
     * Return the 'methodCode' class variable
     * @return methodCode (int)
     */
    public int getMethodCode() {
        return methodCode;
    } // method getMethodCode

    /**
     * Return the 'methodCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMethodCode methods
     * @return methodCode (String)
     */
    public String getMethodCode(String s) {
        return ((methodCode != INTNULL) ? new Integer(methodCode).toString() : "");
    } // method getMethodCode


    /**
     * Return the 'standardCode' class variable
     * @return standardCode (int)
     */
    public int getStandardCode() {
        return standardCode;
    } // method getStandardCode

    /**
     * Return the 'standardCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStandardCode methods
     * @return standardCode (String)
     */
    public String getStandardCode(String s) {
        return ((standardCode != INTNULL) ? new Integer(standardCode).toString() : "");
    } // method getStandardCode


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
     * Return the 'spldattim' class variable
     * @return spldattim (Timestamp)
     */
    public Timestamp getSpldattim() {
        return spldattim;
    } // method getSpldattim

    /**
     * Return the 'spldattim' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpldattim methods
     * @return spldattim (String)
     */
    public String getSpldattim(String s) {
        if (spldattim.equals(DATENULL)) {
            return ("");
        } else {
            String spldattimStr = spldattim.toString();
            return spldattimStr.substring(0,spldattimStr.indexOf('.'));
        } // if
    } // method getSpldattim


    /**
     * Return the 'spldep' class variable
     * @return spldep (float)
     */
    public float getSpldep() {
        return spldep;
    } // method getSpldep

    /**
     * Return the 'spldep' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpldep methods
     * @return spldep (String)
     */
    public String getSpldep(String s) {
        return ((spldep != FLOATNULL) ? new Float(spldep).toString() : "");
    } // method getSpldep


    /**
     * Return the 'spldis' class variable
     * @return spldis (int)
     */
    public int getSpldis() {
        return spldis;
    } // method getSpldis

    /**
     * Return the 'spldis' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpldis methods
     * @return spldis (String)
     */
    public String getSpldis(String s) {
        return ((spldis != INTNULL) ? new Integer(spldis).toString() : "");
    } // method getSpldis


    /**
     * Return the 'splvol' class variable
     * @return splvol (float)
     */
    public float getSplvol() {
        return splvol;
    } // method getSplvol

    /**
     * Return the 'splvol' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSplvol methods
     * @return splvol (String)
     */
    public String getSplvol(String s) {
        return ((splvol != FLOATNULL) ? new Float(splvol).toString() : "");
    } // method getSplvol


    /**
     * Return the 'sievsz' class variable
     * @return sievsz (float)
     */
    public float getSievsz() {
        return sievsz;
    } // method getSievsz

    /**
     * Return the 'sievsz' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSievsz methods
     * @return sievsz (String)
     */
    public String getSievsz(String s) {
        return ((sievsz != FLOATNULL) ? new Float(sievsz).toString() : "");
    } // method getSievsz


    /**
     * Return the 'kurt' class variable
     * @return kurt (float)
     */
    public float getKurt() {
        return kurt;
    } // method getKurt

    /**
     * Return the 'kurt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getKurt methods
     * @return kurt (String)
     */
    public String getKurt(String s) {
        return ((kurt != FLOATNULL) ? new Float(kurt).toString() : "");
    } // method getKurt


    /**
     * Return the 'skew' class variable
     * @return skew (float)
     */
    public float getSkew() {
        return skew;
    } // method getSkew

    /**
     * Return the 'skew' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSkew methods
     * @return skew (String)
     */
    public String getSkew(String s) {
        return ((skew != FLOATNULL) ? new Float(skew).toString() : "");
    } // method getSkew


    /**
     * Return the 'meanpz' class variable
     * @return meanpz (int)
     */
    public int getMeanpz() {
        return meanpz;
    } // method getMeanpz

    /**
     * Return the 'meanpz' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMeanpz methods
     * @return meanpz (String)
     */
    public String getMeanpz(String s) {
        return ((meanpz != INTNULL) ? new Integer(meanpz).toString() : "");
    } // method getMeanpz


    /**
     * Return the 'medipz' class variable
     * @return medipz (int)
     */
    public int getMedipz() {
        return medipz;
    } // method getMedipz

    /**
     * Return the 'medipz' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMedipz methods
     * @return medipz (String)
     */
    public String getMedipz(String s) {
        return ((medipz != INTNULL) ? new Integer(medipz).toString() : "");
    } // method getMedipz


    /**
     * Return the 'pctsat' class variable
     * @return pctsat (float)
     */
    public float getPctsat() {
        return pctsat;
    } // method getPctsat

    /**
     * Return the 'pctsat' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPctsat methods
     * @return pctsat (String)
     */
    public String getPctsat(String s) {
        return ((pctsat != FLOATNULL) ? new Float(pctsat).toString() : "");
    } // method getPctsat


    /**
     * Return the 'pctsil' class variable
     * @return pctsil (float)
     */
    public float getPctsil() {
        return pctsil;
    } // method getPctsil

    /**
     * Return the 'pctsil' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPctsil methods
     * @return pctsil (String)
     */
    public String getPctsil(String s) {
        return ((pctsil != FLOATNULL) ? new Float(pctsil).toString() : "");
    } // method getPctsil


    /**
     * Return the 'permty' class variable
     * @return permty (int)
     */
    public int getPermty() {
        return permty;
    } // method getPermty

    /**
     * Return the 'permty' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPermty methods
     * @return permty (String)
     */
    public String getPermty(String s) {
        return ((permty != INTNULL) ? new Integer(permty).toString() : "");
    } // method getPermty


    /**
     * Return the 'porsty' class variable
     * @return porsty (float)
     */
    public float getPorsty() {
        return porsty;
    } // method getPorsty

    /**
     * Return the 'porsty' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPorsty methods
     * @return porsty (String)
     */
    public String getPorsty(String s) {
        return ((porsty != FLOATNULL) ? new Float(porsty).toString() : "");
    } // method getPorsty


    /**
     * Return the 'dwf' class variable
     * @return dwf (float)
     */
    public float getDwf() {
        return dwf;
    } // method getDwf

    /**
     * Return the 'dwf' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDwf methods
     * @return dwf (String)
     */
    public String getDwf(String s) {
        return ((dwf != FLOATNULL) ? new Float(dwf).toString() : "");
    } // method getDwf


    /**
     * Return the 'cod' class variable
     * @return cod (float)
     */
    public float getCod() {
        return cod;
    } // method getCod

    /**
     * Return the 'cod' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCod methods
     * @return cod (String)
     */
    public String getCod(String s) {
        return ((cod != FLOATNULL) ? new Float(cod).toString() : "");
    } // method getCod


    /**
     * Gets the maximum value for the code from the table
     * @return maximum code value (int)
     */
    public int getMaxCode() {
        Vector result = db.select ("max(" + CODE + ")",TABLE,"1=1");
        Vector row = (Vector) result.elementAt(0);
        return (new Integer((String) row.elementAt(0)).intValue());
    } // method getMaxCode


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
        if ((code == INTNULL) &&
            (stationId == CHARNULL) &&
            (deviceCode == INTNULL) &&
            (methodCode == INTNULL) &&
            (standardCode == INTNULL) &&
            (subdes == CHARNULL) &&
            (spldattim.equals(DATENULL)) &&
            (spldep == FLOATNULL) &&
            (spldis == INTNULL) &&
            (splvol == FLOATNULL) &&
            (sievsz == FLOATNULL) &&
            (kurt == FLOATNULL) &&
            (skew == FLOATNULL) &&
            (meanpz == INTNULL) &&
            (medipz == INTNULL) &&
            (pctsat == FLOATNULL) &&
            (pctsil == FLOATNULL) &&
            (permty == INTNULL) &&
            (porsty == FLOATNULL) &&
            (dwf == FLOATNULL) &&
            (cod == FLOATNULL)) {
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
        int sumError = codeError +
            stationIdError +
            deviceCodeError +
            methodCodeError +
            standardCodeError +
            subdesError +
            spldattimError +
            spldepError +
            spldisError +
            splvolError +
            sievszError +
            kurtError +
            skewError +
            meanpzError +
            medipzError +
            pctsatError +
            pctsilError +
            permtyError +
            porstyError +
            dwfError +
            codError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the code instance variable
     * @return errorcode (int)
     */
    public int getCodeError() {
        return codeError;
    } // method getCodeError

    /**
     * Gets the errorMessage for the code instance variable
     * @return errorMessage (String)
     */
    public String getCodeErrorMessage() {
        return codeErrorMessage;
    } // method getCodeErrorMessage


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
     * Gets the errorcode for the deviceCode instance variable
     * @return errorcode (int)
     */
    public int getDeviceCodeError() {
        return deviceCodeError;
    } // method getDeviceCodeError

    /**
     * Gets the errorMessage for the deviceCode instance variable
     * @return errorMessage (String)
     */
    public String getDeviceCodeErrorMessage() {
        return deviceCodeErrorMessage;
    } // method getDeviceCodeErrorMessage


    /**
     * Gets the errorcode for the methodCode instance variable
     * @return errorcode (int)
     */
    public int getMethodCodeError() {
        return methodCodeError;
    } // method getMethodCodeError

    /**
     * Gets the errorMessage for the methodCode instance variable
     * @return errorMessage (String)
     */
    public String getMethodCodeErrorMessage() {
        return methodCodeErrorMessage;
    } // method getMethodCodeErrorMessage


    /**
     * Gets the errorcode for the standardCode instance variable
     * @return errorcode (int)
     */
    public int getStandardCodeError() {
        return standardCodeError;
    } // method getStandardCodeError

    /**
     * Gets the errorMessage for the standardCode instance variable
     * @return errorMessage (String)
     */
    public String getStandardCodeErrorMessage() {
        return standardCodeErrorMessage;
    } // method getStandardCodeErrorMessage


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
     * Gets the errorcode for the spldattim instance variable
     * @return errorcode (int)
     */
    public int getSpldattimError() {
        return spldattimError;
    } // method getSpldattimError

    /**
     * Gets the errorMessage for the spldattim instance variable
     * @return errorMessage (String)
     */
    public String getSpldattimErrorMessage() {
        return spldattimErrorMessage;
    } // method getSpldattimErrorMessage


    /**
     * Gets the errorcode for the spldep instance variable
     * @return errorcode (int)
     */
    public int getSpldepError() {
        return spldepError;
    } // method getSpldepError

    /**
     * Gets the errorMessage for the spldep instance variable
     * @return errorMessage (String)
     */
    public String getSpldepErrorMessage() {
        return spldepErrorMessage;
    } // method getSpldepErrorMessage


    /**
     * Gets the errorcode for the spldis instance variable
     * @return errorcode (int)
     */
    public int getSpldisError() {
        return spldisError;
    } // method getSpldisError

    /**
     * Gets the errorMessage for the spldis instance variable
     * @return errorMessage (String)
     */
    public String getSpldisErrorMessage() {
        return spldisErrorMessage;
    } // method getSpldisErrorMessage


    /**
     * Gets the errorcode for the splvol instance variable
     * @return errorcode (int)
     */
    public int getSplvolError() {
        return splvolError;
    } // method getSplvolError

    /**
     * Gets the errorMessage for the splvol instance variable
     * @return errorMessage (String)
     */
    public String getSplvolErrorMessage() {
        return splvolErrorMessage;
    } // method getSplvolErrorMessage


    /**
     * Gets the errorcode for the sievsz instance variable
     * @return errorcode (int)
     */
    public int getSievszError() {
        return sievszError;
    } // method getSievszError

    /**
     * Gets the errorMessage for the sievsz instance variable
     * @return errorMessage (String)
     */
    public String getSievszErrorMessage() {
        return sievszErrorMessage;
    } // method getSievszErrorMessage


    /**
     * Gets the errorcode for the kurt instance variable
     * @return errorcode (int)
     */
    public int getKurtError() {
        return kurtError;
    } // method getKurtError

    /**
     * Gets the errorMessage for the kurt instance variable
     * @return errorMessage (String)
     */
    public String getKurtErrorMessage() {
        return kurtErrorMessage;
    } // method getKurtErrorMessage


    /**
     * Gets the errorcode for the skew instance variable
     * @return errorcode (int)
     */
    public int getSkewError() {
        return skewError;
    } // method getSkewError

    /**
     * Gets the errorMessage for the skew instance variable
     * @return errorMessage (String)
     */
    public String getSkewErrorMessage() {
        return skewErrorMessage;
    } // method getSkewErrorMessage


    /**
     * Gets the errorcode for the meanpz instance variable
     * @return errorcode (int)
     */
    public int getMeanpzError() {
        return meanpzError;
    } // method getMeanpzError

    /**
     * Gets the errorMessage for the meanpz instance variable
     * @return errorMessage (String)
     */
    public String getMeanpzErrorMessage() {
        return meanpzErrorMessage;
    } // method getMeanpzErrorMessage


    /**
     * Gets the errorcode for the medipz instance variable
     * @return errorcode (int)
     */
    public int getMedipzError() {
        return medipzError;
    } // method getMedipzError

    /**
     * Gets the errorMessage for the medipz instance variable
     * @return errorMessage (String)
     */
    public String getMedipzErrorMessage() {
        return medipzErrorMessage;
    } // method getMedipzErrorMessage


    /**
     * Gets the errorcode for the pctsat instance variable
     * @return errorcode (int)
     */
    public int getPctsatError() {
        return pctsatError;
    } // method getPctsatError

    /**
     * Gets the errorMessage for the pctsat instance variable
     * @return errorMessage (String)
     */
    public String getPctsatErrorMessage() {
        return pctsatErrorMessage;
    } // method getPctsatErrorMessage


    /**
     * Gets the errorcode for the pctsil instance variable
     * @return errorcode (int)
     */
    public int getPctsilError() {
        return pctsilError;
    } // method getPctsilError

    /**
     * Gets the errorMessage for the pctsil instance variable
     * @return errorMessage (String)
     */
    public String getPctsilErrorMessage() {
        return pctsilErrorMessage;
    } // method getPctsilErrorMessage


    /**
     * Gets the errorcode for the permty instance variable
     * @return errorcode (int)
     */
    public int getPermtyError() {
        return permtyError;
    } // method getPermtyError

    /**
     * Gets the errorMessage for the permty instance variable
     * @return errorMessage (String)
     */
    public String getPermtyErrorMessage() {
        return permtyErrorMessage;
    } // method getPermtyErrorMessage


    /**
     * Gets the errorcode for the porsty instance variable
     * @return errorcode (int)
     */
    public int getPorstyError() {
        return porstyError;
    } // method getPorstyError

    /**
     * Gets the errorMessage for the porsty instance variable
     * @return errorMessage (String)
     */
    public String getPorstyErrorMessage() {
        return porstyErrorMessage;
    } // method getPorstyErrorMessage


    /**
     * Gets the errorcode for the dwf instance variable
     * @return errorcode (int)
     */
    public int getDwfError() {
        return dwfError;
    } // method getDwfError

    /**
     * Gets the errorMessage for the dwf instance variable
     * @return errorMessage (String)
     */
    public String getDwfErrorMessage() {
        return dwfErrorMessage;
    } // method getDwfErrorMessage


    /**
     * Gets the errorcode for the cod instance variable
     * @return errorcode (int)
     */
    public int getCodError() {
        return codError;
    } // method getCodError

    /**
     * Gets the errorMessage for the cod instance variable
     * @return errorMessage (String)
     */
    public String getCodErrorMessage() {
        return codErrorMessage;
    } // method getCodErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnSedphy. e.g.<pre>
     * MrnSedphy sedphy = new MrnSedphy(<val1>);
     * MrnSedphy sedphyArray[] = sedphy.get();</pre>
     * will get the MrnSedphy record where code = <val1>.
     * @return Array of MrnSedphy (MrnSedphy[])
     */
    public MrnSedphy[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnSedphy sedphyArray[] =
     *     new MrnSedphy().get(MrnSedphy.CODE+"=<val1>");</pre>
     * will get the MrnSedphy record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnSedphy (MrnSedphy[])
     */
    public MrnSedphy[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnSedphy sedphyArray[] =
     *     new MrnSedphy().get("1=1",sedphy.CODE);</pre>
     * will get the all the MrnSedphy records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSedphy (MrnSedphy[])
     */
    public MrnSedphy[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnSedphy.CODE,MrnSedphy.STATION_ID;
     * String where = MrnSedphy.CODE + "=<val1";
     * String order = MrnSedphy.CODE;
     * MrnSedphy sedphyArray[] =
     *     new MrnSedphy().get(columns, where, order);</pre>
     * will get the code and stationId colums of all MrnSedphy records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSedphy (MrnSedphy[])
     */
    public MrnSedphy[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnSedphy.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnSedphy[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol         = db.getColNumber(CODE);
        int stationIdCol    = db.getColNumber(STATION_ID);
        int deviceCodeCol   = db.getColNumber(DEVICE_CODE);
        int methodCodeCol   = db.getColNumber(METHOD_CODE);
        int standardCodeCol = db.getColNumber(STANDARD_CODE);
        int subdesCol       = db.getColNumber(SUBDES);
        int spldattimCol    = db.getColNumber(SPLDATTIM);
        int spldepCol       = db.getColNumber(SPLDEP);
        int spldisCol       = db.getColNumber(SPLDIS);
        int splvolCol       = db.getColNumber(SPLVOL);
        int sievszCol       = db.getColNumber(SIEVSZ);
        int kurtCol         = db.getColNumber(KURT);
        int skewCol         = db.getColNumber(SKEW);
        int meanpzCol       = db.getColNumber(MEANPZ);
        int medipzCol       = db.getColNumber(MEDIPZ);
        int pctsatCol       = db.getColNumber(PCTSAT);
        int pctsilCol       = db.getColNumber(PCTSIL);
        int permtyCol       = db.getColNumber(PERMTY);
        int porstyCol       = db.getColNumber(PORSTY);
        int dwfCol          = db.getColNumber(DWF);
        int codCol          = db.getColNumber(COD);
        MrnSedphy[] cArray = new MrnSedphy[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnSedphy();
            if (codeCol != -1)
                cArray[i].setCode        ((String) row.elementAt(codeCol));
            if (stationIdCol != -1)
                cArray[i].setStationId   ((String) row.elementAt(stationIdCol));
            if (deviceCodeCol != -1)
                cArray[i].setDeviceCode  ((String) row.elementAt(deviceCodeCol));
            if (methodCodeCol != -1)
                cArray[i].setMethodCode  ((String) row.elementAt(methodCodeCol));
            if (standardCodeCol != -1)
                cArray[i].setStandardCode((String) row.elementAt(standardCodeCol));
            if (subdesCol != -1)
                cArray[i].setSubdes      ((String) row.elementAt(subdesCol));
            if (spldattimCol != -1)
                cArray[i].setSpldattim   ((String) row.elementAt(spldattimCol));
            if (spldepCol != -1)
                cArray[i].setSpldep      ((String) row.elementAt(spldepCol));
            if (spldisCol != -1)
                cArray[i].setSpldis      ((String) row.elementAt(spldisCol));
            if (splvolCol != -1)
                cArray[i].setSplvol      ((String) row.elementAt(splvolCol));
            if (sievszCol != -1)
                cArray[i].setSievsz      ((String) row.elementAt(sievszCol));
            if (kurtCol != -1)
                cArray[i].setKurt        ((String) row.elementAt(kurtCol));
            if (skewCol != -1)
                cArray[i].setSkew        ((String) row.elementAt(skewCol));
            if (meanpzCol != -1)
                cArray[i].setMeanpz      ((String) row.elementAt(meanpzCol));
            if (medipzCol != -1)
                cArray[i].setMedipz      ((String) row.elementAt(medipzCol));
            if (pctsatCol != -1)
                cArray[i].setPctsat      ((String) row.elementAt(pctsatCol));
            if (pctsilCol != -1)
                cArray[i].setPctsil      ((String) row.elementAt(pctsilCol));
            if (permtyCol != -1)
                cArray[i].setPermty      ((String) row.elementAt(permtyCol));
            if (porstyCol != -1)
                cArray[i].setPorsty      ((String) row.elementAt(porstyCol));
            if (dwfCol != -1)
                cArray[i].setDwf         ((String) row.elementAt(dwfCol));
            if (codCol != -1)
                cArray[i].setCod         ((String) row.elementAt(codCol));
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
     *     new MrnSedphy(
     *         INTNULL,
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
     *         <val14>,
     *         <val15>,
     *         <val16>,
     *         <val17>,
     *         <val18>,
     *         <val19>,
     *         <val20>,
     *         <val21>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     stationId    = <val2>,
     *     deviceCode   = <val3>,
     *     methodCode   = <val4>,
     *     standardCode = <val5>,
     *     subdes       = <val6>,
     *     spldattim    = <val7>,
     *     spldep       = <val8>,
     *     spldis       = <val9>,
     *     splvol       = <val10>,
     *     sievsz       = <val11>,
     *     kurt         = <val12>,
     *     skew         = <val13>,
     *     meanpz       = <val14>,
     *     medipz       = <val15>,
     *     pctsat       = <val16>,
     *     pctsil       = <val17>,
     *     permty       = <val18>,
     *     porsty       = <val19>,
     *     dwf          = <val20>,
     *     cod          = <val21>.
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
     * Boolean success = new MrnSedphy(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where stationId = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSedphy sedphy = new MrnSedphy();
     * success = sedphy.del(MrnSedphy.CODE+"=<val1>");</pre>
     * will delete all records where code = <val1>.
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
     * update are taken from the MrnSedphy argument, .e.g.<pre>
     * Boolean success
     * MrnSedphy updMrnSedphy = new MrnSedphy();
     * updMrnSedphy.setStationId(<val2>);
     * MrnSedphy whereMrnSedphy = new MrnSedphy(<val1>);
     * success = whereMrnSedphy.upd(updMrnSedphy);</pre>
     * will update StationId to <val2> for all records where
     * code = <val1>.
     * @param  sedphy  A MrnSedphy variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSedphy sedphy) {
        return db.update (TABLE, createColVals(sedphy), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSedphy updMrnSedphy = new MrnSedphy();
     * updMrnSedphy.setStationId(<val2>);
     * MrnSedphy whereMrnSedphy = new MrnSedphy();
     * success = whereMrnSedphy.upd(
     *     updMrnSedphy, MrnSedphy.CODE+"=<val1>");</pre>
     * will update StationId to <val2> for all records where
     * code = <val1>.
     * @param  sedphy  A MrnSedphy variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSedphy sedphy, String where) {
        return db.update (TABLE, createColVals(sedphy), where);
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
        if (getCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CODE + "=" + getCode("");
        } // if getCode
        if (getStationId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATION_ID + "='" + getStationId() + "'";
        } // if getStationId
        if (getDeviceCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEVICE_CODE + "=" + getDeviceCode("");
        } // if getDeviceCode
        if (getMethodCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + METHOD_CODE + "=" + getMethodCode("");
        } // if getMethodCode
        if (getStandardCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STANDARD_CODE + "=" + getStandardCode("");
        } // if getStandardCode
        if (getSubdes() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SUBDES + "='" + getSubdes() + "'";
        } // if getSubdes
        if (!getSpldattim().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPLDATTIM +
                "=" + Tables.getDateFormat(getSpldattim());
        } // if getSpldattim
        if (getSpldep() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPLDEP + "=" + getSpldep("");
        } // if getSpldep
        if (getSpldis() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPLDIS + "=" + getSpldis("");
        } // if getSpldis
        if (getSplvol() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPLVOL + "=" + getSplvol("");
        } // if getSplvol
        if (getSievsz() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SIEVSZ + "=" + getSievsz("");
        } // if getSievsz
        if (getKurt() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + KURT + "=" + getKurt("");
        } // if getKurt
        if (getSkew() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SKEW + "=" + getSkew("");
        } // if getSkew
        if (getMeanpz() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MEANPZ + "=" + getMeanpz("");
        } // if getMeanpz
        if (getMedipz() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MEDIPZ + "=" + getMedipz("");
        } // if getMedipz
        if (getPctsat() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PCTSAT + "=" + getPctsat("");
        } // if getPctsat
        if (getPctsil() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PCTSIL + "=" + getPctsil("");
        } // if getPctsil
        if (getPermty() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PERMTY + "=" + getPermty("");
        } // if getPermty
        if (getPorsty() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PORSTY + "=" + getPorsty("");
        } // if getPorsty
        if (getDwf() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DWF + "=" + getDwf("");
        } // if getDwf
        if (getCod() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + COD + "=" + getCod("");
        } // if getCod
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnSedphy aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getDeviceCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEVICE_CODE +"=";
            colVals += (aVar.getDeviceCode() == INTNULL2 ?
                "null" : aVar.getDeviceCode(""));
        } // if aVar.getDeviceCode
        if (aVar.getMethodCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += METHOD_CODE +"=";
            colVals += (aVar.getMethodCode() == INTNULL2 ?
                "null" : aVar.getMethodCode(""));
        } // if aVar.getMethodCode
        if (aVar.getStandardCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STANDARD_CODE +"=";
            colVals += (aVar.getStandardCode() == INTNULL2 ?
                "null" : aVar.getStandardCode(""));
        } // if aVar.getStandardCode
        if (aVar.getSubdes() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SUBDES +"=";
            colVals += (aVar.getSubdes().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSubdes() + "'");
        } // if aVar.getSubdes
        if (!aVar.getSpldattim().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPLDATTIM +"=";
            colVals += (aVar.getSpldattim().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getSpldattim()));
        } // if aVar.getSpldattim
        if (aVar.getSpldep() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPLDEP +"=";
            colVals += (aVar.getSpldep() == FLOATNULL2 ?
                "null" : aVar.getSpldep(""));
        } // if aVar.getSpldep
        if (aVar.getSpldis() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPLDIS +"=";
            colVals += (aVar.getSpldis() == INTNULL2 ?
                "null" : aVar.getSpldis(""));
        } // if aVar.getSpldis
        if (aVar.getSplvol() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPLVOL +"=";
            colVals += (aVar.getSplvol() == FLOATNULL2 ?
                "null" : aVar.getSplvol(""));
        } // if aVar.getSplvol
        if (aVar.getSievsz() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SIEVSZ +"=";
            colVals += (aVar.getSievsz() == FLOATNULL2 ?
                "null" : aVar.getSievsz(""));
        } // if aVar.getSievsz
        if (aVar.getKurt() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += KURT +"=";
            colVals += (aVar.getKurt() == FLOATNULL2 ?
                "null" : aVar.getKurt(""));
        } // if aVar.getKurt
        if (aVar.getSkew() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SKEW +"=";
            colVals += (aVar.getSkew() == FLOATNULL2 ?
                "null" : aVar.getSkew(""));
        } // if aVar.getSkew
        if (aVar.getMeanpz() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MEANPZ +"=";
            colVals += (aVar.getMeanpz() == INTNULL2 ?
                "null" : aVar.getMeanpz(""));
        } // if aVar.getMeanpz
        if (aVar.getMedipz() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MEDIPZ +"=";
            colVals += (aVar.getMedipz() == INTNULL2 ?
                "null" : aVar.getMedipz(""));
        } // if aVar.getMedipz
        if (aVar.getPctsat() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PCTSAT +"=";
            colVals += (aVar.getPctsat() == FLOATNULL2 ?
                "null" : aVar.getPctsat(""));
        } // if aVar.getPctsat
        if (aVar.getPctsil() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PCTSIL +"=";
            colVals += (aVar.getPctsil() == FLOATNULL2 ?
                "null" : aVar.getPctsil(""));
        } // if aVar.getPctsil
        if (aVar.getPermty() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PERMTY +"=";
            colVals += (aVar.getPermty() == INTNULL2 ?
                "null" : aVar.getPermty(""));
        } // if aVar.getPermty
        if (aVar.getPorsty() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PORSTY +"=";
            colVals += (aVar.getPorsty() == FLOATNULL2 ?
                "null" : aVar.getPorsty(""));
        } // if aVar.getPorsty
        if (aVar.getDwf() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DWF +"=";
            colVals += (aVar.getDwf() == FLOATNULL2 ?
                "null" : aVar.getDwf(""));
        } // if aVar.getDwf
        if (aVar.getCod() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += COD +"=";
            colVals += (aVar.getCod() == FLOATNULL2 ?
                "null" : aVar.getCod(""));
        } // if aVar.getCod
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getStationId() != CHARNULL) {
            columns = columns + "," + STATION_ID;
        } // if getStationId
        if (getDeviceCode() != INTNULL) {
            columns = columns + "," + DEVICE_CODE;
        } // if getDeviceCode
        if (getMethodCode() != INTNULL) {
            columns = columns + "," + METHOD_CODE;
        } // if getMethodCode
        if (getStandardCode() != INTNULL) {
            columns = columns + "," + STANDARD_CODE;
        } // if getStandardCode
        if (getSubdes() != CHARNULL) {
            columns = columns + "," + SUBDES;
        } // if getSubdes
        if (!getSpldattim().equals(DATENULL)) {
            columns = columns + "," + SPLDATTIM;
        } // if getSpldattim
        if (getSpldep() != FLOATNULL) {
            columns = columns + "," + SPLDEP;
        } // if getSpldep
        if (getSpldis() != INTNULL) {
            columns = columns + "," + SPLDIS;
        } // if getSpldis
        if (getSplvol() != FLOATNULL) {
            columns = columns + "," + SPLVOL;
        } // if getSplvol
        if (getSievsz() != FLOATNULL) {
            columns = columns + "," + SIEVSZ;
        } // if getSievsz
        if (getKurt() != FLOATNULL) {
            columns = columns + "," + KURT;
        } // if getKurt
        if (getSkew() != FLOATNULL) {
            columns = columns + "," + SKEW;
        } // if getSkew
        if (getMeanpz() != INTNULL) {
            columns = columns + "," + MEANPZ;
        } // if getMeanpz
        if (getMedipz() != INTNULL) {
            columns = columns + "," + MEDIPZ;
        } // if getMedipz
        if (getPctsat() != FLOATNULL) {
            columns = columns + "," + PCTSAT;
        } // if getPctsat
        if (getPctsil() != FLOATNULL) {
            columns = columns + "," + PCTSIL;
        } // if getPctsil
        if (getPermty() != INTNULL) {
            columns = columns + "," + PERMTY;
        } // if getPermty
        if (getPorsty() != FLOATNULL) {
            columns = columns + "," + PORSTY;
        } // if getPorsty
        if (getDwf() != FLOATNULL) {
            columns = columns + "," + DWF;
        } // if getDwf
        if (getCod() != FLOATNULL) {
            columns = columns + "," + COD;
        } // if getCod
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        setCode(getMaxCode()+1);
        String values  = getCode("");
        if (getStationId() != CHARNULL) {
            values  = values  + ",'" + getStationId() + "'";
        } // if getStationId
        if (getDeviceCode() != INTNULL) {
            values  = values  + "," + getDeviceCode("");
        } // if getDeviceCode
        if (getMethodCode() != INTNULL) {
            values  = values  + "," + getMethodCode("");
        } // if getMethodCode
        if (getStandardCode() != INTNULL) {
            values  = values  + "," + getStandardCode("");
        } // if getStandardCode
        if (getSubdes() != CHARNULL) {
            values  = values  + ",'" + getSubdes() + "'";
        } // if getSubdes
        if (!getSpldattim().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getSpldattim());
        } // if getSpldattim
        if (getSpldep() != FLOATNULL) {
            values  = values  + "," + getSpldep("");
        } // if getSpldep
        if (getSpldis() != INTNULL) {
            values  = values  + "," + getSpldis("");
        } // if getSpldis
        if (getSplvol() != FLOATNULL) {
            values  = values  + "," + getSplvol("");
        } // if getSplvol
        if (getSievsz() != FLOATNULL) {
            values  = values  + "," + getSievsz("");
        } // if getSievsz
        if (getKurt() != FLOATNULL) {
            values  = values  + "," + getKurt("");
        } // if getKurt
        if (getSkew() != FLOATNULL) {
            values  = values  + "," + getSkew("");
        } // if getSkew
        if (getMeanpz() != INTNULL) {
            values  = values  + "," + getMeanpz("");
        } // if getMeanpz
        if (getMedipz() != INTNULL) {
            values  = values  + "," + getMedipz("");
        } // if getMedipz
        if (getPctsat() != FLOATNULL) {
            values  = values  + "," + getPctsat("");
        } // if getPctsat
        if (getPctsil() != FLOATNULL) {
            values  = values  + "," + getPctsil("");
        } // if getPctsil
        if (getPermty() != INTNULL) {
            values  = values  + "," + getPermty("");
        } // if getPermty
        if (getPorsty() != FLOATNULL) {
            values  = values  + "," + getPorsty("");
        } // if getPorsty
        if (getDwf() != FLOATNULL) {
            values  = values  + "," + getDwf("");
        } // if getDwf
        if (getCod() != FLOATNULL) {
            values  = values  + "," + getCod("");
        } // if getCod
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")         + "|" +
            getStationId("")    + "|" +
            getDeviceCode("")   + "|" +
            getMethodCode("")   + "|" +
            getStandardCode("") + "|" +
            getSubdes("")       + "|" +
            getSpldattim("")    + "|" +
            getSpldep("")       + "|" +
            getSpldis("")       + "|" +
            getSplvol("")       + "|" +
            getSievsz("")       + "|" +
            getKurt("")         + "|" +
            getSkew("")         + "|" +
            getMeanpz("")       + "|" +
            getMedipz("")       + "|" +
            getPctsat("")       + "|" +
            getPctsil("")       + "|" +
            getPermty("")       + "|" +
            getPorsty("")       + "|" +
            getDwf("")          + "|" +
            getCod("")          + "|";
    } // method toString

} // class MrnSedphy
