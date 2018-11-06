package sadco;

import oracle.html.*;
import java.io.*;

/**
 * delete a vos load
 *
 * SadcoVOS
 * screen.equals("load")
 * version.equals("remove")
 *
 * @author  001123 - SIT Group
 * @version
 * 001123 - Ursula von St Ange - create class<br>
 * 020528 - Ursula von St Ange - update for non-frame menus<br>
 * 020528 - Ursula von St Ange - update for exception catching<br>
 */
public class LoadVOSRemoveDataFrame extends CompoundItem {

    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    boolean dbg = false;
    //boolean dbg = true;

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public LoadVOSRemoveDataFrame (String args[]) {

        try {
            LoadVOSRemoveDataFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // LoadVOSRemoveDataFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void LoadVOSRemoveDataFrameActual(String args[]) {

        // get arguments used more than once
        String vosLoadId  = ec.getArgument(args,sc.VOSLOADID);

        // delete out of vos_main table
        VosMain main = new VosMain();
        main.setLoadId(vosLoadId);
        main.del();
        int mainRecordsDeleted = main.getNumRecords();

        // delete out of vos_arch table
        VosArch arch = new VosArch();
        arch.setLoadId(vosLoadId);
        arch.del();
        int archRecordsDeleted = arch.getNumRecords();

        // delete out of vos_reject table
        VosReject reject = new VosReject();
        reject.setLoadId(vosLoadId);
        reject.del();
        int rejectRecordsDeleted = reject.getNumRecords();

        // delete out of log_vos_loads table
        LogVosLoads logVosLoads = new LogVosLoads();
        logVosLoads.setVosLoadId(vosLoadId);
        logVosLoads.del();


        // the main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        // create the html page
        mainTable.addRow(ec.cr1ColRow("<font size=+2>Remove Loaded Data</font>",
            true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow("Data with load id <b>" + vosLoadId +
            "</b> has been deleted", false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("Records deleted out of <b>VOS_MAIN</b>: " +
            mainRecordsDeleted, false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("Records deleted out of <b>VOS_ARCH</b>: " +
            archRecordsDeleted, false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("Records deleted out of <b>VOS_REJECT</b>: " +
            rejectRecordsDeleted, false, "green", IHAlign.CENTER));


        // add components to screen
        this.addItem(mainTable.setCenter());

    } // constructor LoadVOSRemoveDataFrame

} // class LoadVOSRemoveDataFrame









