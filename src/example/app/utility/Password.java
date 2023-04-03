package example.app.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h1>Class Password</h1>
 * This class provides methods for the operations related to password.
 */
public final class Password {
    /**
     * This method hashes a String password with SHA512 algorithm.
     * @param password The password to be hashed
     * @return The hashed password of the user as array of bytes
     */
    public static byte[] hash(String password) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(password.getBytes());
        }
        catch (NoSuchAlgorithmException exception) {
            System.err.println(exception);
            return null;
        }
    }
}
