import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Sat Aug 14 04:30:00 IST 2021
 */


/**
 * @author unknown
 */
public class Savings extends JFrame {
    private String customerNumber;
    private String customerName;
    private String initialDeposit;
    private String numOfYears;
    private String typeOfSavings;

    //constructor
    public Savings(String customerNumber, String customerName, String initialDeposit, String numOfYears, String typeOfSavings) throws SQLException, ClassNotFoundException {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.initialDeposit = initialDeposit;
        this.numOfYears = numOfYears;
        this.typeOfSavings = typeOfSavings;
    }

    //getter and setter
    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(String initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public String getNumOfYears() {
        return numOfYears;
    }

    public void setNumOfYears(String numOfYears) {
        this.numOfYears = numOfYears;
    }

    public String getTypeOfSavings() {
        return typeOfSavings;
    }

    public void setTypeOfSavings(String typeOfSavings) {
        this.typeOfSavings = typeOfSavings;
    }

    public static ArrayList<Savings> list1 = new ArrayList<Savings>();
    Connection1 con = new Connection1();
    Connection conObj = con.connect();

    public Savings() throws SQLException, ClassNotFoundException {
        initComponents();
    }

    //Attached githublink below
    //https://github.com/bismiswalih/Final.git
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Savings form1 = new Savings();
        form1.setVisible(true);

    }

    public void updateTable() throws SQLException {
        String quer1 = "Select * from savingstable";
        PreparedStatement query = conObj.prepareStatement(quer1,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = query.executeQuery();
        DefaultTableModel df = (DefaultTableModel) table1.getModel();
        rs.last();
        int z = rs.getRow();
        rs.beforeFirst();
        String[][] array = new String[0][];
        if (z > 0) {
            array = new String[z][5];
        }
        int j = 0;
        while (rs.next()) {
            array[j][0] = rs.getString("custno");
            array[j][1] = rs.getString("custname");
            array[j][2] = rs.getString("cdep");
            array[j][3] = rs.getString("nyears");
            array[j][4] = rs.getString("savtype");
            ++j;
        }
        String[] cols = {"Number", "Name", "Deposit", "Years", "Type of Savings"};
        DefaultTableModel model = new DefaultTableModel(array, cols);
        table1.setModel(model);
    }


    private void btnAddActionPerformed(ActionEvent e) throws SQLException {
        String cusNum;
        String cusName;
        String cusInitDeposit;
        String numYears;
        String typeOfSavings;
        try {
            cusNum = txtCusNum.getText();
            if (cusNum.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a Customer Name!");
            } else {
                cusName = txtCusName.getText();
                if (cusName.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the Name!");
                } else {
                    cusInitDeposit = txtInitDeposit.getText();
                    if (cusInitDeposit.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please enter Deposit Amount!");
                    } else {
                        Integer.parseInt(cusInitDeposit);
                        numYears = txtNumYears.getText();
                        if (numYears.equals("")) {
                            JOptionPane.showMessageDialog(null, "Please enter number of years!");
                        }
                        else {
                            Integer.parseInt(numYears);
                            typeOfSavings = cboSavings.getSelectedItem().toString();
                            String query1 = "Select * from savingstable where custno =?";
                            PreparedStatement query = conObj.prepareStatement(query1,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

                            query.setString(1, cusNum);

                            ResultSet rs = query.executeQuery();

                            if (rs.isBeforeFirst()) {
                                JOptionPane.showMessageDialog(null, "The record is existing");
                                //set the textboxes to space
                                return;
                            }

                            String query2 = "Insert into savingstable values (?,?,?,?,?)";
                            query = conObj.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

                            query.setString(1, cusNum);
                            query.setString(2, cusName);
                            query.setString(3, cusInitDeposit);
                            query.setString(4, numYears);
                            query.setString(5, typeOfSavings);

                            query.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Record added");
                            updateTable();
                            txtCusNum.setText("");
                            txtCusName.setText("");
                            txtInitDeposit.setText("");
                            txtNumYears.setText("");
                        }
                    }
                }
            }
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter number!");
        }
    }

    private void table1MouseClicked(MouseEvent e) throws SQLException, ClassNotFoundException {
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        int index = table1.getSelectedRow();

        txtCusName.setText(df.getValueAt(index, 0).toString());
        txtCusNum.setText(df.getValueAt(index, 1).toString());
        txtInitDeposit.setText(df.getValueAt(index, 2).toString());
        txtNumYears.setText(df.getValueAt(index, 3).toString());
        if (cboSavings.getSelectedIndex() == 0) {
            Deluxe obj1 = new Deluxe(df.getValueAt(index, 0).toString(), df.getValueAt(index, 1).toString(), df.getValueAt(index, 2).toString(), df.getValueAt(index, 3).toString(), df.getValueAt(index, 3).toString());
            obj1.generateTable();
        } else if (cboSavings.getSelectedIndex() == 1) {
            Regular obj1 = new Regular(df.getValueAt(index, 0).toString(), df.getValueAt(index, 1).toString(), df.getValueAt(index, 2).toString(), df.getValueAt(index, 3).toString(), df.getValueAt(index, 3).toString());
            obj1.generateTable();
        }
    }

    private void btnEditActionPerformed(ActionEvent e) throws SQLException {
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        int index = table1.getSelectedRow();

        String cusNum;
        String cusName;
        String cusInitDeposit;
        String numYears;
        String typeOfSavings;
        cusNum = txtCusNum.getText();
        cusName = txtCusName.getText();
        cusInitDeposit = txtInitDeposit.getText();
        numYears = txtNumYears.getText();
        typeOfSavings = cboSavings.getSelectedItem().toString();

        String oldvalue = df.getValueAt(index, 0).toString();
        if (!oldvalue.equals(cusNum)) {
            String query1 = "Select * from savingstable where custno =?";
            PreparedStatement stmt = conObj.prepareStatement(query1,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            stmt.setString(1, cusNum);

            ResultSet rs = stmt.executeQuery();

            if (rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "The record is existing");
                //set the textboxes to space
                return;
            }
        }
        String query = "Update savingstable set custno=?,custname=?,cdep=?,nyears=?,savtype=? where custno=?";
        PreparedStatement query2 = conObj.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        query2.setString(1, cusNum);
        query2.setString(2, cusName);
        query2.setString(3, cusInitDeposit);
        query2.setString(4, numYears);
        query2.setString(5, typeOfSavings);
        query2.setString(6, oldvalue);
        query2.executeUpdate();
        updateTable();
        JOptionPane.showMessageDialog(null, "Record Edited");
        txtCusNum.setText("");
        txtCusName.setText("");
        txtInitDeposit.setText("");
        txtNumYears.setText("");
    }

    private void btnDeleteActionPerformed(ActionEvent e) throws SQLException {
        int i = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            String cusNum;
            cusNum = txtCusNum.getText();
            String query = "Delete from savingstable where custno =?";
            PreparedStatement query2 = conObj.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            query2.setString(1, cusNum);
            query2.executeUpdate();
            updateTable();
            txtCusNum.setText("");
            txtCusName.setText("");
            txtInitDeposit.setText("");
            txtNumYears.setText("");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        txtCusNum = new JTextField();
        label2 = new JLabel();
        txtCusName = new JTextField();
        label3 = new JLabel();
        txtInitDeposit = new JTextField();
        label5 = new JLabel();
        txtNumYears = new JTextField();
        label6 = new JLabel();
        cboSavings = new JComboBox<>();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        btnAdd = new JButton();
        btnEdit = new JButton();
        btnDelete = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Enter the Customer Number");
        contentPane.add(label1, "cell 0 0");
        contentPane.add(txtCusNum, "cell 1 0");

        //---- label2 ----
        label2.setText("Enter the Customer Name");
        contentPane.add(label2, "cell 0 1");
        contentPane.add(txtCusName, "cell 1 1");

        //---- label3 ----
        label3.setText("Enter the Initial Deposit");
        contentPane.add(label3, "cell 0 2");
        contentPane.add(txtInitDeposit, "cell 1 2");

        //---- label5 ----
        label5.setText("Enter the number of years");
        contentPane.add(label5, "cell 0 3");
        contentPane.add(txtNumYears, "cell 1 3");

        //---- label6 ----
        label6.setText("Choose the type of  savings");
        contentPane.add(label6, "cell 0 4");

        //---- cboSavings ----
        cboSavings.setModel(new DefaultComboBoxModel<>(new String[] {
            "Savings-Deluxe",
            "Savings-Regular"
        }));
        contentPane.add(cboSavings, "cell 1 4");

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        table1MouseClicked(e);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 0 5");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(table2);
        }
        contentPane.add(scrollPane2, "cell 1 5");

        //---- btnAdd ----
        btnAdd.setText("Add");
        btnAdd.addActionListener(e -> {
            try {
                btnAddActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnAdd, "cell 0 6");

        //---- btnEdit ----
        btnEdit.setText("Edit");
        btnEdit.addActionListener(e -> {
            try {
                btnEditActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnEdit, "cell 0 6");

        //---- btnDelete ----
        btnDelete.setText("Delete");
        btnDelete.addActionListener(e -> {
            try {
                btnDeleteActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnDelete, "cell 0 6");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel label1;
    private JTextField txtCusNum;
    private JLabel label2;
    private JTextField txtCusName;
    private JLabel label3;
    private JTextField txtInitDeposit;
    private JLabel label5;
    private JTextField txtNumYears;
    private JLabel label6;
    private JComboBox<String> cboSavings;
    private JScrollPane scrollPane1;
    public JTable table1;
    private JScrollPane scrollPane2;
    public JTable table2;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
