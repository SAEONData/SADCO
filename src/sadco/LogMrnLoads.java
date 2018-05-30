package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the LOG_MRN_LOADS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class LogMrnLoads extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "LOG_MRN_LOADS";
    /** The loadid field name */
    public static final String LOADID = "LOADID";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The source field name */
    public static final String SOURCE = "SOURCE";
    /** The institute field name */
    public static final String INSTITUTE = "INSTITUTE";
    /** The expnam field name */
    public static final String EXPNAM = "EXPNAM";
    /** The loadDate field name */
    public static final String LOAD_DATE = "LOAD_DATE";
    /** The flagged field name */
    public static final String FLAGGED = "FLAGGED";
    /** The recnumStart field name */
    public static final String RECNUM_START = "RECNUM_START";
    /** The recnumEnd field name */
    public static final String RECNUM_END = "RECNUM_END";
    /** The stationsLoaded field name */
    public static final String STATIONS_LOADED = "STATIONS_LOADED";
    /** The recordsLoaded field name */
    public static final String RECORDS_LOADED = "RECORDS_LOADED";
    /** The recordsRejected field name */
    public static final String RECORDS_REJECTED = "RECORDS_REJECTED";
    /** The dateStart field name */
    public static final String DATE_START = "DATE_START";
    /** The dateEnd field name */
    public static final String DATE_END = "DATE_END";
    /** The latNorth field name */
    public static final String LAT_NORTH = "LAT_NORTH";
    /** The latSouth field name */
    public static final String LAT_SOUTH = "LAT_SOUTH";
    /** The longWest field name */
    public static final String LONG_WEST = "LONG_WEST";
    /** The longEast field name */
    public static final String LONG_EAST = "LONG_EAST";
    /** The respPerson field name */
    public static final String RESP_PERSON = "RESP_PERSON";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    loadid;
    private String    surveyId;
    private String    source;
    private String    institute;
    private String    expnam;
    private Timestamp loadDate;
    private String    flagged;
    private int       recnumStart;
    private int       recnumEnd;
    private int       stationsLoaded;
    private int       recordsLoaded;
    private int       recordsRejected;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private float     latNorth;
    private float     latSouth;
    private float     longWest;
    private float     longEast;
    private String    respPerson;

    /** The error variables  */
    private int loadidError          = ERROR_NORMAL;
    private int surveyIdError        = ERROR_NORMAL;
    private int sourceError          = ERROR_NORMAL;
    private int instituteError       = ERROR_NORMAL;
    private int expnamError          = ERROR_NORMAL;
    private int loadDateError        = ERROR_NORMAL;
    private int flaggedError         = ERROR_NORMAL;
    private int recnumStartError     = ERROR_NORMAL;
    private int recnumEndError       = ERROR_NORMAL;
    private int stationsLoadedError  = ERROR_NORMAL;
    private int recordsLoadedError   = ERROR_NORMAL;
    private int recordsRejectedError = ERROR_NORMAL;
    private int dateStartError       = ERROR_NORMAL;
    private int dateEndError         = ERROR_NORMAL;
    private int latNorthError        = ERROR_NORMAL;
    private int latSouthError        = ERROR_NORMAL;
    private int longWestError        = ERROR_NORMAL;
    private int longEastError        = ERROR_NORMAL;
    private int respPersonError      = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String loadidErrorMessage          = "";
    private String surveyIdErrorMessage        = "";
    private String sourceErrorMessage          = "";
    private String instituteErrorMessage       = "";
    private String expnamErrorMessage          = "";
    private String loadDateErrorMessage        = "";
    private String flaggedErrorMessage         = "";
    private String recnumStartErrorMessage     = "";
    private String recnumEndErrorMessage       = "";
    private String stationsLoadedErrorMessage  = "";
    private String recordsLoadedErrorMessage   = "";
    private String recordsRejectedErrorMessage = "";
    private String dateStartErrorMessage       = "";
    private String dateEndErrorMessage         = "";
    private String latNorthErrorMessage        = "";
    private String latSouthErrorMessage        = "";
    private String longWestErrorMessage        = "";
    private String longEastErrorMessage        = "";
    private String respPersonErrorMessage      = "";

    /** The min-max constants for all numerics */
    public static final Timestamp LOAD_DATE_MN = DATEMIN;
    public static final Timestamp LOAD_DATE_MX = DATEMAX;
    public static final int       RECNUM_START_MN = INTMIN;
    public static final int       RECNUM_START_MX = INTMAX;
    public static final int       RECNUM_END_MN = INTMIN;
    public static final int       RECNUM_END_MX = INTMAX;
    public static final int       STATIONS_LOADED_MN = INTMIN;
    public static final int       STATIONS_LOADED_MX = INTMAX;
    public static final int       RECORDS_LOADED_MN = INTMIN;
    public static final int       RECORDS_LOADED_MX = INTMAX;
    public static final int       RECORDS_REJECTED_MN = INTMIN;
    public static final int       RECORDS_REJECTED_MX = INTMAX;
    public static final Timestamp DATE_START_MN = DATEMIN;
    public static final Timestamp DATE_START_MX = DATEMAX;
    public static final Timestamp DATE_END_MN = DATEMIN;
    public static final Timestamp DATE_END_MX = DATEMAX;
    public static final float     LAT_NORTH_MN = FLOATMIN;
    public static final float     LAT_NORTH_MX = FLOATMAX;
    public static final float     LAT_SOUTH_MN = FLOATMIN;
    public static final float     LAT_SOUTH_MX = FLOATMAX;
    public static final float     LONG_WEST_MN = FLOATMIN;
    public static final float     LONG_WEST_MX = FLOATMAX;
    public static final float     LONG_EAST_MN = FLOATMIN;
    public static final float     LONG_EAST_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception loadDateException =
        new Exception ("'loadDate' is null");
    Exception loadDateOutOfBoundsException =
        new Exception ("'loadDate' out of bounds: " +
            LOAD_DATE_MN + " - " + LOAD_DATE_MX);
    Exception recnumStartOutOfBoundsException =
        new Exception ("'recnumStart' out of bounds: " +
            RECNUM_START_MN + " - " + RECNUM_START_MX);
    Exception recnumEndOutOfBoundsException =
        new Exception ("'recnumEnd' out of bounds: " +
            RECNUM_END_MN + " - " + RECNUM_END_MX);
    Exception stationsLoadedOutOfBoundsException =
        new Exception ("'stationsLoaded' out of bounds: " +
            STATIONS_LOADED_MN + " - " + STATIONS_LOADED_MX);
    Exception recordsLoadedOutOfBoundsException =
        new Exception ("'recordsLoaded' out of bounds: " +
            RECORDS_LOADED_MN + " - " + RECORDS_LOADED_MX);
    Exception recordsRejectedOutOfBoundsException =
        new Exception ("'recordsRejected' out of bounds: " +
            RECORDS_REJECTED_MN + " - " + RECORDS_REJECTED_MX);
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
    Exception latNorthOutOfBoundsException =
        new Exception ("'latNorth' out of bounds: " +
            LAT_NORTH_MN + " - " + LAT_NORTH_MX);
    Exception latSouthOutOfBoundsException =
        new Exception ("'latSouth' out of bounds: " +
            LAT_SOUTH_MN + " - " + LAT_SOUTH_MX);
    Exception longWestOutOfBoundsException =
        new Exception ("'longWest' out of bounds: " +
            LONG_WEST_MN + " - " + LONG_WEST_MX);
    Exception longEastOutOfBoundsException =
        new Exception ("'longEast' out of bounds: " +
            LONG_EAST_MN + " - " + LONG_EAST_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public LogMrnLoads() {
        clearVars();
        if (dbg) System.out.println ("<br>in LogMrnLoads constructor 1"); // debug
    } // LogMrnLoads constructor

    /**
     * Instantiate a LogMrnLoads object and initialize the instance variables.
     * @param loadid  The loadid (String)
     */
    public LogMrnLoads(
            String loadid) {
        this();
        setLoadid          (loadid         );
        if (dbg) System.out.println ("<br>in LogMrnLoads constructor 2"); // debug
    } // LogMrnLoads constructor

    /**
     * Instantiate a LogMrnLoads object and initialize the instance variables.
     * @param loadid           The loadid          (String)
     * @param surveyId         The surveyId        (String)
     * @param source           The source          (String)
     * @param institute        The institute       (String)
     * @param expnam           The expnam          (String)
     * @param loadDate         The loadDate        (java.util.Date)
     * @param flagged          The flagged         (String)
     * @param recnumStart      The recnumStart     (int)
     * @param recnumEnd        The recnumEnd       (int)
     * @param stationsLoaded   The stationsLoaded  (int)
     * @param recordsLoaded    The recordsLoaded   (int)
     * @param recordsRejected  The recordsRejected (int)
     * @param dateStart        The dateStart       (java.util.Date)
     * @param dateEnd          The dateEnd         (java.util.Date)
     * @param latNorth         The latNorth        (float)
     * @param latSouth         The latSouth        (float)
     * @param longWest         The longWest        (float)
     * @param longEast         The longEast        (float)
     * @param respPerson       The respPerson      (String)
     */
    public LogMrnLoads(
            String         loadid,
            String         surveyId,
            String         source,
            String         institute,
            String         expnam,
            java.util.Date loadDate,
            String         flagged,
            int            recnumStart,
            int            recnumEnd,
            int            stationsLoaded,
            int            recordsLoaded,
            int            recordsRejected,
            java.util.Date dateStart,
            java.util.Date dateEnd,
            float          latNorth,
            float          latSouth,
            float          longWest,
            float          longEast,
            String         respPerson) {
        this();
        setLoadid          (loadid         );
        setSurveyId        (surveyId       );
        setSource          (source         );
        setInstitute       (institute      );
        setExpnam          (expnam         );
        setLoadDate        (loadDate       );
        setFlagged         (flagged        );
        setRecnumStart     (recnumStart    );
        setRecnumEnd       (recnumEnd      );
        setStationsLoaded  (stationsLoaded );
        setRecordsLoaded   (recordsLoaded  );
        setRecordsRejected (recordsRejected);
        setDateStart       (dateStart      );
        setDateEnd         (dateEnd        );
        setLatNorth        (latNorth       );
        setLatSouth        (latSouth       );
        setLongWest        (longWest       );
        setLongEast        (longEast       );
        setRespPerson      (respPerson     );
        if (dbg) System.out.println ("<br>in LogMrnLoads constructor 3"); // debug
    } // LogMrnLoads constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setLoadid          (CHARNULL );
        setSurveyId        (CHARNULL );
        setSource          (CHARNULL );
        setInstitute       (CHARNULL );
        setExpnam          (CHARNULL );
        setLoadDate        (DATENULL );
        setFlagged         (CHARNULL );
        setRecnumStart     (INTNULL  );
        setRecnumEnd       (INTNULL  );
        setStationsLoaded  (INTNULL  );
        setRecordsLoaded   (INTNULL  );
        setRecordsRejected (INTNULL  );
        setDateStart       (DATENULL );
        setDateEnd         (DATENULL );
        setLatNorth        (FLOATNULL);
        setLatSouth        (FLOATNULL);
        setLongWest        (FLOATNULL);
        setLongEast        (FLOATNULL);
        setRespPerson      (CHARNULL );
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
     * Set the 'institute' class variable
     * @param  institute (String)
     */
    public int setInstitute(String institute) {
        try {
            this.institute = institute;
            if (this.institute != CHARNULL) {
                this.institute = stripCRLF(this.institute.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>institute = " + this.institute);
        } catch (Exception e) {
            setInstituteError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return instituteError;
    } // method setInstitute

    /**
     * Called when an exception has occured
     * @param  institute (String)
     */
    private void setInstituteError (String institute, Exception e, int error) {
        this.institute = institute;
        instituteErrorMessage = e.toString();
        instituteError = error;
    } // method setInstituteError


    /**
     * Set the 'expnam' class variable
     * @param  expnam (String)
     */
    public int setExpnam(String expnam) {
        try {
            this.expnam = expnam;
            if (this.expnam != CHARNULL) {
                this.expnam = stripCRLF(this.expnam.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>expnam = " + this.expnam);
        } catch (Exception e) {
            setExpnamError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return expnamError;
    } // method setExpnam

    /**
     * Called when an exception has occured
     * @param  expnam (String)
     */
    private void setExpnamError (String expnam, Exception e, int error) {
        this.expnam = expnam;
        expnamErrorMessage = e.toString();
        expnamError = error;
    } // method setExpnamError


    /**
     * Set the 'loadDate' class variable
     * @param  loadDate (Timestamp)
     */
    public int setLoadDate(Timestamp loadDate) {
        try {
            if (loadDate == null) { this.loadDate = DATENULL; }
            if ( (loadDate.equals(DATENULL) ||
                  loadDate.equals(DATENULL2)) ||
                !(loadDate.before(LOAD_DATE_MN) ||
                  loadDate.after(LOAD_DATE_MX)) ) {
                this.loadDate = loadDate;
                loadDateError = ERROR_NORMAL;
            } else {
                throw loadDateOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLoadDateError(DATENULL, e, ERROR_LOCAL);
        } // try
        return loadDateError;
    } // method setLoadDate

    /**
     * Set the 'loadDate' class variable
     * @param  loadDate (java.util.Date)
     */
    public int setLoadDate(java.util.Date loadDate) {
        try {
            setLoadDate(new Timestamp(loadDate.getTime()));
        } catch (Exception e) {
            setLoadDateError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return loadDateError;
    } // method setLoadDate

    /**
     * Set the 'loadDate' class variable
     * @param  loadDate (String)
     */
    public int setLoadDate(String loadDate) {
        try {
            int len = loadDate.length();
            switch (len) {
            // date &/or times
                case 4: loadDate += "-01";
                case 7: loadDate += "-01";
                case 10: loadDate += " 00";
                case 13: loadDate += ":00";
                case 16: loadDate += ":00"; break;
                // times only
                case 5: loadDate = "1800-01-01 " + loadDate + ":00"; break;
                case 8: loadDate = "1800-01-01 " + loadDate; break;
            } // switch
            if (dbg) System.out.println ("loadDate = " + loadDate);
            setLoadDate(Timestamp.valueOf(loadDate));
        } catch (Exception e) {
            setLoadDateError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return loadDateError;
    } // method setLoadDate

    /**
     * Called when an exception has occured
     * @param  loadDate (String)
     */
    private void setLoadDateError (Timestamp loadDate, Exception e, int error) {
        this.loadDate = loadDate;
        loadDateErrorMessage = e.toString();
        loadDateError = error;
    } // method setLoadDateError


    /**
     * Set the 'flagged' class variable
     * @param  flagged (String)
     */
    public int setFlagged(String flagged) {
        try {
            this.flagged = flagged;
            if (this.flagged != CHARNULL) {
                this.flagged = stripCRLF(this.flagged.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>flagged = " + this.flagged);
        } catch (Exception e) {
            setFlaggedError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return flaggedError;
    } // method setFlagged

    /**
     * Called when an exception has occured
     * @param  flagged (String)
     */
    private void setFlaggedError (String flagged, Exception e, int error) {
        this.flagged = flagged;
        flaggedErrorMessage = e.toString();
        flaggedError = error;
    } // method setFlaggedError


    /**
     * Set the 'recnumStart' class variable
     * @param  recnumStart (int)
     */
    public int setRecnumStart(int recnumStart) {
        try {
            if ( ((recnumStart == INTNULL) ||
                  (recnumStart == INTNULL2)) ||
                !((recnumStart < RECNUM_START_MN) ||
                  (recnumStart > RECNUM_START_MX)) ) {
                this.recnumStart = recnumStart;
                recnumStartError = ERROR_NORMAL;
            } else {
                throw recnumStartOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setRecnumStartError(INTNULL, e, ERROR_LOCAL);
        } // try
        return recnumStartError;
    } // method setRecnumStart

    /**
     * Set the 'recnumStart' class variable
     * @param  recnumStart (Integer)
     */
    public int setRecnumStart(Integer recnumStart) {
        try {
            setRecnumStart(recnumStart.intValue());
        } catch (Exception e) {
            setRecnumStartError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recnumStartError;
    } // method setRecnumStart

    /**
     * Set the 'recnumStart' class variable
     * @param  recnumStart (Float)
     */
    public int setRecnumStart(Float recnumStart) {
        try {
            if (recnumStart.floatValue() == FLOATNULL) {
                setRecnumStart(INTNULL);
            } else {
                setRecnumStart(recnumStart.intValue());
            } // if (recnumStart.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setRecnumStartError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recnumStartError;
    } // method setRecnumStart

    /**
     * Set the 'recnumStart' class variable
     * @param  recnumStart (String)
     */
    public int setRecnumStart(String recnumStart) {
        try {
            setRecnumStart(new Integer(recnumStart).intValue());
        } catch (Exception e) {
            setRecnumStartError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recnumStartError;
    } // method setRecnumStart

    /**
     * Called when an exception has occured
     * @param  recnumStart (String)
     */
    private void setRecnumStartError (int recnumStart, Exception e, int error) {
        this.recnumStart = recnumStart;
        recnumStartErrorMessage = e.toString();
        recnumStartError = error;
    } // method setRecnumStartError


    /**
     * Set the 'recnumEnd' class variable
     * @param  recnumEnd (int)
     */
    public int setRecnumEnd(int recnumEnd) {
        try {
            if ( ((recnumEnd == INTNULL) ||
                  (recnumEnd == INTNULL2)) ||
                !((recnumEnd < RECNUM_END_MN) ||
                  (recnumEnd > RECNUM_END_MX)) ) {
                this.recnumEnd = recnumEnd;
                recnumEndError = ERROR_NORMAL;
            } else {
                throw recnumEndOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setRecnumEndError(INTNULL, e, ERROR_LOCAL);
        } // try
        return recnumEndError;
    } // method setRecnumEnd

    /**
     * Set the 'recnumEnd' class variable
     * @param  recnumEnd (Integer)
     */
    public int setRecnumEnd(Integer recnumEnd) {
        try {
            setRecnumEnd(recnumEnd.intValue());
        } catch (Exception e) {
            setRecnumEndError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recnumEndError;
    } // method setRecnumEnd

    /**
     * Set the 'recnumEnd' class variable
     * @param  recnumEnd (Float)
     */
    public int setRecnumEnd(Float recnumEnd) {
        try {
            if (recnumEnd.floatValue() == FLOATNULL) {
                setRecnumEnd(INTNULL);
            } else {
                setRecnumEnd(recnumEnd.intValue());
            } // if (recnumEnd.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setRecnumEndError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recnumEndError;
    } // method setRecnumEnd

    /**
     * Set the 'recnumEnd' class variable
     * @param  recnumEnd (String)
     */
    public int setRecnumEnd(String recnumEnd) {
        try {
            setRecnumEnd(new Integer(recnumEnd).intValue());
        } catch (Exception e) {
            setRecnumEndError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recnumEndError;
    } // method setRecnumEnd

    /**
     * Called when an exception has occured
     * @param  recnumEnd (String)
     */
    private void setRecnumEndError (int recnumEnd, Exception e, int error) {
        this.recnumEnd = recnumEnd;
        recnumEndErrorMessage = e.toString();
        recnumEndError = error;
    } // method setRecnumEndError


    /**
     * Set the 'stationsLoaded' class variable
     * @param  stationsLoaded (int)
     */
    public int setStationsLoaded(int stationsLoaded) {
        try {
            if ( ((stationsLoaded == INTNULL) ||
                  (stationsLoaded == INTNULL2)) ||
                !((stationsLoaded < STATIONS_LOADED_MN) ||
                  (stationsLoaded > STATIONS_LOADED_MX)) ) {
                this.stationsLoaded = stationsLoaded;
                stationsLoadedError = ERROR_NORMAL;
            } else {
                throw stationsLoadedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setStationsLoadedError(INTNULL, e, ERROR_LOCAL);
        } // try
        return stationsLoadedError;
    } // method setStationsLoaded

    /**
     * Set the 'stationsLoaded' class variable
     * @param  stationsLoaded (Integer)
     */
    public int setStationsLoaded(Integer stationsLoaded) {
        try {
            setStationsLoaded(stationsLoaded.intValue());
        } catch (Exception e) {
            setStationsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return stationsLoadedError;
    } // method setStationsLoaded

    /**
     * Set the 'stationsLoaded' class variable
     * @param  stationsLoaded (Float)
     */
    public int setStationsLoaded(Float stationsLoaded) {
        try {
            if (stationsLoaded.floatValue() == FLOATNULL) {
                setStationsLoaded(INTNULL);
            } else {
                setStationsLoaded(stationsLoaded.intValue());
            } // if (stationsLoaded.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setStationsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return stationsLoadedError;
    } // method setStationsLoaded

    /**
     * Set the 'stationsLoaded' class variable
     * @param  stationsLoaded (String)
     */
    public int setStationsLoaded(String stationsLoaded) {
        try {
            setStationsLoaded(new Integer(stationsLoaded).intValue());
        } catch (Exception e) {
            setStationsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return stationsLoadedError;
    } // method setStationsLoaded

    /**
     * Called when an exception has occured
     * @param  stationsLoaded (String)
     */
    private void setStationsLoadedError (int stationsLoaded, Exception e, int error) {
        this.stationsLoaded = stationsLoaded;
        stationsLoadedErrorMessage = e.toString();
        stationsLoadedError = error;
    } // method setStationsLoadedError


    /**
     * Set the 'recordsLoaded' class variable
     * @param  recordsLoaded (int)
     */
    public int setRecordsLoaded(int recordsLoaded) {
        try {
            if ( ((recordsLoaded == INTNULL) ||
                  (recordsLoaded == INTNULL2)) ||
                !((recordsLoaded < RECORDS_LOADED_MN) ||
                  (recordsLoaded > RECORDS_LOADED_MX)) ) {
                this.recordsLoaded = recordsLoaded;
                recordsLoadedError = ERROR_NORMAL;
            } else {
                throw recordsLoadedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setRecordsLoadedError(INTNULL, e, ERROR_LOCAL);
        } // try
        return recordsLoadedError;
    } // method setRecordsLoaded

    /**
     * Set the 'recordsLoaded' class variable
     * @param  recordsLoaded (Integer)
     */
    public int setRecordsLoaded(Integer recordsLoaded) {
        try {
            setRecordsLoaded(recordsLoaded.intValue());
        } catch (Exception e) {
            setRecordsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recordsLoadedError;
    } // method setRecordsLoaded

    /**
     * Set the 'recordsLoaded' class variable
     * @param  recordsLoaded (Float)
     */
    public int setRecordsLoaded(Float recordsLoaded) {
        try {
            if (recordsLoaded.floatValue() == FLOATNULL) {
                setRecordsLoaded(INTNULL);
            } else {
                setRecordsLoaded(recordsLoaded.intValue());
            } // if (recordsLoaded.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setRecordsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recordsLoadedError;
    } // method setRecordsLoaded

    /**
     * Set the 'recordsLoaded' class variable
     * @param  recordsLoaded (String)
     */
    public int setRecordsLoaded(String recordsLoaded) {
        try {
            setRecordsLoaded(new Integer(recordsLoaded).intValue());
        } catch (Exception e) {
            setRecordsLoadedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recordsLoadedError;
    } // method setRecordsLoaded

    /**
     * Called when an exception has occured
     * @param  recordsLoaded (String)
     */
    private void setRecordsLoadedError (int recordsLoaded, Exception e, int error) {
        this.recordsLoaded = recordsLoaded;
        recordsLoadedErrorMessage = e.toString();
        recordsLoadedError = error;
    } // method setRecordsLoadedError


    /**
     * Set the 'recordsRejected' class variable
     * @param  recordsRejected (int)
     */
    public int setRecordsRejected(int recordsRejected) {
        try {
            if ( ((recordsRejected == INTNULL) ||
                  (recordsRejected == INTNULL2)) ||
                !((recordsRejected < RECORDS_REJECTED_MN) ||
                  (recordsRejected > RECORDS_REJECTED_MX)) ) {
                this.recordsRejected = recordsRejected;
                recordsRejectedError = ERROR_NORMAL;
            } else {
                throw recordsRejectedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setRecordsRejectedError(INTNULL, e, ERROR_LOCAL);
        } // try
        return recordsRejectedError;
    } // method setRecordsRejected

    /**
     * Set the 'recordsRejected' class variable
     * @param  recordsRejected (Integer)
     */
    public int setRecordsRejected(Integer recordsRejected) {
        try {
            setRecordsRejected(recordsRejected.intValue());
        } catch (Exception e) {
            setRecordsRejectedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recordsRejectedError;
    } // method setRecordsRejected

    /**
     * Set the 'recordsRejected' class variable
     * @param  recordsRejected (Float)
     */
    public int setRecordsRejected(Float recordsRejected) {
        try {
            if (recordsRejected.floatValue() == FLOATNULL) {
                setRecordsRejected(INTNULL);
            } else {
                setRecordsRejected(recordsRejected.intValue());
            } // if (recordsRejected.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setRecordsRejectedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recordsRejectedError;
    } // method setRecordsRejected

    /**
     * Set the 'recordsRejected' class variable
     * @param  recordsRejected (String)
     */
    public int setRecordsRejected(String recordsRejected) {
        try {
            setRecordsRejected(new Integer(recordsRejected).intValue());
        } catch (Exception e) {
            setRecordsRejectedError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return recordsRejectedError;
    } // method setRecordsRejected

    /**
     * Called when an exception has occured
     * @param  recordsRejected (String)
     */
    private void setRecordsRejectedError (int recordsRejected, Exception e, int error) {
        this.recordsRejected = recordsRejected;
        recordsRejectedErrorMessage = e.toString();
        recordsRejectedError = error;
    } // method setRecordsRejectedError


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
     * Set the 'latNorth' class variable
     * @param  latNorth (float)
     */
    public int setLatNorth(float latNorth) {
        try {
            if ( ((latNorth == FLOATNULL) ||
                  (latNorth == FLOATNULL2)) ||
                !((latNorth < LAT_NORTH_MN) ||
                  (latNorth > LAT_NORTH_MX)) ) {
                this.latNorth = latNorth;
                latNorthError = ERROR_NORMAL;
            } else {
                throw latNorthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatNorthError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return latNorthError;
    } // method setLatNorth

    /**
     * Set the 'latNorth' class variable
     * @param  latNorth (Integer)
     */
    public int setLatNorth(Integer latNorth) {
        try {
            setLatNorth(latNorth.floatValue());
        } catch (Exception e) {
            setLatNorthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latNorthError;
    } // method setLatNorth

    /**
     * Set the 'latNorth' class variable
     * @param  latNorth (Float)
     */
    public int setLatNorth(Float latNorth) {
        try {
            setLatNorth(latNorth.floatValue());
        } catch (Exception e) {
            setLatNorthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latNorthError;
    } // method setLatNorth

    /**
     * Set the 'latNorth' class variable
     * @param  latNorth (String)
     */
    public int setLatNorth(String latNorth) {
        try {
            setLatNorth(new Float(latNorth).floatValue());
        } catch (Exception e) {
            setLatNorthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latNorthError;
    } // method setLatNorth

    /**
     * Called when an exception has occured
     * @param  latNorth (String)
     */
    private void setLatNorthError (float latNorth, Exception e, int error) {
        this.latNorth = latNorth;
        latNorthErrorMessage = e.toString();
        latNorthError = error;
    } // method setLatNorthError


    /**
     * Set the 'latSouth' class variable
     * @param  latSouth (float)
     */
    public int setLatSouth(float latSouth) {
        try {
            if ( ((latSouth == FLOATNULL) ||
                  (latSouth == FLOATNULL2)) ||
                !((latSouth < LAT_SOUTH_MN) ||
                  (latSouth > LAT_SOUTH_MX)) ) {
                this.latSouth = latSouth;
                latSouthError = ERROR_NORMAL;
            } else {
                throw latSouthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatSouthError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return latSouthError;
    } // method setLatSouth

    /**
     * Set the 'latSouth' class variable
     * @param  latSouth (Integer)
     */
    public int setLatSouth(Integer latSouth) {
        try {
            setLatSouth(latSouth.floatValue());
        } catch (Exception e) {
            setLatSouthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latSouthError;
    } // method setLatSouth

    /**
     * Set the 'latSouth' class variable
     * @param  latSouth (Float)
     */
    public int setLatSouth(Float latSouth) {
        try {
            setLatSouth(latSouth.floatValue());
        } catch (Exception e) {
            setLatSouthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latSouthError;
    } // method setLatSouth

    /**
     * Set the 'latSouth' class variable
     * @param  latSouth (String)
     */
    public int setLatSouth(String latSouth) {
        try {
            setLatSouth(new Float(latSouth).floatValue());
        } catch (Exception e) {
            setLatSouthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latSouthError;
    } // method setLatSouth

    /**
     * Called when an exception has occured
     * @param  latSouth (String)
     */
    private void setLatSouthError (float latSouth, Exception e, int error) {
        this.latSouth = latSouth;
        latSouthErrorMessage = e.toString();
        latSouthError = error;
    } // method setLatSouthError


    /**
     * Set the 'longWest' class variable
     * @param  longWest (float)
     */
    public int setLongWest(float longWest) {
        try {
            if ( ((longWest == FLOATNULL) ||
                  (longWest == FLOATNULL2)) ||
                !((longWest < LONG_WEST_MN) ||
                  (longWest > LONG_WEST_MX)) ) {
                this.longWest = longWest;
                longWestError = ERROR_NORMAL;
            } else {
                throw longWestOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLongWestError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return longWestError;
    } // method setLongWest

    /**
     * Set the 'longWest' class variable
     * @param  longWest (Integer)
     */
    public int setLongWest(Integer longWest) {
        try {
            setLongWest(longWest.floatValue());
        } catch (Exception e) {
            setLongWestError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longWestError;
    } // method setLongWest

    /**
     * Set the 'longWest' class variable
     * @param  longWest (Float)
     */
    public int setLongWest(Float longWest) {
        try {
            setLongWest(longWest.floatValue());
        } catch (Exception e) {
            setLongWestError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longWestError;
    } // method setLongWest

    /**
     * Set the 'longWest' class variable
     * @param  longWest (String)
     */
    public int setLongWest(String longWest) {
        try {
            setLongWest(new Float(longWest).floatValue());
        } catch (Exception e) {
            setLongWestError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longWestError;
    } // method setLongWest

    /**
     * Called when an exception has occured
     * @param  longWest (String)
     */
    private void setLongWestError (float longWest, Exception e, int error) {
        this.longWest = longWest;
        longWestErrorMessage = e.toString();
        longWestError = error;
    } // method setLongWestError


    /**
     * Set the 'longEast' class variable
     * @param  longEast (float)
     */
    public int setLongEast(float longEast) {
        try {
            if ( ((longEast == FLOATNULL) ||
                  (longEast == FLOATNULL2)) ||
                !((longEast < LONG_EAST_MN) ||
                  (longEast > LONG_EAST_MX)) ) {
                this.longEast = longEast;
                longEastError = ERROR_NORMAL;
            } else {
                throw longEastOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLongEastError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return longEastError;
    } // method setLongEast

    /**
     * Set the 'longEast' class variable
     * @param  longEast (Integer)
     */
    public int setLongEast(Integer longEast) {
        try {
            setLongEast(longEast.floatValue());
        } catch (Exception e) {
            setLongEastError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longEastError;
    } // method setLongEast

    /**
     * Set the 'longEast' class variable
     * @param  longEast (Float)
     */
    public int setLongEast(Float longEast) {
        try {
            setLongEast(longEast.floatValue());
        } catch (Exception e) {
            setLongEastError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longEastError;
    } // method setLongEast

    /**
     * Set the 'longEast' class variable
     * @param  longEast (String)
     */
    public int setLongEast(String longEast) {
        try {
            setLongEast(new Float(longEast).floatValue());
        } catch (Exception e) {
            setLongEastError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longEastError;
    } // method setLongEast

    /**
     * Called when an exception has occured
     * @param  longEast (String)
     */
    private void setLongEastError (float longEast, Exception e, int error) {
        this.longEast = longEast;
        longEastErrorMessage = e.toString();
        longEastError = error;
    } // method setLongEastError


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
     * Return the 'institute' class variable
     * @return institute (String)
     */
    public String getInstitute() {
        return institute;
    } // method getInstitute

    /**
     * Return the 'institute' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getInstitute methods
     * @return institute (String)
     */
    public String getInstitute(String s) {
        return (institute != CHARNULL ? institute.replace('"','\'') : "");
    } // method getInstitute


    /**
     * Return the 'expnam' class variable
     * @return expnam (String)
     */
    public String getExpnam() {
        return expnam;
    } // method getExpnam

    /**
     * Return the 'expnam' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getExpnam methods
     * @return expnam (String)
     */
    public String getExpnam(String s) {
        return (expnam != CHARNULL ? expnam.replace('"','\'') : "");
    } // method getExpnam


    /**
     * Return the 'loadDate' class variable
     * @return loadDate (Timestamp)
     */
    public Timestamp getLoadDate() {
        return loadDate;
    } // method getLoadDate

    /**
     * Return the 'loadDate' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLoadDate methods
     * @return loadDate (String)
     */
    public String getLoadDate(String s) {
        if (loadDate.equals(DATENULL)) {
            return ("");
        } else {
            String loadDateStr = loadDate.toString();
            return loadDateStr.substring(0,loadDateStr.indexOf('.'));
        } // if
    } // method getLoadDate


    /**
     * Return the 'flagged' class variable
     * @return flagged (String)
     */
    public String getFlagged() {
        return flagged;
    } // method getFlagged

    /**
     * Return the 'flagged' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFlagged methods
     * @return flagged (String)
     */
    public String getFlagged(String s) {
        return (flagged != CHARNULL ? flagged.replace('"','\'') : "");
    } // method getFlagged


    /**
     * Return the 'recnumStart' class variable
     * @return recnumStart (int)
     */
    public int getRecnumStart() {
        return recnumStart;
    } // method getRecnumStart

    /**
     * Return the 'recnumStart' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRecnumStart methods
     * @return recnumStart (String)
     */
    public String getRecnumStart(String s) {
        return ((recnumStart != INTNULL) ? new Integer(recnumStart).toString() : "");
    } // method getRecnumStart


    /**
     * Return the 'recnumEnd' class variable
     * @return recnumEnd (int)
     */
    public int getRecnumEnd() {
        return recnumEnd;
    } // method getRecnumEnd

    /**
     * Return the 'recnumEnd' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRecnumEnd methods
     * @return recnumEnd (String)
     */
    public String getRecnumEnd(String s) {
        return ((recnumEnd != INTNULL) ? new Integer(recnumEnd).toString() : "");
    } // method getRecnumEnd


    /**
     * Return the 'stationsLoaded' class variable
     * @return stationsLoaded (int)
     */
    public int getStationsLoaded() {
        return stationsLoaded;
    } // method getStationsLoaded

    /**
     * Return the 'stationsLoaded' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getStationsLoaded methods
     * @return stationsLoaded (String)
     */
    public String getStationsLoaded(String s) {
        return ((stationsLoaded != INTNULL) ? new Integer(stationsLoaded).toString() : "");
    } // method getStationsLoaded


    /**
     * Return the 'recordsLoaded' class variable
     * @return recordsLoaded (int)
     */
    public int getRecordsLoaded() {
        return recordsLoaded;
    } // method getRecordsLoaded

    /**
     * Return the 'recordsLoaded' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRecordsLoaded methods
     * @return recordsLoaded (String)
     */
    public String getRecordsLoaded(String s) {
        return ((recordsLoaded != INTNULL) ? new Integer(recordsLoaded).toString() : "");
    } // method getRecordsLoaded


    /**
     * Return the 'recordsRejected' class variable
     * @return recordsRejected (int)
     */
    public int getRecordsRejected() {
        return recordsRejected;
    } // method getRecordsRejected

    /**
     * Return the 'recordsRejected' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getRecordsRejected methods
     * @return recordsRejected (String)
     */
    public String getRecordsRejected(String s) {
        return ((recordsRejected != INTNULL) ? new Integer(recordsRejected).toString() : "");
    } // method getRecordsRejected


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
     * Return the 'latNorth' class variable
     * @return latNorth (float)
     */
    public float getLatNorth() {
        return latNorth;
    } // method getLatNorth

    /**
     * Return the 'latNorth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLatNorth methods
     * @return latNorth (String)
     */
    public String getLatNorth(String s) {
        return ((latNorth != FLOATNULL) ? new Float(latNorth).toString() : "");
    } // method getLatNorth


    /**
     * Return the 'latSouth' class variable
     * @return latSouth (float)
     */
    public float getLatSouth() {
        return latSouth;
    } // method getLatSouth

    /**
     * Return the 'latSouth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLatSouth methods
     * @return latSouth (String)
     */
    public String getLatSouth(String s) {
        return ((latSouth != FLOATNULL) ? new Float(latSouth).toString() : "");
    } // method getLatSouth


    /**
     * Return the 'longWest' class variable
     * @return longWest (float)
     */
    public float getLongWest() {
        return longWest;
    } // method getLongWest

    /**
     * Return the 'longWest' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLongWest methods
     * @return longWest (String)
     */
    public String getLongWest(String s) {
        return ((longWest != FLOATNULL) ? new Float(longWest).toString() : "");
    } // method getLongWest


    /**
     * Return the 'longEast' class variable
     * @return longEast (float)
     */
    public float getLongEast() {
        return longEast;
    } // method getLongEast

    /**
     * Return the 'longEast' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLongEast methods
     * @return longEast (String)
     */
    public String getLongEast(String s) {
        return ((longEast != FLOATNULL) ? new Float(longEast).toString() : "");
    } // method getLongEast


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
            (surveyId == CHARNULL) &&
            (source == CHARNULL) &&
            (institute == CHARNULL) &&
            (expnam == CHARNULL) &&
            (loadDate.equals(DATENULL)) &&
            (flagged == CHARNULL) &&
            (recnumStart == INTNULL) &&
            (recnumEnd == INTNULL) &&
            (stationsLoaded == INTNULL) &&
            (recordsLoaded == INTNULL) &&
            (recordsRejected == INTNULL) &&
            (dateStart.equals(DATENULL)) &&
            (dateEnd.equals(DATENULL)) &&
            (latNorth == FLOATNULL) &&
            (latSouth == FLOATNULL) &&
            (longWest == FLOATNULL) &&
            (longEast == FLOATNULL) &&
            (respPerson == CHARNULL)) {
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
            surveyIdError +
            sourceError +
            instituteError +
            expnamError +
            loadDateError +
            flaggedError +
            recnumStartError +
            recnumEndError +
            stationsLoadedError +
            recordsLoadedError +
            recordsRejectedError +
            dateStartError +
            dateEndError +
            latNorthError +
            latSouthError +
            longWestError +
            longEastError +
            respPersonError;
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
     * Gets the errorcode for the institute instance variable
     * @return errorcode (int)
     */
    public int getInstituteError() {
        return instituteError;
    } // method getInstituteError

    /**
     * Gets the errorMessage for the institute instance variable
     * @return errorMessage (String)
     */
    public String getInstituteErrorMessage() {
        return instituteErrorMessage;
    } // method getInstituteErrorMessage


    /**
     * Gets the errorcode for the expnam instance variable
     * @return errorcode (int)
     */
    public int getExpnamError() {
        return expnamError;
    } // method getExpnamError

    /**
     * Gets the errorMessage for the expnam instance variable
     * @return errorMessage (String)
     */
    public String getExpnamErrorMessage() {
        return expnamErrorMessage;
    } // method getExpnamErrorMessage


    /**
     * Gets the errorcode for the loadDate instance variable
     * @return errorcode (int)
     */
    public int getLoadDateError() {
        return loadDateError;
    } // method getLoadDateError

    /**
     * Gets the errorMessage for the loadDate instance variable
     * @return errorMessage (String)
     */
    public String getLoadDateErrorMessage() {
        return loadDateErrorMessage;
    } // method getLoadDateErrorMessage


    /**
     * Gets the errorcode for the flagged instance variable
     * @return errorcode (int)
     */
    public int getFlaggedError() {
        return flaggedError;
    } // method getFlaggedError

    /**
     * Gets the errorMessage for the flagged instance variable
     * @return errorMessage (String)
     */
    public String getFlaggedErrorMessage() {
        return flaggedErrorMessage;
    } // method getFlaggedErrorMessage


    /**
     * Gets the errorcode for the recnumStart instance variable
     * @return errorcode (int)
     */
    public int getRecnumStartError() {
        return recnumStartError;
    } // method getRecnumStartError

    /**
     * Gets the errorMessage for the recnumStart instance variable
     * @return errorMessage (String)
     */
    public String getRecnumStartErrorMessage() {
        return recnumStartErrorMessage;
    } // method getRecnumStartErrorMessage


    /**
     * Gets the errorcode for the recnumEnd instance variable
     * @return errorcode (int)
     */
    public int getRecnumEndError() {
        return recnumEndError;
    } // method getRecnumEndError

    /**
     * Gets the errorMessage for the recnumEnd instance variable
     * @return errorMessage (String)
     */
    public String getRecnumEndErrorMessage() {
        return recnumEndErrorMessage;
    } // method getRecnumEndErrorMessage


    /**
     * Gets the errorcode for the stationsLoaded instance variable
     * @return errorcode (int)
     */
    public int getStationsLoadedError() {
        return stationsLoadedError;
    } // method getStationsLoadedError

    /**
     * Gets the errorMessage for the stationsLoaded instance variable
     * @return errorMessage (String)
     */
    public String getStationsLoadedErrorMessage() {
        return stationsLoadedErrorMessage;
    } // method getStationsLoadedErrorMessage


    /**
     * Gets the errorcode for the recordsLoaded instance variable
     * @return errorcode (int)
     */
    public int getRecordsLoadedError() {
        return recordsLoadedError;
    } // method getRecordsLoadedError

    /**
     * Gets the errorMessage for the recordsLoaded instance variable
     * @return errorMessage (String)
     */
    public String getRecordsLoadedErrorMessage() {
        return recordsLoadedErrorMessage;
    } // method getRecordsLoadedErrorMessage


    /**
     * Gets the errorcode for the recordsRejected instance variable
     * @return errorcode (int)
     */
    public int getRecordsRejectedError() {
        return recordsRejectedError;
    } // method getRecordsRejectedError

    /**
     * Gets the errorMessage for the recordsRejected instance variable
     * @return errorMessage (String)
     */
    public String getRecordsRejectedErrorMessage() {
        return recordsRejectedErrorMessage;
    } // method getRecordsRejectedErrorMessage


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
     * Gets the errorcode for the latNorth instance variable
     * @return errorcode (int)
     */
    public int getLatNorthError() {
        return latNorthError;
    } // method getLatNorthError

    /**
     * Gets the errorMessage for the latNorth instance variable
     * @return errorMessage (String)
     */
    public String getLatNorthErrorMessage() {
        return latNorthErrorMessage;
    } // method getLatNorthErrorMessage


    /**
     * Gets the errorcode for the latSouth instance variable
     * @return errorcode (int)
     */
    public int getLatSouthError() {
        return latSouthError;
    } // method getLatSouthError

    /**
     * Gets the errorMessage for the latSouth instance variable
     * @return errorMessage (String)
     */
    public String getLatSouthErrorMessage() {
        return latSouthErrorMessage;
    } // method getLatSouthErrorMessage


    /**
     * Gets the errorcode for the longWest instance variable
     * @return errorcode (int)
     */
    public int getLongWestError() {
        return longWestError;
    } // method getLongWestError

    /**
     * Gets the errorMessage for the longWest instance variable
     * @return errorMessage (String)
     */
    public String getLongWestErrorMessage() {
        return longWestErrorMessage;
    } // method getLongWestErrorMessage


    /**
     * Gets the errorcode for the longEast instance variable
     * @return errorcode (int)
     */
    public int getLongEastError() {
        return longEastError;
    } // method getLongEastError

    /**
     * Gets the errorMessage for the longEast instance variable
     * @return errorMessage (String)
     */
    public String getLongEastErrorMessage() {
        return longEastErrorMessage;
    } // method getLongEastErrorMessage


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


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of LogMrnLoads. e.g.<pre>
     * LogMrnLoads logMrnLoads = new LogMrnLoads(<val1>);
     * LogMrnLoads logMrnLoadsArray[] = logMrnLoads.get();</pre>
     * will get the LogMrnLoads record where loadid = <val1>.
     * @return Array of LogMrnLoads (LogMrnLoads[])
     */
    public LogMrnLoads[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * LogMrnLoads logMrnLoadsArray[] =
     *     new LogMrnLoads().get(LogMrnLoads.LOADID+"=<val1>");</pre>
     * will get the LogMrnLoads record where loadid = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of LogMrnLoads (LogMrnLoads[])
     */
    public LogMrnLoads[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * LogMrnLoads logMrnLoadsArray[] =
     *     new LogMrnLoads().get("1=1",logMrnLoads.LOADID);</pre>
     * will get the all the LogMrnLoads records, and order them by loadid.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of LogMrnLoads (LogMrnLoads[])
     */
    public LogMrnLoads[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = LogMrnLoads.LOADID,LogMrnLoads.SURVEY_ID;
     * String where = LogMrnLoads.LOADID + "=<val1";
     * String order = LogMrnLoads.LOADID;
     * LogMrnLoads logMrnLoadsArray[] =
     *     new LogMrnLoads().get(columns, where, order);</pre>
     * will get the loadid and surveyId colums of all LogMrnLoads records,
     * where loadid = <val1>, and order them by loadid.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of LogMrnLoads (LogMrnLoads[])
     */
    public LogMrnLoads[] get (String fields, String where, String order) {
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
     * and transforms it into an array of LogMrnLoads.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private LogMrnLoads[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int loadidCol          = db.getColNumber(LOADID);
        int surveyIdCol        = db.getColNumber(SURVEY_ID);
        int sourceCol          = db.getColNumber(SOURCE);
        int instituteCol       = db.getColNumber(INSTITUTE);
        int expnamCol          = db.getColNumber(EXPNAM);
        int loadDateCol        = db.getColNumber(LOAD_DATE);
        int flaggedCol         = db.getColNumber(FLAGGED);
        int recnumStartCol     = db.getColNumber(RECNUM_START);
        int recnumEndCol       = db.getColNumber(RECNUM_END);
        int stationsLoadedCol  = db.getColNumber(STATIONS_LOADED);
        int recordsLoadedCol   = db.getColNumber(RECORDS_LOADED);
        int recordsRejectedCol = db.getColNumber(RECORDS_REJECTED);
        int dateStartCol       = db.getColNumber(DATE_START);
        int dateEndCol         = db.getColNumber(DATE_END);
        int latNorthCol        = db.getColNumber(LAT_NORTH);
        int latSouthCol        = db.getColNumber(LAT_SOUTH);
        int longWestCol        = db.getColNumber(LONG_WEST);
        int longEastCol        = db.getColNumber(LONG_EAST);
        int respPersonCol      = db.getColNumber(RESP_PERSON);
        LogMrnLoads[] cArray = new LogMrnLoads[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new LogMrnLoads();
            if (loadidCol != -1)
                cArray[i].setLoadid         ((String) row.elementAt(loadidCol));
            if (surveyIdCol != -1)
                cArray[i].setSurveyId       ((String) row.elementAt(surveyIdCol));
            if (sourceCol != -1)
                cArray[i].setSource         ((String) row.elementAt(sourceCol));
            if (instituteCol != -1)
                cArray[i].setInstitute      ((String) row.elementAt(instituteCol));
            if (expnamCol != -1)
                cArray[i].setExpnam         ((String) row.elementAt(expnamCol));
            if (loadDateCol != -1)
                cArray[i].setLoadDate       ((String) row.elementAt(loadDateCol));
            if (flaggedCol != -1)
                cArray[i].setFlagged        ((String) row.elementAt(flaggedCol));
            if (recnumStartCol != -1)
                cArray[i].setRecnumStart    ((String) row.elementAt(recnumStartCol));
            if (recnumEndCol != -1)
                cArray[i].setRecnumEnd      ((String) row.elementAt(recnumEndCol));
            if (stationsLoadedCol != -1)
                cArray[i].setStationsLoaded ((String) row.elementAt(stationsLoadedCol));
            if (recordsLoadedCol != -1)
                cArray[i].setRecordsLoaded  ((String) row.elementAt(recordsLoadedCol));
            if (recordsRejectedCol != -1)
                cArray[i].setRecordsRejected((String) row.elementAt(recordsRejectedCol));
            if (dateStartCol != -1)
                cArray[i].setDateStart      ((String) row.elementAt(dateStartCol));
            if (dateEndCol != -1)
                cArray[i].setDateEnd        ((String) row.elementAt(dateEndCol));
            if (latNorthCol != -1)
                cArray[i].setLatNorth       ((String) row.elementAt(latNorthCol));
            if (latSouthCol != -1)
                cArray[i].setLatSouth       ((String) row.elementAt(latSouthCol));
            if (longWestCol != -1)
                cArray[i].setLongWest       ((String) row.elementAt(longWestCol));
            if (longEastCol != -1)
                cArray[i].setLongEast       ((String) row.elementAt(longEastCol));
            if (respPersonCol != -1)
                cArray[i].setRespPerson     ((String) row.elementAt(respPersonCol));
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
     *     new LogMrnLoads(
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
     *     loadid          = <val1>,
     *     surveyId        = <val2>,
     *     source          = <val3>,
     *     institute       = <val4>,
     *     expnam          = <val5>,
     *     loadDate        = <val6>,
     *     flagged         = <val7>,
     *     recnumStart     = <val8>,
     *     recnumEnd       = <val9>,
     *     stationsLoaded  = <val10>,
     *     recordsLoaded   = <val11>,
     *     recordsRejected = <val12>,
     *     dateStart       = <val13>,
     *     dateEnd         = <val14>,
     *     latNorth        = <val15>,
     *     latSouth        = <val16>,
     *     longWest        = <val17>,
     *     longEast        = <val18>,
     *     respPerson      = <val19>.
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
     * Boolean success = new LogMrnLoads(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.DATENULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.DATENULL,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
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
     * LogMrnLoads logMrnLoads = new LogMrnLoads();
     * success = logMrnLoads.del(LogMrnLoads.LOADID+"=<val1>");</pre>
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
     * update are taken from the LogMrnLoads argument, .e.g.<pre>
     * Boolean success
     * LogMrnLoads updLogMrnLoads = new LogMrnLoads();
     * updLogMrnLoads.setSurveyId(<val2>);
     * LogMrnLoads whereLogMrnLoads = new LogMrnLoads(<val1>);
     * success = whereLogMrnLoads.upd(updLogMrnLoads);</pre>
     * will update SurveyId to <val2> for all records where
     * loadid = <val1>.
     * @param  logMrnLoads  A LogMrnLoads variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(LogMrnLoads logMrnLoads) {
        return db.update (TABLE, createColVals(logMrnLoads), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * LogMrnLoads updLogMrnLoads = new LogMrnLoads();
     * updLogMrnLoads.setSurveyId(<val2>);
     * LogMrnLoads whereLogMrnLoads = new LogMrnLoads();
     * success = whereLogMrnLoads.upd(
     *     updLogMrnLoads, LogMrnLoads.LOADID+"=<val1>");</pre>
     * will update SurveyId to <val2> for all records where
     * loadid = <val1>.
     * @param  logMrnLoads  A LogMrnLoads variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(LogMrnLoads logMrnLoads, String where) {
        return db.update (TABLE, createColVals(logMrnLoads), where);
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
        if (getSurveyId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_ID + "='" + getSurveyId() + "'";
        } // if getSurveyId
        if (getSource() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SOURCE + "='" + getSource() + "'";
        } // if getSource
        if (getInstitute() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INSTITUTE + "='" + getInstitute() + "'";
        } // if getInstitute
        if (getExpnam() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + EXPNAM + "='" + getExpnam() + "'";
        } // if getExpnam
        if (!getLoadDate().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LOAD_DATE +
                "=" + Tables.getDateFormat(getLoadDate());
        } // if getLoadDate
        if (getFlagged() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FLAGGED + "='" + getFlagged() + "'";
        } // if getFlagged
        if (getRecnumStart() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RECNUM_START + "=" + getRecnumStart("");
        } // if getRecnumStart
        if (getRecnumEnd() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RECNUM_END + "=" + getRecnumEnd("");
        } // if getRecnumEnd
        if (getStationsLoaded() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + STATIONS_LOADED + "=" + getStationsLoaded("");
        } // if getStationsLoaded
        if (getRecordsLoaded() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RECORDS_LOADED + "=" + getRecordsLoaded("");
        } // if getRecordsLoaded
        if (getRecordsRejected() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RECORDS_REJECTED + "=" + getRecordsRejected("");
        } // if getRecordsRejected
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
        if (getLatNorth() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LAT_NORTH + "=" + getLatNorth("");
        } // if getLatNorth
        if (getLatSouth() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LAT_SOUTH + "=" + getLatSouth("");
        } // if getLatSouth
        if (getLongWest() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LONG_WEST + "=" + getLongWest("");
        } // if getLongWest
        if (getLongEast() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LONG_EAST + "=" + getLongEast("");
        } // if getLongEast
        if (getRespPerson() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RESP_PERSON + "='" + getRespPerson() + "'";
        } // if getRespPerson
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(LogMrnLoads aVar) {
        String colVals = "";
        if (aVar.getLoadid() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LOADID +"=";
            colVals += (aVar.getLoadid().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLoadid() + "'");
        } // if aVar.getLoadid
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getSource() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SOURCE +"=";
            colVals += (aVar.getSource().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSource() + "'");
        } // if aVar.getSource
        if (aVar.getInstitute() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INSTITUTE +"=";
            colVals += (aVar.getInstitute().equals(CHARNULL2) ?
                "null" : "'" + aVar.getInstitute() + "'");
        } // if aVar.getInstitute
        if (aVar.getExpnam() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += EXPNAM +"=";
            colVals += (aVar.getExpnam().equals(CHARNULL2) ?
                "null" : "'" + aVar.getExpnam() + "'");
        } // if aVar.getExpnam
        if (!aVar.getLoadDate().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LOAD_DATE +"=";
            colVals += (aVar.getLoadDate().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getLoadDate()));
        } // if aVar.getLoadDate
        if (aVar.getFlagged() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FLAGGED +"=";
            colVals += (aVar.getFlagged().equals(CHARNULL2) ?
                "null" : "'" + aVar.getFlagged() + "'");
        } // if aVar.getFlagged
        if (aVar.getRecnumStart() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RECNUM_START +"=";
            colVals += (aVar.getRecnumStart() == INTNULL2 ?
                "null" : aVar.getRecnumStart(""));
        } // if aVar.getRecnumStart
        if (aVar.getRecnumEnd() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RECNUM_END +"=";
            colVals += (aVar.getRecnumEnd() == INTNULL2 ?
                "null" : aVar.getRecnumEnd(""));
        } // if aVar.getRecnumEnd
        if (aVar.getStationsLoaded() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += STATIONS_LOADED +"=";
            colVals += (aVar.getStationsLoaded() == INTNULL2 ?
                "null" : aVar.getStationsLoaded(""));
        } // if aVar.getStationsLoaded
        if (aVar.getRecordsLoaded() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RECORDS_LOADED +"=";
            colVals += (aVar.getRecordsLoaded() == INTNULL2 ?
                "null" : aVar.getRecordsLoaded(""));
        } // if aVar.getRecordsLoaded
        if (aVar.getRecordsRejected() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RECORDS_REJECTED +"=";
            colVals += (aVar.getRecordsRejected() == INTNULL2 ?
                "null" : aVar.getRecordsRejected(""));
        } // if aVar.getRecordsRejected
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
        if (aVar.getLatNorth() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LAT_NORTH +"=";
            colVals += (aVar.getLatNorth() == FLOATNULL2 ?
                "null" : aVar.getLatNorth(""));
        } // if aVar.getLatNorth
        if (aVar.getLatSouth() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LAT_SOUTH +"=";
            colVals += (aVar.getLatSouth() == FLOATNULL2 ?
                "null" : aVar.getLatSouth(""));
        } // if aVar.getLatSouth
        if (aVar.getLongWest() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LONG_WEST +"=";
            colVals += (aVar.getLongWest() == FLOATNULL2 ?
                "null" : aVar.getLongWest(""));
        } // if aVar.getLongWest
        if (aVar.getLongEast() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LONG_EAST +"=";
            colVals += (aVar.getLongEast() == FLOATNULL2 ?
                "null" : aVar.getLongEast(""));
        } // if aVar.getLongEast
        if (aVar.getRespPerson() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RESP_PERSON +"=";
            colVals += (aVar.getRespPerson().equals(CHARNULL2) ?
                "null" : "'" + aVar.getRespPerson() + "'");
        } // if aVar.getRespPerson
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = LOADID;
        if (getSurveyId() != CHARNULL) {
            columns = columns + "," + SURVEY_ID;
        } // if getSurveyId
        if (getSource() != CHARNULL) {
            columns = columns + "," + SOURCE;
        } // if getSource
        if (getInstitute() != CHARNULL) {
            columns = columns + "," + INSTITUTE;
        } // if getInstitute
        if (getExpnam() != CHARNULL) {
            columns = columns + "," + EXPNAM;
        } // if getExpnam
        if (!getLoadDate().equals(DATENULL)) {
            columns = columns + "," + LOAD_DATE;
        } // if getLoadDate
        if (getFlagged() != CHARNULL) {
            columns = columns + "," + FLAGGED;
        } // if getFlagged
        if (getRecnumStart() != INTNULL) {
            columns = columns + "," + RECNUM_START;
        } // if getRecnumStart
        if (getRecnumEnd() != INTNULL) {
            columns = columns + "," + RECNUM_END;
        } // if getRecnumEnd
        if (getStationsLoaded() != INTNULL) {
            columns = columns + "," + STATIONS_LOADED;
        } // if getStationsLoaded
        if (getRecordsLoaded() != INTNULL) {
            columns = columns + "," + RECORDS_LOADED;
        } // if getRecordsLoaded
        if (getRecordsRejected() != INTNULL) {
            columns = columns + "," + RECORDS_REJECTED;
        } // if getRecordsRejected
        if (!getDateStart().equals(DATENULL)) {
            columns = columns + "," + DATE_START;
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            columns = columns + "," + DATE_END;
        } // if getDateEnd
        if (getLatNorth() != FLOATNULL) {
            columns = columns + "," + LAT_NORTH;
        } // if getLatNorth
        if (getLatSouth() != FLOATNULL) {
            columns = columns + "," + LAT_SOUTH;
        } // if getLatSouth
        if (getLongWest() != FLOATNULL) {
            columns = columns + "," + LONG_WEST;
        } // if getLongWest
        if (getLongEast() != FLOATNULL) {
            columns = columns + "," + LONG_EAST;
        } // if getLongEast
        if (getRespPerson() != CHARNULL) {
            columns = columns + "," + RESP_PERSON;
        } // if getRespPerson
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getLoadid() + "'";
        if (getSurveyId() != CHARNULL) {
            values  = values  + ",'" + getSurveyId() + "'";
        } // if getSurveyId
        if (getSource() != CHARNULL) {
            values  = values  + ",'" + getSource() + "'";
        } // if getSource
        if (getInstitute() != CHARNULL) {
            values  = values  + ",'" + getInstitute() + "'";
        } // if getInstitute
        if (getExpnam() != CHARNULL) {
            values  = values  + ",'" + getExpnam() + "'";
        } // if getExpnam
        if (!getLoadDate().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getLoadDate());
        } // if getLoadDate
        if (getFlagged() != CHARNULL) {
            values  = values  + ",'" + getFlagged() + "'";
        } // if getFlagged
        if (getRecnumStart() != INTNULL) {
            values  = values  + "," + getRecnumStart("");
        } // if getRecnumStart
        if (getRecnumEnd() != INTNULL) {
            values  = values  + "," + getRecnumEnd("");
        } // if getRecnumEnd
        if (getStationsLoaded() != INTNULL) {
            values  = values  + "," + getStationsLoaded("");
        } // if getStationsLoaded
        if (getRecordsLoaded() != INTNULL) {
            values  = values  + "," + getRecordsLoaded("");
        } // if getRecordsLoaded
        if (getRecordsRejected() != INTNULL) {
            values  = values  + "," + getRecordsRejected("");
        } // if getRecordsRejected
        if (!getDateStart().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateStart());
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateEnd());
        } // if getDateEnd
        if (getLatNorth() != FLOATNULL) {
            values  = values  + "," + getLatNorth("");
        } // if getLatNorth
        if (getLatSouth() != FLOATNULL) {
            values  = values  + "," + getLatSouth("");
        } // if getLatSouth
        if (getLongWest() != FLOATNULL) {
            values  = values  + "," + getLongWest("");
        } // if getLongWest
        if (getLongEast() != FLOATNULL) {
            values  = values  + "," + getLongEast("");
        } // if getLongEast
        if (getRespPerson() != CHARNULL) {
            values  = values  + ",'" + getRespPerson() + "'";
        } // if getRespPerson
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getLoadid("")          + "|" +
            getSurveyId("")        + "|" +
            getSource("")          + "|" +
            getInstitute("")       + "|" +
            getExpnam("")          + "|" +
            getLoadDate("")        + "|" +
            getFlagged("")         + "|" +
            getRecnumStart("")     + "|" +
            getRecnumEnd("")       + "|" +
            getStationsLoaded("")  + "|" +
            getRecordsLoaded("")   + "|" +
            getRecordsRejected("") + "|" +
            getDateStart("")       + "|" +
            getDateEnd("")         + "|" +
            getLatNorth("")        + "|" +
            getLatSouth("")        + "|" +
            getLongWest("")        + "|" +
            getLongEast("")        + "|" +
            getRespPerson("")      + "|";
    } // method toString

} // class LogMrnLoads
