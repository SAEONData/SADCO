package sadco;

import oracle.html.*;
import java.sql.Timestamp;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class calculate's the speed in knots between two points.
 * It's parameters are the following:
 *
 * SadcoMRN
 * screen.equals("admin")
 * version.equals("speeds")
 *
 * It's parameters are the following:
 * <br>@param pscreen   - used by SadcoMRN program. Value must be 'admin'.
 * <br>@param pversion  - used by SadcoMRN program. Value must be 'speeds'.
 * <br>@param surveyId  - argument value from url (string)
 * <br>@param stationId - argument value from url (string)
 *
 * @author 2002/02/13 - SIT Group.
 * @version
 * 20020213 - Mario August       - created class                            <br>
 * 20041106 - Ursula von St Ange - ub01: checked whether survey_id has any
 *                                 stations                                 <br>
 * 20070528 - Ursula von St Ange - ub02: add code for subdes's              <br>
 */
public class AdminCalcSpeeds extends CompoundItem {

    /** A boolean variable for debugging purposes */
    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommon ec = new common.edmCommon();
    sadco.SadConstants sc = new sadco.SadConstants();

    /** Arguments */
    String sessionCode = "";
    String surveyId = "";

    int totStations = 0;
    int errorCnt = 0;

    /** RandomAccessFile variable */
    RandomAccessFile outputFile;

    /** String variable for the directory path. */
    String pathName;

    /** Float variables */
    float speed = 0;

    Collection subdesColl = new ArrayList();            //ub02
    Iterator itSubdes = subdesColl.iterator();          //ub02
    String subdesArray[];                               //ub02

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public AdminCalcSpeeds(String args[]) {

        try {
            AdminCalcSpeedsActual(args);
        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } // try - catch

    } // AdminCalcSpeeds constructor

    /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void AdminCalcSpeedsActual(String args[]) {

        // get the correct PATH NAME - used while debugging
        if (dbg) System.out.println("<br>" + thisClass + ": host = " +
            ec.getHost());
        //if (ec.getHost().equals("morph")) {
        //    pathName = sc.MORPHL;
        //} else {
        //    pathName = sc.LOCAL;
        //} // if host

        if (ec.getHost().startsWith(sc.HOST)) {
            pathName = sc.HOSTDIRL;
        } else {
            pathName = sc.LOCALDIR;
        } // if host

        getArgParms(args);

        String fileName = surveyId.replace('/','_') +".spdRep";
        ec.deleteOldFile(pathName + fileName);
        try {
            outputFile = new RandomAccessFile(pathName + fileName,"rw");
            if (ec.getHost().startsWith(sc.HOST)) {
                //System.out.println("chmod 644 " + fileName);
                ec.submitJob("chmod 644 " + pathName + fileName);
            } //if (ec.getHost().equals(sc.HOST)) {

        } catch(Exception e) {
            ec.processError(e, this.getClass().getName(), "constructor", "");
        } //try-catch

        // Create main table
        DynamicTable mainTable = new DynamicTable(0);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        mainTable.setCenter();
        //mainTable.setFrame(ITableFrame.BOX);

        // get the stations
        MrnStation[] stations = getStations();

        // heading
        mainTable.addRow(ec.crSpanColsRow("Check for valid stations",2,true,
            "blue",IHAlign.CENTER,"+2" ));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTable.addRow(ec.cr2ColRow("Survey id :",surveyId));
        mainTable.addRow(ec.cr2ColRow("No of stations :",stations.length));

        // only continue if there are stations - maybe the wrong
        // survey-id was typed in.
        if (stations.length > 0) {                                      //ub01

            // sort stations according to date/time
            stations = bubbleSort(stations);

            // calculate the ship speeds between stations
            calcSpeeds(stations);

            // create link for download for file
            Link link = new Link (sc.LOADS_URL + fileName, fileName);

            // feedback to user
            mainTable.addRow(ec.cr2ColRow("No of suspect stations :",errorCnt));
            mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTable.addRow(ec.cr2ColRow("Detail report :",link.toHTML()));
            mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTable.addRow(ec.crSpanColsRow(ec.downloadFileInstructions().toHTML(),2));

        } // if (stations.length > 0)                                   //ub01

        addItem(mainTable.setCenter());

        try {
            outputFile.close();
        } catch(Exception e) {
            ec.processError(e, "AdminCalcSpeeds", "constructor", "");
        } //try-catch

    } // AdminCalcSpeedsActual


    /**
     * Get the parameters from the arguments in the URL
     * @param args
     * @return void
     */
    void getArgParms(String args[])   {
        for (int i = 0; i < args.length; i++) {
            if (dbg) System.out.println("<br>" + args[i]);
        } // for i
        surveyId = ec.getArgument(args, sc.SURVEYID);

    } // getArgParms

    /**
     * Get Watphy records for the stations array records
     * @return stations array value stations
     */
    MrnStation[] getStations() {

        MrnStation[] stations = getStationRecords(surveyId);
        if (dbg) System.out.println("<br>getStations: stations.length" +
            stations.length);
        subdesArray = new String[stations.length];          //ub02

        //for all stations
        for (int i = 0; i < stations.length;i++) {

            MrnWatphy[] watphyRecs2 = getWatphyRecords(stations[i].getStationId());
            if (watphyRecs2.length > 0) {
                stations[i].setDateStart(watphyRecs2[0].getSpldattim());
                stations[i].setDateEnd(watphyRecs2[watphyRecs2.length - 1].getSpldattim());
            } // if (watphyRecs2.length > 0)
            subdesColl.clear();                                     //ub02
            for (int j = 0; j < watphyRecs2.length; j++) {          //ub02
                String subd = watphyRecs2[j].getSubdes("");         //ub02
                if (!subdesColl.contains(subd)) subdesColl.add(subd);//ub02
            } // for (int j = 0; j < watphyRecs2.length; j++)       //ub02

            StringBuffer text = new StringBuffer("");               //ub02
            itSubdes = subdesColl.iterator();                       //ub02
            while (itSubdes.hasNext()) text.append(itSubdes.next()+";");//ub02
            if (dbg) System.out.println(text.toString());           //ub02
            subdesArray[i] = new String(text.toString());           //ub02

        } //for (int i = 1; i < stations.length;i++) {

        return stations;
    } // getStations


    /**
     * Get Station records from Database for the surveyId
     * @param surveyId : argument value from url (string)
     * @return array value stations
     */
    MrnStation[] getStationRecords(String surveyId) {
        if (dbg) System.out.println("<br>getStationRecords");
        // construct the table names
        String fields = MrnStation.STATION_ID +","+ MrnStation.LATITUDE +","+
            MrnStation.LONGITUDE +","+ MrnStation.DATE_START +","+ MrnStation.DATE_END;

        // construct the where clause
        String where = MrnStation.SURVEY_ID +"='"+ surveyId +"'";

        // construct the order by cluase
        String order = MrnStation.DATE_START;
        MrnStation[] stations = new MrnStation().get(fields, where, order);

        return stations;
    } //MrnStation[] getStationRecords


    /**
     * Get Watphy records from Database for the @param stationId
     * @param stationId : argument value from url (string)
     * @return array value watphyRecs
     */
    MrnWatphy[] getWatphyRecords(String stationId) {

        if (dbg) System.out.println("<br>getWatphyRecords");
        String fields = MrnWatphy.SPLDATTIM + "," + MrnWatphy.SUBDES;       //ub02
        String where = MrnWatphy.STATION_ID +"='"+ stationId +"'";
        String order = MrnWatphy.SPLDATTIM;

        MrnWatphy[] watphyRecs = new MrnWatphy().get(fields,where,order);

        return watphyRecs;

    } //MrnWatphy[] getWatphyRecords


    /**
     * Calculate the ship speeds between stations
     * @param stations array (MrnStation[])
     * @return void
     */
    void calcSpeeds(MrnStation[] stations) {

        String dt1 = stations[0].getDateStart("").substring(0,16);
        String dt2 = stations[0].getDateEnd("").substring(0,16);

        // make sure the frmFiller is set to a space
        //ec.setFrmFiller(" ");
        ec.writeFileLine(outputFile, ec.frm(stations[0].getStationId(),12) + ", " +
            ec.frm(stations[0].getLatitude(),10,5) + " S" + ", " +
            ec.frm(stations[0].getLongitude(),10,5) + " E" + ", " + dt1 + " " + dt2);
            ec.writeFileLine(outputFile, ec.frm(" ",20) + subdesArray[0]);  //ub02

        for (int i = 1; i < stations.length;i++) {

            // call function to calulate speed
            float speed = CalcSpeed(
                stations[i-1].getLatitude(),  stations[i].getLatitude(),
                stations[i-1].getLongitude(), stations[i].getLongitude(),
                stations[i-1].getDateEnd(),   stations[i].getDateStart());

            dt1 = stations[i].getDateStart("").substring(0,16);
            dt2 = stations[i].getDateEnd("").substring(0,16);

            String knots = " knots ";
            if ((speed > 20) || (speed < 0)) {
                knots = " knots                                                             ***";
            } // if (speed > 20)
            ec.writeFileLine(outputFile, ec.frm(speed,10,3) + knots);
            ec.writeFileLine(outputFile, ec.frm(stations[i].getStationId(),12) +", "+
                    ec.frm(stations[i].getLatitude(),10,5) +" S"+", "+
                    ec.frm(stations[i].getLongitude(),10,5) +" E"+ ", "+dt1 + " " + dt2);
            ec.writeFileLine(outputFile, ec.frm(" ",20) + subdesArray[i]);  //ub02
        } //for (int i = 1; i < stations.length;i++) {
    } //int getDataRecords(MrnStation[] stations) {


    /**
     * Method of sort: Bubble Sort
     * Function get Stations array and sort the stations array in date order.
     * @param stations : array value (string)
     * @return stations sorted by date array value (MrnStations[])
     */
    MrnStation[] bubbleSort(MrnStation[] stations) {

        for (int i=0; i < stations.length; i++) {
            for (int j=0; j < stations.length-i-1; j++) {

                if (dbg) System.out.println("<br>"+stations[j].getDateStart()+" "+
                    stations[j+1].getDateStart());

                if (stations[j].getDateStart().after(stations[j+1].getDateStart())) {
                    if (dbg) System.out.println("<br>swop "+j);

                    String stationID = stations[j].getStationId();
                    stations[j].setStationId(stations[j+1].getStationId());
                    stations[j+1].setStationId(stationID);

                    float latitude = stations[j].getLatitude();
                    stations[j].setLatitude(stations[j+1].getLatitude());
                    stations[j+1].setLatitude(latitude);

                    float longitude = stations[j].getLongitude();
                    stations[j].setLongitude(stations[j+1].getLongitude());
                    stations[j+1].setLongitude(longitude);

                    Timestamp dateStart = stations[j].getDateStart();
                    stations[j].setDateStart(stations[j+1].getDateStart());
                    stations[j+1].setDateStart(dateStart);

                    Timestamp dateEnd = stations[j].getDateEnd();
                    stations[j].setDateEnd(stations[j+1].getDateEnd());
                    stations[j+1].setDateEnd(dateEnd);

                }// if (stations[j]
            } //for (int j= 0; j <
        }//for (int i= 0; i <

        return stations;
    } //MrnStation[] bubbleSort() {

    /**
     * Function calculate the speed between two stations.
     * @param la1 : Latitude  of station one (double)
     * @param la2 : Latitude  of station two (double)
     * @param lo1 : Longitude of station one (double)
     * @param lo2 : Longitude of station two (double)
     * @param pt1 : Time value of point one (Timestamp)
     * @param pt2 : Time value of point two (Timestamp)
     * @return  speed (float) return speed(double) as float value (casting)
     */
    float CalcSpeed(double la1, double la2, double lo1, double lo2,
        Timestamp pt1, Timestamp pt2) {

        if (dbg) System.out.println("<br>CalcSpeed");
        if (dbg) System.out.println("<br>pt1 = "+pt1);
        java.util.Date date1 = pt1;
        if (dbg) System.out.println("<br>pt2 = "+pt2);
        java.util.Date date2 = pt2;

        long diff = pt2.getTime() - pt1.getTime();

        if (dbg) System.out.println("<br>difference before / 1000 / 60 / 60 = "+diff);
        //difference = difference / 1000.0 / 60.0 / 60.0;
        double difference = (double)diff / 3600000.0;
        //difference = difference / 3600000.0f;

        if (dbg) System.out.println("<br>difference = "+difference);
        double DEG2RAD = Math.PI / 180;
        double rLat1 = la1 * DEG2RAD;
        double rLat2 = la2 * DEG2RAD;
        double rLong1 = lo1 * DEG2RAD;
        double rLong2 = lo2 * DEG2RAD;

        //compute the angle
        double angle = rLong2 - rLong1;
        if (dbg) System.out.println("<br> Value of angle "+angle);

        // compute distance with formular
        // dr = acos { sin(lat1).sin(lat2)+cos(lat1).cos(lat2).cos(long2-long1)}
        double dr = Math.acos(Math.sin(rLat1) * Math.sin(rLat2) +
            Math.cos(rLat1) * Math.cos(rLat2) * Math.cos(angle));
        if (dbg) System.out.println("<br> Formula results "+dr);

        if (dr < 0 ) {
            dr += Math.PI;
            if (dbg) System.out.println("<br> ** Formula results if dr < 0 "+dr);

        } //if (dr < 0 ) {

        //double distance = StrictMath.toDegrees(dr * 6371.2);
        //double distance = dr * 6371.2;

        double distance = dr * 6371.2 / 1.852;
        double speed = distance / difference;

        // the speed may not exceed 20 knots ...
        if ( speed > 20) {
           errorCnt++;
        } //if ( speed > 20) {

        return (float)speed;

    } //double CalcSpeed


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            HtmlHead hd = new HtmlHead("AdminCalcSpeeds local");
            HtmlBody bd = new HtmlBody();
            HtmlPage hp = new HtmlPage(hd,bd);
            //PreAdminCalcSpeeds local= new PreAdminCalcSpeeds(args);
            bd.addItem(new AdminCalcSpeeds(args));
            hp.printHeader();
            hp.print();
        } catch(Exception e) {
            ec.processErrorStatic(e, "AdminCalcSpeeds", "Constructor", "");
            // close the connection to the database
            common2.DbAccessC.close();
        } // try-catch

    } // main


} // class AdminCalcSpeeds