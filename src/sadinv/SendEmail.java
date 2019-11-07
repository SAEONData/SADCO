package sadinv;

import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import oracle.html.CompoundItem;
import oracle.html.Form;
import oracle.html.HtmlBody;
import oracle.html.HtmlHead;
import oracle.html.HtmlPage;
import oracle.html.SimpleItem;
import oracle.html.Submit;
import sadco.SadConstants;

/**
 * This class sends an email to the user.  It can be called from either Startup, Register.
 *
 * SadInv
 * pscreen = SendEmail
 * pversion = startup / register / extract / check
 *
 * @author 20091120 - SIT Group.
 * @version
 * 20091120 - Ursula von St Ange - create program                       <br>
 * 20111114 - Ursula von St Ange - add code to extract hydro / area (ub01)<br>
 */
public class SendEmail extends CompoundItem {

    /** For debugging code: true/false */
    static boolean dbg = false;
    //static boolean dbg = true;
    static boolean dbg2 = false;
    //static boolean dbg2 = true;
    RandomAccessFile dbgFile = null;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    static private Connection conn = null;
    static private java.sql.Statement stmt = null;
    static private ResultSet rset = null;

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();
    static SadConstants sc2 = new SadConstants();

    // parameter variables
    String version = "";
    String version2 = "";
    String email = "";
    // for extract
    String dataType = "";
    String dataTypeName = "";
    String surveyId = "";
    String method = "";
    String sessionCode = "";
    int    extrType = 0;    // hydro
    String depthCode = "";  // currents
    String year = "";       // waves and weather
    String latNorth = "";   // vos, hydro area
    String latSouth = "";   // vos, hydro area
    String lonWest = "";    // vos, hydro area
    String lonEast = "";    // vos, hydro area
    String hemNorth = "S";   // vos, hydro area
    String hemSouth = "S";   // vos, hydro area
    String hemWest = "E";    // vos, hydro area
    String hemEast = "E";    // vos, hydro area
    String startDate = "";  // vos, hydro area
    String endDate = "";    // vos, hydro area
    String dataFileName = "";
    String userType = "";

    // work variables for users
    String firstName = "";
    String surname = "";
    String affiliation = "";
    String occupation = "";
    String address = "";
    String password = "";
    String emailToUse = "";
    String firstNameToUse = "";
    String emailName = "";
    String extension = "";
    String subject = "";

    // work variables for files
    String rootPath = "";
    String fileName = "";
    RandomAccessFile file = null;

    // work variable for the email command
    String command = "";


    /**
     * main constructor
     */
    public SendEmail (String args[]) {

        // get the argument values
        version      = ec.getArgument(args,sc.VERSION);
        version2      = ec.getArgument(args,sc.VERSION2);
        email        = ec.getArgument(args,sc.EMAIL);
        // for extract
        dataType     = ec.getArgument(args,sc.DATATYPE);
        dataTypeName = ec.getArgument(args,sc.DATANAME);
        surveyId     = ec.getArgument(args,sc.SURVEYID);
        method       = ec.getArgument(args,sc.ACTION);
        sessionCode  = ec.getArgument(args,sc.PSC);

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass, sessionCode);
        String sql = "";

        // get the correct PATH NAME
        if (ec.getHost().startsWith(sc2.HOST)) {
            rootPath = sc2.HOSTDIR;
        } else {
            rootPath = sc2.LOCALDIR;
        } // if host
        if (dbg) System.out.println("<br>" + thisClass + " rootPath = " + rootPath);

        if (dbg) {
            fileName = rootPath + "emails/SendEmail.dbg";
            ec.deleteOldFile(fileName);
            dbgFile = ec.openFile(fileName, "rw");
            ec.writeFileLine(dbgFile, "rootPath = " + rootPath);
        } // if (dbg)

        // hydro
        if ("1".equals(dataType)) {
            extrType = Integer.parseInt(ec.getArgument(args,sc.EXTRTYPE));
        } // if ("1".equals(dataType))
        // currents
        depthCode    = ec.getArgument(args,sc.DEPTHCODE);
        // waves and weather
        year         = ec.getArgument(args,sc.YEAR);
        // vos, hydro area
        latNorth = ec.getArgument(args,sc.LATITUDENORTH);
        latSouth = ec.getArgument(args,sc.LATITUDESOUTH);
        lonWest = ec.getArgument(args,sc.LONGITUDEWEST);
        lonEast = ec.getArgument(args,sc.LONGITUDEEAST);
        startDate = ec.getArgument(args,sc.STARTDATE);
        endDate = ec.getArgument(args,sc.ENDDATE);

        // correct hemisphere values for N and W
        //if ("7".equals(dataType)) {
        if (!"".equals(latNorth)) {                                     //ub01
            if (latNorth.indexOf("-") > -1) {
                hemNorth = "N";
                latNorth = latNorth.substring(1);
            } // if (latNorth.indexOf("-") > -1)
            if (latSouth.indexOf("-") > -1) {
                hemNorth = "N";
                latSouth = latSouth.substring(1);
            } // if (latNorth.indexOf("-") > -1)
            if (lonWest.indexOf("-") > -1) {
                hemWest = "W";
                lonWest = lonWest.substring(1);
            } // if (latNorth.indexOf("-") > -1)
            if (lonEast.indexOf("-") > -1) {
                hemEast = "W";
                lonEast = lonEast.substring(1);
            } // if (latNorth.indexOf("-") > -1)
        } // if ("7".equals(dataType))

        // for online request
        dataFileName = ec.getArgument(args,sc2.FILENAME);
        userType = ec.getArgument(args,sc2.USERTYPE);
        if (dbg) ec.writeFileLine(dbgFile, "method = *" + method + "*");
        if (dbg) ec.writeFileLine(dbgFile, "sc.ONLINE = *" + sc.ONLINE + "*");

        if (dbg2) {
            //version=sc.STARTUP;
            //version = sc.REGISTER;
            version = sc.EXTRACT;
            email = "data@ocean.gov.za";
            method = sc.ONLINE;
            version2 = sc.CHECK;
            dataFileName = "test";
            sessionCode = "1832";
            userType = "0";
            //firstName = "Ursula";
            //surname = "von St Ange";
            //affiliation = "SADCO / DEA";
            //address = "PO Box 320, Stellenbosch, 7599";
            //email = "sadreq";
            //password = "ter91qes";
        } // if (dbg2)

        // get the file name
        if (sc.ONLINE.equals(method)) {
            // get the email name
            StringTokenizer t = new StringTokenizer(email,"@");
            emailName = t.nextToken();
            // make sure there is not . in the email name
            t = new StringTokenizer(emailName,".");
            emailName = t.nextToken();

            // get the extension
            if ("1".equals(dataType)) {
                extension = sc.HYDRO_EXT[extrType];
            } else if ("2".equals(dataType) || "6".equals(dataType)) {
                extension = ".cur";
            } else if ("3".equals(dataType)) {
                extension = ".wet";
            } else if ("4".equals(dataType)) {
                extension = ".wav";
            } else if ("7".equals(dataType)) {
                extension = ".dbv";
            } // if ("1".equals(dataType))

            dataFileName = emailName + "_" + dataFileName + extension;
            if (dbg) System.out.println("<br>" + thisClass + " dataType = " + dataType);
            if (dbg) System.out.println("<br>" + thisClass + " dataFileName = " + dataFileName);
            if (dbg) ec.writeFileLine(dbgFile, "dataType = " + dataType);
            if (dbg) ec.writeFileLine(dbgFile, "dataFileName = " + dataFileName);
        } // if (sc.ONLINE.equals(method))

        if (dbg) System.out.println("<br>" + thisClass + " version = " + version);
        if (dbg) System.out.println("<br>" + thisClass + " email = " + email);
        if (dbg) ec.writeFileLine(dbgFile, "version = " + version);
        if (dbg) ec.writeFileLine(dbgFile, "email = " + email);


        // get the users details
        try {
            sql =
                "select password, fname, surname, affiliation, occupation, address " +
                "from sad_users " +
                "where userid = '" + email + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                //password = checkNull(rset.getString(1));
                firstName = checkNull(rset.getString(2));
                surname = checkNull(rset.getString(3));
                affiliation = checkNull(rset.getString(4));
                occupation = checkNull(rset.getString(5));
                address = checkNull(rset.getString(6));
            } // while (rset.next())
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;
        } catch (SQLException e) { }

        // for extractions change the email-address and first name
        emailToUse = email;
        firstNameToUse = firstName;
        //String dataTypeName = "";
        if (sc.EXTRACT.equals(version) && sc.OFFLINE.equals(method)) {
            //emailToUse = " kyle@saeon.ac.za ";
            emailToUse = "data@ocean.gov.za";
            firstNameToUse = "Data";
        } else if (sc.REGISTER.equals(version) || (sc.CHECK.equals(version2))){
            //emailToUse = " -b \"mgrundli@csir.co.za uvstange@csir.co.za\" " + emailToUse;
            //emailToUse += " kyle@saeon.ac.za ";
            emailToUse = "data@ocean.gov.za";
        } // if (sc.REQUEST.equals(version))
        if (dbg) ec.writeFileLine(dbgFile, "emailToUse = " + emailToUse);

        // get the email name
        StringTokenizer t = new StringTokenizer(email,"@");
        emailName = t.nextToken();

        // create a textfile with the body of the email
        fileName = rootPath + "emails/" + emailName;
        ec.deleteOldFile(fileName);
        file = ec.openFile(fileName, "rw");

        ec.writeFileLine(file, "Dear " + firstNameToUse);
        ec.writeFileLine(file, "");
        if (sc.STARTUP.equals(version)) {
            //ec.writeFileLine(file, "Your password is " + password);
            //subject = "SADCO_Password";
        } else if (sc.REGISTER.equals(version)){
            subject = "SADCO_Registration";
            //if (dbg) System.out.println("<br>" + thisClass + " 2password = " + password);
            if (dbg) System.out.println("<br>" + thisClass + " 2firstName = " + firstName);
            if (dbg) System.out.println("<br>" + thisClass + " 2surname = " + surname);
            if (dbg) System.out.println("<br>" + thisClass + " 2affiliation = " + affiliation);
            if (dbg) System.out.println("<br>" + thisClass + " 2occupation = " + occupation);
            if (dbg) System.out.println("<br>" + thisClass + " 2address = " + address);
            ec.writeFileLine(file, "Welcome to the SADCO inventory");
            ec.writeFileLine(file, "");
            ec.writeFileLine(file, "You have been registered as follows:");
            ec.writeFileLine(file, "");
            ec.writeFileLine(file, "User ID: " + email);
            ec.writeFileLine(file, "First name: " + firstName);
            ec.writeFileLine(file, "Surname: " + surname);
            ec.writeFileLine(file, "Affiliation: " + affiliation);
            ec.writeFileLine(file, "Occupation: " + occupation);
            ec.writeFileLine(file, "Postal adress: " + address);
            //ec.writeFileLine(file, "Password: " + password);
        } else {
            if (sc.CHECK.equals(version2)) {
                ec.writeFileLine(file, "There is might be a problem with the following online request");
                ec.writeFileLine(file, "The SADCO staff will be alerted, and will contact you once it ");
                ec.writeFileLine(file, "has been resolved.");
                subject = "SADCO_Online_Request_Problem";
            } else if (sc.OFFLINE.equals(method)) {
                ec.writeFileLine(file, "The following offline request has been received");
                subject = "SADCO_Offline_Request";
            } else {
                ec.writeFileLine(file, "The following online request has completed");
                subject = "SADCO_Online_Request";
            } // if (sc.OFFLINE.equals(method))
            ec.writeFileLine(file, "");
            if (!"7".equals(dataType)) {
                ec.writeFileLine(file, "SurveyId: " + surveyId);
            } // if (!"7".equals(dataType))
            ec.writeFileLine(file, "Data Type: " + dataTypeName);
            if ("1".equals(dataType)) {
                ec.writeFileLine(file, "Extraction Type: " + sc.HYDRO_EXTR[extrType]);
            } else if ("2".equals(dataType) || "6".equals(dataType)) {
                ec.writeFileLine(file, "Depth Code: " + depthCode);
            } else if ("3".equals(dataType) || "4".equals(dataType)) {
                ec.writeFileLine(file, "Year: " + year);
            } // if ("1".equals(dataType))
            if (!"".equals(latNorth)) {                                     //ub01
                ec.writeFileLine(file, "Latitude: " + latNorth + sc.DEG + hemNorth +
                    " to " + latSouth + sc.DEG + hemSouth);
                ec.writeFileLine(file, "Longitude: " + lonWest + sc.DEG + hemWest +
                    " to " + lonEast + sc.DEG + hemEast);
                ec.writeFileLine(file, "Period: " + startDate + " to " + endDate);
            } // if (!"".equals(latNorth))                                  //ub01

            ec.writeFileLine(file, "");
            if (sc.OFFLINE.equals(method) || sc.CHECK.equals(version2)) {
                ec.writeFileLine(file, "email: " + email);
                ec.writeFileLine(file, "First name: " + firstName);
                ec.writeFileLine(file, "Surname: " + surname);
                ec.writeFileLine(file, "Affiliation: " + affiliation);
                ec.writeFileLine(file, "Occupation: " + occupation);
                ec.writeFileLine(file, "Postal adress: " + address);
            } else {
                String URLStr = sc2.DATA_URL + "inv_user/" + dataFileName;
                ec.writeFileLine(file, "The file can be downloaded at the following URL");
                ec.writeFileLine(file, URLStr);

                // do products?
                if (".dbm".equals(extension)) {
                    URLStr = SadConstants.LIVE ? "http://sadcodata.ocean.gov.za/sadco1/" : "http://sadcodata.int.ocean.gov.za/sadco1/" +
                        sc2.MRN_APP + "?" +
                        sc2.SCREEN + "=product&" +
                        sc2.VERSION + "=getfile&" +
                        sc2.USERTYPE + "=" + userType + "&" +
                        sc2.SESSIONCODE + "=" + sessionCode + "&" +
                        menusp.MnuConstants.MENUNO + "=74&" +
                        menusp.MnuConstants.MVERSION + "=form";

                    ec.writeFileLine(file, "");
                    ec.writeFileLine(file, "Use the following link to run a " +
                        "product on your extracted file");
                    ec.writeFileLine(file, URLStr);
                } // if (".dbm".equals(extension))
                if (".dbv".equals(extension)) {
                    URLStr = sadco.SadConstants.LIVE ? "http://sadcodata.ocean.gov.za/sadco1/" : "http://sadcodata.int.ocean.gov.za/sadco1/" +
                        sc2.VOS_APP + "?" +
                        sc2.SCREEN + "=product&" +
                        sc2.VERSION + "=getfile&" +
                        sc2.USERTYPE + "=" + userType + "&" +
                        sc2.SESSIONCODE + "=" + sessionCode + "&" +
                        menusp.MnuConstants.MENUNO + "=56&" +
                        menusp.MnuConstants.MVERSION + "=form";

                    ec.writeFileLine(file, "");
                    ec.writeFileLine(file, "Use the following link to run a " +
                        "product on your extracted file");
                    ec.writeFileLine(file, URLStr);
                } // if (".dbm".equals(extension))

            } // if (sc.OFFLINE.equals(method))
        } // if (sc.STARTUP.equals(version))
        ec.writeFileLine(file, "");
        ec.writeFileLine(file, "Reminder of data use agreement: ");
        ec.writeFileLine(file, "The extracted data will be used for research, non-commercial purposes only,");
        ec.writeFileLine(file, "and will not be passed to a 3rd party.");
        ec.writeFileLine(file, "The following acknowledgement should be used in any products:");
        ec.writeFileLine(file, "'The data has been supplied by the Southern African Data Centre for Oceanography'.");
        ec.writeFileLine(file, "SADCO kindly requests a copy of any product (report, publication) where the data is used");
        ec.writeFileLine(file, "(for address please see 'Contacts' on SADCO web site (sadco.ocean.gov.za)");

        ec.writeFileLine(file, "");
        ec.writeFileLine(file, "Regards");
        ec.writeFileLine(file, "SADCO Administrator");
        ec.closeFile(file);

        // create the command to send the mail
        command = sc.SENDMAIL + " " + fileName + " " + subject + " " + emailToUse;
        if (dbg) System.out.println("<br>" + thisClass + " command = " + command);
        if (dbg) ec.writeFileLine(dbgFile, "command = " + command);

        // change the permissions and submit the job
        if (ec.getHost().startsWith(sc2.HOST)) {
            ec.submitJob("chmod 644 " + fileName);
            ec.submitJob(command);
            if (dbg) System.out.println("job submitted");
        } // if host

        // create the user feedback
        if (sc.STARTUP.equals(version)) {
            // create the form with the button  to close the window
            // create the login form
            Form form = new Form("","");
            form.addItem(new Submit("\" onclick=\"window.close()","Close Window"));

            // create the container to hook everything on to
            this.addItem(SimpleItem.LineBreak)
                .addItem("An email containing your password has been sent to you. " +
                "You should receive it shortly.")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem(form);
        } // if (sc.STARTUP.equals(version))

        if (dbg) ec.closeFile(dbgFile);

        // make sure everything is closed and null
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            } // if (stmt != null)
            if (rset != null) {
                rset.close();
                rset = null;
            } // if (rset != null)
            if (conn != null) {
                conn.close();
                conn = null;
            } // if (conn != null)
        }  catch (SQLException se){}

    } // constructor SendEmail


    /**
     * check if the value is null
     * returns  value / empty string
     */
    String checkNull(String value) {
        return (value != null ? value : "");
    } // checkNull


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("SendEmail local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new SendEmail(args));
            bd
                .setActivatedLinkColor   ("#000088")
                .setFollowedLinkColor    ("#663366")
                .setUnfollowedLinkColor  ("#666666")
                ;
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            ec.processErrorStatic(e, "SendEmail", "Constructor", "");
            // make sure everything is closed and null
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                } // if (stmt != null)
                if (rset != null) {
                    rset.close();
                    rset = null;
                } // if (rset != null)
                if (conn != null) {
                    conn.close();
                    conn = null;
                } // if (conn != null)
            }  catch (SQLException se){}
        } // try-catch

    } // main

} // class SendEmail