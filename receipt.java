
package Receipt;

import javax.swing.JFrame;

public class receipt extends javax.swing.JFrame {

    public receipt() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelRECEIPT = new javax.swing.JLabel();
        jLabelNameShop = new javax.swing.JLabel();
        jLabelLINE2 = new javax.swing.JLabel();
        jLabelLINE = new javax.swing.JLabel();
        jLabelThankyou = new javax.swing.JLabel();
        jLabelLine1 = new javax.swing.JLabel();
        jLabelTOTAL = new javax.swing.JLabel();
        jLabelCHANGE = new javax.swing.JLabel();
        jLabelPAYMENT = new javax.swing.JLabel();
        jLabelQTY = new javax.swing.JLabel();
        jLabelSIZE = new javax.swing.JLabel();
        jLabelITEMS = new javax.swing.JLabel();
        jLabelPRICE = new javax.swing.JLabel();
        jLabelLine = new javax.swing.JLabel();
        jLableDateAndTime = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RECEIPT");

        jLabelRECEIPT.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelRECEIPT.setText("RECEIPT");

        jLabelNameShop.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNameShop.setText("CPR COFFEE AND TEA SHOP");

        jLabelLINE2.setText("******************************************************************************");

        jLabelLINE.setText("******************************************************************************");

        jLabelThankyou.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelThankyou.setText("THANK YOU FOR ORDERING!");

        jLabelLine1.setText("______________________________________________________________________________");

        jLabelTOTAL.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTOTAL.setText("TOTAL :");

        jLabelCHANGE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelCHANGE.setText("CHANGE : ");

        jLabelPAYMENT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelPAYMENT.setText("PAYMENT :");

        jLabelQTY.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelQTY.setText("QTY");

        jLabelSIZE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelSIZE.setText("SIZE");

        jLabelITEMS.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelITEMS.setText("ITEMS");

        jLabelPRICE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelPRICE.setText("PRICE");

        jLabelLine.setText("______________________________________________________________________________");

        jLableDateAndTime.setText("DATE AND TIME : ");

        jLabel1.setText("ORDER NO#");

        jLabel2.setText("CUSTOMER NAME :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(jLabelNameShop))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jLabelThankyou))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelPAYMENT)
                                    .addComponent(jLabelTOTAL)
                                    .addComponent(jLabelCHANGE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabelLINE2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelLINE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelLine1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelLine, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabelITEMS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelQTY)
                .addGap(18, 18, 18)
                .addComponent(jLabelSIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelPRICE)
                .addGap(53, 53, 53))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelRECEIPT)
                .addGap(145, 145, 145))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLableDateAndTime)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabelNameShop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelRECEIPT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLableDateAndTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLINE2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelITEMS)
                    .addComponent(jLabelQTY)
                    .addComponent(jLabelSIZE)
                    .addComponent(jLabelPRICE))
                .addGap(2, 2, 2)
                .addComponent(jLabelLine, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 243, Short.MAX_VALUE)
                .addComponent(jLabelLine1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTOTAL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPAYMENT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCHANGE)
                .addGap(9, 9, 9)
                .addComponent(jLabelLINE)
                .addGap(3, 3, 3)
                .addComponent(jLabelThankyou)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new receipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCHANGE;
    private javax.swing.JLabel jLabelITEMS;
    private javax.swing.JLabel jLabelLINE;
    private javax.swing.JLabel jLabelLINE2;
    private javax.swing.JLabel jLabelLine;
    private javax.swing.JLabel jLabelLine1;
    private javax.swing.JLabel jLabelNameShop;
    private javax.swing.JLabel jLabelPAYMENT;
    private javax.swing.JLabel jLabelPRICE;
    private javax.swing.JLabel jLabelQTY;
    private javax.swing.JLabel jLabelRECEIPT;
    private javax.swing.JLabel jLabelSIZE;
    private javax.swing.JLabel jLabelTOTAL;
    private javax.swing.JLabel jLabelThankyou;
    private javax.swing.JLabel jLableDateAndTime;
    // End of variables declaration//GEN-END:variables
}
