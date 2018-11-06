package sadco;

import oracle.html.*;
import java.io.*;

/**
 * Confirms the add/update of a user/dataset.
 *
 * Sadco                                                                    |
 * screen.equals("user")                                                    |
 * version.equals("confirm")                                                   |
 *
 * @author  001107 - SIT Group
 * @version
 * 001107 - Ursula von St Ange - create class<br>
 * 020527 - Ursula von St Ange - update for non-frame menus<br>
 * 020527 - Ursula von St Ange - update for exception catching<br>
 */
public class UserConfirmFrame extends UserFrame{

    /** some common functions */
    //common.edmCommon ec = new common.edmCommon(); - inherited from LoginFrame
    /** url parameters names & applications */
    //sadco.SadConstants sc = new sadco.SadConstants(); - inherited from LoginFrame

    boolean dbg = false;
    //boolean dbg = true;

    /**
     * Default constructor
     */
    public UserConfirmFrame() {
    } // UserConfirmFrame

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
     public UserConfirmFrame (String args[])  {

        try {
            UserConfirmFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // UserConfirmFrame constructor

    /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void UserConfirmFrameActual(String args[])  {

        String userid = ec.getArgument(args, sc.USERID).toLowerCase();
        String oldPassword = ec.getArgument(args, sc.OPASSWORD).toLowerCase();
        String newPassword = ec.getArgument(args, sc.PASSWORD).toLowerCase();
        String newPassword2 = ec.getArgument(args, sc.PASSWORD2).toLowerCase();
        String uType = ec.getArgument(args, sc.NUSERTYPE);
        String fType = ec.getArgument(args, sc.FLAGTYPE);
        String uOldType = ec.getArgument(args, sc.OUSERTYPE);
        String fOldType = ec.getArgument(args, sc.OFLAGTYPE);
        String oldFPassword = ec.getArgument(args, sc.OFPASSWORD).toLowerCase();
        String newFPassword = ec.getArgument(args, sc.FPASSWORD).toLowerCase();
        String newFPassword2 = ec.getArgument(args, sc.FPASSWORD2).toLowerCase();
        String dataset = ec.getArgument(args, sc.DATASET).toLowerCase();
        String action = ec.getArgument(args, sc.ACTION);
        String session = ec.getArgument(args, sc.SESSIONCODE);


        int userType = 0;
        if (!"".equals(uType)) {
            userType = Integer.parseInt(uType.trim());
        } // if (!"".equals(uType))

        int flagType = 0;
        if (!"".equals(fType)) {
            flagType = Integer.parseInt(fType.trim());
        } // if (!"".equals(fType))

        String message = "";
        boolean userOK = true;
        boolean userOK2 = true;

        if (dbg) System.out.println("<br>Userid = " + userid);
        if (dbg) System.out.println("<br>userType = " + userType);
        if (dbg) System.out.println("<br>flagType = " + flagType);

        // get the user id if action = update
        if (action.equals("update")) {
            LogSessions[] sessions =
                new LogSessions(Integer.parseInt(session)).get();
            userid = sessions[0].getUserid();
        } // if (action.equals("update"))


        // check if user on db
        SadUsers user = new SadUsers();
        user.setUserid(userid);
        SadUsers users[] = user.get();


        //-----------------------------//
        // a new user or a new dataset //
        //-----------------------------//
        if (action.equals("new") || "newds".equals(action)) {
            String userTxt = "User";
            String passTxt = "Passwords";
            if ("newds".equals(action)) {
                userTxt = "Dataset";
                passTxt = "Passkeys";
            } // if ("newds".equals(action))
            if (users.length == 0) {
                // user does not exist - OK for new user
                userOK = false;
                message = "";
                if ("".equals(newPassword) || "".equals(newPassword2)) {
                    message = "<font color = red>" + userTxt + " <b>" + userid +
                        "</b> - One of the " + passTxt + " were not supplied - " +
                        "please re-enter</font>";
                } else if (newPassword.equals(newPassword2)) {
                    if (dbg) System.out.println("<br>action=new" +
                        ", users.length=0, passwords same");
                    if (dbg) System.out.println("<br>user = " + user);
                    if (dbg) System.out.println("<br>" + userTxt + " = " + user);
                    // add new user /dataset to the user database
                    user.setPassword(newPassword);
                    user.setUserType(userType);
                    user.setFlagType(flagType);
                    userOK = true;
                } else { // if (newPassword.equals(newPassword2)) {
                    if (dbg) System.out.println("<br>action=new" +
                        ", users.length=0, passwords differ");
                    // new passwords differ
                    userOK = false;
                    message = "<font color = red>" + userTxt + " <b>" + userid +
                        "</b> - " + passTxt +
                        " do not match - please re-enter</font>";
                } // if (newPassword.equals(newPassword2))

                if (flagType == 1) {
                    if ("".equals(newFPassword) || "".equals(newFPassword2)) {
                        userOK = false;
                        if (!"".equals(message)) message += "<br>";
                        message += "<font color = red>" + userTxt + " <b>" +
                            userid + "</b> - One of the Flagged Data Passwords " +
                            "were not supplied - please re-enter</font>";
                    } else if (newFPassword.equals(newFPassword2)) {
                        user.setFlagPassword(newFPassword);
                        //userOk = true;
                    } else {
                        userOK = false;
                        if (!"".equals(message)) message += "<br>";
                        message += "<font color = red>" + userTxt + " <b>" +
                            userid + "</b> - New Flagged Data Passwords " +
                            "do not match - please re-enter</font>";
                    } // if flag password
                } // if flagType
                if (userOK) {
                    boolean success = user.put();
                    message = "<font color = green>" + userTxt + " <b>" +
                        userid + "</b> - Successfully Added</font>";
                } // if (userOk)

            } else { // if (users.length == 0) {
                // user already exists
                message = "<font color = red>" + userTxt + " <b>" + userid +
                    "</b> - Already <b>EXISTS</b> in database - " +
                    "please re-enter</font>";
                userOK = false;
                if (dbg) System.out.println("<br>user already exists" + userOK);
            } // if (users.length == 0) {

        //-------------------------//
        // update by administrator //
        //-------------------------//
        } else if (action.equals("updatesu")) {
            SadUsers updateUser = new SadUsers();
            String message2 = "";

            if ("".equals(newPassword)) {
                userOK = true; // do nothing
            } else if (newPassword.equals(newPassword2)) {
                // update password and maybe user type, flag type
                updateUser.setPassword(newPassword);
                userOK = true;
                if (dbg) System.out.println("<br>wrong password" + userOK);
                message2 = "Password";
            } else { // else if (newPassword.equals(newPassword2)) {
                userOK = false;
                // new passwords differ
                message = "<font color = red>User <b>" + userid +
                    "</b> - New Passwords do not match - please re-enter</font>";
            } // else if (newPassword.equals(newPassword2)) {

            if (!uType.equals(uOldType)) {
                updateUser.setUserType(userType);
                if (!"".equals(message2)) message2 += ", ";
                message2 += "User Type";
            } // if (userType != 0)

            if (!fType.equals(fOldType)) {
                updateUser.setFlagType(flagType);
                if (!"".equals(message2)) message2 += ", ";
                message2 += "Flagged Data Downloads";
            } // if (flagType != 0)

            if (flagType == sc.FTALLOW) {
                if ("".equals(newFPassword)) {
                    userOK2 = true;// do nothing
                } else if (newFPassword.equals(newFPassword2)) {
                    updateUser.setFlagPassword(newFPassword);
                    userOK2 = true;
                    if (!"".equals(message2)) message2 += ", ";
                    message2 += "Flagged Data Password";
                } else { // else if (newPassword.equals(newPassword2)) {
                    userOK2 = false;
                    // new flagged data passwords differ
                    if (!"".equals(message)) message += "<br>";
                    message += "<font color = red>User <b>" + userid +
                        "</b> - New Flagged Data Passwords do not match - " +
                        "please re-enter</font>";
                } // else if (newPassword.equals(newPassword2)) {
            } // if (ftype == sc.FTALLOW)

            if (userOK && userOK2) {
                if ("".equals(message2)) {
                    message = "<font color = blue>User <b>" +
                        userid + "</b> - Nothing was changed</font>";
                } else {
                    user.upd(updateUser);
                    message = "<font color = green>User <b>" +
                        userid + "</b> - Successfully updated<br>(" +
                        message2 + ")</font>";
                } // if ("".equals(message))
            } else {
                // make sure that userOk = false, could have been userOK2 = false
                userOK = false;
            } // if (userOk)

        //-------------------------------------------------------------//
        // update the user's own details, or update a dataset password //
        //-------------------------------------------------------------//
        } else if ("update".equals(action) || "updateds".equals(action)) {
            String userTxt = "User";
            if ("newds".equals(action)) userTxt = "Dataset";
            if (dbg) {
                String test = users[0].getPassword();
                System.out.println("<br>Old password !!!= " + test);
                System.out.println("<br>userid !!!= " + users[0].getUserid());
                System.out.println("<br>New password !!!= " + newPassword);
                System.out.println("<br>New password2 !!!= " + newPassword2);
            } // if (dbg)
            SadUsers updateUser = new SadUsers();
            String message2 = "";
            userOK = false;
            if ("".equals(oldPassword)) {
                // do nothing
            } else if (oldPassword.equals(users[0].getPassword())) {
                // password same as on db
                if (newPassword.equals(newPassword2)) {
                    updateUser.setPassword(newPassword);
                    userOK = true;
                    message2 = "Password";
                } else { // if (newPassword.equals(newPassword2)) {
                    message = "<font color = red>" + userTxt + " <b>" + userid +
                        "</b> - New Passwords do not match - please re-enter" +
                        "</font>";
                } // if (newPassword.equals(newPassword2)) {
            } else { // if (oldPassword.equals(users[0].getPassword())) {
                message = "<font color = red>" + userTxt + " <b>" + userid +
                    "</b> - Old Password does not match DataBase version " +
                    "- please re-enter</font>";
            } // if ("".equals(oldPassword))

            // n/a to datasets - don't user userTxt variable
            // do not have to test for flagtype - will only get changes
            // if flagType = 1
            userOK2 = true;
            if ("".equals(oldFPassword)) {
                // do nothing
            } else if (oldFPassword.equals(users[0].getFlagPassword())) {
                // password same as on db
                if (newFPassword.equals(newFPassword2)) {
                    updateUser.setFlagPassword(newFPassword);
                    userOK2 = true;
                    if ("".equals(message2)) message2 += ", ";
                    message2 = "Flagged Data Password";
                } else { // if (newPassword.equals(newPassword2)) {
                    userOK2 = false;
                    if (!"".equals(message)) message += "<br>";
                    message = "<font color = red>User <b>" + userid +
                        "</b> - New Flagged Data Passwords do not match - " +
                        "please re-enter</font>";
                } // if (newPassword.equals(newPassword2)) {
            } else { // if (oldPassword.equals(users[0].getPassword())) {
                userOK2 = false;
                if (!"".equals(message)) message += "<br>";
                message = "<font color = red>User <b>" + userid +
                    "</b> - Old Flagged Data Password does not match " +
                    "DataBase version - please re-enter</font>";
            } // if ("".equals(oldFPassword))

            if (userOK && userOK2) {
                if ("".equals(message2)) {
                    message = "<font color = blue>User <b>" +
                        userid + "</b> - Nothing was changed</font>";
                } else {
                    user.upd(updateUser);
                    message = "<font color = green>User <b>" +
                        userid + "</b> - Successfully updated<br>(" +
                        message2 + ")</font>";
                } // if ("".equals(message))
            } else {
                userOK = false;
            } // if (userOk)

        //-------------------------------//
        // register a user for a dataset //
        //-------------------------------//
        } else if ("newuds".equals(action)) {
            System.err.println("action = newuds");
            SadUserDset userDSet = new SadUserDset(userid, dataset);
            SadUserDset[] test = userDSet.get();
            userOK = false;
            if (test.length == 0) {
                //userDSet.put();
                userOK = userDSet.put();;
                if (userOK) {
                    message = "<font color = green>User <b>" +
                        userid + "</b> - Successfully registerd for Dataset <b>" +
                        dataset + "</b></font>";
                } else {
                    message = "<font color = red>User <b>" + userid +
                        "</b> - Could not be registerd for Dataset <b>" +
                        dataset + "</b></font>";
                } // if (userOK) {
            } else {
                message = "<font color = blue>User <b>" + userid +
                    "</b> - Was already registerd for Dataset <b>" +
                    dataset + "</b></font>";
            } // if (test.length = 0)

        } // if (action.equals(
        if (dbg) System.out.println("<br>userOK = " + userOK);

        // create the page
        this.addItem(printBanner(args));
        if (!userOK) {
            // print Banner
            this.addItem(new SimpleItem(message).setCenter());
            this.addItem(createUserForm(args).setCenter());
            //this.addItem(form.setCenter());
        } else {
            // print Banner
            this.addItem(new SimpleItem(message).setCenter());
        } // if userOK


        // create the user's directory
        if (userOK && action.equals("new")) {

            // get the correct path name - used while debugging
            String rootPath = "";
            //if ("morph".equals(ec.getHost())) {
            //    rootPath = "/oracle/users/sadco/www/data/";
            //} else {
            //    rootPath = "d:/myfiles/java/data/";
            //} // if host

            if (ec.getHost().startsWith(sc.HOST)) {
                rootPath = sc.HOSTDIR;
            } else {
                rootPath = sc.LOCALDIR;
            } // if (ec.getHost().startsWith(sc.HOST)) {


            File newDir = new File(rootPath + userid);
            boolean success = newDir.mkdir();
            if ("morph".equals(ec.getHost())) {
                ec.submitJob("chmod 777 " + rootPath + userid);
            } // if host
        } // if (userOk && action.equals("new"))


    } // UserConfirmFrameActual

} // UserConfirmFrame class
