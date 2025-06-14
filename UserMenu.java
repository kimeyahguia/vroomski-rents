
package BackendVRS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserMenu {
    private Scanner scanner = new Scanner(System.in);
    private List<Vehicles> vehicleList = new ArrayList<>();
    private Iterable<rentingStatus> rentingStatusList;

    public UserMenu() {
    }

    public void displayUserMenu() {
        int userChoice = 0;
        ClientInput clientInput = new ClientInput(); // Create an instance of Client_Input

        while (userChoice != 5) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Input Client Information");
            System.out.println("2. Create Rental Form");
            System.out.println("3. View Rental Status");
            System.out.println("4. Manage Vehicle Rentals");
            System.out.println("5. Exit");

            System.out.print("Please choose an option (1-5): ");
            userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (userChoice) {
                case 1:
                    inputClientDetails(clientInput);
                    createRentalForm(clientInput);
                    break;
                case 2:
                     viewRentalStatus();
                    break;
                case 3:
                    clientInput.manageVehicleRentalActions();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to input client information
    private void inputClientDetails(ClientInput clientInput) {
        System.out.println("\nInputting Client Information...");
        clientInput.inputClientDetails();
        System.out.println("\nClient Information:");
        clientInput.showClientList();
    }

    // Method to create a rental form
    private void createRentalForm(ClientInput clientInput) {
        // Select a vehicle
        String vehicleId = selectVehicle();
        if (vehicleId == null) {
            System.out.println("No available vehicles selected. Returning to menu.");
            return;
        }
 
        // Proceed to create rental form if a vehicle was selected
        System.out.print("Enter rental start date (YYYY-MM-DD): ");
        String rentalDate = scanner.nextLine();

        System.out.print("Enter return date (YYYY-MM-DD): ");
        String returnDate = scanner.nextLine();

        RentalForm rentalForm = new RentalForm("RF-001", clientInput.getClientID(), vehicleId, rentalDate, returnDate, 0.0, "Pending");

        rentalForm.createRentalForm(clientInput.getClientID(), vehicleId, rentalDate, returnDate);

        // Finalize the rental form and process payment
        System.out.println("Finalizing Rental Form and Processing Payment...");
        rentalForm.finalizeRentalForm(rentalForm.getFormId());
    }

    // Method to select a vehicle from the available list
    private String selectVehicle() {
        System.out.println("\nAvailable Vehicles:");

        List<Vehicles> availableVehicles = new ArrayList<>();
        int optionNumber = 1;
        for (Vehicles vehicle : vehicleList) {
            if ("Available".equalsIgnoreCase(vehicle.getVehicleStatus())) {
                System.out.println(optionNumber + ". " + vehicle);
                availableVehicles.add(vehicle);
                optionNumber++;
            }
        }

        if (availableVehicles.isEmpty()) {
            System.out.println("No vehicles are currently available.");
            return null;
        }

        System.out.print("\nEnter the number corresponding to the Vehicle you want to rent: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (choice > 0 && choice <= availableVehicles.size()) {
            Vehicles selectedVehicle = availableVehicles.get(choice - 1);
            selectedVehicle.setVehicleStatus("Rented"); // Mark as rented
            return selectedVehicle.getVehicleID();
        } else {
            System.out.println("Invalid selection.");
            return null;
        }
    }
    
    
    private void viewRentalStatus() {
        System.out.println("\nViewing Rental Status...");
        System.out.print("Enter rental form ID to view status: ");
        String formId = scanner.nextLine();

        rentingStatus foundStatus = null;
        for (rentingStatus rentalStatus : rentingStatusList) {
            if (rentalStatus.getRentalFormId().equals(formId)) {
                foundStatus = rentalStatus;
                break;
            }
        }

        if (foundStatus != null) {
            foundStatus.displayRentalDetails();

            System.out.println("\nOptions:");
            System.out.println("1. View Receipt");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    foundStatus.displayReceipt();
                    break;
                case 2:
                    foundStatus.cancelBooking();
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Rental form not found.");
        }
    }

}
