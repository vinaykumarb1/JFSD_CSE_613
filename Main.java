import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ParkingOperations Interface
interface ParkingOperations {
    boolean parkVehicle(Vehicle vehicle);
    boolean removeVehicle(String licensePlate);
    void viewParkedVehicles();
    boolean checkAvailability();
}

// ParkingSpot Abstract Class
abstract class ParkingSpot {
    protected String spotID;
    protected boolean isOccupied;
    protected Vehicle vehicleDetails;

    public ParkingSpot(String spotID) {
        this.spotID = spotID;
        this.isOccupied = false;
        this.vehicleDetails = null;
    }

    public String getSpotID() {
        return spotID;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getVehicleDetails() {
        return vehicleDetails;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.vehicleDetails = vehicle;
        this.isOccupied = true;
    }

    public void removeVehicle() {
        this.vehicleDetails = null;
        this.isOccupied = false;
    }
}

// Vehicle Class
class Vehicle {
    private String licensePlate;

    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}

// ParkingSpotImpl Class (Concrete Implementation of ParkingSpot)
class ParkingSpotImpl extends ParkingSpot {
    public ParkingSpotImpl(String spotID) {
        super(spotID);
    }
}

// ParkingLot Class
class ParkingLot implements ParkingOperations {
    private List<ParkingSpot> spots;
    private int capacity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.spots = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            spots.add(new ParkingSpotImpl("Spot" + (i + 1)));
        }
    }

    @Override
    public boolean parkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : spots) {
            if (!spot.isOccupied()) {
                spot.parkVehicle(vehicle);
                System.out.println("Vehicle with license plate " + vehicle.getLicensePlate() + " parked at " + spot.getSpotID());
                return true;
            }
        }
        System.out.println("Parking Lot is full!");
        return false;
    }

    @Override
    public boolean removeVehicle(String licensePlate) {
        for (ParkingSpot spot : spots) {
            if (spot.isOccupied() && spot.getVehicleDetails().getLicensePlate().equals(licensePlate)) {
                spot.removeVehicle();
                System.out.println("Vehicle with license plate " + licensePlate + " removed from " + spot.getSpotID());
                return true;
            }
        }
        System.out.println("Vehicle with license plate " + licensePlate + " not found in parking lot!");
        return false;
    }

    @Override
    public void viewParkedVehicles() {
        System.out.println("Parked Vehicles:");
        for (ParkingSpot spot : spots) {
            if (spot.isOccupied()) {
                System.out.println("Spot ID: " + spot.getSpotID() + ", Vehicle License Plate: " + spot.getVehicleDetails().getLicensePlate());
            }
        }
    }

    @Override
    public boolean checkAvailability() {
        for (ParkingSpot spot : spots) {
            if (!spot.isOccupied()) {
                return true;
            }
        }
        return false;
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(5); // Initialize with capacity of 5
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nParking Lot Management System");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. View Parked Vehicles");
            System.out.println("4. Check Availability");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter vehicle license plate: ");
                    String licensePlate = scanner.nextLine();
                    Vehicle vehicle = new Vehicle(licensePlate);
                    parkingLot.parkVehicle(vehicle);
                    break;
                case 2:
                    System.out.print("Enter vehicle license plate to remove: ");
                    String removeLicensePlate = scanner.nextLine();
                    parkingLot.removeVehicle(removeLicensePlate);
                    break;
                case 3:
                    parkingLot.viewParkedVehicles();
                    break;
                case 4:
                    if (parkingLot.checkAvailability()) {
                        System.out.println("Parking spots are available.");
                    } else {
                        System.out.println("Parking lot is full.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}