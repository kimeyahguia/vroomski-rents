

package VRSgui;

public class adminmenu extends javax.swing.JFrame {

  
    public adminmenu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        homebtn = new javax.swing.JButton();
        exitbtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        showclient = new javax.swing.JButton();
        veinventory = new javax.swing.JButton();
        earnedmoney = new javax.swing.JButton();
        rentalhistory = new javax.swing.JButton();
        returnhistory = new javax.swing.JButton();

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\LOGO2.png")); // NOI18N
        jLabel2.setText("jLabel2");
        jLabel2.setPreferredSize(new java.awt.Dimension(250, 250));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        homebtn.setBackground(new java.awt.Color(204, 204, 204));
        homebtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        homebtn.setForeground(new java.awt.Color(0, 0, 102));
        homebtn.setText("BACK TO HOME");
        homebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homebtnActionPerformed(evt);
            }
        });

        exitbtn.setBackground(new java.awt.Color(204, 204, 204));
        exitbtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        exitbtn.setForeground(new java.awt.Color(0, 0, 102));
        exitbtn.setText("EXIT");
        exitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("VROOMSKI ADMIN!");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(homebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitbtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(homebtn)
                    .addComponent(exitbtn))
                .addContainerGap())
        );

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\LOGO2.png")); // NOI18N

        showclient.setBackground(new java.awt.Color(255, 255, 255));
        showclient.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        showclient.setForeground(new java.awt.Color(0, 0, 51));
        showclient.setText("SHOW CLIENT");
        showclient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showclientActionPerformed(evt);
            }
        });

        veinventory.setBackground(new java.awt.Color(255, 255, 255));
        veinventory.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        veinventory.setForeground(new java.awt.Color(0, 0, 51));
        veinventory.setText("VEHICLE INVENTORY");
        veinventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veinventoryActionPerformed(evt);
            }
        });

        earnedmoney.setBackground(new java.awt.Color(255, 255, 255));
        earnedmoney.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        earnedmoney.setForeground(new java.awt.Color(0, 0, 51));
        earnedmoney.setText("EARNED MONEY");
        earnedmoney.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                earnedmoneyActionPerformed(evt);
            }
        });

        rentalhistory.setBackground(new java.awt.Color(255, 255, 255));
        rentalhistory.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        rentalhistory.setForeground(new java.awt.Color(0, 0, 51));
        rentalhistory.setText("RENTAL HISTORY");
        rentalhistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentalhistoryActionPerformed(evt);
            }
        });

        returnhistory.setBackground(new java.awt.Color(255, 255, 255));
        returnhistory.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        returnhistory.setForeground(new java.awt.Color(0, 0, 51));
        returnhistory.setText("RETURN HISTORY");
        returnhistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnhistoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(veinventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(showclient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(earnedmoney, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rentalhistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(returnhistory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(showclient, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(veinventory, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(earnedmoney, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rentalhistory, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(returnhistory, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homebtnActionPerformed
        userOrAdmin next = new userOrAdmin ();
        next.setVisible(rootPaneCheckingEnabled);
        dispose();
    }//GEN-LAST:event_homebtnActionPerformed

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
        exitSystem next = new exitSystem();
        next.setVisible(rootPaneCheckingEnabled);
        dispose();
    }//GEN-LAST:event_exitbtnActionPerformed

    private void veinventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_veinventoryActionPerformed
       Vehicleinventoryadmin next = new Vehicleinventoryadmin();
       next.setVisible(rootPaneCheckingEnabled);
       dispose();
    }//GEN-LAST:event_veinventoryActionPerformed

    private void showclientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showclientActionPerformed
        ClientlistAdmin next = new ClientlistAdmin();
        next.setVisible(rootPaneCheckingEnabled);
        dispose();
    }//GEN-LAST:event_showclientActionPerformed

    private void earnedmoneyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_earnedmoneyActionPerformed
        EarnedmoneyofVroomski next = new EarnedmoneyofVroomski();
        next.setVisible(rootPaneCheckingEnabled);
        dispose();
                
    }//GEN-LAST:event_earnedmoneyActionPerformed

    private void rentalhistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentalhistoryActionPerformed
        rentalHistorytab next = new rentalHistorytab();
        next.setVisible(rootPaneCheckingEnabled);
        dispose();
    }//GEN-LAST:event_rentalhistoryActionPerformed

    private void returnhistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnhistoryActionPerformed
        returnHistorytab next = new returnHistorytab();
        next.setVisible(rootPaneCheckingEnabled);
        dispose();
    }//GEN-LAST:event_returnhistoryActionPerformed

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
            java.util.logging.Logger.getLogger(adminmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminmenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton earnedmoney;
    private javax.swing.JButton exitbtn;
    private javax.swing.JButton homebtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton rentalhistory;
    private javax.swing.JButton returnhistory;
    private javax.swing.JButton showclient;
    private javax.swing.JButton veinventory;
    // End of variables declaration//GEN-END:variables
}
