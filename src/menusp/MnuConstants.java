
package menusp;

/**
 * Contains all the constants used by the MnuMenu system
 *
 * @author  020625  SIT Group
 * @version
 * 020625 - Ursula von St Ange - created class<br>
 */
public class MnuConstants {

    //boolean dbg = true;
    boolean dbg = false;

    //parameters
    /** the parameter for the menu version: menu/form */
    static final public String VERSION     = "pversion";
    /** the parameter for the menu-no of the menu to be created */
    static final public String MENUNO      = "pmenuno";
    /** the parameter for the type of user to use the menu: 1/2 */
    static final public String USERTYPE    = "put";
    /** the parameter for the sessioncode - used in the SADCO applications */
    static final public String SESSIONCODE = "psc";

    // 'local' paramaters
    /** the parameter for the Oracle schema owner's userid */
    static final public String OWNER       = "powner";
    /** the parameter for the title to appear in the top bar */
    static final public String TITLE       = "pttitle";
    /** the parameter for the application name */
    static final public String APPLICATION = "papplication";
    /** the parameter for the date the application was last updated */
    static final public String DATE        = "pdate";

    //parameters for sending to called program
    /** the parameter for the calling application type: if blank, then the
     *caller was a menu application */
    static final public String CALLTYPE    = "pcalltype";
    /** the parameter for the menu version: to be used by non-frame applications */
    static final public String MVERSION    = "pmversion";

} // class MnuConstants









