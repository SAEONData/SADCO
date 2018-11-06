package sadco;

import java.io.*;
import java.util.*; // for Calendar


public class CountRecs {


    //boolean dbg = false;
    static boolean dbg = true;

    boolean checkDebug = false;
    //boolean checkDebug = true;
    boolean eof = false;

    String thisClass = this.getClass().getName();

    String startLat = "";
    String endLat   = "";
    String incr     = "";
    String database = "";

    static String frmFiller = " ";

    // file stuff
    RandomAccessFile infile;
    RandomAccessFile outfile; // write count's per f
    String      line;

    public CountRecs(String args[]) {

        // get the argument values
        //getArgParms(args);
        String pathName = "d:/myfiles/java/data/vos/";

        //String inFile = "test.txt";

        String inFile = "vos_main_warning.txt";

        //String inFile =   "vos_main2_warning.txt";
        //String inFile = "vos_arch_warning.txt";
        //String inFile = "vos_arch2_warning.txt";

        String outFile = inFile.substring(0,15)+".out";

        //ec.deleteOldFile(pathName+of1);

        String line = "";

        int CSIR_AT_Invalid_Cnt = 0;
        int WMO_AT_Invalid_Cnt = 0;

        int CSIR_DW_Invalid_Cnt = 0;
        int WMO_DW_Invalid_Cnt = 0;
        int DW_Reject_Cnt = 0;

        int CSIR_SLP_Invalid_Cnt = 0;
        int WMO_SLP_Invalid_Cnt = 0;

        int CSIR_SW1H_Invalid_Cnt = 0;
        int WMO_SW1H_Invalid_Cnt = 0;

        int CSIR_SW1P_Invalid_Cnt = 0;
        int WMO_SW1P_Invalid_Cnt = 0;

        int CSIR_SST_Invalid_Cnt = 0;
        int WMO_SST_Invalid_Cnt = 0;

        int CSIR_WWP_Invalid_Cnt = 0;
        int WMO_WWP_Invalid_Cnt = 0;

        int CSIR_WWH_Invalid_Cnt = 0;
        int WMO_WWH_Invalid_Cnt = 0;

        int CSIR_WSP_Invalid_Cnt = 0;
        int WMO_WSP_Invalid_Cnt = 0;

        int CSIR_WT_Invalid_Cnt = 0;
        int WMO_WT_Invalid_Cnt = 0;

        // Open file with error report.
        try {
             infile = new RandomAccessFile(pathName+inFile,"rw");


            //for (int i = 0; i < infile.length();i++) {
            while (infile.getFilePointer() != infile.length()) {

               if (!eof) {
                   line = infile.readLine();

                      // unique variable per code

                      int atIndex   = line.indexOf("AT");    // Air Temp.
                      int dwIndex   = line.indexOf("DW");    // Dew-point Temp.
                      int slpIndex  = line.indexOf("SLP");   // Sea-level pressure
                      int sw1hIndex = line.indexOf("SW1H");  // Swell height
                      int sw1pIndex = line.indexOf("SW1P");  // Swell period
                      int sstIndex  = line.indexOf("SST");   // Sea Surface Temp
                      int wwpIndex  = line.indexOf("WWP");   // Wave period
                      int wwhIndex  = line.indexOf("WWH");   // Wave height
                      int wspIndex  = line.indexOf("WSP");   // Wind speed
                      int wtIndex   = line.indexOf("WT");    // drybulb


                      // varibles used by all codes
                      int invalidIndex = line.indexOf("Invalid");
                      int rejectIndex = line.indexOf("reject:");
                      int csirIndex = line.indexOf("CSIR");
                      int wmoIndex = line.indexOf("WMO");

                      // code,  limit type, status
                      // DW, (CSIR or WMO), Invalid       case2:

                      if ((atIndex >= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         CSIR_AT_Invalid_Cnt++;
                      }
                      if ((atIndex >= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         WMO_AT_Invalid_Cnt++;
                      }

                      if ((dwIndex >= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         String code = line.substring(dwIndex,dwIndex+2);
                         String limitType = line.substring(csirIndex,csirIndex+4);
                         String status = line.substring(invalidIndex,invalidIndex+7);

                         System.out.println(" code "+code);
                         System.out.println(" limitType "+limitType);
                         System.out.println(" status "+status);
                         CSIR_DW_Invalid_Cnt++;
                      } //



                      if ((dwIndex >= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         String code = line.substring(dwIndex,dwIndex+2);
                         String limitType = line.substring(wmoIndex,wmoIndex+4);
                         String status = line.substring(invalidIndex,invalidIndex+7);

                         System.out.println(" code "+code);
                         System.out.println(" limitType "+limitType);
                         System.out.println(" status "+status);

                         WMO_DW_Invalid_Cnt++;
                      }

                      // DW, reject:             case3:
                      if ((dwIndex >= 0) && (rejectIndex >=0)) {

                         String code = line.substring(dwIndex,dwIndex+2);
                         String status = line.substring(rejectIndex,rejectIndex+6);

                         System.out.println(" code "+code);
                         System.out.println(" status "+status);

                         DW_Reject_Cnt++;
                      }

                     // AT, CSIR, INVALID         case4:

                     //SLP
                      if ((slpIndex>= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         CSIR_SLP_Invalid_Cnt++;
                      }
                      if ((slpIndex>= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         WMO_SLP_Invalid_Cnt++;
                      }

                      //SW1H
                      if ((sw1hIndex>= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         CSIR_SW1H_Invalid_Cnt++;
                      }
                      if ((sw1hIndex>= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         WMO_SW1H_Invalid_Cnt++;
                      }

                      //SW1P
                      if ((sw1pIndex>= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         CSIR_SW1P_Invalid_Cnt++;
                      }
                      if ((sw1pIndex>= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         WMO_SW1P_Invalid_Cnt++;
                      }

                      //SST
                      if ((sstIndex>= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         CSIR_SST_Invalid_Cnt++;
                      }
                      if ((sstIndex>= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         WMO_SST_Invalid_Cnt++;
                      }

                      //WWP
                      if ((wwpIndex>= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         CSIR_WWP_Invalid_Cnt++;
                      }
                      if ((wwpIndex>= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         WMO_WWP_Invalid_Cnt++;
                      }

                      //WWH
                      if ((wwhIndex>= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         CSIR_WWH_Invalid_Cnt++;
                      }
                      if ((wwhIndex>= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         WMO_WWH_Invalid_Cnt++;
                      }

                      //WSP
                      if ((wspIndex>= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         CSIR_WSP_Invalid_Cnt++;
                      }
                      if ((wspIndex>= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         WMO_WSP_Invalid_Cnt++;
                      }

                      //WT
                      if ((wtIndex>= 0) && (invalidIndex >=0) && (csirIndex >= 0) ) {
                         CSIR_WT_Invalid_Cnt++;
                      }
                      if ((wtIndex>= 0) && (invalidIndex >=0) && (wmoIndex >= 0) ) {
                         WMO_WT_Invalid_Cnt++;
                      }



                   } else {
                       eof = true;
                   }


            } //while

            try {

                 outfile = new RandomAccessFile(pathName+outFile,"rw");

                 System.out.println(" writting to file :"+pathName+outFile);

                 outfile.writeBytes("File name : "+inFile+"\n\n");
                 /*
                 outfile.writeBytes("AT "+CSIR_AT_Invalid_Cnt+"\n");
                 outfile.writeBytes("DW Point Invalid Count :"+CSIR_DW_Invalid_Cnt+"\n");
                 outfile.writeBytes("SLP "+CSIR_SLP_Invalid_Cnt+"\n");
                 outfile.writeBytes("SW1H "+CSIR_SW1H_Invalid_Cnt+"\n");
                 outfile.writeBytes("SW1P "+CSIR_SW1P_Invalid_Cnt+"\n");
                 outfile.writeBytes("SST "+CSIR_SST_Invalid_Cnt+"\n");
                 outfile.writeBytes("WWP "+CSIR_WWP_Invalid_Cnt+"\n");
                 outfile.writeBytes("WWH "+CSIR_WWH_Invalid_Cnt+"\n");
                 outfile.writeBytes("WSP "+CSIR_WSP_Invalid_Cnt+"\n");
                 outfile.writeBytes("WT "+CSIR_WT_Invalid_Cnt+"\n\n");

                 outfile.writeBytes("WMO stats.\n");
                 outfile.writeBytes("AT "+WMO_AT_Invalid_Cnt+"\n");
                 outfile.writeBytes("DW Point Invalid Count :"+WMO_DW_Invalid_Cnt+"\n");
                 outfile.writeBytes("SLP "+WMO_SLP_Invalid_Cnt+"\n");
                 outfile.writeBytes("SW1H "+WMO_SW1H_Invalid_Cnt+"\n");
                 outfile.writeBytes("SW1P "+WMO_SW1P_Invalid_Cnt+"\n");
                 outfile.writeBytes("SST "+WMO_SST_Invalid_Cnt+"\n");
                 outfile.writeBytes("WWP "+WMO_WWP_Invalid_Cnt+"\n");
                 outfile.writeBytes("WWH "+WMO_WWH_Invalid_Cnt+"\n");
                 outfile.writeBytes("WSP "+WMO_WSP_Invalid_Cnt+"\n");
                 outfile.writeBytes("WT "+WMO_WT_Invalid_Cnt+"\n\n");

                 outfile.writeBytes("DW reject stats.\n");
                 outfile.writeBytes("DW Point Reject Count  :"+DW_Reject_Cnt+"\n\n");

                 */

                 // Dew-point Temp.
                 // Sea-level pressure
                 // Swell height
                 // Swell period
                 // Sea Surface Temp
                 // Wave period
                 // Wave height
                 // Wind speed
                 // Drybulb

                 //outfile.writeBytes("          WMO      CSIR       DW Reject \n");
                 outfile.writeBytes("               "+
                    frmString("WMO",18)+
                    frmString("CSIR",18)+
                    frmString("DW Reject",18)+"\n");

                 outfile.writeBytes("AT  - Air Temp.         "+
                    frm(CSIR_AT_Invalid_Cnt,10)+
                    frm(WMO_AT_Invalid_Cnt,10)+"\n");

                 outfile.writeBytes("DW  - Dew-point Temp.   "+
                    frm(CSIR_DW_Invalid_Cnt,10)+
                    frm(WMO_DW_Invalid_Cnt,10)+
                    frm(DW_Reject_Cnt,10)+"\n");

                 //outfile.writeBytes("DW   "+CSIR_DW_Invalid_Cnt  +"   "+WMO_DW_Invalid_Cnt+"   "+DW_Reject_Cnt+"\n");
                 outfile.writeBytes("SLP - Sea-level pressure."+
                    frm(CSIR_SLP_Invalid_Cnt,10)+
                    frm(WMO_SLP_Invalid_Cnt,10)+"\n");

                 outfile.writeBytes("SW1H - Swell height      "+
                    frm(CSIR_SW1H_Invalid_Cnt,10)+
                    frm(WMO_SW1H_Invalid_Cnt,10)+"\n");

                 outfile.writeBytes("SW1P - Swell period      "+
                    frm(CSIR_SW1P_Invalid_Cnt,10)+
                    frm(WMO_SW1P_Invalid_Cnt,10)+"\n");

                 outfile.writeBytes("SST - Sea Surface Temp.  "+
                    frm(CSIR_SST_Invalid_Cnt,10)+
                    frm(WMO_SST_Invalid_Cnt,10) +"\n");

                 outfile.writeBytes("WWP - Wave Period        "+
                    frm(CSIR_WWP_Invalid_Cnt,10)+
                    frm(WMO_WWP_Invalid_Cnt,10)+"\n");

                 outfile.writeBytes("WWH - Wave Height        "+
                    frm(CSIR_WWH_Invalid_Cnt,10)+
                    frm(WMO_WWH_Invalid_Cnt,10)+"\n");

                 outfile.writeBytes("WSP - Wind Speed         "+
                    frm(CSIR_WSP_Invalid_Cnt,10)+
                    frm(WMO_WSP_Invalid_Cnt,10)+"\n");

                 outfile.writeBytes("WT - Drybulb             "+
                    frm(CSIR_WT_Invalid_Cnt,10)+
                    frm(WMO_WT_Invalid_Cnt,10)+"\n\n");

            } catch (Exception e) {
                if (dbg) System.out.println("<br>" + thisClass + "open files error: " +
                    e.getMessage());
                e.printStackTrace();
            } // try .. catch


        } catch (Exception e) {
            System.out.println("error in reading : "+ e.getMessage());
                e.printStackTrace();
            e.printStackTrace();
        } // try .. catch


        try {
            infile.close();
            outfile.close();

        } catch (Exception e) {
            if (dbg) System.out.println("<br>" + thisClass + "close files error: " +
                e.getMessage());
            e.printStackTrace();
        } // try .. catch


     } //public CountRecs(String args[]) {

    public String frm (int value, int width) {
      if (dbg) System.out.print ("int: " + value + ":" + width + "*");   // debug
      String svalue = new Integer(value).toString();
      String rvalue = "";
      for (int i=0; i<width-svalue.length(); i++) rvalue = rvalue + frmFiller;
      rvalue = rvalue + svalue;
      if (dbg) System.out.println (svalue + "*" + rvalue + "*");   // debug
      return rvalue;
    }   // frm (int value

    public String frmString (String value, int width) {
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

    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            CountRecs local= new CountRecs(args);
        } catch(Exception e) {
            System.out.println("error in main" + e.getMessage());
                e.printStackTrace();
        } // try-catch

    } // main


} // class CountRecs

                      /*

                    // done per record line
                    String one = "";           // 1
                    String two = "";            // 2
                    String skip3 = "";         // 3
                    String skip4 = "";         // 4
                    String skip5 = "";         // 5
                    String skip6 = "";         // 6
                    String rejectStatus = "";  // 7
                    String skip8 = "";         // 6
                    String skip9 = "";         // 6
                    String type  = "";

                      StringTokenizer token = new StringTokenizer(line," ");
                      System.out.println(" writting to file :"+pathName+outFile);


                        one = token.nextToken();
                        two = token.nextToken();
                        skip3 = token.nextToken();
                        skip4 = token.nextToken();
                        skip5 = token.nextToken();
                        skip6 = token.nextToken();
                        rejectStatus = token.nextToken();
                        skip8 = token.nextToken();
                        skip9 = token.nextToken();
                        type  = token.nextToken();


                        System.out.println("one "+one);
                        System.out.println("two "+two);
                        System.out.println("skip3 "+skip3);
                        System.out.println("skip4 "+skip4);
                        System.out.println("skip5 "+skip5);
                        System.out.println("skip6 "+skip6);
                        System.out.println("rejectStatus "+rejectStatus);

                        // if rejectStatus == invalid - vos_reject
                        // if rejectStatus == reject record just deleted.



                        if (two.equals("DW") && rejectStatus.equals("reject:")) {
                        }


                        if (type.equals("CSIR")) {
                            if ((two.equals("DW")) && (rejectStatus.equals("Invalid")) ) {
                                CSIR_DW_Invalid_Cnt++;
                            }
                        }

                        if (type.equals("WMO")) {
                            if ((two.equals("DW")) && (rejectStatus.equals("Invalid")) ) {
                                WMO_DW_Invalid_Cnt++;
                            }
                        }

                        */


