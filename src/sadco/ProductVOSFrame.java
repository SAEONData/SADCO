package sadco;

import common2.*;
import java.io.*;
import java.util.*;
import oracle.html.*;

/**
 * Submit the products
 *
 * SadcoVOS
 * screen.equals("product")
 * version.equals("product")
 *
 * @author  001206 - SIT Group
 * @version
 * 001206 - Dave McKelly       - create class                               <br>
 * 020528 - Ursula von St Ange - update for non-frame menus                 <br>
 * 020528 - Ursula von St Ange - update for exception catching              <br>
 * 020709 - Ursula von St Ange - display output file name pattern on screen <br>
 * 091215 - Ursula von St Ange - change for inventory users (ub01)          <br>
 */
public class ProductVOSFrame extends CompoundItem{

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
    String parms[] = new String[10];

    // vars for all product types
    String startYear;
    String endYear;
    String startMonth;
    String endMonth;

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

    // Product constants
    //static final int sc.VINVN = 1;  VINVN
    //static final int sc.VMEAN = 2;  VMEAN
    //static final int sc.VROSE = 3;  VROSE
    //static final int sc.VHIST = 4;  VHIST
    //static final int sc.VSCAT = 5;  VSCAT
    //static final int sc.VTIME = 6;  VTIME

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public ProductVOSFrame (String args[]) {

        try {
            ProductVOSFrameActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // ProductVOSFrame constructor


    /**
     * Does the actual work.
     * @param args the string array of url-type name-value parameter pairs
     */
    void ProductVOSFrameActual(String args[]) {

        String sessionCode = ec.getArgument(args, sc.SESSIONCODE);
        String productStr = ec.getArgument(args, sc.PRODUCT);
        int product = Integer.parseInt(productStr.trim());
        if (dbg) System.out.println("productStr=" + productStr + "<br>");
        String fileName = ec.getArgument(args, sc.FILENAME);
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
        //if (dbg) System.out.println("<br>pathName = " + pathName);

        if (ec.getHost().startsWith(sc.HOST)) {
            //pathName = sc.HOSTDIR + userId + "/";                     //ub01
            pathName = sc.HOSTDIR + directory + "/";                    //ub01
        } else {
            pathName = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(sc.HOST)) {

        String parmsFileName = "";
        String program = "";
        String quote = "\"";
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
            case(SadConstants.VINVN): //inventories
                readParameters(args, product);
                if (plotType.equals("1")) {
                    program = "mapgd";
                } else {
                    program = "mapsy";
                } // if (plotType.equals("1"))
                line1 = " " + quote + "MARCLIM" + quote + " " +
                    quote + userId + " " + toDayNow() + quote + " " + quote + quote ;
                line2 = startYear + " " + endYear + " " + startMonth + " " +
                    endMonth + " 0  9999";
                line3 = latitudeNorth + " " + latitudeSouth + " " +
                    longitudeWest + " " + longitudeEast + " " +
                    blockSizeValue(blockIndex);
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(line2);
                printLine(line3);
                printParameters(product);
                //for (int i = sc.VOSPARMSLIST.length; i < 20; i++ ){
                //    printLine(" ");
                //} // for (int i
                printParms(product);
                break;

            case(SadConstants.VMEAN): //means
                program = "means";
                readParameters(args, product);
                line1 = startYear + " " + endYear + " " + startMonth + " " +
                    endMonth + " 0  9999 " + multiType + " " + tableType;
                line2 = latitudeNorth + " " + longitudeWest + " " + tableSize;
                line3 = blockSizeValue(blockIndex);
                //if (dbg) System.out.println("<br>fileName = " + fileName);
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(" ");
                printLine(line2);
                printLine(line3);
                printParameters(product);
                printParms(product);
                break;

            case(SadConstants.VROSE): //roses
                program = "roses";
                readParameters(args, product);
                line1 = userId + " " + toDayNow();
                line2 = elementType;
                line3 = startYear + " " + endYear;
                line4 = startMonth + " " + endMonth + " " + multiType;
                line5 = latitudeNorth + " " + latitudeSouth + " " +
                               longitudeWest + " " + longitudeEast;
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(line2);
                printLine(line3);
                printLine(line4);
                printLine(line5);
                break;

            case(SadConstants.VHIST): //histograms
                program = "histog";
                readParameters(args,product);
                line1 = startYear + " " + endYear + " " + startMonth + " " +
                               endMonth + " 0 0";
                line2 = latitudeNorth + " " + latitudeSouth + " " +
                               longitudeWest + " " + longitudeEast;
                if (startRange.equals("")) startRange = "0";
                if (endRange.equals("")) endRange = "0";
                line3 = String.valueOf(sc.VOSPARMSLIST.length-1) + " " +
                    startRange + " " + endRange;
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(line2);
                printLine(line3);
                printParameters(product);
                printParms(product);
                break;

            case(SadConstants.VSCAT)://scattergrams
                program = "sctpl";
                readParameters(args,product);
                line1 = ec.frm(" ", 8) + userId + " " + toDayNow();
                line2 = startYear + " " + endYear + " " + startMonth + " " +
                               endMonth + " 0 0 " + multiType;
                line3 = latitudeNorth + " " + latitudeSouth + " " +
                               longitudeWest + " " + longitudeEast;
                if (xStartRange.equals("")) xStartRange = "0";
                if (xEndRange.equals(""))   xEndRange = "0";
                if (yStartRange.equals("")) yStartRange = "0";
                if (yEndRange.equals(""))   yEndRange = "0";
                line4 = xStartRange + " " + xEndRange;
                line5 = yStartRange + " " + yEndRange;
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(line2);
                printLine(line3);
                printParameters(product);
                printParms(product);
                printLine(line4);
                printLine(line5);
                break;

            case(SadConstants.VTIME)://time series
                program = "tims";
                readParameters(args,product);
                if (dayRLow.equals(""))   dayRLow = "0";
                if (dayRHigh.equals(""))  dayRHigh = "0";
                if (hourRLow.equals(""))  hourRLow = "0";
                if (hourRHigh.equals("")) hourRHigh = "0";
                if (axisRLow.equals(""))  axisRLow = "0";
                if (axisRHigh.equals("")) axisRHigh = "0";
                line1 = userId + " " + toDayNow();
                line2 = startYear + " " + endYear + " " + startMonth + " " +
                    endMonth + " " + dayRLow + " " +  dayRHigh + " " +
                    hourRLow + " " + hourRHigh + " 0  9999 " +
                    axisRLow + " " + axisRHigh;
                line3 = latitudeNorth + " " + latitudeSouth + " " +
                    longitudeWest + " " + longitudeEast;
                parmsFileName = openParmsFile(pathName, fileName, product);
                printLine(line1);
                printLine(line2);
                printLine(line3);
                printLine(timeUnits);
                printLine(unitPcell);
                printLine(stdDev);
                printLine(String.valueOf(sc.VOSPARMSLIST.length));
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
        // $1 == user, $2 == dbd file name (with extension), $3 == parms file name
        //String scriptFile = sc.FORTRAN + " " + program + " " + userId + " " +
        //    dropExtention(fileName) + " " + dropExtention(parmsFileName);
        String scriptFile = sc.FORTRAN + " " + program + " " + directory + " " + // ub01
            fileName + " " + dropExtention(parmsFileName);
        //if (dbg)
        System.err.println("<br>SadcoVOS: scriptFile = " + scriptFile);
        if (dbg) System.err.println("<br>SadcoVOS: hosts = " + sc.HOST + " " + ec.getHost());

        if (sc.HOST.equals(ec.getHost())) {
            ec.submitJob(scriptFile);
            if (dbg) System.err.println("<br>SadcoVOS: script submitted");
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
        mainTable.addRow(ec.cr1ColRow("Product <b>" + sc.VOSPRODLIST[product-1] +
            "</b> for file <b>" + fileName + "</b> is being" +
            " processed", false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("The output files will be called <b>" +
            dropExtention(parmsFileName) + ".*", false, "green", IHAlign.CENTER));
        mainTable.addRow(ec.cr1ColRow("The <b>'Product Status and Download'</b> "+
            "page will appear in 15 seconds (or press the OK button)", false, "", IHAlign.CENTER));

        // create the form
        String target = "_top";
        /*****************/
        if ("".equals(ec.getArgument(args, menusp.MnuConstants.MENUNO))) target = "middle";
        /*****************/
        Form form = new Form("POST", sc.VOS_APP, target);
        form.addItem(new Hidden(sc.SCREEN, "product"));
        form.addItem(new Hidden(sc.VERSION, "progress"));
        form.addItem(new Hidden(sc.SESSIONCODE, sessionCode));
        form.addItem(new Hidden(sc.FILENAME, fileName));
        form.addItem(new Hidden(sc.PRODUCT, productStr));
        form.addItem(new Submit("", "OK"));
        // for the menu system
        form.addItem(new Hidden(sc.USERTYPE, userType));
        form.addItem(new Hidden(menusp.MnuConstants.MENUNO, menuNo));
        form.addItem(new Hidden(menusp.MnuConstants.MVERSION, mVersion));

        // add components to screen
        this.addItem(mainTable.setCenter());
        this.addItem(form.setCenter());

        updateLogUsers(fileName, userId, product);

    } // ProductVOSFrameActual

    void readParameters(String args[], int product){

        startYear = ec.getArgument(args, sc.STARTYEAR);
        endYear = ec.getArgument(args, sc.ENDYEAR);
        startMonth = ec.getArgument(args, sc.STARTMONTH);
        endMonth = ec.getArgument(args, sc.ENDMONTH);

        latitudeNorth = ec.getArgument(args, sc.LATITUDENORTH);
        latitudeSouth = ec.getArgument(args, sc.LATITUDESOUTH);
        longitudeWest = ec.getArgument(args, sc.LONGITUDEWEST);
        longitudeEast = ec.getArgument(args, sc.LONGITUDEEAST);


        // get the 'parameter' values and other common parm values
        if ((product == sc.VINVN) || (product == sc.VMEAN)) { // inventories, means
            for (int i = 0; i < 10; i++) {
                parms[i] = ec.getArgument(args, sc.PARMS+(i+1));
                if (dbg) System.out.println("<br> " + sc.PARMS+(i+1));
            } // for int i

        } else if ((product == sc.VHIST) || (product == sc.VTIME)) { // histories, tims
             parms[0] = ec.getArgument(args, sc.PARMS);

        } else if (product == sc.VSCAT) { // scattergrams
             parms[0] = ec.getArgument(args, sc.PARMS+"x");
             parms[1] = ec.getArgument(args, sc.PARMS+"y");
        } //if ((product == sc.VINVN) || (product == sc.VMEAN))

        // get the other parm values
        if (product == sc.VINVN) { // inventories
            plotType = ec.getArgument(args, sc.PLOTTYPE);
        } // if (product == sc.VINVN)

        if ((product == sc.VINVN) || (product == sc.VMEAN)) { // inventories, means
            blockIndex = ec.getArgument(args, sc.BLOCKSIZE);
            if (dbg) System.out.println("<br>blockindex = " + blockIndex);
        } //if ((product == sc.VINVN) || (product == sc.VMEAN))

        if (product == sc.VMEAN) { // means
            tableSize = ec.getArgument(args, sc.TABLESIZE);
            tableType = ec.getArgument(args, sc.TABLETYPE);
        } // if (product == sc.VMEAN)

        if ((product == sc.VMEAN) || (product == sc.VROSE) || (product == sc.VSCAT)) {
            multiType = ec.getArgument(args, sc.MULTITYPE);
        } // if ((product == sc.VMEAN) || (product == sc.VROSE) || (product == sc.VSCAT))

        if (product == sc.VROSE) {
            elementType = ec.getArgument(args, sc.ELEMENTTYPE);
        } // if (product == sc.VROSE)

        if (product == sc.VHIST) {
            startRange = ec.getArgument(args, sc.STARTRANGE);
            endRange = ec.getArgument(args, sc.ENDRANGE);
        } // if (product == sc.VHIST)

        if (product == sc.VSCAT) {
            xStartRange = ec.getArgument(args, sc.XSTARTRANGE);
            xEndRange = ec.getArgument(args, sc.XENDRANGE);
            yStartRange = ec.getArgument(args, sc.YSTARTRANGE);
            yEndRange = ec.getArgument(args, sc.YENDRANGE);
        } // if (product == sc.VSCAT)

        if (product == sc.VTIME) {  //   Time series parameters
            dayRLow = ec.getArgument(args, sc.DAYRLOW);
            dayRHigh = ec.getArgument(args, sc.DAYRHIGH);
            hourRLow = ec.getArgument(args, sc.HOURRLOW);
            hourRHigh = ec.getArgument(args, sc.HOURRHIGH);
            axisRLow = ec.getArgument(args, sc.AXISRLOW);
            axisRHigh = ec.getArgument(args, sc.AXISRHIGH);
            timeUnits = ec.getArgument(args, sc.TIMEUNITS);
            unitPcell = ec.getArgument(args, sc.UNITPCELL);
            stdDev = ec.getArgument(args, sc.STDDEV);
        } // if (product == sc.VTIME)

        // used by 2 or more
        //tableType = ec.getArgument(args,"ptabletype");
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
        ec.submitJob("chmod 644 " + pathN + parmsFileName);
        return parmsFileName;
    } // openParmsFile


    // ............. Print the record to the parms file .................
    void printLine(String line){
                ec.writeFileLine(parmsFile,line);
    } // void printLine

    // ........ Print the parameters (parms) to the parms file .....
    void printParms(int product) {
        if ((product == sc.VINVN) || (product == sc.VMEAN)) { // inventories, means
            for (int i = 0; i < sc.VOSPARMSLIST.length; i++) {
                //if (dbg) System.out.println("<br> " + i + "*" + parms[i] + "*");
                if (!("").equals(parms[i])) {
                    ec.writeFileLine(parmsFile, parms[i]);
                    //if (dbg) System.out.println("<br> " + i + "*" +
                    //    parms[i] + "*");
                } // if (!("").equals(parms[i]))
            } // for (int i
        } else if ((product == sc.VHIST) || (product == sc.VTIME)) { // histograms, tims
            ec.writeFileLine(parmsFile, parms[0]);
        } else if (product == sc.VSCAT) { // scattergrams
            ec.writeFileLine(parmsFile,parms[0]);
            ec.writeFileLine(parmsFile,parms[1]);
        } // if ((product == sc.VINVN) || (product == sc.VMEAN))
    } // void printParms(String tagg){

    //
    // .....................
    //
    void printParameters(int product){
        for (int i = 0; i < sc.VOSPARMSLIST.length-1; i++) {
            ec.writeFileLine(parmsFile, sc.VOSPARMSLIST[i]);
        } // for i
        if ((product == sc.VINVN) || (product == sc.VMEAN) ||
            (product == sc.VSCAT) || (product == sc.VTIME)) {
            ec.writeFileLine(parmsFile, sc.VOSPARMSLIST[sc.VOSPARMSLIST.length-1]);
        } // if ((product == sc.VINVN) ||  ...
        if (product == sc.VINVN) { // inventories
            for (int i = sc.VOSPARMSLIST.length; i < 20; i++ ){
                printLine(" ");
            } // for (int i
        } // if (product == sc.VINVN)
    } // printParameters

    /**
     * Find the next unique file name
     */
    String fileTest(String path, String fileName, int product) {
        boolean flag = true;
        int count = 1;
        //int dotdbdPosition = fileName.indexOf('.');
        fileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_" +
            sc.VOSPRODLIST[product-1].substring(0,3).toLowerCase();
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
        logs.setDatabase("MARCLIM");
        logs.setDiscipline("weather");
        String temp = sc.VOSPRODLIST[product-1];
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
        boolean success = logs.put();

    } // updateLogUsers


} // ProductVOSFrame class



