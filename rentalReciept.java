
package BackendVRS;
 import java.time.LocalDate;
public class rentalReciept {

public class Receipt {
    private String clientName;
    private String reservationDate;
    private String rentalStatus;
    private String rentalStartDate;
    private String rentalReturnDate;
    private String pickupLocation;
    private String returnLocation;
    private double rentalCost;

    
    
    public Receipt(String clientName, String rentalStatus, String rentalStartDate, 
                   String rentalReturnDate, String pickupLocation, 
                   String returnLocation, double rentalCost) {
        this.clientName = clientName;
        this.reservationDate = LocalDate.now().toString(); // Reservation date is current date
        this.rentalStatus = rentalStatus;
        this.rentalStartDate = rentalStartDate;
        this.rentalReturnDate = rentalReturnDate;
        this.pickupLocation = pickupLocation;
        this.returnLocation = returnLocation;
        this.rentalCost = rentalCost;
    }

    // generate the receipt
    public void generateReceipt() {
        System.out.println("========== RENTAL RECEIPT ==========");
        System.out.println("Client Name: " + clientName);
        System.out.println("Reservation Date: " + reservationDate); // Current date as reservation date
        System.out.println("Rental Status: " + rentalStatus);
        System.out.println("-------------------------------------");
        System.out.println("Rental Start Date: " + rentalStartDate);
        System.out.println("Rental Return Date: " + rentalReturnDate);
        System.out.println("Pickup Location: " + pickupLocation);
        System.out.println("Return Location: " + returnLocation);
        System.out.println("-------------------------------------");
        System.out.println("Rental Cost: $" + rentalCost);
        System.out.println("=====================================");
    }
}

}
