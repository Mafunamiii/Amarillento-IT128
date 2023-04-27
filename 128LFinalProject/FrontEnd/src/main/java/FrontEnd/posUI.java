package FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.table.*;


public class posUI implements ActionListener{

        JFrame frame, CreateItemMenu, DeleteItemMenu, ItemIdSelMenu, EditItemMenu;
        JButton newitem,edit,delete,fetch,createitem,deleteitem, selectId, edititem;
        JPanel panel;
        DefaultTableModel tableModel;
	JTable table;
        JTextField name,quantity,category, supplier, purchaseDate, expiryDate, price, id;
        JTextField idDelete;
        JTextField idEdit;
        JTextField editname,editquantity,editcategory, editsupplier, editpurchaseDate, editexpiryDate, editprice, editid;
        JLabel logo, namelabel,quantitylabel,categorylabel, idlabel, supplierlabel, purchaseDatelabel, expiryDatelabel, pricelabel;
        JLabel idlabelDelete;
        JLabel idlabelEdit;
        JLabel editnamelabel,editquantitylabel,editcategorylabel, editidlabel, editsupplierlabel, editpurchaseDatelabel, editexpiryDatelabel, editpricelabel;

        
        
        private String itemName, itemSupplier, itemCategory, PurchaseDate, ExpiryDate, StringData;
        private String[] columnNames = { "Id","Name", "Supplier","Quantity", "Category", "Purchase Date", "Expiry Date", "Expired", "Price per unit", "Total price"};
        private double itemPpu;
        private int itemQuantity, itemId;
        boolean isExpired = false;
            
    
        APIconnect apiConnect = new APIconnect(
                itemId, itemName, itemSupplier, itemCategory, PurchaseDate, ExpiryDate, itemPpu, itemQuantity
        );
        
    private void FetchData() {
        try {
            StringData = apiConnect.getData();
            System.out.println(StringData);
        } catch (IOException ex) {
            Logger.getLogger(posUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JsonReader reader = Json.createReader(new StringReader(StringData));
        JsonArray jsonArray = reader.readArray();
        
        // Clear the existing data from the tableModel
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        
        // Add the new data to the tableModel
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            
            itemId = jsonObject.getInt("itemId");
            itemName = jsonObject.getJsonString("name").getString();
            itemSupplier = jsonObject.getJsonString("supplier").getString();
            itemQuantity = jsonObject.getInt("quantity");
            itemCategory = jsonObject.getJsonString("category").getString();
            PurchaseDate = jsonObject.getJsonString("PurchaseDate").getString();
            ExpiryDate = jsonObject.getJsonString("ExpiryDate").getString();
            itemPpu = jsonObject.getJsonNumber("ppu").doubleValue();
            isExpired = false;
            
            if (!ExpiryDate.equalsIgnoreCase("N/A")) {
                String[] monYear = ExpiryDate.split("-");
                String year = monYear[0];
                String month = monYear[1];
            
                if (Integer.parseInt(year) <= (YearMonth.now().getYear()) && Integer.parseInt(month) <= (YearMonth.now().getMonthValue())) {
                    isExpired = true;
                }
            }
            
            
            DecimalFormat df = new DecimalFormat("#.##");
            Object[] row = { itemId, itemName, itemSupplier, itemQuantity, itemCategory, PurchaseDate, ExpiryDate, isExpired, itemPpu, Double.parseDouble(df.format(itemPpu * itemQuantity)) };
                tableModel.addRow(row);

        }
    }     
    
    private String verifyDate (String datestr) {
            if (datestr.equalsIgnoreCase("N/A")) {
                return datestr;
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            Date date = null;
            try {
                date = format.parse(datestr);
            } catch (ParseException err) {
                JFrame errorFrame = new JFrame("Error");
                JLabel errorMessage = new JLabel("Date must be YYYY-MM-DD.");
                errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                errorFrame.add(errorMessage);
                errorFrame.setSize(300, 100);
                errorFrame.setLocationRelativeTo(null);
                errorFrame.setVisible(true);
            }
            
            if (date == null) {
                JFrame errorFrame = new JFrame("Error");
                JLabel errorMessage = new JLabel("Date must be YYYY-MM-DD.");
                errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                errorFrame.add(errorMessage);
                errorFrame.setSize(300, 100);
                errorFrame.setLocationRelativeTo(null);
                errorFrame.setVisible(true);
            } else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1; // January is 0
                int day = cal.get(Calendar.DAY_OF_MONTH);
                
                if (year < 1900 || year > 2100) {
                    // handle the year out of range error, e.g. show an error message to the user
                } else if (month < 1 || month > 12) {
                    // handle the month out of range error, e.g. show an error message to the user
                } else {
                    int maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (day < 1 || day > maxDaysInMonth) {
                        // handle the day out of range error, e.g. show an error message to the user
                    } else {
                        // the date is valid, convert it back to string
                        String validatedDateStr = format.format(date);
                        return validatedDateStr;
                    }
                }
            }
        return "error";
    }
    
    private void EditData(int id) {
        
        
        try {
            StringData = apiConnect.getItem(id);
            System.out.println(StringData);
        } catch (IOException ex) {
            Logger.getLogger(posUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JsonReader reader = Json.createReader(new StringReader(StringData));
        JsonObject jsonObject = reader.readObject();
        
        itemId = jsonObject.getInt("itemId");
        itemName = jsonObject.getJsonString("name").getString();
        itemSupplier = jsonObject.getJsonString("supplier").getString();
        itemQuantity = jsonObject.getInt("quantity");
        itemCategory = jsonObject.getJsonString("category").getString();
        PurchaseDate = jsonObject.getJsonString("PurchaseDate").getString();
        ExpiryDate = jsonObject.getJsonString("ExpiryDate").getString();
        itemPpu = jsonObject.getJsonNumber("ppu").doubleValue();

        
        editname.setText(itemName);
        editsupplier.setText(itemSupplier);
        editquantity.setText(String.valueOf(itemQuantity));
        editcategory.setText(itemCategory);
        editpurchaseDate.setText(PurchaseDate);
        editexpiryDate.setText(ExpiryDate);
        editprice.setText(String.valueOf(itemPpu));
        

        EditItemMenu.setVisible(true);
        
    }
    
    public posUI(){
        frame = new JFrame("GrabPanda Inventory System");
        frame.setSize(900,465);
        frame.getContentPane().setBackground(new Color(205,208,246));
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        panel = new JPanel(new BorderLayout());
        panel.setBounds(10,200,865,200);
        panel.setBackground(new Color(0,128,255));
              

        Object[][] data = {};
        tableModel = new DefaultTableModel(data, columnNames);
        
        table = new JTable(tableModel);
        table.setSize(800,440);
        table.setRowHeight(15);
        table.setFont(new Font("Calibri", Font.BOLD, 10));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        newitem = new JButton("New Item");
        newitem.setBounds(10,50,210,40);
        newitem.setBackground(new Color(150, 178,252));
        newitem.setFocusable(false);
        newitem.addActionListener(this);
        
        edit = new JButton("Edit item");
        edit.setBounds(230,50,210,40);
        edit.setBackground(new Color(150, 178,252));
        edit.setFocusable(false);
        edit.addActionListener(this);
        
        delete = new JButton("Delete item");
        delete.setBounds(450,50,210,40);
        delete.setBackground(new Color(150, 178,252));
        delete.setFocusable(false);
        delete.addActionListener(this);
        
        fetch = new JButton("Fetch data");
        fetch.setBounds(10,150,150,40);
        fetch.setBackground(new Color(150, 178,252));
        fetch.setFocusable(false);
        fetch.addActionListener(this);
        
        createitem = new JButton("Submit Item");
        createitem.setBackground(new Color(150, 178,252));
        createitem.setBounds(170, 500, 200,40);
        createitem.setFocusable(false);
        createitem.addActionListener(this);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/Resource/DarkmodeComplete.png"));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(280, 100, Image.SCALE_SMOOTH);
        
        logo = new JLabel();
        logo.setBounds(600,50,400,200);
	logo.setBackground(new Color(128,128,128));
	logo.setIcon(new ImageIcon(resizedImg));
		
        
        panel.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel);
        frame.add(fetch);
        frame.add(delete);
        frame.add(edit);
        frame.add(newitem);
        frame.add(logo);
        frame.setVisible(true);
        
        idlabel = new JLabel("Id: ");
        idlabel.setBounds(10, 50, 200, 40);

        namelabel = new JLabel("Name: ");
        namelabel.setBounds(10, 100, 200, 40);

        supplierlabel = new JLabel("Supplier: ");
        supplierlabel.setBounds(10, 150, 200, 40);

        quantitylabel = new JLabel("Quantity: ");
        quantitylabel.setBounds(10, 200, 200, 40);

        categorylabel = new JLabel("Category: ");
        categorylabel.setBounds(10, 250, 200, 40);

        purchaseDatelabel = new JLabel("Purchase Date (yyyy-mm-dd): ");
        purchaseDatelabel.setBounds(10, 300, 200, 40);

        expiryDatelabel = new JLabel("Expiry Date (yyyy-mm-dd): ");
        expiryDatelabel.setBounds(10, 350, 200, 40);

        pricelabel = new JLabel("Price: ");
        pricelabel.setBounds(10, 400, 200, 40);

        id = new JTextField();
        id.setBounds(180, 50, 200, 40);

        name = new JTextField();
        name.setBounds(180, 100, 200, 40);

        supplier = new JTextField();
        supplier.setBounds(180, 150, 200, 40);

        quantity = new JTextField();
        quantity.setBounds(180, 200, 200, 40);

        category = new JTextField();
        category.setBounds(180, 250, 200, 40);

        purchaseDate = new JTextField();
        purchaseDate.setBounds(180, 300, 200, 40);

        expiryDate = new JTextField();
        expiryDate.setBounds(180, 350, 200, 40);

        price = new JTextField();
        price.setBounds(180, 400, 200, 40);

        CreateItemMenu = new JFrame("Simple Inventory System");
        CreateItemMenu.setSize(400, 600);
        CreateItemMenu.getContentPane().setBackground(new Color(205,208,246));
        CreateItemMenu.setLayout(null);
        CreateItemMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        CreateItemMenu.setResizable(false);

        CreateItemMenu.add(idlabel);
        CreateItemMenu.add(namelabel);
        CreateItemMenu.add(supplierlabel);
        CreateItemMenu.add(quantitylabel);
        CreateItemMenu.add(categorylabel);
        CreateItemMenu.add(purchaseDatelabel);
        CreateItemMenu.add(expiryDatelabel);
        CreateItemMenu.add(pricelabel);
        CreateItemMenu.add(id);
        CreateItemMenu.add(name);
        CreateItemMenu.add(supplier);
        CreateItemMenu.add(quantity);
        CreateItemMenu.add(category);
        CreateItemMenu.add(purchaseDate);
        CreateItemMenu.add(expiryDate);
        CreateItemMenu.add(price);
        CreateItemMenu.add(createitem);
        CreateItemMenu.setVisible(false);
        
        DeleteItemMenu = new JFrame("Delete Item");
        DeleteItemMenu.setSize(400, 300);
        DeleteItemMenu.getContentPane().setBackground(new Color(205,208,246));
        DeleteItemMenu.setLayout(null);
        DeleteItemMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        DeleteItemMenu.setResizable(false);
        
        idlabelDelete = new JLabel("Item Id to delete: ");
        idlabelDelete.setBounds(10, 50, 200, 40);
        
        idDelete = new JTextField();
        idDelete.setBounds(180, 50, 200, 40);
        
        deleteitem = new JButton("Delete item");
        deleteitem.setBackground(new Color(150, 178,252));
        deleteitem.setBounds(170, 150, 200,40);
        deleteitem.setFocusable(false);
        deleteitem.addActionListener(this);
        
        DeleteItemMenu.add(idlabelDelete);
        DeleteItemMenu.add(idDelete);
        DeleteItemMenu.add(deleteitem);
                 
        ItemIdSelMenu = new JFrame("Select Item ID");
        ItemIdSelMenu.setSize(400, 300);
        ItemIdSelMenu.getContentPane().setBackground(new Color(205,208,246));
        ItemIdSelMenu.setLayout(null);
        ItemIdSelMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ItemIdSelMenu.setResizable(false);
        
        idlabelEdit = new JLabel("Item Id to edit: ");
        idlabelEdit.setBounds(10, 50, 200, 40);
        
        idEdit = new JTextField();
        idEdit.setBounds(180, 50, 200, 40);
        
        selectId = new JButton("Select item");
        selectId.setBackground(new Color(150, 178,252));
        selectId.setBounds(170, 150, 200,40);
        selectId.setFocusable(false);
        selectId.addActionListener(this);
        
        ItemIdSelMenu.add(idlabelEdit);
        ItemIdSelMenu.add(idEdit);
        ItemIdSelMenu.add(selectId);
        
        EditItemMenu = new JFrame("Edit Item");
        EditItemMenu.setSize(400, 600);
        EditItemMenu.getContentPane().setBackground(new Color(205,208,246));
        EditItemMenu.setLayout(null);
        EditItemMenu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        EditItemMenu.setResizable(false);
        
        editname = new JTextField();
        editname.setBounds(180, 100, 200, 40);

        editsupplier = new JTextField();
        editsupplier.setBounds(180, 150, 200, 40);

        editquantity = new JTextField();
        editquantity.setBounds(180, 200, 200, 40);

        editcategory = new JTextField();
        editcategory.setBounds(180, 250, 200, 40);

        editpurchaseDate = new JTextField();
        editpurchaseDate.setBounds(180, 300, 200, 40);

        editexpiryDate = new JTextField();
        editexpiryDate.setBounds(180, 350, 200, 40);

        editprice = new JTextField();
        editprice.setBounds(180, 400, 200, 40);
        
        edititem = new JButton("Edit item");
        edititem.setBackground(new Color(150, 178,252));
        edititem.setBounds(170, 500, 200,40);
        edititem.setFocusable(false);
        edititem.addActionListener(this);
        
        editnamelabel = new JLabel("Name: ");
        editnamelabel.setBounds(10, 100, 200, 40);

        editsupplierlabel = new JLabel("Supplier: ");
        editsupplierlabel.setBounds(10, 150, 200, 40);

        editquantitylabel = new JLabel("Quantity: ");
        editquantitylabel.setBounds(10, 200, 200, 40);

        editcategorylabel = new JLabel("Category: ");
        editcategorylabel.setBounds(10, 250, 200, 40);

        editpurchaseDatelabel = new JLabel("Purchase Date (yyyy-mm-dd): ");
        editpurchaseDatelabel.setBounds(10, 300, 200, 40);

        editexpiryDatelabel = new JLabel("Expiry Date (yyyy-mm-dd): ");
        editexpiryDatelabel.setBounds(10, 350, 200, 40);

        editpricelabel = new JLabel("Price: ");
        editpricelabel.setBounds(10, 400, 200, 40);
        
        EditItemMenu.add(editnamelabel);
        EditItemMenu.add(editsupplierlabel);
        EditItemMenu.add(editquantitylabel);
        EditItemMenu.add(editcategorylabel);
        EditItemMenu.add(editpurchaseDatelabel);
        EditItemMenu.add(editexpiryDatelabel);
        EditItemMenu.add(editpricelabel);
        EditItemMenu.add(editname);
        EditItemMenu.add(editsupplier);
        EditItemMenu.add(editquantity);
        EditItemMenu.add(editcategory);
        EditItemMenu.add(editpurchaseDate);
        EditItemMenu.add(editexpiryDate);
        EditItemMenu.add(editprice);
        EditItemMenu.add(edititem);
        EditItemMenu.setVisible(false);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==newitem){
            CreateItemMenu.setVisible(true);
        }
        if(e.getSource()==fetch){
           System.out.println("fetch button pressed");
           FetchData();
        }
        if(e.getSource()==delete){
            DeleteItemMenu.setVisible(true);
        }
        if(e.getSource()==edit){
            ItemIdSelMenu.setVisible(true);
        }
        
        if(e.getSource() == createitem) {
            System.out.println("create item button pressed");
            
            try {
                itemId = Integer.parseInt(id.getText());
                itemQuantity = Integer.parseInt(quantity.getText());
                System.out.println("Id and quantity check");
            } catch (NumberFormatException ex) {
                JFrame errorFrame = new JFrame("Error");
                JLabel errorMessage = new JLabel("Id and Quantity must be an integer.");
                errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                errorFrame.add(errorMessage);
                errorFrame.setSize(300, 100);
                errorFrame.setLocationRelativeTo(null);
                errorFrame.setVisible(true);
            }
            
            try {
                itemPpu = Double.parseDouble(price.getText());
                System.out.println("ppu check");
            } catch (NumberFormatException ex) {
                JFrame errorFrame = new JFrame("Error");
                JLabel errorMessage = new JLabel("Price must be a Double.");
                errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                errorFrame.add(errorMessage);
                errorFrame.setSize(300, 100);
                errorFrame.setLocationRelativeTo(null);
                errorFrame.setVisible(true);
            }
            
            String dateStr = purchaseDate.getText();
            PurchaseDate = verifyDate(dateStr);
            dateStr = expiryDate.getText();
            ExpiryDate = verifyDate(dateStr);
            
            itemName = name.getText();
            itemSupplier = supplier.getText();
            itemCategory = category.getText();

            try {
                apiConnect.Createitem(itemId, itemName, itemSupplier, itemCategory, PurchaseDate, ExpiryDate, itemPpu, itemQuantity);
            } catch (IOException ex) {
                Logger.getLogger(posUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            FetchData();
        }
        
        
        if(e.getSource() == deleteitem) {
            try {
                itemId = Integer.parseInt(idDelete.getText());
                System.out.println("Id check");
                
                try {
                    System.out.println("item to delete: "+ itemId);
                    apiConnect.Deleteitem(itemId);
                } catch (IOException ex) {
                    JFrame errorFrame = new JFrame("Error");
                    JLabel errorMessage = new JLabel("Invalid id.");
                    errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                    errorFrame.add(errorMessage);
                    errorFrame.setSize(300, 100);
                    errorFrame.setLocationRelativeTo(null);
                    errorFrame.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                JFrame errorFrame = new JFrame("Error");
                JLabel errorMessage = new JLabel("Id must be an integer.");
                errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                errorFrame.add(errorMessage);
                errorFrame.setSize(300, 100);
                errorFrame.setLocationRelativeTo(null);
                errorFrame.setVisible(true);
            }
            
            FetchData();
            
        }
        
        if (e.getSource()==selectId) {
            try {
                itemId = Integer.parseInt(idEdit.getText());
                System.out.println("Id check");
                
                try {
                    System.out.println("item to delete: "+ itemId);
                    apiConnect.getItem(itemId);
                    
                    EditData(itemId);
                } catch (IOException ex) {
                    JFrame errorFrame = new JFrame("Error");
                    JLabel errorMessage = new JLabel("Invalid id.");
                    errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                    errorFrame.add(errorMessage);
                    errorFrame.setSize(300, 100);
                    errorFrame.setLocationRelativeTo(null);
                    errorFrame.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                JFrame errorFrame = new JFrame("Error");
                JLabel errorMessage = new JLabel("Id must be an integer.");
                errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                errorFrame.add(errorMessage);
                errorFrame.setSize(300, 100);
                errorFrame.setLocationRelativeTo(null);
                errorFrame.setVisible(true);
            }
        }
        
        if (e.getSource() == edititem) {
            System.out.println("create item button pressed");
            
            try {
                itemQuantity = Integer.parseInt(editquantity.getText());
                System.out.println("quantity check");
            } catch (NumberFormatException ex) {
                JFrame errorFrame = new JFrame("Error");
                JLabel errorMessage = new JLabel("Quantity must be an integer.");
                errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                errorFrame.add(errorMessage);
                errorFrame.setSize(300, 100);
                errorFrame.setLocationRelativeTo(null);
                errorFrame.setVisible(true);
            }
            
            try {
                itemPpu = Double.parseDouble(editprice.getText());
                System.out.println("ppu check");
            } catch (NumberFormatException ex) {
                JFrame errorFrame = new JFrame("Error");
                JLabel errorMessage = new JLabel("Price must be a Double.");
                errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                errorFrame.add(errorMessage);
                errorFrame.setSize(300, 100);
                errorFrame.setLocationRelativeTo(null);
                errorFrame.setVisible(true);
            }
            
            String dateStr = editpurchaseDate.getText();
            PurchaseDate = verifyDate(dateStr);
            dateStr = editexpiryDate.getText();
            ExpiryDate = verifyDate(dateStr);
            
            itemName = editname.getText();
            itemSupplier = editsupplier.getText();
            itemCategory = editcategory.getText();

            try {
                apiConnect.Updateitem(itemId, itemName, itemSupplier, itemCategory, PurchaseDate, ExpiryDate, itemPpu, itemQuantity);
            } catch (IOException ex) {
                Logger.getLogger(posUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            FetchData();
        }
    }
}

