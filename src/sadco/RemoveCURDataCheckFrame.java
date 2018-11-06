package sadco;

import oracle.html.CompoundItem;
import oracle.html.DynamicTable;
import oracle.html.Form;
import oracle.html.Hidden;
import oracle.html.HtmlBody;
import oracle.html.HtmlHead;
import oracle.html.HtmlPage;
import oracle.html.IHAlign;
import oracle.html.ITableFrame;
import oracle.html.ITableRules;
import oracle.html.Submit;

/**
 * This class does some pre-processing for the currents load removal
 *
 * SadcoCUR
 * pScreen.equals("remove")
 * pVersion.equals("check")
 *
 * @author 991103 - SIT Group
 * @version
 * 991103 - SIT Group           - create class                              <br>
 * 110810 - Ursula von St Ange  - copy from currents.RemoveDataCheckFrame   <br>
 */
public class RemoveCURDataCheckFrame extends CompoundItem{

    boolean dbg = false;
    //boolean dbg = true;

    String depthCode      = "";
    String mooringCode    = "";
    String version        = "";
    String option         = "";
    String sessionCode    = "";
    String userType       = "";
    String delInv         = "";

    String instrumentType = "";

    /** A general purpose class used to extract the url-type parameters*/
    static common.edmCommon ec = new common.edmCommon();

    /** A class with all the constants used in Atlantis.*/
    SadConstants ac = new SadConstants();

    public RemoveCURDataCheckFrame(String args[]) {

        // get the argument values
        getArgParms(args);
        int intOption = new Integer(option).intValue();

        String where = "";
        boolean success;

        // Create main table                                         //
        DynamicTable mainTab = new DynamicTable(1);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();
        mainTab.setWidth("100%");

        // Get confirmation form user before deleting record.
        Form confirmForm = new Form("POST",ac.CUR_APP);

        // display header Remove Load.
        mainTab.addRow(ec.crSpanColsRow(ac.INVOPTIONS[intOption],
                    2,true,"blue",IHAlign.CENTER,"+2" ));
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));

        //Get depth details
        where = CurDepth.CODE + "=" + depthCode + " and " +
            CurDepth.MOORING_CODE + " = " + mooringCode;
        CurDepth depth[] = new CurDepth().get(where);

        if (depth.length > 0) {

            // Get mooring details.
            where = CurMooring.CODE+" = "+depth[0].getMooringCode();
            CurMooring mooring[] = new CurMooring().get(where);

            // Test if mooring_code plus depth code entered exists.
            if (mooring.length > 0) {

                // Get EdmInstrument details.
                where = EdmInstrument2.CODE+" = "+depth[0].getInstrumentNumber();
                EdmInstrument2 instrument[] = new EdmInstrument2().get(where);
                if (instrument.length > 0) {
                    instrumentType   = instrument[0].getName();
                } //if (instrument.length > 0) {

                // Display on screen
                mainTab.addRow(ec.cr2ColRow("Mooring: ",
                    mooringCode+" - "+mooring[0].getStnnam()));
                    //depth[0].getInstrumentNumber()));

                mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
                mainTab.addRow(ec.cr2ColRow("Instrument Number : ",
                    depth[0].getInstrumentNumber()));
                mainTab.addRow(ec.cr2ColRow("Deployment : ",
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
                mainTab.addRow(ec.cr2ColRow("Delete Record? ",
                    new Submit(ac.CONFIRM,"Yes").toHTML()));

                confirmForm
                  .addItem(new Hidden(ac.SCREEN,"remove"))
                  .addItem(new Hidden(ac.VERSION,"confirm"))
                  .addItem(new Hidden(ac.USERTYPE,userType))
                  .addItem(new Hidden(ac.SESSIONCODE,sessionCode))
                  .addItem(new Hidden(ac.OPTION,option))
                  .addItem(new Hidden(ac.MOORINGCODE, mooringCode))
                  .addItem(new Hidden(ac.DEPTHCODE, depthCode))
                  .addItem(new Hidden(ac.DELINV, delInv))
                  .addItem(new Hidden(menusp.MnuConstants.MVERSION,
                        ec.getArgument(args,menusp.MnuConstants.MVERSION)))
                  .addItem(new Hidden(menusp.MnuConstants.MENUNO,
                        ec.getArgument(args,menusp.MnuConstants.MENUNO)));

            } else {
                mainTab.addRow(ec.crSpanColsRow("Data load not found.",2));
                //this.addItem(mainTab);

        } // else-if body

        } else {
            mainTab.addRow(ec.crSpanColsRow("No Depth data found.",2));
            //this.addItem(mainTab);
        } //

        confirmForm.addItem(mainTab);
        this.addItem(confirmForm);

    } // constructor

    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {
        option      = ec.getArgument(args,ac.OPTION);
        version     = ec.getArgument(args,ac.VERSION);
        sessionCode = ec.getArgument(args,ac.SESSIONCODE);
        userType    = ec.getArgument(args,ac.USERTYPE);
        mooringCode = ec.getArgument(args,ac.MOORINGCODE);
        depthCode   = ec.getArgument(args,ac.DEPTHCODE);
        delInv      = ec.getArgument(args,ac.DELINV);

    }   //  public void getArgParms()


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("RemoveCURDataCheckFrame local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreRemoveCURDataCheckFrame local= new PreRemoveCURDataCheckFrame(args);
            bd.addItem(new RemoveCURDataCheckFrame(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "RemoveCURDataCheckFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main

} // class RemoveCURDataCheckFrame




