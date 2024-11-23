
package login;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Stocks.stocks;

public class login extends javax.swing.JFrame {
    
    private static final String adminPassword = "adeng2005";
    
    public login() {
        initComponents();
        Connect();
    }
    Connection con;
    PreparedStatement pst;
    
    public void Connect (){
        try {
            
            String Username = tfusername.getText();
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffeetea","root", "");
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        laberusername = new javax.swing.JLabel();
        laberpassword = new javax.swing.JLabel();
        tfusername = new javax.swing.JTextField();
        jpassword = new javax.swing.JPasswordField();
        btnloginOK = new javax.swing.JButton();
        labellogin = new javax.swing.JLabel();
        btnloginBACK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");

        laberusername.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        laberusername.setText("Enter username:");

        laberpassword.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        laberpassword.setText("Enter password:");

        btnloginOK.setBackground(new java.awt.Color(51, 102, 255));
        btnloginOK.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnloginOK.setForeground(new java.awt.Color(255, 255, 255));
        btnloginOK.setText("OK");
        btnloginOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginOKActionPerformed(evt);
            }
        });

        labellogin.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        labellogin.setForeground(new java.awt.Color(51, 102, 255));
        labellogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labellogin.setText("LOGIN");

        btnloginBACK.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronia\\Downloads\\icons8-back-30.png")); // NOI18N
        btnloginBACK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginBACKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnloginOK))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfusername)
                            .addComponent(jpassword, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(laberpassword)
                                    .addComponent(laberusername)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnloginBACK)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labellogin, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labellogin)
                    .addComponent(btnloginBACK, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(laberusername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfusername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(laberpassword)
                .addGap(18, 18, 18)
                .addComponent(jpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnloginOK)
                .addGap(24, 24, 24))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnloginOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginOKActionPerformed
        try {
            String Username = tfusername.getText();
            PreparedStatement ps = con.prepareStatement("INSERT INTO login(Username) VALUES(?)");
            
            ps.setString(1, Username);
            ps.executeUpdate();
            
            
            if(Username.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter your name first.");
            }
            
            String password = new String(jpassword.getPassword());
            
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty.");
                return;
            }
            
            if (password.equals(adminPassword)) {
                this.setVisible(false);
                stocks stockFrame = new stocks();
                stockFrame.setPassword(password);
                stockFrame.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(this, "You've entered the wrong username or password. Try again.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnloginOKActionPerformed

    private void btnloginBACKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginBACKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnloginBACKActionPerformed

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
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnloginBACK;
    private javax.swing.JButton btnloginOK;
    private javax.swing.JPasswordField jpassword;
    private javax.swing.JLabel labellogin;
    private javax.swing.JLabel laberpassword;
    private javax.swing.JLabel laberusername;
    private javax.swing.JTextField tfusername;
    // End of variables declaration//GEN-END:variables

}