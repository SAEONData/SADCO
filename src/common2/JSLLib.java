package common2;

import oracle.html.*;

/**
 * A library of routines for javascript stuff.  One can set the name of the form.
 * The default is 'document.forms[0]'.
 *
 * @author  2000/04/24 - SIT Group
 * @version 2002/02/28 - Ursula von St Ange   made 'document.forms[0]' a variable.
 */
public class JSLLib {

    boolean dbg = false;
    //boolean dbg = true;

    /**
     * The name of the form that the javascript applies to.  The default is
     * 'document.forms[0]'.  If you want to change it, your class must contain the
     * following:<br>
     * <tt>JSLLib.formName = "thisForm";<br>
     * Form form = new Form("GET\" name=\"thisForm", URL);</tt>
     */
    public static String formName = "document.forms[0]";
    static final public String CR = System.getProperty("line.separator");



    /**
     * open the javascript
     */
    public static CompoundItem OpenScript() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("<SCRIPT>"+CR));
        return ci;
    } //  Open() {

    /**
     * close the javascript
     */
    static public CompoundItem CloseScript() {
        CompoundItem ci = new CompoundItem();
        ci.addItem (new SimpleItem ("</SCRIPT>"+CR));
        return ci;
    } //  Close() {

    /**
     * open a function: function
     * @param  alias  button name
     * @param  event  event for button
     */
    static public CompoundItem OpenEvent(String alias, String event) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function " + alias + "_" + event +
            "(ctl) {"+CR));
        return ci;
    } // OpenEvent

    /**
     * close a function:
     */
    static public CompoundItem CloseEvent() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("} // function"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // CloseEvent

    /**
     * call a function:
     * @param  alias  button name
     * @param  event  event for button
     */
    static public CompoundItem CallEvent(String alias, String event) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem(" if (!" + alias + "_" + event +
            //"(document.forms[0]." + alias + ")) { return false; }"+CR));
            "(" + formName + "." + alias + ")) { return false; }"+CR));
        //ci.addItem(new SimpleItem("}   // end event"+CR));
        return ci;
    } // CallEvent

    /**
     * call a validation function:
     * @param  alias  button name
     */
    static public CompoundItem CallValidate(String alias) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  if (!" + alias +
            "_Validate(ctl)) { return false; }"+CR));
        return ci;
    } // CallValidate

    /**
     * write the JSLNotNull function
     */
    static public CompoundItem RtnNotNull() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLNotNull(pctl, pmsg){"+CR));
        ci.addItem(new SimpleItem("  if (pctl.value == \"\") { alert(pmsg); pctl.focus(); return false; }"+CR));
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("} // JSLNotNull"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnNotNull

    /**
     * write the JSLSame function
     */
    static public CompoundItem RtnSame() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLSame(pctl, pctl2, pmsg){"+CR));
        ci.addItem(new SimpleItem("  if (pctl.value != pctl2.value) { alert(pmsg); pctl.focus(); return false; }"+CR));
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("} // JSLSame"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnSame

    /**
     * write the JSLCheckRange function
     */
    static public CompoundItem RtnChkRange() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLCheckRange(pctl, pval, pstyle, plowval, phival, pmsg) {"+CR));
        ci.addItem(new SimpleItem("  // used by CheckDate and CheckTime"+CR));
        ci.addItem(new SimpleItem("  var lval = \"\" + pval;"+CR));
        ci.addItem(new SimpleItem("  if (lval != \"\") {"+CR));
        ci.addItem(new SimpleItem("    var ctlval = parseInt(lval);"+CR));
        ci.addItem(new SimpleItem("    if (pstyle == 1) { // full range"+CR));
        ci.addItem(new SimpleItem("      if ( (ctlval < plowval) || (ctlval > phival)) { alert(pmsg); pctl.focus(); return false; }"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("    if (pstyle == 2) { // check low value"+CR));
        ci.addItem(new SimpleItem("      if (ctlval < plowval) { alert(pmsg); pctl.focus(); return false; }"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("    if (pstyle == 3) { // check high value"+CR));
        ci.addItem(new SimpleItem("      if (ctlval > phival) { alert(pmsg); pctl.focus(); return false; }"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkRange

    /**
     * write the JSLCheckMaxLength function
     */
    static public CompoundItem RtnChkMaxLength() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLCheckMaxLength(pctl, plen, pmsg) {"+CR));
        ci.addItem(new SimpleItem("  if (pctl.value.length > plen) {"+CR));
        ci.addItem(new SimpleItem("    alert(pmsg);"+CR));
        ci.addItem(new SimpleItem("    pctl.focus();"+CR));
        ci.addItem(new SimpleItem("    return false;"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("    return true;"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkMaxLength

    /**
     * write the JSLCheckNumScale function
     */
    static public CompoundItem RtnChkNumScale() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLCheckNumScale (pctl, pval, pscale, pmsg) {"+CR));
        ci.addItem(new SimpleItem("  // checks the number of digits after the decimal point"+CR));
        ci.addItem(new SimpleItem("  if (pval != \"\") {"+CR));
        ci.addItem(new SimpleItem("    var PointPos = pval.indexOf(\".\");"+CR));
        ci.addItem(new SimpleItem("    if (PointPos != -1) {"+CR));
        ci.addItem(new SimpleItem("      var Scale = pval.length - PointPos - 1;"+CR));
        ci.addItem(new SimpleItem("      if (Scale > pscale) {"+CR));
        ci.addItem(new SimpleItem("        alert(pmsg);"+CR));
        ci.addItem(new SimpleItem("        pctl.focus();"+CR));
        ci.addItem(new SimpleItem("        return false;"+CR));
        ci.addItem(new SimpleItem("      }"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkNumScale

    /**
     * write the JSLCheckNumPrecision function
     */
    static public CompoundItem RtnChkNumPrecision() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLCheckNumPrecision(pctl, pval, pprecision, pmsg) {"+CR));
        ci.addItem(new SimpleItem("  // checks the number of digits before the decimal point"+CR));
        ci.addItem(new SimpleItem("  if (pval != \"\") {"+CR));
        ci.addItem(new SimpleItem("    var Prec = 0;"+CR));
        ci.addItem(new SimpleItem("    var PointPos = pval.indexOf(\".\");"+CR));
        ci.addItem(new SimpleItem("    // If a decimal point was not found"+CR));
        ci.addItem(new SimpleItem("    // validate the number of digits in the whole string"+CR));
        ci.addItem(new SimpleItem("    if (PointPos == -1) {"+CR));
        ci.addItem(new SimpleItem("      Prec = pval.length;"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("    else {  // Validate the number of digits before the decimal point"+CR));
        ci.addItem(new SimpleItem("      Prec = PointPos;"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("    if (Prec > pprecision) { alert(pmsg); pctl.focus(); return false; }"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkNumPrecision

    /**
     * write the CheckNumeric function
     */
    static public CompoundItem RtnChkNumeric() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function CheckNumeric (pctl, pstr, pmsg) {"+CR));
        ci.addItem(new SimpleItem("  for (var i=0; i<pstr.length; i++ ) {"+CR));
        ci.addItem(new SimpleItem("    var ch = pstr.charAt(i)"+CR));
        ci.addItem(new SimpleItem("    if ((ch < \"0\" || \"9\" < ch) && (ch != \".\")) {"+CR));
        ci.addItem(new SimpleItem("      alert (pmsg)"+CR));
        ci.addItem(new SimpleItem("      pctl.focus()"+CR));
        ci.addItem(new SimpleItem("      return false"+CR));
        ci.addItem(new SimpleItem("      break"+CR));
        ci.addItem(new SimpleItem("    }   // if (ch ..."+CR));
        ci.addItem(new SimpleItem("  }   // for (var i ..."+CR));
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("}   // CheckNumeric"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkNumeric

    /**
     * write the CheckInteger function
     */
    static public CompoundItem RtnChkInteger() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function CheckInteger (pctl, pstr, pmsg) {"+CR));
        ci.addItem(new SimpleItem("  for (var i=0; i<pstr.length; i++ ) {"+CR));
        ci.addItem(new SimpleItem("    var ch = pstr.charAt(i)"+CR));
        ci.addItem(new SimpleItem("    if (ch < \"0\" || \"9\" < ch) {"+CR));
        ci.addItem(new SimpleItem("      alert (pmsg)"+CR));
        ci.addItem(new SimpleItem("      pctl.focus()"+CR));
        ci.addItem(new SimpleItem("      return false"+CR));
        ci.addItem(new SimpleItem("      break"+CR));
        ci.addItem(new SimpleItem("    }   // if (ch ..."+CR));
        ci.addItem(new SimpleItem("  }   // for (var i ..."+CR));
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("}   // CheckInteger"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkInteger

    /**
     * write the Y2K function (used with CheckDate & CompareDates)
     */
    static public CompoundItem RtnY2K() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function Y2K(number) { return (number < 1000) ? number + 1900 : number; }"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnY2K

    /**
     * write the PadOut function (used with CheckDate & CompareDates)
     */
    static public CompoundItem RtnPadOut() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function PadOut(number) { return (number < 10) ? '0' + number : number; }"+CR));
        return ci;
    } // RtnPadOut

    /**
     * write the CheckDate function
     */
    static public CompoundItem RtnChkDate() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function CheckDate (pctl, pmsg) {"+CR));
        ci.addItem(new SimpleItem("  if (pctl.value == \"\") { return true; }"+CR));
        ci.addItem(new SimpleItem("  fldValue = pctl.value;"+CR));
        ci.addItem(new SimpleItem("  Year = fldValue.substring(0,4)-0;"+CR));
        ci.addItem(new SimpleItem("  Month = fldValue.substring(5,7)-1;"+CR));
        ci.addItem(new SimpleItem("  Day = fldValue.substring(8,10)-0;"+CR));
        ci.addItem(new SimpleItem("  var date = new Date(Year,Month,Day);"+CR));
        ci.addItem(new SimpleItem("  if ( (Y2K(date.getYear()) == Year) &&"+CR));
        ci.addItem(new SimpleItem("       (Month == date.getMonth()) &&"+CR));
        ci.addItem(new SimpleItem("       (Day == date.getDate()) )"+CR));
        ci.addItem(new SimpleItem("    return true;"+CR));
        ci.addItem(new SimpleItem("  else {"+CR));
        ci.addItem(new SimpleItem("    alert(fldValue+\": \"+pmsg);"+CR));
        ci.addItem(new SimpleItem("    pctl.focus();"+CR));
        ci.addItem(new SimpleItem("    return false"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
//        ci.addItem(new SimpleItem("  if (!CheckInteger(pctl, Year, fldValue+\": Year not numeric\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  if (!CheckInteger(pctl, Month, fldValue+\": Month not numeric\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  else {"+CR));
//        ci.addItem(new SimpleItem("    if(!JSLCheckRange(pctl,Month,1,1,12,fldValue+\": Month out of range\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  }"+CR));
//        ci.addItem(new SimpleItem("  if (!CheckInteger(pctl, Day, fldValue+\": Day not numeric\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  else {"+CR));
//        ci.addItem(new SimpleItem("    iMonth = parseInt(Month)"+CR));
//        ci.addItem(new SimpleItem("    if (iMonth==1||iMonth==3||iMonth==5||iMonth==7||iMonth==8||iMonth==10||iMonth==12) {"+CR));
//        ci.addItem(new SimpleItem("      if(!JSLCheckRange(pctl,Day,1,1,31,fldValue+\": Day out of range\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("    }   // 31 days"+CR));
//        ci.addItem(new SimpleItem("    if (iMonth==4||iMonth==6||iMonth==9||iMonth==11) {"+CR));
//        ci.addItem(new SimpleItem("      if(!JSLCheckRange(pctl,Day,1,1,30,fldValue+\": Day out of range\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("    }   // 30 days"+CR));
//        ci.addItem(new SimpleItem("    if (iMonth==2) {"+CR));
//        ci.addItem(new SimpleItem("      iYear = parseInt(Year)"+CR));
//        ci.addItem(new SimpleItem("      iLeap = iYear / 4"+CR));
//        ci.addItem(new SimpleItem("      if (Math.ceil(iLeap)==Math.floor(iLeap)) {"+CR));
//        ci.addItem(new SimpleItem("        if(!JSLCheckRange(pctl,Day,1,1,29,fldValue+\": Day out of range\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("      }else{"+CR));
//        ci.addItem(new SimpleItem("        if(!JSLCheckRange(pctl,Day,1,1,28,fldValue+\": Day out of range\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("      }    // leap year"+CR));
//        ci.addItem(new SimpleItem("    }   // february"+CR));
//        ci.addItem(new SimpleItem("  }   // checkInteger"+CR));
//        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("}   // CheckDate"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkDate

    /**
     * write the CompareDates function
     */
    static public CompoundItem RtnCmpDates() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function CompareDates(pctl1, pctl2) {"+CR));
        ci.addItem(new SimpleItem("  // decipher start date"+CR));
        ci.addItem(new SimpleItem("  fldValue1 = pctl1.value;"+CR));
        ci.addItem(new SimpleItem("  Year1 = fldValue1.substring(0,4)-0;"+CR));
        ci.addItem(new SimpleItem("  Month1 = fldValue1.substring(5,7)-1;"+CR));
        ci.addItem(new SimpleItem("  Day1 = fldValue1.substring(8,10)-0;"+CR));
        ci.addItem(new SimpleItem("  var date1 = new Date(Year1,Month1,Day1);"+CR));
        ci.addItem(new SimpleItem("  // decipher end date"+CR));
        ci.addItem(new SimpleItem("  fldValue2 = pctl2.value;"+CR));
        ci.addItem(new SimpleItem("  Year2 = fldValue2.substring(0,4);"+CR));
        ci.addItem(new SimpleItem("  Month2 = fldValue2.substring(5,7)-1;"+CR));
        ci.addItem(new SimpleItem("  Day2 = fldValue2.substring(8,10);"+CR));
        ci.addItem(new SimpleItem("  var date2 = new Date(Year2,Month2,Day2);"+CR));
        ci.addItem(new SimpleItem("  // do checking"+CR));
        ci.addItem(new SimpleItem("  if (date2 < date1) {"+CR));
        ci.addItem(new SimpleItem("    alert (\"End Date earlier then Start Date\");"+CR));
        ci.addItem(new SimpleItem("    pctl2.focus(); "+CR));
        ci.addItem(new SimpleItem("    return false;"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("}   // CompareDates"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnCmpDates

    /**
     * write the CompareDateTimes function
     */
    static public CompoundItem RtnCmpDateTimes() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function CompareDateTimes(pctl1, pctl2, pctl3, pctl4) {"+CR));
        ci.addItem(new SimpleItem("  // decipher start date/time"+CR));
        ci.addItem(new SimpleItem("  fldValue = pctl1.value;"+CR));
        ci.addItem(new SimpleItem("  Year = fldValue.substring(0,4)-0;"+CR));
        ci.addItem(new SimpleItem("  Month = fldValue.substring(5,7)-1;"+CR));
        ci.addItem(new SimpleItem("  Day = fldValue.substring(8,10)-0;"+CR));
        ci.addItem(new SimpleItem("  fldValue = pctl2.value;"+CR));
        ci.addItem(new SimpleItem("  Hour = fldValue.substring(0,2)-0;"+CR));
        ci.addItem(new SimpleItem("  Minute = fldValue.substring(3,5)-0;"+CR));
        ci.addItem(new SimpleItem("  var date1 = new Date(Year,Month,Day,Hour,Minute);"+CR));
        ci.addItem(new SimpleItem("  // decipher end date/time"+CR));
        ci.addItem(new SimpleItem("  fldValue = pctl3.value;"+CR));
        ci.addItem(new SimpleItem("  Year = fldValue.substring(0,4);"+CR));
        ci.addItem(new SimpleItem("  Month = fldValue.substring(5,7)-1;"+CR));
        ci.addItem(new SimpleItem("  Day = fldValue.substring(8,10);"+CR));
        ci.addItem(new SimpleItem("  fldValue = pctl4.value;"+CR));
        ci.addItem(new SimpleItem("  Hour = fldValue.substring(0,2)-0;"+CR));
        ci.addItem(new SimpleItem("  Minute = fldValue.substring(3,5)-0;"+CR));
        ci.addItem(new SimpleItem("  var date2 = new Date(Year,Month,Day,Hour,Minute);"+CR));
        ci.addItem(new SimpleItem("  // do checking"+CR));
        ci.addItem(new SimpleItem("  if (date2 < date1) {"+CR));
        ci.addItem(new SimpleItem("    alert (\"End Date/time earlier then Start Date/time\");"+CR));
        ci.addItem(new SimpleItem("    pctl3.focus(); "+CR));
        ci.addItem(new SimpleItem("    return false;"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("}   // CompareDateTimes"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnCmpDates

    /**
     * write the CheckTime function
     */
    static public CompoundItem RtnChkTime() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function CheckTime (pctl, pmsg) {"+CR));
        ci.addItem(new SimpleItem("  if (pctl.value == \"\") { return true; }"+CR));
        ci.addItem(new SimpleItem("  fldValue = pctl.value;"+CR));
        ci.addItem(new SimpleItem("  Hour = fldValue.substring(0,2);"+CR));
        ci.addItem(new SimpleItem("  Minute = fldValue.substring(3,5);"+CR));
        ci.addItem(new SimpleItem("  var time = new Date(0,0,0,Hour,Minute,0);"+CR));
        ci.addItem(new SimpleItem("  if ( (time.getHours() == Hour) &&"+CR));
        ci.addItem(new SimpleItem("       (Minute == time.getMinutes()) )"+CR));
        ci.addItem(new SimpleItem("    return true;"+CR));
        ci.addItem(new SimpleItem("  else { "+CR));
        ci.addItem(new SimpleItem("    alert(fldValue+\": \"+pmsg);"+CR));
        ci.addItem(new SimpleItem("    pctl.focus();"+CR));
        ci.addItem(new SimpleItem("    return false;"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
//        ci.addItem(new SimpleItem("  Second = fldValue.substring(6,8);"+CR));
//        ci.addItem(new SimpleItem("  if (!CheckInteger(pctl, Hour, fldValue+\": Hours not numeric\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  else {"+CR));
//        ci.addItem(new SimpleItem("    if(!JSLCheckRange(pctl,Hour,1,0,23,fldValue+\": Hours out of range\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  }"+CR));
//        ci.addItem(new SimpleItem("  if (!CheckInteger(pctl, Minute, fldValue+\": Minutes not numeric\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  else {"+CR));
//        ci.addItem(new SimpleItem("    if(!JSLCheckRange(pctl,Minute,1,0,59,fldValue+\": Minutes out of range\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  }"+CR));
//        ci.addItem(new SimpleItem("  if (!CheckInteger(pctl, Second, fldValue+\": Seconds not numeric\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  else {"+CR));
//        ci.addItem(new SimpleItem("    if(!JSLCheckRange(pctl,Second,1,0,59,fldValue+\": Seconds out of range\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("  }"+CR));
//        ci.addItem(new SimpleItem("  return true;"+CR));
        ci.addItem(new SimpleItem("}   // CheckTime"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkTime

    /**
     * write the JSLStripMask function
     */
    static public CompoundItem RtnStripMask() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLStripMask(p_val) {"+CR));
        ci.addItem(new SimpleItem("  // called by JSLToNumber"+CR));
        ci.addItem(new SimpleItem("  if (p_val == \"\") { return \"\"; }"+CR));
        ci.addItem(new SimpleItem("  var str = p_val;"+CR));
        ci.addItem(new SimpleItem("  str = JSLReplace(str, \" \");"+CR));
        ci.addItem(new SimpleItem("  str = JSLReplace(str, \",\");"+CR));
        ci.addItem(new SimpleItem("  str = JSLReplace(str, \"$\");"+CR));
        ci.addItem(new SimpleItem("  str = JSLReplace(str, \"USD\");"+CR));
        ci.addItem(new SimpleItem("  if ((str.substring(0, 1) == \"<\") && (str.substring(str.length -1, str.length) == \">\")) {"+CR));
        ci.addItem(new SimpleItem("    str = \"-\" + str.substring(1, str.length - 1);"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  if (str.substring(str.length -1, str.length) == \"-\") {"+CR));
        ci.addItem(new SimpleItem("    str = \"-\" +  str.substring(0, str.length - 1);"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  if (str.substring(str.length -1, str.length) == \"+\") {"+CR));
        ci.addItem(new SimpleItem("    str = str.substring(0, str.length - 1);"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  return str;"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnStripMask

    /**
     * write the JSLToNumber function
     */
    static public CompoundItem RtnToNumber() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLToNumber(p_val) {"+CR));
        ci.addItem(new SimpleItem("  var lval = JSLStripMask(p_val);"+CR));
        ci.addItem(new SimpleItem("  if (lval == \"\") { return \"\"; }"+CR));
        ci.addItem(new SimpleItem("  else { return parseFloat(lval); }"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnToNumber

    /**
     * write the JSLCheckConstraint function
     */
    static public CompoundItem RtnChkConstraint() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLCheckConstraint(pconstraint, pmsg) {"+CR));
        ci.addItem(new SimpleItem("  if (!(pconstraint)) { alert(pmsg); return false; }"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkConstraint

    /**
     * write the JSLRadioValue function
     */
    static public CompoundItem RtnRadioValue() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLRadioValue(pctl) {"+CR));
        ci.addItem(new SimpleItem("  var i;"+CR));
        ci.addItem(new SimpleItem("  for (i=0;i<pctl.length;i++) {"+CR));
        ci.addItem(new SimpleItem("    if (pctl[i].checked) {"+CR));
        ci.addItem(new SimpleItem("      return pctl[i].value;"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  return \"\";"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnChkConstraint

    /**
     * write the JSLGetValue function
     */
    static public CompoundItem RtnGetValue() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLGetValue(pctl, ptype, pfalse) {"+CR));
        ci.addItem(new SimpleItem("  var i = 0;"+CR));
        ci.addItem(new SimpleItem("  if (ptype == null) { return pctl.value; }"+CR));
        ci.addItem(new SimpleItem("  if (ptype == \"CHECK\") {"+CR));
        ci.addItem(new SimpleItem("    if (pctl.checked) { return pctl.value; }"+CR));
        ci.addItem(new SimpleItem("    else { return pfalse; }"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  if (ptype == \"RADIO\") {"+CR));
        ci.addItem(new SimpleItem("    for (i=0;i<pctl.length;i++) {"+CR));
        ci.addItem(new SimpleItem("      if (pctl[i].checked) { return pctl[i].value; }"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("    return \"\";"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  if (ptype == \"LIST\") {"+CR));
        ci.addItem(new SimpleItem("    if (pctl.selectedIndex >= 0) {"+CR));
        ci.addItem(new SimpleItem("      if (pctl.options[pctl.selectedIndex].value != \"\") {"+CR));
        ci.addItem(new SimpleItem("        return pctl.options[pctl.selectedIndex].value;"+CR));
        ci.addItem(new SimpleItem("      }"+CR));
        ci.addItem(new SimpleItem("      else { return pctl.options[pctl.selectedIndex].text; }"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("    else { return \"\"; }"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } //  RtnGetValue

    /**
     * write the JSLReplace function
     */
    static public CompoundItem RtnReplace() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLReplace(pstr1, pstr2, pstr3) {"+CR));
        ci.addItem(new SimpleItem("  // called by JSLStripMask"+CR));
        ci.addItem(new SimpleItem("  if (pstr1 != \"\") {"+CR));
        ci.addItem(new SimpleItem("    var rtnstr = \"\";"+CR));
        ci.addItem(new SimpleItem("    var searchstr = pstr1;"+CR));
        ci.addItem(new SimpleItem("    var addlen = pstr2.length;"+CR));
        ci.addItem(new SimpleItem("    var index = pstr1.indexOf(pstr2);"+CR));
        ci.addItem(new SimpleItem("    while ((index != -1) && (searchstr != \"\")) {"+CR));
        ci.addItem(new SimpleItem("      rtnstr = rtnstr + searchstr.substring(0, index);"+CR));
        ci.addItem(new SimpleItem("      if (pstr3 != null) {"+CR));
        ci.addItem(new SimpleItem("        rtnstr = rtnstr + pstr3;"+CR));
        ci.addItem(new SimpleItem("      }"+CR));
        ci.addItem(new SimpleItem("      searchstr = searchstr.substring(index + addlen, searchstr.length);"+CR));
        ci.addItem(new SimpleItem("      if (searchstr != \"\") {"+CR));
        ci.addItem(new SimpleItem("         index = searchstr.indexOf(pstr2);"+CR));
        ci.addItem(new SimpleItem("      }"+CR));
        ci.addItem(new SimpleItem("      else { index = -1; }"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("    return (rtnstr + searchstr);"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  else {"+CR));
        ci.addItem(new SimpleItem("    return \"\";"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } //  RtnReplace

    /**
     * write the JSLLower function
     */
    static public CompoundItem RtnLower() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLLower(pctl) {"+CR));
        ci.addItem(new SimpleItem("  pctl.value = pctl.value.toLowerCase();"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnLower

    /**
     * write the JSLUpper function
     */
    static public CompoundItem RtnUpper() {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("function JSLUpper(pctl) {"+CR));
        ci.addItem(new SimpleItem("  pctl.value = pctl.value.toUpperCase();"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem(CR));
        return ci;
    } // RtnUpper

    /**
     * call the JSLCheckRange function
     * @param  ctl     name
     * @param  val     value
     * @param  lowval  lowest value
     * @param  hival   highest value
     * @param  msg     error message
     */
    static public CompoundItem CallChkRange
        (String ctl, String val, String lowval, String hival, String msg) {
        CompoundItem ci = new CompoundItem();
        if (lowval == null) {
            ci.addItem(new SimpleItem(
                "  if (!JSLCheckRange(" + ctl + ", " + val + ", 3, 0, " +
                hival + ", \"" + msg + "\")) { return false }"+CR));
        } else if (hival == null) {
            ci.addItem(new SimpleItem(
                "  if (!JSLCheckRange(" + ctl + ", " + val + ", 2, " +
                lowval + ", 0, \"" + msg + "\")) { return false }"+CR));
        } else {
            ci.addItem(new SimpleItem(
                "  if (!JSLCheckRange(" + ctl + ", " + val + ", 1, " +
                lowval + ", " + hival + " \"" + msg + "\")) { return false }"+CR));
        }
        return ci;
    } // CallChkRange

    /**
     * call the JSLCheckMaxLength function
     * @param  ctl     name
     * @param  length  length
     * @param  msg     error message
     */
    static public CompoundItem CallChkMaxLength
        (String ctl, int length, String msg) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  if (!JSLCheckMaxLength(" + ctl + ", " +
            length + ", \"" + msg + "\")) { return false }"+CR));
        return ci;
    } // CallChkMaxLength

    /**
     * call the JSLCheckNumPrecision function
     * @param  ctl        name
     * @param  prompt     prompt
     * @param  precision  number of digits before decimal point
     */
    static public CompoundItem CallChkNumPrecision
        (String ctl, String prompt, int precision) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem(
            "  if (!JSLCheckNumPrecision(" + formName + "." + ctl +
            ", JSLStripMask(JSLGetValue(" + formName + "." + ctl +  ")), " +
            precision + ", \"Check " + prompt +
            ": Value cannot have more than " + precision +
            " digits before the decimal point\")) { return false } "+CR));
        return ci;
    } // CallChkNumPrecision

    /**
     * call the JSLCheckNumScale function
     * @param  ctl    name
     * @param  prompt prompt
     * @param  scale  number of digits after decimal point
     */
    static public CompoundItem CallChkNumScale
        (String ctl, String prompt, int scale) {
        CompoundItem ci = new CompoundItem();
        //ci.addItem(new SimpleItem("  if (!JSLCheckNumScale(" + ctl + ", " +
        //    val + ", " + scale + ", \"" + msg + "\")) { return false }"+CR));
        ci.addItem(new SimpleItem(
            "  if (!JSLCheckNumScale(" + formName + "." + ctl +
            ", JSLStripMask(JSLGetValue(" + formName + "." + ctl + ")), " +
            scale +             ", \"Check " + prompt +
            ": Value cannot contain more than " + scale +
            " decimal places\")) { return false } "+CR));
        return ci;
    } // CallChkNumScale

    /**
     * call the JSLCheckConstraint function
     * @param  ctl         name
     * @param  constraint  constraint name
     * @param  msg         error message
     * @param  indent      true/false
     */
    static public CompoundItem CallChkConstraint
        (String ctl, String constraint, String msg, boolean indent) {
        CompoundItem ci = new CompoundItem();
        if (indent) {
            ci.addItem(new SimpleItem("    if (!JSLCheckConstraint(" + constraint +
                ", \"" + msg + "\")) { return false }"+CR));
        } else {
            ci.addItem(new SimpleItem("  if (!JSLCheckConstraint(" + constraint +
                ", \"" + msg + "\")) { return false }"+CR));
        }
        return ci;
    } // CallChkConstraint

    /**
     * call the JSLNotNull function
     * @param  ctl     name
     * @param  prompt  prompt for control
     */
    static public CompoundItem CallNotNull (String ctl, String prompt) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  if (!JSLNotNull(" + formName + "." + ctl +
            ", \"Enter " + prompt +
            ": A value must be entered\")) { return false }"+CR));
        return ci;
    } // CallNotNull

    /**
     * call the JSLSame function
     * @param  ctl     name
     * @param  ctl2    name2
     * @param  prompt  prompt for control
     */
    static public CompoundItem CallSame (String ctl, String ctl2, String prompt) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  if (!JSLSame(" + formName + "." + ctl + "," +
            formName + "." + ctl2 + ", \"The two " + prompt +
            " differ\")) { return false }"+CR));
        return ci;
    } // CallSame

    /**
     * call the CheckDate function
     * @param  ctl     name
     */
    static public CompoundItem CallChkDate (String ctl, String prompt) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  if (!CheckDate (" + formName + "." + ctl +
            ",\"" + prompt + ": Invalid Date\")) { return false }"+CR));
        return ci;
    } // CallChkDate

    /**
     * call the CompareDates function
     * @param  ctl1    date1 name
     * @param  ctl2    date2 name
     */
    static public CompoundItem CallCmpDates (String ctl1, String ctl2) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  if (!CompareDates (" + formName + "." +
            ctl1 + "," + formName + "." + ctl2 +")) { return false }"+CR));
        return ci;
    } // CallChkDate

    /**
     * call the CompareDateTimes function
     * @param  ctl1    date1 name
     * @param  ctl2    time1 name
     * @param  ctl3    date2 name
     * @param  ctl4    time2 name
     */
    static public CompoundItem CallCmpDateTimes
            (String ctl1, String ctl2, String ctl3, String ctl4) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  if (!CompareDateTimes (" + formName + "." +
            ctl1 + "," + formName + "." + ctl2 + "," + formName + "." +
            ctl3 + "," + formName + "." + ctl4 +")) { return false }"+CR));
        return ci;
    } // CallCmpDateTimes

    /**
     * call the CheckTime function
     * @param  ctl     name
     */
    static public CompoundItem CallChkTime (String ctl, String prompt) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  if (!CheckTime (" + formName + "." + ctl +
            ",\"" + prompt + ": Invalid Time\")) { return false }"+CR));
        return ci;
    } // CallChkTime

    /**
     * write stuff for a standard submit
     * @param  setZAction  true/false
     */
    static public CompoundItem StandardSubmit (boolean setZAction) {
        CompoundItem ci = new CompoundItem();
        if (setZAction) {
            ci.addItem(new SimpleItem("  " + formName + ".Z_ACTION.value = " +
                "ctl.value; " + formName + ".submit();"+CR));
        } else {
            ci.addItem(new SimpleItem("  " + formName + ".submit();"+CR));
        }
        return ci;
    } // StandardSubmit

    /**
     * write stuff for a standard submit
     * @param  msg  message
     */
    static public CompoundItem VerifyDelete (String msg) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  if (!confirm(\"" + msg +
            "\")) { return false }"+CR));
        ci.addItem(new SimpleItem("  " + formName + ".Z_ACTION.value = " +
            "\"VerifiedDelete\";"+CR));
        ci.addItem(new SimpleItem("  " + formName + ".submit();"+CR));
        return ci;
    } // VerifyDelete

    /**
     * call the JSLUpper function
     * @param  ctl     name
     */
    static public CompoundItem CallUpper (String ctl) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  JSLUpper(" + ctl + ");"+CR));
        return ci;
    } // CallUpper

    /**
     * call the JSLLower function
     * @param  ctl     name
     */
    static public CompoundItem CallLower (String ctl) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("  JSLLower(" + ctl + ");"+CR));
        return ci;
    } // CallLower


    /**
     * write a button with an 'onClick' event
     * @param  value   button value
     * @param  alias   button alias
     */
    static public CompoundItem OnClickButton (String value, String alias) {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("<input type =\"button\" value = \"" + value +
            "\" " + "onClick=\"" + alias + "_OnClick(this)\">"+CR));
        return ci;
    } // OnClickButton

    /**
     * call the RtnDynamicSelect function
     * @param  form     form name
     * @param  main     main select box name
     * @param  sub      sub select box name
     */
    static public CompoundItem RtnDynamicSelect (String form, String main, String sub) {
        CompoundItem ci = new CompoundItem();
        //ci.addItem(new SimpleItem("/******** array declaration... it holds all of the data for the menus ****/"+CR));
        //ci.addItem(new SimpleItem("var menu = new Array ("+CR));
        //ci.addItem(new SimpleItem("\"Javascript*http://www.radix.net/~jtd/test30/java1.htm|Java Page#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://ds.dial.pipex.com/town/avenue/xio12/wax/|HOT WAX#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.wsabstract.com/cutpastejava.htm|Website Abstraction Free JavaScripts!#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.webteacher.com/javatour/framehol.htm|JavaScript for the Total Non-Programmer#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.developer.com/directories/pages/dir.javascript.html/|Developer.com#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://javascriptsource.com/|The JavaScript Source#\" + "+CR));
        //ci.addItem(new SimpleItem("  \"http://members.aol.com/MHall75819/index.html|Advanced Webpage Design#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.coolnerds.com/webauth/jscript/Default.htm|Coolnerds - A Webhead's Wonderland#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://med-amsa.bu.edu/server/js/|JavaScript Reference Manual#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.loran.com/reference/javascript-ref/contents.htm|JavaScript Reference#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://developer.netscape.com/docs/manuals/communicator/jsguide4/index.htm|JavaScript Guide#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.javascriptg.com/completelisting.html|Welcome! - JavaScript Galore#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.infohiway.com/javascript/indexf.htm|Cut-N-Paste JavaScript#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.jsworld.com/|JavaScript World --> Welcome!#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.webconn.com/java/javascript/intro/|Voodoo's Intro to JavaScript#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.geocities.com/SiliconValley/7116/|The JavaScript Planet#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.hotwired.com/webmonkey/javascript/?tw=javascript|Webmonkey: javascript collection#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.macad.org/referral/sair2/jsrefsrc.html|MACAD's SAIR -- JavaScript Database\","+CR));
        //ci.addItem(new SimpleItem("\"Search Engines*http://home.netscape.com/home/internet-search.html|Netscape Search#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.excite.com|Excite#http://www.infoseek.com|Infoseek#\"+"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.lycos.com|Lycos#http://www.yahoo.com|Yahoo!#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.inference.com/|Inference Corporation Home Page\","+CR));
        //ci.addItem(new SimpleItem("\"Computers and Technology*http://www.atvantage.com/usrbin/atvantage|@vantage#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.bonzi.com/netscape/voicenet.htm|BONZI Voice Email#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.techweb.com/|CMP TechWeb#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.pcmag.com|PC Magazine Online#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.wired.com/news/|Wired News\","+CR));
        //ci.addItem(new SimpleItem("\"General News*http://www.abcnews.com|ABCNEWS.com#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.cnn.com|CNN Interactive#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.nytimes.com|The New York Times on the Web#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.usatoday.com|USA Today#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.abcnews.com/weather/OLM.html|ABCNews.com AccuWeather\","+CR));
        //ci.addItem(new SimpleItem("\"Shopping*http://www.amazon.com/|Amazon.com Bookstore#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.catalogsite.com/|Catalog Site#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.cdnow.com|CDnow#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.sharperimage.com|The Sharper Image Catalog#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.surplusdirect.com|Surplus Direct#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.onsale.com|Welcome to ONSALE!\","+CR));
        //ci.addItem(new SimpleItem("\"Shareware/Freeware*http://hotfiles.zdnet.com/|ZDNet Software Library#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://tucows.holler.net/|Welcome to TUCOWS#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.shareware.com/|Shareware.com\","+CR));
        //ci.addItem(new SimpleItem("\"Graphics*http://www.lysator.liu.se/lothlorien/lothlorien.html|Lothlorien, Amateur fantasy art gallery#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.ssanimation.com/|Entering SS Animation...#\" +"+CR));
        //ci.addItem(new SimpleItem("  \"http://www.enchantress.net/fantasy/index.shtml|Fantasyland Graphics\");"+CR));
        ci.addItem(new SimpleItem("/****** this object holds all of the key data **********/"+CR));
        ci.addItem(new SimpleItem("function Link(name, val, def)"+CR));
        ci.addItem(new SimpleItem("{"+CR));
        ci.addItem(new SimpleItem("  this.name = name;"+CR));
        ci.addItem(new SimpleItem("  this.val  = val;"+CR));
        ci.addItem(new SimpleItem("  this.def  = def;"+CR));
        ci.addItem(new SimpleItem("  this.title = new Array();"+CR));
        ci.addItem(new SimpleItem("  this.url = new Array();"+CR));
        ci.addItem(new SimpleItem("  this.def2 = new Array();"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem("/**************************************************************/"+CR));
        ci.addItem(new SimpleItem("var names      = new Array ();"+CR));
        ci.addItem(new SimpleItem("var temp       = new Array ();"+CR));
        ci.addItem(new SimpleItem("var temp2      = new Array ();"+CR));
        ci.addItem(new SimpleItem("var temp3      = new Array ();"+CR));
        ci.addItem(new SimpleItem("var link       = new Link ();"+CR));
        ci.addItem(new SimpleItem("var final_list = new Array ();"+CR));
        ci.addItem(new SimpleItem("/*****************************************************************/"+CR));
        ci.addItem(new SimpleItem("function stringSplit ( string, delimiter ) {"+CR));
        ci.addItem(new SimpleItem("  if ( string == null || string == \"\" ) {"+CR));
        ci.addItem(new SimpleItem("    return null ;"+CR));
        ci.addItem(new SimpleItem("  } else if ( string.split != null ) {"+CR));
        ci.addItem(new SimpleItem("    return string.split ( delimiter ) ;"+CR));
        ci.addItem(new SimpleItem("  } else {"+CR));
        ci.addItem(new SimpleItem("    var ar = new Array() ;"+CR));
        ci.addItem(new SimpleItem("    var i = 0 ;"+CR));
        ci.addItem(new SimpleItem("    var start = 0 ;"+CR));
        ci.addItem(new SimpleItem("    while( start >= 0 && start < string.length ) {"+CR));
        ci.addItem(new SimpleItem("      var end = string.indexOf ( delimiter, start ) ;"+CR));
        ci.addItem(new SimpleItem("      if( end >= 0 ) {"+CR));
        ci.addItem(new SimpleItem("        ar[i++] = string.substring ( start, end ) ;"+CR));
        ci.addItem(new SimpleItem("        start = end+1 ;"+CR));
        ci.addItem(new SimpleItem("      } else {"+CR));
        ci.addItem(new SimpleItem("        ar[i++] = string.substring ( start, string.length ) ;"+CR));
        ci.addItem(new SimpleItem("        start = -1 ;"+CR));
        ci.addItem(new SimpleItem("      }"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("    return ar ;"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem("/**************************************************************/"+CR));
        ci.addItem(new SimpleItem("function updateMenus ( what ) {"+CR));
        ci.addItem(new SimpleItem("  var n = what.selectedIndex;"+CR));
        ci.addItem(new SimpleItem("  var j = 0;"+CR));
        ci.addItem(new SimpleItem("  what.form."+sub+".length = final_list[n].title.length;"+CR));
        ci.addItem(new SimpleItem("  for (var x = 0; x < what.form."+sub+".length; x++) {"+CR));
        ci.addItem(new SimpleItem("    what.form."+sub+".options[x].text = final_list[n].title[x];"+CR));
        ci.addItem(new SimpleItem("    what.form."+sub+".options[x].value = final_list[n].url[x]; "+CR));
        ci.addItem(new SimpleItem("    what.form."+sub+".options[x].selected = final_list[n].def2[x]; "+CR));
        ci.addItem(new SimpleItem("    if (what.form."+sub+".options[x].selected == true) j = x; "+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  what.form."+sub+".selectedIndex = j;"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem("/**************************************************************/"+CR));
        ci.addItem(new SimpleItem("function give_names () {"+CR));
        ci.addItem(new SimpleItem("  var j = 0;"+CR));
        ci.addItem(new SimpleItem("  document."+form+"."+main+".length = names.length;"+CR));
        ci.addItem(new SimpleItem("  for (var i=0; i<names.length; i++) {"+CR));
        ci.addItem(new SimpleItem("    document."+form+"."+main+".options[i].text = final_list[i].name;"+CR));
        ci.addItem(new SimpleItem("    document."+form+"."+main+".options[i].value = final_list[i].val;"+CR));
        ci.addItem(new SimpleItem("    document."+form+"."+main+".options[i].selected = final_list[i].def;"+CR));
        ci.addItem(new SimpleItem("    if (document."+form+"."+main+".options[i].selected == true) j = i;"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  document."+form+"."+sub+".length = final_list[j].title.length;"+CR));
        ci.addItem(new SimpleItem("  for (var x=0; x<final_list[j].url.length; x++) {"+CR));
        ci.addItem(new SimpleItem("    document."+form+"."+sub+".options[x].text = final_list[j].title[x];"+CR));
        ci.addItem(new SimpleItem("    document."+form+"."+sub+".options[x].value = final_list[j].url[x];"+CR));
        ci.addItem(new SimpleItem("    document."+form+"."+sub+".options[x].selected = final_list[j].def2[x];"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        ci.addItem(new SimpleItem("/**************************************************************/"+CR));
        ci.addItem(new SimpleItem("function createMenus () {"+CR));
        ci.addItem(new SimpleItem("  for ( var i=0; i<menu.length; i++ ) {"+CR));
        ci.addItem(new SimpleItem("    names[i] = stringSplit (menu[i], '*' );"+CR));
        ci.addItem(new SimpleItem("    temp3[i] = stringSplit(names[i][0], '|');"+CR));
        ci.addItem(new SimpleItem("    if (temp3[i][2] == null) temp3[i][2] = false;"+CR));
        ci.addItem(new SimpleItem("    link = new Link(temp3[i][0], temp3[i][1], temp3[i][2]);"+CR));
        ci.addItem(new SimpleItem("    final_list[i] = link;"+CR));
        ci.addItem(new SimpleItem("    temp[i] = stringSplit(names[i][1], '#');"+CR));
        ci.addItem(new SimpleItem("    for (var x=0; x<temp[i].length; x++) {"+CR));
        ci.addItem(new SimpleItem("      temp2[x]  = stringSplit( temp[i][x], '|' );"+CR));
        ci.addItem(new SimpleItem("      if (temp2[x] != null) {"+CR));
        ci.addItem(new SimpleItem("        if (temp2[x][2] == null) temp2[x][2] = false;"+CR));
        ci.addItem(new SimpleItem("        final_list[i].url[x] = temp2[x][0];"+CR));
        ci.addItem(new SimpleItem("        final_list[i].title[x] = temp2[x][1];"+CR));
        ci.addItem(new SimpleItem("        final_list[i].def2[x] = temp2[x][2];"+CR));
        ci.addItem(new SimpleItem("      }"+CR));
        ci.addItem(new SimpleItem("    }"+CR));
        ci.addItem(new SimpleItem("  }"+CR));
        ci.addItem(new SimpleItem("  give_names();"+CR));
        ci.addItem(new SimpleItem("}"+CR));
        return ci;
    } // RtnDynamicSelect

//    static public CompoundItem CallChkConstraint
//        (String ctl, String msg, boolean indent) {
//        CompoundItem ci = new CompoundItem();
//        ci.addItem(new SimpleItem(CR));
//        return ci;
//    } // CallChkConstraint

    /**
     * write <body onLoad="createMenus()" for dynamic select boxes
     */
    static public CompoundItem BodyOnload () {
        CompoundItem ci = new CompoundItem();
        ci.addItem(new SimpleItem("<body onLoad=\"createMenus()\">"+CR));
        return ci;
    } // BodyOnload

//    static public CompoundItem CallChkConstraint
//        (String ctl, String msg, boolean indent) {
//        CompoundItem ci = new CompoundItem();
//        ci.addItem(new SimpleItem(CR));
//        return ci;
//    } // CallChkConstraint

//  public static void main (String[] args){
//    HtmlHead hd = new HtmlHead ("QueryView2");
//    HtmlBody ci = new HtmlBody ();
//    HtmlPage hp = new HtmlPage(hd, ci);
//
//    // set background colour and centered heading
//    ci.setBackgroundColor ("80FFFF");
//    ci.addItem(new SimpleItem("<center>"+CR));
//    ci.addItem(new SimpleItem("Javascript function testing").setHeading(1));
//    ci.addItem(SimpleItem.Paragraph);
//    ci.addItem(SimpleItem.Paragraph);
//
//
//    // add java script stuff
//        ci.addItem(new SimpleItem("function INPUT_Validate(ctl) {"+CR));
//        ci.addItem(new SimpleItem("   if (!JSLNotNull(document.forms[0].file,\"Enter file name: A value must be entered\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("   if (!JSLNotNull(document.forms[0].email,\"Enter e-mail address: A value must be entered\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("   JSLUpper (document.forms[0].upper)"+CR));
//        ci.addItem(new SimpleItem("   JSLLower (document.forms[0].lower)"+CR));
//        ci.addItem(new SimpleItem("   if (!CheckDate (document.forms[0].date)) { return false }"+CR));
//        ci.addItem(new SimpleItem("   if (!CheckTime (document.forms[0].time)) { return false }"+CR));
//        ci.addItem(new SimpleItem("   if (!CheckNumeric (document.forms[0].numeric,document.forms[0].numeric.value," +
//                                    "\"Check numeric: Value contains non-numeric chars\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("   if (!CheckInteger (document.forms[0].integer,document.forms[0].integer.value," +
//                                    "\"Check integer: Value contains non-integer chars\")) { return false }"+CR));
//        ci.addItem(new SimpleItem("   if (!JSLCheckNumScale(document.forms[0].ChkNumScale," +
//                                   " JSLStripMask(JSLGetValue(document.forms[0].ChkNumScale)), 0, " +
//                                   "\"Check CheckNumScale (0): Value cannot contain decimal places\"))" +
//                                   " { return false }"));
//        ci.addItem(new SimpleItem("   if (!JSLCheckNumScale(document.forms[0].ChkNumScale2," +
//                                   " JSLStripMask(JSLGetValue(document.forms[0].ChkNumScale2)), 2, " +
//                                   "\"Check CheckNumScale (2): Value cannot contain more than 2 decimal places\"))" +
//                                   " { return false }"));
//        ci.addItem(new SimpleItem("   if (!JSLCheckNumPrecision(document.forms[0].ChkNumPrec," +
//                                    " JSLStripMask(JSLGetValue(document.forms[0].ChkNumPrec)), 5, " +
//                                    "\"Check CheckNumPrecision (5): Value cannot have more than 5 digits before the decimal point\"))" +
//                                    " { return false } "+CR));
//        ci.addItem(new SimpleItem("   return true;"+CR));
//        ci.addItem(new SimpleItem("}"+CR));
//        ci.addItem(new SimpleItem("function btnIFS_OnClick(ctl) {"+CR));
//        ci.addItem(new SimpleItem("   if (!INPUT_Validate(ctl)) { return false; }"+CR));
//        ci.addItem(new SimpleItem("   document.forms[0].submit();"+CR));
//        ci.addItem(new SimpleItem("   return true;"+CR));
//        ci.addItem(new SimpleItem("}"+CR));
//        ci.addItem(new SimpleItem("//-->"+CR));
//        ci.addItem(new SimpleItem("</SCRIPT>"+CR));
//
//    // add the form
//    Form form = new Form ("post", "/edm-cgi-bin/test.sh");
//
//    // create the table with rows
//    DynamicTable tab2 = new DynamicTable(2);
//    TableRow row0 = new TableRow ();
//    row0.setIVAlign (1);
//    TableDataCell tdc0 = new TableDataCell (new SimpleItem("Enter file name:").setBold());
//    tdc0.setHAlign(3);
//    row0.addCell (tdc0)
//        .addCell (new TableDataCell (new TextField ("file")));
//    tab2.addRow (row0);
//
//    TableRow row1 = new TableRow ();
//    row1.setIVAlign (1);
//    TableDataCell tdc1 = new TableDataCell (new SimpleItem("Enter e-mail address:").setBold());
//    tdc1.setHAlign(3);
//    row1.addCell (tdc1)
//        .addCell (new TableDataCell (new TextField ("email",30,30,"uvstange@csir.co.za")));
//    tab2.addRow (row1);
//
//    TableRow row2 = new TableRow ();
//    row2.setIVAlign (1);
//    TableDataCell tdc2 = new TableDataCell (new SimpleItem("Test MakeUpper:").setBold());
//    tdc2.setHAlign(3);
//    row2.addCell (tdc2)
//        .addCell (new TableDataCell (new TextField ("upper")));
//    tab2.addRow (row2);
//
//    TableRow row3 = new TableRow ();
//    row3.setIVAlign (1);
//    TableDataCell tdc3 = new TableDataCell (new SimpleItem("Test MakeLower:").setBold());
//    tdc3.setHAlign(3);
//    row3.addCell (tdc3)
//        .addCell (new TableDataCell (new TextField ("lower")));
//    tab2.addRow (row3);
//
//    TableRow row4 = new TableRow ();
//    row4.setIVAlign (1);
//    TableDataCell tdc4 = new TableDataCell (new SimpleItem("Check date (yyyy-mm-dd):").setBold());
//    tdc4.setHAlign(3);
//    row4.addCell (tdc4)
//        .addCell (new TableDataCell (new TextField ("date")));
//    tab2.addRow (row4);
//
//    TableRow row5 = new TableRow ();
//    row5.setIVAlign (1);
//    TableDataCell tdc5 = new TableDataCell (new SimpleItem("Check time (hh:mi:ss):").setBold());
//    tdc5.setHAlign(3);
//    row5.addCell (tdc5)
//        .addCell (new TableDataCell (new TextField ("time")));
//    tab2.addRow (row5);
//
//    TableRow row6 = new TableRow ();
//    row6.setIVAlign (1);
//    TableDataCell tdc6 = new TableDataCell (new SimpleItem("Check numeric:").setBold());
//    tdc6.setHAlign(3);
//    row6.addCell (tdc6)
//        .addCell (new TableDataCell (new TextField ("numeric")));
//    tab2.addRow (row6);
//
//    TableRow row7 = new TableRow ();
//    row7.setIVAlign (1);
//    TableDataCell tdc7 = new TableDataCell (new SimpleItem("Check integer:").setBold());
//    tdc7.setHAlign(3);
//    row7.addCell (tdc7)
//        .addCell (new TableDataCell (new TextField ("integer")));
//    tab2.addRow (row7);
//
//    TableRow row8 = new TableRow ();
//    row8.setIVAlign (1);
//    TableDataCell tdc8 = new TableDataCell (new SimpleItem("Check ChkNumScale (0):").setBold());
//    tdc8.setHAlign(3);
//    row8.addCell (tdc8)
//        .addCell (new TableDataCell (new TextField ("ChkNumScale")));
//    tab2.addRow (row8);
//
//    TableRow row9 = new TableRow ();
//    row9.setIVAlign (1);
//    TableDataCell tdc9 = new TableDataCell (new SimpleItem("Check ChkNumScale (2):").setBold());
//    tdc9.setHAlign(3);
//    row9.addCell (tdc9)
//        .addCell (new TableDataCell (new TextField ("ChkNumScale2")));
//    tab2.addRow (row9);
//
//    TableRow row10 = new TableRow ();
//    row10.setIVAlign (1);
//    TableDataCell tdc10 = new TableDataCell (new SimpleItem("Check CheckNumPrecision (5):").setBold());
//    tdc10.setHAlign(3);
//    row10.addCell (tdc10)
//        .addCell (new TableDataCell (new TextField ("ChkNumPrec")));
//    tab2.addRow (row10);
//
//    form.addItem(tab2);
//
//    form.addItem(SimpleItem.Paragraph);
//    form.addItem(SimpleItem.Paragraph);
//    form.addItem(new SimpleItem("<input type =\"button\" value = \"Submit\" onClick=\"btnIFS_OnClick(this)\">"+CR));
//    form.addItem(new Reset ());
//
//    ci.addItem(form);
//    ci.addItem(new SimpleItem("\n</center>"+CR));
//
//    hp.printHeader();
//    hp.print();
//  }   // main


}   // JSLLib

