
package Payment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class payment extends javax.swing.JFrame {

    public payment() {
        this("Default Product", 0.0, 0, "Default Size", 0.0);
    }
    
    public payment(String productName, double price, int quantity, String size, double total) {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        populateCartTable(productName, price, quantity, size, total);
    }

    private void populateCartTable(String productName, double price, int quantity, String size, double total) {
        DefaultTableModel model = (DefaultTableModel) OrdersTbl.getModel();
        model.setRowCount(0); // Clear existing rows
        model.addRow(new Object[]{productName, price, quantity, size, total});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        btnpayOK = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        OrdersTbl = new javax.swing.JTable();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jLabel4 = new javax.swing.JLabel();
        ADD_ORDER = new javax.swing.JButton();
        change = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnRECEIPT = new javax.swing.JButton();
        BGpayment = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("YOUR ORDER");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, 44));

        jTextPane1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jScrollPane2.setViewportView(jTextPane1);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 470, 196, 43));

        btnpayOK.setBackground(new java.awt.Color(0, 102, 255));
        btnpayOK.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnpayOK.setForeground(new java.awt.Color(255, 255, 255));
        btnpayOK.setText("OK");
        jPanel2.add(btnpayOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(1340, 540, -1, 34));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PAY HERE :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1208, 432, -1, -1));

        OrdersTbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        OrdersTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "PRODUCT NAME", "PRICE", "QUANTITY", "SIZE", "SUBTOTAL"
            }
        ));
        jScrollPane3.setViewportView(OrdersTbl);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 907, 521));

        jRadioButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setText("TAKE-OUT");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1208, 246, 130, -1));

        jRadioButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setText("DINE-IN");
        jPanel2.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1208, 203, 98, -1));

        jTextPane2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jScrollPane4.setViewportView(jTextPane2);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 360, 196, 43));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TOTAL : ");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1208, 323, -1, -1));

        ADD_ORDER.setBackground(new java.awt.Color(51, 102, 255));
        ADD_ORDER.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ADD_ORDER.setForeground(new java.awt.Color(255, 255, 255));
        ADD_ORDER.setText("ADD ORDER");
        jPanel2.add(ADD_ORDER, new org.netbeans.lib.awtextra.AbsoluteConstraints(1249, 138, -1, 47));

        change.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        change.setForeground(new java.awt.Color(255, 255, 255));
        change.setText("CHANGE :");
        jPanel2.add(change, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 610, -1, -1));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 650, 190, 40));

        btnRECEIPT.setBackground(new java.awt.Color(51, 102, 255));
        btnRECEIPT.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRECEIPT.setForeground(new java.awt.Color(255, 255, 255));
        btnRECEIPT.setText("PRINT RECEIPT");
        jPanel2.add(btnRECEIPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 710, 190, 50));

        BGpayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Payment/payBG.jpg"))); // NOI18N
        jPanel2.add(BGpayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

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
                new payment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ADD_ORDER;
    private javax.swing.JLabel BGpayment;
    private javax.swing.JTable OrdersTbl;
    private javax.swing.JButton btnRECEIPT;
    private javax.swing.JButton btnpayOK;
    private javax.swing.JLabel change;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    // End of variables declaration//GEN-END:variables
}
