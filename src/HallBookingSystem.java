import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;
public class HallBookingSystem {
    // Constants for showtime
    private static final String MORNING = "Morning";
    private static final String AFTERNOON = "Afternoon";
    private static final String NIGHT = "Night";

    // Constants for seat status
    private static final String AVAILABLE = "AV";
    private static final String BOOKED = "BO";

    // 2D array to represent halls and their seats
    private static final String[][] halls = {
            {"Table A", AVAILABLE, AVAILABLE, AVAILABLE},
            {"Table B", AVAILABLE, AVAILABLE, AVAILABLE},
            {"Table C", AVAILABLE, AVAILABLE, AVAILABLE},
    };
    // Booking history
    private static final String[][] bookingHistory = new String[100][100];
    private static int bookingCount = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--------Welcome to Hall Booking System--------");
            System.out.println("#A. Hall Booking");
            System.out.println("#B. Hall Checking");
            System.out.println("#C. Showtime Checking");
            System.out.println("#D. Booking History");
            System.out.println("#E. Rebooting Hall");
            System.out.println("#F. Exit");
            System.out.print("Select an option (A-F): ");

            String choose = scanner.next();
            scanner.nextLine(); // Consume newline
            switch (choose) {
                case "A","a" -> bookHall(scanner);
                case "B","b" -> displayHallStatus();
                case "C","c" -> displayShowtimeSchedule();
                case "D","d" -> displayBookingHistory();
                case "E","e" -> rebootHall();
                case "F","f" -> {
                    System.out.println("Exiting Hall Booking System.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please select again.");
            }

        }
    }

    /*method booking hall*/
    private static void bookHall(Scanner scanner) {
        System.out.println("-".repeat(50));
        System.out.println("\nHall Booking:");
        System.out.println("Available Showtime: (M). Morning (A). Afternoon (N). Night");
        System.out.println("Enter showtime: ");
        String showtime = scanner.next();
        scanner.nextLine(); // Consume newline
        String showtimeStr = getShowtimeString(showtime);
        System.out.println("\nAvailable Halls:");
        //Display Hall Booked
        displayHallStatus();
        System.out.println("Enter hall name: ");
        String hallName = scanner.nextLine();
        int hallIndex = getHallIndex(hallName);
        if (hallIndex == -1) {
            System.out.println("Invalid hall name. Please try again.");
            return;
        }

        System.out.print("Enter seat number: ");
        int seatNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (seatNumber < 1 || seatNumber > halls[hallIndex].length - 1 || halls[hallIndex][seatNumber].equals(BOOKED)) {
            System.out.println("Invalid seat number or seat already booked. Please try again.");
            return;
        }

        // Perform booking
        halls[hallIndex][seatNumber] = BOOKED;

        // Update booking history
        bookingHistory[bookingCount++] = new String[]{hallName, String.valueOf(seatNumber), getCurrentDate(), showtimeStr};
        System.out.println("Booking successful!");
        System.out.print("-".repeat(50));
    }
    private static void displayHallStatus() {
        System.out.print("-".repeat(50));
        System.out.println("\nHall Status:");
        System.out.println("Seat legend: AV (Available), BO (Booked)");
        for (String[] hall : halls) {
            System.out.print(hall[0] + ": ");
            for (int i = 1; i < hall.length; i++) {
                System.out.print(hall[i] + " ");
            }
            System.out.println();
        }
    }
    /*show time schedule*/
    private static void displayShowtimeSchedule() {
        System.out.print("-".repeat(50));
        System.out.println("\nShowtime Schedule:");
        System.out.println("M. Morning A. Afternoon N. Night");
        System.out.println("Morning:   (9 AM - 12 PM)");
        System.out.println("Afternoon: (1 PM - 4  PM)");
        System.out.println("Night:     (7 PM - 10 PM)");
        System.out.print("-".repeat(50));
    }
    private static void displayBookingHistory() {
        System.out.println("-".repeat(50));
        System.out.println("Booking History:");
        System.out.println("Hall Name | Seat Number| Date of Booking| Showtime");

        for (int i = 0; i < bookingCount; i++) {
            for (int j = 0; j < bookingHistory[i].length; j++) {
                System.out.print(bookingHistory[i][j] + " | ");
            }
            System.out.println();
        }
        bookingCount = 0;
        System.out.println("-".repeat(50));
    }

    private static void rebootHall() {
        System.out.println("\nRebooting Hall...");
        // Reset all seats to available
        for (String[] hall : halls) {
            for (int i = 1; i < hall.length; i++) {
                hall[i] = AVAILABLE;
            }
        }
    }
    private static String getShowtimeString(String showtime) {
        return switch (showtime) {
            case "M","m" -> MORNING;
            case "A","a" -> AFTERNOON;
            case "N","n" -> NIGHT;
            default -> "Time is not available!";
        };

    }
    private static int getHallIndex(String hallName) {
        for (int i = 0; i < halls.length; i++) {
            if (halls[i][0].equalsIgnoreCase(hallName)) {
                return i;
            }
        }
        return -1; // Hall not found
    }
    //get Current LocalDateTime formatted
    private static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(formatter);
    }


}



