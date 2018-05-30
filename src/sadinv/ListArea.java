package sadinv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.html.*;


/**
 * This class handles the search on areas
 *
 * SadInv
 * pscreen = areas
 * pinstitute = <institute code>
 * platitudenorth = <northern latitude boundary - default = -10>
 * platitudesouth = <southern latitude boundary - default = 70>
 * plongitudewest = <western longitude boundary - default = -30>
 * plongitudeeast = <eastern longitude boundary - default = 70>
 * psurveyid = <the survey_id to start with>
 *
 * @author 20090113 - SIT Group.
 * @version
 * 20090113 - Ursula von St Ange - create program                       <br>
 * 20090602 - Ursula von St Ange - chnge cruise name to survey name ub01<br>
 * 20091203 - Ursula von St Ange - add sessionCode, 'data available' and
 *                                 altern rows different colours (ub03) <br>
 * 20100201 - Ursula von St Ange - if dataRecorded = N,
 *                                 make available = N (ub04)            <br>
 * 20100205 - Ursula von St Ange - add a drop-down box for data-type(ub05)<br>
 * 20100507 - Ursula von St Ange - change for postgres            (ub06)<br>
 * 20111102 - Ursula von St Ange - add entry of years, count stations(ub07)<br>
 * 20111103 - Ursula von St Ange - save survey_id list on db       ub08 <br>
 * 20111114 - Ursula von St Ange - add code for user to extract hydro ub09<br>
 * 20130228 - Ursula von St Ange - sum watphy records from
                                   station_stats                   ub10 <br>
 */

public class ListArea extends CompoundItem  {

    //boolean dbg = true;
    boolean dbg = false;
    //boolean dbg2 = true;
    boolean dbg2 = false;

    // contains the method to parse URL-type arguments
    static common.edmCommon ec = new common.edmCommon();
    static SadInvCommon sc = new SadInvCommon();

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    static private Connection conn = null;
    static private java.sql.Statement stmt = null;
    static private ResultSet rset = null;

    static private int MAX_RECS = 15;
    static private int MAX_RECS2 = 1000;                                //ub08

    // +------------------------------------------------------------+
    // | Constructor                                                |
    // +------------------------------------------------------------+
    public ListArea (String args[]) { //throws SQLException {

        // +------------------------------------------------------------+
        // | Get the session code                                       |
        // +------------------------------------------------------------+
        String sessionCode  = ec.getArgument(args,sc.PSC);              //ub03
        String surveyId = ec.getArgument(args,sc.SURVEYID);
        String latitudeNorthIn = ec.getArgument(args,sc.LATITUDENORTH);
        String latitudeSouthIn = ec.getArgument(args,sc.LATITUDESOUTH);
        String longitudeWestIn = ec.getArgument(args,sc.LONGITUDEWEST);
        String longitudeEastIn = ec.getArgument(args,sc.LONGITUDEEAST);
        String hemNorth = ec.getArgument(args,sc.HEMNORTH);
        String hemSouth = ec.getArgument(args,sc.HEMSOUTH);
        String hemWest = ec.getArgument(args,sc.HEMWEST);
        String hemEast = ec.getArgument(args,sc.HEMEAST);
        String dataType = ec.getArgument(args,sc.DATATYPE);             //ub05
        String startDate = ec.getArgument(args,sc.STARTDATE);           //ub07
        String endDate = ec.getArgument(args,sc.ENDDATE);               //ub07
        if (dbg2) {
            surveyId = "";
            sessionCode = "2501";
            latitudeNorthIn = "0"; //"29";
            latitudeSouthIn = "10"; //"35";
            longitudeWestIn = "9"; //"29";
            longitudeEastIn = "1"; //"35";
            hemNorth = "S";
            hemSouth = "S";
            hemWest = "W"; //"E";
            hemEast = "E";
            startDate = "1987"; //"2000";
            endDate = "1987"; //"1992"; //"2002";
            dataType = "-1"; //"1";
        } // if (dbg2)
        if (dbg) System.out.println("<br>dataType = " + dataType);
        String latitudeNorth = latitudeNorthIn;
        String latitudeSouth = latitudeSouthIn;
        String longitudeWest = longitudeWestIn;
        String longitudeEast = longitudeEastIn;

        // +------------------------------------------------------------+
        // | Load the Oracle JDBC driver and connect to the dabase      |
        // +------------------------------------------------------------+
        String sql = "";
        String where1 = "";
        String where2 = "";

        conn = sc.getConnected(thisClass, sessionCode);
        /*
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName(sc.DRIVER);                                   //ub06
            //conn = DriverManager.getConnection
            //    ("jdbc:oracle:thin:" + sc.USER + "/" + sc.PWD +
            //     sc.CONNECTION);          // steamer
            //     //"@morph.csir.co.za:1522:etek8");
            conn = DriverManager.getConnection(                         //ub06
                sc.CONNECTION, sc.USER,  sc.PWD);                       //ub06
            conn.setAutoCommit(false);
        } catch (Exception e) {
            return;
        } // try-catch
        */

        // was it a valid request
        boolean validRequest = false;
        if (!"".equals(latitudeNorth) && !"".equals(latitudeSouth) &&
            !"".equals(longitudeWest) && !"".equals(longitudeEast) &&
            !"".equals(startDate) && !"".equals(endDate) )
                validRequest = true;

        // +------------------------------------------------------------+
        // | Enter input parameters                                     |
        // +------------------------------------------------------------+
        if (!validRequest) {

            // +------------------------------------------------------------+
            // | create drop-down box for data types, start and end year    |
            // +------------------------------------------------------------+
            DynamicTable selTable = new DynamicTable(2);
            selTable.setBorder(0).setCellPadding(0).setCellSpacing(0);
            selTable.setFrame(ITableFrame.BOX).setRules(ITableRules.NONE);
            dateRangeInput(selTable, 12, startDate, endDate, "");
            selTable.addRow(ec.cr2ColRow("Select data type:",createDataTypeSelect()));
            if (dbg) System.out.println("after createDataTypeSelect");

            DynamicTable table = new DynamicTable(1);
            table.setBorder(0).setCellPadding(0).setCellSpacing(0);
            table.setFrame(ITableFrame.BOX).setRules(ITableRules.NONE);
            //table.addRow(ec.latlonRangeInput("", 6));
            table.addRow(ec.latlonRangeInput("", 6,
                latitudeNorth, latitudeSouth, longitudeWest, longitudeEast,
                hemNorth, hemSouth, hemWest, hemEast, "N", "S", "W", "E",
                "", "", "", "", ""));
            table.addRow(ec.cr1ColRow("<br><center>" + selTable.toHTML() + "</center>" ));

            Form frm = new Form("GET", sc.APP);
            frm
                .addItem(table.setCenter())
                .addItem(new Hidden(sc.SCREEN, sc.AREAS))
                .addItem(new Hidden(sc.PSC, sessionCode))                   //ub03
                .addItem("<br>")
                .addItem(new Submit("Submit", "Go!").setCenter())
                ;

            this.addItem(frm);
            if (dbg) System.out.println("after adding form");
        } // if (!validRequest)

        // +------------------------------------------------------------+
        // | Get a listing of the surveys if there is an area input     |
        // +------------------------------------------------------------+
        if (validRequest) {

            // +------------------------------------------------------------+
            // | Correct input paramaters                                   |
            // +------------------------------------------------------------+
            //String latitudeNorth = ec.getArgument(args,sc.LATITUDENORTH);
            //String latitudeSouth = ec.getArgument(args,sc.LATITUDESOUTH);
            //String longitudeWest = ec.getArgument(args,sc.LONGITUDEWEST);
            //String longitudeEast = ec.getArgument(args,sc.LONGITUDEEAST);
            //String surveyId = ec.getArgument(args,sc.SURVEYID);
            if ("N".equals(hemNorth)) latitudeNorth = "-" + latitudeNorth;
            if ("N".equals(hemSouth)) latitudeSouth = "-" + latitudeSouth;
            if ("W".equals(hemWest)) longitudeWest = "-" + longitudeWest;
            if ("W".equals(hemEast)) longitudeEast = "-" + longitudeEast;
            if ("".equals(surveyId)) surveyId = " ";
            String startDate2 = startDate + "-01-01 00:00:00";              //ub07
            String endDate2 = endDate + "-12-31 23:59:59";                  //ub07
            //if ("".equals(latitudeNorth)) latitudeNorth = "-10";
            //if ("".equals(latitudeSouth)) latitudeSouth = "70";
            //if ("".equals(longitudeWest)) longitudeWest = "-30";
            //if ("".equals(longitudeEast)) longitudeEast = "70";

            // +------------------------------------------------------------+
            // | Build the empty arrays for holding survey  data            |
            // +------------------------------------------------------------+
            String[] surveyIds       = new String[MAX_RECS2];           //ub08
            String[] projectNames    = new String[MAX_RECS];
            String[] cruiseNames     = new String[MAX_RECS];
            String[] planamNames     = new String[MAX_RECS];
            String[] surnames        = new String[MAX_RECS];
            String[] fNames          = new String[MAX_RECS];
            String[] titles          = new String[MAX_RECS];
            String[] institutes      = new String[MAX_RECS];
            String[] datesStart      = new String[MAX_RECS];
            String[] datesEnd        = new String[MAX_RECS];
            String[] surveyTypeNames = new String[MAX_RECS];
            String[] dataRecorded    = new String[MAX_RECS];
            String[] available       = new String[MAX_RECS];            //ub03
            String   numStations1    = "0";                             //ub07
            String   numStations2    = "0";                             //ub07
            String   numRecords1     = "0";                             //ub10
            String   totalStations   = "";                              //ub07
            String   datatypeName    = "";                              //ub07
            String   datatypeDesc    = "";                              //ub07
            StringBuffer allSurveys  = new StringBuffer("");            //ub08
            String   tmpSurveyId     = "";                              //ub08
            String   tmpAvailable    = "";                              //ub08
            String   tmpDataRecorded = "";                              //ub08
            boolean isOnline = false;                                   //ub09
            Select selOnline = new Select(sc.EXTRTYPE);

            // +------------------------------------------------------------+
            // | Count the stations                                         |
            // +------------------------------------------------------------+
            // from marine db
            //---------------
            if ("-1".equals(dataType) || "1".equals(dataType)) {        //ub05

                // first count the watphy records                       //ub10
                where1 =                                                //ub10
                    " where station.station_id = station_stats.station_id and " +
                    " latitude between " + latitudeNorth + " and " + latitudeSouth +
                    " and longitude between " + longitudeWest + " and " + longitudeEast +
                    " and date_start between '" + startDate2 + "' and '" + endDate2 + "'";//ub07
                sql =
                    "select sum(watphy_cnt) from station, station_stats " + where1;
                                                                        //ub10
                if (dbg) System.out.println("<br>sql = " + sql);        //ub10
                                                                        //ub10
                try {                                                   //ub10
                                                                        //ub10
                    stmt = conn.createStatement();                      //ub10
                    rset = stmt.executeQuery(sql);                      //ub10
                                                                        //ub10
                    while (rset.next()) {                               //ub10
                        numRecords1 = checkNull(rset.getString(1));     //ub10
                    } // while (rset.next())                            //ub10
                    if ("".equals(numRecords1.trim())) {
                        numRecords1 = "0";
                    } // if ("".equals(numRecords1.trim()))
                    if (dbg) System.out.println("numRecords1 = " +      //ub10
                        numRecords1);                                   //ub10
                                                                        //ub10
                    rset.close();                                       //ub10
                    stmt.close();                                       //ub10
                    rset = null;                                        //ub10
                    stmt = null;                                        //ub10
                                                                        //ub10
                } catch (Exception e) {                                 //ub10
                    System.out.println("<br>" + thisClass +             //ub10
                        ": getSurveys error1b: " +                      //ub10
                        e.getMessage());                                //ub10
                    System.out.println("<br>sql = " + sql);             //ub10
                    e.printStackTrace();                                //ub10
                } // try-catch                                          //ub10


                where1 =
                    " where latitude between " + latitudeNorth + " and " + latitudeSouth +
                    " and longitude between " + longitudeWest + " and " + longitudeEast +
                    " and date_start between '" + startDate2 + "' and '" + endDate2 + "'";//ub07
                sql =
                    "select count(*) from station " + where1;

                if (dbg) System.out.println("<br>sql = " + sql);

                try {

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    while (rset.next()) {
                        numStations1 = checkNull(rset.getString(1));
                    } // while (rset.next())
                    if (dbg) System.out.println("numStations1 = " + numStations1);

                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.out.println("<br>" + thisClass + ": getSurveys error1a: " +
                        e.getMessage());
                    System.out.println("<br>sql = " + sql);
                    e.printStackTrace();
                } // try-catch

            } // if ("-1".equals(dataType) || "1".equals(dataType))

            if (!"1".equals(dataType)) {

                // all others
                //------------

                String operator = ("-1".equals(dataType) ? " != 1" : " = " + dataType);
                where2 =
                    " where lat_north between " + latitudeNorth + " and " + latitudeSouth +
                    " and long_west between " + longitudeWest + " and " + longitudeEast +
                    " and (date_start between '" + startDate2 + "' and '" + endDate2 + "'" +//ub07
                    " or date_end between '" + startDate2 + "' and '" + endDate2 + "')" +   //ub07
                    " and survey_type_code" + operator;                                   //ub07
                sql =
                    "select count(*) from inventory " + where2;
                if (dbg) System.out.println("<br>sql = " + sql);

                try {

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    while (rset.next()) {
                        numStations2 = checkNull(rset.getString(1));
                    } // while (rset.next())
                    if (dbg) System.out.println("numStations2 = " + numStations2);

                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.out.println("<br>" + thisClass + ": getSurveys error1a: " +
                        e.getMessage());
                    System.out.println("<br>sql = " + sql);
                    e.printStackTrace();
                } // try-catch

            } // if ("1".equals(dataType))

            // total number of stations
//            totalStations = String.valueOf(
//                Integer.parseInt(numStations1) + Integer.parseInt(numStations2));
//            if (dbg) System.out.println("totalStations = " + totalStations);

            // hydro extractions online/offline
//            if (Integer.parseInt(numStations1) > sc.MAX_STATIONS) {
//                isOnline = false;
//            } else {
//                isOnline = true;
//            } // if (numStations1 > 0)


            // +------------------------------------------------------------+
            // | Get a list of all surveys                                  |
            // +------------------------------------------------------------+
            int numRecs = 0;

            // from marine db
            //---------------
            if ("-1".equals(dataType) || "1".equals(dataType)) {        //ub05

                sql = "select distinct survey_id from station " + where1 +
                    " and survey_id >= '" + surveyId + "'" +
                    " order by survey_id";
                if (dbg) System.out.println("<br>sql = " + sql);

                try {

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    int i = 0;
                    while (rset.next() && (i < (MAX_RECS2))) {          //ub08
                            surveyIds[i]      = checkNull(rset.getString(1));
                            if (dbg) System.out.println("<br>"+dataType+": +surveyIds[i]: " +
                                (i) + " " + surveyIds[i]);
                            i++;
                    } // while (rset.next() && (i < MAX_RECS))

                    numRecs = i;

                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.out.println("<br>" + thisClass + ": getSurveys error1a: " +
                        e.getMessage());
                    System.out.println("<br>sql = " + sql);
                    e.printStackTrace();
                } // try-catch

            } // if ("-1".equals(dataType) || "1".equals(dataType))

            if (!"1".equals(dataType)) {

                // all others
                //------------

                sql = "select survey_id from inventory " + where2 +
                    " and survey_id >= '" + surveyId + "'" +
                    " order by survey_id";
                if (dbg) System.out.println("<br>sql = " + sql);

                try {

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    int i = numRecs;
                    //while (rset.next() && (i < (numRecs+MAX_RECS))) {
                    while (rset.next() && (i < (MAX_RECS2))) {
                        surveyIds[i]      = checkNull(rset.getString(1));
                        if (dbg) System.out.println("<br>"+dataType+": +surveyIds[i]: " +
                            (i) + " " + surveyIds[i]);
                        i++;
                    } // while (rset.next() && (i < MAX_RECS))

                    numRecs = i;

                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.out.println("<br>" + thisClass + ": getSurveys error1a: " +
                        e.getMessage());
                    System.out.println("<br>sql = " + sql);
                    e.printStackTrace();
                } // try-catch


            } // if (!"1".equals(dataType))

/*
             sql += where +
                    " and survey_id >= '" + surveyId + "'" +
                    " order by survey_id";
            try {

                stmt = conn.createStatement();
                rset = stmt.executeQuery(sql);

                int i = 0;
                while (rset.next() && (i < MAX_RECS)) {
                    surveyIds[i]      = checkNull(rset.getString(1));
                    if (dbg) System.out.println("<br>"+dataType+": +surveyIds[i]: " +
                        (i) + " " + surveyIds[i]);
                    i++;
                } // while (rset.next() && (i < MAX_RECS))

                numRecs = i;

                rset.close();
                stmt.close();
                rset = null;
                stmt = null;

            } catch (Exception e) {
                System.out.println("<br>" + thisClass + ": getSurveys error1a: " +
                    e.getMessage());
                System.out.println("<br>sql = " + sql);
                e.printStackTrace();
            } // try-catch
/*
            } else {
                sql =
                    "select survey_id from inventory " + where +
                    if (dbg) System.out.println("<br>sql = " + sql);

                try {

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    int i = 0;
                    while (rset.next() && (i < MAX_RECS)) {
                        surveyIds[i]      = checkNull(rset.getString(1));
                        if (dbg) System.out.println("<br>marine: surveyIds[i]: " +
                            (i) + " " + surveyIds[i]);
                        i++;
                    } // while (rset.next() && (i < MAX_RECS))

                    numRecs = i;

                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.out.println("<br>" + thisClass + ": getSurveys error1a: " +
                        e.getMessage());
                    System.out.println("<br>sql = " + sql);
                    e.printStackTrace();
                } // try-catch
            } else { // all others

            } // if ("-1".equals(dataType) || "1".equals(dataType))     //ub05
            if (dbg) System.out.println("after marine db");


            // from waves db
            //--------------
            if ("-1".equals(dataType) || "4".equals(dataType)) {        //ub05
                try {

                    sql =
                        "select distinct survey_id from wav_station " +
                        " where latitude between " + latitudeNorth + " and " + latitudeSouth +
                        " and longitude between " + longitudeWest + " and " + longitudeEast +
                        " and survey_id > '" + surveyId + "'" +
                        " order by survey_id";
                    if (dbg) System.out.println("<br>sql = " + sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    int i = numRecs;
                    while (rset.next() && (i < (numRecs+MAX_RECS))) {
                        surveyIds[i]      = checkNull(rset.getString(1));
                        if (dbg) System.out.println("<br>waves: surveyIds[i]: " +
                            (i) + " " + surveyIds[i]);
                        i++;
                    } // while (rset.next())

                    numRecs = i;
                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.out.println("<br>" + thisClass + ": getSurveys error1b: " +
                        e.getMessage());
                    System.out.println("<br>sql = " + sql);
                    e.printStackTrace();
                } // try-catch
            } // if ("-1".equals(dataType) || "4".equals(dataType))     //ub05
            if (dbg) System.out.println("after waves");


            // from weather db
            //----------------
            if ("-1".equals(dataType) || "3".equals(dataType)) {        //ub05
                try {

                    sql =
                        "select distinct survey_id from wet_station " +
                        " where latitude between " + latitudeNorth + " and " + latitudeSouth +
                        " and longitude between " + longitudeWest + " and " + longitudeEast +
                        " and survey_id > '" + surveyId + "'" +
                        " order by survey_id";
                    if (dbg) System.out.println("<br>sql = " + sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    int i = numRecs;
                    while (rset.next() && (i < (numRecs+MAX_RECS))) {
                        surveyIds[i]      = checkNull(rset.getString(1));
                        if (dbg) System.out.println("<br>weather: surveyIds[i]: " +
                            (i) + " " + surveyIds[i]);
                        i++;
                    } // while (rset.next())

                    numRecs = i;
                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.out.println("<br>" + thisClass + ": getSurveys error1b: " +
                        e.getMessage());
                    System.out.println("<br>sql = " + sql);
                    e.printStackTrace();
                } // try-catch
            } // if ("-1".equals(dataType) || "3".equals(dataType))     //ub05
            if (dbg) System.out.println("after weather");


            // from currents and UTR db
            //-------------------------
            if ("-1".equals(dataType) || "2".equals(dataType) || "6".equals(dataType)) { //ub05
                try {

                    sql =
                        "select cur_mooring.survey_id, survey_type_code " +
                        "from cur_mooring, inventory " +
                        " where latitude between " + latitudeNorth + " and " + latitudeSouth +
                        " and longitude between " + longitudeWest + " and " + longitudeEast +
                        " and cur_mooring.survey_id > '" + surveyId + "' " +
                        " and cur_mooring.survey_id = inventory.survey_id " +
                        "order by cur_mooring.survey_id";
                    if (dbg) System.out.println("<br>sql = " + sql);

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    int i = numRecs;
                    while (rset.next() && (i < (numRecs+MAX_RECS))) {
                        String surveyIdL = checkNull(rset.getString(1));
                        String surveyType = checkNull(rset.getString(2));
                        if ("-1".equals(dataType) || surveyType.equals(dataType)) {
                            surveyIds[i] = surveyIdL;
                            if (dbg) System.out.println("<br>currents: surveyIds[i]: " +
                                (i) + " " + surveyIds[i]);
                            i++;
                        } // if ("-1".equals(dataType))
                    } // while (rset.next())

                    numRecs = i;
                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (Exception e) {
                    System.out.println("<br>" + thisClass + ": getSurveys error1b: " +
                        e.getMessage());
                    System.out.println("<br>sql = " + sql);
                    e.printStackTrace();
                } // try-catch
            } // if ("-1".equals(dataType) || "2".equals(dataType) || "6".equals(dataType)) //ub05
            if (dbg) System.out.println("after currents");

            */


            // sort them before processing them
            surveyIds = bubbleSort(surveyIds, numRecs);
            if (dbg) System.out.println("after bubbleSort");

            // we only want MAX_RECS surveys
            int totalRecs = numRecs;                                     //ub08
            if (numRecs > MAX_RECS) { numRecs = MAX_RECS; }
            if (dbg) System.out.println("<br>No of records = " + numRecs);
            if (dbg) System.out.println("<br>No of totalRecs = " + totalRecs);

            // +------------------------------------------------------------+
            // | Get the survey details                                     |
            // +------------------------------------------------------------+
            try {

                // only do if there were records - have to put this in else the
                // rset.close() bombs out
                if (numRecs > 0) {
                    for (int i = 0; i < totalRecs; i++) {

                        sql =
                            "select survey_id, project_name, cruise_name, " +
                            "planam.name, surname, f_name, title, institutes.name, " +
                            "to_char(date_start,'YYYY-MM-DD'), " +
                            "to_char(date_end,'YYYY-MM-DD'), data_recorded, " +
                            "survey_type.name, data_available " +           //ub03 "
                            "from inventory, planam, institutes, "+
                            " scientists, survey_type "+
                            "where survey_id = '" + surveyIds[i] + "' " +
                            "and planam.code = inventory.planam_code " +
                            "and institutes.code = inventory.instit_code " +
                            "and scientists.code = inventory.sci_code_1 " +
                            "and survey_type.code = inventory.survey_type_code";
                        if (dbg) System.out.println("<br>sql = "+ sql);

                        stmt = conn.createStatement();
                        rset = stmt.executeQuery(sql);

                        while (rset.next()) {                                     //ub08
                            if (i < MAX_RECS) {                                   //ub08
                                surveyIds[i]       = checkNull(rset.getString(1));
                                projectNames[i]    = checkNull(rset.getString(2));
                                cruiseNames[i]     = checkNull(rset.getString(3));
                                planamNames[i]     = checkNull(rset.getString(4));
                                surnames[i]        = checkNull(rset.getString(5));
                                fNames[i]          = checkNull(rset.getString(6));
                                titles[i]          = checkNull(rset.getString(7));
                                institutes[i]      = checkNull(rset.getString(8));
                                datesStart[i]      = checkNull(rset.getString(9));
                                datesEnd[i]        = checkNull(rset.getString(10));
                                dataRecorded[i]    = checkNull(rset.getString(11)).toUpperCase();
                                surveyTypeNames[i] = checkNull(rset.getString(12));
                                available[i]       = checkNull(rset.getString(13)).toUpperCase();//ub03
                                if ("N".equals(dataRecorded[i])) available[i] = "N";     //ub04
                                allSurveys.append(surveyIds[i] + " " + available[i] + ",");//ub08
                            } else {                                                          //ub08
                                tmpSurveyId     = checkNull(rset.getString(1));               //ub08
                                tmpDataRecorded = checkNull(rset.getString(11)).toUpperCase();//ub08
                                tmpAvailable    = checkNull(rset.getString(13)).toUpperCase();//ub08
                                if ("N".equals(tmpDataRecorded)) tmpAvailable = "N";          //ub08
                                allSurveys.append(tmpSurveyId + " " + tmpAvailable + ",");    //ub08
                            } // if (i < MAX_RECS)                                            //ub08

                        } // while (rset.next())

                    } // for (int i = 0; i < totalRecs; i++)

                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } // if (numRecs > 0)

            } catch (Exception e) {
                System.out.println("<br>" + thisClass + ": getSurveys error: " +
                    e.getMessage());
                System.out.println("<br>sql = " + sql);
                e.printStackTrace();
            } // try-catch
            if (dbg) System.out.println("after survey details");

            // +------------------------------------------------------------+
            // | Save survey list on the db                                 |
            // +------------------------------------------------------------+
            if (dbg) System.out.println("allSurveysLength = " +             //ub08
                allSurveys.toString().length());                            //ub08
            sc.storeSurveyList(conn, sessionCode, allSurveys.toString());   //ub08


            // +------------------------------------------------------------+
            // | Get the datatype details                                  |
            // +------------------------------------------------------------+
            if ("-1".equals(dataType)) {
                datatypeDesc = "All data types";
            } else {
                try {

                    sql =
                        " select description "+
                        " from survey_type "+
                        " where code = "+dataType;

                    stmt = conn.createStatement();
                    rset = stmt.executeQuery(sql);

                    if (dbg) System.out.println("<br> **sql "+sql);

                    while (rset.next()) {
                        datatypeDesc  = checkNull(rset.getString(1));
                        if (dbg) System.out.println("<br> **datatypeDesc "+datatypeDesc);

                    } //while (rset.next()) {

                    rset.close();
                    stmt.close();
                    rset = null;
                    stmt = null;

                } catch (SQLException e) {
                    System.err.println("<br> "+e.getMessage());
                        e.printStackTrace();
                } // try-catch
            } // if ("-1".equals(dataType))

            String extractFormStr = "";
            // create the extract forms and add to the page
            if (!"".equals(sessionCode) &&
                    ("-1".equals(dataType) || "1".equals(dataType))) {        //ub05

                selOnline.addOption(new Option(sc.HYDRO_EXTR[0],"0",false));
                selOnline.addOption(new Option(sc.HYDRO_EXTR[1],"1",false));
                //addSelectOption(0);
                //addSelectOption(1);

                Form extractForm = new Form("GET", sc.APP);
                extractForm
                    .addItem(new Hidden(sc.SCREEN, sc.EXTRACT))
                    .addItem(new Hidden(sc.LATITUDENORTH,latitudeNorthIn) )
                    .addItem(new Hidden(sc.LATITUDESOUTH,latitudeSouthIn) )
                    .addItem(new Hidden(sc.LONGITUDEWEST,longitudeWestIn) )
                    .addItem(new Hidden(sc.LONGITUDEEAST,longitudeEastIn) )
                    .addItem(new Hidden(sc.HEMNORTH,hemNorth) )
                    .addItem(new Hidden(sc.HEMSOUTH,hemSouth) )
                    .addItem(new Hidden(sc.HEMWEST,hemWest) )
                    .addItem(new Hidden(sc.HEMEAST,hemEast) )
                    .addItem(new Hidden(sc.DATATYPE,"-1") )         //ub05
                    .addItem(new Hidden(sc.DATANAME,"Hydro") )
                    //.addItem(new Hidden(sc.SURVEYID,"Hydro") )
                    .addItem(new Hidden(sc.STARTDATE,startDate) )       //ub07
                    .addItem(new Hidden(sc.ENDDATE,endDate) )           //ub07
                    .addItem(new Hidden(sc.PSC, sessionCode))
                    .addItem(selOnline)
                    .addItem(SimpleItem.LineBreak);
//                if (Integer.parseInt(numStations1) <= sc.MAX_STATIONS) {//ub10
                if (Integer.parseInt(numRecords1) <= sc.MAX_HYDRO) {      //ub10
                    extractForm.addItem(new Submit(sc.ACTION, sc.ONLINE));
                } else {
                    extractForm.addItem(new Submit(sc.ACTION, sc.OFFLINE));
                } // if (Integer.parseInt(numStations1) <= sc.MAX_STATIONS)
                extractFormStr = extractForm.toHTML();

            } // if (!"".equals(sessionCode))

            // +------------------------------------------------------------+
            // | Create the main listing table                              |
            // +------------------------------------------------------------+
            DynamicTable header_tab = new DynamicTable(1);
            header_tab
                .setBorder(1)
                .setCellSpacing(1)
                .setCellPadding(5)
                .setBackgroundColor(new Color("FFFF99"))
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL);

            // +------------------------------------------------------------+
            // | Create the headings                                        |
            // +------------------------------------------------------------+
            TableRow rows = new TableRow();
            StringBuffer searchDetail = new StringBuffer("");
                searchDetail.append(
                "<b>Data search details:</b>" +
                "<br>\nLatitude Range: " + latitudeNorthIn + sc.DEG + hemNorth +
                    " to " + latitudeSouthIn + sc.DEG + hemSouth +
                "<br>\nLongitude Range: " + longitudeWestIn + sc.DEG + hemWest +
                    " to " + longitudeEastIn + sc.DEG + hemEast +
                "<br>\nDate range: " + startDate + " to " + endDate +
                "<br>\nData Type: " + datatypeDesc);
                if ("-1".equals(dataType) || "1".equals(dataType)) {
                    searchDetail.append(
                        "<br>\nNumber of hydrographic stations: " + numStations1);
                    searchDetail.append(                                        //ub10
                        "<br>\nNumber of water column records: " + numRecords1);//ub10
                } // if (("-1".equals(dataType) || "1".equals(dataType))
                if (!"1".equals(dataType)) {
                    searchDetail.append(
                        "<br>\nNumber of time-series stations: " + numStations2);
                } // if (!"1".equals(dataType))
            rows.addCell(new TableDataCell(searchDetail.toString()));
            if (!"".equals(sessionCode) &&
                    ("-1".equals(dataType) || "1".equals(dataType))) {        //ub05
                rows.addCell(new TableDataCell(
                "<b>Data extraction details:</b>" +
                extractFormStr +
                "<b>PLease note:</b>" +
                "<br>\nOnly the following data will be extracted:" +    //ub10
                "<br>\n &nbsp;&nbsp;<i>Water column</i> data with" +    //ub10                    //ub10
                "<br>\n &nbsp;&nbsp;<i>Survey Type = Hydro</i> and <i>Available = Y</i>"
                ));
            } // if (!"".equals(sessionCode))
            header_tab.addRow(rows);

            this.addItem(header_tab .setCenter());

            // +------------------------------------------------------------+
            // | Prompt user to click on survey ID to select                |
            // +------------------------------------------------------------+
            this
                //.addItem(SimpleItem.Break)
                .addItem(new SimpleItem("Click on a survey ID for more information."))
                ;

            // +------------------------------------------------------------+
            // | Create the main listing table                              |
            // +------------------------------------------------------------+
            DynamicTable tab = new DynamicTable(9);
            tab.setBorder(1)
                .setCellSpacing(1)
                .setBackgroundColor(new Color("FFFF99"))
                .setFrame(ITableFrame.BOX)
                .setRules(ITableRules.ALL)
                ;

            // +------------------------------------------------------------+
            // | Create the headings                                        |
            // +------------------------------------------------------------+
            rows = new TableRow();
            rows
                .addCell(new TableHeaderCell(formatStr("Survey ID")))
                .addCell(new TableHeaderCell(formatStr("Project Name")))
                .addCell(new TableHeaderCell(formatStr("Survey/Station Name")))     // ub01
                .addCell(new TableHeaderCell(formatStr("Platform Name")))
                .addCell(new TableHeaderCell(formatStr("Chief Scientist")))
                .addCell(new TableHeaderCell(formatStr("Institute")))
                .addCell(new TableHeaderCell(formatStr("Start")))
                .addCell(new TableHeaderCell(formatStr("End")))
                .addCell(new TableHeaderCell(formatStr("Survey Type")))
                .addCell(new TableHeaderCell(formatStr("Stored?")))         //ub03
                .addCell(new TableHeaderCell(formatStr("Available?")))      //ub03
                ;
            rows.setBackgroundColor(new Color("FFCC66"));
            tab.addRow(rows);

            boolean lightYellow = true;                                     //ub03
            for (int i = 0; i < numRecs; i++) {
                  if (dbg) System.out.println("<br>start: i = " + i);

                // +------------------------------------------------------------+
                // | Create the link to call this page again and pass the last  |
                // | survey ID to it, which will become the start survey ID in  |
                // | new page.                                                  |
                // +------------------------------------------------------------+
                if (dbg) System.out.println("<br>Before link");
                String link1 = new String(
                    sc.APP + "?" + sc.SCREEN + "=" + sc.DISPLAYSURVEY + "&" +
                    sc.SURVEYID + "=" + surveyIds[i] + "&" +
                    sc.PSC + "=" + sessionCode + "&" +                      //ub03
                    sc.AVAILABLE + "=" + available[i]);                     //ub03

                // +------------------------------------------------------------+
                // | Build a new row and add to the list table                  |
                // +------------------------------------------------------------+
                rows = new TableRow();
                rows.addCell(new TableDataCell(new Link(link1,new SimpleItem(surveyIds[i]).setFontSize(1))) );
                rows.addCell(new TableDataCell(formatStr(projectNames  [i])));
                rows.addCell(new TableDataCell(formatStr(cruiseNames   [i])));
                rows.addCell(new TableDataCell(formatStr(planamNames   [i])));
                rows.addCell(new TableDataCell(formatStr(titles        [i].trim()
                    + " " + fNames[i].trim() + " " +surnames[i])));
                rows.addCell(new TableDataCell(formatStr(institutes    [i])));
                rows.addCell(new TableDataCell(formatStr(datesStart    [i])));
                rows.addCell(new TableDataCell(formatStr(datesEnd      [i])));
                rows.addCell(new TableDataCell(formatStr(surveyTypeNames[i]))   //ub03
                    .setHAlign(IHAlign.CENTER));                                //ub03
                rows.addCell(new TableDataCell(formatStr(dataRecorded  [i]))    //ub03
                    .setHAlign(IHAlign.CENTER));                                //ub03
                rows.addCell(new TableDataCell(formatStr(available     [i]))    //ub03
                    .setHAlign(IHAlign.CENTER));                                //ub03

                lightYellow = ("N".equals(available[i]) ? false : true);        //ub03
                if (lightYellow) rows.setBackgroundColor(new Color("FFFFCC"));  //ub03

                tab.addRow(rows);

                if (dbg) System.out.println("<br>end: i = " + i);

            }  //  for (int i = 0; i < MAX_RECS; i++)

            this.addItem(tab .setCenter() );

            // +------------------------------------------------------------+
            // | If there are more surveys, create a button for the user to |
            // | click for the next stations on the list.                   |
            // +------------------------------------------------------------+
            if (numRecs == MAX_RECS) {
                Form moresurvs = new Form("GET", sc.APP);
                moresurvs
                    .addItem(new Hidden(sc.SCREEN, sc.AREAS))
                    .addItem(new Hidden(sc.PSC, sessionCode))           //ub03
                    .addItem(new Hidden(sc.LATITUDENORTH,latitudeNorthIn) )
                    .addItem(new Hidden(sc.LATITUDESOUTH,latitudeSouthIn) )
                    .addItem(new Hidden(sc.LONGITUDEWEST,longitudeWestIn) )
                    .addItem(new Hidden(sc.LONGITUDEEAST,longitudeEastIn) )
                    .addItem(new Hidden(sc.HEMNORTH,hemNorth) )
                    .addItem(new Hidden(sc.HEMSOUTH,hemSouth) )
                    .addItem(new Hidden(sc.HEMWEST,hemWest) )
                    .addItem(new Hidden(sc.HEMEAST,hemEast) )
                    .addItem(new Hidden(sc.SURVEYID,surveyIds[numRecs-1]) )
                    .addItem(new Hidden(sc.DATATYPE,dataType) )         //ub05
                    .addItem(new Hidden(sc.STARTDATE,startDate) )       //ub07
                    .addItem(new Hidden(sc.ENDDATE,endDate) )           //ub07
                    .addItem(new Submit("Submit", "More") )
                    ;
                this.addItem(moresurvs);

            } // if (numRecs == MAX_RECS)

        } // if (args.length > 1)


        // +------------------------------------------------------------+
        // | Close down the Oracle database session - with commit       |
        // +------------------------------------------------------------+
        sc.disConnect2(stmt, rset, conn);                               //ub08

    }  //  ListArea


    /**
     * Sort an array of String in Case order.
     * @param  list   Array to be sorted (String[])
     * @param  cnt    The number of items in the array (int)
     * @return        The sorted array
     */
    public String[] bubbleSort(String[] list, int cnt) {
        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < cnt-i-1; j++) {
                if (list[j].compareTo(list[j+1]) > 0) {
                    String temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                } // if (list[j].compareTo(list[j+1]) > 0)
            } // for j
        } // for i
        return list;
    } // bubbleSort



    /**
     * Add dropt-down box for data types
     */
    Select createDataTypeSelect() {
        // Create a statement
        String sql = "";
        int MAX_RECS = 25;

        Select selDataType = new Select(sc.DATATYPE);
        selDataType.addOption(new Option("All data types","-1",false));

        try {

            sql =
                " select code, description "+
                " from survey_type "+
                " order by upper(description)";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (dbg) System.out.println("<br> ***Test sql "+sql);
            while (rset.next()) {
                String codes    = checkNull(rset.getString(1));
                String descs    = checkNull(rset.getString(2));
                // don't unknown(0), echo-sounding(5) or VOS(7)
                if (!"0".equals(codes) && !"5".equals(codes)&& !"7".equals(codes)) {
                    selDataType.addOption(new Option(descs,codes,false));
                    if (dbg) System.out.println("<br>codes, descsc = " + codes + " " + descs);
                } // if (!"0".equals(codes) && !"5".equals(codes)) {
            } // while (rset.next()

            rset.close();
            stmt.close();
            rset = null;
            stmt = null;

        } catch (Exception e) {
            System.err.println("<br>Debug "+sql);
            System.err.println(e.getMessage());
            e.printStackTrace();
        } // try-catch

        return selDataType;

    } // createDataTypeSelect


    /**
     * return a table for the daterange or datetimerange input
     * @param table  the html table to which the rows should be added
     * @param length  the lenght of the input fields
     * @param dateStartDefault the default start date
     * @param dateEndDefault the default end date
     * @param  compulsory  a "*" if the field is a compulsory field
     * @return the html table with the rows added
     */
    public DynamicTable dateRangeInput(
            DynamicTable table,
            int length,
            String dateStartDefault,
            String dateEndDefault,
            String compulsory)   {

        if (dbg) System.out.println("<br>" + thisClass +
            " dateStartDefault = " + dateStartDefault);
        if (dbg) System.out.println("<br>" + thisClass +
            " dateEndDefault = " + dateEndDefault);

        // constants for date/time
        String dateStartPrompt = "Start Year (yyyy)";
        String dateEndPrompt   = "End Year (yyyy)";

        // start date
        TableRow row = new TableRow();
        row.addCell(new TableDataCell
            ("<b>" + dateStartPrompt + ":</b>").setHAlign(IHAlign.RIGHT));
        row.addCell(new TableDataCell(
            new TextField(sc.STARTDATE, length, length,
                dateStartDefault) + compulsory));
        table.addRow(row);
        // end date
        row = new TableRow();
        row.addCell(new TableDataCell
            ("<b>" + dateEndPrompt + ":</b>").setHAlign(IHAlign.RIGHT));
        row.addCell(new TableDataCell(
            new TextField(sc.ENDDATE, length, length,
                dateEndDefault) + compulsory));
        table.addRow(row);
        return table;
    } // dateRangeInput


    /**
     * Add options to sellOffline or selOnLIne, depending on the value of the count
     * @param   count   the record count
     * @param   index   the index to the string array HYDRO_EXTR
     */
//    void addSelectOption(int index) {
//        String ii = String.valueOf(index);
//        selOnline.addOption(new Option(sc.HYDRO_EXTR[index],ii,false));
//    } // addSelectOption



    /**
     * Format text strings
     */
    private static Container formatStr(String oldStr) {
        Container newStrc = new Container();
        newStrc
            .addItem(new SimpleItem(oldStr).setFontSize(1))
            ;
        return newStrc;

    } // formatStr


    /**
     * check if the value is null
     * returns  value / empty string
     */
    String checkNull(String value) {
        return (value != null ? value : "");
    } // checkNull


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("ListArea local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);

            bd.addItem(new ListArea(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            e.printStackTrace();
        } // try-catch

        // make sure everything is closed and null
        sc.disConnect(stmt, rset, conn);

    } // main

} // class ListArea
