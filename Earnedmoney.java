
package BackendVRS;

import java.util.Scanner;

public class Earnedmoney {
     private static Earnedmoney earnings = new Earnedmoney(); // Simulate Earnings
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMenu() {
        int choice;
        do {
            System.out.println("\nEarnings:");
            System.out.println("1. Show Total Earnings");
            System.out.println("2. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    earnings.showTotalEarnings();
                    break;
                case 2:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 2);
    }

    private void showTotalEarnings() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
