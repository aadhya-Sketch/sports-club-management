# Automated Sports Club Management System

A database-backed application to manage bookings, memberships, and payments for a
sports complex — built with Java, JDBC, and MySQL, with both a console interface
and a full web interface (Servlets + JSP on Apache Tomcat).



## Features

- Member registration and login, with:
  - Email format validation + real OTP email verification
  - Phone number format validation
  - Strong password requirements (6+ chars, upper/lower/digit/special char)
  - Passwords hashed with bcrypt — never stored in plain text
- Browse facilities (Tennis, Badminton, Multi-purpose, Archery) and their units
- Book a facility slot — fixed 1-hour duration, only available to members with an
  active, non-expired membership
- Prevents double-booking of the same unit at the same date/time
- Prevents booking or membership start dates in the past
- Cancel an existing booking (non-refundable, by policy)
- Buy or renew a membership (Monthly ₹500 / Quarterly ₹1000 / Yearly ₹1500)
- Fixed ₹300 flat booking fee
- Payment recording with selectable method: Cash, Card, UPI (real scannable QR
  code), or Net Banking
- View booking history
- Admin view: all bookings across all members, and total revenue

## Tech Stack

- **Backend:** Java (JDK 17+), JDBC
- **Database:** MySQL 8
- **Console app:** plain Java (`SportsClubManagement` project)
- **Web app:** Java Servlets + JSP on Apache Tomcat 10.1 (`SportsClubWeb` project)
- **Libraries:** MySQL Connector/J, ZXing (QR code generation), jBCrypt (password
  hashing), Jakarta Mail + Jakarta Activation (OTP email delivery)
- **IDE:** Eclipse IDE for Enterprise Java and Web Developers

## Project Structure

This repository contains two Eclipse projects sharing the same database:

```
SportsClubManagement/          ← Console application
 └── src/com/sportsclub/
      ├── db/       → DBConnection.java
      ├── model/    → Member, Facility, FacilityUnit, Booking, Membership, Payment
      ├── dao/      → MemberDAO, FacilityDAO, BookingDAO, MembershipDAO, PaymentDAO
      ├── util/     → ValidationUtil, EmailUtil, QRCodeGenerator
      └── main/     → App.java (console menu, entry point)

SportsClubWeb/                 ← Web application
 └── src/main/java/com/sportsclub/
      ├── db/, model/, dao/, util/   → same backend logic as above
      └── servlet/  → LoginServlet, RegisterServlet, VerifyOtpServlet,
                       DashboardServlet, FacilitiesServlet, UnitsServlet,
                       BookServlet, MyBookingsServlet, CancelBookingServlet,
                       BuyMembershipServlet, LogoutServlet,
                       AdminLoginServlet, AdminDashboardServlet,
                       AdminBookingsServlet, AdminRevenueServlet
 └── src/main/webapp/
      ├── *.jsp     → all pages (login, register, dashboard, booking, admin, etc.)
      └── style.css

database/
 └── schema.sql     → full exported MySQL schema

ER_Diagram.jpeg      ← Entity-Relationship diagram
```

## Database Setup

1. Open MySQL Workbench and create the database:
   ```sql
   CREATE DATABASE sports_club_db;
   ```
2. Run `database/schema.sql` against it to create all tables.
3. Update the credentials in **both** `DBConnection.java` files (one in each
   project — they are separate copies and must be kept in sync manually):
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/sports_club_db";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password";
   ```

## How to Run — Console App

1. Import `SportsClubManagement` into Eclipse.
2. Ensure the MySQL Connector/J jar is on the build path.
3. Ensure MySQL is running.
4. Run `com.sportsclub.main.App.java` as a Java Application.

## How to Run — Web App

1. Import `SportsClubWeb` into Eclipse IDE for Enterprise Java and Web Developers.
2. Ensure all six jars are on the build path **and** listed under
   Project Properties → Deployment Assembly (mapped to `WEB-INF/lib`):
   - `mysql-connector-j-*.jar`
   - `core-*.jar`, `javase-*.jar` (ZXing)
   - `jbcrypt-*.jar`
   - `jakarta.mail-*.jar`, `jakarta.activation-*.jar`
3. Set up an Apache Tomcat 10.1 server runtime pointing at your extracted Tomcat
   folder.
4. In `EmailUtil.java`, set a sender Gmail address and an App Password (requires
   2-Step Verification enabled on that Google account) to enable OTP emails.
5. Right-click `SportsClubWeb` → **Run As → Run on Server**.
6. Visit `http://localhost:8080/SportsClubWeb/login.jsp`.

### Admin Login
```
URL: http://localhost:8080/SportsClubWeb/adminLogin.jsp
Username: admin
Password: admin123
```
*(Hardcoded for this project — not production-grade credential storage.)*

## Database Tables

- **members** — registered users (hashed passwords)
- **facilities** — facility types (Tennis, Badminton, Multi-purpose, Archery)
- **facilities_units** — individual bookable units (e.g., Tennis Court 1)
- **bookings** — booking records, fixed 1-hour slots
- **memberships** — membership plans and validity
- **payments** — payment transactions for bookings and memberships, including
  payment method

## Known Simplifications

These are intentional trade-offs for a project at this scope, not oversights:

- Admin credentials are hardcoded rather than stored/hashed in the database.
- Card and Net Banking payments are simulated — no real payment gateway
  integration. UPI generates a genuine, scannable QR code (a real UPI deep link),
  but the app has no way to confirm whether payment was actually completed.
- Cancelled bookings are non-refundable by design.
- Phone numbers are format-validated only (not verified via SMS OTP), unlike
  email which is verified via a real OTP sent through Gmail SMTP.

## Author

Aadhya B N