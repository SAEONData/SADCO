package common;

import java.io.RandomAccessFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.sql.Timestamp;
//import java.math.*;
//import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class contains a set of general purpose methods.
 *
 * @author 000328 - SIT Group
 * @version
 * 000328 - Neville Paynter    - create class                               <br>
 * 000505 - Neville Paynter    - change frm methods from static to public   <br>
 * 000505 - Neville Paynter    - add method floatNullToNines                <br>
 * 020709 - Ursula von St Ange - add Note: to latlonRangeInput prompt       <br>
 * 030724 - Ursula von St Ange - ub01 - change getHost() function,          <br>
 * 040602 - Ursula von St Ange - change frm for float for scientific values <br>
 * 041108 - Ursula von St Ange - change frmFiller to non-static.  See whether
 *                               it sorts out the &nbsp; problem in
 *                               AdminCalcSpeeds spdRep file                <br>
 * 041119 - Ursula von St Ange - ub02 - change nullToNines from 999 to 9999 <br>
 * 090505 - Ursula von St Ange - ub03 - add frm() for double                <br>
 */
public class edmCommonPC {

    /** The filler character used for formatted output */
    //static String frmFiller = " ";
    String frmFiller = " ";
    // when one downloads the .wav files to PC with file, save link as,
    // it does not convert to \r\n for PC
    //static String newLine = System.getProperty("line.separator");
    static String nl = "\r\n";
    //static String br = "\n";
    //static String br = newLine;
    static String br = System.getProperty("line.separator");


    /** For debugging code: true/false */
    static boolean dbg = false;
    //static boolean dbg = true;

    // for error processing
    String thisClass = this.getClass().getName();

//    public edmCommonPC () {
//        System.err.println("edmCommonPC");
//    } // edmCommonPC

    public void getCR() {
        System.err.println("cr = \\r\\n");
    } // getCR


    // +============================================================+
    // | Methods for internet stuff                                 |
    // +============================================================+

    /**
     * get local host name
     * @return  the relevant host name
     */
    public String getHost() {
        String host = "";
        try {
            java.net.InetAddress inetAddress =
                java.net.InetAddress.getLocalHost();
            host = inetAddress.getHostName(); //.substring(0,5);        // ub01
        } catch ( Exception e) {
            System.out.println (nl + "hostName error: " +
                e.getMessage());
            e.printStackTrace();
        } // try ... catch
        if (dbg) System.out.println(nl + "host = " + host);
        return host;

    } // getHost

    // +============================================================+
    // | Methods to manipulate html things                          |
    // +============================================================+

    /**
     * Look up a URL parameter
     * @param  args  the string array of arguments from the form or URL
     * @param  name  the argument name
     * @return the argument value or empty string if the parameter does not exist
     */
    public String getArgument(String args[], String name)    {
        String prefix = name + "=";
        for (int i = 0; i < args.length; i++)   {
            if (args[i].startsWith(prefix) )    {
                return args[i].substring(prefix.length()).trim();
            } //  if (args[i].startsWith(prefix) )
        } //  for (int i = 0; i < args.length; i++)
       return "";
    } //  public String getArgument(String args[], String name)

    /**
     * Look up a URL parameter with multiple entries
     * @param  args  the string array of arguments from the form or URL
     * @param  name  the argument name
     * @return the array of argument value
     */
    public String[] getArguments(String args[], String name)    {
        String prefix = name + "=";
        String[] values = new String[args.length];

        int cnt = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith(prefix)) {
                values[cnt++] = args[i].substring(prefix.length());
            } //  if (args[i].startsWith(prefix) )
        } //  for (int i = 0; i < args.length; i++)
        for (int i = cnt; i < args.length; i++) { values[i] = ""; }
        return values;
    } //  public String getArgument(String args[], String name)



    // +------------------------------------+
    // | Methods for select pull-down boxes |
    // +------------------------------------+

    /**
     * Identify the select item that is to be flagged as default.
     * @param  item  the text in the drop-down box
     * @param  prevSelect  the text of the default value for the drop-down box
     * @return true/false, whether item is the default or not
     */
    public boolean getDefault(String item, String prevSelect)   {
        boolean defS;
        if (item.equals(prevSelect))   {
            defS = true;
        }  else   {
            defS = false;
        }  //  if (item.equals(prevSelect))

        return defS;
    }  //  public boolean getDefault(String item, String year)


    // +============================================================+
    // | Methods for SADCO                                          |
    // +============================================================+


    /**
     * Submit a job to run in the background
     * @param command The command to be submitted to the system (String)
     */
    public void submitJob(String command)  {

        //try {
        //    if (dbg) System.out.println ("<br>command1 = " + command);
        //    if (dbg) System.out.println("<br>command.indexOf('java')" +
        //        command.indexOf("java"));
        //    if (dbg) System.out.println("<br>command.substring(command.indexOf(' '))" +
        //        command.substring(command.indexOf(' ')));
        //
        //    if (command.indexOf("java") > -1) {
        //        command = "/oracle/oraweb/product/734/ows/4.0/jdk/bin/java " +
        //            command.substring(command.indexOf(' '));
        //    }
        //    if (dbg) System.out.println ("<br>command2 = " + command);
        //    Runtime runtime = Runtime.getRuntime();
        //    Process process = runtime.exec(command); //the batch/ script file
        //    int i=0, j=0;
        //    if (dbg) process.waitFor();
        //    //if (dbg) int j = process.exitValue();
        //    if (dbg) System.out.println("<br>wait = " + i + ", exitValue = " + process.exitValue());
        //} catch (Exception e) {
        //    System.out.println("edmCommon submitJob error: " + e.getMessage());
        //    e.printStackTrace();
        //} // try .. catch

        //boolean dbgl = true;
        boolean dbgl = false;

        if (dbgl) System.out.println(nl + "command = " + nl + command + nl);
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command); //the batch/ script file
            if (dbgl) {
                process.waitFor();
                System.out.println(nl + "<b>OUTPUT</b>");
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
                String line = "nothing read";
                while (in.ready()) {
                    try {
                        System.out.println(nl + in.readLine());
                    } catch (Exception e) {
                        System.out.println("end of input?" + e.getMessage());
                        break;
                    } // try-catch
                } // while in
                System.out.println(nl + "<b>END OF OUTPUT</b>" + nl);
                System.out.println(nl + "<b>ERROR</b>");
                BufferedReader error = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
                line = "nothing read";
                while (error.ready()) {
                    try {
                        System.out.println(nl + error.readLine());
                    } catch (Exception e) {
                        System.out.println("end of error?" + e.getMessage());
                        break;
                    } // try-catch
                } // while error
                System.out.println(nl + "<b>END OF ERROR</b>" + nl);
            } // if (dbg)
        } catch (Exception e) {
            System.out.println(nl + "edmCommon submitJob error: " + e.getMessage());
            e.printStackTrace();
        }

    } // SubmitJob


    /**
     * Submit a job to run in the background
     * @param command The command to be submitted to the system (String)
     */
    public void submitJob(String command[])  {

        //try {
        //    if (dbg) System.out.println ("<br>command1 = " + command);
        //    if (dbg) System.out.println("<br>command.indexOf('java')" +
        //        command.indexOf("java"));
        //    if (dbg) System.out.println("<br>command.substring(command.indexOf(' '))" +
        //        command.substring(command.indexOf(' ')));
        //
        //    if (command.indexOf("java") > -1) {
        //        command = "/oracle/oraweb/product/734/ows/4.0/jdk/bin/java " +
        //            command.substring(command.indexOf(' '));
        //    }
        //    if (dbg) System.out.println ("<br>command2 = " + command);
        //    Runtime runtime = Runtime.getRuntime();
        //    Process process = runtime.exec(command); //the batch/ script file
        //    int i=0, j=0;
        //    if (dbg) process.waitFor();
        //    //if (dbg) int j = process.exitValue();
        //    if (dbg) System.out.println("<br>wait = " + i + ", exitValue = " + process.exitValue());
        //} catch (Exception e) {
        //    System.out.println("edmCommon submitJob error: " + e.getMessage());
        //    e.printStackTrace();
        //} // try .. catch

        //boolean dbgl = true;
        boolean dbgl = false;

        if (dbgl) {
            System.out.println("command = ");
            for (int i = 0; i < command.length; i++)
                System.out.println(command[i]);
        } // if (dbgl)
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command); //the batch/ script file
            if (dbgl) {
                process.waitFor();
                System.out.println("<b>OUTPUT</b>");
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
                String line = "nothing read";
                while (in.ready()) {
                    try {
                        System.out.println(in.readLine());
                    } catch (Exception e) {
                        System.out.println("end of input?" + e.getMessage());
                        break;
                    } // try-catch
                } // while in
                System.out.println("<b>END OF OUTPUT</b>");
                System.out.println("<b>ERROR</b>");
                BufferedReader error = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
                line = "nothing read";
                while (error.ready()) {
                    try {
                        System.out.println(nl + error.readLine());
                    } catch (Exception e) {
                        System.out.println("end of error?" + e.getMessage());
                        break;
                    } // try-catch
                } // while error
                System.out.println("<b>END OF ERROR</b>");
            } // if (dbg)
        } catch (Exception e) {
            System.out.println("edmCommon submitJob error: " + e.getMessage());
            e.printStackTrace();
        }

    } // SubmitJob


    /**
     *  Create a new sequential station_id (SADCO marine)
     * @param  stationIdPre the stationId prefix
     * @param  stationId    the stationId
     */
    public String getNewStationId(String stationIdPre, String stationId)   {
        String  newStationId = new String("");
        String snum;
        String convert = new String();
        Integer inum = new Integer(0);

        if (stationId.length() < 1)   {
            // +------------------------------------------------------------+
            // | There is no previous sequential station_id for the         |
            // | selected prefix. Create a "zero" sequential station_id     |
            // +------------------------------------------------------------+
            newStationId = stationIdPre + "SQ0000000";

        }  else  {

           // +------------------------------------------------------------+
           // | The lasted sequential station_id has been found for the    |
           // | selected prefix.                                           |
           // +------------------------------------------------------------+

           // +------------------------------------------------------------+
           // | - store the numeric part of the last sequential station_id |
           // | - convert the string number into a long and add to it so   |
           // |   that the number is incremented by one and leading zeros  |
           // |   are forced into the result.                              |
           // | - The long number is returned to a string and the first    |
           // |   digit, 1, now a character, is removed, resulting in a    |
           // |   string that starts with leading zeros                    |
           // | - The new station_id is reassembled starting by the        |
           // |   selected prefix, followed by "SQ" which identifies the   |
           // |   station_id as a sequential one, followed by the new      |
           // |   sequential number in a string format.                    |
           // | e.g. last sequential station_id = EMDSQ0001303             |
           // |      new  sequential station_id = EMDSQ0001304             |
           // +------------------------------------------------------------+
           snum = stationId.substring(5,12);
           long lnum = inum.valueOf(snum).intValue() + 10000001;
           snum = convert.valueOf(lnum).substring(1,8);
           newStationId = stationIdPre + "SQ" + snum;
        }  //  if (stationId.length() = 0)

        return  newStationId;

    }  //  public String getNewStationId(String stationIdPre, String stationId)


    // +============================================================+
    // | Methods for testing numeric, time, mod                     |
    // +============================================================+

    /**
     *  Tests whether a String is numeric (contains other characters
     * than '0' to '9', '.' and '-').
     * @param inputString  the string to test
     * @return  true/false
     */
    public boolean isNotNumeric(String inputString)   {
        boolean test = false;

        for (int i = 0; i < inputString.length(); i++)    {
            char ch = inputString.charAt(i);
            if (!((ch >= '0' && ch <= '9') || ch == '.' || ch == '-'))
                test = true;
        }  //  for (int i = 0; i < inputString.length(); i++)

        return test;
    }  //  public boolean isNotNumeric(String inputString)


    /**
     *  Test whether the string is a valid time field (hh:mm:ss)
     * @param inputString  the string to test
     * @return  true/false
     */
    public boolean isNotTime(String inputString)   {
        boolean test = false;
        Integer Itest = new Integer(0);

        // +------------------------------------------------------------+
        // | Test for non-numeric characters                            |
        // +------------------------------------------------------------+
        for (int i = 0; i < inputString.length(); i++)    {
            char ch = inputString.charAt(i);
            if (!((ch >= '0' && ch <= '9') || ch == '.' || ch == ':'))
                test = true;
            }  //  for (int i = 0; i < inputString.length(); i++)

        // +------------------------------------------------------------+
        // | If there are only numeric or . or : test hours, minutes    |
        // | and seconds                                                |
        // +------------------------------------------------------------+
        if (!test)   {
            // +------------------------------------------------------------+
            // | test hours                                                 |
            // +------------------------------------------------------------+
            int itest = Itest.valueOf(inputString.substring(0,2)).intValue();
            if (!(itest >= 0 && itest <= 23)) test = true;

            // +------------------------------------------------------------+
            // | test minutes                                               |
            // +------------------------------------------------------------+
            itest = Itest.valueOf(inputString.substring(3,5)).intValue();
            if (!(itest >= 0 && itest <= 59)) test = true;

            // +------------------------------------------------------------+
            // | test minutes                                               |
            // +------------------------------------------------------------+
            itest = Itest.valueOf(inputString.substring(6,8)).intValue();
            if (!(itest >= 0 && itest <= 59)) test = true;
        }  //  if (!test)

        return test;
    }  //  public boolean isNotTime(String inputString)   {


    /**
     *  'mod's 2 integers, and returns true if the remainder = 0.
     * @param count  the number to be devided into
     * @param m      the number to be devided
     * @return  true/false
     */
    public static boolean mod (int count, int m) {
        int i1 = count / m;
        int i2 = count - i1 * m;
        return (i2 == 0);
    }   // mod



    // +============================================================+
    // | Various file-related methods.                              |
    // +============================================================+

    /**
     * Open a file
     * @param   fileName    the file name
     * @param   mode        the file mode: r or rw
     * @return  the file
     *--------------*/
    public RandomAccessFile openFile(String fileName, String mode) {
        //System.out.println("in " + thisClass + ".openFile");
        RandomAccessFile dataFile = null;
        try {
            dataFile = new RandomAccessFile(fileName,mode);
        } catch(Exception e) {
            processError(e, thisClass, "openFile",
                "can not open file " + fileName);
        } //try-catch
        return dataFile;
    } // openFile

    /**
     * Close a file
     * @param   file    the file
     */
    public void closeFile(RandomAccessFile file) {
        try {
            file.close();
        } catch(Exception e) {
            processError(e, thisClass, "closeFile",
                "can not close file ");
        } //try-catch
    } // openFile

    /**
     * Skip to end of file
     * @param   file    the file
     */
    public void skipToEndOfFile(RandomAccessFile file) {
        try {
            file.skipBytes((int)file.length());
        } catch(Exception e) {
            processError(e, thisClass, "skipToEndOfFile",
                "can not skip to end of file");
        } //try-catch
    } // skipToEndOfFile

    /**
     *  Open an output file and append a line of data.
     * @param  fileName  the file name
     * @param  dataLine  the line to be appended to the file
     */
    public void writeFileLine(String fileName, String dataLine) {
        //System.out.println("edmCommonPC.writeFileLine()" + nl);
        try  {
            RandomAccessFile file = new RandomAccessFile(fileName , "rw");
            file.skipBytes((int)file.length());
//            file.writeBytes(dataLine + "\015");
            file.writeBytes(dataLine + nl);
            file.close();
        } catch (Exception e) {
            System.out.println("edmCommonPC: writeFileLine Error: " +
                e.getMessage());
            e.printStackTrace();
        }  //  catch (Exception e)

    }  //  public void writeFileLine(String fileName, String dataLine)

    /**
     *  Write a line and CR to a RandomAccessFile
     * @param  file      the file
     * @param  dataLine  the line to be appended to the file
     */
    public void writeFileLine(RandomAccessFile file, String dataLine) {
        try  {
            file.writeBytes(dataLine + nl);
        } catch (Exception e) {
            System.out.println("edmCommonPC: writeFileLine Error: " +
                e.getMessage());
            e.printStackTrace();
        }  //  catch (Exception e)

    }  //  public void writeFileLine(String fileName, String dataLine)


    /**
     * Write a line and CR to a RandomAccessFile (System.getProperty("line.separator"))
     * @param  file      the file
     * @param  dataLine  the line to be appended to the file
     */
    public void writeFileLineBR(RandomAccessFile file, String dataLine) {
        try  {
            file.writeBytes(dataLine + br);
        } catch (Exception e) {
            System.out.println("edmCommonPC: writeFileLine Error: " +
                e.getMessage());
            e.printStackTrace();
        }  //  catch (Exception e)

    } // writeFileLineBR


    /**
     * Get the next valid line from the data file (empty lines are skipped)
     * @param  lfile      the file
     * @return  the next valid line
     */
    public String getNextValidLine(RandomAccessFile lfile) {
        String line = "";
        try {
            while (lfile.getFilePointer() < lfile.length()) {
                line = lfile.readLine();
                if (!line.equals("")) break;
            }   //  while (lfile.getFilePointer() < lfile.length()) {
        } catch (Exception e) {
            System.out.println(nl + "edmCommonPC: getNextValidLine Error: " +
                e.getMessage());
            e.printStackTrace();
        } // try {
        return line;
    }   //  void getNextValidLine()


    /**
     * Get the next line from the data file
     * @param  lfile      the file
     * @return  the next valid line
     */
    public String getNextLine(RandomAccessFile lfile) {
        String line = "";
        try {
            if (lfile.getFilePointer() < lfile.length()) {
                line = lfile.readLine();
            } // if (lfile.getFilePointer() < lfile.length())
        } catch (Exception e) {
            System.out.println(nl + "edmCommonPC: getNextLine Error: " +
                e.getMessage());
            e.printStackTrace();
        } // try {
        return line;
    }   //  void getNextLine()


    /**
     * Check whether EOF has been reached on input RandomAccessFile file
     * @param  lfile      the file
     * @return true/false
     */
    public boolean eof(RandomAccessFile lfile) {
        boolean eofVar = false;
        try {
            if (lfile.getFilePointer() == lfile.length()) {
                eofVar = true;
            }
        } catch (Exception e) {
            System.out.println(nl + "edmCommonPC: eof Error: " +
                e.getMessage());
            e.printStackTrace();
        } // try {
        return eofVar;
    }   //  void getNextValidLine()


    /**
     *  Create a new file.
     * @param  infile  the name of the file to be created
     */
    public void createNewFile(String infile) {
        //File file = new File(infile);
        try {
//           if (!file.exists()) {
             writeFileLine(infile, " ");
//             }  //  if (!file.exists()
        } catch (Exception e) {
            System.out.println(nl + "edmCommonPC: createNewFile Error: " +
                e.toString());
            e.printStackTrace();
        }  //  catch (Exception e)
    }  //  public void createNewFile(String file)


    /**
     *  Deletes a file if it exists.
     * @param  infile  the name of the file to be deleted
     */
    public void deleteOldFile(String infile) {
        File file = new File(infile);
        if (file.exists()) {
            file.delete();
        }  //  if (mtlFile.exists()
    }  //  public void deleteOldFile(String file)


    /**
     *  Checks wheter a file exists
     * @param  infile  the name of the file to be checked
     * @return true/false
     */
    public boolean fileExists(String infile) {
        File file = new File(infile);
        return (file.exists() ? true : false );
        //boolean exists = false;
        //if (file.exists()) {
        //    exists = true;
        //}  //  if (file.exists()
        //return exists;
    }  //  public void fileExists(String file)


    //-----------------------------------------------------------//
    // Convert a String Time Date into a Timestamp format        //
    //-----------------------------------------------------------//

    /** Convert a String Time Date into a Timestamp format
     * The string can have 4 (yyyy), 7 (yyyy-mm), 10 (yyyy-mm-dd),
     * 13(yyyy-mm-dd hh), 16(yyyy-mm-dd hh:mi) or 19(yyyy-mm-dd hh:mi:ss)
     * characters for the date,  and 5(hh:mi) or 8(hh:mi:ss) characters
     * for the time.  For time values, '1970-01-01' is added for the date
     * part of the dateTime variable.
     * @param  dateTime  the string to be converted to a Timestamp
     * @return the Timestamp
     */
    public Timestamp setDateTime(String dateTime) {
        Timestamp dateTimeT = Timestamp.valueOf("1970-01-01 00:00:00");
        try {
            int len = dateTime.length();
            switch (len) {
            // date &/or times
                case 4: dateTime += "-01";
                case 7: dateTime += "-01";
                case 10: dateTime += " 00";
                case 13: dateTime += ":00";
                case 16: dateTime += ":00"; break;
                // times only
                case 5: dateTime = "1970-01-01 " + dateTime + ":00"; break;
                case 8: dateTime = "1970-01-01 " + dateTime; break;
            } // switch
            dateTimeT = Timestamp.valueOf(dateTime);
        } catch (Exception e) { }
        return dateTimeT;
    } // method setDateTime

    //public Timestamp strToTs(String dateTimeIn)    {
    //
    //    int yy = new Integer(dateTimeIn.substring( 0, 4)).intValue() - 1900;
    //    int mm = new Integer(dateTimeIn.substring( 5, 7)).intValue() - 1;
    //    int dd = new Integer(dateTimeIn.substring( 8,10)).intValue();
    //    int hh = new Integer(dateTimeIn.substring(11,13)).intValue();
    //    int mi = new Integer(dateTimeIn.substring(14,16)).intValue();
    //    int ss = new Integer(dateTimeIn.substring(17,19)).intValue();
    //
    //    Timestamp ts = new Timestamp(yy,mm,dd,hh,mi,ss,0);
    //    return ts;
    //}    //   public Timestamp strToTs(String dateTimeIn)


    /**
     * get the current date and time
     */
    public String getDateTime() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd't'HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    } // getDateTime


    // +============================================================+
    // | Methods to format strings, ints and floats for output      |
    // +============================================================+

    /**
     *  Set the filler character for the 'frm' functions.
     * @param  fill  the filler character - default = ' '
     */
    public void setFrmFiller (String fill) {
       frmFiller = fill;
    }   // setFrmFiller


    /**
     *  Get the filler character for the 'frm' functions.
     * @return frmFiller  the filler character - default = ' '
     */
    public String getFrmFiller () {
       return frmFiller;
    }   // getFrmFiller


    /**
     * Format a float for output (right justified), e.g. frm(12.3,6,2) will
     * return a string ' 12.30'.
     * @param  fvalue  the value to be formatted
     * @param  width  the total width of the formatted number
     * @param  prec   the number of digits after the decimal point
     * @return  the float in the requested format
     */
    public String frm (float fvalue, int width, int prec) {
        /*
        boolean isNeg = false;
        //if (dbg) System.out.print ("float: " + value + ":" + width + ":" + prec + "*");  // debug
        System.out.println("value = " + String.valueOf(value));
        if (value < 0) { isNeg = true;  value *= -1; }
        long mult = 1;
        for (int i=0; i<prec; i++) mult *= 10;
        value = value * mult;
        //if (dbg)
        System.out.print (value + ":");  // debug
        //if (value < 0f) value -= 0.5f;
        //else value += 0.5f;
        value += 0.5f;
        //if (dbg)
        System.out.print (value + ":");  // debug
        long ivalue = (long) value;     // debug ????
        //if (dbg)
        System.out.print (ivalue + "*");  // debug
        //String svalue = new Long(ivalue).toString();
        String svalue = String .valueOf(ivalue);
        if (ivalue < mult) {
        int stop = prec-svalue.length()+1;
        for (int i=0; i<stop; i++)
            svalue = "0" + svalue;
        } // if (ivalue < mult)
        if (isNeg) svalue = "-" + svalue;
        if (dbg) System.out.print (ivalue + "*" + svalue + "*");   // debug
        String rvalue = "";
        for (int i=0; i<width-svalue.length()-1; i++) rvalue = rvalue + frmFiller;
        rvalue = rvalue + svalue.substring(0,svalue.length()-prec) +
            "." + svalue.substring(svalue.length()-prec);
        //if (dbg)
        System.out.println (rvalue + "*");   // debug
        return rvalue;
        */

        return frm((double)fvalue, width, prec);

        /*
        // check for negative values
        boolean isNeg = false;
        if (dbg) System.out.println ("float: fvalue = " + fvalue + ":" + width + ":" + prec + "*");  // debug
        double value = (double) fvalue;
        if (value < 0) { isNeg = true;  value *= -1; }

        // add '.5' for truncation
        double divideBy = 1;
        for (int i = 0; i < prec; i++) divideBy *= 10;
        double correct = 0.5 / divideBy;
        if (dbg) System.out.println ("float: correct = " + correct + "*");  // debug
        value += correct;
        if (dbg) System.out.println ("float: value = " + value + "*");  // debug

        // get the string value
        String svalue = String.valueOf(value);
        int dotPos = svalue.indexOf(".");
        int ePos = svalue.indexOf("E");
        if (dbg) System.out.println("svalue = " + svalue + ", dotPos = " + dotPos + ", ePos = " + ePos);

        // if in e-notation, convert to normal
        if (ePos > -1) {
            // get the positive 'e-value'
            int evalue = -Integer.parseInt(svalue.substring(ePos+1));
            // get value without . and E
            svalue = svalue.substring(0,dotPos) + svalue.substring(dotPos+1,ePos);
            // add 0's in front
            for (int i=0; i<evalue-1; i++) svalue = "0" + svalue;
            // add 0. in front
            svalue = "0." + svalue;
            // get new dotPos for later processing
            dotPos = svalue.indexOf(".");
        } // if (ePos > -1)

        // get correct amount of decimal digits
        int decimals = svalue.length() - dotPos;
        if (decimals <= prec) {
            for (int i=0; i<prec-decimals+1; i++) svalue += "0";
        } else {
            svalue = svalue.substring(0, dotPos+prec+1);
        } // if (ivalue < mult)

        // correct for negative values
        if (isNeg) svalue = "-" + svalue;
        if (dbg) System.out.println ("float: svalue = " + svalue + "*");  // debug

        // fill up with leading fillers
        String rvalue = "";
        for (int i=0; i<width-svalue.length(); i++) rvalue += frmFiller;
        rvalue += svalue;
        if (dbg) System.out.println ("float: rvalue = " + rvalue + "*");  // debug
        if (dbg) System.out.println("");
        return rvalue;
        */
    }   // frm (float value


    /**
     * Format a double for output (right justified), e.g. frm(12.3,6,2) will
     * return a string ' 12.30'.
     * @param  fvalue  the value to be formatted
     * @param  width  the total width of the formatted number
     * @param  prec   the number of digits after the decimal point
     * @return  the float in the requested format
     */
    public String frm (double fvalue, int width, int prec) {            // ub03

        // check for negative values
        boolean isNeg = false;
        if (dbg) System.out.println ("double: fvalue = " + fvalue + ":" + width + ":" + prec + "*");  // debug
        double value = (double) fvalue;
        if (value < 0) { isNeg = true;  value *= -1; }

        // add '.5' for truncation
        double divideBy = 1;
        for (int i = 0; i < prec; i++) divideBy *= 10;
        double correct = 0.5 / divideBy;
        if (dbg) System.out.println ("double: correct = " + correct + "*");  // debug
        value += correct;
        if (dbg) System.out.println ("double: value = " + value + "*");  // debug

        // get the string value
        String svalue = String.valueOf(value);
        int dotPos = svalue.indexOf(".");
        int ePos = svalue.indexOf("E");
        if (dbg) System.out.println("svalue = " + svalue + ", dotPos = " + dotPos + ", ePos = " + ePos);

        // if in e-notation, convert to normal
        if (ePos > -1) {
            // get the positive 'e-value'
            int evalue = -Integer.parseInt(svalue.substring(ePos+1));
            // get value without . and E
            svalue = svalue.substring(0,dotPos) + svalue.substring(dotPos+1,ePos);
            // add 0's in front
            for (int i=0; i<evalue-1; i++) svalue = "0" + svalue;
            // add 0. in front
            svalue = "0." + svalue;
            // get new dotPos for later processing
            dotPos = svalue.indexOf(".");
        } // if (ePos > -1)

        // get correct amount of decimal digits
        int decimals = svalue.length() - dotPos;
        if (decimals <= prec) {
            for (int i=0; i<prec-decimals+1; i++) svalue += "0";
        } else {
            svalue = svalue.substring(0, dotPos+prec+1);
        } // if (ivalue < mult)

        // correct for negative values
        if (isNeg) svalue = "-" + svalue;
        if (dbg) System.out.println ("double: svalue = " + svalue + "*");  // debug

        // fill up with leading fillers
        String rvalue = "";
        for (int i=0; i<width-svalue.length(); i++) rvalue += frmFiller;
        rvalue += svalue;
        if (dbg) System.out.println ("double: rvalue = " + rvalue + "*");  // debug
        if (dbg) System.out.println("");
        return rvalue;
    }   // frm (double value


    /**
     * Format an int for output (right justified), e.g. frm(12,4) will return
     * a string '  12'
     * @param  value  the value to be formatted
     * @param  width  the total width of the formatted number
     * @return  the int in the requested format
     */
    public String frm (int value, int width) {
        if (dbg) System.out.print ("int: " + value + ":" + width + "*");   // debug
        String svalue = new Integer(value).toString();
        String rvalue = "";
        for (int i=0; i<width-svalue.length(); i++) rvalue = rvalue + frmFiller;
        rvalue = rvalue + svalue;
        if (dbg) System.out.println (svalue + "*" + rvalue + "*");   // debug
        return rvalue;
    }   // frm (int value


    /**
     * Format a string for output (left justified), e.g. frm ('abc',5) will
     * return 'abc  '
     * @param  value  the value to be formatted
     * @param  width  the total width of the formatted string,
     * @return  the int in the requested format,
     */
    public String frm (String value, int width) {
        if (dbg) System.out.print ("String: " + value + ":" + width + "*");   // debug
        String svalue = value.trim();
        String rvalue = svalue;
        if (dbg) System.out.print (svalue.length() + "*");   // debug
        if (width > svalue.length())
            for (int i=0; i<width-svalue.length(); i++) rvalue = rvalue + frmFiller;
        else rvalue = rvalue.substring(0,width);
        if (dbg) System.out.println (svalue + "*" + rvalue + "*");   // debug
        return rvalue;
    }   // frm (String value


    // +============================================================+
    // | Methods for converting null values to 9's, and vice versa  |
    // +============================================================+


    /**
     * convert a null float into 9's
     * @param  value  the number to be converted
     * @return a 9999.99 if the value was null, else the value itself   // ub02
     */
    public float nullToNines (float value) {
        return nullToNines(value, 9999.99f);                            // ub02
    } //  nullToNines (float value) {


    /**
     * convert a null float into 9's, specific 9's value
     * @param  value  the number to be converted
     * @param  defaultValue  the default value for a null variable
     * @return a defaultValue if the value was null, else the value itself
     */
    public float nullToNines (float value, float defaultValue) {
        if (value == Float.MIN_VALUE)  value = defaultValue;
        return value;
    } // nullToNines (float value, float defaultValue) {


    /**
     * convert a null int into 9's
     * @param  value  the number to be converted
     * @return a 9999 if the value was null, else the value itself      // ub02
     */
    public int nullToNines (int value) {
        return nullToNines(value, 9999);                                // ub02
    } //  nullToNines (int value) {


    /**
     * convert a null int into 9's, specific 9's value
     * @param  value  the number to be converted
     * @param  defaultValue  the default value for a null variable
     * @return a defaultValue if the value was null, else the value itself
     */
    public int nullToNines (int value, int defaultValue) {
        if (value == Integer.MIN_VALUE)  value = defaultValue;
        return value;
    } // nullToNines (int value, int defaultValue) {


    /**
     * convert a null String to '9'
     * @param  value  the number to be converted
     * @return a '9' if the value was null, else the value itself
     */
    public String nullToNines (String value) {
        return nullToNines (value, "9");
    } // String nullToNones (String value)


    /**
     * convert a null String to '9', specific '9' value
     * @param  value  the number to be converted
     * @param  defaultValue  the default value for a null variable
     * @return a defaultValue if the value was null, else the value itself
     */
    public String nullToNines (String value, String defaultValue) {
        if (value == null) value = defaultValue;
        return value;
    } // String nullToNones (String value, String defaultValue)


    /**
     * convert 9's to a null
     * @param  value  the number to be converted
     * @return the miminum allowable value allowed if the value is >= than
     *          9999.99, else the value itself                          // ub02
     */
    public float ninestoNull (float value) {
        return ninestoNull(value, 9999.99f);                            // ub02
    } // ninestoNull (float value) {


    /**
     * convert 9's to a null, specific 9's value
     * @param  value  the number to be converted
     * @param  limitValue  the default value for a null variable
     * @return the miminum allowable value allowed if the value is >= than
     *          the limitValue, else the value itself
     */
    public float ninestoNull (float value, float limitValue) {
        if (value >= limitValue)  value = Float.MIN_VALUE;
        return value;
    } // ninestoNull (float value, float limitValue) {


    /**
     * convert 9's to a null
     * @param  value  the number to be converted
     * @return the miminum allowable value allowed if the value is >= than
     *          9999, else the value itself                             // ub02
     */
    public int ninestoNull (int value) {
        return ninestoNull(value, 9999);                                // ub02
    } // ninestoNull (int value) {


    /**
     * convert 9's to a null, specific 9's value
     * @param  value  the number to be converted
     * @param  limitValue  the default value for a null variable
     * @return the miminum allowable value allowed if the value is >= than
     *          the limitValue, else the value itself
     */
    public int ninestoNull (int value, int limitValue) {
        if (value >= limitValue)  value = Integer.MIN_VALUE;
        return value;
    } // ninestoNull (int value, int limitValue) {


    // +============================================================+
    // | Miscellaneous stuff                                        |
    // +============================================================+

    /**
     * compare 2 float values to 3rd decimal (for lat's & long's),
     * true if 'equal'
     * @param  value1  the first of the values to compare
     * @param  value2  the second of the values to compare
     * @return  true/false
     */
    public boolean cmp3Decs (float value1, float value2) {
        int ival1 = new Float(value1 * 1000).intValue();
        int ival2 = ival1 + 1;
        float fval1 = new Float (ival1 / 1000.).floatValue();
        float fval2 = new Float (ival2 / 1000.).floatValue();
        return (((fval1 <= value2) && (value2 <= fval2)) ? true : false);
    } // float3Decimals (float value1, float value2) {

    /**
     * returns the 'floor' of a value, depending on the scale given, e.g.
     * floor(12.34, 0) will return 12, floor(12.78, 1) will return 12.7, and
     * floor (-12.34, 0) will return -13
     * @param  value    the value to be used
     * @param  scale    the scale value
     * @return the 'floor'ed value
     */
    public float floor(float value, int scale) {
        int factor = 1;
        if (scale > 0) factor = scale * 10;
        value = value * factor;
        int ivalue = (int) value;
        if (ivalue < 0) ivalue = ivalue - 1;
        value = (float) ivalue / factor;
        return value;
        //double value2 = (double) value + 0.1f;
        //BigDecimal bigValue = new BigDecimal(value2);
        //float floatValue = bigValue.divide(new BigDecimal(1),scale,
        //                    BigDecimal.ROUND_FLOOR).floatValue();
        //return floatValue;
    } //  floor ( float value1, int ival) {

    /**
     * returns the 'ceiling' of a value, depending on the scale given, e.g.
     * ceiling(12.34, 0) will return 13, ceiling(12.78, 1) will return 12.8, and
     * ceiling (-12.34, 0) will return -12
     * @param  value    the value to be used
     * @param  scale    the scale value
     * @return the 'ceiling'ed value
     */
    public float ceiling(float value, int scale) {
        int factor = 1;
        if (scale > 0) factor = scale * 10;
        value = value * factor;
        int ivalue = (int) value + 1;
        if (ivalue < 0) ivalue = ivalue - 1;
        value = (float) ivalue / factor;
        return value;
        //double value2 = (double) value;
        //BigDecimal bigValue = new BigDecimal(value2);
        //float floatValue = bigValue.divide(new BigDecimal(1),scale,
        //                    BigDecimal.ROUND_CEILING).floatValue();
        //return floatValue;
    }

    /**
     * return the bigger of two integer values
     * @param  ival1  first value
     * @param  ival2  second value
     * @return  the bigger of the two values
     */
    public int max(int ival1, int ival2) {
        return (ival1 > ival2 ? ival1 : ival2);
        //BigDecimal val1 = new BigDecimal((double)ival1);
        //BigDecimal val2 = new BigDecimal((double)ival2);
        //return val1.max(val2).intValue();
    } // max

    /**
     * return the smaller of two integer values
     * @param  ival1  first value
     * @param  ival2  second value
     * @return  the smaller of the two values
     */
    public int min(int ival1, int ival2) {
        return (ival1 < ival2 ? ival1 : ival2);
        //BigDecimal val1 = new BigDecimal((double)ival1);
        //BigDecimal val2 = new BigDecimal((double)ival2);
        //return val1.min(val2).intValue();
    } // max

    /**
     * return the bigger of two float values
     * @param  ival1  first value
     * @param  ival2  second value
     * @return  the bigger of the two values
     */
    public float max(float ival1, float ival2) {
        return (ival1 > ival2 ? ival1 : ival2);
        //BigDecimal val1 = new BigDecimal((double)ival1);
        //BigDecimal val2 = new BigDecimal((double)ival2);
        //return val1.max(val2).floatValue();
    } // max

    /**
     * return the smaller of two float values
     * @param  ival1  first value
     * @param  ival2  second value
     * @return  the smaller of the two values
     */
    public float min(float ival1, float ival2) {
        return (ival1 < ival2 ? ival1 : ival2);
        //BigDecimal val1 = new BigDecimal((double)ival1);
        //BigDecimal val2 = new BigDecimal((double)ival2);
        //return val1.min(val2).floatValue();
    } // max

    /**
     * return the bigger of two String date values (yyyy-mm-dd)
     * @param  idate1  first value
     * @param  idate2  second value
     * @return  the bigger of the two values
     */
    public String max(String idate1, String idate2) {
        Timestamp val1 = Timestamp.valueOf(idate1 + " 00:00:00.0");
        Timestamp val2 = Timestamp.valueOf(idate2 + " 00:00:00.0");
        return (val1.after(val2) ? idate1 : idate2);
    } // min

    /**
     * return the smaller of two String date values (yyyy-mm-dd)
     * @param  idate1  first value
     * @param  idate2  second value
     * @return  the smaller of the two values
     */
    public String min(String idate1, String idate2) {
        Timestamp val1 = Timestamp.valueOf(idate1 + " 00:00:00.0");
        Timestamp val2 = Timestamp.valueOf(idate2 + " 00:00:00.0");
        return (val1.before(val2) ? idate1 : idate2);
    } // min

    /**
     * return the bigger of two Timestamp values
     * @param  ival1  first value
     * @param  ival2  second value
     * @return  the bigger of the two values
     */
    public Timestamp max(Timestamp ival1, Timestamp ival2) {
        return (ival1.after(ival2) ? ival1 : ival2);
    } // min

    /**
     * return the smaller of two Timestamp values
     * @param  ival1  first value
     * @param  ival2  second value
     * @return  the smaller of the two values
     */
    public Timestamp min(Timestamp ival1, Timestamp ival2) {
        return (ival1.before(ival2) ? ival1 : ival2);
    } // min


    /**
     * return the time difference between the two Timestamp values
     * @param  date1  first value
     * @param  date2  second value
     * @return  the time difference in hh24:m:ss
     */
    public String timeDiff(Timestamp date1, Timestamp date2) {
        long diff = max(date1, date2).getTime() - min(date1, date2).getTime();
        long hr = diff / 3600000;
        diff = diff - (hr * 3600000);
        long mn = diff / 60000;
        diff = diff - (mn * 60000);
        long sc = diff / 1000;
        setFrmFiller("0");
        String tDiff =
            frm((int)hr,2) + ":" + frm((int)mn,2) + ":" + frm((int)sc,2);
        setFrmFiller(" ");
        return tDiff;
    } // timeDiff


    /**
     * write the error message and stack trace to output
     * @param  e      Exception
     * @param  eClass class name in which error occured (String)
     * @param  method method name in which error occured (String)
     * @param  text   error text (String)
     */
    public void processError(
                Exception e, String eClass, String method, String text) {
        System.out.println(
            nl + "ERROR in " + eClass + "." + method +
            ":" + nl + text);
        e.printStackTrace(System.out);
        //System.out.println("</pre>");
        e.printStackTrace();

        System.out.println (nl + "Please e-mail Ursula von St Ange at " +
            "uvstange@csir.co.za " +
            "with the error details by pasting the error into the e-mail body");

    } // processError

    /**
     * write the error message and stack trace to output
     * @param  e      Exception
     * @param  eClass class name in which error occured (String)
     * @param  method method name in which error occured (String)
     * @param  text   error text (String)
     */
    public static void processErrorStatic(
                Exception e, String eClass, String method, String text) {
        System.out.println(
            nl + "ERROR in " + eClass + "." + method +
            ":" + nl  + text);
        e.printStackTrace(System.out);
        //System.out.println("</pre>");
        e.printStackTrace();

        System.out.println (nl + "Please e-mail Ursula von St Ange at " +
            "uvstange@csir.co.za " +
            "with the error details by pasting the error into the e-mail body");

    } // processErrorStatic

    /**
     * Sort an array of String in Case order.
     * @param  list   Array to be sorted (String[])
     * @param  cnt    The number of items in the array (int)
     * @return        The sorted array
     */
    public String[] bubbleSort(String[] list, int cnt) {
        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < cnt-i-1; j++) {
                if (list[j].compareTo(list[j+1]) > 0) {
                    String temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                } // if (list[j].compareTo(list[j+1]) > 0)
            } // for j
        } // for i
        return list;
    } // bubbleSort

    /**
     * Sort an array of String Ignoring the case.
     * @param  list   Array to be sorted (String[])
     * @param  cnt    The number of items in the array (int)
     * @return        The sorted array
     */
    public String[] bubbleSortIgnoreCase(String[] list, int cnt) {
        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < cnt-i-1; j++) {
                if (list[j].compareToIgnoreCase(list[j+1]) > 0) {
                    String temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                } // if (list[j].compareTo(list[j+1]) > 0)
            } // for j
        } // for i
        return list;
    } // bubbleSort

    /**
     * Sort an array of integers
     * @param  list   Array to be sorted (int[])
     * @param  cnt    The number of items in the array (int)
     * @return        The sorted array
     */
    public int[] bubbleSort(int[] list, int cnt) {
        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < cnt-i-1; j++) {
                if (list[j] > list[j+1]) {
                    int temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                } // if (list[j].compareTo(list[j+1]) > 0)
            } // for j
        } // for i
        return list;
    } // bubbleSort


    /**
     * Sort an array of floats
     * @param  list   Array to be sorted (float[])
     * @param  cnt    The number of items in the array (int)
     * @return        The sorted array
     */
    public float[] bubbleSort(float[] list, int cnt) {
        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < cnt-i-1; j++) {
                if (list[j] > list[j+1]) {
                    float temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                } // if (list[j].compareTo(list[j+1]) > 0)
            } // for j
        } // for i
        return list;
    } // bubbleSort


    /**
     * Replace a CR with a <nl> in a string
     * @param  text   The text to change
     * @return        The change text
     */
    public String replaceCrBR(String text) {
        String newText = "";
        int pos = text.indexOf('\n');
        while (pos >= 0) {
            newText += text.substring(0, pos-1) + "<nl>";
            text = text.substring(pos+1);
            pos = text.indexOf('\n');
        } // while
        newText += text;
        return newText;
    } // replaceCrBR


    /**
     * Replace a <nl> with a CR in a string
     * @param  text   The text to change
     * @return        The change text
     */
    public String replaceBRCr(String text) {
        String newText = "";
        int pos = text.indexOf("<nl>");
        while (pos >= 0) {
            newText += text.substring(0, pos) + '\n';
            text = text.substring(pos+4);
            pos = text.indexOf("<nl>");
        } // while
        newText += text;
        return newText;
    } // replaceBRCr

}  //  class edmCommonPC
