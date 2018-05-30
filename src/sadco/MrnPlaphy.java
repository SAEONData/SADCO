package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the PLAPHY table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 071213 - SIT Group
 * @version
 * 071213 - GenTableClassB class - create class<br>
 */
public class MrnPlaphy extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "PLAPHY";
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
    /** The meshsz field name */
    public static final String MESHSZ = "MESHSZ";
    /** The ddbiom field name */
    public static final String DDBIOM = "DDBIOM";
    /** The netflo field name */
    public static final String NETFLO = "NETFLO";
    /** The phaeop field name */
    public static final String PHAEOP = "PHAEOP";
    /** The phycel field name */
    public static final String PHYCEL = "PHYCEL";
    /** The prodty field name */
    public static final String PRODTY = "PRODTY";
    /** The towatt field name */
    public static final String TOWATT = "TOWATT";
    /** The trawlVol field name */
    public static final String TRAWL_VOL = "TRAWL_VOL";
    /** The vpbiom field name */
    public static final String VPBIOM = "VPBIOM";
    /** The zoobio field name */
    public static final String ZOOBIO = "ZOOBIO";

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
    private float     meshsz;
    private float     ddbiom;
    private float     netflo;
    private float     phaeop;
    private float     phycel;
    private float     prodty;
    private String    towatt;
    private int       trawlVol;
    private float     vpbiom;
    private float     zoobio;

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
    private int meshszError       = ERROR_NORMAL;
    private int ddbiomError       = ERROR_NORMAL;
    private int netfloError       = ERROR_NORMAL;
    private int phaeopError       = ERROR_NORMAL;
    private int phycelError       = ERROR_NORMAL;
    private int prodtyError       = ERROR_NORMAL;
    private int towattError       = ERROR_NORMAL;
    private int trawlVolError     = ERROR_NORMAL;
    private int vpbiomError       = ERROR_NORMAL;
    private int zoobioError       = ERROR_NORMAL;

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
    private String meshszErrorMessage       = "";
    private String ddbiomErrorMessage       = "";
    private String netfloErrorMessage       = "";
    private String phaeopErrorMessage       = "";
    private String phycelErrorMessage       = "";
    private String prodtyErrorMessage       = "";
    private String towattErrorMessage       = "";
    private String trawlVolErrorMessage     = "";
    private String vpbiomErrorMessage       = "";
    private String zoobioErrorMessage       = "";

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
    public static final float     MESHSZ_MN = FLOATMIN;
    public static final float     MESHSZ_MX = FLOATMAX;
    public static final float     DDBIOM_MN = FLOATMIN;
    public static final float     DDBIOM_MX = FLOATMAX;
    public static final float     NETFLO_MN = FLOATMIN;
    public static final float     NETFLO_MX = FLOATMAX;
    public static final float     PHAEOP_MN = FLOATMIN;
    public static final float     PHAEOP_MX = FLOATMAX;
    public static final float     PHYCEL_MN = FLOATMIN;
    public static final float     PHYCEL_MX = FLOATMAX;
    public static final float     PRODTY_MN = FLOATMIN;
    public static final float     PRODTY_MX = FLOATMAX;
    public static final int       TRAWL_VOL_MN = INTMIN;
    public static final int       TRAWL_VOL_MX = INTMAX;
    public static final float     VPBIOM_MN = FLOATMIN;
    public static final float     VPBIOM_MX = FLOATMAX;
    public static final float     ZOOBIO_MN = FLOATMIN;
    public static final float     ZOOBIO_MX = FLOATMAX;

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
    Exception meshszOutOfBoundsException =
        new Exception ("'meshsz' out of bounds: " +
            MESHSZ_MN + " - " + MESHSZ_MX);
    Exception ddbiomOutOfBoundsException =
        new Exception ("'ddbiom' out of bounds: " +
            DDBIOM_MN + " - " + DDBIOM_MX);
    Exception netfloOutOfBoundsException =
        new Exception ("'netflo' out of bounds: " +
            NETFLO_MN + " - " + NETFLO_MX);
    Exception phaeopOutOfBoundsException =
        new Exception ("'phaeop' out of bounds: " +
            PHAEOP_MN + " - " + PHAEOP_MX);
    Exception phycelOutOfBoundsException =
        new Exception ("'phycel' out of bounds: " +
            PHYCEL_MN + " - " + PHYCEL_MX);
    Exception prodtyOutOfBoundsException =
        new Exception ("'prodty' out of bounds: " +
            PRODTY_MN + " - " + PRODTY_MX);
    Exception trawlVolOutOfBoundsException =
        new Exception ("'trawlVol' out of bounds: " +
            TRAWL_VOL_MN + " - " + TRAWL_VOL_MX);
    Exception vpbiomOutOfBoundsException =
        new Exception ("'vpbiom' out of bounds: " +
            VPBIOM_MN + " - " + VPBIOM_MX);
    Exception zoobioOutOfBoundsException =
        new Exception ("'zoobio' out of bounds: " +
            ZOOBIO_MN + " - " + ZOOBIO_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnPlaphy() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnPlaphy constructor 1"); // debug
    } // MrnPlaphy constructor

    /**
     * Instantiate a MrnPlaphy object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MrnPlaphy(
            int code) {
        this();
        setCode         (code        );
        if (dbg) System.out.println ("<br>in MrnPlaphy constructor 2"); // debug
    } // MrnPlaphy constructor

    /**
     * Instantiate a MrnPlaphy object and initialize the instance variables.
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
     * @param meshsz        The meshsz       (float)
     * @param ddbiom        The ddbiom       (float)
     * @param netflo        The netflo       (float)
     * @param phaeop        The phaeop       (float)
     * @param phycel        The phycel       (float)
     * @param prodty        The prodty       (float)
     * @param towatt        The towatt       (String)
     * @param trawlVol      The trawlVol     (int)
     * @param vpbiom        The vpbiom       (float)
     * @param zoobio        The zoobio       (float)
     * @return A MrnPlaphy object
     */
    public MrnPlaphy(
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
            float          meshsz,
            float          ddbiom,
            float          netflo,
            float          phaeop,
            float          phycel,
            float          prodty,
            String         towatt,
            int            trawlVol,
            float          vpbiom,
            float          zoobio) {
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
        setMeshsz       (meshsz      );
        setDdbiom       (ddbiom      );
        setNetflo       (netflo      );
        setPhaeop       (phaeop      );
        setPhycel       (phycel      );
        setProdty       (prodty      );
        setTowatt       (towatt      );
        setTrawlVol     (trawlVol    );
        setVpbiom       (vpbiom      );
        setZoobio       (zoobio      );
        if (dbg) System.out.println ("<br>in MrnPlaphy constructor 3"); // debug
    } // MrnPlaphy constructor

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
        setMeshsz       (FLOATNULL);
        setDdbiom       (FLOATNULL);
        setNetflo       (FLOATNULL);
        setPhaeop       (FLOATNULL);
        setPhycel       (FLOATNULL);
        setProdty       (FLOATNULL);
        setTowatt       (CHARNULL );
        setTrawlVol     (INTNULL  );
        setVpbiom       (FLOATNULL);
        setZoobio       (FLOATNULL);
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
     * Set the 'meshsz' class variable
     * @param  meshsz (float)
     */
    public int setMeshsz(float meshsz) {
        try {
            if ( ((meshsz == FLOATNULL) || 
                  (meshsz == FLOATNULL2)) ||
                !((meshsz < MESHSZ_MN) ||
                  (meshsz > MESHSZ_MX)) ) {
                this.meshsz = meshsz;
                meshszError = ERROR_NORMAL;
            } else {
                throw meshszOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMeshszError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return meshszError;
    } // method setMeshsz

    /**
     * Set the 'meshsz' class variable
     * @param  meshsz (Integer)
     */
    public int setMeshsz(Integer meshsz) {
        try {
            setMeshsz(meshsz.floatValue());
        } catch (Exception e) {
            setMeshszError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return meshszError;
    } // method setMeshsz

    /**
     * Set the 'meshsz' class variable
     * @param  meshsz (Float)
     */
    public int setMeshsz(Float meshsz) {
        try {
            setMeshsz(meshsz.floatValue());
        } catch (Exception e) {
            setMeshszError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return meshszError;
    } // method setMeshsz

    /**
     * Set the 'meshsz' class variable
     * @param  meshsz (String)
     */
    public int setMeshsz(String meshsz) {
        try {
            setMeshsz(new Float(meshsz).floatValue());
        } catch (Exception e) {
            setMeshszError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return meshszError;
    } // method setMeshsz

    /**
     * Called when an exception has occured
     * @param  meshsz (String)
     */
    private void setMeshszError (float meshsz, Exception e, int error) {
        this.meshsz = meshsz;
        meshszErrorMessage = e.toString();
        meshszError = error;
    } // method setMeshszError


    /**
     * Set the 'ddbiom' class variable
     * @param  ddbiom (float)
     */
    public int setDdbiom(float ddbiom) {
        try {
            if ( ((ddbiom == FLOATNULL) || 
                  (ddbiom == FLOATNULL2)) ||
                !((ddbiom < DDBIOM_MN) ||
                  (ddbiom > DDBIOM_MX)) ) {
                this.ddbiom = ddbiom;
                ddbiomError = ERROR_NORMAL;
            } else {
                throw ddbiomOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDdbiomError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return ddbiomError;
    } // method setDdbiom

    /**
     * Set the 'ddbiom' class variable
     * @param  ddbiom (Integer)
     */
    public int setDdbiom(Integer ddbiom) {
        try {
            setDdbiom(ddbiom.floatValue());
        } catch (Exception e) {
            setDdbiomError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ddbiomError;
    } // method setDdbiom

    /**
     * Set the 'ddbiom' class variable
     * @param  ddbiom (Float)
     */
    public int setDdbiom(Float ddbiom) {
        try {
            setDdbiom(ddbiom.floatValue());
        } catch (Exception e) {
            setDdbiomError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ddbiomError;
    } // method setDdbiom

    /**
     * Set the 'ddbiom' class variable
     * @param  ddbiom (String)
     */
    public int setDdbiom(String ddbiom) {
        try {
            setDdbiom(new Float(ddbiom).floatValue());
        } catch (Exception e) {
            setDdbiomError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ddbiomError;
    } // method setDdbiom

    /**
     * Called when an exception has occured
     * @param  ddbiom (String)
     */
    private void setDdbiomError (float ddbiom, Exception e, int error) {
        this.ddbiom = ddbiom;
        ddbiomErrorMessage = e.toString();
        ddbiomError = error;
    } // method setDdbiomError


    /**
     * Set the 'netflo' class variable
     * @param  netflo (float)
     */
    public int setNetflo(float netflo) {
        try {
            if ( ((netflo == FLOATNULL) || 
                  (netflo == FLOATNULL2)) ||
                !((netflo < NETFLO_MN) ||
                  (netflo > NETFLO_MX)) ) {
                this.netflo = netflo;
                netfloError = ERROR_NORMAL;
            } else {
                throw netfloOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNetfloError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return netfloError;
    } // method setNetflo

    /**
     * Set the 'netflo' class variable
     * @param  netflo (Integer)
     */
    public int setNetflo(Integer netflo) {
        try {
            setNetflo(netflo.floatValue());
        } catch (Exception e) {
            setNetfloError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return netfloError;
    } // method setNetflo

    /**
     * Set the 'netflo' class variable
     * @param  netflo (Float)
     */
    public int setNetflo(Float netflo) {
        try {
            setNetflo(netflo.floatValue());
        } catch (Exception e) {
            setNetfloError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return netfloError;
    } // method setNetflo

    /**
     * Set the 'netflo' class variable
     * @param  netflo (String)
     */
    public int setNetflo(String netflo) {
        try {
            setNetflo(new Float(netflo).floatValue());
        } catch (Exception e) {
            setNetfloError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return netfloError;
    } // method setNetflo

    /**
     * Called when an exception has occured
     * @param  netflo (String)
     */
    private void setNetfloError (float netflo, Exception e, int error) {
        this.netflo = netflo;
        netfloErrorMessage = e.toString();
        netfloError = error;
    } // method setNetfloError


    /**
     * Set the 'phaeop' class variable
     * @param  phaeop (float)
     */
    public int setPhaeop(float phaeop) {
        try {
            if ( ((phaeop == FLOATNULL) || 
                  (phaeop == FLOATNULL2)) ||
                !((phaeop < PHAEOP_MN) ||
                  (phaeop > PHAEOP_MX)) ) {
                this.phaeop = phaeop;
                phaeopError = ERROR_NORMAL;
            } else {
                throw phaeopOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPhaeopError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return phaeopError;
    } // method setPhaeop

    /**
     * Set the 'phaeop' class variable
     * @param  phaeop (Integer)
     */
    public int setPhaeop(Integer phaeop) {
        try {
            setPhaeop(phaeop.floatValue());
        } catch (Exception e) {
            setPhaeopError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phaeopError;
    } // method setPhaeop

    /**
     * Set the 'phaeop' class variable
     * @param  phaeop (Float)
     */
    public int setPhaeop(Float phaeop) {
        try {
            setPhaeop(phaeop.floatValue());
        } catch (Exception e) {
            setPhaeopError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phaeopError;
    } // method setPhaeop

    /**
     * Set the 'phaeop' class variable
     * @param  phaeop (String)
     */
    public int setPhaeop(String phaeop) {
        try {
            setPhaeop(new Float(phaeop).floatValue());
        } catch (Exception e) {
            setPhaeopError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phaeopError;
    } // method setPhaeop

    /**
     * Called when an exception has occured
     * @param  phaeop (String)
     */
    private void setPhaeopError (float phaeop, Exception e, int error) {
        this.phaeop = phaeop;
        phaeopErrorMessage = e.toString();
        phaeopError = error;
    } // method setPhaeopError


    /**
     * Set the 'phycel' class variable
     * @param  phycel (float)
     */
    public int setPhycel(float phycel) {
        try {
            if ( ((phycel == FLOATNULL) || 
                  (phycel == FLOATNULL2)) ||
                !((phycel < PHYCEL_MN) ||
                  (phycel > PHYCEL_MX)) ) {
                this.phycel = phycel;
                phycelError = ERROR_NORMAL;
            } else {
                throw phycelOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPhycelError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return phycelError;
    } // method setPhycel

    /**
     * Set the 'phycel' class variable
     * @param  phycel (Integer)
     */
    public int setPhycel(Integer phycel) {
        try {
            setPhycel(phycel.floatValue());
        } catch (Exception e) {
            setPhycelError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phycelError;
    } // method setPhycel

    /**
     * Set the 'phycel' class variable
     * @param  phycel (Float)
     */
    public int setPhycel(Float phycel) {
        try {
            setPhycel(phycel.floatValue());
        } catch (Exception e) {
            setPhycelError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phycelError;
    } // method setPhycel

    /**
     * Set the 'phycel' class variable
     * @param  phycel (String)
     */
    public int setPhycel(String phycel) {
        try {
            setPhycel(new Float(phycel).floatValue());
        } catch (Exception e) {
            setPhycelError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return phycelError;
    } // method setPhycel

    /**
     * Called when an exception has occured
     * @param  phycel (String)
     */
    private void setPhycelError (float phycel, Exception e, int error) {
        this.phycel = phycel;
        phycelErrorMessage = e.toString();
        phycelError = error;
    } // method setPhycelError


    /**
     * Set the 'prodty' class variable
     * @param  prodty (float)
     */
    public int setProdty(float prodty) {
        try {
            if ( ((prodty == FLOATNULL) || 
                  (prodty == FLOATNULL2)) ||
                !((prodty < PRODTY_MN) ||
                  (prodty > PRODTY_MX)) ) {
                this.prodty = prodty;
                prodtyError = ERROR_NORMAL;
            } else {
                throw prodtyOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setProdtyError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return prodtyError;
    } // method setProdty

    /**
     * Set the 'prodty' class variable
     * @param  prodty (Integer)
     */
    public int setProdty(Integer prodty) {
        try {
            setProdty(prodty.floatValue());
        } catch (Exception e) {
            setProdtyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return prodtyError;
    } // method setProdty

    /**
     * Set the 'prodty' class variable
     * @param  prodty (Float)
     */
    public int setProdty(Float prodty) {
        try {
            setProdty(prodty.floatValue());
        } catch (Exception e) {
            setProdtyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return prodtyError;
    } // method setProdty

    /**
     * Set the 'prodty' class variable
     * @param  prodty (String)
     */
    public int setProdty(String prodty) {
        try {
            setProdty(new Float(prodty).floatValue());
        } catch (Exception e) {
            setProdtyError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return prodtyError;
    } // method setProdty

    /**
     * Called when an exception has occured
     * @param  prodty (String)
     */
    private void setProdtyError (float prodty, Exception e, int error) {
        this.prodty = prodty;
        prodtyErrorMessage = e.toString();
        prodtyError = error;
    } // method setProdtyError


    /**
     * Set the 'towatt' class variable
     * @param  towatt (String)
     */
    public int setTowatt(String towatt) {
        try {
            this.towatt = towatt;
            if (this.towatt != CHARNULL) {
                this.towatt = stripCRLF(this.towatt.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>towatt = " + this.towatt);
        } catch (Exception e) {
            setTowattError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return towattError;
    } // method setTowatt

    /**
     * Called when an exception has occured
     * @param  towatt (String)
     */
    private void setTowattError (String towatt, Exception e, int error) {
        this.towatt = towatt;
        towattErrorMessage = e.toString();
        towattError = error;
    } // method setTowattError


    /**
     * Set the 'trawlVol' class variable
     * @param  trawlVol (int)
     */
    public int setTrawlVol(int trawlVol) {
        try {
            if ( ((trawlVol == INTNULL) || 
                  (trawlVol == INTNULL2)) ||
                !((trawlVol < TRAWL_VOL_MN) ||
                  (trawlVol > TRAWL_VOL_MX)) ) {
                this.trawlVol = trawlVol;
                trawlVolError = ERROR_NORMAL;
            } else {
                throw trawlVolOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTrawlVolError(INTNULL, e, ERROR_LOCAL);
        } // try
        return trawlVolError;
    } // method setTrawlVol

    /**
     * Set the 'trawlVol' class variable
     * @param  trawlVol (Integer)
     */
    public int setTrawlVol(Integer trawlVol) {
        try {
            setTrawlVol(trawlVol.intValue());
        } catch (Exception e) {
            setTrawlVolError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return trawlVolError;
    } // method setTrawlVol

    /**
     * Set the 'trawlVol' class variable
     * @param  trawlVol (Float)
     */
    public int setTrawlVol(Float trawlVol) {
        try {
            if (trawlVol.floatValue() == FLOATNULL) {
                setTrawlVol(INTNULL);
            } else {
                setTrawlVol(trawlVol.intValue());
            } // if (trawlVol.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setTrawlVolError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return trawlVolError;
    } // method setTrawlVol

    /**
     * Set the 'trawlVol' class variable
     * @param  trawlVol (String)
     */
    public int setTrawlVol(String trawlVol) {
        try {
            setTrawlVol(new Integer(trawlVol).intValue());
        } catch (Exception e) {
            setTrawlVolError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return trawlVolError;
    } // method setTrawlVol

    /**
     * Called when an exception has occured
     * @param  trawlVol (String)
     */
    private void setTrawlVolError (int trawlVol, Exception e, int error) {
        this.trawlVol = trawlVol;
        trawlVolErrorMessage = e.toString();
        trawlVolError = error;
    } // method setTrawlVolError


    /**
     * Set the 'vpbiom' class variable
     * @param  vpbiom (float)
     */
    public int setVpbiom(float vpbiom) {
        try {
            if ( ((vpbiom == FLOATNULL) || 
                  (vpbiom == FLOATNULL2)) ||
                !((vpbiom < VPBIOM_MN) ||
                  (vpbiom > VPBIOM_MX)) ) {
                this.vpbiom = vpbiom;
                vpbiomError = ERROR_NORMAL;
            } else {
                throw vpbiomOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setVpbiomError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return vpbiomError;
    } // method setVpbiom

    /**
     * Set the 'vpbiom' class variable
     * @param  vpbiom (Integer)
     */
    public int setVpbiom(Integer vpbiom) {
        try {
            setVpbiom(vpbiom.floatValue());
        } catch (Exception e) {
            setVpbiomError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return vpbiomError;
    } // method setVpbiom

    /**
     * Set the 'vpbiom' class variable
     * @param  vpbiom (Float)
     */
    public int setVpbiom(Float vpbiom) {
        try {
            setVpbiom(vpbiom.floatValue());
        } catch (Exception e) {
            setVpbiomError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return vpbiomError;
    } // method setVpbiom

    /**
     * Set the 'vpbiom' class variable
     * @param  vpbiom (String)
     */
    public int setVpbiom(String vpbiom) {
        try {
            setVpbiom(new Float(vpbiom).floatValue());
        } catch (Exception e) {
            setVpbiomError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return vpbiomError;
    } // method setVpbiom

    /**
     * Called when an exception has occured
     * @param  vpbiom (String)
     */
    private void setVpbiomError (float vpbiom, Exception e, int error) {
        this.vpbiom = vpbiom;
        vpbiomErrorMessage = e.toString();
        vpbiomError = error;
    } // method setVpbiomError


    /**
     * Set the 'zoobio' class variable
     * @param  zoobio (float)
     */
    public int setZoobio(float zoobio) {
        try {
            if ( ((zoobio == FLOATNULL) || 
                  (zoobio == FLOATNULL2)) ||
                !((zoobio < ZOOBIO_MN) ||
                  (zoobio > ZOOBIO_MX)) ) {
                this.zoobio = zoobio;
                zoobioError = ERROR_NORMAL;
            } else {
                throw zoobioOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setZoobioError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return zoobioError;
    } // method setZoobio

    /**
     * Set the 'zoobio' class variable
     * @param  zoobio (Integer)
     */
    public int setZoobio(Integer zoobio) {
        try {
            setZoobio(zoobio.floatValue());
        } catch (Exception e) {
            setZoobioError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return zoobioError;
    } // method setZoobio

    /**
     * Set the 'zoobio' class variable
     * @param  zoobio (Float)
     */
    public int setZoobio(Float zoobio) {
        try {
            setZoobio(zoobio.floatValue());
        } catch (Exception e) {
            setZoobioError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return zoobioError;
    } // method setZoobio

    /**
     * Set the 'zoobio' class variable
     * @param  zoobio (String)
     */
    public int setZoobio(String zoobio) {
        try {
            setZoobio(new Float(zoobio).floatValue());
        } catch (Exception e) {
            setZoobioError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return zoobioError;
    } // method setZoobio

    /**
     * Called when an exception has occured
     * @param  zoobio (String)
     */
    private void setZoobioError (float zoobio, Exception e, int error) {
        this.zoobio = zoobio;
        zoobioErrorMessage = e.toString();
        zoobioError = error;
    } // method setZoobioError


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
     * Return the 'meshsz' class variable
     * @return meshsz (float)
     */
    public float getMeshsz() {
        return meshsz;
    } // method getMeshsz

    /**
     * Return the 'meshsz' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMeshsz methods
     * @return meshsz (String)
     */
    public String getMeshsz(String s) {
        return ((meshsz != FLOATNULL) ? new Float(meshsz).toString() : "");
    } // method getMeshsz


    /**
     * Return the 'ddbiom' class variable
     * @return ddbiom (float)
     */
    public float getDdbiom() {
        return ddbiom;
    } // method getDdbiom

    /**
     * Return the 'ddbiom' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDdbiom methods
     * @return ddbiom (String)
     */
    public String getDdbiom(String s) {
        return ((ddbiom != FLOATNULL) ? new Float(ddbiom).toString() : "");
    } // method getDdbiom


    /**
     * Return the 'netflo' class variable
     * @return netflo (float)
     */
    public float getNetflo() {
        return netflo;
    } // method getNetflo

    /**
     * Return the 'netflo' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNetflo methods
     * @return netflo (String)
     */
    public String getNetflo(String s) {
        return ((netflo != FLOATNULL) ? new Float(netflo).toString() : "");
    } // method getNetflo


    /**
     * Return the 'phaeop' class variable
     * @return phaeop (float)
     */
    public float getPhaeop() {
        return phaeop;
    } // method getPhaeop

    /**
     * Return the 'phaeop' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPhaeop methods
     * @return phaeop (String)
     */
    public String getPhaeop(String s) {
        return ((phaeop != FLOATNULL) ? new Float(phaeop).toString() : "");
    } // method getPhaeop


    /**
     * Return the 'phycel' class variable
     * @return phycel (float)
     */
    public float getPhycel() {
        return phycel;
    } // method getPhycel

    /**
     * Return the 'phycel' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPhycel methods
     * @return phycel (String)
     */
    public String getPhycel(String s) {
        return ((phycel != FLOATNULL) ? new Float(phycel).toString() : "");
    } // method getPhycel


    /**
     * Return the 'prodty' class variable
     * @return prodty (float)
     */
    public float getProdty() {
        return prodty;
    } // method getProdty

    /**
     * Return the 'prodty' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getProdty methods
     * @return prodty (String)
     */
    public String getProdty(String s) {
        return ((prodty != FLOATNULL) ? new Float(prodty).toString() : "");
    } // method getProdty


    /**
     * Return the 'towatt' class variable
     * @return towatt (String)
     */
    public String getTowatt() {
        return towatt;
    } // method getTowatt

    /**
     * Return the 'towatt' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTowatt methods
     * @return towatt (String)
     */
    public String getTowatt(String s) {
        return (towatt != CHARNULL ? towatt.replace('"','\'') : "");
    } // method getTowatt


    /**
     * Return the 'trawlVol' class variable
     * @return trawlVol (int)
     */
    public int getTrawlVol() {
        return trawlVol;
    } // method getTrawlVol

    /**
     * Return the 'trawlVol' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTrawlVol methods
     * @return trawlVol (String)
     */
    public String getTrawlVol(String s) {
        return ((trawlVol != INTNULL) ? new Integer(trawlVol).toString() : "");
    } // method getTrawlVol


    /**
     * Return the 'vpbiom' class variable
     * @return vpbiom (float)
     */
    public float getVpbiom() {
        return vpbiom;
    } // method getVpbiom

    /**
     * Return the 'vpbiom' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getVpbiom methods
     * @return vpbiom (String)
     */
    public String getVpbiom(String s) {
        return ((vpbiom != FLOATNULL) ? new Float(vpbiom).toString() : "");
    } // method getVpbiom


    /**
     * Return the 'zoobio' class variable
     * @return zoobio (float)
     */
    public float getZoobio() {
        return zoobio;
    } // method getZoobio

    /**
     * Return the 'zoobio' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getZoobio methods
     * @return zoobio (String)
     */
    public String getZoobio(String s) {
        return ((zoobio != FLOATNULL) ? new Float(zoobio).toString() : "");
    } // method getZoobio


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
            (meshsz == FLOATNULL) &&
            (ddbiom == FLOATNULL) &&
            (netflo == FLOATNULL) &&
            (phaeop == FLOATNULL) &&
            (phycel == FLOATNULL) &&
            (prodty == FLOATNULL) &&
            (towatt == CHARNULL) &&
            (trawlVol == INTNULL) &&
            (vpbiom == FLOATNULL) &&
            (zoobio == FLOATNULL)) {
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
            meshszError +
            ddbiomError +
            netfloError +
            phaeopError +
            phycelError +
            prodtyError +
            towattError +
            trawlVolError +
            vpbiomError +
            zoobioError;
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
     * Gets the errorcode for the meshsz instance variable
     * @return errorcode (int)
     */
    public int getMeshszError() {
        return meshszError;
    } // method getMeshszError

    /**
     * Gets the errorMessage for the meshsz instance variable
     * @return errorMessage (String)
     */
    public String getMeshszErrorMessage() {
        return meshszErrorMessage;
    } // method getMeshszErrorMessage


    /**
     * Gets the errorcode for the ddbiom instance variable
     * @return errorcode (int)
     */
    public int getDdbiomError() {
        return ddbiomError;
    } // method getDdbiomError

    /**
     * Gets the errorMessage for the ddbiom instance variable
     * @return errorMessage (String)
     */
    public String getDdbiomErrorMessage() {
        return ddbiomErrorMessage;
    } // method getDdbiomErrorMessage


    /**
     * Gets the errorcode for the netflo instance variable
     * @return errorcode (int)
     */
    public int getNetfloError() {
        return netfloError;
    } // method getNetfloError

    /**
     * Gets the errorMessage for the netflo instance variable
     * @return errorMessage (String)
     */
    public String getNetfloErrorMessage() {
        return netfloErrorMessage;
    } // method getNetfloErrorMessage


    /**
     * Gets the errorcode for the phaeop instance variable
     * @return errorcode (int)
     */
    public int getPhaeopError() {
        return phaeopError;
    } // method getPhaeopError

    /**
     * Gets the errorMessage for the phaeop instance variable
     * @return errorMessage (String)
     */
    public String getPhaeopErrorMessage() {
        return phaeopErrorMessage;
    } // method getPhaeopErrorMessage


    /**
     * Gets the errorcode for the phycel instance variable
     * @return errorcode (int)
     */
    public int getPhycelError() {
        return phycelError;
    } // method getPhycelError

    /**
     * Gets the errorMessage for the phycel instance variable
     * @return errorMessage (String)
     */
    public String getPhycelErrorMessage() {
        return phycelErrorMessage;
    } // method getPhycelErrorMessage


    /**
     * Gets the errorcode for the prodty instance variable
     * @return errorcode (int)
     */
    public int getProdtyError() {
        return prodtyError;
    } // method getProdtyError

    /**
     * Gets the errorMessage for the prodty instance variable
     * @return errorMessage (String)
     */
    public String getProdtyErrorMessage() {
        return prodtyErrorMessage;
    } // method getProdtyErrorMessage


    /**
     * Gets the errorcode for the towatt instance variable
     * @return errorcode (int)
     */
    public int getTowattError() {
        return towattError;
    } // method getTowattError

    /**
     * Gets the errorMessage for the towatt instance variable
     * @return errorMessage (String)
     */
    public String getTowattErrorMessage() {
        return towattErrorMessage;
    } // method getTowattErrorMessage


    /**
     * Gets the errorcode for the trawlVol instance variable
     * @return errorcode (int)
     */
    public int getTrawlVolError() {
        return trawlVolError;
    } // method getTrawlVolError

    /**
     * Gets the errorMessage for the trawlVol instance variable
     * @return errorMessage (String)
     */
    public String getTrawlVolErrorMessage() {
        return trawlVolErrorMessage;
    } // method getTrawlVolErrorMessage


    /**
     * Gets the errorcode for the vpbiom instance variable
     * @return errorcode (int)
     */
    public int getVpbiomError() {
        return vpbiomError;
    } // method getVpbiomError

    /**
     * Gets the errorMessage for the vpbiom instance variable
     * @return errorMessage (String)
     */
    public String getVpbiomErrorMessage() {
        return vpbiomErrorMessage;
    } // method getVpbiomErrorMessage


    /**
     * Gets the errorcode for the zoobio instance variable
     * @return errorcode (int)
     */
    public int getZoobioError() {
        return zoobioError;
    } // method getZoobioError

    /**
     * Gets the errorMessage for the zoobio instance variable
     * @return errorMessage (String)
     */
    public String getZoobioErrorMessage() {
        return zoobioErrorMessage;
    } // method getZoobioErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnPlaphy. e.g.<pre>
     * MrnPlaphy plaphy = new MrnPlaphy(<val1>);
     * MrnPlaphy plaphyArray[] = plaphy.get();</pre>
     * will get the MrnPlaphy record where code = <val1>.
     * @return Array of MrnPlaphy (MrnPlaphy[])
     */
    public MrnPlaphy[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnPlaphy plaphyArray[] = 
     *     new MrnPlaphy().get(MrnPlaphy.CODE+"=<val1>");</pre>
     * will get the MrnPlaphy record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnPlaphy (MrnPlaphy[])
     */
    public MrnPlaphy[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnPlaphy plaphyArray[] = 
     *     new MrnPlaphy().get("1=1",plaphy.CODE);</pre>
     * will get the all the MrnPlaphy records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlaphy (MrnPlaphy[])
     */
    public MrnPlaphy[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnPlaphy.CODE,MrnPlaphy.STATION_ID;
     * String where = MrnPlaphy.CODE + "=<val1";
     * String order = MrnPlaphy.CODE;
     * MrnPlaphy plaphyArray[] = 
     *     new MrnPlaphy().get(columns, where, order);</pre>
     * will get the code and stationId colums of all MrnPlaphy records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnPlaphy (MrnPlaphy[])
     */
    public MrnPlaphy[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnPlaphy.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnPlaphy[] doGet(Vector result) {
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
        int meshszCol       = db.getColNumber(MESHSZ);
        int ddbiomCol       = db.getColNumber(DDBIOM);
        int netfloCol       = db.getColNumber(NETFLO);
        int phaeopCol       = db.getColNumber(PHAEOP);
        int phycelCol       = db.getColNumber(PHYCEL);
        int prodtyCol       = db.getColNumber(PRODTY);
        int towattCol       = db.getColNumber(TOWATT);
        int trawlVolCol     = db.getColNumber(TRAWL_VOL);
        int vpbiomCol       = db.getColNumber(VPBIOM);
        int zoobioCol       = db.getColNumber(ZOOBIO);
        MrnPlaphy[] cArray = new MrnPlaphy[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnPlaphy();
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
            if (meshszCol != -1)
                cArray[i].setMeshsz      ((String) row.elementAt(meshszCol));
            if (ddbiomCol != -1)
                cArray[i].setDdbiom      ((String) row.elementAt(ddbiomCol));
            if (netfloCol != -1)
                cArray[i].setNetflo      ((String) row.elementAt(netfloCol));
            if (phaeopCol != -1)
                cArray[i].setPhaeop      ((String) row.elementAt(phaeopCol));
            if (phycelCol != -1)
                cArray[i].setPhycel      ((String) row.elementAt(phycelCol));
            if (prodtyCol != -1)
                cArray[i].setProdty      ((String) row.elementAt(prodtyCol));
            if (towattCol != -1)
                cArray[i].setTowatt      ((String) row.elementAt(towattCol));
            if (trawlVolCol != -1)
                cArray[i].setTrawlVol    ((String) row.elementAt(trawlVolCol));
            if (vpbiomCol != -1)
                cArray[i].setVpbiom      ((String) row.elementAt(vpbiomCol));
            if (zoobioCol != -1)
                cArray[i].setZoobio      ((String) row.elementAt(zoobioCol));
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
     *     new MrnPlaphy(
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
     *         <val20>).put();</pre>
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
     *     meshsz       = <val11>,
     *     ddbiom       = <val12>,
     *     netflo       = <val13>,
     *     phaeop       = <val14>,
     *     phycel       = <val15>,
     *     prodty       = <val16>,
     *     towatt       = <val17>,
     *     trawlVol     = <val18>,
     *     vpbiom       = <val19>,
     *     zoobio       = <val20>.
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
     * Boolean success = new MrnPlaphy(
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
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
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
     * MrnPlaphy plaphy = new MrnPlaphy();
     * success = plaphy.del(MrnPlaphy.CODE+"=<val1>");</pre>
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
     * update are taken from the MrnPlaphy argument, .e.g.<pre>
     * Boolean success
     * MrnPlaphy updMrnPlaphy = new MrnPlaphy();
     * updMrnPlaphy.setStationId(<val2>);
     * MrnPlaphy whereMrnPlaphy = new MrnPlaphy(<val1>);
     * success = whereMrnPlaphy.upd(updMrnPlaphy);</pre>
     * will update StationId to <val2> for all records where 
     * code = <val1>.
     * @param plaphy  A MrnPlaphy variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlaphy plaphy) {
        return db.update (TABLE, createColVals(plaphy), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnPlaphy updMrnPlaphy = new MrnPlaphy();
     * updMrnPlaphy.setStationId(<val2>);
     * MrnPlaphy whereMrnPlaphy = new MrnPlaphy();
     * success = whereMrnPlaphy.upd(
     *     updMrnPlaphy, MrnPlaphy.CODE+"=<val1>");</pre>
     * will update StationId to <val2> for all records where 
     * code = <val1>.
     * @param  updMrnPlaphy  A MrnPlaphy variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnPlaphy plaphy, String where) {
        return db.update (TABLE, createColVals(plaphy), where);
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
        if (getMeshsz() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MESHSZ + "=" + getMeshsz("");
        } // if getMeshsz
        if (getDdbiom() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DDBIOM + "=" + getDdbiom("");
        } // if getDdbiom
        if (getNetflo() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NETFLO + "=" + getNetflo("");
        } // if getNetflo
        if (getPhaeop() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PHAEOP + "=" + getPhaeop("");
        } // if getPhaeop
        if (getPhycel() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PHYCEL + "=" + getPhycel("");
        } // if getPhycel
        if (getProdty() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PRODTY + "=" + getProdty("");
        } // if getProdty
        if (getTowatt() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TOWATT + "='" + getTowatt() + "'";
        } // if getTowatt
        if (getTrawlVol() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TRAWL_VOL + "=" + getTrawlVol("");
        } // if getTrawlVol
        if (getVpbiom() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + VPBIOM + "=" + getVpbiom("");
        } // if getVpbiom
        if (getZoobio() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ZOOBIO + "=" + getZoobio("");
        } // if getZoobio
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnPlaphy aVar) {
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
        if (aVar.getMeshsz() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MESHSZ +"=";
            colVals += (aVar.getMeshsz() == FLOATNULL2 ?
                "null" : aVar.getMeshsz(""));
        } // if aVar.getMeshsz
        if (aVar.getDdbiom() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DDBIOM +"=";
            colVals += (aVar.getDdbiom() == FLOATNULL2 ?
                "null" : aVar.getDdbiom(""));
        } // if aVar.getDdbiom
        if (aVar.getNetflo() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NETFLO +"=";
            colVals += (aVar.getNetflo() == FLOATNULL2 ?
                "null" : aVar.getNetflo(""));
        } // if aVar.getNetflo
        if (aVar.getPhaeop() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PHAEOP +"=";
            colVals += (aVar.getPhaeop() == FLOATNULL2 ?
                "null" : aVar.getPhaeop(""));
        } // if aVar.getPhaeop
        if (aVar.getPhycel() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PHYCEL +"=";
            colVals += (aVar.getPhycel() == FLOATNULL2 ?
                "null" : aVar.getPhycel(""));
        } // if aVar.getPhycel
        if (aVar.getProdty() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PRODTY +"=";
            colVals += (aVar.getProdty() == FLOATNULL2 ?
                "null" : aVar.getProdty(""));
        } // if aVar.getProdty
        if (aVar.getTowatt() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TOWATT +"=";
            colVals += (aVar.getTowatt().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTowatt() + "'");
        } // if aVar.getTowatt
        if (aVar.getTrawlVol() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TRAWL_VOL +"=";
            colVals += (aVar.getTrawlVol() == INTNULL2 ?
                "null" : aVar.getTrawlVol(""));
        } // if aVar.getTrawlVol
        if (aVar.getVpbiom() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += VPBIOM +"=";
            colVals += (aVar.getVpbiom() == FLOATNULL2 ?
                "null" : aVar.getVpbiom(""));
        } // if aVar.getVpbiom
        if (aVar.getZoobio() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ZOOBIO +"=";
            colVals += (aVar.getZoobio() == FLOATNULL2 ?
                "null" : aVar.getZoobio(""));
        } // if aVar.getZoobio
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
        if (getMeshsz() != FLOATNULL) {
            columns = columns + "," + MESHSZ;
        } // if getMeshsz
        if (getDdbiom() != FLOATNULL) {
            columns = columns + "," + DDBIOM;
        } // if getDdbiom
        if (getNetflo() != FLOATNULL) {
            columns = columns + "," + NETFLO;
        } // if getNetflo
        if (getPhaeop() != FLOATNULL) {
            columns = columns + "," + PHAEOP;
        } // if getPhaeop
        if (getPhycel() != FLOATNULL) {
            columns = columns + "," + PHYCEL;
        } // if getPhycel
        if (getProdty() != FLOATNULL) {
            columns = columns + "," + PRODTY;
        } // if getProdty
        if (getTowatt() != CHARNULL) {
            columns = columns + "," + TOWATT;
        } // if getTowatt
        if (getTrawlVol() != INTNULL) {
            columns = columns + "," + TRAWL_VOL;
        } // if getTrawlVol
        if (getVpbiom() != FLOATNULL) {
            columns = columns + "," + VPBIOM;
        } // if getVpbiom
        if (getZoobio() != FLOATNULL) {
            columns = columns + "," + ZOOBIO;
        } // if getZoobio
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
        if (getMeshsz() != FLOATNULL) {
            values  = values  + "," + getMeshsz("");
        } // if getMeshsz
        if (getDdbiom() != FLOATNULL) {
            values  = values  + "," + getDdbiom("");
        } // if getDdbiom
        if (getNetflo() != FLOATNULL) {
            values  = values  + "," + getNetflo("");
        } // if getNetflo
        if (getPhaeop() != FLOATNULL) {
            values  = values  + "," + getPhaeop("");
        } // if getPhaeop
        if (getPhycel() != FLOATNULL) {
            values  = values  + "," + getPhycel("");
        } // if getPhycel
        if (getProdty() != FLOATNULL) {
            values  = values  + "," + getProdty("");
        } // if getProdty
        if (getTowatt() != CHARNULL) {
            values  = values  + ",'" + getTowatt() + "'";
        } // if getTowatt
        if (getTrawlVol() != INTNULL) {
            values  = values  + "," + getTrawlVol("");
        } // if getTrawlVol
        if (getVpbiom() != FLOATNULL) {
            values  = values  + "," + getVpbiom("");
        } // if getVpbiom
        if (getZoobio() != FLOATNULL) {
            values  = values  + "," + getZoobio("");
        } // if getZoobio
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
            getMeshsz("")       + "|" +
            getDdbiom("")       + "|" +
            getNetflo("")       + "|" +
            getPhaeop("")       + "|" +
            getPhycel("")       + "|" +
            getProdty("")       + "|" +
            getTowatt("")       + "|" +
            getTrawlVol("")     + "|" +
            getVpbiom("")       + "|" +
            getZoobio("")       + "|";
    } // method toString

} // class MrnPlaphy
