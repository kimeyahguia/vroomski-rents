
package BackendVRS;
public class VehicleServices {
    private String driverName;
    private String assistantName;
    private String carSeatType;

    public void addDriver(String driverName) {
        this.driverName = driverName;
        System.out.println("Driver " + driverName + " added to the rental service.");
    }

    public void addCarSeat(String carSeatType) {
        this.carSeatType = carSeatType;
        System.out.println("Car seat type " + carSeatType + " added to the rental service.");
    }

    public void addRoadAssistant(String assistantName) {
        this.assistantName = assistantName;
        System.out.println("Road assistant " + assistantName + " added to the rental service.");
    }

    public void displayServiceInfo() {
        System.out.println("Driver Name: " + (driverName != null ? driverName : "None"));
        System.out.println("Assistant Name: " + (assistantName != null ? assistantName : "None"));
        System.out.println("Car Seat Type: " + (carSeatType != null ? carSeatType : "None"));
    }
}