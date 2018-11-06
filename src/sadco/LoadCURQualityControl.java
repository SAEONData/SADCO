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
public class LoadCURQualityControl extends CompoundItem {

    boolean dbg             = false;
    //boolean dbg             = true;

    static final int MAX_QC     = 5;

    static common.edmCommon ec = new common.edmCommon();

    /** A class with all the constants used in Currents.*/
    SadConstants cc = new SadConstants();

    // arguments
    String pathName           = "";
    String fileName           = "";
    String completeFileName   = "";
    String reportFileName     = "";

    // data file variables
    String depthCode        = "";
    String personChecked    = "";
    String dateChecked      = "";
    String qcValues[]     = new String[MAX_QC];
    //boolean parmPresent[]   = new boolean[numParms];

    // working variables
    String parameterList[]  = null;
    int    numParms         = 0;
    //int    depthCode        = 0;
    String line             = "";
    String parameter        = "";
    CurDepth depths[]       = null;
    boolean depthLoaded     = false;
    StringTokenizer t       = null;
    int depthsCnt          = 0;
    int depthsLoadedCnt    = 0;
    int depthsNotLoadedCnt = 0;


    // data file
    RandomAccessFile file   = null;
    RandomAccessFile reportFile = null;

    public LoadCURQualityControl(String args[]) {

        if (ec.getHost().startsWith(cc.HOST)) {
            pathName = cc.HOSTDIRL + "currents/";
        } else {
            pathName = cc.LOCALDIR + "currents/";
        } // if host
        if (dbg) System.out.println(
            "<br>currents.LoadCURQualityControl pathName = " + pathName);

        // get the argument values
        getArgParms(args);

        //completeFileName = pathName + fileName;
        reportFileName = fileName + ".report";

        // get the list of parameterList
        CurDepthQcParmcodes parmCodes[] = new CurDepthQcParmcodes().get();
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

        subTab.addRow(ec.cr2ColRow("Total number of depths : ",depthsCnt));
        subTab.addRow(ec.cr2ColRow("Depths loaded          : ",depthsLoadedCnt));
        subTab.addRow(ec.cr2ColRow("Depths not loaded      : ",depthsNotLoadedCnt));

        // the link to download the file, and instructions
        String link = new Link(cc.LOADS_URL+"currents/"+reportFileName, reportFileName).toHTML();
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

            if (line.startsWith("Depth")) {

                depthsCnt++;

                // get depth_code, person and date
                t = new StringTokenizer(line, " ");
                t.nextToken();   // skip 'DepthCode'

                depthCode = t.nextToken();
                if (dbg) System.out.println("\n\nDepthCode = " + depthCode);
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
                ec.writeFileLine(reportFile, "\nprocessing " + depthCode);

                // check whether the depths is actually there
                CurDepth depth = new CurDepth();
                depth.setCode(depthCode);
                depths = depth.get();
                if (depths.length > 0) {
                    depthLoaded = true;
                    depthsLoadedCnt++;
                } else {
                    depthsNotLoadedCnt++;
                    depthLoaded = false;
                    // write message to user
                    ec.writeFileLine(reportFile, "  cur_depth record not found\n");
                } // if (depths.length > 0)
                if (dbg) System.out.println("depth record found = " + depthLoaded);
                if (dbg) System.out.println("depths.length1 = " + depths.length);

            } // if (line.startsWith("Depth"))

            if (dbg) System.out.println("depths.length2 = " + depths.length);

            if (line.startsWith("1") && depthLoaded) {

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

                CurDepthQc depthQc = new CurDepthQc();

                // set some values
                depthQc.setPersonChecked(personChecked);
                depthQc.setDateChecked(dateChecked);

                // set the QC values
                int ii = 0;
                depthQc.setBroadrange  (qcValues[ii++]);
                depthQc.setSpikes      (qcValues[ii++]);
                depthQc.setSensordrift (qcValues[ii++]);
                depthQc.setGaps        (qcValues[ii++]);
                depthQc.setLeadertrailer(qcValues[ii++]);

                if (dbg) System.out.println("depths.length3 = " + depths.length);

                // check whether the record already exists
                CurDepthQc depthQcWhere = new CurDepthQc();
                depthQcWhere.setDepthCode(depthCode);
                depthQcWhere.setParameterCode(parmCode);

                CurDepthQc depthQcArray[] = depthQcWhere.get();

                if (depthQcArray.length == 0) {
                    depthQc.setDepthCode(depthCode);
                    depthQc.setParameterCode(parmCode);
                    depthQc.put();
                    if (dbg) System.out.println("depthQc inserted: " +depthQc);
                    if (dbg) System.out.println("depthQc insString =  " +depthQc.getInsStr());
                    ec.writeFileLine(reportFile,
                        "  depthQc record inserted for '" +
                        parameterList[parmCode] + "'\n    " +
                        depthQc.toString());
                } else {
                    depthQcWhere.upd(depthQc);
                    if (dbg) System.out.println("depthQc updated: " +depthQc);
                    ec.writeFileLine(reportFile,
                    "  depthQc record updated for '" +
                        parameterList[parmCode] + "'\n    " +
                        depthQcWhere.toString() + "   " +
                        depthQc.toString());
                } // if (depthQcArray.length == 0)

            } // if (line.startsWith("1") && depthLoaded)

            // get the next line, read till the next depthCode
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
            LoadCURQualityControl local =
                new LoadCURQualityControl(args);
        } catch(Exception e) {
            common.edmCommonPC.processErrorStatic(
                e, "LoadCURQualityControl", "Constructor", "");
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
              HtmlHead hd = new HtmlHead("LoadCURQualityControl local");
              HtmlBody bd = new HtmlBody();
              HtmlPage hp = new HtmlPage(hd,bd);

              bd.addItem(new LoadCURQualityControl(args));
              hp.printHeader();
              hp.print();
              //common2.DbAccessC.close();
          } catch(Exception e) {
              //ec.processErrorStatic(e, "LoadCURQualityControl", "Constructor", "");
              // close the connection to the database
              //common2.DbAccessC.close();
          } // try-catch
        common2.DbAccessC.close();

      } // main

} // class LoadCURQualityControl

