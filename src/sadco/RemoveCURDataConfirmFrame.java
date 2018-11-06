package sadco;

import oracle.html.CompoundItem;
import oracle.html.DynamicTable;
import oracle.html.HtmlBody;
import oracle.html.HtmlHead;
import oracle.html.HtmlPage;
import oracle.html.IHAlign;
import oracle.html.ITableFrame;
import oracle.html.ITableRules;

/**
 * This class removes a currents load (cur_depth)
 *
 * SadcoCUR
 * pScreen.equals("remove")
 * pVersion.equals("confirm")
 *
 * @author 991103 - SIT Group
 * @version
 * 991103 - SIT Group           - create class                              <br>
 * 110810 - Ursula von St Ange  - copy from currents.RemoveCURDataConfirmFrame <br>
 */
public class RemoveCURDataConfirmFrame extends CompoundItem{

    boolean dbg = false;
    //boolean dbg = true;

    String depthCode   = "";
    String mooringCode = "";
    String confirm     = "";
    String option      = "";
    String delInv      = "";

    String instrumentType = "";

    /** A general purpose class used to extract the url-type parameters*/
    static common.edmCommon ec = new common.edmCommon();

    /** A class with all the constants used in Atlantis.*/
    SadConstants ac = new SadConstants();

    public RemoveCURDataConfirmFrame(String args[]) {

        // get the argument values
        getArgParms(args);
        int intOption = new Integer(option).intValue();

        String where = "";
        String where2 = "";
        String fields = "";
        String order = "";
        boolean success;

        // Create main table                                         //
        DynamicTable mainTab = new DynamicTable(1);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();
        mainTab.setWidth("100%");

        // display header Remove Load.
        mainTab.addRow(ec.crSpanColsRow(ac.INVOPTIONS[intOption],
                2,true,"blue",IHAlign.CENTER,"+2" ));
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));

        //Get details for mooringCode.
        where = CurDepth.CODE+"="+depthCode+" and "+
                CurDepth.MOORING_CODE+" = "+ mooringCode;
        CurDepth depth[] = new CurDepth().get(where);

        if (depth.length > 0) {

            // Get mooring details.
            where = CurMooring.CODE+" = "+depth[0].getMooringCode();
            CurMooring mooring[] = new CurMooring().get(where);

            if (mooring.length > 0) {

                // Get EdmInstrument details.
                where = EdmInstrument2.CODE+" = "+depth[0].getInstrumentNumber();
                EdmInstrument2 instrument[] = new EdmInstrument2().get(where);

                if (instrument.length > 0) {
                    instrumentType   = instrument[0].getName();
                } //if (instrument.length > 0) {

                where = EdmClient2.CODE+" = "+mooring[0].getClientCode();
                EdmClient2 client[] = new EdmClient2().get(where);

                if ("Yes".equals(confirm)) {

                    mainTab.addRow(ec.cr2ColRow("Mooring: ",
                        mooringCode+" - "+mooring[0].getStnnam()));
                    mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
                    mainTab.addRow(ec.cr2ColRow("Deployment: ",
                        depth[0].getDeploymentNumber()));
                    mainTab.addRow(ec.cr2ColRow("Intrument Type : ", instrumentType));
                    mainTab.addRow(ec.cr2ColRow("Depth : ",
                        String.valueOf(depth[0].getSpldep())));
                    mainTab.addRow(ec.cr2ColRow("Date Time Start : ",
                        depth[0].getDateTimeStart("")));
                    mainTab.addRow(ec.cr2ColRow("Date Time End : ",
                        depth[0].getDateTimeEnd("")));
                    mainTab.addRow(ec.cr2ColRow("Time Interval : ",
                        depth[0].getTimeInterval()));
                    mainTab.addRow(ec.cr2ColRow("Number Of Records : ",
                        depth[0].getNumberOfRecords()));
                    mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));

                    // delete the record
                    where = CurDepth.CODE + " = " + depthCode + " and " +
                            CurDepth.MOORING_CODE + " = " + mooringCode;
                    success = new CurDepth().del(where);

                    if (success) {
                        if (dbg) System.out.println("<br>success true delete done ");
                        mainTab.addRow(ec.crSpanColsRow(
                            "The depth record has been successfully deleted.",
                            2,true,"green",IHAlign.CENTER,"+1" ));
                    } else {
                        mainTab.addRow(ec.crSpanColsRow("The record could not be deleted.",
                            2,true,"red",IHAlign.CENTER,"+1" ));
                    }//if (success) {

                    // get ready for later
                    MrnInventory inv = new MrnInventory();
                    where2 = MrnInventory.SURVEY_ID + " = '" +
                        mooring[0].getSurveyId() + "'";
                    MrnInventory inv2 = new MrnInventory();

                    // check if last depth in mooring
                    CurDepth cur = new CurDepth();
                    where = CurDepth.MOORING_CODE + " = " + mooringCode;
                    int records = cur.getRecCnt(where);

                    // get ready for later
                    CurMooring updCurMooring = new CurMooring();
                    where = CurMooring.CODE+" = "+ mooringCode;
                    CurMooring whereCurMooring = new CurMooring();

                    if (records > 0) {

                        // Test to see if there are any remaining sections of this depth.                 |--
                        where = CurDepth.MOORING_CODE + " = " + mooringCode + " and " +
                            CurDepth.SPLDEP + " between " +
                            (int)depth[0].getSpldep() + " and " +
                            ((int)depth[0].getSpldep()) + ".9";
                        if (dbg) System.out.println("<br>Between where :"+where);
                        int numSections = new CurDepth().getRecCnt(where);

                        // no more sections = update the mooring numberOfDepths and date range
                        if (numSections == 0) {

                            updCurMooring.setNumberOfDepths(mooring[0].getNumberOfDepths()-1);
                            if (dbg) System.out.println("<br>Update where :"+where);
                            whereCurMooring.upd(updCurMooring,where);

                        } // if (numSections == 0) {

                        // get the new date range for the inventory and mooring
                        fields = "min(" + CurDepth.DATE_TIME_START + ") as " +
                            CurDepth.DATE_TIME_START +
                            ", max(" + CurDepth.DATE_TIME_END + ") as " +
                            CurDepth.DATE_TIME_END;
                        order = CurDepth.DATE_TIME_START;
                        CurDepth curs[] = cur.get(fields, where, order);

                        // update the mooring for date range
                        updCurMooring.setDateTimeStart(curs[0].getDateTimeStart());
                        updCurMooring.setDateTimeEnd(curs[0].getDateTimeEnd());
                        whereCurMooring.upd(updCurMooring, where);

                        // update the inventory
                        inv.setDateStart(curs[0].getDateTimeStart());
                        inv.setDateEnd(curs[0].getDateTimeEnd());
                        inv2.upd(inv, where2);

                        mainTab.addRow(ec.crSpanColsRow(
                            "The inventory record has been updated for new date range.",
                            2,true,"green",IHAlign.CENTER,"+1" ));

                    } else {

                        // was last depth record
                        whereCurMooring.del(where);
                        mainTab.addRow(ec.crSpanColsRow(
                            "This was the last depth record of the mooring,\n" +
                            "the mooring record has been deleted.",
                            2,true,"green",IHAlign.CENTER,"+1" ));

                        // delete inventory?
                        if ("Y".equals(delInv)) {
                            inv2.del(where2);
                            mainTab.addRow(ec.crSpanColsRow(
                                "The inventory record has been deleted.",
                                2,true,"green",IHAlign.CENTER,"+1" ));
                        } else {
                            inv.setDataRecorded("N");
                            inv2.upd(inv, where2);
                            mainTab.addRow(ec.crSpanColsRow(
                                "The inventory record has been updated for data not available.",
                                2,true,"green",IHAlign.CENTER,"+1" ));
                        } // if ("Y".equals(delInv))


                    } // if (records > 0)

/*
                    // check if any depth records left
                    where = CurDepth.MOORING_CODE + " = " + mooringCode;
                    int numDepths = new CurDepth().getRecCnt(where);

                    if (numDepths == 0 ) {

                        // delete mooring redord.
                        where = CurMooring.CODE+" = "+mooringCode;
                        success = new CurMooring().del(where);

                    } else {

                        // Test to see if there are any remaining sections of this depth.                 |--
                        where = CurDepth.MOORING_CODE + " = " + mooringCode + " and " +
                            CurDepth.SPLDEP + " between " +
                            (int)depth[0].getSpldep() + " and " +
                            ((int)depth[0].getSpldep()) + ".9";
                        if (dbg) System.out.println("<br>Between where :"+where);
                        int numSections = new CurDepth().getRecCnt(where);

                        // no more sections = update the mooring numberOfDepths
                        if (numSections == 0) {

                            //int val = mooring[0].getNumberOfDepths() - 1;
                            CurMooring updCurMooring = new CurMooring();
                            updCurMooring.setNumberOfDepths(mooring[0].getNumberOfDepths()-1);

                            CurMooring whereCurMooring = new CurMooring();
                            where = CurMooring.CODE+" = "+ mooringCode;
                            if (dbg) System.out.println("<br>Update where :"+where);

                            success = whereCurMooring.upd(updCurMooring,where);
                        } // if (numSections == 0) {
                    } // if (numDepths == 0 )
*/
                } //if ("Yes".equals(confirm)) {
            } // if (mooring.length > 0)

        } else {
            // Display mooring and depth code
            mainTab.addRow(ec.crSpanColsRow("Mooring Code :" + mooringCode, 2));
            mainTab.addRow(ec.crSpanColsRow("Depth Code :" + depthCode, 2));
            mainTab.addRow(ec.crSpanColsRow("No depth record found.", 2));
        }

        this.addItem(mainTab);

    } // constructor

    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        option      = ec.getArgument(args, ac.OPTION);
        confirm     = ec.getArgument(args, ac.CONFIRM);
        mooringCode = ec.getArgument(args, ac.MOORINGCODE);
        depthCode   = ec.getArgument(args, ac.DEPTHCODE);
        delInv      = ec.getArgument(args,ac.DELINV);

        if (dbg) System.out.println("<br>option = " + option);
        if (dbg) System.out.println("<br>confirm = " + confirm);
        if (dbg) System.out.println("<br>mooringCode = " + mooringCode);
        if (dbg) System.out.println("<br>depthCode = " + depthCode);
        if (dbg) System.out.println("getArgParms.delInv = " + delInv);
    }   //  public void getArgParms()


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("RemoveCURDataConfirmFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreRemoveCURDataConfirmFrame local= new PreRemoveCURDataConfirmFrame(args);
            bd.addItem(new RemoveCURDataConfirmFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "RemoveCURDataConfirmFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class RemoveCURDataConfirmFrame




