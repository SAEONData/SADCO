package sadco;

import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import oracle.html.CompoundItem;
import oracle.html.DynamicTable;
import oracle.html.HtmlBody;
import oracle.html.HtmlHead;
import oracle.html.HtmlPage;
import oracle.html.IHAlign;
import oracle.html.ITableFrame;
import oracle.html.ITableRules;
import oracle.html.Link;

/**
 * This class loads the qulity control parameterList for cur_depths.
 *
 * SadcoCUR
 * pScreen.equals("load")
 * pVersion.equals("loadqc")
 *
 * @author 20090226 - SIT Group
 * @version
 * 090226 - Mario August        - create class                              <br>
 * 110810 - Ursula von St Ange  - copy from currents.LoadQualityControl     <br>
 */
public class LoadWETQualityControl extends CompoundItem {

    //boolean dbg             = false;
    boolean dbg             = true;

    static final int MAX_QC     = 6;

    static common.edmCommon ec = new common.edmCommon();

    /** A class with all the constants used in Currents.*/
    SadConstants cc = new SadConstants();

    // arguments
    String pathName         = "";
    String fileName         = "";
    String completeFileName = "";
    String reportFileName   = "";

    // data file variables
    String stationID        = "";
    String surveyID         = "";
    String year             = "";
    String personChecked    = "";
    String dateChecked      = "";
    String qcValues[]       = new String[MAX_QC];
    //boolean parmPresent[]   = new boolean[numParms];

    // working variables
    String parameterList[]  = null;
    int    numParms         = 0;
    //int    stationID        = 0;
    String line             = "";
    String parameter        = "";
    int numRecords          = -1;
    WetData wetRecords[]    = null;
    boolean dataLoaded      = false;
    StringTokenizer t       = null;
    int dataCnt             = 0;
    int dataLoadedCnt       = 0;
    int dataNotLoadedCnt    = 0;


    // data file
    RandomAccessFile file   = null;
    RandomAccessFile reportFile = null;

    public LoadWETQualityControl(String args[]) {

        if (ec.getHost().startsWith(cc.HOST)) {
            pathName = cc.HOSTDIRL + "weather/";
        } else {
            pathName = cc.LOCALDIR + "weather/";
        } // if host
        if (dbg) System.out.println(
            "<br>sadco.LoadWETQualityControl pathName = " + pathName);

        // get the argument values
        getArgParms(args);

        //completeFileName = pathName + fileName;
        reportFileName = fileName + ".report";

        // get the list of parameterList
        WetDataQcParmcodes parmCodes[] = new WetDataQcParmcodes().get();
        numParms = parmCodes.length;
        parameterList = new String[numParms];
        for (int i = 0; i < numParms; i++) {
            parameterList[parmCodes[i].getCode()] =
                parmCodes[i].getName();
        } // for (int i = 0; i < numParms; i++)

        // process the input file - load the data
        processInputFile();

        // Create main table                                         //
        DynamicTable mainTable = new DynamicTable(0);
        mainTable.setFrame(ITableFrame.VOLD);
        mainTable.setRules(ITableRules.NONE);
        mainTable.setBorder(0);
        mainTable.setCenter();
        mainTable.setFrame(ITableFrame.BOX);

        mainTable.addRow(ec.crSpanColsRow("Load Quality Control Data ",2,
            true,"blue",IHAlign.CENTER,"+2" ));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));

        DynamicTable subTab = new DynamicTable(0);
        subTab.setFrame(ITableFrame.VOLD);
        subTab.setRules(ITableRules.NONE);
        subTab.setBorder(0);
        subTab.setCenter();

        subTab.addRow(ec.cr2ColRow("Total number of QC weather records: ",dataCnt));
        subTab.addRow(ec.cr2ColRow("Weather QC records loaded         : ",dataLoadedCnt));
        subTab.addRow(ec.cr2ColRow("Weather QC records not loaded     : ",dataNotLoadedCnt));

        // the link to download the file, and instructions
        String link = new Link(cc.LOADS_URL+"weather/"+reportFileName, reportFileName).toHTML();
        String instructions = ec.downloadFileInstructions().toHTML();

        mainTable.addRow(ec.crSpanColsRow(subTab.toHTML(),2));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTable.addRow(ec.crSpanColsRow("The report file has been "+
            "successfully generated.",2,true,"green",IHAlign.CENTER,"+1" ));
        mainTable.addRow(ec.crSpanColsRow(link,2));
        mainTable.addRow(ec.crSpanColsRow("&nbsp;",2));
        mainTable.addRow(ec.crSpanColsRow(instructions,2));

        this.addItem(mainTable.setCenter());

    } // constructor


    /**
     * Get the parameterList from the arguments in the URL
     * Only get values that are not in the header
     * @param  args       (String)
     */
    void getArgParms(String args[])   {

        fileName          = ec.getArgument(args,cc.FILENAME);

    } //  public void getArgParms()


    /**
     * process the input file
     */
    void processInputFile() {

        // Open the data file
        file = ec.openFile(pathName+fileName,"r");

        // open a file so user can follow progress (also report)
        ec.deleteOldFile(pathName+reportFileName);
        reportFile = ec.openFile(pathName+reportFileName,"rw");
        if (ec.getHost().startsWith(cc.HOST)) {
            ec.submitJob("chmod 644 " + pathName+reportFileName);
        } // if (ec.getHost().startsWith(cc.HOST))

        // get the first line
        line = ec.getNextValidLine(file);

        // loop through all the data  - assume all sets have 11 lines
        while (!ec.eof(file)) {

            if (dbg) System.out.println("line = " + line);

            if (line.startsWith("SurveyID")) {

                dataCnt++;

                // get SurveyID , year, person and date
                t = new StringTokenizer(line, " ");
                t.nextToken();   // skip 'Station'

                surveyID = t.nextToken();
                year = t.nextToken();

                // get station Id
                WetStation station = new WetStation();
                String where = WetStation.SURVEY_ID + " = '" +  surveyID + "'";
                String field = WetStation.STATION_ID;
                WetStation stations[] = station.get(field, where, "1=1");
                if (stations.length > 0) stationID = stations[0].getStationId();
                if (dbg) System.out.println("\n\nSurveyID = " + surveyID + " " +
                    stationID + " " + year);
                t.nextToken();  t.nextToken();  // skip 'Checked by'

                personChecked = "";
                StringBuffer person = new StringBuffer();
                String temp = t.nextToken();
                while (!"Date".equals(temp)) {
                    person.append(" " + temp);
                    temp = t.nextToken();
                } // while (!"Date".equals(temp))
                personChecked = person.toString().trim();
                if (dbg) System.out.println("personChecked = " + personChecked);

                dateChecked = t.nextToken();
                if (dbg) System.out.println("dateChecked = " + dateChecked);

                // do some user stuff
                ec.writeFileLine(reportFile, "\nprocessing " + stationID + " " + year);

                // check whether the wetData records is actually there
                WetData wetData = new WetData();
                where = wetData.STATION_ID + " = '" + stationID + "' and " +
                    wetData.DATE_TIME + " between '" + year + "-01-01 00:00:00.00' and '" +
                    year + "-12-31 23:59:59.99'";
                if (dbg) System.out.println("where = " + where);
                numRecords = wetData.getRecCnt(where);
                if (numRecords > 0) {
                    dataLoaded = true;
                    dataLoadedCnt++;
                } else {
                    dataLoaded = false;
                    dataNotLoadedCnt++;
                    // write message to user
                    ec.writeFileLine(reportFile, "  wet_data records not found\n");
                } // if (wetRecords.length > 0)
                if (dbg) System.out.println("wet_data records found = " + dataLoaded);
                if (dbg) System.out.println("numRecords = " + numRecords);

            } // if (line.startsWith("Station"))

            if (dbg) System.out.println("numRecords2 = " + numRecords);

            if (line.startsWith("1") && dataLoaded) {

                // initialise some variables
                for (int i = 0; i < MAX_QC; i++) {
                    qcValues[i] = "";
                } // for (int i = 0; i < MAX_QC; i++)

                t = new StringTokenizer(line, " ");
                t.nextToken(); // skip the '1'
                parameter = t.nextToken();
                if (dbg) System.out.println("parameter = " + parameter);

                // look for the parameter in the list
                int parmCode = -1;
                for (int j = 0; j < numParms; j++) {
                    if (parameter.equals(parameterList[j])) parmCode = j;
                } // for (int j = 0; j < numParms; j++)
                if (dbg) System.out.println("parmCode = " + parmCode);

                //if (dbg) {
                //    System.out.println("line2 = " + line);
                //    StringTokenizer t2 = new StringTokenizer(line, " ");
                //    int ij = 0;
                //    while (t2.hasMoreTokens()) {
                //        System.out.println("token: " + ij++ + " = " + t2.nextToken());
                //    } // while (t2.hasMoreTokens())
                //} // if (dbg)

                // get the QC values
                for (int j = 0; j < MAX_QC; j++) {
                    qcValues[j] = t.nextToken();
                    if (dbg) System.out.println("qcValues["+j+
                        "] = " + qcValues[j]);
                } // for (int j = 0; j < MAX_QC; j++)

                WetDataQc dataQc = new WetDataQc();

                // set some values
                dataQc.setPersonChecked(personChecked);
                dataQc.setDateChecked(dateChecked);

                // set the QC values
                int ii = 0;
                dataQc.setBroadrange  (qcValues[ii++]);
                dataQc.setSpikes      (qcValues[ii++]);
                dataQc.setSensordrift (qcValues[ii++]);
                dataQc.setGaps        (qcValues[ii++]);
                dataQc.setShift       (qcValues[ii++]);
                dataQc.setSensorjam   (qcValues[ii++]);

                if (dbg) System.out.println("numRecords3 = " + numRecords);

                // check whether the record already exists
                WetDataQc dataQcWhere = new WetDataQc();
                dataQcWhere.setStationId(stationID);
                dataQcWhere.setYear(year);
                dataQcWhere.setParameterCode(parmCode);

                WetDataQc dataQcArray[] = dataQcWhere.get();

                if (dataQcArray.length == 0) {
                    dataQc.setStationId(stationID);
                    dataQc.setYear(year);
                    dataQc.setParameterCode(parmCode);
                    dataQc.put();
                    if (dbg) System.out.println("dataQc inserted: " +dataQc);
                    if (dbg) System.out.println("dataQc insString =  " +dataQc.getInsStr());
                    ec.writeFileLine(reportFile,
                        "  dataQc record inserted for '" +
                        parameterList[parmCode] + "'\n    " +
                        dataQc.toString());
                } else {
                    dataQcWhere.upd(dataQc);
                    if (dbg) System.out.println("dataQc updated: " +dataQc);
                    ec.writeFileLine(reportFile,
                    "  dataQc record updated for '" +
                        parameterList[parmCode] + "'\n    " +
                        dataQcWhere.toString() + "   " +
                        dataQc.toString());
                } // if (dataQcArray.length == 0)

            } // if (line.startsWith("1") && dataLoaded)

            // get the next line, read till the next stationID
            line = ec.getNextValidLine(file);

        } //while (!ec.eof(file)) {

        // close the files
        ec.closeFile(file);
        ec.closeFile(reportFile);

    } // processInputFile


    /**
     * The constructor is instantiated in the main class to get away from
     * static / non-static problems.
     * @param args the string array of url-type name-value parameter pairs
     */
/*
    public static void main(String args[]) {

        try {
            LoadWETQualityControl local =
                new LoadWETQualityControl(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "LoadWETQualityControl", "Constructor", "");
        } // try-catch

        // close the connection to the database
        common2.DbAccessC.close();

    } // main
*/

      /**
       * The constructor is instantiated in the main class to get away from
       * static / non-static problems.
       * @param args the string array of url-type name-value parameter pairs
       */
      public static void main(String args[]) {

          try {
              HtmlHead hd = new HtmlHead("LoadWETQualityControl local");
              HtmlBody bd = new HtmlBody();
              HtmlPage hp = new HtmlPage(hd,bd);

              bd.addItem(new LoadWETQualityControl(args));
              hp.printHeader();
              hp.print();
              //common2.DbAccessC.close();
          } catch(Exception e) {
              //ec.processErrorStatic(e, "LoadWETQualityControl", "Constructor", "");
              // close the connection to the database
              //common2.DbAccessC.close();
          } // try-catch
        common2.DbAccessC.close();

      } // main

} // class LoadWETQualityControl

