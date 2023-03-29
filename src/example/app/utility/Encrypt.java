package example.app.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    public static byte[] fromString(String password) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(password.getBytes());
        }
        catch (NoSuchAlgorithmException exception) {
            System.err.println(exception);
            return null;
        }
    }

    private Encrypt() {}
}
