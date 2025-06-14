
package BackendVRS;

public class Vehicles {
    private String vehicleType;
    private String vehicleStatus;
    int vehicleId;
    private String vehicleName;
    private double vehiclePrice;

    // Constructor
    public Vehicles(int vehicleId1, String vehicleID, double rentalRate) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehiclePrice = vehiclePrice;
        this.vehicleType = vehicleType;
        this.vehicleStatus = vehicleStatus;
    }

    Vehicles(String v123, String abC123, String toyota, String corolla, double d, String car, String available) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public int getVehicleId() {
        return vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public double getVehiclePrice() {
        return vehiclePrice;
    }

   

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

   
    void displayUserVehicleChoices() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String getVehicleID() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Object getRegistrationNumber() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
