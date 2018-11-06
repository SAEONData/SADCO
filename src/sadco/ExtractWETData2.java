//+----------------------------------------------+----------------+----------+
//| CLASS: ....... ExtractWETData2                | JAVA PROGRAM   |  20000717|
//+----------------------------------------------+----------------+----------+
//| PROGRAMMER: .. SIT group                                                 |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//| Print the extraction data to an output file                              |
//|                                                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 20000717  Fraizer Jack         create class                              |
//+--------------------------------------------------------------------------+

package sadco;

import java.io.*;
import java.text.*;

public class ExtractWETData2 {

    WetStation  aWetStation[];
    WetData     aWetData[];
    WetStation  vWetStation     = new WetStation();
    WetData     vWetData        = new WetData();
    boolean success;

    boolean dbg = false;
    //boolean dbg = true;

    int    countRecs = 0;

    // contains the method to parse URL-type arguments
    static common.edmCommonPC ec = new common.edmCommonPC();

    /**
     * Do the actual extraction of the data according to the criteria
     * @param  format           chosen data format (int)
     * @param  file         output file name
     * @param  stationId        stationId (if present)
     * @param  wetStationWhere  where clause for WetStation table
     * @param  wetDataWhere     where clause for WetData table
     */
    public ExtractWETData2(
            int format,
            RandomAccessFile file,
            String stationId,
            String wetStationWhere,
            String wetDataWhere
            ) {

        if (dbg) System.out.println("<br>ExtractWETData2 : ");

        /*
        // Print the column headings
        switch (format) {
        case 1: // extraction format 1
            printHeader12(file);
            break;

        case 2: // extraction format 2
            printHeader12(file);
            break;
        }   //  switch (format) {
        */

        // Place the START OF DATA marker at the end of the file.
        //ec.writeFileLine(file,"DATA:");

        if (dbg) System.out.println("<br>wetStationWhere : " + wetStationWhere);
        if (dbg) System.out.println("<br>wetDataWhere : " + wetDataWhere);

        // print extracted data
        if (wetStationWhere.length() == 0)    {
            aWetStation = vWetStation.get("STATION_ID='" + stationId + "'");
            selectData(file, format, aWetStation[0], wetDataWhere);

            if (dbg) System.out.println("<br>** wetStationWhere < 0 "+wetStationWhere
                +" wetStationWhere.length "+wetStationWhere.length());

        } else {
            //  get all stations, and loop through them
            aWetStation = vWetStation.get(wetStationWhere);

            if (dbg) System.out.println("<br>ok wetStationWhere > 0 "+wetStationWhere);
            for (int i = 0; i < aWetStation.length; i++) {
                String newWhere =
                    "STATION_ID='" +
                    aWetStation[i].getStationId() +
                    "' " + wetDataWhere ;
                selectData(file, format, aWetStation[i], newWhere);


            } // for i
        }  //  if (wetStationWhere.length() = 0)

        // Place the END OF DATA marker at the end of the file.
        //ec.writeFileLine(file,"END OF DATA");

    } // constructor


    /*
     * Sort out the position, and print the data
     * @param  file    output file name
     * @param  format      chosen data format (int)
     * @param  station     a WetStation record
     * @param  where       where clause for WetData table
     */
    void selectData (
            RandomAccessFile file,
            int format,
            WetStation station,
            String where) {

        String position = "";

        if (format == 2) {
            position =
                ec.frm(station.getLatitude(),10,5)      +
                ec.frm(station.getLongitude(),11,5)     +
                " ";
        } // if format = 2

        printData(file, format, position,
            vWetData.get(where, WetData.DATE_TIME));
    } // void selectData


    /*
     * Print the data according to the format
     * @param  file    output file name
     * @param  format      chosen data format (int)
     * @param  position    the position
     * @param  aWetData[]  array of WetData records
     */
    void printData(
            RandomAccessFile file,
            int format,
            String position,
            WetData aWetData[]) {

        countRecs += aWetData.length;
        if(dbg) System.out.println("1 : 1 aWetData.length : "+ aWetData.length);

        // print the data list
        for (int i=0; i<aWetData.length; i++) {
            switch (format) {
            case 1: // extraction format 1
                printDataLine12(file, aWetData[i]);
                break;
            case 2: // extraction format 2
                printDataLine12(file, aWetData[i]);
                break;
            }   //  switch (format) {
        } // for i

    } // void printData


    /*
     * Print header for format 1 and 2
     * @param  file    output file name
     */
    /*
    void printHeader12 (RandomAccessFile file) {
        ec.writeFileLine(file, "FORMAT:"                                                                    );
        ec.writeFileLine(file, ":   6: 4:YEAR   :                                            >wdtyy  <"     );
        ec.writeFileLine(file, ":  10: 3:MONTH  :                                            >wdtmm  <"     );
        ec.writeFileLine(file, ":  13: 3:DAY    :                                            >wdtdd  <"     );
        ec.writeFileLine(file, ":  16: 3:HOUR   :                                            >wdmhh  <"     );
        ec.writeFileLine(file, ":  19: 3:MINUTE :                                            >wdmmm  <"     );
        ec.writeFileLine(file, ":  22: 6:Average Temperature (C)       :     99.99:          >ata    <"     );
        ec.writeFileLine(file, ":  28: 7:Minimum Temperature (C)       :     99.99:          >atmn   <"     );
        ec.writeFileLine(file, ":  35: 6:Time of Min. Temerature       :     99.99:          >atmnt  <"     );
        ec.writeFileLine(file, ":  41: 7:Maximum Temperature (C)       :     99.99:          >atmx   <"     );
        ec.writeFileLine(file, ":  48: 6:Time of Max. Temperature      :     99.99:          >atmxt  <"     );
        ec.writeFileLine(file, ":  54: 7:Barometric Pressure           :    9999.9:          >bp     <"     );
        ec.writeFileLine(file, ":  61: 7:Fog                           :     999.9:          >fg     <"     );
        ec.writeFileLine(file, ":  68: 7:Rainfall                      :     999.9:          >rf     <"     );
        ec.writeFileLine(file, ":  75: 7:Relative Humidty              :     999.9:          >rh     <"     );
        ec.writeFileLine(file, ":  82: 8:Solar Radiation               :    9999.9:          >sr     <"     );
        ec.writeFileLine(file, ":  90: 8:Solar Radiation Maximum       :    9999.9:          >srmx   <"     );
        ec.writeFileLine(file, ":  98: 7:Wind Direction                :     999.9:          >wd     <"     );
        ec.writeFileLine(file, ": 105: 7:Wind Speed Average            :     99.99:          >wsa    <"     );
        ec.writeFileLine(file, ": 112: 7:Wind Speed Minimum            :     99.99:          >wsmn   <"     );
        ec.writeFileLine(file, ": 119: 7:Wind Speed Maximum            :     99.99:          >wsmx   <"     );
        ec.writeFileLine(file, ": 126: 6:Time of Wind Speed Maximum    :     99.99:          >wsmxt  <"     );
        ec.writeFileLine(file, ": 132: 3:Duraton of Wind Speed Maximum :        99:          >wsmxd  <"     );
        ec.writeFileLine(file, ": 135: 7:Wind Speed Standard Deviation :     99.99:          >wsssd  <"     );
        ec.writeFileLine(file, ": 142: 7:Wind Speed Maximum Direction  :     999.9:          >wsmxwd <"     );
        //ec.writeFileLine(file, ": 149: 7:Tide Level                    :     999.9:          >tlev   <"     );
        //ec.writeFileLine(file, ": 156: 7:Tide Minimum                  :     999.9:          >tmin   <"     );
        //ec.writeFileLine(file, ": 173: 7:Tide Maximum                  :     999.9:          >tmax   <"     );
        //ec.writeFileLine(file, ": 180: 1:Tide Status (r=rising f=fall) :          :          >tsta   <"     );
    } // printHeader12
    */


    /*
     * Print header for format 3
     * @param  file    output file name
     */
    void printHeader3 (RandomAccessFile file) {
        String head1 = "StID Latitude  Longitude  Date       Time ";
        String line1 = "---- --------- ---------- ---------- ---- ";
        String head2 = "    HMO     TPF AVE DIR AVE SPR INSTR CODE";
        String line2 = "------- ------- ------- ------- ----------";
        ec.writeFileLine(file, head1 + head2);
        ec.writeFileLine(file, line1 + line2);
    } // printHeader3


    /*
     * Print data line for format 1 & 2
     * @param  file    output file name
     * @param  data        a WetData record
     */
    void printDataLine12 (RandomAccessFile file, WetData data) {

        //SimpleDateFormat formatter    = new SimpleDateFormat ("hh.mm");
        String airTempMinTimeString   = "";
        String airTempMaxTimeString   = "";
        String windSpeedMaxTimeString = "";

        if (data.getAirTempMinTime() == Tables.DATENULL) {
            airTempMinTimeString = "99.99";
        } else {
            airTempMinTimeString = data.getAirTempMinTime("");
        }

        if (data.getAirTempMaxTime() == Tables.DATENULL) {
            airTempMaxTimeString = "99.99";
        } else {
            airTempMaxTimeString = data.getAirTempMaxTime("");
        }

        if (data.getWindSpeedMaxTime() == Tables.DATENULL) {
            windSpeedMaxTimeString = "99.99";
        } else {
            windSpeedMaxTimeString =
                data.getWindSpeedMaxTime("").replace(':', '.');
            if (dbg) System.out.println("<br>windSpeedMaxTimeString : " +
                windSpeedMaxTimeString);
        }

        ec.writeFileLine(file,
            data.getStationId()                                         + " " +
            data.getDateTime("").substring( 0, 4)                       + " " +
            data.getDateTime("").substring( 5, 7)                       + " " +
            data.getDateTime("").substring( 8,10)                       + " " +
            data.getDateTime("").substring(11,13)                       + " " +
            data.getDateTime("").substring(14,16)                       +
            ec.frm(ec.nullToNines(data.getAirTempAve(),99.99f), 6,2)    +
            ec.frm(ec.nullToNines(data.getAirTempMin(),99.99f), 7,2)    + " " +
            airTempMinTimeString                                              +
            ec.frm(ec.nullToNines(data.getAirTempMax(),99.99f), 7,2)    + " " +
            airTempMaxTimeString                                              +
            ec.frm(ec.nullToNines(data.getBarometricPressure(),9999.9f), 7,1) +
            ec.frm(ec.nullToNines(data.getFog(),999.9f), 7,1)                 +
            ec.frm(ec.nullToNines(data.getRainfall(),999.9f), 7,1)            +
            ec.frm(ec.nullToNines(data.getRelativeHumidity(),999.9f), 7,1)    +
            ec.frm(ec.nullToNines(data.getSolarRadiation(),9999.9f), 8,1)     +
            ec.frm(ec.nullToNines(data.getSolarRadiationMax(),9999.9f), 8,1)  +
            ec.frm(ec.nullToNines(data.getWindDir(),999.9f), 7,1)             +
            ec.frm(ec.nullToNines(data.getWindSpeedAve(),99.99f), 7,2)        +
            ec.frm(ec.nullToNines(data.getWindSpeedMin(),99.99f), 7,2)        +
            ec.frm(ec.nullToNines(data.getWindSpeedMax(),99.99f), 7,2)   + " "+
            windSpeedMaxTimeString                                            +
            ec.frm(ec.nullToNines(data.getWindSpeedMaxLength(), 99), 3)       +
            ec.frm(ec.nullToNines(data.getWindSpeedStd(),99.99f), 7,2)        +
            ec.frm(ec.nullToNines(data.getWindSpeedMaxDir(),999.9f), 7,1)
            );
    } // printDataLine12


    /*
     * Print data line for format 3
     * @param  file    output file name
     * @param  position    the position
     * @param  data        a WetData record
     */
    void printDataLine3 (RandomAccessFile file, String position, WetData data) {
        ec.writeFileLine(file,
            data.getStationId() +
            position +
            data.getDateTime("").substring( 0, 4) + " " +
            data.getDateTime("").substring( 5, 7) + " " +
            data.getDateTime("").substring( 8,10) + " " +
            data.getDateTime("").substring(11,13) +
            data.getDateTime("").substring(14,16)
            );

    } // printDataLine3



    /*
     * Return the number of records selected
     * @return  countRecs  number of records printed
     */
    int countRecords() {
       return countRecs;
    } // countRecords

} // class ExtractWETData2
