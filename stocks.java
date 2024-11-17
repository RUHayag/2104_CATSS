
package Stocks;

import javax.swing.*;
import menu.menu;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class stocks extends javax.swing.JFrame {

    private String userName;
    private String password;
    
    public void setuserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public stocks() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Connect();
    }
    
    Connection con;
    PreparedStatement pst;
    
    public void Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jbdc:mysql://localhost:3306/stocks","root","");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(stocks.class.getName()).log(Level.SEVERE, null, ex);
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
        btnNew = new javax.swing.JButton();
        lblProdID = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        lblBGkape = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelSTOCKMAN.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelSTOCKMAN.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSTOCKMAN.setText("STOCK MANAGEMENT");
        jPanel1.add(jLabelSTOCKMAN, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 27, 288, 44));

        labelPRONAME.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelPRONAME.setForeground(new java.awt.Color(255, 255, 255));
        labelPRONAME.setText("PRODUCT NAME:");
        jPanel1.add(labelPRONAME, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 140, 160, 26));

        labelPROprice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelPROprice.setForeground(new java.awt.Color(255, 255, 255));
        labelPROprice.setText("PRODUCT PRICE:");
        jPanel1.add(labelPROprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 222, 160, 26));

        labelPROQTY.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelPROQTY.setForeground(new java.awt.Color(255, 255, 255));
        labelPROQTY.setText("PRODUCT QTY:");
        jPanel1.add(labelPROQTY, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 309, 160, 26));

        txtPName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtPName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPNameActionPerformed(evt);
            }
        });
        jPanel1.add(txtPName, new org.netbeans.lib.awtextra.AbsoluteConstraints(267, 140, 304, -1));

        txtPPrice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtPPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPPriceActionPerformed(evt);
            }
        });
        jPanel1.add(txtPPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(267, 220, 304, -1));

        txtPQty.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel1.add(txtPQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(267, 304, 304, -1));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setText("ADD");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 406, 116, 39));

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setText("UPDATE");
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 406, 116, 39));

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setText("DELETE");
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 406, 116, 39));

        btnNew.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNew.setText("NEW");
        jPanel1.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(452, 406, 119, 39));

        lblProdID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblProdID.setForeground(new java.awt.Color(255, 255, 255));
        lblProdID.setText("PRODUCT ID:");
        jPanel1.add(lblProdID, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, 120, 26));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 531, 110, -1));

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch.setText("SEARCH");
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(481, 579, 90, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(631, 140, 853, 475));

        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBack.setText("BACK");
        btnBack.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jPanel1.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(1365, 695, 119, 39));

        lblBGkape.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\kapebg.jpg")); // NOI18N
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
            String pname = txtPName.getText();
            String price = txtPPrice.getText();
            String qty = txtPQty.getText();
            
            pst = con.prepareStatement("INSERT INTO product_tbl (pname,price,qty)VALUES(?,?,?)");
            pst.setString(1, pname);
            pst.setString(2, price);
            pst.setString(3, qty);
            
            int k = pst.executeUpdate();
            
            if(k==1){
                JOptionPane.showMessageDialog(this, "Record Added Successfuly!");
                txtPName.setText("");
                txtPPrice.setText("");
                txtPQty.setText("");       
            } else{
                JOptionPane.showMessageDialog(this, "Record Failed to Save!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(stocks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtPNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPNameActionPerformed
 
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabelSTOCKMAN;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel labelPRONAME;
    private javax.swing.JLabel labelPROQTY;
    private javax.swing.JLabel labelPROprice;
    private javax.swing.JLabel lblBGkape;
    private javax.swing.JLabel lblProdID;
    private javax.swing.JTextField txtPName;
    private javax.swing.JTextField txtPPrice;
    private javax.swing.JTextField txtPQty;
    // End of variables declaration//GEN-END:variables

}