package com.sportsclub.main;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

import com.sportsclub.dao.BookingDAO;
import com.sportsclub.dao.FacilityDAO;
import com.sportsclub.dao.MemberDAO;
import com.sportsclub.dao.MembershipDAO;
import com.sportsclub.dao.PaymentDAO;
import com.sportsclub.model.Facility;
import com.sportsclub.model.FacilityUnit;
import com.sportsclub.model.Member;
import com.sportsclub.model.Booking;
import com.sportsclub.util.ValidationUtil;
import com.sportsclub.util.QRCodeGenerator;
import com.sportsclub.util.EmailUtil;

public class App {

    static Scanner sc = new Scanner(System.in);
    static MemberDAO memberDAO = new MemberDAO();
    static FacilityDAO facilityDAO = new FacilityDAO();
    static BookingDAO bookingDAO = new BookingDAO();
    static PaymentDAO paymentDAO = new PaymentDAO();
    static MembershipDAO membershipDAO = new MembershipDAO();

    static Member currentMember = null; // holds the logged-in user

    public static void main(String[] args) {
        System.out.println("=== Welcome to Sports Club Management ===");

        while (currentMember == null) {
            System.out.println("\n1. Login\n2. Register\n3. Admin Login\n4. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> login();
                case "2" -> register();
                case "3" -> { adminLogin(); return; }
                case "4" -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid option.");
            }
        }

        mainMenu();
    }

    static void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        Member member = memberDAO.login(email, password);
        if (member != null) {
            currentMember = member;
            System.out.println("Login successful. Welcome, " + member.getName() + "!");
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    static void register() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        if (!ValidationUtil.isValidPhone(phone)) {
            System.out.println("Invalid phone number. Please enter a valid 10-digit mobile number.");
            return;
        }

        System.out.print("Email: ");
        String email = sc.nextLine();
        if (!ValidationUtil.isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter a valid email address (e.g. name@example.com).");
            return;
        }

        // Send OTP to verify the email is real and reachable
        String otp = EmailUtil.generateOtp();
        System.out.println("Sending verification code to " + email + "...");
        boolean sent = EmailUtil.sendOtpEmail(email, otp);

        if (!sent) {
            System.out.println("Could not send verification email. Please check the address and try again.");
            return;
        }

        System.out.print("Enter the 6-digit code sent to your email: ");
        String enteredOtp = sc.nextLine();

        if (!enteredOtp.equals(otp)) {
            System.out.println("Incorrect verification code. Registration cancelled.");
            return;
        }

        System.out.println("Email verified successfully!");

        System.out.print("Password: ");
        String password = sc.nextLine();
        if (!ValidationUtil.isStrongPassword(password)) {
            System.out.println("Weak password. Password must be at least 6 characters and include an " +
                    "uppercase letter, a lowercase letter, a number, and a special character (e.g. @#$%).");
            return;
        }

        Member member = new Member();
        member.setName(name);
        member.setPhoneNumber(phone);
        member.setEmail(email);
        member.setPassword(ValidationUtil.hashPassword(password));

        boolean success = memberDAO.registerMember(member);
        System.out.println(success ? "Registration successful! Please log in." : "Registration failed (email may already be in use).");
    }

    static void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Browse Facilities");
            System.out.println("2. Book a Slot");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. Buy Membership");
            System.out.println("5. View My Bookings");
            System.out.println("6. Logout");

            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
            case "1" -> browseFacilities();
            case "2" -> bookSlot();
            case "3" -> cancelBooking();
            case "4" -> buyMembership();
            case "5" -> viewMyBookings();
            case "6" -> { currentMember = null; running = false; }
            default -> System.out.println("Invalid option.");
        }
        }
        // Loop back to login/register after logout
        main(null);
    }

    static void browseFacilities() {
        List<Facility> facilities = facilityDAO.getAllFacilities();
        System.out.println("\n=== Facilities ===");
        for (Facility f : facilities) {
            System.out.println(f.getFacilityId() + ". " + f.getFacilityName() + " (" + f.getFacilityType() + ")");
        }

        System.out.print("Enter a Facility ID to see its units (or 0 to go back): ");
        int facilityId;
        try {
            facilityId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }
        if (facilityId == 0) return;

        List<FacilityUnit> units = facilityDAO.getUnitsByFacility(facilityId);
        System.out.println("\n=== Units ===");
        for (FacilityUnit u : units) {
            System.out.println(u.getUnitId() + ". " + u.getUnitName() + " - " + u.getStatus());
        }
    }

    static final double BOOKING_FEE = 300.00; // flat rate, 1 hour

    static void bookSlot() {
        var activeMembership = membershipDAO.getActiveMembership(currentMember.getMemberId());
        if (activeMembership == null) {
            System.out.println("You need an active membership to book a facility. Please purchase one first.");
            return;
        }

        int unitId;
        Date date;
        Time startTime;

        try {
            System.out.print("Enter Facility Unit ID: ");
            unitId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter date (yyyy-mm-dd): ");
            date = Date.valueOf(sc.nextLine());

            if (date.toLocalDate().isBefore(java.time.LocalDate.now())) {
                System.out.println("You cannot book a slot in the past.");
                return;
            }

            System.out.print("Enter start time (HH:mm:ss): ");
            startTime = Time.valueOf(sc.nextLine());

        } catch (Exception e) {
            System.out.println("Invalid input format. Please check unit ID, date (yyyy-mm-dd), and time (HH:mm:ss).");
            return;
        }

        Time endTime = new Time(startTime.getTime() + (60 * 60 * 1000));

        int bookingId = bookingDAO.createBookingAndReturnId(currentMember.getMemberId(), unitId, date, startTime, endTime);

        if (bookingId != -1) {
            System.out.println("Booking confirmed for 1 hour (" + startTime + " - " + endTime + "). Booking ID = " + bookingId);
            System.out.println("Booking fee: Rs. " + BOOKING_FEE);
            String method = choosePaymentMethod(BOOKING_FEE, "Booking Fee");
            String paymentResult = paymentDAO.recordPayment(currentMember.getMemberId(), BOOKING_FEE, "Booking", bookingId, method);
            System.out.println(paymentResult);
        } else {
            System.out.println("Booking failed — slot may already be taken.");
        }
    }
    
    static void cancelBooking() {
    	System.out.print("Enter Booking ID to cancel: ");
    	int bookingId;
    	try {
    	    bookingId = Integer.parseInt(sc.nextLine());
    	} catch (NumberFormatException e) {
    	    System.out.println("Invalid input. Please enter a numeric Booking ID.");
    	    return;
    	}
    	System.out.println(bookingDAO.cancelBooking(bookingId));
    }
    
    static void adminLogin() {
        System.out.print("Admin username: ");
        String username = sc.nextLine();
        System.out.print("Admin password: ");
        String password = sc.nextLine();

        // Simple hardcoded check — fine for a project this size
        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Admin login successful.");
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    static void adminMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View All Bookings");
            System.out.println("2. View Total Revenue");
            System.out.println("3. Logout");

            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> {
                    var bookings = bookingDAO.getAllBookings();
                    if (bookings.isEmpty()) {
                        System.out.println("No bookings found.");
                    } else {
                        System.out.println("\n=== All Bookings ===");
                        for (var b : bookings) {
                            System.out.println("Booking ID: " + b.getBookingId() +
                                    " | Member ID: " + b.getMemberId() +
                                    " | Unit ID: " + b.getUnitId() +
                                    " | Date: " + b.getBookingDate() +
                                    " | Time: " + b.getStartTime() + " - " + b.getEndTime() +
                                    " | Status: " + b.getBookingStatus());
                        }
                    }
                }
                case "2" -> System.out.println("Total Revenue: Rs. " + paymentDAO.getTotalRevenue());
                case "3" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }
    
    static void viewMyBookings() {
        var bookings = bookingDAO.getBookingsByMember(currentMember.getMemberId());

        if (bookings.isEmpty()) {
            System.out.println("You have no bookings yet.");
            return;
        }

        System.out.println("\n=== My Bookings ===");
        for (var b : bookings) {
            System.out.println("Booking ID: " + b.getBookingId() +
                    " | Unit ID: " + b.getUnitId() +
                    " | Date: " + b.getBookingDate() +
                    " | Time: " + b.getStartTime() + " - " + b.getEndTime() +
                    " | Status: " + b.getBookingStatus());
        }
    }

    static void buyMembership() {
        System.out.print("Membership type (Monthly/Quarterly/Yearly): ");
        String type = sc.nextLine().trim();
        // Normalize casing: "monthly" or "MONTHLY" -> "Monthly"
        type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();

        double amount = membershipDAO.getMembershipPrice(type);
        if (amount == -1) {
            System.out.println("Invalid membership type. Choose Monthly, Quarterly, or Yearly.");
            return;
        }

        Date start;
        try {
            System.out.print("Start date (yyyy-mm-dd): ");
            start = Date.valueOf(sc.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
            return;
        }
        if (start.toLocalDate().isBefore(java.time.LocalDate.now())) {
            System.out.println("Membership start date cannot be in the past.");
            return;
        }

        Date end = switch (type) {
            case "Monthly" -> Date.valueOf(start.toLocalDate().plusMonths(1));
            case "Quarterly" -> Date.valueOf(start.toLocalDate().plusMonths(3));
            case "Yearly" -> Date.valueOf(start.toLocalDate().plusYears(1));
            default -> start;
        };

        int membershipId = membershipDAO.purchaseMembership(currentMember.getMemberId(), type, start, end, amount);

        if (membershipId != -1) {
            System.out.println(type + " membership purchased for Rs. " + amount + ". Valid until " + end);
            System.out.println("Membership ID = " + membershipId);
            String method = choosePaymentMethod(amount, type + " Membership");
            String paymentResult = paymentDAO.recordPayment(currentMember.getMemberId(), amount, "Membership", membershipId, method);
            System.out.println(paymentResult);
        } else {
            System.out.println("Membership purchase failed.");
        }
    }
    
    static String choosePaymentMethod(double amount, String note) {
        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. UPI");
        System.out.println("4. Net Banking");
        System.out.print("Choose an option: ");
        String choice = sc.nextLine();

        return switch (choice) {
            case "1" -> "Cash";
            case "2" -> processCardPayment();
            case "3" -> processUpiPayment(amount, note);
            case "4" -> processNetBankingPayment();
            default -> "Cash";
        };
    }

    static String processUpiPayment(double amount, String note) {
        String qrPath = QRCodeGenerator.generateUpiQr(amount, note);
        if (qrPath != null) {
            System.out.println("Scan the QR code to pay via UPI.");
            System.out.println("QR code saved at: " + qrPath);
            try {
                // Try to open it automatically in the default image viewer
                java.awt.Desktop.getDesktop().open(new java.io.File(qrPath));
            } catch (Exception e) {
                System.out.println("(Could not auto-open the image — open the file manually from the path above.)");
            }
            System.out.print("Press Enter once payment is complete: ");
            sc.nextLine();
        } else {
            System.out.println("Could not generate QR code. Defaulting to Cash.");
            return "Cash";
        }
        return "UPI";
    }

    static String processCardPayment() {
        System.out.print("Enter Card Number (16 digits): ");
        String cardNumber = sc.nextLine();
        if (!cardNumber.matches("\\d{16}")) {
            System.out.println("Invalid card number. Defaulting to Cash.");
            return "Cash";
        }

        System.out.print("Enter Expiry (MM/YY): ");
        String expiry = sc.nextLine();
        if (!expiry.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            System.out.println("Invalid expiry format. Defaulting to Cash.");
            return "Cash";
        }

        System.out.print("Enter CVV (3 digits): ");
        String cvv = sc.nextLine();
        if (!cvv.matches("\\d{3}")) {
            System.out.println("Invalid CVV. Defaulting to Cash.");
            return "Cash";
        }

        // Simulation only — CVV is intentionally never stored or logged anywhere
        System.out.println("Processing card payment ending in " + cardNumber.substring(12) + "...");
        return "Card";
    }

    static String processNetBankingPayment() {
        System.out.println("Select your bank:");
        System.out.println("1. SBI  2. HDFC  3. ICICI  4. Axis");
        System.out.print("Choose: ");
        String bankChoice = sc.nextLine();
        String bank = switch (bankChoice) {
            case "1" -> "SBI";
            case "2" -> "HDFC";
            case "3" -> "ICICI";
            case "4" -> "Axis";
            default -> "Other Bank";
        };

        System.out.println("Redirecting to " + bank + " net banking portal (simulated)...");
        System.out.print("Enter your account reference number: ");
        String refNumber = sc.nextLine();
        System.out.println("Payment via " + bank + " Net Banking (Ref: " + refNumber + ") successful.");
        return "Net Banking";
    }
}