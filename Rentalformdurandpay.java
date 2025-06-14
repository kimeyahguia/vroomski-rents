
package VRSgui;
import BackendVRS.Vehicles;
import VRSdatabase.DBConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import VRSgui.VehiclesUi;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import session.Clientsession;


public class Rentalformdurandpay extends javax.swing.JFrame {
    
    private int vehicleID;
    private int clientID;
    
    public Rentalformdurandpay() {
        initComponents();
        
         this.vehicleID = Clientsession.getInstance().getVehicleID();
        this.clientID = Clientsession.getInstance().getClientID();

        System.out.println("Vehicle ID: " + this.vehicleID);
        System.out.println("Client ID: " + this.clientID);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblLoginadmin = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        calculateTotalbrn = new javax.swing.JButton();
        rentalstarttxt = new javax.swing.JTextField();
        rentalendtxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        paymethod = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lastStepcontract = new javax.swing.JButton();
        paymentamt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        paymentdate = new javax.swing.JTextField();
        methoddetails = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblLoginadmin.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 27)); // NOI18N
        lblLoginadmin.setForeground(new java.awt.Color(255, 255, 255));
        lblLoginadmin.setText("RENTAL FORM ");

        jLabel135.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(255, 255, 255));
        jLabel135.setText("VROOMSKI RENTS");
        jLabel135.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(lblLoginadmin))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel135)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoginadmin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RENTAL USE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Swis721 BlkCn BT", 2, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel2.setText("RENTAL END");

        calculateTotalbrn.setBackground(new java.awt.Color(255, 255, 255));
        calculateTotalbrn.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        calculateTotalbrn.setText("CALCULATE TOTAL");
        calculateTotalbrn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateTotalbrnActionPerformed(evt);
            }
        });

        rentalstarttxt.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        rentalstarttxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentalstarttxtActionPerformed(evt);
            }
        });

        rentalendtxt.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        rentalendtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentalendtxtActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel4.setText("RENTAL START: ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calculateTotalbrn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rentalstarttxt, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(rentalendtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rentalstarttxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rentalendtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(calculateTotalbrn, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RENTAL PAYMENT", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Swis721 BlkCn BT", 2, 12))); // NOI18N

        paymethod.setBackground(new java.awt.Color(204, 204, 204));
        paymethod.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        paymethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Credit Card", "Check" }));
        paymethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymethodActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel1.setText("SELECT PAYMENT METHOD: ");

        jLabel5.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel5.setText("INPUT AMOUNT :");

        lastStepcontract.setBackground(new java.awt.Color(255, 255, 255));
        lastStepcontract.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        lastStepcontract.setText("CONTRACT");
        lastStepcontract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastStepcontractActionPerformed(evt);
            }
        });

        paymentamt.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        paymentamt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentamtActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel6.setText("INPUT PAYMENT DATE:");

        paymentdate.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        paymentdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentdateActionPerformed(evt);
            }
        });

        methoddetails.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        methoddetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                methoddetailsActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 14)); // NOI18N
        jLabel7.setText("INPUT METHOD DETAILS: ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(paymentdate, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(paymethod, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lastStepcontract, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                        .addComponent(paymentamt, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(methoddetails, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(methoddetails, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paymentamt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(paymethod, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paymentdate, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastStepcontract, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
    
    
    private double fetchServicePrice(int serviceID, Connection conn) throws SQLException {
    String query = "SELECT servicePrice FROM `services` WHERE ServiceID = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, serviceID);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("servicePrice");
            }
        }
    }
    throw new SQLException("Service with ID " + serviceID + " not found.");
}
    
    private void rentalstarttxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentalstarttxtActionPerformed
 
    }//GEN-LAST:event_rentalstarttxtActionPerformed

    private void rentalendtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentalendtxtActionPerformed
     
    }//GEN-LAST:event_rentalendtxtActionPerformed

    private void calculateTotalbrnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateTotalbrnActionPerformed
       String rentalStartText = rentalstarttxt.getText();
    String rentalEndText = rentalendtxt.getText();

    // Get the selected service ID from ClientSession
    int selectedServiceID = Clientsession.getInstance().getserviceID();

    try {
        // Parse the rental dates
        LocalDate rentalStart = LocalDate.parse(rentalStartText);
        LocalDate rentalEnd = LocalDate.parse(rentalEndText);

        // Calculate rental days
        long rentalDays = ChronoUnit.DAYS.between(rentalStart, rentalEnd);

        // Validate rental days
        if (rentalDays <= 0) {
            JOptionPane.showMessageDialog(this, "Rental end date must be after the start date.");
            return;
        }

        // Validate vehicle selection
        if (this.vehicleID == 0) {
            JOptionPane.showMessageDialog(this, "No vehicle selected. Please select a vehicle first.");
            return;
        }

        // Validate client session
        int currentClientID = Clientsession.getInstance().getClientID();
        if (currentClientID == 0) {
            JOptionPane.showMessageDialog(this, "No client is currently logged in. Please log in first.");
            return;
        }

        // Connect to the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerentalsystem", "root", "")) {
            // Query to get the vehicle price
            String checkQuery = "SELECT vehicleModel, vehiclePrice FROM (" +
                    "SELECT vehicleID, vehicleModel , vehiclePrice FROM vehiclecar " +
                    "UNION ALL " +
                    "SELECT vehicleID,  vehicleModel, vehiclePrice FROM vehiclevan " +
                    "UNION ALL " +
                    "SELECT vehicleID,  vehicleModel, vehiclePrice FROM vehicletruck " +
                    "UNION ALL " +
                    "SELECT vehicleID, vehicleModel, vehiclePrice FROM vehiclesuv " +
                    "UNION ALL " +
                    "SELECT vehicleID,  vehicleModel, vehiclePrice FROM vehiclemotor" +
                    ") AS allVehicles WHERE vehicleID = ?";

            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, this.vehicleID);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        // Fetch vehicle details
                        String vehicleModel = rs.getString("vehicleModel");
                        double vehiclePrice = rs.getDouble("vehiclePrice");

                        double servicePrice = fetchServicePrice(selectedServiceID, conn);
                        double totalPrice = (vehiclePrice + servicePrice) * rentalDays;

                        // Display a detailed breakdown
                        String breakdownMessage = String.format(
                            "Rental Price Breakdown:\n\n" +
                            "Selected Vehicle: %s\n" +
                            "Vehicle Price per Day: ₱%.2f\n\n" +
                            "Service Price per Day: ₱%.2f\n\n" +
                            "Rental Days: %d\n\n" +
                            "Calculation: (₱%.2f + ₱%.2f) x %d = ₱%.2f\n\n" +
                            "Total Price: ₱%.2f",
                            vehicleModel, vehiclePrice, 
                           servicePrice, 
                            rentalDays, 
                            vehiclePrice, servicePrice, rentalDays, totalPrice, 
                            totalPrice
                        );

                        JOptionPane.showMessageDialog(this, breakdownMessage, "Rental Price Breakdown", JOptionPane.INFORMATION_MESSAGE);

                        // Insert rental duration and total price into the database
                        String insertQuery = "INSERT INTO rentduration (clientID, rentalStart, rentalEnd, amountToPay) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, currentClientID);
                            insertStmt.setDate(2, java.sql.Date.valueOf(rentalStart));
                            insertStmt.setDate(3, java.sql.Date.valueOf(rentalEnd));
                            insertStmt.setDouble(4, totalPrice);

                            insertStmt.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Rental duration and amount successfully recorded!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Vehicle not found in the database. Please check the vehicle ID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    } catch (DateTimeParseException e) {
        JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid vehicle ID. Please enter a valid number.");
    }
    }//GEN-LAST:event_calculateTotalbrnActionPerformed

    private void lastStepcontractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastStepcontractActionPerformed
       String selectedPaymentMethod = (String) paymethod.getSelectedItem(); 
    // String selectedPaymentMethod = (String) paymethod.getSelectedItem();  // Get selected payment method

        // Get other payment details
        String paymentAmountText = paymentamt.getText();  // Payment amount field
        String paymentDateText = paymentdate.getText();  // Payment date field
        String methodDetails = methoddetails.getText();  // Method details (e.g., Card number or bank details)

        try {
            // Parse the payment amount to double
            double paymentAmount = Double.parseDouble(paymentAmountText);

            // Validate payment amount
            if (paymentAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Payment amount must be greater than 0.");
                return;
            }

            // Parse the payment date
            LocalDate paymentDate = LocalDate.parse(paymentDateText);  // Assuming the format is YYYY-MM-DD

            // Validate the payment date
            if (paymentDate.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Payment date cannot be in the past.");
                return;
            }

            // Get client ID and rental information from ClientSession
            int clientID = Clientsession.getInstance().getClientID();
            int vehicleID = Clientsession.getInstance().getVehicleID();  // Vehicle selected in the rental form
            String rentalStart = rentalstarttxt.getText();  // Start date of rental
            String rentalEnd = rentalendtxt.getText();  // End date of rental

            // Now, check if the amount to pay in rentduration matches the payment amount
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerentalsystem", "root", "")) {
                // Query to retrieve the rental amount to be paid for the given clientID and rental period
                String rentQuery = "SELECT amountToPay FROM rentduration WHERE clientID = ? AND rentalStart = ? AND rentalEnd = ?";
                try (PreparedStatement rentStmt = conn.prepareStatement(rentQuery)) {
                    rentStmt.setInt(1, clientID);  // Set clientID from session
                    rentStmt.setDate(2, java.sql.Date.valueOf(rentalStart));  // Set rentalStart
                    rentStmt.setDate(3, java.sql.Date.valueOf(rentalEnd));  // Set rentalEnd

                    try (ResultSet rentRs = rentStmt.executeQuery()) {
                        if (rentRs.next()) {
                            double amountToPay = rentRs.getDouble("amountToPay");

                            // Compare the amount to pay with the payment amount
                            if (paymentAmount != amountToPay) {
                                JOptionPane.showMessageDialog(this, "The payment amount does not match the amount to pay for the rental.");
                                return;
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "No rental record found for the provided clientID and rental dates.");
                            return;
                        }
                    }
                }

                // If payment amount matches, proceed with inserting payment details into the payment table
                String paymentInsertQuery = "INSERT INTO payment (ClientID, Date, amount, PaymentMethod, methodDetails) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement paymentStmt = conn.prepareStatement(paymentInsertQuery)) {
                    paymentStmt.setInt(1, clientID);  // Set Client ID from session
                    paymentStmt.setDate(2, java.sql.Date.valueOf(paymentDate));  // Payment Date
                    paymentStmt.setDouble(3, paymentAmount);  // Payment Amount
                    paymentStmt.setString(4, selectedPaymentMethod);  // Payment Method from combo box
                    paymentStmt.setString(5, methodDetails);  // Method Details (e.g., Card Number, Bank Account)

                    // Execute the payment insert query
                    int rowsInserted = paymentStmt.executeUpdate();

                    if (rowsInserted > 0) {
                        RentalContract next = new RentalContract();
                        next.setVisible(rootPaneCheckingEnabled);
                        dispose();
                       
                    } else {
                        JOptionPane.showMessageDialog(this, "Error recording payment.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid payment amount.");
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid payment date. Please use YYYY-MM-DD.");
        }
    }//GEN-LAST:event_lastStepcontractActionPerformed

    private void paymentamtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentamtActionPerformed
       
    }//GEN-LAST:event_paymentamtActionPerformed

    private void paymentdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentdateActionPerformed

    private void paymethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymethodActionPerformed
        
    }//GEN-LAST:event_paymethodActionPerformed

    private void methoddetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_methoddetailsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_methoddetailsActionPerformed

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
            java.util.logging.Logger.getLogger(Rentalformdurandpay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Rentalformdurandpay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Rentalformdurandpay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Rentalformdurandpay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        try {
        // Initialize the rental form and show it
        RentalForm rentalForm = new RentalForm();
        rentalForm.setVisible(true);

        // Example rental start and end dates (for demonstration purposes)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date rentalStart = (Date) dateFormat.parse("2024-12-01");
        Date rentalEnd = (Date) dateFormat.parse("2024-12-07");

        // Calculate rental duration in days
        long durationInMillis = rentalEnd.getTime() - rentalStart.getTime();
        int rentalDuration = (int) (durationInMillis / (1000 * 60 * 60 * 24));  // Convert milliseconds to days

        // Example vehicle price and rental cost calculation
        int vehicleId = 101;  // Example vehicle ID
        double vehiclePrice = 150.00;  // Price per day for the vehicle
        double totalAmount = rentalDuration * vehiclePrice;  // Total amount to pay

        // Print calculated rental duration and total amount
        System.out.println("Rental Duration: " + rentalDuration + " days");
        System.out.println("Total Amount: ₱" + totalAmount);

        // Here, you would insert the rental duration and payment details into your database
        // Example of insertion into database (not shown here)

    } catch (ParseException e) {
        e.printStackTrace();
    }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton calculateTotalbrn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton lastStepcontract;
    private javax.swing.JLabel lblLoginadmin;
    private javax.swing.JTextField methoddetails;
    private javax.swing.JTextField paymentamt;
    private javax.swing.JTextField paymentdate;
    private javax.swing.JComboBox<String> paymethod;
    private javax.swing.JTextField rentalendtxt;
    private javax.swing.JTextField rentalstarttxt;
    // End of variables declaration//GEN-END:variables
}
