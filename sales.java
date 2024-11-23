
package SalesHistory;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Stocks.stocks;

public class sales extends javax.swing.JFrame {

    public sales() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadSales();
        loadDates();
    }

    private Connection connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/coffeetea";
        String user = "root";
        String password = "";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void loadSales() {
        DefaultTableModel model = (DefaultTableModel) SalesTbl.getModel();
        model.setRowCount(0);

        String query = "SELECT Username, Orders, Quantity, Size, Price, Subtotal, Date_and_Time FROM orders";
        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("Username");
                String orders = rs.getString("Orders");
                int quantity = rs.getInt("Quantity");
                String size = rs.getString("Size");
                double price = rs.getDouble("Price");
                double subtotal = rs.getDouble("Subtotal");
                Timestamp dateAndTime = rs.getTimestamp("Date_and_Time");

                model.addRow(new Object[]{username, orders, quantity, size, price, subtotal, dateAndTime});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading sales data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadDates() {
        cbDate.removeAllItems();
        String query = "SELECT DISTINCT DATE(Date_and_Time) AS SaleDate FROM orders";
        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Date saleDate = rs.getDate("SaleDate");
                cbDate.addItem(saleDate.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading dates: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        SalesTbl = new javax.swing.JTable();
        cbDate = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnSearchSales = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTotalProducts = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTotalSalesByDate = new javax.swing.JTextField();
        btnBackStocks = new javax.swing.JButton();
        btnEXIT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SALES HISTORY");

        SalesTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "USERNAME", "ORDERS", "QUANTITY", "SIZE", "PRICE", "SUBTOTAL", "DATE"
            }
        ));
        jScrollPane1.setViewportView(SalesTbl);

        cbDate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("DATE :");

        btnSearchSales.setBackground(new java.awt.Color(51, 153, 255));
        btnSearchSales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchSales.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchSales.setText("SEARCH");
        btnSearchSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchSalesActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("SALES HISTORY");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("TOTAL OF PRODUCTS SOLD : ");

        txtTotalProducts.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTotalProducts.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalProducts.setText("0");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("TOTAL OF SALES : ");

        txtTotalSalesByDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTotalSalesByDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalSalesByDate.setText("0");

        btnBackStocks.setBackground(new java.awt.Color(51, 153, 255));
        btnBackStocks.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBackStocks.setForeground(new java.awt.Color(255, 255, 255));
        btnBackStocks.setText("BACK");
        btnBackStocks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackStocksActionPerformed(evt);
            }
        });

        btnEXIT.setBackground(new java.awt.Color(255, 0, 51));
        btnEXIT.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEXIT.setForeground(new java.awt.Color(255, 255, 255));
        btnEXIT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu/icons8-fire-exit-30.png"))); // NOI18N
        btnEXIT.setText("EXIT");
        btnEXIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEXITActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(221, 221, 221)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnSearchSales)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel3)
                                .addComponent(txtTotalProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)
                                .addComponent(txtTotalSalesByDate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(499, 499, 499)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEXIT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBackStocks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(600, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchSales)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalSalesByDate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel2)
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(85, 85, 85)
                .addComponent(btnBackStocks, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnEXIT, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(254, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackStocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackStocksActionPerformed
        stocks sts = new stocks();
        sts.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackStocksActionPerformed

    private void btnSearchSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchSalesActionPerformed
        String selectedDate = cbDate.getSelectedItem().toString();
        String query = "SELECT SUM(Quantity) AS TotalProducts, SUM(Subtotal) AS TotalSales FROM orders WHERE DATE(Date_and_Time) = ?";

        try (Connection conn = connectToDatabase();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, selectedDate);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int totalProducts = rs.getInt("TotalProducts");
                double totalSales = rs.getDouble("TotalSales");

                txtTotalProducts.setText(String.valueOf(totalProducts));

                // Format the total sales as currency with peso sign
                String formattedTotalSales = String.format("₱%.2f", totalSales);
                txtTotalSalesByDate.setText(formattedTotalSales);
            } else {
                // If no results, set totals to 0
                txtTotalProducts.setText("0");
                txtTotalSalesByDate.setText("₱0.00");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching sales data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSearchSalesActionPerformed

    private void btnEXITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEXITActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnEXITActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable SalesTbl;
    private javax.swing.JButton btnBackStocks;
    private javax.swing.JButton btnEXIT;
    private javax.swing.JButton btnSearchSales;
    private javax.swing.JComboBox<String> cbDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtTotalProducts;
    private javax.swing.JTextField txtTotalSalesByDate;
    // End of variables declaration//GEN-END:variables
}
