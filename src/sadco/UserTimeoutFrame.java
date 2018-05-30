//+------------------------------------------------+----------------+--------+
//| CLASS: ... UserTimeoutFrame                    | JAVA PROGRAM   | 011113 |
//+------------------------------------------------+----------------+--------+
//| PROGRAMMER: .. SIT Group                                                 |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//| Sadco/SadcoVOS/SadcoMRN                                                  |
//|                                                                          |
//|                                                                          |
//|                                                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 011113  SIT Group            create class                                |
//+--------------------------------------------------------------------------+

package sadco;
import common2.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import oracle.html.*;

/**
 * Check whether the user has last logged in longer than 8 hours ago.
 */
public class UserTimeoutFrame {

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();
    String thisClass = this.getClass().getName();

    //boolean dbg = false;
    boolean dbg = true;
    SimpleDateFormat frm = new SimpleDateFormat ("yyyy-MM-dd HH:mm");

    boolean timeout = true;
    MetaInfo mi = null;

    // for problems with timeouts
    static RandomAccessFile ofile;
    String dbgText = "";
    String userId = "";

    /**
     * Main constructor UserTimeoutFrame
     * @param  args       (String)
     */
    public UserTimeoutFrame(String args[], String callingProgram) {

        // get the arguments
        String sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        dbgText = "sid="+sessionCode+",head="+callingProgram+
            ",screen="+ec.getArgument(args, sc.SCREEN)+
            ",verion="+ec.getArgument(args, sc.VERSION);

        // get the user's log in time and add 8 hours
        int session = 0;
        if (!"".equals(sessionCode)) {
            session = Integer.parseInt(sessionCode);
        } // if (!"".equals(sessionCode))
        LogSessions[] sessions = new LogSessions(session).get();
        if (dbg) System.out.println("<br>sessions.length = " + sessions.length);
        // if it is an invalid sessioncode - logon anyway
        if (dbg) System.out.println("<br>timeout = " + timeout);
        if (sessions.length > 0) {

            // get a time 8 hours from session login time
            Timestamp dateTime = sessions[0].getDateTime();
            GregorianCalendar calTimeOut = new GregorianCalendar();
            calTimeOut.setTime(dateTime);
            if (dbg) System.out.println("<br>calTimeOut = " + frm.format(
                 calTimeOut.getTime()));
            calTimeOut.add(Calendar.HOUR, 8);
            if (dbg) System.out.println("<br>calTimeOut = " + frm.format(
                calTimeOut.getTime()));

            // get the current time
            GregorianCalendar calNow = new GregorianCalendar();
            calNow.setTime(new java.util.Date());
            if (dbg) System.out.println("<br>calNow = " + frm.format(
                calNow.getTime()));

            // problems with timeouts
            userId = sessions[0].getUserid();
            dbgText += ",uid="+userId+
                ",dt="+sessions[0].getDateTime("")+
                ",todt="+frm.format(calTimeOut.getTime())+
                ",now="+frm.format(calNow.getTime());

            // if logged on less than 8 hours ago - user doesn't
            // have to log in again
            //if (calNow.getTime().before(calTimeOut.getTime())) {
            if (calNow.before(calTimeOut)) {
                if (dbg) System.out.println("<br>calNow.before(calTimeOut)");
                timeout = false;
            } // if (calTimeOut.before(calNow)) {
        } // if (sessions.length > 0)
        if (dbg) System.out.println("<br>timeout = " + timeout);

        // problems with timeouts
        dbgText += ",to="+timeout;
        //System.err.println(dbgText);
        // get the correct PATH NAME - used while debugging
        String rootPath = "";
        if (dbg) System.out.println("<br>" + thisClass + ": host = " +
            ec.getHost());

        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIRLOG;
        } else {
            rootPath = sc.LOCALDIR;
        } // if host

        if ("".equals(userId)) userId = "none";
        ec.writeFileLine(rootPath+userId+"_to.log",dbgText);

        if (timeout) {
            mi = new MetaInfo("refresh",
                "0;URL=" + sc.LOGIN_USER_APP + "?" + sc.TIMEOUT + "=1");
        } // if (timeout)


    } // constructor

    /**
     * return true / false depending on whether user has been timed out
     * @return true / false
     */
    public boolean getTimeout() {
        return timeout;
    } // getTimeout

    /**
     * return metainfo
     * @return MetaInfo
     */
    public MetaInfo getMetaInfo() {
        return mi;
    } // getMetaInfo
} // UserTimeoutFrame
