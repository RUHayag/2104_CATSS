
package Stocks;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import menu.menu;
import SalesHistory.sales;

public class stocks extends javax.swing.JFrame {

    private String password;
    String ProductNames = "";
    String ProductPrices = "";
    String ProductsQuantity = "";
    
    public void setPassword(String password) {
        this.password = password;
    }
     public void setPname(String ProductNames){
        this.ProductNames = ProductNames;
    }
    
    public stocks() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Connect();
        SearchProductNo();
        Fetch();
    }
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    public void Connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffeetea","root","");
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(stocks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void SearchProductNo(){
        try {
            pst = con.prepareStatement("SELECT Product_ID FROM stocks");
            rs = pst.executeQuery();
            productID.removeAllItems();
            while(rs.next()){
                productID.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(stocks.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    public void Fetch(){
        try {
            int q;
            pst = con.prepareStatement("SELECT * FROM stocks");
            rs = pst.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            
            DefaultTableModel df = (DefaultTableModel)stocksTbl.getModel();
            df.setRowCount(0);
            while(rs.next()){
                Vector v2 = new Vector();
                for(int a = 1; a <= q; a++){
                    v2.add(rs.getString("Product_ID"));
                    v2.add(rs.getString("ProductName"));
                    v2.add(rs.getString("ProductPrice"));
                    v2.add(rs.getString("ProductQuantity"));
                }
                df.addRow(v2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(stocks.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelSTOCKMAN = new javax.swing.JLabel();
        labelPRONAME = new javax.swing.JLabel();
        labelPROprice = new javax.swing.JLabel();
        labelPROQTY = new javax.swing.JLabel();
        txtPName = new javax.swing.JTextField();
        txtPPrice = new javax.swing.JTextField();
        txtPQty = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblProdID = new javax.swing.JLabel();
        productID = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        btnCheckSales = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        stocksTbl = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        lblBGkape = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("STOCKS");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelSTOCKMAN.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelSTOCKMAN.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSTOCKMAN.setText("STOCK MANAGEMENT");
        jPanel1.add(jLabelSTOCKMAN, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 27, 288, 44));

        labelPRONAME.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelPRONAME.setForeground(new java.awt.Color(255, 255, 255));
        labelPRONAME.setText("PRODUCT NAME :");
        jPanel1.add(labelPRONAME, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 160, 26));

        labelPROprice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelPROprice.setForeground(new java.awt.Color(255, 255, 255));
        labelPROprice.setText("PRODUCT PRICE :");
        jPanel1.add(labelPROprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 160, 26));

        labelPROQTY.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelPROQTY.setForeground(new java.awt.Color(255, 255, 255));
        labelPROQTY.setText("PRODUCT QTY :");
        jPanel1.add(labelPROQTY, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 160, 26));

        txtPName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtPName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPNameActionPerformed(evt);
            }
        });
        jPanel1.add(txtPName, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 304, -1));

        txtPPrice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtPPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPPriceActionPerformed(evt);
            }
        });
        jPanel1.add(txtPPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, 304, -1));

        txtPQty.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel1.add(txtPQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 440, 304, -1));

        btnAdd.setBackground(new java.awt.Color(0, 153, 255));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("ADD");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 530, 116, 39));

        btnUpdate.setBackground(new java.awt.Color(0, 153, 255));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 530, 116, 39));

        btnDelete.setBackground(new java.awt.Color(255, 0, 51));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 530, 116, 39));

        lblProdID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblProdID.setForeground(new java.awt.Color(255, 255, 255));
        lblProdID.setText("PRODUCT ID :");
        jPanel1.add(lblProdID, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 120, 26));

        productID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        productID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(productID, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, 110, -1));

        btnSearch.setBackground(new java.awt.Color(0, 153, 255));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("SEARCH");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 230, 90, 40));

        btnCheckSales.setBackground(new java.awt.Color(0, 102, 0));
        btnCheckSales.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCheckSales.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckSales.setText("SALES HISTORY");
        btnCheckSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckSalesActionPerformed(evt);
            }
        });
        jPanel1.add(btnCheckSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 690, 180, 70));

        stocksTbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        stocksTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "PRODUCT_ID", "PRODUCT NAME", "PRODUCT PRICE", "PRODUCT QUANTITY"
            }
        ));
        jScrollPane1.setViewportView(stocksTbl);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(631, 140, 853, 475));

        btnBack.setBackground(new java.awt.Color(0, 153, 255));
        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("BACK");
        btnBack.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jPanel1.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(1365, 695, 119, 39));

        lblBGkape.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Stocks/bgStock (1).jpg"))); // NOI18N
        lblBGkape.setText("jLabel1");
        jPanel1.add(lblBGkape, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1610, 920));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPPriceActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        menu menuFrame = new menu();
        menuFrame.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
        try {
            ProductNames = txtPName.getText();
            ProductPrices = txtPPrice.getText();
            ProductsQuantity = txtPQty.getText();
            
            pst = con.prepareStatement("INSERT INTO stocks (ProductName, ProductPrice, ProductQuantity) VALUES(?,?,?)");
            pst.setString(1, ProductNames);
            pst.setString(2, ProductPrices);
            pst.setString(3, ProductsQuantity);
            
            int k = pst.executeUpdate();
            
            if(k==1){
                JOptionPane.showMessageDialog(this, "Record Added Successfuly!");
                txtPName.setText("");
                txtPPrice.setText("");
                txtPQty.setText("");       
                Fetch();
                SearchProductNo();
            } 
            else{
                JOptionPane.showMessageDialog(this, "Record Failed to Save!");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(stocks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtPNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPNameActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try {
            String pid = productID.getSelectedItem().toString();
            
            pst = con.prepareStatement("SELECT * FROM stocks WHERE Product_ID = ?");
            pst.setString(1, pid);
            rs = pst.executeQuery();
            
            if(rs.next() == true){
                txtPName.setText(rs.getString(2));
                txtPPrice.setText(rs.getString(3));
                txtPQty.setText(rs.getString(4));
            }
            else{
                JOptionPane.showMessageDialog(this, "No record found!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(stocks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            String pname = txtPName.getText();
            String price = txtPPrice.getText();
            String qty = txtPQty.getText();
            String pid = productID.getSelectedItem().toString();
            
            pst = con.prepareStatement("UPDATE stocks SET ProductName=?, ProductPrice=?, ProductQuantity=? WHERE Product_ID=?");
            pst.setString(1, pname);
            pst.setString(2, price);
            pst.setString(3, qty);
            pst.setString(4, pid);
            
            int k=pst.executeUpdate();
            if(k==1){
                JOptionPane.showMessageDialog(this, "Record has been updated.");
                txtPName.setText("");
                txtPPrice.setText("");
                txtPQty.setText("");
                txtPName.requestFocus();
                Fetch();
                SearchProductNo();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(stocks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            String pid = productID.getSelectedItem().toString();
            pst=con.prepareStatement("DELETE FROM stocks WHERE Product_ID=? ");
            pst.setString(1,pid);
            
            int k =pst.executeUpdate();
            if(k==1){
                JOptionPane.showMessageDialog(this,"Record has been deleted.");
                txtPName.setText("");
                txtPPrice.setText("");
                txtPQty.setText("");
                txtPName.requestFocus();
                Fetch();
                SearchProductNo();

            }
            else{
                JOptionPane.showMessageDialog(this,"Record failed to delete.");

            }
        } catch (SQLException ex) {
            Logger.getLogger(stocks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCheckSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckSalesActionPerformed
        sales sss = new sales();
        sss.setVisible(true);
    }//GEN-LAST:event_btnCheckSalesActionPerformed
 
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Window".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(stocks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(stocks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(stocks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(stocks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new stocks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCheckSales;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabelSTOCKMAN;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelPRONAME;
    private javax.swing.JLabel labelPROQTY;
    private javax.swing.JLabel labelPROprice;
    private javax.swing.JLabel lblBGkape;
    private javax.swing.JLabel lblProdID;
    private javax.swing.JComboBox<String> productID;
    private javax.swing.JTable stocksTbl;
    private javax.swing.JTextField txtPName;
    private javax.swing.JTextField txtPPrice;
    private javax.swing.JTextField txtPQty;
    // End of variables declaration//GEN-END:variables

}