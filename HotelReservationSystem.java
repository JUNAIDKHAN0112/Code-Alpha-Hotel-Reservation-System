import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// Room class representing a hotel room
class Room {
    private int roomNumber;
    private String type; // e.g., "Single", "Double", "Suite"
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - $" + price + "/night";
    }
}

// Reservation class representing a reservation
class Reservation {
    private Room room;
    private String guestName;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Room room, String guestName, Date checkInDate, Date checkOutDate) {
        this.room = room;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        room.setAvailable(false);
    }

    public Room getRoom() {
        return room;
    }

    public String getGuestName() {
        return guestName;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation for " + guestName + " - Room " + room.getRoomNumber() +
               " from " + checkInDate + " to " + checkOutDate;
    }
}

// Payment class representing payment processing
class Payment {
    public boolean processPayment(double amount) {
        // Simulate payment processing
        System.out.println("Processing payment of $" + amount);
        // Always returns true for simulation purposes
        return true;
    }
}

// Hotel class representing the hotel management system
class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Room> searchAvailableRooms(String type) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getType().equalsIgnoreCase(type)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public boolean makeReservation(Room room, String guestName, Date checkInDate, Date checkOutDate) {
        Payment payment = new Payment();
        if (payment.processPayment(room.getPrice())) {
            reservations.add(new Reservation(room, guestName, checkInDate, checkOutDate));
            System.out.println("Reservation successful for " + guestName);
            return true;
        } else {
            System.out.println("Payment failed. Reservation unsuccessful.");
            return false;
        }
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }
}

// Main class to interact with the Hotel Reservation System
public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        // Sample rooms
        hotel.addRoom(new Room(101, "Single", 100.0));
        hotel.addRoom(new Room(102, "Double", 150.0));
        hotel.addRoom(new Room(103, "Suite", 300.0));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Reservations");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room type (Single, Double, Suite): ");
                    String roomType = scanner.nextLine();
                    List<Room> availableRooms = hotel.searchAvailableRooms(roomType);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No available rooms for the specified type.");
                    } else {
                        System.out.println("Available rooms:");
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.nextLine();

                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();

                    // Dates are placeholders for simplicity
                    Date checkInDate = new Date();
                    Date checkOutDate = new Date(checkInDate.getTime() + 86400000L); // +1 day

                    Room roomToBook = null;
                    for (Room room : hotel.getRooms()) {
                        if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                            roomToBook = room;
                            break;
                        }
                    }

                    if (roomToBook == null) {
                        System.out.println("Room is either not available or does not exist.");
                    } else {
                        hotel.makeReservation(roomToBook, guestName, checkInDate, checkOutDate);
                    }
                    break;

                case 3:
                    hotel.viewReservations();
                    break;

                case 4:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}