package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the LOG_USERS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class LogUsers extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "LOG_USERS";
    /** The userid field name */
    public static final String USERID = "USERID";
    /** The dateTime field name */
    public static final String DATE_TIME = "DATE_TIME";
    /** The database field name */
    public static final String DATABASE = "DATABASE";
    /** The discipline field name */
    public static final String DISCIPLINE = "DISCIPLINE";
    /** The product field name */
    public static final String PRODUCT = "PRODUCT";
    /** The fileName field name */
    public static final String FILE_NAME = "FILE_NAME";
    /** The latitudeNorth field name */
    public static final String LATITUDE_NORTH = "LATITUDE_NORTH";
    /** The latitudeSouth field name */
    public static final String LATITUDE_SOUTH = "LATITUDE_SOUTH";
    /** The longitudeWest field name */
    public static final String LONGITUDE_WEST = "LONGITUDE_WEST";
    /** The longitudeEast field name */
    public static final String LONGITUDE_EAST = "LONGITUDE_EAST";
    /** The dateStart field name */
    public static final String DATE_START = "DATE_START";
    /** The dateEnd field name */
    public static final String DATE_END = "DATE_END";
    /** The surveyId field name */
    public static final String SURVEY_ID = "SURVEY_ID";
    /** The numRecords field name */
    public static final String NUM_RECORDS = "NUM_RECORDS";
    /** The jobDuration field name */
    public static final String JOB_DURATION = "JOB_DURATION";
    /** The queueDuration field name */
    public static final String QUEUE_DURATION = "QUEUE_DURATION";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    userid;
    private Timestamp dateTime;
    private String    database;
    private String    discipline;
    private String    product;
    private String    fileName;
    private float     latitudeNorth;
    private float     latitudeSouth;
    private float     longitudeWest;
    private float     longitudeEast;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private String    surveyId;
    private int       numRecords;
    private String    jobDuration;
    private String    queueDuration;

    /** The error variables  */
    private int useridError        = ERROR_NORMAL;
    private int dateTimeError      = ERROR_NORMAL;
    private int databaseError      = ERROR_NORMAL;
    private int disciplineError    = ERROR_NORMAL;
    private int productError       = ERROR_NORMAL;
    private int fileNameError      = ERROR_NORMAL;
    private int latitudeNorthError = ERROR_NORMAL;
    private int latitudeSouthError = ERROR_NORMAL;
    private int longitudeWestError = ERROR_NORMAL;
    private int longitudeEastError = ERROR_NORMAL;
    private int dateStartError     = ERROR_NORMAL;
    private int dateEndError       = ERROR_NORMAL;
    private int surveyIdError      = ERROR_NORMAL;
    private int numRecordsError    = ERROR_NORMAL;
    private int jobDurationError   = ERROR_NORMAL;
    private int queueDurationError   = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String useridErrorMessage        = "";
    private String dateTimeErrorMessage      = "";
    private String databaseErrorMessage      = "";
    private String disciplineErrorMessage    = "";
    private String productErrorMessage       = "";
    private String fileNameErrorMessage      = "";
    private String latitudeNorthErrorMessage = "";
    private String latitudeSouthErrorMessage = "";
    private String longitudeWestErrorMessage = "";
    private String longitudeEastErrorMessage = "";
    private String dateStartErrorMessage     = "";
    private String dateEndErrorMessage       = "";
    private String surveyIdErrorMessage      = "";
    private String numRecordsErrorMessage    = "";
    private String jobDurationErrorMessage   = "";
    private String queueDurationErrorMessage   = "";

    /** The min-max constants for all numerics */
    public static final Timestamp DATE_TIME_MN = DATEMIN;
    public static final Timestamp DATE_TIME_MX = DATEMAX;
    public static final float     LATITUDE_NORTH_MN = FLOATMIN;
    public static final float     LATITUDE_NORTH_MX = FLOATMAX;
    public static final float     LATITUDE_SOUTH_MN = FLOATMIN;
    public static final float     LATITUDE_SOUTH_MX = FLOATMAX;
    public static final float     LONGITUDE_WEST_MN = FLOATMIN;
    public static final float     LONGITUDE_WEST_MX = FLOATMAX;
    public static final float     LONGITUDE_EAST_MN = FLOATMIN;
    public static final float     LONGITUDE_EAST_MX = FLOATMAX;
    public static final Timestamp DATE_START_MN = DATEMIN;
    public static final Timestamp DATE_START_MX = DATEMAX;
    public static final Timestamp DATE_END_MN = DATEMIN;
    public static final Timestamp DATE_END_MX = DATEMAX;
    public static final int       NUM_RECORDS_MN = INTMIN;
    public static final int       NUM_RECORDS_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception dateTimeException =
        new Exception ("'dateTime' is null");
    Exception dateTimeOutOfBoundsException =
        new Exception ("'dateTime' out of bounds: " +
            DATE_TIME_MN + " - " + DATE_TIME_MX);
    Exception latitudeNorthOutOfBoundsException =
        new Exception ("'latitudeNorth' out of bounds: " +
            LATITUDE_NORTH_MN + " - " + LATITUDE_NORTH_MX);
    Exception latitudeSouthOutOfBoundsException =
        new Exception ("'latitudeSouth' out of bounds: " +
            LATITUDE_SOUTH_MN + " - " + LATITUDE_SOUTH_MX);
    Exception longitudeWestOutOfBoundsException =
        new Exception ("'longitudeWest' out of bounds: " +
            LONGITUDE_WEST_MN + " - " + LONGITUDE_WEST_MX);
    Exception longitudeEastOutOfBoundsException =
        new Exception ("'longitudeEast' out of bounds: " +
            LONGITUDE_EAST_MN + " - " + LONGITUDE_EAST_MX);
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
    Exception numRecordsOutOfBoundsException =
        new Exception ("'numRecords' out of bounds: " +
            NUM_RECORDS_MN + " - " + NUM_RECORDS_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public LogUsers() {
        clearVars();
        if (dbg) System.out.println ("<br>in LogUsers constructor 1"); // debug
    } // LogUsers constructor

    /**
     * Instantiate a LogUsers object and initialize the instance variables.
     * @param userid  The userid (String)
     */
    public LogUsers(
            String userid) {
        this();
        setUserid        (userid       );
        if (dbg) System.out.println ("<br>in LogUsers constructor 2"); // debug
    } // LogUsers constructor

    /**
     * Instantiate a LogUsers object and initialize the instance variables.
     * @param userid         The userid        (String)
     * @param dateTime       The dateTime      (java.util.Date)
     * @param database       The database      (String)
     * @param discipline     The discipline    (String)
     * @param product        The product       (String)
     * @param fileName       The fileName      (String)
     * @param latitudeNorth  The latitudeNorth (float)
     * @param latitudeSouth  The latitudeSouth (float)
     * @param longitudeWest  The longitudeWest (float)
     * @param longitudeEast  The longitudeEast (float)
     * @param dateStart      The dateStart     (java.util.Date)
     * @param dateEnd        The dateEnd       (java.util.Date)
     * @param surveyId       The surveyId      (String)
     * @param numRecords     The numRecords    (int)
     * @param jobDuration    The jobDuration   (String)
     * @param queueDuration  The queueDuration   (String)
     */
    public LogUsers(
            String         userid,
            java.util.Date dateTime,
            String         database,
            String         discipline,
            String         product,
            String         fileName,
            float          latitudeNorth,
            float          latitudeSouth,
            float          longitudeWest,
            float          longitudeEast,
            java.util.Date dateStart,
            java.util.Date dateEnd,
            String         surveyId,
            int            numRecords,
            String         jobDuration,
            String         queueDuration) {
        this();
        setUserid        (userid       );
        setDateTime      (dateTime     );
        setDatabase      (database     );
        setDiscipline    (discipline   );
        setProduct       (product      );
        setFileName      (fileName     );
        setLatitudeNorth (latitudeNorth);
        setLatitudeSouth (latitudeSouth);
        setLongitudeWest (longitudeWest);
        setLongitudeEast (longitudeEast);
        setDateStart     (dateStart    );
        setDateEnd       (dateEnd      );
        setSurveyId      (surveyId     );
        setNumRecords    (numRecords   );
        setJobDuration   (jobDuration  );
        setQueueDuration (queueDuration);
        if (dbg) System.out.println ("<br>in LogUsers constructor 3"); // debug
    } // LogUsers constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setUserid        (CHARNULL );
        setDateTime      (DATENULL );
        setDatabase      (CHARNULL );
        setDiscipline    (CHARNULL );
        setProduct       (CHARNULL );
        setFileName      (CHARNULL );
        setLatitudeNorth (FLOATNULL);
        setLatitudeSouth (FLOATNULL);
        setLongitudeWest (FLOATNULL);
        setLongitudeEast (FLOATNULL);
        setDateStart     (DATENULL );
        setDateEnd       (DATENULL );
        setSurveyId      (CHARNULL );
        setNumRecords    (INTNULL  );
        setJobDuration   (CHARNULL );
        setQueueDuration (CHARNULL );
    } // method clearVars


    //=================//
    // All the setters //
    //=================//

    /**
     * Set the 'userid' class variable
     * @param  userid (String)
     */
    public int setUserid(String userid) {
        try {
            this.userid = userid;
            if (this.userid != CHARNULL) {
                this.userid = stripCRLF(this.userid.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>userid = " + this.userid);
        } catch (Exception e) {
            setUseridError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return useridError;
    } // method setUserid

    /**
     * Called when an exception has occured
     * @param  userid (String)
     */
    private void setUseridError (String userid, Exception e, int error) {
        this.userid = userid;
        useridErrorMessage = e.toString();
        useridError = error;
    } // method setUseridError


    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (Timestamp)
     */
    public int setDateTime(Timestamp dateTime) {
        try {
            if (dateTime == null) { this.dateTime = DATENULL; }
            if ( (dateTime.equals(DATENULL) ||
                  dateTime.equals(DATENULL2)) ||
                !(dateTime.before(DATE_TIME_MN) ||
                  dateTime.after(DATE_TIME_MX)) ) {
                this.dateTime = dateTime;
                dateTimeError = ERROR_NORMAL;
            } else {
                throw dateTimeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_LOCAL);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (java.util.Date)
     */
    public int setDateTime(java.util.Date dateTime) {
        try {
            setDateTime(new Timestamp(dateTime.getTime()));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Set the 'dateTime' class variable
     * @param  dateTime (String)
     */
    public int setDateTime(String dateTime) {
        try {
            int len = dateTime.length();
            switch (len) {
            // date &/or times
                case 4: dateTime += "-01";
                case 7: dateTime += "-01";
                case 10: dateTime += " 00";
                case 13: dateTime += ":00";
                case 16: dateTime += ":00"; break;
                // times only
                case 5: dateTime = "1800-01-01 " + dateTime + ":00"; break;
                case 8: dateTime = "1800-01-01 " + dateTime; break;
            } // switch
            if (dbg) System.out.println ("dateTime = " + dateTime);
            setDateTime(Timestamp.valueOf(dateTime));
        } catch (Exception e) {
            setDateTimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return dateTimeError;
    } // method setDateTime

    /**
     * Called when an exception has occured
     * @param  dateTime (String)
     */
    private void setDateTimeError (Timestamp dateTime, Exception e, int error) {
        this.dateTime = dateTime;
        dateTimeErrorMessage = e.toString();
        dateTimeError = error;
    } // method setDateTimeError


    /**
     * Set the 'database' class variable
     * @param  database (String)
     */
    public int setDatabase(String database) {
        try {
            this.database = database;
            if (this.database != CHARNULL) {
                this.database = stripCRLF(this.database.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>database = " + this.database);
        } catch (Exception e) {
            setDatabaseError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return databaseError;
    } // method setDatabase

    /**
     * Called when an exception has occured
     * @param  database (String)
     */
    private void setDatabaseError (String database, Exception e, int error) {
        this.database = database;
        databaseErrorMessage = e.toString();
        databaseError = error;
    } // method setDatabaseError


    /**
     * Set the 'discipline' class variable
     * @param  discipline (String)
     */
    public int setDiscipline(String discipline) {
        try {
            this.discipline = discipline;
            if (this.discipline != CHARNULL) {
                this.discipline = stripCRLF(this.discipline.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>discipline = " + this.discipline);
        } catch (Exception e) {
            setDisciplineError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return disciplineError;
    } // method setDiscipline

    /**
     * Called when an exception has occured
     * @param  discipline (String)
     */
    private void setDisciplineError (String discipline, Exception e, int error) {
        this.discipline = discipline;
        disciplineErrorMessage = e.toString();
        disciplineError = error;
    } // method setDisciplineError


    /**
     * Set the 'product' class variable
     * @param  product (String)
     */
    public int setProduct(String product) {
        try {
            this.product = product;
            if (this.product != CHARNULL) {
                this.product = stripCRLF(this.product.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>product = " + this.product);
        } catch (Exception e) {
            setProductError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return productError;
    } // method setProduct

    /**
     * Called when an exception has occured
     * @param  product (String)
     */
    private void setProductError (String product, Exception e, int error) {
        this.product = product;
        productErrorMessage = e.toString();
        productError = error;
    } // method setProductError


    /**
     * Set the 'fileName' class variable
     * @param  fileName (String)
     */
    public int setFileName(String fileName) {
        try {
            this.fileName = fileName;
            if (this.fileName != CHARNULL) {
                this.fileName = stripCRLF(this.fileName.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>fileName = " + this.fileName);
        } catch (Exception e) {
            setFileNameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return fileNameError;
    } // method setFileName

    /**
     * Called when an exception has occured
     * @param  fileName (String)
     */
    private void setFileNameError (String fileName, Exception e, int error) {
        this.fileName = fileName;
        fileNameErrorMessage = e.toString();
        fileNameError = error;
    } // method setFileNameError


    /**
     * Set the 'latitudeNorth' class variable
     * @param  latitudeNorth (float)
     */
    public int setLatitudeNorth(float latitudeNorth) {
        try {
            if ( ((latitudeNorth == FLOATNULL) ||
                  (latitudeNorth == FLOATNULL2)) ||
                !((latitudeNorth < LATITUDE_NORTH_MN) ||
                  (latitudeNorth > LATITUDE_NORTH_MX)) ) {
                this.latitudeNorth = latitudeNorth;
                latitudeNorthError = ERROR_NORMAL;
            } else {
                throw latitudeNorthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatitudeNorthError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return latitudeNorthError;
    } // method setLatitudeNorth

    /**
     * Set the 'latitudeNorth' class variable
     * @param  latitudeNorth (Integer)
     */
    public int setLatitudeNorth(Integer latitudeNorth) {
        try {
            setLatitudeNorth(latitudeNorth.floatValue());
        } catch (Exception e) {
            setLatitudeNorthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeNorthError;
    } // method setLatitudeNorth

    /**
     * Set the 'latitudeNorth' class variable
     * @param  latitudeNorth (Float)
     */
    public int setLatitudeNorth(Float latitudeNorth) {
        try {
            setLatitudeNorth(latitudeNorth.floatValue());
        } catch (Exception e) {
            setLatitudeNorthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeNorthError;
    } // method setLatitudeNorth

    /**
     * Set the 'latitudeNorth' class variable
     * @param  latitudeNorth (String)
     */
    public int setLatitudeNorth(String latitudeNorth) {
        try {
            setLatitudeNorth(new Float(latitudeNorth).floatValue());
        } catch (Exception e) {
            setLatitudeNorthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeNorthError;
    } // method setLatitudeNorth

    /**
     * Called when an exception has occured
     * @param  latitudeNorth (String)
     */
    private void setLatitudeNorthError (float latitudeNorth, Exception e, int error) {
        this.latitudeNorth = latitudeNorth;
        latitudeNorthErrorMessage = e.toString();
        latitudeNorthError = error;
    } // method setLatitudeNorthError


    /**
     * Set the 'latitudeSouth' class variable
     * @param  latitudeSouth (float)
     */
    public int setLatitudeSouth(float latitudeSouth) {
        try {
            if ( ((latitudeSouth == FLOATNULL) ||
                  (latitudeSouth == FLOATNULL2)) ||
                !((latitudeSouth < LATITUDE_SOUTH_MN) ||
                  (latitudeSouth > LATITUDE_SOUTH_MX)) ) {
                this.latitudeSouth = latitudeSouth;
                latitudeSouthError = ERROR_NORMAL;
            } else {
                throw latitudeSouthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLatitudeSouthError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return latitudeSouthError;
    } // method setLatitudeSouth

    /**
     * Set the 'latitudeSouth' class variable
     * @param  latitudeSouth (Integer)
     */
    public int setLatitudeSouth(Integer latitudeSouth) {
        try {
            setLatitudeSouth(latitudeSouth.floatValue());
        } catch (Exception e) {
            setLatitudeSouthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeSouthError;
    } // method setLatitudeSouth

    /**
     * Set the 'latitudeSouth' class variable
     * @param  latitudeSouth (Float)
     */
    public int setLatitudeSouth(Float latitudeSouth) {
        try {
            setLatitudeSouth(latitudeSouth.floatValue());
        } catch (Exception e) {
            setLatitudeSouthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeSouthError;
    } // method setLatitudeSouth

    /**
     * Set the 'latitudeSouth' class variable
     * @param  latitudeSouth (String)
     */
    public int setLatitudeSouth(String latitudeSouth) {
        try {
            setLatitudeSouth(new Float(latitudeSouth).floatValue());
        } catch (Exception e) {
            setLatitudeSouthError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return latitudeSouthError;
    } // method setLatitudeSouth

    /**
     * Called when an exception has occured
     * @param  latitudeSouth (String)
     */
    private void setLatitudeSouthError (float latitudeSouth, Exception e, int error) {
        this.latitudeSouth = latitudeSouth;
        latitudeSouthErrorMessage = e.toString();
        latitudeSouthError = error;
    } // method setLatitudeSouthError


    /**
     * Set the 'longitudeWest' class variable
     * @param  longitudeWest (float)
     */
    public int setLongitudeWest(float longitudeWest) {
        try {
            if ( ((longitudeWest == FLOATNULL) ||
                  (longitudeWest == FLOATNULL2)) ||
                !((longitudeWest < LONGITUDE_WEST_MN) ||
                  (longitudeWest > LONGITUDE_WEST_MX)) ) {
                this.longitudeWest = longitudeWest;
                longitudeWestError = ERROR_NORMAL;
            } else {
                throw longitudeWestOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLongitudeWestError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return longitudeWestError;
    } // method setLongitudeWest

    /**
     * Set the 'longitudeWest' class variable
     * @param  longitudeWest (Integer)
     */
    public int setLongitudeWest(Integer longitudeWest) {
        try {
            setLongitudeWest(longitudeWest.floatValue());
        } catch (Exception e) {
            setLongitudeWestError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeWestError;
    } // method setLongitudeWest

    /**
     * Set the 'longitudeWest' class variable
     * @param  longitudeWest (Float)
     */
    public int setLongitudeWest(Float longitudeWest) {
        try {
            setLongitudeWest(longitudeWest.floatValue());
        } catch (Exception e) {
            setLongitudeWestError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeWestError;
    } // method setLongitudeWest

    /**
     * Set the 'longitudeWest' class variable
     * @param  longitudeWest (String)
     */
    public int setLongitudeWest(String longitudeWest) {
        try {
            setLongitudeWest(new Float(longitudeWest).floatValue());
        } catch (Exception e) {
            setLongitudeWestError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeWestError;
    } // method setLongitudeWest

    /**
     * Called when an exception has occured
     * @param  longitudeWest (String)
     */
    private void setLongitudeWestError (float longitudeWest, Exception e, int error) {
        this.longitudeWest = longitudeWest;
        longitudeWestErrorMessage = e.toString();
        longitudeWestError = error;
    } // method setLongitudeWestError


    /**
     * Set the 'longitudeEast' class variable
     * @param  longitudeEast (float)
     */
    public int setLongitudeEast(float longitudeEast) {
        try {
            if ( ((longitudeEast == FLOATNULL) ||
                  (longitudeEast == FLOATNULL2)) ||
                !((longitudeEast < LONGITUDE_EAST_MN) ||
                  (longitudeEast > LONGITUDE_EAST_MX)) ) {
                this.longitudeEast = longitudeEast;
                longitudeEastError = ERROR_NORMAL;
            } else {
                throw longitudeEastOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setLongitudeEastError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return longitudeEastError;
    } // method setLongitudeEast

    /**
     * Set the 'longitudeEast' class variable
     * @param  longitudeEast (Integer)
     */
    public int setLongitudeEast(Integer longitudeEast) {
        try {
            setLongitudeEast(longitudeEast.floatValue());
        } catch (Exception e) {
            setLongitudeEastError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeEastError;
    } // method setLongitudeEast

    /**
     * Set the 'longitudeEast' class variable
     * @param  longitudeEast (Float)
     */
    public int setLongitudeEast(Float longitudeEast) {
        try {
            setLongitudeEast(longitudeEast.floatValue());
        } catch (Exception e) {
            setLongitudeEastError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeEastError;
    } // method setLongitudeEast

    /**
     * Set the 'longitudeEast' class variable
     * @param  longitudeEast (String)
     */
    public int setLongitudeEast(String longitudeEast) {
        try {
            setLongitudeEast(new Float(longitudeEast).floatValue());
        } catch (Exception e) {
            setLongitudeEastError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return longitudeEastError;
    } // method setLongitudeEast

    /**
     * Called when an exception has occured
     * @param  longitudeEast (String)
     */
    private void setLongitudeEastError (float longitudeEast, Exception e, int error) {
        this.longitudeEast = longitudeEast;
        longitudeEastErrorMessage = e.toString();
        longitudeEastError = error;
    } // method setLongitudeEastError


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
     * Set the 'numRecords' class variable
     * @param  numRecords (int)
     */
    public int setNumRecords(int numRecords) {
        try {
            if ( ((numRecords == INTNULL) ||
                  (numRecords == INTNULL2)) ||
                !((numRecords < NUM_RECORDS_MN) ||
                  (numRecords > NUM_RECORDS_MX)) ) {
                this.numRecords = numRecords;
                numRecordsError = ERROR_NORMAL;
            } else {
                throw numRecordsOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setNumRecordsError(INTNULL, e, ERROR_LOCAL);
        } // try
        return numRecordsError;
    } // method setNumRecords

    /**
     * Set the 'numRecords' class variable
     * @param  numRecords (Integer)
     */
    public int setNumRecords(Integer numRecords) {
        try {
            setNumRecords(numRecords.intValue());
        } catch (Exception e) {
            setNumRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numRecordsError;
    } // method setNumRecords

    /**
     * Set the 'numRecords' class variable
     * @param  numRecords (Float)
     */
    public int setNumRecords(Float numRecords) {
        try {
            if (numRecords.floatValue() == FLOATNULL) {
                setNumRecords(INTNULL);
            } else {
                setNumRecords(numRecords.intValue());
            } // if (numRecords.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setNumRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numRecordsError;
    } // method setNumRecords

    /**
     * Set the 'numRecords' class variable
     * @param  numRecords (String)
     */
    public int setNumRecords(String numRecords) {
        try {
            setNumRecords(new Integer(numRecords).intValue());
        } catch (Exception e) {
            setNumRecordsError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return numRecordsError;
    } // method setNumRecords

    /**
     * Called when an exception has occured
     * @param  numRecords (String)
     */
    private void setNumRecordsError (int numRecords, Exception e, int error) {
        this.numRecords = numRecords;
        numRecordsErrorMessage = e.toString();
        numRecordsError = error;
    } // method setNumRecordsError


    /**
     * Set the 'jobDuration' class variable
     * @param  jobDuration (String)
     */
    public int setJobDuration(String jobDuration) {
        try {
            this.jobDuration = jobDuration;
            if (this.jobDuration != CHARNULL) {
                this.jobDuration = stripCRLF(this.jobDuration.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>jobDuration = " + this.jobDuration);
        } catch (Exception e) {
            setJobDurationError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return jobDurationError;
    } // method setJobDuration

    /**
     * Called when an exception has occured
     * @param  jobDuration (String)
     */
    private void setJobDurationError (String jobDuration, Exception e, int error) {
        this.jobDuration = jobDuration;
        jobDurationErrorMessage = e.toString();
        jobDurationError = error;
    } // method setJobDurationError


    /**
     * Set the 'queueDuration' class variable
     * @param  queueDuration (String)
     */
    public int setQueueDuration(String queueDuration) {
        try {
            this.queueDuration = queueDuration;
            if (this.queueDuration != CHARNULL) {
                this.queueDuration = stripCRLF(this.queueDuration.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>queueDuration = " + this.queueDuration);
        } catch (Exception e) {
            setQueueDurationError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return queueDurationError;
    } // method setQueueDuration

    /**
     * Called when an exception has occured
     * @param  queueDuration (String)
     */
    private void setQueueDurationError (String queueDuration, Exception e, int error) {
        this.queueDuration = queueDuration;
        queueDurationErrorMessage = e.toString();
        queueDurationError = error;
    } // method setQueueDurationError


    //=================//
    // All the getters //
    //=================//

    /**
     * Return the 'userid' class variable
     * @return userid (String)
     */
    public String getUserid() {
        return userid;
    } // method getUserid

    /**
     * Return the 'userid' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getUserid methods
     * @return userid (String)
     */
    public String getUserid(String s) {
        return (userid != CHARNULL ? userid.replace('"','\'') : "");
    } // method getUserid


    /**
     * Return the 'dateTime' class variable
     * @return dateTime (Timestamp)
     */
    public Timestamp getDateTime() {
        return dateTime;
    } // method getDateTime

    /**
     * Return the 'dateTime' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDateTime methods
     * @return dateTime (String)
     */
    public String getDateTime(String s) {
        if (dateTime.equals(DATENULL)) {
            return ("");
        } else {
            String dateTimeStr = dateTime.toString();
            return dateTimeStr.substring(0,dateTimeStr.indexOf('.'));
        } // if
    } // method getDateTime


    /**
     * Return the 'database' class variable
     * @return database (String)
     */
    public String getDatabase() {
        return database;
    } // method getDatabase

    /**
     * Return the 'database' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDatabase methods
     * @return database (String)
     */
    public String getDatabase(String s) {
        return (database != CHARNULL ? database.replace('"','\'') : "");
    } // method getDatabase


    /**
     * Return the 'discipline' class variable
     * @return discipline (String)
     */
    public String getDiscipline() {
        return discipline;
    } // method getDiscipline

    /**
     * Return the 'discipline' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDiscipline methods
     * @return discipline (String)
     */
    public String getDiscipline(String s) {
        return (discipline != CHARNULL ? discipline.replace('"','\'') : "");
    } // method getDiscipline


    /**
     * Return the 'product' class variable
     * @return product (String)
     */
    public String getProduct() {
        return product;
    } // method getProduct

    /**
     * Return the 'product' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getProduct methods
     * @return product (String)
     */
    public String getProduct(String s) {
        return (product != CHARNULL ? product.replace('"','\'') : "");
    } // method getProduct


    /**
     * Return the 'fileName' class variable
     * @return fileName (String)
     */
    public String getFileName() {
        return fileName;
    } // method getFileName

    /**
     * Return the 'fileName' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFileName methods
     * @return fileName (String)
     */
    public String getFileName(String s) {
        return (fileName != CHARNULL ? fileName.replace('"','\'') : "");
    } // method getFileName


    /**
     * Return the 'latitudeNorth' class variable
     * @return latitudeNorth (float)
     */
    public float getLatitudeNorth() {
        return latitudeNorth;
    } // method getLatitudeNorth

    /**
     * Return the 'latitudeNorth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLatitudeNorth methods
     * @return latitudeNorth (String)
     */
    public String getLatitudeNorth(String s) {
        return ((latitudeNorth != FLOATNULL) ? new Float(latitudeNorth).toString() : "");
    } // method getLatitudeNorth


    /**
     * Return the 'latitudeSouth' class variable
     * @return latitudeSouth (float)
     */
    public float getLatitudeSouth() {
        return latitudeSouth;
    } // method getLatitudeSouth

    /**
     * Return the 'latitudeSouth' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLatitudeSouth methods
     * @return latitudeSouth (String)
     */
    public String getLatitudeSouth(String s) {
        return ((latitudeSouth != FLOATNULL) ? new Float(latitudeSouth).toString() : "");
    } // method getLatitudeSouth


    /**
     * Return the 'longitudeWest' class variable
     * @return longitudeWest (float)
     */
    public float getLongitudeWest() {
        return longitudeWest;
    } // method getLongitudeWest

    /**
     * Return the 'longitudeWest' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLongitudeWest methods
     * @return longitudeWest (String)
     */
    public String getLongitudeWest(String s) {
        return ((longitudeWest != FLOATNULL) ? new Float(longitudeWest).toString() : "");
    } // method getLongitudeWest


    /**
     * Return the 'longitudeEast' class variable
     * @return longitudeEast (float)
     */
    public float getLongitudeEast() {
        return longitudeEast;
    } // method getLongitudeEast

    /**
     * Return the 'longitudeEast' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLongitudeEast methods
     * @return longitudeEast (String)
     */
    public String getLongitudeEast(String s) {
        return ((longitudeEast != FLOATNULL) ? new Float(longitudeEast).toString() : "");
    } // method getLongitudeEast


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
     * Return the 'numRecords' class variable
     * @return numRecords (int)
     */
    public int getNumRecords() {
        return numRecords;
    } // method getNumRecords

    /**
     * Return the 'numRecords' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getNumRecords methods
     * @return numRecords (String)
     */
    public String getNumRecords(String s) {
        return ((numRecords != INTNULL) ? new Integer(numRecords).toString() : "");
    } // method getNumRecords


    /**
     * Return the 'jobDuration' class variable
     * @return jobDuration (String)
     */
    public String getJobDuration() {
        return jobDuration;
    } // method getJobDuration

    /**
     * Return the 'jobDuration' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getJobDuration methods
     * @return jobDuration (String)
     */
    public String getJobDuration(String s) {
        return (jobDuration != CHARNULL ? jobDuration.replace('"','\'') : "");
    } // method getJobDuration


    /**
     * Return the 'queueDuration' class variable
     * @return queueDuration (String)
     */
    public String getQueueDuration() {
        return queueDuration;
    } // method getQueueDuration

    /**
     * Return the 'queueDuration' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getQueueDuration methods
     * @return queueDuration (String)
     */
    public String getQueueDuration(String s) {
        return (queueDuration != CHARNULL ? queueDuration.replace('"','\'') : "");
    } // method getQueueDuration


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
        if ((userid == CHARNULL) &&
            (dateTime.equals(DATENULL)) &&
            (database == CHARNULL) &&
            (discipline == CHARNULL) &&
            (product == CHARNULL) &&
            (fileName == CHARNULL) &&
            (latitudeNorth == FLOATNULL) &&
            (latitudeSouth == FLOATNULL) &&
            (longitudeWest == FLOATNULL) &&
            (longitudeEast == FLOATNULL) &&
            (dateStart.equals(DATENULL)) &&
            (dateEnd.equals(DATENULL)) &&
            (surveyId == CHARNULL) &&
            (numRecords == INTNULL) &&
            (jobDuration == CHARNULL) &&
            (queueDuration == CHARNULL)) {
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
        int sumError = useridError +
            dateTimeError +
            databaseError +
            disciplineError +
            productError +
            fileNameError +
            latitudeNorthError +
            latitudeSouthError +
            longitudeWestError +
            longitudeEastError +
            dateStartError +
            dateEndError +
            surveyIdError +
            numRecordsError +
            jobDurationError +
            queueDurationError;
        return (sumError == 0 ? true : false);
    } // method isValidRecord


    /**
     * Gets the errorcode for the userid instance variable
     * @return errorcode (int)
     */
    public int getUseridError() {
        return useridError;
    } // method getUseridError

    /**
     * Gets the errorMessage for the userid instance variable
     * @return errorMessage (String)
     */
    public String getUseridErrorMessage() {
        return useridErrorMessage;
    } // method getUseridErrorMessage


    /**
     * Gets the errorcode for the dateTime instance variable
     * @return errorcode (int)
     */
    public int getDateTimeError() {
        return dateTimeError;
    } // method getDateTimeError

    /**
     * Gets the errorMessage for the dateTime instance variable
     * @return errorMessage (String)
     */
    public String getDateTimeErrorMessage() {
        return dateTimeErrorMessage;
    } // method getDateTimeErrorMessage


    /**
     * Gets the errorcode for the database instance variable
     * @return errorcode (int)
     */
    public int getDatabaseError() {
        return databaseError;
    } // method getDatabaseError

    /**
     * Gets the errorMessage for the database instance variable
     * @return errorMessage (String)
     */
    public String getDatabaseErrorMessage() {
        return databaseErrorMessage;
    } // method getDatabaseErrorMessage


    /**
     * Gets the errorcode for the discipline instance variable
     * @return errorcode (int)
     */
    public int getDisciplineError() {
        return disciplineError;
    } // method getDisciplineError

    /**
     * Gets the errorMessage for the discipline instance variable
     * @return errorMessage (String)
     */
    public String getDisciplineErrorMessage() {
        return disciplineErrorMessage;
    } // method getDisciplineErrorMessage


    /**
     * Gets the errorcode for the product instance variable
     * @return errorcode (int)
     */
    public int getProductError() {
        return productError;
    } // method getProductError

    /**
     * Gets the errorMessage for the product instance variable
     * @return errorMessage (String)
     */
    public String getProductErrorMessage() {
        return productErrorMessage;
    } // method getProductErrorMessage


    /**
     * Gets the errorcode for the fileName instance variable
     * @return errorcode (int)
     */
    public int getFileNameError() {
        return fileNameError;
    } // method getFileNameError

    /**
     * Gets the errorMessage for the fileName instance variable
     * @return errorMessage (String)
     */
    public String getFileNameErrorMessage() {
        return fileNameErrorMessage;
    } // method getFileNameErrorMessage


    /**
     * Gets the errorcode for the latitudeNorth instance variable
     * @return errorcode (int)
     */
    public int getLatitudeNorthError() {
        return latitudeNorthError;
    } // method getLatitudeNorthError

    /**
     * Gets the errorMessage for the latitudeNorth instance variable
     * @return errorMessage (String)
     */
    public String getLatitudeNorthErrorMessage() {
        return latitudeNorthErrorMessage;
    } // method getLatitudeNorthErrorMessage


    /**
     * Gets the errorcode for the latitudeSouth instance variable
     * @return errorcode (int)
     */
    public int getLatitudeSouthError() {
        return latitudeSouthError;
    } // method getLatitudeSouthError

    /**
     * Gets the errorMessage for the latitudeSouth instance variable
     * @return errorMessage (String)
     */
    public String getLatitudeSouthErrorMessage() {
        return latitudeSouthErrorMessage;
    } // method getLatitudeSouthErrorMessage


    /**
     * Gets the errorcode for the longitudeWest instance variable
     * @return errorcode (int)
     */
    public int getLongitudeWestError() {
        return longitudeWestError;
    } // method getLongitudeWestError

    /**
     * Gets the errorMessage for the longitudeWest instance variable
     * @return errorMessage (String)
     */
    public String getLongitudeWestErrorMessage() {
        return longitudeWestErrorMessage;
    } // method getLongitudeWestErrorMessage


    /**
     * Gets the errorcode for the longitudeEast instance variable
     * @return errorcode (int)
     */
    public int getLongitudeEastError() {
        return longitudeEastError;
    } // method getLongitudeEastError

    /**
     * Gets the errorMessage for the longitudeEast instance variable
     * @return errorMessage (String)
     */
    public String getLongitudeEastErrorMessage() {
        return longitudeEastErrorMessage;
    } // method getLongitudeEastErrorMessage


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
     * Gets the errorcode for the numRecords instance variable
     * @return errorcode (int)
     */
    public int getNumRecordsError() {
        return numRecordsError;
    } // method getNumRecordsError

    /**
     * Gets the errorMessage for the numRecords instance variable
     * @return errorMessage (String)
     */
    public String getNumRecordsErrorMessage() {
        return numRecordsErrorMessage;
    } // method getNumRecordsErrorMessage


    /**
     * Gets the errorcode for the jobDuration instance variable
     * @return errorcode (int)
     */
    public int getJobDurationError() {
        return jobDurationError;
    } // method getJobDurationError

    /**
     * Gets the errorMessage for the jobDuration instance variable
     * @return errorMessage (String)
     */
    public String getJobDurationErrorMessage() {
        return jobDurationErrorMessage;
    } // method getJobDurationErrorMessage


    /**
     * Gets the errorcode for the queueDuration instance variable
     * @return errorcode (int)
     */
    public int getQueueDurationError() {
        return queueDurationError;
    } // method getQueueDurationError

    /**
     * Gets the errorMessage for the queueDuration instance variable
     * @return errorMessage (String)
     */
    public String getQueueDurationErrorMessage() {
        return queueDurationErrorMessage;
    } // method getQueueDurationErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of LogUsers. e.g.<pre>
     * LogUsers logUsers = new LogUsers(<val1>);
     * LogUsers logUsersArray[] = logUsers.get();</pre>
     * will get the LogUsers record where userid = <val1>.
     * @return Array of LogUsers (LogUsers[])
     */
    public LogUsers[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * LogUsers logUsersArray[] =
     *     new LogUsers().get(LogUsers.USERID+"=<val1>");</pre>
     * will get the LogUsers record where userid = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of LogUsers (LogUsers[])
     */
    public LogUsers[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * LogUsers logUsersArray[] =
     *     new LogUsers().get("1=1",logUsers.USERID);</pre>
     * will get the all the LogUsers records, and order them by userid.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of LogUsers (LogUsers[])
     */
    public LogUsers[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = LogUsers.USERID,LogUsers.DATE_TIME;
     * String where = LogUsers.USERID + "=<val1";
     * String order = LogUsers.USERID;
     * LogUsers logUsersArray[] =
     *     new LogUsers().get(columns, where, order);</pre>
     * will get the userid and dateTime colums of all LogUsers records,
     * where userid = <val1>, and order them by userid.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of LogUsers (LogUsers[])
     */
    public LogUsers[] get (String fields, String where, String order) {
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
     * and transforms it into an array of LogUsers.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private LogUsers[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int useridCol        = db.getColNumber(USERID);
        int dateTimeCol      = db.getColNumber(DATE_TIME);
        int databaseCol      = db.getColNumber(DATABASE);
        int disciplineCol    = db.getColNumber(DISCIPLINE);
        int productCol       = db.getColNumber(PRODUCT);
        int fileNameCol      = db.getColNumber(FILE_NAME);
        int latitudeNorthCol = db.getColNumber(LATITUDE_NORTH);
        int latitudeSouthCol = db.getColNumber(LATITUDE_SOUTH);
        int longitudeWestCol = db.getColNumber(LONGITUDE_WEST);
        int longitudeEastCol = db.getColNumber(LONGITUDE_EAST);
        int dateStartCol     = db.getColNumber(DATE_START);
        int dateEndCol       = db.getColNumber(DATE_END);
        int surveyIdCol      = db.getColNumber(SURVEY_ID);
        int numRecordsCol    = db.getColNumber(NUM_RECORDS);
        int jobDurationCol   = db.getColNumber(JOB_DURATION);
        int queueDurationCol = db.getColNumber(QUEUE_DURATION);
        LogUsers[] cArray = new LogUsers[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new LogUsers();
            if (useridCol != -1)
                cArray[i].setUserid       ((String) row.elementAt(useridCol));
            if (dateTimeCol != -1)
                cArray[i].setDateTime     ((String) row.elementAt(dateTimeCol));
            if (databaseCol != -1)
                cArray[i].setDatabase     ((String) row.elementAt(databaseCol));
            if (disciplineCol != -1)
                cArray[i].setDiscipline   ((String) row.elementAt(disciplineCol));
            if (productCol != -1)
                cArray[i].setProduct      ((String) row.elementAt(productCol));
            if (fileNameCol != -1)
                cArray[i].setFileName     ((String) row.elementAt(fileNameCol));
            if (latitudeNorthCol != -1)
                cArray[i].setLatitudeNorth((String) row.elementAt(latitudeNorthCol));
            if (latitudeSouthCol != -1)
                cArray[i].setLatitudeSouth((String) row.elementAt(latitudeSouthCol));
            if (longitudeWestCol != -1)
                cArray[i].setLongitudeWest((String) row.elementAt(longitudeWestCol));
            if (longitudeEastCol != -1)
                cArray[i].setLongitudeEast((String) row.elementAt(longitudeEastCol));
            if (dateStartCol != -1)
                cArray[i].setDateStart    ((String) row.elementAt(dateStartCol));
            if (dateEndCol != -1)
                cArray[i].setDateEnd      ((String) row.elementAt(dateEndCol));
            if (surveyIdCol != -1)
                cArray[i].setSurveyId     ((String) row.elementAt(surveyIdCol));
            if (numRecordsCol != -1)
                cArray[i].setNumRecords   ((String) row.elementAt(numRecordsCol));
            if (jobDurationCol != -1)
                cArray[i].setJobDuration  ((String) row.elementAt(jobDurationCol));
            if (queueDurationCol != -1)
                cArray[i].setQueueDuration  ((String) row.elementAt(queueDurationCol));
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
     *     new LogUsers(
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
     *         <val16>).put();</pre>
     * will insert a record with:
     *     userid        = <val1>,
     *     dateTime      = <val2>,
     *     database      = <val3>,
     *     discipline    = <val4>,
     *     product       = <val5>,
     *     fileName      = <val6>,
     *     latitudeNorth = <val7>,
     *     latitudeSouth = <val8>,
     *     longitudeWest = <val9>,
     *     longitudeEast = <val10>,
     *     dateStart     = <val11>,
     *     dateEnd       = <val12>,
     *     surveyId      = <val13>,
     *     numRecords    = <val14>,
     *     jobDuration   = <val15>.
     *     queueDuration = <val16>.
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
     * Boolean success = new LogUsers(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.DATENULL,
     *     Tables.DATENULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where dateTime = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * LogUsers logUsers = new LogUsers();
     * success = logUsers.del(LogUsers.USERID+"=<val1>");</pre>
     * will delete all records where userid = <val1>.
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
     * update are taken from the LogUsers argument, .e.g.<pre>
     * Boolean success
     * LogUsers updLogUsers = new LogUsers();
     * updLogUsers.setDateTime(<val2>);
     * LogUsers whereLogUsers = new LogUsers(<val1>);
     * success = whereLogUsers.upd(updLogUsers);</pre>
     * will update DateTime to <val2> for all records where
     * userid = <val1>.
     * @param  logUsers  A LogUsers variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(LogUsers logUsers) {
        return db.update (TABLE, createColVals(logUsers), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * LogUsers updLogUsers = new LogUsers();
     * updLogUsers.setDateTime(<val2>);
     * LogUsers whereLogUsers = new LogUsers();
     * success = whereLogUsers.upd(
     *     updLogUsers, LogUsers.USERID+"=<val1>");</pre>
     * will update DateTime to <val2> for all records where
     * userid = <val1>.
     * @param  logUsers  A LogUsers variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(LogUsers logUsers, String where) {
        return db.update (TABLE, createColVals(logUsers), where);
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
        if (getUserid() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + USERID + "='" + getUserid() + "'";
        } // if getUserid
        if (!getDateTime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATE_TIME +
                "=" + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getDatabase() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATABASE + "='" + getDatabase() + "'";
        } // if getDatabase
        if (getDiscipline() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DISCIPLINE + "='" + getDiscipline() + "'";
        } // if getDiscipline
        if (getProduct() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PRODUCT + "='" + getProduct() + "'";
        } // if getProduct
        if (getFileName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FILE_NAME + "='" + getFileName() + "'";
        } // if getFileName
        if (getLatitudeNorth() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LATITUDE_NORTH + "=" + getLatitudeNorth("");
        } // if getLatitudeNorth
        if (getLatitudeSouth() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LATITUDE_SOUTH + "=" + getLatitudeSouth("");
        } // if getLatitudeSouth
        if (getLongitudeWest() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LONGITUDE_WEST + "=" + getLongitudeWest("");
        } // if getLongitudeWest
        if (getLongitudeEast() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LONGITUDE_EAST + "=" + getLongitudeEast("");
        } // if getLongitudeEast
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
        if (getSurveyId() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURVEY_ID + "='" + getSurveyId() + "'";
        } // if getSurveyId
        if (getNumRecords() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NUM_RECORDS + "=" + getNumRecords("");
        } // if getNumRecords
        if (getJobDuration() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + JOB_DURATION + "='" + getJobDuration() + "'";
        } // if getJobDuration
        if (getQueueDuration() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + QUEUE_DURATION + "='" + getQueueDuration() + "'";
        } // if getQueueDuration
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(LogUsers aVar) {
        String colVals = "";
        if (aVar.getUserid() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += USERID +"=";
            colVals += (aVar.getUserid().equals(CHARNULL2) ?
                "null" : "'" + aVar.getUserid() + "'");
        } // if aVar.getUserid
        if (!aVar.getDateTime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATE_TIME +"=";
            colVals += (aVar.getDateTime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDateTime()));
        } // if aVar.getDateTime
        if (aVar.getDatabase() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATABASE +"=";
            colVals += (aVar.getDatabase().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDatabase() + "'");
        } // if aVar.getDatabase
        if (aVar.getDiscipline() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DISCIPLINE +"=";
            colVals += (aVar.getDiscipline().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDiscipline() + "'");
        } // if aVar.getDiscipline
        if (aVar.getProduct() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PRODUCT +"=";
            colVals += (aVar.getProduct().equals(CHARNULL2) ?
                "null" : "'" + aVar.getProduct() + "'");
        } // if aVar.getProduct
        if (aVar.getFileName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FILE_NAME +"=";
            colVals += (aVar.getFileName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getFileName() + "'");
        } // if aVar.getFileName
        if (aVar.getLatitudeNorth() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LATITUDE_NORTH +"=";
            colVals += (aVar.getLatitudeNorth() == FLOATNULL2 ?
                "null" : aVar.getLatitudeNorth(""));
        } // if aVar.getLatitudeNorth
        if (aVar.getLatitudeSouth() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LATITUDE_SOUTH +"=";
            colVals += (aVar.getLatitudeSouth() == FLOATNULL2 ?
                "null" : aVar.getLatitudeSouth(""));
        } // if aVar.getLatitudeSouth
        if (aVar.getLongitudeWest() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LONGITUDE_WEST +"=";
            colVals += (aVar.getLongitudeWest() == FLOATNULL2 ?
                "null" : aVar.getLongitudeWest(""));
        } // if aVar.getLongitudeWest
        if (aVar.getLongitudeEast() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LONGITUDE_EAST +"=";
            colVals += (aVar.getLongitudeEast() == FLOATNULL2 ?
                "null" : aVar.getLongitudeEast(""));
        } // if aVar.getLongitudeEast
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
        if (aVar.getSurveyId() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURVEY_ID +"=";
            colVals += (aVar.getSurveyId().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurveyId() + "'");
        } // if aVar.getSurveyId
        if (aVar.getNumRecords() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NUM_RECORDS +"=";
            colVals += (aVar.getNumRecords() == INTNULL2 ?
                "null" : aVar.getNumRecords(""));
        } // if aVar.getNumRecords
        if (aVar.getJobDuration() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += JOB_DURATION +"=";
            colVals += (aVar.getJobDuration().equals(CHARNULL2) ?
                "null" : "'" + aVar.getJobDuration() + "'");
        } // if aVar.getJobDuration
        if (aVar.getQueueDuration() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += QUEUE_DURATION +"=";
            colVals += (aVar.getQueueDuration().equals(CHARNULL2) ?
                "null" : "'" + aVar.getQueueDuration() + "'");
        } // if aVar.getQueueDuration
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = USERID;
        if (!getDateTime().equals(DATENULL)) {
            columns = columns + "," + DATE_TIME;
        } // if getDateTime
        if (getDatabase() != CHARNULL) {
            columns = columns + "," + DATABASE;
        } // if getDatabase
        if (getDiscipline() != CHARNULL) {
            columns = columns + "," + DISCIPLINE;
        } // if getDiscipline
        if (getProduct() != CHARNULL) {
            columns = columns + "," + PRODUCT;
        } // if getProduct
        if (getFileName() != CHARNULL) {
            columns = columns + "," + FILE_NAME;
        } // if getFileName
        if (getLatitudeNorth() != FLOATNULL) {
            columns = columns + "," + LATITUDE_NORTH;
        } // if getLatitudeNorth
        if (getLatitudeSouth() != FLOATNULL) {
            columns = columns + "," + LATITUDE_SOUTH;
        } // if getLatitudeSouth
        if (getLongitudeWest() != FLOATNULL) {
            columns = columns + "," + LONGITUDE_WEST;
        } // if getLongitudeWest
        if (getLongitudeEast() != FLOATNULL) {
            columns = columns + "," + LONGITUDE_EAST;
        } // if getLongitudeEast
        if (!getDateStart().equals(DATENULL)) {
            columns = columns + "," + DATE_START;
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            columns = columns + "," + DATE_END;
        } // if getDateEnd
        if (getSurveyId() != CHARNULL) {
            columns = columns + "," + SURVEY_ID;
        } // if getSurveyId
        if (getNumRecords() != INTNULL) {
            columns = columns + "," + NUM_RECORDS;
        } // if getNumRecords
        if (getJobDuration() != CHARNULL) {
            columns = columns + "," + JOB_DURATION;
        } // if getJobDuration
        if (getQueueDuration() != CHARNULL) {
            columns = columns + "," + QUEUE_DURATION;
        } // if getQueueDuration
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getUserid() + "'";
        if (!getDateTime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateTime());
        } // if getDateTime
        if (getDatabase() != CHARNULL) {
            values  = values  + ",'" + getDatabase() + "'";
        } // if getDatabase
        if (getDiscipline() != CHARNULL) {
            values  = values  + ",'" + getDiscipline() + "'";
        } // if getDiscipline
        if (getProduct() != CHARNULL) {
            values  = values  + ",'" + getProduct() + "'";
        } // if getProduct
        if (getFileName() != CHARNULL) {
            values  = values  + ",'" + getFileName() + "'";
        } // if getFileName
        if (getLatitudeNorth() != FLOATNULL) {
            values  = values  + "," + getLatitudeNorth("");
        } // if getLatitudeNorth
        if (getLatitudeSouth() != FLOATNULL) {
            values  = values  + "," + getLatitudeSouth("");
        } // if getLatitudeSouth
        if (getLongitudeWest() != FLOATNULL) {
            values  = values  + "," + getLongitudeWest("");
        } // if getLongitudeWest
        if (getLongitudeEast() != FLOATNULL) {
            values  = values  + "," + getLongitudeEast("");
        } // if getLongitudeEast
        if (!getDateStart().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateStart());
        } // if getDateStart
        if (!getDateEnd().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDateEnd());
        } // if getDateEnd
        if (getSurveyId() != CHARNULL) {
            values  = values  + ",'" + getSurveyId() + "'";
        } // if getSurveyId
        if (getNumRecords() != INTNULL) {
            values  = values  + "," + getNumRecords("");
        } // if getNumRecords
        if (getJobDuration() != CHARNULL) {
            values  = values  + ",'" + getJobDuration() + "'";
        } // if getJobDuration
        if (getQueueDuration() != CHARNULL) {
            values  = values  + ",'" + getQueueDuration() + "'";
        } // if getQueueDuration
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getUserid("")        + "|" +
            getDateTime("")      + "|" +
            getDatabase("")      + "|" +
            getDiscipline("")    + "|" +
            getProduct("")       + "|" +
            getFileName("")      + "|" +
            getLatitudeNorth("") + "|" +
            getLatitudeSouth("") + "|" +
            getLongitudeWest("") + "|" +
            getLongitudeEast("") + "|" +
            getDateStart("")     + "|" +
            getDateEnd("")       + "|" +
            getSurveyId("")      + "|" +
            getNumRecords("")    + "|" +
            getJobDuration("")   + "|" +
            getQueueDuration("") + "|";
    } // method toString

} // class LogUsers
