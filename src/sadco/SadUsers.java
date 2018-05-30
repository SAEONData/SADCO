package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the SAD_USERS table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 *
 * @author 091201 - SIT Group
 * @version
 * 091201 - GenTableClassB class - create class<br>
 */
public class SadUsers extends Tables {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    public static final String TABLE = "SAD_USERS";
    /** The userid field name */
    public static final String USERID = "USERID";
    /** The password field name */
    public static final String PASSWORD = "PASSWORD";
    /** The userType field name */
    public static final String USER_TYPE = "USER_TYPE";
    /** The flagType field name */
    public static final String FLAG_TYPE = "FLAG_TYPE";
    /** The flagPassword field name */
    public static final String FLAG_PASSWORD = "FLAG_PASSWORD";
    /** The fname field name */
    public static final String FNAME = "FNAME";
    /** The surname field name */
    public static final String SURNAME = "SURNAME";
    /** The affiliation field name */
    public static final String AFFILIATION = "AFFILIATION";
    /** The address field name */
    public static final String ADDRESS = "ADDRESS";

    /**
     * The instance variables corresponding to the table fields
     */
    private String    userid;
    private String    password;
    private int       userType;
    private int       flagType;
    private String    flagPassword;
    private String    fname;
    private String    surname;
    private String    affiliation;
    private String    address;

    /** The error variables  */
    private int useridError       = ERROR_NORMAL;
    private int passwordError     = ERROR_NORMAL;
    private int userTypeError     = ERROR_NORMAL;
    private int flagTypeError     = ERROR_NORMAL;
    private int flagPasswordError = ERROR_NORMAL;
    private int fnameError        = ERROR_NORMAL;
    private int surnameError      = ERROR_NORMAL;
    private int affiliationError  = ERROR_NORMAL;
    private int addressError      = ERROR_NORMAL;

    /** The errorMessage variables  */
    private String useridErrorMessage       = "";
    private String passwordErrorMessage     = "";
    private String userTypeErrorMessage     = "";
    private String flagTypeErrorMessage     = "";
    private String flagPasswordErrorMessage = "";
    private String fnameErrorMessage        = "";
    private String surnameErrorMessage      = "";
    private String affiliationErrorMessage  = "";
    private String addressErrorMessage      = "";

    /** The min-max constants for all numerics */
    public static final int       USER_TYPE_MN = INTMIN;
    public static final int       USER_TYPE_MX = INTMAX;
    public static final int       FLAG_TYPE_MN = INTMIN;
    public static final int       FLAG_TYPE_MX = INTMAX;

    /** The exceptions for non-Strings */
    Exception userTypeOutOfBoundsException =
        new Exception ("'userType' out of bounds: " +
            USER_TYPE_MN + " - " + USER_TYPE_MX);
    Exception flagTypeOutOfBoundsException =
        new Exception ("'flagType' out of bounds: " +
            FLAG_TYPE_MN + " - " + FLAG_TYPE_MX);


    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public SadUsers() {
        clearVars();
        if (dbg) System.out.println ("<br>in SadUsers constructor 1"); // debug
    } // SadUsers constructor

    /**
     * Instantiate a SadUsers object and initialize the instance variables.
     * @param userid  The userid (String)
     */
    public SadUsers(
            String userid) {
        this();
        setUserid       (userid      );
        if (dbg) System.out.println ("<br>in SadUsers constructor 2"); // debug
    } // SadUsers constructor

    /**
     * Instantiate a SadUsers object and initialize the instance variables.
     * @param userid        The userid       (String)
     * @param password      The password     (String)
     * @param userType      The userType     (int)
     * @param flagType      The flagType     (int)
     * @param flagPassword  The flagPassword (String)
     * @param fname         The fname        (String)
     * @param surname       The surname      (String)
     * @param affiliation   The affiliation  (String)
     * @param address       The address      (String)
     * @return A SadUsers object
     */
    public SadUsers(
            String userid,
            String password,
            int    userType,
            int    flagType,
            String flagPassword,
            String fname,
            String surname,
            String affiliation,
            String address) {
        this();
        setUserid       (userid      );
        setPassword     (password    );
        setUserType     (userType    );
        setFlagType     (flagType    );
        setFlagPassword (flagPassword);
        setFname        (fname       );
        setSurname      (surname     );
        setAffiliation  (affiliation );
        setAddress      (address     );
        if (dbg) System.out.println ("<br>in SadUsers constructor 3"); // debug
    } // SadUsers constructor

    /**
     * Initialise the variables to NULL
     */
    public void clearVars() {
        setUserid       (CHARNULL);
        setPassword     (CHARNULL);
        setUserType     (INTNULL );
        setFlagType     (INTNULL );
        setFlagPassword (CHARNULL);
        setFname        (CHARNULL);
        setSurname      (CHARNULL);
        setAffiliation  (CHARNULL);
        setAddress      (CHARNULL);
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
     * Set the 'password' class variable
     * @param  password (String)
     */
    public int setPassword(String password) {
        try {
            this.password = password;
            if (this.password != CHARNULL) {
                this.password = stripCRLF(this.password.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>password = " + this.password);
        } catch (Exception e) {
            setPasswordError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return passwordError;
    } // method setPassword

    /**
     * Called when an exception has occured
     * @param  password (String)
     */
    private void setPasswordError (String password, Exception e, int error) {
        this.password = password;
        passwordErrorMessage = e.toString();
        passwordError = error;
    } // method setPasswordError


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


    /**
     * Set the 'flagType' class variable
     * @param  flagType (int)
     */
    public int setFlagType(int flagType) {
        try {
            if ( ((flagType == INTNULL) || 
                  (flagType == INTNULL2)) ||
                !((flagType < FLAG_TYPE_MN) ||
                  (flagType > FLAG_TYPE_MX)) ) {
                this.flagType = flagType;
                flagTypeError = ERROR_NORMAL;
            } else {
                throw flagTypeOutOfBoundsException;
            } // if INTNULL ...
        } catch (Exception e) {
            setFlagTypeError(INTNULL, e, ERROR_LOCAL);
        } // try
        return flagTypeError;
    } // method setFlagType

    /**
     * Set the 'flagType' class variable
     * @param  flagType (Integer)
     */
    public int setFlagType(Integer flagType) {
        try {
            setFlagType(flagType.intValue());
        } catch (Exception e) {
            setFlagTypeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return flagTypeError;
    } // method setFlagType

    /**
     * Set the 'flagType' class variable
     * @param  flagType (Float)
     */
    public int setFlagType(Float flagType) {
        try {
            if (flagType.floatValue() == FLOATNULL) {
                setFlagType(INTNULL);
            } else {
                setFlagType(flagType.intValue());
            } // if (flagType.floatValue() == FLOATNULL)
        } catch (Exception e) {
            setFlagTypeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return flagTypeError;
    } // method setFlagType

    /**
     * Set the 'flagType' class variable
     * @param  flagType (String)
     */
    public int setFlagType(String flagType) {
        try {
            setFlagType(new Integer(flagType).intValue());
        } catch (Exception e) {
            setFlagTypeError(INTNULL, e, ERROR_SYSTEM);
        } // try
        return flagTypeError;
    } // method setFlagType

    /**
     * Called when an exception has occured
     * @param  flagType (String)
     */
    private void setFlagTypeError (int flagType, Exception e, int error) {
        this.flagType = flagType;
        flagTypeErrorMessage = e.toString();
        flagTypeError = error;
    } // method setFlagTypeError


    /**
     * Set the 'flagPassword' class variable
     * @param  flagPassword (String)
     */
    public int setFlagPassword(String flagPassword) {
        try {
            this.flagPassword = flagPassword;
            if (this.flagPassword != CHARNULL) {
                this.flagPassword = stripCRLF(this.flagPassword.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>flagPassword = " + this.flagPassword);
        } catch (Exception e) {
            setFlagPasswordError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return flagPasswordError;
    } // method setFlagPassword

    /**
     * Called when an exception has occured
     * @param  flagPassword (String)
     */
    private void setFlagPasswordError (String flagPassword, Exception e, int error) {
        this.flagPassword = flagPassword;
        flagPasswordErrorMessage = e.toString();
        flagPasswordError = error;
    } // method setFlagPasswordError


    /**
     * Set the 'fname' class variable
     * @param  fname (String)
     */
    public int setFname(String fname) {
        try {
            this.fname = fname;
            if (this.fname != CHARNULL) {
                this.fname = stripCRLF(this.fname.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>fname = " + this.fname);
        } catch (Exception e) {
            setFnameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return fnameError;
    } // method setFname

    /**
     * Called when an exception has occured
     * @param  fname (String)
     */
    private void setFnameError (String fname, Exception e, int error) {
        this.fname = fname;
        fnameErrorMessage = e.toString();
        fnameError = error;
    } // method setFnameError


    /**
     * Set the 'surname' class variable
     * @param  surname (String)
     */
    public int setSurname(String surname) {
        try {
            this.surname = surname;
            if (this.surname != CHARNULL) {
                this.surname = stripCRLF(this.surname.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>surname = " + this.surname);
        } catch (Exception e) {
            setSurnameError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return surnameError;
    } // method setSurname

    /**
     * Called when an exception has occured
     * @param  surname (String)
     */
    private void setSurnameError (String surname, Exception e, int error) {
        this.surname = surname;
        surnameErrorMessage = e.toString();
        surnameError = error;
    } // method setSurnameError


    /**
     * Set the 'affiliation' class variable
     * @param  affiliation (String)
     */
    public int setAffiliation(String affiliation) {
        try {
            this.affiliation = affiliation;
            if (this.affiliation != CHARNULL) {
                this.affiliation = stripCRLF(this.affiliation.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>affiliation = " + this.affiliation);
        } catch (Exception e) {
            setAffiliationError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return affiliationError;
    } // method setAffiliation

    /**
     * Called when an exception has occured
     * @param  affiliation (String)
     */
    private void setAffiliationError (String affiliation, Exception e, int error) {
        this.affiliation = affiliation;
        affiliationErrorMessage = e.toString();
        affiliationError = error;
    } // method setAffiliationError


    /**
     * Set the 'address' class variable
     * @param  address (String)
     */
    public int setAddress(String address) {
        try {
            this.address = address;
            if (this.address != CHARNULL) {
                this.address = stripCRLF(this.address.replace('\'','"'));
            }
            if (dbg) System.out.println ("<br>address = " + this.address);
        } catch (Exception e) {
            setAddressError(CHARNULL, e, ERROR_SYSTEM);
        } // try
        return addressError;
    } // method setAddress

    /**
     * Called when an exception has occured
     * @param  address (String)
     */
    private void setAddressError (String address, Exception e, int error) {
        this.address = address;
        addressErrorMessage = e.toString();
        addressError = error;
    } // method setAddressError


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
     * Return the 'password' class variable
     * @return password (String)
     */
    public String getPassword() {
        return password;
    } // method getPassword

    /**
     * Return the 'password' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getPassword methods
     * @return password (String)
     */
    public String getPassword(String s) {
        return (password != CHARNULL ? password.replace('"','\'') : "");
    } // method getPassword


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
     * Return the 'flagType' class variable
     * @return flagType (int)
     */
    public int getFlagType() {
        return flagType;
    } // method getFlagType

    /**
     * Return the 'flagType' class variable as a String
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFlagType methods
     * @return flagType (String)
     */
    public String getFlagType(String s) {
        return ((flagType != INTNULL) ? new Integer(flagType).toString() : "");
    } // method getFlagType


    /**
     * Return the 'flagPassword' class variable
     * @return flagPassword (String)
     */
    public String getFlagPassword() {
        return flagPassword;
    } // method getFlagPassword

    /**
     * Return the 'flagPassword' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFlagPassword methods
     * @return flagPassword (String)
     */
    public String getFlagPassword(String s) {
        return (flagPassword != CHARNULL ? flagPassword.replace('"','\'') : "");
    } // method getFlagPassword


    /**
     * Return the 'fname' class variable
     * @return fname (String)
     */
    public String getFname() {
        return fname;
    } // method getFname

    /**
     * Return the 'fname' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getFname methods
     * @return fname (String)
     */
    public String getFname(String s) {
        return (fname != CHARNULL ? fname.replace('"','\'') : "");
    } // method getFname


    /**
     * Return the 'surname' class variable
     * @return surname (String)
     */
    public String getSurname() {
        return surname;
    } // method getSurname

    /**
     * Return the 'surname' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getSurname methods
     * @return surname (String)
     */
    public String getSurname(String s) {
        return (surname != CHARNULL ? surname.replace('"','\'') : "");
    } // method getSurname


    /**
     * Return the 'affiliation' class variable
     * @return affiliation (String)
     */
    public String getAffiliation() {
        return affiliation;
    } // method getAffiliation

    /**
     * Return the 'affiliation' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAffiliation methods
     * @return affiliation (String)
     */
    public String getAffiliation(String s) {
        return (affiliation != CHARNULL ? affiliation.replace('"','\'') : "");
    } // method getAffiliation


    /**
     * Return the 'address' class variable
     * @return address (String)
     */
    public String getAddress() {
        return address;
    } // method getAddress

    /**
     * Return the 'address' class variable: if NULL returns ""
     * @param  s  Any String variable
     *  - used to differentiate between the 2 getAddress methods
     * @return address (String)
     */
    public String getAddress(String s) {
        return (address != CHARNULL ? address.replace('"','\'') : "");
    } // method getAddress


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
            (password == CHARNULL) &&
            (userType == INTNULL) &&
            (flagType == INTNULL) &&
            (flagPassword == CHARNULL) &&
            (fname == CHARNULL) &&
            (surname == CHARNULL) &&
            (affiliation == CHARNULL) &&
            (address == CHARNULL)) {
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
            passwordError +
            userTypeError +
            flagTypeError +
            flagPasswordError +
            fnameError +
            surnameError +
            affiliationError +
            addressError;
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
     * Gets the errorcode for the password instance variable
     * @return errorcode (int)
     */
    public int getPasswordError() {
        return passwordError;
    } // method getPasswordError

    /**
     * Gets the errorMessage for the password instance variable
     * @return errorMessage (String)
     */
    public String getPasswordErrorMessage() {
        return passwordErrorMessage;
    } // method getPasswordErrorMessage


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


    /**
     * Gets the errorcode for the flagType instance variable
     * @return errorcode (int)
     */
    public int getFlagTypeError() {
        return flagTypeError;
    } // method getFlagTypeError

    /**
     * Gets the errorMessage for the flagType instance variable
     * @return errorMessage (String)
     */
    public String getFlagTypeErrorMessage() {
        return flagTypeErrorMessage;
    } // method getFlagTypeErrorMessage


    /**
     * Gets the errorcode for the flagPassword instance variable
     * @return errorcode (int)
     */
    public int getFlagPasswordError() {
        return flagPasswordError;
    } // method getFlagPasswordError

    /**
     * Gets the errorMessage for the flagPassword instance variable
     * @return errorMessage (String)
     */
    public String getFlagPasswordErrorMessage() {
        return flagPasswordErrorMessage;
    } // method getFlagPasswordErrorMessage


    /**
     * Gets the errorcode for the fname instance variable
     * @return errorcode (int)
     */
    public int getFnameError() {
        return fnameError;
    } // method getFnameError

    /**
     * Gets the errorMessage for the fname instance variable
     * @return errorMessage (String)
     */
    public String getFnameErrorMessage() {
        return fnameErrorMessage;
    } // method getFnameErrorMessage


    /**
     * Gets the errorcode for the surname instance variable
     * @return errorcode (int)
     */
    public int getSurnameError() {
        return surnameError;
    } // method getSurnameError

    /**
     * Gets the errorMessage for the surname instance variable
     * @return errorMessage (String)
     */
    public String getSurnameErrorMessage() {
        return surnameErrorMessage;
    } // method getSurnameErrorMessage


    /**
     * Gets the errorcode for the affiliation instance variable
     * @return errorcode (int)
     */
    public int getAffiliationError() {
        return affiliationError;
    } // method getAffiliationError

    /**
     * Gets the errorMessage for the affiliation instance variable
     * @return errorMessage (String)
     */
    public String getAffiliationErrorMessage() {
        return affiliationErrorMessage;
    } // method getAffiliationErrorMessage


    /**
     * Gets the errorcode for the address instance variable
     * @return errorcode (int)
     */
    public int getAddressError() {
        return addressError;
    } // method getAddressError

    /**
     * Gets the errorMessage for the address instance variable
     * @return errorMessage (String)
     */
    public String getAddressErrorMessage() {
        return addressErrorMessage;
    } // method getAddressErrorMessage


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of SadUsers. e.g.<pre>
     * SadUsers sadUsers = new SadUsers(<val1>);
     * SadUsers sadUsersArray[] = sadUsers.get();</pre>
     * will get the SadUsers record where userid = <val1>.
     * @return Array of SadUsers (SadUsers[])
     */
    public SadUsers[] get() {
        return doGet(db.select(TABLE, createWhere()));
    } // method get

    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * SadUsers sadUsersArray[] = 
     *     new SadUsers().get(SadUsers.USERID+"=<val1>");</pre>
     * will get the SadUsers record where userid = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of SadUsers (SadUsers[])
     */
    public SadUsers[] get (String where) {
        return doGet(db.select(TABLE, where));
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * SadUsers sadUsersArray[] = 
     *     new SadUsers().get("1=1",sadUsers.USERID);</pre>
     * will get the all the SadUsers records, and order them by userid.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of SadUsers (SadUsers[])
     */
    public SadUsers[] get (String where, String order) {
        return doGet(db.select("*", TABLE, where, order));
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = SadUsers.USERID,SadUsers.PASSWORD;
     * String where = SadUsers.USERID + "=<val1";
     * String order = SadUsers.USERID;
     * SadUsers sadUsersArray[] = 
     *     new SadUsers().get(columns, where, order);</pre>
     * will get the userid and password colums of all SadUsers records,
     * where userid = <val1>, and order them by userid.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of SadUsers (SadUsers[])
     */
    public SadUsers[] get (String fields, String where, String order) {
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
     * and transforms it into an array of SadUsers.
     * @param  result  (Vector (rows) of Vectors (columns)).
     */
    private SadUsers[] doGet(Vector result) {
        if (dbg) System.out.println ("vector size = " + result.size());
        int useridCol       = db.getColNumber(USERID);
        int passwordCol     = db.getColNumber(PASSWORD);
        int userTypeCol     = db.getColNumber(USER_TYPE);
        int flagTypeCol     = db.getColNumber(FLAG_TYPE);
        int flagPasswordCol = db.getColNumber(FLAG_PASSWORD);
        int fnameCol        = db.getColNumber(FNAME);
        int surnameCol      = db.getColNumber(SURNAME);
        int affiliationCol  = db.getColNumber(AFFILIATION);
        int addressCol      = db.getColNumber(ADDRESS);
        SadUsers[] cArray = new SadUsers[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector row = (Vector) result.elementAt(i);
            cArray[i] = new SadUsers();
            if (useridCol != -1)
                cArray[i].setUserid      ((String) row.elementAt(useridCol));
            if (passwordCol != -1)
                cArray[i].setPassword    ((String) row.elementAt(passwordCol));
            if (userTypeCol != -1)
                cArray[i].setUserType    ((String) row.elementAt(userTypeCol));
            if (flagTypeCol != -1)
                cArray[i].setFlagType    ((String) row.elementAt(flagTypeCol));
            if (flagPasswordCol != -1)
                cArray[i].setFlagPassword((String) row.elementAt(flagPasswordCol));
            if (fnameCol != -1)
                cArray[i].setFname       ((String) row.elementAt(fnameCol));
            if (surnameCol != -1)
                cArray[i].setSurname     ((String) row.elementAt(surnameCol));
            if (affiliationCol != -1)
                cArray[i].setAffiliation ((String) row.elementAt(affiliationCol));
            if (addressCol != -1)
                cArray[i].setAddress     ((String) row.elementAt(addressCol));
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
     *     new SadUsers(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>,
     *         <val9>).put();</pre>
     * will insert a record with:
     *     userid       = <val1>,
     *     password     = <val2>,
     *     userType     = <val3>,
     *     flagType     = <val4>,
     *     flagPassword = <val5>,
     *     fname        = <val6>,
     *     surname      = <val7>,
     *     affiliation  = <val8>,
     *     address      = <val9>.
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
     * Boolean success = new SadUsers(
     *     Tables.CHARNULL,
     *     <val2>,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where password = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return db.delete (TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * SadUsers sadUsers = new SadUsers();
     * success = sadUsers.del(SadUsers.USERID+"=<val1>");</pre>
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
     * update are taken from the SadUsers argument, .e.g.<pre>
     * Boolean success
     * SadUsers updSadUsers = new SadUsers();
     * updSadUsers.setPassword(<val2>);
     * SadUsers whereSadUsers = new SadUsers(<val1>);
     * success = whereSadUsers.upd(updSadUsers);</pre>
     * will update Password to <val2> for all records where 
     * userid = <val1>.
     * @param sadUsers  A SadUsers variable in which the fields are to be used for 
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(SadUsers sadUsers) {
        return db.update (TABLE, createColVals(sadUsers), createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * SadUsers updSadUsers = new SadUsers();
     * updSadUsers.setPassword(<val2>);
     * SadUsers whereSadUsers = new SadUsers();
     * success = whereSadUsers.upd(
     *     updSadUsers, SadUsers.USERID+"=<val1>");</pre>
     * will update Password to <val2> for all records where 
     * userid = <val1>.
     * @param  updSadUsers  A SadUsers variable in which the fields are to be used for 
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(SadUsers sadUsers, String where) {
        return db.update (TABLE, createColVals(sadUsers), where);
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
        if (getPassword() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + PASSWORD + "='" + getPassword() + "'";
        } // if getPassword
        if (getUserType() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + USER_TYPE + "=" + getUserType("");
        } // if getUserType
        if (getFlagType() != INTNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FLAG_TYPE + "=" + getFlagType("");
        } // if getFlagType
        if (getFlagPassword() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FLAG_PASSWORD + "='" + getFlagPassword() + "'";
        } // if getFlagPassword
        if (getFname() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + FNAME + "='" + getFname() + "'";
        } // if getFname
        if (getSurname() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + SURNAME + "='" + getSurname() + "'";
        } // if getSurname
        if (getAffiliation() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + AFFILIATION + "='" + getAffiliation() + "'";
        } // if getAffiliation
        if (getAddress() != CHARNULL) {
            if (!where.equals("")) {
                where = where + " and ";
            } // if where
            where = where + ADDRESS + "='" + getAddress() + "'";
        } // if getAddress
        if (dbg) System.out.println ("<br>where = " + where);   // debug
        return (!where.equals("") ? where : null);
    } // method createWhere


    /**
     * Creates the column-value pairs for the update statement from the current 
     * values of the instance variables.
     * @return  colVals (String)
     */
    private String createColVals(SadUsers aVar) {
        String colVals = "";
        if (aVar.getUserid() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += USERID +"=";
            colVals += (aVar.getUserid().equals(CHARNULL2) ?
                "null" : "'" + aVar.getUserid() + "'");
        } // if aVar.getUserid
        if (aVar.getPassword() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += PASSWORD +"=";
            colVals += (aVar.getPassword().equals(CHARNULL2) ?
                "null" : "'" + aVar.getPassword() + "'");
        } // if aVar.getPassword
        if (aVar.getUserType() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += USER_TYPE +"=";
            colVals += (aVar.getUserType() == INTNULL2 ?
                "null" : aVar.getUserType(""));
        } // if aVar.getUserType
        if (aVar.getFlagType() != INTNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FLAG_TYPE +"=";
            colVals += (aVar.getFlagType() == INTNULL2 ?
                "null" : aVar.getFlagType(""));
        } // if aVar.getFlagType
        if (aVar.getFlagPassword() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FLAG_PASSWORD +"=";
            colVals += (aVar.getFlagPassword().equals(CHARNULL2) ?
                "null" : "'" + aVar.getFlagPassword() + "'");
        } // if aVar.getFlagPassword
        if (aVar.getFname() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += FNAME +"=";
            colVals += (aVar.getFname().equals(CHARNULL2) ?
                "null" : "'" + aVar.getFname() + "'");
        } // if aVar.getFname
        if (aVar.getSurname() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += SURNAME +"=";
            colVals += (aVar.getSurname().equals(CHARNULL2) ?
                "null" : "'" + aVar.getSurname() + "'");
        } // if aVar.getSurname
        if (aVar.getAffiliation() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += AFFILIATION +"=";
            colVals += (aVar.getAffiliation().equals(CHARNULL2) ?
                "null" : "'" + aVar.getAffiliation() + "'");
        } // if aVar.getAffiliation
        if (aVar.getAddress() != CHARNULL) {
            if (!colVals.equals("")) {
                colVals += ",";
            } // if colVals
            colVals += ADDRESS +"=";
            colVals += (aVar.getAddress().equals(CHARNULL2) ?
                "null" : "'" + aVar.getAddress() + "'");
        } // if aVar.getAddress
        if (dbg) System.out.println ("<br>colVals = " + colVals);   // debug
        return colVals;
    } // method createColVals


    /**
     * Creates the column list from the instance variables.
     * @return  columns (String)
     */
    private String createColumns() {
        String columns = USERID;
        if (getPassword() != CHARNULL) {
            columns = columns + "," + PASSWORD;
        } // if getPassword
        if (getUserType() != INTNULL) {
            columns = columns + "," + USER_TYPE;
        } // if getUserType
        if (getFlagType() != INTNULL) {
            columns = columns + "," + FLAG_TYPE;
        } // if getFlagType
        if (getFlagPassword() != CHARNULL) {
            columns = columns + "," + FLAG_PASSWORD;
        } // if getFlagPassword
        if (getFname() != CHARNULL) {
            columns = columns + "," + FNAME;
        } // if getFname
        if (getSurname() != CHARNULL) {
            columns = columns + "," + SURNAME;
        } // if getSurname
        if (getAffiliation() != CHARNULL) {
            columns = columns + "," + AFFILIATION;
        } // if getAffiliation
        if (getAddress() != CHARNULL) {
            columns = columns + "," + ADDRESS;
        } // if getAddress
        if (dbg) System.out.println ("<br>columns = " + columns);   // debug
        return columns;
    } // method createColumns


    /**
     * Creates the values list from the instance variables.
     * @return  values (String)
     */
    private String createValues() {
        String values  = "'" + getUserid() + "'";
        if (getPassword() != CHARNULL) {
            values  = values  + ",'" + getPassword() + "'";
        } // if getPassword
        if (getUserType() != INTNULL) {
            values  = values  + "," + getUserType("");
        } // if getUserType
        if (getFlagType() != INTNULL) {
            values  = values  + "," + getFlagType("");
        } // if getFlagType
        if (getFlagPassword() != CHARNULL) {
            values  = values  + ",'" + getFlagPassword() + "'";
        } // if getFlagPassword
        if (getFname() != CHARNULL) {
            values  = values  + ",'" + getFname() + "'";
        } // if getFname
        if (getSurname() != CHARNULL) {
            values  = values  + ",'" + getSurname() + "'";
        } // if getSurname
        if (getAffiliation() != CHARNULL) {
            values  = values  + ",'" + getAffiliation() + "'";
        } // if getAffiliation
        if (getAddress() != CHARNULL) {
            values  = values  + ",'" + getAddress() + "'";
        } // if getAddress
        if (dbg) System.out.println ("<br>values = " + values);   // debug
        return values;
    } // method createValues


    /**
     * Returns the String version of this class.
     * @return  toString (String)
     */
    public String toString() {
        return
            getUserid("")       + "|" +
            getPassword("")     + "|" +
            getUserType("")     + "|" +
            getFlagType("")     + "|" +
            getFlagPassword("") + "|" +
            getFname("")        + "|" +
            getSurname("")      + "|" +
            getAffiliation("")  + "|" +
            getAddress("")      + "|";
    } // method toString

} // class SadUsers
