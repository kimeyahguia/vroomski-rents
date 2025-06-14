
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
import java.util.logging.Level;
import java.util.logging.Logger;
import session.Clientsession;


public class VehiclesUi extends javax.swing.JFrame {
    private Vehicles selectedVehicle;

   
      private int clientId;
    public VehiclesUi(int clientId) {
        this.clientId = clientId;
        initComponents();  // Initializes the form components
    }
    
    // Example button action listener
    private void rentButtonActionPerformed(java.awt.event.ActionEvent evt) {
       String vehicleIdInput = JOptionPane.showInputDialog(this, "Enter the ID of the vehicle to rent:");
    try {
        int vehicleId = Integer.parseInt(vehicleIdInput);
        int clientID = Clientsession.getInstance().getClientID();

        
        rentVehicle(vehicleId, clientID);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid vehicle ID. Please enter a valid number.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        ex.printStackTrace();
    }
    }
    
   private void showAvailableVehicles(Connection conn) {
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM vehiclecar WHERE vehicleStatus = 'AVAILABLE' UNION ALL " +
                                          "SELECT * FROM vehiclevan WHERE vehicleStatus = 'AVAILABLE' UNION ALL " +
                                          "SELECT * FROM vehicletruck WHERE vehicleStatus = 'AVAILABLE' UNION ALL " +
                                          "SELECT * FROM vehiclesuv WHERE vehicleStatus = 'AVAILABLE' UNION ALL " +
                                          "SELECT * FROM vehiclemotor WHERE vehicleStatus = 'AVAILABLE'")) {
        StringBuilder availableVehicles = new StringBuilder("Available Vehicles:\n");
        while (rs.next()) {
            availableVehicles.append("ID: ").append(rs.getInt("vehicleID"))
                             .append(" | Name: ").append(rs.getString("vehicleModel"))
                             .append(" | Status: ").append(rs.getString("vehicleStatus")).append("\n");
        }
        JOptionPane.showMessageDialog(this, availableVehicles.toString(), "Available Vehicles", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
     
   private String getVehicleNameById(Connection conn, int vehicleId) throws SQLException {
    String query = "SELECT vehicleName FROM (" +
                   "SELECT vehicleID, vehicleModel AS vehicleName FROM vehiclecar " +
                   "UNION ALL SELECT vehicleID, vehicleModel FROM vehiclevan " +
                   "UNION ALL SELECT vehicleID, vehicleModel FROM vehicletruck " +
                   "UNION ALL SELECT vehicleID, vehicleModel FROM vehiclesuv " +
                   "UNION ALL SELECT vehicleID, vehicleModel FROM vehiclemotor) AS allVehicles " +
                   "WHERE vehicleID = ?";

    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, vehicleId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("vehicleName");
            }
        }
    }
    return "Unknown Vehicle";
}
   
 private void addServicesToRental(int clientID, int vehicleID, Connection conn) {
        int addServices = JOptionPane.showConfirmDialog(null,
            "Would you like to add services to your rental?",
            "Add Services", JOptionPane.YES_NO_OPTION);

    if (addServices == JOptionPane.YES_OPTION) {
        // Fetch and show available services
        String fetchServicesQuery = "SELECT * FROM services";
        StringBuilder availableServices = new StringBuilder("Available Services:\n");
        boolean hasServices = false;

        try (PreparedStatement stmt = conn.prepareStatement(fetchServicesQuery);
             ResultSet serviceRs = stmt.executeQuery()) {
            while (serviceRs.next()) {
                hasServices = true;
                int serviceId = serviceRs.getInt("ServiceID");
                String driverName = serviceRs.getString("driverName");
                String roadAssistant = serviceRs.getString("roadassistantName");
                String carSeatType = serviceRs.getString("carSeatType");
                double servicePrice = serviceRs.getDouble("servicePrice");

                // Add each service's details to the string builder
                availableServices.append("Service ID: " + serviceId + " | Driver: " + driverName
                        + " | Road Assistance: " + roadAssistant + " | Car Seat Type: " + carSeatType
                        + " | Price: ₱" + servicePrice + "\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesUi.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!hasServices) {
            JOptionPane.showMessageDialog(null, "No services available.");
            return;
        }

        // Show available services to the user
        int serviceID = Integer.parseInt(JOptionPane.showInputDialog(null, availableServices.toString() + "\nEnter the Service ID you want to add:"));

        // Fetch the service details for the selected service ID
        String serviceQuery = "SELECT * FROM services WHERE ServiceID = ?";
        try (PreparedStatement serviceStmt = conn.prepareStatement(serviceQuery)) {
            serviceStmt.setInt(1, serviceID);
            try (ResultSet serviceRs = serviceStmt.executeQuery()) {
                if (serviceRs.next()) {
                    double servicePrice = serviceRs.getDouble("servicePrice");

                    // Ask user if they want to add this service
                    int confirmService = JOptionPane.showConfirmDialog(null,
                            "You have selected a service with a cost of ₱" + servicePrice + ". Do you want to add this service?",
                            "Confirm Service Addition", JOptionPane.YES_NO_OPTION);

                    if (confirmService == JOptionPane.YES_OPTION) {
                        // Insert the service into rentedservices table
                        String insertServiceQuery = "INSERT INTO rentedservices (clientID, vehicleID, serviceID, servicePrice) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertServiceStmt = conn.prepareStatement(insertServiceQuery)) {
                            insertServiceStmt.setInt(1, clientID);
                            insertServiceStmt.setInt(2, vehicleID);
                            insertServiceStmt.setInt(3, serviceID);
                            insertServiceStmt.setDouble(4, servicePrice);
                            insertServiceStmt.executeUpdate();
                        }

                        // Store the selected service ID in ClientSession
                        Clientsession.getInstance().setserviceID(serviceID);

                        JOptionPane.showMessageDialog(null, "Service added successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid service ID selected.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching service details: " + e.getMessage());
        }
    }

}
    
    public void rentVehicle(int vehicleID, int clientID) {
    
try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerentalsystem", "root", "")) {

    boolean vehicleAvailable = false;

    while (true) {  // Start a loop to allow repeated vehicle selection
        // Debug: Show the current vehicle ID
        System.out.println("Current Vehicle ID: " + vehicleID);

        // Unified query across all vehicle tables
        String checkQuery = "SELECT vehicleID, vehicleModel, vehiclePrice, vehicleStatus, tableName FROM (" +
                "SELECT vehicleID, vehicleModel, vehiclePrice AS vehiclePrice, vehicleStatus, 'vehiclecar' AS tableName FROM vehiclecar " +
                "UNION ALL " +
                "SELECT vehicleID, vehicleModel, vehiclePrice AS vehiclePrice, vehicleStatus, 'vehiclevan' AS tableName FROM vehiclevan " +
                "UNION ALL " +
                "SELECT vehicleID, vehicleModel, vehiclePrice AS vehiclePrice, vehicleStatus, 'vehicletruck' AS tableName FROM vehicletruck " +
                "UNION ALL " +
                "SELECT vehicleID, vehicleModel AS vehicleModel, vehiclePrice AS vehiclePrice, vehicleStatus, 'vehiclesuv' AS tableName FROM vehiclesuv " +
                "UNION ALL " +
                "SELECT vehicleID, vehicleModel, vehiclePrice AS vehiclePrice, vehicleStatus, 'vehiclemotor' AS tableName FROM vehiclemotor" +
                ") AS allVehicles WHERE vehicleID = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, vehicleID);  // This is where vehicleID is being used
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    String vehicleModel = rs.getString("vehicleModel");
                    double vehiclePrice = rs.getDouble("vehiclePrice");
                    String vehicleStatus = rs.getString("vehicleStatus");
                    String tableName = rs.getString("tableName");

                    // Debug: Show vehicle details
                    System.out.println("Vehicle Model: " + vehicleModel);
                    System.out.println("Vehicle Status: " + vehicleStatus);

                    if ("UNAVAILABLE".equalsIgnoreCase(vehicleStatus)) {
                        JOptionPane.showMessageDialog(null, vehicleModel + " is unavailable. Please choose another vehicle.");
                        
                        // Show available vehicles again if unavailable
                        showAvailableVehicles(conn); 

                        // Prompt user for another vehicle ID
                        String newVehicleID = JOptionPane.showInputDialog(null,
                                "Enter a different Vehicle ID:",
                                "Vehicle Unavailable",
                                JOptionPane.INFORMATION_MESSAGE);

                        if (newVehicleID == null || newVehicleID.trim().isEmpty()) {
                            // If no vehicle ID is entered, exit the method
                            return;
                        }

                        vehicleID = Integer.parseInt(newVehicleID); // Update vehicleID and recheck
                        continue;  // Re-check the availability of the new vehicle
                    }

                    // Confirm rental if vehicle is available
                    int confirmRental = JOptionPane.showConfirmDialog(null,
                            vehicleModel + " is available for rent at ₱" + vehiclePrice + ". Do you want to rent this vehicle?",
                            "Confirm Rental", JOptionPane.YES_NO_OPTION);

                    if (confirmRental == JOptionPane.YES_OPTION) {
                        // Insert rental record into the rentedvehicles table
                        String insertQuery = "INSERT INTO rentedvehicles (clientID, vehicleID, vehicleName, vehiclePrice) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, clientID);  // Using clientID from ClientSession
                            insertStmt.setInt(2, vehicleID);
                            insertStmt.setString(3, vehicleModel);
                            insertStmt.setDouble(4, vehiclePrice);

                            insertStmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, vehicleModel + " rented successfully!");

                            // Update the vehicle's status to UNAVAILABLE
                            String updateStatusQuery = "UPDATE " + tableName + " SET vehicleStatus = 'UNAVAILABLE' WHERE vehicleID = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateStatusQuery)) {
                                updateStmt.setInt(1, vehicleID);
                                updateStmt.executeUpdate();
                            }

                            // Call the method to add services to rental
                            addServicesToRental(clientID, vehicleID, conn);

                            // Ask the user if they want to rent another vehicle
                            int rentAnother = JOptionPane.showConfirmDialog(null,
                                    "Would you like to rent another vehicle?",
                                    "Rent Another Vehicle", JOptionPane.YES_NO_OPTION);

                            if (rentAnother == JOptionPane.NO_OPTION) {
                                // User does not want to rent another vehicle, proceed to next panel
                                JOptionPane.showMessageDialog(null, "Thank you for renting!");

                                // Set vehicleID in the ClientSession before proceeding to the next panel
                                Clientsession.getInstance().setVehicleID(vehicleID);  // Store vehicleID in session

                                // Proceed to the next panel, passing selected vehicle to the next form
                                Rentalformdurandpay next = new Rentalformdurandpay();  // Pass vehicleID to next panel
                                next.setVisible(true); 
                                dispose();  // Close current panel
                                break;  // Exit the loop and finish the process
                            } else {
                                vehicleAvailable = false;  // Continue the loop to choose another vehicle
                                break;  // This will allow the user to select another vehicle
                            }
                        } catch (SQLException insertException) {
                            insertException.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error inserting rental record: " + insertException.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Operation cancelled.");
                        return;  // Exit if rental is cancelled
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vehicle with ID " + vehicleID + " not found.");

                    // Prompt user for another vehicle ID if the current vehicle is not found
                    String newVehicleID = JOptionPane.showInputDialog(null,
                            "Enter a different Vehicle ID:",
                            "Vehicle Not Found",
                            JOptionPane.INFORMATION_MESSAGE);

                    if (newVehicleID == null || newVehicleID.trim().isEmpty()) {
                        return;  // Exit if no new ID is entered
                    }

                    vehicleID = Integer.parseInt(newVehicleID); // Update the vehicleID and recheck
                }
            }
        }
    }
} catch (SQLException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
}
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        rentBMW2 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        rentHondaCity = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        rentToyotaVios = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        rentMirage = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        rentFordEverest = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        rentFortuner = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        rentIsuzu = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        rentMontero = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        toyotahaicebtn = new javax.swing.JButton();
        jLabel114 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        nissanurvannv350btn = new javax.swing.JButton();
        jLabel119 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        hyundaistarexbtn = new javax.swing.JButton();
        jLabel124 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        suzukiapvbtn = new javax.swing.JButton();
        jLabel129 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel151 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        mitsubishil300btn = new javax.swing.JButton();
        jLabel156 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel157 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        hino300seriesbtn = new javax.swing.JButton();
        jLabel161 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel162 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jLabel165 = new javax.swing.JLabel();
        isuzuelfdropsidebtn = new javax.swing.JButton();
        jLabel166 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        dongfengcaptainebtn = new javax.swing.JButton();
        jLabel171 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jLabel130 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        hondaclick125btn = new javax.swing.JButton();
        jLabel135 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        suzukiburgmanstreetbtn = new javax.swing.JButton();
        jLabel140 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        vespaprimaverabtn = new javax.swing.JButton();
        jLabel145 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        yamahanmax155btn = new javax.swing.JButton();
        jLabel150 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        homebtn = new javax.swing.JButton();
        rentalformbtn = new javax.swing.JButton();
        vehicleExitbtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane3.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane3.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane3.setFont(new java.awt.Font("Swis721 Blk BT", 1, 14)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CAR SEDAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Swis721 BlkCn BT", 0, 24))); // NOI18N
        jPanel7.setToolTipText("CAR");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setForeground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("BMW M3 - 2019");
        jLabel13.setToolTipText("");

        jLabel14.setBackground(new java.awt.Color(0, 51, 102));
        jLabel14.setForeground(new java.awt.Color(0, 51, 102));
        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\cars sedan\\BMW NA TALAGA.jpg")); // NOI18N

        jLabel15.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("11,000/day");
        jLabel15.setToolTipText("");

        rentBMW2.setBackground(new java.awt.Color(255, 255, 255));
        rentBMW2.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        rentBMW2.setForeground(new java.awt.Color(0, 0, 102));
        rentBMW2.setText("RENT");
        rentBMW2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentBMW2ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("4-5 Seats");
        jLabel23.setToolTipText("");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel13))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(rentBMW2)
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rentBMW2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setForeground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("HONDA CITY - 4TH GEN");
        jLabel16.setToolTipText("");

        jLabel17.setBackground(new java.awt.Color(0, 51, 102));
        jLabel17.setForeground(new java.awt.Color(0, 51, 102));
        jLabel17.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\cars sedan\\Honda City .jpg")); // NOI18N

        jLabel18.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("2,899/day");
        jLabel18.setToolTipText("");

        rentHondaCity.setBackground(new java.awt.Color(255, 255, 255));
        rentHondaCity.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        rentHondaCity.setForeground(new java.awt.Color(0, 0, 102));
        rentHondaCity.setText("RENT");
        rentHondaCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentHondaCityActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("4-5 Seats");
        jLabel24.setToolTipText("");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(rentHondaCity))
                            .addComponent(jLabel24))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel16)
                .addGap(0, 39, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(rentHondaCity, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setForeground(new java.awt.Color(255, 255, 255));

        jLabel25.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setText("TOYOTA VIOS GR-S");
        jLabel25.setToolTipText("");

        jLabel26.setBackground(new java.awt.Color(0, 51, 102));
        jLabel26.setForeground(new java.awt.Color(0, 51, 102));
        jLabel26.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\cars sedan\\YARIS.jpg")); // NOI18N

        jLabel27.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 102));
        jLabel27.setText("2,599/day");
        jLabel27.setToolTipText("");

        rentToyotaVios.setBackground(new java.awt.Color(255, 255, 255));
        rentToyotaVios.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        rentToyotaVios.setForeground(new java.awt.Color(0, 0, 102));
        rentToyotaVios.setText("RENT");
        rentToyotaVios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentToyotaViosActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 102));
        jLabel28.setText("4-5 Seats");
        jLabel28.setToolTipText("");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel25)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rentToyotaVios)
                            .addComponent(jLabel28))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rentToyotaVios, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setForeground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 102));
        jLabel29.setText("MITSUBISHI MIRAGE");
        jLabel29.setToolTipText("");

        jLabel30.setBackground(new java.awt.Color(0, 51, 102));
        jLabel30.setForeground(new java.awt.Color(0, 51, 102));
        jLabel30.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\cars sedan\\MIRAGE.jpg")); // NOI18N

        jLabel31.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 102));
        jLabel31.setText("2,700/day");
        jLabel31.setToolTipText("");

        rentMirage.setBackground(new java.awt.Color(255, 255, 255));
        rentMirage.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        rentMirage.setForeground(new java.awt.Color(0, 0, 102));
        rentMirage.setText("RENT");
        rentMirage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentMirageActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 102));
        jLabel32.setText("4-5 Seats");
        jLabel32.setToolTipText("");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rentMirage)
                            .addComponent(jLabel32))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel29)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rentMirage, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel10))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 25, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("SEDAN", jPanel7);

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CAR SUV", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Swis721 BlkCn BT", 0, 24))); // NOI18N
        jPanel12.setToolTipText("CAR");

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("FORD EVEREST");
        jLabel20.setToolTipText("");

        jLabel33.setBackground(new java.awt.Color(0, 51, 102));
        jLabel33.setForeground(new java.awt.Color(0, 51, 102));
        jLabel33.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\cars sedan\\ford eve 1.jpg")); // NOI18N

        jLabel34.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 102));
        jLabel34.setText("5,700/day");
        jLabel34.setToolTipText("");

        rentFordEverest.setBackground(new java.awt.Color(255, 255, 255));
        rentFordEverest.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        rentFordEverest.setForeground(new java.awt.Color(0, 0, 102));
        rentFordEverest.setText("RENT");
        rentFordEverest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentFordEverestActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 102));
        jLabel35.setText("4-5 Seats");
        jLabel35.setToolTipText("");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(rentFordEverest)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(83, 83, 83))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(rentFordEverest))
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.setForeground(new java.awt.Color(255, 255, 255));

        jLabel36.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 102));
        jLabel36.setText("TOYOTA FORTUNER");
        jLabel36.setToolTipText("");

        jLabel37.setBackground(new java.awt.Color(0, 51, 102));
        jLabel37.setForeground(new java.awt.Color(0, 51, 102));
        jLabel37.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\cars sedan\\Toyota Fortuner 1.jpg")); // NOI18N

        jLabel38.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 102));
        jLabel38.setText("7,000/day");
        jLabel38.setToolTipText("");

        rentFortuner.setBackground(new java.awt.Color(255, 255, 255));
        rentFortuner.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        rentFortuner.setForeground(new java.awt.Color(0, 0, 102));
        rentFortuner.setText("RENT");
        rentFortuner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentFortunerActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 102));
        jLabel39.setText("4-5 Seats");
        jLabel39.setToolTipText("");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addGap(12, 12, 12)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rentFortuner)
                            .addComponent(jLabel39))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel36)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rentFortuner, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.setForeground(new java.awt.Color(255, 255, 255));

        jLabel40.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 102));
        jLabel40.setText("ISUZU MU - X");
        jLabel40.setToolTipText("");

        jLabel41.setBackground(new java.awt.Color(0, 51, 102));
        jLabel41.setForeground(new java.awt.Color(0, 51, 102));
        jLabel41.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\cars sedan\\Isuzu mu-X SUV 1.jpg")); // NOI18N

        jLabel42.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 102));
        jLabel42.setText("3,500/day");
        jLabel42.setToolTipText("");

        rentIsuzu.setBackground(new java.awt.Color(255, 255, 255));
        rentIsuzu.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        rentIsuzu.setForeground(new java.awt.Color(0, 0, 102));
        rentIsuzu.setText("RENT");
        rentIsuzu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentIsuzuActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 102));
        jLabel43.setText("4-5 Seats");
        jLabel43.setToolTipText("");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(rentIsuzu)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rentIsuzu)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.setForeground(new java.awt.Color(255, 255, 255));

        jLabel44.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 102));
        jLabel44.setText("MITSUBISHI MONTERO SPORT");
        jLabel44.setToolTipText("");

        jLabel45.setBackground(new java.awt.Color(0, 51, 102));
        jLabel45.setForeground(new java.awt.Color(0, 51, 102));
        jLabel45.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\cars sedan\\montero 1.jpg")); // NOI18N

        jLabel46.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 102));
        jLabel46.setText("6,000/day");
        jLabel46.setToolTipText("");

        rentMontero.setBackground(new java.awt.Color(255, 255, 255));
        rentMontero.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        rentMontero.setForeground(new java.awt.Color(0, 0, 102));
        rentMontero.setText("RENT");
        rentMontero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentMonteroActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 102));
        jLabel47.setText("4-5 Seats");
        jLabel47.setToolTipText("");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rentMontero)
                                    .addComponent(jLabel47))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(rentMontero, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel19))
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 27, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("SUV", jPanel12);

        jPanel29.setBackground(new java.awt.Color(204, 204, 204));
        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VAN ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Swis721 BlkCn BT", 0, 24))); // NOI18N
        jPanel29.setToolTipText("VAN \n");

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel30.setForeground(new java.awt.Color(255, 255, 255));

        jLabel110.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(0, 0, 102));
        jLabel110.setText("TOYOTA HAICE");
        jLabel110.setToolTipText("");

        jLabel111.setBackground(new java.awt.Color(0, 51, 102));
        jLabel111.setForeground(new java.awt.Color(0, 51, 102));
        jLabel111.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\vans\\HAICE 250.jpg")); // NOI18N

        jLabel113.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(0, 0, 102));
        jLabel113.setText("5000/Day");
        jLabel113.setToolTipText("");

        toyotahaicebtn.setBackground(new java.awt.Color(255, 255, 255));
        toyotahaicebtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        toyotahaicebtn.setForeground(new java.awt.Color(0, 0, 102));
        toyotahaicebtn.setText("RENT");
        toyotahaicebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toyotahaicebtnActionPerformed(evt);
            }
        });

        jLabel114.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(0, 0, 102));
        jLabel114.setText("15 Seats");
        jLabel114.setToolTipText("");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel114)
                            .addComponent(toyotahaicebtn))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel110)
                .addGap(96, 96, 96))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel111)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(toyotahaicebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel31.setForeground(new java.awt.Color(255, 255, 255));

        jLabel115.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(0, 0, 102));
        jLabel115.setText("NISSA URVAN NV350");
        jLabel115.setToolTipText("");

        jLabel116.setBackground(new java.awt.Color(0, 51, 102));
        jLabel116.setForeground(new java.awt.Color(0, 51, 102));
        jLabel116.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\vans\\NISSAN 250.jpg")); // NOI18N

        jLabel118.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(0, 0, 102));
        jLabel118.setText("4000/Day");
        jLabel118.setToolTipText("");

        nissanurvannv350btn.setBackground(new java.awt.Color(255, 255, 255));
        nissanurvannv350btn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        nissanurvannv350btn.setForeground(new java.awt.Color(0, 0, 102));
        nissanurvannv350btn.setText("RENT");
        nissanurvannv350btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nissanurvannv350btnActionPerformed(evt);
            }
        });

        jLabel119.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(0, 0, 102));
        jLabel119.setText("14 Seats");
        jLabel119.setToolTipText("");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel116)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel119)
                            .addComponent(nissanurvannv350btn))
                        .addContainerGap())))
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel115)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nissanurvannv350btn)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel32.setForeground(new java.awt.Color(255, 255, 255));

        jLabel120.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(0, 0, 102));
        jLabel120.setText("HYUNDAI STAREX");
        jLabel120.setToolTipText("");

        jLabel121.setBackground(new java.awt.Color(0, 51, 102));
        jLabel121.setForeground(new java.awt.Color(0, 51, 102));
        jLabel121.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\vans\\HYUNDAI 250.jpg")); // NOI18N

        jLabel123.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(0, 0, 102));
        jLabel123.setText("3,500/day");
        jLabel123.setToolTipText("");

        hyundaistarexbtn.setBackground(new java.awt.Color(255, 255, 255));
        hyundaistarexbtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        hyundaistarexbtn.setForeground(new java.awt.Color(0, 0, 102));
        hyundaistarexbtn.setText("RENT");
        hyundaistarexbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hyundaistarexbtnActionPerformed(evt);
            }
        });

        jLabel124.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(0, 0, 102));
        jLabel124.setText("12 seats");
        jLabel124.setToolTipText("");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel120)
                .addGap(81, 81, 81))
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hyundaistarexbtn)
                    .addComponent(jLabel124)
                    .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hyundaistarexbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel33.setForeground(new java.awt.Color(255, 255, 255));

        jLabel125.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(0, 0, 102));
        jLabel125.setText("SUZUKI APV");
        jLabel125.setToolTipText("");

        jLabel126.setBackground(new java.awt.Color(0, 51, 102));
        jLabel126.setForeground(new java.awt.Color(0, 51, 102));
        jLabel126.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\vans\\SUZUKI APV 230.jpg")); // NOI18N

        jLabel128.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(0, 0, 102));
        jLabel128.setText("3000/day");
        jLabel128.setToolTipText("");

        suzukiapvbtn.setBackground(new java.awt.Color(255, 255, 255));
        suzukiapvbtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        suzukiapvbtn.setForeground(new java.awt.Color(0, 0, 102));
        suzukiapvbtn.setText("RENT");
        suzukiapvbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suzukiapvbtnActionPerformed(evt);
            }
        });

        jLabel129.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(0, 0, 102));
        jLabel129.setText("4-5 Seats");
        jLabel129.setToolTipText("");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel33Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel125)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel128)
                    .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(suzukiapvbtn)
                        .addComponent(jLabel129)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(suzukiapvbtn)
                        .addGap(24, 24, 24))))
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(jLabel109))
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("VAN", jPanel29);

        jPanel39.setBackground(new java.awt.Color(204, 204, 204));
        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TRUCKS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Swis721 BlkCn BT", 0, 24))); // NOI18N
        jPanel39.setToolTipText("CAR");

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel40.setForeground(new java.awt.Color(255, 255, 255));

        jLabel152.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel152.setForeground(new java.awt.Color(0, 0, 102));
        jLabel152.setText("MITSUBISHI L300");
        jLabel152.setToolTipText("");

        jLabel153.setBackground(new java.awt.Color(0, 51, 102));
        jLabel153.setForeground(new java.awt.Color(0, 51, 102));
        jLabel153.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\trucks\\l300.png")); // NOI18N

        jLabel155.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel155.setForeground(new java.awt.Color(0, 0, 102));
        jLabel155.setText("4000/day");
        jLabel155.setToolTipText("");

        mitsubishil300btn.setBackground(new java.awt.Color(255, 255, 255));
        mitsubishil300btn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        mitsubishil300btn.setForeground(new java.awt.Color(0, 0, 102));
        mitsubishil300btn.setText("RENT");
        mitsubishil300btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitsubishil300btnActionPerformed(evt);
            }
        });

        jLabel156.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel156.setForeground(new java.awt.Color(0, 0, 102));
        jLabel156.setText("Deliveries");
        jLabel156.setToolTipText("");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel153, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel156)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel155, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(mitsubishil300btn)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel152)
                .addGap(83, 83, 83))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel152, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel156, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mitsubishil300btn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel153, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel41.setForeground(new java.awt.Color(255, 255, 255));

        jLabel157.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel157.setForeground(new java.awt.Color(0, 0, 102));
        jLabel157.setText("HINO 300 SERIES");
        jLabel157.setToolTipText("");

        jLabel158.setBackground(new java.awt.Color(0, 51, 102));
        jLabel158.setForeground(new java.awt.Color(0, 51, 102));
        jLabel158.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\trucks\\HINO 300 230.jpg")); // NOI18N

        jLabel160.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel160.setForeground(new java.awt.Color(0, 0, 102));
        jLabel160.setText("5500/day");
        jLabel160.setToolTipText("");

        hino300seriesbtn.setBackground(new java.awt.Color(255, 255, 255));
        hino300seriesbtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        hino300seriesbtn.setForeground(new java.awt.Color(0, 0, 102));
        hino300seriesbtn.setText("RENT");
        hino300seriesbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hino300seriesbtnActionPerformed(evt);
            }
        });

        jLabel161.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel161.setForeground(new java.awt.Color(0, 0, 102));
        jLabel161.setText("Loads");
        jLabel161.setToolTipText("");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel158)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hino300seriesbtn)
                            .addComponent(jLabel161)
                            .addComponent(jLabel160, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel157)))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel157, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel160, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel161, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hino300seriesbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel158, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel42.setForeground(new java.awt.Color(255, 255, 255));

        jLabel162.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel162.setForeground(new java.awt.Color(0, 0, 102));
        jLabel162.setText("ISUZU ELF DROPSIDE");
        jLabel162.setToolTipText("");

        jLabel163.setBackground(new java.awt.Color(0, 51, 102));
        jLabel163.setForeground(new java.awt.Color(0, 51, 102));
        jLabel163.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\trucks\\Isuzu elf 230.jpg")); // NOI18N

        jLabel165.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel165.setForeground(new java.awt.Color(0, 0, 102));
        jLabel165.setText("6000/day");
        jLabel165.setToolTipText("");

        isuzuelfdropsidebtn.setBackground(new java.awt.Color(255, 255, 255));
        isuzuelfdropsidebtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        isuzuelfdropsidebtn.setForeground(new java.awt.Color(0, 0, 102));
        isuzuelfdropsidebtn.setText("RENT");
        isuzuelfdropsidebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isuzuelfdropsidebtnActionPerformed(evt);
            }
        });

        jLabel166.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel166.setForeground(new java.awt.Color(0, 0, 102));
        jLabel166.setText("Loads");
        jLabel166.setToolTipText("");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel165, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel42Layout.createSequentialGroup()
                                .addComponent(jLabel166)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(isuzuelfdropsidebtn)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel162)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel162, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel165, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel166, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(isuzuelfdropsidebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel43.setForeground(new java.awt.Color(255, 255, 255));

        jLabel167.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel167.setForeground(new java.awt.Color(0, 0, 102));
        jLabel167.setText("DONGFENG CAPTAIN E");
        jLabel167.setToolTipText("");

        jLabel168.setBackground(new java.awt.Color(0, 51, 102));
        jLabel168.setForeground(new java.awt.Color(0, 51, 102));
        jLabel168.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\trucks\\DONGFENG 220.png")); // NOI18N

        jLabel170.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel170.setForeground(new java.awt.Color(0, 0, 102));
        jLabel170.setText("8500/day");
        jLabel170.setToolTipText("");

        dongfengcaptainebtn.setBackground(new java.awt.Color(255, 255, 255));
        dongfengcaptainebtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        dongfengcaptainebtn.setForeground(new java.awt.Color(0, 0, 102));
        dongfengcaptainebtn.setText("RENT");
        dongfengcaptainebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dongfengcaptainebtnActionPerformed(evt);
            }
        });

        jLabel171.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel171.setForeground(new java.awt.Color(0, 0, 102));
        jLabel171.setText("Good for loads");
        jLabel171.setToolTipText("");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel168, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel170, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dongfengcaptainebtn)
                            .addComponent(jLabel171))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel167)
                .addGap(29, 29, 29))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel167, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel168, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel170, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel171, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dongfengcaptainebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel151, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jLabel151))
                    .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 24, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("TRUCKS", jPanel39);

        jPanel34.setBackground(new java.awt.Color(204, 204, 204));
        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MOTORCYCLE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Swis721 BlkCn BT", 0, 24))); // NOI18N
        jPanel34.setToolTipText("CAR");

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel35.setForeground(new java.awt.Color(255, 255, 255));

        jLabel131.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(0, 0, 102));
        jLabel131.setText("HONDA CLICK");
        jLabel131.setToolTipText("");

        jLabel132.setBackground(new java.awt.Color(0, 51, 102));
        jLabel132.setForeground(new java.awt.Color(0, 51, 102));
        jLabel132.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\motor\\HONDA CLICK 230.jpg")); // NOI18N

        jLabel134.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(0, 0, 102));
        jLabel134.setText("800/day");
        jLabel134.setToolTipText("");

        hondaclick125btn.setBackground(new java.awt.Color(255, 255, 255));
        hondaclick125btn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        hondaclick125btn.setForeground(new java.awt.Color(0, 0, 102));
        hondaclick125btn.setText("RENT");
        hondaclick125btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hondaclick125btnActionPerformed(evt);
            }
        });

        jLabel135.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(0, 0, 102));
        jLabel135.setText("2 person");
        jLabel135.setToolTipText("");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hondaclick125btn)
                    .addComponent(jLabel135)
                    .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(128, Short.MAX_VALUE)
                .addComponent(jLabel131)
                .addGap(83, 83, 83))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hondaclick125btn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel36.setForeground(new java.awt.Color(255, 255, 255));

        jLabel136.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(0, 0, 102));
        jLabel136.setText("SUZUKI BURGMAN STREET");
        jLabel136.setToolTipText("");

        jLabel137.setBackground(new java.awt.Color(0, 51, 102));
        jLabel137.setForeground(new java.awt.Color(0, 51, 102));
        jLabel137.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\motor\\SUZUKI 230.jpg")); // NOI18N

        jLabel139.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(0, 0, 102));
        jLabel139.setText("1000/day");
        jLabel139.setToolTipText("");

        suzukiburgmanstreetbtn.setBackground(new java.awt.Color(255, 255, 255));
        suzukiburgmanstreetbtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        suzukiburgmanstreetbtn.setForeground(new java.awt.Color(0, 0, 102));
        suzukiburgmanstreetbtn.setText("RENT");
        suzukiburgmanstreetbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suzukiburgmanstreetbtnActionPerformed(evt);
            }
        });

        jLabel140.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(0, 0, 102));
        jLabel140.setText("1-3 person");
        jLabel140.setToolTipText("");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel137)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(suzukiburgmanstreetbtn)
                                    .addComponent(jLabel140))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel139, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel136)))
                .addGap(17, 17, 17))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(suzukiburgmanstreetbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel137, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel37.setForeground(new java.awt.Color(255, 255, 255));

        jLabel141.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(0, 0, 102));
        jLabel141.setText("VESPA PRIMAVERA");
        jLabel141.setToolTipText("");

        jLabel142.setBackground(new java.awt.Color(0, 51, 102));
        jLabel142.setForeground(new java.awt.Color(0, 51, 102));
        jLabel142.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\motor\\VESPA 230.jpg")); // NOI18N

        jLabel144.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(0, 0, 102));
        jLabel144.setText("1800/day");
        jLabel144.setToolTipText("");

        vespaprimaverabtn.setBackground(new java.awt.Color(255, 255, 255));
        vespaprimaverabtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        vespaprimaverabtn.setForeground(new java.awt.Color(0, 0, 102));
        vespaprimaverabtn.setText("RENT");
        vespaprimaverabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vespaprimaverabtnActionPerformed(evt);
            }
        });

        jLabel145.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(0, 0, 102));
        jLabel145.setText("1-2 person");
        jLabel145.setToolTipText("");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel144, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel145)
                            .addComponent(vespaprimaverabtn))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel141)
                .addGap(64, 64, 64))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel141, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel145, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vespaprimaverabtn)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel142)))
                .addContainerGap())
        );

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel38.setForeground(new java.awt.Color(255, 255, 255));

        jLabel146.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 23)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(0, 0, 102));
        jLabel146.setText("YAMAHA NMAX 155");
        jLabel146.setToolTipText("");

        jLabel147.setBackground(new java.awt.Color(0, 51, 102));
        jLabel147.setForeground(new java.awt.Color(0, 51, 102));
        jLabel147.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\motor\\NMAX 230.jpg")); // NOI18N

        jLabel149.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(0, 0, 102));
        jLabel149.setText("1300/day");
        jLabel149.setToolTipText("");

        yamahanmax155btn.setBackground(new java.awt.Color(255, 255, 255));
        yamahanmax155btn.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 24)); // NOI18N
        yamahanmax155btn.setForeground(new java.awt.Color(0, 0, 102));
        yamahanmax155btn.setText("RENT");
        yamahanmax155btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yamahanmax155btnActionPerformed(evt);
            }
        });

        jLabel150.setFont(new java.awt.Font("Swis721 Blk BT", 0, 18)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(0, 0, 102));
        jLabel150.setText("1-3 person");
        jLabel150.setToolTipText("");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(jLabel149, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(jLabel150)
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(yamahanmax155btn)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel146)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(yamahanmax155btn))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel147)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel130))
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("MOTORCYCLE", jPanel34);

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        homebtn.setBackground(new java.awt.Color(255, 255, 255));
        homebtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 24)); // NOI18N
        homebtn.setForeground(new java.awt.Color(0, 0, 102));
        homebtn.setText("MENU");
        homebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homebtnActionPerformed(evt);
            }
        });

        rentalformbtn.setBackground(new java.awt.Color(255, 255, 255));
        rentalformbtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 24)); // NOI18N
        rentalformbtn.setForeground(new java.awt.Color(0, 0, 102));
        rentalformbtn.setText("BACK");
        rentalformbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentalformbtnActionPerformed(evt);
            }
        });

        vehicleExitbtn.setBackground(new java.awt.Color(255, 255, 255));
        vehicleExitbtn.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 24)); // NOI18N
        vehicleExitbtn.setForeground(new java.awt.Color(0, 0, 102));
        vehicleExitbtn.setText("EXIT");
        vehicleExitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vehicleExitbtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Swis721 BlkCn BT", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Vehicles");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(homebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rentalformbtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vehicleExitbtn)
                .addGap(44, 44, 44))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(homebtn)
                        .addComponent(rentalformbtn)
                        .addComponent(vehicleExitbtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\kimberly urge\\Downloads\\OOP PICS PROJ\\logo header 2.png")); // NOI18N

        jPanel18.setBackground(new java.awt.Color(0, 0, 51));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151))
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1481, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rentBMW2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentBMW2ActionPerformed
          int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(1, clientID);
    }//GEN-LAST:event_rentBMW2ActionPerformed

    private void rentHondaCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentHondaCityActionPerformed
        int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(2, clientID);
    }//GEN-LAST:event_rentHondaCityActionPerformed

    private void rentToyotaViosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentToyotaViosActionPerformed
        int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(3, clientID); 
    }//GEN-LAST:event_rentToyotaViosActionPerformed

    private void rentMirageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentMirageActionPerformed
       int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(4, clientID);
    }//GEN-LAST:event_rentMirageActionPerformed

    private void rentFordEverestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentFordEverestActionPerformed
       int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(5, clientID);
    }//GEN-LAST:event_rentFordEverestActionPerformed

    private void rentFortunerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentFortunerActionPerformed
        int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(6, clientID);
    }//GEN-LAST:event_rentFortunerActionPerformed

    private void rentIsuzuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentIsuzuActionPerformed
       int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(7, clientID);
    }//GEN-LAST:event_rentIsuzuActionPerformed

    private void rentMonteroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentMonteroActionPerformed
        int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(8, clientID);
    }//GEN-LAST:event_rentMonteroActionPerformed

    private void toyotahaicebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toyotahaicebtnActionPerformed
       int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(9, clientID);
    }//GEN-LAST:event_toyotahaicebtnActionPerformed

    private void nissanurvannv350btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nissanurvannv350btnActionPerformed
        int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(10, clientID);
    }//GEN-LAST:event_nissanurvannv350btnActionPerformed

    private void hyundaistarexbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hyundaistarexbtnActionPerformed
        int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(11, clientID);
    }//GEN-LAST:event_hyundaistarexbtnActionPerformed

    private void suzukiapvbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suzukiapvbtnActionPerformed
        int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(12, clientID);
    }//GEN-LAST:event_suzukiapvbtnActionPerformed

    private void mitsubishil300btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitsubishil300btnActionPerformed
       int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(13, clientID);
    }//GEN-LAST:event_mitsubishil300btnActionPerformed

    private void hino300seriesbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hino300seriesbtnActionPerformed
        int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(14, clientID);
    }//GEN-LAST:event_hino300seriesbtnActionPerformed

    private void isuzuelfdropsidebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isuzuelfdropsidebtnActionPerformed
       int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(15, clientID);
    }//GEN-LAST:event_isuzuelfdropsidebtnActionPerformed

    private void dongfengcaptainebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dongfengcaptainebtnActionPerformed
      int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(16, clientID);
    }//GEN-LAST:event_dongfengcaptainebtnActionPerformed

    private void hondaclick125btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hondaclick125btnActionPerformed
       int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(17, clientID);
    }//GEN-LAST:event_hondaclick125btnActionPerformed

    private void suzukiburgmanstreetbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suzukiburgmanstreetbtnActionPerformed
       int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(18, clientID);
    }//GEN-LAST:event_suzukiburgmanstreetbtnActionPerformed

    private void vespaprimaverabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vespaprimaverabtnActionPerformed
        int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(19, clientID);
    }//GEN-LAST:event_vespaprimaverabtnActionPerformed

    private void yamahanmax155btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yamahanmax155btnActionPerformed
       int clientID = Clientsession.getInstance().getClientID();
        rentVehicle(20, clientID);
    }//GEN-LAST:event_yamahanmax155btnActionPerformed

    private void homebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homebtnActionPerformed
        usermenu next = new usermenu();
        next.setVisible(rootPaneCheckingEnabled);
        dispose();

    }//GEN-LAST:event_homebtnActionPerformed

    private void rentalformbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentalformbtnActionPerformed
        RentalForm next = new RentalForm();
        next.setVisible(rootPaneCheckingEnabled);
        dispose();
    }//GEN-LAST:event_rentalformbtnActionPerformed

    private void vehicleExitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vehicleExitbtnActionPerformed
        
    }//GEN-LAST:event_vehicleExitbtnActionPerformed

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
            java.util.logging.Logger.getLogger(VehiclesUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VehiclesUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VehiclesUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VehiclesUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int defaultClientID = 1; // Replace with actual logic to fetch a client ID if needed
            new VehiclesUi(defaultClientID).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dongfengcaptainebtn;
    private javax.swing.JButton hino300seriesbtn;
    private javax.swing.JButton homebtn;
    private javax.swing.JButton hondaclick125btn;
    private javax.swing.JButton hyundaistarexbtn;
    private javax.swing.JButton isuzuelfdropsidebtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JButton mitsubishil300btn;
    private javax.swing.JButton nissanurvannv350btn;
    private javax.swing.JButton rentBMW2;
    private javax.swing.JButton rentFordEverest;
    private javax.swing.JButton rentFortuner;
    private javax.swing.JButton rentHondaCity;
    private javax.swing.JButton rentIsuzu;
    private javax.swing.JButton rentMirage;
    private javax.swing.JButton rentMontero;
    private javax.swing.JButton rentToyotaVios;
    private javax.swing.JButton rentalformbtn;
    private javax.swing.JButton suzukiapvbtn;
    private javax.swing.JButton suzukiburgmanstreetbtn;
    private javax.swing.JButton toyotahaicebtn;
    private javax.swing.JButton vehicleExitbtn;
    private javax.swing.JButton vespaprimaverabtn;
    private javax.swing.JButton yamahanmax155btn;
    // End of variables declaration//GEN-END:variables
}
