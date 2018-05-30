package sadco;

//import oracle.html.*;
import java.io.*;
import java.sql.*;

/**
 * This class loads the marine CTD data into the database
 *
 * SadcoMRNApps
 * screen.equals("load")
 * version.equals("doload")
 * loadType.equals("water")
 *
 * @author 030309 - SIT Group
 * @version
 * 030309 - Ursula von St Ange - create class                               <br>
 * 031002 - Ursula von St Ange - I'm not checking whether the records were in
 *                               the right order (fatalCount).
 *                               I'm assuming that this would have been
 *                               caught out in PreLoadMRNFrame              <br>
 * 040610 - Ursula von St Ange - change name from LoadMRNData to
 *                               LoadMRNWatData                             <br>
 */
public class LoadMRNWatData {

    /** A general purpose class used to extract the url-type parameters*/
    static common.edmCommonPC ec = new common.edmCommonPC();
    SadConstants sc = new SadConstants();
    //LoadMRNWatCommon lc = new LoadMRNWatCommon();
    LoadMRNCommon lc = new LoadMRNCommon();

    boolean dbg = false;
    //boolean dbg = true;
    boolean dbg3 = false;
    //boolean dbg3 = true;

    // arguments
    String fileName = "";
    String loadType = "";

    // denotes a new station in
    boolean newStationInFile = true;

    /**
     *
     */
    public LoadMRNWatData(String args[]) {

        // get the argument values
        getArgParms(args);

        // get the corect path
        String rootPath = "";
        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIRL;
        } else {
            rootPath = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(ec.HOST))
        String fileName2 = rootPath + "marine/" + fileName;

        // get the arguments from the work file
        getArgsFromFile(fileName2);
        if (dbg) System.out.println("constructor: loadType = " + loadType);

        // set the data type
        lc.dataType = lc.WATER;
        if ("WATERWOD".equals(loadType)) {
            lc.dataType = lc.WATERWOD;
        } // if ("WATERWOD".equals(loadType))
         if (dbg) System.out.println("constructor: lc.dataType = " + lc.dataType);


        // delete the old the dummy file
        String dummyFile = fileName2 + ".finished";
        ec.deleteOldFile(dummyFile);

        // create the 'processing' file
        String dummyFile2 = fileName2 + ".processing";
        ec.createNewFile(dummyFile2);

        // open input files
        lc.inputFile = lc.openInputFile(fileName2);

        // open output files
        lc.debugFile = lc.openOutputFile(fileName2 + ".debug");
        lc.reportFile = lc.openOutputFile(fileName2 + ".load_report");


        // get the maximum watphy_code
        lc.dataCodeEnd = lc.watphy.getMaxCode();
        lc.dataCodeStart = lc.dataCodeEnd + 1;
        if (dbg3) System.out.println("constructor: lc.dataCodeEnd = " + lc.dataCodeEnd);
        if (dbg3) System.out.println("constructor: lc.dataCodeStart = " + lc.dataCodeStart);


        // loop through file
        int lineCount  = 0;
        while (!ec.eof(lc.inputFile)) {

            String line = ec.getNextValidLine(lc.inputFile);
            if (dbg) System.out.println("LoadMRNWatData.constructor: line = " + line);
            // this is for when there are extra empty lines at the end of the data file
            if ("".equals(line)) {
                lc.code = 99;
            } else {
                // make sure that I don't get StringIndex out of bounds error
                if (line.length() < 100)
                    line += ec.frm(" ",100-line.length());
                lc.lineCount++;
                lc.code = lc.toInteger(line.substring(0,2));
                if (dbg) System.out.println("LoadMRNWatData.constructor: lc.code = " + lc.code);
            } // if (ec.eof(lc.inputFile))

            switch (lc.code) {
                case 1:  // survey data
                        lc.format01(line, lc.lineCount);
                        break;

                case 2:  // survey note
                        lc.checkSurveyExists();
                        lc.format02(line, lc.lineCount);
                        break;

                case 3:  // station data
                        // load last watphy, watnut, watchem1
                        //   and/or watcurrents record(s) of previous station
                        //if (!"".equals(lc.watphy.getStationId(""))) {
                        lc.loadData();
                        //} // if (!lc.watphy.isNullRecord())
                        // update previous station's maximum sample depth
                        lc.updateStation();
                        // print previous station's report
                        lc.outputReport(lc.reportFile, false);
                        lc.station = new MrnStation();
                        lc.resetStationStatistics();
//                        lc.watphy = new MrnWatphy();
//                        lc.stationId = "";
                        lc.format03(line, lc.lineCount);
//                        lc.loadStation();
                        newStationInFile = true;
                        break;

                case 4:  // weather data
                        int subCode = lc.format04(line, lc.lineCount);
                        if (subCode == 1) {
                            // station Id must match that of previous station record
                            //lc.checkStationId(lc.lineCount, lc.stationId04);
                            // check if the station unique index (UI) is already in use
                            lc.checkStationUIExists();
                            // is the station Id already in use or does the station exist ?
                            // if yes - update if requested to do so
                            lc.checkStationExists();
                        } // if (subCode == 1)
//                        lc.loadStation();
//                        lc.loadWeather();
                        break;

                case 5:  // watphy, watcurrents data
                        // first load
                        if (newStationInFile) {
                            lc.loadStation();
                            lc.loadWeather();
                            newStationInFile = false;
                        } // if (newStationInFile)
                        // load the previous watphy, watnut, watchem1
                        //   and/or watcurrents record(s)
                        //if (!lc.watphy.isNullRecord()) {
                        //if (!"".equals(lc.watphy.getStationId(""))) {
                            lc.loadData();
                        //} // if (!lc.watphy.isNullRecord())
                        lc.format05(line, lc.lineCount);
                        break;

                case 6:  // watnut, watchl data
                        lc.format06(line, lc.lineCount);
                        break;

                case 7: // watchem1 data
                        lc.format07(line, lc.lineCount);
                        break;

                case 8: // watchem1 data
                        lc.format08(line, lc.lineCount);
                        break;

                case 9: // watchem2 data
                        lc.format09(line, lc.lineCount);
                        break;

                case 10: // watchem2 data
                        lc.format10(line, lc.lineCount);
                        break;

                case 11: // watpol1 data
                        lc.format11(line, lc.lineCount);
                        break;

                case 12: // watpol1 data
                        lc.format12(line, lc.lineCount);
                        break;

                case 13: // watpol2 data
                        lc.format13(line, lc.lineCount);
                        break;

                case 99: // eof reached
                        break;

                default: // all other codes
                        lc.outputError(lc.lineCount + " - Fatal - " +
                            "Incorrect lc.code: " + lc.code);
                        break;

            } // end switch

            lc.prevCode = lc.code;
            //line = ec.getNextValidLine(lc.inputFile);

        } // while (!ec.eof(file))

        // load the last data record
        lc.loadData();

        // update last station's maximum sample depth
        lc.updateStation();
        // report on last station details
        lc.outputReport(lc.reportFile, true);

        // load the last data record
        //lc.loadData();   // moved up to before updateStation
        // update with min, max, count values
        lc.updateInventory();
        lc.updateLoadLog();

        if (dbg) System.out.println("constructor: lc.dateMin, lc.dateMax = " +
            lc.dateMin + " " + lc.dateMax);
        if (dbg) System.out.println("constructor: lc.latitudeMin, lc.latitudeMax = " +
            lc.latitudeMin + " " + lc.latitudeMax);
        if (dbg) System.out.println("constructor: lc.longitudeMin, lc.longitudeMax = " +
            lc.longitudeMin + " " + lc.longitudeMax);
        if (dbg) System.out.println("constructor: lc.newStationCount = " + lc.newStationCount);



        // close the files
        lc.closeFiles();

        // delete .processing, create the .finished
        ec.deleteOldFile(dummyFile2);
        ec.createNewFile(dummyFile);

        common2.DbAccessC.close();

    } // constructor


    /**
     * Get the parameters from the arguments in the URL
     * @param  args       (String)
     */
    void getArgParms(String args[])   {

        fileName   = ec.getArgument(args,sc.FILENAME);

    }   //  getArgParms


    /**
     * Get the parameters from the arguments in the URL
     * @param  fileName2       (String)
     */
    void getArgsFromFile(String fileName2)   {

        RandomAccessFile workFile = lc.openInputFile(fileName2 + ".work");
        String line = ec.getNextValidLine(workFile);
        int numberOfLines = Integer.parseInt(line.trim());
        String args[] = new String[numberOfLines];
        for (int i = 0; i < numberOfLines; i++) {
            args[i] = ec.getNextValidLine(workFile);
            if (dbg) System.out.println("getArgsFromFile: args[i] = " + i + " " + args[i]);
        } // for (int i = 0; i < numberOfLines; i++)


        // get values for loading
        lc.loadFlag = true;
        lc.updateStationsList = ec.getArguments(args, sc.UPDATESTATION);
        lc.replaceDataList    = ec.getArguments(args, sc.REPLACEDATA);
        lc.passkey            = ec.getArgument(args, sc.PASSKEY);
        lc.timeZone           = ec.getArgument(args,sc.TIMEZONE).toLowerCase();
        loadType              = ec.getArgument(args,sc.LOADTYPE).toUpperCase();
        if (dbg) System.out.println("getArgsFromFile: loadType = " + loadType);


        String check = ec.getArgument(args, sc.INVENTORYEXIST).toLowerCase();
        if ("y".equals(check)) {
            lc.inventoryExists = true;
        } // if (!"Y".equals(check))
        if (dbg3) System.out.println("getArgsFromFile: check = " +
            check + ", " + sc.INVENTORYEXIST);
        if (dbg3) System.out.println("getArgsFromFile: lc.inventoryExists = " +
            lc.inventoryExists);

        // get inventory details
        check = ec.getArgument(args, sc.PROJECTNAME);
        if (!"".equals(check)) {
            lc.inventory.setProjectName(check);
        } // if (!"".equals(check))

        check = ec.getArgument(args, sc.CRUISENAME);
        if (!"".equals(check)) {
            lc.inventory.setCruiseName(check);
        } // if (!"".equals(check))

        check = ec.getArgument(args, sc.AREANAME);
        if (!"".equals(check)) {
            lc.inventory.setAreaname(check);
        } // if (!"".equals(check))

        check = ec.getArgument(args, sc.DOMAIN);
        if (!"".equals(check)) {
            lc.inventory.setDomain(check);
        } // if (!"".equals(check))

        check = ec.getArgument(args, sc.COUNTRYCODE);
        if (!"".equals(check)) {
            lc.inventory.setCountryCode(check);
        } // if (!"".equals(check))

        check = ec.getArgument(args, sc.SCIENTCODE);            // ub03
        if (!"".equals(check)) {                                // ub03
            lc.inventory.setSciCode1(check);                    // ub03
        } // if (!"".equals(check))                             // ub03

        check = ec.getArgument(args, sc.INSTITCODE);
        if (!"".equals(check)) {
            lc.inventory.setInstitCode(check);
        } // if (!"".equals(check))

        check = ec.getArgument(args, sc.PLANAMCODE);
        if (!"".equals(check)) {
            lc.inventory.setPlanamCode(check);
        } // if (!"".equals(check))

        // get loadlog details
        //--------------------
        check = ec.getArgument(args, sc.LOADID);
        if (!"".equals(check)) {
            lc.logMrnLoads.setLoadid(check);
        } // if (!"".equals(check))

        check = ec.getArgument(args, sc.SUBMITSC);
        if (!"".equals(check)) {
            lc.logMrnLoads.setSource(check);
        } // if (!"".equals(check))

        check = ec.getArgument(args, sc.RESPPERSON);
        if (!"".equals(check)) {
            lc.logMrnLoads.setRespPerson(check);
        } // if (!"".equals(check))

        check = ec.getArgument(args, sc.INSTITUTE);
        if (!"".equals(check)) {
            lc.logMrnLoads.setInstitute(check);
        } // if (!"".equals(check))

        try {
            workFile.close();
        } catch(Exception e) { } //

    }   //  getArgsFromFile


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            LoadMRNWatData local= new LoadMRNWatData(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, "LoadMRNFrame", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class LoadMRNWatData
