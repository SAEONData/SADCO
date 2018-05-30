//import common2.*;
import oracle.html.*;
import survsum.*;
//import java.io.*;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.*;
//import javax.servlet.http.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * An Application class that manages the SADCO SurvSum application.
 *
 * @author  001102 - SIT Group
 * @version
 * 031110 - Ursula von St Ange - create class                               <br>
 */

public class SurvSum extends HttpServlet {

    // values for in this application
    static String thisClass   = "SurvSum"; // class name
    static String headTitle   = "SurvSum"; // title in head part of page
    static String password    = "ter91qes"; // Oracle schema password

    // parameters
    static String screen       = "";
    static String version      = "";

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();

    /**
     * The only constructor.
     * @param args  array of URL-like name=value parameter pairs - from the command line
     */
    //public SurvSum(String args[]) {
    public void doWorkS (HttpServletRequest req, HttpServletResponse res)
        throws UnknownHostException {

        // get the parameter-value pairs
        String args[] = common.edmCommon.convertReq2Args(req);

        InetAddress remoteIp = getRemoteIp(req);
        String hostName = remoteIp.getHostName();
        System.out.print(thisClass + ":" + remoteIp.getHostAddress() + ":" +
            hostName + ":" + ec.getDateTime());
        if (hostName.endsWith("googlebot.com")) {
            System.out.println(":abort pgm");
            return;
        } else if (hostName.indexOf("crawl") > -1) {
            System.out.println(":crawl:abort pgm");
            return;
        } // if (hostName.endsWith("googlebot.com"))
        System.out.println();

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


        // get the 'command line' arguments
        screen = ec.getArgument(args, "pscreen");

        // create the page
        HtmlHead hd = new HtmlHead(headTitle);
        HtmlBody bd = new HtmlBody();
        HtmlPage hp = new HtmlPage(hd,bd);

        bd
            .setActivatedLinkColor   ("#ff3300")
            .setBackgroundColor      ("#fffff2")
            .setFollowedLinkColor    ("#000055")
            .setUnfollowedLinkColor  ("#cc0000")
        ;

        if ("".equals(screen))   {
            screen = "DisplaySurvsumList";
        } //if ("".equals(screen))   {

        if (screen.equals("DisplaySurvsumList")) {
            bd.addItem(new DisplaySurvsumList(args));
        } else if (screen.equals("DisplaySurvsum")) {
            bd.addItem(new DisplaySurvsum(args));
        } else if (screen.equals("UpdateDataBlock")) {
            bd.addItem(new UpdateDataBlock(args));
        } else if (screen.equals("UpdatePlatformBlock")) {
            bd.addItem(new UpdatePlatformBlock(args));
        } else if (screen.equals("UpdateSurvNamBlock")) {
            bd.addItem(new UpdateSurvNamBlock(args));
        } else if (screen.equals("UpdatePeriodsBlock")) {
            bd.addItem(new UpdatePeriodsBlock(args));
        } else if (screen.equals("UpdateResLabBlock")) {
            bd.addItem(new UpdateResLabBlock(args));
        } else if (screen.equals("UpdateChiefScBlock")) {
            bd.addItem(new UpdateChiefScBlock(args));
        } else if (screen.equals("AddNewScientist")) {
            bd.addItem(new AddNewScientist(args));
        } else if (screen.equals("UpdateObjectsBlock")) {
            bd.addItem(new UpdateObjectsBlock(args));
        } else if (screen.equals("UpdateProjectBlock")) {
            bd.addItem(new UpdateProjectBlock(args));
        } else if (screen.equals("UpdatePriInvsBlock")) {
            bd.addItem(new UpdatePriInvsBlock(args));
        } else if (screen.equals("UpdateMooringBlock")) {
            bd.addItem(new UpdateMooringBlock(args));
        } else if (screen.equals("UpdateSamplesBlock")) {
            bd.addItem(new UpdateSamplesBlock(args));
        } else if (screen.equals("UpdateTrackChBlock")) {
            bd.addItem(new UpdateTrackChBlock(args));
        } else if (screen.equals("UpdateOcAreasBlock")) {
            bd.addItem(new UpdateOcAreasBlock(args));
        } else if (screen.equals("UpdateZonesBlock")) {
            bd.addItem(new UpdateZonesBlock(args));
        } else if (screen.equals("UpdateTCountryBlock")) {
            bd.addItem(new UpdateTCountryBlock(args));
        } else if (screen.equals("UpdatePSDdataBlock")) {
            bd.addItem(new UpdatePSDdataBlock(args));
        } else if (screen.equals("UpdateSamplingBatches")) {
            bd.addItem(new UpdateSamplingBatches(args));
        } else if (screen.equals("UpdateStationsBlock")) {
            bd.addItem(new UpdateStationsBlock(args));
/*
        } else if (screen.equals("DisplayPlatform")) {
            bd.addItem(new DisplayPlatform(args));
*/
        } // if (screen.equals("DisplaySurvsumList"))

        common2.DbAccessC.close();
        return hp;

    }  //  SurvSum constructor


    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            doWorkS(req, res);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "doGet", "");
            common2.DbAccessC.close();
        } // try-catch
    } // doGet


    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            doWorkS(req, res);
        } catch(Exception e) {
            ec.processErrorStatic(e, thisClass, "doPost", "");
            common2.DbAccessC.close();
        } // try-catch
    } // doGet


    public static InetAddress getRemoteIp(final HttpServletRequest request) {
        try {
            if (request.getHeader("x-forwarded-for") != null) {
                return InetAddress.getByName(request.getHeader("x-forwarded-for"));
            } // if (request.getHeader("x-forwarded-for") != null)
            return InetAddress.getByName(request.getRemoteAddr());
        } catch(UnknownHostException e) {
            return null;
        } // try-catch
    } // getRemoteIp

} // class SurvSum
