//+------------------------------------------------+----------------+--------+
//| CLASS: ... LoginConfirmFrame                   | JAVA PROGRAM   | 001106 |
package sadco;

import oracle.html.*;

/**
 * Authenticates user, creates a LogSession record if successful, and creates
 * a page with a banner, a message, and either a login form or a continue button
 *
 * Sadco
 * screen.equals("security")
 * version.equals("login")
 *
 * @author  001106 - SIT Group
 * @version
 * 001106 - Ursula von St Ange - create class<br>
 * 020527 - Ursula von St Ange - update for non-frame menus<br>
 * 020527 - Ursula von St Ange - update for exception catching<br>
 */
public class LoginConfirmFrame extends LoginFrame{

    //common.edmCommon ec = new common.edmCommon(); - inherited from LoginFrame

    boolean dbg = false;
    //boolean dbg = true;
    String thisClass = this.getClass().getName();

    /** some common functions */
    //common.edmCommon ec = new common.edmCommon(); - inherited from LoginFrame
    /** url parameters names & applications */
    //SadConstants sc = new SadConstants(); - inherited from LoginFrame

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoginConfirmFrame (String args[], HtmlHead hd)  {

        try {
            LoginConfirmFrameActual(args, hd);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoginConfirmFrame constructor


   /**
     * Also pass along HtmlHead to add the metatags for refreshing in case
     * the logon is successfull
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoginConfirmFrameActual(String args[], HtmlHead hd)  {

        String userid = ec.getArgument(args, sc.USERID).toLowerCase();
        String password = ec.getArgument(args, sc.PASSWORD).toLowerCase();
        String sessionCode = "";
        /*****************/
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        /*****************/

        String message = "";

        // check if user on db
        if (dbg) System.out.println("<br>" + thisClass +
            ": before SadUsers");
        SadUsers user = new SadUsers();
        user.setUserid(userid);
        SadUsers users[] = user.get();

        // authenticate user
        boolean userOK = true;
        if (users.length == 0) {
            // user does not exist
            userOK = false;
            if (dbg) System.out.println("<br>" + thisClass +
                ": user does not exist" + userOK);
            message = "<font color = red>User <b>" + userid +
                "</b> does not exist - please re-enter</font>";
        } else if (!password.equals(users[0].getPassword())) {
         // user entered wrong password
            userOK = false;
            if (dbg) System.out.println("<br>" + thisClass +
                ": wrong password" + userOK);
            message = "<font color = red>Invalid password for user <b>" + userid +
                "</b> - please re-enter</font>";
        } else {
            // create new session entry
            LogSessions session = new LogSessions();
            session.setUserid(userid);
            session.setDateTime(new java.util.Date());
            boolean success = session.put();
            sessionCode = session.getCode("");
            if (dbg) System.out.println("<br>" + thisClass + ": date = " +
                session.getDateTime(""));
            if (dbg) System.out.println("<br>" + thisClass + ": sessionCode = " +
                sessionCode);
            message = "<font color = green>User <b>" + userid +
                "</b> successfully logged on</font>";
        } // if users.length ...
        if (dbg) System.out.println("<br>" + thisClass + ": userOK = " + userOK);

        // create the page
        if (userOK) {
            // want to immediately move to the SadcoMenu if successfull
            /*****************/
            //if ("".equals(menuNo)) {
            //    hd.addMetaInfo(new MetaInfo("refresh",
            //        "0;URL=" + sc.MENU_APP + "?" +
            //        sc.SCREEN + "=panel&" +
            //        sc.USERTYPE + "=" + users[0].getUserType("") + "&" +
            //        sc.SESSIONCODE + "=" + sessionCode));
            //} else {
            /*****************/
                // keep this one
                hd.addMetaInfo(new MetaInfo("refresh",
                    "0;URL=" + sc.MENU_APP + "?" +
                    sc.VERSION + "=menu&" +
                    sc.USERTYPE + "=" + users[0].getUserType("") + "&" +
                    sc.SESSIONCODE + "=" + sessionCode));
            /*****************/
            //} // if ("".equals(menuNo)
            /*****************/
        } else {
            this.addItem(createBannerTable().setCenter());
            this.addItem(new SimpleItem(message).setCenter());
            this.addItem(createLoginForm(args).setCenter());
        } // if userOK

    } // constructor with args

} // class LoginConfirmFrame