package example.app.entities.users;

import example.app.entities.exceptions.EmptyArgumentException;
import example.app.entities.exceptions.NullArgumentException;
import example.app.entities.FileDatabaseCompatible;
import example.app.utility.Password;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * <h1>Class User</h1>
 *  <ul>
 *  <li>This class represents a user account.</li>
 *  <li>Each user has a unique ID (UUID v4) assigned to them which cannot be changed.</li>
 *  <li>Each user has a account creation date assigned to them at the time of their account creation.</li>
 *  <li>The password for each user is stored in a byte array, which is created by the encrypting the</li>
 *  <li>password with SHA512 algorithm.</li>
 *  <li>If two user have same username, then they are considered equal, else they aren't</li>
 * </ul>
 */
public class User implements Serializable, FileDatabaseCompatible {
    private final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    final private UUID id;
    private String username;
    private byte[] password;
    private String firstName;
    private String lastName;
    private String email;
    private final LocalDateTime accountCreationDate;

    /**
     * Getter for the ID
     * @return The id of the user
     */
    public UUID getId() {
        return id;
    }

    /**
     * Getter for the username
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username
     * @param username The username of the user
     * @throws NullArgumentException If the username is null
     * @throws EmptyArgumentException If the username is empty
     */
    public void setUsername(String username)
            throws NullArgumentException, EmptyArgumentException {
        if(username == null)
            throw new NullArgumentException("Cannot set the value of \"username\" in class \"User\" to null");
        if(username.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"username\" in class \"User\" to an empty string");

        this.username = username;
    }

    /**
     * Setter for the password
     * @param password The password of the user
     * @throws NullArgumentException If the password is null
     * @throws EmptyArgumentException If the password is empty
     */
    public void setPassword(String password)
            throws NullArgumentException, EmptyArgumentException {
        if(password == null)
            throw new NullArgumentException("Cannot set the value of \"password\" in class \"User\" to null");
        if(password.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"password\" in class \"User\" to an empty string");

        this.password = Password.hash(password);
    }

    /**
     * Getter for the first name
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the first name
     * @param firstName The first name of the user
     * @throws NullArgumentException If the first name is null
     * @throws EmptyArgumentException If the first name is empty
     */
    public void setFirstName(String firstName)
            throws NullArgumentException, EmptyArgumentException {
        if(firstName == null)
            throw new NullArgumentException("Cannot set the value of \"firstName\" in class \"User\" to null");
        if(firstName.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"firstName\" in class \"User\" to an empty string");

        this.firstName = firstName;
    }

    /**
     * Getter for the last name
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the last name
     * @param lastName The last name of the user
     * @throws NullArgumentException If the last name is null
     * @throws EmptyArgumentException If the last name is empty
     */
    public void setLastName(String lastName)
            throws NullArgumentException, EmptyArgumentException {
        if(lastName == null)
            throw new NullArgumentException("Cannot set the value of \"lastName\" in class \"User\" to null");
        if(lastName.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"lastName\" in class \"User\" to an empty string");

        this.lastName = lastName;
    }

    /**
     * Getter for the email
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email
     * @param email The email of the user
     * @throws NullArgumentException If the email is null
     * @throws EmptyArgumentException If the email is empty
     */
    public void setEmail(String email)
            throws NullArgumentException, EmptyArgumentException {
        if(email == null)
            throw new NullArgumentException("Cannot set the value of \"email\" in class \"User\" to null");
        if(email.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"email\" in class \"User\" to an empty string");

        this.email = email;
    }

    /**
     * Getter for the account creation date
     * @return The account creation date
     */
    public LocalDateTime getCreationDate() {
        return LocalDateTime.parse(accountCreationDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
    }

    /**
     * This method checks if a username is equal to the username of the user or not
     * @param username The username to be checked
     * @return true if the username is equal to the username of the user, false otherwise
     */
    public boolean usernameEquals(String username) {
        return this.username.equals(username);
    }

    /**
     * This method checks if a password is equal to the password of the user or not
     * @param password The password to be checked
     * @return true if the password is equal to the password of the user, false otherwise
     */
    public boolean passwordEquals(String password) {
        return Arrays.equals(this.password, Password.hash(password));
    }

    /**
     * This method checks if a given username and password are equal to the user or not
     * @param username The username to be checked
     * @param password The password to be checked
     * @return true if the username and password are equal to the user, false otherwise
     */
    public boolean credentialsEquals(String username, String password) {
        return this.usernameEquals(username) && this.passwordEquals(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getUsername());
//        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        return  "Name: " + getFirstName() + " " + getLastName() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Account creation date: " + getCreationDate();
    }

    User(String username, String password, String firstName, String lastName, String email)
            throws NullArgumentException, EmptyArgumentException {
        this.setUsername(username);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.accountCreationDate = LocalDateTime.now();
        this.id = UUID.randomUUID();
    }
}
