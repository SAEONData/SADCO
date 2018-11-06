package sadco;

import java.io.*;
import oracle.html.*;

/**
 * This class displays a list of weather data load files on the screen
 *
 * SadcoWET
 * pScreen.equals("load")
 * pVersion.equals("files")
 *
 * @author 000728 - SIT Group
 * @version
 * 000728 - SIT Group - create class                                        <br>
 * 20060405 - Ursula von St Ange - add timezone for date/time correction
 *                                   to UTC (ub04)                          <br>
 * 20080519 - Ursula von St Ange - copy from weather and adapt for sadco    <br>
 */
public class SelectWETLoadFileFrame extends CompoundItem{

    boolean     dbg = false;
    //boolean     dbg = true;

    common.edmCommon ec = new common.edmCommon();
    //WeatherConstants wc = new WeatherConstants();
    SadConstants sc = new SadConstants();

    // parameters
    String      stationId;

    // file stuff
    String      pathName;

    public SelectWETLoadFileFrame(String args[]) {

        // get the correct path name
        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL + "weather/";
        } else {
            pathName = sc.LOCALDIR + "weather/";
        } // if host

        if (dbg) System.out.println("SelectWETLoadFileFrame pathName = " + pathName);


        // get the argument values
        getArgParms(args);

        // build up the file name with wild cards
        String filter = "." + stationId.substring(0,2).toLowerCase() + "l";
        if (dbg) System.out.println ("<br>filter = " + filter);

        // get the file list
        File path = new File(pathName);
        String[] fileList = path.list();
        fileList = ec.bubbleSort(fileList, fileList.length);
        if (dbg) System.out.println ("<br>fileList.length = " + fileList.length);

        // create the pull-down box
        Select files = createFileListSelect(fileList, filter);

        // get the station details
        WetStation[] stationA = new WetStation(stationId).get();

        // Main container
        Container con = new Container();

        // Create main table
        DynamicTable mainTable = new DynamicTable(2);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        // write the header
        mainTable.addRow(ec.crSpanColsRow("Load Data",2,true,"blue",IHAlign.CENTER,"+2" ));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));

        mainTable.addRow(ec.cr2ColRow(
            "Station:", stationId + " - " + stationA[0].getName()));

        if (files.size() > 0) {
            // create form
            //Form form = new Form("get","EdmWeather","middle");
            Form form = new Form("get",sc.WET_APP);
            mainTable.addRow(ec.cr2ColRow("File Name:",files));
            mainTable.addRow(ec.cr2ColRow(
                "Time Zone of Data to be Loaded:",createTimeZoneSelect()));     // ub04
            form.addItem (mainTable);
            form.addItem(new Submit("","Go!").setCenter());
            form.addItem(new Hidden(sc.SCREEN,"load"));
            form.addItem(new Hidden(sc.VERSION,"preload"));
            form.addItem(new Hidden(sc.USERTYPE,ec.getArgument(args, sc.USERTYPE)));
            form.addItem(new Hidden(sc.SESSIONCODE,ec.getArgument(args,sc.SESSIONCODE)));

            // for the menu system
            form.addItem(new Hidden(sc.MENUNO,ec.getArgument(args,sc.MENUNO)));
            form.addItem(new Hidden(sc.MVERSION,ec.getArgument(args,sc.MVERSION)));

            con.addItem(form.setCenter());
        } else {
            mainTable.addRow(ec.crSpan2ColRow(
                "<br>There are no files for this station"));
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
        stationId     = ec.getArgument(args,sc.STATIONID);
        if (dbg) System.out.println ("<br>stationId = " + stationId);
    }   //  public void getArgParms()


    /**
     * create the combo-box (pull-down box) for the file names
     */
    Select createFileListSelect(String[] fileList, String filter) {
        Select sel = new Select(sc.FILENAME);
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
    Select createTimeZoneSelect() {                         // ub04
        Select sel = new Select(sc.TIMEZONE);
        sel.addOption(new Option(""));
        sel.addOption(new Option("SAST"));
        sel.addOption(new Option("UTC"));
        return sel;
    } // createTimeZoneSelect


} // class
