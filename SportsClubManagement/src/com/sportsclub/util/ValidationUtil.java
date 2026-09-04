package com.sportsclub.util;

import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

public class ValidationUtil {
	
	private static final String PHONE_REGEX = "^[6-9]\\d{9}$";

	public static boolean isValidPhone(String phone) {
	    return phone != null && Pattern.matches(PHONE_REGEX, phone);
	}

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!_\\-]).{6,}$";

    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isStrongPassword(String password) {
        return password != null && Pattern.matches(PASSWORD_REGEX, password);
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}