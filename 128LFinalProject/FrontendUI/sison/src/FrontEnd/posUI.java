package FrontEnd;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
public class posUI implements ActionListener{
        JFrame frame;
        JFrame frame1;
        JButton newitem,edit,delete,fetch;
        JPanel panel;
        DefaultTableModel tableModel;
	JTable table;
        JTextField name,quantity,category;
        JLabel namelabel,quantitylabel,categorylabel;
        
        
    public posUI(){
        frame = new JFrame("Simple Inventory System");
        frame.setSize(575,465);
        frame.getContentPane().setBackground(new Color(51,153,255));
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        panel = new JPanel();
        panel.setBounds(10,200,538,200);
        panel.setBackground(new Color(0,128,255));
        
        String[] columnNames = {"Name","Quantity", "Category"};
        Object[][] data = {};
        tableModel = new DefaultTableModel(data, columnNames);
        
        table = new JTable(tableModel);
        table.setSize(800,440);
        table.setRowHeight(15);
        table.setFont(new Font("Calibri", Font.BOLD, 10));
        
         
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        newitem = new JButton("New Item");
        newitem.setBounds(50,50,100,40);
        newitem.setFocusable(false);
        newitem.addActionListener(this);
        
        edit = new JButton("Edit");
        edit.setBounds(225,50,100,40);
        edit.setFocusable(false);
        edit.addActionListener(this);
        
        delete = new JButton("Delete");
        delete.setBounds(400,50,100,40);
        delete.setFocusable(false);
        delete.addActionListener(this);
        
        fetch = new JButton("Fetch");
        fetch.setBounds(50,150,100,40);
        fetch.setFocusable(false);
        fetch.addActionListener(this);
        
        panel.add(scrollPane);
        frame.add(panel);
        frame.add(fetch);
        frame.add(delete);
        frame.add(edit);
        frame.add(newitem);
        frame.setVisible(true);
        
        frame1 = new JFrame("Simple Inventory System");
        frame1.setSize(400,465);
        frame1.getContentPane().setBackground(new Color(51,153,255));
        frame1.setLayout(null);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setResizable(false);
        
        namelabel = new JLabel("Name: ");
        namelabel.setBounds(10,50,150,40);
        
        quantitylabel = new JLabel("Quantity: ");
        quantitylabel.setBounds(10,150,150,40);
        
        categorylabel = new JLabel("Category: ");
        categorylabel.setBounds(10,250,150,40);
        
        name = new JTextField();
        name.setBounds(150,50,150,40);
        
        quantity = new JTextField();
        quantity.setBounds(150,150,150,40);
        
        category = new JTextField();
        category.setBounds(150,250,150,40);
        
        
        frame1.add(category);
        frame1.add(quantity);
        frame1.add(name);
        frame1.add(categorylabel);
        frame1.add(quantitylabel);
        frame1.add(namelabel);
        frame1.setVisible(false);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==newitem){
            frame1.setVisible(true);
        }
        if(e.getSource()==fetch){
            String name1 = name.getText();
            String quantity1 = quantity.getText();
            String category1 = category.getText();
            
            
            Object[] rowData = {name1,quantity1,category1};
	        tableModel.addRow(rowData);
	        // Refresh table
	        tableModel.fireTableDataChanged();
                name.setText(null);
                quantity.setText(null);
                category.setText(null);
        }
        if(e.getSource()==edit){
            String name1 = tableModel.getValueAt(0, 0).toString();
            String quantity1 = tableModel.getValueAt(0, 1).toString();
            String category1 = tableModel.getValueAt(0, 2).toString();
            
            
            name.setText(name1);
            quantity.setText(quantity1);
            category.setText(category1);
            tableModel.removeRow(0);
        }
        if(e.getSource()==delete){
            tableModel.removeRow(0);
        }
    }
}
