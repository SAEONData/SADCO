package sadco;

import java.io.*;
import java.text.*;
import java.util.*;


/**
 * This class changes the subdes for all the watphy records for a requested
 * survey to a user-specified value.
 * It's parameters are the following:
 *
 * SadcoMRN
 * screen.equals("admin")
 * version.equals("subdes")
 *
 * It's parameters are the following:                                   <br>
 * pscreen   - used by SadcoMRN program. Value must be 'admin'.  <br>
 * pversion  - used by SadcoMRN program. Value must be 'subdes'. <br>
 * surveyId  - argument value from url (string)                  <br>
 * subdes    - argument value from url (string)                  <br>
 *
 * @author 2004/02/11 - SIT Group.
 * @version
 * 2004/02/11 - Ursula von St Ange - created class<br>
 */
public class AdminCheckDupStations {

    /** A boolean variable for debugging purposes */
    boolean dbg = false;
    //boolean dbg = true;

    /** The class name for error reporting purposes */
    String thisClass = this.getClass().getName();

    /** A general purpose class used to extract the url-type parameters*/
    /** must be static for the call in main */
    static common.edmCommonPC ec = new common.edmCommonPC();
    sadco.SadConstants sc = new sadco.SadConstants();

    /**
     * Calls the method that does the actual work within a
     * try-catch clause, so that Exceptions can be caught.
     * @param args the string array of url-type name-value parameter pairs
     */
    public AdminCheckDupStations(String args[]) {

        try {
            AdminCheckDupStationsActual(args);
        } catch(Exception e) {
            ec.processError(e, thisClass, "constructor", "");
        } // try - catch

    } // AdminCheckDupStations constructor

    /**
     * Creates a page with a banner and an entry form
     * @param args the string array of url-type name-value parameter pairs
     */
    void AdminCheckDupStationsActual(String args[]) {

        // get the argument
        String surveyId = ec.getArgument(args, sc.SURVEYID);

        // get the corect path
        String rootPath = "";
        if (ec.getHost().startsWith(sc.HOST)) {
            rootPath = sc.HOSTDIRL;
        } else {
            rootPath = sc.LOCALDIR;
        } // if (ec.getHost().startsWith(ec.HOST))

        String fileName = rootPath + "marine/" + surveyId.replace('/','_');
        String outFileName = fileName + ".checkstn";

        // delete the old the dummy file
        String dummyFile = fileName + ".finished";
        ec.deleteOldFile(dummyFile);

        // create the 'processing' file
        String dummyFile2 = fileName + ".processing";
        ec.createNewFile(dummyFile2);

        // open the reportFile files
        RandomAccessFile reportFile = openOutputFile(outFileName);

        // get the station records
        MrnStation[] stations = getStationRecords(surveyId);

        String spldattim[] = new String[stations.length];
        boolean processed[] = new boolean[stations.length];
        float latitude[] = new float[stations.length];
        float longitude[] = new float[stations.length];

        // change the latitude for each station, and get the spldattim
        for (int i = 0; i < stations.length; i++) {

            // change latitude and longitude from 5 to 4 decimal values
            latitude[i] = convert(stations[i].getLatitude());
            longitude[i] = convert(stations[i].getLongitude());
            if (dbg) System.out.println(stations[i].getStationId() + " " +
                ec.frm(stations[i].getLatitude(),10,5) + " " +
                ec.frm(latitude[i],10,5) + " " +
                ec.frm(stations[i].getLongitude(),10,5) + " " +
                ec.frm(longitude[i],10,5));

            // get spldattim
            // get the watphy and watnut records
            spldattim[i] = getWatphyRecords(stations[i].getStationId());
            processed[i] = false;
        } // for (int i = 0; i < stations.length; i++)

        // process each station
        for (int i = 0; i < stations.length; i++) {

            if (!processed[i]) {

                ec.writeFileLine(reportFile, "");
                writeRecord(reportFile, stations[i], spldattim[i]);

                for (int j = i + 1; j < stations.length; j++) {

                    if ((latitude[i] == latitude[j]) &&
                        (longitude[i] == longitude[j])) {
                        writeRecord(reportFile, stations[j], spldattim[j]);
                        processed[j] = true;
                    } // if (stations[i].getLatitude() == stations[j].getLatitude())
                } // for (int j = i + 1; j < stations.length; j++)
            } // if (!processed[i])
        } // for (int i = 0; i < stations.length; i++)

        try {
            reportFile.close();
        } catch(Exception e) { } //

        // delete .processing, create the .finished
        ec.deleteOldFile(dummyFile2);
        ec.createNewFile(dummyFile);

    } // AdminCheckDupStationsActual


    /**
     * Get Station records from Database for the @param surveyId
     * @param surveyId : argument value from url (string)
     * @return array of station records
     */
    MrnStation[] getStationRecords(String surveyId) {

        if (dbg) System.out.println("<br<getStationRecords ...");

        String where = MrnStation.SURVEY_ID +"='"+ surveyId +"'";
        //String order = MrnStation.STATION_ID;
        String order = MrnStation.DATE_START;
        MrnStation[] stations = new MrnStation().get(where, order);

        return stations;
    } // getStationRecords


    /**
     * Get watphy records from Database for the @param station-id
     * @param stationId : argument value from url (string)
     * @return array of watphy records
     */
    String getWatphyRecords(String stationId) {

        if (dbg) System.out.println("<br<getWatphyRecords ...");

        String field = "distinct " + MrnWatphy.SPLDATTIM;
        String where = MrnWatphy.STATION_ID +"='"+ stationId +"'";
        MrnWatphy[] watphy = new MrnWatphy().get(field, where, "");

        return (watphy.length > 0 ? watphy[0].getSpldattim("") : "");
    } //getWatphyRecords


    /**
     * Open the output file and make it readable by all
     * @param   fileName   The complete file name (String)
     * @return  the RandomAccessFile handle
     */
    RandomAccessFile openOutputFile(String fileName) {
        ec.deleteOldFile(fileName);
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(fileName, "rw");
        } catch (Exception e) {
            //ec.printError()
        } // try-catch
        if (ec.getHost().startsWith(sc.HOST)) {
            ec.submitJob("chmod 644 " + fileName);
        } // if (ec.getHost().startsWith(sc.HOST))
        return file;
    } // openInputFile


    /**
     * Write the station info to the reportFile file
     * @param  reportFile   the reportFile file
     * @param  station  the station record
     * @param  spldattim   the date-time
     */
    void writeRecord(RandomAccessFile reportFile, MrnStation station, String spldattim) {


        ec.writeFileLine(reportFile,
            ec.frm(station.getStationId(), 14) +
            ec.frm(station.getLatitude(), 11, 5) +
            ec.frm(station.getLongitude(), 12, 5) + "  " +
            spldattim);

    } // writeRecord


    /**
     * convert latitude or longitude from a 5 decimal to a 4 decimal value
     * @param  value  5 decimal latitude or longitude value
     * @return value converted to 4 decimals
     */
    float convert(float value) {
        int degree = (int)(value);
        float temp1 = value - (float)degree;
        int temp2 = (int)(temp1 * 10000f);
        float minutes = (float)temp2 / 10000f;
        return ((float)degree + minutes);
    } // convert


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
    public static void main(String args[]) {

        try {
            AdminCheckDupStations local= new AdminCheckDupStations(args);
        } catch(Exception e) {
            ec.processErrorStatic(e, "AdminCheckDupStations", "Main", "");
            // close the connection to the database
        } // try-catch
        common2.DbAccessC.close();

    } // main

} // class AdminCheckDupStations