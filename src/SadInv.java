import common2.*;
import oracle.html.*;
import sadinv.*;   //****************
//import menusp.*;
//import java.io.*;
//import javax.servlet.*;
//import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.File;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * An Application class that manages the edm waves application.
 *
 * @author  991027 - SIT Group
 * @version
 */

public class SadInv extends HttpServlet {

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    // values for in this application
    static String thisClass   = sc.APP;     // class name
    static String headTitle   = sc.APP;     // title in head part of page

    static String htmlFile =
        //"/opt/jakarta-tomcat-4.1.18/webapps/"+
        //"sadco1/WEB-INF/classes/sadinvnew/indexFrames.html";
        "/opt/tomcat/webapps/sadco1/WEB-INF/classes/sadinv/indexFrames.html";

    // parameters
    static String screen  = "";
    static String version = "";


    /**
     * The only constructor.
     * @param args  array of URL-like name=value parameter pairs - from the command line
     */
    //public SadInv(String args[]) {
//    public void doWorkS (HttpServletRequest req, HttpServletResponse res)
//        throws UnknownHostException {
    public void doWorkS (HttpServletRequest req, HttpServletResponse res) {

        // get the parameter-value pairs
        String args[] = ec.convertReq2Args(req);

        InetAddress remoteIp = ec.getRemoteIp(req);
        if (remoteIp == null) {
            System.out.println("Unkown host:abort pgm");
        } else {
            String hostName = remoteIp.getHostName();
            System.out.print(thisClass + ":" + remoteIp.getHostAddress() + ":" +
                hostName + ":" + ec.getDateTime());
            if (hostName.endsWith("googlebot.com")) {
                System.out.println(":abort pgm");
                return;
            } else if (hostName.indexOf("crawl") > -1) {
                System.out.println(":abort pgm");
                return;
            } // if (hostName.endsWith("googlebot.com"))
            System.out.println();
        } // if (remoteIp == null)

        HtmlPage hp = doWork(args);

        // get the 'printer' to write to
        try {
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.println(hp.toHTML());
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "Constructor", "");
        } // try-catch
    } //


    public HtmlPage doWork (String args[]) {

        // get the 'command line' arguments - screen
        getParms(args);

        // create the page
        HtmlHead hd = new HtmlHead(headTitle);
        HtmlPage hp = new HtmlPage();

        //if there were no arguments
        if ("".equals(screen)) {
            File file = new File(htmlFile);
            hp = new HtmlPage(file);

        } else {

            HtmlBody bd = new HtmlBody();
            hp = new HtmlPage(hd,bd);

            bd
                .setActivatedLinkColor   ("#000088")
                .setFollowedLinkColor    ("#663366")
                .setUnfollowedLinkColor  ("#666666")
            ;

            if (screen.equals(sc.HEADER)) {
                bd.addItem(new Header(args));

            } else if (screen.equals(sc.BUTTONS)) {
                bd.addItem(new Buttons(args));

            } else if (screen.equals(sc.FOOTER)) {
                bd.addItem(new Footer(args));

            } else if (screen.equals(sc.HELP)) {
                bd.addItem(new Help(args));

            } else if (screen.equals(sc.STARTUP)) {
                bd.addItem(new Startup(args));

            } else if (screen.equals(sc.LOGIN)) {
                bd.addItem(new Login(args));

            } else if (screen.equals(sc.REGISTER)) {
                bd.addItem(new Register(args));

            } else if (screen.equals(sc.SENDEMAIL)) {
                bd.addItem(new SendEmail(args));

            } else if (screen.equals(sc.EXTRACT)) {
                bd.addItem(new ExtractRequest(args));

            } else if (screen.equals(sc.SURVEYS)) {
                bd.addItem(new ListSurveyId(args));

            } else if (screen.equals(sc.DISPLAYSURVEY)) {
                bd.addItem(new DisplaySurvey(args));

            } else if (screen.equals(sc.DATES)) {
                bd.addItem(new ListDates(args));

            } else if (screen.equals(sc.INSTITUTES)) {
                bd.addItem(new GetInstitute(args));

            } else if (screen.equals(sc.INSTITUTES2)) {
                bd.addItem(new ListInstitute(args));

            } else if (screen.equals(sc.SCIENTISTS)) {
                bd.addItem(new GetScientist(args));

            } else if (screen.equals(sc.SCIENTISTS2)) {
                bd.addItem(new ListScientist(args));

            } else if (screen.equals(sc.PLATFORMS)) {
                bd.addItem(new GetPlatform(args));

            } else if (screen.equals(sc.PLATFORMS2)) {
                bd.addItem(new ListPlatform(args));

            } else if (screen.equals(sc.AREAS)) {
                bd.addItem(new ListArea(args));

            } else if (screen.equals(sc.DATATYPES)) {
                bd.addItem(new GetDataType(args));

            } else if (screen.equals(sc.DATATYPES2)) {
                bd.addItem(new ListDataType(args));

            } else if (screen.equals(sc.DATATYPEVOS)) {
                bd.addItem(new ListVos(args));

            } else if (screen.equals(sc.PROJECTS)) {
                bd.addItem(new GetProject(args));

            } else if (screen.equals(sc.PROJECTS2)) {
                bd.addItem(new ListProject(args));

            } else if (screen.equals(sc.KML)) {
                bd.addItem(new DisplayKML(args));

            } //if (screen.equals(sc.header)) {

        } // if ("".equals(screen))

        //hp.print();
        common2.DbAccessC.close();
        return hp;

    }  //  SadInv constructor


    /**
     * get the parameters
     * @param args the string array of url-type name-value parameter pairs
     */
    static private void getParms(String args[])  {
        screen        = ec.getArgument(args, sc.SCREEN);
    }  //  static private void getParms(String args[])  {


    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            doWorkS(req, res);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "doGet", "");
        } // try-catch
        common2.DbAccessC.close();
    } // doGet


    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            doWorkS(req, res);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "doPost", "");
        } // try-catch
        common2.DbAccessC.close();
    } // doGet


//    public static InetAddress getRemoteIp(final HttpServletRequest request) {
//        try {
//            if (request.getHeader("x-forwarded-for") != null) {
//                return InetAddress.getByName(request.getHeader("x-forwarded-for"));
//            } // if (request.getHeader("x-forwarded-for") != null)
//            return InetAddress.getByName(request.getRemoteAddr());
//        } catch(UnknownHostException e) {
//            return null;
//        } // try-catch
//    } // getRemoteIp

//    public static InetAddress getRemoteIp(final HttpServletRequest request)
//            throws UnknownHostException {
//        if (request.getHeader("x-forwarded-for") != null) {
//            return InetAddress.getByName(request.getHeader("x-forwarded-for"));
//        } // if (request.getHeader("x-forwarded-for") != null)
//        return InetAddress.getByName(request.getRemoteAddr());
//    } // getRemoteIp

} // class SadInv
