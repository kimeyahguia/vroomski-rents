
package VRSgui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
public class Vehicleinventoryadmin extends javax.swing.JFrame {

   
    public Vehicleinventoryadmin() {
        initComponents();
        displayInventory();
    }

    public void displayInventory(){
        DefaultTableModel model = (DefaultTableModel) vehicleInventory.getModel();
        model.setColumnIdentifiers(new String[]{"Vehicle Model", "Plate Number", "Vehicle Capacity", "Vehicle Price", "Vehicle Status"});
        
        String url = "jdbc:mysql://localhost:3306/vehiclerentalsystem";
        String user = "root";
        String pass = "";
        
         try (Connection conn = DriverManager.getConnection(url, user, pass);
         Statement smt = conn.createStatement()) {

        // Union query to combine all vehicle tables
        String query = "SELECT VehicleModel, PlateNumber, VehicleCapacity, VehiclePrice, VehicleStatus FROM vehiclevan " +
                       "UNION ALL " +
                       "SELECT VehicleModel, PlateNumber, VehicleCapacity, VehiclePrice, VehicleStatus FROM vehicletruck " +
                       "UNION ALL " +
                       "SELECT VehicleModel, PlateNumber, VehicleCapacity, VehiclePrice, VehicleStatus FROM vehiclesuv " +
                       "UNION ALL " +
                       "SELECT VehicleModel, PlateNumber, VehicleCapacity, VehiclePrice, VehicleStatus FROM vehiclemotor " +
                       "UNION ALL " +
                       "SELECT VehicleModel, PlateNumber, VehicleCapacity, VehiclePrice, VehicleStatus FROM vehiclecar";

        ResultSet rs = smt.executeQuery(query);

        while (rs.next()) {
            String vehicleModel = rs.getString("VehicleModel");
            String plateNumber = rs.getString("PlateNumber");
            int vehicleCapacity = rs.getInt("VehicleCapacity");
            double vehiclePrice = rs.getDouble("VehiclePrice");
            String vehicleStatus = rs.getString("VehicleStatus");

            // Add the retrieved data to the table model
            model.addRow(new Object[]{vehicleModel, plateNumber, vehicleCapacity, vehiclePrice, vehicleStatus});
        }
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        vehicleInventory = new javax.swing.JTable();
        backtomenu = new javax.swing.JButton();
        addvehicle1 = new javax.swing.JButton();
        deletevehicle1 = new javax.swing.JButton();
        updatevehicle = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        vehicleInventory.setBackground(new java.awt.Color(204, 204, 204));
        vehicleInventory.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        vehicleInventory.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 12)); // NOI18N
        vehicleInventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vehicle Model", "Plate Number", "Vehicle Capacity", "Vehicle Price", "Vehicle Status"
            }
        ));
        jScrollPane1.setViewportView(vehicleInventory);

        backtomenu.setBackground(new java.awt.Color(102, 102, 102));
        backtomenu.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        backtomenu.setForeground(new java.awt.Color(255, 255, 255));
        backtomenu.setText("BACK");
        backtomenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backtomenuActionPerformed(evt);
            }
        });

        addvehicle1.setBackground(new java.awt.Color(102, 102, 102));
        addvehicle1.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        addvehicle1.setForeground(new java.awt.Color(255, 255, 255));
        addvehicle1.setText("ADD VEHICLE");
        addvehicle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addvehicle1ActionPerformed(evt);
            }
        });

        deletevehicle1.setBackground(new java.awt.Color(102, 102, 102));
        deletevehicle1.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        deletevehicle1.setForeground(new java.awt.Color(255, 255, 255));
        deletevehicle1.setText("DELETE VEHICLE");
        deletevehicle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletevehicle1ActionPerformed(evt);
            }
        });

        updatevehicle.setBackground(new java.awt.Color(102, 102, 102));
        updatevehicle.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        updatevehicle.setForeground(new java.awt.Color(255, 255, 255));
        updatevehicle.setText("UPDATE VEHICLE");
        updatevehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatevehicleActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("VEHICLE INVERNTORY ADMIN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(420, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(updatevehicle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(deletevehicle1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addvehicle1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backtomenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addvehicle1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deletevehicle1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updatevehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                        .addComponent(backtomenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deletevehicle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletevehicle1ActionPerformed
           // Prompt the user for the vehicle model to delete
    String vehicleModel = JOptionPane.showInputDialog(this, "Enter Vehicle Model to delete:");
    if (vehicleModel != null && !vehicleModel.isEmpty()) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerentalsystem", "root", "")) {
            // Determine the table containing the vehicle model
            String tableName = null;
            String findTableQuery = "SELECT CASE "
                                   + "WHEN EXISTS (SELECT 1 FROM vehiclevan WHERE VehicleModel = ?) THEN 'vehiclevan' "
                                   + "WHEN EXISTS (SELECT 1 FROM vehicletruck WHERE VehicleModel = ?) THEN 'vehicletruck' "
                                   + "WHEN EXISTS (SELECT 1 FROM vehiclesuv WHERE VehicleModel = ?) THEN 'vehiclesuv' "
                                   + "WHEN EXISTS (SELECT 1 FROM vehiclemotor WHERE VehicleModel = ?) THEN 'vehiclemotor' "
                                   + "WHEN EXISTS (SELECT 1 FROM vehiclecar WHERE VehicleModel = ?) THEN 'vehiclecar' "
                                   + "ELSE NULL END AS TableName";

            try (PreparedStatement findStmt = conn.prepareStatement(findTableQuery)) {
                findStmt.setString(1, vehicleModel);
                findStmt.setString(2, vehicleModel);
                findStmt.setString(3, vehicleModel);
                findStmt.setString(4, vehicleModel);
                findStmt.setString(5, vehicleModel);
                try (ResultSet rs = findStmt.executeQuery()) {
                    if (rs.next()) {
                        tableName = rs.getString("TableName");
                    }
                }
            }

            if (tableName != null) {
                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to delete the vehicle model: " + vehicleModel + "?", 
                    "Confirm Deletion", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    // Execute the delete query
                    String deleteQuery = "DELETE FROM " + tableName + " WHERE VehicleModel = ?";
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                        deleteStmt.setString(1, vehicleModel);

                        int rowsDeleted = deleteStmt.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(this, "Successfully deleted Vehicle Model: " + vehicleModel);
                        } else {
                            JOptionPane.showMessageDialog(this, "Deletion failed! Vehicle not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vehicle Model not found in any table. Please check the model name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vehicle Model cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_deletevehicle1ActionPerformed

    private void backtomenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backtomenuActionPerformed
        adminmenu next = new adminmenu();
        next.setVisible(rootPaneCheckingEnabled);
        dispose();
    }//GEN-LAST:event_backtomenuActionPerformed

    private void updatevehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatevehicleActionPerformed
       // Prompt the user for the vehicle model to update
    String vehicleModel = JOptionPane.showInputDialog(this, "Enter Vehicle Model to update:");
    if (vehicleModel != null && !vehicleModel.isEmpty()) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerentalsystem", "root", "")) {
            // Determine the table containing the vehicle model
            String tableName = null;
            String findTableQuery = "SELECT CASE "
                                   + "WHEN EXISTS (SELECT 1 FROM vehiclevan WHERE VehicleModel = ?) THEN 'vehiclevan' "
                                   + "WHEN EXISTS (SELECT 1 FROM vehicletruck WHERE VehicleModel = ?) THEN 'vehicletruck' "
                                   + "WHEN EXISTS (SELECT 1 FROM vehiclesuv WHERE VehicleModel = ?) THEN 'vehiclesuv' "
                                   + "WHEN EXISTS (SELECT 1 FROM vehiclemotor WHERE VehicleModel = ?) THEN 'vehiclemotor' "
                                   + "WHEN EXISTS (SELECT 1 FROM vehiclecar WHERE VehicleModel = ?) THEN 'vehiclecar' "
                                   + "ELSE NULL END AS TableName";

            try (PreparedStatement findStmt = conn.prepareStatement(findTableQuery)) {
                findStmt.setString(1, vehicleModel);
                findStmt.setString(2, vehicleModel);
                findStmt.setString(3, vehicleModel);
                findStmt.setString(4, vehicleModel);
                findStmt.setString(5, vehicleModel);
                try (ResultSet rs = findStmt.executeQuery()) {
                    if (rs.next()) {
                        tableName = rs.getString("TableName");
                    }
                }
            }

            if (tableName != null) {
                // Prompt the user for which attribute to update
                String[] options = {"Plate Number", "Vehicle Capacity", "Vehicle Price", "Vehicle Status"};
                String choice = (String) JOptionPane.showInputDialog(
                        this,
                        "Which attribute do you want to update?",
                        "Select Attribute",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice != null) {
                    String newValue = JOptionPane.showInputDialog(this, "Enter new value for " + choice + ":");
                    if (newValue != null && !newValue.isEmpty()) {
                        String updateColumn = null;
                        String sqlValue = newValue;

                        switch (choice) {
                            case "Plate Number":
                                updateColumn = "PlateNumber";
                                break;
                            case "Vehicle Capacity":
                                updateColumn = "VehicleCapacity";
                                break;
                            case "Vehicle Price":
                                updateColumn = "VehiclePrice";
                            case "Vehicle Status":
                                updateColumn = "VehicleStatus";
                                try {
                                  
                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(this, "Invalid price format!", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                break;
                        }

                        if (updateColumn != null) {
                            String updateQuery = "UPDATE " + tableName + " SET " + updateColumn + " = ? WHERE VehicleModel = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                updateStmt.setString(1, sqlValue);
                                updateStmt.setString(2, vehicleModel);

                                int rowsUpdated = updateStmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    JOptionPane.showMessageDialog(this, "Successfully updated " + choice + " for Vehicle Model: " + vehicleModel);
                                } else {
                                    JOptionPane.showMessageDialog(this, "Update failed! Please check the details.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please provide a value.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No attribute selected for update.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vehicle Model not found in any table. Please check the model name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vehicle Model cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_updatevehicleActionPerformed

    private void addvehicle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addvehicle1ActionPerformed
    String type = JOptionPane.showInputDialog(this, "Enter Vehicle Type (e.g., Van, Truck, SUV, Motor, Car):");
    String model = JOptionPane.showInputDialog(this, "Enter Vehicle Model:");
    String plateNumber = JOptionPane.showInputDialog(this, "Enter Plate Number:");
    String capacity = JOptionPane.showInputDialog(this, "Enter Vehicle Capacity (e.g., number of passengers or load):");
    String priceStr = JOptionPane.showInputDialog(this, "Enter Vehicle Price (₱):");
    String status = JOptionPane.showInputDialog(this, "Enter Vehicle Status: ");

    if (type != null && model != null && plateNumber != null && capacity != null && priceStr != null &&
        !type.isEmpty() && !model.isEmpty() && !plateNumber.isEmpty() && !capacity.isEmpty() && !priceStr.isEmpty()) {

        try {
            double price = Double.parseDouble(priceStr); 
            
            DefaultTableModel tableModel = (DefaultTableModel) vehicleInventory.getModel();
            tableModel.addRow(new Object[]{ model, plateNumber, capacity, price, status});

            // Determine the correct database table based on vehicle type
            String tableName;
            switch (type.toLowerCase()) {
                case "van":
                    tableName = "vehiclevan";
                    break;
                case "truck":
                    tableName = "vehicletruck";
                    break;
                case "suv":
                    tableName = "vehiclesuv";
                    break;
                case "motor":
                    tableName = "vehiclemotor";
                    break;
                case "car":
                    tableName = "vehiclecar";
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid vehicle type! Please enter a valid type (Van, Truck, SUV, Motor, Car).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            // Insert into the specific database table
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerentalsystem", "root", "")) {
                // Get the next available ID dynamically
                String getMaxIdQuery = "SELECT MAX(VehicleID) FROM " + tableName;
                try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {
                    int nextId = 21; // Default start
                    if (rs.next() && rs.getInt(1) >= 20) {
                        nextId = rs.getInt(1) + 1; // Increment from the current max
                    }

                    String insertQuery = "INSERT INTO " + tableName + " (VehicleID, VehicleModel, PlateNumber, VehicleCapacity, VehiclePrice, VehicleStatus) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                        pstmt.setInt(1, nextId);
                        pstmt.setString(2, model);
                        pstmt.setString(3, plateNumber);
                        pstmt.setString(4, capacity);
                        pstmt.setDouble(5, price);
                        pstmt.setString(6, status); // Default status
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Vehicle added successfully to " + tableName + "!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Price must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Invalid input! Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_addvehicle1ActionPerformed

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
            java.util.logging.Logger.getLogger(Vehicleinventoryadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vehicleinventoryadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vehicleinventoryadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vehicleinventoryadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vehicleinventoryadmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addvehicle1;
    private javax.swing.JButton backtomenu;
    private javax.swing.JButton deletevehicle1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton updatevehicle;
    private javax.swing.JTable vehicleInventory;
    // End of variables declaration//GEN-END:variables
}
