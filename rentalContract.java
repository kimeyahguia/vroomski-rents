
package BackendVRS;
import java.util.Scanner;

public class rentalContract {
    private String name;
    private String rentalOwner;
    private Vehicles vehicle;
    private String vehicleReservationReceipt;
    private double charges;
    private String agreement;
    private boolean isVehicleDamaged;
    private String userSignature;

    public rentalContract(String name, String rentalOwner, Vehicles vehicle, String vehicleReservationReceipt, double charges, String agreement, boolean isVehicleDamaged) {
        this.name = name;
        this.rentalOwner = rentalOwner;
        this.vehicle = vehicle;
        this.vehicleReservationReceipt = vehicleReservationReceipt;
        this.charges = charges;
        this.agreement = agreement;
        this.isVehicleDamaged = isVehicleDamaged;
        this.userSignature = ""; // Initialize as unsigned
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getRentalOwner() {
        return rentalOwner;
    }

    public Vehicles getVehicle() {
        return vehicle;
    }

    public String getVehicleReservationReceipt() {
        return vehicleReservationReceipt;
    }

    public double getCharges() {
        return charges;
    }

    public String getAgreement() {
        return agreement;
    }

    public boolean isVehicleDamaged() {
        return isVehicleDamaged;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public void setVehicleDamaged(boolean isVehicleDamaged) {
        this.isVehicleDamaged = isVehicleDamaged;
    }

    public void displayContractInfo() {
        System.out.println("Contract Name: " + name);
        System.out.println("Rental Owner: " + rentalOwner);
        System.out.println("Vehicle Information: ");
        vehicle.displayUserVehicleChoices();
        System.out.println("Reservation Receipt: " + vehicleReservationReceipt);
        System.out.println("Charges: $" + charges);
        System.out.println("Agreement: \n" + agreement);
        if (!userSignature.isEmpty()) {
            System.out.println("User Signature: " + userSignature);
        } else {
            System.out.println("User Signature: Not Signed");
        }
        if (isVehicleDamaged) {
            System.out.println("Note: The vehicle has been reported damaged. Additional charges may apply based on the severity of the damage.");
        }
    }

    public void signAgreement() {
        if (!userSignature.isEmpty()) {
            System.out.println("Agreement has already been signed by: " + userSignature);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please sign the agreement by entering your full name:");
        String signature = scanner.nextLine();

        if (signature.isEmpty()) {
            System.out.println("Signature cannot be empty. Please try again.");
        } else {
            this.userSignature = signature;
            this.agreement += "\n\nUser Signature: " + signature + "\nDate: " + java.time.LocalDate.now();
            System.out.println("Thank you! The agreement has been signed.");
        }
    }

    public void addTermsAndConditions() {
        this.agreement = 
            "----------------------------------------\n" +
            "              VEHICLE RENTAL AGREEMENT\n" +
            "----------------------------------------\n" +
            "Terms and Conditions:\n\n" +
            "1. Rental Period: The rental period begins on the date of reservation confirmation and ends on the return date specified by the renter.\n\n" +
            "2. Payment: The renter agrees to pay all rental charges before the vehicle is released for use. A refundable deposit may also be required.\n\n" +
            "3. Insurance: The renter is responsible for securing insurance coverage during the rental period. If the renter does not have their own insurance, the rental company may offer optional insurance at an additional cost.\n\n" +
            "4. Vehicle Condition: The vehicle is provided in good working condition and has been inspected before rental. The renter agrees to return the vehicle in the same condition, barring normal wear and tear.\n\n" +
            "5. Vehicle Damage: The renter is responsible for any damages incurred during the rental period. This includes, but is not limited to, accidents, scratches, tire damage, and interior damage. The renter agrees to pay all repair costs, which may include towing fees and lost rental income if applicable.\n\n" +
            "6. Fuel Policy: The vehicle should be returned with the same amount of fuel as it had at the time of rental. If the vehicle is returned with less fuel, a refueling charge will apply.\n\n" +
            "7. Traffic Violations: The renter is responsible for any traffic tickets, fines, or legal violations incurred during the rental period.\n\n" +
            "8. Damaged Vehicle: In the event that the vehicle is damaged, the renter agrees to pay additional charges for repair costs. The charges will be determined based on the severity of the damage and the cost of repair.\n\n" +
            "9. Indemnity: The renter agrees to indemnify the rental company against any claims, damages, or losses that arise due to the use of the vehicle during the rental period.\n\n" +
            "10. Termination: The rental company reserves the right to terminate the rental agreement if the renter violates any terms of this contract, including but not limited to illegal use of the vehicle or failing to return the vehicle on time.\n\n" +
            "----------------------------------------\n";
    }
}
