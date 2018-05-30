package menusp;

import java.sql.*;
import java.util.*;

/**
 * This class manages the DMN_MENU_ITEMS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MnuMenuItems extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "DMN_MENU_ITEMS";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The sequenceNumber field name */
    public static final String SEQUENCE_NUMBER = "SEQUENCE_NUMBER";
    /** The childOf field name */
    public static final String CHILD_OF = "CHILD_OF";
    /** The text field name */
    public static final String TEXT = "TEXT";
    /** The url field name */
    public static final String URL = "URL";
    /** The menuLevel field name */
    public static final String MENU_LEVEL = "MENU_LEVEL";
    /** The scriptTypeCode field name */
    public static final String SCRIPT_TYPE_CODE = "SCRIPT_TYPE_CODE";
    /** The owner field name */
    public static final String OWNER = "OWNER";
    /** The application field name */
    public static final String APPLICATION = "APPLICATION";
    /** The userType field name */
    public static final String USER_TYPE = "USER_TYPE";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private int       sequenceNumber;
    private int       childOf;
    private String    text;
    private String    url;
    private int       menuLevel;
    private int       scriptTypeCode;
    private String    owner;
    private String    application;
    private int       userType;

    /** The error variables  */
    private int codeError           = ERROR_NORMAL;
    private int sequenceNumberError = ERROR_NORMAL;
    private int childOfError        = ERROR_NORMAL;
    private int textError           = ERROR_NORMAL;
    private int urlError            = ERROR_NORMAL;
    private int menuLevelError      = ERROR_NORMAL;
    private int scriptTypeCodeError = ERROR_NORMAL;
    private int ownerError          = ERROR_NORMAL;
    private int applicationError    = ERROR_NORMAL;
    private int userTypeError       = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage           = "";
    private String sequenceNumberErrorMessage = "";
    private String childOfErrorMessage        = "";
    private String textErrorMessage           = "";
    private String urlErrorMessage            = "";
    private String menuLevelErrorMessage      = "";
    private String scriptTypeCodeErrorMessage = "";
    private String ownerErrorMessage          = "";
    private String applicationErrorMessage    = "";
    private String userTypeErrorMessage       = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       SEQUENCE_NUMBER_MN = INTMIN;
    public static final int       SEQUENCE_NUMBER_MX = INTMAX;
    public static final int       CHILD_OF_MN = INTMIN;
    public static final int       CHILD_OF_MX = INTMAX;
    public static final int       MENU_LEVEL_MN = INTMIN;
    public static final int       MENU_LEVEL_MX = INTMAX;
    public static final int       SCRIPT_TYPE_CODE_MN = INTMIN;
    public static final int       SCRIPT_TYPE_CODE_MX = INTMAX;
    public static final int       USER_TYPE_MN = INTMIN;
    public static final int       USER_TYPE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception sequenceNumberOutOfBoundsException =
        new Exception ("'sequenceNumber' out of bounds: " +
            SEQUENCE_NUMBER_MN + " - " + SEQUENCE_NUMBER_MX);
    Exception childOfOutOfBoundsException =
        new Exception ("'childOf' out of bounds: " +
            CHILD_OF_MN + " - " + CHILD_OF_MX);
    Exception menuLevelOutOfBoundsException =
        new Exception ("'menuLevel' out of bounds: " +
            MENU_LEVEL_MN + " - " + MENU_LEVEL_MX);
    Exception scriptTypeCodeOutOfBoundsException =
        new Exception ("'scriptTypeCode' out of bounds: " +
            SCRIPT_TYPE_CODE_MN + " - " + SCRIPT_TYPE_CODE_MX);
    Exception userTypeOutOfBoundsException =
        new Exception ("'userType' out of bounds: " +
            USER_TYPE_MN + " - " + USER_TYPE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MnuMenuItems() {
        clearVars();
        if (dbg) System.out.println ("<br>in MnuMenuItems constructor 1"); // debug
    } // MnuMenuItems constructor

    /**
     * Instantiate a MnuMenuItems object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MnuMenuItems(
            int code) {
        this();
        setCode           (code          );
        if (dbg) System.out.println ("<br>in MnuMenuItems constructor 2"); // debug
    } // MnuMenuItems constructor

    /**
     * Instantiate a MnuMenuItems object and initialize the instance variables.
     * @param code            The code           (int)
     * @param sequenceNumber  The sequenceNumber (int)
     * @param childOf         The childOf        (int)
     * @param text            The text           (String)
     * @param url             The url            (String)
     * @param menuLevel       The menuLevel      (int)
     * @param scriptTypeCode  The scriptTypeCode (int)
     * @param owner           The owner          (String)
     * @param application     The application    (String)
     * @param userType        The userType       (int)
     */
    public MnuMenuItems(
            int    code,
            int    sequenceNumber,
            int    childOf,
            String text,
            String url,
            int    menuLevel,
            int    scriptTypeCode,
            String owner,
            String application,
            int    userType) {
        this();
        setCode           (code          );
        setSequenceNumber (sequenceNumber);
        setChildOf        (childOf       );
        setText           (text          );
        setUrl            (url           );
        setMenuLevel      (menuLevel     );
        setScriptTypeCode (scriptTypeCode);
        setOwner          (owner         );
        setApplication    (application   );
        setUserType       (userType      );
        if (dbg) System.out.println ("<br>in MnuMenuItems constructor 3"); // debug
    } // MnuMenuItems constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode           (INTNULL );
        setSequenceNumber (INTNULL );
        setChildOf        (INTNULL );
        setText           (CHARNULL);
        setUrl            (CHARNULL);
        setMenuLevel      (INTNULL );
        setScriptTypeCode (INTNULL );
        setOwner          (CHARNULL);
        setApplication    (CHARNULL);
        setUserType       (INTNULL );
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
     * Set the 'sequenceNumber' class variable
     * @param  sequenceNumber (int)
     */
    public int setSequenceNumber(int sequenceNumber) {
        try {
            if ( ((sequenceNumber == INTNULL) ||
                  (sequenceNumber == INTNULL2)) ||
                !((sequenceNumber < SEQUENCE_NUMBER_MN) ||
                  (sequenceNumber > SEQUENCE_NUMBER_MX)) ) {
                this.sequenceNumber = sequenceNumber;
                sequenceNumberError = ERROR_NORMAL;
            } else {
                throw sequenceNumberOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setSequenceNumberError(INTNULL, e, ERROR_LOCAL);
        } // try
        return sequenceNumberError;
    } // method setSequenceNumber

    /**
     * Set the 'sequenceNumber' class variable
     * @param  sequenceNumber (Integer)
     */
    public int setSequenceNumber(Integer sequenceNumber) {
        try {
            setSequenceNumber(sequenceNumber.intValue());
        } catch (Exception e) {
            setSequenceNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sequenceNumberError;
    } // method setSequenceNumber

    /**
     * Set the 'sequenceNumber' class variable
     * @param  sequenceNumber (Float)
     */
    public int setSequenceNumber(Float sequenceNumber) {
        try {
            if (sequenceNumber.floatValue() == FLOATNULL) {
                setSequenceNumber(INTNULL);
            } else {
                setSequenceNumber(sequenceNumber.intValue());
            } // if (sequenceNumber.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setSequenceNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sequenceNumberError;
    } // method setSequenceNumber

    /**
     * Set the 'sequenceNumber' class variable
     * @param  sequenceNumber (String)
     */
    public int setSequenceNumber(String sequenceNumber) {
        try {
            setSequenceNumber(new Integer(sequenceNumber).intValue());
        } catch (Exception e) {
            setSequenceNumberError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return sequenceNumberError;
    } // method setSequenceNumber

    /**
     * Called when an exception has occured
     * @param  sequenceNumber (String)
     */
    private void setSequenceNumberError (int sequenceNumber, Exception e, int error) {
        this.sequenceNumber = sequenceNumber;
        sequenceNumberErrorMessage = e.toString();
        sequenceNumberError = error;
    } // method setSequenceNumberError


    /**
     * Set the 'childOf' class variable
     * @param  childOf (int)
     */
    public int setChildOf(int childOf) {
        try {
            if ( ((childOf == INTNULL) ||
                  (childOf == INTNULL2)) ||
                !((childOf < CHILD_OF_MN) ||
                  (childOf > CHILD_OF_MX)) ) {
                this.childOf = childOf;
                childOfError = ERROR_NORMAL;
            } else {
                throw childOfOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setChildOfError(INTNULL, e, ERROR_LOCAL);
        } // try
        return childOfError;
    } // method setChildOf

    /**
     * Set the 'childOf' class variable
     * @param  childOf (Integer)
     */
    public int setChildOf(Integer childOf) {
        try {
            setChildOf(childOf.intValue());
        } catch (Exception e) {
            setChildOfError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return childOfError;
    } // method setChildOf

    /**
     * Set the 'childOf' class variable
     * @param  childOf (Float)
     */
    public int setChildOf(Float childOf) {
        try {
            if (childOf.floatValue() == FLOATNULL) {
                setChildOf(INTNULL);
            } else {
                setChildOf(childOf.intValue());
            } // if (childOf.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setChildOfError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return childOfError;
    } // method setChildOf

    /**
     * Set the 'childOf' class variable
     * @param  childOf (String)
     */
    public int setChildOf(String childOf) {
        try {
            setChildOf(new Integer(childOf).intValue());
        } catch (Exception e) {
            setChildOfError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return childOfError;
    } // method setChildOf

    /**
     * Called when an exception has occured
     * @param  childOf (String)
     */
    private void setChildOfError (int childOf, Exception e, int error) {
        this.childOf = childOf;
        childOfErrorMessage = e.toString();
        childOfError = error;
    } // method setChildOfError


    /**
     * Set the 'text' class variable
     * @param  text (String)
     */
    public int setText(String text) {
        try {
            this.text = text;
            if (this.text != CHARNULL) {
                this.text = stripCRLF(this.text.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>text = " + this.text);
        } catch (Exception e) {
            setTextError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return textError;
    } // method setText

    /**
     * Called when an exception has occured
     * @param  text (String)
     */
    private void setTextError (String text, Exception e, int error) {
        this.text = text;
        textErrorMessage = e.toString();
        textError = error;
    } // method setTextError


    /**
     * Set the 'url' class variable
     * @param  url (String)
     */
    public int setUrl(String url) {
        try {
            this.url = url;
            if (this.url != CHARNULL) {
                this.url = stripCRLF(this.url.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>url = " + this.url);
        } catch (Exception e) {
            setUrlError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return urlError;
    } // method setUrl

    /**
     * Called when an exception has occured
     * @param  url (String)
     */
    private void setUrlError (String url, Exception e, int error) {
        this.url = url;
        urlErrorMessage = e.toString();
        urlError = error;
    } // method setUrlError


    /**
     * Set the 'menuLevel' class variable
     * @param  menuLevel (int)
     */
    public int setMenuLevel(int menuLevel) {
        try {
            if ( ((menuLevel == INTNULL) ||
                  (menuLevel == INTNULL2)) ||
                !((menuLevel < MENU_LEVEL_MN) ||
                  (menuLevel > MENU_LEVEL_MX)) ) {
                this.menuLevel = menuLevel;
                menuLevelError = ERROR_NORMAL;
            } else {
                throw menuLevelOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMenuLevelError(INTNULL, e, ERROR_LOCAL);
        } // try
        return menuLevelError;
    } // method setMenuLevel

    /**
     * Set the 'menuLevel' class variable
     * @param  menuLevel (Integer)
     */
    public int setMenuLevel(Integer menuLevel) {
        try {
            setMenuLevel(menuLevel.intValue());
        } catch (Exception e) {
            setMenuLevelError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return menuLevelError;
    } // method setMenuLevel

    /**
     * Set the 'menuLevel' class variable
     * @param  menuLevel (Float)
     */
    public int setMenuLevel(Float menuLevel) {
        try {
            if (menuLevel.floatValue() == FLOATNULL) {
                setMenuLevel(INTNULL);
            } else {
                setMenuLevel(menuLevel.intValue());
            } // if (menuLevel.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMenuLevelError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return menuLevelError;
    } // method setMenuLevel

    /**
     * Set the 'menuLevel' class variable
     * @param  menuLevel (String)
     */
    public int setMenuLevel(String menuLevel) {
        try {
            setMenuLevel(new Integer(menuLevel).intValue());
        } catch (Exception e) {
            setMenuLevelError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return menuLevelError;
    } // method setMenuLevel

    /**
     * Called when an exception has occured
     * @param  menuLevel (String)
     */
    private void setMenuLevelError (int menuLevel, Exception e, int error) {
        this.menuLevel = menuLevel;
        menuLevelErrorMessage = e.toString();
        menuLevelError = error;
    } // method setMenuLevelError


    /**
     * Set the 'scriptTypeCode' class variable
     * @param  scriptTypeCode (int)
     */
    public int setScriptTypeCode(int scriptTypeCode) {
        try {
            if ( ((scriptTypeCode == INTNULL) ||
                  (scriptTypeCode == INTNULL2)) ||
                !((scriptTypeCode < SCRIPT_TYPE_CODE_MN) ||
                  (scriptTypeCode > SCRIPT_TYPE_CODE_MX)) ) {
                this.scriptTypeCode = scriptTypeCode;
                scriptTypeCodeError = ERROR_NORMAL;
            } else {
                throw scriptTypeCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setScriptTypeCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return scriptTypeCodeError;
    } // method setScriptTypeCode

    /**
     * Set the 'scriptTypeCode' class variable
     * @param  scriptTypeCode (Integer)
     */
    public int setScriptTypeCode(Integer scriptTypeCode) {
        try {
            setScriptTypeCode(scriptTypeCode.intValue());
        } catch (Exception e) {
            setScriptTypeCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return scriptTypeCodeError;
    } // method setScriptTypeCode

    /**
     * Set the 'scriptTypeCode' class variable
     * @param  scriptTypeCode (Float)
     */
    public int setScriptTypeCode(Float scriptTypeCode) {
        try {
            if (scriptTypeCode.floatValue() == FLOATNULL) {
                setScriptTypeCode(INTNULL);
            } else {
                setScriptTypeCode(scriptTypeCode.intValue());
            } // if (scriptTypeCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setScriptTypeCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return scriptTypeCodeError;
    } // method setScriptTypeCode

    /**
     * Set the 'scriptTypeCode' class variable
     * @param  scriptTypeCode (String)
     */
    public int setScriptTypeCode(String scriptTypeCode) {
        try {
            setScriptTypeCode(new Integer(scriptTypeCode).intValue());
        } catch (Exception e) {
            setScriptTypeCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return scriptTypeCodeError;
    } // method setScriptTypeCode

    /**
     * Called when an exception has occured
     * @param  scriptTypeCode (String)
     */
    private void setScriptTypeCodeError (int scriptTypeCode, Exception e, int error) {
        this.scriptTypeCode = scriptTypeCode;
        scriptTypeCodeErrorMessage = e.toString();
        scriptTypeCodeError = error;
    } // method setScriptTypeCodeError


    /**
     * Set the 'owner' class variable
     * @param  owner (String)
     */
    public int setOwner(String owner) {
        try {
            this.owner = owner;
            if (this.owner != CHARNULL) {
                this.owner = stripCRLF(this.owner.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>owner = " + this.owner);
        } catch (Exception e) {
            setOwnerError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return ownerError;
    } // method setOwner

    /**
     * Called when an exception has occured
     * @param  owner (String)
     */
    private void setOwnerError (String owner, Exception e, int error) {
        this.owner = owner;
        ownerErrorMessage = e.toString();
        ownerError = error;
    } // method setOwnerError


    /**
     * Set the 'application' class variable
     * @param  application (String)
     */
    public int setApplication(String application) {
        try {
            this.application = application;
            if (this.application != CHARNULL) {
                this.application = stripCRLF(this.application.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>application = " + this.application);
        } catch (Exception e) {
            setApplicationError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return applicationError;
    } // method setApplication

    /**
     * Called when an exception has occured
     * @param  application (String)
     */
    private void setApplicationError (String application, Exception e, int error) {
        this.application = application;
        applicationErrorMessage = e.toString();
        applicationError = error;
    } // method setApplicationError


    /**
     * Set the 'userType' class variable
     * @param  userType (int)
     */
    public int setUserType(int userType) {
        try {
            if ( ((userType == INTNULL) ||
                  (userType == INTNULL2)) ||
                !((userType < USER_TYPE_MN) ||
                  (userType > USER_TYPE_MX)) ) {
                this.userType = userType;
                userTypeError = ERROR_NORMAL;
            } else {
                throw userTypeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setUserTypeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return userTypeError;
    } // method setUserType

    /**
     * Set the 'userType' class variable
     * @param  userType (Integer)
     */
    public int setUserType(Integer userType) {
        try {
            setUserType(userType.intValue());
        } catch (Exception e) {
            setUserTypeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return userTypeError;
    } // method setUserType

    /**
     * Set the 'userType' class variable
     * @param  userType (Float)
     */
    public int setUserType(Float userType) {
        try {
            if (userType.floatValue() == FLOATNULL) {
                setUserType(INTNULL);
            } else {
                setUserType(userType.intValue());
            } // if (userType.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setUserTypeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return userTypeError;
    } // method setUserType

    /**
     * Set the 'userType' class variable
     * @param  userType (String)
     */
    public int setUserType(String userType) {
        try {
            setUserType(new Integer(userType).intValue());
        } catch (Exception e) {
            setUserTypeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return userTypeError;
    } // method setUserType

    /**
     * Called when an exception has occured
     * @param  userType (String)
     */
    private void setUserTypeError (int userType, Exception e, int error) {
        this.userType = userType;
        userTypeErrorMessage = e.toString();
        userTypeError = error;
    } // method setUserTypeError


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
     * Return the 'sequenceNumber' class variable
     * @return sequenceNumber (int)
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    } // method getSequenceNumber

    /**
     * Return the 'sequenceNumber' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSequenceNumber methods
     * @return sequenceNumber (String)
     */
    public String getSequenceNumber(String s) {
        return ((sequenceNumber != INTNULL) ? new Integer(sequenceNumber).toString() : "");
    } // method getSequenceNumber


    /**
     * Return the 'childOf' class variable
     * @return childOf (int)
     */
    public int getChildOf() {
        return childOf;
    } // method getChildOf

    /**
     * Return the 'childOf' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getChildOf methods
     * @return childOf (String)
     */
    public String getChildOf(String s) {
        return ((childOf != INTNULL) ? new Integer(childOf).toString() : "");
    } // method getChildOf


    /**
     * Return the 'text' class variable
     * @return text (String)
     */
    public String getText() {
        return text;
    } // method getText

    /**
     * Return the 'text' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getText methods
     * @return text (String)
     */
    public String getText(String s) {
        return (text != CHARNULL ? text.replace('"','\'') : "");
    } // method getText


    /**
     * Return the 'url' class variable
     * @return url (String)
     */
    public String getUrl() {
        return url;
    } // method getUrl

    /**
     * Return the 'url' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getUrl methods
     * @return url (String)
     */
    public String getUrl(String s) {
        return (url != CHARNULL ? url.replace('"','\'') : "");
    } // method getUrl


    /**
     * Return the 'menuLevel' class variable
     * @return menuLevel (int)
     */
    public int getMenuLevel() {
        return menuLevel;
    } // method getMenuLevel

    /**
     * Return the 'menuLevel' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMenuLevel methods
     * @return menuLevel (String)
     */
    public String getMenuLevel(String s) {
        return ((menuLevel != INTNULL) ? new Integer(menuLevel).toString() : "");
    } // method getMenuLevel


    /**
     * Return the 'scriptTypeCode' class variable
     * @return scriptTypeCode (int)
     */
    public int getScriptTypeCode() {
        return scriptTypeCode;
    } // method getScriptTypeCode

    /**
     * Return the 'scriptTypeCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getScriptTypeCode methods
     * @return scriptTypeCode (String)
     */
    public String getScriptTypeCode(String s) {
        return ((scriptTypeCode != INTNULL) ? new Integer(scriptTypeCode).toString() : "");
    } // method getScriptTypeCode


    /**
     * Return the 'owner' class variable
     * @return owner (String)
     */
    public String getOwner() {
        return owner;
    } // method getOwner

    /**
     * Return the 'owner' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getOwner methods
     * @return owner (String)
     */
    public String getOwner(String s) {
        return (owner != CHARNULL ? owner.replace('"','\'') : "");
    } // method getOwner


    /**
     * Return the 'application' class variable
     * @return application (String)
     */
    public String getApplication() {
        return application;
    } // method getApplication

    /**
     * Return the 'application' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getApplication methods
     * @return application (String)
     */
    public String getApplication(String s) {
        return (application != CHARNULL ? application.replace('"','\'') : "");
    } // method getApplication


    /**
     * Return the 'userType' class variable
     * @return userType (int)
     */
    public int getUserType() {
        return userType;
    } // method getUserType

    /**
     * Return the 'userType' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getUserType methods
     * @return userType (String)
     */
    public String getUserType(String s) {
        return ((userType != INTNULL) ? new Integer(userType).toString() : "");
    } // method getUserType


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
            (sequenceNumber == INTNULL) &&
            (childOf == INTNULL) &&
            (text == CHARNULL) &&
            (url == CHARNULL) &&
            (menuLevel == INTNULL) &&
            (scriptTypeCode == INTNULL) &&
            (owner == CHARNULL) &&
            (application == CHARNULL) &&
            (userType == INTNULL)) {
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
            sequenceNumberError +
            childOfError +
            textError +
            urlError +
            menuLevelError +
            scriptTypeCodeError +
            ownerError +
            applicationError +
            userTypeError;
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
     * Gets the errorcode for the sequenceNumber instance variable
     * @return errorcode (int)
     */
    public int getSequenceNumberError() {
        return sequenceNumberError;
    } // method getSequenceNumberError

    /**
     * Gets the errorMessage for the sequenceNumber instance variable
     * @return errorMessage (String)
     */
    public String getSequenceNumberErrorMessage() {
        return sequenceNumberErrorMessage;
    } // method getSequenceNumberErrorMessage


    /**
     * Gets the errorcode for the childOf instance variable
     * @return errorcode (int)
     */
    public int getChildOfError() {
        return childOfError;
    } // method getChildOfError

    /**
     * Gets the errorMessage for the childOf instance variable
     * @return errorMessage (String)
     */
    public String getChildOfErrorMessage() {
        return childOfErrorMessage;
    } // method getChildOfErrorMessage


    /**
     * Gets the errorcode for the text instance variable
     * @return errorcode (int)
     */
    public int getTextError() {
        return textError;
    } // method getTextError

    /**
     * Gets the errorMessage for the text instance variable
     * @return errorMessage (String)
     */
    public String getTextErrorMessage() {
        return textErrorMessage;
    } // method getTextErrorMessage


    /**
     * Gets the errorcode for the url instance variable
     * @return errorcode (int)
     */
    public int getUrlError() {
        return urlError;
    } // method getUrlError

    /**
     * Gets the errorMessage for the url instance variable
     * @return errorMessage (String)
     */
    public String getUrlErrorMessage() {
        return urlErrorMessage;
    } // method getUrlErrorMessage


    /**
     * Gets the errorcode for the menuLevel instance variable
     * @return errorcode (int)
     */
    public int getMenuLevelError() {
        return menuLevelError;
    } // method getMenuLevelError

    /**
     * Gets the errorMessage for the menuLevel instance variable
     * @return errorMessage (String)
     */
    public String getMenuLevelErrorMessage() {
        return menuLevelErrorMessage;
    } // method getMenuLevelErrorMessage


    /**
     * Gets the errorcode for the scriptTypeCode instance variable
     * @return errorcode (int)
     */
    public int getScriptTypeCodeError() {
        return scriptTypeCodeError;
    } // method getScriptTypeCodeError

    /**
     * Gets the errorMessage for the scriptTypeCode instance variable
     * @return errorMessage (String)
     */
    public String getScriptTypeCodeErrorMessage() {
        return scriptTypeCodeErrorMessage;
    } // method getScriptTypeCodeErrorMessage


    /**
     * Gets the errorcode for the owner instance variable
     * @return errorcode (int)
     */
    public int getOwnerError() {
        return ownerError;
    } // method getOwnerError

    /**
     * Gets the errorMessage for the owner instance variable
     * @return errorMessage (String)
     */
    public String getOwnerErrorMessage() {
        return ownerErrorMessage;
    } // method getOwnerErrorMessage


    /**
     * Gets the errorcode for the application instance variable
     * @return errorcode (int)
     */
    public int getApplicationError() {
        return applicationError;
    } // method getApplicationError

    /**
     * Gets the errorMessage for the application instance variable
     * @return errorMessage (String)
     */
    public String getApplicationErrorMessage() {
        return applicationErrorMessage;
    } // method getApplicationErrorMessage


    /**
     * Gets the errorcode for the userType instance variable
     * @return errorcode (int)
     */
    public int getUserTypeError() {
        return userTypeError;
    } // method getUserTypeError

    /**
     * Gets the errorMessage for the userType instance variable
     * @return errorMessage (String)
     */
    public String getUserTypeErrorMessage() {
        return userTypeErrorMessage;
    } // method getUserTypeErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MnuMenuItems. e.g.<pre>
     * MnuMenuItems dmnMenuItems = new MnuMenuItems(<val1>);
     * MnuMenuItems dmnMenuItemsArray[] = dmnMenuItems.get();</pre>
     * will get the MnuMenuItems record where code = <val1>.
     * @return Array of MnuMenuItems (MnuMenuItems[])
     */
    public MnuMenuItems[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MnuMenuItems dmnMenuItemsArray[] =
     *     new MnuMenuItems().get(MnuMenuItems.CODE+"=<val1>");</pre>
     * will get the MnuMenuItems record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MnuMenuItems (MnuMenuItems[])
     */
    public MnuMenuItems[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MnuMenuItems dmnMenuItemsArray[] =
     *     new MnuMenuItems().get("1=1",dmnMenuItems.CODE);</pre>
     * will get the all the MnuMenuItems records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MnuMenuItems (MnuMenuItems[])
     */
    public MnuMenuItems[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MnuMenuItems.CODE,MnuMenuItems.SEQUENCE_NUMBER;
     * String where = MnuMenuItems.CODE + "=<val1";
     * String order = MnuMenuItems.CODE;
     * MnuMenuItems dmnMenuItemsArray[] =
     *     new MnuMenuItems().get(columns, where, order);</pre>
     * will get the code and sequenceNumber colums of all MnuMenuItems records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MnuMenuItems (MnuMenuItems[])
     */
    public MnuMenuItems[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MnuMenuItems.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MnuMenuItems[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol           = db.getColNumber(CODE);
        int sequenceNumberCol = db.getColNumber(SEQUENCE_NUMBER);
        int childOfCol        = db.getColNumber(CHILD_OF);
        int textCol           = db.getColNumber(TEXT);
        int urlCol            = db.getColNumber(URL);
        int menuLevelCol      = db.getColNumber(MENU_LEVEL);
        int scriptTypeCodeCol = db.getColNumber(SCRIPT_TYPE_CODE);
        int ownerCol          = db.getColNumber(OWNER);
        int applicationCol    = db.getColNumber(APPLICATION);
        int userTypeCol       = db.getColNumber(USER_TYPE);
        MnuMenuItems[] cArray = new MnuMenuItems[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MnuMenuItems();
            if (codeCol != -1)
                cArray[i].setCode          ((String) row.elementAt(codeCol));
            if (sequenceNumberCol != -1)
                cArray[i].setSequenceNumber((String) row.elementAt(sequenceNumberCol));
            if (childOfCol != -1)
                cArray[i].setChildOf       ((String) row.elementAt(childOfCol));
            if (textCol != -1)
                cArray[i].setText          ((String) row.elementAt(textCol));
            if (urlCol != -1)
                cArray[i].setUrl           ((String) row.elementAt(urlCol));
            if (menuLevelCol != -1)
                cArray[i].setMenuLevel     ((String) row.elementAt(menuLevelCol));
            if (scriptTypeCodeCol != -1)
                cArray[i].setScriptTypeCode((String) row.elementAt(scriptTypeCodeCol));
            if (ownerCol != -1)
                cArray[i].setOwner         ((String) row.elementAt(ownerCol));
            if (applicationCol != -1)
                cArray[i].setApplication   ((String) row.elementAt(applicationCol));
            if (userTypeCol != -1)
                cArray[i].setUserType      ((String) row.elementAt(userTypeCol));
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
     *     new MnuMenuItems(
     *         INTNULL,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>,
     *         <val9>,
     *         <val10>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     sequenceNumber = <val2>,
     *     childOf        = <val3>,
     *     text           = <val4>,
     *     url            = <val5>,
     *     menuLevel      = <val6>,
     *     scriptTypeCode = <val7>,
     *     owner          = <val8>,
     *     application    = <val9>,
     *     userType       = <val10>.
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
     * Boolean success = new MnuMenuItems(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL).del();</pre>
     * will delete all records where sequenceNumber = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MnuMenuItems dmnMenuItems = new MnuMenuItems();
     * success = dmnMenuItems.del(MnuMenuItems.CODE+"=<val1>");</pre>
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
     * update are taken from the MnuMenuItems argument, .e.g.<pre>
     * Boolean success
     * MnuMenuItems updMnuMenuItems = new MnuMenuItems();
     * updMnuMenuItems.setSequenceNumber(<val2>);
     * MnuMenuItems whereMnuMenuItems = new MnuMenuItems(<val1>);
     * success = whereMnuMenuItems.upd(updMnuMenuItems);</pre>
     * will update SequenceNumber to <val2> for all records where
     * code = <val1>.
     * @param  dmnMenuItems  A MnuMenuItems variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MnuMenuItems dmnMenuItems) {
        return db.update (TABLE, createColVals(dmnMenuItems), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MnuMenuItems updMnuMenuItems = new MnuMenuItems();
     * updMnuMenuItems.setSequenceNumber(<val2>);
     * MnuMenuItems whereMnuMenuItems = new MnuMenuItems();
     * success = whereMnuMenuItems.upd(
     *     updMnuMenuItems, MnuMenuItems.CODE+"=<val1>");</pre>
     * will update SequenceNumber to <val2> for all records where
     * code = <val1>.
     * @param  dmnMenuItems  A MnuMenuItems variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MnuMenuItems dmnMenuItems, String where) {
        return db.update (TABLE, createColVals(dmnMenuItems), where);
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
        if (getSequenceNumber() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEQUENCE_NUMBER + "=" + getSequenceNumber("");
        } // if getSequenceNumber
        if (getChildOf() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + CHILD_OF + "=" + getChildOf("");
        } // if getChildOf
        if (getText() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TEXT + "='" + getText() + "'";
        } // if getText
        if (getUrl() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + URL + "='" + getUrl() + "'";
        } // if getUrl
        if (getMenuLevel() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MENU_LEVEL + "=" + getMenuLevel("");
        } // if getMenuLevel
        if (getScriptTypeCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SCRIPT_TYPE_CODE + "=" + getScriptTypeCode("");
        } // if getScriptTypeCode
        if (getOwner() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + OWNER + "='" + getOwner() + "'";
        } // if getOwner
        if (getApplication() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + APPLICATION + "='" + getApplication() + "'";
        } // if getApplication
        if (getUserType() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + USER_TYPE + "=" + getUserType("");
        } // if getUserType
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MnuMenuItems aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getSequenceNumber() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEQUENCE_NUMBER +"=";
            colVals += (aVar.getSequenceNumber() == INTNULL2 ?
                "null" : aVar.getSequenceNumber(""));
        } // if aVar.getSequenceNumber
        if (aVar.getChildOf() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CHILD_OF +"=";
            colVals += (aVar.getChildOf() == INTNULL2 ?
                "null" : aVar.getChildOf(""));
        } // if aVar.getChildOf
        if (aVar.getText() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TEXT +"=";
            colVals += (aVar.getText().equals(CHARNULL2) ?
                "null" : "'" + aVar.getText() + "'");
        } // if aVar.getText
        if (aVar.getUrl() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += URL +"=";
            colVals += (aVar.getUrl().equals(CHARNULL2) ?
                "null" : "'" + aVar.getUrl() + "'");
        } // if aVar.getUrl
        if (aVar.getMenuLevel() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MENU_LEVEL +"=";
            colVals += (aVar.getMenuLevel() == INTNULL2 ?
                "null" : aVar.getMenuLevel(""));
        } // if aVar.getMenuLevel
        if (aVar.getScriptTypeCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SCRIPT_TYPE_CODE +"=";
            colVals += (aVar.getScriptTypeCode() == INTNULL2 ?
                "null" : aVar.getScriptTypeCode(""));
        } // if aVar.getScriptTypeCode
        if (aVar.getOwner() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += OWNER +"=";
            colVals += (aVar.getOwner().equals(CHARNULL2) ?
                "null" : "'" + aVar.getOwner() + "'");
        } // if aVar.getOwner
        if (aVar.getApplication() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += APPLICATION +"=";
            colVals += (aVar.getApplication().equals(CHARNULL2) ?
                "null" : "'" + aVar.getApplication() + "'");
        } // if aVar.getApplication
        if (aVar.getUserType() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += USER_TYPE +"=";
            colVals += (aVar.getUserType() == INTNULL2 ?
                "null" : aVar.getUserType(""));
        } // if aVar.getUserType
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getSequenceNumber() != INTNULL) {
            columns = columns + "," + SEQUENCE_NUMBER;
        } // if getSequenceNumber
        if (getChildOf() != INTNULL) {
            columns = columns + "," + CHILD_OF;
        } // if getChildOf
        if (getText() != CHARNULL) {
            columns = columns + "," + TEXT;
        } // if getText
        if (getUrl() != CHARNULL) {
            columns = columns + "," + URL;
        } // if getUrl
        if (getMenuLevel() != INTNULL) {
            columns = columns + "," + MENU_LEVEL;
        } // if getMenuLevel
        if (getScriptTypeCode() != INTNULL) {
            columns = columns + "," + SCRIPT_TYPE_CODE;
        } // if getScriptTypeCode
        if (getOwner() != CHARNULL) {
            columns = columns + "," + OWNER;
        } // if getOwner
        if (getApplication() != CHARNULL) {
            columns = columns + "," + APPLICATION;
        } // if getApplication
        if (getUserType() != INTNULL) {
            columns = columns + "," + USER_TYPE;
        } // if getUserType
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
        if (getSequenceNumber() != INTNULL) {
            values  = values  + "," + getSequenceNumber("");
        } // if getSequenceNumber
        if (getChildOf() != INTNULL) {
            values  = values  + "," + getChildOf("");
        } // if getChildOf
        if (getText() != CHARNULL) {
            values  = values  + ",'" + getText() + "'";
        } // if getText
        if (getUrl() != CHARNULL) {
            values  = values  + ",'" + getUrl() + "'";
        } // if getUrl
        if (getMenuLevel() != INTNULL) {
            values  = values  + "," + getMenuLevel("");
        } // if getMenuLevel
        if (getScriptTypeCode() != INTNULL) {
            values  = values  + "," + getScriptTypeCode("");
        } // if getScriptTypeCode
        if (getOwner() != CHARNULL) {
            values  = values  + ",'" + getOwner() + "'";
        } // if getOwner
        if (getApplication() != CHARNULL) {
            values  = values  + ",'" + getApplication() + "'";
        } // if getApplication
        if (getUserType() != INTNULL) {
            values  = values  + "," + getUserType("");
        } // if getUserType
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
            getSequenceNumber("") + "|" +
            getChildOf("")        + "|" +
            getText("")           + "|" +
            getUrl("")            + "|" +
            getMenuLevel("")      + "|" +
            getScriptTypeCode("") + "|" +
            getOwner("")          + "|" +
            getApplication("")    + "|" +
            getUserType("")       + "|";
    } // method toString

} // class MnuMenuItems
