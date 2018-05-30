package menusp;

import common2.*;
import java.sql.*;
import java.util.*;
import oracle.html.*;

/**
 * Generates a form if it is a leaf menu-item, and there are non-hidden
 * parameters.
 *
 * @author 000424 - SIT Group
 * @version
 * 000424 - Ursula von St Ange - create class<br>
 * 020528 - Ursula von St Ange - Added a form name 'thisForm'. Took away the
 *                               target for the form.  Move the boolean
 *                               variable 'optional' to the javascript stuff.<br>
 * 020625 - Ursula von St Ange - Also send pmenuno and pversion (pmversion)
 *                               to the program called in the form.  Also
 *                               implement MnuConstants.<br>
 */
public class MnuMenuFormFrame extends CompoundItem {

    /** contains the method to parse URL-type arguments */
    common.edmCommon ec = new common.edmCommon();

    // parameters
    private String version = "";
    private String menuNo = "";
    private String userType = "";
    private String sessionCode = "";

    // 'local' paramaters
    private String owner = "";

    boolean dbg = false;
    //boolean dbg = true;

    /**
     * Constructor called from an application-type class.<p>
     * The <b>args</b> variable must contain the following parameters: <br>
     * MnuConstants.VERSION - values = menu / form<br>
     * MnuConstants.MENUNO - the menu-no of the menu to be generated<br>
     * MnuConstants.USERTYPE - values = 1 (super-user) / 2 (normal user)<br>
     * MnuConstants.SESSIONCODE - the current session code - used by the
     *    SADCO applications<p>
     * The <b>args2</b> variable must contain the following parameters: <br>
     * MnuConstants.OWNER - the owner of the application<p>
     * @param args  array of URL-like name=value parameter pairs - from the command line
     * @param args2 array of URL-like name=value parameter pairs - must be defined in
     *              the calling class
     */
    public MnuMenuFormFrame(String args[], String args2[]) {
        // get any parameters off the command line
        version = ec.getArgument(args, MnuConstants.VERSION);
        menuNo = ec.getArgument(args, MnuConstants.MENUNO);
        userType = ec.getArgument(args, MnuConstants.USERTYPE);
        sessionCode = ec.getArgument(args, MnuConstants.SESSIONCODE);

        // get the locally set parameters
        owner = ec.getArgument(args2, MnuConstants.OWNER);

        if (dbg) System.out.println("<br>menuNo = " + menuNo);

        // get all the parameters
        String where = MnuParameters.MENU_ITEM_CODE+"="+menuNo;
        String order = MnuParameters.SEQUENCE_NUMBER;
        if (dbg) System.out.println("<br>where = " + where);
        MnuParameters[] mnuParameters = new MnuParameters().get(where, order);

        // construct form
        where = MnuMenuItems.CODE+"="+menuNo;
        if (dbg) System.out.println("<br>where = " + where);
        MnuMenuItems[] mnuMenuItems = new MnuMenuItems().get(where);

        // get right default path
        String URL = mnuMenuItems[0].getUrl();
        if ((mnuMenuItems[0].getScriptTypeCode() != 3) &&
            (mnuMenuItems[0].getScriptTypeCode() != 4)) {
            MnuScriptType[] mnuScriptType = new MnuScriptType().get(
                MnuScriptType.CODE+"="+
                mnuMenuItems[0].getScriptTypeCode(""));
            URL = mnuScriptType[0].getPath() + URL;
        } // getScriptTypeCode

        //Form form = new Form("GET", URL, "middle");
        JSLLib.formName = "thisForm";
        Form form = new Form("GET\" name=\"thisForm", URL);

        // Create form table
        DynamicTable formTable = new DynamicTable(2);
        formTable.setFrame(ITableFrame.VOLD);
        formTable.setRules(ITableRules.NONE);
        formTable.setBorder(0);
        TableRow row;

        // add the menu header
        formTable.addRow(ec.crSpanColsRow(mnuMenuItems[0].getText(), 2, true,
            "blue", IHAlign.CENTER, "+2"));

        // write help info
        ec.writeHelpInfo(formTable);

        // add the sessioncode
        form.addItem(new Hidden(MnuConstants.SESSIONCODE, sessionCode));
        if (URL.indexOf("morph") > 0) {
            form.addItem(new Hidden("phost", ec.getHost()));
        } //

        // add the rest of the parameters
        if (dbg) System.out.println("<br>mnuParameters.length = " + mnuParameters.length);
        for (int i = 0; i < mnuParameters.length; i++) {
            if (dbg) System.out.println("<br>mnuParameters[i]" + i + " " + mnuParameters[i]);

            String compulsory = "";
            if (mnuParameters[i].getOptional().equals("no")) {
                compulsory = "*";
            } // if (mnuParameters[i].getOptional().equals("no"))

            // hidden fields
            if (mnuParameters[i].getInputType().equals("hidden")) {
                form.addItem(
                    new Hidden(
                        "p"+mnuParameters[i].getName(),
                        mnuParameters[i].getDefaultValue()
                    ));
            } else if (mnuParameters[i].getInputType().equals("area")) {
                // geographical area
                formTable.addRow(ec.latlonRangeInput(
                    mnuParameters[i].getPrompt(),
                    mnuParameters[i].getDataLength(),
                    "", "", "", "", compulsory));

            } else if (mnuParameters[i].getInputType().startsWith("date")) {
                // daterange / datetimerange
                ec.datetimeRangeInput(formTable,
                    mnuParameters[i].getInputType(),
                    mnuParameters[i].getDataLength(),
                    "", "00:00", "", "23:59", compulsory);
            } else {
                // all other fields
                // the prompt
                String colon = ":";
                if (mnuParameters[i].getInputType().equals("display")) {
                    colon = "";
                }
                row = new TableRow();
                TableDataCell dataCell = new TableDataCell(
                    "<b>" + mnuParameters[i].getPrompt() + colon + "</b>");
                if (mnuParameters[i].getInputType().equals("display")) {
                    dataCell.setColSpan(2).setHAlign(IHAlign.CENTER);
                } else {
                    dataCell.setHAlign(IHAlign.RIGHT);
                } // if (mnuParameters[i].getInputType().equals("display")) {
                row.addCell(dataCell);
                //row.addCell(new TableDataCell(
                //    "<b>" + mnuParameters[i].getPrompt() + ":</b>")
                //        .setHAlign(IHAlign.RIGHT));

                // the input field
                if (mnuParameters[i].getInputType().equals("text")) {
                    row.addCell(new TableDataCell(
                        new TextField(
                            "p"+mnuParameters[i].getName(),
                            mnuParameters[i].getDataLength(),
                            mnuParameters[i].getDataLength(),
                            mnuParameters[i].getDefaultValue()
                        ) + compulsory));
                } else if (mnuParameters[i].getInputType().equals("password")) {
                    // password fields
                    row.addCell(new TableDataCell(
                        new PasswordField(
                            "p"+mnuParameters[i].getName(),
                            mnuParameters[i].getDataLength(),
                            mnuParameters[i].getDataLength(),
                            mnuParameters[i].getDefaultValue()
                        ) + compulsory));
                } else if (mnuParameters[i].getInputType().startsWith("select")) {
                    // select field
                    if (mnuParameters[i].getLookupTable("").equals("")) {
                        row.addCell(new TableDataCell(
                            createSelect(
                                "p"+mnuParameters[i].getName(),
                                getParamSelectValues(mnuParameters[i]),
                                mnuParameters[i].getOptional(),
                                mnuParameters[i].getDefaultValue(),
                                mnuParameters[i].getInputType())
                                .toHTML() + compulsory));
                    } else {
                        row.addCell(new TableDataCell(
                            createSelect(
                                "p"+mnuParameters[i].getName(),
                                getTableValues(mnuParameters[i]),
                                mnuParameters[i].getOptional(),
                                mnuParameters[i].getDefaultValue(),
                                mnuParameters[i].getInputType())
                                .toHTML() + compulsory));
                    } // if (mnuParameters[i].getLookupTable().equals(""))
                } // if mnuParameters[i].getInputType()
                formTable.addRow(row);
            } // if hidden

        } // for i

        // add pmenuno and pversion (as pmversion) to the form to send to
        // program being called.
        form.addItem(new Hidden(MnuConstants.MVERSION, version));
        form.addItem(new Hidden(MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(MnuConstants.USERTYPE, userType));

        // finish off form
        form.addItem(formTable);
        form.addItem(SimpleItem.LineBreak);
        form.addItem(JSLLib.OnClickButton("Go!","btnIFI")).setCenter();

        // main container
        Container con = new Container();

        // add javascript stuff
        con.addItem(JSLLib.OpenScript());
        con.addItem(JSLLib.RtnNotNull());
        con.addItem(JSLLib.RtnStripMask());
        con.addItem(JSLLib.RtnReplace());
        con.addItem(JSLLib.RtnGetValue());
        con.addItem(JSLLib.RtnY2K());
        con.addItem(JSLLib.RtnChkDate());
        con.addItem(JSLLib.RtnCmpDates());
        con.addItem(JSLLib.RtnCmpDateTimes());
        con.addItem(JSLLib.RtnChkTime());
        con.addItem(JSLLib.RtnChkRange());
        con.addItem(JSLLib.RtnChkInteger());
        con.addItem(JSLLib.RtnChkNumPrecision());
        con.addItem(JSLLib.RtnChkNumScale());
        con.addItem(JSLLib.RtnLower());
        con.addItem(JSLLib.RtnUpper());

        con.addItem(JSLLib.OpenEvent("INPUT","Validate"));

        boolean optional = true;
        for (int i = 0; i < mnuParameters.length; i++) {
            optional = true;
            if (mnuParameters[i].getOptional().equals("no")) {
                optional = false;
                if ((mnuParameters[i].getInputType().equals("text")) ||
                           (mnuParameters[i].getInputType().equals("select"))) {
                    con.addItem(JSLLib.CallNotNull(
                        "p" + mnuParameters[i].getName(),
                        mnuParameters[i].getPrompt()));
                } // if area
            } // if not optional
            if (mnuParameters[i].getInputType().equals("area")) {
                con.addItem(ec.latlonRangeJS(
                    mnuParameters[i].getDataPrecision(),
                    mnuParameters[i].getDataScale(), optional));
            } else if (mnuParameters[i].getInputType().startsWith("date")) {
                con.addItem(ec.datetimeRangeJS(
                    mnuParameters[i].getInputType(), optional));
            } else if (mnuParameters[i].getDataType().equals("date")) {
                if (!mnuParameters[i].getInputType().startsWith("date")) {
                    con.addItem(JSLLib.CallChkDate(
                        "p" + mnuParameters[i].getName(),
                        mnuParameters[i].getPrompt()));
                } // if not dates
            } else if (mnuParameters[i].getDataType().equals("time")) {
                con.addItem(JSLLib.CallChkTime(
                    "p" + mnuParameters[i].getName(),
                    mnuParameters[i].getPrompt()));
            } else if (mnuParameters[i].getDataType().equals("number")) {
                if (!mnuParameters[i].getInputType().equals("area")) {
                    con.addItem(JSLLib.CallChkNumPrecision(
                        "p" + mnuParameters[i].getName(),
                        mnuParameters[i].getPrompt(),
                        mnuParameters[i].getDataPrecision()));
                    con.addItem(JSLLib.CallChkNumScale(
                        "p" + mnuParameters[i].getName(),
                        mnuParameters[i].getPrompt(),
                        mnuParameters[i].getDataScale()));
                } // if !area
            } // if date/time/number
        } // for i
        con.addItem(JSLLib.CloseEvent());
        con.addItem(JSLLib.OpenEvent("btnIFI","OnClick"));
        con.addItem(JSLLib.CallValidate("INPUT"));
        con.addItem(JSLLib.StandardSubmit(false));
        con.addItem(JSLLib.CloseEvent());
        con.addItem(JSLLib.CloseScript());
        con.addItem(form.setCenter());
        addItem(con);

    } // MnuMenuFormFrame constructor


    /**
     * get the select box values from the relevant lookup table
     * @param   mnuParameters  The relevant MnuParameters record
     * @return  selectArray    The array of values (String[][])
     */
    String[][] getTableValues (MnuParameters mnuParameters) {
        if (dbg) System.out.println("<br>getTableValues");

        // build the select string
        String selectStr = "select " + mnuParameters.getTextField() + ", " +
            mnuParameters.getValueField() + " from " + owner + "." +
            mnuParameters.getLookupTable() + " order by " +
            (mnuParameters.getInputType().equals("select") ?
                mnuParameters.getValueField() : mnuParameters.getTextField());

        if (dbg) System.out.println("<br>selectStr = " + selectStr);

        // do database connection stuff
        Vector result = new Vector();
        int recCnt = 0;
        Connection conn = DbAccessC.getConnection();

        // get the data and put into a Vector of Vectors
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(selectStr);
            while (rset.next()) {
                Vector row = new Vector();
                row.addElement (rset.getString (1));
                row.addElement (rset.getString (2));
                result.addElement (row);
                recCnt = recCnt + 1;
            } // while
            rset.close();
            if (dbg) System.out.println("<br>rset.getRow() = " + recCnt);
        } catch (Exception e) {
            System.out.println("<br>MnuMenuFormFrame: getTableValues error: " +
                e.getMessage());
        } // try

        // move over to a double array  of String
        String[][] selectArray = new String [recCnt][2];
        for (int i = 0; i < recCnt; i++) {
            Vector row = (Vector) result.elementAt(i);
            selectArray[i][0] = (String) row.elementAt(0);
            selectArray[i][1] = (String) row.elementAt(1);
        } // for i

        if (dbg) System.out.println("<br>getTableValues: " + selectArray.length);
        return selectArray;
    } // method getTableValues


    /**
     * get the select box values from param_select table
     * @param   mnuParameters  The relevant MnuParameters record
     * @return  selectArray    The array of values (String[][])
     */
    String[][] getParamSelectValues (MnuParameters mnuParameters) {
        if (dbg) System.out.println("<br>getParamSelectValues * " +
            mnuParameters.getCode(""));

        String where = MnuParamSelect.PARAMETER_CODE+"="+mnuParameters.getCode("");

        MnuParamSelect[] mnuParamSelect= new MnuParamSelect().get(where);
        String[][] selectArray = new String[mnuParamSelect.length][2];

        for (int i = 0; i < mnuParamSelect.length; i++) {
            selectArray[i][0] = mnuParamSelect[i].getText();
            selectArray[i][1] = mnuParamSelect[i].getValue();
        } // for i
        if (dbg) System.out.println("<br>getParamSelectValues" + selectArray.length);
        return selectArray;
    } // method getParamSelectValues


    /**
     * create a select pulldown box for the lookup table
     * @param  pname       The html element name
     * @param  selectArray The lookup table values (String[][])
     * @param  opt         boolean: yes / no
     * @param  def         default value
     * @param  selectType  lookup table select type
     * @return newSelect   The html select statement
     */
    Select createSelect
            (String pname, String[][] selectArray, String opt, String def,
             String selectType) {
        Select newSelect = new Select (pname);
        if (dbg) System.out.println("<br>" + selectArray.length);
        if (opt.equals("yes")) { newSelect.addOption(new Option("","",false)); }

        for (int i = 0; i < selectArray.length; i++) {
            boolean defaultOption = false;
            if (selectArray[i][1].equals(def)) { defaultOption = true; }
            if ((selectArray[i][0] == null) || (selectArray[i][0].equals(""))) {
                newSelect.addOption(new Option(
                    selectArray[i][1],
                    selectArray[i][1], defaultOption));
            } else {
                if (selectType.equals("select2")) {
                    newSelect.addOption(new Option(
                        selectArray[i][0],
                        selectArray[i][1], defaultOption));
                } else {
                    newSelect.addOption(new Option(
                        selectArray[i][0] + " (" +selectArray[i][1] + ")",
                        selectArray[i][1], defaultOption));
                } // if (selectType.equals("select2"))
            } // if ((selectArray[i][0] == null)
        } // for i
        return newSelect;
    } // createSelect


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        // values for args2 variable
        String thisClass = "SadcoMenu";    // class name
        String owner     = "sadco";        // Oracle schema userid
        String title     = "SADCO";        // title on top bar
        String date      = "27 June 2002"; // last update date

        String[] args2 = {
            MnuConstants.APPLICATION    + "=" + thisClass,
            MnuConstants.OWNER          + "=" + owner,
            MnuConstants.TITLE          + "=" + title,
            MnuConstants.DATE           + "=" + date
        }; // args2

        try {
            // create the page head
            HtmlHead hd = new HtmlHead("headTitle");

            // create the body and the page itself
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            hp.printHeader();

            bd.addItem(new MnuMenuFormFrame(args, args2));

            hp.print();


        } catch(Exception e) {
        //  ec.processErrorStatic(e, thisClass, "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class MnuMenuFormFrame