package common2;

import sadco.SadUserQueue;

/**
 * This class uses the table sad_user_queue to implement a queueing system
 * for extractions.
 *
 * @author 20091221 - SIT Group
 * @version
 * 20091221 - Ursula von St Ange - create class                         <br>
 */

public class Queue {

    /** For debugging code: true/false */
    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** class variables */
    int     code        = 0;
    //String  userId      = "";
    //String  extraction  = "";
    SadUserQueue queue  = null;
    SadUserQueue updQueue  = null;
    SadUserQueue whereQueue  = null;
    SadUserQueue queues[]  = null;

    String  fields      = "min(" + SadUserQueue.CODE + ") as " + SadUserQueue.CODE;
    String  where       = "";
    String  order       = SadUserQueue.CODE;

    /**
     * Creates the instance
     * @param   userid      the userid of the user
     * @param   extraction  the calling program
     */
    public Queue (String userid, String extraction) {
        //this.extraction = extraction;
        //this.userId = userId;

        queue = new SadUserQueue();
        queue.setUserid(userid);
        queue.setExtraction(extraction);
        queue.setDateTime(new java.util.Date());
        if (dbg) System.out.println ("<br>" + thisClass + ".constructor: " + userid);
        if (dbg) System.out.println ("<br>" + thisClass + ".constructor: queue = " +
            queue.toString());
    } // Queue constructor


    /**
     * Creates the instance
     * @param   userid      the userid of the user
     * @param   extraction  the calling program
     * @param   args[]      the arguments of the calling program
     */
    public Queue (String userid, String extraction, String[] args) {
        //this.extraction = extraction;
        //this.userId = userId;

        queue = new SadUserQueue();
        queue.setUserid(userid);
        queue.setExtraction(extraction);
        queue.setDateTime(new java.util.Date());
        queue.setArguments(convert2String(args));
        if (dbg) System.out.println ("<br>" + thisClass + ".constructor: " + userid);
        if (dbg) System.out.println ("<br>" + thisClass + ".constructor: queue = " +
            queue.toString());
    } // Queue constructor


    /**
     * Enqueue the extraction
     */
    public void enQueue() {
        if (dbg) System.out.println ("<br>" + thisClass + ".enQueue: start");
        queue.putLock();
        code = queue.getCode();
        where = SadUserQueue.CODE + "=" + code;
        if (dbg) System.out.println ("<br>" + thisClass + ".enQueue: code = " + code);
    } // enQueue


    /**
     * Dequeue the extraction
     */
    public void deQueue() {
        queue.del(where);
        if (dbg) System.out.println ("<br>" + thisClass + ".deQueue: code = " + code);
        if (dbg) System.out.println ("<br>" + thisClass + ".deQueue: delStr = " + queue.getDelStr());
        if (dbg) System.out.println ("<br>" + thisClass + ".deQueue: numRecs = " + queue.getNumRecords());
        if (dbg) System.out.println ("<br>" + thisClass + ".deQueue: messages = " + queue.getMessages());
    } // enQueue


    /**
     * Wait for your turn, and update the 2nd dateTime when you get going
     */
    public void wait4Turn() {

        // get the minimum value of code
        if (dbg) System.out.println ("<br>" + thisClass + ".deQueue: fields = " + fields);
        //queues = queue.get(fields, "1=1", order);
        queues = queue.get("*", "1=1", order);
        //queues = queue.get(fields, "1=1");
        int minCode = queues[0].getCode();
        //int minCode = queue.getMinCode();

        // loop while code is not the minimum code
        while (code != minCode) {

            if (dbg) System.out.println ("<br>" + thisClass + ".wait1: code, minCode = " +
                code + " " + minCode);

            //Pause for 5 seconds
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // try - catch

            // check again
            //queues = queue.get(fields, "1=1", order);
            queues = queue.get("*", "1=1", order);
            //queues = queue.get(fields, "1=1");
            minCode = queues[0].getCode();
            //minCode = queue.getMinCode();

        } // while (code != minCode)

        // update the 2nd date_time
        updQueue = new SadUserQueue();
        updQueue.setDateTime2(new java.util.Date());
        whereQueue = new SadUserQueue();
        whereQueue.upd(updQueue, where);
        if (dbg) System.out.println ("<br>" + thisClass + ".deQueue: updStr = " +
            whereQueue.getUpdStr());

        if (dbg) System.out.println ("<br>" + thisClass + ".wait2: code, minCode = " +
            code + " " + minCode);

    } // wait4Turn


    /**
     * Convert the args array to a string
     * @param   args    the array of arguments
     * @return  the arguments as a ; delimited string
     */
    private String convert2String(String[] args) {
        StringBuffer argsStr = new StringBuffer("");
        if (args.length > 1) argsStr.append(args[0]);
        for (int i = 1; i < args.length; i++) {
            argsStr.append(";" + args[i]);
        } // for (int i = 1; i < args.length; i++)
        return argsStr.toString();
    } // convert2String


}   // class Queue
