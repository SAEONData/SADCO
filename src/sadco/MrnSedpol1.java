package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SEDPOL1 table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnSedpol1 extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SEDPOL1";
    /** The sedphyCode field name */
    public static final String SEDPHY_CODE = "SEDPHY_CODE";
    /** The arsenic field name */
    public static final String ARSENIC = "ARSENIC";
    /** The cadmium field name */
    public static final String CADMIUM = "CADMIUM";
    /** The chromium field name */
    public static final String CHROMIUM = "CHROMIUM";
    /** The cobalt field name */
    public static final String COBALT = "COBALT";
    /** The copper field name */
    public static final String COPPER = "COPPER";
    /** The iron field name */
    public static final String IRON = "IRON";
    /** The lead field name */
    public static final String LEAD = "LEAD";
    /** The manganese field name */
    public static final String MANGANESE = "MANGANESE";
    /** The mercury field name */
    public static final String MERCURY = "MERCURY";
    /** The nickel field name */
    public static final String NICKEL = "NICKEL";
    /** The selenium field name */
    public static final String SELENIUM = "SELENIUM";
    /** The zinc field name */
    public static final String ZINC = "ZINC";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       sedphyCode;
    private float     arsenic;
    private float     cadmium;
    private float     chromium;
    private float     cobalt;
    private float     copper;
    private float     iron;
    private float     lead;
    private float     manganese;
    private float     mercury;
    private float     nickel;
    private float     selenium;
    private float     zinc;

    /** The error variables  */
    private int sedphyCodeError = ERROR_NORMAL;
    private int arsenicError    = ERROR_NORMAL;
    private int cadmiumError    = ERROR_NORMAL;
    private int chromiumError   = ERROR_NORMAL;
    private int cobaltError     = ERROR_NORMAL;
    private int copperError     = ERROR_NORMAL;
    private int ironError       = ERROR_NORMAL;
    private int leadError       = ERROR_NORMAL;
    private int manganeseError  = ERROR_NORMAL;
    private int mercuryError    = ERROR_NORMAL;
    private int nickelError     = ERROR_NORMAL;
    private int seleniumError   = ERROR_NORMAL;
    private int zincError       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String sedphyCodeErrorMessage = "";
    private String arsenicErrorMessage    = "";
    private String cadmiumErrorMessage    = "";
    private String chromiumErrorMessage   = "";
    private String cobaltErrorMessage     = "";
    private String copperErrorMessage     = "";
    private String ironErrorMessage       = "";
    private String leadErrorMessage       = "";
    private String manganeseErrorMessage  = "";
    private String mercuryErrorMessage    = "";
    private String nickelErrorMessage     = "";
    private String seleniumErrorMessage   = "";
    private String zincErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       SEDPHY_CODE_MN = INTMIN;
    public static final int       SEDPHY_CODE_MX = INTMAX;
    public static final float     ARSENIC_MN = FLOATMIN;
    public static final float     ARSENIC_MX = FLOATMAX;
    public static final float     CADMIUM_MN = FLOATMIN;
    public static final float     CADMIUM_MX = FLOATMAX;
    public static final float     CHROMIUM_MN = FLOATMIN;
    public static final float     CHROMIUM_MX = FLOATMAX;
    public static final float     COBALT_MN = FLOATMIN;
    public static final float     COBALT_MX = FLOATMAX;
    public static final float     COPPER_MN = FLOATMIN;
    public static final float     COPPER_MX = FLOATMAX;
    public static final float     IRON_MN = FLOATMIN;
    public static final float     IRON_MX = FLOATMAX;
    public static final float     LEAD_MN = FLOATMIN;
    public static final float     LEAD_MX = FLOATMAX;
    public static final float     MANGANESE_MN = FLOATMIN;
    public static final float     MANGANESE_MX = FLOATMAX;
    public static final float     MERCURY_MN = FLOATMIN;
    public static final float     MERCURY_MX = FLOATMAX;
    public static final float     NICKEL_MN = FLOATMIN;
    public static final float     NICKEL_MX = FLOATMAX;
    public static final float     SELENIUM_MN = FLOATMIN;
    public static final float     SELENIUM_MX = FLOATMAX;
    public static final float     ZINC_MN = FLOATMIN;
    public static final float     ZINC_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception sedphyCodeOutOfBoundsException =
        new Exception ("'sedphyCode' out of bounds: " +
            SEDPHY_CODE_MN + " - " + SEDPHY_CODE_MX);
    Exception arsenicOutOfBoundsException =
        new Exception ("'arsenic' out of bounds: " +
            ARSENIC_MN + " - " + ARSENIC_MX);
    Exception cadmiumOutOfBoundsException =
        new Exception ("'cadmium' out of bounds: " +
            CADMIUM_MN + " - " + CADMIUM_MX);
    Exception chromiumOutOfBoundsException =
        new Exception ("'chromium' out of bounds: " +
            CHROMIUM_MN + " - " + CHROMIUM_MX);
    Exception cobaltOutOfBoundsException =
        new Exception ("'cobalt' out of bounds: " +
            COBALT_MN + " - " + COBALT_MX);
    Exception copperOutOfBoundsException =
        new Exception ("'copper' out of bounds: " +
            COPPER_MN + " - " + COPPER_MX);
    Exception ironOutOfBoundsException =
        new Exception ("'iron' out of bounds: " +
            IRON_MN + " - " + IRON_MX);
    Exception leadOutOfBoundsException =
        new Exception ("'lead' out of bounds: " +
            LEAD_MN + " - " + LEAD_MX);
    Exception manganeseOutOfBoundsException =
        new Exception ("'manganese' out of bounds: " +
            MANGANESE_MN + " - " + MANGANESE_MX);
    Exception mercuryOutOfBoundsException =
        new Exception ("'mercury' out of bounds: " +
            MERCURY_MN + " - " + MERCURY_MX);
    Exception nickelOutOfBoundsException =
        new Exception ("'nickel' out of bounds: " +
            NICKEL_MN + " - " + NICKEL_MX);
    Exception seleniumOutOfBoundsException =
        new Exception ("'selenium' out of bounds: " +
            SELENIUM_MN + " - " + SELENIUM_MX);
    Exception zincOutOfBoundsException =
        new Exception ("'zinc' out of bounds: " +
            ZINC_MN + " - " + ZINC_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnSedpol1() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnSedpol1 constructor 1"); // debug
    } // MrnSedpol1 constructor

    /**
     * Instantiate a MrnSedpol1 object and initialize the instance variables.
     * @param sedphyCode  The sedphyCode (int)
     */
    public MrnSedpol1(
            int sedphyCode) {
        this();
        setSedphyCode (sedphyCode);
        if (dbg) System.out.println ("<br>in MrnSedpol1 constructor 2"); // debug
    } // MrnSedpol1 constructor

    /**
     * Instantiate a MrnSedpol1 object and initialize the instance variables.
     * @param sedphyCode  The sedphyCode (int)
     * @param arsenic     The arsenic    (float)
     * @param cadmium     The cadmium    (float)
     * @param chromium    The chromium   (float)
     * @param cobalt      The cobalt     (float)
     * @param copper      The copper     (float)
     * @param iron        The iron       (float)
     * @param lead        The lead       (float)
     * @param manganese   The manganese  (float)
     * @param mercury     The mercury    (float)
     * @param nickel      The nickel     (float)
     * @param selenium    The selenium   (float)
     * @param zinc        The zinc       (float)
     */
    public MrnSedpol1(
            int   sedphyCode,
            float arsenic,
            float cadmium,
            float chromium,
            float cobalt,
            float copper,
            float iron,
            float lead,
            float manganese,
            float mercury,
            float nickel,
            float selenium,
            float zinc) {
        this();
        setSedphyCode (sedphyCode);
        setArsenic    (arsenic   );
        setCadmium    (cadmium   );
        setChromium   (chromium  );
        setCobalt     (cobalt    );
        setCopper     (copper    );
        setIron       (iron      );
        setLead       (lead      );
        setManganese  (manganese );
        setMercury    (mercury   );
        setNickel     (nickel    );
        setSelenium   (selenium  );
        setZinc       (zinc      );
        if (dbg) System.out.println ("<br>in MrnSedpol1 constructor 3"); // debug
    } // MrnSedpol1 constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setSedphyCode (INTNULL  );
        setArsenic    (FLOATNULL);
        setCadmium    (FLOATNULL);
        setChromium   (FLOATNULL);
        setCobalt     (FLOATNULL);
        setCopper     (FLOATNULL);
        setIron       (FLOATNULL);
        setLead       (FLOATNULL);
        setManganese  (FLOATNULL);
        setMercury    (FLOATNULL);
        setNickel     (FLOATNULL);
        setSelenium   (FLOATNULL);
        setZinc       (FLOATNULL);
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
     * Set the 'arsenic' class variable
     * @param  arsenic (float)
     */
    public int setArsenic(float arsenic) {
        try {
            if ( ((arsenic == FLOATNULL) ||
                  (arsenic == FLOATNULL2)) ||
                !((arsenic < ARSENIC_MN) ||
                  (arsenic > ARSENIC_MX)) ) {
                this.arsenic = arsenic;
                arsenicError = ERROR_NORMAL;
            } else {
                throw arsenicOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setArsenicError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return arsenicError;
    } // method setArsenic

    /**
     * Set the 'arsenic' class variable
     * @param  arsenic (Integer)
     */
    public int setArsenic(Integer arsenic) {
        try {
            setArsenic(arsenic.floatValue());
        } catch (Exception e) {
            setArsenicError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return arsenicError;
    } // method setArsenic

    /**
     * Set the 'arsenic' class variable
     * @param  arsenic (Float)
     */
    public int setArsenic(Float arsenic) {
        try {
            setArsenic(arsenic.floatValue());
        } catch (Exception e) {
            setArsenicError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return arsenicError;
    } // method setArsenic

    /**
     * Set the 'arsenic' class variable
     * @param  arsenic (String)
     */
    public int setArsenic(String arsenic) {
        try {
            setArsenic(new Float(arsenic).floatValue());
        } catch (Exception e) {
            setArsenicError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return arsenicError;
    } // method setArsenic

    /**
     * Called when an exception has occured
     * @param  arsenic (String)
     */
    private void setArsenicError (float arsenic, Exception e, int error) {
        this.arsenic = arsenic;
        arsenicErrorMessage = e.toString();
        arsenicError = error;
    } // method setArsenicError


    /**
     * Set the 'cadmium' class variable
     * @param  cadmium (float)
     */
    public int setCadmium(float cadmium) {
        try {
            if ( ((cadmium == FLOATNULL) ||
                  (cadmium == FLOATNULL2)) ||
                !((cadmium < CADMIUM_MN) ||
                  (cadmium > CADMIUM_MX)) ) {
                this.cadmium = cadmium;
                cadmiumError = ERROR_NORMAL;
            } else {
                throw cadmiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCadmiumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return cadmiumError;
    } // method setCadmium

    /**
     * Set the 'cadmium' class variable
     * @param  cadmium (Integer)
     */
    public int setCadmium(Integer cadmium) {
        try {
            setCadmium(cadmium.floatValue());
        } catch (Exception e) {
            setCadmiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return cadmiumError;
    } // method setCadmium

    /**
     * Set the 'cadmium' class variable
     * @param  cadmium (Float)
     */
    public int setCadmium(Float cadmium) {
        try {
            setCadmium(cadmium.floatValue());
        } catch (Exception e) {
            setCadmiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return cadmiumError;
    } // method setCadmium

    /**
     * Set the 'cadmium' class variable
     * @param  cadmium (String)
     */
    public int setCadmium(String cadmium) {
        try {
            setCadmium(new Float(cadmium).floatValue());
        } catch (Exception e) {
            setCadmiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return cadmiumError;
    } // method setCadmium

    /**
     * Called when an exception has occured
     * @param  cadmium (String)
     */
    private void setCadmiumError (float cadmium, Exception e, int error) {
        this.cadmium = cadmium;
        cadmiumErrorMessage = e.toString();
        cadmiumError = error;
    } // method setCadmiumError


    /**
     * Set the 'chromium' class variable
     * @param  chromium (float)
     */
    public int setChromium(float chromium) {
        try {
            if ( ((chromium == FLOATNULL) ||
                  (chromium == FLOATNULL2)) ||
                !((chromium < CHROMIUM_MN) ||
                  (chromium > CHROMIUM_MX)) ) {
                this.chromium = chromium;
                chromiumError = ERROR_NORMAL;
            } else {
                throw chromiumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setChromiumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return chromiumError;
    } // method setChromium

    /**
     * Set the 'chromium' class variable
     * @param  chromium (Integer)
     */
    public int setChromium(Integer chromium) {
        try {
            setChromium(chromium.floatValue());
        } catch (Exception e) {
            setChromiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chromiumError;
    } // method setChromium

    /**
     * Set the 'chromium' class variable
     * @param  chromium (Float)
     */
    public int setChromium(Float chromium) {
        try {
            setChromium(chromium.floatValue());
        } catch (Exception e) {
            setChromiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chromiumError;
    } // method setChromium

    /**
     * Set the 'chromium' class variable
     * @param  chromium (String)
     */
    public int setChromium(String chromium) {
        try {
            setChromium(new Float(chromium).floatValue());
        } catch (Exception e) {
            setChromiumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return chromiumError;
    } // method setChromium

    /**
     * Called when an exception has occured
     * @param  chromium (String)
     */
    private void setChromiumError (float chromium, Exception e, int error) {
        this.chromium = chromium;
        chromiumErrorMessage = e.toString();
        chromiumError = error;
    } // method setChromiumError


    /**
     * Set the 'cobalt' class variable
     * @param  cobalt (float)
     */
    public int setCobalt(float cobalt) {
        try {
            if ( ((cobalt == FLOATNULL) ||
                  (cobalt == FLOATNULL2)) ||
                !((cobalt < COBALT_MN) ||
                  (cobalt > COBALT_MX)) ) {
                this.cobalt = cobalt;
                cobaltError = ERROR_NORMAL;
            } else {
                throw cobaltOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCobaltError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return cobaltError;
    } // method setCobalt

    /**
     * Set the 'cobalt' class variable
     * @param  cobalt (Integer)
     */
    public int setCobalt(Integer cobalt) {
        try {
            setCobalt(cobalt.floatValue());
        } catch (Exception e) {
            setCobaltError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return cobaltError;
    } // method setCobalt

    /**
     * Set the 'cobalt' class variable
     * @param  cobalt (Float)
     */
    public int setCobalt(Float cobalt) {
        try {
            setCobalt(cobalt.floatValue());
        } catch (Exception e) {
            setCobaltError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return cobaltError;
    } // method setCobalt

    /**
     * Set the 'cobalt' class variable
     * @param  cobalt (String)
     */
    public int setCobalt(String cobalt) {
        try {
            setCobalt(new Float(cobalt).floatValue());
        } catch (Exception e) {
            setCobaltError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return cobaltError;
    } // method setCobalt

    /**
     * Called when an exception has occured
     * @param  cobalt (String)
     */
    private void setCobaltError (float cobalt, Exception e, int error) {
        this.cobalt = cobalt;
        cobaltErrorMessage = e.toString();
        cobaltError = error;
    } // method setCobaltError


    /**
     * Set the 'copper' class variable
     * @param  copper (float)
     */
    public int setCopper(float copper) {
        try {
            if ( ((copper == FLOATNULL) ||
                  (copper == FLOATNULL2)) ||
                !((copper < COPPER_MN) ||
                  (copper > COPPER_MX)) ) {
                this.copper = copper;
                copperError = ERROR_NORMAL;
            } else {
                throw copperOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setCopperError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return copperError;
    } // method setCopper

    /**
     * Set the 'copper' class variable
     * @param  copper (Integer)
     */
    public int setCopper(Integer copper) {
        try {
            setCopper(copper.floatValue());
        } catch (Exception e) {
            setCopperError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return copperError;
    } // method setCopper

    /**
     * Set the 'copper' class variable
     * @param  copper (Float)
     */
    public int setCopper(Float copper) {
        try {
            setCopper(copper.floatValue());
        } catch (Exception e) {
            setCopperError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return copperError;
    } // method setCopper

    /**
     * Set the 'copper' class variable
     * @param  copper (String)
     */
    public int setCopper(String copper) {
        try {
            setCopper(new Float(copper).floatValue());
        } catch (Exception e) {
            setCopperError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return copperError;
    } // method setCopper

    /**
     * Called when an exception has occured
     * @param  copper (String)
     */
    private void setCopperError (float copper, Exception e, int error) {
        this.copper = copper;
        copperErrorMessage = e.toString();
        copperError = error;
    } // method setCopperError


    /**
     * Set the 'iron' class variable
     * @param  iron (float)
     */
    public int setIron(float iron) {
        try {
            if ( ((iron == FLOATNULL) ||
                  (iron == FLOATNULL2)) ||
                !((iron < IRON_MN) ||
                  (iron > IRON_MX)) ) {
                this.iron = iron;
                ironError = ERROR_NORMAL;
            } else {
                throw ironOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setIronError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return ironError;
    } // method setIron

    /**
     * Set the 'iron' class variable
     * @param  iron (Integer)
     */
    public int setIron(Integer iron) {
        try {
            setIron(iron.floatValue());
        } catch (Exception e) {
            setIronError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ironError;
    } // method setIron

    /**
     * Set the 'iron' class variable
     * @param  iron (Float)
     */
    public int setIron(Float iron) {
        try {
            setIron(iron.floatValue());
        } catch (Exception e) {
            setIronError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ironError;
    } // method setIron

    /**
     * Set the 'iron' class variable
     * @param  iron (String)
     */
    public int setIron(String iron) {
        try {
            setIron(new Float(iron).floatValue());
        } catch (Exception e) {
            setIronError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return ironError;
    } // method setIron

    /**
     * Called when an exception has occured
     * @param  iron (String)
     */
    private void setIronError (float iron, Exception e, int error) {
        this.iron = iron;
        ironErrorMessage = e.toString();
        ironError = error;
    } // method setIronError


    /**
     * Set the 'lead' class variable
     * @param  lead (float)
     */
    public int setLead(float lead) {
        try {
            if ( ((lead == FLOATNULL) ||
                  (lead == FLOATNULL2)) ||
                !((lead < LEAD_MN) ||
                  (lead > LEAD_MX)) ) {
                this.lead = lead;
                leadError = ERROR_NORMAL;
            } else {
                throw leadOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLeadError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return leadError;
    } // method setLead

    /**
     * Set the 'lead' class variable
     * @param  lead (Integer)
     */
    public int setLead(Integer lead) {
        try {
            setLead(lead.floatValue());
        } catch (Exception e) {
            setLeadError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return leadError;
    } // method setLead

    /**
     * Set the 'lead' class variable
     * @param  lead (Float)
     */
    public int setLead(Float lead) {
        try {
            setLead(lead.floatValue());
        } catch (Exception e) {
            setLeadError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return leadError;
    } // method setLead

    /**
     * Set the 'lead' class variable
     * @param  lead (String)
     */
    public int setLead(String lead) {
        try {
            setLead(new Float(lead).floatValue());
        } catch (Exception e) {
            setLeadError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return leadError;
    } // method setLead

    /**
     * Called when an exception has occured
     * @param  lead (String)
     */
    private void setLeadError (float lead, Exception e, int error) {
        this.lead = lead;
        leadErrorMessage = e.toString();
        leadError = error;
    } // method setLeadError


    /**
     * Set the 'manganese' class variable
     * @param  manganese (float)
     */
    public int setManganese(float manganese) {
        try {
            if ( ((manganese == FLOATNULL) ||
                  (manganese == FLOATNULL2)) ||
                !((manganese < MANGANESE_MN) ||
                  (manganese > MANGANESE_MX)) ) {
                this.manganese = manganese;
                manganeseError = ERROR_NORMAL;
            } else {
                throw manganeseOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setManganeseError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return manganeseError;
    } // method setManganese

    /**
     * Set the 'manganese' class variable
     * @param  manganese (Integer)
     */
    public int setManganese(Integer manganese) {
        try {
            setManganese(manganese.floatValue());
        } catch (Exception e) {
            setManganeseError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return manganeseError;
    } // method setManganese

    /**
     * Set the 'manganese' class variable
     * @param  manganese (Float)
     */
    public int setManganese(Float manganese) {
        try {
            setManganese(manganese.floatValue());
        } catch (Exception e) {
            setManganeseError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return manganeseError;
    } // method setManganese

    /**
     * Set the 'manganese' class variable
     * @param  manganese (String)
     */
    public int setManganese(String manganese) {
        try {
            setManganese(new Float(manganese).floatValue());
        } catch (Exception e) {
            setManganeseError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return manganeseError;
    } // method setManganese

    /**
     * Called when an exception has occured
     * @param  manganese (String)
     */
    private void setManganeseError (float manganese, Exception e, int error) {
        this.manganese = manganese;
        manganeseErrorMessage = e.toString();
        manganeseError = error;
    } // method setManganeseError


    /**
     * Set the 'mercury' class variable
     * @param  mercury (float)
     */
    public int setMercury(float mercury) {
        try {
            if ( ((mercury == FLOATNULL) ||
                  (mercury == FLOATNULL2)) ||
                !((mercury < MERCURY_MN) ||
                  (mercury > MERCURY_MX)) ) {
                this.mercury = mercury;
                mercuryError = ERROR_NORMAL;
            } else {
                throw mercuryOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMercuryError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return mercuryError;
    } // method setMercury

    /**
     * Set the 'mercury' class variable
     * @param  mercury (Integer)
     */
    public int setMercury(Integer mercury) {
        try {
            setMercury(mercury.floatValue());
        } catch (Exception e) {
            setMercuryError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return mercuryError;
    } // method setMercury

    /**
     * Set the 'mercury' class variable
     * @param  mercury (Float)
     */
    public int setMercury(Float mercury) {
        try {
            setMercury(mercury.floatValue());
        } catch (Exception e) {
            setMercuryError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return mercuryError;
    } // method setMercury

    /**
     * Set the 'mercury' class variable
     * @param  mercury (String)
     */
    public int setMercury(String mercury) {
        try {
            setMercury(new Float(mercury).floatValue());
        } catch (Exception e) {
            setMercuryError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return mercuryError;
    } // method setMercury

    /**
     * Called when an exception has occured
     * @param  mercury (String)
     */
    private void setMercuryError (float mercury, Exception e, int error) {
        this.mercury = mercury;
        mercuryErrorMessage = e.toString();
        mercuryError = error;
    } // method setMercuryError


    /**
     * Set the 'nickel' class variable
     * @param  nickel (float)
     */
    public int setNickel(float nickel) {
        try {
            if ( ((nickel == FLOATNULL) ||
                  (nickel == FLOATNULL2)) ||
                !((nickel < NICKEL_MN) ||
                  (nickel > NICKEL_MX)) ) {
                this.nickel = nickel;
                nickelError = ERROR_NORMAL;
            } else {
                throw nickelOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNickelError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return nickelError;
    } // method setNickel

    /**
     * Set the 'nickel' class variable
     * @param  nickel (Integer)
     */
    public int setNickel(Integer nickel) {
        try {
            setNickel(nickel.floatValue());
        } catch (Exception e) {
            setNickelError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nickelError;
    } // method setNickel

    /**
     * Set the 'nickel' class variable
     * @param  nickel (Float)
     */
    public int setNickel(Float nickel) {
        try {
            setNickel(nickel.floatValue());
        } catch (Exception e) {
            setNickelError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nickelError;
    } // method setNickel

    /**
     * Set the 'nickel' class variable
     * @param  nickel (String)
     */
    public int setNickel(String nickel) {
        try {
            setNickel(new Float(nickel).floatValue());
        } catch (Exception e) {
            setNickelError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return nickelError;
    } // method setNickel

    /**
     * Called when an exception has occured
     * @param  nickel (String)
     */
    private void setNickelError (float nickel, Exception e, int error) {
        this.nickel = nickel;
        nickelErrorMessage = e.toString();
        nickelError = error;
    } // method setNickelError


    /**
     * Set the 'selenium' class variable
     * @param  selenium (float)
     */
    public int setSelenium(float selenium) {
        try {
            if ( ((selenium == FLOATNULL) ||
                  (selenium == FLOATNULL2)) ||
                !((selenium < SELENIUM_MN) ||
                  (selenium > SELENIUM_MX)) ) {
                this.selenium = selenium;
                seleniumError = ERROR_NORMAL;
            } else {
                throw seleniumOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSeleniumError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return seleniumError;
    } // method setSelenium

    /**
     * Set the 'selenium' class variable
     * @param  selenium (Integer)
     */
    public int setSelenium(Integer selenium) {
        try {
            setSelenium(selenium.floatValue());
        } catch (Exception e) {
            setSeleniumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return seleniumError;
    } // method setSelenium

    /**
     * Set the 'selenium' class variable
     * @param  selenium (Float)
     */
    public int setSelenium(Float selenium) {
        try {
            setSelenium(selenium.floatValue());
        } catch (Exception e) {
            setSeleniumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return seleniumError;
    } // method setSelenium

    /**
     * Set the 'selenium' class variable
     * @param  selenium (String)
     */
    public int setSelenium(String selenium) {
        try {
            setSelenium(new Float(selenium).floatValue());
        } catch (Exception e) {
            setSeleniumError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return seleniumError;
    } // method setSelenium

    /**
     * Called when an exception has occured
     * @param  selenium (String)
     */
    private void setSeleniumError (float selenium, Exception e, int error) {
        this.selenium = selenium;
        seleniumErrorMessage = e.toString();
        seleniumError = error;
    } // method setSeleniumError


    /**
     * Set the 'zinc' class variable
     * @param  zinc (float)
     */
    public int setZinc(float zinc) {
        try {
            if ( ((zinc == FLOATNULL) ||
                  (zinc == FLOATNULL2)) ||
                !((zinc < ZINC_MN) ||
                  (zinc > ZINC_MX)) ) {
                this.zinc = zinc;
                zincError = ERROR_NORMAL;
            } else {
                throw zincOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setZincError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return zincError;
    } // method setZinc

    /**
     * Set the 'zinc' class variable
     * @param  zinc (Integer)
     */
    public int setZinc(Integer zinc) {
        try {
            setZinc(zinc.floatValue());
        } catch (Exception e) {
            setZincError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return zincError;
    } // method setZinc

    /**
     * Set the 'zinc' class variable
     * @param  zinc (Float)
     */
    public int setZinc(Float zinc) {
        try {
            setZinc(zinc.floatValue());
        } catch (Exception e) {
            setZincError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return zincError;
    } // method setZinc

    /**
     * Set the 'zinc' class variable
     * @param  zinc (String)
     */
    public int setZinc(String zinc) {
        try {
            setZinc(new Float(zinc).floatValue());
        } catch (Exception e) {
            setZincError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return zincError;
    } // method setZinc

    /**
     * Called when an exception has occured
     * @param  zinc (String)
     */
    private void setZincError (float zinc, Exception e, int error) {
        this.zinc = zinc;
        zincErrorMessage = e.toString();
        zincError = error;
    } // method setZincError


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
     * Return the 'arsenic' class variable
     * @return arsenic (float)
     */
    public float getArsenic() {
        return arsenic;
    } // method getArsenic

    /**
     * Return the 'arsenic' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getArsenic methods
     * @return arsenic (String)
     */
    public String getArsenic(String s) {
        return ((arsenic != FLOATNULL) ? new Float(arsenic).toString() : "");
    } // method getArsenic


    /**
     * Return the 'cadmium' class variable
     * @return cadmium (float)
     */
    public float getCadmium() {
        return cadmium;
    } // method getCadmium

    /**
     * Return the 'cadmium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCadmium methods
     * @return cadmium (String)
     */
    public String getCadmium(String s) {
        return ((cadmium != FLOATNULL) ? new Float(cadmium).toString() : "");
    } // method getCadmium


    /**
     * Return the 'chromium' class variable
     * @return chromium (float)
     */
    public float getChromium() {
        return chromium;
    } // method getChromium

    /**
     * Return the 'chromium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getChromium methods
     * @return chromium (String)
     */
    public String getChromium(String s) {
        return ((chromium != FLOATNULL) ? new Float(chromium).toString() : "");
    } // method getChromium


    /**
     * Return the 'cobalt' class variable
     * @return cobalt (float)
     */
    public float getCobalt() {
        return cobalt;
    } // method getCobalt

    /**
     * Return the 'cobalt' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCobalt methods
     * @return cobalt (String)
     */
    public String getCobalt(String s) {
        return ((cobalt != FLOATNULL) ? new Float(cobalt).toString() : "");
    } // method getCobalt


    /**
     * Return the 'copper' class variable
     * @return copper (float)
     */
    public float getCopper() {
        return copper;
    } // method getCopper

    /**
     * Return the 'copper' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getCopper methods
     * @return copper (String)
     */
    public String getCopper(String s) {
        return ((copper != FLOATNULL) ? new Float(copper).toString() : "");
    } // method getCopper


    /**
     * Return the 'iron' class variable
     * @return iron (float)
     */
    public float getIron() {
        return iron;
    } // method getIron

    /**
     * Return the 'iron' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getIron methods
     * @return iron (String)
     */
    public String getIron(String s) {
        return ((iron != FLOATNULL) ? new Float(iron).toString() : "");
    } // method getIron


    /**
     * Return the 'lead' class variable
     * @return lead (float)
     */
    public float getLead() {
        return lead;
    } // method getLead

    /**
     * Return the 'lead' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLead methods
     * @return lead (String)
     */
    public String getLead(String s) {
        return ((lead != FLOATNULL) ? new Float(lead).toString() : "");
    } // method getLead


    /**
     * Return the 'manganese' class variable
     * @return manganese (float)
     */
    public float getManganese() {
        return manganese;
    } // method getManganese

    /**
     * Return the 'manganese' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getManganese methods
     * @return manganese (String)
     */
    public String getManganese(String s) {
        return ((manganese != FLOATNULL) ? new Float(manganese).toString() : "");
    } // method getManganese


    /**
     * Return the 'mercury' class variable
     * @return mercury (float)
     */
    public float getMercury() {
        return mercury;
    } // method getMercury

    /**
     * Return the 'mercury' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMercury methods
     * @return mercury (String)
     */
    public String getMercury(String s) {
        return ((mercury != FLOATNULL) ? new Float(mercury).toString() : "");
    } // method getMercury


    /**
     * Return the 'nickel' class variable
     * @return nickel (float)
     */
    public float getNickel() {
        return nickel;
    } // method getNickel

    /**
     * Return the 'nickel' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNickel methods
     * @return nickel (String)
     */
    public String getNickel(String s) {
        return ((nickel != FLOATNULL) ? new Float(nickel).toString() : "");
    } // method getNickel


    /**
     * Return the 'selenium' class variable
     * @return selenium (float)
     */
    public float getSelenium() {
        return selenium;
    } // method getSelenium

    /**
     * Return the 'selenium' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSelenium methods
     * @return selenium (String)
     */
    public String getSelenium(String s) {
        return ((selenium != FLOATNULL) ? new Float(selenium).toString() : "");
    } // method getSelenium


    /**
     * Return the 'zinc' class variable
     * @return zinc (float)
     */
    public float getZinc() {
        return zinc;
    } // method getZinc

    /**
     * Return the 'zinc' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getZinc methods
     * @return zinc (String)
     */
    public String getZinc(String s) {
        return ((zinc != FLOATNULL) ? new Float(zinc).toString() : "");
    } // method getZinc


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
            (arsenic == FLOATNULL) &&
            (cadmium == FLOATNULL) &&
            (chromium == FLOATNULL) &&
            (cobalt == FLOATNULL) &&
            (copper == FLOATNULL) &&
            (iron == FLOATNULL) &&
            (lead == FLOATNULL) &&
            (manganese == FLOATNULL) &&
            (mercury == FLOATNULL) &&
            (nickel == FLOATNULL) &&
            (selenium == FLOATNULL) &&
            (zinc == FLOATNULL)) {
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
            arsenicError +
            cadmiumError +
            chromiumError +
            cobaltError +
            copperError +
            ironError +
            leadError +
            manganeseError +
            mercuryError +
            nickelError +
            seleniumError +
            zincError;
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
     * Gets the errorcode for the arsenic instance variable
     * @return errorcode (int)
     */
    public int getArsenicError() {
        return arsenicError;
    } // method getArsenicError

    /**
     * Gets the errorMessage for the arsenic instance variable
     * @return errorMessage (String)
     */
    public String getArsenicErrorMessage() {
        return arsenicErrorMessage;
    } // method getArsenicErrorMessage


    /**
     * Gets the errorcode for the cadmium instance variable
     * @return errorcode (int)
     */
    public int getCadmiumError() {
        return cadmiumError;
    } // method getCadmiumError

    /**
     * Gets the errorMessage for the cadmium instance variable
     * @return errorMessage (String)
     */
    public String getCadmiumErrorMessage() {
        return cadmiumErrorMessage;
    } // method getCadmiumErrorMessage


    /**
     * Gets the errorcode for the chromium instance variable
     * @return errorcode (int)
     */
    public int getChromiumError() {
        return chromiumError;
    } // method getChromiumError

    /**
     * Gets the errorMessage for the chromium instance variable
     * @return errorMessage (String)
     */
    public String getChromiumErrorMessage() {
        return chromiumErrorMessage;
    } // method getChromiumErrorMessage


    /**
     * Gets the errorcode for the cobalt instance variable
     * @return errorcode (int)
     */
    public int getCobaltError() {
        return cobaltError;
    } // method getCobaltError

    /**
     * Gets the errorMessage for the cobalt instance variable
     * @return errorMessage (String)
     */
    public String getCobaltErrorMessage() {
        return cobaltErrorMessage;
    } // method getCobaltErrorMessage


    /**
     * Gets the errorcode for the copper instance variable
     * @return errorcode (int)
     */
    public int getCopperError() {
        return copperError;
    } // method getCopperError

    /**
     * Gets the errorMessage for the copper instance variable
     * @return errorMessage (String)
     */
    public String getCopperErrorMessage() {
        return copperErrorMessage;
    } // method getCopperErrorMessage


    /**
     * Gets the errorcode for the iron instance variable
     * @return errorcode (int)
     */
    public int getIronError() {
        return ironError;
    } // method getIronError

    /**
     * Gets the errorMessage for the iron instance variable
     * @return errorMessage (String)
     */
    public String getIronErrorMessage() {
        return ironErrorMessage;
    } // method getIronErrorMessage


    /**
     * Gets the errorcode for the lead instance variable
     * @return errorcode (int)
     */
    public int getLeadError() {
        return leadError;
    } // method getLeadError

    /**
     * Gets the errorMessage for the lead instance variable
     * @return errorMessage (String)
     */
    public String getLeadErrorMessage() {
        return leadErrorMessage;
    } // method getLeadErrorMessage


    /**
     * Gets the errorcode for the manganese instance variable
     * @return errorcode (int)
     */
    public int getManganeseError() {
        return manganeseError;
    } // method getManganeseError

    /**
     * Gets the errorMessage for the manganese instance variable
     * @return errorMessage (String)
     */
    public String getManganeseErrorMessage() {
        return manganeseErrorMessage;
    } // method getManganeseErrorMessage


    /**
     * Gets the errorcode for the mercury instance variable
     * @return errorcode (int)
     */
    public int getMercuryError() {
        return mercuryError;
    } // method getMercuryError

    /**
     * Gets the errorMessage for the mercury instance variable
     * @return errorMessage (String)
     */
    public String getMercuryErrorMessage() {
        return mercuryErrorMessage;
    } // method getMercuryErrorMessage


    /**
     * Gets the errorcode for the nickel instance variable
     * @return errorcode (int)
     */
    public int getNickelError() {
        return nickelError;
    } // method getNickelError

    /**
     * Gets the errorMessage for the nickel instance variable
     * @return errorMessage (String)
     */
    public String getNickelErrorMessage() {
        return nickelErrorMessage;
    } // method getNickelErrorMessage


    /**
     * Gets the errorcode for the selenium instance variable
     * @return errorcode (int)
     */
    public int getSeleniumError() {
        return seleniumError;
    } // method getSeleniumError

    /**
     * Gets the errorMessage for the selenium instance variable
     * @return errorMessage (String)
     */
    public String getSeleniumErrorMessage() {
        return seleniumErrorMessage;
    } // method getSeleniumErrorMessage


    /**
     * Gets the errorcode for the zinc instance variable
     * @return errorcode (int)
     */
    public int getZincError() {
        return zincError;
    } // method getZincError

    /**
     * Gets the errorMessage for the zinc instance variable
     * @return errorMessage (String)
     */
    public String getZincErrorMessage() {
        return zincErrorMessage;
    } // method getZincErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnSedpol1. e.g.<pre>
     * MrnSedpol1 sedpol1 = new MrnSedpol1(<val1>);
     * MrnSedpol1 sedpol1Array[] = sedpol1.get();</pre>
     * will get the MrnSedpol1 record where sedphyCode = <val1>.
     * @return Array of MrnSedpol1 (MrnSedpol1[])
     */
    public MrnSedpol1[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnSedpol1 sedpol1Array[] =
     *     new MrnSedpol1().get(MrnSedpol1.SEDPHY_CODE+"=<val1>");</pre>
     * will get the MrnSedpol1 record where sedphyCode = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnSedpol1 (MrnSedpol1[])
     */
    public MrnSedpol1[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnSedpol1 sedpol1Array[] =
     *     new MrnSedpol1().get("1=1",sedpol1.SEDPHY_CODE);</pre>
     * will get the all the MrnSedpol1 records, and order them by sedphyCode.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSedpol1 (MrnSedpol1[])
     */
    public MrnSedpol1[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnSedpol1.SEDPHY_CODE,MrnSedpol1.ARSENIC;
     * String where = MrnSedpol1.SEDPHY_CODE + "=<val1";
     * String order = MrnSedpol1.SEDPHY_CODE;
     * MrnSedpol1 sedpol1Array[] =
     *     new MrnSedpol1().get(columns, where, order);</pre>
     * will get the sedphyCode and arsenic colums of all MrnSedpol1 records,
     * where sedphyCode = <val1>, and order them by sedphyCode.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnSedpol1 (MrnSedpol1[])
     */
    public MrnSedpol1[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnSedpol1.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnSedpol1[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int sedphyCodeCol = db.getColNumber(SEDPHY_CODE);
        int arsenicCol    = db.getColNumber(ARSENIC);
        int cadmiumCol    = db.getColNumber(CADMIUM);
        int chromiumCol   = db.getColNumber(CHROMIUM);
        int cobaltCol     = db.getColNumber(COBALT);
        int copperCol     = db.getColNumber(COPPER);
        int ironCol       = db.getColNumber(IRON);
        int leadCol       = db.getColNumber(LEAD);
        int manganeseCol  = db.getColNumber(MANGANESE);
        int mercuryCol    = db.getColNumber(MERCURY);
        int nickelCol     = db.getColNumber(NICKEL);
        int seleniumCol   = db.getColNumber(SELENIUM);
        int zincCol       = db.getColNumber(ZINC);
        MrnSedpol1[] cArray = new MrnSedpol1[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnSedpol1();
            if (sedphyCodeCol != -1)
                cArray[i].setSedphyCode((String) row.elementAt(sedphyCodeCol));
            if (arsenicCol != -1)
                cArray[i].setArsenic   ((String) row.elementAt(arsenicCol));
            if (cadmiumCol != -1)
                cArray[i].setCadmium   ((String) row.elementAt(cadmiumCol));
            if (chromiumCol != -1)
                cArray[i].setChromium  ((String) row.elementAt(chromiumCol));
            if (cobaltCol != -1)
                cArray[i].setCobalt    ((String) row.elementAt(cobaltCol));
            if (copperCol != -1)
                cArray[i].setCopper    ((String) row.elementAt(copperCol));
            if (ironCol != -1)
                cArray[i].setIron      ((String) row.elementAt(ironCol));
            if (leadCol != -1)
                cArray[i].setLead      ((String) row.elementAt(leadCol));
            if (manganeseCol != -1)
                cArray[i].setManganese ((String) row.elementAt(manganeseCol));
            if (mercuryCol != -1)
                cArray[i].setMercury   ((String) row.elementAt(mercuryCol));
            if (nickelCol != -1)
                cArray[i].setNickel    ((String) row.elementAt(nickelCol));
            if (seleniumCol != -1)
                cArray[i].setSelenium  ((String) row.elementAt(seleniumCol));
            if (zincCol != -1)
                cArray[i].setZinc      ((String) row.elementAt(zincCol));
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
     *     new MrnSedpol1(
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
     *     sedphyCode = <val1>,
     *     arsenic    = <val2>,
     *     cadmium    = <val3>,
     *     chromium   = <val4>,
     *     cobalt     = <val5>,
     *     copper     = <val6>,
     *     iron       = <val7>,
     *     lead       = <val8>,
     *     manganese  = <val9>,
     *     mercury    = <val10>,
     *     nickel     = <val11>,
     *     selenium   = <val12>,
     *     zinc       = <val13>.
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
     * Boolean success = new MrnSedpol1(
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
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where arsenic = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSedpol1 sedpol1 = new MrnSedpol1();
     * success = sedpol1.del(MrnSedpol1.SEDPHY_CODE+"=<val1>");</pre>
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
     * update are taken from the MrnSedpol1 argument, .e.g.<pre>
     * Boolean success
     * MrnSedpol1 updMrnSedpol1 = new MrnSedpol1();
     * updMrnSedpol1.setArsenic(<val2>);
     * MrnSedpol1 whereMrnSedpol1 = new MrnSedpol1(<val1>);
     * success = whereMrnSedpol1.upd(updMrnSedpol1);</pre>
     * will update Arsenic to <val2> for all records where
     * sedphyCode = <val1>.
     * @param  sedpol1  A MrnSedpol1 variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSedpol1 sedpol1) {
        return db.update (TABLE, createColVals(sedpol1), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnSedpol1 updMrnSedpol1 = new MrnSedpol1();
     * updMrnSedpol1.setArsenic(<val2>);
     * MrnSedpol1 whereMrnSedpol1 = new MrnSedpol1();
     * success = whereMrnSedpol1.upd(
     *     updMrnSedpol1, MrnSedpol1.SEDPHY_CODE+"=<val1>");</pre>
     * will update Arsenic to <val2> for all records where
     * sedphyCode = <val1>.
     * @param  sedpol1  A MrnSedpol1 variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnSedpol1 sedpol1, String where) {
        return db.update (TABLE, createColVals(sedpol1), where);
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
        if (getArsenic() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ARSENIC + "=" + getArsenic("");
        } // if getArsenic
        if (getCadmium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CADMIUM + "=" + getCadmium("");
        } // if getCadmium
        if (getChromium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CHROMIUM + "=" + getChromium("");
        } // if getChromium
        if (getCobalt() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + COBALT + "=" + getCobalt("");
        } // if getCobalt
        if (getCopper() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + COPPER + "=" + getCopper("");
        } // if getCopper
        if (getIron() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + IRON + "=" + getIron("");
        } // if getIron
        if (getLead() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LEAD + "=" + getLead("");
        } // if getLead
        if (getManganese() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MANGANESE + "=" + getManganese("");
        } // if getManganese
        if (getMercury() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MERCURY + "=" + getMercury("");
        } // if getMercury
        if (getNickel() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NICKEL + "=" + getNickel("");
        } // if getNickel
        if (getSelenium() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SELENIUM + "=" + getSelenium("");
        } // if getSelenium
        if (getZinc() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ZINC + "=" + getZinc("");
        } // if getZinc
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnSedpol1 aVar) {
        String colVals = "";
        if (aVar.getSedphyCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEDPHY_CODE +"=";
            colVals += (aVar.getSedphyCode() == INTNULL2 ?
                "null" : aVar.getSedphyCode(""));
        } // if aVar.getSedphyCode
        if (aVar.getArsenic() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ARSENIC +"=";
            colVals += (aVar.getArsenic() == FLOATNULL2 ?
                "null" : aVar.getArsenic(""));
        } // if aVar.getArsenic
        if (aVar.getCadmium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CADMIUM +"=";
            colVals += (aVar.getCadmium() == FLOATNULL2 ?
                "null" : aVar.getCadmium(""));
        } // if aVar.getCadmium
        if (aVar.getChromium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CHROMIUM +"=";
            colVals += (aVar.getChromium() == FLOATNULL2 ?
                "null" : aVar.getChromium(""));
        } // if aVar.getChromium
        if (aVar.getCobalt() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += COBALT +"=";
            colVals += (aVar.getCobalt() == FLOATNULL2 ?
                "null" : aVar.getCobalt(""));
        } // if aVar.getCobalt
        if (aVar.getCopper() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += COPPER +"=";
            colVals += (aVar.getCopper() == FLOATNULL2 ?
                "null" : aVar.getCopper(""));
        } // if aVar.getCopper
        if (aVar.getIron() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += IRON +"=";
            colVals += (aVar.getIron() == FLOATNULL2 ?
                "null" : aVar.getIron(""));
        } // if aVar.getIron
        if (aVar.getLead() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LEAD +"=";
            colVals += (aVar.getLead() == FLOATNULL2 ?
                "null" : aVar.getLead(""));
        } // if aVar.getLead
        if (aVar.getManganese() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MANGANESE +"=";
            colVals += (aVar.getManganese() == FLOATNULL2 ?
                "null" : aVar.getManganese(""));
        } // if aVar.getManganese
        if (aVar.getMercury() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MERCURY +"=";
            colVals += (aVar.getMercury() == FLOATNULL2 ?
                "null" : aVar.getMercury(""));
        } // if aVar.getMercury
        if (aVar.getNickel() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NICKEL +"=";
            colVals += (aVar.getNickel() == FLOATNULL2 ?
                "null" : aVar.getNickel(""));
        } // if aVar.getNickel
        if (aVar.getSelenium() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SELENIUM +"=";
            colVals += (aVar.getSelenium() == FLOATNULL2 ?
                "null" : aVar.getSelenium(""));
        } // if aVar.getSelenium
        if (aVar.getZinc() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ZINC +"=";
            colVals += (aVar.getZinc() == FLOATNULL2 ?
                "null" : aVar.getZinc(""));
        } // if aVar.getZinc
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = SEDPHY_CODE;
        if (getArsenic() != FLOATNULL) {
            columns = columns + "," + ARSENIC;
        } // if getArsenic
        if (getCadmium() != FLOATNULL) {
            columns = columns + "," + CADMIUM;
        } // if getCadmium
        if (getChromium() != FLOATNULL) {
            columns = columns + "," + CHROMIUM;
        } // if getChromium
        if (getCobalt() != FLOATNULL) {
            columns = columns + "," + COBALT;
        } // if getCobalt
        if (getCopper() != FLOATNULL) {
            columns = columns + "," + COPPER;
        } // if getCopper
        if (getIron() != FLOATNULL) {
            columns = columns + "," + IRON;
        } // if getIron
        if (getLead() != FLOATNULL) {
            columns = columns + "," + LEAD;
        } // if getLead
        if (getManganese() != FLOATNULL) {
            columns = columns + "," + MANGANESE;
        } // if getManganese
        if (getMercury() != FLOATNULL) {
            columns = columns + "," + MERCURY;
        } // if getMercury
        if (getNickel() != FLOATNULL) {
            columns = columns + "," + NICKEL;
        } // if getNickel
        if (getSelenium() != FLOATNULL) {
            columns = columns + "," + SELENIUM;
        } // if getSelenium
        if (getZinc() != FLOATNULL) {
            columns = columns + "," + ZINC;
        } // if getZinc
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = getSedphyCode("");
        if (getArsenic() != FLOATNULL) {
            values  = values  + "," + getArsenic("");
        } // if getArsenic
        if (getCadmium() != FLOATNULL) {
            values  = values  + "," + getCadmium("");
        } // if getCadmium
        if (getChromium() != FLOATNULL) {
            values  = values  + "," + getChromium("");
        } // if getChromium
        if (getCobalt() != FLOATNULL) {
            values  = values  + "," + getCobalt("");
        } // if getCobalt
        if (getCopper() != FLOATNULL) {
            values  = values  + "," + getCopper("");
        } // if getCopper
        if (getIron() != FLOATNULL) {
            values  = values  + "," + getIron("");
        } // if getIron
        if (getLead() != FLOATNULL) {
            values  = values  + "," + getLead("");
        } // if getLead
        if (getManganese() != FLOATNULL) {
            values  = values  + "," + getManganese("");
        } // if getManganese
        if (getMercury() != FLOATNULL) {
            values  = values  + "," + getMercury("");
        } // if getMercury
        if (getNickel() != FLOATNULL) {
            values  = values  + "," + getNickel("");
        } // if getNickel
        if (getSelenium() != FLOATNULL) {
            values  = values  + "," + getSelenium("");
        } // if getSelenium
        if (getZinc() != FLOATNULL) {
            values  = values  + "," + getZinc("");
        } // if getZinc
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
            getArsenic("")    + "|" +
            getCadmium("")    + "|" +
            getChromium("")   + "|" +
            getCobalt("")     + "|" +
            getCopper("")     + "|" +
            getIron("")       + "|" +
            getLead("")       + "|" +
            getManganese("")  + "|" +
            getMercury("")    + "|" +
            getNickel("")     + "|" +
            getSelenium("")   + "|" +
            getZinc("")       + "|";
    } // method toString

} // class MrnSedpol1
