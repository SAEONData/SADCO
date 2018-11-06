package sadco;

import java.io.*;
import java.sql.*;

/**
 * This class tests numbers
 *
 * @author 040122 - SIT Group
 * @version
 * 040122 - Ursula von St Ange - create class                           <br>
 */
public class TestNumbers {

    // values for in this application
    String thisClass   = this.getClass().getName(); // class name

    /**
     *
     */
    public TestNumbers(String args[]) {

        System.out.println("before create getInv");
        MrnInventory getInv = new MrnInventory();
        System.out.println("after create getInv");
        getInv.setSurveyId("2004/9998");
        getInv.del();


        MrnInventory inventory = new MrnInventory();
        inventory.setSurveyId("2004/9998");
        inventory.setDomain("domain");
        inventory.setAreaname("areaname");
        inventory.setDataCentre("SADCO");
        inventory.setTargetCountryCode(0);   // unknown
        inventory.setStnidPrefix("UCT");
        inventory.setDataRecorded("Y");
        inventory.setCountryCode(1);
        inventory.setInstitCode(200);
        inventory.setCruiseName("cruise name");
        inventory.setProjectName("project name");
        inventory.setSciCode1(1);  // unknown
        inventory.setSurveyTypeCode(1);  // hydro
        inventory.setLatSouth(12.34f);
        inventory.setDateStart("2054-01-29");
        System.out.println("dateStart error = " + inventory.getDateStartError());
        inventory.setDateStart("2004-01-29");
        System.out.println("dateStart error = " + inventory.getDateStartError());

        inventory.setPlanamCode(250072731);
        System.out.println("planam error = " + inventory.getPlanamCodeError());
        inventory.setPlanamCode(25007273);
        System.out.println("planam error = " + inventory.getPlanamCodeError());
        System.out.println("inventory = " + inventory);
        inventory.put();




        MrnInventory inv[] = getInv.get();
        System.out.println("inv[0] = " + inv[0]);
        System.out.println("planamCode = " + inv[0].getPlanamCode());
        System.out.println("institCode = " + inv[0].getInstitCode());
        System.out.println("latNorth = " + inv[0].getLatNorth());
        System.out.println("latNorth = " + inv[0].getLatNorth(""));
        System.out.println("sciCode2 = " + inv[0].getSciCode2());
        System.out.println("sciCode2 = " + inv[0].getSciCode2(""));

        System.out.println("DateStart = " + common2.TablesC.getDateFormat(inv[0].getDateStart()));
/*
        System.out.println("FLOATNULL = " + getInv.FLOATNULL);
        System.out.println("FLOATNULL2 = " + getInv.FLOATNULL2);
        System.out.println("FLOATMIN = " + -Float.MAX_VALUE);
        System.out.println("FLOATMAX = " + Float.MAX_VALUE);

        System.out.println("DOUBLENULL = " + getInv.DOUBLENULL);
        System.out.println("DOUBLENULL2 = " + getInv.DOUBLENULL2);
        System.out.println("DOUBLEMIN = " + -Double.MAX_VALUE);
        System.out.println("DOUBLEMAX = " + Double.MAX_VALUE);

        System.out.println("LONGNULL = " + getInv.LONGNULL);
        System.out.println("LONGNULL2 = " + getInv.LONGNULL2);
        System.out.println("LONGMIN = " + -Long.MAX_VALUE);
        System.out.println("LONGMAX = " + Long.MAX_VALUE);

        System.out.println("INTNULL = " + getInv.INTNULL);
        System.out.println("INTNULL2 = " + getInv.INTNULL2);

        MrnInventory updInv = new MrnInventory();
        updInv.setDomain(updInv.CHARNULL2);
        updInv.setInstitCode(updInv.INTNULL2);
        updInv.setPlanamCode(updInv.INTNULL2);
        updInv.setLatSouth(updInv.FLOATNULL2);
        updInv.setDateStart(updInv.DATENULL2);
        System.out.println("planam error = " + inventory.getPlanamCodeError());
        System.out.println("dateStart error = " + inventory.getDateStartError());
        System.out.println("domain = *" + updInv.getDomain() + "*");
        System.out.println("institCode = *" + updInv.getInstitCode() + "*");
        System.out.println("planam = *" + updInv.getPlanamCode() + "*");
        System.out.println("latSouth = *" + updInv.getLatSouth() + "*");
        System.out.println("dateStart = *" + updInv.getDateStart() + "*");
        System.out.println("updInv = " + updInv);

        getInv.upd(updInv);

        inv = getInv.get();
        System.out.println("inv[0] = " + inv[0]);

        MrnWatphy watphy = new MrnWatphy();
        int maxCode = watphy.getMaxCode();
        int maxCode1 = maxCode + 1;
        System.out.println("watphy.getMaxCode() = " + watphy.getMaxCode());
        System.out.println("maxCode = " + maxCode);
        System.out.println("maxCode1 = " + maxCode1);
        */



        common2.DbAccessC.close();

    } // constructor


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            TestNumbers local= new TestNumbers(args);
        } catch(Exception e) {
            //ec.processErrorStatic(e, "LoadMRNFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main




} // class TestNumbers
