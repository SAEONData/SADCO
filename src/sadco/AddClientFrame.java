package sadco;

import oracle.html.*;

/**
 * This class add a client to the EdmClient2 table
 *
 * SadcoWET
 * pScreen.equals("client")
 *
 * @author 991103 - SIT Group
 * @version
 * 19991103 - SIT Group - create class                                      <br>
 * 20080516 - Ursula von St Ange - copy from waves and adapt for sadco      <br>
 */
public class AddClientFrame extends CompoundItem{

    common.edmCommon ec = new common.edmCommon();
    SadConstants sc = new SadConstants();

    static EdmClient2 var;
    boolean success;
    int maxCode;
    int code;
    String name;

    public AddClientFrame(String args[]) {


        String newClient = ec.getArgument(args, sc.NEWCLIENT );

        // Create main table
        DynamicTable mainTab = new DynamicTable(1);
        mainTab.setFrame(ITableFrame.VOLD);
        mainTab.setRules(ITableRules.NONE);
        mainTab.setBorder(0);
        mainTab.setCenter();
        mainTab.addRow(ec.crSpanColsRow(
            "Add a Client",2 , true, "blue", IHAlign.CENTER, "+2"));
        mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));


        //Get the Client class, and check whether the new client exists
        EdmClient2 client[] = new EdmClient2().get();
        int count = client.length;
        for (int i = 0; i < client.length; i++) {
           if(client[i].getName().equalsIgnoreCase(newClient)) {
              count = i;
           }  // if
        }  // for (int i

        // the client did not exist
        if (count == client.length) {

            // add the new client
            EdmClient2 var = new EdmClient2();
            maxCode = var.getMaxCode();
            maxCode++;
            new EdmClient2(maxCode, newClient).put();

            mainTab.addRow(ec.cr2ColRow("Code:", maxCode));
            mainTab.addRow(ec.cr2ColRow("Client:", newClient));
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow("The new client has been added succesfully",
                2, true, "green", IHAlign.CENTER));

        } else {
            mainTab.addRow(ec.cr2ColRow("Code:", client[count].getCode()));
            mainTab.addRow(ec.cr2ColRow("Client:", client[count].getName()));
            mainTab.addRow(ec.crSpanColsRow("&nbsp;",2));
            mainTab.addRow(ec.crSpanColsRow("The client already exists in the database ",
                2 ,true ,"red" ,IHAlign.CENTER));
        } // if (count == client.length) {

        this.addItem(mainTab.setCenter());

    } // constructor

} // class AddClientFrame









