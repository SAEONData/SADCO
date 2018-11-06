package sadinv;

import java.io.File;
import java.sql.Timestamp;
import java.util.StringTokenizer;
import sadco.SadUserQueue;
import sadco.SadConstants;

/**
 * This class checks the user queue for extractions that are taking too long
 * to complete.
 *
 * @author 100615 - SIT Group.
 * @version
 * 100615 - Ursula von St Ange - created class                          <br>
 */
public class CheckQueue {

    /** A boolean variable for debugging purposes */
    //boolean dbg = false;
    boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();
    static SadConstants sc2 = new sadco.SadConstants();

    /** String variable for the directory path. */
    String rootPath;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public CheckQueue(String args[]) {

        try {
            CheckQueueActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // CheckQueue constructor


    /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void CheckQueueActual(String args[]) {

        // get a list of all the entries in the sad_user_queue
        String fields = "*";
        String where = "1=1";
        String order = SadUserQueue.CODE;
        SadUserQueue queue = new SadUserQueue();
        SadUserQueue queues[] = queue.get(fields, where, order);

        if (dbg) System.out.println("<br>" + thisClass + ": queues.length = " + queues.length);

        // only process if there is something in the queue
        if (queues.length > 0) {
            Timestamp dateTime = queues[0].getDateTime2();
            Timestamp now = new Timestamp(new java.util.Date().getTime());
            long dateTimeMS = dateTime.getTime();
            long nowMs = now.getTime();
            long diffMs = nowMs - dateTimeMS;
            long diffMinutes = diffMs / 36000;

            // jobs cannot be in the queue for more than 10 minutes
            if (diffMinutes > 10) {

                // get the rest of the info
                int code = queues[0].getCode();
                String userid = queues[0].getUserid();
                String extraction = queues[0].getExtraction();
                String arguments = queues[0].getArguments();

                if (dbg) System.out.println("<br>" + thisClass + ": userid = " +
                    userid + ", extraction = " + extraction);

                // set the code in the queue
                queue.setCode(code);

                // get the correct path
                String rootPath = "";
                if (dbg) System.out.println("<br>" + thisClass + ": host = " +
                    ec.getHost());
                if (ec.getHost().startsWith(sc2.HOST)) {
                    rootPath = sc2.HOSTDIRL + "inv_user/";
                } else {
                    rootPath = sc2.LOCALDIR;
                } // if host

                // get the data file name
                StringTokenizer t = new StringTokenizer(extraction, " ");
                t.nextToken();  // skip program name
                String fileName = t.nextToken();

                // get the email name
                t = new StringTokenizer(userid, "@");
                String useridName = t.nextToken();
                // make sure there is not . in the email name
                t = new StringTokenizer(useridName,".");
                useridName = t.nextToken();

                fileName = useridName + "_" + fileName;

                // is the job still queued or seems to be still processing
                if (check4File(fileName, ".queued", rootPath) ||
                    check4File(fileName, ".processing", rootPath)) {
                    if (dbg) System.out.println("<br>" + thisClass + ": deleteing " +
                        "fileName = " + fileName);

                    String[] args2 = arguments.split(";");
                    //pdataname=Hydro;psurveyid=1970/0011;pdatatype=1;
                    //pextrtype=0;paction=Extract_online;pscreen=extract;
                    //psc=2004;pfilename=19700011_0;preqnumber=ursula;
                    //put=0;pversion=extract;pemail=ursula.vonstange@gmail.com
                    String args3[] = new String[args2.length+1];
                    args3[0] = sc.VERSION2 + "=" + sc.CHECK;
                    for (int i = 0; i < args2.length; i++) args3[i+1] = args2[i];

                    SendEmail mail = new SendEmail(args3);

                } // if (check4File(fileName, ".queued", rootPath) ...

                // delete / dequeue the job
                queue.del();
                //common2.DbAccessC.commit();


            } // if (diffMinutes > 10)
        } // if (queues.length > 0)

    } // CheckQueueActual


    /**
     * checks for the existence of a particular file
     * @param filter1   the filter that the file starts with
     * @param filter2   the filter that the file ends with
     * @param pathName  the path name of the file
     * @returns         true/false
     */
    boolean check4File(String filter1, String filter2,
            String pathName) {

        File path = new File(pathName);
        String[] fileList = path.list();

        int size = fileList.length;
        if (dbg) System.out.println(" size "+size);

        String list[] = ec.bubbleSort(fileList, size);
        boolean foundFile = false;

        int j = 0;
        for (int i = list.length-1; i >= 0; i--) {
        //for (int i = 0; i < list.length; i++) {
            if (list[i].startsWith(filter1) &&
                    list[i].endsWith(filter2)) {

                foundFile = true;

            } // if (fileList[i].endsWith(filter)) {
        } // for i

        //if (dbg) System.out.println(" createFileList end "+foundFiles);
        return foundFile;
    } // check4File


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            CheckQueue local =
                new CheckQueue(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "CheckQueue", "Constructor", "");
            // close the connection to the database
        } // try-catch
        common2.DbAccessC.close();

    } // main

} // class CheckQueue