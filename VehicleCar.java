
package BackendVRS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehicleCar {

    public VehicleCar(String vehicleID, String registrationNumber, String make, String model, int carCapacity, String available) {
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name"; // Replace with your DB URL
    private static final String DB_USER = "your_username"; // Replace with your DB username
    private static final String DB_PASSWORD = "your_password"; // Replace with your DB password

   
    // Method to retrieve all vehicles from the 'vehiclecar' table
    public List<VehicleCar> getAllVehicleCars() {
        List<VehicleCar> vehicleCars = new ArrayList<>();
        String query = "SELECT * FROM vehiclecar";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Extract data from the result set
                String vehicleID = resultSet.getString("vehicleID");
                String registrationNumber = resultSet.getString("registrationNumber");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                double rentalRate = resultSet.getDouble("rentalRate");
                String available = resultSet.getString("available");
                String carModel = resultSet.getString("carModel");
                String carDescription = resultSet.getString("carDescription");
                String plateNumber = resultSet.getString("plateNumber");
                int carCapacity = resultSet.getInt("carCapacity");
                double carPrice = resultSet.getDouble("carPrice");

                // Create a VehicleCar object and add it to the list
               VehicleCar vehicleCar = new VehicleCar(vehicleID, registrationNumber, make, model, carCapacity, 
                                       available);


                vehicleCars.add(vehicleCar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vehicleCars;
    }

    private void setCarModel(String carModel) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}