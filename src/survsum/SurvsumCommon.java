package survsum;

import oracle.html.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * This class contains a set of commonly used routines in the survsum package
 *
 * @author 070823 - SIT Group
 * @version
 * 070823 - Ursula von St Ange - create class                               <br>
 */

public class SurvsumCommon {

    // Create java.sql stuff
    String USER = "sadco";
    String PWD = "ter91qes";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rset = null;
    String sql = "";

    /**
     * return the sadco logo
     */
    Image getSlogo() {
        String host = sadco.SadConstants.LIVE ? "sadco.ocean.gov.za" : "sadco.int.ocean.gov.za";
        Image slogo = new Image
            ("http://" + host + "/sadco-img/sadlogo.gif",
            "sadlog.gif", IVAlign.TOP, false);
        return slogo;
    } // getSlogo

    /**
     * connect to the database
     */
//    void connect2Db(CompoundItem caller) {
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = java.sql.DriverManager.getConnection
//                ("jdbc:oracle:thin:" + USER + "/" + PWD +
//                "@morph.csir.co.za:1522:etek8");
//            conn.setAutoCommit(false);
//        } catch (Exception e) {
//            caller.addItem("Logon to SADCO fails: " + e.getMessage());
//            return;
//        } // try-catch
//    } // connect2Db

    /**
     * header row
     * @param   surveyId
     */
    TableRow headRow(String pSurveyId) {
        TableRow row = new TableRow();
        row
            .addCell(new TableHeaderCell(formatStrBig(
                "<b>SURVEY SUMMARY REPORT</b>")).setColSpan(80))
            .addCell(new TableHeaderCell(formatStrBig(
                "<b>" + pSurveyId + "</b")))
            ;
        return row;
    } // headRow
    /**
     * Look up a URL parameter
     * @param   args    list of url name-value parameter pairs
     * @param   name    name of parameter to find value for
     */
    private static String getArgument(String args[], String name) {
        String prefix = name + "=";
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith(prefix) ) {
                return args[i].substring(prefix.length());
            }  //  if (args[i].startsWith(prefix) )
        }  //  for (int i = 0; i < args.length; i++)
        return "";
    } // getArgument


    /**
     * Format text strings for html
     * @param   oldStr  the text to format
     */
    private static Container formatStr(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(2));
        return newStrc;
    }  //  private static Container formatStr(String oldStr)


    /**
     * Format text strings for html (big)
     * @param   oldStr  the text to format
     */
    private static Container formatStrBig(String oldStr) {
        Container newStrc = new Container();
        newStrc.addItem(new SimpleItem(oldStr).setFontSize(3));
        return newStrc;
    } // formatStrBig


    /**
     * Return space iso null
     * @param   text    text to test for null
     */
    public String spaceIfNull(String text) {
        return (text == null ? "" : text);
    } // spaceIfNull

} // class SurvsumCommon