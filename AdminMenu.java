package BackendVRS;

import java.util.Scanner;

public class AdminMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMenu() {
        int adminChoice;

        // Admin menu loop
        do {
            System.out.println("\nAdmin Menu - Choose Category:");
            System.out.println("1. Vehicle Inventory");
            System.out.println("2. Client Information");
            System.out.println("3. Earnings");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            adminChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (adminChoice) {
                case 1:
                    VehicleInventory.displayMenu();
                    break;
                case 2:
                    ClientAdmin.displayClientInfo();
                    break;
                case 3:
                    Earnedmoney.displayMenu();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (adminChoice != 4);
    }
}
