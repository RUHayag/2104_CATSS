/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package user_name;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import menu.menu;

/**
 *
 * @author ronia
 */
public class username extends javax.swing.JFrame {

    /**
     * Creates new form username
     */
    public username() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lSU = new javax.swing.JLabel();
        lName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        bntOK = new javax.swing.JButton();
        lBG = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lSU.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lSU.setForeground(new java.awt.Color(255, 0, 51));
        lSU.setText("SignUp");
        getContentPane().add(lSU, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 310, -1, -1));

        lName.setBackground(new java.awt.Color(204, 204, 204));
        lName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lName.setForeground(new java.awt.Color(204, 204, 204));
        lName.setText("NAME:");
        getContentPane().add(lName, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 387, 73, -1));

        txtName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        getContentPane().add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(693, 379, 206, 39));

        bntOK.setBackground(new java.awt.Color(51, 153, 255));
        bntOK.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bntOK.setText("OK");
        bntOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOKActionPerformed(evt);
            }
        });
        getContentPane().add(bntOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 440, 60, -1));

        lBG.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\KAPE.jpg")); // NOI18N
        getContentPane().add(lBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1920, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed

    }//GEN-LAST:event_txtNameActionPerformed

    private void bntOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOKActionPerformed
        try {
            String customerName = txtName.getText();
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/coffeetea","root","");
            PreparedStatement ps = con.prepareStatement("insert into customers(customerName)values(?)");
            ps.setString(1, customerName);
            ps.executeUpdate();
            
            if(customerName.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter your name first.");
            }
            else {
                this.setVisible(false);
                
                menu menuFrame = new menu();
                menuFrame.setUserName(customerName);
                menuFrame.setVisible(true);
                
            }
        } catch (Exception ex) {
            Logger.getLogger(username.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_bntOKActionPerformed

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
            java.util.logging.Logger.getLogger(username.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(username.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(username.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(username.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new username().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntOK;
    private javax.swing.JLabel lBG;
    private javax.swing.JLabel lName;
    private javax.swing.JLabel lSU;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
