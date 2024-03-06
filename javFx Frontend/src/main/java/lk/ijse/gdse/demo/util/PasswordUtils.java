package lk.ijse.gdse.demo.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Hash a password
    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    // Verify a hashed password
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
