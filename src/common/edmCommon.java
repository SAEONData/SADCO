package common;

import java.io.RandomAccessFile;
//import java.sql.*;
import oracle.html.*;
//import java.math.*;
//import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.PrintWriter;
//import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class contains a set of general purpose methods.
 *
 * @author 000328 - SIT Group
 * @version
 * 000328 - Neville Paynter    - create class                               <br>
 * 000505 - Neville Paynter    - change frm methods from static to public   <br>
 * 000505 - Neville Paynter    - add method floatNullToNines                <br>
 * 020709 - Ursula von St Ange - add Note: to latlonRangeInput prompt       <br>
 * 030724 - Ursula von St Ange - ub01 - add stuff for servlets              <br>
 */
public class edmCommon extends edmCommonPC {

    /** The filler character used for formatted output */
    static String frmFiller = " ";
    static String br = "<br>";
    //static String newLine = System.getProperty("line.separator");
    // when one downloads the .wav files to PC with file, save link as,
    // it does not convert to \r\n for PC
    static String newLine = "\r\n";



    /** For debugging code: true/false */
    static boolean dbg = false;
    //static boolean dbg = true;
    RandomAccessFile dbgF = null;

    public edmCommon() {
        try {
            if (dbg) dbgF = new RandomAccessFile(
                "/home/localuser/debugs/" + "common.edmCommon.dbg", "rw");
        } catch (Exception e) {}
    } // edmCommon

    protected void finalize() throws Throwable {
        try {
            if (dbg) dbgF.close();
        } catch (Exception e) {}
        super.finalize();
    } // finalize

    // +============================================================+
    // | Methods to get remote IP address                           |
    // +============================================================+
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


    public static void print2Log(String callingClass, String logFile, String text) {
        try {
            //System.out.println(wc.HOSTDIR+"logs/WASA.log");
            //System.out.println(text);
            PrintWriter out = new PrintWriter(new BufferedWriter(
                new FileWriter("/home/localuser/website_logs/" + logFile, true)));
            out.println(text);
            out.close();
        } catch (IOException e) {
            processErrorStatic(e, callingClass, "print2Log", "ERROR");
        } // try-catch
    } // print2Log


    // +============================================================+
    // | Methods to manipulate html things                          |
    // +============================================================+

    /**
     * Convert HttpServletRequest-type parameters to application-type args
     * @param  req   the HttpServletRequest with the arguments from the form or URL
     * @return the array of argument name=values pairs
     */

    static public String[] convertReq2Args(HttpServletRequest req) {    // ub01


        // get the names
        String[] names = new String[10000];
        int i = 0;
        for (java.util.Enumeration e = req.getParameterNames(); e.hasMoreElements(); ) {
            names[i++] = (String)e.nextElement();
        } // for (java.util.Enumeration e
        int cnt = i;

        // count the values
        int numValues = 0;
        for (int j = 0; j < cnt; j++) {
            String[] values = req.getParameterValues(names[j]);
            numValues += values.length;
        } // for (int j = 0; j < cnt; j++)

        // get the parameter-value pairs
        String args[] = new String[numValues];
        int cnt2 = 0;
        for (int j = 0; j < cnt; j++) {
            String[] values = req.getParameterValues(names[j]);
            for (int k = 0; k < values.length; k++) {
                args[cnt2++] = names[j] + "=" + values[k];
            } // for (k = 0; k < values.length; k++)
        } // for (int j = 0; j < cnt; j++)
/*

        // do it with the querystring
        String parameters = req.getQueryString();
        StringTokenizer t = new StringTokenizer(parameters, "&");

        int cnt = 0;
        while (t.hasMoreTokens()) {
            cnt++;
        } // while (t.hasMoreTokens())

        String args[] = new String[cnt];
        int i = 0;
        t = new StringTokenizer(parameters, "&");
        while (t.hasMoreTokens()) {
            args[i++] = t.nextToken();
        } // while (t.hasMoreTokens())
*/
        return args;

    } // convertReq2Args


    /**
     * Look up a URL parameter
     * @param  req   the HttpServletRequest with the arguments from the form or URL
     * @param  name  the argument name
     * @return the argument value or empty string if the parameter does not exist
     */
//    public String getArgument(HttpServletRequest req, String name) {  // ub01
//        String value = req.getParameter(name);
//        return (value != null ? value : "");
//    } //  public String getArgument


    /**
     * Look up a URL parameter with multiple entries
     * @param  req   the HttpServletRequest with the arguments from the form or URL
     * @param  name  the argument name
     * @return the array of argument values
     */
//    public String[] getArguments(HttpServletRequest req, String name) {   // ub01
//        String[] values = req.getParameterValues(name);
//        return values;
//    } //  public String[] getArguments


    // +============================================================+
    // | Methods for internet stuff                                 |
    // +============================================================+

    /**
     * Format text strings for a html page, setting the font size = 2
     * @param  oldStr  the text string
     * @return the Container containing the formatted string
     */
    public Container formatStr(String oldStr)   {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(2));
        return newStrc;
    }  //  public Container formatStr(String oldStr)


    /**
     * Format text strings for a html page, setting the font size = 3 (big)
     * @param  oldStr  the text string
     * @return the Container containing the formatted string
     */
    public Container formatStrBig(String oldStr)   {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(3));
        return newStrc;
    }  //  public Container formatStrBig(String oldStr)


    // +----------------------------------+
    // | Methods to span columns in a row |
    // +----------------------------------+

    /**
     * Return a row where the datacell spans columns, bold, size,
     * colour and alignment chosen, as well as number of columns to span
     * @param  text  the text
     * @param  cols  the number of columns to be spanned
     * @param  bold  true/false
     * @param  colour  the colour, e.g. red, green, or 876543#
     * @param  align  the text alignment, IHAlign.LEFT or IHAlign.CENTER or
     *                 IHAlign.RIGHT
     * @param  size  the text size, e.g. +2, -3, 4
     * @return the table row
     */
    public TableRow crSpanColsRow(
            String text,
            int cols,
            boolean bold,
            String colour,
            int align,
            String size)   {

        if (dbg) try { dbgF.writeBytes("in crSpanColsRow" + newLine); } catch (Exception e) {}


        if (!colour.equals("") && !size.equals("")) {
            text = "<font color=" + colour + " size=" + size + ">" + text +
                "</font>";
        } else if (!size.equals("")) {
            text = "<font size=" + size + ">" + text + "</font>";
        } else if (!colour.equals("")) {
            text = "<font color=" + colour + ">" + text + "</font>";
        } // if colour & size
        if (dbg) try { dbgF.writeBytes("text1 = " + text + newLine); } catch (Exception e) {}
        if (bold) {
            text = "<b>" + text + "</b>";
        } // if bold
        if (dbg) try { dbgF.writeBytes("text2 = " + text + newLine); } catch (Exception e) {}
        TableRow row = new TableRow();
        row.addCell(
            new TableDataCell(text).setColSpan(cols).setHAlign(align));
        if (dbg) try { dbgF.writeBytes("row = " + row.toHTML() + newLine); } catch (Exception e) {}
        return row;
    }  // TableRow crSpanColsRow

    /**
     * Return a row where the datacell spans columns, bold,
     * colour and alignment chosen, as well as number of columns to span
     * @param  text  the text
     * @param  cols  the number of columns to be spanned
     * @param  bold  true/false
     * @param  colour  the colour, e.g. red, green, or 876543#
     * @param  align  the text alignment, IHAlign.LEFT or IHAlign.CENTER or
     *                 IHAlign.RIGHT
     * @return the table row
     */
    public TableRow crSpanColsRow(
            String text,
            int cols,
            boolean bold,
            String colour,
            int align)   {
        return crSpanColsRow(text, cols, bold, colour, align, "");
    }  // TableRow crSpanColsRow

    /**
     * Return a row where the datacell spans columns, bold and centered
     * @param  text  the text
     * @param  cols  the number of columns to be spanned
     * @return the table row
     */
    public TableRow crSpanColsRow(String text, int cols)   {
        return crSpanColsRow (text, cols, true, "", IHAlign.CENTER, "");
    }  // TableRow crSpanColsRow

    /**
     * Return a row where the datacell spans 2 columns, bold and centered
     * @param  text  the text
     * @return the table row
     */
    public TableRow crSpan2ColRow(String text)   {
        return crSpanColsRow (text, 2, true, "", IHAlign.CENTER, "");
    }  // TableRow crSpan2ColRow


    // +---------------------------------+
    // | Methods to return 1 column rows |
    // +---------------------------------+

    /**
     * Return a 1-column row, bold, colour and alignment chosen
     * @param  text  the text
     * @param  bold  true/false
     * @param  colour  the colour, e.g. red, green, or 876543#
     * @param  align  the text alignment, IHAlign.LEFT/IHAlign.CENTER/IHAlign.RIGHT
     * @param  size  the text size, e.g. +2, -3, 4
     * @return the table row
     */
    public TableRow cr1ColRow(
            String text,
            boolean bold,
            String colour,
            int align,
            String size)   {
        if (!colour.equals("") && !size.equals("")) {
            text = "<font color=" + colour + " size=" + size + ">" + text +
                "</font>";
        } else if (!size.equals("")) {
            text = "<font size=" + size + ">" + text + "</font>";
        } else if (!colour.equals("")) {
            text = "<font color=" + colour + ">" + text + "</font>";
        } // if colour & size
        if (bold) {
            text = "<b>" + text + "</b>";
        }
        TableRow row = new TableRow();
        row.addCell(new TableDataCell(text).setHAlign(align));
        return row;
    }  // TableRow cr1ColRow

    /**
     * Return a 1-column row, bold, colour and alignment chosen
     * @param  text  the text
     * @param  bold  true/false
     * @param  colour  the colour, e.g. red, green, or 876543#
     * @param  align  the text alignment, IHAlign.LEFT/IHAlign.CENTER/IHAlign.RIGHT
     * @return the table row
     */
    public TableRow cr1ColRow(
            String text,
            boolean bold,
            String colour,
            int align)   {
        return cr1ColRow(text, bold, colour, align, "");
    }  // TableRow cr1ColRow

    /**
     * Return a 1-column row, bold and colour chosen, left aligned
     * @param  text  the text
     * @param  colour  the colour, e.g. red, green, or 876543#
     * @return the table row
     */
    public TableRow cr1ColRow(
            String text,
            boolean bold,
            String colour)   {
        return cr1ColRow(text, bold, colour, IHAlign.LEFT, "");
    }  // TableRow cr1ColRow

    /**
     * Return a 1-column row, with text left-aligned, not bold
     * @param  text  the text
     * @return the table row
     */
    public TableRow cr1ColRow(String text)   {
        return cr1ColRow(text, false, "", IHAlign.LEFT, "");
    }  // TableRow cr1ColRow


    // +---------------------------------+
    // | Methods to return 2 column rows |
    // +---------------------------------+

    /**
     * Return a 2-column row, with the LH cell bold and right-aligned, text
     * @param  prompt  the prompt for the LH cell
     * @param  value  the value for the RH cell
     * @return the table row
     */
    public TableRow cr2ColRow(String prompt, String value)   {
        TableRow row = new TableRow();
        row.addCell(new TableHeaderCell(prompt).setHAlign(IHAlign.RIGHT))
            .addCell(new TableDataCell(value))
            ;
        return row;
    }  // TableRow cr2ColRow

    /**
     * Return a 2-column row, with the LH cell bold and right-aligned, int
     * @param  prompt  the prompt for the LH cell
     * @param  value  the value for the RH cell
     * @return the table row
     */
    public TableRow cr2ColRow(String prompt, int value)   {
        return cr2ColRow(prompt, new Integer(value).toString());
    }  // TableRow cr2ColRow

    /**
     * Return a 2-column row, with the LH cell bold and right-aligned,
     * input, display & maxlength different, with String default
     * @param  prompt  the prompt for the LH cell
     * @param  name  the name of the text input field for the RH cell
     * @param  len  the length of the text input field
     * @param  maxLen  the maximum length of the text input field
     * @param  defValue  the default value for the text input field
     * @return the table row
     */
    public TableRow cr2ColRow
            (String prompt,
             String name,
             int len,
             int maxLen,
             String defValue)   {
        String value = new TextField(name,maxLen,len,defValue).toHTML();
        return cr2ColRow(prompt, value);
    }  // TableRow cr2ColRow

    /**
     * Return a 2-column row, with the LH cell bold and right-aligned,
     * input, display & maxlength different, with String default
     * @param  prompt  the prompt for the LH cell
     * @param  name  the name of the text input field for the RH cell
     * @param  len  the length of the text input field
     * @param  maxLen  the maximum length of the text input field
     * @param  defValue  the default value for the text input field
     * @param  compulsory  the character that shows this is a non-optianal field
     * @return the table row
     */
    public TableRow cr2ColRow
            (String prompt,
             String name,
             int len,
             int maxLen,
             String defValue,
             String compulsory)   {
        String value = new TextField(name,maxLen,len,defValue).toHTML() +
            compulsory;
        return cr2ColRow(prompt, value);
    }  // TableRow cr2ColRow

    /**
     * Return a 2-column row, with the LH cell bold and right-aligned,
     * input, display & maxlength same, with String default
     * @param  prompt  the prompt for the LH cell
     * @param  name  the name of the text input field for the RH cell
     * @param  len  the length (and maximum length) of the text input field
     * @param  defValue  the default value for the text input field
     * @return the table row
     */
    public TableRow cr2ColRow
            (String prompt, String name, int len, String defValue)   {
        return cr2ColRow(prompt, name, len, len, defValue);
    }  // TableRow cr2ColRow

    /**
     * Return a 2-column row, with the LH cell bold and right-aligned,
     * input, with int default
     * @param  prompt  the prompt for the LH cell
     * @param  name  the name of the text input field for the RH cell
     * @param  len  the length (and maximum length) of the text input field
     * @param  defValue  the default value for the text input field
     * @return the table row
     */
    public TableRow cr2ColRow
            (String prompt, String name, int len, int defValue)   {
        return cr2ColRow(prompt, name, len, len, String.valueOf(defValue));
    }  // TableRow cr2ColRow

    /**
     * Return a 2-column row, with the LH cell bold and right-aligned,
     * input, with float default
     * @param  prompt  the prompt for the LH cell
     * @param  name  the name of the text input field for the RH cell
     * @param  len  the length (and maximum length) of the text input field
     * @param  defValue  the default value for the text input field
     * @return the table row
     */
    public TableRow cr2ColRow
            (String prompt, String name, int len, float defValue)   {
        return cr2ColRow(prompt, name, len, String.valueOf(defValue));
    }  // TableRow cr2ColRow

    /**
     * Return a 2-column row, with the LH cell bold and right-aligned, input
     * @param  prompt  the prompt for the LH cell
     * @param  name  the name of the text input field for the RH cell
     * @param  len  the length (and maximum length) of the text input field
     * @return the table row
     */
    public TableRow cr2ColRow(String prompt, String name, int len)   {
        return cr2ColRow(prompt, name, len, "");
    }  // TableRow cr2ColRow

    /**
     * Return a 2-column row, with the LH cell bold and right-aligned, Select
     * @param  prompt  the prompt for the LH cell
     * @param  sel  the drop-down box (Select) for the RH cell
     * @return the table row
     */
    public TableRow cr2ColRow(String prompt, Select sel)   {
        return cr2ColRow(prompt, sel.toHTML());
    }  // TableRow cr2ColRow


    // +---------------------------------+
    // | Methods to return 3 column rows |
    // +---------------------------------+

    /**
     * Return a 3-column row, with cells 1 & 3 bold and right-aligned, text
     * @param  prompt  the prompt for cell
     * @param  value1  the value for cell 2
     * @param  value2  the value for cell 3
     * @return the table row
     */
    public TableRow cr3ColRow
            (String prompt, String value1, String value2)   {
        TableRow row = new TableRow();
        row.addCell(new TableHeaderCell(prompt).setHAlign(IHAlign.RIGHT));
        row.addCell(new TableDataCell(value1));
        row.addCell(new TableDataCell(value2));
        return row;
    }  // TableRow cr3ColRow

    // +---------------------------------+
    // | Methods to return 4 column rows |
    // +---------------------------------+

    /**
     * Return a 4-column row, with cells 1 & 3 bold and right-aligned, text
     * @param  prompt1  the prompt for cell 1
     * @param  value1  the value for cell 2
     * @param  prompt2  the prompt for cell 3
     * @param  value2  the value for cell 4
     * @return the table row
     */
    public TableRow cr4ColRow
            (String prompt1, String value1, String prompt2, String value2)   {
        TableRow row = new TableRow();
        row.addCell(new TableHeaderCell(prompt1).setHAlign(IHAlign.RIGHT))
            .addCell(new TableDataCell(value1))
            ;
        row.addCell(new TableHeaderCell(prompt2).setHAlign(IHAlign.RIGHT))
            .addCell(new TableDataCell(value2))
            ;
        return row;
    }  // TableRow cr4ColRow

    /**
     * Return a 4-column row, with cells1 & 3 bold and right-aligned,
     * input, String default
     * @param  prompt1  the prompt for cell 1
     * @param  name1  the name of the text input field for cell 2
     * @param  len1  the length of the text input field in cell 2
     * @param  maxLen1  the maximum length of the text input field in cell 2
     * @param  defVal1  the default value for the text input field in cell 2
     * @param  prompt2  the prompt for cell 3
     * @param  name2  the name of the text input field for cell 4
     * @param  len2  the length of the text input field in cell 4
     * @param  maxLen2  the maximum length of the text input field in cell 4
     * @param  defVal2  the default value for the text input field in cell 4
     * @return the table row
     */
    public TableRow cr4ColRow
        (String prompt1, String name1, int len1, int maxLen1, String defVal1,
         String prompt2, String name2, int len2, int maxLen2, String defVal2) {
        String value1 = new TextField(name1,len1,maxLen1,defVal1).toHTML();
        String value2 = new TextField(name2,len2,maxLen2,defVal2).toHTML();
        return cr4ColRow(prompt1, value1, prompt2, value2);
    }  // TableRow cr4ColRow

    /**
     * Return a 4-column row, with cells1 & 3 bold and right-aligned,
     * input
     * @param  prompt1  the prompt for cell 1
     * @param  name1  the name of the text input field for cell 2
     * @param  len1  the length of the text input field in cell 2
     * @param  prompt2  the prompt for cell 3
     * @param  name2  the name of the text input field for cell 4
     * @param  len2  the length of the text input field in cell 4
     * @return the table row
     */
    public TableRow cr4ColRow
            (String prompt1, String name1, int len1,
             String prompt2, String name2, int len2)   {
        return cr4ColRow(
            prompt1, name1, len1, len1, "",
            prompt2, name2, len2, len2, "");
    }  // TableRow cr4ColRow


    // +--------------------------------------------+
    // | Methods to return tables with instructions |
    // +--------------------------------------------+

    /**
     * Return the instructions to download a file in a table
     * @return a html table containing the instructions
     */
    public DynamicTable downloadFileInstructions()   {
        String text = "<b>To download file to PC:</b><br>" +
            "Right-click on the hyperlink, and ...<br>" +
            "For Netscape: choose 'Save link as' (near bottom of list)<br>" +
            "For IE: choose 'Save target as' (near top of list)";

        DynamicTable table = new DynamicTable(1);
        table.setBorder(1);
        table.setFrame(ITableFrame.BOX);
        //table.addRow(cr1ColRow(text));

        TableRow row = new TableRow();
        row.addCell(new TableDataCell(text)
            .setBackgroundColor(Color.cyan))
            ;
        table.addRow(row);
        return table;
    }  // public DynamicTable downloadFileInstructions

    /**
     * Tells the user that the input file was not found
     * @return a html table containing the instructions
     */
    public DynamicTable displayFileNotFound(String fileName) {
        String text = "<br>The file <b>" + fileName +
            "</b> does not exist!<br><br>" +
            "Click on the 'Back' button below or on the Toolbar above, " +
            "<br>correct the file name and press the 'Go!' button";
        String formText = "<FORM>" + newLine +
            "<INPUT TYPE = \"BUTTON\" VALUE = \"Back\" " +
            "onClick = \"history.go(-1);\">" + newLine + "</FORM>";

        DynamicTable table = new DynamicTable(1);
        table.setBorder(1);
        table.setFrame(ITableFrame.BOX);
        table.addRow(cr1ColRow(text, false, "", IHAlign. CENTER));
        table.addRow(cr1ColRow(" ", false, "", IHAlign.CENTER));
        table.addRow(cr1ColRow(formText, false, "", IHAlign.CENTER));
        //System.out.println ("IHAlign.LEFT: " + IHAlign.LEFT); // == 1
        //System.out.println ("IHAlign.CENTER: " + IHAlign.CENTER); // == 2
        //System.out.println ("IHAlign.RIGHT: " + IHAlign.RIGHT); // == 3
        return table;
    }  // public DynamicTable displayFileNotFound

    /**
     * write some into to help the user at the top of the form
     * @param  formTable  The HTML table
     */
    public void writeHelpInfo(DynamicTable formTable) {

        //TableRow row;
        //row = new TableRow();
        //row.addCell(new TableDataCell
        //    ("<b>Please fill in the form, and click on the Go! button</b>")
        //    .setColSpan(2).setHAlign(IHAlign.CENTER));
        //formTable.addRow(row);
        formTable.addRow(crSpanColsRow(
            "Please fill in the form, and click on the Go! button", 2));

        //row = new TableRow();
        //row.addCell(new TableDataCell
        //    ("Compulsory fields are marked with an *<br><br>")
        //    .setColSpan(2).setHAlign(IHAlign.CENTER));
        //formTable.addRow(row);
        formTable.addRow(crSpanColsRow(
            "Compulsory fields are marked with an *<br><br>", 2));
    } // writeHelpInfo

    // +------------------------------------------+
    // | Methods to return tables with area input |
    // +------------------------------------------+

    /**
     * Return a row for lat/lon range input
     * @param  prompt      the prompt for the table
     * @param  length      the length for the text input boxes
     * @param  latNdefault the default value for latitude North
     * @param  latSdefault the default value for latitude South
     * @param  lonWdefault the default value for longtidue West
     * @param  lonEdefault the default value for longtidue East
     * @param  latNPrompt  the prompt for latitude North
     * @param  latSPrompt  the prompt for latitude South
     * @param  lonWPrompt  the prompt for longtidue West
     * @param  lonEPrompt  the prompt for longtidue East
     * @param  latNNameAdd any additional javascript stuff for latitude North
     * @param  latSNameAdd any additional javascript stuff for latitude South
     * @param  lonWNameAdd any additional javascript stuff for longtidue West
     * @param  lonENameAdd any additional javascript stuff for longtidue East
     * @param  compulsory  a "*" if the field is a compulsory field
     * @return the table row
     */
    public TableRow latlonRangeInput(
            String prompt,
            int length,
            String latNdefault,
            String latSdefault,
            String lonWdefault,
            String lonEdefault,
            String hemNdefault,
            String hemSdefault,
            String hemWdefault,
            String hemEdefault,
            String latNPrompt,
            String latSPrompt,
            String lonWPrompt,
            String lonEPrompt,
            String latNNameAdd,
            String latSNameAdd,
            String lonWNameAdd,
            String lonENameAdd,
            String compulsory)   {
        // constants for area
        String latitudeNorthName = "latitudenorth" + latNNameAdd;
        String latitudeSouthName = "latitudesouth" + latSNameAdd;
        String longitudeWestName = "longitudewest" + lonWNameAdd;
        String longitudeEastName = "longitudeeast" + lonENameAdd;
        String areaPrompt = "Enter latitude (N/S) and longitude (E/W) " +
            "limits in decimal degrees";

        if (!compulsory.equals("*")) compulsory = "&nbsp;";

        // geographical area
        DynamicTable subTable = new DynamicTable(8);
        subTable.setBorder(0).setCellPadding(0).setCellSpacing(0);
        subTable.setFrame(ITableFrame.VOLD);
        subTable.setRules(ITableRules.NONE);

        // longitude input boxes
        TableRow row = new TableRow();
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(compulsory + new TextField(
            "p" + longitudeWestName,length,length,lonWdefault).toHTML() +
            createSelect("phemwest","E","W",hemWdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(compulsory + new TextField(
            "p" + longitudeEastName,length,length,lonEdefault).toHTML() +
            createSelect("phemeast","E","W",hemEdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);

        // latitude North input box
        row = new TableRow();
        row.addCell(new TableDataCell(compulsory + new TextField(
            "p" + latitudeNorthName,length,length,latNdefault).toHTML() +
            createSelect("phemnorth","S","N",hemNdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----------+----------<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("----")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----------+----------<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);

        // latitude South input box
        row = new TableRow();
        row.addCell(new TableDataCell(compulsory + new TextField(
            "p" + latitudeSouthName,length,length,latSdefault).toHTML() +
            createSelect("phemsouth","S","N",hemSdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----------+----------<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("----")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----------+----------<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);


        // main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setBorder(0).setCellPadding(0).setCellSpacing(0);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);

        //prompt
        String cellContents = prompt;
        if ((cellContents.equals("p")) || (cellContents.equals(""))) {
            cellContents = areaPrompt;
        } // if ((cellContents.

        row = new TableRow();
        row.addCell(new TableDataCell(
            "<b>" + cellContents + "</b><br><br>").setHAlign(IHAlign.CENTER));
        mainTable.addRow(row);

        row = new TableRow();
        row.addCell(new TableDataCell(subTable.toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        mainTable.addRow(row);

        cellContents = mainTable.toHTML();

        row = new TableRow();
        row.addCell(new TableDataCell(cellContents)
            .setColSpan(2).setHAlign(IHAlign.CENTER));
        return row;

    }  // latlonRangeInput


    /**
     * Add options FOR N/S and E/W selection
     * @param   option1   the first option
     * @param   option2   the second option
     * @param   name      the variable name
     */
    Select createSelect(String name, String option1, String option2, String defOpt) {
        Select select = new Select(name);
        select.addOption(new Option(option1, option1, (defOpt.equals(option1)?true:false)));
        select.addOption(new Option(option2, option2, (defOpt.equals(option2)?true:false)));
        return select;
    } // createSelect


    /**
     * Return a row for lat/lon range input - with drop-down boxes
     * @param  prompt      the prompt for the table
     * @param  length      the length for the text input boxes
     * @param  latNdefault the default value for latitude North
     * @param  latSdefault the default value for latitude South
     * @param  lonWdefault the default value for longtidue West
     * @param  lonEdefault the default value for longtidue East
     * @param  latNPrompt  the prompt for latitude North
     * @param  latSPrompt  the prompt for latitude South
     * @param  lonWPrompt  the prompt for longtidue West
     * @param  lonEPrompt  the prompt for longtidue East
     * @param  latNNameAdd any additional javascript stuff for latitude North
     * @param  latSNameAdd any additional javascript stuff for latitude South
     * @param  lonWNameAdd any additional javascript stuff for longtidue West
     * @param  lonENameAdd any additional javascript stuff for longtidue East
     * @param  compulsory  a "*" if the field is a compulsory field
     * @return the table row
     */
    public TableRow latlonRangeInput(
            String prompt,
            int length,
            String latNdefault,
            String latSdefault,
            String lonWdefault,
            String lonEdefault,
            String latNPrompt,
            String latSPrompt,
            String lonWPrompt,
            String lonEPrompt,
            String latNNameAdd,
            String latSNameAdd,
            String lonWNameAdd,
            String lonENameAdd,
            String compulsory)   {
        // constants for area
        String latitudeNorthName = "latitudenorth" + latNNameAdd;
        String latitudeSouthName = "latitudesouth" + latSNameAdd;
        String longitudeWestName = "longitudewest" + lonWNameAdd;
        String longitudeEastName = "longitudeeast" + lonENameAdd;
        String areaPrompt = "Enter latitude (N/S) and longitude (E/W) " +
            "limits in decimal degrees<br>" +
            "(South = POSITIVE latitude, West = NEGATIVE longitude)";

        if (!compulsory.equals("*")) compulsory = "&nbsp;";

        // geographical area
        DynamicTable subTable = new DynamicTable(8);
        subTable.setBorder(0).setCellPadding(0).setCellSpacing(0);
        subTable.setFrame(ITableFrame.VOLD);
        subTable.setRules(ITableRules.NONE);

        // longitude input boxes
        TableRow row = new TableRow();
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(compulsory)
            .setHAlign(IHAlign.RIGHT).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(new TextField(
            "p" + longitudeWestName,length,length,lonWdefault).toHTML())
            .setColSpan(2).setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(new TextField(
            "p" + longitudeEastName,length,length,lonEdefault).toHTML())
            .setColSpan(2).setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(compulsory)
            .setHAlign(IHAlign.LEFT).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);

        // longitude prompts
        row = new TableRow();
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(lonWPrompt)
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell(lonEPrompt)
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);

            // latitude North input box
        row = new TableRow();
        row.addCell(new TableDataCell(compulsory));
        row.addCell(new TableDataCell(new TextField(
            "p" + latitudeNorthName,length,length,latNdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;&nbsp;" + latNPrompt)
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----+----<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("----")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----+----<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;&nbsp;&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);

        // latitude South input box
        row = new TableRow();
        row.addCell(new TableDataCell(compulsory));
        row.addCell(new TableDataCell(new TextField(
            "p" + latitudeSouthName,length,length,latSdefault).toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;&nbsp;" + latSPrompt)
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----+----<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("----")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("|<br>----+----<br>|")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        row.addCell(new TableDataCell("&nbsp;")
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        subTable.addRow(row);


        // main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setBorder(0).setCellPadding(0).setCellSpacing(0);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);

        //prompt
        String cellContents = prompt;
        if ((cellContents.equals("p")) || (cellContents.equals(""))) {
            cellContents = areaPrompt;
        } // if ((cellContents.
        cellContents +="<br><br></b><font size=-1>NOTE: Latitude North " +
                "and Longitude West values will be INCLUDED, but " +
                "Latitude South and Longitude East values will be " +
                "EXCLUDED</font><b>";

        row = new TableRow();
        row.addCell(new TableDataCell(
            "<b>" + cellContents + "</b><br><br>").setHAlign(IHAlign.CENTER));
        mainTable.addRow(row);

        row = new TableRow();
        row.addCell(new TableDataCell(subTable.toHTML())
            .setHAlign(IHAlign.CENTER).setVAlign(IVAlign.MIDDLE));
        mainTable.addRow(row);

        cellContents = mainTable.toHTML();

        /*// prompt
        String cellContents = prompt;
        if ((cellContents.equals("p")) || (cellContents.equals(""))) {
            cellContents = areaPrompt;
        }
        cellContents = "<b>" + cellContents + ":</b><br>";
        if (!compulsory.equals("*")) compulsory = "&nbsp;";

        // latitude North input box
        cellContents += compulsory + " " +
            new TextField(
                "p" + latitudeNorthName,
                length,
                length,
                latNdefault
            ).toHTML() + "N-|-------------|--<br>\n";
        // vertical bars
        for (int j = 0; j < 2; j++) {
            cellContents += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            for (int i = 0; i < length; i++) cellContents += "&nbsp;&nbsp;";
            cellContents += "|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>\n";
        } // for int j
        // latitude South input box
        cellContents += compulsory + " " +
            new TextField(
                "p" + latitudeSouthName,
                length,
                length,
                latSdefault
            ).toHTML() + "S-|-------------|--<br>\n";
        // longitude West and East names
        cellContents += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        for (int i = 0; i < length; i++) cellContents += "&nbsp;&nbsp;";
        cellContents += "W&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;E<br>\n";
        // longitude West and East input box
        cellContents += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        for (int i = 0; i < length; i++) cellContents += "&nbsp;&nbsp;";
        cellContents += compulsory + " " +
            new TextField("p" + longitudeWestName,length,length,
                lonWdefault).toHTML() + "&nbsp;&nbsp;&nbsp;&nbsp;" +
            new TextField("p" + longitudeEastName,length,length,
                lonEdefault).toHTML() + " " + compulsory + "<br>";
*/
/*        // latitude North input box
        cellContents = cellContents + "<p>" +
            new TextField(
                "p" + latitudeNorthName,
                length,
                length,
                latNdefault
            ).toHTML() + compulsory;
        cellContents = cellContents + "<br><b>N&nbsp;<br>|&nbsp;";
        // longitude West input box
        cellContents = cellContents + "<br>" + compulsory +
            new TextField(
                "p" + longitudeWestName,
                length,
                length,
                lonWdefault
            ).toHTML();
        cellContents = cellContents + "W------|------E";
        // longitude East input box
        cellContents = cellContents +
            new TextField(
                "p" + longitudeEastName,
                length,
                length,
                lonEdefault
            ).toHTML() + compulsory;
        cellContents = cellContents +
            "&nbsp;&nbsp;<br>|&nbsp;<br>S&nbsp;";
        // latitude South input box
        cellContents = cellContents + "<br>" +
            new TextField(
                "p" + latitudeSouthName,
                length,
                length,
                latNdefault
            ).toHTML() + compulsory + "<br><br>";
*/
        row = new TableRow();
        row.addCell(new TableDataCell(cellContents)
            .setColSpan(2).setHAlign(IHAlign.CENTER));
        return row;
    }  // latlonRangeInput

    /**
     * Return a row for lat/lon range input
     * @param  prompt      the prompt for the table
     * @param  length      the length for the text input boxes
     * @param  latNdefault the default value for latitude North
     * @param  latSdefault the default value for latitude South
     * @param  lonWdefault the default value for longtidue West
     * @param  lonEdefault the default value for longtidue East
     * @return the table row
     */
    public TableRow latlonRangeInput(
            String prompt,
            int length,
            String latNdefault,
            String latSdefault,
            String lonWdefault,
            String lonEdefault)   {
        return latlonRangeInput(prompt, length, latNdefault, latSdefault,
            lonWdefault, lonEdefault, "N", "S", "W", "E",
            "", "", "", "", "");
    }  // latlonRangeInput

    /**
     * Return a row for lat/lon range input
     * @param  prompt      the prompt for the table
     * @param  length      the length for the text input boxes
     * @param  latNdefault the default value for latitude North
     * @param  latSdefault the default value for latitude South
     * @param  lonWdefault the default value for longtidue West
     * @param  lonEdefault the default value for longtidue East
     * @param  compulsory  a "*" if the field is a compulsory field
     * @return the table row
     */
    public TableRow latlonRangeInput(
            String prompt,
            int length,
            String latNdefault,
            String latSdefault,
            String lonWdefault,
            String lonEdefault,
            String compulsory)   {
        return latlonRangeInput(prompt, length, latNdefault, latSdefault,
            lonWdefault, lonEdefault, "N", "S", "W", "E",
            "", "", "", "", compulsory);
    }  // latlonRangeInput

    /**
     * Return a row for lat/lon range input
     * @param  prompt      the prompt for the table
     * @param  length      the length for the text input boxes
     * @return the table row
     */
    public TableRow latlonRangeInput(
            String prompt,
            int length)  {
        return latlonRangeInput(prompt, length, "", "", "", "",
            "N", "S", "W", "E", "", "", "", "", "");
    }  // latlonRangeInput

    /**
     * return the JSL for testing for lat/lon range input
     * @param precision  the number of digits before the decimal point
     * @param scale  the number of digits after the decimal point
     * @param optional    = true / false
     * @return the container with the javascript code
     */
    public Container latlonRangeJS(int precision, int scale, boolean optional) {
        Container con = new Container();
        // constants for area
        String latitudeNorthName = "latitudenorth";
        String latitudeSouthName = "latitudesouth";
        String longitudeWestName = "longitudewest";
        String longitudeEastName = "longitudeeast";
        String latitudeNorthPrompt = "Latitude North";
        String latitudeSouthPrompt = "Latitude South";
        String longitudeWestPrompt = "Longitude West";
        String longitudeEastPrompt = "Longitude East";
        // also checking for precision here - makes it less
        // frustrating for the user if all testing gets done
        // at the same time
        // latitude north
        if (!optional) {
            con.addItem(common2.JSLLib.CallNotNull(
                "p" + latitudeNorthName, latitudeNorthPrompt));
        } // if (!optional)
        con.addItem(common2.JSLLib.CallChkNumPrecision(
            "p" + latitudeNorthName, latitudeNorthPrompt, precision));
        con.addItem(common2.JSLLib.CallChkNumScale(
            "p" + latitudeNorthName, latitudeNorthPrompt, scale));
        // longitude west
        if (!optional) {
            con.addItem(common2.JSLLib.CallNotNull(
                "p" + longitudeWestName, longitudeWestPrompt));
        } // if (!optional)
        con.addItem(common2.JSLLib.CallChkNumPrecision(
            "p" + longitudeWestName, longitudeWestPrompt, precision));
        con.addItem(common2.JSLLib.CallChkNumScale(
            "p" + longitudeWestName, longitudeWestPrompt, scale));
        // longitude east
        if (!optional) {
            con.addItem(common2.JSLLib.CallNotNull(
                "p" + longitudeEastName, longitudeEastPrompt));
        } // if (!optional)
        con.addItem(common2.JSLLib.CallChkNumPrecision(
            "p" + longitudeEastName, longitudeEastPrompt, precision));
        con.addItem(common2.JSLLib.CallChkNumScale(
            "p" + longitudeEastName, longitudeEastPrompt, scale));
        // latitude south
        if (!optional) {
            con.addItem(common2.JSLLib.CallNotNull(
                "p" + latitudeSouthName, latitudeSouthPrompt));
        } // if (!optional)
        con.addItem(common2.JSLLib.CallChkNumPrecision(
            "p" + latitudeSouthName, latitudeSouthPrompt, precision));
        con.addItem(common2.JSLLib.CallChkNumScale(
            "p" + latitudeSouthName, latitudeSouthPrompt, scale));
        return con;
    } // latlonRangeJS


    // +---------------------------------------------------------------+
    // | Methods to return tables with daterange / datetimerange input |
    // +---------------------------------------------------------------+

    /**
     * return a table for the daterange or datetimerange input
     * @param table  the html table to which the rows should be added
     * @param inputType   = daterange / datetimerange
     * @param length  the lenght of the input fields
     * @param dateStartDefault the default start date
     * @param timeStartDefault the default start time
     * @param dateEndDefault the default end date
     * @param timeEndDefault the default end date
     * @param  compulsory  a "*" if the field is a compulsory field
     * @return the html table with the rows added
     */
    public DynamicTable datetimeRangeInput(
            DynamicTable table,
            String inputType,
            int length,
            String dateStartDefault,
            String timeStartDefault,
            String dateEndDefault,
            String timeEndDefault,
            String compulsory)   {
        // constants for date/time
        String dateStartName = "startdate";
        String dateEndName   = "enddate";
        String timeStartName = "starttime";
        String timeEndName   = "endtime";
        String dateStartPrompt = "Start Date (yyyy-mm-dd)";
        String dateEndPrompt   = "End Date (yyyy-mm-dd)";
        String timeStartPrompt = "Start Time (hh:mi)";
        String timeEndPrompt   = "End Time (hh:mi)";

        // daterange / datetimerange
        // start date
        TableRow row = new TableRow();
        row.addCell(new TableDataCell
            ("<b>" + dateStartPrompt + ":</b>").setHAlign(IHAlign.RIGHT));
        row.addCell(new TableDataCell(
            new TextField("p" + dateStartName, length, length,
                dateStartDefault) + compulsory));
        table.addRow(row);
        // start time
        if (inputType.equals("datetimerange")) {
            row = new TableRow();
            row.addCell(new TableDataCell
                ("<b>" + timeStartPrompt + ":</b>").setHAlign(IHAlign.RIGHT));
            row.addCell(new TableDataCell(
                new TextField("p" + timeStartName, length, length,
                    timeStartDefault) + compulsory));
            table.addRow(row);
        } // if datetimerange
        // end date
        row = new TableRow();
        row.addCell(new TableDataCell
            ("<b>" + dateEndPrompt + ":</b>").setHAlign(IHAlign.RIGHT));
        row.addCell(new TableDataCell(
            new TextField("p" + dateEndName, length, length,
                dateEndDefault) + compulsory));
        table.addRow(row);
        // end time
        if (inputType.equals("datetimerange")) {
            row = new TableRow();
            row.addCell(new TableDataCell
                ("<b>" + timeEndPrompt + ":</b>").setHAlign(IHAlign.RIGHT));
            row.addCell(new TableDataCell(
                new TextField("p" + timeEndName, length, length,
                    timeEndDefault) + compulsory));
            table.addRow(row);
        } // datetimerange
        return table;
    } // datetimeRangeInput

    /**
     * return a table for the daterange or datetimerange input
     * @param table  the html table to which the rows should be added
     * @param inputType   = daterange / datetimerange
     * @param length  the lenght of the input fields
     * @param dateStartDefault the default start date
     * @param timeStartDefault the default start time
     * @param dateEndDefault the default end date
     * @param timeEndDefault the default end date
     * @return the html table with the rows added
     */
    public DynamicTable datetimeRangeInput(
            DynamicTable table,
            String inputType,
            int length,
            String dateStartDefault,
            String dateEndDefault,
            String timeStartDefault,
            String timeEndDefault)   {
        return datetimeRangeInput(table, inputType, length, dateStartDefault,
            dateEndDefault, timeStartDefault, timeEndDefault, "");
    } // datetimeRangeInput

    /**
     * return a table for the daterange or datetimerange input
     * @param table  the html table to which the rows should be added
     * @param inputType   = daterange / datetimerange
     * @param length  the lenght of the input fields
     * @return the html table with the rows added
     */
    public DynamicTable datetimeRangeInput(
            DynamicTable table,
            String inputType,
            int length) {
        return datetimeRangeInput(table, inputType, length, "", "", "", "", "");
    } // datetimeRangeInput

    /**
     * Return the JSL for testing for datetime / datetimerange input
     * @param inputType   = daterange / datetimerange
     * @param optional    = true / false
     * @return the container with the javascript code
     */
    public Container datetimeRangeJS(String inputType, boolean optional) {
        Container con = new Container();
        // constants for date/time
        String dateStartName = "startdate";
        String dateEndName   = "enddate";
        String timeStartName = "starttime";
        String timeEndName   = "endtime";
        String dateStartPrompt = "Start Date (yyyy-mm-dd)";
        String dateEndPrompt   = "End Date (yyyy-mm-dd)";
        String timeStartPrompt = "Start Time (hh:mi)";
        String timeEndPrompt   = "End Time (hh:mi)";
        // also checking for correct date/time formats here - makes it less
        // frustrating for the user if all testing gets done
        // at the same time
        // latitude north
        if (!optional) {
            con.addItem(common2.JSLLib.CallNotNull(
                "p" + dateStartName, dateStartPrompt));
        } // if (!optional) {
        con.addItem(common2.JSLLib.CallChkDate(
            "p" + dateStartName, dateStartPrompt));
        if (inputType.equals("datetimerange")) {
            if (!optional) {
                con.addItem(common2.JSLLib.CallNotNull(
                    "p" + timeStartName, timeStartPrompt));
            } // if (!optional) {
            con.addItem(common2.JSLLib.CallChkTime(
                "p" + timeStartName, timeStartPrompt));
        } // if datetimerange
        if (!optional) {
            con.addItem(common2.JSLLib.CallNotNull(
                "p" + dateEndName, dateEndPrompt));
        } // if (!optional) {
        con.addItem(common2.JSLLib.CallChkDate(
            "p" + dateEndName, dateEndPrompt));
        if (inputType.equals("datetimerange")) {
            if (!optional) {
                con.addItem(common2.JSLLib.CallNotNull(
                    "p" + timeEndName, timeEndPrompt));
            } // if (!optional) {
            con.addItem(common2.JSLLib.CallChkTime(
                "p" + timeEndName, timeEndPrompt));
        } // if datetimerange
        if (inputType.equals("daterange")) {
            con.addItem(common2.JSLLib.CallCmpDates(
                "p" + dateStartName, "p" + dateEndName));
        } else {
            con.addItem(common2.JSLLib.CallCmpDateTimes(
                "p" + dateStartName, "p" + timeStartName,
                "p" + dateEndName, "p" + timeEndName));
        } // if daterange
        return con;
    } // datetimeRangeJS


    // +------------------------------------+
    // | Methods for select pull-down boxes |
    // +------------------------------------+

    /**
     * Identify the select item that is to be flagged as default.
     * @param  item  the text in the drop-down box
     * @param  prevSelect  the text of the default value for the drop-down box
     * @return true/false, whether item is the default or not
     */
/*    public static boolean getDefault(String item, String prevSelect)   {
        boolean defS;
        if (item.equals(prevSelect))   {
            defS = true;
        }  else   {
            defS = false;
        }  //  if (item.equals(prevSelect))

        return defS;
    }  //  public boolean getDefault(String item, String year)

*/
    // +============================================================+
    // | Methods for SADCO                                          |
    // +============================================================+


    /**
     * Submit a job to run in the background
     * @param command The command to be submitted to the system (String)
     */
/*    public static void submitJob(String command)  {

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

        if (dbg) System.out.println("<br>command = <br>" + command + "<br>");
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command); //the batch/ script file
            if (dbg) {
                process.waitFor();
                System.out.println("<br><b>OUTPUT</b>");
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
                String line = "nothing read";
                while (in.ready()) {
                    try {
                        System.out.println("<br>" + in.readLine());
                    } catch (Exception e) {
                        System.out.println("end of input?" + e.getMessage());
                        break;
                    } // try-catch
                } // while in
                System.out.println("<br><b>END OF OUTPUT</b></br>");
                System.out.println("<br><b>ERROR</b>");
                BufferedReader error = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
                line = "nothing read";
                while (error.ready()) {
                    try {
                        System.out.println("<br>" + error.readLine());
                    } catch (Exception e) {
                        System.out.println("end of error?" + e.getMessage());
                        break;
                    } // try-catch
                } // while error
                System.out.println("<br><b>END OF ERROR</b></br>");
            } // if (dbg)
        } catch (Exception e) {
            System.out.println("<br>edmCommon submitJob error: " + e.getMessage());
            e.printStackTrace();
        }

    } // SubmitJob


    /**
     *  Create a new sequential station_id (SADCO marine)
     * @param  stationIdPre the stationId prefix
     * @param  stationId    the stationId
     */
/*    public String getNewStationId(String stationIdPre, String stationId)   {
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

*/
    // +============================================================+
    // | Methods for testing numeric, time, mod                     |
    // +============================================================+

    /**
     *  Tests whether a String is numeric (contains other characters
     * than '0' to '9', '.' and '-').
     * @param inputString  the string to test
     * @return  true/false
     */
/*    public boolean isNotNumeric(String inputString)   {
        boolean test = false;

        for (int i = 0; i < inputString.length(); i++)    {
            char ch = inputString.charAt(i);
            if (!((ch >= '0' && ch <= '9') || ch == '.' || ch == '-'))
                test = true;
        }  //  for (int i = 0; i < inputString.length(); i++)

        return test;
    }  //  public boolean isNotNumeric(String inputString)

*/
    /**
     *  Test whether the string is a valid time field (hh:mm:ss)
     * @param inputString  the string to test
     * @return  true/false
     */
/*    public boolean isNotTime(String inputString)   {
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

*/
    /**
     *  'mod's 2 integers, and returns true if the remainder = 0.
     * @param count  the number to be devided into
     * @param m      the number to be devided
     * @return  true/false
     */
/*    public static boolean mod (int count, int m) {
        int i1 = count / m;
        int i2 = count - i1 * m;
        return (i2 == 0);
    }   // mod

*/

    // +============================================================+
    // | Various file-related methods.                              |
    // +============================================================+

    /**
     *  Open an output file and append a line of data.
     * @param  fileName  the file name
     * @param  dataLine  the line to be appended to the file
     */
/*
    public void writeFileLine(String fileName, String dataLine) {
        try  {
            RandomAccessFile file = new RandomAccessFile(fileName , "rw");
            file.skipBytes((int)file.length());
//            file.writeBytes(dataLine + "\015");
            file.writeBytes(dataLine + newLine);
            file.close();
        } catch (Exception e) {
            System.out.println("<br>edmCommon: writeFileLine Error: " +
                e.getMessage());
            e.printStackTrace();
        }  //  catch (Exception e)

    }  //  public void writeFileLine(String fileName, String dataLine)
*/
    /**
     *  Write a line and CR to a RandomAccessFile
     * @param  file      the file
     * @param  dataLine  the line to be appended to the file
     */
/*
    public void writeFileLine(RandomAccessFile file, String dataLine) {
        try  {
            file.writeBytes(dataLine + newLine);
        } catch (Exception e) {
            System.out.println("<br>edmCommon: writeFileLine Error: " +
                e.getMessage());
            e.printStackTrace();
        }  //  catch (Exception e)

    }  //  public void writeFileLine(String fileName, String dataLine)
*/

    /**
     * Get the next valid line from the data file (empty lines are skipped)
     * @param  file      the file
     * @return  the next valid line
     */
/*    public String getNextValidLine(RandomAccessFile lfile) {
        String line = "";
        try {
            while (lfile.getFilePointer() < lfile.length()) {
                line = lfile.readLine();
                if (!line.equals("")) break;
            }   //  while (lfile.getFilePointer() < lfile.length()) {
        } catch (Exception e) {
            System.out.println("<br>edmCommon: getNextValidLine Error: " +
                e.getMessage());
            e.printStackTrace();
        } // try {
        return line;
    }   //  void getNextValidLine()

*/
    /**
     * Check whether EOF has been reached on input RandomAccessFile file
     * @param  file      the file
     * @return true/false
     */
/*    public boolean eof(RandomAccessFile lfile) {
        boolean eofVar = false;
        try {
            if (lfile.getFilePointer() == lfile.length()) {
                eofVar = true;
            }
        } catch (Exception e) {
            System.out.println("<br>edmCommon: eof Error: " +
                e.getMessage());
            e.printStackTrace();
        } // try {
        return eofVar;
    }   //  void getNextValidLine()

*/
    /**
     *  Create a new file.
     * @param  infile  the name of the file to be created
     */
/*    public void createNewFile(String infile) {
        //File file = new File(infile);
        try {
//           if (!file.exists()) {
             writeFileLine(infile, " ");
//             }  //  if (!file.exists()
        } catch (Exception e) {
            System.out.println("<br>edmCommon: createNewFile Error: " +
                e.toString());
            e.printStackTrace();
        }  //  catch (Exception e)
    }  //  public void createNewFile(String file)

*/
    /**
     *  Deletes a file if it exists.
     * @param  infile  the name of the file to be deleted
     */
/*    public void deleteOldFile(String infile) {
        File file = new File(infile);
        if (file.exists()) {
            file.delete();
        }  //  if (mtlFile.exists()
    }  //  public void deleteOldFile(String file)

*/
    /**
     *  Checks wheter a file exists
     * @param  infile  the name of the file to be checked
     * @return true/false
     */
/*    public boolean fileExists(String infile) {
        File file = new File(infile);
        return (file.exists() ? true : false );
        //boolean exists = false;
        //if (file.exists()) {
        //    exists = true;
        //}  //  if (file.exists()
        //return exists;
    }  //  public void fileExists(String file)

*/
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
/*    public Timestamp setDateTime(String dateTime) {
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
*/
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


    // +============================================================+
    // | Methods to format strings, ints and floats for output      |
    // +============================================================+

    /**
     *  Set the filler character for the 'frm' functions.
     * @param  fill  the filler character - default = ' '
     */
/*    public void setFrmFiller (String fill) {
       frmFiller = fill;
    }   // setFrmFiller

*/
    /**
     * Format a float for output (right justified), e.g. frm(12.3,6,2) will
     * return a string ' 12.30'.
     * @param  value  the value to be formatted
     * @param  width  the total width of the formatted number
     * @param  prec   the number of digits after the decimal point
     * @return  the float in the requested format
     */
/*    public String frm (float value, int width, int prec) {
      boolean isNeg = false;
      //if (dbg) System.out.print ("float: " + value + ":" + width + ":" + prec + "*");  // debug
      if (value < 0) { isNeg = true;  value *= -1; }
      int mult = 1;
      for (int i=0; i<prec; i++) mult *= 10;
      value = value * mult;
      //if (dbg) System.out.print (value + ":");  // debug
      //if (value < 0f) value -= 0.5f;
      //else value += 0.5f;
      value += 0.5f;
      //if (dbg) System.out.print (value + ":");  // debug
      int ivalue = (int) value;     // debug ????
      //if (dbg) System.out.print (ivalue + "*");  // debug
      String svalue = new Integer(ivalue).toString();
      if (ivalue < mult) {
        int stop = prec-svalue.length()+1;
        for (int i=0; i<stop; i++)
          svalue = "0" + svalue;
      }
      if (isNeg) svalue = "-" + svalue;
      if (dbg) System.out.print (ivalue + "*" + svalue + "*");   // debug
      String rvalue = "";
      for (int i=0; i<width-svalue.length()-1; i++) rvalue = rvalue + frmFiller;
      rvalue = rvalue + svalue.substring(0,svalue.length()-prec) +
               "." + svalue.substring(svalue.length()-prec);
      if (dbg) System.out.println (rvalue + "*");   // debug
      return rvalue;
    }   // frm (float value
*/

    /**
     * Format an int for output (right justified), e.g. frm(12,4) will return
     * a string '  12'
     * @param  value  the value to be formatted
     * @param  width  the total width of the formatted number
     * @return  the int in the requested format
     */
/*    public String frm (int value, int width) {
      if (dbg) System.out.print ("int: " + value + ":" + width + "*");   // debug
      String svalue = new Integer(value).toString();
      String rvalue = "";
      for (int i=0; i<width-svalue.length(); i++) rvalue = rvalue + frmFiller;
      rvalue = rvalue + svalue;
      if (dbg) System.out.println (svalue + "*" + rvalue + "*");   // debug
      return rvalue;
    }   // frm (int value
*/

    /**
     * Format a string for output (left justified), e.g. frm ('abc',5) will
     * return 'abc  '
     * @param  value  the value to be formatted
     * @param  width  the total width of the formatted string,
     * @return  the int in the requested format,
     */
/*    public String frm (String value, int width) {
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
*/

    // +============================================================+
    // | Methods for converting null values to 9's, and vice versa  |
    // +============================================================+


    /**
     * convert a null float into 9's
     * @param  value  the number to be converted
     * @return a 999.99 if the value was null, else the value itself
     */
/*    public float nullToNines (float value) {
        return nullToNines(value, 999.99f);
    } //  nullToNines (float value) {

*/
    /**
     * convert a null float into 9's, specific 9's value
     * @param  value  the number to be converted
     * @param  defaultValue  the default value for a null variable
     * @return a defaultValue if the value was null, else the value itself
     */
/*    public float nullToNines (float value, float defaultValue) {
        if (value == Float.MIN_VALUE)  value = defaultValue;
        return value;
    } // nullToNines (float value, float defaultValue) {
*/

    /**
     * convert a null int into 9's
     * @param  value  the number to be converted
     * @return a 999 if the value was null, else the value itself
     */
/*    public int nullToNines (int value) {
        return nullToNines(value, 999);
    } //  nullToNines (int value) {

*/
    /**
     * convert a null int into 9's, specific 9's value
     * @param  value  the number to be converted
     * @param  defaultValue  the default value for a null variable
     * @return a defaultValue if the value was null, else the value itself
     */
/*    public int nullToNines (int value, int defaultValue) {
        if (value == Integer.MIN_VALUE)  value = defaultValue;
        return value;
    } // nullToNines (int value, int defaultValue) {

*/
    /**
     * convert a null String to '9'
     * @param  value  the number to be converted
     * @return a '9' if the value was null, else the value itself
     */
/*    public String nullToNines (String value) {
        return nullToNines (value, "9");
    } // String nullToNones (String value)

*/
    /**
     * convert a null String to '9', specific '9' value
     * @param  value  the number to be converted
     * @param  defaultValue  the default value for a null variable
     * @return a defaultValue if the value was null, else the value itself
     */
/*    public String nullToNines (String value, String defaultValue) {
        if (value == null) value = defaultValue;
        return value;
    } // String nullToNones (String value, String defaultValue)

*/
    /**
     * convert 9's to a null
     * @param  value  the number to be converted
     * @return the miminum allowable value allowed if the value is >= than
     *          999.99, else the value itself
     */
/*    public float ninestoNull (float value) {
        return ninestoNull(value, 999.99f);
    } // ninestoNull (float value) {

*/
    /**
     * convert 9's to a null, specific 9's value
     * @param  value  the number to be converted
     * @param  limitValue  the default value for a null variable
     * @return the miminum allowable value allowed if the value is >= than
     *          the limitValue, else the value itself
     */
/*    public float ninestoNull (float value, float limitValue) {
        if (value >= limitValue)  value = Float.MIN_VALUE;
        return value;
    } // ninestoNull (float value, float limitValue) {

*/
    /**
     * convert 9's to a null
     * @param  value  the number to be converted
     * @return the miminum allowable value allowed if the value is >= than
     *          999, else the value itself
     */
/*    public int ninestoNull (int value) {
        return ninestoNull(value, 999);
    } // ninestoNull (int value) {

*/
    /**
     * convert 9's to a null, specific 9's value
     * @param  value  the number to be converted
     * @param  limitValue  the default value for a null variable
     * @return the miminum allowable value allowed if the value is >= than
     *          the limitValue, else the value itself
     */
/*    public int ninestoNull (int value, int limitValue) {
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
/*    public boolean cmp3Decs (float value1, float value2) {
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
/*    public float floor(float value, int scale) {
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
/*    public float ceiling(float value, int scale) {
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
/*    public int max(int ival1, int ival2) {
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
/*    public int min(int ival1, int ival2) {
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
/*    public float max(float ival1, float ival2) {
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
/*    public float min(float ival1, float ival2) {
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
/*    public String max(String idate1, String idate2) {
        Timestamp val1 = Timestamp.valueOf(idate1 + " 00:00:00.0");
        Timestamp val2 = Timestamp.valueOf(idate2 + " 00:00:00.0");
        return (val1.after(val2) ? idate1 : idate2);
    } // min
*/
    /**
     * return the smaller of two String date values (yyyy-mm-dd)
     * @param  idate1  first value
     * @param  idate2  second value
     * @return  the smaller of the two values
     */
/*    public String min(String idate1, String idate2) {
        Timestamp val1 = Timestamp.valueOf(idate1 + " 00:00:00.0");
        Timestamp val2 = Timestamp.valueOf(idate2 + " 00:00:00.0");
        return (val1.before(val2) ? idate1 : idate2);
    } // min
*/
    /**
     * return the bigger of two Timestamp values
     * @param  ival1  first value
     * @param  ival2  second value
     * @return  the bigger of the two values
     */
/*    public Timestamp max(Timestamp ival1, Timestamp ival2) {
        return (ival1.after(ival2) ? ival1 : ival2);
    } // min
*/
    /**
     * return the smaller of two Timestamp values
     * @param  ival1  first value
     * @param  ival2  second value
     * @return  the smaller of the two values
     */
/*    public Timestamp min(Timestamp ival1, Timestamp ival2) {
        return (ival1.before(ival2) ? ival1 : ival2);
    } // min
*/

    /**
     * return the time difference between the two Timestamp values
     * @param  ival1  first value
     * @param  ival2  second value
     * @return  the time difference in hh24:m:ss
     */
/*    public String timeDiff(Timestamp date1, Timestamp date2) {
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
*/

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
            "<br>" + newLine + "<font color=red size=+1>ERROR in " +
            eClass + "." + method + "</font>" +
            ":<br>" + newLine + text + "<br><pre>");
        e.printStackTrace(System.out);
        System.out.println("</pre>");
        e.printStackTrace();

        System.out.println ("<br><br>" + newLine +
            "Please e-mail Ursula von St Ange at " +
            "<a href=\"mailto:uvstange@csir.co.za,\">uvstange@csir.co.za</a> " +
            " with the error details by pasting the error into the e-mail body");

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
            "<br>" + newLine + "<font color=red size=+1>ERROR in " +
            eClass + "." + method + "</font>" +
            ":<br>" + newLine +  text + "<br><pre>");
        e.printStackTrace(System.out);
        System.out.println("</pre>");
        e.printStackTrace();

        System.out.println ("<br><br>" + newLine +
            "Please e-mail Ursula von St Ange at " +
            "<a href=\"mailto:uvstange@csir.co.za,\">uvstange@csir.co.za</a> " +
            " with the error details by pasting the error into the e-mail body");

    } // processErrorStatic

    /**
     * Sort an array of String in Case order.
     * @param  list   Array to be sorted (String[])
     * @param  cnt    The number of items in the array (int)
     * @return        The sorted array
     */
/*    public String[] bubbleSort(String[] list, int cnt) {
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
/*    public String[] bubbleSortIgnoreCase(String[] list, int cnt) {
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
/*    public int[] bubbleSort(int[] list, int cnt) {
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
/*    public float[] bubbleSort(float[] list, int cnt) {
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
     * Replace a CR with a <br> in a string
     * @param  text   The text to change
     * @return        The change text
     */
/*    public String replaceCrBR(String text) {
        String newText = "";
        int pos = text.indexOf('\n');
        while (pos >= 0) {
            newText += text.substring(0, pos-1) + "<br>";
            text = text.substring(pos+1);
            pos = text.indexOf('\n');
        } // while
        newText += text;
        return newText;
    } // replaceCrBR


    /**
     * Replace a <br> with a CR in a string
     * @param  text   The text to change
     * @return        The change text
     */
/*    public String replaceBRCr(String text) {
        String newText = "";
        int pos = text.indexOf("<br>");
        while (pos >= 0) {
            newText += text.substring(0, pos) + '\n';
            text = text.substring(pos+4);
            pos = text.indexOf("<br>");
        } // while
        newText += text;
        return newText;
    } // replaceBRCr
*/
}  //  class edmCommon
