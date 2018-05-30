
package common2;

import oracle.html.*;

/**
 * This class creates the menu items for two types of menus.  The is a
 * URL-type clickable menu item which is preceded by a pointing triangle.
 * The other is a form with a submit-button and all the parameters as
 * hidden fields.
 *
 * @author  1999/10/27 - SIT Group
 * @version 1999/10/27 - Neville Paynter - create class
 */
public class MenuItem extends CompoundItem{

    /**
     * Creates a URL-type clickable menu item which is preceded by a
     * pointing triangle
     * @param link   the URL for the hyperlink
     * @param title  the title for the hyperlink
     * @param target the target for the hyperlink
     */
    public MenuItem(String link, String title, String target) {

        // Create Image ID                                           //
        Image iTriangle = new Image
            ("/edm-img/triang-r.gif",
             "triang-r.gif",
             IVAlign.TOP, false);

        // Create table to contain the menu item                     //
        DynamicTable tab = new DynamicTable(2);
        tab.setFrame(ITableFrame.VOLD);
        tab.setRules(ITableRules.NONE);
        tab.setBorder(0);
        //tab.setFrame(ITableFrame.BOX);

        // Add the image and the menu item to the row                //
        TableRow row = new TableRow();
        row
            .addCell(new TableDataCell(iTriangle))
            //.addCell(new TableDataCell(new Link(link, title, target)) )
            .addCell(new TableDataCell(
                new Link(link, new SimpleItem(title), target)) )
            ;
        tab.addRow(row);

        addItem(tab);

    } // MenuItem constructor


    /**
     * Creates a URL-type clickable menu item which is preceded by a
     * pointing triangle.  The target defaults to '_top'.
     * @param link  the URL for the hyperlink
     * @param title the title for the hyperlink
     */
    public MenuItem(String link, String title) {

        this(link, title, "_top");

    } // MenuItem constructor


    /**
     * Creates a form-type menu-item with a submit-button and all the
     * parameters as hidden fields.
     * @param application  the application that will be called
     * @param title        the text on the submit button
     * @param hiddenNames  the array of names for the hidden fields
     * @param hiddenValues the array of values for the hidden fields
     * @param target       the target for the application
     */
    public MenuItem(String application, String title, String[] hiddenNames,
            String[] hiddenValues, String target) {

        // create the form
        Form form = new Form("post", application, target);
        for (int i = 0; i < hiddenNames.length; i++) {
            if (hiddenNames[i] != null) {
                form.addItem(new Hidden(hiddenNames[i], hiddenValues[i]));
            } // if (hiddenNames[i] != null)
        } // for int i
        form.addItem(new Submit("submit", title));

        addItem(form);

    } // MenuItem constructor


    /**
     * Creates a form-type menu-item with a submit-button and all the
     * parameters as hidden fields.  The target defaults to '_top'.
     * @param application  the application that will be called
     * @param title        the text on the submit button
     * @param hiddenNames  the array of names for the hidden fields
     * @param hiddenValues the array of values for the hidden fields
     */
    public MenuItem(String application, String title, String[] hiddenNames,
            String[] hiddenValues) {

        this(application, title, hiddenNames, hiddenValues, "_top");

    } // MenuItem constructor

} // class MenuItem









