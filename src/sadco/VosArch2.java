//+------------------------------------------------+----------------+--------+
//| CLASS: ....... VosArch2                        | JAVA PROGRAM   | 001120 |
//+------------------------------------------------+----------------+--------+
//| PROGRAMMER: .. SIT Group                                                 |
//+--------------------------------------------------------------------------+
//| Description                                                              |
//| ===========                                                              |
//| The class that handles the VOS_ARCH2 table.                              |
//|                                                                          |
//+--------------------------------------------------------------------------+
//| History                                                                  |
//| =======                                                                  |
//| 001120 GenTableClassB class   create class                               |
//+--------------------------------------------------------------------------+

package sadco;

import java.sql.*;
import java.util.*;

/**
 * This class manages the VOS_ARCH2 table with the help of the DbAccessC
 * class.   It has <b>get, put, del</b> and <b>upd</b> methods to <b>select,
 * insert, delete</b> and <b>update</b> the table.
 */
public class VosArch2 extends VosTable {

    boolean dbg = false;
    //boolean dbg = true;

    //==========================================//
    // All the constants and instance variables //
    //==========================================//

    /** The table name */
    //public static final String TABLE = "VOS_ARCH2";

    //======================//
    // All the constructors //
    //======================//

    /**
     * Makes use of Tables constructor where the DbAccessC class is instantiated.
     */
    public VosArch2() {
        super();
        TABLE = "VOS_ARCH2";
        //clearVars();
        if (dbg) System.out.println ("<br>in VosArch2 constructor 1"); // debug
    } // VosArch2 constructor

    /**
     * Instantiate a VosArch2 object and initialize the instance variables.
     * @param latitude  The latitude (float)
     */
    public VosArch2(
            float latitude) {
        //this();
        super(latitude);
        TABLE = "VOS_ARCH2";
        //setLatitude               (latitude              );
        if (dbg) System.out.println ("<br>in VosArch2 constructor 2"); // debug
    } // VosArch2 constructor

    /**
     * Instantiate a VosArch2 object and initialize the instance variables.
     * @param latitude                The latitude               (float)
     * @param longitude               The longitude              (float)
     * @param dateTime                The dateTime               (java.util.Date)
     * @param daynull                 The daynull                (String)
     * @param callsign                The callsign               (String)
     * @param country                 The country                (String)
     * @param platform                The platform               (String)
     * @param dataId                  The dataId                 (String)
     * @param qualityControl          The qualityControl         (String)
     * @param source1                 The source1                (String)
     * @param loadId                  The loadId                 (int)
     * @param dupflag                 The dupflag                (String)
     * @param atmosphericPressure     The atmosphericPressure    (float)
     * @param surfaceTemperature      The surfaceTemperature     (float)
     * @param surfaceTemperatureType  The surfaceTemperatureType (String)
     * @param drybulb                 The drybulb                (float)
     * @param wetbulb                 The wetbulb                (float)
     * @param wetbulbIce              The wetbulbIce             (String)
     * @param dewpoint                The dewpoint               (float)
     * @param cloudAmount             The cloudAmount            (String)
     * @param cloud1                  The cloud1                 (String)
     * @param cloud2                  The cloud2                 (String)
     * @param cloud3                  The cloud3                 (String)
     * @param cloud4                  The cloud4                 (String)
     * @param cloud5                  The cloud5                 (String)
     * @param visibilityCode          The visibilityCode         (String)
     * @param weatherCode             The weatherCode            (String)
     * @param swellDirection          The swellDirection         (int)
     * @param swellHeight             The swellHeight            (float)
     * @param swellPeriod             The swellPeriod            (int)
     * @param waveHeight              The waveHeight             (float)
     * @param wavePeriod              The wavePeriod             (int)
     * @param windDirection           The windDirection          (int)
     * @param windSpeed               The windSpeed              (float)
     * @param windSpeedType           The windSpeedType          (String)
     */
    public VosArch2(
            float          latitude,
            float          longitude,
            java.util.Date dateTime,
            String         daynull,
            String         callsign,
            String         country,
            String         platform,
            String         dataId,
            String         qualityControl,
            String         source1,
            int            loadId,
            String         dupflag,
            float          atmosphericPressure,
            float          surfaceTemperature,
            String         surfaceTemperatureType,
            float          drybulb,
            float          wetbulb,
            String         wetbulbIce,
            float          dewpoint,
            String         cloudAmount,
            String         cloud1,
            String         cloud2,
            String         cloud3,
            String         cloud4,
            String         cloud5,
            String         visibilityCode,
            String         weatherCode,
            int            swellDirection,
            float          swellHeight,
            int            swellPeriod,
            float          waveHeight,
            int            wavePeriod,
            int            windDirection,
            float          windSpeed,
            String         windSpeedType) {
        //this();
        super(latitude, longitude, dateTime, daynull, callsign, country,
            platform, dataId, qualityControl, source1, loadId, dupflag,
            atmosphericPressure, surfaceTemperature, surfaceTemperatureType,
            drybulb, wetbulb, wetbulbIce, dewpoint, cloudAmount, cloud1,
            cloud2, cloud3, cloud4, cloud5, visibilityCode, weatherCode,
            swellDirection, swellHeight, swellPeriod, waveHeight, wavePeriod,
            windDirection, windSpeed, windSpeedType);
        TABLE = "VOS_ARCH2";
        if (dbg) System.out.println ("<br>in VosArch2 constructor 3"); // debug
    } // VosArch2 constructor


    //=================//
    // All the setters //
    //=================//

    /** those in VosTable are used */


    //=================//
    // All the getters //
    //=================//

    /** those in VosTable are used */

    /**
     * Gets the number of records in the table
     * @return number of records (int)
     */
    public int getRecCnt () {
        return (getRecCnt(TABLE, "1=1"));
    } // method getRecCnt

    /**
     * Gets the number of records in the table
     * @param  where  The where clause (String)
     * @return number of records (int)
     */
    public int getRecCnt(String where) {
        return getRecCnt(TABLE, where);
    } // method getRecCnt


    //=============================//
    // All the get & doGet methods //
    //=============================//

    /**
     * Select all the fields from the table.  A where clause is created
     * with the current values of the class variables. The results
     * are returned in an array of VosArch2. e.g.<pre>
     * VosArch2 vosArch2 = new VosArch2(<val1>);
     * VosArch2 vosArch2Array[] = vosArch2.get();</pre>
     * will get the VosArch2 record where latitude = <val1>.
     * @return Array of VosArch2 (VosArch2[])
     */
    public VosArch2[] get() {
        return cvt2Local(doGet(db.select(TABLE, createWhere())));
    } // method get
    public Vector getV() {
        return db.select(TABLE, createWhere());
    } // method getV


    /**
     * Select all the fields from the table, restrict records with a 'where'
     * clause.  Same as <b>get()</b>, but the 'where' is explicitly given.
     * e.g.<pre>
     * VosArch2 vosArch2Array[] =
     *     new VosArch2().get(VosArch2.LATITUDE+"=<val1>");</pre>
     * will get the VosArch2 record where latitude = <val1>.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @return Array of VosArch2 (VosArch2[])
     */
    public VosArch2[] get (String where) {
        return cvt2Local(doGet(db.select(TABLE, where)));
    } // method get
    public Vector getV (String where) {
        return db.select(TABLE, where);
    } // method get

    /**
     * Select all the fields from the table, and order the result.  Same as
     * <b>get(where)</b>, but an order is specified. e.g.<pre>
     * VosArch2 vosArch2Array[] =
     *     new VosArch2().get("1=1",vosArch2.LATITUDE);</pre>
     * will get the all the VosArch2 records, and order them by latitude.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of VosArch2 (VosArch2[])
     */
    public VosArch2[] get (String where, String order) {
        return cvt2Local(doGet(db.select("*", TABLE, where, order)));
    } // method get
    public Vector getV (String where, String order) {
        return db.select("*", TABLE, where, order);
    } // method get

    /**
     * Select selected fields from the table, and order the result.  Same as
     * <b>get(where, order)</b>, but the fields are specified. e.g.<pre>
     * String columns = VosArch2.LATITUDE,VosArch2.LONGITUDE;
     * String where = VosArch2.LATITUDE + "=<val1";
     * String order = VosArch2.LATITUDE;
     * VosArch2 vosArch2Array[] =
     *     new VosArch2().get(columns, where, order);</pre>
     * will get the latitude and longitude colums of all VosArch2 records,
     * where latitude = <val1>, and order them by latitude.
     * @param  fields A comma-delimited string of the the list of fields to select. (String)
     *     If fields = "" / null, --> created from non-null instance variables.
     * @param  where  The where clause for the select statement. (String)
     *     If where == "" / null, it is ignored.
     * @param  order  A comma-delimited string of the fields to sort on. (String)
     *     If order == "" / null, it is ignored.
     * @return Array of VosArch2 (VosArch2[])
     */
    public VosArch2[] get (String fields, String where, String order) {
        // create the column list from the instance variables if neccessary
        try {
            if (fields.equals("")) { fields = null; }
        } catch (NullPointerException e) {}
        if (fields == null) {
            fields = createColumns();
        } // if (fields != null)
        return cvt2Local(doGet(db.select(fields, TABLE, where, order)));
    } // method get


    //=====================//
    // All the put methods //
    //=====================//

    /**
     * Insert a record into the table.  The values are taken from the current
     * value of the instance variables. .e.g.<pre>
     * String    CHARNULL  = Tables.CHARNULL;
     * Timestamp DATENULL  = Tables.DATENULL;
     * int       INTNULL   = Tables.INTNULL;
     * float     FLOATNULL = Tables.FLOATNULL;
     * Boolean success =
     *     new VosArch2(
     *         <val1>,
     *         <val2>,
     *         <val3>,
     *         <val4>,
     *         <val5>,
     *         <val6>,
     *         <val7>,
     *         <val8>,
     *         <val9>,
     *         <val10>,
     *         <val11>,
     *         <val12>,
     *         <val13>,
     *         <val14>,
     *         <val15>,
     *         <val16>,
     *         <val17>,
     *         <val18>,
     *         <val19>,
     *         <val20>,
     *         <val21>,
     *         <val22>,
     *         <val23>,
     *         <val24>,
     *         <val25>,
     *         <val26>,
     *         <val27>,
     *         <val28>,
     *         <val29>,
     *         <val30>,
     *         <val31>,
     *         <val32>,
     *         <val33>,
     *         <val34>,
     *         <val35>).put();</pre>
     * will insert a record with:
     *     latitude               = <val1>,
     *     longitude              = <val2>,
     *     dateTime               = <val3>,
     *     daynull                = <val4>,
     *     callsign               = <val5>,
     *     country                = <val6>,
     *     platform               = <val7>,
     *     dataId                 = <val8>,
     *     qualityControl         = <val9>,
     *     source1                = <val10>,
     *     loadId                 = <val11>,
     *     dupflag                = <val12>,
     *     atmosphericPressure    = <val13>,
     *     surfaceTemperature     = <val14>,
     *     surfaceTemperatureType = <val15>,
     *     drybulb                = <val16>,
     *     wetbulb                = <val17>,
     *     wetbulbIce             = <val18>,
     *     dewpoint               = <val19>,
     *     cloudAmount            = <val20>,
     *     cloud1                 = <val21>,
     *     cloud2                 = <val22>,
     *     cloud3                 = <val23>,
     *     cloud4                 = <val24>,
     *     cloud5                 = <val25>,
     *     visibilityCode         = <val26>,
     *     weatherCode            = <val27>,
     *     swellDirection         = <val28>,
     *     swellHeight            = <val29>,
     *     swellPeriod            = <val30>,
     *     waveHeight             = <val31>,
     *     wavePeriod             = <val32>,
     *     windDirection          = <val33>,
     *     windSpeed              = <val34>,
     *     windSpeedType          = <val35>.
     * @return success = true/false (boolean)
     */
    public boolean put() {
        return doPut(TABLE);
    } // method put


    //=====================//
    // All the del methods //
    //=====================//

    /**
     * Delete record(s) from the table, The where clause is created from the
     * current values of the instance variables. .e.g.<pre>
     * Boolean success = new VosArch2(
     *     Tables.FLOATNULL,
     *     <val2>,
     *     Tables.DATENULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.CHARNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.INTNULL,
     *     Tables.INTNULL,
     *     Tables.FLOATNULL,
     *     Tables.CHARNULL).del();</pre>
     * will delete all records where longitude = <val2>.
     * @return success = true/false (boolean)
     */
    public boolean del() {
        return doDel(TABLE, createWhere());
    } // method del

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * VosArch2 vosArch2 = new VosArch2();
     * success = vosArch2.del(VosArch2.LATITUDE+"=<val1>");</pre>
     * will delete all records where latitude = <val1>.
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean del(String where) {
        return doDel(TABLE, where);
    } // method del


    //=====================//
    // All the upd methods //
    //=====================//

    /**
     * Update record(s) from the table, The fields and values to use for the
     * update are taken from the VosArch2 argument, .e.g.<pre>
     * Boolean success
     * VosArch2 updVosArch2 = new VosArch2();
     * updVosArch2.setLongitude(<val2>);
     * VosArch2 whereVosArch2 = new VosArch2(<val1>);
     * success = whereVosArch2.upd(updJobs);</pre>
     * will update Longitude to <val2> for all records where
     * latitude = <val1>.
     * @param  vosArch2  A VosArch2 variable in which the fields are to be used for
     *                 the update are set
     * @return success = true/false (boolean)
     */
    public boolean upd(VosArch2 vosArch2) {
        return doUpd(TABLE, vosArch2, createWhere());
    } // method upd

    /**
     * Delete record(s) from the table.  with an explicitly given where clause.
     *  .e.g.<pre>
     * Boolean success
     * VosArch2 updVosArch2 = new VosArch2();
     * updVosArch2.setLongitude(<val2>);
     * VosArch2 whereVosArch2 = new VosArch2();
     * success = whereVosArch2.upd(
     *     updVosArch2, VosArch2.LATITUDE+"=<val1>");</pre>
     * will update Longitude to <val2> for all records where
     * latitude = <val1>.
     * @param  vosArch2  A VosArch2 variable in which the fields are to be used for
     *                 the update are set
     * @param  where  The where clause for the select statement. (String)
     * @return success = true/false (boolean)
     */
    public boolean upd(VosArch2 vosArch2, String where) {
        return doUpd(TABLE, vosArch2, where);
    } // method upd


    //======================//
    // Other helper methods //
    //======================//

    /** those in VosTable are used */

    /**
     * converts the VosTables array to a VosArch2 array
     */
    VosArch2[] cvt2Local (VosTable[] ct) {
        VosArch2[] local = new VosArch2[ct.length];
        for (int i = 0; i < ct.length; i++) {
            local[i] = new VosArch2();
            local[i].setLatitude               (ct[i].getLatitude()              );
            local[i].setLongitude              (ct[i].getLongitude()             );
            local[i].setDateTime               (ct[i].getDateTime()              );
            local[i].setDaynull                (ct[i].getDaynull()               );
            local[i].setCallsign               (ct[i].getCallsign()              );
            local[i].setCountry                (ct[i].getCountry()               );
            local[i].setPlatform               (ct[i].getPlatform()              );
            local[i].setDataId                 (ct[i].getDataId()                );
            local[i].setQualityControl         (ct[i].getQualityControl()        );
            local[i].setSource1                (ct[i].getSource1()               );
            local[i].setLoadId                 (ct[i].getLoadId()                );
            local[i].setDupflag                (ct[i].getDupflag()               );
            local[i].setAtmosphericPressure    (ct[i].getAtmosphericPressure()   );
            local[i].setSurfaceTemperature     (ct[i].getSurfaceTemperature()    );
            local[i].setSurfaceTemperatureType (ct[i].getSurfaceTemperatureType());
            local[i].setDrybulb                (ct[i].getDrybulb()               );
            local[i].setWetbulb                (ct[i].getWetbulb()               );
            local[i].setWetbulbIce             (ct[i].getWetbulbIce()            );
            local[i].setDewpoint               (ct[i].getDewpoint()              );
            local[i].setCloudAmount            (ct[i].getCloudAmount()           );
            local[i].setCloud1                 (ct[i].getCloud1()                );
            local[i].setCloud2                 (ct[i].getCloud2()                );
            local[i].setCloud3                 (ct[i].getCloud3()                );
            local[i].setCloud4                 (ct[i].getCloud4()                );
            local[i].setCloud5                 (ct[i].getCloud5()                );
            local[i].setVisibilityCode         (ct[i].getVisibilityCode()        );
            local[i].setWeatherCode            (ct[i].getWeatherCode()           );
            local[i].setSwellDirection         (ct[i].getSwellDirection()        );
            local[i].setSwellHeight            (ct[i].getSwellHeight()           );
            local[i].setSwellPeriod            (ct[i].getSwellPeriod()           );
            local[i].setWaveHeight             (ct[i].getWaveHeight()            );
            local[i].setWavePeriod             (ct[i].getWavePeriod()            );
            local[i].setWindDirection          (ct[i].getWindDirection()         );
            local[i].setWindSpeed              (ct[i].getWindSpeed()             );
            local[i].setWindSpeedType          (ct[i].getWindSpeedType()         );
        }
        return local;
    } // cvt2Local

} // class VosArch2
