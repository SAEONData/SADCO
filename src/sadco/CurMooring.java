package sadco;

import java.sql.Timestamp;
import java.util.Vector;

/**
 * This class manages the cur_mooring table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 20140813 - SIT Group
 * @version
 * 20140813 - GenTableClassB class - create class<br>
 */
public class CurMooring extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "cur_mooring";
    /** The code field name */
    public static final String CODE = "code";
    /** The clientCode field name */
    public static final String CLIENT_CODE = "client_code";
    /** The planamCode field name */
    public static final String PLANAM_CODE = "planam_code";
    /** The stnnam field name */
    public static final String STNNAM = "stnnam";
    /** The arenam field name */
    public static final String ARENAM = "arenam";
    /** The description field name */
    public static final String DESCRIPTION = "description";
    /** The latitude field name */
    public static final String LATITUDE = "latitude";
    /** The longitude field name */
    public static final String LONGITUDE = "longitude";
    /** The stndep field name */
    public static final String STNDEP = "stndep";
    /** The dateTimeStart field name */
    public static final String DATE_TIME_START = "date_time_start";
    /** The dateTimeEnd field name */
    public static final String DATE_TIME_END = "date_time_end";
    /** The numberOfDepths field name */
    public static final String NUMBER_OF_DEPTHS = "number_of_depths";
    /** The publicationRef field name */
    public static final String PUBLICATION_REF = "publication_ref";
    /** The surveyId field name */
    public static final String SURVEY_ID = "survey_id";
    /** The prjnam field name */
    public static final String PRJNAM = "prjnam";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private int       clientCode;
    private int       planamCode;
    private String    stnnam;
    private String    arenam;
    private String    description;
    private float     latitude;
    private float     longitude;
    private float     stndep;
    private Timestamp dateTimeStart;
    private Timestamp dateTimeEnd;
    private int       numberOfDepths;
    private String    publicationRef;
    private String    surveyId;
    private String    prjnam;

    /** The error variables  */
    private int codeError           = ERROR_NORMAL;
    private int clientCodeError     = ERROR_NORMAL;
    private int planamCodeError     = ERROR_NORMAL;
    private int stnnamError         = ERROR_NORMAL;
    private int arenamError         = ERROR_NORMAL;
    private int descriptionError    = ERROR_NORMAL;
    private int latitudeError       = ERROR_NORMAL;
    private int longitudeError      = ERROR_NORMAL;
    private int stndepError         = ERROR_NORMAL;
    private int dateTimeStartError  = ERROR_NORMAL;
    private int dateTimeEndError    = ERROR_NORMAL;
    private int numberOfDepthsError = ERROR_NORMAL;
    private int publicationRefError = ERROR_NORMAL;
    private int surveyIdError       = ERROR_NORMAL;
    private int prjnamError         = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage           = "";
    private String clientCodeErrorMessage     = "";
    private String planamCodeErrorMessage     = "";
    private String stnnamErrorMessage         = "";
    private String arenamErrorMessage         = "";
    private String descriptionErrorMessage    = "";
    private String latitudeErrorMessage       = "";
    private String longitudeErrorMessage      = "";
    private String stndepErrorMessage         = "";
    private String dateTimeStartErrorMessage  = "";
    private String dateTimeEndErrorMessage    = "";
    private String numberOfDepthsErrorMessage = "";
    private String publicationRefErrorMessage = "";
    private String surveyIdErrorMessage       = "";
    private String prjnamErrorMessage         = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       CLIENT_CODE_MN = INTMIN;
    public static final int       CLIENT_CODE_MX = INTMAX;
    public static final int       PLANAM_CODE_MN = INTMIN;
    public static final int       PLANAM_CODE_MX = INTMAX;
    public static final float     LATITUDE_MN = FLOATMIN;
    public static final float     LATITUDE_MX = FLOATMAX;
    public static final float     LONGITUDE_MN = FLOATMIN;
    public static final float     LONGITUDE_MX = FLOATMAX;
    public static final float     STNDEP_MN = FLOATMIN;
    public static final float     STNDEP_MX = FLOATMAX;
    public static final Timestamp DATE_TIME_START_MN = DATEMIN;
    public static final Timestamp DATE_TIME_START_MX = DATEMAX;
    public static final Timestamp DATE_TIME_END_MN = DATEMIN;
    public static final Timestamp DATE_TIME_END_MX = DATEMAX;
    public static final int       NUMBER_OF_DEPTHS_MN = INTMIN;
    public static final int       NUMBER_OF_DEPTHS_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception clientCodeOutOfBoundsException =
        new Exception ("'clientCode' out of bounds: " +
            CLIENT_CODE_MN + " - " + CLIENT_CODE_MX);
    Exception planamCodeOutOfBoundsException =
        new Exception ("'planamCode' out of bounds: " +
            PLANAM_CODE_MN + " - " + PLANAM_CODE_MX);
    Exception latitudeOutOfBoundsException =
        new Exception ("'latitude' out of bounds: " +
            LATITUDE_MN + " - " + LATITUDE_MX);
    Exception longitudeOutOfBoundsException =
        new Exception ("'longitude' out of bounds: " +
            LONGITUDE_MN + " - " + LONGITUDE_MX);
    Exception stndepOutOfBoundsException =
        new Exception ("'stndep' out of bounds: " +
            STNDEP_MN + " - " + STNDEP_MX);
    Exception dateTimeStartException =
        new Exception ("'dateTimeStart' is null");
    Exception dateTimeStartOutOfBoundsException =
        new Exception ("'dateTimeStart' out of bounds: " +
            DATE_TIME_START_MN + " - " + DATE_TIME_START_MX);
    Exception dateTimeEndException =
        new Exception ("'dateTimeEnd' is null");
    Exception dateTimeEndOutOfBoundsException =
        new Exception ("'dateTimeEnd' out of bounds: " +
            DATE_TIME_END_MN + " - " + DATE_TIME_END_MX);
    Exception numberOfDepthsOutOfBoundsException =
        new Exception ("'numberOfDepths' out of bounds: " +
            NUMBER_OF_DEPTHS_MN + " - " + NUMBER_OF_DEPTHS_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public CurMooring() {
        clearVars();
        if (dbg) System.out.println ("<br>in CurMooring constructor 1"); // debug
    } // CurMooring constructor

    /**
     * Instantiate a CurMooring object and initialize the instance variables.
     * @param code  The code (int)
     */
    public CurMooring(
            int code) {
        this();
        setCode           (code          );
        if (dbg) System.out.println ("<br>in CurMooring constructor 2"); // debug
    } // CurMooring constructor

    /**
     * Instantiate a CurMooring object and initialize the instance variables.
     * @param code            The code           (int)
     * @param clientCode      The clientCode     (int)
     * @param planamCode      The planamCode     (int)
     * @param stnnam          The stnnam         (String)
     * @param arenam          The arenam         (String)
     * @param description     The description    (String)
     * @param latitude        The latitude       (float)
     * @param longitude       The longitude      (float)
     * @param stndep          The stndep         (float)
     * @param dateTimeStart   The dateTimeStart  (java.util.Date)
     * @param dateTimeEnd     The dateTimeEnd    (java.util.Date)
     * @param numberOfDepths  The numberOfDepths (int)
     * @param publicationRef  The publicationRef (String)
     * @param surveyId        The surveyId       (String)
     * @param prjnam          The prjnam         (String)
     * @return A CurMooring object
     */
    public CurMooring(
            int            code,
            int            clientCode,
            int            planamCode,
            String         stnnam,
            String         arenam,
            String         description,
            float          latitude,
            float          longitude,
            float          stndep,
            java.util.Date dateTimeStart,
            java.util.Date dateTimeEnd,
            int            numberOfDepths,
            String         publicationRef,
            String         surveyId,
            String         prjnam) {
        this();
        setCode           (code          );
        setClientCode     (clientCode    );
        setPlanamCode     (planamCode    );
        setStnnam         (stnnam        );
        setArenam         (arenam        );
        setDescription    (description   );
        setLatitude       (latitude      );
        setLongitude      (longitude     );
        setStndep         (stndep        );
        setDateTimeStart  (dateTimeStart );
        setDateTimeEnd    (dateTimeEnd   );
        setNumberOfDepths (numberOfDepths);
        setPublicationRef (publicationRef);
        setSurveyId       (surveyId      );
        setPrjnam         (prjnam        );
        if (dbg) System.out.println ("<br>in CurMooring constructor 3"); // debug
    } // CurMooring constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode           (INTNULL  );
        setClientCode     (INTNULL  );
        setPlanamCode     (INTNULL  );
        setStnnam         (CHARNULL );
        setArenam         (CHARNULL );
        setDescription    (CHARNULL );
        setLatitude       (FLOATNULL);
        setLongitude      (FLOATNULL);
        setStndep         (FLOATNULL);
        setDateTimeStart  (DATENULL );
        setDateTimeEnd    (DATENULL );
        setNumberOfDepths (INTNULL  );
        setPublicationRef (CHARNULL );
        setSurveyId       (CHARNULL );
        setPrjnam         (CHARNULL );
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
     * Set the 'clientCode' class variable
     * @param  clientCode (int)
     */
    public int setClientCode(int clientCode) {
        try {
            if ( ((clientCode == INTNULL) ||
                  (clientCode == INTNULL2)) ||
                !((clientCode < CLIENT_CODE_MN) ||
                  (clientCode > CLIENT_CODE_MX)) ) {
                this.clientCode = clientCode;
                clientCodeError = ERROR_NORMAL;
            } else {
                throw clientCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (Integer)
     */
    public int setClientCode(Integer clientCode) {
        try {
            setClientCode(clientCode.intValue());
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (Float)
     */
    public int setClientCode(Float clientCode) {
        try {
            if (clientCode.floatValue() == FLOATNULL) {
                setClientCode(INTNULL);
            } else {
                setClientCode(clientCode.intValue());
            } // if (clientCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Set the 'clientCode' class variable
     * @param  clientCode (String)
     */
    public int setClientCode(String clientCode) {
        try {
            setClientCode(new Integer(clientCode).intValue());
        } catch (Exception e) {
            setClientCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return clientCodeError;
    } // method setClientCode

    /**
     * Called when an exception has occured
     * @param  clientCode (String)
     */
    private void setClientCodeError (int clientCode, Exception e, int error) {
        this.clientCode = clientCode;
        clientCodeErrorMessage = e.toString();
        clientCodeError = error;
    } // method setClientCodeError


    /**
     * Set the 'planamCode' class variable
     * @param  planamCode (int)
     */
    public int setPlanamCode(int planamCode) {
        try {
            if ( ((planamCode == INTNULL) ||
                  (planamCode == INTNULL2)) ||
                !((planamCode < PLANAM_CODE_MN) ||
                  (planamCode > PLANAM_CODE_MX)) ) {
                this.planamCode = planamCode;
                planamCodeError = ERROR_NORMAL;
            } else {
                throw planamCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPlanamCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return planamCodeError;
    } // method setPlanamCode

    /**
     * Set the 'planamCode' class variable
     * @param  planamCode (Integer)
     */
    public int setPlanamCode(Integer planamCode) {
        try {
            setPlanamCode(planamCode.intValue());
        } catch (Exception e) {
            setPlanamCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return planamCodeError;
    } // method setPlanamCode

    /**
     * Set the 'planamCode' class variable
     * @param  planamCode (Float)
     */
    public int setPlanamCode(Float planamCode) {
        try {
            if (planamCode.floatValue() == FLOATNULL) {
                setPlanamCode(INTNULL);
            } else {
                setPlanamCode(planamCode.intValue());
            } // if (planamCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setPlanamCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return planamCodeError;
    } // method setPlanamCode

    /**
     * Set the 'planamCode' class variable
     * @param  planamCode (String)
     */
    public int setPlanamCode(String planamCode) {
        try {
            setPlanamCode(new Integer(planamCode).intValue());
        } catch (Exception e) {
            setPlanamCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return planamCodeError;
    } // method setPlanamCode

    /**
     * Called when an exception has occured
     * @param  planamCode (String)
     */
    private void setPlanamCodeError (int planamCode, Exception e, int error) {
        this.planamCode = planamCode;
        planamCodeErrorMessage = e.toString();
        planamCodeError = error;
    } // method setPlanamCodeError


    /**
     * Set the 'stnnam' class variable
     * @param  stnnam (String)
     */
    public int setStnnam(String stnnam) {
        try {
            this.stnnam = stnnam;
            if (this.stnnam != CHARNULL) {
                this.stnnam = stripCRLF(this.stnnam.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>stnnam = " + this.stnnam);
        } catch (Exception e) {
            setStnnamError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return stnnamError;
    } // method setStnnam

    /**
     * Called when an exception has occured
     * @param  stnnam (String)
     */
    private void setStnnamError (String stnnam, Exception e, int error) {
        this.stnnam = stnnam;
        stnnamErrorMessage = e.toString();
        stnnamError = error;
    } // method setStnnamError


    /**
     * Set the 'arenam' class variable
     * @param  arenam (String)
     */
    public int setArenam(String arenam) {
        try {
            this.arenam = arenam;
            if (this.arenam != CHARNULL) {
                this.arenam = stripCRLF(this.arenam.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>arenam = " + this.arenam);
        } catch (Exception e) {
            setArenamError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return arenamError;
    } // method setArenam

    /**
     * Called when an exception has occured
     * @param  arenam (String)
     */
    private void setArenamError (String arenam, Exception e, int error) {
        this.arenam = arenam;
        arenamErrorMessage = e.toString();
        arenamError = error;
    } // method setArenamError


    /**
     * Set the 'description' class variable
     * @param  description (String)
     */
    public int setDescription(String description) {
        try {
            this.description = description;
            if (this.description != CHARNULL) {
                this.description = stripCRLF(this.description.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>description = " + this.description);
        } catch (Exception e) {
            setDescriptionError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return descriptionError;
    } // method setDescription

    /**
     * Called when an exception has occured
     * @param  description (String)
     */
    private void setDescriptionError (String description, Exception e, int error) {
        this.description = description;
        descriptionErrorMessage = e.toString();
        descriptionError = error;
    } // method setDescriptionError


    /**
     * Set the 'latitude' class variable
     * @param  latitude (float)
     */
    public int setLatitude(float latitude) {
        try {
            if ( ((latitude == FLOATNULL) ||
                  (latitude == FLOATNULL2)) ||
                !((latitude < LATITUDE_MN) ||
                  (latitude > LATITUDE_MX)) ) {
                this.latitude = latitude;
                latitudeError = ERROR_NORMAL;
            } else {
                throw latitudeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Set the 'latitude' class variable
     * @param  latitude (Integer)
     */
    public int setLatitude(Integer latitude) {
        try {
            setLatitude(latitude.floatValue());
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Set the 'latitude' class variable
     * @param  latitude (Float)
     */
    public int setLatitude(Float latitude) {
        try {
            setLatitude(latitude.floatValue());
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Set the 'latitude' class variable
     * @param  latitude (String)
     */
    public int setLatitude(String latitude) {
        try {
            setLatitude(new Float(latitude).floatValue());
        } catch (Exception e) {
            setLatitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeError;
    } // method setLatitude

    /**
     * Called when an exception has occured
     * @param  latitude (String)
     */
    private void setLatitudeError (float latitude, Exception e, int error) {
        this.latitude = latitude;
        latitudeErrorMessage = e.toString();
        latitudeError = error;
    } // method setLatitudeError


    /**
     * Set the 'longitude' class variable
     * @param  longitude (float)
     */
    public int setLongitude(float longitude) {
        try {
            if ( ((longitude == FLOATNULL) ||
                  (longitude == FLOATNULL2)) ||
                !((longitude < LONGITUDE_MN) ||
                  (longitude > LONGITUDE_MX)) ) {
                this.longitude = longitude;
                longitudeError = ERROR_NORMAL;
            } else {
                throw longitudeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Set the 'longitude' class variable
     * @param  longitude (Integer)
     */
    public int setLongitude(Integer longitude) {
        try {
            setLongitude(longitude.floatValue());
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Set the 'longitude' class variable
     * @param  longitude (Float)
     */
    public int setLongitude(Float longitude) {
        try {
            setLongitude(longitude.floatValue());
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Set the 'longitude' class variable
     * @param  longitude (String)
     */
    public int setLongitude(String longitude) {
        try {
            setLongitude(new Float(longitude).floatValue());
        } catch (Exception e) {
            setLongitudeError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeError;
    } // method setLongitude

    /**
     * Called when an exception has occured
     * @param  longitude (String)
     */
    private void setLongitudeError (float longitude, Exception e, int error) {
        this.longitude = longitude;
        longitudeErrorMessage = e.toString();
        longitudeError = error;
    } // method setLongitudeError


    /**
     * Set the 'stndep' class variable
     * @param  stndep (float)
     */
    public int setStndep(float stndep) {
        try {
            if ( ((stndep == FLOATNULL) ||
                  (stndep == FLOATNULL2)) ||
                !((stndep < STNDEP_MN) ||
                  (stndep > STNDEP_MX)) ) {
                this.stndep = stndep;
                stndepError = ERROR_NORMAL;
            } else {
                throw stndepOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStndepError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return stndepError;
    } // method setStndep

    /**
     * Set the 'stndep' class variable
     * @param  stndep (Integer)
     */
    public int setStndep(Integer stndep) {
        try {
            setStndep(stndep.floatValue());
        } catch (Exception e) {
            setStndepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return stndepError;
    } // method setStndep

    /**
     * Set the 'stndep' class variable
     * @param  stndep (Float)
     */
    public int setStndep(Float stndep) {
        try {
            setStndep(stndep.floatValue());
        } catch (Exception e) {
            setStndepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return stndepError;
    } // method setStndep

    /**
     * Set the 'stndep' class variable
     * @param  stndep (String)
     */
    public int setStndep(String stndep) {
        try {
            setStndep(new Float(stndep).floatValue());
        } catch (Exception e) {
            setStndepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return stndepError;
    } // method setStndep

    /**
     * Called when an exception has occured
     * @param  stndep (String)
     */
    private void setStndepError (float stndep, Exception e, int error) {
        this.stndep = stndep;
        stndepErrorMessage = e.toString();
        stndepError = error;
    } // method setStndepError


    /**
     * Set the 'dateTimeStart' class variable
     * @param  dateTimeStart (Timestamp)
     */
    public int setDateTimeStart(Timestamp dateTimeStart) {
        try {
            if (dateTimeStart == null) { this.dateTimeStart = DATENULL; }
            if ( (dateTimeStart.equals(DATENULL) ||
                  dateTimeStart.equals(DATENULL2)) ||
                !(dateTimeStart.before(DATE_TIME_START_MN) ||
                  dateTimeStart.after(DATE_TIME_START_MX)) ) {
                this.dateTimeStart = dateTimeStart;
                dateTimeStartError = ERROR_NORMAL;
            } else {
                throw dateTimeStartOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateTimeStartError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateTimeStartError;
    } // method setDateTimeStart

    /**
     * Set the 'dateTimeStart' class variable
     * @param  dateTimeStart (java.util.Date)
     */
    public int setDateTimeStart(java.util.Date dateTimeStart) {
        try {
            setDateTimeStart(new Timestamp(dateTimeStart.getTime()));
        } catch (Exception e) {
            setDateTimeStartError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeStartError;
    } // method setDateTimeStart

    /**
     * Set the 'dateTimeStart' class variable
     * @param  dateTimeStart (String)
     */
    public int setDateTimeStart(String dateTimeStart) {
        try {
            int len = dateTimeStart.length();
            switch (len) {
            // date &/or times
                case 4: dateTimeStart += "-01";
                case 7: dateTimeStart += "-01";
                case 10: dateTimeStart += " 00";
                case 13: dateTimeStart += ":00";
                case 16: dateTimeStart += ":00"; break;
                // times only
                case 5: dateTimeStart = "1800-01-01 " + dateTimeStart + ":00"; break;
                case 8: dateTimeStart = "1800-01-01 " + dateTimeStart; break;
            } // switch
            if (dbg) System.out.println ("dateTimeStart = " + dateTimeStart);
            setDateTimeStart(Timestamp.valueOf(dateTimeStart));
        } catch (Exception e) {
            setDateTimeStartError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeStartError;
    } // method setDateTimeStart

    /**
     * Called when an exception has occured
     * @param  dateTimeStart (String)
     */
    private void setDateTimeStartError (Timestamp dateTimeStart, Exception e, int error) {
        this.dateTimeStart = dateTimeStart;
        dateTimeStartErrorMessage = e.toString();
        dateTimeStartError = error;
    } // method setDateTimeStartError


    /**
     * Set the 'dateTimeEnd' class variable
     * @param  dateTimeEnd (Timestamp)
     */
    public int setDateTimeEnd(Timestamp dateTimeEnd) {
        try {
            if (dateTimeEnd == null) { this.dateTimeEnd = DATENULL; }
            if ( (dateTimeEnd.equals(DATENULL) ||
                  dateTimeEnd.equals(DATENULL2)) ||
                !(dateTimeEnd.before(DATE_TIME_END_MN) ||
                  dateTimeEnd.after(DATE_TIME_END_MX)) ) {
                this.dateTimeEnd = dateTimeEnd;
                dateTimeEndError = ERROR_NORMAL;
            } else {
                throw dateTimeEndOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateTimeEndError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateTimeEndError;
    } // method setDateTimeEnd

    /**
     * Set the 'dateTimeEnd' class variable
     * @param  dateTimeEnd (java.util.Date)
     */
    public int setDateTimeEnd(java.util.Date dateTimeEnd) {
        try {
            setDateTimeEnd(new Timestamp(dateTimeEnd.getTime()));
        } catch (Exception e) {
            setDateTimeEndError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeEndError;
    } // method setDateTimeEnd

    /**
     * Set the 'dateTimeEnd' class variable
     * @param  dateTimeEnd (String)
     */
    public int setDateTimeEnd(String dateTimeEnd) {
        try {
            int len = dateTimeEnd.length();
            switch (len) {
            // date &/or times
                case 4: dateTimeEnd += "-01";
                case 7: dateTimeEnd += "-01";
                case 10: dateTimeEnd += " 00";
                case 13: dateTimeEnd += ":00";
                case 16: dateTimeEnd += ":00"; break;
                // times only
                case 5: dateTimeEnd = "1800-01-01 " + dateTimeEnd + ":00"; break;
                case 8: dateTimeEnd = "1800-01-01 " + dateTimeEnd; break;
            } // switch
            if (dbg) System.out.println ("dateTimeEnd = " + dateTimeEnd);
            setDateTimeEnd(Timestamp.valueOf(dateTimeEnd));
        } catch (Exception e) {
            setDateTimeEndError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeEndError;
    } // method setDateTimeEnd

    /**
     * Called when an exception has occured
     * @param  dateTimeEnd (String)
     */
    private void setDateTimeEndError (Timestamp dateTimeEnd, Exception e, int error) {
        this.dateTimeEnd = dateTimeEnd;
        dateTimeEndErrorMessage = e.toString();
        dateTimeEndError = error;
    } // method setDateTimeEndError


    /**
     * Set the 'numberOfDepths' class variable
     * @param  numberOfDepths (int)
     */
    public int setNumberOfDepths(int numberOfDepths) {
        try {
            if ( ((numberOfDepths == INTNULL) ||
                  (numberOfDepths == INTNULL2)) ||
                !((numberOfDepths < NUMBER_OF_DEPTHS_MN) ||
                  (numberOfDepths > NUMBER_OF_DEPTHS_MX)) ) {
                this.numberOfDepths = numberOfDepths;
                numberOfDepthsError = ERROR_NORMAL;
            } else {
                throw numberOfDepthsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNumberOfDepthsError(INTNULL, e, ERROR_LOCAL);
        } // try
        return numberOfDepthsError;
    } // method setNumberOfDepths

    /**
     * Set the 'numberOfDepths' class variable
     * @param  numberOfDepths (Integer)
     */
    public int setNumberOfDepths(Integer numberOfDepths) {
        try {
            setNumberOfDepths(numberOfDepths.intValue());
        } catch (Exception e) {
            setNumberOfDepthsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberOfDepthsError;
    } // method setNumberOfDepths

    /**
     * Set the 'numberOfDepths' class variable
     * @param  numberOfDepths (Float)
     */
    public int setNumberOfDepths(Float numberOfDepths) {
        try {
            if (numberOfDepths.floatValue() == FLOATNULL) {
                setNumberOfDepths(INTNULL);
            } else {
                setNumberOfDepths(numberOfDepths.intValue());
            } // if (numberOfDepths.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setNumberOfDepthsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberOfDepthsError;
    } // method setNumberOfDepths

    /**
     * Set the 'numberOfDepths' class variable
     * @param  numberOfDepths (String)
     */
    public int setNumberOfDepths(String numberOfDepths) {
        try {
            setNumberOfDepths(new Integer(numberOfDepths).intValue());
        } catch (Exception e) {
            setNumberOfDepthsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numberOfDepthsError;
    } // method setNumberOfDepths

    /**
     * Called when an exception has occured
     * @param  numberOfDepths (String)
     */
    private void setNumberOfDepthsError (int numberOfDepths, Exception e, int error) {
        this.numberOfDepths = numberOfDepths;
        numberOfDepthsErrorMessage = e.toString();
        numberOfDepthsError = error;
    } // method setNumberOfDepthsError


    /**
     * Set the 'publicationRef' class variable
     * @param  publicationRef (String)
     */
    public int setPublicationRef(String publicationRef) {
        try {
            this.publicationRef = publicationRef;
            if (this.publicationRef != CHARNULL) {
                this.publicationRef = stripCRLF(this.publicationRef.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>publicationRef = " + this.publicationRef);
        } catch (Exception e) {
            setPublicationRefError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return publicationRefError;
    } // method setPublicationRef

    /**
     * Called when an exception has occured
     * @param  publicationRef (String)
     */
    private void setPublicationRefError (String publicationRef, Exception e, int error) {
        this.publicationRef = publicationRef;
        publicationRefErrorMessage = e.toString();
        publicationRefError = error;
    } // method setPublicationRefError


    /**
     * Set the 'surveyId' class variable
     * @param  surveyId (String)
     */
    public int setSurveyId(String surveyId) {
        try {
            this.surveyId = surveyId;
            if (this.surveyId != CHARNULL) {
                this.surveyId = stripCRLF(this.surveyId.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>surveyId = " + this.surveyId);
        } catch (Exception e) {
            setSurveyIdError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return surveyIdError;
    } // method setSurveyId

    /**
     * Called when an exception has occured
     * @param  surveyId (String)
     */
    private void setSurveyIdError (String surveyId, Exception e, int error) {
        this.surveyId = surveyId;
        surveyIdErrorMessage = e.toString();
        surveyIdError = error;
    } // method setSurveyIdError


    /**
     * Set the 'prjnam' class variable
     * @param  prjnam (String)
     */
    public int setPrjnam(String prjnam) {
        try {
            this.prjnam = prjnam;
            if (this.prjnam != CHARNULL) {
                this.prjnam = stripCRLF(this.prjnam.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>prjnam = " + this.prjnam);
        } catch (Exception e) {
            setPrjnamError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return prjnamError;
    } // method setPrjnam

    /**
     * Called when an exception has occured
     * @param  prjnam (String)
     */
    private void setPrjnamError (String prjnam, Exception e, int error) {
        this.prjnam = prjnam;
        prjnamErrorMessage = e.toString();
        prjnamError = error;
    } // method setPrjnamError


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
     * Return the 'clientCode' class variable
     * @return clientCode (int)
     */
    public int getClientCode() {
        return clientCode;
    } // method getClientCode

    /**
     * Return the 'clientCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getClientCode methods
     * @return clientCode (String)
     */
    public String getClientCode(String s) {
        return ((clientCode != INTNULL) ? new Integer(clientCode).toString() : "");
    } // method getClientCode


    /**
     * Return the 'planamCode' class variable
     * @return planamCode (int)
     */
    public int getPlanamCode() {
        return planamCode;
    } // method getPlanamCode

    /**
     * Return the 'planamCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPlanamCode methods
     * @return planamCode (String)
     */
    public String getPlanamCode(String s) {
        return ((planamCode != INTNULL) ? new Integer(planamCode).toString() : "");
    } // method getPlanamCode


    /**
     * Return the 'stnnam' class variable
     * @return stnnam (String)
     */
    public String getStnnam() {
        return stnnam;
    } // method getStnnam

    /**
     * Return the 'stnnam' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStnnam methods
     * @return stnnam (String)
     */
    public String getStnnam(String s) {
        return (stnnam != CHARNULL ? stnnam.replace('"','\'') : "");
    } // method getStnnam


    /**
     * Return the 'arenam' class variable
     * @return arenam (String)
     */
    public String getArenam() {
        return arenam;
    } // method getArenam

    /**
     * Return the 'arenam' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getArenam methods
     * @return arenam (String)
     */
    public String getArenam(String s) {
        return (arenam != CHARNULL ? arenam.replace('"','\'') : "");
    } // method getArenam


    /**
     * Return the 'description' class variable
     * @return description (String)
     */
    public String getDescription() {
        return description;
    } // method getDescription

    /**
     * Return the 'description' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDescription methods
     * @return description (String)
     */
    public String getDescription(String s) {
        return (description != CHARNULL ? description.replace('"','\'') : "");
    } // method getDescription


    /**
     * Return the 'latitude' class variable
     * @return latitude (float)
     */
    public float getLatitude() {
        return latitude;
    } // method getLatitude

    /**
     * Return the 'latitude' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLatitude methods
     * @return latitude (String)
     */
    public String getLatitude(String s) {
        return ((latitude != FLOATNULL) ? new Float(latitude).toString() : "");
    } // method getLatitude


    /**
     * Return the 'longitude' class variable
     * @return longitude (float)
     */
    public float getLongitude() {
        return longitude;
    } // method getLongitude

    /**
     * Return the 'longitude' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLongitude methods
     * @return longitude (String)
     */
    public String getLongitude(String s) {
        return ((longitude != FLOATNULL) ? new Float(longitude).toString() : "");
    } // method getLongitude


    /**
     * Return the 'stndep' class variable
     * @return stndep (float)
     */
    public float getStndep() {
        return stndep;
    } // method getStndep

    /**
     * Return the 'stndep' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStndep methods
     * @return stndep (String)
     */
    public String getStndep(String s) {
        return ((stndep != FLOATNULL) ? new Float(stndep).toString() : "");
    } // method getStndep


    /**
     * Return the 'dateTimeStart' class variable
     * @return dateTimeStart (Timestamp)
     */
    public Timestamp getDateTimeStart() {
        return dateTimeStart;
    } // method getDateTimeStart

    /**
     * Return the 'dateTimeStart' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateTimeStart methods
     * @return dateTimeStart (String)
     */
    public String getDateTimeStart(String s) {
        if (dateTimeStart.equals(DATENULL)) {
            return ("");
        } else {
            String dateTimeStartStr = dateTimeStart.toString();
            return dateTimeStartStr.substring(0,dateTimeStartStr.indexOf('.'));
        } // if
    } // method getDateTimeStart


    /**
     * Return the 'dateTimeEnd' class variable
     * @return dateTimeEnd (Timestamp)
     */
    public Timestamp getDateTimeEnd() {
        return dateTimeEnd;
    } // method getDateTimeEnd

    /**
     * Return the 'dateTimeEnd' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateTimeEnd methods
     * @return dateTimeEnd (String)
     */
    public String getDateTimeEnd(String s) {
        if (dateTimeEnd.equals(DATENULL)) {
            return ("");
        } else {
            String dateTimeEndStr = dateTimeEnd.toString();
            return dateTimeEndStr.substring(0,dateTimeEndStr.indexOf('.'));
        } // if
    } // method getDateTimeEnd


    /**
     * Return the 'numberOfDepths' class variable
     * @return numberOfDepths (int)
     */
    public int getNumberOfDepths() {
        return numberOfDepths;
    } // method getNumberOfDepths

    /**
     * Return the 'numberOfDepths' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNumberOfDepths methods
     * @return numberOfDepths (String)
     */
    public String getNumberOfDepths(String s) {
        return ((numberOfDepths != INTNULL) ? new Integer(numberOfDepths).toString() : "");
    } // method getNumberOfDepths


    /**
     * Return the 'publicationRef' class variable
     * @return publicationRef (String)
     */
    public String getPublicationRef() {
        return publicationRef;
    } // method getPublicationRef

    /**
     * Return the 'publicationRef' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPublicationRef methods
     * @return publicationRef (String)
     */
    public String getPublicationRef(String s) {
        return (publicationRef != CHARNULL ? publicationRef.replace('"','\'') : "");
    } // method getPublicationRef


    /**
     * Return the 'surveyId' class variable
     * @return surveyId (String)
     */
    public String getSurveyId() {
        return surveyId;
    } // method getSurveyId

    /**
     * Return the 'surveyId' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurveyId methods
     * @return surveyId (String)
     */
    public String getSurveyId(String s) {
        return (surveyId != CHARNULL ? surveyId.replace('"','\'') : "");
    } // method getSurveyId


    /**
     * Return the 'prjnam' class variable
     * @return prjnam (String)
     */
    public String getPrjnam() {
        return prjnam;
    } // method getPrjnam

    /**
     * Return the 'prjnam' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPrjnam methods
     * @return prjnam (String)
     */
    public String getPrjnam(String s) {
        return (prjnam != CHARNULL ? prjnam.replace('"','\'') : "");
    } // method getPrjnam


    /**
     * Gets the maximum value for the code from the table
     * @return maximum code value (int)
     */
    public int getMaxCode() {
        Vector result = db.select ("max(" + CODE + ")",TABLE,"1=1");
        int maxCode = 0;
        Vector row = (Vector) result.elementAt(0);
        if (row.firstElement() != null){
            maxCode = new Integer((String) row.elementAt(0)).intValue();
        } // if (row.firstElement() != null)
        return maxCode;
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
            (clientCode == INTNULL) &&
            (planamCode == INTNULL) &&
            (stnnam == CHARNULL) &&
            (arenam == CHARNULL) &&
            (description == CHARNULL) &&
            (latitude == FLOATNULL) &&
            (longitude == FLOATNULL) &&
            (stndep == FLOATNULL) &&
            (dateTimeStart.equals(DATENULL)) &&
            (dateTimeEnd.equals(DATENULL)) &&
            (numberOfDepths == INTNULL) &&
            (publicationRef == CHARNULL) &&
            (surveyId == CHARNULL) &&
            (prjnam == CHARNULL)) {
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
            clientCodeError +
            planamCodeError +
            stnnamError +
            arenamError +
            descriptionError +
            latitudeError +
            longitudeError +
            stndepError +
            dateTimeStartError +
            dateTimeEndError +
            numberOfDepthsError +
            publicationRefError +
            surveyIdError +
            prjnamError;
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
     * Gets the errorcode for the clientCode instance variable
     * @return errorcode (int)
     */
    public int getClientCodeError() {
        return clientCodeError;
    } // method getClientCodeError

    /**
     * Gets the errorMessage for the clientCode instance variable
     * @return errorMessage (String)
     */
    public String getClientCodeErrorMessage() {
        return clientCodeErrorMessage;
    } // method getClientCodeErrorMessage


    /**
     * Gets the errorcode for the planamCode instance variable
     * @return errorcode (int)
     */
    public int getPlanamCodeError() {
        return planamCodeError;
    } // method getPlanamCodeError

    /**
     * Gets the errorMessage for the planamCode instance variable
     * @return errorMessage (String)
     */
    public String getPlanamCodeErrorMessage() {
        return planamCodeErrorMessage;
    } // method getPlanamCodeErrorMessage


    /**
     * Gets the errorcode for the stnnam instance variable
     * @return errorcode (int)
     */
    public int getStnnamError() {
        return stnnamError;
    } // method getStnnamError

    /**
     * Gets the errorMessage for the stnnam instance variable
     * @return errorMessage (String)
     */
    public String getStnnamErrorMessage() {
        return stnnamErrorMessage;
    } // method getStnnamErrorMessage


    /**
     * Gets the errorcode for the arenam instance variable
     * @return errorcode (int)
     */
    public int getArenamError() {
        return arenamError;
    } // method getArenamError

    /**
     * Gets the errorMessage for the arenam instance variable
     * @return errorMessage (String)
     */
    public String getArenamErrorMessage() {
        return arenamErrorMessage;
    } // method getArenamErrorMessage


    /**
     * Gets the errorcode for the description instance variable
     * @return errorcode (int)
     */
    public int getDescriptionError() {
        return descriptionError;
    } // method getDescriptionError

    /**
     * Gets the errorMessage for the description instance variable
     * @return errorMessage (String)
     */
    public String getDescriptionErrorMessage() {
        return descriptionErrorMessage;
    } // method getDescriptionErrorMessage


    /**
     * Gets the errorcode for the latitude instance variable
     * @return errorcode (int)
     */
    public int getLatitudeError() {
        return latitudeError;
    } // method getLatitudeError

    /**
     * Gets the errorMessage for the latitude instance variable
     * @return errorMessage (String)
     */
    public String getLatitudeErrorMessage() {
        return latitudeErrorMessage;
    } // method getLatitudeErrorMessage


    /**
     * Gets the errorcode for the longitude instance variable
     * @return errorcode (int)
     */
    public int getLongitudeError() {
        return longitudeError;
    } // method getLongitudeError

    /**
     * Gets the errorMessage for the longitude instance variable
     * @return errorMessage (String)
     */
    public String getLongitudeErrorMessage() {
        return longitudeErrorMessage;
    } // method getLongitudeErrorMessage


    /**
     * Gets the errorcode for the stndep instance variable
     * @return errorcode (int)
     */
    public int getStndepError() {
        return stndepError;
    } // method getStndepError

    /**
     * Gets the errorMessage for the stndep instance variable
     * @return errorMessage (String)
     */
    public String getStndepErrorMessage() {
        return stndepErrorMessage;
    } // method getStndepErrorMessage


    /**
     * Gets the errorcode for the dateTimeStart instance variable
     * @return errorcode (int)
     */
    public int getDateTimeStartError() {
        return dateTimeStartError;
    } // method getDateTimeStartError

    /**
     * Gets the errorMessage for the dateTimeStart instance variable
     * @return errorMessage (String)
     */
    public String getDateTimeStartErrorMessage() {
        return dateTimeStartErrorMessage;
    } // method getDateTimeStartErrorMessage


    /**
     * Gets the errorcode for the dateTimeEnd instance variable
     * @return errorcode (int)
     */
    public int getDateTimeEndError() {
        return dateTimeEndError;
    } // method getDateTimeEndError

    /**
     * Gets the errorMessage for the dateTimeEnd instance variable
     * @return errorMessage (String)
     */
    public String getDateTimeEndErrorMessage() {
        return dateTimeEndErrorMessage;
    } // method getDateTimeEndErrorMessage


    /**
     * Gets the errorcode for the numberOfDepths instance variable
     * @return errorcode (int)
     */
    public int getNumberOfDepthsError() {
        return numberOfDepthsError;
    } // method getNumberOfDepthsError

    /**
     * Gets the errorMessage for the numberOfDepths instance variable
     * @return errorMessage (String)
     */
    public String getNumberOfDepthsErrorMessage() {
        return numberOfDepthsErrorMessage;
    } // method getNumberOfDepthsErrorMessage


    /**
     * Gets the errorcode for the publicationRef instance variable
     * @return errorcode (int)
     */
    public int getPublicationRefError() {
        return publicationRefError;
    } // method getPublicationRefError

    /**
     * Gets the errorMessage for the publicationRef instance variable
     * @return errorMessage (String)
     */
    public String getPublicationRefErrorMessage() {
        return publicationRefErrorMessage;
    } // method getPublicationRefErrorMessage


    /**
     * Gets the errorcode for the surveyId instance variable
     * @return errorcode (int)
     */
    public int getSurveyIdError() {
        return surveyIdError;
    } // method getSurveyIdError

    /**
     * Gets the errorMessage for the surveyId instance variable
     * @return errorMessage (String)
     */
    public String getSurveyIdErrorMessage() {
        return surveyIdErrorMessage;
    } // method getSurveyIdErrorMessage


    /**
     * Gets the errorcode for the prjnam instance variable
     * @return errorcode (int)
     */
    public int getPrjnamError() {
        return prjnamError;
    } // method getPrjnamError

    /**
     * Gets the errorMessage for the prjnam instance variable
     * @return errorMessage (String)
     */
    public String getPrjnamErrorMessage() {
        return prjnamErrorMessage;
    } // method getPrjnamErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of CurMooring. e.g.<pre>
     * CurMooring curMooring = new CurMooring(<val1>);
     * CurMooring curMooringArray[] = curMooring.get();</pre>
     * will get the CurMooring record where code = <val1>.
     * @return Array of CurMooring (CurMooring[])
     */
    public CurMooring[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * CurMooring curMooringArray[] =
     *     new CurMooring().get(CurMooring.CODE+"=<val1>");</pre>
     * will get the CurMooring record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of CurMooring (CurMooring[])
     */
    public CurMooring[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * CurMooring curMooringArray[] =
     *     new CurMooring().get("1=1",curMooring.CODE);</pre>
     * will get the all the CurMooring records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurMooring (CurMooring[])
     */
    public CurMooring[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = CurMooring.CODE,CurMooring.CLIENT_CODE;
     * String where = CurMooring.CODE + "=<val1";
     * String order = CurMooring.CODE;
     * CurMooring curMooringArray[] =
     *     new CurMooring().get(columns, where, order);</pre>
     * will get the code and clientCode colums of all CurMooring records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurMooring (CurMooring[])
     */
    public CurMooring[] get (String fields, String where, String order) {
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
     * and transforms it into an array of CurMooring.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private CurMooring[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol           = db.getColNumber(CODE);
        int clientCodeCol     = db.getColNumber(CLIENT_CODE);
        int planamCodeCol     = db.getColNumber(PLANAM_CODE);
        int stnnamCol         = db.getColNumber(STNNAM);
        int arenamCol         = db.getColNumber(ARENAM);
        int descriptionCol    = db.getColNumber(DESCRIPTION);
        int latitudeCol       = db.getColNumber(LATITUDE);
        int longitudeCol      = db.getColNumber(LONGITUDE);
        int stndepCol         = db.getColNumber(STNDEP);
        int dateTimeStartCol  = db.getColNumber(DATE_TIME_START);
        int dateTimeEndCol    = db.getColNumber(DATE_TIME_END);
        int numberOfDepthsCol = db.getColNumber(NUMBER_OF_DEPTHS);
        int publicationRefCol = db.getColNumber(PUBLICATION_REF);
        int surveyIdCol       = db.getColNumber(SURVEY_ID);
        int prjnamCol         = db.getColNumber(PRJNAM);
        CurMooring[] cArray = new CurMooring[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new CurMooring();
            if (codeCol != -1)
                cArray[i].setCode          ((String) row.elementAt(codeCol));
            if (clientCodeCol != -1)
                cArray[i].setClientCode    ((String) row.elementAt(clientCodeCol));
            if (planamCodeCol != -1)
                cArray[i].setPlanamCode    ((String) row.elementAt(planamCodeCol));
            if (stnnamCol != -1)
                cArray[i].setStnnam        ((String) row.elementAt(stnnamCol));
            if (arenamCol != -1)
                cArray[i].setArenam        ((String) row.elementAt(arenamCol));
            if (descriptionCol != -1)
                cArray[i].setDescription   ((String) row.elementAt(descriptionCol));
            if (latitudeCol != -1)
                cArray[i].setLatitude      ((String) row.elementAt(latitudeCol));
            if (longitudeCol != -1)
                cArray[i].setLongitude     ((String) row.elementAt(longitudeCol));
            if (stndepCol != -1)
                cArray[i].setStndep        ((String) row.elementAt(stndepCol));
            if (dateTimeStartCol != -1)
                cArray[i].setDateTimeStart ((String) row.elementAt(dateTimeStartCol));
            if (dateTimeEndCol != -1)
                cArray[i].setDateTimeEnd   ((String) row.elementAt(dateTimeEndCol));
            if (numberOfDepthsCol != -1)
                cArray[i].setNumberOfDepths((String) row.elementAt(numberOfDepthsCol));
            if (publicationRefCol != -1)
                cArray[i].setPublicationRef((String) row.elementAt(publicationRefCol));
            if (surveyIdCol != -1)
                cArray[i].setSurveyId      ((String) row.elementAt(surveyIdCol));
            if (prjnamCol != -1)
                cArray[i].setPrjnam        ((String) row.elementAt(prjnamCol));
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
     *     new CurMooring(
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
     *         <val15>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     clientCode     = <val2>,
     *     planamCode     = <val3>,
     *     stnnam         = <val4>,
     *     arenam         = <val5>,
     *     description    = <val6>,
     *     latitude       = <val7>,
     *     longitude      = <val8>,
     *     stndep         = <val9>,
     *     dateTimeStart  = <val10>,
     *     dateTimeEnd    = <val11>,
     *     numberOfDepths = <val12>,
     *     publicationRef = <val13>,
     *     surveyId       = <val14>,
     *     prjnam         = <val15>.
     * @return success = true/false (boolean)
     */
    public boolean put() {
        return db.insert (TABLE, createColumns(), createValues());
    } // method put


    /**
     * Insert a record into the table.  The table is locked first,
     * and afterwards committed.  The values are taken from the current
     * value of the instance variables. .e.g.<pre>
     * String    CHARNULL  = Tables.CHARNULL;
     * Timestamp DATENULL  = Tables.DATENULL;
     * int       INTNULL   = Tables.INTNULL;
     * float     FLOATNULL = Tables.FLOATNULL;
     * Boolean success =
     *     new CurMooring(
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
     *         <val15>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     clientCode     = <val2>,
     *     planamCode     = <val3>,
     *     stnnam         = <val4>,
     *     arenam         = <val5>,
     *     description    = <val6>,
     *     latitude       = <val7>,
     *     longitude      = <val8>,
     *     stndep         = <val9>,
     *     dateTimeStart  = <val10>,
     *     dateTimeEnd    = <val11>,
     *     numberOfDepths = <val12>,
     *     publicationRef = <val13>,
     *     surveyId       = <val14>,
     *     prjnam         = <val15>.
     * @return success = true/false (boolean)
     */
    public boolean putLock() {
        db.lock(TABLE);
        boolean success = db.insert (TABLE, createColumns(), createValues());
        db.commit();
        return success;
    } // method putLock


    //=====================//
    // All the del methods //
    //=====================//

    /**
     * Delete record(s) from the table, The where clause is created from the
     * current values of the instance variables. .e.g.<pre>
     * Boolean success = new CurMooring(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.DATENULL,
     *     Tables.DATENULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where clientCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurMooring curMooring = new CurMooring();
     * success = curMooring.del(CurMooring.CODE+"=<val1>");</pre>
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
     * update are taken from the CurMooring argument, .e.g.<pre>
     * Boolean success
     * CurMooring updCurMooring = new CurMooring();
     * updCurMooring.setClientCode(<val2>);
     * CurMooring whereCurMooring = new CurMooring(<val1>);
     * success = whereCurMooring.upd(updCurMooring);</pre>
     * will update ClientCode to <val2> for all records where
     * code = <val1>.
     * @param curMooring  A CurMooring variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(CurMooring curMooring) {
        return db.update (TABLE, createColVals(curMooring), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurMooring updCurMooring = new CurMooring();
     * updCurMooring.setClientCode(<val2>);
     * CurMooring whereCurMooring = new CurMooring();
     * success = whereCurMooring.upd(
     *     updCurMooring, CurMooring.CODE+"=<val1>");</pre>
     * will update ClientCode to <val2> for all records where
     * code = <val1>.
     * @param  updCurMooring  A CurMooring variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(CurMooring curMooring, String where) {
        return db.update (TABLE, createColVals(curMooring), where);
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
        if (getClientCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CLIENT_CODE + "=" + getClientCode("");
        } // if getClientCode
        if (getPlanamCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PLANAM_CODE + "=" + getPlanamCode("");
        } // if getPlanamCode
        if (getStnnam() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STNNAM + "='" + getStnnam() + "'";
        } // if getStnnam
        if (getArenam() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ARENAM + "='" + getArenam() + "'";
        } // if getArenam
        if (getDescription() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DESCRIPTION + "='" + getDescription() + "'";
        } // if getDescription
        if (getLatitude() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LATITUDE + "=" + getLatitude("");
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LONGITUDE + "=" + getLongitude("");
        } // if getLongitude
        if (getStndep() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STNDEP + "=" + getStndep("");
        } // if getStndep
        if (!getDateTimeStart().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME_START +
                "=" + Tables.getDateFormat(getDateTimeStart());
        } // if getDateTimeStart
        if (!getDateTimeEnd().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME_END +
                "=" + Tables.getDateFormat(getDateTimeEnd());
        } // if getDateTimeEnd
        if (getNumberOfDepths() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NUMBER_OF_DEPTHS + "=" + getNumberOfDepths("");
        } // if getNumberOfDepths
        if (getPublicationRef() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PUBLICATION_REF + "='" + getPublicationRef() + "'";
        } // if getPublicationRef
        if (getSurveyId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_ID + "='" + getSurveyId() + "'";
        } // if getSurveyId
        if (getPrjnam() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PRJNAM + "='" + getPrjnam() + "'";
        } // if getPrjnam
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(CurMooring aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getClientCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CLIENT_CODE +"=";
            colVals += (aVar.getClientCode() == INTNULL2 ?
                "null" : aVar.getClientCode(""));
        } // if aVar.getClientCode
        if (aVar.getPlanamCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PLANAM_CODE +"=";
            colVals += (aVar.getPlanamCode() == INTNULL2 ?
                "null" : aVar.getPlanamCode(""));
        } // if aVar.getPlanamCode
        if (aVar.getStnnam() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STNNAM +"=";
            colVals += (aVar.getStnnam().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStnnam() + "'");
        } // if aVar.getStnnam
        if (aVar.getArenam() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ARENAM +"=";
            colVals += (aVar.getArenam().equals(CHARNULL2) ?
                "null" : "'" + aVar.getArenam() + "'");
        } // if aVar.getArenam
        if (aVar.getDescription() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DESCRIPTION +"=";
            colVals += (aVar.getDescription().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDescription() + "'");
        } // if aVar.getDescription
        if (aVar.getLatitude() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LATITUDE +"=";
            colVals += (aVar.getLatitude() == FLOATNULL2 ?
                "null" : aVar.getLatitude(""));
        } // if aVar.getLatitude
        if (aVar.getLongitude() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LONGITUDE +"=";
            colVals += (aVar.getLongitude() == FLOATNULL2 ?
                "null" : aVar.getLongitude(""));
        } // if aVar.getLongitude
        if (aVar.getStndep() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STNDEP +"=";
            colVals += (aVar.getStndep() == FLOATNULL2 ?
                "null" : aVar.getStndep(""));
        } // if aVar.getStndep
        if (!aVar.getDateTimeStart().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME_START +"=";
            colVals += (aVar.getDateTimeStart().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTimeStart()));
        } // if aVar.getDateTimeStart
        if (!aVar.getDateTimeEnd().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME_END +"=";
            colVals += (aVar.getDateTimeEnd().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTimeEnd()));
        } // if aVar.getDateTimeEnd
        if (aVar.getNumberOfDepths() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NUMBER_OF_DEPTHS +"=";
            colVals += (aVar.getNumberOfDepths() == INTNULL2 ?
                "null" : aVar.getNumberOfDepths(""));
        } // if aVar.getNumberOfDepths
        if (aVar.getPublicationRef() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PUBLICATION_REF +"=";
            colVals += (aVar.getPublicationRef().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPublicationRef() + "'");
        } // if aVar.getPublicationRef
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getPrjnam() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PRJNAM +"=";
            colVals += (aVar.getPrjnam().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPrjnam() + "'");
        } // if aVar.getPrjnam
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getClientCode() != INTNULL) {
            columns = columns + "," + CLIENT_CODE;
        } // if getClientCode
        if (getPlanamCode() != INTNULL) {
            columns = columns + "," + PLANAM_CODE;
        } // if getPlanamCode
        if (getStnnam() != CHARNULL) {
            columns = columns + "," + STNNAM;
        } // if getStnnam
        if (getArenam() != CHARNULL) {
            columns = columns + "," + ARENAM;
        } // if getArenam
        if (getDescription() != CHARNULL) {
            columns = columns + "," + DESCRIPTION;
        } // if getDescription
        if (getLatitude() != FLOATNULL) {
            columns = columns + "," + LATITUDE;
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            columns = columns + "," + LONGITUDE;
        } // if getLongitude
        if (getStndep() != FLOATNULL) {
            columns = columns + "," + STNDEP;
        } // if getStndep
        if (!getDateTimeStart().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME_START;
        } // if getDateTimeStart
        if (!getDateTimeEnd().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME_END;
        } // if getDateTimeEnd
        if (getNumberOfDepths() != INTNULL) {
            columns = columns + "," + NUMBER_OF_DEPTHS;
        } // if getNumberOfDepths
        if (getPublicationRef() != CHARNULL) {
            columns = columns + "," + PUBLICATION_REF;
        } // if getPublicationRef
        if (getSurveyId() != CHARNULL) {
            columns = columns + "," + SURVEY_ID;
        } // if getSurveyId
        if (getPrjnam() != CHARNULL) {
            columns = columns + "," + PRJNAM;
        } // if getPrjnam
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
        if (getClientCode() != INTNULL) {
            values  = values  + "," + getClientCode("");
        } // if getClientCode
        if (getPlanamCode() != INTNULL) {
            values  = values  + "," + getPlanamCode("");
        } // if getPlanamCode
        if (getStnnam() != CHARNULL) {
            values  = values  + ",'" + getStnnam() + "'";
        } // if getStnnam
        if (getArenam() != CHARNULL) {
            values  = values  + ",'" + getArenam() + "'";
        } // if getArenam
        if (getDescription() != CHARNULL) {
            values  = values  + ",'" + getDescription() + "'";
        } // if getDescription
        if (getLatitude() != FLOATNULL) {
            values  = values  + "," + getLatitude("");
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            values  = values  + "," + getLongitude("");
        } // if getLongitude
        if (getStndep() != FLOATNULL) {
            values  = values  + "," + getStndep("");
        } // if getStndep
        if (!getDateTimeStart().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTimeStart());
        } // if getDateTimeStart
        if (!getDateTimeEnd().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTimeEnd());
        } // if getDateTimeEnd
        if (getNumberOfDepths() != INTNULL) {
            values  = values  + "," + getNumberOfDepths("");
        } // if getNumberOfDepths
        if (getPublicationRef() != CHARNULL) {
            values  = values  + ",'" + getPublicationRef() + "'";
        } // if getPublicationRef
        if (getSurveyId() != CHARNULL) {
            values  = values  + ",'" + getSurveyId() + "'";
        } // if getSurveyId
        if (getPrjnam() != CHARNULL) {
            values  = values  + ",'" + getPrjnam() + "'";
        } // if getPrjnam
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getCode("")           + "|" +
            getClientCode("")     + "|" +
            getPlanamCode("")     + "|" +
            getStnnam("")         + "|" +
            getArenam("")         + "|" +
            getDescription("")    + "|" +
            getLatitude("")       + "|" +
            getLongitude("")      + "|" +
            getStndep("")         + "|" +
            getDateTimeStart("")  + "|" +
            getDateTimeEnd("")    + "|" +
            getNumberOfDepths("") + "|" +
            getPublicationRef("") + "|" +
            getSurveyId("")       + "|" +
            getPrjnam("")         + "|";
    } // method toString

} // class CurMooring
