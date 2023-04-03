package example.app.entities.users;

import example.app.utility.Password;
import java.util.Arrays;

/**
 * <h1>Class Admin</h1>
 * This is a final class that represents a Admin. All the values are hard coded as there can be only one admin
 * in this app. Admin class does not extend the User class.
 */
public final class Admin {
    /**
     * The field PASSWORD is the SHA512 encrypted password of the admin.
     */
    private static final byte[] PASSWORD = {
            -57,-83, 68, -53, -83, 118, 42, 93,
            -96, -92, 82, -7, -24, 84, -3, -63,
            -32, -25, -91, 42, 56, 1, 95, 35,
            -13, -22, -79, -40, 11, -109, 29, -44,
            114, 99, 77, -6, -57, 28, -45, 78,
            -68, 53, -47, 106, -73, -5, -118, -112,
            -56, 31, -105, 81, 19, -42, -57, 83,
            -115, -58, -99, -40, -34, -112, 119, -20
    };
    private static final String USER_NAME = "admin";
    public static final String FIRST_NAME = "Admin";
    public static final String LAST_NAME = "User";
    public static final String EMAIL = "admin@email.example";

    /**
     * This method checks if a username is equal to the admins or not.
     * @param username The username to be checked
     * @return True if the username is equal to the admins or false if not
     */
    public static boolean usernameEquals(String username) {
        return USER_NAME.equals(username);
    }

    /**
     * This method checks if a password is equal to the admins or not.
     * @param password The password to be checked
     * @return True if the password is equal to the admins or false if not
     */
    public static boolean passwordEquals(String password) {
        return Arrays.equals(PASSWORD, Password.hash(password));
    }

    /**
     * This method checks if a username and password are equal to the admins or not.
     * @param username The username to be checked
     * @param password The password to be checked
     * @return True if the username and password are equal to the admins or false if not
     */
    public static boolean credentialsEquals(String username, String password) {
        return usernameEquals(username) && passwordEquals(password);
    }
}
