package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the CONSTITUENT table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 *insert, delete</b> and <b>update</b> the table.
 *
 * @author 040203 - SIT Group
 * @version
 * 040203 - GenTableClassB class - create class<br>
 */
public class MrnConstituent extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "CONSTITUENT";
    /** The code field name */
    public static final String CODE = "CODE";
    /** The disciplineCode field name */
    public static final String DISCIPLINE_CODE = "DISCIPLINE_CODE";
    /** The name field name */
    public static final String NAME = "NAME";
    /** The abbreviation field name */
    public static final String ABBREVIATION = "ABBREVIATION";
    /** The unit field name */
    public static final String UNIT = "UNIT";
    /** The htmlUnit field name */
    public static final String HTML_UNIT = "HTML_UNIT";
    /** The formatt field name */
    public static final String FORMATT = "FORMATT";
    /** The resTable field name */
    public static final String RES_TABLE = "RES_TABLE";
    /** The fieldName field name */
    public static final String FIELD_NAME = "FIELD_NAME";

    /**
     * The instance variables corresponding to the table fields
     */
    private int       code;
    private int       disciplineCode;
    private String    name;
    private String    abbreviation;
    private String    unit;
    private String    htmlUnit;
    private String    formatt;
    private String    resTable;
    private String    fieldName;

    /** The error variables  */
    private int codeError           = ERROR_NORMAL;
    private int disciplineCodeError = ERROR_NORMAL;
    private int nameError           = ERROR_NORMAL;
    private int abbreviationError   = ERROR_NORMAL;
    private int unitError           = ERROR_NORMAL;
    private int htmlUnitError       = ERROR_NORMAL;
    private int formattError        = ERROR_NORMAL;
    private int resTableError       = ERROR_NORMAL;
    private int fieldNameError      = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String codeErrorMessage           = "";
    private String disciplineCodeErrorMessage = "";
    private String nameErrorMessage           = "";
    private String abbreviationErrorMessage   = "";
    private String unitErrorMessage           = "";
    private String htmlUnitErrorMessage       = "";
    private String formattErrorMessage        = "";
    private String resTableErrorMessage       = "";
    private String fieldNameErrorMessage      = "";

    /** The min-max constants for all numerics */
    public static final int       CODE_MN = INTMIN;
    public static final int       CODE_MX = INTMAX;
    public static final int       DISCIPLINE_CODE_MN = INTMIN;
    public static final int       DISCIPLINE_CODE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception codeOutOfBoundsException =
        new Exception ("'code' out of bounds: " +
            CODE_MN + " - " + CODE_MX);
    Exception disciplineCodeOutOfBoundsException =
        new Exception ("'disciplineCode' out of bounds: " +
            DISCIPLINE_CODE_MN + " - " + DISCIPLINE_CODE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public MrnConstituent() {
        clearVars();
        if (dbg) System.out.println ("<br>in MrnConstituent constructor 1"); // debug
    } // MrnConstituent constructor

    /**
     * Instantiate a MrnConstituent object and initialize the instance variables.
     * @param code  The code (int)
     */
    public MrnConstituent(
            int code) {
        this();
        setCode           (code          );
        if (dbg) System.out.println ("<br>in MrnConstituent constructor 2"); // debug
    } // MrnConstituent constructor

    /**
     * Instantiate a MrnConstituent object and initialize the instance variables.
     * @param code            The code           (int)
     * @param disciplineCode  The disciplineCode (int)
     * @param name            The name           (String)
     * @param abbreviation    The abbreviation   (String)
     * @param unit            The unit           (String)
     * @param htmlUnit        The htmlUnit       (String)
     * @param formatt         The formatt        (String)
     * @param resTable        The resTable       (String)
     * @param fieldName       The fieldName      (String)
     */
    public MrnConstituent(
            int    code,
            int    disciplineCode,
            String name,
            String abbreviation,
            String unit,
            String htmlUnit,
            String formatt,
            String resTable,
            String fieldName) {
        this();
        setCode           (code          );
        setDisciplineCode (disciplineCode);
        setName           (name          );
        setAbbreviation   (abbreviation  );
        setUnit           (unit          );
        setHtmlUnit       (htmlUnit      );
        setFormatt        (formatt       );
        setResTable       (resTable      );
        setFieldName      (fieldName     );
        if (dbg) System.out.println ("<br>in MrnConstituent constructor 3"); // debug
    } // MrnConstituent constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setCode           (INTNULL );
        setDisciplineCode (INTNULL );
        setName           (CHARNULL);
        setAbbreviation   (CHARNULL);
        setUnit           (CHARNULL);
        setHtmlUnit       (CHARNULL);
        setFormatt        (CHARNULL);
        setResTable       (CHARNULL);
        setFieldName      (CHARNULL);
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
     * Set the 'disciplineCode' class variable
     * @param  disciplineCode (int)
     */
    public int setDisciplineCode(int disciplineCode) {
        try {
            if ( ((disciplineCode == INTNULL) ||
                  (disciplineCode == INTNULL2)) ||
                !((disciplineCode < DISCIPLINE_CODE_MN) ||
                  (disciplineCode > DISCIPLINE_CODE_MX)) ) {
                this.disciplineCode = disciplineCode;
                disciplineCodeError = ERROR_NORMAL;
            } else {
                throw disciplineCodeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setDisciplineCodeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return disciplineCodeError;
    } // method setDisciplineCode

    /**
     * Set the 'disciplineCode' class variable
     * @param  disciplineCode (Integer)
     */
    public int setDisciplineCode(Integer disciplineCode) {
        try {
            setDisciplineCode(disciplineCode.intValue());
        } catch (Exception e) {
            setDisciplineCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return disciplineCodeError;
    } // method setDisciplineCode

    /**
     * Set the 'disciplineCode' class variable
     * @param  disciplineCode (Float)
     */
    public int setDisciplineCode(Float disciplineCode) {
        try {
            if (disciplineCode.floatValue() == FLOATNULL) {
                setDisciplineCode(INTNULL);
            } else {
                setDisciplineCode(disciplineCode.intValue());
            } // if (disciplineCode.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setDisciplineCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return disciplineCodeError;
    } // method setDisciplineCode

    /**
     * Set the 'disciplineCode' class variable
     * @param  disciplineCode (String)
     */
    public int setDisciplineCode(String disciplineCode) {
        try {
            setDisciplineCode(new Integer(disciplineCode).intValue());
        } catch (Exception e) {
            setDisciplineCodeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return disciplineCodeError;
    } // method setDisciplineCode

    /**
     * Called when an exception has occured
     * @param  disciplineCode (String)
     */
    private void setDisciplineCodeError (int disciplineCode, Exception e, int error) {
        this.disciplineCode = disciplineCode;
        disciplineCodeErrorMessage = e.toString();
        disciplineCodeError = error;
    } // method setDisciplineCodeError


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
     * Set the 'abbreviation' class variable
     * @param  abbreviation (String)
     */
    public int setAbbreviation(String abbreviation) {
        try {
            this.abbreviation = abbreviation;
            if (this.abbreviation != CHARNULL) {
                this.abbreviation = stripCRLF(this.abbreviation.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>abbreviation = " + this.abbreviation);
        } catch (Exception e) {
            setAbbreviationError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return abbreviationError;
    } // method setAbbreviation

    /**
     * Called when an exception has occured
     * @param  abbreviation (String)
     */
    private void setAbbreviationError (String abbreviation, Exception e, int error) {
        this.abbreviation = abbreviation;
        abbreviationErrorMessage = e.toString();
        abbreviationError = error;
    } // method setAbbreviationError


    /**
     * Set the 'unit' class variable
     * @param  unit (String)
     */
    public int setUnit(String unit) {
        try {
            this.unit = unit;
            if (this.unit != CHARNULL) {
                this.unit = stripCRLF(this.unit.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>unit = " + this.unit);
        } catch (Exception e) {
            setUnitError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return unitError;
    } // method setUnit

    /**
     * Called when an exception has occured
     * @param  unit (String)
     */
    private void setUnitError (String unit, Exception e, int error) {
        this.unit = unit;
        unitErrorMessage = e.toString();
        unitError = error;
    } // method setUnitError


    /**
     * Set the 'htmlUnit' class variable
     * @param  htmlUnit (String)
     */
    public int setHtmlUnit(String htmlUnit) {
        try {
            this.htmlUnit = htmlUnit;
            if (this.htmlUnit != CHARNULL) {
                this.htmlUnit = stripCRLF(this.htmlUnit.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>htmlUnit = " + this.htmlUnit);
        } catch (Exception e) {
            setHtmlUnitError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return htmlUnitError;
    } // method setHtmlUnit

    /**
     * Called when an exception has occured
     * @param  htmlUnit (String)
     */
    private void setHtmlUnitError (String htmlUnit, Exception e, int error) {
        this.htmlUnit = htmlUnit;
        htmlUnitErrorMessage = e.toString();
        htmlUnitError = error;
    } // method setHtmlUnitError


    /**
     * Set the 'formatt' class variable
     * @param  formatt (String)
     */
    public int setFormatt(String formatt) {
        try {
            this.formatt = formatt;
            if (this.formatt != CHARNULL) {
                this.formatt = stripCRLF(this.formatt.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>formatt = " + this.formatt);
        } catch (Exception e) {
            setFormattError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return formattError;
    } // method setFormatt

    /**
     * Called when an exception has occured
     * @param  formatt (String)
     */
    private void setFormattError (String formatt, Exception e, int error) {
        this.formatt = formatt;
        formattErrorMessage = e.toString();
        formattError = error;
    } // method setFormattError


    /**
     * Set the 'resTable' class variable
     * @param  resTable (String)
     */
    public int setResTable(String resTable) {
        try {
            this.resTable = resTable;
            if (this.resTable != CHARNULL) {
                this.resTable = stripCRLF(this.resTable.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>resTable = " + this.resTable);
        } catch (Exception e) {
            setResTableError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return resTableError;
    } // method setResTable

    /**
     * Called when an exception has occured
     * @param  resTable (String)
     */
    private void setResTableError (String resTable, Exception e, int error) {
        this.resTable = resTable;
        resTableErrorMessage = e.toString();
        resTableError = error;
    } // method setResTableError


    /**
     * Set the 'fieldName' class variable
     * @param  fieldName (String)
     */
    public int setFieldName(String fieldName) {
        try {
            this.fieldName = fieldName;
            if (this.fieldName != CHARNULL) {
                this.fieldName = stripCRLF(this.fieldName.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>fieldName = " + this.fieldName);
        } catch (Exception e) {
            setFieldNameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return fieldNameError;
    } // method setFieldName

    /**
     * Called when an exception has occured
     * @param  fieldName (String)
     */
    private void setFieldNameError (String fieldName, Exception e, int error) {
        this.fieldName = fieldName;
        fieldNameErrorMessage = e.toString();
        fieldNameError = error;
    } // method setFieldNameError


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
     * Return the 'disciplineCode' class variable
     * @return disciplineCode (int)
     */
    public int getDisciplineCode() {
        return disciplineCode;
    } // method getDisciplineCode

    /**
     * Return the 'disciplineCode' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getDisciplineCode methods
     * @return disciplineCode (String)
     */
    public String getDisciplineCode(String s) {
        return ((disciplineCode != INTNULL) ? new Integer(disciplineCode).toString() : "");
    } // method getDisciplineCode


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
     * Return the 'abbreviation' class variable
     * @return abbreviation (String)
     */
    public String getAbbreviation() {
        return abbreviation;
    } // method getAbbreviation

    /**
     * Return the 'abbreviation' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAbbreviation methods
     * @return abbreviation (String)
     */
    public String getAbbreviation(String s) {
        return (abbreviation != CHARNULL ? abbreviation.replace('"','\'') : "");
    } // method getAbbreviation


    /**
     * Return the 'unit' class variable
     * @return unit (String)
     */
    public String getUnit() {
        return unit;
    } // method getUnit

    /**
     * Return the 'unit' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getUnit methods
     * @return unit (String)
     */
    public String getUnit(String s) {
        return (unit != CHARNULL ? unit.replace('"','\'') : "");
    } // method getUnit


    /**
     * Return the 'htmlUnit' class variable
     * @return htmlUnit (String)
     */
    public String getHtmlUnit() {
        return htmlUnit;
    } // method getHtmlUnit

    /**
     * Return the 'htmlUnit' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getHtmlUnit methods
     * @return htmlUnit (String)
     */
    public String getHtmlUnit(String s) {
        return (htmlUnit != CHARNULL ? htmlUnit.replace('"','\'') : "");
    } // method getHtmlUnit


    /**
     * Return the 'formatt' class variable
     * @return formatt (String)
     */
    public String getFormatt() {
        return formatt;
    } // method getFormatt

    /**
     * Return the 'formatt' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFormatt methods
     * @return formatt (String)
     */
    public String getFormatt(String s) {
        return (formatt != CHARNULL ? formatt.replace('"','\'') : "");
    } // method getFormatt


    /**
     * Return the 'resTable' class variable
     * @return resTable (String)
     */
    public String getResTable() {
        return resTable;
    } // method getResTable

    /**
     * Return the 'resTable' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getResTable methods
     * @return resTable (String)
     */
    public String getResTable(String s) {
        return (resTable != CHARNULL ? resTable.replace('"','\'') : "");
    } // method getResTable


    /**
     * Return the 'fieldName' class variable
     * @return fieldName (String)
     */
    public String getFieldName() {
        return fieldName;
    } // method getFieldName

    /**
     * Return the 'fieldName' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFieldName methods
     * @return fieldName (String)
     */
    public String getFieldName(String s) {
        return (fieldName != CHARNULL ? fieldName.replace('"','\'') : "");
    } // method getFieldName


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
            (disciplineCode == INTNULL) &&
            (name == CHARNULL) &&
            (abbreviation == CHARNULL) &&
            (unit == CHARNULL) &&
            (htmlUnit == CHARNULL) &&
            (formatt == CHARNULL) &&
            (resTable == CHARNULL) &&
            (fieldName == CHARNULL)) {
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
            disciplineCodeError +
            nameError +
            abbreviationError +
            unitError +
            htmlUnitError +
            formattError +
            resTableError +
            fieldNameError;
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
     * Gets the errorcode for the disciplineCode instance variable
     * @return errorcode (int)
     */
    public int getDisciplineCodeError() {
        return disciplineCodeError;
    } // method getDisciplineCodeError

    /**
     * Gets the errorMessage for the disciplineCode instance variable
     * @return errorMessage (String)
     */
    public String getDisciplineCodeErrorMessage() {
        return disciplineCodeErrorMessage;
    } // method getDisciplineCodeErrorMessage


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
     * Gets the errorcode for the abbreviation instance variable
     * @return errorcode (int)
     */
    public int getAbbreviationError() {
        return abbreviationError;
    } // method getAbbreviationError

    /**
     * Gets the errorMessage for the abbreviation instance variable
     * @return errorMessage (String)
     */
    public String getAbbreviationErrorMessage() {
        return abbreviationErrorMessage;
    } // method getAbbreviationErrorMessage


    /**
     * Gets the errorcode for the unit instance variable
     * @return errorcode (int)
     */
    public int getUnitError() {
        return unitError;
    } // method getUnitError

    /**
     * Gets the errorMessage for the unit instance variable
     * @return errorMessage (String)
     */
    public String getUnitErrorMessage() {
        return unitErrorMessage;
    } // method getUnitErrorMessage


    /**
     * Gets the errorcode for the htmlUnit instance variable
     * @return errorcode (int)
     */
    public int getHtmlUnitError() {
        return htmlUnitError;
    } // method getHtmlUnitError

    /**
     * Gets the errorMessage for the htmlUnit instance variable
     * @return errorMessage (String)
     */
    public String getHtmlUnitErrorMessage() {
        return htmlUnitErrorMessage;
    } // method getHtmlUnitErrorMessage


    /**
     * Gets the errorcode for the formatt instance variable
     * @return errorcode (int)
     */
    public int getFormattError() {
        return formattError;
    } // method getFormattError

    /**
     * Gets the errorMessage for the formatt instance variable
     * @return errorMessage (String)
     */
    public String getFormattErrorMessage() {
        return formattErrorMessage;
    } // method getFormattErrorMessage


    /**
     * Gets the errorcode for the resTable instance variable
     * @return errorcode (int)
     */
    public int getResTableError() {
        return resTableError;
    } // method getResTableError

    /**
     * Gets the errorMessage for the resTable instance variable
     * @return errorMessage (String)
     */
    public String getResTableErrorMessage() {
        return resTableErrorMessage;
    } // method getResTableErrorMessage


    /**
     * Gets the errorcode for the fieldName instance variable
     * @return errorcode (int)
     */
    public int getFieldNameError() {
        return fieldNameError;
    } // method getFieldNameError

    /**
     * Gets the errorMessage for the fieldName instance variable
     * @return errorMessage (String)
     */
    public String getFieldNameErrorMessage() {
        return fieldNameErrorMessage;
    } // method getFieldNameErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of MrnConstituent. e.g.<pre>
     * MrnConstituent constituent = new MrnConstituent(<val1>);
     * MrnConstituent constituentArray[] = constituent.get();</pre>
     * will get the MrnConstituent record where code = <val1>.
     * @return Array of MrnConstituent (MrnConstituent[])
     */
    public MrnConstituent[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * MrnConstituent constituentArray[] =
     *     new MrnConstituent().get(MrnConstituent.CODE+"=<val1>");</pre>
     * will get the MrnConstituent record where code = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of MrnConstituent (MrnConstituent[])
     */
    public MrnConstituent[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * MrnConstituent constituentArray[] =
     *     new MrnConstituent().get("1=1",constituent.CODE);</pre>
     * will get the all the MrnConstituent records, and order them by code.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnConstituent (MrnConstituent[])
     */
    public MrnConstituent[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = MrnConstituent.CODE,MrnConstituent.DISCIPLINE_CODE;
     * String where = MrnConstituent.CODE + "=<val1";
     * String order = MrnConstituent.CODE;
     * MrnConstituent constituentArray[] =
     *     new MrnConstituent().get(columns, where, order);</pre>
     * will get the code and disciplineCode colums of all MrnConstituent records,
     * where code = <val1>, and order them by code.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of MrnConstituent (MrnConstituent[])
     */
    public MrnConstituent[] get (String fields, String where, String order) {
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
     * and transforms it into an array of MrnConstituent.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private MrnConstituent[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int codeCol           = db.getColNumber(CODE);
        int disciplineCodeCol = db.getColNumber(DISCIPLINE_CODE);
        int nameCol           = db.getColNumber(NAME);
        int abbreviationCol   = db.getColNumber(ABBREVIATION);
        int unitCol           = db.getColNumber(UNIT);
        int htmlUnitCol       = db.getColNumber(HTML_UNIT);
        int formattCol        = db.getColNumber(FORMATT);
        int resTableCol       = db.getColNumber(RES_TABLE);
        int fieldNameCol      = db.getColNumber(FIELD_NAME);
        MrnConstituent[] cArray = new MrnConstituent[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new MrnConstituent();
            if (codeCol != -1)
                cArray[i].setCode          ((String) row.elementAt(codeCol));
            if (disciplineCodeCol != -1)
                cArray[i].setDisciplineCode((String) row.elementAt(disciplineCodeCol));
            if (nameCol != -1)
                cArray[i].setName          ((String) row.elementAt(nameCol));
            if (abbreviationCol != -1)
                cArray[i].setAbbreviation  ((String) row.elementAt(abbreviationCol));
            if (unitCol != -1)
                cArray[i].setUnit          ((String) row.elementAt(unitCol));
            if (htmlUnitCol != -1)
                cArray[i].setHtmlUnit      ((String) row.elementAt(htmlUnitCol));
            if (formattCol != -1)
                cArray[i].setFormatt       ((String) row.elementAt(formattCol));
            if (resTableCol != -1)
                cArray[i].setResTable      ((String) row.elementAt(resTableCol));
            if (fieldNameCol != -1)
                cArray[i].setFieldName     ((String) row.elementAt(fieldNameCol));
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
     *     new MrnConstituent(
     *         INTNULL,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>,
     *         <val9>).put();</pre>
     * will insert a record with:
     *     code = the maximum code+1,
     *     disciplineCode = <val2>,
     *     name           = <val3>,
     *     abbreviation   = <val4>,
     *     unit           = <val5>,
     *     htmlUnit       = <val6>,
     *     formatt        = <val7>,
     *     resTable       = <val8>,
     *     fieldName      = <val9>.
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
     * Boolean success = new MrnConstituent(
     *     Tables.INTNULL,
     *     <val2>,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where disciplineCode = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnConstituent constituent = new MrnConstituent();
     * success = constituent.del(MrnConstituent.CODE+"=<val1>");</pre>
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
     * update are taken from the MrnConstituent argument, .e.g.<pre>
     * Boolean success
     * MrnConstituent updMrnConstituent = new MrnConstituent();
     * updMrnConstituent.setDisciplineCode(<val2>);
     * MrnConstituent whereMrnConstituent = new MrnConstituent(<val1>);
     * success = whereMrnConstituent.upd(updMrnConstituent);</pre>
     * will update DisciplineCode to <val2> for all records where
     * code = <val1>.
     * @param  constituent  A MrnConstituent variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnConstituent constituent) {
        return db.update (TABLE, createColVals(constituent), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * MrnConstituent updMrnConstituent = new MrnConstituent();
     * updMrnConstituent.setDisciplineCode(<val2>);
     * MrnConstituent whereMrnConstituent = new MrnConstituent();
     * success = whereMrnConstituent.upd(
     *     updMrnConstituent, MrnConstituent.CODE+"=<val1>");</pre>
     * will update DisciplineCode to <val2> for all records where
     * code = <val1>.
     * @param  constituent  A MrnConstituent variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(MrnConstituent constituent, String where) {
        return db.update (TABLE, createColVals(constituent), where);
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
        if (getDisciplineCode() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + DISCIPLINE_CODE + "=" + getDisciplineCode("");
        } // if getDisciplineCode
        if (getName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + NAME + "='" + getName() + "'";
        } // if getName
        if (getAbbreviation() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ABBREVIATION + "='" + getAbbreviation() + "'";
        } // if getAbbreviation
        if (getUnit() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + UNIT + "='" + getUnit() + "'";
        } // if getUnit
        if (getHtmlUnit() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + HTML_UNIT + "='" + getHtmlUnit() + "'";
        } // if getHtmlUnit
        if (getFormatt() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FORMATT + "='" + getFormatt() + "'";
        } // if getFormatt
        if (getResTable() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + RES_TABLE + "='" + getResTable() + "'";
        } // if getResTable
        if (getFieldName() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FIELD_NAME + "='" + getFieldName() + "'";
        } // if getFieldName
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(MrnConstituent aVar) {
        String colVals = "";
        if (aVar.getCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += CODE +"=";
            colVals += (aVar.getCode() == INTNULL2 ?
                "null" : aVar.getCode(""));
        } // if aVar.getCode
        if (aVar.getDisciplineCode() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += DISCIPLINE_CODE +"=";
            colVals += (aVar.getDisciplineCode() == INTNULL2 ?
                "null" : aVar.getDisciplineCode(""));
        } // if aVar.getDisciplineCode
        if (aVar.getName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += NAME +"=";
            colVals += (aVar.getName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getName() + "'");
        } // if aVar.getName
        if (aVar.getAbbreviation() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ABBREVIATION +"=";
            colVals += (aVar.getAbbreviation().equals(CHARNULL2) ?
                "null" : "'" + aVar.getAbbreviation() + "'");
        } // if aVar.getAbbreviation
        if (aVar.getUnit() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += UNIT +"=";
            colVals += (aVar.getUnit().equals(CHARNULL2) ?
                "null" : "'" + aVar.getUnit() + "'");
        } // if aVar.getUnit
        if (aVar.getHtmlUnit() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += HTML_UNIT +"=";
            colVals += (aVar.getHtmlUnit().equals(CHARNULL2) ?
                "null" : "'" + aVar.getHtmlUnit() + "'");
        } // if aVar.getHtmlUnit
        if (aVar.getFormatt() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FORMATT +"=";
            colVals += (aVar.getFormatt().equals(CHARNULL2) ?
                "null" : "'" + aVar.getFormatt() + "'");
        } // if aVar.getFormatt
        if (aVar.getResTable() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += RES_TABLE +"=";
            colVals += (aVar.getResTable().equals(CHARNULL2) ?
                "null" : "'" + aVar.getResTable() + "'");
        } // if aVar.getResTable
        if (aVar.getFieldName() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FIELD_NAME +"=";
            colVals += (aVar.getFieldName().equals(CHARNULL2) ?
                "null" : "'" + aVar.getFieldName() + "'");
        } // if aVar.getFieldName
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = CODE;
        if (getDisciplineCode() != INTNULL) {
            columns = columns + "," + DISCIPLINE_CODE;
        } // if getDisciplineCode
        if (getName() != CHARNULL) {
            columns = columns + "," + NAME;
        } // if getName
        if (getAbbreviation() != CHARNULL) {
            columns = columns + "," + ABBREVIATION;
        } // if getAbbreviation
        if (getUnit() != CHARNULL) {
            columns = columns + "," + UNIT;
        } // if getUnit
        if (getHtmlUnit() != CHARNULL) {
            columns = columns + "," + HTML_UNIT;
        } // if getHtmlUnit
        if (getFormatt() != CHARNULL) {
            columns = columns + "," + FORMATT;
        } // if getFormatt
        if (getResTable() != CHARNULL) {
            columns = columns + "," + RES_TABLE;
        } // if getResTable
        if (getFieldName() != CHARNULL) {
            columns = columns + "," + FIELD_NAME;
        } // if getFieldName
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
        if (getDisciplineCode() != INTNULL) {
            values  = values  + "," + getDisciplineCode("");
        } // if getDisciplineCode
        if (getName() != CHARNULL) {
            values  = values  + ",'" + getName() + "'";
        } // if getName
        if (getAbbreviation() != CHARNULL) {
            values  = values  + ",'" + getAbbreviation() + "'";
        } // if getAbbreviation
        if (getUnit() != CHARNULL) {
            values  = values  + ",'" + getUnit() + "'";
        } // if getUnit
        if (getHtmlUnit() != CHARNULL) {
            values  = values  + ",'" + getHtmlUnit() + "'";
        } // if getHtmlUnit
        if (getFormatt() != CHARNULL) {
            values  = values  + ",'" + getFormatt() + "'";
        } // if getFormatt
        if (getResTable() != CHARNULL) {
            values  = values  + ",'" + getResTable() + "'";
        } // if getResTable
        if (getFieldName() != CHARNULL) {
            values  = values  + ",'" + getFieldName() + "'";
        } // if getFieldName
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
            getDisciplineCode("") + "|" +
            getName("")           + "|" +
            getAbbreviation("")   + "|" +
            getUnit("")           + "|" +
            getHtmlUnit("")       + "|" +
            getFormatt("")        + "|" +
            getResTable("")       + "|" +
            getFieldName("")      + "|";
    } // method toString

} // class MrnConstituent
