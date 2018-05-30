package sadco;

import java.io.*;
import oracle.html.*;
import java.sql.*;

/**
 * This class adds an instrument to the EdmInstrument2 table
 *
 * SadcoWET
 * pScreen.equals("instrument")
 *
 * @author 20000728 - SIT Group
 * @version
 * 20000728 - SIT Group - create class                                      <br>
 * 20080516 - Ursula von St Ange - copy from waves and adapt for sadco      <br>
 */
public class AddInstrumentFrame extends CompoundItem{

    boolean dbg = false;
    //boolean dbg = true;
    int maxCode = 50;

    common.edmCommon ec = new common.edmCommon();
    SadConstants sc = new SadConstants();

    public AddInstrumentFrame(String args[]) {

        String newInstrument = ec.getArgument(args, sc.NEWINSTRUMENT );

        // Create main table
        DynamicTable mainTab = new DynamicTable(1);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();
        mainTab.addRow(ec.crSpanColsRow(
            "Add an Instrument",2 , true, "blue", IHAlign.CENTER, "+2"));
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));

        //Get the instrument class, and check whether the instrument exists
        EdmInstrument2 instrument[] =
            new EdmInstrument2().get(
                EdmInstrument2.CODE + "<=" + maxCode, EdmInstrument2.CODE);
        int count = instrument.length;
        for (int i = 0; i < instrument.length; i++) {
           if(instrument[i].getName().equalsIgnoreCase(newInstrument)) {
              count = i;
           } // if
        } // for i

        if (dbg) System.out.println("<br>count : " + count);
        if (dbg) System.out.println("<br>newInstrument : " + newInstrument);
        if (dbg) System.out.println("<br>instrument.length : " + instrument.length);
        if (dbg) System.out.println("<br>instrument[instrument.length - 1].getCode() : " + instrument[instrument.length - 1].getCode());

        if (count == instrument.length) {
            //check for an empty slot for the code
            int code = 0;
            for (int i = 0; i < instrument.length; i++) {
                if (dbg) System.out.println("<br>" + i + " = " +
                   instrument[i].getCode() + " " + instrument[i].getName());
               if(instrument[i].getCode() != i) {
                 if (dbg) System.out.println("<br> Gap found");
                 code = i;
                 break;
               } // if
            } // for i

            if(code == 0) {
              code = instrument.length;
            } // if

            if (dbg) System.out.println("<br>code : " + code);

            // Waves and weather codes are only allowed to take up the first
            // 50 records from the Edm_Instrument table
            if (code <= maxCode) {
                //boolean success = new EdmInstrument2(code, newInstrument).put();
                EdmInstrument2 instr = new EdmInstrument2(code, newInstrument);
                if (dbg) System.out.println("<br>instr = " + instr);
                boolean success = instr.put();
                if (dbg) System.out.println("<br>insStr = " + instr.getInsStr());
                if (dbg) System.out.println("<br>numRecords = " + instr.getNumRecords());
                if (dbg) System.out.println("<br>success = " + success);

                mainTab.addRow(ec.cr2ColRow("Code:", code));
                mainTab.addRow(ec.cr2ColRow("Name:", newInstrument));
                mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
                mainTab.addRow(ec.crSpanColsRow(
                    "The instrument has been succesfully added", 2, true,
                    "green", IHAlign.CENTER));
            } else {
                mainTab.addRow(ec.crSpanColsRow(
                    "The maximum code (" + maxCode + ") has been reached for " +
                    "the waves and weather instruments", 2));
                mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
                mainTab.addRow(ec.crSpanColsRow(
                    "Please contact your database administrator", 2));
            }//if (code <= 50)

        } else {
            mainTab.addRow(ec.cr2ColRow("Code:", instrument[count].getCode()));
            mainTab.addRow(ec.cr2ColRow("Name:", instrument[count].getName()));
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow(
                "The instrument already exists in the database ",
                2 ,true ,"red" ,IHAlign.CENTER));
        } //if (count == 0)

        // add to the CompoundItem
        this.addItem(mainTab.setCenter());

    } // constructor

} // class AddInstrumentFrame









