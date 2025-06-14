
package BackendVRS;

import java.util.Scanner;

public class ClientAdmin {
       private static final ClientInput clientInfo = new ClientInput(); // Simulate ClientInfo
        private static Scanner scanner = new Scanner(System.in);

        public static void displayClientInfo() {
            int choice;
            do {
                System.out.println("\nClient Information:");
                System.out.println("1. Show Client List");
                System.out.println("2. Add New Client");
                System.out.println("3. Update Client Info");
                System.out.println("4. Delete Client");
                System.out.println("5. Back to Main Menu");

                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        clientInfo.showClientList();
                        break;
                    case 2:
                        addNewClient();
                        break;
                    case 3:
                        updateClientInfo();
                        break;
                    case 4:
                        deleteClient();
                        break;
                    case 5:
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } while (choice != 5);
        }

        private static void addNewClient() {
            System.out.print("Enter Client ID: ");
            String clientID = scanner.nextLine();
            System.out.print("Enter Client Name: ");
            String clientName = scanner.nextLine();
            System.out.print("Enter Client Address: ");
            String clientAddress = scanner.nextLine();
            System.out.print("Enter Client Contact Number: ");
            String clientContact = scanner.nextLine();
            System.out.print("Enter Client Email: ");
            String clientEmail = scanner.nextLine();
            System.out.print("Enter Renting Status: ");
            String rentingStatus = scanner.nextLine();

            clientInfo.addClient(clientID, clientName, clientAddress, clientContact, clientEmail);
        }

        private static void updateClientInfo() {
            System.out.print("Enter Client ID to update: ");
            String clientID = scanner.nextLine();
            System.out.print("Enter New Client Name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter New Client Address: ");
            String newAddress = scanner.nextLine();
            System.out.print("Enter New Client Contact Number: ");
            String newContact = scanner.nextLine();
            System.out.print("Enter New Client Email: ");
            String newEmail = scanner.nextLine();
            System.out.print("Enter New Renting Status: ");
            String newRentingStatus = scanner.nextLine();

            clientInfo.updateClient(clientID, newName, newAddress, newContact, newEmail);
        }

        private static void deleteClient() {
            System.out.print("Enter Client ID to delete: ");
            String clientID = scanner.nextLine();
            clientInfo.deleteClient(clientID);
        }
}
