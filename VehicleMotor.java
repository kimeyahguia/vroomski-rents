
package BackendVRS;

public class VehicleMotor  {
    private double engineDisplacement; // in cc

    // Constructor
    public VehicleMotor(String vehicleID, String registrationNumber, String make, String model, double rentalRate, boolean vehicleStatus) {
        
        this.engineDisplacement = engineDisplacement;
    }

    // Getter and setter
    public double getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(double engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    @Override
    public String toString() {
        return super.toString() + ", Engine Displacement=" + engineDisplacement + " cc]";
    }
}

