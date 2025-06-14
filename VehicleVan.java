
package BackendVRS;

public class VehicleVan {
    private int cargoCapacity; // in cubic feet
    private int numOfDoors;

    // Constructor
    public VehicleVan(String vehicleID, String registrationNumber, String make, double rentalRate, String model, int cargoCapacity) {
        
        this.cargoCapacity = cargoCapacity;
        this.numOfDoors = numOfDoors;
    }

    // Getters and setters
    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public int getNumOfDoors() {
        return numOfDoors;
    }

    public void setNumOfDoors(int numOfDoors) {
        this.numOfDoors = numOfDoors;
    }

    @Override
    public String toString() {
        return super.toString() + ", Cargo Capacity=" + cargoCapacity + " cubic feet, Number of Doors=" + numOfDoors + "]";
    }
}

