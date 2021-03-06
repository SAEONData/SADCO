package sadco;

import java.io.File;
import oracle.html.CheckBox;
import oracle.html.CompoundItem;
import oracle.html.Container;
import oracle.html.DynamicTable;
import oracle.html.Form;
import oracle.html.Hidden;
import oracle.html.HtmlBody;
import oracle.html.HtmlHead;
import oracle.html.HtmlPage;
import oracle.html.IHAlign;
import oracle.html.ITableFrame;
import oracle.html.ITableRules;
import oracle.html.Option;
import oracle.html.Select;
import oracle.html.Submit;

/**
 * This class displays a list of load files for the user to select from
 *
 * SadcoCUR
 * pScreen.equals("load")
 * pVersion.equals("files")
 *
 * @author 021025 - SIT Group
 * @version
 * 021025 - Mario August        - create class                              <br>
 * 060201 - Ursula von St Ange  - add timezone for date/time correction to UTC<br>
 * 110810 - Ursula von St Ange  - change from currents.SelectLoadFileFrame  <br>
 */
public class SelectCURLoadFileFrame extends CompoundItem{

    //boolean     dbg = false;
    boolean     dbg = true;

    static common.edmCommon ec = new common.edmCommon();

    /** A class with all the constants used in Currents.*/
    SadConstants sc = new SadConstants();

    // parameters
    String      extension = "";
    String      sessionCode = "";
    String      userType = "";

    // file stuff
    String      pathName;

    public SelectCURLoadFileFrame(String args[]) {

        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL + "currents/";
        } else {
            pathName = sc.LOCALDIR + "currents/";
        } // if host
        if (dbg) System.out.println("currents.SelectCURLoadFileFrame pathName = " + pathName);

        // get the argument values
        getArgParms(args);

        // build up the file name with wild cards
        String filter = extension;

        // get the file list
        File path = new File(pathName);
        String[] fileList = path.list();
        if (dbg) System.out.println ("<br>fileList.length = " + fileList.length);

        // create the pull-down box
        Select files = createFileListSelect(fileList, filter);

        // Main container
        Container con = new Container();

        // Create main table
        DynamicTable mainTable = new DynamicTable(2);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        // write the header
        mainTable.addRow(ec.crSpanColsRow(" Load Data",2,true,"blue",IHAlign.CENTER,"+2" ));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTable.addRow(ec.cr2ColRow("File Type:", extension+" format"));

        if (files.size() > 0) {
            // create form
            Form form = new Form("get", sc.CUR_APP);
            mainTable.addRow(ec.cr2ColRow("File name(s):",files));
            mainTable.addRow(ec.cr2ColRow(
                "Time Zone of Data to be Loaded:",createTimeZoneSelect()));
            mainTable.addRow(ec.cr2ColRow(
                "Check time interval?",new CheckBox(sc.CHECKINTERVAL,"Y",true).toHTML()));
            form.addItem (mainTable);
            form.addItem(new Submit("","Go!").setCenter());
            form.addItem(new Hidden(sc.SCREEN,"load"));
            form.addItem(new Hidden(sc.VERSION,"preload"));
            form.addItem(new Hidden(sc.USERTYPE,userType));
            form.addItem(new Hidden(sc.SESSIONCODE,sessionCode));
            form.addItem(new Hidden(sc.EXTENSION,extension));

            // for the menu system
            form.addItem(new Hidden("pmenuno",ec.getArgument(args,"pmenuno")));
            form.addItem(new Hidden("pmversion",ec.getArgument(args,"pmversion")));

            con.addItem(form.setCenter());
        } else {
            mainTable.addRow(ec.crSpan2ColRow(
                "<br>There are no files of this format"));
            con.addItem(mainTable.setCenter());
        } //if (files.size() > 0) {

        // finish off
        addItem(con);
    } // constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        extension = ec.getArgument(args, sc.EXTENSION);
        sessionCode = ec.getArgument(args,sc.SESSIONCODE);
        userType    = ec.getArgument(args,sc.USERTYPE);
        if (dbg) System.out.println ("<br>extension = " + extension);
    }   //  public void getArgParms()


    /**
     * create the combo-box (pull-down box) for the file names
     */
    Select createFileListSelect(String[] fileList, String filter) {
        Select sel = new Select(sc.FILENAME);
        fileList = ec.bubbleSort(fileList, fileList.length);
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].endsWith(filter)) {
                sel.addOption(new Option(fileList[i]));
            } // if (fileList[i].endsWith(filter)) {
        } // for i
        return sel;
    } // createFileListSelect


    /**
     * create the combo-box (pull-down box) for the file names
     */
    Select createTimeZoneSelect() {
        Select sel = new Select(sc.TIMEZONE);
        sel.addOption(new Option(""));
        sel.addOption(new Option("SAST"));
        sel.addOption(new Option("UTC"));
        return sel;
    } // createTimeZoneSelect

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("SelectCURLoadFileFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreSelectCURLoadFileFrame local= new PreSelectCURLoadFileFrame(args);
            bd.addItem(new SelectCURLoadFileFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "SelectCURLoadFileFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class SelectCURLoadFileFrame
