package sadinv;

//import sadco.LogSessions;
//import sadco.SadUsers;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import oracle.html.*;
import sadco.SadConstants;


/**
 * This class handles the extraction request from the user.  It will either submit the
 * extraction, or send an email to Louise and Marten.
 *
 * SadInv
 * pscreen = Extract
 *
 * @author 20091204 - SIT Group.
 * @version
 * 20091204 - Ursula von St Ange - create program                       <br>
 * 20111114 - Ursula von St Ange - add code to extract hydro / area (ub01)<br>
 */
public class ExtractRequest extends CompoundItem {

    /** For debugging code: true/false */
    static boolean dbg = false;
    //static boolean dbg = true;
    static boolean dbg2 = false;
    //static boolean dbg2 = true;
    static boolean dbg3 = false;
    //static boolean dbg3 = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    static private Connection conn = null;
    static private java.sql.Statement stmt = null;
    static private ResultSet rset = null;

    /** A general purpose class used to ExtractRequest the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();
    static SadConstants sc2 = new SadConstants();

    // parameter variables
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
    String hemNorth = "";   // vos, hydro area
    String hemSouth = "";   // vos, hydro area
    String hemWest = "";    // vos, hydro area
    String hemEast = "";    // vos, hydro area
    String startDate = "";  // vos, hydro area
    String endDate = "";    // vos, hydro area
    String args2[] = new String[1];


    // work variables
    String email = "";
    String emailName = "";
    String userType = "";
    String url = "";
    String fileName = "";
    StringBuffer command = null;
    String application = "";
    String menuNo = "";


    /**
     * main constructor
     */
    public ExtractRequest (String args[]) {

        // get the argument values
        dataType     = ec.getArgument(args,sc.DATATYPE);
        dataTypeName = ec.getArgument(args,sc.DATANAME);
        surveyId     = ec.getArgument(args,sc.SURVEYID);
        method       = ec.getArgument(args,sc.ACTION);
        sessionCode  = ec.getArgument(args,sc.PSC);
        // hydro
        if ("1".equals(dataType) || "-1".equals(dataType)) {            //ub01
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
        hemNorth = ec.getArgument(args,sc.HEMNORTH);
        hemSouth = ec.getArgument(args,sc.HEMSOUTH);
        hemWest = ec.getArgument(args,sc.HEMWEST);
        hemEast = ec.getArgument(args,sc.HEMEAST);
        startDate = ec.getArgument(args,sc.STARTDATE);
        endDate = ec.getArgument(args,sc.ENDDATE);
        if (dbg) for(int i = 0; i < args.length; i++)
            System.out.println("<br>" + thisClass + " args["+i+"] = " + args[i]);


        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        conn = sc.getConnected(thisClass, sessionCode);
        String sql = "";

        if (dbg2) {
            args2[0] = "platitudenorth=29";
            dataType = "-1";
            dataTypeName = "Hydro";
            surveyId = "";
            method = sc.ONLINE;
            extrType = 0;
            sessionCode = "1832";
            //hemNorth = "N";
            //latNorth = "5";
            surveyId = "";
            sessionCode = "2501";
            latNorth = "29";
            latSouth = "35";
            lonWest = "29";
            lonEast = "35";
            hemNorth = "N";
            hemSouth = "S";
            hemWest = "E";
            hemEast = "E";
            startDate = "2000";
            endDate = "2002";
            //phemnorth=N phemsouth=S phemwest=E phemeast=E
        } // if (dbg2)
        if (dbg) System.out.println("<br>" + thisClass + " dataType = " + dataType);
        if (dbg) System.out.println("<br>" + thisClass + " surveyId = " + surveyId);
        if (dbg) System.out.println("<br>" + thisClass + " method = " + method);
        if (dbg) System.out.println("<br>" + thisClass + " extrType = " + extrType);

        // correct signs for latitude North and longitude West
        if ("7".equals(dataType) || "-1".equals(dataType)) { // vos, hydro area
            if ("N".equals(hemNorth))
                args = changeArgument(args, sc.LATITUDENORTH, "-");
            if ("N".equals(hemSouth))
                args = changeArgument(args, sc.LATITUDESOUTH, "-");
            if ("W".equals(hemWest))
                args = changeArgument(args, sc.LONGITUDEWEST, "-");
            if ("W".equals(hemEast))
                args = changeArgument(args, sc.LONGITUDEEAST, "-");
            if (dbg) System.out.println("<br>" + thisClass +
                " hemNorth = " + hemNorth);
            if (dbg) System.out.println("<br>" + thisClass +
                " latNorth = " + latNorth);
            if (dbg) System.out.println("<br>" + thisClass + " latNorth = " +
                ec.getArgument(args,sc.LATITUDENORTH));

            if ("-1".equals(dataType)) {                                //ub01
                args = changeArgument2(args, sc.STARTDATE, "-01-01");   //ub01
                args = changeArgument2(args, sc.ENDDATE, "-12-31");     //ub01
                args = changeArgument3(args, sc.DATATYPE, "1");         //ub01
            } // if ("-1".equals(dataType))                             //ub01

            // not allowed more than 19 arguments!!!!
            args = deleteArgument(args, sc.HEMNORTH);
            args = deleteArgument(args, sc.HEMSOUTH);
            args = deleteArgument(args, sc.HEMWEST);
            args = deleteArgument(args, sc.HEMEAST);

        } // if ("7".equals(dataType) || "-1".equals(dataType))


        // get the user's e-mail address
        try {
            sql =
                "select userid from log_sessions " +
                "where code = " + sessionCode;
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                email = checkNull(rset.getString(1));
            } // while (rset.next())
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;
        } catch (SQLException e) { }
        if (dbg) System.out.println("<br>" + thisClass + " email = " + email);

        // get the user type
        //SadUsers user = new SadUsers();
        //user.setUserid(email);
        //SadUsers users[] = user.get();
        //userType = users[0].getUserType("");
        try {
            sql =
                "select user_type from sad_users " +
                "where userid = '" + email + "'";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                userType = checkNull(rset.getString(1));
            } // while (rset.next())
            rset.close();
            stmt.close();
            rset = null;
            stmt = null;
        } catch (SQLException e) { }
        if (dbg) System.out.println("<br>" + thisClass + " userType = " + userType);

        // which method for extraction?
        if (sc.OFFLINE.equals(method)) {

            // send email to request the extractions
            String args2[] = new String[args.length+2];
            args2[0] = sc.VERSION + "=" + sc.EXTRACT;
            args2[1] = sc.EMAIL + "=" + email;
            for (int i = 0; i < args.length; i++) args2[i+2] = args[i];

            SendEmail mail = new SendEmail(args2);

        } else {

            // get the email name
            StringTokenizer t = new StringTokenizer(email,"@");
            emailName = t.nextToken();
            // make sure there is not . in the email name
            t = new StringTokenizer(emailName,".");
            emailName = t.nextToken();

            if (dbg) System.out.println("<br>" + thisClass + " surveyId = " + surveyId);
            // get the file name
            StringBuffer name;
            if (!"7".equals(dataType) && !"-1".equals(dataType)) { // all except vos, hydro area
                name = new StringBuffer(
                    surveyId.substring(0,4) + surveyId.substring(5) + "_");
                if (dbg) System.out.println("<br>" + thisClass + " name1 = " + name);
            } else if ("-1".equals(dataType)) {                         //ub01
                name = new StringBuffer("Hydro_");                      //ub01
            } else {
                name = new StringBuffer(surveyId + "_");
                if (dbg) System.out.println("<br>" + thisClass + " name2 = " + name);
            } // if (!"7".equals(dataType) && !"-1".equals(dataType))

            if ("1".equals(dataType)) { // hydro
                name.append(extrType);
            } else if ("2".equals(dataType) || "6".equals(dataType)) { // currents, utr
                name.append(depthCode);
            } else if ("7".equals(dataType) || "-1".equals(dataType)) { // vos
                name.append(latNorth + hemNorth + "_" +
                    latSouth + hemSouth + "_" +
                    lonWest + hemWest + "_" + lonEast + hemEast + "_" +
                    startDate.substring(0,4) + "_" + endDate.substring(0,4));
            } else {
                name.append(year);
            } // if ("1".equals(dataType))
            fileName = name.toString();
            if (dbg) System.out.println("<br>" + thisClass + " fileName = " + fileName);

            // create the command that will do the extraction
            command = new StringBuffer(sc2.JAVA + " " + sc.APPS);
                for (int i = 0; i < args.length; i++) command.append(" " + args[i]);
                command.append(" " + sc2.FILENAME + "=" + fileName);
                command.append(" " + sc2.REQNUMBER + "=" + emailName);
                command.append(" " + sc2.USERTYPE + "=" + userType);
                command.append(" " + sc.VERSION + "=" + sc.EXTRACT);
                command.append(" " + sc.EMAIL + "=" + email);

            if ("3".equals(dataType) || "4".equals(dataType)) { // weather, waves
                command.append(" " + sc.STARTDATE + "=" + year+"-01-01");
                command.append(" " + sc.ENDDATE + "=" + year+"-12-31");
            } // if ("3".equals(dataType) || "4".equals(dataType))

            if (dbg3) command.append(
                " > /u02/people/sadco/www/data/inv_user/ExtractRequest.dbg");

            //if (dbg)
            System.out.println("<br>" + thisClass + " command = " + command);

            String command2[] = command.toString().split(" ");
            if (dbg) for(int i = 0; i < command2.length; i++)
                System.out.println("<br>" + thisClass + " command2["+i+"] = " + command2[i]);

            // submit the job
            if (dbg) System.out.println("<br>" + thisClass + " ec.getHost() = " + ec.getHost() +
                ", sc2.HOST = " + sc2.HOST);
            if (ec.getHost().startsWith(sc2.HOST)) {
                if (dbg3) ec.submitJob(
                    "rm -f /u02/people/sadco/www/data/inv_user/ExtractRequest.dbg");
                //ec.submitJob(command.toString());
                System.out.println("**************************");
                System.out.println("ARGUMENTS FOR JAVA.SH");
                System.out.println("**************************");
                for (int counter = 0; counter < command2.length; counter++) {
                    System.out.println(command2[counter]);
                }
                System.out.println("***************************");
                System.out.println("END ARGUMENTS FOR JAVA.SH");
                System.out.println("***************************");

                ec.submitJob(command2);
                if (dbg) System.out.println("<br>" + thisClass + " command submitted");
            } // if (ec.getHost().equals(sc2.HOST))

        } // if (sc.OFFLINE.equals(method))

        // create all the tables
        DynamicTable mainTab = new DynamicTable(1);
        mainTab
            .setBorder(0)
            .setRules(ITableRules.NONE)
            .setFrame(ITableFrame.VOLD)
            ;

        DynamicTable dataTab = new DynamicTable(1);
        dataTab
            .setBorder(0)
            .setRules(ITableRules.NONE)
            .setFrame(ITableFrame.VOLD)
            ;

        // create the container to hook everyhting on to
        Container con = new Container();

        // add data details to table
        if (!"7".equals(dataType) && !"-1".equals(dataType)) { // not vos
            dataTab.addRow(ec.cr2ColRow("SurveyId: ", surveyId));
        } // if (!"7".equals(dataType) && !"-1".equals(dataType))
        dataTab.addRow(ec.cr2ColRow("Data Type: ", dataTypeName));
        if ("1".equals(dataType)) { // hydro
            dataTab.addRow(ec.cr2ColRow("Extraction Type: ", sc.HYDRO_EXTR[extrType]));
        } else if ("2".equals(dataType) || "6".equals(dataType)) { // currents, utr
            dataTab.addRow(ec.cr2ColRow("Depth Code: ", depthCode));
            //dataTab.addRow("Depth: ", splDepth);
        } else if ("7".equals(dataType) || "-1".equals(dataType)) { // vos
            if ("-1".equals(dataType)) { // hydro                       //ub01
                dataTab.addRow(ec.cr2ColRow("Extraction Type: ", sc.HYDRO_EXTR[extrType])); //ub01
            } // if ("-1".equals(dataType))                             //ub01
            dataTab.addRow(ec.cr2ColRow("Latitude: ", latNorth + sc.DEG + hemNorth +
                " to " + latSouth + sc.DEG + hemSouth));
            dataTab.addRow(ec.cr2ColRow("Longitude: ", lonWest + sc.DEG + hemWest +
                " to " + lonEast + sc.DEG + hemEast));
            dataTab.addRow(ec.cr2ColRow("Period: ", startDate + " to " + endDate));
        } else {
            dataTab.addRow(ec.cr2ColRow("Year: ", year));
        } // if ("1".equals(dataType))


        if (sc.OFFLINE.equals(method)) {

            con .addItem("A request has been sent to SADCO staff to extract " +
                    "the data below on your behalf.  Once it has been completed, " +
                    "an e-mail will be sent to you with the details of where " +
                    "it can be downloaded.")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem(dataTab)
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("To continue browsing the inventory, please click " +
                    "on the <b>back button</b>")
                //.addItem("To continue browsing the inventory, please click " +
                //    "<a href='#' onClick='history.go(-1)'>here</a> ")
                ;

        } else {

            // get the right application
            if ("1".equals(dataType) || "-1".equals(dataType)) { // hydro
                application = sc2.MRN_APP;
                menuNo = "74";
            } else if ("2".equals(dataType) || "6".equals(dataType)) { // currents, utr
                application = sc2.CUR_APP;
                menuNo = "103";
            } else if ("3".equals(dataType)) { // weather
                application = sc2.WET_APP;
                menuNo = "105";
            } else if ("4".equals(dataType)) { // waves
                application = sc2.WAV_APP;
                menuNo = "126";
            } else if ("7".equals(dataType)) { // vos
                application = sc2.VOS_APP;
                menuNo = "56";
            } // if ("1".equals(dataType))

            // weather url
            // SadcoWET?psc=1894&pscreen=extract&pversion=progress&pmversion=form&pmenuno=105&put=2&preqnumber=&pfilename=
            // waves url
            // SadcoWAV?psc=1894&pscreen=extract&pversion=progress&pmversion=form&pmenuno=126&put=2&preqnumber=&pfilename=
            // currents url
            // SadcoCUR?psc=1894&pscreen=extract&pversion=progress&pmversion=form&pmenuno=103&put=2&preqnumber=&pfilename=
            // marine url
            // SadcoMRN?psc=1894&pscreen=extract&pversion=progress&pmversion=form&pmenuno=74&put=2&preqnumber=&pfilename=
            // vos url
            // SadcoVOS?psc=1894&pscreen=extract&pversion=progress&pmversion=form&pmenuno=56&put=2&preqnumber=&pfilename=



            // create the url
            String host = sadco.SadConstants.LIVE ? "http://sadcodata.ocean.gov.za/sadco1/" : "http://sadcodata.int.ocean.gov.za/sadco1/"
            url = host + application +
                "?psc=" + sessionCode + "&pscreen=extract&" +
                "pversion=progress&pmversion=form&pmenuno=" + menuNo + "&put=0&" +
                "preqnumber=" + emailName + "&pfilename=" + fileName;

            if (dbg) System.out.println("<br>" + thisClass + " url = " + url);

            // report back to the user
            con .addItem("Your extraction for the data below has been submitted. " +
                    "Once it has been completed, an e-mail will be sent to you " +
                    "with the details of where it can be downloaded.")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("Please note that there is a queueing system in place. " +
                    "Once your request has reached the front of the queue, it should " +
                    "finish within 3 minutes.")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem(dataTab)
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("To continue browsing the inventory, please click " +
                    "on the <b>back button</b>")
                .addItem(SimpleItem.LineBreak)
                .addItem(SimpleItem.LineBreak)
                .addItem("To check on the progress of your extraction, and " +
                    "then download the data, please click " +
                    "<a target='_blank' href='" + url + "'>here</a> ")
                ;

        } // if (sc.OFFLINE.equals(method))

        // add everthing to the main table, and add to the screen
        TableRow rows = new TableRow();
        rows
            .addCell(new TableDataCell(" ").setWidth(20))
            .addCell(new TableDataCell(con))
            ;
        mainTab.addRow(rows);
        this.addItem(mainTab);

        //if (dbg) System.out.println("<br>" + thisClass + " mainTab = " + mainTab.toHTML());

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

    } // constructor ExtractRequest


    /**
     * check if the value is null
     * returns  value / empty string
     */
    String checkNull(String value) {
        return (value != null ? value : "");
    } // checkNull


    /**
     * change the value of a URL parameter
     * @param  args  the string array of arguments from the form or URL
     * @param  name  the argument name
     * @return the argument value or empty string if the parameter does not exist
     */
    public String[] changeArgument(String args[], String name, String add) {
        String prefix = name + "=";
        if (dbg) System.out.println("<br>" + thisClass +
            ".changeArgument: prefix = " + prefix);
        for (int i = 0; i < args.length; i++)   {
            if (args[i].startsWith(prefix)) {
                if (dbg) System.out.println("<br>" + thisClass +
                    ".changeArguemnt: args[i] = " + args[i]);
                args[i] = prefix + add + args[i].substring(prefix.length());
                if (dbg) System.out.println("<br>" + thisClass +
                    ".changeArguemnt: args[i] = " + args[i]);
            } // if (args[i].startsWith(prefix))
        } // for (int i = 0; i < args.length; i++)
        return args;
    } // changeArgument


    /**
     * change the value of a URL parameter
     * @param  args  the string array of arguments from the form or URL
     * @param  name  the argument name
     * @return the argument value or empty string if the parameter does not exist
     */
    public String[] changeArgument2(String args[], String name, String add) {
        String prefix = name + "=";
        if (dbg) System.out.println("<br>" + thisClass +
            ".changeArgument2: prefix = " + prefix);
        for (int i = 0; i < args.length; i++)   {
            if (args[i].startsWith(prefix)) {
                if (dbg) System.out.println("<br>" + thisClass +
                    ".changeArgument2: args[i] = " + args[i]);
                args[i] = args[i]+add;
                if (dbg) System.out.println("<br>" + thisClass +
                    ".changeArgument2: args[i] = " + args[i]);
            } // if (args[i].startsWith(prefix))
        } // for (int i = 0; i < args.length; i++)
        return args;
    } // changeArgument2


    /**
     * change the value of a URL parameter
     * @param  args  the string array of arguments from the form or URL
     * @param  name  the argument name
     * @return the argument value or empty string if the parameter does not exist
     */
    public String[] changeArgument3(String args[], String name, String replace) {
        String prefix = name + "=";
        if (dbg) System.out.println("<br>" + thisClass +
            ".changeArgument3: prefix = " + prefix);
        for (int i = 0; i < args.length; i++)   {
            if (args[i].startsWith(prefix)) {
                if (dbg) System.out.println("<br>" + thisClass +
                    ".changeArgument3: args[i] = " + args[i]);
                args[i] = prefix + replace;
                if (dbg) System.out.println("<br>" + thisClass +
                    ".changeArgument3: args[i] = " + args[i]);
            } // if (args[i].startsWith(prefix))
        } // for (int i = 0; i < args.length; i++)
        return args;
    } // changeArgument3


    /**
     * delete a URL parameter
     * @param  args  the string array of arguments from the form or URL
     * @param  name  the argument name
     */
    public String[] deleteArgument(String args[], String name) {
        String prefix = name + "=";
        String args2[] = new String[args.length-1];
        if (dbg) System.out.println("<br>" + thisClass +
            ".deleteArgument: prefix = " + prefix);
        int ii = 0;
        for (int i = 0; i < args.length; i++)   {
            if (dbg) System.out.println("<br>" + thisClass +
                ".deleteArgument: i = " + i);
            if (!args[i].startsWith(prefix)) {
                args2[ii++] = args[i];
                //ii++
                if (dbg) System.out.println("<br>" + thisClass +
                    ".deleteArgument: args[i] = " + args[i]);
            } // if (args[i].startsWith(prefix))
        } // for (int i = 0; i < args.length; i++)
        return args2;
    } // deleteArgument


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("ExtractRequest local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new ExtractRequest(args));
            bd
                .setActivatedLinkColor   ("#000088")
                .setFollowedLinkColor    ("#663366")
                .setUnfollowedLinkColor  ("#666666")
                ;
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
            ec.processErrorStatic(e, "ExtractRequest", "Constructor", "");
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

} // class ExtractRequest