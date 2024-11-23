
package user_name;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import menu.menu;


public class username extends javax.swing.JFrame {

   
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
        setTitle("CUSTOMER NAME");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lSU.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lSU.setForeground(new java.awt.Color(255, 204, 102));
        lSU.setText("SignUp");
        getContentPane().add(lSU, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 310, -1, -1));

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
        bntOK.setForeground(new java.awt.Color(255, 255, 255));
        bntOK.setText("OK");
        bntOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOKActionPerformed(evt);
            }
        });
        getContentPane().add(bntOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 440, 70, 30));

        lBG.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\KAPE.jpg")); // NOI18N
        getContentPane().add(lBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1920, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed

    }//GEN-LAST:event_txtNameActionPerformed

    private void bntOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOKActionPerformed
        try {
            String customerName = txtName.getText();

            // Check if the name is empty
            if (customerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name first.");
                return;
            }

            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/coffeetea", "root", "");

            // Check if the customer name already exists
            PreparedStatement checkStmt = con.prepareStatement("SELECT COUNT(*) FROM customers WHERE customerName = ?");
            checkStmt.setString(1, customerName);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "The username '" + customerName + "' is already used. Please enter a different name.");
                txtName.setText("");
            } else {
                // If the name does not exist, proceed to insert it
                PreparedStatement ps = con.prepareStatement("INSERT INTO customers(customerName) VALUES(?)");
                ps.setString(1, customerName);
                ps.executeUpdate();

                // Proceed to the next screen
                this.setVisible(false);
                menu menuFrame = new menu();
                menuFrame.setUserName(customerName);
                menuFrame.setVisible(true);
            }

            // Close resources
            rs.close();
            checkStmt.close();
            con.close();

        } catch (Exception ex) {
            Logger.getLogger(username.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
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
