
package BackendVRS;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class RentalForm {
    private String formId;
    private String clientId;
    private String vehicleId;
    private String rentalDate;
    private String returnDate;
    private double rentalCost;
    private String rentalStatus;

    // Constructor
    public RentalForm(String formId, String clientId, String vehicleId, String rentalDate, String returnDate, double rentalCost, String rentalStatus) {
        this.formId = formId;
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalCost = rentalCost;
        this.rentalStatus = rentalStatus;
    }

    // Method to create a rental form
    public void createRentalForm(String clientId, String vehicleId, String rentalDate, String returnDate) {
        this.formId = generateFormId();
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        int rentalDays = calculateRentalDays(rentalDate, returnDate);
        this.rentalCost = calculateRentalCost(vehicleId, rentalDays);
        this.rentalStatus = "Created";
        
        displayFormStep("create");
    }

    // Method to update a rental form's rental and return dates
    public void updateRentalForm(String formId, String newRentalDate, String newReturnDate) {
        if (this.formId.equals(formId) && !"Finalized".equals(this.rentalStatus)) {
            this.rentalDate = newRentalDate;
            this.returnDate = newReturnDate;
            int rentalDays = calculateRentalDays(newRentalDate, newReturnDate);
            this.rentalCost = calculateRentalCost(vehicleId, rentalDays);
            displayFormStep("update");
        } else {
            System.out.println("Cannot update rental form. Either form ID does not match or rental is already finalized.");
        }
    }

    // Method to cancel a rental form
    public void cancelRentalForm(String formId) {
        if (this.formId.equals(formId) && !"Finalized".equals(this.rentalStatus)) {
            this.rentalStatus = "Cancelled";
            displayFormStep("cancel");
        } else {
            System.out.println("Cannot cancel rental form. Either form ID does not match or rental is already finalized.");
        }
    }

    // Method to calculate rental cost based on rental days and vehicle rate
    public double calculateRentalCost(String vehicleId, int rentalDays) {
        double dailyRate = getDailyRate(vehicleId); // Assume a method to get rate based on vehicle ID
        return dailyRate * rentalDays;
    }

    // Switch-based display method for different form actions
    private void displayFormStep(String action) {
        switch (action.toLowerCase()) {
            case "create":
                System.out.println("Rental Form Created:");
                System.out.println("Form ID: " + formId);
                System.out.println("Client ID: " + clientId);
                System.out.println("Vehicle ID: " + vehicleId);
                System.out.println("Rental Date: " + rentalDate);
                System.out.println("Return Date: " + returnDate);
                System.out.println("Rental Cost: $" + rentalCost);
                System.out.println("Status: " + rentalStatus);
                break;
                
            case "update":
                System.out.println("Rental Form Updated:");
                System.out.println("Form ID: " + formId);
                System.out.println("New Rental Date: " + rentalDate);
                System.out.println("New Return Date: " + returnDate);
                System.out.println("Updated Rental Cost: $" + rentalCost);
                System.out.println("Status: " + rentalStatus);
                break;
                
            case "cancel":
                System.out.println("Rental Form Cancelled:");
                System.out.println("Form ID: " + formId);
                System.out.println("Status: " + rentalStatus);
                break;
                
            case "finalize":
                System.out.println("Rental Form Finalized:");
                System.out.println("Form ID: " + formId);
                System.out.println("Total Cost: $" + rentalCost);
                System.out.println("Status: " + rentalStatus);
                break;
                
            default:
                System.out.println("Unknown action.");
                break;
        }
    }
    
    public void finalizeRentalForm(String formId) {
    if (this.formId.equals(formId) && "Created".equals(this.rentalStatus)) {
        this.rentalStatus = "Finalized";
        
        // Prompt user for payment method
        System.out.println("Select payment method: 1. Credit Card, 2. Cash, 3. Check");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        
        Payment payment;
        switch (choice) {
            case 1:
                payment = new PayCredit(this.formId, this.rentalCost);
                break;
            case 2:
                payment = new PayCash(this.formId, this.rentalCost);
                break;
            case 3:
                payment = new PayCheck(this.formId, this.rentalCost);
                break;
            default:
                System.out.println("Invalid payment method selected.");
                return;
        }
        
        payment.makePayment();
        displayFormStep("finalize");
    } else {
        System.out.println("Cannot finalize rental form. Either form ID does not match or rental is already cancelled/finalized.");
    }
}

    // Helper method to generate a unique form ID
    private String generateFormId() {
        return "RF-" + System.currentTimeMillis();
    }

    // Helper method to calculate rental days from dates
    private int calculateRentalDays(String rentalDate, String returnDate) {
        LocalDate start = LocalDate.parse(rentalDate);
        LocalDate end = LocalDate.parse(returnDate);
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    // Placeholder method to get the daily rate for a vehicle
    private double getDailyRate(String vehicleId) {
        return 100.0; // Example daily rate, replace with actual logic
    }

    String getFormId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    // Inner Services class
    public class Services {
        private String driverName;
        private String assistantName;
        private String carseatType;

        public Services(String driverName, String assistantName, String carseatType) {
            this.driverName = driverName;
            this.assistantName = assistantName;
            this.carseatType = carseatType;
        }

        // Methods to add services
        public void addDriver(String driverName) {
            this.driverName = driverName;
            System.out.println("Driver added: " + driverName);
        }

        public void addCarseat(String carseatType) {
            this.carseatType = carseatType;
            System.out.println("Carseat added: " + carseatType);
        }

        public void addRoadAssistant(String assistantName) {
            this.assistantName = assistantName;
            System.out.println("Road Assistant added: " + assistantName);
        }

        // Getter methods for services
        public String getDriverName() {
            return driverName;
        }

        public String getAssistantName() {
            return assistantName;
        }

        public String getCarseatType() {
            return carseatType;
        }
    }
}
