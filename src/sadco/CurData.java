package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the CUR_DATA table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class CurData extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "CUR_DATA";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The depthCode field name */
    public static final String DEPTH_CODE = "DEPTH_CODE";
    /** The datetime field name */
    public static final String DATETIME = "DATETIME";
    /** The speed field name */
    public static final String SPEED = "SPEED";
    /** The direction field name */
    public static final String DIRECTION = "DIRECTION";
    /** The temperature field name */
    public static final String TEMPERATURE = "TEMPERATURE";
    /** The vertVelocity field name */
    public static final String VERT_VELOCITY = "VERT_VELOCITY";
    /** The fSpeed9 field name */
    public static final String F_SPEED_9 = "F_SPEED_9";
    /** The fDirection9 field name */
    public static final String F_DIRECTION_9 = "F_DIRECTION_9";
    /** The fSpeed14 field name */
    public static final String F_SPEED_14 = "F_SPEED_14";
    /** The fDirection14 field name */
    public static final String F_DIRECTION_14 = "F_DIRECTION_14";
    /** The pressure field name */
    public static final String PRESSURE = "PRESSURE";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private int       depthCode;
    private Timestamp datetime;
    private float     speed;
    private float     direction;
    private float     temperature;
    private float     vertVelocity;
    private float     fSpeed9;
    private float     fDirection9;
    private float     fSpeed14;
    private float     fDirection14;
    private float     pressure;

    /** The error variables  */
    private int codeError         = ERROR_NORMAL;
    private int depthCodeError    = ERROR_NORMAL;
    private int datetimeError     = ERROR_NORMAL;
    private int speedError        = ERROR_NORMAL;
    private int directionError    = ERROR_NORMAL;
    private int temperatureError  = ERROR_NORMAL;
    private int vertVelocityError = ERROR_NORMAL;
    private int fSpeed9Error      = ERROR_NORMAL;
    private int fDirection9Error  = ERROR_NORMAL;
    private int fSpeed14Error     = ERROR_NORMAL;
    private int fDirection14Error = ERROR_NORMAL;
    private int pressureError     = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage         = "";
    private String depthCodeErrorMessage    = "";
    private String datetimeErrorMessage     = "";
    private String speedErrorMessage        = "";
    private String directionErrorMessage    = "";
    private String temperatureErrorMessage  = "";
    private String vertVelocityErrorMessage = "";
    private String fSpeed9ErrorMessage      = "";
    private String fDirection9ErrorMessage  = "";
    private String fSpeed14ErrorMessage     = "";
    private String fDirection14ErrorMessage = "";
    private String pressureErrorMessage     = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       DEPTH_CODE_MN = INTMIN;
    public static final int       DEPTH_CODE_MX = INTMAX;
    public static final Timestamp DATETIME_MN = DATEMIN;
    public static final Timestamp DATETIME_MX = DATEMAX;
    public static final float     SPEED_MN = FLOATMIN;
    public static final float     SPEED_MX = FLOATMAX;
    public static final float     DIRECTION_MN = FLOATMIN;
    public static final float     DIRECTION_MX = FLOATMAX;
    public static final float     TEMPERATURE_MN = FLOATMIN;
    public static final float     TEMPERATURE_MX = FLOATMAX;
    public static final float     VERT_VELOCITY_MN = FLOATMIN;
    public static final float     VERT_VELOCITY_MX = FLOATMAX;
    public static final float     F_SPEED_9_MN = FLOATMIN;
    public static final float     F_SPEED_9_MX = FLOATMAX;
    public static final float     F_DIRECTION_9_MN = FLOATMIN;
    public static final float     F_DIRECTION_9_MX = FLOATMAX;
    public static final float     F_SPEED_14_MN = FLOATMIN;
    public static final float     F_SPEED_14_MX = FLOATMAX;
    public static final float     F_DIRECTION_14_MN = FLOATMIN;
    public static final float     F_DIRECTION_14_MX = FLOATMAX;
    public static final float     PRESSURE_MN = FLOATMIN;
    public static final float     PRESSURE_MX = FLOATMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception depthCodeOutOfBoundsException =
        new Exception ("'depthCode' out of bounds: " +
            DEPTH_CODE_MN + " - " + DEPTH_CODE_MX);
    Exception datetimeException =
        new Exception ("'datetime' is null");
    Exception datetimeOutOfBoundsException =
        new Exception ("'datetime' out of bounds: " +
            DATETIME_MN + " - " + DATETIME_MX);
    Exception speedOutOfBoundsException =
        new Exception ("'speed' out of bounds: " +
            SPEED_MN + " - " + SPEED_MX);
    Exception directionOutOfBoundsException =
        new Exception ("'direction' out of bounds: " +
            DIRECTION_MN + " - " + DIRECTION_MX);
    Exception temperatureOutOfBoundsException =
        new Exception ("'temperature' out of bounds: " +
            TEMPERATURE_MN + " - " + TEMPERATURE_MX);
    Exception vertVelocityOutOfBoundsException =
        new Exception ("'vertVelocity' out of bounds: " +
            VERT_VELOCITY_MN + " - " + VERT_VELOCITY_MX);
    Exception fSpeed9OutOfBoundsException =
        new Exception ("'fSpeed9' out of bounds: " +
            F_SPEED_9_MN + " - " + F_SPEED_9_MX);
    Exception fDirection9OutOfBoundsException =
        new Exception ("'fDirection9' out of bounds: " +
            F_DIRECTION_9_MN + " - " + F_DIRECTION_9_MX);
    Exception fSpeed14OutOfBoundsException =
        new Exception ("'fSpeed14' out of bounds: " +
            F_SPEED_14_MN + " - " + F_SPEED_14_MX);
    Exception fDirection14OutOfBoundsException =
        new Exception ("'fDirection14' out of bounds: " +
            F_DIRECTION_14_MN + " - " + F_DIRECTION_14_MX);
    Exception pressureOutOfBoundsException =
        new Exception ("'pressure' out of bounds: " +
            PRESSURE_MN + " - " + PRESSURE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public CurData() {
        clearVars();
        if (dbg) System.out.println ("<br>in CurData constructor 1"); // debug
    } // CurData constructor

    /**
     * Instantiate a CurData object and initialize the instance variables.
     * @param code  The code (int)
     */
    public CurData(
            int code) {
        this();
        setCode         (code        );
        if (dbg) System.out.println ("<br>in CurData constructor 2"); // debug
    } // CurData constructor

    /**
     * Instantiate a CurData object and initialize the instance variables.
     * @param code          The code         (int)
     * @param depthCode     The depthCode    (int)
     * @param datetime      The datetime     (java.util.Date)
     * @param speed         The speed        (float)
     * @param direction     The direction    (float)
     * @param temperature   The temperature  (float)
     * @param vertVelocity  The vertVelocity (float)
     * @param fSpeed9       The fSpeed9      (float)
     * @param fDirection9   The fDirection9  (float)
     * @param fSpeed14      The fSpeed14     (float)
     * @param fDirection14  The fDirection14 (float)
     * @param pressure      The pressure     (float)
     */
    public CurData(
            int            code,
            int            depthCode,
            java.util.Date datetime,
            float          speed,
            float          direction,
            float          temperature,
            float          vertVelocity,
            float          fSpeed9,
            float          fDirection9,
            float          fSpeed14,
            float          fDirection14,
            float          pressure) {
        this();
        setCode         (code        );
        setDepthCode    (depthCode   );
        setDatetime     (datetime    );
        setSpeed        (speed       );
        setDirection    (direction   );
        setTemperature  (temperature );
        setVertVelocity (vertVelocity);
        setFSpeed9      (fSpeed9     );
        setFDirection9  (fDirection9 );
        setFSpeed14     (fSpeed14    );
        setFDirection14 (fDirection14);
        setPressure     (pressure    );
        if (dbg) System.out.println ("<br>in CurData constructor 3"); // debug
    } // CurData constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode         (INTNULL  );
        setDepthCode    (INTNULL  );
        setDatetime     (DATENULL );
        setSpeed        (FLOATNULL);
        setDirection    (FLOATNULL);
        setTemperature  (FLOATNULL);
        setVertVelocity (FLOATNULL);
        setFSpeed9      (FLOATNULL);
        setFDirection9  (FLOATNULL);
        setFSpeed14     (FLOATNULL);
        setFDirection14 (FLOATNULL);
        setPressure     (FLOATNULL);
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
     * Set the 'depthCode' class variable
     * @param  depthCode (int)
     */
    public int setDepthCode(int depthCode) {
        try {
            if ( ((depthCode == INTNULL) ||
                  (depthCode == INTNULL2)) ||
                !((depthCode < DEPTH_CODE_MN) ||
                  (depthCode > DEPTH_CODE_MX)) ) {
                this.depthCode = depthCode;
                depthCodeError = ERROR_NORMAL;
            } else {
                throw depthCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (Integer)
     */
    public int setDepthCode(Integer depthCode) {
        try {
            setDepthCode(depthCode.intValue());
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (Float)
     */
    public int setDepthCode(Float depthCode) {
        try {
            if (depthCode.floatValue() == FLOATNULL) {
                setDepthCode(INTNULL);
            } else {
                setDepthCode(depthCode.intValue());
            } // if (depthCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Set the 'depthCode' class variable
     * @param  depthCode (String)
     */
    public int setDepthCode(String depthCode) {
        try {
            setDepthCode(new Integer(depthCode).intValue());
        } catch (Exception e) {
            setDepthCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return depthCodeError;
    } // method setDepthCode

    /**
     * Called when an exception has occured
     * @param  depthCode (String)
     */
    private void setDepthCodeError (int depthCode, Exception e, int error) {
        this.depthCode = depthCode;
        depthCodeErrorMessage = e.toString();
        depthCodeError = error;
    } // method setDepthCodeError


    /**
     * Set the 'datetime' class variable
     * @param  datetime (Timestamp)
     */
    public int setDatetime(Timestamp datetime) {
        try {
            if (datetime == null) { this.datetime = DATENULL; }
            if ( (datetime.equals(DATENULL) ||
                  datetime.equals(DATENULL2)) ||
                !(datetime.before(DATETIME_MN) ||
                  datetime.after(DATETIME_MX)) ) {
                this.datetime = datetime;
                datetimeError = ERROR_NORMAL;
            } else {
                throw datetimeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDatetimeError(DATENULL, e, ERROR_LOCAL);
        } // try
        return datetimeError;
    } // method setDatetime

    /**
     * Set the 'datetime' class variable
     * @param  datetime (java.util.Date)
     */
    public int setDatetime(java.util.Date datetime) {
        try {
            setDatetime(new Timestamp(datetime.getTime()));
        } catch (Exception e) {
            setDatetimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return datetimeError;
    } // method setDatetime

    /**
     * Set the 'datetime' class variable
     * @param  datetime (String)
     */
    public int setDatetime(String datetime) {
        try {
            int len = datetime.length();
            switch (len) {
            // date &/or times
                case 4: datetime += "-01";
                case 7: datetime += "-01";
                case 10: datetime += " 00";
                case 13: datetime += ":00";
                case 16: datetime += ":00"; break;
                // times only
                case 5: datetime = "1800-01-01 " + datetime + ":00"; break;
                case 8: datetime = "1800-01-01 " + datetime; break;
            } // switch
            if (dbg) System.out.println ("datetime = " + datetime);
            setDatetime(Timestamp.valueOf(datetime));
        } catch (Exception e) {
            setDatetimeError(DATENULL, e, ERROR_SYSTEM);
        } // try
        return datetimeError;
    } // method setDatetime

    /**
     * Called when an exception has occured
     * @param  datetime (String)
     */
    private void setDatetimeError (Timestamp datetime, Exception e, int error) {
        this.datetime = datetime;
        datetimeErrorMessage = e.toString();
        datetimeError = error;
    } // method setDatetimeError


    /**
     * Set the 'speed' class variable
     * @param  speed (float)
     */
    public int setSpeed(float speed) {
        try {
            if ( ((speed == FLOATNULL) ||
                  (speed == FLOATNULL2)) ||
                !((speed < SPEED_MN) ||
                  (speed > SPEED_MX)) ) {
                this.speed = speed;
                speedError = ERROR_NORMAL;
            } else {
                throw speedOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSpeedError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return speedError;
    } // method setSpeed

    /**
     * Set the 'speed' class variable
     * @param  speed (Integer)
     */
    public int setSpeed(Integer speed) {
        try {
            setSpeed(speed.floatValue());
        } catch (Exception e) {
            setSpeedError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return speedError;
    } // method setSpeed

    /**
     * Set the 'speed' class variable
     * @param  speed (Float)
     */
    public int setSpeed(Float speed) {
        try {
            setSpeed(speed.floatValue());
        } catch (Exception e) {
            setSpeedError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return speedError;
    } // method setSpeed

    /**
     * Set the 'speed' class variable
     * @param  speed (String)
     */
    public int setSpeed(String speed) {
        try {
            setSpeed(new Float(speed).floatValue());
        } catch (Exception e) {
            setSpeedError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return speedError;
    } // method setSpeed

    /**
     * Called when an exception has occured
     * @param  speed (String)
     */
    private void setSpeedError (float speed, Exception e, int error) {
        this.speed = speed;
        speedErrorMessage = e.toString();
        speedError = error;
    } // method setSpeedError


    /**
     * Set the 'direction' class variable
     * @param  direction (float)
     */
    public int setDirection(float direction) {
        try {
            if ( ((direction == FLOATNULL) ||
                  (direction == FLOATNULL2)) ||
                !((direction < DIRECTION_MN) ||
                  (direction > DIRECTION_MX)) ) {
                this.direction = direction;
                directionError = ERROR_NORMAL;
            } else {
                throw directionOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDirectionError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return directionError;
    } // method setDirection

    /**
     * Set the 'direction' class variable
     * @param  direction (Integer)
     */
    public int setDirection(Integer direction) {
        try {
            setDirection(direction.floatValue());
        } catch (Exception e) {
            setDirectionError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return directionError;
    } // method setDirection

    /**
     * Set the 'direction' class variable
     * @param  direction (Float)
     */
    public int setDirection(Float direction) {
        try {
            setDirection(direction.floatValue());
        } catch (Exception e) {
            setDirectionError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return directionError;
    } // method setDirection

    /**
     * Set the 'direction' class variable
     * @param  direction (String)
     */
    public int setDirection(String direction) {
        try {
            setDirection(new Float(direction).floatValue());
        } catch (Exception e) {
            setDirectionError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return directionError;
    } // method setDirection

    /**
     * Called when an exception has occured
     * @param  direction (String)
     */
    private void setDirectionError (float direction, Exception e, int error) {
        this.direction = direction;
        directionErrorMessage = e.toString();
        directionError = error;
    } // method setDirectionError


    /**
     * Set the 'temperature' class variable
     * @param  temperature (float)
     */
    public int setTemperature(float temperature) {
        try {
            if ( ((temperature == FLOATNULL) ||
                  (temperature == FLOATNULL2)) ||
                !((temperature < TEMPERATURE_MN) ||
                  (temperature > TEMPERATURE_MX)) ) {
                this.temperature = temperature;
                temperatureError = ERROR_NORMAL;
            } else {
                throw temperatureOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setTemperatureError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Set the 'temperature' class variable
     * @param  temperature (Integer)
     */
    public int setTemperature(Integer temperature) {
        try {
            setTemperature(temperature.floatValue());
        } catch (Exception e) {
            setTemperatureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Set the 'temperature' class variable
     * @param  temperature (Float)
     */
    public int setTemperature(Float temperature) {
        try {
            setTemperature(temperature.floatValue());
        } catch (Exception e) {
            setTemperatureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Set the 'temperature' class variable
     * @param  temperature (String)
     */
    public int setTemperature(String temperature) {
        try {
            setTemperature(new Float(temperature).floatValue());
        } catch (Exception e) {
            setTemperatureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return temperatureError;
    } // method setTemperature

    /**
     * Called when an exception has occured
     * @param  temperature (String)
     */
    private void setTemperatureError (float temperature, Exception e, int error) {
        this.temperature = temperature;
        temperatureErrorMessage = e.toString();
        temperatureError = error;
    } // method setTemperatureError


    /**
     * Set the 'vertVelocity' class variable
     * @param  vertVelocity (float)
     */
    public int setVertVelocity(float vertVelocity) {
        try {
            if ( ((vertVelocity == FLOATNULL) ||
                  (vertVelocity == FLOATNULL2)) ||
                !((vertVelocity < VERT_VELOCITY_MN) ||
                  (vertVelocity > VERT_VELOCITY_MX)) ) {
                this.vertVelocity = vertVelocity;
                vertVelocityError = ERROR_NORMAL;
            } else {
                throw vertVelocityOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setVertVelocityError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return vertVelocityError;
    } // method setVertVelocity

    /**
     * Set the 'vertVelocity' class variable
     * @param  vertVelocity (Integer)
     */
    public int setVertVelocity(Integer vertVelocity) {
        try {
            setVertVelocity(vertVelocity.floatValue());
        } catch (Exception e) {
            setVertVelocityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return vertVelocityError;
    } // method setVertVelocity

    /**
     * Set the 'vertVelocity' class variable
     * @param  vertVelocity (Float)
     */
    public int setVertVelocity(Float vertVelocity) {
        try {
            setVertVelocity(vertVelocity.floatValue());
        } catch (Exception e) {
            setVertVelocityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return vertVelocityError;
    } // method setVertVelocity

    /**
     * Set the 'vertVelocity' class variable
     * @param  vertVelocity (String)
     */
    public int setVertVelocity(String vertVelocity) {
        try {
            setVertVelocity(new Float(vertVelocity).floatValue());
        } catch (Exception e) {
            setVertVelocityError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return vertVelocityError;
    } // method setVertVelocity

    /**
     * Called when an exception has occured
     * @param  vertVelocity (String)
     */
    private void setVertVelocityError (float vertVelocity, Exception e, int error) {
        this.vertVelocity = vertVelocity;
        vertVelocityErrorMessage = e.toString();
        vertVelocityError = error;
    } // method setVertVelocityError


    /**
     * Set the 'fSpeed9' class variable
     * @param  fSpeed9 (float)
     */
    public int setFSpeed9(float fSpeed9) {
        try {
            if ( ((fSpeed9 == FLOATNULL) ||
                  (fSpeed9 == FLOATNULL2)) ||
                !((fSpeed9 < F_SPEED_9_MN) ||
                  (fSpeed9 > F_SPEED_9_MX)) ) {
                this.fSpeed9 = fSpeed9;
                fSpeed9Error = ERROR_NORMAL;
            } else {
                throw fSpeed9OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFSpeed9Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return fSpeed9Error;
    } // method setFSpeed9

    /**
     * Set the 'fSpeed9' class variable
     * @param  fSpeed9 (Integer)
     */
    public int setFSpeed9(Integer fSpeed9) {
        try {
            setFSpeed9(fSpeed9.floatValue());
        } catch (Exception e) {
            setFSpeed9Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fSpeed9Error;
    } // method setFSpeed9

    /**
     * Set the 'fSpeed9' class variable
     * @param  fSpeed9 (Float)
     */
    public int setFSpeed9(Float fSpeed9) {
        try {
            setFSpeed9(fSpeed9.floatValue());
        } catch (Exception e) {
            setFSpeed9Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fSpeed9Error;
    } // method setFSpeed9

    /**
     * Set the 'fSpeed9' class variable
     * @param  fSpeed9 (String)
     */
    public int setFSpeed9(String fSpeed9) {
        try {
            setFSpeed9(new Float(fSpeed9).floatValue());
        } catch (Exception e) {
            setFSpeed9Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fSpeed9Error;
    } // method setFSpeed9

    /**
     * Called when an exception has occured
     * @param  fSpeed9 (String)
     */
    private void setFSpeed9Error (float fSpeed9, Exception e, int error) {
        this.fSpeed9 = fSpeed9;
        fSpeed9ErrorMessage = e.toString();
        fSpeed9Error = error;
    } // method setFSpeed9Error


    /**
     * Set the 'fDirection9' class variable
     * @param  fDirection9 (float)
     */
    public int setFDirection9(float fDirection9) {
        try {
            if ( ((fDirection9 == FLOATNULL) ||
                  (fDirection9 == FLOATNULL2)) ||
                !((fDirection9 < F_DIRECTION_9_MN) ||
                  (fDirection9 > F_DIRECTION_9_MX)) ) {
                this.fDirection9 = fDirection9;
                fDirection9Error = ERROR_NORMAL;
            } else {
                throw fDirection9OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFDirection9Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return fDirection9Error;
    } // method setFDirection9

    /**
     * Set the 'fDirection9' class variable
     * @param  fDirection9 (Integer)
     */
    public int setFDirection9(Integer fDirection9) {
        try {
            setFDirection9(fDirection9.floatValue());
        } catch (Exception e) {
            setFDirection9Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fDirection9Error;
    } // method setFDirection9

    /**
     * Set the 'fDirection9' class variable
     * @param  fDirection9 (Float)
     */
    public int setFDirection9(Float fDirection9) {
        try {
            setFDirection9(fDirection9.floatValue());
        } catch (Exception e) {
            setFDirection9Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fDirection9Error;
    } // method setFDirection9

    /**
     * Set the 'fDirection9' class variable
     * @param  fDirection9 (String)
     */
    public int setFDirection9(String fDirection9) {
        try {
            setFDirection9(new Float(fDirection9).floatValue());
        } catch (Exception e) {
            setFDirection9Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fDirection9Error;
    } // method setFDirection9

    /**
     * Called when an exception has occured
     * @param  fDirection9 (String)
     */
    private void setFDirection9Error (float fDirection9, Exception e, int error) {
        this.fDirection9 = fDirection9;
        fDirection9ErrorMessage = e.toString();
        fDirection9Error = error;
    } // method setFDirection9Error


    /**
     * Set the 'fSpeed14' class variable
     * @param  fSpeed14 (float)
     */
    public int setFSpeed14(float fSpeed14) {
        try {
            if ( ((fSpeed14 == FLOATNULL) ||
                  (fSpeed14 == FLOATNULL2)) ||
                !((fSpeed14 < F_SPEED_14_MN) ||
                  (fSpeed14 > F_SPEED_14_MX)) ) {
                this.fSpeed14 = fSpeed14;
                fSpeed14Error = ERROR_NORMAL;
            } else {
                throw fSpeed14OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFSpeed14Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return fSpeed14Error;
    } // method setFSpeed14

    /**
     * Set the 'fSpeed14' class variable
     * @param  fSpeed14 (Integer)
     */
    public int setFSpeed14(Integer fSpeed14) {
        try {
            setFSpeed14(fSpeed14.floatValue());
        } catch (Exception e) {
            setFSpeed14Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fSpeed14Error;
    } // method setFSpeed14

    /**
     * Set the 'fSpeed14' class variable
     * @param  fSpeed14 (Float)
     */
    public int setFSpeed14(Float fSpeed14) {
        try {
            setFSpeed14(fSpeed14.floatValue());
        } catch (Exception e) {
            setFSpeed14Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fSpeed14Error;
    } // method setFSpeed14

    /**
     * Set the 'fSpeed14' class variable
     * @param  fSpeed14 (String)
     */
    public int setFSpeed14(String fSpeed14) {
        try {
            setFSpeed14(new Float(fSpeed14).floatValue());
        } catch (Exception e) {
            setFSpeed14Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fSpeed14Error;
    } // method setFSpeed14

    /**
     * Called when an exception has occured
     * @param  fSpeed14 (String)
     */
    private void setFSpeed14Error (float fSpeed14, Exception e, int error) {
        this.fSpeed14 = fSpeed14;
        fSpeed14ErrorMessage = e.toString();
        fSpeed14Error = error;
    } // method setFSpeed14Error


    /**
     * Set the 'fDirection14' class variable
     * @param  fDirection14 (float)
     */
    public int setFDirection14(float fDirection14) {
        try {
            if ( ((fDirection14 == FLOATNULL) ||
                  (fDirection14 == FLOATNULL2)) ||
                !((fDirection14 < F_DIRECTION_14_MN) ||
                  (fDirection14 > F_DIRECTION_14_MX)) ) {
                this.fDirection14 = fDirection14;
                fDirection14Error = ERROR_NORMAL;
            } else {
                throw fDirection14OutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFDirection14Error(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return fDirection14Error;
    } // method setFDirection14

    /**
     * Set the 'fDirection14' class variable
     * @param  fDirection14 (Integer)
     */
    public int setFDirection14(Integer fDirection14) {
        try {
            setFDirection14(fDirection14.floatValue());
        } catch (Exception e) {
            setFDirection14Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fDirection14Error;
    } // method setFDirection14

    /**
     * Set the 'fDirection14' class variable
     * @param  fDirection14 (Float)
     */
    public int setFDirection14(Float fDirection14) {
        try {
            setFDirection14(fDirection14.floatValue());
        } catch (Exception e) {
            setFDirection14Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fDirection14Error;
    } // method setFDirection14

    /**
     * Set the 'fDirection14' class variable
     * @param  fDirection14 (String)
     */
    public int setFDirection14(String fDirection14) {
        try {
            setFDirection14(new Float(fDirection14).floatValue());
        } catch (Exception e) {
            setFDirection14Error(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return fDirection14Error;
    } // method setFDirection14

    /**
     * Called when an exception has occured
     * @param  fDirection14 (String)
     */
    private void setFDirection14Error (float fDirection14, Exception e, int error) {
        this.fDirection14 = fDirection14;
        fDirection14ErrorMessage = e.toString();
        fDirection14Error = error;
    } // method setFDirection14Error


    /**
     * Set the 'pressure' class variable
     * @param  pressure (float)
     */
    public int setPressure(float pressure) {
        try {
            if ( ((pressure == FLOATNULL) ||
                  (pressure == FLOATNULL2)) ||
                !((pressure < PRESSURE_MN) ||
                  (pressure > PRESSURE_MX)) ) {
                this.pressure = pressure;
                pressureError = ERROR_NORMAL;
            } else {
                throw pressureOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setPressureError(FLOATNULL, e, ERROR_LOCAL);
        } // try
        return pressureError;
    } // method setPressure

    /**
     * Set the 'pressure' class variable
     * @param  pressure (Integer)
     */
    public int setPressure(Integer pressure) {
        try {
            setPressure(pressure.floatValue());
        } catch (Exception e) {
            setPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pressureError;
    } // method setPressure

    /**
     * Set the 'pressure' class variable
     * @param  pressure (Float)
     */
    public int setPressure(Float pressure) {
        try {
            setPressure(pressure.floatValue());
        } catch (Exception e) {
            setPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pressureError;
    } // method setPressure

    /**
     * Set the 'pressure' class variable
     * @param  pressure (String)
     */
    public int setPressure(String pressure) {
        try {
            setPressure(new Float(pressure).floatValue());
        } catch (Exception e) {
            setPressureError(FLOATNULL, e, ERROR_SYSTEM);
        } // try
        return pressureError;
    } // method setPressure

    /**
     * Called when an exception has occured
     * @param  pressure (String)
     */
    private void setPressureError (float pressure, Exception e, int error) {
        this.pressure = pressure;
        pressureErrorMessage = e.toString();
        pressureError = error;
    } // method setPressureError


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
     * Return the 'depthCode' class variable
     * @return depthCode (int)
     */
    public int getDepthCode() {
        return depthCode;
    } // method getDepthCode

    /**
     * Return the 'depthCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDepthCode methods
     * @return depthCode (String)
     */
    public String getDepthCode(String s) {
        return ((depthCode != INTNULL) ? new Integer(depthCode).toString() : "");
    } // method getDepthCode


    /**
     * Return the 'datetime' class variable
     * @return datetime (Timestamp)
     */
    public Timestamp getDatetime() {
        return datetime;
    } // method getDatetime

    /**
     * Return the 'datetime' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDatetime methods
     * @return datetime (String)
     */
    public String getDatetime(String s) {
        if (datetime.equals(DATENULL)) {
            return ("");
        } else {
            String datetimeStr = datetime.toString();
            return datetimeStr.substring(0,datetimeStr.indexOf('.'));
        } // if
    } // method getDatetime


    /**
     * Return the 'speed' class variable
     * @return speed (float)
     */
    public float getSpeed() {
        return speed;
    } // method getSpeed

    /**
     * Return the 'speed' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpeed methods
     * @return speed (String)
     */
    public String getSpeed(String s) {
        return ((speed != FLOATNULL) ? new Float(speed).toString() : "");
    } // method getSpeed


    /**
     * Return the 'direction' class variable
     * @return direction (float)
     */
    public float getDirection() {
        return direction;
    } // method getDirection

    /**
     * Return the 'direction' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDirection methods
     * @return direction (String)
     */
    public String getDirection(String s) {
        return ((direction != FLOATNULL) ? new Float(direction).toString() : "");
    } // method getDirection


    /**
     * Return the 'temperature' class variable
     * @return temperature (float)
     */
    public float getTemperature() {
        return temperature;
    } // method getTemperature

    /**
     * Return the 'temperature' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTemperature methods
     * @return temperature (String)
     */
    public String getTemperature(String s) {
        return ((temperature != FLOATNULL) ? new Float(temperature).toString() : "");
    } // method getTemperature


    /**
     * Return the 'vertVelocity' class variable
     * @return vertVelocity (float)
     */
    public float getVertVelocity() {
        return vertVelocity;
    } // method getVertVelocity

    /**
     * Return the 'vertVelocity' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getVertVelocity methods
     * @return vertVelocity (String)
     */
    public String getVertVelocity(String s) {
        return ((vertVelocity != FLOATNULL) ? new Float(vertVelocity).toString() : "");
    } // method getVertVelocity


    /**
     * Return the 'fSpeed9' class variable
     * @return fSpeed9 (float)
     */
    public float getFSpeed9() {
        return fSpeed9;
    } // method getFSpeed9

    /**
     * Return the 'fSpeed9' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFSpeed9 methods
     * @return fSpeed9 (String)
     */
    public String getFSpeed9(String s) {
        return ((fSpeed9 != FLOATNULL) ? new Float(fSpeed9).toString() : "");
    } // method getFSpeed9


    /**
     * Return the 'fDirection9' class variable
     * @return fDirection9 (float)
     */
    public float getFDirection9() {
        return fDirection9;
    } // method getFDirection9

    /**
     * Return the 'fDirection9' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFDirection9 methods
     * @return fDirection9 (String)
     */
    public String getFDirection9(String s) {
        return ((fDirection9 != FLOATNULL) ? new Float(fDirection9).toString() : "");
    } // method getFDirection9


    /**
     * Return the 'fSpeed14' class variable
     * @return fSpeed14 (float)
     */
    public float getFSpeed14() {
        return fSpeed14;
    } // method getFSpeed14

    /**
     * Return the 'fSpeed14' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFSpeed14 methods
     * @return fSpeed14 (String)
     */
    public String getFSpeed14(String s) {
        return ((fSpeed14 != FLOATNULL) ? new Float(fSpeed14).toString() : "");
    } // method getFSpeed14


    /**
     * Return the 'fDirection14' class variable
     * @return fDirection14 (float)
     */
    public float getFDirection14() {
        return fDirection14;
    } // method getFDirection14

    /**
     * Return the 'fDirection14' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFDirection14 methods
     * @return fDirection14 (String)
     */
    public String getFDirection14(String s) {
        return ((fDirection14 != FLOATNULL) ? new Float(fDirection14).toString() : "");
    } // method getFDirection14


    /**
     * Return the 'pressure' class variable
     * @return pressure (float)
     */
    public float getPressure() {
        return pressure;
    } // method getPressure

    /**
     * Return the 'pressure' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPressure methods
     * @return pressure (String)
     */
    public String getPressure(String s) {
        return ((pressure != FLOATNULL) ? new Float(pressure).toString() : "");
    } // method getPressure


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
            (depthCode == INTNULL) &&
            (datetime.equals(DATENULL)) &&
            (speed == FLOATNULL) &&
            (direction == FLOATNULL) &&
            (temperature == FLOATNULL) &&
            (vertVelocity == FLOATNULL) &&
            (fSpeed9 == FLOATNULL) &&
            (fDirection9 == FLOATNULL) &&
            (fSpeed14 == FLOATNULL) &&
            (fDirection14 == FLOATNULL) &&
            (pressure == FLOATNULL)) {
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
            depthCodeError +
            datetimeError +
            speedError +
            directionError +
            temperatureError +
            vertVelocityError +
            fSpeed9Error +
            fDirection9Error +
            fSpeed14Error +
            fDirection14Error +
            pressureError;
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
     * Gets the errorcode for the depthCode instance variable
     * @return errorcode (int)
     */
    public int getDepthCodeError() {
        return depthCodeError;
    } // method getDepthCodeError

    /**
     * Gets the errorMessage for the depthCode instance variable
     * @return errorMessage (String)
     */
    public String getDepthCodeErrorMessage() {
        return depthCodeErrorMessage;
    } // method getDepthCodeErrorMessage


    /**
     * Gets the errorcode for the datetime instance variable
     * @return errorcode (int)
     */
    public int getDatetimeError() {
        return datetimeError;
    } // method getDatetimeError

    /**
     * Gets the errorMessage for the datetime instance variable
     * @return errorMessage (String)
     */
    public String getDatetimeErrorMessage() {
        return datetimeErrorMessage;
    } // method getDatetimeErrorMessage


    /**
     * Gets the errorcode for the speed instance variable
     * @return errorcode (int)
     */
    public int getSpeedError() {
        return speedError;
    } // method getSpeedError

    /**
     * Gets the errorMessage for the speed instance variable
     * @return errorMessage (String)
     */
    public String getSpeedErrorMessage() {
        return speedErrorMessage;
    } // method getSpeedErrorMessage


    /**
     * Gets the errorcode for the direction instance variable
     * @return errorcode (int)
     */
    public int getDirectionError() {
        return directionError;
    } // method getDirectionError

    /**
     * Gets the errorMessage for the direction instance variable
     * @return errorMessage (String)
     */
    public String getDirectionErrorMessage() {
        return directionErrorMessage;
    } // method getDirectionErrorMessage


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
     * Gets the errorcode for the vertVelocity instance variable
     * @return errorcode (int)
     */
    public int getVertVelocityError() {
        return vertVelocityError;
    } // method getVertVelocityError

    /**
     * Gets the errorMessage for the vertVelocity instance variable
     * @return errorMessage (String)
     */
    public String getVertVelocityErrorMessage() {
        return vertVelocityErrorMessage;
    } // method getVertVelocityErrorMessage


    /**
     * Gets the errorcode for the fSpeed9 instance variable
     * @return errorcode (int)
     */
    public int getFSpeed9Error() {
        return fSpeed9Error;
    } // method getFSpeed9Error

    /**
     * Gets the errorMessage for the fSpeed9 instance variable
     * @return errorMessage (String)
     */
    public String getFSpeed9ErrorMessage() {
        return fSpeed9ErrorMessage;
    } // method getFSpeed9ErrorMessage


    /**
     * Gets the errorcode for the fDirection9 instance variable
     * @return errorcode (int)
     */
    public int getFDirection9Error() {
        return fDirection9Error;
    } // method getFDirection9Error

    /**
     * Gets the errorMessage for the fDirection9 instance variable
     * @return errorMessage (String)
     */
    public String getFDirection9ErrorMessage() {
        return fDirection9ErrorMessage;
    } // method getFDirection9ErrorMessage


    /**
     * Gets the errorcode for the fSpeed14 instance variable
     * @return errorcode (int)
     */
    public int getFSpeed14Error() {
        return fSpeed14Error;
    } // method getFSpeed14Error

    /**
     * Gets the errorMessage for the fSpeed14 instance variable
     * @return errorMessage (String)
     */
    public String getFSpeed14ErrorMessage() {
        return fSpeed14ErrorMessage;
    } // method getFSpeed14ErrorMessage


    /**
     * Gets the errorcode for the fDirection14 instance variable
     * @return errorcode (int)
     */
    public int getFDirection14Error() {
        return fDirection14Error;
    } // method getFDirection14Error

    /**
     * Gets the errorMessage for the fDirection14 instance variable
     * @return errorMessage (String)
     */
    public String getFDirection14ErrorMessage() {
        return fDirection14ErrorMessage;
    } // method getFDirection14ErrorMessage


    /**
     * Gets the errorcode for the pressure instance variable
     * @return errorcode (int)
     */
    public int getPressureError() {
        return pressureError;
    } // method getPressureError

    /**
     * Gets the errorMessage for the pressure instance variable
     * @return errorMessage (String)
     */
    public String getPressureErrorMessage() {
        return pressureErrorMessage;
    } // method getPressureErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of CurData. e.g.<pre>
     * CurData curData = new CurData(<val1>);
     * CurData curDataArray[] = curData.get();</pre>
     * will get the CurData record where code = <val1>.
     * @return Array of CurData (CurData[])
     */
    public CurData[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * CurData curDataArray[] =
     *     new CurData().get(CurData.CODE+"=<val1>");</pre>
     * will get the CurData record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of CurData (CurData[])
     */
    public CurData[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * CurData curDataArray[] =
     *     new CurData().get("1=1",curData.CODE);</pre>
     * will get the all the CurData records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurData (CurData[])
     */
    public CurData[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = CurData.CODE,CurData.DEPTH_CODE;
     * String where = CurData.CODE + "=<val1";
     * String order = CurData.CODE;
     * CurData curDataArray[] =
     *     new CurData().get(columns, where, order);</pre>
     * will get the code and depthCode colums of all CurData records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of CurData (CurData[])
     */
    public CurData[] get (String fields, String where, String order) {
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
     * and transforms it into an array of CurData.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private CurData[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol         = db.getColNumber(CODE);
        int depthCodeCol    = db.getColNumber(DEPTH_CODE);
        int datetimeCol     = db.getColNumber(DATETIME);
        int speedCol        = db.getColNumber(SPEED);
        int directionCol    = db.getColNumber(DIRECTION);
        int temperatureCol  = db.getColNumber(TEMPERATURE);
        int vertVelocityCol = db.getColNumber(VERT_VELOCITY);
        int fSpeed9Col      = db.getColNumber(F_SPEED_9);
        int fDirection9Col  = db.getColNumber(F_DIRECTION_9);
        int fSpeed14Col     = db.getColNumber(F_SPEED_14);
        int fDirection14Col = db.getColNumber(F_DIRECTION_14);
        int pressureCol     = db.getColNumber(PRESSURE);
        CurData[] cArray = new CurData[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new CurData();
            if (codeCol != -1)
                cArray[i].setCode        ((String) row.elementAt(codeCol));
            if (depthCodeCol != -1)
                cArray[i].setDepthCode   ((String) row.elementAt(depthCodeCol));
            if (datetimeCol != -1)
                cArray[i].setDatetime    ((String) row.elementAt(datetimeCol));
            if (speedCol != -1)
                cArray[i].setSpeed       ((String) row.elementAt(speedCol));
            if (directionCol != -1)
                cArray[i].setDirection   ((String) row.elementAt(directionCol));
            if (temperatureCol != -1)
                cArray[i].setTemperature ((String) row.elementAt(temperatureCol));
            if (vertVelocityCol != -1)
                cArray[i].setVertVelocity((String) row.elementAt(vertVelocityCol));
            if (fSpeed9Col != -1)
                cArray[i].setFSpeed9     ((String) row.elementAt(fSpeed9Col));
            if (fDirection9Col != -1)
                cArray[i].setFDirection9 ((String) row.elementAt(fDirection9Col));
            if (fSpeed14Col != -1)
                cArray[i].setFSpeed14    ((String) row.elementAt(fSpeed14Col));
            if (fDirection14Col != -1)
                cArray[i].setFDirection14((String) row.elementAt(fDirection14Col));
            if (pressureCol != -1)
                cArray[i].setPressure    ((String) row.elementAt(pressureCol));
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
     *     new CurData(
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
     *         <val12>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     depthCode    = <val2>,
     *     datetime     = <val3>,
     *     speed        = <val4>,
     *     direction    = <val5>,
     *     temperature  = <val6>,
     *     vertVelocity = <val7>,
     *     fSpeed9      = <val8>,
     *     fDirection9  = <val9>,
     *     fSpeed14     = <val10>,
     *     fDirection14 = <val11>,
     *     pressure     = <val12>.
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
     * Boolean success = new CurData(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.DATENULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL).del();</pre>
     * will delete all records where depthCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurData curData = new CurData();
     * success = curData.del(CurData.CODE+"=<val1>");</pre>
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
     * update are taken from the CurData argument, .e.g.<pre>
     * Boolean success
     * CurData updCurData = new CurData();
     * updCurData.setDepthCode(<val2>);
     * CurData whereCurData = new CurData(<val1>);
     * success = whereCurData.upd(updCurData);</pre>
     * will update DepthCode to <val2> for all records where
     * code = <val1>.
     * @param  curData  A CurData variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(CurData curData) {
        return db.update (TABLE, createColVals(curData), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * CurData updCurData = new CurData();
     * updCurData.setDepthCode(<val2>);
     * CurData whereCurData = new CurData();
     * success = whereCurData.upd(
     *     updCurData, CurData.CODE+"=<val1>");</pre>
     * will update DepthCode to <val2> for all records where
     * code = <val1>.
     * @param  curData  A CurData variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(CurData curData, String where) {
        return db.update (TABLE, createColVals(curData), where);
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
        if (getDepthCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEPTH_CODE + "=" + getDepthCode("");
        } // if getDepthCode
        if (!getDatetime().equals(DATENULL)) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATETIME +
                "=" + Tables.getDateFormat(getDatetime());
        } // if getDatetime
        if (getSpeed() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPEED + "=" + getSpeed("");
        } // if getSpeed
        if (getDirection() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DIRECTION + "=" + getDirection("");
        } // if getDirection
        if (getTemperature() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TEMPERATURE + "=" + getTemperature("");
        } // if getTemperature
        if (getVertVelocity() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + VERT_VELOCITY + "=" + getVertVelocity("");
        } // if getVertVelocity
        if (getFSpeed9() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + F_SPEED_9 + "=" + getFSpeed9("");
        } // if getFSpeed9
        if (getFDirection9() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + F_DIRECTION_9 + "=" + getFDirection9("");
        } // if getFDirection9
        if (getFSpeed14() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + F_SPEED_14 + "=" + getFSpeed14("");
        } // if getFSpeed14
        if (getFDirection14() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + F_DIRECTION_14 + "=" + getFDirection14("");
        } // if getFDirection14
        if (getPressure() != FLOATNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PRESSURE + "=" + getPressure("");
        } // if getPressure
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(CurData aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getDepthCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEPTH_CODE +"=";
            colVals += (aVar.getDepthCode() == INTNULL2 ?
                "null" : aVar.getDepthCode(""));
        } // if aVar.getDepthCode
        if (!aVar.getDatetime().equals(DATENULL)) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATETIME +"=";
            colVals += (aVar.getDatetime().equals(DATENULL2) ?
                "null" : Tables.getDateFormat(aVar.getDatetime()));
        } // if aVar.getDatetime
        if (aVar.getSpeed() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPEED +"=";
            colVals += (aVar.getSpeed() == FLOATNULL2 ?
                "null" : aVar.getSpeed(""));
        } // if aVar.getSpeed
        if (aVar.getDirection() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DIRECTION +"=";
            colVals += (aVar.getDirection() == FLOATNULL2 ?
                "null" : aVar.getDirection(""));
        } // if aVar.getDirection
        if (aVar.getTemperature() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TEMPERATURE +"=";
            colVals += (aVar.getTemperature() == FLOATNULL2 ?
                "null" : aVar.getTemperature(""));
        } // if aVar.getTemperature
        if (aVar.getVertVelocity() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += VERT_VELOCITY +"=";
            colVals += (aVar.getVertVelocity() == FLOATNULL2 ?
                "null" : aVar.getVertVelocity(""));
        } // if aVar.getVertVelocity
        if (aVar.getFSpeed9() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += F_SPEED_9 +"=";
            colVals += (aVar.getFSpeed9() == FLOATNULL2 ?
                "null" : aVar.getFSpeed9(""));
        } // if aVar.getFSpeed9
        if (aVar.getFDirection9() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += F_DIRECTION_9 +"=";
            colVals += (aVar.getFDirection9() == FLOATNULL2 ?
                "null" : aVar.getFDirection9(""));
        } // if aVar.getFDirection9
        if (aVar.getFSpeed14() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += F_SPEED_14 +"=";
            colVals += (aVar.getFSpeed14() == FLOATNULL2 ?
                "null" : aVar.getFSpeed14(""));
        } // if aVar.getFSpeed14
        if (aVar.getFDirection14() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += F_DIRECTION_14 +"=";
            colVals += (aVar.getFDirection14() == FLOATNULL2 ?
                "null" : aVar.getFDirection14(""));
        } // if aVar.getFDirection14
        if (aVar.getPressure() != FLOATNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PRESSURE +"=";
            colVals += (aVar.getPressure() == FLOATNULL2 ?
                "null" : aVar.getPressure(""));
        } // if aVar.getPressure
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getDepthCode() != INTNULL) {
            columns = columns + "," + DEPTH_CODE;
        } // if getDepthCode
        if (!getDatetime().equals(DATENULL)) {
            columns = columns + "," + DATETIME;
        } // if getDatetime
        if (getSpeed() != FLOATNULL) {
            columns = columns + "," + SPEED;
        } // if getSpeed
        if (getDirection() != FLOATNULL) {
            columns = columns + "," + DIRECTION;
        } // if getDirection
        if (getTemperature() != FLOATNULL) {
            columns = columns + "," + TEMPERATURE;
        } // if getTemperature
        if (getVertVelocity() != FLOATNULL) {
            columns = columns + "," + VERT_VELOCITY;
        } // if getVertVelocity
        if (getFSpeed9() != FLOATNULL) {
            columns = columns + "," + F_SPEED_9;
        } // if getFSpeed9
        if (getFDirection9() != FLOATNULL) {
            columns = columns + "," + F_DIRECTION_9;
        } // if getFDirection9
        if (getFSpeed14() != FLOATNULL) {
            columns = columns + "," + F_SPEED_14;
        } // if getFSpeed14
        if (getFDirection14() != FLOATNULL) {
            columns = columns + "," + F_DIRECTION_14;
        } // if getFDirection14
        if (getPressure() != FLOATNULL) {
            columns = columns + "," + PRESSURE;
        } // if getPressure
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
        if (getDepthCode() != INTNULL) {
            values  = values  + "," + getDepthCode("");
        } // if getDepthCode
        if (!getDatetime().equals(DATENULL)) {
            values  = values  + "," + Tables.getDateFormat(getDatetime());
        } // if getDatetime
        if (getSpeed() != FLOATNULL) {
            values  = values  + "," + getSpeed("");
        } // if getSpeed
        if (getDirection() != FLOATNULL) {
            values  = values  + "," + getDirection("");
        } // if getDirection
        if (getTemperature() != FLOATNULL) {
            values  = values  + "," + getTemperature("");
        } // if getTemperature
        if (getVertVelocity() != FLOATNULL) {
            values  = values  + "," + getVertVelocity("");
        } // if getVertVelocity
        if (getFSpeed9() != FLOATNULL) {
            values  = values  + "," + getFSpeed9("");
        } // if getFSpeed9
        if (getFDirection9() != FLOATNULL) {
            values  = values  + "," + getFDirection9("");
        } // if getFDirection9
        if (getFSpeed14() != FLOATNULL) {
            values  = values  + "," + getFSpeed14("");
        } // if getFSpeed14
        if (getFDirection14() != FLOATNULL) {
            values  = values  + "," + getFDirection14("");
        } // if getFDirection14
        if (getPressure() != FLOATNULL) {
            values  = values  + "," + getPressure("");
        } // if getPressure
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
            getDepthCode("")    + "|" +
            getDatetime("")     + "|" +
            getSpeed("")        + "|" +
            getDirection("")    + "|" +
            getTemperature("")  + "|" +
            getVertVelocity("") + "|" +
            getFSpeed9("")      + "|" +
            getFDirection9("")  + "|" +
            getFSpeed14("")     + "|" +
            getFDirection14("") + "|" +
            getPressure("")     + "|";
    } // method toString

} // class CurData
