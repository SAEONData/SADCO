package sadco;

import common2.*;
import java.io.*;
import java.util.*;
import oracle.html.*;

/**
 * Get the parameters, create parameter files, submit the product, and
 * give a message on the screen.                                            <br>
 *
 * SadcoMRN
 * screen.equals("product")
 * version.equals("product")
 *
 * @author 001206 - SIT Group
 * @version
 * 001206 - Dave McKelly       - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020709 - Ursula von St Ange - dtspl parms file now has to have both lat
 *                               and lon range limits (prev only had bottom
 *                               limit)                                     <br>
 * 020709 - Ursula von St Ange - display output file name pattern on screen <br>
 * 091215 - Ursula von St Ange - change for inventory users (ub01)          <br>
 */
public class ProductMRNFrame extends CompoundItem{

    boolean     dbg = false;
    //boolean     dbg = true;
    String thisClass = this.getClass().getName();


    /** some common functions */
    common.edmCommon ec = new common.edmCommon();
    /** url parameters names & applications */
    sadco.SadConstants sc = new sadco.SadConstants();

    // date at start of execution time
    java.util.Date dt = new java.util.Date();

    // parameter file
    RandomAccessFile parmsFile;

    // parameter values
    String parms[] = new String[SadConstants.MRNPARMSLIST.length];

    // vars for all product types
    String startYear;
    String endYear;
    String startMonth;
    String endMonth;

    String startDepth;
    String endDepth;

    String surveyId;

    String latitudeNorth;
    String latitudeSouth;
    String longitudeWest;
    String longitudeEast;

    // vars for specific products
    String plotType;     // inv
    String blockIndex;   // inv, mea
    String tableSize;    // mea
    String tableType;    // mea
    String multiType;    // mea, ros, sca
    String elementType;  // ros
    String startRange;   // his
    String endRange;     // his
    String xStartRange;  // sca
    String xEndRange;    // sca
    String yStartRange;  // sca
    String yEndRange;    // sca
    String dayRLow;      // tim
    String dayRHigh;     // tim
    String hourRLow;     // tim
    String hourRHigh;    // tim
    String axisRLow;     // tim
    String axisRHigh;    // tim
    String timeUnits;    // tim
    String unitPcell;    // tim
    String stdDev;       // tim

    String tmpLow;       // tempsalin
    String tmpHigh;      // tempsalin
    String salinLow;     // tempsalin
    String salinHigh;    // tempsalin

    String minDepPar;    //other formats
    String minDepVal;    //other formats

    String limit1;       //thermo
    String limit2;       //thermo


    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ProductMRNFrame (String args[]) {

        try {
            ProductMRNFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ProductMRNFrame constructor

    /**
    * Creates a page with a banner and an entry form
    * @param args the string array of url-type name-value parameter pairs
    */
    void ProductMRNFrameActual(String args[]) {

        String sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        String productStr = ec.getArgument(args, sc.PRODUCT);
        int product = Integer.parseInt(productStr.trim());
        if (dbg) System.out.println("productStr=" + productStr + "<br>");
        String fileName = ec.getArgument(args, sc.FILENAME);
        String surveyId = ec.getArgument(args, sc.SURVEYID);
        // for the top menu bar
        String userType = ec.getArgument(args, sc.USERTYPE);
        String menuNo = ec.getArgument(args, menusp.MnuConstants.MENUNO);
        String mVersion = ec.getArgument(args, menusp.MnuConstants.MVERSION);

        // ................ EXTRACT THE USER record from db .....................
        LogSessions session = new LogSessions();
        session.setCode(sessionCode);
        LogSessions sessions[] = session.get();
        if (dbg) System.out.println("<br>Session code = " + sessionCode);
        String userId = sessions[0].getUserid();
        String directory = userId;                                      //ub01
        if ("0".equals(userType)) directory = "inv_user";               //ub01

        // .................PATH NAME: Get the correct........................
        String pathName = "";
        //if ("morph".equals(ec.getHost())) {
        //    pathName = sc.MORPH + userId + "/";
        //} else {
        //    pathName = sc.LOCAL;
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            //pathName = sc.HOSTDIR + userId + "/";                     //ub01
            pathName = sc.HOSTDIR + directory + "/";                    //ub01
        } else {
            pathName = sc.LOCALDIR;
        }

        if (dbg) System.out.println("<br>pathName = " + pathName);

        String parmsFileName = "";
        String program = "";
        String quote = "\"";
        String space = " ";
        String line1 = "";
        String line2 = "";
        String line3 = "";
        String line4 = "";
        String line5 = "";
        String line6 = "";
        String line7 = "";
        String line8 = "";

        //................SWITCH....................................
        switch (product){
            case(SadConstants.MINVN): //inventories
                readParameters(args, product);
                if (plotType.equals("1")) {
                    program = "mapgd";
                } else {
                    program = "mapsy";
                } // if (plotType.equals("1"))
                line1 = space + quote + "Marine" + quote + space +
                        quote + userId + space + toDayNow() + quote + space + quote +
                        surveyId + quote;
                line2 = startYear + space + endYear + space + startMonth + space +
                        endMonth + space + startDepth + space + endDepth;
                line3 = latitudeNorth + space + latitudeSouth + space +
                        longitudeWest + space + longitudeEast + space +
                        blockSizeValue(blockIndex);
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(line2);
                printLine(line3);
                printParameters(product);
                printParms(product);
                break;

            case(SadConstants.MTSPL): //Temperature Salinity Curves
                readParameters(args, product);
                program = "dtspl";
                line1 = space + quote + "Marine" + quote + space +
                        quote + userId + space + toDayNow() + quote + space +
                        quote + surveyId + quote;
                line2 = tmpLow + space + tmpHigh + space + salinLow + space + salinHigh +
                        " 0 9999";
                line3 = startYear + space + endYear + space +
                        startMonth + space + endMonth + space +
                        startDepth + space + endDepth + space + multiType;
                line4 = latitudeNorth + space + latitudeSouth + space +
                        longitudeWest + space + longitudeEast;
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(line2);
                printLine(line3);
                printLine(line4);
                break;

            case(SadConstants.MMEAN): //means
                program = "means";
                readParameters(args, product);
                line1 = startYear + space + endYear + space +
                        startMonth + space + endMonth + space +
                        startDepth + space + endDepth + space +
                        multiType + space + tableType;
                line2 = latitudeNorth + space + longitudeWest + space + tableSize;
                line3 = blockSizeValue(blockIndex);
                if (dbg) System.out.println("<br>fileName = " + fileName);
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(surveyId);
                printLine(line2);
                printLine(line3);
                printParameters(product);
                printParms(product);
                break;

            case(SadConstants.MOTHR): //Create other formats
                ec.setFrmFiller("0");
                program = "marpnut";
                readParameters(args, product);
                line1 = latitudeNorth + space + latitudeSouth ;
                line2 = longitudeWest + space + longitudeEast ;
                line3 = startYear + ec.frm(startMonth,2) + "01 " +
                        endYear + ec.frm(endMonth,2) + "31";
                ec.setFrmFiller(" ");
                if ("0".equals(minDepPar)) {
                    line4 = minDepVal + space + "0.0";
                } else {
                    line4 = "0.0" + space + minDepVal;
                } // if ("0".equals(minDepPar))

                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(line2);
                printLine(line3);
                printLine(line4);
                break;

            case(SadConstants.MTHRM): //Thermo means
                program = "thermo";
                readParameters(args, product);
                line1 = startYear + space + endYear + space +
                        startMonth + space + endMonth + space +
                        multiType + space + tableType;
                line2 = surveyId;
                line3 = latitudeNorth + space + longitudeWest + space + tableSize;
                line4 = limit1 + space + limit2;
                line5 = blockSizeValue(blockIndex);
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(line2);
                printLine(line3);
                printLine(line4);
                printLine(line5);
                printParameters(product);
                printParms(product);
                break;
            default:
                //showError();
        } // switch

        try {
            parmsFile.close();
        } catch (Exception e) {}

        // ....................... Submit process .....................
        // !/bin/sh
        // $1 == user, $2 == dbd file name, $3 == parms file name
        //String scriptFile = sc.FORTRAN + space + program + space + userId + space +
        //    fileName + space + dropExtention(parmsFileName);
        //String scriptFile = sc.FORTRAN + space + program + space + userId + space +
        String scriptFile = sc.FORTRAN + space + program + space + directory + space +  // ub01
            fileName + space + dropExtention(parmsFileName);
        System.err.println("<br>SadcoMRN: scriptFile = " + scriptFile);
        //System.err.println("<br>SadcoMRN: sc.HOST = " + sc.HOST);
        //System.err.println("<br>SadcoMRN: ec.getHost() = " + ec.getHost());
        if (sc.HOST.equals(ec.getHost())) {
            ec.submitJob(scriptFile);
            //System.err.println("<br>SadcoMRN: job submitted");
        } // if ("morph".equals(ec.getHost())

        // the main table
        DynamicTable mainTable = new DynamicTable(1);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        //mainTable.setFrame(ITableFrame.BOX);

        // create the html page
        mainTable.addRow(ec.cr1ColRow("<font size=+2>Products</font>",
            true, "blue", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("<br>"));
        mainTable.addRow(ec.cr1ColRow("Product <b>" + sc.MRNPRODLIST[product-1] +
            "</b> for file <b>" + fileName + "</b> is being" +
            " processed", false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("The output files will be called <b>" +
            dropExtention(parmsFileName) + ".*", false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("The <b>'Product Status and Download'</b> "+
            "page will appear in 15 seconds", false, "", IHAlign.CENTER));

        // create the form
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST", sc.MRN_APP, target);
        form.addItem(new Hidden(sc.SCREEN, "product"));
        form.addItem(new Hidden(sc.VERSION, "progress"));
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.FILENAME, fileName));
        form.addItem(new Hidden(sc.PRODUCT, productStr));
        // for the menu system
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        form.addItem(new Submit("", "OK"));

        // add components to screen
        this.addItem(mainTable.setCenter());
        this.addItem(form.setCenter());

        updateLogUsers(fileName, userId, product);

    } // ProductMRNFrameActual


    void readParameters(String args[], int product){

        startYear = ec.getArgument(args, sc.STARTYEAR);
        endYear = ec.getArgument(args, sc.ENDYEAR);
        startMonth = ec.getArgument(args, sc.STARTMONTH);
        endMonth = ec.getArgument(args, sc.ENDMONTH);

        if (product != sc.MOTHR ) {
            startDepth = ec.getArgument(args, sc.STARTDEPTH);
            endDepth = ec.getArgument(args, sc.ENDDEPTH);
        } // if (product != sc.MOTHR )

        surveyId = ec.getArgument(args, sc.SURVEYID);

        latitudeNorth = ec.getArgument(args, sc.LATITUDENORTH);
        latitudeSouth = ec.getArgument(args, sc.LATITUDESOUTH);
        longitudeWest = ec.getArgument(args, sc.LONGITUDEWEST);
        longitudeEast = ec.getArgument(args, sc.LONGITUDEEAST);


        // get the 'parameter' values and other common parm values
        if ((product == sc.MINVN) || (product == sc.MMEAN) ||
            (product == sc.MTHRM)) { // inventories, means, thermo

            int end = sc.MRNPARMSLIST.length;
            if (product == sc.MMEAN ) { end -= 1; }  // inventories has extra station
            //int start = 0;
            //int end = sc.MRNPARMSLIST.length;

            //if (product == sc.MMEAN) { start = 1; }

            if (product == sc.MTHRM) {
                end = sc.THRMPARMSLIST.length;
            } // if (product == sc.MTHRM) {

            for (int i = 0; i < end; i++) {
                parms[i] = ec.getArgument(args, sc.PARMS+(i+1));
                if (dbg) System.out.println("<br> " + sc.PARMS+(i+1) + " " + parms[i]);
            } // for int i


        } //else if ((product == 4) || (product == 6)) { // histories, time series
        //     parms[0] = ec.getArgument(args, sc.PARMS);

        //} else if (product == 5) { // scattergrams
        //     parms[0] = ec.getArgument(args, sc.PARMS+"x");
        //     parms[1] = ec.getArgument(args, sc.PARMS+"y");
        //} //if ((product == 1) || (product == 2))

        // get the other parm values
        if (product == sc.MINVN) { // inventories
            plotType = ec.getArgument(args, sc.PLOTTYPE);
        } // if (product == 1)

        if ((product == sc.MINVN) || (product == sc.MMEAN) ||
            (product == sc.MTHRM)) { // inventories, means, thermo
            blockIndex = ec.getArgument(args, sc.BLOCKSIZE);
            if (dbg) System.out.println("<br>blockindex = " + blockIndex);
        } //if ((product == 1) || (product == 2))

        if (product == sc.MTSPL) {
            tmpLow = ec.getArgument(args, sc.TEMPLOW);
            tmpHigh = ec.getArgument(args, sc.TEMPHIGH);
            salinLow = ec.getArgument(args, sc.SALINLOW);
            salinHigh = ec.getArgument(args, sc.SALINHIGH);
        } // if (product == sc.MTSPL) {

        if ((product == sc.MMEAN) || (product == sc.MTHRM)) { // inventories, means, thermo
            tableSize = ec.getArgument(args, sc.TABLESIZE);
            tableType = ec.getArgument(args, sc.TABLETYPE);
        } // if (product == 2)

        if ((product == sc.MMEAN) || (product == sc.MTSPL) ||
            (product == sc.MTHRM)) { // means, thermo, tempsalin
            multiType = ec.getArgument(args, sc.MULTITYPE);
        } // if ((product == 2) || (product == 3) || (product == 5))

        if ((product == sc.MOTHR)) {
            minDepPar = ec.getArgument(args, sc.MINDEPPAR);
            minDepVal = ec.getArgument(args, sc.MINDEPVAL);
        }

        if (product == sc.MTHRM) {
            limit1 = ec.getArgument(args, sc.LIMIT1);
            limit2 = ec.getArgument(args, sc.LIMIT2);
        } // if (product == sc.MTHRM)

    } // void readParameters(){


    String blockSizeValue(String blockIndex){
        StringTokenizer t = new StringTokenizer (blockIndex, "-");
        int i = Integer.parseInt(t.nextToken().trim());
        int j = Integer.parseInt(t.nextToken().trim());
        return String.valueOf(sc.BSIZE[i][j]);
    } // blockSizeValue



    // ................OPEN THE PARAMETER FILE ................
    String openParmsFile(String pathN, String fileN, int product){

        String parmsFileName = fileTest(pathN, fileN, product);
        try {
            parmsFile = new RandomAccessFile(pathN + parmsFileName, "rw");
        } catch (Exception e) {
            System.out.println("<br>" + thisClass + ".openParmsFile: error: " +
                e.getMessage());
            e.printStackTrace();
        }  // try-catch
        if (sc.HOST.equals(ec.getHost())) {
            ec.submitJob("chmod 644 " + pathN + parmsFileName);
        } // if ("morph".equals(ec.getHost())
        return parmsFileName;
    } // openParmsFile


    // ............. Print the record to the parms file .................
    void printLine(String line){
        ec.writeFileLineBR(parmsFile,line);
    } // void printLine

    // ............. Print the parameters (parms) to the parms file .................
    /*
    void printParms(int product) {
        if ((product == 1) || (product == 2)) { // inventories, means
            for (int i = 0; i < sc.MRNPARMSLIST.length; i++) {
                //if (dbg) System.out.println("<br> " + i + "*" + parms[i] + "*");
                if (!("").equals(parms[i])) {
                    ec.writeFileLine(parmsFile, parms[i]);
                    //if (dbg) System.out.println("<br> " + i + "*" + parms[i] + "*");
                } // if (!("").equals(parms[i]))
            } // for (int i
        } else if ((product == 4) || (product == 6)) { // histograms, time series
            ec.writeFileLine(parmsFile, parms[0]);
        } else if (product == 5) { // scattergrams
            ec.writeFileLine(parmsFile,parms[0]);
            ec.writeFileLine(parmsFile,parms[1]);
        } // if ((product == 1) || (product == 2))
    } // void printParms(String tagg){ */


    void printParms(int product) {
        if ((product == sc.MINVN) || (product == sc.MMEAN) ||
            (product == sc.MTHRM)) { // inventories, means, thermo

            int end = sc.MRNPARMSLIST.length;
            if (product == sc.MMEAN ) { end -= 1; }  // inventories has extra station
            if (product == sc.MTHRM) {
                end = sc.THRMPARMSLIST.length;
            } // if (product == sc.MTHRM)

            for (int i = 0; i < end; i++) {
                //if (dbg) System.out.println("<br> " + i + "*" + parms[i] + "*");
                if (!("").equals(parms[i])) {
                    //ec.writeFileLineBR(parmsFile, parms[i]);
                    printLine(parms[i]);
                    //if (dbg) System.out.println("<br> " + i + "*" + parms[i] + "*");
                } // if (!("").equals(parms[i]))
            } // for (int i

        } // if ((product == sc.MINVN) || (product == sc.MMEAN)){

    } // void printParms(String tagg){
    //
    // .....................
    //
    void printParameters(int product){
        // there is one exsta parameter for the inventory
        // inventry loop start at 0 and means loop start's at 1

        if ( product != sc.MTHRM ) {
            int stop = sc.MRNPARMSLIST.length;
            if ( product != sc.MINVN ) { stop -= 1; }
            for (int i = 0; i < stop; i++) {
                 //ec.writeFileLineBR(parmsFile, sc.MRNPARMSLIST[i]);
                 printLine(sc.MRNPARMSLIST[i]);
            } // for i = start; i < stop; i++)

            //if ((product == 1) || (product == 2) || (product == 5) || (product == 6)) {
            //      ec.writeFileLine(parmsFile, sc.MRNPARMSLIST[sc.MRNPARMSLIST.length-1]);
            //} // if ((product == 1) ||  ...
            if (product == sc.MINVN) { // inventories
                for (int i = sc.MRNPARMSLIST.length; i < 20; i++ ){
                    printLine(" ");
                } // for (int i
            } // if (product == 1)
        } else {
            for (int i = 0; i < sc.THRMPARMSLIST.length; i++) {
                //ec.writeFileLineBR(parmsFile, sc.THRMPARMSLIST[i]);
                printLine(sc.THRMPARMSLIST[i]);
            } // for i
        } // if ( product != sc.MTHRM ) {

    } // printParameters
    /**
     * Find the next unique file name
     */
    String fileTest(String path, String fileName, int product) {
        boolean flag = true;
        int count = 1;
        //int dotdbdPosition = fileName.indexOf('.');
        fileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_" +
            sc.MRNPRODLIST[product-1].substring(0,3).toLowerCase();
        String localFileName = "";
        while (flag) {
            localFileName = fileName + String.valueOf(count) + ".parms";
            if (dbg) System.out.println("<br>localfilename1 = " + localFileName);
            if (!ec.fileExists(path + localFileName)) {
                flag = false;
            } // if (!ec.fileExists(path + localFileName))
            count += 1;
        } // while (flag)
        if (dbg) System.out.println("<br>localfilename2 = " + localFileName);
        return localFileName;
    } //  fileTest

    //
    //   ............. Return the present Time and Date ...............
    //   ............. FORMAT:  yyyy/mm/dd hh:mm:ss  ..................
    //
    String toDayNow(){
        GregorianCalendar calDate = new GregorianCalendar();
        java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        calDate.setTime(dt);
        return formatter.format(calDate.getTime());
    } // toDayNow

    //
    // ................  Drop the extention to the fileName ...................
    //
    String dropExtention(String nameStr){
        return nameStr.substring(0, nameStr.lastIndexOf('.'));
    } // dropExtention

    void updateLogUsers(String fileName, String userid, int product) {

        java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat ("yyyy-MM-dd");
        // get start date
        GregorianCalendar calDate = new GregorianCalendar(
            Integer.parseInt(startYear), Integer.parseInt(startMonth)-1,1);
        String start = formatter.format(calDate.getTime());
        // get end date
        calDate = new GregorianCalendar(
            Integer.parseInt(endYear), Integer.parseInt(endMonth)-1,1);
        int lastDay = calDate.getMaximum(Calendar.DATE);
        calDate.set(Calendar.DATE, lastDay);
        String end = formatter.format(calDate.getTime());

        /// update logUsers table
        LogUsers logs = new LogUsers();
        logs.setUserid(userid);
        logs.setDateTime(new java.sql.Timestamp(new java.util.Date().getTime()));
        //logs.setDatabase("MARCLIM");
        //logs.setDiscipline("weather");
        logs.setDatabase("Marine");
        logs.setDiscipline("PHYSNUT");

        String temp = sc.MRNPRODLIST[product-1];
        if (temp.length() > 10) temp = temp.substring(0,10);
        logs.setProduct(temp);

        logs.setFileName(fileName);
        logs.setLatitudeNorth(latitudeNorth);
        logs.setLatitudeSouth(latitudeSouth);
        logs.setLongitudeWest(longitudeWest);
        logs.setLongitudeEast(longitudeEast);
        logs.setDateStart(start);
        logs.setDateEnd(end);
        logs.setSurveyId("product");
        logs.setNumRecords(-9999);
        logs.setJobDuration("n/a");
        logs.setQueueDuration("n/a");
        System.err.println(logs.toString());
        boolean success = logs.put();

    } // updateLogUsers


} // class
