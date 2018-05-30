package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the STATION table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040324 - SIT Group
 * @version
 * 040324 - GenTableClassB class - create class<br>
 */
public class MrnStation extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "STATION";
    /** The stationId field name */
    public static final String STATION_ID = "STATION_ID";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The latitude field name */
    public static final String LATITUDE = "LATITUDE";
    /** The longitude field name */
    public static final String LONGITUDE = "LONGITUDE";
    /** The dateStart field name */
    public static final String DATE_START = "DATE_START";
    /** The dateEnd field name */
    public static final String DATE_END = "DATE_END";
    /** The daynull field name */
    public static final String DAYNULL = "DAYNULL";
    /** The stnnam field name */
    public static final String STNNAM = "STNNAM";
    /** The stndep field name */
    public static final String STNDEP = "STNDEP";
    /** The offshd field name */
    public static final String OFFSHD = "OFFSHD";
    /** The passkey field name */
    public static final String PASSKEY = "PASSKEY";
    /** The dupflag field name */
    public static final String DUPFLAG = "DUPFLAG";
    /** The maxSpldep field name */
    public static final String MAX_SPLDEP = "MAX_SPLDEP";
    /** The lat field name */
    public static final String LAT = "LAT";
    /** The lon field name */
    public static final String LON = "LON";
    /** The yearmon field name */
    public static final String YEARMON = "YEARMON";
    /** The statusCode field name */
    public static final String STATUS_CODE = "STATUS_CODE";
    /** The stnRef field name */
    public static final String STN_REF = "STN_REF";
    /** The notes field name */
    public static final String NOTES = "NOTES";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    stationId;
    private String    surveyId;
    private float     latitude;
    private float     longitude;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private String    daynull;
    private String    stnnam;
    private float     stndep;
    private float     offshd;
    private String    passkey;
    private String    dupflag;
    private float     maxSpldep;
    private int       lat;
    private int       lon;
    private String    yearmon;
    private int       statusCode;
    private String    stnRef;
    private String    notes;

    /** The error variables  */
    private int stationIdError  = ERROR_NORMAL;
    private int surveyIdError   = ERROR_NORMAL;
    private int latitudeError   = ERROR_NORMAL;
    private int longitudeError  = ERROR_NORMAL;
    private int dateStartError  = ERROR_NORMAL;
    private int dateEndError    = ERROR_NORMAL;
    private int daynullError    = ERROR_NORMAL;
    private int stnnamError     = ERROR_NORMAL;
    private int stndepError     = ERROR_NORMAL;
    private int offshdError     = ERROR_NORMAL;
    private int passkeyError    = ERROR_NORMAL;
    private int dupflagError    = ERROR_NORMAL;
    private int maxSpldepError  = ERROR_NORMAL;
    private int latError        = ERROR_NORMAL;
    private int lonError        = ERROR_NORMAL;
    private int yearmonError    = ERROR_NORMAL;
    private int statusCodeError = ERROR_NORMAL;
    private int stnRefError     = ERROR_NORMAL;
    private int notesError      = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String stationIdErrorMessage  = "";
    private String surveyIdErrorMessage   = "";
    private String latitudeErrorMessage   = "";
    private String longitudeErrorMessage  = "";
    private String dateStartErrorMessage  = "";
    private String dateEndErrorMessage    = "";
    private String daynullErrorMessage    = "";
    private String stnnamErrorMessage     = "";
    private String stndepErrorMessage     = "";
    private String offshdErrorMessage     = "";
    private String passkeyErrorMessage    = "";
    private String dupflagErrorMessage    = "";
    private String maxSpldepErrorMessage  = "";
    private String latErrorMessage        = "";
    private String lonErrorMessage        = "";
    private String yearmonErrorMessage    = "";
    private String statusCodeErrorMessage = "";
    private String stnRefErrorMessage     = "";
    private String notesErrorMessage      = "";

    /** The min-max constants for all numerics */
    public static final float     LATITUDE_MN = FLOATMIN;
    public static final float     LATITUDE_MX = FLOATMAX;
    public static final float     LONGITUDE_MN = FLOATMIN;
    public static final float     LONGITUDE_MX = FLOATMAX;
    public static final Timestamp DATE_START_MN = DATEMIN;
    public static final Timestamp DATE_START_MX = DATEMAX;
    public static final Timestamp DATE_END_MN = DATEMIN;
    public static final Timestamp DATE_END_MX = DATEMAX;
    public static final float     STNDEP_MN = FLOATMIN;
    public static final float     STNDEP_MX = FLOATMAX;
    public static final float     OFFSHD_MN = FLOATMIN;
    public static final float     OFFSHD_MX = FLOATMAX;
    public static final float     MAX_SPLDEP_MN = FLOATMIN;
    public static final float     MAX_SPLDEP_MX = FLOATMAX;
    public static final int       LAT_MN = INTMIN;
    public static final int       LAT_MX = INTMAX;
    public static final int       LON_MN = INTMIN;
    public static final int       LON_MX = INTMAX;
    public static final int       STATUS_CODE_MN = INTMIN;
    public static final int       STATUS_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception latitudeOutOfBoundsException =
        new Exception ("'latitude' out of bounds: " +
            LATITUDE_MN + " - " + LATITUDE_MX);
    Exception longitudeOutOfBoundsException =
        new Exception ("'longitude' out of bounds: " +
            LONGITUDE_MN + " - " + LONGITUDE_MX);
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
    Exception stndepOutOfBoundsException =
        new Exception ("'stndep' out of bounds: " +
            STNDEP_MN + " - " + STNDEP_MX);
    Exception offshdOutOfBoundsException =
        new Exception ("'offshd' out of bounds: " +
            OFFSHD_MN + " - " + OFFSHD_MX);
    Exception maxSpldepOutOfBoundsException =
        new Exception ("'maxSpldep' out of bounds: " +
            MAX_SPLDEP_MN + " - " + MAX_SPLDEP_MX);
    Exception latOutOfBoundsException =
        new Exception ("'lat' out of bounds: " +
            LAT_MN + " - " + LAT_MX);
    Exception lonOutOfBoundsException =
        new Exception ("'lon' out of bounds: " +
            LON_MN + " - " + LON_MX);
    Exception statusCodeOutOfBoundsException =
        new Exception ("'statusCode' out of bounds: " +
            STATUS_CODE_MN + " - " + STATUS_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnStation() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnStation constructor 1"); // debug
    } // MrnStation constructor

    /**
     * Instantiate a MrnStation object and initialize the instance variables.
     * @param stationId  The stationId (String)
     */
    public MrnStation(
            String stationId) {
        this();
        setStationId  (stationId );
        if (dbg) System.out.println ("<br>in MrnStation constructor 2"); // debug
    } // MrnStation constructor

    /**
     * Instantiate a MrnStation object and initialize the instance variables.
     * @param stationId   The stationId  (String)
     * @param surveyId    The surveyId   (String)
     * @param latitude    The latitude   (float)
     * @param longitude   The longitude  (float)
     * @param dateStart   The dateStart  (java.util.Date)
     * @param dateEnd     The dateEnd    (java.util.Date)
     * @param daynull     The daynull    (String)
     * @param stnnam      The stnnam     (String)
     * @param stndep      The stndep     (float)
     * @param offshd      The offshd     (float)
     * @param passkey     The passkey    (String)
     * @param dupflag     The dupflag    (String)
     * @param maxSpldep   The maxSpldep  (float)
     * @param lat         The lat        (int)
     * @param lon         The lon        (int)
     * @param yearmon     The yearmon    (String)
     * @param statusCode  The statusCode (int)
     * @param stnRef      The stnRef     (String)
     * @param notes       The notes      (String)
     */
    public MrnStation(
            String         stationId,
            String         surveyId,
            float          latitude,
            float          longitude,
            java.util.Date dateStart,
            java.util.Date dateEnd,
            String         daynull,
            String         stnnam,
            float          stndep,
            float          offshd,
            String         passkey,
            String         dupflag,
            float          maxSpldep,
            int            lat,
            int            lon,
            String         yearmon,
            int            statusCode,
            String         stnRef,
            String         notes) {
        this();
        setStationId  (stationId );
        setSurveyId   (surveyId  );
        setLatitude   (latitude  );
        setLongitude  (longitude );
        setDateStart  (dateStart );
        setDateEnd    (dateEnd   );
        setDaynull    (daynull   );
        setStnnam     (stnnam    );
        setStndep     (stndep    );
        setOffshd     (offshd    );
        setPasskey    (passkey   );
        setDupflag    (dupflag   );
        setMaxSpldep  (maxSpldep );
        setLat        (lat       );
        setLon        (lon       );
        setYearmon    (yearmon   );
        setStatusCode (statusCode);
        setStnRef     (stnRef    );
        setNotes      (notes     );
        if (dbg) System.out.println ("<br>in MrnStation constructor 3"); // debug
    } // MrnStation constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setStationId  (CHARNULL );
        setSurveyId   (CHARNULL );
        setLatitude   (FLOATNULL);
        setLongitude  (FLOATNULL);
        setDateStart  (DATENULL );
        setDateEnd    (DATENULL );
        setDaynull    (CHARNULL );
        setStnnam     (CHARNULL );
        setStndep     (FLOATNULL);
        setOffshd     (FLOATNULL);
        setPasskey    (CHARNULL );
        setDupflag    (CHARNULL );
        setMaxSpldep  (FLOATNULL);
        setLat        (INTNULL  );
        setLon        (INTNULL  );
        setYearmon    (CHARNULL );
        setStatusCode (INTNULL  );
        setStnRef     (CHARNULL );
        setNotes      (CHARNULL );
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
     * Set the 'daynull' class variable
     * @param  daynull (String)
     */
    public int setDaynull(String daynull) {
        try {
            this.daynull = daynull;
            if (this.daynull != CHARNULL) {
                this.daynull = stripCRLF(this.daynull.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>daynull = " + this.daynull);
        } catch (Exception e) {
            setDaynullError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return daynullError;
    } // method setDaynull

    /**
     * Called when an exception has occured
     * @param  daynull (String)
     */
    private void setDaynullError (String daynull, Exception e, int error) {
        this.daynull = daynull;
        daynullErrorMessage = e.toString();
        daynullError = error;
    } // method setDaynullError


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
     * Set the 'offshd' class variable
     * @param  offshd (float)
     */
    public int setOffshd(float offshd) {
        try {
            if ( ((offshd == FLOATNULL) ||
                  (offshd == FLOATNULL2)) ||
                !((offshd < OFFSHD_MN) ||
                  (offshd > OFFSHD_MX)) ) {
                this.offshd = offshd;
                offshdError = ERROR_NORMAL;
            } else {
                throw offshdOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setOffshdError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return offshdError;
    } // method setOffshd

    /**
     * Set the 'offshd' class variable
     * @param  offshd (Integer)
     */
    public int setOffshd(Integer offshd) {
        try {
            setOffshd(offshd.floatValue());
        } catch (Exception e) {
            setOffshdError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return offshdError;
    } // method setOffshd

    /**
     * Set the 'offshd' class variable
     * @param  offshd (Float)
     */
    public int setOffshd(Float offshd) {
        try {
            setOffshd(offshd.floatValue());
        } catch (Exception e) {
            setOffshdError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return offshdError;
    } // method setOffshd

    /**
     * Set the 'offshd' class variable
     * @param  offshd (String)
     */
    public int setOffshd(String offshd) {
        try {
            setOffshd(new Float(offshd).floatValue());
        } catch (Exception e) {
            setOffshdError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return offshdError;
    } // method setOffshd

    /**
     * Called when an exception has occured
     * @param  offshd (String)
     */
    private void setOffshdError (float offshd, Exception e, int error) {
        this.offshd = offshd;
        offshdErrorMessage = e.toString();
        offshdError = error;
    } // method setOffshdError


    /**
     * Set the 'passkey' class variable
     * @param  passkey (String)
     */
    public int setPasskey(String passkey) {
        try {
            this.passkey = passkey;
            if (this.passkey != CHARNULL) {
                this.passkey = stripCRLF(this.passkey.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>passkey = " + this.passkey);
        } catch (Exception e) {
            setPasskeyError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return passkeyError;
    } // method setPasskey

    /**
     * Called when an exception has occured
     * @param  passkey (String)
     */
    private void setPasskeyError (String passkey, Exception e, int error) {
        this.passkey = passkey;
        passkeyErrorMessage = e.toString();
        passkeyError = error;
    } // method setPasskeyError


    /**
     * Set the 'dupflag' class variable
     * @param  dupflag (String)
     */
    public int setDupflag(String dupflag) {
        try {
            this.dupflag = dupflag;
            if (this.dupflag != CHARNULL) {
                this.dupflag = stripCRLF(this.dupflag.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>dupflag = " + this.dupflag);
        } catch (Exception e) {
            setDupflagError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return dupflagError;
    } // method setDupflag

    /**
     * Called when an exception has occured
     * @param  dupflag (String)
     */
    private void setDupflagError (String dupflag, Exception e, int error) {
        this.dupflag = dupflag;
        dupflagErrorMessage = e.toString();
        dupflagError = error;
    } // method setDupflagError


    /**
     * Set the 'maxSpldep' class variable
     * @param  maxSpldep (float)
     */
    public int setMaxSpldep(float maxSpldep) {
        try {
            if ( ((maxSpldep == FLOATNULL) ||
                  (maxSpldep == FLOATNULL2)) ||
                !((maxSpldep < MAX_SPLDEP_MN) ||
                  (maxSpldep > MAX_SPLDEP_MX)) ) {
                this.maxSpldep = maxSpldep;
                maxSpldepError = ERROR_NORMAL;
            } else {
                throw maxSpldepOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMaxSpldepError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return maxSpldepError;
    } // method setMaxSpldep

    /**
     * Set the 'maxSpldep' class variable
     * @param  maxSpldep (Integer)
     */
    public int setMaxSpldep(Integer maxSpldep) {
        try {
            setMaxSpldep(maxSpldep.floatValue());
        } catch (Exception e) {
            setMaxSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return maxSpldepError;
    } // method setMaxSpldep

    /**
     * Set the 'maxSpldep' class variable
     * @param  maxSpldep (Float)
     */
    public int setMaxSpldep(Float maxSpldep) {
        try {
            setMaxSpldep(maxSpldep.floatValue());
        } catch (Exception e) {
            setMaxSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return maxSpldepError;
    } // method setMaxSpldep

    /**
     * Set the 'maxSpldep' class variable
     * @param  maxSpldep (String)
     */
    public int setMaxSpldep(String maxSpldep) {
        try {
            setMaxSpldep(new Float(maxSpldep).floatValue());
        } catch (Exception e) {
            setMaxSpldepError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return maxSpldepError;
    } // method setMaxSpldep

    /**
     * Called when an exception has occured
     * @param  maxSpldep (String)
     */
    private void setMaxSpldepError (float maxSpldep, Exception e, int error) {
        this.maxSpldep = maxSpldep;
        maxSpldepErrorMessage = e.toString();
        maxSpldepError = error;
    } // method setMaxSpldepError


    /**
     * Set the 'lat' class variable
     * @param  lat (int)
     */
    public int setLat(int lat) {
        try {
            if ( ((lat == INTNULL) ||
                  (lat == INTNULL2)) ||
                !((lat < LAT_MN) ||
                  (lat > LAT_MX)) ) {
                this.lat = lat;
                latError = ERROR_NORMAL;
            } else {
                throw latOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatError(INTNULL, e, ERROR_LOCAL);
        } // try
        return latError;
    } // method setLat

    /**
     * Set the 'lat' class variable
     * @param  lat (Integer)
     */
    public int setLat(Integer lat) {
        try {
            setLat(lat.intValue());
        } catch (Exception e) {
            setLatError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return latError;
    } // method setLat

    /**
     * Set the 'lat' class variable
     * @param  lat (Float)
     */
    public int setLat(Float lat) {
        try {
            if (lat.floatValue() == FLOATNULL) {
                setLat(INTNULL);
            } else {
                setLat(lat.intValue());
            } // if (lat.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setLatError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return latError;
    } // method setLat

    /**
     * Set the 'lat' class variable
     * @param  lat (String)
     */
    public int setLat(String lat) {
        try {
            setLat(new Integer(lat).intValue());
        } catch (Exception e) {
            setLatError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return latError;
    } // method setLat

    /**
     * Called when an exception has occured
     * @param  lat (String)
     */
    private void setLatError (int lat, Exception e, int error) {
        this.lat = lat;
        latErrorMessage = e.toString();
        latError = error;
    } // method setLatError


    /**
     * Set the 'lon' class variable
     * @param  lon (int)
     */
    public int setLon(int lon) {
        try {
            if ( ((lon == INTNULL) ||
                  (lon == INTNULL2)) ||
                !((lon < LON_MN) ||
                  (lon > LON_MX)) ) {
                this.lon = lon;
                lonError = ERROR_NORMAL;
            } else {
                throw lonOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLonError(INTNULL, e, ERROR_LOCAL);
        } // try
        return lonError;
    } // method setLon

    /**
     * Set the 'lon' class variable
     * @param  lon (Integer)
     */
    public int setLon(Integer lon) {
        try {
            setLon(lon.intValue());
        } catch (Exception e) {
            setLonError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return lonError;
    } // method setLon

    /**
     * Set the 'lon' class variable
     * @param  lon (Float)
     */
    public int setLon(Float lon) {
        try {
            if (lon.floatValue() == FLOATNULL) {
                setLon(INTNULL);
            } else {
                setLon(lon.intValue());
            } // if (lon.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setLonError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return lonError;
    } // method setLon

    /**
     * Set the 'lon' class variable
     * @param  lon (String)
     */
    public int setLon(String lon) {
        try {
            setLon(new Integer(lon).intValue());
        } catch (Exception e) {
            setLonError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return lonError;
    } // method setLon

    /**
     * Called when an exception has occured
     * @param  lon (String)
     */
    private void setLonError (int lon, Exception e, int error) {
        this.lon = lon;
        lonErrorMessage = e.toString();
        lonError = error;
    } // method setLonError


    /**
     * Set the 'yearmon' class variable
     * @param  yearmon (String)
     */
    public int setYearmon(String yearmon) {
        try {
            this.yearmon = yearmon;
            if (this.yearmon != CHARNULL) {
                this.yearmon = stripCRLF(this.yearmon.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>yearmon = " + this.yearmon);
        } catch (Exception e) {
            setYearmonError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return yearmonError;
    } // method setYearmon

    /**
     * Called when an exception has occured
     * @param  yearmon (String)
     */
    private void setYearmonError (String yearmon, Exception e, int error) {
        this.yearmon = yearmon;
        yearmonErrorMessage = e.toString();
        yearmonError = error;
    } // method setYearmonError


    /**
     * Set the 'statusCode' class variable
     * @param  statusCode (int)
     */
    public int setStatusCode(int statusCode) {
        try {
            if ( ((statusCode == INTNULL) ||
                  (statusCode == INTNULL2)) ||
                !((statusCode < STATUS_CODE_MN) ||
                  (statusCode > STATUS_CODE_MX)) ) {
                this.statusCode = statusCode;
                statusCodeError = ERROR_NORMAL;
            } else {
                throw statusCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStatusCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return statusCodeError;
    } // method setStatusCode

    /**
     * Set the 'statusCode' class variable
     * @param  statusCode (Integer)
     */
    public int setStatusCode(Integer statusCode) {
        try {
            setStatusCode(statusCode.intValue());
        } catch (Exception e) {
            setStatusCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return statusCodeError;
    } // method setStatusCode

    /**
     * Set the 'statusCode' class variable
     * @param  statusCode (Float)
     */
    public int setStatusCode(Float statusCode) {
        try {
            if (statusCode.floatValue() == FLOATNULL) {
                setStatusCode(INTNULL);
            } else {
                setStatusCode(statusCode.intValue());
            } // if (statusCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setStatusCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return statusCodeError;
    } // method setStatusCode

    /**
     * Set the 'statusCode' class variable
     * @param  statusCode (String)
     */
    public int setStatusCode(String statusCode) {
        try {
            setStatusCode(new Integer(statusCode).intValue());
        } catch (Exception e) {
            setStatusCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return statusCodeError;
    } // method setStatusCode

    /**
     * Called when an exception has occured
     * @param  statusCode (String)
     */
    private void setStatusCodeError (int statusCode, Exception e, int error) {
        this.statusCode = statusCode;
        statusCodeErrorMessage = e.toString();
        statusCodeError = error;
    } // method setStatusCodeError


    /**
     * Set the 'stnRef' class variable
     * @param  stnRef (String)
     */
    public int setStnRef(String stnRef) {
        try {
            this.stnRef = stnRef;
            if (this.stnRef != CHARNULL) {
                this.stnRef = stripCRLF(this.stnRef.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>stnRef = " + this.stnRef);
        } catch (Exception e) {
            setStnRefError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return stnRefError;
    } // method setStnRef

    /**
     * Called when an exception has occured
     * @param  stnRef (String)
     */
    private void setStnRefError (String stnRef, Exception e, int error) {
        this.stnRef = stnRef;
        stnRefErrorMessage = e.toString();
        stnRefError = error;
    } // method setStnRefError


    /**
     * Set the 'notes' class variable
     * @param  notes (String)
     */
    public int setNotes(String notes) {
        try {
            this.notes = notes;
            if (this.notes != CHARNULL) {
                this.notes = stripCRLF(this.notes.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>notes = " + this.notes);
        } catch (Exception e) {
            setNotesError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return notesError;
    } // method setNotes

    /**
     * Called when an exception has occured
     * @param  notes (String)
     */
    private void setNotesError (String notes, Exception e, int error) {
        this.notes = notes;
        notesErrorMessage = e.toString();
        notesError = error;
    } // method setNotesError


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
     * Return the 'daynull' class variable
     * @return daynull (String)
     */
    public String getDaynull() {
        return daynull;
    } // method getDaynull

    /**
     * Return the 'daynull' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDaynull methods
     * @return daynull (String)
     */
    public String getDaynull(String s) {
        return (daynull != CHARNULL ? daynull.replace('"','\'') : "");
    } // method getDaynull


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
     * Return the 'offshd' class variable
     * @return offshd (float)
     */
    public float getOffshd() {
        return offshd;
    } // method getOffshd

    /**
     * Return the 'offshd' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getOffshd methods
     * @return offshd (String)
     */
    public String getOffshd(String s) {
        return ((offshd != FLOATNULL) ? new Float(offshd).toString() : "");
    } // method getOffshd


    /**
     * Return the 'passkey' class variable
     * @return passkey (String)
     */
    public String getPasskey() {
        return passkey;
    } // method getPasskey

    /**
     * Return the 'passkey' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPasskey methods
     * @return passkey (String)
     */
    public String getPasskey(String s) {
        return (passkey != CHARNULL ? passkey.replace('"','\'') : "");
    } // method getPasskey


    /**
     * Return the 'dupflag' class variable
     * @return dupflag (String)
     */
    public String getDupflag() {
        return dupflag;
    } // method getDupflag

    /**
     * Return the 'dupflag' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDupflag methods
     * @return dupflag (String)
     */
    public String getDupflag(String s) {
        return (dupflag != CHARNULL ? dupflag.replace('"','\'') : "");
    } // method getDupflag


    /**
     * Return the 'maxSpldep' class variable
     * @return maxSpldep (float)
     */
    public float getMaxSpldep() {
        return maxSpldep;
    } // method getMaxSpldep

    /**
     * Return the 'maxSpldep' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMaxSpldep methods
     * @return maxSpldep (String)
     */
    public String getMaxSpldep(String s) {
        return ((maxSpldep != FLOATNULL) ? new Float(maxSpldep).toString() : "");
    } // method getMaxSpldep


    /**
     * Return the 'lat' class variable
     * @return lat (int)
     */
    public int getLat() {
        return lat;
    } // method getLat

    /**
     * Return the 'lat' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLat methods
     * @return lat (String)
     */
    public String getLat(String s) {
        return ((lat != INTNULL) ? new Integer(lat).toString() : "");
    } // method getLat


    /**
     * Return the 'lon' class variable
     * @return lon (int)
     */
    public int getLon() {
        return lon;
    } // method getLon

    /**
     * Return the 'lon' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLon methods
     * @return lon (String)
     */
    public String getLon(String s) {
        return ((lon != INTNULL) ? new Integer(lon).toString() : "");
    } // method getLon


    /**
     * Return the 'yearmon' class variable
     * @return yearmon (String)
     */
    public String getYearmon() {
        return yearmon;
    } // method getYearmon

    /**
     * Return the 'yearmon' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getYearmon methods
     * @return yearmon (String)
     */
    public String getYearmon(String s) {
        return (yearmon != CHARNULL ? yearmon.replace('"','\'') : "");
    } // method getYearmon


    /**
     * Return the 'statusCode' class variable
     * @return statusCode (int)
     */
    public int getStatusCode() {
        return statusCode;
    } // method getStatusCode

    /**
     * Return the 'statusCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStatusCode methods
     * @return statusCode (String)
     */
    public String getStatusCode(String s) {
        return ((statusCode != INTNULL) ? new Integer(statusCode).toString() : "");
    } // method getStatusCode


    /**
     * Return the 'stnRef' class variable
     * @return stnRef (String)
     */
    public String getStnRef() {
        return stnRef;
    } // method getStnRef

    /**
     * Return the 'stnRef' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStnRef methods
     * @return stnRef (String)
     */
    public String getStnRef(String s) {
        return (stnRef != CHARNULL ? stnRef.replace('"','\'') : "");
    } // method getStnRef


    /**
     * Return the 'notes' class variable
     * @return notes (String)
     */
    public String getNotes() {
        return notes;
    } // method getNotes

    /**
     * Return the 'notes' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNotes methods
     * @return notes (String)
     */
    public String getNotes(String s) {
        return (notes != CHARNULL ? notes.replace('"','\'') : "");
    } // method getNotes


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
            (surveyId == CHARNULL) &&
            (latitude == FLOATNULL) &&
            (longitude == FLOATNULL) &&
            (dateStart.equals(DATENULL)) &&
            (dateEnd.equals(DATENULL)) &&
            (daynull == CHARNULL) &&
            (stnnam == CHARNULL) &&
            (stndep == FLOATNULL) &&
            (offshd == FLOATNULL) &&
            (passkey == CHARNULL) &&
            (dupflag == CHARNULL) &&
            (maxSpldep == FLOATNULL) &&
            (lat == INTNULL) &&
            (lon == INTNULL) &&
            (yearmon == CHARNULL) &&
            (statusCode == INTNULL) &&
            (stnRef == CHARNULL) &&
            (notes == CHARNULL)) {
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
            surveyIdError +
            latitudeError +
            longitudeError +
            dateStartError +
            dateEndError +
            daynullError +
            stnnamError +
            stndepError +
            offshdError +
            passkeyError +
            dupflagError +
            maxSpldepError +
            latError +
            lonError +
            yearmonError +
            statusCodeError +
            stnRefError +
            notesError;
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
     * Gets the errorcode for the daynull instance variable
     * @return errorcode (int)
     */
    public int getDaynullError() {
        return daynullError;
    } // method getDaynullError

    /**
     * Gets the errorMessage for the daynull instance variable
     * @return errorMessage (String)
     */
    public String getDaynullErrorMessage() {
        return daynullErrorMessage;
    } // method getDaynullErrorMessage


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
     * Gets the errorcode for the offshd instance variable
     * @return errorcode (int)
     */
    public int getOffshdError() {
        return offshdError;
    } // method getOffshdError

    /**
     * Gets the errorMessage for the offshd instance variable
     * @return errorMessage (String)
     */
    public String getOffshdErrorMessage() {
        return offshdErrorMessage;
    } // method getOffshdErrorMessage


    /**
     * Gets the errorcode for the passkey instance variable
     * @return errorcode (int)
     */
    public int getPasskeyError() {
        return passkeyError;
    } // method getPasskeyError

    /**
     * Gets the errorMessage for the passkey instance variable
     * @return errorMessage (String)
     */
    public String getPasskeyErrorMessage() {
        return passkeyErrorMessage;
    } // method getPasskeyErrorMessage


    /**
     * Gets the errorcode for the dupflag instance variable
     * @return errorcode (int)
     */
    public int getDupflagError() {
        return dupflagError;
    } // method getDupflagError

    /**
     * Gets the errorMessage for the dupflag instance variable
     * @return errorMessage (String)
     */
    public String getDupflagErrorMessage() {
        return dupflagErrorMessage;
    } // method getDupflagErrorMessage


    /**
     * Gets the errorcode for the maxSpldep instance variable
     * @return errorcode (int)
     */
    public int getMaxSpldepError() {
        return maxSpldepError;
    } // method getMaxSpldepError

    /**
     * Gets the errorMessage for the maxSpldep instance variable
     * @return errorMessage (String)
     */
    public String getMaxSpldepErrorMessage() {
        return maxSpldepErrorMessage;
    } // method getMaxSpldepErrorMessage


    /**
     * Gets the errorcode for the lat instance variable
     * @return errorcode (int)
     */
    public int getLatError() {
        return latError;
    } // method getLatError

    /**
     * Gets the errorMessage for the lat instance variable
     * @return errorMessage (String)
     */
    public String getLatErrorMessage() {
        return latErrorMessage;
    } // method getLatErrorMessage


    /**
     * Gets the errorcode for the lon instance variable
     * @return errorcode (int)
     */
    public int getLonError() {
        return lonError;
    } // method getLonError

    /**
     * Gets the errorMessage for the lon instance variable
     * @return errorMessage (String)
     */
    public String getLonErrorMessage() {
        return lonErrorMessage;
    } // method getLonErrorMessage


    /**
     * Gets the errorcode for the yearmon instance variable
     * @return errorcode (int)
     */
    public int getYearmonError() {
        return yearmonError;
    } // method getYearmonError

    /**
     * Gets the errorMessage for the yearmon instance variable
     * @return errorMessage (String)
     */
    public String getYearmonErrorMessage() {
        return yearmonErrorMessage;
    } // method getYearmonErrorMessage


    /**
     * Gets the errorcode for the statusCode instance variable
     * @return errorcode (int)
     */
    public int getStatusCodeError() {
        return statusCodeError;
    } // method getStatusCodeError

    /**
     * Gets the errorMessage for the statusCode instance variable
     * @return errorMessage (String)
     */
    public String getStatusCodeErrorMessage() {
        return statusCodeErrorMessage;
    } // method getStatusCodeErrorMessage


    /**
     * Gets the errorcode for the stnRef instance variable
     * @return errorcode (int)
     */
    public int getStnRefError() {
        return stnRefError;
    } // method getStnRefError

    /**
     * Gets the errorMessage for the stnRef instance variable
     * @return errorMessage (String)
     */
    public String getStnRefErrorMessage() {
        return stnRefErrorMessage;
    } // method getStnRefErrorMessage


    /**
     * Gets the errorcode for the notes instance variable
     * @return errorcode (int)
     */
    public int getNotesError() {
        return notesError;
    } // method getNotesError

    /**
     * Gets the errorMessage for the notes instance variable
     * @return errorMessage (String)
     */
    public String getNotesErrorMessage() {
        return notesErrorMessage;
    } // method getNotesErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnStation. e.g.<pre>
     * MrnStation station = new MrnStation(<val1>);
     * MrnStation stationArray[] = station.get();</pre>
     * will get the MrnStation record where stationId = <val1>.
     * @return Array of MrnStation (MrnStation[])
     */
    public MrnStation[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnStation stationArray[] =
     *     new MrnStation().get(MrnStation.STATION_ID+"=<val1>");</pre>
     * will get the MrnStation record where stationId = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnStation (MrnStation[])
     */
    public MrnStation[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnStation stationArray[] =
     *     new MrnStation().get("1=1",station.STATION_ID);</pre>
     * will get the all the MrnStation records, and order them by stationId.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnStation (MrnStation[])
     */
    public MrnStation[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnStation.STATION_ID,MrnStation.SURVEY_ID;
     * String where = MrnStation.STATION_ID + "=<val1";
     * String order = MrnStation.STATION_ID;
     * MrnStation stationArray[] =
     *     new MrnStation().get(columns, where, order);</pre>
     * will get the stationId and surveyId colums of all MrnStation records,
     * where stationId = <val1>, and order them by stationId.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnStation (MrnStation[])
     */
    public MrnStation[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnStation.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnStation[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int stationIdCol  = db.getColNumber(STATION_ID);
        int surveyIdCol   = db.getColNumber(SURVEY_ID);
        int latitudeCol   = db.getColNumber(LATITUDE);
        int longitudeCol  = db.getColNumber(LONGITUDE);
        int dateStartCol  = db.getColNumber(DATE_START);
        int dateEndCol    = db.getColNumber(DATE_END);
        int daynullCol    = db.getColNumber(DAYNULL);
        int stnnamCol     = db.getColNumber(STNNAM);
        int stndepCol     = db.getColNumber(STNDEP);
        int offshdCol     = db.getColNumber(OFFSHD);
        int passkeyCol    = db.getColNumber(PASSKEY);
        int dupflagCol    = db.getColNumber(DUPFLAG);
        int maxSpldepCol  = db.getColNumber(MAX_SPLDEP);
        int latCol        = db.getColNumber(LAT);
        int lonCol        = db.getColNumber(LON);
        int yearmonCol    = db.getColNumber(YEARMON);
        int statusCodeCol = db.getColNumber(STATUS_CODE);
        int stnRefCol     = db.getColNumber(STN_REF);
        int notesCol      = db.getColNumber(NOTES);
        MrnStation[] cArray = new MrnStation[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnStation();
            if (stationIdCol != -1)
                cArray[i].setStationId ((String) row.elementAt(stationIdCol));
            if (surveyIdCol != -1)
                cArray[i].setSurveyId  ((String) row.elementAt(surveyIdCol));
            if (latitudeCol != -1)
                cArray[i].setLatitude  ((String) row.elementAt(latitudeCol));
            if (longitudeCol != -1)
                cArray[i].setLongitude ((String) row.elementAt(longitudeCol));
            if (dateStartCol != -1)
                cArray[i].setDateStart ((String) row.elementAt(dateStartCol));
            if (dateEndCol != -1)
                cArray[i].setDateEnd   ((String) row.elementAt(dateEndCol));
            if (daynullCol != -1)
                cArray[i].setDaynull   ((String) row.elementAt(daynullCol));
            if (stnnamCol != -1)
                cArray[i].setStnnam    ((String) row.elementAt(stnnamCol));
            if (stndepCol != -1)
                cArray[i].setStndep    ((String) row.elementAt(stndepCol));
            if (offshdCol != -1)
                cArray[i].setOffshd    ((String) row.elementAt(offshdCol));
            if (passkeyCol != -1)
                cArray[i].setPasskey   ((String) row.elementAt(passkeyCol));
            if (dupflagCol != -1)
                cArray[i].setDupflag   ((String) row.elementAt(dupflagCol));
            if (maxSpldepCol != -1)
                cArray[i].setMaxSpldep ((String) row.elementAt(maxSpldepCol));
            if (latCol != -1)
                cArray[i].setLat       ((String) row.elementAt(latCol));
            if (lonCol != -1)
                cArray[i].setLon       ((String) row.elementAt(lonCol));
            if (yearmonCol != -1)
                cArray[i].setYearmon   ((String) row.elementAt(yearmonCol));
            if (statusCodeCol != -1)
                cArray[i].setStatusCode((String) row.elementAt(statusCodeCol));
            if (stnRefCol != -1)
                cArray[i].setStnRef    ((String) row.elementAt(stnRefCol));
            if (notesCol != -1)
                cArray[i].setNotes     ((String) row.elementAt(notesCol));
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
     *     new MrnStation(
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
     *         <val14>,
     *         <val15>,
     *         <val16>,
     *         <val17>,
     *         <val18>,
     *         <val19>).put();</pre>
     * will insert a record with:
     *     stationId  = <val1>,
     *     surveyId   = <val2>,
     *     latitude   = <val3>,
     *     longitude  = <val4>,
     *     dateStart  = <val5>,
     *     dateEnd    = <val6>,
     *     daynull    = <val7>,
     *     stnnam     = <val8>,
     *     stndep     = <val9>,
     *     offshd     = <val10>,
     *     passkey    = <val11>,
     *     dupflag    = <val12>,
     *     maxSpldep  = <val13>,
     *     lat        = <val14>,
     *     lon        = <val15>,
     *     yearmon    = <val16>,
     *     statusCode = <val17>,
     *     stnRef     = <val18>,
     *     notes      = <val19>.
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
     * Boolean success = new MrnStation(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.DATENULL,
     *     Tables.DATENULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where surveyId = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnStation station = new MrnStation();
     * success = station.del(MrnStation.STATION_ID+"=<val1>");</pre>
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
     * update are taken from the MrnStation argument, .e.g.<pre>
     * Boolean success
     * MrnStation updMrnStation = new MrnStation();
     * updMrnStation.setSurveyId(<val2>);
     * MrnStation whereMrnStation = new MrnStation(<val1>);
     * success = whereMrnStation.upd(updMrnStation);</pre>
     * will update SurveyId to <val2> for all records where
     * stationId = <val1>.
     * @param  station  A MrnStation variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnStation station) {
        return db.update (TABLE, createColVals(station), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnStation updMrnStation = new MrnStation();
     * updMrnStation.setSurveyId(<val2>);
     * MrnStation whereMrnStation = new MrnStation();
     * success = whereMrnStation.upd(
     *     updMrnStation, MrnStation.STATION_ID+"=<val1>");</pre>
     * will update SurveyId to <val2> for all records where
     * stationId = <val1>.
     * @param  station  A MrnStation variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnStation station, String where) {
        return db.update (TABLE, createColVals(station), where);
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
        if (getSurveyId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_ID + "='" + getSurveyId() + "'";
        } // if getSurveyId
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
        if (getDaynull() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DAYNULL + "='" + getDaynull() + "'";
        } // if getDaynull
        if (getStnnam() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STNNAM + "='" + getStnnam() + "'";
        } // if getStnnam
        if (getStndep() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STNDEP + "=" + getStndep("");
        } // if getStndep
        if (getOffshd() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + OFFSHD + "=" + getOffshd("");
        } // if getOffshd
        if (getPasskey() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PASSKEY + "='" + getPasskey() + "'";
        } // if getPasskey
        if (getDupflag() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DUPFLAG + "='" + getDupflag() + "'";
        } // if getDupflag
        if (getMaxSpldep() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MAX_SPLDEP + "=" + getMaxSpldep("");
        } // if getMaxSpldep
        if (getLat() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LAT + "=" + getLat("");
        } // if getLat
        if (getLon() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LON + "=" + getLon("");
        } // if getLon
        if (getYearmon() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + YEARMON + "='" + getYearmon() + "'";
        } // if getYearmon
        if (getStatusCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATUS_CODE + "=" + getStatusCode("");
        } // if getStatusCode
        if (getStnRef() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STN_REF + "='" + getStnRef() + "'";
        } // if getStnRef
        if (getNotes() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NOTES + "='" + getNotes() + "'";
        } // if getNotes
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnStation aVar) {
        String colVals = "";
        if (aVar.getStationId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATION_ID +"=";
            colVals += (aVar.getStationId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStationId() + "'");
        } // if aVar.getStationId
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
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
        if (aVar.getDaynull() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DAYNULL +"=";
            colVals += (aVar.getDaynull().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDaynull() + "'");
        } // if aVar.getDaynull
        if (aVar.getStnnam() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STNNAM +"=";
            colVals += (aVar.getStnnam().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStnnam() + "'");
        } // if aVar.getStnnam
        if (aVar.getStndep() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STNDEP +"=";
            colVals += (aVar.getStndep() == FLOATNULL2 ?
                "null" : aVar.getStndep(""));
        } // if aVar.getStndep
        if (aVar.getOffshd() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += OFFSHD +"=";
            colVals += (aVar.getOffshd() == FLOATNULL2 ?
                "null" : aVar.getOffshd(""));
        } // if aVar.getOffshd
        if (aVar.getPasskey() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PASSKEY +"=";
            colVals += (aVar.getPasskey().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPasskey() + "'");
        } // if aVar.getPasskey
        if (aVar.getDupflag() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DUPFLAG +"=";
            colVals += (aVar.getDupflag().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDupflag() + "'");
        } // if aVar.getDupflag
        if (aVar.getMaxSpldep() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MAX_SPLDEP +"=";
            colVals += (aVar.getMaxSpldep() == FLOATNULL2 ?
                "null" : aVar.getMaxSpldep(""));
        } // if aVar.getMaxSpldep
        if (aVar.getLat() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LAT +"=";
            colVals += (aVar.getLat() == INTNULL2 ?
                "null" : aVar.getLat(""));
        } // if aVar.getLat
        if (aVar.getLon() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LON +"=";
            colVals += (aVar.getLon() == INTNULL2 ?
                "null" : aVar.getLon(""));
        } // if aVar.getLon
        if (aVar.getYearmon() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += YEARMON +"=";
            colVals += (aVar.getYearmon().equals(CHARNULL2) ?
                "null" : "'" + aVar.getYearmon() + "'");
        } // if aVar.getYearmon
        if (aVar.getStatusCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATUS_CODE +"=";
            colVals += (aVar.getStatusCode() == INTNULL2 ?
                "null" : aVar.getStatusCode(""));
        } // if aVar.getStatusCode
        if (aVar.getStnRef() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STN_REF +"=";
            colVals += (aVar.getStnRef().equals(CHARNULL2) ?
                "null" : "'" + aVar.getStnRef() + "'");
        } // if aVar.getStnRef
        if (aVar.getNotes() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NOTES +"=";
            colVals += (aVar.getNotes().equals(CHARNULL2) ?
                "null" : "'" + aVar.getNotes() + "'");
        } // if aVar.getNotes
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = STATION_ID;
        if (getSurveyId() != CHARNULL) {
            columns = columns + "," + SURVEY_ID;
        } // if getSurveyId
        if (getLatitude() != FLOATNULL) {
            columns = columns + "," + LATITUDE;
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            columns = columns + "," + LONGITUDE;
        } // if getLongitude
        if (!getDateStart().equals(DATENULL)) {
            columns = columns + "," + DATE_START;
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            columns = columns + "," + DATE_END;
        } // if getDateEnd
        if (getDaynull() != CHARNULL) {
            columns = columns + "," + DAYNULL;
        } // if getDaynull
        if (getStnnam() != CHARNULL) {
            columns = columns + "," + STNNAM;
        } // if getStnnam
        if (getStndep() != FLOATNULL) {
            columns = columns + "," + STNDEP;
        } // if getStndep
        if (getOffshd() != FLOATNULL) {
            columns = columns + "," + OFFSHD;
        } // if getOffshd
        if (getPasskey() != CHARNULL) {
            columns = columns + "," + PASSKEY;
        } // if getPasskey
        if (getDupflag() != CHARNULL) {
            columns = columns + "," + DUPFLAG;
        } // if getDupflag
        if (getMaxSpldep() != FLOATNULL) {
            columns = columns + "," + MAX_SPLDEP;
        } // if getMaxSpldep
        if (getLat() != INTNULL) {
            columns = columns + "," + LAT;
        } // if getLat
        if (getLon() != INTNULL) {
            columns = columns + "," + LON;
        } // if getLon
        if (getYearmon() != CHARNULL) {
            columns = columns + "," + YEARMON;
        } // if getYearmon
        if (getStatusCode() != INTNULL) {
            columns = columns + "," + STATUS_CODE;
        } // if getStatusCode
        if (getStnRef() != CHARNULL) {
            columns = columns + "," + STN_REF;
        } // if getStnRef
        if (getNotes() != CHARNULL) {
            columns = columns + "," + NOTES;
        } // if getNotes
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getStationId() + "'";
        if (getSurveyId() != CHARNULL) {
            values  = values  + ",'" + getSurveyId() + "'";
        } // if getSurveyId
        if (getLatitude() != FLOATNULL) {
            values  = values  + "," + getLatitude("");
        } // if getLatitude
        if (getLongitude() != FLOATNULL) {
            values  = values  + "," + getLongitude("");
        } // if getLongitude
        if (!getDateStart().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateStart());
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateEnd());
        } // if getDateEnd
        if (getDaynull() != CHARNULL) {
            values  = values  + ",'" + getDaynull() + "'";
        } // if getDaynull
        if (getStnnam() != CHARNULL) {
            values  = values  + ",'" + getStnnam() + "'";
        } // if getStnnam
        if (getStndep() != FLOATNULL) {
            values  = values  + "," + getStndep("");
        } // if getStndep
        if (getOffshd() != FLOATNULL) {
            values  = values  + "," + getOffshd("");
        } // if getOffshd
        if (getPasskey() != CHARNULL) {
            values  = values  + ",'" + getPasskey() + "'";
        } // if getPasskey
        if (getDupflag() != CHARNULL) {
            values  = values  + ",'" + getDupflag() + "'";
        } // if getDupflag
        if (getMaxSpldep() != FLOATNULL) {
            values  = values  + "," + getMaxSpldep("");
        } // if getMaxSpldep
        if (getLat() != INTNULL) {
            values  = values  + "," + getLat("");
        } // if getLat
        if (getLon() != INTNULL) {
            values  = values  + "," + getLon("");
        } // if getLon
        if (getYearmon() != CHARNULL) {
            values  = values  + ",'" + getYearmon() + "'";
        } // if getYearmon
        if (getStatusCode() != INTNULL) {
            values  = values  + "," + getStatusCode("");
        } // if getStatusCode
        if (getStnRef() != CHARNULL) {
            values  = values  + ",'" + getStnRef() + "'";
        } // if getStnRef
        if (getNotes() != CHARNULL) {
            values  = values  + ",'" + getNotes() + "'";
        } // if getNotes
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getStationId("")  + "|" +
            getSurveyId("")   + "|" +
            getLatitude("")   + "|" +
            getLongitude("")  + "|" +
            getDateStart("")  + "|" +
            getDateEnd("")    + "|" +
            getDaynull("")    + "|" +
            getStnnam("")     + "|" +
            getStndep("")     + "|" +
            getOffshd("")     + "|" +
            getPasskey("")    + "|" +
            getDupflag("")    + "|" +
            getMaxSpldep("")  + "|" +
            getLat("")        + "|" +
            getLon("")        + "|" +
            getYearmon("")    + "|" +
            getStatusCode("") + "|" +
            getStnRef("")     + "|" +
            getNotes("")      + "|";
    } // method toString

} // class MrnStation
