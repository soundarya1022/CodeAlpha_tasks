import java.util.*;
import java.io.*;

class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Booking {
    String customerName;
    int roomNumber;
    String category;
    double amountPaid;

    Booking(String customerName, int roomNumber, String category, double amountPaid) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amountPaid = amountPaid;
    }

    @Override
    public String toString() {
        return customerName + "," + roomNumber + "," + category + "," + amountPaid;
    }
}

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {

        initializeRooms();
        loadBookings();

        while (true) {

            System.out.println("\n=================================");
            System.out.println("     HOTEL RESERVATION SYSTEM");
            System.out.println("=================================");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    viewAvailableRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelReservation();
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:
                    saveBookings();
                    System.out.println("Thank you for using the system!");
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    static void initializeRooms() {

        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));

        rooms.add(new Room(201, "Deluxe", 3000));
        rooms.add(new Room(202, "Deluxe", 3000));

        rooms.add(new Room(301, "Suite", 5000));
        rooms.add(new Room(302, "Suite", 5000));
    }

    static void viewAvailableRooms() {

        System.out.println("\n----- AVAILABLE ROOMS -----");

        boolean found = false;

        for (Room room : rooms) {

            if (room.available) {

                System.out.println(
                        "Room: " + room.roomNumber +
                                " | Category: " + room.category +
                                " | Price: ₹" + room.price);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    static void bookRoom() {

        viewAvailableRooms();

        System.out.print("\nEnter Customer Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();

        for (Room room : rooms) {

            if (room.roomNumber == roomNo && room.available) {

                System.out.println("\nPayment Amount: ₹" + room.price);
                System.out.print("Confirm Payment? (yes/no): ");
                String payment = sc.next();

                if (payment.equalsIgnoreCase("yes")) {

                    room.available = false;

                    Booking booking =
                            new Booking(name,
                                    room.roomNumber,
                                    room.category,
                                    room.price);

                    bookings.add(booking);

                    saveBookings();

                    System.out.println("\nBooking Successful!");
                    System.out.println("Booking ID: " + room.roomNumber);
                    return;
                } else {
                    System.out.println("Payment Cancelled.");
                    return;
                }
            }
        }

        System.out.println("Room not available.");
    }

    static void cancelReservation() {

        System.out.print("Enter Room Number to Cancel: ");
        int roomNo = sc.nextInt();

        Iterator<Booking> iterator = bookings.iterator();

        while (iterator.hasNext()) {

            Booking booking = iterator.next();

            if (booking.roomNumber == roomNo) {

                iterator.remove();

                for (Room room : rooms) {
                    if (room.roomNumber == roomNo) {
                        room.available = true;
                    }
                }

                saveBookings();

                System.out.println("Reservation Cancelled Successfully!");
                return;
            }
        }

        System.out.println("Booking Not Found.");
    }

    static void viewBookings() {

        System.out.println("\n----- BOOKING DETAILS -----");

        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Booking booking : bookings) {

            System.out.println(
                    "Customer: " + booking.customerName +
                            " | Room: " + booking.roomNumber +
                            " | Category: " + booking.category +
                            " | Paid: ₹" + booking.amountPaid);
        }
    }

    static void saveBookings() {

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Booking booking : bookings) {
                writer.println(booking);
            }

        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }

    static void loadBookings() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                Booking booking = new Booking(
                        data[0],
                        Integer.parseInt(data[1]),
                        data[2],
                        Double.parseDouble(data[3])
                );

                bookings.add(booking);

                for (Room room : rooms) {
                    if (room.roomNumber == booking.roomNumber) {
                        room.available = false;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading bookings.");
        }
    }
}