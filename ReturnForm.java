
package VRSgui;
import VRSdatabase.DBConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.sql.*;
import java.util.Date;
import javax.swing.JOptionPane;


public class ReturnForm extends javax.swing.JFrame {

    
    public ReturnForm() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblLoginadmin = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        backtomenu = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtclientname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        vehicletype = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        vehiclemodel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rentstart = new javax.swing.JTextField();
        submitreturnform = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        rentend = new javax.swing.JTextField();
        scratchescbox = new javax.swing.JCheckBox();
        deepmarkscbox = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        damagecbox = new javax.swing.JCheckBox();
        repaircbox = new javax.swing.JCheckBox();
        severedamage = new javax.swing.JCheckBox();
        calculatedamage = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        clientid = new javax.swing.JTextField();
        cboxlatereturn = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblLoginadmin.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 30)); // NOI18N
        lblLoginadmin.setForeground(new java.awt.Color(255, 255, 255));
        lblLoginadmin.setText("RETURN FORM ");

        jLabel135.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(255, 255, 255));
        jLabel135.setText("VROOMSKI RENTS");
        jLabel135.setToolTipText("");

        backtomenu.setBackground(new java.awt.Color(204, 204, 204));
        backtomenu.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        backtomenu.setText("BACK");
        backtomenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backtomenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLoginadmin)
                    .addComponent(jLabel135))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backtomenu, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoginadmin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backtomenu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel1.setText("CLIENT NAME: ");

        txtclientname.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        txtclientname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtclientnameActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel2.setText("VEHICLE TYPE:");

        vehicletype.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        vehicletype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vehicletypeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel3.setText("VEHICLE MODEL:");

        vehiclemodel.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        vehiclemodel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vehiclemodelActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel4.setText("RENTAL START:");

        rentstart.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        rentstart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentstartActionPerformed(evt);
            }
        });

        submitreturnform.setBackground(new java.awt.Color(204, 204, 204));
        submitreturnform.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        submitreturnform.setText("PAY & SUBMIT");
        submitreturnform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitreturnformActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel5.setText("RENTAL END:");

        rentend.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        rentend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentendActionPerformed(evt);
            }
        });

        scratchescbox.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 15)); // NOI18N
        scratchescbox.setText("Have minimal scratches");
        scratchescbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scratchescboxActionPerformed(evt);
            }
        });

        deepmarkscbox.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 15)); // NOI18N
        deepmarkscbox.setText("Have deep marks/scracthes");
        deepmarkscbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deepmarkscboxActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        jLabel6.setText("CHARGES: ");

        damagecbox.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 15)); // NOI18N
        damagecbox.setText("Is the vehicle have damage");
        damagecbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                damagecboxActionPerformed(evt);
            }
        });

        repaircbox.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 15)); // NOI18N
        repaircbox.setText("Need of repair");
        repaircbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repaircboxActionPerformed(evt);
            }
        });

        severedamage.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 15)); // NOI18N
        severedamage.setText("Vehicle is severely damaged");
        severedamage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                severedamageActionPerformed(evt);
            }
        });

        calculatedamage.setBackground(new java.awt.Color(204, 204, 204));
        calculatedamage.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        calculatedamage.setText("CALCULATE");
        calculatedamage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatedamageActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel7.setText("CLIENT ID: ");

        clientid.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        clientid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientidActionPerformed(evt);
            }
        });

        cboxlatereturn.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 15)); // NOI18N
        cboxlatereturn.setText("Return vehicle late");
        cboxlatereturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxlatereturnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtclientname)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(vehicletype)
                    .addComponent(jLabel3)
                    .addComponent(vehiclemodel)
                    .addComponent(jLabel4)
                    .addComponent(rentstart)
                    .addComponent(jLabel5)
                    .addComponent(rentend, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(clientid, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(calculatedamage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deepmarkscbox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(damagecbox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(repaircbox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(severedamage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(submitreturnform, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scratchescbox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboxlatereturn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clientid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(deepmarkscbox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtclientname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(damagecbox, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vehicletype, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scratchescbox)
                        .addGap(76, 76, 76)
                        .addComponent(repaircbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(severedamage)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vehiclemodel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rentstart, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rentend, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cboxlatereturn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(calculatedamage, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitreturnform, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
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
    
    //estimated prices for damages
    private int latereturn = 2000;
    private int scratchCharge = 5000;    
    private int deepMarkCharge = 8000; 
    private int damageCharge = 15000;   
    private int repairCharge = 30000;   
    private int severeDamageCharge = 50000;

    private int totalCharges = 0;
    
    //query to bes ito gagamitin mo ang gagawin ay insert dyan ha 
    //SELECT * FROM `returnformhistory`
    //returnformID	clientName	vehicleType	vehicleModel	rentalStart	rentalEnd	charges	totalCharges	payedCharge	
    private void txtclientnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclientnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclientnameActionPerformed

    private void vehicletypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vehicletypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vehicletypeActionPerformed

    private void vehiclemodelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vehiclemodelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vehiclemodelActionPerformed

    private void rentstartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentstartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rentstartActionPerformed

    private void submitreturnformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitreturnformActionPerformed
      String clientId = clientid.getText();  
    String clientName = txtclientname.getText();
    String vehicleType = vehicletype.getText();  
    String vehicleModel = vehiclemodel.getText(); 
    String rentalStart = rentstart.getText();    
    String rentalEnd = rentend.getText();     

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerentalsystem", "root", "")) {

        // Check if client ID exists in the receipt table
        String checkClientQuery = "SELECT * FROM reciept WHERE clientId = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkClientQuery)) {
            checkStmt.setString(1, clientId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Client ID does not exist in the receipt table. Cannot proceed with form submission.", "Error", JOptionPane.ERROR_MESSAGE);
                return;  
            }
        }

        // Payment Process
        String paymentDay = JOptionPane.showInputDialog(this, "Enter the day of payment (e.g., '2024-11-24'):", "Payment Day", JOptionPane.QUESTION_MESSAGE);
        if (paymentDay == null || paymentDay.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid payment day.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] paymentMethods = {"Cash", "Credit Card", "Debit Card", "Online Transfer"};
        String selectedMethod = (String) JOptionPane.showInputDialog(
                this, 
                "Choose payment method:", 
                "Payment Method", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                paymentMethods, 
                paymentMethods[0]
        );
        if (selectedMethod == null || selectedMethod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please select a payment method.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int totalDue = totalCharges;  // Assuming totalCharges is already calculated
        double paymentAmount = 0;
        boolean validPayment = false;

        while (!validPayment) {
            String input = JOptionPane.showInputDialog(this, "Total due: " + totalDue + " Php\nEnter payment amount:", "Payment", JOptionPane.QUESTION_MESSAGE);

            try {
                paymentAmount = Double.parseDouble(input);

                if (paymentAmount == totalDue) {
                    validPayment = true;  // Payment is correct
                } else {
                    JOptionPane.showMessageDialog(this, "Payment is not correct. Please enter the exact amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Insert into returnformhistory table
        String insertQuery = "INSERT INTO returnformhistory (clientId, clientName, vehicleType, vehicleModel, rentalStart, rentalEnd, totalCharges, paymentMethod, payedCharge, payedDate) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, clientId);  
            stmt.setString(2, clientName);
            stmt.setString(3, vehicleType);
            stmt.setString(4, vehicleModel);
            stmt.setString(5, rentalStart);
            stmt.setString(6, rentalEnd);
            stmt.setInt(7, totalCharges);  
            stmt.setString(8, selectedMethod);   
            stmt.setDouble(9, paymentAmount); 
            stmt.setString(10, paymentDay);
  
            stmt.executeUpdate();

            // Update vehicle status to "available" in all possible vehicle tables
            String[] updateQueries = {
                "UPDATE vehiclecar SET vehicleStatus = 'available' WHERE vehicleModel = ?",
                "UPDATE vehiclevan SET vehicleStatus = 'available' WHERE vehicleModel = ?",
                "UPDATE vehicletruck SET vehicleStatus = 'available' WHERE vehicleModel = ?",
                "UPDATE vehiclesuv SET vehicleStatus = 'available' WHERE vehicleModel = ?",
                "UPDATE vehiclemotor SET vehicleStatus = 'available' WHERE vehicleModel = ?"
            };

            for (String updateQuery : updateQueries) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, vehicleModel);
                    updateStmt.executeUpdate();
                }
            }

            // Confirmation message
            JOptionPane.showMessageDialog(this, "Return form submitted successfully, and vehicle status updated to 'available'!");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error while submitting form: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // For debugging
    }
    }//GEN-LAST:event_submitreturnformActionPerformed

    private void rentendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rentendActionPerformed

    private void scratchescboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scratchescboxActionPerformed
        if (scratchescbox.isSelected()) {
        totalCharges += scratchCharge;
        } else{
        totalCharges -= scratchCharge;  
    }//GEN-LAST:event_scratchescboxActionPerformed
    }
    private void deepmarkscboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deepmarkscboxActionPerformed
        if (deepmarkscbox.isSelected()) {
        totalCharges += deepMarkCharge;  
    } else {
        totalCharges -= deepMarkCharge;  
    }
    }//GEN-LAST:event_deepmarkscboxActionPerformed

    private void damagecboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_damagecboxActionPerformed
        if (damagecbox.isSelected()) {
        totalCharges += damageCharge; 
    } else {
        totalCharges -= damageCharge; 
    }
    }//GEN-LAST:event_damagecboxActionPerformed

    private void repaircboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repaircboxActionPerformed
          if (repaircbox.isSelected()) {
        totalCharges += repairCharge; 
    } else {
        totalCharges -= repairCharge;
    }
    }//GEN-LAST:event_repaircboxActionPerformed

    private void severedamageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_severedamageActionPerformed
          if (severedamage.isSelected()) {
        totalCharges += severeDamageCharge;  
    } else {
        totalCharges -= severeDamageCharge;  
    }
    }//GEN-LAST:event_severedamageActionPerformed

    private void calculatedamageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatedamageActionPerformed
         JOptionPane.showMessageDialog(this, "Total Charges: " + totalCharges + " Php");
    }//GEN-LAST:event_calculatedamageActionPerformed

    private void clientidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientidActionPerformed

    private void cboxlatereturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxlatereturnActionPerformed
          if (cboxlatereturn.isSelected()) {
        totalCharges += latereturn;  
    } else {
        totalCharges -= latereturn;  
    }
    }//GEN-LAST:event_cboxlatereturnActionPerformed

    private void backtomenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backtomenuActionPerformed
       usermenu next = new usermenu();
       next.setVisible(rootPaneCheckingEnabled);
       dispose();
       
    }//GEN-LAST:event_backtomenuActionPerformed

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
            java.util.logging.Logger.getLogger(ReturnForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backtomenu;
    private javax.swing.JButton calculatedamage;
    private javax.swing.JCheckBox cboxlatereturn;
    private javax.swing.JTextField clientid;
    private javax.swing.JCheckBox damagecbox;
    private javax.swing.JCheckBox deepmarkscbox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblLoginadmin;
    private javax.swing.JTextField rentend;
    private javax.swing.JTextField rentstart;
    private javax.swing.JCheckBox repaircbox;
    private javax.swing.JCheckBox scratchescbox;
    private javax.swing.JCheckBox severedamage;
    private javax.swing.JButton submitreturnform;
    private javax.swing.JTextField txtclientname;
    private javax.swing.JTextField vehiclemodel;
    private javax.swing.JTextField vehicletype;
    // End of variables declaration//GEN-END:variables
}
