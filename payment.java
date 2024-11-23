
package Payment;


import java.sql.*;
import javax.swing.*;
import Receipt.receipt;

public class payment extends javax.swing.JFrame {

    private String customerName;
    
    public payment(String customerName, double total) {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.customerName = customerName;
        txtTotalPay.setText("₱ " + String.format("%.2f", total));
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

    private void calculateChange() {
        try {
            double total = Double.parseDouble(txtTotalPay.getText().replace("₱ ", "").replace(",", ""));
            double payment = Double.parseDouble(txtPayment.getText());
            double changeAmount = payment - total;
            txtChange.setText("₱ " + String.format("%.2f", changeAmount));
        } catch (NumberFormatException e) {
            txtChange.setText("₱ 0.00");
        }
    }
    
    private void paymentsData() {
        String orderType = cbOrderType.getSelectedItem().toString();
        double totalAmount = Double.parseDouble(txtTotalPay.getText().replace("₱ ", "").replace(",", ""));
        double payment = Double.parseDouble(txtPayment.getText());
        double changeMoney = Double.parseDouble(txtChange.getText().replace("₱ ", "").replace(",", ""));

        String insertQuery = "INSERT INTO payments (CustomerName, OrderType, TotalAmount, Payment, ChangeMoney) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = connectToDatabase(); 
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, customerName);
            pstmt.setString(2, orderType);
            pstmt.setDouble(3, totalAmount);
            pstmt.setDouble(4, payment);
            pstmt.setDouble(5, changeMoney);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
            } else {
                JOptionPane.showMessageDialog(this, "Failed to record payment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnpayOK = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        change = new javax.swing.JLabel();
        txtTotalPay = new javax.swing.JTextField();
        txtChange = new javax.swing.JTextField();
        txtPayment = new javax.swing.JTextField();
        btnRECEIPT = new javax.swing.JButton();
        cbOrderType = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PAYMENT");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnpayOK.setBackground(new java.awt.Color(0, 102, 255));
        btnpayOK.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnpayOK.setForeground(new java.awt.Color(255, 255, 255));
        btnpayOK.setText("OK");
        btnpayOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpayOKActionPerformed(evt);
            }
        });
        jPanel2.add(btnpayOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 500, -1, 34));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PAY HERE :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 380, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TOTAL : ");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, -1, -1));

        change.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        change.setForeground(new java.awt.Color(255, 255, 255));
        change.setText("CHANGE :");
        jPanel2.add(change, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 550, -1, -1));

        txtTotalPay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel2.add(txtTotalPay, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 320, 190, 40));

        txtChange.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel2.add(txtChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 590, 190, 40));

        txtPayment.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel2.add(txtPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 420, 190, 40));

        btnRECEIPT.setBackground(new java.awt.Color(51, 102, 255));
        btnRECEIPT.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRECEIPT.setForeground(new java.awt.Color(255, 255, 255));
        btnRECEIPT.setText("PRINT RECEIPT");
        btnRECEIPT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRECEIPTActionPerformed(evt);
            }
        });
        jPanel2.add(btnRECEIPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 670, 190, 50));

        cbOrderType.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbOrderType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dine-In", "Take-Out" }));
        jPanel2.add(cbOrderType, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 140, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Order Type:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Payment/paymentBG (1).jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-140, -20, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnpayOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpayOKActionPerformed
        calculateChange();
        paymentsData();
    }//GEN-LAST:event_btnpayOKActionPerformed

    private void btnRECEIPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRECEIPTActionPerformed
        receipt rc = new receipt();
        rc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRECEIPTActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new payment("", 0.0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRECEIPT;
    private javax.swing.JButton btnpayOK;
    private javax.swing.JComboBox<String> cbOrderType;
    private javax.swing.JLabel change;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtChange;
    private javax.swing.JTextField txtPayment;
    private javax.swing.JTextField txtTotalPay;
    // End of variables declaration//GEN-END:variables
}
