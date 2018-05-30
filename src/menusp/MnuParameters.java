package menusp;

import java.sql.*;
import java.util.*;

/**
 * This class manages the DMN_PARAMETERS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MnuParameters extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "DMN_PARAMETERS";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The menuItemCode field name */
    public static final String MENU_ITEM_CODE = "MENU_ITEM_CODE";
    /** The sequenceNumber field name */
    public static final String SEQUENCE_NUMBER = "SEQUENCE_NUMBER";
    /** The name field name */
    public static final String NAME = "NAME";
    /** The dataType field name */
    public static final String DATA_TYPE = "DATA_TYPE";
    /** The dataLength field name */
    public static final String DATA_LENGTH = "DATA_LENGTH";
    /** The dataPrecision field name */
    public static final String DATA_PRECISION = "DATA_PRECISION";
    /** The dataScale field name */
    public static final String DATA_SCALE = "DATA_SCALE";
    /** The defaultValue field name */
    public static final String DEFAULT_VALUE = "DEFAULT_VALUE";
    /** The prompt field name */
    public static final String PROMPT = "PROMPT";
    /** The optional field name */
    public static final String OPTIONAL = "OPTIONAL";
    /** The inputType field name */
    public static final String INPUT_TYPE = "INPUT_TYPE";
    /** The special field name */
    public static final String SPECIAL = "SPECIAL";
    /** The lookupTable field name */
    public static final String LOOKUP_TABLE = "LOOKUP_TABLE";
    /** The textField field name */
    public static final String TEXT_FIELD = "TEXT_FIELD";
    /** The valueField field name */
    public static final String VALUE_FIELD = "VALUE_FIELD";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private int       menuItemCode;
    private int       sequenceNumber;
    private String    name;
    private String    dataType;
    private int       dataLength;
    private int       dataPrecision;
    private int       dataScale;
    private String    defaultValue;
    private String    prompt;
    private String    optional;
    private String    inputType;
    private String    special;
    private String    lookupTable;
    private String    textField;
    private String    valueField;

    /** The error variables  */
    private int codeError           = ERROR_NORMAL;
    private int menuItemCodeError   = ERROR_NORMAL;
    private int sequenceNumberError = ERROR_NORMAL;
    private int nameError           = ERROR_NORMAL;
    private int dataTypeError       = ERROR_NORMAL;
    private int dataLengthError     = ERROR_NORMAL;
    private int dataPrecisionError  = ERROR_NORMAL;
    private int dataScaleError      = ERROR_NORMAL;
    private int defaultValueError   = ERROR_NORMAL;
    private int promptError         = ERROR_NORMAL;
    private int optionalError       = ERROR_NORMAL;
    private int inputTypeError      = ERROR_NORMAL;
    private int specialError        = ERROR_NORMAL;
    private int lookupTableError    = ERROR_NORMAL;
    private int textFieldError      = ERROR_NORMAL;
    private int valueFieldError     = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage           = "";
    private String menuItemCodeErrorMessage   = "";
    private String sequenceNumberErrorMessage = "";
    private String nameErrorMessage           = "";
    private String dataTypeErrorMessage       = "";
    private String dataLengthErrorMessage     = "";
    private String dataPrecisionErrorMessage  = "";
    private String dataScaleErrorMessage      = "";
    private String defaultValueErrorMessage   = "";
    private String promptErrorMessage         = "";
    private String optionalErrorMessage       = "";
    private String inputTypeErrorMessage      = "";
    private String specialErrorMessage        = "";
    private String lookupTableErrorMessage    = "";
    private String textFieldErrorMessage      = "";
    private String valueFieldErrorMessage     = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       MENU_ITEM_CODE_MN = INTMIN;
    public static final int       MENU_ITEM_CODE_MX = INTMAX;
    public static final int       SEQUENCE_NUMBER_MN = INTMIN;
    public static final int       SEQUENCE_NUMBER_MX = INTMAX;
    public static final int       DATA_LENGTH_MN = INTMIN;
    public static final int       DATA_LENGTH_MX = INTMAX;
    public static final int       DATA_PRECISION_MN = INTMIN;
    public static final int       DATA_PRECISION_MX = INTMAX;
    public static final int       DATA_SCALE_MN = INTMIN;
    public static final int       DATA_SCALE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception menuItemCodeOutOfBoundsException =
        new Exception ("'menuItemCode' out of bounds: " +
            MENU_ITEM_CODE_MN + " - " + MENU_ITEM_CODE_MX);
    Exception sequenceNumberOutOfBoundsException =
        new Exception ("'sequenceNumber' out of bounds: " +
            SEQUENCE_NUMBER_MN + " - " + SEQUENCE_NUMBER_MX);
    Exception dataLengthOutOfBoundsException =
        new Exception ("'dataLength' out of bounds: " +
            DATA_LENGTH_MN + " - " + DATA_LENGTH_MX);
    Exception dataPrecisionOutOfBoundsException =
        new Exception ("'dataPrecision' out of bounds: " +
            DATA_PRECISION_MN + " - " + DATA_PRECISION_MX);
    Exception dataScaleOutOfBoundsException =
        new Exception ("'dataScale' out of bounds: " +
            DATA_SCALE_MN + " - " + DATA_SCALE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MnuParameters() {
        clearVars();
        if (dbg) System.out.println ("<br>in MnuParameters constructor 1"); // debug
    } // MnuParameters constructor

    /**
     * Instantiate a MnuParameters object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MnuParameters(
            int code) {
        this();
        setCode           (code          );
        if (dbg) System.out.println ("<br>in MnuParameters constructor 2"); // debug
    } // MnuParameters constructor

    /**
     * Instantiate a MnuParameters object and initialize the instance variables.
     * @param code            The code           (int)
     * @param menuItemCode    The menuItemCode   (int)
     * @param sequenceNumber  The sequenceNumber (int)
     * @param name            The name           (String)
     * @param dataType        The dataType       (String)
     * @param dataLength      The dataLength     (int)
     * @param dataPrecision   The dataPrecision  (int)
     * @param dataScale       The dataScale      (int)
     * @param defaultValue    The defaultValue   (String)
     * @param prompt          The prompt         (String)
     * @param optional        The optional       (String)
     * @param inputType       The inputType      (String)
     * @param special         The special        (String)
     * @param lookupTable     The lookupTable    (String)
     * @param textField       The textField      (String)
     * @param valueField      The valueField     (String)
     */
    public MnuParameters(
            int    code,
            int    menuItemCode,
            int    sequenceNumber,
            String name,
            String dataType,
            int    dataLength,
            int    dataPrecision,
            int    dataScale,
            String defaultValue,
            String prompt,
            String optional,
            String inputType,
            String special,
            String lookupTable,
            String textField,
            String valueField) {
        this();
        setCode           (code          );
        setMenuItemCode   (menuItemCode  );
        setSequenceNumber (sequenceNumber);
        setName           (name          );
        setDataType       (dataType      );
        setDataLength     (dataLength    );
        setDataPrecision  (dataPrecision );
        setDataScale      (dataScale     );
        setDefaultValue   (defaultValue  );
        setPrompt         (prompt        );
        setOptional       (optional      );
        setInputType      (inputType     );
        setSpecial        (special       );
        setLookupTable    (lookupTable   );
        setTextField      (textField     );
        setValueField     (valueField    );
        if (dbg) System.out.println ("<br>in MnuParameters constructor 3"); // debug
    } // MnuParameters constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode           (INTNULL );
        setMenuItemCode   (INTNULL );
        setSequenceNumber (INTNULL );
        setName           (CHARNULL);
        setDataType       (CHARNULL);
        setDataLength     (INTNULL );
        setDataPrecision  (INTNULL );
        setDataScale      (INTNULL );
        setDefaultValue   (CHARNULL);
        setPrompt         (CHARNULL);
        setOptional       (CHARNULL);
        setInputType      (CHARNULL);
        setSpecial        (CHARNULL);
        setLookupTable    (CHARNULL);
        setTextField      (CHARNULL);
        setValueField     (CHARNULL);
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
     * Set the 'menuItemCode' class variable
     * @param  menuItemCode (int)
     */
    public int setMenuItemCode(int menuItemCode) {
        try {
            if ( ((menuItemCode == INTNULL) ||
                  (menuItemCode == INTNULL2)) ||
                !((menuItemCode < MENU_ITEM_CODE_MN) ||
                  (menuItemCode > MENU_ITEM_CODE_MX)) ) {
                this.menuItemCode = menuItemCode;
                menuItemCodeError = ERROR_NORMAL;
            } else {
                throw menuItemCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setMenuItemCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return menuItemCodeError;
    } // method setMenuItemCode

    /**
     * Set the 'menuItemCode' class variable
     * @param  menuItemCode (Integer)
     */
    public int setMenuItemCode(Integer menuItemCode) {
        try {
            setMenuItemCode(menuItemCode.intValue());
        } catch (Exception e) {
            setMenuItemCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return menuItemCodeError;
    } // method setMenuItemCode

    /**
     * Set the 'menuItemCode' class variable
     * @param  menuItemCode (Float)
     */
    public int setMenuItemCode(Float menuItemCode) {
        try {
            if (menuItemCode.floatValue() == FLOATNULL) {
                setMenuItemCode(INTNULL);
            } else {
                setMenuItemCode(menuItemCode.intValue());
            } // if (menuItemCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setMenuItemCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return menuItemCodeError;
    } // method setMenuItemCode

    /**
     * Set the 'menuItemCode' class variable
     * @param  menuItemCode (String)
     */
    public int setMenuItemCode(String menuItemCode) {
        try {
            setMenuItemCode(new Integer(menuItemCode).intValue());
        } catch (Exception e) {
            setMenuItemCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return menuItemCodeError;
    } // method setMenuItemCode

    /**
     * Called when an exception has occured
     * @param  menuItemCode (String)
     */
    private void setMenuItemCodeError (int menuItemCode, Exception e, int error) {
        this.menuItemCode = menuItemCode;
        menuItemCodeErrorMessage = e.toString();
        menuItemCodeError = error;
    } // method setMenuItemCodeError


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
     * Set the 'name' class variable
     * @param  name (String)
     */
    public int setName(String name) {
        try {
            this.name = name;
            if (this.name != CHARNULL) {
                this.name = stripCRLF(this.name.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>name = " + this.name);
        } catch (Exception e) {
            setNameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return nameError;
    } // method setName

    /**
     * Called when an exception has occured
     * @param  name (String)
     */
    private void setNameError (String name, Exception e, int error) {
        this.name = name;
        nameErrorMessage = e.toString();
        nameError = error;
    } // method setNameError


    /**
     * Set the 'dataType' class variable
     * @param  dataType (String)
     */
    public int setDataType(String dataType) {
        try {
            this.dataType = dataType;
            if (this.dataType != CHARNULL) {
                this.dataType = stripCRLF(this.dataType.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>dataType = " + this.dataType);
        } catch (Exception e) {
            setDataTypeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return dataTypeError;
    } // method setDataType

    /**
     * Called when an exception has occured
     * @param  dataType (String)
     */
    private void setDataTypeError (String dataType, Exception e, int error) {
        this.dataType = dataType;
        dataTypeErrorMessage = e.toString();
        dataTypeError = error;
    } // method setDataTypeError


    /**
     * Set the 'dataLength' class variable
     * @param  dataLength (int)
     */
    public int setDataLength(int dataLength) {
        try {
            if ( ((dataLength == INTNULL) ||
                  (dataLength == INTNULL2)) ||
                !((dataLength < DATA_LENGTH_MN) ||
                  (dataLength > DATA_LENGTH_MX)) ) {
                this.dataLength = dataLength;
                dataLengthError = ERROR_NORMAL;
            } else {
                throw dataLengthOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDataLengthError(INTNULL, e, ERROR_LOCAL);
        } // try
        return dataLengthError;
    } // method setDataLength

    /**
     * Set the 'dataLength' class variable
     * @param  dataLength (Integer)
     */
    public int setDataLength(Integer dataLength) {
        try {
            setDataLength(dataLength.intValue());
        } catch (Exception e) {
            setDataLengthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataLengthError;
    } // method setDataLength

    /**
     * Set the 'dataLength' class variable
     * @param  dataLength (Float)
     */
    public int setDataLength(Float dataLength) {
        try {
            if (dataLength.floatValue() == FLOATNULL) {
                setDataLength(INTNULL);
            } else {
                setDataLength(dataLength.intValue());
            } // if (dataLength.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDataLengthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataLengthError;
    } // method setDataLength

    /**
     * Set the 'dataLength' class variable
     * @param  dataLength (String)
     */
    public int setDataLength(String dataLength) {
        try {
            setDataLength(new Integer(dataLength).intValue());
        } catch (Exception e) {
            setDataLengthError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataLengthError;
    } // method setDataLength

    /**
     * Called when an exception has occured
     * @param  dataLength (String)
     */
    private void setDataLengthError (int dataLength, Exception e, int error) {
        this.dataLength = dataLength;
        dataLengthErrorMessage = e.toString();
        dataLengthError = error;
    } // method setDataLengthError


    /**
     * Set the 'dataPrecision' class variable
     * @param  dataPrecision (int)
     */
    public int setDataPrecision(int dataPrecision) {
        try {
            if ( ((dataPrecision == INTNULL) ||
                  (dataPrecision == INTNULL2)) ||
                !((dataPrecision < DATA_PRECISION_MN) ||
                  (dataPrecision > DATA_PRECISION_MX)) ) {
                this.dataPrecision = dataPrecision;
                dataPrecisionError = ERROR_NORMAL;
            } else {
                throw dataPrecisionOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDataPrecisionError(INTNULL, e, ERROR_LOCAL);
        } // try
        return dataPrecisionError;
    } // method setDataPrecision

    /**
     * Set the 'dataPrecision' class variable
     * @param  dataPrecision (Integer)
     */
    public int setDataPrecision(Integer dataPrecision) {
        try {
            setDataPrecision(dataPrecision.intValue());
        } catch (Exception e) {
            setDataPrecisionError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataPrecisionError;
    } // method setDataPrecision

    /**
     * Set the 'dataPrecision' class variable
     * @param  dataPrecision (Float)
     */
    public int setDataPrecision(Float dataPrecision) {
        try {
            if (dataPrecision.floatValue() == FLOATNULL) {
                setDataPrecision(INTNULL);
            } else {
                setDataPrecision(dataPrecision.intValue());
            } // if (dataPrecision.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDataPrecisionError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataPrecisionError;
    } // method setDataPrecision

    /**
     * Set the 'dataPrecision' class variable
     * @param  dataPrecision (String)
     */
    public int setDataPrecision(String dataPrecision) {
        try {
            setDataPrecision(new Integer(dataPrecision).intValue());
        } catch (Exception e) {
            setDataPrecisionError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataPrecisionError;
    } // method setDataPrecision

    /**
     * Called when an exception has occured
     * @param  dataPrecision (String)
     */
    private void setDataPrecisionError (int dataPrecision, Exception e, int error) {
        this.dataPrecision = dataPrecision;
        dataPrecisionErrorMessage = e.toString();
        dataPrecisionError = error;
    } // method setDataPrecisionError


    /**
     * Set the 'dataScale' class variable
     * @param  dataScale (int)
     */
    public int setDataScale(int dataScale) {
        try {
            if ( ((dataScale == INTNULL) ||
                  (dataScale == INTNULL2)) ||
                !((dataScale < DATA_SCALE_MN) ||
                  (dataScale > DATA_SCALE_MX)) ) {
                this.dataScale = dataScale;
                dataScaleError = ERROR_NORMAL;
            } else {
                throw dataScaleOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDataScaleError(INTNULL, e, ERROR_LOCAL);
        } // try
        return dataScaleError;
    } // method setDataScale

    /**
     * Set the 'dataScale' class variable
     * @param  dataScale (Integer)
     */
    public int setDataScale(Integer dataScale) {
        try {
            setDataScale(dataScale.intValue());
        } catch (Exception e) {
            setDataScaleError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataScaleError;
    } // method setDataScale

    /**
     * Set the 'dataScale' class variable
     * @param  dataScale (Float)
     */
    public int setDataScale(Float dataScale) {
        try {
            if (dataScale.floatValue() == FLOATNULL) {
                setDataScale(INTNULL);
            } else {
                setDataScale(dataScale.intValue());
            } // if (dataScale.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDataScaleError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataScaleError;
    } // method setDataScale

    /**
     * Set the 'dataScale' class variable
     * @param  dataScale (String)
     */
    public int setDataScale(String dataScale) {
        try {
            setDataScale(new Integer(dataScale).intValue());
        } catch (Exception e) {
            setDataScaleError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return dataScaleError;
    } // method setDataScale

    /**
     * Called when an exception has occured
     * @param  dataScale (String)
     */
    private void setDataScaleError (int dataScale, Exception e, int error) {
        this.dataScale = dataScale;
        dataScaleErrorMessage = e.toString();
        dataScaleError = error;
    } // method setDataScaleError


    /**
     * Set the 'defaultValue' class variable
     * @param  defaultValue (String)
     */
    public int setDefaultValue(String defaultValue) {
        try {
            this.defaultValue = defaultValue;
            if (this.defaultValue != CHARNULL) {
                this.defaultValue = stripCRLF(this.defaultValue.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>defaultValue = " + this.defaultValue);
        } catch (Exception e) {
            setDefaultValueError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return defaultValueError;
    } // method setDefaultValue

    /**
     * Called when an exception has occured
     * @param  defaultValue (String)
     */
    private void setDefaultValueError (String defaultValue, Exception e, int error) {
        this.defaultValue = defaultValue;
        defaultValueErrorMessage = e.toString();
        defaultValueError = error;
    } // method setDefaultValueError


    /**
     * Set the 'prompt' class variable
     * @param  prompt (String)
     */
    public int setPrompt(String prompt) {
        try {
            this.prompt = prompt;
            if (this.prompt != CHARNULL) {
                this.prompt = stripCRLF(this.prompt.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>prompt = " + this.prompt);
        } catch (Exception e) {
            setPromptError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return promptError;
    } // method setPrompt

    /**
     * Called when an exception has occured
     * @param  prompt (String)
     */
    private void setPromptError (String prompt, Exception e, int error) {
        this.prompt = prompt;
        promptErrorMessage = e.toString();
        promptError = error;
    } // method setPromptError


    /**
     * Set the 'optional' class variable
     * @param  optional (String)
     */
    public int setOptional(String optional) {
        try {
            this.optional = optional;
            if (this.optional != CHARNULL) {
                this.optional = stripCRLF(this.optional.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>optional = " + this.optional);
        } catch (Exception e) {
            setOptionalError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return optionalError;
    } // method setOptional

    /**
     * Called when an exception has occured
     * @param  optional (String)
     */
    private void setOptionalError (String optional, Exception e, int error) {
        this.optional = optional;
        optionalErrorMessage = e.toString();
        optionalError = error;
    } // method setOptionalError


    /**
     * Set the 'inputType' class variable
     * @param  inputType (String)
     */
    public int setInputType(String inputType) {
        try {
            this.inputType = inputType;
            if (this.inputType != CHARNULL) {
                this.inputType = stripCRLF(this.inputType.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>inputType = " + this.inputType);
        } catch (Exception e) {
            setInputTypeError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return inputTypeError;
    } // method setInputType

    /**
     * Called when an exception has occured
     * @param  inputType (String)
     */
    private void setInputTypeError (String inputType, Exception e, int error) {
        this.inputType = inputType;
        inputTypeErrorMessage = e.toString();
        inputTypeError = error;
    } // method setInputTypeError


    /**
     * Set the 'special' class variable
     * @param  special (String)
     */
    public int setSpecial(String special) {
        try {
            this.special = special;
            if (this.special != CHARNULL) {
                this.special = stripCRLF(this.special.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>special = " + this.special);
        } catch (Exception e) {
            setSpecialError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return specialError;
    } // method setSpecial

    /**
     * Called when an exception has occured
     * @param  special (String)
     */
    private void setSpecialError (String special, Exception e, int error) {
        this.special = special;
        specialErrorMessage = e.toString();
        specialError = error;
    } // method setSpecialError


    /**
     * Set the 'lookupTable' class variable
     * @param  lookupTable (String)
     */
    public int setLookupTable(String lookupTable) {
        try {
            this.lookupTable = lookupTable;
            if (this.lookupTable != CHARNULL) {
                this.lookupTable = stripCRLF(this.lookupTable.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>lookupTable = " + this.lookupTable);
        } catch (Exception e) {
            setLookupTableError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return lookupTableError;
    } // method setLookupTable

    /**
     * Called when an exception has occured
     * @param  lookupTable (String)
     */
    private void setLookupTableError (String lookupTable, Exception e, int error) {
        this.lookupTable = lookupTable;
        lookupTableErrorMessage = e.toString();
        lookupTableError = error;
    } // method setLookupTableError


    /**
     * Set the 'textField' class variable
     * @param  textField (String)
     */
    public int setTextField(String textField) {
        try {
            this.textField = textField;
            if (this.textField != CHARNULL) {
                this.textField = stripCRLF(this.textField.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>textField = " + this.textField);
        } catch (Exception e) {
            setTextFieldError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return textFieldError;
    } // method setTextField

    /**
     * Called when an exception has occured
     * @param  textField (String)
     */
    private void setTextFieldError (String textField, Exception e, int error) {
        this.textField = textField;
        textFieldErrorMessage = e.toString();
        textFieldError = error;
    } // method setTextFieldError


    /**
     * Set the 'valueField' class variable
     * @param  valueField (String)
     */
    public int setValueField(String valueField) {
        try {
            this.valueField = valueField;
            if (this.valueField != CHARNULL) {
                this.valueField = stripCRLF(this.valueField.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>valueField = " + this.valueField);
        } catch (Exception e) {
            setValueFieldError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return valueFieldError;
    } // method setValueField

    /**
     * Called when an exception has occured
     * @param  valueField (String)
     */
    private void setValueFieldError (String valueField, Exception e, int error) {
        this.valueField = valueField;
        valueFieldErrorMessage = e.toString();
        valueFieldError = error;
    } // method setValueFieldError


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
     * Return the 'menuItemCode' class variable
     * @return menuItemCode (int)
     */
    public int getMenuItemCode() {
        return menuItemCode;
    } // method getMenuItemCode

    /**
     * Return the 'menuItemCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getMenuItemCode methods
     * @return menuItemCode (String)
     */
    public String getMenuItemCode(String s) {
        return ((menuItemCode != INTNULL) ? new Integer(menuItemCode).toString() : "");
    } // method getMenuItemCode


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
     * Return the 'name' class variable
     * @return name (String)
     */
    public String getName() {
        return name;
    } // method getName

    /**
     * Return the 'name' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getName methods
     * @return name (String)
     */
    public String getName(String s) {
        return (name != CHARNULL ? name.replace('"','\'') : "");
    } // method getName


    /**
     * Return the 'dataType' class variable
     * @return dataType (String)
     */
    public String getDataType() {
        return dataType;
    } // method getDataType

    /**
     * Return the 'dataType' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataType methods
     * @return dataType (String)
     */
    public String getDataType(String s) {
        return (dataType != CHARNULL ? dataType.replace('"','\'') : "");
    } // method getDataType


    /**
     * Return the 'dataLength' class variable
     * @return dataLength (int)
     */
    public int getDataLength() {
        return dataLength;
    } // method getDataLength

    /**
     * Return the 'dataLength' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataLength methods
     * @return dataLength (String)
     */
    public String getDataLength(String s) {
        return ((dataLength != INTNULL) ? new Integer(dataLength).toString() : "");
    } // method getDataLength


    /**
     * Return the 'dataPrecision' class variable
     * @return dataPrecision (int)
     */
    public int getDataPrecision() {
        return dataPrecision;
    } // method getDataPrecision

    /**
     * Return the 'dataPrecision' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataPrecision methods
     * @return dataPrecision (String)
     */
    public String getDataPrecision(String s) {
        return ((dataPrecision != INTNULL) ? new Integer(dataPrecision).toString() : "");
    } // method getDataPrecision


    /**
     * Return the 'dataScale' class variable
     * @return dataScale (int)
     */
    public int getDataScale() {
        return dataScale;
    } // method getDataScale

    /**
     * Return the 'dataScale' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDataScale methods
     * @return dataScale (String)
     */
    public String getDataScale(String s) {
        return ((dataScale != INTNULL) ? new Integer(dataScale).toString() : "");
    } // method getDataScale


    /**
     * Return the 'defaultValue' class variable
     * @return defaultValue (String)
     */
    public String getDefaultValue() {
        return defaultValue;
    } // method getDefaultValue

    /**
     * Return the 'defaultValue' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDefaultValue methods
     * @return defaultValue (String)
     */
    public String getDefaultValue(String s) {
        return (defaultValue != CHARNULL ? defaultValue.replace('"','\'') : "");
    } // method getDefaultValue


    /**
     * Return the 'prompt' class variable
     * @return prompt (String)
     */
    public String getPrompt() {
        return prompt;
    } // method getPrompt

    /**
     * Return the 'prompt' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPrompt methods
     * @return prompt (String)
     */
    public String getPrompt(String s) {
        return (prompt != CHARNULL ? prompt.replace('"','\'') : "");
    } // method getPrompt


    /**
     * Return the 'optional' class variable
     * @return optional (String)
     */
    public String getOptional() {
        return optional;
    } // method getOptional

    /**
     * Return the 'optional' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getOptional methods
     * @return optional (String)
     */
    public String getOptional(String s) {
        return (optional != CHARNULL ? optional.replace('"','\'') : "");
    } // method getOptional


    /**
     * Return the 'inputType' class variable
     * @return inputType (String)
     */
    public String getInputType() {
        return inputType;
    } // method getInputType

    /**
     * Return the 'inputType' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getInputType methods
     * @return inputType (String)
     */
    public String getInputType(String s) {
        return (inputType != CHARNULL ? inputType.replace('"','\'') : "");
    } // method getInputType


    /**
     * Return the 'special' class variable
     * @return special (String)
     */
    public String getSpecial() {
        return special;
    } // method getSpecial

    /**
     * Return the 'special' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSpecial methods
     * @return special (String)
     */
    public String getSpecial(String s) {
        return (special != CHARNULL ? special.replace('"','\'') : "");
    } // method getSpecial


    /**
     * Return the 'lookupTable' class variable
     * @return lookupTable (String)
     */
    public String getLookupTable() {
        return lookupTable;
    } // method getLookupTable

    /**
     * Return the 'lookupTable' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getLookupTable methods
     * @return lookupTable (String)
     */
    public String getLookupTable(String s) {
        return (lookupTable != CHARNULL ? lookupTable.replace('"','\'') : "");
    } // method getLookupTable


    /**
     * Return the 'textField' class variable
     * @return textField (String)
     */
    public String getTextField() {
        return textField;
    } // method getTextField

    /**
     * Return the 'textField' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getTextField methods
     * @return textField (String)
     */
    public String getTextField(String s) {
        return (textField != CHARNULL ? textField.replace('"','\'') : "");
    } // method getTextField


    /**
     * Return the 'valueField' class variable
     * @return valueField (String)
     */
    public String getValueField() {
        return valueField;
    } // method getValueField

    /**
     * Return the 'valueField' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getValueField methods
     * @return valueField (String)
     */
    public String getValueField(String s) {
        return (valueField != CHARNULL ? valueField.replace('"','\'') : "");
    } // method getValueField


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
            (menuItemCode == INTNULL) &&
            (sequenceNumber == INTNULL) &&
            (name == CHARNULL) &&
            (dataType == CHARNULL) &&
            (dataLength == INTNULL) &&
            (dataPrecision == INTNULL) &&
            (dataScale == INTNULL) &&
            (defaultValue == CHARNULL) &&
            (prompt == CHARNULL) &&
            (optional == CHARNULL) &&
            (inputType == CHARNULL) &&
            (special == CHARNULL) &&
            (lookupTable == CHARNULL) &&
            (textField == CHARNULL) &&
            (valueField == CHARNULL)) {
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
            menuItemCodeError +
            sequenceNumberError +
            nameError +
            dataTypeError +
            dataLengthError +
            dataPrecisionError +
            dataScaleError +
            defaultValueError +
            promptError +
            optionalError +
            inputTypeError +
            specialError +
            lookupTableError +
            textFieldError +
            valueFieldError;
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
     * Gets the errorcode for the menuItemCode instance variable
     * @return errorcode (int)
     */
    public int getMenuItemCodeError() {
        return menuItemCodeError;
    } // method getMenuItemCodeError

    /**
     * Gets the errorMessage for the menuItemCode instance variable
     * @return errorMessage (String)
     */
    public String getMenuItemCodeErrorMessage() {
        return menuItemCodeErrorMessage;
    } // method getMenuItemCodeErrorMessage


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
     * Gets the errorcode for the name instance variable
     * @return errorcode (int)
     */
    public int getNameError() {
        return nameError;
    } // method getNameError

    /**
     * Gets the errorMessage for the name instance variable
     * @return errorMessage (String)
     */
    public String getNameErrorMessage() {
        return nameErrorMessage;
    } // method getNameErrorMessage


    /**
     * Gets the errorcode for the dataType instance variable
     * @return errorcode (int)
     */
    public int getDataTypeError() {
        return dataTypeError;
    } // method getDataTypeError

    /**
     * Gets the errorMessage for the dataType instance variable
     * @return errorMessage (String)
     */
    public String getDataTypeErrorMessage() {
        return dataTypeErrorMessage;
    } // method getDataTypeErrorMessage


    /**
     * Gets the errorcode for the dataLength instance variable
     * @return errorcode (int)
     */
    public int getDataLengthError() {
        return dataLengthError;
    } // method getDataLengthError

    /**
     * Gets the errorMessage for the dataLength instance variable
     * @return errorMessage (String)
     */
    public String getDataLengthErrorMessage() {
        return dataLengthErrorMessage;
    } // method getDataLengthErrorMessage


    /**
     * Gets the errorcode for the dataPrecision instance variable
     * @return errorcode (int)
     */
    public int getDataPrecisionError() {
        return dataPrecisionError;
    } // method getDataPrecisionError

    /**
     * Gets the errorMessage for the dataPrecision instance variable
     * @return errorMessage (String)
     */
    public String getDataPrecisionErrorMessage() {
        return dataPrecisionErrorMessage;
    } // method getDataPrecisionErrorMessage


    /**
     * Gets the errorcode for the dataScale instance variable
     * @return errorcode (int)
     */
    public int getDataScaleError() {
        return dataScaleError;
    } // method getDataScaleError

    /**
     * Gets the errorMessage for the dataScale instance variable
     * @return errorMessage (String)
     */
    public String getDataScaleErrorMessage() {
        return dataScaleErrorMessage;
    } // method getDataScaleErrorMessage


    /**
     * Gets the errorcode for the defaultValue instance variable
     * @return errorcode (int)
     */
    public int getDefaultValueError() {
        return defaultValueError;
    } // method getDefaultValueError

    /**
     * Gets the errorMessage for the defaultValue instance variable
     * @return errorMessage (String)
     */
    public String getDefaultValueErrorMessage() {
        return defaultValueErrorMessage;
    } // method getDefaultValueErrorMessage


    /**
     * Gets the errorcode for the prompt instance variable
     * @return errorcode (int)
     */
    public int getPromptError() {
        return promptError;
    } // method getPromptError

    /**
     * Gets the errorMessage for the prompt instance variable
     * @return errorMessage (String)
     */
    public String getPromptErrorMessage() {
        return promptErrorMessage;
    } // method getPromptErrorMessage


    /**
     * Gets the errorcode for the optional instance variable
     * @return errorcode (int)
     */
    public int getOptionalError() {
        return optionalError;
    } // method getOptionalError

    /**
     * Gets the errorMessage for the optional instance variable
     * @return errorMessage (String)
     */
    public String getOptionalErrorMessage() {
        return optionalErrorMessage;
    } // method getOptionalErrorMessage


    /**
     * Gets the errorcode for the inputType instance variable
     * @return errorcode (int)
     */
    public int getInputTypeError() {
        return inputTypeError;
    } // method getInputTypeError

    /**
     * Gets the errorMessage for the inputType instance variable
     * @return errorMessage (String)
     */
    public String getInputTypeErrorMessage() {
        return inputTypeErrorMessage;
    } // method getInputTypeErrorMessage


    /**
     * Gets the errorcode for the special instance variable
     * @return errorcode (int)
     */
    public int getSpecialError() {
        return specialError;
    } // method getSpecialError

    /**
     * Gets the errorMessage for the special instance variable
     * @return errorMessage (String)
     */
    public String getSpecialErrorMessage() {
        return specialErrorMessage;
    } // method getSpecialErrorMessage


    /**
     * Gets the errorcode for the lookupTable instance variable
     * @return errorcode (int)
     */
    public int getLookupTableError() {
        return lookupTableError;
    } // method getLookupTableError

    /**
     * Gets the errorMessage for the lookupTable instance variable
     * @return errorMessage (String)
     */
    public String getLookupTableErrorMessage() {
        return lookupTableErrorMessage;
    } // method getLookupTableErrorMessage


    /**
     * Gets the errorcode for the textField instance variable
     * @return errorcode (int)
     */
    public int getTextFieldError() {
        return textFieldError;
    } // method getTextFieldError

    /**
     * Gets the errorMessage for the textField instance variable
     * @return errorMessage (String)
     */
    public String getTextFieldErrorMessage() {
        return textFieldErrorMessage;
    } // method getTextFieldErrorMessage


    /**
     * Gets the errorcode for the valueField instance variable
     * @return errorcode (int)
     */
    public int getValueFieldError() {
        return valueFieldError;
    } // method getValueFieldError

    /**
     * Gets the errorMessage for the valueField instance variable
     * @return errorMessage (String)
     */
    public String getValueFieldErrorMessage() {
        return valueFieldErrorMessage;
    } // method getValueFieldErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MnuParameters. e.g.<pre>
     * MnuParameters dmnParameters = new MnuParameters(<val1>);
     * MnuParameters dmnParametersArray[] = dmnParameters.get();</pre>
     * will get the MnuParameters record where code = <val1>.
     * @return Array of MnuParameters (MnuParameters[])
     */
    public MnuParameters[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MnuParameters dmnParametersArray[] =
     *     new MnuParameters().get(MnuParameters.CODE+"=<val1>");</pre>
     * will get the MnuParameters record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MnuParameters (MnuParameters[])
     */
    public MnuParameters[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MnuParameters dmnParametersArray[] =
     *     new MnuParameters().get("1=1",dmnParameters.CODE);</pre>
     * will get the all the MnuParameters records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MnuParameters (MnuParameters[])
     */
    public MnuParameters[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MnuParameters.CODE,MnuParameters.MENU_ITEM_CODE;
     * String where = MnuParameters.CODE + "=<val1";
     * String order = MnuParameters.CODE;
     * MnuParameters dmnParametersArray[] =
     *     new MnuParameters().get(columns, where, order);</pre>
     * will get the code and menuItemCode colums of all MnuParameters records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MnuParameters (MnuParameters[])
     */
    public MnuParameters[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MnuParameters.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MnuParameters[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol           = db.getColNumber(CODE);
        int menuItemCodeCol   = db.getColNumber(MENU_ITEM_CODE);
        int sequenceNumberCol = db.getColNumber(SEQUENCE_NUMBER);
        int nameCol           = db.getColNumber(NAME);
        int dataTypeCol       = db.getColNumber(DATA_TYPE);
        int dataLengthCol     = db.getColNumber(DATA_LENGTH);
        int dataPrecisionCol  = db.getColNumber(DATA_PRECISION);
        int dataScaleCol      = db.getColNumber(DATA_SCALE);
        int defaultValueCol   = db.getColNumber(DEFAULT_VALUE);
        int promptCol         = db.getColNumber(PROMPT);
        int optionalCol       = db.getColNumber(OPTIONAL);
        int inputTypeCol      = db.getColNumber(INPUT_TYPE);
        int specialCol        = db.getColNumber(SPECIAL);
        int lookupTableCol    = db.getColNumber(LOOKUP_TABLE);
        int textFieldCol      = db.getColNumber(TEXT_FIELD);
        int valueFieldCol     = db.getColNumber(VALUE_FIELD);
        MnuParameters[] cArray = new MnuParameters[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MnuParameters();
            if (codeCol != -1)
                cArray[i].setCode          ((String) row.elementAt(codeCol));
            if (menuItemCodeCol != -1)
                cArray[i].setMenuItemCode  ((String) row.elementAt(menuItemCodeCol));
            if (sequenceNumberCol != -1)
                cArray[i].setSequenceNumber((String) row.elementAt(sequenceNumberCol));
            if (nameCol != -1)
                cArray[i].setName          ((String) row.elementAt(nameCol));
            if (dataTypeCol != -1)
                cArray[i].setDataType      ((String) row.elementAt(dataTypeCol));
            if (dataLengthCol != -1)
                cArray[i].setDataLength    ((String) row.elementAt(dataLengthCol));
            if (dataPrecisionCol != -1)
                cArray[i].setDataPrecision ((String) row.elementAt(dataPrecisionCol));
            if (dataScaleCol != -1)
                cArray[i].setDataScale     ((String) row.elementAt(dataScaleCol));
            if (defaultValueCol != -1)
                cArray[i].setDefaultValue  ((String) row.elementAt(defaultValueCol));
            if (promptCol != -1)
                cArray[i].setPrompt        ((String) row.elementAt(promptCol));
            if (optionalCol != -1)
                cArray[i].setOptional      ((String) row.elementAt(optionalCol));
            if (inputTypeCol != -1)
                cArray[i].setInputType     ((String) row.elementAt(inputTypeCol));
            if (specialCol != -1)
                cArray[i].setSpecial       ((String) row.elementAt(specialCol));
            if (lookupTableCol != -1)
                cArray[i].setLookupTable   ((String) row.elementAt(lookupTableCol));
            if (textFieldCol != -1)
                cArray[i].setTextField     ((String) row.elementAt(textFieldCol));
            if (valueFieldCol != -1)
                cArray[i].setValueField    ((String) row.elementAt(valueFieldCol));
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
     *     new MnuParameters(
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
     *         <val16>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     menuItemCode   = <val2>,
     *     sequenceNumber = <val3>,
     *     name           = <val4>,
     *     dataType       = <val5>,
     *     dataLength     = <val6>,
     *     dataPrecision  = <val7>,
     *     dataScale      = <val8>,
     *     defaultValue   = <val9>,
     *     prompt         = <val10>,
     *     optional       = <val11>,
     *     inputType      = <val12>,
     *     special        = <val13>,
     *     lookupTable    = <val14>,
     *     textField      = <val15>,
     *     valueField     = <val16>.
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
     * Boolean success = new MnuParameters(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where menuItemCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MnuParameters dmnParameters = new MnuParameters();
     * success = dmnParameters.del(MnuParameters.CODE+"=<val1>");</pre>
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
     * update are taken from the MnuParameters argument, .e.g.<pre>
     * Boolean success
     * MnuParameters updMnuParameters = new MnuParameters();
     * updMnuParameters.setMenuItemCode(<val2>);
     * MnuParameters whereMnuParameters = new MnuParameters(<val1>);
     * success = whereMnuParameters.upd(updMnuParameters);</pre>
     * will update MenuItemCode to <val2> for all records where
     * code = <val1>.
     * @param  dmnParameters  A MnuParameters variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MnuParameters dmnParameters) {
        return db.update (TABLE, createColVals(dmnParameters), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MnuParameters updMnuParameters = new MnuParameters();
     * updMnuParameters.setMenuItemCode(<val2>);
     * MnuParameters whereMnuParameters = new MnuParameters();
     * success = whereMnuParameters.upd(
     *     updMnuParameters, MnuParameters.CODE+"=<val1>");</pre>
     * will update MenuItemCode to <val2> for all records where
     * code = <val1>.
     * @param  dmnParameters  A MnuParameters variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MnuParameters dmnParameters, String where) {
        return db.update (TABLE, createColVals(dmnParameters), where);
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
        if (getMenuItemCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + MENU_ITEM_CODE + "=" + getMenuItemCode("");
        } // if getMenuItemCode
        if (getSequenceNumber() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SEQUENCE_NUMBER + "=" + getSequenceNumber("");
        } // if getSequenceNumber
        if (getName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NAME + "='" + getName() + "'";
        } // if getName
        if (getDataType() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATA_TYPE + "='" + getDataType() + "'";
        } // if getDataType
        if (getDataLength() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATA_LENGTH + "=" + getDataLength("");
        } // if getDataLength
        if (getDataPrecision() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATA_PRECISION + "=" + getDataPrecision("");
        } // if getDataPrecision
        if (getDataScale() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DATA_SCALE + "=" + getDataScale("");
        } // if getDataScale
        if (getDefaultValue() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DEFAULT_VALUE + "='" + getDefaultValue() + "'";
        } // if getDefaultValue
        if (getPrompt() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PROMPT + "='" + getPrompt() + "'";
        } // if getPrompt
        if (getOptional() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + OPTIONAL + "='" + getOptional() + "'";
        } // if getOptional
        if (getInputType() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + INPUT_TYPE + "='" + getInputType() + "'";
        } // if getInputType
        if (getSpecial() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SPECIAL + "='" + getSpecial() + "'";
        } // if getSpecial
        if (getLookupTable() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + LOOKUP_TABLE + "='" + getLookupTable() + "'";
        } // if getLookupTable
        if (getTextField() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + TEXT_FIELD + "='" + getTextField() + "'";
        } // if getTextField
        if (getValueField() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + VALUE_FIELD + "='" + getValueField() + "'";
        } // if getValueField
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MnuParameters aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getMenuItemCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += MENU_ITEM_CODE +"=";
            colVals += (aVar.getMenuItemCode() == INTNULL2 ?
                "null" : aVar.getMenuItemCode(""));
        } // if aVar.getMenuItemCode
        if (aVar.getSequenceNumber() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SEQUENCE_NUMBER +"=";
            colVals += (aVar.getSequenceNumber() == INTNULL2 ?
                "null" : aVar.getSequenceNumber(""));
        } // if aVar.getSequenceNumber
        if (aVar.getName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NAME +"=";
            colVals += (aVar.getName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getName() + "'");
        } // if aVar.getName
        if (aVar.getDataType() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATA_TYPE +"=";
            colVals += (aVar.getDataType().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDataType() + "'");
        } // if aVar.getDataType
        if (aVar.getDataLength() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATA_LENGTH +"=";
            colVals += (aVar.getDataLength() == INTNULL2 ?
                "null" : aVar.getDataLength(""));
        } // if aVar.getDataLength
        if (aVar.getDataPrecision() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATA_PRECISION +"=";
            colVals += (aVar.getDataPrecision() == INTNULL2 ?
                "null" : aVar.getDataPrecision(""));
        } // if aVar.getDataPrecision
        if (aVar.getDataScale() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DATA_SCALE +"=";
            colVals += (aVar.getDataScale() == INTNULL2 ?
                "null" : aVar.getDataScale(""));
        } // if aVar.getDataScale
        if (aVar.getDefaultValue() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DEFAULT_VALUE +"=";
            colVals += (aVar.getDefaultValue().equals(CHARNULL2) ?
                "null" : "'" + aVar.getDefaultValue() + "'");
        } // if aVar.getDefaultValue
        if (aVar.getPrompt() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PROMPT +"=";
            colVals += (aVar.getPrompt().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPrompt() + "'");
        } // if aVar.getPrompt
        if (aVar.getOptional() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += OPTIONAL +"=";
            colVals += (aVar.getOptional().equals(CHARNULL2) ?
                "null" : "'" + aVar.getOptional() + "'");
        } // if aVar.getOptional
        if (aVar.getInputType() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += INPUT_TYPE +"=";
            colVals += (aVar.getInputType().equals(CHARNULL2) ?
                "null" : "'" + aVar.getInputType() + "'");
        } // if aVar.getInputType
        if (aVar.getSpecial() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SPECIAL +"=";
            colVals += (aVar.getSpecial().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSpecial() + "'");
        } // if aVar.getSpecial
        if (aVar.getLookupTable() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += LOOKUP_TABLE +"=";
            colVals += (aVar.getLookupTable().equals(CHARNULL2) ?
                "null" : "'" + aVar.getLookupTable() + "'");
        } // if aVar.getLookupTable
        if (aVar.getTextField() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += TEXT_FIELD +"=";
            colVals += (aVar.getTextField().equals(CHARNULL2) ?
                "null" : "'" + aVar.getTextField() + "'");
        } // if aVar.getTextField
        if (aVar.getValueField() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += VALUE_FIELD +"=";
            colVals += (aVar.getValueField().equals(CHARNULL2) ?
                "null" : "'" + aVar.getValueField() + "'");
        } // if aVar.getValueField
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getMenuItemCode() != INTNULL) {
            columns = columns + "," + MENU_ITEM_CODE;
        } // if getMenuItemCode
        if (getSequenceNumber() != INTNULL) {
            columns = columns + "," + SEQUENCE_NUMBER;
        } // if getSequenceNumber
        if (getName() != CHARNULL) {
            columns = columns + "," + NAME;
        } // if getName
        if (getDataType() != CHARNULL) {
            columns = columns + "," + DATA_TYPE;
        } // if getDataType
        if (getDataLength() != INTNULL) {
            columns = columns + "," + DATA_LENGTH;
        } // if getDataLength
        if (getDataPrecision() != INTNULL) {
            columns = columns + "," + DATA_PRECISION;
        } // if getDataPrecision
        if (getDataScale() != INTNULL) {
            columns = columns + "," + DATA_SCALE;
        } // if getDataScale
        if (getDefaultValue() != CHARNULL) {
            columns = columns + "," + DEFAULT_VALUE;
        } // if getDefaultValue
        if (getPrompt() != CHARNULL) {
            columns = columns + "," + PROMPT;
        } // if getPrompt
        if (getOptional() != CHARNULL) {
            columns = columns + "," + OPTIONAL;
        } // if getOptional
        if (getInputType() != CHARNULL) {
            columns = columns + "," + INPUT_TYPE;
        } // if getInputType
        if (getSpecial() != CHARNULL) {
            columns = columns + "," + SPECIAL;
        } // if getSpecial
        if (getLookupTable() != CHARNULL) {
            columns = columns + "," + LOOKUP_TABLE;
        } // if getLookupTable
        if (getTextField() != CHARNULL) {
            columns = columns + "," + TEXT_FIELD;
        } // if getTextField
        if (getValueField() != CHARNULL) {
            columns = columns + "," + VALUE_FIELD;
        } // if getValueField
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
        if (getMenuItemCode() != INTNULL) {
            values  = values  + "," + getMenuItemCode("");
        } // if getMenuItemCode
        if (getSequenceNumber() != INTNULL) {
            values  = values  + "," + getSequenceNumber("");
        } // if getSequenceNumber
        if (getName() != CHARNULL) {
            values  = values  + ",'" + getName() + "'";
        } // if getName
        if (getDataType() != CHARNULL) {
            values  = values  + ",'" + getDataType() + "'";
        } // if getDataType
        if (getDataLength() != INTNULL) {
            values  = values  + "," + getDataLength("");
        } // if getDataLength
        if (getDataPrecision() != INTNULL) {
            values  = values  + "," + getDataPrecision("");
        } // if getDataPrecision
        if (getDataScale() != INTNULL) {
            values  = values  + "," + getDataScale("");
        } // if getDataScale
        if (getDefaultValue() != CHARNULL) {
            values  = values  + ",'" + getDefaultValue() + "'";
        } // if getDefaultValue
        if (getPrompt() != CHARNULL) {
            values  = values  + ",'" + getPrompt() + "'";
        } // if getPrompt
        if (getOptional() != CHARNULL) {
            values  = values  + ",'" + getOptional() + "'";
        } // if getOptional
        if (getInputType() != CHARNULL) {
            values  = values  + ",'" + getInputType() + "'";
        } // if getInputType
        if (getSpecial() != CHARNULL) {
            values  = values  + ",'" + getSpecial() + "'";
        } // if getSpecial
        if (getLookupTable() != CHARNULL) {
            values  = values  + ",'" + getLookupTable() + "'";
        } // if getLookupTable
        if (getTextField() != CHARNULL) {
            values  = values  + ",'" + getTextField() + "'";
        } // if getTextField
        if (getValueField() != CHARNULL) {
            values  = values  + ",'" + getValueField() + "'";
        } // if getValueField
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
            getMenuItemCode("")   + "|" +
            getSequenceNumber("") + "|" +
            getName("")           + "|" +
            getDataType("")       + "|" +
            getDataLength("")     + "|" +
            getDataPrecision("")  + "|" +
            getDataScale("")      + "|" +
            getDefaultValue("")   + "|" +
            getPrompt("")         + "|" +
            getOptional("")       + "|" +
            getInputType("")      + "|" +
            getSpecial("")        + "|" +
            getLookupTable("")    + "|" +
            getTextField("")      + "|" +
            getValueField("")     + "|";
    } // method toString

} // class MnuParameters
